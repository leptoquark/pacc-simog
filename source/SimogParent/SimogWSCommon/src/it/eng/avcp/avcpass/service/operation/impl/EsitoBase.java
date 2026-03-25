/**
 * EsitoBase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.avcp.avcpass.service.operation.impl;

public class EsitoBase  implements java.io.Serializable {
    private it.eng.avcp.avcpass.service.operation.impl.MessaggioAVCpass[] esito;

    public EsitoBase() {
    }

    public EsitoBase(
           it.eng.avcp.avcpass.service.operation.impl.MessaggioAVCpass[] esito) {
           this.esito = esito;
    }


    /**
     * Gets the esito value for this EsitoBase.
     * 
     * @return esito
     */
    public it.eng.avcp.avcpass.service.operation.impl.MessaggioAVCpass[] getEsito() {
        return esito;
    }


    /**
     * Sets the esito value for this EsitoBase.
     * 
     * @param esito
     */
    public void setEsito(it.eng.avcp.avcpass.service.operation.impl.MessaggioAVCpass[] esito) {
        this.esito = esito;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EsitoBase)) return false;
        EsitoBase other = (EsitoBase) obj;
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
              java.util.Arrays.equals(this.esito, other.getEsito())));
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
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEsito());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEsito(), i);
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
        new org.apache.axis.description.TypeDesc(EsitoBase.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.operation.service.avcpass.avcp.eng.it/", "esitoBase"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.operation.service.avcpass.avcp.eng.it/", "messaggioAVCpass"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "messaggi"));
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
