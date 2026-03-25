package it.avlp.simog.ws.commons;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.garamanager.lotto.LottoManager;

import java.sql.Connection;

import org.apache.log4j.Logger;

	/**
	 * Classe che si occupa della chiamata per la generazione di un cig e 
	 * ne restituisce il bean
	 * 
	 * **/

public class CigManager {
	
	private Logger logger;

	/*	costruttore	*/
	public CigManager(){
		this.logger = LoggerManager.getInstance().getLogger();
	}
	/*	metodo per la generazione e restituzione di un bean cig	*/
	
	/********************************************************************
	 * Metodo che si occupa della generazione e restituzione del CIG
	 * @param con : Connection 
	 * @param User_id : String
	 * @param coll : Collaborazione
	 * @return CIGBean
	 * @throws SimogWSException
	 */
	public CIGBean generate(Connection con,String User_id,Collaborazione coll,String adminOr)throws SimogWSException{
		try{
			if(con != null){
				logger.debug("eseguendo: CIGBean generate(Connection con)");
				LottoManager lm = new LottoManager(con,logger);
				//3.04.3.2
				System.out.println("===== TB: START INIZIO RICHIESTA NUOVO CIG DA UTENTE "+User_id+" PER L'AMMINISTRAZIONE "+coll.getAzienda_codiceFiscale()+" tramite app "+CIGBean.APPL_WS);
				CIGBean cb = lm.generaCig(new CIGBean(CIGBean.APPL_WS, 
														User_id, 
														coll.getAzienda_codiceFiscale(), 
														coll.getUfficio_id()),
														adminOr);
				if(cb != null){
					System.out.println("===== TB: FINE RICHIESTA NUOVO CIG DA UTENTE "+User_id+" PER L'AMMINISTRAZIONE "+coll.getAzienda_codiceFiscale()+" tramite app "+CIGBean.APPL_WS);
					return cb;
				}else{
					throw new SimogWSException(ErrorManager.SIMOGWS_CIGMANAGER_NULL_01);
				}
			}else{
				logger.error("la connessione risulta nulla");
				throw new SimogWSException(ErrorManager.SIMOGWS_CIGMANAGER_NULL_02);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("eccezione occorsa provando a generare il cig per la response"+e.getMessage());
			throw new SimogWSException(ErrorManager.SIMOGWS_CIGMANAGER_SIMOGCOMMON_03);
		}
	}
}
