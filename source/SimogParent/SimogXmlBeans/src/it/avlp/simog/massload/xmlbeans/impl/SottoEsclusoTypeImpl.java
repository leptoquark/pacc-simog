/*
 * XML Type:  SottoEsclusoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SottoEsclusoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SottoEsclusoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SottoEsclusoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SottoEsclusoType
{
    
    public SottoEsclusoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LUOGOISTAT$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_ISTAT");
    private static final javax.xml.namespace.QName LUOGONUTS$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_NUTS");
    private static final javax.xml.namespace.QName CUP$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CUP");
    private static final javax.xml.namespace.QName IMPORTOCOMPLESSIVO$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_COMPLESSIVO");
    private static final javax.xml.namespace.QName IMPORTODISPOSIZIONE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_DISPOSIZIONE");
    private static final javax.xml.namespace.QName IDSCELTACONTRAENTE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCELTA_CONTRAENTE");
    private static final javax.xml.namespace.QName ASTAELETTRONICA$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ASTA_ELETTRONICA");
    private static final javax.xml.namespace.QName PERCRIBASSOAGG$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERC_RIBASSO_AGG");
    private static final javax.xml.namespace.QName PERCOFFAUMENTO$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERC_OFF_AUMENTO");
    private static final javax.xml.namespace.QName IMPORTOAGGIUDICAZIONE$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName DATAAGGIUDICAZIONE$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName DATASTIPULA$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_STIPULA");
    private static final javax.xml.namespace.QName TERMINECONTRATTUALE$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TERMINE_CONTRATTUALE");
    private static final javax.xml.namespace.QName DURATACONTRATTUALE$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DURATA_CONTRATTUALE");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IMPORTOATTUAZIONESICUREZZA$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_ATTUAZIONE_SICUREZZA");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$34 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    private static final javax.xml.namespace.QName ORIGINESCHEDA$36 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ORIGINE_SCHEDA");
    private static final javax.xml.namespace.QName FLAGCUP$38 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_CUP");
    
    
    /**
     * Gets the "LUOGO_ISTAT" attribute
     */
    public java.lang.String getLUOGOISTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOISTAT$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LUOGO_ISTAT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LuogoIstatType xgetLUOGOISTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().find_attribute_user(LUOGOISTAT$0);
            return target;
        }
    }
    
    /**
     * True if has "LUOGO_ISTAT" attribute
     */
    public boolean isSetLUOGOISTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LUOGOISTAT$0) != null;
        }
    }
    
    /**
     * Sets the "LUOGO_ISTAT" attribute
     */
    public void setLUOGOISTAT(java.lang.String luogoistat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOISTAT$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LUOGOISTAT$0);
            }
            target.setStringValue(luogoistat);
        }
    }
    
    /**
     * Sets (as xml) the "LUOGO_ISTAT" attribute
     */
    public void xsetLUOGOISTAT(it.avlp.simog.massload.xmlbeans.LuogoIstatType luogoistat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().find_attribute_user(LUOGOISTAT$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().add_attribute_user(LUOGOISTAT$0);
            }
            target.set(luogoistat);
        }
    }
    
    /**
     * Unsets the "LUOGO_ISTAT" attribute
     */
    public void unsetLUOGOISTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LUOGOISTAT$0);
        }
    }
    
    /**
     * Gets the "LUOGO_NUTS" attribute
     */
    public java.lang.String getLUOGONUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGONUTS$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LUOGO_NUTS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType xgetLUOGONUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(LUOGONUTS$2);
            return target;
        }
    }
    
    /**
     * True if has "LUOGO_NUTS" attribute
     */
    public boolean isSetLUOGONUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LUOGONUTS$2) != null;
        }
    }
    
    /**
     * Sets the "LUOGO_NUTS" attribute
     */
    public void setLUOGONUTS(java.lang.String luogonuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGONUTS$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LUOGONUTS$2);
            }
            target.setStringValue(luogonuts);
        }
    }
    
    /**
     * Sets (as xml) the "LUOGO_NUTS" attribute
     */
    public void xsetLUOGONUTS(it.avlp.simog.massload.xmlbeans.LuogoNutsType luogonuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(LUOGONUTS$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().add_attribute_user(LUOGONUTS$2);
            }
            target.set(luogonuts);
        }
    }
    
    /**
     * Unsets the "LUOGO_NUTS" attribute
     */
    public void unsetLUOGONUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LUOGONUTS$2);
        }
    }
    
    /**
     * Gets the "CUP" attribute
     */
    public java.lang.String getCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUP$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CUP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CupType xgetCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CupType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CupType)get_store().find_attribute_user(CUP$4);
            return target;
        }
    }
    
    /**
     * True if has "CUP" attribute
     */
    public boolean isSetCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CUP$4) != null;
        }
    }
    
    /**
     * Sets the "CUP" attribute
     */
    public void setCUP(java.lang.String cup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUP$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUP$4);
            }
            target.setStringValue(cup);
        }
    }
    
    /**
     * Sets (as xml) the "CUP" attribute
     */
    public void xsetCUP(it.avlp.simog.massload.xmlbeans.CupType cup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CupType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CupType)get_store().find_attribute_user(CUP$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CupType)get_store().add_attribute_user(CUP$4);
            }
            target.set(cup);
        }
    }
    
    /**
     * Unsets the "CUP" attribute
     */
    public void unsetCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CUP$4);
        }
    }
    
    /**
     * Gets the "IMPORTO_COMPLESSIVO" attribute
     */
    public java.math.BigDecimal getIMPORTOCOMPLESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOCOMPLESSIVO$6);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_COMPLESSIVO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOCOMPLESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOCOMPLESSIVO$6);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_COMPLESSIVO" attribute
     */
    public void setIMPORTOCOMPLESSIVO(java.math.BigDecimal importocomplessivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOCOMPLESSIVO$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOCOMPLESSIVO$6);
            }
            target.setBigDecimalValue(importocomplessivo);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_COMPLESSIVO" attribute
     */
    public void xsetIMPORTOCOMPLESSIVO(it.avlp.simog.massload.xmlbeans.ImportoType importocomplessivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOCOMPLESSIVO$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOCOMPLESSIVO$6);
            }
            target.set(importocomplessivo);
        }
    }
    
    /**
     * Gets the "IMPORTO_DISPOSIZIONE" attribute
     */
    public java.math.BigDecimal getIMPORTODISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTODISPOSIZIONE$8);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_DISPOSIZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTODISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTODISPOSIZIONE$8);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_DISPOSIZIONE" attribute
     */
    public void setIMPORTODISPOSIZIONE(java.math.BigDecimal importodisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTODISPOSIZIONE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTODISPOSIZIONE$8);
            }
            target.setBigDecimalValue(importodisposizione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_DISPOSIZIONE" attribute
     */
    public void xsetIMPORTODISPOSIZIONE(it.avlp.simog.massload.xmlbeans.ImportoType importodisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTODISPOSIZIONE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTODISPOSIZIONE$8);
            }
            target.set(importodisposizione);
        }
    }
    
    /**
     * Gets the "ID_SCELTA_CONTRAENTE" attribute
     */
    public java.lang.String getIDSCELTACONTRAENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCELTACONTRAENTE$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_SCELTA_CONTRAENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.SceltaContraenteType xgetIDSCELTACONTRAENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SceltaContraenteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SceltaContraenteType)get_store().find_attribute_user(IDSCELTACONTRAENTE$10);
            return target;
        }
    }
    
    /**
     * True if has "ID_SCELTA_CONTRAENTE" attribute
     */
    public boolean isSetIDSCELTACONTRAENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDSCELTACONTRAENTE$10) != null;
        }
    }
    
    /**
     * Sets the "ID_SCELTA_CONTRAENTE" attribute
     */
    public void setIDSCELTACONTRAENTE(java.lang.String idsceltacontraente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCELTACONTRAENTE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCELTACONTRAENTE$10);
            }
            target.setStringValue(idsceltacontraente);
        }
    }
    
    /**
     * Sets (as xml) the "ID_SCELTA_CONTRAENTE" attribute
     */
    public void xsetIDSCELTACONTRAENTE(it.avlp.simog.massload.xmlbeans.SceltaContraenteType idsceltacontraente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SceltaContraenteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SceltaContraenteType)get_store().find_attribute_user(IDSCELTACONTRAENTE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SceltaContraenteType)get_store().add_attribute_user(IDSCELTACONTRAENTE$10);
            }
            target.set(idsceltacontraente);
        }
    }
    
    /**
     * Unsets the "ID_SCELTA_CONTRAENTE" attribute
     */
    public void unsetIDSCELTACONTRAENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDSCELTACONTRAENTE$10);
        }
    }
    
    /**
     * Gets the "ASTA_ELETTRONICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ASTAELETTRONICA$12);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ASTA_ELETTRONICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ASTAELETTRONICA$12);
            return target;
        }
    }
    
    /**
     * Sets the "ASTA_ELETTRONICA" attribute
     */
    public void setASTAELETTRONICA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum astaelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ASTAELETTRONICA$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ASTAELETTRONICA$12);
            }
            target.setEnumValue(astaelettronica);
        }
    }
    
    /**
     * Sets (as xml) the "ASTA_ELETTRONICA" attribute
     */
    public void xsetASTAELETTRONICA(it.avlp.simog.massload.xmlbeans.FlagSNType astaelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ASTAELETTRONICA$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(ASTAELETTRONICA$12);
            }
            target.set(astaelettronica);
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
     * Gets the "DATA_AGGIUDICAZIONE" attribute
     */
    public java.util.Calendar getDATAAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAGGIUDICAZIONE$20);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_AGGIUDICAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAGGIUDICAZIONE$20);
            return target;
        }
    }
    
    /**
     * True if has "DATA_AGGIUDICAZIONE" attribute
     */
    public boolean isSetDATAAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAAGGIUDICAZIONE$20) != null;
        }
    }
    
    /**
     * Sets the "DATA_AGGIUDICAZIONE" attribute
     */
    public void setDATAAGGIUDICAZIONE(java.util.Calendar dataaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAGGIUDICAZIONE$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAAGGIUDICAZIONE$20);
            }
            target.setCalendarValue(dataaggiudicazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_AGGIUDICAZIONE" attribute
     */
    public void xsetDATAAGGIUDICAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType dataaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAGGIUDICAZIONE$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAAGGIUDICAZIONE$20);
            }
            target.set(dataaggiudicazione);
        }
    }
    
    /**
     * Unsets the "DATA_AGGIUDICAZIONE" attribute
     */
    public void unsetDATAAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAAGGIUDICAZIONE$20);
        }
    }
    
    /**
     * Gets the "DATA_STIPULA" attribute
     */
    public java.util.Calendar getDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASTIPULA$22);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_STIPULA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASTIPULA$22);
            return target;
        }
    }
    
    /**
     * True if has "DATA_STIPULA" attribute
     */
    public boolean isSetDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATASTIPULA$22) != null;
        }
    }
    
    /**
     * Sets the "DATA_STIPULA" attribute
     */
    public void setDATASTIPULA(java.util.Calendar datastipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASTIPULA$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATASTIPULA$22);
            }
            target.setCalendarValue(datastipula);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_STIPULA" attribute
     */
    public void xsetDATASTIPULA(it.avlp.simog.massload.xmlbeans.DbDateType datastipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASTIPULA$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATASTIPULA$22);
            }
            target.set(datastipula);
        }
    }
    
    /**
     * Unsets the "DATA_STIPULA" attribute
     */
    public void unsetDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATASTIPULA$22);
        }
    }
    
    /**
     * Gets the "TERMINE_CONTRATTUALE" attribute
     */
    public java.util.Calendar getTERMINECONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TERMINECONTRATTUALE$24);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "TERMINE_CONTRATTUALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetTERMINECONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(TERMINECONTRATTUALE$24);
            return target;
        }
    }
    
    /**
     * True if has "TERMINE_CONTRATTUALE" attribute
     */
    public boolean isSetTERMINECONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TERMINECONTRATTUALE$24) != null;
        }
    }
    
    /**
     * Sets the "TERMINE_CONTRATTUALE" attribute
     */
    public void setTERMINECONTRATTUALE(java.util.Calendar terminecontrattuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TERMINECONTRATTUALE$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TERMINECONTRATTUALE$24);
            }
            target.setCalendarValue(terminecontrattuale);
        }
    }
    
    /**
     * Sets (as xml) the "TERMINE_CONTRATTUALE" attribute
     */
    public void xsetTERMINECONTRATTUALE(it.avlp.simog.massload.xmlbeans.DbDateType terminecontrattuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(TERMINECONTRATTUALE$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(TERMINECONTRATTUALE$24);
            }
            target.set(terminecontrattuale);
        }
    }
    
    /**
     * Unsets the "TERMINE_CONTRATTUALE" attribute
     */
    public void unsetTERMINECONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TERMINECONTRATTUALE$24);
        }
    }
    
    /**
     * Gets the "DURATA_CONTRATTUALE" attribute
     */
    public int getDURATACONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATACONTRATTUALE$26);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "DURATA_CONTRATTUALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetDURATACONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(DURATACONTRATTUALE$26);
            return target;
        }
    }
    
    /**
     * True if has "DURATA_CONTRATTUALE" attribute
     */
    public boolean isSetDURATACONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DURATACONTRATTUALE$26) != null;
        }
    }
    
    /**
     * Sets the "DURATA_CONTRATTUALE" attribute
     */
    public void setDURATACONTRATTUALE(int duratacontrattuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DURATACONTRATTUALE$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DURATACONTRATTUALE$26);
            }
            target.setIntValue(duratacontrattuale);
        }
    }
    
    /**
     * Sets (as xml) the "DURATA_CONTRATTUALE" attribute
     */
    public void xsetDURATACONTRATTUALE(it.avlp.simog.massload.xmlbeans.InteroType duratacontrattuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(DURATACONTRATTUALE$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(DURATACONTRATTUALE$26);
            }
            target.set(duratacontrattuale);
        }
    }
    
    /**
     * Unsets the "DURATA_CONTRATTUALE" attribute
     */
    public void unsetDURATACONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DURATACONTRATTUALE$26);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$28);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$28);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$28) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$28);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$28);
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
            get_store().remove_attribute(IDSCHEDALOCALE$28);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$30);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$30);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$30) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$30);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$30);
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
            get_store().remove_attribute(IDSCHEDASIMOG$30);
        }
    }
    
    /**
     * Gets the "IMPORTO_ATTUAZIONE_SICUREZZA" attribute
     */
    public java.math.BigDecimal getIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$32);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_ATTUAZIONE_SICUREZZA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$32);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_ATTUAZIONE_SICUREZZA" attribute
     */
    public boolean isSetIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$32) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_ATTUAZIONE_SICUREZZA" attribute
     */
    public void setIMPORTOATTUAZIONESICUREZZA(java.math.BigDecimal importoattuazionesicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOATTUAZIONESICUREZZA$32);
            }
            target.setBigDecimalValue(importoattuazionesicurezza);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_ATTUAZIONE_SICUREZZA" attribute
     */
    public void xsetIMPORTOATTUAZIONESICUREZZA(it.avlp.simog.massload.xmlbeans.ImportoType importoattuazionesicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOATTUAZIONESICUREZZA$32);
            }
            target.set(importoattuazionesicurezza);
        }
    }
    
    /**
     * Unsets the "IMPORTO_ATTUAZIONE_SICUREZZA" attribute
     */
    public void unsetIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOATTUAZIONESICUREZZA$32);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$34);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$34);
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
            return get_store().find_attribute_user(IDSTATOSCHEDA$34) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$34);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$34);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$34);
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
            get_store().remove_attribute(IDSTATOSCHEDA$34);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIGINESCHEDA$36);
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
            target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().find_attribute_user(ORIGINESCHEDA$36);
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
            return get_store().find_attribute_user(ORIGINESCHEDA$36) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIGINESCHEDA$36);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORIGINESCHEDA$36);
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
            target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().find_attribute_user(ORIGINESCHEDA$36);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().add_attribute_user(ORIGINESCHEDA$36);
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
            get_store().remove_attribute(ORIGINESCHEDA$36);
        }
    }
    
    /**
     * Gets the "FLAG_CUP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGCUP$38);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_CUP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGCUP$38);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_CUP" attribute
     */
    public boolean isSetFLAGCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGCUP$38) != null;
        }
    }
    
    /**
     * Sets the "FLAG_CUP" attribute
     */
    public void setFLAGCUP(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagcup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGCUP$38);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGCUP$38);
            }
            target.setEnumValue(flagcup);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_CUP" attribute
     */
    public void xsetFLAGCUP(it.avlp.simog.massload.xmlbeans.FlagSNType flagcup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGCUP$38);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGCUP$38);
            }
            target.set(flagcup);
        }
    }
    
    /**
     * Unsets the "FLAG_CUP" attribute
     */
    public void unsetFLAGCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGCUP$38);
        }
    }
}
