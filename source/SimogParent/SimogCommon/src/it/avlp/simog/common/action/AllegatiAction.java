package it.avlp.simog.common.action;

import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.AllegatoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;

import com.philvarner.clamavj.ClamScan;
import com.philvarner.clamavj.ScanResult;

public class AllegatiAction 
{
	protected Connection connection;
	protected Logger logger;
	protected SimogProperties conf;
	protected String esitoCheck;
	protected AllegatoBean ab;
	
	protected AllValidationBeans mEccezioni;

	public AllegatiAction(AllegatoBean aBean, SimogProperties config, Connection activeConnection, Logger logger) {
		this.connection = activeConnection;
		this.logger = logger;
		this.mEccezioni = new AllValidationBeans();
		this.conf = config;
		this.ab = aBean;
	}
	
	
	public int checkAndSave(){
		int retVal = 0;
        
		File savedFile = null;
		String pathFileMese = null;
		try {
			// verifica grandezza
			if(ab.getBout().length > conf.getMax_file_size())
				throw new Exception("Allegato supera la massima lunghezza ammessa");
							
			// test antivirus tramite api Clamav
			if (conf.getClamHost() != null){
				ClamScan cs = new ClamScan(conf.getClamHost(),
										Integer.parseInt(conf.getClamPort()),
										Integer.parseInt(conf.getClamTO()),
										logger);
				
				ScanResult scr = new ScanResult("");
				
				
				try {
					scr = cs.scan(ab.getBout());
					// virus!
					if (scr.getStatus() != ScanResult.Status.PASSED){
						throw new Exception(scr.getStatus().toString());
					}					
				} catch (Exception e) {
					String mess = 
						ScanResult.Status.ERROR.toString().equals(e.getMessage()) 
							? scr.getException().getMessage()
							: ScanResult.Status.FAILED.toString().equals(e.getMessage())
								? scr.getResult() : e.getMessage();
							
					logger.info("*** virusch check fallito! " + mess + " - "+ ab.getNomeFile());
					throw new Exception(mess);
					
				}
			}

			// salvo l'allegato se non ci sono errori
			// ... su filesystem		
			           	
			//CODICE INIZIALE PRIMA DELLA MEV 34186 3.04.8 VALIDO MA COMMENTATO
//			FileOutputStream outFile = new FileOutputStream(conf.getPathAllegati() + ab.getNomeFile());
//			outFile.write(ab.getBout());
//			outFile.flush();
//			outFile.close();
			
			//MEV 34186 3.04.8
			Calendar c = Calendar.getInstance();
			Date d = c.getTime();
			System.out.println(d);
			
			//String pathJboss = System.getProperty("jboss.home.dir");
						
			pathFileMese = conf.getPathAllegati()+new SimpleDateFormat("MMMMMM").format(c.getTime())+"_"+new SimpleDateFormat("yyyy").format(c.getTime())+"/";
						
			File dir = null;			
			try 
			{						
				dir = new File(pathFileMese);
				ab.setPathFile(pathFileMese);
				boolean boolCreatedDir = false;
				
				 if(dir.exists() && dir.isDirectory())
				 {
					 FileOutputStream outFile = new FileOutputStream(pathFileMese + ab.getNomeFile());
					 outFile.write(ab.getBout());
					 outFile.flush();
					 outFile.close();					
				 }		
				 else
				 {
					 boolCreatedDir = dir.mkdirs();  
					 
				     if(boolCreatedDir)
				      {  
				         logger.info("Directory is created successfully : name is "+dir.getAbsolutePath());
				         FileOutputStream outFile = new FileOutputStream(pathFileMese + ab.getNomeFile());
						 outFile.write(ab.getBout());
						 outFile.flush();
						 outFile.close();
				      }
				      else
				      {  
				         logger.error("Error while create directory!");
				      }
				 }
			 
			}
			catch(IOException e)
			{
				logger.error("errore durante il salvataggio remoto del file : "+ab.getNomeFile());
			}
			//FINE MEV 34186 3.04.8 -----------------------------

			
			// verifica congruenza formato PDF				
			//savedFile = new File(conf.getPathAllegati() + ab.getNomeFile());
			savedFile = new File(pathFileMese + ab.getNomeFile());
			boolean validPdf = true;
			
	        Document document = new Document();
	        try {
	            //document.setFile(conf.getPathAllegati() + ab.getNomeFile());
	        	document.setFile(pathFileMese + ab.getNomeFile());
	        } catch (PDFException ex) {
	            logger.info("Error parsing PDF document " + ex);
	            validPdf = false;
	        } catch (PDFSecurityException ex) {
	        	logger.info("Error encryption not supported " + ex);
	            validPdf = false;
	        } catch (FileNotFoundException ex) {
	        	logger.info("Error file not found " + ex);
	            validPdf = false;
	        } catch (IOException ex) {
	        	logger.info("Error handling PDF document " + ex);
	            validPdf = false;
	        }
	        
	        document.dispose();
	        

			if(!validPdf){
				// cancello il file
				savedFile.delete();
				throw new Exception("L'allegato non e' un file PDF valido");
			}
			
			// salvataggio record allegato
			try {
				
			 AllegatoManager aManager = new AllegatoManager( connection, logger );
			 ab.setPathFile(pathFileMese);
			 ab = aManager.storeAllegato(ab);
			 retVal = ab.getIdAllegato();
			 
			 if(savedFile != null){
				 //File newFile = new File(conf.getPathAllegati() + Integer.toString(ab.getIdGara()).trim() + "_" + Integer.toString(ab.getIdAllegato()).trim() + "_" + ab.getTipoDoc()+".pdf");
				 //MEV 34186 3.04.8
				 File newFile = new File(pathFileMese + Integer.toString(ab.getIdGara()).trim() + "_" + Integer.toString(ab.getIdAllegato()).trim() + "_" + ab.getTipoDoc()+".pdf");
				 savedFile.renameTo(newFile);
				 //FINE MEV 34186 3.04.8
			 }
			 
			} catch (SQLException e) {
				e.printStackTrace();
				esitoCheck = e.getMessage();
			}
			
		} catch (Exception e) {
			esitoCheck = e.getMessage();
			if (esitoCheck == null) 
			   esitoCheck = "L'allegato non e' un file PDF valido";
			
			ab.setEsitoCheck(esitoCheck);
		}
		
		return retVal;
	}
		
	public List<AllegatoBean> load(AllegatoBean aBean){
		List<AllegatoBean> retVal = new ArrayList<AllegatoBean>();
		
		try {
			 AllegatoManager aManager = new AllegatoManager( connection, logger );	 
			 retVal = aManager.load(aBean);
			 			 
			} catch (SQLException e) {
				e.printStackTrace();
				esitoCheck = e.getMessage();
				retVal = null;
			}
		
		return retVal;
	}
	
	/** restituisce il Bean e anche l'allegato
	 * @param idAllegato
	 * @return
	 */
	public AllegatoBean load(String idAllegato, boolean content){
		AllegatoBean retVal = null;
		
		try {
			 AllegatoManager aManager = new AllegatoManager( connection, logger );	 
			 retVal = aManager.load(Long.parseLong(idAllegato));
			 			
			 if(content){
				 
				 // PP 3.02.2 in base alla data di inserimento, ed alla scadenza prevista, determino il giusto messaggio
				 boolean estinto = false;
				 
				 if(conf.getGiorniAllegati() != null){
					 Integer giorni = Integer.parseInt(conf.getGiorniAllegati());
					 
					 String datScad = PageHelper.formatDate(PageHelper.getIncreasedDate(PageHelper.getDBDateFromTS(retVal.getDataUpload()), giorni).getTime());
					 estinto =  datScad.compareTo(PageHelper.getCurrentDate()) < 0; 
				 }
                 //MEV 34186 3.04.8
								 
				 if(retVal.getPathFile() == null || retVal.getPathFile().equalsIgnoreCase(""))
				 {	 
					 retVal.setBout(readFile(conf.getPathAllegati() 
							 + Integer.toString(retVal.getIdGara()).trim() 
							 + "_" + Integer.toString(retVal.getIdAllegato()).trim() 
							 + "_" + retVal.getTipoDoc()+".pdf", estinto));
				 }
				 else
				 {
					 retVal.setBout(readFile(retVal.getPathFile() 
							 + Integer.toString(retVal.getIdGara()).trim() 
							 + "_" + Integer.toString(retVal.getIdAllegato()).trim() 
							 + "_" + retVal.getTipoDoc()+".pdf", estinto));
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
			esitoCheck = e.getMessage();
			retVal = null;
		}
		
		return retVal;
	}
	
	public void delete(String idAllegato){		
		try {
			AllegatoManager aManager = new AllegatoManager( connection, logger );	 

			AllegatoBean ab = aManager.load(Long.parseLong(idAllegato));
             
			File newFile = null;
			if(ab.getPathFile() == null || ab.getPathFile().equalsIgnoreCase(""))
			{
				newFile = new File(conf.getPathAllegati() + Integer.toString(ab.getIdGara()).trim() + "_" + Integer.toString(ab.getIdAllegato()).trim() + "_" + ab.getTipoDoc()+".pdf");								
			}
			else //MEV 34186 3.04.8 E PER MANTENERE LA RETRO COMPATIBILITA CON GLI ALLEGATI CHE NON HANNO IL FOLDER MENSILE 
			{
				newFile = new File(ab.getPathFile() + Integer.toString(ab.getIdGara()).trim() + "_" + Integer.toString(ab.getIdAllegato()).trim() + "_" + ab.getTipoDoc()+".pdf");
			}
			
			newFile.delete();
    		
			aManager.delete(Integer.parseInt(idAllegato));
			} catch (Exception e) {
				e.printStackTrace();
				esitoCheck = e.getMessage();
			}	
	}

	/*********************************************************************
	 * il metodo si occupa di restituire il parametro mEccezioni 
	 * definito nella classe contenente i messaggi relativi alle accezioni rilevate
	 * 
	 * @return  AllValidatorBeans
	 */
	public AllValidationBeans getEccezioni() {
		return mEccezioni;
	}


	public String getEsitoCheck() {
		return esitoCheck;
	}	
	
	/**
	   *  Reads a file storing intermediate data into a list. Fast method.
	   *  @param file the file to be read
	   *  @return a file data
	   */

	   public ByteArrayOutputStream readFile(String file, boolean estinto) throws Exception {

		  InputStream in = null;
	      ByteArrayOutputStream bout = new ByteArrayOutputStream();
	      File lFile = new File(file);

 	      // PP 3.02.2
	      if(!lFile.exists()){
	    	  if (estinto)
	    		  throw new Exception(Messaggi.SIMOG_ALLEGATI_001); 
	    	  else{
	    	      this.logger.fatal("*** ALLEGATO non trovato! " + file);
	    		  throw new Exception(Messaggi.SIMOG_ALLEGATI_002);
	    	  }
	      }
	      
    	  try{

	         in = new BufferedInputStream(new FileInputStream(file));
	         byte[] buf = new byte[(int)lFile.length()];

	         int offset = 0;
	         int numRead = 0;
	         while (offset < buf.length
	                && (numRead=in.read(buf, offset, buf.length-offset)) >= 0) {
	             offset += numRead;
	         }

	         bout.write(buf);

	      }finally{
	         if (in != null) try{ in.close();}catch (Exception e){}
	      }

	      return bout;
	   }
}
