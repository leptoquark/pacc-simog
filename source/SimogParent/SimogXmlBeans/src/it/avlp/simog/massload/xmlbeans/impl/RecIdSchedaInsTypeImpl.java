/*
 * XML Type:  RecIdSchedaInsType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RecIdSchedaInsType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RecIdSchedaInsTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType
{
    
    public RecIdSchedaInsTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SCHEDA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SCHEDA");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName OPERAZIONE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OPERAZIONE");
    
    
    /**
     * Gets the "SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum getSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCHEDA$0);
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
            target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().find_attribute_user(SCHEDA$0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCHEDA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SCHEDA$0);
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
            target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().find_attribute_user(SCHEDA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().add_attribute_user(SCHEDA$0);
            }
            target.set(scheda);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$2);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$2);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$2);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$2);
            }
            target.set(idschedasimog);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$4);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$4);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$4) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$4);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$4);
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
            get_store().remove_attribute(IDSCHEDALOCALE$4);
        }
    }
    
    /**
     * Gets the "OPERAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipiOperazioneType.Enum getOPERAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OPERAZIONE$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.TipiOperazioneType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "OPERAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipiOperazioneType xgetOPERAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiOperazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiOperazioneType)get_store().find_attribute_user(OPERAZIONE$6);
            return target;
        }
    }
    
    /**
     * Sets the "OPERAZIONE" attribute
     */
    public void setOPERAZIONE(it.avlp.simog.massload.xmlbeans.TipiOperazioneType.Enum operazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OPERAZIONE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OPERAZIONE$6);
            }
            target.setEnumValue(operazione);
        }
    }
    
    /**
     * Sets (as xml) the "OPERAZIONE" attribute
     */
    public void xsetOPERAZIONE(it.avlp.simog.massload.xmlbeans.TipiOperazioneType operazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiOperazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiOperazioneType)get_store().find_attribute_user(OPERAZIONE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipiOperazioneType)get_store().add_attribute_user(OPERAZIONE$6);
            }
            target.set(operazione);
        }
    }
}
