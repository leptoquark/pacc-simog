/*
 * XML Type:  DescrizioneAppaltoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DescrizioneAppaltoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DescrizioneAppaltoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType
{
    
    public DescrizioneAppaltoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CRITERIOQUALITA$0 = 
        new javax.xml.namespace.QName("", "CRITERIO_QUALITA");
    private static final javax.xml.namespace.QName CRITERIOCOSTO$2 = 
        new javax.xml.namespace.QName("", "CRITERIO_COSTO");
    private static final javax.xml.namespace.QName CRITERIOPREZZO$4 = 
        new javax.xml.namespace.QName("", "CRITERIO_PREZZO");
    private static final javax.xml.namespace.QName TITOLOAPPALTO$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TITOLO_APPALTO");
    private static final javax.xml.namespace.QName LUOGOESECUZIONEPRINCIPALE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_ESECUZIONE_PRINCIPALE");
    private static final javax.xml.namespace.QName CRITERIOAGGLOTTO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CRITERIO_AGG_LOTTO");
    private static final javax.xml.namespace.QName TIPOCRITERIO$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPO_CRITERIO");
    private static final javax.xml.namespace.QName DESCRINNOVICONTR$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESC_RINNOVI_CONTR");
    private static final javax.xml.namespace.QName NUMCANDIDATIPREVISTI$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_CANDIDATI_PREVISTI");
    private static final javax.xml.namespace.QName MINNUMCANDIDATIPREVISTI$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MIN_NUM_CANDIDATI_PREVISTI");
    private static final javax.xml.namespace.QName MAXNUMCANDIDATIPREVISTI$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MAX_NUM_CANDIDATI_PREVISTI");
    private static final javax.xml.namespace.QName CRITERIMAXNUMCANDIDATI$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CRITERI_MAX_NUM_CANDIDATI");
    private static final javax.xml.namespace.QName ACCETTATEVARIANTI$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ACCETTATE_VARIANTI");
    private static final javax.xml.namespace.QName DESCRIZIONEOPZIONI$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESCRIZIONE_OPZIONI");
    private static final javax.xml.namespace.QName PRESOFFERTECATALOGOELETTRONICO$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PRES_OFFERTE_CATALOGO_ELETTRONICO");
    private static final javax.xml.namespace.QName FLAGAPPALTOPROGETTOUE$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_APPALTO_PROGETTO_UE");
    private static final javax.xml.namespace.QName APPALTOPROGETTOUE$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "APPALTO_PROGETTO_UE");
    private static final javax.xml.namespace.QName ULTERIORIINFOLOTTO$34 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ULTERIORI_INFO_LOTTO");
    
    
    /**
     * Gets array of all "CRITERIO_QUALITA" elements
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[] getCRITERIOQUALITAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CRITERIOQUALITA$0, targetList);
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[] result = new it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "CRITERIO_QUALITA" element
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType getCRITERIOQUALITAArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().find_element_user(CRITERIOQUALITA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "CRITERIO_QUALITA" element
     */
    public int sizeOfCRITERIOQUALITAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CRITERIOQUALITA$0);
        }
    }
    
    /**
     * Sets array of all "CRITERIO_QUALITA" element
     */
    public void setCRITERIOQUALITAArray(it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[] criterioqualitaArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(criterioqualitaArray, CRITERIOQUALITA$0);
        }
    }
    
    /**
     * Sets ith "CRITERIO_QUALITA" element
     */
    public void setCRITERIOQUALITAArray(int i, it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType criterioqualita)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().find_element_user(CRITERIOQUALITA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(criterioqualita);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CRITERIO_QUALITA" element
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType insertNewCRITERIOQUALITA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().insert_element_user(CRITERIOQUALITA$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CRITERIO_QUALITA" element
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType addNewCRITERIOQUALITA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().add_element_user(CRITERIOQUALITA$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "CRITERIO_QUALITA" element
     */
    public void removeCRITERIOQUALITA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CRITERIOQUALITA$0, i);
        }
    }
    
    /**
     * Gets array of all "CRITERIO_COSTO" elements
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[] getCRITERIOCOSTOArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CRITERIOCOSTO$2, targetList);
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[] result = new it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "CRITERIO_COSTO" element
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType getCRITERIOCOSTOArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().find_element_user(CRITERIOCOSTO$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "CRITERIO_COSTO" element
     */
    public int sizeOfCRITERIOCOSTOArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CRITERIOCOSTO$2);
        }
    }
    
    /**
     * Sets array of all "CRITERIO_COSTO" element
     */
    public void setCRITERIOCOSTOArray(it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType[] criteriocostoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(criteriocostoArray, CRITERIOCOSTO$2);
        }
    }
    
    /**
     * Sets ith "CRITERIO_COSTO" element
     */
    public void setCRITERIOCOSTOArray(int i, it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType criteriocosto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().find_element_user(CRITERIOCOSTO$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(criteriocosto);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CRITERIO_COSTO" element
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType insertNewCRITERIOCOSTO(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().insert_element_user(CRITERIOCOSTO$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CRITERIO_COSTO" element
     */
    public it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType addNewCRITERIOCOSTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType)get_store().add_element_user(CRITERIOCOSTO$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "CRITERIO_COSTO" element
     */
    public void removeCRITERIOCOSTO(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CRITERIOCOSTO$2, i);
        }
    }
    
    /**
     * Gets the "CRITERIO_PREZZO" element
     */
    public it.avlp.simog.massload.xmlbeans.PriceCriteriaType getCRITERIOPREZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PriceCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PriceCriteriaType)get_store().find_element_user(CRITERIOPREZZO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "CRITERIO_PREZZO" element
     */
    public boolean isSetCRITERIOPREZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CRITERIOPREZZO$4) != 0;
        }
    }
    
    /**
     * Sets the "CRITERIO_PREZZO" element
     */
    public void setCRITERIOPREZZO(it.avlp.simog.massload.xmlbeans.PriceCriteriaType criterioprezzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PriceCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PriceCriteriaType)get_store().find_element_user(CRITERIOPREZZO$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PriceCriteriaType)get_store().add_element_user(CRITERIOPREZZO$4);
            }
            target.set(criterioprezzo);
        }
    }
    
    /**
     * Appends and returns a new empty "CRITERIO_PREZZO" element
     */
    public it.avlp.simog.massload.xmlbeans.PriceCriteriaType addNewCRITERIOPREZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PriceCriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PriceCriteriaType)get_store().add_element_user(CRITERIOPREZZO$4);
            return target;
        }
    }
    
    /**
     * Unsets the "CRITERIO_PREZZO" element
     */
    public void unsetCRITERIOPREZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CRITERIOPREZZO$4, 0);
        }
    }
    
    /**
     * Gets the "TITOLO_APPALTO" attribute
     */
    public java.lang.String getTITOLOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITOLOAPPALTO$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TITOLO_APPALTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO xgetTITOLOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO)get_store().find_attribute_user(TITOLOAPPALTO$6);
            return target;
        }
    }
    
    /**
     * True if has "TITOLO_APPALTO" attribute
     */
    public boolean isSetTITOLOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TITOLOAPPALTO$6) != null;
        }
    }
    
    /**
     * Sets the "TITOLO_APPALTO" attribute
     */
    public void setTITOLOAPPALTO(java.lang.String titoloappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITOLOAPPALTO$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TITOLOAPPALTO$6);
            }
            target.setStringValue(titoloappalto);
        }
    }
    
    /**
     * Sets (as xml) the "TITOLO_APPALTO" attribute
     */
    public void xsetTITOLOAPPALTO(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO titoloappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO)get_store().find_attribute_user(TITOLOAPPALTO$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO)get_store().add_attribute_user(TITOLOAPPALTO$6);
            }
            target.set(titoloappalto);
        }
    }
    
    /**
     * Unsets the "TITOLO_APPALTO" attribute
     */
    public void unsetTITOLOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TITOLOAPPALTO$6);
        }
    }
    
    /**
     * Gets the "LUOGO_ESECUZIONE_PRINCIPALE" attribute
     */
    public java.lang.String getLUOGOESECUZIONEPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOESECUZIONEPRINCIPALE$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LUOGO_ESECUZIONE_PRINCIPALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE xgetLUOGOESECUZIONEPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE)get_store().find_attribute_user(LUOGOESECUZIONEPRINCIPALE$8);
            return target;
        }
    }
    
    /**
     * Sets the "LUOGO_ESECUZIONE_PRINCIPALE" attribute
     */
    public void setLUOGOESECUZIONEPRINCIPALE(java.lang.String luogoesecuzioneprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOESECUZIONEPRINCIPALE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LUOGOESECUZIONEPRINCIPALE$8);
            }
            target.setStringValue(luogoesecuzioneprincipale);
        }
    }
    
    /**
     * Sets (as xml) the "LUOGO_ESECUZIONE_PRINCIPALE" attribute
     */
    public void xsetLUOGOESECUZIONEPRINCIPALE(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE luogoesecuzioneprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE)get_store().find_attribute_user(LUOGOESECUZIONEPRINCIPALE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE)get_store().add_attribute_user(LUOGOESECUZIONEPRINCIPALE$8);
            }
            target.set(luogoesecuzioneprincipale);
        }
    }
    
    /**
     * Gets the "CRITERIO_AGG_LOTTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CriterioAggLottoType.Enum getCRITERIOAGGLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERIOAGGLOTTO$10);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.CriterioAggLottoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CRITERIO_AGG_LOTTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CriterioAggLottoType xgetCRITERIOAGGLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CriterioAggLottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CriterioAggLottoType)get_store().find_attribute_user(CRITERIOAGGLOTTO$10);
            return target;
        }
    }
    
    /**
     * Sets the "CRITERIO_AGG_LOTTO" attribute
     */
    public void setCRITERIOAGGLOTTO(it.avlp.simog.massload.xmlbeans.CriterioAggLottoType.Enum criterioagglotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERIOAGGLOTTO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CRITERIOAGGLOTTO$10);
            }
            target.setEnumValue(criterioagglotto);
        }
    }
    
    /**
     * Sets (as xml) the "CRITERIO_AGG_LOTTO" attribute
     */
    public void xsetCRITERIOAGGLOTTO(it.avlp.simog.massload.xmlbeans.CriterioAggLottoType criterioagglotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CriterioAggLottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CriterioAggLottoType)get_store().find_attribute_user(CRITERIOAGGLOTTO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CriterioAggLottoType)get_store().add_attribute_user(CRITERIOAGGLOTTO$10);
            }
            target.set(criterioagglotto);
        }
    }
    
    /**
     * Gets the "TIPO_CRITERIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CriteriaType.Enum getTIPOCRITERIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCRITERIO$12);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.CriteriaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_CRITERIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CriteriaType xgetTIPOCRITERIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CriteriaType)get_store().find_attribute_user(TIPOCRITERIO$12);
            return target;
        }
    }
    
    /**
     * Sets the "TIPO_CRITERIO" attribute
     */
    public void setTIPOCRITERIO(it.avlp.simog.massload.xmlbeans.CriteriaType.Enum tipocriterio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCRITERIO$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOCRITERIO$12);
            }
            target.setEnumValue(tipocriterio);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_CRITERIO" attribute
     */
    public void xsetTIPOCRITERIO(it.avlp.simog.massload.xmlbeans.CriteriaType tipocriterio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CriteriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CriteriaType)get_store().find_attribute_user(TIPOCRITERIO$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CriteriaType)get_store().add_attribute_user(TIPOCRITERIO$12);
            }
            target.set(tipocriterio);
        }
    }
    
    /**
     * Gets the "DESC_RINNOVI_CONTR" attribute
     */
    public java.lang.String getDESCRINNOVICONTR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRINNOVICONTR$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESC_RINNOVI_CONTR" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR xgetDESCRINNOVICONTR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR)get_store().find_attribute_user(DESCRINNOVICONTR$14);
            return target;
        }
    }
    
    /**
     * True if has "DESC_RINNOVI_CONTR" attribute
     */
    public boolean isSetDESCRINNOVICONTR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DESCRINNOVICONTR$14) != null;
        }
    }
    
    /**
     * Sets the "DESC_RINNOVI_CONTR" attribute
     */
    public void setDESCRINNOVICONTR(java.lang.String descrinnovicontr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRINNOVICONTR$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCRINNOVICONTR$14);
            }
            target.setStringValue(descrinnovicontr);
        }
    }
    
    /**
     * Sets (as xml) the "DESC_RINNOVI_CONTR" attribute
     */
    public void xsetDESCRINNOVICONTR(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR descrinnovicontr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR)get_store().find_attribute_user(DESCRINNOVICONTR$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR)get_store().add_attribute_user(DESCRINNOVICONTR$14);
            }
            target.set(descrinnovicontr);
        }
    }
    
    /**
     * Unsets the "DESC_RINNOVI_CONTR" attribute
     */
    public void unsetDESCRINNOVICONTR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DESCRINNOVICONTR$14);
        }
    }
    
    /**
     * Gets the "NUM_CANDIDATI_PREVISTI" attribute
     */
    public int getNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMCANDIDATIPREVISTI$16);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_CANDIDATI_PREVISTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI xgetNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI)get_store().find_attribute_user(NUMCANDIDATIPREVISTI$16);
            return target;
        }
    }
    
    /**
     * True if has "NUM_CANDIDATI_PREVISTI" attribute
     */
    public boolean isSetNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMCANDIDATIPREVISTI$16) != null;
        }
    }
    
    /**
     * Sets the "NUM_CANDIDATI_PREVISTI" attribute
     */
    public void setNUMCANDIDATIPREVISTI(int numcandidatiprevisti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMCANDIDATIPREVISTI$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMCANDIDATIPREVISTI$16);
            }
            target.setIntValue(numcandidatiprevisti);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_CANDIDATI_PREVISTI" attribute
     */
    public void xsetNUMCANDIDATIPREVISTI(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI numcandidatiprevisti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI)get_store().find_attribute_user(NUMCANDIDATIPREVISTI$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI)get_store().add_attribute_user(NUMCANDIDATIPREVISTI$16);
            }
            target.set(numcandidatiprevisti);
        }
    }
    
    /**
     * Unsets the "NUM_CANDIDATI_PREVISTI" attribute
     */
    public void unsetNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMCANDIDATIPREVISTI$16);
        }
    }
    
    /**
     * Gets the "MIN_NUM_CANDIDATI_PREVISTI" attribute
     */
    public int getMINNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MINNUMCANDIDATIPREVISTI$18);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "MIN_NUM_CANDIDATI_PREVISTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI xgetMINNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI)get_store().find_attribute_user(MINNUMCANDIDATIPREVISTI$18);
            return target;
        }
    }
    
    /**
     * True if has "MIN_NUM_CANDIDATI_PREVISTI" attribute
     */
    public boolean isSetMINNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MINNUMCANDIDATIPREVISTI$18) != null;
        }
    }
    
    /**
     * Sets the "MIN_NUM_CANDIDATI_PREVISTI" attribute
     */
    public void setMINNUMCANDIDATIPREVISTI(int minnumcandidatiprevisti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MINNUMCANDIDATIPREVISTI$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MINNUMCANDIDATIPREVISTI$18);
            }
            target.setIntValue(minnumcandidatiprevisti);
        }
    }
    
    /**
     * Sets (as xml) the "MIN_NUM_CANDIDATI_PREVISTI" attribute
     */
    public void xsetMINNUMCANDIDATIPREVISTI(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI minnumcandidatiprevisti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI)get_store().find_attribute_user(MINNUMCANDIDATIPREVISTI$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI)get_store().add_attribute_user(MINNUMCANDIDATIPREVISTI$18);
            }
            target.set(minnumcandidatiprevisti);
        }
    }
    
    /**
     * Unsets the "MIN_NUM_CANDIDATI_PREVISTI" attribute
     */
    public void unsetMINNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MINNUMCANDIDATIPREVISTI$18);
        }
    }
    
    /**
     * Gets the "MAX_NUM_CANDIDATI_PREVISTI" attribute
     */
    public int getMAXNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAXNUMCANDIDATIPREVISTI$20);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "MAX_NUM_CANDIDATI_PREVISTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI xgetMAXNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI)get_store().find_attribute_user(MAXNUMCANDIDATIPREVISTI$20);
            return target;
        }
    }
    
    /**
     * True if has "MAX_NUM_CANDIDATI_PREVISTI" attribute
     */
    public boolean isSetMAXNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MAXNUMCANDIDATIPREVISTI$20) != null;
        }
    }
    
    /**
     * Sets the "MAX_NUM_CANDIDATI_PREVISTI" attribute
     */
    public void setMAXNUMCANDIDATIPREVISTI(int maxnumcandidatiprevisti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAXNUMCANDIDATIPREVISTI$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MAXNUMCANDIDATIPREVISTI$20);
            }
            target.setIntValue(maxnumcandidatiprevisti);
        }
    }
    
    /**
     * Sets (as xml) the "MAX_NUM_CANDIDATI_PREVISTI" attribute
     */
    public void xsetMAXNUMCANDIDATIPREVISTI(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI maxnumcandidatiprevisti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI)get_store().find_attribute_user(MAXNUMCANDIDATIPREVISTI$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI)get_store().add_attribute_user(MAXNUMCANDIDATIPREVISTI$20);
            }
            target.set(maxnumcandidatiprevisti);
        }
    }
    
    /**
     * Unsets the "MAX_NUM_CANDIDATI_PREVISTI" attribute
     */
    public void unsetMAXNUMCANDIDATIPREVISTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MAXNUMCANDIDATIPREVISTI$20);
        }
    }
    
    /**
     * Gets the "CRITERI_MAX_NUM_CANDIDATI" attribute
     */
    public java.lang.String getCRITERIMAXNUMCANDIDATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERIMAXNUMCANDIDATI$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CRITERI_MAX_NUM_CANDIDATI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI xgetCRITERIMAXNUMCANDIDATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI)get_store().find_attribute_user(CRITERIMAXNUMCANDIDATI$22);
            return target;
        }
    }
    
    /**
     * True if has "CRITERI_MAX_NUM_CANDIDATI" attribute
     */
    public boolean isSetCRITERIMAXNUMCANDIDATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CRITERIMAXNUMCANDIDATI$22) != null;
        }
    }
    
    /**
     * Sets the "CRITERI_MAX_NUM_CANDIDATI" attribute
     */
    public void setCRITERIMAXNUMCANDIDATI(java.lang.String criterimaxnumcandidati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERIMAXNUMCANDIDATI$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CRITERIMAXNUMCANDIDATI$22);
            }
            target.setStringValue(criterimaxnumcandidati);
        }
    }
    
    /**
     * Sets (as xml) the "CRITERI_MAX_NUM_CANDIDATI" attribute
     */
    public void xsetCRITERIMAXNUMCANDIDATI(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI criterimaxnumcandidati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI)get_store().find_attribute_user(CRITERIMAXNUMCANDIDATI$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI)get_store().add_attribute_user(CRITERIMAXNUMCANDIDATI$22);
            }
            target.set(criterimaxnumcandidati);
        }
    }
    
    /**
     * Unsets the "CRITERI_MAX_NUM_CANDIDATI" attribute
     */
    public void unsetCRITERIMAXNUMCANDIDATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CRITERIMAXNUMCANDIDATI$22);
        }
    }
    
    /**
     * Gets the "ACCETTATE_VARIANTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getACCETTATEVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACCETTATEVARIANTI$24);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ACCETTATE_VARIANTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetACCETTATEVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ACCETTATEVARIANTI$24);
            return target;
        }
    }
    
    /**
     * Sets the "ACCETTATE_VARIANTI" attribute
     */
    public void setACCETTATEVARIANTI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum accettatevarianti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACCETTATEVARIANTI$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ACCETTATEVARIANTI$24);
            }
            target.setEnumValue(accettatevarianti);
        }
    }
    
    /**
     * Sets (as xml) the "ACCETTATE_VARIANTI" attribute
     */
    public void xsetACCETTATEVARIANTI(it.avlp.simog.massload.xmlbeans.FlagSNType accettatevarianti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ACCETTATEVARIANTI$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(ACCETTATEVARIANTI$24);
            }
            target.set(accettatevarianti);
        }
    }
    
    /**
     * Gets the "DESCRIZIONE_OPZIONI" attribute
     */
    public java.lang.String getDESCRIZIONEOPZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONEOPZIONI$26);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESCRIZIONE_OPZIONI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI xgetDESCRIZIONEOPZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI)get_store().find_attribute_user(DESCRIZIONEOPZIONI$26);
            return target;
        }
    }
    
    /**
     * True if has "DESCRIZIONE_OPZIONI" attribute
     */
    public boolean isSetDESCRIZIONEOPZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DESCRIZIONEOPZIONI$26) != null;
        }
    }
    
    /**
     * Sets the "DESCRIZIONE_OPZIONI" attribute
     */
    public void setDESCRIZIONEOPZIONI(java.lang.String descrizioneopzioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONEOPZIONI$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCRIZIONEOPZIONI$26);
            }
            target.setStringValue(descrizioneopzioni);
        }
    }
    
    /**
     * Sets (as xml) the "DESCRIZIONE_OPZIONI" attribute
     */
    public void xsetDESCRIZIONEOPZIONI(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI descrizioneopzioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI)get_store().find_attribute_user(DESCRIZIONEOPZIONI$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI)get_store().add_attribute_user(DESCRIZIONEOPZIONI$26);
            }
            target.set(descrizioneopzioni);
        }
    }
    
    /**
     * Unsets the "DESCRIZIONE_OPZIONI" attribute
     */
    public void unsetDESCRIZIONEOPZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DESCRIZIONEOPZIONI$26);
        }
    }
    
    /**
     * Gets the "PRES_OFFERTE_CATALOGO_ELETTRONICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getPRESOFFERTECATALOGOELETTRONICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRESOFFERTECATALOGOELETTRONICO$28);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PRES_OFFERTE_CATALOGO_ELETTRONICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetPRESOFFERTECATALOGOELETTRONICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PRESOFFERTECATALOGOELETTRONICO$28);
            return target;
        }
    }
    
    /**
     * Sets the "PRES_OFFERTE_CATALOGO_ELETTRONICO" attribute
     */
    public void setPRESOFFERTECATALOGOELETTRONICO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum presoffertecatalogoelettronico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRESOFFERTECATALOGOELETTRONICO$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PRESOFFERTECATALOGOELETTRONICO$28);
            }
            target.setEnumValue(presoffertecatalogoelettronico);
        }
    }
    
    /**
     * Sets (as xml) the "PRES_OFFERTE_CATALOGO_ELETTRONICO" attribute
     */
    public void xsetPRESOFFERTECATALOGOELETTRONICO(it.avlp.simog.massload.xmlbeans.FlagSNType presoffertecatalogoelettronico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PRESOFFERTECATALOGOELETTRONICO$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(PRESOFFERTECATALOGOELETTRONICO$28);
            }
            target.set(presoffertecatalogoelettronico);
        }
    }
    
    /**
     * Gets the "FLAG_APPALTO_PROGETTO_UE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGAPPALTOPROGETTOUE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAPPALTOPROGETTOUE$30);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_APPALTO_PROGETTO_UE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGAPPALTOPROGETTOUE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGAPPALTOPROGETTOUE$30);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_APPALTO_PROGETTO_UE" attribute
     */
    public void setFLAGAPPALTOPROGETTOUE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagappaltoprogettoue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAPPALTOPROGETTOUE$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGAPPALTOPROGETTOUE$30);
            }
            target.setEnumValue(flagappaltoprogettoue);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_APPALTO_PROGETTO_UE" attribute
     */
    public void xsetFLAGAPPALTOPROGETTOUE(it.avlp.simog.massload.xmlbeans.FlagSNType flagappaltoprogettoue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGAPPALTOPROGETTOUE$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGAPPALTOPROGETTOUE$30);
            }
            target.set(flagappaltoprogettoue);
        }
    }
    
    /**
     * Gets the "APPALTO_PROGETTO_UE" attribute
     */
    public java.lang.String getAPPALTOPROGETTOUE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(APPALTOPROGETTOUE$32);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "APPALTO_PROGETTO_UE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE xgetAPPALTOPROGETTOUE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE)get_store().find_attribute_user(APPALTOPROGETTOUE$32);
            return target;
        }
    }
    
    /**
     * True if has "APPALTO_PROGETTO_UE" attribute
     */
    public boolean isSetAPPALTOPROGETTOUE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(APPALTOPROGETTOUE$32) != null;
        }
    }
    
    /**
     * Sets the "APPALTO_PROGETTO_UE" attribute
     */
    public void setAPPALTOPROGETTOUE(java.lang.String appaltoprogettoue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(APPALTOPROGETTOUE$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(APPALTOPROGETTOUE$32);
            }
            target.setStringValue(appaltoprogettoue);
        }
    }
    
    /**
     * Sets (as xml) the "APPALTO_PROGETTO_UE" attribute
     */
    public void xsetAPPALTOPROGETTOUE(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE appaltoprogettoue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE)get_store().find_attribute_user(APPALTOPROGETTOUE$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE)get_store().add_attribute_user(APPALTOPROGETTOUE$32);
            }
            target.set(appaltoprogettoue);
        }
    }
    
    /**
     * Unsets the "APPALTO_PROGETTO_UE" attribute
     */
    public void unsetAPPALTOPROGETTOUE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(APPALTOPROGETTOUE$32);
        }
    }
    
    /**
     * Gets the "ULTERIORI_INFO_LOTTO" attribute
     */
    public java.lang.String getULTERIORIINFOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ULTERIORIINFOLOTTO$34);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ULTERIORI_INFO_LOTTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO xgetULTERIORIINFOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO)get_store().find_attribute_user(ULTERIORIINFOLOTTO$34);
            return target;
        }
    }
    
    /**
     * True if has "ULTERIORI_INFO_LOTTO" attribute
     */
    public boolean isSetULTERIORIINFOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ULTERIORIINFOLOTTO$34) != null;
        }
    }
    
    /**
     * Sets the "ULTERIORI_INFO_LOTTO" attribute
     */
    public void setULTERIORIINFOLOTTO(java.lang.String ulterioriinfolotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ULTERIORIINFOLOTTO$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ULTERIORIINFOLOTTO$34);
            }
            target.setStringValue(ulterioriinfolotto);
        }
    }
    
    /**
     * Sets (as xml) the "ULTERIORI_INFO_LOTTO" attribute
     */
    public void xsetULTERIORIINFOLOTTO(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO ulterioriinfolotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO)get_store().find_attribute_user(ULTERIORIINFOLOTTO$34);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO)get_store().add_attribute_user(ULTERIORIINFOLOTTO$34);
            }
            target.set(ulterioriinfolotto);
        }
    }
    
    /**
     * Unsets the "ULTERIORI_INFO_LOTTO" attribute
     */
    public void unsetULTERIORIINFOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ULTERIORIINFOLOTTO$34);
        }
    }
    /**
     * An XML TITOLO_APPALTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$TITOLOAPPALTO.
     */
    public static class TITOLOAPPALTOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.TITOLOAPPALTO
    {
        
        public TITOLOAPPALTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected TITOLOAPPALTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML LUOGO_ESECUZIONE_PRINCIPALE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$LUOGOESECUZIONEPRINCIPALE.
     */
    public static class LUOGOESECUZIONEPRINCIPALEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.LUOGOESECUZIONEPRINCIPALE
    {
        
        public LUOGOESECUZIONEPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected LUOGOESECUZIONEPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DESC_RINNOVI_CONTR(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$DESCRINNOVICONTR.
     */
    public static class DESCRINNOVICONTRImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRINNOVICONTR
    {
        
        public DESCRINNOVICONTRImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DESCRINNOVICONTRImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NUM_CANDIDATI_PREVISTI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$NUMCANDIDATIPREVISTI.
     */
    public static class NUMCANDIDATIPREVISTIImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.NUMCANDIDATIPREVISTI
    {
        
        public NUMCANDIDATIPREVISTIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NUMCANDIDATIPREVISTIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML MIN_NUM_CANDIDATI_PREVISTI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$MINNUMCANDIDATIPREVISTI.
     */
    public static class MINNUMCANDIDATIPREVISTIImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MINNUMCANDIDATIPREVISTI
    {
        
        public MINNUMCANDIDATIPREVISTIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MINNUMCANDIDATIPREVISTIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML MAX_NUM_CANDIDATI_PREVISTI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$MAXNUMCANDIDATIPREVISTI.
     */
    public static class MAXNUMCANDIDATIPREVISTIImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.MAXNUMCANDIDATIPREVISTI
    {
        
        public MAXNUMCANDIDATIPREVISTIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MAXNUMCANDIDATIPREVISTIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CRITERI_MAX_NUM_CANDIDATI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$CRITERIMAXNUMCANDIDATI.
     */
    public static class CRITERIMAXNUMCANDIDATIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.CRITERIMAXNUMCANDIDATI
    {
        
        public CRITERIMAXNUMCANDIDATIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CRITERIMAXNUMCANDIDATIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DESCRIZIONE_OPZIONI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$DESCRIZIONEOPZIONI.
     */
    public static class DESCRIZIONEOPZIONIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.DESCRIZIONEOPZIONI
    {
        
        public DESCRIZIONEOPZIONIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DESCRIZIONEOPZIONIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML APPALTO_PROGETTO_UE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$APPALTOPROGETTOUE.
     */
    public static class APPALTOPROGETTOUEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.APPALTOPROGETTOUE
    {
        
        public APPALTOPROGETTOUEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected APPALTOPROGETTOUEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML ULTERIORI_INFO_LOTTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType$ULTERIORIINFOLOTTO.
     */
    public static class ULTERIORIINFOLOTTOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType.ULTERIORIINFOLOTTO
    {
        
        public ULTERIORIINFOLOTTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ULTERIORIINFOLOTTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
