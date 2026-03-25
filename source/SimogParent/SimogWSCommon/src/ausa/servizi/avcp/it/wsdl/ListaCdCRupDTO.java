/**
 * ListaCdCRupDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public class ListaCdCRupDTO  implements java.io.Serializable {
    private ausa.servizi.avcp.it.wsdl.EsitoDTO esito;

    private ausa.servizi.avcp.it.wsdl.CdCRupDTO[] lista;

    public ListaCdCRupDTO() {
    }

    public ListaCdCRupDTO(
           ausa.servizi.avcp.it.wsdl.EsitoDTO esito,
           ausa.servizi.avcp.it.wsdl.CdCRupDTO[] lista) {
           this.esito = esito;
           this.lista = lista;
    }


    /**
     * Gets the esito value for this ListaCdCRupDTO.
     * 
     * @return esito
     */
    public ausa.servizi.avcp.it.wsdl.EsitoDTO getEsito() {
        return esito;
    }


    /**
     * Sets the esito value for this ListaCdCRupDTO.
     * 
     * @param esito
     */
    public void setEsito(ausa.servizi.avcp.it.wsdl.EsitoDTO esito) {
        this.esito = esito;
    }


    /**
     * Gets the lista value for this ListaCdCRupDTO.
     * 
     * @return lista
     */
    public ausa.servizi.avcp.it.wsdl.CdCRupDTO[] getLista() {
        return lista;
    }


    /**
     * Sets the lista value for this ListaCdCRupDTO.
     * 
     * @param lista
     */
    public void setLista(ausa.servizi.avcp.it.wsdl.CdCRupDTO[] lista) {
        this.lista = lista;
    }

    public ausa.servizi.avcp.it.wsdl.CdCRupDTO getLista(int i) {
        return this.lista[i];
    }

    public void setLista(int i, ausa.servizi.avcp.it.wsdl.CdCRupDTO _value) {
        this.lista[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaCdCRupDTO)) return false;
        ListaCdCRupDTO other = (ListaCdCRupDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.esito==null && other.getEsito()==null) || 
             (this.esito!=null &&
              this.esito.equals(other.getEsito()))) &&
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
        if (getEsito() != null) {
            _hashCode += getEsito().hashCode();
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
        new org.apache.axis.description.TypeDesc(ListaCdCRupDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaCdCRupDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "esitoDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lista");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "cdCRupDTO"));
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
