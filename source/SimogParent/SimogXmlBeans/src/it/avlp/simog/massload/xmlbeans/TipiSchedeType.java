/*
 * XML Type:  TipiSchedeType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TipiSchedeType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML TipiSchedeType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.TipiSchedeType.
 */
public interface TipiSchedeType extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(TipiSchedeType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("tipischedetypeac0etype");
    
    org.apache.xmlbeans.StringEnumAbstractBase enumValue();
    void set(org.apache.xmlbeans.StringEnumAbstractBase e);
    
    static final Enum DATI_COMUNI = Enum.forString("DATI_COMUNI");
    static final Enum AGGIUDICAZIONE = Enum.forString("AGGIUDICAZIONE");
    static final Enum ESCLUSO = Enum.forString("ESCLUSO");
    static final Enum SOTTOSOGLIA = Enum.forString("SOTTOSOGLIA");
    static final Enum ADESIONE = Enum.forString("ADESIONE");
    static final Enum FASE_INIZIALE = Enum.forString("FASE_INIZIALE");
    static final Enum STIPULA = Enum.forString("STIPULA");
    static final Enum STATO_AVANZAMENTO = Enum.forString("STATO_AVANZAMENTO");
    static final Enum FINE_LAVORI = Enum.forString("FINE_LAVORI");
    static final Enum COLLAUDO = Enum.forString("COLLAUDO");
    static final Enum IPOTESI_RECESSO = Enum.forString("IPOTESI_RECESSO");
    static final Enum ACCORDO_BONARIO = Enum.forString("ACCORDO_BONARIO");
    static final Enum SOSPENSIONE = Enum.forString("SOSPENSIONE");
    static final Enum VARIANTE = Enum.forString("VARIANTE");
    static final Enum SUBAPPALTO = Enum.forString("SUBAPPALTO");
    
    static final int INT_DATI_COMUNI = Enum.INT_DATI_COMUNI;
    static final int INT_AGGIUDICAZIONE = Enum.INT_AGGIUDICAZIONE;
    static final int INT_ESCLUSO = Enum.INT_ESCLUSO;
    static final int INT_SOTTOSOGLIA = Enum.INT_SOTTOSOGLIA;
    static final int INT_ADESIONE = Enum.INT_ADESIONE;
    static final int INT_FASE_INIZIALE = Enum.INT_FASE_INIZIALE;
    static final int INT_STIPULA = Enum.INT_STIPULA;
    static final int INT_STATO_AVANZAMENTO = Enum.INT_STATO_AVANZAMENTO;
    static final int INT_FINE_LAVORI = Enum.INT_FINE_LAVORI;
    static final int INT_COLLAUDO = Enum.INT_COLLAUDO;
    static final int INT_IPOTESI_RECESSO = Enum.INT_IPOTESI_RECESSO;
    static final int INT_ACCORDO_BONARIO = Enum.INT_ACCORDO_BONARIO;
    static final int INT_SOSPENSIONE = Enum.INT_SOSPENSIONE;
    static final int INT_VARIANTE = Enum.INT_VARIANTE;
    static final int INT_SUBAPPALTO = Enum.INT_SUBAPPALTO;
    
    /**
     * Enumeration value class for it.avlp.simog.massload.xmlbeans.TipiSchedeType.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_DATI_COMUNI
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    static final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase
    {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s)
            { return (Enum)table.forString(s); }
        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i)
            { return (Enum)table.forInt(i); }
        
        private Enum(java.lang.String s, int i)
            { super(s, i); }
        
        static final int INT_DATI_COMUNI = 1;
        static final int INT_AGGIUDICAZIONE = 2;
        static final int INT_ESCLUSO = 3;
        static final int INT_SOTTOSOGLIA = 4;
        static final int INT_ADESIONE = 5;
        static final int INT_FASE_INIZIALE = 6;
        static final int INT_STIPULA = 7;
        static final int INT_STATO_AVANZAMENTO = 8;
        static final int INT_FINE_LAVORI = 9;
        static final int INT_COLLAUDO = 10;
        static final int INT_IPOTESI_RECESSO = 11;
        static final int INT_ACCORDO_BONARIO = 12;
        static final int INT_SOSPENSIONE = 13;
        static final int INT_VARIANTE = 14;
        static final int INT_SUBAPPALTO = 15;
        
        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table
        (
            new Enum[]
            {
                new Enum("DATI_COMUNI", INT_DATI_COMUNI),
                new Enum("AGGIUDICAZIONE", INT_AGGIUDICAZIONE),
                new Enum("ESCLUSO", INT_ESCLUSO),
                new Enum("SOTTOSOGLIA", INT_SOTTOSOGLIA),
                new Enum("ADESIONE", INT_ADESIONE),
                new Enum("FASE_INIZIALE", INT_FASE_INIZIALE),
                new Enum("STIPULA", INT_STIPULA),
                new Enum("STATO_AVANZAMENTO", INT_STATO_AVANZAMENTO),
                new Enum("FINE_LAVORI", INT_FINE_LAVORI),
                new Enum("COLLAUDO", INT_COLLAUDO),
                new Enum("IPOTESI_RECESSO", INT_IPOTESI_RECESSO),
                new Enum("ACCORDO_BONARIO", INT_ACCORDO_BONARIO),
                new Enum("SOSPENSIONE", INT_SOSPENSIONE),
                new Enum("VARIANTE", INT_VARIANTE),
                new Enum("SUBAPPALTO", INT_SUBAPPALTO),
            }
        );
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() { return forInt(intValue()); } 
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType newValue(java.lang.Object obj) {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) type.newValue( obj ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.TipiSchedeType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.TipiSchedeType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
