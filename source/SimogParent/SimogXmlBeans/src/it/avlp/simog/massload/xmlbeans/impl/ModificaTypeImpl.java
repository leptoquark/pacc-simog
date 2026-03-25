/*
 * XML Type:  ModificaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ModificaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ModificaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ModificaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ModificaType
{
    
    public ModificaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CPVSECONDARIE$0 = 
        new javax.xml.namespace.QName("", "CPV_SECONDARIE");
    private static final javax.xml.namespace.QName NUTS$2 = 
        new javax.xml.namespace.QName("", "NUTS");
    private static final javax.xml.namespace.QName CONTRAENTE$4 = 
        new javax.xml.namespace.QName("", "CONTRAENTE");
    private static final javax.xml.namespace.QName CPVPRINCIPALE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CPV_PRINCIPALE");
    private static final javax.xml.namespace.QName LUOGOESECPRINCIPALE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_ESEC_PRINCIPALE");
    private static final javax.xml.namespace.QName DESCPROCUREMENT$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESC_PROCUREMENT");
    private static final javax.xml.namespace.QName DURATACONTRATTOMESI$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DURATA_CONTRATTO_MESI");
    private static final javax.xml.namespace.QName DURATACONTRATTOGIORNI$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DURATA_CONTRATTO_GIORNI");
    private static final javax.xml.namespace.QName INIZIOCONTRATTOLOTTO$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INIZIO_CONTRATTO_LOTTO");
    private static final javax.xml.namespace.QName FINECONTRATTOLOTTO$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FINE_CONTRATTO_LOTTO");
    private static final javax.xml.namespace.QName JUSTIFICATION$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "JUSTIFICATION");
    private static final javax.xml.namespace.QName VALTOTAL$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_TOTAL");
    private static final javax.xml.namespace.QName DESCNATURECHANGES$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESC_NATURE_CHANGES");
    private static final javax.xml.namespace.QName REASONMODIFICATION$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "REASON_MODIFICATION");
    private static final javax.xml.namespace.QName DESCREASONMODIFICATION$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESC_REASON_MODIFICATION");
    private static final javax.xml.namespace.QName VALTOTALBEFORE$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_TOTAL_BEFORE");
    private static final javax.xml.namespace.QName VALTOTALAFTER$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_TOTAL_AFTER");
    
    
    /**
     * Gets array of all "CPV_SECONDARIE" elements
     */
    public it.avlp.simog.massload.xmlbeans.ModificaCpvSecType[] getCPVSECONDARIEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CPVSECONDARIE$0, targetList);
            it.avlp.simog.massload.xmlbeans.ModificaCpvSecType[] result = new it.avlp.simog.massload.xmlbeans.ModificaCpvSecType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "CPV_SECONDARIE" element
     */
    public it.avlp.simog.massload.xmlbeans.ModificaCpvSecType getCPVSECONDARIEArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaCpvSecType)get_store().find_element_user(CPVSECONDARIE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "CPV_SECONDARIE" element
     */
    public int sizeOfCPVSECONDARIEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CPVSECONDARIE$0);
        }
    }
    
    /**
     * Sets array of all "CPV_SECONDARIE" element
     */
    public void setCPVSECONDARIEArray(it.avlp.simog.massload.xmlbeans.ModificaCpvSecType[] cpvsecondarieArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(cpvsecondarieArray, CPVSECONDARIE$0);
        }
    }
    
    /**
     * Sets ith "CPV_SECONDARIE" element
     */
    public void setCPVSECONDARIEArray(int i, it.avlp.simog.massload.xmlbeans.ModificaCpvSecType cpvsecondarie)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaCpvSecType)get_store().find_element_user(CPVSECONDARIE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(cpvsecondarie);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CPV_SECONDARIE" element
     */
    public it.avlp.simog.massload.xmlbeans.ModificaCpvSecType insertNewCPVSECONDARIE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaCpvSecType)get_store().insert_element_user(CPVSECONDARIE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CPV_SECONDARIE" element
     */
    public it.avlp.simog.massload.xmlbeans.ModificaCpvSecType addNewCPVSECONDARIE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaCpvSecType)get_store().add_element_user(CPVSECONDARIE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "CPV_SECONDARIE" element
     */
    public void removeCPVSECONDARIE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CPVSECONDARIE$0, i);
        }
    }
    
    /**
     * Gets array of all "NUTS" elements
     */
    public java.lang.String[] getNUTSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(NUTS$2, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "NUTS" element
     */
    public java.lang.String getNUTSArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUTS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "NUTS" elements
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType[] xgetNUTSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(NUTS$2, targetList);
            it.avlp.simog.massload.xmlbeans.LuogoNutsType[] result = new it.avlp.simog.massload.xmlbeans.LuogoNutsType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "NUTS" element
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType xgetNUTSArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_element_user(NUTS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (it.avlp.simog.massload.xmlbeans.LuogoNutsType)target;
        }
    }
    
    /**
     * Returns number of "NUTS" element
     */
    public int sizeOfNUTSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUTS$2);
        }
    }
    
    /**
     * Sets array of all "NUTS" element
     */
    public void setNUTSArray(java.lang.String[] nutsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(nutsArray, NUTS$2);
        }
    }
    
    /**
     * Sets ith "NUTS" element
     */
    public void setNUTSArray(int i, java.lang.String nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUTS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(nuts);
        }
    }
    
    /**
     * Sets (as xml) array of all "NUTS" element
     */
    public void xsetNUTSArray(it.avlp.simog.massload.xmlbeans.LuogoNutsType[]nutsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(nutsArray, NUTS$2);
        }
    }
    
    /**
     * Sets (as xml) ith "NUTS" element
     */
    public void xsetNUTSArray(int i, it.avlp.simog.massload.xmlbeans.LuogoNutsType nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_element_user(NUTS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(nuts);
        }
    }
    
    /**
     * Inserts the value as the ith "NUTS" element
     */
    public void insertNUTS(int i, java.lang.String nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(NUTS$2, i);
            target.setStringValue(nuts);
        }
    }
    
    /**
     * Appends the value as the last "NUTS" element
     */
    public void addNUTS(java.lang.String nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUTS$2);
            target.setStringValue(nuts);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "NUTS" element
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType insertNewNUTS(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().insert_element_user(NUTS$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "NUTS" element
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType addNewNUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().add_element_user(NUTS$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "NUTS" element
     */
    public void removeNUTS(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUTS$2, i);
        }
    }
    
    /**
     * Gets array of all "CONTRAENTE" elements
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteTypeMod[] getCONTRAENTEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CONTRAENTE$4, targetList);
            it.avlp.simog.massload.xmlbeans.ContraenteTypeMod[] result = new it.avlp.simog.massload.xmlbeans.ContraenteTypeMod[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "CONTRAENTE" element
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteTypeMod getCONTRAENTEArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteTypeMod target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteTypeMod)get_store().find_element_user(CONTRAENTE$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "CONTRAENTE" element
     */
    public int sizeOfCONTRAENTEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONTRAENTE$4);
        }
    }
    
    /**
     * Sets array of all "CONTRAENTE" element
     */
    public void setCONTRAENTEArray(it.avlp.simog.massload.xmlbeans.ContraenteTypeMod[] contraenteArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(contraenteArray, CONTRAENTE$4);
        }
    }
    
    /**
     * Sets ith "CONTRAENTE" element
     */
    public void setCONTRAENTEArray(int i, it.avlp.simog.massload.xmlbeans.ContraenteTypeMod contraente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteTypeMod target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteTypeMod)get_store().find_element_user(CONTRAENTE$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(contraente);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CONTRAENTE" element
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteTypeMod insertNewCONTRAENTE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteTypeMod target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteTypeMod)get_store().insert_element_user(CONTRAENTE$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CONTRAENTE" element
     */
    public it.avlp.simog.massload.xmlbeans.ContraenteTypeMod addNewCONTRAENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContraenteTypeMod target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContraenteTypeMod)get_store().add_element_user(CONTRAENTE$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "CONTRAENTE" element
     */
    public void removeCONTRAENTE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONTRAENTE$4, i);
        }
    }
    
    /**
     * Gets the "CPV_PRINCIPALE" attribute
     */
    public java.lang.String getCPVPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPVPRINCIPALE$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CPV_PRINCIPALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE xgetCPVPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE)get_store().find_attribute_user(CPVPRINCIPALE$6);
            return target;
        }
    }
    
    /**
     * Sets the "CPV_PRINCIPALE" attribute
     */
    public void setCPVPRINCIPALE(java.lang.String cpvprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPVPRINCIPALE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CPVPRINCIPALE$6);
            }
            target.setStringValue(cpvprincipale);
        }
    }
    
    /**
     * Sets (as xml) the "CPV_PRINCIPALE" attribute
     */
    public void xsetCPVPRINCIPALE(it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE cpvprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE)get_store().find_attribute_user(CPVPRINCIPALE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE)get_store().add_attribute_user(CPVPRINCIPALE$6);
            }
            target.set(cpvprincipale);
        }
    }
    
    /**
     * Gets the "LUOGO_ESEC_PRINCIPALE" attribute
     */
    public java.lang.String getLUOGOESECPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOESECPRINCIPALE$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LUOGO_ESEC_PRINCIPALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE xgetLUOGOESECPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE)get_store().find_attribute_user(LUOGOESECPRINCIPALE$8);
            return target;
        }
    }
    
    /**
     * True if has "LUOGO_ESEC_PRINCIPALE" attribute
     */
    public boolean isSetLUOGOESECPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LUOGOESECPRINCIPALE$8) != null;
        }
    }
    
    /**
     * Sets the "LUOGO_ESEC_PRINCIPALE" attribute
     */
    public void setLUOGOESECPRINCIPALE(java.lang.String luogoesecprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOESECPRINCIPALE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LUOGOESECPRINCIPALE$8);
            }
            target.setStringValue(luogoesecprincipale);
        }
    }
    
    /**
     * Sets (as xml) the "LUOGO_ESEC_PRINCIPALE" attribute
     */
    public void xsetLUOGOESECPRINCIPALE(it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE luogoesecprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE)get_store().find_attribute_user(LUOGOESECPRINCIPALE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE)get_store().add_attribute_user(LUOGOESECPRINCIPALE$8);
            }
            target.set(luogoesecprincipale);
        }
    }
    
    /**
     * Unsets the "LUOGO_ESEC_PRINCIPALE" attribute
     */
    public void unsetLUOGOESECPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LUOGOESECPRINCIPALE$8);
        }
    }
    
    /**
     * Gets the "DESC_PROCUREMENT" attribute
     */
    public java.lang.String getDESCPROCUREMENT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCPROCUREMENT$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESC_PROCUREMENT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT xgetDESCPROCUREMENT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT)get_store().find_attribute_user(DESCPROCUREMENT$10);
            return target;
        }
    }
    
    /**
     * Sets the "DESC_PROCUREMENT" attribute
     */
    public void setDESCPROCUREMENT(java.lang.String descprocurement)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCPROCUREMENT$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCPROCUREMENT$10);
            }
            target.setStringValue(descprocurement);
        }
    }
    
    /**
     * Sets (as xml) the "DESC_PROCUREMENT" attribute
     */
    public void xsetDESCPROCUREMENT(it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT descprocurement)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT)get_store().find_attribute_user(DESCPROCUREMENT$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT)get_store().add_attribute_user(DESCPROCUREMENT$10);
            }
            target.set(descprocurement);
        }
    }
    
    /**
     * Gets the "DURATA_CONTRATTO_MESI" attribute
     */
    public int getDURATACONTRATTOMESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATACONTRATTOMESI$12);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "DURATA_CONTRATTO_MESI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI xgetDURATACONTRATTOMESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI)get_store().find_attribute_user(DURATACONTRATTOMESI$12);
            return target;
        }
    }
    
    /**
     * True if has "DURATA_CONTRATTO_MESI" attribute
     */
    public boolean isSetDURATACONTRATTOMESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DURATACONTRATTOMESI$12) != null;
        }
    }
    
    /**
     * Sets the "DURATA_CONTRATTO_MESI" attribute
     */
    public void setDURATACONTRATTOMESI(int duratacontrattomesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATACONTRATTOMESI$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DURATACONTRATTOMESI$12);
            }
            target.setIntValue(duratacontrattomesi);
        }
    }
    
    /**
     * Sets (as xml) the "DURATA_CONTRATTO_MESI" attribute
     */
    public void xsetDURATACONTRATTOMESI(it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI duratacontrattomesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI)get_store().find_attribute_user(DURATACONTRATTOMESI$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI)get_store().add_attribute_user(DURATACONTRATTOMESI$12);
            }
            target.set(duratacontrattomesi);
        }
    }
    
    /**
     * Unsets the "DURATA_CONTRATTO_MESI" attribute
     */
    public void unsetDURATACONTRATTOMESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DURATACONTRATTOMESI$12);
        }
    }
    
    /**
     * Gets the "DURATA_CONTRATTO_GIORNI" attribute
     */
    public int getDURATACONTRATTOGIORNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATACONTRATTOGIORNI$14);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "DURATA_CONTRATTO_GIORNI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI xgetDURATACONTRATTOGIORNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI)get_store().find_attribute_user(DURATACONTRATTOGIORNI$14);
            return target;
        }
    }
    
    /**
     * True if has "DURATA_CONTRATTO_GIORNI" attribute
     */
    public boolean isSetDURATACONTRATTOGIORNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DURATACONTRATTOGIORNI$14) != null;
        }
    }
    
    /**
     * Sets the "DURATA_CONTRATTO_GIORNI" attribute
     */
    public void setDURATACONTRATTOGIORNI(int duratacontrattogiorni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATACONTRATTOGIORNI$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DURATACONTRATTOGIORNI$14);
            }
            target.setIntValue(duratacontrattogiorni);
        }
    }
    
    /**
     * Sets (as xml) the "DURATA_CONTRATTO_GIORNI" attribute
     */
    public void xsetDURATACONTRATTOGIORNI(it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI duratacontrattogiorni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI)get_store().find_attribute_user(DURATACONTRATTOGIORNI$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI)get_store().add_attribute_user(DURATACONTRATTOGIORNI$14);
            }
            target.set(duratacontrattogiorni);
        }
    }
    
    /**
     * Unsets the "DURATA_CONTRATTO_GIORNI" attribute
     */
    public void unsetDURATACONTRATTOGIORNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DURATACONTRATTOGIORNI$14);
        }
    }
    
    /**
     * Gets the "INIZIO_CONTRATTO_LOTTO" attribute
     */
    public java.util.Calendar getINIZIOCONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INIZIOCONTRATTOLOTTO$16);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "INIZIO_CONTRATTO_LOTTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetINIZIOCONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(INIZIOCONTRATTOLOTTO$16);
            return target;
        }
    }
    
    /**
     * True if has "INIZIO_CONTRATTO_LOTTO" attribute
     */
    public boolean isSetINIZIOCONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INIZIOCONTRATTOLOTTO$16) != null;
        }
    }
    
    /**
     * Sets the "INIZIO_CONTRATTO_LOTTO" attribute
     */
    public void setINIZIOCONTRATTOLOTTO(java.util.Calendar iniziocontrattolotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INIZIOCONTRATTOLOTTO$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INIZIOCONTRATTOLOTTO$16);
            }
            target.setCalendarValue(iniziocontrattolotto);
        }
    }
    
    /**
     * Sets (as xml) the "INIZIO_CONTRATTO_LOTTO" attribute
     */
    public void xsetINIZIOCONTRATTOLOTTO(it.avlp.simog.massload.xmlbeans.DbDateType iniziocontrattolotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(INIZIOCONTRATTOLOTTO$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(INIZIOCONTRATTOLOTTO$16);
            }
            target.set(iniziocontrattolotto);
        }
    }
    
    /**
     * Unsets the "INIZIO_CONTRATTO_LOTTO" attribute
     */
    public void unsetINIZIOCONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INIZIOCONTRATTOLOTTO$16);
        }
    }
    
    /**
     * Gets the "FINE_CONTRATTO_LOTTO" attribute
     */
    public java.util.Calendar getFINECONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FINECONTRATTOLOTTO$18);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "FINE_CONTRATTO_LOTTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetFINECONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(FINECONTRATTOLOTTO$18);
            return target;
        }
    }
    
    /**
     * True if has "FINE_CONTRATTO_LOTTO" attribute
     */
    public boolean isSetFINECONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FINECONTRATTOLOTTO$18) != null;
        }
    }
    
    /**
     * Sets the "FINE_CONTRATTO_LOTTO" attribute
     */
    public void setFINECONTRATTOLOTTO(java.util.Calendar finecontrattolotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FINECONTRATTOLOTTO$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FINECONTRATTOLOTTO$18);
            }
            target.setCalendarValue(finecontrattolotto);
        }
    }
    
    /**
     * Sets (as xml) the "FINE_CONTRATTO_LOTTO" attribute
     */
    public void xsetFINECONTRATTOLOTTO(it.avlp.simog.massload.xmlbeans.DbDateType finecontrattolotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(FINECONTRATTOLOTTO$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(FINECONTRATTOLOTTO$18);
            }
            target.set(finecontrattolotto);
        }
    }
    
    /**
     * Unsets the "FINE_CONTRATTO_LOTTO" attribute
     */
    public void unsetFINECONTRATTOLOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FINECONTRATTOLOTTO$18);
        }
    }
    
    /**
     * Gets the "JUSTIFICATION" attribute
     */
    public java.lang.String getJUSTIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(JUSTIFICATION$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "JUSTIFICATION" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION xgetJUSTIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION)get_store().find_attribute_user(JUSTIFICATION$20);
            return target;
        }
    }
    
    /**
     * True if has "JUSTIFICATION" attribute
     */
    public boolean isSetJUSTIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(JUSTIFICATION$20) != null;
        }
    }
    
    /**
     * Sets the "JUSTIFICATION" attribute
     */
    public void setJUSTIFICATION(java.lang.String justification)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(JUSTIFICATION$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(JUSTIFICATION$20);
            }
            target.setStringValue(justification);
        }
    }
    
    /**
     * Sets (as xml) the "JUSTIFICATION" attribute
     */
    public void xsetJUSTIFICATION(it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION justification)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION)get_store().find_attribute_user(JUSTIFICATION$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION)get_store().add_attribute_user(JUSTIFICATION$20);
            }
            target.set(justification);
        }
    }
    
    /**
     * Unsets the "JUSTIFICATION" attribute
     */
    public void unsetJUSTIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(JUSTIFICATION$20);
        }
    }
    
    /**
     * Gets the "VAL_TOTAL" attribute
     */
    public java.math.BigDecimal getVALTOTAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTAL$22);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_TOTAL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetVALTOTAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTAL$22);
            return target;
        }
    }
    
    /**
     * Sets the "VAL_TOTAL" attribute
     */
    public void setVALTOTAL(java.math.BigDecimal valtotal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTAL$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALTOTAL$22);
            }
            target.setBigDecimalValue(valtotal);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_TOTAL" attribute
     */
    public void xsetVALTOTAL(it.avlp.simog.massload.xmlbeans.ImportoType valtotal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTAL$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(VALTOTAL$22);
            }
            target.set(valtotal);
        }
    }
    
    /**
     * Gets the "DESC_NATURE_CHANGES" attribute
     */
    public java.lang.String getDESCNATURECHANGES()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCNATURECHANGES$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESC_NATURE_CHANGES" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES xgetDESCNATURECHANGES()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES)get_store().find_attribute_user(DESCNATURECHANGES$24);
            return target;
        }
    }
    
    /**
     * Sets the "DESC_NATURE_CHANGES" attribute
     */
    public void setDESCNATURECHANGES(java.lang.String descnaturechanges)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCNATURECHANGES$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCNATURECHANGES$24);
            }
            target.setStringValue(descnaturechanges);
        }
    }
    
    /**
     * Sets (as xml) the "DESC_NATURE_CHANGES" attribute
     */
    public void xsetDESCNATURECHANGES(it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES descnaturechanges)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES)get_store().find_attribute_user(DESCNATURECHANGES$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES)get_store().add_attribute_user(DESCNATURECHANGES$24);
            }
            target.set(descnaturechanges);
        }
    }
    
    /**
     * Gets the "REASON_MODIFICATION" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ReasonModificationType.Enum getREASONMODIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REASONMODIFICATION$26);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.ReasonModificationType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "REASON_MODIFICATION" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ReasonModificationType xgetREASONMODIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReasonModificationType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReasonModificationType)get_store().find_attribute_user(REASONMODIFICATION$26);
            return target;
        }
    }
    
    /**
     * Sets the "REASON_MODIFICATION" attribute
     */
    public void setREASONMODIFICATION(it.avlp.simog.massload.xmlbeans.ReasonModificationType.Enum reasonmodification)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REASONMODIFICATION$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(REASONMODIFICATION$26);
            }
            target.setEnumValue(reasonmodification);
        }
    }
    
    /**
     * Sets (as xml) the "REASON_MODIFICATION" attribute
     */
    public void xsetREASONMODIFICATION(it.avlp.simog.massload.xmlbeans.ReasonModificationType reasonmodification)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReasonModificationType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReasonModificationType)get_store().find_attribute_user(REASONMODIFICATION$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ReasonModificationType)get_store().add_attribute_user(REASONMODIFICATION$26);
            }
            target.set(reasonmodification);
        }
    }
    
    /**
     * Gets the "DESC_REASON_MODIFICATION" attribute
     */
    public java.lang.String getDESCREASONMODIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCREASONMODIFICATION$28);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESC_REASON_MODIFICATION" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION xgetDESCREASONMODIFICATION()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION)get_store().find_attribute_user(DESCREASONMODIFICATION$28);
            return target;
        }
    }
    
    /**
     * Sets the "DESC_REASON_MODIFICATION" attribute
     */
    public void setDESCREASONMODIFICATION(java.lang.String descreasonmodification)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCREASONMODIFICATION$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCREASONMODIFICATION$28);
            }
            target.setStringValue(descreasonmodification);
        }
    }
    
    /**
     * Sets (as xml) the "DESC_REASON_MODIFICATION" attribute
     */
    public void xsetDESCREASONMODIFICATION(it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION descreasonmodification)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION)get_store().find_attribute_user(DESCREASONMODIFICATION$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION)get_store().add_attribute_user(DESCREASONMODIFICATION$28);
            }
            target.set(descreasonmodification);
        }
    }
    
    /**
     * Gets the "VAL_TOTAL_BEFORE" attribute
     */
    public java.math.BigDecimal getVALTOTALBEFORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTALBEFORE$30);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_TOTAL_BEFORE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetVALTOTALBEFORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTALBEFORE$30);
            return target;
        }
    }
    
    /**
     * Sets the "VAL_TOTAL_BEFORE" attribute
     */
    public void setVALTOTALBEFORE(java.math.BigDecimal valtotalbefore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTALBEFORE$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALTOTALBEFORE$30);
            }
            target.setBigDecimalValue(valtotalbefore);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_TOTAL_BEFORE" attribute
     */
    public void xsetVALTOTALBEFORE(it.avlp.simog.massload.xmlbeans.ImportoType valtotalbefore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTALBEFORE$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(VALTOTALBEFORE$30);
            }
            target.set(valtotalbefore);
        }
    }
    
    /**
     * Gets the "VAL_TOTAL_AFTER" attribute
     */
    public java.math.BigDecimal getVALTOTALAFTER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTALAFTER$32);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_TOTAL_AFTER" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetVALTOTALAFTER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTALAFTER$32);
            return target;
        }
    }
    
    /**
     * Sets the "VAL_TOTAL_AFTER" attribute
     */
    public void setVALTOTALAFTER(java.math.BigDecimal valtotalafter)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALTOTALAFTER$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALTOTALAFTER$32);
            }
            target.setBigDecimalValue(valtotalafter);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_TOTAL_AFTER" attribute
     */
    public void xsetVALTOTALAFTER(it.avlp.simog.massload.xmlbeans.ImportoType valtotalafter)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(VALTOTALAFTER$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(VALTOTALAFTER$32);
            }
            target.set(valtotalafter);
        }
    }
    /**
     * An XML CPV_PRINCIPALE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$CPVPRINCIPALE.
     */
    public static class CPVPRINCIPALEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.CPVPRINCIPALE
    {
        
        public CPVPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CPVPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML LUOGO_ESEC_PRINCIPALE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$LUOGOESECPRINCIPALE.
     */
    public static class LUOGOESECPRINCIPALEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.LUOGOESECPRINCIPALE
    {
        
        public LUOGOESECPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected LUOGOESECPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DESC_PROCUREMENT(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$DESCPROCUREMENT.
     */
    public static class DESCPROCUREMENTImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.DESCPROCUREMENT
    {
        
        public DESCPROCUREMENTImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DESCPROCUREMENTImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DURATA_CONTRATTO_MESI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$DURATACONTRATTOMESI.
     */
    public static class DURATACONTRATTOMESIImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOMESI
    {
        
        public DURATACONTRATTOMESIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DURATACONTRATTOMESIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DURATA_CONTRATTO_GIORNI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$DURATACONTRATTOGIORNI.
     */
    public static class DURATACONTRATTOGIORNIImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.DURATACONTRATTOGIORNI
    {
        
        public DURATACONTRATTOGIORNIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DURATACONTRATTOGIORNIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML JUSTIFICATION(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$JUSTIFICATION.
     */
    public static class JUSTIFICATIONImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.JUSTIFICATION
    {
        
        public JUSTIFICATIONImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected JUSTIFICATIONImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DESC_NATURE_CHANGES(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$DESCNATURECHANGES.
     */
    public static class DESCNATURECHANGESImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.DESCNATURECHANGES
    {
        
        public DESCNATURECHANGESImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DESCNATURECHANGESImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DESC_REASON_MODIFICATION(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaType$DESCREASONMODIFICATION.
     */
    public static class DESCREASONMODIFICATIONImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaType.DESCREASONMODIFICATION
    {
        
        public DESCREASONMODIFICATIONImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DESCREASONMODIFICATIONImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
