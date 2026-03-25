/**
 * LoginPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.service.ex;

public interface LoginPortType extends java.rmi.Remote {
    public void noOperation(it.avcp.sicurezza.dto.ex.xsd.Check_login checkLogin, it.avcp.sicurezza.dto.ex.xsd.Soggetto soggetto, it.avcp.sicurezza.dto.ex.xsd.Collaborazione collaborazione, it.avcp.sicurezza.dto.ex.xsd.Ufficio ufficio, it.avcp.sicurezza.dto.ex.xsd.Azienda azienda, it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin appLogin, it.avcp.sicurezza.dto.ex.xsd.Applicazione applicazione, it.avcp.sicurezza.dto.ex.xsd.ApplicationsCheckLogin appsLogin) throws java.rmi.RemoteException;
    public it.avcp.sicurezza.dto.ex.xsd.Check_login check_login(java.lang.String login, java.lang.String password, java.lang.String applicazione) throws java.rmi.RemoteException;
}
