/*
 * XML Type:  OffertaType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.OffertaType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML OffertaType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class OffertaTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.OffertaType
{
    
    public OffertaTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATASCADENZAMANIFINTERESSE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_SCADENZA_MANIF_INTERESSE");
    private static final javax.xml.namespace.QName DATASCADENZARICHIESTAINVITO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_SCADENZA_RICHIESTA_INVITO");
    private static final javax.xml.namespace.QName DATAINVITO$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_INVITO");
    private static final javax.xml.namespace.QName DATASCADENZAPRESOFFERTA$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "DATA_SCADENZA_PRES_OFFERTA");
    private static final javax.xml.namespace.QName NUMMANIFINTERESSE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_MANIF_INTERESSE");
    private static final javax.xml.namespace.QName NUMIMPRESERICHIEDENTI$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_IMPRESE_RICHIEDENTI");
    private static final javax.xml.namespace.QName NUMIMPRESEINVITATE$12 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_IMPRESE_INVITATE");
    private static final javax.xml.namespace.QName NUMIMPRESEOFFERENTI$14 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_IMPRESE_OFFERENTI");
    private static final javax.xml.namespace.QName NUMOFFERTEAMMESSE$16 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_OFFERTE_AMMESSE");
    private static final javax.xml.namespace.QName OFFERTAMASSIMO$18 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OFFERTA_MASSIMO");
    private static final javax.xml.namespace.QName OFFERTAMINIMA$20 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OFFERTA_MINIMA");
    private static final javax.xml.namespace.QName VALSOGLIAANOMALIA$22 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_SOGLIA_ANOMALIA");
    private static final javax.xml.namespace.QName NUMOFFERTEFUORISOGLIA$24 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_OFFERTE_FUORI_SOGLIA");
    private static final javax.xml.namespace.QName NUMOFFERTEESCLUSE$26 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_OFFERTE_ESCLUSE");
    private static final javax.xml.namespace.QName NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$28 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI");
    
    
    /**
     * Gets the "DATA_SCADENZA_MANIF_INTERESSE" attribute
     */
    public java.util.Calendar getDATASCADENZAMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZAMANIFINTERESSE$0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_MANIF_INTERESSE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATASCADENZAMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZAMANIFINTERESSE$0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_SCADENZA_MANIF_INTERESSE" attribute
     */
    public void setDATASCADENZAMANIFINTERESSE(java.util.Calendar datascadenzamanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZAMANIFINTERESSE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATASCADENZAMANIFINTERESSE$0);
            }
            target.setCalendarValue(datascadenzamanifinteresse);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_MANIF_INTERESSE" attribute
     */
    public void xsetDATASCADENZAMANIFINTERESSE(it.avlp.simog.massload.xmlbeans.DbDateType datascadenzamanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZAMANIFINTERESSE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATASCADENZAMANIFINTERESSE$0);
            }
            target.set(datascadenzamanifinteresse);
        }
    }
    
    /**
     * Gets the "DATA_SCADENZA_RICHIESTA_INVITO" attribute
     */
    public java.util.Calendar getDATASCADENZARICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZARICHIESTAINVITO$2);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_RICHIESTA_INVITO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATASCADENZARICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZARICHIESTAINVITO$2);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_SCADENZA_RICHIESTA_INVITO" attribute
     */
    public void setDATASCADENZARICHIESTAINVITO(java.util.Calendar datascadenzarichiestainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZARICHIESTAINVITO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATASCADENZARICHIESTAINVITO$2);
            }
            target.setCalendarValue(datascadenzarichiestainvito);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_RICHIESTA_INVITO" attribute
     */
    public void xsetDATASCADENZARICHIESTAINVITO(it.avlp.simog.massload.xmlbeans.DbDateType datascadenzarichiestainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZARICHIESTAINVITO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATASCADENZARICHIESTAINVITO$2);
            }
            target.set(datascadenzarichiestainvito);
        }
    }
    
    /**
     * Gets the "DATA_INVITO" attribute
     */
    public java.util.Calendar getDATAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAINVITO$4);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INVITO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAINVITO$4);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_INVITO" attribute
     */
    public void setDATAINVITO(java.util.Calendar datainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATAINVITO$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATAINVITO$4);
            }
            target.setCalendarValue(datainvito);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INVITO" attribute
     */
    public void xsetDATAINVITO(it.avlp.simog.massload.xmlbeans.DbDateType datainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATAINVITO$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATAINVITO$4);
            }
            target.set(datainvito);
        }
    }
    
    /**
     * Gets the "DATA_SCADENZA_PRES_OFFERTA" attribute
     */
    public java.util.Calendar getDATASCADENZAPRESOFFERTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZAPRESOFFERTA$6);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_PRES_OFFERTA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.DbDateType xgetDATASCADENZAPRESOFFERTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZAPRESOFFERTA$6);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_SCADENZA_PRES_OFFERTA" attribute
     */
    public void setDATASCADENZAPRESOFFERTA(java.util.Calendar datascadenzapresofferta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DATASCADENZAPRESOFFERTA$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DATASCADENZAPRESOFFERTA$6);
            }
            target.setCalendarValue(datascadenzapresofferta);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_PRES_OFFERTA" attribute
     */
    public void xsetDATASCADENZAPRESOFFERTA(it.avlp.simog.massload.xmlbeans.DbDateType datascadenzapresofferta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.DbDateType target = null;
            target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().find_attribute_user(DATASCADENZAPRESOFFERTA$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.DbDateType)get_store().add_attribute_user(DATASCADENZAPRESOFFERTA$6);
            }
            target.set(datascadenzapresofferta);
        }
    }
    
    /**
     * Gets the "NUM_MANIF_INTERESSE" attribute
     */
    public int getNUMMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMANIFINTERESSE$8);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_MANIF_INTERESSE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMMANIFINTERESSE$8);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_MANIF_INTERESSE" attribute
     */
    public void setNUMMANIFINTERESSE(int nummanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMMANIFINTERESSE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMMANIFINTERESSE$8);
            }
            target.setIntValue(nummanifinteresse);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_MANIF_INTERESSE" attribute
     */
    public void xsetNUMMANIFINTERESSE(it.avlp.simog.massload.xmlbeans.InteroType nummanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMMANIFINTERESSE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMMANIFINTERESSE$8);
            }
            target.set(nummanifinteresse);
        }
    }
    
    /**
     * Gets the "NUM_IMPRESE_RICHIEDENTI" attribute
     */
    public int getNUMIMPRESERICHIEDENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPRESERICHIEDENTI$10);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_RICHIEDENTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMIMPRESERICHIEDENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPRESERICHIEDENTI$10);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_IMPRESE_RICHIEDENTI" attribute
     */
    public void setNUMIMPRESERICHIEDENTI(int numimpreserichiedenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPRESERICHIEDENTI$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMIMPRESERICHIEDENTI$10);
            }
            target.setIntValue(numimpreserichiedenti);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_RICHIEDENTI" attribute
     */
    public void xsetNUMIMPRESERICHIEDENTI(it.avlp.simog.massload.xmlbeans.InteroType numimpreserichiedenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPRESERICHIEDENTI$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMIMPRESERICHIEDENTI$10);
            }
            target.set(numimpreserichiedenti);
        }
    }
    
    /**
     * Gets the "NUM_IMPRESE_INVITATE" attribute
     */
    public int getNUMIMPRESEINVITATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPRESEINVITATE$12);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_INVITATE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMIMPRESEINVITATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPRESEINVITATE$12);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_IMPRESE_INVITATE" attribute
     */
    public void setNUMIMPRESEINVITATE(int numimpreseinvitate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPRESEINVITATE$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMIMPRESEINVITATE$12);
            }
            target.setIntValue(numimpreseinvitate);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_INVITATE" attribute
     */
    public void xsetNUMIMPRESEINVITATE(it.avlp.simog.massload.xmlbeans.InteroType numimpreseinvitate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPRESEINVITATE$12);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMIMPRESEINVITATE$12);
            }
            target.set(numimpreseinvitate);
        }
    }
    
    /**
     * Gets the "NUM_IMPRESE_OFFERENTI" attribute
     */
    public int getNUMIMPRESEOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPRESEOFFERENTI$14);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_OFFERENTI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMIMPRESEOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPRESEOFFERENTI$14);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_IMPRESE_OFFERENTI" attribute
     */
    public void setNUMIMPRESEOFFERENTI(int numimpreseofferenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPRESEOFFERENTI$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMIMPRESEOFFERENTI$14);
            }
            target.setIntValue(numimpreseofferenti);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_OFFERENTI" attribute
     */
    public void xsetNUMIMPRESEOFFERENTI(it.avlp.simog.massload.xmlbeans.InteroType numimpreseofferenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPRESEOFFERENTI$14);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMIMPRESEOFFERENTI$14);
            }
            target.set(numimpreseofferenti);
        }
    }
    
    /**
     * Gets the "NUM_OFFERTE_AMMESSE" attribute
     */
    public int getNUMOFFERTEAMMESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEAMMESSE$16);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_AMMESSE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMOFFERTEAMMESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEAMMESSE$16);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_OFFERTE_AMMESSE" attribute
     */
    public void setNUMOFFERTEAMMESSE(int numofferteammesse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEAMMESSE$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMOFFERTEAMMESSE$16);
            }
            target.setIntValue(numofferteammesse);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_AMMESSE" attribute
     */
    public void xsetNUMOFFERTEAMMESSE(it.avlp.simog.massload.xmlbeans.InteroType numofferteammesse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEAMMESSE$16);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMOFFERTEAMMESSE$16);
            }
            target.set(numofferteammesse);
        }
    }
    
    /**
     * Gets the "OFFERTA_MASSIMO" attribute
     */
    public java.math.BigDecimal getOFFERTAMASSIMO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMASSIMO$18);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "OFFERTA_MASSIMO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PercentualeType xgetOFFERTAMASSIMO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMASSIMO$18);
            return target;
        }
    }
    
    /**
     * Sets the "OFFERTA_MASSIMO" attribute
     */
    public void setOFFERTAMASSIMO(java.math.BigDecimal offertamassimo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMASSIMO$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OFFERTAMASSIMO$18);
            }
            target.setBigDecimalValue(offertamassimo);
        }
    }
    
    /**
     * Sets (as xml) the "OFFERTA_MASSIMO" attribute
     */
    public void xsetOFFERTAMASSIMO(it.avlp.simog.massload.xmlbeans.PercentualeType offertamassimo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMASSIMO$18);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(OFFERTAMASSIMO$18);
            }
            target.set(offertamassimo);
        }
    }
    
    /**
     * Gets the "OFFERTA_MINIMA" attribute
     */
    public java.math.BigDecimal getOFFERTAMINIMA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMINIMA$20);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "OFFERTA_MINIMA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PercentualeType xgetOFFERTAMINIMA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMINIMA$20);
            return target;
        }
    }
    
    /**
     * Sets the "OFFERTA_MINIMA" attribute
     */
    public void setOFFERTAMINIMA(java.math.BigDecimal offertaminima)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMINIMA$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OFFERTAMINIMA$20);
            }
            target.setBigDecimalValue(offertaminima);
        }
    }
    
    /**
     * Sets (as xml) the "OFFERTA_MINIMA" attribute
     */
    public void xsetOFFERTAMINIMA(it.avlp.simog.massload.xmlbeans.PercentualeType offertaminima)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMINIMA$20);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(OFFERTAMINIMA$20);
            }
            target.set(offertaminima);
        }
    }
    
    /**
     * Gets the "VAL_SOGLIA_ANOMALIA" attribute
     */
    public java.math.BigDecimal getVALSOGLIAANOMALIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALSOGLIAANOMALIA$22);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_SOGLIA_ANOMALIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.PercentualeType xgetVALSOGLIAANOMALIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(VALSOGLIAANOMALIA$22);
            return target;
        }
    }
    
    /**
     * Sets the "VAL_SOGLIA_ANOMALIA" attribute
     */
    public void setVALSOGLIAANOMALIA(java.math.BigDecimal valsogliaanomalia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALSOGLIAANOMALIA$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALSOGLIAANOMALIA$22);
            }
            target.setBigDecimalValue(valsogliaanomalia);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_SOGLIA_ANOMALIA" attribute
     */
    public void xsetVALSOGLIAANOMALIA(it.avlp.simog.massload.xmlbeans.PercentualeType valsogliaanomalia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.PercentualeType target = null;
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(VALSOGLIAANOMALIA$22);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(VALSOGLIAANOMALIA$22);
            }
            target.set(valsogliaanomalia);
        }
    }
    
    /**
     * Gets the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    public int getNUMOFFERTEFUORISOGLIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$24);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMOFFERTEFUORISOGLIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$24);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    public void setNUMOFFERTEFUORISOGLIA(int numoffertefuorisoglia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMOFFERTEFUORISOGLIA$24);
            }
            target.setIntValue(numoffertefuorisoglia);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" attribute
     */
    public void xsetNUMOFFERTEFUORISOGLIA(it.avlp.simog.massload.xmlbeans.InteroType numoffertefuorisoglia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$24);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMOFFERTEFUORISOGLIA$24);
            }
            target.set(numoffertefuorisoglia);
        }
    }
    
    /**
     * Gets the "NUM_OFFERTE_ESCLUSE" attribute
     */
    public int getNUMOFFERTEESCLUSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEESCLUSE$26);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_ESCLUSE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMOFFERTEESCLUSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEESCLUSE$26);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_OFFERTE_ESCLUSE" attribute
     */
    public void setNUMOFFERTEESCLUSE(int numofferteescluse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEESCLUSE$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMOFFERTEESCLUSE$26);
            }
            target.setIntValue(numofferteescluse);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_ESCLUSE" attribute
     */
    public void xsetNUMOFFERTEESCLUSE(it.avlp.simog.massload.xmlbeans.InteroType numofferteescluse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEESCLUSE$26);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMOFFERTEESCLUSE$26);
            }
            target.set(numofferteescluse);
        }
    }
    
    /**
     * Gets the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    public int getNUMIMPESCLUSEINSUFGIUSTIFICAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$28);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    public it.avlp.simog.massload.xmlbeans.InteroType xgetNUMIMPESCLUSEINSUFGIUSTIFICAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$28);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    public void setNUMIMPESCLUSEINSUFGIUSTIFICAZIONI(int numimpescluseinsufgiustificazioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$28);
            }
            target.setIntValue(numimpescluseinsufgiustificazioni);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI" attribute
     */
    public void xsetNUMIMPESCLUSEINSUFGIUSTIFICAZIONI(it.avlp.simog.massload.xmlbeans.InteroType numimpescluseinsufgiustificazioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.InteroType target = null;
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$28);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$28);
            }
            target.set(numimpescluseinsufgiustificazioni);
        }
    }
}
