/*
 * XML Type:  SchedaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SchedaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SchedaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SchedaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SchedaType
{
    
    public SchedaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIGARA$0 = 
        new javax.xml.namespace.QName("", "DatiGara");
    private static final javax.xml.namespace.QName DATISCHEDA$2 = 
        new javax.xml.namespace.QName("", "DatiScheda");
    private static final javax.xml.namespace.QName RESPONSABILI$4 = 
        new javax.xml.namespace.QName("", "Responsabili");
    private static final javax.xml.namespace.QName AGGIUDICATARI$6 = 
        new javax.xml.namespace.QName("", "Aggiudicatari");
    
    
    /**
     * Gets the "DatiGara" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraType getDatiGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().find_element_user(DATIGARA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DatiGara" element
     */
    public void setDatiGara(it.avlp.simog.massload.xmlbeans.DatiGaraType datiGara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().find_element_user(DATIGARA$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().add_element_user(DATIGARA$0);
            }
            target.set(datiGara);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiGara" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraType addNewDatiGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().add_element_user(DATIGARA$0);
            return target;
        }
    }
    
    /**
     * Gets the "DatiScheda" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType getDatiScheda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().find_element_user(DATISCHEDA$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiScheda" element
     */
    public boolean isSetDatiScheda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISCHEDA$2) != 0;
        }
    }
    
    /**
     * Sets the "DatiScheda" element
     */
    public void setDatiScheda(it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType datiScheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().find_element_user(DATISCHEDA$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().add_element_user(DATISCHEDA$2);
            }
            target.set(datiScheda);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiScheda" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType addNewDatiScheda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().add_element_user(DATISCHEDA$2);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiScheda" element
     */
    public void unsetDatiScheda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISCHEDA$2, 0);
        }
    }
    
    /**
     * Gets the "Responsabili" element
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabiliType getResponsabili()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabiliType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().find_element_user(RESPONSABILI$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Responsabili" element
     */
    public boolean isSetResponsabili()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RESPONSABILI$4) != 0;
        }
    }
    
    /**
     * Sets the "Responsabili" element
     */
    public void setResponsabili(it.avlp.simog.massload.xmlbeans.ResponsabiliType responsabili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabiliType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().find_element_user(RESPONSABILI$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().add_element_user(RESPONSABILI$4);
            }
            target.set(responsabili);
        }
    }
    
    /**
     * Appends and returns a new empty "Responsabili" element
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabiliType addNewResponsabili()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabiliType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().add_element_user(RESPONSABILI$4);
            return target;
        }
    }
    
    /**
     * Unsets the "Responsabili" element
     */
    public void unsetResponsabili()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RESPONSABILI$4, 0);
        }
    }
    
    /**
     * Gets the "Aggiudicatari" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatariType getAggiudicatari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatariType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().find_element_user(AGGIUDICATARI$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Aggiudicatari" element
     */
    public boolean isSetAggiudicatari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGGIUDICATARI$6) != 0;
        }
    }
    
    /**
     * Sets the "Aggiudicatari" element
     */
    public void setAggiudicatari(it.avlp.simog.massload.xmlbeans.AggiudicatariType aggiudicatari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatariType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().find_element_user(AGGIUDICATARI$6, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().add_element_user(AGGIUDICATARI$6);
            }
            target.set(aggiudicatari);
        }
    }
    
    /**
     * Appends and returns a new empty "Aggiudicatari" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatariType addNewAggiudicatari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatariType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().add_element_user(AGGIUDICATARI$6);
            return target;
        }
    }
    
    /**
     * Unsets the "Aggiudicatari" element
     */
    public void unsetAggiudicatari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGGIUDICATARI$6, 0);
        }
    }
}
