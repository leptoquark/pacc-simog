/*
 * XML Type:  ContenziosoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ContenziosoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML ContenziosoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface ContenziosoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ContenziosoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9EB3FE6CEAF33BF01AA03B1C0DCBAC0C").resolveHandle("contenziosotypeb6c1type");
    
    /**
     * Gets the "CONTENZIOSO_GARA" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getCONTENZIOSOGARA();
    
    /**
     * Gets (as xml) the "CONTENZIOSO_GARA" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetCONTENZIOSOGARA();
    
    /**
     * Sets the "CONTENZIOSO_GARA" attribute
     */
    void setCONTENZIOSOGARA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum contenziosogara);
    
    /**
     * Sets (as xml) the "CONTENZIOSO_GARA" attribute
     */
    void xsetCONTENZIOSOGARA(it.avlp.simog.massload.xmlbeans.FlagSNType contenziosogara);
    
    /**
     * Gets the "MOTIVAZIONE" attribute
     */
    java.lang.String getMOTIVAZIONE();
    
    /**
     * Gets (as xml) the "MOTIVAZIONE" attribute
     */
    it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE xgetMOTIVAZIONE();
    
    /**
     * True if has "MOTIVAZIONE" attribute
     */
    boolean isSetMOTIVAZIONE();
    
    /**
     * Sets the "MOTIVAZIONE" attribute
     */
    void setMOTIVAZIONE(java.lang.String motivazione);
    
    /**
     * Sets (as xml) the "MOTIVAZIONE" attribute
     */
    void xsetMOTIVAZIONE(it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE motivazione);
    
    /**
     * Unsets the "MOTIVAZIONE" attribute
     */
    void unsetMOTIVAZIONE();
    
    /**
     * Gets the "CODICE_FISCALE_DITTA" attribute
     */
    java.lang.String getCODICEFISCALEDITTA();
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_DITTA" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEDITTA();
    
    /**
     * Sets the "CODICE_FISCALE_DITTA" attribute
     */
    void setCODICEFISCALEDITTA(java.lang.String codicefiscaleditta);
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_DITTA" attribute
     */
    void xsetCODICEFISCALEDITTA(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleditta);
    
    /**
     * An XML MOTIVAZIONE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ContenziosoType$MOTIVAZIONE.
     */
    public interface MOTIVAZIONE extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MOTIVAZIONE.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9EB3FE6CEAF33BF01AA03B1C0DCBAC0C").resolveHandle("motivazione696eattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE newInstance() {
              return (it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ContenziosoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ContenziosoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
