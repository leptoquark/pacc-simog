/*
 * An XML document type.
 * Localname: TrasferimentoDati
 * Namespace: xmlbeans.massload.simog.avlp.it
 * Java type: it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument
 *
 * Automatically generated - do not modify.
 */
package it.avlp.simog.massload.xmlbeans.impl;
/**
 * A document containing one TrasferimentoDati(@xmlbeans.massload.simog.avlp.it) element.
 *
 * This is a complex type.
 */
public class TrasferimentoDatiDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument
{
    
    public TrasferimentoDatiDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRASFERIMENTODATI$0 = 
        new javax.xml.namespace.QName("xmlbeans.massload.simog.avlp.it", "TrasferimentoDati");
    
    
    /**
     * Gets the "TrasferimentoDati" element
     */
    public it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati getTrasferimentoDati()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati target = null;
            target = (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati)get_store().find_element_user(TRASFERIMENTODATI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "TrasferimentoDati" element
     */
    public void setTrasferimentoDati(it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati trasferimentoDati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati target = null;
            target = (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati)get_store().find_element_user(TRASFERIMENTODATI$0, 0);
            if (target == null)
            {
                target = (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati)get_store().add_element_user(TRASFERIMENTODATI$0);
            }
            target.set(trasferimentoDati);
        }
    }
    
    /**
     * Appends and returns a new empty "TrasferimentoDati" element
     */
    public it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati addNewTrasferimentoDati()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati target = null;
            target = (it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati)get_store().add_element_user(TRASFERIMENTODATI$0);
            return target;
        }
    }
    /**
     * An XML TrasferimentoDati(@xmlbeans.massload.simog.avlp.it).
     *
     * This is a complex type.
     */
    public static class TrasferimentoDatiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati
    {
        
        public TrasferimentoDatiImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName INFOTRASFERIMENTO$0 = 
            new javax.xml.namespace.QName("", "InfoTrasferimento");
        private static final javax.xml.namespace.QName SCHEDE$2 = 
            new javax.xml.namespace.QName("", "Schede");
        private static final javax.xml.namespace.QName SCHEDEELIMINATE$4 = 
            new javax.xml.namespace.QName("", "SchedeEliminate");
        private static final javax.xml.namespace.QName VARIAZIONIANAG$6 = 
            new javax.xml.namespace.QName("", "VariazioniAnag");
        private static final javax.xml.namespace.QName RESPONSABILI$8 = 
            new javax.xml.namespace.QName("", "Responsabili");
        private static final javax.xml.namespace.QName AGGIUDICATARI$10 = 
            new javax.xml.namespace.QName("", "Aggiudicatari");
        private static final javax.xml.namespace.QName VARIAZIONISA$12 = 
            new javax.xml.namespace.QName("", "VariazioniSA");
        
        
        /**
         * Gets the "InfoTrasferimento" element
         */
        public it.avlp.simog.massload.xmlbeans.TrasferimentoType getInfoTrasferimento()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.TrasferimentoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.TrasferimentoType)get_store().find_element_user(INFOTRASFERIMENTO$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "InfoTrasferimento" element
         */
        public void setInfoTrasferimento(it.avlp.simog.massload.xmlbeans.TrasferimentoType infoTrasferimento)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.TrasferimentoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.TrasferimentoType)get_store().find_element_user(INFOTRASFERIMENTO$0, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.TrasferimentoType)get_store().add_element_user(INFOTRASFERIMENTO$0);
                }
                target.set(infoTrasferimento);
            }
        }
        
        /**
         * Appends and returns a new empty "InfoTrasferimento" element
         */
        public it.avlp.simog.massload.xmlbeans.TrasferimentoType addNewInfoTrasferimento()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.TrasferimentoType target = null;
                target = (it.avlp.simog.massload.xmlbeans.TrasferimentoType)get_store().add_element_user(INFOTRASFERIMENTO$0);
                return target;
            }
        }
        
        /**
         * Gets array of all "Schede" elements
         */
        public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[] getSchedeArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(SCHEDE$2, targetList);
                it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[] result = new it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "Schede" element
         */
        public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType getSchedeArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType target = null;
                target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().find_element_user(SCHEDE$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "Schede" element
         */
        public int sizeOfSchedeArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SCHEDE$2);
            }
        }
        
        /**
         * Sets array of all "Schede" element
         */
        public void setSchedeArray(it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType[] schedeArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(schedeArray, SCHEDE$2);
            }
        }
        
        /**
         * Sets ith "Schede" element
         */
        public void setSchedeArray(int i, it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType schede)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType target = null;
                target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().find_element_user(SCHEDE$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(schede);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "Schede" element
         */
        public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType insertNewSchede(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType target = null;
                target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().insert_element_user(SCHEDE$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "Schede" element
         */
        public it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType addNewSchede()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType target = null;
                target = (it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType)get_store().add_element_user(SCHEDE$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "Schede" element
         */
        public void removeSchede(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SCHEDE$2, i);
            }
        }
        
        /**
         * Gets array of all "SchedeEliminate" elements
         */
        public it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType[] getSchedeEliminateArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(SCHEDEELIMINATE$4, targetList);
                it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType[] result = new it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "SchedeEliminate" element
         */
        public it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType getSchedeEliminateArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType target = null;
                target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().find_element_user(SCHEDEELIMINATE$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "SchedeEliminate" element
         */
        public int sizeOfSchedeEliminateArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SCHEDEELIMINATE$4);
            }
        }
        
        /**
         * Sets array of all "SchedeEliminate" element
         */
        public void setSchedeEliminateArray(it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType[] schedeEliminateArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(schedeEliminateArray, SCHEDEELIMINATE$4);
            }
        }
        
        /**
         * Sets ith "SchedeEliminate" element
         */
        public void setSchedeEliminateArray(int i, it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType schedeEliminate)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType target = null;
                target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().find_element_user(SCHEDEELIMINATE$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(schedeEliminate);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "SchedeEliminate" element
         */
        public it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType insertNewSchedeEliminate(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType target = null;
                target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().insert_element_user(SCHEDEELIMINATE$4, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "SchedeEliminate" element
         */
        public it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType addNewSchedeEliminate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType target = null;
                target = (it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType)get_store().add_element_user(SCHEDEELIMINATE$4);
                return target;
            }
        }
        
        /**
         * Removes the ith "SchedeEliminate" element
         */
        public void removeSchedeEliminate(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SCHEDEELIMINATE$4, i);
            }
        }
        
        /**
         * Gets array of all "VariazioniAnag" elements
         */
        public it.avlp.simog.massload.xmlbeans.VarAnagType[] getVariazioniAnagArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(VARIAZIONIANAG$6, targetList);
                it.avlp.simog.massload.xmlbeans.VarAnagType[] result = new it.avlp.simog.massload.xmlbeans.VarAnagType[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "VariazioniAnag" element
         */
        public it.avlp.simog.massload.xmlbeans.VarAnagType getVariazioniAnagArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.VarAnagType target = null;
                target = (it.avlp.simog.massload.xmlbeans.VarAnagType)get_store().find_element_user(VARIAZIONIANAG$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "VariazioniAnag" element
         */
        public int sizeOfVariazioniAnagArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(VARIAZIONIANAG$6);
            }
        }
        
        /**
         * Sets array of all "VariazioniAnag" element
         */
        public void setVariazioniAnagArray(it.avlp.simog.massload.xmlbeans.VarAnagType[] variazioniAnagArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(variazioniAnagArray, VARIAZIONIANAG$6);
            }
        }
        
        /**
         * Sets ith "VariazioniAnag" element
         */
        public void setVariazioniAnagArray(int i, it.avlp.simog.massload.xmlbeans.VarAnagType variazioniAnag)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.VarAnagType target = null;
                target = (it.avlp.simog.massload.xmlbeans.VarAnagType)get_store().find_element_user(VARIAZIONIANAG$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(variazioniAnag);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "VariazioniAnag" element
         */
        public it.avlp.simog.massload.xmlbeans.VarAnagType insertNewVariazioniAnag(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.VarAnagType target = null;
                target = (it.avlp.simog.massload.xmlbeans.VarAnagType)get_store().insert_element_user(VARIAZIONIANAG$6, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "VariazioniAnag" element
         */
        public it.avlp.simog.massload.xmlbeans.VarAnagType addNewVariazioniAnag()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.VarAnagType target = null;
                target = (it.avlp.simog.massload.xmlbeans.VarAnagType)get_store().add_element_user(VARIAZIONIANAG$6);
                return target;
            }
        }
        
        /**
         * Removes the ith "VariazioniAnag" element
         */
        public void removeVariazioniAnag(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(VARIAZIONIANAG$6, i);
            }
        }
        
        /**
         * Gets the "Responsabili" element
         */
        public it.avlp.simog.massload.xmlbeans.ResponsabiliType getResponsabili()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.ResponsabiliType target = null;
                target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().find_element_user(RESPONSABILI$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "Responsabili" element
         */
        public boolean isSetResponsabili()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RESPONSABILI$8) != 0;
            }
        }
        
        /**
         * Sets the "Responsabili" element
         */
        public void setResponsabili(it.avlp.simog.massload.xmlbeans.ResponsabiliType responsabili)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.ResponsabiliType target = null;
                target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().find_element_user(RESPONSABILI$8, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().add_element_user(RESPONSABILI$8);
                }
                target.set(responsabili);
            }
        }
        
        /**
         * Appends and returns a new empty "Responsabili" element
         */
        public it.avlp.simog.massload.xmlbeans.ResponsabiliType addNewResponsabili()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.ResponsabiliType target = null;
                target = (it.avlp.simog.massload.xmlbeans.ResponsabiliType)get_store().add_element_user(RESPONSABILI$8);
                return target;
            }
        }
        
        /**
         * Unsets the "Responsabili" element
         */
        public void unsetResponsabili()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RESPONSABILI$8, 0);
            }
        }
        
        /**
         * Gets the "Aggiudicatari" element
         */
        public it.avlp.simog.massload.xmlbeans.AggiudicatariType getAggiudicatari()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.AggiudicatariType target = null;
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().find_element_user(AGGIUDICATARI$10, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "Aggiudicatari" element
         */
        public boolean isSetAggiudicatari()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(AGGIUDICATARI$10) != 0;
            }
        }
        
        /**
         * Sets the "Aggiudicatari" element
         */
        public void setAggiudicatari(it.avlp.simog.massload.xmlbeans.AggiudicatariType aggiudicatari)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.AggiudicatariType target = null;
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().find_element_user(AGGIUDICATARI$10, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().add_element_user(AGGIUDICATARI$10);
                }
                target.set(aggiudicatari);
            }
        }
        
        /**
         * Appends and returns a new empty "Aggiudicatari" element
         */
        public it.avlp.simog.massload.xmlbeans.AggiudicatariType addNewAggiudicatari()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.AggiudicatariType target = null;
                target = (it.avlp.simog.massload.xmlbeans.AggiudicatariType)get_store().add_element_user(AGGIUDICATARI$10);
                return target;
            }
        }
        
        /**
         * Unsets the "Aggiudicatari" element
         */
        public void unsetAggiudicatari()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(AGGIUDICATARI$10, 0);
            }
        }
        
        /**
         * Gets the "VariazioniSA" element
         */
        public it.avlp.simog.massload.xmlbeans.VariazioneSAType getVariazioniSA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.VariazioneSAType target = null;
                target = (it.avlp.simog.massload.xmlbeans.VariazioneSAType)get_store().find_element_user(VARIAZIONISA$12, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "VariazioniSA" element
         */
        public boolean isSetVariazioniSA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(VARIAZIONISA$12) != 0;
            }
        }
        
        /**
         * Sets the "VariazioniSA" element
         */
        public void setVariazioniSA(it.avlp.simog.massload.xmlbeans.VariazioneSAType variazioniSA)
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.VariazioneSAType target = null;
                target = (it.avlp.simog.massload.xmlbeans.VariazioneSAType)get_store().find_element_user(VARIAZIONISA$12, 0);
                if (target == null)
                {
                    target = (it.avlp.simog.massload.xmlbeans.VariazioneSAType)get_store().add_element_user(VARIAZIONISA$12);
                }
                target.set(variazioniSA);
            }
        }
        
        /**
         * Appends and returns a new empty "VariazioniSA" element
         */
        public it.avlp.simog.massload.xmlbeans.VariazioneSAType addNewVariazioniSA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                it.avlp.simog.massload.xmlbeans.VariazioneSAType target = null;
                target = (it.avlp.simog.massload.xmlbeans.VariazioneSAType)get_store().add_element_user(VARIAZIONISA$12);
                return target;
            }
        }
        
        /**
         * Unsets the "VariazioniSA" element
         */
        public void unsetVariazioniSA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(VARIAZIONISA$12, 0);
            }
        }
    }
}
