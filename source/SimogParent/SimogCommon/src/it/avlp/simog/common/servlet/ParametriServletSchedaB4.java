package it.avlp.simog.common.servlet;


public interface ParametriServletSchedaB4 {
	
	public static final String FINE_LAVORI_TABLEBEAN = "FineLavoriTableBean";
	public static final String SUBAPPALTI_TABLEBEAN = "SubappaltiTableBean";
	public static final String RESPONSABILI_FINE_TABLEBEAN = "ResponsabiliFineTableBean";
	
	
	public static final String SRV_SCHEDA_B4 = "SrvSchedaB4";
	public static final String JSP_SCHEDA_B4 = "schedaB4/schedaB4.jsp";
	public static final String FORM_SCHEDA_B4 = "FormSchedaB4";
	
	public static final String TAB_FINE_LAVORI = "ETabFineLavori";
	public static final String TAB_SUBAPPALTI = "TabSubappalti";
	public static final String TAB_RESP_FINE = PSBD.RESPONSABILE;
	
	public static final String NR_RIGHE_SUBAPPALTI = "nrRighe" + TAB_SUBAPPALTI;
	public static final String NR_RIGHE_RESP_FINE = "nrRighe" +TAB_RESP_FINE;
	
	
	public static final String FINE_LAVORI_FLAG = "FineLavoriFlag";
	public static final String SUBAPPALTI_FLAG = "SubappaltiFlag";
	public static final String RESPONSABILI_FINE_FLAG= "ResponsabiliFineFlag";
	
	public static final String SI_MODO_COLLAUDO = "SiModoCollaudo";
	public static final String NO_MODO_COLLAUDO = "NoModoCollaudo";
	
	public static final String SI_ESITO_COLLAUDO = "SiEsitoCollaudo";
	public static final String NO_ESITO_COLLAUDO = "NoEsitoCollaudo";
	
	public static final String SENZA_ONERI = "senzaOneri";
	public static final String MANCATO_UTILE = "mancatoUtile";
	public static final String AVANZAMENTO_CONSEGUITO = "avanzamentoConseguito";
	public static final String ADDEBITO_DANNO_APPALTATORE = "addebitoDannoAppaltatore";
	
	public static final String FRODE = "perFrode";
	public static final String GRAVE_NEGLIGENZA = "graveNegligenza";
	public static final String INADEMPIMENTO_CONTRATTUALE = "inadempimentoContrattuale";
	public static final String GRAVE_RITARDO = "graveRitardo";
	public static final String argsSubNascosti = "";


}
