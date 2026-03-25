package it.avlp.simog.ws.endpoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import it.anticorruzione.simog.ws.util.IniziativaConverter;
import it.avlp.simog.beans.AllegatoType;
import it.avlp.simog.beans.CUPLOTTO;
import it.avlp.simog.beans.CodiciCup;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.MisuraPremialeBean;
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.TIPIAPPALTO;
import it.avlp.simog.common.actions.CancellaGaraActionManager;
import it.avlp.simog.common.actions.CancellaLottoActionManager;
import it.avlp.simog.common.actions.ChiudiSessionActionManager;
import it.avlp.simog.common.actions.ConsultaGaraActionManager;
import it.avlp.simog.common.actions.ConsultaIniziativaActionManager;
import it.avlp.simog.common.actions.InserisciGaraActionManager;
import it.avlp.simog.common.actions.InserisciLottoActionManager;
import it.avlp.simog.common.actions.IntegraCUPActionManager;
import it.avlp.simog.common.actions.IntegraPariOpportunitaActionManager;
import it.avlp.simog.common.actions.InviaRequisitiActionManager;
import it.avlp.simog.common.actions.LoginActionManager;
import it.avlp.simog.common.actions.ModificaDatiPerfezionamentoActionManager;
import it.avlp.simog.common.actions.ModificaGaraActionManager;
import it.avlp.simog.common.actions.ModificaLottoActionManager;
import it.avlp.simog.common.actions.MoificaCPVActionManager;
import it.avlp.simog.common.actions.PerfezionaGaraActionManager;
import it.avlp.simog.common.actions.PerfezionaLottoActionManager;
import it.avlp.simog.common.actions.PresaInCaricoActionManager;
import it.avlp.simog.common.actions.PresaInCaricoGaraDelegataActionManager;
import it.avlp.simog.common.actions.PubblicazioneBandoActionManager;
import it.avlp.simog.common.util.General;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.beans.ResponseCancellaGara;
import it.avlp.simog.ws.beans.ResponseCancellaLotto;
import it.avlp.simog.ws.beans.ResponseCheckLogin;
import it.avlp.simog.ws.beans.ResponseChiudiSession;
import it.avlp.simog.ws.beans.ResponseConsultaGara;
import it.avlp.simog.ws.beans.ResponseConsultaIniziativa;
import it.avlp.simog.ws.beans.ResponseConsultaNumeroGara;
import it.avlp.simog.ws.beans.ResponseInserisciGara;
import it.avlp.simog.ws.beans.ResponseInserisciLotto;
import it.avlp.simog.ws.beans.ResponseIntegraCUP;
import it.avlp.simog.ws.beans.ResponseIntegraPariOpportunita;
import it.avlp.simog.ws.beans.ResponseInviaRequisiti;
import it.avlp.simog.ws.beans.ResponseModificaCPV;
import it.avlp.simog.ws.beans.ResponseModificaDatiPerfezionamento;
import it.avlp.simog.ws.beans.ResponseModificaGara;
import it.avlp.simog.ws.beans.ResponseModificaLotto;
import it.avlp.simog.ws.beans.ResponsePerfezionaGara;
import it.avlp.simog.ws.beans.ResponsePerfezionaLotto;
import it.avlp.simog.ws.beans.ResponsePresaInCarico;
import it.avlp.simog.ws.beans.ResponsePubblicazioneBando;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType;
import it.avlp.simog.ws.massload.xmlbeans.CategLottoType;
import it.avlp.simog.ws.massload.xmlbeans.CigType;
import it.avlp.simog.ws.massload.xmlbeans.CupType;
import it.avlp.simog.ws.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.ws.massload.xmlbeans.FlagSNType;
import it.avlp.simog.ws.massload.xmlbeans.GaraWS;
import it.avlp.simog.ws.massload.xmlbeans.IniziativaWS;
import it.avlp.simog.ws.massload.xmlbeans.LottoWS;
import it.avlp.simog.ws.massload.xmlbeans.MotivoDerogaType;
import it.avlp.simog.ws.massload.xmlbeans.PubblicazioneWS;
import it.avlp.simog.ws.massload.xmlbeans.RequisitiWS;
import it.avlp.simog.ws.massload.xmlbeans.SchedaGaraCig;
import it.avlp.simog.ws.massload.xmlbeans.SchedaType;
import it.avlp.simog.ws.massload.xmlbeans.TerritorioType;
import it.avlp.simog.ws.massload.xmlbeans.TerritorioWS;

@WebService(targetNamespace = "xmlbeans.massload.simog.avlp.it")
public class SimogWSPDD {

//	private GaraObjectFactory gof;
//	private LottoObjectFactory lof;
//	private PubblicaObjectFactory pof;
//	private RequisitiObjectFactory rof;
	//private SchedaObjectFactory of;
	public OutputStream os;

	/**
	 * metodo che rappresenta la funzione di "LOGIN" esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso dell'operazione.
	 * 
	 * @param login
	 * @param password
	 * 
	 * @return ResponseCheckLogin
	 */
	@WebMethod
	public ResponseCheckLogin login(@WebParam(name = "login") String login, @WebParam(name = "password") String password) {

		ResponseCheckLogin ris = null;

		
      // versione
      if("".equals(login) && "".equals(password)){
         // ritorno la versione
         ris = new ResponseCheckLogin();
         ris.setSuccess(false);
         try {
           ris.setError(ConfigurationManager.getInstance().buildVersion(this.getClass().getSimpleName(), "/simogWSPDDversion.properties"));
        } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
        }
         return ris;
      }
      
      
		if ((login != null && !login.trim().equals("")) && (password != null && !password.trim().equals(""))) {

			it.avlp.simog.common.beans.ResponseCheckLogin responseCheckLogin = LoginActionManager.execute(login.trim(), password.trim());

			ris = new ResponseCheckLogin();
			ris.setColl(responseCheckLogin.getColl());
			ris.setError(responseCheckLogin.getError());
			ris.setSuccess(responseCheckLogin.isSuccess());
			ris.setTicket(responseCheckLogin.getTicket());
		} else {
			ris = new ResponseCheckLogin();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}
	
	
	/**
	 * metodo che rappresenta la funzione di "LOGIN" esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso dell'operazione.
	 * 
	 * @param login
	 * @param password
	 * @param cfrup
	 * 
	 * @return ResponseCheckLogin
	 */
	
	// TICKET ALM #4225
	@WebMethod
	public ResponseCheckLogin loginRPNT(@WebParam(name = "login") String login, @WebParam(name = "password") String password, @WebParam(name = "cfrup") String cfrup) {

		ResponseCheckLogin ris = null;

		if ((login != null && !login.trim().equals("")) && 
			(password != null && !password.trim().equals("")) &&
			cfrup!=null && !cfrup.trim().equals("")) {

			
				it.avlp.simog.common.beans.ResponseCheckLogin responseCheckLogin = LoginActionManager.execute(login.trim(), password.trim(),cfrup.trim());
	
				ris = new ResponseCheckLogin();
				ris.setColl(responseCheckLogin.getColl());
				ris.setError(responseCheckLogin.getError());
				ris.setSuccess(responseCheckLogin.isSuccess());
				ris.setTicket(responseCheckLogin.getTicket());
			
		} else {
			ris = new ResponseCheckLogin();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	} 
	//FINE TICKET ALM #4225
	

	/**
	 * Metodo che rappresenta la funzione INSERISCIGARA, esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso della
	 * operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param datiGara
	 * 
	 * @return ResponseInserisciGara
	 */
	@WebMethod
	public ResponseInserisciGara inserisciGara(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, @WebParam(name = "datiGara") GaraWS datiGara) {

		ResponseInserisciGara ris = null;
		String datiGaraXml = null;

		if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false) && datiGara != null) {

			//gof = new GaraObjectFactory();

			try {
//				JAXBElement<GaraWS> gt = gof.createGara(datiGara);
				JAXBContext jaxbContext = JAXBContext.newInstance(GaraWS.class);
				Marshaller m = jaxbContext.createMarshaller();
				os = new ByteArrayOutputStream();
				m.marshal(datiGara, os);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			int index = os.toString().indexOf("?", 2) + 2;
			datiGaraXml = os.toString().substring(index);

			it.avlp.simog.common.beans.ResponseInserisciGara responseInserisciGara = InserisciGaraActionManager.execute(ticket.trim(), indexCollaborazione.trim(), datiGaraXml);

			ris = new ResponseInserisciGara();
			ris.setError(responseInserisciGara.getError());
			ris.setId_gara(responseInserisciGara.getId_gara());
			ris.setSuccess(responseInserisciGara.isSuccess());

		} else {
			ris = new ResponseInserisciGara();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione di "CONSULTAGARA" esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso
	 * dell'operazione.
	 * 
	 * @param ticket
	 * @param schede
	 * @param CIG
	 * 
	 * @return ResponseConsultaGara
	 */
	@WebMethod
	public ResponseConsultaGara consultaGara(@WebParam(name = "ticket") String ticket, @WebParam(name = "schede") String schede, @WebParam(name = "CIG") String CIG) {

		ResponseConsultaGara ris = new ResponseConsultaGara();
		SchedaType doc = null;

		if ((ticket != null && !ticket.trim().equals("")) && (CIG != null && !CIG.trim().equals("") && (CIG.length() == 10) && (schede != null && !schede.trim().equals("")))) {

			it.avlp.simog.common.beans.ResponseConsultaGara responseConsultaGara = ConsultaGaraActionManager.execute(ticket.trim(), CIG.trim(), schede.trim(),true);
			String resp = "";
			
			if (responseConsultaGara.isSuccess()){
			   resp = responseConsultaGara.getGaraXML();
			   
			 //FIX LETTURA XML 
             boolean isDataVerbAggNull = resp.indexOf("xb:DATA_VERB_AGGIUDICAZIONE=\"\"") !=-1? true: false; //true

             if (isDataVerbAggNull) {
            	 resp = resp.replace("xb:DATA_VERB_AGGIUDICAZIONE=\"\"", "");
             }
             //

//      		   of = new SchedaObjectFactory();
//      		   SchedaType schedaType = of.createSchedaType();

			      SchedaType schedaType = new SchedaType();
      		   try {
      				String packageName = schedaType.getClass().getPackage().getName();
     				
      				JAXBContext jc = JAXBContext.newInstance(packageName);
      				Unmarshaller u = jc.createUnmarshaller();
      				//MEV 39049 3.04.9 
      				//aggiunta codifica UTF-8 all'interno di getBytes()
      				//aggiunta del System.out
      				ByteArrayInputStream is = new ByteArrayInputStream(resp.getBytes("UTF-8"));
      				System.out.println(resp);
      				//fine MEV 39049
      
      				doc = (SchedaType) u.unmarshal(is);
      				
      				if (doc != null) {
      					ris.setGaraXML(doc);
      					ris.setError(responseConsultaGara.getError());
      					ris.setSuccess(responseConsultaGara.isSuccess());
      				}
      		   } catch (Exception e) {
      				e.printStackTrace();
                  ris.setError((new ErrorManager(ErrorManager.SIMOGWS_XMLMANAGER_STRING_05).getError())  + ": " + resp);
                  ris.setSuccess(false);
      		   }
			}
			else{
               ris.setError(responseConsultaGara.getError());
               ris.setSuccess(responseConsultaGara.isSuccess());			   
			}
		} else{
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione di "CONSULTANUMEROGARA" esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso
	 * dell'operazione.
	 * 
	 * @param ticket
	 * @param schede
	 * @param id_gara
	 * 
	 * @return ResponseConsultaNumeroGara
	 */
	@WebMethod
	public ResponseConsultaNumeroGara consultaNumeroGara(@WebParam(name = "ticket") String ticket, @WebParam(name = "schede") String schede, @WebParam(name = "id_gara") String id_gara) {

		ResponseConsultaNumeroGara ris = new ResponseConsultaNumeroGara();
		SchedaGaraCig doc = null;

		if ((ticket != null && !ticket.trim().equals("")) && (id_gara != null && !id_gara.trim().equals("") && (General.isNumber(id_gara, true)) && (schede != null && !schede.trim().equals("")))) {

			it.avlp.simog.common.beans.ResponseConsultaGara responseConsultaGara = ConsultaGaraActionManager.execute(ticket.trim(), id_gara.trim(), schede.trim(), false);
			String resp = ""; 
			      
	         if (responseConsultaGara.isSuccess()){
	            resp = responseConsultaGara.getGaraXML();

      			//of = new SchedaObjectFactory();
      			SchedaGaraCig schedaGaraCig = new SchedaGaraCig();

      			try {
      				String packageName = schedaGaraCig.getClass().getPackage().getName();
      				JAXBContext jc = JAXBContext.newInstance(packageName);
      				Unmarshaller u = jc.createUnmarshaller();
      				ByteArrayInputStream is = new ByteArrayInputStream(resp.getBytes());
      
      				doc = (SchedaGaraCig) u.unmarshal(is);
      
      				if (doc != null) {
      					ris = new ResponseConsultaNumeroGara();
      					ris.setSchedaGaraCig(doc);
      					ris.setError(responseConsultaGara.getError());
      					ris.setSuccess(responseConsultaGara.isSuccess());
      				}
      			} catch (Exception e) {
      				e.printStackTrace();
                  ris.setError((new ErrorManager(ErrorManager.SIMOGWS_XMLMANAGER_STRING_05).getError())  + ": " + resp);
                  ris.setSuccess(false);
      			}
	         }
	         else{
                ris.setError(responseConsultaGara.getError());
                ris.setSuccess(responseConsultaGara.isSuccess());	            
	         }
	      } else {
			ris = new ResponseConsultaNumeroGara();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione di "CHIUDISESSIONE" esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso
	 * dell'operazione.
	 * 
	 * @param ticket
	 * 
	 * @return ResponseChiudiSession
	 */
	@WebMethod
	public ResponseChiudiSession chiudiSessione(@WebParam(name = "ticket") String ticket) {

		ResponseChiudiSession ris = null;
		if (ticket != null && !ticket.trim().equals("")) {
			it.avlp.simog.common.beans.ResponseChiudiSession responseChiudiSessione = ChiudiSessionActionManager.execute(ticket.trim());

			ris = new ResponseChiudiSession();
			ris.setMessaggio(responseChiudiSessione.getMessaggio());
			ris.setError(responseChiudiSessione.getError());
			ris.setSuccess(responseChiudiSessione.isSuccess());
		} else {
			ris = new ResponseChiudiSession();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione "MODIFICALOTTO" esegue il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa dell'operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param cig
	 * @param datiLotto
	 * 
	 * @return ResponseModificaLotto
	 */
	@WebMethod
	public ResponseModificaLotto modificaLotto(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "cig") String cig, @WebParam(name = "datiLotto") LottoWS datiLotto) {

		ResponseModificaLotto ris = null;
		String datiLottoXml = null;

		if ((ticket != null && !ticket.trim().equals("")) && datiLotto != null && (cig != null && !cig.equals("") && !(cig.length() != 10)) && General.isNumber(indexCollaborazione, false)) {

			//lof = new LottoObjectFactory();

			try {
				//JAXBElement<LottoWS> gt = lof.createLotto(datiLotto);
				JAXBContext jaxbContext = JAXBContext.newInstance(LottoWS.class);
				Marshaller m = jaxbContext.createMarshaller();
				os = new ByteArrayOutputStream();
				m.marshal(datiLotto, os);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			int index = os.toString().indexOf("?", 2) + 2;
			datiLottoXml = os.toString().substring(index);

			it.avlp.simog.common.beans.ResponseModificaLotto responseModificaLotto = ModificaLottoActionManager.execute(ticket.trim(), indexCollaborazione, datiLottoXml, cig.trim());
			ris = new ResponseModificaLotto();
			ris.setMessaggio(responseModificaLotto.getMessaggio());
			ris.setError(responseModificaLotto.getError());
			ris.setSuccess(responseModificaLotto.isSuccess());
			
         if(SimogProperties.getInstance().isCUPAttivo()
               && responseModificaLotto.getCUPLOTTO()!=null
               && responseModificaLotto.getCUPLOTTO().getCODICICUP()!=null){
            CUPLOTTOType elem = new CUPLOTTOType();
            CigType cigt = new CigType();
            cigt.setValue("");
            elem.setCIG(cigt);
            
            for(CodiciCup item : responseModificaLotto.getCUPLOTTO().getCODICICUP()){
               DatiCUPType dati = new DatiCUPType();
               CupType tcup = new CupType();
               tcup.setValue(item.getCUP());
               dati.setCUP(tcup);
               dati.setDATIDIPE(item.getDATI_DIPE());
               //dati.setIDRICHIESTA(new Long);
               dati.setOKUTENTE(FlagSNType.fromValue(item.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : item.getOK_UTENTE()));
               dati.setVALIDO(FlagSNType.fromValue(item.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item.getVALIDO()));
               elem.getCODICICUP().add(dati );
            }
            ris.setCUPLOTTO(elem);           
         }

		} else {
			ris = new ResponseModificaLotto();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione "PERFEZIONALOTTO" esegue il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa dell'operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param cig
	 * @param dataPubblicazione
	 * @param dataScadenzaPag
	 * @param oraScadenza
	 * 
	 * @return ResponsePerfezionaLotto
	 */
	@WebMethod
	public ResponsePerfezionaLotto perfezionaLotto(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "cig") String cig, @WebParam(name = "dataPubblicazione") String dataPubblicazione, @WebParam(name = "dataScadenzaPag") String dataScadenzaPag, 
			@WebParam(name = "oraScadenza") String oraScadenza) {

		ResponsePerfezionaLotto ris = null;

		if ((ticket != null && !ticket.trim().equals("")) && (cig != null && !cig.equals("") && !(cig.length() != 10)) && (dataPubblicazione != null && !dataPubblicazione.equals("") && dataPubblicazione.length() == 8) && (dataScadenzaPag != null && !dataScadenzaPag.equals("") && dataScadenzaPag.length() == 8) && General.isNumber(indexCollaborazione, false)) {

			it.avlp.simog.common.beans.ResponsePerfezionaLotto responsePerfezionaLotto = PerfezionaLottoActionManager.execute(ticket.trim(), indexCollaborazione, dataPubblicazione, dataScadenzaPag, cig.trim(), oraScadenza);

			ris = new ResponsePerfezionaLotto();
			ris.setMessaggio(responsePerfezionaLotto.getMessaggio());
			ris.setError(responsePerfezionaLotto.getError());
			ris.setSuccess(responsePerfezionaLotto.isSuccess());
		} else {
			ris = new ResponsePerfezionaLotto();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione "INSERISCILOTTO" esegue il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa dell'operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param id_gara
	 * @param datiLotto
	 * 
	 * @return ResponseInserisciLotto
	 */
	@WebMethod
	public ResponseInserisciLotto inserisciLotto(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "id_gara") String id_gara, @WebParam(name = "datiLotto") LottoWS datiLotto) {

		ResponseInserisciLotto ris = null;
		String datiLottoXml = null;

		if ((ticket != null && !ticket.trim().equals("")) && datiLotto != null && General.isNumber(indexCollaborazione, false)) {

			//lof = new LottoObjectFactory();

			try {
				//JAXBElement<LottoWS> gt = lof.createLotto(datiLotto);
				JAXBContext jaxbContext = JAXBContext.newInstance(LottoWS.class);
				Marshaller m = jaxbContext.createMarshaller();
				os = new ByteArrayOutputStream();
				m.marshal(datiLotto, os);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			int index = os.toString().indexOf("?", 2) + 2;
			datiLottoXml = os.toString().substring(index);

			it.avlp.simog.common.beans.ResponseInserisciLotto responseInserisciLotto = InserisciLottoActionManager.execute(ticket.trim(), indexCollaborazione, datiLottoXml, id_gara.trim());

			ris = new ResponseInserisciLotto();
			ris.setCig(responseInserisciLotto.getCig());
			ris.setError(responseInserisciLotto.getError());
			ris.setSuccess(responseInserisciLotto.isSuccess());
			
			if(SimogFlags.is3031_RFWEBGL02Active() 
               && SimogProperties.getInstance().isCUPAttivo()
               && responseInserisciLotto.getCUPLOTTO()!=null
			      && responseInserisciLotto.getCUPLOTTO().getCODICICUP()!=null){
	         CUPLOTTOType elem = new CUPLOTTOType();
	         CigType cig = new CigType();
	         cig.setValue("");
	         elem.setCIG(cig);
	         
	         for(CodiciCup item : responseInserisciLotto.getCUPLOTTO().getCODICICUP()){
	            DatiCUPType dati = new DatiCUPType();
	            CupType tcup = new CupType();
	            tcup.setValue(item.getCUP());
	            dati.setCUP(tcup);
	            dati.setDATIDIPE(item.getDATI_DIPE());
	            //dati.setIDRICHIESTA(new Long);
	            dati.setOKUTENTE(FlagSNType.fromValue(item.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : item.getOK_UTENTE()));
               dati.setVALIDO(FlagSNType.fromValue(item.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item.getVALIDO()));
               elem.getCODICICUP().add(dati );
	         }
	         ris.setCUPLOTTO(elem);			   
			}

		} else {
			ris = new ResponseInserisciLotto();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * Metodo che rappresenta la funzione MODIFICAGARA, esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso della
	 * operazione
	 * 
	 * @param ticket 
	 * @param indexCollaborazione
	 * @param id_gara
	 * @param datiGara
	 * 
	 * @return ResponseModificaGara
	 */
	@WebMethod
	public ResponseModificaGara modificaGara(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "id_gara") String id_gara, @WebParam(name = "datiGara") GaraWS datiGara) {

		ResponseModificaGara ris = null;
		String datiGaraxml = null;

		if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false) && General.isNumber(id_gara, true) && datiGara != null) {

			//gof = new GaraObjectFactory();

			try {
				//JAXBElement<GaraWS> gt = gof.createGara(datiGara);
				JAXBContext jaxbContext = JAXBContext.newInstance(GaraWS.class);
				Marshaller m = jaxbContext.createMarshaller();
				os = new ByteArrayOutputStream();
				m.marshal(datiGara, os);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			int index = os.toString().indexOf("?", 2) + 2;
			datiGaraxml = os.toString().substring(index);

			it.avlp.simog.common.beans.ResponseModificaGara responseModificaGara = ModificaGaraActionManager.execute(ticket.trim(), indexCollaborazione.trim(), id_gara, datiGaraxml);

			ris = new ResponseModificaGara();
			ris.setError(responseModificaGara.getError());
			ris.setMessaggio(responseModificaGara.getMessaggio());
			ris.setSuccess(responseModificaGara.isSuccess());

		} else {
			ris = new ResponseModificaGara();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

   /**
    * Metodo che rappresenta la funzione INTEGRADL133, esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso della
    * operazione
    * 
    * @param ticket 
    * @param indexCollaborazione
    * @param id_gara
    * @param flagDL133
    * 
    * @return ResponseModificaGara
    */
   @WebMethod
   public ResponseModificaGara integraDL133(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "id_gara") String id_gara, @WebParam(name = "flagDL133") String flagDL133) {

      ResponseModificaGara ris = null;

      if ((ticket != null && !ticket.trim().equals("")) 
            && General.isNumber(indexCollaborazione, false) 
            && General.isNumber(id_gara, true) 
            && flagDL133 != null
            && (Costanti.FLAG_VALORE_SI.equals(flagDL133)
                  || Costanti.FLAG_VALORE_NO.equals(flagDL133))
         ) {

         it.avlp.simog.common.beans.ResponseModificaGara responseModificaGara = ModificaGaraActionManager.executeDL133(ticket.trim(), indexCollaborazione.trim(), id_gara, flagDL133);

         ris = new ResponseModificaGara();
         ris.setError(responseModificaGara.getError());
         ris.setMessaggio(responseModificaGara.getMessaggio());
         ris.setSuccess(responseModificaGara.isSuccess());

      } else {
         ris = new ResponseModificaGara();
         ris.success = false;
         ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
      }
      return ris;
   }

   /**
	 * Metodo che rappresenta la funzione PERFEZIONAGARA, esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso della
	 * operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param id_gara 
	 *  
	 * @return ResponsePerfezionaGara
	 */
	@WebMethod
	public ResponsePerfezionaGara perfezionaGara(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "id_gara") String id_gara) {

		ResponsePerfezionaGara ris = null;

		if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false) && General.isNumber(id_gara, true)) {
			
			it.avlp.simog.common.beans.ResponsePerfezionaGara responsePerfezionaGara = PerfezionaGaraActionManager.execute(ticket.trim(), indexCollaborazione.trim(), id_gara);

			ris = new ResponsePerfezionaGara();
			ris.setMessaggio(responsePerfezionaGara.getMessaggio());
			ris.setError(responsePerfezionaGara.getError());
			ris.setSuccess(responsePerfezionaGara.isSuccess());
		} else {
			ris = new ResponsePerfezionaGara();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * Metodo che rappresenta la funzione CANCELLAAGARA, esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso della
	 * operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param id_gara
	 * @param id_motivazione
	 * @param note_canc
	 * 
	 * @return ResponseCancellaGara
	 */
	@WebMethod
	public ResponseCancellaGara cancellaGara(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "id_gara") String id_gara, @WebParam(name = "id_motivazione") String id_motivazione, @WebParam(name = "note_canc") String note_canc) {

		ResponseCancellaGara ris = null;

		if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false) && General.isNumber(id_gara, true) && General.isNumber(id_motivazione, true) && note_canc != null) {

			it.avlp.simog.common.beans.ResponseCancellaGara responseCancellaGara = CancellaGaraActionManager.execute(ticket.trim(), indexCollaborazione.trim(), id_gara, id_motivazione.trim(), note_canc.trim());

			ris = new ResponseCancellaGara();
			ris.setMessaggio(responseCancellaGara.getMessaggio());
			ris.setError(responseCancellaGara.getError());
			ris.setSuccess(responseCancellaGara.isSuccess());
		} else {
			ris = new ResponseCancellaGara();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione "CANCELLALOTTO" esegue il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa dell'operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param cig
	 * @param id_motivazione
	 * @param note_canc
	 * 
	 * @return ResponseCancellaLotto
	 */
	@WebMethod
	public ResponseCancellaLotto cancellaLotto(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "cig") String cig, @WebParam(name = "id_motivazione") String id_motivazione, @WebParam(name = "note_canc") String note_canc) {

		ResponseCancellaLotto ris = null;
		if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false) && cig != null && !cig.trim().equals("") && General.isNumber(id_motivazione, true) && note_canc != null) {

			it.avlp.simog.common.beans.ResponseCancellaLotto responseCancellaLotto = CancellaLottoActionManager.execute(ticket.trim(), indexCollaborazione.trim(), id_motivazione.trim(), note_canc.trim(), cig.trim());

			ris = new ResponseCancellaLotto();
			ris.setMessaggio(responseCancellaLotto.getMessaggio());
			ris.setError(responseCancellaLotto.getError());
			ris.setSuccess(responseCancellaLotto.isSuccess());
		} else {
			// raise exception
			ris = new ResponseCancellaLotto();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione "PUBBLICAZIONEBANDO" esegue il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa dell'operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param cig
	 * @param dataPubblicazione
	 * @param dataScadenzaPag
	 * @param oraScadenza
	 * @param progCui
     * @param dataScadenzaRichiestaInvito
     * @param dataLetteraInvito	 
	 * @param tipoOperazione
	 * @param allegato
	 * @param datiPubblicazione
	 * 
	 * @return ResponsePubblicazioneBando
	 */
	@WebMethod
	public ResponsePubblicazioneBando pubblica(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "cig") String cig, @WebParam(name = "dataPubblicazione") String dataPubblicazione, @WebParam(name = "dataScadenzaPag") String dataScadenzaPag, 
			@WebParam(name = "oraScadenza") String oraScadenza, @WebParam(name = "progCui") String progCui, @WebParam(name = "tipoOperazione") String tipoOperazione, 
			@WebParam(name = "allegato") AllegatoType[] allegato, @WebParam(name = "datiPubblicazione") PubblicazioneWS datiPubblicazione, 
			@WebParam(name = "dataScadenzaRichiestaInvito") String dataScadenzaRichiestaInvito, @WebParam(name = "dataLetteraInvito") String dataLetteraInvito
			// is3031_RNFDBGL01Active()
			,@WebParam(name = "CUPLOTTO") it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType cupLotto[]) {

		ResponsePubblicazioneBando ris = null;
		String datiPubblicazioneXml = null;

		if ((ticket != null && !ticket.trim().equals("")) && cig != null && !cig.equals("") && General.isNumber(indexCollaborazione, false)) {

			//pof = new PubblicaObjectFactory();

			try {
				//JAXBElement<PubblicazioneWS> gt = pof.createPubblicazione(datiPubblicazione);
				JAXBContext jaxbContext = JAXBContext.newInstance(PubblicazioneWS.class);
				Marshaller m = jaxbContext.createMarshaller();
				os = null;
				if(datiPubblicazione != null){
				   os = new ByteArrayOutputStream();
				   m.marshal(datiPubblicazione, os);
				}
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			if(os != null){
	         int index = os.toString().indexOf("?", 2) + 2;
            datiPubblicazioneXml = os.toString().substring(index);

			}

			ArrayList<CUPLOTTO> listaCupWs = new ArrayList<CUPLOTTO>();
			if(cupLotto!=null && cupLotto.length > 0){
			   
			   for(CUPLOTTOType elem : cupLotto){
			      CUPLOTTO item = new CUPLOTTO();
			      item.setCIG(elem.getCIG().getValue());
			      
			      if(elem.getCODICICUP() != null && elem.getCODICICUP().size()> 0){
			         CodiciCup[] arr = new CodiciCup[elem.getCODICICUP().size()];
			         int i = 0;
			         for(DatiCUPType temp : elem.getCODICICUP()){
			            CodiciCup bean = new CodiciCup();
			            bean.setCUP(temp.getCUP().getValue());
			            bean.setOK_UTENTE(temp.getOKUTENTE().value());
			            arr[i++] = bean;
			         }
                  item.setCODICICUP(arr);
			      }
	            listaCupWs.add(item);
			   }
			}
			
			CUPLOTTO[] array = listaCupWs.toArray(new CUPLOTTO[listaCupWs.size()]);
			
			it.avlp.simog.common.beans.ResponsePubblicazioneBando responsePubblicazioneBando = PubblicazioneBandoActionManager.execute(ticket.trim(), indexCollaborazione, 
					dataPubblicazione, dataScadenzaPag, cig.trim(), progCui, datiPubblicazioneXml, 
					tipoOperazione, allegato, oraScadenza, dataScadenzaRichiestaInvito, 
					dataLetteraInvito, array, false);

			ris = new ResponsePubblicazioneBando();
			ris.setMessaggio(responsePubblicazioneBando.getMessaggio());
			ris.setError(responsePubblicazioneBando.getError());
			ris.setSuccess(responsePubblicazioneBando.isSuccess());
			
         // genero l'elenco dei dati relativi ai codici cup validati e non 
         // è un array di oggetti CUPLOTTO
         if(SimogFlags.is3031_RFWEBGL02Active() 
               && SimogProperties.getInstance().isCUPAttivo()
               && responsePubblicazioneBando.getCUPLOTTO()!=null
               && responsePubblicazioneBando.getCUPLOTTO().length > 0){
            
            ArrayList<CUPLOTTOType> out = new ArrayList<CUPLOTTOType>();
            
            CUPLOTTO[] resp = responsePubblicazioneBando.getCUPLOTTO();
            
            if(resp != null){
               for(CUPLOTTO  elem : resp){
                  CUPLOTTOType item = new CUPLOTTOType();
                  CigType cigt = new CigType();
                  cigt.setValue(elem.getCIG());
                  item.setCIG(cigt);

                  for(CodiciCup codl : elem.getCODICICUP()){
                     DatiCUPType dati = new DatiCUPType();
                     CupType tcup = new CupType();
                     tcup.setValue(codl.getCUP());
                     dati.setCUP(tcup);
                     dati.setDATIDIPE(codl.getDATI_DIPE());
                     //dati.setIDRICHIESTA(new Long);
                     dati.setOKUTENTE(FlagSNType.fromValue(codl.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : codl.getOK_UTENTE()));
                     dati.setVALIDO(FlagSNType.fromValue(codl.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : codl.getVALIDO()));
                     item.getCODICICUP().add(dati );
                  }
                  out.add(item);
               }
               
               CUPLOTTOType[] tempOut = out.toArray(new CUPLOTTOType[out.size()]);
               
               ris.setCUPLOTTO((CUPLOTTOType[]) tempOut);
            }
         }

		} else {
			ris = new ResponsePubblicazioneBando();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	/**
	 * metodo che rappresenta la funzione "INVIA REQUISITI" esegue il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa dell'operazione
	 * 
	 * @param ticket
	 * @param indexCollaborazione
	 * @param id_gara
	 * @param requisiti
	 * 
	 * @return ResponsePubblicazioneBando
	 */
	@WebMethod
	public ResponseInviaRequisiti inviaRequisiti(@WebParam(name = "ticket") String ticket, @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			@WebParam(name = "id_gara") String id_gara, @WebParam(name = "requisiti") RequisitiWS requisiti) {

		ResponseInviaRequisiti ris = null;
		String requisitiXml = null;

		if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false) && General.isNumber(id_gara, true) && requisiti != null) {

			//rof = new RequisitiObjectFactory();

			try {
				//JAXBElement<RequisitiWS> gt = rof.createRequisiti(requisiti);
				JAXBContext jaxbContext = JAXBContext.newInstance(RequisitiWS.class);
				Marshaller m = jaxbContext.createMarshaller();
				os = new ByteArrayOutputStream();
				m.marshal(requisiti, os);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			int index = os.toString().indexOf("?", 2) + 2;
			requisitiXml = os.toString().substring(index);

			it.avlp.simog.common.beans.ResponseInviaRequisiti responseInviaRequisiti = InviaRequisitiActionManager.execute(ticket.trim(), indexCollaborazione.trim(), id_gara, requisitiXml);

			ris = new ResponseInviaRequisiti();
			ris.setError(responseInviaRequisiti.getError());
			ris.setMessaggio(responseInviaRequisiti.getMessaggio());
			ris.setSuccess(responseInviaRequisiti.isSuccess());

		} else {
			ris = new ResponseInviaRequisiti();
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
		return ris;
	}

	  /**
    * Metodo che rappresenta la funzione PRESAINCARICO, esegue solo il controllo dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita che si occupa del flusso della
    * operazione
    * 
    * @param ticket 
    * @param indexCollaborazione
    * @param id_gara
    * @param datiGara
    * 
    * @return ResponseModificaGara
    */
   @WebMethod
   public ResponsePresaInCarico presaInCarico (
         @WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "garaOcig") String garaOcig, 
         @WebParam(name = "estremiProvv") String estremiProvv,
         @WebParam(name = "flagDatiComuni") String flagDatiComuni) {

      ResponsePresaInCarico ris = null;
      String datiGaraxml = null;

      // controllo validità parametri
      boolean paramOk = true;
           
      // ticket valorizzato
      if(ticket == null || ticket.trim().equals(""))
         paramOk = false;
      
      // estremi valorizzato e non maggiore di 250 caratteri
   if(estremiProvv == null || estremiProvv.trim().equals("") || estremiProvv.length() > 250)
         paramOk = false;

      // ticket valorizzato S o N
      if(Costanti.FLAG_VALORE_SI.equals(flagDatiComuni) && Costanti.FLAG_VALORE_NO.equals(flagDatiComuni))
         paramOk = false;

      // garaocig valorizzato, numerico se non lungo 10
      if(garaOcig == null || garaOcig.equals("")
         || (garaOcig.length() != 10 && !General.isNumber(garaOcig, true))
        )
         paramOk = false;
      
      // gara cig lunghezza 10 se daticomuni = si
      if(garaOcig.length() != 10 && Costanti.FLAG_VALORE_SI.equals(flagDatiComuni))
         paramOk = false;
      
      // indice coll. numerico
      if(!General.isNumber(indexCollaborazione,false))
         paramOk = false;
            
      // il resto lo verifica la action
      if(paramOk){

         it.avlp.simog.common.beans.ResponsePresaCarico response = PresaInCaricoActionManager.execute(ticket, indexCollaborazione,
               garaOcig, estremiProvv, flagDatiComuni);

         ris = new ResponsePresaInCarico();
         ris.setError(response.getError());
         ris.setMessaggio(response.getMessaggio());
         ris.setSuccess(response.isSuccess());

      } else {
         ris = new ResponsePresaInCarico();
         ris.success = false;
         ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
      }
      return ris;
   }

   //TICKET ALM - 3.04.3
   /**
    * Metodo che si occupa della presa in carico delle gare che sono state delegate dalla SA delegante
    * 
    * @param ticket 
    * @param indexCollaborazione
    * @param id_gara
    * @param datiGara
    * 
    * @return ResponseModificaGara
    */
   @WebMethod
   public ResponsePresaInCarico presaInCaricoGaraDelegata (
         @WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "idgara") String idgara) {

      ResponsePresaInCarico ris = null;
      String datiGaraxml = null;

      // controllo validità parametri
      boolean paramOk = true;
           
      // ticket valorizzato
      if(ticket == null || ticket.trim().equals(""))
         paramOk = false;
      
      // garaocig valorizzato, numerico se non lungo 10
      if(idgara == null || idgara.equals(""))
         paramOk = false;
      
      // indice coll. numerico
      if(!General.isNumber(indexCollaborazione,false))
         paramOk = false;
            
      // il resto lo verifica la action
      if(paramOk){
    	  boolean isGara = idgara.length() != 10 && General.isNumber(idgara, true);

       it.avlp.simog.common.beans.ResponsePresaCarico response = PresaInCaricoGaraDelegataActionManager.execute(ticket, indexCollaborazione,
               idgara,isGara);
     
         ris = new ResponsePresaInCarico();
         ris.setError(response.getError());
         ris.setMessaggio(response.getMessaggio());
         ris.setSuccess(response.isSuccess());

      } else {
         ris = new ResponsePresaInCarico();
         ris.success = false;
         ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
      }
      return ris;
   }
   
   
   /**
    * metodo che rappresenta la funzione "INTEGRACUP" esegue il controllo
    * dei campi (che non siano nulli) il resto viene devoluto ad una classe apposita
    * che si occupa dell'operazione
    * 
    * @param String: ticket
    * @param String: indexCollaborazione, indice della collaborazione con la quale si intende effettuare l'operazione
    * @param String: cig
    * @param String: flagCUP
    * @param String: CUPLOTTO (optional se flagCUP=N)
    * @return ResponseIntegraCUP
    * 
    */
   @WebMethod
   public ResponseIntegraCUP integraCUP(@WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "cig") String cig, 
         @WebParam(name = "flagCUP") String flagCUP, 
         @WebParam(name = "TIPIAPPALTOL") it.avlp.simog.ws.massload.xmlbeans.TipiAppaltoType TIPIAPPALTOL[],
         @WebParam(name = "TIPIAPPALTOFS") it.avlp.simog.ws.massload.xmlbeans.TipiAppaltoType TIPIAPPALTOFS[],
         @WebParam(name = "CUPLOTTO") CUPLOTTOType CUPLOTTO) {

      ResponseIntegraCUP ris = null;

      // conversione dei tipi in bean per il passaggio alla logica di business
      CUPLOTTO locCL = new CUPLOTTO();
      CodiciCup[] locCC = null;
      
      if(CUPLOTTO != null && CUPLOTTO.getCODICICUP()!= null){
         locCC =  new CodiciCup[CUPLOTTO.getCODICICUP().size()];
         int i = 0;
         for(DatiCUPType elem : CUPLOTTO.getCODICICUP()){
            locCC[i] = new CodiciCup();
            locCC[i].setCUP(elem.getCUP().getValue());
            locCC[i].setOK_UTENTE(elem.getOKUTENTE().value());
            
            i++;
         }
         locCL.setCODICICUP(locCC);
      }

      TIPIAPPALTO locTAL = null;
      TIPIAPPALTO locTAFS = null;

      if(TIPIAPPALTOL != null && TIPIAPPALTOL.length > 0){
         locTAL = new TIPIAPPALTO();
         String[] locTipi = new String[TIPIAPPALTOL.length];
         int i = 0;
         for (it.avlp.simog.ws.massload.xmlbeans.TipiAppaltoType elem : TIPIAPPALTOL){
            
            locTipi[i] = elem.getIDAPPALTO().getValue();
            i++;
         }
         locTAL.setTIPOAPPALTO(locTipi);
      }
            
      if(TIPIAPPALTOFS != null && TIPIAPPALTOFS.length > 0){
         locTAFS = new TIPIAPPALTO();
         String[] locTipi = new String[TIPIAPPALTOFS.length];//TICKET ALM #8059 MAC
         int i = 0;
         for (it.avlp.simog.ws.massload.xmlbeans.TipiAppaltoType elem : TIPIAPPALTOFS){
            locTipi[i] = elem.getIDAPPALTO().getValue();
            i++;
         }
         locTAFS.setTIPOAPPALTO(locTipi);
      }

      if ((ticket != null && !ticket.trim().equals("")) 
            && (flagCUP != null && (Costanti.FLAG_VALORE_SI.equals(flagCUP) || Costanti.FLAG_VALORE_NO.equals(flagCUP)))
            && (cig != null && !cig.equals("") && !(cig.length() != 10)) 
            && General.isNumber(indexCollaborazione, false)
            // && CUPLOTTO != null
            ) {

         it.avlp.simog.common.beans.ResponseIntegraCUP response = IntegraCUPActionManager.execute(ticket.trim(), indexCollaborazione, cig.trim(), 
               flagCUP, locTAL, locTAFS, locCL);
         
         ris = new ResponseIntegraCUP();
         ris.setMessaggio(response.getMessaggio());
         ris.setError(response.getError());
         ris.setSuccess(response.isSuccess());
         
         if(SimogFlags.is3031_RFWEBGL02Active() 
               && SimogProperties.getInstance().isCUPAttivo()
               && response.getCUPLOTTO()!=null
               && response.getCUPLOTTO().getCODICICUP()!=null){
            CUPLOTTOType elem = new CUPLOTTOType();
            CigType cigt = new CigType();
            cigt.setValue("");
            elem.setCIG(cigt);
            
            for(CodiciCup item : response.getCUPLOTTO().getCODICICUP()){
               DatiCUPType dati = new DatiCUPType();
               CupType tcup = new CupType();
               tcup.setValue(item.getCUP());
               dati.setCUP(tcup);
               dati.setDATIDIPE(item.getDATI_DIPE());
               //dati.setIDRICHIESTA(new Long);
               dati.setOKUTENTE(FlagSNType.fromValue(item.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : item.getOK_UTENTE()));
               dati.setVALIDO(FlagSNType.fromValue(item.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item.getVALIDO()));
               elem.getCODICICUP().add(dati );
            }
            ris.setCUPLOTTO(elem);           
         }

      } else {
         ris = new ResponseIntegraCUP();
         ris.success = false;
         ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
      }
      return ris;
   }
   
   //MEV 37010 3.04.8.1
   @WebMethod
   public ResponseIntegraPariOpportunita integraPariOpportunita(@WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "cig") String cig, 
         @WebParam(name = "FLAG_PNRR_PNC") it.avlp.simog.ws.massload.xmlbeans.FlagSNType flagPnrrPnc, 
         @WebParam(name = "FLAG_PREVISIONE_QUOTA") it.avlp.simog.ws.massload.xmlbeans.FlagSNQType flagPrevisioneQuota,
   		 @WebParam(name = "QUOTA_FEMMINILE") it.avlp.simog.ws.massload.xmlbeans.PercentualeType quotaFemminile,
		 @WebParam(name = "QUOTA_GIOVANILE") it.avlp.simog.ws.massload.xmlbeans.PercentualeType quotaGiovanile,
		 @WebParam(name = "FLAG_MISURE_PREMIALI") it.avlp.simog.ws.massload.xmlbeans.FlagSNType flagMisurePremiali,
         @WebParam(name = "MotivoDeroga") it.avlp.simog.ws.massload.xmlbeans.MotivoDerogaType MotivoDeroga[],
         @WebParam(name = "MisuraPremiale") it.avlp.simog.ws.massload.xmlbeans.MisuraPremialeType MisuraPremiale[])
     {

	   ResponseIntegraPariOpportunita ris = null;
	   
	// conversione dei tipi in bean per il passaggio alla logica di business
	   List<MotivoDerogaBean> locMD = null;
	   List<MisuraPremialeBean> locMP = null;

	      if(MotivoDeroga != null && MotivoDeroga.length > 0){
	    	  locMD = new ArrayList<MotivoDerogaBean>();
	         String[] locTipi = new String[MotivoDeroga.length];
	         int i = 0;
	         for (it.avlp.simog.ws.massload.xmlbeans.MotivoDerogaType elem : MotivoDeroga){
	            
	            locTipi[i] = elem.getValue();
	            MotivoDerogaBean mdbTemp = new MotivoDerogaBean();
	            mdbTemp.setIdMotivoDeroga(Long.parseLong(locTipi[i]));
	            locMD.add(mdbTemp);
	            i++;
	         }
	         
	      }
	      
	      if(MisuraPremiale != null && MisuraPremiale.length > 0){
	    	  locMP = new ArrayList<MisuraPremialeBean>();
	         String[] locTipi = new String[MisuraPremiale.length];
	         int i = 0;
	         for (it.avlp.simog.ws.massload.xmlbeans.MisuraPremialeType elem : MisuraPremiale){
	            
	        	locTipi[i] = elem.getValue();
	        	MisuraPremialeBean mdbTemp = new MisuraPremialeBean();
	            mdbTemp.setIdMisuraPremiale(Long.parseLong(locTipi[i]));
	            locMP.add(mdbTemp);
	            i++;
	         }
	         
	      }
	      
	      BigDecimal quotaFemminileBD = null;
	      BigDecimal quotaGiovanileBD = null;
	      if (quotaFemminile != null) {
	    	  quotaFemminileBD = quotaFemminile.getValue();
	      }
	      if (quotaGiovanile != null) {
	    	  quotaGiovanileBD = quotaGiovanile.getValue();
	      }
	      
	      

     
     
      if ((ticket != null && !ticket.trim().equals("")) 
            && (flagPnrrPnc != null && (Costanti.FLAG_VALORE_SI.equals(flagPnrrPnc.toString()) || Costanti.FLAG_VALORE_NO.equals(flagPnrrPnc.toString())))
            && (cig != null && !cig.equals("") && !(cig.length() != 10)) 
            && General.isNumber(indexCollaborazione, false)
            // && CUPLOTTO != null
            ) {

         it.avlp.simog.common.beans.ResponseIntegraPariOpportunita response = IntegraPariOpportunitaActionManager.execute(ticket.trim(), indexCollaborazione, cig.trim(), 
        		 flagPnrrPnc, flagPrevisioneQuota, quotaFemminileBD, quotaGiovanileBD, flagMisurePremiali, locMD, locMP);
         
         ris = new ResponseIntegraPariOpportunita();
         ris.setMessaggio(response.getMessaggio());
         ris.setError(response.getError());
         ris.setSuccess(response.isSuccess());
         
//         if(SimogFlags.is3031_RFWEBGL02Active() 
//               && SimogProperties.getInstance().isCUPAttivo()
//               && response.getCUPLOTTO()!=null
//               && response.getCUPLOTTO().getCODICICUP()!=null){
//            CUPLOTTOType elem = new CUPLOTTOType();
//            CigType cigt = new CigType();
//            cigt.setValue("");
//            elem.setCIG(cigt);
//            
//            for(CodiciCup item : response.getCUPLOTTO().getCODICICUP()){
//               DatiCUPType dati = new DatiCUPType();
//               CupType tcup = new CupType();
//               tcup.setValue(item.getCUP());
//               dati.setCUP(tcup);
//               dati.setDATIDIPE(item.getDATI_DIPE());
//               //dati.setIDRICHIESTA(new Long);
//               dati.setOKUTENTE(FlagSNType.fromValue(item.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : item.getOK_UTENTE()));
//               dati.setVALIDO(FlagSNType.fromValue(item.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item.getVALIDO()));
//               elem.getCODICICUP().add(dati );
//            }
//            ris.setCUPLOTTO(elem);           
//         }

      } else {
         ris = new ResponseIntegraPariOpportunita();
         ris.success = false;
         ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
      }
      return ris;
   }
   //FINE MEV 37010 3.04.8.1
   
 //MEV 3.04.10 43227
   @WebMethod
   public ResponseModificaDatiPerfezionamento modificaDatiPerfezionamento(@WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "cig") String cig, 
         @WebParam(name = "DATA_SCADENZA_OFFERTE") String dataScadenzaOfferte, 
         @WebParam(name = "ORA_SCADENZA_OFFERTE") String oraScadenzaOfferte,
   		 @WebParam(name = "DATA_SCADENZA_RIC_INVITO") String dataScadenzaRicInvito)
		 
     {

	   ResponseModificaDatiPerfezionamento ris = null;
	   
	// conversione dei tipi in bean per il passaggio alla logica di business
//	   List<MotivoDerogaBean> locMD = null;
//	   List<MisuraPremialeBean> locMP = null;
//
//	      if(MotivoDeroga != null && MotivoDeroga.length > 0){
//	    	  locMD = new ArrayList<MotivoDerogaBean>();
//	         String[] locTipi = new String[MotivoDeroga.length];
//	         int i = 0;
//	         for (it.avlp.simog.ws.massload.xmlbeans.MotivoDerogaType elem : MotivoDeroga){
//	            
//	            locTipi[i] = elem.getValue();
//	            MotivoDerogaBean mdbTemp = new MotivoDerogaBean();
//	            mdbTemp.setIdMotivoDeroga(Long.parseLong(locTipi[i]));
//	            locMD.add(mdbTemp);
//	            i++;
//	         }
//	         
//	      }
//	      
//	      if(MisuraPremiale != null && MisuraPremiale.length > 0){
//	    	  locMP = new ArrayList<MisuraPremialeBean>();
//	         String[] locTipi = new String[MisuraPremiale.length];
//	         int i = 0;
//	         for (it.avlp.simog.ws.massload.xmlbeans.MisuraPremialeType elem : MisuraPremiale){
//	            
//	        	locTipi[i] = elem.getValue();
//	        	MisuraPremialeBean mdbTemp = new MisuraPremialeBean();
//	            mdbTemp.setIdMisuraPremiale(Long.parseLong(locTipi[i]));
//	            locMP.add(mdbTemp);
//	            i++;
//	         }
//	         
//	      }
//	      
//	      BigDecimal quotaFemminileBD = null;
//	      BigDecimal quotaGiovanileBD = null;
//	      if (quotaFemminile != null) {
//	    	  quotaFemminileBD = quotaFemminile.getValue();
//	      }
//	      if (quotaGiovanile != null) {
//	    	  quotaGiovanileBD = quotaGiovanile.getValue();
//	      }
	      
	      

     
     
      if ((ticket != null && !ticket.trim().equals("")) && (cig != null && !cig.equals("") && !(cig.length() != 10)) 
            && General.isNumber(indexCollaborazione, false)
            // && CUPLOTTO != null
            ) {

         it.avlp.simog.common.beans.ResponseModificaDatiPerfezionamento response = ModificaDatiPerfezionamentoActionManager.execute(ticket.trim(), indexCollaborazione, cig.trim(), 
        		 dataScadenzaOfferte, oraScadenzaOfferte, dataScadenzaRicInvito);
         
         ris = new ResponseModificaDatiPerfezionamento();
         ris.setMessaggio(response.getMessaggio());
         ris.setError(response.getError());
         ris.setSuccess(response.isSuccess());
         
//         if(SimogFlags.is3031_RFWEBGL02Active() 
//               && SimogProperties.getInstance().isCUPAttivo()
//               && response.getCUPLOTTO()!=null
//               && response.getCUPLOTTO().getCODICICUP()!=null){
//            CUPLOTTOType elem = new CUPLOTTOType();
//            CigType cigt = new CigType();
//            cigt.setValue("");
//            elem.setCIG(cigt);
//            
//            for(CodiciCup item : response.getCUPLOTTO().getCODICICUP()){
//               DatiCUPType dati = new DatiCUPType();
//               CupType tcup = new CupType();
//               tcup.setValue(item.getCUP());
//               dati.setCUP(tcup);
//               dati.setDATIDIPE(item.getDATI_DIPE());
//               //dati.setIDRICHIESTA(new Long);
//               dati.setOKUTENTE(FlagSNType.fromValue(item.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : item.getOK_UTENTE()));
//               dati.setVALIDO(FlagSNType.fromValue(item.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item.getVALIDO()));
//               elem.getCODICICUP().add(dati );
//            }
//            ris.setCUPLOTTO(elem);           
//         }

      } else {
         ris = new ResponseModificaDatiPerfezionamento();
         ris.success = false;
         ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
      }
      return ris;
   }
   //FINE MEV 3.04.10 43227
   
 //MEV 53643 3.04.13
   @WebMethod
   public ResponseModificaCPV modificaCPV(@WebParam(name = "ticket") String ticket, 
         @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
         @WebParam(name = "cig") String cig, 
         @WebParam(name = "CPV_PREVALENTE") String cpvPrevalente, 
         @WebParam(name = "CPV_SECONDARIA") it.avlp.simog.ws.massload.xmlbeans.CPVSecondariaType cpvSecondaria[])
         
		 
     {

	   ResponseModificaCPV ris = null;
	   
	 //conversione dei tipi in bean per il passaggio alla logica di business
	   List<CpvLotto> locCPV = null;

	      if(cpvSecondaria != null && cpvSecondaria.length > 0){
	    	  locCPV = new ArrayList<CpvLotto>();
	         String[] locTipi = new String[cpvSecondaria.length];
	         int i = 0;
	         for (it.avlp.simog.ws.massload.xmlbeans.CPVSecondariaType elem : cpvSecondaria){
	            
	            locTipi[i] = elem.getCODCPVSECONDARIA();
	            CpvLotto cpvLottoTemp = new CpvLotto();
	            cpvLottoTemp.setIdCpv(locTipi[i]);
	            locCPV.add(cpvLottoTemp);
	            i++;
	         }
	         
	      }

	      
	      

     
     
      if ((ticket != null && !ticket.trim().equals("")) && (cig != null && !cig.equals("") && !(cig.length() != 10)) 
            && General.isNumber(indexCollaborazione, false)
            // && CUPLOTTO != null
            ) {

         it.avlp.simog.common.beans.ResponseModificaCPV response = MoificaCPVActionManager.execute(ticket.trim(), indexCollaborazione, cig.trim(), 
        		 cpvPrevalente, locCPV);
         
         ris = new ResponseModificaCPV();
         ris.setMessaggio(response.getMessaggio());
         ris.setError(response.getError());
         ris.setSuccess(response.isSuccess());
         
//         if(SimogFlags.is3031_RFWEBGL02Active() 
//               && SimogProperties.getInstance().isCUPAttivo()
//               && response.getCUPLOTTO()!=null
//               && response.getCUPLOTTO().getCODICICUP()!=null){
//            CUPLOTTOType elem = new CUPLOTTOType();
//            CigType cigt = new CigType();
//            cigt.setValue("");
//            elem.setCIG(cigt);
//            
//            for(CodiciCup item : response.getCUPLOTTO().getCODICICUP()){
//               DatiCUPType dati = new DatiCUPType();
//               CupType tcup = new CupType();
//               tcup.setValue(item.getCUP());
//               dati.setCUP(tcup);
//               dati.setDATIDIPE(item.getDATI_DIPE());
//               //dati.setIDRICHIESTA(new Long);
//               dati.setOKUTENTE(FlagSNType.fromValue(item.getOK_UTENTE()== null ? Costanti.FLAG_VALORE_NO : item.getOK_UTENTE()));
//               dati.setVALIDO(FlagSNType.fromValue(item.getVALIDO()== null ? Costanti.FLAG_VALORE_NO : item.getVALIDO()));
//               elem.getCODICICUP().add(dati );
//            }
//            ris.setCUPLOTTO(elem);           
//         }

      } else {
         ris = new ResponseModificaCPV();
         ris.success = false;
         ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
      }
      return ris;
   }
   //FINE  //MEV 53643 3.04.13
   
   //TICKET ALM - 3.04.4
   /**
	 * metodo che restituisce 
	 * 
	 * @param ticket
	 * @param schede
	 * @param CIG
	 * 
	 * @return ResponseConsultaIniziativa
	 */
	@WebMethod
	public ResponseConsultaIniziativa consultaIniziativa(@WebParam(name = "ticket") String ticket, 
			                                         //    @WebParam(name = "territori") TerritorioType territori, 
			                                             @WebParam(name = "indexCollaborazione") String indexCollaborazione, 
			                                             @WebParam(name = "categorie") CategLottoType categorie,
			                                             @WebParam(name = "CIG") String CIG) {

		ResponseConsultaIniziativa ris = new ResponseConsultaIniziativa();
		IniziativaWS doc = null;

		if ((ticket != null && !ticket.trim().equals("") && General.isNumber(indexCollaborazione, false)) //&& 
				//((CIG != null && !CIG.trim().equals("") && (CIG.length() == 10)) 
				//|| (territori != null && territori.getCodRegioneIstat()!=null && territori.getCodRegioneIstat().size()>0)
						
				//|| (categorie != null && categorie.getCATEGORIA()!=null && categorie.getCATEGORIA().size()>0))
				) {
			String resp="";
			it.avlp.simog.common.beans.ResponseConsultaIniziativa responseConsultaIniziativa = ConsultaIniziativaActionManager.execute(ticket.trim(), 
					                                                                           CIG != null ? CIG.trim() : null, 
					                                                                        //   IniziativaConverter.convertTerritori(territori),
					                                                                        		   indexCollaborazione,
					                                                                           IniziativaConverter.convertCategorie(categorie)
					                                                                           );
			if(responseConsultaIniziativa.isSuccess() ) {
			resp=responseConsultaIniziativa.getIniziativaXML();

		      IniziativaWS schedaType = new IniziativaWS();
     		   try {
     				String packageName = schedaType.getClass().getPackage().getName();
    				
     				JAXBContext jc = JAXBContext.newInstance(packageName);
     				Unmarshaller u = jc.createUnmarshaller();
     				ByteArrayInputStream is = new ByteArrayInputStream(resp.getBytes());
     
     				doc = (IniziativaWS) u.unmarshal(is);
     				
     				if (doc != null) {
     					ris.setIniziativaXML(doc);
     					ris.setSuccess(true);
     				}
     		   } catch (Exception e) {
     				e.printStackTrace();
                 ris.setError((new ErrorManager(ErrorManager.SIMOGWS_XMLMANAGER_STRING_05).getError())  + ": " + resp);
                 ris.setSuccess(false);
     		   }
			
			} else {
				ris.setSuccess(false);
				ris.setError(responseConsultaIniziativa.getError());
			}

			
		} else{
			ris.success = false;
			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
		}
//		}
//			if (responseConsultaGara.isSuccess()){
//			   resp = responseConsultaGara.getGaraXML();
//
////     		   of = new SchedaObjectFactory();
////     		   SchedaType schedaType = of.createSchedaType();
//
//			      SchedaType schedaType = new SchedaType();
//     		   try {
//     				String packageName = schedaType.getClass().getPackage().getName();
//    				
//     				JAXBContext jc = JAXBContext.newInstance(packageName);
//     				Unmarshaller u = jc.createUnmarshaller();
//     				ByteArrayInputStream is = new ByteArrayInputStream(resp.getBytes());
//     
//     				doc = (IniziativaWS) u.unmarshal(is);
//     				
//     				if (doc != null) {
//     					ris.setIniziativaXML(doc);
//     					ris.setError(responseConsultaGara.getError());
//     					ris.setSuccess(responseConsultaGara.isSuccess());
//     				}
//     		   } catch (Exception e) {
//     				e.printStackTrace();
//                 ris.setError((new ErrorManager(ErrorManager.SIMOGWS_XMLMANAGER_STRING_05).getError())  + ": " + resp);
//                 ris.setSuccess(false);
//     		   }
//			}
//			else{
//              ris.setError(responseConsultaGara.getError());
//              ris.setSuccess(responseConsultaGara.isSuccess());			   
//			}
//		} else{
//			ris.success = false;
//			ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
//		}
		return ris;
	}

}
