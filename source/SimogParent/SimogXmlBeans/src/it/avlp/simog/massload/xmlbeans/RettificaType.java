/*
 * XML Type:  RettificaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RettificaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML RettificaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface RettificaType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RettificaType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("rettificatypef2b7type");
    
    /**
     * Gets array of all "RETTIFICA_CPV_SEC" elements
     */
    it.avlp.simog.massload.xmlbeans.RettificaCpvSecType[] getRETTIFICACPVSECArray();
    
    /**
     * Gets ith "RETTIFICA_CPV_SEC" element
     */
    it.avlp.simog.massload.xmlbeans.RettificaCpvSecType getRETTIFICACPVSECArray(int i);
    
    /**
     * Returns number of "RETTIFICA_CPV_SEC" element
     */
    int sizeOfRETTIFICACPVSECArray();
    
    /**
     * Sets array of all "RETTIFICA_CPV_SEC" element
     */
    void setRETTIFICACPVSECArray(it.avlp.simog.massload.xmlbeans.RettificaCpvSecType[] rettificacpvsecArray);
    
    /**
     * Sets ith "RETTIFICA_CPV_SEC" element
     */
    void setRETTIFICACPVSECArray(int i, it.avlp.simog.massload.xmlbeans.RettificaCpvSecType rettificacpvsec);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "RETTIFICA_CPV_SEC" element
     */
    it.avlp.simog.massload.xmlbeans.RettificaCpvSecType insertNewRETTIFICACPVSEC(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "RETTIFICA_CPV_SEC" element
     */
    it.avlp.simog.massload.xmlbeans.RettificaCpvSecType addNewRETTIFICACPVSEC();
    
    /**
     * Removes the ith "RETTIFICA_CPV_SEC" element
     */
    void removeRETTIFICACPVSEC(int i);
    
    /**
     * Gets the "SECTION_NUMBER" attribute
     */
    java.lang.String getSECTIONNUMBER();
    
    /**
     * Gets (as xml) the "SECTION_NUMBER" attribute
     */
    it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER xgetSECTIONNUMBER();
    
    /**
     * Sets the "SECTION_NUMBER" attribute
     */
    void setSECTIONNUMBER(java.lang.String sectionnumber);
    
    /**
     * Sets (as xml) the "SECTION_NUMBER" attribute
     */
    void xsetSECTIONNUMBER(it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER sectionnumber);
    
    /**
     * Gets the "CIG_RETTIFICA" attribute
     */
    java.lang.String getCIGRETTIFICA();
    
    /**
     * Gets (as xml) the "CIG_RETTIFICA" attribute
     */
    it.avlp.simog.massload.xmlbeans.CigType xgetCIGRETTIFICA();
    
    /**
     * True if has "CIG_RETTIFICA" attribute
     */
    boolean isSetCIGRETTIFICA();
    
    /**
     * Sets the "CIG_RETTIFICA" attribute
     */
    void setCIGRETTIFICA(java.lang.String cigrettifica);
    
    /**
     * Sets (as xml) the "CIG_RETTIFICA" attribute
     */
    void xsetCIGRETTIFICA(it.avlp.simog.massload.xmlbeans.CigType cigrettifica);
    
    /**
     * Unsets the "CIG_RETTIFICA" attribute
     */
    void unsetCIGRETTIFICA();
    
    /**
     * Gets the "SECTION_TO_MODIFY" attribute
     */
    java.lang.String getSECTIONTOMODIFY();
    
    /**
     * Gets (as xml) the "SECTION_TO_MODIFY" attribute
     */
    it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY xgetSECTIONTOMODIFY();
    
    /**
     * True if has "SECTION_TO_MODIFY" attribute
     */
    boolean isSetSECTIONTOMODIFY();
    
    /**
     * Sets the "SECTION_TO_MODIFY" attribute
     */
    void setSECTIONTOMODIFY(java.lang.String sectiontomodify);
    
    /**
     * Sets (as xml) the "SECTION_TO_MODIFY" attribute
     */
    void xsetSECTIONTOMODIFY(it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY sectiontomodify);
    
    /**
     * Unsets the "SECTION_TO_MODIFY" attribute
     */
    void unsetSECTIONTOMODIFY();
    
    /**
     * Gets the "OLD_VALUE_TEXT" attribute
     */
    java.lang.String getOLDVALUETEXT();
    
    /**
     * Gets (as xml) the "OLD_VALUE_TEXT" attribute
     */
    it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT xgetOLDVALUETEXT();
    
    /**
     * True if has "OLD_VALUE_TEXT" attribute
     */
    boolean isSetOLDVALUETEXT();
    
    /**
     * Sets the "OLD_VALUE_TEXT" attribute
     */
    void setOLDVALUETEXT(java.lang.String oldvaluetext);
    
    /**
     * Sets (as xml) the "OLD_VALUE_TEXT" attribute
     */
    void xsetOLDVALUETEXT(it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT oldvaluetext);
    
    /**
     * Unsets the "OLD_VALUE_TEXT" attribute
     */
    void unsetOLDVALUETEXT();
    
    /**
     * Gets the "NEW_VALUE_TEXT" attribute
     */
    java.lang.String getNEWVALUETEXT();
    
    /**
     * Gets (as xml) the "NEW_VALUE_TEXT" attribute
     */
    it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT xgetNEWVALUETEXT();
    
    /**
     * True if has "NEW_VALUE_TEXT" attribute
     */
    boolean isSetNEWVALUETEXT();
    
    /**
     * Sets the "NEW_VALUE_TEXT" attribute
     */
    void setNEWVALUETEXT(java.lang.String newvaluetext);
    
    /**
     * Sets (as xml) the "NEW_VALUE_TEXT" attribute
     */
    void xsetNEWVALUETEXT(it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT newvaluetext);
    
    /**
     * Unsets the "NEW_VALUE_TEXT" attribute
     */
    void unsetNEWVALUETEXT();
    
    /**
     * Gets the "OLD_MAIN_CPV" attribute
     */
    java.lang.String getOLDMAINCPV();
    
    /**
     * Gets (as xml) the "OLD_MAIN_CPV" attribute
     */
    it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV xgetOLDMAINCPV();
    
    /**
     * True if has "OLD_MAIN_CPV" attribute
     */
    boolean isSetOLDMAINCPV();
    
    /**
     * Sets the "OLD_MAIN_CPV" attribute
     */
    void setOLDMAINCPV(java.lang.String oldmaincpv);
    
    /**
     * Sets (as xml) the "OLD_MAIN_CPV" attribute
     */
    void xsetOLDMAINCPV(it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV oldmaincpv);
    
    /**
     * Unsets the "OLD_MAIN_CPV" attribute
     */
    void unsetOLDMAINCPV();
    
    /**
     * Gets the "NEW_MAIN_CPV" attribute
     */
    java.lang.String getNEWMAINCPV();
    
    /**
     * Gets (as xml) the "NEW_MAIN_CPV" attribute
     */
    it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV xgetNEWMAINCPV();
    
    /**
     * True if has "NEW_MAIN_CPV" attribute
     */
    boolean isSetNEWMAINCPV();
    
    /**
     * Sets the "NEW_MAIN_CPV" attribute
     */
    void setNEWMAINCPV(java.lang.String newmaincpv);
    
    /**
     * Sets (as xml) the "NEW_MAIN_CPV" attribute
     */
    void xsetNEWMAINCPV(it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV newmaincpv);
    
    /**
     * Unsets the "NEW_MAIN_CPV" attribute
     */
    void unsetNEWMAINCPV();
    
    /**
     * Gets the "OLD_VALUE_DATE" attribute
     */
    java.util.Calendar getOLDVALUEDATE();
    
    /**
     * Gets (as xml) the "OLD_VALUE_DATE" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetOLDVALUEDATE();
    
    /**
     * True if has "OLD_VALUE_DATE" attribute
     */
    boolean isSetOLDVALUEDATE();
    
    /**
     * Sets the "OLD_VALUE_DATE" attribute
     */
    void setOLDVALUEDATE(java.util.Calendar oldvaluedate);
    
    /**
     * Sets (as xml) the "OLD_VALUE_DATE" attribute
     */
    void xsetOLDVALUEDATE(it.avlp.simog.massload.xmlbeans.DbDateType oldvaluedate);
    
    /**
     * Unsets the "OLD_VALUE_DATE" attribute
     */
    void unsetOLDVALUEDATE();
    
    /**
     * Gets the "OLD_VALUE_TIME" attribute
     */
    java.lang.String getOLDVALUETIME();
    
    /**
     * Gets (as xml) the "OLD_VALUE_TIME" attribute
     */
    it.avlp.simog.massload.xmlbeans.Time xgetOLDVALUETIME();
    
    /**
     * True if has "OLD_VALUE_TIME" attribute
     */
    boolean isSetOLDVALUETIME();
    
    /**
     * Sets the "OLD_VALUE_TIME" attribute
     */
    void setOLDVALUETIME(java.lang.String oldvaluetime);
    
    /**
     * Sets (as xml) the "OLD_VALUE_TIME" attribute
     */
    void xsetOLDVALUETIME(it.avlp.simog.massload.xmlbeans.Time oldvaluetime);
    
    /**
     * Unsets the "OLD_VALUE_TIME" attribute
     */
    void unsetOLDVALUETIME();
    
    /**
     * Gets the "NEW_VALUE_DATE" attribute
     */
    java.util.Calendar getNEWVALUEDATE();
    
    /**
     * Gets (as xml) the "NEW_VALUE_DATE" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetNEWVALUEDATE();
    
    /**
     * True if has "NEW_VALUE_DATE" attribute
     */
    boolean isSetNEWVALUEDATE();
    
    /**
     * Sets the "NEW_VALUE_DATE" attribute
     */
    void setNEWVALUEDATE(java.util.Calendar newvaluedate);
    
    /**
     * Sets (as xml) the "NEW_VALUE_DATE" attribute
     */
    void xsetNEWVALUEDATE(it.avlp.simog.massload.xmlbeans.DbDateType newvaluedate);
    
    /**
     * Unsets the "NEW_VALUE_DATE" attribute
     */
    void unsetNEWVALUEDATE();
    
    /**
     * Gets the "NEW_VALUE_TIME" attribute
     */
    java.lang.String getNEWVALUETIME();
    
    /**
     * Gets (as xml) the "NEW_VALUE_TIME" attribute
     */
    it.avlp.simog.massload.xmlbeans.Time xgetNEWVALUETIME();
    
    /**
     * True if has "NEW_VALUE_TIME" attribute
     */
    boolean isSetNEWVALUETIME();
    
    /**
     * Sets the "NEW_VALUE_TIME" attribute
     */
    void setNEWVALUETIME(java.lang.String newvaluetime);
    
    /**
     * Sets (as xml) the "NEW_VALUE_TIME" attribute
     */
    void xsetNEWVALUETIME(it.avlp.simog.massload.xmlbeans.Time newvaluetime);
    
    /**
     * Unsets the "NEW_VALUE_TIME" attribute
     */
    void unsetNEWVALUETIME();
    
    /**
     * An XML SECTION_NUMBER(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$SECTIONNUMBER.
     */
    public interface SECTIONNUMBER extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SECTIONNUMBER.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("sectionnumberefcaattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER newInstance() {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML SECTION_TO_MODIFY(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$SECTIONTOMODIFY.
     */
    public interface SECTIONTOMODIFY extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SECTIONTOMODIFY.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("sectiontomodify1c33attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY newInstance() {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML OLD_VALUE_TEXT(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$OLDVALUETEXT.
     */
    public interface OLDVALUETEXT extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OLDVALUETEXT.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("oldvaluetext765aattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT newInstance() {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML NEW_VALUE_TEXT(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$NEWVALUETEXT.
     */
    public interface NEWVALUETEXT extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NEWVALUETEXT.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("newvaluetextbb41attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT newInstance() {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML OLD_MAIN_CPV(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$OLDMAINCPV.
     */
    public interface OLDMAINCPV extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OLDMAINCPV.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("oldmaincpv6e02attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV newInstance() {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML NEW_MAIN_CPV(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$NEWMAINCPV.
     */
    public interface NEWMAINCPV extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NEWMAINCPV.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("newmaincpvc0a9attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV newInstance() {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.RettificaType newInstance() {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.RettificaType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.RettificaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
