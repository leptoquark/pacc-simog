/*
 * XML Type:  ValoreAppaltoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ValoreAppaltoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ValoreAppaltoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ValoreAppaltoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ValoreAppaltoType
{
    
    public ValoreAppaltoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VALTOTAL$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_TOTAL");
    private static final javax.xml.namespace.QName VALRANGETOTALLOW$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_RANGE_TOTAL_LOW");
    private static final javax.xml.namespace.QName VALRANGETOTALHIGH$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_RANGE_TOTAL_HIGH");
    
    
    /**
     * Gets the "VAL_TOTAL" attribute
     */
    public java.math.BigDecimal getVALTOTAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTAL$0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_TOTAL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetVALTOTAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTAL$0);
            return target;
        }
    }
    
    /**
     * True if has "VAL_TOTAL" attribute
     */
    public boolean isSetVALTOTAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(VALTOTAL$0) != null;
        }
    }
    
    /**
     * Sets the "VAL_TOTAL" attribute
     */
    public void setVALTOTAL(java.math.BigDecimal valtotal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTAL$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALTOTAL$0);
            }
            target.setBigDecimalValue(valtotal);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_TOTAL" attribute
     */
    public void xsetVALTOTAL(it.avlp.simog.massload.xmlbeans.ImportoType valtotal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTAL$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(VALTOTAL$0);
            }
            target.set(valtotal);
        }
    }
    
    /**
     * Unsets the "VAL_TOTAL" attribute
     */
    public void unsetVALTOTAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(VALTOTAL$0);
        }
    }
    
    /**
     * Gets the "VAL_RANGE_TOTAL_LOW" attribute
     */
    public java.math.BigDecimal getVALRANGETOTALLOW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALRANGETOTALLOW$2);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_RANGE_TOTAL_LOW" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetVALRANGETOTALLOW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALRANGETOTALLOW$2);
            return target;
        }
    }
    
    /**
     * True if has "VAL_RANGE_TOTAL_LOW" attribute
     */
    public boolean isSetVALRANGETOTALLOW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(VALRANGETOTALLOW$2) != null;
        }
    }
    
    /**
     * Sets the "VAL_RANGE_TOTAL_LOW" attribute
     */
    public void setVALRANGETOTALLOW(java.math.BigDecimal valrangetotallow)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALRANGETOTALLOW$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALRANGETOTALLOW$2);
            }
            target.setBigDecimalValue(valrangetotallow);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_RANGE_TOTAL_LOW" attribute
     */
    public void xsetVALRANGETOTALLOW(it.avlp.simog.massload.xmlbeans.ImportoType valrangetotallow)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALRANGETOTALLOW$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(VALRANGETOTALLOW$2);
            }
            target.set(valrangetotallow);
        }
    }
    
    /**
     * Unsets the "VAL_RANGE_TOTAL_LOW" attribute
     */
    public void unsetVALRANGETOTALLOW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(VALRANGETOTALLOW$2);
        }
    }
    
    /**
     * Gets the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    public java.math.BigDecimal getVALRANGETOTALHIGH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALRANGETOTALHIGH$4);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetVALRANGETOTALHIGH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALRANGETOTALHIGH$4);
            return target;
        }
    }
    
    /**
     * True if has "VAL_RANGE_TOTAL_HIGH" attribute
     */
    public boolean isSetVALRANGETOTALHIGH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(VALRANGETOTALHIGH$4) != null;
        }
    }
    
    /**
     * Sets the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    public void setVALRANGETOTALHIGH(java.math.BigDecimal valrangetotalhigh)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALRANGETOTALHIGH$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALRANGETOTALHIGH$4);
            }
            target.setBigDecimalValue(valrangetotalhigh);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    public void xsetVALRANGETOTALHIGH(it.avlp.simog.massload.xmlbeans.ImportoType valrangetotalhigh)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALRANGETOTALHIGH$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(VALRANGETOTALHIGH$4);
            }
            target.set(valrangetotalhigh);
        }
    }
    
    /**
     * Unsets the "VAL_RANGE_TOTAL_HIGH" attribute
     */
    public void unsetVALRANGETOTALHIGH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(VALRANGETOTALHIGH$4);
        }
    }
}
