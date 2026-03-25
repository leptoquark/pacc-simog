package it.avlp.simog.db.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Vector;


public class DatabaseNavigatorSQLServer  {

	public final static String TABLE				=	"TABLE";
	public final static String VIEW					=	"VIEW";
	public final static String SYSTEM_TABLE			=	"SYSTEM TABLE";
	public final static String GLOBAL_TEMPORARY		=	"GLOBAL TEMPORARY";
	public final static String LOCAL_TEMPORARY		=	"LOCAL TEMPORARY";
	public final static String ALIAS 				=	"ALIAS";
	public final static String SYNONYM 				=	"SYNONYM";

	public final static String[] ALL_TYPES = {
		TABLE
		, VIEW
		, SYSTEM_TABLE
		, GLOBAL_TEMPORARY
		, LOCAL_TEMPORARY
		, ALIAS
		, SYNONYM
	};
	

	private static String schema = "dbo";
	private static String path = "c:/tmp";
	private String extension = ".java";
	private String currentFormattedDate = null;
	private String packageName = "it.avlp.simog.db.generated";
	private static String dbName = "SIMOG_MEV_30";
	// NEcessario per SQLSERVER
	// ricordarsi di aggiungere all path del progetto lal ibreria del driver sql

	/**
	 * Contructor no args
	 */
	public DatabaseNavigatorSQLServer () {

		SimpleDateFormat formatter = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss:SSS" );
		currentFormattedDate = formatter.format( new java.util.Date() );
	}

	@SuppressWarnings("unchecked")
	public static void main ( String [] args ) throws Exception {
		
		System.out.println ("Archiviazione dei file in : " + path);
		
		Connection conn = null;
		
		try {
			System.out.println("Starting");

			System.out.println("Caricamento in corso del driver DB");
			// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			
		
			System.out.println("Tentativo Connessione db");
			
			// conn = DriverManager.getConnection("jdbc:odbc:simoglocal");
         conn = DriverManager.getConnection("jdbc:sqlserver://step_pp:1433;user=sa;password=step;SelectMethod=cursor;DatabaseName=SIMOG_MEV_30;sendStringParametersAsUnicode=false;lockTimeout=60000");
			 
			
			DatabaseNavigatorSQLServer dbn = new DatabaseNavigatorSQLServer();

			System.out.println("Informazioni relative alla connessione");
			DatabaseMetaData dbmtdt = conn.getMetaData();
			
			ResultSet rs = dbmtdt.getTables( dbName, schema, null, new String [] {TABLE,VIEW} );
			
			Vector tables = new Vector();

			while ( rs.next() ) {	
				tables.add(rs.getString("TABLE_NAME"));
			}
			rs.close();
			
			for ( Enumeration e = tables.elements(); e.hasMoreElements(); ) {
				dbn.createClassFile( ((String) e.nextElement() ).toUpperCase() , dbmtdt);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(); throw (e);
			
		}
		  finally {
			try {
				conn.close();
			} catch ( Exception nodone ) {}
			conn = null;
			System.out.println("Completed");
		}
	}

	/***************************************************************************************************
	 * Viene stampato il class file con le informazioni della tabella e del DataBAseMetaData
	 * @param tableName nome della tabella 
	 * @param dbmdt DatabaseMetaData
	 * @throws Exception
	 ***************************************************************************************************/
	private void createClassFile ( String tableName, DatabaseMetaData dbmdt ) throws Exception {
		
		File directoryCorrente = new File ( path );
		
		if ( ! directoryCorrente.exists() ) {
			boolean created = directoryCorrente.mkdir();
			if ( created ) {
	//			System.out.println( "Directory [" + directoryCorrente.getAbsolutePath() + "] creata correttamente" );
			}
		}
		
		
		
		
		
		PrintWriter fo = new PrintWriter ( new BufferedWriter ( new FileWriter ( new File ( directoryCorrente, tableName + extension ) ), 1024 ) );

		fo.println ( "package " +  packageName + "; " );

		fo.println("\t/*" );
		fo.println("\t*\tFILE " + tableName + " created " + currentFormattedDate );
		fo.println("\t*/");
		fo.println();
		
		fo.println ( "public class " + tableName + " {");
		fo.println ();
		fo.println ( "\tpublic final static String TABLE_NAME = \"" + tableName + "\";");
		fo.println ();
		
		ResultSet rs = dbmdt.getColumns( dbName, schema, tableName, null );
		
		//System.out.println("---------- NUOVA TABELLA [" + tableName + "] ---------------");
		while ( rs.next() ) {
			
			String currentLabel	= rs.getString("COLUMN_NAME");
			
		//	System.out.println("---------- CAMPO CORRENTE [" + currentLabel + "] ---------------");
			
			String currentType	= rs.getString("TYPE_NAME").trim();
			String currentSize	= rs.getString("COLUMN_SIZE").trim();
			String isNullable	= rs.getString("IS_NULLABLE").trim();
			
			String oldType = currentType;
			
			if ( "bigint identity".equalsIgnoreCase(currentType.trim()) || "bigint".equalsIgnoreCase(currentType.trim()) ) {
				currentType = "long";
			} else if ( currentType.trim().equalsIgnoreCase("decimal") ) {
				currentType = "java.math.BigDecimal";
			} else if ( "varchar".equalsIgnoreCase(currentType.trim()) ) {
				currentType = "String";
			} else if ( "datetime".equalsIgnoreCase(currentType.trim()) ) {
						currentType = "java.sql.Date";
			}
				
			fo.println ();
			fo.println ("\tpublic final static String " + currentLabel.toUpperCase() + " = \"" + currentLabel.toUpperCase() + "\";" );
			fo.println ("\tpublic final static String T_" + currentLabel.toUpperCase() + " = TABLE_NAME + \".\" + " +  currentLabel.toUpperCase() + ";" );	
			fo.println ("\t\t// COLUMN TYPE [" + oldType + "]" );
			fo.println ("\t\t// COLUMN SIZE [" + currentSize + "]" );
			fo.println ("\t\t// NULLABLE [" + isNullable + "]" );
			
			
			fo.println();
			fo.println("\tpublic " + currentType + " " + getCapitalizedCurrentLabel(currentLabel) + "_field;");
			
		}
		fo.println ( "}" );
		rs.close();
		fo.close();
	}
	
	
	/**************************************************************************************
	 * Riscrive la stringa in ingresso tutta in maiuscolo
	 * @param label stringa in ingresso
	 * @return String
	 **************************************************************************************/
	private String getCapitalizedCurrentLabel(String label) {
		String start = label.substring(0,1);
		
		return start.toUpperCase() + label.substring(1,label.length());
	}
	
	
}