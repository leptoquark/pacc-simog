/*
 * XML Type:  DatiGaraType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiGaraType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiGaraType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiGaraTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiGaraType
{
    
    public DatiGaraTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GARA$0 = 
        new javax.xml.namespace.QName("", "Gara");
    private static final javax.xml.namespace.QName LOTTO$2 = 
        new javax.xml.namespace.QName("", "Lotto");
    private static final javax.xml.namespace.QName REQUISITO$4 = 
        new javax.xml.namespace.QName("", "Requisito");
    
    
    /**
     * Gets the "Gara" element
     */
    public it.avlp.simog.massload.xmlbeans.GaraType getGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.GaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().find_element_user(GARA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Gara" element
     */
    public void setGara(it.avlp.simog.massload.xmlbeans.GaraType gara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.GaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().find_element_user(GARA$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().add_element_user(GARA$0);
            }
            target.set(gara);
        }
    }
    
    /**
     * Appends and returns a new empty "Gara" element
     */
    public it.avlp.simog.massload.xmlbeans.GaraType addNewGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.GaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().add_element_user(GARA$0);
            return target;
        }
    }
    
    /**
     * Gets the "Lotto" element
     */
    public it.avlp.simog.massload.xmlbeans.LottoType getLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().find_element_user(LOTTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Lotto" element
     */
    public boolean isSetLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOTTO$2) != 0;
        }
    }
    
    /**
     * Sets the "Lotto" element
     */
    public void setLotto(it.avlp.simog.massload.xmlbeans.LottoType lotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().find_element_user(LOTTO$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().add_element_user(LOTTO$2);
            }
            target.set(lotto);
        }
    }
    
    /**
     * Appends and returns a new empty "Lotto" element
     */
    public it.avlp.simog.massload.xmlbeans.LottoType addNewLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().add_element_user(LOTTO$2);
            return target;
        }
    }
    
    /**
     * Unsets the "Lotto" element
     */
    public void unsetLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOTTO$2, 0);
        }
    }
    
    /**
     * Gets array of all "Requisito" elements
     */
    public it.avlp.simog.massload.xmlbeans.ReqGaraType[] getRequisitoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(REQUISITO$4, targetList);
            it.avlp.simog.massload.xmlbeans.ReqGaraType[] result = new it.avlp.simog.massload.xmlbeans.ReqGaraType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Requisito" element
     */
    public it.avlp.simog.massload.xmlbeans.ReqGaraType getRequisitoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().find_element_user(REQUISITO$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Requisito" element
     */
    public int sizeOfRequisitoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REQUISITO$4);
        }
    }
    
    /**
     * Sets array of all "Requisito" element
     */
    public void setRequisitoArray(it.avlp.simog.massload.xmlbeans.ReqGaraType[] requisitoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(requisitoArray, REQUISITO$4);
        }
    }
    
    /**
     * Sets ith "Requisito" element
     */
    public void setRequisitoArray(int i, it.avlp.simog.massload.xmlbeans.ReqGaraType requisito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().find_element_user(REQUISITO$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(requisito);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Requisito" element
     */
    public it.avlp.simog.massload.xmlbeans.ReqGaraType insertNewRequisito(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().insert_element_user(REQUISITO$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Requisito" element
     */
    public it.avlp.simog.massload.xmlbeans.ReqGaraType addNewRequisito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().add_element_user(REQUISITO$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "Requisito" element
     */
    public void removeRequisito(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REQUISITO$4, i);
        }
    }
}
