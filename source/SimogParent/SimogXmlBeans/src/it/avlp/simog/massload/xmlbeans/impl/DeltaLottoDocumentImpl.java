/*
 * An XML document type.
 * Localname: DeltaLotto
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DeltaLottoDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one DeltaLotto(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class DeltaLottoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DeltaLottoDocument
{
    
    public DeltaLottoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DELTALOTTO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DeltaLotto");
    
    
    /**
     * Gets the "DeltaLotto" element
     */
    public it.avlp.simog.massload.xmlbeans.DeltaLottoTED getDeltaLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoTED target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoTED)get_store().find_element_user(DELTALOTTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DeltaLotto" element
     */
    public void setDeltaLotto(it.avlp.simog.massload.xmlbeans.DeltaLottoTED deltaLotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoTED target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoTED)get_store().find_element_user(DELTALOTTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaLottoTED)get_store().add_element_user(DELTALOTTO$0);
            }
            target.set(deltaLotto);
        }
    }
    
    /**
     * Appends and returns a new empty "DeltaLotto" element
     */
    public it.avlp.simog.massload.xmlbeans.DeltaLottoTED addNewDeltaLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoTED target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoTED)get_store().add_element_user(DELTALOTTO$0);
            return target;
        }
    }
}
