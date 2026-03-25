/*
 * An XML document type.
 * Localname: check_login
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CheckLoginDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans.impl;
/**
 * A document containing one check_login(@xmlbeans.ws.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class CheckLoginDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.ws.xmlbeans.CheckLoginDocument
{
    
    public CheckLoginDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CHECKLOGIN$0 = 
        new javax.xml.namespace.QName("xmlbeans.ws.simog.avlp.it", "check_login");
    
    
    /**
     * Gets the "check_login" element
     */
    public it.avlp.simog.ws.xmlbeans.CheckLoginType getCheckLogin()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CheckLoginType target = null;
            target = (it.avlp.simog.ws.xmlbeans.CheckLoginType)get_store().find_element_user(CHECKLOGIN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "check_login" element
     */
    public void setCheckLogin(it.avlp.simog.ws.xmlbeans.CheckLoginType checkLogin)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CheckLoginType target = null;
            target = (it.avlp.simog.ws.xmlbeans.CheckLoginType)get_store().find_element_user(CHECKLOGIN$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.ws.xmlbeans.CheckLoginType)get_store().add_element_user(CHECKLOGIN$0);
            }
            target.set(checkLogin);
        }
    }
    
    /**
     * Appends and returns a new empty "check_login" element
     */
    public it.avlp.simog.ws.xmlbeans.CheckLoginType addNewCheckLogin()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CheckLoginType target = null;
            target = (it.avlp.simog.ws.xmlbeans.CheckLoginType)get_store().add_element_user(CHECKLOGIN$0);
            return target;
        }
    }
}
