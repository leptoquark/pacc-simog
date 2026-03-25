/*
 * XML Type:  AddrS5Type
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AddrS5Type
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AddrS5Type(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AddrS5TypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AddrS5Type
{
    
    public AddrS5TypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NATIONALID$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NATIONALID");
    private static final javax.xml.namespace.QName NUTS$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUTS");
    private static final javax.xml.namespace.QName EMAIL$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "E_MAIL");
    private static final javax.xml.namespace.QName PHONE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PHONE");
    private static final javax.xml.namespace.QName URL$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL");
    private static final javax.xml.namespace.QName FAX$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FAX");
    
    
    /**
     * Gets the "NATIONALID" attribute
     */
    public java.lang.String getNATIONALID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NATIONALID$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NATIONALID" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID xgetNATIONALID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID)get_store().find_attribute_user(NATIONALID$0);
            return target;
        }
    }
    
    /**
     * Sets the "NATIONALID" attribute
     */
    public void setNATIONALID(java.lang.String nationalid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NATIONALID$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NATIONALID$0);
            }
            target.setStringValue(nationalid);
        }
    }
    
    /**
     * Sets (as xml) the "NATIONALID" attribute
     */
    public void xsetNATIONALID(it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID nationalid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID)get_store().find_attribute_user(NATIONALID$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID)get_store().add_attribute_user(NATIONALID$0);
            }
            target.set(nationalid);
        }
    }
    
    /**
     * Gets the "NUTS" attribute
     */
    public java.lang.String getNUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUTS$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUTS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType xgetNUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(NUTS$2);
            return target;
        }
    }
    
    /**
     * Sets the "NUTS" attribute
     */
    public void setNUTS(java.lang.String nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUTS$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUTS$2);
            }
            target.setStringValue(nuts);
        }
    }
    
    /**
     * Sets (as xml) the "NUTS" attribute
     */
    public void xsetNUTS(it.avlp.simog.massload.xmlbeans.LuogoNutsType nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(NUTS$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().add_attribute_user(NUTS$2);
            }
            target.set(nuts);
        }
    }
    
    /**
     * Gets the "E_MAIL" attribute
     */
    public java.lang.String getEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "E_MAIL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL xgetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL)get_store().find_attribute_user(EMAIL$4);
            return target;
        }
    }
    
    /**
     * True if has "E_MAIL" attribute
     */
    public boolean isSetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(EMAIL$4) != null;
        }
    }
    
    /**
     * Sets the "E_MAIL" attribute
     */
    public void setEMAIL(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EMAIL$4);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "E_MAIL" attribute
     */
    public void xsetEMAIL(it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL)get_store().find_attribute_user(EMAIL$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL)get_store().add_attribute_user(EMAIL$4);
            }
            target.set(email);
        }
    }
    
    /**
     * Unsets the "E_MAIL" attribute
     */
    public void unsetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(EMAIL$4);
        }
    }
    
    /**
     * Gets the "PHONE" attribute
     */
    public java.lang.String getPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PHONE$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PHONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.Phone xgetPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(PHONE$6);
            return target;
        }
    }
    
    /**
     * True if has "PHONE" attribute
     */
    public boolean isSetPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PHONE$6) != null;
        }
    }
    
    /**
     * Sets the "PHONE" attribute
     */
    public void setPHONE(java.lang.String phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PHONE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PHONE$6);
            }
            target.setStringValue(phone);
        }
    }
    
    /**
     * Sets (as xml) the "PHONE" attribute
     */
    public void xsetPHONE(it.avlp.simog.massload.xmlbeans.Phone phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(PHONE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().add_attribute_user(PHONE$6);
            }
            target.set(phone);
        }
    }
    
    /**
     * Unsets the "PHONE" attribute
     */
    public void unsetPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PHONE$6);
        }
    }
    
    /**
     * Gets the "URL" attribute
     */
    public java.lang.String getURL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URL$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS5Type.URL xgetURL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type.URL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.URL)get_store().find_attribute_user(URL$8);
            return target;
        }
    }
    
    /**
     * True if has "URL" attribute
     */
    public boolean isSetURL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(URL$8) != null;
        }
    }
    
    /**
     * Sets the "URL" attribute
     */
    public void setURL(java.lang.String url)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URL$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URL$8);
            }
            target.setStringValue(url);
        }
    }
    
    /**
     * Sets (as xml) the "URL" attribute
     */
    public void xsetURL(it.avlp.simog.massload.xmlbeans.AddrS5Type.URL url)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type.URL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.URL)get_store().find_attribute_user(URL$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS5Type.URL)get_store().add_attribute_user(URL$8);
            }
            target.set(url);
        }
    }
    
    /**
     * Unsets the "URL" attribute
     */
    public void unsetURL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(URL$8);
        }
    }
    
    /**
     * Gets the "FAX" attribute
     */
    public java.lang.String getFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FAX" attribute
     */
    public it.avlp.simog.massload.xmlbeans.Phone xgetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(FAX$10);
            return target;
        }
    }
    
    /**
     * True if has "FAX" attribute
     */
    public boolean isSetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FAX$10) != null;
        }
    }
    
    /**
     * Sets the "FAX" attribute
     */
    public void setFAX(java.lang.String fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FAX$10);
            }
            target.setStringValue(fax);
        }
    }
    
    /**
     * Sets (as xml) the "FAX" attribute
     */
    public void xsetFAX(it.avlp.simog.massload.xmlbeans.Phone fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(FAX$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().add_attribute_user(FAX$10);
            }
            target.set(fax);
        }
    }
    
    /**
     * Unsets the "FAX" attribute
     */
    public void unsetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FAX$10);
        }
    }
    /**
     * An XML NATIONALID(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS5Type$NATIONALID.
     */
    public static class NATIONALIDImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS5Type.NATIONALID
    {
        
        public NATIONALIDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NATIONALIDImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML E_MAIL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS5Type$EMAIL.
     */
    public static class EMAILImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS5Type.EMAIL
    {
        
        public EMAILImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected EMAILImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML URL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS5Type$URL.
     */
    public static class URLImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS5Type.URL
    {
        
        public URLImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
