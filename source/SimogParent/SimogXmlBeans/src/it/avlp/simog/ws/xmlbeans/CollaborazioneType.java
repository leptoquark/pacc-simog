/*
 * XML Type:  collaborazioneType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CollaborazioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans;


/**
 * An XML collaborazioneType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface CollaborazioneType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CollaborazioneType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s418CDE98FD70CC5EAC8293609178E17E").resolveHandle("collaborazionetype669atype");
    
    /**
     * Gets the "azienda" element
     */
    it.avlp.simog.ws.xmlbeans.AziendaType getAzienda();
    
    /**
     * Sets the "azienda" element
     */
    void setAzienda(it.avlp.simog.ws.xmlbeans.AziendaType azienda);
    
    /**
     * Appends and returns a new empty "azienda" element
     */
    it.avlp.simog.ws.xmlbeans.AziendaType addNewAzienda();
    
    /**
     * Gets the "ufficio" element
     */
    it.avlp.simog.ws.xmlbeans.UfficioType getUfficio();
    
    /**
     * Sets the "ufficio" element
     */
    void setUfficio(it.avlp.simog.ws.xmlbeans.UfficioType ufficio);
    
    /**
     * Appends and returns a new empty "ufficio" element
     */
    it.avlp.simog.ws.xmlbeans.UfficioType addNewUfficio();
    
    /**
     * Gets the "index" attribute
     */
    java.lang.String getIndex();
    
    /**
     * Gets (as xml) the "index" attribute
     */
    org.apache.xmlbeans.XmlString xgetIndex();
    
    /**
     * Sets the "index" attribute
     */
    void setIndex(java.lang.String index);
    
    /**
     * Sets (as xml) the "index" attribute
     */
    void xsetIndex(org.apache.xmlbeans.XmlString index);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType newInstance() {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
