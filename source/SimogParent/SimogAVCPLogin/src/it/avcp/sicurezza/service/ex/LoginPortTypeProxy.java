package it.avcp.sicurezza.service.ex;

public class LoginPortTypeProxy implements it.avcp.sicurezza.service.ex.LoginPortType {
  private String _endpoint = null;
  private it.avcp.sicurezza.service.ex.LoginPortType loginPortType = null;
  
  public LoginPortTypeProxy() {
    _initLoginPortTypeProxy();
  }
  
  private void _initLoginPortTypeProxy() {
    try {
      loginPortType = (new it.avcp.sicurezza.service.ex.LoginLocator()).getloginHttpSoap11Endpoint();
      if (loginPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loginPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loginPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loginPortType != null)
      ((javax.xml.rpc.Stub)loginPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.avcp.sicurezza.service.ex.LoginPortType getLoginPortType() {
    if (loginPortType == null)
      _initLoginPortTypeProxy();
    return loginPortType;
  }
  
  public void noOperation(it.avcp.sicurezza.dto.ex.xsd.Check_login checkLogin, it.avcp.sicurezza.dto.ex.xsd.Soggetto soggetto, it.avcp.sicurezza.dto.ex.xsd.Collaborazione collaborazione, it.avcp.sicurezza.dto.ex.xsd.Ufficio ufficio, it.avcp.sicurezza.dto.ex.xsd.Azienda azienda, it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin appLogin, it.avcp.sicurezza.dto.ex.xsd.Applicazione applicazione, it.avcp.sicurezza.dto.ex.xsd.ApplicationsCheckLogin appsLogin) throws java.rmi.RemoteException{
    if (loginPortType == null)
      _initLoginPortTypeProxy();
    loginPortType.noOperation(checkLogin, soggetto, collaborazione, ufficio, azienda, appLogin, applicazione, appsLogin);
  }
  
  public it.avcp.sicurezza.dto.ex.xsd.Check_login check_login(java.lang.String login, java.lang.String password, java.lang.String applicazione) throws java.rmi.RemoteException{
    if (loginPortType == null)
      _initLoginPortTypeProxy();
    return loginPortType.check_login(login, password, applicazione);
  }
  
  
}