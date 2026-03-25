/*
 * XML Type:  RitardoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RitardoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RitardoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RitardoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RitardoType
{
    
    public RitardoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATATERMINE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_TERMINE");
    private static final javax.xml.namespace.QName DATACONSEGNA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_CONSEGNA");
    private static final javax.xml.namespace.QName TIPOCOMUN$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPO_COMUN");
    private static final javax.xml.namespace.QName DURATASOSP$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DURATA_SOSP");
    private static final javax.xml.namespace.QName MOTIVOSOSP$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MOTIVO_SOSP");
    private static final javax.xml.namespace.QName DATAISTRECESSO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_IST_RECESSO");
    private static final javax.xml.namespace.QName FLAGACCOLTA$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_ACCOLTA");
    private static final javax.xml.namespace.QName FLAGTARDIVA$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_TARDIVA");
    private static final javax.xml.namespace.QName FLAGRIPRESA$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_RIPRESA");
    private static final javax.xml.namespace.QName FLAGRISERVA$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_RISERVA");
    private static final javax.xml.namespace.QName IMPORTOSPESE$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_SPESE");
    private static final javax.xml.namespace.QName IMPORTOONERI$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_ONERI");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets the "DATA_TERMINE" attribute
     */
    public java.util.Calendar getDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATATERMINE$0);
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
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATATERMINE$0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATATERMINE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATATERMINE$0);
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
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATATERMINE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATATERMINE$0);
            }
            target.set(datatermine);
        }
    }
    
    /**
     * Gets the "DATA_CONSEGNA" attribute
     */
    public java.util.Calendar getDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACONSEGNA$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CONSEGNA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACONSEGNA$2);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CONSEGNA" attribute
     */
    public boolean isSetDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATACONSEGNA$2) != null;
        }
    }
    
    /**
     * Sets the "DATA_CONSEGNA" attribute
     */
    public void setDATACONSEGNA(java.util.Calendar dataconsegna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACONSEGNA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATACONSEGNA$2);
            }
            target.setCalendarValue(dataconsegna);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CONSEGNA" attribute
     */
    public void xsetDATACONSEGNA(it.avlp.simog.massload.xmlbeans.DbDateType dataconsegna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACONSEGNA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATACONSEGNA$2);
            }
            target.set(dataconsegna);
        }
    }
    
    /**
     * Unsets the "DATA_CONSEGNA" attribute
     */
    public void unsetDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATACONSEGNA$2);
        }
    }
    
    /**
     * Gets the "TIPO_COMUN" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagTCType.Enum getTIPOCOMUN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCOMUN$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagTCType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_COMUN" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagTCType xgetTIPOCOMUN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagTCType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagTCType)get_store().find_attribute_user(TIPOCOMUN$4);
            return target;
        }
    }
    
    /**
     * Sets the "TIPO_COMUN" attribute
     */
    public void setTIPOCOMUN(it.avlp.simog.massload.xmlbeans.FlagTCType.Enum tipocomun)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCOMUN$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOCOMUN$4);
            }
            target.setEnumValue(tipocomun);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_COMUN" attribute
     */
    public void xsetTIPOCOMUN(it.avlp.simog.massload.xmlbeans.FlagTCType tipocomun)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagTCType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagTCType)get_store().find_attribute_user(TIPOCOMUN$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagTCType)get_store().add_attribute_user(TIPOCOMUN$4);
            }
            target.set(tipocomun);
        }
    }
    
    /**
     * Gets the "DURATA_SOSP" attribute
     */
    public int getDURATASOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATASOSP$6);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "DURATA_SOSP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetDURATASOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(DURATASOSP$6);
            return target;
        }
    }
    
    /**
     * Sets the "DURATA_SOSP" attribute
     */
    public void setDURATASOSP(int duratasosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATASOSP$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DURATASOSP$6);
            }
            target.setIntValue(duratasosp);
        }
    }
    
    /**
     * Sets (as xml) the "DURATA_SOSP" attribute
     */
    public void xsetDURATASOSP(it.avlp.simog.massload.xmlbeans.InteroType duratasosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(DURATASOSP$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(DURATASOSP$6);
            }
            target.set(duratasosp);
        }
    }
    
    /**
     * Gets the "MOTIVO_SOSP" attribute
     */
    public java.lang.String getMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVOSOSP$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVO_SOSP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP xgetMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP)get_store().find_attribute_user(MOTIVOSOSP$8);
            return target;
        }
    }
    
    /**
     * True if has "MOTIVO_SOSP" attribute
     */
    public boolean isSetMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MOTIVOSOSP$8) != null;
        }
    }
    
    /**
     * Sets the "MOTIVO_SOSP" attribute
     */
    public void setMOTIVOSOSP(java.lang.String motivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVOSOSP$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOTIVOSOSP$8);
            }
            target.setStringValue(motivososp);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVO_SOSP" attribute
     */
    public void xsetMOTIVOSOSP(it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP motivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP)get_store().find_attribute_user(MOTIVOSOSP$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP)get_store().add_attribute_user(MOTIVOSOSP$8);
            }
            target.set(motivososp);
        }
    }
    
    /**
     * Unsets the "MOTIVO_SOSP" attribute
     */
    public void unsetMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MOTIVOSOSP$8);
        }
    }
    
    /**
     * Gets the "DATA_IST_RECESSO" attribute
     */
    public java.util.Calendar getDATAISTRECESSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAISTRECESSO$10);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_IST_RECESSO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAISTRECESSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAISTRECESSO$10);
            return target;
        }
    }
    
    /**
     * True if has "DATA_IST_RECESSO" attribute
     */
    public boolean isSetDATAISTRECESSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAISTRECESSO$10) != null;
        }
    }
    
    /**
     * Sets the "DATA_IST_RECESSO" attribute
     */
    public void setDATAISTRECESSO(java.util.Calendar dataistrecesso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAISTRECESSO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAISTRECESSO$10);
            }
            target.setCalendarValue(dataistrecesso);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_IST_RECESSO" attribute
     */
    public void xsetDATAISTRECESSO(it.avlp.simog.massload.xmlbeans.DbDateType dataistrecesso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAISTRECESSO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAISTRECESSO$10);
            }
            target.set(dataistrecesso);
        }
    }
    
    /**
     * Unsets the "DATA_IST_RECESSO" attribute
     */
    public void unsetDATAISTRECESSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAISTRECESSO$10);
        }
    }
    
    /**
     * Gets the "FLAG_ACCOLTA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGACCOLTA$12);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ACCOLTA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGACCOLTA$12);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_ACCOLTA" attribute
     */
    public boolean isSetFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGACCOLTA$12) != null;
        }
    }
    
    /**
     * Sets the "FLAG_ACCOLTA" attribute
     */
    public void setFLAGACCOLTA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagaccolta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGACCOLTA$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGACCOLTA$12);
            }
            target.setEnumValue(flagaccolta);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ACCOLTA" attribute
     */
    public void xsetFLAGACCOLTA(it.avlp.simog.massload.xmlbeans.FlagSNType flagaccolta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGACCOLTA$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGACCOLTA$12);
            }
            target.set(flagaccolta);
        }
    }
    
    /**
     * Unsets the "FLAG_ACCOLTA" attribute
     */
    public void unsetFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGACCOLTA$12);
        }
    }
    
    /**
     * Gets the "FLAG_TARDIVA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGTARDIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGTARDIVA$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_TARDIVA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGTARDIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGTARDIVA$14);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_TARDIVA" attribute
     */
    public void setFLAGTARDIVA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagtardiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGTARDIVA$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGTARDIVA$14);
            }
            target.setEnumValue(flagtardiva);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_TARDIVA" attribute
     */
    public void xsetFLAGTARDIVA(it.avlp.simog.massload.xmlbeans.FlagSNType flagtardiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGTARDIVA$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGTARDIVA$14);
            }
            target.set(flagtardiva);
        }
    }
    
    /**
     * Gets the "FLAG_RIPRESA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGRIPRESA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRIPRESA$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RIPRESA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGRIPRESA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRIPRESA$16);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RIPRESA" attribute
     */
    public void setFLAGRIPRESA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagripresa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRIPRESA$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGRIPRESA$16);
            }
            target.setEnumValue(flagripresa);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RIPRESA" attribute
     */
    public void xsetFLAGRIPRESA(it.avlp.simog.massload.xmlbeans.FlagSNType flagripresa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRIPRESA$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGRIPRESA$16);
            }
            target.set(flagripresa);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVA$18);
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
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVA$18);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVA$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGRISERVA$18);
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
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVA$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGRISERVA$18);
            }
            target.set(flagriserva);
        }
    }
    
    /**
     * Gets the "IMPORTO_SPESE" attribute
     */
    public java.math.BigDecimal getIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOSPESE$20);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_SPESE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOSPESE$20);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_SPESE" attribute
     */
    public boolean isSetIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOSPESE$20) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_SPESE" attribute
     */
    public void setIMPORTOSPESE(java.math.BigDecimal importospese)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOSPESE$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOSPESE$20);
            }
            target.setBigDecimalValue(importospese);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_SPESE" attribute
     */
    public void xsetIMPORTOSPESE(it.avlp.simog.massload.xmlbeans.ImportoType importospese)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOSPESE$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOSPESE$20);
            }
            target.set(importospese);
        }
    }
    
    /**
     * Unsets the "IMPORTO_SPESE" attribute
     */
    public void unsetIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOSPESE$20);
        }
    }
    
    /**
     * Gets the "IMPORTO_ONERI" attribute
     */
    public java.math.BigDecimal getIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOONERI$22);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_ONERI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOONERI$22);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_ONERI" attribute
     */
    public boolean isSetIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOONERI$22) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_ONERI" attribute
     */
    public void setIMPORTOONERI(java.math.BigDecimal importooneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOONERI$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOONERI$22);
            }
            target.setBigDecimalValue(importooneri);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_ONERI" attribute
     */
    public void xsetIMPORTOONERI(it.avlp.simog.massload.xmlbeans.ImportoType importooneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOONERI$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOONERI$22);
            }
            target.set(importooneri);
        }
    }
    
    /**
     * Unsets the "IMPORTO_ONERI" attribute
     */
    public void unsetIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOONERI$22);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$24);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$24);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$24) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$24);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$24);
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
            get_store().remove_attribute(IDSCHEDALOCALE$24);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$26);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$26);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$26) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$26);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$26);
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
            get_store().remove_attribute(IDSCHEDASIMOG$26);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$28);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$28);
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
            return get_store().find_attribute_user(IDSTATOSCHEDA$28) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$28);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$28);
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
            get_store().remove_attribute(IDSTATOSCHEDA$28);
        }
    }
    /**
     * An XML MOTIVO_SOSP(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RitardoType$MOTIVOSOSP.
     */
    public static class MOTIVOSOSPImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RitardoType.MOTIVOSOSP
    {
        
        public MOTIVOSOSPImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MOTIVOSOSPImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
