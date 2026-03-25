/*
 * XML Type:  SubappaltiType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SubappaltiType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SubappaltiType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SubappaltiTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SubappaltiType
{
    
    public SubappaltiTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SUBAPPALTO$0 = 
        new javax.xml.namespace.QName("", "Subappalto");
    
    
    /**
     * Gets array of all "Subappalto" elements
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltoType[] getSubappaltoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SUBAPPALTO$0, targetList);
            it.avlp.simog.massload.xmlbeans.SubappaltoType[] result = new it.avlp.simog.massload.xmlbeans.SubappaltoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Subappalto" element
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltoType getSubappaltoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType)get_store().find_element_user(SUBAPPALTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Subappalto" element
     */
    public int sizeOfSubappaltoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUBAPPALTO$0);
        }
    }
    
    /**
     * Sets array of all "Subappalto" element
     */
    public void setSubappaltoArray(it.avlp.simog.massload.xmlbeans.SubappaltoType[] subappaltoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(subappaltoArray, SUBAPPALTO$0);
        }
    }
    
    /**
     * Sets ith "Subappalto" element
     */
    public void setSubappaltoArray(int i, it.avlp.simog.massload.xmlbeans.SubappaltoType subappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType)get_store().find_element_user(SUBAPPALTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(subappalto);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Subappalto" element
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltoType insertNewSubappalto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType)get_store().insert_element_user(SUBAPPALTO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Subappalto" element
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltoType addNewSubappalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType)get_store().add_element_user(SUBAPPALTO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Subappalto" element
     */
    public void removeSubappalto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUBAPPALTO$0, i);
        }
    }
}
