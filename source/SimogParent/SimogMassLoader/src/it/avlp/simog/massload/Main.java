package it.avlp.simog.massload;

import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.massload.parser.XmlParser;
import it.avlp.simog.massload.util.FeedBackWriterBase;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.xmlbeans.XmlException;
/**
 * 
 *  
 * Processo di caricamento dei dati provenienti dagli enti speciali
 * evocato da linea di comando riceve in in put il nome del file
 * da elaborare, scrive in output il file di risposta
 * 
 * Solo per quanto riguarda blande modifiche ^^
 * per pulire un po' il codice sopratutto in vista della
 * cresciuta complessita' dei dati passati devolvo
 * la responsabilita' del caricamento (implementazione specifica)
 * a classi apposite pur mantenendo il controllo a questo livello
 * (riuso)
 * 
 *
 */
public class Main {
	
	private static final String IN_PATH = "/IN";
	private static final String OUT_PATH = "/OUT";
	private static final String WRK_PATH = "/WRK";
	private static final String BKP_PATH = "/BKP";
	
	private static final String ERR_USAGE = "MassLoader - Utilizzo:\n"
		+ "   java -jar MassLoader.jar <pathBase> <nomeFileIn> <pathConf> <nomeConf>";
	
	private static final String SOFTWARE_VERS = "Simog MassLoader";
	private static final String PROP_FILE_NAME = "massloaderversion.properties";
	
	/** punto di ingresso per lettura dati da filesystem
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {

	   MassLoader classe = new MassLoader(OrigineSchedaEnum.MASSLOADER);
	   
		if (args.length > 4 || args.length == 0){
			System.err.println(ERR_USAGE);
			
			System.exit(MassLoader.RET_VALUES.INSUFF_PARAM.ordinal());
		}
		
		try {
			
			if(args[0].equals("--version")) {
				Properties prop = new Properties();
				InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME);
				prop.load(inputStream);
	            System.err.println(SOFTWARE_VERS+" "+prop.getProperty("version.number")+" ("+prop.getProperty("build.date")+")");
				System.exit(MassLoader.RET_VALUES.INSUFF_PARAM.ordinal());
			}
			
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
			System.err.println(e2.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		}
		
		


		// forzatura per prove locali
		if (args[0].equals("DEBUG")){
			classe.pathBase = "C://PROGETTI//ANAC//TestMassLoader//test";
			classe.nomeFile = "test_agg.xml";
			classe.nomePathConf = "C://PROGETTI/ANAC/TestMassLoader//conf";
			classe.nomeFileConf = "massloader.properties";
		} else if (args[0].equals("DEBUGUNIX")){
			classe.pathBase = "/opt/MassLoaderEnv/DATI/test";
			classe.nomeFile = args[1];
			classe.nomePathConf = "/opt/MassLoaderEnv/conf/test";
			classe.nomeFileConf = "massloader.properties";
		}
		else {	
		   classe.pathBase = args[0];
		   classe.nomeFile = args[1];
		   classe.nomePathConf = args[2];
		   classe.nomeFileConf = args[3];
		}

		classe.nomeFileOut =  classe.pathBase +  OUT_PATH + "/" + classe.nomeFile + "_feedback";		
		classe.nomeFileOutNewFashion = classe.pathBase +  OUT_PATH + "/NEW_" + classe.nomeFile + "_feedback";		

		/*
		 * verifica congruenza parametri di input
		 * pathBase deve esistere
		 * nomeFile deve esistere
		 * pathConf deve esistere
		 * nomeConf deve esistere
		 * inoltre viene controllata l'esistenza delle directory IN, OUT e WORK creandole se necessario 
		 */
		if(!creaDir(classe.nomePathConf)) {
			System.err.println(MassLoader.ERR_NO_CONF);
			System.exit(MassLoader.RET_VALUES.NO_CONFIG.ordinal());	
		}
		if(!checkFile(classe.nomePathConf + "/" + classe.nomeFileConf)) {
			System.err.println(MassLoader.ERR_NO_CONF);
			System.exit(MassLoader.RET_VALUES.NO_CONFIG.ordinal());			
		}
		
		if(!creaDir(classe.pathBase)) {
			System.err.println(MassLoader.ERR_NO_BASE);
			System.exit(MassLoader.RET_VALUES.NO_BASEPATH.ordinal());				
		}
		if(!creaDir(classe.pathBase + IN_PATH)) {
			
			System.err.println(MassLoader.ERR_NO_IN);
			System.exit(MassLoader.RET_VALUES.NO_INPATH.ordinal());			
		}
		if(!creaDir(classe.pathBase  + OUT_PATH)) {
			System.err.println(MassLoader.ERR_NO_OUT);
			System.exit(MassLoader.RET_VALUES.NO_OUTPATH.ordinal());			
		}
		if(!creaDir(classe.pathBase  + WRK_PATH)) {
			System.err.println(MassLoader.ERR_NO_WRK);
			System.exit(MassLoader.RET_VALUES.NO_WORKPATH.ordinal());			
		}
		
		if(!checkFile(classe.pathBase  + WRK_PATH + "/" + classe.nomeFile)) {
			System.err.print("??? "+classe.pathBase  + WRK_PATH + "/" + classe.nomeFile);
			System.err.println(MassLoader.ERR_NO_INFILE);
			System.exit(MassLoader.RET_VALUES.NO_INPATH.ordinal());		
		}
		
		// Impostazione del flag che disabilita i controlli sulle date validità nelle tipologiche
		SimogFlags.setFlagNoDate(true);
		SimogFlags.setFromMassLoader(true);//Ticket ALM #3529
		
        /*
         * Inizializzazione configurazione
         */
        try {
            classe.init(classe.nomePathConf, classe.nomeFileConf);
        } catch (Exception e1) {
            System.err.println(MassLoader.ERR_INIT + " - " + e1.getMessage());
            System.exit(MassLoader.RET_VALUES.FATAL_ERROR.ordinal());   
        }

        /*
         * istanza nuovo parser
         */
        XmlParser test = new XmlParser(classe.logger, classe.configuration.getUsername());

        // Blocco Try-Catch principale, in catch solo per eccezioni inaspettate
		try{
					
				classe.logger.debug("*** Elaborazione file: " + classe.pathBase  + WRK_PATH + "/" + classe.nomeFile);
				
				/**
				 * variabile necessaria per identificare se la validazione xml e' riuscita
				 * 
				 * */
	
				try {
					// validazione rispetto alla struttura ed eventuale scrittura feedback
					//gestisce sia errori di validazione che eccezione xmlException
					test.xsdValidate(classe.pathBase + WRK_PATH + "/" + classe.nomeFile, false);
				} catch (XmlException e) {
				   classe.logger.error(MassLoader.ERR_XML, e); //  non ritorno FATAL per non allertare il monitoraggio 
					System.err.println(MassLoader.ERR_XML + " - " + e.getMessage());
				} catch (IOException e) {
				   classe.logger.fatal(MassLoader.ERR_IO, e);
					System.err.println(MassLoader.ERR_IO + " - " + e.getMessage());
					System.exit(MassLoader.RET_VALUES.FATAL_ERROR.ordinal());	
				}
				
				classe.elabora(test);
								
				//classe.feedbackWrite(test.getFeedbackXml(), classe.cig, null, null); 

		}catch(Exception t){
			// log
		   classe.logger.fatal("Eccezione in Main: " +t.getMessage());
			
			// stack trace
			t.printStackTrace();
			
			// try to write on feedback..

			FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(classe.logger, classe.configuration.getUsername());
			
			//FeedBackDocument feedDoc = FeedBackDocument.Factory.newInstance();
			
			feedBackWriter.writeUnandledException(test.getFeedbackXml(), classe.cig, FeedBackWriterBase.getStack(t), classe.configuration.getUsername());
			
			// classe.feedbackWrite(test.getFeedbackXml(), classe.cig, null, null);

			classe.setRetVal(MassLoader.RET_VALUES.FATAL_ERROR.ordinal());			
		}
		finally{
			// chiusura della connessione se ancora attiva.
			if(classe.getDbm() != null && classe.getDbm().getCurrentActiveConnection() != null){
			   classe.logger.info("Closing connection: " + classe.getDbm().getCurrentActiveConnection().toString());
			   classe.getDbm().closeConnection();
			}
			classe.feedbackWrite(test.getFeedbackXml(), classe.cig, null, null);
		}
		
		System.exit(classe.getRetVal());
//		return;
	}

	/*
	 * controllo esistenza file
	 */
	private static boolean checkFile(String lFile)
	{
		return (new File(lFile)).exists();
	}	
	
	/*
	 * creazione directory compresi i rami intermedi
	 */
	private static boolean creaDir(String dir)
	{
		File lDir = new File(dir);
		
		if(lDir.exists()) return true;
		
		return (lDir.mkdirs());
	}	
}
