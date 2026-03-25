package it.avlp.simog.util;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;

import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;


public class PageHelper {
	
	public final static String DEFAULT_VIEW_DATE = "dd/MM/yyyy";
	/**
	 * 2030
	 */
	public final static int SELECTED_MAX_ANNO = 2030;
	/**
	 * 2006
	 */
	public final static int SELECTED_MIN_ANNO = 2006;
	/**
	 * "yyyyMMdd"
	 */
	public final static String DEFAULT_DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * "yyyyMMdd HH:mm:ss"
	 */
	public final static String DEFAULT_DATE_TIME_FORMAT = "yyyyMMdd HH:mm:ss";
    public final static String DEFAULT_DATETIME_COMPR = "yyyyMMdd_HHmmss";
	/**
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public final static String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; //"2002-06-13 00:00:00.0"
   public final static String XML_DATE_TIME_FORMAT = "$1T00:00:00";  // $1 data in formato yyyy-MM-dd
	
	public final static String IMPORTO_ND = "N.D.";
	/**
	 * Data una stringa 20060810 ( = 10 agosto 2006) restituisce 10/08/2006
	 * 
	 * @param date String
	 * @return String
	 */
	public static String getFormattedDate( String date ) {
		if( date == null ) return "NULL";
		if(date.contains("/"))
			return date;
		if ( date.trim().length() > 0 ) {
			return getDay(date) + "/" + getMonth(date) + "/" + getYear(date);
		} else {
			return "";
		}
	}
	
	public static Integer getInteger(Object o){
		return o == null ? null : (Integer)o;
	}
	
	
	/**Ritorna la data corrente in oggetto Date
	 * @return {@link Date}
	 */
	public static Date getCurrentUtilDate(){
		return getIncreasedDate(
				getCurrentDate(), 0).getTime();
	}
	/**
	 * Visualizza la data in ingresso formato in YYYYMMDD espressa come Stringa 10/08/2006
	 * 
	 * @param date String per la data
	 * @return String
	 */
	public static String getViewDate(String date){
		if(date == null || date.trim().length() != 8)
			return null;
		else return getDay(date) + "/" + getMonth(date) + "/" + getYear(date);
	}
	
	/**
	 * Trasforma l'oggetto Date in ingresso in una stringa dd/MM/yyyy
	 * 
	 * @param {@link Date}
	 * @return String data in formato dd/MM/yyyy oppure null
	 */
	public static String getViewDate(Date date){
		if(date == null)
			return null;
		else
			return (new SimpleDateFormat(DEFAULT_VIEW_DATE)).format(date);
		
	}
	
	/** 
	 * Data una stringa rappresentante la data in formato "2007-10-14 15:12:32.0"
	 * ritorna la data in formato "14/10/2007 15.12.32"
	 *  
	 * @param datetime
	 * @return String
	 */
	public static String getFormattedDateTime( String datetime ) {
		if( datetime == null ) return "NULL";
		if(datetime.contains("/"))
			return datetime;
		if ( datetime.trim().length() > 0 ) {
			return datetime.substring(8,10) + "/" + datetime.substring(5,7) + "/" + getYear(datetime)+" "
				+getTime(datetime);
		} else {
			return "";
		}
	}
	
	
	/**
	 * Formatta la data in ingresso togliendo spazi voti, ":" e "-".
	 * 
	 * @param dateTime String
	 * @return String
	 */
	public static String getFormattedLogDateTime(String dateTime){
		
		if(dateTime.contains("-")){
			dateTime = getFormattedDBDateTime(dateTime);
			return  dateTime.replace(":", "").replace(" ", "");
			
		}
			
		else{
			return (dateTime.substring(6, 10) + dateTime.substring(3, 5) + 
			dateTime.substring(0, 2) + (dateTime.substring(11, dateTime.length())).replace(":", "")).substring(0, 14); 
			
		}
	}
	
	/** 
	 * Data una stringa rappresentante la data in formato "2007-10-14 15:12:32.0"
	 *  ritorna la stringa rappresentante l'ora nel formato "15:12:32".
	 *  
	 * @param datetime String
	 * @return String
	 */
	public static String getTime(String datetime){
		if( datetime == null ) return "NULL";
		if ( datetime.trim().length() > 0 ) {
			StringTokenizer st = new StringTokenizer(datetime);
			st.nextToken();
			String time = st.nextToken();
			return time.substring(0,2)+":"+time.substring(3,5)+"" +":"+time.substring(6,8);			
		} else {
			return "";
		}
	}
	
	/** Data una stringa rappresentante la data in formato "2007-10-14 15:12:32.0"
	 *  ritorna la stringa rappresentante la data nel formato "14/10/2007"
	 *  
	 * @param datetime String
	 * @return String
	 */
	public static String getFormattedDateFromDateTime( String datetime ) {
		if( datetime == null ) return null;
		if(datetime.contains("/"))
			return datetime;
		if ( datetime.trim().length() > 0 ) {
			StringTokenizer st = new StringTokenizer(datetime);
			String date = st.nextToken();
			return date.substring(8,10) + "/" + date.substring(5,7) + "/" + getYear(date);
		} else {
			return "";
		}
	}
	
	
	  /** Data una stringa rappresentante la data in formato "YYYYMMDD" o in formato DB
    *  ritorna la stringa rappresentante la data nel formato "XML
    *  
    * @param datetime String
    * @return String
    */
   public static String getFormattedXMLDateTime( String data ) {
      if( data == null || data.trim().length()==0) return null;

      if(data.length() == 8)
         return XML_DATE_TIME_FORMAT.replace("$1", getYear(data) + "-" + getMonth(data) + "-" + getDay(data));
      else{
         return data.replace("/", "-").replace(" ", "T");
      }
   }
   
	/** Data la data nel formato yyyyMMdd ritorna il giorno dd.
	 * 
	 * @param date String
	 * @return String
	 */
	public static String getDay( String date ) {
		try {
			return date.substring(6,8);
		} catch ( Exception e ) {
			return "";
		}
	}
	
	/**
	 * @return String
	 */
	public static String getNextDay () {
		return fillLeftDateItem("" + getIncreasedDate(1).get( GregorianCalendar.DAY_OF_MONTH ), 2, "0" );
	}
	
	
	/**
	 * @return String
	 */
	public static String getNextDayMonth () {
		return fillLeftDateItem("" + ( getIncreasedDate(1).get( GregorianCalendar.MONTH ) + 1 ), 2, "0") ;
	}
	
	/**
	 * @return String
	 */
	public static String getNextDayYear () {
		return "" + getIncreasedDate(1).get( GregorianCalendar.YEAR );
	}

	
	/**
	 * @param amount int
	 * @return Calendar
	 */
	public static Calendar getIncreasedDate(int amount) {		
		GregorianCalendar calend = new GregorianCalendar();
		calend.add(GregorianCalendar.DAY_OF_MONTH, amount);

		return calend;
	}
	
	/**
	 * @param data in formato YYYYMMDD String
	 * @param amount int
	 * @return Calendar
	 */
	public static Calendar getIncreasedDate(String date, int amount) {
		Calendar calend = getCalendarFromStringDate(date) ;
		calend.add(GregorianCalendar.DAY_OF_MONTH, amount);
		
		return calend;
	}
	
	/** ritorna il giorno successivo a quello odierno
	 * @return String
	 */
	public static String getFormattedIncreasedDate() {
		return formatDate( getIncreasedDate(1).getTime() );
	}	
	
	/** Data la data nel formato yyyyMMdd ritorna il mese MM.
	 * 
	 * @param date String
	 * @return String
	 */
	public static String getMonth( String date ) {
		try {
			return date.substring(4,6);
		} catch ( Exception e ) {
			return "";
		}
	}
	
	/** Data la data nel formato yyyyMMdd ritorna l'anno yyyy.
	 * 
	 * @param date String
	 * @return String
	 */
	public static String getYear ( String date ) {
		try {
			return date.substring(0, 4);
		} catch ( Exception e ) {
			return "";
		}
	}
	
	
	/**
	 * @param dateItem String
	 * @param size int
	 * @param filler String
	 * @return String
	 */
	public static String fillLeftDateItem ( String dateItem, int size, String filler ) {
		for ( int i = dateItem.length(); i < size; i++ ) {
			dateItem = filler + dateItem;
		}
		return dateItem;
	}
	
	
	/**
	 * @param giorno String
	 * @param mese String
	 * @param anno String
	 * @return String
	 */
	public static String getyyyymmddDate( String giorno, String mese, String anno ) {
		return fillLeftDateItem( anno, 4, "0")
		+ fillLeftDateItem( mese, 2, "0" )
		+ fillLeftDateItem( giorno, 2, "0");
	}

	
	/**
	 * @param selectedValue String
	 * @param start int
	 * @param max int
	 * @param size int
	 * @param out PrintWriter
	 */
	public static void printOption( String selectedValue, int start, int max, int size, PrintWriter out ) {
		
		for ( int i = start; i <= max; i++ ) {
			String selected = "";
			String value = fillLeftDateItem( Integer.toString(i), size, "0");
			if ( value.equalsIgnoreCase(selectedValue) ) {
				selected = " selected";
			}
			out.print("<option value=\"" + value + "\"" + selected + ">" +  value + "</option>" );
		}
		out.flush();
	}
	
	/**
	 * @param start int
	 * @param max int
	 * @param size int
	 * @param out PrintWriter
	 */
	public static void printOption( int start, int max, int size, PrintWriter out ) {
		
		out.print("<option value=\"--\" selected >--</option>" );
		for ( int i = start; i <= max; i++ ) {
			String value = fillLeftDateItem( Integer.toString(i), size, "0");
			out.print("<option value=\"" + value + "\">" +  value + "</option>" );
		}
		out.flush();
	}
	
	/**
	 * @param out Writer
	 * @param selected String
	 */
	public static void printAnni ( Writer out, String selected ) {
		printOption( selected, SELECTED_MIN_ANNO, SELECTED_MAX_ANNO, 4, getPrintWriter(out) );
	} 
	
	/**
	 * @param out Writer
	 */
	public static void printAnni ( Writer out ) {
		printOption( SELECTED_MIN_ANNO, SELECTED_MAX_ANNO, 4, getPrintWriter(out) );
	}
	
	/** Ritorna la data corrente nel formato yyyyMMdd
	 * 
	 * @return String
	 */
	public static String getCurrentDate () {
		return formatDate(new Date());
	}
	
	//	
	/** formatta la data corrente (oggetto Date) nel formato yyyyMMdd ritornando
	 *  una stringa.
	 *  
	 * @param currentDate Date
	 * @return String la data nel formato specificato 
	 * @see #DEFAULT_DATE_FORMAT
	 */
	public static String formatDate ( Date currentDate ) {
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(currentDate);
	}
		
	/**
	 * @param out Writer
	 * @see #SELECTED_MIN_ANNO
	 */
	public static void printAnniStart( Writer out ) {
		printAnni(out, Integer.toString(SELECTED_MIN_ANNO));
	}

	/**
	 * @param out Writer
	 * @see #SELECTED_MAX_ANNO
	 */
	public static void printAnniEnd( Writer out ) {
		printAnni(out, Integer.toString(SELECTED_MAX_ANNO));
	}
	
	/**
	 * @param out Writer
	 * @param selected String
	 */
	public static void printGiorni ( Writer out, String selected ) {
		printOption( selected, 1, 31, 2, getPrintWriter (out) );
	}
	
	/**
	 * @param out Writer
	 */
	public static void printGiorni ( Writer out ) {
		printOption( 1, 31, 2, getPrintWriter (out) );
	}
	
	/**
	 * @param out Writer
	 */
	public static void printMesi( Writer out ) {
		printOption( 1, 12, 2, getPrintWriter( out ) );
	}
	
	/**
	 * @param out Writer
	 * @param selected String
	 */
	public static void printMesi( Writer out, String selected ) {
		printOption( selected, 1, 12, 2, getPrintWriter( out ) );
	}
	
	/**
	 * @param out Writer
	 * @return PrintWriter
	 */
	private static PrintWriter getPrintWriter( Writer out ) {
		return new PrintWriter ( out );
	}

	/*
	<% String currentCIG =  currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK); %>
	<% String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA); %>
	<% currentCIG = PageHelper.getCIG ( currentCIG,  sommaUrgenza ); %>
	*/
	
	/**
	 * restutuisce il cig per il frontend
	 * @param currentCIG String
	 * @param sommaUrgenza String
	 * @return String
	 */
	public static String getCIG( String currentCIG, String sommaUrgenza, String dataCreazione ) {
		
		currentCIG = CIGBean.getFeCig(sommaUrgenza, currentCIG, dataCreazione);
		
		return currentCIG;
	}
	
	
	/*
	 * Imposta il formato corretto in migliaia degli importi
	 * 
	 * Da modificare con FORMATTER, ma solo se si definisce compliance 1.5
	 *    %[argument_index$][flags][width][.precision]conversion
	 */
	/**
	 * @param rawImporto String
	 * @return String
	 */
	public static String getFormattedImporto ( String rawImporto ) {
		return _getFormattedImporto(rawImporto, true);		
	}

	public static String getFormattedImportoNoEuro ( String rawImporto ) {
		return _getFormattedImporto(rawImporto, false);		
	}
	
	private static String _getFormattedImporto ( String rawImporto, boolean euro ) {
		
		if ( rawImporto.equals(Costanti.IMPORTO_FUORI_SCALA_STRING) ||
		     rawImporto.equals(Costanti.IMPORTO_FUORI_SCALA_STRING_3D) ||
		     "".equals(rawImporto)) {
			return IMPORTO_ND;
		}
		
		int posizionePunto = rawImporto.indexOf(ParametriServlet.DEC_SEPARATOR);
		String importoCentesimi = "00";
		String importoEuro = "0";
		
		//System.out.println("Posizione del punto [" + posizionePunto + "]");

		if ( posizionePunto > -1 ) {
			// elimina le decimali
			//System.out.println("Provo ad eliminare il punto");
			importoEuro = rawImporto.substring(0, posizionePunto);
			importoCentesimi = rawImporto.substring(posizionePunto + 1);
			//System.out.println("Importo raw [" + rawImporto + "] privo del punto decimale inglese");
		}
		StringBuffer importoFormattato = new StringBuffer();

		int counter = 0;
		//System.out.println("Importo rawImporto.length()[" + rawImporto.length() + "]");

		for ( int i = importoEuro.length() - 1; i >= 0; i-- ) {
			//System.out.println("Valore counter [" + counter + "]");
			if ( importoEuro.length() > 3 && counter % 3 == 0 && counter != 0) {
				//System.out.println("Importo counter [" + counter + "]");
				importoFormattato.insert(0, ".");
				counter = 0;
			}
			importoFormattato.insert(0, rawImporto.charAt(i) );
			counter++;
		}
		if (euro) importoFormattato.insert ( 0, "&euro; " );
		//System.out.println("Centesimi [" + importoCentesimi + "]");
		importoFormattato.append("," + importoCentesimi );
		
		if ( importoFormattato.toString().equals ( Costanti.IMPORTO_FUORI_SCALA_STRING ) ) {
			importoFormattato = new StringBuffer (Messaggi.IMPORTO_NON_INSERITO);
		}
		return importoFormattato.toString();
	}
	
	
	/** Ritorna la data e l'ora nel formato yyyyMMdd HH:mm:ss
	 * 
	 * @return una stringa rappresentante la data e l'ora corrente.
	 */
	@Deprecated
	public static String getCurrentDateTime(){
		return formatDateTime(new Date());		
	}
	
	/**
	 * @param currentDate Date
	 * @see #DEFAULT_DATE_TIME_FORMAT
	 * @return String
	 */
	public static String formatDateTime ( Date currentDate ) {
		return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(currentDate);
	}

	   /**
     * @param currentDate Date
     * @see #DEFAULT_DATE_TIME_FORMAT
     * @return String
     */
    public static String formatDateTimeCompr ( Date currentDate ) {
        return new SimpleDateFormat(DEFAULT_DATETIME_COMPR).format(currentDate);
    }

	/**
	 * @param currentDate
	 * @see #DB_DATE_TIME_FORMAT
	 * @return String
	 */
	public static String formatTimeStamp ( Timestamp currentDate ) {
		return new SimpleDateFormat(DB_DATE_TIME_FORMAT).format(currentDate);
	}
	
	
	/**
	 * @param currentDate String
	 * @see #DB_DATE_TIME_FORMAT
	 * @return Timestamp
	 */
	public static Timestamp parseTime ( String currentDate ) {	
		try {
			return new Timestamp(new SimpleDateFormat(DB_DATE_TIME_FORMAT).parse(currentDate).getTime());
		} catch (ParseException e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	  /**
    * @param currentDate String
    * 
    * @return Timestamp
    */
   public static Timestamp parseTimeYMD ( String currentDate ) {  
      try {
         return new Timestamp(new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(currentDate).getTime());
      } catch (ParseException e) {
         //e.printStackTrace();
         return null;
      }
   }
	/** Metodo per convertire una stringa dal formato dd/mm/yyyy 
	 *  nel formato del db yyyymmdd
	 *  
	 * @param date String
	 * @return String
	 */
	public static String getFormattedDBDate( String date ) {
		if( date == null ) return "NULL";
		if(date.trim().length() == 8 || !date.contains("/") )
			return date;
		if ( date.trim().length() > 0 && date.length()==10) {
			return date.substring(6,10)+date.substring(3,5) + date.substring(0,2);
		} else {
			return "";
		}
	}
	
	/**
	 * @param date String
	 * @return String
	 */
	public static String formatDateOrNull(String date){
		String temp = getFormattedDBDate(date);
		if("null".equalsIgnoreCase(temp) || "".equalsIgnoreCase(temp.trim()))
			return null;
		else return temp;
	}
	
	/** Prende in ingresso la data nel formato "2007-10-14 15:12:32.0" e la
	 *  restituisce nel formato "20071014 15:12:32"
	 *  
	 * @param datetime la data nel formato "2007-10-14 15:12:32.0"
	 * @return la data nel formato "20071014 15:12:32"
	 */
	public static String getFormattedDBDateTime( String datetime ) {
		
		// PP per riutilizzare la funzione opero questa replace che non dovrebbe dare
		// problemi
		String ldat = datetime.replace("T", " ");
		
		if( ldat == null ) return null;
		if ( ldat.trim().length() > 0 ) {
			
			return getYear(ldat) + ldat.substring(5,7) + ldat.substring(8,10) 
			+" "+ getTime(ldat); 
		} else {
			return "";
		}
	}
	
	/**
	 * @param datetime String
	 * @return String
	 */
	public static String getDBDateTime(String datetime){
		if( datetime == null ) return null;
		if ( datetime.trim().length() > 0 ) {
			StringTokenizer st = new StringTokenizer(datetime);
			String date = st.nextToken();
			return getYear(date)+"-"+getMonth(date)+"-"+getDay(date)+" "+st.nextToken(); 
		} else {
			return "";
		}
	}

	/**
	 * Dato un oggetto di tipo calendar
	 * 
	 * restituisce yyyymmdd
	 * @param date
	 * @return String
	 */
	public static String getFormattedCalendarDate( Calendar date ) {
		// PP if( date == null ) return "NULL";
		if( date == null ) return "";

		SimpleDateFormat out = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		
		return out.format(date.getTime());
	}

	/**
	 * Data una string contenente una data in formato gg/mm/aaaa 
	 * restituisce un oggetto SqlDate
	 * 
	 * @param date String
	 * @return Date
	 */
	public static java.sql.Date getSqlDateFromFormattedDate( String date ) {
		
		if( date == null || "".equals(date)) 
			return null;
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(6)));
		c.set(Calendar.MONTH, Integer.valueOf(date.substring(4, 6))-1);
		c.set(Calendar.YEAR, Integer.valueOf(date.substring(0, 4)));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		java.sql.Date jsqlD = java.sql.Date.valueOf( 
	            c.get(Calendar.YEAR) + "-" + 
	            (c.get(Calendar.MONTH)+1) + "-" + 
	            c.get(Calendar.DAY_OF_MONTH));
		return jsqlD;
	}

	   /**
     * Data una string contenente una data in formato yyyymmdd 
     * restituisce un oggetto SqlDate
     * 
     * @param date String
     * @return Date
     */
    public static java.sql.Date getSqlDateFromYMD( String date ) {
        
        if( date == null || "".equals(date)) 
            return null;
        
        java.sql.Date jsqlD = java.sql.Date.valueOf(date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8));
        
        return jsqlD;
    }
	/**
	 * Il metodo si occupa della corretta formattazione di un importo inserendo il punto 
	 * come separatore delle migliaia e la virgola come separatore decimale. In ingresso 
	 * il metodo ha un valore con il punto come separatore decimale. Il metodo e'
	 * un'estensione di 
	 * "replaceDotsWithCommas(BigDecimal dotVal)". 
	 *  
	 * @param dotVal un BigDecimal
	 * @return String - la stringa rappresentante il separatore decimale con un punto,
	 *	 senza eventuali punti per le migliaia
	 * */
	public static String formattaImporto(BigDecimal dotVal){
		//WARN : MODIFICATO per visualizzare 0,000 nel
		//caso in cui il valore sia zero.
		if(dotVal != null){
			String newImporto = PageHelper.replaceDotsWithCommas(dotVal);
			String toConvert,endWithcomma = "";
			try{
				toConvert = newImporto.substring(0, newImporto.indexOf(","));
				endWithcomma = newImporto.substring(newImporto.indexOf(","));
			}catch(StringIndexOutOfBoundsException e){
				//non c'e' la virgola
				toConvert = newImporto;
			}
			toConvert = putKDots(toConvert);
			//System.out.println(toConvert+endWithcomma);
			return toConvert+endWithcomma;
		}else if(dotVal != null && (dotVal.compareTo(new BigDecimal(0))==0)){
			return "0,000";
		}return "";
	}
	/**
	 * Data una stringa rappresentante un numero (senza virgole o punti), il metodo 
	 * restituisce la stringa avente il punto come separatore delle migliaia
	 * 
	 * @param toProcess String stringa rappresentante un numero (senza virgole o punti) 
	 * @return String - la stringa con i punti per le migliaia
	 */
	private static String putKDots(String toProcess){
		if(toProcess.length() > 3){
			return putKDots(toProcess.substring(0,toProcess.length()-3))+"."+toProcess.substring(toProcess.length()-3);
		}return toProcess;
	}
	
	/**
	 * Il metodo si occupa di formattare il valore in ingresso rimuovendo gli eventuali punti come separataori delle migliaia 
	 * e sostituendo la virgola eventuale (come separatore decimale) con un punto.  
	 *
	 * @param importoStr String la stringa rappresentante il numero decimale con la virgola, con i punti per le migliaia
	 * @return String la stringa rappresentante il decimale con un punto
	 */
	public static String formattaImporto(String importoStr){
		if(importoStr != null && ! importoStr.equals("") && importoStr.indexOf(",") != -1){
			String toConvert,endWithcomma = "";
			try{
				toConvert = importoStr.substring(0, importoStr.indexOf(","));
				endWithcomma = importoStr.substring(importoStr.indexOf(","));
				endWithcomma = PageHelper.replaceCommasWithDots(endWithcomma);
			}catch(StringIndexOutOfBoundsException e){
				//non c'e' la virgola
				toConvert = importoStr;
			}
			//System.out.println("()()()()formattaImporto: INPUT->"+importoStr+" , OUTPUT:->"+toConvert.replace(".", "")+endWithcomma);
			return toConvert.replace(".", "")+endWithcomma;
		}return importoStr;
	}
	/**
	 * Restituisce  una stringa ottenuta sostituendo al punto, utilizzato come 
	 * separatore decimale nel BigDecimal in ingresso, la virgola.
	 *
	 * @param dotVal BigDecimal la stringa rappresentante il numero decimale
	 * @return String la stringa rappresentante il decimale con la virgola
	 */
	public static String replaceDotsWithCommas(BigDecimal dotVal){
		String commaVal = "";
		if(dotVal != null)
			commaVal = dotVal.toString();
		return commaVal.replace(".", ",");
		
	}
	
	/**
	 * Il metodo restituisce la stringa in ingresso avendo sostituito al punto la virgola 
	 *
	 * @param value String contenente il valore da formattare
	 * @return String
	 */
	public static String replaceDotsWithCommas(String value){
		
		if(value == null || "".equals(value)) return value;
		return (value.replace(".", ","));
	}
	
	/**
	 * Sostituisce la virgola nella stringa in ingresso, con il punto.
	 * 
	 * param value String la stringa rappresentante il numero decimale
	 * return String - la stringa rappresentante il decimale con il punto.
	 */
	private static String replaceCommasWithDots(String value){
		if(value == null || "".equals(value)) return value;
		return value.replace(",", ".");
	}
	
	/**
	 * Il metodo inserisce all'interno di una HashMap i nomi delle seguenti tabelle :
	 * <ul>
	 * <li>Dati Comuni
	 * <li>Aggiudicazioni
	 * <li>Inizio Lavori
	 * <li>Stati Avanzamento
	 * <li>Collaudo
	 * <li>Sospensione
	 * <li>Variante
	 * <li>Accordo Bonario
	 * <li>Subappalto
	 * <li>Ritardo
	 * </ul>
	 * 
	 * @return HashMap&lt;String, String&gt;
	 */
	
	/**
	 * il metodo permette di ottenere una stringa con la data formattata in yyyyMMdd  
	 * ottenuta dal Timestamp in ingresso
	 * 
	 * @param ts Timestamp da cui si vuole ottenere la stringa formattata
	 * @return String nel formato yyyyMMdd ottenuta da ts
	 */
	public static String getDBDateFromTS(Timestamp ts){
		if (ts == null) return null;
		else
			return (new SimpleDateFormat(DEFAULT_DATE_FORMAT)).format(ts.getTime());
	}
	/*funzione che prende una stringa yyyymmdd e ritorna un calendar*/
	
	/**
	 * Il metodo permette di convertire una data da stringa a Caendar
	 * 
	 * @param data String della data nel formato yyyyMMdd
	 * @return Calendar
	 */
	public static Calendar getCalendarFromStringDate(String data){
		try{
			if(data!=null){
				SimpleDateFormat df=new SimpleDateFormat(DEFAULT_DATE_FORMAT);
				Calendar cal=Calendar.getInstance();
				String temp = PageHelper.getFormattedDBDate(data);
				Date d1;
				if(temp.equals("")){	d1 = df.parse(data);	}
				else{					d1 = df.parse(temp);	}
			    cal.setTime(d1);
			    return cal;
			}
		}catch(Exception e){/*e.printStackTrace();*/}
		
		return null;
	}
	/**
	 * @param o Object puo' essere un Timestamp o una String con formato yyyymmdd
	 * @param getNow Timestamp data corrente presa dal db
	 * @return String - una stringa formattata come yyyymmdd
	 * @throws Exception - nel caso l'oggetto ( o ) in ingresso non sia ne di tipo Timestamp ne String
	 */
	public static String getFormattedNowOrInputFormattedDate(Object o, Timestamp getNow)throws Exception{
		try{
			if(o != null){
				if(o instanceof String){
					if(((String)o).equals("")){return PageHelper.getDBDateFromTS(getNow);}
					else if(!((String)o).equals("") && getNow == null){ 
						/** devo formattare la stringa */
						return PageHelper.getFormattedDBDateTime((String)o);
					}else{ return (String)o; }			
				}else if(o instanceof Timestamp){
					return PageHelper.getDBDateFromTS((Timestamp)o);
				}else{
					throw new Exception("unexpected Object type");
				}
			}return PageHelper.getDBDateFromTS(getNow);
		}catch(Exception e){
			throw new Exception("Errore durante la formattazione di una data in [getFormattedNowOrInputFormattedDate]:"+e.getMessage());
		}
	}
	   public static String HTMLEntityEncode( String s ){
	       StringBuffer buf = new StringBuffer();
	       int len = (s == null ? -1 : s.length());

	       for ( int i = 0; i < len; i++ ){
	           char c = s.charAt( i );
	           if ( c>='a' && c<='z' || c>='A' && c<='Z' || c>='0' && c<='9' ){
	               buf.append( c );
	           }else{
	               buf.append( "&#" + (int)c + ";" );
	           }
	       }
	       return buf.toString();
	   }
//	public static void main(String[] args){
//		PageHelper.formattaImporto(new BigDecimal(7030100234.900000));
//		PageHelper.formattaImporto("7.030.100.234");
//	}
//	public static void main ( String [] args ) throws Exception {
//		/*
//		System.out.println("Domani [" + getNextDay() + "]");
//		System.out.println("Domani mese [" + getNextDayMonth() + "]");
//		System.out.println("Domani anno [" + getNextDayYear() + "]");
//		*/
//		
//		System.out.println(getFormattedImporto("2000.20"));
//	
//	}
	   
   /**
    * Verifica che una stringa è in formato numerico
    * 
	* @param str String
	* @return boolean
	*/
	static public boolean isNumeric(String str) {
		int sz = str.length();
		for(int i=0; i<sz; i++) {
			if(!Character.isDigit(str.charAt(i))) {
				return false;
}
	    }
	    return true;
	}
	
	/**
	 * Formattazione del testo per evitare l'inserimento illegale di codice html/javascript
	 * 
	 * @param testo String
	 * @return String
	 */
	static public String formattaTesto(String testo){
		// (interna) PP caratteri non ammessi corrompono la pagina elencogarerssa
	   //return "<![CDATA[" + testo + "]]>";
		return testo == null ? "" 
		      : testo.replace("<", "&lt;")
		      .replace(">", "&gt;")
		      .replace("“", "&ldquo;")
		      .replace("”", "&rdquo;")
            .replace("‘", "&lsquo;")
            .replace("’", "&rsquo;")
            .replace("'", "&#39;")
            .replace("\"", "&quot;");
	}

	public static String getNulledField(String field) {
		String retVal = field;
		if ( retVal == null || "null".equalsIgnoreCase(retVal) ) {
			retVal = "";
		}
		return retVal;
	}
	
	public static String decodeSN(String flag){
		
		return Costanti.FLAG_VALORE_SI.equals(flag) ? "Si" : Costanti.FLAG_VALORE_NO.equals(flag) ? "No" : "";
	}
	
	public static boolean isValidCIG (String cig){
       if (cig == null)
          return false;
       else if (cig.length() == 0)
          return false;
       else if(cig.length() != 10)
             return false;             
       else if(!cig.matches("[0-9A-U][0-9A-F]{6}[0-9A-Fa-f]{3}"))  //nuovo algoritmo cig
          return false;
       else
          return true;
	}

   /**
    * Trasforma tutti i caratteri UTF-8 che non esistono nel set di caratteri ISO-8859-1
    * nella relativa entitÃ  (&#nnn;)
    *
    * @param utf8 stringa UTF-8 da codificare in ISO-8859
    * @return la stringa codificata.
    */
   public static String encodeUTF8ToASCII(String utf8){
        if(utf8 == null)
            return "";
        
        if(utf8.length() == 0)
           return "";
         
       StringBuffer sb = new StringBuffer();
       char[] c = utf8.toCharArray();
           
       for(int i=0; i<c.length; i++){
           if(32 <= (int)c[i] && (int)c[i] <= 122)
               sb.append(c[i]);
           else   
               sb.append("&#" + (int)c[i] + ";");
       }
      
       return sb.toString();
   }
   
   /*
    * soctituisce caratteri speciali
    */
   public static String replaceWordChars(String text) {
      
      if(text == null || text.isEmpty())
         return text;
      
      String s = text;
      
      // smart single quotes and apostrophe
      s = s.replace("/[\u2018|\u2019|\u201A]/g", "\'");
      // smart double quotes
      s = s.replace("/[\u201C|\u201D|\u201E]/g", "\'");
      // ellipsis
      s = s.replace("/\u2026/g", "...");
      // dashes
      s = s.replace("/[\u2013|\u2014]/g", "-");
      // circumflex
      s = s.replace("/\u02C6/g", "^");
      // open angle bracket
      s = s.replace("/\u2039/g", "<");
      // close angle bracket
      s = s.replace("/\u203A/g", ">");
      // spaces
      s = s.replace("/[\u02DC|\u00A0]/g", " ");
      s = s.replace("/[\u00C2|\u00B0]/g", "^");
      
      // Line feed / CR
      s = s.replace("/[\n\r|\n|\r]/g", "");
      s = s.replace("\n", "");
      s = s.replace("\r", "");
      s = s.replace("&#13;", "");
      s = s.replace("&#10;", "");

      return s;
   }
}
