/*
 * XML Type:  VariantiType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.VariantiType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML VariantiType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class VariantiTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.VariantiType
{
    
    public VariantiTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VARIANTE$0 = 
        new javax.xml.namespace.QName("", "Variante");
    
    
    /**
     * Gets array of all "Variante" elements
     */
    public it.avlp.simog.massload.xmlbeans.VarianteType[] getVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(VARIANTE$0, targetList);
            it.avlp.simog.massload.xmlbeans.VarianteType[] result = new it.avlp.simog.massload.xmlbeans.VarianteType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Variante" element
     */
    public it.avlp.simog.massload.xmlbeans.VarianteType getVarianteArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VarianteType)get_store().find_element_user(VARIANTE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Variante" element
     */
    public int sizeOfVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VARIANTE$0);
        }
    }
    
    /**
     * Sets array of all "Variante" element
     */
    public void setVarianteArray(it.avlp.simog.massload.xmlbeans.VarianteType[] varianteArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(varianteArray, VARIANTE$0);
        }
    }
    
    /**
     * Sets ith "Variante" element
     */
    public void setVarianteArray(int i, it.avlp.simog.massload.xmlbeans.VarianteType variante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VarianteType)get_store().find_element_user(VARIANTE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(variante);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Variante" element
     */
    public it.avlp.simog.massload.xmlbeans.VarianteType insertNewVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VarianteType)get_store().insert_element_user(VARIANTE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Variante" element
     */
    public it.avlp.simog.massload.xmlbeans.VarianteType addNewVariante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VarianteType)get_store().add_element_user(VARIANTE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Variante" element
     */
    public void removeVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VARIANTE$0, i);
        }
    }
}
