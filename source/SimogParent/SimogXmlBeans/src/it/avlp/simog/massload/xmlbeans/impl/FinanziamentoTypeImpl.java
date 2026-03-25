/*
 * XML Type:  FinanziamentoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FinanziamentoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML FinanziamentoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class FinanziamentoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FinanziamentoType
{
    
    public FinanziamentoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDFINANZIAMENTO$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_FINANZIAMENTO");
    private static final javax.xml.namespace.QName IMPORTOFINANZIAMENTO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IMPORTO_FINANZIAMENTO");
    
    
    /**
     * Gets the "ID_FINANZIAMENTO" attribute
     */
    public java.lang.String getIDFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDFINANZIAMENTO$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_FINANZIAMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.TipoFinanziamentoType xgetIDFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoFinanziamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoFinanziamentoType)get_store().find_attribute_user(IDFINANZIAMENTO$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_FINANZIAMENTO" attribute
     */
    public void setIDFINANZIAMENTO(java.lang.String idfinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDFINANZIAMENTO$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDFINANZIAMENTO$0);
            }
            target.setStringValue(idfinanziamento);
        }
    }
    
    /**
     * Sets (as xml) the "ID_FINANZIAMENTO" attribute
     */
    public void xsetIDFINANZIAMENTO(it.avlp.simog.massload.xmlbeans.TipoFinanziamentoType idfinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TipoFinanziamentoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TipoFinanziamentoType)get_store().find_attribute_user(IDFINANZIAMENTO$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TipoFinanziamentoType)get_store().add_attribute_user(IDFINANZIAMENTO$0);
            }
            target.set(idfinanziamento);
        }
    }
    
    /**
     * Gets the "IMPORTO_FINANZIAMENTO" attribute
     */
    public java.math.BigDecimal getIMPORTOFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOFINANZIAMENTO$2);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_FINANZIAMENTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ImportoType xgetIMPORTOFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOFINANZIAMENTO$2);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_FINANZIAMENTO" attribute
     */
    public void setIMPORTOFINANZIAMENTO(java.math.BigDecimal importofinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMPORTOFINANZIAMENTO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMPORTOFINANZIAMENTO$2);
            }
            target.setBigDecimalValue(importofinanziamento);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_FINANZIAMENTO" attribute
     */
    public void xsetIMPORTOFINANZIAMENTO(it.avlp.simog.massload.xmlbeans.ImportoType importofinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().find_attribute_user(IMPORTOFINANZIAMENTO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ImportoType)get_store().add_attribute_user(IMPORTOFINANZIAMENTO$2);
            }
            target.set(importofinanziamento);
        }
    }
}
