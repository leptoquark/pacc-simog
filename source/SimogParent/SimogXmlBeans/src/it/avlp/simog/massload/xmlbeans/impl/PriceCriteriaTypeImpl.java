/*
 * XML Type:  PriceCriteriaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.PriceCriteriaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML PriceCriteriaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class PriceCriteriaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.PriceCriteriaType
{
    
    public PriceCriteriaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PCCRITERIAWEIGHTING$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PC_CRITERIA_WEIGHTING");
    
    
    /**
     * Gets the "PC_CRITERIA_WEIGHTING" attribute
     */
    public java.lang.String getPCCRITERIAWEIGHTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PCCRITERIAWEIGHTING$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PC_CRITERIA_WEIGHTING" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING xgetPCCRITERIAWEIGHTING()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING target = null;
            target = (it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING)get_store().find_attribute_user(PCCRITERIAWEIGHTING$0);
            return target;
        }
    }
    
    /**
     * Sets the "PC_CRITERIA_WEIGHTING" attribute
     */
    public void setPCCRITERIAWEIGHTING(java.lang.String pccriteriaweighting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PCCRITERIAWEIGHTING$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PCCRITERIAWEIGHTING$0);
            }
            target.setStringValue(pccriteriaweighting);
        }
    }
    
    /**
     * Sets (as xml) the "PC_CRITERIA_WEIGHTING" attribute
     */
    public void xsetPCCRITERIAWEIGHTING(it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING pccriteriaweighting)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING target = null;
            target = (it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING)get_store().find_attribute_user(PCCRITERIAWEIGHTING$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING)get_store().add_attribute_user(PCCRITERIAWEIGHTING$0);
            }
            target.set(pccriteriaweighting);
        }
    }
    /**
     * An XML PC_CRITERIA_WEIGHTING(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.PriceCriteriaType$PCCRITERIAWEIGHTING.
     */
    public static class PCCRITERIAWEIGHTINGImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.PriceCriteriaType.PCCRITERIAWEIGHTING
    {
        
        public PCCRITERIAWEIGHTINGImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected PCCRITERIAWEIGHTINGImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
