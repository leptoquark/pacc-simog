/*
 * XML Type:  SubappaltoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SubappaltoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML SubappaltoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface SubappaltoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SubappaltoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD95CF1D1A49336AD8BE7BE717440765C").resolveHandle("subappaltotype7079type");
    
    /**
     * Gets array of all "Subappaltatore" elements
     */
    it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType[] getSubappaltatoreArray();
    
    /**
     * Gets ith "Subappaltatore" element
     */
    it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType getSubappaltatoreArray(int i);
    
    /**
     * Returns number of "Subappaltatore" element
     */
    int sizeOfSubappaltatoreArray();
    
    /**
     * Sets array of all "Subappaltatore" element
     */
    void setSubappaltatoreArray(it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType[] subappaltatoreArray);
    
    /**
     * Sets ith "Subappaltatore" element
     */
    void setSubappaltatoreArray(int i, it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType subappaltatore);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Subappaltatore" element
     */
    it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType insertNewSubappaltatore(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Subappaltatore" element
     */
    it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType addNewSubappaltatore();
    
    /**
     * Removes the ith "Subappaltatore" element
     */
    void removeSubappaltatore(int i);
    
    /**
     * Gets the "CF_DITTA" attribute
     */
    java.lang.String getCFDITTA();
    
    /**
     * Gets (as xml) the "CF_DITTA" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFDITTA();
    
    /**
     * Sets the "CF_DITTA" attribute
     */
    void setCFDITTA(java.lang.String cfditta);
    
    /**
     * Sets (as xml) the "CF_DITTA" attribute
     */
    void xsetCFDITTA(it.avlp.simog.massload.xmlbeans.CodFiscType cfditta);
    
    /**
     * Gets the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGDITTASUBESTERA();
    
    /**
     * Gets (as xml) the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGDITTASUBESTERA();
    
    /**
     * True if has "FLAG_DITTA_SUB_ESTERA" attribute
     */
    boolean isSetFLAGDITTASUBESTERA();
    
    /**
     * Sets the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    void setFLAGDITTASUBESTERA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagdittasubestera);
    
    /**
     * Sets (as xml) the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    void xsetFLAGDITTASUBESTERA(it.avlp.simog.massload.xmlbeans.FlagSNType flagdittasubestera);
    
    /**
     * Unsets the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    void unsetFLAGDITTASUBESTERA();
    
    /**
     * Gets the "DATA_AUTORIZZAZIONE" attribute
     */
    java.util.Calendar getDATAAUTORIZZAZIONE();
    
    /**
     * Gets (as xml) the "DATA_AUTORIZZAZIONE" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAAUTORIZZAZIONE();
    
    /**
     * True if has "DATA_AUTORIZZAZIONE" attribute
     */
    boolean isSetDATAAUTORIZZAZIONE();
    
    /**
     * Sets the "DATA_AUTORIZZAZIONE" attribute
     */
    void setDATAAUTORIZZAZIONE(java.util.Calendar dataautorizzazione);
    
    /**
     * Sets (as xml) the "DATA_AUTORIZZAZIONE" attribute
     */
    void xsetDATAAUTORIZZAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType dataautorizzazione);
    
    /**
     * Unsets the "DATA_AUTORIZZAZIONE" attribute
     */
    void unsetDATAAUTORIZZAZIONE();
    
    /**
     * Gets the "OGGETTO_SUBAPPALTO" attribute
     */
    java.lang.String getOGGETTOSUBAPPALTO();
    
    /**
     * Gets (as xml) the "OGGETTO_SUBAPPALTO" attribute
     */
    it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO xgetOGGETTOSUBAPPALTO();
    
    /**
     * True if has "OGGETTO_SUBAPPALTO" attribute
     */
    boolean isSetOGGETTOSUBAPPALTO();
    
    /**
     * Sets the "OGGETTO_SUBAPPALTO" attribute
     */
    void setOGGETTOSUBAPPALTO(java.lang.String oggettosubappalto);
    
    /**
     * Sets (as xml) the "OGGETTO_SUBAPPALTO" attribute
     */
    void xsetOGGETTOSUBAPPALTO(it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO oggettosubappalto);
    
    /**
     * Unsets the "OGGETTO_SUBAPPALTO" attribute
     */
    void unsetOGGETTOSUBAPPALTO();
    
    /**
     * Gets the "IMPORTO_PRESUNTO" attribute
     */
    java.math.BigDecimal getIMPORTOPRESUNTO();
    
    /**
     * Gets (as xml) the "IMPORTO_PRESUNTO" attribute
     */
    it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOPRESUNTO();
    
    /**
     * Sets the "IMPORTO_PRESUNTO" attribute
     */
    void setIMPORTOPRESUNTO(java.math.BigDecimal importopresunto);
    
    /**
     * Sets (as xml) the "IMPORTO_PRESUNTO" attribute
     */
    void xsetIMPORTOPRESUNTO(it.avlp.simog.massload.xmlbeans.ImportoType importopresunto);
    
    /**
     * Gets the "IMPORTO_EFFETTIVO" attribute
     */
    java.math.BigDecimal getIMPORTOEFFETTIVO();
    
    /**
     * Gets (as xml) the "IMPORTO_EFFETTIVO" attribute
     */
    it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOEFFETTIVO();
    
    /**
     * True if has "IMPORTO_EFFETTIVO" attribute
     */
    boolean isSetIMPORTOEFFETTIVO();
    
    /**
     * Sets the "IMPORTO_EFFETTIVO" attribute
     */
    void setIMPORTOEFFETTIVO(java.math.BigDecimal importoeffettivo);
    
    /**
     * Sets (as xml) the "IMPORTO_EFFETTIVO" attribute
     */
    void xsetIMPORTOEFFETTIVO(it.avlp.simog.massload.xmlbeans.ImportoType importoeffettivo);
    
    /**
     * Unsets the "IMPORTO_EFFETTIVO" attribute
     */
    void unsetIMPORTOEFFETTIVO();
    
    /**
     * Gets the "ID_CATEGORIA" attribute
     */
    java.lang.String getIDCATEGORIA();
    
    /**
     * Gets (as xml) the "ID_CATEGORIA" attribute
     */
    it.avlp.simog.massload.xmlbeans.CategoriaType xgetIDCATEGORIA();
    
    /**
     * True if has "ID_CATEGORIA" attribute
     */
    boolean isSetIDCATEGORIA();
    
    /**
     * Sets the "ID_CATEGORIA" attribute
     */
    void setIDCATEGORIA(java.lang.String idcategoria);
    
    /**
     * Sets (as xml) the "ID_CATEGORIA" attribute
     */
    void xsetIDCATEGORIA(it.avlp.simog.massload.xmlbeans.CategoriaType idcategoria);
    
    /**
     * Unsets the "ID_CATEGORIA" attribute
     */
    void unsetIDCATEGORIA();
    
    /**
     * Gets the "ID_CPV" attribute
     */
    java.lang.String getIDCPV();
    
    /**
     * Gets (as xml) the "ID_CPV" attribute
     */
    it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV xgetIDCPV();
    
    /**
     * Sets the "ID_CPV" attribute
     */
    void setIDCPV(java.lang.String idcpv);
    
    /**
     * Sets (as xml) the "ID_CPV" attribute
     */
    void xsetIDCPV(it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV idcpv);
    
    /**
     * Gets the "ID_SCHEDA_LOCALE" attribute
     */
    java.lang.String getIDSCHEDALOCALE();
    
    /**
     * Gets (as xml) the "ID_SCHEDA_LOCALE" attribute
     */
    it.avlp.simog.massload.xmlbeans.IdSchedaType xgetIDSCHEDALOCALE();
    
    /**
     * True if has "ID_SCHEDA_LOCALE" attribute
     */
    boolean isSetIDSCHEDALOCALE();
    
    /**
     * Sets the "ID_SCHEDA_LOCALE" attribute
     */
    void setIDSCHEDALOCALE(java.lang.String idschedalocale);
    
    /**
     * Sets (as xml) the "ID_SCHEDA_LOCALE" attribute
     */
    void xsetIDSCHEDALOCALE(it.avlp.simog.massload.xmlbeans.IdSchedaType idschedalocale);
    
    /**
     * Unsets the "ID_SCHEDA_LOCALE" attribute
     */
    void unsetIDSCHEDALOCALE();
    
    /**
     * Gets the "ID_SCHEDA_SIMOG" attribute
     */
    java.lang.String getIDSCHEDASIMOG();
    
    /**
     * Gets (as xml) the "ID_SCHEDA_SIMOG" attribute
     */
    it.avlp.simog.massload.xmlbeans.IdSchedaType xgetIDSCHEDASIMOG();
    
    /**
     * True if has "ID_SCHEDA_SIMOG" attribute
     */
    boolean isSetIDSCHEDASIMOG();
    
    /**
     * Sets the "ID_SCHEDA_SIMOG" attribute
     */
    void setIDSCHEDASIMOG(java.lang.String idschedasimog);
    
    /**
     * Sets (as xml) the "ID_SCHEDA_SIMOG" attribute
     */
    void xsetIDSCHEDASIMOG(it.avlp.simog.massload.xmlbeans.IdSchedaType idschedasimog);
    
    /**
     * Unsets the "ID_SCHEDA_SIMOG" attribute
     */
    void unsetIDSCHEDASIMOG();
    
    /**
     * Gets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    java.lang.String getCODICEFISCALEAGGIUDICATARIO();
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEAGGIUDICATARIO();
    
    /**
     * True if has "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    boolean isSetCODICEFISCALEAGGIUDICATARIO();
    
    /**
     * Sets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    void setCODICEFISCALEAGGIUDICATARIO(java.lang.String codicefiscaleaggiudicatario);
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    void xsetCODICEFISCALEAGGIUDICATARIO(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleaggiudicatario);
    
    /**
     * Unsets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    void unsetCODICEFISCALEAGGIUDICATARIO();
    
    /**
     * Gets the "CODICE_STATO" attribute
     */
    java.lang.String getCODICESTATO();
    
    /**
     * Gets (as xml) the "CODICE_STATO" attribute
     */
    it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATO();
    
    /**
     * True if has "CODICE_STATO" attribute
     */
    boolean isSetCODICESTATO();
    
    /**
     * Sets the "CODICE_STATO" attribute
     */
    void setCODICESTATO(java.lang.String codicestato);
    
    /**
     * Sets (as xml) the "CODICE_STATO" attribute
     */
    void xsetCODICESTATO(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestato);
    
    /**
     * Unsets the "CODICE_STATO" attribute
     */
    void unsetCODICESTATO();
    
    /**
     * Gets the "ID_STATO_SCHEDA" attribute
     */
    it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum getIDSTATOSCHEDA();
    
    /**
     * Gets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    it.avlp.simog.massload.xmlbeans.StatoSchedaType xgetIDSTATOSCHEDA();
    
    /**
     * True if has "ID_STATO_SCHEDA" attribute
     */
    boolean isSetIDSTATOSCHEDA();
    
    /**
     * Sets the "ID_STATO_SCHEDA" attribute
     */
    void setIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum idstatoscheda);
    
    /**
     * Sets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    void xsetIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType idstatoscheda);
    
    /**
     * Unsets the "ID_STATO_SCHEDA" attribute
     */
    void unsetIDSTATOSCHEDA();
    
    /**
     * An XML OGGETTO_SUBAPPALTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.SubappaltoType$OGGETTOSUBAPPALTO.
     */
    public interface OGGETTOSUBAPPALTO extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OGGETTOSUBAPPALTO.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD95CF1D1A49336AD8BE7BE717440765C").resolveHandle("oggettosubappalto6d1cattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO newInstance() {
              return (it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML ID_CPV(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.SubappaltoType$IDCPV.
     */
    public interface IDCPV extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(IDCPV.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD95CF1D1A49336AD8BE7BE717440765C").resolveHandle("idcpvf3a4attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV newInstance() {
              return (it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.SubappaltoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
