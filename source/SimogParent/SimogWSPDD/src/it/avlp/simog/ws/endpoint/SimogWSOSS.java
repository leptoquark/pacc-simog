package it.avlp.simog.ws.endpoint;

import it.avlp.simog.ws.beans.ResponseCheckLogin;
import it.avlp.simog.ws.beans.ResponseChiudiSession;
import it.avlp.simog.ws.beans.ResponseConsultaGara;
import it.avlp.simog.ws.beans.ResponseConsultaNumeroGara;

import java.io.OutputStream;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "xmlbeans.massload.simog.avlp.it")
public class SimogWSOSS {

//	private SchedaObjectFactory of;
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

	   // richiamo quella principale
	   ResponseCheckLogin ris = null;
		SimogWSPDD pdd = new SimogWSPDD();
		ris = pdd.login(login, password);
		
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

		ResponseConsultaGara ris = null;
		SimogWSPDD pdd = new SimogWSPDD();
		ris = pdd.consultaGara(ticket, schede, CIG);
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

		ResponseConsultaNumeroGara ris = null;
	   SimogWSPDD pdd = new SimogWSPDD();
	   ris = pdd.consultaNumeroGara(ticket, schede, id_gara);   
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
		SimogWSPDD pdd = new SimogWSPDD();
		ris = pdd.chiudiSessione(ticket);
		return ris;
	}

}
