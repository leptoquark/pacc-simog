package it.avlp.simog.tabmanager.xml.parser;


import it.avlp.simog.db.advanced.TableBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class TabelleManagerSAXParser implements TabellaLoaderObserver {
	
	private Logger logger = null;
	private TableBean tableDELETE = new TableBean();
	private TableBean tableINSERT = new TableBean();
	private TableBean tableUPDATE = new TableBean();

	private String nomeTabella = null;
	
	public TabelleManagerSAXParser ( Logger logger ) {
		this.logger = logger;
	}
	
	/******************************************************************************************************
	 * Il metodo effettua il parse della Stringa in ingresso 
	 * gestendolo come un XML attraverso l'opportuno Handler
	 * 
	 * @param contentToParse String di ingresso
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parse ( String contentToParse )  throws ParserConfigurationException, SAXException, IOException {
		
		logger.debug("Richiesta analisi di [" + contentToParse + "]");

		BufferedReader fileSparso = null;
		StringBuffer fileCompatto = null;
		
		try {
			fileSparso = new BufferedReader ( new StringReader ( contentToParse ) );
			fileCompatto = new StringBuffer ();
			String read = null;
			
			while ( ( read = fileSparso.readLine() ) != null ) {
				if ( ! "\n".equalsIgnoreCase(read) || ! "\t".equalsIgnoreCase(read) ) {
					fileCompatto.append ( getCleanString(read) );
				}
			}
			// ottengo in fileCompatto il contenuto da parsare privato dei \t \n \r 
			logger.debug("Compattato input [" + fileCompatto + "]");
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			InputSource in = new InputSource ( new StringReader ( contentToParse ) );
			
			DefaultHandler handler = new TabelleManagerXMLHandler(logger, this);
			
			logger.debug("Analisi in corso...");
			saxParser.parse( in, handler );
			logger.debug("Analisi Completata");
		} catch ( IOException ioe ) {
			throw ioe;
		} finally {
			try {
				fileSparso.close();
			} catch ( Exception e ) {}
			fileSparso = null;
		}
	}
	
	/********************************************************************************************
	 * Genera una copia della stringa in ingresso, read, togliendo tutte le occorrenze di \t
	 *
	 * param read : Stringa in ingresso
	 * return StringBuffer
	 */
	private StringBuffer getCleanString(String read) {
		StringTokenizer st = new StringTokenizer ( read, "\t" );
		
		StringBuffer sb = new StringBuffer();
		
		while ( st.hasMoreElements() ) {
			String elem = st.nextToken();
			logger.debug("Aggiunto token [" + elem + "]");
			sb.append ( elem.trim() );
		}
		return sb;
	}

	public static void main ( String [] args ) throws Exception {
		
		
		/* PROVA GESTIONE RIGHE SPORCHE TABULAZIONE ***************************
		File f = new File ( "/opt/SIMOG/amministrazione", "test_upload_categoria.xml" );
		
		LineNumberReader in = new LineNumberReader ( new FileReader ( f ) );

		String read = null;

		while ( ( read = in.readLine() ) != null ) {
			System.out.println("Letta riga [" + read + "]");
			
			System.out.println("RIGA [" + in.getLineNumber() + "] PULITA[" + getCleanString(read) + "]");
		}
		****************************/
		
		/*
		PropertyConfigurator.configure( "C:/Program Files/Apache Software Foundation/Tomcat 5.0/webapps/SimogWeb/simog_log4j.properties" );
		Logger logger = Logger.getLogger("SIMOG_LOGGER");
		logger.debug("LOGGER applicativo inizializzato correttamente");
		
		TabelleManagerSAXParser sapars = new TabelleManagerSAXParser( logger );
		
		BufferedReader bfr = new BufferedReader ( new FileReader (  "d:/TEMP/SIMOG/login_RSSA_OK.xml"  ) );
		
		StringBuffer fileRead = new StringBuffer();
		String read = null;
		
		while ( ( read = bfr.readLine() ) != null ) {
//			if ( ! "\n".equalsIgnoreCase(read) || ! "\t".equalsIgnoreCase(read) ) {
				fileRead.append ( read.trim() );
//			}
		}
		sapars.parse( fileRead.toString() );
	*/
	}


	/**
	 * @return TableBean the tableDELETE
	 */
	public TableBean getTableDELETE() {
		return tableDELETE;
	}

	/**
	 * @return TableBean the tableINSERT
	 */
	public TableBean getTableINSERT() {
		return tableINSERT;
	}

	/**
	 * @return TableBean the tableUPDATE
	 */
	public TableBean getTableUPDATE() {
		return tableUPDATE;
	}

	public String getTableName() {
		return nomeTabella ;
	}

	public void setTableName(String nomeTabella) {
		this.nomeTabella = nomeTabella;		
	}
	
	
	/***********************************************************************
	 * Il metodo restituisce una delle tabelle private della classe in base 
	 * al valore della Stringa operazioneValue:
	 * <ul>
	 * <li>M : tableUPDATE
	 * <li>I : tableINSERT
	 * <li>C : tableDELETE 
	 * </ul>
	 * @param operazioneValue String identificante la tabela da restituire. 
	 */
	public TableBean getTableByOperazione (String operazioneValue) {
		
		TableBean currentTable = null;
		
		if ( "M".equalsIgnoreCase(operazioneValue) ) {
			currentTable = tableUPDATE;
		} else if ("I".equalsIgnoreCase(operazioneValue) ) {
			currentTable = tableINSERT;
		} else if ("C".equalsIgnoreCase(operazioneValue) ) {
			currentTable = tableDELETE;
		} else {
			throw new RuntimeException ( "Valore OPERAZIONE [" + operazioneValue +"] non previsto" );
		}
		return currentTable;
	}
}