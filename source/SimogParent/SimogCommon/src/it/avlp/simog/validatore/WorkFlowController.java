package it.avlp.simog.validatore;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.NavigationBean;
import it.avlp.simog.beans.NavigationConstants;
import it.avlp.simog.beans.SchedaState;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.util.SimogProperties;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;
@SuppressWarnings("unused")
public class WorkFlowController { //extends BaseAction {
	
	protected Connection connection;
	protected Logger logger;
	private SimogFlusso flusso = null;
	
	public WorkFlowController(Connection activeConnection, Logger logger) {
		
	  //super(activeConnection, logger);
	  try {
		  paths = new HashMap<String, String>();
		  paths.put(IdentificativoSchede.getDatiComuni().getDecodificaVN(), NavigationConstants.PATH_DATI_COMUNI);
		  paths.put(IdentificativoSchede.getAggiudicazione().getDecodificaVN(), NavigationConstants.PATH_AGGIUDICAZIONE);
		  paths.put(IdentificativoSchede.getInizioLavori().getDecodificaVN(), NavigationConstants.PATH_INIZIO_LAVORI);
		  paths.put(IdentificativoSchede.getAvanzamenti().getDecodificaVN(), NavigationConstants.PATH_AVANZAMENTO_LAVORI);
		  paths.put(IdentificativoSchede.getConclusione().getDecodificaVN(), NavigationConstants.PATH_CONCLUSIONE);
		  paths.put(IdentificativoSchede.getCollaudo().getDecodificaVN(), NavigationConstants.PATH_COLLAUDO);
		  paths.put(IdentificativoSchede.getRitardo().getDecodificaVN(), NavigationConstants.PATH_R129);
		  paths.put(IdentificativoSchede.getAccordi().getDecodificaVN(), NavigationConstants.PATH_ACCORDI);
		  paths.put(IdentificativoSchede.getSospensioni().getDecodificaVN(), NavigationConstants.PATH_SOSPENSIONI);
		  paths.put(IdentificativoSchede.getVarianti().getDecodificaVN(), NavigationConstants.PATH_VARIANTE);
		  paths.put(IdentificativoSchede.getSubAppalti().getDecodificaVN(), NavigationConstants.PATH_SUBAPPALTO);
		  paths.put(IdentificativoSchede.getSottosoglia().getDecodificaVN(), NavigationConstants.PATH_SOTTOSOGLIA);
		  paths.put(IdentificativoSchede.getEscluso().getDecodificaVN(), NavigationConstants.PATH_ESCLUSI);
		  // PP stipula
		  paths.put(IdentificativoSchede.getStipula().getDecodificaVN(), NavigationConstants.PATH_STIPULA);
		  //gm adesione
		  paths.put(IdentificativoSchede.getAdesione().getDecodificaVN(), NavigationConstants.PATH_ADESIONE);
      
	  } catch (NotFound e) {
		e.printStackTrace();
	}
	
	  this.connection = activeConnection;
	  this.logger = logger;
	}
	
	
	private HashMap<String, String> paths;
		 
	
	private String buildPath(String viewName, boolean multiple, long id, Timestamp di){
		return ( paths.get(viewName) + (multiple? "&":"?") + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "="+id + "&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE +"="+di);
		
	}
	
	
	private NavigationBean getNavBeanForModule(String viewName, boolean multipla, String metodoRicercaStato, AggiudicazioneBean ab){
		NavigationBean navBean = new NavigationBean();
		navBean.setViewName(viewName);
		SchedaState isp = new SchedaState(new Long(ab.getIdStato()).intValue(),ab.getIdAggiudicazione());
		Method m = null;
			
			RichAnnCancController richCheck = new RichAnnCancController(connection, logger);
			
			if(metodoRicercaStato != null)
				isp = richCheck.getSchedaState(metodoRicercaStato, false, ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			
			navBean.setStato(isp.getState());
			
			if(metodoRicercaStato != null){
				navBean.setRichAnn(isp.isRichAnn());
				navBean.setRichDelete(isp.isRichDelete());
			}
			else{
				navBean.setRichAnn(ab.isRichAnn());
				navBean.setRichDelete(ab.isRichDelete());
			}
			
			navBean.setId(isp.getId());
			navBean.setPath(buildPath(viewName, multipla, ab.getIdAggiudicazione(),ab.getDataInizioAggiudicazione()));
			
		return navBean;
	}
	
    /********************************************************************************************************
     * Crea una linked Hash Map per il Bean di Navigazione, versione 3.02.3
     * @param bean AggiudicazioneBean
     * @param tipoEnte String
     * @param isDatiComuniAnnullati Boolean
     * @return LinkedHashMap&lt;String, NavigationBean&gt;
     * @throws SimogException
     */
    public LinkedHashMap<String, NavigationBean> createNavigationBean3023(AggiudicazioneBean bean, String tipoEnte,
                                  Boolean isDatiComuniAnnullati, String tipoContratto, 
                                  boolean delegaScheda, String flagEscluso,
                                  int modoReal, String dataPubb, float importo, 
                                  int garaDelegata, String dataCreazioneGara, int idSvolgimento
                                  )throws SimogException{
        
   // il tipo flusso lo decido con la nuova classe
   this.flusso = new SimogFlusso(tipoEnte, tipoContratto, flagEscluso, modoReal, dataPubb, importo,garaDelegata,dataCreazioneGara,0);
   
   // retrocompatibilita'�provo con la vecchia versione del metodo
   if(this.flusso.getTipoFlusso() == null){
      //TICKET ALM #2847
	  //if(modoReal == Costanti.MODOREAL_ACCORDO)
	   if(SimogFlags.isAccordoQuadroOrConvenzione(modoReal) || SimogFlags.isSvolgimentoAccordoQuadro(idSvolgimento))
         return createStipulaNavigationBean(bean, tipoEnte, isDatiComuniAnnullati, tipoContratto, delegaScheda);
      else
         return createAggiudicazioneNavigationBean(bean, tipoEnte, isDatiComuniAnnullati, tipoContratto, delegaScheda, dataCreazioneGara);
   }
   // Rinaldo Ticket 651 //////////////////////
   return buildAllNavigationBean(bean, tipoEnte, isDatiComuniAnnullati, tipoContratto, delegaScheda, modoReal,dataCreazioneGara);
   ////////////////////////////////////////////
   }
	
	/********************************************************************************************************
	 * Crea una linked Hash Map per il Bean di Navigazione
	 * 
	 * @param bean AggiudicazioneBean
	 * @param tipoEnte String
	 * @param isDatiComuniAnnullati Boolean
	 * @return LinkedHashMap&lt;String, NavigationBean&gt;
	 * @throws SimogException
	 */
	public LinkedHashMap<String, NavigationBean> createNavigationBean(AggiudicazioneBean bean, String tipoEnte,
			Boolean isDatiComuniAnnullati, String TipoContratto, boolean delegaScheda, TipoFlusso tipoFlusso)throws SimogException{
		if(TipoFlusso.STIPULA.equals(tipoFlusso))
			return createStipulaNavigationBean(bean, tipoEnte, isDatiComuniAnnullati, TipoContratto, delegaScheda);
		else
			return createAggiudicazioneNavigationBean(bean, tipoEnte, isDatiComuniAnnullati, TipoContratto, delegaScheda, "20991231" );
	
	}
	
	private LinkedHashMap<String, NavigationBean> createAggiudicazioneNavigationBean(AggiudicazioneBean bean, String tipoEnte, 
			Boolean isDatiComuniAnnullati, String TipoContratto, boolean delegaScheda, String dataCreazioneGara)throws SimogException{
		
		NavigationBean aggiudNb, inizioNb,avanzamentoNb,conclusioneNb, collaudoNb, nb2, stipulaNb;
		LinkedHashMap<String, NavigationBean> navMap = new LinkedHashMap<String, NavigationBean>();
		TipoAggiudicazione ta = bean.getSottotipo();
		boolean riaggiudicata = false, riaggiudicabile = false;
		
		
			
		try{
			if(ta.equals(TipoAggiudicazione.E)){
				aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getEscluso().getDecodificaVN(), false, null, bean);
				aggiudNb.setTab(IdentificativoSchede.TAB_ESCLUSI);
			}
			else if(ta.equals(TipoAggiudicazione.S)){
				aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getSottosoglia().getDecodificaVN(), false, null, bean);
				aggiudNb.setTab(IdentificativoSchede.TAB_SOTTOSOGLIA);
			}
			else if(ta.equals(TipoAggiudicazione.Q)){
				aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getAdesione().getDecodificaVN(), false, null, bean);
				aggiudNb.setTab(IdentificativoSchede.TAB_ADESIONE);
			}
			else {
				aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getAggiudicazione().getDecodificaVN(), false, null, bean);
				aggiudNb.setTab(IdentificativoSchede.TAB_AGGIUDICAZIONE);
			}
			aggiudNb.setReadonly(false || riaggiudicata);
			
			/** AGGIUDICAZIONE **/
			if(isDatiComuniAnnullati){
				aggiudNb.setReadonly(true);
			}
			aggiudNb.setDelegaScheda(delegaScheda);		
			riaggiudicata = isRiaggiudicataDefinitiva(bean.getIdInfo(), bean.getDataInizioInfo(), bean.getProgCUI());
			riaggiudicabile =isRevocata(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione()) && !riaggiudicata;
			navMap.put(aggiudNb.getTab(), aggiudNb);
		
				
		
			/*** settori speciali, sottosoglia, esclusi
			 ***********************************************/
			if((Costanti.TIPO_ENTE_SPECIALE.equals(tipoEnte)) || ((ta.equals(TipoAggiudicazione.A) == false && ta.equals(TipoAggiudicazione.Q)==false) && !SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneGara))) {
				conclusioneNb = this.getNavBeanForModule(IdentificativoSchede.getConclusione().getDecodificaVN(), false, IdentificativoSchede.FINE_LAVORI, bean);
				conclusioneNb.setTab(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
				conclusioneNb.setDelegaScheda(delegaScheda);
				
				conclusioneNb.setReadonly(
						aggiudNb.isRichAnn() 
						|| !aggiudNb.isDone() 
						|| isDatiComuniAnnullati
						|| aggiudNb.isRichDelete()
						|| riaggiudicata);

				// conlcusione non ammessa per sottosoglia ed esclusi
				if((!SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneGara) && !ta.equals(TipoAggiudicazione.E)) && !ta.equals(TipoAggiudicazione.S))
					navMap.put(conclusioneNb.getTab(), conclusioneNb);
				aggiudNb.setRiaggiudicabile(riaggiudicabile && aggiudNb.isDone() && conclusioneNb.isDone());
				aggiudNb.setRiaggiudicata(riaggiudicata);
				conclusioneNb.setRiaggiudicata(riaggiudicata);
				return navMap;
			}
			//gm controllo aggiunto per appalti multilotto
			if(bean.getCodiceContratto()==null || (bean.getFlagAggiudPrincipale()!=null && bean.getFlagAggiudPrincipale().equals(Costanti.FLAG_VALORE_SI))){
			inizioNb = this.getNavBeanForModule(IdentificativoSchede.getInizioLavori().getDecodificaVN(), false, IdentificativoSchede.FASE_INIZIALE, bean);
			inizioNb.setTab(IdentificativoSchede.TAB_INIZIO_LAVORI);
			inizioNb.setDelegaScheda(delegaScheda);
			avanzamentoNb = this.getNavBeanForModule(IdentificativoSchede.getAvanzamenti().getDecodificaVN(), true, IdentificativoSchede.STATO_AVANZAMENTO, bean);
			avanzamentoNb.setTab(ParametriServletAvanzamento.TAB_AVANZAMENTO);
			avanzamentoNb.setDelegaScheda(delegaScheda);
			conclusioneNb = this.getNavBeanForModule(IdentificativoSchede.getConclusione().getDecodificaVN(), false, IdentificativoSchede.FINE_LAVORI, bean);
			conclusioneNb.setTab(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
			conclusioneNb.setDelegaScheda(delegaScheda);
			collaudoNb = this.getNavBeanForModule(IdentificativoSchede.getCollaudo().getDecodificaVN(), false, IdentificativoSchede.COLLAUDO, bean);
			collaudoNb.setTab(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
			collaudoNb.setDelegaScheda(delegaScheda);
			
			
			//SCHEDE PRINCIPALI - > stato
			
			/** INIZIO LAVORI **/
			inizioNb.setReadonly((!aggiudNb.isDone() 
								|| aggiudNb.isRichAnn() 
								|| (conclusioneNb.isPresent() && !inizioNb.isPresent())) 
								|| isDatiComuniAnnullati
								|| riaggiudicata);
			
			// non inseribile se il padre e' in richiesta cancellazione
			if(!inizioNb.isPresent() && aggiudNb.isRichDelete())
				inizioNb.setReadonly(true);

		
			
			/** AVANZAMENTO **/
			avanzamentoNb.setReadonly((!inizioNb.isDone() 
								|| inizioNb.isRichAnn() 
								|| aggiudNb.isRichAnn() 
								|| !aggiudNb.isDone() 
								|| (collaudoNb.isPresent() && !avanzamentoNb.isPresent())) 
								|| isDatiComuniAnnullati
								|| riaggiudicata);
			
			// non inseribile se il padre e' in richiesta cancellazione
			if(!avanzamentoNb.isPresent() && inizioNb.isRichDelete())
				inizioNb.setReadonly(true);

			/** CONCLUSIONE **/
			if(avanzamentoNb.isPresent())
				conclusioneNb.setReadonly(!avanzamentoNb.isDone() 
											|| avanzamentoNb.isRichAnn() 
											|| isDatiComuniAnnullati
											|| riaggiudicata);
			else 
				if(inizioNb.isPresent())
					conclusioneNb.setReadonly(!inizioNb.isDone() 
												|| inizioNb.isRichAnn() 
												|| isDatiComuniAnnullati
												|| riaggiudicata);
				else
					conclusioneNb.setReadonly((!aggiudNb.isDone() && ta.equals(TipoAggiudicazione.A)) || isDatiComuniAnnullati || riaggiudicata);
			
			conclusioneNb.setReadonly((conclusioneNb.isReadonly() /*|| !inizioNb.isDone()*/ 
										|| aggiudNb.isRichAnn() 
										|| !aggiudNb.isDone()) 
										|| isDatiComuniAnnullati);
			
			// non inseribile se il padre e' in richiesta cancellazione
			if(!conclusioneNb.isPresent() 
					&& (inizioNb.isRichDelete()
						|| avanzamentoNb.isRichDelete()
						|| aggiudNb.isRichDelete()))
				conclusioneNb.setReadonly(true);

			/** COLLAUDO **/
			collaudoNb.setReadonly((!conclusioneNb.isDone() 
										|| conclusioneNb.isRichAnn() 
										|| aggiudNb.isRichAnn() 
										|| !aggiudNb.isDone()) 
										|| isDatiComuniAnnullati
										|| riaggiudicata);
			
			// non inseribile se il padre e' in richiesta cancellazione
			if(!collaudoNb.isPresent() && conclusioneNb.isRichDelete())
				collaudoNb.setReadonly(true);
			inizioNb.setRiaggiudicata(riaggiudicata);
			avanzamentoNb.setRiaggiudicata(riaggiudicata);
			conclusioneNb.setRiaggiudicata(riaggiudicata);
			collaudoNb.setRiaggiudicata(riaggiudicata);
			navMap.put(inizioNb.getTab(), inizioNb);
			navMap.put(avanzamentoNb.getTab(), avanzamentoNb);	
			navMap.put(conclusioneNb.getTab(), conclusioneNb);
			navMap.put(collaudoNb.getTab(), collaudoNb);
			riaggiudicabile = riaggiudicabile && ( ! inizioNb.isPresent() || inizioNb.isDone() ) && (!avanzamentoNb.isPresent() || avanzamentoNb.isDone() )&& conclusioneNb.isDone();
			
			/** IPOTESI DI RECESSO **/
			nb2 = getNavBeanForModule(IdentificativoSchede.getRitardo().getDecodificaVN(), true, IdentificativoSchede.IPOTESI_RECESSO, bean);
			nb2.setReadonly(inizioNb.isPresent() || conclusioneNb.isDone() || aggiudNb.isRichAnn() || !aggiudNb.isDone() || isDatiComuniAnnullati || riaggiudicata);
			nb2.setDelegaScheda(delegaScheda);
			// non inseribile se il padre e' in richiesta cancellazione
			if(!aggiudNb.isPresent() && aggiudNb.isRichDelete())
				nb2.setReadonly(true);

			if(aggiudNb.isDone() && !Costanti.TIPO_SCHEDA_LAVORI.equals(TipoContratto)){
				nb2.setReadonly(true);	// se non e' lavori la scheda è sempre nascosta
			}
			nb2.setTab(ParametriServletR129.TAB_SCHEDA_R129);
			nb2.setRiaggiudicata(riaggiudicata);
			navMap.put(nb2.getTab(), nb2);
			riaggiudicabile = riaggiudicabile && (!nb2.isPresent() || nb2.isDone());
			
			/** SOSPENSIONI **/
			nb2 = getNavBeanForModule(IdentificativoSchede.getSospensioni().getDecodificaVN(), true, IdentificativoSchede.SOSPENSIONE, bean);
			nb2.setReadonly(( !inizioNb.isDone() || conclusioneNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
			
			//nb2.setReadonly(( !inizioNb.isDone() || (conclusioneNb.isPresent() && !nb2.isPresent()) || (conclusioneNb.isPresent() && nb2.isDone()) || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati);
			
			// non inseribile se il padre e' in richiesta cancellazione
			if(!inizioNb.isPresent() && inizioNb.isRichDelete())
				nb2.setReadonly(true);
			nb2.setDelegaScheda(delegaScheda);
			nb2.setTab(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI);
			nb2.setRiaggiudicata(riaggiudicata);
			navMap.put(nb2.getTab(), nb2);
			riaggiudicabile = riaggiudicabile && (!nb2.isPresent() || nb2.isDone());
			/** SUBAPPALTO **/
			nb2 = getNavBeanForModule(IdentificativoSchede.getSubAppalti().getDecodificaVN(), true, IdentificativoSchede.SUBAPPALTO, bean);
			nb2.setReadonly(( /*! (inizioNb.isPresent() || conclusioneNb.isPresent()) */ collaudoNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
			nb2.setDelegaScheda(delegaScheda);
			// non inseribile se il padre e' in richiesta cancellazione
			if(!aggiudNb.isPresent() && aggiudNb.isRichDelete())
				nb2.setReadonly(true);

			nb2.setTab(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
			nb2.setRiaggiudicata(riaggiudicata);
			navMap.put(nb2.getTab(), nb2);
			riaggiudicabile = riaggiudicabile && (!nb2.isPresent() || nb2.isDone());
			/** VARIANTE **/
			nb2 = getNavBeanForModule(IdentificativoSchede.getVarianti().getDecodificaVN(), true, IdentificativoSchede.VARIANTE, bean);
			nb2.setReadonly( /*!inizioNb.isPresent() || conclusioneNb.isPresent())) ||*/ collaudoNb.isPresent() || (collaudoNb.isPresent() && !nb2.isPresent()) || (collaudoNb.isPresent() && nb2.isDone()) || aggiudNb.isRichAnn() || !aggiudNb.isDone() || isDatiComuniAnnullati || riaggiudicata);
//			nb2.setReadonly((collaudoNb.isPresent() && !nb2.isPresent()) || (collaudoNb.isPresent() && nb2.isDone()) || aggiudNb.isRichAnn() || !aggiudNb.isDone() || isDatiComuniAnnullati);
			nb2.setDelegaScheda(delegaScheda);
			// non inseribile se il padre e' in richiesta cancellazione
			if(!aggiudNb.isPresent() && aggiudNb.isRichDelete())
				nb2.setReadonly(true);

			nb2.setTab(ParametriServletVariante.TAB_SCHEDA_VARIANTE);
			nb2.setRiaggiudicata(riaggiudicata);
			
			//TICKET ALM - 3.04.3
			if(!SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneGara))
				nb2.setViewName("Variante");
				
			navMap.put(nb2.getTab(), nb2);
			riaggiudicabile = riaggiudicabile && (!nb2.isPresent() || nb2.isDone());
			/** ACCORDI BONARI **/
			nb2 = getNavBeanForModule(IdentificativoSchede.getAccordi().getDecodificaVN(), true, IdentificativoSchede.ACCORDO_BONARIO, bean);
			nb2.setDelegaScheda(delegaScheda);
			if( (inizioNb.isPresent() && !inizioNb.isDone())){
				nb2.setReadonly((!inizioNb.isDone() || !avanzamentoNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
			}else{
				if(conclusioneNb.isPresent())
					nb2.setReadonly((collaudoNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
				else 
					nb2.setReadonly((!inizioNb.isDone() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
			}
			
			// non inseribile se il padre e' in richiesta cancellazione
			if(!inizioNb.isPresent() && inizioNb.isRichDelete())
				nb2.setReadonly(true);
		
			nb2.setTab(ParametriServletAccordo.TAB_SCHEDA_ACCORDO);
			nb2.setRiaggiudicata(riaggiudicata);
			navMap.put(nb2.getTab(), nb2);
			riaggiudicabile = riaggiudicabile && (!nb2.isPresent() || nb2.isDone());
			aggiudNb.setRiaggiudicabile(riaggiudicabile);
			aggiudNb.setRiaggiudicata(riaggiudicata);
			
			return navMap;
			}
			else
				return navMap;
		}catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			throw new SimogException("WorkFlowController: Errore imprevisto -> ", e);
		}
	}
	
    private LinkedHashMap<String, NavigationBean> buildAllNavigationBean(AggiudicazioneBean bean, String tipoEnte, 
          Boolean isDatiComuniAnnullati, String TipoContratto, boolean delegaScheda, int modoReal, String dataCreazioneGara)throws SimogException{
      
      NavigationBean aggiudNb = null, inizioNb = null,avanzamentoNb = null,conclusioneNb = null, collaudoNb = null, nbRecesso, 
                     stipulaNb, nbSosp, nbSub, nbVar, nbAcco;
      LinkedHashMap<String, NavigationBean> navMap = new LinkedHashMap<String, NavigationBean>();
      TipoAggiudicazione ta = bean.getSottotipo();
      boolean riaggiudicata = false, riaggiudicabile = false;

      try{

         riaggiudicata = isRiaggiudicataDefinitiva(bean.getIdInfo(), bean.getDataInizioInfo(), bean.getProgCUI());
         
         if(ta.equals(TipoAggiudicazione.E)){
            aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getEscluso().getDecodificaVN(), false, null, bean);
            aggiudNb.setTab(IdentificativoSchede.TAB_ESCLUSI);
         }
         else if(ta.equals(TipoAggiudicazione.S)){
            aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getSottosoglia().getDecodificaVN(), false, null, bean);
            aggiudNb.setTab(IdentificativoSchede.TAB_SOTTOSOGLIA);
         }
         else if(ta.equals(TipoAggiudicazione.Q)){
            aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getAdesione().getDecodificaVN(), false, null, bean);
            aggiudNb.setTab(IdentificativoSchede.TAB_ADESIONE);
         }
         else {
            aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getAggiudicazione().getDecodificaVN(), false, null, bean);
            aggiudNb.setTab(IdentificativoSchede.TAB_AGGIUDICAZIONE);
         }
         aggiudNb.setDelegaScheda(delegaScheda);     
         aggiudNb.setReadonly(false || riaggiudicata || isDatiComuniAnnullati);
 
         conclusioneNb = this.getNavBeanForModule(IdentificativoSchede.getConclusione().getDecodificaVN(), false, IdentificativoSchede.FINE_LAVORI, bean);
         conclusioneNb.setTab(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
         conclusioneNb.setDelegaScheda(delegaScheda);
         inizioNb = this.getNavBeanForModule(IdentificativoSchede.getInizioLavori().getDecodificaVN(), false, IdentificativoSchede.FASE_INIZIALE, bean);
         inizioNb.setTab(IdentificativoSchede.TAB_INIZIO_LAVORI);
         inizioNb.setDelegaScheda(delegaScheda);
         avanzamentoNb = this.getNavBeanForModule(IdentificativoSchede.getAvanzamenti().getDecodificaVN(), true, IdentificativoSchede.STATO_AVANZAMENTO, bean);
         avanzamentoNb.setTab(ParametriServletAvanzamento.TAB_AVANZAMENTO);
         avanzamentoNb.setDelegaScheda(delegaScheda);
         collaudoNb = this.getNavBeanForModule(IdentificativoSchede.getCollaudo().getDecodificaVN(), false, IdentificativoSchede.COLLAUDO, bean);
         collaudoNb.setTab(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
         collaudoNb.setDelegaScheda(delegaScheda);
         nbRecesso = getNavBeanForModule(IdentificativoSchede.getRitardo().getDecodificaVN(), true, IdentificativoSchede.IPOTESI_RECESSO, bean);
         nbRecesso.setTab(ParametriServletR129.TAB_SCHEDA_R129);
         nbRecesso.setDelegaScheda(delegaScheda);
         nbSosp = getNavBeanForModule(IdentificativoSchede.getSospensioni().getDecodificaVN(), true, IdentificativoSchede.SOSPENSIONE, bean);
         nbSosp.setTab(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI);
         nbSosp.setDelegaScheda(delegaScheda);
         nbSub = getNavBeanForModule(IdentificativoSchede.getSubAppalti().getDecodificaVN(), true, IdentificativoSchede.SUBAPPALTO, bean);
         nbSub.setTab(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
         nbSub.setDelegaScheda(delegaScheda);
         nbVar = getNavBeanForModule(IdentificativoSchede.getVarianti().getDecodificaVN(), true, IdentificativoSchede.VARIANTE, bean);
         nbVar.setTab(ParametriServletVariante.TAB_SCHEDA_VARIANTE);
         nbVar.setDelegaScheda(delegaScheda);
		 
		 //TICKET ALM - 3.04.3
			if(!SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneGara))
				nbVar.setViewName("Variante");
		 
         nbAcco = getNavBeanForModule(IdentificativoSchede.getAccordi().getDecodificaVN(), true, IdentificativoSchede.ACCORDO_BONARIO, bean);
         nbAcco.setTab(ParametriServletAccordo.TAB_SCHEDA_ACCORDO);
         nbAcco.setDelegaScheda(delegaScheda);
         stipulaNb = getNavBeanForModule(IdentificativoSchede.getStipula().getDecodificaVN(), false, IdentificativoSchede.STIPULA, bean);
         stipulaNb.setTab(IdentificativoSchede.TAB_STIPULA);
         stipulaNb.setDelegaScheda(delegaScheda);

         // parametri che dipendono da altre situazioni
         aggiudNb.setRiaggiudicata(riaggiudicata);
         conclusioneNb.setRiaggiudicata(riaggiudicata);
         stipulaNb.setRiaggiudicata(riaggiudicata);
         inizioNb.setRiaggiudicata(riaggiudicata);
         avanzamentoNb.setRiaggiudicata(riaggiudicata);
         collaudoNb.setRiaggiudicata(riaggiudicata);
         nbSosp.setRiaggiudicata(riaggiudicata);
         nbRecesso.setRiaggiudicata(riaggiudicata);
         nbAcco.setRiaggiudicata(riaggiudicata);
         nbSub.setRiaggiudicata(riaggiudicata);
         nbVar.setRiaggiudicata(riaggiudicata);

         // riaggiudicabile se e' revocata e tutte le schede presenti sono confermate, senza richieste pending
         riaggiudicabile = isRevocata(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione()) && !riaggiudicata;
         riaggiudicabile = riaggiudicabile && (!stipulaNb.isPresent() || stipulaNb.isDone());                  
         riaggiudicabile = riaggiudicabile && ( ! inizioNb.isPresent() || inizioNb.isDone()); 
         riaggiudicabile = riaggiudicabile && (!avanzamentoNb.isPresent() || avanzamentoNb.isDone() );
         riaggiudicabile = riaggiudicabile && (!nbSosp.isPresent() || nbSosp.isDone());
         riaggiudicabile = riaggiudicabile && (!nbSub.isPresent() || nbSub.isDone());
         riaggiudicabile = riaggiudicabile && (!nbRecesso.isPresent() || nbRecesso.isDone());
         riaggiudicabile = riaggiudicabile && (!nbAcco.isPresent() || nbAcco.isDone());
         riaggiudicabile = riaggiudicabile && (!nbVar.isPresent() || nbVar.isDone());
         riaggiudicabile = riaggiudicabile && (!collaudoNb.isPresent() || collaudoNb.isDone());
        
         // Rinaldo Ticket 651 //////////////////////
//         3.04.8 34190 fix
         if ((modoReal == Costanti.MODOREAL_ADESIONE || modoReal == Costanti.MODOREAL_CONCESSIONE) && Costanti.TIPO_ENTE_SPECIALE.equals(tipoEnte) && !riaggiudicabile) 
        	 riaggiudicabile = true;
         ////////////////////////////////////////////
         aggiudNb.setRiaggiudicabile(riaggiudicabile && aggiudNb.isDone() && conclusioneNb.isDone());
         
         
         // per ogni scheda prevista nel flusso verifico le condizioni
         for (Iterator iterator = this.flusso.getSchede().iterator(); iterator.hasNext();) {
            IdentificativoSchede scheda = (IdentificativoSchede) iterator.next();

            switch (scheda.getIndiceScheda()) {
               case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
               case IdentificativoSchede.INDICE_ESCLUSO:
               case IdentificativoSchede.INDICE_ADESIONE:
               case IdentificativoSchede.INDICE_SOTTOSOGLIA:
                 
                 navMap.put(aggiudNb.getTab(), aggiudNb);
                 
                 break;
      
               case IdentificativoSchede.INDICE_FINE_LAVORI:             
                  conclusioneNb.setReadonly(
                          aggiudNb.isRichAnn() 
                          || !aggiudNb.isDone() 
                          || isDatiComuniAnnullati
                          || aggiudNb.isRichDelete()
                          || riaggiudicata);
      
                  if(avanzamentoNb.isPresent())
                     conclusioneNb.setReadonly(!avanzamentoNb.isDone() 
                                                 || avanzamentoNb.isRichAnn() 
                                                 || isDatiComuniAnnullati
                                                 || riaggiudicata);
                  else if(inizioNb.isPresent())
                         conclusioneNb.setReadonly(!inizioNb.isDone() 
                                                     || inizioNb.isRichAnn() 
                                                     || isDatiComuniAnnullati
                                                     || riaggiudicata);
                               
                 // non inseribile se il padre e' in richiesta cancellazione
                 if(!conclusioneNb.isPresent() 
                         && (inizioNb.isRichDelete()
                             || avanzamentoNb.isRichDelete()
                             || aggiudNb.isRichDelete()))
                     conclusioneNb.setReadonly(true);
   
                 // non inseribile se il padre e' in richiesta cancellazione
                 if(!conclusioneNb.isPresent() && (aggiudNb.isRichDelete() || inizioNb.isRichDelete()))
                    conclusioneNb.setReadonly(true);
   
                 navMap.put(conclusioneNb.getTab(), conclusioneNb);

                 break;
   
               case IdentificativoSchede.INDICE_FASE_INIZIALE:
                  inizioNb.setReadonly((!aggiudNb.isDone() 
                                   || aggiudNb.isRichAnn() 
                                   || (conclusioneNb.isPresent() && !inizioNb.isPresent())) 
                                   || isDatiComuniAnnullati
                                   || riaggiudicata);
               
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!inizioNb.isPresent() && aggiudNb.isRichDelete())
                      inizioNb.setReadonly(true);

                  navMap.put(inizioNb.getTab(), inizioNb);
                  
                  break;
   
               case IdentificativoSchede.INDICE_STIPULA:
                  stipulaNb.setReadonly(!aggiudNb.isDone() 
                                      || aggiudNb.isRichAnn() 
                                      || aggiudNb.isRichDelete()
                                      || isDatiComuniAnnullati
                                      || stipulaNb.isRichDelete() );

                  navMap.put(stipulaNb.getTab(), stipulaNb);

                  break;
                  
               case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
                  avanzamentoNb.setReadonly((!inizioNb.isDone() 
                        || inizioNb.isRichAnn() 
                        || aggiudNb.isRichAnn() 
                        || !aggiudNb.isDone() 
                        || (collaudoNb.isPresent() && !avanzamentoNb.isPresent())) 
                        || isDatiComuniAnnullati
                        || riaggiudicata);
    
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!avanzamentoNb.isPresent() && inizioNb.isRichDelete())
                     avanzamentoNb.setReadonly(true);
                  navMap.put(avanzamentoNb.getTab(), avanzamentoNb);  
                
                  break;
                  
               case IdentificativoSchede.INDICE_COLLAUDO:
                  collaudoNb.setReadonly((!conclusioneNb.isDone() 
                        || conclusioneNb.isRichAnn() 
                        || aggiudNb.isRichAnn() 
                        || !aggiudNb.isDone()) 
                        || isDatiComuniAnnullati
                        || riaggiudicata);
   
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!collaudoNb.isPresent() && conclusioneNb.isRichDelete())
                     collaudoNb.setReadonly(true);
                  
                  navMap.put(collaudoNb.getTab(), collaudoNb);
   
                  break;

               case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
                  nbRecesso.setReadonly(inizioNb.isPresent() || conclusioneNb.isDone() || aggiudNb.isRichAnn() || !aggiudNb.isDone() || isDatiComuniAnnullati || riaggiudicata);
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!aggiudNb.isPresent() && aggiudNb.isRichDelete())
                     nbRecesso.setReadonly(true);

                  if(aggiudNb.isDone() && !Costanti.TIPO_SCHEDA_LAVORI.equals(TipoContratto)){
                     nbRecesso.setReadonly(true);  // se non e' lavori la scheda e' sempre nascosta
                  }
                  navMap.put(nbRecesso.getTab(), nbRecesso);

                  break;

               case IdentificativoSchede.INDICE_SOSPENSIONE:
                  nbSosp.setReadonly(( !inizioNb.isDone() || conclusioneNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!inizioNb.isPresent() && inizioNb.isRichDelete())
                     nbSosp.setReadonly(true);

                  navMap.put(nbSosp.getTab(), nbSosp);

                  break;

               case IdentificativoSchede.INDICE_SUBAPPALTO:
                  nbSub.setReadonly((collaudoNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!aggiudNb.isPresent() && aggiudNb.isRichDelete())
                     nbSub.setReadonly(true);

                  nbSub.setRiaggiudicata(riaggiudicata);
                  navMap.put(nbSub.getTab(), nbSub);
                  riaggiudicabile = riaggiudicabile && (!nbSub.isPresent() || nbSub.isDone());
                  
                  break;
                  
               case IdentificativoSchede.INDICE_VARIANTE:
                  nbVar.setReadonly(collaudoNb.isPresent() || (collaudoNb.isPresent() && !nbVar.isPresent()) || (collaudoNb.isPresent() && nbVar.isDone()) || aggiudNb.isRichAnn() || !aggiudNb.isDone() || isDatiComuniAnnullati || riaggiudicata);
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!aggiudNb.isPresent() && aggiudNb.isRichDelete())
                     nbVar.setReadonly(true);

                  nbVar.setRiaggiudicata(riaggiudicata);
                  navMap.put(nbVar.getTab(), nbVar);
                  riaggiudicabile = riaggiudicabile && (!nbVar.isPresent() || nbVar.isDone());
                  
                  break;

               case IdentificativoSchede.INDICE_ACCORDO_BONARIO:

                  if( (inizioNb.isPresent() && !inizioNb.isDone())){
                     nbAcco.setReadonly((!inizioNb.isDone() || !avanzamentoNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
                  }else{
                      if(conclusioneNb.isPresent())
                         nbAcco.setReadonly((collaudoNb.isPresent() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
                      else 
                         nbAcco.setReadonly((!inizioNb.isDone() || aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || riaggiudicata);
                  }
                  
                  // non inseribile se il padre e' in richiesta cancellazione
                  if(!inizioNb.isPresent() && inizioNb.isRichDelete())
                     nbAcco.setReadonly(true);
              
                  navMap.put(nbAcco.getTab(), nbAcco);
                  
                  break; 
                  
               default:
                  break;
            }
         }
      
         return navMap;

      }catch (Exception e) {
          e.printStackTrace();
          logger.fatal(e);
          throw new SimogException("WorkFlowController: Errore imprevisto -> ", e);
      }
  }

	
	
	private boolean isRiaggiudicataDefinitiva(long idInfo, Timestamp dataInizioInfo, int progCUI)throws SQLException {
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		return man.isRevocataWithNewAgg(idInfo, dataInizioInfo, progCUI);
		
	}
	
	private boolean isRevocata(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException {
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		return man.isAggiudicazioneRevocata(idAggiudicazione, dataInizioAggiudicazione);
		
	}


    private LinkedHashMap<String, NavigationBean> createStipulaNavigationBean(AggiudicazioneBean bean, String tipoEnte, Boolean isDatiComuniAnnullati, String TipoContratto, boolean delegaScheda)throws SimogException{
		
		NavigationBean aggiudNb, stipulaNb ,conclusioneNb,  nb2;
		LinkedHashMap<String, NavigationBean> navMap = new LinkedHashMap<String, NavigationBean>();
		TipoAggiudicazione ta = bean.getSottotipo();		
		boolean riaggiudicata = false, riaggiudicabile = false;
		
		try{
			if(ta.equals(TipoAggiudicazione.E)){
				aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getEscluso().getDecodificaVN(), false, null, bean);
				aggiudNb.setTab(IdentificativoSchede.TAB_ESCLUSI);
			}
			else if(ta.equals(TipoAggiudicazione.S)){
				aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getSottosoglia().getDecodificaVN(), false, null, bean);
				aggiudNb.setTab(IdentificativoSchede.TAB_SOTTOSOGLIA);
			}
			else {
				aggiudNb = this.getNavBeanForModule(IdentificativoSchede.getAggiudicazione().getDecodificaVN(), false, null, bean);
				aggiudNb.setTab(IdentificativoSchede.TAB_AGGIUDICAZIONE);
			}
			aggiudNb.setReadonly(false);
			
			/** AGGIUDICAZIONE **/
			if(isDatiComuniAnnullati){
				aggiudNb.setReadonly(true);
			}
			aggiudNb.setDelegaScheda(delegaScheda);		
		
			riaggiudicata = isRiaggiudicataDefinitiva(bean.getIdInfo(), bean.getDataInizioInfo(), bean.getProgCUI());
			riaggiudicabile =isRevocata(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione()) && !riaggiudicata;
			aggiudNb.setRiaggiudicata(riaggiudicata);
			navMap.put(aggiudNb.getTab(), aggiudNb);
		
			
			/***************************************************/
			if(Costanti.TIPO_ENTE_SPECIALE.equals(tipoEnte) || (ta.equals(TipoAggiudicazione.A) == false && ta.equals(TipoAggiudicazione.Q)==false) ) {
				conclusioneNb = this.getNavBeanForModule(IdentificativoSchede.getConclusione().getDecodificaVN(), false, IdentificativoSchede.FINE_LAVORI, bean);
				conclusioneNb.setTab(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
				conclusioneNb.setDelegaScheda(delegaScheda);
				
				conclusioneNb.setReadonly(
						aggiudNb.isRichAnn() 
						|| !aggiudNb.isDone() 
						|| isDatiComuniAnnullati
						|| aggiudNb.isRichDelete()
						|| riaggiudicata);

				navMap.put(conclusioneNb.getTab(), conclusioneNb);
				
				
				aggiudNb.setRiaggiudicabile(riaggiudicabile && aggiudNb.isDone() && conclusioneNb.isDone());
				aggiudNb.setRiaggiudicata(riaggiudicata);
				conclusioneNb.setRiaggiudicata(riaggiudicata);
				return navMap;
			}
			
			
			stipulaNb = this.getNavBeanForModule(IdentificativoSchede.getStipula().getDecodificaVN(), false, IdentificativoSchede.STIPULA, bean);
			stipulaNb.setTab(IdentificativoSchede.TAB_STIPULA);
			stipulaNb.setDelegaScheda(delegaScheda);
			
			conclusioneNb = this.getNavBeanForModule(IdentificativoSchede.getConclusione().getDecodificaVN(), false, IdentificativoSchede.FINE_LAVORI, bean);
			conclusioneNb.setTab(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
			conclusioneNb.setDelegaScheda(delegaScheda);
			
			stipulaNb.setReadonly(!aggiudNb.isDone() 
								|| aggiudNb.isRichAnn() 
								|| aggiudNb.isRichDelete()
								|| isDatiComuniAnnullati
								|| stipulaNb.isRichDelete() );
			
		
			
				
		   conclusioneNb.setReadonly(!stipulaNb.isDone() 
									 || stipulaNb.isRichAnn() 
									 || isDatiComuniAnnullati
									 || aggiudNb.isRichDelete()
									 || aggiudNb.isRichAnn()
									 || stipulaNb.isRichDelete()
		   								);

		    stipulaNb.setRiaggiudicata(riaggiudicata);
		    conclusioneNb.setRiaggiudicata(riaggiudicata);
			navMap.put(stipulaNb.getTab(), stipulaNb);
			navMap.put(conclusioneNb.getTab(), conclusioneNb);
			riaggiudicabile = riaggiudicabile && (stipulaNb.isDone()) && conclusioneNb.isDone();
			
			
			
			/** SUBAPPALTO **/
			nb2 = getNavBeanForModule(IdentificativoSchede.getSubAppalti().getDecodificaVN(), true, IdentificativoSchede.SUBAPPALTO, bean);
			nb2.setReadonly((  aggiudNb.isRichAnn() || !aggiudNb.isDone()) || isDatiComuniAnnullati || aggiudNb.isRichDelete());
			nb2.setDelegaScheda(delegaScheda);
			riaggiudicabile = riaggiudicabile && ( !nb2.isPresent() || nb2.isDone() );

			nb2.setTab(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
			nb2.setRiaggiudicata(riaggiudicata);
			navMap.put(nb2.getTab(), nb2);
			
		
			aggiudNb.setRiaggiudicabile(riaggiudicabile);
			
		
			return navMap;
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			throw new SimogException("WorkFlowController: Errore imprevisto -> ", e);
		}
	}
	
	public void markViewName(HashMap<String, Boolean> validaMap, LinkedHashMap<String, NavigationBean> navMap){
		for(NavigationBean navBean: navMap.values()){
            if(validaMap.get(navBean.getTab()) != null){
               boolean ret = validaMap.get(navBean.getTab()).booleanValue();
               
               if(!ret && navBean.getStato() != StatiScheda.CONFERMATO)
                  ret = false;
               else
                  ret = true;
               
                  navBean.setValido(ret);
            }
            else
               navBean.setValido(true);

      }
	}
		
}
