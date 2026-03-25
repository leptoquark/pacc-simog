/*
 * XML Type:  AggiudicatarioType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AggiudicatarioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AggiudicatarioType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AggiudicatarioTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType
{
    
    public AggiudicatarioTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SOGGETTOESTERO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SOGGETTO_ESTERO");
    private static final javax.xml.namespace.QName CODICESTATO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_STATO");
    private static final javax.xml.namespace.QName CODICEFISCALEAGGIUDICATARIO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_AGGIUDICATARIO");
    private static final javax.xml.namespace.QName DENOMINAZIONE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DENOMINAZIONE");
    private static final javax.xml.namespace.QName COGNOME$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "COGNOME");
    private static final javax.xml.namespace.QName NOME$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NOME");
    private static final javax.xml.namespace.QName CAMERACOMMERCIO$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CAMERA_COMMERCIO");
    private static final javax.xml.namespace.QName PARTITAIVA$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PARTITA_IVA");
    private static final javax.xml.namespace.QName CFRAPPRESENTANTE$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_RAPPRESENTANTE");
    private static final javax.xml.namespace.QName INDIRIZZO$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INDIRIZZO");
    private static final javax.xml.namespace.QName CIVICO$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CIVICO");
    private static final javax.xml.namespace.QName CAP$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CAP");
    private static final javax.xml.namespace.QName CITTA$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CITTA");
    private static final javax.xml.namespace.QName PROVINCIA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PROVINCIA");
    
    
    /**
     * Gets the "SOGGETTO_ESTERO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getSOGGETTOESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SOGGETTOESTERO$0);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SOGGETTO_ESTERO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetSOGGETTOESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SOGGETTOESTERO$0);
            return target;
        }
    }
    
    /**
     * Sets the "SOGGETTO_ESTERO" attribute
     */
    public void setSOGGETTOESTERO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum soggettoestero)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SOGGETTOESTERO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SOGGETTOESTERO$0);
            }
            target.setEnumValue(soggettoestero);
        }
    }
    
    /**
     * Sets (as xml) the "SOGGETTO_ESTERO" attribute
     */
    public void xsetSOGGETTOESTERO(it.avlp.simog.massload.xmlbeans.FlagSNType soggettoestero)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SOGGETTOESTERO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(SOGGETTOESTERO$0);
            }
            target.set(soggettoestero);
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
     * Gets the "CODICE_FISCALE_AGGIUDICATARIO" attribute
     */
    public java.lang.String getCODICEFISCALEAGGIUDICATARIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$4);
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
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$4);
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
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALEAGGIUDICATARIO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALEAGGIUDICATARIO$4);
            }
            target.set(codicefiscaleaggiudicatario);
        }
    }
    
    /**
     * Gets the "DENOMINAZIONE" attribute
     */
    public java.lang.String getDENOMINAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENOMINAZIONE$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DENOMINAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE xgetDENOMINAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE)get_store().find_attribute_user(DENOMINAZIONE$6);
            return target;
        }
    }
    
    /**
     * Sets the "DENOMINAZIONE" attribute
     */
    public void setDENOMINAZIONE(java.lang.String denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DENOMINAZIONE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DENOMINAZIONE$6);
            }
            target.setStringValue(denominazione);
        }
    }
    
    /**
     * Sets (as xml) the "DENOMINAZIONE" attribute
     */
    public void xsetDENOMINAZIONE(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE)get_store().find_attribute_user(DENOMINAZIONE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE)get_store().add_attribute_user(DENOMINAZIONE$6);
            }
            target.set(denominazione);
        }
    }
    
    /**
     * Gets the "COGNOME" attribute
     */
    public java.lang.String getCOGNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COGNOME$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "COGNOME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME xgetCOGNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME)get_store().find_attribute_user(COGNOME$8);
            return target;
        }
    }
    
    /**
     * Sets the "COGNOME" attribute
     */
    public void setCOGNOME(java.lang.String cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COGNOME$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(COGNOME$8);
            }
            target.setStringValue(cognome);
        }
    }
    
    /**
     * Sets (as xml) the "COGNOME" attribute
     */
    public void xsetCOGNOME(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME)get_store().find_attribute_user(COGNOME$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME)get_store().add_attribute_user(COGNOME$8);
            }
            target.set(cognome);
        }
    }
    
    /**
     * Gets the "NOME" attribute
     */
    public java.lang.String getNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOME$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NOME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME xgetNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME)get_store().find_attribute_user(NOME$10);
            return target;
        }
    }
    
    /**
     * Sets the "NOME" attribute
     */
    public void setNOME(java.lang.String nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOME$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NOME$10);
            }
            target.setStringValue(nome);
        }
    }
    
    /**
     * Sets (as xml) the "NOME" attribute
     */
    public void xsetNOME(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME)get_store().find_attribute_user(NOME$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME)get_store().add_attribute_user(NOME$10);
            }
            target.set(nome);
        }
    }
    
    /**
     * Gets the "CAMERA_COMMERCIO" attribute
     */
    public java.lang.String getCAMERACOMMERCIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAMERACOMMERCIO$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CAMERA_COMMERCIO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO xgetCAMERACOMMERCIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO)get_store().find_attribute_user(CAMERACOMMERCIO$12);
            return target;
        }
    }
    
    /**
     * Sets the "CAMERA_COMMERCIO" attribute
     */
    public void setCAMERACOMMERCIO(java.lang.String cameracommercio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAMERACOMMERCIO$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CAMERACOMMERCIO$12);
            }
            target.setStringValue(cameracommercio);
        }
    }
    
    /**
     * Sets (as xml) the "CAMERA_COMMERCIO" attribute
     */
    public void xsetCAMERACOMMERCIO(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO cameracommercio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO)get_store().find_attribute_user(CAMERACOMMERCIO$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO)get_store().add_attribute_user(CAMERACOMMERCIO$12);
            }
            target.set(cameracommercio);
        }
    }
    
    /**
     * Gets the "PARTITA_IVA" attribute
     */
    public java.lang.String getPARTITAIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PARTITAIVA$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PARTITA_IVA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetPARTITAIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(PARTITAIVA$14);
            return target;
        }
    }
    
    /**
     * Sets the "PARTITA_IVA" attribute
     */
    public void setPARTITAIVA(java.lang.String partitaiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PARTITAIVA$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PARTITAIVA$14);
            }
            target.setStringValue(partitaiva);
        }
    }
    
    /**
     * Sets (as xml) the "PARTITA_IVA" attribute
     */
    public void xsetPARTITAIVA(it.avlp.simog.massload.xmlbeans.CodFiscType partitaiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(PARTITAIVA$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(PARTITAIVA$14);
            }
            target.set(partitaiva);
        }
    }
    
    /**
     * Gets the "CF_RAPPRESENTANTE" attribute
     */
    public java.lang.String getCFRAPPRESENTANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFRAPPRESENTANTE$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_RAPPRESENTANTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFRAPPRESENTANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFRAPPRESENTANTE$16);
            return target;
        }
    }
    
    /**
     * Sets the "CF_RAPPRESENTANTE" attribute
     */
    public void setCFRAPPRESENTANTE(java.lang.String cfrappresentante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFRAPPRESENTANTE$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFRAPPRESENTANTE$16);
            }
            target.setStringValue(cfrappresentante);
        }
    }
    
    /**
     * Sets (as xml) the "CF_RAPPRESENTANTE" attribute
     */
    public void xsetCFRAPPRESENTANTE(it.avlp.simog.massload.xmlbeans.CodFiscType cfrappresentante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFRAPPRESENTANTE$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFRAPPRESENTANTE$16);
            }
            target.set(cfrappresentante);
        }
    }
    
    /**
     * Gets the "INDIRIZZO" attribute
     */
    public java.lang.String getINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INDIRIZZO$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "INDIRIZZO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO xgetINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO)get_store().find_attribute_user(INDIRIZZO$18);
            return target;
        }
    }
    
    /**
     * Sets the "INDIRIZZO" attribute
     */
    public void setINDIRIZZO(java.lang.String indirizzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INDIRIZZO$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INDIRIZZO$18);
            }
            target.setStringValue(indirizzo);
        }
    }
    
    /**
     * Sets (as xml) the "INDIRIZZO" attribute
     */
    public void xsetINDIRIZZO(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO indirizzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO)get_store().find_attribute_user(INDIRIZZO$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO)get_store().add_attribute_user(INDIRIZZO$18);
            }
            target.set(indirizzo);
        }
    }
    
    /**
     * Gets the "CIVICO" attribute
     */
    public java.lang.String getCIVICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIVICO$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIVICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO xgetCIVICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO)get_store().find_attribute_user(CIVICO$20);
            return target;
        }
    }
    
    /**
     * Sets the "CIVICO" attribute
     */
    public void setCIVICO(java.lang.String civico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CIVICO$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CIVICO$20);
            }
            target.setStringValue(civico);
        }
    }
    
    /**
     * Sets (as xml) the "CIVICO" attribute
     */
    public void xsetCIVICO(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO civico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO)get_store().find_attribute_user(CIVICO$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO)get_store().add_attribute_user(CIVICO$20);
            }
            target.set(civico);
        }
    }
    
    /**
     * Gets the "CAP" attribute
     */
    public java.lang.String getCAP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAP$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CAP" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP xgetCAP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP)get_store().find_attribute_user(CAP$22);
            return target;
        }
    }
    
    /**
     * Sets the "CAP" attribute
     */
    public void setCAP(java.lang.String cap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAP$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CAP$22);
            }
            target.setStringValue(cap);
        }
    }
    
    /**
     * Sets (as xml) the "CAP" attribute
     */
    public void xsetCAP(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP cap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP)get_store().find_attribute_user(CAP$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP)get_store().add_attribute_user(CAP$22);
            }
            target.set(cap);
        }
    }
    
    /**
     * Gets the "CITTA" attribute
     */
    public java.lang.String getCITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CITTA$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CITTA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA xgetCITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA)get_store().find_attribute_user(CITTA$24);
            return target;
        }
    }
    
    /**
     * Sets the "CITTA" attribute
     */
    public void setCITTA(java.lang.String citta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CITTA$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CITTA$24);
            }
            target.setStringValue(citta);
        }
    }
    
    /**
     * Sets (as xml) the "CITTA" attribute
     */
    public void xsetCITTA(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA citta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA)get_store().find_attribute_user(CITTA$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA)get_store().add_attribute_user(CITTA$24);
            }
            target.set(citta);
        }
    }
    
    /**
     * Gets the "PROVINCIA" attribute
     */
    public java.lang.String getPROVINCIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROVINCIA$26);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROVINCIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA xgetPROVINCIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA)get_store().find_attribute_user(PROVINCIA$26);
            return target;
        }
    }
    
    /**
     * Sets the "PROVINCIA" attribute
     */
    public void setPROVINCIA(java.lang.String provincia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROVINCIA$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROVINCIA$26);
            }
            target.setStringValue(provincia);
        }
    }
    
    /**
     * Sets (as xml) the "PROVINCIA" attribute
     */
    public void xsetPROVINCIA(it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA provincia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA)get_store().find_attribute_user(PROVINCIA$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA)get_store().add_attribute_user(PROVINCIA$26);
            }
            target.set(provincia);
        }
    }
    /**
     * An XML DENOMINAZIONE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$DENOMINAZIONE.
     */
    public static class DENOMINAZIONEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.DENOMINAZIONE
    {
        
        public DENOMINAZIONEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DENOMINAZIONEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML COGNOME(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$COGNOME.
     */
    public static class COGNOMEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.COGNOME
    {
        
        public COGNOMEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected COGNOMEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NOME(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$NOME.
     */
    public static class NOMEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.NOME
    {
        
        public NOMEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NOMEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CAMERA_COMMERCIO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$CAMERACOMMERCIO.
     */
    public static class CAMERACOMMERCIOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAMERACOMMERCIO
    {
        
        public CAMERACOMMERCIOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CAMERACOMMERCIOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML INDIRIZZO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$INDIRIZZO.
     */
    public static class INDIRIZZOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.INDIRIZZO
    {
        
        public INDIRIZZOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected INDIRIZZOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CIVICO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$CIVICO.
     */
    public static class CIVICOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CIVICO
    {
        
        public CIVICOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CIVICOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CAP(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$CAP.
     */
    public static class CAPImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CAP
    {
        
        public CAPImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CAPImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CITTA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$CITTA.
     */
    public static class CITTAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.CITTA
    {
        
        public CITTAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CITTAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML PROVINCIA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AggiudicatarioType$PROVINCIA.
     */
    public static class PROVINCIAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AggiudicatarioType.PROVINCIA
    {
        
        public PROVINCIAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected PROVINCIAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
