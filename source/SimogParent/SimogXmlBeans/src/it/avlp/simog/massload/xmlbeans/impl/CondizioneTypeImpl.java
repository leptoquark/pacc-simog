/*
 * XML Type:  CondizioneType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CondizioneType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML CondizioneType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CondizioneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CondizioneType
{
    
    public CondizioneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCONDIZIONE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_CONDIZIONE");
    
    
    /**
     * Gets the "ID_CONDIZIONE" attribute
     */
    public java.lang.String getIDCONDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCONDIZIONE$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CONDIZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioneAggType xgetIDCONDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneAggType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneAggType)get_store().find_attribute_user(IDCONDIZIONE$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_CONDIZIONE" attribute
     */
    public void setIDCONDIZIONE(java.lang.String idcondizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCONDIZIONE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDCONDIZIONE$0);
            }
            target.setStringValue(idcondizione);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CONDIZIONE" attribute
     */
    public void xsetIDCONDIZIONE(it.avlp.simog.massload.xmlbeans.CondizioneAggType idcondizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneAggType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneAggType)get_store().find_attribute_user(IDCONDIZIONE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioneAggType)get_store().add_attribute_user(IDCONDIZIONE$0);
            }
            target.set(idcondizione);
        }
    }
}
