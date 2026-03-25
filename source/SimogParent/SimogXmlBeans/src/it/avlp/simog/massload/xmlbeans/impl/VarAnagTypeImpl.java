/*
 * XML Type:  VarAnagType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.VarAnagType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML VarAnagType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class VarAnagTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.VarAnagType
{
    
    public VarAnagTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VARIAZIONEANAG$0 = 
        new javax.xml.namespace.QName("", "VariazioneAnag");
    
    
    /**
     * Gets array of all "VariazioneAnag" elements
     */
    public it.avlp.simog.massload.xmlbeans.RecVarAnagType[] getVariazioneAnagArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(VARIAZIONEANAG$0, targetList);
            it.avlp.simog.massload.xmlbeans.RecVarAnagType[] result = new it.avlp.simog.massload.xmlbeans.RecVarAnagType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "VariazioneAnag" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVarAnagType getVariazioneAnagArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarAnagType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarAnagType)get_store().find_element_user(VARIAZIONEANAG$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "VariazioneAnag" element
     */
    public int sizeOfVariazioneAnagArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VARIAZIONEANAG$0);
        }
    }
    
    /**
     * Sets array of all "VariazioneAnag" element
     */
    public void setVariazioneAnagArray(it.avlp.simog.massload.xmlbeans.RecVarAnagType[] variazioneAnagArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(variazioneAnagArray, VARIAZIONEANAG$0);
        }
    }
    
    /**
     * Sets ith "VariazioneAnag" element
     */
    public void setVariazioneAnagArray(int i, it.avlp.simog.massload.xmlbeans.RecVarAnagType variazioneAnag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarAnagType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarAnagType)get_store().find_element_user(VARIAZIONEANAG$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(variazioneAnag);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "VariazioneAnag" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVarAnagType insertNewVariazioneAnag(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarAnagType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarAnagType)get_store().insert_element_user(VARIAZIONEANAG$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "VariazioneAnag" element
     */
    public it.avlp.simog.massload.xmlbeans.RecVarAnagType addNewVariazioneAnag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarAnagType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarAnagType)get_store().add_element_user(VARIAZIONEANAG$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "VariazioneAnag" element
     */
    public void removeVariazioneAnag(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VARIAZIONEANAG$0, i);
        }
    }
}
