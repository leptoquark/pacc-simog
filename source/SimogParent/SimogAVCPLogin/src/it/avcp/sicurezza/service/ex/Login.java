/**
 * Login.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.service.ex;

public interface Login extends javax.xml.rpc.Service {
    public java.lang.String getloginHttpSoap11EndpointAddress();

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException;

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getloginHttpSoap12EndpointAddress();

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException;

    public it.avcp.sicurezza.service.ex.LoginPortType getloginHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
