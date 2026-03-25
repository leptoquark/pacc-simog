/**
 * RicercaContributoTo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.spc.appalti.ejbImpl.servizi;

public class RicercaContributoTo  implements java.io.Serializable {
    private it.avcp.spc.appalti.ejbImpl.servizi.ValidationBean[] esitoValidazione;

    private java.math.BigDecimal importContributo;

    private it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToMapImportContributoEntry[] mapImportContributo;

    private it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToParametroContributoEntry[] parametroContributo;

    public RicercaContributoTo() {
    }

    public RicercaContributoTo(
           it.avcp.spc.appalti.ejbImpl.servizi.ValidationBean[] esitoValidazione,
           java.math.BigDecimal importContributo,
           it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToMapImportContributoEntry[] mapImportContributo,
           it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToParametroContributoEntry[] parametroContributo) {
           this.esitoValidazione = esitoValidazione;
           this.importContributo = importContributo;
           this.mapImportContributo = mapImportContributo;
           this.parametroContributo = parametroContributo;
    }


    /**
     * Gets the esitoValidazione value for this RicercaContributoTo.
     * 
     * @return esitoValidazione
     */
    public it.avcp.spc.appalti.ejbImpl.servizi.ValidationBean[] getEsitoValidazione() {
        return esitoValidazione;
    }


    /**
     * Sets the esitoValidazione value for this RicercaContributoTo.
     * 
     * @param esitoValidazione
     */
    public void setEsitoValidazione(it.avcp.spc.appalti.ejbImpl.servizi.ValidationBean[] esitoValidazione) {
        this.esitoValidazione = esitoValidazione;
    }

    public it.avcp.spc.appalti.ejbImpl.servizi.ValidationBean getEsitoValidazione(int i) {
        return this.esitoValidazione[i];
    }

    public void setEsitoValidazione(int i, it.avcp.spc.appalti.ejbImpl.servizi.ValidationBean _value) {
        this.esitoValidazione[i] = _value;
    }


    /**
     * Gets the importContributo value for this RicercaContributoTo.
     * 
     * @return importContributo
     */
    public java.math.BigDecimal getImportContributo() {
        return importContributo;
    }


    /**
     * Sets the importContributo value for this RicercaContributoTo.
     * 
     * @param importContributo
     */
    public void setImportContributo(java.math.BigDecimal importContributo) {
        this.importContributo = importContributo;
    }


    /**
     * Gets the mapImportContributo value for this RicercaContributoTo.
     * 
     * @return mapImportContributo
     */
    public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToMapImportContributoEntry[] getMapImportContributo() {
        return mapImportContributo;
    }


    /**
     * Sets the mapImportContributo value for this RicercaContributoTo.
     * 
     * @param mapImportContributo
     */
    public void setMapImportContributo(it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToMapImportContributoEntry[] mapImportContributo) {
        this.mapImportContributo = mapImportContributo;
    }


    /**
     * Gets the parametroContributo value for this RicercaContributoTo.
     * 
     * @return parametroContributo
     */
    public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToParametroContributoEntry[] getParametroContributo() {
        return parametroContributo;
    }


    /**
     * Sets the parametroContributo value for this RicercaContributoTo.
     * 
     * @param parametroContributo
     */
    public void setParametroContributo(it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoToParametroContributoEntry[] parametroContributo) {
        this.parametroContributo = parametroContributo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaContributoTo)) return false;
        RicercaContributoTo other = (RicercaContributoTo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.esitoValidazione==null && other.getEsitoValidazione()==null) || 
             (this.esitoValidazione!=null &&
              java.util.Arrays.equals(this.esitoValidazione, other.getEsitoValidazione()))) &&
            ((this.importContributo==null && other.getImportContributo()==null) || 
             (this.importContributo!=null &&
              this.importContributo.equals(other.getImportContributo()))) &&
            ((this.mapImportContributo==null && other.getMapImportContributo()==null) || 
             (this.mapImportContributo!=null &&
              java.util.Arrays.equals(this.mapImportContributo, other.getMapImportContributo()))) &&
            ((this.parametroContributo==null && other.getParametroContributo()==null) || 
             (this.parametroContributo!=null &&
              java.util.Arrays.equals(this.parametroContributo, other.getParametroContributo())));
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
        if (getEsitoValidazione() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEsitoValidazione());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEsitoValidazione(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getImportContributo() != null) {
            _hashCode += getImportContributo().hashCode();
        }
        if (getMapImportContributo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMapImportContributo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMapImportContributo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getParametroContributo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParametroContributo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParametroContributo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaContributoTo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "ricercaContributoTo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esitoValidazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esitoValidazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "validationBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importContributo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importContributo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapImportContributo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mapImportContributo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", ">>ricercaContributoTo>mapImportContributo>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametroContributo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametroContributo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", ">>ricercaContributoTo>parametroContributo>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
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
