/*
 * XML Type:  DatiComuniType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiComuniType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiComuniType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiComuniTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiComuniType
{
    
    public DatiComuniTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CIG$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG");
    private static final javax.xml.namespace.QName FLAGENTESPECIALE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_ENTE_SPECIALE");
    private static final javax.xml.namespace.QName TIPOCONTRATTO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPO_CONTRATTO");
    private static final javax.xml.namespace.QName IDCATEGSA$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_CATEG_SA");
    private static final javax.xml.namespace.QName CFAMMAGENTE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_AMM_AGENTE");
    private static final javax.xml.namespace.QName DENAMMAGENTE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DEN_AMM_AGENTE");
    private static final javax.xml.namespace.QName CFAMM$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_AMM");
    private static final javax.xml.namespace.QName DENAMM$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DEN_AMM");
    private static final javax.xml.namespace.QName CFSA$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_SA");
    private static final javax.xml.namespace.QName DENSA$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DEN_SA");
    private static final javax.xml.namespace.QName CODICECC$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_CC");
    private static final javax.xml.namespace.QName DENOMCC$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DENOM_CC");
    private static final javax.xml.namespace.QName IDTIPOLOGIASA$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_TIPOLOGIA_SA");
    private static final javax.xml.namespace.QName FLAGSAAGENTE$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_SA_AGENTE");
    private static final javax.xml.namespace.QName CFRUP$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_RUP");
    private static final javax.xml.namespace.QName ESITOPROCEDURA$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ESITO_PROCEDURA");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$34 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName TIPOLOGIAPROCEDURA$36 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPOLOGIA_PROCEDURA");
    private static final javax.xml.namespace.QName FLAGACCQUADRO$38 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_ACC_QUADRO");
    private static final javax.xml.namespace.QName DURATAACCQUADROCONVENZIONE$40 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DURATA_ACCQUADRO_CONVENZIONE");
    private static final javax.xml.namespace.QName FLAGCENTRALESTIPULA$42 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_CENTRALE_STIPULA");
    private static final javax.xml.namespace.QName MODOREALIZZAZIONE$44 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MODO_REALIZZAZIONE");
    private static final javax.xml.namespace.QName FLAGESCLUSO$46 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_ESCLUSO");
    private static final javax.xml.namespace.QName IDESCLUSIONE$48 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_ESCLUSIONE");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$50 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    private static final javax.xml.namespace.QName ORIGINESCHEDA$52 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ORIGINE_SCHEDA");
    private static final javax.xml.namespace.QName PROVVPRESACARICO$54 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PROVV_PRESA_CARICO");
    
    
    /**
     * Gets the "CIG" attribute
     */
    public java.lang.String getCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$0);
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
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIG$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIG$0);
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
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIG$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIG$0);
            }
            target.set(cig);
        }
    }
    
    /**
     * Gets the "FLAG_ENTE_SPECIALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSOType.Enum getFLAGENTESPECIALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGENTESPECIALE$2);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSOType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ENTE_SPECIALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSOType xgetFLAGENTESPECIALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSOType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSOType)get_store().find_attribute_user(FLAGENTESPECIALE$2);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_ENTE_SPECIALE" attribute
     */
    public void setFLAGENTESPECIALE(it.avlp.simog.massload.xmlbeans.FlagSOType.Enum flagentespeciale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGENTESPECIALE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGENTESPECIALE$2);
            }
            target.setEnumValue(flagentespeciale);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ENTE_SPECIALE" attribute
     */
    public void xsetFLAGENTESPECIALE(it.avlp.simog.massload.xmlbeans.FlagSOType flagentespeciale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSOType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSOType)get_store().find_attribute_user(FLAGENTESPECIALE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSOType)get_store().add_attribute_user(FLAGENTESPECIALE$2);
            }
            target.set(flagentespeciale);
        }
    }
    
    /**
     * Gets the "TIPO_CONTRATTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipoSchedaType.Enum getTIPOCONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCONTRATTO$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.TipoSchedaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_CONTRATTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipoSchedaType xgetTIPOCONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoSchedaType)get_store().find_attribute_user(TIPOCONTRATTO$4);
            return target;
        }
    }
    
    /**
     * Sets the "TIPO_CONTRATTO" attribute
     */
    public void setTIPOCONTRATTO(it.avlp.simog.massload.xmlbeans.TipoSchedaType.Enum tipocontratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOCONTRATTO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOCONTRATTO$4);
            }
            target.setEnumValue(tipocontratto);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_CONTRATTO" attribute
     */
    public void xsetTIPOCONTRATTO(it.avlp.simog.massload.xmlbeans.TipoSchedaType tipocontratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoSchedaType)get_store().find_attribute_user(TIPOCONTRATTO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipoSchedaType)get_store().add_attribute_user(TIPOCONTRATTO$4);
            }
            target.set(tipocontratto);
        }
    }
    
    /**
     * Gets the "ID_CATEG_SA" attribute
     */
    public java.lang.String getIDCATEGSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCATEGSA$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CATEG_SA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CategSAType xgetIDCATEGSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategSAType)get_store().find_attribute_user(IDCATEGSA$6);
            return target;
        }
    }
    
    /**
     * True if has "ID_CATEG_SA" attribute
     */
    public boolean isSetIDCATEGSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDCATEGSA$6) != null;
        }
    }
    
    /**
     * Sets the "ID_CATEG_SA" attribute
     */
    public void setIDCATEGSA(java.lang.String idcategsa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCATEGSA$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDCATEGSA$6);
            }
            target.setStringValue(idcategsa);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CATEG_SA" attribute
     */
    public void xsetIDCATEGSA(it.avlp.simog.massload.xmlbeans.CategSAType idcategsa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategSAType)get_store().find_attribute_user(IDCATEGSA$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CategSAType)get_store().add_attribute_user(IDCATEGSA$6);
            }
            target.set(idcategsa);
        }
    }
    
    /**
     * Unsets the "ID_CATEG_SA" attribute
     */
    public void unsetIDCATEGSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDCATEGSA$6);
        }
    }
    
    /**
     * Gets the "CF_AMM_AGENTE" attribute
     */
    public java.lang.String getCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAMMAGENTE$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_AMM_AGENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAMMAGENTE$8);
            return target;
        }
    }
    
    /**
     * True if has "CF_AMM_AGENTE" attribute
     */
    public boolean isSetCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CFAMMAGENTE$8) != null;
        }
    }
    
    /**
     * Sets the "CF_AMM_AGENTE" attribute
     */
    public void setCFAMMAGENTE(java.lang.String cfammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAMMAGENTE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFAMMAGENTE$8);
            }
            target.setStringValue(cfammagente);
        }
    }
    
    /**
     * Sets (as xml) the "CF_AMM_AGENTE" attribute
     */
    public void xsetCFAMMAGENTE(it.avlp.simog.massload.xmlbeans.CodFiscType cfammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAMMAGENTE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFAMMAGENTE$8);
            }
            target.set(cfammagente);
        }
    }
    
    /**
     * Unsets the "CF_AMM_AGENTE" attribute
     */
    public void unsetCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CFAMMAGENTE$8);
        }
    }
    
    /**
     * Gets the "DEN_AMM_AGENTE" attribute
     */
    public java.lang.String getDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENAMMAGENTE$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DEN_AMM_AGENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE xgetDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE)get_store().find_attribute_user(DENAMMAGENTE$10);
            return target;
        }
    }
    
    /**
     * True if has "DEN_AMM_AGENTE" attribute
     */
    public boolean isSetDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DENAMMAGENTE$10) != null;
        }
    }
    
    /**
     * Sets the "DEN_AMM_AGENTE" attribute
     */
    public void setDENAMMAGENTE(java.lang.String denammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENAMMAGENTE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DENAMMAGENTE$10);
            }
            target.setStringValue(denammagente);
        }
    }
    
    /**
     * Sets (as xml) the "DEN_AMM_AGENTE" attribute
     */
    public void xsetDENAMMAGENTE(it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE denammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE)get_store().find_attribute_user(DENAMMAGENTE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE)get_store().add_attribute_user(DENAMMAGENTE$10);
            }
            target.set(denammagente);
        }
    }
    
    /**
     * Unsets the "DEN_AMM_AGENTE" attribute
     */
    public void unsetDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DENAMMAGENTE$10);
        }
    }
    
    /**
     * Gets the "CF_AMM" attribute
     */
    public java.lang.String getCFAMM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAMM$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_AMM" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFAMM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAMM$12);
            return target;
        }
    }
    
    /**
     * Sets the "CF_AMM" attribute
     */
    public void setCFAMM(java.lang.String cfamm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAMM$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFAMM$12);
            }
            target.setStringValue(cfamm);
        }
    }
    
    /**
     * Sets (as xml) the "CF_AMM" attribute
     */
    public void xsetCFAMM(it.avlp.simog.massload.xmlbeans.CodFiscType cfamm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAMM$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFAMM$12);
            }
            target.set(cfamm);
        }
    }
    
    /**
     * Gets the "DEN_AMM" attribute
     */
    public java.lang.String getDENAMM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENAMM$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DEN_AMM" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM xgetDENAMM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM)get_store().find_attribute_user(DENAMM$14);
            return target;
        }
    }
    
    /**
     * Sets the "DEN_AMM" attribute
     */
    public void setDENAMM(java.lang.String denamm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENAMM$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DENAMM$14);
            }
            target.setStringValue(denamm);
        }
    }
    
    /**
     * Sets (as xml) the "DEN_AMM" attribute
     */
    public void xsetDENAMM(it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM denamm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM)get_store().find_attribute_user(DENAMM$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM)get_store().add_attribute_user(DENAMM$14);
            }
            target.set(denamm);
        }
    }
    
    /**
     * Gets the "CF_SA" attribute
     */
    public java.lang.String getCFSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFSA$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_SA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFSA$16);
            return target;
        }
    }
    
    /**
     * Sets the "CF_SA" attribute
     */
    public void setCFSA(java.lang.String cfsa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFSA$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFSA$16);
            }
            target.setStringValue(cfsa);
        }
    }
    
    /**
     * Sets (as xml) the "CF_SA" attribute
     */
    public void xsetCFSA(it.avlp.simog.massload.xmlbeans.CodFiscType cfsa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFSA$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFSA$16);
            }
            target.set(cfsa);
        }
    }
    
    /**
     * Gets the "DEN_SA" attribute
     */
    public java.lang.String getDENSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENSA$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DEN_SA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA xgetDENSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA)get_store().find_attribute_user(DENSA$18);
            return target;
        }
    }
    
    /**
     * Sets the "DEN_SA" attribute
     */
    public void setDENSA(java.lang.String densa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENSA$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DENSA$18);
            }
            target.setStringValue(densa);
        }
    }
    
    /**
     * Sets (as xml) the "DEN_SA" attribute
     */
    public void xsetDENSA(it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA densa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA)get_store().find_attribute_user(DENSA$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA)get_store().add_attribute_user(DENSA$18);
            }
            target.set(densa);
        }
    }
    
    /**
     * Gets the "CODICE_CC" attribute
     */
    public java.lang.String getCODICECC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICECC$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_CC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC xgetCODICECC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC)get_store().find_attribute_user(CODICECC$20);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_CC" attribute
     */
    public void setCODICECC(java.lang.String codicecc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICECC$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICECC$20);
            }
            target.setStringValue(codicecc);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_CC" attribute
     */
    public void xsetCODICECC(it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC codicecc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC)get_store().find_attribute_user(CODICECC$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC)get_store().add_attribute_user(CODICECC$20);
            }
            target.set(codicecc);
        }
    }
    
    /**
     * Gets the "DENOM_CC" attribute
     */
    public java.lang.String getDENOMCC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENOMCC$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DENOM_CC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC xgetDENOMCC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC)get_store().find_attribute_user(DENOMCC$22);
            return target;
        }
    }
    
    /**
     * Sets the "DENOM_CC" attribute
     */
    public void setDENOMCC(java.lang.String denomcc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENOMCC$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DENOMCC$22);
            }
            target.setStringValue(denomcc);
        }
    }
    
    /**
     * Sets (as xml) the "DENOM_CC" attribute
     */
    public void xsetDENOMCC(it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC denomcc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC)get_store().find_attribute_user(DENOMCC$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC)get_store().add_attribute_user(DENOMCC$22);
            }
            target.set(denomcc);
        }
    }
    
    /**
     * Gets the "ID_TIPOLOGIA_SA" attribute
     */
    public java.lang.String getIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDTIPOLOGIASA$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_TIPOLOGIA_SA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipologiaSAType xgetIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipologiaSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipologiaSAType)get_store().find_attribute_user(IDTIPOLOGIASA$24);
            return target;
        }
    }
    
    /**
     * True if has "ID_TIPOLOGIA_SA" attribute
     */
    public boolean isSetIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDTIPOLOGIASA$24) != null;
        }
    }
    
    /**
     * Sets the "ID_TIPOLOGIA_SA" attribute
     */
    public void setIDTIPOLOGIASA(java.lang.String idtipologiasa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDTIPOLOGIASA$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDTIPOLOGIASA$24);
            }
            target.setStringValue(idtipologiasa);
        }
    }
    
    /**
     * Sets (as xml) the "ID_TIPOLOGIA_SA" attribute
     */
    public void xsetIDTIPOLOGIASA(it.avlp.simog.massload.xmlbeans.TipologiaSAType idtipologiasa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipologiaSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipologiaSAType)get_store().find_attribute_user(IDTIPOLOGIASA$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipologiaSAType)get_store().add_attribute_user(IDTIPOLOGIASA$24);
            }
            target.set(idtipologiasa);
        }
    }
    
    /**
     * Unsets the "ID_TIPOLOGIA_SA" attribute
     */
    public void unsetIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDTIPOLOGIASA$24);
        }
    }
    
    /**
     * Gets the "FLAG_SA_AGENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGSAAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSAAGENTE$26);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SA_AGENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGSAAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSAAGENTE$26);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SA_AGENTE" attribute
     */
    public void setFLAGSAAGENTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagsaagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSAAGENTE$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGSAAGENTE$26);
            }
            target.setEnumValue(flagsaagente);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SA_AGENTE" attribute
     */
    public void xsetFLAGSAAGENTE(it.avlp.simog.massload.xmlbeans.FlagSNType flagsaagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSAAGENTE$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGSAAGENTE$26);
            }
            target.set(flagsaagente);
        }
    }
    
    /**
     * Gets the "CF_RUP" attribute
     */
    public java.lang.String getCFRUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFRUP$28);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_RUP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFRUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFRUP$28);
            return target;
        }
    }
    
    /**
     * Sets the "CF_RUP" attribute
     */
    public void setCFRUP(java.lang.String cfrup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFRUP$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFRUP$28);
            }
            target.setStringValue(cfrup);
        }
    }
    
    /**
     * Sets (as xml) the "CF_RUP" attribute
     */
    public void xsetCFRUP(it.avlp.simog.massload.xmlbeans.CodFiscType cfrup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFRUP$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFRUP$28);
            }
            target.set(cfrup);
        }
    }
    
    /**
     * Gets the "ESITO_PROCEDURA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EsitoProceduraType.Enum getESITOPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ESITOPROCEDURA$30);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.EsitoProceduraType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ESITO_PROCEDURA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.EsitoProceduraType xgetESITOPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EsitoProceduraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.EsitoProceduraType)get_store().find_attribute_user(ESITOPROCEDURA$30);
            return target;
        }
    }
    
    /**
     * True if has "ESITO_PROCEDURA" attribute
     */
    public boolean isSetESITOPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ESITOPROCEDURA$30) != null;
        }
    }
    
    /**
     * Sets the "ESITO_PROCEDURA" attribute
     */
    public void setESITOPROCEDURA(it.avlp.simog.massload.xmlbeans.EsitoProceduraType.Enum esitoprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ESITOPROCEDURA$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ESITOPROCEDURA$30);
            }
            target.setEnumValue(esitoprocedura);
        }
    }
    
    /**
     * Sets (as xml) the "ESITO_PROCEDURA" attribute
     */
    public void xsetESITOPROCEDURA(it.avlp.simog.massload.xmlbeans.EsitoProceduraType esitoprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EsitoProceduraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.EsitoProceduraType)get_store().find_attribute_user(ESITOPROCEDURA$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EsitoProceduraType)get_store().add_attribute_user(ESITOPROCEDURA$30);
            }
            target.set(esitoprocedura);
        }
    }
    
    /**
     * Unsets the "ESITO_PROCEDURA" attribute
     */
    public void unsetESITOPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ESITOPROCEDURA$30);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$32);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$32);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$32) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$32);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$32);
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
            get_store().remove_attribute(IDSCHEDALOCALE$32);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$34);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$34);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$34) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$34);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$34);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$34);
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
            get_store().remove_attribute(IDSCHEDASIMOG$34);
        }
    }
    
    /**
     * Gets the "TIPOLOGIA_PROCEDURA" attribute
     */
    public java.lang.String getTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOLOGIAPROCEDURA$36);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPOLOGIA_PROCEDURA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipologiaProceduraType xgetTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipologiaProceduraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipologiaProceduraType)get_store().find_attribute_user(TIPOLOGIAPROCEDURA$36);
            return target;
        }
    }
    
    /**
     * True if has "TIPOLOGIA_PROCEDURA" attribute
     */
    public boolean isSetTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TIPOLOGIAPROCEDURA$36) != null;
        }
    }
    
    /**
     * Sets the "TIPOLOGIA_PROCEDURA" attribute
     */
    public void setTIPOLOGIAPROCEDURA(java.lang.String tipologiaprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOLOGIAPROCEDURA$36);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOLOGIAPROCEDURA$36);
            }
            target.setStringValue(tipologiaprocedura);
        }
    }
    
    /**
     * Sets (as xml) the "TIPOLOGIA_PROCEDURA" attribute
     */
    public void xsetTIPOLOGIAPROCEDURA(it.avlp.simog.massload.xmlbeans.TipologiaProceduraType tipologiaprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipologiaProceduraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipologiaProceduraType)get_store().find_attribute_user(TIPOLOGIAPROCEDURA$36);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipologiaProceduraType)get_store().add_attribute_user(TIPOLOGIAPROCEDURA$36);
            }
            target.set(tipologiaprocedura);
        }
    }
    
    /**
     * Unsets the "TIPOLOGIA_PROCEDURA" attribute
     */
    public void unsetTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TIPOLOGIAPROCEDURA$36);
        }
    }
    
    /**
     * Gets the "FLAG_ACC_QUADRO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGACCQUADRO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGACCQUADRO$38);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ACC_QUADRO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGACCQUADRO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGACCQUADRO$38);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_ACC_QUADRO" attribute
     */
    public boolean isSetFLAGACCQUADRO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGACCQUADRO$38) != null;
        }
    }
    
    /**
     * Sets the "FLAG_ACC_QUADRO" attribute
     */
    public void setFLAGACCQUADRO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagaccquadro)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGACCQUADRO$38);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGACCQUADRO$38);
            }
            target.setEnumValue(flagaccquadro);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ACC_QUADRO" attribute
     */
    public void xsetFLAGACCQUADRO(it.avlp.simog.massload.xmlbeans.FlagSNType flagaccquadro)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGACCQUADRO$38);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGACCQUADRO$38);
            }
            target.set(flagaccquadro);
        }
    }
    
    /**
     * Unsets the "FLAG_ACC_QUADRO" attribute
     */
    public void unsetFLAGACCQUADRO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGACCQUADRO$38);
        }
    }
    
    /**
     * Gets the "DURATA_ACCQUADRO_CONVENZIONE" attribute
     */
    public int getDURATAACCQUADROCONVENZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATAACCQUADROCONVENZIONE$40);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "DURATA_ACCQUADRO_CONVENZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetDURATAACCQUADROCONVENZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(DURATAACCQUADROCONVENZIONE$40);
            return target;
        }
    }
    
    /**
     * True if has "DURATA_ACCQUADRO_CONVENZIONE" attribute
     */
    public boolean isSetDURATAACCQUADROCONVENZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DURATAACCQUADROCONVENZIONE$40) != null;
        }
    }
    
    /**
     * Sets the "DURATA_ACCQUADRO_CONVENZIONE" attribute
     */
    public void setDURATAACCQUADROCONVENZIONE(int durataaccquadroconvenzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATAACCQUADROCONVENZIONE$40);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DURATAACCQUADROCONVENZIONE$40);
            }
            target.setIntValue(durataaccquadroconvenzione);
        }
    }
    
    /**
     * Sets (as xml) the "DURATA_ACCQUADRO_CONVENZIONE" attribute
     */
    public void xsetDURATAACCQUADROCONVENZIONE(it.avlp.simog.massload.xmlbeans.InteroType durataaccquadroconvenzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(DURATAACCQUADROCONVENZIONE$40);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(DURATAACCQUADROCONVENZIONE$40);
            }
            target.set(durataaccquadroconvenzione);
        }
    }
    
    /**
     * Unsets the "DURATA_ACCQUADRO_CONVENZIONE" attribute
     */
    public void unsetDURATAACCQUADROCONVENZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DURATAACCQUADROCONVENZIONE$40);
        }
    }
    
    /**
     * Gets the "FLAG_CENTRALE_STIPULA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGCENTRALESTIPULA$42);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_CENTRALE_STIPULA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGCENTRALESTIPULA$42);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_CENTRALE_STIPULA" attribute
     */
    public boolean isSetFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGCENTRALESTIPULA$42) != null;
        }
    }
    
    /**
     * Sets the "FLAG_CENTRALE_STIPULA" attribute
     */
    public void setFLAGCENTRALESTIPULA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagcentralestipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGCENTRALESTIPULA$42);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGCENTRALESTIPULA$42);
            }
            target.setEnumValue(flagcentralestipula);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_CENTRALE_STIPULA" attribute
     */
    public void xsetFLAGCENTRALESTIPULA(it.avlp.simog.massload.xmlbeans.FlagSNType flagcentralestipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGCENTRALESTIPULA$42);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGCENTRALESTIPULA$42);
            }
            target.set(flagcentralestipula);
        }
    }
    
    /**
     * Unsets the "FLAG_CENTRALE_STIPULA" attribute
     */
    public void unsetFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGCENTRALESTIPULA$42);
        }
    }
    
    /**
     * Gets the "MODO_REALIZZAZIONE" attribute
     */
    public java.lang.String getMODOREALIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODOREALIZZAZIONE$44);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MODO_REALIZZAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModoRealizzazioneType xgetMODOREALIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModoRealizzazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModoRealizzazioneType)get_store().find_attribute_user(MODOREALIZZAZIONE$44);
            return target;
        }
    }
    
    /**
     * True if has "MODO_REALIZZAZIONE" attribute
     */
    public boolean isSetMODOREALIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MODOREALIZZAZIONE$44) != null;
        }
    }
    
    /**
     * Sets the "MODO_REALIZZAZIONE" attribute
     */
    public void setMODOREALIZZAZIONE(java.lang.String modorealizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODOREALIZZAZIONE$44);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MODOREALIZZAZIONE$44);
            }
            target.setStringValue(modorealizzazione);
        }
    }
    
    /**
     * Sets (as xml) the "MODO_REALIZZAZIONE" attribute
     */
    public void xsetMODOREALIZZAZIONE(it.avlp.simog.massload.xmlbeans.ModoRealizzazioneType modorealizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModoRealizzazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModoRealizzazioneType)get_store().find_attribute_user(MODOREALIZZAZIONE$44);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModoRealizzazioneType)get_store().add_attribute_user(MODOREALIZZAZIONE$44);
            }
            target.set(modorealizzazione);
        }
    }
    
    /**
     * Unsets the "MODO_REALIZZAZIONE" attribute
     */
    public void unsetMODOREALIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MODOREALIZZAZIONE$44);
        }
    }
    
    /**
     * Gets the "FLAG_ESCLUSO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGESCLUSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGESCLUSO$46);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ESCLUSO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGESCLUSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGESCLUSO$46);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_ESCLUSO" attribute
     */
    public boolean isSetFLAGESCLUSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGESCLUSO$46) != null;
        }
    }
    
    /**
     * Sets the "FLAG_ESCLUSO" attribute
     */
    public void setFLAGESCLUSO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagescluso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGESCLUSO$46);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGESCLUSO$46);
            }
            target.setEnumValue(flagescluso);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ESCLUSO" attribute
     */
    public void xsetFLAGESCLUSO(it.avlp.simog.massload.xmlbeans.FlagSNType flagescluso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGESCLUSO$46);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGESCLUSO$46);
            }
            target.set(flagescluso);
        }
    }
    
    /**
     * Unsets the "FLAG_ESCLUSO" attribute
     */
    public void unsetFLAGESCLUSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGESCLUSO$46);
        }
    }
    
    /**
     * Gets the "ID_ESCLUSIONE" attribute
     */
    public java.lang.String getIDESCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDESCLUSIONE$48);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_ESCLUSIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ArtEsclusioneType xgetIDESCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ArtEsclusioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ArtEsclusioneType)get_store().find_attribute_user(IDESCLUSIONE$48);
            return target;
        }
    }
    
    /**
     * True if has "ID_ESCLUSIONE" attribute
     */
    public boolean isSetIDESCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDESCLUSIONE$48) != null;
        }
    }
    
    /**
     * Sets the "ID_ESCLUSIONE" attribute
     */
    public void setIDESCLUSIONE(java.lang.String idesclusione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDESCLUSIONE$48);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDESCLUSIONE$48);
            }
            target.setStringValue(idesclusione);
        }
    }
    
    /**
     * Sets (as xml) the "ID_ESCLUSIONE" attribute
     */
    public void xsetIDESCLUSIONE(it.avlp.simog.massload.xmlbeans.ArtEsclusioneType idesclusione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ArtEsclusioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ArtEsclusioneType)get_store().find_attribute_user(IDESCLUSIONE$48);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ArtEsclusioneType)get_store().add_attribute_user(IDESCLUSIONE$48);
            }
            target.set(idesclusione);
        }
    }
    
    /**
     * Unsets the "ID_ESCLUSIONE" attribute
     */
    public void unsetIDESCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDESCLUSIONE$48);
        }
    }
    
    /**
     * Gets the "ID_STATO_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum getIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$50);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoSchedaType xgetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$50);
            return target;
        }
    }
    
    /**
     * True if has "ID_STATO_SCHEDA" attribute
     */
    public boolean isSetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSTATOSCHEDA$50) != null;
        }
    }
    
    /**
     * Sets the "ID_STATO_SCHEDA" attribute
     */
    public void setIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType.Enum idstatoscheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$50);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$50);
            }
            target.setEnumValue(idstatoscheda);
        }
    }
    
    /**
     * Sets (as xml) the "ID_STATO_SCHEDA" attribute
     */
    public void xsetIDSTATOSCHEDA(it.avlp.simog.massload.xmlbeans.StatoSchedaType idstatoscheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$50);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$50);
            }
            target.set(idstatoscheda);
        }
    }
    
    /**
     * Unsets the "ID_STATO_SCHEDA" attribute
     */
    public void unsetIDSTATOSCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSTATOSCHEDA$50);
        }
    }
    
    /**
     * Gets the "ORIGINE_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.OrigineSchedaType.Enum getORIGINESCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIGINESCHEDA$52);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.OrigineSchedaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ORIGINE_SCHEDA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.OrigineSchedaType xgetORIGINESCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.OrigineSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().find_attribute_user(ORIGINESCHEDA$52);
            return target;
        }
    }
    
    /**
     * True if has "ORIGINE_SCHEDA" attribute
     */
    public boolean isSetORIGINESCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ORIGINESCHEDA$52) != null;
        }
    }
    
    /**
     * Sets the "ORIGINE_SCHEDA" attribute
     */
    public void setORIGINESCHEDA(it.avlp.simog.massload.xmlbeans.OrigineSchedaType.Enum originescheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIGINESCHEDA$52);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORIGINESCHEDA$52);
            }
            target.setEnumValue(originescheda);
        }
    }
    
    /**
     * Sets (as xml) the "ORIGINE_SCHEDA" attribute
     */
    public void xsetORIGINESCHEDA(it.avlp.simog.massload.xmlbeans.OrigineSchedaType originescheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.OrigineSchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().find_attribute_user(ORIGINESCHEDA$52);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().add_attribute_user(ORIGINESCHEDA$52);
            }
            target.set(originescheda);
        }
    }
    
    /**
     * Unsets the "ORIGINE_SCHEDA" attribute
     */
    public void unsetORIGINESCHEDA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ORIGINESCHEDA$52);
        }
    }
    
    /**
     * Gets the "PROVV_PRESA_CARICO" attribute
     */
    public java.lang.String getPROVVPRESACARICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROVVPRESACARICO$54);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROVV_PRESA_CARICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO xgetPROVVPRESACARICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO)get_store().find_attribute_user(PROVVPRESACARICO$54);
            return target;
        }
    }
    
    /**
     * True if has "PROVV_PRESA_CARICO" attribute
     */
    public boolean isSetPROVVPRESACARICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROVVPRESACARICO$54) != null;
        }
    }
    
    /**
     * Sets the "PROVV_PRESA_CARICO" attribute
     */
    public void setPROVVPRESACARICO(java.lang.String provvpresacarico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROVVPRESACARICO$54);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROVVPRESACARICO$54);
            }
            target.setStringValue(provvpresacarico);
        }
    }
    
    /**
     * Sets (as xml) the "PROVV_PRESA_CARICO" attribute
     */
    public void xsetPROVVPRESACARICO(it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO provvpresacarico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO)get_store().find_attribute_user(PROVVPRESACARICO$54);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO)get_store().add_attribute_user(PROVVPRESACARICO$54);
            }
            target.set(provvpresacarico);
        }
    }
    
    /**
     * Unsets the "PROVV_PRESA_CARICO" attribute
     */
    public void unsetPROVVPRESACARICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROVVPRESACARICO$54);
        }
    }
    /**
     * An XML DEN_AMM_AGENTE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiComuniType$DENAMMAGENTE.
     */
    public static class DENAMMAGENTEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMMAGENTE
    {
        
        public DENAMMAGENTEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DENAMMAGENTEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DEN_AMM(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiComuniType$DENAMM.
     */
    public static class DENAMMImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiComuniType.DENAMM
    {
        
        public DENAMMImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DENAMMImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DEN_SA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiComuniType$DENSA.
     */
    public static class DENSAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiComuniType.DENSA
    {
        
        public DENSAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DENSAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CODICE_CC(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiComuniType$CODICECC.
     */
    public static class CODICECCImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiComuniType.CODICECC
    {
        
        public CODICECCImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CODICECCImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML DENOM_CC(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiComuniType$DENOMCC.
     */
    public static class DENOMCCImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiComuniType.DENOMCC
    {
        
        public DENOMCCImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DENOMCCImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML PROVV_PRESA_CARICO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiComuniType$PROVVPRESACARICO.
     */
    public static class PROVVPRESACARICOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiComuniType.PROVVPRESACARICO
    {
        
        public PROVVPRESACARICOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected PROVVPRESACARICOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
