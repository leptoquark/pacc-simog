package it.avcp.avcpass;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.common.action.BaseSharedAction;
import it.avlp.simog.common.comparator.AggiudicatariDuplicatiComparator;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.dbToXml.XmlBeanManager;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType;
import it.avlp.simog.massload.xmlbeans.AppaltoType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaDocument;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.SottoEsclusoType;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.DummyValidator;
import it.avlp.simog.ws.massload.xmlbeans.AdesioneType;
import it.avlp.simog.ws.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.ws.massload.xmlbeans.ArtEsclusioneType;
import it.avlp.simog.ws.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.ws.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.ws.massload.xmlbeans.FlagSNType;
import it.avlp.simog.ws.massload.xmlbeans.LuogoIstatType;
import it.avlp.simog.ws.massload.xmlbeans.LuogoNutsType;
import it.avlp.simog.ws.massload.xmlbeans.ModoRealizzazioneType;
import it.avlp.simog.ws.massload.xmlbeans.SceltaContraenteType;
import it.avlp.simog.ws.massload.xmlbeans.SchedaEsclusoType;
import it.avlp.simog.ws.massload.xmlbeans.TrasferimentoDati;
import it.eng.avcp.avcpass.service.operation.impl.DatiConsultaStatoCIG;
import it.eng.avcp.avcpass.service.operation.impl.GestioneSimogServiceBeanProxy;
import it.eng.avcp.avcpass.service.operation.impl.MessaggioAVCpass;
import it.eng.avcp.avcpass.service.operation.impl.RisultatoConsultaStatoCIG;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 * @author ppientini
 *
 */
public class AVCPassAction extends BaseSharedAction {

   // La competenza sui lotti di AVCPass inizia dallo stato "COMMISSIONE"  
   // e termina allo stato "AGGIUDICATO". Durante il periodo di competenza 
   // non sarà consentita alcuna funzione di aggiornamento su SIMOG, comprese quelle del contact center.
   
   private SimogProperties config;
   private AVCPassStatus lastStatus;
   
	public AVCPassAction(Connection activeConnection, Logger logger, SimogProperties conf) {
		super(activeConnection, logger);
		this.config = conf;
	}
	
	/**
	 * @param gara
	 * @param listaLotti
	 * @return
	 * @throws Exception
	 */
	private Boolean getSemaforo(Gara gara, List<Lotto> listaLotti) throws Exception{
	   return getSemaforo(gara, listaLotti, null);
	}
	
	/**
	 * @param gara
	 * @param listaLotti
	 * @param codiceFunzione
	 * @return
	 * @throws Exception
	 */
	private Boolean getSemaforo(Gara gara, List<Lotto> listaLotti, String codiceFunzione) throws Exception{

	  // host non definito, ritorno false;
	  DummyValidator dum = new DummyValidator(this.connection, this.logger);
	
	  if(config.getWS_AVCPASS_HOST() == null)
	     return false;
	
      try {
         GestioneSimogServiceBeanProxy cli = new GestioneSimogServiceBeanProxy(config.getWS_AVCPASS_HOST());
         
         RisultatoConsultaStatoCIG resp = null;
         
         if(SimogFlags.is3030_RFWEBGL02Active()){
         
            resp = cli.consultaStatoCIG(listaLotti == null ? null : listaLotti.get(0).getFullCIG(), 
                                       gara == null ? null : String.valueOf(gara.getId_Gara()).trim(), config.getSIMOG_IDENTIFIER(), codiceFunzione);

         } else {
            
            resp = cli.consultaStatoCIG(listaLotti == null ? null : listaLotti.get(0).getFullCIG(), 
                                       gara == null ? null : String.valueOf(gara.getId_Gara()).trim(), config.getSIMOG_IDENTIFIER());
            
            
         }
         // lista di esiti... uno per ogni cig passato?
         MessaggioAVCpass[] esiti = resp.getEsito();

         if (esiti != null) {
            for (MessaggioAVCpass esitoCorrente : esiti) {
               logger.debug("AVCPASS:consultaStatoCIG: [" + esitoCorrente.getCodice() + "] " + esitoCorrente.getDescrizione());
            }
            
            // analisi degli esiti
            for (MessaggioAVCpass esitoCorrente : esiti) {
               AVCPassEsiti esito = AVCPassEsiti.getEnumBycodice(esitoCorrente.getCodice());
               
               if(esito != null && esito.getSemaforo().getCodice().equals(AVCPassSemaforo.ROSSO.getCodice())){
                  // esito che blocca l'elemento
                  return true;
               }
            }
         }

         // elenco CIG ... tutti, quelli solo AVCPass ?
         DatiConsultaStatoCIG[] elencoCIG = resp.getElencoCIG();

         if (elencoCIG != null) {
            // se almeno un CIG è ROSSO per me è tutto ROSSO
            for (int i = 0; i < elencoCIG.length; i++) {

               this.lastStatus = AVCPassStatus.getEnumBycodice(String.valueOf(elencoCIG[i].getIdStato()));
               
               if (lastStatus != null && AVCPassSemaforo.ROSSO.getCodice().equals(lastStatus.getSemaforo().codice())) {
                  return true;
               }
               
            }
         }
         return false;
      } catch (Exception e) {
         logger.fatal(e.getMessage());
         e.printStackTrace();
         return true;
      }
	}
	
   /*
    * verifica se la gara è in gestione di AVCPASS
    */
   public boolean isAVCPass(Gara gara, List<Lotto> lotti, String codiceFunzione) throws Exception{
      boolean retVal = false;
      /*     CHECK BLOCCO AVCPASS   */
      if(SimogFlags.is3028_RFWEBGL07Active()){
         return this.getSemaforo(gara, lotti, codiceFunzione);
      }
      
      return retVal;
   }
   
   
   /**
    * Proteggere i dati provenienti da AVCPass rimpiazzandoli nelle schede provenienti dal massloader.
    * Rimpiazzare i dati solo vanno a modificare schede bloccate da AVCPass. 
    * @param schedeArray
    * @return
    * @throws Exception
    */
   public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[] replaceDatiProtettiAVCPass(it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati root) throws Exception{


      /*
       * Struttura che contiene le anagrafiche protette + le anagrafiche nuove
       * Non consente duplicati (per codice fiscale e codice stato)
       */
      TreeSet<AggiudicatarioType> anagAggiudicatari = new TreeSet<AggiudicatarioType>(new AggiudicatariDuplicatiComparator());
      
      XmlBeanManager xmlMan = new XmlBeanManager(connection, logger, null, Costanti.VERS_MAX);
      
      final String CODE_AVCPASS = String.valueOf(OrigineSchedaEnum.AVCPASS.code());
      
      for(it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType item: root.getSchedeArray())
      {
         String cig = item.getDatiComuni().getCIG();
       
         xmlMan.setCig(cig);
         
         SchedaDocument schedaLocale = null;
         try {
            schedaLocale = xmlMan.getXmlBean();
         } catch (Exception e) {
            // per qualsiasi errore esco senza fare nulla, l'eccezione è tracciata prima!
            if (e.getMessage().contains("SIMOG"))
               logger.debug("Eccezione in replaceDatiAVCPASS per cig : " + cig + " msg: " + e.getMessage());
            else
               logger.error("Eccezione in replaceDatiAVCPASS per cig : " + cig + " msg: " + e.getMessage());
            //e.printStackTrace();
            continue;
         }
         
         // Se sul DB non ci sono schede relative al CIG corrente, ignora la protezione AVCPass
         if( !schedaLocale.getScheda().isSetDatiScheda() ) 
            continue;
         
         it.avlp.simog.massload.xmlbeans.DatiComuniType datiComuniLocal = schedaLocale.getScheda().getDatiScheda().getDatiComuni();
         
         if( CODE_AVCPASS.equals(datiComuniLocal.getORIGINESCHEDA().toString()) ){
            
            item.getDatiComuni().setFLAGENTESPECIALE( datiComuniLocal.getFLAGENTESPECIALE() );
            item.getDatiComuni().setTIPOCONTRATTO( datiComuniLocal.getTIPOCONTRATTO() );
            item.getDatiComuni().setMODOREALIZZAZIONE( datiComuniLocal.getMODOREALIZZAZIONE() );
            item.getDatiComuni().setFLAGESCLUSO( datiComuniLocal.getFLAGESCLUSO() );
            item.getDatiComuni().setIDESCLUSIONE( datiComuniLocal.getIDESCLUSIONE() );
            item.getDatiComuni().setESITOPROCEDURA( datiComuniLocal.getESITOPROCEDURA() );
            
            // PP
            item.getDatiComuni().setCFRUP(datiComuniLocal.getCFRUP());
            
            //La scheda deve rimanere bloccata da AVCPass
            //item.getDatiComuni().setORIGINESCHEDA( datiComuniLocal.getORIGINESCHEDA() );
         }

         SoggAggiudicatarioType[] listaSoggettiCorrente = new SoggAggiudicatarioType[0];
         
         for(SchedaCompletaType schedaA: item.getSchedaCompletaArray() )
         {
            String cui = schedaA.getCUI(); 
            if( !cui.isEmpty()  ){
               
               SchedaCompletaType schedaAlocale = 
                     getSchedaCompletaByCUI(schedaLocale.getScheda().getDatiScheda().getSchedaCompletaArray(), cui);
                
               if( isFromAVCPass(schedaAlocale, CODE_AVCPASS) ){

                  boolean proteggiAggiudicazione = schedaA.getAggiudicazione() != null && schedaAlocale.getAggiudicazione() != null;
                  boolean proteggiAdesione = schedaA.getAdesione() != null && schedaAlocale.getAdesione() != null;
                  boolean proteggiSottosoglia = schedaA.getSottosoglia() != null && schedaAlocale.getSottosoglia() != null;
                  boolean proteggiEscluso = schedaA.getEscluso() != null && schedaAlocale.getEscluso() != null;
                  
                  if( proteggiAggiudicazione ){
                     AppaltoType appalto = schedaA.getAggiudicazione().getAppalto();
                     appalto.setDATASCADENZAPRESOFFERTA( schedaAlocale.getAggiudicazione().getAppalto().getDATASCADENZAPRESOFFERTA() );
                     appalto.setNUMIMPRESEINVITATE( schedaAlocale.getAggiudicazione().getAppalto().getNUMIMPRESEINVITATE() );
                     appalto.setNUMIMPRESEOFFERENTI( schedaAlocale.getAggiudicazione().getAppalto().getNUMIMPRESEOFFERENTI() );
                     appalto.setNUMOFFERTEAMMESSE( schedaAlocale.getAggiudicazione().getAppalto().getNUMOFFERTEAMMESSE() );
                     appalto.setOFFERTAMASSIMO( ifNull(schedaAlocale.getAggiudicazione().getAppalto().getOFFERTAMASSIMO(), new BigDecimal(0)) );
                     appalto.setOFFERTAMINIMA( ifNull(schedaAlocale.getAggiudicazione().getAppalto().getOFFERTAMINIMA(), new BigDecimal(0)) );
                     appalto.setPERCRIBASSOAGG( ifNull(schedaAlocale.getAggiudicazione().getAppalto().getPERCRIBASSOAGG(), new BigDecimal(0)) );
                     appalto.setPERCOFFAUMENTO( ifNull(schedaAlocale.getAggiudicazione().getAppalto().getPERCOFFAUMENTO(), new BigDecimal(0)) );
                     appalto.setDATAVERBAGGIUDICAZIONE( schedaAlocale.getAggiudicazione().getAppalto().getDATAVERBAGGIUDICAZIONE() );
                     //la scheda deve rimanere bloccata da AVCPass
                     //appalto.setORIGINESCHEDA( schedaAlocale.getAggiudicazione().getAppalto().getORIGINESCHEDA() );
                     
                     schedaA.getAggiudicazione().setAggiudicatariArray( schedaAlocale.getAggiudicazione().getAggiudicatariArray() );
                     listaSoggettiCorrente = schedaAlocale.getAggiudicazione().getAggiudicatariArray();
                  }
                  else if( proteggiSottosoglia ){
                     SottoEsclusoType appalto = schedaA.getSottosoglia().getAppalto();
                     //appalto.setIMPORTOCOMPLESSIVO( schedaAlocale.getSottosoglia().getAppalto().getIMPORTOCOMPLESSIVO() );
                     appalto.setPERCRIBASSOAGG( ifNull(schedaAlocale.getSottosoglia().getAppalto().getPERCRIBASSOAGG(), new BigDecimal(0)) );
                     appalto.setPERCOFFAUMENTO( ifNull(schedaAlocale.getSottosoglia().getAppalto().getPERCOFFAUMENTO(), new BigDecimal(0)) );
                     // appalto.setIMPORTOAGGIUDICAZIONE( schedaAlocale.getSottosoglia().getAppalto().getIMPORTOAGGIUDICAZIONE() );
                     appalto.setDATAAGGIUDICAZIONE( schedaAlocale.getSottosoglia().getAppalto().getDATAAGGIUDICAZIONE() );
                     //la scheda deve rimanere bloccata da AVCPass
                     //appalto.setORIGINESCHEDA( schedaAlocale.getSottosoglia().getAppalto().getORIGINESCHEDA() );  
                     
                     schedaA.getSottosoglia().setAggiudicatariArray( schedaAlocale.getSottosoglia().getAggiudicatariArray() );
                     listaSoggettiCorrente = schedaAlocale.getSottosoglia().getAggiudicatariArray();
                  } 
                  else if( proteggiEscluso ){
                     SottoEsclusoType appalto = schedaA.getEscluso().getAppalto();
                     //appalto.setIMPORTOCOMPLESSIVO( schedaAlocale.getEscluso().getAppalto().getIMPORTOCOMPLESSIVO() );
                     appalto.setPERCRIBASSOAGG( ifNull(schedaAlocale.getEscluso().getAppalto().getPERCRIBASSOAGG(), new BigDecimal(0)) );
                     appalto.setPERCOFFAUMENTO( ifNull(schedaAlocale.getEscluso().getAppalto().getPERCOFFAUMENTO(), new BigDecimal(0)) );
                     // appalto.setIMPORTOAGGIUDICAZIONE( schedaAlocale.getEscluso().getAppalto().getIMPORTOAGGIUDICAZIONE() );
                     appalto.setDATAAGGIUDICAZIONE( schedaAlocale.getEscluso().getAppalto().getDATAAGGIUDICAZIONE() );
                     //la scheda deve rimanere bloccata da AVCPass
                     //appalto.setORIGINESCHEDA( schedaAlocale.getEscluso().getAppalto().getORIGINESCHEDA() );
                     
                     schedaA.getEscluso().setAggiudicatariArray( schedaAlocale.getEscluso().getAggiudicatariArray() );
                     listaSoggettiCorrente = schedaAlocale.getEscluso().getAggiudicatariArray();
                  }
                  else if( proteggiAdesione ){
                     AppaltoAdesioneType appalto = schedaA.getAdesione().getAppalto();
                     appalto.setPERCRIBASSOAGG( ifNull(schedaAlocale.getAdesione().getAppalto().getPERCRIBASSOAGG(), new BigDecimal(0)) );
                     appalto.setPERCOFFAUMENTO( ifNull(schedaAlocale.getAdesione().getAppalto().getPERCOFFAUMENTO(), new BigDecimal(0)) );
                     appalto.setDATAAGGIUDICAZIONE(schedaAlocale.getAdesione().getAppalto().getDATAAGGIUDICAZIONE() );
                     //appalto.setIMPORTOAGGIUDICAZIONE( schedaAlocale.getAdesione().getAppalto().getIMPORTOAGGIUDICAZIONE() );
                     
                     //la scheda deve rimanere bloccata da AVCPass
                     //appalto.setORIGINESCHEDA( schedaAlocale.getAggiudicazione().getAppalto().getORIGINESCHEDA() );
                     
                     schedaA.getAdesione().setAggiudicatariArray( schedaAlocale.getAggiudicazione().getAggiudicatariArray() );
                     listaSoggettiCorrente = schedaAlocale.getAdesione().getAggiudicatariArray();
                  }
               }
            }
         }
         
         /*
          * Conserviamo le anagrafiche da proteggere
          */
         if(schedaLocale.getScheda().getAggiudicatari() != null){
            AggiudicatarioType[] listaAnagrafiche = schedaLocale.getScheda().getAggiudicatari().getAggiudicatarioArray();          
            anagAggiudicatari.addAll( getListaAnagraficheProtette(listaSoggettiCorrente, listaAnagrafiche) );
         }
      }
      //Aggiungere le anagrafiche nuove a quelle protette
      if(root.getAggiudicatari() != null){
         anagAggiudicatari.addAll( Arrays.asList(root.getAggiudicatari().getAggiudicatarioArray()) );
         root.getAggiudicatari().setAggiudicatarioArray( anagAggiudicatari.toArray(new AggiudicatarioType[0]) );
      }
      return root.getSchedeArray();
   }
   
   /**
    * Integro i dati mancanti in AVCPASS
    * @param schedeArray
    * @return
    * @throws Exception
    */
   public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[] integraDatiAVCPass(it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati root) throws Exception{

      
      XmlBeanManager xmlMan = new XmlBeanManager(connection, logger, null, Costanti.VERS_MAX);
      
      final String CODE_AVCPASS = String.valueOf(OrigineSchedaEnum.AVCPASS.code());
      
      for(it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType item: root.getSchedeArray())
      {
         String cig = item.getDatiComuni().getCIG();
       
         xmlMan.setCig(cig);
         
         SchedaDocument schedaLocale = xmlMan.getXmlBean();
                          
         if (schedaLocale != null && schedaLocale.getScheda() != null){
            item.getDatiComuni().setFLAGENTESPECIALE( schedaLocale.getScheda().getDatiGara().getGara().getTIPOSCHEDA() );
            item.getDatiComuni().setMODOREALIZZAZIONE( schedaLocale.getScheda().getDatiGara().getGara().getMODOREALIZZAZIONE() );
         }
         
         // se manca, aggiungo la sezione pubblicazione necessaria per le adesioni
         if (!item.isSetPubblicazione() 
               && item.getSchedaCompletaArray() != null
               && item.getSchedaCompletaArray().length > 0
               && item.getSchedaCompletaArray(0).isSetAdesione()){
            item.addNewPubblicazione();
            item.getPubblicazione().setFLAGBENICULT(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
            item.getPubblicazione().setPROFILOCOMMITTENTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
            item.getPubblicazione().setSITOMINISTEROINFTRASP(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
            item.getPubblicazione().setSITOOSSERVATORIOCP(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
         }
      }
      return root.getSchedeArray();
   }

   /**
    * Restituisce la lista delle anagrafiche da proteggere ossia le anagrafiche relative agli aggiudicatari 
    * di una aggudicazione protetta da AVCPASS
    * @param listaSoggetti
    * @param listaAnagrafiche
    * @return
    */
   private List<AggiudicatarioType> getListaAnagraficheProtette(SoggAggiudicatarioType[] listaSoggetti, AggiudicatarioType[] listaAnagrafiche){
      List<AggiudicatarioType> listaAnagraficheProtette = new ArrayList<AggiudicatarioType>();
      List<String> elencoSoggettiProtetti = new ArrayList<String>();
      for(SoggAggiudicatarioType item: listaSoggetti){
         elencoSoggettiProtetti.add(item.getCODICESTATO() + "_" + item.getCODICEFISCALEAGGIUDICATARIO());
      }
      for(AggiudicatarioType item: listaAnagrafiche){
         String token = item.getCODICESTATO() + "_" + item.getCODICEFISCALEAGGIUDICATARIO();
         if( elencoSoggettiProtetti.contains(token) ){
            listaAnagraficheProtette.add(item);
         }
      }
      return listaAnagraficheProtette;
   }
   
   /* Utility */
   private BigDecimal ifNull(BigDecimal campo, BigDecimal value){ return campo == null ? value : campo; }
   
   /**
    * Verifica se l'aggiudicazione, indipedentemente dalla tipolgia, è protetto da AVCPass
    * @param schedaA
    * @param CODE_AVCPASS
    * @return
    */
   private boolean isFromAVCPass(SchedaCompletaType schedaA, String CODE_AVCPASS) {

      boolean isAvcPass = false;
      
      if(schedaA != null){
         if(schedaA.getAggiudicazione() != null 
               && schedaA.getAggiudicazione().getAppalto() != null
               && schedaA.getAggiudicazione().getAppalto().getORIGINESCHEDA() != null
               && CODE_AVCPASS.equals(schedaA.getAggiudicazione().getAppalto().getORIGINESCHEDA().toString())){
            isAvcPass = true;
         }
         if(schedaA.getSottosoglia() != null 
               && schedaA.getSottosoglia().getAppalto() != null
               && schedaA.getSottosoglia().getAppalto().getORIGINESCHEDA() != null
               && CODE_AVCPASS.equals(schedaA.getSottosoglia().getAppalto().getORIGINESCHEDA().toString())){
            isAvcPass = true;
         }
         if(schedaA.getAdesione() != null 
               && schedaA.getAdesione().getAppalto() != null
               && schedaA.getAdesione().getAppalto().getORIGINESCHEDA() != null
               && CODE_AVCPASS.equals(schedaA.getAdesione().getAppalto().getORIGINESCHEDA().toString())){
            isAvcPass = true;
         }
         if(schedaA.getEscluso() != null 
               && schedaA.getEscluso().getAppalto() != null
               && schedaA.getEscluso().getAppalto().getORIGINESCHEDA() != null
               && CODE_AVCPASS.equals(schedaA.getEscluso().getAppalto().getORIGINESCHEDA().toString())){
            isAvcPass = true;
         }
      }
      
      return isAvcPass;
   }

   /**
    * Restituisce la scheda corrispondente al CUI specificato
    * @param schede
    * @param cui
    * @return
    */
   private SchedaCompletaType getSchedaCompletaByCUI(SchedaCompletaType[] schede, String cui){
      SchedaCompletaType target = null;
      for(SchedaCompletaType item: schede){
         if(cui.equals(item.getCUI())){
            target = item;
            break;
         }
      }
      return target;
   }   
   
   
    /** integra i dati mancanti da AVCPAS prendendoli da gara e lotto o default prestabiliti
    * @param root
    * @throws Exception
    */
   public void integraDatiAVCPASS(TrasferimentoDati root) throws Exception{
  
      GaraManager gm = new GaraManager(connection, logger);
      LottoManager lm = new LottoManager(connection, logger);
      
      List<DatiAggiudicazioneType> schede = root.getSchede();
      List<Lotto> lotto = null;
      Gara gara = null;
            
      //soprasoglia/esclusi/adesioni
      if (schede != null  && !schede.isEmpty()){
         DatiComuniType dc = schede.get(0).getDatiComuni();
         
         if(dc != null){
            String cig = dc.getCIG().getValue();
            
            lotto = lm.getLottoByCigWS(cig);
            
            if(lotto != null && !lotto.isEmpty()){
               
               gara = gm.getGara(lotto.get(0).getId_Gara());
               
               if(dc.getMODOREALIZZAZIONE() == null){
                  ModoRealizzazioneType val = new ModoRealizzazioneType();
                  val.setValue(String.valueOf(gara.getID_MODO_REAL()));
                  dc.setMODOREALIZZAZIONE(val);
               }
               
               if(dc.getFLAGESCLUSO() == null)
                  dc.setFLAGESCLUSO(FlagSNType.valueOf(lotto.get(0).getFLAG_ESCLUSO()));
                  
               if(dc.getIDESCLUSIONE() == null){
                  ArtEsclusioneType val = new ArtEsclusioneType();
                  val.setValue(String.valueOf(lotto.get(0).getID_ESCLUSIONE()));
                  dc.setIDESCLUSIONE(val);
               }
            }
         }
         
         List<it.avlp.simog.ws.massload.xmlbeans.SchedaCompletaType> schedaCompleta = schede.get(0).getSchedaCompleta();
         
         if(schedaCompleta != null){
            AggiudicazioneType agg = schedaCompleta.get(0).getAggiudicazione();
            if(agg != null) {
               
               if (agg.getAppalto().getLUOGOISTAT() == null && lotto.get(0).getLUOGO_ISTAT() != null){
                  LuogoIstatType li = new LuogoIstatType();
                  li.setValue(lotto.get(0).getLUOGO_ISTAT());
                  
                  agg.getAppalto().setLUOGOISTAT(li);
               }
               
               if (agg.getAppalto().getLUOGONUTS() == null && lotto.get(0).getLUOGO_NUTS() != null){
                  LuogoNutsType nu = new LuogoNutsType();
                  nu.setValue(lotto.get(0).getLUOGO_NUTS());
                  
                  agg.getAppalto().setLUOGONUTS(nu);      
               }
               
               if (agg.getAppalto().getIDSCELTACONTRAENTE() == null){
                  SceltaContraenteType sc = new SceltaContraenteType();
                  sc.setValue(lotto.get(0).getId_Scelta_Contraente());
                  
                  agg.getAppalto().setIDSCELTACONTRAENTE(sc);
               }
            }
            
            SchedaEsclusoType esc = schedaCompleta.get(0).getEscluso();
            
            if(esc != null) {
               
               if (esc.getAppalto().getLUOGOISTAT() == null && lotto.get(0).getLUOGO_ISTAT() != null){
                  LuogoIstatType li = new LuogoIstatType();
                  li.setValue(lotto.get(0).getLUOGO_ISTAT());
                  
                  esc.getAppalto().setLUOGOISTAT(li);
               }
               
               if (esc.getAppalto().getLUOGONUTS() == null && lotto.get(0).getLUOGO_NUTS() != null){
                  LuogoNutsType nu = new LuogoNutsType();
                  nu.setValue(lotto.get(0).getLUOGO_NUTS());
                  
                  esc.getAppalto().setLUOGONUTS(nu);      
               }
               
               if (esc.getAppalto().getIDSCELTACONTRAENTE() == null){
                  SceltaContraenteType sc = new SceltaContraenteType();
                  sc.setValue(lotto.get(0).getId_Scelta_Contraente());
                  
                  esc.getAppalto().setIDSCELTACONTRAENTE(sc);
               }
               
               /**-- simog:DATA_STIPULA="2001-12-31T12:00:00" 
                     simog:DURATA_CONTRATTUALE="0"
                     simog:TERMINE_CONTRATTUALE="2001-12-31T12:00:00" 
                     simog:IMPORTO_ATTUAZIONE_SICUREZZA="0.0"
               **/ 
            }

            AdesioneType ade = schedaCompleta.get(0).getAdesione();
            
            if(ade != null) {
               
               if (ade.getAppalto().getLUOGOISTAT() == null && lotto.get(0).getLUOGO_ISTAT() != null){
                  LuogoIstatType li = new LuogoIstatType();
                  li.setValue(lotto.get(0).getLUOGO_ISTAT());
                  
                  ade.getAppalto().setLUOGOISTAT(li);
               }
               
               if (ade.getAppalto().getLUOGONUTS() == null && lotto.get(0).getLUOGO_NUTS() != null){
                  LuogoNutsType nu = new LuogoNutsType();
                  nu.setValue(lotto.get(0).getLUOGO_NUTS());
                  
                  ade.getAppalto().setLUOGONUTS(nu);      
               }
            }
         }
      }
   }


   public AVCPassStatus getLastStatus() {
      return lastStatus;
   }
   
}
