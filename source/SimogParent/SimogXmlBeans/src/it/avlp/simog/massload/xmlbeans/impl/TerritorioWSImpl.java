/*
 * XML Type:  TerritorioWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TerritorioWS
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * An XML TerritorioWS(@xmlbeans.massload.simog.avlp.it).
 *
 * This is a complex type.
 */
public class TerritorioWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.TerritorioWS
{
    
    public TerritorioWSImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TERRITORI$0 = 
        new javax.xml.namespace.QName("", "Territori");
    
    
    /**
     * Gets the "Territori" element
     */
    public it.avlp.simog.massload.xmlbeans.TerritorioType getTerritori()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TerritorioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().find_element_user(TERRITORI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Territori" element
     */
    public boolean isSetTerritori()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TERRITORI$0) != 0;
        }
    }
    
    /**
     * Sets the "Territori" element
     */
    public void setTerritori(it.avlp.simog.massload.xmlbeans.TerritorioType territori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TerritorioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().find_element_user(TERRITORI$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().add_element_user(TERRITORI$0);
            }
            target.set(territori);
        }
    }
    
    /**
     * Appends and returns a new empty "Territori" element
     */
    public it.avlp.simog.massload.xmlbeans.TerritorioType addNewTerritori()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TerritorioType target = null;
            target = (it.avlp.simog.massload.xmlbeans.TerritorioType)get_store().add_element_user(TERRITORI$0);
            return target;
        }
    }
    
    /**
     * Unsets the "Territori" element
     */
    public void unsetTerritori()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TERRITORI$0, 0);
        }
    }
}
