/*
 * XML Type:  collaborazioneList
 * Namespace: xmlbeans.ws.simog.avlp.it
 * Java type: it.avlp.simog.ws.xmlbeans.CollaborazioneList
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.ws.xmlbeans.impl;
/**
 * An XML collaborazioneList(@xmlbeans.ws.simog.avlp.it).
 *
 * This is a complex type.
 */
public class CollaborazioneListImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.ws.xmlbeans.CollaborazioneList
{
    
    public CollaborazioneListImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COLLABORAZIONE$0 = 
        new javax.xml.namespace.QName("", "collaborazione");
    
    
    /**
     * Gets array of all "collaborazione" elements
     */
    public it.avlp.simog.ws.xmlbeans.CollaborazioneType[] getCollaborazioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(COLLABORAZIONE$0, targetList);
            it.avlp.simog.ws.xmlbeans.CollaborazioneType[] result = new it.avlp.simog.ws.xmlbeans.CollaborazioneType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "collaborazione" element
     */
    public it.avlp.simog.ws.xmlbeans.CollaborazioneType getCollaborazioneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CollaborazioneType target = null;
            target = (it.avlp.simog.ws.xmlbeans.CollaborazioneType)get_store().find_element_user(COLLABORAZIONE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "collaborazione" element
     */
    public int sizeOfCollaborazioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COLLABORAZIONE$0);
        }
    }
    
    /**
     * Sets array of all "collaborazione" element
     */
    public void setCollaborazioneArray(it.avlp.simog.ws.xmlbeans.CollaborazioneType[] collaborazioneArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(collaborazioneArray, COLLABORAZIONE$0);
        }
    }
    
    /**
     * Sets ith "collaborazione" element
     */
    public void setCollaborazioneArray(int i, it.avlp.simog.ws.xmlbeans.CollaborazioneType collaborazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CollaborazioneType target = null;
            target = (it.avlp.simog.ws.xmlbeans.CollaborazioneType)get_store().find_element_user(COLLABORAZIONE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(collaborazione);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "collaborazione" element
     */
    public it.avlp.simog.ws.xmlbeans.CollaborazioneType insertNewCollaborazione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CollaborazioneType target = null;
            target = (it.avlp.simog.ws.xmlbeans.CollaborazioneType)get_store().insert_element_user(COLLABORAZIONE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "collaborazione" element
     */
    public it.avlp.simog.ws.xmlbeans.CollaborazioneType addNewCollaborazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.ws.xmlbeans.CollaborazioneType target = null;
            target = (it.avlp.simog.ws.xmlbeans.CollaborazioneType)get_store().add_element_user(COLLABORAZIONE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "collaborazione" element
     */
    public void removeCollaborazione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COLLABORAZIONE$0, i);
        }
    }
}
