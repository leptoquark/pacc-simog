/*
 * An XML document type.
 * Localname: PubblicazioneWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one PubblicazioneWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class PubblicazioneWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument
{
    
    public PubblicazioneWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PUBBLICAZIONEWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PubblicazioneWS");
    
    
    /**
     * Gets the "PubblicazioneWS" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS getPubblicazioneWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS)get_store().find_element_user(PUBBLICAZIONEWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PubblicazioneWS" element
     */
    public void setPubblicazioneWS(it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS pubblicazioneWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS)get_store().find_element_user(PUBBLICAZIONEWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS)get_store().add_element_user(PUBBLICAZIONEWS$0);
            }
            target.set(pubblicazioneWS);
        }
    }
    
    /**
     * Appends and returns a new empty "PubblicazioneWS" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS addNewPubblicazioneWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS)get_store().add_element_user(PUBBLICAZIONEWS$0);
            return target;
        }
    }
    /**
     * An XML PubblicazioneWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class PubblicazioneWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument.PubblicazioneWS
    {
        
        public PubblicazioneWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName PUBBLICAZIONE$0 = 
            new javax.xml.namespace.QName("", "Pubblicazione");
        
        
        /**
         * Gets the "Pubblicazione" element
         */
        public it.avlp.simog.massload.xmlbeans.PubblicazioneType getPubblicazione()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONE$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "Pubblicazione" element
         */
        public void setPubblicazione(it.avlp.simog.massload.xmlbeans.PubblicazioneType pubblicazione)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONE$0, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONE$0);
                }
                target.set(pubblicazione);
            }
        }
        
        /**
         * Appends and returns a new empty "Pubblicazione" element
         */
        public it.avlp.simog.massload.xmlbeans.PubblicazioneType addNewPubblicazione()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONE$0);
                return target;
            }
        }
    }
}
