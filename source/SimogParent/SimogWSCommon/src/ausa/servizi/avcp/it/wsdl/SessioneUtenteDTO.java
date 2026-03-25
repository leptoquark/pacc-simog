/**
 * SessioneUtenteDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public class SessioneUtenteDTO  implements java.io.Serializable {
    private java.lang.String codiceApplicazione;

    private java.lang.String codiceFiscaleUtente;

    private java.lang.String codiceOperazione;

    private java.lang.String codiceSessione;

    private java.lang.String codiceTransazione;

    private java.lang.String entita;

    private java.lang.Long idEntita;

    private java.lang.String SAML;

    public SessioneUtenteDTO() {
    }

    public SessioneUtenteDTO(
           java.lang.String codiceApplicazione,
           java.lang.String codiceFiscaleUtente,
           java.lang.String codiceOperazione,
           java.lang.String codiceSessione,
           java.lang.String codiceTransazione,
           java.lang.String entita,
           java.lang.Long idEntita,
           java.lang.String SAML) {
           this.codiceApplicazione = codiceApplicazione;
           this.codiceFiscaleUtente = codiceFiscaleUtente;
           this.codiceOperazione = codiceOperazione;
           this.codiceSessione = codiceSessione;
           this.codiceTransazione = codiceTransazione;
           this.entita = entita;
           this.idEntita = idEntita;
           this.SAML = SAML;
    }


    /**
     * Gets the codiceApplicazione value for this SessioneUtenteDTO.
     * 
     * @return codiceApplicazione
     */
    public java.lang.String getCodiceApplicazione() {
        return codiceApplicazione;
    }


    /**
     * Sets the codiceApplicazione value for this SessioneUtenteDTO.
     * 
     * @param codiceApplicazione
     */
    public void setCodiceApplicazione(java.lang.String codiceApplicazione) {
        this.codiceApplicazione = codiceApplicazione;
    }


    /**
     * Gets the codiceFiscaleUtente value for this SessioneUtenteDTO.
     * 
     * @return codiceFiscaleUtente
     */
    public java.lang.String getCodiceFiscaleUtente() {
        return codiceFiscaleUtente;
    }


    /**
     * Sets the codiceFiscaleUtente value for this SessioneUtenteDTO.
     * 
     * @param codiceFiscaleUtente
     */
    public void setCodiceFiscaleUtente(java.lang.String codiceFiscaleUtente) {
        this.codiceFiscaleUtente = codiceFiscaleUtente;
    }


    /**
     * Gets the codiceOperazione value for this SessioneUtenteDTO.
     * 
     * @return codiceOperazione
     */
    public java.lang.String getCodiceOperazione() {
        return codiceOperazione;
    }


    /**
     * Sets the codiceOperazione value for this SessioneUtenteDTO.
     * 
     * @param codiceOperazione
     */
    public void setCodiceOperazione(java.lang.String codiceOperazione) {
        this.codiceOperazione = codiceOperazione;
    }


    /**
     * Gets the codiceSessione value for this SessioneUtenteDTO.
     * 
     * @return codiceSessione
     */
    public java.lang.String getCodiceSessione() {
        return codiceSessione;
    }


    /**
     * Sets the codiceSessione value for this SessioneUtenteDTO.
     * 
     * @param codiceSessione
     */
    public void setCodiceSessione(java.lang.String codiceSessione) {
        this.codiceSessione = codiceSessione;
    }


    /**
     * Gets the codiceTransazione value for this SessioneUtenteDTO.
     * 
     * @return codiceTransazione
     */
    public java.lang.String getCodiceTransazione() {
        return codiceTransazione;
    }


    /**
     * Sets the codiceTransazione value for this SessioneUtenteDTO.
     * 
     * @param codiceTransazione
     */
    public void setCodiceTransazione(java.lang.String codiceTransazione) {
        this.codiceTransazione = codiceTransazione;
    }


    /**
     * Gets the entita value for this SessioneUtenteDTO.
     * 
     * @return entita
     */
    public java.lang.String getEntita() {
        return entita;
    }


    /**
     * Sets the entita value for this SessioneUtenteDTO.
     * 
     * @param entita
     */
    public void setEntita(java.lang.String entita) {
        this.entita = entita;
    }


    /**
     * Gets the idEntita value for this SessioneUtenteDTO.
     * 
     * @return idEntita
     */
    public java.lang.Long getIdEntita() {
        return idEntita;
    }


    /**
     * Sets the idEntita value for this SessioneUtenteDTO.
     * 
     * @param idEntita
     */
    public void setIdEntita(java.lang.Long idEntita) {
        this.idEntita = idEntita;
    }


    /**
     * Gets the SAML value for this SessioneUtenteDTO.
     * 
     * @return SAML
     */
    public java.lang.String getSAML() {
        return SAML;
    }


    /**
     * Sets the SAML value for this SessioneUtenteDTO.
     * 
     * @param SAML
     */
    public void setSAML(java.lang.String SAML) {
        this.SAML = SAML;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SessioneUtenteDTO)) return false;
        SessioneUtenteDTO other = (SessioneUtenteDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceApplicazione==null && other.getCodiceApplicazione()==null) || 
             (this.codiceApplicazione!=null &&
              this.codiceApplicazione.equals(other.getCodiceApplicazione()))) &&
            ((this.codiceFiscaleUtente==null && other.getCodiceFiscaleUtente()==null) || 
             (this.codiceFiscaleUtente!=null &&
              this.codiceFiscaleUtente.equals(other.getCodiceFiscaleUtente()))) &&
            ((this.codiceOperazione==null && other.getCodiceOperazione()==null) || 
             (this.codiceOperazione!=null &&
              this.codiceOperazione.equals(other.getCodiceOperazione()))) &&
            ((this.codiceSessione==null && other.getCodiceSessione()==null) || 
             (this.codiceSessione!=null &&
              this.codiceSessione.equals(other.getCodiceSessione()))) &&
            ((this.codiceTransazione==null && other.getCodiceTransazione()==null) || 
             (this.codiceTransazione!=null &&
              this.codiceTransazione.equals(other.getCodiceTransazione()))) &&
            ((this.entita==null && other.getEntita()==null) || 
             (this.entita!=null &&
              this.entita.equals(other.getEntita()))) &&
            ((this.idEntita==null && other.getIdEntita()==null) || 
             (this.idEntita!=null &&
              this.idEntita.equals(other.getIdEntita()))) &&
            ((this.SAML==null && other.getSAML()==null) || 
             (this.SAML!=null &&
              this.SAML.equals(other.getSAML())));
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
        if (getCodiceApplicazione() != null) {
            _hashCode += getCodiceApplicazione().hashCode();
        }
        if (getCodiceFiscaleUtente() != null) {
            _hashCode += getCodiceFiscaleUtente().hashCode();
        }
        if (getCodiceOperazione() != null) {
            _hashCode += getCodiceOperazione().hashCode();
        }
        if (getCodiceSessione() != null) {
            _hashCode += getCodiceSessione().hashCode();
        }
        if (getCodiceTransazione() != null) {
            _hashCode += getCodiceTransazione().hashCode();
        }
        if (getEntita() != null) {
            _hashCode += getEntita().hashCode();
        }
        if (getIdEntita() != null) {
            _hashCode += getIdEntita().hashCode();
        }
        if (getSAML() != null) {
            _hashCode += getSAML().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SessioneUtenteDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceApplicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceApplicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscaleUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFiscaleUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceOperazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceOperazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSessione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSessione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTransazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceTransazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEntita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SAML");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SAML"));
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
