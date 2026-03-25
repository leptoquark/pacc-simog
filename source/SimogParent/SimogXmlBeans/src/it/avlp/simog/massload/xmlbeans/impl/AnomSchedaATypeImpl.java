/*
 * XML Type:  AnomScheda_AType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AnomSchedaAType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AnomScheda_AType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AnomSchedaATypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AnomSchedaAType
{
    
    public AnomSchedaATypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ANOMALIA$0 = 
        new javax.xml.namespace.QName("", "Anomalia");
    private static final javax.xml.namespace.QName IDSCHEDA$2 = 
        new javax.xml.namespace.QName("", "IdScheda");
    private static final javax.xml.namespace.QName CUPLOTTO$4 = 
        new javax.xml.namespace.QName("", "CUPLOTTO");
    private static final javax.xml.namespace.QName CIG$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG");
    private static final javax.xml.namespace.QName PROGRESSIVO$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PROGRESSIVO");
    private static final javax.xml.namespace.QName CUI$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CUI");
    
    
    /**
     * Gets array of all "Anomalia" elements
     */
    public it.avlp.simog.massload.xmlbeans.AnomaliaType[] getAnomaliaArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ANOMALIA$0, targetList);
            it.avlp.simog.massload.xmlbeans.AnomaliaType[] result = new it.avlp.simog.massload.xmlbeans.AnomaliaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Anomalia" element
     */
    public it.avlp.simog.massload.xmlbeans.AnomaliaType getAnomaliaArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AnomaliaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AnomaliaType)get_store().find_element_user(ANOMALIA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Anomalia" element
     */
    public int sizeOfAnomaliaArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ANOMALIA$0);
        }
    }
    
    /**
     * Sets array of all "Anomalia" element
     */
    public void setAnomaliaArray(it.avlp.simog.massload.xmlbeans.AnomaliaType[] anomaliaArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(anomaliaArray, ANOMALIA$0);
        }
    }
    
    /**
     * Sets ith "Anomalia" element
     */
    public void setAnomaliaArray(int i, it.avlp.simog.massload.xmlbeans.AnomaliaType anomalia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AnomaliaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AnomaliaType)get_store().find_element_user(ANOMALIA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(anomalia);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Anomalia" element
     */
    public it.avlp.simog.massload.xmlbeans.AnomaliaType insertNewAnomalia(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AnomaliaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AnomaliaType)get_store().insert_element_user(ANOMALIA$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Anomalia" element
     */
    public it.avlp.simog.massload.xmlbeans.AnomaliaType addNewAnomalia()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AnomaliaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AnomaliaType)get_store().add_element_user(ANOMALIA$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Anomalia" element
     */
    public void removeAnomalia(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ANOMALIA$0, i);
        }
    }
    
    /**
     * Gets array of all "IdScheda" elements
     */
    public it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType[] getIdSchedaArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDSCHEDA$2, targetList);
            it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType[] result = new it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "IdScheda" element
     */
    public it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType getIdSchedaArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType)get_store().find_element_user(IDSCHEDA$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "IdScheda" element
     */
    public int sizeOfIdSchedaArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDSCHEDA$2);
        }
    }
    
    /**
     * Sets array of all "IdScheda" element
     */
    public void setIdSchedaArray(it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType[] idSchedaArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idSchedaArray, IDSCHEDA$2);
        }
    }
    
    /**
     * Sets ith "IdScheda" element
     */
    public void setIdSchedaArray(int i, it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType idScheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType)get_store().find_element_user(IDSCHEDA$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idScheda);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "IdScheda" element
     */
    public it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType insertNewIdScheda(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType)get_store().insert_element_user(IDSCHEDA$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "IdScheda" element
     */
    public it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType addNewIdScheda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType)get_store().add_element_user(IDSCHEDA$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "IdScheda" element
     */
    public void removeIdScheda(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDSCHEDA$2, i);
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
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().find_element_user(CUPLOTTO$4, 0);
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
            return get_store().count_elements(CUPLOTTO$4) != 0;
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
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().find_element_user(CUPLOTTO$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().add_element_user(CUPLOTTO$4);
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
            target = (it.avlp.simog.massload.xmlbeans.CUPLOTTOType)get_store().add_element_user(CUPLOTTO$4);
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
            get_store().remove_element(CUPLOTTO$4, 0);
        }
    }
    
    /**
     * Gets the "CIG" attribute
     */
    public java.lang.String getCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$6);
            return target;
        }
    }
    
    /**
     * Sets the "CIG" attribute
     */
    public void setCIG(java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIG$6);
            }
            target.setStringValue(cig);
        }
    }
    
    /**
     * Sets (as xml) the "CIG" attribute
     */
    public void xsetCIG(it.avlp.simog.massload.xmlbeans.CigType cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIG$6);
            }
            target.set(cig);
        }
    }
    
    /**
     * Gets the "PROGRESSIVO" attribute
     */
    public int getPROGRESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGRESSIVO$8);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROGRESSIVO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetPROGRESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(PROGRESSIVO$8);
            return target;
        }
    }
    
    /**
     * Sets the "PROGRESSIVO" attribute
     */
    public void setPROGRESSIVO(int progressivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGRESSIVO$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROGRESSIVO$8);
            }
            target.setIntValue(progressivo);
        }
    }
    
    /**
     * Sets (as xml) the "PROGRESSIVO" attribute
     */
    public void xsetPROGRESSIVO(it.avlp.simog.massload.xmlbeans.InteroType progressivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(PROGRESSIVO$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(PROGRESSIVO$8);
            }
            target.set(progressivo);
        }
    }
    
    /**
     * Gets the "CUI" attribute
     */
    public java.lang.String getCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUI$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CUI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CuiType xgetCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(CUI$10);
            return target;
        }
    }
    
    /**
     * True if has "CUI" attribute
     */
    public boolean isSetCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CUI$10) != null;
        }
    }
    
    /**
     * Sets the "CUI" attribute
     */
    public void setCUI(java.lang.String cui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUI$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUI$10);
            }
            target.setStringValue(cui);
        }
    }
    
    /**
     * Sets (as xml) the "CUI" attribute
     */
    public void xsetCUI(it.avlp.simog.massload.xmlbeans.CuiType cui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(CUI$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().add_attribute_user(CUI$10);
            }
            target.set(cui);
        }
    }
    
    /**
     * Unsets the "CUI" attribute
     */
    public void unsetCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CUI$10);
        }
    }
}
