/*
 * XML Type:  VariazioneSAType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.VariazioneSAType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML VariazioneSAType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class VariazioneSATypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.VariazioneSAType
{
    
    public VariazioneSATypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VARIAZIONESA$0 = 
        new javax.xml.namespace.QName("", "VariazioneSA");
    
    
    /**
     * Gets array of all "VariazioneSA" elements
     */
    public it.avlp.simog.massload.xmlbeans.RecVariazioneSAType[] getVariazioneSAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(VARIAZIONESA$0, targetList);
            it.avlp.simog.massload.xmlbeans.RecVariazioneSAType[] result = new it.avlp.simog.massload.xmlbeans.RecVariazioneSAType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "VariazioneSA" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVariazioneSAType getVariazioneSAArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVariazioneSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType)get_store().find_element_user(VARIAZIONESA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "VariazioneSA" element
     */
    public int sizeOfVariazioneSAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VARIAZIONESA$0);
        }
    }
    
    /**
     * Sets array of all "VariazioneSA" element
     */
    public void setVariazioneSAArray(it.avlp.simog.massload.xmlbeans.RecVariazioneSAType[] variazioneSAArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(variazioneSAArray, VARIAZIONESA$0);
        }
    }
    
    /**
     * Sets ith "VariazioneSA" element
     */
    public void setVariazioneSAArray(int i, it.avlp.simog.massload.xmlbeans.RecVariazioneSAType variazioneSA)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVariazioneSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType)get_store().find_element_user(VARIAZIONESA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(variazioneSA);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "VariazioneSA" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVariazioneSAType insertNewVariazioneSA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVariazioneSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType)get_store().insert_element_user(VARIAZIONESA$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "VariazioneSA" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVariazioneSAType addNewVariazioneSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVariazioneSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType)get_store().add_element_user(VARIAZIONESA$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "VariazioneSA" element
     */
    public void removeVariazioneSA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VARIAZIONESA$0, i);
        }
    }
}
