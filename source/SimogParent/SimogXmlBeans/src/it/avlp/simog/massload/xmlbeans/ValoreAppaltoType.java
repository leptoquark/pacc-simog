/*
 * XML Type:  ValoreAppaltoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ValoreAppaltoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML ValoreAppaltoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface ValoreAppaltoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ValoreAppaltoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("valoreappaltotype5da6type");
    
    /**
     * Gets the "VAL_TOTAL" attribute
     */
    java.math.BigDecimal getVALTOTAL();
    
    /**
     * Gets (as xml) the "VAL_TOTAL" attribute
     */
    it.avlp.simog.massload.xmlbeans.ImportoType xgetVALTOTAL();
    
    /**
     * True if has "VAL_TOTAL" attribute
     */
    boolean isSetVALTOTAL();
    
    /**
     * Sets the "VAL_TOTAL" attribute
     */
    void setVALTOTAL(java.math.BigDecimal valtotal);
    
    /**
     * Sets (as xml) the "VAL_TOTAL" attribute
     */
    void xsetVALTOTAL(it.avlp.simog.massload.xmlbeans.ImportoType valtotal);
    
    /**
     * Unsets the "VAL_TOTAL" attribute
     */
    void unsetVALTOTAL();
    
    /**
     * Gets the "VAL_RANGE_TOTAL_LOW" attribute
     */
    java.math.BigDecimal getVALRANGETOTALLOW();
    
    /**
     * Gets (as xml) the "VAL_RANGE_TOTAL_LOW" attribute
     */
    it.avlp.simog.massload.xmlbeans.ImportoType xgetVALRANGETOTALLOW();
    
    /**
     * True if has "VAL_RANGE_TOTAL_LOW" attribute
     */
    boolean isSetVALRANGETOTALLOW();
    
    /**
     * Sets the "VAL_RANGE_TOTAL_LOW" attribute
     */
    void setVALRANGETOTALLOW(java.math.BigDecimal valrangetotallow);
    
    /**
     * Sets (as xml) the "VAL_RANGE_TOTAL_LOW" attribute
     */
    void xsetVALRANGETOTALLOW(it.avlp.simog.massload.xmlbeans.ImportoType valrangetotallow);
    
    /**
     * Unsets the "VAL_RANGE_TOTAL_LOW" attribute
     */
    void unsetVALRANGETOTALLOW();
    
    /**
     * Gets the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    java.math.BigDecimal getVALRANGETOTALHIGH();
    
    /**
     * Gets (as xml) the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    it.avlp.simog.massload.xmlbeans.ImportoType xgetVALRANGETOTALHIGH();
    
    /**
     * True if has "VAL_RANGE_TOTAL_HIGH" attribute
     */
    boolean isSetVALRANGETOTALHIGH();
    
    /**
     * Sets the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    void setVALRANGETOTALHIGH(java.math.BigDecimal valrangetotalhigh);
    
    /**
     * Sets (as xml) the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    void xsetVALRANGETOTALHIGH(it.avlp.simog.massload.xmlbeans.ImportoType valrangetotalhigh);
    
    /**
     * Unsets the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    void unsetVALRANGETOTALHIGH();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ValoreAppaltoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
