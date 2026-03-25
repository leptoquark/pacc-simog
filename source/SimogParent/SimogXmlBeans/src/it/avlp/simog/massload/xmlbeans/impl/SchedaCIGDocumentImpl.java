/*
 * An XML document type.
 * Localname: SchedaCIG
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SchedaCIGDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one SchedaCIG(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class SchedaCIGDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SchedaCIGDocument
{
    
    public SchedaCIGDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SCHEDACIG$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SchedaCIG");
    
    
    /**
     * Gets the "SchedaCIG" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType getSchedaCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType)get_store().find_element_user(SCHEDACIG$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "SchedaCIG" element
     */
    public void setSchedaCIG(it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType schedaCIG)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType)get_store().find_element_user(SCHEDACIG$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType)get_store().add_element_user(SCHEDACIG$0);
            }
            target.set(schedaCIG);
        }
    }
    
    /**
     * Appends and returns a new empty "SchedaCIG" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType addNewSchedaCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiConsultaGaraType)get_store().add_element_user(SCHEDACIG$0);
            return target;
        }
    }
}
