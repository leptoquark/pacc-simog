/*
 * XML Type:  InfoAmministrativeTypeAgg
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML InfoAmministrativeTypeAgg(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class InfoAmministrativeTypeAggImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg
{
    
    public InfoAmministrativeTypeAggImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INFOSDA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INFO_SDA");
    private static final javax.xml.namespace.QName INFOAVVPRE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INFO_AVV_PRE");
    
    
    /**
     * Gets the "INFO_SDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getINFOSDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOSDA$0);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "INFO_SDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetINFOSDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(INFOSDA$0);
            return target;
        }
    }
    
    /**
     * True if has "INFO_SDA" attribute
     */
    public boolean isSetINFOSDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INFOSDA$0) != null;
        }
    }
    
    /**
     * Sets the "INFO_SDA" attribute
     */
    public void setINFOSDA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum infosda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOSDA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INFOSDA$0);
            }
            target.setEnumValue(infosda);
        }
    }
    
    /**
     * Sets (as xml) the "INFO_SDA" attribute
     */
    public void xsetINFOSDA(it.avlp.simog.massload.xmlbeans.FlagSNType infosda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(INFOSDA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(INFOSDA$0);
            }
            target.set(infosda);
        }
    }
    
    /**
     * Unsets the "INFO_SDA" attribute
     */
    public void unsetINFOSDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INFOSDA$0);
        }
    }
    
    /**
     * Gets the "INFO_AVV_PRE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getINFOAVVPRE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOAVVPRE$2);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "INFO_AVV_PRE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetINFOAVVPRE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(INFOAVVPRE$2);
            return target;
        }
    }
    
    /**
     * True if has "INFO_AVV_PRE" attribute
     */
    public boolean isSetINFOAVVPRE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INFOAVVPRE$2) != null;
        }
    }
    
    /**
     * Sets the "INFO_AVV_PRE" attribute
     */
    public void setINFOAVVPRE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum infoavvpre)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOAVVPRE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INFOAVVPRE$2);
            }
            target.setEnumValue(infoavvpre);
        }
    }
    
    /**
     * Sets (as xml) the "INFO_AVV_PRE" attribute
     */
    public void xsetINFOAVVPRE(it.avlp.simog.massload.xmlbeans.FlagSNType infoavvpre)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(INFOAVVPRE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(INFOAVVPRE$2);
            }
            target.set(infoavvpre);
        }
    }
    
    /**
     * Unsets the "INFO_AVV_PRE" attribute
     */
    public void unsetINFOAVVPRE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INFOAVVPRE$2);
        }
    }
}
