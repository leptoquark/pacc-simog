/*
 * XML Type:  AvanzamentoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AvanzamentoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AvanzamentoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AvanzamentoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AvanzamentoType
{
    
    public AvanzamentoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FLAGPAGAMENTO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_PAGAMENTO");
    private static final javax.xml.namespace.QName DATAANTICIPAZIONE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_ANTICIPAZIONE");
    private static final javax.xml.namespace.QName IMPORTOANTICIPAZIONE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_ANTICIPAZIONE");
    private static final javax.xml.namespace.QName DATARAGGIUNGIMENTO$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_RAGGIUNGIMENTO");
    private static final javax.xml.namespace.QName IMPORTOSAL$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_SAL");
    private static final javax.xml.namespace.QName DATACERTIFICATO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_CERTIFICATO");
    private static final javax.xml.namespace.QName IMPORTOCERTIFICATO$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_CERTIFICATO");
    private static final javax.xml.namespace.QName FLAGRITARDO$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_RITARDO");
    private static final javax.xml.namespace.QName NUMGIORNISCOST$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_GIORNI_SCOST");
    private static final javax.xml.namespace.QName NUMGIORNIPROROGA$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_GIORNI_PROROGA");
    private static final javax.xml.namespace.QName DENOMAVANZAMENTO$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DENOM_AVANZAMENTO");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets the "FLAG_PAGAMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagPagamentoType.Enum getFLAGPAGAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPAGAMENTO$0);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagPagamentoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_PAGAMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagPagamentoType xgetFLAGPAGAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagPagamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagPagamentoType)get_store().find_attribute_user(FLAGPAGAMENTO$0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_PAGAMENTO" attribute
     */
    public void setFLAGPAGAMENTO(it.avlp.simog.massload.xmlbeans.FlagPagamentoType.Enum flagpagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGPAGAMENTO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGPAGAMENTO$0);
            }
            target.setEnumValue(flagpagamento);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_PAGAMENTO" attribute
     */
    public void xsetFLAGPAGAMENTO(it.avlp.simog.massload.xmlbeans.FlagPagamentoType flagpagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagPagamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagPagamentoType)get_store().find_attribute_user(FLAGPAGAMENTO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagPagamentoType)get_store().add_attribute_user(FLAGPAGAMENTO$0);
            }
            target.set(flagpagamento);
        }
    }
    
    /**
     * Gets the "DATA_ANTICIPAZIONE" attribute
     */
    public java.util.Calendar getDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAANTICIPAZIONE$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ANTICIPAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAANTICIPAZIONE$2);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ANTICIPAZIONE" attribute
     */
    public boolean isSetDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAANTICIPAZIONE$2) != null;
        }
    }
    
    /**
     * Sets the "DATA_ANTICIPAZIONE" attribute
     */
    public void setDATAANTICIPAZIONE(java.util.Calendar dataanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAANTICIPAZIONE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAANTICIPAZIONE$2);
            }
            target.setCalendarValue(dataanticipazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ANTICIPAZIONE" attribute
     */
    public void xsetDATAANTICIPAZIONE(it.avlp.simog.massload.xmlbeans.DbDateType dataanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAANTICIPAZIONE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAANTICIPAZIONE$2);
            }
            target.set(dataanticipazione);
        }
    }
    
    /**
     * Unsets the "DATA_ANTICIPAZIONE" attribute
     */
    public void unsetDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAANTICIPAZIONE$2);
        }
    }
    
    /**
     * Gets the "IMPORTO_ANTICIPAZIONE" attribute
     */
    public java.math.BigDecimal getIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOANTICIPAZIONE$4);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_ANTICIPAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOANTICIPAZIONE$4);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_ANTICIPAZIONE" attribute
     */
    public boolean isSetIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOANTICIPAZIONE$4) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_ANTICIPAZIONE" attribute
     */
    public void setIMPORTOANTICIPAZIONE(java.math.BigDecimal importoanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOANTICIPAZIONE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOANTICIPAZIONE$4);
            }
            target.setBigDecimalValue(importoanticipazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_ANTICIPAZIONE" attribute
     */
    public void xsetIMPORTOANTICIPAZIONE(it.avlp.simog.massload.xmlbeans.ImportoType importoanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOANTICIPAZIONE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOANTICIPAZIONE$4);
            }
            target.set(importoanticipazione);
        }
    }
    
    /**
     * Unsets the "IMPORTO_ANTICIPAZIONE" attribute
     */
    public void unsetIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOANTICIPAZIONE$4);
        }
    }
    
    /**
     * Gets the "DATA_RAGGIUNGIMENTO" attribute
     */
    public java.util.Calendar getDATARAGGIUNGIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATARAGGIUNGIMENTO$6);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_RAGGIUNGIMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATARAGGIUNGIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATARAGGIUNGIMENTO$6);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_RAGGIUNGIMENTO" attribute
     */
    public void setDATARAGGIUNGIMENTO(java.util.Calendar dataraggiungimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATARAGGIUNGIMENTO$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATARAGGIUNGIMENTO$6);
            }
            target.setCalendarValue(dataraggiungimento);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_RAGGIUNGIMENTO" attribute
     */
    public void xsetDATARAGGIUNGIMENTO(it.avlp.simog.massload.xmlbeans.DbDateType dataraggiungimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATARAGGIUNGIMENTO$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATARAGGIUNGIMENTO$6);
            }
            target.set(dataraggiungimento);
        }
    }
    
    /**
     * Gets the "IMPORTO_SAL" attribute
     */
    public java.math.BigDecimal getIMPORTOSAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOSAL$8);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_SAL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOSAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOSAL$8);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_SAL" attribute
     */
    public void setIMPORTOSAL(java.math.BigDecimal importosal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOSAL$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOSAL$8);
            }
            target.setBigDecimalValue(importosal);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_SAL" attribute
     */
    public void xsetIMPORTOSAL(it.avlp.simog.massload.xmlbeans.ImportoType importosal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOSAL$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOSAL$8);
            }
            target.set(importosal);
        }
    }
    
    /**
     * Gets the "DATA_CERTIFICATO" attribute
     */
    public java.util.Calendar getDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACERTIFICATO$10);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CERTIFICATO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACERTIFICATO$10);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CERTIFICATO" attribute
     */
    public boolean isSetDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATACERTIFICATO$10) != null;
        }
    }
    
    /**
     * Sets the "DATA_CERTIFICATO" attribute
     */
    public void setDATACERTIFICATO(java.util.Calendar datacertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACERTIFICATO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATACERTIFICATO$10);
            }
            target.setCalendarValue(datacertificato);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CERTIFICATO" attribute
     */
    public void xsetDATACERTIFICATO(it.avlp.simog.massload.xmlbeans.DbDateType datacertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACERTIFICATO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATACERTIFICATO$10);
            }
            target.set(datacertificato);
        }
    }
    
    /**
     * Unsets the "DATA_CERTIFICATO" attribute
     */
    public void unsetDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATACERTIFICATO$10);
        }
    }
    
    /**
     * Gets the "IMPORTO_CERTIFICATO" attribute
     */
    public java.math.BigDecimal getIMPORTOCERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOCERTIFICATO$12);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_CERTIFICATO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOCERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOCERTIFICATO$12);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_CERTIFICATO" attribute
     */
    public boolean isSetIMPORTOCERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPORTOCERTIFICATO$12) != null;
        }
    }
    
    /**
     * Sets the "IMPORTO_CERTIFICATO" attribute
     */
    public void setIMPORTOCERTIFICATO(java.math.BigDecimal importocertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOCERTIFICATO$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOCERTIFICATO$12);
            }
            target.setBigDecimalValue(importocertificato);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_CERTIFICATO" attribute
     */
    public void xsetIMPORTOCERTIFICATO(it.avlp.simog.massload.xmlbeans.ImportoType importocertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOCERTIFICATO$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOCERTIFICATO$12);
            }
            target.set(importocertificato);
        }
    }
    
    /**
     * Unsets the "IMPORTO_CERTIFICATO" attribute
     */
    public void unsetIMPORTOCERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPORTOCERTIFICATO$12);
        }
    }
    
    /**
     * Gets the "FLAG_RITARDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagRitardoType.Enum getFLAGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRITARDO$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagRitardoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RITARDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagRitardoType xgetFLAGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagRitardoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagRitardoType)get_store().find_attribute_user(FLAGRITARDO$14);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RITARDO" attribute
     */
    public void setFLAGRITARDO(it.avlp.simog.massload.xmlbeans.FlagRitardoType.Enum flagritardo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGRITARDO$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGRITARDO$14);
            }
            target.setEnumValue(flagritardo);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RITARDO" attribute
     */
    public void xsetFLAGRITARDO(it.avlp.simog.massload.xmlbeans.FlagRitardoType flagritardo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagRitardoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagRitardoType)get_store().find_attribute_user(FLAGRITARDO$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagRitardoType)get_store().add_attribute_user(FLAGRITARDO$14);
            }
            target.set(flagritardo);
        }
    }
    
    /**
     * Gets the "NUM_GIORNI_SCOST" attribute
     */
    public int getNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNISCOST$16);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_GIORNI_SCOST" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNISCOST$16);
            return target;
        }
    }
    
    /**
     * True if has "NUM_GIORNI_SCOST" attribute
     */
    public boolean isSetNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMGIORNISCOST$16) != null;
        }
    }
    
    /**
     * Sets the "NUM_GIORNI_SCOST" attribute
     */
    public void setNUMGIORNISCOST(int numgiorniscost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNISCOST$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMGIORNISCOST$16);
            }
            target.setIntValue(numgiorniscost);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_GIORNI_SCOST" attribute
     */
    public void xsetNUMGIORNISCOST(it.avlp.simog.massload.xmlbeans.InteroType numgiorniscost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNISCOST$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMGIORNISCOST$16);
            }
            target.set(numgiorniscost);
        }
    }
    
    /**
     * Unsets the "NUM_GIORNI_SCOST" attribute
     */
    public void unsetNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMGIORNISCOST$16);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNIPROROGA$18);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNIPROROGA$18);
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
            return get_store().find_attribute_user(NUMGIORNIPROROGA$18) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMGIORNIPROROGA$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMGIORNIPROROGA$18);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMGIORNIPROROGA$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMGIORNIPROROGA$18);
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
            get_store().remove_attribute(NUMGIORNIPROROGA$18);
        }
    }
    
    /**
     * Gets the "DENOM_AVANZAMENTO" attribute
     */
    public java.lang.String getDENOMAVANZAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENOMAVANZAMENTO$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DENOM_AVANZAMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO xgetDENOMAVANZAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO)get_store().find_attribute_user(DENOMAVANZAMENTO$20);
            return target;
        }
    }
    
    /**
     * True if has "DENOM_AVANZAMENTO" attribute
     */
    public boolean isSetDENOMAVANZAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DENOMAVANZAMENTO$20) != null;
        }
    }
    
    /**
     * Sets the "DENOM_AVANZAMENTO" attribute
     */
    public void setDENOMAVANZAMENTO(java.lang.String denomavanzamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENOMAVANZAMENTO$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DENOMAVANZAMENTO$20);
            }
            target.setStringValue(denomavanzamento);
        }
    }
    
    /**
     * Sets (as xml) the "DENOM_AVANZAMENTO" attribute
     */
    public void xsetDENOMAVANZAMENTO(it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO denomavanzamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO)get_store().find_attribute_user(DENOMAVANZAMENTO$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO)get_store().add_attribute_user(DENOMAVANZAMENTO$20);
            }
            target.set(denomavanzamento);
        }
    }
    
    /**
     * Unsets the "DENOM_AVANZAMENTO" attribute
     */
    public void unsetDENOMAVANZAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DENOMAVANZAMENTO$20);
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
     * An XML DENOM_AVANZAMENTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AvanzamentoType$DENOMAVANZAMENTO.
     */
    public static class DENOMAVANZAMENTOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AvanzamentoType.DENOMAVANZAMENTO
    {
        
        public DENOMAVANZAMENTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DENOMAVANZAMENTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
