/**
 * AVCPWSFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.anagrafe.AnagrafeWS;

public class AVCPWSFault  implements java.io.Serializable {
    private java.lang.String faultcode;

    private java.lang.String[] faultreason;

    public AVCPWSFault() {
    }

    public AVCPWSFault(
           java.lang.String faultcode,
           java.lang.String[] faultreason) {
           this.faultcode = faultcode;
           this.faultreason = faultreason;
    }


    /**
     * Gets the faultcode value for this AVCPWSFault.
     * 
     * @return faultcode
     */
    public java.lang.String getFaultcode() {
        return faultcode;
    }


    /**
     * Sets the faultcode value for this AVCPWSFault.
     * 
     * @param faultcode
     */
    public void setFaultcode(java.lang.String faultcode) {
        this.faultcode = faultcode;
    }


    /**
     * Gets the faultreason value for this AVCPWSFault.
     * 
     * @return faultreason
     */
    public java.lang.String[] getFaultreason() {
        return faultreason;
    }


    /**
     * Sets the faultreason value for this AVCPWSFault.
     * 
     * @param faultreason
     */
    public void setFaultreason(java.lang.String[] faultreason) {
        this.faultreason = faultreason;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AVCPWSFault)) return false;
        AVCPWSFault other = (AVCPWSFault) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.faultcode==null && other.getFaultcode()==null) || 
             (this.faultcode!=null &&
              this.faultcode.equals(other.getFaultcode()))) &&
            ((this.faultreason==null && other.getFaultreason()==null) || 
             (this.faultreason!=null &&
              java.util.Arrays.equals(this.faultreason, other.getFaultreason())));
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
        if (getFaultcode() != null) {
            _hashCode += getFaultcode().hashCode();
        }
        if (getFaultreason() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFaultreason());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFaultreason(), i);
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
        new org.apache.axis.description.TypeDesc(AVCPWSFault.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://anagrafe.avcp.it/AnagrafeWS/", "AVCPWSFault"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultcode");
        elemField.setXmlName(new javax.xml.namespace.QName("https://anagrafe.avcp.it/AnagrafeWS/", "faultcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultreason");
        elemField.setXmlName(new javax.xml.namespace.QName("https://anagrafe.avcp.it/AnagrafeWS/", "faultreason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("https://anagrafe.avcp.it/AnagrafeWS/", "reasonText"));
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
