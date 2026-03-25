/*
 * XML Type:  TerritorioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TerritorioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML TerritorioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class TerritorioTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.TerritorioType
{
    
    public TerritorioTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODREGIONEISTAT$0 = 
        new javax.xml.namespace.QName("", "CodRegioneIstat");
    
    
    /**
     * Gets array of all "CodRegioneIstat" elements
     */
    public java.lang.String[] getCodRegioneIstatArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CODREGIONEISTAT$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "CodRegioneIstat" element
     */
    public java.lang.String getCodRegioneIstatArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODREGIONEISTAT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "CodRegioneIstat" elements
     */
    public it.avlp.simog.massload.xmlbeans.LuogoIstatType[] xgetCodRegioneIstatArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CODREGIONEISTAT$0, targetList);
            it.avlp.simog.massload.xmlbeans.LuogoIstatType[] result = new it.avlp.simog.massload.xmlbeans.LuogoIstatType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "CodRegioneIstat" element
     */
    public it.avlp.simog.massload.xmlbeans.LuogoIstatType xgetCodRegioneIstatArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().find_element_user(CODREGIONEISTAT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (it.avlp.simog.massload.xmlbeans.LuogoIstatType)target;
        }
    }
    
    /**
     * Returns number of "CodRegioneIstat" element
     */
    public int sizeOfCodRegioneIstatArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODREGIONEISTAT$0);
        }
    }
    
    /**
     * Sets array of all "CodRegioneIstat" element
     */
    public void setCodRegioneIstatArray(java.lang.String[] codRegioneIstatArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(codRegioneIstatArray, CODREGIONEISTAT$0);
        }
    }
    
    /**
     * Sets ith "CodRegioneIstat" element
     */
    public void setCodRegioneIstatArray(int i, java.lang.String codRegioneIstat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODREGIONEISTAT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(codRegioneIstat);
        }
    }
    
    /**
     * Sets (as xml) array of all "CodRegioneIstat" element
     */
    public void xsetCodRegioneIstatArray(it.avlp.simog.massload.xmlbeans.LuogoIstatType[]codRegioneIstatArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(codRegioneIstatArray, CODREGIONEISTAT$0);
        }
    }
    
    /**
     * Sets (as xml) ith "CodRegioneIstat" element
     */
    public void xsetCodRegioneIstatArray(int i, it.avlp.simog.massload.xmlbeans.LuogoIstatType codRegioneIstat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().find_element_user(CODREGIONEISTAT$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(codRegioneIstat);
        }
    }
    
    /**
     * Inserts the value as the ith "CodRegioneIstat" element
     */
    public void insertCodRegioneIstat(int i, java.lang.String codRegioneIstat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(CODREGIONEISTAT$0, i);
            target.setStringValue(codRegioneIstat);
        }
    }
    
    /**
     * Appends the value as the last "CodRegioneIstat" element
     */
    public void addCodRegioneIstat(java.lang.String codRegioneIstat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODREGIONEISTAT$0);
            target.setStringValue(codRegioneIstat);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CodRegioneIstat" element
     */
    public it.avlp.simog.massload.xmlbeans.LuogoIstatType insertNewCodRegioneIstat(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().insert_element_user(CODREGIONEISTAT$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CodRegioneIstat" element
     */
    public it.avlp.simog.massload.xmlbeans.LuogoIstatType addNewCodRegioneIstat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().add_element_user(CODREGIONEISTAT$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "CodRegioneIstat" element
     */
    public void removeCodRegioneIstat(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODREGIONEISTAT$0, i);
        }
    }
}
