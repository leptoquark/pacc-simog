/*
 * An XML document type.
 * Localname: check_login
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CheckLoginDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans;


/**
 * A document containing one check_login(@xmlbeans.ws.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public interface CheckLoginDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CheckLoginDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s418CDE98FD70CC5EAC8293609178E17E").resolveHandle("checklogin77aadoctype");
    
    /**
     * Gets the "check_login" element
     */
    it.avlp.simog.ws.xmlbeans.CheckLoginType getCheckLogin();
    
    /**
     * Sets the "check_login" element
     */
    void setCheckLogin(it.avlp.simog.ws.xmlbeans.CheckLoginType checkLogin);
    
    /**
     * Appends and returns a new empty "check_login" element
     */
    it.avlp.simog.ws.xmlbeans.CheckLoginType addNewCheckLogin();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument newInstance() {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.ws.xmlbeans.CheckLoginDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.ws.xmlbeans.CheckLoginDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
