/*
 * An XML document type.
 * Localname: DatiGara
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiGaraDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one DatiGara(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class DatiGaraDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiGaraDocument
{
    
    public DatiGaraDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIGARA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DatiGara");
    
    
    /**
     * Gets the "DatiGara" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraType getDatiGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().find_element_user(DATIGARA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DatiGara" element
     */
    public void setDatiGara(it.avlp.simog.massload.xmlbeans.DatiGaraType datiGara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().find_element_user(DATIGARA$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().add_element_user(DATIGARA$0);
            }
            target.set(datiGara);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiGara" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraType addNewDatiGara()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraType)get_store().add_element_user(DATIGARA$0);
            return target;
        }
    }
}
