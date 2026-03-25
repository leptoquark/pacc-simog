/*
 * XML Type:  SubappaltiType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SubappaltiType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML SubappaltiType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface SubappaltiType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SubappaltiType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("subappaltitype7b73type");
    
    /**
     * Gets array of all "Subappalto" elements
     */
    it.avlp.simog.massload.xmlbeans.SubappaltoType[] getSubappaltoArray();
    
    /**
     * Gets ith "Subappalto" element
     */
    it.avlp.simog.massload.xmlbeans.SubappaltoType getSubappaltoArray(int i);
    
    /**
     * Returns number of "Subappalto" element
     */
    int sizeOfSubappaltoArray();
    
    /**
     * Sets array of all "Subappalto" element
     */
    void setSubappaltoArray(it.avlp.simog.massload.xmlbeans.SubappaltoType[] subappaltoArray);
    
    /**
     * Sets ith "Subappalto" element
     */
    void setSubappaltoArray(int i, it.avlp.simog.massload.xmlbeans.SubappaltoType subappalto);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Subappalto" element
     */
    it.avlp.simog.massload.xmlbeans.SubappaltoType insertNewSubappalto(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Subappalto" element
     */
    it.avlp.simog.massload.xmlbeans.SubappaltoType addNewSubappalto();
    
    /**
     * Removes the ith "Subappalto" element
     */
    void removeSubappalto(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.SubappaltiType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.SubappaltiType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
