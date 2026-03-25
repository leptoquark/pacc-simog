package it.avlp.simog.db.generated;

import java.util.List;

public interface INIZIATIVE_SOGG_AGGR {

	public final static String TABLE_NAME = "INIZIATIVE_SOGG_AGGR";
	
	public final static Boolean IDENTITY = Boolean.TRUE;
	
	public final static String ID_INIZIATIVA = "ID_INIZIATIVA";
	public final static String T_ID_INIZIATIVA = TABLE_NAME+"."+ID_INIZIATIVA;
	
	public long getIdIniziativa();
	public void setIdIniziativa(long idIniziativa);
	
	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = TABLE_NAME+"."+ID_GARA;

	public long getIdGara();
	public void setIdGara(long idGara);
	
	public final static String CIG = "CIG";
	public final static String T_CIG = TABLE_NAME+"."+CIG;
	
	public String getCIG();
	public void setCIG(String CIG);
	
	public final static String DESCRIZIONE_SOGG_AGGR = "DESCRIZIONE_SOGG_AGGR";
	public final static String T_DESCRIZIONE_SOGG_AGGR = TABLE_NAME+"."+DESCRIZIONE_SOGG_AGGR;

	public String getDescrizioneSoggAggr();
	public void setDescrizioneSoggAggr(String descrizione);
	
	public final static String DESCRIZIONE_INIZIATIVA = "DESCRIZIONE_INIZIATIVA";
	public final static String T_DESCRIZIONE_INIZIATIVA = TABLE_NAME+"."+DESCRIZIONE_INIZIATIVA;

	public String getDescrizioneIniziativa();
	public void setDescrizioneIniziativa(String descrizione);
	
	public final static String SSAA_RIF = "SSAA_RIF";
	public final static String T_SSAA_RIF = TABLE_NAME+"."+SSAA_RIF;

	public String getSSAARif();
	public void setSSAARif(String ssaarif);
	
	public final static String STATO_INIZIATIVA = "STATO_INIZIATIVA";
	public final static String T_STATO_INIZIATIVA = TABLE_NAME+"."+STATO_INIZIATIVA;

	public String getStatoIniziativa();
	public void setStatoIniziativa(String stato);
	
	public final static String FLAG_CONFRONTO_COMP = "FLAG_CONFRONTO_COMP";
	public final static String T_FLAG_CONFRONTO_COMP = TABLE_NAME+"."+FLAG_CONFRONTO_COMP;

	public String getFlagConfrontoComp();
	public void setFlagConfrontoComp(String flagConfrontoComp);
	
	public final static String NOTE = "NOTE";
	public final static String T_NOTE = TABLE_NAME+"."+NOTE;

	public String getNote();
	public void setNote(String note);
	
	public final static String LINK = "LINK";
	public final static String T_LINK = TABLE_NAME+"."+LINK;

	public String getLink();
	public void setLink(String link);
	
}
