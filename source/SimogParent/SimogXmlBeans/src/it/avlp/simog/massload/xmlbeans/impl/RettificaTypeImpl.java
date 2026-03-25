/*
 * XML Type:  RettificaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RettificaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RettificaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RettificaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RettificaType
{
    
    public RettificaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RETTIFICACPVSEC$0 = 
        new javax.xml.namespace.QName("", "RETTIFICA_CPV_SEC");
    private static final javax.xml.namespace.QName SECTIONNUMBER$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SECTION_NUMBER");
    private static final javax.xml.namespace.QName CIGRETTIFICA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG_RETTIFICA");
    private static final javax.xml.namespace.QName SECTIONTOMODIFY$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SECTION_TO_MODIFY");
    private static final javax.xml.namespace.QName OLDVALUETEXT$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OLD_VALUE_TEXT");
    private static final javax.xml.namespace.QName NEWVALUETEXT$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NEW_VALUE_TEXT");
    private static final javax.xml.namespace.QName OLDMAINCPV$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OLD_MAIN_CPV");
    private static final javax.xml.namespace.QName NEWMAINCPV$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NEW_MAIN_CPV");
    private static final javax.xml.namespace.QName OLDVALUEDATE$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OLD_VALUE_DATE");
    private static final javax.xml.namespace.QName OLDVALUETIME$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OLD_VALUE_TIME");
    private static final javax.xml.namespace.QName NEWVALUEDATE$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NEW_VALUE_DATE");
    private static final javax.xml.namespace.QName NEWVALUETIME$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NEW_VALUE_TIME");
    
    
    /**
     * Gets array of all "RETTIFICA_CPV_SEC" elements
     */
    public it.avlp.simog.massload.xmlbeans.RettificaCpvSecType[] getRETTIFICACPVSECArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RETTIFICACPVSEC$0, targetList);
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType[] result = new it.avlp.simog.massload.xmlbeans.RettificaCpvSecType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "RETTIFICA_CPV_SEC" element
     */
    public it.avlp.simog.massload.xmlbeans.RettificaCpvSecType getRETTIFICACPVSECArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType)get_store().find_element_user(RETTIFICACPVSEC$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "RETTIFICA_CPV_SEC" element
     */
    public int sizeOfRETTIFICACPVSECArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RETTIFICACPVSEC$0);
        }
    }
    
    /**
     * Sets array of all "RETTIFICA_CPV_SEC" element
     */
    public void setRETTIFICACPVSECArray(it.avlp.simog.massload.xmlbeans.RettificaCpvSecType[] rettificacpvsecArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(rettificacpvsecArray, RETTIFICACPVSEC$0);
        }
    }
    
    /**
     * Sets ith "RETTIFICA_CPV_SEC" element
     */
    public void setRETTIFICACPVSECArray(int i, it.avlp.simog.massload.xmlbeans.RettificaCpvSecType rettificacpvsec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType)get_store().find_element_user(RETTIFICACPVSEC$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(rettificacpvsec);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "RETTIFICA_CPV_SEC" element
     */
    public it.avlp.simog.massload.xmlbeans.RettificaCpvSecType insertNewRETTIFICACPVSEC(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType)get_store().insert_element_user(RETTIFICACPVSEC$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "RETTIFICA_CPV_SEC" element
     */
    public it.avlp.simog.massload.xmlbeans.RettificaCpvSecType addNewRETTIFICACPVSEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaCpvSecType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaCpvSecType)get_store().add_element_user(RETTIFICACPVSEC$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "RETTIFICA_CPV_SEC" element
     */
    public void removeRETTIFICACPVSEC(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RETTIFICACPVSEC$0, i);
        }
    }
    
    /**
     * Gets the "SECTION_NUMBER" attribute
     */
    public java.lang.String getSECTIONNUMBER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SECTIONNUMBER$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SECTION_NUMBER" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER xgetSECTIONNUMBER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER)get_store().find_attribute_user(SECTIONNUMBER$2);
            return target;
        }
    }
    
    /**
     * Sets the "SECTION_NUMBER" attribute
     */
    public void setSECTIONNUMBER(java.lang.String sectionnumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SECTIONNUMBER$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SECTIONNUMBER$2);
            }
            target.setStringValue(sectionnumber);
        }
    }
    
    /**
     * Sets (as xml) the "SECTION_NUMBER" attribute
     */
    public void xsetSECTIONNUMBER(it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER sectionnumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER)get_store().find_attribute_user(SECTIONNUMBER$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER)get_store().add_attribute_user(SECTIONNUMBER$2);
            }
            target.set(sectionnumber);
        }
    }
    
    /**
     * Gets the "CIG_RETTIFICA" attribute
     */
    public java.lang.String getCIGRETTIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGRETTIFICA$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG_RETTIFICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIGRETTIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGRETTIFICA$4);
            return target;
        }
    }
    
    /**
     * True if has "CIG_RETTIFICA" attribute
     */
    public boolean isSetCIGRETTIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CIGRETTIFICA$4) != null;
        }
    }
    
    /**
     * Sets the "CIG_RETTIFICA" attribute
     */
    public void setCIGRETTIFICA(java.lang.String cigrettifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGRETTIFICA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIGRETTIFICA$4);
            }
            target.setStringValue(cigrettifica);
        }
    }
    
    /**
     * Sets (as xml) the "CIG_RETTIFICA" attribute
     */
    public void xsetCIGRETTIFICA(it.avlp.simog.massload.xmlbeans.CigType cigrettifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGRETTIFICA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIGRETTIFICA$4);
            }
            target.set(cigrettifica);
        }
    }
    
    /**
     * Unsets the "CIG_RETTIFICA" attribute
     */
    public void unsetCIGRETTIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CIGRETTIFICA$4);
        }
    }
    
    /**
     * Gets the "SECTION_TO_MODIFY" attribute
     */
    public java.lang.String getSECTIONTOMODIFY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SECTIONTOMODIFY$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SECTION_TO_MODIFY" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY xgetSECTIONTOMODIFY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY)get_store().find_attribute_user(SECTIONTOMODIFY$6);
            return target;
        }
    }
    
    /**
     * True if has "SECTION_TO_MODIFY" attribute
     */
    public boolean isSetSECTIONTOMODIFY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SECTIONTOMODIFY$6) != null;
        }
    }
    
    /**
     * Sets the "SECTION_TO_MODIFY" attribute
     */
    public void setSECTIONTOMODIFY(java.lang.String sectiontomodify)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SECTIONTOMODIFY$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SECTIONTOMODIFY$6);
            }
            target.setStringValue(sectiontomodify);
        }
    }
    
    /**
     * Sets (as xml) the "SECTION_TO_MODIFY" attribute
     */
    public void xsetSECTIONTOMODIFY(it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY sectiontomodify)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY)get_store().find_attribute_user(SECTIONTOMODIFY$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY)get_store().add_attribute_user(SECTIONTOMODIFY$6);
            }
            target.set(sectiontomodify);
        }
    }
    
    /**
     * Unsets the "SECTION_TO_MODIFY" attribute
     */
    public void unsetSECTIONTOMODIFY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SECTIONTOMODIFY$6);
        }
    }
    
    /**
     * Gets the "OLD_VALUE_TEXT" attribute
     */
    public java.lang.String getOLDVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDVALUETEXT$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OLD_VALUE_TEXT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT xgetOLDVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT)get_store().find_attribute_user(OLDVALUETEXT$8);
            return target;
        }
    }
    
    /**
     * True if has "OLD_VALUE_TEXT" attribute
     */
    public boolean isSetOLDVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OLDVALUETEXT$8) != null;
        }
    }
    
    /**
     * Sets the "OLD_VALUE_TEXT" attribute
     */
    public void setOLDVALUETEXT(java.lang.String oldvaluetext)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDVALUETEXT$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OLDVALUETEXT$8);
            }
            target.setStringValue(oldvaluetext);
        }
    }
    
    /**
     * Sets (as xml) the "OLD_VALUE_TEXT" attribute
     */
    public void xsetOLDVALUETEXT(it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT oldvaluetext)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT)get_store().find_attribute_user(OLDVALUETEXT$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT)get_store().add_attribute_user(OLDVALUETEXT$8);
            }
            target.set(oldvaluetext);
        }
    }
    
    /**
     * Unsets the "OLD_VALUE_TEXT" attribute
     */
    public void unsetOLDVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OLDVALUETEXT$8);
        }
    }
    
    /**
     * Gets the "NEW_VALUE_TEXT" attribute
     */
    public java.lang.String getNEWVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWVALUETEXT$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NEW_VALUE_TEXT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT xgetNEWVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT)get_store().find_attribute_user(NEWVALUETEXT$10);
            return target;
        }
    }
    
    /**
     * True if has "NEW_VALUE_TEXT" attribute
     */
    public boolean isSetNEWVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NEWVALUETEXT$10) != null;
        }
    }
    
    /**
     * Sets the "NEW_VALUE_TEXT" attribute
     */
    public void setNEWVALUETEXT(java.lang.String newvaluetext)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWVALUETEXT$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NEWVALUETEXT$10);
            }
            target.setStringValue(newvaluetext);
        }
    }
    
    /**
     * Sets (as xml) the "NEW_VALUE_TEXT" attribute
     */
    public void xsetNEWVALUETEXT(it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT newvaluetext)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT)get_store().find_attribute_user(NEWVALUETEXT$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT)get_store().add_attribute_user(NEWVALUETEXT$10);
            }
            target.set(newvaluetext);
        }
    }
    
    /**
     * Unsets the "NEW_VALUE_TEXT" attribute
     */
    public void unsetNEWVALUETEXT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NEWVALUETEXT$10);
        }
    }
    
    /**
     * Gets the "OLD_MAIN_CPV" attribute
     */
    public java.lang.String getOLDMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDMAINCPV$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OLD_MAIN_CPV" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV xgetOLDMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV)get_store().find_attribute_user(OLDMAINCPV$12);
            return target;
        }
    }
    
    /**
     * True if has "OLD_MAIN_CPV" attribute
     */
    public boolean isSetOLDMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OLDMAINCPV$12) != null;
        }
    }
    
    /**
     * Sets the "OLD_MAIN_CPV" attribute
     */
    public void setOLDMAINCPV(java.lang.String oldmaincpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDMAINCPV$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OLDMAINCPV$12);
            }
            target.setStringValue(oldmaincpv);
        }
    }
    
    /**
     * Sets (as xml) the "OLD_MAIN_CPV" attribute
     */
    public void xsetOLDMAINCPV(it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV oldmaincpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV)get_store().find_attribute_user(OLDMAINCPV$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV)get_store().add_attribute_user(OLDMAINCPV$12);
            }
            target.set(oldmaincpv);
        }
    }
    
    /**
     * Unsets the "OLD_MAIN_CPV" attribute
     */
    public void unsetOLDMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OLDMAINCPV$12);
        }
    }
    
    /**
     * Gets the "NEW_MAIN_CPV" attribute
     */
    public java.lang.String getNEWMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWMAINCPV$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NEW_MAIN_CPV" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV xgetNEWMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV)get_store().find_attribute_user(NEWMAINCPV$14);
            return target;
        }
    }
    
    /**
     * True if has "NEW_MAIN_CPV" attribute
     */
    public boolean isSetNEWMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NEWMAINCPV$14) != null;
        }
    }
    
    /**
     * Sets the "NEW_MAIN_CPV" attribute
     */
    public void setNEWMAINCPV(java.lang.String newmaincpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWMAINCPV$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NEWMAINCPV$14);
            }
            target.setStringValue(newmaincpv);
        }
    }
    
    /**
     * Sets (as xml) the "NEW_MAIN_CPV" attribute
     */
    public void xsetNEWMAINCPV(it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV newmaincpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV)get_store().find_attribute_user(NEWMAINCPV$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV)get_store().add_attribute_user(NEWMAINCPV$14);
            }
            target.set(newmaincpv);
        }
    }
    
    /**
     * Unsets the "NEW_MAIN_CPV" attribute
     */
    public void unsetNEWMAINCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NEWMAINCPV$14);
        }
    }
    
    /**
     * Gets the "OLD_VALUE_DATE" attribute
     */
    public java.util.Calendar getOLDVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDVALUEDATE$16);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "OLD_VALUE_DATE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetOLDVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(OLDVALUEDATE$16);
            return target;
        }
    }
    
    /**
     * True if has "OLD_VALUE_DATE" attribute
     */
    public boolean isSetOLDVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OLDVALUEDATE$16) != null;
        }
    }
    
    /**
     * Sets the "OLD_VALUE_DATE" attribute
     */
    public void setOLDVALUEDATE(java.util.Calendar oldvaluedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDVALUEDATE$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OLDVALUEDATE$16);
            }
            target.setCalendarValue(oldvaluedate);
        }
    }
    
    /**
     * Sets (as xml) the "OLD_VALUE_DATE" attribute
     */
    public void xsetOLDVALUEDATE(it.avlp.simog.massload.xmlbeans.DbDateType oldvaluedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(OLDVALUEDATE$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(OLDVALUEDATE$16);
            }
            target.set(oldvaluedate);
        }
    }
    
    /**
     * Unsets the "OLD_VALUE_DATE" attribute
     */
    public void unsetOLDVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OLDVALUEDATE$16);
        }
    }
    
    /**
     * Gets the "OLD_VALUE_TIME" attribute
     */
    public java.lang.String getOLDVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDVALUETIME$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OLD_VALUE_TIME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.Time xgetOLDVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Time target = null;
            target = (it.avlp.simog.massload.xmlbeans.Time)get_store().find_attribute_user(OLDVALUETIME$18);
            return target;
        }
    }
    
    /**
     * True if has "OLD_VALUE_TIME" attribute
     */
    public boolean isSetOLDVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OLDVALUETIME$18) != null;
        }
    }
    
    /**
     * Sets the "OLD_VALUE_TIME" attribute
     */
    public void setOLDVALUETIME(java.lang.String oldvaluetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLDVALUETIME$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OLDVALUETIME$18);
            }
            target.setStringValue(oldvaluetime);
        }
    }
    
    /**
     * Sets (as xml) the "OLD_VALUE_TIME" attribute
     */
    public void xsetOLDVALUETIME(it.avlp.simog.massload.xmlbeans.Time oldvaluetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Time target = null;
            target = (it.avlp.simog.massload.xmlbeans.Time)get_store().find_attribute_user(OLDVALUETIME$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Time)get_store().add_attribute_user(OLDVALUETIME$18);
            }
            target.set(oldvaluetime);
        }
    }
    
    /**
     * Unsets the "OLD_VALUE_TIME" attribute
     */
    public void unsetOLDVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OLDVALUETIME$18);
        }
    }
    
    /**
     * Gets the "NEW_VALUE_DATE" attribute
     */
    public java.util.Calendar getNEWVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWVALUEDATE$20);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "NEW_VALUE_DATE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetNEWVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(NEWVALUEDATE$20);
            return target;
        }
    }
    
    /**
     * True if has "NEW_VALUE_DATE" attribute
     */
    public boolean isSetNEWVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NEWVALUEDATE$20) != null;
        }
    }
    
    /**
     * Sets the "NEW_VALUE_DATE" attribute
     */
    public void setNEWVALUEDATE(java.util.Calendar newvaluedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWVALUEDATE$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NEWVALUEDATE$20);
            }
            target.setCalendarValue(newvaluedate);
        }
    }
    
    /**
     * Sets (as xml) the "NEW_VALUE_DATE" attribute
     */
    public void xsetNEWVALUEDATE(it.avlp.simog.massload.xmlbeans.DbDateType newvaluedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(NEWVALUEDATE$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(NEWVALUEDATE$20);
            }
            target.set(newvaluedate);
        }
    }
    
    /**
     * Unsets the "NEW_VALUE_DATE" attribute
     */
    public void unsetNEWVALUEDATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NEWVALUEDATE$20);
        }
    }
    
    /**
     * Gets the "NEW_VALUE_TIME" attribute
     */
    public java.lang.String getNEWVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWVALUETIME$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NEW_VALUE_TIME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.Time xgetNEWVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Time target = null;
            target = (it.avlp.simog.massload.xmlbeans.Time)get_store().find_attribute_user(NEWVALUETIME$22);
            return target;
        }
    }
    
    /**
     * True if has "NEW_VALUE_TIME" attribute
     */
    public boolean isSetNEWVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NEWVALUETIME$22) != null;
        }
    }
    
    /**
     * Sets the "NEW_VALUE_TIME" attribute
     */
    public void setNEWVALUETIME(java.lang.String newvaluetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NEWVALUETIME$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NEWVALUETIME$22);
            }
            target.setStringValue(newvaluetime);
        }
    }
    
    /**
     * Sets (as xml) the "NEW_VALUE_TIME" attribute
     */
    public void xsetNEWVALUETIME(it.avlp.simog.massload.xmlbeans.Time newvaluetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Time target = null;
            target = (it.avlp.simog.massload.xmlbeans.Time)get_store().find_attribute_user(NEWVALUETIME$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Time)get_store().add_attribute_user(NEWVALUETIME$22);
            }
            target.set(newvaluetime);
        }
    }
    
    /**
     * Unsets the "NEW_VALUE_TIME" attribute
     */
    public void unsetNEWVALUETIME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NEWVALUETIME$22);
        }
    }
    /**
     * An XML SECTION_NUMBER(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$SECTIONNUMBER.
     */
    public static class SECTIONNUMBERImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONNUMBER
    {
        
        public SECTIONNUMBERImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected SECTIONNUMBERImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML SECTION_TO_MODIFY(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$SECTIONTOMODIFY.
     */
    public static class SECTIONTOMODIFYImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaType.SECTIONTOMODIFY
    {
        
        public SECTIONTOMODIFYImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected SECTIONTOMODIFYImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML OLD_VALUE_TEXT(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$OLDVALUETEXT.
     */
    public static class OLDVALUETEXTImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaType.OLDVALUETEXT
    {
        
        public OLDVALUETEXTImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected OLDVALUETEXTImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NEW_VALUE_TEXT(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$NEWVALUETEXT.
     */
    public static class NEWVALUETEXTImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaType.NEWVALUETEXT
    {
        
        public NEWVALUETEXTImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NEWVALUETEXTImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML OLD_MAIN_CPV(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$OLDMAINCPV.
     */
    public static class OLDMAINCPVImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaType.OLDMAINCPV
    {
        
        public OLDMAINCPVImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected OLDMAINCPVImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NEW_MAIN_CPV(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RettificaType$NEWMAINCPV.
     */
    public static class NEWMAINCPVImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RettificaType.NEWMAINCPV
    {
        
        public NEWMAINCPVImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NEWMAINCPVImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
