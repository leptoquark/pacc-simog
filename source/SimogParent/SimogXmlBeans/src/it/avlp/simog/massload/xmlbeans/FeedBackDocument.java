/*
 * An XML document type.
 * Localname: FeedBack
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FeedBackDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * A document containing one FeedBack(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public interface FeedBackDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FeedBackDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("feedback79cddoctype");
    
    /**
     * Gets the "FeedBack" element
     */
    it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack getFeedBack();
    
    /**
     * Sets the "FeedBack" element
     */
    void setFeedBack(it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack feedBack);
    
    /**
     * Appends and returns a new empty "FeedBack" element
     */
    it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack addNewFeedBack();
    
    /**
     * An XML FeedBack(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public interface FeedBack extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FeedBack.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("feedback32ccelemtype");
        
        /**
         * Gets the "InfoFlusso" element
         */
        it.avlp.simog.massload.xmlbeans.FlussoType getInfoFlusso();
        
        /**
         * Sets the "InfoFlusso" element
         */
        void setInfoFlusso(it.avlp.simog.massload.xmlbeans.FlussoType infoFlusso);
        
        /**
         * Appends and returns a new empty "InfoFlusso" element
         */
        it.avlp.simog.massload.xmlbeans.FlussoType addNewInfoFlusso();
        
        /**
         * Gets array of all "AnomalieSchede" elements
         */
        it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede[] getAnomalieSchedeArray();
        
        /**
         * Gets ith "AnomalieSchede" element
         */
        it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede getAnomalieSchedeArray(int i);
        
        /**
         * Returns number of "AnomalieSchede" element
         */
        int sizeOfAnomalieSchedeArray();
        
        /**
         * Sets array of all "AnomalieSchede" element
         */
        void setAnomalieSchedeArray(it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede[] anomalieSchedeArray);
        
        /**
         * Sets ith "AnomalieSchede" element
         */
        void setAnomalieSchedeArray(int i, it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede anomalieSchede);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "AnomalieSchede" element
         */
        it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede insertNewAnomalieSchede(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "AnomalieSchede" element
         */
        it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede addNewAnomalieSchede();
        
        /**
         * Removes the ith "AnomalieSchede" element
         */
        void removeAnomalieSchede(int i);
        
        /**
         * An XML AnomalieSchede(@).
         *
         * This is a complex type.
         */
        public interface AnomalieSchede extends it.avlp.simog.massload.xmlbeans.AnomSchedaAType
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AnomalieSchede.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("anomalieschede9d78elemtype");
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede newInstance() {
                  return (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack newInstance() {
              return (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument newInstance() {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.FeedBackDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.FeedBackDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
