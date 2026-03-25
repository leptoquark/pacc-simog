/**
 * ApplicationCheckLogin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.dto.ex.xsd;

public class ApplicationCheckLogin  extends it.avcp.sicurezza.dto.ex.xsd.Check_login  implements java.io.Serializable {
    private it.avcp.sicurezza.dto.ex.xsd.Applicazione applicazione;

    public ApplicationCheckLogin() {
    }

    public ApplicationCheckLogin(
           it.avcp.sicurezza.dto.ex.xsd.Collaborazione[] collaborazioni,
           java.lang.String messaggio,
           it.avcp.sicurezza.dto.ex.xsd.Soggetto soggetto,
           java.lang.String stato,
           it.avcp.sicurezza.dto.ex.xsd.Applicazione applicazione) {
        super(
            collaborazioni,
            messaggio,
            soggetto,
            stato);
        this.applicazione = applicazione;
    }


    /**
     * Gets the applicazione value for this ApplicationCheckLogin.
     * 
     * @return applicazione
     */
    public it.avcp.sicurezza.dto.ex.xsd.Applicazione getApplicazione() {
        return applicazione;
    }


    /**
     * Sets the applicazione value for this ApplicationCheckLogin.
     * 
     * @param applicazione
     */
    public void setApplicazione(it.avcp.sicurezza.dto.ex.xsd.Applicazione applicazione) {
        this.applicazione = applicazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApplicationCheckLogin)) return false;
        ApplicationCheckLogin other = (ApplicationCheckLogin) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.applicazione==null && other.getApplicazione()==null) || 
             (this.applicazione!=null &&
              this.applicazione.equals(other.getApplicazione())));
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
        if (getApplicazione() != null) {
            _hashCode += getApplicazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApplicationCheckLogin.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ApplicationCheckLogin"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "applicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Applicazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
