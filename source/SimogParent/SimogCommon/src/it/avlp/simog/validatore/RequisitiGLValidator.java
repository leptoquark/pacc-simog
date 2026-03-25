package it.avlp.simog.validatore;


import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.RequisitoGara.Documento;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.comparators.RequisitoGaraDuplicatiAssLottiComparator;
import it.avlp.simog.beans.comparators.RequisitoGaraDuplicatiAttributiComparator;
import it.avlp.simog.beans.comparators.RequisitoGaraDuplicatiPerCodiceComparator;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.servlet.PSReq;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.RequisitiConfigFile;
import it.avlp.simog.util.RequisitiConfigFile.ReqConfig;

public class RequisitiGLValidator extends SimogValidator {
   
   private EsitoValidaReq esito;
   
   public RequisitiGLValidator(Connection connection, Logger logger) {
      super(connection, logger);
      
      this.esito = new EsitoValidaReq();
   }
   
   /*
    * Metodo ignorato in quanto non risponde alle esigenze di validazione richieste
    */
   @Override
   public boolean valida(Object bean, String section) {
      return false;
   }
   
   /**
    * Validatore della lista di requisiti di una gara
    * @param idGara
    * @param listaRequisitiGara
    * @param dataRiferimento
    * @return boolean
    */
	public boolean valida(long idGara, List<RequisitoGara> listaRequisitiGara, Timestamp dataRiferimento)
			throws Exception {
      
      Map<String, String> mappaRequisiti = null;
      Map<String, String> mappaTipoDocumenti = null;
      List<String> listaCodiciDocumentiFonteOE = null;
      
      try{
         
         RequisitiGLManager requisitiGLManager = new RequisitiGLManager(connection, logger);
         mappaRequisiti = requisitiGLManager.getRequisitiMap(dataRiferimento);
         mappaTipoDocumenti = requisitiGLManager.getCodiceDocMap(dataRiferimento);
         listaCodiciDocumentiFonteOE = requisitiGLManager.getCodiceDocumentiFonteOEList(dataRiferimento);
         
      }catch(Exception e){
         logger.error("Non e' stato possibile caricare la mappa dei requisiti", e);
         throw e;
      }

      
if( SimogFlags.is3028_RFWEBGL06Active() ){   

      /*
       * Verifica che due requisti uguali (stesso codice e descrizione) 
       * siano associati a gruppi di lotti differenti.
       * In caso contrario scarta l'intera lista di requisiti
       */
			boolean esisteDuplicato = esisteDuplicazione(listaRequisitiGara,
					new RequisitoGaraDuplicatiAssLottiComparator());
      
      /*
       * Verifica l'esistenza di due requisti uguali 
       * (tutti gli attributi uguali a meno di lotti e documenti) 
       * In tal caso scarta l'intera lista di requisiti
       */
      if( !esisteDuplicato ){
         esisteDuplicazione(listaRequisitiGara, new RequisitoGaraDuplicatiAttributiComparator());
      }

} else {
   
      /*
       * Verifica che ci siano requisiti identici (tutti campi uguali)
       * In tal caso scarta l'intera lista di requisiti
       */
      List<RequisitoGara> listaRequisitiGaraFake = getFakeList(listaRequisitiGara);
      List<RequisitoGara> listaRequisitiApp = new LinkedList<RequisitoGara>();
      int index = 0;
      for(RequisitoGara currentRequisito: listaRequisitiGaraFake){
         index++;
         if( !listaRequisitiApp.contains(currentRequisito) ){
            listaRequisitiApp.add(currentRequisito);
         } else {
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Requisito"),
							index);
         }
      }
}

      /*
       * Validazione dei singoli requisiti
       */
      int indexElemento = 0;
      for(RequisitoGara currentRequisito: listaRequisitiGara){
         indexElemento++;
         validaRequisito(currentRequisito, mappaRequisiti, listaCodiciDocumentiFonteOE, indexElemento);
         
         for(Long currCIG: currentRequisito.getLotti_associati()){
            validaCIG(currCIG, indexElemento);
         }
         
         
         /*
          * Verifica che ci siano documenti identici
          * In tal caso scarta l'intera lista di requisiti
          */
         List<Documento> listaDocumenti = new LinkedList<Documento>(currentRequisito.getDocumenti());
         List<Documento> listaDocumentiApp = new LinkedList<Documento>();
         String message = "Requisito (riga " + indexElemento + ") Documenti -> ";
         int i = 0;
         for(Documento documento: listaDocumenti){
            i++;
            if( !listaDocumentiApp.contains(documento) ){
               listaDocumentiApp.add(documento);
            } else {
               mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", message), i);
            }
         }
         
         int indexDoc = 0;
         for(Documento currentDocumento: currentRequisito.getDocumenti()){
            indexDoc++;
            
            validaDocumento(currentDocumento, mappaTipoDocumenti, indexDoc, message);
         }
      }
      
      return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
   }
   

      /**
       * Valida il singolo cig
       * @param requisitoGara
       * @param mappaRequisiti
       */
      private void validaCIG(Long idLotto, int indexElemento){

		// il lotto indicato non esiste (i ws impostano a zero se fallisce la ricerca
		// durante la conversione)
         if( idLotto <= 0){
            mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_008, indexElemento);
         }
      }
      
   /**
    * Valida il singolo requisito
    * @param requisitoGara
    * @param mappaRequisiti
    */
	private void validaRequisito(RequisitoGara requisitoGara, Map<String, String> mappaRequisiti,
			List<String> listaCodiciDocumentiFonteOE, int indexElemento) {
      /*
       * Validazione campo "Requisito"
       */

         String codiceRequisito = String.valueOf(requisitoGara.getCodice_dettaglio());
         //MAC 37334 3.04.8.1 
         //rendiamo possibile inviare come codice requisito 999 nella request
         if(codiceRequisito.equals( PSReq.CODICE_REQUISITO_NON_CODIFICATO)) {
        	 codiceRequisito = "33";
         }
         //FINE MAC
       
         //Codice requisito esistente
         if( codiceRequisito == null || !mappaRequisiti.containsKey(codiceRequisito) ){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Requisito"),
					indexElemento);
         }
         
         //Requisito non codificato
         if( PSReq.CODICE_REQUISITO_NON_CODIFICATO.equals(requisitoGara.getCodice()) ){
            if( isStringEmptyValue(requisitoGara.getDescrizione()) ){
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Descrizione"),
						indexElemento);
            }
         }
         
         
      /*
       * Validazione FLAG
       */
         
         if( !isFlag( requisitoGara.getFlag_esclusione() ) ){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Eslusione"),
					indexElemento);
         }
 
         if( !isFlag( requisitoGara.getFlag_comprova_offerta() ) ){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Comprova offerta"),
					indexElemento);
         }
         
         if( !isFlag( requisitoGara.getFlag_avvalimento()) ){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Avvalimento"),
					indexElemento);
         }
         
         if( !isFlag( requisitoGara.getFlag_bando_tipo() ) ){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Bando tipo"),
					indexElemento);
         }
         
         if( !isFlag( requisitoGara.getFlag_riservatezza()) ){ 
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Riservatezza"),
					indexElemento);
         }
         
         
      /*
       * Vincolo comprova offerta
       */
         // PP 30.05.2013 commentato su richiesta di Pizziconi
//         if( isYFlag( requisitoGara.getFlag_comprova_offerta() ) ){
//            for(Documento documento: requisitoGara.getDocumenti() ){
//               if( !listaCodiciDocumentiFonteOE.contains(documento.getCodice()) ){
//                  mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_217, indexElemento);
//                  break;
//               }
//            }
//         }
         
     }
     
   
   /**
    * Valida il singolo documento
    * @param documento
    */
	private void validaDocumento(Documento documento, Map<String, String> mappaTipoDocumenti, int indexElemento,
			String message) {
      
	   try {
	   
      String codiceDocumento = documento.getCodice();
      
	      //Codice documento esistente
	      if( codiceDocumento == null || !mappaTipoDocumenti.containsKey(codiceDocumento) ){
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", message + "Tipo Documento"), indexElemento);
	      }
	      
	      //Codice non codificato
	      if( PSReq.CODICE_DOCUMENTO_NON_CODIFICATO.equals(codiceDocumento) ){
	         if( isStringEmptyValue(documento.getDescrizione_documento()) ){
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", message + "Descrizione"), indexElemento);
	         }
	         if( isStringEmptyValue(documento.getEmettitore()) ){
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", message + "Emettitore"), indexElemento);
	         }         
	         if( isStringEmptyValue(documento.getTelefono()) ){
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", message + "Telefono"), indexElemento);
	         }        
	         if( !isNumber(documento.getTelefono()) ){
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", message + "Telefono"), indexElemento);
	         }
	         if( isStringEmptyValue(documento.getFax()) ){
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", message + "Fax"),
							indexElemento);
	         }  
	         if( !isNumber(documento.getFax()) ){
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", message + "Fax"),
							indexElemento);
	         }
	         if( !isEmpty(documento.getMail()) && !isMail(documento.getMail()) ){
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", message + "Mail"),
							indexElemento);
	         }
	         if( !isEmpty(documento.getMail_pec()) && !isMail(documento.getMail_pec()) ){
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", message + "Mail Pec"), indexElemento);
	         }
	         
	      }
	   }catch(NoClassDefFoundError er) {//Ticket ALM #3159
		   mEccezioni.addValidationErrElemento(Messaggi.SIMOG_MASSLOADER_205+ " - Validazione Mail", indexElemento);
	   }catch(Exception e) {
		   mEccezioni.addValidationErrElemento(Messaggi.SIMOG_MASSLOADER_205+ " - Validazione Mail", indexElemento);
	   }
      
   }
   
   
   private List<RequisitoGara> getFakeList(List<RequisitoGara> lista){
      List<RequisitoGara> listaFake = new LinkedList<RequisitoGara>();
      for(RequisitoGara item: lista){
         listaFake.add( item.fakeCopy() );
      }
      return listaFake;
   }
   
   public class EsitoValidaReq {
      private boolean isErr;
      private boolean isWarn;
      private boolean isRequisiti;
      
      public boolean isErr() {
         return isErr;
      }
      public void setErr(boolean isErr) {
         this.isErr = isErr;
      }
      public boolean isWarn() {
         return isWarn;
      }
      public void setWarn(boolean isWarn) {
         this.isWarn = isWarn;
      }
      public boolean isRequisiti() {
         return isRequisiti;
      }
      public void setRequisiti(boolean isRequisiti) {
         this.isRequisiti = isRequisiti;
      }
   }
   
   /**
    * Validazione pubblicazione per operatori esterni
    * @param importo
    * @return
    */
   public boolean validaRequisitoOE(BigDecimal importo, String dataCreazioneGara) throws Exception{
	   List<ReqConfig> listaRequisitiOE =null;
	   String configDir="";
	   try{
		   
//		   Properties p = new Prop
		   // Ticket #20058 - 09 - 02 - 21
		   
		  listaRequisitiOE = new RequisitiConfigFile("/opt/SIMOG/req_config.csv").getReqConfigList(); 
//		  listaRequisitiOE = new RequisitiConfigFile("C:\\Users\\Fe.Lattanzi\\Documents\\SVN\\Configurazioni\\req_config.csv").getReqConfigList(); 
	   }catch (Exception e){
		   // Ticket #20058 - 09 - 02 - 21
		  /// listaRequisitiOE = new RequisitiConfigFile("C:\\Users\\diego.squillaci\\Documents\\Documenti_SIMOG\\FileNecessari\\req_config.csv").getReqConfigList();
	   }
		Timestamp dataCorrente = PageHelper.parseTimeYMD(dataCreazioneGara); // new AccessiDB(connection,
																				// logger).getNow();
      
      this.esito.setErr(false);
      this.esito.setWarn(false);
      this.esito.setRequisiti(false);
      
      for(ReqConfig currentRequisitoOE: listaRequisitiOE){
			if (!this.esito.isErr())
				this.esito.setErr(currentRequisitoOE.isError(importo, dataCorrente));
			if (!this.esito.isWarn())
				this.esito.setWarn(currentRequisitoOE.isWarning(importo, dataCorrente));
			if (!this.esito.isRequisiti())
				this.esito.setRequisiti(currentRequisitoOE.isRequisito(importo, dataCorrente));
      }

      if(this.esito.isErr() && this.esito.isWarn()){
			mEccezioni.addValidationField("label_Pubblicazione");
         mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_220);  
		} else if (this.esito.isErr() && !this.esito.isWarn()) {
         mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_219); 
      }

      return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
   }

   public BigDecimal getImportoGara(TableBean listaLotti){
      BigDecimal retVal = new BigDecimal(0);
      
      for (int i = 0; i < listaLotti.getRowsCount(); i++) {
         
         BigDecimal imp = new BigDecimal(listaLotti.getNulledField(LOTTO.IMPORTO_LOTTO, i));
         String canc = listaLotti.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO, i);
         // se non cancellato
         if ("".equals(canc)){
            if (imp.floatValue() == Costanti.IMPORTO_FUORI_SCALA){
               retVal = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
               break;
				} else
               retVal = retVal.add(imp);
         }
      }
      
      // se importo è zero equivale a indeterminato (gara ancora senza lotti)
      if(retVal.floatValue() == 0)
         retVal = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
      
      return retVal;
   }
   
   public EsitoValidaReq getEsito() {
      return esito;
   }

   public void setEsito(EsitoValidaReq esito) {
      this.esito = esito;
   }
   
   /**
    * Verifica l'esistenza di requisiti duplicati in base alle regole definite dal comparatore.
    * </br>Aggiunge errori di validazione. 
    * @param listaRequisitiGara
    * @param comparatore
    * @return Restiruisce <b>true</b> se esiste un duplicato, <b>false</b> altrimenti
    */
   private boolean esisteDuplicazione(List<RequisitoGara> listaRequisitiGara, Comparator<RequisitoGara> comparatore){
      TreeSet<RequisitoGara> requisitiGaraDuplicatiValidi = new TreeSet<RequisitoGara>(comparatore);
      int duplicati = 0;
      int index = 0;
      for(RequisitoGara currentRequisito: listaRequisitiGara){
         index++;
         if( !requisitiGaraDuplicatiValidi.contains(currentRequisito) ){
            requisitiGaraDuplicatiValidi.add(currentRequisito);
         } else {
            mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Requisito"), index);
            duplicati++;
         }
      } 
      return duplicati > 0; 
   }
   
   /**
    * Valida le operazioni sui requisiti in base alle direttive AVCPass
    * @param idGara
    * @param listaRequisitiGara
    * @param bloccoAdd
    * @param bloccoMod
    * @param bloccoDel
    * @return
    */
	public boolean validaAVCPassPrivileges(long idGara, List<RequisitoGara> listaRequisitiGara, boolean bloccoAdd,
			boolean bloccoMod, boolean bloccoDel) throws Exception {
      
      List<RequisitoGara> listaRequisitiGaraFromDB = null;
      
      try{
         
         RequisitiGLAction requisitiGLAction = new RequisitiGLAction(connection, logger);
         listaRequisitiGaraFromDB = requisitiGLAction.getRequisitoGaraList(idGara);
         
      }catch(Exception e){
         logger.error("Non e' stato possibile caricare la lista dei requisiti", e);
         throw e;
      }
      
      /* Verifica la presenza di requisiti aggiornati */
      List<RequisitoGara> listaAggiornati = new ArrayList<RequisitoGara>(listaRequisitiGara);
      listaAggiornati.removeAll(listaRequisitiGaraFromDB);
      
      /* Conta la presenza di requisiti aggiunti */
      int added = 0;
      for(RequisitoGara item: listaAggiornati){
         if( item.getCodice_requisito_gara() < 0 )
            added++;
      }
      
      /* Verifica la presenza di requisiti eliminati */
		Set<RequisitoGara> listaRequisitiGaraCancellati = new TreeSet<RequisitoGara>(
				new RequisitoGaraDuplicatiPerCodiceComparator());
      listaRequisitiGaraCancellati.addAll(listaRequisitiGaraFromDB);
      for(RequisitoGara item: listaRequisitiGara){
         if( listaRequisitiGaraCancellati.contains(item) ){
            listaRequisitiGaraCancellati.remove(item);
         }
      }
      
      boolean isUpdate = (listaAggiornati.size() - added) > 0;
      boolean isAdding = added > 0; 
      boolean isDelete = listaRequisitiGaraCancellati.size() > 0;
      
      if( (bloccoMod && isUpdate)
       || (bloccoAdd && isAdding)
       || (bloccoDel && isDelete))
      {
         String msgOperazioni = " Operazioni non consentite:";
         if( bloccoMod ) msgOperazioni += " rettifica dei requisiti,";
         if( bloccoAdd ) msgOperazioni += " aggiunta,";
         if( bloccoDel ) msgOperazioni += " cancellazione,";
         msgOperazioni = msgOperazioni.substring(0, msgOperazioni.length()-1);
         
			mEccezioni.addValidationField("label_GaraAVCPass");
         mEccezioni.addValidationErr(Messaggi.SIMOG_AVCPASS_001.concat(msgOperazioni));
      } 
      
      return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
   }
   

}

