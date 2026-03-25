/**
 * RicercaSAWSBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ausa.servizi.avcp.it.wsdl;

public class RicercaSAWSBindingStub extends org.apache.axis.client.Stub implements ausa.servizi.avcp.it.wsdl.RicercaSAWS_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[10];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elencaTuttiCdCDaCodiceAusaWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceAusa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaCdCRupDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elencoCdCdaCodiceAusaWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceAusa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaCdCRupDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elencoRupDaCdCWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codCdC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaRupDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaRupDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaAnagraficaSADaStatoWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceStato"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaAusaRicercaDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaLegaleRappresentanteValidoWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceFiscaleSA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaRappresentanteLegaleDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaSAdaCFWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceFiscaleSA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaAusaDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaSAdaCodiceAusaWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceAusa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaAusaDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaSAdaDenominazioneWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "denominazioneSA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaAusaDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaSAdaStatoWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceStato"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaAusaDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaUrlSAdaCodiceAusaWS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codAusa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessioneUtente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO"), ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaUrlDTO"));
        oper.setReturnClass(ausa.servizi.avcp.it.wsdl.ListaUrlDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    public RicercaSAWSBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public RicercaSAWSBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public RicercaSAWSBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ausaDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.AusaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ausaRicercaSADTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.AusaRicercaSADTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "cdCRupDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.CdCRupDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "esitoDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.EsitoDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaAusaDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaAusaRicercaDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaCdCRupDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaRappresentanteLegaleDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaRupDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.ListaRupDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "listaUrlDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.ListaUrlDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "rappresentanteLegaleDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.RappresentanteLegaleDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "rupDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.RupDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "sessioneUtenteDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "urlDTO");
            cachedSerQNames.add(qName);
            cls = ausa.servizi.avcp.it.wsdl.UrlDTO.class;
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

    public ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO elencaTuttiCdCDaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "elencaTuttiCdCDaCodiceAusaWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceAusa, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO elencoCdCdaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "elencoCdCdaCodiceAusaWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceAusa, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaCdCRupDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaRupDTO elencoRupDaCdCWS(long codCdC, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "elencoRupDaCdCWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(codCdC), sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaRupDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaRupDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaRupDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO ricercaAnagraficaSADaStatoWS(java.lang.String codiceStato, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ricercaAnagraficaSADaStatoWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceStato, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaAusaRicercaDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO ricercaLegaleRappresentanteValidoWS(java.lang.String codiceFiscaleSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ricercaLegaleRappresentanteValidoWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceFiscaleSA, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaRappresentanteLegaleDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaCFWS(java.lang.String codiceFiscaleSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ricercaSAdaCFWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceFiscaleSA, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaCodiceAusaWS(java.lang.String codiceAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ricercaSAdaCodiceAusaWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceAusa, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaDenominazioneWS(java.lang.String denominazioneSA, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ricercaSAdaDenominazioneWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {denominazioneSA, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaAusaDTO ricercaSAdaStatoWS(java.lang.String codiceStato, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ricercaSAdaStatoWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceStato, sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaAusaDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaAusaDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ausa.servizi.avcp.it.wsdl.ListaUrlDTO ricercaUrlSAdaCodiceAusaWS(long codAusa, ausa.servizi.avcp.it.wsdl.SessioneUtenteDTO sessioneUtente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://it.avcp.servizi.ausa/wsdl", "ricercaUrlSAdaCodiceAusaWS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(codAusa), sessioneUtente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ausa.servizi.avcp.it.wsdl.ListaUrlDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (ausa.servizi.avcp.it.wsdl.ListaUrlDTO) org.apache.axis.utils.JavaUtils.convert(_resp, ausa.servizi.avcp.it.wsdl.ListaUrlDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
