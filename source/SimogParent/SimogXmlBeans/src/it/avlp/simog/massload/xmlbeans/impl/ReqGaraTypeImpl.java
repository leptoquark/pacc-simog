/*
 * XML Type:  ReqGaraType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ReqGaraType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ReqGaraType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ReqGaraTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ReqGaraType
{
    
    public ReqGaraTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CIG$0 = 
        new javax.xml.namespace.QName("", "CIG");
    private static final javax.xml.namespace.QName DOCUMENTO$2 = 
        new javax.xml.namespace.QName("", "DOCUMENTO");
    private static final javax.xml.namespace.QName CODICEDETTAGLIO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "codice_dettaglio");
    private static final javax.xml.namespace.QName DESCRIZIONE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "descrizione");
    private static final javax.xml.namespace.QName VALORE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "valore");
    private static final javax.xml.namespace.QName FLAGESCLUSIONE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "flag_esclusione");
    private static final javax.xml.namespace.QName FLAGCOMPROVAOFFERTA$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "flag_comprova_offerta");
    private static final javax.xml.namespace.QName FLAGAVVALIMENTO$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "flag_avvalimento");
    private static final javax.xml.namespace.QName FLAGBANDOTIPO$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "flag_bando_tipo");
    private static final javax.xml.namespace.QName FLAGRISERVATEZZA$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "flag_riservatezza");
    
    
    /**
     * Gets array of all "CIG" elements
     */
    public java.lang.String[] getCIGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CIG$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "CIG" element
     */
    public java.lang.String getCIGArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIG$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "CIG" elements
     */
    public it.avlp.simog.massload.xmlbeans.CigType[] xgetCIGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CIG$0, targetList);
            it.avlp.simog.massload.xmlbeans.CigType[] result = new it.avlp.simog.massload.xmlbeans.CigType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "CIG" element
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIGArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_element_user(CIG$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (it.avlp.simog.massload.xmlbeans.CigType)target;
        }
    }
    
    /**
     * Returns number of "CIG" element
     */
    public int sizeOfCIGArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIG$0);
        }
    }
    
    /**
     * Sets array of all "CIG" element
     */
    public void setCIGArray(java.lang.String[] cigArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(cigArray, CIG$0);
        }
    }
    
    /**
     * Sets ith "CIG" element
     */
    public void setCIGArray(int i, java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIG$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(cig);
        }
    }
    
    /**
     * Sets (as xml) array of all "CIG" element
     */
    public void xsetCIGArray(it.avlp.simog.massload.xmlbeans.CigType[]cigArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(cigArray, CIG$0);
        }
    }
    
    /**
     * Sets (as xml) ith "CIG" element
     */
    public void xsetCIGArray(int i, it.avlp.simog.massload.xmlbeans.CigType cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_element_user(CIG$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(cig);
        }
    }
    
    /**
     * Inserts the value as the ith "CIG" element
     */
    public void insertCIG(int i, java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(CIG$0, i);
            target.setStringValue(cig);
        }
    }
    
    /**
     * Appends the value as the last "CIG" element
     */
    public void addCIG(java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIG$0);
            target.setStringValue(cig);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CIG" element
     */
    public it.avlp.simog.massload.xmlbeans.CigType insertNewCIG(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().insert_element_user(CIG$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CIG" element
     */
    public it.avlp.simog.massload.xmlbeans.CigType addNewCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_element_user(CIG$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "CIG" element
     */
    public void removeCIG(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIG$0, i);
        }
    }
    
    /**
     * Gets array of all "DOCUMENTO" elements
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType[] getDOCUMENTOArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DOCUMENTO$2, targetList);
            it.avlp.simog.massload.xmlbeans.ReqDocType[] result = new it.avlp.simog.massload.xmlbeans.ReqDocType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "DOCUMENTO" element
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType getDOCUMENTOArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType)get_store().find_element_user(DOCUMENTO$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "DOCUMENTO" element
     */
    public int sizeOfDOCUMENTOArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DOCUMENTO$2);
        }
    }
    
    /**
     * Sets array of all "DOCUMENTO" element
     */
    public void setDOCUMENTOArray(it.avlp.simog.massload.xmlbeans.ReqDocType[] documentoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(documentoArray, DOCUMENTO$2);
        }
    }
    
    /**
     * Sets ith "DOCUMENTO" element
     */
    public void setDOCUMENTOArray(int i, it.avlp.simog.massload.xmlbeans.ReqDocType documento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType)get_store().find_element_user(DOCUMENTO$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(documento);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "DOCUMENTO" element
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType insertNewDOCUMENTO(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType)get_store().insert_element_user(DOCUMENTO$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "DOCUMENTO" element
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType addNewDOCUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType)get_store().add_element_user(DOCUMENTO$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "DOCUMENTO" element
     */
    public void removeDOCUMENTO(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DOCUMENTO$2, i);
        }
    }
    
    /**
     * Gets the "codice_dettaglio" attribute
     */
    public java.lang.String getCodiceDettaglio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEDETTAGLIO$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codice_dettaglio" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodRequisitoType xgetCodiceDettaglio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodRequisitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodRequisitoType)get_store().find_attribute_user(CODICEDETTAGLIO$4);
            return target;
        }
    }
    
    /**
     * Sets the "codice_dettaglio" attribute
     */
    public void setCodiceDettaglio(java.lang.String codiceDettaglio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEDETTAGLIO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEDETTAGLIO$4);
            }
            target.setStringValue(codiceDettaglio);
        }
    }
    
    /**
     * Sets (as xml) the "codice_dettaglio" attribute
     */
    public void xsetCodiceDettaglio(it.avlp.simog.massload.xmlbeans.CodRequisitoType codiceDettaglio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodRequisitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodRequisitoType)get_store().find_attribute_user(CODICEDETTAGLIO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodRequisitoType)get_store().add_attribute_user(CODICEDETTAGLIO$4);
            }
            target.set(codiceDettaglio);
        }
    }
    
    /**
     * Gets the "descrizione" attribute
     */
    public java.lang.String getDescrizione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONE$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "descrizione" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione xgetDescrizione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione)get_store().find_attribute_user(DESCRIZIONE$6);
            return target;
        }
    }
    
    /**
     * Sets the "descrizione" attribute
     */
    public void setDescrizione(java.lang.String descrizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCRIZIONE$6);
            }
            target.setStringValue(descrizione);
        }
    }
    
    /**
     * Sets (as xml) the "descrizione" attribute
     */
    public void xsetDescrizione(it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione descrizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione)get_store().find_attribute_user(DESCRIZIONE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione)get_store().add_attribute_user(DESCRIZIONE$6);
            }
            target.set(descrizione);
        }
    }
    
    /**
     * Gets the "valore" attribute
     */
    public java.lang.String getValore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALORE$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "valore" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CuiType xgetValore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(VALORE$8);
            return target;
        }
    }
    
    /**
     * Sets the "valore" attribute
     */
    public void setValore(java.lang.String valore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALORE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALORE$8);
            }
            target.setStringValue(valore);
        }
    }
    
    /**
     * Sets (as xml) the "valore" attribute
     */
    public void xsetValore(it.avlp.simog.massload.xmlbeans.CuiType valore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(VALORE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().add_attribute_user(VALORE$8);
            }
            target.set(valore);
        }
    }
    
    /**
     * Gets the "flag_esclusione" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagEsclusione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGESCLUSIONE$10);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "flag_esclusione" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagEsclusione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGESCLUSIONE$10);
            return target;
        }
    }
    
    /**
     * Sets the "flag_esclusione" attribute
     */
    public void setFlagEsclusione(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagEsclusione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGESCLUSIONE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGESCLUSIONE$10);
            }
            target.setEnumValue(flagEsclusione);
        }
    }
    
    /**
     * Sets (as xml) the "flag_esclusione" attribute
     */
    public void xsetFlagEsclusione(it.avlp.simog.massload.xmlbeans.FlagSNType flagEsclusione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGESCLUSIONE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGESCLUSIONE$10);
            }
            target.set(flagEsclusione);
        }
    }
    
    /**
     * Gets the "flag_comprova_offerta" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagComprovaOfferta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGCOMPROVAOFFERTA$12);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "flag_comprova_offerta" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagComprovaOfferta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGCOMPROVAOFFERTA$12);
            return target;
        }
    }
    
    /**
     * Sets the "flag_comprova_offerta" attribute
     */
    public void setFlagComprovaOfferta(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagComprovaOfferta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGCOMPROVAOFFERTA$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGCOMPROVAOFFERTA$12);
            }
            target.setEnumValue(flagComprovaOfferta);
        }
    }
    
    /**
     * Sets (as xml) the "flag_comprova_offerta" attribute
     */
    public void xsetFlagComprovaOfferta(it.avlp.simog.massload.xmlbeans.FlagSNType flagComprovaOfferta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGCOMPROVAOFFERTA$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGCOMPROVAOFFERTA$12);
            }
            target.set(flagComprovaOfferta);
        }
    }
    
    /**
     * Gets the "flag_avvalimento" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagAvvalimento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAVVALIMENTO$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "flag_avvalimento" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagAvvalimento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGAVVALIMENTO$14);
            return target;
        }
    }
    
    /**
     * Sets the "flag_avvalimento" attribute
     */
    public void setFlagAvvalimento(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagAvvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAVVALIMENTO$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGAVVALIMENTO$14);
            }
            target.setEnumValue(flagAvvalimento);
        }
    }
    
    /**
     * Sets (as xml) the "flag_avvalimento" attribute
     */
    public void xsetFlagAvvalimento(it.avlp.simog.massload.xmlbeans.FlagSNType flagAvvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGAVVALIMENTO$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGAVVALIMENTO$14);
            }
            target.set(flagAvvalimento);
        }
    }
    
    /**
     * Gets the "flag_bando_tipo" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagBandoTipo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGBANDOTIPO$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "flag_bando_tipo" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagBandoTipo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGBANDOTIPO$16);
            return target;
        }
    }
    
    /**
     * Sets the "flag_bando_tipo" attribute
     */
    public void setFlagBandoTipo(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagBandoTipo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGBANDOTIPO$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGBANDOTIPO$16);
            }
            target.setEnumValue(flagBandoTipo);
        }
    }
    
    /**
     * Sets (as xml) the "flag_bando_tipo" attribute
     */
    public void xsetFlagBandoTipo(it.avlp.simog.massload.xmlbeans.FlagSNType flagBandoTipo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGBANDOTIPO$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGBANDOTIPO$16);
            }
            target.set(flagBandoTipo);
        }
    }
    
    /**
     * Gets the "flag_riservatezza" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFlagRiservatezza()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVATEZZA$18);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "flag_riservatezza" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFlagRiservatezza()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVATEZZA$18);
            return target;
        }
    }
    
    /**
     * Sets the "flag_riservatezza" attribute
     */
    public void setFlagRiservatezza(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagRiservatezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRISERVATEZZA$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGRISERVATEZZA$18);
            }
            target.setEnumValue(flagRiservatezza);
        }
    }
    
    /**
     * Sets (as xml) the "flag_riservatezza" attribute
     */
    public void xsetFlagRiservatezza(it.avlp.simog.massload.xmlbeans.FlagSNType flagRiservatezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRISERVATEZZA$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGRISERVATEZZA$18);
            }
            target.set(flagRiservatezza);
        }
    }
    /**
     * An XML descrizione(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqGaraType$Descrizione.
     */
    public static class DescrizioneImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ReqGaraType.Descrizione
    {
        
        public DescrizioneImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DescrizioneImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
