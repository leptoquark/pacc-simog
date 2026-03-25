package it.avlp.simog.db.generated;
//TICKET ALM #659 #4201
public class RICORSO_DELEGA {
public final static String TABLE_NAME = "RICORSO_DELEGA";
	
	
	public final static String ID_RICORSO_DELEGA = "ID_RICORSO_DELEGA";
	public final static String T_ID_RICORSO_DELEGA = TABLE_NAME + "."+ ID_RICORSO_DELEGA;
	
	
	
	
	public long ID_RICORSO_DELEGA_field;
	
	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INIZIO_VALIDITA_field;
	
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
