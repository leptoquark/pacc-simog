/*
 * XML Type:  AmbitoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AmbitoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AmbitoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AmbitoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AmbitoType
{
    
    public AmbitoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AMBITOLOTTO$0 = 
        new javax.xml.namespace.QName("", "AmbitoLotto");
    
    
    /**
     * Gets array of all "AmbitoLotto" elements
     */
    public java.lang.String[] getAmbitoLottoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AMBITOLOTTO$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "AmbitoLotto" element
     */
    public java.lang.String getAmbitoLottoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMBITOLOTTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "AmbitoLotto" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetAmbitoLottoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AMBITOLOTTO$0, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "AmbitoLotto" element
     */
    public org.apache.xmlbeans.XmlString xgetAmbitoLottoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AMBITOLOTTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Returns number of "AmbitoLotto" element
     */
    public int sizeOfAmbitoLottoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AMBITOLOTTO$0);
        }
    }
    
    /**
     * Sets array of all "AmbitoLotto" element
     */
    public void setAmbitoLottoArray(java.lang.String[] ambitoLottoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(ambitoLottoArray, AMBITOLOTTO$0);
        }
    }
    
    /**
     * Sets ith "AmbitoLotto" element
     */
    public void setAmbitoLottoArray(int i, java.lang.String ambitoLotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMBITOLOTTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(ambitoLotto);
        }
    }
    
    /**
     * Sets (as xml) array of all "AmbitoLotto" element
     */
    public void xsetAmbitoLottoArray(org.apache.xmlbeans.XmlString[]ambitoLottoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(ambitoLottoArray, AMBITOLOTTO$0);
        }
    }
    
    /**
     * Sets (as xml) ith "AmbitoLotto" element
     */
    public void xsetAmbitoLottoArray(int i, org.apache.xmlbeans.XmlString ambitoLotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AMBITOLOTTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(ambitoLotto);
        }
    }
    
    /**
     * Inserts the value as the ith "AmbitoLotto" element
     */
    public void insertAmbitoLotto(int i, java.lang.String ambitoLotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(AMBITOLOTTO$0, i);
            target.setStringValue(ambitoLotto);
        }
    }
    
    /**
     * Appends the value as the last "AmbitoLotto" element
     */
    public void addAmbitoLotto(java.lang.String ambitoLotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AMBITOLOTTO$0);
            target.setStringValue(ambitoLotto);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "AmbitoLotto" element
     */
    public org.apache.xmlbeans.XmlString insertNewAmbitoLotto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(AMBITOLOTTO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "AmbitoLotto" element
     */
    public org.apache.xmlbeans.XmlString addNewAmbitoLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AMBITOLOTTO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "AmbitoLotto" element
     */
    public void removeAmbitoLotto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AMBITOLOTTO$0, i);
        }
    }
}
