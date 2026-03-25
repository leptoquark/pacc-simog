/*
 * An XML document type.
 * Localname: DatiGaraLotto
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one DatiGaraLotto(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class DatiGaraLottoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument
{
    
    public DatiGaraLottoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIGARALOTTO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DatiGaraLotto");
    
    
    /**
     * Gets the "DatiGaraLotto" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto getDatiGaraLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto)get_store().find_element_user(DATIGARALOTTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DatiGaraLotto" element
     */
    public void setDatiGaraLotto(it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto datiGaraLotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto)get_store().find_element_user(DATIGARALOTTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto)get_store().add_element_user(DATIGARALOTTO$0);
            }
            target.set(datiGaraLotto);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiGaraLotto" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto addNewDatiGaraLotto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto)get_store().add_element_user(DATIGARALOTTO$0);
            return target;
        }
    }
    /**
     * An XML DatiGaraLotto(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class DatiGaraLottoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiGaraLottoDocument.DatiGaraLotto
    {
        
        public DatiGaraLottoImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName DATIGARA$0 = 
            new javax.xml.namespace.QName("", "DatiGara");
        
        
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
}
