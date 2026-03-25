package it.avlp.simog.db.generated;
//TICKET ALM #3836
public class ART_REGIMI_PARTICOLARI_DI_APPALTO {
public final static String TABLE_NAME = "ART_REGIMI_PARTICOLARI_DI_APPALTO";
	
	
	public final static String ID_ART_REGIME = "ID_ART_REGIME";
	public final static String T_ID_ART_REGIME = TABLE_NAME + "."+ ID_ART_REGIME;
	
	
	
	
	public long ID_ART_REGIME_field;
	
	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_FINE_VALIDITA_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ULTIMA_MODIFICA_field;
}
