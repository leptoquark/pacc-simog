/*
 * XML Type:  RecVarianteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecVarianteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RecVarianteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RecVarianteTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RecVarianteType
{
    
    public RecVarianteTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAVERBAPPR$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_VERB_APPR");
    private static final javax.xml.namespace.QName ALTREMOTIVAZIONI$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ALTRE_MOTIVAZIONI");
    private static final javax.xml.namespace.QName IMPRIDETLAVORI$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_RIDET_LAVORI");
    private static final javax.xml.namespace.QName IMPRIDETSERVIZI$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_RIDET_SERVIZI");
    private static final javax.xml.namespace.QName IMPRIDETFORNIT$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_RIDET_FORNIT");
    private static final javax.xml.namespace.QName IMPSICUREZZA$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_SICUREZZA");
    private static final javax.xml.namespace.QName IMPPROGETTAZIONE$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_PROGETTAZIONE");
    private static final javax.xml.namespace.QName IMPDISPOSIZIONE$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_DISPOSIZIONE");
    private static final javax.xml.namespace.QName ULTERIORISOMME$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ULTERIORI_SOMME");
    private static final javax.xml.namespace.QName DATAATTOAGGIUNTIVO$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_ATTO_AGGIUNTIVO");
    private static final javax.xml.namespace.QName NUMGIORNIPROROGA$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_GIORNI_PROROGA");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    private static final javax.xml.namespace.QName CIGPROCEDURA$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIG_PROCEDURA");
    private static final javax.xml.namespace.QName LINKVARIANTI$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LINK_VARIANTI");
    private static final javax.xml.namespace.QName IDMOTIVOREVPREZZI$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_MOTIVO_REV_PREZZI");
    
    
    /**
     * Gets the "DATA_VERB_APPR" attribute
     */
    public java.util.Calendar getDATAVERBAPPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBAPPR$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_APPR" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAVERBAPPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBAPPR$0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_VERB_APPR" attribute
     */
    public void setDATAVERBAPPR(java.util.Calendar dataverbappr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBAPPR$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAVERBAPPR$0);
            }
            target.setCalendarValue(dataverbappr);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_APPR" attribute
     */
    public void xsetDATAVERBAPPR(it.avlp.simog.massload.xmlbeans.DbDateType dataverbappr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBAPPR$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAVERBAPPR$0);
            }
            target.set(dataverbappr);
        }
    }
    
    /**
     * Gets the "ALTRE_MOTIVAZIONI" attribute
     */
    public java.lang.String getALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTREMOTIVAZIONI$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ALTRE_MOTIVAZIONI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI xgetALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI)get_store().find_attribute_user(ALTREMOTIVAZIONI$2);
            return target;
        }
    }
    
    /**
     * True if has "ALTRE_MOTIVAZIONI" attribute
     */
    public boolean isSetALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ALTREMOTIVAZIONI$2) != null;
        }
    }
    
    /**
     * Sets the "ALTRE_MOTIVAZIONI" attribute
     */
    public void setALTREMOTIVAZIONI(java.lang.String altremotivazioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTREMOTIVAZIONI$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ALTREMOTIVAZIONI$2);
            }
            target.setStringValue(altremotivazioni);
        }
    }
    
    /**
     * Sets (as xml) the "ALTRE_MOTIVAZIONI" attribute
     */
    public void xsetALTREMOTIVAZIONI(it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI altremotivazioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI)get_store().find_attribute_user(ALTREMOTIVAZIONI$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI)get_store().add_attribute_user(ALTREMOTIVAZIONI$2);
            }
            target.set(altremotivazioni);
        }
    }
    
    /**
     * Unsets the "ALTRE_MOTIVAZIONI" attribute
     */
    public void unsetALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ALTREMOTIVAZIONI$2);
        }
    }
    
    /**
     * Gets the "IMP_RIDET_LAVORI" attribute
     */
    public java.math.BigDecimal getIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPRIDETLAVORI$4);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_RIDET_LAVORI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPRIDETLAVORI$4);
            return target;
        }
    }
    
    /**
     * True if has "IMP_RIDET_LAVORI" attribute
     */
    public boolean isSetIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPRIDETLAVORI$4) != null;
        }
    }
    
    /**
     * Sets the "IMP_RIDET_LAVORI" attribute
     */
    public void setIMPRIDETLAVORI(java.math.BigDecimal impridetlavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPRIDETLAVORI$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPRIDETLAVORI$4);
            }
            target.setBigDecimalValue(impridetlavori);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_RIDET_LAVORI" attribute
     */
    public void xsetIMPRIDETLAVORI(it.avlp.simog.massload.xmlbeans.ImportoType impridetlavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPRIDETLAVORI$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPRIDETLAVORI$4);
            }
            target.set(impridetlavori);
        }
    }
    
    /**
     * Unsets the "IMP_RIDET_LAVORI" attribute
     */
    public void unsetIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPRIDETLAVORI$4);
        }
    }
    
    /**
     * Gets the "IMP_RIDET_SERVIZI" attribute
     */
    public java.math.BigDecimal getIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPRIDETSERVIZI$6);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_RIDET_SERVIZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPRIDETSERVIZI$6);
            return target;
        }
    }
    
    /**
     * True if has "IMP_RIDET_SERVIZI" attribute
     */
    public boolean isSetIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPRIDETSERVIZI$6) != null;
        }
    }
    
    /**
     * Sets the "IMP_RIDET_SERVIZI" attribute
     */
    public void setIMPRIDETSERVIZI(java.math.BigDecimal impridetservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPRIDETSERVIZI$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPRIDETSERVIZI$6);
            }
            target.setBigDecimalValue(impridetservizi);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_RIDET_SERVIZI" attribute
     */
    public void xsetIMPRIDETSERVIZI(it.avlp.simog.massload.xmlbeans.ImportoType impridetservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPRIDETSERVIZI$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPRIDETSERVIZI$6);
            }
            target.set(impridetservizi);
        }
    }
    
    /**
     * Unsets the "IMP_RIDET_SERVIZI" attribute
     */
    public void unsetIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPRIDETSERVIZI$6);
        }
    }
    
    /**
     * Gets the "IMP_RIDET_FORNIT" attribute
     */
    public java.math.BigDecimal getIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPRIDETFORNIT$8);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_RIDET_FORNIT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPRIDETFORNIT$8);
            return target;
        }
    }
    
    /**
     * True if has "IMP_RIDET_FORNIT" attribute
     */
    public boolean isSetIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPRIDETFORNIT$8) != null;
        }
    }
    
    /**
     * Sets the "IMP_RIDET_FORNIT" attribute
     */
    public void setIMPRIDETFORNIT(java.math.BigDecimal impridetfornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPRIDETFORNIT$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPRIDETFORNIT$8);
            }
            target.setBigDecimalValue(impridetfornit);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_RIDET_FORNIT" attribute
     */
    public void xsetIMPRIDETFORNIT(it.avlp.simog.massload.xmlbeans.ImportoType impridetfornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPRIDETFORNIT$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPRIDETFORNIT$8);
            }
            target.set(impridetfornit);
        }
    }
    
    /**
     * Unsets the "IMP_RIDET_FORNIT" attribute
     */
    public void unsetIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPRIDETFORNIT$8);
        }
    }
    
    /**
     * Gets the "IMP_SICUREZZA" attribute
     */
    public java.math.BigDecimal getIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPSICUREZZA$10);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_SICUREZZA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPSICUREZZA$10);
            return target;
        }
    }
    
    /**
     * True if has "IMP_SICUREZZA" attribute
     */
    public boolean isSetIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPSICUREZZA$10) != null;
        }
    }
    
    /**
     * Sets the "IMP_SICUREZZA" attribute
     */
    public void setIMPSICUREZZA(java.math.BigDecimal impsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPSICUREZZA$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPSICUREZZA$10);
            }
            target.setBigDecimalValue(impsicurezza);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_SICUREZZA" attribute
     */
    public void xsetIMPSICUREZZA(it.avlp.simog.massload.xmlbeans.ImportoType impsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPSICUREZZA$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPSICUREZZA$10);
            }
            target.set(impsicurezza);
        }
    }
    
    /**
     * Unsets the "IMP_SICUREZZA" attribute
     */
    public void unsetIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPSICUREZZA$10);
        }
    }
    
    /**
     * Gets the "IMP_PROGETTAZIONE" attribute
     */
    public java.math.BigDecimal getIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPPROGETTAZIONE$12);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_PROGETTAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPPROGETTAZIONE$12);
            return target;
        }
    }
    
    /**
     * True if has "IMP_PROGETTAZIONE" attribute
     */
    public boolean isSetIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPPROGETTAZIONE$12) != null;
        }
    }
    
    /**
     * Sets the "IMP_PROGETTAZIONE" attribute
     */
    public void setIMPPROGETTAZIONE(java.math.BigDecimal impprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPPROGETTAZIONE$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPPROGETTAZIONE$12);
            }
            target.setBigDecimalValue(impprogettazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_PROGETTAZIONE" attribute
     */
    public void xsetIMPPROGETTAZIONE(it.avlp.simog.massload.xmlbeans.ImportoType impprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPPROGETTAZIONE$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPPROGETTAZIONE$12);
            }
            target.set(impprogettazione);
        }
    }
    
    /**
     * Unsets the "IMP_PROGETTAZIONE" attribute
     */
    public void unsetIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPPROGETTAZIONE$12);
        }
    }
    
    /**
     * Gets the "IMP_DISPOSIZIONE" attribute
     */
    public java.math.BigDecimal getIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPDISPOSIZIONE$14);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_DISPOSIZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPDISPOSIZIONE$14);
            return target;
        }
    }
    
    /**
     * True if has "IMP_DISPOSIZIONE" attribute
     */
    public boolean isSetIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPDISPOSIZIONE$14) != null;
        }
    }
    
    /**
     * Sets the "IMP_DISPOSIZIONE" attribute
     */
    public void setIMPDISPOSIZIONE(java.math.BigDecimal impdisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPDISPOSIZIONE$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPDISPOSIZIONE$14);
            }
            target.setBigDecimalValue(impdisposizione);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_DISPOSIZIONE" attribute
     */
    public void xsetIMPDISPOSIZIONE(it.avlp.simog.massload.xmlbeans.ImportoType impdisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPDISPOSIZIONE$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPDISPOSIZIONE$14);
            }
            target.set(impdisposizione);
        }
    }
    
    /**
     * Unsets the "IMP_DISPOSIZIONE" attribute
     */
    public void unsetIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPDISPOSIZIONE$14);
        }
    }
    
    /**
     * Gets the "ULTERIORI_SOMME" attribute
     */
    public java.math.BigDecimal getULTERIORISOMME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ULTERIORISOMME$16);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "ULTERIORI_SOMME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetULTERIORISOMME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ULTERIORISOMME$16);
            return target;
        }
    }
    
    /**
     * True if has "ULTERIORI_SOMME" attribute
     */
    public boolean isSetULTERIORISOMME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ULTERIORISOMME$16) != null;
        }
    }
    
    /**
     * Sets the "ULTERIORI_SOMME" attribute
     */
    public void setULTERIORISOMME(java.math.BigDecimal ulteriorisomme)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ULTERIORISOMME$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ULTERIORISOMME$16);
            }
            target.setBigDecimalValue(ulteriorisomme);
        }
    }
    
    /**
     * Sets (as xml) the "ULTERIORI_SOMME" attribute
     */
    public void xsetULTERIORISOMME(it.avlp.simog.massload.xmlbeans.ImportoType ulteriorisomme)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ULTERIORISOMME$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(ULTERIORISOMME$16);
            }
            target.set(ulteriorisomme);
        }
    }
    
    /**
     * Unsets the "ULTERIORI_SOMME" attribute
     */
    public void unsetULTERIORISOMME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ULTERIORISOMME$16);
        }
    }
    
    /**
     * Gets the "DATA_ATTO_AGGIUNTIVO" attribute
     */
    public java.util.Calendar getDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAATTOAGGIUNTIVO$18);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ATTO_AGGIUNTIVO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAATTOAGGIUNTIVO$18);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ATTO_AGGIUNTIVO" attribute
     */
    public boolean isSetDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAATTOAGGIUNTIVO$18) != null;
        }
    }
    
    /**
     * Sets the "DATA_ATTO_AGGIUNTIVO" attribute
     */
    public void setDATAATTOAGGIUNTIVO(java.util.Calendar dataattoaggiuntivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAATTOAGGIUNTIVO$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAATTOAGGIUNTIVO$18);
            }
            target.setCalendarValue(dataattoaggiuntivo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ATTO_AGGIUNTIVO" attribute
     */
    public void xsetDATAATTOAGGIUNTIVO(it.avlp.simog.massload.xmlbeans.DbDateType dataattoaggiuntivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAATTOAGGIUNTIVO$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAATTOAGGIUNTIVO$18);
            }
            target.set(dataattoaggiuntivo);
        }
    }
    
    /**
     * Unsets the "DATA_ATTO_AGGIUNTIVO" attribute
     */
    public void unsetDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAATTOAGGIUNTIVO$18);
        }
    }
    
    /**
     * Gets the "NUM_GIORNI_PROROGA" attribute
     */
    public int getNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNIPROROGA$20);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_GIORNI_PROROGA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNIPROROGA$20);
            return target;
        }
    }
    
    /**
     * True if has "NUM_GIORNI_PROROGA" attribute
     */
    public boolean isSetNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMGIORNIPROROGA$20) != null;
        }
    }
    
    /**
     * Sets the "NUM_GIORNI_PROROGA" attribute
     */
    public void setNUMGIORNIPROROGA(int numgiorniproroga)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNIPROROGA$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMGIORNIPROROGA$20);
            }
            target.setIntValue(numgiorniproroga);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_GIORNI_PROROGA" attribute
     */
    public void xsetNUMGIORNIPROROGA(it.avlp.simog.massload.xmlbeans.InteroType numgiorniproroga)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNIPROROGA$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMGIORNIPROROGA$20);
            }
            target.set(numgiorniproroga);
        }
    }
    
    /**
     * Unsets the "NUM_GIORNI_PROROGA" attribute
     */
    public void unsetNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMGIORNIPROROGA$20);
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
     * Gets the "CIG_PROCEDURA" attribute
     */
    public java.lang.String getCIGPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGPROCEDURA$28);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG_PROCEDURA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CigType xgetCIGPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGPROCEDURA$28);
            return target;
        }
    }
    
    /**
     * True if has "CIG_PROCEDURA" attribute
     */
    public boolean isSetCIGPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CIGPROCEDURA$28) != null;
        }
    }
    
    /**
     * Sets the "CIG_PROCEDURA" attribute
     */
    public void setCIGPROCEDURA(java.lang.String cigprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIGPROCEDURA$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIGPROCEDURA$28);
            }
            target.setStringValue(cigprocedura);
        }
    }
    
    /**
     * Sets (as xml) the "CIG_PROCEDURA" attribute
     */
    public void xsetCIGPROCEDURA(it.avlp.simog.massload.xmlbeans.CigType cigprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CigType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().find_attribute_user(CIGPROCEDURA$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CigType)get_store().add_attribute_user(CIGPROCEDURA$28);
            }
            target.set(cigprocedura);
        }
    }
    
    /**
     * Unsets the "CIG_PROCEDURA" attribute
     */
    public void unsetCIGPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CIGPROCEDURA$28);
        }
    }
    
    /**
     * Gets the "LINK_VARIANTI" attribute
     */
    public java.lang.String getLINKVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LINKVARIANTI$30);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LINK_VARIANTI" attribute
     */
    public org.apache.xmlbeans.XmlString xgetLINKVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LINKVARIANTI$30);
            return target;
        }
    }
    
    /**
     * True if has "LINK_VARIANTI" attribute
     */
    public boolean isSetLINKVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LINKVARIANTI$30) != null;
        }
    }
    
    /**
     * Sets the "LINK_VARIANTI" attribute
     */
    public void setLINKVARIANTI(java.lang.String linkvarianti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LINKVARIANTI$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LINKVARIANTI$30);
            }
            target.setStringValue(linkvarianti);
        }
    }
    
    /**
     * Sets (as xml) the "LINK_VARIANTI" attribute
     */
    public void xsetLINKVARIANTI(org.apache.xmlbeans.XmlString linkvarianti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LINKVARIANTI$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(LINKVARIANTI$30);
            }
            target.set(linkvarianti);
        }
    }
    
    /**
     * Unsets the "LINK_VARIANTI" attribute
     */
    public void unsetLINKVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LINKVARIANTI$30);
        }
    }
    
    /**
     * Gets the "ID_MOTIVO_REV_PREZZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType.Enum getIDMOTIVOREVPREZZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOREVPREZZI$32);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_REV_PREZZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType xgetIDMOTIVOREVPREZZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType)get_store().find_attribute_user(IDMOTIVOREVPREZZI$32);
            return target;
        }
    }
    
    /**
     * True if has "ID_MOTIVO_REV_PREZZI" attribute
     */
    public boolean isSetIDMOTIVOREVPREZZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDMOTIVOREVPREZZI$32) != null;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_REV_PREZZI" attribute
     */
    public void setIDMOTIVOREVPREZZI(it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType.Enum idmotivorevprezzi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOREVPREZZI$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDMOTIVOREVPREZZI$32);
            }
            target.setEnumValue(idmotivorevprezzi);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_REV_PREZZI" attribute
     */
    public void xsetIDMOTIVOREVPREZZI(it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType idmotivorevprezzi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType)get_store().find_attribute_user(IDMOTIVOREVPREZZI$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType)get_store().add_attribute_user(IDMOTIVOREVPREZZI$32);
            }
            target.set(idmotivorevprezzi);
        }
    }
    
    /**
     * Unsets the "ID_MOTIVO_REV_PREZZI" attribute
     */
    public void unsetIDMOTIVOREVPREZZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDMOTIVOREVPREZZI$32);
        }
    }
    /**
     * An XML ALTRE_MOTIVAZIONI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RecVarianteType$ALTREMOTIVAZIONI.
     */
    public static class ALTREMOTIVAZIONIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RecVarianteType.ALTREMOTIVAZIONI
    {
        
        public ALTREMOTIVAZIONIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ALTREMOTIVAZIONIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
