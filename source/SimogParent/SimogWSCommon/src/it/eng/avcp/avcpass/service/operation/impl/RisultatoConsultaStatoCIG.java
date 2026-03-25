/**
 * RisultatoConsultaStatoCIG.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.avcp.avcpass.service.operation.impl;

public class RisultatoConsultaStatoCIG  extends it.eng.avcp.avcpass.service.operation.impl.EsitoBase  implements java.io.Serializable {
    private it.eng.avcp.avcpass.service.operation.impl.DatiConsultaStatoCIG[] elencoCIG;

    public RisultatoConsultaStatoCIG() {
    }

    public RisultatoConsultaStatoCIG(
           it.eng.avcp.avcpass.service.operation.impl.MessaggioAVCpass[] esito,
           it.eng.avcp.avcpass.service.operation.impl.DatiConsultaStatoCIG[] elencoCIG) {
        super(
            esito);
        this.elencoCIG = elencoCIG;
    }


    /**
     * Gets the elencoCIG value for this RisultatoConsultaStatoCIG.
     * 
     * @return elencoCIG
     */
    public it.eng.avcp.avcpass.service.operation.impl.DatiConsultaStatoCIG[] getElencoCIG() {
        return elencoCIG;
    }


    /**
     * Sets the elencoCIG value for this RisultatoConsultaStatoCIG.
     * 
     * @param elencoCIG
     */
    public void setElencoCIG(it.eng.avcp.avcpass.service.operation.impl.DatiConsultaStatoCIG[] elencoCIG) {
        this.elencoCIG = elencoCIG;
    }

    public it.eng.avcp.avcpass.service.operation.impl.DatiConsultaStatoCIG getElencoCIG(int i) {
        return this.elencoCIG[i];
    }

    public void setElencoCIG(int i, it.eng.avcp.avcpass.service.operation.impl.DatiConsultaStatoCIG _value) {
        this.elencoCIG[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RisultatoConsultaStatoCIG)) return false;
        RisultatoConsultaStatoCIG other = (RisultatoConsultaStatoCIG) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.elencoCIG==null && other.getElencoCIG()==null) || 
             (this.elencoCIG!=null &&
              java.util.Arrays.equals(this.elencoCIG, other.getElencoCIG())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getElencoCIG() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoCIG());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoCIG(), i);
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
        new org.apache.axis.description.TypeDesc(RisultatoConsultaStatoCIG.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.operation.service.avcpass.avcp.eng.it/", "risultatoConsultaStatoCIG"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoCIG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoCIG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.operation.service.avcpass.avcp.eng.it/", "datiConsultaStatoCIG"));
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
