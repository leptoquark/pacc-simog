package it.avlp.simog.gestioneannullamentomanager.app;

import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.actions.GenericAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.SchedaState;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServletRichAnnullamento;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.gestioneannullamentomanager.AnnullamentoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.validatore.RichAnnCancController;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Attenzione la classe è stata implementata solo per sanare una situazione nel DB non va utilizzata nella
 * logica SIMOG
 *
 */
 public class SrvRigettaTutteRichieste extends ServletBase{
   private static final long serialVersionUID = -2187811152695516370L;

//   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//          perform(request, response);
//   }

   @Override
	protected void perform(HttpServletRequest request,
	      HttpServletResponse response) throws ServletException, IOException {
	   String ret  = rigetta(request);
	   sendMessage(request, response, ret, JSP_ERRORE);
	}
			
   
    /********************************************************************
     * rigetta le richieste di modifica ancora non evase, da usare una tantum per bonifica
     * 
     * va richiamata tramite la servlet __rigetta e il parametro "doIT", che se indicato effettua l'update
     * altrimenti fa solo la simulazione
     * @return 
     */
    @SuppressWarnings("unused")
	public String rigetta(HttpServletRequest request)
    {		
		Connection currentActiveConnection = null;
		String retVal = "*** unexpected ***";
		int retRig = 0;
		int retDel = 0;
		int elab = 0;
		RichiestaAnnullamento richiestaAnnullamento = null;
        boolean doIt = request.getParameter("doIT") != null;
        boolean canc = request.getParameter("canc") != null;
		
		try{
		   
		   Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
           currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
           currentActiveConnection.setAutoCommit(false);
           RichAnnCancController ranc = new RichAnnCancController(currentActiveConnection, logger);
           AnnullamentoManager annMan = new AnnullamentoManager(currentActiveConnection, logger);
           
           // ciclo lettura richieste da evadere, uso scheda dati comuni per avere una istanza valida della classe
           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(IdentificativoSchede.TAB_INFO_COMUNI, currentActiveConnection, logger);

           List<RichiestaAnnullamento> lista = annAction.getRichiesteAnnullamentoBean(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE, null, ProfiloEnum.REGIONE_999, null, 0, 9999999, canc);
           
           logger.info("Inizio elaborazione rigetta richieste " + (doIt ? "" : " (simulazione)") 
                 + (canc ? "(rich.canc)" : " (rich.mod)"));
			
           for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
              richiestaAnnullamento = (RichiestaAnnullamento) iterator.next();
              
//if(richiestaAnnullamento.getId_richiesta() != 1931)
//   continue;

              richiestaAnnullamento.setDecisore(currentUser.getLogin());
              richiestaAnnullamento.setMotivo_esito("Rigettata da assistenza tecnica (bonifica dati)");
              // aggiungo esito e decisore
              richiestaAnnullamento.setEsito(RichiestaAnnullamento.RICHIESTA_RIFIUTATA);

              // in base al blocco uso la classe appropriata
              annAction = AnnullamentoFactory.getAction(richiestaAnnullamento.getBlocco(), currentActiveConnection, logger);
              
              IdentificativoSchede scheda = IdentificativoSchede.findIdentificativoByBlocco(richiestaAnnullamento.getBlocco());
              // controllo se la scheda è ancora in richiesta, altrimenti è un duplicato e lo cancello semplicemente
              SchedaState stato = null;
              
              switch (scheda.getIndiceScheda()) {
                 case IdentificativoSchede.INDICE_DATI_COMUNI:
                 case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
                 case IdentificativoSchede.INDICE_ADESIONE:
                 case IdentificativoSchede.INDICE_SOTTOSOGLIA:
                 case IdentificativoSchede.INDICE_ESCLUSO:
                 case IdentificativoSchede.INDICE_FASE_INIZIALE:
                 case IdentificativoSchede.INDICE_STIPULA:
                 case IdentificativoSchede.INDICE_FINE_LAVORI:
                 case IdentificativoSchede.INDICE_COLLAUDO:
                    // chiamo il metodo come le multiple per usare gli id scheda invece che id aggiudicazione
                    stato = ranc.getSchedaState(scheda.getNomeScheda(), true, Long.valueOf(richiestaAnnullamento.getId_record()), richiestaAnnullamento.getData_inizio_record());
                    break;

                 case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
                 case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
                 case IdentificativoSchede.INDICE_SOSPENSIONE:
                 case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
                 case IdentificativoSchede.INDICE_SUBAPPALTO:
                 case IdentificativoSchede.INDICE_VARIANTE:
                    stato = ranc.getSchedaState(scheda.getNomeScheda(), true, Long.valueOf(richiestaAnnullamento.getId_record()), richiestaAnnullamento.getData_inizio_record());
                    break;

                 default:
                    break;
              }
              
              // elaborazione richieste di modifica
              if(!canc){
                 if(!stato.isRichAnn()){
                    // scheda non ha richiesta in corso, possibile duplicato, cancello il record della richiesta
                    logger.info("ID_RICHIESTA: " + richiestaAnnullamento.getId_richiesta() + " - stato scheda non coerente");
   
                    if(doIt){
                       annMan.deleteRecordAnnullamento(Long.parseLong(richiestaAnnullamento.getId_record()), richiestaAnnullamento.getData_inizio_record(), richiestaAnnullamento.getBlocco());
                    }
                    retDel++;
                 }
                 else {
                    try{
                       // scheda ha richiesta in corso
                       // rigetto la richiesta
                       logger.info("ID_RICHIESTA: " + richiestaAnnullamento.getId_richiesta() + " - rigetto");
                       if(doIt){
                          boolean result = annAction.gestisciRichiesta(richiestaAnnullamento,currentUser.getLogin());
                       }
                       retRig++;
                    }catch(Exception e){
                       // errore imprevisto cancello il rekord
                       logger.info("*** ID_RICHIESTA: " + richiestaAnnullamento.getId_richiesta() + " - ERRORE: " + e.getMessage());
                       if(doIt){
                          rollback(currentActiveConnection);
                          annMan.deleteRecordAnnullamento(Long.parseLong(richiestaAnnullamento.getId_record()), richiestaAnnullamento.getData_inizio_record(), richiestaAnnullamento.getBlocco());
                          retDel++;
                       }
                    }
                 }
              }
              
              // elaborazione richieste di cancellazione
              if(canc){
                 if(!stato.isRichDelete()){
                    // scheda non ha richiesta in corso, possibile duplicato, cancello il record della richiesta
                    logger.info("ID_RICHIESTA(del): " + richiestaAnnullamento.getId_richiesta() + " - stato scheda non coerente");
   
                    if(doIt){
                       annMan.deleteRecordCancellazione(Long.parseLong(richiestaAnnullamento.getId_record()), richiestaAnnullamento.getData_inizio_record(), richiestaAnnullamento.getBlocco());
                    }
                    retDel++;
                 }
                 else {
                    try{
                       // scheda ha richiesta in corso
                       // rigetto la richiesta
                       logger.info("ID_RICHIESTA(del): " + richiestaAnnullamento.getId_richiesta() + " - rigetto");
                       if(doIt){
                          boolean result = annAction.gestisciRichiestaCancellazione(richiestaAnnullamento,currentUser.getLogin());
                       }
                       retRig++;
                    }catch(Exception e){
                       // errore imprevisto cancello il rekord
                       logger.info("*** ID_RICHIESTA(del): " + richiestaAnnullamento.getId_richiesta() + " - ERRORE: " + e.getMessage());
                       if(doIt){
                          rollback(currentActiveConnection);
                          annMan.deleteRecordCancellazione(Long.parseLong(richiestaAnnullamento.getId_record()), richiestaAnnullamento.getData_inizio_record(), richiestaAnnullamento.getBlocco());
                          retDel++;
                       }
                    }
                 }
              }

              // finalizzo
              if(doIt)
                 commit(currentActiveConnection);

              //logger.debug(ObjectIntrospector.propertiesInfo(RichiestaAnnullamento.class, richiestaAnnullamento));
              elab ++;
           }
           
		}
		catch(Exception e){
				rollback(currentActiveConnection);
				logger.info("ID_RICHIESTA: " + (richiestaAnnullamento == null ? "" : richiestaAnnullamento.getId_richiesta()) + " - Eccezione: " + e.getMessage());
			}finally {
				closeConnection(request.getSession().getId(),getClass().getName());
			}
		
		retVal = "Fine elaborazione rigetta " 
              +  (doIt ? "" : " (simulazione)") 
              +  " - richieste: " + String.valueOf(elab)
              + " rigettate: " + String.valueOf(retRig)
              + " cancellate: " + String.valueOf(retDel)
              + (canc ? "(rich.canc)" : " (rich.mod)");
              
		logger.info(retVal);
		
        return retVal;
	}
}