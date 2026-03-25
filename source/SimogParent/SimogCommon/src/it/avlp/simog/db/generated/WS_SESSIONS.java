package it.avlp.simog.db.generated; 
	/*
	*	FILE WS_SESSIONS created lun 17/08/2009 13:45:36:687
	*/

public class WS_SESSIONS {

	public final static String TABLE_NAME = "WS_SESSIONS";


	public final static String SESSION_ID = "SESSION_ID";
	public final static String T_SESSION_ID = TABLE_NAME + "." + SESSION_ID;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long SESSION_ID_field;

	public final static String USER_ID = "USER_ID";
	public final static String T_USER_ID = TABLE_NAME + "." + USER_ID;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [25]
		// NULLABLE [YES]

	public String USER_ID_field;

	public final static String XMLAUTH = "XMLAUTH";
	public final static String T_XMLAUTH = TABLE_NAME + "." + XMLAUTH;
		// COLUMN TYPE [text]
		// COLUMN SIZE [2147483647]
		// NULLABLE [YES]

	public String XMLAUTH_field;

	public final static String USERSTATUS = "USERSTATUS";
	public final static String T_USERSTATUS = TABLE_NAME + "." + USERSTATUS;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [5]
		// NULLABLE [YES]

	public String USERSTATUS_field;

	public final static String SESSION_START = "SESSION_START";
	public final static String T_SESSION_START = TABLE_NAME + "." + SESSION_START;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date SESSION_START_field;

	public final static String SESSION_END = "SESSION_END";
	public final static String T_SESSION_END = TABLE_NAME + "." + SESSION_END;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date SESSION_END_field;

	public final static String TICKET = "TICKET";
	public final static String T_TICKET = TABLE_NAME + "." + TICKET;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [YES]

	public String TICKET_field;

	public final static String LAST_ERROR = "LAST_ERROR";
	public final static String T_LAST_ERROR = TABLE_NAME + "." + LAST_ERROR;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String LAST_ERROR_field;

	public final static String SESSION_STATUS = "SESSION_STATUS";
	public final static String T_SESSION_STATUS = TABLE_NAME + "." + SESSION_STATUS;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SESSION_STATUS_field;

	public final static String COMANDO = "COMANDO";
	public final static String T_COMANDO = TABLE_NAME + "." + COMANDO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String COMANDO_field;

	public final static String COLLABORAZIONE = "COLLABORAZIONE";
	public final static String T_COLLABORAZIONE = TABLE_NAME + "." + COLLABORAZIONE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int COLLABORAZIONE_field;
	
	public final static String RPNT_ID = "RPNT_ID";
	public final static String T_RPNT_ID = TABLE_NAME + "." + RPNT_ID;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [25]
		// NULLABLE [YES]

	public String RPNT_ID_field;
}
