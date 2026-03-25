/*
 * An XML document type.
 * Localname: SchedaGaraCig
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SchedaGaraCigDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one SchedaGaraCig(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class SchedaGaraCigDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SchedaGaraCigDocument
{
    
    public SchedaGaraCigDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SCHEDAGARACIG$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SchedaGaraCig");
    
    
    /**
     * Gets the "SchedaGaraCig" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaGaraCig getSchedaGaraCig()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaGaraCig target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaGaraCig)get_store().find_element_user(SCHEDAGARACIG$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "SchedaGaraCig" element
     */
    public void setSchedaGaraCig(it.avlp.simog.massload.xmlbeans.SchedaGaraCig schedaGaraCig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaGaraCig target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaGaraCig)get_store().find_element_user(SCHEDAGARACIG$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SchedaGaraCig)get_store().add_element_user(SCHEDAGARACIG$0);
            }
            target.set(schedaGaraCig);
        }
    }
    
    /**
     * Appends and returns a new empty "SchedaGaraCig" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaGaraCig addNewSchedaGaraCig()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaGaraCig target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaGaraCig)get_store().add_element_user(SCHEDAGARACIG$0);
            return target;
        }
    }
}
