/*
 * XML Type:  DatiInizioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiInizioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiInizioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiInizioTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiInizioType
{
    
    public DatiInizioTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PUBBLICAZIONEESITO$0 = 
        new javax.xml.namespace.QName("", "PubblicazioneEsito");
    private static final javax.xml.namespace.QName INIZIO$2 = 
        new javax.xml.namespace.QName("", "Inizio");
    private static final javax.xml.namespace.QName POSIZIONI$4 = 
        new javax.xml.namespace.QName("", "Posizioni");
    private static final javax.xml.namespace.QName INCARICATI$6 = 
        new javax.xml.namespace.QName("", "Incaricati");
    
    
    /**
     * Gets the "PubblicazioneEsito" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneType getPubblicazioneEsito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONEESITO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PubblicazioneEsito" element
     */
    public void setPubblicazioneEsito(it.avlp.simog.massload.xmlbeans.PubblicazioneType pubblicazioneEsito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONEESITO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONEESITO$0);
            }
            target.set(pubblicazioneEsito);
        }
    }
    
    /**
     * Appends and returns a new empty "PubblicazioneEsito" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneType addNewPubblicazioneEsito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONEESITO$0);
            return target;
        }
    }
    
    /**
     * Gets the "Inizio" element
     */
    public it.avlp.simog.massload.xmlbeans.InizioType getInizio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InizioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InizioType)get_store().find_element_user(INIZIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Inizio" element
     */
    public void setInizio(it.avlp.simog.massload.xmlbeans.InizioType inizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InizioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InizioType)get_store().find_element_user(INIZIO$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InizioType)get_store().add_element_user(INIZIO$2);
            }
            target.set(inizio);
        }
    }
    
    /**
     * Appends and returns a new empty "Inizio" element
     */
    public it.avlp.simog.massload.xmlbeans.InizioType addNewInizio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InizioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InizioType)get_store().add_element_user(INIZIO$2);
            return target;
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
            get_store().find_all_element_users(POSIZIONI$4, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().find_element_user(POSIZIONI$4, i);
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
            return get_store().count_elements(POSIZIONI$4);
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
            arraySetterHelper(posizioniArray, POSIZIONI$4);
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
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().find_element_user(POSIZIONI$4, i);
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
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().insert_element_user(POSIZIONI$4, i);
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
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType)get_store().add_element_user(POSIZIONI$4);
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
            get_store().remove_element(POSIZIONI$4, i);
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
}
