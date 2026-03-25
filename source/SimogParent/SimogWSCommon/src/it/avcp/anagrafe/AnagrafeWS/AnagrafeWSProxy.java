package it.avcp.anagrafe.AnagrafeWS;

public class AnagrafeWSProxy implements it.avcp.anagrafe.AnagrafeWS.AnagrafeWS {
  private String _endpoint = null;
  private it.avcp.anagrafe.AnagrafeWS.AnagrafeWS anagrafeWS = null;
  
  public AnagrafeWSProxy() {
    _initAnagrafeWSProxy();
  }
  
  private void _initAnagrafeWSProxy() {
    try {
      anagrafeWS = (new it.avcp.anagrafe.AnagrafeWS.AVCPWSLocator()).getAnagrafeWS();
      if (anagrafeWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)anagrafeWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)anagrafeWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (anagrafeWS != null)
      ((javax.xml.rpc.Stub)anagrafeWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.avcp.anagrafe.AnagrafeWS.AnagrafeWS getAnagrafeWS() {
    if (anagrafeWS == null)
      _initAnagrafeWSProxy();
    return anagrafeWS;
  }
  
  public java.lang.String listaCentriCosto(java.lang.String parameters) throws java.rmi.RemoteException{
    if (anagrafeWS == null)
      _initAnagrafeWSProxy();
    return anagrafeWS.listaCentriCosto(parameters);
  }
  
  public java.lang.String nuovoCentroCosto(java.lang.String parameters) throws java.rmi.RemoteException{
    if (anagrafeWS == null)
      _initAnagrafeWSProxy();
    return anagrafeWS.nuovoCentroCosto(parameters);
  }
  
  public java.lang.String nuovaStazioneAppaltante(java.lang.String parameters) throws java.rmi.RemoteException{
    if (anagrafeWS == null)
      _initAnagrafeWSProxy();
    return anagrafeWS.nuovaStazioneAppaltante(parameters);
  }
  
  
}