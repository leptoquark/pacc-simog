/*
 * XML Type:  DittaAusiliariaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DittaAusiliariaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML DittaAusiliariaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface DittaAusiliariaType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DittaAusiliariaType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("dittaausiliariatypee52ctype");
    
    /**
     * Gets the "FLAG_AVVALIMENTO" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum getFLAGAVVALIMENTO();
    
    /**
     * Gets (as xml) the "FLAG_AVVALIMENTO" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType xgetFLAGAVVALIMENTO();
    
    /**
     * Sets the "FLAG_AVVALIMENTO" attribute
     */
    void setFLAGAVVALIMENTO(it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum flagavvalimento);
    
    /**
     * Sets (as xml) the "FLAG_AVVALIMENTO" attribute
     */
    void xsetFLAGAVVALIMENTO(it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType flagavvalimento);
    
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
     * Gets the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    java.lang.String getCODICESTATOAGGIUDICATARIO();
    
    /**
     * Gets (as xml) the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATOAGGIUDICATARIO();
    
    /**
     * Sets the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    void setCODICESTATOAGGIUDICATARIO(java.lang.String codicestatoaggiudicatario);
    
    /**
     * Sets (as xml) the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    void xsetCODICESTATOAGGIUDICATARIO(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestatoaggiudicatario);
    
    /**
     * Gets the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    java.lang.String getCODICEFISCALEAUSILIARIA();
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEAUSILIARIA();
    
    /**
     * Sets the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    void setCODICEFISCALEAUSILIARIA(java.lang.String codicefiscaleausiliaria);
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    void xsetCODICEFISCALEAUSILIARIA(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleausiliaria);
    
    /**
     * Gets the "CODICE_STATO_AUSILIARIA" attribute
     */
    java.lang.String getCODICESTATOAUSILIARIA();
    
    /**
     * Gets (as xml) the "CODICE_STATO_AUSILIARIA" attribute
     */
    it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATOAUSILIARIA();
    
    /**
     * Sets the "CODICE_STATO_AUSILIARIA" attribute
     */
    void setCODICESTATOAUSILIARIA(java.lang.String codicestatoausiliaria);
    
    /**
     * Sets (as xml) the "CODICE_STATO_AUSILIARIA" attribute
     */
    void xsetCODICESTATOAUSILIARIA(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestatoausiliaria);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DittaAusiliariaType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
