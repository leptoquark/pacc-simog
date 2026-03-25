/**
 * LoginSoap11BindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avcp.sicurezza.service.ex;

public class LoginSoap11BindingStub extends org.apache.axis.client.Stub implements it.avcp.sicurezza.service.ex.LoginPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[2];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("noOperation");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "checkLogin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Check_login"), it.avcp.sicurezza.dto.ex.xsd.Check_login.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "soggetto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Soggetto"), it.avcp.sicurezza.dto.ex.xsd.Soggetto.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "collaborazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Collaborazione"), it.avcp.sicurezza.dto.ex.xsd.Collaborazione.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "ufficio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Ufficio"), it.avcp.sicurezza.dto.ex.xsd.Ufficio.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "azienda"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Azienda"), it.avcp.sicurezza.dto.ex.xsd.Azienda.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "appLogin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ApplicationCheckLogin"), it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "applicazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Applicazione"), it.avcp.sicurezza.dto.ex.xsd.Applicazione.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "appsLogin"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ApplicationsCheckLogin"), it.avcp.sicurezza.dto.ex.xsd.ApplicationsCheckLogin.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("check_login");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "login"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "applicazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Check_login"));
        oper.setReturnClass(it.avcp.sicurezza.dto.ex.xsd.Check_login.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

    }

    public LoginSoap11BindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public LoginSoap11BindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public LoginSoap11BindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ApplicationCheckLogin");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "ApplicationsCheckLogin");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.ApplicationsCheckLogin.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Applicazione");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.Applicazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Azienda");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.Azienda.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Check_login");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.Check_login.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Collaborazione");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.Collaborazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Soggetto");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.Soggetto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ex.dto.sicurezza.avcp.it/xsd", "Ufficio");
            cachedSerQNames.add(qName);
            cls = it.avcp.sicurezza.dto.ex.xsd.Ufficio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public void noOperation(it.avcp.sicurezza.dto.ex.xsd.Check_login checkLogin, it.avcp.sicurezza.dto.ex.xsd.Soggetto soggetto, it.avcp.sicurezza.dto.ex.xsd.Collaborazione collaborazione, it.avcp.sicurezza.dto.ex.xsd.Ufficio ufficio, it.avcp.sicurezza.dto.ex.xsd.Azienda azienda, it.avcp.sicurezza.dto.ex.xsd.ApplicationCheckLogin appLogin, it.avcp.sicurezza.dto.ex.xsd.Applicazione applicazione, it.avcp.sicurezza.dto.ex.xsd.ApplicationsCheckLogin appsLogin) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:noOperation");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "noOperation"));

        setRequestHeaders(_call);
        setAttachments(_call);
        _call.invokeOneWay(new java.lang.Object[] {checkLogin, soggetto, collaborazione, ufficio, azienda, appLogin, applicazione, appsLogin});

    }

    public it.avcp.sicurezza.dto.ex.xsd.Check_login check_login(java.lang.String login, java.lang.String password, java.lang.String applicazione) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:check_login");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ex.service.sicurezza.avcp.it", "check_login"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {login, password, applicazione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.avcp.sicurezza.dto.ex.xsd.Check_login) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.avcp.sicurezza.dto.ex.xsd.Check_login) org.apache.axis.utils.JavaUtils.convert(_resp, it.avcp.sicurezza.dto.ex.xsd.Check_login.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
