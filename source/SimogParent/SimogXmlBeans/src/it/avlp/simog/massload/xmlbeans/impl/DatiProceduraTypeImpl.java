/*
 * XML Type:  DatiProceduraType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiProceduraType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiProceduraType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiProceduraTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiProceduraType
{
    
    public DatiProceduraTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOPROCEDURA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPO_PROCEDURA");
    private static final javax.xml.namespace.QName FLAGPROCEDURAACCELLERATA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_PROCEDURA_ACCELLERATA");
    private static final javax.xml.namespace.QName MOTIVAZIONEPROCEDURAACCELLERATA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MOTIVAZIONE_PROCEDURA_ACCELLERATA");
    private static final javax.xml.namespace.QName TIPOOPERATORIAQ$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPO_OPERATORI_AQ");
    private static final javax.xml.namespace.QName NUMMAXPARTECIPANTIAQ$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_MAX_PARTECIPANTI_AQ");
    private static final javax.xml.namespace.QName ALTRIACQUIRENTISISDINAMICO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ALTRI_ACQUIRENTI_SIS_DINAMICO");
    private static final javax.xml.namespace.QName NOTEAQQUATTROANNI$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NOTE_AQ_QUATTRO_ANNI");
    private static final javax.xml.namespace.QName REDUCTIONRECOURSE$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "REDUCTION_RECOURSE");
    private static final javax.xml.namespace.QName AGGIUDICAZIONESENZANEGOZIAZIONE$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AGGIUDICAZIONE_SENZA_NEGOZIAZIONE");
    private static final javax.xml.namespace.QName NOTEASTAELETTRONICA$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NOTE_ASTA_ELETTRONICA");
    private static final javax.xml.namespace.QName FLAGAPP$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_APP");
    
    
    /**
     * Gets the "TIPO_PROCEDURA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA.Enum getTIPOPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOPROCEDURA$0);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_PROCEDURA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA xgetTIPOPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA)get_store().find_attribute_user(TIPOPROCEDURA$0);
            return target;
        }
    }
    
    /**
     * Sets the "TIPO_PROCEDURA" attribute
     */
    public void setTIPOPROCEDURA(it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA.Enum tipoprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOPROCEDURA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOPROCEDURA$0);
            }
            target.setEnumValue(tipoprocedura);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_PROCEDURA" attribute
     */
    public void xsetTIPOPROCEDURA(it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA tipoprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA)get_store().find_attribute_user(TIPOPROCEDURA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA)get_store().add_attribute_user(TIPOPROCEDURA$0);
            }
            target.set(tipoprocedura);
        }
    }
    
    /**
     * Gets the "FLAG_PROCEDURA_ACCELLERATA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPROCEDURAACCELLERATA$2);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_PROCEDURA_ACCELLERATA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGPROCEDURAACCELLERATA$2);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_PROCEDURA_ACCELLERATA" attribute
     */
    public boolean isSetFLAGPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGPROCEDURAACCELLERATA$2) != null;
        }
    }
    
    /**
     * Sets the "FLAG_PROCEDURA_ACCELLERATA" attribute
     */
    public void setFLAGPROCEDURAACCELLERATA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagproceduraaccellerata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPROCEDURAACCELLERATA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGPROCEDURAACCELLERATA$2);
            }
            target.setEnumValue(flagproceduraaccellerata);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_PROCEDURA_ACCELLERATA" attribute
     */
    public void xsetFLAGPROCEDURAACCELLERATA(it.avlp.simog.massload.xmlbeans.FlagSNType flagproceduraaccellerata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGPROCEDURAACCELLERATA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGPROCEDURAACCELLERATA$2);
            }
            target.set(flagproceduraaccellerata);
        }
    }
    
    /**
     * Unsets the "FLAG_PROCEDURA_ACCELLERATA" attribute
     */
    public void unsetFLAGPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGPROCEDURAACCELLERATA$2);
        }
    }
    
    /**
     * Gets the "MOTIVAZIONE_PROCEDURA_ACCELLERATA" attribute
     */
    public java.lang.String getMOTIVAZIONEPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVAZIONEPROCEDURAACCELLERATA$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVAZIONE_PROCEDURA_ACCELLERATA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA xgetMOTIVAZIONEPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA)get_store().find_attribute_user(MOTIVAZIONEPROCEDURAACCELLERATA$4);
            return target;
        }
    }
    
    /**
     * True if has "MOTIVAZIONE_PROCEDURA_ACCELLERATA" attribute
     */
    public boolean isSetMOTIVAZIONEPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MOTIVAZIONEPROCEDURAACCELLERATA$4) != null;
        }
    }
    
    /**
     * Sets the "MOTIVAZIONE_PROCEDURA_ACCELLERATA" attribute
     */
    public void setMOTIVAZIONEPROCEDURAACCELLERATA(java.lang.String motivazioneproceduraaccellerata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVAZIONEPROCEDURAACCELLERATA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOTIVAZIONEPROCEDURAACCELLERATA$4);
            }
            target.setStringValue(motivazioneproceduraaccellerata);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVAZIONE_PROCEDURA_ACCELLERATA" attribute
     */
    public void xsetMOTIVAZIONEPROCEDURAACCELLERATA(it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA motivazioneproceduraaccellerata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA)get_store().find_attribute_user(MOTIVAZIONEPROCEDURAACCELLERATA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA)get_store().add_attribute_user(MOTIVAZIONEPROCEDURAACCELLERATA$4);
            }
            target.set(motivazioneproceduraaccellerata);
        }
    }
    
    /**
     * Unsets the "MOTIVAZIONE_PROCEDURA_ACCELLERATA" attribute
     */
    public void unsetMOTIVAZIONEPROCEDURAACCELLERATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MOTIVAZIONEPROCEDURAACCELLERATA$4);
        }
    }
    
    /**
     * Gets the "TIPO_OPERATORI_AQ" attribute
     */
    public it.avlp.simog.massload.xmlbeans.OperatoriAQType.Enum getTIPOOPERATORIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOOPERATORIAQ$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.OperatoriAQType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_OPERATORI_AQ" attribute
     */
    public it.avlp.simog.massload.xmlbeans.OperatoriAQType xgetTIPOOPERATORIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.OperatoriAQType target = null;
            target = (it.avlp.simog.massload.xmlbeans.OperatoriAQType)get_store().find_attribute_user(TIPOOPERATORIAQ$6);
            return target;
        }
    }
    
    /**
     * True if has "TIPO_OPERATORI_AQ" attribute
     */
    public boolean isSetTIPOOPERATORIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TIPOOPERATORIAQ$6) != null;
        }
    }
    
    /**
     * Sets the "TIPO_OPERATORI_AQ" attribute
     */
    public void setTIPOOPERATORIAQ(it.avlp.simog.massload.xmlbeans.OperatoriAQType.Enum tipooperatoriaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOOPERATORIAQ$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOOPERATORIAQ$6);
            }
            target.setEnumValue(tipooperatoriaq);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_OPERATORI_AQ" attribute
     */
    public void xsetTIPOOPERATORIAQ(it.avlp.simog.massload.xmlbeans.OperatoriAQType tipooperatoriaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.OperatoriAQType target = null;
            target = (it.avlp.simog.massload.xmlbeans.OperatoriAQType)get_store().find_attribute_user(TIPOOPERATORIAQ$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.OperatoriAQType)get_store().add_attribute_user(TIPOOPERATORIAQ$6);
            }
            target.set(tipooperatoriaq);
        }
    }
    
    /**
     * Unsets the "TIPO_OPERATORI_AQ" attribute
     */
    public void unsetTIPOOPERATORIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TIPOOPERATORIAQ$6);
        }
    }
    
    /**
     * Gets the "NUM_MAX_PARTECIPANTI_AQ" attribute
     */
    public int getNUMMAXPARTECIPANTIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMAXPARTECIPANTIAQ$8);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_MAX_PARTECIPANTI_AQ" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ xgetNUMMAXPARTECIPANTIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ)get_store().find_attribute_user(NUMMAXPARTECIPANTIAQ$8);
            return target;
        }
    }
    
    /**
     * True if has "NUM_MAX_PARTECIPANTI_AQ" attribute
     */
    public boolean isSetNUMMAXPARTECIPANTIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMMAXPARTECIPANTIAQ$8) != null;
        }
    }
    
    /**
     * Sets the "NUM_MAX_PARTECIPANTI_AQ" attribute
     */
    public void setNUMMAXPARTECIPANTIAQ(int nummaxpartecipantiaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMAXPARTECIPANTIAQ$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMMAXPARTECIPANTIAQ$8);
            }
            target.setIntValue(nummaxpartecipantiaq);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_MAX_PARTECIPANTI_AQ" attribute
     */
    public void xsetNUMMAXPARTECIPANTIAQ(it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ nummaxpartecipantiaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ)get_store().find_attribute_user(NUMMAXPARTECIPANTIAQ$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ)get_store().add_attribute_user(NUMMAXPARTECIPANTIAQ$8);
            }
            target.set(nummaxpartecipantiaq);
        }
    }
    
    /**
     * Unsets the "NUM_MAX_PARTECIPANTI_AQ" attribute
     */
    public void unsetNUMMAXPARTECIPANTIAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMMAXPARTECIPANTIAQ$8);
        }
    }
    
    /**
     * Gets the "ALTRI_ACQUIRENTI_SIS_DINAMICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getALTRIACQUIRENTISISDINAMICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTRIACQUIRENTISISDINAMICO$10);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ALTRI_ACQUIRENTI_SIS_DINAMICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetALTRIACQUIRENTISISDINAMICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ALTRIACQUIRENTISISDINAMICO$10);
            return target;
        }
    }
    
    /**
     * True if has "ALTRI_ACQUIRENTI_SIS_DINAMICO" attribute
     */
    public boolean isSetALTRIACQUIRENTISISDINAMICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ALTRIACQUIRENTISISDINAMICO$10) != null;
        }
    }
    
    /**
     * Sets the "ALTRI_ACQUIRENTI_SIS_DINAMICO" attribute
     */
    public void setALTRIACQUIRENTISISDINAMICO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum altriacquirentisisdinamico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTRIACQUIRENTISISDINAMICO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ALTRIACQUIRENTISISDINAMICO$10);
            }
            target.setEnumValue(altriacquirentisisdinamico);
        }
    }
    
    /**
     * Sets (as xml) the "ALTRI_ACQUIRENTI_SIS_DINAMICO" attribute
     */
    public void xsetALTRIACQUIRENTISISDINAMICO(it.avlp.simog.massload.xmlbeans.FlagSNType altriacquirentisisdinamico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ALTRIACQUIRENTISISDINAMICO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(ALTRIACQUIRENTISISDINAMICO$10);
            }
            target.set(altriacquirentisisdinamico);
        }
    }
    
    /**
     * Unsets the "ALTRI_ACQUIRENTI_SIS_DINAMICO" attribute
     */
    public void unsetALTRIACQUIRENTISISDINAMICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ALTRIACQUIRENTISISDINAMICO$10);
        }
    }
    
    /**
     * Gets the "NOTE_AQ_QUATTRO_ANNI" attribute
     */
    public java.lang.String getNOTEAQQUATTROANNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOTEAQQUATTROANNI$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NOTE_AQ_QUATTRO_ANNI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI xgetNOTEAQQUATTROANNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI)get_store().find_attribute_user(NOTEAQQUATTROANNI$12);
            return target;
        }
    }
    
    /**
     * True if has "NOTE_AQ_QUATTRO_ANNI" attribute
     */
    public boolean isSetNOTEAQQUATTROANNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NOTEAQQUATTROANNI$12) != null;
        }
    }
    
    /**
     * Sets the "NOTE_AQ_QUATTRO_ANNI" attribute
     */
    public void setNOTEAQQUATTROANNI(java.lang.String noteaqquattroanni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOTEAQQUATTROANNI$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NOTEAQQUATTROANNI$12);
            }
            target.setStringValue(noteaqquattroanni);
        }
    }
    
    /**
     * Sets (as xml) the "NOTE_AQ_QUATTRO_ANNI" attribute
     */
    public void xsetNOTEAQQUATTROANNI(it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI noteaqquattroanni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI)get_store().find_attribute_user(NOTEAQQUATTROANNI$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI)get_store().add_attribute_user(NOTEAQQUATTROANNI$12);
            }
            target.set(noteaqquattroanni);
        }
    }
    
    /**
     * Unsets the "NOTE_AQ_QUATTRO_ANNI" attribute
     */
    public void unsetNOTEAQQUATTROANNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NOTEAQQUATTROANNI$12);
        }
    }
    
    /**
     * Gets the "REDUCTION_RECOURSE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getREDUCTIONRECOURSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REDUCTIONRECOURSE$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "REDUCTION_RECOURSE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetREDUCTIONRECOURSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(REDUCTIONRECOURSE$14);
            return target;
        }
    }
    
    /**
     * True if has "REDUCTION_RECOURSE" attribute
     */
    public boolean isSetREDUCTIONRECOURSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(REDUCTIONRECOURSE$14) != null;
        }
    }
    
    /**
     * Sets the "REDUCTION_RECOURSE" attribute
     */
    public void setREDUCTIONRECOURSE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum reductionrecourse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REDUCTIONRECOURSE$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(REDUCTIONRECOURSE$14);
            }
            target.setEnumValue(reductionrecourse);
        }
    }
    
    /**
     * Sets (as xml) the "REDUCTION_RECOURSE" attribute
     */
    public void xsetREDUCTIONRECOURSE(it.avlp.simog.massload.xmlbeans.FlagSNType reductionrecourse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(REDUCTIONRECOURSE$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(REDUCTIONRECOURSE$14);
            }
            target.set(reductionrecourse);
        }
    }
    
    /**
     * Unsets the "REDUCTION_RECOURSE" attribute
     */
    public void unsetREDUCTIONRECOURSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(REDUCTIONRECOURSE$14);
        }
    }
    
    /**
     * Gets the "AGGIUDICAZIONE_SENZA_NEGOZIAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getAGGIUDICAZIONESENZANEGOZIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AGGIUDICAZIONESENZANEGOZIAZIONE$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "AGGIUDICAZIONE_SENZA_NEGOZIAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetAGGIUDICAZIONESENZANEGOZIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(AGGIUDICAZIONESENZANEGOZIAZIONE$16);
            return target;
        }
    }
    
    /**
     * True if has "AGGIUDICAZIONE_SENZA_NEGOZIAZIONE" attribute
     */
    public boolean isSetAGGIUDICAZIONESENZANEGOZIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(AGGIUDICAZIONESENZANEGOZIAZIONE$16) != null;
        }
    }
    
    /**
     * Sets the "AGGIUDICAZIONE_SENZA_NEGOZIAZIONE" attribute
     */
    public void setAGGIUDICAZIONESENZANEGOZIAZIONE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum aggiudicazionesenzanegoziazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AGGIUDICAZIONESENZANEGOZIAZIONE$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(AGGIUDICAZIONESENZANEGOZIAZIONE$16);
            }
            target.setEnumValue(aggiudicazionesenzanegoziazione);
        }
    }
    
    /**
     * Sets (as xml) the "AGGIUDICAZIONE_SENZA_NEGOZIAZIONE" attribute
     */
    public void xsetAGGIUDICAZIONESENZANEGOZIAZIONE(it.avlp.simog.massload.xmlbeans.FlagSNType aggiudicazionesenzanegoziazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(AGGIUDICAZIONESENZANEGOZIAZIONE$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(AGGIUDICAZIONESENZANEGOZIAZIONE$16);
            }
            target.set(aggiudicazionesenzanegoziazione);
        }
    }
    
    /**
     * Unsets the "AGGIUDICAZIONE_SENZA_NEGOZIAZIONE" attribute
     */
    public void unsetAGGIUDICAZIONESENZANEGOZIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(AGGIUDICAZIONESENZANEGOZIAZIONE$16);
        }
    }
    
    /**
     * Gets the "NOTE_ASTA_ELETTRONICA" attribute
     */
    public java.lang.String getNOTEASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOTEASTAELETTRONICA$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NOTE_ASTA_ELETTRONICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA xgetNOTEASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA)get_store().find_attribute_user(NOTEASTAELETTRONICA$18);
            return target;
        }
    }
    
    /**
     * True if has "NOTE_ASTA_ELETTRONICA" attribute
     */
    public boolean isSetNOTEASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NOTEASTAELETTRONICA$18) != null;
        }
    }
    
    /**
     * Sets the "NOTE_ASTA_ELETTRONICA" attribute
     */
    public void setNOTEASTAELETTRONICA(java.lang.String noteastaelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOTEASTAELETTRONICA$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NOTEASTAELETTRONICA$18);
            }
            target.setStringValue(noteastaelettronica);
        }
    }
    
    /**
     * Sets (as xml) the "NOTE_ASTA_ELETTRONICA" attribute
     */
    public void xsetNOTEASTAELETTRONICA(it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA noteastaelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA)get_store().find_attribute_user(NOTEASTAELETTRONICA$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA)get_store().add_attribute_user(NOTEASTAELETTRONICA$18);
            }
            target.set(noteastaelettronica);
        }
    }
    
    /**
     * Unsets the "NOTE_ASTA_ELETTRONICA" attribute
     */
    public void unsetNOTEASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NOTEASTAELETTRONICA$18);
        }
    }
    
    /**
     * Gets the "FLAG_APP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGAPP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAPP$20);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_APP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGAPP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGAPP$20);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_APP" attribute
     */
    public boolean isSetFLAGAPP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGAPP$20) != null;
        }
    }
    
    /**
     * Sets the "FLAG_APP" attribute
     */
    public void setFLAGAPP(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagapp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAPP$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGAPP$20);
            }
            target.setEnumValue(flagapp);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_APP" attribute
     */
    public void xsetFLAGAPP(it.avlp.simog.massload.xmlbeans.FlagSNType flagapp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGAPP$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGAPP$20);
            }
            target.set(flagapp);
        }
    }
    
    /**
     * Unsets the "FLAG_APP" attribute
     */
    public void unsetFLAGAPP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGAPP$20);
        }
    }
    /**
     * An XML TIPO_PROCEDURA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiProceduraType$TIPOPROCEDURA.
     */
    public static class TIPOPROCEDURAImpl extends org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx implements it.avlp.simog.massload.xmlbeans.DatiProceduraType.TIPOPROCEDURA
    {
        
        public TIPOPROCEDURAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected TIPOPROCEDURAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML MOTIVAZIONE_PROCEDURA_ACCELLERATA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiProceduraType$MOTIVAZIONEPROCEDURAACCELLERATA.
     */
    public static class MOTIVAZIONEPROCEDURAACCELLERATAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiProceduraType.MOTIVAZIONEPROCEDURAACCELLERATA
    {
        
        public MOTIVAZIONEPROCEDURAACCELLERATAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MOTIVAZIONEPROCEDURAACCELLERATAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NUM_MAX_PARTECIPANTI_AQ(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiProceduraType$NUMMAXPARTECIPANTIAQ.
     */
    public static class NUMMAXPARTECIPANTIAQImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.DatiProceduraType.NUMMAXPARTECIPANTIAQ
    {
        
        public NUMMAXPARTECIPANTIAQImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NUMMAXPARTECIPANTIAQImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NOTE_AQ_QUATTRO_ANNI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiProceduraType$NOTEAQQUATTROANNI.
     */
    public static class NOTEAQQUATTROANNIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEAQQUATTROANNI
    {
        
        public NOTEAQQUATTROANNIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NOTEAQQUATTROANNIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NOTE_ASTA_ELETTRONICA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiProceduraType$NOTEASTAELETTRONICA.
     */
    public static class NOTEASTAELETTRONICAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiProceduraType.NOTEASTAELETTRONICA
    {
        
        public NOTEASTAELETTRONICAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NOTEASTAELETTRONICAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
