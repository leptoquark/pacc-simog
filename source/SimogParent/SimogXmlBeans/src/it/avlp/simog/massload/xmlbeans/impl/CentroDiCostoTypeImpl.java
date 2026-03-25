/*
 * XML Type:  centroDiCostoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CentroDiCostoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML centroDiCostoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CentroDiCostoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CentroDiCostoType
{
    
    public CentroDiCostoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ID$0 = 
        new javax.xml.namespace.QName("", "id");
    private static final javax.xml.namespace.QName UFFICIO$2 = 
        new javax.xml.namespace.QName("", "ufficio");
    private static final javax.xml.namespace.QName CODICETOPONIMO$4 = 
        new javax.xml.namespace.QName("", "codiceToponimo");
    private static final javax.xml.namespace.QName INDIRIZZO$6 = 
        new javax.xml.namespace.QName("", "indirizzo");
    private static final javax.xml.namespace.QName CIVICO$8 = 
        new javax.xml.namespace.QName("", "civico");
    private static final javax.xml.namespace.QName CAP$10 = 
        new javax.xml.namespace.QName("", "cap");
    private static final javax.xml.namespace.QName COMUNE$12 = 
        new javax.xml.namespace.QName("", "comune");
    
    
    /**
     * Gets the "id" element
     */
    public java.lang.String getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" element
     */
    public org.apache.xmlbeans.XmlString xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ID$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "id" element
     */
    public boolean isSetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ID$0) != 0;
        }
    }
    
    /**
     * Sets the "id" element
     */
    public void setId(java.lang.String id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ID$0);
            }
            target.setStringValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" element
     */
    public void xsetId(org.apache.xmlbeans.XmlString id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ID$0);
            }
            target.set(id);
        }
    }
    
    /**
     * Unsets the "id" element
     */
    public void unsetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ID$0, 0);
        }
    }
    
    /**
     * Gets the "ufficio" element
     */
    public java.lang.String getUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UFFICIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ufficio" element
     */
    public org.apache.xmlbeans.XmlString xgetUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UFFICIO$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "ufficio" element
     */
    public boolean isSetUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(UFFICIO$2) != 0;
        }
    }
    
    /**
     * Sets the "ufficio" element
     */
    public void setUfficio(java.lang.String ufficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UFFICIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UFFICIO$2);
            }
            target.setStringValue(ufficio);
        }
    }
    
    /**
     * Sets (as xml) the "ufficio" element
     */
    public void xsetUfficio(org.apache.xmlbeans.XmlString ufficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UFFICIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(UFFICIO$2);
            }
            target.set(ufficio);
        }
    }
    
    /**
     * Unsets the "ufficio" element
     */
    public void unsetUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(UFFICIO$2, 0);
        }
    }
    
    /**
     * Gets the "codiceToponimo" element
     */
    public java.lang.String getCodiceToponimo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICETOPONIMO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codiceToponimo" element
     */
    public org.apache.xmlbeans.XmlString xgetCodiceToponimo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICETOPONIMO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "codiceToponimo" element
     */
    public boolean isSetCodiceToponimo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODICETOPONIMO$4) != 0;
        }
    }
    
    /**
     * Sets the "codiceToponimo" element
     */
    public void setCodiceToponimo(java.lang.String codiceToponimo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICETOPONIMO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICETOPONIMO$4);
            }
            target.setStringValue(codiceToponimo);
        }
    }
    
    /**
     * Sets (as xml) the "codiceToponimo" element
     */
    public void xsetCodiceToponimo(org.apache.xmlbeans.XmlString codiceToponimo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICETOPONIMO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICETOPONIMO$4);
            }
            target.set(codiceToponimo);
        }
    }
    
    /**
     * Unsets the "codiceToponimo" element
     */
    public void unsetCodiceToponimo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODICETOPONIMO$4, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$6, 0);
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
            return get_store().count_elements(INDIRIZZO$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZO$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INDIRIZZO$6);
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
            get_store().remove_element(INDIRIZZO$6, 0);
        }
    }
    
    /**
     * Gets the "civico" element
     */
    public java.lang.String getCivico()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICO$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "civico" element
     */
    public org.apache.xmlbeans.XmlString xgetCivico()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIVICO$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "civico" element
     */
    public boolean isSetCivico()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIVICO$8) != 0;
        }
    }
    
    /**
     * Sets the "civico" element
     */
    public void setCivico(java.lang.String civico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIVICO$8);
            }
            target.setStringValue(civico);
        }
    }
    
    /**
     * Sets (as xml) the "civico" element
     */
    public void xsetCivico(org.apache.xmlbeans.XmlString civico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIVICO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CIVICO$8);
            }
            target.set(civico);
        }
    }
    
    /**
     * Unsets the "civico" element
     */
    public void unsetCivico()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIVICO$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$10, 0);
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
            return get_store().count_elements(CAP$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAP$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CAP$10);
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
            get_store().remove_element(CAP$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNE$12, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNE$12, 0);
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
            return get_store().count_elements(COMUNE$12) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMUNE$12);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMUNE$12);
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
            get_store().remove_element(COMUNE$12, 0);
        }
    }
}
