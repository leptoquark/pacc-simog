/**
 * AVCPWSLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.anagrafe.AnagrafeWS;

public class AVCPWSLocator extends org.apache.axis.client.Service implements it.avcp.anagrafe.AnagrafeWS.AVCPWS {

    public AVCPWSLocator() {
    }


    public AVCPWSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AVCPWSLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AnagrafeWS
    private java.lang.String AnagrafeWS_address = "https://anagrafe.avcp.it/AnagrafeWS/index.php";

    public java.lang.String getAnagrafeWSAddress() {
        return AnagrafeWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AnagrafeWSWSDDServiceName = "AnagrafeWS";

    public java.lang.String getAnagrafeWSWSDDServiceName() {
        return AnagrafeWSWSDDServiceName;
    }

    public void setAnagrafeWSWSDDServiceName(java.lang.String name) {
        AnagrafeWSWSDDServiceName = name;
    }

    public it.avcp.anagrafe.AnagrafeWS.AnagrafeWS getAnagrafeWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AnagrafeWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAnagrafeWS(endpoint);
    }

    public it.avcp.anagrafe.AnagrafeWS.AnagrafeWS getAnagrafeWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.avcp.anagrafe.AnagrafeWS.AnagrafeWSStub _stub = new it.avcp.anagrafe.AnagrafeWS.AnagrafeWSStub(portAddress, this);
            _stub.setPortName(getAnagrafeWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAnagrafeWSEndpointAddress(java.lang.String address) {
        AnagrafeWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.avcp.anagrafe.AnagrafeWS.AnagrafeWS.class.isAssignableFrom(serviceEndpointInterface)) {
                it.avcp.anagrafe.AnagrafeWS.AnagrafeWSStub _stub = new it.avcp.anagrafe.AnagrafeWS.AnagrafeWSStub(new java.net.URL(AnagrafeWS_address), this);
                _stub.setPortName(getAnagrafeWSWSDDServiceName());
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
        if ("AnagrafeWS".equals(inputPortName)) {
            return getAnagrafeWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://anagrafe.avcp.it/AnagrafeWS/", "AVCPWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://anagrafe.avcp.it/AnagrafeWS/", "AnagrafeWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AnagrafeWS".equals(portName)) {
            setAnagrafeWSEndpointAddress(address);
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
