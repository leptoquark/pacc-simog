package it.avcp.simog.auth.manager.ws;

public class IloginProxy implements it.avcp.simog.auth.manager.ws.Ilogin {
  private String _endpoint = null;
  private it.avcp.simog.auth.manager.ws.Ilogin ilogin = null;
  
  public IloginProxy() {
    _initIloginProxy();
  }
  
  public IloginProxy(String endpoint) {
    _endpoint = endpoint;
    _initIloginProxy();
  }
  
  private void _initIloginProxy() {
    try {
      ilogin = (new it.avcp.simog.auth.manager.ws.IloginserviceLocator()).getIloginPort();
      if (ilogin != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ilogin)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ilogin)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ilogin != null)
      ((javax.xml.rpc.Stub)ilogin)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.avcp.simog.auth.manager.ws.Ilogin getIlogin() {
    if (ilogin == null)
      _initIloginProxy();
    return ilogin;
  }
  
  public java.lang.String check_login(java.lang.String login, java.lang.String password, java.lang.String applicazione) throws java.rmi.RemoteException{
    if (ilogin == null)
      _initIloginProxy();
    return ilogin.check_login(login, password, applicazione);
  }
  
  //TICKET ALM - 3.04.3
  public java.lang.String check_loginRPNT(java.lang.String login, java.lang.String password, java.lang.String cfrup, java.lang.String applicazione) throws java.rmi.RemoteException{
	    if (ilogin == null)
	      _initIloginProxy();
	    return ilogin.check_loginRPNT(login, password, cfrup, applicazione);
	  }
  
}