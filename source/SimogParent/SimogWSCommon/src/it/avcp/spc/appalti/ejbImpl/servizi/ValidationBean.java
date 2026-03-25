/**
 * ValidationBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.spc.appalti.ejbImpl.servizi;

public class ValidationBean  implements java.io.Serializable {
    private java.lang.String campo;

    private java.lang.String code;

    private int elemento;

    private int progressivo;

    private it.avcp.spc.appalti.ejbImpl.servizi.SeverityLevel severity;

    public ValidationBean() {
    }

    public ValidationBean(
           java.lang.String campo,
           java.lang.String code,
           int elemento,
           int progressivo,
           it.avcp.spc.appalti.ejbImpl.servizi.SeverityLevel severity) {
           this.campo = campo;
           this.code = code;
           this.elemento = elemento;
           this.progressivo = progressivo;
           this.severity = severity;
    }


    /**
     * Gets the campo value for this ValidationBean.
     * 
     * @return campo
     */
    public java.lang.String getCampo() {
        return campo;
    }


    /**
     * Sets the campo value for this ValidationBean.
     * 
     * @param campo
     */
    public void setCampo(java.lang.String campo) {
        this.campo = campo;
    }


    /**
     * Gets the code value for this ValidationBean.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this ValidationBean.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the elemento value for this ValidationBean.
     * 
     * @return elemento
     */
    public int getElemento() {
        return elemento;
    }


    /**
     * Sets the elemento value for this ValidationBean.
     * 
     * @param elemento
     */
    public void setElemento(int elemento) {
        this.elemento = elemento;
    }


    /**
     * Gets the progressivo value for this ValidationBean.
     * 
     * @return progressivo
     */
    public int getProgressivo() {
        return progressivo;
    }


    /**
     * Sets the progressivo value for this ValidationBean.
     * 
     * @param progressivo
     */
    public void setProgressivo(int progressivo) {
        this.progressivo = progressivo;
    }


    /**
     * Gets the severity value for this ValidationBean.
     * 
     * @return severity
     */
    public it.avcp.spc.appalti.ejbImpl.servizi.SeverityLevel getSeverity() {
        return severity;
    }


    /**
     * Sets the severity value for this ValidationBean.
     * 
     * @param severity
     */
    public void setSeverity(it.avcp.spc.appalti.ejbImpl.servizi.SeverityLevel severity) {
        this.severity = severity;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidationBean)) return false;
        ValidationBean other = (ValidationBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.campo==null && other.getCampo()==null) || 
             (this.campo!=null &&
              this.campo.equals(other.getCampo()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            this.elemento == other.getElemento() &&
            this.progressivo == other.getProgressivo() &&
            ((this.severity==null && other.getSeverity()==null) || 
             (this.severity!=null &&
              this.severity.equals(other.getSeverity())));
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
        if (getCampo() != null) {
            _hashCode += getCampo().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        _hashCode += getElemento();
        _hashCode += getProgressivo();
        if (getSeverity() != null) {
            _hashCode += getSeverity().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidationBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "validationBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "campo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elemento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elemento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("progressivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "progressivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("severity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "severity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "severityLevel"));
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
