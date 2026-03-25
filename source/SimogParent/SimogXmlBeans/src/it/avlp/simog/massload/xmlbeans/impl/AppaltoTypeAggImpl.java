/*
 * XML Type:  AppaltoTypeAgg
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AppaltoTypeAgg(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AppaltoTypeAggImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg
{
    
    public AppaltoTypeAggImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AWARDEDNOTICE$0 = 
        new javax.xml.namespace.QName("", "AWARDED_NOTICE");
    private static final javax.xml.namespace.QName CIGAGG$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG_AGG");
    private static final javax.xml.namespace.QName AWARDEDCONTRACT$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AWARDED_CONTRACT");
    private static final javax.xml.namespace.QName PROCUREMENTUNSUCCESSFUL$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PROCUREMENT_UNSUCCESSFUL");
    private static final javax.xml.namespace.QName NBTENDERSRECEIVEDSME$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NB_TENDERS_RECEIVED_SME");
    private static final javax.xml.namespace.QName NBTENDERSRECEIVEDOTHEREU$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NB_TENDERS_RECEIVED_OTHER_EU");
    private static final javax.xml.namespace.QName NBTENDERSRECEIVEDNONEU$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NB_TENDERS_RECEIVED_NON_EU");
    private static final javax.xml.namespace.QName NBTENDERSRECEIVEDEMEANS$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NB_TENDERS_RECEIVED_EMEANS");
    private static final javax.xml.namespace.QName LIKELYSUBCONTRACTED$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LIKELY_SUBCONTRACTED");
    private static final javax.xml.namespace.QName VALSUBCONTRACTING$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_SUBCONTRACTING");
    private static final javax.xml.namespace.QName PCTSUBCONTRACTING$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PCT_SUBCONTRACTING");
    private static final javax.xml.namespace.QName INFOADDSUBCONTRACTING$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INFO_ADD_SUBCONTRACTING");
    private static final javax.xml.namespace.QName DATECONCLUSIONCONTRACT$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATE_CONCLUSION_CONTRACT");
    
    
    /**
     * Gets array of all "AWARDED_NOTICE" elements
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteType[] getAWARDEDNOTICEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AWARDEDNOTICE$0, targetList);
            it.avlp.simog.massload.xmlbeans.ContraenteType[] result = new it.avlp.simog.massload.xmlbeans.ContraenteType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "AWARDED_NOTICE" element
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteType getAWARDEDNOTICEArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteType)get_store().find_element_user(AWARDEDNOTICE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "AWARDED_NOTICE" element
     */
    public int sizeOfAWARDEDNOTICEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AWARDEDNOTICE$0);
        }
    }
    
    /**
     * Sets array of all "AWARDED_NOTICE" element
     */
    public void setAWARDEDNOTICEArray(it.avlp.simog.massload.xmlbeans.ContraenteType[] awardednoticeArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(awardednoticeArray, AWARDEDNOTICE$0);
        }
    }
    
    /**
     * Sets ith "AWARDED_NOTICE" element
     */
    public void setAWARDEDNOTICEArray(int i, it.avlp.simog.massload.xmlbeans.ContraenteType awardednotice)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteType)get_store().find_element_user(AWARDEDNOTICE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(awardednotice);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "AWARDED_NOTICE" element
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteType insertNewAWARDEDNOTICE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteType)get_store().insert_element_user(AWARDEDNOTICE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "AWARDED_NOTICE" element
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteType addNewAWARDEDNOTICE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteType)get_store().add_element_user(AWARDEDNOTICE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "AWARDED_NOTICE" element
     */
    public void removeAWARDEDNOTICE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AWARDEDNOTICE$0, i);
        }
    }
    
    /**
     * Gets the "CIG_AGG" attribute
     */
    public java.lang.String getCIGAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGAGG$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG_AGG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIGAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGAGG$2);
            return target;
        }
    }
    
    /**
     * Sets the "CIG_AGG" attribute
     */
    public void setCIGAGG(java.lang.String cigagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGAGG$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIGAGG$2);
            }
            target.setStringValue(cigagg);
        }
    }
    
    /**
     * Sets (as xml) the "CIG_AGG" attribute
     */
    public void xsetCIGAGG(it.avlp.simog.massload.xmlbeans.CigType cigagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGAGG$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIGAGG$2);
            }
            target.set(cigagg);
        }
    }
    
    /**
     * Gets the "AWARDED_CONTRACT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getAWARDEDCONTRACT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AWARDEDCONTRACT$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "AWARDED_CONTRACT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetAWARDEDCONTRACT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(AWARDEDCONTRACT$4);
            return target;
        }
    }
    
    /**
     * Sets the "AWARDED_CONTRACT" attribute
     */
    public void setAWARDEDCONTRACT(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum awardedcontract)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AWARDEDCONTRACT$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(AWARDEDCONTRACT$4);
            }
            target.setEnumValue(awardedcontract);
        }
    }
    
    /**
     * Sets (as xml) the "AWARDED_CONTRACT" attribute
     */
    public void xsetAWARDEDCONTRACT(it.avlp.simog.massload.xmlbeans.FlagSNType awardedcontract)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(AWARDEDCONTRACT$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(AWARDEDCONTRACT$4);
            }
            target.set(awardedcontract);
        }
    }
    
    /**
     * Gets the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType.Enum getPROCUREMENTUNSUCCESSFUL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROCUREMENTUNSUCCESSFUL$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType xgetPROCUREMENTUNSUCCESSFUL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType)get_store().find_attribute_user(PROCUREMENTUNSUCCESSFUL$6);
            return target;
        }
    }
    
    /**
     * True if has "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    public boolean isSetPROCUREMENTUNSUCCESSFUL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROCUREMENTUNSUCCESSFUL$6) != null;
        }
    }
    
    /**
     * Sets the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    public void setPROCUREMENTUNSUCCESSFUL(it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType.Enum procurementunsuccessful)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROCUREMENTUNSUCCESSFUL$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROCUREMENTUNSUCCESSFUL$6);
            }
            target.setEnumValue(procurementunsuccessful);
        }
    }
    
    /**
     * Sets (as xml) the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    public void xsetPROCUREMENTUNSUCCESSFUL(it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType procurementunsuccessful)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType)get_store().find_attribute_user(PROCUREMENTUNSUCCESSFUL$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType)get_store().add_attribute_user(PROCUREMENTUNSUCCESSFUL$6);
            }
            target.set(procurementunsuccessful);
        }
    }
    
    /**
     * Unsets the "PROCUREMENT_UNSUCCESSFUL" attribute
     */
    public void unsetPROCUREMENTUNSUCCESSFUL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROCUREMENTUNSUCCESSFUL$6);
        }
    }
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_SME" attribute
     */
    public int getNBTENDERSRECEIVEDSME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDSME$8);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_SME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME xgetNBTENDERSRECEIVEDSME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME)get_store().find_attribute_user(NBTENDERSRECEIVEDSME$8);
            return target;
        }
    }
    
    /**
     * True if has "NB_TENDERS_RECEIVED_SME" attribute
     */
    public boolean isSetNBTENDERSRECEIVEDSME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NBTENDERSRECEIVEDSME$8) != null;
        }
    }
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_SME" attribute
     */
    public void setNBTENDERSRECEIVEDSME(int nbtendersreceivedsme)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDSME$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NBTENDERSRECEIVEDSME$8);
            }
            target.setIntValue(nbtendersreceivedsme);
        }
    }
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_SME" attribute
     */
    public void xsetNBTENDERSRECEIVEDSME(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME nbtendersreceivedsme)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME)get_store().find_attribute_user(NBTENDERSRECEIVEDSME$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME)get_store().add_attribute_user(NBTENDERSRECEIVEDSME$8);
            }
            target.set(nbtendersreceivedsme);
        }
    }
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_SME" attribute
     */
    public void unsetNBTENDERSRECEIVEDSME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NBTENDERSRECEIVEDSME$8);
        }
    }
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    public int getNBTENDERSRECEIVEDOTHEREU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDOTHEREU$10);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU xgetNBTENDERSRECEIVEDOTHEREU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU)get_store().find_attribute_user(NBTENDERSRECEIVEDOTHEREU$10);
            return target;
        }
    }
    
    /**
     * True if has "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    public boolean isSetNBTENDERSRECEIVEDOTHEREU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NBTENDERSRECEIVEDOTHEREU$10) != null;
        }
    }
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    public void setNBTENDERSRECEIVEDOTHEREU(int nbtendersreceivedothereu)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDOTHEREU$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NBTENDERSRECEIVEDOTHEREU$10);
            }
            target.setIntValue(nbtendersreceivedothereu);
        }
    }
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    public void xsetNBTENDERSRECEIVEDOTHEREU(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU nbtendersreceivedothereu)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU)get_store().find_attribute_user(NBTENDERSRECEIVEDOTHEREU$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU)get_store().add_attribute_user(NBTENDERSRECEIVEDOTHEREU$10);
            }
            target.set(nbtendersreceivedothereu);
        }
    }
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_OTHER_EU" attribute
     */
    public void unsetNBTENDERSRECEIVEDOTHEREU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NBTENDERSRECEIVEDOTHEREU$10);
        }
    }
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    public int getNBTENDERSRECEIVEDNONEU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDNONEU$12);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU xgetNBTENDERSRECEIVEDNONEU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU)get_store().find_attribute_user(NBTENDERSRECEIVEDNONEU$12);
            return target;
        }
    }
    
    /**
     * True if has "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    public boolean isSetNBTENDERSRECEIVEDNONEU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NBTENDERSRECEIVEDNONEU$12) != null;
        }
    }
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    public void setNBTENDERSRECEIVEDNONEU(int nbtendersreceivednoneu)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDNONEU$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NBTENDERSRECEIVEDNONEU$12);
            }
            target.setIntValue(nbtendersreceivednoneu);
        }
    }
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    public void xsetNBTENDERSRECEIVEDNONEU(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU nbtendersreceivednoneu)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU)get_store().find_attribute_user(NBTENDERSRECEIVEDNONEU$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU)get_store().add_attribute_user(NBTENDERSRECEIVEDNONEU$12);
            }
            target.set(nbtendersreceivednoneu);
        }
    }
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_NON_EU" attribute
     */
    public void unsetNBTENDERSRECEIVEDNONEU()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NBTENDERSRECEIVEDNONEU$12);
        }
    }
    
    /**
     * Gets the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    public int getNBTENDERSRECEIVEDEMEANS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDEMEANS$14);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS xgetNBTENDERSRECEIVEDEMEANS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS)get_store().find_attribute_user(NBTENDERSRECEIVEDEMEANS$14);
            return target;
        }
    }
    
    /**
     * True if has "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    public boolean isSetNBTENDERSRECEIVEDEMEANS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NBTENDERSRECEIVEDEMEANS$14) != null;
        }
    }
    
    /**
     * Sets the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    public void setNBTENDERSRECEIVEDEMEANS(int nbtendersreceivedemeans)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NBTENDERSRECEIVEDEMEANS$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NBTENDERSRECEIVEDEMEANS$14);
            }
            target.setIntValue(nbtendersreceivedemeans);
        }
    }
    
    /**
     * Sets (as xml) the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    public void xsetNBTENDERSRECEIVEDEMEANS(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS nbtendersreceivedemeans)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS)get_store().find_attribute_user(NBTENDERSRECEIVEDEMEANS$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS)get_store().add_attribute_user(NBTENDERSRECEIVEDEMEANS$14);
            }
            target.set(nbtendersreceivedemeans);
        }
    }
    
    /**
     * Unsets the "NB_TENDERS_RECEIVED_EMEANS" attribute
     */
    public void unsetNBTENDERSRECEIVEDEMEANS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NBTENDERSRECEIVEDEMEANS$14);
        }
    }
    
    /**
     * Gets the "LIKELY_SUBCONTRACTED" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getLIKELYSUBCONTRACTED()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIKELYSUBCONTRACTED$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "LIKELY_SUBCONTRACTED" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetLIKELYSUBCONTRACTED()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(LIKELYSUBCONTRACTED$16);
            return target;
        }
    }
    
    /**
     * True if has "LIKELY_SUBCONTRACTED" attribute
     */
    public boolean isSetLIKELYSUBCONTRACTED()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LIKELYSUBCONTRACTED$16) != null;
        }
    }
    
    /**
     * Sets the "LIKELY_SUBCONTRACTED" attribute
     */
    public void setLIKELYSUBCONTRACTED(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum likelysubcontracted)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIKELYSUBCONTRACTED$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LIKELYSUBCONTRACTED$16);
            }
            target.setEnumValue(likelysubcontracted);
        }
    }
    
    /**
     * Sets (as xml) the "LIKELY_SUBCONTRACTED" attribute
     */
    public void xsetLIKELYSUBCONTRACTED(it.avlp.simog.massload.xmlbeans.FlagSNType likelysubcontracted)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(LIKELYSUBCONTRACTED$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(LIKELYSUBCONTRACTED$16);
            }
            target.set(likelysubcontracted);
        }
    }
    
    /**
     * Unsets the "LIKELY_SUBCONTRACTED" attribute
     */
    public void unsetLIKELYSUBCONTRACTED()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LIKELYSUBCONTRACTED$16);
        }
    }
    
    /**
     * Gets the "VAL_SUBCONTRACTING" attribute
     */
    public java.math.BigDecimal getVALSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALSUBCONTRACTING$18);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_SUBCONTRACTING" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetVALSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALSUBCONTRACTING$18);
            return target;
        }
    }
    
    /**
     * True if has "VAL_SUBCONTRACTING" attribute
     */
    public boolean isSetVALSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(VALSUBCONTRACTING$18) != null;
        }
    }
    
    /**
     * Sets the "VAL_SUBCONTRACTING" attribute
     */
    public void setVALSUBCONTRACTING(java.math.BigDecimal valsubcontracting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALSUBCONTRACTING$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALSUBCONTRACTING$18);
            }
            target.setBigDecimalValue(valsubcontracting);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_SUBCONTRACTING" attribute
     */
    public void xsetVALSUBCONTRACTING(it.avlp.simog.massload.xmlbeans.ImportoType valsubcontracting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALSUBCONTRACTING$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(VALSUBCONTRACTING$18);
            }
            target.set(valsubcontracting);
        }
    }
    
    /**
     * Unsets the "VAL_SUBCONTRACTING" attribute
     */
    public void unsetVALSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(VALSUBCONTRACTING$18);
        }
    }
    
    /**
     * Gets the "PCT_SUBCONTRACTING" attribute
     */
    public int getPCTSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PCTSUBCONTRACTING$20);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "PCT_SUBCONTRACTING" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING xgetPCTSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING)get_store().find_attribute_user(PCTSUBCONTRACTING$20);
            return target;
        }
    }
    
    /**
     * True if has "PCT_SUBCONTRACTING" attribute
     */
    public boolean isSetPCTSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PCTSUBCONTRACTING$20) != null;
        }
    }
    
    /**
     * Sets the "PCT_SUBCONTRACTING" attribute
     */
    public void setPCTSUBCONTRACTING(int pctsubcontracting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PCTSUBCONTRACTING$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PCTSUBCONTRACTING$20);
            }
            target.setIntValue(pctsubcontracting);
        }
    }
    
    /**
     * Sets (as xml) the "PCT_SUBCONTRACTING" attribute
     */
    public void xsetPCTSUBCONTRACTING(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING pctsubcontracting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING)get_store().find_attribute_user(PCTSUBCONTRACTING$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING)get_store().add_attribute_user(PCTSUBCONTRACTING$20);
            }
            target.set(pctsubcontracting);
        }
    }
    
    /**
     * Unsets the "PCT_SUBCONTRACTING" attribute
     */
    public void unsetPCTSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PCTSUBCONTRACTING$20);
        }
    }
    
    /**
     * Gets the "INFO_ADD_SUBCONTRACTING" attribute
     */
    public java.lang.String getINFOADDSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOADDSUBCONTRACTING$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "INFO_ADD_SUBCONTRACTING" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING xgetINFOADDSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING)get_store().find_attribute_user(INFOADDSUBCONTRACTING$22);
            return target;
        }
    }
    
    /**
     * True if has "INFO_ADD_SUBCONTRACTING" attribute
     */
    public boolean isSetINFOADDSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INFOADDSUBCONTRACTING$22) != null;
        }
    }
    
    /**
     * Sets the "INFO_ADD_SUBCONTRACTING" attribute
     */
    public void setINFOADDSUBCONTRACTING(java.lang.String infoaddsubcontracting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOADDSUBCONTRACTING$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INFOADDSUBCONTRACTING$22);
            }
            target.setStringValue(infoaddsubcontracting);
        }
    }
    
    /**
     * Sets (as xml) the "INFO_ADD_SUBCONTRACTING" attribute
     */
    public void xsetINFOADDSUBCONTRACTING(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING infoaddsubcontracting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING)get_store().find_attribute_user(INFOADDSUBCONTRACTING$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING)get_store().add_attribute_user(INFOADDSUBCONTRACTING$22);
            }
            target.set(infoaddsubcontracting);
        }
    }
    
    /**
     * Unsets the "INFO_ADD_SUBCONTRACTING" attribute
     */
    public void unsetINFOADDSUBCONTRACTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INFOADDSUBCONTRACTING$22);
        }
    }
    
    /**
     * Gets the "DATE_CONCLUSION_CONTRACT" attribute
     */
    public java.util.Calendar getDATECONCLUSIONCONTRACT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATECONCLUSIONCONTRACT$24);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATE_CONCLUSION_CONTRACT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATECONCLUSIONCONTRACT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATECONCLUSIONCONTRACT$24);
            return target;
        }
    }
    
    /**
     * True if has "DATE_CONCLUSION_CONTRACT" attribute
     */
    public boolean isSetDATECONCLUSIONCONTRACT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATECONCLUSIONCONTRACT$24) != null;
        }
    }
    
    /**
     * Sets the "DATE_CONCLUSION_CONTRACT" attribute
     */
    public void setDATECONCLUSIONCONTRACT(java.util.Calendar dateconclusioncontract)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATECONCLUSIONCONTRACT$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATECONCLUSIONCONTRACT$24);
            }
            target.setCalendarValue(dateconclusioncontract);
        }
    }
    
    /**
     * Sets (as xml) the "DATE_CONCLUSION_CONTRACT" attribute
     */
    public void xsetDATECONCLUSIONCONTRACT(it.avlp.simog.massload.xmlbeans.DbDateType dateconclusioncontract)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATECONCLUSIONCONTRACT$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATECONCLUSIONCONTRACT$24);
            }
            target.set(dateconclusioncontract);
        }
    }
    
    /**
     * Unsets the "DATE_CONCLUSION_CONTRACT" attribute
     */
    public void unsetDATECONCLUSIONCONTRACT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATECONCLUSIONCONTRACT$24);
        }
    }
    /**
     * An XML NB_TENDERS_RECEIVED_SME(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDSME.
     */
    public static class NBTENDERSRECEIVEDSMEImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDSME
    {
        
        public NBTENDERSRECEIVEDSMEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NBTENDERSRECEIVEDSMEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NB_TENDERS_RECEIVED_OTHER_EU(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDOTHEREU.
     */
    public static class NBTENDERSRECEIVEDOTHEREUImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDOTHEREU
    {
        
        public NBTENDERSRECEIVEDOTHEREUImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NBTENDERSRECEIVEDOTHEREUImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NB_TENDERS_RECEIVED_NON_EU(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDNONEU.
     */
    public static class NBTENDERSRECEIVEDNONEUImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDNONEU
    {
        
        public NBTENDERSRECEIVEDNONEUImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NBTENDERSRECEIVEDNONEUImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NB_TENDERS_RECEIVED_EMEANS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$NBTENDERSRECEIVEDEMEANS.
     */
    public static class NBTENDERSRECEIVEDEMEANSImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.NBTENDERSRECEIVEDEMEANS
    {
        
        public NBTENDERSRECEIVEDEMEANSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NBTENDERSRECEIVEDEMEANSImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML PCT_SUBCONTRACTING(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$PCTSUBCONTRACTING.
     */
    public static class PCTSUBCONTRACTINGImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.PCTSUBCONTRACTING
    {
        
        public PCTSUBCONTRACTINGImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected PCTSUBCONTRACTINGImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML INFO_ADD_SUBCONTRACTING(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg$INFOADDSUBCONTRACTING.
     */
    public static class INFOADDSUBCONTRACTINGImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg.INFOADDSUBCONTRACTING
    {
        
        public INFOADDSUBCONTRACTINGImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected INFOADDSUBCONTRACTINGImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
