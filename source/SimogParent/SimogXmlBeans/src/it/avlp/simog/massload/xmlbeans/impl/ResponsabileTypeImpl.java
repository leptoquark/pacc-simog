/*
 * XML Type:  ResponsabileType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ResponsabileType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ResponsabileType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ResponsabileTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ResponsabileType
{
    
    public ResponsabileTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SOGGETTOESTERO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SOGGETTO_ESTERO");
    private static final javax.xml.namespace.QName CODICEFISCALERESPONSABILE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_FISCALE_RESPONSABILE");
    private static final javax.xml.namespace.QName COGNOME$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "COGNOME");
    private static final javax.xml.namespace.QName NOME$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NOME");
    private static final javax.xml.namespace.QName TELEFONO$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TELEFONO");
    private static final javax.xml.namespace.QName EMAIL$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "EMAIL");
    private static final javax.xml.namespace.QName FAX$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FAX");
    private static final javax.xml.namespace.QName INDIRIZZO$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INDIRIZZO");
    private static final javax.xml.namespace.QName CAP$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CAP");
    private static final javax.xml.namespace.QName CODICEISTATCOMUNE$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CODICE_ISTAT_COMUNE");
    
    
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
     * True if has "SOGGETTO_ESTERO" attribute
     */
    public boolean isSetSOGGETTOESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SOGGETTOESTERO$0) != null;
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
     * Unsets the "SOGGETTO_ESTERO" attribute
     */
    public void unsetSOGGETTOESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SOGGETTOESTERO$0);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public java.lang.String getCODICEFISCALERESPONSABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCODICEFISCALERESPONSABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$2);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public void setCODICEFISCALERESPONSABILE(java.lang.String codicefiscaleresponsabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEFISCALERESPONSABILE$2);
            }
            target.setStringValue(codicefiscaleresponsabile);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE_RESPONSABILE" attribute
     */
    public void xsetCODICEFISCALERESPONSABILE(it.avlp.simog.massload.xmlbeans.CodFiscType codicefiscaleresponsabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CODICEFISCALERESPONSABILE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CODICEFISCALERESPONSABILE$2);
            }
            target.set(codicefiscaleresponsabile);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COGNOME$4);
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
    public it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME xgetCOGNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME)get_store().find_attribute_user(COGNOME$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COGNOME$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(COGNOME$4);
            }
            target.setStringValue(cognome);
        }
    }
    
    /**
     * Sets (as xml) the "COGNOME" attribute
     */
    public void xsetCOGNOME(it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME)get_store().find_attribute_user(COGNOME$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME)get_store().add_attribute_user(COGNOME$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOME$6);
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
    public it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME xgetNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME)get_store().find_attribute_user(NOME$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOME$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NOME$6);
            }
            target.setStringValue(nome);
        }
    }
    
    /**
     * Sets (as xml) the "NOME" attribute
     */
    public void xsetNOME(it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME)get_store().find_attribute_user(NOME$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME)get_store().add_attribute_user(NOME$6);
            }
            target.set(nome);
        }
    }
    
    /**
     * Gets the "TELEFONO" attribute
     */
    public java.lang.String getTELEFONO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TELEFONO$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TELEFONO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO xgetTELEFONO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO)get_store().find_attribute_user(TELEFONO$8);
            return target;
        }
    }
    
    /**
     * Sets the "TELEFONO" attribute
     */
    public void setTELEFONO(java.lang.String telefono)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TELEFONO$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TELEFONO$8);
            }
            target.setStringValue(telefono);
        }
    }
    
    /**
     * Sets (as xml) the "TELEFONO" attribute
     */
    public void xsetTELEFONO(it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO telefono)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO)get_store().find_attribute_user(TELEFONO$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO)get_store().add_attribute_user(TELEFONO$8);
            }
            target.set(telefono);
        }
    }
    
    /**
     * Gets the "EMAIL" attribute
     */
    public java.lang.String getEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "EMAIL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL xgetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL)get_store().find_attribute_user(EMAIL$10);
            return target;
        }
    }
    
    /**
     * Sets the "EMAIL" attribute
     */
    public void setEMAIL(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EMAIL$10);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "EMAIL" attribute
     */
    public void xsetEMAIL(it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL)get_store().find_attribute_user(EMAIL$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL)get_store().add_attribute_user(EMAIL$10);
            }
            target.set(email);
        }
    }
    
    /**
     * Gets the "FAX" attribute
     */
    public java.lang.String getFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FAX" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX xgetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX)get_store().find_attribute_user(FAX$12);
            return target;
        }
    }
    
    /**
     * Sets the "FAX" attribute
     */
    public void setFAX(java.lang.String fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FAX$12);
            }
            target.setStringValue(fax);
        }
    }
    
    /**
     * Sets (as xml) the "FAX" attribute
     */
    public void xsetFAX(it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX)get_store().find_attribute_user(FAX$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX)get_store().add_attribute_user(FAX$12);
            }
            target.set(fax);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INDIRIZZO$14);
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
    public it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO xgetINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO)get_store().find_attribute_user(INDIRIZZO$14);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INDIRIZZO$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INDIRIZZO$14);
            }
            target.setStringValue(indirizzo);
        }
    }
    
    /**
     * Sets (as xml) the "INDIRIZZO" attribute
     */
    public void xsetINDIRIZZO(it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO indirizzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO)get_store().find_attribute_user(INDIRIZZO$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO)get_store().add_attribute_user(INDIRIZZO$14);
            }
            target.set(indirizzo);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAP$16);
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
    public it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP xgetCAP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP)get_store().find_attribute_user(CAP$16);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CAP$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CAP$16);
            }
            target.setStringValue(cap);
        }
    }
    
    /**
     * Sets (as xml) the "CAP" attribute
     */
    public void xsetCAP(it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP cap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP target = null;
            target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP)get_store().find_attribute_user(CAP$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP)get_store().add_attribute_user(CAP$16);
            }
            target.set(cap);
        }
    }
    
    /**
     * Gets the "CODICE_ISTAT_COMUNE" attribute
     */
    public java.lang.String getCODICEISTATCOMUNE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEISTATCOMUNE$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_ISTAT_COMUNE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LuogoIstatType xgetCODICEISTATCOMUNE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().find_attribute_user(CODICEISTATCOMUNE$18);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_ISTAT_COMUNE" attribute
     */
    public void setCODICEISTATCOMUNE(java.lang.String codiceistatcomune)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICEISTATCOMUNE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICEISTATCOMUNE$18);
            }
            target.setStringValue(codiceistatcomune);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_ISTAT_COMUNE" attribute
     */
    public void xsetCODICEISTATCOMUNE(it.avlp.simog.massload.xmlbeans.LuogoIstatType codiceistatcomune)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoIstatType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().find_attribute_user(CODICEISTATCOMUNE$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LuogoIstatType)get_store().add_attribute_user(CODICEISTATCOMUNE$18);
            }
            target.set(codiceistatcomune);
        }
    }
    /**
     * An XML COGNOME(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ResponsabileType$COGNOME.
     */
    public static class COGNOMEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ResponsabileType.COGNOME
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
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ResponsabileType$NOME.
     */
    public static class NOMEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ResponsabileType.NOME
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
     * An XML TELEFONO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ResponsabileType$TELEFONO.
     */
    public static class TELEFONOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ResponsabileType.TELEFONO
    {
        
        public TELEFONOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected TELEFONOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML EMAIL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ResponsabileType$EMAIL.
     */
    public static class EMAILImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ResponsabileType.EMAIL
    {
        
        public EMAILImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected EMAILImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML FAX(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ResponsabileType$FAX.
     */
    public static class FAXImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ResponsabileType.FAX
    {
        
        public FAXImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected FAXImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML INDIRIZZO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ResponsabileType$INDIRIZZO.
     */
    public static class INDIRIZZOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ResponsabileType.INDIRIZZO
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
     * An XML CAP(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ResponsabileType$CAP.
     */
    public static class CAPImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ResponsabileType.CAP
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
}
