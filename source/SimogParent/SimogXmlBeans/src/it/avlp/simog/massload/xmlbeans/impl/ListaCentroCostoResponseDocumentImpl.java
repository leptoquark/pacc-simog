/*
 * An XML document type.
 * Localname: listaCentroCostoResponse
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one listaCentroCostoResponse(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class ListaCentroCostoResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseDocument
{
    
    public ListaCentroCostoResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LISTACENTROCOSTORESPONSE$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "listaCentroCostoResponse");
    
    
    /**
     * Gets the "listaCentroCostoResponse" element
     */
    public it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType getListaCentroCostoResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType)get_store().find_element_user(LISTACENTROCOSTORESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "listaCentroCostoResponse" element
     */
    public void setListaCentroCostoResponse(it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType listaCentroCostoResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType)get_store().find_element_user(LISTACENTROCOSTORESPONSE$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType)get_store().add_element_user(LISTACENTROCOSTORESPONSE$0);
            }
            target.set(listaCentroCostoResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "listaCentroCostoResponse" element
     */
    public it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType addNewListaCentroCostoResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType)get_store().add_element_user(LISTACENTROCOSTORESPONSE$0);
            return target;
        }
    }
}
