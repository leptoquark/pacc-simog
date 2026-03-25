/*
 * XML Type:  IncaricatoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.IncaricatoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML IncaricatoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface IncaricatoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(IncaricatoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("incaricatotype7f45type");
    
    /**
     * Gets the "SEZIONE" attribute
     */
    it.avlp.simog.massload.xmlbeans.SezioneType.Enum getSEZIONE();
    
    /**
     * Gets (as xml) the "SEZIONE" attribute
     */
    it.avlp.simog.massload.xmlbeans.SezioneType xgetSEZIONE();
    
    /**
     * Sets the "SEZIONE" attribute
     */
    void setSEZIONE(it.avlp.simog.massload.xmlbeans.SezioneType.Enum sezione);
    
    /**
     * Sets (as xml) the "SEZIONE" attribute
     */
    void xsetSEZIONE(it.avlp.simog.massload.xmlbeans.SezioneType sezione);
    
    /**
     * Gets the "ID_RUOLO" attribute
     */
    java.lang.String getIDRUOLO();
    
    /**
     * Gets (as xml) the "ID_RUOLO" attribute
     */
    it.avlp.simog.massload.xmlbeans.RuoloResponsabileType xgetIDRUOLO();
    
    /**
     * Sets the "ID_RUOLO" attribute
     */
    void setIDRUOLO(java.lang.String idruolo);
    
    /**
     * Sets (as xml) the "ID_RUOLO" attribute
     */
    void xsetIDRUOLO(it.avlp.simog.massload.xmlbeans.RuoloResponsabileType idruolo);
    
    /**
     * Gets the "CIG_PROG_ESTERNA" attribute
     */
    java.lang.String getCIGPROGESTERNA();
    
    /**
     * Gets (as xml) the "CIG_PROG_ESTERNA" attribute
     */
    it.avlp.simog.massload.xmlbeans.CigType xgetCIGPROGESTERNA();
    
    /**
     * True if has "CIG_PROG_ESTERNA" attribute
     */
    boolean isSetCIGPROGESTERNA();
    
    /**
     * Sets the "CIG_PROG_ESTERNA" attribute
     */
    void setCIGPROGESTERNA(java.lang.String cigprogesterna);
    
    /**
     * Sets (as xml) the "CIG_PROG_ESTERNA" attribute
     */
    void xsetCIGPROGESTERNA(it.avlp.simog.massload.xmlbeans.CigType cigprogesterna);
    
    /**
     * Unsets the "CIG_PROG_ESTERNA" attribute
     */
    void unsetCIGPROGESTERNA();
    
    /**
     * Gets the "DATA_AFF_PROG_ESTERNA" attribute
     */
    java.util.Calendar getDATAAFFPROGESTERNA();
    
    /**
     * Gets (as xml) the "DATA_AFF_PROG_ESTERNA" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAAFFPROGESTERNA();
    
    /**
     * True if has "DATA_AFF_PROG_ESTERNA" attribute
     */
    boolean isSetDATAAFFPROGESTERNA();
    
    /**
     * Sets the "DATA_AFF_PROG_ESTERNA" attribute
     */
    void setDATAAFFPROGESTERNA(java.util.Calendar dataaffprogesterna);
    
    /**
     * Sets (as xml) the "DATA_AFF_PROG_ESTERNA" attribute
     */
    void xsetDATAAFFPROGESTERNA(it.avlp.simog.massload.xmlbeans.DbDateType dataaffprogesterna);
    
    /**
     * Unsets the "DATA_AFF_PROG_ESTERNA" attribute
     */
    void unsetDATAAFFPROGESTERNA();
    
    /**
     * Gets the "DATA_CONS_PROG_ESTERNA" attribute
     */
    java.util.Calendar getDATACONSPROGESTERNA();
    
    /**
     * Gets (as xml) the "DATA_CONS_PROG_ESTERNA" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetDATACONSPROGESTERNA();
    
    /**
     * True if has "DATA_CONS_PROG_ESTERNA" attribute
     */
    boolean isSetDATACONSPROGESTERNA();
    
    /**
     * Sets the "DATA_CONS_PROG_ESTERNA" attribute
     */
    void setDATACONSPROGESTERNA(java.util.Calendar dataconsprogesterna);
    
    /**
     * Sets (as xml) the "DATA_CONS_PROG_ESTERNA" attribute
     */
    void xsetDATACONSPROGESTERNA(it.avlp.simog.massload.xmlbeans.DbDateType dataconsprogesterna);
    
    /**
     * Unsets the "DATA_CONS_PROG_ESTERNA" attribute
     */
    void unsetDATACONSPROGESTERNA();
    
    /**
     * Gets the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    java.lang.String getCODICEFISCALERESPONSABILE();
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALERESPONSABILE();
    
    /**
     * Sets the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    void setCODICEFISCALERESPONSABILE(java.lang.String codicefiscaleresponsabile);
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    void xsetCODICEFISCALERESPONSABILE(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleresponsabile);
    
    /**
     * Gets the "CODICE_STATO" attribute
     */
    java.lang.String getCODICESTATO();
    
    /**
     * Gets (as xml) the "CODICE_STATO" attribute
     */
    it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATO();
    
    /**
     * True if has "CODICE_STATO" attribute
     */
    boolean isSetCODICESTATO();
    
    /**
     * Sets the "CODICE_STATO" attribute
     */
    void setCODICESTATO(java.lang.String codicestato);
    
    /**
     * Sets (as xml) the "CODICE_STATO" attribute
     */
    void xsetCODICESTATO(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestato);
    
    /**
     * Unsets the "CODICE_STATO" attribute
     */
    void unsetCODICESTATO();
    
    /**
     * Gets the "PERSONA_GIURIDICA" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getPERSONAGIURIDICA();
    
    /**
     * Gets (as xml) the "PERSONA_GIURIDICA" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetPERSONAGIURIDICA();
    
    /**
     * True if has "PERSONA_GIURIDICA" attribute
     */
    boolean isSetPERSONAGIURIDICA();
    
    /**
     * Sets the "PERSONA_GIURIDICA" attribute
     */
    void setPERSONAGIURIDICA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum personagiuridica);
    
    /**
     * Sets (as xml) the "PERSONA_GIURIDICA" attribute
     */
    void xsetPERSONAGIURIDICA(it.avlp.simog.massload.xmlbeans.FlagSNType personagiuridica);
    
    /**
     * Unsets the "PERSONA_GIURIDICA" attribute
     */
    void unsetPERSONAGIURIDICA();
    
    /**
     * Gets the "ID_GRUPPO_INCARICATO" attribute
     */
    int getIDGRUPPOINCARICATO();
    
    /**
     * Gets (as xml) the "ID_GRUPPO_INCARICATO" attribute
     */
    it.avlp.simog.massload.xmlbeans.InteroType99 xgetIDGRUPPOINCARICATO();
    
    /**
     * True if has "ID_GRUPPO_INCARICATO" attribute
     */
    boolean isSetIDGRUPPOINCARICATO();
    
    /**
     * Sets the "ID_GRUPPO_INCARICATO" attribute
     */
    void setIDGRUPPOINCARICATO(int idgruppoincaricato);
    
    /**
     * Sets (as xml) the "ID_GRUPPO_INCARICATO" attribute
     */
    void xsetIDGRUPPOINCARICATO(it.avlp.simog.massload.xmlbeans.InteroType99 idgruppoincaricato);
    
    /**
     * Unsets the "ID_GRUPPO_INCARICATO" attribute
     */
    void unsetIDGRUPPOINCARICATO();
    
    /**
     * Gets the "MANDANTE" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getMANDANTE();
    
    /**
     * Gets (as xml) the "MANDANTE" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetMANDANTE();
    
    /**
     * True if has "MANDANTE" attribute
     */
    boolean isSetMANDANTE();
    
    /**
     * Sets the "MANDANTE" attribute
     */
    void setMANDANTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum mandante);
    
    /**
     * Sets (as xml) the "MANDANTE" attribute
     */
    void xsetMANDANTE(it.avlp.simog.massload.xmlbeans.FlagSNType mandante);
    
    /**
     * Unsets the "MANDANTE" attribute
     */
    void unsetMANDANTE();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.IncaricatoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.IncaricatoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
