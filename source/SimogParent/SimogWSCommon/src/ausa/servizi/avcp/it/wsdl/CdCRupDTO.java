/**
 * CdCRupDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public class CdCRupDTO  implements java.io.Serializable {
    private long codCdC;

    private java.lang.String codice;

    private java.lang.String denominazione;

    private ausa.servizi.avcp.it.wsdl.RupDTO[] lista;

    public CdCRupDTO() {
    }

    public CdCRupDTO(
           long codCdC,
           java.lang.String codice,
           java.lang.String denominazione,
           ausa.servizi.avcp.it.wsdl.RupDTO[] lista) {
           this.codCdC = codCdC;
           this.codice = codice;
           this.denominazione = denominazione;
           this.lista = lista;
    }


    /**
     * Gets the codCdC value for this CdCRupDTO.
     * 
     * @return codCdC
     */
    public long getCodCdC() {
        return codCdC;
    }


    /**
     * Sets the codCdC value for this CdCRupDTO.
     * 
     * @param codCdC
     */
    public void setCodCdC(long codCdC) {
        this.codCdC = codCdC;
    }


    /**
     * Gets the codice value for this CdCRupDTO.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CdCRupDTO.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the denominazione value for this CdCRupDTO.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this CdCRupDTO.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the lista value for this CdCRupDTO.
     * 
     * @return lista
     */
    public ausa.servizi.avcp.it.wsdl.RupDTO[] getLista() {
        return lista;
    }


    /**
     * Sets the lista value for this CdCRupDTO.
     * 
     * @param lista
     */
    public void setLista(ausa.servizi.avcp.it.wsdl.RupDTO[] lista) {
        this.lista = lista;
    }

    public ausa.servizi.avcp.it.wsdl.RupDTO getLista(int i) {
        return this.lista[i];
    }

    public void setLista(int i, ausa.servizi.avcp.it.wsdl.RupDTO _value) {
        this.lista[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CdCRupDTO)) return false;
        CdCRupDTO other = (CdCRupDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codCdC == other.getCodCdC() &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.lista==null && other.getLista()==null) || 
             (this.lista!=null &&
              java.util.Arrays.equals(this.lista, other.getLista())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getCodCdC()).hashCode();
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getLista() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLista());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLista(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CdCRupDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "cdCRupDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCdC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codCdC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lista");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "rupDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
