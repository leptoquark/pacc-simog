/*
 * XML Type:  RecVarAnagType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecVarAnagType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RecVarAnagType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RecVarAnagTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RecVarAnagType
{
    
    public RecVarAnagTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MOTIVO$0 = 
        new javax.xml.namespace.QName("", "MOTIVO");
    private static final javax.xml.namespace.QName RIFERIMENTO$2 = 
        new javax.xml.namespace.QName("", "Riferimento");
    private static final javax.xml.namespace.QName RESPONSABILI$4 = 
        new javax.xml.namespace.QName("", "Responsabili");
    private static final javax.xml.namespace.QName AGGIUDICATARI$6 = 
        new javax.xml.namespace.QName("", "Aggiudicatari");
    private static final javax.xml.namespace.QName DITTEAUSILIARIE$8 = 
        new javax.xml.namespace.QName("", "DitteAusiliarie");
    private static final javax.xml.namespace.QName POSIZIONI$10 = 
        new javax.xml.namespace.QName("", "Posizioni");
    
    
    /**
     * Gets the "MOTIVO" element
     */
    public java.lang.String getMOTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVO" element
     */
    public it.avlp.simog.massload.xmlbeans.MotiviVariazioneCOType xgetMOTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotiviVariazioneCOType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotiviVariazioneCOType)get_store().find_element_user(MOTIVO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "MOTIVO" element
     */
    public void setMOTIVO(java.lang.String motivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MOTIVO$0);
            }
            target.setStringValue(motivo);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVO" element
     */
    public void xsetMOTIVO(it.avlp.simog.massload.xmlbeans.MotiviVariazioneCOType motivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotiviVariazioneCOType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotiviVariazioneCOType)get_store().find_element_user(MOTIVO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotiviVariazioneCOType)get_store().add_element_user(MOTIVO$0);
            }
            target.set(motivo);
        }
    }
    
    /**
     * Gets the "Riferimento" element
     */
    public it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType getRiferimento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().find_element_user(RIFERIMENTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Riferimento" element
     */
    public void setRiferimento(it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType riferimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().find_element_user(RIFERIMENTO$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().add_element_user(RIFERIMENTO$2);
            }
            target.set(riferimento);
        }
    }
    
    /**
     * Appends and returns a new empty "Riferimento" element
     */
    public it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType addNewRiferimento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().add_element_user(RIFERIMENTO$2);
            return target;
        }
    }
    
    /**
     * Gets array of all "Responsabili" elements
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType[] getResponsabiliArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RESPONSABILI$4, targetList);
            it.avlp.simog.massload.xmlbeans.IncaricatoType[] result = new it.avlp.simog.massload.xmlbeans.IncaricatoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Responsabili" element
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType getResponsabiliArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(RESPONSABILI$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Responsabili" element
     */
    public int sizeOfResponsabiliArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RESPONSABILI$4);
        }
    }
    
    /**
     * Sets array of all "Responsabili" element
     */
    public void setResponsabiliArray(it.avlp.simog.massload.xmlbeans.IncaricatoType[] responsabiliArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(responsabiliArray, RESPONSABILI$4);
        }
    }
    
    /**
     * Sets ith "Responsabili" element
     */
    public void setResponsabiliArray(int i, it.avlp.simog.massload.xmlbeans.IncaricatoType responsabili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(RESPONSABILI$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(responsabili);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Responsabili" element
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType insertNewResponsabili(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().insert_element_user(RESPONSABILI$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Responsabili" element
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType addNewResponsabili()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().add_element_user(RESPONSABILI$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "Responsabili" element
     */
    public void removeResponsabili(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RESPONSABILI$4, i);
        }
    }
    
    /**
     * Gets array of all "Aggiudicatari" elements
     */
    public it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType[] getAggiudicatariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AGGIUDICATARI$6, targetList);
            it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType[] result = new it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Aggiudicatari" element
     */
    public it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType getAggiudicatariArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Aggiudicatari" element
     */
    public int sizeOfAggiudicatariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGGIUDICATARI$6);
        }
    }
    
    /**
     * Sets array of all "Aggiudicatari" element
     */
    public void setAggiudicatariArray(it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType[] aggiudicatariArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(aggiudicatariArray, AGGIUDICATARI$6);
        }
    }
    
    /**
     * Sets ith "Aggiudicatari" element
     */
    public void setAggiudicatariArray(int i, it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType aggiudicatari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(aggiudicatari);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Aggiudicatari" element
     */
    public it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType insertNewAggiudicatari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().insert_element_user(AGGIUDICATARI$6, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Aggiudicatari" element
     */
    public it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType addNewAggiudicatari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().add_element_user(AGGIUDICATARI$6);
            return target;
        }
    }
    
    /**
     * Removes the ith "Aggiudicatari" element
     */
    public void removeAggiudicatari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGGIUDICATARI$6, i);
        }
    }
    
    /**
     * Gets array of all "DitteAusiliarie" elements
     */
    public it.avlp.simog.massload.xmlbeans.DittaAusiliariaType[] getDitteAusiliarieArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DITTEAUSILIARIE$8, targetList);
            it.avlp.simog.massload.xmlbeans.DittaAusiliariaType[] result = new it.avlp.simog.massload.xmlbeans.DittaAusiliariaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "DitteAusiliarie" element
     */
    public it.avlp.simog.massload.xmlbeans.DittaAusiliariaType getDitteAusiliarieArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DittaAusiliariaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().find_element_user(DITTEAUSILIARIE$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "DitteAusiliarie" element
     */
    public int sizeOfDitteAusiliarieArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DITTEAUSILIARIE$8);
        }
    }
    
    /**
     * Sets array of all "DitteAusiliarie" element
     */
    public void setDitteAusiliarieArray(it.avlp.simog.massload.xmlbeans.DittaAusiliariaType[] ditteAusiliarieArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(ditteAusiliarieArray, DITTEAUSILIARIE$8);
        }
    }
    
    /**
     * Sets ith "DitteAusiliarie" element
     */
    public void setDitteAusiliarieArray(int i, it.avlp.simog.massload.xmlbeans.DittaAusiliariaType ditteAusiliarie)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DittaAusiliariaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().find_element_user(DITTEAUSILIARIE$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(ditteAusiliarie);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "DitteAusiliarie" element
     */
    public it.avlp.simog.massload.xmlbeans.DittaAusiliariaType insertNewDitteAusiliarie(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DittaAusiliariaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().insert_element_user(DITTEAUSILIARIE$8, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "DitteAusiliarie" element
     */
    public it.avlp.simog.massload.xmlbeans.DittaAusiliariaType addNewDitteAusiliarie()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DittaAusiliariaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().add_element_user(DITTEAUSILIARIE$8);
            return target;
        }
    }
    
    /**
     * Removes the ith "DitteAusiliarie" element
     */
    public void removeDitteAusiliarie(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DITTEAUSILIARIE$8, i);
        }
    }
    
    /**
     * Gets array of all "Posizioni" elements
     */
    public it.avlp.simog.massload.xmlbeans.PosizioneType[] getPosizioniArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(POSIZIONI$10, targetList);
            it.avlp.simog.massload.xmlbeans.PosizioneType[] result = new it.avlp.simog.massload.xmlbeans.PosizioneType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Posizioni" element
     */
    public it.avlp.simog.massload.xmlbeans.PosizioneType getPosizioniArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().find_element_user(POSIZIONI$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Posizioni" element
     */
    public int sizeOfPosizioniArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(POSIZIONI$10);
        }
    }
    
    /**
     * Sets array of all "Posizioni" element
     */
    public void setPosizioniArray(it.avlp.simog.massload.xmlbeans.PosizioneType[] posizioniArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(posizioniArray, POSIZIONI$10);
        }
    }
    
    /**
     * Sets ith "Posizioni" element
     */
    public void setPosizioniArray(int i, it.avlp.simog.massload.xmlbeans.PosizioneType posizioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().find_element_user(POSIZIONI$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(posizioni);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Posizioni" element
     */
    public it.avlp.simog.massload.xmlbeans.PosizioneType insertNewPosizioni(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().insert_element_user(POSIZIONI$10, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Posizioni" element
     */
    public it.avlp.simog.massload.xmlbeans.PosizioneType addNewPosizioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().add_element_user(POSIZIONI$10);
            return target;
        }
    }
    
    /**
     * Removes the ith "Posizioni" element
     */
    public void removePosizioni(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(POSIZIONI$10, i);
        }
    }
}
