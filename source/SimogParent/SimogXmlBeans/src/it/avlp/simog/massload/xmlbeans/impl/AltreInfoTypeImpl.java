/*
 * XML Type:  AltreInfoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.AltreInfoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML AltreInfoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class AltreInfoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.AltreInfoType
{
    
    public AltreInfoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ORGANISMORICORSO$0 = 
        new javax.xml.namespace.QName("", "ORGANISMO_RICORSO");
    private static final javax.xml.namespace.QName ORGANISMOMEDIAZIONE$2 = 
        new javax.xml.namespace.QName("", "ORGANISMO_MEDIAZIONE");
    private static final javax.xml.namespace.QName SERVIZIOINFORICORSO$4 = 
        new javax.xml.namespace.QName("", "SERVIZIO_INFO_RICORSO");
    private static final javax.xml.namespace.QName APPALTORINNOVABILE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "APPALTO_RINNOVABILE");
    private static final javax.xml.namespace.QName TEMPOSTIMATOPROSSIMIBANDI$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TEMPO_STIMATO_PROSSIMI_BANDI");
    private static final javax.xml.namespace.QName ORDINATIVOELETTRONICO$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ORDINATIVO_ELETTRONICO");
    private static final javax.xml.namespace.QName FATTURAZIONEELETTRONICA$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FATTURAZIONE_ELETTRONICA");
    private static final javax.xml.namespace.QName PAGAMENTIELETTRONICI$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PAGAMENTI_ELETTRONICI");
    private static final javax.xml.namespace.QName INFOADD$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INFO_ADD");
    private static final javax.xml.namespace.QName REVIEWPROCEDURE$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "REVIEW_PROCEDURE");
    
    
    /**
     * Gets the "ORGANISMO_RICORSO" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS6Type getORGANISMORICORSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().find_element_user(ORGANISMORICORSO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ORGANISMO_RICORSO" element
     */
    public void setORGANISMORICORSO(it.avlp.simog.massload.xmlbeans.AddrS6Type organismoricorso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().find_element_user(ORGANISMORICORSO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().add_element_user(ORGANISMORICORSO$0);
            }
            target.set(organismoricorso);
        }
    }
    
    /**
     * Appends and returns a new empty "ORGANISMO_RICORSO" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS6Type addNewORGANISMORICORSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().add_element_user(ORGANISMORICORSO$0);
            return target;
        }
    }
    
    /**
     * Gets the "ORGANISMO_MEDIAZIONE" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS6Type getORGANISMOMEDIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().find_element_user(ORGANISMOMEDIAZIONE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "ORGANISMO_MEDIAZIONE" element
     */
    public boolean isSetORGANISMOMEDIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ORGANISMOMEDIAZIONE$2) != 0;
        }
    }
    
    /**
     * Sets the "ORGANISMO_MEDIAZIONE" element
     */
    public void setORGANISMOMEDIAZIONE(it.avlp.simog.massload.xmlbeans.AddrS6Type organismomediazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().find_element_user(ORGANISMOMEDIAZIONE$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().add_element_user(ORGANISMOMEDIAZIONE$2);
            }
            target.set(organismomediazione);
        }
    }
    
    /**
     * Appends and returns a new empty "ORGANISMO_MEDIAZIONE" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS6Type addNewORGANISMOMEDIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().add_element_user(ORGANISMOMEDIAZIONE$2);
            return target;
        }
    }
    
    /**
     * Unsets the "ORGANISMO_MEDIAZIONE" element
     */
    public void unsetORGANISMOMEDIAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ORGANISMOMEDIAZIONE$2, 0);
        }
    }
    
    /**
     * Gets the "SERVIZIO_INFO_RICORSO" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS6Type getSERVIZIOINFORICORSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().find_element_user(SERVIZIOINFORICORSO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SERVIZIO_INFO_RICORSO" element
     */
    public boolean isSetSERVIZIOINFORICORSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SERVIZIOINFORICORSO$4) != 0;
        }
    }
    
    /**
     * Sets the "SERVIZIO_INFO_RICORSO" element
     */
    public void setSERVIZIOINFORICORSO(it.avlp.simog.massload.xmlbeans.AddrS6Type servizioinforicorso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().find_element_user(SERVIZIOINFORICORSO$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().add_element_user(SERVIZIOINFORICORSO$4);
            }
            target.set(servizioinforicorso);
        }
    }
    
    /**
     * Appends and returns a new empty "SERVIZIO_INFO_RICORSO" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS6Type addNewSERVIZIOINFORICORSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS6Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS6Type)get_store().add_element_user(SERVIZIOINFORICORSO$4);
            return target;
        }
    }
    
    /**
     * Unsets the "SERVIZIO_INFO_RICORSO" element
     */
    public void unsetSERVIZIOINFORICORSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SERVIZIOINFORICORSO$4, 0);
        }
    }
    
    /**
     * Gets the "APPALTO_RINNOVABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getAPPALTORINNOVABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(APPALTORINNOVABILE$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "APPALTO_RINNOVABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetAPPALTORINNOVABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(APPALTORINNOVABILE$6);
            return target;
        }
    }
    
    /**
     * Sets the "APPALTO_RINNOVABILE" attribute
     */
    public void setAPPALTORINNOVABILE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum appaltorinnovabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(APPALTORINNOVABILE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(APPALTORINNOVABILE$6);
            }
            target.setEnumValue(appaltorinnovabile);
        }
    }
    
    /**
     * Sets (as xml) the "APPALTO_RINNOVABILE" attribute
     */
    public void xsetAPPALTORINNOVABILE(it.avlp.simog.massload.xmlbeans.FlagSNType appaltorinnovabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(APPALTORINNOVABILE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(APPALTORINNOVABILE$6);
            }
            target.set(appaltorinnovabile);
        }
    }
    
    /**
     * Gets the "TEMPO_STIMATO_PROSSIMI_BANDI" attribute
     */
    public java.lang.String getTEMPOSTIMATOPROSSIMIBANDI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEMPOSTIMATOPROSSIMIBANDI$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TEMPO_STIMATO_PROSSIMI_BANDI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI xgetTEMPOSTIMATOPROSSIMIBANDI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI)get_store().find_attribute_user(TEMPOSTIMATOPROSSIMIBANDI$8);
            return target;
        }
    }
    
    /**
     * True if has "TEMPO_STIMATO_PROSSIMI_BANDI" attribute
     */
    public boolean isSetTEMPOSTIMATOPROSSIMIBANDI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TEMPOSTIMATOPROSSIMIBANDI$8) != null;
        }
    }
    
    /**
     * Sets the "TEMPO_STIMATO_PROSSIMI_BANDI" attribute
     */
    public void setTEMPOSTIMATOPROSSIMIBANDI(java.lang.String tempostimatoprossimibandi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEMPOSTIMATOPROSSIMIBANDI$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TEMPOSTIMATOPROSSIMIBANDI$8);
            }
            target.setStringValue(tempostimatoprossimibandi);
        }
    }
    
    /**
     * Sets (as xml) the "TEMPO_STIMATO_PROSSIMI_BANDI" attribute
     */
    public void xsetTEMPOSTIMATOPROSSIMIBANDI(it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI tempostimatoprossimibandi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI)get_store().find_attribute_user(TEMPOSTIMATOPROSSIMIBANDI$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI)get_store().add_attribute_user(TEMPOSTIMATOPROSSIMIBANDI$8);
            }
            target.set(tempostimatoprossimibandi);
        }
    }
    
    /**
     * Unsets the "TEMPO_STIMATO_PROSSIMI_BANDI" attribute
     */
    public void unsetTEMPOSTIMATOPROSSIMIBANDI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TEMPOSTIMATOPROSSIMIBANDI$8);
        }
    }
    
    /**
     * Gets the "ORDINATIVO_ELETTRONICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getORDINATIVOELETTRONICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORDINATIVOELETTRONICO$10);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ORDINATIVO_ELETTRONICO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetORDINATIVOELETTRONICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ORDINATIVOELETTRONICO$10);
            return target;
        }
    }
    
    /**
     * Sets the "ORDINATIVO_ELETTRONICO" attribute
     */
    public void setORDINATIVOELETTRONICO(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum ordinativoelettronico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORDINATIVOELETTRONICO$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORDINATIVOELETTRONICO$10);
            }
            target.setEnumValue(ordinativoelettronico);
        }
    }
    
    /**
     * Sets (as xml) the "ORDINATIVO_ELETTRONICO" attribute
     */
    public void xsetORDINATIVOELETTRONICO(it.avlp.simog.massload.xmlbeans.FlagSNType ordinativoelettronico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(ORDINATIVOELETTRONICO$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(ORDINATIVOELETTRONICO$10);
            }
            target.set(ordinativoelettronico);
        }
    }
    
    /**
     * Gets the "FATTURAZIONE_ELETTRONICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getFATTURAZIONEELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FATTURAZIONEELETTRONICA$12);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FATTURAZIONE_ELETTRONICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetFATTURAZIONEELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FATTURAZIONEELETTRONICA$12);
            return target;
        }
    }
    
    /**
     * Sets the "FATTURAZIONE_ELETTRONICA" attribute
     */
    public void setFATTURAZIONEELETTRONICA(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum fatturazioneelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FATTURAZIONEELETTRONICA$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FATTURAZIONEELETTRONICA$12);
            }
            target.setEnumValue(fatturazioneelettronica);
        }
    }
    
    /**
     * Sets (as xml) the "FATTURAZIONE_ELETTRONICA" attribute
     */
    public void xsetFATTURAZIONEELETTRONICA(it.avlp.simog.massload.xmlbeans.FlagSNType fatturazioneelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(FATTURAZIONEELETTRONICA$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(FATTURAZIONEELETTRONICA$12);
            }
            target.set(fatturazioneelettronica);
        }
    }
    
    /**
     * Gets the "PAGAMENTI_ELETTRONICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getPAGAMENTIELETTRONICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAGAMENTIELETTRONICI$14);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PAGAMENTI_ELETTRONICI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetPAGAMENTIELETTRONICI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PAGAMENTIELETTRONICI$14);
            return target;
        }
    }
    
    /**
     * Sets the "PAGAMENTI_ELETTRONICI" attribute
     */
    public void setPAGAMENTIELETTRONICI(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum pagamentielettronici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAGAMENTIELETTRONICI$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PAGAMENTIELETTRONICI$14);
            }
            target.setEnumValue(pagamentielettronici);
        }
    }
    
    /**
     * Sets (as xml) the "PAGAMENTI_ELETTRONICI" attribute
     */
    public void xsetPAGAMENTIELETTRONICI(it.avlp.simog.massload.xmlbeans.FlagSNType pagamentielettronici)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PAGAMENTIELETTRONICI$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(PAGAMENTIELETTRONICI$14);
            }
            target.set(pagamentielettronici);
        }
    }
    
    /**
     * Gets the "INFO_ADD" attribute
     */
    public java.lang.String getINFOADD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOADD$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "INFO_ADD" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD xgetINFOADD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD)get_store().find_attribute_user(INFOADD$16);
            return target;
        }
    }
    
    /**
     * True if has "INFO_ADD" attribute
     */
    public boolean isSetINFOADD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INFOADD$16) != null;
        }
    }
    
    /**
     * Sets the "INFO_ADD" attribute
     */
    public void setINFOADD(java.lang.String infoadd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOADD$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INFOADD$16);
            }
            target.setStringValue(infoadd);
        }
    }
    
    /**
     * Sets (as xml) the "INFO_ADD" attribute
     */
    public void xsetINFOADD(it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD infoadd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD)get_store().find_attribute_user(INFOADD$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD)get_store().add_attribute_user(INFOADD$16);
            }
            target.set(infoadd);
        }
    }
    
    /**
     * Unsets the "INFO_ADD" attribute
     */
    public void unsetINFOADD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INFOADD$16);
        }
    }
    
    /**
     * Gets the "REVIEW_PROCEDURE" attribute
     */
    public java.lang.String getREVIEWPROCEDURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REVIEWPROCEDURE$18);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "REVIEW_PROCEDURE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE xgetREVIEWPROCEDURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE)get_store().find_attribute_user(REVIEWPROCEDURE$18);
            return target;
        }
    }
    
    /**
     * True if has "REVIEW_PROCEDURE" attribute
     */
    public boolean isSetREVIEWPROCEDURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(REVIEWPROCEDURE$18) != null;
        }
    }
    
    /**
     * Sets the "REVIEW_PROCEDURE" attribute
     */
    public void setREVIEWPROCEDURE(java.lang.String reviewprocedure)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REVIEWPROCEDURE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(REVIEWPROCEDURE$18);
            }
            target.setStringValue(reviewprocedure);
        }
    }
    
    /**
     * Sets (as xml) the "REVIEW_PROCEDURE" attribute
     */
    public void xsetREVIEWPROCEDURE(it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE reviewprocedure)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE)get_store().find_attribute_user(REVIEWPROCEDURE$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE)get_store().add_attribute_user(REVIEWPROCEDURE$18);
            }
            target.set(reviewprocedure);
        }
    }
    
    /**
     * Unsets the "REVIEW_PROCEDURE" attribute
     */
    public void unsetREVIEWPROCEDURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(REVIEWPROCEDURE$18);
        }
    }
    /**
     * An XML TEMPO_STIMATO_PROSSIMI_BANDI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AltreInfoType$TEMPOSTIMATOPROSSIMIBANDI.
     */
    public static class TEMPOSTIMATOPROSSIMIBANDIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AltreInfoType.TEMPOSTIMATOPROSSIMIBANDI
    {
        
        public TEMPOSTIMATOPROSSIMIBANDIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected TEMPOSTIMATOPROSSIMIBANDIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML INFO_ADD(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AltreInfoType$INFOADD.
     */
    public static class INFOADDImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AltreInfoType.INFOADD
    {
        
        public INFOADDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected INFOADDImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML REVIEW_PROCEDURE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.AltreInfoType$REVIEWPROCEDURE.
     */
    public static class REVIEWPROCEDUREImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.AltreInfoType.REVIEWPROCEDURE
    {
        
        public REVIEWPROCEDUREImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected REVIEWPROCEDUREImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
