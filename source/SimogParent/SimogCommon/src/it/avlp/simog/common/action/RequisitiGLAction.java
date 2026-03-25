package it.avlp.simog.common.action;

import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.RequisitoGara.Documento;
import it.avlp.simog.common.servlet.PSReq;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.REQUISITO_GARA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.RequisitiGLValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class RequisitiGLAction extends BaseSharedAction {

   private RequisitiGLManager rqglman = null;
   
   public RequisitiGLAction(Connection activeConnection, Logger logger) {
      super(activeConnection, logger);
   }
   
   private RequisitiGLManager getRequisitiGLManager(){
      if (this.rqglman == null)
         rqglman = new RequisitiGLManager(connection, logger); 
      return this.rqglman;
   }
   
   /**
    * Restituisce la lista dei requisiti di una gara, caricamento per WS
    * Carica tutti i requisiti dalla tabella REQUISTO_GARA eccetto quelli facoltativi.
    * Altrimenti carica i requisti obbligatori (O,OM,AA)  
    * @param idGara
    * @param dataRif 
    * @return List<RequisitoGara>
    * @throws ActionException
    */
@Deprecated
   public List<RequisitoGara> getRequisitoGaraListWS( long idGara, Timestamp dataRif ) throws ActionException {
      try{
         
         List<RequisitoGara> listaRequisitiGara = new LinkedList<RequisitoGara>();
         List<RequisitoGara> listaRequisitiGaraDB = getRequisitoGaraList(idGara);
         if( listaRequisitiGaraDB.isEmpty() ){
            listaRequisitiGara.addAll(getRequisitoObbligatorioGaraList(dataRif));
         } else {
            //Eliminazione requisiti di tipo "F" (facoltativi)
            for(RequisitoGara currentRequisito: listaRequisitiGaraDB){
               if(currentRequisito.isObbligatorio()){
                  listaRequisitiGara.add(currentRequisito);
               }
            }
         }
         
         return listaRequisitiGara;
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
   
   /**
    * Restituisce la lista dei requisiti, completi, di una gara
    * @param idGara
    * @return List<RequisitoGara>
    * @throws ActionException
    */
   public List<RequisitoGara> getRequisitoGaraList( long idGara ) throws ActionException {
      try{
         
         GaraManager gm = new GaraManager(connection, logger);
         
         List<RequisitoGara> listaRequisitiGara = getRequisitiGLManager().getRequisitoGaraList(idGara);
         
         for( RequisitoGara currentRequisito: listaRequisitiGara ){
            
            List<Long> listaLottiAssocitati = getRequisitiGLManager().getListaLottiAssociati(idGara, currentRequisito);
            currentRequisito.setLotti_associati( listaLottiAssocitati );
            
            Timestamp dataRif = gm.getNow();
            Gara gara = gm.getGara(idGara);
            if(gara.getData_creazione() != null)
              dataRif = PageHelper.parseTimeYMD(gara.getData_creazione()); 
            
            List<Documento> listaDocumentiGara = getRequisitiGLManager().getDocumentiGaraList(currentRequisito.getCodice_requisito_gara(), dataRif );
            
            currentRequisito.setDocumenti( listaDocumentiGara );
         }
         
         return listaRequisitiGara;
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
   
   /**
    * Restituisce la lista dei requisiti obbligatori di una gara
    * @param timestamp 
    * @return List<RequisitoGara>
    * @throws ActionException
    */
   public List<RequisitoGara> getRequisitoObbligatorioGaraList(Timestamp timestamp) throws ActionException {
      try{
         
         List<RequisitoGara> listaRequisitiGara = getRequisitiGLManager().getRequisitoGaraObbligatorioList(timestamp);
         
         for( RequisitoGara currentRequisito: listaRequisitiGara ){
           
            List<Documento> listaDocumentiGara = getRequisitiGLManager().getDocumentiObbligatoriGaraList(String.valueOf(currentRequisito.getCodice_dettaglio()), timestamp);
            currentRequisito.setDocumenti( listaDocumentiGara );
         }
         
         return listaRequisitiGara;
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }

   /**
    * Restituisce la lista dei requisiti obbligatori di una gara
    * @param timestamp 
    * @return List<RequisitoGara>
    * @throws ActionException
    */
   public List<RequisitoGara> getRequisitoObbligatorioGaraListAR(Timestamp timestamp) throws ActionException {
      try{
         
         List<RequisitoGara> listaRequisitiGara = getRequisitiGLManager().getRequisitoGaraObbligatorioListAR(timestamp);
         
         for( RequisitoGara currentRequisito: listaRequisitiGara ){
           
            List<Documento> listaDocumentiGara = getRequisitiGLManager().getDocumentiObbligatoriGaraList(String.valueOf(currentRequisito.getCodice_dettaglio()), timestamp);
            currentRequisito.setDocumenti( listaDocumentiGara );
         }
         
         return listaRequisitiGara;
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
   
   /**
    * Restituisce una mappa con il codice e la descrizione dei requisiti facoltativi
    * @return Map<String,String>
    * @throws SQLException
    */
   public Map<String,String> getRequisitiFacoltativiMap(Timestamp targetDate) throws ActionException {
      try{
         
         return getRequisitiGLManager().getRequisitiFacoltativiMap(targetDate);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
   
   
   /**
    * Restituisce una mappa con il codice e la descrizione dei requisiti facoltativi, per tipologia
    * @param currentDatetime 
    * @return Map<String,String>
    * @throws SQLException
    */
   public Map<String, TreeMap<String,String>> getRequisitiPerTipologiaMap(Map<String,String> mappaRequisiti, Timestamp currentDatetime) throws ActionException {
      try{
         
         Map<String, TreeMap<String,String>> mappaRequisitiFacoltativiPerTipologia = new TreeMap<String, TreeMap<String,String>>();
         Map<String,String> mappaRequistiTipologia = getRequisitiGLManager().getRequisitiPerTipologiaMap(currentDatetime);
         
         for(String codiceRequisito: mappaRequistiTipologia.keySet()){
            
            String descTipologia = mappaRequistiTipologia.get(codiceRequisito);
            
            if( !mappaRequisitiFacoltativiPerTipologia.containsKey(descTipologia) ){
               mappaRequisitiFacoltativiPerTipologia.put(descTipologia, new TreeMap<String, String>());
            }
            
            Map<String, String> tipologiaMap = mappaRequisitiFacoltativiPerTipologia.get(descTipologia);
            
            if( mappaRequisiti.containsKey(codiceRequisito) ){
               tipologiaMap.put(codiceRequisito, mappaRequisiti.get(codiceRequisito) );
            }
         }
         
         return mappaRequisitiFacoltativiPerTipologia;
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }   
   
   /**
    * Restituisce una mappa con il codice e la descrizione dei requisiti obbligatori
    * @return Map<Long,String>
    * @throws SQLException
    */
   public Map<String,String> getRequisitiObbligatoriMap(Timestamp targetDate) throws ActionException {
      try{
         
         return getRequisitiGLManager().getRequisitiObbligatoriMap(targetDate);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
   
   /**
    * Restituisce una mappa con il codice e la descrizione dei requisiti facoltativi
    * @return Map<Long,String>
    * @throws SQLException
    */
   public Map<String,String> getRequisitiFacoltativiUsoMap(Timestamp targetDate) throws ActionException {
      try{
         
         return getRequisitiGLManager().getRequisitiFacoltativiUsoMap(targetDate);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }   
   
   
   /**
    * Restituisce una mappa con il codice e la descrizione
    * @return Map<Long,String>
    * @throws SQLException
    */
//   public Map<String,String> getRequisitiMap(Timestamp targetDate) throws ActionException {
//      try{
//         
//         return getRequisitiGLManager().getRequisitiMap(targetDate);
//         
//      }catch (Exception e) {
//         logger.error(e);
//         throw new ActionException(e);
//      }
//   }
   
   /**
    * Restituisce la lista dei lotti di una gara
    * @param idGara
    * @return List<Lotto>
    * @throws SQLException
    * @throws Exception
    */
   public List<Lotto> getLottoList( long idGara ) throws ActionException {
      try{
         
         LottoManager lottoManager = new LottoManager(connection, logger);
         return lottoManager.getListaLotti(idGara);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
   
   /**
    * Restiuisce la lista dei documenti
    * @param timestamp 
    * @return List<Documento>
    * @throws SQLException
    */
   public List<Documento> getDocumentiList(Timestamp timestamp) throws ActionException {
      try{
         
         return getRequisitiGLManager().getDocumentiList(timestamp);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
   
   /**
    * Restiuisce la lista dei documenti obbligatori
    * @param dataRif 
    * @return List<Documento>
    * @throws SQLException
    */
   public List<Documento> getDocumentiObbligatoriList(String codiceDettaglioRequisito, Timestamp dataRif) throws ActionException {
      try{
         
         return getRequisitiGLManager().getDocumentiObbligatoriGaraList(codiceDettaglioRequisito, dataRif);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }

   
   /**
    * Restituisce una mappa di relazione codice - codice_dettaglio
    * @param targetDate
    * @return Map<String,String>
    * @throws ActionException
    */
   public Map<String,String> getCodiceMap(Timestamp targetDate)  throws ActionException {
      try{
         
         return getRequisitiGLManager().getCodiceMap(targetDate);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }      
   }
   
   /**
    * Restituisce una mappa di relazione codice_dettaglio - codice
    * @param targetDate
    * @return Map<String,String>
    * @throws ActionException
    */
   public Map<String,String> getCodiceDettaglioReqMap(Timestamp targetDate)  throws ActionException {
      try{
         
         return getRequisitiGLManager().getCodiceDettaglioReqMap(targetDate);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }      
   }

   /**
    * Restituisce una mappa di relazione cod_tipo_doc_req - codice per la tabella TIPO_DOCUMENTO_REQ
    * @param targetDate
    * @return Map<String,String>
    * @throws ActionException
    */
   public Map<String,String> getCodiceTipoDocReqMap(Timestamp targetDate)  throws ActionException {
      try{
         
         return getRequisitiGLManager().getCodiceTipoDocReqMap(targetDate);
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }      
   }
   
   
//   public List<String> getCodiceDocumentiFonteOEList() throws ActionException {
//      try{
//         
//         return getRequisitiGLManager().getCodiceDocumentiFonteOEList();
//         
//      }catch (Exception e) {
//         logger.error(e);
//         throw new ActionException(e);
//      }  
//   }
   
/**************************************************************************************************
 **************************************************************************************************/   
   
   private AllValidationBeans eccezioni = new AllValidationBeans();
   
   public AllValidationBeans getEccezioni() {
      return eccezioni;
   }

   public void setEccezioni(AllValidationBeans eccezioni) {
      this.eccezioni = eccezioni;
   }
   
   
   /**
    * Aggiorna i requisiti di una gara 
    * @param listaRequisitiGara
    */
   public boolean updateRequisitiGara( List<RequisitoGara> listaRequisitiGara, long idGara, Timestamp dataRiferimento, boolean revocaLogica ) throws ActionException {
      try{
         int cambiamenti = 0;

         RequisitiGLValidator validator = new RequisitiGLValidator(connection, logger);
         
         if( validator.valida(idGara, listaRequisitiGara, dataRiferimento) ){

            /*
             * Recupero della lista dei requisiti gara da revocare e della lista dei requisiti da aggiornare 
             */
           // PP non serve  List<RequisitoGara> listaRequisitoGaraDB = getRequisitoGaraList(idGara);
            
            //List<RequisitoGara> listaAggiornabili = new ArrayList<RequisitoGara>(listaRequisitiGara);
            //List<RequisitoGara> listaNotAggiornabili = new ArrayList<RequisitoGara>(listaRequisitiGara);
            //listaAggiornabili.removeAll(listaRequisitoGaraDB);
            //listaNotAggiornabili.retainAll(listaRequisitoGaraDB);
            
            //List<RequisitoGara> listaRevocabili = new ArrayList<RequisitoGara>(listaRequisitoGaraDB); 
            //listaRevocabili.removeAll(listaNotAggiornabili);
            
            /*
             * Aggiornamento requisiti gara per ogni lotto
             */
            if( !revocaLogica )
               getRequisitiGLManager().deleteDocumenti(idGara);
            
            cambiamenti += getRequisitiGLManager().revocaRequisitiGara(idGara, revocaLogica);
            cambiamenti += getRequisitiGLManager().insertRequisitiGara(listaRequisitiGara, idGara);

            /*
             * Inserimento documenti gara per ogni requisito
             */
            cambiamenti += getRequisitiGLManager().insertDocumentiRequisito(listaRequisitiGara, idGara);

            // duplico i riferimenti anche ai record non master
            if(SimogFlags.is3028_RNFDBDT03Active()){
               insertDocumentiNonMaster(listaRequisitiGara, idGara);
            }
            
         }
         
         if(getEccezioni().getAll().isEmpty())
            setEccezioni(validator.getEccezioni());
         else
            getEccezioni().add(validator.getEccezioni());
         
         return cambiamenti > 0;
         
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }

	/**
	 * Aggiorna i requisiti di una gara per quanto riguarda i WS.
	 * 
	 * @param listaRequisitiGara
	 */
	public boolean updateRequisitiGaraWS(List<RequisitoGara> listaRequisitiGara, long idGara, Timestamp dataRiferimento, boolean revocaLogica) throws ActionException {
		try {
			this.checkListaRequisiti(listaRequisitiGara, dataRiferimento);
			return this.updateRequisitiGara(listaRequisitiGara, idGara, dataRiferimento, revocaLogica);

		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}
 

/**
    * Associazione dei requisiti obbligatori di tipo uso AR ad un determinato lotto
    * @param idGara
    * @param cigPerAR
 * @param timestamp 
    * @return true, se sono stati inseriti dei record, false altrimenti
    * @throws ActionException
    */
   public boolean updateRequisitiARbyLotto( long idGara, List<Long> cigPerAR, boolean revocaLogica, Timestamp timestamp ) throws ActionException {
      try {
         int cambiamenti = 0;
         
         List<RequisitoGara> listaRequisitiGaraOB = getRequisitoObbligatorioGaraListAR(timestamp);
         List<RequisitoGara> listaRequisitiGaraAR = new LinkedList<RequisitoGara>();
         
// 13.06.2013 richiesta modifica, i requisiti AR vanno associati a livello di gara, se la lista è vuota aggiungo un elemento
//             a livello di gara         
         if(cigPerAR.size() == 0){
            for(RequisitoGara item: listaRequisitiGaraOB){
               listaRequisitiGaraAR.add(item);
            }
         }
         else{
         // costruisco la lista dei requisiti associati ai lotti
            for(RequisitoGara item: listaRequisitiGaraOB){
               for(Long idLotto: cigPerAR)
                  item.getLotti_associati().add(idLotto);
               listaRequisitiGaraAR.add(item);
            }
         }
                  
         /* XXX NOTA Revoca Requisiti Gara AR
          * La cancellazione fisica dei requisiti AR e dei relativi documenti non e' testata.
          * Non esiste, attualmente, un caso in cui e' possibile rettificare i requisiti AR inseriti 
          */
         if( !revocaLogica )
            getRequisitiGLManager().deleteDocumentiByTipoUso(idGara, RequisitoGara.TIPO_USO_AR);

         getRequisitiGLManager().revocaRequisitiGaraByGaraAndTipoUso(idGara, RequisitoGara.TIPO_USO_AR, revocaLogica); 
         
         cambiamenti += getRequisitiGLManager().insertRequisitiGara(listaRequisitiGaraAR, idGara);
         
         return cambiamenti > 0;
      
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }   

   /**
    * Il metodo elimina dalla lista dei requisiti quelli con tipologia 'non facoltativa'.
    * integra inoltre eventuali requisiti obbligatori non indicati dall'utente ma previsti dal sistema
    * 
    * l'utente può solo inviare il tipo "F" "M" e "OM", questi ultimi possono essere solo validi a livello di gara
    * le tipologie "AR" e "AA" vanno scartate se presenti
    * le tipologie "M" e "OM" mancanti vanno aggiunte con i valori di default
    * (UN 10/12/2013 tipologie "OM" non vanno aggiunte se non presenti)
    * 
    * pulisce le informazioni di eventuali requisiti e documenti codificati associati ai requisiti
    * @param listaRequisitiGara
    * @param dataRiferimento
    * @throws ActionException
    */
	private void checkListaRequisiti(List<RequisitoGara> listaRequisitiGara, Timestamp dataRiferimento) throws ActionException {
		
		try {
			RequisitiGLManager requisitiGLManager = new RequisitiGLManager(connection, logger);

         Map<String, String> tipoFA = requisitiGLManager.getRequisitiMapUso(dataRiferimento, RequisitoGara.TIPO_USO_FA);
         Map<String, String> tipoOB = requisitiGLManager.getRequisitiMapUso(dataRiferimento, RequisitoGara.TIPO_USO_OB);
         Map<String, String> tipoOM = requisitiGLManager.getRequisitiMapUso(dataRiferimento, RequisitoGara.TIPO_USO_OM);
         Map<String, String> tipoAR = requisitiGLManager.getRequisitiMapUso(dataRiferimento, RequisitoGara.TIPO_USO_AR);
         Map<String, String> tipoAA = requisitiGLManager.getRequisitiMapUso(dataRiferimento, RequisitoGara.TIPO_USO_AA);
         
         Map<String, String> tipiDoc = requisitiGLManager.getCodiceDocMap(dataRiferimento);
			
			if (listaRequisitiGara != null ) { 
			   boolean avvisato = false;
				for (Iterator<RequisitoGara> it = listaRequisitiGara.iterator(); it.hasNext();) {
					RequisitoGara currentReq = it.next();
					
					if (currentReq != null){
					   // pulisco il requisito
					   if(!PSReq.CODICE_REQUISITO_NON_CODIFICATO.equals(currentReq.getCodice())){
		                if(tipoOM.containsKey(String.valueOf(currentReq.getCodice_dettaglio())))
		                      currentReq.setDescrizione(tipoOM.get(String.valueOf(currentReq.getCodice_dettaglio())));   		                      
		                if(tipoOB.containsKey(String.valueOf(currentReq.getCodice_dettaglio()))) 
                         currentReq.setDescrizione(tipoOB.get(String.valueOf(currentReq.getCodice_dettaglio()))); 
                      if(tipoFA.containsKey(String.valueOf(currentReq.getCodice_dettaglio()))) 
                         currentReq.setDescrizione(tipoFA.get(String.valueOf(currentReq.getCodice_dettaglio()))); 
                      if(tipoAA.containsKey(String.valueOf(currentReq.getCodice_dettaglio())))
                         currentReq.setDescrizione(tipoAA.get(String.valueOf(currentReq.getCodice_dettaglio())));                             
					   }
					      
					   // pulisco i documenti codificati
					   if(currentReq.getDocumenti() != null){
					      for (Iterator iterator = currentReq.getDocumenti().iterator(); iterator.hasNext();) {
                        Documento doc = (Documento) iterator.next();
                        if(!PSReq.CODICE_DOCUMENTO_NON_CODIFICATO.equals(doc.getCodice())){
                           doc.setDescrizione_documento(tipiDoc.get(doc.getCodice()));
                           doc.setEmettitore("");
                           doc.setFax("");
                           doc.setMail("");
                           doc.setMail_pec("");//MAC xxx
                           doc.setTelefono("");
                        }
                     }
					   }
					   
	               // elimino gli "AR" se presenti
					   if(tipoAR.containsKey(String.valueOf(currentReq.getCodice_dettaglio()))
					         // || tipoAA.containsKey(String.valueOf(currentReq.getCodice_dettaglio()))
					         ) {
					      it.remove();
   						if (!avvisato){
   						   eccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_218);
   						   avvisato = true;
   						}
					   }
		             
	               // tolgo le associazioni ai lotti per gli "M" e "OM" "AA" eventualmente presenti
					   if(tipoOM.containsKey(String.valueOf(currentReq.getCodice_dettaglio()))
					         || tipoOB.containsKey(String.valueOf(currentReq.getCodice_dettaglio()))
					         || tipoAA.containsKey(String.valueOf(currentReq.getCodice_dettaglio()))
					         ) {
					      if(!currentReq.getLotti_associati().isEmpty())
					         currentReq.getLotti_associati().clear();
					   }
					}
				}

			List<RequisitoGara> lista = requisitiGLManager.getRequisitoGaraUso(RequisitoGara.TIPO_USO_OB, dataRiferimento);
            //UN 10/12/2013 le tipologie "OM" non vanno aggiunte se non presenti
			//List<RequisitoGara> lista2 = requisitiGLManager.getRequisitoGaraUso(RequisitoGara.TIPO_USO_OM);
            List<RequisitoGara> lista3 = requisitiGLManager.getRequisitoGaraUso(RequisitoGara.TIPO_USO_AA, dataRiferimento);
				
            // aggiungo i requisiti di ordine generale obbligatori, solo "M" e "OM", "AA" mancanti
            for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
               RequisitoGara requisitoGara = (RequisitoGara) iterator.next();
               
               boolean trovato = false;
               for (int i = 0; i < listaRequisitiGara.size(); i++) {
                  if(listaRequisitiGara.get(i).getCodice_dettaglio() == requisitoGara.getCodice_dettaglio()){
                     trovato = true;
                     break;
                  }
               }
               if (!trovato)
                  listaRequisitiGara.add(requisitoGara);
            }
            
// UN 10/12/2013 le tipologie "OM" non vanno aggiunte se non presenti
            
//            for (Iterator iterator = lista2.iterator(); iterator.hasNext();) {
//               RequisitoGara requisitoGara = (RequisitoGara) iterator.next();
//               
//               boolean trovato = false;
//               for (int i = 0; i < listaRequisitiGara.size(); i++) {
//                  if(listaRequisitiGara.get(i).getCodice_dettaglio() == requisitoGara.getCodice_dettaglio()){
//                     trovato = true;
//                     break;
//                  }
//               }
//               if (!trovato){
//                  // preimpostazione flag avvalimento a si per gli OM automatici
//                  if(SimogFlags.is3029_MAC01Active())
//                     requisitoGara.setFlag_avvalimento(Costanti.FLAG_VALORE_SI);
//                  
//                  listaRequisitiGara.add(requisitoGara);
//               }
//            }

            for (Iterator iterator = lista3.iterator(); iterator.hasNext();) {
               RequisitoGara requisitoGara = (RequisitoGara) iterator.next();
               
               boolean trovato = false;
               for (int i = 0; i < listaRequisitiGara.size(); i++) {
                  if(listaRequisitiGara.get(i).getCodice_dettaglio() == requisitoGara.getCodice_dettaglio()){
                     trovato = true;
                     break;
                  }
               }
               if (!trovato)
                  listaRequisitiGara.add(requisitoGara);
            }
			}
			
		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}

   /**
    * Revoca dell'associazione requisito gara e lotto
    * @param idGara
    * @param idLotto
    * @return
    * @throws ActionException
    */
	// FIXMato: PP così no può funzionare, per ora restano i requisiti, associati al lotto cancellato
	
   public boolean revocaRequisitiByLotto( long idGara, long idLotto, Timestamp dataRiferimento, boolean revocaLogica ) throws ActionException {
      try {
         
         List<RequisitoGara> listaRequisitiGaraEliminare = new LinkedList<RequisitoGara>();
         List<RequisitoGara> listaRequisitiGara = this.getRequisitoGaraList(idGara);
         for(RequisitoGara currentRequisito: listaRequisitiGara){
            
            /*
             * Per ogni requisito che:
             * - coinvolge il lotto specificato
             * - non ha un solo lotto associato
             * aggiornare il requisto eliminando l'associazione requisito gara - lotto
             */
            boolean requisitoAssociatoLotto = currentRequisito.getLotti_associati().contains(idLotto);
            boolean requisitoMonoLotto = currentRequisito.getLotti_associati().size() == 1;
            
            if( requisitoAssociatoLotto && !requisitoMonoLotto ){
               currentRequisito.getLotti_associati().remove(idLotto);
            }
            
            /*
             * Eliminazione dei requisiti associati al solo lotto cancellato
             */
            if( requisitoAssociatoLotto && requisitoMonoLotto ){
               listaRequisitiGaraEliminare.add(currentRequisito);
            }
         }
         
         listaRequisitiGara.removeAll(listaRequisitiGaraEliminare);
         
         return this.updateRequisitiGara(listaRequisitiGara, idGara, dataRiferimento, revocaLogica);
         
      }catch (Exception e) {
         String message = "Non e' stato possibile eliminare l'associazione gara[" + idGara + "] lotto[" + idLotto + "]";
         logger.error(message, e);
         throw new ActionException(message, e);
      }
   }   

   /**
    * @param idGara
    * @param idLotto
    * @return
    * @throws ActionException
    */
   public boolean revocaRequisitiByGara( long idGara, boolean revocaLogica ) throws ActionException {
      try {
         int cambiamenti = 0;
         
         if( !revocaLogica )
            getRequisitiGLManager().deleteDocumenti(idGara);
         
         cambiamenti += getRequisitiGLManager().revocaRequisitiGara(idGara, revocaLogica);
         
         return cambiamenti > 0;
      
      }catch (Exception e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }   
   
   /*
    * inserisce riferimenti ai documenti per i record non master
    */
   public void insertDocumentiNonMaster(List<RequisitoGara> lista, long idGara) throws ActionException{
      try {
         TableBean lstNonMaster = getRequisitiGLManager().getRequisitiNonMaster(idGara);
         
         // per ogni riferimento al record non master ...
         for (int i = 0; i < lstNonMaster.getFullSize(); i++) {
            long codDett = Long.parseLong(lstNonMaster.getNulledField(REQUISITO_GARA.COD_DETT_REQUISITO, i));
            long codPk = Long.parseLong(lstNonMaster.getNulledField(REQUISITO_GARA.COD_REQUISITO_GARA, i));
            
            List<RequisitoGara> listaRif = new ArrayList<RequisitoGara>();
            
            // cerco i dati dei documenti del requisito
            for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
               RequisitoGara requisitoGara = (RequisitoGara) iterator.next();
               // trovato il requisito, scrivo i documenti
               if(requisitoGara.getCodice_dettaglio() == codDett){
                  // ci sono documenti da associare
                  if(!requisitoGara.getDocumenti().isEmpty()){
                     RequisitoGara reqNew = requisitoGara.fakeCopy();
                     reqNew.setCodice_requisito_gara(codPk); // nuova PK da aggiungere
                     listaRif.add(reqNew);
                  }
                  
                  break;
               }
            }
            
            // se ci sono associazioni le scrivo
            if(!listaRif.isEmpty())
               getRequisitiGLManager().insertDocumentiRequisito(listaRif, idGara);
         }
         
      } catch (SQLException e) {
         logger.error(e);
         throw new ActionException(e);
      }
   }
}
