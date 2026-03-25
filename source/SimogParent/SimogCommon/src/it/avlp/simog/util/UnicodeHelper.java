package it.avlp.simog.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import it.avlp.simog.util.VarcharUnicodeEncoder;

/**
 * Utility class per gestire correttamente stringhe Unicode (arabo) nel database.
 * 
 * Questa classe garantisce che le stringhe contenenti caratteri Unicode
 * (come l'arabo) vengano salvate correttamente nel database SQL Server.
 * 
 * Utilizzo:
 *   UnicodeHelper.setUnicodeString(pstmt, 1, "نص عربي");
 * 
 * @author SIMOG Development Team
 * @version 1.1 - Migliorata gestione errori e robustezza
 */
public class UnicodeHelper {
    
    private static Logger logger = Logger.getLogger(UnicodeHelper.class);
    
    // Cache per il metodo setNString per evitare reflection ripetute
    private static java.lang.reflect.Method cachedSetNStringMethod = null;
    private static boolean setNStringAvailable = false;
    private static boolean setNStringChecked = false;
    
    /**
     * Verifica se setNString() è disponibile e lo cache per performance.
     * Thread-safe tramite synchronized.
     */
    private static synchronized void checkSetNStringAvailability(PreparedStatement pstmt) {
        if (setNStringChecked) {
            return; // Già verificato
        }
        
        try {
            cachedSetNStringMethod = pstmt.getClass().getMethod("setNString", int.class, String.class);
            setNStringAvailable = true;
            logger.debug("setNString() disponibile per questo PreparedStatement");
        } catch (NoSuchMethodException e) {
            setNStringAvailable = false;
            logger.debug("setNString() non disponibile, userà setString() con sendStringParametersAsUnicode=true");
        } catch (Exception e) {
            setNStringAvailable = false;
            logger.warn("Errore durante verifica disponibilità setNString(): " + e.getMessage());
        } finally {
            setNStringChecked = true;
        }
    }
    
    /**
     * Imposta un parametro stringa su PreparedStatement garantendo supporto Unicode.
     * 
     * Per SQL Server, usa setNString() se disponibile (supporto Unicode nativo),
     * altrimenti usa setString() assumendo che sendStringParametersAsUnicode=true
     * nella configurazione della connessione.
     * 
     * Questo metodo è AGNOSTICO rispetto alla configurazione del database:
     * - Funziona con qualsiasi versione di SQL Server
     * - Funziona con qualsiasi versione del driver JDBC
     * - Ha fallback automatico se setNString() non è disponibile
     * - Funziona anche se sendStringParametersAsUnicode non è configurato (ma con limitazioni)
     * 
     * Gestisce automaticamente:
     * - Valori null
     * - Reflection errors (fallback a setString)
     * - SQLException (rilancia dopo logging)
     * - Versioni diverse di driver JDBC
     * 
     * @param pstmt PreparedStatement su cui impostare il parametro
     * @param parameterIndex indice del parametro (1-based, come in PreparedStatement)
     * @param value valore stringa da impostare (può essere null)
     * @throws SQLException se si verifica un errore durante l'impostazione
     * @throws IllegalArgumentException se pstmt è null o parameterIndex < 1
     */
    public static void setUnicodeString(PreparedStatement pstmt, int parameterIndex, String value) throws SQLException {
        // Validazione input
        if (pstmt == null) {
            throw new IllegalArgumentException("PreparedStatement non può essere null");
        }
        if (parameterIndex < 1) {
            throw new IllegalArgumentException("parameterIndex deve essere >= 1 (1-based)");
        }
        
        // Gestione null
        if (value == null) {
            try {
                pstmt.setString(parameterIndex, null);
                return;
            } catch (SQLException e) {
                logger.error("Errore impostando parametro null all'indice " + parameterIndex, e);
                throw e;
            }
        }
        
        // Verifica disponibilità setNString (solo la prima volta)
        if (!setNStringChecked) {
            checkSetNStringAvailability(pstmt);
        }
        
        // Prova a usare setNString() per SQL Server (supporto Unicode nativo)
        if (setNStringAvailable && cachedSetNStringMethod != null) {
            try {
                cachedSetNStringMethod.invoke(pstmt, parameterIndex, value);
                if (logger.isDebugEnabled() && containsNonASCII(value)) {
                    logger.debug("Usato setNString() per parametro " + parameterIndex + " (contiene caratteri Unicode)");
                }
                return; // Successo
            } catch (java.lang.reflect.InvocationTargetException e) {
                // Se l'invocazione fallisce, potrebbe essere un SQLException
                Throwable cause = e.getCause();
                if (cause instanceof SQLException) {
                    logger.error("SQLException durante setNString() per parametro " + parameterIndex + 
                               ": " + cause.getMessage(), cause);
                    throw (SQLException) cause;
                }
                // Altri errori di reflection, fallback a setString()
                logger.warn("Errore durante invocazione setNString() per parametro " + parameterIndex + 
                          ", uso setString() come fallback: " + e.getMessage());
            } catch (Exception e) {
                // Altri errori di reflection, fallback a setString()
                logger.warn("Errore durante invocazione setNString() per parametro " + parameterIndex + 
                          ", uso setString() come fallback: " + e.getMessage());
            }
        }
        
        // Fallback a setString() (assumendo sendStringParametersAsUnicode=true nella connessione)
        try {
            pstmt.setString(parameterIndex, value);
            if (logger.isDebugEnabled() && containsNonASCII(value)) {
                logger.debug("Usato setString() per parametro " + parameterIndex + 
                          " (setNString non disponibile o fallito)");
            }
        } catch (SQLException e) {
            // Log dettagliato dell'errore
            logger.error("SQLException durante setString() per parametro " + parameterIndex + 
                       " con valore: " + (value.length() > 100 ? value.substring(0, 100) + "..." : value), e);
            throw e;
        }
    }
    
    /**
     * Verifica se una stringa contiene caratteri non-ASCII (potenzialmente Unicode).
     * 
     * I caratteri ASCII sono quelli con codice <= 127.
     * Caratteri > 127 includono caratteri arabi, accenti, simboli speciali, ecc.
     * 
     * @param value stringa da verificare (può essere null)
     * @return true se la stringa contiene caratteri non-ASCII, false altrimenti
     */
    public static boolean containsNonASCII(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            // Caratteri arabi sono nel range 0x0600-0x06FF
            // Altri caratteri Unicode sono > 127
            if (c > 127) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifica se una stringa contiene caratteri arabi specificamente.
     * 
     * I caratteri arabi sono nel range Unicode 0x0600-0x06FF.
     * 
     * @param value stringa da verificare (può essere null)
     * @return true se la stringa contiene caratteri arabi, false altrimenti
     */
    public static boolean containsArabic(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            // Range Unicode per caratteri arabi: 0x0600-0x06FF
            if (c >= 0x0600 && c <= 0x06FF) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Imposta un parametro stringa su PreparedStatement, usando automaticamente
     * setUnicodeString() se la stringa contiene caratteri non-ASCII,
     * altrimenti usa setString() normale per performance.
     * 
     * Questo è il metodo consigliato per uso generale.
     * 
     * @param pstmt PreparedStatement su cui impostare il parametro
     * @param parameterIndex indice del parametro (1-based)
     * @param value valore stringa da impostare (può essere null)
     * @throws SQLException se si verifica un errore durante l'impostazione
     * @throws IllegalArgumentException se pstmt è null o parameterIndex < 1
     */
    public static void setStringSmart(PreparedStatement pstmt, int parameterIndex, String value) throws SQLException {
        if (value == null) {
            pstmt.setString(parameterIndex, null);
        } else if (containsNonASCII(value)) {
            // Usa setUnicodeString per caratteri non-ASCII
            setUnicodeString(pstmt, parameterIndex, value);
        } else {
            // Usa setString normale per caratteri ASCII (più veloce)
            pstmt.setString(parameterIndex, value);
        }
    }
    
    /**
     * Imposta un parametro stringa su PreparedStatement con verifica automatica del tipo colonna.
     * 
     * Questo metodo verifica se la colonna è VARCHAR o NVARCHAR e:
     * - Se NVARCHAR: usa setUnicodeString() normale
     * - Se VARCHAR: codifica automaticamente in Base64 se contiene caratteri Unicode
     * 
     * Questo è il metodo consigliato quando si conoscono le informazioni sulla colonna.
     * 
     * @param pstmt PreparedStatement su cui impostare il parametro
     * @param parameterIndex indice del parametro (1-based)
     * @param value valore stringa da impostare (può essere null)
     * @param connection Connection al database per verificare il tipo colonna
     * @param tableName nome della tabella
     * @param columnName nome della colonna
     * @throws SQLException se si verifica un errore durante l'impostazione
     * @throws IllegalArgumentException se pstmt è null o parameterIndex < 1
     */
    public static void setStringSmart(PreparedStatement pstmt, int parameterIndex, String value, 
                                     Connection connection, String tableName, String columnName) throws SQLException {
        if (value == null) {
            pstmt.setString(parameterIndex, null);
            return;
        }
        
        // Verifica se la colonna è Unicode-capable (NVARCHAR/NTEXT/NCHAR)
        boolean isUnicodeColumn = false;
        if (connection != null && tableName != null && columnName != null) {
            try {
                isUnicodeColumn = UnicodeColumnHandler.isColumnUnicode(connection, tableName, columnName);
            } catch (Exception e) {
                logger.warn("Errore durante verifica tipo colonna " + tableName + "." + columnName + 
                           ", assumo NVARCHAR: " + e.getMessage());
                // In caso di errore, assumiamo NVARCHAR per sicurezza
                isUnicodeColumn = true;
            }
        }
        
        if (isUnicodeColumn) {
            // Colonna NVARCHAR/NTEXT, usa setUnicodeString normale
            if (containsNonASCII(value)) {
                setUnicodeString(pstmt, parameterIndex, value);
            } else {
                pstmt.setString(parameterIndex, value);
            }
        } else {
            // Colonna VARCHAR/TEXT, usa encoding se contiene caratteri non-ASCII
            if (containsNonASCII(value)) {
                String encodedValue = VarcharUnicodeEncoder.encode(value);
                pstmt.setString(parameterIndex, encodedValue);
                if (logger.isDebugEnabled()) {
                    logger.debug("Encoded Unicode string for VARCHAR column " + tableName + "." + columnName + 
                               ": " + (encodedValue.length() > 50 ? encodedValue.substring(0, 50) + "..." : encodedValue));
                }
            } else {
                // Stringa ASCII, usa setString normale
                pstmt.setString(parameterIndex, value);
            }
        }
    }
}
