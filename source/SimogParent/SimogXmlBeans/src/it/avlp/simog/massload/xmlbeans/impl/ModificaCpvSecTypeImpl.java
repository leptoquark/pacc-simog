/*
 * XML Type:  ModificaCpvSecType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ModificaCpvSecType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ModificaCpvSecType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ModificaCpvSecTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ModificaCpvSecType
{
    
    public ModificaCpvSecTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDITIONALCPVCODE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ADDITIONAL_CPV_CODE");
    
    
    /**
     * Gets the "ADDITIONAL_CPV_CODE" attribute
     */
    public java.lang.String getADDITIONALCPVCODE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONALCPVCODE$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ADDITIONAL_CPV_CODE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE xgetADDITIONALCPVCODE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE)get_store().find_attribute_user(ADDITIONALCPVCODE$0);
            return target;
        }
    }
    
    /**
     * Sets the "ADDITIONAL_CPV_CODE" attribute
     */
    public void setADDITIONALCPVCODE(java.lang.String additionalcpvcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONALCPVCODE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDITIONALCPVCODE$0);
            }
            target.setStringValue(additionalcpvcode);
        }
    }
    
    /**
     * Sets (as xml) the "ADDITIONAL_CPV_CODE" attribute
     */
    public void xsetADDITIONALCPVCODE(it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE additionalcpvcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE)get_store().find_attribute_user(ADDITIONALCPVCODE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE)get_store().add_attribute_user(ADDITIONALCPVCODE$0);
            }
            target.set(additionalcpvcode);
        }
    }
    /**
     * An XML ADDITIONAL_CPV_CODE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.ModificaCpvSecType$ADDITIONALCPVCODE.
     */
    public static class ADDITIONALCPVCODEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.ModificaCpvSecType.ADDITIONALCPVCODE
    {
        
        public ADDITIONALCPVCODEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ADDITIONALCPVCODEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
