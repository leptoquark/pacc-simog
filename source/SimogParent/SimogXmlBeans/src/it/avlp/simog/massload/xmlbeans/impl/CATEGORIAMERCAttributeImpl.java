/*
 * An XML attribute type.
 * Localname: CATEGORIA_MERC
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CATEGORIAMERCAttribute
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one CATEGORIA_MERC(@xmlbeans.massload.simog.avlp.it) attribute.
 *
 * This is a complex type.
 */
public class CATEGORIAMERCAttributeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CATEGORIAMERCAttribute
{
    
    public CATEGORIAMERCAttributeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CATEGORIAMERC$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CATEGORIA_MERC");
    
    
    /**
     * Gets the "CATEGORIA_MERC" attribute
     */
    public java.lang.String getCATEGORIAMERC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CATEGORIAMERC$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CATEGORIA_MERC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType xgetCATEGORIAMERC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)get_store().find_attribute_user(CATEGORIAMERC$0);
            return target;
        }
    }
    
    /**
     * True if has "CATEGORIA_MERC" attribute
     */
    public boolean isSetCATEGORIAMERC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CATEGORIAMERC$0) != null;
        }
    }
    
    /**
     * Sets the "CATEGORIA_MERC" attribute
     */
    public void setCATEGORIAMERC(java.lang.String categoriamerc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CATEGORIAMERC$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CATEGORIAMERC$0);
            }
            target.setStringValue(categoriamerc);
        }
    }
    
    /**
     * Sets (as xml) the "CATEGORIA_MERC" attribute
     */
    public void xsetCATEGORIAMERC(it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType categoriamerc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)get_store().find_attribute_user(CATEGORIAMERC$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CategorieMerceologicheType)get_store().add_attribute_user(CATEGORIAMERC$0);
            }
            target.set(categoriamerc);
        }
    }
    
    /**
     * Unsets the "CATEGORIA_MERC" attribute
     */
    public void unsetCATEGORIAMERC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CATEGORIAMERC$0);
        }
    }
}
