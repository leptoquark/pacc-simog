package it.avlp.simog.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import it.avcp.simog.manager.delega.DelegaDatiSimogManager;
import it.avlp.simog.beans.DelegaDatiSimog;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

public class DelegaDatiSimogAction extends BaseAction {

	public DelegaDatiSimogAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);

	}
	
	

	/**Controlla se tutti gli osservatori dell'utente currentUser sono disabilitati
	 * @param currentDate
	 * @param currentUser
	 * @return il messaggio da visualizzare, oppure null se esiste almeno un osservatorio assiocato all'utente che e abilitato
	 * @throws ActionException
	 */
	public String getDelegaAllMessage(Date currentDate, Utente currentUser)throws ActionException{
		if(currentUser != null && !currentUser.isRSSAorRUP())
			return null;
		
		boolean disabled = true;
		DelegaDatiSimogManager man = new DelegaDatiSimogManager(connection,
				logger);
		String lastOss = null;
		for(String oss: currentUser.getOsservatori().values()){
			lastOss = oss;
			disabled = disabled &&
			getDelegaCIGMessage(oss, currentDate, currentUser) != null && 
			getDelegaSchedeMessage(oss, currentDate, currentUser) != null;
		}
		
		if(disabled){
			DelegaDatiSimog das;
			try {
				das = man.getDelegaDatiSimog(lastOss);
			} catch (SQLException e) {
				throw new ActionException(e);
			}
			return createMessage(Messaggi.SIMOG_DELEGA_003, das.getDelegaCigDal(), das.getDescrizione(), das.getUrlSistema());
		}
		return null;
	}

	/**Controlla se la richiesta CIG e abilitata o meno per l'osservatorio
	 * @param idOsservatorio osservatorio a cui verificare lo stato di abilitazione
	 * @param currentDate
	 * @param currentUser
	 * @return Il messaggio da visualizzare all'utente oppure null se l'oservatorio puo richiedere il CIG/ gestire le gare
	 * @throws ActionException
	 */
	public String getDelegaCIGMessage(String idOsservatorio, Date currentDate, Utente currentUser)
			throws ActionException {
		if(currentUser != null && !currentUser.isRSSAorRUP())
			return null;
		DelegaDatiSimogManager man = new DelegaDatiSimogManager(connection,
				logger);

		try {
			DelegaDatiSimog bean = man.getDelegaDatiSimog(idOsservatorio);
			if (Costanti.FLAG_VALORE_SI.equalsIgnoreCase(bean.getDelegaCig())) {
				if (currentDate != null && bean.getDelegaCigDal() != null) {
					Calendar curr = Calendar.getInstance();
					curr.setTime(currentDate);
					Calendar dal = Calendar.getInstance();
					dal.setTime(bean.getDelegaCigDal());
					if (dal.before(curr))
						return createMessage(Messaggi.SIMOG_DELEGA_001,
								bean.getDelegaCigDal(), bean.getDescrizione(),
								bean.getUrlSistema());

				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}

		return null;
	}


	/**Controlla se la gestione delle schede e abilitata o meno per l'osservatorio
	 * @param idOsservatorio osservatorio a cui verificare lo stato di abilitazione
	 * @param currentDate
	 * @param currentUser
	 * @return Il messaggio da visualizzare all'utente oppure null se l'oservatorio puo gestire le schede
	 * @throws ActionException
	 */
	public String getDelegaSchedeMessage(String idOsservatorio, Date currentDate, Utente currentUser)
			throws ActionException {
		if(currentUser != null && !currentUser.isRSSAorRUP())
			return null;
		DelegaDatiSimogManager man = new DelegaDatiSimogManager(connection,
				logger);

		try {
			DelegaDatiSimog bean = man.getDelegaDatiSimog(idOsservatorio);
			if (Costanti.FLAG_VALORE_SI
					.equalsIgnoreCase(bean.getDelegaSchede())) {
				if (currentDate != null && bean.getDelegaSchedeDal() != null) {
					Calendar curr = Calendar.getInstance();
					curr.setTime(currentDate);
					Calendar dal = Calendar.getInstance();
					dal.setTime(bean.getDelegaSchedeDal());
					if (dal.before(curr))
						return createMessage(Messaggi.SIMOG_DELEGA_002,
								bean.getDelegaSchedeDal(),
								bean.getDescrizione(), bean.getUrlSistema());

				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}

		return null;
	}

	/**
	 * Crea un messaggio dinamico usando il template e i parametri in input
	 * 
	 * @param template
	 *            Template del messaggio
	 * @param params
	 *            Parametri del messaggio, sostituisce i $i
	 * @return messaggio
	 */
	public String createMessage(String template,Date date, String desc, String url) {
		template = template.replace("$1", PageHelper.getViewDate(date));
		template = template.replace("$2", desc);
		
		if(url != null){
			if(url.startsWith("http://"))
				url = url.replace("http//", "");
			url = url.replace(" ", "%20");
			
		}
		logger.debug("url:" + url);
		return template.replace("$3", url);    
		
	}

}
