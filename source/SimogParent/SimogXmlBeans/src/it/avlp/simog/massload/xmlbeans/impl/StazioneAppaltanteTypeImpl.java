/*
 * XML Type:  stazioneAppaltanteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML stazioneAppaltanteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class StazioneAppaltanteTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType
{
    
    public StazioneAppaltanteTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CF$0 = 
        new javax.xml.namespace.QName("", "cf");
    private static final javax.xml.namespace.QName DENOMINAZIONE$2 = 
        new javax.xml.namespace.QName("", "denominazione");
    private static final javax.xml.namespace.QName INDIRIZZO$4 = 
        new javax.xml.namespace.QName("", "indirizzo");
    private static final javax.xml.namespace.QName CAP$6 = 
        new javax.xml.namespace.QName("", "cap");
    private static final javax.xml.namespace.QName COMUNE$8 = 
        new javax.xml.namespace.QName("", "comune");
    private static final javax.xml.namespace.QName CATEGORIA$10 = 
        new javax.xml.namespace.QName("", "categoria");
    private static final javax.xml.namespace.QName IDOSSERVATORIO$12 = 
        new javax.xml.namespace.QName("", "idOsservatorio");
    
    
    /**
     * Gets the "cf" element
     */
    public java.lang.String getCf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CF$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cf" element
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_element_user(CF$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "cf" element
     */
    public boolean isSetCf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CF$0) != 0;
        }
    }
    
    /**
     * Sets the "cf" element
     */
    public void setCf(java.lang.String cf)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CF$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CF$0);
            }
            target.setStringValue(cf);
        }
    }
    
    /**
     * Sets (as xml) the "cf" element
     */
    public void xsetCf(it.avlp.simog.massload.xmlbeans.CodFiscType cf)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_element_user(CF$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_element_user(CF$0);
            }
            target.set(cf);
        }
    }
    
    /**
     * Unsets the "cf" element
     */
    public void unsetCf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CF$0, 0);
        }
    }
    
    /**
     * Gets the "denominazione" element
     */
    public java.lang.String getDenominazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "denominazione" element
     */
    public boolean isSetDenominazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DENOMINAZIONE$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONE$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DENOMINAZIONE$2);
            }
            target.set(denominazione);
        }
    }
    
    /**
     * Unsets the "denominazione" element
     */
    public void unsetDenominazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DENOMINAZIONE$2, 0);
        }
    }
    
    /**
     * Gets the "indirizzo" element
     */
    public java.lang.String getIndirizzo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "indirizzo" element
     */
    public org.apache.xmlbeans.XmlString xgetIndirizzo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "indirizzo" element
     */
    public boolean isSetIndirizzo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDIRIZZO$4) != 0;
        }
    }
    
    /**
     * Sets the "indirizzo" element
     */
    public void setIndirizzo(java.lang.String indirizzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZO$4);
            }
            target.setStringValue(indirizzo);
        }
    }
    
    /**
     * Sets (as xml) the "indirizzo" element
     */
    public void xsetIndirizzo(org.apache.xmlbeans.XmlString indirizzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INDIRIZZO$4);
            }
            target.set(indirizzo);
        }
    }
    
    /**
     * Unsets the "indirizzo" element
     */
    public void unsetIndirizzo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDIRIZZO$4, 0);
        }
    }
    
    /**
     * Gets the "cap" element
     */
    public java.lang.String getCap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cap" element
     */
    public org.apache.xmlbeans.XmlString xgetCap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "cap" element
     */
    public boolean isSetCap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAP$6) != 0;
        }
    }
    
    /**
     * Sets the "cap" element
     */
    public void setCap(java.lang.String cap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAP$6);
            }
            target.setStringValue(cap);
        }
    }
    
    /**
     * Sets (as xml) the "cap" element
     */
    public void xsetCap(org.apache.xmlbeans.XmlString cap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CAP$6);
            }
            target.set(cap);
        }
    }
    
    /**
     * Unsets the "cap" element
     */
    public void unsetCap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAP$6, 0);
        }
    }
    
    /**
     * Gets the "comune" element
     */
    public java.lang.String getComune()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "comune" element
     */
    public org.apache.xmlbeans.XmlString xgetComune()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "comune" element
     */
    public boolean isSetComune()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMUNE$8) != 0;
        }
    }
    
    /**
     * Sets the "comune" element
     */
    public void setComune(java.lang.String comune)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMUNE$8);
            }
            target.setStringValue(comune);
        }
    }
    
    /**
     * Sets (as xml) the "comune" element
     */
    public void xsetComune(org.apache.xmlbeans.XmlString comune)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMUNE$8);
            }
            target.set(comune);
        }
    }
    
    /**
     * Unsets the "comune" element
     */
    public void unsetComune()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMUNE$8, 0);
        }
    }
    
    /**
     * Gets the "categoria" element
     */
    public java.lang.String getCategoria()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CATEGORIA$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "categoria" element
     */
    public org.apache.xmlbeans.XmlString xgetCategoria()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CATEGORIA$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "categoria" element
     */
    public boolean isSetCategoria()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CATEGORIA$10) != 0;
        }
    }
    
    /**
     * Sets the "categoria" element
     */
    public void setCategoria(java.lang.String categoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CATEGORIA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CATEGORIA$10);
            }
            target.setStringValue(categoria);
        }
    }
    
    /**
     * Sets (as xml) the "categoria" element
     */
    public void xsetCategoria(org.apache.xmlbeans.XmlString categoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CATEGORIA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CATEGORIA$10);
            }
            target.set(categoria);
        }
    }
    
    /**
     * Unsets the "categoria" element
     */
    public void unsetCategoria()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CATEGORIA$10, 0);
        }
    }
    
    /**
     * Gets the "idOsservatorio" element
     */
    public java.lang.String getIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDOSSERVATORIO$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "idOsservatorio" element
     */
    public org.apache.xmlbeans.XmlString xgetIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDOSSERVATORIO$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "idOsservatorio" element
     */
    public boolean isSetIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDOSSERVATORIO$12) != 0;
        }
    }
    
    /**
     * Sets the "idOsservatorio" element
     */
    public void setIdOsservatorio(java.lang.String idOsservatorio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDOSSERVATORIO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDOSSERVATORIO$12);
            }
            target.setStringValue(idOsservatorio);
        }
    }
    
    /**
     * Sets (as xml) the "idOsservatorio" element
     */
    public void xsetIdOsservatorio(org.apache.xmlbeans.XmlString idOsservatorio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDOSSERVATORIO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDOSSERVATORIO$12);
            }
            target.set(idOsservatorio);
        }
    }
    
    /**
     * Unsets the "idOsservatorio" element
     */
    public void unsetIdOsservatorio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDOSSERVATORIO$12, 0);
        }
    }
}
