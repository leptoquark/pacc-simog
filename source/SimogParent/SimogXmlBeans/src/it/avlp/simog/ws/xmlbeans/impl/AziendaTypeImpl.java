/*
 * XML Type:  aziendaType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.AziendaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans.impl;
/**
 * An XML aziendaType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AziendaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.ws.xmlbeans.AziendaType
{
    
    public AziendaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DENOMINAZIONE$0 = 
        new javax.xml.namespace.QName("", "denominazione");
    private static final javax.xml.namespace.QName CODICEFISCALE$2 = 
        new javax.xml.namespace.QName("", "codice_fiscale");
    private static final javax.xml.namespace.QName IDOSSERVATORIO$4 = 
        new javax.xml.namespace.QName("", "id_osservatorio");
    
    
    /**
     * Gets the "denominazione" element
     */
    public java.lang.String getDenominazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "denominazione" element
     */
    public org.apache.xmlbeans.XmlString xgetDenominazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "denominazione" element
     */
    public void setDenominazione(java.lang.String denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONE$0);
            }
            target.setStringValue(denominazione);
        }
    }
    
    /**
     * Sets (as xml) the "denominazione" element
     */
    public void xsetDenominazione(org.apache.xmlbeans.XmlString denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DENOMINAZIONE$0);
            }
            target.set(denominazione);
        }
    }
    
    /**
     * Gets the "codice_fiscale" element
     */
    public java.lang.String getCodiceFiscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codice_fiscale" element
     */
    public org.apache.xmlbeans.XmlString xgetCodiceFiscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codice_fiscale" element
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEFISCALE$2);
            }
            target.setStringValue(codiceFiscale);
        }
    }
    
    /**
     * Sets (as xml) the "codice_fiscale" element
     */
    public void xsetCodiceFiscale(org.apache.xmlbeans.XmlString codiceFiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICEFISCALE$2);
            }
            target.set(codiceFiscale);
        }
    }
    
    /**
     * Gets the "id_osservatorio" element
     */
    public java.lang.String getIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDOSSERVATORIO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id_osservatorio" element
     */
    public org.apache.xmlbeans.XmlString xgetIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDOSSERVATORIO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "id_osservatorio" element
     */
    public boolean isSetIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDOSSERVATORIO$4) != 0;
        }
    }
    
    /**
     * Sets the "id_osservatorio" element
     */
    public void setIdOsservatorio(java.lang.String idOsservatorio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDOSSERVATORIO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDOSSERVATORIO$4);
            }
            target.setStringValue(idOsservatorio);
        }
    }
    
    /**
     * Sets (as xml) the "id_osservatorio" element
     */
    public void xsetIdOsservatorio(org.apache.xmlbeans.XmlString idOsservatorio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDOSSERVATORIO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDOSSERVATORIO$4);
            }
            target.set(idOsservatorio);
        }
    }
    
    /**
     * Unsets the "id_osservatorio" element
     */
    public void unsetIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDOSSERVATORIO$4, 0);
        }
    }
}
