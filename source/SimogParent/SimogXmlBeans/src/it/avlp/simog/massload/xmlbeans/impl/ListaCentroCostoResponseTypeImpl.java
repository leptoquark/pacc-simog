/*
 * XML Type:  listaCentroCostoResponseType
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML listaCentroCostoResponseType(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class ListaCentroCostoResponseTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType
{
    
    public ListaCentroCostoResponseTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName STAZIONEAPPALTANTE$0 = 
        new javax.xml.namespace.QName("", "stazioneAppaltante");
    private static final javax.xml.namespace.QName LISTACENTRICOSTO$2 = 
        new javax.xml.namespace.QName("", "listaCentriCosto");
    
    
    /**
     * Gets the "stazioneAppaltante" element
     */
    public it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType getStazioneAppaltante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType)get_store().find_element_user(STAZIONEAPPALTANTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "stazioneAppaltante" element
     */
    public void setStazioneAppaltante(it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType stazioneAppaltante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType)get_store().find_element_user(STAZIONEAPPALTANTE$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType)get_store().add_element_user(STAZIONEAPPALTANTE$0);
            }
            target.set(stazioneAppaltante);
        }
    }
    
    /**
     * Appends and returns a new empty "stazioneAppaltante" element
     */
    public it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType addNewStazioneAppaltante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType target = null;
            target = (it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType)get_store().add_element_user(STAZIONEAPPALTANTE$0);
            return target;
        }
    }
    
    /**
     * Gets the "listaCentriCosto" element
     */
    public it.avlp.simog.massload.xmlbeans.ListaCentriCostoType getListaCentriCosto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentriCostoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentriCostoType)get_store().find_element_user(LISTACENTRICOSTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "listaCentriCosto" element
     */
    public void setListaCentriCosto(it.avlp.simog.massload.xmlbeans.ListaCentriCostoType listaCentriCosto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentriCostoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentriCostoType)get_store().find_element_user(LISTACENTRICOSTO$2, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.ListaCentriCostoType)get_store().add_element_user(LISTACENTRICOSTO$2);
            }
            target.set(listaCentriCosto);
        }
    }
    
    /**
     * Appends and returns a new empty "listaCentriCosto" element
     */
    public it.avlp.simog.massload.xmlbeans.ListaCentriCostoType addNewListaCentriCosto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.ListaCentriCostoType target = null;
            target = (it.avlp.simog.massload.xmlbeans.ListaCentriCostoType)get_store().add_element_user(LISTACENTRICOSTO$2);
            return target;
        }
    }
}
