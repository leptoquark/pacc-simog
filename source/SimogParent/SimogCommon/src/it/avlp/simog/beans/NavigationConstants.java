package it.avlp.simog.beans;

import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletStipula;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;

public interface NavigationConstants {
	
	public static final String PATH_DATI_COMUNI = PSBD.SRV_DATI_COMUNI;
	public static final String PATH_AGGIUDICAZIONE = ParametriServlet.SRV_SCHEDA_A;
	public static final String PATH_INIZIO_LAVORI = ParametriServletInizioLavori.SRV_INIZIO_LAVORI;
	public static final String PATH_AVANZAMENTO_LAVORI = ParametriServletAvanzamento.SRV_SCHEDA_AVANZAMENTO + "?toDo=loadAll";
	public static final String PATH_CONCLUSIONE = ParametriServletConclusioni.SRV_SCHEDA_CONCLUSIONI;
	public static final String PATH_COLLAUDO =ParametriServletCollaudo.SRV_SCHEDA_COLLAUDO;
	public static final String PATH_R129 = ParametriServletR129.SRV_SCHEDA_R129 + "?toDo=loadAll";
	public static final String PATH_ACCORDI = ParametriServletAccordo.SRV_SCHEDA_ACCORDO + "?toDo=loadAll";
	public static final String PATH_SOSPENSIONI = ParametriServletSospensioni.SRV_SCHEDA_SOSPENSIONI + "?toDo=loadAll";
	public static final String PATH_SUBAPPALTO = ParametriServletSubappalti.SRV_SCHEDA_SUBAPPALTI + "?toDo=loadAll";
	public static final String PATH_VARIANTE = ParametriServletVariante.SRV_SCHEDA_VARIANTE + "?toDo=loadAll";
	// PP stipula
	public static final String PATH_STIPULA = ParametriServletStipula.SRV_STIPULA;
	// gm adesione
	public static final String PATH_ADESIONE = ParametriServlet.SRV_SCHEDA_ADESIONE;
	public static final String PATH_SOTTOSOGLIA = ParametriServlet.SRV_SCHEDA_SOTTOSOGLIA;
	public static final String PATH_ESCLUSI = ParametriServlet.SRV_SCHEDA_ESCLUSI;	
}
