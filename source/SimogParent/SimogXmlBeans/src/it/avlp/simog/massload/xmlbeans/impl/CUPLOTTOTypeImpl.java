/*
 * XML Type:  CUPLOTTOType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CUPLOTTOType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML CUPLOTTOType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CUPLOTTOTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CUPLOTTOType
{
    
    public CUPLOTTOTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICICUP$0 = 
        new javax.xml.namespace.QName("", "CODICICUP");
    private static final javax.xml.namespace.QName CIG$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG");
    
    
    /**
     * Gets array of all "CODICICUP" elements
     */
    public it.avlp.simog.massload.xmlbeans.DatiCUPType[] getCODICICUPArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CODICICUP$0, targetList);
            it.avlp.simog.massload.xmlbeans.DatiCUPType[] result = new it.avlp.simog.massload.xmlbeans.DatiCUPType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "CODICICUP" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiCUPType getCODICICUPArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCUPType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCUPType)get_store().find_element_user(CODICICUP$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "CODICICUP" element
     */
    public int sizeOfCODICICUPArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODICICUP$0);
        }
    }
    
    /**
     * Sets array of all "CODICICUP" element
     */
    public void setCODICICUPArray(it.avlp.simog.massload.xmlbeans.DatiCUPType[] codicicupArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(codicicupArray, CODICICUP$0);
        }
    }
    
    /**
     * Sets ith "CODICICUP" element
     */
    public void setCODICICUPArray(int i, it.avlp.simog.massload.xmlbeans.DatiCUPType codicicup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCUPType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCUPType)get_store().find_element_user(CODICICUP$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(codicicup);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CODICICUP" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiCUPType insertNewCODICICUP(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCUPType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCUPType)get_store().insert_element_user(CODICICUP$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CODICICUP" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiCUPType addNewCODICICUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCUPType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCUPType)get_store().add_element_user(CODICICUP$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "CODICICUP" element
     */
    public void removeCODICICUP(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODICICUP$0, i);
        }
    }
    
    /**
     * Gets the "CIG" attribute
     */
    public java.lang.String getCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$2);
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
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$2);
            return target;
        }
    }
    
    /**
     * True if has "CIG" attribute
     */
    public boolean isSetCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CIG$2) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIG$2);
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
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIG$2);
            }
            target.set(cig);
        }
    }
    
    /**
     * Unsets the "CIG" attribute
     */
    public void unsetCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CIG$2);
        }
    }
}
