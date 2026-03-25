/*
 * XML Type:  DatiInizioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiInizioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML DatiInizioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface DatiInizioType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiInizioType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("datiiniziotypeb4c0type");
    
    /**
     * Gets the "PubblicazioneEsito" element
     */
    it.avlp.simog.massload.xmlbeans.PubblicazioneType getPubblicazioneEsito();
    
    /**
     * Sets the "PubblicazioneEsito" element
     */
    void setPubblicazioneEsito(it.avlp.simog.massload.xmlbeans.PubblicazioneType pubblicazioneEsito);
    
    /**
     * Appends and returns a new empty "PubblicazioneEsito" element
     */
    it.avlp.simog.massload.xmlbeans.PubblicazioneType addNewPubblicazioneEsito();
    
    /**
     * Gets the "Inizio" element
     */
    it.avlp.simog.massload.xmlbeans.InizioType getInizio();
    
    /**
     * Sets the "Inizio" element
     */
    void setInizio(it.avlp.simog.massload.xmlbeans.InizioType inizio);
    
    /**
     * Appends and returns a new empty "Inizio" element
     */
    it.avlp.simog.massload.xmlbeans.InizioType addNewInizio();
    
    /**
     * Gets array of all "Posizioni" elements
     */
    it.avlp.simog.massload.xmlbeans.PosizioneType[] getPosizioniArray();
    
    /**
     * Gets ith "Posizioni" element
     */
    it.avlp.simog.massload.xmlbeans.PosizioneType getPosizioniArray(int i);
    
    /**
     * Returns number of "Posizioni" element
     */
    int sizeOfPosizioniArray();
    
    /**
     * Sets array of all "Posizioni" element
     */
    void setPosizioniArray(it.avlp.simog.massload.xmlbeans.PosizioneType[] posizioniArray);
    
    /**
     * Sets ith "Posizioni" element
     */
    void setPosizioniArray(int i, it.avlp.simog.massload.xmlbeans.PosizioneType posizioni);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Posizioni" element
     */
    it.avlp.simog.massload.xmlbeans.PosizioneType insertNewPosizioni(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Posizioni" element
     */
    it.avlp.simog.massload.xmlbeans.PosizioneType addNewPosizioni();
    
    /**
     * Removes the ith "Posizioni" element
     */
    void removePosizioni(int i);
    
    /**
     * Gets array of all "Incaricati" elements
     */
    it.avlp.simog.massload.xmlbeans.IncaricatoType[] getIncaricatiArray();
    
    /**
     * Gets ith "Incaricati" element
     */
    it.avlp.simog.massload.xmlbeans.IncaricatoType getIncaricatiArray(int i);
    
    /**
     * Returns number of "Incaricati" element
     */
    int sizeOfIncaricatiArray();
    
    /**
     * Sets array of all "Incaricati" element
     */
    void setIncaricatiArray(it.avlp.simog.massload.xmlbeans.IncaricatoType[] incaricatiArray);
    
    /**
     * Sets ith "Incaricati" element
     */
    void setIncaricatiArray(int i, it.avlp.simog.massload.xmlbeans.IncaricatoType incaricati);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Incaricati" element
     */
    it.avlp.simog.massload.xmlbeans.IncaricatoType insertNewIncaricati(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Incaricati" element
     */
    it.avlp.simog.massload.xmlbeans.IncaricatoType addNewIncaricati();
    
    /**
     * Removes the ith "Incaricati" element
     */
    void removeIncaricati(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DatiInizioType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DatiInizioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
