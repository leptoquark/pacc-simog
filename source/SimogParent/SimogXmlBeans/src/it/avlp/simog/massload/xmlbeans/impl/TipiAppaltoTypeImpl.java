/*
 * XML Type:  TipiAppaltoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TipiAppaltoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML TipiAppaltoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class TipiAppaltoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.TipiAppaltoType
{
    
    public TipiAppaltoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDAPPALTO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_APPALTO");
    
    
    /**
     * Gets the "ID_APPALTO" attribute
     */
    public java.lang.String getIDAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDAPPALTO$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_APPALTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipoAppaltoType xgetIDAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoAppaltoType)get_store().find_attribute_user(IDAPPALTO$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_APPALTO" attribute
     */
    public void setIDAPPALTO(java.lang.String idappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDAPPALTO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDAPPALTO$0);
            }
            target.setStringValue(idappalto);
        }
    }
    
    /**
     * Sets (as xml) the "ID_APPALTO" attribute
     */
    public void xsetIDAPPALTO(it.avlp.simog.massload.xmlbeans.TipoAppaltoType idappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoAppaltoType)get_store().find_attribute_user(IDAPPALTO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipoAppaltoType)get_store().add_attribute_user(IDAPPALTO$0);
            }
            target.set(idappalto);
        }
    }
}
