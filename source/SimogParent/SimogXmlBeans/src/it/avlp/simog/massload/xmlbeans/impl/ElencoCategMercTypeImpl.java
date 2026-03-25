/*
 * XML Type:  ElencoCategMercType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ElencoCategMercType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ElencoCategMercType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ElencoCategMercTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ElencoCategMercType
{
    
    public ElencoCategMercTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CATEGORIA$0 = 
        new javax.xml.namespace.QName("", "CATEGORIA");
    
    
    /**
     * Gets array of all "CATEGORIA" elements
     */
    public java.lang.String[] getCATEGORIAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CATEGORIA$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "CATEGORIA" element
     */
    public java.lang.String getCATEGORIAArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CATEGORIA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "CATEGORIA" elements
     */
    public it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType[] xgetCATEGORIAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CATEGORIA$0, targetList);
            it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType[] result = new it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "CATEGORIA" element
     */
    public it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType xgetCATEGORIAArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)get_store().find_element_user(CATEGORIA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)target;
        }
    }
    
    /**
     * Returns number of "CATEGORIA" element
     */
    public int sizeOfCATEGORIAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CATEGORIA$0);
        }
    }
    
    /**
     * Sets array of all "CATEGORIA" element
     */
    public void setCATEGORIAArray(java.lang.String[] categoriaArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(categoriaArray, CATEGORIA$0);
        }
    }
    
    /**
     * Sets ith "CATEGORIA" element
     */
    public void setCATEGORIAArray(int i, java.lang.String categoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CATEGORIA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(categoria);
        }
    }
    
    /**
     * Sets (as xml) array of all "CATEGORIA" element
     */
    public void xsetCATEGORIAArray(it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType[]categoriaArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(categoriaArray, CATEGORIA$0);
        }
    }
    
    /**
     * Sets (as xml) ith "CATEGORIA" element
     */
    public void xsetCATEGORIAArray(int i, it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType categoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)get_store().find_element_user(CATEGORIA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(categoria);
        }
    }
    
    /**
     * Inserts the value as the ith "CATEGORIA" element
     */
    public void insertCATEGORIA(int i, java.lang.String categoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(CATEGORIA$0, i);
            target.setStringValue(categoria);
        }
    }
    
    /**
     * Appends the value as the last "CATEGORIA" element
     */
    public void addCATEGORIA(java.lang.String categoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CATEGORIA$0);
            target.setStringValue(categoria);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CATEGORIA" element
     */
    public it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType insertNewCATEGORIA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)get_store().insert_element_user(CATEGORIA$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CATEGORIA" element
     */
    public it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType addNewCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)get_store().add_element_user(CATEGORIA$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "CATEGORIA" element
     */
    public void removeCATEGORIA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CATEGORIA$0, i);
        }
    }
}
