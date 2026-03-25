/*
 * XML Type:  SchedaEsclusoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SchedaEsclusoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SchedaEsclusoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SchedaEsclusoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SchedaEsclusoType
{
    
    public SchedaEsclusoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName APPALTO$0 = 
        new javax.xml.namespace.QName("", "Appalto");
    private static final javax.xml.namespace.QName AGGIUDICATARI$2 = 
        new javax.xml.namespace.QName("", "Aggiudicatari");
    private static final javax.xml.namespace.QName INCARICATI$4 = 
        new javax.xml.namespace.QName("", "Incaricati");
    private static final javax.xml.namespace.QName CUPLOTTO$6 = 
        new javax.xml.namespace.QName("", "CUPLOTTO");
    
    
    /**
     * Gets the "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.SottoEsclusoType getAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SottoEsclusoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SottoEsclusoType)get_store().find_element_user(APPALTO$0, 0);
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
    public void setAppalto(it.avlp.simog.massload.xmlbeans.SottoEsclusoType appalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SottoEsclusoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SottoEsclusoType)get_store().find_element_user(APPALTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SottoEsclusoType)get_store().add_element_user(APPALTO$0);
            }
            target.set(appalto);
        }
    }
    
    /**
     * Appends and returns a new empty "Appalto" element
     */
    public it.avlp.simog.massload.xmlbeans.SottoEsclusoType addNewAppalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SottoEsclusoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SottoEsclusoType)get_store().add_element_user(APPALTO$0);
            return target;
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
            get_store().find_all_element_users(AGGIUDICATARI$2, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$2, i);
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
            return get_store().count_elements(AGGIUDICATARI$2);
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
            arraySetterHelper(aggiudicatariArray, AGGIUDICATARI$2);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().find_element_user(AGGIUDICATARI$2, i);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().insert_element_user(AGGIUDICATARI$2, i);
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
            target = (it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType)get_store().add_element_user(AGGIUDICATARI$2);
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
            get_store().remove_element(AGGIUDICATARI$2, i);
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
            get_store().find_all_element_users(INCARICATI$4, targetList);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$4, i);
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
            return get_store().count_elements(INCARICATI$4);
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
            arraySetterHelper(incaricatiArray, INCARICATI$4);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().find_element_user(INCARICATI$4, i);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().insert_element_user(INCARICATI$4, i);
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
            target = (it.avlp.simog.massload.xmlbeans.IncaricatoType)get_store().add_element_user(INCARICATI$4);
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
            get_store().remove_element(INCARICATI$4, i);
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
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().find_element_user(CUPLOTTO$6, 0);
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
            return get_store().count_elements(CUPLOTTO$6) != 0;
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
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().find_element_user(CUPLOTTO$6, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().add_element_user(CUPLOTTO$6);
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
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().add_element_user(CUPLOTTO$6);
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
            get_store().remove_element(CUPLOTTO$6, 0);
        }
    }
}
