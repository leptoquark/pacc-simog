/*
 * XML Type:  CPVSecondariaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CPVSecondariaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML CPVSecondariaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CPVSecondariaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CPVSecondariaType
{
    
    public CPVSecondariaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODCPVSECONDARIA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "COD_CPV_SECONDARIA");
    
    
    /**
     * Gets the "COD_CPV_SECONDARIA" attribute
     */
    public java.lang.String getCODCPVSECONDARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODCPVSECONDARIA$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "COD_CPV_SECONDARIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA xgetCODCPVSECONDARIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA target = null;
            target = (it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA)get_store().find_attribute_user(CODCPVSECONDARIA$0);
            return target;
        }
    }
    
    /**
     * Sets the "COD_CPV_SECONDARIA" attribute
     */
    public void setCODCPVSECONDARIA(java.lang.String codcpvsecondaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODCPVSECONDARIA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODCPVSECONDARIA$0);
            }
            target.setStringValue(codcpvsecondaria);
        }
    }
    
    /**
     * Sets (as xml) the "COD_CPV_SECONDARIA" attribute
     */
    public void xsetCODCPVSECONDARIA(it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA codcpvsecondaria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA target = null;
            target = (it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA)get_store().find_attribute_user(CODCPVSECONDARIA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA)get_store().add_attribute_user(CODCPVSECONDARIA$0);
            }
            target.set(codcpvsecondaria);
        }
    }
    /**
     * An XML COD_CPV_SECONDARIA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.CPVSecondariaType$CODCPVSECONDARIA.
     */
    public static class CODCPVSECONDARIAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.CPVSecondariaType.CODCPVSECONDARIA
    {
        
        public CODCPVSECONDARIAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CODCPVSECONDARIAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
