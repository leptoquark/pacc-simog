/*
 * XML Type:  RecIdSchedaElimType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML RecIdSchedaElimType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface RecIdSchedaElimType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RecIdSchedaElimType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("recidschedaelimtypec0a0type");
    
    /**
     * Gets the "CIG" attribute
     */
    java.lang.String getCIG();
    
    /**
     * Gets (as xml) the "CIG" attribute
     */
    it.avlp.simog.massload.xmlbeans.CigType xgetCIG();
    
    /**
     * Sets the "CIG" attribute
     */
    void setCIG(java.lang.String cig);
    
    /**
     * Sets (as xml) the "CIG" attribute
     */
    void xsetCIG(it.avlp.simog.massload.xmlbeans.CigType cig);
    
    /**
     * Gets the "CUI" attribute
     */
    java.lang.String getCUI();
    
    /**
     * Gets (as xml) the "CUI" attribute
     */
    it.avlp.simog.massload.xmlbeans.CuiType xgetCUI();
    
    /**
     * Sets the "CUI" attribute
     */
    void setCUI(java.lang.String cui);
    
    /**
     * Sets (as xml) the "CUI" attribute
     */
    void xsetCUI(it.avlp.simog.massload.xmlbeans.CuiType cui);
    
    /**
     * Gets the "SCHEDA" attribute
     */
    it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum getSCHEDA();
    
    /**
     * Gets (as xml) the "SCHEDA" attribute
     */
    it.avlp.simog.massload.xmlbeans.TipiSchedeType xgetSCHEDA();
    
    /**
     * True if has "SCHEDA" attribute
     */
    boolean isSetSCHEDA();
    
    /**
     * Sets the "SCHEDA" attribute
     */
    void setSCHEDA(it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum scheda);
    
    /**
     * Sets (as xml) the "SCHEDA" attribute
     */
    void xsetSCHEDA(it.avlp.simog.massload.xmlbeans.TipiSchedeType scheda);
    
    /**
     * Unsets the "SCHEDA" attribute
     */
    void unsetSCHEDA();
    
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
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
