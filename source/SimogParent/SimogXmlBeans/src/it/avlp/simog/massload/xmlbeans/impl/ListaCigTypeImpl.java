/*
 * XML Type:  ListaCigType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ListaCigType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ListaCigType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ListaCigTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ListaCigType
{
    
    public ListaCigTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIGARA$0 = 
        new javax.xml.namespace.QName("", "DatiGara");
    private static final javax.xml.namespace.QName CIG$2 = 
        new javax.xml.namespace.QName("", "CIG");
    
    
    /**
     * Gets the "DatiGara" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraType getDatiGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().find_element_user(DATIGARA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DatiGara" element
     */
    public void setDatiGara(it.avlp.simog.massload.xmlbeans.DatiGaraType datiGara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().find_element_user(DATIGARA$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().add_element_user(DATIGARA$0);
            }
            target.set(datiGara);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiGara" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraType addNewDatiGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().add_element_user(DATIGARA$0);
            return target;
        }
    }
    
    /**
     * Gets array of all "CIG" elements
     */
    public java.lang.String[] getCIGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CIG$2, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "CIG" element
     */
    public java.lang.String getCIGArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "CIG" elements
     */
    public it.avlp.simog.massload.xmlbeans.CigType[] xgetCIGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CIG$2, targetList);
            it.avlp.simog.massload.xmlbeans.CigType[] result = new it.avlp.simog.massload.xmlbeans.CigType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "CIG" element
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIGArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_element_user(CIG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (it.avlp.simog.massload.xmlbeans.CigType)target;
        }
    }
    
    /**
     * Returns number of "CIG" element
     */
    public int sizeOfCIGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIG$2);
        }
    }
    
    /**
     * Sets array of all "CIG" element
     */
    public void setCIGArray(java.lang.String[] cigArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(cigArray, CIG$2);
        }
    }
    
    /**
     * Sets ith "CIG" element
     */
    public void setCIGArray(int i, java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(cig);
        }
    }
    
    /**
     * Sets (as xml) array of all "CIG" element
     */
    public void xsetCIGArray(it.avlp.simog.massload.xmlbeans.CigType[]cigArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(cigArray, CIG$2);
        }
    }
    
    /**
     * Sets (as xml) ith "CIG" element
     */
    public void xsetCIGArray(int i, it.avlp.simog.massload.xmlbeans.CigType cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_element_user(CIG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(cig);
        }
    }
    
    /**
     * Inserts the value as the ith "CIG" element
     */
    public void insertCIG(int i, java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(CIG$2, i);
            target.setStringValue(cig);
        }
    }
    
    /**
     * Appends the value as the last "CIG" element
     */
    public void addCIG(java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIG$2);
            target.setStringValue(cig);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CIG" element
     */
    public it.avlp.simog.massload.xmlbeans.CigType insertNewCIG(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().insert_element_user(CIG$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CIG" element
     */
    public it.avlp.simog.massload.xmlbeans.CigType addNewCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_element_user(CIG$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "CIG" element
     */
    public void removeCIG(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIG$2, i);
        }
    }
}
