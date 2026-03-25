/*
 * XML Type:  ContraenteTypeMod
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ContraenteTypeMod
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML ContraenteTypeMod(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ContraenteTypeModImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ContraenteTypeMod
{
    
    public ContraenteTypeModImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESSCONTRACTORMOD$0 = 
        new javax.xml.namespace.QName("", "ADDRESS_CONTRACTOR_MOD");
    private static final javax.xml.namespace.QName AWARDEDISSME$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "AWARDED_IS_SME");
    
    
    /**
     * Gets the "ADDRESS_CONTRACTOR_MOD" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS5TypeMod getADDRESSCONTRACTORMOD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5TypeMod target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5TypeMod)get_store().find_element_user(ADDRESSCONTRACTORMOD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ADDRESS_CONTRACTOR_MOD" element
     */
    public void setADDRESSCONTRACTORMOD(it.avlp.simog.massload.xmlbeans.AddrS5TypeMod addresscontractormod)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5TypeMod target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5TypeMod)get_store().find_element_user(ADDRESSCONTRACTORMOD$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS5TypeMod)get_store().add_element_user(ADDRESSCONTRACTORMOD$0);
            }
            target.set(addresscontractormod);
        }
    }
    
    /**
     * Appends and returns a new empty "ADDRESS_CONTRACTOR_MOD" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS5TypeMod addNewADDRESSCONTRACTORMOD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS5TypeMod target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS5TypeMod)get_store().add_element_user(ADDRESSCONTRACTORMOD$0);
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
