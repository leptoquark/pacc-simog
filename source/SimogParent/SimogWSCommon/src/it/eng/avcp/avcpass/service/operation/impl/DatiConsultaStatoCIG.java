/**
 * DatiConsultaStatoCIG.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.avcp.avcpass.service.operation.impl;

public class DatiConsultaStatoCIG  implements java.io.Serializable {
    private java.lang.String cig;

    private java.lang.String[] elencoFunzioniAbilitate;

    private java.lang.Long idStato;

    public DatiConsultaStatoCIG() {
    }

    public DatiConsultaStatoCIG(
           java.lang.String cig,
           java.lang.String[] elencoFunzioniAbilitate,
           java.lang.Long idStato) {
           this.cig = cig;
           this.elencoFunzioniAbilitate = elencoFunzioniAbilitate;
           this.idStato = idStato;
    }


    /**
     * Gets the cig value for this DatiConsultaStatoCIG.
     * 
     * @return cig
     */
    public java.lang.String getCig() {
        return cig;
    }


    /**
     * Sets the cig value for this DatiConsultaStatoCIG.
     * 
     * @param cig
     */
    public void setCig(java.lang.String cig) {
        this.cig = cig;
    }


    /**
     * Gets the elencoFunzioniAbilitate value for this DatiConsultaStatoCIG.
     * 
     * @return elencoFunzioniAbilitate
     */
    public java.lang.String[] getElencoFunzioniAbilitate() {
        return elencoFunzioniAbilitate;
    }


    /**
     * Sets the elencoFunzioniAbilitate value for this DatiConsultaStatoCIG.
     * 
     * @param elencoFunzioniAbilitate
     */
    public void setElencoFunzioniAbilitate(java.lang.String[] elencoFunzioniAbilitate) {
        this.elencoFunzioniAbilitate = elencoFunzioniAbilitate;
    }

    public java.lang.String getElencoFunzioniAbilitate(int i) {
        return this.elencoFunzioniAbilitate[i];
    }

    public void setElencoFunzioniAbilitate(int i, java.lang.String _value) {
        this.elencoFunzioniAbilitate[i] = _value;
    }


    /**
     * Gets the idStato value for this DatiConsultaStatoCIG.
     * 
     * @return idStato
     */
    public java.lang.Long getIdStato() {
        return idStato;
    }


    /**
     * Sets the idStato value for this DatiConsultaStatoCIG.
     * 
     * @param idStato
     */
    public void setIdStato(java.lang.Long idStato) {
        this.idStato = idStato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiConsultaStatoCIG)) return false;
        DatiConsultaStatoCIG other = (DatiConsultaStatoCIG) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cig==null && other.getCig()==null) || 
             (this.cig!=null &&
              this.cig.equals(other.getCig()))) &&
            ((this.elencoFunzioniAbilitate==null && other.getElencoFunzioniAbilitate()==null) || 
             (this.elencoFunzioniAbilitate!=null &&
              java.util.Arrays.equals(this.elencoFunzioniAbilitate, other.getElencoFunzioniAbilitate()))) &&
            ((this.idStato==null && other.getIdStato()==null) || 
             (this.idStato!=null &&
              this.idStato.equals(other.getIdStato())));
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
        if (getCig() != null) {
            _hashCode += getCig().hashCode();
        }
        if (getElencoFunzioniAbilitate() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoFunzioniAbilitate());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoFunzioniAbilitate(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdStato() != null) {
            _hashCode += getIdStato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiConsultaStatoCIG.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.operation.service.avcpass.avcp.eng.it/", "datiConsultaStatoCIG"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cig");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoFunzioniAbilitate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoFunzioniAbilitate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idStato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idStato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
