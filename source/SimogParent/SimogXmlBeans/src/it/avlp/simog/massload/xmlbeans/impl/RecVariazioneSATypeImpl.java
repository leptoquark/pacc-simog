/*
 * XML Type:  RecVariazioneSAType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RecVariazioneSAType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RecVariazioneSAType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RecVariazioneSATypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RecVariazioneSAType
{
    
    public RecVariazioneSATypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDGARA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_GARA");
    private static final javax.xml.namespace.QName MOTIVO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MOTIVO");
    private static final javax.xml.namespace.QName CFAMMINISTRAZIONE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CF_AMMINISTRAZIONE");
    private static final javax.xml.namespace.QName IDCENTROCOSTO$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_CENTRO_COSTO");
    
    
    /**
     * Gets the "ID_GARA" attribute
     */
    public long getIDGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGARA$0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_GARA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.LongType xgetIDGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LongType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().find_attribute_user(IDGARA$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_GARA" attribute
     */
    public void setIDGARA(long idgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDGARA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDGARA$0);
            }
            target.setLongValue(idgara);
        }
    }
    
    /**
     * Sets (as xml) the "ID_GARA" attribute
     */
    public void xsetIDGARA(it.avlp.simog.massload.xmlbeans.LongType idgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.LongType target = null;
            target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().find_attribute_user(IDGARA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.LongType)get_store().add_attribute_user(IDGARA$0);
            }
            target.set(idgara);
        }
    }
    
    /**
     * Gets the "MOTIVO" attribute
     */
    public java.lang.String getMOTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVO$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType xgetMOTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType)get_store().find_attribute_user(MOTIVO$2);
            return target;
        }
    }
    
    /**
     * Sets the "MOTIVO" attribute
     */
    public void setMOTIVO(java.lang.String motivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOTIVO$2);
            }
            target.setStringValue(motivo);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVO" attribute
     */
    public void xsetMOTIVO(it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType motivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType)get_store().find_attribute_user(MOTIVO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotiviVariazioneSAType)get_store().add_attribute_user(MOTIVO$2);
            }
            target.set(motivo);
        }
    }
    
    /**
     * Gets the "CF_AMMINISTRAZIONE" attribute
     */
    public java.lang.String getCFAMMINISTRAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAMMINISTRAZIONE$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_AMMINISTRAZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CodFiscType xgetCFAMMINISTRAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAMMINISTRAZIONE$4);
            return target;
        }
    }
    
    /**
     * Sets the "CF_AMMINISTRAZIONE" attribute
     */
    public void setCFAMMINISTRAZIONE(java.lang.String cfamministrazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CFAMMINISTRAZIONE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CFAMMINISTRAZIONE$4);
            }
            target.setStringValue(cfamministrazione);
        }
    }
    
    /**
     * Sets (as xml) the "CF_AMMINISTRAZIONE" attribute
     */
    public void xsetCFAMMINISTRAZIONE(it.avlp.simog.massload.xmlbeans.CodFiscType cfamministrazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CodFiscType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().find_attribute_user(CFAMMINISTRAZIONE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CodFiscType)get_store().add_attribute_user(CFAMMINISTRAZIONE$4);
            }
            target.set(cfamministrazione);
        }
    }
    
    /**
     * Gets the "ID_CENTRO_COSTO" attribute
     */
    public java.lang.String getIDCENTROCOSTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCENTROCOSTO$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CENTRO_COSTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO xgetIDCENTROCOSTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO)get_store().find_attribute_user(IDCENTROCOSTO$6);
            return target;
        }
    }
    
    /**
     * Sets the "ID_CENTRO_COSTO" attribute
     */
    public void setIDCENTROCOSTO(java.lang.String idcentrocosto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCENTROCOSTO$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDCENTROCOSTO$6);
            }
            target.setStringValue(idcentrocosto);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CENTRO_COSTO" attribute
     */
    public void xsetIDCENTROCOSTO(it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO idcentrocosto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO target = null;
            target = (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO)get_store().find_attribute_user(IDCENTROCOSTO$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO)get_store().add_attribute_user(IDCENTROCOSTO$6);
            }
            target.set(idcentrocosto);
        }
    }
    /**
     * An XML ID_CENTRO_COSTO(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.RecVariazioneSAType$IDCENTROCOSTO.
     */
    public static class IDCENTROCOSTOImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.RecVariazioneSAType.IDCENTROCOSTO
    {
        
        public IDCENTROCOSTOImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected IDCENTROCOSTOImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
