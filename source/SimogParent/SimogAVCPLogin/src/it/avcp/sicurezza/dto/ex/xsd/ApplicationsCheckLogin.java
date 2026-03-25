/**
 * ApplicationsCheckLogin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.dto.ex.xsd;

public class ApplicationsCheckLogin  implements java.io.Serializable {
    private it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin[] applicationCheckLogin;

    private java.lang.String codice;

    private java.lang.String messaggio;

    private java.lang.String stato;

    public ApplicationsCheckLogin() {
    }

    public ApplicationsCheckLogin(
           it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin[] applicationCheckLogin,
           java.lang.String codice,
           java.lang.String messaggio,
           java.lang.String stato) {
           this.applicationCheckLogin = applicationCheckLogin;
           this.codice = codice;
           this.messaggio = messaggio;
           this.stato = stato;
    }


    /**
     * Gets the applicationCheckLogin value for this ApplicationsCheckLogin.
     * 
     * @return applicationCheckLogin
     */
    public it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin[] getApplicationCheckLogin() {
        return applicationCheckLogin;
    }


    /**
     * Sets the applicationCheckLogin value for this ApplicationsCheckLogin.
     * 
     * @param applicationCheckLogin
     */
    public void setApplicationCheckLogin(it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin[] applicationCheckLogin) {
        this.applicationCheckLogin = applicationCheckLogin;
    }

    public it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin getApplicationCheckLogin(int i) {
        return this.applicationCheckLogin[i];
    }

    public void setApplicationCheckLogin(int i, it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin _value) {
        this.applicationCheckLogin[i] = _value;
    }


    /**
     * Gets the codice value for this ApplicationsCheckLogin.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this ApplicationsCheckLogin.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the messaggio value for this ApplicationsCheckLogin.
     * 
     * @return messaggio
     */
    public java.lang.String getMessaggio() {
        return messaggio;
    }


    /**
     * Sets the messaggio value for this ApplicationsCheckLogin.
     * 
     * @param messaggio
     */
    public void setMessaggio(java.lang.String messaggio) {
        this.messaggio = messaggio;
    }


    /**
     * Gets the stato value for this ApplicationsCheckLogin.
     * 
     * @return stato
     */
    public java.lang.String getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this ApplicationsCheckLogin.
     * 
     * @param stato
     */
    public void setStato(java.lang.String stato) {
        this.stato = stato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApplicationsCheckLogin)) return false;
        ApplicationsCheckLogin other = (ApplicationsCheckLogin) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.applicationCheckLogin==null && other.getApplicationCheckLogin()==null) || 
             (this.applicationCheckLogin!=null &&
              java.util.Arrays.equals(this.applicationCheckLogin, other.getApplicationCheckLogin()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.messaggio==null && other.getMessaggio()==null) || 
             (this.messaggio!=null &&
              this.messaggio.equals(other.getMessaggio()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato())));
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
        if (getApplicationCheckLogin() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getApplicationCheckLogin());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getApplicationCheckLogin(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getMessaggio() != null) {
            _hashCode += getMessaggio().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApplicationsCheckLogin.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ApplicationsCheckLogin"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationCheckLogin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "applicationCheckLogin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ApplicationCheckLogin"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "messaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
