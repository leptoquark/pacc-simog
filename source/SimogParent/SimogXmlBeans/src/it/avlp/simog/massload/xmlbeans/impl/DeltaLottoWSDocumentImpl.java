/*
 * An XML document type.
 * Localname: DeltaLottoWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one DeltaLottoWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class DeltaLottoWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument
{
    
    public DeltaLottoWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DELTALOTTOWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DeltaLottoWS");
    
    
    /**
     * Gets the "DeltaLottoWS" element
     */
    public it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS getDeltaLottoWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS)get_store().find_element_user(DELTALOTTOWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DeltaLottoWS" element
     */
    public void setDeltaLottoWS(it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS deltaLottoWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS)get_store().find_element_user(DELTALOTTOWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS)get_store().add_element_user(DELTALOTTOWS$0);
            }
            target.set(deltaLottoWS);
        }
    }
    
    /**
     * Appends and returns a new empty "DeltaLottoWS" element
     */
    public it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS addNewDeltaLottoWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS)get_store().add_element_user(DELTALOTTOWS$0);
            return target;
        }
    }
    /**
     * An XML DeltaLottoWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class DeltaLottoWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument.DeltaLottoWS
    {
        
        public DeltaLottoWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName DELTALOTTO$0 = 
            new javax.xml.namespace.QName("", "DeltaLotto");
        
        
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
}
