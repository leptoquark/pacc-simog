/**
 * RicercaSAWS_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public interface RicercaSAWS_PortType extends java.rmi.Remote {
    public ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO elencaTuttiCdCDaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO elencoCdCdaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaRupDTO elencoRupDaCdCWS(long codCdC, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO ricercaAnagraficaSADaStatoWS(java.lang.String codiceStato, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO ricercaLegaleRappresentanteValidoWS(java.lang.String codiceFiscaleSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaCFWS(java.lang.String codiceFiscaleSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaDenominazioneWS(java.lang.String denominazioneSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaStatoWS(java.lang.String codiceStato, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
    public ausa.servizi.avcp.it.wsdl.ListaUrlDTO ricercaUrlSAdaCodiceAusaWS(long codAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException;
}
