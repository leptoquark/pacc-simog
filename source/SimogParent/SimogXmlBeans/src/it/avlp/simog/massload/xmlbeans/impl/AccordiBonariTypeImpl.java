/*
 * XML Type:  AccordiBonariType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AccordiBonariType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AccordiBonariType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AccordiBonariTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AccordiBonariType
{
    
    public AccordiBonariTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACCORDOBONARIO$0 = 
        new javax.xml.namespace.QName("", "AccordoBonario");
    
    
    /**
     * Gets array of all "AccordoBonario" elements
     */
    public it.avlp.simog.massload.xmlbeans.AccordoBonarioType[] getAccordoBonarioArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ACCORDOBONARIO$0, targetList);
            it.avlp.simog.massload.xmlbeans.AccordoBonarioType[] result = new it.avlp.simog.massload.xmlbeans.AccordoBonarioType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "AccordoBonario" element
     */
    public it.avlp.simog.massload.xmlbeans.AccordoBonarioType getAccordoBonarioArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AccordoBonarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AccordoBonarioType)get_store().find_element_user(ACCORDOBONARIO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "AccordoBonario" element
     */
    public int sizeOfAccordoBonarioArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACCORDOBONARIO$0);
        }
    }
    
    /**
     * Sets array of all "AccordoBonario" element
     */
    public void setAccordoBonarioArray(it.avlp.simog.massload.xmlbeans.AccordoBonarioType[] accordoBonarioArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(accordoBonarioArray, ACCORDOBONARIO$0);
        }
    }
    
    /**
     * Sets ith "AccordoBonario" element
     */
    public void setAccordoBonarioArray(int i, it.avlp.simog.massload.xmlbeans.AccordoBonarioType accordoBonario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AccordoBonarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AccordoBonarioType)get_store().find_element_user(ACCORDOBONARIO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(accordoBonario);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "AccordoBonario" element
     */
    public it.avlp.simog.massload.xmlbeans.AccordoBonarioType insertNewAccordoBonario(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AccordoBonarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AccordoBonarioType)get_store().insert_element_user(ACCORDOBONARIO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "AccordoBonario" element
     */
    public it.avlp.simog.massload.xmlbeans.AccordoBonarioType addNewAccordoBonario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AccordoBonarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AccordoBonarioType)get_store().add_element_user(ACCORDOBONARIO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "AccordoBonario" element
     */
    public void removeAccordoBonario(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACCORDOBONARIO$0, i);
        }
    }
}
