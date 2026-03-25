/*
 * XML Type:  DeltaLottoTED
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DeltaLottoTED
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DeltaLottoTED(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DeltaLottoTEDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DeltaLottoTED
{
    
    public DeltaLottoTEDImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DESCRIZIONEAPPALTO$0 = 
        new javax.xml.namespace.QName("", "DESCRIZIONE_APPALTO");
    private static final javax.xml.namespace.QName NOLOT$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NO_LOT");
    
    
    /**
     * Gets the "DESCRIZIONE_APPALTO" element
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType getDESCRIZIONEAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType)get_store().find_element_user(DESCRIZIONEAPPALTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DESCRIZIONE_APPALTO" element
     */
    public void setDESCRIZIONEAPPALTO(it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType descrizioneappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType)get_store().find_element_user(DESCRIZIONEAPPALTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType)get_store().add_element_user(DESCRIZIONEAPPALTO$0);
            }
            target.set(descrizioneappalto);
        }
    }
    
    /**
     * Appends and returns a new empty "DESCRIZIONE_APPALTO" element
     */
    public it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType addNewDESCRIZIONEAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType)get_store().add_element_user(DESCRIZIONEAPPALTO$0);
            return target;
        }
    }
    
    /**
     * Gets the "NO_LOT" attribute
     */
    public int getNOLOT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOLOT$2);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NO_LOT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT xgetNOLOT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT)get_store().find_attribute_user(NOLOT$2);
            return target;
        }
    }
    
    /**
     * True if has "NO_LOT" attribute
     */
    public boolean isSetNOLOT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NOLOT$2) != null;
        }
    }
    
    /**
     * Sets the "NO_LOT" attribute
     */
    public void setNOLOT(int nolot)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NOLOT$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NOLOT$2);
            }
            target.setIntValue(nolot);
        }
    }
    
    /**
     * Sets (as xml) the "NO_LOT" attribute
     */
    public void xsetNOLOT(it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT nolot)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT)get_store().find_attribute_user(NOLOT$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT)get_store().add_attribute_user(NOLOT$2);
            }
            target.set(nolot);
        }
    }
    
    /**
     * Unsets the "NO_LOT" attribute
     */
    public void unsetNOLOT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NOLOT$2);
        }
    }
    /**
     * An XML NO_LOT(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DeltaLottoTED$NOLOT.
     */
    public static class NOLOTImpl extends org.apache.xmlbeans.impl.values.JavaIntHolderEx implements it.avlp.simog.massload.xmlbeans.DeltaLottoTED.NOLOT
    {
        
        public NOLOTImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NOLOTImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
