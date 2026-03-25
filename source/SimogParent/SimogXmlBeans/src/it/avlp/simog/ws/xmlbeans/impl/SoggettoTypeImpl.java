/*
 * XML Type:  soggettoType
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.SoggettoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans.impl;
/**
 * An XML soggettoType(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SoggettoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.ws.xmlbeans.SoggettoType
{
    
    public SoggettoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COGNOME$0 = 
        new javax.xml.namespace.QName("", "cognome");
    private static final javax.xml.namespace.QName NOME$2 = 
        new javax.xml.namespace.QName("", "nome");
    private static final javax.xml.namespace.QName TEL$4 = 
        new javax.xml.namespace.QName("", "tel");
    private static final javax.xml.namespace.QName FAX$6 = 
        new javax.xml.namespace.QName("", "fax");
    private static final javax.xml.namespace.QName EMAIL$8 = 
        new javax.xml.namespace.QName("", "email");
    private static final javax.xml.namespace.QName INDIRIZZO$10 = 
        new javax.xml.namespace.QName("", "indirizzo");
    private static final javax.xml.namespace.QName CAP$12 = 
        new javax.xml.namespace.QName("", "cap");
    private static final javax.xml.namespace.QName COMUNEISTAT$14 = 
        new javax.xml.namespace.QName("", "comune_istat");
    private static final javax.xml.namespace.QName ADMINOR$16 = 
        new javax.xml.namespace.QName("", "admin_or");
    
    
    /**
     * Gets the "cognome" element
     */
    public java.lang.String getCognome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COGNOME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cognome" element
     */
    public org.apache.xmlbeans.XmlString xgetCognome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COGNOME$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "cognome" element
     */
    public boolean isSetCognome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COGNOME$0) != 0;
        }
    }
    
    /**
     * Sets the "cognome" element
     */
    public void setCognome(java.lang.String cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COGNOME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COGNOME$0);
            }
            target.setStringValue(cognome);
        }
    }
    
    /**
     * Sets (as xml) the "cognome" element
     */
    public void xsetCognome(org.apache.xmlbeans.XmlString cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COGNOME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COGNOME$0);
            }
            target.set(cognome);
        }
    }
    
    /**
     * Unsets the "cognome" element
     */
    public void unsetCognome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COGNOME$0, 0);
        }
    }
    
    /**
     * Gets the "nome" element
     */
    public java.lang.String getNome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOME$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "nome" element
     */
    public org.apache.xmlbeans.XmlString xgetNome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOME$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "nome" element
     */
    public boolean isSetNome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NOME$2) != 0;
        }
    }
    
    /**
     * Sets the "nome" element
     */
    public void setNome(java.lang.String nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NOME$2);
            }
            target.setStringValue(nome);
        }
    }
    
    /**
     * Sets (as xml) the "nome" element
     */
    public void xsetNome(org.apache.xmlbeans.XmlString nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NOME$2);
            }
            target.set(nome);
        }
    }
    
    /**
     * Unsets the "nome" element
     */
    public void unsetNome()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NOME$2, 0);
        }
    }
    
    /**
     * Gets the "tel" element
     */
    public java.lang.String getTel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEL$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "tel" element
     */
    public org.apache.xmlbeans.XmlString xgetTel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TEL$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "tel" element
     */
    public boolean isSetTel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TEL$4) != 0;
        }
    }
    
    /**
     * Sets the "tel" element
     */
    public void setTel(java.lang.String tel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TEL$4);
            }
            target.setStringValue(tel);
        }
    }
    
    /**
     * Sets (as xml) the "tel" element
     */
    public void xsetTel(org.apache.xmlbeans.XmlString tel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TEL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TEL$4);
            }
            target.set(tel);
        }
    }
    
    /**
     * Unsets the "tel" element
     */
    public void unsetTel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TEL$4, 0);
        }
    }
    
    /**
     * Gets the "fax" element
     */
    public java.lang.String getFax()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAX$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "fax" element
     */
    public org.apache.xmlbeans.XmlString xgetFax()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAX$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "fax" element
     */
    public boolean isSetFax()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FAX$6) != 0;
        }
    }
    
    /**
     * Sets the "fax" element
     */
    public void setFax(java.lang.String fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAX$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAX$6);
            }
            target.setStringValue(fax);
        }
    }
    
    /**
     * Sets (as xml) the "fax" element
     */
    public void xsetFax(org.apache.xmlbeans.XmlString fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAX$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAX$6);
            }
            target.set(fax);
        }
    }
    
    /**
     * Unsets the "fax" element
     */
    public void unsetFax()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FAX$6, 0);
        }
    }
    
    /**
     * Gets the "email" element
     */
    public java.lang.String getEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "email" element
     */
    public org.apache.xmlbeans.XmlString xgetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "email" element
     */
    public boolean isSetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAIL$8) != 0;
        }
    }
    
    /**
     * Sets the "email" element
     */
    public void setEmail(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$8);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "email" element
     */
    public void xsetEmail(org.apache.xmlbeans.XmlString email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAIL$8);
            }
            target.set(email);
        }
    }
    
    /**
     * Unsets the "email" element
     */
    public void unsetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAIL$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$10, 0);
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
            return get_store().count_elements(INDIRIZZO$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZO$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INDIRIZZO$10);
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
            get_store().remove_element(INDIRIZZO$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$12, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$12, 0);
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
            return get_store().count_elements(CAP$12) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAP$12);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CAP$12);
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
            get_store().remove_element(CAP$12, 0);
        }
    }
    
    /**
     * Gets the "comune_istat" element
     */
    public java.lang.String getComuneIstat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNEISTAT$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "comune_istat" element
     */
    public org.apache.xmlbeans.XmlString xgetComuneIstat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNEISTAT$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "comune_istat" element
     */
    public boolean isSetComuneIstat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMUNEISTAT$14) != 0;
        }
    }
    
    /**
     * Sets the "comune_istat" element
     */
    public void setComuneIstat(java.lang.String comuneIstat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNEISTAT$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMUNEISTAT$14);
            }
            target.setStringValue(comuneIstat);
        }
    }
    
    /**
     * Sets (as xml) the "comune_istat" element
     */
    public void xsetComuneIstat(org.apache.xmlbeans.XmlString comuneIstat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNEISTAT$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMUNEISTAT$14);
            }
            target.set(comuneIstat);
        }
    }
    
    /**
     * Unsets the "comune_istat" element
     */
    public void unsetComuneIstat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMUNEISTAT$14, 0);
        }
    }
    
    /**
     * Gets the "admin_or" element
     */
    public java.lang.String getAdminOr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADMINOR$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "admin_or" element
     */
    public org.apache.xmlbeans.XmlString xgetAdminOr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADMINOR$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "admin_or" element
     */
    public boolean isSetAdminOr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADMINOR$16) != 0;
        }
    }
    
    /**
     * Sets the "admin_or" element
     */
    public void setAdminOr(java.lang.String adminOr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADMINOR$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADMINOR$16);
            }
            target.setStringValue(adminOr);
        }
    }
    
    /**
     * Sets (as xml) the "admin_or" element
     */
    public void xsetAdminOr(org.apache.xmlbeans.XmlString adminOr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADMINOR$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADMINOR$16);
            }
            target.set(adminOr);
        }
    }
    
    /**
     * Unsets the "admin_or" element
     */
    public void unsetAdminOr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADMINOR$16, 0);
        }
    }
}
