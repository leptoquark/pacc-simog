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
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * Questa classe si prefigge di caricare tutti i dati relativi:
 * 
 *  ad un CIG -> dati comuni
 *  
 *  od a un CUI -> altre schede
 *  
 * @author vletizia
 *
 */
public class LoadSituazioneByIdScheda extends LoadSituazioneByDatiAggiudicazione{ 


	
	public LoadSituazioneByIdScheda(Connection con, Logger logger){
		super(con,logger);
	}
	
	/**
	 * @see it.avlp.simog.flusso.business.impl.LoadSituazioneByDatiAggiudicazione#loadSituazioneByCIG(java.lang.String)
	 */
	public ArrayList<SituazioneSchedeAttuale> loadSituazioneByCIG(String CIG)throws SQLException,Exception{
		return super.loadSituazioneByCIG(CIG);
	}
//	
	/**
	 * @see it.avlp.simog.flusso.business.impl.LoadSituazioneByDatiAggiudicazione#loadSituazioneByCUI(java.lang.String)
	 */
	public SituazioneSchedeAttuale loadSituazioneByCUI(String CUI)throws SQLException,Exception{
		return super.loadSituazioneByCUI(CUI);
	}

	

	/**
	 * Effettua il caricamento della situazione attuale della scheda di cui identificativo in ingresso
	 * 
	 * @param identificativo
	 * @param cui
	 * @param idScheda
	 * @return
	 * @throws Exception
	 */
	public SituazioneSchedeAttuale loadSchedaByRefs(IdentificativoSchede identificativo, String cui, String idScheda)throws NotFound,SQLException,Exception{
		
		int indiceScheda = identificativo.getIndiceScheda();
		
		switch (indiceScheda) {
		
//		case IdentificativoSchede.INDICE_DATI_COMUNI:			
//			return this.loadSituazioneByInfoComuniIdSimog(cuiOrCig, idScheda);
		case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
		case IdentificativoSchede.INDICE_SOTTOSOGLIA:
		case IdentificativoSchede.INDICE_ESCLUSO:
		case IdentificativoSchede.INDICE_ADESIONE:
			return this.loadSituazioneByAggiudicazioneIdSimog(idScheda);
		case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
			return this.loadSituazioneByAccordoIdSimog(idScheda);
		case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
			return this.loadSituazioneByAvanzamentoIdSimog(idScheda);
		case IdentificativoSchede.INDICE_COLLAUDO:
			return this.loadSituazioneByCollaudoIdSimog(idScheda);
		case IdentificativoSchede.INDICE_FINE_LAVORI:
			return this.loadSituazioneByConclusioneIdSimog(idScheda);
		case IdentificativoSchede.INDICE_FASE_INIZIALE:
			return this.loadSituazioneByInizioIdSimog(idScheda);
        case IdentificativoSchede.INDICE_STIPULA:
           return this.loadSituazioneByStipulaIdSimog(idScheda);
		case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
			return this.loadSituazioneByR129IdSimog(idScheda);
		case IdentificativoSchede.INDICE_SOSPENSIONE:
			return this.loadSituazioneBySospensioneIdSimog(idScheda);
		case IdentificativoSchede.INDICE_SUBAPPALTO:
			return this.loadSituazioneBySubAppaltoIdSimog(idScheda);
		case IdentificativoSchede.INDICE_VARIANTE:
			return this.loadSituazioneByVarianteIdSimog(idScheda);
		default :
			throw new NotFound();
		}		
	}
	

//	/**
//	 * Effettua il caricamento della situazione attuale della scheda Info Comuni
//	 * tramite i parametri in ingresso CUI e idInfo
//	 * 
//	 * @param CUI
//	 * @param idSimog
//	 * @return
//	 */
//	public SituazioneSchedeAttuale loadSituazioneByInfoComuniIdSimog(String CUI, String idSimog)throws SQLException,Exception{
//		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();
//		
//		AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
//		AggiudicazioneBean aggiudicazioneBean = aggiudicazioneManager.getAggiudicazioneByProgAndCui(CUI, false);
//		
//		this.fillSituazione(situazioneCorrente, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
//
//
//		return situazioneCorrente;
//	}

	/**
	 * Effettua il caricamento della situazione attuale della scheda Aggiudicazioni
	 * tramite l'id della aggiudicazione
	 * 
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByAggiudicazioneIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();
			
		AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
		AggiudicazioneBean aggiudicazioneBean = aggiudicazioneManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());

		return situazioneCorrente;
	}

	/**
	 * Effettua il caricamento della situazione attuale della scheda Accordo
	 * tramite l'id dell'accordo
	 * 
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByAccordoIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();
		
		AccordoManager accordoManager = new AccordoManager(con, logger);
		AccordoBean accordoBean = accordoManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, accordoBean.getIdAggiudicazione(), accordoBean.getDataInizioAggiudicazione());

		return situazioneCorrente;
	}

	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByAvanzamentoIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		AvanzamentoManager avanzamentoManager = new AvanzamentoManager(con, logger);
		AvanzamentoBean avanzamentoBean = avanzamentoManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, avanzamentoBean.getIdAggiudicazione(), avanzamentoBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}

	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByCollaudoIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		CollaudoManager collaudoManager = new CollaudoManager(con, logger);
		CollaudoBean collaudoBean = collaudoManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, collaudoBean.getIdAggiudicazione(), collaudoBean.getDataIniAggiudicazione());

		return situazioneCorrente;		
	}	
	

	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByConclusioneIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		ConclusioniManager conclusioniManager = new ConclusioniManager(con, logger);
		ConclusioneBean conclusioneBean = conclusioniManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, conclusioneBean.getIdAggiudicazione(), conclusioneBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}

	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByInizioIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		InizioLavoriManager inizioLavoriManager = new InizioLavoriManager(con, logger);
		InizioLavoriBean inizioLavoriBean = inizioLavoriManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, inizioLavoriBean.getIdAggiudicazione(), inizioLavoriBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}	

	   /**
     * @param idSimog
     * @return
     */
    public SituazioneSchedeAttuale loadSituazioneByStipulaIdSimog(String idSimog)throws SQLException,Exception{
        SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

        StipulaManager stipulaManager = new StipulaManager(con, logger);
        StipulaBean stipulaBean = stipulaManager.loadByIdSimog(new Long(idSimog).longValue());
        
        this.fillSituazione(situazioneCorrente, stipulaBean.getIdAggiudicazione(), stipulaBean.getDataInizioAggiudicazione());

        return situazioneCorrente;      
    }
	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByR129IdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		R129Manager recessoManager = new R129Manager(con, logger);
		R129Bean recessoBean = recessoManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, recessoBean.getIdAggiudicazione(), recessoBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}	

	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneBySospensioneIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		SospensioniManager sospensioniManager = new SospensioniManager(con, logger);
		SospensioniBean sospensioneBean = sospensioniManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, sospensioneBean.getIdAggiudicazione(), sospensioneBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}	

	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneBySubAppaltoIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		SubappaltiManager subAppaltimanager = new SubappaltiManager(con, logger);
		SubappaltiBean subAppaltiBean = subAppaltimanager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, subAppaltiBean.getIdAggiudicazione(), subAppaltiBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}	

	/**
	 * @param idSimog
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByVarianteIdSimog(String idSimog)throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneCorrente = new SituazioneSchedeAttuale();

		VarianteManager varianteManager = new VarianteManager(con, logger);
		VarianteBean varianteBean = varianteManager.loadByIdSimog(new Long(idSimog).longValue());
		
		this.fillSituazione(situazioneCorrente, varianteBean.getIdAggiudicazione(), varianteBean.getDataInizioAggiudicazione());

		return situazioneCorrente;		
	}
	

	
	
	
	

}
