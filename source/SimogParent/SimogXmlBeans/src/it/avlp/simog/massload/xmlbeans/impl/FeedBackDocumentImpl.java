/*
 * An XML document type.
 * Localname: FeedBack
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.FeedBackDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one FeedBack(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class FeedBackDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FeedBackDocument
{
    
    public FeedBackDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FEEDBACK$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "FeedBack");
    
    
    /**
     * Gets the "FeedBack" element
     */
    public it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack getFeedBack()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack target = null;
            target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack)get_store().find_element_user(FEEDBACK$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "FeedBack" element
     */
    public void setFeedBack(it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack feedBack)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack target = null;
            target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack)get_store().find_element_user(FEEDBACK$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack)get_store().add_element_user(FEEDBACK$0);
            }
            target.set(feedBack);
        }
    }
    
    /**
     * Appends and returns a new empty "FeedBack" element
     */
    public it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack addNewFeedBack()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack target = null;
            target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack)get_store().add_element_user(FEEDBACK$0);
            return target;
        }
    }
    /**
     * An XML FeedBack(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class FeedBackImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack
    {
        
        public FeedBackImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName INFOFLUSSO$0 = 
            new javax.xml.namespace.QName("", "InfoFlusso");
        private static final javax.xml.namespace.QName ANOMALIESCHEDE$2 = 
            new javax.xml.namespace.QName("", "AnomalieSchede");
        
        
        /**
         * Gets the "InfoFlusso" element
         */
        public it.avlp.simog.massload.xmlbeans.FlussoType getInfoFlusso()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FlussoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.FlussoType)get_store().find_element_user(INFOFLUSSO$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "InfoFlusso" element
         */
        public void setInfoFlusso(it.avlp.simog.massload.xmlbeans.FlussoType infoFlusso)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FlussoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.FlussoType)get_store().find_element_user(INFOFLUSSO$0, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.FlussoType)get_store().add_element_user(INFOFLUSSO$0);
                }
                target.set(infoFlusso);
            }
        }
        
        /**
         * Appends and returns a new empty "InfoFlusso" element
         */
        public it.avlp.simog.massload.xmlbeans.FlussoType addNewInfoFlusso()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FlussoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.FlussoType)get_store().add_element_user(INFOFLUSSO$0);
                return target;
            }
        }
        
        /**
         * Gets array of all "AnomalieSchede" elements
         */
        public it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede[] getAnomalieSchedeArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(ANOMALIESCHEDE$2, targetList);
                it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede[] result = new it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "AnomalieSchede" element
         */
        public it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede getAnomalieSchedeArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede target = null;
                target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede)get_store().find_element_user(ANOMALIESCHEDE$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "AnomalieSchede" element
         */
        public int sizeOfAnomalieSchedeArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(ANOMALIESCHEDE$2);
            }
        }
        
        /**
         * Sets array of all "AnomalieSchede" element
         */
        public void setAnomalieSchedeArray(it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede[] anomalieSchedeArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(anomalieSchedeArray, ANOMALIESCHEDE$2);
            }
        }
        
        /**
         * Sets ith "AnomalieSchede" element
         */
        public void setAnomalieSchedeArray(int i, it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede anomalieSchede)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede target = null;
                target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede)get_store().find_element_user(ANOMALIESCHEDE$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(anomalieSchede);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "AnomalieSchede" element
         */
        public it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede insertNewAnomalieSchede(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede target = null;
                target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede)get_store().insert_element_user(ANOMALIESCHEDE$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "AnomalieSchede" element
         */
        public it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede addNewAnomalieSchede()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede target = null;
                target = (it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede)get_store().add_element_user(ANOMALIESCHEDE$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "AnomalieSchede" element
         */
        public void removeAnomalieSchede(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(ANOMALIESCHEDE$2, i);
            }
        }
        /**
         * An XML AnomalieSchede(@).
         *
         * This is a complex type.
         */
        public static class AnomalieSchedeImpl extends it.avlp.simog.massload.xmlbeans.impl.AnomSchedaATypeImpl implements it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede
        {
            
            public AnomalieSchedeImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            
        }
    }
}
