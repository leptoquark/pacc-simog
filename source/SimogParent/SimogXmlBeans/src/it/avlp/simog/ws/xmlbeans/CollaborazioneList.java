/*
 * XML Type:  collaborazioneList
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CollaborazioneList
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans;


/**
 * An XML collaborazioneList(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface CollaborazioneList extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CollaborazioneList.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s418CDE98FD70CC5EAC8293609178E17E").resolveHandle("collaborazionelistd2fetype");
    
    /**
     * Gets array of all "collaborazione" elements
     */
    it.avlp.simog.ws.xmlbeans.CollaborazioneType[] getCollaborazioneArray();
    
    /**
     * Gets ith "collaborazione" element
     */
    it.avlp.simog.ws.xmlbeans.CollaborazioneType getCollaborazioneArray(int i);
    
    /**
     * Returns number of "collaborazione" element
     */
    int sizeOfCollaborazioneArray();
    
    /**
     * Sets array of all "collaborazione" element
     */
    void setCollaborazioneArray(it.avlp.simog.ws.xmlbeans.CollaborazioneType[] collaborazioneArray);
    
    /**
     * Sets ith "collaborazione" element
     */
    void setCollaborazioneArray(int i, it.avlp.simog.ws.xmlbeans.CollaborazioneType collaborazione);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "collaborazione" element
     */
    it.avlp.simog.ws.xmlbeans.CollaborazioneType insertNewCollaborazione(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "collaborazione" element
     */
    it.avlp.simog.ws.xmlbeans.CollaborazioneType addNewCollaborazione();
    
    /**
     * Removes the ith "collaborazione" element
     */
    void removeCollaborazione(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList newInstance() {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CollaborazioneList parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CollaborazioneList) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
