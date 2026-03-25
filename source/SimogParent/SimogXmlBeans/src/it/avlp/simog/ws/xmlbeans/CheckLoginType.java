/*
 * XML Type:  checkLoginType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CheckLoginType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans;


/**
 * An XML checkLoginType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface CheckLoginType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CheckLoginType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s418CDE98FD70CC5EAC8293609178E17E").resolveHandle("checklogintype5d55type");
    
    /**
     * Gets the "soggetto" element
     */
    it.avlp.simog.ws.xmlbeans.SoggettoType getSoggetto();
    
    /**
     * True if has "soggetto" element
     */
    boolean isSetSoggetto();
    
    /**
     * Sets the "soggetto" element
     */
    void setSoggetto(it.avlp.simog.ws.xmlbeans.SoggettoType soggetto);
    
    /**
     * Appends and returns a new empty "soggetto" element
     */
    it.avlp.simog.ws.xmlbeans.SoggettoType addNewSoggetto();
    
    /**
     * Unsets the "soggetto" element
     */
    void unsetSoggetto();
    
    /**
     * Gets the "collaborazioni" element
     */
    it.avlp.simog.ws.xmlbeans.CollaborazioneList getCollaborazioni();
    
    /**
     * True if has "collaborazioni" element
     */
    boolean isSetCollaborazioni();
    
    /**
     * Sets the "collaborazioni" element
     */
    void setCollaborazioni(it.avlp.simog.ws.xmlbeans.CollaborazioneList collaborazioni);
    
    /**
     * Appends and returns a new empty "collaborazioni" element
     */
    it.avlp.simog.ws.xmlbeans.CollaborazioneList addNewCollaborazioni();
    
    /**
     * Unsets the "collaborazioni" element
     */
    void unsetCollaborazioni();
    
    /**
     * Gets the "stato" element
     */
    java.lang.String getStato();
    
    /**
     * Gets (as xml) the "stato" element
     */
    org.apache.xmlbeans.XmlString xgetStato();
    
    /**
     * Sets the "stato" element
     */
    void setStato(java.lang.String stato);
    
    /**
     * Sets (as xml) the "stato" element
     */
    void xsetStato(org.apache.xmlbeans.XmlString stato);
    
    /**
     * Gets the "messaggio" element
     */
    java.lang.String getMessaggio();
    
    /**
     * Gets (as xml) the "messaggio" element
     */
    org.apache.xmlbeans.XmlString xgetMessaggio();
    
    /**
     * True if has "messaggio" element
     */
    boolean isSetMessaggio();
    
    /**
     * Sets the "messaggio" element
     */
    void setMessaggio(java.lang.String messaggio);
    
    /**
     * Sets (as xml) the "messaggio" element
     */
    void xsetMessaggio(org.apache.xmlbeans.XmlString messaggio);
    
    /**
     * Unsets the "messaggio" element
     */
    void unsetMessaggio();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType newInstance() {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
