/*
 * XML Type:  ReqGaraType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ReqGaraType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML ReqGaraType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface ReqGaraType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ReqGaraType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s5DE9F8D54B0399C9A54FBA1514CCCE6C").resolveHandle("reqgaratype8287type");
    
    /**
     * Gets array of all "CIG" elements
     */
    java.lang.String[] getCIGArray();
    
    /**
     * Gets ith "CIG" element
     */
    java.lang.String getCIGArray(int i);
    
    /**
     * Gets (as xml) array of all "CIG" elements
     */
    it.avlp.simog.massload.xmlbeans.CigType[] xgetCIGArray();
    
    /**
     * Gets (as xml) ith "CIG" element
     */
    it.avlp.simog.massload.xmlbeans.CigType xgetCIGArray(int i);
    
    /**
     * Returns number of "CIG" element
     */
    int sizeOfCIGArray();
    
    /**
     * Sets array of all "CIG" element
     */
    void setCIGArray(java.lang.String[] cigArray);
    
    /**
     * Sets ith "CIG" element
     */
    void setCIGArray(int i, java.lang.String cig);
    
    /**
     * Sets (as xml) array of all "CIG" element
     */
    void xsetCIGArray(it.avlp.simog.massload.xmlbeans.CigType[] cigArray);
    
    /**
     * Sets (as xml) ith "CIG" element
     */
    void xsetCIGArray(int i, it.avlp.simog.massload.xmlbeans.CigType cig);
    
    /**
     * Inserts the value as the ith "CIG" element
     */
    void insertCIG(int i, java.lang.String cig);
    
    /**
     * Appends the value as the last "CIG" element
     */
    void addCIG(java.lang.String cig);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CIG" element
     */
    it.avlp.simog.massload.xmlbeans.CigType insertNewCIG(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CIG" element
     */
    it.avlp.simog.massload.xmlbeans.CigType addNewCIG();
    
    /**
     * Removes the ith "CIG" element
     */
    void removeCIG(int i);
    
    /**
     * Gets array of all "DOCUMENTO" elements
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType[] getDOCUMENTOArray();
    
    /**
     * Gets ith "DOCUMENTO" element
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType getDOCUMENTOArray(int i);
    
    /**
     * Returns number of "DOCUMENTO" element
     */
    int sizeOfDOCUMENTOArray();
    
    /**
     * Sets array of all "DOCUMENTO" element
     */
    void setDOCUMENTOArray(it.avlp.simog.massload.xmlbeans.ReqDocType[] documentoArray);
    
    /**
     * Sets ith "DOCUMENTO" element
     */
    void setDOCUMENTOArray(int i, it.avlp.simog.massload.xmlbeans.ReqDocType documento);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "DOCUMENTO" element
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType insertNewDOCUMENTO(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "DOCUMENTO" element
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType addNewDOCUMENTO();
    
    /**
     * Removes the ith "DOCUMENTO" element
     */
    void removeDOCUMENTO(int i);
    
    /**
     * Gets the "codice_dettaglio" attribute
     */
    java.lang.String getCodiceDettaglio();
    
    /**
     * Gets (as xml) the "codice_dettaglio" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodRequisitoType xgetCodiceDettaglio();
    
    /**
     * Sets the "codice_dettaglio" attribute
     */
    void setCodiceDettaglio(java.lang.String codiceDettaglio);
    
    /**
     * Sets (as xml) the "codice_dettaglio" attribute
     */
    void xsetCodiceDettaglio(it.avlp.simog.massload.xmlbeans.CodRequisitoType codiceDettaglio);
    
    /**
     * Gets the "descrizione" attribute
     */
    java.lang.String getDescrizione();
    
    /**
     * Gets (as xml) the "descrizione" attribute
     */
    it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione xgetDescrizione();
    
    /**
     * Sets the "descrizione" attribute
     */
    void setDescrizione(java.lang.String descrizione);
    
    /**
     * Sets (as xml) the "descrizione" attribute
     */
    void xsetDescrizione(it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione descrizione);
    
    /**
     * Gets the "valore" attribute
     */
    java.lang.String getValore();
    
    /**
     * Gets (as xml) the "valore" attribute
     */
    it.avlp.simog.massload.xmlbeans.CuiType xgetValore();
    
    /**
     * Sets the "valore" attribute
     */
    void setValore(java.lang.String valore);
    
    /**
     * Sets (as xml) the "valore" attribute
     */
    void xsetValore(it.avlp.simog.massload.xmlbeans.CuiType valore);
    
    /**
     * Gets the "flag_esclusione" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagEsclusione();
    
    /**
     * Gets (as xml) the "flag_esclusione" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagEsclusione();
    
    /**
     * Sets the "flag_esclusione" attribute
     */
    void setFlagEsclusione(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagEsclusione);
    
    /**
     * Sets (as xml) the "flag_esclusione" attribute
     */
    void xsetFlagEsclusione(it.avlp.simog.massload.xmlbeans.FlagSNType flagEsclusione);
    
    /**
     * Gets the "flag_comprova_offerta" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagComprovaOfferta();
    
    /**
     * Gets (as xml) the "flag_comprova_offerta" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagComprovaOfferta();
    
    /**
     * Sets the "flag_comprova_offerta" attribute
     */
    void setFlagComprovaOfferta(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagComprovaOfferta);
    
    /**
     * Sets (as xml) the "flag_comprova_offerta" attribute
     */
    void xsetFlagComprovaOfferta(it.avlp.simog.massload.xmlbeans.FlagSNType flagComprovaOfferta);
    
    /**
     * Gets the "flag_avvalimento" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagAvvalimento();
    
    /**
     * Gets (as xml) the "flag_avvalimento" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagAvvalimento();
    
    /**
     * Sets the "flag_avvalimento" attribute
     */
    void setFlagAvvalimento(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagAvvalimento);
    
    /**
     * Sets (as xml) the "flag_avvalimento" attribute
     */
    void xsetFlagAvvalimento(it.avlp.simog.massload.xmlbeans.FlagSNType flagAvvalimento);
    
    /**
     * Gets the "flag_bando_tipo" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagBandoTipo();
    
    /**
     * Gets (as xml) the "flag_bando_tipo" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagBandoTipo();
    
    /**
     * Sets the "flag_bando_tipo" attribute
     */
    void setFlagBandoTipo(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagBandoTipo);
    
    /**
     * Sets (as xml) the "flag_bando_tipo" attribute
     */
    void xsetFlagBandoTipo(it.avlp.simog.massload.xmlbeans.FlagSNType flagBandoTipo);
    
    /**
     * Gets the "flag_riservatezza" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagRiservatezza();
    
    /**
     * Gets (as xml) the "flag_riservatezza" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagRiservatezza();
    
    /**
     * Sets the "flag_riservatezza" attribute
     */
    void setFlagRiservatezza(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagRiservatezza);
    
    /**
     * Sets (as xml) the "flag_riservatezza" attribute
     */
    void xsetFlagRiservatezza(it.avlp.simog.massload.xmlbeans.FlagSNType flagRiservatezza);
    
    /**
     * An XML descrizione(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqGaraType$Descrizione.
     */
    public interface Descrizione extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Descrizione.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s5DE9F8D54B0399C9A54FBA1514CCCE6C").resolveHandle("descrizionefa0eattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione newInstance() {
              return (it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ReqGaraType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ReqGaraType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
