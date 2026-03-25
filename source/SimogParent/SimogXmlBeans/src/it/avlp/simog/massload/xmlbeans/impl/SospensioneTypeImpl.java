/*
 * XML Type:  SospensioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SospensioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SospensioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SospensioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SospensioneType
{
    
    public SospensioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAVERBSOSP$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_VERB_SOSP");
    private static final javax.xml.namespace.QName DATAVERBRIPR$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_VERB_RIPR");
    private static final javax.xml.namespace.QName IDMOTIVOSOSP$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_MOTIVO_SOSP");
    private static final javax.xml.namespace.QName FLAGSUPEROTEMPO$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_SUPERO_TEMPO");
    private static final javax.xml.namespace.QName FLAGRISERVE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_RISERVE");
    private static final javax.xml.namespace.QName FLAGVERBALE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_VERBALE");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets the "DATA_VERB_SOSP" attribute
     */
    public java.util.Calendar getDATAVERBSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBSOSP$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_SOSP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAVERBSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBSOSP$0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_VERB_SOSP" attribute
     */
    public void setDATAVERBSOSP(java.util.Calendar dataverbsosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBSOSP$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAVERBSOSP$0);
            }
            target.setCalendarValue(dataverbsosp);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_SOSP" attribute
     */
    public void xsetDATAVERBSOSP(it.avlp.simog.massload.xmlbeans.DbDateType dataverbsosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBSOSP$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAVERBSOSP$0);
            }
            target.set(dataverbsosp);
        }
    }
    
    /**
     * Gets the "DATA_VERB_RIPR" attribute
     */
    public java.util.Calendar getDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBRIPR$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_RIPR" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBRIPR$2);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERB_RIPR" attribute
     */
    public boolean isSetDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAVERBRIPR$2) != null;
        }
    }
    
    /**
     * Sets the "DATA_VERB_RIPR" attribute
     */
    public void setDATAVERBRIPR(java.util.Calendar dataverbripr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBRIPR$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAVERBRIPR$2);
            }
            target.setCalendarValue(dataverbripr);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_RIPR" attribute
     */
    public void xsetDATAVERBRIPR(it.avlp.simog.massload.xmlbeans.DbDateType dataverbripr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBRIPR$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAVERBRIPR$2);
            }
            target.set(dataverbripr);
        }
    }
    
    /**
     * Unsets the "DATA_VERB_RIPR" attribute
     */
    public void unsetDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAVERBRIPR$2);
        }
    }
    
    /**
     * Gets the "ID_MOTIVO_SOSP" attribute
     */
    public java.lang.String getIDMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOSOSP$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_SOSP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoSospensioneType xgetIDMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoSospensioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoSospensioneType)get_store().find_attribute_user(IDMOTIVOSOSP$4);
            return target;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_SOSP" attribute
     */
    public void setIDMOTIVOSOSP(java.lang.String idmotivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOSOSP$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDMOTIVOSOSP$4);
            }
            target.setStringValue(idmotivososp);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_SOSP" attribute
     */
    public void xsetIDMOTIVOSOSP(it.avlp.simog.massload.xmlbeans.MotivoSospensioneType idmotivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoSospensioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoSospensioneType)get_store().find_attribute_user(IDMOTIVOSOSP$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotivoSospensioneType)get_store().add_attribute_user(IDMOTIVOSOSP$4);
            }
            target.set(idmotivososp);
        }
    }
    
    /**
     * Gets the "FLAG_SUPERO_TEMPO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGSUPEROTEMPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSUPEROTEMPO$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SUPERO_TEMPO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGSUPEROTEMPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSUPEROTEMPO$6);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SUPERO_TEMPO" attribute
     */
    public void setFLAGSUPEROTEMPO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagsuperotempo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSUPEROTEMPO$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGSUPEROTEMPO$6);
            }
            target.setEnumValue(flagsuperotempo);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SUPERO_TEMPO" attribute
     */
    public void xsetFLAGSUPEROTEMPO(it.avlp.simog.massload.xmlbeans.FlagSNType flagsuperotempo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSUPEROTEMPO$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGSUPEROTEMPO$6);
            }
            target.set(flagsuperotempo);
        }
    }
    
    /**
     * Gets the "FLAG_RISERVE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVE$8);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RISERVE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVE$8);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RISERVE" attribute
     */
    public void setFLAGRISERVE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGRISERVE$8);
            }
            target.setEnumValue(flagriserve);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RISERVE" attribute
     */
    public void xsetFLAGRISERVE(it.avlp.simog.massload.xmlbeans.FlagSNType flagriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGRISERVE$8);
            }
            target.set(flagriserve);
        }
    }
    
    /**
     * Gets the "FLAG_VERBALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGVERBALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGVERBALE$10);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_VERBALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGVERBALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGVERBALE$10);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_VERBALE" attribute
     */
    public void setFLAGVERBALE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagverbale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGVERBALE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGVERBALE$10);
            }
            target.setEnumValue(flagverbale);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_VERBALE" attribute
     */
    public void xsetFLAGVERBALE(it.avlp.simog.massload.xmlbeans.FlagSNType flagverbale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGVERBALE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGVERBALE$10);
            }
            target.set(flagverbale);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$12);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$12);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$12) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$12);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$12);
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
            get_store().remove_attribute(IDSCHEDALOCALE$12);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$14);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$14);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$14) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$14);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$14);
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
            get_store().remove_attribute(IDSCHEDASIMOG$14);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$16);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$16);
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
            return get_store().find_attribute_user(IDSTATOSCHEDA$16) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$16);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$16);
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
            get_store().remove_attribute(IDSTATOSCHEDA$16);
        }
    }
}
