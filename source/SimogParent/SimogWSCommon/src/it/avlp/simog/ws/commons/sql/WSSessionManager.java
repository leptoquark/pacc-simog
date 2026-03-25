package it.avlp.simog.ws.commons.sql;

import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.generated.WS_KEYS;
import it.avlp.simog.db.generated.WS_SESSIONS;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
public class WSSessionManager {

    //--------- login query --------------//
	/*
	 * PRIMA DELL'INSERIMENTO DI UNA NUOVA SESSIONE
	 * -	SE TROVATA E NON VALIDA CHIUSURA (S_STATUS E S_END)
	 * -	SE TROVATA "ERRORE" O MEGLIO NOTIFICA SESSIONE GIA ESISTENTE
	 **/
	private final String SELECT_FIND_SESSION =
		"SELECT "
		+ WS_SESSIONS.SESSION_ID	+ ", " 
		+ WS_SESSIONS.USER_ID		+ ", " 
		+ WS_SESSIONS.XMLAUTH		+ ", " 
		+ WS_SESSIONS.USERSTATUS	+ ", " 
		+ WS_SESSIONS.SESSION_START	+ ", " 
		+ WS_SESSIONS.SESSION_END	+ ", " 
		+ WS_SESSIONS.TICKET		+ ", " 
		+ WS_SESSIONS.LAST_ERROR	+ ", " 
		+ WS_SESSIONS.SESSION_STATUS+ ", " 
		+ WS_SESSIONS.COMANDO		+ ", " 
		+ WS_SESSIONS.COLLABORAZIONE
		+ " FROM " 
		+ WS_SESSIONS.TABLE_NAME
		+ " WHERE "
		+ WS_SESSIONS.USER_ID		+ " = ?  AND "
		+ WS_SESSIONS.SESSION_STATUS+ " != ?";	
	/*
	 * SE e' STATA TROVATA UNA SESSIONE NON VALIDA ASSOCIATA A UN 
	 * USER_ID 
	 **/
	private final String UPDATE_CLOSE_UNVALID_SESSION =
		"UPDATE "	+ WS_SESSIONS.TABLE_NAME
		+" SET "  	
		+ WS_SESSIONS.SESSION_END		+ " = ? " + ", "	
		+ WS_SESSIONS.SESSION_STATUS	+ " = ? "
		+" WHERE "	
		+ WS_SESSIONS.USER_ID		+ " = ? "
		+ " AND " + WS_SESSIONS.SESSION_STATUS+ " != ?";
	/*
	 * ON INSERT TAKE ID FOR SUCCESSIVE UPDATE
	 * 
	 **/
	private final String INSERT_BEGIN_SESSION = 
		"INSERT INTO "	+ WS_SESSIONS.TABLE_NAME
		+ " (" 
		+ WS_SESSIONS.USER_ID			+ ", " 
		+ WS_SESSIONS.SESSION_START		+ ", " 
		+ WS_SESSIONS.SESSION_END		+ ", " 
		+ WS_SESSIONS.SESSION_STATUS	+ ", " 
		+ WS_SESSIONS.COMANDO
		+ " ) VALUES (?, ?, ?, ?, ?)";
	private final String INSERT_BEGIN_SESSION_RPNT = 
			"INSERT INTO "	+ WS_SESSIONS.TABLE_NAME
			+ " (" 
			+ WS_SESSIONS.USER_ID			+ ", " 
			+ WS_SESSIONS.SESSION_START		+ ", " 
			+ WS_SESSIONS.SESSION_END		+ ", " 
			+ WS_SESSIONS.SESSION_STATUS	+ ", " 
			+ WS_SESSIONS.COMANDO			+ ", "
			+ WS_SESSIONS.RPNT_ID
			+ " ) VALUES (?, ?, ?, ?, ?, ?)";
	/*
	 * SE TUTTO OK PER IL LOGIN AGGIORNO SESSIONE
	 **/
	private final String UPDATE_SESSION_LOGIN = 
		"UPDATE "	+ WS_SESSIONS.TABLE_NAME
		+" SET "  	
		+	WS_SESSIONS.SESSION_END		+ " = ? " + ", "		
		+   WS_SESSIONS.SESSION_STATUS	+ " = ? " + ", "		
		+   WS_SESSIONS.XMLAUTH			+ " = ? " + ", "		
		+   WS_SESSIONS.USERSTATUS		+ " = ? " + ", "		
		+   WS_SESSIONS.TICKET			+ " = ? "
		+" WHERE "	
		+	WS_SESSIONS.SESSION_ID		+ " = ? ";
	/* AS NAME
	 **/
	private final String UPDATE_SESSION_AFTER_LOGIN_FAIL =
		"UPDATE " 	+	WS_SESSIONS.TABLE_NAME+" "
		+"SET "	
		+	WS_SESSIONS.SESSION_STATUS	+	" = ? "+", "
		+	WS_SESSIONS.SESSION_END	+	" = ? "+", "
		+	WS_SESSIONS.LAST_ERROR	+	" = ? "
		+" WHERE "	
		+	WS_SESSIONS.SESSION_ID	+	" = ? ";

	//-------- generacig query ----------------//
	/*
	 * FIND VALID SESSION ON REQUEST OPERATION
	 * 
	 **/
	private final String SELECT_FIND_VALID_SESSION =
		"SELECT "
		+ WS_SESSIONS.SESSION_ID	+ ", " 
		+ WS_SESSIONS.USER_ID		+ ", " 
		+ WS_SESSIONS.XMLAUTH		+ ", " 
		+ WS_SESSIONS.USERSTATUS	+ ", " 
		+ WS_SESSIONS.SESSION_START	+ ", " 
		+ WS_SESSIONS.SESSION_END	+ ", " 
		+ WS_SESSIONS.TICKET		+ ", " 
		+ WS_SESSIONS.LAST_ERROR	+ ", " 
		+ WS_SESSIONS.SESSION_STATUS+ ", " 
		+ WS_SESSIONS.COMANDO		+ ", " 
		+ WS_SESSIONS.COLLABORAZIONE
		+ " FROM " 
		+ WS_SESSIONS.TABLE_NAME
		+ " WHERE "
		+ WS_SESSIONS.TICKET 			+ " = ? AND " 
		+ WS_SESSIONS.SESSION_END		+ " > ? AND "
		+ WS_SESSIONS.SESSION_STATUS	+ " = ? ";
	/*
	 * UPDATE SESSION STATUS & COMANDO
	 * - manca collaborazione con la quale effetua la richiesta
	 **/
	private final String UPDATE_SESSION_STATUS_TO_PROCESSING = 
		"UPDATE "	+ WS_SESSIONS.TABLE_NAME
		+" SET "  	
		+	WS_SESSIONS.SESSION_STATUS	+ " = ? " + ", "
		+ 	WS_SESSIONS.COLLABORAZIONE	+ " = ? " + ", "
		+	WS_SESSIONS.COMANDO			+ " = ? "
		+" WHERE "	
		+	WS_SESSIONS.SESSION_ID		+ " = ? ";
	/*
	 * UPDATE SESSION STATUS,TIME-END,ESITO_COMANDO
	 **/
	private final String UPDATE_SESSION_AFTER_OP =
		"UPDATE " 	+	WS_SESSIONS.TABLE_NAME+" "
		+"SET "	
		+	WS_SESSIONS.SESSION_STATUS	+	" = ? "+", "
		+	WS_SESSIONS.LAST_ERROR	+	" = ? "+", "
		+	WS_SESSIONS.SESSION_END	+	" = ? "
		+" WHERE "	
		+	WS_SESSIONS.SESSION_ID	+	" = ? ";
	private final String UPDATE_SESSION_AFTER_OP_SENZA_ER =
		"UPDATE " 	+	WS_SESSIONS.TABLE_NAME+" "
		+"SET "	
		+	WS_SESSIONS.SESSION_STATUS	+	" = ? "+", "
		+	WS_SESSIONS.SESSION_END	+	" = ? "
		+" WHERE "	
		+	WS_SESSIONS.SESSION_ID	+	" = ? ";

	//--------- close session	---------------//
	/*
	 * UPDATE SESSION UNVALIDATE IT (RICHIESTA UTENTE)
	 **/
	private final String UPDATE_SESSION_UNVALIDATE_FROM_TICKET =
		"UPDATE "	+ WS_SESSIONS.TABLE_NAME
		+" SET "  	
		+	WS_SESSIONS.SESSION_END		+ " = ? " + ", "
		+	WS_SESSIONS.SESSION_STATUS	+ " = ? "
		+" WHERE "	
		+	WS_SESSIONS.TICKET			+ " = ? AND "
		+   WS_SESSIONS.SESSION_STATUS	+ " != ?";		


	//TICKET ALM #4508
	private final String SELECT_KEY_CONSULTA_GARA =
			"SELECT " + WS_KEYS.CODE +
			" FROM " + WS_KEYS.TABLE_NAME +
			" WHERE " + WS_KEYS.CODE + " = ? AND "
			+ WS_KEYS.KEY_END + " > ?";
	//FINE TICKET ALM #4508
	
	private Logger logger = null;
	private ConnectionWSManager cwsm = null;
	private Connection con = null;
	//private WsSessions wss = null;	
	
	/******************************************************
	 * Costruttore della classe
	 * @param logger : Logger
	 * @param cwsm : ConnectionWSManager
	 * @throws SimogWSException
	 */
	public WSSessionManager(Logger logger,ConnectionWSManager cwsm) throws SimogWSException{
		this.logger = logger;
		this.cwsm = cwsm;
		this.cwsm.createConnection(getClass().getName());
		this.con = cwsm.getConnection();
		if(con==null){
			logger.debug("connessione nulla");
		}
	}

	//-------------------------------------------------------//
	//--------------	LOGIN			---------------------//
	//-------------------------------------------------------//
	
	
	/****************************************************************************
	 * Ricerca una sessione 
	 * param wss : WsSessions
	 * return WsSessions
	 * throws SimogWSException
	 */
	private WsSessions selectFindSession(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: selectFindSession("+wss.toString()+")");
		if(con != null){
			WsSessions ws = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				ps = con.prepareStatement(SELECT_FIND_SESSION);
				ps.setString(1, wss.getUserId());
				ps.setString(2, "E");
				rs = ps.executeQuery();
				if(rs.next()){
					ws = new WsSessions();
					ws.setSessionId(rs.getLong(WS_SESSIONS.SESSION_ID));
					ws.setUserId(rs.getString(WS_SESSIONS.USER_ID));
					ws.setXmlAuth(rs.getString(WS_SESSIONS.XMLAUTH));
					ws.setUserStatus(rs.getString(WS_SESSIONS.USERSTATUS));
					ws.setSessionStart(rs.getTimestamp(WS_SESSIONS.SESSION_START));
					ws.setSessionEnd(rs.getTimestamp(WS_SESSIONS.SESSION_END));
					ws.setTicket(rs.getString(WS_SESSIONS.TICKET));
					ws.setLastError(rs.getString(WS_SESSIONS.LAST_ERROR));
					ws.setSessionStatus(rs.getString(WS_SESSIONS.SESSION_STATUS));
					ws.setComando(rs.getString(WS_SESSIONS.COMANDO));
					ws.setCollaborazione(rs.getInt(WS_SESSIONS.COLLABORAZIONE));				
				}
				//rs.close();
				//ps.close();				
			}catch(SQLException sqle){
				logger.error("errore: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_01);
			}
			finally{
				try{
					ps.close();
					rs.close();
				}
				catch(Exception e){}
				ps = null;
				rs = null;
			}
			return ws;
		}
		return null;
	}
	
	/************************************************************************************
	 * Il metodo si occupa dell'aggiornamento della fine sessione e status 
	 * della sessione  in base all'id utente 
	 * param wss : WsSessions
	 * return updateCloseUnvalidSession
	 * throws SimogWSException
	 */
	private boolean updateCloseUnvalidSession(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: updateCloseUnvalidSession("+wss.toString()+")");
		if(con!=null){
			PreparedStatement ps = null;
			try{
				ps = con.prepareStatement(UPDATE_CLOSE_UNVALID_SESSION);
				ps.setTimestamp(1, new SqlTools().getDBDate(con, logger));
				ps.setString(2, "E");
				ps.setString(3, wss.getUserId());
            ps.setString(4, "E");
				ps.executeUpdate();
				//ps.close();
				return true;
				
			}catch(SQLException sqle){
				logger.error("errore: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_02);
			}
			finally{
				try{
					ps.close();
				}
				catch(Exception e){}
				ps = null;
			}
		}throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_03);
	}
	/**********************************************************************************
	 * Inserisce una sessione controllando che non ce ne siano di gia' 
	 * attive o da chiudere
	 * @param wss : WsSessions
	 * @return boolean
	 * @throws SimogWSException
	 */
	public boolean insertBeginSession(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: insertBeginSession("+wss.toString()+")");
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			WsSessions wssfinded = this.selectFindSession(wss);
			if(wssfinded != null){
				if((wssfinded.getSessionEnd().compareTo(new SqlTools().getDBDate(con, logger))>0) 
						&& (wssfinded.getSessionStatus().equals("I"))){
					logger.debug(">>>>esiste gia' una sessione attiva: " + wssfinded.getTicket());
				}else{
					logger.debug(">>>>trovate sessioni da chiudere correttamente");
					b = this.updateCloseUnvalidSession(wssfinded);
				}
			}
			if((b && wssfinded!=null) || (!b && wssfinded==null)){
				if(wss.getRpntId()!=null) //TICKET ALM - 3.04.3
				   ps = con.prepareStatement(new SqlTools().returnIdOnInsert(logger, INSERT_BEGIN_SESSION_RPNT, "ID_SESSIONE"));
				else
				   ps = con.prepareStatement(new SqlTools().returnIdOnInsert(logger, INSERT_BEGIN_SESSION, "ID_SESSIONE"));
				ps.setString(1, wss.getUserId());
				ps.setTimestamp(2, wss.getSessionStart());
				ps.setTimestamp(3, wss.getSessionEnd());
				ps.setString(4, wss.getSessionStatus());
				ps.setString(5, wss.getComando());
				if(wss.getRpntId()!=null)//TICKET ALM - 3.04.3
					ps.setString(6, wss.getRpntId());
				ps.execute();
				rs = ps.getResultSet();
				if(rs.next()){
					b = true;
					wss.setSessionId(rs.getLong("ID_SESSIONE"));
					//this.wss = wss;
				}
			}
		}catch(SQLException sqle){
			logger.error("errore nell'inserimento di una nuova sessione: "+sqle.getMessage());
			sqle.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_04);
		}
		finally{
			try{
				if(rs != null) rs.close();
                if(ps != null) ps.close();
			}
			catch(Exception e){}
			ps = null;
			rs = null;
		}
		return b;
	}
	/****************************************************************************
	 * Aggiorna la sessione 
	 * @param wss : WsSessions
	 * @return boolean
	 * @throws SimogWSException
	 */
	public boolean updateSessionWithLoginSuccess(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: updateSessionWithLoginSuccess("+wss.toString()+")");
		if(con != null){
			PreparedStatement ps = null;
			try{
				ps = con.prepareStatement(UPDATE_SESSION_LOGIN);
				ps.setTimestamp(1, wss.getSessionEnd());
				ps.setString(2, wss.getSessionStatus());
				ps.setString(3, wss.getXmlAuth());
				ps.setString(4, wss.getUserStatus());
				ps.setString(5, wss.getTicket());
				ps.setLong(6, wss.getSessionId());
				ps.executeUpdate();
				//ps.close();
				return true;
				
			}catch(SQLException sqle){
				logger.error("errore valorizzando gli attributi di una sessione valida: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_05);
			}
			finally{
				try{
					ps.close();
				}
				catch(Exception e){}
				ps = null;
			}
		}
		logger.error("la connessione risulta nulla");
		throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_03);
	}
	
	/******************************************************************************
	 * Effettua l'aggiornamento della sessione
	 * @param wss : WsSessions
	 * @return boolean
	 * @throws SimogWSException
	 */
	public boolean updateSessionWithLoginFail(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: updateSessionWithLoginFail(wsswssions object)");
		if(con!=null){
			PreparedStatement ps = null;
			try{
				ps = con.prepareStatement(UPDATE_SESSION_AFTER_LOGIN_FAIL);		
				ps.setString(1, wss.getSessionStatus());
				ps.setTimestamp(2, wss.getSessionEnd());
				ps.setString(3, wss.getLastError());
				ps.setLong(4, wss.getSessionId());
				ps.executeUpdate();
				//ps.close();
				return true;
				
			}catch(SQLException sqle){
				logger.error("errore valorizzando gli attributi di una sessione valida: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_06);
			}
			finally{
				try{
					ps.close();
				}
				catch(Exception e){}
				ps = null;
			}
		}
		logger.error("la connessione risulta nulla");
		throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_07);
	}

	//------------------------------------------------------//
	//--------------  	GENERA CIG		--------------------//
	//------------------------------------------------------//
	
	
	/**************************************************************************************
	 * Effettua l'aggiornamento di status, collaboraione e comando della sessione
	 * param wss : WsSessions
	 * return booelan
	 * throws SimogWSException
	 */
	private boolean updateSessionStatusToProcessing(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: updateSessionStatusToProcessing("+wss.toString()+")");
		if(con != null){
			PreparedStatement ps = null;
			try{
				logger.debug("collaborazione"+wss.getCollaborazione()+",comando"+wss.getComando()+", sessionid"+wss.getSessionId());
				ps = con.prepareStatement(UPDATE_SESSION_STATUS_TO_PROCESSING);
				ps.setString(1, "P");
				ps.setInt(2, wss.getCollaborazione());
				ps.setString(3, wss.getComando());
				ps.setLong(4, wss.getSessionId());
				ps.executeUpdate();
				//ps.close();
				return true;
				
			}catch(SQLException sqle){
				logger.error("errore: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_08);
			}
			finally{
				try{
					ps.close();
				}
				catch(Exception e){}
				ps = null;
			}
		}
		logger.error("la connessione risulta nulla");
		throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_09);
	}
	/*****************************************************************************
	 * Aggiorna la sessione dopo un'operazione
	 * @param wss : WsSessions
	 * @return boolean
	 * @throws SimogWSException
	 */
	public boolean updateSessionAfterOp(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: updateSessionAfterOp("+wss.toString()+")");
		if(con != null){
			PreparedStatement ps = null;
			try{	
				// per evitare la sovrascrittura dell'ultimo errore..
				if(wss.getLastError()==null || wss.getLastError().equals("")){
					logger.debug("variante della query senza errore");
					ps = con.prepareStatement(UPDATE_SESSION_AFTER_OP_SENZA_ER);					
					ps.setString(1, "I");
					ps.setTimestamp(2, wss.getSessionEnd());
					ps.setLong(3, wss.getSessionId());
				}else{
					logger.debug("variante della query CON errore");
					ps = con.prepareStatement(UPDATE_SESSION_AFTER_OP);
					ps.setString(1, "I");
					
					if(wss.getLastError().length()>1000)
					   ps.setString(2, wss.getLastError().substring(0, 1000));
					else
					   ps.setString(2, wss.getLastError());
					
					ps.setTimestamp(3, wss.getSessionEnd());
					ps.setLong(4, wss.getSessionId());
				}				
				ps.executeUpdate();
				//ps.close();
				return true;
			}catch(SQLException sqle){
				logger.error("errore updating: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_10);
			}
			finally{
				try{
					ps.close();
				}
				catch(Exception e){}
				ps = null;
			}
		}
		logger.error("la connessione risulta nulla");
		throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_11);
	}

	//------------------------------------------------------//
	//--------------  	CLOSE SESSION	--------------------//
	//------------------------------------------------------//
	
	/**************************************************************************************
	 * Aggiorna lo stato della sessione nel db in seguito al comando di chiusura 
	 * @param wss : WsSessions
	 * @return boolean
	 * @throws SimogWSException
	 */
	public boolean updateSessionUnvalidateFromTicket(WsSessions wss)throws SimogWSException{
		logger.debug("updateSessionUnvalidateFromTicket("+wss.toString()+")");		
		if(con != null){
			PreparedStatement ps = null;
			try{
				ps = con.prepareStatement(UPDATE_SESSION_UNVALIDATE_FROM_TICKET);
				ps.setTimestamp(1, new SqlTools().getDBDate(con, logger));
				ps.setString(2, "E");
				ps.setString(3, wss.getTicket());
				ps.setString(4, "E");			
				ps.executeUpdate();
				//ps.close();
				return true;
				
			}catch(SQLException sqle){
				logger.error("errore: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_12);
			}
			finally{
				try{
					ps.close();
				}
				catch(Exception e){}
				ps = null;
			}
		}
		logger.error("la connessione risulta nulla");
		throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_13);
	}
	//------------------------------------------------------//
	//-------------- ISERISCI GARA-LOTTO -------------------//
	//------------------------------------------------------//
	
	//------------------------------------------------------//
	//-------------- MODIFICA GARA-LOTTO -------------------//
	//------------------------------------------------------//
	
	//------------------------------------------------------//
	//--------------  		SHARED		--------------------//
	//------------------------------------------------------//
	
	
	/*******************************************************************************
	 * ricerca la sessione valida
	 * @param wss : WsSessions
	 * @return WsSessions
	 * @throws SimogWSException
	 */
	public WsSessions selectFindValidSession(WsSessions wss)throws SimogWSException{
		logger.debug("eseguendo: selectFindValidSession("+wss.toString()+")");
		if(con != null){
			WsSessions ws = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				ps = con.prepareStatement(SELECT_FIND_VALID_SESSION);
				ps.setString(1, wss.getTicket());
				ps.setTimestamp(2, new SqlTools().getDBDate(con, logger));
				ps.setString(3, "I");
				rs = ps.executeQuery();
				if(rs.next()){
					ws = new WsSessions();
					//sovrascivo informazioni importanti..
					ws.setSessionId(rs.getLong(WS_SESSIONS.SESSION_ID));
					ws.setUserId(rs.getString(WS_SESSIONS.USER_ID));
					ws.setXmlAuth(rs.getString(WS_SESSIONS.XMLAUTH));
					ws.setUserStatus(rs.getString(WS_SESSIONS.USERSTATUS));
					ws.setSessionStart(rs.getTimestamp(WS_SESSIONS.SESSION_START));
					ws.setSessionEnd(rs.getTimestamp(WS_SESSIONS.SESSION_END));
					ws.setTicket(rs.getString(WS_SESSIONS.TICKET));
					ws.setLastError(rs.getString(WS_SESSIONS.LAST_ERROR));
					ws.setSessionStatus(rs.getString(WS_SESSIONS.SESSION_STATUS));
					ws.setComando(rs.getString(WS_SESSIONS.COMANDO));
					ws.setCollaborazione(rs.getInt(WS_SESSIONS.COLLABORAZIONE));
				}
			}catch(SQLException sqle){
				logger.error("errore: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_14);
			}
			finally{
				try{
					ps.close();
					rs.close();
				}
				catch(Exception e){}
				ps = null;
				rs = null;
			}
			if(ws != null){
				wss.setUserId(ws.getUserId());
				wss.setSessionId(ws.getSessionId());
				wss.setXmlAuth(ws.getXmlAuth());
				this.updateSessionStatusToProcessing(wss);
				return wss;
			}else{
				logger.error("non e' stata trovata alcuna sessione valida");
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_15);
			}
		}
		logger.error("la connessione risulta nulla");
		throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_16);
	}

	//TICKET ALM #4508
	public boolean checkKey(String ticket)  throws SimogWSException {
		logger.debug("eseguendo: checkKey() ");
	
		boolean res = false;
		if(con != null){
			WsSessions ws = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				ps = con.prepareStatement(SELECT_KEY_CONSULTA_GARA);
				ps.setString(1, ticket);
				ps.setTimestamp(2, new SqlTools().getDBDate(con, logger));
				rs = ps.executeQuery();
				if(rs.next()){
				   res = true;
				}
			}catch(SQLException sqle){
				logger.error("errore: "+sqle.getMessage());
				sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_14);
			}
			finally{
				try{
					ps.close();
					rs.close();
				}
				catch(Exception e){}
				ps = null;
				rs = null;
			}
			
			return res;
		} else {
		   logger.error("la connessione risulta nulla");
		    throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_16);
		}

	}
	//FINE TICKET ALM #4508
	
	//------------------------------------------------------//
	//--------------  		TOOLS		--------------------//
	//------------------------------------------------------//	
	
	/*************************************************************
	 * ottiene il ConnectionWSManager 
	 * @return  ConnectionWSManager
	 * @throws SimogWSException
	 */
//	public ConnectionWSManager getConnectionManager()throws SimogWSException{
//		logger.debug("eseguendo: getConnectionManager()");
//		if(con!=null){
//			return this.cwsm;
//		}
//		logger.error("la connessione wrappata dal connection manager contenuto nel gestore delle sessioni risulta nulla");
//		throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_SQL_17);
//	}	
}
