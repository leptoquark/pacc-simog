/**
 * UrlDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public class UrlDTO  implements java.io.Serializable {
    private java.lang.Integer anno;

    private long cod_ausa;

    private java.util.Calendar data_creazione;

    private long id_url;

    private java.lang.String type_url;

    private java.lang.String url;

    public UrlDTO() {
    }

    public UrlDTO(
           java.lang.Integer anno,
           long cod_ausa,
           java.util.Calendar data_creazione,
           long id_url,
           java.lang.String type_url,
           java.lang.String url) {
           this.anno = anno;
           this.cod_ausa = cod_ausa;
           this.data_creazione = data_creazione;
           this.id_url = id_url;
           this.type_url = type_url;
           this.url = url;
    }


    /**
     * Gets the anno value for this UrlDTO.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this UrlDTO.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the cod_ausa value for this UrlDTO.
     * 
     * @return cod_ausa
     */
    public long getCod_ausa() {
        return cod_ausa;
    }


    /**
     * Sets the cod_ausa value for this UrlDTO.
     * 
     * @param cod_ausa
     */
    public void setCod_ausa(long cod_ausa) {
        this.cod_ausa = cod_ausa;
    }


    /**
     * Gets the data_creazione value for this UrlDTO.
     * 
     * @return data_creazione
     */
    public java.util.Calendar getData_creazione() {
        return data_creazione;
    }


    /**
     * Sets the data_creazione value for this UrlDTO.
     * 
     * @param data_creazione
     */
    public void setData_creazione(java.util.Calendar data_creazione) {
        this.data_creazione = data_creazione;
    }


    /**
     * Gets the id_url value for this UrlDTO.
     * 
     * @return id_url
     */
    public long getId_url() {
        return id_url;
    }


    /**
     * Sets the id_url value for this UrlDTO.
     * 
     * @param id_url
     */
    public void setId_url(long id_url) {
        this.id_url = id_url;
    }


    /**
     * Gets the type_url value for this UrlDTO.
     * 
     * @return type_url
     */
    public java.lang.String getType_url() {
        return type_url;
    }


    /**
     * Sets the type_url value for this UrlDTO.
     * 
     * @param type_url
     */
    public void setType_url(java.lang.String type_url) {
        this.type_url = type_url;
    }


    /**
     * Gets the url value for this UrlDTO.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this UrlDTO.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UrlDTO)) return false;
        UrlDTO other = (UrlDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            this.cod_ausa == other.getCod_ausa() &&
            ((this.data_creazione==null && other.getData_creazione()==null) || 
             (this.data_creazione!=null &&
              this.data_creazione.equals(other.getData_creazione()))) &&
            this.id_url == other.getId_url() &&
            ((this.type_url==null && other.getType_url()==null) || 
             (this.type_url!=null &&
              this.type_url.equals(other.getType_url()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl())));
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
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        _hashCode += new Long(getCod_ausa()).hashCode();
        if (getData_creazione() != null) {
            _hashCode += getData_creazione().hashCode();
        }
        _hashCode += new Long(getId_url()).hashCode();
        if (getType_url() != null) {
            _hashCode += getType_url().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UrlDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "urlDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cod_ausa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cod_ausa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data_creazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data_creazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id_url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type_url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type_url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url"));
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
