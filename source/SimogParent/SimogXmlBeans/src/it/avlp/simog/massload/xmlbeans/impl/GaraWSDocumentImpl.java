/*
 * An XML document type.
 * Localname: GaraWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.GaraWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one GaraWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class GaraWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.GaraWSDocument
{
    
    public GaraWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GARAWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "GaraWS");
    
    
    /**
     * Gets the "GaraWS" element
     */
    public it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS getGaraWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS)get_store().find_element_user(GARAWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "GaraWS" element
     */
    public void setGaraWS(it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS garaWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS)get_store().find_element_user(GARAWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS)get_store().add_element_user(GARAWS$0);
            }
            target.set(garaWS);
        }
    }
    
    /**
     * Appends and returns a new empty "GaraWS" element
     */
    public it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS addNewGaraWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS)get_store().add_element_user(GARAWS$0);
            return target;
        }
    }
    /**
     * An XML GaraWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class GaraWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS
    {
        
        public GaraWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName DATIGARA$0 = 
            new javax.xml.namespace.QName("", "DatiGara");
        
        
        /**
         * Gets the "DatiGara" element
         */
        public it.avlp.simog.massload.xmlbeans.GaraType getDatiGara()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.GaraType target = null;
                target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().find_element_user(DATIGARA$0, 0);
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
        public void setDatiGara(it.avlp.simog.massload.xmlbeans.GaraType datiGara)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.GaraType target = null;
                target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().find_element_user(DATIGARA$0, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().add_element_user(DATIGARA$0);
                }
                target.set(datiGara);
            }
        }
        
        /**
         * Appends and returns a new empty "DatiGara" element
         */
        public it.avlp.simog.massload.xmlbeans.GaraType addNewDatiGara()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.GaraType target = null;
                target = (it.avlp.simog.massload.xmlbeans.GaraType)get_store().add_element_user(DATIGARA$0);
                return target;
            }
        }
    }
}
