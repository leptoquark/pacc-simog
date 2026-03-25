package it.avlp.simog.massload;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.variazioneSA.VariazioneSAManager;
import it.avlp.simog.beans.Amministrazione;
import it.avlp.simog.beans.CUPLOTTO;
import it.avlp.simog.beans.CodiciCup;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.common.actions.VariazioneSAAction;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.massload.actions.GenericSchedeAction;
import it.avlp.simog.massload.actions.InserimentoSchedeAction;
import it.avlp.simog.massload.actions.ModificaSchedeAction;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.caricamento.CaricamentoBusiness;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.massload.parser.XmlParser;
import it.avlp.simog.massload.separate.SeparatorBusiness;
import it.avlp.simog.massload.util.FeedBackWriterBase;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.validation.MassloaderValidator;
import it.avlp.simog.massload.xmlbeans.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.RecVarAnagType;
import it.avlp.simog.massload.xmlbeans.RecVariazioneSAType;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.TipiSchedeType;
import it.avlp.simog.massload.xmlbeans.VarAnagType;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
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
/**
 * @author ppientini
 *
 */
public class MassLoader {
 
    // FIXato: !*!*!*!*! PP occorre trovare una soluzione migliore !
    //public static final String MASSLOADER_VERSION = "MASSLOADER - Versione 3.02.5.0 (20130228)";

    public String MASSLOADER_VERSION = buildVersion();
    
    String pathBase = "";
    String nomeFile = "";
    String nomeFileOut = "";
    String nomeFileOutNewFashion = "";
    String nomeFileConf = "";
    String nomePathConf = "";
    
    MassLoaderProperties configuration = null;
    Logger logger = null; 
    
    private DbManager dbm;
    
    private int retVal = RET_VALUES.NO_ERROR.ordinal();
     
    String cig = null;
     
    // is3028_RFWEBSC00Active
    private OrigineSchedaEnum origine;
    // is3031_WEBGL02Active
    private List <CUPLOTTO> cuplotto = null;
    SimogProperties confSimog = null;

    
    static enum RET_VALUES {
        NO_ERROR,
        FATAL_ERROR,
        INSUFF_PARAM,
        NO_BASEPATH,
        NO_INPATH,
        NO_OUTPATH,
        NO_WORKPATH,
        NO_INFILE,
        NO_CONFIG,
        NO_LOGGER
    }
    
    static final String ERR_NO_CONF = "Directory o file di configurazione inesistenti";
    static final String ERR_NO_BASE = "Directory di base inesistente";
    static final String ERR_NO_IN = "Directory di input inesistente";
    static final String ERR_NO_OUT = "Directory di output inesistente";
    static final String ERR_NO_WRK = "Directory di lavoro inesistente";
    static final String ERR_NO_INFILE = "File di input inesistente";
    static final String ERR_XML = "Errore XML durante verifica file";
    static final String ERR_IO= "Errore IO durante verifica file";
    static final String ERR_INIT= "Errore imprevisto durante inizializzazione";
    static final String ERR_CONN= "Errore imprevisto durante connessione al database";
    static final String ERR_INFO= "Errore imprevisto durante lettura dati comuni";  
    static final String ERR_SAVE= "Errore imprevisto durante salvataggio scheda";   
    static final String ERR_FEED="Errore imprevisto durante scrittura feedback";
    
    public MassLoader(OrigineSchedaEnum origine2) {
      this.origine = origine2;
      
      if(SimogFlags.is3031_RFWEBGL02Active()){
         // imposto che pronvengo dai WS, per pilotare la logica di attivazione della nuova gestione CUP
         // messa qui ha effetto su massloader, loaderappalto e loaderappalto AVCPASS
         SimogFlags.setFromWS(true);
      }
   }

    private String buildVersion(){
       String retVal = "";
       
       Properties version = new Properties();
       try {
         version.load(this.getClass().getResourceAsStream("/massloaderversion.properties"));
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
       
       retVal = "MASSLOADER - Versione $1($2) ($3)"
                   .replace("$1", version.getProperty("version.number"))
                   .replace("$2", version.getProperty("build.number"))
                   .replace("$3", version.getProperty("build.date"));
       
       return retVal;
    }
    
    /**
     * Metodo che si occupa della scrittura del feedback. </p>
     * 
     * - Scrive un file con il "vecchio" feedback </p>
     * - Scrive un file con il "nuovo" feedback, il cui nome file ha prefisso " NEW_ " </p>
     * 
     * @param test
     */
    void feedbackWrite(FeedBackDocument feedDoc, String cig, FeedBackDocument writeHere, FeedBackDocument writeHereNew){
        try {           
            /** writing new feedback ***/
            // eventuale sovrascrittura del feedback per gli errori delle anagrafiche
            FeedBackDocument newFashionDocumento = FeedBackWriterValidationsBeans.accorpaAnomalieSenzaCig(feedDoc.getFeedBack(),cig, this.configuration.getUsername());
            
            // se non e' nullo sono stati trovati i due tag senza ci dell'errore sulle anagrafiche, quindi sostituisci
            if(newFashionDocumento != null){                
                feedDoc = newFashionDocumento;
            }
            
            XmlCursor cursor = feedDoc.newCursor();
            if (cursor.toFirstChild())
            {
              cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance","schemaLocation"), "xmlbeans.massload.simog.avlp.it feedbackscheda_A.xsd");
            }
            XmlOptions opt = new XmlOptions();
            opt.setSavePrettyPrint();
            
            //is3031_WEBGL02Active
            if( SimogFlags.is3031_RFWEBGL02Active() 
                  && SimogProperties.getInstance().isCUPAttivo())            
            addCUPLOTTOtoFeedBack(feedDoc);
            
            /**XXX writing old feedback **/
            FeedBackDocument oldFashionDocumento = FeedBackWriterValidationsBeans.convertToOldFeedbackComplience(feedDoc.getFeedBack(),cig, this.configuration.getUsername());
            
            if(oldFashionDocumento == null){
               logger.fatal("**** ATTENZIONE nessuna anomaliaaaaa " + feedDoc.toString());
            }
            
            XmlCursor cursor1 = oldFashionDocumento.newCursor();
            if (cursor1.toFirstChild())
            {
                cursor1.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance","schemaLocation"), "xmlbeans.massload.simog.avlp.it feedbackscheda_A.xsd");
            }

            if( SimogFlags.is3031_RFWEBGL02Active() 
                  && SimogProperties.getInstance().isCUPAttivo())            
               addCUPLOTTOtoFeedBack(oldFashionDocumento);

            
            if (writeHereNew != null){
               writeHereNew.set(feedDoc);
               //writeHereNew.setFeedBack(feedDoc.getFeedBack());
            }
            else{
                feedDoc.save(new File(nomeFileOutNewFashion), opt);
            }           

            if (writeHere != null){
               writeHere.set(oldFashionDocumento);
               // writeHere.setFeedBack(oldFashionDocumento.getFeedBack());
            }
            else{
                oldFashionDocumento.save (new File(nomeFileOut), opt);
            }           

        } catch (IOException e) {
            logger.fatal(ERR_FEED, e);
            setRetVal(RET_VALUES.FATAL_ERROR.ordinal());
        }       
    }

    
    public void init(String nomePathConf, String nomeFileConf) throws Exception{
        
        try {
            PropertyConfigurator.configure(nomePathConf + "/massloader.log4j.properties" );
            logger = Logger.getLogger("MASSLOADER_LOGGER");
            logger.debug("LOGGER applicativo inizializzato correttamente");

            this.configuration = new MassLoaderProperties(nomePathConf+"/"+nomeFileConf, logger);
            try {
               this.confSimog = ConfigurationManager.getInstance(logger).getSimogProperties();
            } catch (SimogWSException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }                                       
    }

    
    /** punto di ingrsso elaborazione dati da stream
     * @param userName     utente che richiede l'operazione
     * @param nomePathConf path dove risiede il file di configurazione (senza barra finale)
     * @param nomeFileConf nome del file di configurazione
     * @param fileInput    dati da elaborare in formato XML
     * @param out          feedback formato old
     * @param outNew       feedback formato new
     * @return             eventuale errore o null se nessun errore
     */
    public String mainStream(String userName, String nomePathConf, String nomeFileConf, String fileInput, FeedBackDocument out, FeedBackDocument outNew) {

        // Impostazione del flag che disabilita i controlli sulle date validità nelle tipologiche
        SimogFlags.setFlagNoDate(true);
        
        // Blocco Try-Catch principale, in catch solo per eccezioni inaspettate
        try{

           /*
             * Inizializzazione configurazione
             */
            this.init(nomePathConf, nomeFileConf);
                 
            // PP 17.09.2103 override dell'utente da usare per il logging
            this.configuration.setProperty(MassLoaderProperties.USERNAME, userName);
            
            this.logger.debug("*** Elaborazione file da stream");
                
            /*
             * istanza nuovo parser
             */
            XmlParser test = new XmlParser(this.logger, userName);
            
            // validazione rispetto alla struttura ed eventuale scrittura feedback
            //gestisce sia errori di validazione che eccezione xmlException
            test.xsdValidate(fileInput, true);
            
            // elaborazione die dati trasmessi
            this.elabora( test);
            
            feedbackWrite(test.getFeedbackXml(), this.cig, out, outNew);

            this.logger.debug("*** FINE Elaborazione file da stream");
            this.logger.debug("*** FEEDBACK:\r\n"+ test.getFeedbackXml().xmlText());
            
            return null;
            
        } catch (XmlException e) {
           this.logger.error(ERR_XML, e); //  non ritorno FATAL per non allertare il monitoraggio 
            return ERR_XML + " - " + e.getMessage();
        } catch (IOException e) {
           this.logger.fatal(ERR_IO, e);
            return ERR_IO + " - " + e.getMessage();
        } catch (Exception e1) {
            return ERR_INIT + " - " + e1.getMessage();  
        }
    }
            
    /** elaborazione comune ai due punti di ingresso
     * @param test         oggetto che contiene i dati validi
     * @param feedBack     oggetto per restituzione feedback old
     * @param feedBackNew  oggetto per restituzione feedback old
     * @return             codice errore ritorno elaborazione
     * @throws Exception
     */
    int elabora (XmlParser test) throws Exception {
        try{
            // la struttura e' valida
            if(test.getErrors().size()== 0){
                // init connessione db
                try {
                    // apro la connessione solamente se devo operare delle operazioni sul db (ovverosia la struttura e' valida)
                   this.logger.info("Apro la connessione al db, poiche' la struttura e' valida");
                   this.setDbm(new DbManager(this.logger,this.configuration));
                   this.logger.info("Ottenuta connection: " + this.getDbm().getCurrentActiveConnection().toString());

                } catch (Exception e1) {
                   this.logger.fatal(ERR_CONN, e1);
                    return RET_VALUES.FATAL_ERROR.ordinal();    
                }
        
                /** variabili per info trasferimento    **/
                
                ArrayList<String> listOfCigNonValidi = null;            

                // e' il numero delle schede presenti
                int infoElaborateLivelloSchede = 0;
                if(test.getDoc().getTrasferimentoDati().getSchedeEliminateArray() != null){
                    infoElaborateLivelloSchede = test.getDoc().getTrasferimentoDati().getSchedeArray().length;
                }
                    
                // ogni volta che elimino un cig da quelli validi devo incrementare questo valore, quindi la gestione dell'incremento di questo valore 
                // la devolvo allo sharedLayers
                int infoErroriLivelloSchede = 0;
                // il max e' il numero delle elaborate
                int infoWarningLivelloSchede = 0;
                // elaborate - errore
                int infoInseriteLivelloSchede = 0;
                /** variabili per info trasferimento end    **/

//  !*!*!*!*!*! codice per elaborazione BOLZANO
//if(SimogFlags.isBOLZANOActive()){
//   // creazione della gara e lotto se non esistono
//   
//  // oggetto che si occupa di convertire dati xml in dati simog
//  ConvertXMLtoBeanBusiness converter = new ConvertXMLtoBeanBusiness();
//
//  for (int i = 0; i < test.getDoc().getTrasferimentoDati().getSchedeArray().length; i++) {
//
//     DatiComuniType datiComuni = test.getDoc().getTrasferimentoDati().getSchedeArray(i).getDatiComuni();
//     PubblicazioneType pubb = test.getDoc().getTrasferimentoDati().getSchedeArray(i).getPubblicazione();
//     AggiudicazioneType a = test.getDoc().getTrasferimentoDati().getSchedeArray(i).getSchedaCompletaArray(0).getAggiudicazione();
//
//     AppaltoType aa = null;
//     
//     if(a != null)
//        aa = a.getAppalto();
//
//     String cig = test.getDoc().getTrasferimentoDati().getSchedeArray(i).getDatiComuni().getCIG().toUpperCase();
//     
//     // senno scoppia la conversione
//     if(!PageHelper.isValidCIG(cig))
//        test.getDoc().getTrasferimentoDati().getSchedeArray(i).getDatiComuni().setCIG("0000000000");
//     
//     InfoComuniBean icb = converter.converti(datiComuni);
//     PubblicazioneBean puB = converter.converti(pubb);
//     
//     GaraManager gm = new GaraManager(dbm.getCurrentActiveConnection(), logger);
//     LottoManager lm = new LottoManager(dbm.getCurrentActiveConnection(), logger);
//   
//     dbm.getCurrentActiveConnection().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//     
//     //prima devo controllare se il lotto esiste e se è un cig valido
//     //solo se non esiste o non valido creo il nuovo lotto e sostituisco il dato sull'xml
//     
//     List<Lotto> lista = null;
//     boolean valido = PageHelper.isValidCIG(cig);
//     
//     if(valido)
//        lista = lm.getLottoByCigWS(test.getDoc().getTrasferimentoDati().getSchedeArray(i).getDatiComuni().getCIG());
//     
//     if(!valido || (valido && lista.size() == 0)){        
//        // richiedo un nuovo cig inserendo gara e lotto
//        Gara gara = new Gara();
//        gara.setCF_AMMINISTRAZIONE(icb.getCfAmministrazione());
//        gara.setCF_UTENTE(icb.getCfRup());
//        gara.setDATA_CONFERMA_GARA(PageHelper.getCurrentDate());
//        gara.setData_creazione(PageHelper.getCurrentDate());
//        gara.setDATA_PERFEZIONAMENTO_BANDO(PageHelper.getCurrentDate());
//        gara.setDATA_TERMINE_PAGAMENTO(PageHelper.getCurrentDate());
//        gara.setDENOM_AMMINISTRAZIONE(icb.getDenAmministrazione());
//        gara.setDENOM_STAZIONE_APPALTANTE(icb.getDenomCC());
//        gara.setID_MODO_GARA(aa == null ? 0 : aa.getIDMODOINDIZIONE() == null ? 0 : Integer.valueOf(aa.getIDMODOINDIZIONE()));
//        gara.setID_MODO_REAL(datiComuni.getMODOREALIZZAZIONE() == null ? 0 : Integer.valueOf(datiComuni.getMODOREALIZZAZIONE()));
//        gara.setID_OSSERVATORIO("004");
//        gara.setID_STATO_GARA(StatiScheda.CONFERMATO);
//        gara.setID_STAZIONE_APPALTANTE(icb.getCodiceCC());
//        gara.setIMPORTO_GARA(new BigDecimal(-1));
//        gara.setNumeroLotti(1);
//        gara.setOggetto("*** GARA CREATA PER CIG ANOMALO " + cig);
//        gara.setTIPO_SCHEDA_GARA(icb.getFlagEnteSpeciale());
//        
//        long idGara = gm.creaNuovaGara(gara);
//        gara.setIdGara(idGara);
//        gm.confirmGara(gara);
//    
//        Lotto lotto = new Lotto();
//        lotto.setData_Pubblicazione(puB.getDataGuce() != null ? puB.getDataGuce() : puB.getDataGuri() != null ? puB.getDataGuri() : null);
//        lotto.setDataCreazione(PageHelper.getCurrentDate());
//        lotto.setDataScadenzaPagamenti(lotto.getData_Pubblicazione());
//        lotto.setFLAG_ESCLUSO(icb.getFLAG_ESCLUSO());
//        lotto.setId_Categoria_prevalente(Costanti.TIPO_SCHEDA_FORNITURE.equals(icb.getTipoContratto()) ? "FB" : Costanti.TIPO_SCHEDA_SERVIZI.equals(icb.getTipoContratto()) ? "FS" : "OG1");
//        lotto.setId_CPV("44164200-9"); // tubi
//        lotto.setID_ESCLUSIONE(icb.getID_ESCLUSIONE());
//        lotto.setId_Gara(idGara);
//        lotto.setId_Scelta_Contraente(aa != null ? aa.getIDSCELTACONTRAENTE() : "1");
//        // patch contraente
//        if("0".equals(lotto.getId_Scelta_Contraente())) lotto.setId_Scelta_Contraente("1");
//        lotto.setId_Tipologia("01");
//        lotto.setIMPORTO_ATTUAZIONE_SICUREZZA(aa != null ? aa.getIMPORTOATTUAZIONESICUREZZA() : new BigDecimal(0));
//        lotto.setImporto_Lotto(new BigDecimal(-1));
//        lotto.setLUOGO_ISTAT(aa != null ? aa.getLUOGOISTAT() : null);
//        lotto.setLUOGO_NUTS(aa != null ? aa.getLUOGONUTS() : null);
//        lotto.setOggetto(gara.getOggetto());
//        lotto.setTIPO_CONTRATTO_LOTTO(icb.getTipoContratto());
//        
//        
//        Lotto newLotto = lm.creaNuovoLotto(gara, lotto, CIGBean.APPL_TEST, "000");
//        lm.perfezionaLotto(String.valueOf(newLotto.getId_Lotto()), PageHelper.getCurrentDate(), PageHelper.getCurrentDate(), new BigDecimal(0));
//     
//        datiComuni.setCIG(newLotto.getCIG() + newLotto.getCIG_kkk());
//        
//        test.getDoc().getTrasferimentoDati().getSchedeArray(i).getDatiComuni().setCIG(newLotto.getCIG() + newLotto.getCIG_kkk());
//     }
//     
//     if(!PageHelper.isValidCIG(test.getDoc().getTrasferimentoDati().getSchedeArray(i).getDatiComuni().getCIG()))
//        test.getDoc().getTrasferimentoDati().getSchedeArray(i).getDatiComuni().setCIG("0000000000");
//  }
//  test.getDoc().save(new File("c:\\nuovo.xml"));
//} 
                
                // recupero primo cig per casi speciali
                if(test.getDoc().getTrasferimentoDati().getSchedeArray()!= null
                        && test.getDoc().getTrasferimentoDati().getSchedeArray().length > 0){
                   this.cig = test.getDoc().getTrasferimentoDati().getSchedeArray()[0].getDatiComuni().getCIG();
                    
                } // PP caso solo eliminazione schede
                else if (test.getDoc().getTrasferimentoDati().getSchedeEliminateArray()!= null
                            && test.getDoc().getTrasferimentoDati().getSchedeEliminateArray().length > 0){
                   this.cig = test.getDoc().getTrasferimentoDati().getSchedeEliminateArray()[0].getCIG();
                }
                    
                // oggetto per la scrittura di feedback 
                FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(this.logger, this.configuration.getUsername());
                    
                // test per verificare che esista almeno una sezione
                if(test.getDoc().getTrasferimentoDati().getSchedeArray().length == 0
                    && test.getDoc().getTrasferimentoDati().getSchedeEliminateArray().length == 0
                    && test.getDoc().getTrasferimentoDati().getVariazioniAnagArray().length == 0
                    && (SimogFlags.is30233_RFMLVS00Active() && test.getDoc().getTrasferimentoDati().getVariazioniSA() == null)
                   ) {
                    
                    ValidationBean validation = 
                        new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_213, 
                            ValidationBean.VALBEAN_SEV_ERR, 
                            0, 0, 0, TipiSchedeType.DATI_COMUNI.toString(), null, null);
                    
                    List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
                    validazioni.add(validation);
                    
                    feedBackWriter.fillStandardFeedBack(test.getFeedbackXml().getFeedBack(), 
                            validazioni, 
                            "inizializzazione",  TipiSchedeType.DATI_COMUNI.toString(), null, null, 0,null, null);
                }
                    
                //Aggiudicatari - opt
                AggiudicatariType at = null;
                if(test.getDoc().getTrasferimentoDati().isSetAggiudicatari())   
                    at = test.getDoc().getTrasferimentoDati().getAggiudicatari();
                
                //Responsabili - opt
                ResponsabiliType rt = null;
                if(test.getDoc().getTrasferimentoDati().isSetResponsabili())    
                    rt = test.getDoc().getTrasferimentoDati().getResponsabili();
                
boolean stopElab = false;     

// 3.02.3.3 gestione variazione SA, deve essere l'unica sezione presente
if (SimogFlags.is30233_RFMLVS00Active()){
   if(test.getDoc().getTrasferimentoDati().getVariazioniSA() != null
         && (test.getDoc().getTrasferimentoDati().getSchedeArray().length > 0 
               || test.getDoc().getTrasferimentoDati().getSchedeEliminateArray().length > 0
               || test.getDoc().getTrasferimentoDati().getVariazioniAnagArray().length > 0
               || test.getDoc().getTrasferimentoDati().getResponsabili() != null
               || test.getDoc().getTrasferimentoDati().getAggiudicatari() != null)
      ){
      ValidationBean validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_215, 
            ValidationBean.VALBEAN_SEV_ERR, 0, 0, 0,  IdentificativoSchede.DATI_COMUNI, 
            String.valueOf(test.getDoc().getTrasferimentoDati().getVariazioniSA().getVariazioneSAArray(0).getIDGARA()), "");

      List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
       validazioni.add(validation);
       feedBackWriter.fillStandardFeedBack(test.getFeedbackXml().getFeedBack(), validazioni, OperazioneScheda.getVariazioneSA().getNomeOperazione(), 
                 IdentificativoSchede.DATI_COMUNI, String.valueOf(test.getDoc().getTrasferimentoDati().getVariazioniSA().getVariazioneSAArray(0).getIDGARA()), 
                 "", 0, "", "");

       // interrompere l'elaborazione
       stopElab = true;
   }
}
if(!stopElab){   

               // 3.02.3.3 variazioni SA
               if (SimogFlags.is30233_RFMLVS00Active() && test.getDoc().getTrasferimentoDati().getVariazioniSA() != null){
                  VariazioneSAManager saManager = new VariazioneSAManager(this.getDbm().getCurrentActiveConnection(),logger);
                  /* per ogni istanza del tipo Variazione SA
                        controlli formali
                           id motivo valido 
                           id gara valido e gara che si può variare
                           cf amm valido
                           guid valorizzato
                        se ci sono errori -> exit
                        accesso ai ws
                           se non ritorna dati errore -> exit
                           se ritorna dati
                              verifica che guid esista
                                 se non esiste errore -> exit
                           aggiornamento gara e dati comuni
                  */

                  for (int i = 0; i < test.getDoc().getTrasferimentoDati().getVariazioniSA().getVariazioneSAArray().length; i++) {
                     List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
                     RecVariazioneSAType elem = test.getDoc().getTrasferimentoDati().getVariazioniSA().getVariazioneSAArray(i);
                     
                     // controllo motivo valido
                     if (!saManager.loadMotiviVariazioneSA().containsKey(elem.getMOTIVO())){
                        ValidationBean validation 
                           = new SchedaSpecificaValidationBean(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "MOTIVO"), 
                              ValidationBean.VALBEAN_SEV_ERR, 0, i, 0,  IdentificativoSchede.DATI_COMUNI, 
                              String.valueOf(elem.getIDGARA()), "");
                        validazioni.add(validation);
                     }
                     
                     // controllo gara esistente e non cancellata
                     GaraManager gm = new GaraManager(getDbm().getCurrentActiveConnection(), logger);
                     Gara gara = gm.getGara(new Long(elem.getIDGARA()));
                     if(gara == null || gara.getDATA_CANCELLAZIONE_GARA() != null || gara.getDATA_INIB_PAGAM() != null){
                        ValidationBean validation 
                        = new SchedaSpecificaValidationBean(Messaggi.SIMOG_VALIDAZIONE_211.replace("$1", "un ID_GARA corripondente ad una gara valida"), 
                           ValidationBean.VALBEAN_SEV_ERR, 0, i, 0,  IdentificativoSchede.DATI_COMUNI, 
                           String.valueOf(elem.getIDGARA()), "");
                        validazioni.add(validation);                        
                     }
                     
                     if(SimogFlags.is3028_RFWEBGL07Active() && gara != null){
                        GaraLottoManager glm = new GaraLottoManager(getDbm().getCurrentActiveConnection(), logger);
                        //LottoManager lman = new LottoManager(getDbm().getCurrentActiveConnection(), logger);
                        if(glm.isAVCPass(gara, null, AVCPassFunzioneEnum.ML_VARIAZIONE_SA.getCodice())){
                           ValidationBean validation 
                           = new SchedaSpecificaValidationBean(Messaggi.SIMOG_AVCPASS_001, 
                              ValidationBean.VALBEAN_SEV_ERR, 0, i, 0,  IdentificativoSchede.DATI_COMUNI, 
                              String.valueOf(elem.getIDGARA()), "");
                           validazioni.add(validation);   

                           if(listOfCigNonValidi == null) listOfCigNonValidi = new ArrayList<String>();
                           listOfCigNonValidi.add(String.valueOf(elem.getIDGARA()));                             
                        }
                     }
                     
                     // controllo amministrazione valida (tramite servizi anagrafe)
                     Amministrazione amm =new Amministrazione(elem.getCFAMMINISTRAZIONE(), "");
                     VariazioneSAAction saAction = new VariazioneSAAction(getDbm().getCurrentActiveConnection(), logger, confSimog);
                     List<StazioneAppaltante> saList = null;
                     String message = "";
                     try{
                         saList = saAction.getSAList(amm, confSimog.getWsAnagUrl(), confSimog.getWsAnagUser(), confSimog.getWsAnagPwd());
                         if(saList.size() == 0)
                             message = Messaggi.SIMOG_VALIDAZIONE_068;
                     }
                     catch(Exception se) {
                        throw (se);
                         //message = Messaggi.SIMOG_MASSLOADER_205 + " [" + se.getMessage() +  "]";
                     }
                     
                     int found = -1;

                     if (!"".equals(message)){
                        ValidationBean validation 
                           = new SchedaSpecificaValidationBean(message, 
                              ValidationBean.VALBEAN_SEV_ERR, 0, i, 0,  IdentificativoSchede.DATI_COMUNI, 
                              String.valueOf(elem.getIDGARA()), "");
                        validazioni.add(validation);
                     }
                     else{
                        // non ci sono problemi con l'amministrazione verifico il guid del centro di costo
                        found = -1;
                        
                        for (int j = 0; j < saList.size(); j++) {
                           StazioneAppaltante stazioneAppaltante = (StazioneAppaltante) saList.get(j);
                           if (stazioneAppaltante.getIdUfficio().equals(elem.getIDCENTROCOSTO())){
                              found = j;
                              break;
                           }                          
                        }

                        if(found == -1){
                           ValidationBean validation 
                           = new SchedaSpecificaValidationBean(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "IDCENTROCOSTO"), 
                              ValidationBean.VALBEAN_SEV_ERR, 0, i, 0,  IdentificativoSchede.DATI_COMUNI, 
                              String.valueOf(elem.getIDGARA()), "");
                           validazioni.add(validation);                              
                        }
                     }

                     // scrittura su feedback degli errori
                     if (validazioni.size() > 0){
                        feedBackWriter.fillStandardFeedBack(test.getFeedbackXml().getFeedBack(), validazioni, OperazioneScheda.getVariazioneSA().getNomeOperazione(), 
                              IdentificativoSchede.DATI_COMUNI, String.valueOf(elem.getIDGARA()),
                               "", i, "" , "");
                     }
                     else{
                        // procedo alla variazione della stazione appaltante
                        saAction.doVariazione(Long.valueOf(elem.getIDGARA()), Long.valueOf(elem.getMOTIVO()), saList.get(found));
                        
                        // scrivo l'esito positivo sul feedback
                        feedBackWriter.fillMassloaderFeedBack(test.getFeedbackXml().getFeedBack(), 
                              new SchedaSpecificaValidationBean("",ValidationBean.VALBEAN_SEV_INFO,0,i,0,
                                    IdentificativoSchede.DATI_COMUNI, String.valueOf(elem.getIDGARA()),"","","")
                              ,OperazioneScheda.getVariazioneSA().getNomeOperazione());
                     }
                  }
               }
               
               //Variazioni anagrafiche - opt
               // gm variazioni anagrafiche non consentite per adesso, invio un warning per ogni richiesta
                if(!SimogFlags.isVarAnagMLActive()){                    
                    VarAnagType[] variazioni = null;
                    variazioni = test.getDoc().getTrasferimentoDati().getVariazioniAnagArray();
                    if(variazioni != null && variazioni.length>0){
                        test.getDoc().getTrasferimentoDati().getVariazioniAnagArray(0).getVariazioneAnagArray(0).getRiferimento();
                        for(int i=0; i<variazioni.length; i++){
                            RecVarAnagType[] recs = variazioni[i].getVariazioneAnagArray();
                            if(recs!=null && recs.length>0){
                                for(int j=0; j<recs.length; j++){
                                    RecVarAnagType rec = recs[j];
                                    if(rec!=null && rec.getRiferimento()!=null){
                                        ValidationBean validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_212, ValidationBean.VALBEAN_SEV_WARN, 
                                                0, 0, 0, rec.getRiferimento().getSCHEDA().toString(), rec.getRiferimento().getCIG(), rec.getRiferimento().getCUI());
                                        List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
                                        validazioni.add(validation);
                                        if(validazioni.size()>0){
                                            feedBackWriter.fillStandardFeedBack(test.getFeedbackXml().getFeedBack(), validazioni, "variazioni anagrafiche", rec.getRiferimento().getSCHEDA().toString(), rec.getRiferimento().getCIG(), 
                                                    rec.getRiferimento().getCUI(), 0, rec.getRiferimento().getIDSCHEDALOCALE(), rec.getRiferimento().getIDSCHEDASIMOG());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                    
                //
                // variazioni anagrafiche
                //
                if(SimogFlags.isVarAnagMLActive()){
                    if(test.getDoc().getTrasferimentoDati().getVariazioniAnagArray() != null 
                            && test.getDoc().getTrasferimentoDati().getVariazioniAnagArray().length > 0){

                        SharedLayer tempLayer = new SharedLayer(this.getDbm().getCurrentActiveConnection(), this.logger, feedBackWriter, test.getFeedbackXml().getFeedBack(), this.configuration.getUsername());
                        
                        tempLayer.setCig(this.cig);
        
                        // oggetto che si occupa di convertire dati xml in dati simog
                        ConvertXMLtoBeanBusiness converter = new ConvertXMLtoBeanBusiness();
                        // oggetto che si occupa del caricamento dei dati
                        CaricamentoBusiness loader = new CaricamentoBusiness(this.getDbm().getCurrentActiveConnection(), this.logger);
                        // oggetto che si occupa delle azioni collegate all'operazione di inserimento
                        GenericSchedeAction action = new GenericSchedeAction(this.getDbm().getCurrentActiveConnection(), this.logger, this.origine); 
            
                        // oggetto che si occupa delle validazioni
                        MassloaderValidator validator = new MassloaderValidator(this.getDbm().getCurrentActiveConnection(), this.logger);
                        
                        Map<Integer, ArrayList<IdsSchedaXML>> listeSeparate = tempLayer.eseguiOperazione(null, at, rt, converter, loader, action, validator);

                       VarAnagLayer vaLayer = new VarAnagLayer(this.getDbm().getCurrentActiveConnection(), this.logger, feedBackWriter, test.getFeedbackXml().getFeedBack(), this.configuration.getUsername(), this.origine);
                        vaLayer.esequiOperazione(validator, test.getDoc().getTrasferimentoDati().getVariazioniAnagArray(),
                              tempLayer.getListOfAnaPartecipanti(), tempLayer.getListOfAnaResponsabili());
                        ArrayList<String> listOfCigNonValidiVar = vaLayer.listOfCigNonValidi;
                        if(listOfCigNonValidiVar != null){
                            // write errors
                            feedBackWriter.fillMessaggioErroreOperazioniCigs(test.getFeedbackXml().getFeedBack(),OperazioneScheda.getVariazioneAnag(), listOfCigNonValidiVar);
                            infoErroriLivelloSchede += listOfCigNonValidiVar.size();
                        }
                        
                        infoErroriLivelloSchede += tempLayer.numeroSchedeInErrore;
                    }
                }

                /** cancellazione   **/
                if(test.getDoc().getTrasferimentoDati().getSchedeEliminateArray() != null && test.getDoc().getTrasferimentoDati().getSchedeEliminateArray().length > 0){
                    CancellazioneLayer cLayer = new CancellazioneLayer(this.getDbm().getCurrentActiveConnection(), 
                          this.logger, feedBackWriter, test.getFeedbackXml().getFeedBack(), 
                          this.configuration.getUsername(), this.origine);
                    
                    // eseguo la cancellazione
                    cLayer.esequiOperazione(test.getDoc().getTrasferimentoDati().getSchedeEliminateArray());
                    
                    listOfCigNonValidi = cLayer.listOfCigNonValidi;
                    if(listOfCigNonValidi != null){
                        // se ci sono cig non validi le cancellazioni sono state rollbackate, tolgo le anomalie con successo (idscheda)
                        feedBackWriter.fillMessaggioErroreOperazioniCigs(test.getFeedbackXml().getFeedBack(),OperazioneScheda.getCancellazione(), listOfCigNonValidi);
                    }
                }

                /** modifica ed Inserimento **/
                SharedLayer imLayer = new SharedLayer(this.getDbm().getCurrentActiveConnection(), this.logger, feedBackWriter, test.getFeedbackXml().getFeedBack(), this.configuration.getUsername());

                // XXX: HANDLE LIST OF CIG PROCESSANDO I QUALI SI SONO VERIFICATI DEGLI ERRORI
                DatiAggiudicazioneType[] schedeArray = null;
                if(listOfCigNonValidi != null){
                    // rimuovi le schede con i cig non validi
                    schedeArray = imLayer.filterSchedePerEliminareICigNonValidi(test.getDoc().getTrasferimentoDati().getSchedeArray(), listOfCigNonValidi);
                    // imposta a null la lista per poter rieffettuare il controllo di nullita sulle altre operazionis
                    listOfCigNonValidi = null;
                }else{
                    // nel caso tutti validi assegna tutti !
                    schedeArray = test.getDoc().getTrasferimentoDati().getSchedeArray();
                }
                    
                //XXX: Se mi sta inviando i dati AVCPASS, integro alcune informazioni non disponibili nel sistema
                if( SimogFlags.is3028_RFWEBSC00Active() && OrigineSchedaEnum.AVCPASS.equals(this.origine) ){
                   AVCPassAction avcpassAction = new AVCPassAction(dbm.getCurrentActiveConnection(), logger, confSimog);
                   
                   try {
                     schedeArray = avcpassAction.integraDatiAVCPass(test.getDoc().getTrasferimentoDati());
                  } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                     logger.debug(e.getMessage());
                     logger.debug(FeedBackWriterBase.getStack(e));
                  }
                }
                
                //XXX: Rimpiazzo dei dati protetti da AVCPass
                if( SimogFlags.is3028_RFWEBSC00Active() && !OrigineSchedaEnum.AVCPASS.equals(this.origine) ){
                   AVCPassAction avcpassAction = new AVCPassAction(dbm.getCurrentActiveConnection(), logger, confSimog);
                   
                   try {
                     schedeArray = avcpassAction.replaceDatiProtettiAVCPass(test.getDoc().getTrasferimentoDati());
                  } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                     logger.debug(e.getMessage());
                     logger.debug(FeedBackWriterBase.getStack(e));
                  }
                }

                if( SimogFlags.is3030_RFWEBSC00Active() ){
                   GenericSchedeAction action = new GenericSchedeAction(dbm.getCurrentActiveConnection(), this.logger, this.origine);
                   schedeArray = action.replaceDatiAdesione(test.getDoc().getTrasferimentoDati(), 
                         feedBackWriter, test.getFeedbackXml().getFeedBack());
                }

                if(schedeArray != null && schedeArray.length > 0){
                    
                    imLayer.setCig(this.cig);
    
                    // oggetto che si occupa di convertire dati xml in dati simog
                    ConvertXMLtoBeanBusiness converter = new ConvertXMLtoBeanBusiness();
                    // oggetto che si occupa del caricamento dei dati
                    CaricamentoBusiness loader = new CaricamentoBusiness(this.getDbm().getCurrentActiveConnection(), this.logger);
                    // oggetto che si occupa delle azioni collegate all'operazione di inserimento
                    GenericSchedeAction action = new GenericSchedeAction(this.getDbm().getCurrentActiveConnection(), this.logger, this.origine);   
        
                    // oggetto che si occupa delle validazioni
                    MassloaderValidator validator = new MassloaderValidator(this.getDbm().getCurrentActiveConnection(), this.logger);
                    
                    Map<Integer, ArrayList<IdsSchedaXML>> listeSeparate = imLayer.eseguiOperazione(Arrays.asList(schedeArray), at, rt, converter, loader, action, validator);
                    listOfCigNonValidi = imLayer.listOfCigNonValidi;
                    
                    // XXX: HANDLE LIST OF CIG PROCESSANDO I QUALI SI SONO VERIFICATI DEGLI ERRORI
                    if(listOfCigNonValidi != null){
                        // write errors
                        feedBackWriter.fillMessaggioErroreOperazioniCigs(test.getFeedbackXml().getFeedBack(),null, listOfCigNonValidi);
//                          logger.debug("\r\n\t\t\t####\r\nCig NON Validi Separazione..:\r\n" +test.getFeedbackXml().getFeedBack()+"\r\n\t\t\t####\r\n");
                        if(listeSeparate != null){
                            // rimuovi le schede con i cig non validi
                            listeSeparate = imLayer.filterSchedePerEliminareICigNonValidi(listeSeparate, listOfCigNonValidi);
                        }else{
                            // se e' nulla crea una istanza vuota.
                            listeSeparate = new TreeMap<Integer, ArrayList<IdsSchedaXML>>();
                            imLayer.numeroSchedeInErrore = listOfCigNonValidi.size();
                        }
                        // imposta a null la lista per poter rieffettuare il controllo di nullita sulle altre operazionis
                        //listOfCigNonValidi = null;
                    }
                    // gestione errore sulle anagrafiche.. non rientra nell'eliminazione di un cig da quelli validi ma tutto il file 
                    if(listeSeparate == null){
                        // se e' nulla crea una istanza vuota.
                        listeSeparate = new TreeMap<Integer, ArrayList<IdsSchedaXML>>();
                    }
                    List<SoggettoPartecipanteBean> listOfAnaPartecipanti = imLayer.getListOfAnaPartecipanti();
                    List<SoggettoResponsabileBean> listOfAnaResponsabili = imLayer.getListOfAnaResponsabili();
                            
                    TreeMap<String, Integer> warningByCig = new TreeMap<String, Integer>();
                    /** modifica    **/
                    ModificaSchedeAction modificaAction = new ModificaSchedeAction(this.getDbm().getCurrentActiveConnection(), this.logger, this.configuration.getUsername(), this.origine); 
                    ArrayList<IdsSchedaXML> schedeInModifica = listeSeparate.get(SeparatorBusiness.SCHEDEINMODIFICA);
                    if(schedeInModifica != null && schedeInModifica.size() > 0){
                       this.logger.debug("Sono state rilevate delle schede da modificare("+schedeInModifica.size()+"), si procedera' dunque alla modifica delle schede");
                        ModificaLayer mLayer = new ModificaLayer(this.getDbm().getCurrentActiveConnection(), this.logger, feedBackWriter, test.getFeedbackXml().getFeedBack(), this.configuration.getUsername(), this.origine);
                        mLayer.setListOfWarning(warningByCig);
                        mLayer.eseguiOperazione(modificaAction, validator, schedeInModifica, listOfAnaPartecipanti, listOfAnaResponsabili, converter, loader);
                        
                        if( SimogFlags.is3031_RFWEBGL02Active() 
                              && SimogProperties.getInstance().isCUPAttivo())            
                           addCuplotto(validator.getCuplotto());
                           validator.setCuplotto(null);
                        
                        listOfCigNonValidi = mLayer.listOfCigNonValidi;
                    }
                    
                    // XXX: HANDLE LIST OF CIG PROCESSANDO I QUALI SI SONO VERIFICATI DEGLI ERRORI
                    if(listOfCigNonValidi != null){
                        // write errors
                        feedBackWriter.fillMessaggioErroreOperazioniCigs(test.getFeedbackXml().getFeedBack(),OperazioneScheda.getModifica(), listOfCigNonValidi);
//                          logger.debug("\r\n\t\t\t####\r\nCig NON Validi Modifica..:\r\n" +test.getFeedbackXml().getFeedBack()+"\r\n\t\t\t####\r\n");
                        
                        if(listeSeparate != null){
                            // rimuovi le schede con i cig non validi
                            listeSeparate = imLayer.filterSchedePerEliminareICigNonValidi(listeSeparate, listOfCigNonValidi);
                        }else{
                            // se e' nulla crea una istanza vuota.
                            listeSeparate = new TreeMap<Integer, ArrayList<IdsSchedaXML>>();
                            imLayer.numeroSchedeInErrore = 1;
                        }
                        // imposta a null la lista per poter rieffettuare il controllo di nullita sulle altre operazionis
                        // PP conservo quelli della fase di inserimento 
                        // listOfCigNonValidi = null;
                    }
                    
                    /** inserimento     **/
                    InserimentoSchedeAction inserimentoAction = new InserimentoSchedeAction(this.getDbm().getCurrentActiveConnection(), this.logger, this.configuration.getUsername(), this.origine);    
                    ArrayList<IdsSchedaXML> schedeInInserimento = listeSeparate.get(SeparatorBusiness.SCHEDEININSERIMENTO);
                    if(schedeInInserimento != null && schedeInInserimento.size() > 0){
                       this.logger.debug("Sono state rilevate delle schede da inserire("+schedeInInserimento.size()+"), si procedera' dunque allo inserimento delle schede");
                        InserimentoLayer iLayer = new InserimentoLayer(this.getDbm().getCurrentActiveConnection(), 
                              this.logger, feedBackWriter, test.getFeedbackXml().getFeedBack(), 
                              this.configuration.getUsername(), this.origine);
                        iLayer.setListOfWarning(warningByCig);

                        // PP imposto la lista cig non validi della fase di inserimento
                        iLayer.listOfCigNonValidi = listOfCigNonValidi;

                        iLayer.eseguiOperazione(inserimentoAction, validator, schedeInInserimento, listOfAnaPartecipanti, listOfAnaResponsabili, converter, loader);
                        listOfCigNonValidi = iLayer.listOfCigNonValidi; 
                        
                        if( SimogFlags.is3031_RFWEBGL02Active() 
                              && SimogProperties.getInstance().isCUPAttivo())            
                           addCuplotto(validator.getCuplotto());
                        validator.setCuplotto(null);
                    }
                    
                    // XXX: HANDLE LIST OF CIG PROCESSANDO I QUALI SI SONO VERIFICATI DEGLI ERRORI
                    if(listOfCigNonValidi != null){
                        // write errors
                        feedBackWriter.fillMessaggioErroreOperazioniCigs(test.getFeedbackXml().getFeedBack(),OperazioneScheda.getInserimento(), listOfCigNonValidi);
//                          logger.debug("\r\n\t\t\t####\r\nCig NON Validi Inserimento..:\r\n" +test.getFeedbackXml().getFeedBack()+"\r\n\t\t\t####\r\n");
                        
                        if(schedeInInserimento != null){
                            imLayer.filterSchedePerEliminareICigNonValidi(schedeInInserimento, listOfCigNonValidi);
                        }
                        // imposta a null la lista per poter rieffettuare il controllo di nullita sulle altre operazionis
                        // /PP mi serve la lista per contare le schede in errore
                        //listOfCigNonValidi = null;
                    }
                    infoWarningLivelloSchede = warningByCig.size();
                    // PP infoErroriLivelloSchede += imLayer.numeroSchedeInErrore;
                    infoErroriLivelloSchede += listOfCigNonValidi == null ? 0 : listOfCigNonValidi.size();
                }
}                                    
                /** Aggiornamento Contatori info trasferimento **/          
                test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMELABORATE(infoElaborateLivelloSchede);
                test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMERRORE(infoErroriLivelloSchede);
                infoInseriteLivelloSchede = infoElaborateLivelloSchede - infoErroriLivelloSchede;
                test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMCARICATE(infoInseriteLivelloSchede);
                test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMWARNING(infoWarningLivelloSchede);
                    
//  PATCH - VL - condizione per rilevare eccezioni non gestite  a livello di feedback, presenza del solo tag "infoflusso"
                if(test.getFeedbackXml().getFeedBack().getAnomalieSchedeArray() == null 
                      || test.getFeedbackXml().getFeedBack().getAnomalieSchedeArray().length == 0){
                    test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMELABORATE(infoElaborateLivelloSchede);
                    test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMERRORE(++infoErroriLivelloSchede);
                    test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMCARICATE(0);
                    test.getFeedbackXml().getFeedBack().getInfoFlusso().setNUMWARNING(0);
                    feedBackWriter.writeUnandledException(test.getFeedbackXml(), this.cig, " *** NO ANOMALIE ***", this.configuration.getUsername());
                }
                    
//                  logger.info("\r\n\t\t\t####\r\nInputString COMPLETA..:\r\n" +test.getDoc().getTrasferimentoDati().xmlText()+"\r\n\t\t\t####\r\n");
                                        
                /****/
                
            }
                //
                // -- REPORT END --  
                
            

        }catch(Exception t){
           // annullo scritture in sospeso sul db altrimenti fa il commit automatico
            if(this.getDbm().getCurrentActiveConnection() != null
                  && this.getDbm().getCurrentActiveConnection().getAutoCommit()==false)
               this.getDbm().getCurrentActiveConnection().rollback();
            
            // log
           this.logger.fatal("Eccezione in Main: " +t.getMessage());
            
            // stack trace
            t.printStackTrace();
            
            // try to write on feedback..

            FeedBackWriterValidationsBeans feedBackWriter = new FeedBackWriterValidationsBeans(this.logger, this.configuration.getUsername());
            
            //FeedBackDocument feedDoc = FeedBackDocument.Factory.newInstance();
            String payload = FeedBackWriterBase.getStack(t);
            if(payload == null || "".equals(payload))
               payload = t.getMessage();
            
            feedBackWriter.writeUnandledException(test.getFeedbackXml(), this.cig, payload, this.configuration.getUsername());
             
//          logger.debug("\r\n\t\t\t####\r\nFeedBack COMPLETO(EXCEPTION)..:\r\n" +feedDoc.getFeedBack()+"\r\n\t\t\t####\r\n");
            //this.feedbackWrite(feedDoc, this.cig, feedBack, feedBackNew);

            this.setRetVal(RET_VALUES.FATAL_ERROR.ordinal());           
        }
        finally{
            // chiusura della connessione se ancora attiva.
            if(this.getDbm() != null && this.getDbm().getCurrentActiveConnection() != null){
               this.logger.info("Closing connection: " + this.getDbm().getCurrentActiveConnection().toString());
               this.getDbm().closeConnection();
            }

        }
        
        return this.getRetVal();
    }

   public int getRetVal() {
      return retVal;
   }


   public void setRetVal(int retVal) {
      this.retVal = retVal;
   }


   public MassLoaderProperties getConfiguration() {
      return configuration;
   }


   public Logger getLogger() {
      return logger;
   }

   public DbManager getDbm() {
      return dbm;
   }

   public void setDbm(DbManager dbm) {
      this.dbm = dbm;
   }

   public List<CUPLOTTO> getCuplotto() {
      return cuplotto;
   }

   public void addCuplotto(CUPLOTTO cuplot) {
      if(this.cuplotto==null)
         this.cuplotto = new ArrayList<CUPLOTTO>();
      this.cuplotto.add(cuplot);
   }
    
   /** aggiunge la sezione cuplotto
    * @param doc
    */
   void addCUPLOTTOtoFeedBack(FeedBackDocument doc){

      if(getCuplotto() == null) return;
      
      if(doc.getFeedBack().getAnomalieSchedeArray()!= null && doc.getFeedBack().getAnomalieSchedeArray().length> 0){
         // per ogni sezione anomalie presente nel feedback integro i dati restituiti dai validatori se ne esistono
         for(AnomalieSchede anomalia : doc.getFeedBack().getAnomalieSchedeArray()){
            for(CUPLOTTO item : this.getCuplotto()){
               if(item!= null && anomalia.getCIG().equals(item.getCIG())
                     && anomalia.getCUI() != null && anomalia.getCUI().length()>0){
                  if(!anomalia.isSetCUPLOTTO())
                     anomalia.addNewCUPLOTTO();

                  it.avlp.simog.massload.xmlbeans.CUPLOTTOType currCUPLOTTO = anomalia.getCUPLOTTO();

                  currCUPLOTTO.setCIG(anomalia.getCIG());
                  
                  for(CodiciCup item1 : item.getCODICICUP()){
                     DatiCUPType dati = currCUPLOTTO.addNewCODICICUP();
                     dati.setCUP(item1.getCUP());
                     dati.setDATIDIPE(item1.getDATI_DIPE());
                     dati.setOKUTENTE(FlagSNType.Enum.forString(item1.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : item1.getOK_UTENTE()));
                     dati.setVALIDO(FlagSNType.Enum.forString(item1.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item1.getVALIDO()));
                  }
               }
            }
         }
      }
   }
}

