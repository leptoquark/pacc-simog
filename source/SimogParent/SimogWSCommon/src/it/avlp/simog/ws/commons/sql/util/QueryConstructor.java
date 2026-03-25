package it.avlp.simog.ws.commons.sql.util;


	/**
	 * Classe creata ad hoc per la costruzione di query dipendenti da db
	 * (sql proprietario), propabile utilizzo in SIMOG WEB
	 * **/

public class QueryConstructor {
	/*
	 * probabilmente utile in futuro per determinare che tipo di db e' usato
	 * e formattare le query di conseguenza
	 * */
	//private static SimogProperties sp = ConfigurationManager.getInstance().getSimogProperties();

	private final static String MYSQLselectIdOnInsert = 
		"BEGIN "
		+"replaceMe ;"
		+"SELECT LAST_INSERT_ID();" 
		+" END";

	/***************************************************
	 * in base al valore di db trovato in simogproperties
	 * costruisco la query dipendente dal db
	 * @param query : String
	 * @return String contenente la query adatta al db
	 */
	public static String costruisci(String query){
		//in base al valore di db trovato in simogproperties
		//costruisco la query dipendente dal db
		//sp.dammiNomeOIdentificativoDiDB
		return MYSQLselectIdOnInsert.replaceAll("replaceMe", query);
	}
	/*	test	*/
//	public static void main(String[] args){
//		/* ---- print --------
//		 * BEGIN 
//		 * INSERT INTO WS_SESSIONS (USER_ID, SESSION_START, SESSION_END, SESSION_STATUS, COMANDO ) 
//		 * VALUES (?, ?, ?, ?, ?) ;
//		 * SELECT LAST_INSERT_ID(); 
//		 * END*/
//		String INSERT_BEGIN_SESSION = 
//			"INSERT INTO "	+ WS_SESSIONS.TABLE_NAME
//			+ " (" 
//			+ WS_SESSIONS.USER_ID			+ ", " 
//			+ WS_SESSIONS.SESSION_START		+ ", " 
//			+ WS_SESSIONS.SESSION_END		+ ", " 
//			+ WS_SESSIONS.SESSION_STATUS	+ ", " 
//			+ WS_SESSIONS.COMANDO
//			+ " ) VALUES (?, ?, ?, ?, ?)";
//		
//	}

}
