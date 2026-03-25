/*
 * XML Type:  OfferteType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.OfferteType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML OfferteType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class OfferteTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.OfferteType
{
    
    public OfferteTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OFFERTAMASSIMO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OFFERTA_MASSIMO");
    private static final javax.xml.namespace.QName OFFERTAMINIMA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "OFFERTA_MINIMA");
    private static final javax.xml.namespace.QName VALSOGLIAANOMALIA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "VAL_SOGLIA_ANOMALIA");
    private static final javax.xml.namespace.QName NUMOFFERTEESCLUSE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_OFFERTE_ESCLUSE");
    private static final javax.xml.namespace.QName NUMOFFERTEFUORISOGLIA$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_OFFERTE_FUORI_SOGLIA");
    private static final javax.xml.namespace.QName NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$10 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "NUM_IMP_ESCLUSE_INSUF_GIUSTIFICAZIONI");
    
    
    /**
     * Gets the "OFFERTA_MASSIMO" attribute
     */
    public java.math.BigDecimal getOFFERTAMASSIMO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMASSIMO$0);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMASSIMO$0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMASSIMO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OFFERTAMASSIMO$0);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMASSIMO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(OFFERTAMASSIMO$0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMINIMA$2);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMINIMA$2);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OFFERTAMINIMA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OFFERTAMINIMA$2);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(OFFERTAMINIMA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(OFFERTAMINIMA$2);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALSOGLIAANOMALIA$4);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(VALSOGLIAANOMALIA$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(VALSOGLIAANOMALIA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(VALSOGLIAANOMALIA$4);
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
            target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().find_attribute_user(VALSOGLIAANOMALIA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.PercentualeType)get_store().add_attribute_user(VALSOGLIAANOMALIA$4);
            }
            target.set(valsogliaanomalia);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEESCLUSE$6);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEESCLUSE$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEESCLUSE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMOFFERTEESCLUSE$6);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEESCLUSE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMOFFERTEESCLUSE$6);
            }
            target.set(numofferteescluse);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$8);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$8);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMOFFERTEFUORISOGLIA$8);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMOFFERTEFUORISOGLIA$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMOFFERTEFUORISOGLIA$8);
            }
            target.set(numoffertefuorisoglia);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$10);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$10);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$10);
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
            target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().find_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$10);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.InteroType)get_store().add_attribute_user(NUMIMPESCLUSEINSUFGIUSTIFICAZIONI$10);
            }
            target.set(numimpescluseinsufgiustificazioni);
        }
    }
}
