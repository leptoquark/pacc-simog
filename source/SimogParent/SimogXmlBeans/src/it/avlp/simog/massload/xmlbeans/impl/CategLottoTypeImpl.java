/*
 * XML Type:  CategLottoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CategLottoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML CategLottoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CategLottoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CategLottoType
{
    
    public CategLottoTypeImpl(org.apache.xmlbeans.SchemaType sType)
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
    public it.avlp.simog.massload.xmlbeans.CategoriaType[] xgetCATEGORIAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CATEGORIA$0, targetList);
            it.avlp.simog.massload.xmlbeans.CategoriaType[] result = new it.avlp.simog.massload.xmlbeans.CategoriaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "CATEGORIA" element
     */
    public it.avlp.simog.massload.xmlbeans.CategoriaType xgetCATEGORIAArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().find_element_user(CATEGORIA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (it.avlp.simog.massload.xmlbeans.CategoriaType)target;
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
    public void xsetCATEGORIAArray(it.avlp.simog.massload.xmlbeans.CategoriaType[]categoriaArray)
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
    public void xsetCATEGORIAArray(int i, it.avlp.simog.massload.xmlbeans.CategoriaType categoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().find_element_user(CATEGORIA$0, i);
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
    public it.avlp.simog.massload.xmlbeans.CategoriaType insertNewCATEGORIA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().insert_element_user(CATEGORIA$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CATEGORIA" element
     */
    public it.avlp.simog.massload.xmlbeans.CategoriaType addNewCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().add_element_user(CATEGORIA$0);
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
