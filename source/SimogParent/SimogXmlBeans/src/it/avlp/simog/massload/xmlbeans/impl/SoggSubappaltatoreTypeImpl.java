/*
 * XML Type:  SoggSubappaltatoreType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SoggSubappaltatoreType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SoggSubappaltatoreTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType
{
    
    public SoggSubappaltatoreTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICEFISCALESUBAPPALTATORE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_SUBAPPALTATORE");
    
    
    /**
     * Gets the "CODICE_FISCALE_SUBAPPALTATORE" attribute
     */
    public java.lang.String getCODICEFISCALESUBAPPALTATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALESUBAPPALTATORE$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_SUBAPPALTATORE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALESUBAPPALTATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALESUBAPPALTATORE$0);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE_SUBAPPALTATORE" attribute
     */
    public void setCODICEFISCALESUBAPPALTATORE(java.lang.String codicefiscalesubappaltatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALESUBAPPALTATORE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALESUBAPPALTATORE$0);
            }
            target.setStringValue(codicefiscalesubappaltatore);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_SUBAPPALTATORE" attribute
     */
    public void xsetCODICEFISCALESUBAPPALTATORE(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscalesubappaltatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALESUBAPPALTATORE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALESUBAPPALTATORE$0);
            }
            target.set(codicefiscalesubappaltatore);
        }
    }
}
