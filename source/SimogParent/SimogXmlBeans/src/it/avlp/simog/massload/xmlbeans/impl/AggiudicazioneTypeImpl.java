/*
 * XML Type:  AggiudicazioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AggiudicazioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AggiudicazioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AggiudicazioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AggiudicazioneType
{
    
    public AggiudicazioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName APPALTO$0 = 
        new javax.xml.namespace.QName("", "Appalto");
    private static final javax.xml.namespace.QName TIPIAPPALTOLAV$2 = 
        new javax.xml.namespace.QName("", "TipiAppaltoLav");
    private static final javax.xml.namespace.QName TIPIAPPALTOFORN$4 = 
        new javax.xml.namespace.QName("", "TipiAppaltoForn");
    private static final javax.xml.namespace.QName CONDIZIONI$6 = 
        new javax.xml.namespace.QName("", "Condizioni");
    private static final javax.xml.namespace.QName REQUISITI$8 = 
        new javax.xml.namespace.QName("", "Requisiti");
    private static final javax.xml.namespace.QName FINANZIAMENTI$10 = 
        new javax.xml.namespace.QName("", "Finanziamenti");
    private static final javax.xml.namespace.QName AGGIUDICATARI$12 = 
        new javax.xml.namespace.QName("", "Aggiudicatari");
    private static final javax.xml.namespace.QName INCARICATI$14 = 
        new javax.xml.namespace.QName("", "Incaricati");
    private static final javax.xml.namespace.QName DITTEAUSILIARIE$16 = 
        new javax.xml.namespace.QName("", "DitteAusiliarie");
    private static final javax.xml.namespace.QName CUPLOTTO$18 = 
        new javax.xml.namespace.QName("", "CUPLOTTO");
    
    
    /**
     * Gets the "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoType getAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoType)get_store().find_element_user(APPALTO$0, 0);
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
    public void setAppalto(it.avlp.simog.massload.xmlbeans.AppaltoType appalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoType)get_store().find_element_user(APPALTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AppaltoType)get_store().add_element_user(APPALTO$0);
            }
            target.set(appalto);
        }
    }
    
    /**
     * Appends and returns a new empty "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.AppaltoType addNewAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AppaltoType)get_store().add_element_user(APPALTO$0);
            return target;
        }
    }
    
    /**
     * Gets array of all "TipiAppaltoLav" elements
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] getTipiAppaltoLavArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TIPIAPPALTOLAV$2, targetList);
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] result = new it.avlp.simog.massload.xmlbeans.TipiAppaltoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "TipiAppaltoLav" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType getTipiAppaltoLavArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().find_element_user(TIPIAPPALTOLAV$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "TipiAppaltoLav" element
     */
    public int sizeOfTipiAppaltoLavArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIPIAPPALTOLAV$2);
        }
    }
    
    /**
     * Sets array of all "TipiAppaltoLav" element
     */
    public void setTipiAppaltoLavArray(it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] tipiAppaltoLavArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(tipiAppaltoLavArray, TIPIAPPALTOLAV$2);
        }
    }
    
    /**
     * Sets ith "TipiAppaltoLav" element
     */
    public void setTipiAppaltoLavArray(int i, it.avlp.simog.massload.xmlbeans.TipiAppaltoType tipiAppaltoLav)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().find_element_user(TIPIAPPALTOLAV$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(tipiAppaltoLav);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "TipiAppaltoLav" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType insertNewTipiAppaltoLav(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().insert_element_user(TIPIAPPALTOLAV$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "TipiAppaltoLav" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType addNewTipiAppaltoLav()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().add_element_user(TIPIAPPALTOLAV$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "TipiAppaltoLav" element
     */
    public void removeTipiAppaltoLav(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIPIAPPALTOLAV$2, i);
        }
    }
    
    /**
     * Gets array of all "TipiAppaltoForn" elements
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] getTipiAppaltoFornArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TIPIAPPALTOFORN$4, targetList);
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] result = new it.avlp.simog.massload.xmlbeans.TipiAppaltoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "TipiAppaltoForn" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType getTipiAppaltoFornArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().find_element_user(TIPIAPPALTOFORN$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "TipiAppaltoForn" element
     */
    public int sizeOfTipiAppaltoFornArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIPIAPPALTOFORN$4);
        }
    }
    
    /**
     * Sets array of all "TipiAppaltoForn" element
     */
    public void setTipiAppaltoFornArray(it.avlp.simog.massload.xmlbeans.TipiAppaltoType[] tipiAppaltoFornArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(tipiAppaltoFornArray, TIPIAPPALTOFORN$4);
        }
    }
    
    /**
     * Sets ith "TipiAppaltoForn" element
     */
    public void setTipiAppaltoFornArray(int i, it.avlp.simog.massload.xmlbeans.TipiAppaltoType tipiAppaltoForn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().find_element_user(TIPIAPPALTOFORN$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(tipiAppaltoForn);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "TipiAppaltoForn" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType insertNewTipiAppaltoForn(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().insert_element_user(TIPIAPPALTOFORN$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "TipiAppaltoForn" element
     */
    public it.avlp.simog.massload.xmlbeans.TipiAppaltoType addNewTipiAppaltoForn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiAppaltoType)get_store().add_element_user(TIPIAPPALTOFORN$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "TipiAppaltoForn" element
     */
    public void removeTipiAppaltoForn(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIPIAPPALTOFORN$4, i);
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
            get_store().find_all_element_users(CONDIZIONI$6, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().find_element_user(CONDIZIONI$6, i);
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
            return get_store().count_elements(CONDIZIONI$6);
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
            arraySetterHelper(condizioniArray, CONDIZIONI$6);
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
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().find_element_user(CONDIZIONI$6, i);
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
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().insert_element_user(CONDIZIONI$6, i);
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
            target = (it.avlp.simog.massload.xmlbeans.CondizioneType)get_store().add_element_user(CONDIZIONI$6);
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
            get_store().remove_element(CONDIZIONI$6, i);
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
            get_store().find_all_element_users(REQUISITI$8, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().find_element_user(REQUISITI$8, i);
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
            return get_store().count_elements(REQUISITI$8);
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
            arraySetterHelper(requisitiArray, REQUISITI$8);
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
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().find_element_user(REQUISITI$8, i);
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
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().insert_element_user(REQUISITI$8, i);
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
            target = (it.avlp.simog.massload.xmlbeans.RequisitoType)get_store().add_element_user(REQUISITI$8);
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
            get_store().remove_element(REQUISITI$8, i);
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
            get_store().find_all_element_users(FINANZIAMENTI$10, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().find_element_user(FINANZIAMENTI$10, i);
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
            return get_store().count_elements(FINANZIAMENTI$10);
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
            arraySetterHelper(finanziamentiArray, FINANZIAMENTI$10);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().find_element_user(FINANZIAMENTI$10, i);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().insert_element_user(FINANZIAMENTI$10, i);
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
            target = (it.avlp.simog.massload.xmlbeans.FinanziamentoType)get_store().add_element_user(FINANZIAMENTI$10);
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
            get_store().remove_element(FINANZIAMENTI$10, i);
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
            get_store().find_all_element_users(AGGIUDICATARI$12, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$12, i);
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
            return get_store().count_elements(AGGIUDICATARI$12);
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
            arraySetterHelper(aggiudicatariArray, AGGIUDICATARI$12);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$12, i);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().insert_element_user(AGGIUDICATARI$12, i);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().add_element_user(AGGIUDICATARI$12);
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
            get_store().remove_element(AGGIUDICATARI$12, i);
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
            get_store().find_all_element_users(INCARICATI$14, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$14, i);
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
            return get_store().count_elements(INCARICATI$14);
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
            arraySetterHelper(incaricatiArray, INCARICATI$14);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$14, i);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().insert_element_user(INCARICATI$14, i);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().add_element_user(INCARICATI$14);
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
            get_store().remove_element(INCARICATI$14, i);
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
            get_store().find_all_element_users(DITTEAUSILIARIE$16, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().find_element_user(DITTEAUSILIARIE$16, i);
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
            return get_store().count_elements(DITTEAUSILIARIE$16);
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
            arraySetterHelper(ditteAusiliarieArray, DITTEAUSILIARIE$16);
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
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().find_element_user(DITTEAUSILIARIE$16, i);
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
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().insert_element_user(DITTEAUSILIARIE$16, i);
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
            target = (it.avlp.simog.massload.xmlbeans.DittaAusiliariaType)get_store().add_element_user(DITTEAUSILIARIE$16);
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
            get_store().remove_element(DITTEAUSILIARIE$16, i);
        }
    }
    
    /**
     * Gets the "CUPLOTTO" element
     */
    public it.avlp.simog.massload.xmlbeans.CUPLOTTOType getCUPLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CUPLOTTOType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().find_element_user(CUPLOTTO$18, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "CUPLOTTO" element
     */
    public boolean isSetCUPLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUPLOTTO$18) != 0;
        }
    }
    
    /**
     * Sets the "CUPLOTTO" element
     */
    public void setCUPLOTTO(it.avlp.simog.massload.xmlbeans.CUPLOTTOType cuplotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CUPLOTTOType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().find_element_user(CUPLOTTO$18, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().add_element_user(CUPLOTTO$18);
            }
            target.set(cuplotto);
        }
    }
    
    /**
     * Appends and returns a new empty "CUPLOTTO" element
     */
    public it.avlp.simog.massload.xmlbeans.CUPLOTTOType addNewCUPLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CUPLOTTOType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().add_element_user(CUPLOTTO$18);
            return target;
        }
    }
    
    /**
     * Unsets the "CUPLOTTO" element
     */
    public void unsetCUPLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUPLOTTO$18, 0);
        }
    }
}
