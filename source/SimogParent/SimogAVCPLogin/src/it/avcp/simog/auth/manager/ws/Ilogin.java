/**
 * Ilogin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avcp.simog.auth.manager.ws;

public interface Ilogin extends java.rmi.Remote {
    public java.lang.String check_login(java.lang.String login, java.lang.String password, java.lang.String applicazione) throws java.rmi.RemoteException;
    public java.lang.String check_loginRPNT(java.lang.String login, java.lang.String password, java.lang.String cfrup, java.lang.String applicazione) throws java.rmi.RemoteException; //TICKET ALM - 3.04.3

}
