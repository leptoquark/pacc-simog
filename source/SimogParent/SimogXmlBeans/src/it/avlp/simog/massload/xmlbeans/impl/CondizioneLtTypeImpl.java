/*
 * XML Type:  CondizioneLtType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.CondizioneLtType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML CondizioneLtType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CondizioneLtTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.CondizioneLtType
{
    
    public CondizioneLtTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCONDIZIONE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_CONDIZIONE");
    
    
    /**
     * Gets the "ID_CONDIZIONE" attribute
     */
    public java.lang.String getIDCONDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCONDIZIONE$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CONDIZIONE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CondizioneLottoType xgetIDCONDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneLottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneLottoType)get_store().find_attribute_user(IDCONDIZIONE$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_CONDIZIONE" attribute
     */
    public void setIDCONDIZIONE(java.lang.String idcondizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCONDIZIONE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDCONDIZIONE$0);
            }
            target.setStringValue(idcondizione);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CONDIZIONE" attribute
     */
    public void xsetIDCONDIZIONE(it.avlp.simog.massload.xmlbeans.CondizioneLottoType idcondizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CondizioneLottoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CondizioneLottoType)get_store().find_attribute_user(IDCONDIZIONE$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CondizioneLottoType)get_store().add_attribute_user(IDCONDIZIONE$0);
            }
            target.set(idcondizione);
        }
    }
}
