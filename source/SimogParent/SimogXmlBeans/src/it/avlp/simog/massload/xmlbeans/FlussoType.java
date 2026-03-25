/*
 * XML Type:  FlussoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FlussoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML FlussoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface FlussoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FlussoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("flussotypea472type");
    
    /**
     * Gets the "DATA_ELABORAZIONE" attribute
     */
    java.util.Calendar getDATAELABORAZIONE();
    
    /**
     * Gets (as xml) the "DATA_ELABORAZIONE" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAELABORAZIONE();
    
    /**
     * Sets the "DATA_ELABORAZIONE" attribute
     */
    void setDATAELABORAZIONE(java.util.Calendar dataelaborazione);
    
    /**
     * Sets (as xml) the "DATA_ELABORAZIONE" attribute
     */
    void xsetDATAELABORAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType dataelaborazione);
    
    /**
     * Gets the "NUM_ELABORATE" attribute
     */
    int getNUMELABORATE();
    
    /**
     * Gets (as xml) the "NUM_ELABORATE" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMELABORATE();
    
    /**
     * Sets the "NUM_ELABORATE" attribute
     */
    void setNUMELABORATE(int numelaborate);
    
    /**
     * Sets (as xml) the "NUM_ELABORATE" attribute
     */
    void xsetNUMELABORATE(it.avlp.simog.massload.xmlbeans.InteroType numelaborate);
    
    /**
     * Gets the "NUM_ERRORE" attribute
     */
    int getNUMERRORE();
    
    /**
     * Gets (as xml) the "NUM_ERRORE" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMERRORE();
    
    /**
     * Sets the "NUM_ERRORE" attribute
     */
    void setNUMERRORE(int numerrore);
    
    /**
     * Sets (as xml) the "NUM_ERRORE" attribute
     */
    void xsetNUMERRORE(it.avlp.simog.massload.xmlbeans.InteroType numerrore);
    
    /**
     * Gets the "NUM_WARNING" attribute
     */
    int getNUMWARNING();
    
    /**
     * Gets (as xml) the "NUM_WARNING" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMWARNING();
    
    /**
     * Sets the "NUM_WARNING" attribute
     */
    void setNUMWARNING(int numwarning);
    
    /**
     * Sets (as xml) the "NUM_WARNING" attribute
     */
    void xsetNUMWARNING(it.avlp.simog.massload.xmlbeans.InteroType numwarning);
    
    /**
     * Gets the "NUM_CARICATE" attribute
     */
    int getNUMCARICATE();
    
    /**
     * Gets (as xml) the "NUM_CARICATE" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMCARICATE();
    
    /**
     * Sets the "NUM_CARICATE" attribute
     */
    void setNUMCARICATE(int numcaricate);
    
    /**
     * Sets (as xml) the "NUM_CARICATE" attribute
     */
    void xsetNUMCARICATE(it.avlp.simog.massload.xmlbeans.InteroType numcaricate);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.FlussoType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FlussoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FlussoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
