/*
 * XML Type:  Scheda_AType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SchedaAType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML Scheda_AType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SchedaATypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SchedaAType
{
    
    public SchedaATypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATICOMUNI$0 = 
        new javax.xml.namespace.QName("", "DatiComuni");
    private static final javax.xml.namespace.QName PUBBLICAZIONE$2 = 
        new javax.xml.namespace.QName("", "Pubblicazione");
    private static final javax.xml.namespace.QName AGGIUDICAZIONE$4 = 
        new javax.xml.namespace.QName("", "Aggiudicazione");
    
    
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
     * Gets array of all "Aggiudicazione" elements
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType[] getAggiudicazioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AGGIUDICAZIONE$4, targetList);
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType[] result = new it.avlp.simog.massload.xmlbeans.AggiudicazioneType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Aggiudicazione" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType getAggiudicazioneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().find_element_user(AGGIUDICAZIONE$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Aggiudicazione" element
     */
    public int sizeOfAggiudicazioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGGIUDICAZIONE$4);
        }
    }
    
    /**
     * Sets array of all "Aggiudicazione" element
     */
    public void setAggiudicazioneArray(it.avlp.simog.massload.xmlbeans.AggiudicazioneType[] aggiudicazioneArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(aggiudicazioneArray, AGGIUDICAZIONE$4);
        }
    }
    
    /**
     * Sets ith "Aggiudicazione" element
     */
    public void setAggiudicazioneArray(int i, it.avlp.simog.massload.xmlbeans.AggiudicazioneType aggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().find_element_user(AGGIUDICAZIONE$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(aggiudicazione);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Aggiudicazione" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType insertNewAggiudicazione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().insert_element_user(AGGIUDICAZIONE$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Aggiudicazione" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType addNewAggiudicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().add_element_user(AGGIUDICAZIONE$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "Aggiudicazione" element
     */
    public void removeAggiudicazione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGGIUDICAZIONE$4, i);
        }
    }
}
