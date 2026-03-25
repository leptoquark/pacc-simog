/*
 * XML Type:  ufficioType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.UfficioType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans.impl;
/**
 * An XML ufficioType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public class UfficioTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.ws.xmlbeans.UfficioType
{
    
    public UfficioTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DENOMINAZIONE$0 = 
        new javax.xml.namespace.QName("", "denominazione");
    private static final javax.xml.namespace.QName IDUFFICIO$2 = 
        new javax.xml.namespace.QName("", "id_ufficio");
    private static final javax.xml.namespace.QName PROFILO$4 = 
        new javax.xml.namespace.QName("", "profilo");
    
    
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
     * Gets the "id_ufficio" element
     */
    public java.lang.String getIdUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDUFFICIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id_ufficio" element
     */
    public org.apache.xmlbeans.XmlString xgetIdUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDUFFICIO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "id_ufficio" element
     */
    public void setIdUfficio(java.lang.String idUfficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDUFFICIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDUFFICIO$2);
            }
            target.setStringValue(idUfficio);
        }
    }
    
    /**
     * Sets (as xml) the "id_ufficio" element
     */
    public void xsetIdUfficio(org.apache.xmlbeans.XmlString idUfficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDUFFICIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDUFFICIO$2);
            }
            target.set(idUfficio);
        }
    }
    
    /**
     * Gets the "profilo" element
     */
    public java.lang.String getProfilo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "profilo" element
     */
    public it.avlp.simog.ws.xmlbeans.ProfiloType xgetProfilo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.ProfiloType target = null;
            target = (it.avlp.simog.ws.xmlbeans.ProfiloType)get_store().find_element_user(PROFILO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "profilo" element
     */
    public void setProfilo(java.lang.String profilo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROFILO$4);
            }
            target.setStringValue(profilo);
        }
    }
    
    /**
     * Sets (as xml) the "profilo" element
     */
    public void xsetProfilo(it.avlp.simog.ws.xmlbeans.ProfiloType profilo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.ProfiloType target = null;
            target = (it.avlp.simog.ws.xmlbeans.ProfiloType)get_store().find_element_user(PROFILO$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.ws.xmlbeans.ProfiloType)get_store().add_element_user(PROFILO$4);
            }
            target.set(profilo);
        }
    }
}
