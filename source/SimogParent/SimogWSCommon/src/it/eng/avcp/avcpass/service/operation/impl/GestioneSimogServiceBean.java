/**
 * GestioneSimogServiceBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.avcp.avcpass.service.operation.impl;

public interface GestioneSimogServiceBean extends java.rmi.Remote {
    public it.eng.avcp.avcpass.service.operation.impl.RisultatoConsultaStatoCIG consultaStatoCIG(java.lang.String cig, java.lang.String codiceGara, java.lang.String codiceApplicazione) throws java.rmi.RemoteException;
    public it.eng.avcp.avcpass.service.operation.impl.RisultatoConsultaStatoCIG consultaStatoCIG(java.lang.String cig, java.lang.String codiceGara, java.lang.String codiceApplicazione, java.lang.String codiceFunzione) throws java.rmi.RemoteException;
}
