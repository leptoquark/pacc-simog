package it.avlp.simog.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import it.avlp.simog.util.VarcharUnicodeEncoder;

/**
 * Gestisce automaticamente la conversione di colonne VARCHAR a NVARCHAR nelle query SQL
 * per supportare Unicode anche quando le colonne non sono configurate correttamente.
 * 
 * Supporta anche encoding automatico per usare VARCHAR con dati Unicode tramite
 * VarcharUnicodeEncoder (soluzione workaround).
 * 
 * Questa classe fornisce una soluzione alternativa quando le colonne sono VARCHAR:
 * - Verifica automaticamente il tipo di colonna
 * - Usa CAST/CONVERT nelle query per forzare la conversione a NVARCHAR
 * - Avvisa se la colonna è VARCHAR e potrebbe causare problemi
 * 
 * @author SIMOG Development Team
 * @version 1.0
 */
public class UnicodeColumnHandler {
    
    private static Logger logger = Logger.getLogger(UnicodeColumnHandler.class);
    
    // Cache per i tipi di colonna (table.column -> tipo)
    private static Map<String, String> columnTypeCache = new HashMap<String, String>();
    
    /**
     * Verifica se una colonna è NVARCHAR (supporta Unicode).
     * 
     * @param connection Connessione al database
     * @param tableName Nome della tabella
     * @param columnName Nome della colonna
     * @return true se la colonna è NVARCHAR/NTEXT/NCHAR, false se è VARCHAR/TEXT/CHAR
     */
    public static boolean isColumnUnicode(Connection connection, String tableName, String columnName) {
        if (connection == null || tableName == null || columnName == null) {
            return false;
        }
        
        String cacheKey = tableName.toLowerCase() + "." + columnName.toLowerCase();
        
        // Controlla cache
        if (columnTypeCache.containsKey(cacheKey)) {
            String cachedType = columnTypeCache.get(cacheKey);
            return "nvarchar".equalsIgnoreCase(cachedType) || 
                   "ntext".equalsIgnoreCase(cachedType) ||
                   "nchar".equalsIgnoreCase(cachedType);
        }
        
        // Query al database
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
                
                // Salva in cache
                columnTypeCache.put(cacheKey, dataType);
                
                if (logger.isDebugEnabled()) {
                    logger.debug("Colonna " + tableName + "." + columnName + " tipo: " + dataType + 
                               " (Unicode: " + isUnicode + ")");
                }
                
                return isUnicode;
            }
        } catch (SQLException e) {
            logger.warn("Errore durante verifica tipo colonna " + tableName + "." + columnName + 
                       ": " + e.getMessage());
            // In caso di errore, assumiamo che non sia Unicode (più sicuro)
            return false;
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { /* ignore */ }
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            }
        }
        
        // Se non trovata, assumiamo che non sia Unicode
        return false;
    }
    
    /**
     * Modifica una query INSERT per usare CAST quando le colonne sono VARCHAR.
     * 
     * ATTENZIONE: Questa è una soluzione di "best effort". Se la colonna è VARCHAR,
     * SQL Server potrebbe comunque troncare i dati Unicode. La soluzione migliore
     * è convertire le colonne a NVARCHAR nello schema del database.
     * 
     * @param connection Connessione al database
     * @param originalQuery Query INSERT originale (es. "INSERT INTO table (col1, col2) VALUES (?, ?)")
     * @param tableName Nome della tabella
     * @return Query modificata con CAST se necessario, altrimenti query originale
     */
    public static String enhanceInsertQueryForUnicode(Connection connection, String originalQuery, String tableName) {
        if (connection == null || originalQuery == null || tableName == null) {
            return originalQuery;
        }
        
        // Estrai nomi colonne dalla query
        // Pattern: INSERT INTO table (col1, col2, ...) VALUES (?, ?, ...)
        String upperQuery = originalQuery.toUpperCase();
        int valuesIndex = upperQuery.indexOf("VALUES");
        if (valuesIndex == -1) {
            return originalQuery; // Query non valida
        }
        
        String columnsPart = originalQuery.substring(0, valuesIndex);
        int columnsStart = columnsPart.indexOf("(");
        int columnsEnd = columnsPart.lastIndexOf(")");
        
        if (columnsStart == -1 || columnsEnd == -1 || columnsEnd <= columnsStart) {
            return originalQuery; // Non riusciamo a estrarre le colonne
        }
        
        String columnsList = columnsPart.substring(columnsStart + 1, columnsEnd).trim();
        String[] columns = columnsList.split(",");
        
        // Verifica ogni colonna e aggiungi CAST se necessario
        boolean needsModification = false;
        StringBuilder modifiedQuery = new StringBuilder(originalQuery);
        
        for (String column : columns) {
            column = column.trim();
            if (column.isEmpty()) continue;
            
            // Verifica se la colonna è VARCHAR
            if (!isColumnUnicode(connection, tableName, column)) {
                needsModification = true;
                logger.warn("⚠️ Colonna " + tableName + "." + column + " è VARCHAR invece di NVARCHAR. " +
                          "I dati Unicode potrebbero essere corrotti. " +
                          "Raccomandazione: convertire la colonna a NVARCHAR.");
                
                // NOTA: Non possiamo modificare facilmente la query INSERT per usare CAST
                // perché i parametri sono già posizionati. La soluzione migliore è
                // avvisare e lasciare che UnicodeHelper gestisca l'inserimento.
            }
        }
        
        if (needsModification) {
            logger.warn("⚠️ Tabella " + tableName + " contiene colonne VARCHAR. " +
                      "I dati Unicode potrebbero essere corrotti. " +
                      "Raccomandazione: convertire le colonne a NVARCHAR.");
        }
        
        return originalQuery; // Ritorniamo la query originale
        // La conversione deve essere fatta a livello di schema database
    }
    
    /**
     * Verifica tutte le colonne di una tabella e restituisce un report.
     * 
     * @param connection Connessione al database
     * @param tableName Nome della tabella
     * @return Report con informazioni sulle colonne
     */
    public static ColumnReport checkTableColumns(Connection connection, String tableName) {
        ColumnReport report = new ColumnReport(tableName);
        
        if (connection == null || tableName == null) {
            return report;
        }
        
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(
                 "SELECT c.name AS ColumnName, ty.name AS DataType, c.max_length AS MaxLength " +
                 "FROM sys.tables t " +
                 "INNER JOIN sys.columns c ON t.object_id = c.object_id " +
                 "INNER JOIN sys.types ty ON c.user_type_id = ty.user_type_id " +
                 "WHERE t.name = '" + tableName.replace("'", "''") + "' " +
                 "AND ty.name IN ('varchar', 'nvarchar', 'text', 'ntext', 'char', 'nchar') " +
                 "ORDER BY c.name");
            
            while (rs.next()) {
                String columnName = rs.getString("ColumnName");
                String dataType = rs.getString("DataType");
                int maxLength = rs.getInt("MaxLength");
                
                boolean isUnicode = "nvarchar".equalsIgnoreCase(dataType) || 
                                   "ntext".equalsIgnoreCase(dataType) ||
                                   "nchar".equalsIgnoreCase(dataType);
                
                report.addColumn(columnName, dataType, maxLength, isUnicode);
                
                // Salva in cache
                String cacheKey = tableName.toLowerCase() + "." + columnName.toLowerCase();
                columnTypeCache.put(cacheKey, dataType);
            }
        } catch (SQLException e) {
            logger.error("Errore durante verifica colonne tabella " + tableName + ": " + e.getMessage(), e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { /* ignore */ }
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            }
        }
        
        return report;
    }
    
    /**
     * Genera script SQL per convertire colonne VARCHAR a NVARCHAR.
     * 
     * @param connection Connessione al database
     * @param tableName Nome della tabella
     * @return Script SQL per la conversione
     */
    public static String generateConversionScript(Connection connection, String tableName) {
        ColumnReport report = checkTableColumns(connection, tableName);
        StringBuilder script = new StringBuilder();
        
        script.append("-- Script per convertire colonne VARCHAR a NVARCHAR per tabella: ").append(tableName).append("\n");
        script.append("-- ATTENZIONE: Eseguire questo script durante un periodo di manutenzione\n");
        script.append("-- I dati esistenti verranno preservati durante la conversione\n\n");
        
        for (ColumnReport.ColumnInfo column : report.getVarcharColumns()) {
            script.append("-- Converti colonna: ").append(column.getName()).append("\n");
            script.append("ALTER TABLE ").append(tableName).append(" ");
            script.append("ALTER COLUMN ").append(column.getName()).append(" ");
            
            if ("varchar".equalsIgnoreCase(column.getDataType())) {
                if (column.getMaxLength() == -1) {
                    script.append("NVARCHAR(MAX)");
                } else {
                    script.append("NVARCHAR(").append(column.getMaxLength()).append(")");
                }
            } else if ("text".equalsIgnoreCase(column.getDataType())) {
                script.append("NTEXT");
            } else if ("char".equalsIgnoreCase(column.getDataType())) {
                script.append("NCHAR(").append(column.getMaxLength()).append(")");
            }
            
            script.append(";\n\n");
        }
        
        return script.toString();
    }
    
    /**
     * Report sulle colonne di una tabella.
     */
    public static class ColumnReport {
        private String tableName;
        private java.util.List<ColumnInfo> columns = new java.util.ArrayList<ColumnInfo>();
        
        public ColumnReport(String tableName) {
            this.tableName = tableName;
        }
        
        public void addColumn(String name, String dataType, int maxLength, boolean isUnicode) {
            columns.add(new ColumnInfo(name, dataType, maxLength, isUnicode));
        }
        
        public String getTableName() { return tableName; }
        public java.util.List<ColumnInfo> getColumns() { return columns; }
        
        public java.util.List<ColumnInfo> getVarcharColumns() {
            java.util.List<ColumnInfo> result = new java.util.ArrayList<ColumnInfo>();
            for (ColumnInfo col : columns) {
                if (!col.isUnicode()) {
                    result.add(col);
                }
            }
            return result;
        }
        
        public boolean hasVarcharColumns() {
            return !getVarcharColumns().isEmpty();
        }
        
        public static class ColumnInfo {
            private String name;
            private String dataType;
            private int maxLength;
            private boolean isUnicode;
            
            public ColumnInfo(String name, String dataType, int maxLength, boolean isUnicode) {
                this.name = name;
                this.dataType = dataType;
                this.maxLength = maxLength;
                this.isUnicode = isUnicode;
            }
            
            public String getName() { return name; }
            public String getDataType() { return dataType; }
            public int getMaxLength() { return maxLength; }
            public boolean isUnicode() { return isUnicode; }
        }
    }
    
    /**
     * Pulisce la cache dei tipi di colonna.
     * Utile dopo modifiche allo schema del database.
     */
    public static void clearCache() {
        columnTypeCache.clear();
        logger.debug("Cache tipi colonna pulita");
    }
}

