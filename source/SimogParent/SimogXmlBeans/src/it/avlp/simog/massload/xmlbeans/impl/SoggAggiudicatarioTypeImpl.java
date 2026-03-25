/*
 * XML Type:  SoggAggiudicatarioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SoggAggiudicatarioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SoggAggiudicatarioTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType
{
    
    public SoggAggiudicatarioTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDTIPOAGG$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_TIPOAGG");
    private static final javax.xml.namespace.QName RUOLO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "RUOLO");
    private static final javax.xml.namespace.QName FLAGAVVALIMENTO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_AVVALIMENTO");
    private static final javax.xml.namespace.QName CFAUSILIARIA$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_AUSILIARIA");
    private static final javax.xml.namespace.QName CODICEFISCALEAGGIUDICATARIO$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_AGGIUDICATARIO");
    private static final javax.xml.namespace.QName CODICESTATO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_STATO");
    private static final javax.xml.namespace.QName IDGRUPPO$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_GRUPPO");
    private static final javax.xml.namespace.QName PERCRIBASSOAGG$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERC_RIBASSO_AGG");
    private static final javax.xml.namespace.QName PERCOFFAUMENTO$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERC_OFF_AUMENTO");
    private static final javax.xml.namespace.QName IMPORTOAGGIUDICAZIONE$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_AGGIUDICAZIONE");
    
    
    /**
     * Gets the "ID_TIPOAGG" attribute
     */
    public java.lang.String getIDTIPOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDTIPOAGG$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_TIPOAGG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipoAggiudicatarioType xgetIDTIPOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoAggiudicatarioType)get_store().find_attribute_user(IDTIPOAGG$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_TIPOAGG" attribute
     */
    public void setIDTIPOAGG(java.lang.String idtipoagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDTIPOAGG$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDTIPOAGG$0);
            }
            target.setStringValue(idtipoagg);
        }
    }
    
    /**
     * Sets (as xml) the "ID_TIPOAGG" attribute
     */
    public void xsetIDTIPOAGG(it.avlp.simog.massload.xmlbeans.TipoAggiudicatarioType idtipoagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoAggiudicatarioType)get_store().find_attribute_user(IDTIPOAGG$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipoAggiudicatarioType)get_store().add_attribute_user(IDTIPOAGG$0);
            }
            target.set(idtipoagg);
        }
    }
    
    /**
     * Gets the "RUOLO" attribute
     */
    public java.lang.String getRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RUOLO$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "RUOLO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RuoloAggiudicatarioType xgetRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RuoloAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RuoloAggiudicatarioType)get_store().find_attribute_user(RUOLO$2);
            return target;
        }
    }
    
    /**
     * True if has "RUOLO" attribute
     */
    public boolean isSetRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(RUOLO$2) != null;
        }
    }
    
    /**
     * Sets the "RUOLO" attribute
     */
    public void setRUOLO(java.lang.String ruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RUOLO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(RUOLO$2);
            }
            target.setStringValue(ruolo);
        }
    }
    
    /**
     * Sets (as xml) the "RUOLO" attribute
     */
    public void xsetRUOLO(it.avlp.simog.massload.xmlbeans.RuoloAggiudicatarioType ruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RuoloAggiudicatarioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RuoloAggiudicatarioType)get_store().find_attribute_user(RUOLO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RuoloAggiudicatarioType)get_store().add_attribute_user(RUOLO$2);
            }
            target.set(ruolo);
        }
    }
    
    /**
     * Unsets the "RUOLO" attribute
     */
    public void unsetRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(RUOLO$2);
        }
    }
    
    /**
     * Gets the "FLAG_AVVALIMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum getFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAVVALIMENTO$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_AVVALIMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType xgetFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType)get_store().find_attribute_user(FLAGAVVALIMENTO$4);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_AVVALIMENTO" attribute
     */
    public void setFLAGAVVALIMENTO(it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum flagavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAVVALIMENTO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGAVVALIMENTO$4);
            }
            target.setEnumValue(flagavvalimento);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_AVVALIMENTO" attribute
     */
    public void xsetFLAGAVVALIMENTO(it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType flagavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType)get_store().find_attribute_user(FLAGAVVALIMENTO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType)get_store().add_attribute_user(FLAGAVVALIMENTO$4);
            }
            target.set(flagavvalimento);
        }
    }
    
    /**
     * Gets the "CF_AUSILIARIA" attribute
     */
    public java.lang.String getCFAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAUSILIARIA$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_AUSILIARIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAUSILIARIA$6);
            return target;
        }
    }
    
    /**
     * True if has "CF_AUSILIARIA" attribute
     */
    public boolean isSetCFAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CFAUSILIARIA$6) != null;
        }
    }
    
    /**
     * Sets the "CF_AUSILIARIA" attribute
     */
    public void setCFAUSILIARIA(java.lang.String cfausiliaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAUSILIARIA$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFAUSILIARIA$6);
            }
            target.setStringValue(cfausiliaria);
        }
    }
    
    /**
     * Sets (as xml) the "CF_AUSILIARIA" attribute
     */
    public void xsetCFAUSILIARIA(it.avlp.simog.massload.xmlbeans.CodFiscType cfausiliaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAUSILIARIA$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFAUSILIARIA$6);
            }
            target.set(cfausiliaria);
        }
    }
    
    /**
     * Unsets the "CF_AUSILIARIA" attribute
     */
    public void unsetCFAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CFAUSILIARIA$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$8);
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
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$8);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$8);
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
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$8);
            }
            target.set(codicefiscaleaggiudicatario);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$10);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$10);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICESTATO$10);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().add_attribute_user(CODICESTATO$10);
            }
            target.set(codicestato);
        }
    }
    
    /**
     * Gets the "ID_GRUPPO" attribute
     */
    public int getIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGRUPPO$12);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_GRUPPO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType99 xgetIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType99 target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType99)get_store().find_attribute_user(IDGRUPPO$12);
            return target;
        }
    }
    
    /**
     * True if has "ID_GRUPPO" attribute
     */
    public boolean isSetIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDGRUPPO$12) != null;
        }
    }
    
    /**
     * Sets the "ID_GRUPPO" attribute
     */
    public void setIDGRUPPO(int idgruppo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGRUPPO$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDGRUPPO$12);
            }
            target.setIntValue(idgruppo);
        }
    }
    
    /**
     * Sets (as xml) the "ID_GRUPPO" attribute
     */
    public void xsetIDGRUPPO(it.avlp.simog.massload.xmlbeans.InteroType99 idgruppo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType99 target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType99)get_store().find_attribute_user(IDGRUPPO$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType99)get_store().add_attribute_user(IDGRUPPO$12);
            }
            target.set(idgruppo);
        }
    }
    
    /**
     * Unsets the "ID_GRUPPO" attribute
     */
    public void unsetIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDGRUPPO$12);
        }
    }
    
    /**
     * Gets the "PERC_RIBASSO_AGG" attribute
     */
    public java.math.BigDecimal getPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCRIBASSOAGG$14);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERC_RIBASSO_AGG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PercentualeType xgetPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCRIBASSOAGG$14);
            return target;
        }
    }
    
    /**
     * True if has "PERC_RIBASSO_AGG" attribute
     */
    public boolean isSetPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PERCRIBASSOAGG$14) != null;
        }
    }
    
    /**
     * Sets the "PERC_RIBASSO_AGG" attribute
     */
    public void setPERCRIBASSOAGG(java.math.BigDecimal percribassoagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCRIBASSOAGG$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERCRIBASSOAGG$14);
            }
            target.setBigDecimalValue(percribassoagg);
        }
    }
    
    /**
     * Sets (as xml) the "PERC_RIBASSO_AGG" attribute
     */
    public void xsetPERCRIBASSOAGG(it.avlp.simog.massload.xmlbeans.PercentualeType percribassoagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCRIBASSOAGG$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(PERCRIBASSOAGG$14);
            }
            target.set(percribassoagg);
        }
    }
    
    /**
     * Unsets the "PERC_RIBASSO_AGG" attribute
     */
    public void unsetPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PERCRIBASSOAGG$14);
        }
    }
    
    /**
     * Gets the "PERC_OFF_AUMENTO" attribute
     */
    public java.math.BigDecimal getPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCOFFAUMENTO$16);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERC_OFF_AUMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PercentualeType xgetPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCOFFAUMENTO$16);
            return target;
        }
    }
    
    /**
     * True if has "PERC_OFF_AUMENTO" attribute
     */
    public boolean isSetPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PERCOFFAUMENTO$16) != null;
        }
    }
    
    /**
     * Sets the "PERC_OFF_AUMENTO" attribute
     */
    public void setPERCOFFAUMENTO(java.math.BigDecimal percoffaumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCOFFAUMENTO$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERCOFFAUMENTO$16);
            }
            target.setBigDecimalValue(percoffaumento);
        }
    }
    
    /**
     * Sets (as xml) the "PERC_OFF_AUMENTO" attribute
     */
    public void xsetPERCOFFAUMENTO(it.avlp.simog.massload.xmlbeans.PercentualeType percoffaumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCOFFAUMENTO$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(PERCOFFAUMENTO$16);
            }
            target.set(percoffaumento);
        }
    }
    
    /**
     * Unsets the "PERC_OFF_AUMENTO" attribute
     */
    public void unsetPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PERCOFFAUMENTO$16);
        }
    }
    
    /**
     * Gets the "IMPORTO_AGGIUDICAZIONE" attribute
     */
    public java.math.BigDecimal getIMPORTOAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$18);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_AGGIUDICAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$18);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_AGGIUDICAZIONE" attribute
     */
    public boolean isSetIMPORTOAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$18) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_AGGIUDICAZIONE" attribute
     */
    public void setIMPORTOAGGIUDICAZIONE(java.math.BigDecimal importoaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOAGGIUDICAZIONE$18);
            }
            target.setBigDecimalValue(importoaggiudicazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_AGGIUDICAZIONE" attribute
     */
    public void xsetIMPORTOAGGIUDICAZIONE(it.avlp.simog.massload.xmlbeans.ImportoType importoaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOAGGIUDICAZIONE$18);
            }
            target.set(importoaggiudicazione);
        }
    }
    
    /**
     * Unsets the "IMPORTO_AGGIUDICAZIONE" attribute
     */
    public void unsetIMPORTOAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOAGGIUDICAZIONE$18);
        }
    }
}
