/*
 * An XML document type.
 * Localname: IniziativaWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.IniziativaWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one IniziativaWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class IniziativaWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.IniziativaWSDocument
{
    
    public IniziativaWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INIZIATIVAWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "IniziativaWS");
    
    
    /**
     * Gets the "IniziativaWS" element
     */
    public it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS getIniziativaWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS)get_store().find_element_user(INIZIATIVAWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "IniziativaWS" element
     */
    public void setIniziativaWS(it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS iniziativaWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS)get_store().find_element_user(INIZIATIVAWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS)get_store().add_element_user(INIZIATIVAWS$0);
            }
            target.set(iniziativaWS);
        }
    }
    
    /**
     * Appends and returns a new empty "IniziativaWS" element
     */
    public it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS addNewIniziativaWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS)get_store().add_element_user(INIZIATIVAWS$0);
            return target;
        }
    }
    /**
     * An XML IniziativaWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class IniziativaWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS
    {
        
        public IniziativaWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName INIZIATIVA$0 = 
            new javax.xml.namespace.QName("", "Iniziativa");
        
        
        /**
         * Gets array of all "Iniziativa" elements
         */
        public it.avlp.simog.massload.xmlbeans.IniziativaType[] getIniziativaArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(INIZIATIVA$0, targetList);
                it.avlp.simog.massload.xmlbeans.IniziativaType[] result = new it.avlp.simog.massload.xmlbeans.IniziativaType[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "Iniziativa" element
         */
        public it.avlp.simog.massload.xmlbeans.IniziativaType getIniziativaArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.IniziativaType target = null;
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType)get_store().find_element_user(INIZIATIVA$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "Iniziativa" element
         */
        public int sizeOfIniziativaArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INIZIATIVA$0);
            }
        }
        
        /**
         * Sets array of all "Iniziativa" element
         */
        public void setIniziativaArray(it.avlp.simog.massload.xmlbeans.IniziativaType[] iniziativaArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(iniziativaArray, INIZIATIVA$0);
            }
        }
        
        /**
         * Sets ith "Iniziativa" element
         */
        public void setIniziativaArray(int i, it.avlp.simog.massload.xmlbeans.IniziativaType iniziativa)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.IniziativaType target = null;
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType)get_store().find_element_user(INIZIATIVA$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(iniziativa);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "Iniziativa" element
         */
        public it.avlp.simog.massload.xmlbeans.IniziativaType insertNewIniziativa(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.IniziativaType target = null;
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType)get_store().insert_element_user(INIZIATIVA$0, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "Iniziativa" element
         */
        public it.avlp.simog.massload.xmlbeans.IniziativaType addNewIniziativa()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.IniziativaType target = null;
                target = (it.avlp.simog.massload.xmlbeans.IniziativaType)get_store().add_element_user(INIZIATIVA$0);
                return target;
            }
        }
        
        /**
         * Removes the ith "Iniziativa" element
         */
        public void removeIniziativa(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INIZIATIVA$0, i);
            }
        }
    }
}
