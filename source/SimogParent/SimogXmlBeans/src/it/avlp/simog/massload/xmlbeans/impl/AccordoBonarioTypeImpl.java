/*
 * XML Type:  AccordoBonarioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AccordoBonarioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AccordoBonarioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AccordoBonarioTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AccordoBonarioType
{
    
    public AccordoBonarioTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAACCORDO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_ACCORDO");
    private static final javax.xml.namespace.QName ONERIDERIVANTI$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ONERI_DERIVANTI");
    private static final javax.xml.namespace.QName NUMRISERVE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_RISERVE");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets the "DATA_ACCORDO" attribute
     */
    public java.util.Calendar getDATAACCORDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAACCORDO$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ACCORDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAACCORDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAACCORDO$0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_ACCORDO" attribute
     */
    public void setDATAACCORDO(java.util.Calendar dataaccordo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAACCORDO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAACCORDO$0);
            }
            target.setCalendarValue(dataaccordo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ACCORDO" attribute
     */
    public void xsetDATAACCORDO(it.avlp.simog.massload.xmlbeans.DbDateType dataaccordo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAACCORDO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAACCORDO$0);
            }
            target.set(dataaccordo);
        }
    }
    
    /**
     * Gets the "ONERI_DERIVANTI" attribute
     */
    public java.math.BigDecimal getONERIDERIVANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ONERIDERIVANTI$2);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_DERIVANTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetONERIDERIVANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ONERIDERIVANTI$2);
            return target;
        }
    }
    
    /**
     * True if has "ONERI_DERIVANTI" attribute
     */
    public boolean isSetONERIDERIVANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ONERIDERIVANTI$2) != null;
        }
    }
    
    /**
     * Sets the "ONERI_DERIVANTI" attribute
     */
    public void setONERIDERIVANTI(java.math.BigDecimal oneriderivanti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ONERIDERIVANTI$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ONERIDERIVANTI$2);
            }
            target.setBigDecimalValue(oneriderivanti);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_DERIVANTI" attribute
     */
    public void xsetONERIDERIVANTI(it.avlp.simog.massload.xmlbeans.ImportoType oneriderivanti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ONERIDERIVANTI$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(ONERIDERIVANTI$2);
            }
            target.set(oneriderivanti);
        }
    }
    
    /**
     * Unsets the "ONERI_DERIVANTI" attribute
     */
    public void unsetONERIDERIVANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ONERIDERIVANTI$2);
        }
    }
    
    /**
     * Gets the "NUM_RISERVE" attribute
     */
    public int getNUMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMRISERVE$4);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_RISERVE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMRISERVE$4);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_RISERVE" attribute
     */
    public void setNUMRISERVE(int numriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMRISERVE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMRISERVE$4);
            }
            target.setIntValue(numriserve);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_RISERVE" attribute
     */
    public void xsetNUMRISERVE(it.avlp.simog.massload.xmlbeans.InteroType numriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMRISERVE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMRISERVE$4);
            }
            target.set(numriserve);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$6);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$6);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$6) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$6);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$6);
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
            get_store().remove_attribute(IDSCHEDALOCALE$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$8);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$8);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$8) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$8);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$8);
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
            get_store().remove_attribute(IDSCHEDASIMOG$8);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$10);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$10);
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
            return get_store().find_attribute_user(IDSTATOSCHEDA$10) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$10);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$10);
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
            get_store().remove_attribute(IDSTATOSCHEDA$10);
        }
    }
}
