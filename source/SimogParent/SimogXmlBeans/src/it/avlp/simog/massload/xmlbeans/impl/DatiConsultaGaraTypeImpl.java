/*
 * XML Type:  DatiConsultaGaraType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiConsultaGaraType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiConsultaGaraTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType
{
    
    public DatiConsultaGaraTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATICONSULTAGARA$0 = 
        new javax.xml.namespace.QName("", "DatiConsultaGara");
    
    
    /**
     * Gets array of all "DatiConsultaGara" elements
     */
    public it.avlp.simog.massload.xmlbeans.SchedaType[] getDatiConsultaGaraArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DATICONSULTAGARA$0, targetList);
            it.avlp.simog.massload.xmlbeans.SchedaType[] result = new it.avlp.simog.massload.xmlbeans.SchedaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "DatiConsultaGara" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaType getDatiConsultaGaraArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().find_element_user(DATICONSULTAGARA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "DatiConsultaGara" element
     */
    public int sizeOfDatiConsultaGaraArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATICONSULTAGARA$0);
        }
    }
    
    /**
     * Sets array of all "DatiConsultaGara" element
     */
    public void setDatiConsultaGaraArray(it.avlp.simog.massload.xmlbeans.SchedaType[] datiConsultaGaraArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(datiConsultaGaraArray, DATICONSULTAGARA$0);
        }
    }
    
    /**
     * Sets ith "DatiConsultaGara" element
     */
    public void setDatiConsultaGaraArray(int i, it.avlp.simog.massload.xmlbeans.SchedaType datiConsultaGara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().find_element_user(DATICONSULTAGARA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(datiConsultaGara);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "DatiConsultaGara" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaType insertNewDatiConsultaGara(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().insert_element_user(DATICONSULTAGARA$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "DatiConsultaGara" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaType addNewDatiConsultaGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().add_element_user(DATICONSULTAGARA$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "DatiConsultaGara" element
     */
    public void removeDatiConsultaGara(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATICONSULTAGARA$0, i);
        }
    }
}
