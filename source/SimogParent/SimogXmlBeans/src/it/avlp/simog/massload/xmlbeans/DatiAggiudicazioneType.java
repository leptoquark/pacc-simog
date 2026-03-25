/*
 * XML Type:  DatiAggiudicazioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML DatiAggiudicazioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface DatiAggiudicazioneType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiAggiudicazioneType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("datiaggiudicazionetype6b4btype");
    
    /**
     * Gets the "DatiComuni" element
     */
    it.avlp.simog.massload.xmlbeans.DatiComuniType getDatiComuni();
    
    /**
     * Sets the "DatiComuni" element
     */
    void setDatiComuni(it.avlp.simog.massload.xmlbeans.DatiComuniType datiComuni);
    
    /**
     * Appends and returns a new empty "DatiComuni" element
     */
    it.avlp.simog.massload.xmlbeans.DatiComuniType addNewDatiComuni();
    
    /**
     * Gets the "Pubblicazione" element
     */
    it.avlp.simog.massload.xmlbeans.PubblicazioneType getPubblicazione();
    
    /**
     * True if has "Pubblicazione" element
     */
    boolean isSetPubblicazione();
    
    /**
     * Sets the "Pubblicazione" element
     */
    void setPubblicazione(it.avlp.simog.massload.xmlbeans.PubblicazioneType pubblicazione);
    
    /**
     * Appends and returns a new empty "Pubblicazione" element
     */
    it.avlp.simog.massload.xmlbeans.PubblicazioneType addNewPubblicazione();
    
    /**
     * Unsets the "Pubblicazione" element
     */
    void unsetPubblicazione();
    
    /**
     * Gets array of all "SchedaCompleta" elements
     */
    it.avlp.simog.massload.xmlbeans.SchedaCompletaType[] getSchedaCompletaArray();
    
    /**
     * Gets ith "SchedaCompleta" element
     */
    it.avlp.simog.massload.xmlbeans.SchedaCompletaType getSchedaCompletaArray(int i);
    
    /**
     * Returns number of "SchedaCompleta" element
     */
    int sizeOfSchedaCompletaArray();
    
    /**
     * Sets array of all "SchedaCompleta" element
     */
    void setSchedaCompletaArray(it.avlp.simog.massload.xmlbeans.SchedaCompletaType[] schedaCompletaArray);
    
    /**
     * Sets ith "SchedaCompleta" element
     */
    void setSchedaCompletaArray(int i, it.avlp.simog.massload.xmlbeans.SchedaCompletaType schedaCompleta);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "SchedaCompleta" element
     */
    it.avlp.simog.massload.xmlbeans.SchedaCompletaType insertNewSchedaCompleta(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "SchedaCompleta" element
     */
    it.avlp.simog.massload.xmlbeans.SchedaCompletaType addNewSchedaCompleta();
    
    /**
     * Removes the ith "SchedaCompleta" element
     */
    void removeSchedaCompleta(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
