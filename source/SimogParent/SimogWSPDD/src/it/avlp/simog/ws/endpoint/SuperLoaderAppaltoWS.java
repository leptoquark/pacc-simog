package it.avlp.simog.ws.endpoint;

import it.avlp.simog.beans.CodiciCup;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.common.util.General;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.massload.MassLoader;
import it.avlp.simog.massload.MassLoaderProperties;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.massload.parser.XmlParser;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.xmlbeans.CUPLOTTOType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.beans.ResponseLoaderAppalto;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;
import it.avlp.simog.ws.massload.xmlbeans.AnomaliaType;
import it.avlp.simog.ws.massload.xmlbeans.CigType;
import it.avlp.simog.ws.massload.xmlbeans.CuiType;
import it.avlp.simog.ws.massload.xmlbeans.DbDateType;
import it.avlp.simog.ws.massload.xmlbeans.FeedBack;
import it.avlp.simog.ws.massload.xmlbeans.FeedBack.AnomalieSchede;
import it.avlp.simog.ws.massload.xmlbeans.CupType;
import it.avlp.simog.ws.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.ws.massload.xmlbeans.FlagSNType;
import it.avlp.simog.ws.massload.xmlbeans.FlussoType;
import it.avlp.simog.ws.massload.xmlbeans.IdSchedaType;
import it.avlp.simog.ws.massload.xmlbeans.InteroType;
import it.avlp.simog.ws.massload.xmlbeans.LivelloType;
import it.avlp.simog.ws.massload.xmlbeans.NomeCampoType;
import it.avlp.simog.ws.massload.xmlbeans.RecIdSchedaInsType;
import it.avlp.simog.ws.massload.xmlbeans.TipiOperazioneType;
import it.avlp.simog.ws.massload.xmlbeans.TipiSchedeType;
import it.avlp.simog.ws.massload.xmlbeans.TrasferimentoDati;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

public class SuperLoaderAppaltoWS {

   private String nomePathConf = "/opt/SIMOG/LoaderAppaltoWS/";
	//private String nomePathConf = "C:\\ANAC\\software\\eclipse-workspace_ANAC\\3.04.7\\Configurazioni\\LoaderAppaltoWS";
   private String SimogConfigName = "massloader.properties";
   protected MassLoaderProperties configuration = null;
   protected Logger logger = null;
   private DbManager dbm;
   private String cig = null;
   protected MassLoader massl;

   // is3028_RFWEBSC00Active
   private OrigineSchedaEnum origine;
   
   public SuperLoaderAppaltoWS(OrigineSchedaEnum origine) {
      super();
      massl = new MassLoader(origine);
      try {
         massl.init(nomePathConf, SimogConfigName);
         configuration = massl.getConfiguration();
         this.logger = massl.getLogger();
         this.origine = origine;
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * Rappresenta l'operation del WS 'LoaderAppalto'. Il metodo si occupa del caricamento dei dati dell'Appalto attraverso le segg. fasi:
    * 		<ul><li>	1) Inizializzazione dei parametri di configurazione;</li>
    * 		<li>		2) Marshalling dei dati di input;</li>
    * 		<li>		3) validazione dell' Xml ricavato dai dati di input;</li>
    * 		<li>		4) invocazione del Mass Loader per il caricamento dei dati;</li>
    * 		<li>		5) restituzione di un Feedback che viene incapsulato nella risposta dell'operation suddetta.</li></ul>
    *  
    * @param ticket
    * @param indexCollaborazione
    * @param trasferimentoDati
    * @param origine 
    * 
    * @return ResponseLoaderAppalto
    */
   public ResponseLoaderAppalto loaderAppalto(String ticket, String indexCollaborazione, TrasferimentoDati trasferimentoDati) {
   
   	   XmlParser xmlParser = null;
   	   FeedBackDocument feedDoc = null;
   	   ResponseLoaderAppalto ris = null;
          try {
            feedDoc = FeedBackDocument.Factory.newInstance();
            ris = new ResponseLoaderAppalto();
            TicketManager tm = new TicketManager();
            WsSessions wss = new WsSessions();
          
            xmlParser = new XmlParser(logger, null);
   
            String trasferimentoDatiXml = null;
   
            FeedBackDocument out = FeedBackDocument.Factory.newInstance();
   
   
//            per loaderAppalto, se il ticket null restituisco un feedback fittizio con la versione
            if(ticket == null){
               FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(logger, "NOUSER");
               feedDoc = xmlParser.getFeedbackXml();
               feedBackWriter.writeUnandledException(feedDoc, "",massl.MASSLOADER_VERSION, "NOUSER");
               
               ris.setFeedBack(this.fromFeedBackDocumentToFeedBackXml(feedDoc));   
               return ris;
            }
            
            // controllo parametri di input   
   		 if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false) 
   		       && trasferimentoDati != null) {
   		    
   		    // controllo sessione
               String ret = checkAbilitazione(ticket.trim(), indexCollaborazione, wss, tm);
                  
               if(ret != null){
                  FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(logger, "NOUSER");
                  ValidationBean validation = new SchedaSpecificaValidationBean(
                        ret, ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0, 
                        TipiSchedeType.DATI_COMUNI.toString(), null, null);
   
                  List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
                  validazioni.add(validation);
                  feedBackWriter.fillStandardFeedBack(xmlParser.getFeedbackXml().getFeedBack(), validazioni, TipiOperazioneType.INSERIMENTO.toString(), TipiSchedeType.DATI_COMUNI.toString(), cig, null, 0, null, null);
                  feedDoc = xmlParser.getFeedbackXml();
               }
               else{
                  // sessione valida
                  FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(logger, wss.getUserId());
                  trasferimentoDatiXml = this.getTrasferimentoDatiFromLoaderAppaltoInput(trasferimentoDati);
                  xmlParser.xsdValidate(trasferimentoDatiXml, true);
                  feedDoc = this.validateXmlAndCallMassLoader(xmlParser, trasferimentoDatiXml, out, feedDoc, feedBackWriter, wss.getUserId(), tm);
               }
   		}
   		else{
   		   // parametri non corretti
              FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(logger, "NOUSER");
              ValidationBean validation = new SchedaSpecificaValidationBean(
                    Messaggi.LOADER_APPALTO_003, ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0, 
                    TipiSchedeType.DATI_COMUNI.toString(), null, null);
   
              List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
              validazioni.add(validation);
              feedBackWriter.fillStandardFeedBack(xmlParser.getFeedbackXml().getFeedBack(), validazioni, TipiOperazioneType.INSERIMENTO.toString(), TipiSchedeType.DATI_COMUNI.toString(), cig, null, 0, null, null);
              feedDoc = xmlParser.getFeedbackXml();
   		}
   
           } catch (Throwable t) {
              logger.fatal("Eccezione in Main: " + t.getMessage());
              t.printStackTrace();
              FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(logger, "NOUSER");
              feedDoc = xmlParser.getFeedbackXml();
              feedBackWriter.writeUnandledException(feedDoc, indexCollaborazione, t.getMessage(), "NOUSER");
   
   //           ValidationBean validation =
   //                 new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_205 + " : " + t.getMessage(), ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0, TipiSchedeType.DATI_COMUNI.toString(), null, null);
   //
   //           List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
   //           validazioni.add(validation);
   //           feedBackWriter.fillStandardFeedBack(xmlParser.getFeedbackXml().getFeedBack(), validazioni, TipiOperazioneType.INSERIMENTO.toString(), TipiSchedeType.DATI_COMUNI.toString(), cig, null, 0, null, null);
   //           feedDoc = xmlParser.getFeedbackXml();
          } 
   
           ris.setFeedBack(this.fromFeedBackDocumentToFeedBackXml(feedDoc));   
   		return ris;
   	}

   /**
    * Restituisce una stringa in formato Xml che rappresenta i dati di input della richiesta.
    * 
    * @param trasferimentoDatiInput
    * @return String
    */
   private String getTrasferimentoDatiFromLoaderAppaltoInput(TrasferimentoDati trasferimentoDatiInput) {
   
   	    OutputStream os = null;
   
   		String ris = null;
   		//tdof = new TrasferimentoDatiObjectFactory();
   
   		try {
   		   
   		   JAXBContext jaxbContext = JAXBContext.newInstance(TrasferimentoDati.class);
   		   Marshaller m = jaxbContext.createMarshaller();
         
   //			JAXBElement<TrasferimentoDati> gt = tdof.createTrasferimentoDati(trasferimentoDatiInput);
   //			JAXBContext jaxbContext = JAXBContext.newInstance(TrasferimentoDati.class);
   //			Marshaller m = jaxbContext.createMarshaller();
   			os = new ByteArrayOutputStream();
   			m.marshal(trasferimentoDatiInput, os);
   		} catch (JAXBException e) {
   			e.printStackTrace();
   		}
   
   		int index = os.toString().indexOf("?", 2) + 2;
   		ris = os.toString().substring(index);
   //		String declarations = " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xsi:schemaLocation=\"xmlbeans.massload.simog.avlp.it GaraWS.xsd\"";
   //		ris = ris.substring(0, 55) + declarations + ris.substring(55);
   
   		return ris;
   	}

   /**
    * Effettua i controlli opportuni per validare l' Xml che rappresenta l'input della richiesta, e successivamente, se tali controlli
    * vanno a buon fine, invoca il MassLoader per il caricamento dei dati e la restituzione di un Feedback.
    * 
    * @param xmlParser
    * @param trasferimentoDati
    * @param out
    * @param feedDoc
    * @param feedBackWriter
    * @throws Exception 
    */
   private FeedBackDocument validateXmlAndCallMassLoader(XmlParser xmlParser, String trasferimentoDati,
         FeedBackDocument out, FeedBackDocument feedDoc, FeedBackWriterValidationsBeans feedBackWriter, String user, TicketManager tm)
         throws Exception {
         
      cig = "0000000000";
               
      // non ci sono errori, continuo
      if (xmlParser.getErrors().size() == 0) {
         it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati obj = xmlParser.getDoc().getTrasferimentoDati();
         
         if (obj.getSchedeArray() != null
               && obj.getSchedeArray().length > 0) {
            cig = obj.getSchedeArray()[0].getDatiComuni().getCIG();

         } // caso solo eliminazione schede
         else if (obj.getSchedeEliminateArray() != null
               && obj.getSchedeEliminateArray().length > 0) {
            cig = obj.getSchedeEliminateArray()[0].getCIG();
         }
         else if (obj.getVariazioniAnagArray() != null 
               && obj.getVariazioniAnagArray().length > 0) {
            cig = obj.getVariazioniAnagArray()[0]
                  .getVariazioneAnagArray()[0].getRiferimento()
                  .getCIG();
         }
// non so se va bene!         
//         else if (obj.getVariazioniSA() != null
//               && obj.getVariazioniSA().getVariazioneSAArray().length > 0){
//            cig = String.valueOf(obj.getVariazioniSA().getVariazioneSAArray(0).getIDGARA());
//         }
         
         boolean ok = true;
         
         int contaSez = 0;
         int schedetot = 0;
         boolean schede_pres = false;
         
         contaSez += obj.getSchedeArray() != null && obj.getSchedeArray().length > 0 ? 1 : 0 ;
         contaSez += obj.getSchedeEliminateArray() != null && obj.getSchedeEliminateArray().length > 0 ? 1 : 0 ;
         contaSez += obj.getVariazioniAnagArray() != null && obj.getVariazioniAnagArray().length > 0 ? 1 : 0 ;
         contaSez += obj.getVariazioniSA() != null 
               && obj.getVariazioniSA().getVariazioneSAArray() != null 
               && obj.getVariazioniSA().getVariazioneSAArray().length > 0 ? 1 : 0 ;

//         ci sono piu sezioni
         if(contaSez > 1) {
            ok = false;
            
            ValidationBean validation = new SchedaSpecificaValidationBean(
                  Messaggi.LOADER_APPALTO_001,
                  ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0,
                  TipiSchedeType.DATI_COMUNI.toString(), null, null);

            List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
            validazioni.add(validation);

            feedBackWriter.fillStandardFeedBack(xmlParser
                  .getFeedbackXml().getFeedBack(), validazioni,
                  TipiOperazioneType.INSERIMENTO.toString(),
                  TipiSchedeType.DATI_COMUNI.toString(), cig, null, 0,
                  null, null);
            feedDoc = xmlParser.getFeedbackXml();

         }

         // nessuna operazione richiesta
         if(contaSez == 0) {
            ok = false;
            
            ValidationBean validation = new SchedaSpecificaValidationBean(
                  Messaggi.SIMOG_MASSLOADER_213,
                  ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0,
                  TipiSchedeType.DATI_COMUNI.toString(), null, null);

            List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
            validazioni.add(validation);

            feedBackWriter.fillStandardFeedBack(xmlParser
                  .getFeedbackXml().getFeedBack(), validazioni,
                  TipiOperazioneType.INSERIMENTO.toString(),
                  TipiSchedeType.DATI_COMUNI.toString(), cig, null, 0,
                  null, null);
            feedDoc = xmlParser.getFeedbackXml();
         }

         if(ok){
            // l'unica sezione presente deve avere una sola istanza
            contaSez = 0;
            contaSez += obj.sizeOfSchedeArray();
            contaSez += obj.sizeOfSchedeEliminateArray();
            contaSez += obj.sizeOfVariazioniAnagArray();
            contaSez += obj.isSetVariazioniSA() ? obj.getVariazioniSA().sizeOfVariazioneSAArray() : 0;
            
            if(contaSez > 1) {
               ok = false;
               
               ValidationBean validation = new SchedaSpecificaValidationBean(
                     Messaggi.LOADER_APPALTO_001,
                     ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0,
                     TipiSchedeType.DATI_COMUNI.toString(), null, null);

               List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
               validazioni.add(validation);

               feedBackWriter.fillStandardFeedBack(xmlParser
                     .getFeedbackXml().getFeedBack(), validazioni,
                     TipiOperazioneType.INSERIMENTO.toString(),
                     TipiSchedeType.DATI_COMUNI.toString(), cig, null, 0,
                     null, null);
               feedDoc = xmlParser.getFeedbackXml();
            }
         }
         
         if(ok){
            // ci sono schede da inserire/modificare
            schede_pres = obj.getSchedeArray().length == 1;
            
            if (schede_pres && obj.getSchedeArray()[0].getSchedaCompletaArray()!= null)
               schede_pres = schede_pres && obj.getSchedeArray()[0].getSchedaCompletaArray().length == 1;
            
            // ammetto solo una istanza di ogni singola scheda
            if(schede_pres){
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getAggiudicazione() != null)
                  schedetot++;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getEscluso() != null)
                  schedetot++;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getSottosoglia() != null)
                  schedetot++;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getAdesione() != null)
                  schedetot++;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiCollaudo() != null)
                  schedetot++;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiConclusione() != null)
                  schedetot++;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiInizio() != null)
                  schedetot++;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiStipula() != null)
                  schedetot++;

               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiAvanzamenti() != null)
                  schedetot += obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiAvanzamenti().getAvanzamentoArray().length;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiAccordi() != null)
                  schedetot += obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiAccordi().getAccordoBonarioArray().length;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiRitardi() != null)
                  schedetot += obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiRitardi().getRitardoArray().length;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiSospensioni() != null)
                  schedetot += obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiSospensioni().getSospensioneArray().length;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiSubappalti() != null)
                  schedetot += obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiSubappalti().getSubappaltoArray().length;
               if (obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiVarianti() != null)
                  schedetot += obj.getSchedeArray()[0].getSchedaCompletaArray()[0].getDatiVarianti().getVarianteArray().length;
               
               ok = schedetot == 1;
               
               if(!ok){
                  ValidationBean validation = new SchedaSpecificaValidationBean(
                        Messaggi.LOADER_APPALTO_001, ValidationBean.VALBEAN_SEV_ERR,
                        0, 0, 0, TipiSchedeType.DATI_COMUNI.toString(), null, null);

                  List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
                  validazioni.add(validation);

                  feedBackWriter.fillStandardFeedBack(xmlParser.getFeedbackXml()
                        .getFeedBack(), validazioni, TipiOperazioneType.INSERIMENTO
                        .toString(), TipiSchedeType.DATI_COMUNI.toString(), cig,
                        null, 0, null, null);
                  feedDoc = xmlParser.getFeedbackXml();
               }
            }

            // OK posso elaborare se esiste una sola scheda
            if (ok) {
               // L'ELABORAZIONE PROSEGUE IN QUANTO E' STATA INVIATA UNA SOLA SCHEDA
               try {
                  logger.info("Apro la connessione al db, poiche' la struttura e' valida");
                  dbm = new DbManager(logger, configuration);
                  logger.info("Ottenuta connection: " + dbm.getCurrentActiveConnection().toString());
               } catch (Exception e1) {
                  logger.fatal(e1);
                  throw e1;
               }
               Collaborazione coll = new Collaborazione();
               if (!tm.isOperaComeOsservatorio())
                  coll = tm.getCollaborazione();

               GaraManager garaManager = new GaraManager(dbm.getCurrentActiveConnection(), logger);

               TableBean tableBean = null;
               Hashtable listaSA = new Hashtable();
               tableBean = garaManager.getGaraList("", "", cig, "",
                     listaSA, null, null, null, null, 0, 10, false,
                     null, tm.getAdminOr(), null, "", null, null, "",
                     null);

               String cfAmministrazione = "";
               String cfAmmDatiComuni = "";
               String idOsservatorio = "";
               String cfRup = "";
               String cfRupInfo = "";
               if (tableBean != null && !tableBean.isEmpty()) {
                  cfAmministrazione = tableBean.getNulledField(GARA.CF_AMMINISTRAZIONE, 0);
                  cfAmmDatiComuni = tableBean.getNulledField(INFO_AGGIUDICAZIONI.CF_AMM, 0);
                  idOsservatorio = tableBean.getNulledField(GARA.ID_OSSERVATORIO, 0);
                  cfRup = tableBean.getNulledField(GARA.CF_UTENTE, 0);
                  cfRupInfo = tableBean.getNulledField(INFO_AGGIUDICAZIONI.CF_RUP, 0);
//                   if(cfRupInfo != null && !cfRupInfo.equals(""))
//                	  cfRup = cfRupInfo;

                  //TICKET ALM 13444 - 3.04.4.1
                  //L'utente ha profilo osservatorio e ha competenza sulla gara
                  boolean abilitato = (tm.isOperaComeOsservatorio() && tm.getAdminOr().equals(idOsservatorio))
                        || (!tm.isOperaComeOsservatorio() //L'utente e' RUP e il suo CF e la sua SA sono quelle della gara
                        	 && (cfAmministrazione.equals(coll.getAzienda_codiceFiscale()) || coll.getAzienda_codiceFiscale().equals(cfAmmDatiComuni))
                        	 && (cfRup.equalsIgnoreCase(user) || user.equalsIgnoreCase(cfRupInfo))) //MAC 34373 - controllo no case sensitive
     						|| (!tm.isOperaComeOsservatorio() && coll != null && coll.getUfficio_profilo().equals(ProfiloEnum.RPNT.codice()));
                 // boolean abilitato = true;
//                  abilitato = false;
                  if (!abilitato) {
                     /*
                      * L'UTENTE NON E' AUTORIZZATO AD AGIRE SULL'APPALTO
                      */
                     logger.debug("errore?" + tm.getAdminOr());
                     ValidationBean validation = new SchedaSpecificaValidationBean(
                           Messaggi.LOADER_APPALTO_002,
                           ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0,
                           TipiSchedeType.DATI_COMUNI.toString(), null,
                           null);

                     List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
                     validazioni.add(validation);

                     feedBackWriter.fillStandardFeedBack(xmlParser
                           .getFeedbackXml().getFeedBack(), validazioni,
                           TipiOperazioneType.INSERIMENTO.toString(),
                           TipiSchedeType.DATI_COMUNI.toString(), cig,
                           null, 0, null, null);
                     feedDoc = xmlParser.getFeedbackXml();
                  } 
                  else {
                     /*
                      * L'ELABORAZIONE PROSEGUE IN QUANTO L'UTENTE E'
                      * AUTORIZZATO AD AGIRE SULL'APPALTO
                      */
                     FeedBackDocument outNew = FeedBackDocument.Factory
                           .newInstance();
                     String ritorno = new MassLoader(this.origine).mainStream(user,
                           nomePathConf, SimogConfigName,
                           trasferimentoDati, out, outNew);
                     feedDoc = outNew;
                     logger.debug("dopo elaborazione" + ritorno);
                     logger.debug("dopo elaborazione out" + out);
                     logger.debug("dopo elaborazione outNew" + feedDoc);
                  }
               } 
               else {
                  ValidationBean validation = new SchedaSpecificaValidationBean(
                        Messaggi.SIMOG_VALIDAZIONE_008,
                        ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0,
                        TipiSchedeType.DATI_COMUNI.toString(), null, null);

                  List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
                  validazioni.add(validation);
                  feedBackWriter.fillStandardFeedBack(xmlParser
                        .getFeedbackXml().getFeedBack(), validazioni,
                        TipiOperazioneType.INSERIMENTO.toString(),
                        TipiSchedeType.DATI_COMUNI.toString(), cig, null,
                        0, null, null);
                  feedDoc = xmlParser.getFeedbackXml();
               }
            } 
         }          
      }
      else {
         // errori di parse
         feedDoc = xmlParser.getFeedbackXml();
      }
      
      if (dbm != null && dbm.getCurrentActiveConnection() != null) {
         logger.info("Closing connection: "
               + dbm.getCurrentActiveConnection().toString());
         dbm.closeConnection();
      }
      if (feedDoc == FeedBackDocument.Factory.newInstance())
         feedDoc = xmlParser.getFeedbackXml();
   
      return feedDoc;
   }

   /**
    * Converte un oggetto di tipo FeedBackDocument in un oggetto di tipo FeedBack che rappresenta un Xml Bean.
    * @param feedDoc
    * @return FeedBack
    */
   private FeedBack fromFeedBackDocumentToFeedBackXml(FeedBackDocument feedDoc) {
   
   	FeedBack ris = new FeedBack();
   	logger.debug("feedback:" + feedDoc.getFeedBack());
   
   	if (feedDoc.getFeedBack().getInfoFlusso() != null) {
   		FlussoType flussoType = new FlussoType();
   		if (feedDoc.getFeedBack().getInfoFlusso().getDATAELABORAZIONE() != null)
   			flussoType.setDATAELABORAZIONE(buildDbDateType(feedDoc.getFeedBack().getInfoFlusso().getDATAELABORAZIONE().getTime()));
   		if (new Integer(feedDoc.getFeedBack().getInfoFlusso().getNUMCARICATE()) != null)
   			flussoType.setNUMCARICATE(this.buildInteroType(feedDoc.getFeedBack().getInfoFlusso().getNUMCARICATE()));
   		if (new Integer(feedDoc.getFeedBack().getInfoFlusso().getNUMELABORATE()) != null)
   			flussoType.setNUMELABORATE(this.buildInteroType(feedDoc.getFeedBack().getInfoFlusso().getNUMELABORATE()));
   		if (new Integer(feedDoc.getFeedBack().getInfoFlusso().getNUMERRORE()) != null)
   			flussoType.setNUMERRORE(this.buildInteroType(feedDoc.getFeedBack().getInfoFlusso().getNUMERRORE()));
   		if (new Integer(feedDoc.getFeedBack().getInfoFlusso().getNUMWARNING()) != null)
   			flussoType.setNUMWARNING(this.buildInteroType(feedDoc.getFeedBack().getInfoFlusso().getNUMWARNING()));
   		ris.setInfoFlusso(flussoType);
   	}
   
   	if (feedDoc.getFeedBack().getAnomalieSchedeArray() != null) {
   		List<AnomalieSchede> anomalieSchede = ris.getAnomalieSchede();
   
   		for (int i = 0; i < feedDoc.getFeedBack().getAnomalieSchedeArray().length; i++) {
   			AnomalieSchede anomalia = new AnomalieSchede();
   
   			if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getCIG() != null)
   				anomalia.setCIG(this.buildCigType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getCIG()));
   			if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getCUI() != null)
   				anomalia.setCUI(this.buildCuiType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getCUI()));
   			if (new Integer(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getPROGRESSIVO()) != null)
   				anomalia.setPROGRESSIVO(this.buildInteroType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getPROGRESSIVO()));
   
   			if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray() != null) {
   				List<AnomaliaType> anomaliaTypes = anomalia.getAnomalia();
   				logger.debug("lunghezza array" + feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray().length);
   
   				for (int k = 0; k < feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray().length; k++) {
   					AnomaliaType anomaliaType = new AnomaliaType();
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getCAMPOXML() != null)
   						anomaliaType.setCAMPOXML(this.buildNomeCampoType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getCAMPOXML()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getCODICE() != null)
   						anomaliaType.setCODICE(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getCODICE());
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getDESCRIZIONE() != null)
   						anomaliaType.setDESCRIZIONE(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getDESCRIZIONE());
   					if (new Integer(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getELEMENTO()) != null)
   						anomaliaType.setELEMENTO(this.buildInteroType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getELEMENTO()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getIDSCHEDALOCALE() != null)
   						anomaliaType.setIDSCHEDALOCALE(this.buildIdSchedaType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getIDSCHEDALOCALE()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getIDSCHEDASIMOG() != null)
   						anomaliaType.setIDSCHEDASIMOG(this.buildIdSchedaType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getIDSCHEDASIMOG()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getLIVELLO() != null)
   						anomaliaType.setLIVELLO(LivelloType.fromValue(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getLIVELLO().toString()));
   					if (new Integer(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getPROGRESSIVO()) != null)
   						anomaliaType.setPROGRESSIVO(this.buildInteroType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getPROGRESSIVO()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getSCHEDA() != null)
   						anomaliaType.setSCHEDA(TipiSchedeType.fromValue(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getAnomaliaArray(k).getSCHEDA().toString()));
   					      
   					anomaliaTypes.add(anomaliaType);
   				}
   			}
   
   			if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray() != null) {
   				List<RecIdSchedaInsType> recIdSchedaInsTypes = anomalia.getIdScheda();
   				logger.debug("lunghezza array" + feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray().length);
   
   				for (int k = 0; k < feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray().length; k++) {
   					RecIdSchedaInsType recIdSchedaInsType = new RecIdSchedaInsType();
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getIDSCHEDALOCALE() != null)
   						recIdSchedaInsType.setIDSCHEDALOCALE(this.buildIdSchedaType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getIDSCHEDALOCALE()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getIDSCHEDASIMOG() != null)
   						recIdSchedaInsType.setIDSCHEDASIMOG(this.buildIdSchedaType(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getIDSCHEDASIMOG()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getOPERAZIONE() != null)
   						recIdSchedaInsType.setOPERAZIONE(TipiOperazioneType.fromValue(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getOPERAZIONE().toString()));
   					if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getSCHEDA() != null)
   						recIdSchedaInsType.setSCHEDA(TipiSchedeType.fromValue(feedDoc.getFeedBack().getAnomalieSchedeArray(i).getIdSchedaArray(k).getSCHEDA().toString()));
   					recIdSchedaInsTypes.add(recIdSchedaInsType);
   				}
   			}
   			
            // aggiungo la sezione CUPLOTTO
            if(SimogFlags.is3031_RFWEBGL02Active()
                  && SimogProperties.getInstance().isCUPAttivo()
                  ){
               if (feedDoc.getFeedBack().getAnomalieSchedeArray(i).isSetCUPLOTTO()){
                  CUPLOTTOType cupl = feedDoc.getFeedBack().getAnomalieSchedeArray(i).getCUPLOTTO();
                  
                  it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType cupOut = new it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType();
                  
                  CigType cigt = new CigType();
                  cigt.setValue(cupl.getCIG());
                  cupOut.setCIG(cigt);
                  
                  for(it.avlp.simog.massload.xmlbeans.DatiCUPType item : cupl.getCODICICUPArray()){
                     DatiCUPType dati = new DatiCUPType();
                     CupType tcup = new CupType();
                     tcup.setValue(item.getCUP());
                     dati.setCUP(tcup);
                     dati.setDATIDIPE(item.getDATIDIPE());
                     dati.setOKUTENTE(FlagSNType.fromValue(item.getOKUTENTE()== null ? Costanti.FLAG_VALORE_NO : item.getOKUTENTE().toString()));
                     dati.setVALIDO(FlagSNType.fromValue(item.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item.getVALIDO().toString()));
                     cupOut.getCODICICUP().add(dati );
                  }
                                    
                  anomalia.setCUPLOTTO(cupOut);
               }
            }

   			anomalieSchede.add(anomalia);
   		}
   	}
   
   	return ris;
   }

   private String checkAbilitazione(String ticket, String indexCollaborazione, WsSessions wss,
         TicketManager tm) {
         
             ConnectionWSManager cwsm = null;
             Connection con = null;
             
             String retVal = null;
         
             if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
                 indexCollaborazione = "-1";
             }else{
                 indexCollaborazione = indexCollaborazione.trim();
             }
             try{
                 //WsSessions wss = new WsSessions();
                 cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
                 WSSessionManager wsm = new WSSessionManager(logger,cwsm);
                 con = cwsm.getConnection();
                 //TicketManager tm = new TicketManager();
                 SqlTools sqlt = new SqlTools();
                 //-------    begin operations       --------//  
                 cwsm.setAutocommit(false);
                 wss.setTicket(ticket);
                 wss.setComando("LoaderAppaltoWS");
                 wss.setCollaborazione(Integer.parseInt(indexCollaborazione));       
                 wss = wsm.selectFindValidSession(wss);
                 cwsm.commit();
                 if(wss != null){
                     logger.info(">>>>esiste una sessione associata al ticket");
                     try{
                         boolean esito;
                         tm.validateRequestedActionByProfile(wss,TicketManager.MASSLOADER_WS);
                         if(tm.isValido()){
                             logger.info(">>>>utente abilitato al comando richiesto");
                             //cwsm.setIsolation("t_serialize");
                             logger.info(">>>>generazione cig e valorizzazione del bean nella response (connnessione settata a transaction serialized)");
                             //la validazione viene fatta al caricamento, se non passa la validazione passa direttamente al catch
                             GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);
                             try{
                                 Collaborazione col = null;
                                 CollaborazioniRssa collsRssa = null;
                                 Collaborazioni colls = null;
                                 if(!tm.isOperaComeOsservatorio()){      
                                     col = tm.getCollaborazione();
                                     colls = tm.getCollaborazioni();
                                     collsRssa = new CollaborazioniRssa(colls,col);
                                 }
                                 
                             }catch(SimogWSException swe){
                                 //validazione stringa xml fallita
                                 wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
                                 String messaggioErrore = swe.getMyMessage();
                                 //setto l'errore nell'oggetto di risposta
                                 logger.error("SimogWSException catched: "+messaggioErrore);
                                 //setto l'errore da scrivere nel db
                                 wss.setLastError(messaggioErrore);              
                                 wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
                                 if(wsm.updateSessionAfterOp(wss)){
                                     logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
                                     //setto l'errore nell'oggetto di risposta
                                     cwsm.commit();
                                 }else{
                                     logger.debug("aggiornamento sessione fallito");
                                     retVal = messaggioErrore;
                                 }
                                 return retVal;
         
                             }
                             wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
                             //cwsm.setIsolation("t_read_committed");
                             if(wsm.updateSessionAfterOp(wss)){
                                 cwsm.commit();              
                                 logger.info(">>>>aggiornamento dello stato della sessione riuscito");
                             }
                         }else{
                             logger.info("fallimento della validazione del ticket associazione comando - profilo non autorizzata");
                             String messaggioErrore = "collaborazione ["+wss.getCollaborazione()+"] non abilitata al comando ["+wss.getComando()+"] richiesto";
                             wss.setLastError(messaggioErrore);              
                             wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
                             if(wsm.updateSessionAfterOp(wss)){
                                 cwsm.commit();
                                 logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
                             }           
                             retVal = messaggioErrore;
                         }
                     //caso in cui l'indice passato non sia valido
                     }catch(SimogWSException swe){
                         logger.error("indice collaborazione non valido");
                         String messaggioErrore = swe.getMyMessage();
                         wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");                
                         wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
                         retVal = messaggioErrore;
                         if(wsm.updateSessionAfterOp(wss)){
                             logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
                             cwsm.commit();
                         }
                         return retVal;
                     }
                 }      
                 
                 return retVal;
             }catch(SimogWSException swe){
                 if(cwsm != null){
                     cwsm.rollback();
                 }
                 // setto l'errore nell'oggetto di risposta
                 String messaggioErrore = swe.getMyMessage();
                 retVal = messaggioErrore;
                 logger.error("SimogWSException catched: "+messaggioErrore);
                 
                 return retVal;
                 
             }catch(Throwable t){
                 t.printStackTrace();
                 retVal = t.getMessage();
                 return retVal;
             }
             finally{
                 if(cwsm != null){
                     cwsm.closeConnection();
                 }
             }
         }

   private InteroType buildInteroType(int value) {
   	InteroType ris = new InteroType();
   	ris.setValue(value);
   	return ris;
   }

   private DbDateType buildDbDateType(Date value) {
   	DbDateType ris = new DbDateType();
   	ris.setValue(value);
   	return ris;
   }

   private CigType buildCigType(String value) {
   	CigType ris = new CigType();
   	ris.setValue(value);
   	return ris;
   }

   private CuiType buildCuiType(String value) {
   	CuiType ris = new CuiType();
   	ris.setValue(value);
   	return ris;
   }

   private NomeCampoType buildNomeCampoType(String value) {
   	NomeCampoType ris = new NomeCampoType();
   	ris.setValue(value);
   	return ris;
   }

   private IdSchedaType buildIdSchedaType(String value) {
   	IdSchedaType ris = new IdSchedaType();
   	ris.setValue(value);
   	return ris;
   }

}