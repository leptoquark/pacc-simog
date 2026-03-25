/*
 * XML Type:  listaCentroCostoRequestType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML listaCentroCostoRequestType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ListaCentroCostoRequestTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType
{
    
    public ListaCentroCostoRequestTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LOGIN$0 = 
        new javax.xml.namespace.QName("", "Login");
    private static final javax.xml.namespace.QName CODICEFISCALESTAZIONEAPPALTANTE$2 = 
        new javax.xml.namespace.QName("", "codiceFiscaleStazioneAppaltante");
    private static final javax.xml.namespace.QName EXTRA$4 = 
        new javax.xml.namespace.QName("", "extra");
    
    
    /**
     * Gets the "Login" element
     */
    public it.avlp.simog.massload.xmlbeans.LoginWSType getLogin()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LoginWSType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LoginWSType)get_store().find_element_user(LOGIN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Login" element
     */
    public void setLogin(it.avlp.simog.massload.xmlbeans.LoginWSType login)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LoginWSType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LoginWSType)get_store().find_element_user(LOGIN$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LoginWSType)get_store().add_element_user(LOGIN$0);
            }
            target.set(login);
        }
    }
    
    /**
     * Appends and returns a new empty "Login" element
     */
    public it.avlp.simog.massload.xmlbeans.LoginWSType addNewLogin()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LoginWSType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LoginWSType)get_store().add_element_user(LOGIN$0);
            return target;
        }
    }
    
    /**
     * Gets the "codiceFiscaleStazioneAppaltante" element
     */
    public java.lang.String getCodiceFiscaleStazioneAppaltante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALESTAZIONEAPPALTANTE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codiceFiscaleStazioneAppaltante" element
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCodiceFiscaleStazioneAppaltante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_element_user(CODICEFISCALESTAZIONEAPPALTANTE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codiceFiscaleStazioneAppaltante" element
     */
    public void setCodiceFiscaleStazioneAppaltante(java.lang.String codiceFiscaleStazioneAppaltante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALESTAZIONEAPPALTANTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEFISCALESTAZIONEAPPALTANTE$2);
            }
            target.setStringValue(codiceFiscaleStazioneAppaltante);
        }
    }
    
    /**
     * Sets (as xml) the "codiceFiscaleStazioneAppaltante" element
     */
    public void xsetCodiceFiscaleStazioneAppaltante(it.avlp.simog.massload.xmlbeans.CodFiscType codiceFiscaleStazioneAppaltante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_element_user(CODICEFISCALESTAZIONEAPPALTANTE$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_element_user(CODICEFISCALESTAZIONEAPPALTANTE$2);
            }
            target.set(codiceFiscaleStazioneAppaltante);
        }
    }
    
    /**
     * Gets the "extra" element
     */
    public java.lang.String getExtra()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTRA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "extra" element
     */
    public org.apache.xmlbeans.XmlString xgetExtra()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EXTRA$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "extra" element
     */
    public boolean isSetExtra()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EXTRA$4) != 0;
        }
    }
    
    /**
     * Sets the "extra" element
     */
    public void setExtra(java.lang.String extra)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTRA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTRA$4);
            }
            target.setStringValue(extra);
        }
    }
    
    /**
     * Sets (as xml) the "extra" element
     */
    public void xsetExtra(org.apache.xmlbeans.XmlString extra)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EXTRA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EXTRA$4);
            }
            target.set(extra);
        }
    }
    
    /**
     * Unsets the "extra" element
     */
    public void unsetExtra()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EXTRA$4, 0);
        }
    }
}
