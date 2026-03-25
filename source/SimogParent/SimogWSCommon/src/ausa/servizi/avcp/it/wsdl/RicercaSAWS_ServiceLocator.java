/**
 * RicercaSAWS_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public class RicercaSAWS_ServiceLocator extends org.apache.axis.client.Service implements ausa.servizi.avcp.it.wsdl.RicercaSAWS_Service {

    public RicercaSAWS_ServiceLocator() {
    }


    public RicercaSAWS_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RicercaSAWS_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RicercaSAPort
    private java.lang.String RicercaSAPort_address = "http://10.119.26.28:8080/ServiziAUSA-ear-ServiziAUSA-ejb/RicercaSABean";

    public java.lang.String getRicercaSAPortAddress() {
        return RicercaSAPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RicercaSAPortWSDDServiceName = "RicercaSAPort";

    public java.lang.String getRicercaSAPortWSDDServiceName() {
        return RicercaSAPortWSDDServiceName;
    }

    public void setRicercaSAPortWSDDServiceName(java.lang.String name) {
        RicercaSAPortWSDDServiceName = name;
    }

    public ausa.servizi.avcp.it.wsdl.RicercaSAWS_PortType getRicercaSAPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RicercaSAPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRicercaSAPort(endpoint);
    }

    public ausa.servizi.avcp.it.wsdl.RicercaSAWS_PortType getRicercaSAPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ausa.servizi.avcp.it.wsdl.RicercaSAWSBindingStub _stub = new ausa.servizi.avcp.it.wsdl.RicercaSAWSBindingStub(portAddress, this);
            _stub.setPortName(getRicercaSAPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRicercaSAPortEndpointAddress(java.lang.String address) {
        RicercaSAPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ausa.servizi.avcp.it.wsdl.RicercaSAWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ausa.servizi.avcp.it.wsdl.RicercaSAWSBindingStub _stub = new ausa.servizi.avcp.it.wsdl.RicercaSAWSBindingStub(new java.net.URL(RicercaSAPort_address), this);
                _stub.setPortName(getRicercaSAPortWSDDServiceName());
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
        if ("RicercaSAPort".equals(inputPortName)) {
            return getRicercaSAPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "RicercaSAWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "RicercaSAPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RicercaSAPort".equals(portName)) {
            setRicercaSAPortEndpointAddress(address);
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
