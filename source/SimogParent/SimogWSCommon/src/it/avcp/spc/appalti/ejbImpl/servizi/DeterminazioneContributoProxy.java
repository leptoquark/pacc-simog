package it.avcp.spc.appalti.ejbImpl.servizi;

public class DeterminazioneContributoProxy implements it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributo {
  private String _endpoint = null;
  private it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributo determinazioneContributo = null;
  
  public DeterminazioneContributoProxy() {
    _initDeterminazioneContributoProxy();
  }
  
  private void _initDeterminazioneContributoProxy() {
    try {
      determinazioneContributo = (new it.avcp.spc.appalti.ejbImpl.servizi.GestioneContributoWSBeanServiceLocator()).getDeterminazioneContributoPort();
      if (determinazioneContributo != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)determinazioneContributo)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)determinazioneContributo)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (determinazioneContributo != null)
      ((javax.xml.rpc.Stub)determinazioneContributo)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.avcp.spc.appalti.ejbImpl.servizi.DeterminazioneContributo getDeterminazioneContributo() {
    if (determinazioneContributo == null)
      _initDeterminazioneContributoProxy();
    return determinazioneContributo;
  }
  
  public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo determinazioneContributoOE(java.lang.String codiceFiscale, java.math.BigDecimal importo, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String accordoQuadro, java.lang.String applicazione, long idGara) throws java.rmi.RemoteException{
    if (determinazioneContributo == null)
      _initDeterminazioneContributoProxy();
    return determinazioneContributo.determinazioneContributoOE(codiceFiscale, importo, dataPubblicazione, motivoEscusione, tipoProcedura, accordoQuadro, applicazione, idGara);
  }
  
  public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo determinazioneContributoSA(java.lang.String codiceFiscale, java.math.BigDecimal importo, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String accordoQuadro, java.lang.String applicazione, long idGara) throws java.rmi.RemoteException{
    if (determinazioneContributo == null)
      _initDeterminazioneContributoProxy();
    return determinazioneContributo.determinazioneContributoSA(codiceFiscale, importo, dataPubblicazione, motivoEscusione, tipoProcedura, accordoQuadro, applicazione, idGara);
  }
  
  public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo listaDeterminazioneContributoOE(java.lang.String codiceFiscale, java.math.BigDecimal[] listaImporti, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String cigAccordoQuadro, java.lang.String applicazione) throws java.rmi.RemoteException{
    if (determinazioneContributo == null)
      _initDeterminazioneContributoProxy();
    return determinazioneContributo.listaDeterminazioneContributoOE(codiceFiscale, listaImporti, dataPubblicazione, motivoEscusione, tipoProcedura, cigAccordoQuadro, applicazione);
  }
  
  public it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo listaDeterminazioneContributoSA(java.lang.String codiceFiscale, java.math.BigDecimal[] listaImporti, java.util.Calendar dataPubblicazione, java.lang.String motivoEscusione, java.lang.String tipoProcedura, java.lang.String cigAccordoQuadro, java.lang.String applicazione) throws java.rmi.RemoteException{
    if (determinazioneContributo == null)
      _initDeterminazioneContributoProxy();
    return determinazioneContributo.listaDeterminazioneContributoSA(codiceFiscale, listaImporti, dataPubblicazione, motivoEscusione, tipoProcedura, cigAccordoQuadro, applicazione);
  }
  
  
}