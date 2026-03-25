/*
 * XML Type:  CollaudoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CollaudoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML CollaudoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CollaudoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CollaudoType
{
    
    public CollaudoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAREGOLAREESEC$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_REGOLARE_ESEC");
    private static final javax.xml.namespace.QName DATACOLLAUDOSTAT$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_COLLAUDO_STAT");
    private static final javax.xml.namespace.QName MODOCOLLAUDO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MODO_COLLAUDO");
    private static final javax.xml.namespace.QName DATANOMINACOLL$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_NOMINA_COLL");
    private static final javax.xml.namespace.QName DATAINIZIOOPER$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_INIZIO_OPER");
    private static final javax.xml.namespace.QName DATACERTCOLLAUDO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_CERT_COLLAUDO");
    private static final javax.xml.namespace.QName DATADELIBERA$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_DELIBERA");
    private static final javax.xml.namespace.QName ESITOCOLLAUDO$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ESITO_COLLAUDO");
    private static final javax.xml.namespace.QName IMPFINALELAVORI$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_FINALE_LAVORI");
    private static final javax.xml.namespace.QName IMPFINALESERVIZI$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_FINALE_SERVIZI");
    private static final javax.xml.namespace.QName IMPFINALEFORNIT$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_FINALE_FORNIT");
    private static final javax.xml.namespace.QName IMPFINALESECUR$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_FINALE_SECUR");
    private static final javax.xml.namespace.QName IMPPROGETTAZIONE$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_PROGETTAZIONE");
    private static final javax.xml.namespace.QName IMPDISPOSIZIONE$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMP_DISPOSIZIONE");
    private static final javax.xml.namespace.QName AMMNUMDEFINITE$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AMM_NUM_DEFINITE");
    private static final javax.xml.namespace.QName AMMNUMDADEF$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AMM_NUM_DADEF");
    private static final javax.xml.namespace.QName AMMIMPORTORICH$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AMM_IMPORTO_RICH");
    private static final javax.xml.namespace.QName AMMIMPORTODEF$34 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AMM_IMPORTO_DEF");
    private static final javax.xml.namespace.QName ARBNUMDEFINITE$36 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ARB_NUM_DEFINITE");
    private static final javax.xml.namespace.QName ARBNUMDADEF$38 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ARB_NUM_DADEF");
    private static final javax.xml.namespace.QName ARBIMPORTORICH$40 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ARB_IMPORTO_RICH");
    private static final javax.xml.namespace.QName ARBIMPORTODEF$42 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ARB_IMPORTO_DEF");
    private static final javax.xml.namespace.QName GIUNUMDEFINITE$44 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "GIU_NUM_DEFINITE");
    private static final javax.xml.namespace.QName GIUNUMDADEF$46 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "GIU_NUM_DADEF");
    private static final javax.xml.namespace.QName GIUIMPORTORICH$48 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "GIU_IMPORTO_RICH");
    private static final javax.xml.namespace.QName GIUIMPORTODEF$50 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "GIU_IMPORTO_DEF");
    private static final javax.xml.namespace.QName TRANUMDEFINITE$52 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TRA_NUM_DEFINITE");
    private static final javax.xml.namespace.QName TRANUMDADEF$54 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TRA_NUM_DADEF");
    private static final javax.xml.namespace.QName TRAIMPORTORICH$56 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TRA_IMPORTO_RICH");
    private static final javax.xml.namespace.QName TRAIMPORTODEF$58 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TRA_IMPORTO_DEF");
    private static final javax.xml.namespace.QName LAVORIESTESI$60 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LAVORI_ESTESI");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$62 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$64 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName IDSTATOSCHEDA$66 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_STATO_SCHEDA");
    
    
    /**
     * Gets the "DATA_REGOLARE_ESEC" attribute
     */
    public java.util.Calendar getDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAREGOLAREESEC$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_REGOLARE_ESEC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAREGOLAREESEC$0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_REGOLARE_ESEC" attribute
     */
    public boolean isSetDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAREGOLAREESEC$0) != null;
        }
    }
    
    /**
     * Sets the "DATA_REGOLARE_ESEC" attribute
     */
    public void setDATAREGOLAREESEC(java.util.Calendar dataregolareesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAREGOLAREESEC$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAREGOLAREESEC$0);
            }
            target.setCalendarValue(dataregolareesec);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_REGOLARE_ESEC" attribute
     */
    public void xsetDATAREGOLAREESEC(it.avlp.simog.massload.xmlbeans.DbDateType dataregolareesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAREGOLAREESEC$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAREGOLAREESEC$0);
            }
            target.set(dataregolareesec);
        }
    }
    
    /**
     * Unsets the "DATA_REGOLARE_ESEC" attribute
     */
    public void unsetDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAREGOLAREESEC$0);
        }
    }
    
    /**
     * Gets the "DATA_COLLAUDO_STAT" attribute
     */
    public java.util.Calendar getDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACOLLAUDOSTAT$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_COLLAUDO_STAT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACOLLAUDOSTAT$2);
            return target;
        }
    }
    
    /**
     * True if has "DATA_COLLAUDO_STAT" attribute
     */
    public boolean isSetDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATACOLLAUDOSTAT$2) != null;
        }
    }
    
    /**
     * Sets the "DATA_COLLAUDO_STAT" attribute
     */
    public void setDATACOLLAUDOSTAT(java.util.Calendar datacollaudostat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACOLLAUDOSTAT$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATACOLLAUDOSTAT$2);
            }
            target.setCalendarValue(datacollaudostat);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_COLLAUDO_STAT" attribute
     */
    public void xsetDATACOLLAUDOSTAT(it.avlp.simog.massload.xmlbeans.DbDateType datacollaudostat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACOLLAUDOSTAT$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATACOLLAUDOSTAT$2);
            }
            target.set(datacollaudostat);
        }
    }
    
    /**
     * Unsets the "DATA_COLLAUDO_STAT" attribute
     */
    public void unsetDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATACOLLAUDOSTAT$2);
        }
    }
    
    /**
     * Gets the "MODO_COLLAUDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType.Enum getMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODOCOLLAUDO$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "MODO_COLLAUDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType xgetMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType)get_store().find_attribute_user(MODOCOLLAUDO$4);
            return target;
        }
    }
    
    /**
     * True if has "MODO_COLLAUDO" attribute
     */
    public boolean isSetMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MODOCOLLAUDO$4) != null;
        }
    }
    
    /**
     * Sets the "MODO_COLLAUDO" attribute
     */
    public void setMODOCOLLAUDO(it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType.Enum modocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODOCOLLAUDO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MODOCOLLAUDO$4);
            }
            target.setEnumValue(modocollaudo);
        }
    }
    
    /**
     * Sets (as xml) the "MODO_COLLAUDO" attribute
     */
    public void xsetMODOCOLLAUDO(it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType modocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType)get_store().find_attribute_user(MODOCOLLAUDO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType)get_store().add_attribute_user(MODOCOLLAUDO$4);
            }
            target.set(modocollaudo);
        }
    }
    
    /**
     * Unsets the "MODO_COLLAUDO" attribute
     */
    public void unsetMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MODOCOLLAUDO$4);
        }
    }
    
    /**
     * Gets the "DATA_NOMINA_COLL" attribute
     */
    public java.util.Calendar getDATANOMINACOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATANOMINACOLL$6);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_NOMINA_COLL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATANOMINACOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATANOMINACOLL$6);
            return target;
        }
    }
    
    /**
     * True if has "DATA_NOMINA_COLL" attribute
     */
    public boolean isSetDATANOMINACOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATANOMINACOLL$6) != null;
        }
    }
    
    /**
     * Sets the "DATA_NOMINA_COLL" attribute
     */
    public void setDATANOMINACOLL(java.util.Calendar datanominacoll)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATANOMINACOLL$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATANOMINACOLL$6);
            }
            target.setCalendarValue(datanominacoll);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_NOMINA_COLL" attribute
     */
    public void xsetDATANOMINACOLL(it.avlp.simog.massload.xmlbeans.DbDateType datanominacoll)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATANOMINACOLL$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATANOMINACOLL$6);
            }
            target.set(datanominacoll);
        }
    }
    
    /**
     * Unsets the "DATA_NOMINA_COLL" attribute
     */
    public void unsetDATANOMINACOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATANOMINACOLL$6);
        }
    }
    
    /**
     * Gets the "DATA_INIZIO_OPER" attribute
     */
    public java.util.Calendar getDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAINIZIOOPER$8);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INIZIO_OPER" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAINIZIOOPER$8);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INIZIO_OPER" attribute
     */
    public boolean isSetDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAINIZIOOPER$8) != null;
        }
    }
    
    /**
     * Sets the "DATA_INIZIO_OPER" attribute
     */
    public void setDATAINIZIOOPER(java.util.Calendar datainiziooper)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAINIZIOOPER$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAINIZIOOPER$8);
            }
            target.setCalendarValue(datainiziooper);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INIZIO_OPER" attribute
     */
    public void xsetDATAINIZIOOPER(it.avlp.simog.massload.xmlbeans.DbDateType datainiziooper)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAINIZIOOPER$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAINIZIOOPER$8);
            }
            target.set(datainiziooper);
        }
    }
    
    /**
     * Unsets the "DATA_INIZIO_OPER" attribute
     */
    public void unsetDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAINIZIOOPER$8);
        }
    }
    
    /**
     * Gets the "DATA_CERT_COLLAUDO" attribute
     */
    public java.util.Calendar getDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACERTCOLLAUDO$10);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CERT_COLLAUDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACERTCOLLAUDO$10);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CERT_COLLAUDO" attribute
     */
    public boolean isSetDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATACERTCOLLAUDO$10) != null;
        }
    }
    
    /**
     * Sets the "DATA_CERT_COLLAUDO" attribute
     */
    public void setDATACERTCOLLAUDO(java.util.Calendar datacertcollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACERTCOLLAUDO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATACERTCOLLAUDO$10);
            }
            target.setCalendarValue(datacertcollaudo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CERT_COLLAUDO" attribute
     */
    public void xsetDATACERTCOLLAUDO(it.avlp.simog.massload.xmlbeans.DbDateType datacertcollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACERTCOLLAUDO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATACERTCOLLAUDO$10);
            }
            target.set(datacertcollaudo);
        }
    }
    
    /**
     * Unsets the "DATA_CERT_COLLAUDO" attribute
     */
    public void unsetDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATACERTCOLLAUDO$10);
        }
    }
    
    /**
     * Gets the "DATA_DELIBERA" attribute
     */
    public java.util.Calendar getDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATADELIBERA$12);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_DELIBERA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATADELIBERA$12);
            return target;
        }
    }
    
    /**
     * True if has "DATA_DELIBERA" attribute
     */
    public boolean isSetDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATADELIBERA$12) != null;
        }
    }
    
    /**
     * Sets the "DATA_DELIBERA" attribute
     */
    public void setDATADELIBERA(java.util.Calendar datadelibera)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATADELIBERA$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATADELIBERA$12);
            }
            target.setCalendarValue(datadelibera);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_DELIBERA" attribute
     */
    public void xsetDATADELIBERA(it.avlp.simog.massload.xmlbeans.DbDateType datadelibera)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATADELIBERA$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATADELIBERA$12);
            }
            target.set(datadelibera);
        }
    }
    
    /**
     * Unsets the "DATA_DELIBERA" attribute
     */
    public void unsetDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATADELIBERA$12);
        }
    }
    
    /**
     * Gets the "ESITO_COLLAUDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType.Enum getESITOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ESITOCOLLAUDO$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ESITO_COLLAUDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType xgetESITOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType)get_store().find_attribute_user(ESITOCOLLAUDO$14);
            return target;
        }
    }
    
    /**
     * Sets the "ESITO_COLLAUDO" attribute
     */
    public void setESITOCOLLAUDO(it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType.Enum esitocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ESITOCOLLAUDO$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ESITOCOLLAUDO$14);
            }
            target.setEnumValue(esitocollaudo);
        }
    }
    
    /**
     * Sets (as xml) the "ESITO_COLLAUDO" attribute
     */
    public void xsetESITOCOLLAUDO(it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType esitocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType)get_store().find_attribute_user(ESITOCOLLAUDO$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType)get_store().add_attribute_user(ESITOCOLLAUDO$14);
            }
            target.set(esitocollaudo);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_LAVORI" attribute
     */
    public java.math.BigDecimal getIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALELAVORI$16);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_LAVORI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALELAVORI$16);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_LAVORI" attribute
     */
    public boolean isSetIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPFINALELAVORI$16) != null;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_LAVORI" attribute
     */
    public void setIMPFINALELAVORI(java.math.BigDecimal impfinalelavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALELAVORI$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPFINALELAVORI$16);
            }
            target.setBigDecimalValue(impfinalelavori);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_LAVORI" attribute
     */
    public void xsetIMPFINALELAVORI(it.avlp.simog.massload.xmlbeans.ImportoType impfinalelavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALELAVORI$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPFINALELAVORI$16);
            }
            target.set(impfinalelavori);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_LAVORI" attribute
     */
    public void unsetIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPFINALELAVORI$16);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_SERVIZI" attribute
     */
    public java.math.BigDecimal getIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALESERVIZI$18);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_SERVIZI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALESERVIZI$18);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_SERVIZI" attribute
     */
    public boolean isSetIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPFINALESERVIZI$18) != null;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_SERVIZI" attribute
     */
    public void setIMPFINALESERVIZI(java.math.BigDecimal impfinaleservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALESERVIZI$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPFINALESERVIZI$18);
            }
            target.setBigDecimalValue(impfinaleservizi);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_SERVIZI" attribute
     */
    public void xsetIMPFINALESERVIZI(it.avlp.simog.massload.xmlbeans.ImportoType impfinaleservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALESERVIZI$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPFINALESERVIZI$18);
            }
            target.set(impfinaleservizi);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_SERVIZI" attribute
     */
    public void unsetIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPFINALESERVIZI$18);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_FORNIT" attribute
     */
    public java.math.BigDecimal getIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALEFORNIT$20);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_FORNIT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALEFORNIT$20);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_FORNIT" attribute
     */
    public boolean isSetIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPFINALEFORNIT$20) != null;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_FORNIT" attribute
     */
    public void setIMPFINALEFORNIT(java.math.BigDecimal impfinalefornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALEFORNIT$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPFINALEFORNIT$20);
            }
            target.setBigDecimalValue(impfinalefornit);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_FORNIT" attribute
     */
    public void xsetIMPFINALEFORNIT(it.avlp.simog.massload.xmlbeans.ImportoType impfinalefornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALEFORNIT$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPFINALEFORNIT$20);
            }
            target.set(impfinalefornit);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_FORNIT" attribute
     */
    public void unsetIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPFINALEFORNIT$20);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_SECUR" attribute
     */
    public java.math.BigDecimal getIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALESECUR$22);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_SECUR" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALESECUR$22);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_SECUR" attribute
     */
    public boolean isSetIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IMPFINALESECUR$22) != null;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_SECUR" attribute
     */
    public void setIMPFINALESECUR(java.math.BigDecimal impfinalesecur)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPFINALESECUR$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPFINALESECUR$22);
            }
            target.setBigDecimalValue(impfinalesecur);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_SECUR" attribute
     */
    public void xsetIMPFINALESECUR(it.avlp.simog.massload.xmlbeans.ImportoType impfinalesecur)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPFINALESECUR$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPFINALESECUR$22);
            }
            target.set(impfinalesecur);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_SECUR" attribute
     */
    public void unsetIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IMPFINALESECUR$22);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPPROGETTAZIONE$24);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPPROGETTAZIONE$24);
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
            return get_store().find_attribute_user(IMPPROGETTAZIONE$24) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPPROGETTAZIONE$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPPROGETTAZIONE$24);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPPROGETTAZIONE$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPPROGETTAZIONE$24);
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
            get_store().remove_attribute(IMPPROGETTAZIONE$24);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPDISPOSIZIONE$26);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPDISPOSIZIONE$26);
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
            return get_store().find_attribute_user(IMPDISPOSIZIONE$26) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPDISPOSIZIONE$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPDISPOSIZIONE$26);
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
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPDISPOSIZIONE$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPDISPOSIZIONE$26);
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
            get_store().remove_attribute(IMPDISPOSIZIONE$26);
        }
    }
    
    /**
     * Gets the "AMM_NUM_DEFINITE" attribute
     */
    public int getAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMNUMDEFINITE$28);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_NUM_DEFINITE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(AMMNUMDEFINITE$28);
            return target;
        }
    }
    
    /**
     * True if has "AMM_NUM_DEFINITE" attribute
     */
    public boolean isSetAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(AMMNUMDEFINITE$28) != null;
        }
    }
    
    /**
     * Sets the "AMM_NUM_DEFINITE" attribute
     */
    public void setAMMNUMDEFINITE(int ammnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMNUMDEFINITE$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(AMMNUMDEFINITE$28);
            }
            target.setIntValue(ammnumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_NUM_DEFINITE" attribute
     */
    public void xsetAMMNUMDEFINITE(it.avlp.simog.massload.xmlbeans.InteroType ammnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(AMMNUMDEFINITE$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(AMMNUMDEFINITE$28);
            }
            target.set(ammnumdefinite);
        }
    }
    
    /**
     * Unsets the "AMM_NUM_DEFINITE" attribute
     */
    public void unsetAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(AMMNUMDEFINITE$28);
        }
    }
    
    /**
     * Gets the "AMM_NUM_DADEF" attribute
     */
    public int getAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMNUMDADEF$30);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_NUM_DADEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(AMMNUMDADEF$30);
            return target;
        }
    }
    
    /**
     * True if has "AMM_NUM_DADEF" attribute
     */
    public boolean isSetAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(AMMNUMDADEF$30) != null;
        }
    }
    
    /**
     * Sets the "AMM_NUM_DADEF" attribute
     */
    public void setAMMNUMDADEF(int ammnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMNUMDADEF$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(AMMNUMDADEF$30);
            }
            target.setIntValue(ammnumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_NUM_DADEF" attribute
     */
    public void xsetAMMNUMDADEF(it.avlp.simog.massload.xmlbeans.InteroType ammnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(AMMNUMDADEF$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(AMMNUMDADEF$30);
            }
            target.set(ammnumdadef);
        }
    }
    
    /**
     * Unsets the "AMM_NUM_DADEF" attribute
     */
    public void unsetAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(AMMNUMDADEF$30);
        }
    }
    
    /**
     * Gets the "AMM_IMPORTO_RICH" attribute
     */
    public java.math.BigDecimal getAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMIMPORTORICH$32);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_IMPORTO_RICH" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(AMMIMPORTORICH$32);
            return target;
        }
    }
    
    /**
     * True if has "AMM_IMPORTO_RICH" attribute
     */
    public boolean isSetAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(AMMIMPORTORICH$32) != null;
        }
    }
    
    /**
     * Sets the "AMM_IMPORTO_RICH" attribute
     */
    public void setAMMIMPORTORICH(java.math.BigDecimal ammimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMIMPORTORICH$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(AMMIMPORTORICH$32);
            }
            target.setBigDecimalValue(ammimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_IMPORTO_RICH" attribute
     */
    public void xsetAMMIMPORTORICH(it.avlp.simog.massload.xmlbeans.ImportoType ammimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(AMMIMPORTORICH$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(AMMIMPORTORICH$32);
            }
            target.set(ammimportorich);
        }
    }
    
    /**
     * Unsets the "AMM_IMPORTO_RICH" attribute
     */
    public void unsetAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(AMMIMPORTORICH$32);
        }
    }
    
    /**
     * Gets the "AMM_IMPORTO_DEF" attribute
     */
    public java.math.BigDecimal getAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMIMPORTODEF$34);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_IMPORTO_DEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(AMMIMPORTODEF$34);
            return target;
        }
    }
    
    /**
     * True if has "AMM_IMPORTO_DEF" attribute
     */
    public boolean isSetAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(AMMIMPORTODEF$34) != null;
        }
    }
    
    /**
     * Sets the "AMM_IMPORTO_DEF" attribute
     */
    public void setAMMIMPORTODEF(java.math.BigDecimal ammimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AMMIMPORTODEF$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(AMMIMPORTODEF$34);
            }
            target.setBigDecimalValue(ammimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_IMPORTO_DEF" attribute
     */
    public void xsetAMMIMPORTODEF(it.avlp.simog.massload.xmlbeans.ImportoType ammimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(AMMIMPORTODEF$34);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(AMMIMPORTODEF$34);
            }
            target.set(ammimportodef);
        }
    }
    
    /**
     * Unsets the "AMM_IMPORTO_DEF" attribute
     */
    public void unsetAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(AMMIMPORTODEF$34);
        }
    }
    
    /**
     * Gets the "ARB_NUM_DEFINITE" attribute
     */
    public int getARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBNUMDEFINITE$36);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_NUM_DEFINITE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(ARBNUMDEFINITE$36);
            return target;
        }
    }
    
    /**
     * True if has "ARB_NUM_DEFINITE" attribute
     */
    public boolean isSetARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ARBNUMDEFINITE$36) != null;
        }
    }
    
    /**
     * Sets the "ARB_NUM_DEFINITE" attribute
     */
    public void setARBNUMDEFINITE(int arbnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBNUMDEFINITE$36);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ARBNUMDEFINITE$36);
            }
            target.setIntValue(arbnumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_NUM_DEFINITE" attribute
     */
    public void xsetARBNUMDEFINITE(it.avlp.simog.massload.xmlbeans.InteroType arbnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(ARBNUMDEFINITE$36);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(ARBNUMDEFINITE$36);
            }
            target.set(arbnumdefinite);
        }
    }
    
    /**
     * Unsets the "ARB_NUM_DEFINITE" attribute
     */
    public void unsetARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ARBNUMDEFINITE$36);
        }
    }
    
    /**
     * Gets the "ARB_NUM_DADEF" attribute
     */
    public int getARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBNUMDADEF$38);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_NUM_DADEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(ARBNUMDADEF$38);
            return target;
        }
    }
    
    /**
     * True if has "ARB_NUM_DADEF" attribute
     */
    public boolean isSetARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ARBNUMDADEF$38) != null;
        }
    }
    
    /**
     * Sets the "ARB_NUM_DADEF" attribute
     */
    public void setARBNUMDADEF(int arbnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBNUMDADEF$38);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ARBNUMDADEF$38);
            }
            target.setIntValue(arbnumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_NUM_DADEF" attribute
     */
    public void xsetARBNUMDADEF(it.avlp.simog.massload.xmlbeans.InteroType arbnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(ARBNUMDADEF$38);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(ARBNUMDADEF$38);
            }
            target.set(arbnumdadef);
        }
    }
    
    /**
     * Unsets the "ARB_NUM_DADEF" attribute
     */
    public void unsetARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ARBNUMDADEF$38);
        }
    }
    
    /**
     * Gets the "ARB_IMPORTO_RICH" attribute
     */
    public java.math.BigDecimal getARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBIMPORTORICH$40);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_IMPORTO_RICH" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ARBIMPORTORICH$40);
            return target;
        }
    }
    
    /**
     * True if has "ARB_IMPORTO_RICH" attribute
     */
    public boolean isSetARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ARBIMPORTORICH$40) != null;
        }
    }
    
    /**
     * Sets the "ARB_IMPORTO_RICH" attribute
     */
    public void setARBIMPORTORICH(java.math.BigDecimal arbimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBIMPORTORICH$40);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ARBIMPORTORICH$40);
            }
            target.setBigDecimalValue(arbimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_IMPORTO_RICH" attribute
     */
    public void xsetARBIMPORTORICH(it.avlp.simog.massload.xmlbeans.ImportoType arbimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ARBIMPORTORICH$40);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(ARBIMPORTORICH$40);
            }
            target.set(arbimportorich);
        }
    }
    
    /**
     * Unsets the "ARB_IMPORTO_RICH" attribute
     */
    public void unsetARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ARBIMPORTORICH$40);
        }
    }
    
    /**
     * Gets the "ARB_IMPORTO_DEF" attribute
     */
    public java.math.BigDecimal getARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBIMPORTODEF$42);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_IMPORTO_DEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ARBIMPORTODEF$42);
            return target;
        }
    }
    
    /**
     * True if has "ARB_IMPORTO_DEF" attribute
     */
    public boolean isSetARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ARBIMPORTODEF$42) != null;
        }
    }
    
    /**
     * Sets the "ARB_IMPORTO_DEF" attribute
     */
    public void setARBIMPORTODEF(java.math.BigDecimal arbimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARBIMPORTODEF$42);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ARBIMPORTODEF$42);
            }
            target.setBigDecimalValue(arbimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_IMPORTO_DEF" attribute
     */
    public void xsetARBIMPORTODEF(it.avlp.simog.massload.xmlbeans.ImportoType arbimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(ARBIMPORTODEF$42);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(ARBIMPORTODEF$42);
            }
            target.set(arbimportodef);
        }
    }
    
    /**
     * Unsets the "ARB_IMPORTO_DEF" attribute
     */
    public void unsetARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ARBIMPORTODEF$42);
        }
    }
    
    /**
     * Gets the "GIU_NUM_DEFINITE" attribute
     */
    public int getGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUNUMDEFINITE$44);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_NUM_DEFINITE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(GIUNUMDEFINITE$44);
            return target;
        }
    }
    
    /**
     * True if has "GIU_NUM_DEFINITE" attribute
     */
    public boolean isSetGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(GIUNUMDEFINITE$44) != null;
        }
    }
    
    /**
     * Sets the "GIU_NUM_DEFINITE" attribute
     */
    public void setGIUNUMDEFINITE(int giunumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUNUMDEFINITE$44);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GIUNUMDEFINITE$44);
            }
            target.setIntValue(giunumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_NUM_DEFINITE" attribute
     */
    public void xsetGIUNUMDEFINITE(it.avlp.simog.massload.xmlbeans.InteroType giunumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(GIUNUMDEFINITE$44);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(GIUNUMDEFINITE$44);
            }
            target.set(giunumdefinite);
        }
    }
    
    /**
     * Unsets the "GIU_NUM_DEFINITE" attribute
     */
    public void unsetGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(GIUNUMDEFINITE$44);
        }
    }
    
    /**
     * Gets the "GIU_NUM_DADEF" attribute
     */
    public int getGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUNUMDADEF$46);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_NUM_DADEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(GIUNUMDADEF$46);
            return target;
        }
    }
    
    /**
     * True if has "GIU_NUM_DADEF" attribute
     */
    public boolean isSetGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(GIUNUMDADEF$46) != null;
        }
    }
    
    /**
     * Sets the "GIU_NUM_DADEF" attribute
     */
    public void setGIUNUMDADEF(int giunumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUNUMDADEF$46);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GIUNUMDADEF$46);
            }
            target.setIntValue(giunumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_NUM_DADEF" attribute
     */
    public void xsetGIUNUMDADEF(it.avlp.simog.massload.xmlbeans.InteroType giunumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(GIUNUMDADEF$46);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(GIUNUMDADEF$46);
            }
            target.set(giunumdadef);
        }
    }
    
    /**
     * Unsets the "GIU_NUM_DADEF" attribute
     */
    public void unsetGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(GIUNUMDADEF$46);
        }
    }
    
    /**
     * Gets the "GIU_IMPORTO_RICH" attribute
     */
    public java.math.BigDecimal getGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUIMPORTORICH$48);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_IMPORTO_RICH" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(GIUIMPORTORICH$48);
            return target;
        }
    }
    
    /**
     * True if has "GIU_IMPORTO_RICH" attribute
     */
    public boolean isSetGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(GIUIMPORTORICH$48) != null;
        }
    }
    
    /**
     * Sets the "GIU_IMPORTO_RICH" attribute
     */
    public void setGIUIMPORTORICH(java.math.BigDecimal giuimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUIMPORTORICH$48);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GIUIMPORTORICH$48);
            }
            target.setBigDecimalValue(giuimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_IMPORTO_RICH" attribute
     */
    public void xsetGIUIMPORTORICH(it.avlp.simog.massload.xmlbeans.ImportoType giuimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(GIUIMPORTORICH$48);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(GIUIMPORTORICH$48);
            }
            target.set(giuimportorich);
        }
    }
    
    /**
     * Unsets the "GIU_IMPORTO_RICH" attribute
     */
    public void unsetGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(GIUIMPORTORICH$48);
        }
    }
    
    /**
     * Gets the "GIU_IMPORTO_DEF" attribute
     */
    public java.math.BigDecimal getGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUIMPORTODEF$50);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_IMPORTO_DEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(GIUIMPORTODEF$50);
            return target;
        }
    }
    
    /**
     * True if has "GIU_IMPORTO_DEF" attribute
     */
    public boolean isSetGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(GIUIMPORTODEF$50) != null;
        }
    }
    
    /**
     * Sets the "GIU_IMPORTO_DEF" attribute
     */
    public void setGIUIMPORTODEF(java.math.BigDecimal giuimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GIUIMPORTODEF$50);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GIUIMPORTODEF$50);
            }
            target.setBigDecimalValue(giuimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_IMPORTO_DEF" attribute
     */
    public void xsetGIUIMPORTODEF(it.avlp.simog.massload.xmlbeans.ImportoType giuimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(GIUIMPORTODEF$50);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(GIUIMPORTODEF$50);
            }
            target.set(giuimportodef);
        }
    }
    
    /**
     * Unsets the "GIU_IMPORTO_DEF" attribute
     */
    public void unsetGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(GIUIMPORTODEF$50);
        }
    }
    
    /**
     * Gets the "TRA_NUM_DEFINITE" attribute
     */
    public int getTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANUMDEFINITE$52);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_NUM_DEFINITE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(TRANUMDEFINITE$52);
            return target;
        }
    }
    
    /**
     * True if has "TRA_NUM_DEFINITE" attribute
     */
    public boolean isSetTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TRANUMDEFINITE$52) != null;
        }
    }
    
    /**
     * Sets the "TRA_NUM_DEFINITE" attribute
     */
    public void setTRANUMDEFINITE(int tranumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANUMDEFINITE$52);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TRANUMDEFINITE$52);
            }
            target.setIntValue(tranumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_NUM_DEFINITE" attribute
     */
    public void xsetTRANUMDEFINITE(it.avlp.simog.massload.xmlbeans.InteroType tranumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(TRANUMDEFINITE$52);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(TRANUMDEFINITE$52);
            }
            target.set(tranumdefinite);
        }
    }
    
    /**
     * Unsets the "TRA_NUM_DEFINITE" attribute
     */
    public void unsetTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TRANUMDEFINITE$52);
        }
    }
    
    /**
     * Gets the "TRA_NUM_DADEF" attribute
     */
    public int getTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANUMDADEF$54);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_NUM_DADEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(TRANUMDADEF$54);
            return target;
        }
    }
    
    /**
     * True if has "TRA_NUM_DADEF" attribute
     */
    public boolean isSetTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TRANUMDADEF$54) != null;
        }
    }
    
    /**
     * Sets the "TRA_NUM_DADEF" attribute
     */
    public void setTRANUMDADEF(int tranumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANUMDADEF$54);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TRANUMDADEF$54);
            }
            target.setIntValue(tranumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_NUM_DADEF" attribute
     */
    public void xsetTRANUMDADEF(it.avlp.simog.massload.xmlbeans.InteroType tranumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(TRANUMDADEF$54);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(TRANUMDADEF$54);
            }
            target.set(tranumdadef);
        }
    }
    
    /**
     * Unsets the "TRA_NUM_DADEF" attribute
     */
    public void unsetTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TRANUMDADEF$54);
        }
    }
    
    /**
     * Gets the "TRA_IMPORTO_RICH" attribute
     */
    public java.math.BigDecimal getTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRAIMPORTORICH$56);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_IMPORTO_RICH" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(TRAIMPORTORICH$56);
            return target;
        }
    }
    
    /**
     * True if has "TRA_IMPORTO_RICH" attribute
     */
    public boolean isSetTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TRAIMPORTORICH$56) != null;
        }
    }
    
    /**
     * Sets the "TRA_IMPORTO_RICH" attribute
     */
    public void setTRAIMPORTORICH(java.math.BigDecimal traimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRAIMPORTORICH$56);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TRAIMPORTORICH$56);
            }
            target.setBigDecimalValue(traimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_IMPORTO_RICH" attribute
     */
    public void xsetTRAIMPORTORICH(it.avlp.simog.massload.xmlbeans.ImportoType traimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(TRAIMPORTORICH$56);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(TRAIMPORTORICH$56);
            }
            target.set(traimportorich);
        }
    }
    
    /**
     * Unsets the "TRA_IMPORTO_RICH" attribute
     */
    public void unsetTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TRAIMPORTORICH$56);
        }
    }
    
    /**
     * Gets the "TRA_IMPORTO_DEF" attribute
     */
    public java.math.BigDecimal getTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRAIMPORTODEF$58);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_IMPORTO_DEF" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(TRAIMPORTODEF$58);
            return target;
        }
    }
    
    /**
     * True if has "TRA_IMPORTO_DEF" attribute
     */
    public boolean isSetTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TRAIMPORTODEF$58) != null;
        }
    }
    
    /**
     * Sets the "TRA_IMPORTO_DEF" attribute
     */
    public void setTRAIMPORTODEF(java.math.BigDecimal traimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRAIMPORTODEF$58);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TRAIMPORTODEF$58);
            }
            target.setBigDecimalValue(traimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_IMPORTO_DEF" attribute
     */
    public void xsetTRAIMPORTODEF(it.avlp.simog.massload.xmlbeans.ImportoType traimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(TRAIMPORTODEF$58);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(TRAIMPORTODEF$58);
            }
            target.set(traimportodef);
        }
    }
    
    /**
     * Unsets the "TRA_IMPORTO_DEF" attribute
     */
    public void unsetTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TRAIMPORTODEF$58);
        }
    }
    
    /**
     * Gets the "LAVORI_ESTESI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LAVORIESTESI$60);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "LAVORI_ESTESI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(LAVORIESTESI$60);
            return target;
        }
    }
    
    /**
     * True if has "LAVORI_ESTESI" attribute
     */
    public boolean isSetLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LAVORIESTESI$60) != null;
        }
    }
    
    /**
     * Sets the "LAVORI_ESTESI" attribute
     */
    public void setLAVORIESTESI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum lavoriestesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LAVORIESTESI$60);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LAVORIESTESI$60);
            }
            target.setEnumValue(lavoriestesi);
        }
    }
    
    /**
     * Sets (as xml) the "LAVORI_ESTESI" attribute
     */
    public void xsetLAVORIESTESI(it.avlp.simog.massload.xmlbeans.FlagSNType lavoriestesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(LAVORIESTESI$60);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(LAVORIESTESI$60);
            }
            target.set(lavoriestesi);
        }
    }
    
    /**
     * Unsets the "LAVORI_ESTESI" attribute
     */
    public void unsetLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LAVORIESTESI$60);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$62);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$62);
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
            return get_store().find_attribute_user(IDSCHEDALOCALE$62) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDALOCALE$62);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDALOCALE$62);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDALOCALE$62);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDALOCALE$62);
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
            get_store().remove_attribute(IDSCHEDALOCALE$62);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$64);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$64);
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
            return get_store().find_attribute_user(IDSCHEDASIMOG$64) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSCHEDASIMOG$64);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSCHEDASIMOG$64);
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
            target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().find_attribute_user(IDSCHEDASIMOG$64);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IdSchedaType)get_store().add_attribute_user(IDSCHEDASIMOG$64);
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
            get_store().remove_attribute(IDSCHEDASIMOG$64);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$66);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$66);
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
            return get_store().find_attribute_user(IDSTATOSCHEDA$66) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDSTATOSCHEDA$66);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDSTATOSCHEDA$66);
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
            target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().find_attribute_user(IDSTATOSCHEDA$66);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoSchedaType)get_store().add_attribute_user(IDSTATOSCHEDA$66);
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
            get_store().remove_attribute(IDSTATOSCHEDA$66);
        }
    }
}
