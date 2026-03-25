/*
 * XML Type:  ConclusioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ConclusioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ConclusioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ConclusioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ConclusioneType
{
    
    public ConclusioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDMOTIVOINTERR$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_MOTIVO_INTERR");
    private static final javax.xml.namespace.QName IDMOTIVORISOL$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_MOTIVO_RISOL");
    private static final javax.xml.namespace.QName DATARISOLUZIONE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_RISOLUZIONE");
    private static final javax.xml.namespace.QName FLAGONERI$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_ONERI");
    private static final javax.xml.namespace.QName ONERIRISOLUZIONE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ONERI_RISOLUZIONE");
    private static final javax.xml.namespace.QName FLAGPOLIZZA$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_POLIZZA");
    private static final javax.xml.namespace.QName DATAULTIMAZIONE$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_ULTIMAZIONE");
    private static final javax.xml.namespace.QName NUMINFORTUNI$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_INFORTUNI");
    private static final javax.xml.namespace.QName NUMINFPERM$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_INF_PERM");
    private static final javax.xml.namespace.QName NUMINFMORT$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_INF_MORT");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName TERMINECONTRATTULTIMAZIONE$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TERMINE_CONTRATT_ULTIMAZIONE");
    private static final javax.xml.namespace.QName NUMGIORNIPROROGA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_GIORNI_PROROGA");
    private static final javax.xml.namespace.QName DATAVERBCONSEGNAAVVIO$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_VERB_CONSEGNA_AVVIO");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets the "ID_MOTIVO_INTERR" attribute
     */
    public java.lang.String getIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOINTERR$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_INTERR" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoInterruzioneType xgetIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoInterruzioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoInterruzioneType)get_store().find_attribute_user(IDMOTIVOINTERR$0);
            return target;
        }
    }
    
    /**
     * True if has "ID_MOTIVO_INTERR" attribute
     */
    public boolean isSetIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDMOTIVOINTERR$0) != null;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_INTERR" attribute
     */
    public void setIDMOTIVOINTERR(java.lang.String idmotivointerr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOINTERR$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDMOTIVOINTERR$0);
            }
            target.setStringValue(idmotivointerr);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_INTERR" attribute
     */
    public void xsetIDMOTIVOINTERR(it.avlp.simog.massload.xmlbeans.MotivoInterruzioneType idmotivointerr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoInterruzioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoInterruzioneType)get_store().find_attribute_user(IDMOTIVOINTERR$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotivoInterruzioneType)get_store().add_attribute_user(IDMOTIVOINTERR$0);
            }
            target.set(idmotivointerr);
        }
    }
    
    /**
     * Unsets the "ID_MOTIVO_INTERR" attribute
     */
    public void unsetIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDMOTIVOINTERR$0);
        }
    }
    
    /**
     * Gets the "ID_MOTIVO_RISOL" attribute
     */
    public java.lang.String getIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVORISOL$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_RISOL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoRisoluzioneType xgetIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoRisoluzioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoRisoluzioneType)get_store().find_attribute_user(IDMOTIVORISOL$2);
            return target;
        }
    }
    
    /**
     * True if has "ID_MOTIVO_RISOL" attribute
     */
    public boolean isSetIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDMOTIVORISOL$2) != null;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_RISOL" attribute
     */
    public void setIDMOTIVORISOL(java.lang.String idmotivorisol)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVORISOL$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDMOTIVORISOL$2);
            }
            target.setStringValue(idmotivorisol);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_RISOL" attribute
     */
    public void xsetIDMOTIVORISOL(it.avlp.simog.massload.xmlbeans.MotivoRisoluzioneType idmotivorisol)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoRisoluzioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoRisoluzioneType)get_store().find_attribute_user(IDMOTIVORISOL$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotivoRisoluzioneType)get_store().add_attribute_user(IDMOTIVORISOL$2);
            }
            target.set(idmotivorisol);
        }
    }
    
    /**
     * Unsets the "ID_MOTIVO_RISOL" attribute
     */
    public void unsetIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDMOTIVORISOL$2);
        }
    }
    
    /**
     * Gets the "DATA_RISOLUZIONE" attribute
     */
    public java.util.Calendar getDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATARISOLUZIONE$4);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_RISOLUZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATARISOLUZIONE$4);
            return target;
        }
    }
    
    /**
     * True if has "DATA_RISOLUZIONE" attribute
     */
    public boolean isSetDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATARISOLUZIONE$4) != null;
        }
    }
    
    /**
     * Sets the "DATA_RISOLUZIONE" attribute
     */
    public void setDATARISOLUZIONE(java.util.Calendar datarisoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATARISOLUZIONE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATARISOLUZIONE$4);
            }
            target.setCalendarValue(datarisoluzione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_RISOLUZIONE" attribute
     */
    public void xsetDATARISOLUZIONE(it.avlp.simog.massload.xmlbeans.DbDateType datarisoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATARISOLUZIONE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATARISOLUZIONE$4);
            }
            target.set(datarisoluzione);
        }
    }
    
    /**
     * Unsets the "DATA_RISOLUZIONE" attribute
     */
    public void unsetDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATARISOLUZIONE$4);
        }
    }
    
    /**
     * Gets the "FLAG_ONERI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagOneriType.Enum getFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGONERI$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagOneriType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ONERI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagOneriType xgetFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagOneriType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagOneriType)get_store().find_attribute_user(FLAGONERI$6);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_ONERI" attribute
     */
    public boolean isSetFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGONERI$6) != null;
        }
    }
    
    /**
     * Sets the "FLAG_ONERI" attribute
     */
    public void setFLAGONERI(it.avlp.simog.massload.xmlbeans.FlagOneriType.Enum flagoneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGONERI$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGONERI$6);
            }
            target.setEnumValue(flagoneri);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ONERI" attribute
     */
    public void xsetFLAGONERI(it.avlp.simog.massload.xmlbeans.FlagOneriType flagoneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagOneriType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagOneriType)get_store().find_attribute_user(FLAGONERI$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagOneriType)get_store().add_attribute_user(FLAGONERI$6);
            }
            target.set(flagoneri);
        }
    }
    
    /**
     * Unsets the "FLAG_ONERI" attribute
     */
    public void unsetFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGONERI$6);
        }
    }
    
    /**
     * Gets the "ONERI_RISOLUZIONE" attribute
     */
    public java.math.BigDecimal getONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ONERIRISOLUZIONE$8);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_RISOLUZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ONERIRISOLUZIONE$8);
            return target;
        }
    }
    
    /**
     * True if has "ONERI_RISOLUZIONE" attribute
     */
    public boolean isSetONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ONERIRISOLUZIONE$8) != null;
        }
    }
    
    /**
     * Sets the "ONERI_RISOLUZIONE" attribute
     */
    public void setONERIRISOLUZIONE(java.math.BigDecimal oneririsoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ONERIRISOLUZIONE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ONERIRISOLUZIONE$8);
            }
            target.setBigDecimalValue(oneririsoluzione);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_RISOLUZIONE" attribute
     */
    public void xsetONERIRISOLUZIONE(it.avlp.simog.massload.xmlbeans.ImportoType oneririsoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ONERIRISOLUZIONE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(ONERIRISOLUZIONE$8);
            }
            target.set(oneririsoluzione);
        }
    }
    
    /**
     * Unsets the "ONERI_RISOLUZIONE" attribute
     */
    public void unsetONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ONERIRISOLUZIONE$8);
        }
    }
    
    /**
     * Gets the "FLAG_POLIZZA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGPOLIZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPOLIZZA$10);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_POLIZZA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGPOLIZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGPOLIZZA$10);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_POLIZZA" attribute
     */
    public boolean isSetFLAGPOLIZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGPOLIZZA$10) != null;
        }
    }
    
    /**
     * Sets the "FLAG_POLIZZA" attribute
     */
    public void setFLAGPOLIZZA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagpolizza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPOLIZZA$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGPOLIZZA$10);
            }
            target.setEnumValue(flagpolizza);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_POLIZZA" attribute
     */
    public void xsetFLAGPOLIZZA(it.avlp.simog.massload.xmlbeans.FlagSNType flagpolizza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGPOLIZZA$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGPOLIZZA$10);
            }
            target.set(flagpolizza);
        }
    }
    
    /**
     * Unsets the "FLAG_POLIZZA" attribute
     */
    public void unsetFLAGPOLIZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGPOLIZZA$10);
        }
    }
    
    /**
     * Gets the "DATA_ULTIMAZIONE" attribute
     */
    public java.util.Calendar getDATAULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAULTIMAZIONE$12);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ULTIMAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAULTIMAZIONE$12);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ULTIMAZIONE" attribute
     */
    public boolean isSetDATAULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAULTIMAZIONE$12) != null;
        }
    }
    
    /**
     * Sets the "DATA_ULTIMAZIONE" attribute
     */
    public void setDATAULTIMAZIONE(java.util.Calendar dataultimazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAULTIMAZIONE$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAULTIMAZIONE$12);
            }
            target.setCalendarValue(dataultimazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ULTIMAZIONE" attribute
     */
    public void xsetDATAULTIMAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType dataultimazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAULTIMAZIONE$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAULTIMAZIONE$12);
            }
            target.set(dataultimazione);
        }
    }
    
    /**
     * Unsets the "DATA_ULTIMAZIONE" attribute
     */
    public void unsetDATAULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAULTIMAZIONE$12);
        }
    }
    
    /**
     * Gets the "NUM_INFORTUNI" attribute
     */
    public int getNUMINFORTUNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMINFORTUNI$14);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_INFORTUNI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMINFORTUNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMINFORTUNI$14);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_INFORTUNI" attribute
     */
    public void setNUMINFORTUNI(int numinfortuni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMINFORTUNI$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMINFORTUNI$14);
            }
            target.setIntValue(numinfortuni);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_INFORTUNI" attribute
     */
    public void xsetNUMINFORTUNI(it.avlp.simog.massload.xmlbeans.InteroType numinfortuni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMINFORTUNI$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMINFORTUNI$14);
            }
            target.set(numinfortuni);
        }
    }
    
    /**
     * Gets the "NUM_INF_PERM" attribute
     */
    public int getNUMINFPERM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMINFPERM$16);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_INF_PERM" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMINFPERM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMINFPERM$16);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_INF_PERM" attribute
     */
    public void setNUMINFPERM(int numinfperm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMINFPERM$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMINFPERM$16);
            }
            target.setIntValue(numinfperm);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_INF_PERM" attribute
     */
    public void xsetNUMINFPERM(it.avlp.simog.massload.xmlbeans.InteroType numinfperm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMINFPERM$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMINFPERM$16);
            }
            target.set(numinfperm);
        }
    }
    
    /**
     * Gets the "NUM_INF_MORT" attribute
     */
    public int getNUMINFMORT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMINFMORT$18);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_INF_MORT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMINFMORT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMINFMORT$18);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_INF_MORT" attribute
     */
    public void setNUMINFMORT(int numinfmort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMINFMORT$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMINFMORT$18);
            }
            target.setIntValue(numinfmort);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_INF_MORT" attribute
     */
    public void xsetNUMINFMORT(it.avlp.simog.massload.xmlbeans.InteroType numinfmort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMINFMORT$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMINFMORT$18);
            }
            target.set(numinfmort);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$20);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$20);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$20) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$20);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$20);
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
            get_store().remove_attribute(IDSCHEDALOCALE$20);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$22);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$22);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$22) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$22);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$22);
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
            get_store().remove_attribute(IDSCHEDASIMOG$22);
        }
    }
    
    /**
     * Gets the "TERMINE_CONTRATT_ULTIMAZIONE" attribute
     */
    public java.util.Calendar getTERMINECONTRATTULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TERMINECONTRATTULTIMAZIONE$24);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "TERMINE_CONTRATT_ULTIMAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetTERMINECONTRATTULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(TERMINECONTRATTULTIMAZIONE$24);
            return target;
        }
    }
    
    /**
     * True if has "TERMINE_CONTRATT_ULTIMAZIONE" attribute
     */
    public boolean isSetTERMINECONTRATTULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TERMINECONTRATTULTIMAZIONE$24) != null;
        }
    }
    
    /**
     * Sets the "TERMINE_CONTRATT_ULTIMAZIONE" attribute
     */
    public void setTERMINECONTRATTULTIMAZIONE(java.util.Calendar terminecontrattultimazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TERMINECONTRATTULTIMAZIONE$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TERMINECONTRATTULTIMAZIONE$24);
            }
            target.setCalendarValue(terminecontrattultimazione);
        }
    }
    
    /**
     * Sets (as xml) the "TERMINE_CONTRATT_ULTIMAZIONE" attribute
     */
    public void xsetTERMINECONTRATTULTIMAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType terminecontrattultimazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(TERMINECONTRATTULTIMAZIONE$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(TERMINECONTRATTULTIMAZIONE$24);
            }
            target.set(terminecontrattultimazione);
        }
    }
    
    /**
     * Unsets the "TERMINE_CONTRATT_ULTIMAZIONE" attribute
     */
    public void unsetTERMINECONTRATTULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TERMINECONTRATTULTIMAZIONE$24);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNIPROROGA$26);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNIPROROGA$26);
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
            return get_store().find_attribute_user(NUMGIORNIPROROGA$26) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNIPROROGA$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMGIORNIPROROGA$26);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNIPROROGA$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMGIORNIPROROGA$26);
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
            get_store().remove_attribute(NUMGIORNIPROROGA$26);
        }
    }
    
    /**
     * Gets the "DATA_VERB_CONSEGNA_AVVIO" attribute
     */
    public java.util.Calendar getDATAVERBCONSEGNAAVVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBCONSEGNAAVVIO$28);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_CONSEGNA_AVVIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAVERBCONSEGNAAVVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBCONSEGNAAVVIO$28);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERB_CONSEGNA_AVVIO" attribute
     */
    public boolean isSetDATAVERBCONSEGNAAVVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAVERBCONSEGNAAVVIO$28) != null;
        }
    }
    
    /**
     * Sets the "DATA_VERB_CONSEGNA_AVVIO" attribute
     */
    public void setDATAVERBCONSEGNAAVVIO(java.util.Calendar dataverbconsegnaavvio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAVERBCONSEGNAAVVIO$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAVERBCONSEGNAAVVIO$28);
            }
            target.setCalendarValue(dataverbconsegnaavvio);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_CONSEGNA_AVVIO" attribute
     */
    public void xsetDATAVERBCONSEGNAAVVIO(it.avlp.simog.massload.xmlbeans.DbDateType dataverbconsegnaavvio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAVERBCONSEGNAAVVIO$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAVERBCONSEGNAAVVIO$28);
            }
            target.set(dataverbconsegnaavvio);
        }
    }
    
    /**
     * Unsets the "DATA_VERB_CONSEGNA_AVVIO" attribute
     */
    public void unsetDATAVERBCONSEGNAAVVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAVERBCONSEGNAAVVIO$28);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$30);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$30);
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
            return get_store().find_attribute_user(IDSTATOSCHEDA$30) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$30);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$30);
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
            get_store().remove_attribute(IDSTATOSCHEDA$30);
        }
    }
}
