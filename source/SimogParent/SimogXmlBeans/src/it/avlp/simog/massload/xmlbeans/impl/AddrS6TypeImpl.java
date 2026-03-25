/*
 * XML Type:  AddrS6Type
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AddrS6Type
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AddrS6Type(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AddrS6TypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AddrS6Type
{
    
    public AddrS6TypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OFFICIALNAME$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OFFICIALNAME");
    private static final javax.xml.namespace.QName ADDRESS$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ADDRESS");
    private static final javax.xml.namespace.QName TOWN$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TOWN");
    private static final javax.xml.namespace.QName POSTALCODE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "POSTAL_CODE");
    private static final javax.xml.namespace.QName COUNTRY$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "COUNTRY");
    private static final javax.xml.namespace.QName PHONE$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PHONE");
    private static final javax.xml.namespace.QName FAX$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FAX");
    private static final javax.xml.namespace.QName EMAIL$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "E_MAIL");
    private static final javax.xml.namespace.QName URLSA$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL_SA");
    
    
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
    public it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME xgetOFFICIALNAME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME)get_store().find_attribute_user(OFFICIALNAME$0);
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
    public void xsetOFFICIALNAME(it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME officialname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME)get_store().find_attribute_user(OFFICIALNAME$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME)get_store().add_attribute_user(OFFICIALNAME$0);
            }
            target.set(officialname);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDRESS$2);
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
    public it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS xgetADDRESS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS)get_store().find_attribute_user(ADDRESS$2);
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
            return get_store().find_attribute_user(ADDRESS$2) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDRESS$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDRESS$2);
            }
            target.setStringValue(address);
        }
    }
    
    /**
     * Sets (as xml) the "ADDRESS" attribute
     */
    public void xsetADDRESS(it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS address)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS)get_store().find_attribute_user(ADDRESS$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS)get_store().add_attribute_user(ADDRESS$2);
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
            get_store().remove_attribute(ADDRESS$2);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOWN$4);
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
    public it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN xgetTOWN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN)get_store().find_attribute_user(TOWN$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOWN$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TOWN$4);
            }
            target.setStringValue(town);
        }
    }
    
    /**
     * Sets (as xml) the "TOWN" attribute
     */
    public void xsetTOWN(it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN town)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN)get_store().find_attribute_user(TOWN$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN)get_store().add_attribute_user(TOWN$4);
            }
            target.set(town);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(POSTALCODE$6);
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
    public it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE xgetPOSTALCODE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE)get_store().find_attribute_user(POSTALCODE$6);
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
            return get_store().find_attribute_user(POSTALCODE$6) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(POSTALCODE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(POSTALCODE$6);
            }
            target.setStringValue(postalcode);
        }
    }
    
    /**
     * Sets (as xml) the "POSTAL_CODE" attribute
     */
    public void xsetPOSTALCODE(it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE postalcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE)get_store().find_attribute_user(POSTALCODE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE)get_store().add_attribute_user(POSTALCODE$6);
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
            get_store().remove_attribute(POSTALCODE$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COUNTRY$8);
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
            target = (it.avlp.simog.massload.xmlbeans.CountryType)get_store().find_attribute_user(COUNTRY$8);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COUNTRY$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(COUNTRY$8);
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
            target = (it.avlp.simog.massload.xmlbeans.CountryType)get_store().find_attribute_user(COUNTRY$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CountryType)get_store().add_attribute_user(COUNTRY$8);
            }
            target.set(country);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PHONE$10);
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
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(PHONE$10);
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
            return get_store().find_attribute_user(PHONE$10) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PHONE$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PHONE$10);
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
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(PHONE$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().add_attribute_user(PHONE$10);
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
            get_store().remove_attribute(PHONE$10);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$12);
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
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(FAX$12);
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
            return get_store().find_attribute_user(FAX$12) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAX$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FAX$12);
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
            target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().find_attribute_user(FAX$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.Phone)get_store().add_attribute_user(FAX$12);
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
            get_store().remove_attribute(FAX$12);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$14);
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
    public it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL xgetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL)get_store().find_attribute_user(EMAIL$14);
            return target;
        }
    }
    
    /**
     * True if has "E_MAIL" attribute
     */
    public boolean isSetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(EMAIL$14) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMAIL$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EMAIL$14);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "E_MAIL" attribute
     */
    public void xsetEMAIL(it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL)get_store().find_attribute_user(EMAIL$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL)get_store().add_attribute_user(EMAIL$14);
            }
            target.set(email);
        }
    }
    
    /**
     * Unsets the "E_MAIL" attribute
     */
    public void unsetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(EMAIL$14);
        }
    }
    
    /**
     * Gets the "URL_SA" attribute
     */
    public java.lang.String getURLSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLSA$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL_SA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA xgetURLSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA)get_store().find_attribute_user(URLSA$16);
            return target;
        }
    }
    
    /**
     * True if has "URL_SA" attribute
     */
    public boolean isSetURLSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(URLSA$16) != null;
        }
    }
    
    /**
     * Sets the "URL_SA" attribute
     */
    public void setURLSA(java.lang.String urlsa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLSA$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URLSA$16);
            }
            target.setStringValue(urlsa);
        }
    }
    
    /**
     * Sets (as xml) the "URL_SA" attribute
     */
    public void xsetURLSA(it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA urlsa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA)get_store().find_attribute_user(URLSA$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA)get_store().add_attribute_user(URLSA$16);
            }
            target.set(urlsa);
        }
    }
    
    /**
     * Unsets the "URL_SA" attribute
     */
    public void unsetURLSA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(URLSA$16);
        }
    }
    /**
     * An XML OFFICIALNAME(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS6Type$OFFICIALNAME.
     */
    public static class OFFICIALNAMEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS6Type.OFFICIALNAME
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
     * An XML ADDRESS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS6Type$ADDRESS.
     */
    public static class ADDRESSImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS6Type.ADDRESS
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
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS6Type$TOWN.
     */
    public static class TOWNImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS6Type.TOWN
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
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS6Type$POSTALCODE.
     */
    public static class POSTALCODEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS6Type.POSTALCODE
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
     * An XML E_MAIL(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS6Type$EMAIL.
     */
    public static class EMAILImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS6Type.EMAIL
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
     * An XML URL_SA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AddrS6Type$URLSA.
     */
    public static class URLSAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AddrS6Type.URLSA
    {
        
        public URLSAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLSAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
