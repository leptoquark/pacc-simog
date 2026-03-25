/*
 * XML Type:  InizioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.InizioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML InizioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class InizioTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.InizioType
{
    
    public InizioTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATASTIPULA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_STIPULA");
    private static final javax.xml.namespace.QName DATAESECUTIVITA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_ESECUTIVITA");
    private static final javax.xml.namespace.QName IMPORTOCAUZIONE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_CAUZIONE");
    private static final javax.xml.namespace.QName DATAINIPROGESEC$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_INI_PROG_ESEC");
    private static final javax.xml.namespace.QName DATAAPPPROGESEC$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_APP_PROG_ESEC");
    private static final javax.xml.namespace.QName FLAGFRAZIONATA$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_FRAZIONATA");
    private static final javax.xml.namespace.QName DATAVERBALECONS$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_VERBALE_CONS");
    private static final javax.xml.namespace.QName DATAVERBALEDEF$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_VERBALE_DEF");
    private static final javax.xml.namespace.QName FLAGRISERVA$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_RISERVA");
    private static final javax.xml.namespace.QName DATAVERBINIZIO$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_VERB_INIZIO");
    private static final javax.xml.namespace.QName DATATERMINE$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_TERMINE");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets the "DATA_STIPULA" attribute
     */
    public java.util.Calendar getDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASTIPULA$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_STIPULA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASTIPULA$0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_STIPULA" attribute
     */
    public boolean isSetDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATASTIPULA$0) != null;
        }
    }
    
    /**
     * Sets the "DATA_STIPULA" attribute
     */
    public void setDATASTIPULA(java.util.Calendar datastipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASTIPULA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATASTIPULA$0);
            }
            target.setCalendarValue(datastipula);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_STIPULA" attribute
     */
    public void xsetDATASTIPULA(it.avlp.simog.massload.xmlbeans.DbDateType datastipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASTIPULA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATASTIPULA$0);
            }
            target.set(datastipula);
        }
    }
    
    /**
     * Unsets the "DATA_STIPULA" attribute
     */
    public void unsetDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATASTIPULA$0);
        }
    }
    
    /**
     * Gets the "DATA_ESECUTIVITA" attribute
     */
    public java.util.Calendar getDATAESECUTIVITA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAESECUTIVITA$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ESECUTIVITA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAESECUTIVITA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAESECUTIVITA$2);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ESECUTIVITA" attribute
     */
    public boolean isSetDATAESECUTIVITA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAESECUTIVITA$2) != null;
        }
    }
    
    /**
     * Sets the "DATA_ESECUTIVITA" attribute
     */
    public void setDATAESECUTIVITA(java.util.Calendar dataesecutivita)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAESECUTIVITA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAESECUTIVITA$2);
            }
            target.setCalendarValue(dataesecutivita);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ESECUTIVITA" attribute
     */
    public void xsetDATAESECUTIVITA(it.avlp.simog.massload.xmlbeans.DbDateType dataesecutivita)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAESECUTIVITA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAESECUTIVITA$2);
            }
            target.set(dataesecutivita);
        }
    }
    
    /**
     * Unsets the "DATA_ESECUTIVITA" attribute
     */
    public void unsetDATAESECUTIVITA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAESECUTIVITA$2);
        }
    }
    
    /**
     * Gets the "IMPORTO_CAUZIONE" attribute
     */
    public java.math.BigDecimal getIMPORTOCAUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOCAUZIONE$4);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_CAUZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOCAUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOCAUZIONE$4);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_CAUZIONE" attribute
     */
    public void setIMPORTOCAUZIONE(java.math.BigDecimal importocauzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOCAUZIONE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOCAUZIONE$4);
            }
            target.setBigDecimalValue(importocauzione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_CAUZIONE" attribute
     */
    public void xsetIMPORTOCAUZIONE(it.avlp.simog.massload.xmlbeans.ImportoType importocauzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOCAUZIONE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOCAUZIONE$4);
            }
            target.set(importocauzione);
        }
    }
    
    /**
     * Gets the "DATA_INI_PROG_ESEC" attribute
     */
    public java.util.Calendar getDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAINIPROGESEC$6);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INI_PROG_ESEC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAINIPROGESEC$6);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INI_PROG_ESEC" attribute
     */
    public boolean isSetDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAINIPROGESEC$6) != null;
        }
    }
    
    /**
     * Sets the "DATA_INI_PROG_ESEC" attribute
     */
    public void setDATAINIPROGESEC(java.util.Calendar datainiprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAINIPROGESEC$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAINIPROGESEC$6);
            }
            target.setCalendarValue(datainiprogesec);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INI_PROG_ESEC" attribute
     */
    public void xsetDATAINIPROGESEC(it.avlp.simog.massload.xmlbeans.DbDateType datainiprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAINIPROGESEC$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAINIPROGESEC$6);
            }
            target.set(datainiprogesec);
        }
    }
    
    /**
     * Unsets the "DATA_INI_PROG_ESEC" attribute
     */
    public void unsetDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAINIPROGESEC$6);
        }
    }
    
    /**
     * Gets the "DATA_APP_PROG_ESEC" attribute
     */
    public java.util.Calendar getDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAPPPROGESEC$8);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_APP_PROG_ESEC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAPPPROGESEC$8);
            return target;
        }
    }
    
    /**
     * True if has "DATA_APP_PROG_ESEC" attribute
     */
    public boolean isSetDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAAPPPROGESEC$8) != null;
        }
    }
    
    /**
     * Sets the "DATA_APP_PROG_ESEC" attribute
     */
    public void setDATAAPPPROGESEC(java.util.Calendar dataappprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAPPPROGESEC$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAAPPPROGESEC$8);
            }
            target.setCalendarValue(dataappprogesec);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_APP_PROG_ESEC" attribute
     */
    public void xsetDATAAPPPROGESEC(it.avlp.simog.massload.xmlbeans.DbDateType dataappprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAPPPROGESEC$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAAPPPROGESEC$8);
            }
            target.set(dataappprogesec);
        }
    }
    
    /**
     * Unsets the "DATA_APP_PROG_ESEC" attribute
     */
    public void unsetDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAAPPPROGESEC$8);
        }
    }
    
    /**
     * Gets the "FLAG_FRAZIONATA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGFRAZIONATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGFRAZIONATA$10);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_FRAZIONATA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGFRAZIONATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGFRAZIONATA$10);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_FRAZIONATA" attribute
     */
    public void setFLAGFRAZIONATA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagfrazionata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGFRAZIONATA$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGFRAZIONATA$10);
            }
            target.setEnumValue(flagfrazionata);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_FRAZIONATA" attribute
     */
    public void xsetFLAGFRAZIONATA(it.avlp.simog.massload.xmlbeans.FlagSNType flagfrazionata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGFRAZIONATA$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGFRAZIONATA$10);
            }
            target.set(flagfrazionata);
        }
    }
    
    /**
     * Gets the "DATA_VERBALE_CONS" attribute
     */
    public java.util.Calendar getDATAVERBALECONS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBALECONS$12);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERBALE_CONS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAVERBALECONS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBALECONS$12);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERBALE_CONS" attribute
     */
    public boolean isSetDATAVERBALECONS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAVERBALECONS$12) != null;
        }
    }
    
    /**
     * Sets the "DATA_VERBALE_CONS" attribute
     */
    public void setDATAVERBALECONS(java.util.Calendar dataverbalecons)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBALECONS$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAVERBALECONS$12);
            }
            target.setCalendarValue(dataverbalecons);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERBALE_CONS" attribute
     */
    public void xsetDATAVERBALECONS(it.avlp.simog.massload.xmlbeans.DbDateType dataverbalecons)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBALECONS$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAVERBALECONS$12);
            }
            target.set(dataverbalecons);
        }
    }
    
    /**
     * Unsets the "DATA_VERBALE_CONS" attribute
     */
    public void unsetDATAVERBALECONS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAVERBALECONS$12);
        }
    }
    
    /**
     * Gets the "DATA_VERBALE_DEF" attribute
     */
    public java.util.Calendar getDATAVERBALEDEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBALEDEF$14);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERBALE_DEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAVERBALEDEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBALEDEF$14);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERBALE_DEF" attribute
     */
    public boolean isSetDATAVERBALEDEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAVERBALEDEF$14) != null;
        }
    }
    
    /**
     * Sets the "DATA_VERBALE_DEF" attribute
     */
    public void setDATAVERBALEDEF(java.util.Calendar dataverbaledef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBALEDEF$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAVERBALEDEF$14);
            }
            target.setCalendarValue(dataverbaledef);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERBALE_DEF" attribute
     */
    public void xsetDATAVERBALEDEF(it.avlp.simog.massload.xmlbeans.DbDateType dataverbaledef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBALEDEF$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAVERBALEDEF$14);
            }
            target.set(dataverbaledef);
        }
    }
    
    /**
     * Unsets the "DATA_VERBALE_DEF" attribute
     */
    public void unsetDATAVERBALEDEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAVERBALEDEF$14);
        }
    }
    
    /**
     * Gets the "FLAG_RISERVA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGRISERVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVA$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RISERVA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGRISERVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVA$16);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RISERVA" attribute
     */
    public void setFLAGRISERVA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagriserva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVA$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGRISERVA$16);
            }
            target.setEnumValue(flagriserva);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RISERVA" attribute
     */
    public void xsetFLAGRISERVA(it.avlp.simog.massload.xmlbeans.FlagSNType flagriserva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVA$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGRISERVA$16);
            }
            target.set(flagriserva);
        }
    }
    
    /**
     * Gets the "DATA_VERB_INIZIO" attribute
     */
    public java.util.Calendar getDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBINIZIO$18);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_INIZIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBINIZIO$18);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERB_INIZIO" attribute
     */
    public boolean isSetDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAVERBINIZIO$18) != null;
        }
    }
    
    /**
     * Sets the "DATA_VERB_INIZIO" attribute
     */
    public void setDATAVERBINIZIO(java.util.Calendar dataverbinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBINIZIO$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAVERBINIZIO$18);
            }
            target.setCalendarValue(dataverbinizio);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_INIZIO" attribute
     */
    public void xsetDATAVERBINIZIO(it.avlp.simog.massload.xmlbeans.DbDateType dataverbinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBINIZIO$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAVERBINIZIO$18);
            }
            target.set(dataverbinizio);
        }
    }
    
    /**
     * Unsets the "DATA_VERB_INIZIO" attribute
     */
    public void unsetDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAVERBINIZIO$18);
        }
    }
    
    /**
     * Gets the "DATA_TERMINE" attribute
     */
    public java.util.Calendar getDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATATERMINE$20);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_TERMINE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATATERMINE$20);
            return target;
        }
    }
    
    /**
     * True if has "DATA_TERMINE" attribute
     */
    public boolean isSetDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATATERMINE$20) != null;
        }
    }
    
    /**
     * Sets the "DATA_TERMINE" attribute
     */
    public void setDATATERMINE(java.util.Calendar datatermine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATATERMINE$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATATERMINE$20);
            }
            target.setCalendarValue(datatermine);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_TERMINE" attribute
     */
    public void xsetDATATERMINE(it.avlp.simog.massload.xmlbeans.DbDateType datatermine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATATERMINE$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATATERMINE$20);
            }
            target.set(datatermine);
        }
    }
    
    /**
     * Unsets the "DATA_TERMINE" attribute
     */
    public void unsetDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATATERMINE$20);
        }
    }
    
    /**
     * Gets the "ID_SCHEDA_LOCALE" attribute
     */
    public java.lang.String getIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_SCHEDA_LOCALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IdSchedaType xgetIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$22);
            return target;
        }
    }
    
    /**
     * True if has "ID_SCHEDA_LOCALE" attribute
     */
    public boolean isSetIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSCHEDALOCALE$22) != null;
        }
    }
    
    /**
     * Sets the "ID_SCHEDA_LOCALE" attribute
     */
    public void setIDSCHEDALOCALE(java.lang.String idschedalocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$22);
            }
            target.setStringValue(idschedalocale);
        }
    }
    
    /**
     * Sets (as xml) the "ID_SCHEDA_LOCALE" attribute
     */
    public void xsetIDSCHEDALOCALE(it.avlp.simog.massload.xmlbeans.IdSchedaType idschedalocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$22);
            }
            target.set(idschedalocale);
        }
    }
    
    /**
     * Unsets the "ID_SCHEDA_LOCALE" attribute
     */
    public void unsetIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSCHEDALOCALE$22);
        }
    }
    
    /**
     * Gets the "ID_SCHEDA_SIMOG" attribute
     */
    public java.lang.String getIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_SCHEDA_SIMOG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IdSchedaType xgetIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$24);
            return target;
        }
    }
    
    /**
     * True if has "ID_SCHEDA_SIMOG" attribute
     */
    public boolean isSetIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSCHEDASIMOG$24) != null;
        }
    }
    
    /**
     * Sets the "ID_SCHEDA_SIMOG" attribute
     */
    public void setIDSCHEDASIMOG(java.lang.String idschedasimog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$24);
            }
            target.setStringValue(idschedasimog);
        }
    }
    
    /**
     * Sets (as xml) the "ID_SCHEDA_SIMOG" attribute
     */
    public void xsetIDSCHEDASIMOG(it.avlp.simog.massload.xmlbeans.IdSchedaType idschedasimog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$24);
            }
            target.set(idschedasimog);
        }
    }
    
    /**
     * Unsets the "ID_SCHEDA_SIMOG" attribute
     */
    public void unsetIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSCHEDASIMOG$24);
        }
    }
    
    /**
     * Gets the "ID_STATO_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum getIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoSchedaType xgetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            return target;
        }
    }
    
    /**
     * True if has "ID_STATO_SCHEDA" attribute
     */
    public boolean isSetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSTATOSCHEDA$26) != null;
        }
    }
    
    /**
     * Sets the "ID_STATO_SCHEDA" attribute
     */
    public void setIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum idstatoscheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$26);
            }
            target.setEnumValue(idstatoscheda);
        }
    }
    
    /**
     * Sets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    public void xsetIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType idstatoscheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$26);
            }
            target.set(idstatoscheda);
        }
    }
    
    /**
     * Unsets the "ID_STATO_SCHEDA" attribute
     */
    public void unsetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSTATOSCHEDA$26);
        }
    }
}
