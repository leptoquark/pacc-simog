/*
 * An XML document type.
 * Localname: DeltaGaraWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one DeltaGaraWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class DeltaGaraWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument
{
    
    public DeltaGaraWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DELTAGARAWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DeltaGaraWS");
    
    
    /**
     * Gets the "DeltaGaraWS" element
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS getDeltaGaraWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS)get_store().find_element_user(DELTAGARAWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DeltaGaraWS" element
     */
    public void setDeltaGaraWS(it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS deltaGaraWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS)get_store().find_element_user(DELTAGARAWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS)get_store().add_element_user(DELTAGARAWS$0);
            }
            target.set(deltaGaraWS);
        }
    }
    
    /**
     * Appends and returns a new empty "DeltaGaraWS" element
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS addNewDeltaGaraWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS)get_store().add_element_user(DELTAGARAWS$0);
            return target;
        }
    }
    /**
     * An XML DeltaGaraWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class DeltaGaraWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.DeltaGaraWS
    {
        
        public DeltaGaraWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName DELTAGARA$0 = 
            new javax.xml.namespace.QName("", "DeltaGara");
        
        
        /**
         * Gets the "DeltaGara" element
         */
        public it.avlp.simog.massload.xmlbeans.DeltaGaraTED getDeltaGara()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.DeltaGaraTED target = null;
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED)get_store().find_element_user(DELTAGARA$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "DeltaGara" element
         */
        public void setDeltaGara(it.avlp.simog.massload.xmlbeans.DeltaGaraTED deltaGara)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.DeltaGaraTED target = null;
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED)get_store().find_element_user(DELTAGARA$0, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED)get_store().add_element_user(DELTAGARA$0);
                }
                target.set(deltaGara);
            }
        }
        
        /**
         * Appends and returns a new empty "DeltaGara" element
         */
        public it.avlp.simog.massload.xmlbeans.DeltaGaraTED addNewDeltaGara()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.DeltaGaraTED target = null;
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED)get_store().add_element_user(DELTAGARA$0);
                return target;
            }
        }
    }
}
