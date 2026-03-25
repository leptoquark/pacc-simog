/*
 * XML Type:  AvanzamentiType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AvanzamentiType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AvanzamentiType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AvanzamentiTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AvanzamentiType
{
    
    public AvanzamentiTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AVANZAMENTO$0 = 
        new javax.xml.namespace.QName("", "Avanzamento");
    
    
    /**
     * Gets array of all "Avanzamento" elements
     */
    public it.avlp.simog.massload.xmlbeans.AvanzamentoType[] getAvanzamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AVANZAMENTO$0, targetList);
            it.avlp.simog.massload.xmlbeans.AvanzamentoType[] result = new it.avlp.simog.massload.xmlbeans.AvanzamentoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Avanzamento" element
     */
    public it.avlp.simog.massload.xmlbeans.AvanzamentoType getAvanzamentoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentoType)get_store().find_element_user(AVANZAMENTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Avanzamento" element
     */
    public int sizeOfAvanzamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AVANZAMENTO$0);
        }
    }
    
    /**
     * Sets array of all "Avanzamento" element
     */
    public void setAvanzamentoArray(it.avlp.simog.massload.xmlbeans.AvanzamentoType[] avanzamentoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(avanzamentoArray, AVANZAMENTO$0);
        }
    }
    
    /**
     * Sets ith "Avanzamento" element
     */
    public void setAvanzamentoArray(int i, it.avlp.simog.massload.xmlbeans.AvanzamentoType avanzamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentoType)get_store().find_element_user(AVANZAMENTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(avanzamento);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Avanzamento" element
     */
    public it.avlp.simog.massload.xmlbeans.AvanzamentoType insertNewAvanzamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentoType)get_store().insert_element_user(AVANZAMENTO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Avanzamento" element
     */
    public it.avlp.simog.massload.xmlbeans.AvanzamentoType addNewAvanzamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentoType)get_store().add_element_user(AVANZAMENTO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Avanzamento" element
     */
    public void removeAvanzamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AVANZAMENTO$0, i);
        }
    }
}
