/*
 * XML Type:  AddrS5Type
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AddrS5Type
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML AddrS5Type(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface AddrS5Type extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AddrS5Type.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("addrs5typecda5type");
    
    /**
     * Gets the "NATIONALID" attribute
     */
    java.lang.String getNATIONALID();
    
    /**
     * Gets (as xml) the "NATIONALID" attribute
     */
    it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID xgetNATIONALID();
    
    /**
     * Sets the "NATIONALID" attribute
     */
    void setNATIONALID(java.lang.String nationalid);
    
    /**
     * Sets (as xml) the "NATIONALID" attribute
     */
    void xsetNATIONALID(it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID nationalid);
    
    /**
     * Gets the "NUTS" attribute
     */
    java.lang.String getNUTS();
    
    /**
     * Gets (as xml) the "NUTS" attribute
     */
    it.avlp.simog.massload.xmlbeans.LuogoNutsType xgetNUTS();
    
    /**
     * Sets the "NUTS" attribute
     */
    void setNUTS(java.lang.String nuts);
    
    /**
     * Sets (as xml) the "NUTS" attribute
     */
    void xsetNUTS(it.avlp.simog.massload.xmlbeans.LuogoNutsType nuts);
    
    /**
     * Gets the "E_MAIL" attribute
     */
    java.lang.String getEMAIL();
    
    /**
     * Gets (as xml) the "E_MAIL" attribute
     */
    it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL xgetEMAIL();
    
    /**
     * True if has "E_MAIL" attribute
     */
    boolean isSetEMAIL();
    
    /**
     * Sets the "E_MAIL" attribute
     */
    void setEMAIL(java.lang.String email);
    
    /**
     * Sets (as xml) the "E_MAIL" attribute
     */
    void xsetEMAIL(it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL email);
    
    /**
     * Unsets the "E_MAIL" attribute
     */
    void unsetEMAIL();
    
    /**
     * Gets the "PHONE" attribute
     */
    java.lang.String getPHONE();
    
    /**
     * Gets (as xml) the "PHONE" attribute
     */
    it.avlp.simog.massload.xmlbeans.Phone xgetPHONE();
    
    /**
     * True if has "PHONE" attribute
     */
    boolean isSetPHONE();
    
    /**
     * Sets the "PHONE" attribute
     */
    void setPHONE(java.lang.String phone);
    
    /**
     * Sets (as xml) the "PHONE" attribute
     */
    void xsetPHONE(it.avlp.simog.massload.xmlbeans.Phone phone);
    
    /**
     * Unsets the "PHONE" attribute
     */
    void unsetPHONE();
    
    /**
     * Gets the "URL" attribute
     */
    java.lang.String getURL();
    
    /**
     * Gets (as xml) the "URL" attribute
     */
    it.avlp.simog.massload.xmlbeans.AddrS5Type.URL xgetURL();
    
    /**
     * True if has "URL" attribute
     */
    boolean isSetURL();
    
    /**
     * Sets the "URL" attribute
     */
    void setURL(java.lang.String url);
    
    /**
     * Sets (as xml) the "URL" attribute
     */
    void xsetURL(it.avlp.simog.massload.xmlbeans.AddrS5Type.URL url);
    
    /**
     * Unsets the "URL" attribute
     */
    void unsetURL();
    
    /**
     * Gets the "FAX" attribute
     */
    java.lang.String getFAX();
    
    /**
     * Gets (as xml) the "FAX" attribute
     */
    it.avlp.simog.massload.xmlbeans.Phone xgetFAX();
    
    /**
     * True if has "FAX" attribute
     */
    boolean isSetFAX();
    
    /**
     * Sets the "FAX" attribute
     */
    void setFAX(java.lang.String fax);
    
    /**
     * Sets (as xml) the "FAX" attribute
     */
    void xsetFAX(it.avlp.simog.massload.xmlbeans.Phone fax);
    
    /**
     * Unsets the "FAX" attribute
     */
    void unsetFAX();
    
    /**
     * An XML NATIONALID(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS5Type$NATIONALID.
     */
    public interface NATIONALID extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NATIONALID.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("nationalid7b68attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML E_MAIL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS5Type$EMAIL.
     */
    public interface EMAIL extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(EMAIL.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("email00c4attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML URL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS5Type$URL.
     */
    public interface URL extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(URL.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("url513eattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.URL newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.URL) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.URL newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.URL) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AddrS5Type.URL newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AddrS5Type.URL) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type newInstance() {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AddrS5Type parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AddrS5Type) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
