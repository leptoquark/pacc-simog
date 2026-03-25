/*
 * XML Type:  CondizioniPartecipazioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML CondizioniPartecipazioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CondizioniPartecipazioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType
{
    
    public CondizioniPartecipazioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ELENCOCONDIZIONI$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ELENCO_CONDIZIONI");
    private static final javax.xml.namespace.QName CRITERIECONOMICI$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CRITERI_ECONOMICI");
    private static final javax.xml.namespace.QName ELENCOCRITERIECONOMICI$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ELENCO_CRITERI_ECONOMICI");
    private static final javax.xml.namespace.QName LIVELLICRITERIECONOMICI$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LIVELLI_CRITERI_ECONOMICI");
    private static final javax.xml.namespace.QName CRITERITECNICI$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CRITERI_TECNICI");
    private static final javax.xml.namespace.QName ELENCOCRITERITECNICI$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ELENCO_CRITERI_TECNICI");
    private static final javax.xml.namespace.QName LIVELLICRITERITECNICI$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LIVELLI_CRITERI_TECNICI");
    private static final javax.xml.namespace.QName INTEGRAZIONEDISABILI$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INTEGRAZIONE_DISABILI");
    private static final javax.xml.namespace.QName LAVORIPROTETTI$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LAVORI_PROTETTI");
    private static final javax.xml.namespace.QName FLAGPROFESSIONESERVIZI$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_PROFESSIONE_SERVIZI");
    private static final javax.xml.namespace.QName PROFESSIONESERVIZI$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PROFESSIONE_SERVIZI");
    private static final javax.xml.namespace.QName CONDIZIONIESECUZIONECONTRATTO$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CONDIZIONI_ESECUZIONE_CONTRATTO");
    private static final javax.xml.namespace.QName OBBLIGONOMIESECUZIONECONTRATTO$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OBBLIGO_NOMI_ESECUZIONE_CONTRATTO");
    
    
    /**
     * Gets the "ELENCO_CONDIZIONI" attribute
     */
    public java.lang.String getELENCOCONDIZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELENCOCONDIZIONI$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ELENCO_CONDIZIONI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI xgetELENCOCONDIZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI)get_store().find_attribute_user(ELENCOCONDIZIONI$0);
            return target;
        }
    }
    
    /**
     * True if has "ELENCO_CONDIZIONI" attribute
     */
    public boolean isSetELENCOCONDIZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ELENCOCONDIZIONI$0) != null;
        }
    }
    
    /**
     * Sets the "ELENCO_CONDIZIONI" attribute
     */
    public void setELENCOCONDIZIONI(java.lang.String elencocondizioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELENCOCONDIZIONI$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ELENCOCONDIZIONI$0);
            }
            target.setStringValue(elencocondizioni);
        }
    }
    
    /**
     * Sets (as xml) the "ELENCO_CONDIZIONI" attribute
     */
    public void xsetELENCOCONDIZIONI(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI elencocondizioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI)get_store().find_attribute_user(ELENCOCONDIZIONI$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI)get_store().add_attribute_user(ELENCOCONDIZIONI$0);
            }
            target.set(elencocondizioni);
        }
    }
    
    /**
     * Unsets the "ELENCO_CONDIZIONI" attribute
     */
    public void unsetELENCOCONDIZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ELENCOCONDIZIONI$0);
        }
    }
    
    /**
     * Gets the "CRITERI_ECONOMICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getCRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERIECONOMICI$2);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CRITERI_ECONOMICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetCRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CRITERIECONOMICI$2);
            return target;
        }
    }
    
    /**
     * Sets the "CRITERI_ECONOMICI" attribute
     */
    public void setCRITERIECONOMICI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum criterieconomici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERIECONOMICI$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CRITERIECONOMICI$2);
            }
            target.setEnumValue(criterieconomici);
        }
    }
    
    /**
     * Sets (as xml) the "CRITERI_ECONOMICI" attribute
     */
    public void xsetCRITERIECONOMICI(it.avlp.simog.massload.xmlbeans.FlagSNType criterieconomici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CRITERIECONOMICI$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(CRITERIECONOMICI$2);
            }
            target.set(criterieconomici);
        }
    }
    
    /**
     * Gets the "ELENCO_CRITERI_ECONOMICI" attribute
     */
    public java.lang.String getELENCOCRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELENCOCRITERIECONOMICI$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ELENCO_CRITERI_ECONOMICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI xgetELENCOCRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI)get_store().find_attribute_user(ELENCOCRITERIECONOMICI$4);
            return target;
        }
    }
    
    /**
     * True if has "ELENCO_CRITERI_ECONOMICI" attribute
     */
    public boolean isSetELENCOCRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ELENCOCRITERIECONOMICI$4) != null;
        }
    }
    
    /**
     * Sets the "ELENCO_CRITERI_ECONOMICI" attribute
     */
    public void setELENCOCRITERIECONOMICI(java.lang.String elencocriterieconomici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELENCOCRITERIECONOMICI$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ELENCOCRITERIECONOMICI$4);
            }
            target.setStringValue(elencocriterieconomici);
        }
    }
    
    /**
     * Sets (as xml) the "ELENCO_CRITERI_ECONOMICI" attribute
     */
    public void xsetELENCOCRITERIECONOMICI(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI elencocriterieconomici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI)get_store().find_attribute_user(ELENCOCRITERIECONOMICI$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI)get_store().add_attribute_user(ELENCOCRITERIECONOMICI$4);
            }
            target.set(elencocriterieconomici);
        }
    }
    
    /**
     * Unsets the "ELENCO_CRITERI_ECONOMICI" attribute
     */
    public void unsetELENCOCRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ELENCOCRITERIECONOMICI$4);
        }
    }
    
    /**
     * Gets the "LIVELLI_CRITERI_ECONOMICI" attribute
     */
    public java.lang.String getLIVELLICRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIVELLICRITERIECONOMICI$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LIVELLI_CRITERI_ECONOMICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI xgetLIVELLICRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI)get_store().find_attribute_user(LIVELLICRITERIECONOMICI$6);
            return target;
        }
    }
    
    /**
     * True if has "LIVELLI_CRITERI_ECONOMICI" attribute
     */
    public boolean isSetLIVELLICRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LIVELLICRITERIECONOMICI$6) != null;
        }
    }
    
    /**
     * Sets the "LIVELLI_CRITERI_ECONOMICI" attribute
     */
    public void setLIVELLICRITERIECONOMICI(java.lang.String livellicriterieconomici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIVELLICRITERIECONOMICI$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LIVELLICRITERIECONOMICI$6);
            }
            target.setStringValue(livellicriterieconomici);
        }
    }
    
    /**
     * Sets (as xml) the "LIVELLI_CRITERI_ECONOMICI" attribute
     */
    public void xsetLIVELLICRITERIECONOMICI(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI livellicriterieconomici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI)get_store().find_attribute_user(LIVELLICRITERIECONOMICI$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI)get_store().add_attribute_user(LIVELLICRITERIECONOMICI$6);
            }
            target.set(livellicriterieconomici);
        }
    }
    
    /**
     * Unsets the "LIVELLI_CRITERI_ECONOMICI" attribute
     */
    public void unsetLIVELLICRITERIECONOMICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LIVELLICRITERIECONOMICI$6);
        }
    }
    
    /**
     * Gets the "CRITERI_TECNICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getCRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERITECNICI$8);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CRITERI_TECNICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetCRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CRITERITECNICI$8);
            return target;
        }
    }
    
    /**
     * Sets the "CRITERI_TECNICI" attribute
     */
    public void setCRITERITECNICI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum criteritecnici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CRITERITECNICI$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CRITERITECNICI$8);
            }
            target.setEnumValue(criteritecnici);
        }
    }
    
    /**
     * Sets (as xml) the "CRITERI_TECNICI" attribute
     */
    public void xsetCRITERITECNICI(it.avlp.simog.massload.xmlbeans.FlagSNType criteritecnici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CRITERITECNICI$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(CRITERITECNICI$8);
            }
            target.set(criteritecnici);
        }
    }
    
    /**
     * Gets the "ELENCO_CRITERI_TECNICI" attribute
     */
    public java.lang.String getELENCOCRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELENCOCRITERITECNICI$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ELENCO_CRITERI_TECNICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI xgetELENCOCRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI)get_store().find_attribute_user(ELENCOCRITERITECNICI$10);
            return target;
        }
    }
    
    /**
     * True if has "ELENCO_CRITERI_TECNICI" attribute
     */
    public boolean isSetELENCOCRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ELENCOCRITERITECNICI$10) != null;
        }
    }
    
    /**
     * Sets the "ELENCO_CRITERI_TECNICI" attribute
     */
    public void setELENCOCRITERITECNICI(java.lang.String elencocriteritecnici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELENCOCRITERITECNICI$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ELENCOCRITERITECNICI$10);
            }
            target.setStringValue(elencocriteritecnici);
        }
    }
    
    /**
     * Sets (as xml) the "ELENCO_CRITERI_TECNICI" attribute
     */
    public void xsetELENCOCRITERITECNICI(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI elencocriteritecnici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI)get_store().find_attribute_user(ELENCOCRITERITECNICI$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI)get_store().add_attribute_user(ELENCOCRITERITECNICI$10);
            }
            target.set(elencocriteritecnici);
        }
    }
    
    /**
     * Unsets the "ELENCO_CRITERI_TECNICI" attribute
     */
    public void unsetELENCOCRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ELENCOCRITERITECNICI$10);
        }
    }
    
    /**
     * Gets the "LIVELLI_CRITERI_TECNICI" attribute
     */
    public java.lang.String getLIVELLICRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIVELLICRITERITECNICI$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LIVELLI_CRITERI_TECNICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI xgetLIVELLICRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI)get_store().find_attribute_user(LIVELLICRITERITECNICI$12);
            return target;
        }
    }
    
    /**
     * True if has "LIVELLI_CRITERI_TECNICI" attribute
     */
    public boolean isSetLIVELLICRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LIVELLICRITERITECNICI$12) != null;
        }
    }
    
    /**
     * Sets the "LIVELLI_CRITERI_TECNICI" attribute
     */
    public void setLIVELLICRITERITECNICI(java.lang.String livellicriteritecnici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIVELLICRITERITECNICI$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LIVELLICRITERITECNICI$12);
            }
            target.setStringValue(livellicriteritecnici);
        }
    }
    
    /**
     * Sets (as xml) the "LIVELLI_CRITERI_TECNICI" attribute
     */
    public void xsetLIVELLICRITERITECNICI(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI livellicriteritecnici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI)get_store().find_attribute_user(LIVELLICRITERITECNICI$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI)get_store().add_attribute_user(LIVELLICRITERITECNICI$12);
            }
            target.set(livellicriteritecnici);
        }
    }
    
    /**
     * Unsets the "LIVELLI_CRITERI_TECNICI" attribute
     */
    public void unsetLIVELLICRITERITECNICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LIVELLICRITERITECNICI$12);
        }
    }
    
    /**
     * Gets the "INTEGRAZIONE_DISABILI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getINTEGRAZIONEDISABILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INTEGRAZIONEDISABILI$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "INTEGRAZIONE_DISABILI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetINTEGRAZIONEDISABILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(INTEGRAZIONEDISABILI$14);
            return target;
        }
    }
    
    /**
     * True if has "INTEGRAZIONE_DISABILI" attribute
     */
    public boolean isSetINTEGRAZIONEDISABILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INTEGRAZIONEDISABILI$14) != null;
        }
    }
    
    /**
     * Sets the "INTEGRAZIONE_DISABILI" attribute
     */
    public void setINTEGRAZIONEDISABILI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum integrazionedisabili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INTEGRAZIONEDISABILI$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INTEGRAZIONEDISABILI$14);
            }
            target.setEnumValue(integrazionedisabili);
        }
    }
    
    /**
     * Sets (as xml) the "INTEGRAZIONE_DISABILI" attribute
     */
    public void xsetINTEGRAZIONEDISABILI(it.avlp.simog.massload.xmlbeans.FlagSNType integrazionedisabili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(INTEGRAZIONEDISABILI$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(INTEGRAZIONEDISABILI$14);
            }
            target.set(integrazionedisabili);
        }
    }
    
    /**
     * Unsets the "INTEGRAZIONE_DISABILI" attribute
     */
    public void unsetINTEGRAZIONEDISABILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INTEGRAZIONEDISABILI$14);
        }
    }
    
    /**
     * Gets the "LAVORI_PROTETTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getLAVORIPROTETTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LAVORIPROTETTI$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "LAVORI_PROTETTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetLAVORIPROTETTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(LAVORIPROTETTI$16);
            return target;
        }
    }
    
    /**
     * True if has "LAVORI_PROTETTI" attribute
     */
    public boolean isSetLAVORIPROTETTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LAVORIPROTETTI$16) != null;
        }
    }
    
    /**
     * Sets the "LAVORI_PROTETTI" attribute
     */
    public void setLAVORIPROTETTI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum lavoriprotetti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LAVORIPROTETTI$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LAVORIPROTETTI$16);
            }
            target.setEnumValue(lavoriprotetti);
        }
    }
    
    /**
     * Sets (as xml) the "LAVORI_PROTETTI" attribute
     */
    public void xsetLAVORIPROTETTI(it.avlp.simog.massload.xmlbeans.FlagSNType lavoriprotetti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(LAVORIPROTETTI$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(LAVORIPROTETTI$16);
            }
            target.set(lavoriprotetti);
        }
    }
    
    /**
     * Unsets the "LAVORI_PROTETTI" attribute
     */
    public void unsetLAVORIPROTETTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LAVORIPROTETTI$16);
        }
    }
    
    /**
     * Gets the "FLAG_PROFESSIONE_SERVIZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPROFESSIONESERVIZI$18);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_PROFESSIONE_SERVIZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGPROFESSIONESERVIZI$18);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_PROFESSIONE_SERVIZI" attribute
     */
    public boolean isSetFLAGPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGPROFESSIONESERVIZI$18) != null;
        }
    }
    
    /**
     * Sets the "FLAG_PROFESSIONE_SERVIZI" attribute
     */
    public void setFLAGPROFESSIONESERVIZI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagprofessioneservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPROFESSIONESERVIZI$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGPROFESSIONESERVIZI$18);
            }
            target.setEnumValue(flagprofessioneservizi);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_PROFESSIONE_SERVIZI" attribute
     */
    public void xsetFLAGPROFESSIONESERVIZI(it.avlp.simog.massload.xmlbeans.FlagSNType flagprofessioneservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGPROFESSIONESERVIZI$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGPROFESSIONESERVIZI$18);
            }
            target.set(flagprofessioneservizi);
        }
    }
    
    /**
     * Unsets the "FLAG_PROFESSIONE_SERVIZI" attribute
     */
    public void unsetFLAGPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGPROFESSIONESERVIZI$18);
        }
    }
    
    /**
     * Gets the "PROFESSIONE_SERVIZI" attribute
     */
    public java.lang.String getPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROFESSIONESERVIZI$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROFESSIONE_SERVIZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI xgetPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI)get_store().find_attribute_user(PROFESSIONESERVIZI$20);
            return target;
        }
    }
    
    /**
     * True if has "PROFESSIONE_SERVIZI" attribute
     */
    public boolean isSetPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROFESSIONESERVIZI$20) != null;
        }
    }
    
    /**
     * Sets the "PROFESSIONE_SERVIZI" attribute
     */
    public void setPROFESSIONESERVIZI(java.lang.String professioneservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROFESSIONESERVIZI$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROFESSIONESERVIZI$20);
            }
            target.setStringValue(professioneservizi);
        }
    }
    
    /**
     * Sets (as xml) the "PROFESSIONE_SERVIZI" attribute
     */
    public void xsetPROFESSIONESERVIZI(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI professioneservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI)get_store().find_attribute_user(PROFESSIONESERVIZI$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI)get_store().add_attribute_user(PROFESSIONESERVIZI$20);
            }
            target.set(professioneservizi);
        }
    }
    
    /**
     * Unsets the "PROFESSIONE_SERVIZI" attribute
     */
    public void unsetPROFESSIONESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROFESSIONESERVIZI$20);
        }
    }
    
    /**
     * Gets the "CONDIZIONI_ESECUZIONE_CONTRATTO" attribute
     */
    public java.lang.String getCONDIZIONIESECUZIONECONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONDIZIONIESECUZIONECONTRATTO$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CONDIZIONI_ESECUZIONE_CONTRATTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO xgetCONDIZIONIESECUZIONECONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO)get_store().find_attribute_user(CONDIZIONIESECUZIONECONTRATTO$22);
            return target;
        }
    }
    
    /**
     * True if has "CONDIZIONI_ESECUZIONE_CONTRATTO" attribute
     */
    public boolean isSetCONDIZIONIESECUZIONECONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CONDIZIONIESECUZIONECONTRATTO$22) != null;
        }
    }
    
    /**
     * Sets the "CONDIZIONI_ESECUZIONE_CONTRATTO" attribute
     */
    public void setCONDIZIONIESECUZIONECONTRATTO(java.lang.String condizioniesecuzionecontratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONDIZIONIESECUZIONECONTRATTO$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONDIZIONIESECUZIONECONTRATTO$22);
            }
            target.setStringValue(condizioniesecuzionecontratto);
        }
    }
    
    /**
     * Sets (as xml) the "CONDIZIONI_ESECUZIONE_CONTRATTO" attribute
     */
    public void xsetCONDIZIONIESECUZIONECONTRATTO(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO condizioniesecuzionecontratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO)get_store().find_attribute_user(CONDIZIONIESECUZIONECONTRATTO$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO)get_store().add_attribute_user(CONDIZIONIESECUZIONECONTRATTO$22);
            }
            target.set(condizioniesecuzionecontratto);
        }
    }
    
    /**
     * Unsets the "CONDIZIONI_ESECUZIONE_CONTRATTO" attribute
     */
    public void unsetCONDIZIONIESECUZIONECONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CONDIZIONIESECUZIONECONTRATTO$22);
        }
    }
    
    /**
     * Gets the "OBBLIGO_NOMI_ESECUZIONE_CONTRATTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getOBBLIGONOMIESECUZIONECONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBBLIGONOMIESECUZIONECONTRATTO$24);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "OBBLIGO_NOMI_ESECUZIONE_CONTRATTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetOBBLIGONOMIESECUZIONECONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(OBBLIGONOMIESECUZIONECONTRATTO$24);
            return target;
        }
    }
    
    /**
     * Sets the "OBBLIGO_NOMI_ESECUZIONE_CONTRATTO" attribute
     */
    public void setOBBLIGONOMIESECUZIONECONTRATTO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum obbligonomiesecuzionecontratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBBLIGONOMIESECUZIONECONTRATTO$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OBBLIGONOMIESECUZIONECONTRATTO$24);
            }
            target.setEnumValue(obbligonomiesecuzionecontratto);
        }
    }
    
    /**
     * Sets (as xml) the "OBBLIGO_NOMI_ESECUZIONE_CONTRATTO" attribute
     */
    public void xsetOBBLIGONOMIESECUZIONECONTRATTO(it.avlp.simog.massload.xmlbeans.FlagSNType obbligonomiesecuzionecontratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(OBBLIGONOMIESECUZIONECONTRATTO$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(OBBLIGONOMIESECUZIONECONTRATTO$24);
            }
            target.set(obbligonomiesecuzionecontratto);
        }
    }
    /**
     * An XML ELENCO_CONDIZIONI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType$ELENCOCONDIZIONI.
     */
    public static class ELENCOCONDIZIONIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCONDIZIONI
    {
        
        public ELENCOCONDIZIONIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ELENCOCONDIZIONIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML ELENCO_CRITERI_ECONOMICI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType$ELENCOCRITERIECONOMICI.
     */
    public static class ELENCOCRITERIECONOMICIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERIECONOMICI
    {
        
        public ELENCOCRITERIECONOMICIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ELENCOCRITERIECONOMICIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML LIVELLI_CRITERI_ECONOMICI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType$LIVELLICRITERIECONOMICI.
     */
    public static class LIVELLICRITERIECONOMICIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERIECONOMICI
    {
        
        public LIVELLICRITERIECONOMICIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected LIVELLICRITERIECONOMICIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML ELENCO_CRITERI_TECNICI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType$ELENCOCRITERITECNICI.
     */
    public static class ELENCOCRITERITECNICIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.ELENCOCRITERITECNICI
    {
        
        public ELENCOCRITERITECNICIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ELENCOCRITERITECNICIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML LIVELLI_CRITERI_TECNICI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType$LIVELLICRITERITECNICI.
     */
    public static class LIVELLICRITERITECNICIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.LIVELLICRITERITECNICI
    {
        
        public LIVELLICRITERITECNICIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected LIVELLICRITERITECNICIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML PROFESSIONE_SERVIZI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType$PROFESSIONESERVIZI.
     */
    public static class PROFESSIONESERVIZIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.PROFESSIONESERVIZI
    {
        
        public PROFESSIONESERVIZIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected PROFESSIONESERVIZIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CONDIZIONI_ESECUZIONE_CONTRATTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType$CONDIZIONIESECUZIONECONTRATTO.
     */
    public static class CONDIZIONIESECUZIONECONTRATTOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType.CONDIZIONIESECUZIONECONTRATTO
    {
        
        public CONDIZIONIESECUZIONECONTRATTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CONDIZIONIESECUZIONECONTRATTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
