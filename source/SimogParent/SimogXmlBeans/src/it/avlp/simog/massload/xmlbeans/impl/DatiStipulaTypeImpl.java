/*
 * XML Type:  DatiStipulaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DatiStipulaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DatiStipulaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DatiStipulaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DatiStipulaType
{
    
    public DatiStipulaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PUBBLICAZIONEESITO$0 = 
        new javax.xml.namespace.QName("", "PubblicazioneEsito");
    private static final javax.xml.namespace.QName STIPULA$2 = 
        new javax.xml.namespace.QName("", "Stipula");
    
    
    /**
     * Gets the "PubblicazioneEsito" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneType getPubblicazioneEsito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONEESITO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PubblicazioneEsito" element
     */
    public void setPubblicazioneEsito(it.avlp.simog.massload.xmlbeans.PubblicazioneType pubblicazioneEsito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().find_element_user(PUBBLICAZIONEESITO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONEESITO$0);
            }
            target.set(pubblicazioneEsito);
        }
    }
    
    /**
     * Appends and returns a new empty "PubblicazioneEsito" element
     */
    public it.avlp.simog.massload.xmlbeans.PubblicazioneType addNewPubblicazioneEsito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PubblicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PubblicazioneType)get_store().add_element_user(PUBBLICAZIONEESITO$0);
            return target;
        }
    }
    
    /**
     * Gets the "Stipula" element
     */
    public it.avlp.simog.massload.xmlbeans.StipulaType getStipula()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StipulaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StipulaType)get_store().find_element_user(STIPULA$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Stipula" element
     */
    public void setStipula(it.avlp.simog.massload.xmlbeans.StipulaType stipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StipulaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StipulaType)get_store().find_element_user(STIPULA$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StipulaType)get_store().add_element_user(STIPULA$2);
            }
            target.set(stipula);
        }
    }
    
    /**
     * Appends and returns a new empty "Stipula" element
     */
    public it.avlp.simog.massload.xmlbeans.StipulaType addNewStipula()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StipulaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StipulaType)get_store().add_element_user(STIPULA$2);
            return target;
        }
    }
}
