/*
 * XML Type:  IniziativaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.IniziativaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML IniziativaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class IniziativaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.IniziativaType
{
    
    public IniziativaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CATEGORIEI$0 = 
        new javax.xml.namespace.QName("", "CATEGORIE_I");
    private static final javax.xml.namespace.QName TERRITORII$2 = 
        new javax.xml.namespace.QName("", "TERRITORI_I");
    private static final javax.xml.namespace.QName AMBITILOTTOI$4 = 
        new javax.xml.namespace.QName("", "AMBITI_LOTTO_I");
    private static final javax.xml.namespace.QName IDGARAI$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_GARA_I");
    private static final javax.xml.namespace.QName CIGI$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG_I");
    private static final javax.xml.namespace.QName DESCRIZIONESOGGAGGI$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESCRIZIONE_SOGG_AGG_I");
    private static final javax.xml.namespace.QName DESCRIZIONEINIZIATIVAI$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESCRIZIONE_INIZIATIVA_I");
    private static final javax.xml.namespace.QName SSAARIFI$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SSAA_RIF_I");
    private static final javax.xml.namespace.QName STATOI$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "STATO_I");
    private static final javax.xml.namespace.QName CONFRONTOCOMPETITIVOI$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CONFRONTO_COMPETITIVO_I");
    private static final javax.xml.namespace.QName NOTEI$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NOTE_I");
    private static final javax.xml.namespace.QName URLI$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL_I");
    
    
    /**
     * Gets the "CATEGORIE_I" element
     */
    public it.avlp.simog.massload.xmlbeans.CategLottoType getCATEGORIEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategLottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategLottoType)get_store().find_element_user(CATEGORIEI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "CATEGORIE_I" element
     */
    public boolean isSetCATEGORIEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CATEGORIEI$0) != 0;
        }
    }
    
    /**
     * Sets the "CATEGORIE_I" element
     */
    public void setCATEGORIEI(it.avlp.simog.massload.xmlbeans.CategLottoType categoriei)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategLottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategLottoType)get_store().find_element_user(CATEGORIEI$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CategLottoType)get_store().add_element_user(CATEGORIEI$0);
            }
            target.set(categoriei);
        }
    }
    
    /**
     * Appends and returns a new empty "CATEGORIE_I" element
     */
    public it.avlp.simog.massload.xmlbeans.CategLottoType addNewCATEGORIEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategLottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategLottoType)get_store().add_element_user(CATEGORIEI$0);
            return target;
        }
    }
    
    /**
     * Unsets the "CATEGORIE_I" element
     */
    public void unsetCATEGORIEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CATEGORIEI$0, 0);
        }
    }
    
    /**
     * Gets the "TERRITORI_I" element
     */
    public it.avlp.simog.massload.xmlbeans.TerritorioType getTERRITORII()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TerritorioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().find_element_user(TERRITORII$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "TERRITORI_I" element
     */
    public boolean isSetTERRITORII()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TERRITORII$2) != 0;
        }
    }
    
    /**
     * Sets the "TERRITORI_I" element
     */
    public void setTERRITORII(it.avlp.simog.massload.xmlbeans.TerritorioType territorii)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TerritorioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().find_element_user(TERRITORII$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().add_element_user(TERRITORII$2);
            }
            target.set(territorii);
        }
    }
    
    /**
     * Appends and returns a new empty "TERRITORI_I" element
     */
    public it.avlp.simog.massload.xmlbeans.TerritorioType addNewTERRITORII()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TerritorioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().add_element_user(TERRITORII$2);
            return target;
        }
    }
    
    /**
     * Unsets the "TERRITORI_I" element
     */
    public void unsetTERRITORII()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TERRITORII$2, 0);
        }
    }
    
    /**
     * Gets the "AMBITI_LOTTO_I" element
     */
    public it.avlp.simog.massload.xmlbeans.AmbitoType getAMBITILOTTOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AmbitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AmbitoType)get_store().find_element_user(AMBITILOTTOI$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "AMBITI_LOTTO_I" element
     */
    public boolean isSetAMBITILOTTOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AMBITILOTTOI$4) != 0;
        }
    }
    
    /**
     * Sets the "AMBITI_LOTTO_I" element
     */
    public void setAMBITILOTTOI(it.avlp.simog.massload.xmlbeans.AmbitoType ambitilottoi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AmbitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AmbitoType)get_store().find_element_user(AMBITILOTTOI$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AmbitoType)get_store().add_element_user(AMBITILOTTOI$4);
            }
            target.set(ambitilottoi);
        }
    }
    
    /**
     * Appends and returns a new empty "AMBITI_LOTTO_I" element
     */
    public it.avlp.simog.massload.xmlbeans.AmbitoType addNewAMBITILOTTOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AmbitoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AmbitoType)get_store().add_element_user(AMBITILOTTOI$4);
            return target;
        }
    }
    
    /**
     * Unsets the "AMBITI_LOTTO_I" element
     */
    public void unsetAMBITILOTTOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AMBITILOTTOI$4, 0);
        }
    }
    
    /**
     * Gets the "ID_GARA_I" attribute
     */
    public long getIDGARAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGARAI$6);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_GARA_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LongType xgetIDGARAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LongType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().find_attribute_user(IDGARAI$6);
            return target;
        }
    }
    
    /**
     * True if has "ID_GARA_I" attribute
     */
    public boolean isSetIDGARAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDGARAI$6) != null;
        }
    }
    
    /**
     * Sets the "ID_GARA_I" attribute
     */
    public void setIDGARAI(long idgarai)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGARAI$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDGARAI$6);
            }
            target.setLongValue(idgarai);
        }
    }
    
    /**
     * Sets (as xml) the "ID_GARA_I" attribute
     */
    public void xsetIDGARAI(it.avlp.simog.massload.xmlbeans.LongType idgarai)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LongType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().find_attribute_user(IDGARAI$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().add_attribute_user(IDGARAI$6);
            }
            target.set(idgarai);
        }
    }
    
    /**
     * Unsets the "ID_GARA_I" attribute
     */
    public void unsetIDGARAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDGARAI$6);
        }
    }
    
    /**
     * Gets the "CIG_I" attribute
     */
    public java.lang.String getCIGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGI$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGI$8);
            return target;
        }
    }
    
    /**
     * True if has "CIG_I" attribute
     */
    public boolean isSetCIGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CIGI$8) != null;
        }
    }
    
    /**
     * Sets the "CIG_I" attribute
     */
    public void setCIGI(java.lang.String cigi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGI$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIGI$8);
            }
            target.setStringValue(cigi);
        }
    }
    
    /**
     * Sets (as xml) the "CIG_I" attribute
     */
    public void xsetCIGI(it.avlp.simog.massload.xmlbeans.CigType cigi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGI$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIGI$8);
            }
            target.set(cigi);
        }
    }
    
    /**
     * Unsets the "CIG_I" attribute
     */
    public void unsetCIGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CIGI$8);
        }
    }
    
    /**
     * Gets the "DESCRIZIONE_SOGG_AGG_I" attribute
     */
    public java.lang.String getDESCRIZIONESOGGAGGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONESOGGAGGI$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESCRIZIONE_SOGG_AGG_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI xgetDESCRIZIONESOGGAGGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI)get_store().find_attribute_user(DESCRIZIONESOGGAGGI$10);
            return target;
        }
    }
    
    /**
     * True if has "DESCRIZIONE_SOGG_AGG_I" attribute
     */
    public boolean isSetDESCRIZIONESOGGAGGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DESCRIZIONESOGGAGGI$10) != null;
        }
    }
    
    /**
     * Sets the "DESCRIZIONE_SOGG_AGG_I" attribute
     */
    public void setDESCRIZIONESOGGAGGI(java.lang.String descrizionesoggaggi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONESOGGAGGI$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCRIZIONESOGGAGGI$10);
            }
            target.setStringValue(descrizionesoggaggi);
        }
    }
    
    /**
     * Sets (as xml) the "DESCRIZIONE_SOGG_AGG_I" attribute
     */
    public void xsetDESCRIZIONESOGGAGGI(it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI descrizionesoggaggi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI)get_store().find_attribute_user(DESCRIZIONESOGGAGGI$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI)get_store().add_attribute_user(DESCRIZIONESOGGAGGI$10);
            }
            target.set(descrizionesoggaggi);
        }
    }
    
    /**
     * Unsets the "DESCRIZIONE_SOGG_AGG_I" attribute
     */
    public void unsetDESCRIZIONESOGGAGGI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DESCRIZIONESOGGAGGI$10);
        }
    }
    
    /**
     * Gets the "DESCRIZIONE_INIZIATIVA_I" attribute
     */
    public java.lang.String getDESCRIZIONEINIZIATIVAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONEINIZIATIVAI$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESCRIZIONE_INIZIATIVA_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI xgetDESCRIZIONEINIZIATIVAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI)get_store().find_attribute_user(DESCRIZIONEINIZIATIVAI$12);
            return target;
        }
    }
    
    /**
     * True if has "DESCRIZIONE_INIZIATIVA_I" attribute
     */
    public boolean isSetDESCRIZIONEINIZIATIVAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DESCRIZIONEINIZIATIVAI$12) != null;
        }
    }
    
    /**
     * Sets the "DESCRIZIONE_INIZIATIVA_I" attribute
     */
    public void setDESCRIZIONEINIZIATIVAI(java.lang.String descrizioneiniziativai)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONEINIZIATIVAI$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCRIZIONEINIZIATIVAI$12);
            }
            target.setStringValue(descrizioneiniziativai);
        }
    }
    
    /**
     * Sets (as xml) the "DESCRIZIONE_INIZIATIVA_I" attribute
     */
    public void xsetDESCRIZIONEINIZIATIVAI(it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI descrizioneiniziativai)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI)get_store().find_attribute_user(DESCRIZIONEINIZIATIVAI$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI)get_store().add_attribute_user(DESCRIZIONEINIZIATIVAI$12);
            }
            target.set(descrizioneiniziativai);
        }
    }
    
    /**
     * Unsets the "DESCRIZIONE_INIZIATIVA_I" attribute
     */
    public void unsetDESCRIZIONEINIZIATIVAI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DESCRIZIONEINIZIATIVAI$12);
        }
    }
    
    /**
     * Gets the "SSAA_RIF_I" attribute
     */
    public java.lang.String getSSAARIFI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SSAARIFI$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SSAA_RIF_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI xgetSSAARIFI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI)get_store().find_attribute_user(SSAARIFI$14);
            return target;
        }
    }
    
    /**
     * True if has "SSAA_RIF_I" attribute
     */
    public boolean isSetSSAARIFI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SSAARIFI$14) != null;
        }
    }
    
    /**
     * Sets the "SSAA_RIF_I" attribute
     */
    public void setSSAARIFI(java.lang.String ssaarifi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SSAARIFI$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SSAARIFI$14);
            }
            target.setStringValue(ssaarifi);
        }
    }
    
    /**
     * Sets (as xml) the "SSAA_RIF_I" attribute
     */
    public void xsetSSAARIFI(it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI ssaarifi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI)get_store().find_attribute_user(SSAARIFI$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI)get_store().add_attribute_user(SSAARIFI$14);
            }
            target.set(ssaarifi);
        }
    }
    
    /**
     * Unsets the "SSAA_RIF_I" attribute
     */
    public void unsetSSAARIFI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SSAARIFI$14);
        }
    }
    
    /**
     * Gets the "STATO_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getSTATOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STATOI$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "STATO_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetSTATOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(STATOI$16);
            return target;
        }
    }
    
    /**
     * True if has "STATO_I" attribute
     */
    public boolean isSetSTATOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(STATOI$16) != null;
        }
    }
    
    /**
     * Sets the "STATO_I" attribute
     */
    public void setSTATOI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum statoi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STATOI$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STATOI$16);
            }
            target.setEnumValue(statoi);
        }
    }
    
    /**
     * Sets (as xml) the "STATO_I" attribute
     */
    public void xsetSTATOI(it.avlp.simog.massload.xmlbeans.FlagSNType statoi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(STATOI$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(STATOI$16);
            }
            target.set(statoi);
        }
    }
    
    /**
     * Unsets the "STATO_I" attribute
     */
    public void unsetSTATOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(STATOI$16);
        }
    }
    
    /**
     * Gets the "CONFRONTO_COMPETITIVO_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getCONFRONTOCOMPETITIVOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONFRONTOCOMPETITIVOI$18);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CONFRONTO_COMPETITIVO_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetCONFRONTOCOMPETITIVOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CONFRONTOCOMPETITIVOI$18);
            return target;
        }
    }
    
    /**
     * True if has "CONFRONTO_COMPETITIVO_I" attribute
     */
    public boolean isSetCONFRONTOCOMPETITIVOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CONFRONTOCOMPETITIVOI$18) != null;
        }
    }
    
    /**
     * Sets the "CONFRONTO_COMPETITIVO_I" attribute
     */
    public void setCONFRONTOCOMPETITIVOI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum confrontocompetitivoi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONFRONTOCOMPETITIVOI$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONFRONTOCOMPETITIVOI$18);
            }
            target.setEnumValue(confrontocompetitivoi);
        }
    }
    
    /**
     * Sets (as xml) the "CONFRONTO_COMPETITIVO_I" attribute
     */
    public void xsetCONFRONTOCOMPETITIVOI(it.avlp.simog.massload.xmlbeans.FlagSNType confrontocompetitivoi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CONFRONTOCOMPETITIVOI$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(CONFRONTOCOMPETITIVOI$18);
            }
            target.set(confrontocompetitivoi);
        }
    }
    
    /**
     * Unsets the "CONFRONTO_COMPETITIVO_I" attribute
     */
    public void unsetCONFRONTOCOMPETITIVOI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CONFRONTOCOMPETITIVOI$18);
        }
    }
    
    /**
     * Gets the "NOTE_I" attribute
     */
    public java.lang.String getNOTEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOTEI$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NOTE_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI xgetNOTEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI)get_store().find_attribute_user(NOTEI$20);
            return target;
        }
    }
    
    /**
     * True if has "NOTE_I" attribute
     */
    public boolean isSetNOTEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NOTEI$20) != null;
        }
    }
    
    /**
     * Sets the "NOTE_I" attribute
     */
    public void setNOTEI(java.lang.String notei)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOTEI$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NOTEI$20);
            }
            target.setStringValue(notei);
        }
    }
    
    /**
     * Sets (as xml) the "NOTE_I" attribute
     */
    public void xsetNOTEI(it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI notei)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI)get_store().find_attribute_user(NOTEI$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI)get_store().add_attribute_user(NOTEI$20);
            }
            target.set(notei);
        }
    }
    
    /**
     * Unsets the "NOTE_I" attribute
     */
    public void unsetNOTEI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NOTEI$20);
        }
    }
    
    /**
     * Gets the "URL_I" attribute
     */
    public java.lang.String getURLI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLI$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL_I" attribute
     */
    public it.avlp.simog.massload.xmlbeans.IniziativaType.URLI xgetURLI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.URLI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.URLI)get_store().find_attribute_user(URLI$22);
            return target;
        }
    }
    
    /**
     * True if has "URL_I" attribute
     */
    public boolean isSetURLI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(URLI$22) != null;
        }
    }
    
    /**
     * Sets the "URL_I" attribute
     */
    public void setURLI(java.lang.String urli)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLI$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URLI$22);
            }
            target.setStringValue(urli);
        }
    }
    
    /**
     * Sets (as xml) the "URL_I" attribute
     */
    public void xsetURLI(it.avlp.simog.massload.xmlbeans.IniziativaType.URLI urli)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaType.URLI target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaType.URLI)get_store().find_attribute_user(URLI$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType.URLI)get_store().add_attribute_user(URLI$22);
            }
            target.set(urli);
        }
    }
    
    /**
     * Unsets the "URL_I" attribute
     */
    public void unsetURLI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(URLI$22);
        }
    }
    /**
     * An XML DESCRIZIONE_SOGG_AGG_I(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.IniziativaType$DESCRIZIONESOGGAGGI.
     */
    public static class DESCRIZIONESOGGAGGIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONESOGGAGGI
    {
        
        public DESCRIZIONESOGGAGGIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DESCRIZIONESOGGAGGIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DESCRIZIONE_INIZIATIVA_I(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.IniziativaType$DESCRIZIONEINIZIATIVAI.
     */
    public static class DESCRIZIONEINIZIATIVAIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.IniziativaType.DESCRIZIONEINIZIATIVAI
    {
        
        public DESCRIZIONEINIZIATIVAIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DESCRIZIONEINIZIATIVAIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML SSAA_RIF_I(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.IniziativaType$SSAARIFI.
     */
    public static class SSAARIFIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.IniziativaType.SSAARIFI
    {
        
        public SSAARIFIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected SSAARIFIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NOTE_I(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.IniziativaType$NOTEI.
     */
    public static class NOTEIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.IniziativaType.NOTEI
    {
        
        public NOTEIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NOTEIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML URL_I(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.IniziativaType$URLI.
     */
    public static class URLIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.IniziativaType.URLI
    {
        
        public URLIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
