/*
 * XML Type:  DeltaGaraTED
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.DeltaGaraTED
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML DeltaGaraTED(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class DeltaGaraTEDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.DeltaGaraTED
{
    
    public DeltaGaraTEDImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ENTITAAPPALTO$0 = 
        new javax.xml.namespace.QName("", "ENTITA_APPALTO");
    private static final javax.xml.namespace.QName DATIAMMAGGIUDICATRICE$2 = 
        new javax.xml.namespace.QName("", "DATI_AMM_AGGIUDICATRICE");
    private static final javax.xml.namespace.QName ALTROINDIRIZZOIA$4 = 
        new javax.xml.namespace.QName("", "ALTRO_INDIRIZZO_IA");
    private static final javax.xml.namespace.QName ALTROINDIRIZZOPARTECIPAZIONE$6 = 
        new javax.xml.namespace.QName("", "ALTRO_INDIRIZZO_PARTECIPAZIONE");
    private static final javax.xml.namespace.QName ALTREINFO$8 = 
        new javax.xml.namespace.QName("", "ALTRE_INFO");
    private static final javax.xml.namespace.QName CONDIZIONIPARTECIPAZIONE$10 = 
        new javax.xml.namespace.QName("", "CONDIZIONI_PARTECIPAZIONE");
    private static final javax.xml.namespace.QName DATIPROCEDURA$12 = 
        new javax.xml.namespace.QName("", "DATI_PROCEDURA");
    private static final javax.xml.namespace.QName INFOAMMINISTRATIVE$14 = 
        new javax.xml.namespace.QName("", "INFO_AMMINISTRATIVE");
    private static final javax.xml.namespace.QName NORMATIVEAPPCONGIUNTO$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NORMATIVE_APP_CONGIUNTO");
    private static final javax.xml.namespace.QName APPALTOCC$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "APPALTO_CC");
    private static final javax.xml.namespace.QName DOCUMENTIDISPONIBILI$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DOCUMENTI_DISPONIBILI");
    private static final javax.xml.namespace.QName URLDOCDISPONIBILI$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL_DOC_DISPONIBILI");
    private static final javax.xml.namespace.QName INFOAGGIUNTIVE$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INFO_AGGIUNTIVE");
    private static final javax.xml.namespace.QName URLVERSIONEELETTRONICA$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL_VERSIONE_ELETTRONICA");
    private static final javax.xml.namespace.QName URLSTRUMENTI$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "URL_STRUMENTI");
    private static final javax.xml.namespace.QName TIPOAMMAGG$30 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TIPO_AMM_AGG");
    private static final javax.xml.namespace.QName ALTROTIPOAMMAGG$32 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ALTRO_TIPO_AMM_AGG");
    private static final javax.xml.namespace.QName SETTOREPRINCIPALE$34 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SETTORE_PRINCIPALE");
    private static final javax.xml.namespace.QName ALTROSETTOREPRINCIPALE$36 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ALTRO_SETTORE_PRINCIPALE");
    
    
    /**
     * Gets the "ENTITA_APPALTO" element
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType getENTITAAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType)get_store().find_element_user(ENTITAAPPALTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ENTITA_APPALTO" element
     */
    public void setENTITAAPPALTO(it.avlp.simog.massload.xmlbeans.EntitaAppaltoType entitaappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType)get_store().find_element_user(ENTITAAPPALTO$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType)get_store().add_element_user(ENTITAAPPALTO$0);
            }
            target.set(entitaappalto);
        }
    }
    
    /**
     * Appends and returns a new empty "ENTITA_APPALTO" element
     */
    public it.avlp.simog.massload.xmlbeans.EntitaAppaltoType addNewENTITAAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.EntitaAppaltoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.EntitaAppaltoType)get_store().add_element_user(ENTITAAPPALTO$0);
            return target;
        }
    }
    
    /**
     * Gets array of all "DATI_AMM_AGGIUDICATRICE" elements
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type[] getDATIAMMAGGIUDICATRICEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DATIAMMAGGIUDICATRICE$2, targetList);
            it.avlp.simog.massload.xmlbeans.AddrS1Type[] result = new it.avlp.simog.massload.xmlbeans.AddrS1Type[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "DATI_AMM_AGGIUDICATRICE" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type getDATIAMMAGGIUDICATRICEArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().find_element_user(DATIAMMAGGIUDICATRICE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "DATI_AMM_AGGIUDICATRICE" element
     */
    public int sizeOfDATIAMMAGGIUDICATRICEArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIAMMAGGIUDICATRICE$2);
        }
    }
    
    /**
     * Sets array of all "DATI_AMM_AGGIUDICATRICE" element
     */
    public void setDATIAMMAGGIUDICATRICEArray(it.avlp.simog.massload.xmlbeans.AddrS1Type[] datiammaggiudicatriceArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(datiammaggiudicatriceArray, DATIAMMAGGIUDICATRICE$2);
        }
    }
    
    /**
     * Sets ith "DATI_AMM_AGGIUDICATRICE" element
     */
    public void setDATIAMMAGGIUDICATRICEArray(int i, it.avlp.simog.massload.xmlbeans.AddrS1Type datiammaggiudicatrice)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().find_element_user(DATIAMMAGGIUDICATRICE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(datiammaggiudicatrice);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "DATI_AMM_AGGIUDICATRICE" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type insertNewDATIAMMAGGIUDICATRICE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().insert_element_user(DATIAMMAGGIUDICATRICE$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "DATI_AMM_AGGIUDICATRICE" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type addNewDATIAMMAGGIUDICATRICE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().add_element_user(DATIAMMAGGIUDICATRICE$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "DATI_AMM_AGGIUDICATRICE" element
     */
    public void removeDATIAMMAGGIUDICATRICE(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIAMMAGGIUDICATRICE$2, i);
        }
    }
    
    /**
     * Gets the "ALTRO_INDIRIZZO_IA" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type getALTROINDIRIZZOIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().find_element_user(ALTROINDIRIZZOIA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "ALTRO_INDIRIZZO_IA" element
     */
    public boolean isSetALTROINDIRIZZOIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ALTROINDIRIZZOIA$4) != 0;
        }
    }
    
    /**
     * Sets the "ALTRO_INDIRIZZO_IA" element
     */
    public void setALTROINDIRIZZOIA(it.avlp.simog.massload.xmlbeans.AddrS1Type altroindirizzoia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().find_element_user(ALTROINDIRIZZOIA$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().add_element_user(ALTROINDIRIZZOIA$4);
            }
            target.set(altroindirizzoia);
        }
    }
    
    /**
     * Appends and returns a new empty "ALTRO_INDIRIZZO_IA" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type addNewALTROINDIRIZZOIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().add_element_user(ALTROINDIRIZZOIA$4);
            return target;
        }
    }
    
    /**
     * Unsets the "ALTRO_INDIRIZZO_IA" element
     */
    public void unsetALTROINDIRIZZOIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ALTROINDIRIZZOIA$4, 0);
        }
    }
    
    /**
     * Gets the "ALTRO_INDIRIZZO_PARTECIPAZIONE" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type getALTROINDIRIZZOPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().find_element_user(ALTROINDIRIZZOPARTECIPAZIONE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "ALTRO_INDIRIZZO_PARTECIPAZIONE" element
     */
    public boolean isSetALTROINDIRIZZOPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ALTROINDIRIZZOPARTECIPAZIONE$6) != 0;
        }
    }
    
    /**
     * Sets the "ALTRO_INDIRIZZO_PARTECIPAZIONE" element
     */
    public void setALTROINDIRIZZOPARTECIPAZIONE(it.avlp.simog.massload.xmlbeans.AddrS1Type altroindirizzopartecipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().find_element_user(ALTROINDIRIZZOPARTECIPAZIONE$6, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().add_element_user(ALTROINDIRIZZOPARTECIPAZIONE$6);
            }
            target.set(altroindirizzopartecipazione);
        }
    }
    
    /**
     * Appends and returns a new empty "ALTRO_INDIRIZZO_PARTECIPAZIONE" element
     */
    public it.avlp.simog.massload.xmlbeans.AddrS1Type addNewALTROINDIRIZZOPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AddrS1Type target = null;
            target = (it.avlp.simog.massload.xmlbeans.AddrS1Type)get_store().add_element_user(ALTROINDIRIZZOPARTECIPAZIONE$6);
            return target;
        }
    }
    
    /**
     * Unsets the "ALTRO_INDIRIZZO_PARTECIPAZIONE" element
     */
    public void unsetALTROINDIRIZZOPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ALTROINDIRIZZOPARTECIPAZIONE$6, 0);
        }
    }
    
    /**
     * Gets the "ALTRE_INFO" element
     */
    public it.avlp.simog.massload.xmlbeans.AltreInfoType getALTREINFO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType)get_store().find_element_user(ALTREINFO$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ALTRE_INFO" element
     */
    public void setALTREINFO(it.avlp.simog.massload.xmlbeans.AltreInfoType altreinfo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType)get_store().find_element_user(ALTREINFO$8, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AltreInfoType)get_store().add_element_user(ALTREINFO$8);
            }
            target.set(altreinfo);
        }
    }
    
    /**
     * Appends and returns a new empty "ALTRE_INFO" element
     */
    public it.avlp.simog.massload.xmlbeans.AltreInfoType addNewALTREINFO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltreInfoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltreInfoType)get_store().add_element_user(ALTREINFO$8);
            return target;
        }
    }
    
    /**
     * Gets the "CONDIZIONI_PARTECIPAZIONE" element
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType getCONDIZIONIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType)get_store().find_element_user(CONDIZIONIPARTECIPAZIONE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "CONDIZIONI_PARTECIPAZIONE" element
     */
    public boolean isSetCONDIZIONIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONDIZIONIPARTECIPAZIONE$10) != 0;
        }
    }
    
    /**
     * Sets the "CONDIZIONI_PARTECIPAZIONE" element
     */
    public void setCONDIZIONIPARTECIPAZIONE(it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType condizionipartecipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType)get_store().find_element_user(CONDIZIONIPARTECIPAZIONE$10, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType)get_store().add_element_user(CONDIZIONIPARTECIPAZIONE$10);
            }
            target.set(condizionipartecipazione);
        }
    }
    
    /**
     * Appends and returns a new empty "CONDIZIONI_PARTECIPAZIONE" element
     */
    public it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType addNewCONDIZIONIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType)get_store().add_element_user(CONDIZIONIPARTECIPAZIONE$10);
            return target;
        }
    }
    
    /**
     * Unsets the "CONDIZIONI_PARTECIPAZIONE" element
     */
    public void unsetCONDIZIONIPARTECIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONDIZIONIPARTECIPAZIONE$10, 0);
        }
    }
    
    /**
     * Gets the "DATI_PROCEDURA" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType getDATIPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType)get_store().find_element_user(DATIPROCEDURA$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_PROCEDURA" element
     */
    public void setDATIPROCEDURA(it.avlp.simog.massload.xmlbeans.DatiProceduraType datiprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType)get_store().find_element_user(DATIPROCEDURA$12, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType)get_store().add_element_user(DATIPROCEDURA$12);
            }
            target.set(datiprocedura);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_PROCEDURA" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiProceduraType addNewDATIPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiProceduraType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiProceduraType)get_store().add_element_user(DATIPROCEDURA$12);
            return target;
        }
    }
    
    /**
     * Gets the "INFO_AMMINISTRATIVE" element
     */
    public it.avlp.simog.massload.xmlbeans.InfoAmministrativeType getINFOAMMINISTRATIVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType)get_store().find_element_user(INFOAMMINISTRATIVE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "INFO_AMMINISTRATIVE" element
     */
    public void setINFOAMMINISTRATIVE(it.avlp.simog.massload.xmlbeans.InfoAmministrativeType infoamministrative)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType)get_store().find_element_user(INFOAMMINISTRATIVE$14, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType)get_store().add_element_user(INFOAMMINISTRATIVE$14);
            }
            target.set(infoamministrative);
        }
    }
    
    /**
     * Appends and returns a new empty "INFO_AMMINISTRATIVE" element
     */
    public it.avlp.simog.massload.xmlbeans.InfoAmministrativeType addNewINFOAMMINISTRATIVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InfoAmministrativeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InfoAmministrativeType)get_store().add_element_user(INFOAMMINISTRATIVE$14);
            return target;
        }
    }
    
    /**
     * Gets the "NORMATIVE_APP_CONGIUNTO" attribute
     */
    public java.lang.String getNORMATIVEAPPCONGIUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NORMATIVEAPPCONGIUNTO$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NORMATIVE_APP_CONGIUNTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO xgetNORMATIVEAPPCONGIUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO)get_store().find_attribute_user(NORMATIVEAPPCONGIUNTO$16);
            return target;
        }
    }
    
    /**
     * True if has "NORMATIVE_APP_CONGIUNTO" attribute
     */
    public boolean isSetNORMATIVEAPPCONGIUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(NORMATIVEAPPCONGIUNTO$16) != null;
        }
    }
    
    /**
     * Sets the "NORMATIVE_APP_CONGIUNTO" attribute
     */
    public void setNORMATIVEAPPCONGIUNTO(java.lang.String normativeappcongiunto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NORMATIVEAPPCONGIUNTO$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NORMATIVEAPPCONGIUNTO$16);
            }
            target.setStringValue(normativeappcongiunto);
        }
    }
    
    /**
     * Sets (as xml) the "NORMATIVE_APP_CONGIUNTO" attribute
     */
    public void xsetNORMATIVEAPPCONGIUNTO(it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO normativeappcongiunto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO)get_store().find_attribute_user(NORMATIVEAPPCONGIUNTO$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO)get_store().add_attribute_user(NORMATIVEAPPCONGIUNTO$16);
            }
            target.set(normativeappcongiunto);
        }
    }
    
    /**
     * Unsets the "NORMATIVE_APP_CONGIUNTO" attribute
     */
    public void unsetNORMATIVEAPPCONGIUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(NORMATIVEAPPCONGIUNTO$16);
        }
    }
    
    /**
     * Gets the "APPALTO_CC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getAPPALTOCC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(APPALTOCC$18);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "APPALTO_CC" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetAPPALTOCC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(APPALTOCC$18);
            return target;
        }
    }
    
    /**
     * Sets the "APPALTO_CC" attribute
     */
    public void setAPPALTOCC(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum appaltocc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(APPALTOCC$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(APPALTOCC$18);
            }
            target.setEnumValue(appaltocc);
        }
    }
    
    /**
     * Sets (as xml) the "APPALTO_CC" attribute
     */
    public void xsetAPPALTOCC(it.avlp.simog.massload.xmlbeans.FlagSNType appaltocc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(APPALTOCC$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(APPALTOCC$18);
            }
            target.set(appaltocc);
        }
    }
    
    /**
     * Gets the "DOCUMENTI_DISPONIBILI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DocDisponibiliType.Enum getDOCUMENTIDISPONIBILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DOCUMENTIDISPONIBILI$20);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.DocDisponibiliType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "DOCUMENTI_DISPONIBILI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DocDisponibiliType xgetDOCUMENTIDISPONIBILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DocDisponibiliType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DocDisponibiliType)get_store().find_attribute_user(DOCUMENTIDISPONIBILI$20);
            return target;
        }
    }
    
    /**
     * Sets the "DOCUMENTI_DISPONIBILI" attribute
     */
    public void setDOCUMENTIDISPONIBILI(it.avlp.simog.massload.xmlbeans.DocDisponibiliType.Enum documentidisponibili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DOCUMENTIDISPONIBILI$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DOCUMENTIDISPONIBILI$20);
            }
            target.setEnumValue(documentidisponibili);
        }
    }
    
    /**
     * Sets (as xml) the "DOCUMENTI_DISPONIBILI" attribute
     */
    public void xsetDOCUMENTIDISPONIBILI(it.avlp.simog.massload.xmlbeans.DocDisponibiliType documentidisponibili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DocDisponibiliType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DocDisponibiliType)get_store().find_attribute_user(DOCUMENTIDISPONIBILI$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DocDisponibiliType)get_store().add_attribute_user(DOCUMENTIDISPONIBILI$20);
            }
            target.set(documentidisponibili);
        }
    }
    
    /**
     * Gets the "URL_DOC_DISPONIBILI" attribute
     */
    public java.lang.String getURLDOCDISPONIBILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLDOCDISPONIBILI$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL_DOC_DISPONIBILI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI xgetURLDOCDISPONIBILI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI)get_store().find_attribute_user(URLDOCDISPONIBILI$22);
            return target;
        }
    }
    
    /**
     * Sets the "URL_DOC_DISPONIBILI" attribute
     */
    public void setURLDOCDISPONIBILI(java.lang.String urldocdisponibili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLDOCDISPONIBILI$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URLDOCDISPONIBILI$22);
            }
            target.setStringValue(urldocdisponibili);
        }
    }
    
    /**
     * Sets (as xml) the "URL_DOC_DISPONIBILI" attribute
     */
    public void xsetURLDOCDISPONIBILI(it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI urldocdisponibili)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI)get_store().find_attribute_user(URLDOCDISPONIBILI$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI)get_store().add_attribute_user(URLDOCDISPONIBILI$22);
            }
            target.set(urldocdisponibili);
        }
    }
    
    /**
     * Gets the "INFO_AGGIUNTIVE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AltroIndirizzoType.Enum getINFOAGGIUNTIVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOAGGIUNTIVE$24);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.AltroIndirizzoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "INFO_AGGIUNTIVE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AltroIndirizzoType xgetINFOAGGIUNTIVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltroIndirizzoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltroIndirizzoType)get_store().find_attribute_user(INFOAGGIUNTIVE$24);
            return target;
        }
    }
    
    /**
     * Sets the "INFO_AGGIUNTIVE" attribute
     */
    public void setINFOAGGIUNTIVE(it.avlp.simog.massload.xmlbeans.AltroIndirizzoType.Enum infoaggiuntive)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOAGGIUNTIVE$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INFOAGGIUNTIVE$24);
            }
            target.setEnumValue(infoaggiuntive);
        }
    }
    
    /**
     * Sets (as xml) the "INFO_AGGIUNTIVE" attribute
     */
    public void xsetINFOAGGIUNTIVE(it.avlp.simog.massload.xmlbeans.AltroIndirizzoType infoaggiuntive)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AltroIndirizzoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AltroIndirizzoType)get_store().find_attribute_user(INFOAGGIUNTIVE$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AltroIndirizzoType)get_store().add_attribute_user(INFOAGGIUNTIVE$24);
            }
            target.set(infoaggiuntive);
        }
    }
    
    /**
     * Gets the "URL_VERSIONE_ELETTRONICA" attribute
     */
    public java.lang.String getURLVERSIONEELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLVERSIONEELETTRONICA$26);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL_VERSIONE_ELETTRONICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA xgetURLVERSIONEELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA)get_store().find_attribute_user(URLVERSIONEELETTRONICA$26);
            return target;
        }
    }
    
    /**
     * True if has "URL_VERSIONE_ELETTRONICA" attribute
     */
    public boolean isSetURLVERSIONEELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(URLVERSIONEELETTRONICA$26) != null;
        }
    }
    
    /**
     * Sets the "URL_VERSIONE_ELETTRONICA" attribute
     */
    public void setURLVERSIONEELETTRONICA(java.lang.String urlversioneelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLVERSIONEELETTRONICA$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URLVERSIONEELETTRONICA$26);
            }
            target.setStringValue(urlversioneelettronica);
        }
    }
    
    /**
     * Sets (as xml) the "URL_VERSIONE_ELETTRONICA" attribute
     */
    public void xsetURLVERSIONEELETTRONICA(it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA urlversioneelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA)get_store().find_attribute_user(URLVERSIONEELETTRONICA$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA)get_store().add_attribute_user(URLVERSIONEELETTRONICA$26);
            }
            target.set(urlversioneelettronica);
        }
    }
    
    /**
     * Unsets the "URL_VERSIONE_ELETTRONICA" attribute
     */
    public void unsetURLVERSIONEELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(URLVERSIONEELETTRONICA$26);
        }
    }
    
    /**
     * Gets the "URL_STRUMENTI" attribute
     */
    public java.lang.String getURLSTRUMENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLSTRUMENTI$28);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "URL_STRUMENTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI xgetURLSTRUMENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI)get_store().find_attribute_user(URLSTRUMENTI$28);
            return target;
        }
    }
    
    /**
     * True if has "URL_STRUMENTI" attribute
     */
    public boolean isSetURLSTRUMENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(URLSTRUMENTI$28) != null;
        }
    }
    
    /**
     * Sets the "URL_STRUMENTI" attribute
     */
    public void setURLSTRUMENTI(java.lang.String urlstrumenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(URLSTRUMENTI$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(URLSTRUMENTI$28);
            }
            target.setStringValue(urlstrumenti);
        }
    }
    
    /**
     * Sets (as xml) the "URL_STRUMENTI" attribute
     */
    public void xsetURLSTRUMENTI(it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI urlstrumenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI)get_store().find_attribute_user(URLSTRUMENTI$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI)get_store().add_attribute_user(URLSTRUMENTI$28);
            }
            target.set(urlstrumenti);
        }
    }
    
    /**
     * Unsets the "URL_STRUMENTI" attribute
     */
    public void unsetURLSTRUMENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(URLSTRUMENTI$28);
        }
    }
    
    /**
     * Gets the "TIPO_AMM_AGG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AmmAggType.Enum getTIPOAMMAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOAMMAGG$30);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.AmmAggType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_AMM_AGG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.AmmAggType xgetTIPOAMMAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AmmAggType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AmmAggType)get_store().find_attribute_user(TIPOAMMAGG$30);
            return target;
        }
    }
    
    /**
     * Sets the "TIPO_AMM_AGG" attribute
     */
    public void setTIPOAMMAGG(it.avlp.simog.massload.xmlbeans.AmmAggType.Enum tipoammagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TIPOAMMAGG$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TIPOAMMAGG$30);
            }
            target.setEnumValue(tipoammagg);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_AMM_AGG" attribute
     */
    public void xsetTIPOAMMAGG(it.avlp.simog.massload.xmlbeans.AmmAggType tipoammagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AmmAggType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AmmAggType)get_store().find_attribute_user(TIPOAMMAGG$30);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AmmAggType)get_store().add_attribute_user(TIPOAMMAGG$30);
            }
            target.set(tipoammagg);
        }
    }
    
    /**
     * Gets the "ALTRO_TIPO_AMM_AGG" attribute
     */
    public java.lang.String getALTROTIPOAMMAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTROTIPOAMMAGG$32);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ALTRO_TIPO_AMM_AGG" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG xgetALTROTIPOAMMAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG)get_store().find_attribute_user(ALTROTIPOAMMAGG$32);
            return target;
        }
    }
    
    /**
     * True if has "ALTRO_TIPO_AMM_AGG" attribute
     */
    public boolean isSetALTROTIPOAMMAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ALTROTIPOAMMAGG$32) != null;
        }
    }
    
    /**
     * Sets the "ALTRO_TIPO_AMM_AGG" attribute
     */
    public void setALTROTIPOAMMAGG(java.lang.String altrotipoammagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTROTIPOAMMAGG$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ALTROTIPOAMMAGG$32);
            }
            target.setStringValue(altrotipoammagg);
        }
    }
    
    /**
     * Sets (as xml) the "ALTRO_TIPO_AMM_AGG" attribute
     */
    public void xsetALTROTIPOAMMAGG(it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG altrotipoammagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG)get_store().find_attribute_user(ALTROTIPOAMMAGG$32);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG)get_store().add_attribute_user(ALTROTIPOAMMAGG$32);
            }
            target.set(altrotipoammagg);
        }
    }
    
    /**
     * Unsets the "ALTRO_TIPO_AMM_AGG" attribute
     */
    public void unsetALTROTIPOAMMAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ALTROTIPOAMMAGG$32);
        }
    }
    
    /**
     * Gets the "SETTORE_PRINCIPALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.SettorePrincipaleType.Enum getSETTOREPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTOREPRINCIPALE$34);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.SettorePrincipaleType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SETTORE_PRINCIPALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.SettorePrincipaleType xgetSETTOREPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SettorePrincipaleType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SettorePrincipaleType)get_store().find_attribute_user(SETTOREPRINCIPALE$34);
            return target;
        }
    }
    
    /**
     * Sets the "SETTORE_PRINCIPALE" attribute
     */
    public void setSETTOREPRINCIPALE(it.avlp.simog.massload.xmlbeans.SettorePrincipaleType.Enum settoreprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTOREPRINCIPALE$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SETTOREPRINCIPALE$34);
            }
            target.setEnumValue(settoreprincipale);
        }
    }
    
    /**
     * Sets (as xml) the "SETTORE_PRINCIPALE" attribute
     */
    public void xsetSETTOREPRINCIPALE(it.avlp.simog.massload.xmlbeans.SettorePrincipaleType settoreprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SettorePrincipaleType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SettorePrincipaleType)get_store().find_attribute_user(SETTOREPRINCIPALE$34);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SettorePrincipaleType)get_store().add_attribute_user(SETTOREPRINCIPALE$34);
            }
            target.set(settoreprincipale);
        }
    }
    
    /**
     * Gets the "ALTRO_SETTORE_PRINCIPALE" attribute
     */
    public java.lang.String getALTROSETTOREPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTROSETTOREPRINCIPALE$36);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ALTRO_SETTORE_PRINCIPALE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE xgetALTROSETTOREPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE)get_store().find_attribute_user(ALTROSETTOREPRINCIPALE$36);
            return target;
        }
    }
    
    /**
     * True if has "ALTRO_SETTORE_PRINCIPALE" attribute
     */
    public boolean isSetALTROSETTOREPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ALTROSETTOREPRINCIPALE$36) != null;
        }
    }
    
    /**
     * Sets the "ALTRO_SETTORE_PRINCIPALE" attribute
     */
    public void setALTROSETTOREPRINCIPALE(java.lang.String altrosettoreprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTROSETTOREPRINCIPALE$36);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ALTROSETTOREPRINCIPALE$36);
            }
            target.setStringValue(altrosettoreprincipale);
        }
    }
    
    /**
     * Sets (as xml) the "ALTRO_SETTORE_PRINCIPALE" attribute
     */
    public void xsetALTROSETTOREPRINCIPALE(it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE altrosettoreprincipale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE target = null;
            target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE)get_store().find_attribute_user(ALTROSETTOREPRINCIPALE$36);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE)get_store().add_attribute_user(ALTROSETTOREPRINCIPALE$36);
            }
            target.set(altrosettoreprincipale);
        }
    }
    
    /**
     * Unsets the "ALTRO_SETTORE_PRINCIPALE" attribute
     */
    public void unsetALTROSETTOREPRINCIPALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ALTROSETTOREPRINCIPALE$36);
        }
    }
    /**
     * An XML NORMATIVE_APP_CONGIUNTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DeltaGaraTED$NORMATIVEAPPCONGIUNTO.
     */
    public static class NORMATIVEAPPCONGIUNTOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DeltaGaraTED.NORMATIVEAPPCONGIUNTO
    {
        
        public NORMATIVEAPPCONGIUNTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected NORMATIVEAPPCONGIUNTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML URL_DOC_DISPONIBILI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DeltaGaraTED$URLDOCDISPONIBILI.
     */
    public static class URLDOCDISPONIBILIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLDOCDISPONIBILI
    {
        
        public URLDOCDISPONIBILIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLDOCDISPONIBILIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML URL_VERSIONE_ELETTRONICA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DeltaGaraTED$URLVERSIONEELETTRONICA.
     */
    public static class URLVERSIONEELETTRONICAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLVERSIONEELETTRONICA
    {
        
        public URLVERSIONEELETTRONICAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLVERSIONEELETTRONICAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML URL_STRUMENTI(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DeltaGaraTED$URLSTRUMENTI.
     */
    public static class URLSTRUMENTIImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DeltaGaraTED.URLSTRUMENTI
    {
        
        public URLSTRUMENTIImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected URLSTRUMENTIImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML ALTRO_TIPO_AMM_AGG(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DeltaGaraTED$ALTROTIPOAMMAGG.
     */
    public static class ALTROTIPOAMMAGGImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROTIPOAMMAGG
    {
        
        public ALTROTIPOAMMAGGImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ALTROTIPOAMMAGGImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
    /**
     * An XML ALTRO_SETTORE_PRINCIPALE(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.DeltaGaraTED$ALTROSETTOREPRINCIPALE.
     */
    public static class ALTROSETTOREPRINCIPALEImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.DeltaGaraTED.ALTROSETTOREPRINCIPALE
    {
        
        public ALTROSETTOREPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ALTROSETTOREPRINCIPALEImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
