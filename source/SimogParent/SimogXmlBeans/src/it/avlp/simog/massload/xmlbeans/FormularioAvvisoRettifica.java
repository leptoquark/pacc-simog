/*
 * XML Type:  FormularioAvvisoRettifica
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML FormularioAvvisoRettifica(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface FormularioAvvisoRettifica extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FormularioAvvisoRettifica.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s115B2A225BB83D77E5E0601BD0D365B4").resolveHandle("formularioavvisorettificae667type");
    
    /**
     * Gets array of all "RETTIFICA" elements
     */
    it.avlp.simog.massload.xmlbeans.RettificaType[] getRETTIFICAArray();
    
    /**
     * Gets ith "RETTIFICA" element
     */
    it.avlp.simog.massload.xmlbeans.RettificaType getRETTIFICAArray(int i);
    
    /**
     * Returns number of "RETTIFICA" element
     */
    int sizeOfRETTIFICAArray();
    
    /**
     * Sets array of all "RETTIFICA" element
     */
    void setRETTIFICAArray(it.avlp.simog.massload.xmlbeans.RettificaType[] rettificaArray);
    
    /**
     * Sets ith "RETTIFICA" element
     */
    void setRETTIFICAArray(int i, it.avlp.simog.massload.xmlbeans.RettificaType rettifica);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "RETTIFICA" element
     */
    it.avlp.simog.massload.xmlbeans.RettificaType insertNewRETTIFICA(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "RETTIFICA" element
     */
    it.avlp.simog.massload.xmlbeans.RettificaType addNewRETTIFICA();
    
    /**
     * Removes the ith "RETTIFICA" element
     */
    void removeRETTIFICA(int i);
    
    /**
     * Gets the "MOTIVO_RETTIFICA" attribute
     */
    it.avlp.simog.massload.xmlbeans.MotivoRettificaType.Enum getMOTIVORETTIFICA();
    
    /**
     * Gets (as xml) the "MOTIVO_RETTIFICA" attribute
     */
    it.avlp.simog.massload.xmlbeans.MotivoRettificaType xgetMOTIVORETTIFICA();
    
    /**
     * Sets the "MOTIVO_RETTIFICA" attribute
     */
    void setMOTIVORETTIFICA(it.avlp.simog.massload.xmlbeans.MotivoRettificaType.Enum motivorettifica);
    
    /**
     * Sets (as xml) the "MOTIVO_RETTIFICA" attribute
     */
    void xsetMOTIVORETTIFICA(it.avlp.simog.massload.xmlbeans.MotivoRettificaType motivorettifica);
    
    /**
     * Gets the "INFO_ADD_MODIFICA" attribute
     */
    java.lang.String getINFOADDMODIFICA();
    
    /**
     * Gets (as xml) the "INFO_ADD_MODIFICA" attribute
     */
    it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA xgetINFOADDMODIFICA();
    
    /**
     * True if has "INFO_ADD_MODIFICA" attribute
     */
    boolean isSetINFOADDMODIFICA();
    
    /**
     * Sets the "INFO_ADD_MODIFICA" attribute
     */
    void setINFOADDMODIFICA(java.lang.String infoaddmodifica);
    
    /**
     * Sets (as xml) the "INFO_ADD_MODIFICA" attribute
     */
    void xsetINFOADDMODIFICA(it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA infoaddmodifica);
    
    /**
     * Unsets the "INFO_ADD_MODIFICA" attribute
     */
    void unsetINFOADDMODIFICA();
    
    /**
     * An XML INFO_ADD_MODIFICA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica$INFOADDMODIFICA.
     */
    public interface INFOADDMODIFICA extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(INFOADDMODIFICA.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s115B2A225BB83D77E5E0601BD0D365B4").resolveHandle("infoaddmodifica4dd6attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA newInstance() {
              return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica newInstance() {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
