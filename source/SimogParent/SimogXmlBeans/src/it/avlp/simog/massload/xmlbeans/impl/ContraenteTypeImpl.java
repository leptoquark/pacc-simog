/*
 * XML Type:  ContraenteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ContraenteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ContraenteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ContraenteTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ContraenteType
{
    
    public ContraenteTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESSCONTRACTOR$0 = 
        new javax.xml.namespace.QName("", "ADDRESS_CONTRACTOR");
    private static final javax.xml.namespace.QName AWARDEDISSME$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AWARDED_IS_SME");
    
    
    /**
     * Gets the "ADDRESS_CONTRACTOR" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS5Type getADDRESSCONTRACTOR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type)get_store().find_element_user(ADDRESSCONTRACTOR$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ADDRESS_CONTRACTOR" element
     */
    public void setADDRESSCONTRACTOR(it.avlp.simog.massload.xmlbeans.AddrS5Type addresscontractor)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type)get_store().find_element_user(ADDRESSCONTRACTOR$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS5Type)get_store().add_element_user(ADDRESSCONTRACTOR$0);
            }
            target.set(addresscontractor);
        }
    }
    
    /**
     * Appends and returns a new empty "ADDRESS_CONTRACTOR" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS5Type addNewADDRESSCONTRACTOR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5Type)get_store().add_element_user(ADDRESSCONTRACTOR$0);
            return target;
        }
    }
    
    /**
     * Gets the "AWARDED_IS_SME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getAWARDEDISSME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AWARDEDISSME$2);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "AWARDED_IS_SME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetAWARDEDISSME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(AWARDEDISSME$2);
            return target;
        }
    }
    
    /**
     * Sets the "AWARDED_IS_SME" attribute
     */
    public void setAWARDEDISSME(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum awardedissme)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(AWARDEDISSME$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(AWARDEDISSME$2);
            }
            target.setEnumValue(awardedissme);
        }
    }
    
    /**
     * Sets (as xml) the "AWARDED_IS_SME" attribute
     */
    public void xsetAWARDEDISSME(it.avlp.simog.massload.xmlbeans.FlagSNType awardedissme)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(AWARDEDISSME$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(AWARDEDISSME$2);
            }
            target.set(awardedissme);
        }
    }
}
