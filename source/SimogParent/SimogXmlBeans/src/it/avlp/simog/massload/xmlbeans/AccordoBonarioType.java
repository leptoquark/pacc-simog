/*
 * XML Type:  AccordoBonarioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AccordoBonarioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML AccordoBonarioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface AccordoBonarioType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AccordoBonarioType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("accordobonariotype915btype");
    
    /**
     * Gets the "DATA_ACCORDO" attribute
     */
    java.util.Calendar getDATAACCORDO();
    
    /**
     * Gets (as xml) the "DATA_ACCORDO" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAACCORDO();
    
    /**
     * Sets the "DATA_ACCORDO" attribute
     */
    void setDATAACCORDO(java.util.Calendar dataaccordo);
    
    /**
     * Sets (as xml) the "DATA_ACCORDO" attribute
     */
    void xsetDATAACCORDO(it.avlp.simog.massload.xmlbeans.DbDateType dataaccordo);
    
    /**
     * Gets the "ONERI_DERIVANTI" attribute
     */
    java.math.BigDecimal getONERIDERIVANTI();
    
    /**
     * Gets (as xml) the "ONERI_DERIVANTI" attribute
     */
    it.avlp.simog.massload.xmlbeans.ImportoType xgetONERIDERIVANTI();
    
    /**
     * True if has "ONERI_DERIVANTI" attribute
     */
    boolean isSetONERIDERIVANTI();
    
    /**
     * Sets the "ONERI_DERIVANTI" attribute
     */
    void setONERIDERIVANTI(java.math.BigDecimal oneriderivanti);
    
    /**
     * Sets (as xml) the "ONERI_DERIVANTI" attribute
     */
    void xsetONERIDERIVANTI(it.avlp.simog.massload.xmlbeans.ImportoType oneriderivanti);
    
    /**
     * Unsets the "ONERI_DERIVANTI" attribute
     */
    void unsetONERIDERIVANTI();
    
    /**
     * Gets the "NUM_RISERVE" attribute
     */
    int getNUMRISERVE();
    
    /**
     * Gets (as xml) the "NUM_RISERVE" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMRISERVE();
    
    /**
     * Sets the "NUM_RISERVE" attribute
     */
    void setNUMRISERVE(int numriserve);
    
    /**
     * Sets (as xml) the "NUM_RISERVE" attribute
     */
    void xsetNUMRISERVE(it.avlp.simog.massload.xmlbeans.InteroType numriserve);
    
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
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AccordoBonarioType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AccordoBonarioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
