/*
 * XML Type:  SospensioniType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SospensioniType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SospensioniType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SospensioniTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SospensioniType
{
    
    public SospensioniTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SOSPENSIONE$0 = 
        new javax.xml.namespace.QName("", "Sospensione");
    
    
    /**
     * Gets array of all "Sospensione" elements
     */
    public it.avlp.simog.massload.xmlbeans.SospensioneType[] getSospensioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SOSPENSIONE$0, targetList);
            it.avlp.simog.massload.xmlbeans.SospensioneType[] result = new it.avlp.simog.massload.xmlbeans.SospensioneType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Sospensione" element
     */
    public it.avlp.simog.massload.xmlbeans.SospensioneType getSospensioneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SospensioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SospensioneType)get_store().find_element_user(SOSPENSIONE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Sospensione" element
     */
    public int sizeOfSospensioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SOSPENSIONE$0);
        }
    }
    
    /**
     * Sets array of all "Sospensione" element
     */
    public void setSospensioneArray(it.avlp.simog.massload.xmlbeans.SospensioneType[] sospensioneArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(sospensioneArray, SOSPENSIONE$0);
        }
    }
    
    /**
     * Sets ith "Sospensione" element
     */
    public void setSospensioneArray(int i, it.avlp.simog.massload.xmlbeans.SospensioneType sospensione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SospensioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SospensioneType)get_store().find_element_user(SOSPENSIONE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(sospensione);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Sospensione" element
     */
    public it.avlp.simog.massload.xmlbeans.SospensioneType insertNewSospensione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SospensioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SospensioneType)get_store().insert_element_user(SOSPENSIONE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Sospensione" element
     */
    public it.avlp.simog.massload.xmlbeans.SospensioneType addNewSospensione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SospensioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SospensioneType)get_store().add_element_user(SOSPENSIONE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Sospensione" element
     */
    public void removeSospensione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SOSPENSIONE$0, i);
        }
    }
}
