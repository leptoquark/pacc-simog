//package it.avlp.simog.ws.beans;
//
//import it.avlp.simog.beans.Collaborazione;
//import it.avlp.simog.beans.Collaborazioni;
//import it.avlp.simog.beans.CollaborazioniRssa;
//import it.avlp.simog.beans.ws.WsSessions;
//import it.avlp.simog.common.sql.ConnectionWSManager;
//import it.avlp.simog.error.SimogWSException;
//import it.avlp.simog.ws.commons.CigManager;
//import it.avlp.simog.ws.commons.CollaborazioniManager;
//import it.avlp.simog.ws.commons.ConfigurationManager;
//import it.avlp.simog.ws.commons.TicketManager;
//import it.avlp.simog.ws.commons.sql.WSSessionManager;
//import it.avlp.simog.ws.commons.sql.util.SqlTools;
//import it.avlp.simog.ws.xmlbeans.CheckLoginType;
//
//import java.awt.color.CMMException;
//import java.sql.Connection;
//import java.text.SimpleDateFormat;
//
//import org.apache.log4j.Logger;
//
//public class ControllaWsSession {
	
//	public static enum Comando {
//
//		CONSULTA_GARA(0),
//		MODIFICA_LOTTO(2),
//		PERFEZIONA_LOTTO(3),
//		GENERA_CIG(4),
//		INSERISCI_LOTTO(5),
//		INSERISCI_GARA(6),
//		MODIFICA_GARA(7),
//		PERFEZIONA_GARA(8),
//		CANCELLA_GARA(9),
//		CANCELLA_LOTTO(10),
//		CONSULTA_CIG(11),
//		MASSLOADER_WS(12);
//		
//		private int comando;
//		
//		public int getComando() {
//			return comando;
//		}
//
//		private Comando(int comando){
//			this.comando = comando;
//		}
//	}

//	static Logger logger = Logger.getLogger(ControllaWsSession.class);
//	SimpleDateFormat dateFormat;
//
//	public ControllaWsSession() {
//		dateFormat = new SimpleDateFormat("yyyyMMdd");
//	}
//
//	public Collaborazione Controlla(String ticket, String index, int comando, String codStazioneAppaltante) throws Exception {
//		Collaborazione coll = null;
//		Collaborazioni colls = null;
//		ConnectionWSManager cwsm = null;
//		Connection con = null;
//		//CheckLoginType clt = null;
//		try {
//			logger.info("-----------\tbegin  \t---------------");
//			coll = new Collaborazione();
//			colls = new Collaborazioni();
//			WsSessions wss = new WsSessions();
//			cwsm = new ConnectionWSManager(logger, ConfigurationManager.getInstance().getSimogProperties());
//			WSSessionManager wsm = new WSSessionManager(logger, cwsm);
//			con = cwsm.getConnection();
//			TicketManager tm = new TicketManager();
//			SqlTools sqlt = new SqlTools();
//			CigManager cm = new CigManager();
//			cwsm.setAutocommit(false);
//			wss.setTicket(ticket);
//			switch (comando) {
//			case TicketManager.CONSULTA_GARA: // '\0'
//				wss.setComando("CONSULTA_GARA");
//				// fall through
//
//			case TicketManager.MODIFICA_LOTTO: // '\002'
//				wss.setComando("MODIFICA_LOTTO");
//				// fall through
//
//			case TicketManager.PERFEZIONA_LOTTO: // '\003'
//				wss.setComando("PERFEZIONA_LOTTO");
//				// fall through
//
//			case TicketManager.GENERA_CIG: // '\004'
//				wss.setComando("GENERA_CIG");
//				// fall through
//
//			case TicketManager.INSERISCI_LOTTO: // '\005'
//				wss.setComando("INSERISCI_LOTTO");
//				// fall through
//
//			case TicketManager.INSERISCI_GARA: // '\006'
//				wss.setComando("INSERISCI_GARA");
//				// fall through
//
//			case TicketManager.MODIFICA_GARA: // '\007'
//				wss.setComando("MODIFICA_GARA");
//				// fall through
//
//			case TicketManager.PERFEZIONA_GARA: // '\b'
//				wss.setComando("PERFEZIONA_GARA");
//				// fall through
//
//			case TicketManager.CANCELLA_GARA: // '\t'
//				wss.setComando("CANCELLA_GARA");
//				// fall through
//
//			case TicketManager.CANCELLA_LOTTO: // '\n'
//				wss.setComando("CANCELLA_LOTTO");
//				// fall through
//
//			case TicketManager.CONSULTA_CIG: // '\013'
//				wss.setComando("CONSULTA_CIG");
//				// fall through
//
//			case TicketManager.MASSLOADER_WS: // '\f'
//				wss.setComando("MASSLOADER_WS");
//				// fall through
//
////			case 13: // '\r'
////				wss.setComando("SMART_CIG");
////				// fall through
//
//			//case 1: // '\001'
//			default:
//				wss.setCollaborazione(Integer.parseInt(index));
//				break;
//			}
//			wss = wsm.selectFindValidSession(wss);
//			cwsm.commit();
//			if (wss != null) {
//				logger.info(">>>>esiste una sessione associata al ticket");
//				try {
//					//clt = wss.getXmlAuth();
//					
//					tm.validateRequestedActionByProfile(wss, comando);
//					
//                    CollaborazioniRssa collsRssa = null;
//                    if(!tm.isOperaComeOsservatorio()){      
//                        coll = tm.getCollaborazione();
//                        colls = tm.getCollaborazioni();
//                        collsRssa = new CollaborazioniRssa(colls,coll);
//                    }
//                    
//					if (tm.isValido()) {
//						logger.info(">>>>utente abilitato al comando richiesto");
//						cwsm.setIsolation("t_serialize");
//						logger.info(">>>>consulta cig e set della response con collaborazione");
////						if (!index.equals("-1")) {
////							coll = (new CollaborazioniManager()).getCollaborazioni(clt).getPerIndice(index);
////							logger.info((new StringBuilder("coll:")).append(coll).toString());
////						} 
////						else {
////							coll.setAdminOr(clt.getSoggetto().getAdminOr());
////						}
//						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
//						cwsm.setIsolation("t_read_committed");
//						if (wsm.updateSessionAfterOp(wss)) {
//							cwsm.commit();
//							logger.info(">>>>aggiornamento dello stato della sessione riuscito");
//						}
//					} else {
//						logger.info("fallimento della validazione del ticket associazione comando - profilo non autor" + "izzata");
//						String messaggioErrore = (new StringBuilder("collaborazione [")).append(wss.getCollaborazione()).append("] non abilitata al comando [").append(wss.getComando()).append("] richiesto").toString();
//						wss.setLastError(messaggioErrore);
//						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
//						if (wsm.updateSessionAfterOp(wss)) {
//							logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
//						}
//					}
//				} catch (SimogWSException swe) {
//					logger.error("indice collaborazione non valido");
//					String messaggioErrore = swe.getMyMessage();
//					wss.setLastError((new StringBuilder("collaborazione [")).append(wss.getCollaborazione()).append("] non esiste").toString());
//					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
//					if (wsm.updateSessionAfterOp(wss)) {
//						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
//						cwsm.commit();
//					}
//					throw new SimogWSException(swe.getMessage());
//				}
//			}
//		} catch (SimogWSException swe) {
//			if (cwsm != null) {
//				cwsm.rollback();
//			}
//			logger.error((new StringBuilder("SimogWSException catched: ")).append(swe.getMyMessage()).toString());
//		}
//		if (cwsm != null) {
//			cwsm.setAutocommit(true);
//			cwsm.closeConnection();
//		}
//		logger.info("----------\t\tEND\t\t----------");
//		return coll;
//	}
//
//}