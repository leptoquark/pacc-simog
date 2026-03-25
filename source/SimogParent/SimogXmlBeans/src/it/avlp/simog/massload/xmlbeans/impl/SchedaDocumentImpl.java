/*
 * An XML document type.
 * Localname: Scheda
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SchedaDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one Scheda(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class SchedaDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SchedaDocument
{
    
    public SchedaDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SCHEDA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "Scheda");
    
    
    /**
     * Gets the "Scheda" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaType getScheda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().find_element_user(SCHEDA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Scheda" element
     */
    public void setScheda(it.avlp.simog.massload.xmlbeans.SchedaType scheda)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().find_element_user(SCHEDA$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().add_element_user(SCHEDA$0);
            }
            target.set(scheda);
        }
    }
    
    /**
     * Appends and returns a new empty "Scheda" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaType addNewScheda()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaType)get_store().add_element_user(SCHEDA$0);
            return target;
        }
    }
}
