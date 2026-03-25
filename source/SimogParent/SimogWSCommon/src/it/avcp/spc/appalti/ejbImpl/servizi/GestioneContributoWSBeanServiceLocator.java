/**
 * GestioneContributoWSBeanServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.spc.appalti.ejbImpl.servizi;

public class GestioneContributoWSBeanServiceLocator extends org.apache.axis.client.Service implements it.avcp.spc.appalti.ejbImpl.servizi.GestioneContributoWSBeanService {

    public GestioneContributoWSBeanServiceLocator() {
    }


    public GestioneContributoWSBeanServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GestioneContributoWSBeanServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DeterminazioneContributoPort
    private java.lang.String DeterminazioneContributoPort_address = "http://PRE-SERVBDNCP01:8080/GestioneContributoService/GestioneContributoWSBean";

    public java.lang.String getDeterminazioneContributoPortAddress() {
        return DeterminazioneContributoPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DeterminazioneContributoPortWSDDServiceName = "DeterminazioneContributoPort";

    public java.lang.String getDeterminazioneContributoPortWSDDServiceName() {
        return DeterminazioneContributoPortWSDDServiceName;
    }

    public void setDeterminazioneContributoPortWSDDServiceName(java.lang.String name) {
        DeterminazioneContributoPortWSDDServiceName = name;
    }

    public it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributo getDeterminazioneContributoPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DeterminazioneContributoPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDeterminazioneContributoPort(endpoint);
    }

    public it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributo getDeterminazioneContributoPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributoBindingStub _stub = new it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributoBindingStub(portAddress, this);
            _stub.setPortName(getDeterminazioneContributoPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDeterminazioneContributoPortEndpointAddress(java.lang.String address) {
        DeterminazioneContributoPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributo.class.isAssignableFrom(serviceEndpointInterface)) {
                it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributoBindingStub _stub = new it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributoBindingStub(new java.net.URL(DeterminazioneContributoPort_address), this);
                _stub.setPortName(getDeterminazioneContributoPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DeterminazioneContributoPort".equals(inputPortName)) {
            return getDeterminazioneContributoPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "GestioneContributoWSBeanService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servizi.ejbImpl.appalti.spc.avcp.it/", "DeterminazioneContributoPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DeterminazioneContributoPort".equals(portName)) {
            setDeterminazioneContributoPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
