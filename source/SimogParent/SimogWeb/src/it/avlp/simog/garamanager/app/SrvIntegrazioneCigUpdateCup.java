package it.avlp.simog.garamanager.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;

public class SrvIntegrazioneCigUpdateCup extends ServletBase {

   private static final long serialVersionUID = 809280776067844305L;

   @Override
   protected void perform(HttpServletRequest request,
         HttpServletResponse response) throws ServletException, IOException 
   {  
      Connection currentActiveConnection = null;
      HttpSession currentActiveSession = request.getSession();
      Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
      if ( checkSession(request) ) {
         if ( currentUser.isRUP()) {            
            visualizzaListaParametriValori(request, response);

            String paramFlagPnrrPnc = request.getParameter(ParametriServlet.FLAG_PNRR_PNC);
            String paramFlagDerogaAdesione = request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE); //MEV 37010 3.04.8.1
            String paramFlagPrevisioneQuote= request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA);
            String paramFlagMisurePremiali= request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI);
            String paramQuotaGiovanile = request.getParameter(ParametriServlet.QUOTA_GIOVANILE);
            String paramQuotaFemminile = request.getParameter(ParametriServlet.QUOTA_FEMMINILE);
            String paramSelMotivoDeroga = request.getParameter(ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN);
            String paramFlagCup = request.getParameter(ParametriCup.FIELD_FLAG_CUP);
            String paramIdLotto = request.getParameter(ParametriCup.FIELD_NAME_ID_LOTTO);
            try
            {
               currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
               currentActiveConnection.setAutoCommit(false);
               
               Lotto lotto = new Lotto();
               lotto.setId_Lotto(Long.parseLong(paramIdLotto));
               lotto.setFLAG_CUP(paramFlagCup);
               lotto.setFLAG_PNRR_PNC(paramFlagPnrrPnc);
               lotto.setFLAG_DEROGA_ADESIONE(paramFlagDerogaAdesione); //MEV 37010 3.04.8.1
               lotto.setFLAG_PREVISIONE_QUOTA(paramFlagPrevisioneQuote);
               lotto.setFLAG_MISURE_PREMIALI(paramFlagMisurePremiali);
               lotto.setQuotaGiovanile(new BigDecimal(paramQuotaGiovanile));
               lotto.setQuotaFemminile(new BigDecimal(paramQuotaFemminile));
               
               //lotto.setElencoMotivoDeroga(new ArrayList<MotivoDerogaBean>());

               
               if( paramFlagCup != null && ( Costanti.FLAG_VALORE_NO.equals(paramFlagCup)) )
               {
                  LottoManager lman = new LottoManager(currentActiveConnection, logger);
                  lman.updateFlagCup(lotto);
                  
                  commit(currentActiveConnection);
               }
               
               String targetPage = ParametriCup.SRV_ELENCO_CIG_INTEGRAZIONE_CUP + "?nav=yes";
               
               if( Costanti.FLAG_VALORE_SI.equals(paramFlagCup) )
               {
                  
                  currentActiveSession.setAttribute(ParametriCup.FROM_ELENCO_CUP, "visCup");
                  
                  targetPage = ParametriServlet.SRV_GESTISCI_LOTTO 
                             + "?action=" + ParametriCup.ACTION_MODIFICA_DATI_CUP
                             + "&" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + paramIdLotto
                             + "&" + ParametriServlet.FROM_RICERCA + "=visCup"
                             ;
               }

               forward(targetPage, request, response);
               
            } catch ( Exception e ) {
               e.printStackTrace();
               rollback(currentActiveConnection);
               sendError(request, response, e.getMessage(), JSP_ERRORE, e);
               return;
            } finally {
               closeConnection(request.getSession().getId(),getClass().getName());
            }
            
         }
      }
      
   }

}
