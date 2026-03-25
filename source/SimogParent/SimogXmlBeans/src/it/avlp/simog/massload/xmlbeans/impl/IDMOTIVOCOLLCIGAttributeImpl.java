/*
 * An XML attribute type.
 * Localname: ID_MOTIVO_COLL_CIG
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.IDMOTIVOCOLLCIGAttribute
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one ID_MOTIVO_COLL_CIG(@xmlbeans.massload.simog.avlp.it) attribute.
 *
 * This is a complex type.
 */
public class IDMOTIVOCOLLCIGAttributeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.IDMOTIVOCOLLCIGAttribute
{
    
    public IDMOTIVOCOLLCIGAttributeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDMOTIVOCOLLCIG$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_MOTIVO_COLL_CIG");
    
    
    /**
     * Gets the "ID_MOTIVO_COLL_CIG" attribute
     */
    public java.lang.String getIDMOTIVOCOLLCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOCOLLCIG$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_COLL_CIG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoCollegamentoType xgetIDMOTIVOCOLLCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoCollegamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoCollegamentoType)get_store().find_attribute_user(IDMOTIVOCOLLCIG$0);
            return target;
        }
    }
    
    /**
     * True if has "ID_MOTIVO_COLL_CIG" attribute
     */
    public boolean isSetIDMOTIVOCOLLCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(IDMOTIVOCOLLCIG$0) != null;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_COLL_CIG" attribute
     */
    public void setIDMOTIVOCOLLCIG(java.lang.String idmotivocollcig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOCOLLCIG$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDMOTIVOCOLLCIG$0);
            }
            target.setStringValue(idmotivocollcig);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_COLL_CIG" attribute
     */
    public void xsetIDMOTIVOCOLLCIG(it.avlp.simog.massload.xmlbeans.MotivoCollegamentoType idmotivocollcig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoCollegamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoCollegamentoType)get_store().find_attribute_user(IDMOTIVOCOLLCIG$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotivoCollegamentoType)get_store().add_attribute_user(IDMOTIVOCOLLCIG$0);
            }
            target.set(idmotivocollcig);
        }
    }
    
    /**
     * Unsets the "ID_MOTIVO_COLL_CIG" attribute
     */
    public void unsetIDMOTIVOCOLLCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(IDMOTIVOCOLLCIG$0);
        }
    }
}
