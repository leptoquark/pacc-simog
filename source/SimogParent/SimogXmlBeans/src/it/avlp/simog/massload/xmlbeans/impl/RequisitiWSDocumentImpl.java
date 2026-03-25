/*
 * An XML document type.
 * Localname: RequisitiWS
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.RequisitiWSDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one RequisitiWS(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class RequisitiWSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RequisitiWSDocument
{
    
    public RequisitiWSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REQUISITIWS$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "RequisitiWS");
    
    
    /**
     * Gets the "RequisitiWS" element
     */
    public it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS getRequisitiWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS)get_store().find_element_user(REQUISITIWS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RequisitiWS" element
     */
    public void setRequisitiWS(it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS requisitiWS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS)get_store().find_element_user(REQUISITIWS$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS)get_store().add_element_user(REQUISITIWS$0);
            }
            target.set(requisitiWS);
        }
    }
    
    /**
     * Appends and returns a new empty "RequisitiWS" element
     */
    public it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS addNewRequisitiWS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS target = null;
            target = (it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS)get_store().add_element_user(REQUISITIWS$0);
            return target;
        }
    }
    /**
     * An XML RequisitiWS(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class RequisitiWSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS
    {
        
        public RequisitiWSImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName REQUISITO$0 = 
            new javax.xml.namespace.QName("", "Requisito");
        
        
        /**
         * Gets array of all "Requisito" elements
         */
        public it.avlp.simog.massload.xmlbeans.ReqGaraType[] getRequisitoArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(REQUISITO$0, targetList);
                it.avlp.simog.massload.xmlbeans.ReqGaraType[] result = new it.avlp.simog.massload.xmlbeans.ReqGaraType[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "Requisito" element
         */
        public it.avlp.simog.massload.xmlbeans.ReqGaraType getRequisitoArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
                target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().find_element_user(REQUISITO$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "Requisito" element
         */
        public int sizeOfRequisitoArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(REQUISITO$0);
            }
        }
        
        /**
         * Sets array of all "Requisito" element
         */
        public void setRequisitoArray(it.avlp.simog.massload.xmlbeans.ReqGaraType[] requisitoArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(requisitoArray, REQUISITO$0);
            }
        }
        
        /**
         * Sets ith "Requisito" element
         */
        public void setRequisitoArray(int i, it.avlp.simog.massload.xmlbeans.ReqGaraType requisito)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
                target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().find_element_user(REQUISITO$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(requisito);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "Requisito" element
         */
        public it.avlp.simog.massload.xmlbeans.ReqGaraType insertNewRequisito(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
                target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().insert_element_user(REQUISITO$0, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "Requisito" element
         */
        public it.avlp.simog.massload.xmlbeans.ReqGaraType addNewRequisito()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.ReqGaraType target = null;
                target = (it.avlp.simog.massload.xmlbeans.ReqGaraType)get_store().add_element_user(REQUISITO$0);
                return target;
            }
        }
        
        /**
         * Removes the ith "Requisito" element
         */
        public void removeRequisito(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(REQUISITO$0, i);
            }
        }
    }
}
