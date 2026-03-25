/*
 * XML Type:  DatiCUPType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiCUPType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiCUPType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiCUPTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiCUPType
{
    
    public DatiCUPTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CUP$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CUP");
    private static final javax.xml.namespace.QName IDRICHIESTA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_RICHIESTA");
    private static final javax.xml.namespace.QName DATIDIPE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATI_DIPE");
    private static final javax.xml.namespace.QName VALIDO$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VALIDO");
    private static final javax.xml.namespace.QName OKUTENTE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OK_UTENTE");
    private static final javax.xml.namespace.QName TEMATICAPNRR$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TEMATICA_PNRR");
    
    
    /**
     * Gets the "CUP" attribute
     */
    public java.lang.String getCUP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUP$0);
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
            target = (it.avlp.simog.massload.xmlbeans.CupType)get_store().find_attribute_user(CUP$0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUP$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUP$0);
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
            target = (it.avlp.simog.massload.xmlbeans.CupType)get_store().find_attribute_user(CUP$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CupType)get_store().add_attribute_user(CUP$0);
            }
            target.set(cup);
        }
    }
    
    /**
     * Gets the "ID_RICHIESTA" attribute
     */
    public long getIDRICHIESTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDRICHIESTA$2);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_RICHIESTA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LongType xgetIDRICHIESTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LongType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().find_attribute_user(IDRICHIESTA$2);
            return target;
        }
    }
    
    /**
     * True if has "ID_RICHIESTA" attribute
     */
    public boolean isSetIDRICHIESTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDRICHIESTA$2) != null;
        }
    }
    
    /**
     * Sets the "ID_RICHIESTA" attribute
     */
    public void setIDRICHIESTA(long idrichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDRICHIESTA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDRICHIESTA$2);
            }
            target.setLongValue(idrichiesta);
        }
    }
    
    /**
     * Sets (as xml) the "ID_RICHIESTA" attribute
     */
    public void xsetIDRICHIESTA(it.avlp.simog.massload.xmlbeans.LongType idrichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LongType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().find_attribute_user(IDRICHIESTA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().add_attribute_user(IDRICHIESTA$2);
            }
            target.set(idrichiesta);
        }
    }
    
    /**
     * Unsets the "ID_RICHIESTA" attribute
     */
    public void unsetIDRICHIESTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDRICHIESTA$2);
        }
    }
    
    /**
     * Gets the "DATI_DIPE" attribute
     */
    public java.lang.String getDATIDIPE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATIDIPE$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATI_DIPE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE xgetDATIDIPE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE)get_store().find_attribute_user(DATIDIPE$4);
            return target;
        }
    }
    
    /**
     * True if has "DATI_DIPE" attribute
     */
    public boolean isSetDATIDIPE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATIDIPE$4) != null;
        }
    }
    
    /**
     * Sets the "DATI_DIPE" attribute
     */
    public void setDATIDIPE(java.lang.String datidipe)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATIDIPE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATIDIPE$4);
            }
            target.setStringValue(datidipe);
        }
    }
    
    /**
     * Sets (as xml) the "DATI_DIPE" attribute
     */
    public void xsetDATIDIPE(it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE datidipe)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE)get_store().find_attribute_user(DATIDIPE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE)get_store().add_attribute_user(DATIDIPE$4);
            }
            target.set(datidipe);
        }
    }
    
    /**
     * Unsets the "DATI_DIPE" attribute
     */
    public void unsetDATIDIPE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATIDIPE$4);
        }
    }
    
    /**
     * Gets the "VALIDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getVALIDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALIDO$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "VALIDO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetVALIDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(VALIDO$6);
            return target;
        }
    }
    
    /**
     * True if has "VALIDO" attribute
     */
    public boolean isSetVALIDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(VALIDO$6) != null;
        }
    }
    
    /**
     * Sets the "VALIDO" attribute
     */
    public void setVALIDO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum valido)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALIDO$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALIDO$6);
            }
            target.setEnumValue(valido);
        }
    }
    
    /**
     * Sets (as xml) the "VALIDO" attribute
     */
    public void xsetVALIDO(it.avlp.simog.massload.xmlbeans.FlagSNType valido)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(VALIDO$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(VALIDO$6);
            }
            target.set(valido);
        }
    }
    
    /**
     * Unsets the "VALIDO" attribute
     */
    public void unsetVALIDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(VALIDO$6);
        }
    }
    
    /**
     * Gets the "OK_UTENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getOKUTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OKUTENTE$8);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "OK_UTENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetOKUTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(OKUTENTE$8);
            return target;
        }
    }
    
    /**
     * True if has "OK_UTENTE" attribute
     */
    public boolean isSetOKUTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OKUTENTE$8) != null;
        }
    }
    
    /**
     * Sets the "OK_UTENTE" attribute
     */
    public void setOKUTENTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum okutente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OKUTENTE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OKUTENTE$8);
            }
            target.setEnumValue(okutente);
        }
    }
    
    /**
     * Sets (as xml) the "OK_UTENTE" attribute
     */
    public void xsetOKUTENTE(it.avlp.simog.massload.xmlbeans.FlagSNType okutente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(OKUTENTE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(OKUTENTE$8);
            }
            target.set(okutente);
        }
    }
    
    /**
     * Unsets the "OK_UTENTE" attribute
     */
    public void unsetOKUTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OKUTENTE$8);
        }
    }
    
    /**
     * Gets the "TEMATICA_PNRR" attribute
     */
    public java.lang.String getTEMATICAPNRR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEMATICAPNRR$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TEMATICA_PNRR" attribute
     */
    public org.apache.xmlbeans.XmlString xgetTEMATICAPNRR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEMATICAPNRR$10);
            return target;
        }
    }
    
    /**
     * True if has "TEMATICA_PNRR" attribute
     */
    public boolean isSetTEMATICAPNRR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TEMATICAPNRR$10) != null;
        }
    }
    
    /**
     * Sets the "TEMATICA_PNRR" attribute
     */
    public void setTEMATICAPNRR(java.lang.String tematicapnrr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEMATICAPNRR$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TEMATICAPNRR$10);
            }
            target.setStringValue(tematicapnrr);
        }
    }
    
    /**
     * Sets (as xml) the "TEMATICA_PNRR" attribute
     */
    public void xsetTEMATICAPNRR(org.apache.xmlbeans.XmlString tematicapnrr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEMATICAPNRR$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TEMATICAPNRR$10);
            }
            target.set(tematicapnrr);
        }
    }
    
    /**
     * Unsets the "TEMATICA_PNRR" attribute
     */
    public void unsetTEMATICAPNRR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TEMATICAPNRR$10);
        }
    }
    /**
     * An XML DATI_DIPE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DatiCUPType$DATIDIPE.
     */
    public static class DATIDIPEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DatiCUPType.DATIDIPE
    {
        
        public DATIDIPEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DATIDIPEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
