package it.avlp.simog.garamanager.app;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.RequisitoGara.Documento;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.servlet.PSReq;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.BeanUtilsServlet;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvRequisitiGL extends BeanUtilsServlet implements PSReq{

   public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
       perform(request, response);
   }
   
   @Override
   protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      Connection currentActiveConnection = null;
      Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
      HttpSession currentActiveSession = request.getSession();
      
      if ( checkSession(request) ) {
         
          String idGara =(String) getValueFromContexts(String.class, SESSION_ID_GARA, request);
          
          try {
              currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

              GaraManager gm = new GaraManager(currentActiveConnection, logger);
              Gara gara = gm.getGara(Long.valueOf(idGara)); 
              
              RequisitiGLAction requisitiGLAction = new RequisitiGLAction(currentActiveConnection, logger);
              
              String codiceDettaglioRequisito = request.getParameter("reqCodice");
              
              // il codice deve essere normalizzato
              long cod = Long.valueOf(codiceDettaglioRequisito);
              if( cod > PSReq.MARKER_999)
                 codiceDettaglioRequisito = String.valueOf(cod - PSReq.MARKER_999);
              
              String codice = requisitiGLAction.getCodiceMap(
                    // PP PageHelper.parseTimeYMD(PageHelper.getCurrentDate())
                    PageHelper.parseTimeYMD(gara.getData_creazione())
                    ).get(codiceDettaglioRequisito);
              List<Documento> listaDocumenti = requisitiGLAction.getDocumentiList(PageHelper.parseTimeYMD(gara.getData_creazione()));
              
              if( SimogFlags.is3028_RFWEBGL06Active() ){
                 listaDocumenti = filtraDocumentiPerRequisitiCodificati(listaDocumenti, codice);
              }
              
              List<Documento> listaDocumentiOB = requisitiGLAction.getDocumentiObbligatoriList(codiceDettaglioRequisito, PageHelper.parseTimeYMD(gara.getData_creazione()));
              
              request.setAttribute(LISTA_DOCUMENTI, listaDocumenti);
              request.setAttribute(LISTA_DOCUMENTI_OB, listaDocumentiOB);
              
              String fromRicerca = request.getParameter(ParametriServlet.FROM_RICERCA);
              request.setAttribute(ParametriServlet.FROM_RICERCA, fromRicerca);
              
              forward(JSP_POPUP_DOCUMENTI_REQUISITI, request, response);
          } catch ( Exception sqle ) { 
              sqle.printStackTrace();
              rollback(currentActiveConnection);  
              sendError(request, response, SIMOG_GARA_005, JSP_ERRORE, sqle);
              return;
          } finally {
              closeConnection(request.getSession().getId(),getClass().getName());
          }
      }
   }

   
   /*
    * Restituisce la lista dei documento che possono essere aggiunti ad un requisito codificato
    */
   private List<Documento> filtraDocumentiPerRequisitiCodificati(List<Documento> listaDocumenti, String codiceDettaglioRequisito){
      if( !PSReq.CODICE_REQUISITO_NON_CODIFICATO.equals(codiceDettaglioRequisito) ){
         List<Documento> listaDocumentiRequisitoCodificato = new ArrayList<Documento>();
         for(Documento currentDocumento: listaDocumenti){
            if( PSReq.CODICE_DOCUMENTO_NON_CODIFICATO.equals(currentDocumento.getCodice()) ){
               listaDocumentiRequisitoCodificato.add(currentDocumento);
            }
         }
         listaDocumenti.clear();
         listaDocumenti.addAll(listaDocumentiRequisitoCodificato);
      }
      return listaDocumenti;
   }
   
}
