/*
 * XML Type:  aziendaType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.AziendaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans;


/**
 * An XML aziendaType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface AziendaType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AziendaType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s418CDE98FD70CC5EAC8293609178E17E").resolveHandle("aziendatype2f58type");
    
    /**
     * Gets the "denominazione" element
     */
    java.lang.String getDenominazione();
    
    /**
     * Gets (as xml) the "denominazione" element
     */
    org.apache.xmlbeans.XmlString xgetDenominazione();
    
    /**
     * Sets the "denominazione" element
     */
    void setDenominazione(java.lang.String denominazione);
    
    /**
     * Sets (as xml) the "denominazione" element
     */
    void xsetDenominazione(org.apache.xmlbeans.XmlString denominazione);
    
    /**
     * Gets the "codice_fiscale" element
     */
    java.lang.String getCodiceFiscale();
    
    /**
     * Gets (as xml) the "codice_fiscale" element
     */
    org.apache.xmlbeans.XmlString xgetCodiceFiscale();
    
    /**
     * Sets the "codice_fiscale" element
     */
    void setCodiceFiscale(java.lang.String codiceFiscale);
    
    /**
     * Sets (as xml) the "codice_fiscale" element
     */
    void xsetCodiceFiscale(org.apache.xmlbeans.XmlString codiceFiscale);
    
    /**
     * Gets the "id_osservatorio" element
     */
    java.lang.String getIdOsservatorio();
    
    /**
     * Gets (as xml) the "id_osservatorio" element
     */
    org.apache.xmlbeans.XmlString xgetIdOsservatorio();
    
    /**
     * True if has "id_osservatorio" element
     */
    boolean isSetIdOsservatorio();
    
    /**
     * Sets the "id_osservatorio" element
     */
    void setIdOsservatorio(java.lang.String idOsservatorio);
    
    /**
     * Sets (as xml) the "id_osservatorio" element
     */
    void xsetIdOsservatorio(org.apache.xmlbeans.XmlString idOsservatorio);
    
    /**
     * Unsets the "id_osservatorio" element
     */
    void unsetIdOsservatorio();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.ws.xmlbeans.AziendaType newInstance() {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.AziendaType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.AziendaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
