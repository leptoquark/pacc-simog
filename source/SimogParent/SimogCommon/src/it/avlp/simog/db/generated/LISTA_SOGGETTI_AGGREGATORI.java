package it.avlp.simog.db.generated; 
	/*
	*	FILE LISTA_SOGGETTI_AGGREGATORI created gio 14/12/2006 12:14:41:734
	*/

public interface LISTA_SOGGETTI_AGGREGATORI {

	public final static String TABLE_NAME = "LISTA_SOGGETTI_AGGREGATORI";


	public final static String CF_SOGG_AGGREGATORE = "CF_SOGG_AGGREGATORE";
	public String getCF_Sogg_Aggregatore();
	public void setCF_Sogg_Aggregatore(String cf);
	
	public final static String ID_STAZIONE_APPALTANTE = "ID_STAZIONE_APPALTANTE";
	public final static String T_ID_STAZIONE_APPALTANTE = TABLE_NAME+"."+ID_STAZIONE_APPALTANTE;
	public String getID_STAZIONE_APPALTANTE();
	public void setID_STAZIONE_APPALTANTE(String id_stazione_appaltante);
	
	public final static String DENOMINAZIONE_SOGG_AGGREGATORE = "DENOMINAZIONE_SOGG_AGGREGATORE";
	public String getDenominazione_Sogg_Aggregatore();
	public void setDenominazione_Sogg_Aggregatore(String den);

	public final static String DATA_INIZIO = "DATA_INIZIO";
    public final static String DATA_FINE = "DATA_FINE";
	
	
}
