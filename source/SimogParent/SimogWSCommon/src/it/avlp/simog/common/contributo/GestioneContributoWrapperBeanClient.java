package it.avlp.simog.common.contributo;

import it.avcp.spc.appalti.ejb.servizi.ContributoClient;
import it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributo;
import it.avcp.spc.appalti.ejbImpl.servizi.GestioneContributoWSBeanServiceLocator;
import it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo;
import it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToMapImportContributoEntry;
import it.avcp.spc.appalti.ejbImpl.servizi.SeverityLevel;
import it.avcp.spc.appalti.ejbImpl.servizi.ValidationBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.IMPORTI;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;


public class GestioneContributoWrapperBeanClient extends AccessiDB {

	private String url;
	private Logger logger = null;
	private Connection conn = null;
	private RicercaContributoTo esitoRicerca = new RicercaContributoTo();
	private boolean localEsente;
	
	public GestioneContributoWrapperBeanClient(String url, Connection conn, Logger logger) {
		super(conn, logger);
	    this.url = url;
		this.logger = logger;
		this.conn = conn;
	}

	
	/**
	 * Richiede al WS il calcolo contributo per la SA
	 * 
	 * @param cfAmm
	 * @param importo
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public BigDecimal getContributoSA(ParametriContributo params) 
	throws Exception
	{
		logger.debug("*** CALCOLO CONTRIBUTO SA : " + (url == null ? "INTERNO" : url));
		logger.debug(ObjectIntrospector.propertiesInfo(ParametriContributo.class, params));
		
		try {
			if (url == null){
				localEsente=false;
				// PP patch piccinini 06.04.2011 se adesione ritorno sempre zero 
//				3.04.8 34190 fix
				if(params.getModoReal() == Costanti.MODOREAL_ADESIONE || params.getModoReal() == Costanti.MODOREAL_ADESIONE_NOCOMPET || params.getModoReal() == Costanti.MODOREAL_CONCESSIONE || params.getModoReal() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) {
					return new BigDecimal(0);
				}

				BigDecimal[] importi = getImportiByImportoLotto(params.getImporto(), PageHelper.getFormattedCalendarDate(params.getDataPubblicazione()), params.getModoReal());
				return importi[0];
			}
			else if ("EJB".equals(url)){
				// tramite EJB
				ContributoClient cli = new ContributoClient();
				RicercaContributoTo ret = cli.determinazioneContributoSA(params.getCodiceFiscale(), params.getImporto(), params.getDataPubblicazione().getTime(), params.getMotivoEscusione(), params.getTipoProcedura(), params.getCigAccordoQuadro(), params.getApplicazione(),params.getIdGara());
				esitoRicerca = ret;
				logger.debug("*** CALCOLO CONTRIBUTO SA : valore ritornato " + ret.getImportContributo().toPlainString());

				return ret.getImportContributo();
			}
			else {
				// tramite WS
			   
	            // PP 3.02.3 NRF-WEB_XX.00 check se raggiungibile
               if(SimogFlags.is30230_NRFWEBXX00Active()){ 
      			   if(!ping(new URL(url).getHost())){
                           logger.error("Host contributo non raggiungibile");
                           throw new Exception("Host contributo non raggiungibile");
                      }
               }

				GestioneContributoWSBeanServiceLocator locator = new GestioneContributoWSBeanServiceLocator();
				DeterminazioneContributo proxy = locator.getDeterminazioneContributoPort(new URL(url));
				RicercaContributoTo ret = proxy.determinazioneContributoSA(params.getCodiceFiscale(), params.getImporto(), params.getDataPubblicazione(), params.getMotivoEscusione(), params.getTipoProcedura(), params.getCigAccordoQuadro(), params.getApplicazione(),params.getIdGara());		
				esitoRicerca = ret;			
				logger.debug("*** CALCOLO CONTRIBUTO SA : valore ritornato " + ret.getImportContributo().toPlainString());

				return ret.getImportContributo();
			}			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			
			ValidationBean[] lista = new ValidationBean[1 + (esitoRicerca.getEsitoValidazione() == null ? 0 : esitoRicerca.getEsitoValidazione().length)];
			lista[0] = new ValidationBean(Messaggi.SIMOG_CONTRIBUTO_002,"SIMOG_CONTRIBUTO_002",0,0,SeverityLevel.ERROR);
			
			if (esitoRicerca.getEsitoValidazione() != null){
				for (int i = 0; i < esitoRicerca.getEsitoValidazione().length; i++) {
					lista[i+1] = esitoRicerca.getEsitoValidazione(i);
				}
			}
			
			esitoRicerca.setEsitoValidazione(lista);
			
			return null;
		}	
	}		

	/**
	 * Richiede al WS il calcolo contributo per l'OE
	 * 
	 * @param cfAmm
	 * @param importo
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public BigDecimal getContributoOE(ParametriContributo params) //entrato qui ticket contributo 50kk
	throws Exception
	{
		logger.debug("*** CALCOLO CONTRIBUTO OE : " + (url == null ? "INTERNO" : url));
		logger.debug(ObjectIntrospector.propertiesInfo(ParametriContributo.class, params));

		try {
         String scelta = params.getTipoProcedura();
         
         // ricavo la scelta contraente equivalente
//         if(SimogFlags.is3028_RFWEBGL00Active()){
//            LottoManager lm = new LottoManager(conn, logger);
//            scelta = lm.getSceltaContraenteAVCP(null, Long.valueOf(scelta));
//         }
         
			if (url == null){ //url in sviluppo http://10.119.26.28:8080/GestioneContributoService/GestioneContributoWSBean
				// PP patch piccinini 06.04.2011 se adesione ritorno sempre zero 
				//3.04.8 34190 fix
				if(params.getModoReal() == Costanti.MODOREAL_ADESIONE || params.getModoReal() == Costanti.MODOREAL_ADESIONE_NOCOMPET || params.getModoReal() == Costanti.MODOREAL_CONCESSIONE || params.getModoReal() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) {
		    		localEsente = true;
					return new BigDecimal(0);
				}
	
		    	// patch contratti esclusi, l'importo va azzerato perche non e dovuto il contributo
		    	if (params.getMotivoEscusione() != null && "0".compareTo(params.getMotivoEscusione()) < 0){
		    		localEsente = true;
		    		params.setImporto(new BigDecimal(0));
		    	}
		    	
				BigDecimal[] importi = getImportiByImportoLotto(params.getImporto(), PageHelper.getFormattedCalendarDate(params.getDataPubblicazione()), params.getModoReal());
				
				return importi[1];
			}
			else if ("EJB".equals(url)){
				// tramite EJB
				ContributoClient cli = new ContributoClient();
				RicercaContributoTo ret 
				   = cli.determinazioneContributoOE(params.getCodiceFiscale(), 
				         params.getImporto(), params.getDataPubblicazione().getTime(), 
				         params.getMotivoEscusione(), scelta, params.getCigAccordoQuadro(), 
				         params.getApplicazione(),params.getIdGara());
				esitoRicerca = ret;
				logger.debug("*** CALCOLO CONTRIBUTO OE : valore ritornato " + ret.getImportContributo().toPlainString());
	
				return ret.getImportContributo();
			}
			else { //entra qui ticket contributo 50kk
				// tramite WS
			   
			    // PP 3.02.3 NRF-WEB_XX.00 check se raggiungibile
                if(SimogFlags.is30230_NRFWEBXX00Active()){ 
                   if(!ping(new URL(url).getHost())){
                          logger.error("Host contributo non raggiungibile");
                          throw new Exception("Host contributo non raggiungibile");
                   }
               }

			    GestioneContributoWSBeanServiceLocator locator = new GestioneContributoWSBeanServiceLocator();
				DeterminazioneContributo proxy = locator.getDeterminazioneContributoPort(new URL(url));
				RicercaContributoTo 
				   ret = proxy.determinazioneContributoOE(params.getCodiceFiscale(), 
				            params.getImporto(), params.getDataPubblicazione(), 
				            params.getMotivoEscusione(), scelta, params.getCigAccordoQuadro(), 
				            params.getApplicazione(), params.getIdGara());		
				esitoRicerca = ret;
				logger.debug("*** CALCOLO CONTRIBUTO OE : valore ritornato " + ret.getImportContributo().toPlainString());
				
				return ret.getImportContributo();
			}	
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			
			ValidationBean[] lista = new ValidationBean[1 + (esitoRicerca.getEsitoValidazione() == null ? 0 : esitoRicerca.getEsitoValidazione().length)];
			lista[0] = new ValidationBean(Messaggi.SIMOG_CONTRIBUTO_003,"SIMOG_CONTRIBUTO_003",0,0,SeverityLevel.ERROR);
			
			if (esitoRicerca.getEsitoValidazione() != null){
				for (int i = 0; i < esitoRicerca.getEsitoValidazione().length; i++) {
					lista[i+1] = esitoRicerca.getEsitoValidazione(i);
				}
			}
			
			esitoRicerca.setEsitoValidazione(lista);
			
			return null;
		}	
	}		

	/*****************************************************************************************************
	 * Calcola il corrispettivo dovuto da
	 * Stazione Appaltante
	 * Impresa partecipante
	 * in corrispondenza di una base d'asta
	 * Le informazioni sono contenute nella tabella
	 * Importi
	 * @param importoLotto : Bigdecimal per l'importo del lotto
	 * @param dataVal : Data
	 *****************************************************************************************************/
	
	
	public BigDecimal[] getImportiByImportoLotto ( BigDecimal importoLotto, String dataVal , int modoReal) throws SQLException {
		
		BigDecimal[] result = new BigDecimal[2];
		
		result [0] = BigDecimal.valueOf(0);
		result [1] = BigDecimal.valueOf(0);

		String selectImportiByImportoLotto =
			"SELECT "
			+ IMPORTI.IMPORTO_SA
			+ ", " + IMPORTI.IMPORTO_AZIENDA
			+ " FROM "
			+ IMPORTI.TABLE_NAME
			+ " WHERE " + buildISNULL("IMPORTI.IMPORTO_LOTTO_MAX",999999999999L) + " >= ?"
			+ " AND "
			+ IMPORTI.IMPORTO_LOTTO_MIN + " < ?"
			+ " AND " + buildISNULL("IMPORTI.DATA_FINE_VALIDITA","99999999") + " >= ?"
			+ " ORDER BY " + buildISNULL("IMPORTI.DATA_FINE_VALIDITA","99999999")
			;
		
		// PP se adesione ad accordo quadro non e' previsto il contributo (obino 24/03/2010)
		//3.04.8 34190 fix
		if(modoReal == Costanti.MODOREAL_ADESIONE || modoReal == Costanti.MODOREAL_ADESIONE_NOCOMPET || modoReal == Costanti.MODOREAL_CONCESSIONE || modoReal == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) {
			result [0] = BigDecimal.valueOf(0);
			result [1] = BigDecimal.valueOf(0);

			return result;
		}
				
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
	    	pstmt = conn.prepareStatement(selectImportiByImportoLotto);
	    	pstmt.setObject(1, importoLotto);
	    	pstmt.setObject(2, importoLotto);
	    	pstmt.setString(3, dataVal);

    	    rs = pstmt.executeQuery();
	 	 
	    	BigDecimal importoSA = null;
	    	BigDecimal importoImpresa = null;
		
	    	if ( rs.next() ) {
		    	result [0] = importoSA = (BigDecimal)rs.getObject(1);
		    	result [1] = importoImpresa = (BigDecimal)rs.getObject(2);
		    }
		}
		catch(SQLException e){
			throw e;
		}
		finally{
	    	close(rs,pstmt);	
		}
	    return result;
	}	

	/** verifica se l'ultima chiamata alla determinazionecontributo ha interessato un lotto esente
	 * @param importo del lotto passato alla chiamata per la determinazione del contributo
	 * @return
	 */
	public boolean isEsente(BigDecimal importo){
		boolean retVal = false;
		
		if(esitoRicerca != null && url != null){
			if(esitoRicerca.getMapImportContributo() != null){
				RicercaContributoToMapImportContributoEntry elem = esitoRicerca.getMapImportContributo()[0];
				//se la key della mappa ha un valore diverso dall'importo originario allora e esente
				// affermato da Dell'Amico il 19/07/2011
				if(elem.getKey().floatValue() != importo.floatValue())
					retVal = true;
			}
		}
		else{
			// chiamata locale
			retVal = localEsente;
		}
			
		
		return retVal;
	}

	/** veriica se l'ultima chiamata alla determinazionecontributo ha restituito errori
	 * @return messaggio di errore o null
	 */
//	private String getErrore(){
//		String retVal = null;
//		
//		AllValidationBeans errs =  (AllValidationBeans) esitoRicerca.getEsitoValidazione();
//		if(errs.){
//			retVal = "";
//		}
//		
//		return retVal;
//	}

	/*****************************************************************************************************
	 * Calcola l'importo della Gara a partire dalla somma degli importi dei Lotti attivi della Gara
	 * @param elencoLotti : Collection<Lotto>
	 *****************************************************************************************************/	
	public BigDecimal getImportoGara (Collection<Lotto> elencoLotti, boolean perContributo, boolean soloPubblicati) throws Exception { //perContributo = false soloPubblicati = False entra seconda volta con perContributo = true

	   BigDecimal totLotti = new BigDecimal(0.00);
	   BigDecimal totLottiContributoSA = new BigDecimal(0.00);
        
		//calcolo importo totale ai fini dell'importo gara
		if(!perContributo){
			for (Iterator iter = elencoLotti.iterator(); iter.hasNext();) {
				Lotto element = (Lotto) iter.next();
				
				if(!soloPubblicati || (soloPubblicati && element.getData_Pubblicazione() != null)){
      				if(element.getImporto_Lotto().floatValue() != Costanti.IMPORTO_FUORI_SCALA)
      					totLotti = totLotti.add(element.getImporto_Lotto());
      				else{
      					totLotti = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
      					break;
      				}
	            }
			}
			
    		// se l'importo gara e zero lo metto a -1 (indeterminato)
			//MAC #9786
    		//if(totLotti.intValue() == 0) totLotti = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
			if(totLotti.doubleValue() == 0.00) totLotti = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA); //totLotti = 50000

		}
		// calcolo ai fini del contributo, devo richiamare il ws di calcolo contributo per ogni lotto
		// per determinare se l'importo va considerato o meno nel totale
		else{
    		GaraManager gm = new GaraManager(conn, logger);
    		
		    Gara gara = null;
		    
    		for (Iterator iter = elencoLotti.iterator(); iter.hasNext();) {
				Lotto element = (Lotto) iter.next();
				
                if(!soloPubblicati || (soloPubblicati && element.getData_Pubblicazione() != null)){

   				// lettura dati gara, una sola volta
   				if(gara == null)
   					gara = gm.getGara(element.getId_Gara());
   				
   				boolean isRipetizione=Costanti.FLAG_VALORE_SI.equals(element.getFLAG_RIPETIZIONE()) || Costanti.COLL_CIG_RIP.equals(element.getID_MOTIVO_COLL_CIG());
   				
   					
   				// calcolo importo totale gara ai fini del contributo, solo lotti non esclusi
   				// devo richiamare il ws di calcolo per verificare eventuali esenzioni
   	    		ParametriContributo params = new ParametriContributo(gara, element, PageHelper.getCalendarFromStringDate(PageHelper.getFormattedNowOrInputFormattedDate(element.getData_Pubblicazione(), getNow())),conn,logger);
   	    		BigDecimal impImpresa = this.getContributoOE(params);
   	    		
   	    		// se ci sono stati errori durante il calcolo imposto come non esente
   	    		if(this.hasErrors()){
                      totLottiContributoSA = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA); // poi esce on il break seguente
                      impImpresa = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA); // contributo non calcolato
                      break;
   	    		}
   	    		else{
         				if(!this.isEsente(params.getImporto())){
         					if(element.getImporto_Lotto().floatValue() != Costanti.IMPORTO_FUORI_SCALA)
         						totLottiContributoSA = totLottiContributoSA.add(element.getImporto_Lotto());
         					else{
         						totLottiContributoSA = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA); // poi esce on il break seguente
         						break;
         					}
         				}
                   }
   	    		   // aggiorno il valore del contributo sul lotto
  	    		   element.setImporto_Impresa(isRipetizione ? new BigDecimal(0) : impImpresa);
                }
             }
    		
    		// patch per ws contributo, se l'importo gara e zero lo metto a 1 dell'amico 17/10/2011
    		if(totLottiContributoSA.intValue() == 0) totLottiContributoSA = new BigDecimal(1); // Costanti.IMPORTO_FUORI_SCALA;
		}

        return perContributo ? totLottiContributoSA : totLotti;
	}


    /**
    * Elimina i Lotti attivi della Gara che non sono validi ai fini del calcolo contributo
    * @param gara
    * @param elencoLotti
    * @return true se operazione conclusa con successo
    * @throws Exception
    */
//   private boolean pulisciLotti (Gara gara, Map<String, Lotto> elencoLotti) throws Exception {
//
//      boolean retVal = true;
//      
//      for (Iterator iter = elencoLotti.keySet().iterator(); iter.hasNext();) {
//         String key = (String) iter.next(); 
//         
//         Lotto element = (Lotto) elencoLotti.get(key);
//          
//         // devo richiamare il ws di calcolo per verificare eventuali esenzioni
//         ParametriContributo params = new ParametriContributo(gara, element, PageHelper.getCalendarFromStringDate(PageHelper.getFormattedNowOrInputFormattedDate(element.getData_Pubblicazione(), getNow())));
//         BigDecimal impImpresa = this.getContributoOE(params);
//          
//         // se non ci sono stati errori durante il calcolo ed e esente elimino il lotto dalla lista
//         if(!this.hasErrors()){
//            if(this.isEsente(params.getImporto())){
//               elencoLotti.remove(key);
//            }
//         }
//      }  
//      
//      return retVal;
//   }

   /**
   * Restituisce la minima data di pubblicazione dei lotti o la data corrente
   * @param elencoLotti
   * @return data
   * @throws Exception
   */
  public Calendar getDataPubb (Map<String, Lotto> elencoLotti) {

     Calendar retVal = PageHelper.getCalendarFromStringDate(PageHelper.getCurrentDate());
     
     for (Iterator<String> iter = elencoLotti.keySet().iterator(); iter.hasNext();) {
        String key = (String) iter.next(); 
        
        Lotto element = (Lotto) elencoLotti.get(key);
        
        if(element.getData_Pubblicazione() != null){
           Calendar data = PageHelper.getCalendarFromStringDate(element.getData_Pubblicazione());
        
           if(data.before(retVal))
              retVal = data;
        }
     }  
     
     return retVal;
  }
    public it.avlp.simog.beans.AllValidationBeans getErrors(){
		it.avlp.simog.beans.AllValidationBeans ret = new it.avlp.simog.beans.AllValidationBeans();
		
		if(esitoRicerca.getEsitoValidazione() != null){
/** PP propago solo i messaggi simog , altrimenti mando il generico
		   for (int i = 0; i < esitoRicerca.getEsitoValidazione().length; i++) {
				if(esitoRicerca.getEsitoValidazione(i).getSeverity().equals(SeverityLevel.ERROR))
					ret.addValidationWarn(Messaggi.SIMOG_CONTRIBUTO_001.replace("$1", esitoRicerca.getEsitoValidazione(i).getCampo()));
			}
**/
		   // log su debug
		   //logErrors();
		   
           for (int i = 0; i < esitoRicerca.getEsitoValidazione().length; i++) {
              if(esitoRicerca.getEsitoValidazione(i).getCode().contains("SIMOG")
              && esitoRicerca.getEsitoValidazione(i).getSeverity().equals(SeverityLevel.ERROR)){
                 ret.addValidationWarn(esitoRicerca.getEsitoValidazione(i).getCampo());
                 break;
              }
           }

           // se non ci sono errori simog ma di altro tipo ritorno il generico con il primo messaggio restituito
           if(ret.getSize() == 0){
              for (int i = 0; i < esitoRicerca.getEsitoValidazione().length; i++) {
                 if(esitoRicerca.getEsitoValidazione(i).getSeverity().equals(SeverityLevel.ERROR)){
                    ret.addValidationWarn(Messaggi.SIMOG_CONTRIBUTO_001.replace("$1", esitoRicerca.getEsitoValidazione(i).getCampo()));
                    break;
                 }              
              }		
           }        
		}
        return ret;
	}
	
	/** ricalcola il contributo per i lotti e la gara
	 * @param gara
	 * @param lotti
	 * @return true se non ci sono stati errori
	 * @throws Exception 
	 */
	public boolean ricalcola (Gara gara, Map<String, Lotto> lotti) throws Exception{
	   
	   boolean retVal = false;
       BigDecimal impGara = new BigDecimal(0);
     //MAC 36255 3.04.8 aggiunto motivoEsclusioneGara
       String motivoEsclusioneGara = null;
	         
       Collection<Lotto> lottiPerf = lotti.values();
       BigDecimal importo = new BigDecimal(0);
       
       // ricalcolo dei lotti perfezionati
       boolean isRipetizione,hasRipetizione=false;
       for(Lotto l:lottiPerf){
          if(l.getData_Pubblicazione() != null){
        	  isRipetizione = Costanti.FLAG_VALORE_SI.equals(l.getFLAG_RIPETIZIONE()) || Costanti.COLL_CIG_RIP.equals(l.getID_MOTIVO_COLL_CIG());
              hasRipetizione = hasRipetizione || isRipetizione;
        	  
             ParametriContributo parLotto = 
                   new ParametriContributo(gara, l, 
                         PageHelper.getCalendarFromStringDate(l.getData_Pubblicazione()),conn,logger);
               
             BigDecimal impOE = !isRipetizione ? this.getContributoOE(parLotto) : new BigDecimal(0);
             if(!this.hasErrors() || isRipetizione)
                importo = impOE;
             else 
                importo = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
               
             l.setImporto_Impresa(importo);
             l.setNoCalcolo(this.isEsente(l.getImporto_Lotto()));
             if(l.isNoCalcolo() == false)
                impGara = impGara.add(l.getImporto_Lotto());
          }
        //MAC 36255 3.04.8
	         //se la gara e singolo lotto salvo in una variabile la modalita esclusione da passare a getContributoSA
	         if (lottiPerf.size()==1) {
	        	 motivoEsclusioneGara = String.valueOf(l.getID_ESCLUSIONE()).trim();
			}
       }
        
       Calendar dataRif = getDataPubb(lotti);
           
     //MAC 36255 3.04.8 aggiunto motivoEsclusioneGara
       ParametriContributo parGara = new ParametriContributo(gara, null, motivoEsclusioneGara, dataRif);
       parGara.setImporto(impGara);
       // patch per ws contributo, se l'importo gara e zero lo metto a 1 dell'amico 17/10/2011
       if(impGara.floatValue() == 0) parGara.setImporto(new BigDecimal(1));
       
       importo = new BigDecimal(0);
     //if lotti da perfez sono maggiore di 1 non cambia, se 1 prendere 36255 3.04.8
       BigDecimal impSA = !hasRipetizione ? this.getContributoSA(parGara) : new BigDecimal(0);
       if(!this.hasErrors() || hasRipetizione)
          importo = impSA;
       else 
          importo = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);

       gara.setIMPORTO_SA_GARA(importo);
       gara.setIMPORTO_GARA(this.getImportoGara(lotti.values(), false, false));
       retVal = true;
       
	   return retVal;
	}
	
	public boolean hasErrors(){
		return this.getErrors().getAll().size()> 0;
	}

    private void logErrors(){
       if (!hasErrors()) return;
       
       for (int i = 0; i < esitoRicerca.getEsitoValidazione().length; i++) {
          if(esitoRicerca.getEsitoValidazione(i).getSeverity().equals(SeverityLevel.ERROR))
              logger.error (esitoRicerca.getEsitoValidazione(i).getCampo());
       }   
    }

    boolean ping (String host){
	   boolean reachable = false;
	   try  {
	      InetAddress address = InetAddress.getByName(host);
	      reachable = address.isReachable(10000);
       } 
	   catch (Exception e) {}
	   
	   return reachable;
	}
}
