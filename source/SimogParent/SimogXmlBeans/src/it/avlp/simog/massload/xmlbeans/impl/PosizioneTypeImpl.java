/*
 * XML Type:  PosizioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.PosizioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML PosizioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class PosizioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.PosizioneType
{
    
    public PosizioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICEFISCALEAGGIUDICATARIO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_AGGIUDICATARIO");
    private static final javax.xml.namespace.QName CODICESTATO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_STATO");
    private static final javax.xml.namespace.QName CODICEINPS$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_INPS");
    private static final javax.xml.namespace.QName CODICEINAIL$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_INAIL");
    private static final javax.xml.namespace.QName CODICECASSA$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_CASSA");
    
    
    /**
     * Gets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public java.lang.String getCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$0);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public void setCODICEFISCALEAGGIUDICATARIO(java.lang.String codicefiscaleaggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$0);
            }
            target.setStringValue(codicefiscaleaggiudicatario);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public void xsetCODICEFISCALEAGGIUDICATARIO(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleaggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$0);
            }
            target.set(codicefiscaleaggiudicatario);
        }
    }
    
    /**
     * Gets the "CODICE_STATO" attribute
     */
    public java.lang.String getCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_STATO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$2);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_STATO" attribute
     */
    public void setCODICESTATO(java.lang.String codicestato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICESTATO$2);
            }
            target.setStringValue(codicestato);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_STATO" attribute
     */
    public void xsetCODICESTATO(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().add_attribute_user(CODICESTATO$2);
            }
            target.set(codicestato);
        }
    }
    
    /**
     * Gets the "CODICE_INPS" attribute
     */
    public java.lang.String getCODICEINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEINPS$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_INPS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS xgetCODICEINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS)get_store().find_attribute_user(CODICEINPS$4);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_INPS" attribute
     */
    public boolean isSetCODICEINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODICEINPS$4) != null;
        }
    }
    
    /**
     * Sets the "CODICE_INPS" attribute
     */
    public void setCODICEINPS(java.lang.String codiceinps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEINPS$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEINPS$4);
            }
            target.setStringValue(codiceinps);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_INPS" attribute
     */
    public void xsetCODICEINPS(it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS codiceinps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS)get_store().find_attribute_user(CODICEINPS$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS)get_store().add_attribute_user(CODICEINPS$4);
            }
            target.set(codiceinps);
        }
    }
    
    /**
     * Unsets the "CODICE_INPS" attribute
     */
    public void unsetCODICEINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODICEINPS$4);
        }
    }
    
    /**
     * Gets the "CODICE_INAIL" attribute
     */
    public java.lang.String getCODICEINAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEINAIL$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_INAIL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL xgetCODICEINAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL)get_store().find_attribute_user(CODICEINAIL$6);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_INAIL" attribute
     */
    public boolean isSetCODICEINAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODICEINAIL$6) != null;
        }
    }
    
    /**
     * Sets the "CODICE_INAIL" attribute
     */
    public void setCODICEINAIL(java.lang.String codiceinail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEINAIL$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEINAIL$6);
            }
            target.setStringValue(codiceinail);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_INAIL" attribute
     */
    public void xsetCODICEINAIL(it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL codiceinail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL)get_store().find_attribute_user(CODICEINAIL$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL)get_store().add_attribute_user(CODICEINAIL$6);
            }
            target.set(codiceinail);
        }
    }
    
    /**
     * Unsets the "CODICE_INAIL" attribute
     */
    public void unsetCODICEINAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODICEINAIL$6);
        }
    }
    
    /**
     * Gets the "CODICE_CASSA" attribute
     */
    public java.lang.String getCODICECASSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICECASSA$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_CASSA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA xgetCODICECASSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA)get_store().find_attribute_user(CODICECASSA$8);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_CASSA" attribute
     */
    public boolean isSetCODICECASSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODICECASSA$8) != null;
        }
    }
    
    /**
     * Sets the "CODICE_CASSA" attribute
     */
    public void setCODICECASSA(java.lang.String codicecassa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICECASSA$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICECASSA$8);
            }
            target.setStringValue(codicecassa);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_CASSA" attribute
     */
    public void xsetCODICECASSA(it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA codicecassa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA target = null;
            target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA)get_store().find_attribute_user(CODICECASSA$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA)get_store().add_attribute_user(CODICECASSA$8);
            }
            target.set(codicecassa);
        }
    }
    
    /**
     * Unsets the "CODICE_CASSA" attribute
     */
    public void unsetCODICECASSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODICECASSA$8);
        }
    }
    /**
     * An XML CODICE_INPS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PosizioneType$CODICEINPS.
     */
    public static class CODICEINPSImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINPS
    {
        
        public CODICEINPSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CODICEINPSImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CODICE_INAIL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PosizioneType$CODICEINAIL.
     */
    public static class CODICEINAILImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.PosizioneType.CODICEINAIL
    {
        
        public CODICEINAILImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CODICEINAILImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CODICE_CASSA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PosizioneType$CODICECASSA.
     */
    public static class CODICECASSAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.PosizioneType.CODICECASSA
    {
        
        public CODICECASSAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CODICECASSAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
