/*
 * XML Type:  PosizioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.PosizioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML PosizioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface PosizioneType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(PosizioneType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("posizionetype5662type");
    
    /**
     * Gets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    java.lang.String getCODICEFISCALEAGGIUDICATARIO();
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEAGGIUDICATARIO();
    
    /**
     * Sets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    void setCODICEFISCALEAGGIUDICATARIO(java.lang.String codicefiscaleaggiudicatario);
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    void xsetCODICEFISCALEAGGIUDICATARIO(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleaggiudicatario);
    
    /**
     * Gets the "CODICE_STATO" attribute
     */
    java.lang.String getCODICESTATO();
    
    /**
     * Gets (as xml) the "CODICE_STATO" attribute
     */
    it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATO();
    
    /**
     * Sets the "CODICE_STATO" attribute
     */
    void setCODICESTATO(java.lang.String codicestato);
    
    /**
     * Sets (as xml) the "CODICE_STATO" attribute
     */
    void xsetCODICESTATO(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestato);
    
    /**
     * Gets the "CODICE_INPS" attribute
     */
    java.lang.String getCODICEINPS();
    
    /**
     * Gets (as xml) the "CODICE_INPS" attribute
     */
    it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS xgetCODICEINPS();
    
    /**
     * True if has "CODICE_INPS" attribute
     */
    boolean isSetCODICEINPS();
    
    /**
     * Sets the "CODICE_INPS" attribute
     */
    void setCODICEINPS(java.lang.String codiceinps);
    
    /**
     * Sets (as xml) the "CODICE_INPS" attribute
     */
    void xsetCODICEINPS(it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS codiceinps);
    
    /**
     * Unsets the "CODICE_INPS" attribute
     */
    void unsetCODICEINPS();
    
    /**
     * Gets the "CODICE_INAIL" attribute
     */
    java.lang.String getCODICEINAIL();
    
    /**
     * Gets (as xml) the "CODICE_INAIL" attribute
     */
    it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL xgetCODICEINAIL();
    
    /**
     * True if has "CODICE_INAIL" attribute
     */
    boolean isSetCODICEINAIL();
    
    /**
     * Sets the "CODICE_INAIL" attribute
     */
    void setCODICEINAIL(java.lang.String codiceinail);
    
    /**
     * Sets (as xml) the "CODICE_INAIL" attribute
     */
    void xsetCODICEINAIL(it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL codiceinail);
    
    /**
     * Unsets the "CODICE_INAIL" attribute
     */
    void unsetCODICEINAIL();
    
    /**
     * Gets the "CODICE_CASSA" attribute
     */
    java.lang.String getCODICECASSA();
    
    /**
     * Gets (as xml) the "CODICE_CASSA" attribute
     */
    it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA xgetCODICECASSA();
    
    /**
     * True if has "CODICE_CASSA" attribute
     */
    boolean isSetCODICECASSA();
    
    /**
     * Sets the "CODICE_CASSA" attribute
     */
    void setCODICECASSA(java.lang.String codicecassa);
    
    /**
     * Sets (as xml) the "CODICE_CASSA" attribute
     */
    void xsetCODICECASSA(it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA codicecassa);
    
    /**
     * Unsets the "CODICE_CASSA" attribute
     */
    void unsetCODICECASSA();
    
    /**
     * An XML CODICE_INPS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PosizioneType$CODICEINPS.
     */
    public interface CODICEINPS extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CODICEINPS.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("codiceinpsf96eattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS newInstance() {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML CODICE_INAIL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PosizioneType$CODICEINAIL.
     */
    public interface CODICEINAIL extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CODICEINAIL.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("codiceinail6125attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL newInstance() {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML CODICE_CASSA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PosizioneType$CODICECASSA.
     */
    public interface CODICECASSA extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CODICECASSA.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("codicecassa5809attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA newInstance() {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.PosizioneType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.PosizioneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.PosizioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
