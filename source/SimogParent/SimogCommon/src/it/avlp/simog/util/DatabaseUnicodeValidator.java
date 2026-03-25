package it.avlp.simog.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Utility class per validare la configurazione del database per supporto Unicode.
 * 
 * Verifica:
 * - Versione SQL Server
 * - Collation del database
 * - Configurazione connessione (sendStringParametersAsUnicode)
 * - Disponibilità setNString() nel driver
 * 
 * @author SIMOG Development Team
 * @version 1.0
 */
public class DatabaseUnicodeValidator {
    
    private static Logger logger = Logger.getLogger(DatabaseUnicodeValidator.class);
    
    /**
     * Risultato della validazione della configurazione Unicode del database.
     */
    public static class ValidationResult {
        private boolean isValid;
        private String databaseProduct;
        private String databaseVersion;
        private String databaseCollation;
        private boolean sendStringParametersAsUnicode;
        private boolean setNStringAvailable;
        private String warnings;
        private String errors;
        
        public ValidationResult() {
            this.isValid = true;
            this.warnings = "";
            this.errors = "";
        }
        
        public boolean isValid() { return isValid; }
        public void setValid(boolean valid) { this.isValid = valid; }
        
        public String getDatabaseProduct() { return databaseProduct; }
        public void setDatabaseProduct(String product) { this.databaseProduct = product; }
        
        public String getDatabaseVersion() { return databaseVersion; }
        public void setDatabaseVersion(String version) { this.databaseVersion = version; }
        
        public String getDatabaseCollation() { return databaseCollation; }
        public void setDatabaseCollation(String collation) { this.databaseCollation = collation; }
        
        public boolean isSendStringParametersAsUnicode() { return sendStringParametersAsUnicode; }
        public void setSendStringParametersAsUnicode(boolean value) { this.sendStringParametersAsUnicode = value; }
        
        public boolean isSetNStringAvailable() { return setNStringAvailable; }
        public void setSetNStringAvailable(boolean available) { this.setNStringAvailable = available; }
        
        public String getWarnings() { return warnings; }
        public void addWarning(String warning) {
            if (warnings.isEmpty()) {
                warnings = warning;
            } else {
                warnings += "; " + warning;
            }
        }
        
        public String getErrors() { return errors; }
        public void addError(String error) {
            isValid = false;
            if (errors.isEmpty()) {
                errors = error;
            } else {
                errors += "; " + error;
            }
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ValidationResult{");
            sb.append("isValid=").append(isValid);
            sb.append(", database=").append(databaseProduct);
            sb.append(", version=").append(databaseVersion);
            sb.append(", collation=").append(databaseCollation);
            sb.append(", sendStringParametersAsUnicode=").append(sendStringParametersAsUnicode);
            sb.append(", setNStringAvailable=").append(setNStringAvailable);
            if (!warnings.isEmpty()) {
                sb.append(", warnings=[").append(warnings).append("]");
            }
            if (!errors.isEmpty()) {
                sb.append(", errors=[").append(errors).append("]");
            }
            sb.append("}");
            return sb.toString();
        }
    }
    
    /**
     * Valida la configurazione del database per supporto Unicode.
     * 
     * @param connection Connessione al database
     * @return ValidationResult con i risultati della validazione
     */
    public static ValidationResult validateUnicodeSupport(Connection connection) {
        ValidationResult result = new ValidationResult();
        
        if (connection == null) {
            result.addError("Connection è null");
            return result;
        }
        
        try {
            // 1. Verifica tipo e versione database
            DatabaseMetaData metaData = connection.getMetaData();
            String productName = metaData.getDatabaseProductName();
            String productVersion = metaData.getDatabaseProductVersion();
            
            result.setDatabaseProduct(productName);
            result.setDatabaseVersion(productVersion);
            
            logger.info("Database rilevato: " + productName + " " + productVersion);
            
            // Verifica che sia SQL Server
            if (!productName.toLowerCase().contains("sql server") && 
                !productName.toLowerCase().contains("microsoft")) {
                result.addWarning("Database non è SQL Server: " + productName + 
                                ". Il supporto Unicode potrebbe non essere garantito.");
            }
            
            // 2. Verifica collation del database
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(
                     "SELECT name, collation_name FROM sys.databases WHERE name = DB_NAME()");
                
                if (rs.next()) {
                    String collation = rs.getString("collation_name");
                    result.setDatabaseCollation(collation);
                    
                    logger.info("Collation database: " + collation);
                    
                    // Verifica se il collation supporta Unicode
                    if (collation != null) {
                        // Collation che supportano Unicode generalmente hanno caratteristiche specifiche
                        // Non possiamo verificare direttamente, ma possiamo avvisare se sembra problematico
                        if (collation.contains("_CS_") || collation.contains("_BIN")) {
                            // Case-sensitive o Binary - potrebbe essere OK, ma avvisiamo
                            result.addWarning("Collation case-sensitive o binary: " + collation + 
                                            ". Verificare supporto Unicode.");
                        }
                    } else {
                        result.addWarning("Collation database non rilevato. Verificare supporto Unicode.");
                    }
                } else {
                    result.addWarning("Impossibile rilevare collation database.");
                }
            } catch (SQLException e) {
                logger.warn("Errore durante verifica collation: " + e.getMessage());
                result.addWarning("Impossibile verificare collation: " + e.getMessage());
            } finally {
                if (rs != null) {
                    try { rs.close(); } catch (SQLException e) { /* ignore */ }
                }
                if (stmt != null) {
                    try { stmt.close(); } catch (SQLException e) { /* ignore */ }
                }
            }
            
            // 3. Verifica configurazione connessione (sendStringParametersAsUnicode)
            // Non possiamo verificare direttamente dalla connessione, ma possiamo testare
            String connectionUrl = metaData.getURL();
            if (connectionUrl != null) {
                boolean hasUnicodeParam = connectionUrl.toLowerCase().contains("sendstringparametersasunicode=true");
                result.setSendStringParametersAsUnicode(hasUnicodeParam);
                
                if (!hasUnicodeParam) {
                    result.addError("sendStringParametersAsUnicode non è true nella connection URL: " + connectionUrl);
                } else {
                    logger.info("sendStringParametersAsUnicode=true rilevato nella connection URL");
                }
            } else {
                result.addWarning("Impossibile verificare connection URL. Assicurarsi che sendStringParametersAsUnicode=true.");
            }
            
            // 4. Verifica disponibilità setNString() nel driver
            try {
                java.sql.PreparedStatement testPstmt = connection.prepareStatement("SELECT ?");
                java.lang.reflect.Method setNStringMethod = testPstmt.getClass().getMethod("setNString", int.class, String.class);
                result.setSetNStringAvailable(true);
                logger.info("setNString() disponibile nel driver JDBC");
                testPstmt.close();
            } catch (NoSuchMethodException e) {
                result.setSetNStringAvailable(false);
                result.addWarning("setNString() non disponibile nel driver JDBC. Verrà usato setString() con sendStringParametersAsUnicode=true.");
                logger.warn("setNString() non disponibile: " + e.getMessage());
            } catch (Exception e) {
                result.setSetNStringAvailable(false);
                result.addWarning("Errore durante verifica setNString(): " + e.getMessage());
                logger.warn("Errore durante verifica setNString(): " + e.getMessage());
            }
            
        } catch (SQLException e) {
            logger.error("Errore durante validazione configurazione database: " + e.getMessage(), e);
            result.addError("Errore durante validazione: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * Verifica se una colonna specifica è NVARCHAR (supporta Unicode).
     * 
     * @param connection Connessione al database
     * @param tableName Nome della tabella
     * @param columnName Nome della colonna
     * @return true se la colonna è NVARCHAR/NTEXT, false se è VARCHAR/TEXT
     */
    public static boolean isColumnUnicode(Connection connection, String tableName, String columnName) {
        if (connection == null || tableName == null || columnName == null) {
            return false;
        }
        
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(
                 "SELECT ty.name AS DataType " +
                 "FROM sys.tables t " +
                 "INNER JOIN sys.columns c ON t.object_id = c.object_id " +
                 "INNER JOIN sys.types ty ON c.user_type_id = ty.user_type_id " +
                 "WHERE t.name = '" + tableName.replace("'", "''") + "' " +
                 "AND c.name = '" + columnName.replace("'", "''") + "'");
            
            if (rs.next()) {
                String dataType = rs.getString("DataType");
                boolean isUnicode = "nvarchar".equalsIgnoreCase(dataType) || 
                                   "ntext".equalsIgnoreCase(dataType) ||
                                   "nchar".equalsIgnoreCase(dataType);
                
                logger.debug("Colonna " + tableName + "." + columnName + " tipo: " + dataType + 
                           " (Unicode: " + isUnicode + ")");
                return isUnicode;
            }
        } catch (SQLException e) {
            logger.warn("Errore durante verifica tipo colonna " + tableName + "." + columnName + 
                       ": " + e.getMessage());
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { /* ignore */ }
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            }
        }
        
        return false;
    }
    
    /**
     * Logga un report completo della validazione.
     * 
     * @param result Risultato della validazione
     */
    public static void logValidationReport(ValidationResult result) {
        if (result.isValid()) {
            logger.info("✅ Validazione configurazione Unicode: OK");
        } else {
            logger.error("❌ Validazione configurazione Unicode: ERRORI TROVATI");
        }
        
        logger.info("Database: " + result.getDatabaseProduct() + " " + result.getDatabaseVersion());
        logger.info("Collation: " + result.getDatabaseCollation());
        logger.info("sendStringParametersAsUnicode: " + result.isSendStringParametersAsUnicode());
        logger.info("setNString() disponibile: " + result.isSetNStringAvailable());
        
        if (!result.getWarnings().isEmpty()) {
            logger.warn("⚠️ Warnings: " + result.getWarnings());
        }
        
        if (!result.getErrors().isEmpty()) {
            logger.error("❌ Errors: " + result.getErrors());
        }
    }
}

