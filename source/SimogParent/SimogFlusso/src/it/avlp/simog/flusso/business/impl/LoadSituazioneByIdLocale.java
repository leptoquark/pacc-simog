package it.avlp.simog.flusso.business.impl;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.r129.R129Manager;
import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.fllusso.action.RichiesteAction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class LoadSituazioneByIdLocale extends LoadSituazioneByDatiAggiudicazione {

	public LoadSituazioneByIdLocale(Connection con, Logger logger){
		super(con,logger);
	}
	/**
	 * @see it.avlp.simog.flusso.business.impl.LoadSituazioneByDatiAggiudicazione#loadSituazioneByCIG(java.lang.String)
	 */
	public ArrayList<SituazioneSchedeAttuale> loadSituazioneByCIG(String CIG) throws SQLException,Exception{
		return super.loadSituazioneByCIG(CIG);
	}
//	
	/**
	 * @see it.avlp.simog.flusso.business.impl.LoadSituazioneByDatiAggiudicazione#loadSituazioneByCUI(java.lang.String)
	 */
	public SituazioneSchedeAttuale loadSituazioneByCUI(String CUI) throws SQLException,Exception{
		return super.loadSituazioneByCUI(CUI);
	}
	/**
	 * Metodo che serve a caricare la scheda di cui riferimenti in ingresso
	 * 
	 * @param identificativo
	 * @param rifSimog: e' il parametro simog che rende univoco localmente l'id locale puo essere (cig,cui,idScheda)
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSchedaByIdLocale(IdentificativoSchede identificativo, String CIG, String CUI,String idAggiudicazione, String idLocale) throws NotFound,SQLException,Exception{
		
		int indiceScheda = identificativo.getIndiceScheda();
		
		switch (indiceScheda) {
		
		case IdentificativoSchede.INDICE_DATI_COMUNI:			
			return this.loadSituazioneByInfoComuniIdLocale(CIG, CUI, idLocale);
		case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
			return this.loadSituazioneByAggiudicazioneIdLocale(CUI, idLocale, TipoAggiudicazione.A);
		case IdentificativoSchede.INDICE_SOTTOSOGLIA:
			return this.loadSituazioneByAggiudicazioneIdLocale(CUI, idLocale, TipoAggiudicazione.S);
		case IdentificativoSchede.INDICE_ESCLUSO:
			return this.loadSituazioneByAggiudicazioneIdLocale(CUI, idLocale, TipoAggiudicazione.E);
		case IdentificativoSchede.INDICE_ADESIONE:
			return this.loadSituazioneByAggiudicazioneIdLocale(CUI, idLocale, TipoAggiudicazione.Q);
		case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
			return this.loadSituazioneByAccordoIdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
			return this.loadSituazioneByAvanzamentoIdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_COLLAUDO:
			return this.loadSituazioneByCollaudoIdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_FINE_LAVORI:
			return this.loadSituazioneByConclusioneIdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_FASE_INIZIALE:
			return this.loadSituazioneByInizioIdLocale(idAggiudicazione, idLocale);
        case IdentificativoSchede.INDICE_STIPULA:
           return this.loadSituazioneByStipulaIdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
			return this.loadSituazioneByR129IdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_SOSPENSIONE:
			return this.loadSituazioneBySospensioneIdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_SUBAPPALTO:
			return this.loadSituazioneBySubAppaltoIdLocale(idAggiudicazione, idLocale);
		case IdentificativoSchede.INDICE_VARIANTE:
			return this.loadSituazioneByVarianteIdLocale(idAggiudicazione, idLocale);
		default :
			throw new NotFound();
		}		
	}
	/**
	 * @param CIG
	 * @param CUI
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByInfoComuniIdLocale(String CIG, String CUI, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();
		
		AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
		AggiudicazioneBean aggiudicazioneBean = aggiudicazioneManager.getAggiudicazioneByProgAndCui(CUI, false);

		this.fillSituazione(situazioneCorrente, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());


		return situazioneCorrente;
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneBySubAppaltoIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		SubappaltiManager subAppaltimanager = new SubappaltiManager(con, logger);
		SubappaltiBean subAppaltiBean = subAppaltimanager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, subAppaltiBean.getIdAggiudicazione(), subAppaltiBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneBySospensioneIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		SospensioniManager sospensioniManager = new SospensioniManager(con, logger);
		SospensioniBean sospensioneBean = sospensioniManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, sospensioneBean.getIdAggiudicazione(), sospensioneBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByR129IdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		R129Manager recessoManager = new R129Manager(con, logger);
		R129Bean recessoBean = recessoManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, recessoBean.getIdAggiudicazione(), recessoBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByInizioIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		InizioLavoriManager inizioLavoriManager = new InizioLavoriManager(con, logger);
		InizioLavoriBean inizioLavoriBean = inizioLavoriManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, inizioLavoriBean.getIdAggiudicazione(), inizioLavoriBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}
	   /**
     * @param idAggiudicazione
     * @param idLocale
     * @return
     */
    public SituazioneSchedeAttuale loadSituazioneByStipulaIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
        SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

        StipulaManager stipulaManager = new StipulaManager(con, logger);
        StipulaBean stipulaBean = stipulaManager.loadByIdLocale(idLocale, idAggiudicazione);
        
        this.fillSituazione(situazioneCorrente, stipulaBean.getIdAggiudicazione(), stipulaBean.getDataInizioAggiudicazione());

        return situazioneCorrente;      
    }
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByConclusioneIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		ConclusioniManager conclusionManager = new ConclusioniManager(con, logger);
		ConclusioneBean conclusioneBean = conclusionManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, conclusioneBean.getIdAggiudicazione(), conclusioneBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByCollaudoIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		CollaudoManager collaudoManager = new CollaudoManager(con, logger);
		CollaudoBean collaudoBean = collaudoManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, collaudoBean.getIdAggiudicazione(), collaudoBean.getDataIniAggiudicazione());

		return situazioneCorrente;		
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByAvanzamentoIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		AvanzamentoManager avanzamentoManager = new AvanzamentoManager(con, logger);
		AvanzamentoBean avanzamentoBean = avanzamentoManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, avanzamentoBean.getIdAggiudicazione(), avanzamentoBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}
	/**
	 * @param CUI
	 * @param idLocale
	 * @return
	 */
	
	
	public SituazioneSchedeAttuale loadSituazioneByAggiudicazioneIdLocale(String CUI, String idLocale, TipoAggiudicazione tipo)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();
			
		AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
		AggiudicazioneBean aggiudicazioneBean = aggiudicazioneManager.loadByIdLocale(idLocale, CUI);
		int inRichiesta = 0;
		RichiesteAction richiesteAction = new RichiesteAction(con,logger);
//		String blocco = IdentificativoSchede.getAggiudicazione().getDecodificaBlocco();
//		String idScheda = String.valueOf(aggiudicazioneBean.getIdAggiudicazione());
		
		
		switch (tipo) {
		case A:
			richiesteAction.isInRichiesta(IdentificativoSchede.getAggiudicazione().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
			situazioneCorrente.setStatoAggiudicazione(aggiudicazioneBean, inRichiesta);
			break;
		case S:
			richiesteAction.isInRichiesta(IdentificativoSchede.getSottosoglia().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
			situazioneCorrente.setStatoSottosoglia(aggiudicazioneBean, inRichiesta);
			break;
		case E:
			richiesteAction.isInRichiesta(IdentificativoSchede.getEscluso().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
			situazioneCorrente.setStatoEscluso(aggiudicazioneBean, inRichiesta);
			break;
		case Q:
			richiesteAction.isInRichiesta(IdentificativoSchede.getAdesione().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
			situazioneCorrente.setStatoAdesione(aggiudicazioneBean, inRichiesta);
			break;

		default:
			break;
		}
		
		 
		
		this.fillSituazione(situazioneCorrente, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());

		return situazioneCorrente;
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByVarianteIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		VarianteManager varianteManager = new VarianteManager(con, logger);
		VarianteBean varianteBean = varianteManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, varianteBean.getIdAggiudicazione(), varianteBean.getDataInizioAggiudicazione());


		return situazioneCorrente;		
	}
	/**
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByAccordoIdLocale(String idAggiudicazione, String idLocale)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		AccordoManager accordoManager = new AccordoManager(con, logger);
		AccordoBean accordoBean = accordoManager.loadByIdLocale(idLocale, idAggiudicazione);
		
		this.fillSituazione(situazioneCorrente, accordoBean.getIdAggiudicazione(), accordoBean.getDataInizioAggiudicazione());


		return situazioneCorrente;
	}
}
