/*
 * XML Type:  checkLoginType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CheckLoginType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans.impl;
/**
 * An XML checkLoginType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CheckLoginTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.ws.xmlbeans.CheckLoginType
{
    
    public CheckLoginTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SOGGETTO$0 = 
        new javax.xml.namespace.QName("", "soggetto");
    private static final javax.xml.namespace.QName COLLABORAZIONI$2 = 
        new javax.xml.namespace.QName("", "collaborazioni");
    private static final javax.xml.namespace.QName STATO$4 = 
        new javax.xml.namespace.QName("", "stato");
    private static final javax.xml.namespace.QName MESSAGGIO$6 = 
        new javax.xml.namespace.QName("", "messaggio");
    
    
    /**
     * Gets the "soggetto" element
     */
    public it.avlp.simog.ws.xmlbeans.SoggettoType getSoggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.SoggettoType target = null;
            target = (it.avlp.simog.ws.xmlbeans.SoggettoType)get_store().find_element_user(SOGGETTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "soggetto" element
     */
    public boolean isSetSoggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SOGGETTO$0) != 0;
        }
    }
    
    /**
     * Sets the "soggetto" element
     */
    public void setSoggetto(it.avlp.simog.ws.xmlbeans.SoggettoType soggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.SoggettoType target = null;
            target = (it.avlp.simog.ws.xmlbeans.SoggettoType)get_store().find_element_user(SOGGETTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.ws.xmlbeans.SoggettoType)get_store().add_element_user(SOGGETTO$0);
            }
            target.set(soggetto);
        }
    }
    
    /**
     * Appends and returns a new empty "soggetto" element
     */
    public it.avlp.simog.ws.xmlbeans.SoggettoType addNewSoggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.SoggettoType target = null;
            target = (it.avlp.simog.ws.xmlbeans.SoggettoType)get_store().add_element_user(SOGGETTO$0);
            return target;
        }
    }
    
    /**
     * Unsets the "soggetto" element
     */
    public void unsetSoggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SOGGETTO$0, 0);
        }
    }
    
    /**
     * Gets the "collaborazioni" element
     */
    public it.avlp.simog.ws.xmlbeans.CollaborazioneList getCollaborazioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CollaborazioneList target = null;
            target = (it.avlp.simog.ws.xmlbeans.CollaborazioneList)get_store().find_element_user(COLLABORAZIONI$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "collaborazioni" element
     */
    public boolean isSetCollaborazioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COLLABORAZIONI$2) != 0;
        }
    }
    
    /**
     * Sets the "collaborazioni" element
     */
    public void setCollaborazioni(it.avlp.simog.ws.xmlbeans.CollaborazioneList collaborazioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CollaborazioneList target = null;
            target = (it.avlp.simog.ws.xmlbeans.CollaborazioneList)get_store().find_element_user(COLLABORAZIONI$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.ws.xmlbeans.CollaborazioneList)get_store().add_element_user(COLLABORAZIONI$2);
            }
            target.set(collaborazioni);
        }
    }
    
    /**
     * Appends and returns a new empty "collaborazioni" element
     */
    public it.avlp.simog.ws.xmlbeans.CollaborazioneList addNewCollaborazioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CollaborazioneList target = null;
            target = (it.avlp.simog.ws.xmlbeans.CollaborazioneList)get_store().add_element_user(COLLABORAZIONI$2);
            return target;
        }
    }
    
    /**
     * Unsets the "collaborazioni" element
     */
    public void unsetCollaborazioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COLLABORAZIONI$2, 0);
        }
    }
    
    /**
     * Gets the "stato" element
     */
    public java.lang.String getStato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "stato" element
     */
    public org.apache.xmlbeans.XmlString xgetStato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "stato" element
     */
    public void setStato(java.lang.String stato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATO$4);
            }
            target.setStringValue(stato);
        }
    }
    
    /**
     * Sets (as xml) the "stato" element
     */
    public void xsetStato(org.apache.xmlbeans.XmlString stato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATO$4);
            }
            target.set(stato);
        }
    }
    
    /**
     * Gets the "messaggio" element
     */
    public java.lang.String getMessaggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGGIO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "messaggio" element
     */
    public org.apache.xmlbeans.XmlString xgetMessaggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGGIO$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "messaggio" element
     */
    public boolean isSetMessaggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MESSAGGIO$6) != 0;
        }
    }
    
    /**
     * Sets the "messaggio" element
     */
    public void setMessaggio(java.lang.String messaggio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGGIO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGGIO$6);
            }
            target.setStringValue(messaggio);
        }
    }
    
    /**
     * Sets (as xml) the "messaggio" element
     */
    public void xsetMessaggio(org.apache.xmlbeans.XmlString messaggio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGGIO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGGIO$6);
            }
            target.set(messaggio);
        }
    }
    
    /**
     * Unsets the "messaggio" element
     */
    public void unsetMessaggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MESSAGGIO$6, 0);
        }
    }
}
