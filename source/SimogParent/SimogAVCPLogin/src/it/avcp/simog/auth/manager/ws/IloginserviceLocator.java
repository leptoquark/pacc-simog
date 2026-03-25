/**
 * IloginserviceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avcp.simog.auth.manager.ws;

public class IloginserviceLocator extends org.apache.axis.client.Service implements it.avcp.simog.auth.manager.ws.Iloginservice {

    public IloginserviceLocator() {
    }


    public IloginserviceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IloginserviceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IloginPort
    private java.lang.String IloginPort_address = "https://anagrafe.avcp.it:447/login.cgi/soap/Ilogin";

    public java.lang.String getIloginPortAddress() {
        return IloginPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IloginPortWSDDServiceName = "IloginPort";

    public java.lang.String getIloginPortWSDDServiceName() {
        return IloginPortWSDDServiceName;
    }

    public void setIloginPortWSDDServiceName(java.lang.String name) {
        IloginPortWSDDServiceName = name;
    }

    public it.avcp.simog.auth.manager.ws.Ilogin getIloginPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IloginPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIloginPort(endpoint);
    }

    public it.avcp.simog.auth.manager.ws.Ilogin getIloginPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	it.avcp.simog.auth.manager.ws.IloginbindingStub _stub = new it.avcp.simog.auth.manager.ws.IloginbindingStub(portAddress, this);
            _stub.setPortName(getIloginPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIloginPortEndpointAddress(java.lang.String address) {
        IloginPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.avcp.simog.auth.manager.ws.Ilogin.class.isAssignableFrom(serviceEndpointInterface)) {
            	it.avcp.simog.auth.manager.ws.IloginbindingStub _stub = new it.avcp.simog.auth.manager.ws.IloginbindingStub(new java.net.URL(IloginPort_address), this);
                _stub.setPortName(getIloginPortWSDDServiceName());
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
        if ("IloginPort".equals(inputPortName)) {
            return getIloginPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.avlp.it/webservices", "Iloginservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.avlp.it/webservices", "IloginPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IloginPort".equals(portName)) {
            setIloginPortEndpointAddress(address);
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
