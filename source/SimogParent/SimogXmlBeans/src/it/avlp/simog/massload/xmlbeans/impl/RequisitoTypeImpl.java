/*
 * XML Type:  RequisitoType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RequisitoType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML RequisitoType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class RequisitoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RequisitoType
{
    
    public RequisitoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCATEGORIA$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "ID_CATEGORIA");
    private static final javax.xml.namespace.QName CLASSEIMPORTO$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "CLASSE_IMPORTO");
    private static final javax.xml.namespace.QName PREVALENTE$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "PREVALENTE");
    private static final javax.xml.namespace.QName SCORPORABILE$6 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SCORPORABILE");
    private static final javax.xml.namespace.QName SUBAPPALTABILE$8 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "SUBAPPALTABILE");
    
    
    /**
     * Gets the "ID_CATEGORIA" attribute
     */
    public java.lang.String getIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCATEGORIA$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CATEGORIA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.CategoriaType xgetIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().find_attribute_user(IDCATEGORIA$0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_CATEGORIA" attribute
     */
    public void setIDCATEGORIA(java.lang.String idcategoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDCATEGORIA$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDCATEGORIA$0);
            }
            target.setStringValue(idcategoria);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CATEGORIA" attribute
     */
    public void xsetIDCATEGORIA(it.avlp.simog.massload.xmlbeans.CategoriaType idcategoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.CategoriaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().find_attribute_user(IDCATEGORIA$0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.CategoriaType)get_store().add_attribute_user(IDCATEGORIA$0);
            }
            target.set(idcategoria);
        }
    }
    
    /**
     * Gets the "CLASSE_IMPORTO" attribute
     */
    public java.lang.String getCLASSEIMPORTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CLASSEIMPORTO$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CLASSE_IMPORTO" attribute
     */
    public it.avlp.simog.massload.xmlbeans.ClasseImportoType xgetCLASSEIMPORTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ClasseImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ClasseImportoType)get_store().find_attribute_user(CLASSEIMPORTO$2);
            return target;
        }
    }
    
    /**
     * Sets the "CLASSE_IMPORTO" attribute
     */
    public void setCLASSEIMPORTO(java.lang.String classeimporto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CLASSEIMPORTO$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CLASSEIMPORTO$2);
            }
            target.setStringValue(classeimporto);
        }
    }
    
    /**
     * Sets (as xml) the "CLASSE_IMPORTO" attribute
     */
    public void xsetCLASSEIMPORTO(it.avlp.simog.massload.xmlbeans.ClasseImportoType classeimporto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ClasseImportoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ClasseImportoType)get_store().find_attribute_user(CLASSEIMPORTO$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ClasseImportoType)get_store().add_attribute_user(CLASSEIMPORTO$2);
            }
            target.set(classeimporto);
        }
    }
    
    /**
     * Gets the "PREVALENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PREVALENTE$4);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PREVALENTE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PREVALENTE$4);
            return target;
        }
    }
    
    /**
     * True if has "PREVALENTE" attribute
     */
    public boolean isSetPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PREVALENTE$4) != null;
        }
    }
    
    /**
     * Sets the "PREVALENTE" attribute
     */
    public void setPREVALENTE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum prevalente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PREVALENTE$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PREVALENTE$4);
            }
            target.setEnumValue(prevalente);
        }
    }
    
    /**
     * Sets (as xml) the "PREVALENTE" attribute
     */
    public void xsetPREVALENTE(it.avlp.simog.massload.xmlbeans.FlagSNType prevalente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(PREVALENTE$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(PREVALENTE$4);
            }
            target.set(prevalente);
        }
    }
    
    /**
     * Unsets the "PREVALENTE" attribute
     */
    public void unsetPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PREVALENTE$4);
        }
    }
    
    /**
     * Gets the "SCORPORABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCORPORABILE$6);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SCORPORABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SCORPORABILE$6);
            return target;
        }
    }
    
    /**
     * True if has "SCORPORABILE" attribute
     */
    public boolean isSetSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SCORPORABILE$6) != null;
        }
    }
    
    /**
     * Sets the "SCORPORABILE" attribute
     */
    public void setSCORPORABILE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum scorporabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SCORPORABILE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SCORPORABILE$6);
            }
            target.setEnumValue(scorporabile);
        }
    }
    
    /**
     * Sets (as xml) the "SCORPORABILE" attribute
     */
    public void xsetSCORPORABILE(it.avlp.simog.massload.xmlbeans.FlagSNType scorporabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SCORPORABILE$6);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(SCORPORABILE$6);
            }
            target.set(scorporabile);
        }
    }
    
    /**
     * Unsets the "SCORPORABILE" attribute
     */
    public void unsetSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SCORPORABILE$6);
        }
    }
    
    /**
     * Gets the "SUBAPPALTABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType.Enum getSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SUBAPPALTABILE$8);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SUBAPPALTABILE" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FlagSNType xgetSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SUBAPPALTABILE$8);
            return target;
        }
    }
    
    /**
     * True if has "SUBAPPALTABILE" attribute
     */
    public boolean isSetSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SUBAPPALTABILE$8) != null;
        }
    }
    
    /**
     * Sets the "SUBAPPALTABILE" attribute
     */
    public void setSUBAPPALTABILE(it.avlp.simog.massload.xmlbeans.FlagSNType.Enum subappaltabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SUBAPPALTABILE$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SUBAPPALTABILE$8);
            }
            target.setEnumValue(subappaltabile);
        }
    }
    
    /**
     * Sets (as xml) the "SUBAPPALTABILE" attribute
     */
    public void xsetSUBAPPALTABILE(it.avlp.simog.massload.xmlbeans.FlagSNType subappaltabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FlagSNType target = null;
            target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().find_attribute_user(SUBAPPALTABILE$8);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FlagSNType)get_store().add_attribute_user(SUBAPPALTABILE$8);
            }
            target.set(subappaltabile);
        }
    }
    
    /**
     * Unsets the "SUBAPPALTABILE" attribute
     */
    public void unsetSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SUBAPPALTABILE$8);
        }
    }
}
