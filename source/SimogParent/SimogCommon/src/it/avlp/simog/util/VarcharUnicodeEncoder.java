package it.avlp.simog.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.log4j.Logger;

/**
 * Codifica/Decodifica stringhe Unicode per essere salvate in colonne VARCHAR.
 * 
 * Questa classe fornisce una soluzione alternativa quando le colonne sono VARCHAR
 * invece di NVARCHAR. I dati Unicode vengono codificati in una rappresentazione
 * compatibile con VARCHAR (Base64) prima di essere salvati, e decodificati quando
 * vengono letti.
 * 
 * ATTENZIONE: Questa è una soluzione workaround. La soluzione migliore è usare
 * colonne NVARCHAR. Questa soluzione:
 * - Aumenta la dimensione dei dati (Base64 aumenta ~33%)
 * - Richiede encoding/decoding a ogni lettura/scrittura
 * - Potrebbe causare problemi con ricerche e ordinamenti
 * 
 * Utilizzo:
 *   String encoded = VarcharUnicodeEncoder.encode("نص عربي");
 *   String decoded = VarcharUnicodeEncoder.decode(encoded);
 * 
 * @author SIMOG Development Team
 * @version 1.0
 */
public class VarcharUnicodeEncoder {
    
    private static Logger logger = Logger.getLogger(VarcharUnicodeEncoder.class);
    
    // Prefisso per identificare stringhe codificate
    private static final String ENCODED_PREFIX = "UENC:";
    
    // Flag per abilitare/disabilitare encoding automatico
    private static boolean autoEncodeEnabled = false;
    
    /**
     * Abilita encoding automatico per tutte le stringhe Unicode.
     * Quando abilitato, tutte le stringhe con caratteri non-ASCII vengono
     * automaticamente codificate prima di essere salvate.
     */
    public static void enableAutoEncode() {
        autoEncodeEnabled = true;
        logger.info("Encoding automatico Unicode abilitato per colonne VARCHAR");
    }
    
    /**
     * Disabilita encoding automatico.
     */
    public static void disableAutoEncode() {
        autoEncodeEnabled = false;
        logger.info("Encoding automatico Unicode disabilitato");
    }
    
    /**
     * Verifica se l'encoding automatico è abilitato.
     */
    public static boolean isAutoEncodeEnabled() {
        return autoEncodeEnabled;
    }
    
    /**
     * Codifica una stringa Unicode per essere salvata in VARCHAR.
     * 
     * Se la stringa contiene solo caratteri ASCII, viene ritornata senza modifiche.
     * Se contiene caratteri Unicode, viene codificata in Base64 con prefisso.
     * 
     * @param value Stringa da codificare (può essere null)
     * @return Stringa codificata o originale se solo ASCII
     */
    public static String encode(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        
        // Se contiene solo ASCII, non codificare
        if (!UnicodeHelper.containsNonASCII(value)) {
            return value;
        }
        
        try {
            // Codifica in Base64
            byte[] utf8Bytes = value.getBytes(StandardCharsets.UTF_8);
            String base64 = Base64.getEncoder().encodeToString(utf8Bytes);
            
            // Aggiungi prefisso per identificare stringhe codificate
            String encoded = ENCODED_PREFIX + base64;
            
            if (logger.isDebugEnabled()) {
                logger.debug("Stringa Unicode codificata: " + value.length() + " caratteri -> " + 
                           encoded.length() + " caratteri (Base64)");
            }
            
            return encoded;
        } catch (Exception e) {
            logger.error("Errore durante codifica stringa Unicode: " + e.getMessage(), e);
            // In caso di errore, ritorna la stringa originale
            return value;
        }
    }
    
    /**
     * Decodifica una stringa precedentemente codificata.
     * 
     * Se la stringa inizia con il prefisso ENCODED_PREFIX, viene decodificata.
     * Altrimenti viene ritornata senza modifiche.
     * 
     * @param value Stringa da decodificare (può essere null)
     * @return Stringa decodificata o originale se non codificata
     */
    public static String decode(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        
        // Se non inizia con il prefisso, non è codificata
        if (!value.startsWith(ENCODED_PREFIX)) {
            return value;
        }
        
        try {
            // Rimuovi prefisso e decodifica Base64
            String base64 = value.substring(ENCODED_PREFIX.length());
            byte[] utf8Bytes = Base64.getDecoder().decode(base64);
            String decoded = new String(utf8Bytes, StandardCharsets.UTF_8);
            
            if (logger.isDebugEnabled()) {
                logger.debug("Stringa Unicode decodificata: " + value.length() + " caratteri -> " + 
                           decoded.length() + " caratteri");
            }
            
            return decoded;
        } catch (Exception e) {
            logger.error("Errore durante decodifica stringa: " + e.getMessage(), e);
            // In caso di errore, ritorna la stringa originale
            return value;
        }
    }
    
    /**
     * Codifica automaticamente una stringa se l'encoding automatico è abilitato
     * e la stringa contiene caratteri non-ASCII.
     * 
     * @param value Stringa da codificare (può essere null)
     * @return Stringa codificata se necessario, altrimenti originale
     */
    public static String encodeIfNeeded(String value) {
        if (!autoEncodeEnabled) {
            return value;
        }
        
        return encode(value);
    }
    
    /**
     * Verifica se una stringa è codificata (inizia con ENCODED_PREFIX).
     * 
     * @param value Stringa da verificare
     * @return true se la stringa è codificata
     */
    public static boolean isEncoded(String value) {
        return value != null && value.startsWith(ENCODED_PREFIX);
    }
    
    /**
     * Stima la dimensione della stringa codificata.
     * Base64 aumenta la dimensione di ~33% rispetto all'UTF-8 originale.
     * 
     * @param originalLength Lunghezza della stringa originale in caratteri
     * @return Dimensione stimata dopo codifica
     */
    public static int estimateEncodedLength(int originalLength) {
        // Base64: ogni 3 byte diventano 4 caratteri
        // UTF-8: caratteri arabi sono 2-3 byte ciascuno
        // Stima conservativa: 2 byte per carattere Unicode * 4/3 per Base64
        return (int) Math.ceil(originalLength * 2.0 * 4.0 / 3.0) + ENCODED_PREFIX.length();
    }
    
    /**
     * Verifica se una stringa può essere salvata in una colonna VARCHAR
     * di dimensione specificata dopo la codifica.
     * 
     * @param value Stringa da verificare
     * @param maxColumnLength Lunghezza massima della colonna VARCHAR
     * @return true se la stringa può essere salvata dopo codifica
     */
    public static boolean canFitInVarchar(String value, int maxColumnLength) {
        if (value == null) {
            return true;
        }
        
        if (!UnicodeHelper.containsNonASCII(value)) {
            // Solo ASCII, nessuna codifica necessaria
            return value.length() <= maxColumnLength;
        }
        
        // Stima dimensione dopo codifica
        int estimatedLength = estimateEncodedLength(value.length());
        return estimatedLength <= maxColumnLength;
    }
}

