/*
 * XML Type:  IncaricatoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.IncaricatoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML IncaricatoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class IncaricatoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.IncaricatoType
{
    
    public IncaricatoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SEZIONE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SEZIONE");
    private static final javax.xml.namespace.QName IDRUOLO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_RUOLO");
    private static final javax.xml.namespace.QName CIGPROGESTERNA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG_PROG_ESTERNA");
    private static final javax.xml.namespace.QName DATAAFFPROGESTERNA$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_AFF_PROG_ESTERNA");
    private static final javax.xml.namespace.QName DATACONSPROGESTERNA$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_CONS_PROG_ESTERNA");
    private static final javax.xml.namespace.QName CODICEFISCALERESPONSABILE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_RESPONSABILE");
    private static final javax.xml.namespace.QName CODICESTATO$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_STATO");
    private static final javax.xml.namespace.QName PERSONAGIURIDICA$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERSONA_GIURIDICA");
    private static final javax.xml.namespace.QName IDGRUPPOINCARICATO$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_GRUPPO_INCARICATO");
    private static final javax.xml.namespace.QName MANDANTE$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MANDANTE");
    
    
    /**
     * Gets the "SEZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.SezioneType.Enum getSEZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SEZIONE$0);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.SezioneType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SEZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.SezioneType xgetSEZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SezioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SezioneType)get_store().find_attribute_user(SEZIONE$0);
            return target;
        }
    }
    
    /**
     * Sets the "SEZIONE" attribute
     */
    public void setSEZIONE(it.avlp.simog.massload.xmlbeans.SezioneType.Enum sezione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SEZIONE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SEZIONE$0);
            }
            target.setEnumValue(sezione);
        }
    }
    
    /**
     * Sets (as xml) the "SEZIONE" attribute
     */
    public void xsetSEZIONE(it.avlp.simog.massload.xmlbeans.SezioneType sezione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SezioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SezioneType)get_store().find_attribute_user(SEZIONE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SezioneType)get_store().add_attribute_user(SEZIONE$0);
            }
            target.set(sezione);
        }
    }
    
    /**
     * Gets the "ID_RUOLO" attribute
     */
    public java.lang.String getIDRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDRUOLO$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_RUOLO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RuoloResponsabileType xgetIDRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RuoloResponsabileType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RuoloResponsabileType)get_store().find_attribute_user(IDRUOLO$2);
            return target;
        }
    }
    
    /**
     * Sets the "ID_RUOLO" attribute
     */
    public void setIDRUOLO(java.lang.String idruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDRUOLO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDRUOLO$2);
            }
            target.setStringValue(idruolo);
        }
    }
    
    /**
     * Sets (as xml) the "ID_RUOLO" attribute
     */
    public void xsetIDRUOLO(it.avlp.simog.massload.xmlbeans.RuoloResponsabileType idruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RuoloResponsabileType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RuoloResponsabileType)get_store().find_attribute_user(IDRUOLO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RuoloResponsabileType)get_store().add_attribute_user(IDRUOLO$2);
            }
            target.set(idruolo);
        }
    }
    
    /**
     * Gets the "CIG_PROG_ESTERNA" attribute
     */
    public java.lang.String getCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGPROGESTERNA$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG_PROG_ESTERNA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGPROGESTERNA$4);
            return target;
        }
    }
    
    /**
     * True if has "CIG_PROG_ESTERNA" attribute
     */
    public boolean isSetCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CIGPROGESTERNA$4) != null;
        }
    }
    
    /**
     * Sets the "CIG_PROG_ESTERNA" attribute
     */
    public void setCIGPROGESTERNA(java.lang.String cigprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGPROGESTERNA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIGPROGESTERNA$4);
            }
            target.setStringValue(cigprogesterna);
        }
    }
    
    /**
     * Sets (as xml) the "CIG_PROG_ESTERNA" attribute
     */
    public void xsetCIGPROGESTERNA(it.avlp.simog.massload.xmlbeans.CigType cigprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGPROGESTERNA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIGPROGESTERNA$4);
            }
            target.set(cigprogesterna);
        }
    }
    
    /**
     * Unsets the "CIG_PROG_ESTERNA" attribute
     */
    public void unsetCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CIGPROGESTERNA$4);
        }
    }
    
    /**
     * Gets the "DATA_AFF_PROG_ESTERNA" attribute
     */
    public java.util.Calendar getDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAFFPROGESTERNA$6);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_AFF_PROG_ESTERNA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAFFPROGESTERNA$6);
            return target;
        }
    }
    
    /**
     * True if has "DATA_AFF_PROG_ESTERNA" attribute
     */
    public boolean isSetDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAAFFPROGESTERNA$6) != null;
        }
    }
    
    /**
     * Sets the "DATA_AFF_PROG_ESTERNA" attribute
     */
    public void setDATAAFFPROGESTERNA(java.util.Calendar dataaffprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAFFPROGESTERNA$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAAFFPROGESTERNA$6);
            }
            target.setCalendarValue(dataaffprogesterna);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_AFF_PROG_ESTERNA" attribute
     */
    public void xsetDATAAFFPROGESTERNA(it.avlp.simog.massload.xmlbeans.DbDateType dataaffprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAFFPROGESTERNA$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAAFFPROGESTERNA$6);
            }
            target.set(dataaffprogesterna);
        }
    }
    
    /**
     * Unsets the "DATA_AFF_PROG_ESTERNA" attribute
     */
    public void unsetDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAAFFPROGESTERNA$6);
        }
    }
    
    /**
     * Gets the "DATA_CONS_PROG_ESTERNA" attribute
     */
    public java.util.Calendar getDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACONSPROGESTERNA$8);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CONS_PROG_ESTERNA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACONSPROGESTERNA$8);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CONS_PROG_ESTERNA" attribute
     */
    public boolean isSetDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATACONSPROGESTERNA$8) != null;
        }
    }
    
    /**
     * Sets the "DATA_CONS_PROG_ESTERNA" attribute
     */
    public void setDATACONSPROGESTERNA(java.util.Calendar dataconsprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACONSPROGESTERNA$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATACONSPROGESTERNA$8);
            }
            target.setCalendarValue(dataconsprogesterna);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CONS_PROG_ESTERNA" attribute
     */
    public void xsetDATACONSPROGESTERNA(it.avlp.simog.massload.xmlbeans.DbDateType dataconsprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACONSPROGESTERNA$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATACONSPROGESTERNA$8);
            }
            target.set(dataconsprogesterna);
        }
    }
    
    /**
     * Unsets the "DATA_CONS_PROG_ESTERNA" attribute
     */
    public void unsetDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATACONSPROGESTERNA$8);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public java.lang.String getCODICEFISCALERESPONSABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALERESPONSABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$10);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public void setCODICEFISCALERESPONSABILE(java.lang.String codicefiscaleresponsabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALERESPONSABILE$10);
            }
            target.setStringValue(codicefiscaleresponsabile);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public void xsetCODICEFISCALERESPONSABILE(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleresponsabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALERESPONSABILE$10);
            }
            target.set(codicefiscaleresponsabile);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$12);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$12);
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
            return get_store().find_attribute_user(CODICESTATO$12) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICESTATO$12);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().add_attribute_user(CODICESTATO$12);
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
            get_store().remove_attribute(CODICESTATO$12);
        }
    }
    
    /**
     * Gets the "PERSONA_GIURIDICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getPERSONAGIURIDICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERSONAGIURIDICA$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERSONA_GIURIDICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetPERSONAGIURIDICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PERSONAGIURIDICA$14);
            return target;
        }
    }
    
    /**
     * True if has "PERSONA_GIURIDICA" attribute
     */
    public boolean isSetPERSONAGIURIDICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PERSONAGIURIDICA$14) != null;
        }
    }
    
    /**
     * Sets the "PERSONA_GIURIDICA" attribute
     */
    public void setPERSONAGIURIDICA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum personagiuridica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERSONAGIURIDICA$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERSONAGIURIDICA$14);
            }
            target.setEnumValue(personagiuridica);
        }
    }
    
    /**
     * Sets (as xml) the "PERSONA_GIURIDICA" attribute
     */
    public void xsetPERSONAGIURIDICA(it.avlp.simog.massload.xmlbeans.FlagSNType personagiuridica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PERSONAGIURIDICA$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(PERSONAGIURIDICA$14);
            }
            target.set(personagiuridica);
        }
    }
    
    /**
     * Unsets the "PERSONA_GIURIDICA" attribute
     */
    public void unsetPERSONAGIURIDICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PERSONAGIURIDICA$14);
        }
    }
    
    /**
     * Gets the "ID_GRUPPO_INCARICATO" attribute
     */
    public int getIDGRUPPOINCARICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGRUPPOINCARICATO$16);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_GRUPPO_INCARICATO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType99 xgetIDGRUPPOINCARICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType99 target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType99)get_store().find_attribute_user(IDGRUPPOINCARICATO$16);
            return target;
        }
    }
    
    /**
     * True if has "ID_GRUPPO_INCARICATO" attribute
     */
    public boolean isSetIDGRUPPOINCARICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDGRUPPOINCARICATO$16) != null;
        }
    }
    
    /**
     * Sets the "ID_GRUPPO_INCARICATO" attribute
     */
    public void setIDGRUPPOINCARICATO(int idgruppoincaricato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGRUPPOINCARICATO$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDGRUPPOINCARICATO$16);
            }
            target.setIntValue(idgruppoincaricato);
        }
    }
    
    /**
     * Sets (as xml) the "ID_GRUPPO_INCARICATO" attribute
     */
    public void xsetIDGRUPPOINCARICATO(it.avlp.simog.massload.xmlbeans.InteroType99 idgruppoincaricato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType99 target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType99)get_store().find_attribute_user(IDGRUPPOINCARICATO$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType99)get_store().add_attribute_user(IDGRUPPOINCARICATO$16);
            }
            target.set(idgruppoincaricato);
        }
    }
    
    /**
     * Unsets the "ID_GRUPPO_INCARICATO" attribute
     */
    public void unsetIDGRUPPOINCARICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDGRUPPOINCARICATO$16);
        }
    }
    
    /**
     * Gets the "MANDANTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getMANDANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MANDANTE$18);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "MANDANTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetMANDANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(MANDANTE$18);
            return target;
        }
    }
    
    /**
     * True if has "MANDANTE" attribute
     */
    public boolean isSetMANDANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MANDANTE$18) != null;
        }
    }
    
    /**
     * Sets the "MANDANTE" attribute
     */
    public void setMANDANTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum mandante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MANDANTE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MANDANTE$18);
            }
            target.setEnumValue(mandante);
        }
    }
    
    /**
     * Sets (as xml) the "MANDANTE" attribute
     */
    public void xsetMANDANTE(it.avlp.simog.massload.xmlbeans.FlagSNType mandante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(MANDANTE$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(MANDANTE$18);
            }
            target.set(mandante);
        }
    }
    
    /**
     * Unsets the "MANDANTE" attribute
     */
    public void unsetMANDANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MANDANTE$18);
        }
    }
}
