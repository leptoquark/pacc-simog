/*
 * XML Type:  listaCentriCostoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ListaCentriCostoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML listaCentriCostoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ListaCentriCostoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ListaCentriCostoType
{
    
    public ListaCentriCostoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CENTRODICOSTO$0 = 
        new javax.xml.namespace.QName("", "centroDiCosto");
    
    
    /**
     * Gets array of all "centroDiCosto" elements
     */
    public it.avlp.simog.massload.xmlbeans.CentroDiCostoType[] getCentroDiCostoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CENTRODICOSTO$0, targetList);
            it.avlp.simog.massload.xmlbeans.CentroDiCostoType[] result = new it.avlp.simog.massload.xmlbeans.CentroDiCostoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "centroDiCosto" element
     */
    public it.avlp.simog.massload.xmlbeans.CentroDiCostoType getCentroDiCostoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CentroDiCostoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CentroDiCostoType)get_store().find_element_user(CENTRODICOSTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "centroDiCosto" element
     */
    public int sizeOfCentroDiCostoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CENTRODICOSTO$0);
        }
    }
    
    /**
     * Sets array of all "centroDiCosto" element
     */
    public void setCentroDiCostoArray(it.avlp.simog.massload.xmlbeans.CentroDiCostoType[] centroDiCostoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(centroDiCostoArray, CENTRODICOSTO$0);
        }
    }
    
    /**
     * Sets ith "centroDiCosto" element
     */
    public void setCentroDiCostoArray(int i, it.avlp.simog.massload.xmlbeans.CentroDiCostoType centroDiCosto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CentroDiCostoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CentroDiCostoType)get_store().find_element_user(CENTRODICOSTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(centroDiCosto);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "centroDiCosto" element
     */
    public it.avlp.simog.massload.xmlbeans.CentroDiCostoType insertNewCentroDiCosto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CentroDiCostoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CentroDiCostoType)get_store().insert_element_user(CENTRODICOSTO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "centroDiCosto" element
     */
    public it.avlp.simog.massload.xmlbeans.CentroDiCostoType addNewCentroDiCosto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CentroDiCostoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CentroDiCostoType)get_store().add_element_user(CENTRODICOSTO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "centroDiCosto" element
     */
    public void removeCentroDiCosto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CENTRODICOSTO$0, i);
        }
    }
}
