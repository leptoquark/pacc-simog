/*
 * XML Type:  StipulaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.StipulaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML StipulaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class StipulaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.StipulaType
{
    
    public StipulaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATASTIPULA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_STIPULA");
    private static final javax.xml.namespace.QName DATADECORRRENZA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_DECORRRENZA");
    private static final javax.xml.namespace.QName DATASCADENZA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_SCADENZA");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$10 = 
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
     * Gets the "DATA_DECORRRENZA" attribute
     */
    public java.util.Calendar getDATADECORRRENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATADECORRRENZA$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_DECORRRENZA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATADECORRRENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATADECORRRENZA$2);
            return target;
        }
    }
    
    /**
     * True if has "DATA_DECORRRENZA" attribute
     */
    public boolean isSetDATADECORRRENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATADECORRRENZA$2) != null;
        }
    }
    
    /**
     * Sets the "DATA_DECORRRENZA" attribute
     */
    public void setDATADECORRRENZA(java.util.Calendar datadecorrrenza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATADECORRRENZA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATADECORRRENZA$2);
            }
            target.setCalendarValue(datadecorrrenza);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_DECORRRENZA" attribute
     */
    public void xsetDATADECORRRENZA(it.avlp.simog.massload.xmlbeans.DbDateType datadecorrrenza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATADECORRRENZA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATADECORRRENZA$2);
            }
            target.set(datadecorrrenza);
        }
    }
    
    /**
     * Unsets the "DATA_DECORRRENZA" attribute
     */
    public void unsetDATADECORRRENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATADECORRRENZA$2);
        }
    }
    
    /**
     * Gets the "DATA_SCADENZA" attribute
     */
    public java.util.Calendar getDATASCADENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZA$4);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_SCADENZA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATASCADENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZA$4);
            return target;
        }
    }
    
    /**
     * True if has "DATA_SCADENZA" attribute
     */
    public boolean isSetDATASCADENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATASCADENZA$4) != null;
        }
    }
    
    /**
     * Sets the "DATA_SCADENZA" attribute
     */
    public void setDATASCADENZA(java.util.Calendar datascadenza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATASCADENZA$4);
            }
            target.setCalendarValue(datascadenza);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_SCADENZA" attribute
     */
    public void xsetDATASCADENZA(it.avlp.simog.massload.xmlbeans.DbDateType datascadenza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATASCADENZA$4);
            }
            target.set(datascadenza);
        }
    }
    
    /**
     * Unsets the "DATA_SCADENZA" attribute
     */
    public void unsetDATASCADENZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATASCADENZA$4);
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
