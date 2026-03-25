/**
 * ParametroContributo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.spc.appalti.ejbImpl.servizi;

public class ParametroContributo  implements java.io.Serializable {
    private java.lang.Long codParametroContributo;

    private java.lang.Long codTipoBaseImponibile;

    private java.lang.Long codTipoContribuente;

    private java.lang.String codice;

    private java.util.Calendar dataDelibera;

    private java.util.Calendar dataFineValidita;

    private java.util.Calendar dataInizioValidita;

    private java.lang.String decorrenza;

    private java.lang.String descrizione;

    private java.math.BigDecimal fasciaA;

    private java.math.BigDecimal fasciaDa;

    private java.math.BigDecimal importo;

    private java.lang.String numDelibera;

    private java.math.BigDecimal percentualeRiferimento;

    private java.lang.Short scadenza;

    public ParametroContributo() {
    }

    public ParametroContributo(
           java.lang.Long codParametroContributo,
           java.lang.Long codTipoBaseImponibile,
           java.lang.Long codTipoContribuente,
           java.lang.String codice,
           java.util.Calendar dataDelibera,
           java.util.Calendar dataFineValidita,
           java.util.Calendar dataInizioValidita,
           java.lang.String decorrenza,
           java.lang.String descrizione,
           java.math.BigDecimal fasciaA,
           java.math.BigDecimal fasciaDa,
           java.math.BigDecimal importo,
           java.lang.String numDelibera,
           java.math.BigDecimal percentualeRiferimento,
           java.lang.Short scadenza) {
           this.codParametroContributo = codParametroContributo;
           this.codTipoBaseImponibile = codTipoBaseImponibile;
           this.codTipoContribuente = codTipoContribuente;
           this.codice = codice;
           this.dataDelibera = dataDelibera;
           this.dataFineValidita = dataFineValidita;
           this.dataInizioValidita = dataInizioValidita;
           this.decorrenza = decorrenza;
           this.descrizione = descrizione;
           this.fasciaA = fasciaA;
           this.fasciaDa = fasciaDa;
           this.importo = importo;
           this.numDelibera = numDelibera;
           this.percentualeRiferimento = percentualeRiferimento;
           this.scadenza = scadenza;
    }


    /**
     * Gets the codParametroContributo value for this ParametroContributo.
     * 
     * @return codParametroContributo
     */
    public java.lang.Long getCodParametroContributo() {
        return codParametroContributo;
    }


    /**
     * Sets the codParametroContributo value for this ParametroContributo.
     * 
     * @param codParametroContributo
     */
    public void setCodParametroContributo(java.lang.Long codParametroContributo) {
        this.codParametroContributo = codParametroContributo;
    }


    /**
     * Gets the codTipoBaseImponibile value for this ParametroContributo.
     * 
     * @return codTipoBaseImponibile
     */
    public java.lang.Long getCodTipoBaseImponibile() {
        return codTipoBaseImponibile;
    }


    /**
     * Sets the codTipoBaseImponibile value for this ParametroContributo.
     * 
     * @param codTipoBaseImponibile
     */
    public void setCodTipoBaseImponibile(java.lang.Long codTipoBaseImponibile) {
        this.codTipoBaseImponibile = codTipoBaseImponibile;
    }


    /**
     * Gets the codTipoContribuente value for this ParametroContributo.
     * 
     * @return codTipoContribuente
     */
    public java.lang.Long getCodTipoContribuente() {
        return codTipoContribuente;
    }


    /**
     * Sets the codTipoContribuente value for this ParametroContributo.
     * 
     * @param codTipoContribuente
     */
    public void setCodTipoContribuente(java.lang.Long codTipoContribuente) {
        this.codTipoContribuente = codTipoContribuente;
    }


    /**
     * Gets the codice value for this ParametroContributo.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this ParametroContributo.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the dataDelibera value for this ParametroContributo.
     * 
     * @return dataDelibera
     */
    public java.util.Calendar getDataDelibera() {
        return dataDelibera;
    }


    /**
     * Sets the dataDelibera value for this ParametroContributo.
     * 
     * @param dataDelibera
     */
    public void setDataDelibera(java.util.Calendar dataDelibera) {
        this.dataDelibera = dataDelibera;
    }


    /**
     * Gets the dataFineValidita value for this ParametroContributo.
     * 
     * @return dataFineValidita
     */
    public java.util.Calendar getDataFineValidita() {
        return dataFineValidita;
    }


    /**
     * Sets the dataFineValidita value for this ParametroContributo.
     * 
     * @param dataFineValidita
     */
    public void setDataFineValidita(java.util.Calendar dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }


    /**
     * Gets the dataInizioValidita value for this ParametroContributo.
     * 
     * @return dataInizioValidita
     */
    public java.util.Calendar getDataInizioValidita() {
        return dataInizioValidita;
    }


    /**
     * Sets the dataInizioValidita value for this ParametroContributo.
     * 
     * @param dataInizioValidita
     */
    public void setDataInizioValidita(java.util.Calendar dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }


    /**
     * Gets the decorrenza value for this ParametroContributo.
     * 
     * @return decorrenza
     */
    public java.lang.String getDecorrenza() {
        return decorrenza;
    }


    /**
     * Sets the decorrenza value for this ParametroContributo.
     * 
     * @param decorrenza
     */
    public void setDecorrenza(java.lang.String decorrenza) {
        this.decorrenza = decorrenza;
    }


    /**
     * Gets the descrizione value for this ParametroContributo.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ParametroContributo.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the fasciaA value for this ParametroContributo.
     * 
     * @return fasciaA
     */
    public java.math.BigDecimal getFasciaA() {
        return fasciaA;
    }


    /**
     * Sets the fasciaA value for this ParametroContributo.
     * 
     * @param fasciaA
     */
    public void setFasciaA(java.math.BigDecimal fasciaA) {
        this.fasciaA = fasciaA;
    }


    /**
     * Gets the fasciaDa value for this ParametroContributo.
     * 
     * @return fasciaDa
     */
    public java.math.BigDecimal getFasciaDa() {
        return fasciaDa;
    }


    /**
     * Sets the fasciaDa value for this ParametroContributo.
     * 
     * @param fasciaDa
     */
    public void setFasciaDa(java.math.BigDecimal fasciaDa) {
        this.fasciaDa = fasciaDa;
    }


    /**
     * Gets the importo value for this ParametroContributo.
     * 
     * @return importo
     */
    public java.math.BigDecimal getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this ParametroContributo.
     * 
     * @param importo
     */
    public void setImporto(java.math.BigDecimal importo) {
        this.importo = importo;
    }


    /**
     * Gets the numDelibera value for this ParametroContributo.
     * 
     * @return numDelibera
     */
    public java.lang.String getNumDelibera() {
        return numDelibera;
    }


    /**
     * Sets the numDelibera value for this ParametroContributo.
     * 
     * @param numDelibera
     */
    public void setNumDelibera(java.lang.String numDelibera) {
        this.numDelibera = numDelibera;
    }


    /**
     * Gets the percentualeRiferimento value for this ParametroContributo.
     * 
     * @return percentualeRiferimento
     */
    public java.math.BigDecimal getPercentualeRiferimento() {
        return percentualeRiferimento;
    }


    /**
     * Sets the percentualeRiferimento value for this ParametroContributo.
     * 
     * @param percentualeRiferimento
     */
    public void setPercentualeRiferimento(java.math.BigDecimal percentualeRiferimento) {
        this.percentualeRiferimento = percentualeRiferimento;
    }


    /**
     * Gets the scadenza value for this ParametroContributo.
     * 
     * @return scadenza
     */
    public java.lang.Short getScadenza() {
        return scadenza;
    }


    /**
     * Sets the scadenza value for this ParametroContributo.
     * 
     * @param scadenza
     */
    public void setScadenza(java.lang.Short scadenza) {
        this.scadenza = scadenza;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ParametroContributo)) return false;
        ParametroContributo other = (ParametroContributo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codParametroContributo==null && other.getCodParametroContributo()==null) || 
             (this.codParametroContributo!=null &&
              this.codParametroContributo.equals(other.getCodParametroContributo()))) &&
            ((this.codTipoBaseImponibile==null && other.getCodTipoBaseImponibile()==null) || 
             (this.codTipoBaseImponibile!=null &&
              this.codTipoBaseImponibile.equals(other.getCodTipoBaseImponibile()))) &&
            ((this.codTipoContribuente==null && other.getCodTipoContribuente()==null) || 
             (this.codTipoContribuente!=null &&
              this.codTipoContribuente.equals(other.getCodTipoContribuente()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.dataDelibera==null && other.getDataDelibera()==null) || 
             (this.dataDelibera!=null &&
              this.dataDelibera.equals(other.getDataDelibera()))) &&
            ((this.dataFineValidita==null && other.getDataFineValidita()==null) || 
             (this.dataFineValidita!=null &&
              this.dataFineValidita.equals(other.getDataFineValidita()))) &&
            ((this.dataInizioValidita==null && other.getDataInizioValidita()==null) || 
             (this.dataInizioValidita!=null &&
              this.dataInizioValidita.equals(other.getDataInizioValidita()))) &&
            ((this.decorrenza==null && other.getDecorrenza()==null) || 
             (this.decorrenza!=null &&
              this.decorrenza.equals(other.getDecorrenza()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.fasciaA==null && other.getFasciaA()==null) || 
             (this.fasciaA!=null &&
              this.fasciaA.equals(other.getFasciaA()))) &&
            ((this.fasciaDa==null && other.getFasciaDa()==null) || 
             (this.fasciaDa!=null &&
              this.fasciaDa.equals(other.getFasciaDa()))) &&
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
            ((this.numDelibera==null && other.getNumDelibera()==null) || 
             (this.numDelibera!=null &&
              this.numDelibera.equals(other.getNumDelibera()))) &&
            ((this.percentualeRiferimento==null && other.getPercentualeRiferimento()==null) || 
             (this.percentualeRiferimento!=null &&
              this.percentualeRiferimento.equals(other.getPercentualeRiferimento()))) &&
            ((this.scadenza==null && other.getScadenza()==null) || 
             (this.scadenza!=null &&
              this.scadenza.equals(other.getScadenza())));
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
        if (getCodParametroContributo() != null) {
            _hashCode += getCodParametroContributo().hashCode();
        }
        if (getCodTipoBaseImponibile() != null) {
            _hashCode += getCodTipoBaseImponibile().hashCode();
        }
        if (getCodTipoContribuente() != null) {
            _hashCode += getCodTipoContribuente().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDataDelibera() != null) {
            _hashCode += getDataDelibera().hashCode();
        }
        if (getDataFineValidita() != null) {
            _hashCode += getDataFineValidita().hashCode();
        }
        if (getDataInizioValidita() != null) {
            _hashCode += getDataInizioValidita().hashCode();
        }
        if (getDecorrenza() != null) {
            _hashCode += getDecorrenza().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getFasciaA() != null) {
            _hashCode += getFasciaA().hashCode();
        }
        if (getFasciaDa() != null) {
            _hashCode += getFasciaDa().hashCode();
        }
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getNumDelibera() != null) {
            _hashCode += getNumDelibera().hashCode();
        }
        if (getPercentualeRiferimento() != null) {
            _hashCode += getPercentualeRiferimento().hashCode();
        }
        if (getScadenza() != null) {
            _hashCode += getScadenza().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ParametroContributo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "parametroContributo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codParametroContributo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codParametroContributo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codTipoBaseImponibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codTipoBaseImponibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codTipoContribuente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codTipoContribuente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDelibera");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataDelibera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFineValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizioValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("decorrenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "decorrenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasciaA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasciaA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasciaDa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasciaDa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numDelibera");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numDelibera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentualeRiferimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percentualeRiferimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
