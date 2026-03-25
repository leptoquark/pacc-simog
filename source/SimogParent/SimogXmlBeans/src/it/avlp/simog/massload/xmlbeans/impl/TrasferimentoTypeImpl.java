/*
 * XML Type:  TrasferimentoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TrasferimentoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML TrasferimentoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class TrasferimentoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.TrasferimentoType
{
    
    public TrasferimentoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATACREAZIONEFLUSSO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_CREAZIONE_FLUSSO");
    private static final javax.xml.namespace.QName NUMSCHEDE$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_SCHEDE");
    
    
    /**
     * Gets the "DATA_CREAZIONE_FLUSSO" attribute
     */
    public java.util.Calendar getDATACREAZIONEFLUSSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACREAZIONEFLUSSO$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CREAZIONE_FLUSSO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATACREAZIONEFLUSSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACREAZIONEFLUSSO$0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_CREAZIONE_FLUSSO" attribute
     */
    public void setDATACREAZIONEFLUSSO(java.util.Calendar datacreazioneflusso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATACREAZIONEFLUSSO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATACREAZIONEFLUSSO$0);
            }
            target.setCalendarValue(datacreazioneflusso);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CREAZIONE_FLUSSO" attribute
     */
    public void xsetDATACREAZIONEFLUSSO(it.avlp.simog.massload.xmlbeans.DbDateType datacreazioneflusso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATACREAZIONEFLUSSO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATACREAZIONEFLUSSO$0);
            }
            target.set(datacreazioneflusso);
        }
    }
    
    /**
     * Gets the "NUM_SCHEDE" attribute
     */
    public int getNUMSCHEDE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMSCHEDE$2);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_SCHEDE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMSCHEDE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMSCHEDE$2);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_SCHEDE" attribute
     */
    public void setNUMSCHEDE(int numschede)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMSCHEDE$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMSCHEDE$2);
            }
            target.setIntValue(numschede);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_SCHEDE" attribute
     */
    public void xsetNUMSCHEDE(it.avlp.simog.massload.xmlbeans.InteroType numschede)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMSCHEDE$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMSCHEDE$2);
            }
            target.set(numschede);
        }
    }
}
