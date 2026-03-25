/**
 * RupDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public class RupDTO  implements java.io.Serializable {
    private long codProfilo;

    private java.lang.String codiceFiscale;

    private java.lang.String cognome;

    private java.util.Calendar dataFineProfilo;

    private java.util.Calendar dataInizioProfilo;

    private java.lang.String nome;

    private java.lang.String nome2;

    public RupDTO() {
    }

    public RupDTO(
           long codProfilo,
           java.lang.String codiceFiscale,
           java.lang.String cognome,
           java.util.Calendar dataFineProfilo,
           java.util.Calendar dataInizioProfilo,
           java.lang.String nome,
           java.lang.String nome2) {
           this.codProfilo = codProfilo;
           this.codiceFiscale = codiceFiscale;
           this.cognome = cognome;
           this.dataFineProfilo = dataFineProfilo;
           this.dataInizioProfilo = dataInizioProfilo;
           this.nome = nome;
           this.nome2 = nome2;
    }


    /**
     * Gets the codProfilo value for this RupDTO.
     * 
     * @return codProfilo
     */
    public long getCodProfilo() {
        return codProfilo;
    }


    /**
     * Sets the codProfilo value for this RupDTO.
     * 
     * @param codProfilo
     */
    public void setCodProfilo(long codProfilo) {
        this.codProfilo = codProfilo;
    }


    /**
     * Gets the codiceFiscale value for this RupDTO.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this RupDTO.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the cognome value for this RupDTO.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this RupDTO.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the dataFineProfilo value for this RupDTO.
     * 
     * @return dataFineProfilo
     */
    public java.util.Calendar getDataFineProfilo() {
        return dataFineProfilo;
    }


    /**
     * Sets the dataFineProfilo value for this RupDTO.
     * 
     * @param dataFineProfilo
     */
    public void setDataFineProfilo(java.util.Calendar dataFineProfilo) {
        this.dataFineProfilo = dataFineProfilo;
    }


    /**
     * Gets the dataInizioProfilo value for this RupDTO.
     * 
     * @return dataInizioProfilo
     */
    public java.util.Calendar getDataInizioProfilo() {
        return dataInizioProfilo;
    }


    /**
     * Sets the dataInizioProfilo value for this RupDTO.
     * 
     * @param dataInizioProfilo
     */
    public void setDataInizioProfilo(java.util.Calendar dataInizioProfilo) {
        this.dataInizioProfilo = dataInizioProfilo;
    }


    /**
     * Gets the nome value for this RupDTO.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this RupDTO.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the nome2 value for this RupDTO.
     * 
     * @return nome2
     */
    public java.lang.String getNome2() {
        return nome2;
    }


    /**
     * Sets the nome2 value for this RupDTO.
     * 
     * @param nome2
     */
    public void setNome2(java.lang.String nome2) {
        this.nome2 = nome2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RupDTO)) return false;
        RupDTO other = (RupDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codProfilo == other.getCodProfilo() &&
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.dataFineProfilo==null && other.getDataFineProfilo()==null) || 
             (this.dataFineProfilo!=null &&
              this.dataFineProfilo.equals(other.getDataFineProfilo()))) &&
            ((this.dataInizioProfilo==null && other.getDataInizioProfilo()==null) || 
             (this.dataInizioProfilo!=null &&
              this.dataInizioProfilo.equals(other.getDataInizioProfilo()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.nome2==null && other.getNome2()==null) || 
             (this.nome2!=null &&
              this.nome2.equals(other.getNome2())));
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
        _hashCode += new Long(getCodProfilo()).hashCode();
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getDataFineProfilo() != null) {
            _hashCode += getDataFineProfilo().hashCode();
        }
        if (getDataInizioProfilo() != null) {
            _hashCode += getDataInizioProfilo().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getNome2() != null) {
            _hashCode += getNome2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RupDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "rupDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codProfilo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codProfilo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineProfilo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFineProfilo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioProfilo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizioProfilo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
