package it.avlp.simog.tabmanager.xml.parser;

import it.avlp.simog.db.advanced.TableBean;



public interface TabellaLoaderObserver {
	
	
	public TableBean getTableByOperazione (String operazioneValue);
	public void setTableName(String nomeTabella);
//	public void addRow(	String idName, String idValue, String descrizioneName, String descrizionevalue, String operazioneName, String operazioneValue);

}
