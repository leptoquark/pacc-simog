package it.avlp.simog.actions;

import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.RequisitoGara.Documento;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.servlet.PSReq;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class RequisitiGLWebAction extends BaseAction implements PSReq {

   public RequisitiGLWebAction(Connection activeConnection, Logger logger) {
      super(activeConnection, logger);
   }
   
   /**
    * Costruisce la lista di RequisitoGara a partire dai dati in request
    * @param request
    * @return
    */
   public List<RequisitoGara> getListaRequistiGaraFromRequest(HttpServletRequest request){

      Map<Integer, RequisitoGara> mappaRequisiti = new TreeMap<Integer, RequisitoGara>();
      
      /*
       * Caricamento dei requisiti gara 
       */
      List<RequisitoGara> listaRequisitiGara = new LinkedList<RequisitoGara>();
 
      
      int maxIndexRequisiti = getIntReqParameter(request, 0, MAX_INDEX_REQUISTI);
      
      for(int i = 0; i <= maxIndexRequisiti; i++){
         
         String prefix = "row" + PREFIX_REQUISITO_GARA + i;
         
         String codiceRequisito = getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_REQUISITO);
         
         if( codiceRequisito != null ){
         
            RequisitoGara requisitoGara = new RequisitoGara();
            
            requisitoGara.setCodice_requisito_gara( getLongReqParameter(request, ND_REQUISITO_ID, prefix + FIELD_NAME_REQ_ID) );
//            requisitoGara.setCodice( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_REQUISITO) );
            requisitoGara.setCodice_dettaglio(Long.valueOf(getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_REQUISITO) ));
            if(requisitoGara.getCodice_dettaglio() > PSReq.MARKER_999){
               requisitoGara.setCodice_dettaglio(requisitoGara.getCodice_dettaglio() - PSReq.MARKER_999);
               requisitoGara.setCodice(PSReq.CODICE_REQUISITO_NON_CODIFICATO);
            }
            
            requisitoGara.setDescrizione( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_DESCRIZIONE) );
            requisitoGara.setValore( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_VALORE) );
            requisitoGara.setFlag_esclusione( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_ESCLUSIONE)  );
            requisitoGara.setFlag_comprova_offerta( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_COMPROVAOFF) );
            requisitoGara.setFlag_avvalimento( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_AVVALIMENTO) );
            requisitoGara.setFlag_bando_tipo( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_BANDO_TIPO) );
            requisitoGara.setFlag_riservatezza( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_RISERVATEZZA)  );
            
            requisitoGara.setListaDocumentiString( getStringReqParameter(request, null, prefix + PSReq.FIELD_NAME_REQ_DOC_LISTA_DOCUMENTI) );
            requisitoGara.setTipoUso( getStringReqParameter(request, null, prefix + FIELD_NAME_REQ_TIPO_USO)  );
            requisitoGara.getDocumenti().addAll( convertToListaDocumenti(requisitoGara.getListaDocumentiString()) );
            
            listaRequisitiGara.add(requisitoGara);
            mappaRequisiti.put(i, requisitoGara);
         }
      }
      
      /*
       * Caricamento dei lotti assegnati ai requisiti
       */
      
      int numLotti = getIntReqParameter(request, 0, NUM_LOTTI);
      
      for(int i = 0; i < numLotti; i++){
         
         String prefix = PREFIX_REQUISITO_GARA + "SelectLotto" + i;
         
         Long idLotto = getLongReqParameter(request, -1L, "idLotto" + prefix);
         
         if( idLotto > 0 ){
            
            String[] reqSelected = request.getParameterValues("select" + prefix);
            
            if( reqSelected != null ){
               for( String indexReq: reqSelected ){
                  int key = Integer.parseInt(indexReq);
                  mappaRequisiti.get(new Integer(key)).getLotti_associati().add(idLotto);
               }
            }
         }
      }
      
      return listaRequisitiGara;
   }
   
   /**
    * Converte il campo "listaDocumentiString" in una lista di documenti
    * @param request
    * @param listaDocumentiString
    * @return
    */
   private List<Documento> convertToListaDocumenti(String listaDocumentiString) {
      List<Documento> listaDocumenti = new LinkedList<RequisitoGara.Documento>();
      if( listaDocumentiString != null ){
         String[] arrayDocumenti = listaDocumentiString.split("~");
         for(int i = 0; i < arrayDocumenti.length; i++ ){
            String record = arrayDocumenti[i].replace("|", "#"); //pipe e' un operatore
            String[] campi = record.split("#");
            
            /*
             * Il codice inserito nella compo è data dalla concatenazione (separatore "_") 
             * del codice tipo documento e del codice documento
             */
            String codice_tipo_doc = campi[6].split("_")[0];
            String codice = campi[6].split("_")[1];
            
            Documento documento = new RequisitoGara().new Documento();
            
            documento.setCodice_tipo_doc( Long.parseLong(codice_tipo_doc) );
            documento.setCodice( codice );
            documento.setDescrizione_documento( campi[0] );
            documento.setEmettitore( campi[1] );
            documento.setFax( campi[3] );
            documento.setTelefono( campi[2] );
            documento.setMail( campi[4] );
            documento.setMail_pec( campi[5] );
            
            listaDocumenti.add(documento);
         }
      }
      return listaDocumenti;
   }
   
   /**
    * Converte una lista di documenti in un "listaDocumentiString"
    * @param listaDocumenti
    * @return
    */
   public String convertToListaDocumentiString(List<Documento> listaDocumenti) {
      String listaDocumentiString = "";
      for( Documento currentDocumento: listaDocumenti ){
         
         /*
          * Il codice inserito nella compo è data dalla concatenazione (separatore "_") 
          * del codice tipo documento e del codice documento
          */
         String codiceValue = currentDocumento.getCodice_tipo_doc() + "_" + currentDocumento.getCodice();
               
         String record =
             "" + currentDocumento.getDescrizione_documento()
             + "|" + currentDocumento.getEmettitore()
             + "|" + currentDocumento.getTelefono()
             + "|" + currentDocumento.getFax()
             + "|" + currentDocumento.getMail()
             + "|" + currentDocumento.getMail_pec()
             + "|" + codiceValue
             ;
         listaDocumentiString += record + "~";
      }
      return listaDocumentiString;
   }
   

   /**
    * Per ogni requisitoGara setta il campo "codice" a partire dal campo  "codice_dettaglio"
    * @param listaRequisitiGaraWeb
    * @param reqAction
    * @throws ActionException
    */
  public void aggiornaCodiceDettaglio(List<RequisitoGara> listaRequisitiGaraWeb, RequisitiGLAction reqAction, Timestamp dataRiferimento) 
        throws ActionException {
     
     Map<String,String> mappaRelazioneCodici = reqAction.getCodiceMap( dataRiferimento );
     
     for( RequisitoGara currentRequisito: listaRequisitiGaraWeb ){
        Long codiceDettaglioRequisito = currentRequisito.getCodice_dettaglio();
        String codice = mappaRelazioneCodici.get(String.valueOf(codiceDettaglioRequisito));
        currentRequisito.setCodice( codice );
     }
     
  }
  
  /**
   * Per ogni requisitoGara setta il campo "listaDocumentiString"
   * @param listaRequisitiGara
   */
  public void aggiornaListaDocumenti(List<RequisitoGara> listaRequisitiGara){
     for( RequisitoGara currentRequisito: listaRequisitiGara ){
        String listaDocumentiString = convertToListaDocumentiString( currentRequisito.getDocumenti() ) ;
        currentRequisito.setListaDocumentiString( listaDocumentiString );
     }
  }
   
}
