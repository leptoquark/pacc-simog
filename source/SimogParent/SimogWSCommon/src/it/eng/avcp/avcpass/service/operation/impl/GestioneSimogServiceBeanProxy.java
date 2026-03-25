package it.eng.avcp.avcpass.service.operation.impl;

public class GestioneSimogServiceBeanProxy implements it.eng.avcp.avcpass.service.operation.impl.GestioneSimogServiceBean {
  private String _endpoint = null;
  private it.eng.avcp.avcpass.service.operation.impl.GestioneSimogServiceBean gestioneSimogServiceBean = null;
  
  public GestioneSimogServiceBeanProxy() {
    _initGestioneSimogServiceBeanProxy();
  }
  
  public GestioneSimogServiceBeanProxy(String endpoint) {
    _endpoint = endpoint;
    _initGestioneSimogServiceBeanProxy();
  }
  
  private void _initGestioneSimogServiceBeanProxy() {
    try {
      gestioneSimogServiceBean = (new it.eng.avcp.avcpass.service.operation.impl.GestioneSimogServiceBeanServiceLocator()).getGestioneSimogServiceBeanPort();
      if (gestioneSimogServiceBean != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)gestioneSimogServiceBean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)gestioneSimogServiceBean)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (gestioneSimogServiceBean != null)
      ((javax.xml.rpc.Stub)gestioneSimogServiceBean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.eng.avcp.avcpass.service.operation.impl.GestioneSimogServiceBean getGestioneSimogServiceBean() {
    if (gestioneSimogServiceBean == null)
      _initGestioneSimogServiceBeanProxy();
    return gestioneSimogServiceBean;
  }
  
  public it.eng.avcp.avcpass.service.operation.impl.RisultatoConsultaStatoCIG consultaStatoCIG(java.lang.String cig, java.lang.String codiceGara, java.lang.String codiceApplicazione) throws java.rmi.RemoteException{
    if (gestioneSimogServiceBean == null)
      _initGestioneSimogServiceBeanProxy();
    return gestioneSimogServiceBean.consultaStatoCIG(cig, codiceGara, codiceApplicazione);
  }
  
  public it.eng.avcp.avcpass.service.operation.impl.RisultatoConsultaStatoCIG consultaStatoCIG(java.lang.String cig, java.lang.String codiceGara, java.lang.String codiceApplicazione, java.lang.String codiceFunzione) throws java.rmi.RemoteException{
     if (gestioneSimogServiceBean == null)
       _initGestioneSimogServiceBeanProxy();
     return gestioneSimogServiceBean.consultaStatoCIG(cig, codiceGara, codiceApplicazione, codiceFunzione);
   }  
  
  
}