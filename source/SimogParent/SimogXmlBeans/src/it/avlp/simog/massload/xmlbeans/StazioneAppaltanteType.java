/*
 * XML Type:  stazioneAppaltanteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML stazioneAppaltanteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface StazioneAppaltanteType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(StazioneAppaltanteType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB2782A181E49677F55A0C0AE30D6EDB9").resolveHandle("stazioneappaltantetypec66ftype");
    
    /**
     * Gets the "cf" element
     */
    java.lang.String getCf();
    
    /**
     * Gets (as xml) the "cf" element
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCf();
    
    /**
     * True if has "cf" element
     */
    boolean isSetCf();
    
    /**
     * Sets the "cf" element
     */
    void setCf(java.lang.String cf);
    
    /**
     * Sets (as xml) the "cf" element
     */
    void xsetCf(it.avlp.simog.massload.xmlbeans.CodFiscType cf);
    
    /**
     * Unsets the "cf" element
     */
    void unsetCf();
    
    /**
     * Gets the "denominazione" element
     */
    java.lang.String getDenominazione();
    
    /**
     * Gets (as xml) the "denominazione" element
     */
    org.apache.xmlbeans.XmlString xgetDenominazione();
    
    /**
     * True if has "denominazione" element
     */
    boolean isSetDenominazione();
    
    /**
     * Sets the "denominazione" element
     */
    void setDenominazione(java.lang.String denominazione);
    
    /**
     * Sets (as xml) the "denominazione" element
     */
    void xsetDenominazione(org.apache.xmlbeans.XmlString denominazione);
    
    /**
     * Unsets the "denominazione" element
     */
    void unsetDenominazione();
    
    /**
     * Gets the "indirizzo" element
     */
    java.lang.String getIndirizzo();
    
    /**
     * Gets (as xml) the "indirizzo" element
     */
    org.apache.xmlbeans.XmlString xgetIndirizzo();
    
    /**
     * True if has "indirizzo" element
     */
    boolean isSetIndirizzo();
    
    /**
     * Sets the "indirizzo" element
     */
    void setIndirizzo(java.lang.String indirizzo);
    
    /**
     * Sets (as xml) the "indirizzo" element
     */
    void xsetIndirizzo(org.apache.xmlbeans.XmlString indirizzo);
    
    /**
     * Unsets the "indirizzo" element
     */
    void unsetIndirizzo();
    
    /**
     * Gets the "cap" element
     */
    java.lang.String getCap();
    
    /**
     * Gets (as xml) the "cap" element
     */
    org.apache.xmlbeans.XmlString xgetCap();
    
    /**
     * True if has "cap" element
     */
    boolean isSetCap();
    
    /**
     * Sets the "cap" element
     */
    void setCap(java.lang.String cap);
    
    /**
     * Sets (as xml) the "cap" element
     */
    void xsetCap(org.apache.xmlbeans.XmlString cap);
    
    /**
     * Unsets the "cap" element
     */
    void unsetCap();
    
    /**
     * Gets the "comune" element
     */
    java.lang.String getComune();
    
    /**
     * Gets (as xml) the "comune" element
     */
    org.apache.xmlbeans.XmlString xgetComune();
    
    /**
     * True if has "comune" element
     */
    boolean isSetComune();
    
    /**
     * Sets the "comune" element
     */
    void setComune(java.lang.String comune);
    
    /**
     * Sets (as xml) the "comune" element
     */
    void xsetComune(org.apache.xmlbeans.XmlString comune);
    
    /**
     * Unsets the "comune" element
     */
    void unsetComune();
    
    /**
     * Gets the "categoria" element
     */
    java.lang.String getCategoria();
    
    /**
     * Gets (as xml) the "categoria" element
     */
    org.apache.xmlbeans.XmlString xgetCategoria();
    
    /**
     * True if has "categoria" element
     */
    boolean isSetCategoria();
    
    /**
     * Sets the "categoria" element
     */
    void setCategoria(java.lang.String categoria);
    
    /**
     * Sets (as xml) the "categoria" element
     */
    void xsetCategoria(org.apache.xmlbeans.XmlString categoria);
    
    /**
     * Unsets the "categoria" element
     */
    void unsetCategoria();
    
    /**
     * Gets the "idOsservatorio" element
     */
    java.lang.String getIdOsservatorio();
    
    /**
     * Gets (as xml) the "idOsservatorio" element
     */
    org.apache.xmlbeans.XmlString xgetIdOsservatorio();
    
    /**
     * True if has "idOsservatorio" element
     */
    boolean isSetIdOsservatorio();
    
    /**
     * Sets the "idOsservatorio" element
     */
    void setIdOsservatorio(java.lang.String idOsservatorio);
    
    /**
     * Sets (as xml) the "idOsservatorio" element
     */
    void xsetIdOsservatorio(org.apache.xmlbeans.XmlString idOsservatorio);
    
    /**
     * Unsets the "idOsservatorio" element
     */
    void unsetIdOsservatorio();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
