/*
 * XML Type:  AggiudicatariType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AggiudicatariType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML AggiudicatariType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface AggiudicatariType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AggiudicatariType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("aggiudicataritype7cf4type");
    
    /**
     * Gets array of all "Aggiudicatario" elements
     */
    it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] getAggiudicatarioArray();
    
    /**
     * Gets ith "Aggiudicatario" element
     */
    it.avlp.simog.massload.xmlbeans.AggiudicatarioType getAggiudicatarioArray(int i);
    
    /**
     * Returns number of "Aggiudicatario" element
     */
    int sizeOfAggiudicatarioArray();
    
    /**
     * Sets array of all "Aggiudicatario" element
     */
    void setAggiudicatarioArray(it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] aggiudicatarioArray);
    
    /**
     * Sets ith "Aggiudicatario" element
     */
    void setAggiudicatarioArray(int i, it.avlp.simog.massload.xmlbeans.AggiudicatarioType aggiudicatario);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Aggiudicatario" element
     */
    it.avlp.simog.massload.xmlbeans.AggiudicatarioType insertNewAggiudicatario(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Aggiudicatario" element
     */
    it.avlp.simog.massload.xmlbeans.AggiudicatarioType addNewAggiudicatario();
    
    /**
     * Removes the ith "Aggiudicatario" element
     */
    void removeAggiudicatario(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AggiudicatariType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AggiudicatariType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
