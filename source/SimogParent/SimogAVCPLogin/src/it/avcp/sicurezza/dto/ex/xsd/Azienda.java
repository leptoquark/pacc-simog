/**
 * Azienda.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.dto.ex.xsd;

public class Azienda  implements java.io.Serializable {
    private java.lang.String codice_fiscale;

    private java.lang.String denominazione;

    private java.lang.String id_osservatorio;

    public Azienda() {
    }

    public Azienda(
           java.lang.String codice_fiscale,
           java.lang.String denominazione,
           java.lang.String id_osservatorio) {
           this.codice_fiscale = codice_fiscale;
           this.denominazione = denominazione;
           this.id_osservatorio = id_osservatorio;
    }


    /**
     * Gets the codice_fiscale value for this Azienda.
     * 
     * @return codice_fiscale
     */
    public java.lang.String getCodice_fiscale() {
        return codice_fiscale;
    }


    /**
     * Sets the codice_fiscale value for this Azienda.
     * 
     * @param codice_fiscale
     */
    public void setCodice_fiscale(java.lang.String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }


    /**
     * Gets the denominazione value for this Azienda.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this Azienda.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the id_osservatorio value for this Azienda.
     * 
     * @return id_osservatorio
     */
    public java.lang.String getId_osservatorio() {
        return id_osservatorio;
    }


    /**
     * Sets the id_osservatorio value for this Azienda.
     * 
     * @param id_osservatorio
     */
    public void setId_osservatorio(java.lang.String id_osservatorio) {
        this.id_osservatorio = id_osservatorio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Azienda)) return false;
        Azienda other = (Azienda) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codice_fiscale==null && other.getCodice_fiscale()==null) || 
             (this.codice_fiscale!=null &&
              this.codice_fiscale.equals(other.getCodice_fiscale()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.id_osservatorio==null && other.getId_osservatorio()==null) || 
             (this.id_osservatorio!=null &&
              this.id_osservatorio.equals(other.getId_osservatorio())));
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
        if (getCodice_fiscale() != null) {
            _hashCode += getCodice_fiscale().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getId_osservatorio() != null) {
            _hashCode += getId_osservatorio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Azienda.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Azienda"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice_fiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "codice_fiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_osservatorio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "id_osservatorio"));
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
