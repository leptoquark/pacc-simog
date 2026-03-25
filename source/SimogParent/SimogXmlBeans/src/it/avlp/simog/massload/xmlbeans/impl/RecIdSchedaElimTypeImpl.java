/*
 * XML Type:  RecIdSchedaElimType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RecIdSchedaElimType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RecIdSchedaElimTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType
{
    
    public RecIdSchedaElimTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CIG$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG");
    private static final javax.xml.namespace.QName CUI$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CUI");
    private static final javax.xml.namespace.QName SCHEDA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SCHEDA");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    
    
    /**
     * Gets the "CIG" attribute
     */
    public java.lang.String getCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$0);
            return target;
        }
    }
    
    /**
     * Sets the "CIG" attribute
     */
    public void setCIG(java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIG$0);
            }
            target.setStringValue(cig);
        }
    }
    
    /**
     * Sets (as xml) the "CIG" attribute
     */
    public void xsetCIG(it.avlp.simog.massload.xmlbeans.CigType cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIG$0);
            }
            target.set(cig);
        }
    }
    
    /**
     * Gets the "CUI" attribute
     */
    public java.lang.String getCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUI$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CUI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CuiType xgetCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(CUI$2);
            return target;
        }
    }
    
    /**
     * Sets the "CUI" attribute
     */
    public void setCUI(java.lang.String cui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUI$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUI$2);
            }
            target.setStringValue(cui);
        }
    }
    
    /**
     * Sets (as xml) the "CUI" attribute
     */
    public void xsetCUI(it.avlp.simog.massload.xmlbeans.CuiType cui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(CUI$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().add_attribute_user(CUI$2);
            }
            target.set(cui);
        }
    }
    
    /**
     * Gets the "SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum getSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCHEDA$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipiSchedeType xgetSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiSchedeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().find_attribute_user(SCHEDA$4);
            return target;
        }
    }
    
    /**
     * True if has "SCHEDA" attribute
     */
    public boolean isSetSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SCHEDA$4) != null;
        }
    }
    
    /**
     * Sets the "SCHEDA" attribute
     */
    public void setSCHEDA(it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum scheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCHEDA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SCHEDA$4);
            }
            target.setEnumValue(scheda);
        }
    }
    
    /**
     * Sets (as xml) the "SCHEDA" attribute
     */
    public void xsetSCHEDA(it.avlp.simog.massload.xmlbeans.TipiSchedeType scheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiSchedeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().find_attribute_user(SCHEDA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().add_attribute_user(SCHEDA$4);
            }
            target.set(scheda);
        }
    }
    
    /**
     * Unsets the "SCHEDA" attribute
     */
    public void unsetSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SCHEDA$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$6);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$6);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$6) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$6);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$6);
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
            get_store().remove_attribute(IDSCHEDASIMOG$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$8);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$8);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$8) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$8);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$8);
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
            get_store().remove_attribute(IDSCHEDALOCALE$8);
        }
    }
}
