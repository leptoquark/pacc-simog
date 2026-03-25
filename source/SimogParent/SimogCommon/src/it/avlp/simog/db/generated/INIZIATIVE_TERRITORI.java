package it.avlp.simog.db.generated;

public interface INIZIATIVE_TERRITORI {

    public final static String TABLE_NAME = "INIZIATIVE_TERRITORI";
	
	public final static Boolean IDENTITY = Boolean.TRUE;
	
	public final static String ID_INIZIATIVE_TERRITORI = "ID_INIZIATIVE_TERRITORI";
	public final static String T_ID_INIZIATIVE_TERRITORI = TABLE_NAME+"."+ID_INIZIATIVE_TERRITORI;
	
	public long getIdIniziativeTerritori();
	public void setIdIniziativeTerritori(long id);
	
	public final static String ID_INIZIATIVA = "ID_INIZIATIVA";
	public final static String T_ID_INIZIATIVA = TABLE_NAME+"."+ID_INIZIATIVA;
	
	public long getIdIniziativa();
	public void setIdIniziativa(long id);
	
	public final static String ID_REGIONE = "ID_REGIONE";
	public final static String T_ID_REGIONE = TABLE_NAME+"."+ID_REGIONE;
	
	public String getIdRegione();
	public void setIdRegione(String idRegione);
	
}
