/*
 * XML Type:  ReqDocType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ReqDocType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ReqDocType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ReqDocTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ReqDocType
{
    
    public ReqDocTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICETIPODOC$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "codice_tipo_doc");
    private static final javax.xml.namespace.QName DESCRIZIONEDOCUMENTO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "descrizione_documento");
    private static final javax.xml.namespace.QName EMETTITORE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "emettitore");
    private static final javax.xml.namespace.QName FAX$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "fax");
    private static final javax.xml.namespace.QName TELEFONO$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "telefono");
    private static final javax.xml.namespace.QName MAIL$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "mail");
    private static final javax.xml.namespace.QName MAILPEC$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "mail_pec");
    
    
    /**
     * Gets the "codice_tipo_doc" attribute
     */
    public java.lang.String getCodiceTipoDoc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICETIPODOC$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codice_tipo_doc" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodTipoDocType xgetCodiceTipoDoc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodTipoDocType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodTipoDocType)get_store().find_attribute_user(CODICETIPODOC$0);
            return target;
        }
    }
    
    /**
     * Sets the "codice_tipo_doc" attribute
     */
    public void setCodiceTipoDoc(java.lang.String codiceTipoDoc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODICETIPODOC$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODICETIPODOC$0);
            }
            target.setStringValue(codiceTipoDoc);
        }
    }
    
    /**
     * Sets (as xml) the "codice_tipo_doc" attribute
     */
    public void xsetCodiceTipoDoc(it.avlp.simog.massload.xmlbeans.CodTipoDocType codiceTipoDoc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodTipoDocType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodTipoDocType)get_store().find_attribute_user(CODICETIPODOC$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodTipoDocType)get_store().add_attribute_user(CODICETIPODOC$0);
            }
            target.set(codiceTipoDoc);
        }
    }
    
    /**
     * Gets the "descrizione_documento" attribute
     */
    public java.lang.String getDescrizioneDocumento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONEDOCUMENTO$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "descrizione_documento" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento xgetDescrizioneDocumento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento)get_store().find_attribute_user(DESCRIZIONEDOCUMENTO$2);
            return target;
        }
    }
    
    /**
     * Sets the "descrizione_documento" attribute
     */
    public void setDescrizioneDocumento(java.lang.String descrizioneDocumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DESCRIZIONEDOCUMENTO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DESCRIZIONEDOCUMENTO$2);
            }
            target.setStringValue(descrizioneDocumento);
        }
    }
    
    /**
     * Sets (as xml) the "descrizione_documento" attribute
     */
    public void xsetDescrizioneDocumento(it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento descrizioneDocumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento)get_store().find_attribute_user(DESCRIZIONEDOCUMENTO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento)get_store().add_attribute_user(DESCRIZIONEDOCUMENTO$2);
            }
            target.set(descrizioneDocumento);
        }
    }
    
    /**
     * Gets the "emettitore" attribute
     */
    public java.lang.String getEmettitore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMETTITORE$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "emettitore" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore xgetEmettitore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore)get_store().find_attribute_user(EMETTITORE$4);
            return target;
        }
    }
    
    /**
     * Sets the "emettitore" attribute
     */
    public void setEmettitore(java.lang.String emettitore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMETTITORE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EMETTITORE$4);
            }
            target.setStringValue(emettitore);
        }
    }
    
    /**
     * Sets (as xml) the "emettitore" attribute
     */
    public void xsetEmettitore(it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore emettitore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore)get_store().find_attribute_user(EMETTITORE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore)get_store().add_attribute_user(EMETTITORE$4);
            }
            target.set(emettitore);
        }
    }
    
    /**
     * Gets the "fax" attribute
     */
    public java.lang.String getFax()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "fax" attribute
     */
    public it.avlp.simog.massload.xmlbeans.NumTelType xgetFax()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.NumTelType target = null;
            target = (it.avlp.simog.massload.xmlbeans.NumTelType)get_store().find_attribute_user(FAX$6);
            return target;
        }
    }
    
    /**
     * Sets the "fax" attribute
     */
    public void setFax(java.lang.String fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FAX$6);
            }
            target.setStringValue(fax);
        }
    }
    
    /**
     * Sets (as xml) the "fax" attribute
     */
    public void xsetFax(it.avlp.simog.massload.xmlbeans.NumTelType fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.NumTelType target = null;
            target = (it.avlp.simog.massload.xmlbeans.NumTelType)get_store().find_attribute_user(FAX$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.NumTelType)get_store().add_attribute_user(FAX$6);
            }
            target.set(fax);
        }
    }
    
    /**
     * Gets the "telefono" attribute
     */
    public java.lang.String getTelefono()
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
     * Gets (as xml) the "telefono" attribute
     */
    public it.avlp.simog.massload.xmlbeans.NumTelType xgetTelefono()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.NumTelType target = null;
            target = (it.avlp.simog.massload.xmlbeans.NumTelType)get_store().find_attribute_user(TELEFONO$8);
            return target;
        }
    }
    
    /**
     * Sets the "telefono" attribute
     */
    public void setTelefono(java.lang.String telefono)
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
     * Sets (as xml) the "telefono" attribute
     */
    public void xsetTelefono(it.avlp.simog.massload.xmlbeans.NumTelType telefono)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.NumTelType target = null;
            target = (it.avlp.simog.massload.xmlbeans.NumTelType)get_store().find_attribute_user(TELEFONO$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.NumTelType)get_store().add_attribute_user(TELEFONO$8);
            }
            target.set(telefono);
        }
    }
    
    /**
     * Gets the "mail" attribute
     */
    public java.lang.String getMail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAIL$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "mail" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType.Mail xgetMail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.Mail target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.Mail)get_store().find_attribute_user(MAIL$10);
            return target;
        }
    }
    
    /**
     * Sets the "mail" attribute
     */
    public void setMail(java.lang.String mail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAIL$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MAIL$10);
            }
            target.setStringValue(mail);
        }
    }
    
    /**
     * Sets (as xml) the "mail" attribute
     */
    public void xsetMail(it.avlp.simog.massload.xmlbeans.ReqDocType.Mail mail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.Mail target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.Mail)get_store().find_attribute_user(MAIL$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ReqDocType.Mail)get_store().add_attribute_user(MAIL$10);
            }
            target.set(mail);
        }
    }
    
    /**
     * Gets the "mail_pec" attribute
     */
    public java.lang.String getMailPec()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAILPEC$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "mail_pec" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec xgetMailPec()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec)get_store().find_attribute_user(MAILPEC$12);
            return target;
        }
    }
    
    /**
     * Sets the "mail_pec" attribute
     */
    public void setMailPec(java.lang.String mailPec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MAILPEC$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MAILPEC$12);
            }
            target.setStringValue(mailPec);
        }
    }
    
    /**
     * Sets (as xml) the "mail_pec" attribute
     */
    public void xsetMailPec(it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec mailPec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec target = null;
            target = (it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec)get_store().find_attribute_user(MAILPEC$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec)get_store().add_attribute_user(MAILPEC$12);
            }
            target.set(mailPec);
        }
    }
    /**
     * An XML descrizione_documento(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$DescrizioneDocumento.
     */
    public static class DescrizioneDocumentoImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ReqDocType.DescrizioneDocumento
    {
        
        public DescrizioneDocumentoImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected DescrizioneDocumentoImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML emettitore(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$Emettitore.
     */
    public static class EmettitoreImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ReqDocType.Emettitore
    {
        
        public EmettitoreImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected EmettitoreImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML mail(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$Mail.
     */
    public static class MailImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ReqDocType.Mail
    {
        
        public MailImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MailImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML mail_pec(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ReqDocType$MailPec.
     */
    public static class MailPecImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ReqDocType.MailPec
    {
        
        public MailPecImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected MailPecImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
