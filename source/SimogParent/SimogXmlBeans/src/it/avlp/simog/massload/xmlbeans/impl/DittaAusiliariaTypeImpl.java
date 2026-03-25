/*
 * XML Type:  DittaAusiliariaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DittaAusiliariaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DittaAusiliariaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DittaAusiliariaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DittaAusiliariaType
{
    
    public DittaAusiliariaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FLAGAVVALIMENTO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FLAG_AVVALIMENTO");
    private static final javax.xml.namespace.QName CODICEFISCALEAGGIUDICATARIO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_AGGIUDICATARIO");
    private static final javax.xml.namespace.QName CODICESTATOAGGIUDICATARIO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_STATO_AGGIUDICATARIO");
    private static final javax.xml.namespace.QName CODICEFISCALEAUSILIARIA$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_AUSILIARIA");
    private static final javax.xml.namespace.QName CODICESTATOAUSILIARIA$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_STATO_AUSILIARIA");
    
    
    /**
     * Gets the "FLAG_AVVALIMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum getFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAVVALIMENTO$0);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_AVVALIMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType xgetFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType)get_store().find_attribute_user(FLAGAVVALIMENTO$0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_AVVALIMENTO" attribute
     */
    public void setFLAGAVVALIMENTO(it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType.Enum flagavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FLAGAVVALIMENTO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FLAGAVVALIMENTO$0);
            }
            target.setEnumValue(flagavvalimento);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_AVVALIMENTO" attribute
     */
    public void xsetFLAGAVVALIMENTO(it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType flagavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType)get_store().find_attribute_user(FLAGAVVALIMENTO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType)get_store().add_attribute_user(FLAGAVVALIMENTO$0);
            }
            target.set(flagavvalimento);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public java.lang.String getCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$2);
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
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$2);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$2);
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
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$2);
            }
            target.set(codicefiscaleaggiudicatario);
        }
    }
    
    /**
     * Gets the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    public java.lang.String getCODICESTATOAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATOAGGIUDICATARIO$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATOAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATOAGGIUDICATARIO$4);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    public void setCODICESTATOAGGIUDICATARIO(java.lang.String codicestatoaggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATOAGGIUDICATARIO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICESTATOAGGIUDICATARIO$4);
            }
            target.setStringValue(codicestatoaggiudicatario);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_STATO_AGGIUDICATARIO" attribute
     */
    public void xsetCODICESTATOAGGIUDICATARIO(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestatoaggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATOAGGIUDICATARIO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().add_attribute_user(CODICESTATOAGGIUDICATARIO$4);
            }
            target.set(codicestatoaggiudicatario);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    public java.lang.String getCODICEFISCALEAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAUSILIARIA$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALEAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAUSILIARIA$6);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    public void setCODICEFISCALEAUSILIARIA(java.lang.String codicefiscaleausiliaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAUSILIARIA$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALEAUSILIARIA$6);
            }
            target.setStringValue(codicefiscaleausiliaria);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_AUSILIARIA" attribute
     */
    public void xsetCODICEFISCALEAUSILIARIA(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleausiliaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAUSILIARIA$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALEAUSILIARIA$6);
            }
            target.set(codicefiscaleausiliaria);
        }
    }
    
    /**
     * Gets the "CODICE_STATO_AUSILIARIA" attribute
     */
    public java.lang.String getCODICESTATOAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATOAUSILIARIA$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_STATO_AUSILIARIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.StatoEsteroType xgetCODICESTATOAUSILIARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATOAUSILIARIA$8);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_STATO_AUSILIARIA" attribute
     */
    public void setCODICESTATOAUSILIARIA(java.lang.String codicestatoausiliaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICESTATOAUSILIARIA$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICESTATOAUSILIARIA$8);
            }
            target.setStringValue(codicestatoausiliaria);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_STATO_AUSILIARIA" attribute
     */
    public void xsetCODICESTATOAUSILIARIA(it.avlp.simog.massload.xmlbeans.StatoEsteroType codicestatoausiliaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StatoEsteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().find_attribute_user(CODICESTATOAUSILIARIA$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StatoEsteroType)get_store().add_attribute_user(CODICESTATOAUSILIARIA$8);
            }
            target.set(codicestatoausiliaria);
        }
    }
}
