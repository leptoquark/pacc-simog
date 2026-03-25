package ausa.servizi.avcp.it.wsdl;

public class RicercaSAWSProxy implements ausa.servizi.avcp.it.wsdl.RicercaSAWS_PortType {
  private String _endpoint = null;
  private ausa.servizi.avcp.it.wsdl.RicercaSAWS_PortType ricercaSAWS_PortType = null;
  
  public RicercaSAWSProxy() {
    _initRicercaSAWSProxy();
  }
  
  public RicercaSAWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initRicercaSAWSProxy();
  }
  
  private void _initRicercaSAWSProxy() {
    try {
      ricercaSAWS_PortType = (new ausa.servizi.avcp.it.wsdl.RicercaSAWS_ServiceLocator()).getRicercaSAPort();
      if (ricercaSAWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ricercaSAWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ricercaSAWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ricercaSAWS_PortType != null)
      ((javax.xml.rpc.Stub)ricercaSAWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ausa.servizi.avcp.it.wsdl.RicercaSAWS_PortType getRicercaSAWS_PortType() {
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType;
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO elencaTuttiCdCDaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.elencaTuttiCdCDaCodiceAusaWS(codiceAusa, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO elencoCdCdaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.elencoCdCdaCodiceAusaWS(codiceAusa, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaRupDTO elencoRupDaCdCWS(long codCdC, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.elencoRupDaCdCWS(codCdC, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO ricercaAnagraficaSADaStatoWS(java.lang.String codiceStato, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.ricercaAnagraficaSADaStatoWS(codiceStato, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO ricercaLegaleRappresentanteValidoWS(java.lang.String codiceFiscaleSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.ricercaLegaleRappresentanteValidoWS(codiceFiscaleSA, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaCFWS(java.lang.String codiceFiscaleSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.ricercaSAdaCFWS(codiceFiscaleSA, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.ricercaSAdaCodiceAusaWS(codiceAusa, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaDenominazioneWS(java.lang.String denominazioneSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.ricercaSAdaDenominazioneWS(denominazioneSA, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaStatoWS(java.lang.String codiceStato, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.ricercaSAdaStatoWS(codiceStato, sessioneUtente);
  }
  
  public ausa.servizi.avcp.it.wsdl.ListaUrlDTO ricercaUrlSAdaCodiceAusaWS(long codAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException{
    if (ricercaSAWS_PortType == null)
      _initRicercaSAWSProxy();
    return ricercaSAWS_PortType.ricercaUrlSAdaCodiceAusaWS(codAusa, sessioneUtente);
  }
  
  
}