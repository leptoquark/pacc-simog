/*
 * An XML document type.
 * Localname: TrasferimentoDati
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * A document containing one TrasferimentoDati(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public interface TrasferimentoDatiDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(TrasferimentoDatiDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("trasferimentodatif193doctype");
    
    /**
     * Gets the "TrasferimentoDati" element
     */
    it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati getTrasferimentoDati();
    
    /**
     * Sets the "TrasferimentoDati" element
     */
    void setTrasferimentoDati(it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati trasferimentoDati);
    
    /**
     * Appends and returns a new empty "TrasferimentoDati" element
     */
    it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati addNewTrasferimentoDati();
    
    /**
     * An XML TrasferimentoDati(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public interface TrasferimentoDati extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(TrasferimentoDati.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6D579C1799740018F66FF5159B80DC6A").resolveHandle("trasferimentodati03dcelemtype");
        
        /**
         * Gets the "InfoTrasferimento" element
         */
        it.avlp.simog.massload.xmlbeans.TrasferimentoType getInfoTrasferimento();
        
        /**
         * Sets the "InfoTrasferimento" element
         */
        void setInfoTrasferimento(it.avlp.simog.massload.xmlbeans.TrasferimentoType infoTrasferimento);
        
        /**
         * Appends and returns a new empty "InfoTrasferimento" element
         */
        it.avlp.simog.massload.xmlbeans.TrasferimentoType addNewInfoTrasferimento();
        
        /**
         * Gets array of all "Schede" elements
         */
        it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[] getSchedeArray();
        
        /**
         * Gets ith "Schede" element
         */
        it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType getSchedeArray(int i);
        
        /**
         * Returns number of "Schede" element
         */
        int sizeOfSchedeArray();
        
        /**
         * Sets array of all "Schede" element
         */
        void setSchedeArray(it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[] schedeArray);
        
        /**
         * Sets ith "Schede" element
         */
        void setSchedeArray(int i, it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType schede);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "Schede" element
         */
        it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType insertNewSchede(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "Schede" element
         */
        it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType addNewSchede();
        
        /**
         * Removes the ith "Schede" element
         */
        void removeSchede(int i);
        
        /**
         * Gets array of all "SchedeEliminate" elements
         */
        it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType[] getSchedeEliminateArray();
        
        /**
         * Gets ith "SchedeEliminate" element
         */
        it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType getSchedeEliminateArray(int i);
        
        /**
         * Returns number of "SchedeEliminate" element
         */
        int sizeOfSchedeEliminateArray();
        
        /**
         * Sets array of all "SchedeEliminate" element
         */
        void setSchedeEliminateArray(it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType[] schedeEliminateArray);
        
        /**
         * Sets ith "SchedeEliminate" element
         */
        void setSchedeEliminateArray(int i, it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType schedeEliminate);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "SchedeEliminate" element
         */
        it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType insertNewSchedeEliminate(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "SchedeEliminate" element
         */
        it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType addNewSchedeEliminate();
        
        /**
         * Removes the ith "SchedeEliminate" element
         */
        void removeSchedeEliminate(int i);
        
        /**
         * Gets array of all "VariazioniAnag" elements
         */
        it.avlp.simog.massload.xmlbeans.VarAnagType[] getVariazioniAnagArray();
        
        /**
         * Gets ith "VariazioniAnag" element
         */
        it.avlp.simog.massload.xmlbeans.VarAnagType getVariazioniAnagArray(int i);
        
        /**
         * Returns number of "VariazioniAnag" element
         */
        int sizeOfVariazioniAnagArray();
        
        /**
         * Sets array of all "VariazioniAnag" element
         */
        void setVariazioniAnagArray(it.avlp.simog.massload.xmlbeans.VarAnagType[] variazioniAnagArray);
        
        /**
         * Sets ith "VariazioniAnag" element
         */
        void setVariazioniAnagArray(int i, it.avlp.simog.massload.xmlbeans.VarAnagType variazioniAnag);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "VariazioniAnag" element
         */
        it.avlp.simog.massload.xmlbeans.VarAnagType insertNewVariazioniAnag(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "VariazioniAnag" element
         */
        it.avlp.simog.massload.xmlbeans.VarAnagType addNewVariazioniAnag();
        
        /**
         * Removes the ith "VariazioniAnag" element
         */
        void removeVariazioniAnag(int i);
        
        /**
         * Gets the "Responsabili" element
         */
        it.avlp.simog.massload.xmlbeans.ResponsabiliType getResponsabili();
        
        /**
         * True if has "Responsabili" element
         */
        boolean isSetResponsabili();
        
        /**
         * Sets the "Responsabili" element
         */
        void setResponsabili(it.avlp.simog.massload.xmlbeans.ResponsabiliType responsabili);
        
        /**
         * Appends and returns a new empty "Responsabili" element
         */
        it.avlp.simog.massload.xmlbeans.ResponsabiliType addNewResponsabili();
        
        /**
         * Unsets the "Responsabili" element
         */
        void unsetResponsabili();
        
        /**
         * Gets the "Aggiudicatari" element
         */
        it.avlp.simog.massload.xmlbeans.AggiudicatariType getAggiudicatari();
        
        /**
         * True if has "Aggiudicatari" element
         */
        boolean isSetAggiudicatari();
        
        /**
         * Sets the "Aggiudicatari" element
         */
        void setAggiudicatari(it.avlp.simog.massload.xmlbeans.AggiudicatariType aggiudicatari);
        
        /**
         * Appends and returns a new empty "Aggiudicatari" element
         */
        it.avlp.simog.massload.xmlbeans.AggiudicatariType addNewAggiudicatari();
        
        /**
         * Unsets the "Aggiudicatari" element
         */
        void unsetAggiudicatari();
        
        /**
         * Gets the "VariazioniSA" element
         */
        it.avlp.simog.massload.xmlbeans.VariazioneSAType getVariazioniSA();
        
        /**
         * True if has "VariazioniSA" element
         */
        boolean isSetVariazioniSA();
        
        /**
         * Sets the "VariazioniSA" element
         */
        void setVariazioniSA(it.avlp.simog.massload.xmlbeans.VariazioneSAType variazioniSA);
        
        /**
         * Appends and returns a new empty "VariazioniSA" element
         */
        it.avlp.simog.massload.xmlbeans.VariazioneSAType addNewVariazioniSA();
        
        /**
         * Unsets the "VariazioniSA" element
         */
        void unsetVariazioniSA();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati newInstance() {
              return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument newInstance() {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
