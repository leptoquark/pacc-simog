/*
 * XML Type:  ResponsabiliType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ResponsabiliType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ResponsabiliType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ResponsabiliTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ResponsabiliType
{
    
    public ResponsabiliTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RESPONSABILE$0 = 
        new javax.xml.namespace.QName("", "Responsabile");
    
    
    /**
     * Gets array of all "Responsabile" elements
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabileType[] getResponsabileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RESPONSABILE$0, targetList);
            it.avlp.simog.massload.xmlbeans.ResponsabileType[] result = new it.avlp.simog.massload.xmlbeans.ResponsabileType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Responsabile" element
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabileType getResponsabileArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType)get_store().find_element_user(RESPONSABILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Responsabile" element
     */
    public int sizeOfResponsabileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RESPONSABILE$0);
        }
    }
    
    /**
     * Sets array of all "Responsabile" element
     */
    public void setResponsabileArray(it.avlp.simog.massload.xmlbeans.ResponsabileType[] responsabileArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(responsabileArray, RESPONSABILE$0);
        }
    }
    
    /**
     * Sets ith "Responsabile" element
     */
    public void setResponsabileArray(int i, it.avlp.simog.massload.xmlbeans.ResponsabileType responsabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType)get_store().find_element_user(RESPONSABILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(responsabile);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Responsabile" element
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabileType insertNewResponsabile(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType)get_store().insert_element_user(RESPONSABILE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Responsabile" element
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabileType addNewResponsabile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType)get_store().add_element_user(RESPONSABILE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Responsabile" element
     */
    public void removeResponsabile(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RESPONSABILE$0, i);
        }
    }
}
