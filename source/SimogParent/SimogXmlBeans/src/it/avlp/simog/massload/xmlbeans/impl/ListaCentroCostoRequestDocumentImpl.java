/*
 * An XML document type.
 * Localname: listaCentroCostoRequest
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one listaCentroCostoRequest(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class ListaCentroCostoRequestDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestDocument
{
    
    public ListaCentroCostoRequestDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LISTACENTROCOSTOREQUEST$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "listaCentroCostoRequest");
    
    
    /**
     * Gets the "listaCentroCostoRequest" element
     */
    public it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType getListaCentroCostoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType)get_store().find_element_user(LISTACENTROCOSTOREQUEST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "listaCentroCostoRequest" element
     */
    public void setListaCentroCostoRequest(it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType listaCentroCostoRequest)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType)get_store().find_element_user(LISTACENTROCOSTOREQUEST$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType)get_store().add_element_user(LISTACENTROCOSTOREQUEST$0);
            }
            target.set(listaCentroCostoRequest);
        }
    }
    
    /**
     * Appends and returns a new empty "listaCentroCostoRequest" element
     */
    public it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType addNewListaCentroCostoRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestType)get_store().add_element_user(LISTACENTROCOSTOREQUEST$0);
            return target;
        }
    }
}
