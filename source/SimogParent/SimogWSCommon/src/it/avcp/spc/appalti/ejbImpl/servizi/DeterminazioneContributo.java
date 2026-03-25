/**
 * DeterminazioneContributo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.spc.appalti.ejbImpl.servizi;

public interface DeterminazioneContributo extends java.rmi.Remote {
    public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo determinazioneContributoOE(java.lang.String codiceFiscale, java.math.BigDecimal importo, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String accordoQuadro, java.lang.String applicazione, long idGara) throws java.rmi.RemoteException;
    public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo determinazioneContributoSA(java.lang.String codiceFiscale, java.math.BigDecimal importo, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String accordoQuadro, java.lang.String applicazione, long idGara) throws java.rmi.RemoteException;
    public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo listaDeterminazioneContributoOE(java.lang.String codiceFiscale, java.math.BigDecimal[] listaImporti, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String cigAccordoQuadro, java.lang.String applicazione) throws java.rmi.RemoteException;
    public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo listaDeterminazioneContributoSA(java.lang.String codiceFiscale, java.math.BigDecimal[] listaImporti, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String cigAccordoQuadro, java.lang.String applicazione) throws java.rmi.RemoteException;
}
