/*
 * XML Type:  LuogoNutsAggType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.LuogoNutsAggType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML LuogoNutsAggType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class LuogoNutsAggTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.LuogoNutsAggType
{
    
    public LuogoNutsAggTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LUOGONUTS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "LUOGO_NUTS");
    
    
    /**
     * Gets the "LUOGO_NUTS" attribute
     */
    public java.lang.String getLUOGONUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGONUTS$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LUOGO_NUTS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType xgetLUOGONUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(LUOGONUTS$0);
            return target;
        }
    }
    
    /**
     * Sets the "LUOGO_NUTS" attribute
     */
    public void setLUOGONUTS(java.lang.String luogonuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LUOGONUTS$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LUOGONUTS$0);
            }
            target.setStringValue(luogonuts);
        }
    }
    
    /**
     * Sets (as xml) the "LUOGO_NUTS" attribute
     */
    public void xsetLUOGONUTS(it.avlp.simog.massload.xmlbeans.LuogoNutsType luogonuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(LUOGONUTS$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().add_attribute_user(LUOGONUTS$0);
            }
            target.set(luogonuts);
        }
    }
}
