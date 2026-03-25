/*
 * XML Type:  DatiAggiudicazioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiAggiudicazioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiAggiudicazioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType
{
    
    public DatiAggiudicazioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATICOMUNI$0 = 
        new javax.xml.namespace.QName("", "DatiComuni");
    private static final javax.xml.namespace.QName PUBBLICAZIONE$2 = 
        new javax.xml.namespace.QName("", "Pubblicazione");
    private static final javax.xml.namespace.QName SCHEDACOMPLETA$4 = 
        new javax.xml.namespace.QName("", "SchedaCompleta");
    
    
    /**
     * Gets the "DatiComuni" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType getDatiComuni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType)get_store().find_element_user(DATICOMUNI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DatiComuni" element
     */
    public void setDatiComuni(it.avlp.simog.massload.xmlbeans.DatiComuniType datiComuni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType)get_store().find_element_user(DATICOMUNI$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiComuniType)get_store().add_element_user(DATICOMUNI$0);
            }
            target.set(datiComuni);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiComuni" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType addNewDatiComuni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType)get_store().add_element_user(DATICOMUNI$0);
            return target;
        }
    }
    
    /**
     * Gets the "Pubblicazione" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneType getPubblicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Pubblicazione" element
     */
    public boolean isSetPubblicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PUBBLICAZIONE$2) != 0;
        }
    }
    
    /**
     * Sets the "Pubblicazione" element
     */
    public void setPubblicazione(it.avlp.simog.massload.xmlbeans.PubblicazioneType pubblicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONE$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONE$2);
            }
            target.set(pubblicazione);
        }
    }
    
    /**
     * Appends and returns a new empty "Pubblicazione" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneType addNewPubblicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONE$2);
            return target;
        }
    }
    
    /**
     * Unsets the "Pubblicazione" element
     */
    public void unsetPubblicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PUBBLICAZIONE$2, 0);
        }
    }
    
    /**
     * Gets array of all "SchedaCompleta" elements
     */
    public it.avlp.simog.massload.xmlbeans.SchedaCompletaType[] getSchedaCompletaArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SCHEDACOMPLETA$4, targetList);
            it.avlp.simog.massload.xmlbeans.SchedaCompletaType[] result = new it.avlp.simog.massload.xmlbeans.SchedaCompletaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "SchedaCompleta" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaCompletaType getSchedaCompletaArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaCompletaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaCompletaType)get_store().find_element_user(SCHEDACOMPLETA$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "SchedaCompleta" element
     */
    public int sizeOfSchedaCompletaArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SCHEDACOMPLETA$4);
        }
    }
    
    /**
     * Sets array of all "SchedaCompleta" element
     */
    public void setSchedaCompletaArray(it.avlp.simog.massload.xmlbeans.SchedaCompletaType[] schedaCompletaArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(schedaCompletaArray, SCHEDACOMPLETA$4);
        }
    }
    
    /**
     * Sets ith "SchedaCompleta" element
     */
    public void setSchedaCompletaArray(int i, it.avlp.simog.massload.xmlbeans.SchedaCompletaType schedaCompleta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaCompletaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaCompletaType)get_store().find_element_user(SCHEDACOMPLETA$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(schedaCompleta);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "SchedaCompleta" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaCompletaType insertNewSchedaCompleta(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaCompletaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaCompletaType)get_store().insert_element_user(SCHEDACOMPLETA$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "SchedaCompleta" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaCompletaType addNewSchedaCompleta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaCompletaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaCompletaType)get_store().add_element_user(SCHEDACOMPLETA$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "SchedaCompleta" element
     */
    public void removeSchedaCompleta(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SCHEDACOMPLETA$4, i);
        }
    }
}
