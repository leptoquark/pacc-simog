/*
 * An XML document type.
 * Localname: FormularioAvvisoModificaWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one FormularioAvvisoModificaWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class FormularioAvvisoModificaWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument
{
    
    public FormularioAvvisoModificaWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FORMULARIOAVVISOMODIFICAWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FormularioAvvisoModificaWS");
    
    
    /**
     * Gets the "FormularioAvvisoModificaWS" element
     */
    public it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS getFormularioAvvisoModificaWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS)get_store().find_element_user(FORMULARIOAVVISOMODIFICAWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "FormularioAvvisoModificaWS" element
     */
    public void setFormularioAvvisoModificaWS(it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS formularioAvvisoModificaWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS)get_store().find_element_user(FORMULARIOAVVISOMODIFICAWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS)get_store().add_element_user(FORMULARIOAVVISOMODIFICAWS$0);
            }
            target.set(formularioAvvisoModificaWS);
        }
    }
    
    /**
     * Appends and returns a new empty "FormularioAvvisoModificaWS" element
     */
    public it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS addNewFormularioAvvisoModificaWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS)get_store().add_element_user(FORMULARIOAVVISOMODIFICAWS$0);
            return target;
        }
    }
    /**
     * An XML FormularioAvvisoModificaWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class FormularioAvvisoModificaWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument.FormularioAvvisoModificaWS
    {
        
        public FormularioAvvisoModificaWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName FORMULARIOAVVISOMODIFICA$0 = 
            new javax.xml.namespace.QName("", "formularioAvvisoModifica");
        
        
        /**
         * Gets the "formularioAvvisoModifica" element
         */
        public it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica getFormularioAvvisoModifica()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica target = null;
                target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica)get_store().find_element_user(FORMULARIOAVVISOMODIFICA$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "formularioAvvisoModifica" element
         */
        public void setFormularioAvvisoModifica(it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica formularioAvvisoModifica)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica target = null;
                target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica)get_store().find_element_user(FORMULARIOAVVISOMODIFICA$0, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica)get_store().add_element_user(FORMULARIOAVVISOMODIFICA$0);
                }
                target.set(formularioAvvisoModifica);
            }
        }
        
        /**
         * Appends and returns a new empty "formularioAvvisoModifica" element
         */
        public it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica addNewFormularioAvvisoModifica()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica target = null;
                target = (it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica)get_store().add_element_user(FORMULARIOAVVISOMODIFICA$0);
                return target;
            }
        }
    }
}
