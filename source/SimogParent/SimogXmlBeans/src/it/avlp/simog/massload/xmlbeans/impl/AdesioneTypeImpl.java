/*
 * XML Type:  AdesioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AdesioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AdesioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AdesioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AdesioneType
{
    
    public AdesioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName APPALTO$0 = 
        new javax.xml.namespace.QName("", "Appalto");
    private static final javax.xml.namespace.QName FINANZIAMENTI$2 = 
        new javax.xml.namespace.QName("", "Finanziamenti");
    private static final javax.xml.namespace.QName AGGIUDICATARI$4 = 
        new javax.xml.namespace.QName("", "Aggiudicatari");
    private static final javax.xml.namespace.QName INCARICATI$6 = 
        new javax.xml.namespace.QName("", "Incaricati");
    private static final javax.xml.namespace.QName DITTEAUSILIARIE$8 = 
        new javax.xml.namespace.QName("", "DitteAusiliarie");
    
    
    /**
     * Gets the "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType getAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType)get_store().find_element_user(APPALTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Appalto" element
     */
    public void setAppalto(it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType appalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType)get_store().find_element_user(APPALTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType)get_store().add_element_user(APPALTO$0);
            }
            target.set(appalto);
        }
    }
    
    /**
     * Appends and returns a new empty "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType addNewAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType)get_store().add_element_user(APPALTO$0);
            return target;
        }
    }
    
    /**
     * Gets array of all "Finanziamenti" elements
     */
    public it.avlp.simog.massload.xmlbeans.FinanziamentoType[] getFinanziamentiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(FINANZIAMENTI$2, targetList);
            it.avlp.simog.massload.xmlbeans.FinanziamentoType[] result = new it.avlp.simog.massload.xmlbeans.FinanziamentoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Finanziamenti" element
     */
    public it.avlp.simog.massload.xmlbeans.FinanziamentoType getFinanziamentiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FinanziamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().find_element_user(FINANZIAMENTI$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Finanziamenti" element
     */
    public int sizeOfFinanziamentiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FINANZIAMENTI$2);
        }
    }
    
    /**
     * Sets array of all "Finanziamenti" element
     */
    public void setFinanziamentiArray(it.avlp.simog.massload.xmlbeans.FinanziamentoType[] finanziamentiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(finanziamentiArray, FINANZIAMENTI$2);
        }
    }
    
    /**
     * Sets ith "Finanziamenti" element
     */
    public void setFinanziamentiArray(int i, it.avlp.simog.massload.xmlbeans.FinanziamentoType finanziamenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FinanziamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().find_element_user(FINANZIAMENTI$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(finanziamenti);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Finanziamenti" element
     */
    public it.avlp.simog.massload.xmlbeans.FinanziamentoType insertNewFinanziamenti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FinanziamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().insert_element_user(FINANZIAMENTI$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Finanziamenti" element
     */
    public it.avlp.simog.massload.xmlbeans.FinanziamentoType addNewFinanziamenti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FinanziamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().add_element_user(FINANZIAMENTI$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "Finanziamenti" element
     */
    public void removeFinanziamenti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FINANZIAMENTI$2, i);
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
            get_store().find_all_element_users(AGGIUDICATARI$4, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$4, i);
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
            return get_store().count_elements(AGGIUDICATARI$4);
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
            arraySetterHelper(aggiudicatariArray, AGGIUDICATARI$4);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$4, i);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().insert_element_user(AGGIUDICATARI$4, i);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().add_element_user(AGGIUDICATARI$4);
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
            get_store().remove_element(AGGIUDICATARI$4, i);
        }
    }
    
    /**
     * Gets array of all "Incaricati" elements
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType[] getIncaricatiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(INCARICATI$6, targetList);
            it.avlp.simog.massload.xmlbeans.IncaricatoType[] result = new it.avlp.simog.massload.xmlbeans.IncaricatoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Incaricati" element
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType getIncaricatiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Incaricati" element
     */
    public int sizeOfIncaricatiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INCARICATI$6);
        }
    }
    
    /**
     * Sets array of all "Incaricati" element
     */
    public void setIncaricatiArray(it.avlp.simog.massload.xmlbeans.IncaricatoType[] incaricatiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(incaricatiArray, INCARICATI$6);
        }
    }
    
    /**
     * Sets ith "Incaricati" element
     */
    public void setIncaricatiArray(int i, it.avlp.simog.massload.xmlbeans.IncaricatoType incaricati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(incaricati);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Incaricati" element
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType insertNewIncaricati(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().insert_element_user(INCARICATI$6, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Incaricati" element
     */
    public it.avlp.simog.massload.xmlbeans.IncaricatoType addNewIncaricati()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IncaricatoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().add_element_user(INCARICATI$6);
            return target;
        }
    }
    
    /**
     * Removes the ith "Incaricati" element
     */
    public void removeIncaricati(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INCARICATI$6, i);
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
}
