/**
 * LoginLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.service.ex;

public class LoginLocator extends org.apache.axis.client.Service implements it.avcp.sicurezza.service.ex.Login {

    public LoginLocator() {
    }


    public LoginLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoginLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for loginHttpSoap11Endpoint
    private java.lang.String loginHttpSoap11Endpoint_address = "http://10.119.128.241:9090/sicurezza-anagrafeWS/services/login.loginHttpSoap11Endpoint/";

    public java.lang.String getloginHttpSoap11EndpointAddress() {
        return loginHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String loginHttpSoap11EndpointWSDDServiceName = "loginHttpSoap11Endpoint";

    public java.lang.String getloginHttpSoap11EndpointWSDDServiceName() {
        return loginHttpSoap11EndpointWSDDServiceName;
    }

    public void setloginHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        loginHttpSoap11EndpointWSDDServiceName = name;
    }

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(loginHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getloginHttpSoap11Endpoint(endpoint);
    }

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.avcp.sicurezza.service.ex.LoginSoap11BindingStub _stub = new it.avcp.sicurezza.service.ex.LoginSoap11BindingStub(portAddress, this);
            _stub.setPortName(getloginHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setloginHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        loginHttpSoap11Endpoint_address = address;
    }


    // Use to get a proxy class for loginHttpSoap12Endpoint
    private java.lang.String loginHttpSoap12Endpoint_address = "http://10.119.128.241:9090/sicurezza-anagrafeWS/services/login.loginHttpSoap12Endpoint/";

    public java.lang.String getloginHttpSoap12EndpointAddress() {
        return loginHttpSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String loginHttpSoap12EndpointWSDDServiceName = "loginHttpSoap12Endpoint";

    public java.lang.String getloginHttpSoap12EndpointWSDDServiceName() {
        return loginHttpSoap12EndpointWSDDServiceName;
    }

    public void setloginHttpSoap12EndpointWSDDServiceName(java.lang.String name) {
        loginHttpSoap12EndpointWSDDServiceName = name;
    }

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(loginHttpSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getloginHttpSoap12Endpoint(endpoint);
    }

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.avcp.sicurezza.service.ex.LoginSoap12BindingStub _stub = new it.avcp.sicurezza.service.ex.LoginSoap12BindingStub(portAddress, this);
            _stub.setPortName(getloginHttpSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setloginHttpSoap12EndpointEndpointAddress(java.lang.String address) {
        loginHttpSoap12Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.avcp.sicurezza.service.ex.LoginPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.avcp.sicurezza.service.ex.LoginSoap11BindingStub _stub = new it.avcp.sicurezza.service.ex.LoginSoap11BindingStub(new java.net.URL(loginHttpSoap11Endpoint_address), this);
                _stub.setPortName(getloginHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
            if (it.avcp.sicurezza.service.ex.LoginPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.avcp.sicurezza.service.ex.LoginSoap12BindingStub _stub = new it.avcp.sicurezza.service.ex.LoginSoap12BindingStub(new java.net.URL(loginHttpSoap12Endpoint_address), this);
                _stub.setPortName(getloginHttpSoap12EndpointWSDDServiceName());
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
        if ("loginHttpSoap11Endpoint".equals(inputPortName)) {
            return getloginHttpSoap11Endpoint();
        }
        else if ("loginHttpSoap12Endpoint".equals(inputPortName)) {
            return getloginHttpSoap12Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "login");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "loginHttpSoap11Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "loginHttpSoap12Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("loginHttpSoap11Endpoint".equals(portName)) {
            setloginHttpSoap11EndpointEndpointAddress(address);
        }
        else 
if ("loginHttpSoap12Endpoint".equals(portName)) {
            setloginHttpSoap12EndpointEndpointAddress(address);
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
