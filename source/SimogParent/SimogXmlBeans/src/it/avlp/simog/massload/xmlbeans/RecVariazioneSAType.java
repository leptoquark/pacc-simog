/*
 * XML Type:  RecVariazioneSAType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecVariazioneSAType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML RecVariazioneSAType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface RecVariazioneSAType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RecVariazioneSAType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("recvariazionesatyped476type");
    
    /**
     * Gets the "ID_GARA" attribute
     */
    long getIDGARA();
    
    /**
     * Gets (as xml) the "ID_GARA" attribute
     */
    it.avlp.simog.massload.xmlbeans.LongType xgetIDGARA();
    
    /**
     * Sets the "ID_GARA" attribute
     */
    void setIDGARA(long idgara);
    
    /**
     * Sets (as xml) the "ID_GARA" attribute
     */
    void xsetIDGARA(it.avlp.simog.massload.xmlbeans.LongType idgara);
    
    /**
     * Gets the "MOTIVO" attribute
     */
    java.lang.String getMOTIVO();
    
    /**
     * Gets (as xml) the "MOTIVO" attribute
     */
    it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType xgetMOTIVO();
    
    /**
     * Sets the "MOTIVO" attribute
     */
    void setMOTIVO(java.lang.String motivo);
    
    /**
     * Sets (as xml) the "MOTIVO" attribute
     */
    void xsetMOTIVO(it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType motivo);
    
    /**
     * Gets the "CF_AMMINISTRAZIONE" attribute
     */
    java.lang.String getCFAMMINISTRAZIONE();
    
    /**
     * Gets (as xml) the "CF_AMMINISTRAZIONE" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFAMMINISTRAZIONE();
    
    /**
     * Sets the "CF_AMMINISTRAZIONE" attribute
     */
    void setCFAMMINISTRAZIONE(java.lang.String cfamministrazione);
    
    /**
     * Sets (as xml) the "CF_AMMINISTRAZIONE" attribute
     */
    void xsetCFAMMINISTRAZIONE(it.avlp.simog.massload.xmlbeans.CodFiscType cfamministrazione);
    
    /**
     * Gets the "ID_CENTRO_COSTO" attribute
     */
    java.lang.String getIDCENTROCOSTO();
    
    /**
     * Gets (as xml) the "ID_CENTRO_COSTO" attribute
     */
    it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO xgetIDCENTROCOSTO();
    
    /**
     * Sets the "ID_CENTRO_COSTO" attribute
     */
    void setIDCENTROCOSTO(java.lang.String idcentrocosto);
    
    /**
     * Sets (as xml) the "ID_CENTRO_COSTO" attribute
     */
    void xsetIDCENTROCOSTO(it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO idcentrocosto);
    
    /**
     * An XML ID_CENTRO_COSTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RecVariazioneSAType$IDCENTROCOSTO.
     */
    public interface IDCENTROCOSTO extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(IDCENTROCOSTO.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("idcentrocosto39daattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO newInstance() {
              return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.RecVariazioneSAType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
