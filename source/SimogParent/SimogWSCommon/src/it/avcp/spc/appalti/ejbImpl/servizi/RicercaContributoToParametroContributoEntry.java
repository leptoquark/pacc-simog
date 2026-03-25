/**
 * RicercaContributoToParametroContributoEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.spc.appalti.ejbImpl.servizi;

public class RicercaContributoToParametroContributoEntry  implements java.io.Serializable {
    private java.math.BigDecimal key;

    private it.avcp.spc.appalti.ejbImpl.servizi.ParametroContributo value;

    public RicercaContributoToParametroContributoEntry() {
    }

    public RicercaContributoToParametroContributoEntry(
           java.math.BigDecimal key,
           it.avcp.spc.appalti.ejbImpl.servizi.ParametroContributo value) {
           this.key = key;
           this.value = value;
    }


    /**
     * Gets the key value for this RicercaContributoToParametroContributoEntry.
     * 
     * @return key
     */
    public java.math.BigDecimal getKey() {
        return key;
    }


    /**
     * Sets the key value for this RicercaContributoToParametroContributoEntry.
     * 
     * @param key
     */
    public void setKey(java.math.BigDecimal key) {
        this.key = key;
    }


    /**
     * Gets the value value for this RicercaContributoToParametroContributoEntry.
     * 
     * @return value
     */
    public it.avcp.spc.appalti.ejbImpl.servizi.ParametroContributo getValue() {
        return value;
    }


    /**
     * Sets the value value for this RicercaContributoToParametroContributoEntry.
     * 
     * @param value
     */
    public void setValue(it.avcp.spc.appalti.ejbImpl.servizi.ParametroContributo value) {
        this.value = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaContributoToParametroContributoEntry)) return false;
        RicercaContributoToParametroContributoEntry other = (RicercaContributoToParametroContributoEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.key==null && other.getKey()==null) || 
             (this.key!=null &&
              this.key.equals(other.getKey()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue())));
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
        if (getKey() != null) {
            _hashCode += getKey().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaContributoToParametroContributoEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", ">>ricercaContributoTo>parametroContributo>entry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("", "key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "parametroContributo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
