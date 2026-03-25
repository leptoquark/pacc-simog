/**
 * Collaborazione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.dto.ex.xsd;

public class Collaborazione  implements java.io.Serializable {
    private it.avcp.sicurezza.dto.ex.xsd.Azienda azienda;

    private java.lang.String index;

    private it.avcp.sicurezza.dto.ex.xsd.Ufficio ufficio;

    public Collaborazione() {
    }

    public Collaborazione(
           it.avcp.sicurezza.dto.ex.xsd.Azienda azienda,
           java.lang.String index,
           it.avcp.sicurezza.dto.ex.xsd.Ufficio ufficio) {
           this.azienda = azienda;
           this.index = index;
           this.ufficio = ufficio;
    }


    /**
     * Gets the azienda value for this Collaborazione.
     * 
     * @return azienda
     */
    public it.avcp.sicurezza.dto.ex.xsd.Azienda getAzienda() {
        return azienda;
    }


    /**
     * Sets the azienda value for this Collaborazione.
     * 
     * @param azienda
     */
    public void setAzienda(it.avcp.sicurezza.dto.ex.xsd.Azienda azienda) {
        this.azienda = azienda;
    }


    /**
     * Gets the index value for this Collaborazione.
     * 
     * @return index
     */
    public java.lang.String getIndex() {
        return index;
    }


    /**
     * Sets the index value for this Collaborazione.
     * 
     * @param index
     */
    public void setIndex(java.lang.String index) {
        this.index = index;
    }


    /**
     * Gets the ufficio value for this Collaborazione.
     * 
     * @return ufficio
     */
    public it.avcp.sicurezza.dto.ex.xsd.Ufficio getUfficio() {
        return ufficio;
    }


    /**
     * Sets the ufficio value for this Collaborazione.
     * 
     * @param ufficio
     */
    public void setUfficio(it.avcp.sicurezza.dto.ex.xsd.Ufficio ufficio) {
        this.ufficio = ufficio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Collaborazione)) return false;
        Collaborazione other = (Collaborazione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.azienda==null && other.getAzienda()==null) || 
             (this.azienda!=null &&
              this.azienda.equals(other.getAzienda()))) &&
            ((this.index==null && other.getIndex()==null) || 
             (this.index!=null &&
              this.index.equals(other.getIndex()))) &&
            ((this.ufficio==null && other.getUfficio()==null) || 
             (this.ufficio!=null &&
              this.ufficio.equals(other.getUfficio())));
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
        if (getAzienda() != null) {
            _hashCode += getAzienda().hashCode();
        }
        if (getIndex() != null) {
            _hashCode += getIndex().hashCode();
        }
        if (getUfficio() != null) {
            _hashCode += getUfficio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Collaborazione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Collaborazione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("azienda");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "azienda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Azienda"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("index");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "index"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ufficio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ufficio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Ufficio"));
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
