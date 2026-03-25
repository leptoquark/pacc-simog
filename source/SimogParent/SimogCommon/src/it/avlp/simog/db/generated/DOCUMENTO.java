package it.avlp.simog.db.generated; 

	/*
	*	FILE DOCUMENTO created dom 11/02/2007 21:55:42:781
	*/

public interface DOCUMENTO {

	public final static String TABLE_NAME = "DOCUMENTO";


	public final static String ID_DOCUMENTO = "ID_DOCUMENTO";
	public final static String T_ID_DOCUMENTO = "DOCUMENTO.ID_DOCUMENTO";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_documento();

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = "DOCUMENTO.ID_LOTTO";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_lotto();

	public final static String NOMEDOCUMENTO = "NOMEDOCUMENTO";
	public final static String T_NOMEDOCUMENTO = "DOCUMENTO.NOMEDOCUMENTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String getNomeDocumento();

	public final static String DOCUMENTO = "DOCUMENTO";
	public final static String T_DOCUMENTO = "DOCUMENTO.DOCUMENTO";
		// COLUMN TYPE [binary]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public byte[] getDocumento();
}