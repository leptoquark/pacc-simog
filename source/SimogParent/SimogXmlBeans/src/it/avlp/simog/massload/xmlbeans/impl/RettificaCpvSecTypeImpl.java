/*
 * XML Type:  RettificaCpvSecType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RettificaCpvSecType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RettificaCpvSecType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RettificaCpvSecTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RettificaCpvSecType
{
    
    public RettificaCpvSecTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OLDMAINCPVSEC$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OLD_MAIN_CPV_SEC");
    private static final javax.xml.namespace.QName NEWMAINCPVSEC$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NEW_MAIN_CPV_SEC");
    
    
    /**
     * Gets the "OLD_MAIN_CPV_SEC" attribute
     */
    public java.lang.String getOLDMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDMAINCPVSEC$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OLD_MAIN_CPV_SEC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC xgetOLDMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC)get_store().find_attribute_user(OLDMAINCPVSEC$0);
            return target;
        }
    }
    
    /**
     * True if has "OLD_MAIN_CPV_SEC" attribute
     */
    public boolean isSetOLDMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OLDMAINCPVSEC$0) != null;
        }
    }
    
    /**
     * Sets the "OLD_MAIN_CPV_SEC" attribute
     */
    public void setOLDMAINCPVSEC(java.lang.String oldmaincpvsec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDMAINCPVSEC$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OLDMAINCPVSEC$0);
            }
            target.setStringValue(oldmaincpvsec);
        }
    }
    
    /**
     * Sets (as xml) the "OLD_MAIN_CPV_SEC" attribute
     */
    public void xsetOLDMAINCPVSEC(it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC oldmaincpvsec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC)get_store().find_attribute_user(OLDMAINCPVSEC$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC)get_store().add_attribute_user(OLDMAINCPVSEC$0);
            }
            target.set(oldmaincpvsec);
        }
    }
    
    /**
     * Unsets the "OLD_MAIN_CPV_SEC" attribute
     */
    public void unsetOLDMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OLDMAINCPVSEC$0);
        }
    }
    
    /**
     * Gets the "NEW_MAIN_CPV_SEC" attribute
     */
    public java.lang.String getNEWMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWMAINCPVSEC$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NEW_MAIN_CPV_SEC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC xgetNEWMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC)get_store().find_attribute_user(NEWMAINCPVSEC$2);
            return target;
        }
    }
    
    /**
     * True if has "NEW_MAIN_CPV_SEC" attribute
     */
    public boolean isSetNEWMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NEWMAINCPVSEC$2) != null;
        }
    }
    
    /**
     * Sets the "NEW_MAIN_CPV_SEC" attribute
     */
    public void setNEWMAINCPVSEC(java.lang.String newmaincpvsec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWMAINCPVSEC$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NEWMAINCPVSEC$2);
            }
            target.setStringValue(newmaincpvsec);
        }
    }
    
    /**
     * Sets (as xml) the "NEW_MAIN_CPV_SEC" attribute
     */
    public void xsetNEWMAINCPVSEC(it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC newmaincpvsec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC)get_store().find_attribute_user(NEWMAINCPVSEC$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC)get_store().add_attribute_user(NEWMAINCPVSEC$2);
            }
            target.set(newmaincpvsec);
        }
    }
    
    /**
     * Unsets the "NEW_MAIN_CPV_SEC" attribute
     */
    public void unsetNEWMAINCPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NEWMAINCPVSEC$2);
        }
    }
    /**
     * An XML OLD_MAIN_CPV_SEC(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaCpvSecType$OLDMAINCPVSEC.
     */
    public static class OLDMAINCPVSECImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.OLDMAINCPVSEC
    {
        
        public OLDMAINCPVSECImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected OLDMAINCPVSECImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NEW_MAIN_CPV_SEC(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaCpvSecType$NEWMAINCPVSEC.
     */
    public static class NEWMAINCPVSECImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaCpvSecType.NEWMAINCPVSEC
    {
        
        public NEWMAINCPVSECImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NEWMAINCPVSECImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
