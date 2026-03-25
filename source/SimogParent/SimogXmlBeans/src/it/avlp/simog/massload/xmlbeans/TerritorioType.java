/*
 * XML Type:  TerritorioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TerritorioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML TerritorioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface TerritorioType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(TerritorioType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41BFE58B4F2A4AA49D01B2AF95803561").resolveHandle("territoriotype3bd7type");
    
    /**
     * Gets array of all "CodRegioneIstat" elements
     */
    java.lang.String[] getCodRegioneIstatArray();
    
    /**
     * Gets ith "CodRegioneIstat" element
     */
    java.lang.String getCodRegioneIstatArray(int i);
    
    /**
     * Gets (as xml) array of all "CodRegioneIstat" elements
     */
    it.avlp.simog.massload.xmlbeans.LuogoIstatType[] xgetCodRegioneIstatArray();
    
    /**
     * Gets (as xml) ith "CodRegioneIstat" element
     */
    it.avlp.simog.massload.xmlbeans.LuogoIstatType xgetCodRegioneIstatArray(int i);
    
    /**
     * Returns number of "CodRegioneIstat" element
     */
    int sizeOfCodRegioneIstatArray();
    
    /**
     * Sets array of all "CodRegioneIstat" element
     */
    void setCodRegioneIstatArray(java.lang.String[] codRegioneIstatArray);
    
    /**
     * Sets ith "CodRegioneIstat" element
     */
    void setCodRegioneIstatArray(int i, java.lang.String codRegioneIstat);
    
    /**
     * Sets (as xml) array of all "CodRegioneIstat" element
     */
    void xsetCodRegioneIstatArray(it.avlp.simog.massload.xmlbeans.LuogoIstatType[] codRegioneIstatArray);
    
    /**
     * Sets (as xml) ith "CodRegioneIstat" element
     */
    void xsetCodRegioneIstatArray(int i, it.avlp.simog.massload.xmlbeans.LuogoIstatType codRegioneIstat);
    
    /**
     * Inserts the value as the ith "CodRegioneIstat" element
     */
    void insertCodRegioneIstat(int i, java.lang.String codRegioneIstat);
    
    /**
     * Appends the value as the last "CodRegioneIstat" element
     */
    void addCodRegioneIstat(java.lang.String codRegioneIstat);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CodRegioneIstat" element
     */
    it.avlp.simog.massload.xmlbeans.LuogoIstatType insertNewCodRegioneIstat(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CodRegioneIstat" element
     */
    it.avlp.simog.massload.xmlbeans.LuogoIstatType addNewCodRegioneIstat();
    
    /**
     * Removes the ith "CodRegioneIstat" element
     */
    void removeCodRegioneIstat(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.TerritorioType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.TerritorioType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.TerritorioType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
