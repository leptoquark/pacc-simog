/**
 * Check_login.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.dto.ex.xsd;

public class Check_login  implements java.io.Serializable {
    private it.avcp.sicurezza.dto.ex.xsd.Collaborazione[] collaborazioni;

    private java.lang.String messaggio;

    private it.avcp.sicurezza.dto.ex.xsd.Soggetto soggetto;

    private java.lang.String stato;

    public Check_login() {
    }

    public Check_login(
           it.avcp.sicurezza.dto.ex.xsd.Collaborazione[] collaborazioni,
           java.lang.String messaggio,
           it.avcp.sicurezza.dto.ex.xsd.Soggetto soggetto,
           java.lang.String stato) {
           this.collaborazioni = collaborazioni;
           this.messaggio = messaggio;
           this.soggetto = soggetto;
           this.stato = stato;
    }


    /**
     * Gets the collaborazioni value for this Check_login.
     * 
     * @return collaborazioni
     */
    public it.avcp.sicurezza.dto.ex.xsd.Collaborazione[] getCollaborazioni() {
        return collaborazioni;
    }


    /**
     * Sets the collaborazioni value for this Check_login.
     * 
     * @param collaborazioni
     */
    public void setCollaborazioni(it.avcp.sicurezza.dto.ex.xsd.Collaborazione[] collaborazioni) {
        this.collaborazioni = collaborazioni;
    }

    public it.avcp.sicurezza.dto.ex.xsd.Collaborazione getCollaborazioni(int i) {
        return this.collaborazioni[i];
    }

    public void setCollaborazioni(int i, it.avcp.sicurezza.dto.ex.xsd.Collaborazione _value) {
        this.collaborazioni[i] = _value;
    }


    /**
     * Gets the messaggio value for this Check_login.
     * 
     * @return messaggio
     */
    public java.lang.String getMessaggio() {
        return messaggio;
    }


    /**
     * Sets the messaggio value for this Check_login.
     * 
     * @param messaggio
     */
    public void setMessaggio(java.lang.String messaggio) {
        this.messaggio = messaggio;
    }


    /**
     * Gets the soggetto value for this Check_login.
     * 
     * @return soggetto
     */
    public it.avcp.sicurezza.dto.ex.xsd.Soggetto getSoggetto() {
        return soggetto;
    }


    /**
     * Sets the soggetto value for this Check_login.
     * 
     * @param soggetto
     */
    public void setSoggetto(it.avcp.sicurezza.dto.ex.xsd.Soggetto soggetto) {
        this.soggetto = soggetto;
    }


    /**
     * Gets the stato value for this Check_login.
     * 
     * @return stato
     */
    public java.lang.String getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this Check_login.
     * 
     * @param stato
     */
    public void setStato(java.lang.String stato) {
        this.stato = stato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Check_login)) return false;
        Check_login other = (Check_login) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.collaborazioni==null && other.getCollaborazioni()==null) || 
             (this.collaborazioni!=null &&
              java.util.Arrays.equals(this.collaborazioni, other.getCollaborazioni()))) &&
            ((this.messaggio==null && other.getMessaggio()==null) || 
             (this.messaggio!=null &&
              this.messaggio.equals(other.getMessaggio()))) &&
            ((this.soggetto==null && other.getSoggetto()==null) || 
             (this.soggetto!=null &&
              this.soggetto.equals(other.getSoggetto()))) &&
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
        if (getCollaborazioni() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCollaborazioni());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCollaborazioni(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMessaggio() != null) {
            _hashCode += getMessaggio().hashCode();
        }
        if (getSoggetto() != null) {
            _hashCode += getSoggetto().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Check_login.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Check_login"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collaborazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "collaborazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Collaborazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "messaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "soggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Soggetto"));
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
