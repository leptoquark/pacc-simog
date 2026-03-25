/*
 * XML Type:  ReqDocType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ReqDocType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML ReqDocType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface ReqDocType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ReqDocType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s5DE9F8D54B0399C9A54FBA1514CCCE6C").resolveHandle("reqdoctype6fectype");
    
    /**
     * Gets the "codice_tipo_doc" attribute
     */
    java.lang.String getCodiceTipoDoc();
    
    /**
     * Gets (as xml) the "codice_tipo_doc" attribute
     */
    it.avlp.simog.massload.xmlbeans.CodTipoDocType xgetCodiceTipoDoc();
    
    /**
     * Sets the "codice_tipo_doc" attribute
     */
    void setCodiceTipoDoc(java.lang.String codiceTipoDoc);
    
    /**
     * Sets (as xml) the "codice_tipo_doc" attribute
     */
    void xsetCodiceTipoDoc(it.avlp.simog.massload.xmlbeans.CodTipoDocType codiceTipoDoc);
    
    /**
     * Gets the "descrizione_documento" attribute
     */
    java.lang.String getDescrizioneDocumento();
    
    /**
     * Gets (as xml) the "descrizione_documento" attribute
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento xgetDescrizioneDocumento();
    
    /**
     * Sets the "descrizione_documento" attribute
     */
    void setDescrizioneDocumento(java.lang.String descrizioneDocumento);
    
    /**
     * Sets (as xml) the "descrizione_documento" attribute
     */
    void xsetDescrizioneDocumento(it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento descrizioneDocumento);
    
    /**
     * Gets the "emettitore" attribute
     */
    java.lang.String getEmettitore();
    
    /**
     * Gets (as xml) the "emettitore" attribute
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore xgetEmettitore();
    
    /**
     * Sets the "emettitore" attribute
     */
    void setEmettitore(java.lang.String emettitore);
    
    /**
     * Sets (as xml) the "emettitore" attribute
     */
    void xsetEmettitore(it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore emettitore);
    
    /**
     * Gets the "fax" attribute
     */
    java.lang.String getFax();
    
    /**
     * Gets (as xml) the "fax" attribute
     */
    it.avlp.simog.massload.xmlbeans.NumTelType xgetFax();
    
    /**
     * Sets the "fax" attribute
     */
    void setFax(java.lang.String fax);
    
    /**
     * Sets (as xml) the "fax" attribute
     */
    void xsetFax(it.avlp.simog.massload.xmlbeans.NumTelType fax);
    
    /**
     * Gets the "telefono" attribute
     */
    java.lang.String getTelefono();
    
    /**
     * Gets (as xml) the "telefono" attribute
     */
    it.avlp.simog.massload.xmlbeans.NumTelType xgetTelefono();
    
    /**
     * Sets the "telefono" attribute
     */
    void setTelefono(java.lang.String telefono);
    
    /**
     * Sets (as xml) the "telefono" attribute
     */
    void xsetTelefono(it.avlp.simog.massload.xmlbeans.NumTelType telefono);
    
    /**
     * Gets the "mail" attribute
     */
    java.lang.String getMail();
    
    /**
     * Gets (as xml) the "mail" attribute
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType.Mail xgetMail();
    
    /**
     * Sets the "mail" attribute
     */
    void setMail(java.lang.String mail);
    
    /**
     * Sets (as xml) the "mail" attribute
     */
    void xsetMail(it.avlp.simog.massload.xmlbeans.ReqDocType.Mail mail);
    
    /**
     * Gets the "mail_pec" attribute
     */
    java.lang.String getMailPec();
    
    /**
     * Gets (as xml) the "mail_pec" attribute
     */
    it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec xgetMailPec();
    
    /**
     * Sets the "mail_pec" attribute
     */
    void setMailPec(java.lang.String mailPec);
    
    /**
     * Sets (as xml) the "mail_pec" attribute
     */
    void xsetMailPec(it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec mailPec);
    
    /**
     * An XML descrizione_documento(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$DescrizioneDocumento.
     */
    public interface DescrizioneDocumento extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DescrizioneDocumento.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s5DE9F8D54B0399C9A54FBA1514CCCE6C").resolveHandle("descrizionedocumento9030attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento newInstance() {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML emettitore(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$Emettitore.
     */
    public interface Emettitore extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Emettitore.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s5DE9F8D54B0399C9A54FBA1514CCCE6C").resolveHandle("emettitore4522attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore newInstance() {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML mail(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$Mail.
     */
    public interface Mail extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Mail.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s5DE9F8D54B0399C9A54FBA1514CCCE6C").resolveHandle("mailfd85attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.Mail newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.Mail) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.Mail newInstance() {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.Mail) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.Mail newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.Mail) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML mail_pec(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$MailPec.
     */
    public interface MailPec extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MailPec.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s5DE9F8D54B0399C9A54FBA1514CCCE6C").resolveHandle("mailpecfb16attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec newInstance() {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.ReqDocType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.ReqDocType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.ReqDocType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
