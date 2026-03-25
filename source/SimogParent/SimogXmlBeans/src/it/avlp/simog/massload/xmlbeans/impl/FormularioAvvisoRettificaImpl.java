/*
 * XML Type:  FormularioAvvisoRettifica
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML FormularioAvvisoRettifica(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class FormularioAvvisoRettificaImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica
{
    
    public FormularioAvvisoRettificaImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RETTIFICA$0 = 
        new javax.xml.namespace.QName("", "RETTIFICA");
    private static final javax.xml.namespace.QName MOTIVORETTIFICA$2 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "MOTIVO_RETTIFICA");
    private static final javax.xml.namespace.QName INFOADDMODIFICA$4 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "INFO_ADD_MODIFICA");
    
    
    /**
     * Gets array of all "RETTIFICA" elements
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType[] getRETTIFICAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RETTIFICA$0, targetList);
            it.avlp.simog.massload.xmlbeans.RettificaType[] result = new it.avlp.simog.massload.xmlbeans.RettificaType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "RETTIFICA" element
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType getRETTIFICAArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType)get_store().find_element_user(RETTIFICA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "RETTIFICA" element
     */
    public int sizeOfRETTIFICAArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RETTIFICA$0);
        }
    }
    
    /**
     * Sets array of all "RETTIFICA" element
     */
    public void setRETTIFICAArray(it.avlp.simog.massload.xmlbeans.RettificaType[] rettificaArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(rettificaArray, RETTIFICA$0);
        }
    }
    
    /**
     * Sets ith "RETTIFICA" element
     */
    public void setRETTIFICAArray(int i, it.avlp.simog.massload.xmlbeans.RettificaType rettifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType)get_store().find_element_user(RETTIFICA$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(rettifica);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "RETTIFICA" element
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType insertNewRETTIFICA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType)get_store().insert_element_user(RETTIFICA$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "RETTIFICA" element
     */
    public it.avlp.simog.massload.xmlbeans.RettificaType addNewRETTIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RettificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.RettificaType)get_store().add_element_user(RETTIFICA$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "RETTIFICA" element
     */
    public void removeRETTIFICA(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RETTIFICA$0, i);
        }
    }
    
    /**
     * Gets the "MOTIVO_RETTIFICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoRettificaType.Enum getMOTIVORETTIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVORETTIFICA$2);
            if (target == null)
            {
                return null;
            }
            return (it.avlp.simog.massload.xmlbeans.MotivoRettificaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVO_RETTIFICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.MotivoRettificaType xgetMOTIVORETTIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoRettificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoRettificaType)get_store().find_attribute_user(MOTIVORETTIFICA$2);
            return target;
        }
    }
    
    /**
     * Sets the "MOTIVO_RETTIFICA" attribute
     */
    public void setMOTIVORETTIFICA(it.avlp.simog.massload.xmlbeans.MotivoRettificaType.Enum motivorettifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOTIVORETTIFICA$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOTIVORETTIFICA$2);
            }
            target.setEnumValue(motivorettifica);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVO_RETTIFICA" attribute
     */
    public void xsetMOTIVORETTIFICA(it.avlp.simog.massload.xmlbeans.MotivoRettificaType motivorettifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.MotivoRettificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.MotivoRettificaType)get_store().find_attribute_user(MOTIVORETTIFICA$2);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.MotivoRettificaType)get_store().add_attribute_user(MOTIVORETTIFICA$2);
            }
            target.set(motivorettifica);
        }
    }
    
    /**
     * Gets the "INFO_ADD_MODIFICA" attribute
     */
    public java.lang.String getINFOADDMODIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOADDMODIFICA$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "INFO_ADD_MODIFICA" attribute
     */
    public it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA xgetINFOADDMODIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA target = null;
            target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA)get_store().find_attribute_user(INFOADDMODIFICA$4);
            return target;
        }
    }
    
    /**
     * True if has "INFO_ADD_MODIFICA" attribute
     */
    public boolean isSetINFOADDMODIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(INFOADDMODIFICA$4) != null;
        }
    }
    
    /**
     * Sets the "INFO_ADD_MODIFICA" attribute
     */
    public void setINFOADDMODIFICA(java.lang.String infoaddmodifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INFOADDMODIFICA$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INFOADDMODIFICA$4);
            }
            target.setStringValue(infoaddmodifica);
        }
    }
    
    /**
     * Sets (as xml) the "INFO_ADD_MODIFICA" attribute
     */
    public void xsetINFOADDMODIFICA(it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA infoaddmodifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA target = null;
            target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA)get_store().find_attribute_user(INFOADDMODIFICA$4);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA)get_store().add_attribute_user(INFOADDMODIFICA$4);
            }
            target.set(infoaddmodifica);
        }
    }
    
    /**
     * Unsets the "INFO_ADD_MODIFICA" attribute
     */
    public void unsetINFOADDMODIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(INFOADDMODIFICA$4);
        }
    }
    /**
     * An XML INFO_ADD_MODIFICA(@xmlbeans.massload.simog.avlp.it).
     *
     * This is an atomic type that is a restriction of it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica$INFOADDMODIFICA.
     */
    public static class INFOADDMODIFICAImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica.INFOADDMODIFICA
    {
        
        public INFOADDMODIFICAImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected INFOADDMODIFICAImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
