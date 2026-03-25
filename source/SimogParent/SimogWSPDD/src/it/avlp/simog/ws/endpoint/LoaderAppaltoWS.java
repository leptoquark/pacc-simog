package it.avlp.simog.ws.endpoint;

import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.ws.beans.ResponseLoaderAppalto;
import it.avlp.simog.ws.massload.xmlbeans.TrasferimentoDati;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "xmlbeans.massload.simog.avlp.it")
public class LoaderAppaltoWS {

   /**
    * Rappresenta l'operation del WS 'LoaderAppalto'. Il metodo si occupa del caricamento dei dati dell'Appalto attraverso le segg. fasi:
    *       <ul><li> 1) Inizializzazione dei parametri di configurazione;</li>
    *       <li>     2) Marshalling dei dati di input;</li>
    *       <li>     3) validazione dell' Xml ricavato dai dati di input;</li>
    *       <li>     4) invocazione del Mass Loader per il caricamento dei dati;</li>
    *       <li>     5) restituzione di un Feedback che viene incapsulato nella risposta dell'operation suddetta.</li></ul>
    *  
    * @param ticket
    * @param indexCollaborazione
    * @param trasferimentoDati
    * 
    * @return ResponseLoaderAppalto
    */
   @WebMethod
   public ResponseLoaderAppalto loaderAppalto(@WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "trasferimentoDati") TrasferimentoDati trasferimentoDati) {
   
      SuperLoaderAppaltoWS slaws = new SuperLoaderAppaltoWS(OrigineSchedaEnum.LOADER_APPALTO);
      
      return slaws.loaderAppalto(ticket, indexCollaborazione, trasferimentoDati);
   }

}

