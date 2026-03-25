/*
 * An XML document type.
 * Localname: FormularioAvvisoAggiudicazioneWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * A document containing one FormularioAvvisoAggiudicazioneWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public interface FormularioAvvisoAggiudicazioneWSDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FormularioAvvisoAggiudicazioneWSDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4DE1721B01BD793CD076D1283EC8BA64").resolveHandle("formularioavvisoaggiudicazionews46c1doctype");
    
    /**
     * Gets the "FormularioAvvisoAggiudicazioneWS" element
     */
    it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument.FormularioAvvisoAggiudicazioneWS getFormularioAvvisoAggiudicazioneWS();
    
    /**
     * Sets the "FormularioAvvisoAggiudicazioneWS" element
     */
    void setFormularioAvvisoAggiudicazioneWS(it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument.FormularioAvvisoAggiudicazioneWS formularioAvvisoAggiudicazioneWS);
    
    /**
     * Appends and returns a new empty "FormularioAvvisoAggiudicazioneWS" element
     */
    it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument.FormularioAvvisoAggiudicazioneWS addNewFormularioAvvisoAggiudicazioneWS();
    
    /**
     * An XML FormularioAvvisoAggiudicazioneWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public interface FormularioAvvisoAggiudicazioneWS extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FormularioAvvisoAggiudicazioneWS.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4DE1721B01BD793CD076D1283EC8BA64").resolveHandle("formularioavvisoaggiudicazionewsee4celemtype");
        
        /**
         * Gets the "formularioAvvisoAggiudicazione" element
         */
        it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione getFormularioAvvisoAggiudicazione();
        
        /**
         * Sets the "formularioAvvisoAggiudicazione" element
         */
        void setFormularioAvvisoAggiudicazione(it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione formularioAvvisoAggiudicazione);
        
        /**
         * Appends and returns a new empty "formularioAvvisoAggiudicazione" element
         */
        it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione addNewFormularioAvvisoAggiudicazione();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument.FormularioAvvisoAggiudicazioneWS newInstance() {
              return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument.FormularioAvvisoAggiudicazioneWS) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument.FormularioAvvisoAggiudicazioneWS newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument.FormularioAvvisoAggiudicazioneWS) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument newInstance() {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
