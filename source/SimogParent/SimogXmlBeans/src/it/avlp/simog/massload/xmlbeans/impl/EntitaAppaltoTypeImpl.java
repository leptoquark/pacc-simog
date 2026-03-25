/*
 * XML Type:  EntitaAppaltoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.EntitaAppaltoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML EntitaAppaltoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class EntitaAppaltoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.EntitaAppaltoType
{
    
    public EntitaAppaltoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TITOLOPROCEDURAGARA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TITOLO_PROCEDURA_GARA");
    private static final javax.xml.namespace.QName CPVGARA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CPV_GARA");
    private static final javax.xml.namespace.QName TIPOCONTRATTOAPPALTO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPO_CONTRATTO_APPALTO");
    private static final javax.xml.namespace.QName MAXLOTTIPARTECIPAZIONE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MAX_LOTTI_PARTECIPAZIONE");
    private static final javax.xml.namespace.QName NUMMAXLOTTIPARTECIPAZIONE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_MAX_LOTTI_PARTECIPAZIONE");
    private static final javax.xml.namespace.QName NUMMAXLOTTIOFFERENTE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_MAX_LOTTI_OFFERENTE");
    private static final javax.xml.namespace.QName FLAGSAAGGGRUPPILOTTI$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_SA_AGG_GRUPPI_LOTTI");
    private static final javax.xml.namespace.QName SAAGGGRUPPILOTTI$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SA_AGG_GRUPPI_LOTTI");
    
    
    /**
     * Gets the "TITOLO_PROCEDURA_GARA" attribute
     */
    public java.lang.String getTITOLOPROCEDURAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITOLOPROCEDURAGARA$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TITOLO_PROCEDURA_GARA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA xgetTITOLOPROCEDURAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA)get_store().find_attribute_user(TITOLOPROCEDURAGARA$0);
            return target;
        }
    }
    
    /**
     * Sets the "TITOLO_PROCEDURA_GARA" attribute
     */
    public void setTITOLOPROCEDURAGARA(java.lang.String titoloproceduragara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITOLOPROCEDURAGARA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TITOLOPROCEDURAGARA$0);
            }
            target.setStringValue(titoloproceduragara);
        }
    }
    
    /**
     * Sets (as xml) the "TITOLO_PROCEDURA_GARA" attribute
     */
    public void xsetTITOLOPROCEDURAGARA(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA titoloproceduragara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA)get_store().find_attribute_user(TITOLOPROCEDURAGARA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA)get_store().add_attribute_user(TITOLOPROCEDURAGARA$0);
            }
            target.set(titoloproceduragara);
        }
    }
    
    /**
     * Gets the "CPV_GARA" attribute
     */
    public java.lang.String getCPVGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPVGARA$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CPV_GARA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA xgetCPVGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA)get_store().find_attribute_user(CPVGARA$2);
            return target;
        }
    }
    
    /**
     * Sets the "CPV_GARA" attribute
     */
    public void setCPVGARA(java.lang.String cpvgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPVGARA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CPVGARA$2);
            }
            target.setStringValue(cpvgara);
        }
    }
    
    /**
     * Sets (as xml) the "CPV_GARA" attribute
     */
    public void xsetCPVGARA(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA cpvgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA)get_store().find_attribute_user(CPVGARA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA)get_store().add_attribute_user(CPVGARA$2);
            }
            target.set(cpvgara);
        }
    }
    
    /**
     * Gets the "TIPO_CONTRATTO_APPALTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO.Enum getTIPOCONTRATTOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCONTRATTOAPPALTO$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_CONTRATTO_APPALTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO xgetTIPOCONTRATTOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO)get_store().find_attribute_user(TIPOCONTRATTOAPPALTO$4);
            return target;
        }
    }
    
    /**
     * Sets the "TIPO_CONTRATTO_APPALTO" attribute
     */
    public void setTIPOCONTRATTOAPPALTO(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO.Enum tipocontrattoappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCONTRATTOAPPALTO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOCONTRATTOAPPALTO$4);
            }
            target.setEnumValue(tipocontrattoappalto);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_CONTRATTO_APPALTO" attribute
     */
    public void xsetTIPOCONTRATTOAPPALTO(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO tipocontrattoappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO)get_store().find_attribute_user(TIPOCONTRATTOAPPALTO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO)get_store().add_attribute_user(TIPOCONTRATTOAPPALTO$4);
            }
            target.set(tipocontrattoappalto);
        }
    }
    
    /**
     * Gets the "MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType.Enum getMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAXLOTTIPARTECIPAZIONE$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType xgetMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType)get_store().find_attribute_user(MAXLOTTIPARTECIPAZIONE$6);
            return target;
        }
    }
    
    /**
     * True if has "MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public boolean isSetMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MAXLOTTIPARTECIPAZIONE$6) != null;
        }
    }
    
    /**
     * Sets the "MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public void setMAXLOTTIPARTECIPAZIONE(it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType.Enum maxlottipartecipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAXLOTTIPARTECIPAZIONE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MAXLOTTIPARTECIPAZIONE$6);
            }
            target.setEnumValue(maxlottipartecipazione);
        }
    }
    
    /**
     * Sets (as xml) the "MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public void xsetMAXLOTTIPARTECIPAZIONE(it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType maxlottipartecipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType)get_store().find_attribute_user(MAXLOTTIPARTECIPAZIONE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType)get_store().add_attribute_user(MAXLOTTIPARTECIPAZIONE$6);
            }
            target.set(maxlottipartecipazione);
        }
    }
    
    /**
     * Unsets the "MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public void unsetMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MAXLOTTIPARTECIPAZIONE$6);
        }
    }
    
    /**
     * Gets the "NUM_MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public int getNUMMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMAXLOTTIPARTECIPAZIONE$8);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE xgetNUMMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE)get_store().find_attribute_user(NUMMAXLOTTIPARTECIPAZIONE$8);
            return target;
        }
    }
    
    /**
     * True if has "NUM_MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public boolean isSetNUMMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMMAXLOTTIPARTECIPAZIONE$8) != null;
        }
    }
    
    /**
     * Sets the "NUM_MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public void setNUMMAXLOTTIPARTECIPAZIONE(int nummaxlottipartecipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMAXLOTTIPARTECIPAZIONE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMMAXLOTTIPARTECIPAZIONE$8);
            }
            target.setIntValue(nummaxlottipartecipazione);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public void xsetNUMMAXLOTTIPARTECIPAZIONE(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE nummaxlottipartecipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE)get_store().find_attribute_user(NUMMAXLOTTIPARTECIPAZIONE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE)get_store().add_attribute_user(NUMMAXLOTTIPARTECIPAZIONE$8);
            }
            target.set(nummaxlottipartecipazione);
        }
    }
    
    /**
     * Unsets the "NUM_MAX_LOTTI_PARTECIPAZIONE" attribute
     */
    public void unsetNUMMAXLOTTIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMMAXLOTTIPARTECIPAZIONE$8);
        }
    }
    
    /**
     * Gets the "NUM_MAX_LOTTI_OFFERENTE" attribute
     */
    public int getNUMMAXLOTTIOFFERENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMAXLOTTIOFFERENTE$10);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_MAX_LOTTI_OFFERENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE xgetNUMMAXLOTTIOFFERENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE)get_store().find_attribute_user(NUMMAXLOTTIOFFERENTE$10);
            return target;
        }
    }
    
    /**
     * True if has "NUM_MAX_LOTTI_OFFERENTE" attribute
     */
    public boolean isSetNUMMAXLOTTIOFFERENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMMAXLOTTIOFFERENTE$10) != null;
        }
    }
    
    /**
     * Sets the "NUM_MAX_LOTTI_OFFERENTE" attribute
     */
    public void setNUMMAXLOTTIOFFERENTE(int nummaxlottiofferente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMAXLOTTIOFFERENTE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMMAXLOTTIOFFERENTE$10);
            }
            target.setIntValue(nummaxlottiofferente);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_MAX_LOTTI_OFFERENTE" attribute
     */
    public void xsetNUMMAXLOTTIOFFERENTE(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE nummaxlottiofferente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE)get_store().find_attribute_user(NUMMAXLOTTIOFFERENTE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE)get_store().add_attribute_user(NUMMAXLOTTIOFFERENTE$10);
            }
            target.set(nummaxlottiofferente);
        }
    }
    
    /**
     * Unsets the "NUM_MAX_LOTTI_OFFERENTE" attribute
     */
    public void unsetNUMMAXLOTTIOFFERENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMMAXLOTTIOFFERENTE$10);
        }
    }
    
    /**
     * Gets the "FLAG_SA_AGG_GRUPPI_LOTTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSAAGGGRUPPILOTTI$12);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SA_AGG_GRUPPI_LOTTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSAAGGGRUPPILOTTI$12);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_SA_AGG_GRUPPI_LOTTI" attribute
     */
    public boolean isSetFLAGSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGSAAGGGRUPPILOTTI$12) != null;
        }
    }
    
    /**
     * Sets the "FLAG_SA_AGG_GRUPPI_LOTTI" attribute
     */
    public void setFLAGSAAGGGRUPPILOTTI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagsaagggruppilotti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSAAGGGRUPPILOTTI$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGSAAGGGRUPPILOTTI$12);
            }
            target.setEnumValue(flagsaagggruppilotti);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SA_AGG_GRUPPI_LOTTI" attribute
     */
    public void xsetFLAGSAAGGGRUPPILOTTI(it.avlp.simog.massload.xmlbeans.FlagSNType flagsaagggruppilotti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSAAGGGRUPPILOTTI$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGSAAGGGRUPPILOTTI$12);
            }
            target.set(flagsaagggruppilotti);
        }
    }
    
    /**
     * Unsets the "FLAG_SA_AGG_GRUPPI_LOTTI" attribute
     */
    public void unsetFLAGSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGSAAGGGRUPPILOTTI$12);
        }
    }
    
    /**
     * Gets the "SA_AGG_GRUPPI_LOTTI" attribute
     */
    public java.lang.String getSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SAAGGGRUPPILOTTI$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SA_AGG_GRUPPI_LOTTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI xgetSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI)get_store().find_attribute_user(SAAGGGRUPPILOTTI$14);
            return target;
        }
    }
    
    /**
     * True if has "SA_AGG_GRUPPI_LOTTI" attribute
     */
    public boolean isSetSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SAAGGGRUPPILOTTI$14) != null;
        }
    }
    
    /**
     * Sets the "SA_AGG_GRUPPI_LOTTI" attribute
     */
    public void setSAAGGGRUPPILOTTI(java.lang.String saagggruppilotti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SAAGGGRUPPILOTTI$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SAAGGGRUPPILOTTI$14);
            }
            target.setStringValue(saagggruppilotti);
        }
    }
    
    /**
     * Sets (as xml) the "SA_AGG_GRUPPI_LOTTI" attribute
     */
    public void xsetSAAGGGRUPPILOTTI(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI saagggruppilotti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI)get_store().find_attribute_user(SAAGGGRUPPILOTTI$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI)get_store().add_attribute_user(SAAGGGRUPPILOTTI$14);
            }
            target.set(saagggruppilotti);
        }
    }
    
    /**
     * Unsets the "SA_AGG_GRUPPI_LOTTI" attribute
     */
    public void unsetSAAGGGRUPPILOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SAAGGGRUPPILOTTI$14);
        }
    }
    /**
     * An XML TITOLO_PROCEDURA_GARA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.EntitaAppaltoType$TITOLOPROCEDURAGARA.
     */
    public static class TITOLOPROCEDURAGARAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TITOLOPROCEDURAGARA
    {
        
        public TITOLOPROCEDURAGARAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected TITOLOPROCEDURAGARAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CPV_GARA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.EntitaAppaltoType$CPVGARA.
     */
    public static class CPVGARAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.CPVGARA
    {
        
        public CPVGARAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CPVGARAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML TIPO_CONTRATTO_APPALTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.EntitaAppaltoType$TIPOCONTRATTOAPPALTO.
     */
    public static class TIPOCONTRATTOAPPALTOImpl extends org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx implements it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.TIPOCONTRATTOAPPALTO
    {
        
        public TIPOCONTRATTOAPPALTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected TIPOCONTRATTOAPPALTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NUM_MAX_LOTTI_PARTECIPAZIONE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.EntitaAppaltoType$NUMMAXLOTTIPARTECIPAZIONE.
     */
    public static class NUMMAXLOTTIPARTECIPAZIONEImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIPARTECIPAZIONE
    {
        
        public NUMMAXLOTTIPARTECIPAZIONEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NUMMAXLOTTIPARTECIPAZIONEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NUM_MAX_LOTTI_OFFERENTE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.EntitaAppaltoType$NUMMAXLOTTIOFFERENTE.
     */
    public static class NUMMAXLOTTIOFFERENTEImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.NUMMAXLOTTIOFFERENTE
    {
        
        public NUMMAXLOTTIOFFERENTEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NUMMAXLOTTIOFFERENTEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML SA_AGG_GRUPPI_LOTTI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.EntitaAppaltoType$SAAGGGRUPPILOTTI.
     */
    public static class SAAGGGRUPPILOTTIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.EntitaAppaltoType.SAAGGGRUPPILOTTI
    {
        
        public SAAGGGRUPPILOTTIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected SAAGGGRUPPILOTTIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
