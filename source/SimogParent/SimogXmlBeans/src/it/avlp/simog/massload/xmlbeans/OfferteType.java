/*
 * XML Type:  OfferteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.OfferteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML OfferteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface OfferteType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OfferteType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC7E9E188FA91B663A6AD081E92AB5FEC").resolveHandle("offertetypebf0dtype");
    
    /**
     * Gets the "OFFERTA_MASSIMO" attribute
     */
    java.math.BigDecimal getOFFERTAMASSIMO();
    
    /**
     * Gets (as xml) the "OFFERTA_MASSIMO" attribute
     */
    it.avlp.simog.massload.xmlbeans.PercentualeType xgetOFFERTAMASSIMO();
    
    /**
     * Sets the "OFFERTA_MASSIMO" attribute
     */
    void setOFFERTAMASSIMO(java.math.BigDecimal offertamassimo);
    
    /**
     * Sets (as xml) the "OFFERTA_MASSIMO" attribute
     */
    void xsetOFFERTAMASSIMO(it.avlp.simog.massload.xmlbeans.PercentualeType offertamassimo);
    
    /**
     * Gets the "OFFERTA_MINIMA" attribute
     */
    java.math.BigDecimal getOFFERTAMINIMA();
    
    /**
     * Gets (as xml) the "OFFERTA_MINIMA" attribute
     */
    it.avlp.simog.massload.xmlbeans.PercentualeType xgetOFFERTAMINIMA();
    
    /**
     * Sets the "OFFERTA_MINIMA" attribute
     */
    void setOFFERTAMINIMA(java.math.BigDecimal offertaminima);
    
    /**
     * Sets (as xml) the "OFFERTA_MINIMA" attribute
     */
    void xsetOFFERTAMINIMA(it.avlp.simog.massload.xmlbeans.PercentualeType offertaminima);
    
    /**
     * Gets the "VAL_SOGLIA_ANOMALIA" attribute
     */
    java.math.BigDecimal getVALSOGLIAANOMALIA();
    
    /**
     * Gets (as xml) the "VAL_SOGLIA_ANOMALIA" attribute
     */
    it.avlp.simog.massload.xmlbeans.PercentualeType xgetVALSOGLIAANOMALIA();
    
    /**
     * Sets the "VAL_SOGLIA_ANOMALIA" attribute
     */
    void setVALSOGLIAANOMALIA(java.math.BigDecimal valsogliaanomalia);
    
    /**
     * Sets (as xml) the "VAL_SOGLIA_ANOMALIA" attribute
     */
    void xsetVALSOGLIAANOMALIA(it.avlp.simog.massload.xmlbeans.PercentualeType valsogliaanomalia);
    
    /**
     * Gets the "NUM_OFFERTE_ESCLUSE" attribute
     */
    int getNUMOFFERTEESCLUSE();
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_ESCLUSE" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMOFFERTEESCLUSE();
    
    /**
     * Sets the "NUM_OFFERTE_ESCLUSE" attribute
     */
    void setNUMOFFERTEESCLUSE(int numofferteescluse);
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_ESCLUSE" attribute
     */
    void xsetNUMOFFERTEESCLUSE(it.avlp.simog.massload.xmlbeans.InteroType numofferteescluse);
    
    /**
     * Gets the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    int getNUMOFFERTEFUORISOGLIA();
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMOFFERTEFUORISOGLIA();
    
    /**
     * Sets the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    void setNUMOFFERTEFUORISOGLIA(int numoffertefuorisoglia);
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    void xsetNUMOFFERTEFUORISOGLIA(it.avlp.simog.massload.xmlbeans.InteroType numoffertefuorisoglia);
    
    /**
     * Gets the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    int getNUMIMPESCLUSEINSUFGIUSTIFICAZIONI();
    
    /**
     * Gets (as xml) the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType xgetNUMIMPESCLUSEINSUFGIUSTIFICAZIONI();
    
    /**
     * Sets the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    void setNUMIMPESCLUSEINSUFGIUSTIFICAZIONI(int numimpescluseinsufgiustificazioni);
    
    /**
     * Sets (as xml) the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    void xsetNUMIMPESCLUSEINSUFGIUSTIFICAZIONI(it.avlp.simog.massload.xmlbeans.InteroType numimpescluseinsufgiustificazioni);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.OfferteType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.OfferteType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.OfferteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
