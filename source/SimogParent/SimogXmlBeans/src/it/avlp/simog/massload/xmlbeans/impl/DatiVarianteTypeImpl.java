/*
 * XML Type:  DatiVarianteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiVarianteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiVarianteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiVarianteTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiVarianteType
{
    
    public DatiVarianteTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VARIANTE$0 = 
        new javax.xml.namespace.QName("", "Variante");
    private static final javax.xml.namespace.QName MOTIVIVARIANTE$2 = 
        new javax.xml.namespace.QName("", "MotiviVariante");
    
    
    /**
     * Gets the "Variante" element
     */
    public it.avlp.simog.massload.xmlbeans.VarianteType getVariante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VarianteType)get_store().find_element_user(VARIANTE$0, 0);
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
    public void setVariante(it.avlp.simog.massload.xmlbeans.VarianteType variante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VarianteType)get_store().find_element_user(VARIANTE$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.VarianteType)get_store().add_element_user(VARIANTE$0);
            }
            target.set(variante);
        }
    }
    
    /**
     * Appends and returns a new empty "Variante" element
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
     * Gets array of all "MotiviVariante" elements
     */
    public it.avlp.simog.massload.xmlbeans.MotivoVarType[] getMotiviVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(MOTIVIVARIANTE$2, targetList);
            it.avlp.simog.massload.xmlbeans.MotivoVarType[] result = new it.avlp.simog.massload.xmlbeans.MotivoVarType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "MotiviVariante" element
     */
    public it.avlp.simog.massload.xmlbeans.MotivoVarType getMotiviVarianteArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoVarType)get_store().find_element_user(MOTIVIVARIANTE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "MotiviVariante" element
     */
    public int sizeOfMotiviVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MOTIVIVARIANTE$2);
        }
    }
    
    /**
     * Sets array of all "MotiviVariante" element
     */
    public void setMotiviVarianteArray(it.avlp.simog.massload.xmlbeans.MotivoVarType[] motiviVarianteArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(motiviVarianteArray, MOTIVIVARIANTE$2);
        }
    }
    
    /**
     * Sets ith "MotiviVariante" element
     */
    public void setMotiviVarianteArray(int i, it.avlp.simog.massload.xmlbeans.MotivoVarType motiviVariante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoVarType)get_store().find_element_user(MOTIVIVARIANTE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(motiviVariante);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "MotiviVariante" element
     */
    public it.avlp.simog.massload.xmlbeans.MotivoVarType insertNewMotiviVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoVarType)get_store().insert_element_user(MOTIVIVARIANTE$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "MotiviVariante" element
     */
    public it.avlp.simog.massload.xmlbeans.MotivoVarType addNewMotiviVariante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoVarType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoVarType)get_store().add_element_user(MOTIVIVARIANTE$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "MotiviVariante" element
     */
    public void removeMotiviVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MOTIVIVARIANTE$2, i);
        }
    }
}
