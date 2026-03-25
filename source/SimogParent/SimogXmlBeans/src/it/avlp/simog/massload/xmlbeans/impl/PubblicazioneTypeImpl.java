/*
 * XML Type:  PubblicazioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.PubblicazioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML PubblicazioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class PubblicazioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.PubblicazioneType
{
    
    public PubblicazioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAGUCE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_GUCE");
    private static final javax.xml.namespace.QName DATAGURI$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_GURI");
    private static final javax.xml.namespace.QName DATAALBO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_ALBO");
    private static final javax.xml.namespace.QName DATABORE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_BORE");
    private static final javax.xml.namespace.QName QUOTIDIANINAZ$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "QUOTIDIANI_NAZ");
    private static final javax.xml.namespace.QName QUOTIDIANIREG$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "QUOTIDIANI_REG");
    private static final javax.xml.namespace.QName PERIODICI$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERIODICI");
    private static final javax.xml.namespace.QName PROFILOCOMMITTENTE$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PROFILO_COMMITTENTE");
    private static final javax.xml.namespace.QName SITOMINISTEROINFTRASP$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SITO_MINISTERO_INF_TRASP");
    private static final javax.xml.namespace.QName SITOOSSERVATORIOCP$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SITO_OSSERVATORIO_CP");
    private static final javax.xml.namespace.QName IDSCHEDALOCALE$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_LOCALE");
    private static final javax.xml.namespace.QName IDSCHEDASIMOG$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_SCHEDA_SIMOG");
    private static final javax.xml.namespace.QName NUMEROGUCE$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUMERO_GUCE");
    private static final javax.xml.namespace.QName NUMEROGURI$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUMERO_GURI");
    private static final javax.xml.namespace.QName NUMEROBORE$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUMERO_BORE");
    private static final javax.xml.namespace.QName LINKSITO$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LINK_SITO");
    private static final javax.xml.namespace.QName LINKAFFIDAMENTODIRETTO$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LINK_AFFIDAMENTO_DIRETTO");
    private static final javax.xml.namespace.QName FLAGBENICULT$34 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_BENICULT");
    private static final javax.xml.namespace.QName FLAGSOSPESO$36 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_SOSPESO");
    
    
    /**
     * Gets the "DATA_GUCE" attribute
     */
    public java.util.Calendar getDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAGUCE$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_GUCE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAGUCE$0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_GUCE" attribute
     */
    public boolean isSetDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAGUCE$0) != null;
        }
    }
    
    /**
     * Sets the "DATA_GUCE" attribute
     */
    public void setDATAGUCE(java.util.Calendar dataguce)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAGUCE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAGUCE$0);
            }
            target.setCalendarValue(dataguce);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_GUCE" attribute
     */
    public void xsetDATAGUCE(it.avlp.simog.massload.xmlbeans.DbDateType dataguce)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAGUCE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAGUCE$0);
            }
            target.set(dataguce);
        }
    }
    
    /**
     * Unsets the "DATA_GUCE" attribute
     */
    public void unsetDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAGUCE$0);
        }
    }
    
    /**
     * Gets the "DATA_GURI" attribute
     */
    public java.util.Calendar getDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAGURI$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_GURI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAGURI$2);
            return target;
        }
    }
    
    /**
     * True if has "DATA_GURI" attribute
     */
    public boolean isSetDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAGURI$2) != null;
        }
    }
    
    /**
     * Sets the "DATA_GURI" attribute
     */
    public void setDATAGURI(java.util.Calendar dataguri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAGURI$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAGURI$2);
            }
            target.setCalendarValue(dataguri);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_GURI" attribute
     */
    public void xsetDATAGURI(it.avlp.simog.massload.xmlbeans.DbDateType dataguri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAGURI$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAGURI$2);
            }
            target.set(dataguri);
        }
    }
    
    /**
     * Unsets the "DATA_GURI" attribute
     */
    public void unsetDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAGURI$2);
        }
    }
    
    /**
     * Gets the "DATA_ALBO" attribute
     */
    public java.util.Calendar getDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAALBO$4);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ALBO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAALBO$4);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ALBO" attribute
     */
    public boolean isSetDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAALBO$4) != null;
        }
    }
    
    /**
     * Sets the "DATA_ALBO" attribute
     */
    public void setDATAALBO(java.util.Calendar dataalbo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAALBO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAALBO$4);
            }
            target.setCalendarValue(dataalbo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ALBO" attribute
     */
    public void xsetDATAALBO(it.avlp.simog.massload.xmlbeans.DbDateType dataalbo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAALBO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAALBO$4);
            }
            target.set(dataalbo);
        }
    }
    
    /**
     * Unsets the "DATA_ALBO" attribute
     */
    public void unsetDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAALBO$4);
        }
    }
    
    /**
     * Gets the "DATA_BORE" attribute
     */
    public java.util.Calendar getDATABORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATABORE$6);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_BORE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATABORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATABORE$6);
            return target;
        }
    }
    
    /**
     * True if has "DATA_BORE" attribute
     */
    public boolean isSetDATABORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATABORE$6) != null;
        }
    }
    
    /**
     * Sets the "DATA_BORE" attribute
     */
    public void setDATABORE(java.util.Calendar databore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATABORE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATABORE$6);
            }
            target.setCalendarValue(databore);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_BORE" attribute
     */
    public void xsetDATABORE(it.avlp.simog.massload.xmlbeans.DbDateType databore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATABORE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATABORE$6);
            }
            target.set(databore);
        }
    }
    
    /**
     * Unsets the "DATA_BORE" attribute
     */
    public void unsetDATABORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATABORE$6);
        }
    }
    
    /**
     * Gets the "QUOTIDIANI_NAZ" attribute
     */
    public int getQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QUOTIDIANINAZ$8);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "QUOTIDIANI_NAZ" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(QUOTIDIANINAZ$8);
            return target;
        }
    }
    
    /**
     * True if has "QUOTIDIANI_NAZ" attribute
     */
    public boolean isSetQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(QUOTIDIANINAZ$8) != null;
        }
    }
    
    /**
     * Sets the "QUOTIDIANI_NAZ" attribute
     */
    public void setQUOTIDIANINAZ(int quotidianinaz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QUOTIDIANINAZ$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(QUOTIDIANINAZ$8);
            }
            target.setIntValue(quotidianinaz);
        }
    }
    
    /**
     * Sets (as xml) the "QUOTIDIANI_NAZ" attribute
     */
    public void xsetQUOTIDIANINAZ(it.avlp.simog.massload.xmlbeans.InteroType quotidianinaz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(QUOTIDIANINAZ$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(QUOTIDIANINAZ$8);
            }
            target.set(quotidianinaz);
        }
    }
    
    /**
     * Unsets the "QUOTIDIANI_NAZ" attribute
     */
    public void unsetQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(QUOTIDIANINAZ$8);
        }
    }
    
    /**
     * Gets the "QUOTIDIANI_REG" attribute
     */
    public int getQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QUOTIDIANIREG$10);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "QUOTIDIANI_REG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(QUOTIDIANIREG$10);
            return target;
        }
    }
    
    /**
     * True if has "QUOTIDIANI_REG" attribute
     */
    public boolean isSetQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(QUOTIDIANIREG$10) != null;
        }
    }
    
    /**
     * Sets the "QUOTIDIANI_REG" attribute
     */
    public void setQUOTIDIANIREG(int quotidianireg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QUOTIDIANIREG$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(QUOTIDIANIREG$10);
            }
            target.setIntValue(quotidianireg);
        }
    }
    
    /**
     * Sets (as xml) the "QUOTIDIANI_REG" attribute
     */
    public void xsetQUOTIDIANIREG(it.avlp.simog.massload.xmlbeans.InteroType quotidianireg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(QUOTIDIANIREG$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(QUOTIDIANIREG$10);
            }
            target.set(quotidianireg);
        }
    }
    
    /**
     * Unsets the "QUOTIDIANI_REG" attribute
     */
    public void unsetQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(QUOTIDIANIREG$10);
        }
    }
    
    /**
     * Gets the "PERIODICI" attribute
     */
    public int getPERIODICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERIODICI$12);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERIODICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetPERIODICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(PERIODICI$12);
            return target;
        }
    }
    
    /**
     * True if has "PERIODICI" attribute
     */
    public boolean isSetPERIODICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PERIODICI$12) != null;
        }
    }
    
    /**
     * Sets the "PERIODICI" attribute
     */
    public void setPERIODICI(int periodici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERIODICI$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERIODICI$12);
            }
            target.setIntValue(periodici);
        }
    }
    
    /**
     * Sets (as xml) the "PERIODICI" attribute
     */
    public void xsetPERIODICI(it.avlp.simog.massload.xmlbeans.InteroType periodici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(PERIODICI$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(PERIODICI$12);
            }
            target.set(periodici);
        }
    }
    
    /**
     * Unsets the "PERIODICI" attribute
     */
    public void unsetPERIODICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PERIODICI$12);
        }
    }
    
    /**
     * Gets the "PROFILO_COMMITTENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getPROFILOCOMMITTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROFILOCOMMITTENTE$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROFILO_COMMITTENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetPROFILOCOMMITTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PROFILOCOMMITTENTE$14);
            return target;
        }
    }
    
    /**
     * True if has "PROFILO_COMMITTENTE" attribute
     */
    public boolean isSetPROFILOCOMMITTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROFILOCOMMITTENTE$14) != null;
        }
    }
    
    /**
     * Sets the "PROFILO_COMMITTENTE" attribute
     */
    public void setPROFILOCOMMITTENTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum profilocommittente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROFILOCOMMITTENTE$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROFILOCOMMITTENTE$14);
            }
            target.setEnumValue(profilocommittente);
        }
    }
    
    /**
     * Sets (as xml) the "PROFILO_COMMITTENTE" attribute
     */
    public void xsetPROFILOCOMMITTENTE(it.avlp.simog.massload.xmlbeans.FlagSNType profilocommittente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PROFILOCOMMITTENTE$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(PROFILOCOMMITTENTE$14);
            }
            target.set(profilocommittente);
        }
    }
    
    /**
     * Unsets the "PROFILO_COMMITTENTE" attribute
     */
    public void unsetPROFILOCOMMITTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROFILOCOMMITTENTE$14);
        }
    }
    
    /**
     * Gets the "SITO_MINISTERO_INF_TRASP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getSITOMINISTEROINFTRASP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SITOMINISTEROINFTRASP$16);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SITO_MINISTERO_INF_TRASP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetSITOMINISTEROINFTRASP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SITOMINISTEROINFTRASP$16);
            return target;
        }
    }
    
    /**
     * True if has "SITO_MINISTERO_INF_TRASP" attribute
     */
    public boolean isSetSITOMINISTEROINFTRASP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SITOMINISTEROINFTRASP$16) != null;
        }
    }
    
    /**
     * Sets the "SITO_MINISTERO_INF_TRASP" attribute
     */
    public void setSITOMINISTEROINFTRASP(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum sitoministeroinftrasp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SITOMINISTEROINFTRASP$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SITOMINISTEROINFTRASP$16);
            }
            target.setEnumValue(sitoministeroinftrasp);
        }
    }
    
    /**
     * Sets (as xml) the "SITO_MINISTERO_INF_TRASP" attribute
     */
    public void xsetSITOMINISTEROINFTRASP(it.avlp.simog.massload.xmlbeans.FlagSNType sitoministeroinftrasp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SITOMINISTEROINFTRASP$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(SITOMINISTEROINFTRASP$16);
            }
            target.set(sitoministeroinftrasp);
        }
    }
    
    /**
     * Unsets the "SITO_MINISTERO_INF_TRASP" attribute
     */
    public void unsetSITOMINISTEROINFTRASP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SITOMINISTEROINFTRASP$16);
        }
    }
    
    /**
     * Gets the "SITO_OSSERVATORIO_CP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getSITOOSSERVATORIOCP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SITOOSSERVATORIOCP$18);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SITO_OSSERVATORIO_CP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetSITOOSSERVATORIOCP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SITOOSSERVATORIOCP$18);
            return target;
        }
    }
    
    /**
     * True if has "SITO_OSSERVATORIO_CP" attribute
     */
    public boolean isSetSITOOSSERVATORIOCP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SITOOSSERVATORIOCP$18) != null;
        }
    }
    
    /**
     * Sets the "SITO_OSSERVATORIO_CP" attribute
     */
    public void setSITOOSSERVATORIOCP(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum sitoosservatoriocp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SITOOSSERVATORIOCP$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SITOOSSERVATORIOCP$18);
            }
            target.setEnumValue(sitoosservatoriocp);
        }
    }
    
    /**
     * Sets (as xml) the "SITO_OSSERVATORIO_CP" attribute
     */
    public void xsetSITOOSSERVATORIOCP(it.avlp.simog.massload.xmlbeans.FlagSNType sitoosservatoriocp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SITOOSSERVATORIOCP$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(SITOOSSERVATORIOCP$18);
            }
            target.set(sitoosservatoriocp);
        }
    }
    
    /**
     * Unsets the "SITO_OSSERVATORIO_CP" attribute
     */
    public void unsetSITOOSSERVATORIOCP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SITOOSSERVATORIOCP$18);
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
     * Gets the "NUMERO_GUCE" attribute
     */
    public java.lang.String getNUMEROGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMEROGUCE$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUMERO_GUCE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CuiType xgetNUMEROGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(NUMEROGUCE$24);
            return target;
        }
    }
    
    /**
     * True if has "NUMERO_GUCE" attribute
     */
    public boolean isSetNUMEROGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMEROGUCE$24) != null;
        }
    }
    
    /**
     * Sets the "NUMERO_GUCE" attribute
     */
    public void setNUMEROGUCE(java.lang.String numeroguce)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMEROGUCE$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMEROGUCE$24);
            }
            target.setStringValue(numeroguce);
        }
    }
    
    /**
     * Sets (as xml) the "NUMERO_GUCE" attribute
     */
    public void xsetNUMEROGUCE(it.avlp.simog.massload.xmlbeans.CuiType numeroguce)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(NUMEROGUCE$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().add_attribute_user(NUMEROGUCE$24);
            }
            target.set(numeroguce);
        }
    }
    
    /**
     * Unsets the "NUMERO_GUCE" attribute
     */
    public void unsetNUMEROGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMEROGUCE$24);
        }
    }
    
    /**
     * Gets the "NUMERO_GURI" attribute
     */
    public java.lang.String getNUMEROGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMEROGURI$26);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUMERO_GURI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CuiType xgetNUMEROGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(NUMEROGURI$26);
            return target;
        }
    }
    
    /**
     * True if has "NUMERO_GURI" attribute
     */
    public boolean isSetNUMEROGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMEROGURI$26) != null;
        }
    }
    
    /**
     * Sets the "NUMERO_GURI" attribute
     */
    public void setNUMEROGURI(java.lang.String numeroguri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMEROGURI$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMEROGURI$26);
            }
            target.setStringValue(numeroguri);
        }
    }
    
    /**
     * Sets (as xml) the "NUMERO_GURI" attribute
     */
    public void xsetNUMEROGURI(it.avlp.simog.massload.xmlbeans.CuiType numeroguri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(NUMEROGURI$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().add_attribute_user(NUMEROGURI$26);
            }
            target.set(numeroguri);
        }
    }
    
    /**
     * Unsets the "NUMERO_GURI" attribute
     */
    public void unsetNUMEROGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMEROGURI$26);
        }
    }
    
    /**
     * Gets the "NUMERO_BORE" attribute
     */
    public java.lang.String getNUMEROBORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMEROBORE$28);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUMERO_BORE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CuiType xgetNUMEROBORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(NUMEROBORE$28);
            return target;
        }
    }
    
    /**
     * True if has "NUMERO_BORE" attribute
     */
    public boolean isSetNUMEROBORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NUMEROBORE$28) != null;
        }
    }
    
    /**
     * Sets the "NUMERO_BORE" attribute
     */
    public void setNUMEROBORE(java.lang.String numerobore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMEROBORE$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMEROBORE$28);
            }
            target.setStringValue(numerobore);
        }
    }
    
    /**
     * Sets (as xml) the "NUMERO_BORE" attribute
     */
    public void xsetNUMEROBORE(it.avlp.simog.massload.xmlbeans.CuiType numerobore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_attribute_user(NUMEROBORE$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().add_attribute_user(NUMEROBORE$28);
            }
            target.set(numerobore);
        }
    }
    
    /**
     * Unsets the "NUMERO_BORE" attribute
     */
    public void unsetNUMEROBORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NUMEROBORE$28);
        }
    }
    
    /**
     * Gets the "LINK_SITO" attribute
     */
    public java.lang.String getLINKSITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LINKSITO$30);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LINK_SITO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO xgetLINKSITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO)get_store().find_attribute_user(LINKSITO$30);
            return target;
        }
    }
    
    /**
     * True if has "LINK_SITO" attribute
     */
    public boolean isSetLINKSITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LINKSITO$30) != null;
        }
    }
    
    /**
     * Sets the "LINK_SITO" attribute
     */
    public void setLINKSITO(java.lang.String linksito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LINKSITO$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LINKSITO$30);
            }
            target.setStringValue(linksito);
        }
    }
    
    /**
     * Sets (as xml) the "LINK_SITO" attribute
     */
    public void xsetLINKSITO(it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO linksito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO)get_store().find_attribute_user(LINKSITO$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO)get_store().add_attribute_user(LINKSITO$30);
            }
            target.set(linksito);
        }
    }
    
    /**
     * Unsets the "LINK_SITO" attribute
     */
    public void unsetLINKSITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LINKSITO$30);
        }
    }
    
    /**
     * Gets the "LINK_AFFIDAMENTO_DIRETTO" attribute
     */
    public java.lang.String getLINKAFFIDAMENTODIRETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LINKAFFIDAMENTODIRETTO$32);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LINK_AFFIDAMENTO_DIRETTO" attribute
     */
    public org.apache.xmlbeans.XmlString xgetLINKAFFIDAMENTODIRETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LINKAFFIDAMENTODIRETTO$32);
            return target;
        }
    }
    
    /**
     * True if has "LINK_AFFIDAMENTO_DIRETTO" attribute
     */
    public boolean isSetLINKAFFIDAMENTODIRETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LINKAFFIDAMENTODIRETTO$32) != null;
        }
    }
    
    /**
     * Sets the "LINK_AFFIDAMENTO_DIRETTO" attribute
     */
    public void setLINKAFFIDAMENTODIRETTO(java.lang.String linkaffidamentodiretto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LINKAFFIDAMENTODIRETTO$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LINKAFFIDAMENTODIRETTO$32);
            }
            target.setStringValue(linkaffidamentodiretto);
        }
    }
    
    /**
     * Sets (as xml) the "LINK_AFFIDAMENTO_DIRETTO" attribute
     */
    public void xsetLINKAFFIDAMENTODIRETTO(org.apache.xmlbeans.XmlString linkaffidamentodiretto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LINKAFFIDAMENTODIRETTO$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(LINKAFFIDAMENTODIRETTO$32);
            }
            target.set(linkaffidamentodiretto);
        }
    }
    
    /**
     * Unsets the "LINK_AFFIDAMENTO_DIRETTO" attribute
     */
    public void unsetLINKAFFIDAMENTODIRETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LINKAFFIDAMENTODIRETTO$32);
        }
    }
    
    /**
     * Gets the "FLAG_BENICULT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGBENICULT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGBENICULT$34);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_BENICULT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGBENICULT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGBENICULT$34);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_BENICULT" attribute
     */
    public boolean isSetFLAGBENICULT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGBENICULT$34) != null;
        }
    }
    
    /**
     * Sets the "FLAG_BENICULT" attribute
     */
    public void setFLAGBENICULT(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagbenicult)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGBENICULT$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGBENICULT$34);
            }
            target.setEnumValue(flagbenicult);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_BENICULT" attribute
     */
    public void xsetFLAGBENICULT(it.avlp.simog.massload.xmlbeans.FlagSNType flagbenicult)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGBENICULT$34);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGBENICULT$34);
            }
            target.set(flagbenicult);
        }
    }
    
    /**
     * Unsets the "FLAG_BENICULT" attribute
     */
    public void unsetFLAGBENICULT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGBENICULT$34);
        }
    }
    
    /**
     * Gets the "FLAG_SOSPESO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFLAGSOSPESO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSOSPESO$36);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SOSPESO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFLAGSOSPESO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSOSPESO$36);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_SOSPESO" attribute
     */
    public boolean isSetFLAGSOSPESO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FLAGSOSPESO$36) != null;
        }
    }
    
    /**
     * Sets the "FLAG_SOSPESO" attribute
     */
    public void setFLAGSOSPESO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum flagsospeso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGSOSPESO$36);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGSOSPESO$36);
            }
            target.setEnumValue(flagsospeso);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SOSPESO" attribute
     */
    public void xsetFLAGSOSPESO(it.avlp.simog.massload.xmlbeans.FlagSNType flagsospeso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FLAGSOSPESO$36);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FLAGSOSPESO$36);
            }
            target.set(flagsospeso);
        }
    }
    
    /**
     * Unsets the "FLAG_SOSPESO" attribute
     */
    public void unsetFLAGSOSPESO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FLAGSOSPESO$36);
        }
    }
    /**
     * An XML LINK_SITO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PubblicazioneType$LINKSITO.
     */
    public static class LINKSITOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.PubblicazioneType.LINKSITO
    {
        
        public LINKSITOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected LINKSITOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
