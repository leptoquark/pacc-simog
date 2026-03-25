/*
 * XML Type:  FormularioAvvisoAggiudicazione
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML FormularioAvvisoAggiudicazione(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class FormularioAvvisoAggiudicazioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione
{
    
    public FormularioAvvisoAggiudicazioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VALOREAPPALTO$0 = 
        new javax.xml.namespace.QName("", "VALORE_APPALTO");
    private static final javax.xml.namespace.QName INFOAMMINISTRATIVEAGG$2 = 
        new javax.xml.namespace.QName("", "INFO_AMMINISTRATIVE_AGG");
    private static final javax.xml.namespace.QName APPALTOAVVAGG$4 = 
        new javax.xml.namespace.QName("", "APPALTO_AVV_AGG");
    
    
    /**
     * Gets the "VALORE_APPALTO" element
     */
    public it.avlp.simog.massload.xmlbeans.ValoreAppaltoType getVALOREAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ValoreAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType)get_store().find_element_user(VALOREAPPALTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "VALORE_APPALTO" element
     */
    public boolean isSetVALOREAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VALOREAPPALTO$0) != 0;
        }
    }
    
    /**
     * Sets the "VALORE_APPALTO" element
     */
    public void setVALOREAPPALTO(it.avlp.simog.massload.xmlbeans.ValoreAppaltoType valoreappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ValoreAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType)get_store().find_element_user(VALOREAPPALTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType)get_store().add_element_user(VALOREAPPALTO$0);
            }
            target.set(valoreappalto);
        }
    }
    
    /**
     * Appends and returns a new empty "VALORE_APPALTO" element
     */
    public it.avlp.simog.massload.xmlbeans.ValoreAppaltoType addNewVALOREAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ValoreAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ValoreAppaltoType)get_store().add_element_user(VALOREAPPALTO$0);
            return target;
        }
    }
    
    /**
     * Unsets the "VALORE_APPALTO" element
     */
    public void unsetVALOREAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VALOREAPPALTO$0, 0);
        }
    }
    
    /**
     * Gets the "INFO_AMMINISTRATIVE_AGG" element
     */
    public it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg getINFOAMMINISTRATIVEAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg)get_store().find_element_user(INFOAMMINISTRATIVEAGG$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "INFO_AMMINISTRATIVE_AGG" element
     */
    public boolean isSetINFOAMMINISTRATIVEAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INFOAMMINISTRATIVEAGG$2) != 0;
        }
    }
    
    /**
     * Sets the "INFO_AMMINISTRATIVE_AGG" element
     */
    public void setINFOAMMINISTRATIVEAGG(it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg infoamministrativeagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg)get_store().find_element_user(INFOAMMINISTRATIVEAGG$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg)get_store().add_element_user(INFOAMMINISTRATIVEAGG$2);
            }
            target.set(infoamministrativeagg);
        }
    }
    
    /**
     * Appends and returns a new empty "INFO_AMMINISTRATIVE_AGG" element
     */
    public it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg addNewINFOAMMINISTRATIVEAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg)get_store().add_element_user(INFOAMMINISTRATIVEAGG$2);
            return target;
        }
    }
    
    /**
     * Unsets the "INFO_AMMINISTRATIVE_AGG" element
     */
    public void unsetINFOAMMINISTRATIVEAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INFOAMMINISTRATIVEAGG$2, 0);
        }
    }
    
    /**
     * Gets array of all "APPALTO_AVV_AGG" elements
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg[] getAPPALTOAVVAGGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(APPALTOAVVAGG$4, targetList);
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg[] result = new it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "APPALTO_AVV_AGG" element
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg getAPPALTOAVVAGGArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg)get_store().find_element_user(APPALTOAVVAGG$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "APPALTO_AVV_AGG" element
     */
    public int sizeOfAPPALTOAVVAGGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(APPALTOAVVAGG$4);
        }
    }
    
    /**
     * Sets array of all "APPALTO_AVV_AGG" element
     */
    public void setAPPALTOAVVAGGArray(it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg[] appaltoavvaggArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(appaltoavvaggArray, APPALTOAVVAGG$4);
        }
    }
    
    /**
     * Sets ith "APPALTO_AVV_AGG" element
     */
    public void setAPPALTOAVVAGGArray(int i, it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg appaltoavvagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg)get_store().find_element_user(APPALTOAVVAGG$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(appaltoavvagg);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "APPALTO_AVV_AGG" element
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg insertNewAPPALTOAVVAGG(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg)get_store().insert_element_user(APPALTOAVVAGG$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "APPALTO_AVV_AGG" element
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg addNewAPPALTOAVVAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg)get_store().add_element_user(APPALTOAVVAGG$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "APPALTO_AVV_AGG" element
     */
    public void removeAPPALTOAVVAGG(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(APPALTOAVVAGG$4, i);
        }
    }
}
