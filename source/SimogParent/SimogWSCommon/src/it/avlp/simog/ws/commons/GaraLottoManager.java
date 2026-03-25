package it.avlp.simog.ws.commons;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.DateValidator;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import ausa.servizi.avcp.it.wsdl.RicercaSAWS_Client;
import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avcp.simog.managers.bdncp.BdncpManager;
import it.avcp.simog.managers.luogo.IstatNutsManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.CUPLOTTO;
import it.avlp.simog.beans.CodiciCup;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeBean;
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.RichiestaCUP;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.CondizioniAction;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletGara;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.error.SimogWsXmlException;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.massload.xmlbeans.CPVSecondariaType;
import it.avlp.simog.massload.xmlbeans.CondizioneLtType;
import it.avlp.simog.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.DerogaQualificazioneSA;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.GaraWSDocument;
import it.avlp.simog.massload.xmlbeans.LottoType;
import it.avlp.simog.massload.xmlbeans.LottoWSDocument;
import it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument;
import it.avlp.simog.massload.xmlbeans.ReqDocType;
import it.avlp.simog.massload.xmlbeans.ReqGaraType;
import it.avlp.simog.massload.xmlbeans.RequisitiWSDocument;
import it.avlp.simog.massload.xmlbeans.RequisitiWSDocument.RequisitiWS;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.LottoValidator;
import it.avlp.simog.validatore.RequisitiGLValidator;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;
import it.mef.serviziCUP.ElaborazioniCUPClient;
import it.avlp.simog.common.servlet.PSReq;
public class GaraLottoManager {

	private Logger logger;
	private Connection con;
	private long id_gara;
	private String error;
	private boolean thereIsAnError;
	private CUPLOTTO cuplotto = null;

	// costanti per la conversione
	public String TIPO_GARA = "GARA";
	public String TIPO_LOTTO = "LOTTO";
	public String TIPO_GARA_LOTTO = "GARA_LOTTO";

	// PP BANDI
	public String TIPO_PUBBLICAZIONE = "PUBB";

	// is3025_REQUISITIActive
	public String TIPO_REQUISITI = "REQ";

	public GaraLottoManager(Connection con, Logger logger) {
		this.logger = logger;
		this.con = con;
	}
	/*----------------------------------------------*/
	/*------------- OPERAZIONI DB ------------------*/
	/*----------------------------------------------*/
//	/**
//	 * metodo per l'inserimento simultaneo di una gara e un lotto
//	 * @deprecated
//	 * @param DatiGaraType: datiGara, xmlbean
//	 * @param String: User_id
//	 * @param Collaborazione: coll
//	 * @param adminOr: codice regionale dell'osservatorio
//	 * @return CIGBean: bean contente il cig
//	 * @throws SimogWSException
//	 */
//	public CIGBean inserisciGaraLotto(DatiGaraType datiGara,String User_id,Collaborazione coll, String adminOr)throws SimogWSException{
//		CIGBean cigBean = null;
//		Gara gara = this.converti(datiGara.getGara(), User_id, coll, false, null);		
//		Lotto lotto  = this.converti(datiGara.getLotto());
//		//logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, gara));
//		//logger.debug(ObjectIntrospector.propertiesInfo(Lotto.class, lotto));
//		//se non ci sono errore durante la conversione che fa anche la validazione esegui l'inserimento
//		if(!this.thereIsAnError){
//		if(con != null && gara != null && lotto != null){
//			GaraManager garaManager = new GaraManager(con,logger);
//			LottoManager lottoManager = new LottoManager(con,logger);
//			cigBean = new CIGBean(CIGBean.APPL_WS, 
//										gara.getCF_UTENTE(),
//										gara.getCF_AMMINISTRAZIONE(),
//										gara.getID_STAZIONE_APPALTANTE());
//			try{
//				// aggiunto campo id_osservatorio
//				gara.setID_OSSERVATORIO(coll.getIdOsservatorio());
//				;
//				/* viene valorizzato durante l'operazione anche il campo id del bean */
//				garaManager.creaNuovaGara(gara);	
//				setId_gara(gara.getId_Gara());
//				lotto.setId_Gara(gara.getId_Gara());
//				/* durante questa operazione vengono valroizzati anche i campi del cig */
//				lottoManager.creaNuovoLotto(gara, lotto, CIGBean.APPL_WS, adminOr);
//				
//				/* categorie scorporabili */
//				if(lotto.getCategorieScorporabili() != null && !lotto.getCategorieScorporabili().isEmpty()){
//					this.insertCategorieScorporabili(lotto.getCategorieScorporabili(), lotto.getId_Lotto(), lottoManager);
//				}
//				
//				LogManager logManager = new LogManager(con, logger);
//				logManager.log(
//						PageHelper.getDBDateFromTS(new AccessiDB(con,logger).getNow()),
//						gara.getID_STAZIONE_APPALTANTE(),
//						User_id,
//						lotto.getCIG() + lotto.getCIG_kkk(),
//						LogManager.INS_GARA +", "+ LogManager.INS_LOTTO,
//						gara.getCF_AMMINISTRAZIONE(),
//						Long.toString( lotto.getId_Lotto() ),
//						String.valueOf(gara.getId_Gara()));
//
//				/* tutto cio per il bean di ritorno */
//				cigBean.setCig(lotto.getCIG());
//				cigBean.setCigCicle(lotto.getCIG_cicle());
//				cigBean.setCigKKK(lotto.getCIG_kkk());
//				
//			}catch(SQLException sqle){
//				//sqle.printStackTrace();
//				logger.error("eccezione occorsa provando a inserire gara e lotto "+sqle.getMessage());
//				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_SQL_01);
//			}
//		}else{
//			logger.error("uno degli oggetti necessari all'inserimento della gara e del lotto risulta nullo");
//			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_02);
//		}
//			return cigBean;
//		}else{return null;}
//	}

	/**
	 * Centralizzazione del controllo che autorizza se si opera come osservatorio la
	 * nullita della collaborazione, in tal caso alcuni dati verrano recuperati
	 * dall'xml in quanto gli osservatori sono responsabili per i dati immessi.
	 * 
	 * @param collaborazioniRssa
	 * @param isOperaComeOsservatorio
	 * @param gara
	 * @return
	 */
	private boolean checkCollaborazioneOrOperaComeOsservatorio(CollaborazioniRssa collaborazioniRssa,
			boolean isOperaComeOsservatorio, Gara gara) {

		return isOperaComeOsservatorio || (collaborazioniRssa != null && collaborazioniRssa
				.checkCollaborazione(gara.getCF_AMMINISTRAZIONE(), gara.getID_STAZIONE_APPALTANTE()));

	}

	/**
	 * Centralizzazione del controllo che autorizza l'operazione si si opera come
	 * osservatorio oppure controlla la coerenza dei dati collaborazione / dati gara
	 * 
	 * @param collaborazioneSelezionata
	 * @param isOperaComeOsservatorio
	 * @param gara
	 * @return
	 */
	private boolean checkCollaborazioneOrOperaComeOsservatorio(Collaborazione collaborazioneSelezionata,
			boolean isOperaComeOsservatorio, Gara gara) {

		return isOperaComeOsservatorio || (collaborazioneSelezionata != null
				&& gara.getCF_AMMINISTRAZIONE().equals(collaborazioneSelezionata.getAzienda_codiceFiscale())
				&& gara.getID_STAZIONE_APPALTANTE().equals(collaborazioneSelezionata.getUfficio_id()));
	}

	/**
	 * Metodo per l'inserimento di una gara
	 * 
	 * @param gara_xml
	 * @param User_id
	 * @param collaborazioniRssa
	 * @param isOperaComeOsservatorio
	 * @param adminOr
	 * @return
	 * @throws SimogWSException
	 */
	public long inserisciGara(GaraType gara_xml, String User_id, CollaborazioniRssa collaborazioniRssa,
			boolean isOperaComeOsservatorio, String adminOr) throws SimogWSException {

		Collaborazione coll = null;
		if (!isOperaComeOsservatorio)
			coll = collaborazioniRssa.getCollaborazione();

		Gara gara = this.converti(gara_xml, User_id, coll, isOperaComeOsservatorio, adminOr);
		// logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, gara));
		// logger.debug(ObjectIntrospector.propertiesInfo(Lotto.class, lotto));
		// se non ci sono errore durante la conversione che fa anche la validazione
		// esegui l'inserimento
		if (!this.thereIsAnError) {
			if (con != null && gara != null) {
				GaraManager garaManager = new GaraManager(con, logger);
				try {
					// apertura osservatorio
					if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

						/* viene valorizzato durante l'operazione anche il campo id del bean */
						garaManager.creaNuovaGara(gara);

						// TICKET TICKET ALM #659 - 3.04.4
						if (gara.getFlagSAAgente() != null && !"".equals(gara.getFlagSAAgente())
								&& Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente())) {
							BdncpManager bm = new BdncpManager(con, logger);
							gara.setDEN_AMM_AGENTE(bm.loadDenSA(gara.getCF_AMM_AGENTE()));
							garaManager.insertFunzioniDelegateGara(gara);
						}
						// FINE TICKET ALM #659 - 3.04.4

						LogManager logManager = new LogManager(con, logger);
						// TICKET ALM #692
						logManager.log(
								// PageHelper.getDBDateFromTS(new AccessiDB(con,logger).getNow()),
								gara.getData_creazione() != null ? gara.getData_creazione()
										: PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
								gara.getID_STAZIONE_APPALTANTE(), User_id, "", LogManager.INS_GARA,
								gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));

					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_25);
					}

				} catch (SQLException sqle) {
					sqle.printStackTrace();
					logger.error("eccezione occorsa provando a inserire gara " + sqle.getMessage());
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_SQL_01);
				}
			} else {
				logger.error("uno degli oggetti necessari all'inserimento della gara risulta nullo");
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_03);
			}
			return gara.getId_Gara();
		} else {
			return 0;
		}
	}

	/**
	 * metodo per l'inserimento di un lotto da ws
	 * 
	 * @param dati                    LottoWSDocument: bean xml caricato tramite la
	 *                                stringa xml in ingresso
	 * @param User_id
	 * @param collaborazioniRssa
	 * @param id_gara
	 * @param adminOr
	 * @param isOperaComeOsservatorio
	 * @return
	 * @throws SimogWSException
	 */
	public CIGBean inserisciLotto(LottoWSDocument dati, String User_id, CollaborazioniRssa collaborazioniRssa,
			String id_gara, String adminOr, boolean isOperaComeOsservatorio) throws SimogWSException {
		logger.debug(
				"eseguendo:  CIGBean inserisciLotto(LottoWSDocument dati,String User_id,Collaborazione coll,String id_gara)throws SimogWSException");
		LottoType lottoXml = dati.getLottoWS().getLotto();
		CIGBean cigBean = new CIGBean();
		// P.C. controllo dell'id_gara
		if (id_gara == null || "".equals(id_gara) || !PageHelper.isNumeric(id_gara)) {
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
		}
		LottoManager lottoManager = new LottoManager(con, logger);
		GaraManager garaManager = new GaraManager(con, logger);
		try {
			Gara gara = garaManager.getGara(Long.parseLong(id_gara));
			if (gara == null) {
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
			}

			this.id_gara = Long.valueOf(id_gara);
			Lotto lotto = this.converti(lottoXml);
			lotto.setId_Gara(Long.parseLong(id_gara));

			// logger.debug(ObjectIntrospector.propertiesInfo(Lotto.class, lotto));
			if (!this.thereIsAnError) {
				if (con != null) { // && lotto != null

					Collaborazione collaborazioneSelezionata = null;
					if (!isOperaComeOsservatorio)
						collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

					// end
					if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
						if (gara != null) {
							cigBean = new CIGBean(CIGBean.APPL_WS, User_id, gara.getCF_AMMINISTRAZIONE(),
									gara.getID_STAZIONE_APPALTANTE());
							// confronta gara->collaborazioni(must be equals)

							// apertura osservatorio
							if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio,
									gara)) {

								// check avcpass
//		                  if(SimogFlags.is3028_RFWEBGL07Active()){
//		                     //List<Lotto> listaLotti = new ArrayList<Lotto>();
//		                     //listaLotti.add(lotto);		                     
//		                     if(isAVCPass(gara, null, AVCPassFunzioneEnum.WS_LOTTO_CREATE.getCodice())){
//		                        throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
//		                     }
//		                  }

								// X-XX: VL - nuova normativa, lotto inseribile solamente se lo stato gara e'
								// 'confermato'
								if (gara.getID_STATO_GARA() == StatiScheda.CONFERMATO
										// PP 304 anche se in definizione posso aggiungere lotti
										|| gara.getID_STATO_GARA() == StatiScheda.IN_DEFINIZIONE) {
									// solamente se l'importo lotto risulta inferiore all'importo complessivo della
									// gara
								} else {
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_11);
								}

								// MAC #10525 (3.04.3.1)
								if (gara.getDATA_PERFEZIONAMENTO_BANDO() != null
										&& !"".equals(gara.getDATA_PERFEZIONAMENTO_BANDO())) {
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_41);
								}

								// invocare il nuovo servizio calcolo contributo
// PP 21/07/2011 il contributo si calcola al perfezionamento
//									GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient (ConfigurationManager.getInstance().getSimogProperties().getContributoUrl(), con, logger);
//						    		ParametriContributo params = new ParametriContributo(gara, lotto, PageHelper.getIncreasedDate(0));
//						    		lotto.setImporto_Impresa(gcwbc.getContributoOE(params));

								// TICKET ALM #3832-02.3
								// Se la gara e' stata creata successivamente la data di attivazione, il valore
								// delle somme urgenze deve essere sovrascritto dal flag della gara
								if (SimogFlags.is3042Active() && gara.getData_creazione()
										.compareTo(SimogProperties.getInstance().getDataAttivazione3042()) >= 0) {
									lotto.setSomma_Urgenza(
											gara.getURGENZA_DL133() != null ? gara.getURGENZA_DL133().charAt(0) : 'N');
								}

								// TICKET ALM - 3.04.7
								if (lotto.getLUOGO_ISTAT() != null && !"".equals(lotto.getLUOGO_ISTAT())) {
									IstatNutsManager inm = new IstatNutsManager(con, logger);
									lotto.setLUOGO_NUTS(inm.getNutsFromIstat(lotto.getLUOGO_ISTAT()));
								}

								lottoManager.creaNuovoLotto(gara, lotto, CIGBean.APPL_WS, adminOr);

								// TICKET ALM #4219 - 3.04.4
								// Elimina duplicati
								lotto.setElencoCpvSecondarie(this.verificaDuplicati(lotto.getElencoCpvSecondarie()));
								for (CpvLotto cpvSec : lotto.getElencoCpvSecondarie())
									lottoManager.insertCpvLotto(lotto.getId_Lotto(), cpvSec);
								// FINE TICKET ALM #4219 - 3.04.4

								// TICKET ALM #4223-#4224 - 3.04.4
								boolean autoNoDpcmSet = false;
								boolean autoNoSaClass = false;
								if (lotto.getFlagNoAdesione() != null && !"".equals(lotto.getFlagNoAdesione())
										&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagNoAdesione())) {
									IniziativaManager im = new IniziativaManager(con, logger);
									im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()),
											lotto.getId_Lotto(), Costanti.INIZIATIVE_NON_IDONEE);
									autoNoDpcmSet = true;
								}
								if (lotto.getFlagSANonClass() != null && !"".equals(lotto.getFlagSANonClass())
										&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagSANonClass())) {
									IniziativaManager im = new IniziativaManager(con, logger);
									im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()),
											lotto.getId_Lotto(), Costanti.SA_NON_CLASSIFICATA);
									autoNoSaClass = true;
								}
								// FINE TICKET ALM #4223-#4224 - 3.04.4

								if (SimogFlags.is3031_RFWEBGL02Active()) {
									CupLottoAggManager cupMan = new CupLottoAggManager(con, logger);
									cupMan.settingIdLotto(lotto);
									cupMan.addCup(lotto.getElencoCup(), false);
								}

								if (SimogFlags.is3031_RFWEBGL00Active()) {
									TipoAppaltoManager talMan = new TipoAppaltoManager(con, logger);
									talMan.settingIdLotto(lotto);
									talMan.aggiornaTipoAppaltoLotto(lotto, false);
								}

								// TICKET ALM #3835
								if (SimogFlags.is3042Active()) {
									CondizioniAction ca = new CondizioniAction(con, logger);
									ca.saveCondizioniLotto(lotto.getCondizioni(), lotto.getId_Lotto());

								}
								// FINE TICKET ALM #3835

								/* categorie scorporabili */
								if (lotto.getCategorieScorporabili() != null
										&& !lotto.getCategorieScorporabili().isEmpty()) {
									this.insertCategorieScorporabili(lotto.getCategorieScorporabili(),
											lotto.getId_Lotto(), lottoManager);
								}

								
								
								
								// #31047 PARITA DI GENERE
								MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(con, logger);
								MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(con, logger);

								motivoDerogaManager.createMotivoDerogaLottoRelation(lotto.getId_Lotto(), lotto.getElencoMotivoDeroga());
								misuraPremialeManager.createMisuraPremialeLottoRelation(lotto.getId_Lotto(),
										lotto.getElencoMisurePremiali());

								
								
								
								
								
								
								
								
								LogManager logManager = new LogManager(con, logger);
								logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
										gara.getID_STAZIONE_APPALTANTE(), User_id, lotto.getCIG() + lotto.getCIG_kkk(),
										LogManager.INS_LOTTO, gara.getCF_AMMINISTRAZIONE(),
										Long.toString(lotto.getId_Lotto()), String.valueOf(lotto.getId_Gara()));

								cigBean = new CIGBean(CIGBean.APPL_WS, User_id, gara.getCF_AMMINISTRAZIONE(),
										gara.getID_STAZIONE_APPALTANTE());

								cigBean.setCig(lotto.getCIG());
								cigBean.setCigCicle(lotto.getCIG_cicle());
								cigBean.setCigKKK(lotto.getCIG_kkk());
								return cigBean;

							} else {
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
							}
						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
						}
					} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
								// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
								// NON
								// siano previste evoluzioni future in questo senso si puo levare

// PP						lm.creaNuovoLotto(gt, lotto, User_id, adminOr);
//						/* categorie scorporabili */
//						if(lotto.getCategorieScorporabili() != null && !lotto.getCategorieScorporabili().isEmpty()){
//							this.insertCategorieScorporabili(lotto.getCategorieScorporabili(), lotto.getId_Lotto(), lm);
//						}
//						cig.setCig(lotto.getCIG());
//						cig.setCigCicle(lotto.getCIG_cicle());
//						cig.setCigKKK(lotto.getCIG_kkk());
//						return cig;
					}
				}
				return null;
			} else {
				return null;
			}
		} catch (SimogWSException swe) {
			throw (swe);
		} catch (SQLException sqle) {
			logger.error("[SQL] - errore nell'inserimento del lotto");
			sqle.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_26 + sqle.getMessage());
		} catch (Exception e) {
			logger.error("[APP] - errore nell'inserimento del lotto");
			 e.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_26 + e.getMessage());
		}
	}

	private List<CpvLotto> verificaDuplicati(List<CpvLotto> listaCpvOriginale) {
		List<CpvLotto> listaCpvNoDuplicati = new ArrayList<CpvLotto>();

		// Cicla tutte le cpv
		for (CpvLotto cpvOrig : listaCpvOriginale) {
			boolean duplicate = false;

			// Cicla le cpv gia' inserite
			for (CpvLotto cpv : listaCpvNoDuplicati) {
				// Se gia' esiste una cpv con lo stesso codice, non inserirla nella nuova lista
				if (cpvOrig.getIdCpv().equals(cpv.getIdCpv())) {
					duplicate = true;
					break;
				}
			}

			if (!duplicate)
				listaCpvNoDuplicati.add(cpvOrig);

		}

		return listaCpvNoDuplicati;
	}

	/**
	 * Metodo per l'aggiornamento di una gara gia' esistente
	 * 
	 * @param User_id
	 * @param indexCollaboraxione
	 * @param collaborazioniRssa
	 * @param id_gara
	 * @param garaXml
	 * @param isOperaComeOsservatorio
	 * @param adminOr
	 * @return
	 * @throws SimogWSException
	 */
	public boolean modificaGara(String User_id, String indexCollaboraxione, CollaborazioniRssa collaborazioniRssa,
			String id_gara, GaraType garaXml, boolean isOperaComeOsservatorio, String adminOr) throws SimogWSException {
		if (con != null) {
			GaraManager garaManager = new GaraManager(con, logger);
			try {

				Gara garaLocale = (Gara) this.converti(garaXml, User_id,
						collaborazioniRssa != null ? collaborazioniRssa.getCollaborazione() : null,
						isOperaComeOsservatorio, adminOr);

				// carico la gara prima
				Gara gara = garaManager.getGara(Long.parseLong(id_gara));
				// caso in cui id_gara non sia valido (corretto)
				if (gara != null) {
					// apertura osservatorio
					if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

						Collaborazione collaborazioneSelezionata = null;
						if (!isOperaComeOsservatorio)
							collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

						TableBean tableBeanGara = null;
						if (!isOperaComeOsservatorio) {
							// costruzione sa abilitate per ricerca gara
							Hashtable<String, String> listaCodiciRssa = new Hashtable<String, String>();
							ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
							for (String stringa : elenco) {
								listaCodiciRssa.put(stringa, "");
							}
							tableBeanGara = garaManager.getDettagliGaraByIdGaraRSSA(id_gara, listaCodiciRssa);
						} else {
							tableBeanGara = garaManager.getDettagliGaraByIdGara(id_gara);
						}
						boolean modificabile = "0".equals(tableBeanGara.getNulledField(PSBD.HASSCHEDE, 0));
						// PP && "0".equals(tableBeanGara.getNulledField(LOTTO.ID_LOTTO, 0));

						String dataScadPagStr = tableBeanGara.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI, 0);
						boolean modificabilePerScadenzaPag = false;
						if (dataScadPagStr != null && !"".equals(dataScadPagStr)) {
							Date today = PageHelper.getCurrentUtilDate();
							Date dataScadPag = new SimpleDateFormat("yyyyMMdd").parse(dataScadPagStr);
							modificabilePerScadenzaPag = dataScadPag.after(today);
						}

						if ((gara.getID_STATO_GARA() == StatiScheda.IN_DEFINIZIONE
								|| (gara.getID_STATO_GARA() == StatiScheda.CONFERMATO && modificabilePerScadenzaPag)
						// PP 05.08.2016 gara confermata non modificabile!!! || gara.getID_STATO_GARA()
						// == StatiScheda.CONFERMATO
						) && modificabile) {

							// check avcpass
							// LottoManager lman = new LottoManager(con, logger);
							// List<Lotto> listaLotti = lman.getListaLotti(gara.getId_Gara());
							if (isAVCPass(gara, null, AVCPassFunzioneEnum.WS_GARA_UPDATE.getCodice())) {
								throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
							}

							// setto i campi mancanti.., per sicurezza sovrascrivo i dati della gara con il
							// modificatore della gara..
							if (!isOperaComeOsservatorio) {
								gara.setCF_AMMINISTRAZIONE(collaborazioneSelezionata.getAzienda_codiceFiscale());// override
								// modifico anche i dati della sa/uff
								gara.setDENOM_STAZIONE_APPALTANTE(collaborazioneSelezionata.getUfficio_denominazione());
								gara.setDENOM_AMMINISTRAZIONE(collaborazioneSelezionata.getAzienda_denominazione());
								gara.setID_STAZIONE_APPALTANTE(collaborazioneSelezionata.getUfficio_id());
								//
							} else {
								gara.setCF_AMMINISTRAZIONE(garaXml.getCFAMMINISTRAZIONE());
								gara.setDENOM_STAZIONE_APPALTANTE(garaXml.getDENOMSTAZIONEAPPALTANTE());
								gara.setDENOM_AMMINISTRAZIONE(garaXml.getDENOMAMMINISTRAZIONE());
								gara.setID_STAZIONE_APPALTANTE(garaXml.getIDSTAZIONEAPPALTANTE());

								// condizione che non dovrebbe mai capitare dato che l'adminOr e' da tre
								// caratteri
								if (adminOr != null && adminOr.length() == 2)
									adminOr = "0" + adminOr;

								gara.setID_OSSERVATORIO(adminOr);
							}
							gara.setOggetto(garaLocale.getOggetto());
							/***
							 * PP 11/03/2011 obino dice che si toglie questo vincolo // importo gara non
							 * modificabile se già trasmessa a riscossione String dataCom =
							 * tableBeanGara.getNulledField(GARA.DATA_COMUN, 0) ;
							 * 
							 * if("".equals(dataCom)){ gara.setIMPORTO_GARA(garaLocale.getIMPORTO_GARA()); }
							 ***/
							// MAC #24169
							if (gara.getDATA_PERFEZIONAMENTO_BANDO() == null)
								gara.setIMPORTO_GARA(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));

							gara.setTIPO_SCHEDA_GARA(garaLocale.getTIPO_SCHEDA_GARA());
							gara.setID_MODO_REAL(garaLocale.getID_MODO_REAL());
							gara.setCIG_ACC_QUADRO(garaLocale.getCIG_ACC_QUADRO());

							// PP 14.09.2016 questa if non ha senso il dato va preso dalla busta
							// if (garaLocale.getID_MODO_GARA() != 0 ) {
							gara.setID_MODO_GARA(garaLocale.getID_MODO_GARA());
							// }

							// PP 22.08.2014 mancava valorizzazione numero lotti
							gara.setNumeroLotti(garaLocale.getNumeroLotti());

							if (SimogFlags.is3031_ESCL_AVCPASS()) { // PP 14.09.2016 && garaXml.getESCLUSOAVCPASS() !=
																	// null){
								gara.setESCLUSO_AVCPASS(garaLocale.getESCLUSO_AVCPASS());
							}

							if (SimogFlags.isINT87_RFSIMOGWS01Active() // PP 14.09.2016 && garaXml.getURGENZADL133() !=
																		// null
									&& ConfigurationManager.getInstance().getSimogProperties()
											.isINT87Attivo(gara.getData_creazione())) {
								gara.setURGENZA_DL133(garaLocale.getURGENZA_DL133());
							}

							if (SimogFlags.is30350_RFWEBGL01Active()) {
								gara.setCOD_MOTIVO_EAGG(garaLocale.getCOD_MOTIVO_EAGG());
								gara.setCatMerc(garaLocale.getCatMerc());
							}

							// TICKET ALM #664
							gara.setID_SVOLGIMENTO(garaLocale.getID_SVOLGIMENTO());
							// FINE TICKET ALM #664

							// TICKET ALM #3832
							gara.setID_ESTREMA_URGENZA(garaLocale.getID_ESTREMA_URGENZA());
							// FINE TICKET ALM #3832

							// TICKET ALM #3834
							gara.setID_ALLEGATO_IX(garaLocale.getID_ALLEGATO_IX());
							// FINE TICKET ALM #3834

							// TICKET ALM - 3.04.3
							gara.setDurataGiorni(garaLocale.getDurataGiorni());
							// FINE TICKET ALM - 3.04.3

							// TICKET ALM #659 - 3.04.4
							gara.setFlagSAAgente(garaLocale.getFlagSAAgente());
							gara.setCF_AMM_AGENTE(garaLocale.getCF_AMM_AGENTE());
							gara.setID_F_DELEGATE(garaLocale.getID_F_DELEGATE());
							gara.setDEN_AMM_AGENTE(garaLocale.getDEN_AMM_AGENTE());
							// FINE TICKET ALM #659 - 3.04.4

							gara.setCodiceAusa(garaLocale.getCodiceAusa());

							// valido
							validate(gara, ParametriServletGara.TAB_GARA);
							// se passa la validazione
							if (!thereIsAnError) {
								// salvo
								garaManager.saveGara(gara);

								// TICKET TICKET ALM #659 - 3.04.4
								if (gara.getFlagSAAgente() != null && !"".equals(gara.getFlagSAAgente())) {
									if (Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente())) {
										BdncpManager bm = new BdncpManager(con, logger);
										gara.setDEN_AMM_AGENTE(bm.loadDenSA(gara.getCF_AMM_AGENTE()));

										if (garaManager.checkFunzioniDelegateGara(gara.getId_Gara())) {
											garaManager.updateFunzioniDelegateGara(gara);
										} else {
											garaManager.insertFunzioniDelegateGara(gara);
										}
									} else
										garaManager.deleteFunzioniDelegateGara(gara.getId_Gara());
								}
								// FINE TICKET ALM #659 - 3.04.4
								LogManager logManager = new LogManager(con, logger);
								logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
										gara.getID_STAZIONE_APPALTANTE(), User_id, "", LogManager.MOD_GARA,
										gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));

								return true;
								// altrimenti
							} else {
								logger.debug("Fallita la validazione della gara");
								return false;
							}
						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_05);
						}
					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_13);
					}
				} else {
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
				}
			} catch (NumberFormatException nfe) {
				logger.error(
						"eccezione occorsa durante la conversione di id_gara o dell'importo gara " + nfe.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_06);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				logger.error("eccezione occorsa provando ad aggiornare una gara " + sqle.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);
			} catch (SimogWSException swe) {
				throw swe;
			} catch (Exception e) {
				logger.error("eccezione occorsa provando ad aggiornare una gara " + e.getMessage());
				e.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);

			}
		} else {
			logger.error("uno degli oggetti necessari all'inserimento della gara risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
		}

	}

	/**
	 * Metodo per la cancellazione di una gara gia' esistente
	 * 
	 * @param User_id
	 * @param collaborazioniRssa
	 * @param id_gara
	 * @param id_motivazione
	 * @param note
	 * @param isOperaComeOsservatorio
	 * @return
	 * @throws SimogWSException
	 */
	public boolean cancellaGara(String User_id, CollaborazioniRssa collaborazioniRssa, String id_gara,
			String id_motivazione, String note, boolean isOperaComeOsservatorio) throws SimogWSException {
		if (con != null) {
			GaraManager garaManager = new GaraManager(con, logger);
			try {
				// carico la gara prima
				Gara gara = garaManager.getGara(Long.parseLong(id_gara));
				// caso in cui id_gara non sia valido (corretto)
				if (gara != null) {
					boolean pubblicato = false; // PP ex riscossione boolean pubblicato = (gara.getID_STATO_GARA() ==
												// StatiScheda.CONFERMATO) ;
					TableBean dettGara = garaManager.getDettagliGaraByIdGara(id_gara);
					boolean hasLotti = Integer.parseInt(dettGara.getNulledField(LOTTO.ID_LOTTO, 0)) != 0;

					// se tutti i lotti sono cancellati allora la gara � cancellabile
					if (hasLotti) {
						hasLotti = false;
						for (int i = 0; i < dettGara.getFullSize(); i++) {
							String dataCanc = dettGara.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO, i);
							String dataInib = dettGara.getNulledField(LOTTO.DATA_INIB_PAGAMENTO, i);

							if ("".equals(dataCanc) && "".equals(dataInib))
								hasLotti = true;
							break;
						}
					}

					// apertura osservatorio
					if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

						// se la gara e' cancellabile
						if (gara.getDATA_CANCELLAZIONE_GARA() == null && gara.getDATA_PERFEZIONAMENTO_BANDO() == null // TICKET
																														// ALM
																														// #20238
								&& gara.getDATA_INIB_PAGAM() == null && !hasLotti) {

							// check avcpass
							// LottoManager lman = new LottoManager(con, logger);
							// List<Lotto> listaLotti = lman.getListaLotti(gara.getId_Gara());
							if (isAVCPass(gara, null, AVCPassFunzioneEnum.WS_GARA_DELETE.getCodice())) {
								throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
							}

							// cancello la gara
							garaManager.cancelGara(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
									id_gara, pubblicato, id_motivazione, note);

							if (ConfigurationManager.getInstance().getSimogProperties().getDataRequisiti()
									.compareTo(PageHelper.getCurrentDate()) <= 0) {
								// revoca dei requisiti associati al lotto
								RequisitiGLAction rqa = new RequisitiGLAction(con, logger);
								rqa.revocaRequisitiByGara(Long.valueOf(id_gara), !SimogFlags.is3028_RNFDBDT01Active());
							}

							LogManager logManager = new LogManager(con, logger);
							logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
									gara.getID_STAZIONE_APPALTANTE(), User_id, "", LogManager.DEL_GARA,
									gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));

							return true;

						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_17);
						}
					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_18);
					}
				} else {
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
				}
			} catch (NumberFormatException nfe) {
				logger.error(
						"eccezione occorsa durante la conversione di id_gara o dell'importo gara " + nfe.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_06);
			} catch (SQLException sqle) {
				logger.error("eccezione occorsa provando a cancellare una gara " + sqle.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_SQL_24);
			} catch (SimogWSException swe) {
				throw swe;
			} catch (Exception e) {
				logger.error("eccezione occorsa provando a cancellare una gara " + e.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_SQL_24);

			}
		} else {
			logger.error("uno degli oggetti necessari alla cancellazione della gara risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_03);
		}

	}

	/**
	 * Metodo per la conferma di una gara
	 * 
	 * @param User_id
	 * @param collaborazioniRssa
	 * @param id_gara
	 * @param isOperaComeOsservatorio
	 * @return
	 * @throws SimogWSException
	 */
	public boolean perfezionaGara(String User_id, CollaborazioniRssa collaborazioniRssa, String id_gara,
			boolean isOperaComeOsservatorio) throws SimogWSException {

		return false;

//		if(con != null){
//			GaraManager garaManager = new GaraManager(con,logger);
//			try{
//				//carico la gara prima
//				Gara gara = garaManager.getGara(Long.parseLong(id_gara));
//				if(gara != null){
//					// apertura osservatorio
//					if(checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara) ){
//						if(gara.getID_STATO_GARA() == StatiScheda.IN_DEFINIZIONE){ 
//							// messaggi da ritornare
//				    		AllValidationBeans msgs = new AllValidationBeans();
//				    		
//					    	// pp calcolo del contributo SA usando i WS se configurati altrimenti la tabella SIMOG
//				    		GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient (ConfigurationManager.getInstance().getSimogProperties().getContributoUrl(), con, logger);
//
//				    		ParametriContributo params = new ParametriContributo(gara, null, PageHelper.getIncreasedDate(0));
//				    		
//							BigDecimal importo = gcwbc.getContributoSA(params);
//							if(!gcwbc.hasErrors())
//								gara.setIMPORTO_SA_GARA(importo);
//							else if(SimogFlags.is30230_NRFWEBXX00Active())
//							   gara.setIMPORTO_SA_GARA(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));
//							
//				    		//confermo
//							garaManager.confirmGara(gara);
//
//							LogManager logManager = new LogManager(con, logger);
//							logManager.log(
//									PageHelper.getDBDateFromTS(new AccessiDB(con,logger).getNow()),
//									gara.getID_STAZIONE_APPALTANTE(),
//									User_id,
//									"",
//									LogManager.PERF_GARA + "(304)",
//									gara.getCF_AMMINISTRAZIONE(),
//									"",
//									String.valueOf(gara.getId_Gara()));
//								
//							return true;
//						}
//						else{
//							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_05);
//						} 
//					}else{
//						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_13);
//					}
//					
//				}else{
//					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
//				}
//			}catch(NumberFormatException nfe){
//				logger.error("eccezione occorsa durante la conversione di id_gara "+nfe.getMessage());
//				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_06);
//			}catch(SQLException sqle){
//				logger.error("eccezione occorsa provando ad aggiornare una gara "+sqle.getMessage());
//				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_08);
//			}catch(SimogWSException swe){
//				throw swe;
//			}catch(Exception e){
//				logger.error("eccezione occorsa provando ad aggiornare una gara "+e.getMessage());
//				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_08);				
//			}
//		}else{
//			logger.error("uno degli oggetti necessari all'inserimento della gara risulta nullo");
//			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_02);
//		}		
	}

	/**
	 * metodo per la modifica di un lotto di cui CIG
	 * 
	 * @param dati                    LottoWSDocument: dati, xmlbean
	 * @param User_id
	 * @param collaborazioniRssa
	 * @param cig
	 * @param isOperaComeOsservatorio
	 * @return
	 * @throws SimogWSException
	 */
	public boolean modificaLotto(LottoWSDocument dati, String User_id, CollaborazioniRssa collaborazioniRssa,
			String cig, boolean isOperaComeOsservatorio) throws SimogWSException {
		LottoType datiLotto = dati.getLottoWS().getLotto();
		// la validazione viene fatta a tempo di conversione quindi
		// ho dovuto anticipare il recuper del lotto locale per avere l'id_gara
		// che risulta necessario in fase di validazione (importo gara >= importo lotto)
		Lotto localLotto = null;
		LottoManager lottoManager = new LottoManager(con, logger);
		try {

			localLotto = lottoManager.getLottoByCigWS(cig).get(0);
		} catch (Exception e) {
			logger.debug("Impossibile recuperare la copia locale del lotto, per il confronto: " + e.getMessage());
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
		}
		this.id_gara = localLotto.getId_Gara();
		// imposto il cig mi serve nel validate
		datiLotto.setCIG(cig);

		Lotto lotto = this.converti(datiLotto);
		// se non ci sono errori
		if (!this.thereIsAnError) {
			if (con != null && lotto != null) {

				Collaborazione collaborazioneSelezionata = null;
				if (!isOperaComeOsservatorio)
					collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

				GaraManager garaManager = new GaraManager(con, logger);

				try {
					Gara gara = garaManager.getGara(localLotto.getId_Gara());
					if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
						// confronta gara->collaborazioni(must be equals)

						// apertura osservatorio
						if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio,
								gara)) {

							TableBean tableBeanGara = null;
							if (!isOperaComeOsservatorio) {
								// controllo se e' modificabile
								Hashtable listaCodiciRssa = new Hashtable();
								ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
								for (String stringa : elenco) {
									listaCodiciRssa.put(stringa, "");
								}

								tableBeanGara = garaManager.getDettagliGaraByIdGaraRSSA(String.valueOf(id_gara),
										listaCodiciRssa);
							} else {
								tableBeanGara = garaManager.getDettagliGaraByIdGara(String.valueOf(id_gara));
							}
							boolean modificabile = true;

							for (int i = 0; i < tableBeanGara.getFullSize(); i++) {
								if (CIGBean.getRealCIG(cig)
										.equals(tableBeanGara.getNulledField(LOTTO.CIG, i)
												+ tableBeanGara.getNulledField(LOTTO.CIG_KKK, i))
										&& !"0".equals(tableBeanGara.getNulledField(PSBD.HASSCHEDE, i))) {
									modificabile = false;
									break;
								}
							}

							String dataScadPagStr = tableBeanGara.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI, 0);
							boolean modificabilePerScadenzaPag = false;
							if (dataScadPagStr != null && !"".equals(dataScadPagStr)) {
								Date today = PageHelper.getCurrentUtilDate();
								Date dataScadPag = new SimpleDateFormat("yyyyMMdd").parse(dataScadPagStr);
								modificabilePerScadenzaPag = dataScadPag.after(today);
							}

							if (((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null
									|| "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO()))
									// && ( localLotto.getData_Comunicazione() == null ||
									// "".equals(localLotto.getData_Comunicazione()))
									&& (localLotto.getDATA_INIB_PAGAMENTO() == null
											|| "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
									// PP 29.07.2014 ripristinato controllo su data pubblicazione per impedire
									// modifiche dopo la pubblicazione
									&& (localLotto.getData_Pubblicazione() == null
											|| "".equals(localLotto.getData_Pubblicazione()))
									|| modificabilePerScadenzaPag) && modificabile) {

								// check avcpass
								if (SimogFlags.is3028_RFWEBGL07Active()) {
									List<Lotto> listaLotti = new ArrayList<Lotto>();
									listaLotti.add(localLotto);
									if (isAVCPass(null, listaLotti, AVCPassFunzioneEnum.WS_LOTTO_UPDATE.getCodice())) {
										throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
									}
								}

								lotto.setId_Lotto(localLotto.getId_Lotto());
								// X-XX: mancava l'id_gara e altro
								lotto.setId_Gara(localLotto.getId_Gara());
								lotto.setCig(localLotto.getCIG());
								lotto.setCig_kkk(localLotto.getCIG_kkk());

								/***
								 * PP 11/03/2011 obino dice che si toglie questo vincolo
								 * 
								 * // importo lotto non modificabile se già trasmessa a riscossione
								 * if(!"".equals(localLotto.getData_Comunicazione())){
								 * lotto.setImporto_Lotto(localLotto.getImporto_Lotto()); }
								 * 
								 * //end
								 ***/

								// TICKET ALM #3832-02.3
								// Se la gara e' stata creata successivamente la data di attivazione, il valore
								// delle somme urgenze deve essere sovrascritto dal flag della gara
								if (SimogFlags.is3042Active() && gara.getData_creazione()
										.compareTo(SimogProperties.getInstance().getDataAttivazione3042()) >= 0) {
									lotto.setSomma_Urgenza(
											gara.getURGENZA_DL133() != null ? gara.getURGENZA_DL133().charAt(0) : 'N');
								}

								// TICKET ALM - 3.04.7
								if (lotto.getLUOGO_ISTAT() != null && !"".equals(lotto.getLUOGO_ISTAT())) {
									IstatNutsManager inm = new IstatNutsManager(con, logger);
									lotto.setLUOGO_NUTS(inm.getNutsFromIstat(lotto.getLUOGO_ISTAT()));
								}

								lottoManager.modificaLotto(lotto);
								
								// update misure premiali
								MisuraPremialeManager mpm = new MisuraPremialeManager(con, logger);
								mpm.aggiornaMisuraPremialeLotto(lotto);
								
								// update motivo deroga
								MotivoDerogaManager mdm = new MotivoDerogaManager(con, logger);
								mdm.aggiornaMotivoDerogaLotto(lotto);
								
								// update categorie scorporabili
								if (lotto.getCategorieScorporabili() != null
										&& !lotto.getCategorieScorporabili().isEmpty()) {
									this.updateCategorieScorporabili(lotto.getCategorieScorporabili(),
											localLotto.getId_Lotto(), lottoManager);
								}

								if (SimogFlags.is3031_RFWEBGL02Active()) {
									CupLottoAggManager cupMan = new CupLottoAggManager(con, logger);
									cupMan.settingIdLotto(lotto);
									cupMan.updateElencoCup(lotto, false);
								}

								if (SimogFlags.is3031_RFWEBGL00Active()) {
									TipoAppaltoManager talMan = new TipoAppaltoManager(con, logger);
									talMan.settingIdLotto(lotto);
									talMan.aggiornaTipoAppaltoLotto(lotto, false);
								}

								// TICKET ALM #3835
								if (SimogFlags.is3042Active()) {
									CondizioniAction ca = new CondizioniAction(con, logger);
									ca.saveCondizioniLotto(lotto.getCondizioni(), lotto.getId_Lotto());
								}
								// FINE TICKET ALM #3835

								// TICKET ALM #4219 - 3.04.4
								lottoManager.deleteCpvLotto(lotto.getId_Lotto());
								// Elimina duplicati
								lotto.setElencoCpvSecondarie(this.verificaDuplicati(lotto.getElencoCpvSecondarie()));
								for (CpvLotto cpvSec : lotto.getElencoCpvSecondarie())
									lottoManager.insertCpvLotto(lotto.getId_Lotto(), cpvSec);
								// FINE TICKET ALM #4219 - 3.04.4

								// TICKET ALM #4223-#4224 - 3.04.4
								IniziativaManager im = new IniziativaManager(con, logger);

								boolean flagNoDpcmDB = false;
								boolean flagNoSaClassDB = false;
								List<Long> autodichiarazioniDB = im.getAutodichiarazioni(lotto.getId_Lotto(),
										lotto.getCOD_CATEGORIA());
								boolean flagNoDpcmUser = lotto.getFlagNoAdesione() != null
										&& !"".equals(lotto.getFlagNoAdesione())
										&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagNoAdesione());
								boolean flagNoSaClassUser = lotto.getFlagSANonClass() != null
										&& !"".equals(lotto.getFlagSANonClass())
										&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagSANonClass());

								for (Long auto : autodichiarazioniDB) {
									if (auto == Costanti.INIZIATIVE_NON_IDONEE)
										flagNoDpcmDB = true;
									if (auto == Costanti.SA_NON_CLASSIFICATA)
										flagNoSaClassDB = true;
								}

								// Se non ci sono autodichiarazioni nel db e' possibile che la categoria sia
								// cambiata.
								// Per sicurezza, far scadere tutte le autodichiarazioni valide
								if (Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA())
										|| autodichiarazioniDB.size() == 0)
									im.expireAutodichiarazione(lotto.getId_Lotto(), 0);

								// Se c'era un'autodichiarazone nel db e il flag dell'utente e' no, far scadere
								// l'autodichiarazione
								if (flagNoDpcmDB && !flagNoDpcmUser)
									im.expireAutodichiarazione(lotto.getId_Lotto(), Costanti.INIZIATIVE_NON_IDONEE);
								if (flagNoSaClassDB && !flagNoDpcmUser)
									im.expireAutodichiarazione(lotto.getId_Lotto(), Costanti.SA_NON_CLASSIFICATA);

								// Se le autodichiarazioni non sono presenti su db ma sono state richieste,
								// vanno inserite
								if (!flagNoDpcmDB && flagNoDpcmUser) {
									im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()),
											lotto.getId_Lotto(), Costanti.INIZIATIVE_NON_IDONEE);
								}
								if (!flagNoSaClassDB && flagNoSaClassUser) {
									im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()),
											lotto.getId_Lotto(), Costanti.SA_NON_CLASSIFICATA);
								}

								// SOLO WEB
								// Se non sono state inviate autodichiarazioni ma si sta aderendo a una
								// iniziativa, azzerare anche in questo caso le autodichiarazioni valide
								if (SimogFlags.isFromWeb() && !flagNoDpcmUser && !flagNoSaClassUser
										&& !Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA())
										&& lotto.getCigIniziativa() != null) {
									im.expireAutodichiarazione(lotto.getId_Lotto(), 0);
									IniziativaManager inizMan = new IniziativaManager(con, logger);
									IniziativaSoggAggr iniziativa = inizMan
											.getIniziative(lotto.getCigIniziativa(), null, null, null, null, true)
											.get(0);
									if (gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE || gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET) {
										int idModReal = iniziativa.getFlagConfrontoComp() != null
												&& Costanti.FLAG_VALORE_SI.equals(iniziativa.getFlagConfrontoComp())
														? Costanti.MODOREAL_ADESIONE
														: Costanti.MODOREAL_ADESIONE_NOCOMPET;
										garaManager.updateCIGAccQ(lotto.getId_Gara(), lotto.getCigIniziativa(), idModReal);
									}
//									3.04.8 34190 fix
									if (gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE || gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) {
										int idModReal = iniziativa.getFlagConfrontoComp() != null
												&& Costanti.FLAG_VALORE_SI.equals(iniziativa.getFlagConfrontoComp())
														? Costanti.MODOREAL_CONCESSIONE
														: Costanti.MODOREAL_CONCESSIONE_NOCOMPET;
										garaManager.updateCIGAccQ(lotto.getId_Gara(), lotto.getCigIniziativa(), idModReal);
									}
									
									
									
								}

								// FINE TICKET ALM #4223-#4224 - 3.04.4

								LogManager logManager = new LogManager(con, logger);
								logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
										gara.getID_STAZIONE_APPALTANTE(), User_id, lotto.getCIG() + lotto.getCIG_kkk(),
										LogManager.MOD_LOTTO, gara.getCF_AMMINISTRAZIONE(),
										Long.toString(lotto.getId_Lotto()), String.valueOf(lotto.getId_Gara()));

								return true;

							} else {
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_12);
							}
						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
						}
					} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
								// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
								// NON
								// siano previste evoluzioni future in questo senso si puo levare
// PP						if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//								&& ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//								&& ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//								&& ( localLotto.getData_Pubblicazione() == null || "".equals(localLotto.getData_Pubblicazione()))){
//							lotto.setId_Lotto(localLotto.getId_Lotto());					
//							lm.modificaLotto(lotto);
//							this.updateCategorieScorporabili(lotto.getCategorieScorporabili(), localLotto.getId_Lotto(), lm);
//							return true;	
//						}else{ throw new SimogWSException("il lotto non si trova in uno stato che permetta la modifica!"); }
					}
				} catch (SimogWSException swe) {
					throw swe;
				} catch (SQLException sqle) {
					logger.error("errore nel recupero del lotto da modificare");
					// sqle.printStackTrace();
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + sqle.getMessage());
				} catch (Exception e) {
					logger.error("errore nel recupero del lotto da modificare");
					// e.printStackTrace();
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + e.getMessage());
				}
			}
			throw new SimogWSException(ErrorManager.SIMOGWS_CIGMANAGER_NULL_02);
		} else {
			logger.error("modifica fallita");
			return false;
		}
	}

	/**
	 * metodo per la cancellazione di un lotto di cui CIG
	 * 
	 * @param User_id
	 * @param collaborazioneSelezionata
	 * @param collaborazioniRssa
	 * @param cig
	 * @param id_motivazione
	 * @param note
	 * @param isOperaComeOsservatorio
	 * @return
	 * @throws SimogWSException
	 */
	public boolean cancellaLotto(String User_id, Collaborazione collaborazioneSelezionata,
			CollaborazioniRssa collaborazioniRssa, String cig, String id_motivazione, String note,
			boolean isOperaComeOsservatorio) throws SimogWSException {
		Lotto localLotto = null;
		LottoManager lottoManager = new LottoManager(con, logger);
		try {
			localLotto = lottoManager.getLottoByCigWS(cig).get(0);
		} catch (Exception e) {
			logger.debug("Impossibile recuperare la copia locale del lotto, per il confronto");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_19);
		}
		this.id_gara = localLotto.getId_Gara();

		// se il lotto e' cancellabile
		GaraManager garaManager = new GaraManager(con, logger);

		TableBean tableBeanGara = null;
		try {
			if (!isOperaComeOsservatorio) {
				// controllo se e' modificabile
				Hashtable listaCodiciRssa = new Hashtable();
				ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
				for (String stringa : elenco) {
					listaCodiciRssa.put(stringa, "");
				}

				tableBeanGara = garaManager.getDettagliGaraByIdGaraRSSA(String.valueOf(id_gara), listaCodiciRssa);
			} else {
				tableBeanGara = garaManager.getDettagliGaraByIdGara(String.valueOf(id_gara));
			}
		} catch (SQLException e1) {
			// e1.printStackTrace();
			logger.error("errore nel recupero del lotto da cancellare");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + e1.getMessage());
		}

		boolean cancellabile = true;

		// TICKET ALM #20238
		for (int i = 0; i < tableBeanGara.getFullSize(); i++) {
			if (CIGBean.getRealCIG(cig)
					.equals(tableBeanGara.getNulledField(LOTTO.CIG, i) + tableBeanGara.getNulledField(LOTTO.CIG_KKK, i))
					&& ((tableBeanGara.getNulledField(LOTTO.DATA_PUBBLICAZIONE, i) != null
							&& !"".equals(tableBeanGara.getNulledField(LOTTO.DATA_PUBBLICAZIONE, i)))
							|| !"0".equals(tableBeanGara.getNulledField(PSBD.HASSCHEDE, i)))) {
				cancellabile = false;
				break;
			}
		}

		if (localLotto.getDATA_CANCELLAZIONE_LOTTO() == null && localLotto.getDATA_INIB_PAGAMENTO() == null
				&& cancellabile) {

			try {
				Gara gara = garaManager.getGara(localLotto.getId_Gara());

				// check avcpass
				if (SimogFlags.is3028_RFWEBGL07Active()) {
					List<Lotto> listaLotti = new ArrayList<Lotto>();
					listaLotti.add(localLotto);
					if (isAVCPass(null, listaLotti, AVCPassFunzioneEnum.WS_LOTTO_DELETE.getCodice())) {
						throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
					}
				}

				if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
					// confronta gara->collaborazioni(must be equals)
					// apertura osservatorio
					if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioneSelezionata, isOperaComeOsservatorio,
							gara)) {

						boolean pubblicato = false; // PP ex riscossione (localLotto.getData_Pubblicazione() != null &&
													// localLotto.getData_Pubblicazione().length() > 0 ) ;

// FIXMato: PP così no può funzionare, per ora restano i requisiti, associati al lotto cancellato
						if (SimogFlags.is3025_REQUISITIActive()) {
							Timestamp currentDatetime = new AccessiDB(con, logger).getNow();

							// PP devo considerare la data di creazione della gara se esiste
							if (gara.getData_creazione() != null)
								currentDatetime = PageHelper.parseTimeYMD(gara.getData_creazione());

							// revoca dei requisiti associati al lotto
							RequisitiGLAction rqa = new RequisitiGLAction(con, logger);
							rqa.revocaRequisitiByLotto(localLotto.getId_Gara(), localLotto.getId_Lotto(),
									currentDatetime, false);
						}

						lottoManager.cancellaLotto(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
								String.valueOf(localLotto.getId_Lotto()), id_motivazione, note, pubblicato);

						if (SimogFlags.isGRIGLIA_CONTRIBUTO()) {
							GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(
									ConfigurationManager.getInstance().getSimogProperties().getContributoUrl(), con,
									logger);
							if (gara.getDATA_CONFERMA_GARA() != null || gara.getDATA_PERFEZIONAMENTO_BANDO() != null) {
								// devo ricalcolare i contributi di tutti i lotti e della gara
								Map<String, Lotto> lotti = lottoManager.getMappaLotti(gara.getId_Gara());

								gcwbc.ricalcola(gara, lotti);

								garaManager.saveGara(gara);
							}
						}

						LogManager logManager = new LogManager(con, logger);
						logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
								gara.getID_STAZIONE_APPALTANTE(), User_id, cig, LogManager.DEL_LOTTO,
								gara.getCF_AMMINISTRAZIONE(), String.valueOf(localLotto.getId_Lotto()),
								String.valueOf(localLotto.getId_Gara()));

						return true;

					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_20);
					}
				} else {
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
				}
			} catch (SQLException sqle) {
				logger.error("errore nel recupero del lotto da cancellare");
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + sqle.getMessage());
			} catch (Exception e) {
				logger.error("errore nel recupero del lotto da cancellare");
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + e.getMessage());
			}
		} else {
			logger.error("LOTTO NON CANCELLABILE");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_21);
		}
	}

	/**
	 * metodo per il perfezionamento di un lotto di cui CIG
	 * 
	 * @param dataPubblicazione
	 * @param dataScadenzaPagamenti
	 * @param User_id
	 * @param collaborazioneSelezionata
	 * @param cig
	 * @param isOperaComeOsservatorio
	 * @return
	 * @throws SimogWSException
	 */
	public boolean perfezionaLotto(String dataPubblicazione, String dataScadenzaPagamenti, String User_id,
			Collaborazione collaborazioneSelezionata, String cig, boolean isOperaComeOsservatorio, String oraScadenza)
			throws SimogWSException {

		return false;

//		if(con != null && this.checkData(dataPubblicazione) && this.checkData(dataScadenzaPagamenti)){
//			LottoManager lottoManager = new LottoManager(con,logger);
//			GaraManager garaManager = new GaraManager(con,logger);
//			try{
//				Lotto localLotto = lottoManager.getLottoByCigWS(cig).get(0);
//				Gara gara = garaManager.getGara(localLotto.getId_Gara());
//				
//				this.id_gara = localLotto.getId_Gara();
//				
//				if(collaborazioneSelezionata != null || isOperaComeOsservatorio){				
//					//confronta gara->collaborazioni(must be equals)
//					// apertura osservatorio
//					if(checkCollaborazioneOrOperaComeOsservatorio(collaborazioneSelezionata, isOperaComeOsservatorio, gara) ){
//						//controllo se e' perfezionabile
//						if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//								// PP && ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//								&& ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//								&& ( localLotto.getDATA_SCADENZA_PAGAMENTI() == null || PageHelper.getCurrentDate().compareTo(localLotto.getDATA_SCADENZA_PAGAMENTI())<0)){
//							//validazione
//							/** adds [validazione per il perfezionamento]*/
//							SimogValidator sv = ValidatorFactory.getValidator(ParametriServletLotto.TAB_LOTTO, con, logger);
//							localLotto.setData_Pubblicazione(dataPubblicazione);
//							localLotto.setDataScadenzaPagamenti(dataScadenzaPagamenti);
//							//esito validazione negato se valido la variabile &egrave false
//							//QUI BASTA CONTROLLARE LE DATE DI PERFEZIONAMENTO IN QUANTO IL RESTO E' CARICATO DA DB
//							
//							//is3028_RFWEBGL08Active
//							sv.setGiorni(ConfigurationManager.getInstance().getSimogProperties().getGiorni_pubb_scadenza());
//							this.thereIsAnError =  !sv.valida(localLotto, ParametriServlet.PERFEZIONAMENTO);
//							//aggiungo eventuali errori
//							if(this.thereIsAnError){
//								this.setSimogValidatorError(sv);
//							}
//							/** end */
//		//					if(dataPubblicazione != null && !"".equals(dataPubblicazione)
//		//							&& (dataScadenzaPagamenti != null && !"".equals(dataScadenzaPagamenti))
//		//							&& (dataPubblicazione.compareTo(dataScadenzaPagamenti)<0)){
//							if(!this.thereIsAnError){
//								//setto il bean intero per la validazione del validatore
//								localLotto.setData_Pubblicazione(dataPubblicazione);
//								localLotto.setDataScadenzaPagamenti(dataScadenzaPagamenti);
//								
//								
//								 //aggiungo la validazione (qui non passo dal convertitore quindi la devo fare esplicitamente)
//								validate(localLotto, ParametriServletLotto.TAB_LOTTO);
//
//								if(!this.thereIsAnError){
//
//									// messaggi da ritornare
//						    		AllValidationBeans msgs = new AllValidationBeans();
//											
//									// pp calcolo del contributo OE usando i WS se configurati altrimenti la tabella SIMOG		
//						    		GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient (ConfigurationManager.getInstance().getSimogProperties().getContributoUrl(), con, logger);
//
//						    		ParametriContributo params = new ParametriContributo(gara, localLotto, PageHelper.getCalendarFromStringDate(dataPubblicazione));
//
//						    		BigDecimal impImpresa = new BigDecimal(0);
//						    		BigDecimal importo = gcwbc.getContributoOE(params);
//									if(!gcwbc.hasErrors())
//									   impImpresa = importo;
//					                else if(SimogFlags.is30230_NRFWEBXX00Active())
//					                   impImpresa = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
//
//									lottoManager.perfezionaLotto(Long.toString(localLotto.getId_Lotto()), 
//									      dataPubblicazione, dataScadenzaPagamenti, impImpresa, oraScadenza);
//									
//			                        // PP devo anche ricalcolare il contributo  gara (patch su 3.02.3)
//                                    if(gara.getDATA_CONFERMA_GARA() != null || gara.getDATA_PERFEZIONAMENTO_BANDO() != null){
//		                               if(SimogFlags.isGRIGLIA_CONTRIBUTO()){
//		                                     // devo ricalcolare i contributi di tutti i lotti e della gara
//		                                     Map<String, Lotto> lotti = lottoManager.getMappaLotti(gara.getId_Gara());
//		                                          
//		                                     gcwbc.ricalcola(gara, lotti);
//		                                  }
//		                               else{ 
//      		                                // PP devo ricalcolare l'importo contributo gara se questa è perfezionata
//      		                                BigDecimal impGaraCalcolo = gcwbc.getImportoGara(lottoManager.getListaLotti(gara.getId_Gara()), true, true);
//      		                                
//      		                                Gara garaCalc = (Gara) gara.clone();
//      		                                garaCalc.setIMPORTO_GARA(impGaraCalcolo);
//      		                                
//      		                                params = new ParametriContributo(garaCalc, null, 
//      		                                        garaCalc.getDATA_CONFERMA_GARA() != null
//      		                                        ? PageHelper.getCalendarFromStringDate(garaCalc.getDATA_CONFERMA_GARA())
//      		                                        : PageHelper.getCalendarFromStringDate(garaCalc.getDATA_PERFEZIONAMENTO_BANDO())
//      		                                        );
//      		                                
//      		                                importo = gcwbc.getContributoSA(params);
//      		                                if(!gcwbc.hasErrors())
//      		                                    gara.setIMPORTO_SA_GARA(importo);
//      		                                else if(SimogFlags.is30230_NRFWEBXX00Active())
//      		                                    gara.setIMPORTO_SA_GARA(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));
//                                       }
//                                    
//		                                // controlo messaggio di ritorno
//		                                msgs.add(gcwbc.getErrors());
//
//		                                // sfrutto la modifica della gara passando le stesse impostazioni 
//		                                garaManager.saveGara(gara);
//		                            }
//
//									
//									LogManager logManager = new LogManager(con, logger);
//									logManager.log(
//											PageHelper.getDBDateFromTS(new AccessiDB(con,logger).getNow()),
//											gara.getID_STAZIONE_APPALTANTE(),
//											User_id,
//											localLotto.getCIG() + localLotto.getCIG_kkk(),
//											LogManager.PERF_LOTTO,
//											gara.getCF_AMMINISTRAZIONE(),
//											Long.toString( localLotto.getId_Lotto() ),
//											String.valueOf(localLotto.getId_Gara()));
//												return true;
//								}
//								else{
//									logger.error("validazione fallita");
//								
//									return false;
//								}					
//							}else{
//								logger.error("Errore durante la validazione del Lotto");
//								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_16);
//							}
//						}else{
//							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_15);
//						}
//					}else{
//						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
//					}
//				}else{
//					return false;
//				}
//			}catch(Exception e){
//				if(e instanceof SimogWSException){
//					String error = ((SimogWSException)e).getMyMessage();
//					logger.error(error);
//					throw (SimogWSException)e;
//				}
//				e.printStackTrace();
//				logger.error("errore nel recupero del lotto da perfezionare");
//				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": "+e.getMessage());
//			}
//		}else{
//			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_02);
//		}	
	}

	/**
	 * metodo per l'inserimento delle categorie scorporabili
	 * 
	 * param HashMap<String,String>: categorieScorporabili<id,descrizione>
	 * 
	 * @param id_lotto
	 * @param lottoManager
	 * @throws SimogWSException
	 */
	private void insertCategorieScorporabili(HashMap<String, String> categorieScorporabili, long id_lotto,
			LottoManager lottoManager) throws SimogWSException {
		logger.debug(
				"eseguendo: void insertCategorieScorporabili(HashMap<String,String> m,long id_lotto,LottoManager lm)throws SQLException");
		/* categorie scorporabili */
		Set<String> set = categorieScorporabili.keySet();
		String[] categorie = new String[set.size()];
		int i = 0;
		for (String s : set) {
			categorie[i] = s;
			i++;
		}
		try {
			lottoManager.inserisciLottoCategoriaScorporabile(categorie, id_lotto);
		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.error(sqle.getMessage());
			throw new SimogWSException(sqle.getMessage());
		}
		/* end */
	}

	/**
	 * metodo per l'aggionrnamento delle categorie scorporabili
	 * 
	 * @param categorieScorporabili
	 * @param id_lotto
	 * @param lottoManager
	 * @throws SimogWSException
	 */
	private void updateCategorieScorporabili(HashMap<String, String> categorieScorporabili, long id_lotto,
			LottoManager lottoManager) throws SimogWSException {
		logger.debug(
				"eseguendo: void updateCategorieScorporabili(HashMap<String,String> m,long id_lotto,LottoManager lm)throws SQLException");
		/* categorie scorporabili */
		Set<String> set = categorieScorporabili.keySet();
		String[] categorie = new String[set.size()];
		int i = 0;
		for (String s : set) {
			categorie[i] = s;
			i++;
		}
		try {
			lottoManager.updateLottoCategorieScorporabili(Long.toString(id_lotto), categorie);
		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.error(sqle.getMessage());

			throw new SimogWSException(sqle.getMessage());
		}
		/* end */
	}
	/*----------------------------------------------*/
	/*----------- TOOLS OPERATIONS -----------------*/
	/*----------------------------------------------*/

	/**
	 * Metodo per la conversione di una stringa xml (che puo essere una gara o un
	 * lotto) nel bean xml, EFFETTUA ANCHE LA VALIDAZIONE del bean se la stringa xml
	 * risulta valida
	 * 
	 * @param String:         garaXml, e' la stringa rappresentante lotto o gara
	 * @param String:         User_id
	 * @param Collaborazione: collaborazioneSelezionata
	 * @param String:         nomeOggetto, parametro che puo assumere 3 valori [vedi
	 *                        costanti TIPO_XXX] serve a sapere di quale conversione
	 *                        di necessita
	 * @return Object: 2 tipi LottoWSDocument o GaraWSDocument (castare in base alla
	 *         stringa in ingresso)
	 * @throws SimogWSException
	 */
	public Object converti(String garaXml, String User_id, Collaborazione collaborazioneSelezionata, String nomeOggetto)
			throws SimogWsXmlException {
		// questo si occupa della conversione da stringa a xml bean
		logger.debug(
				"eseguendo:  Object converti(String datiGara,String User_id,Collaborazione coll,String param)throws SimogWSException");
		XmlOptions opts = new XmlOptions();
		ArrayList<XmlError> errors = new ArrayList<XmlError>();
		opts.setErrorListener(errors);
		try {

			if (nomeOggetto.equalsIgnoreCase(this.TIPO_LOTTO)) {
				LottoWSDocument lt = LottoWSDocument.Factory.parse(garaXml, opts);
				if (!lt.validate(opts)) {
					this.thereIsAnError = true;
					this.error = "XML_PARSE: \r\n";
					this.error += this.aggiungiDescrizioneErroriValidazioneXsd(errors);
					throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_01, this.error);
				}
				return lt;
				// conversione di solo gara
			} else if (nomeOggetto.equalsIgnoreCase(this.TIPO_GARA)) {
				GaraWSDocument gt = GaraWSDocument.Factory.parse(garaXml, opts);
				if (!gt.validate(opts)) {
					this.thereIsAnError = true;
					this.error = "XML_PARSE: \r\n";
					this.error += this.aggiungiDescrizioneErroriValidazioneXsd(errors);
					throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_01, this.error);
				}
				return gt;
			} else if (nomeOggetto.equalsIgnoreCase(this.TIPO_PUBBLICAZIONE)) {
				PubblicazioneWSDocument pub = PubblicazioneWSDocument.Factory.parse(garaXml, opts);
				if (!pub.validate(opts)) {
					this.thereIsAnError = true;
					this.error = "XML_PARSE: \r\n";
					this.error += this.aggiungiDescrizioneErroriValidazioneXsd(errors);
					throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_01, this.error);
				}
				return pub;
			} else if (nomeOggetto.equalsIgnoreCase(this.TIPO_REQUISITI)) {
				RequisitiWSDocument gt = RequisitiWSDocument.Factory.parse(garaXml, opts);
				if (!gt.validate(opts)) {
					this.thereIsAnError = true;
					this.error = "XML_PARSE: \r\n";
					this.error += this.aggiungiDescrizioneErroriValidazioneXsd(errors);
					throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_01, this.error);
				}
				return gt;
			} else {
				throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_02,
						"parametro per la conversione non valido");
			}
		} catch (XmlException xmle) {
			this.thereIsAnError = true;
			logger.error("eccezione durante la validazione del xml: " + xmle.getMessage());
			xmle.printStackTrace();
			// se this error null accoda messaggio, altrimenti valorizza con messaggio
			this.error = this.error != null ? this.error + xmle.getMessage() : xmle.getMessage();
			throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_02, this.error);
			// return false;
		}

	}

	// questo da xml type a bean per l'inserimento sul db
	/**
	 * metodo per convertire un bean xml(GaraType) al bean(Gara)
	 * 
	 * @param garaXml
	 * @param User_id
	 * @param collaborazioneSelezionata
	 * @param isOperaComeOsservatorio
	 * @param adminOr
	 * @return
	 * @throws SimogWSException
	 */
	private Gara converti(GaraType garaXml, String User_id, Collaborazione collaborazioneSelezionata,
			boolean isOperaComeOsservatorio, String adminOr) throws SimogWSException {
		// questo si occupa di convertire da xmlbean a bean
		Gara gara = new Gara();
		// primo passo servira' per la generazione del cig
		try {
			gara.setCF_UTENTE(User_id);

			gara.setData_creazione(new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
			gara.setOggetto(garaXml.getOGGETTO());

			gara.setIMPORTO_GARA(garaXml.getIMPORTOGARA());
			if (garaXml.getTIPOSCHEDA() != null) {
				gara.setTIPO_SCHEDA_GARA(String.valueOf(garaXml.getTIPOSCHEDA()));
			}
			// gara.setTIPO_SCHEDA_GARA(gt.getTIPOSCHEDA().toString()) ;
			if (garaXml.getMODOINDIZIONE() != null) {
				gara.setID_MODO_GARA(Integer.parseInt(garaXml.getMODOINDIZIONE()));
			}
			if (garaXml.getMODOREALIZZAZIONE() != null) {
				gara.setID_MODO_REAL(Integer.parseInt(garaXml.getMODOREALIZZAZIONE()));
			}

			if (garaXml.getCIGACCQUADRO() != null) {
				gara.setCIG_ACC_QUADRO(garaXml.getCIGACCQUADRO());
			}

			// Build 304

			if (garaXml.isSetNUMEROLOTTI()) {
				gara.setNumeroLotti(garaXml.getNUMEROLOTTI());
			} else
				gara.setNumeroLotti(null);

			if (SimogFlags.is3031_ESCL_AVCPASS() && garaXml.getESCLUSOAVCPASS() != null) {
				gara.setESCLUSO_AVCPASS(garaXml.getESCLUSOAVCPASS().toString());
			}

			// TICKET MAC #8215
			if (SimogFlags.isINT87_RFSIMOGWS01Active() && ConfigurationManager.getInstance().getSimogProperties()
					.isINT87Attivo(gara.getData_creazione())) {
				if (garaXml.getURGENZADL133() != null)
					gara.setURGENZA_DL133(garaXml.getURGENZADL133().toString());
				else
					gara.setURGENZA_DL133(FlagSNType.N.toString());
			}
			// FINE TICKET MAC #8215

			if (SimogFlags.is30350_RFWSGL01Active()
					&& ConfigurationManager.getInstance().getSimogProperties().isEAGGAttivo(gara.getData_creazione())) {

				if (garaXml.getMOTIVORICHCIG() != null)
					gara.setCOD_MOTIVO_EAGG(Integer.valueOf(garaXml.getMOTIVORICHCIG()));

				if (garaXml.getMOTIVORICHCIGCOMUNI() != null)
					gara.setSCELTA_LEGGE89(Integer.valueOf(garaXml.getMOTIVORICHCIGCOMUNI()));

				if (garaXml.isSetCATEGORIEMERC()) {
					List<String> lista = new ArrayList<String>();

					for (int i = 0; i < garaXml.getCATEGORIEMERC().getCATEGORIAArray().length; i++) {
						lista.add(garaXml.getCATEGORIEMERC().getCATEGORIAArray()[i]);
					}
					gara.setCatMerc(lista);
				}

				// TICKET ALM #664
				if (garaXml.getSTRUMENTOSVOLGIMENTO() != null)
					gara.setID_SVOLGIMENTO(Integer.valueOf(garaXml.getSTRUMENTOSVOLGIMENTO()));
				// FINE TICKET ALM #664

				// TICKET ALM #3832
				if (garaXml.getESTREMAURGENZA() != null)
					gara.setID_ESTREMA_URGENZA(Integer.valueOf(garaXml.getESTREMAURGENZA()));
				// FINE TICKET ALM #3832

				// TICKET ALM #3834
				if (garaXml.getALLEGATOIX() != null)
					gara.setID_ALLEGATO_IX(Integer.valueOf(garaXml.getALLEGATOIX()));
				// FINE TICKET ALM #3834

				// TICKET ALM - 3.04.3
				if (garaXml.isSetDURATAACCQUADROCONVENZIONEGARA())
					gara.setDurataGiorni(garaXml.getDURATAACCQUADROCONVENZIONEGARA());
				else
					gara.setDurataGiorni(null);
				// TICKET ALM - 3.04.3

				// TICKET ALM #659 - 3.04.4
				if (garaXml.isSetFLAGSAAGENTEGARA())
					gara.setFlagSAAgente(garaXml.getFLAGSAAGENTEGARA().toString());
				if (garaXml.isSetIDFDELEGATE())
					gara.setID_F_DELEGATE(Integer.valueOf(garaXml.getIDFDELEGATE()));
				if (garaXml.isSetCFAMMAGENTEGARA())
					gara.setCF_AMM_AGENTE(garaXml.getCFAMMAGENTEGARA());
				// FINE TICKET ALM #659 - 3.04.4

				// imposto la tipologia della stazione appaltante per il controllo sui comuni
				BdncpManager bm = new BdncpManager(con, logger);

				String ret = "";
				if (!SimogFlags.isNUOVO_TIPOSAActive())
					ret = bm.loadTipoSA(collaborazioneSelezionata.getAzienda_codiceFiscale());
				else
					ret = bm.loadTipoSANew(collaborazioneSelezionata.getUfficio_id());

				gara.setTIPOSA_BDNCP(ret);
			}

			if (collaborazioneSelezionata != null && !isOperaComeOsservatorio) {
				// devo fare il controllo tra collaborazione e dati dentro xml bean
				// logger.debug(ObjectIntrospector.propertiesInfo(Collaborazione.class, coll));
				if (this.verificaOwnerShipDellaGara(collaborazioneSelezionata, garaXml)) {

					gara.setCF_AMMINISTRAZIONE(collaborazioneSelezionata.getAzienda_codiceFiscale());
					gara.setDENOM_STAZIONE_APPALTANTE(collaborazioneSelezionata.getUfficio_denominazione());
					gara.setDENOM_AMMINISTRAZIONE(collaborazioneSelezionata.getAzienda_denominazione());
					gara.setID_STAZIONE_APPALTANTE(collaborazioneSelezionata.getUfficio_id());
					gara.setID_OSSERVATORIO(collaborazioneSelezionata.getIdOsservatorio());

				} else {
					// logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, gara));
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);

				}
				// apertura osservatori regionali
			} else if (isOperaComeOsservatorio) {

				gara.setCF_UTENTE(garaXml.getCFUTENTE());
				gara.setCF_AMMINISTRAZIONE(garaXml.getCFAMMINISTRAZIONE());
				gara.setDENOM_STAZIONE_APPALTANTE(garaXml.getDENOMSTAZIONEAPPALTANTE());
				gara.setDENOM_AMMINISTRAZIONE(garaXml.getDENOMAMMINISTRAZIONE());
				gara.setID_STAZIONE_APPALTANTE(garaXml.getIDSTAZIONEAPPALTANTE());

				// condizione che non dovrebbe mai capitare dato che l'adminOr e' da tre
				// caratteri
				if (adminOr != null && adminOr.length() == 2)
					adminOr = "0" + adminOr;

				gara.setID_OSSERVATORIO(adminOr);
			} else {
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
			}

			if (gara.getCF_AMMINISTRAZIONE() != null && !"".equals(gara.getCF_AMMINISTRAZIONE())
					&& SimogProperties.getInstance().getUrlWsAusa() != null
					&& !"".equals(SimogProperties.getInstance().getUrlWsAusa())) {
				RicercaSAWS_Client ausaClient = new RicercaSAWS_Client();
				String codiceAusa = ausaClient.getCodAusaFromCF(gara.getCF_AMMINISTRAZIONE());
				if (!"".equals(codiceAusa))
					gara.setCodiceAusa(codiceAusa);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SimogWSException(e.getMessage());
		}
		validate(gara, ParametriServletGara.TAB_GARA);
		return gara;
	}

	// questo da xml type a bean per l'inserimento sul db
	/**
	 * metodo per la conversione da LottoType (bean xml) al bean Lotto param
	 * LottoType: lottoXml return Lotto throws SimogWSException
	 */
	private Lotto converti(LottoType lottoType) throws SimogWSException {

		Lotto lotto = new Lotto();
		try {
			if (lottoType.isSetCIG()) {
				lotto.setCig(CIGBean.getCIGPart(lottoType.getCIG()));
				lotto.setCig_kkk(CIGBean.getCIGKKK(lottoType.getCIG()));
			}

			// verificare gli enum
			lotto.setOggetto(lottoType.getOGGETTO());
			lotto.setSomma_Urgenza(
					lottoType.getSOMMAURGENZA() != null && !"".equals(lottoType.getSOMMAURGENZA().toString())
							? lottoType.getSOMMAURGENZA().toString().charAt(0)
							: 'N'); // ex enum
			lotto.setImporto_Lotto(lottoType.getIMPORTOLOTTO());
			lotto.setImporto_SA(lottoType.getIMPORTOSA());
			lotto.setImporto_Impresa(lottoType.getIMPORTOIMPRESA());
			lotto.setId_CPV(lottoType.getCPV());
			lotto.setId_Scelta_Contraente(lottoType.getIDSCELTACONTRAENTE().toString()); // ex enum

//	          if(SimogFlags.is3028_RFWEBGL00Active()){
//                 // normalizzazione scelta contraente
//                 if(lotto.getId_Scelta_Contraente() != null && !"".equals(lotto.getId_Scelta_Contraente().trim())){
//                    // separo i valori della scelta contraente se è personalizzata
//                    if(lotto.getId_Scelta_Contraente().length()>3){
//                       lotto.setID_SCELTA_OSS(new Long(lotto.getId_Scelta_Contraente().substring(3)));
//                       lotto.setId_Scelta_Contraente(new AccessiDB(con, logger).getSceltaContraenteAVCP(null, lotto.getID_SCELTA_OSS()));
//                    }
//                 }
//              }

			lotto.setId_Categoria_prevalente(lottoType.getIDCATEGORIAPREVALENTE().toString()); // ex enum
			lotto.setData_Pubblicazione(PageHelper.getFormattedCalendarDate(lottoType.getDATAPUBBLICAZIONE())); // ex da
																												// calendar
																												// a
																												// string
			lotto.setDataScadenzaPagamenti(PageHelper.getFormattedCalendarDate(lottoType.getDATASCADENZAPAGAMENTI()));
			lotto.setDataComunicazione(PageHelper.getFormattedCalendarDate(lottoType.getDATACOMUNICAZIONE()));
			lotto.setDataInibizionePagamento(PageHelper.getFormattedCalendarDate(lottoType.getDATAINIBPAGAMENTO()));
			lotto.setId_Tipologia(ParametriServlet.TIPOLOGIA_LAVORI_PUBBLICI); // PP fisso, campo non ngestito
			/**/
			if (lottoType.getTRIENNIOANNOINIZIO() > 0)
				lotto.setTriennio_anno_inizio(Integer.toString(lottoType.getTRIENNIOANNOINIZIO()));

			if (lottoType.getTRIENNIOANNOFINE() > 0)
				lotto.setTriennio_anno_fine(Integer.toString(lottoType.getTRIENNIOANNOFINE()));

			if (lottoType.getTRIENNIOPROGRESSIVO() > 0)
				lotto.setTriennio_progressivo(Integer.toString(lottoType.getTRIENNIOPROGRESSIVO()));

			lotto.setAnnuale_cui_mininf(lottoType.getANNUALECUIMININF());

			if (lottoType.getFLAGESCLUSO() != null) {
				lotto.setFLAG_ESCLUSO(String.valueOf(lottoType.getFLAGESCLUSO()));
			} else { // PP retrocompatibile
				lotto.setFLAG_ESCLUSO(FlagSNType.N.toString());
			}
			if (lottoType.getTIPOCONTRATTO() != null) {
				lotto.setTIPO_CONTRATTO_LOTTO(String.valueOf(lottoType.getTIPOCONTRATTO()));
			}
			if (lottoType.getIDESCLUSIONE() != null) {
				lotto.setID_ESCLUSIONE(Integer.parseInt(lottoType.getIDESCLUSIONE()));
			}

			// BANDI PP
			if (lottoType.getLUOGOISTAT() != null)
				lotto.setLUOGO_ISTAT(lottoType.getLUOGOISTAT());
			if (lottoType.getLUOGONUTS() != null)
				lotto.setLUOGO_NUTS(lottoType.getLUOGONUTS());
			if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null)
				lotto.setIMPORTO_ATTUAZIONE_SICUREZZA(lottoType.getIMPORTOATTUAZIONESICUREZZA());

			// PP B302.3.3
			if (lottoType.getCIGORIGINERIP() != null)
				lotto.setCIG_ORIGINE_RIP(lottoType.getCIGORIGINERIP());

			if (lottoType.getFLAGPREVEDERIP() != null)
				lotto.setFLAG_PREVEDE_RIP(String.valueOf(lottoType.getFLAGPREVEDERIP()));

			if (lottoType.getFLAGRIPETIZIONE() != null)
				lotto.setFLAG_RIPETIZIONE(String.valueOf(lottoType.getFLAGRIPETIZIONE()));

			// ATTENZIONE DATO DETERMINANTE PER LA VALIDAZIONE
			lotto.setId_Gara(this.id_gara); // altrimenti la validazione viene falsata
			HashMap<String, String> m = new HashMap<String, String>();

			if (lottoType.isSetCATEGORIE()) {
				String[] e = lottoType.getCATEGORIE().getCATEGORIAArray();
				for (int i = 0; i < e.length; i++) {
					logger.debug("enum categorie: " + e[i].toString());
					m.put(e[i].toString(), "");
				}
				lotto.setCategorieScorporabili(m);
			}

			// tipi appalto
			if (SimogFlags.is3031_RFWEBGL00Active()) {
				if (lottoType.getTipiAppaltoLavArray() != null) {
					List<TipoAppaltoAggBean> tipi = new ArrayList<TipoAppaltoAggBean>();
					for (int i = 0; i < lottoType.getTipiAppaltoLavArray().length; i++) {
						TipiAppaltoType elem = lottoType.getTipiAppaltoLavArray()[i];
						tipi.add(new TipoAppaltoAggBean(elem.getIDAPPALTO()));
					}
					lotto.setElencoTipoAppaltoLottoL(tipi);
				}
				if (lottoType.getTipiAppaltoFornArray() != null) {
					List<TipoAppaltoAggBean> tipi = new ArrayList<TipoAppaltoAggBean>();
					for (int i = 0; i < lottoType.getTipiAppaltoFornArray().length; i++) {
						TipiAppaltoType elem = lottoType.getTipiAppaltoFornArray()[i];
						tipi.add(new TipoAppaltoAggBean(elem.getIDAPPALTO()));
					}
					lotto.setElencoTipoAppaltoLottoF(tipi);
				}
			}
			if (SimogFlags.is3031_RFWEBGL02Active()) {
				// caricamento flag CUP e codici
				if (lottoType.isSetFLAGCUP())
					lotto.setFLAG_CUP(String.valueOf(lottoType.getFLAGCUP()));

				if (lottoType.isSetCUPLOTTO()) {
					List<CupLottoAggExt> cups = new ArrayList<CupLottoAggExt>();
					for (int i = 0; i < lottoType.getCUPLOTTO().getCODICICUPArray().length; i++) {
						DatiCUPType elem = lottoType.getCUPLOTTO().getCODICICUPArray()[i];
						CupLottoAggExt beanCup = new CupLottoAggExt();
						beanCup.setCup(elem.getCUP());
						if (elem.isSetOKUTENTE())
							beanCup.setOkUtente(String.valueOf(elem.getOKUTENTE()));

						cups.add(beanCup);
					}
					lotto.setElencoCup(cups);
				}

				// TICKET ALM #2845
				if (lottoType.getFLAGDL50() != null) {
					lotto.setFLAG_DL50(String.valueOf(lottoType.getFLAGDL50()));
				}
				if (lottoType.getPRIMAANNUALITA() != null) {
					lotto.setPRIMA_ANNUALITA(String.valueOf(lottoType.getPRIMAANNUALITA()));
				}
				// FINE TICKET ALM #2845

				// TICKET - 3.04.3 #2846
				if (lottoType.getIDMOTIVOCOLLCIG() != null) {
					lotto.setID_MOTIVO_COLL_CIG(lottoType.getIDMOTIVOCOLLCIG().toString());
				}
				// FINE TICKET - 3.04.3 #2846

				// TICKET ALM #3835
				if (lottoType.getIDAFFRISERVATI() != null) {
					lotto.setID_AFF_RISERVATI(Integer.parseInt(lottoType.getIDAFFRISERVATI()));
				}

				if (lottoType.getCondizioniArray() != null) {
					List<CondizioneLottoBean> list = new ArrayList<CondizioneLottoBean>();
					for (int i = 0; i < lottoType.getCondizioniArray().length; i++) {
						CondizioneLtType elem = lottoType.getCondizioniArray()[i];
						CondizioneLottoBean bean = new CondizioneLottoBean();
						bean.setIdCondizione(Long.parseLong(elem.getIDCONDIZIONE()));
						list.add(bean);
					}
					lotto.setCondizioni(list);
				}
				// FINE TICKET ALM #3835

				// TICKET ALM #3836
				if (lottoType.getFLAGREGIME() != null) {
					lotto.set_FLAG_REGIME(String.valueOf(lottoType.getFLAGREGIME()));
				}

				if (lottoType.getARTREGIME() != null) {
					lotto.setID_ART_REGIME(Integer.parseInt(lottoType.getARTREGIME()));
				}
				// FINE TICKET ALM #3836

				// TICKET ALM #4222 - 3.04.4
				if (lottoType.getCATEGORIAMERC() != null)
					lotto.setCOD_CATEGORIA(lottoType.getCATEGORIAMERC());
				// FINE TICKET ALM #4222 - 3.04.4

				// TICKET ALM #4219 - 3.04.4
				if (lottoType.getCPVSecondariaArray() != null) {
					List<CpvLotto> lista = new ArrayList<CpvLotto>();
					for (int i = 0; i < lottoType.getCPVSecondariaArray().length; i++) {
						CpvLotto cpvSec = new CpvLotto();
						CPVSecondariaType cpvSecType = lottoType.getCPVSecondariaArray()[i];
						cpvSec.setIdCpv(cpvSecType.getCODCPVSECONDARIA());
						lista.add(cpvSec);
					}
					lotto.setElencoCpvSecondarie(lista);
				}
				// FINE TICKET ALM #4219 - 3.04.4

				// TICKET ALM #4223-#4224 - 3.04.4
				if (lottoType.isSetFLAGNOADESIONEINIZIATIVA())
					lotto.setFlagNoAdesione(String.valueOf(lottoType.getFLAGNOADESIONEINIZIATIVA()));
				if (lottoType.isSetFLAGSANONCLASSIFICATA())
					lotto.setFlagSANonClass(String.valueOf(lottoType.getFLAGSANONCLASSIFICATA()));
				// FINE TICKET ALM - 3.04.4

				// TICKET ALM #13691 - 3.04.5
				if (lottoType.isSetIMPORTOOPZIONI())
					lotto.setImporto_opzioni(lottoType.getIMPORTOOPZIONI());

				// Simog 3.04.6
				if (lottoType.isSetDURATARINNOVI())
					lotto.setDurataRipetizioni(lottoType.getDURATARINNOVI());
				if (lottoType.isSetDURATAAFFIDAMENTO())
					lotto.setDurataAffidamentoGiorni(lottoType.getDURATAAFFIDAMENTO());

			}

			// Simog 3.04.7
			//lotto.setFLAG_PREVISIONE_QUOTA(String.valueOf(lottoType.getFLAGPREVISIONEQUOTA()));
			lotto.setFLAG_PNRR_PNC(String.valueOf(lottoType.getFLAGPNRRPNC()));
			if(lottoType.isSetFLAGPREVISIONEQUOTA())
				lotto.setFLAG_PREVISIONE_QUOTA(String.valueOf(lottoType.getFLAGPREVISIONEQUOTA()));
			if(lottoType.isSetQUOTAFEMMINILE())
				lotto.setQuotaFemminile(lottoType.getQUOTAFEMMINILE());
			if(lottoType.isSetQUOTAGIOVANILE())
				lotto.setQuotaGiovanile(lottoType.getQUOTAGIOVANILE());
			if(lottoType.isSetFLAGMISUREPREMIALI())
				lotto.setFLAG_MISURE_PREMIALI(String.valueOf(lottoType.getFLAGMISUREPREMIALI()));

			//MEV 37010 3.04.8.1
			if(lottoType.isSetFLAGDEROGAADESIONE())
				lotto.setFLAG_DEROGA_ADESIONE(String.valueOf(lottoType.getFLAGDEROGAADESIONE()));

			//MEV 38205 3.04.8.1
			if(lottoType.isSetFLAGUSOMETODIEDILIZIA())
				lotto.setFLAG_USO_METODI_EDILIZIA(String.valueOf(lottoType.getFLAGUSOMETODIEDILIZIA()));

			//3.04.9 MEV 40610
			if(lottoType.isSetDEROGAQUALIFICAZIONESA())
				lotto.setDerogaQualificazioneSA(String.valueOf(lottoType.getDEROGAQUALIFICAZIONESA()));
			
			if (lottoType.getMisuraPremialeArray() != null) {
				List<MisuraPremialeBean> m1 = new ArrayList<MisuraPremialeBean>();
				for (int i = 0; i < lottoType.getMisuraPremialeArray().length; i++) {
					MisuraPremialeBean mplBean = new MisuraPremialeBean(Long.parseLong(lottoType.getMisuraPremialeArray()[i]),null,null,null,null);
					m1.add(mplBean);
				}
				lotto.setElencoMisurePremiali(m1);
			}

			
			if (lottoType.getMotivoDerogaArray() != null) {
				List<MotivoDerogaBean> m2 = new ArrayList<MotivoDerogaBean>();
				for (int i = 0; i < lottoType.getMotivoDerogaArray().length; i++) {
					MotivoDerogaBean mdlBean = new MotivoDerogaBean(Long.parseLong(lottoType.getMotivoDerogaArray()[i]),null,null,null,null);
					m2.add(mdlBean);
				}
				lotto.setElencoMotivoDeroga(m2);
			}
			

	
		} catch (Exception e) {
			logger.error(e.getMessage());

			// e.printStackTrace();
		}

		validate(lotto, ParametriServletLotto.TAB_LOTTO);

		return lotto;
	}

	
	/**
	 * metodo per la validazione di gara e lotto, invoca il validatore di competenza
	 * 
	 * @param garaOrLotto
	 * @param nomeOggetto
	 * @throws SimogWSException
	 */
	private void validate(Object garaOrLotto, String nomeOggetto) throws SimogWSException {

		// PP imposto il flag per condizionare i validatori e non dare errori bloccanti
		// sui WS per i nuovi campi (pubblicazione bandi)
		SimogFlags.setFromWS(true);

		try {
			if (ParametriServletLotto.TAB_LOTTO.equals(nomeOggetto)) {
				LottoValidator validatore_lotto = new LottoValidator(con, logger);
				//boolean esito = validatore_lotto.valida(garaOrLotto, null);
				boolean esito = validatore_lotto.valida(garaOrLotto, null, "");
				GaraManager garaManager = new GaraManager(con, logger);
				Gara gara;
				try {
					gara = garaManager.getGara(id_gara);
				} catch (Exception e) {
					// e.printStackTrace();

					throw new SimogWSException(e.getMessage());
				}
				// categorie 0 - 1 quindi opzionali
				if (((Lotto) garaOrLotto).getCategorieScorporabili() != null) {
					esito &= validatore_lotto.validaCategorie(((Lotto) garaOrLotto).getCategorieScorporabili(), null,
							gara);
				}

				ConfigurationManager.getInstance().getSimogProperties();
				// postvalidazione nuove info CUP, solo se gara successiva a MEV
				if (SimogFlags.is3031_RFWEBGL02Active() && esito && gara.getData_creazione()
						.compareTo(SimogProperties.getInstance().getDataAttivazioneCup()) >= 0) {
					// Per ogni CUP verifico la situazione
					ElaborazioniCUPClient cli = new ElaborazioniCUPClient(
							ConfigurationManager.getInstance().getSimogProperties(), logger);
					Lotto lt = (Lotto) garaOrLotto;
					AllValidationBeans eccez = cli.validaCupDIPE(lt, true);
					if (eccez != null) {
						validatore_lotto.getEccezioni().add(eccez);
						esito = eccez.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;

						// valorizzo la struttura per restituire in response gli esiti DIPE
						// if(eccez.existMessage(Messaggi.SIMOG_VALIDAZIONE_226)){
						if (lt.getElencoCup() != null && lt.getElencoCup().size() > 0) {
							this.cuplotto = new CUPLOTTO();
							cuplotto.setCIG(lt.getCIG() + lt.getCIG_kkk());
							CodiciCup[] codici = new CodiciCup[lt.getElencoCup().size()];
							int i = 0;

							for (CupLottoAggExt elem : lt.getElencoCup()) {
								RichiestaCUP ret = null;
								try {
									ret = cli.getCUP(elem.getCup());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									// e.printStackTrace();
									logger.error(e.getMessage());

								}

								if (ret != null) {
									CodiciCup item = new CodiciCup();
									item.setCUP(elem.getCup());
									item.setID_RICHIESTA(String.valueOf(ret.getID_RICHIESTA()));
									item.setDATI_DIPE(ret.getESITO_RICHIESTA());
									item.setVALIDO(ret.getVALIDO() == null ? "N" : ret.getVALIDO());
									item.setOK_UTENTE(elem.getOkUtente() == null ? "N" : elem.getOkUtente());
									codici[i++] = item;
								}
							}
							cuplotto.setCODICICUP(codici);
						}
					}
				}

				if (!esito) {
					this.setSimogValidatorError(validatore_lotto);
				}

				this.thereIsAnError = !esito;
			} else {
				// oggetto validatore
				SimogValidator sv = ValidatorFactory.getValidator(nomeOggetto, con, logger);
				// esito validazione negato se valido la variabile e' false
				this.thereIsAnError = !sv.valida(garaOrLotto, null);
				// se ci sono errori
				if (this.thereIsAnError) {
					// invoca metodo per il settaggio della stringa locale errore
					this.setSimogValidatorError(sv);
				}
			}
		} catch (SimogException se) {
			throw new SimogWSException(se.getMessage());
		}
	}

//	private void validate (long idGara, List<RequisitoGara> reqList){
//       RequisitiGLValidator validatore = new RequisitiGLValidator(con, logger);
//       boolean esito = false;
//      try {
//         esito = validatore.valida(idGara, reqList, new Timestamp(PageHelper.getCurrentUtilDate().getTime()));
//      } catch (Exception e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//
//       if(!esito){
//           this.setSimogValidatorError(validatore);
//       }
//       this.thereIsAnError =  !esito;
//	}

	/**
	 * metodo che controlla la data (usa una librerie apache)
	 * 
	 * param String: data return boolean
	 */
	private boolean checkData(String data) {
		// return DateValidator.getInstance().isValid(data, "yyyymmdd", false);
		// PP era errato il formato della data
		return DateValidator.getInstance().isValid(data, "yyyyMMdd", true);
	}

	/**
	 * metodo per il settaggio degli errori del validatore nella variabile locale
	 * error in caso di fallimento della validazione, siccome potrebbero essere piu
	 * di uno tra l'uno e l'altro viene messo \n per andare a capo
	 * 
	 * param SimogValidator: simogValidator
	 */
	private void setSimogValidatorError(SimogValidator simogValidator) {
		// recupero il bean degli errori [NOTA: soli errori !!!]
		AllValidationBeans avb = simogValidator.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR);

		this.setSimogValidatorError(avb);
	}

	public boolean verificaOwnerShipDellaGara(Collaborazione coll, GaraType gt) {
		return gt.getCFAMMINISTRAZIONE().equals(coll.getAzienda_codiceFiscale())
				// PP 16.04.2009 commentata, troppo vincolante &&
				// gt.getDENOMSTAZIONEAPPALTANTE().equals(coll.getUfficio_denominazione())
				// PP 16.04.2009 commentata, troppo vincolante &&
				// gt.getDENOMAMMINISTRAZIONE().equals(coll.getAzienda_denominazione())
				&& gt.getIDSTAZIONEAPPALTANTE().equals(coll.getUfficio_id());
	}

	/**
	 * @return
	 */
	public String getError() {
		return error;
	}

	/**
	 * @return
	 */
	public boolean thereIsAnError() {
		return thereIsAnError;
	}

	/**
	 * @return
	 */
	public long getId_gara() {
		return id_gara;
	}

	/**
	 * @param id_gara
	 */
	public void setId_gara(long id_gara) {
		this.id_gara = id_gara;
	}

	/**
	 * Metodo che si occupa di creare una stringa con il dettaglio degli errori di
	 * validazione xsd.
	 * 
	 * @param errors
	 * @return
	 */
	private String aggiungiDescrizioneErroriValidazioneXsd(ArrayList<XmlError> errors) {

		String buff = "";
		for (XmlError error : errors) {
			if (error.getSeverity() == XmlError.SEVERITY_ERROR) {

				if (error.getCursorLocation().getName() != null)
					buff += error.getCursorLocation().getName() + " -> ";

				buff += (String) error.toString() + "\r\n";

				logger.debug(">> " + buff);
			}
		}
		return buff;
	}

	/**
	 * is3025_REQUISITIActive Metodo per l'aggiornamento dei requisiti di una gara
	 * gia' esistente
	 * 
	 * @param User_id
	 * @param indexCollaboraxione
	 * @param collaborazioniRssa
	 * @param id_gara
	 * @param garaXml
	 * @param isOperaComeOsservatorio
	 * @param adminOr
	 * @return
	 * @throws SimogWSException
	 */
	public boolean modificaRequisiti(String User_id, String indexCollaboraxione, CollaborazioniRssa collaborazioniRssa,
			String id_gara, RequisitiWSDocument garaXml, boolean isOperaComeOsservatorio, String adminOr)
			throws SimogWSException {
		if (con != null) {
			GaraManager garaManager = new GaraManager(con, logger);
			RequisitiGLManager reqMan = new RequisitiGLManager(con, logger);
			try {

				this.id_gara = Long.valueOf(id_gara);
				List<RequisitoGara> reqList = this.converti(garaXml, User_id,
						collaborazioniRssa != null ? collaborazioniRssa.getCollaborazione() : null,
						isOperaComeOsservatorio, adminOr);
				//MAC 37334 3.04.8.1
				//per accettare 999 come codice dettaglio nella request
				for (RequisitoGara req : reqList) {
					if (String.valueOf(req.getCodice_dettaglio()).equals(PSReq.CODICE_REQUISITO_NON_CODIFICATO)) {
						req.setCodice_dettaglio(33);
					}
				}
				//FINE MAC
				
				// carico la gara prima
				Gara gara = garaManager.getGara(Long.parseLong(id_gara));
				// caso in cui id_gara non sia valido (corretto)
				if (gara != null) {
					// apertura osservatorio
					if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

						Collaborazione collaborazioneSelezionata = null;
						if (!isOperaComeOsservatorio)
							collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

						TableBean tableBeanGara = null;
						if (!isOperaComeOsservatorio) {
							// costruzione sa abilitate per ricerca gara
							Hashtable<String, String> listaCodiciRssa = new Hashtable<String, String>();
							ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
							for (String stringa : elenco) {
								listaCodiciRssa.put(stringa, "");
							}
							tableBeanGara = garaManager.getDettagliGaraByIdGaraRSSA(id_gara, listaCodiciRssa);
						} else {
							tableBeanGara = garaManager.getDettagliGaraByIdGara(id_gara);
						}
						boolean modificabile = tableBeanGara.getFullSize() > 0
								&& "".equals(tableBeanGara.getNulledField(GARA.DATA_CANCELLAZIONE_GARA, 0));

						// gara esistente e non cancellata
						if (modificabile) {

							// check avcpass
							if (SimogFlags.is3030_RFWEBGL02Active()) {
								RequisitiGLValidator reqValidator = new RequisitiGLValidator(con, logger);
								boolean bloccoAdd = isAVCPass(gara, null,
										AVCPassFunzioneEnum.WS_REQUISITI_CREATE.getCodice());
								boolean bloccoMod = isAVCPass(gara, null,
										AVCPassFunzioneEnum.WS_REQUISITI_UPDATE.getCodice());
								boolean bloccoDel = isAVCPass(gara, null,
										AVCPassFunzioneEnum.WS_REQUISITI_DELETE.getCodice());
								boolean validoAVCPass = reqValidator.validaAVCPassPrivileges(gara.getId_Gara(), reqList,
										bloccoAdd, bloccoMod, bloccoDel);
								if (!validoAVCPass) {
									throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
								}
							} else if (SimogFlags.is3028_RFWEBGL07Active()) {
								// LottoManager lman = new LottoManager(con, logger);
								// List<Lotto> listaLotti = lman.getListaLotti(gara.getId_Gara());
								if (isAVCPass(gara, null, AVCPassFunzioneEnum.WS_REQUISITI_UPDATE.getCodice())) {
									throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
								}
							}

							if (SimogFlags.is3028_RFWEBGL02Active()) {
								// su gara pubblicata impossibile modificare requisiti
								if (gara.getDATA_PERFEZIONAMENTO_BANDO() != null)
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_31);
							}

							boolean okRequisiti = true; // per default abilitata gestione
							RequisitiGLValidator rqvl = new RequisitiGLValidator(con, logger);
							rqvl.validaRequisitoOE(rqvl.getImportoGara(tableBeanGara), gara.getData_creazione());

							okRequisiti = rqvl.getEsito().isRequisiti();

							if (!okRequisiti) {
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_37);
							} else {
								// validazione e salvataggio
								RequisitiGLAction rgla = new RequisitiGLAction(con, logger);
								this.aggiornaCodiciRequisiti(reqList, rgla, gara.getData_creazione());

								boolean scriviLog = rgla.updateRequisitiGaraWS(reqList, Long.parseLong(id_gara),
										// PP devo considerare la data creazione new
										// Timestamp(PageHelper.getCurrentUtilDate().getTime())
										PageHelper.parseTimeYMD(gara.getData_creazione()),
										!SimogFlags.is3028_RNFDBDT01Active());
								this.thereIsAnError = rgla.getEccezioni()
										.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() > 0;

								if (thereIsAnError) {
									this.setSimogValidatorError(
											rgla.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR));
								} else
									this.setSimogValidatorError(
											rgla.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_WARN));

								if (!thereIsAnError && scriviLog) {
									LogManager logManager = new LogManager(con, logger);
									logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
											gara.getID_STAZIONE_APPALTANTE(), User_id, "", LogManager.MOD_REQUISITI,
											gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));
								}
							}
							return !thereIsAnError;
						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
						}
					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_13);
					}
				} else {
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
				}
			} catch (NumberFormatException nfe) {
				logger.error(
						"eccezione occorsa durante la conversione di id_gara o dell'importo gara " + nfe.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_06);
			} catch (SQLException sqle) {
				// sqle.printStackTrace();
				logger.error("eccezione occorsa provando ad aggiornare una gara " + sqle.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);
			} catch (SimogWSException swe) {
				throw swe;
			} catch (Exception e) {
				logger.error("eccezione occorsa provando ad aggiornare una gara " + e.getMessage());
				// e.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);

			}
		} else {
			logger.error("uno degli oggetti necessari all'inserimento della gara risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
		}

	}

	private void setSimogValidatorError(AllValidationBeans allBySeverity) {
		// recupero la lista
		List<ValidationBean> l = allBySeverity.getAll();
		// itero
		StringBuilder sb = new StringBuilder();
		for (ValidationBean vb : l) {
			// aggiungo alla stringa locale gli errori
			sb.append(vb.getSeverity() + ": " + vb.getMessage()
					+ (vb.getElemento() > 0 ? " Elemento: " + vb.getElemento() + " \n " : ""));
			// this.error += "Severity: " + vb.getSeverity() + " Messaggio: " +
			// vb.getMessage()+ "Numero: " +vb.getElemento()+" \n ";
		}
		this.error = sb.toString();
	}

	/**
	 * metodo per convertire un bean xml(GaraType) al bean(Gara)
	 * 
	 * @param garaXml
	 * @param User_id
	 * @param collaborazioneSelezionata
	 * @param isOperaComeOsservatorio
	 * @param adminOr
	 * @return
	 * @throws SimogWSException
	 */
	private List<RequisitoGara> converti(RequisitiWSDocument garaXml, String User_id,
			Collaborazione collaborazioneSelezionata, boolean isOperaComeOsservatorio, String adminOr)
			throws SimogWSException {

		// questo si occupa di convertire da xmlbean a bean
		List<RequisitoGara> requisiti = new ArrayList<RequisitoGara>();

		try {
			RequisitiWS obj = garaXml.getRequisitiWS();
			// se esistono requisiti ...
			if (!obj.isNil() && obj.getRequisitoArray().length > 0) {
				for (int i = 0; i < obj.getRequisitoArray().length; i++) {
					ReqGaraType temp = obj.getRequisitoArray(i);
					RequisitoGara req = new RequisitoGara(0, new Long(temp.getCodiceDettaglio()), null,
							temp.getDescrizione(), temp.getValore(), temp.getFlagEsclusione().toString(),
							temp.getFlagComprovaOfferta().toString(), temp.getFlagAvvalimento().toString(),
							temp.getFlagBandoTipo().toString(), temp.getFlagRiservatezza().toString());

					// eventuali associazioni cig
					if (temp.getCIGArray().length > 0) {
						LottoManager lm = new LottoManager(con, logger);
						List<Long> idl = new ArrayList<Long>();
						for (int j = 0; j < temp.getCIGArray().length; j++) {
							List<Lotto> lotto = lm.getLottoByCigWS(temp.getCIGArray(j));
							// il lotto deve esistere, non deve essere cancellato e deve apprtenere alla
							// gara
							if (lotto.size() > 0 && lotto.get(0).getDATA_CANCELLAZIONE_LOTTO() == null
									&& lotto.get(0).getId_Gara() == id_gara)
								idl.add(lotto.get(0).getId_Lotto());
							else
								idl.add(0L); // condizione che va testata in validazione
						}
						req.setLotti_associati(idl);
					}

					if (temp.getDOCUMENTOArray().length > 0) {
						for (int j = 0; j < temp.getDOCUMENTOArray().length; j++) {
							ReqDocType tDoc = temp.getDOCUMENTOArray(j);

							RequisitoGara.Documento doc = req.new Documento(new Long(tDoc.getCodiceTipoDoc()),
									tDoc.getDescrizioneDocumento(), tDoc.getEmettitore(), tDoc.getFax(),
									tDoc.getTelefono(), tDoc.getMail(), tDoc.getMailPec());
							req.getDocumenti().add(doc);
						}
					}

					requisiti.add(req);
				}
			}
		} catch (Exception e) {
			throw new SimogWSException(e.getMessage());
		}

		// validate(requisiti);

		return requisiti;
	}

	/**
	 * Aggiorna il codice dettaglio di ogni requisito ed il codice di ogni documento
	 * presente all'interno del requisito.
	 * 
	 * @param reqList
	 * @param rgla
	 * @param dataRif
	 * @throws ActionException
	 */
	private void aggiornaCodiciRequisiti(List<RequisitoGara> reqList, RequisitiGLAction rgla, String dataRif)
			throws ActionException {
		Map<String, String> mappaRelazioneCodici = rgla.getCodiceDettaglioReqMap(PageHelper.parseTimeYMD(dataRif));
		Map<String, String> mappaRelazioneCodiciDocumento = rgla
				.getCodiceTipoDocReqMap(PageHelper.parseTimeYMD(dataRif));

		for (RequisitoGara currentRequisito : reqList) {
			String codiceDettaglioRequisito = Long.toString(currentRequisito.getCodice_dettaglio());
			String codiceRequisito = mappaRelazioneCodici.get(codiceDettaglioRequisito);
			currentRequisito.setCodice(codiceRequisito);

			for (RequisitoGara.Documento currentDocumento : currentRequisito.getDocumenti()) {
				String codiceTipoDocRequisito = Long.toString(currentDocumento.getCodice_tipo_doc());
				String codiceTipoDoc = mappaRelazioneCodiciDocumento.get(codiceTipoDocRequisito);
				currentDocumento.setCodice(codiceTipoDoc);
			}
		}
	}

	/*
	 * verifica se la gara è in gestione di AVCPASS
	 */
	public boolean isAVCPass(Gara gara, List<Lotto> lotti, String codiceFunzione) {
		boolean retVal = false;
		/* CHECK BLOCCO AVCPASS */
		if (SimogFlags.is3028_RFWEBGL07Active()) {
			// verifico se posso modificare i dati (blocco avcpass)
			// richiamo il servizio AVCPASS
			try {
				AVCPassAction avpa = new AVCPassAction(con, logger,
						ConfigurationManager.getInstance().getSimogProperties());

				retVal = avpa.isAVCPass(gara, lotti, codiceFunzione);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.fatal(e.getMessage());
			}
		}
		// TECHNIS REMOVE
		return retVal;
		// return false;
	}

//   public boolean isAVCPass(Gara gara, List<Lotto> lotti){
//      return isAVCPass(gara, lotti, null);
//   }

	/**
	 * Metodo per la presa in carico di una gara
	 * 
	 * @param User_id
	 * @param indexCollaboraxione
	 * @param collaborazioniRssa
	 * @param id_gara
	 * @param isOperaComeOsservatorio
	 * @param adminOr
	 * @param estremi
	 * @return
	 * @throws SimogWSException
	 */
	public boolean presaInCarico(String User_id, String indexCollaboraxione, CollaborazioniRssa collaborazioniRssa,
			String id_gara, boolean isOperaComeOsservatorio, String adminOr, String estremi) throws SimogWSException {

		if (con != null) {
			GaraManager garaManager = new GaraManager(con, logger);
			try {

				// carico la gara prima
				Gara gara = garaManager.getGara(Long.parseLong(id_gara));

				// caso in cui id_gara non sia valido (corretto)
				if (gara != null) {
					// apertura osservatorio
					if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

						Collaborazione collaborazioneSelezionata = null;
						if (!isOperaComeOsservatorio)
							collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

						TableBean tableBeanGara = null;
						if (!isOperaComeOsservatorio) {
							// costruzione sa abilitate per ricerca gara
							Hashtable<String, String> listaCodiciRssa = new Hashtable<String, String>();
							ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
							for (String stringa : elenco) {
								listaCodiciRssa.put(stringa, "");
							}
							tableBeanGara = garaManager.getDettagliGaraByIdGaraRSSA(id_gara, listaCodiciRssa);
						} else {
							tableBeanGara = garaManager.getDettagliGaraByIdGara(id_gara);
						}

						if ((gara.getID_STATO_GARA() == StatiScheda.IN_DEFINIZIONE
								|| gara.getID_STATO_GARA() == StatiScheda.CONFERMATO)) {

							gara.setCF_UTENTE(User_id);
							gara.setPROVV_PRESA_CARICO(estremi);

							// salvo
							garaManager.updateGaraPresaInCarico(gara);

							LogManager logManager = new LogManager(con, logger);
							logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
									gara.getID_STAZIONE_APPALTANTE(), User_id, "", LogManager.PRESA_CARICO,
									gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));

							return true;
							// altrimenti
						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_05);
						}
					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_13);
					}
				} else {
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
				}
			} catch (NumberFormatException nfe) {
				logger.error(
						"eccezione occorsa durante la conversione di id_gara o dell'importo gara " + nfe.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_06);
			} catch (SQLException sqle) {
				// sqle.printStackTrace();
				logger.error("eccezione occorsa provando ad aggiornare una gara " + sqle.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);
			} catch (SimogWSException swe) {
				throw swe;
			} catch (Exception e) {
				logger.error("eccezione occorsa provando ad aggiornare una gara " + e.getMessage());
				// e.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);

			}
		} else {
			logger.error("uno degli oggetti necessari all'inserimento della gara risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
		}

	}

	/**
	 * metodo per la integrazione dei dati CUP
	 * 
	 * @param dati                    LottoWSDocument: dati, xmlbean
	 * @param User_id
	 * @param collaborazioniRssa
	 * @param cig
	 * @param isOperaComeOsservatorio
	 * @return
	 * @throws SimogWSException
	 */
	public boolean integraDatiCUP(Lotto dati, String User_id, CollaborazioniRssa collaborazioniRssa, String cig,
			boolean isOperaComeOsservatorio) throws SimogWSException {
		Lotto localLotto = null;
		LottoManager lottoManager = new LottoManager(con, logger);
		try {

			localLotto = lottoManager.getLottoByCigWS(cig).get(0);
		} catch (Exception e) {
			logger.debug("Impossibile recuperare la copia locale del lotto, per il confronto: " + e.getMessage());
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
		}
		this.id_gara = localLotto.getId_Gara();

		// sovrascrivo i dati CUP a quelli letti sul DB per le vadliazioni
		localLotto.setFLAG_CUP(dati.getFLAG_CUP());
		localLotto.setElencoCup(dati.getElencoCup());
		localLotto.setElencoTipoAppaltoLottoL(dati.getElencoTipoAppaltoLottoL());
		localLotto.setElencoTipoAppaltoLottoF(dati.getElencoTipoAppaltoLottoF());

		Collaborazione collaborazioneSelezionata = null;
		if (!isOperaComeOsservatorio)
			collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

		GaraManager garaManager = new GaraManager(con, logger);

		try {
			Gara gara = garaManager.getGara(localLotto.getId_Gara());
			if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
				// confronta gara->collaborazioni(must be equals)

				// apertura osservatorio
				if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

					if (!isOperaComeOsservatorio) {
						// controllo se e' modificabile
						Hashtable listaCodiciRssa = new Hashtable();
						ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
						for (String stringa : elenco) {
							listaCodiciRssa.put(stringa, "");
						}

						// qui va fatto anche il controllo che la gara sia ok per
						// l'integrazione CUP, ignoro lo stato del flagCUP per consentire
						// ulteriori invii

						// PP modificata data attivazione per coprire l'intervallo att_MEV - ATT_MEVWS
						// prima era
						// ConfigurationManager.getInstance().getSimogProperties().getDataAttivazioneCup(),

// 19.08.2014 secondo Piccinini non è corretto, deve essere sempre consentita l'integrazione                  
//                  GaraManager gm = new GaraManager(con, logger);
//                  TableBean ret = gm.getElencoCigIntegrazioneCup(null, 
//                        User_id, listaCodiciRssa, null, null, 
//                        ConfigurationManager.getInstance().getSimogProperties().getDataAttivazioneCupWs(), 
//                        null, false, 0, 1, cig, true);
//                       
//                  // la gara non soddisfa i requisiti per l'integrazione cup
//                  if(ret.getFullSize() == 0)
//                     throw new SimogWSException(Messaggi.SIMOG_VALIDAZIONE_007.replace("$1", "ERRORE non sono soddisfatti i criteri per la funzione di integrazione"));

						if ((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null
								|| "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO()))
								&& (localLotto.getDATA_INIB_PAGAMENTO() == null
										|| "".equals(localLotto.getDATA_INIB_PAGAMENTO()))) {

							// check avcpass non serve
//                        if(SimogFlags.is3028_RFWEBGL07Active()){
//                            List<Lotto> listaLotti = new ArrayList<Lotto>();
//                            listaLotti.add(localLotto);                          
//                           if(isAVCPass(null, listaLotti, AVCPassFunzioneEnum.WS_LOTTO_UPDATE.getCodice())){
//                              throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
//                           }
//                        }

							// validazione
							LottoValidator lv = new LottoValidator(con, logger);

							boolean esito = lv.valida(localLotto, ParametriCup.ACTION_MODIFICA_DATI_CUP);

							if (esito) {
								ElaborazioniCUPClient cli = new ElaborazioniCUPClient(
										ConfigurationManager.getInstance().getSimogProperties(), logger);
								AllValidationBeans eccez = cli.validaCupDIPE(localLotto, true);
								if (eccez != null) {
									lv.getEccezioni().add(eccez);
									esito = eccez.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
								}
							}

							// valorizzo la struttura per restituire in response gli esiti DIPE
							ElaborazioniCUPClient cli = new ElaborazioniCUPClient(
									ConfigurationManager.getInstance().getSimogProperties(), logger);
							if (localLotto.getElencoCup() != null && localLotto.getElencoCup().size() > 0) {
								this.cuplotto = new CUPLOTTO();
								cuplotto.setCIG(localLotto.getCIG() + localLotto.getCIG_kkk());
								CodiciCup[] codici = new CodiciCup[localLotto.getElencoCup().size()];
								int i = 0;

								for (CupLottoAggExt elem : localLotto.getElencoCup()) {
									RichiestaCUP retx = null;
									try {
										retx = cli.getCUP(elem.getCup());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										// e.printStackTrace();
										logger.error(e.getMessage());

									}

									if (retx != null) {
										CodiciCup item = new CodiciCup();
										item.setCUP(elem.getCup());
										item.setID_RICHIESTA(String.valueOf(retx.getID_RICHIESTA()));
										item.setDATI_DIPE(retx.getESITO_RICHIESTA());
										item.setVALIDO(retx.getVALIDO());
										item.setOK_UTENTE(elem.getOkUtente() == null ? "N" : elem.getOkUtente());
										codici[i++] = item;
									}
								}
								cuplotto.setCODICICUP(codici);
							}

							if (esito) {

								CupLottoAggAction cla = new CupLottoAggAction(con, logger);

								cla.updateDatiCupLotto(localLotto);
								/**
								 * PP usata la action lottoManager.updateFlagCup(localLotto);
								 * 
								 * boolean confermato = false; if(localLotto.getData_Pubblicazione() != null &&
								 * !localLotto.getData_Pubblicazione().equals("")) confermato = true;
								 * 
								 * if( SimogFlags.is3031_RFWEBGL02Active() ){ CupLottoAggManager cupMan = new
								 * CupLottoAggManager(con,logger); cupMan.settingIdLotto(localLotto);
								 * cupMan.updateElencoCup(localLotto, confermato ); }
								 * 
								 * if( SimogFlags.is3031_RFWEBGL00Active() ){ TipoAppaltoManager talMan = new
								 * TipoAppaltoManager(con, logger); talMan.settingIdLotto(localLotto);
								 * talMan.aggiornaTipoAppaltoLotto(localLotto, confermato); }
								 */
								LogManager logManager = new LogManager(con, logger);
								logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
										gara.getID_STAZIONE_APPALTANTE(), User_id,
										localLotto.getCIG() + localLotto.getCIG_kkk(), LogManager.INT_CUP,
										gara.getCF_AMMINISTRAZIONE(), Long.toString(localLotto.getId_Lotto()),
										String.valueOf(localLotto.getId_Gara()));

								return true;

							} else {
								this.setSimogValidatorError(lv);
								this.thereIsAnError = true;
								return false;
							}

						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_12);
						}
					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
					}
				} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
					// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
					// NON
					// siano previste evoluzioni future in questo senso si puo levare
// PP                if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//                      && ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//                      && ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//                      && ( localLotto.getData_Pubblicazione() == null || "".equals(localLotto.getData_Pubblicazione()))){
//                   lotto.setId_Lotto(localLotto.getId_Lotto());             
//                   lm.modificaLotto(lotto);
//                   this.updateCategorieScorporabili(lotto.getCategorieScorporabili(), localLotto.getId_Lotto(), lm);
//                   return true;   
//                }else{ throw new SimogWSException("il lotto non si trova in uno stato che permetta la modifica!"); }
				}
			}
		} catch (SimogWSException swe) {
			throw swe;
		} catch (SQLException sqle) {
			logger.error("errore nel recupero del lotto da modificare");
			// sqle.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + sqle.getMessage());
		} catch (Exception e) {
			logger.error("errore nel recupero del lotto da modificare");
			// e.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + e.getMessage());
		}
		return false;
	}
	
	//MEV 37010 3.04.8.1
	public boolean integraDatiPariOpportunita(Lotto dati, String User_id, CollaborazioniRssa collaborazioniRssa, String cig,
			boolean isOperaComeOsservatorio) throws SimogWSException {
		
		
		Lotto localLotto = null;
		LottoManager lottoManager = new LottoManager(con, logger);
		GaraManager garaManager = new GaraManager(con, logger);
		
		try {
			
			localLotto = lottoManager.getLottoByCigWS(cig).get(0);
		} catch (Exception e) {
			logger.debug("Impossibile recuperare la copia locale del lotto, per il confronto: " + e.getMessage());
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
		}
		this.id_gara = localLotto.getId_Gara();
		
		
		
		Collaborazione collaborazioneSelezionata = null;
		if (!isOperaComeOsservatorio)
			collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

		

		try {
			Gara gara = garaManager.getGara(localLotto.getId_Gara());
			//controllo se la funzione non attiva
			String currentDate = PageHelper.getCurrentDate();
			if (currentDate.compareTo(SimogProperties.getInstance().getDataAttivazioneMev37010()) >= 0) {
//				// MEV 46937 3.04.12
//				if (gara.getID_MODO_REAL() == Costanti.MODOREAL_ACCORDO_QUADRO ||
//						gara.getID_MODO_REAL() == Costanti.MODOREAL_CONVENZIONE ||
//						gara.getID_MODO_REAL() == Costanti.MODOREAL_ACCORDO) {
				// FINE MEV 46937 3.04.12
//					if (gara.getDataInizioPubblicazione() != null && SimogProperties.getInstance()
//							.isDataCreatedAfterDerogaAdesione(PageHelper.getFormattedDBDateTime(gara.getDataInizioPubblicazione().toString()).substring(0,8)) 
//							&&
//							SimogProperties.getInstance()
//							.isDataCreatedBefore3047(PageHelper.getFormattedDBDateTime(gara.getDataInizioPubblicazione().toString()).substring(0,8))
//							) 
					if(gara.getData_creazione() != null && !gara.getData_creazione().equals("") && SimogProperties.getInstance().isDataCreatedBefore3047(gara.getData_creazione())){
							// validazione dati ed integrazione sul db 
						if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
							// confronta gara->collaborazioni(must be equals)

							// apertura osservatorio
							if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

								if (!isOperaComeOsservatorio) {
									// controllo se e' modificabile
									Hashtable listaCodiciRssa = new Hashtable();
									ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
									for (String stringa : elenco) {
										listaCodiciRssa.put(stringa, "");
									}

									

									if ((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null
											|| "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO()))
											&& (localLotto.getDATA_INIB_PAGAMENTO() == null
													|| "".equals(localLotto.getDATA_INIB_PAGAMENTO()))) {

										

										//--------------------------------------------------------------------------
										 //MEV 37010 3.04.8.1

										//MEV 37010 3.04.8.1
										localLotto.setFLAG_PNRR_PNC(dati.getFLAG_PNRR_PNC());
										if (dati.getFLAG_DEROGA_ADESIONE() != null) {
											localLotto.setFLAG_DEROGA_ADESIONE(dati.getFLAG_DEROGA_ADESIONE()); //MEV 37010 3.04.8.1
										}
										localLotto.setFLAG_PREVISIONE_QUOTA(dati.getFLAG_PREVISIONE_QUOTA());
										localLotto.setFLAG_MISURE_PREMIALI(dati.getFLAG_MISURE_PREMIALI());
										localLotto.setQuotaGiovanile(dati.getQuotaGiovanile());
										localLotto.setQuotaFemminile(dati.getQuotaFemminile());
										
										
										MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(con, logger);
										MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(con, logger);
										
										motivoDerogaManager.updateDataFineValiditaMotivoDerogaLotto(localLotto.getId_Lotto());
										misuraPremialeManager.updateDataFineValiditaMisuraPremialeLotto(localLotto.getId_Lotto());
										localLotto.setElencoMotivoDeroga(dati.getElencoMotivoDeroga());
										localLotto.setElencoMisurePremiali(dati.getElencoMisurePremiali());
										//FINE MEV 37010 3.04.8.1
										
										//MEV 37010 3.04.8.1 sovrascrivo i dati integrati delle pari opportunità ai CIG figli se ci sono
										GaraManager gm = new GaraManager(con, logger);
										//mi prendo la lista dei lotti delle gare di adesione all'AQ
										List<Lotto> LottiAdesioni = lottoManager.getLottiAdesioniByCigAQ(localLotto.getCIG()+localLotto.getCIG_kkk());
										
										if (!LottiAdesioni.isEmpty()) {
											for (Lotto lottoAdesione : LottiAdesioni) {
												
												
												lottoAdesione.setFLAG_PNRR_PNC(dati.getFLAG_PNRR_PNC());
												if (dati.getFLAG_DEROGA_ADESIONE() != null) {
													lottoAdesione.setFLAG_DEROGA_ADESIONE(dati.getFLAG_DEROGA_ADESIONE()); //MEV 37010 3.04.8.1
												}
												lottoAdesione.setFLAG_PREVISIONE_QUOTA(dati.getFLAG_PREVISIONE_QUOTA());
												lottoAdesione.setFLAG_MISURE_PREMIALI(dati.getFLAG_MISURE_PREMIALI());
												lottoAdesione.setQuotaGiovanile(dati.getQuotaGiovanile());
												lottoAdesione.setQuotaFemminile(dati.getQuotaFemminile());

												
												
												motivoDerogaManager.updateDataFineValiditaMotivoDerogaLotto(localLotto.getId_Lotto());
												misuraPremialeManager.updateDataFineValiditaMisuraPremialeLotto(localLotto.getId_Lotto());
												lottoAdesione.setElencoMotivoDeroga(dati.getElencoMotivoDeroga());
												lottoAdesione.setElencoMisurePremiali(dati.getElencoMisurePremiali());
												
												motivoDerogaManager.createMotivoDerogaLottoRelation(lottoAdesione.getId_Lotto(), lottoAdesione.getElencoMotivoDeroga());
												misuraPremialeManager.createMisuraPremialeLottoRelation(lottoAdesione.getId_Lotto(),lottoAdesione.getElencoMisurePremiali());
												lottoManager.updatePariOpportunita(lottoAdesione);
											}
										}
										//FINE MEV 37010 3.04.8.1 sovrascrivo i dati integrati delle pari opportunità ai CIG figli se ci sono
										
										
										

										LottoValidator lv = new LottoValidator(con, logger);
										boolean esito = lv.valida(localLotto, ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA);

										

										if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

											// messaggi da ritornare
											AllValidationBeans msgs = new AllValidationBeans();

//											String idGara = request.getParameter(SESSION_ID_GARA);
											//Gara gara = gm.getGara(Long.parseLong(idGara));

											

											//MEV 37010 3.04.8.1
											motivoDerogaManager.createMotivoDerogaLottoRelation(localLotto.getId_Lotto(), localLotto.getElencoMotivoDeroga());
											misuraPremialeManager.createMisuraPremialeLottoRelation(localLotto.getId_Lotto(),localLotto.getElencoMisurePremiali());
											lottoManager.updatePariOpportunita(localLotto);
											//MEV 37010 3.04.8.1

											
										} 
									
									
										//--------------------------------------------------------------------------

										if (esito) {

//											CupLottoAggAction cla = new CupLottoAggAction(con, logger);
			//
//											cla.updateDatiCupLotto(localLotto);
											
											LogManager logManager = new LogManager(con, logger);
											logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
													gara.getID_STAZIONE_APPALTANTE(), User_id,
													localLotto.getCIG() + localLotto.getCIG_kkk(), LogManager.INT_PARI_OPPORTUNITA,
													gara.getCF_AMMINISTRAZIONE(), Long.toString(localLotto.getId_Lotto()),
													String.valueOf(localLotto.getId_Gara()));

											return true;

										} else {
											this.setSimogValidatorError(lv);
											this.thereIsAnError = true;
											return false;
										}

									} else {
										throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_12);
									}
								} else {
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
								}
							} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
								// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
								// NON
								// siano previste evoluzioni future in questo senso si puo levare
			// PP                if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//			                      && ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//			                      && ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//			                      && ( localLotto.getData_Pubblicazione() == null || "".equals(localLotto.getData_Pubblicazione()))){
//			                   lotto.setId_Lotto(localLotto.getId_Lotto());             
//			                   lm.modificaLotto(lotto);
//			                   this.updateCategorieScorporabili(lotto.getCategorieScorporabili(), localLotto.getId_Lotto(), lm);
//			                   return true;   
//			                }else{ throw new SimogWSException("il lotto non si trova in uno stato che permetta la modifica!"); }
							}
						}

					}else {
						throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_VALIDAZIONE_000);
					}
//				}else {
//					throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_VALIDAZIONE_000);
//				}
			}else {
				throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_VALIDAZIONE_000);
			}
			
		} catch (SimogWSException swe) {
			throw swe;
		} catch (SQLException sqle) {
			logger.error("errore nel recupero del lotto da modificare");
			// sqle.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + sqle.getMessage());
		} catch (Exception e) {
			logger.error("errore nel recupero del lotto da modificare");
			// e.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + e.getMessage());
		}
		return false;
	}
	//FINE MEV 37010 3.04.8.1
	
	//MEV 3.04.10 43227
		public boolean modificaDatiPerfezionamento(Lotto dati, String User_id, CollaborazioniRssa collaborazioniRssa, String cig,
				boolean isOperaComeOsservatorio) throws SimogWSException {
			
			
			Lotto localLotto = null;
			LottoManager lottoManager = new LottoManager(con, logger);
			GaraManager garaManager = new GaraManager(con, logger);
			
			try {
				
				localLotto = lottoManager.getLottoByCigWS(cig).get(0);
			} catch (Exception e) {
				logger.debug("Impossibile recuperare la copia locale del lotto, per il confronto: " + e.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
			}
			this.id_gara = localLotto.getId_Gara();
			
			
			
			Collaborazione collaborazioneSelezionata = null;
			if (!isOperaComeOsservatorio)
				collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

			

			try {
				Gara gara = garaManager.getGara(localLotto.getId_Gara());
				//controllo se la funzione non attiva
				String currentDate = PageHelper.getCurrentDate();
				//////////////////////////////////////////////////////////
				//MEV 3.04.10 43227
				
				
				//se proc ristretta e fase 2 
				if ((localLotto.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(localLotto.getDATA_SCADENZA_PAGAMENTI()))
						&&(localLotto.getId_Scelta_Contraente().equals("2") || localLotto.getId_Scelta_Contraente().equals("13") || localLotto.getId_Scelta_Contraente().equals("25"))
						&& (localLotto.getDataScadenzaRichiestaInvito() != null && !"".equals(localLotto.getDataScadenzaRichiestaInvito()))
						&& (localLotto.getDataLetteraInvito() != null && !"".equals(localLotto.getDataLetteraInvito()))) {
					//la funzione è attiva solo se la data odierna è precedente o uguale alla data di scadenza pagamenti
					if (localLotto.getDATA_SCADENZA_PAGAMENTI() != null && 
							currentDate.compareTo(localLotto.getDATA_SCADENZA_PAGAMENTI()) <= 0) {

						// validazione dati ed integrazione sul db 
					if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
						// confronta gara->collaborazioni(must be equals)

						// apertura osservatorio
						if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

							if (!isOperaComeOsservatorio) {
								// controllo se e' modificabile
								Hashtable listaCodiciRssa = new Hashtable();
								ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
								for (String stringa : elenco) {
									listaCodiciRssa.put(stringa, "");
								}

								

								if ((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null
										|| "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO()))
										&& (localLotto.getDATA_INIB_PAGAMENTO() == null
												|| "".equals(localLotto.getDATA_INIB_PAGAMENTO()))) {

									

									//--------------------------------------------------------------------------
									 //MEV 3.04.10 43227

									//MEV 3.04.10 43227
									if (dati.getDATA_SCADENZA_PAGAMENTI() != null) {
										localLotto.setDataScadenzaPagamenti(dati.getDATA_SCADENZA_PAGAMENTI()); 
									}
									if (dati.getORA_SCADENZA() != null) {
										localLotto.setORA_SCADENZA(dati.getORA_SCADENZA()); 
									}
									if (dati.getDataScadenzaRichiestaInvito() != null) {
										localLotto.setDataScadenzaRichiestaInvito(dati.getDataScadenzaRichiestaInvito()); 
										throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_MODIFICA_PERFEZIONAMENTO_001);
									}
									
									//FINE MEV 3.04.10 43227
									
									
									
									
									

									LottoValidator lv = new LottoValidator(con, logger);
									boolean esito = lv.valida(localLotto, ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO);

									

									if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

										// messaggi da ritornare
										AllValidationBeans msgs = new AllValidationBeans();

//										String idGara = request.getParameter(SESSION_ID_GARA);
										//Gara gara = gm.getGara(Long.parseLong(idGara));

										

										
										lottoManager.updateModificaDatiPerfezionamento(localLotto);
										//MEV 3.04.10 43227

										
									} 
								
								
									//--------------------------------------------------------------------------

									if (esito) {

//										CupLottoAggAction cla = new CupLottoAggAction(con, logger);
		//
//										cla.updateDatiCupLotto(localLotto);
										
										LogManager logManager = new LogManager(con, logger);
										logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
												gara.getID_STAZIONE_APPALTANTE(), User_id,
												localLotto.getCIG() + localLotto.getCIG_kkk(), LogManager.INT_MODIFICA_DATI_PERFEZIONAMENTO,
												gara.getCF_AMMINISTRAZIONE(), Long.toString(localLotto.getId_Lotto()),
												String.valueOf(localLotto.getId_Gara()));

										return true;

									} else {
										this.setSimogValidatorError(lv);
										this.thereIsAnError = true;
										return false;
									}

								} else {
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_12);
								}
							} else {
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
							}
						} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
							// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
							// NON
							// siano previste evoluzioni future in questo senso si puo levare
		// PP                if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//		                      && ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//		                      && ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//		                      && ( localLotto.getData_Pubblicazione() == null || "".equals(localLotto.getData_Pubblicazione()))){
//		                   lotto.setId_Lotto(localLotto.getId_Lotto());             
//		                   lm.modificaLotto(lotto);
//		                   this.updateCategorieScorporabili(lotto.getCategorieScorporabili(), localLotto.getId_Lotto(), lm);
//		                   return true;   
//		                }else{ throw new SimogWSException("il lotto non si trova in uno stato che permetta la modifica!"); }
						}
					}

				
					}else {
						throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_VALIDAZIONE_000);
					}
					//se proc ristretta fase 1
				}else if ((localLotto.getDataScadenzaRichiestaInvito() != null && !"".equals(localLotto.getDataScadenzaRichiestaInvito()))
						&&(localLotto.getId_Scelta_Contraente().equals("2") || localLotto.getId_Scelta_Contraente().equals("13") || localLotto.getId_Scelta_Contraente().equals("25"))) {
					//la funzione è attva solo se la data odierna è precedente o uguale alla data di scadenza richiesta invito
					if (localLotto.getDataScadenzaRichiestaInvito() != null && 
							currentDate.compareTo(localLotto.getDataScadenzaRichiestaInvito()) <= 0) {


						// validazione dati ed integrazione sul db 
					if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
						// confronta gara->collaborazioni(must be equals)

						// apertura osservatorio
						if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

							if (!isOperaComeOsservatorio) {
								// controllo se e' modificabile
								Hashtable listaCodiciRssa = new Hashtable();
								ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
								for (String stringa : elenco) {
									listaCodiciRssa.put(stringa, "");
								}

								

								if ((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null
										|| "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO()))
										&& (localLotto.getDATA_INIB_PAGAMENTO() == null
												|| "".equals(localLotto.getDATA_INIB_PAGAMENTO()))) {

									

									//--------------------------------------------------------------------------
									 //MEV 3.04.10 43227

									//MEV 3.04.10 43227
									if (dati.getDATA_SCADENZA_PAGAMENTI() != null) {
										localLotto.setDataScadenzaPagamenti(dati.getDATA_SCADENZA_PAGAMENTI()); 
										throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_MODIFICA_PERFEZIONAMENTO_002);
									}
									if (dati.getORA_SCADENZA() != null) {
										localLotto.setORA_SCADENZA(dati.getORA_SCADENZA()); 
										throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_MODIFICA_PERFEZIONAMENTO_003);
									}
									if (dati.getDataScadenzaRichiestaInvito() != null) {
										localLotto.setDataScadenzaRichiestaInvito(dati.getDataScadenzaRichiestaInvito()); 
									}
									
									//FINE MEV 3.04.10 43227
									
									
									
									
									

									LottoValidator lv = new LottoValidator(con, logger);
									boolean esito = lv.valida(localLotto, ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO);

									

									if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

										// messaggi da ritornare
										AllValidationBeans msgs = new AllValidationBeans();

//										String idGara = request.getParameter(SESSION_ID_GARA);
										//Gara gara = gm.getGara(Long.parseLong(idGara));

										

										
										lottoManager.updateModificaDatiPerfezionamento(localLotto);
										//MEV 3.04.10 43227

										
									} 
								
								
									//--------------------------------------------------------------------------

									if (esito) {

//										CupLottoAggAction cla = new CupLottoAggAction(con, logger);
		//
//										cla.updateDatiCupLotto(localLotto);
										
										LogManager logManager = new LogManager(con, logger);
										logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
												gara.getID_STAZIONE_APPALTANTE(), User_id,
												localLotto.getCIG() + localLotto.getCIG_kkk(), LogManager.INT_MODIFICA_DATI_PERFEZIONAMENTO,
												gara.getCF_AMMINISTRAZIONE(), Long.toString(localLotto.getId_Lotto()),
												String.valueOf(localLotto.getId_Gara()));

										return true;

									} else {
										this.setSimogValidatorError(lv);
										this.thereIsAnError = true;
										return false;
									}

								} else {
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_12);
								}
							} else {
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
							}
						} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
							// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
							// NON
							// siano previste evoluzioni future in questo senso si puo levare
		// PP                if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//		                      && ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//		                      && ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//		                      && ( localLotto.getData_Pubblicazione() == null || "".equals(localLotto.getData_Pubblicazione()))){
//		                   lotto.setId_Lotto(localLotto.getId_Lotto());             
//		                   lm.modificaLotto(lotto);
//		                   this.updateCategorieScorporabili(lotto.getCategorieScorporabili(), localLotto.getId_Lotto(), lm);
//		                   return true;   
//		                }else{ throw new SimogWSException("il lotto non si trova in uno stato che permetta la modifica!"); }
						}
					}

				
					
					}else {
						throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_VALIDAZIONE_000);
					}
				// se caso normale controllo la data scadenza pagamenti
				}else if (localLotto.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(localLotto.getDATA_SCADENZA_PAGAMENTI())) {
					if (localLotto.getDATA_SCADENZA_PAGAMENTI() != null && 
							currentDate.compareTo(localLotto.getDATA_SCADENZA_PAGAMENTI()) <= 0) {


						// validazione dati ed integrazione sul db 
					if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
						// confronta gara->collaborazioni(must be equals)

						// apertura osservatorio
						if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

							if (!isOperaComeOsservatorio) {
								// controllo se e' modificabile
								Hashtable listaCodiciRssa = new Hashtable();
								ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
								for (String stringa : elenco) {
									listaCodiciRssa.put(stringa, "");
								}

								

								if ((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null
										|| "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO()))
										&& (localLotto.getDATA_INIB_PAGAMENTO() == null
												|| "".equals(localLotto.getDATA_INIB_PAGAMENTO()))) {

									

									//--------------------------------------------------------------------------
									 //MEV 3.04.10 43227

									//MEV 3.04.10 43227
									if (dati.getDATA_SCADENZA_PAGAMENTI() != null) {
										localLotto.setDataScadenzaPagamenti(dati.getDATA_SCADENZA_PAGAMENTI()); 
									}
									if (dati.getORA_SCADENZA() != null) {
										localLotto.setORA_SCADENZA(dati.getORA_SCADENZA()); 
									}
									if (dati.getDataScadenzaRichiestaInvito() != null) {
//										localLotto.setDataScadenzaRichiestaInvito(dati.getDataScadenzaRichiestaInvito()); 
										throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_MODIFICA_PERFEZIONAMENTO_001);
									}
									
									//FINE MEV 3.04.10 43227
									
									
									
									
									

									LottoValidator lv = new LottoValidator(con, logger);
									boolean esito = lv.valida(localLotto, ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO);

									

									if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

										// messaggi da ritornare
										AllValidationBeans msgs = new AllValidationBeans();

//										String idGara = request.getParameter(SESSION_ID_GARA);
										//Gara gara = gm.getGara(Long.parseLong(idGara));

										

										
										lottoManager.updateModificaDatiPerfezionamento(localLotto);
										//MEV 3.04.10 43227

										
									} 
								
								
									//--------------------------------------------------------------------------

									if (esito) {

//										CupLottoAggAction cla = new CupLottoAggAction(con, logger);
		//
//										cla.updateDatiCupLotto(localLotto);
										
										LogManager logManager = new LogManager(con, logger);
										logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
												gara.getID_STAZIONE_APPALTANTE(), User_id,
												localLotto.getCIG() + localLotto.getCIG_kkk(), LogManager.INT_MODIFICA_DATI_PERFEZIONAMENTO,
												gara.getCF_AMMINISTRAZIONE(), Long.toString(localLotto.getId_Lotto()),
												String.valueOf(localLotto.getId_Gara()));

										return true;

									} else {
										this.setSimogValidatorError(lv);
										this.thereIsAnError = true;
										return false;
									}

								} else {
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_12);
								}
							} else {
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
							}
						} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
							// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
							// NON
							// siano previste evoluzioni future in questo senso si puo levare
		// PP                if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//		                      && ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//		                      && ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//		                      && ( localLotto.getData_Pubblicazione() == null || "".equals(localLotto.getData_Pubblicazione()))){
//		                   lotto.setId_Lotto(localLotto.getId_Lotto());             
//		                   lm.modificaLotto(lotto);
//		                   this.updateCategorieScorporabili(lotto.getCategorieScorporabili(), localLotto.getId_Lotto(), lm);
//		                   return true;   
//		                }else{ throw new SimogWSException("il lotto non si trova in uno stato che permetta la modifica!"); }
						}
					}
					}else {
						throw new SimogWSException("ERRORE: " + Messaggi.SIMOG_VALIDAZIONE_000);
					}
				}
			////////////////////////////////////////////////////////////
				
				
				
			} catch (SimogWSException swe) {
				throw swe;
			} catch (SQLException sqle) {
				logger.error("errore nel recupero del lotto da modificare");
				// sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + sqle.getMessage());
			} catch (Exception e) {
				logger.error("errore nel recupero del lotto da modificare");
				// e.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + e.getMessage());
			}
			return false;
		}
		//FINE MEV 3.04.10 43227
		
		///MEV 53643 3.04.13
		public boolean ModificaCPV(Lotto dati, String User_id, CollaborazioniRssa collaborazioniRssa, String cig,
				boolean isOperaComeOsservatorio, List<CpvLotto> cpvSecondarie) throws SimogWSException {
			
			
			
			Lotto localLotto = null;
			LottoManager lottoManager = new LottoManager(con, logger);
			GaraManager garaManager = new GaraManager(con, logger);
			
			try {
				
				localLotto = lottoManager.getLottoByCigWS(cig).get(0);
			} catch (Exception e) {
				logger.debug("Impossibile recuperare la copia locale del lotto, per il confronto: " + e.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
			}
			this.id_gara = localLotto.getId_Gara();
			
			
			
			Collaborazione collaborazioneSelezionata = null;
			if (!isOperaComeOsservatorio)
				collaborazioneSelezionata = collaborazioniRssa.getCollaborazione();

			

			try {
				Gara gara = garaManager.getGara(localLotto.getId_Gara());
				//controllo se la funzione non attiva
				String currentDate = PageHelper.getCurrentDate();
				
								// validazione dati ed integrazione sul db 
							if (isOperaComeOsservatorio || collaborazioneSelezionata != null) {
								// confronta gara->collaborazioni(must be equals)

								// apertura osservatorio
								if (checkCollaborazioneOrOperaComeOsservatorio(collaborazioniRssa, isOperaComeOsservatorio, gara)) {

									if (!isOperaComeOsservatorio) {
										// controllo se e' modificabile
										Hashtable listaCodiciRssa = new Hashtable();
										ArrayList<String> elenco = collaborazioniRssa.getCfAmmDoveRssa();
										for (String stringa : elenco) {
											listaCodiciRssa.put(stringa, "");
										}

										

										if ((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null
												|| "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO()))
												&& (localLotto.getDATA_INIB_PAGAMENTO() == null
														|| "".equals(localLotto.getDATA_INIB_PAGAMENTO()))) {

											

											
											localLotto.setId_CPV(dati.getId_CPV());
											lottoManager.updateCPV(localLotto);
											
											lottoManager.deleteCpvLotto(localLotto.getId_Lotto());
											// Elimina duplicati
											if (cpvSecondarie != null && cpvSecondarie.size() > 0) {
												localLotto.setElencoCpvSecondarie(this.verificaDuplicati(cpvSecondarie));
												for (CpvLotto cpvSec : localLotto.getElencoCpvSecondarie()) {
													lottoManager.insertCpvLotto(localLotto.getId_Lotto(), cpvSec);
												}
											}
											
											
											
											
											

											LottoValidator lv = new LottoValidator(con, logger);
											boolean esito = lv.valida(localLotto, ParametriServlet.ACTION_MODIFICA_CPV);

											

											if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

												// messaggi da ritornare
												AllValidationBeans msgs = new AllValidationBeans();

//												String idGara = request.getParameter(SESSION_ID_GARA);
												//Gara gara = gm.getGara(Long.parseLong(idGara));

												

												

												
											} 
										
										
											//--------------------------------------------------------------------------

											if (esito) {

//												CupLottoAggAction cla = new CupLottoAggAction(con, logger);
				//
//												cla.updateDatiCupLotto(localLotto);
												
												LogManager logManager = new LogManager(con, logger);
												logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
														gara.getID_STAZIONE_APPALTANTE(), User_id,
														localLotto.getCIG() + localLotto.getCIG_kkk(), LogManager.MODFIFICA_CPV,
														gara.getCF_AMMINISTRAZIONE(), Long.toString(localLotto.getId_Lotto()),
														String.valueOf(localLotto.getId_Gara()));

												return true;

											} else {
												this.setSimogValidatorError(lv);
												this.thereIsAnError = true;
												return false;
											}

										} else {
											throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_12);
										}
									} else {
										throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_10);
									}
								} else { // vl - questa condizione si protrebbe verificare e venir usata solamente
									// se si variano, le funzioni ammesse per l' osservatorio regionale, nel caso
									// NON
									// siano previste evoluzioni future in questo senso si puo levare
				// PP                if((localLotto.getDATA_CANCELLAZIONE_LOTTO() == null || "".equals(localLotto.getDATA_CANCELLAZIONE_LOTTO())) 
//				                      && ( localLotto.getData_Comunicazione() == null || "".equals(localLotto.getData_Comunicazione())) 
//				                      && ( localLotto.getDATA_INIB_PAGAMENTO() == null || "".equals(localLotto.getDATA_INIB_PAGAMENTO()))
//				                      && ( localLotto.getData_Pubblicazione() == null || "".equals(localLotto.getData_Pubblicazione()))){
//				                   lotto.setId_Lotto(localLotto.getId_Lotto());             
//				                   lm.modificaLotto(lotto);
//				                   this.updateCategorieScorporabili(lotto.getCategorieScorporabili(), localLotto.getId_Lotto(), lm);
//				                   return true;   
//				                }else{ throw new SimogWSException("il lotto non si trova in uno stato che permetta la modifica!"); }
								}
							}

						
				
			} catch (SimogWSException swe) {
				throw swe;
			} catch (SQLException sqle) {
				logger.error("errore nel recupero del lotto da modificare");
				// sqle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + sqle.getMessage());
			} catch (Exception e) {
				logger.error("errore nel recupero del lotto da modificare");
				// e.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_28 + ": " + e.getMessage());
			}
			return false;
		}
		//FINE //MEV 53643 3.04.13
	

	public CUPLOTTO getCuplotto() {
		return cuplotto;
	}

	public boolean integraDL133(String userId, String indexCollaborazione, CollaborazioniRssa collsRssa,
			String id_gara2, String flagDL133, boolean operaComeOsservatorio, String codiceRegione)
			throws SimogWSException {
		if (con != null) {
			GaraManager garaManager = new GaraManager(con, logger);
			try {

				// carico la gara prima
				Gara gara = garaManager.getGara(Long.parseLong(id_gara2));
				// caso in cui id_gara non sia valido (corretto)
				if (gara != null) {
					// apertura osservatorio
					if (checkCollaborazioneOrOperaComeOsservatorio(collsRssa, operaComeOsservatorio, gara)) {

						Collaborazione collaborazioneSelezionata = null;
						if (!operaComeOsservatorio)
							collaborazioneSelezionata = collsRssa.getCollaborazione();

						TableBean tableBeanGara = null;
						if (!operaComeOsservatorio) {
							// costruzione sa abilitate per ricerca gara
							Hashtable<String, String> listaCodiciRssa = new Hashtable<String, String>();
							ArrayList<String> elenco = collsRssa.getCfAmmDoveRssa();
							for (String stringa : elenco) {
								listaCodiciRssa.put(stringa, "");
							}
							tableBeanGara = garaManager.getDettagliGaraByIdGaraRSSA(id_gara2, listaCodiciRssa);
						} else {
							tableBeanGara = garaManager.getDettagliGaraByIdGara(id_gara2);
						}

						String dataPubb = "";
						for (int i = 0; i < tableBeanGara.getFullSize(); i++) {
							if (!"".equals(tableBeanGara.getNulledField(LOTTO.DATA_PUBBLICAZIONE, i))) {
								dataPubb = tableBeanGara.getNulledField(LOTTO.DATA_PUBBLICAZIONE, i);
								break;
							}
						}

						if ((gara.getID_STATO_GARA() == StatiScheda.CONFERMATO) && tableBeanGara.getFullSize() > 0
								&& SimogFlags.isINT87_RFSIMOGWS01Active()
								// &&
								// ConfigurationManager.getInstance().getSimogProperties().isINT87Attivo(gara.getData_creazione())
								// ){
								&& ConfigurationManager.getInstance().getSimogProperties().isINT87Attivo(dataPubb)) {

							// check avcpass
//                     if(SimogFlags.is3028_RFWEBGL07Active()){
//                        //LottoManager lman = new LottoManager(con, logger);
//                        //List<Lotto> listaLotti = lman.getListaLotti(gara.getId_Gara());
//                        if(isAVCPass(gara, null, AVCPassFunzioneEnum.WS_GARA_UPDATE.getCodice())){
//                           throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
//                        }
//                     }

							gara.setURGENZA_DL133(flagDL133);
							// salvo
							garaManager.saveDL133(gara);

							LogManager logManager = new LogManager(con, logger);
							logManager.log(PageHelper.getDBDateFromTS(new AccessiDB(con, logger).getNow()),
									gara.getID_STAZIONE_APPALTANTE(), userId, "", LogManager.MOD_DL133,
									gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));

							return true;
							// altrimenti
						} else {
							throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_39);
						}
					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_13);
					}
				} else {
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_14);
				}
			} catch (NumberFormatException nfe) {
				logger.error(
						"eccezione occorsa durante la conversione di id_gara o dell'importo gara " + nfe.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_06);
			} catch (SQLException sqle) {
				// sqle.printStackTrace();
				logger.error("eccezione occorsa provando ad aggiornare una gara " + sqle.getMessage());
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);
			} catch (SimogWSException swe) {
				throw swe;
			} catch (Exception e) {
				logger.error("eccezione occorsa provando ad aggiornare una gara " + e.getMessage());
				// e.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_07);

			}
		} else {
			logger.error("uno degli oggetti necessari all'inserimento della gara risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_04);
		}

	}

//   public void setCuplotto(CUPLOTTO cuplotto) {
//      this.cuplotto = cuplotto;
//   }

}
