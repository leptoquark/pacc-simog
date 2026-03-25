/*
 * XML Type:  AnomScheda_AType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AnomSchedaAType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML AnomScheda_AType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface AnomSchedaAType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AnomSchedaAType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("anomschedaatypee4a9type");
    
    /**
     * Gets array of all "Anomalia" elements
     */
    it.avlp.simog.massload.xmlbeans.AnomaliaType[] getAnomaliaArray();
    
    /**
     * Gets ith "Anomalia" element
     */
    it.avlp.simog.massload.xmlbeans.AnomaliaType getAnomaliaArray(int i);
    
    /**
     * Returns number of "Anomalia" element
     */
    int sizeOfAnomaliaArray();
    
    /**
     * Sets array of all "Anomalia" element
     */
    void setAnomaliaArray(it.avlp.simog.massload.xmlbeans.AnomaliaType[] anomaliaArray);
    
    /**
     * Sets ith "Anomalia" element
     */
    void setAnomaliaArray(int i, it.avlp.simog.massload.xmlbeans.AnomaliaType anomalia);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Anomalia" element
     */
    it.avlp.simog.massload.xmlbeans.AnomaliaType insertNewAnomalia(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Anomalia" element
     */
    it.avlp.simog.massload.xmlbeans.AnomaliaType addNewAnomalia();
    
    /**
     * Removes the ith "Anomalia" element
     */
    void removeAnomalia(int i);
    
    /**
     * Gets array of all "IdScheda" elements
     */
    it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType[] getIdSchedaArray();
    
    /**
     * Gets ith "IdScheda" element
     */
    it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType getIdSchedaArray(int i);
    
    /**
     * Returns number of "IdScheda" element
     */
    int sizeOfIdSchedaArray();
    
    /**
     * Sets array of all "IdScheda" element
     */
    void setIdSchedaArray(it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType[] idSchedaArray);
    
    /**
     * Sets ith "IdScheda" element
     */
    void setIdSchedaArray(int i, it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType idScheda);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "IdScheda" element
     */
    it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType insertNewIdScheda(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "IdScheda" element
     */
    it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType addNewIdScheda();
    
    /**
     * Removes the ith "IdScheda" element
     */
    void removeIdScheda(int i);
    
    /**
     * Gets the "CUPLOTTO" element
     */
    it.avlp.simog.massload.xmlbeans.CUPLOTTOType getCUPLOTTO();
    
    /**
     * True if has "CUPLOTTO" element
     */
    boolean isSetCUPLOTTO();
    
    /**
     * Sets the "CUPLOTTO" element
     */
    void setCUPLOTTO(it.avlp.simog.massload.xmlbeans.CUPLOTTOType cuplotto);
    
    /**
     * Appends and returns a new empty "CUPLOTTO" element
     */
    it.avlp.simog.massload.xmlbeans.CUPLOTTOType addNewCUPLOTTO();
    
    /**
     * Unsets the "CUPLOTTO" element
     */
    void unsetCUPLOTTO();
    
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
     * Gets the "PROGRESSIVO" attribute
     */
    int getPROGRESSIVO();
    
    /**
     * Gets (as xml) the "PROGRESSIVO" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetPROGRESSIVO();
    
    /**
     * Sets the "PROGRESSIVO" attribute
     */
    void setPROGRESSIVO(int progressivo);
    
    /**
     * Sets (as xml) the "PROGRESSIVO" attribute
     */
    void xsetPROGRESSIVO(it.avlp.simog.massload.xmlbeans.InteroType progressivo);
    
    /**
     * Gets the "CUI" attribute
     */
    java.lang.String getCUI();
    
    /**
     * Gets (as xml) the "CUI" attribute
     */
    it.avlp.simog.massload.xmlbeans.CuiType xgetCUI();
    
    /**
     * True if has "CUI" attribute
     */
    boolean isSetCUI();
    
    /**
     * Sets the "CUI" attribute
     */
    void setCUI(java.lang.String cui);
    
    /**
     * Sets (as xml) the "CUI" attribute
     */
    void xsetCUI(it.avlp.simog.massload.xmlbeans.CuiType cui);
    
    /**
     * Unsets the "CUI" attribute
     */
    void unsetCUI();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AnomSchedaAType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AnomSchedaAType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
