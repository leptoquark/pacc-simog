/*
 * XML Type:  AnomaliaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AnomaliaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AnomaliaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AnomaliaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AnomaliaType
{
    
    public AnomaliaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE");
    private static final javax.xml.namespace.QName DESCRIZIONE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DESCRIZIONE");
    private static final javax.xml.namespace.QName LIVELLO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LIVELLO");
    private static final javax.xml.namespace.QName ELEMENTO$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ELEMENTO");
    private static final javax.xml.namespace.QName SCHEDA$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SCHEDA");
    private static final javax.xml.namespace.QName PROGRESSIVO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PROGRESSIVO");
    private static final javax.xml.namespace.QName CAMPOXML$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CAMPO_XML");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    
    
    /**
     * Gets the "CODICE" attribute
     */
    public java.lang.String getCODICE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICE$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE" attribute
     */
    public org.apache.xmlbeans.XmlString xgetCODICE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CODICE$0);
            return target;
        }
    }
    
    /**
     * True if has "CODICE" attribute
     */
    public boolean isSetCODICE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODICE$0) != null;
        }
    }
    
    /**
     * Sets the "CODICE" attribute
     */
    public void setCODICE(java.lang.String codice)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICE$0);
            }
            target.setStringValue(codice);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE" attribute
     */
    public void xsetCODICE(org.apache.xmlbeans.XmlString codice)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CODICE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CODICE$0);
            }
            target.set(codice);
        }
    }
    
    /**
     * Unsets the "CODICE" attribute
     */
    public void unsetCODICE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODICE$0);
        }
    }
    
    /**
     * Gets the "DESCRIZIONE" attribute
     */
    public java.lang.String getDESCRIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONE$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESCRIZIONE" attribute
     */
    public org.apache.xmlbeans.XmlString xgetDESCRIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DESCRIZIONE$2);
            return target;
        }
    }
    
    /**
     * Sets the "DESCRIZIONE" attribute
     */
    public void setDESCRIZIONE(java.lang.String descrizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCRIZIONE$2);
            }
            target.setStringValue(descrizione);
        }
    }
    
    /**
     * Sets (as xml) the "DESCRIZIONE" attribute
     */
    public void xsetDESCRIZIONE(org.apache.xmlbeans.XmlString descrizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DESCRIZIONE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DESCRIZIONE$2);
            }
            target.set(descrizione);
        }
    }
    
    /**
     * Gets the "LIVELLO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LivelloType.Enum getLIVELLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIVELLO$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.LivelloType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "LIVELLO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LivelloType xgetLIVELLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LivelloType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LivelloType)get_store().find_attribute_user(LIVELLO$4);
            return target;
        }
    }
    
    /**
     * Sets the "LIVELLO" attribute
     */
    public void setLIVELLO(it.avlp.simog.massload.xmlbeans.LivelloType.Enum livello)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LIVELLO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LIVELLO$4);
            }
            target.setEnumValue(livello);
        }
    }
    
    /**
     * Sets (as xml) the "LIVELLO" attribute
     */
    public void xsetLIVELLO(it.avlp.simog.massload.xmlbeans.LivelloType livello)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LivelloType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LivelloType)get_store().find_attribute_user(LIVELLO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LivelloType)get_store().add_attribute_user(LIVELLO$4);
            }
            target.set(livello);
        }
    }
    
    /**
     * Gets the "ELEMENTO" attribute
     */
    public int getELEMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELEMENTO$6);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ELEMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetELEMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(ELEMENTO$6);
            return target;
        }
    }
    
    /**
     * Sets the "ELEMENTO" attribute
     */
    public void setELEMENTO(int elemento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ELEMENTO$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ELEMENTO$6);
            }
            target.setIntValue(elemento);
        }
    }
    
    /**
     * Sets (as xml) the "ELEMENTO" attribute
     */
    public void xsetELEMENTO(it.avlp.simog.massload.xmlbeans.InteroType elemento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(ELEMENTO$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(ELEMENTO$6);
            }
            target.set(elemento);
        }
    }
    
    /**
     * Gets the "SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum getSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCHEDA$8);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipiSchedeType xgetSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiSchedeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().find_attribute_user(SCHEDA$8);
            return target;
        }
    }
    
    /**
     * True if has "SCHEDA" attribute
     */
    public boolean isSetSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SCHEDA$8) != null;
        }
    }
    
    /**
     * Sets the "SCHEDA" attribute
     */
    public void setSCHEDA(it.avlp.simog.massload.xmlbeans.TipiSchedeType.Enum scheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCHEDA$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SCHEDA$8);
            }
            target.setEnumValue(scheda);
        }
    }
    
    /**
     * Sets (as xml) the "SCHEDA" attribute
     */
    public void xsetSCHEDA(it.avlp.simog.massload.xmlbeans.TipiSchedeType scheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipiSchedeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().find_attribute_user(SCHEDA$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipiSchedeType)get_store().add_attribute_user(SCHEDA$8);
            }
            target.set(scheda);
        }
    }
    
    /**
     * Unsets the "SCHEDA" attribute
     */
    public void unsetSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SCHEDA$8);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGRESSIVO$10);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(PROGRESSIVO$10);
            return target;
        }
    }
    
    /**
     * True if has "PROGRESSIVO" attribute
     */
    public boolean isSetPROGRESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROGRESSIVO$10) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGRESSIVO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROGRESSIVO$10);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(PROGRESSIVO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(PROGRESSIVO$10);
            }
            target.set(progressivo);
        }
    }
    
    /**
     * Unsets the "PROGRESSIVO" attribute
     */
    public void unsetPROGRESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROGRESSIVO$10);
        }
    }
    
    /**
     * Gets the "CAMPO_XML" attribute
     */
    public java.lang.String getCAMPOXML()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAMPOXML$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CAMPO_XML" attribute
     */
    public it.avlp.simog.massload.xmlbeans.NomeCampoType xgetCAMPOXML()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.NomeCampoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.NomeCampoType)get_store().find_attribute_user(CAMPOXML$12);
            return target;
        }
    }
    
    /**
     * True if has "CAMPO_XML" attribute
     */
    public boolean isSetCAMPOXML()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CAMPOXML$12) != null;
        }
    }
    
    /**
     * Sets the "CAMPO_XML" attribute
     */
    public void setCAMPOXML(java.lang.String campoxml)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAMPOXML$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CAMPOXML$12);
            }
            target.setStringValue(campoxml);
        }
    }
    
    /**
     * Sets (as xml) the "CAMPO_XML" attribute
     */
    public void xsetCAMPOXML(it.avlp.simog.massload.xmlbeans.NomeCampoType campoxml)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.NomeCampoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.NomeCampoType)get_store().find_attribute_user(CAMPOXML$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.NomeCampoType)get_store().add_attribute_user(CAMPOXML$12);
            }
            target.set(campoxml);
        }
    }
    
    /**
     * Unsets the "CAMPO_XML" attribute
     */
    public void unsetCAMPOXML()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CAMPOXML$12);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$14);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$14);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$14) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$14);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$14);
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
            get_store().remove_attribute(IDSCHEDASIMOG$14);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$16);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$16);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$16) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$16);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$16);
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
            get_store().remove_attribute(IDSCHEDALOCALE$16);
        }
    }
}
