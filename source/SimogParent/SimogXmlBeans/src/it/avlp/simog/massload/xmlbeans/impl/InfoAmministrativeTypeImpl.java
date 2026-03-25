/*
 * XML Type:  InfoAmministrativeType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.InfoAmministrativeType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML InfoAmministrativeType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class InfoAmministrativeTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.InfoAmministrativeType
{
    
    public InfoAmministrativeTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PERIODOVALIDITAOFFERTE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERIODO_VALIDITA_OFFERTE");
    private static final javax.xml.namespace.QName MESIVALIDITAOFFERTE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MESI_VALIDITA_OFFERTE");
    private static final javax.xml.namespace.QName DATAAPERTURAOFFERTE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_APERTURA_OFFERTE");
    private static final javax.xml.namespace.QName ORAAPERTURAOFFERTE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ORA_APERTURA_OFFERTE");
    private static final javax.xml.namespace.QName LUOGOAPERTURAOFFERTE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_APERTURA_OFFERTE");
    private static final javax.xml.namespace.QName PERSONEAPERTURAOFFERTE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PERSONE_APERTURA_OFFERTE");
    
    
    /**
     * Gets the "PERIODO_VALIDITA_OFFERTE" attribute
     */
    public java.util.Calendar getPERIODOVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERIODOVALIDITAOFFERTE$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERIODO_VALIDITA_OFFERTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetPERIODOVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(PERIODOVALIDITAOFFERTE$0);
            return target;
        }
    }
    
    /**
     * True if has "PERIODO_VALIDITA_OFFERTE" attribute
     */
    public boolean isSetPERIODOVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PERIODOVALIDITAOFFERTE$0) != null;
        }
    }
    
    /**
     * Sets the "PERIODO_VALIDITA_OFFERTE" attribute
     */
    public void setPERIODOVALIDITAOFFERTE(java.util.Calendar periodovaliditaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERIODOVALIDITAOFFERTE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERIODOVALIDITAOFFERTE$0);
            }
            target.setCalendarValue(periodovaliditaofferte);
        }
    }
    
    /**
     * Sets (as xml) the "PERIODO_VALIDITA_OFFERTE" attribute
     */
    public void xsetPERIODOVALIDITAOFFERTE(it.avlp.simog.massload.xmlbeans.DbDateType periodovaliditaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(PERIODOVALIDITAOFFERTE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(PERIODOVALIDITAOFFERTE$0);
            }
            target.set(periodovaliditaofferte);
        }
    }
    
    /**
     * Unsets the "PERIODO_VALIDITA_OFFERTE" attribute
     */
    public void unsetPERIODOVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PERIODOVALIDITAOFFERTE$0);
        }
    }
    
    /**
     * Gets the "MESI_VALIDITA_OFFERTE" attribute
     */
    public int getMESIVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MESIVALIDITAOFFERTE$2);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "MESI_VALIDITA_OFFERTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE xgetMESIVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE)get_store().find_attribute_user(MESIVALIDITAOFFERTE$2);
            return target;
        }
    }
    
    /**
     * True if has "MESI_VALIDITA_OFFERTE" attribute
     */
    public boolean isSetMESIVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MESIVALIDITAOFFERTE$2) != null;
        }
    }
    
    /**
     * Sets the "MESI_VALIDITA_OFFERTE" attribute
     */
    public void setMESIVALIDITAOFFERTE(int mesivaliditaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MESIVALIDITAOFFERTE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MESIVALIDITAOFFERTE$2);
            }
            target.setIntValue(mesivaliditaofferte);
        }
    }
    
    /**
     * Sets (as xml) the "MESI_VALIDITA_OFFERTE" attribute
     */
    public void xsetMESIVALIDITAOFFERTE(it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE mesivaliditaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE)get_store().find_attribute_user(MESIVALIDITAOFFERTE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE)get_store().add_attribute_user(MESIVALIDITAOFFERTE$2);
            }
            target.set(mesivaliditaofferte);
        }
    }
    
    /**
     * Unsets the "MESI_VALIDITA_OFFERTE" attribute
     */
    public void unsetMESIVALIDITAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MESIVALIDITAOFFERTE$2);
        }
    }
    
    /**
     * Gets the "DATA_APERTURA_OFFERTE" attribute
     */
    public java.util.Calendar getDATAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAPERTURAOFFERTE$4);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_APERTURA_OFFERTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAPERTURAOFFERTE$4);
            return target;
        }
    }
    
    /**
     * True if has "DATA_APERTURA_OFFERTE" attribute
     */
    public boolean isSetDATAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DATAAPERTURAOFFERTE$4) != null;
        }
    }
    
    /**
     * Sets the "DATA_APERTURA_OFFERTE" attribute
     */
    public void setDATAAPERTURAOFFERTE(java.util.Calendar dataaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAAPERTURAOFFERTE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAAPERTURAOFFERTE$4);
            }
            target.setCalendarValue(dataaperturaofferte);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_APERTURA_OFFERTE" attribute
     */
    public void xsetDATAAPERTURAOFFERTE(it.avlp.simog.massload.xmlbeans.DbDateType dataaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAAPERTURAOFFERTE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAAPERTURAOFFERTE$4);
            }
            target.set(dataaperturaofferte);
        }
    }
    
    /**
     * Unsets the "DATA_APERTURA_OFFERTE" attribute
     */
    public void unsetDATAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DATAAPERTURAOFFERTE$4);
        }
    }
    
    /**
     * Gets the "ORA_APERTURA_OFFERTE" attribute
     */
    public java.lang.String getORAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORAAPERTURAOFFERTE$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ORA_APERTURA_OFFERTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.Time xgetORAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Time target = null;
            target = (it.avlp.simog.massload.xmlbeans.Time)get_store().find_attribute_user(ORAAPERTURAOFFERTE$6);
            return target;
        }
    }
    
    /**
     * True if has "ORA_APERTURA_OFFERTE" attribute
     */
    public boolean isSetORAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ORAAPERTURAOFFERTE$6) != null;
        }
    }
    
    /**
     * Sets the "ORA_APERTURA_OFFERTE" attribute
     */
    public void setORAAPERTURAOFFERTE(java.lang.String oraaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORAAPERTURAOFFERTE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORAAPERTURAOFFERTE$6);
            }
            target.setStringValue(oraaperturaofferte);
        }
    }
    
    /**
     * Sets (as xml) the "ORA_APERTURA_OFFERTE" attribute
     */
    public void xsetORAAPERTURAOFFERTE(it.avlp.simog.massload.xmlbeans.Time oraaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Time target = null;
            target = (it.avlp.simog.massload.xmlbeans.Time)get_store().find_attribute_user(ORAAPERTURAOFFERTE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Time)get_store().add_attribute_user(ORAAPERTURAOFFERTE$6);
            }
            target.set(oraaperturaofferte);
        }
    }
    
    /**
     * Unsets the "ORA_APERTURA_OFFERTE" attribute
     */
    public void unsetORAAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ORAAPERTURAOFFERTE$6);
        }
    }
    
    /**
     * Gets the "LUOGO_APERTURA_OFFERTE" attribute
     */
    public java.lang.String getLUOGOAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOAPERTURAOFFERTE$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LUOGO_APERTURA_OFFERTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE xgetLUOGOAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE)get_store().find_attribute_user(LUOGOAPERTURAOFFERTE$8);
            return target;
        }
    }
    
    /**
     * True if has "LUOGO_APERTURA_OFFERTE" attribute
     */
    public boolean isSetLUOGOAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(LUOGOAPERTURAOFFERTE$8) != null;
        }
    }
    
    /**
     * Sets the "LUOGO_APERTURA_OFFERTE" attribute
     */
    public void setLUOGOAPERTURAOFFERTE(java.lang.String luogoaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGOAPERTURAOFFERTE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LUOGOAPERTURAOFFERTE$8);
            }
            target.setStringValue(luogoaperturaofferte);
        }
    }
    
    /**
     * Sets (as xml) the "LUOGO_APERTURA_OFFERTE" attribute
     */
    public void xsetLUOGOAPERTURAOFFERTE(it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE luogoaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE)get_store().find_attribute_user(LUOGOAPERTURAOFFERTE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE)get_store().add_attribute_user(LUOGOAPERTURAOFFERTE$8);
            }
            target.set(luogoaperturaofferte);
        }
    }
    
    /**
     * Unsets the "LUOGO_APERTURA_OFFERTE" attribute
     */
    public void unsetLUOGOAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(LUOGOAPERTURAOFFERTE$8);
        }
    }
    
    /**
     * Gets the "PERSONE_APERTURA_OFFERTE" attribute
     */
    public java.lang.String getPERSONEAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERSONEAPERTURAOFFERTE$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERSONE_APERTURA_OFFERTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE xgetPERSONEAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE)get_store().find_attribute_user(PERSONEAPERTURAOFFERTE$10);
            return target;
        }
    }
    
    /**
     * True if has "PERSONE_APERTURA_OFFERTE" attribute
     */
    public boolean isSetPERSONEAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PERSONEAPERTURAOFFERTE$10) != null;
        }
    }
    
    /**
     * Sets the "PERSONE_APERTURA_OFFERTE" attribute
     */
    public void setPERSONEAPERTURAOFFERTE(java.lang.String personeaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PERSONEAPERTURAOFFERTE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PERSONEAPERTURAOFFERTE$10);
            }
            target.setStringValue(personeaperturaofferte);
        }
    }
    
    /**
     * Sets (as xml) the "PERSONE_APERTURA_OFFERTE" attribute
     */
    public void xsetPERSONEAPERTURAOFFERTE(it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE personeaperturaofferte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE)get_store().find_attribute_user(PERSONEAPERTURAOFFERTE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE)get_store().add_attribute_user(PERSONEAPERTURAOFFERTE$10);
            }
            target.set(personeaperturaofferte);
        }
    }
    
    /**
     * Unsets the "PERSONE_APERTURA_OFFERTE" attribute
     */
    public void unsetPERSONEAPERTURAOFFERTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PERSONEAPERTURAOFFERTE$10);
        }
    }
    /**
     * An XML MESI_VALIDITA_OFFERTE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.InfoAmministrativeType$MESIVALIDITAOFFERTE.
     */
    public static class MESIVALIDITAOFFERTEImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.MESIVALIDITAOFFERTE
    {
        
        public MESIVALIDITAOFFERTEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MESIVALIDITAOFFERTEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML LUOGO_APERTURA_OFFERTE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.InfoAmministrativeType$LUOGOAPERTURAOFFERTE.
     */
    public static class LUOGOAPERTURAOFFERTEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.LUOGOAPERTURAOFFERTE
    {
        
        public LUOGOAPERTURAOFFERTEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected LUOGOAPERTURAOFFERTEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML PERSONE_APERTURA_OFFERTE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.InfoAmministrativeType$PERSONEAPERTURAOFFERTE.
     */
    public static class PERSONEAPERTURAOFFERTEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.InfoAmministrativeType.PERSONEAPERTURAOFFERTE
    {
        
        public PERSONEAPERTURAOFFERTEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected PERSONEAPERTURAOFFERTEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
