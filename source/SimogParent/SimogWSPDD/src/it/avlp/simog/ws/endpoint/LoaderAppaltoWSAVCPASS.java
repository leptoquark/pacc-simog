package it.avlp.simog.ws.endpoint;

import it.avcp.avcpass.AVCPassAction;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.ws.beans.ResponseLoaderAppalto;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.massload.xmlbeans.TrasferimentoDati;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "xmlbeans.massload.simog.avlp.it")
public class LoaderAppaltoWSAVCPASS {

   /** QUESTA VERSIONE DEVE ESSERE RESA DISPONIBILE SOLO AL SISTEMA AVCPASS!!!
    * Rappresenta l'operation del WS 'LoaderAppaltoAVCPASS'. Il metodo si occupa del caricamento dei dati dell'Appalto 
    *  
    * @param ticket
    * @param indexCollaborazione
    * @param trasferimentoDati
    * 
    * @return ResponseLoaderAppalto
    */
   @WebMethod
   public ResponseLoaderAppalto loaderAppaltoAVCPASS(@WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "trasferimentoDati") TrasferimentoDati trasferimentoDati) {
   
      SuperLoaderAppaltoWS slaws = new SuperLoaderAppaltoWS(OrigineSchedaEnum.AVCPASS);
      
      if( SimogFlags.is3028_RFWEBSC00Active() ){
         DbManager dbm = null;
         try {
            dbm = new DbManager(slaws.logger, slaws.configuration);
            AVCPassAction avcpassAction = new AVCPassAction(dbm.getCurrentActiveConnection(), 
                  slaws.logger, ConfigurationManager.getInstance().getSimogProperties());
            avcpassAction.integraDatiAVCPASS(trasferimentoDati);
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         if (dbm != null && dbm.getCurrentActiveConnection() != null) {
            dbm.closeConnection();
         }
      }
      
      return slaws.loaderAppalto(ticket, indexCollaborazione, trasferimentoDati);
   }

}

