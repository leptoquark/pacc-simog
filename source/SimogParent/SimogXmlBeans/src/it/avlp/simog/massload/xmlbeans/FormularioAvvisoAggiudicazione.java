/*
 * XML Type:  FormularioAvvisoAggiudicazione
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML FormularioAvvisoAggiudicazione(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface FormularioAvvisoAggiudicazione extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FormularioAvvisoAggiudicazione.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4DE1721B01BD793CD076D1283EC8BA64").resolveHandle("formularioavvisoaggiudicazione4755type");
    
    /**
     * Gets the "VALORE_APPALTO" element
     */
    it.avlp.simog.massload.xmlbeans.ValoreAppaltoType getVALOREAPPALTO();
    
    /**
     * True if has "VALORE_APPALTO" element
     */
    boolean isSetVALOREAPPALTO();
    
    /**
     * Sets the "VALORE_APPALTO" element
     */
    void setVALOREAPPALTO(it.avlp.simog.massload.xmlbeans.ValoreAppaltoType valoreappalto);
    
    /**
     * Appends and returns a new empty "VALORE_APPALTO" element
     */
    it.avlp.simog.massload.xmlbeans.ValoreAppaltoType addNewVALOREAPPALTO();
    
    /**
     * Unsets the "VALORE_APPALTO" element
     */
    void unsetVALOREAPPALTO();
    
    /**
     * Gets the "INFO_AMMINISTRATIVE_AGG" element
     */
    it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg getINFOAMMINISTRATIVEAGG();
    
    /**
     * True if has "INFO_AMMINISTRATIVE_AGG" element
     */
    boolean isSetINFOAMMINISTRATIVEAGG();
    
    /**
     * Sets the "INFO_AMMINISTRATIVE_AGG" element
     */
    void setINFOAMMINISTRATIVEAGG(it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg infoamministrativeagg);
    
    /**
     * Appends and returns a new empty "INFO_AMMINISTRATIVE_AGG" element
     */
    it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg addNewINFOAMMINISTRATIVEAGG();
    
    /**
     * Unsets the "INFO_AMMINISTRATIVE_AGG" element
     */
    void unsetINFOAMMINISTRATIVEAGG();
    
    /**
     * Gets array of all "APPALTO_AVV_AGG" elements
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg[] getAPPALTOAVVAGGArray();
    
    /**
     * Gets ith "APPALTO_AVV_AGG" element
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg getAPPALTOAVVAGGArray(int i);
    
    /**
     * Returns number of "APPALTO_AVV_AGG" element
     */
    int sizeOfAPPALTOAVVAGGArray();
    
    /**
     * Sets array of all "APPALTO_AVV_AGG" element
     */
    void setAPPALTOAVVAGGArray(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg[] appaltoavvaggArray);
    
    /**
     * Sets ith "APPALTO_AVV_AGG" element
     */
    void setAPPALTOAVVAGGArray(int i, it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg appaltoavvagg);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "APPALTO_AVV_AGG" element
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg insertNewAPPALTOAVVAGG(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "APPALTO_AVV_AGG" element
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg addNewAPPALTOAVVAGG();
    
    /**
     * Removes the ith "APPALTO_AVV_AGG" element
     */
    void removeAPPALTOAVVAGG(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione newInstance() {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
