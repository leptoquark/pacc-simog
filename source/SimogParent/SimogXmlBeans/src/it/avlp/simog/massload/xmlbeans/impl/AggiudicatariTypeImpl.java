/*
 * XML Type:  AggiudicatariType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AggiudicatariType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AggiudicatariType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AggiudicatariTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AggiudicatariType
{
    
    public AggiudicatariTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AGGIUDICATARIO$0 = 
        new javax.xml.namespace.QName("", "Aggiudicatario");
    
    
    /**
     * Gets array of all "Aggiudicatario" elements
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] getAggiudicatarioArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AGGIUDICATARIO$0, targetList);
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] result = new it.avlp.simog.massload.xmlbeans.AggiudicatarioType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Aggiudicatario" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType getAggiudicatarioArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().find_element_user(AGGIUDICATARIO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Aggiudicatario" element
     */
    public int sizeOfAggiudicatarioArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGGIUDICATARIO$0);
        }
    }
    
    /**
     * Sets array of all "Aggiudicatario" element
     */
    public void setAggiudicatarioArray(it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] aggiudicatarioArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(aggiudicatarioArray, AGGIUDICATARIO$0);
        }
    }
    
    /**
     * Sets ith "Aggiudicatario" element
     */
    public void setAggiudicatarioArray(int i, it.avlp.simog.massload.xmlbeans.AggiudicatarioType aggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().find_element_user(AGGIUDICATARIO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(aggiudicatario);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Aggiudicatario" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType insertNewAggiudicatario(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().insert_element_user(AGGIUDICATARIO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Aggiudicatario" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType addNewAggiudicatario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().add_element_user(AGGIUDICATARIO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Aggiudicatario" element
     */
    public void removeAggiudicatario(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGGIUDICATARIO$0, i);
        }
    }
}
