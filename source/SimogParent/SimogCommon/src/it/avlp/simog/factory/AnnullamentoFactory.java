package it.avlp.simog.factory;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.common.action.AccordoAnnullamentoAction;
import it.avlp.simog.common.action.AggiudicazioniAnnullamentoAction;
import it.avlp.simog.common.action.AvanzamentoAnnullamentoAction;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.action.CollaudoAnnullamentoAction;
import it.avlp.simog.common.action.ConclusioniAnnullamentoAction;
import it.avlp.simog.common.action.DefaultAnnullamentoAction;
import it.avlp.simog.common.action.InfoComuniAnullamentoAction;
import it.avlp.simog.common.action.InizioLavoriAnnullamentoAction;
import it.avlp.simog.common.action.R129AnnullamentoAction;
import it.avlp.simog.common.action.SospensioniAnnullamentoAction;
import it.avlp.simog.common.action.StipulaAnnullamentoAction;
import it.avlp.simog.common.action.SubappaltiAnnullamentoAction;
import it.avlp.simog.common.action.VarianteAnnullamentoAction;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class AnnullamentoFactory {
	/*******************************************************************************************************
	 * Il metodo permette di associare ad una data scheda l'opportuna action per la richiesta di annullamento
	 * @param scheda String
	 * @param connection Connection
	 * @param logger Logger
	 * @return BaseRichiestaAnnullamento
	 */
	public static BaseRichiestaAnnullamento getAction(String scheda, Connection connection, Logger logger){
		if(IdentificativoSchede.TAB_AGGIUDICAZIONE.equals(scheda) || IdentificativoSchede.TAB_SOTTOSOGLIA.equals(scheda)|| 
				IdentificativoSchede.TAB_ESCLUSI.equals(scheda) || IdentificativoSchede.TAB_ADESIONE.equals(scheda))
			return new AggiudicazioniAnnullamentoAction(connection, logger);
		else if(IdentificativoSchede.TAB_INFO_COMUNI.equals(scheda))
			return new InfoComuniAnullamentoAction(connection, logger);
		else if(IdentificativoSchede.TAB_INIZIO_LAVORI.equals(scheda))
			return new InizioLavoriAnnullamentoAction(connection, logger);
		else if(IdentificativoSchede.TAB_STIPULA.equals(scheda))
			return new StipulaAnnullamentoAction(connection, logger);
		else if(ParametriServletAvanzamento.TAB_AVANZAMENTO.equals(scheda))
			return new AvanzamentoAnnullamentoAction(connection, logger);
		else if(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI.equals(scheda))
			return new ConclusioniAnnullamentoAction(connection, logger);
		else if(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO.equals(scheda))
			return new CollaudoAnnullamentoAction(connection, logger);
		else if(ParametriServletR129.TAB_SCHEDA_R129.equals(scheda))
			return new R129AnnullamentoAction(connection, logger);
		else if(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI.equals(scheda))
			return new SospensioniAnnullamentoAction(connection, logger);
		else if(ParametriServletAccordo.TAB_SCHEDA_ACCORDO.equals(scheda))
			return new AccordoAnnullamentoAction(connection, logger);
		else if(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI.equals(scheda))
			return new SubappaltiAnnullamentoAction(connection, logger);
		else if(ParametriServletVariante.TAB_SCHEDA_VARIANTE.equals(scheda))
			return new VarianteAnnullamentoAction(connection, logger);
		
		
		
		else return new DefaultAnnullamentoAction(connection,logger);
	}

}
