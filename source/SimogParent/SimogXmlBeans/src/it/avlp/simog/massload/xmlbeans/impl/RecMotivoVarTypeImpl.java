/*
 * XML Type:  RecMotivoVarType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecMotivoVarType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RecMotivoVarType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RecMotivoVarTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RecMotivoVarType
{
    
    public RecMotivoVarTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDMOTIVOVAR$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_MOTIVO_VAR");
    
    
    /**
     * Gets the "ID_MOTIVO_VAR" attribute
     */
    public java.lang.String getIDMOTIVOVAR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOVAR$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_VAR" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoVarianteType xgetIDMOTIVOVAR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoVarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoVarianteType)get_store().find_attribute_user(IDMOTIVOVAR$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_VAR" attribute
     */
    public void setIDMOTIVOVAR(java.lang.String idmotivovar)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDMOTIVOVAR$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDMOTIVOVAR$0);
            }
            target.setStringValue(idmotivovar);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_VAR" attribute
     */
    public void xsetIDMOTIVOVAR(it.avlp.simog.massload.xmlbeans.MotivoVarianteType idmotivovar)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoVarianteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoVarianteType)get_store().find_attribute_user(IDMOTIVOVAR$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotivoVarianteType)get_store().add_attribute_user(IDMOTIVOVAR$0);
            }
            target.set(idmotivovar);
        }
    }
}
