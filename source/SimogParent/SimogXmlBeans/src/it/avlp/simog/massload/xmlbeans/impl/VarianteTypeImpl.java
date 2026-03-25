/*
 * XML Type:  VarianteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.VarianteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML VarianteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class VarianteTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.VarianteType
{
    
    public VarianteTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VARIANTE$0 = 
        new javax.xml.namespace.QName("", "Variante");
    private static final javax.xml.namespace.QName MOTIVI$2 = 
        new javax.xml.namespace.QName("", "Motivi");
    
    
    /**
     * Gets the "Variante" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVarianteType getVariante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarianteType)get_store().find_element_user(VARIANTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Variante" element
     */
    public void setVariante(it.avlp.simog.massload.xmlbeans.RecVarianteType variante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarianteType)get_store().find_element_user(VARIANTE$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RecVarianteType)get_store().add_element_user(VARIANTE$0);
            }
            target.set(variante);
        }
    }
    
    /**
     * Appends and returns a new empty "Variante" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVarianteType addNewVariante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarianteType)get_store().add_element_user(VARIANTE$0);
            return target;
        }
    }
    
    /**
     * Gets array of all "Motivi" elements
     */
    public it.avlp.simog.massload.xmlbeans.RecMotivoVarType[] getMotiviArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(MOTIVI$2, targetList);
            it.avlp.simog.massload.xmlbeans.RecMotivoVarType[] result = new it.avlp.simog.massload.xmlbeans.RecMotivoVarType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Motivi" element
     */
    public it.avlp.simog.massload.xmlbeans.RecMotivoVarType getMotiviArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecMotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecMotivoVarType)get_store().find_element_user(MOTIVI$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Motivi" element
     */
    public int sizeOfMotiviArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MOTIVI$2);
        }
    }
    
    /**
     * Sets array of all "Motivi" element
     */
    public void setMotiviArray(it.avlp.simog.massload.xmlbeans.RecMotivoVarType[] motiviArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(motiviArray, MOTIVI$2);
        }
    }
    
    /**
     * Sets ith "Motivi" element
     */
    public void setMotiviArray(int i, it.avlp.simog.massload.xmlbeans.RecMotivoVarType motivi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecMotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecMotivoVarType)get_store().find_element_user(MOTIVI$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(motivi);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Motivi" element
     */
    public it.avlp.simog.massload.xmlbeans.RecMotivoVarType insertNewMotivi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecMotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecMotivoVarType)get_store().insert_element_user(MOTIVI$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Motivi" element
     */
    public it.avlp.simog.massload.xmlbeans.RecMotivoVarType addNewMotivi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecMotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecMotivoVarType)get_store().add_element_user(MOTIVI$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "Motivi" element
     */
    public void removeMotivi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MOTIVI$2, i);
        }
    }
}
