/*
 * XML Type:  SubappaltoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SubappaltoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SubappaltoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SubappaltoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SubappaltoType
{
    
    public SubappaltoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SUBAPPALTATORE$0 = 
        new javax.xml.namespace.QName("", "Subappaltatore");
    private static final javax.xml.namespace.QName CFDITTA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_DITTA");
    private static final javax.xml.namespace.QName FLAGDITTASUBESTERA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_DITTA_SUB_ESTERA");
    private static final javax.xml.namespace.QName DATAAUTORIZZAZIONE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_AUTORIZZAZIONE");
    private static final javax.xml.namespace.QName OGGETTOSUBAPPALTO$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OGGETTO_SUBAPPALTO");
    private static final javax.xml.namespace.QName IMPORTOPRESUNTO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_PRESUNTO");
    private static final javax.xml.namespace.QName IMPORTOEFFETTIVO$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_EFFETTIVO");
    private static final javax.xml.namespace.QName IDCATEGORIA$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_CATEGORIA");
    private static final javax.xml.namespace.QName IDCPV$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_CPV");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName CODICEFISCALEAGGIUDICATARIO$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_AGGIUDICATARIO");
    private static final javax.xml.namespace.QName CODICESTATO$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_STATO");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets array of all "Subappaltatore" elements
     */
    public it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType[] getSubappaltatoreArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SUBAPPALTATORE$0, targetList);
            it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType[] result = new it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Subappaltatore" element
     */
    public it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType getSubappaltatoreArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType)get_store().find_element_user(SUBAPPALTATORE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Subappaltatore" element
     */
    public int sizeOfSubappaltatoreArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUBAPPALTATORE$0);
        }
    }
    
    /**
     * Sets array of all "Subappaltatore" element
     */
    public void setSubappaltatoreArray(it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType[] subappaltatoreArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(subappaltatoreArray, SUBAPPALTATORE$0);
        }
    }
    
    /**
     * Sets ith "Subappaltatore" element
     */
    public void setSubappaltatoreArray(int i, it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType subappaltatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType)get_store().find_element_user(SUBAPPALTATORE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(subappaltatore);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Subappaltatore" element
     */
    public it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType insertNewSubappaltatore(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType)get_store().insert_element_user(SUBAPPALTATORE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Subappaltatore" element
     */
    public it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType addNewSubappaltatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType)get_store().add_element_user(SUBAPPALTATORE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Subappaltatore" element
     */
    public void removeSubappaltatore(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUBAPPALTATORE$0, i);
        }
    }
    
    /**
     * Gets the "CF_DITTA" attribute
     */
    public java.lang.String getCFDITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFDITTA$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_DITTA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFDITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFDITTA$2);
            return target;
        }
    }
    
    /**
     * Sets the "CF_DITTA" attribute
     */
    public void setCFDITTA(java.lang.String cfditta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFDITTA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFDITTA$2);
            }
            target.setStringValue(cfditta);
        }
    }
    
    /**
     * Sets (as xml) the "CF_DITTA" attribute
     */
    public void xsetCFDITTA(it.avlp.simog.massload.xmlbeans.CodFiscType cfditta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFDITTA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFDITTA$2);
            }
            target.set(cfditta);
        }
    }
    
    /**
     * Gets the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGDITTASUBESTERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGDITTASUBESTERA$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGDITTASUBESTERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGDITTASUBESTERA$4);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_DITTA_SUB_ESTERA" attribute
     */
    public boolean isSetFLAGDITTASUBESTERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGDITTASUBESTERA$4) != null;
        }
    }
    
    /**
     * Sets the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    public void setFLAGDITTASUBESTERA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagdittasubestera)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGDITTASUBESTERA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGDITTASUBESTERA$4);
            }
            target.setEnumValue(flagdittasubestera);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    public void xsetFLAGDITTASUBESTERA(it.avlp.simog.massload.xmlbeans.FlagSNType flagdittasubestera)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGDITTASUBESTERA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGDITTASUBESTERA$4);
            }
            target.set(flagdittasubestera);
        }
    }
    
    /**
     * Unsets the "FLAG_DITTA_SUB_ESTERA" attribute
     */
    public void unsetFLAGDITTASUBESTERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGDITTASUBESTERA$4);
        }
    }
    
    /**
     * Gets the "DATA_AUTORIZZAZIONE" attribute
     */
    public java.util.Calendar getDATAAUTORIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAUTORIZZAZIONE$6);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_AUTORIZZAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAAUTORIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAUTORIZZAZIONE$6);
            return target;
        }
    }
    
    /**
     * True if has "DATA_AUTORIZZAZIONE" attribute
     */
    public boolean isSetDATAAUTORIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAAUTORIZZAZIONE$6) != null;
        }
    }
    
    /**
     * Sets the "DATA_AUTORIZZAZIONE" attribute
     */
    public void setDATAAUTORIZZAZIONE(java.util.Calendar dataautorizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAUTORIZZAZIONE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAAUTORIZZAZIONE$6);
            }
            target.setCalendarValue(dataautorizzazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_AUTORIZZAZIONE" attribute
     */
    public void xsetDATAAUTORIZZAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType dataautorizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAUTORIZZAZIONE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAAUTORIZZAZIONE$6);
            }
            target.set(dataautorizzazione);
        }
    }
    
    /**
     * Unsets the "DATA_AUTORIZZAZIONE" attribute
     */
    public void unsetDATAAUTORIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAAUTORIZZAZIONE$6);
        }
    }
    
    /**
     * Gets the "OGGETTO_SUBAPPALTO" attribute
     */
    public java.lang.String getOGGETTOSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OGGETTOSUBAPPALTO$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OGGETTO_SUBAPPALTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO xgetOGGETTOSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO)get_store().find_attribute_user(OGGETTOSUBAPPALTO$8);
            return target;
        }
    }
    
    /**
     * True if has "OGGETTO_SUBAPPALTO" attribute
     */
    public boolean isSetOGGETTOSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OGGETTOSUBAPPALTO$8) != null;
        }
    }
    
    /**
     * Sets the "OGGETTO_SUBAPPALTO" attribute
     */
    public void setOGGETTOSUBAPPALTO(java.lang.String oggettosubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OGGETTOSUBAPPALTO$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OGGETTOSUBAPPALTO$8);
            }
            target.setStringValue(oggettosubappalto);
        }
    }
    
    /**
     * Sets (as xml) the "OGGETTO_SUBAPPALTO" attribute
     */
    public void xsetOGGETTOSUBAPPALTO(it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO oggettosubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO)get_store().find_attribute_user(OGGETTOSUBAPPALTO$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO)get_store().add_attribute_user(OGGETTOSUBAPPALTO$8);
            }
            target.set(oggettosubappalto);
        }
    }
    
    /**
     * Unsets the "OGGETTO_SUBAPPALTO" attribute
     */
    public void unsetOGGETTOSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OGGETTOSUBAPPALTO$8);
        }
    }
    
    /**
     * Gets the "IMPORTO_PRESUNTO" attribute
     */
    public java.math.BigDecimal getIMPORTOPRESUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOPRESUNTO$10);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_PRESUNTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOPRESUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOPRESUNTO$10);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_PRESUNTO" attribute
     */
    public void setIMPORTOPRESUNTO(java.math.BigDecimal importopresunto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOPRESUNTO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOPRESUNTO$10);
            }
            target.setBigDecimalValue(importopresunto);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_PRESUNTO" attribute
     */
    public void xsetIMPORTOPRESUNTO(it.avlp.simog.massload.xmlbeans.ImportoType importopresunto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOPRESUNTO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOPRESUNTO$10);
            }
            target.set(importopresunto);
        }
    }
    
    /**
     * Gets the "IMPORTO_EFFETTIVO" attribute
     */
    public java.math.BigDecimal getIMPORTOEFFETTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOEFFETTIVO$12);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_EFFETTIVO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOEFFETTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOEFFETTIVO$12);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_EFFETTIVO" attribute
     */
    public boolean isSetIMPORTOEFFETTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOEFFETTIVO$12) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_EFFETTIVO" attribute
     */
    public void setIMPORTOEFFETTIVO(java.math.BigDecimal importoeffettivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOEFFETTIVO$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOEFFETTIVO$12);
            }
            target.setBigDecimalValue(importoeffettivo);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_EFFETTIVO" attribute
     */
    public void xsetIMPORTOEFFETTIVO(it.avlp.simog.massload.xmlbeans.ImportoType importoeffettivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOEFFETTIVO$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOEFFETTIVO$12);
            }
            target.set(importoeffettivo);
        }
    }
    
    /**
     * Unsets the "IMPORTO_EFFETTIVO" attribute
     */
    public void unsetIMPORTOEFFETTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOEFFETTIVO$12);
        }
    }
    
    /**
     * Gets the "ID_CATEGORIA" attribute
     */
    public java.lang.String getIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCATEGORIA$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CATEGORIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CategoriaType xgetIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().find_attribute_user(IDCATEGORIA$14);
            return target;
        }
    }
    
    /**
     * True if has "ID_CATEGORIA" attribute
     */
    public boolean isSetIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDCATEGORIA$14) != null;
        }
    }
    
    /**
     * Sets the "ID_CATEGORIA" attribute
     */
    public void setIDCATEGORIA(java.lang.String idcategoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCATEGORIA$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDCATEGORIA$14);
            }
            target.setStringValue(idcategoria);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CATEGORIA" attribute
     */
    public void xsetIDCATEGORIA(it.avlp.simog.massload.xmlbeans.CategoriaType idcategoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().find_attribute_user(IDCATEGORIA$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().add_attribute_user(IDCATEGORIA$14);
            }
            target.set(idcategoria);
        }
    }
    
    /**
     * Unsets the "ID_CATEGORIA" attribute
     */
    public void unsetIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDCATEGORIA$14);
        }
    }
    
    /**
     * Gets the "ID_CPV" attribute
     */
    public java.lang.String getIDCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCPV$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CPV" attribute
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV xgetIDCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV)get_store().find_attribute_user(IDCPV$16);
            return target;
        }
    }
    
    /**
     * Sets the "ID_CPV" attribute
     */
    public void setIDCPV(java.lang.String idcpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCPV$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDCPV$16);
            }
            target.setStringValue(idcpv);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CPV" attribute
     */
    public void xsetIDCPV(it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV idcpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV)get_store().find_attribute_user(IDCPV$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV)get_store().add_attribute_user(IDCPV$16);
            }
            target.set(idcpv);
        }
    }
    
    /**
     * Gets the "ID_SCHEDA_LOCALE" attribute
     */
    public java.lang.String getIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_SCHEDA_LOCALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IdSchedaType xgetIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$18);
            return target;
        }
    }
    
    /**
     * True if has "ID_SCHEDA_LOCALE" attribute
     */
    public boolean isSetIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSCHEDALOCALE$18) != null;
        }
    }
    
    /**
     * Sets the "ID_SCHEDA_LOCALE" attribute
     */
    public void setIDSCHEDALOCALE(java.lang.String idschedalocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$18);
            }
            target.setStringValue(idschedalocale);
        }
    }
    
    /**
     * Sets (as xml) the "ID_SCHEDA_LOCALE" attribute
     */
    public void xsetIDSCHEDALOCALE(it.avlp.simog.massload.xmlbeans.IdSchedaType idschedalocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$18);
            }
            target.set(idschedalocale);
        }
    }
    
    /**
     * Unsets the "ID_SCHEDA_LOCALE" attribute
     */
    public void unsetIDSCHEDALOCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSCHEDALOCALE$18);
        }
    }
    
    /**
     * Gets the "ID_SCHEDA_SIMOG" attribute
     */
    public java.lang.String getIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_SCHEDA_SIMOG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IdSchedaType xgetIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$20);
            return target;
        }
    }
    
    /**
     * True if has "ID_SCHEDA_SIMOG" attribute
     */
    public boolean isSetIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSCHEDASIMOG$20) != null;
        }
    }
    
    /**
     * Sets the "ID_SCHEDA_SIMOG" attribute
     */
    public void setIDSCHEDASIMOG(java.lang.String idschedasimog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$20);
            }
            target.setStringValue(idschedasimog);
        }
    }
    
    /**
     * Sets (as xml) the "ID_SCHEDA_SIMOG" attribute
     */
    public void xsetIDSCHEDASIMOG(it.avlp.simog.massload.xmlbeans.IdSchedaType idschedasimog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IdSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$20);
            }
            target.set(idschedasimog);
        }
    }
    
    /**
     * Unsets the "ID_SCHEDA_SIMOG" attribute
     */
    public void unsetIDSCHEDASIMOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSCHEDASIMOG$20);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public java.lang.String getCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$22);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public boolean isSetCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$22) != null;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public void setCODICEFISCALEAGGIUDICATARIO(java.lang.String codicefiscaleaggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$22);
            }
            target.setStringValue(codicefiscaleaggiudicatario);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public void xsetCODICEFISCALEAGGIUDICATARIO(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleaggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$22);
            }
            target.set(codicefiscaleaggiudicatario);
        }
    }
    
    /**
     * Unsets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public void unsetCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODICEFISCALEAGGIUDICATARIO$22);
        }
    }
    
    /**
     * Gets the "CODICE_STATO" attribute
     */
    public java.lang.String getCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_STATO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$24);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_STATO" attribute
     */
    public boolean isSetCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODICESTATO$24) != null;
        }
    }
    
    /**
     * Sets the "CODICE_STATO" attribute
     */
    public void setCODICESTATO(java.lang.String codicestato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICESTATO$24);
            }
            target.setStringValue(codicestato);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_STATO" attribute
     */
    public void xsetCODICESTATO(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().add_attribute_user(CODICESTATO$24);
            }
            target.set(codicestato);
        }
    }
    
    /**
     * Unsets the "CODICE_STATO" attribute
     */
    public void unsetCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODICESTATO$24);
        }
    }
    
    /**
     * Gets the "ID_STATO_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum getIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoSchedaType xgetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            return target;
        }
    }
    
    /**
     * True if has "ID_STATO_SCHEDA" attribute
     */
    public boolean isSetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSTATOSCHEDA$26) != null;
        }
    }
    
    /**
     * Sets the "ID_STATO_SCHEDA" attribute
     */
    public void setIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum idstatoscheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$26);
            }
            target.setEnumValue(idstatoscheda);
        }
    }
    
    /**
     * Sets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    public void xsetIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType idstatoscheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$26);
            }
            target.set(idstatoscheda);
        }
    }
    
    /**
     * Unsets the "ID_STATO_SCHEDA" attribute
     */
    public void unsetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSTATOSCHEDA$26);
        }
    }
    /**
     * An XML OGGETTO_SUBAPPALTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.SubappaltoType$OGGETTOSUBAPPALTO.
     */
    public static class OGGETTOSUBAPPALTOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.SubappaltoType.OGGETTOSUBAPPALTO
    {
        
        public OGGETTOSUBAPPALTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected OGGETTOSUBAPPALTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML ID_CPV(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.SubappaltoType$IDCPV.
     */
    public static class IDCPVImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.SubappaltoType.IDCPV
    {
        
        public IDCPVImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected IDCPVImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
