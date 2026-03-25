package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.POSIZ_AGGIUD;

public interface ParametriServletInizioLavori {
	
	public static String TAB_INIZIO_LAVORI = IdentificativoSchede.TAB_INIZIO_LAVORI;
	
	public static String ATTRIB_AGGIUDICATARI = "listAggiudicatari";
	
	public static String TAB_POSIZIONE_AGGIUDICATARI = "TabPosizioneAggiudicatari";
	
	//blocco contratti
	public static String POSIZIONE_AGGIUDICATARIO = "PosizioneAggiudicatario";  
	
	public static String INCARICATI = "Incaricati";

	public static String NR_RIGHE_POSIZIONI = "nrRighe"+POSIZIONE_AGGIUDICATARIO;
	
	public static String argsPos = "'"+PSBD.FIELD_NAME_AGG_DENOMINAZIONE+"','"+
										PSBD.FIELD_NAME_COD_FISC_POSIZIONI+"','"+
										PSBD.FIELD_NAME_AGG_ID_PAESE+"','"+
										POSIZ_AGGIUD.CODICE_INPS + "','"+
										POSIZ_AGGIUD.CODICE_INAIL + "','"+
										POSIZ_AGGIUD.CODICE_CASSA + "'";    
	
	public static String argsPosNascosti = "'"+PSBD.FIELD_NAME_AGG_ID_SOGG_POSIZIONI+"','"+
												PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI+"'";
						
	public static String ID_INIZIO_LAVORI = it.avlp.simog.db.generated.INIZIO_LAVORI.ID_INIZIO;
	public static String DATA_INIZIO_LAVORI = it.avlp.simog.db.generated.INIZIO_LAVORI.DATA_INIZIO_INIZIO;
	
	public static String POSIZIONE_AGGIUDICATARI_TABLEBEAN = "TableBeanPosizioneAggiudicatari";
	public static String INIZIO_LAVORI_TABLEBEAN = "TableBeanInizioLavori";
	public static String INCARICATI_TABLEBEAN = "TableBeanIncaricati";
	
	public static String SRV_INIZIO_LAVORI = "srvInizioLavori";
	public static String JSP_INIZIO_LAVORI = "schedaB1/inizioLavori.jsp";
	
	public static String SI_FLAG_RISERVA = "Si"+it.avlp.simog.db.generated.INIZIO_LAVORI.FLAG_RISERVA;
	public static String NO_FLAG_RISERVA = "No"+it.avlp.simog.db.generated.INIZIO_LAVORI.FLAG_RISERVA;
	public static String FIELD_NAME_CODICE_INAIL = POSIZ_AGGIUD.CODICE_INAIL;
	public static String FIELD_NAME_CODICE_INPS = POSIZ_AGGIUD.CODICE_INPS;
	public static String FIELD_NAME_CODICE_CASSA = POSIZ_AGGIUD.CODICE_CASSA;
	
	public static String FIELD_NAME_DATA_STIPULA = INIZIO_LAVORI.DATA_STIPULA;
	public static String FIELD_NAME_DATA_ESECUTIVITA = INIZIO_LAVORI.DATA_ESECUTIVITA;
	public static String FIELD_NAME_IMPORTO_CAUZIONE = INIZIO_LAVORI.IMPORTO_CAUZ;
	public static String FIELD_NAME_DATA_INI_PROG_ESEC = INIZIO_LAVORI.DATA_INI_PROG_ESEC;
	public static String FIELD_NAME_DATA_APP_PROG_ESEC = INIZIO_LAVORI.DATA_APP_PROG_ESEC;
	public static String FIELD_NAME_CONSEGNA_FRAZIONATA = INIZIO_LAVORI.FLAG_FRAZIONATA;
	public static String FIELD_NAME_DATA_VERB_PRIMA_CONSEGNA = INIZIO_LAVORI.DATA_VERBALE_CONS;
	public static String FIELD_NAME_DATA_VERB_CONSEGNA_DEF = INIZIO_LAVORI.DATA_VERBALE_DEF;
	public static String FIELD_NAME_CONSEGNA_RISERVA = INIZIO_LAVORI.FLAG_RISERVA;
	public static String FIELD_NAME_DATA_VERB_INIZIO = INIZIO_LAVORI.DATA_VERB_INIZIO;
	public static String FIELD_NAME_DATA_TERMINE = INIZIO_LAVORI.DATA_TERMINE;
	
	
	
	
}
