/*
 * XML Type:  FlussoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FlussoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML FlussoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class FlussoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FlussoType
{
    
    public FlussoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAELABORAZIONE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_ELABORAZIONE");
    private static final javax.xml.namespace.QName NUMELABORATE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_ELABORATE");
    private static final javax.xml.namespace.QName NUMERRORE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_ERRORE");
    private static final javax.xml.namespace.QName NUMWARNING$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_WARNING");
    private static final javax.xml.namespace.QName NUMCARICATE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_CARICATE");
    
    
    /**
     * Gets the "DATA_ELABORAZIONE" attribute
     */
    public java.util.Calendar getDATAELABORAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAELABORAZIONE$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ELABORAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAELABORAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAELABORAZIONE$0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_ELABORAZIONE" attribute
     */
    public void setDATAELABORAZIONE(java.util.Calendar dataelaborazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAELABORAZIONE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAELABORAZIONE$0);
            }
            target.setCalendarValue(dataelaborazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ELABORAZIONE" attribute
     */
    public void xsetDATAELABORAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType dataelaborazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAELABORAZIONE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAELABORAZIONE$0);
            }
            target.set(dataelaborazione);
        }
    }
    
    /**
     * Gets the "NUM_ELABORATE" attribute
     */
    public int getNUMELABORATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMELABORATE$2);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_ELABORATE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMELABORATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMELABORATE$2);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_ELABORATE" attribute
     */
    public void setNUMELABORATE(int numelaborate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMELABORATE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMELABORATE$2);
            }
            target.setIntValue(numelaborate);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_ELABORATE" attribute
     */
    public void xsetNUMELABORATE(it.avlp.simog.massload.xmlbeans.InteroType numelaborate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMELABORATE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMELABORATE$2);
            }
            target.set(numelaborate);
        }
    }
    
    /**
     * Gets the "NUM_ERRORE" attribute
     */
    public int getNUMERRORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMERRORE$4);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_ERRORE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMERRORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMERRORE$4);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_ERRORE" attribute
     */
    public void setNUMERRORE(int numerrore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMERRORE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMERRORE$4);
            }
            target.setIntValue(numerrore);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_ERRORE" attribute
     */
    public void xsetNUMERRORE(it.avlp.simog.massload.xmlbeans.InteroType numerrore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMERRORE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMERRORE$4);
            }
            target.set(numerrore);
        }
    }
    
    /**
     * Gets the "NUM_WARNING" attribute
     */
    public int getNUMWARNING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMWARNING$6);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_WARNING" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMWARNING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMWARNING$6);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_WARNING" attribute
     */
    public void setNUMWARNING(int numwarning)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMWARNING$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMWARNING$6);
            }
            target.setIntValue(numwarning);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_WARNING" attribute
     */
    public void xsetNUMWARNING(it.avlp.simog.massload.xmlbeans.InteroType numwarning)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMWARNING$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMWARNING$6);
            }
            target.set(numwarning);
        }
    }
    
    /**
     * Gets the "NUM_CARICATE" attribute
     */
    public int getNUMCARICATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMCARICATE$8);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_CARICATE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMCARICATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMCARICATE$8);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_CARICATE" attribute
     */
    public void setNUMCARICATE(int numcaricate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMCARICATE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMCARICATE$8);
            }
            target.setIntValue(numcaricate);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_CARICATE" attribute
     */
    public void xsetNUMCARICATE(it.avlp.simog.massload.xmlbeans.InteroType numcaricate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMCARICATE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMCARICATE$8);
            }
            target.set(numcaricate);
        }
    }
}
