/*
 * XML Type:  DatiCUPType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiCUPType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML DatiCUPType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface DatiCUPType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiCUPType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("daticuptype5aectype");
    
    /**
     * Gets the "CUP" attribute
     */
    java.lang.String getCUP();
    
    /**
     * Gets (as xml) the "CUP" attribute
     */
    it.avlp.simog.massload.xmlbeans.CupType xgetCUP();
    
    /**
     * Sets the "CUP" attribute
     */
    void setCUP(java.lang.String cup);
    
    /**
     * Sets (as xml) the "CUP" attribute
     */
    void xsetCUP(it.avlp.simog.massload.xmlbeans.CupType cup);
    
    /**
     * Gets the "ID_RICHIESTA" attribute
     */
    long getIDRICHIESTA();
    
    /**
     * Gets (as xml) the "ID_RICHIESTA" attribute
     */
    it.avlp.simog.massload.xmlbeans.LongType xgetIDRICHIESTA();
    
    /**
     * True if has "ID_RICHIESTA" attribute
     */
    boolean isSetIDRICHIESTA();
    
    /**
     * Sets the "ID_RICHIESTA" attribute
     */
    void setIDRICHIESTA(long idrichiesta);
    
    /**
     * Sets (as xml) the "ID_RICHIESTA" attribute
     */
    void xsetIDRICHIESTA(it.avlp.simog.massload.xmlbeans.LongType idrichiesta);
    
    /**
     * Unsets the "ID_RICHIESTA" attribute
     */
    void unsetIDRICHIESTA();
    
    /**
     * Gets the "DATI_DIPE" attribute
     */
    java.lang.String getDATIDIPE();
    
    /**
     * Gets (as xml) the "DATI_DIPE" attribute
     */
    it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE xgetDATIDIPE();
    
    /**
     * True if has "DATI_DIPE" attribute
     */
    boolean isSetDATIDIPE();
    
    /**
     * Sets the "DATI_DIPE" attribute
     */
    void setDATIDIPE(java.lang.String datidipe);
    
    /**
     * Sets (as xml) the "DATI_DIPE" attribute
     */
    void xsetDATIDIPE(it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE datidipe);
    
    /**
     * Unsets the "DATI_DIPE" attribute
     */
    void unsetDATIDIPE();
    
    /**
     * Gets the "VALIDO" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getVALIDO();
    
    /**
     * Gets (as xml) the "VALIDO" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetVALIDO();
    
    /**
     * True if has "VALIDO" attribute
     */
    boolean isSetVALIDO();
    
    /**
     * Sets the "VALIDO" attribute
     */
    void setVALIDO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum valido);
    
    /**
     * Sets (as xml) the "VALIDO" attribute
     */
    void xsetVALIDO(it.avlp.simog.massload.xmlbeans.FlagSNType valido);
    
    /**
     * Unsets the "VALIDO" attribute
     */
    void unsetVALIDO();
    
    /**
     * Gets the "OK_UTENTE" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getOKUTENTE();
    
    /**
     * Gets (as xml) the "OK_UTENTE" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetOKUTENTE();
    
    /**
     * True if has "OK_UTENTE" attribute
     */
    boolean isSetOKUTENTE();
    
    /**
     * Sets the "OK_UTENTE" attribute
     */
    void setOKUTENTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum okutente);
    
    /**
     * Sets (as xml) the "OK_UTENTE" attribute
     */
    void xsetOKUTENTE(it.avlp.simog.massload.xmlbeans.FlagSNType okutente);
    
    /**
     * Unsets the "OK_UTENTE" attribute
     */
    void unsetOKUTENTE();
    
    /**
     * Gets the "TEMATICA_PNRR" attribute
     */
    java.lang.String getTEMATICAPNRR();
    
    /**
     * Gets (as xml) the "TEMATICA_PNRR" attribute
     */
    org.apache.xmlbeans.XmlString xgetTEMATICAPNRR();
    
    /**
     * True if has "TEMATICA_PNRR" attribute
     */
    boolean isSetTEMATICAPNRR();
    
    /**
     * Sets the "TEMATICA_PNRR" attribute
     */
    void setTEMATICAPNRR(java.lang.String tematicapnrr);
    
    /**
     * Sets (as xml) the "TEMATICA_PNRR" attribute
     */
    void xsetTEMATICAPNRR(org.apache.xmlbeans.XmlString tematicapnrr);
    
    /**
     * Unsets the "TEMATICA_PNRR" attribute
     */
    void unsetTEMATICAPNRR();
    
    /**
     * An XML DATI_DIPE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiCUPType$DATIDIPE.
     */
    public interface DATIDIPE extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DATIDIPE.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("datidipe95cbattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE newInstance() {
              return (it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.DatiCUPType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.DatiCUPType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
