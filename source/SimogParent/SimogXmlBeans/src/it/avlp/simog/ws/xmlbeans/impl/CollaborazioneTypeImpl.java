/*
 * XML Type:  collaborazioneType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CollaborazioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans.impl;
/**
 * An XML collaborazioneType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CollaborazioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.ws.xmlbeans.CollaborazioneType
{
    
    public CollaborazioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AZIENDA$0 = 
        new javax.xml.namespace.QName("", "azienda");
    private static final javax.xml.namespace.QName UFFICIO$2 = 
        new javax.xml.namespace.QName("", "ufficio");
    private static final javax.xml.namespace.QName INDEX$4 = 
        new javax.xml.namespace.QName("xmlbeans.ws.simog.avlp.it", "index");
    
    
    /**
     * Gets the "azienda" element
     */
    public it.avlp.simog.ws.xmlbeans.AziendaType getAzienda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.AziendaType target = null;
            target = (it.avlp.simog.ws.xmlbeans.AziendaType)get_store().find_element_user(AZIENDA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "azienda" element
     */
    public void setAzienda(it.avlp.simog.ws.xmlbeans.AziendaType azienda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.AziendaType target = null;
            target = (it.avlp.simog.ws.xmlbeans.AziendaType)get_store().find_element_user(AZIENDA$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.ws.xmlbeans.AziendaType)get_store().add_element_user(AZIENDA$0);
            }
            target.set(azienda);
        }
    }
    
    /**
     * Appends and returns a new empty "azienda" element
     */
    public it.avlp.simog.ws.xmlbeans.AziendaType addNewAzienda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.AziendaType target = null;
            target = (it.avlp.simog.ws.xmlbeans.AziendaType)get_store().add_element_user(AZIENDA$0);
            return target;
        }
    }
    
    /**
     * Gets the "ufficio" element
     */
    public it.avlp.simog.ws.xmlbeans.UfficioType getUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.UfficioType target = null;
            target = (it.avlp.simog.ws.xmlbeans.UfficioType)get_store().find_element_user(UFFICIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ufficio" element
     */
    public void setUfficio(it.avlp.simog.ws.xmlbeans.UfficioType ufficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.UfficioType target = null;
            target = (it.avlp.simog.ws.xmlbeans.UfficioType)get_store().find_element_user(UFFICIO$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.ws.xmlbeans.UfficioType)get_store().add_element_user(UFFICIO$2);
            }
            target.set(ufficio);
        }
    }
    
    /**
     * Appends and returns a new empty "ufficio" element
     */
    public it.avlp.simog.ws.xmlbeans.UfficioType addNewUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.UfficioType target = null;
            target = (it.avlp.simog.ws.xmlbeans.UfficioType)get_store().add_element_user(UFFICIO$2);
            return target;
        }
    }
    
    /**
     * Gets the "index" attribute
     */
    public java.lang.String getIndex()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INDEX$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "index" attribute
     */
    public org.apache.xmlbeans.XmlString xgetIndex()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INDEX$4);
            return target;
        }
    }
    
    /**
     * Sets the "index" attribute
     */
    public void setIndex(java.lang.String index)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INDEX$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INDEX$4);
            }
            target.setStringValue(index);
        }
    }
    
    /**
     * Sets (as xml) the "index" attribute
     */
    public void xsetIndex(org.apache.xmlbeans.XmlString index)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INDEX$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INDEX$4);
            }
            target.set(index);
        }
    }
}
