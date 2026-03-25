/*
 * XML Type:  AppaltoAdesioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AppaltoAdesioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AppaltoAdesioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType
{
    
    public AppaltoAdesioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LUOGOISTAT$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_ISTAT");
    private static final javax.xml.namespace.QName LUOGONUTS$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_NUTS");
    private static final javax.xml.namespace.QName CODSTRUMENTO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "COD_STRUMENTO");
    private static final javax.xml.namespace.QName IMPORTOLAVORI$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_LAVORI");
    private static final javax.xml.namespace.QName IMPORTOSERVIZI$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_SERVIZI");
    private static final javax.xml.namespace.QName IMPORTOFORNITURE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_FORNITURE");
    private static final javax.xml.namespace.QName PERCRIBASSOAGG$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERC_RIBASSO_AGG");
    private static final javax.xml.namespace.QName PERCOFFAUMENTO$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERC_OFF_AUMENTO");
    private static final javax.xml.namespace.QName IMPORTOAGGIUDICAZIONE$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName DATAAGGIUDICAZIONE$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName FLAGRICHSUBAPPALTO$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_RICH_SUBAPPALTO");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    private static final javax.xml.namespace.QName ORIGINESCHEDA$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ORIGINE_SCHEDA");
    private static final javax.xml.namespace.QName IMPORTOATTUAZIONESICUREZZA$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_ATTUAZIONE_SICUREZZA");
    private static final javax.xml.namespace.QName IMPORTOPROGETTAZIONE$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_PROGETTAZIONE");
    private static final javax.xml.namespace.QName IMPNONASSOG$34 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_NON_ASSOG");
    
    
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
     * Gets the "COD_STRUMENTO" attribute
     */
    public java.lang.String getCODSTRUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODSTRUMENTO$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "COD_STRUMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipoStrumentoType xgetCODSTRUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoStrumentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoStrumentoType)get_store().find_attribute_user(CODSTRUMENTO$4);
            return target;
        }
    }
    
    /**
     * True if has "COD_STRUMENTO" attribute
     */
    public boolean isSetCODSTRUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODSTRUMENTO$4) != null;
        }
    }
    
    /**
     * Sets the "COD_STRUMENTO" attribute
     */
    public void setCODSTRUMENTO(java.lang.String codstrumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODSTRUMENTO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODSTRUMENTO$4);
            }
            target.setStringValue(codstrumento);
        }
    }
    
    /**
     * Sets (as xml) the "COD_STRUMENTO" attribute
     */
    public void xsetCODSTRUMENTO(it.avlp.simog.massload.xmlbeans.TipoStrumentoType codstrumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoStrumentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoStrumentoType)get_store().find_attribute_user(CODSTRUMENTO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipoStrumentoType)get_store().add_attribute_user(CODSTRUMENTO$4);
            }
            target.set(codstrumento);
        }
    }
    
    /**
     * Unsets the "COD_STRUMENTO" attribute
     */
    public void unsetCODSTRUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODSTRUMENTO$4);
        }
    }
    
    /**
     * Gets the "IMPORTO_LAVORI" attribute
     */
    public java.math.BigDecimal getIMPORTOLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOLAVORI$6);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_LAVORI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOLAVORI$6);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_LAVORI" attribute
     */
    public void setIMPORTOLAVORI(java.math.BigDecimal importolavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOLAVORI$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOLAVORI$6);
            }
            target.setBigDecimalValue(importolavori);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_LAVORI" attribute
     */
    public void xsetIMPORTOLAVORI(it.avlp.simog.massload.xmlbeans.ImportoType importolavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOLAVORI$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOLAVORI$6);
            }
            target.set(importolavori);
        }
    }
    
    /**
     * Gets the "IMPORTO_SERVIZI" attribute
     */
    public java.math.BigDecimal getIMPORTOSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOSERVIZI$8);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_SERVIZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOSERVIZI$8);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_SERVIZI" attribute
     */
    public void setIMPORTOSERVIZI(java.math.BigDecimal importoservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOSERVIZI$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOSERVIZI$8);
            }
            target.setBigDecimalValue(importoservizi);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_SERVIZI" attribute
     */
    public void xsetIMPORTOSERVIZI(it.avlp.simog.massload.xmlbeans.ImportoType importoservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOSERVIZI$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOSERVIZI$8);
            }
            target.set(importoservizi);
        }
    }
    
    /**
     * Gets the "IMPORTO_FORNITURE" attribute
     */
    public java.math.BigDecimal getIMPORTOFORNITURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOFORNITURE$10);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_FORNITURE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOFORNITURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOFORNITURE$10);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_FORNITURE" attribute
     */
    public void setIMPORTOFORNITURE(java.math.BigDecimal importoforniture)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOFORNITURE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOFORNITURE$10);
            }
            target.setBigDecimalValue(importoforniture);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_FORNITURE" attribute
     */
    public void xsetIMPORTOFORNITURE(it.avlp.simog.massload.xmlbeans.ImportoType importoforniture)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOFORNITURE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOFORNITURE$10);
            }
            target.set(importoforniture);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCRIBASSOAGG$12);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCRIBASSOAGG$12);
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
            return get_store().find_attribute_user(PERCRIBASSOAGG$12) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCRIBASSOAGG$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERCRIBASSOAGG$12);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCRIBASSOAGG$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(PERCRIBASSOAGG$12);
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
            get_store().remove_attribute(PERCRIBASSOAGG$12);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCOFFAUMENTO$14);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCOFFAUMENTO$14);
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
            return get_store().find_attribute_user(PERCOFFAUMENTO$14) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERCOFFAUMENTO$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERCOFFAUMENTO$14);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(PERCOFFAUMENTO$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(PERCOFFAUMENTO$14);
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
            get_store().remove_attribute(PERCOFFAUMENTO$14);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$16);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$16);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOAGGIUDICAZIONE$16);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOAGGIUDICAZIONE$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOAGGIUDICAZIONE$16);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAGGIUDICAZIONE$18);
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
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAGGIUDICAZIONE$18);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAGGIUDICAZIONE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAAGGIUDICAZIONE$18);
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
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAGGIUDICAZIONE$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAAGGIUDICAZIONE$18);
            }
            target.set(dataaggiudicazione);
        }
    }
    
    /**
     * Gets the "FLAG_RICH_SUBAPPALTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGRICHSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRICHSUBAPPALTO$20);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RICH_SUBAPPALTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGRICHSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRICHSUBAPPALTO$20);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RICH_SUBAPPALTO" attribute
     */
    public void setFLAGRICHSUBAPPALTO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagrichsubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRICHSUBAPPALTO$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGRICHSUBAPPALTO$20);
            }
            target.setEnumValue(flagrichsubappalto);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RICH_SUBAPPALTO" attribute
     */
    public void xsetFLAGRICHSUBAPPALTO(it.avlp.simog.massload.xmlbeans.FlagSNType flagrichsubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGRICHSUBAPPALTO$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGRICHSUBAPPALTO$20);
            }
            target.set(flagrichsubappalto);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$22);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$22);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$22) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$22);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$22);
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
            get_store().remove_attribute(IDSCHEDALOCALE$22);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$24);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$24);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$24) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$24);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$24);
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
            get_store().remove_attribute(IDSCHEDASIMOG$24);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$26);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$26);
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
            return get_store().find_attribute_user(IDSTATOSCHEDA$26) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$26);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$26);
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
            get_store().remove_attribute(IDSTATOSCHEDA$26);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIGINESCHEDA$28);
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
            target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().find_attribute_user(ORIGINESCHEDA$28);
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
            return get_store().find_attribute_user(ORIGINESCHEDA$28) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIGINESCHEDA$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORIGINESCHEDA$28);
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
            target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().find_attribute_user(ORIGINESCHEDA$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.OrigineSchedaType)get_store().add_attribute_user(ORIGINESCHEDA$28);
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
            get_store().remove_attribute(ORIGINESCHEDA$28);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$30);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$30);
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
            return get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$30) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOATTUAZIONESICUREZZA$30);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOATTUAZIONESICUREZZA$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOATTUAZIONESICUREZZA$30);
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
            get_store().remove_attribute(IMPORTOATTUAZIONESICUREZZA$30);
        }
    }
    
    /**
     * Gets the "IMPORTO_PROGETTAZIONE" attribute
     */
    public java.math.BigDecimal getIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOPROGETTAZIONE$32);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_PROGETTAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOPROGETTAZIONE$32);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_PROGETTAZIONE" attribute
     */
    public boolean isSetIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOPROGETTAZIONE$32) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_PROGETTAZIONE" attribute
     */
    public void setIMPORTOPROGETTAZIONE(java.math.BigDecimal importoprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOPROGETTAZIONE$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOPROGETTAZIONE$32);
            }
            target.setBigDecimalValue(importoprogettazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_PROGETTAZIONE" attribute
     */
    public void xsetIMPORTOPROGETTAZIONE(it.avlp.simog.massload.xmlbeans.ImportoType importoprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOPROGETTAZIONE$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOPROGETTAZIONE$32);
            }
            target.set(importoprogettazione);
        }
    }
    
    /**
     * Unsets the "IMPORTO_PROGETTAZIONE" attribute
     */
    public void unsetIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOPROGETTAZIONE$32);
        }
    }
    
    /**
     * Gets the "IMP_NON_ASSOG" attribute
     */
    public java.math.BigDecimal getIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPNONASSOG$34);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_NON_ASSOG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPNONASSOG$34);
            return target;
        }
    }
    
    /**
     * True if has "IMP_NON_ASSOG" attribute
     */
    public boolean isSetIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPNONASSOG$34) != null;
        }
    }
    
    /**
     * Sets the "IMP_NON_ASSOG" attribute
     */
    public void setIMPNONASSOG(java.math.BigDecimal impnonassog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPNONASSOG$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPNONASSOG$34);
            }
            target.setBigDecimalValue(impnonassog);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_NON_ASSOG" attribute
     */
    public void xsetIMPNONASSOG(it.avlp.simog.massload.xmlbeans.ImportoType impnonassog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPNONASSOG$34);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPNONASSOG$34);
            }
            target.set(impnonassog);
        }
    }
    
    /**
     * Unsets the "IMP_NON_ASSOG" attribute
     */
    public void unsetIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPNONASSOG$34);
        }
    }
}
