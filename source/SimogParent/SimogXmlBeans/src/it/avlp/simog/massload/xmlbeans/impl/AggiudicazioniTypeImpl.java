/*
 * XML Type:  AggiudicazioniType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AggiudicazioniType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AggiudicazioniType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AggiudicazioniTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AggiudicazioniType
{
    
    public AggiudicazioniTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName APPALTO$0 = 
        new javax.xml.namespace.QName("", "Appalto");
    private static final javax.xml.namespace.QName TIPIAPPALTO$2 = 
        new javax.xml.namespace.QName("", "TipiAppalto");
    private static final javax.xml.namespace.QName CONDIZIONI$4 = 
        new javax.xml.namespace.QName("", "Condizioni");
    private static final javax.xml.namespace.QName REQUISITI$6 = 
        new javax.xml.namespace.QName("", "Requisiti");
    private static final javax.xml.namespace.QName FINANZIAMENTI$8 = 
        new javax.xml.namespace.QName("", "Finanziamenti");
    private static final javax.xml.namespace.QName AGGIUDICATARI$10 = 
        new javax.xml.namespace.QName("", "Aggiudicatari");
    private static final javax.xml.namespace.QName INCARICATI$12 = 
        new javax.xml.namespace.QName("", "Incaricati");
    
    
    /**
     * Gets the "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType getAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().find_element_user(APPALTO$0, 0);
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
    public void setAppalto(it.avlp.simog.massload.xmlbeans.AggiudicazioneType appalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().find_element_user(APPALTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().add_element_user(APPALTO$0);
            }
            target.set(appalto);
        }
    }
    
    /**
     * Appends and returns a new empty "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType addNewAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().add_element_user(APPALTO$0);
            return target;
        }
    }
    
    /**
     * Gets array of all "TipiAppalto" elements
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] getTipiAppaltoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TIPIAPPALTO$2, targetList);
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] result = new it.avlp.simog.massload.xmlbeans.TipiAppaltoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "TipiAppalto" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType getTipiAppaltoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().find_element_user(TIPIAPPALTO$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "TipiAppalto" element
     */
    public int sizeOfTipiAppaltoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIPIAPPALTO$2);
        }
    }
    
    /**
     * Sets array of all "TipiAppalto" element
     */
    public void setTipiAppaltoArray(it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] tipiAppaltoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(tipiAppaltoArray, TIPIAPPALTO$2);
        }
    }
    
    /**
     * Sets ith "TipiAppalto" element
     */
    public void setTipiAppaltoArray(int i, it.avlp.simog.massload.xmlbeans.TipiAppaltoType tipiAppalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().find_element_user(TIPIAPPALTO$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(tipiAppalto);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "TipiAppalto" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType insertNewTipiAppalto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().insert_element_user(TIPIAPPALTO$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "TipiAppalto" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType addNewTipiAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().add_element_user(TIPIAPPALTO$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "TipiAppalto" element
     */
    public void removeTipiAppalto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIPIAPPALTO$2, i);
        }
    }
    
    /**
     * Gets array of all "Condizioni" elements
     */
    public it.avlp.simog.massload.xmlbeans.CondizioneType[] getCondizioniArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CONDIZIONI$4, targetList);
            it.avlp.simog.massload.xmlbeans.CondizioneType[] result = new it.avlp.simog.massload.xmlbeans.CondizioneType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Condizioni" element
     */
    public it.avlp.simog.massload.xmlbeans.CondizioneType getCondizioniArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().find_element_user(CONDIZIONI$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Condizioni" element
     */
    public int sizeOfCondizioniArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONDIZIONI$4);
        }
    }
    
    /**
     * Sets array of all "Condizioni" element
     */
    public void setCondizioniArray(it.avlp.simog.massload.xmlbeans.CondizioneType[] condizioniArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(condizioniArray, CONDIZIONI$4);
        }
    }
    
    /**
     * Sets ith "Condizioni" element
     */
    public void setCondizioniArray(int i, it.avlp.simog.massload.xmlbeans.CondizioneType condizioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().find_element_user(CONDIZIONI$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(condizioni);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Condizioni" element
     */
    public it.avlp.simog.massload.xmlbeans.CondizioneType insertNewCondizioni(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().insert_element_user(CONDIZIONI$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Condizioni" element
     */
    public it.avlp.simog.massload.xmlbeans.CondizioneType addNewCondizioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().add_element_user(CONDIZIONI$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "Condizioni" element
     */
    public void removeCondizioni(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONDIZIONI$4, i);
        }
    }
    
    /**
     * Gets array of all "Requisiti" elements
     */
    public it.avlp.simog.massload.xmlbeans.RequisitoType[] getRequisitiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(REQUISITI$6, targetList);
            it.avlp.simog.massload.xmlbeans.RequisitoType[] result = new it.avlp.simog.massload.xmlbeans.RequisitoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Requisiti" element
     */
    public it.avlp.simog.massload.xmlbeans.RequisitoType getRequisitiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RequisitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().find_element_user(REQUISITI$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Requisiti" element
     */
    public int sizeOfRequisitiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REQUISITI$6);
        }
    }
    
    /**
     * Sets array of all "Requisiti" element
     */
    public void setRequisitiArray(it.avlp.simog.massload.xmlbeans.RequisitoType[] requisitiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(requisitiArray, REQUISITI$6);
        }
    }
    
    /**
     * Sets ith "Requisiti" element
     */
    public void setRequisitiArray(int i, it.avlp.simog.massload.xmlbeans.RequisitoType requisiti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RequisitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().find_element_user(REQUISITI$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(requisiti);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Requisiti" element
     */
    public it.avlp.simog.massload.xmlbeans.RequisitoType insertNewRequisiti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RequisitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().insert_element_user(REQUISITI$6, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Requisiti" element
     */
    public it.avlp.simog.massload.xmlbeans.RequisitoType addNewRequisiti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RequisitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().add_element_user(REQUISITI$6);
            return target;
        }
    }
    
    /**
     * Removes the ith "Requisiti" element
     */
    public void removeRequisiti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REQUISITI$6, i);
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
            get_store().find_all_element_users(FINANZIAMENTI$8, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().find_element_user(FINANZIAMENTI$8, i);
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
            return get_store().count_elements(FINANZIAMENTI$8);
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
            arraySetterHelper(finanziamentiArray, FINANZIAMENTI$8);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().find_element_user(FINANZIAMENTI$8, i);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().insert_element_user(FINANZIAMENTI$8, i);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().add_element_user(FINANZIAMENTI$8);
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
            get_store().remove_element(FINANZIAMENTI$8, i);
        }
    }
    
    /**
     * Gets array of all "Aggiudicatari" elements
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] getAggiudicatariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(AGGIUDICATARI$10, targetList);
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] result = new it.avlp.simog.massload.xmlbeans.AggiudicatarioType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Aggiudicatari" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType getAggiudicatariArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$10, i);
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
            return get_store().count_elements(AGGIUDICATARI$10);
        }
    }
    
    /**
     * Sets array of all "Aggiudicatari" element
     */
    public void setAggiudicatariArray(it.avlp.simog.massload.xmlbeans.AggiudicatarioType[] aggiudicatariArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(aggiudicatariArray, AGGIUDICATARI$10);
        }
    }
    
    /**
     * Sets ith "Aggiudicatari" element
     */
    public void setAggiudicatariArray(int i, it.avlp.simog.massload.xmlbeans.AggiudicatarioType aggiudicatari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$10, i);
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
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType insertNewAggiudicatari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().insert_element_user(AGGIUDICATARI$10, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Aggiudicatari" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType addNewAggiudicatari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType)get_store().add_element_user(AGGIUDICATARI$10);
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
            get_store().remove_element(AGGIUDICATARI$10, i);
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
            get_store().find_all_element_users(INCARICATI$12, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$12, i);
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
            return get_store().count_elements(INCARICATI$12);
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
            arraySetterHelper(incaricatiArray, INCARICATI$12);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$12, i);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().insert_element_user(INCARICATI$12, i);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().add_element_user(INCARICATI$12);
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
            get_store().remove_element(INCARICATI$12, i);
        }
    }
}
