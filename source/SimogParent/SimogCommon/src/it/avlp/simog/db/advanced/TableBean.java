package it.avlp.simog.db.advanced;

import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.VarcharUnicodeEncoder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Vector;

import com.csvreader.CsvWriter;
 
public class TableBean extends Hashtable implements java.io.Serializable {
	
	private String keyName = null;

	private static final long serialVersionUID = -1556420630272168549L;
	
	protected int rowsAdded = 0;
	
	private Collection indexMap = new ArrayList();
	
	private	boolean sorted = false;
	
	/* Indica la dimensione del resultset a prescindere dalla dimensione del
	* TableBean
	*/
	/** X-XX: la fullsize non e' piu attendibile..*/
	private int fullSize = 0;
	/**
	 * parametro che serve a specificare il tipo di iterazione 
	 * sui records
	 */
	private boolean iterAllRecords = true;
	
	/**
	 * TableBean constructor comment.
	 */
	public TableBean() {}

	/**
	 * TableBean constructor comment.
	 */
	public TableBean( java.sql.ResultSet rs ) throws java.sql.SQLException {
		this ( rs, 0, Integer.MAX_VALUE );
				
		/*
		java.sql.ResultSetMetaData rsmdt = rs.getMetaData();
			
		while ( rs.next() ) {

			TableBeanRow newRow = new TableBeanRow ( this, ++rowsAdded );
			
			for ( int colCount = 1; colCount <= rsmdt.getColumnCount(); colCount++ ) {
				Object objectFromDB = rs.getObject( colCount );
				String currentValue =  objectFromDB != null ? objectFromDB.toString() : "null";
				// Decodifica automatica se la stringa è codificata (VARCHAR con Unicode)
				if (currentValue != null && !"null".equals(currentValue)) {
					currentValue = VarcharUnicodeEncoder.decode(currentValue);
				}
				newRow.addFieldValue ( rsmdt.getColumnLabel(colCount), currentValue );
			}
		}

		try {
			rs.close();
		} catch ( Exception e ) {}
		rs = null;
		*/
	}
	
	public TableBean( java.sql.ResultSet rs, boolean closeRS ) throws java.sql.SQLException {
		this ( rs, 0, Integer.MAX_VALUE, closeRS );
	}
	
	/*********************************************************************************************************
	 * Costruttore di TableBean
	 * @param rs result set da ottenere i metadata
	 * @param startRow riga da cui iniziare l'inserimento
	 * @param maxRowsAllowed massimo numero di righe da poter inserire 
	 * @throws java.sql.SQLException
	 */
	public TableBean( java.sql.ResultSet rs, int startRow, int maxRowsAllowed) throws java.sql.SQLException {
		this ( rs, startRow, maxRowsAllowed, true );
	}
	/**
	 * added recently
	 * Costruttore di TableBean si differenzia per il fatto che si sceglie se 
	 * iterare o meno sul recordSetCompleto, quindi tralasciando il fatto
	 * che i record restituiti sono quelli specificati se true il getFullSize
	 * conterr� il numero di record restituibili dalla query.
	 * 
	 * default = true, ovvero itera sul recordset completo
	 * false itera solamente sui record specificati
	 * 
	 * @param iterAllRecords boolean, default = true, se si vuole iterare solamente nel gap specificato settare a false 
	 * @param rs
	 * @param startRow
	 * @param maxRowsAllowed
	 * @throws java.sql.SQLException
	 */
	public TableBean( boolean iterAllRecords, java.sql.ResultSet rs, int startRow, int maxRowsAllowed) throws java.sql.SQLException {		
		this (iterAllRecords, rs, startRow, maxRowsAllowed, true );
	}

	/**
	 * Inserisce gli elementi della ResultSet nella TableBean partendo da una determinata riga indicata
	 * @param rs ResultSet
	 * @param startRow int rappresenta la riga da cui si inizieranno ad inserire gli elementi
	 * @param maxRowsAllowed int, indica il numero massimo di righe presenti nella TableBean
	 * @param closeRS
	 * @throws java.sql.SQLException
	 */
	public TableBean( java.sql.ResultSet rs, int startRow, int maxRowsAllowed , boolean closeRS) throws java.sql.SQLException {

		java.sql.ResultSetMetaData rsmdt = rs.getMetaData();
			
		int count = 0;
		boolean exitFor = false; // uscita dal ciclo se esaurito il caricamento 
		//UN !exit deve stare prima cosi' non esegue la rs.next()
		while ( !exitFor && rs.next() ) {
			count++;
			if(!this.iterAllRecords){
				exitFor = ! (count <= ( startRow + maxRowsAllowed ) );
			}

			if ( count > startRow && count <= ( startRow + maxRowsAllowed ) ) {				
				
				TableBeanRow newRow = new TableBeanRow ( this, ++rowsAdded );	

				for ( int colCount = 1; colCount <= rsmdt.getColumnCount(); colCount++ ) {
					Object objectFromDB = rs.getObject( colCount );
					String currentValue =  objectFromDB != null ? objectFromDB.toString() : "null";
					// Decodifica automatica se la stringa è codificata (VARCHAR con Unicode)
					if (currentValue != null && !"null".equals(currentValue)) {
						currentValue = VarcharUnicodeEncoder.decode(currentValue);
					}
					newRow.addFieldValue ( rsmdt.getColumnLabel(colCount), currentValue );
				}
			}
		}
		
		setFullSize( count ); 
		
		if(closeRS){
		try {
			rs.close();
		} catch ( Exception e ) {}
		rs = null;
		}
	}
		
	/**
	 * added recently
	 * Costruttore di TableBean si differenzia per il fatto che si sceglie se 
	 * iterare o meno sul recordSetCompleto, quindi tralasciando il fatto
	 * che i record restituiti sono quelli specificati se true il getFullSize
	 * conterr� il numero di record restituibili dalla query.
	 * 
	 * default = true, ovvero itera sul recordset completo
	 * false itera solamente sui record specificati
	 * 
	 * @param iterAllRecords boolean, default = true, se si vuole iterare solamente nel gap specificato settare a false 
	 * @param rs
	 * @param startRow
	 * @param maxRowsAllowed
	 * @param closeRS
	 * @throws java.sql.SQLException
	 */
	public TableBean(boolean iterAllRecords, java.sql.ResultSet rs, int startRow, int maxRowsAllowed , boolean closeRS) throws java.sql.SQLException {
		
		this.iterAllRecords = iterAllRecords;
		java.sql.ResultSetMetaData rsmdt = rs.getMetaData();
			
		int count = 0;
		boolean exitFor = false; // uscita dal ciclo se esaurito il caricamento 
		//UN !exit deve stare prima cosi' non esegue la rs.next()
		while ( !exitFor && rs.next() ) {
			count++;
			if(!this.iterAllRecords){
				exitFor = ! (count <= ( startRow + maxRowsAllowed ) );
			}

			if ( count > startRow && count <= ( startRow + maxRowsAllowed ) ) {				
				
				TableBeanRow newRow = new TableBeanRow ( this, ++rowsAdded );	

				for ( int colCount = 1; colCount <= rsmdt.getColumnCount(); colCount++ ) {
					Object objectFromDB = rs.getObject( colCount );
					String currentValue =  objectFromDB != null ? objectFromDB.toString() : "null";
					// Decodifica automatica se la stringa è codificata (VARCHAR con Unicode)
					if (currentValue != null && !"null".equals(currentValue)) {
						currentValue = VarcharUnicodeEncoder.decode(currentValue);
					}
					newRow.addFieldValue ( rsmdt.getColumnLabel(colCount), currentValue );
				}
			}
		}
		/** ATTENZIONE: nel caso di iterazione parziale bisogna sovrascrivere questo valore*/
		setFullSize( count ); 
		
		if(closeRS){
		try {
			rs.close();
		} catch ( Exception e ) {}
		rs = null;
		}
	}	

	/**
	 * TableBean constructor comment.
	 * Restituisce il valore di rowsAdded che indica il numero di riche aggiunte
	 */
	public int getTableSize () {
		return rowsAdded;
	}

//	public static void main ( String [] args ) throws Exception {
//		//Class.forName("oracle.jdbc.driver.OracleDriver");
//		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//		Connection conn = DriverManager.getConnection("jdbc:odbc:simoglocal", "sa", "step");
//	
//		TableBean tb = new TableBean ( conn.prepareStatement("SELECT top 10 * FROM LOTTO").executeQuery() );
//
//		conn.close();
//		tb.printHTMLTable( new PrintWriter ( new OutputStreamWriter ( System.out ) ) );
//		
//		tb.addColumn("CIAO", "miao");
//		tb.printHTMLTable( new PrintWriter ( new OutputStreamWriter ( System.out ) ) );
//	}
	
	
	/*******************************************************************************************************
	 * Inserisce una nuova riga nella tabella
	 * @param newRow TableBeanRow
	 * @return int - restituisce il numero di righe della tabella
	 */
	public synchronized int addRow ( TableBeanRow newRow ) {
		newRow.setCurrentTable(this);
		newRow.setRowNumber( ++rowsAdded );
		return rowsAdded;
	}
	

	/**
	 * Metodo per "Scegliere" il modo di iterazione sui recordset
	 * Default = true, l'iterazione avverr� su tutto il recordset
	 * quindi getFullSize restituir� la dimensione corretta
	 * 
	 * 
	 * @param iterAllRecords
	 */
	public void setIterationMode(boolean iterAllRecords){
		this.iterAllRecords = iterAllRecords;
	}

	/*******************************************************************************************************
	 * Trasforma la tabella in una tabella HTML senza Null
	 * @param printerOut java.io.PrintWriter
	 *******************************************************************************************************/
	public void printHTMLTable ( java.io.PrintWriter printerOut ) {
		printBaseHTMLTable(printerOut, false, "", null);
	}

	/**********************************************************************************************
	 * Stampa una tabella HTML di classe specificata ed inserisce link verso target
 	 * permette l'ordinamento 
	 * @param printerOut java.io.PrintWriter
	 * @param target stringa per il target
	 **********************************************************************************************/

	public void printNulledHTMLTable ( java.io.PrintWriter printerOut, String target ) {
		printBaseHTMLTable(printerOut, true, "", target);
	}

	/*******************************************************************************************************
	 * Trasforma la tabella in una tabella HTML con NULL visibili
	 * @param printerOut java.io.PrintWriter
	 *******************************************************************************************************/
	public void printMyHTMLTable ( java.io.PrintWriter printerOut ) {
		printBaseHTMLTable(printerOut, false, "(NULL)", null);
	}
	
	/*******************************************************************************************************
	 * Trasforma la tabella in una tabella HTML
	 * @param printerOut java.io.PrintWriter
	 *******************************************************************************************************/
	private void printBaseHTMLTable ( java.io.PrintWriter printerOut, boolean nulled, String nullValue, String target) {
	
		printerOut.println ( "<table class=\"TableBean\">" );
		printerOut.println ( "<tr>" );	
		
		if(target != null) {
			if ( target.indexOf("?") < 0 ) {
				target += "?";
			} else {
				target += "&";
			}
		}
		
		for ( Enumeration e = keys(); e.hasMoreElements(); ) {
			String currentColumnName = (String) e.nextElement();
			if(target == null)
				printerOut.println ( "<td class=\"TableBeanTitle\">" + currentColumnName + "</td>" );
			else{
				String link  = "<a href=\"" + target + ParametriServlet.ORDER_FIELD + "=" + currentColumnName + "\">" + currentColumnName + "</a>";
				printerOut.println ( "<td class=\"TableBeanTitle\">" +  link + "</td>" );
			}
		}
		
		printerOut.println ( "</tr>" );
		
		for ( int rowNumber = 0; rowNumber < rowsAdded; rowNumber++ ) {

			String oddEven = ( rowNumber % 2 == 0 ) ? "TableBeanEven" : "TableBeanOdd" ; 
			printerOut.println ( "<tr class=\"" + oddEven + "\">" );
			
			for ( Enumeration en = keys(); en.hasMoreElements(); ) {
				String currentColumnName = (String)en.nextElement();
				printerOut.println ( "<td nowrap>" );
				String currentValue = nulled ? getNulledField( currentColumnName, rowNumber) : getField( currentColumnName, rowNumber);
				if ( currentColumnName.toUpperCase().startsWith( "DATA" ) ) {
					if (currentValue.length()>8)
						currentValue = PageHelper.getFormattedDateTime( currentValue );
					else if(currentValue.length() == 8)
						currentValue = PageHelper.getFormattedDate( currentValue );
				}
				currentValue = PageHelper.formattaTesto(currentValue); // sostituzione eventuale di <> con &lt; &gt;
				if(!nulled){
					if (currentValue == null || "null".equalsIgnoreCase(currentValue) ) { 
						currentValue = nullValue; 
					}
				}
				printerOut.println( currentValue );
				printerOut.println ( "</td>" );
			}
		printerOut.println ( "</tr>" );			
		}
		printerOut.println ( "</table>" );	
	}
	
   /*******************************************************************************************************
    * Trasforma la tabella in una tabella XML
    * @param printerOut java.io.PrintWriter
    *******************************************************************************************************/
   public void printXMLTable ( java.io.PrintWriter printerOut, boolean nulled, String nullValue,  String name) {
   
      printerOut.println ( "<"+name.toUpperCase()+">" );
            
      for ( int rowNumber = 0; rowNumber < rowsAdded; rowNumber++ ) {
         printerOut.println ( "<row>" );
         
         for ( Enumeration en = keys(); en.hasMoreElements(); ) {
            String currentColumnName = (String)en.nextElement();
            String currentValue = nulled ? getNulledField( currentColumnName, rowNumber) : getField( currentColumnName, rowNumber);
            if ( currentColumnName.toUpperCase().startsWith( "DATA" ) ) {
               if (currentValue.length()>8)
                  currentValue = PageHelper.getFormattedXMLDateTime( currentValue );
               else if(currentValue.length() == 8)
                  currentValue = PageHelper.getFormattedDate( currentValue );
            }
            currentValue = PageHelper.formattaTesto(currentValue); // sostituzione eventuale di <> con &lt; &gt;
            currentValue = PageHelper.encodeUTF8ToASCII(currentValue);
            
            if(!nulled){
               if (currentValue == null || "null".equalsIgnoreCase(currentValue) ) { 
                  currentValue = nullValue; 
               }
            }
            printerOut.println ( "<" + currentColumnName + ">" + currentValue + "</"+currentColumnName+">");
         }
         printerOut.println ( "</row>" );
      }
      printerOut.println ( "</"+name.toUpperCase()+">" );   
   }
   
	/************************************************************************************************
	 * Cerca valori non validi in un campo, restituisce un'opportuna stringa che comunica l'esito 
	 * della ricerca
	 * 
	 * @param searchField Stringa per il campo di ricerca
	 * @param searchVal Stringa per il valore da ricercare 
	 * @param field Stringa per il nome del capo
	 * @return String
	 ************************************************************************************************/
	public String getFieldBySearchField ( String searchField, String searchVal, String field ) {

		Vector currentColumn = getColumn(searchField);

		for ( int rowNumber = 0; rowNumber < currentColumn.size(); rowNumber++ ) {
			//MAC 35585 3.04.9
			//controllo che la var searchVal non arrivi mai null, e aggiunta del trim prima dell'equals
			if ( searchVal != null && searchVal.trim().equalsIgnoreCase((String)currentColumn.get(rowNumber) ) ) {
				return getField(field, rowNumber);
			}
		}
		return "VALORE [" + searchVal + "] non valido per il campo [" + searchField + "]";
	}
	

	/******************************************************************************************************
	 * stampa il CSV della tabella passando un PrinterWriter ed un carattere di delimitazione
	 * @param pw PrinterWriter
	 * @param delim char, carattere di delimitazione
	 * @throws IOException
	 * @throws FinalizedException
	 ******************************************************************************************************/
	private void _printCSVHelper(PrintWriter pw, char delim, boolean ordered) throws IOException{
		
		CsvWriter writer = new CsvWriter(pw,delim);
		writer.setUseTextQualifier(true);
		writer.setForceQualifier(true);

		//		 Sort hashtable.
	    Vector v = new Vector(this.keySet());
	    Collections.sort(v);
	    
		for ( Enumeration e = v.elements(); e.hasMoreElements(); ) {
			String currentColumnName = (String) e.nextElement();
			if(ordered) 
				currentColumnName = currentColumnName.substring(3);
			writer.write  ( currentColumnName, true );
		}		
		writer.endRecord();

		for ( int rowNumber = 0; rowNumber < rowsAdded; rowNumber++ ) {
			for ( Enumeration en = v.elements(); en.hasMoreElements(); ) {
				String currentColumnName = (String) en.nextElement();
				String currentValue = getNulledField(currentColumnName, rowNumber );
				if(ordered) 
					currentColumnName = currentColumnName.substring(3);
				if ( currentColumnName.toUpperCase().startsWith("DATA") && currentValue.trim().length() == 8 ) {
					currentValue = PageHelper.getFormattedDate(currentValue);
				}
				writer.write ( currentValue, true );
			}
			writer.endRecord();
		}
	}
	
	/**************************************************************************************
	 * Scrive un CSV passandogli un PrinterWriter ed un delimitatore
	 * @param pw PrinterWriter
	 * @param delim char per il delimitatore
	 * @throws IOException
	 **************************************************************************************/
	public void writeCSV(PrintWriter pw, char delim) throws IOException{
		try {
			_printCSVHelper(pw, delim, false);
		} catch(Exception exc) {
			throw new IOException( exc.getMessage() );
		}
	}

	/**************************************************************************************
	 * Scrive un CSV passandogli un PrinterWriter ed un delimitatore
	 * @param pw PrinterWriter
	 * @param delim char per il delimitatore
	 * @throws IOException
	 **************************************************************************************/
	public void writeCSVOrdered(PrintWriter pw, char delim) throws IOException{
		try {
			_printCSVHelper(pw, delim, true);
		} catch(Exception exc) {
			throw new IOException( exc.getMessage() );
		}
	}

	/******************************************************************************************
	 * Restituisce un campo della HashTable 
	 * @param nomeCampo Stringa perm il nome del campo
	 * @return Vector 
	 ******************************************************************************************/
	public Vector getColumn(String nomeCampo ) {
		
		nomeCampo = nomeCampo.trim().toUpperCase();
		
		//System.out.println("Inizio campi----------------------------");
		//System.out.println("Campi presenti nella TABLEBEAN");
		/*
		for ( Enumeration e = keys(); e.hasMoreElements(); ) {
			System.out.println( e.nextElement() );
		}
		*/
		//System.out.println("fine campi----------------------------");
		
		//System.out.println("Getting column [" + nomeCampo + "]");
		
		if  ( ! containsKey( nomeCampo ) ) {
			////System.out.println("Adding column [" + nomeCampo + "]" );
			put ( nomeCampo, new Vector() );
		}
		//System.out.println("VETTORE [" + nomeCampo + "] [[" + get ( nomeCampo ) + "]]");
		return (Vector) get ( nomeCampo );
	}

	/********************************************************************************************************
	 * Restituisce un elemento di una colonna in base all'indice della riga e al nome della colonna
	 * @param columnName Stringa per il nome della colonna
	 * @param rowCount indice della colonna da cui prelevare il campo
	 * @return String
	 ********************************************************************************************************/
	public String getField(String columnName, int rowCount) {
		try {
			String currentValue = (String)getColumn(columnName).elementAt(rowCount);
			//System.out.println("The value is[" + currentValue + "] for Field [" + columnName + "] at row [" + rowCount + "]");
			return currentValue;
		} catch ( Exception e ) {
			throw new RuntimeException("Impossibile ottenere il campo [" + columnName + "] per la riga", e);
		}
	}
	
	/*************************************************************************************************
	 * restituisce una riga della tabella in base all'indice specificato
	 * se la Tablebean è ordinata fa riferimento alla "indexMap" per recuperare la riga
	 * @param rowNumber int indice in base al quale prelevare la riga della tabella
	 * @return TableBeanRow
	 *************************************************************************************************/
	//se la tablebean è stata ordinata viene restituita la riga tenendo conto della mappa delle corrispondenze "indexMap"
	public TableBeanRow getRow ( int rowNumber ) {
		//System.out.println ( "Getting row [" + rowNumber + "]" );
		if(!sorted)
			return new TableBeanRow ( this, rowNumber );
		else{
			Object array[] = indexMap.toArray();
			int row = (Integer)array[rowNumber];
			return new TableBeanRow ( this, row );
		}
	}

	
	/**************************************************************************************************
	 * String <b>getNulledField</b> ( String, int )<br><br >
	 * Restituisce il valore di un campo in base al nome e all'indice della riga, se il campo e' null visualizza 
	 * il campo vuoto
	 * @param field Stringa per il nomed el camppo
	 * @param rowNumber int per l'indice della riga 
	 * @return String contenente il contenuto del campo
	 **************************************************************************************************/
	public String getNulledField(String field, int rowNumber) {
		//System.out.println("Trying to get field [" + field + "] at row [" + rowNumber + "]" );
		String value = getField(field, rowNumber);
		if ( "null".equalsIgnoreCase(value) ) {
			value = "";
		}
		return value;
	}
	
	/** Sets the specified field of the specifed row of the tablebean
	 *  with the specified value. No changes are apported if the
	 *  specified field does not exist in the tablebean.
	 * @param field the key
	 * @param value
	 * @return Object
	 */
	public Object setField(String field,String value){
		if(containsKey(field)){
			Vector v = new Vector();
			v.add(value);
			return put(field, v);
			//getColumn(field).setElementAt(value.toString(), 0);
		}
		return null;
	}
		
	/*************************************************************************************************
	 * Stampa una tabella con la lista dei pagamenti
	 * @param printerOut java.io.PrintWriter
	 *************************************************************************************************/
	public void printPagamentiList ( java.io.PrintWriter printerOut ) {
		
		/*
		 * 	for ( Enumeration e = keys(); e.hasMoreElements(); ) {
		 *	String currentColumnName = (String) e.nextElement();
		 *	printerOut.println ( "<td class=\"TableBeanTitle\">" + currentColumnName + "</td>" );
		 * }
		 */
			
		printerOut.println ( "<div class=\"elenco\">" );
		
		for ( int rowNumber = 0; rowNumber < rowsAdded; rowNumber++ ) {
			
			int colorSelector = 0;
			
			printerOut.println ( "<div class=\"gara\">" );
			printerOut.println("<table>");
			
			for ( Enumeration en = keys(); en.hasMoreElements(); ) {
				
				colorSelector++;
				String rowStyle = ( colorSelector % 2 == 0 )  ? "TableBeanEven" : "TableBeanOdd";				
				printerOut.println("<tr class=\"" + rowStyle + "\">");
				printerOut.println("<th width=\"50%\">");
				String currentColumnName = (String)en.nextElement();
				printerOut.println(currentColumnName);
				printerOut.println("</th>");
				printerOut.println("<td width=\"50%\">");
				String currentValue = getField( currentColumnName, rowNumber );
				if ( currentValue == null || "null".equalsIgnoreCase( currentValue ) ) {
					currentValue = "-";
				} else if ( currentColumnName.toUpperCase().startsWith("DATA") && currentValue.trim().length() == 8 ) {
					currentValue = PageHelper.getFormattedDate(currentValue);
				}

				
				printerOut.println( currentValue );
				printerOut.println("</td>");
				printerOut.println("</tr>");
			}
			printerOut.println ( "</table>" );			
			printerOut.println ( "</div>" );
		}
		printerOut.println ( "</div>" );
	}

	/**
	 * @return int - the fullSize
	 */
	
	public int getFullSize() {
		return fullSize;
	}

	/**
	 * @param fullSize - int the fullSize to set
	 */
	public void setFullSize(int fullSize) {
		this.fullSize = fullSize;
	}

	/**
	 * @return String - the keyName
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName String the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	/**********************************************************************************************
	 * Restituisce il numero di righe della tabella
	 * @return int 
	 **********************************************************************************************/
	public int getRowsCount() {
		
		int count = 0;
		
		String firstKey = null;
		
		for ( Enumeration e = keys(); e.hasMoreElements(); ) {
			firstKey = (String) e.nextElement();
			break;
		}
		if ( firstKey != null ) {
			count = ( (Vector)get (firstKey) ).size();
		}
		return count;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Hashtable#toString()
	 */
	public String toString(){
		String result = "";
		for ( Enumeration e = keys(); e.hasMoreElements(); ) {
			String currentColumnName = (String) e.nextElement();
			result = result.concat(currentColumnName + "\t\t" );
		}
		result = result.concat("\n");
		for ( int rowNumber = 0; rowNumber < rowsAdded; rowNumber++ ) {
			for ( Enumeration en = keys(); en.hasMoreElements(); ) {
				String currentColumnName = (String) en.nextElement();
				String currentValue = getField( currentColumnName, rowNumber );
				if ( currentColumnName.toUpperCase().startsWith("DATA") && currentValue.trim().length() == 8 ) {
					currentValue = PageHelper.getFormattedDate(currentValue);
				}
				result = result.concat(currentValue + "\t" );
			}
			result = result.concat("\n");
		}
		return result;
		
	}
	
	
	/***********************************************************************************************
	 * <b>addEmptyRow</b><br>
	 * aggiunge una riga vuota
	 * @param metaData ResultSetMetaData
	 * @throws SQLException
	 */
	public synchronized void addEmptyRow(ResultSetMetaData metaData)throws SQLException{
		this.addEmptyRow(metaData, "null");
	}
	
	
	/***********************************************************************************************
	 * <b>addEmptyRow</b><br>
	 * Inserisce una riga vuota con dei valori di default
	 * @param metaData ResultSetMetaData
	 * @param defaultValue String
	 * @throws SQLException
	 */
	public synchronized void addEmptyRow(ResultSetMetaData metaData, String defaultValue)throws SQLException{
		TableBeanRow tbRow = new TableBeanRow(this, ++rowsAdded);
		
		for(int i = 1; i <= metaData.getColumnCount(); i++){
			
			tbRow.addFieldValue(metaData.getColumnName(i), defaultValue);
		}
		setFullSize(1);
		
	}
	
	/***********************************************************************************************
	 * Ordina in modo ascedente la Tablebean in base ai campi ID_GARA e ID_LOTTO
	 */	
	//L'ordinamento non avviene realmente sulle righe della tablebean.
	//Si utilizza una mappa per tenere traccia delle corrispondenze tra in numeri di riga
	//delle righe ordinate e quelle originali
	public synchronized void sortByGaraAndLotto(){	
		TreeMap tm = new TreeMap();
		for(int row = 0; row < this.getRowsCount(); row++){
			String value1 = this.getNulledField(LOTTO.ID_GARA, row);
			String value2 = this.getNulledField(LOTTO.ID_LOTTO, row);
			//caso della riga "sentinella", spostarla in fondo
			if("-1".equals(value1) && "-1".equals(value2))
				tm.put(">tutti", row);
			else{
				value1 = addZero(value1, 20);
				value2 = addZero(value2, 20);
				tm.put(value1+"-"+value2, row);
			}
		}
		this.indexMap = tm.values();
		this.sorted = true;
	}
	
	/***********************************************************************************************
	 * Riporta lo stato del tableBean all'ordine originario, 
	 */		
	public synchronized void unsort(){
		this.indexMap.clear();
		this.sorted = false;
	}
	
	/***********************************************************************************************
	 * Antepone tanti zeri alla stringa sino a che non si raggiunge lunghezza maxlength
	 * @param value String
	 * @param maxlength int
	 */	
	private String addZero(String value, int maxlength){
		//String zeri = "";
		//for(int resto = maxlength - value.length(); resto > 0; resto--) zeri += "0";
		//return zeri + value;
		return (maxlength - value.length() <= 0) ? value : "0" + addZero(value, maxlength-1);
	}

	/**
	 * aggiunge una colonna al tablebean, se la colonna esiste non fa nulla
	 * @param colName nome della colonna
	 * @param defValue valore di defult da attribuire
	 */
	public void addColumn(String colName, String defValue){
		
		// provo a vedere se esiste
		Vector newCol = this.getColumn(colName);
		
		// esiste, esco
		if(newCol.size() > 0) return;
		
		// se non esisteva è statao creato un vettore vuoto, lo riempio con il valore di default,
		// per tutte le righe presenti nel tablebean
		for(int i = 0 ; i< this.getFullSize();i++){
			newCol.add(i, defValue);
		}
	}

	   /**
     * aggiunge una colonna al tablebean, se la colonna esiste non fa nulla
     * @param colName nome della colonna
     */
    public void addColumn(String colName){
        
        // provo a vedere se esiste
        Vector newCol = this.getColumn(colName);
        
        // esiste, esco
        if(newCol.size() > 0) return;
    }
	public int getRowsAdded() {
		return rowsAdded;
	}

	public void setRowsAdded(int rowsAdded) {
		this.rowsAdded = rowsAdded;
	}

}
