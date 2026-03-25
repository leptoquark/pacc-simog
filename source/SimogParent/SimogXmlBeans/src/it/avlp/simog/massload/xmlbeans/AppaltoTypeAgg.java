/*
 * XML Type:  AppaltoTypeAgg
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans;


/**
 * An XML AppaltoTypeAgg(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public interface AppaltoTypeAgg extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AppaltoTypeAgg.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("appaltotypeagg8938type");
    
    /**
     * Gets array of all "AWARDED_NOTICE" elements
     */
    it.avlp.simog.massload.xmlbeans.ContraenteType[] getAWARDEDNOTICEArray();
    
    /**
     * Gets ith "AWARDED_NOTICE" element
     */
    it.avlp.simog.massload.xmlbeans.ContraenteType getAWARDEDNOTICEArray(int i);
    
    /**
     * Returns number of "AWARDED_NOTICE" element
     */
    int sizeOfAWARDEDNOTICEArray();
    
    /**
     * Sets array of all "AWARDED_NOTICE" element
     */
    void setAWARDEDNOTICEArray(it.avlp.simog.massload.xmlbeans.ContraenteType[] awardednoticeArray);
    
    /**
     * Sets ith "AWARDED_NOTICE" element
     */
    void setAWARDEDNOTICEArray(int i, it.avlp.simog.massload.xmlbeans.ContraenteType awardednotice);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "AWARDED_NOTICE" element
     */
    it.avlp.simog.massload.xmlbeans.ContraenteType insertNewAWARDEDNOTICE(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "AWARDED_NOTICE" element
     */
    it.avlp.simog.massload.xmlbeans.ContraenteType addNewAWARDEDNOTICE();
    
    /**
     * Removes the ith "AWARDED_NOTICE" element
     */
    void removeAWARDEDNOTICE(int i);
    
    /**
     * Gets the "CIG_AGG" attribute
     */
    java.lang.String getCIGAGG();
    
    /**
     * Gets (as xml) the "CIG_AGG" attribute
     */
    it.avlp.simog.massload.xmlbeans.CigType xgetCIGAGG();
    
    /**
     * Sets the "CIG_AGG" attribute
     */
    void setCIGAGG(java.lang.String cigagg);
    
    /**
     * Sets (as xml) the "CIG_AGG" attribute
     */
    void xsetCIGAGG(it.avlp.simog.massload.xmlbeans.CigType cigagg);
    
    /**
     * Gets the "AWARDED_CONTRACT" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getAWARDEDCONTRACT();
    
    /**
     * Gets (as xml) the "AWARDED_CONTRACT" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetAWARDEDCONTRACT();
    
    /**
     * Sets the "AWARDED_CONTRACT" attribute
     */
    void setAWARDEDCONTRACT(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum awardedcontract);
    
    /**
     * Sets (as xml) the "AWARDED_CONTRACT" attribute
     */
    void xsetAWARDEDCONTRACT(it.avlp.simog.massload.xmlbeans.FlagSNType awardedcontract);
    
    /**
     * Gets the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType.Enum getPROCUREMENTUNSUCCESSFUL();
    
    /**
     * Gets (as xml) the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType xgetPROCUREMENTUNSUCCESSFUL();
    
    /**
     * True if has "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    boolean isSetPROCUREMENTUNSUCCESSFUL();
    
    /**
     * Sets the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    void setPROCUREMENTUNSUCCESSFUL(it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType.Enum procurementunsuccessful);
    
    /**
     * Sets (as xml) the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    void xsetPROCUREMENTUNSUCCESSFUL(it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType procurementunsuccessful);
    
    /**
     * Unsets the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    void unsetPROCUREMENTUNSUCCESSFUL();
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_SME" attribute
     */
    int getNBTENDERSRECEIVEDSME();
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_SME" attribute
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME xgetNBTENDERSRECEIVEDSME();
    
    /**
     * True if has "NB_TENDERS_RECEIVED_SME" attribute
     */
    boolean isSetNBTENDERSRECEIVEDSME();
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_SME" attribute
     */
    void setNBTENDERSRECEIVEDSME(int nbtendersreceivedsme);
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_SME" attribute
     */
    void xsetNBTENDERSRECEIVEDSME(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME nbtendersreceivedsme);
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_SME" attribute
     */
    void unsetNBTENDERSRECEIVEDSME();
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    int getNBTENDERSRECEIVEDOTHEREU();
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU xgetNBTENDERSRECEIVEDOTHEREU();
    
    /**
     * True if has "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    boolean isSetNBTENDERSRECEIVEDOTHEREU();
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    void setNBTENDERSRECEIVEDOTHEREU(int nbtendersreceivedothereu);
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    void xsetNBTENDERSRECEIVEDOTHEREU(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU nbtendersreceivedothereu);
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    void unsetNBTENDERSRECEIVEDOTHEREU();
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    int getNBTENDERSRECEIVEDNONEU();
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU xgetNBTENDERSRECEIVEDNONEU();
    
    /**
     * True if has "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    boolean isSetNBTENDERSRECEIVEDNONEU();
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    void setNBTENDERSRECEIVEDNONEU(int nbtendersreceivednoneu);
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    void xsetNBTENDERSRECEIVEDNONEU(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU nbtendersreceivednoneu);
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    void unsetNBTENDERSRECEIVEDNONEU();
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    int getNBTENDERSRECEIVEDEMEANS();
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS xgetNBTENDERSRECEIVEDEMEANS();
    
    /**
     * True if has "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    boolean isSetNBTENDERSRECEIVEDEMEANS();
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    void setNBTENDERSRECEIVEDEMEANS(int nbtendersreceivedemeans);
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    void xsetNBTENDERSRECEIVEDEMEANS(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS nbtendersreceivedemeans);
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    void unsetNBTENDERSRECEIVEDEMEANS();
    
    /**
     * Gets the "LIKELY_SUBCONTRACTED" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getLIKELYSUBCONTRACTED();
    
    /**
     * Gets (as xml) the "LIKELY_SUBCONTRACTED" attribute
     */
    it.avlp.simog.massload.xmlbeans.FlagSNType xgetLIKELYSUBCONTRACTED();
    
    /**
     * True if has "LIKELY_SUBCONTRACTED" attribute
     */
    boolean isSetLIKELYSUBCONTRACTED();
    
    /**
     * Sets the "LIKELY_SUBCONTRACTED" attribute
     */
    void setLIKELYSUBCONTRACTED(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum likelysubcontracted);
    
    /**
     * Sets (as xml) the "LIKELY_SUBCONTRACTED" attribute
     */
    void xsetLIKELYSUBCONTRACTED(it.avlp.simog.massload.xmlbeans.FlagSNType likelysubcontracted);
    
    /**
     * Unsets the "LIKELY_SUBCONTRACTED" attribute
     */
    void unsetLIKELYSUBCONTRACTED();
    
    /**
     * Gets the "VAL_SUBCONTRACTING" attribute
     */
    java.math.BigDecimal getVALSUBCONTRACTING();
    
    /**
     * Gets (as xml) the "VAL_SUBCONTRACTING" attribute
     */
    it.avlp.simog.massload.xmlbeans.ImportoType xgetVALSUBCONTRACTING();
    
    /**
     * True if has "VAL_SUBCONTRACTING" attribute
     */
    boolean isSetVALSUBCONTRACTING();
    
    /**
     * Sets the "VAL_SUBCONTRACTING" attribute
     */
    void setVALSUBCONTRACTING(java.math.BigDecimal valsubcontracting);
    
    /**
     * Sets (as xml) the "VAL_SUBCONTRACTING" attribute
     */
    void xsetVALSUBCONTRACTING(it.avlp.simog.massload.xmlbeans.ImportoType valsubcontracting);
    
    /**
     * Unsets the "VAL_SUBCONTRACTING" attribute
     */
    void unsetVALSUBCONTRACTING();
    
    /**
     * Gets the "PCT_SUBCONTRACTING" attribute
     */
    int getPCTSUBCONTRACTING();
    
    /**
     * Gets (as xml) the "PCT_SUBCONTRACTING" attribute
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING xgetPCTSUBCONTRACTING();
    
    /**
     * True if has "PCT_SUBCONTRACTING" attribute
     */
    boolean isSetPCTSUBCONTRACTING();
    
    /**
     * Sets the "PCT_SUBCONTRACTING" attribute
     */
    void setPCTSUBCONTRACTING(int pctsubcontracting);
    
    /**
     * Sets (as xml) the "PCT_SUBCONTRACTING" attribute
     */
    void xsetPCTSUBCONTRACTING(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING pctsubcontracting);
    
    /**
     * Unsets the "PCT_SUBCONTRACTING" attribute
     */
    void unsetPCTSUBCONTRACTING();
    
    /**
     * Gets the "INFO_ADD_SUBCONTRACTING" attribute
     */
    java.lang.String getINFOADDSUBCONTRACTING();
    
    /**
     * Gets (as xml) the "INFO_ADD_SUBCONTRACTING" attribute
     */
    it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING xgetINFOADDSUBCONTRACTING();
    
    /**
     * True if has "INFO_ADD_SUBCONTRACTING" attribute
     */
    boolean isSetINFOADDSUBCONTRACTING();
    
    /**
     * Sets the "INFO_ADD_SUBCONTRACTING" attribute
     */
    void setINFOADDSUBCONTRACTING(java.lang.String infoaddsubcontracting);
    
    /**
     * Sets (as xml) the "INFO_ADD_SUBCONTRACTING" attribute
     */
    void xsetINFOADDSUBCONTRACTING(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING infoaddsubcontracting);
    
    /**
     * Unsets the "INFO_ADD_SUBCONTRACTING" attribute
     */
    void unsetINFOADDSUBCONTRACTING();
    
    /**
     * Gets the "DATE_CONCLUSION_CONTRACT" attribute
     */
    java.util.Calendar getDATECONCLUSIONCONTRACT();
    
    /**
     * Gets (as xml) the "DATE_CONCLUSION_CONTRACT" attribute
     */
    it.avlp.simog.massload.xmlbeans.DbDateType xgetDATECONCLUSIONCONTRACT();
    
    /**
     * True if has "DATE_CONCLUSION_CONTRACT" attribute
     */
    boolean isSetDATECONCLUSIONCONTRACT();
    
    /**
     * Sets the "DATE_CONCLUSION_CONTRACT" attribute
     */
    void setDATECONCLUSIONCONTRACT(java.util.Calendar dateconclusioncontract);
    
    /**
     * Sets (as xml) the "DATE_CONCLUSION_CONTRACT" attribute
     */
    void xsetDATECONCLUSIONCONTRACT(it.avlp.simog.massload.xmlbeans.DbDateType dateconclusioncontract);
    
    /**
     * Unsets the "DATE_CONCLUSION_CONTRACT" attribute
     */
    void unsetDATECONCLUSIONCONTRACT();
    
    /**
     * An XML NB_TENDERS_RECEIVED_SME(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDSME.
     */
    public interface NBTENDERSRECEIVEDSME extends org.apache.xmlbeans.XmlInt
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NBTENDERSRECEIVEDSME.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("nbtendersreceivedsmebf08attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML NB_TENDERS_RECEIVED_OTHER_EU(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDOTHEREU.
     */
    public interface NBTENDERSRECEIVEDOTHEREU extends org.apache.xmlbeans.XmlInt
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NBTENDERSRECEIVEDOTHEREU.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("nbtendersreceivedothereu4b76attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML NB_TENDERS_RECEIVED_NON_EU(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDNONEU.
     */
    public interface NBTENDERSRECEIVEDNONEU extends org.apache.xmlbeans.XmlInt
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NBTENDERSRECEIVEDNONEU.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("nbtendersreceivednoneu9af3attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML NB_TENDERS_RECEIVED_EMEANS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDEMEANS.
     */
    public interface NBTENDERSRECEIVEDEMEANS extends org.apache.xmlbeans.XmlInt
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NBTENDERSRECEIVEDEMEANS.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("nbtendersreceivedemeans094cattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML PCT_SUBCONTRACTING(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$PCTSUBCONTRACTING.
     */
    public interface PCTSUBCONTRACTING extends org.apache.xmlbeans.XmlInt
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(PCTSUBCONTRACTING.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("pctsubcontracting3f5aattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * An XML INFO_ADD_SUBCONTRACTING(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$INFOADDSUBCONTRACTING.
     */
    public interface INFOADDSUBCONTRACTING extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(INFOADDSUBCONTRACTING.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s36328917151C07A83E2A5DDFFEA9F1B7").resolveHandle("infoaddsubcontractinge7a1attrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING newValue(java.lang.Object obj) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING) type.newValue( obj ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING newInstance() {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg newInstance() {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
