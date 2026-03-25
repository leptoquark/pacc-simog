/*
 * XML Type:  SchedaCompletaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.SchedaCompletaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML SchedaCompletaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class SchedaCompletaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.SchedaCompletaType
{
    
    public SchedaCompletaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CUI$0 = 
        new javax.xml.namespace.QName("", "CUI");
    private static final javax.xml.namespace.QName AGGIUDICAZIONE$2 = 
        new javax.xml.namespace.QName("", "Aggiudicazione");
    private static final javax.xml.namespace.QName SOTTOSOGLIA$4 = 
        new javax.xml.namespace.QName("", "Sottosoglia");
    private static final javax.xml.namespace.QName ESCLUSO$6 = 
        new javax.xml.namespace.QName("", "Escluso");
    private static final javax.xml.namespace.QName ADESIONE$8 = 
        new javax.xml.namespace.QName("", "Adesione");
    private static final javax.xml.namespace.QName DATIINIZIO$10 = 
        new javax.xml.namespace.QName("", "DatiInizio");
    private static final javax.xml.namespace.QName DATISTIPULA$12 = 
        new javax.xml.namespace.QName("", "DatiStipula");
    private static final javax.xml.namespace.QName DATIAVANZAMENTI$14 = 
        new javax.xml.namespace.QName("", "DatiAvanzamenti");
    private static final javax.xml.namespace.QName DATICONCLUSIONE$16 = 
        new javax.xml.namespace.QName("", "DatiConclusione");
    private static final javax.xml.namespace.QName DATICOLLAUDO$18 = 
        new javax.xml.namespace.QName("", "DatiCollaudo");
    private static final javax.xml.namespace.QName DATIRITARDI$20 = 
        new javax.xml.namespace.QName("", "DatiRitardi");
    private static final javax.xml.namespace.QName DATIACCORDI$22 = 
        new javax.xml.namespace.QName("", "DatiAccordi");
    private static final javax.xml.namespace.QName DATISOSPENSIONI$24 = 
        new javax.xml.namespace.QName("", "DatiSospensioni");
    private static final javax.xml.namespace.QName DATIVARIANTI$26 = 
        new javax.xml.namespace.QName("", "DatiVarianti");
    private static final javax.xml.namespace.QName DATISUBAPPALTI$28 = 
        new javax.xml.namespace.QName("", "DatiSubappalti");
    
    
    /**
     * Gets the "CUI" element
     */
    public java.lang.String getCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CUI" element
     */
    public it.avlp.simog.massload.xmlbeans.CuiType xgetCUI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_element_user(CUI$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CUI" element
     */
    public void setCUI(java.lang.String cui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUI$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUI$0);
            }
            target.setStringValue(cui);
        }
    }
    
    /**
     * Sets (as xml) the "CUI" element
     */
    public void xsetCUI(it.avlp.simog.massload.xmlbeans.CuiType cui)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CuiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().find_element_user(CUI$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CuiType)get_store().add_element_user(CUI$0);
            }
            target.set(cui);
        }
    }
    
    /**
     * Gets the "Aggiudicazione" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType getAggiudicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().find_element_user(AGGIUDICAZIONE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Aggiudicazione" element
     */
    public boolean isSetAggiudicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGGIUDICAZIONE$2) != 0;
        }
    }
    
    /**
     * Sets the "Aggiudicazione" element
     */
    public void setAggiudicazione(it.avlp.simog.massload.xmlbeans.AggiudicazioneType aggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().find_element_user(AGGIUDICAZIONE$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().add_element_user(AGGIUDICAZIONE$2);
            }
            target.set(aggiudicazione);
        }
    }
    
    /**
     * Appends and returns a new empty "Aggiudicazione" element
     */
    public it.avlp.simog.massload.xmlbeans.AggiudicazioneType addNewAggiudicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AggiudicazioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AggiudicazioneType)get_store().add_element_user(AGGIUDICAZIONE$2);
            return target;
        }
    }
    
    /**
     * Unsets the "Aggiudicazione" element
     */
    public void unsetAggiudicazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGGIUDICAZIONE$2, 0);
        }
    }
    
    /**
     * Gets the "Sottosoglia" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType getSottosoglia()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType)get_store().find_element_user(SOTTOSOGLIA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Sottosoglia" element
     */
    public boolean isSetSottosoglia()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SOTTOSOGLIA$4) != 0;
        }
    }
    
    /**
     * Sets the "Sottosoglia" element
     */
    public void setSottosoglia(it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType sottosoglia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType)get_store().find_element_user(SOTTOSOGLIA$4, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType)get_store().add_element_user(SOTTOSOGLIA$4);
            }
            target.set(sottosoglia);
        }
    }
    
    /**
     * Appends and returns a new empty "Sottosoglia" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType addNewSottosoglia()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType)get_store().add_element_user(SOTTOSOGLIA$4);
            return target;
        }
    }
    
    /**
     * Unsets the "Sottosoglia" element
     */
    public void unsetSottosoglia()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SOTTOSOGLIA$4, 0);
        }
    }
    
    /**
     * Gets the "Escluso" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaEsclusoType getEscluso()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaEsclusoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaEsclusoType)get_store().find_element_user(ESCLUSO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Escluso" element
     */
    public boolean isSetEscluso()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ESCLUSO$6) != 0;
        }
    }
    
    /**
     * Sets the "Escluso" element
     */
    public void setEscluso(it.avlp.simog.massload.xmlbeans.SchedaEsclusoType escluso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaEsclusoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaEsclusoType)get_store().find_element_user(ESCLUSO$6, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SchedaEsclusoType)get_store().add_element_user(ESCLUSO$6);
            }
            target.set(escluso);
        }
    }
    
    /**
     * Appends and returns a new empty "Escluso" element
     */
    public it.avlp.simog.massload.xmlbeans.SchedaEsclusoType addNewEscluso()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SchedaEsclusoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SchedaEsclusoType)get_store().add_element_user(ESCLUSO$6);
            return target;
        }
    }
    
    /**
     * Unsets the "Escluso" element
     */
    public void unsetEscluso()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ESCLUSO$6, 0);
        }
    }
    
    /**
     * Gets the "Adesione" element
     */
    public it.avlp.simog.massload.xmlbeans.AdesioneType getAdesione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AdesioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AdesioneType)get_store().find_element_user(ADESIONE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Adesione" element
     */
    public boolean isSetAdesione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADESIONE$8) != 0;
        }
    }
    
    /**
     * Sets the "Adesione" element
     */
    public void setAdesione(it.avlp.simog.massload.xmlbeans.AdesioneType adesione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AdesioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AdesioneType)get_store().find_element_user(ADESIONE$8, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AdesioneType)get_store().add_element_user(ADESIONE$8);
            }
            target.set(adesione);
        }
    }
    
    /**
     * Appends and returns a new empty "Adesione" element
     */
    public it.avlp.simog.massload.xmlbeans.AdesioneType addNewAdesione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AdesioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AdesioneType)get_store().add_element_user(ADESIONE$8);
            return target;
        }
    }
    
    /**
     * Unsets the "Adesione" element
     */
    public void unsetAdesione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADESIONE$8, 0);
        }
    }
    
    /**
     * Gets the "DatiInizio" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiInizioType getDatiInizio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiInizioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiInizioType)get_store().find_element_user(DATIINIZIO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiInizio" element
     */
    public boolean isSetDatiInizio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIINIZIO$10) != 0;
        }
    }
    
    /**
     * Sets the "DatiInizio" element
     */
    public void setDatiInizio(it.avlp.simog.massload.xmlbeans.DatiInizioType datiInizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiInizioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiInizioType)get_store().find_element_user(DATIINIZIO$10, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiInizioType)get_store().add_element_user(DATIINIZIO$10);
            }
            target.set(datiInizio);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiInizio" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiInizioType addNewDatiInizio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiInizioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiInizioType)get_store().add_element_user(DATIINIZIO$10);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiInizio" element
     */
    public void unsetDatiInizio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIINIZIO$10, 0);
        }
    }
    
    /**
     * Gets the "DatiStipula" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiStipulaType getDatiStipula()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiStipulaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiStipulaType)get_store().find_element_user(DATISTIPULA$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiStipula" element
     */
    public boolean isSetDatiStipula()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISTIPULA$12) != 0;
        }
    }
    
    /**
     * Sets the "DatiStipula" element
     */
    public void setDatiStipula(it.avlp.simog.massload.xmlbeans.DatiStipulaType datiStipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiStipulaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiStipulaType)get_store().find_element_user(DATISTIPULA$12, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiStipulaType)get_store().add_element_user(DATISTIPULA$12);
            }
            target.set(datiStipula);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiStipula" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiStipulaType addNewDatiStipula()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiStipulaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiStipulaType)get_store().add_element_user(DATISTIPULA$12);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiStipula" element
     */
    public void unsetDatiStipula()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISTIPULA$12, 0);
        }
    }
    
    /**
     * Gets the "DatiAvanzamenti" element
     */
    public it.avlp.simog.massload.xmlbeans.AvanzamentiType getDatiAvanzamenti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentiType)get_store().find_element_user(DATIAVANZAMENTI$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiAvanzamenti" element
     */
    public boolean isSetDatiAvanzamenti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIAVANZAMENTI$14) != 0;
        }
    }
    
    /**
     * Sets the "DatiAvanzamenti" element
     */
    public void setDatiAvanzamenti(it.avlp.simog.massload.xmlbeans.AvanzamentiType datiAvanzamenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentiType)get_store().find_element_user(DATIAVANZAMENTI$14, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AvanzamentiType)get_store().add_element_user(DATIAVANZAMENTI$14);
            }
            target.set(datiAvanzamenti);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiAvanzamenti" element
     */
    public it.avlp.simog.massload.xmlbeans.AvanzamentiType addNewDatiAvanzamenti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AvanzamentiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AvanzamentiType)get_store().add_element_user(DATIAVANZAMENTI$14);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiAvanzamenti" element
     */
    public void unsetDatiAvanzamenti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIAVANZAMENTI$14, 0);
        }
    }
    
    /**
     * Gets the "DatiConclusione" element
     */
    public it.avlp.simog.massload.xmlbeans.ConclusioneType getDatiConclusione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ConclusioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ConclusioneType)get_store().find_element_user(DATICONCLUSIONE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiConclusione" element
     */
    public boolean isSetDatiConclusione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATICONCLUSIONE$16) != 0;
        }
    }
    
    /**
     * Sets the "DatiConclusione" element
     */
    public void setDatiConclusione(it.avlp.simog.massload.xmlbeans.ConclusioneType datiConclusione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ConclusioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ConclusioneType)get_store().find_element_user(DATICONCLUSIONE$16, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ConclusioneType)get_store().add_element_user(DATICONCLUSIONE$16);
            }
            target.set(datiConclusione);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiConclusione" element
     */
    public it.avlp.simog.massload.xmlbeans.ConclusioneType addNewDatiConclusione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ConclusioneType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ConclusioneType)get_store().add_element_user(DATICONCLUSIONE$16);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiConclusione" element
     */
    public void unsetDatiConclusione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATICONCLUSIONE$16, 0);
        }
    }
    
    /**
     * Gets the "DatiCollaudo" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiCollaudoType getDatiCollaudo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCollaudoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCollaudoType)get_store().find_element_user(DATICOLLAUDO$18, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiCollaudo" element
     */
    public boolean isSetDatiCollaudo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATICOLLAUDO$18) != 0;
        }
    }
    
    /**
     * Sets the "DatiCollaudo" element
     */
    public void setDatiCollaudo(it.avlp.simog.massload.xmlbeans.DatiCollaudoType datiCollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCollaudoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCollaudoType)get_store().find_element_user(DATICOLLAUDO$18, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DatiCollaudoType)get_store().add_element_user(DATICOLLAUDO$18);
            }
            target.set(datiCollaudo);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiCollaudo" element
     */
    public it.avlp.simog.massload.xmlbeans.DatiCollaudoType addNewDatiCollaudo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DatiCollaudoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DatiCollaudoType)get_store().add_element_user(DATICOLLAUDO$18);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiCollaudo" element
     */
    public void unsetDatiCollaudo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATICOLLAUDO$18, 0);
        }
    }
    
    /**
     * Gets the "DatiRitardi" element
     */
    public it.avlp.simog.massload.xmlbeans.RitardiType getDatiRitardi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardiType)get_store().find_element_user(DATIRITARDI$20, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiRitardi" element
     */
    public boolean isSetDatiRitardi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIRITARDI$20) != 0;
        }
    }
    
    /**
     * Sets the "DatiRitardi" element
     */
    public void setDatiRitardi(it.avlp.simog.massload.xmlbeans.RitardiType datiRitardi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardiType)get_store().find_element_user(DATIRITARDI$20, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RitardiType)get_store().add_element_user(DATIRITARDI$20);
            }
            target.set(datiRitardi);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiRitardi" element
     */
    public it.avlp.simog.massload.xmlbeans.RitardiType addNewDatiRitardi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RitardiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RitardiType)get_store().add_element_user(DATIRITARDI$20);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiRitardi" element
     */
    public void unsetDatiRitardi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIRITARDI$20, 0);
        }
    }
    
    /**
     * Gets the "DatiAccordi" element
     */
    public it.avlp.simog.massload.xmlbeans.AccordiBonariType getDatiAccordi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AccordiBonariType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AccordiBonariType)get_store().find_element_user(DATIACCORDI$22, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiAccordi" element
     */
    public boolean isSetDatiAccordi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIACCORDI$22) != 0;
        }
    }
    
    /**
     * Sets the "DatiAccordi" element
     */
    public void setDatiAccordi(it.avlp.simog.massload.xmlbeans.AccordiBonariType datiAccordi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AccordiBonariType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AccordiBonariType)get_store().find_element_user(DATIACCORDI$22, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.AccordiBonariType)get_store().add_element_user(DATIACCORDI$22);
            }
            target.set(datiAccordi);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiAccordi" element
     */
    public it.avlp.simog.massload.xmlbeans.AccordiBonariType addNewDatiAccordi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.AccordiBonariType target = null;
            target = (it.avlp.simog.massload.xmlbeans.AccordiBonariType)get_store().add_element_user(DATIACCORDI$22);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiAccordi" element
     */
    public void unsetDatiAccordi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIACCORDI$22, 0);
        }
    }
    
    /**
     * Gets the "DatiSospensioni" element
     */
    public it.avlp.simog.massload.xmlbeans.SospensioniType getDatiSospensioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SospensioniType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SospensioniType)get_store().find_element_user(DATISOSPENSIONI$24, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiSospensioni" element
     */
    public boolean isSetDatiSospensioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISOSPENSIONI$24) != 0;
        }
    }
    
    /**
     * Sets the "DatiSospensioni" element
     */
    public void setDatiSospensioni(it.avlp.simog.massload.xmlbeans.SospensioniType datiSospensioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SospensioniType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SospensioniType)get_store().find_element_user(DATISOSPENSIONI$24, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SospensioniType)get_store().add_element_user(DATISOSPENSIONI$24);
            }
            target.set(datiSospensioni);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiSospensioni" element
     */
    public it.avlp.simog.massload.xmlbeans.SospensioniType addNewDatiSospensioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SospensioniType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SospensioniType)get_store().add_element_user(DATISOSPENSIONI$24);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiSospensioni" element
     */
    public void unsetDatiSospensioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISOSPENSIONI$24, 0);
        }
    }
    
    /**
     * Gets the "DatiVarianti" element
     */
    public it.avlp.simog.massload.xmlbeans.VariantiType getDatiVarianti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VariantiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VariantiType)get_store().find_element_user(DATIVARIANTI$26, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiVarianti" element
     */
    public boolean isSetDatiVarianti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIVARIANTI$26) != 0;
        }
    }
    
    /**
     * Sets the "DatiVarianti" element
     */
    public void setDatiVarianti(it.avlp.simog.massload.xmlbeans.VariantiType datiVarianti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VariantiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VariantiType)get_store().find_element_user(DATIVARIANTI$26, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.VariantiType)get_store().add_element_user(DATIVARIANTI$26);
            }
            target.set(datiVarianti);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiVarianti" element
     */
    public it.avlp.simog.massload.xmlbeans.VariantiType addNewDatiVarianti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.VariantiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.VariantiType)get_store().add_element_user(DATIVARIANTI$26);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiVarianti" element
     */
    public void unsetDatiVarianti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIVARIANTI$26, 0);
        }
    }
    
    /**
     * Gets the "DatiSubappalti" element
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltiType getDatiSubappalti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltiType)get_store().find_element_user(DATISUBAPPALTI$28, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DatiSubappalti" element
     */
    public boolean isSetDatiSubappalti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISUBAPPALTI$28) != 0;
        }
    }
    
    /**
     * Sets the "DatiSubappalti" element
     */
    public void setDatiSubappalti(it.avlp.simog.massload.xmlbeans.SubappaltiType datiSubappalti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltiType)get_store().find_element_user(DATISUBAPPALTI$28, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.SubappaltiType)get_store().add_element_user(DATISUBAPPALTI$28);
            }
            target.set(datiSubappalti);
        }
    }
    
    /**
     * Appends and returns a new empty "DatiSubappalti" element
     */
    public it.avlp.simog.massload.xmlbeans.SubappaltiType addNewDatiSubappalti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.SubappaltiType target = null;
            target = (it.avlp.simog.massload.xmlbeans.SubappaltiType)get_store().add_element_user(DATISUBAPPALTI$28);
            return target;
        }
    }
    
    /**
     * Unsets the "DatiSubappalti" element
     */
    public void unsetDatiSubappalti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISUBAPPALTI$28, 0);
        }
    }
}
