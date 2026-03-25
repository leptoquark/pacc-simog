/*
 * XML Type:  ContenziosoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ContenziosoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ContenziosoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ContenziosoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ContenziosoType
{
    
    public ContenziosoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONTENZIOSOGARA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CONTENZIOSO_GARA");
    private static final javax.xml.namespace.QName MOTIVAZIONE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MOTIVAZIONE");
    private static final javax.xml.namespace.QName CODICEFISCALEDITTA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_DITTA");
    
    
    /**
     * Gets the "CONTENZIOSO_GARA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getCONTENZIOSOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTENZIOSOGARA$0);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CONTENZIOSO_GARA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetCONTENZIOSOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CONTENZIOSOGARA$0);
            return target;
        }
    }
    
    /**
     * Sets the "CONTENZIOSO_GARA" attribute
     */
    public void setCONTENZIOSOGARA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum contenziosogara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTENZIOSOGARA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONTENZIOSOGARA$0);
            }
            target.setEnumValue(contenziosogara);
        }
    }
    
    /**
     * Sets (as xml) the "CONTENZIOSO_GARA" attribute
     */
    public void xsetCONTENZIOSOGARA(it.avlp.simog.massload.xmlbeans.FlagSNType contenziosogara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(CONTENZIOSOGARA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(CONTENZIOSOGARA$0);
            }
            target.set(contenziosogara);
        }
    }
    
    /**
     * Gets the "MOTIVAZIONE" attribute
     */
    public java.lang.String getMOTIVAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVAZIONE$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE xgetMOTIVAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE)get_store().find_attribute_user(MOTIVAZIONE$2);
            return target;
        }
    }
    
    /**
     * True if has "MOTIVAZIONE" attribute
     */
    public boolean isSetMOTIVAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MOTIVAZIONE$2) != null;
        }
    }
    
    /**
     * Sets the "MOTIVAZIONE" attribute
     */
    public void setMOTIVAZIONE(java.lang.String motivazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVAZIONE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOTIVAZIONE$2);
            }
            target.setStringValue(motivazione);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVAZIONE" attribute
     */
    public void xsetMOTIVAZIONE(it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE motivazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE)get_store().find_attribute_user(MOTIVAZIONE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE)get_store().add_attribute_user(MOTIVAZIONE$2);
            }
            target.set(motivazione);
        }
    }
    
    /**
     * Unsets the "MOTIVAZIONE" attribute
     */
    public void unsetMOTIVAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MOTIVAZIONE$2);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE_DITTA" attribute
     */
    public java.lang.String getCODICEFISCALEDITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEDITTA$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_DITTA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEDITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEDITTA$4);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE_DITTA" attribute
     */
    public void setCODICEFISCALEDITTA(java.lang.String codicefiscaleditta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEDITTA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALEDITTA$4);
            }
            target.setStringValue(codicefiscaleditta);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_DITTA" attribute
     */
    public void xsetCODICEFISCALEDITTA(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleditta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEDITTA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALEDITTA$4);
            }
            target.set(codicefiscaleditta);
        }
    }
    /**
     * An XML MOTIVAZIONE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ContenziosoType$MOTIVAZIONE.
     */
    public static class MOTIVAZIONEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ContenziosoType.MOTIVAZIONE
    {
        
        public MOTIVAZIONEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MOTIVAZIONEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
