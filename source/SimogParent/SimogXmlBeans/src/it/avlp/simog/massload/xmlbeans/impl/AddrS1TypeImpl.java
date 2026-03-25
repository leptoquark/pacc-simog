/*
 * XML Type:  AddrS1Type
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AddrS1Type
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AddrS1Type(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AddrS1TypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AddrS1Type
{
    
    public AddrS1TypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OFFICIALNAME$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OFFICIALNAME");
    private static final javax.xml.namespace.QName NATIONALID$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NATIONALID");
    private static final javax.xml.namespace.QName ADDRESS$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ADDRESS");
    private static final javax.xml.namespace.QName TOWN$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TOWN");
    private static final javax.xml.namespace.QName NUTS$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUTS");
    private static final javax.xml.namespace.QName POSTALCODE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "POSTAL_CODE");
    private static final javax.xml.namespace.QName COUNTRY$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "COUNTRY");
    private static final javax.xml.namespace.QName CONTACTPOINT$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CONTACT_POINT");
    private static final javax.xml.namespace.QName PHONE$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PHONE");
    private static final javax.xml.namespace.QName FAX$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FAX");
    private static final javax.xml.namespace.QName EMAIL$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "E_MAIL");
    private static final javax.xml.namespace.QName URLGENERAL$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL_GENERAL");
    private static final javax.xml.namespace.QName URLBUYER$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL_BUYER");
    
    
    /**
     * Gets the "OFFICIALNAME" attribute
     */
    public java.lang.String getOFFICIALNAME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFICIALNAME$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OFFICIALNAME" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME xgetOFFICIALNAME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME)get_store().find_attribute_user(OFFICIALNAME$0);
            return target;
        }
    }
    
    /**
     * Sets the "OFFICIALNAME" attribute
     */
    public void setOFFICIALNAME(java.lang.String officialname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFICIALNAME$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OFFICIALNAME$0);
            }
            target.setStringValue(officialname);
        }
    }
    
    /**
     * Sets (as xml) the "OFFICIALNAME" attribute
     */
    public void xsetOFFICIALNAME(it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME officialname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME)get_store().find_attribute_user(OFFICIALNAME$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME)get_store().add_attribute_user(OFFICIALNAME$0);
            }
            target.set(officialname);
        }
    }
    
    /**
     * Gets the "NATIONALID" attribute
     */
    public java.lang.String getNATIONALID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NATIONALID$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NATIONALID" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID xgetNATIONALID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID)get_store().find_attribute_user(NATIONALID$2);
            return target;
        }
    }
    
    /**
     * Sets the "NATIONALID" attribute
     */
    public void setNATIONALID(java.lang.String nationalid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NATIONALID$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NATIONALID$2);
            }
            target.setStringValue(nationalid);
        }
    }
    
    /**
     * Sets (as xml) the "NATIONALID" attribute
     */
    public void xsetNATIONALID(it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID nationalid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID)get_store().find_attribute_user(NATIONALID$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID)get_store().add_attribute_user(NATIONALID$2);
            }
            target.set(nationalid);
        }
    }
    
    /**
     * Gets the "ADDRESS" attribute
     */
    public java.lang.String getADDRESS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDRESS$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ADDRESS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS xgetADDRESS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS)get_store().find_attribute_user(ADDRESS$4);
            return target;
        }
    }
    
    /**
     * True if has "ADDRESS" attribute
     */
    public boolean isSetADDRESS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ADDRESS$4) != null;
        }
    }
    
    /**
     * Sets the "ADDRESS" attribute
     */
    public void setADDRESS(java.lang.String address)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDRESS$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDRESS$4);
            }
            target.setStringValue(address);
        }
    }
    
    /**
     * Sets (as xml) the "ADDRESS" attribute
     */
    public void xsetADDRESS(it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS address)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS)get_store().find_attribute_user(ADDRESS$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS)get_store().add_attribute_user(ADDRESS$4);
            }
            target.set(address);
        }
    }
    
    /**
     * Unsets the "ADDRESS" attribute
     */
    public void unsetADDRESS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ADDRESS$4);
        }
    }
    
    /**
     * Gets the "TOWN" attribute
     */
    public java.lang.String getTOWN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOWN$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TOWN" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN xgetTOWN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN)get_store().find_attribute_user(TOWN$6);
            return target;
        }
    }
    
    /**
     * Sets the "TOWN" attribute
     */
    public void setTOWN(java.lang.String town)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOWN$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TOWN$6);
            }
            target.setStringValue(town);
        }
    }
    
    /**
     * Sets (as xml) the "TOWN" attribute
     */
    public void xsetTOWN(it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN town)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN)get_store().find_attribute_user(TOWN$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN)get_store().add_attribute_user(TOWN$6);
            }
            target.set(town);
        }
    }
    
    /**
     * Gets the "NUTS" attribute
     */
    public java.lang.String getNUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUTS$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUTS" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LuogoNutsType xgetNUTS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(NUTS$8);
            return target;
        }
    }
    
    /**
     * Sets the "NUTS" attribute
     */
    public void setNUTS(java.lang.String nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUTS$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUTS$8);
            }
            target.setStringValue(nuts);
        }
    }
    
    /**
     * Sets (as xml) the "NUTS" attribute
     */
    public void xsetNUTS(it.avlp.simog.massload.xmlbeans.LuogoNutsType nuts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LuogoNutsType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().find_attribute_user(NUTS$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LuogoNutsType)get_store().add_attribute_user(NUTS$8);
            }
            target.set(nuts);
        }
    }
    
    /**
     * Gets the "POSTAL_CODE" attribute
     */
    public java.lang.String getPOSTALCODE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(POSTALCODE$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "POSTAL_CODE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE xgetPOSTALCODE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE)get_store().find_attribute_user(POSTALCODE$10);
            return target;
        }
    }
    
    /**
     * True if has "POSTAL_CODE" attribute
     */
    public boolean isSetPOSTALCODE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(POSTALCODE$10) != null;
        }
    }
    
    /**
     * Sets the "POSTAL_CODE" attribute
     */
    public void setPOSTALCODE(java.lang.String postalcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(POSTALCODE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(POSTALCODE$10);
            }
            target.setStringValue(postalcode);
        }
    }
    
    /**
     * Sets (as xml) the "POSTAL_CODE" attribute
     */
    public void xsetPOSTALCODE(it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE postalcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE)get_store().find_attribute_user(POSTALCODE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE)get_store().add_attribute_user(POSTALCODE$10);
            }
            target.set(postalcode);
        }
    }
    
    /**
     * Unsets the "POSTAL_CODE" attribute
     */
    public void unsetPOSTALCODE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(POSTALCODE$10);
        }
    }
    
    /**
     * Gets the "COUNTRY" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CountryType.Enum getCOUNTRY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COUNTRY$12);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.CountryType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "COUNTRY" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CountryType xgetCOUNTRY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CountryType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CountryType)get_store().find_attribute_user(COUNTRY$12);
            return target;
        }
    }
    
    /**
     * Sets the "COUNTRY" attribute
     */
    public void setCOUNTRY(it.avlp.simog.massload.xmlbeans.CountryType.Enum country)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COUNTRY$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(COUNTRY$12);
            }
            target.setEnumValue(country);
        }
    }
    
    /**
     * Sets (as xml) the "COUNTRY" attribute
     */
    public void xsetCOUNTRY(it.avlp.simog.massload.xmlbeans.CountryType country)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CountryType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CountryType)get_store().find_attribute_user(COUNTRY$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CountryType)get_store().add_attribute_user(COUNTRY$12);
            }
            target.set(country);
        }
    }
    
    /**
     * Gets the "CONTACT_POINT" attribute
     */
    public java.lang.String getCONTACTPOINT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTPOINT$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CONTACT_POINT" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT xgetCONTACTPOINT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT)get_store().find_attribute_user(CONTACTPOINT$14);
            return target;
        }
    }
    
    /**
     * True if has "CONTACT_POINT" attribute
     */
    public boolean isSetCONTACTPOINT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CONTACTPOINT$14) != null;
        }
    }
    
    /**
     * Sets the "CONTACT_POINT" attribute
     */
    public void setCONTACTPOINT(java.lang.String contactpoint)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTPOINT$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONTACTPOINT$14);
            }
            target.setStringValue(contactpoint);
        }
    }
    
    /**
     * Sets (as xml) the "CONTACT_POINT" attribute
     */
    public void xsetCONTACTPOINT(it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT contactpoint)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT)get_store().find_attribute_user(CONTACTPOINT$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT)get_store().add_attribute_user(CONTACTPOINT$14);
            }
            target.set(contactpoint);
        }
    }
    
    /**
     * Unsets the "CONTACT_POINT" attribute
     */
    public void unsetCONTACTPOINT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CONTACTPOINT$14);
        }
    }
    
    /**
     * Gets the "PHONE" attribute
     */
    public java.lang.String getPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PHONE$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PHONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.Phone xgetPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(PHONE$16);
            return target;
        }
    }
    
    /**
     * True if has "PHONE" attribute
     */
    public boolean isSetPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PHONE$16) != null;
        }
    }
    
    /**
     * Sets the "PHONE" attribute
     */
    public void setPHONE(java.lang.String phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PHONE$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PHONE$16);
            }
            target.setStringValue(phone);
        }
    }
    
    /**
     * Sets (as xml) the "PHONE" attribute
     */
    public void xsetPHONE(it.avlp.simog.massload.xmlbeans.Phone phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(PHONE$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().add_attribute_user(PHONE$16);
            }
            target.set(phone);
        }
    }
    
    /**
     * Unsets the "PHONE" attribute
     */
    public void unsetPHONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PHONE$16);
        }
    }
    
    /**
     * Gets the "FAX" attribute
     */
    public java.lang.String getFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FAX" attribute
     */
    public it.avlp.simog.massload.xmlbeans.Phone xgetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(FAX$18);
            return target;
        }
    }
    
    /**
     * True if has "FAX" attribute
     */
    public boolean isSetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(FAX$18) != null;
        }
    }
    
    /**
     * Sets the "FAX" attribute
     */
    public void setFAX(java.lang.String fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FAX$18);
            }
            target.setStringValue(fax);
        }
    }
    
    /**
     * Sets (as xml) the "FAX" attribute
     */
    public void xsetFAX(it.avlp.simog.massload.xmlbeans.Phone fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.Phone target = null;
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(FAX$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().add_attribute_user(FAX$18);
            }
            target.set(fax);
        }
    }
    
    /**
     * Unsets the "FAX" attribute
     */
    public void unsetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(FAX$18);
        }
    }
    
    /**
     * Gets the "E_MAIL" attribute
     */
    public java.lang.String getEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$20);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "E_MAIL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL xgetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL)get_store().find_attribute_user(EMAIL$20);
            return target;
        }
    }
    
    /**
     * Sets the "E_MAIL" attribute
     */
    public void setEMAIL(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EMAIL$20);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "E_MAIL" attribute
     */
    public void xsetEMAIL(it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL)get_store().find_attribute_user(EMAIL$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL)get_store().add_attribute_user(EMAIL$20);
            }
            target.set(email);
        }
    }
    
    /**
     * Gets the "URL_GENERAL" attribute
     */
    public java.lang.String getURLGENERAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLGENERAL$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL_GENERAL" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL xgetURLGENERAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL)get_store().find_attribute_user(URLGENERAL$22);
            return target;
        }
    }
    
    /**
     * Sets the "URL_GENERAL" attribute
     */
    public void setURLGENERAL(java.lang.String urlgeneral)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLGENERAL$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URLGENERAL$22);
            }
            target.setStringValue(urlgeneral);
        }
    }
    
    /**
     * Sets (as xml) the "URL_GENERAL" attribute
     */
    public void xsetURLGENERAL(it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL urlgeneral)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL)get_store().find_attribute_user(URLGENERAL$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL)get_store().add_attribute_user(URLGENERAL$22);
            }
            target.set(urlgeneral);
        }
    }
    
    /**
     * Gets the "URL_BUYER" attribute
     */
    public java.lang.String getURLBUYER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLBUYER$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL_BUYER" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER xgetURLBUYER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER)get_store().find_attribute_user(URLBUYER$24);
            return target;
        }
    }
    
    /**
     * True if has "URL_BUYER" attribute
     */
    public boolean isSetURLBUYER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(URLBUYER$24) != null;
        }
    }
    
    /**
     * Sets the "URL_BUYER" attribute
     */
    public void setURLBUYER(java.lang.String urlbuyer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLBUYER$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URLBUYER$24);
            }
            target.setStringValue(urlbuyer);
        }
    }
    
    /**
     * Sets (as xml) the "URL_BUYER" attribute
     */
    public void xsetURLBUYER(it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER urlbuyer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER)get_store().find_attribute_user(URLBUYER$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER)get_store().add_attribute_user(URLBUYER$24);
            }
            target.set(urlbuyer);
        }
    }
    
    /**
     * Unsets the "URL_BUYER" attribute
     */
    public void unsetURLBUYER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(URLBUYER$24);
        }
    }
    /**
     * An XML OFFICIALNAME(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$OFFICIALNAME.
     */
    public static class OFFICIALNAMEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.OFFICIALNAME
    {
        
        public OFFICIALNAMEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected OFFICIALNAMEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML NATIONALID(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$NATIONALID.
     */
    public static class NATIONALIDImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.NATIONALID
    {
        
        public NATIONALIDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NATIONALIDImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML ADDRESS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$ADDRESS.
     */
    public static class ADDRESSImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.ADDRESS
    {
        
        public ADDRESSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ADDRESSImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML TOWN(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$TOWN.
     */
    public static class TOWNImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.TOWN
    {
        
        public TOWNImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected TOWNImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML POSTAL_CODE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$POSTALCODE.
     */
    public static class POSTALCODEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.POSTALCODE
    {
        
        public POSTALCODEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected POSTALCODEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML CONTACT_POINT(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$CONTACTPOINT.
     */
    public static class CONTACTPOINTImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.CONTACTPOINT
    {
        
        public CONTACTPOINTImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected CONTACTPOINTImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML E_MAIL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$EMAIL.
     */
    public static class EMAILImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.EMAIL
    {
        
        public EMAILImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected EMAILImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML URL_GENERAL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$URLGENERAL.
     */
    public static class URLGENERALImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.URLGENERAL
    {
        
        public URLGENERALImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLGENERALImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML URL_BUYER(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS1Type$URLBUYER.
     */
    public static class URLBUYERImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS1Type.URLBUYER
    {
        
        public URLBUYERImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLBUYERImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
