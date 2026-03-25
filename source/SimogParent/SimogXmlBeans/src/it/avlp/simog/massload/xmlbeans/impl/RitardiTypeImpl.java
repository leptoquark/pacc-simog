/*
 * XML Type:  RitardiType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RitardiType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RitardiType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RitardiTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RitardiType
{
    
    public RitardiTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RITARDO$0 = 
        new javax.xml.namespace.QName("", "Ritardo");
    
    
    /**
     * Gets array of all "Ritardo" elements
     */
    public it.avlp.simog.massload.xmlbeans.RitardoType[] getRitardoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RITARDO$0, targetList);
            it.avlp.simog.massload.xmlbeans.RitardoType[] result = new it.avlp.simog.massload.xmlbeans.RitardoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Ritardo" element
     */
    public it.avlp.simog.massload.xmlbeans.RitardoType getRitardoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardoType)get_store().find_element_user(RITARDO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Ritardo" element
     */
    public int sizeOfRitardoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RITARDO$0);
        }
    }
    
    /**
     * Sets array of all "Ritardo" element
     */
    public void setRitardoArray(it.avlp.simog.massload.xmlbeans.RitardoType[] ritardoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(ritardoArray, RITARDO$0);
        }
    }
    
    /**
     * Sets ith "Ritardo" element
     */
    public void setRitardoArray(int i, it.avlp.simog.massload.xmlbeans.RitardoType ritardo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardoType)get_store().find_element_user(RITARDO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(ritardo);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Ritardo" element
     */
    public it.avlp.simog.massload.xmlbeans.RitardoType insertNewRitardo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardoType)get_store().insert_element_user(RITARDO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Ritardo" element
     */
    public it.avlp.simog.massload.xmlbeans.RitardoType addNewRitardo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardoType)get_store().add_element_user(RITARDO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Ritardo" element
     */
    public void removeRitardo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RITARDO$0, i);
        }
    }
}
