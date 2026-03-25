/*
 * XML Type:  FormularioAvvisoModifica
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML FormularioAvvisoModifica(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class FormularioAvvisoModificaImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica
{
    
    public FormularioAvvisoModificaImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MODIFICA$0 = 
        new javax.xml.namespace.QName("", "MODIFICA");
    
    
    /**
     * Gets the "MODIFICA" element
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType getMODIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType)get_store().find_element_user(MODIFICA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "MODIFICA" element
     */
    public void setMODIFICA(it.avlp.simog.massload.xmlbeans.ModificaType modifica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType)get_store().find_element_user(MODIFICA$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ModificaType)get_store().add_element_user(MODIFICA$0);
            }
            target.set(modifica);
        }
    }
    
    /**
     * Appends and returns a new empty "MODIFICA" element
     */
    public it.avlp.simog.massload.xmlbeans.ModificaType addNewMODIFICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ModificaType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ModificaType)get_store().add_element_user(MODIFICA$0);
            return target;
        }
    }
}
