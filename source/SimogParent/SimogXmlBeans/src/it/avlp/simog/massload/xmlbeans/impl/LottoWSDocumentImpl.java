/*
 * An XML document type.
 * Localname: LottoWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.LottoWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one LottoWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class LottoWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.LottoWSDocument
{
    
    public LottoWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LOTTOWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LottoWS");
    
    
    /**
     * Gets the "LottoWS" element
     */
    public it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS getLottoWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS)get_store().find_element_user(LOTTOWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "LottoWS" element
     */
    public void setLottoWS(it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS lottoWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS)get_store().find_element_user(LOTTOWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS)get_store().add_element_user(LOTTOWS$0);
            }
            target.set(lottoWS);
        }
    }
    
    /**
     * Appends and returns a new empty "LottoWS" element
     */
    public it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS addNewLottoWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS)get_store().add_element_user(LOTTOWS$0);
            return target;
        }
    }
    /**
     * An XML LottoWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class LottoWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS
    {
        
        public LottoWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName LOTTO$0 = 
            new javax.xml.namespace.QName("", "Lotto");
        
        
        /**
         * Gets the "Lotto" element
         */
        public it.avlp.simog.massload.xmlbeans.LottoType getLotto()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.LottoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().find_element_user(LOTTO$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "Lotto" element
         */
        public void setLotto(it.avlp.simog.massload.xmlbeans.LottoType lotto)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.LottoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().find_element_user(LOTTO$0, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().add_element_user(LOTTO$0);
                }
                target.set(lotto);
            }
        }
        
        /**
         * Appends and returns a new empty "Lotto" element
         */
        public it.avlp.simog.massload.xmlbeans.LottoType addNewLotto()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.LottoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.LottoType)get_store().add_element_user(LOTTO$0);
                return target;
            }
        }
    }
}
