package it.avlp.simog.validatore;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.cpv.CPVEUManager;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.DerogaQualificazioneSABean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeLottoBean;
import it.avlp.simog.beans.MotivazioniBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.AFFIDAMENTI_RISERVATI;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.CONDIZIONI;
import it.avlp.simog.db.generated.MOTIVO_COLLEGAMENTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.xmlbeans.DerogaQualificazioneSA;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

public class LottoValidator extends SimogValidator {

	public LottoValidator(Connection connection, Logger logger) {
		super(connection, logger);
	}

	@Override
	public boolean valida(Object bean, String section) {
		if (bean != null) {
			Lotto lotto = (Lotto) bean;
			if (section == null || section.equals("")) {
				valida(lotto);
				// gm come era }else if(section.equalsIgnoreCase("perfezionamento")){
				// gm come e adesso
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO)) {
				validaPerf(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_SENZA_LOTTI)) {
				validaPerfSenzaLotti(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_PROC_RISTRETTA_FASE1)) {
				validaPerfProceduraRistretta(lotto, false);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_PROC_RISTRETTA_FASE2)) {
				validaPerfProceduraRistretta(lotto, true);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_PROC_MISTA)) {
				validaPerfProceduraMista(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.CANCELLAZIONE)) {
				validaCanc(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.CANCELLAZIONE_SENZA_LOTTI)) {
				validaCancSenzaLotti(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_CONTRATTO_ESCLUSO)) {
				validaContrattiEsclusi(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_CAT_SOA)) { /* MAD 68089 3.04.16 Inizio */
				validaCategoriaSoa(lotto); /* MAD 68089 3.04.16 Fine */
			} else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_CPV)) {
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());
					
					validaCPV(lotto,g);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			} 
			//MEV 37010 3.04.8.1
			else if (section.equalsIgnoreCase(ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA)) {
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());
					
					validaPariOpportunita(lotto,g,true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//FINE MEV 37010 3.04.8.1
			}//MEV 3.04.10 43227
			else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO)) {
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());
					
					validaModificaDatiPerfezionamento(lotto,g,true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//FINE MEV 3.04.10 43227
			} else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_RIPETIZIONI)) {
				// TICKET ALM - 3.04.3 #7849
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());

					String strCreazioneGara = "";
					long dataCreazioneGara = 0;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					strCreazioneGara = g.getData_creazione();
					dataCreazioneGara = sdf.parse(strCreazioneGara).getTime();

					validaRipetizioni(lotto, dataCreazioneGara);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (section.equalsIgnoreCase(ParametriCup.ACTION_MODIFICA_DATI_CUP)) {
				validaModificaDatiCup(lotto);
			} else {
				logger.error("Tab passato al validatore non valido");
			}

			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}
		return false;
	}
	
	//3.04.9 MEV 40610
	public boolean valida(Object bean, String section, String sessionId) {
		logger.info("valida lotto con qualificazione SA");
		if (bean != null) {
			Lotto lotto = (Lotto) bean;
			if (section == null || section.equals("")) {
				
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				valida(lotto);
				// gm come era }else if(section.equalsIgnoreCase("perfezionamento")){
				// gm come e adesso
				
				validaQualificazioneSA(lotto, g, sessionId);
				
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO)) {
				validaPerf(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_SENZA_LOTTI)) {
				validaPerfSenzaLotti(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_PROC_RISTRETTA_FASE1)) {
				validaPerfProceduraRistretta(lotto, false);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_PROC_RISTRETTA_FASE2)) {
				validaPerfProceduraRistretta(lotto, true);
			} else if (section.equalsIgnoreCase(ParametriServlet.PERFEZIONAMENTO_PROC_MISTA)) {
				validaPerfProceduraMista(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.CANCELLAZIONE)) {
				validaCanc(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.CANCELLAZIONE_SENZA_LOTTI)) {
				validaCancSenzaLotti(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_CONTRATTO_ESCLUSO)) {
				validaContrattiEsclusi(lotto);
			} else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_CPV)) {
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());
					
					validaCPV(lotto,g);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} 
			//MEV 37010 3.04.8.1
			else if (section.equalsIgnoreCase(ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA)) {
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());
					
					validaPariOpportunita(lotto,g,true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//FINE MEV 37010 3.04.8.1
			} else if (section.equalsIgnoreCase(ParametriServlet.ACTION_MODIFICA_RIPETIZIONI)) {
				// TICKET ALM - 3.04.3 #7849
				GaraManager gm = new GaraManager(connection, logger);
				Gara g = null;
				try {
					g = gm.getGara(lotto.getId_Gara());

					String strCreazioneGara = "";
					long dataCreazioneGara = 0;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					strCreazioneGara = g.getData_creazione();
					dataCreazioneGara = sdf.parse(strCreazioneGara).getTime();

					validaRipetizioni(lotto, dataCreazioneGara);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (section.equalsIgnoreCase(ParametriCup.ACTION_MODIFICA_DATI_CUP)) {
				validaModificaDatiCup(lotto);
			} else {
				logger.error("Tab passato al validatore non valido");
			}

			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}
		return false;
	}
	//fine 3.04.9 MEV 40610

	/******************************************************************************************************
	 * Validatore per il Lotto
	 * 
	 * @param lotto Lotto
	 */
	private void valida(Lotto lotto) {
		GaraManager gm = new GaraManager(connection, logger);
		Gara g = null;
		try {
			g = gm.getGara(lotto.getId_Gara());

			// se importo gara non -1 ovvero importo non noto a priori
			// PP controllo solo per i WS che possono specificarlo
			if (SimogFlags.isFromWS()
					&& g.getIMPORTO_GARA().compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0) {
				// effettua il confronto
				// PP NOOOO sta bene cosi non dovrebbe essere la somma degli importi dei lotti
				// della gara non devono superare l'importo gara ?
				if (lotto.getImporto_Lotto().compareTo(g.getIMPORTO_GARA()) > 0) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_022);
				}
			}
		} catch (Exception e) {
			logger.debug("Errore durante il recupero della gara per il cofronto degli importi");
			e.printStackTrace();
		}
		if (lotto.getOggetto() == null || lotto.getOggetto().trim().length() == 0) {
			mEccezioni.addValidationField("label_OggettoLotto"); // LABEL ERR
			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_013);
		}

		// non vale piu' indeterminato
		if (lotto.getImporto_Lotto() == null || lotto.getImporto_Lotto().doubleValue() <= 0) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_014);
		}

		if (lotto.getId_CPV() == null || lotto.getId_CPV().trim().length() == 0) {
			// pp organi costituzionali, CPV facoltativo
			if (SimogFlags.isOrganiCostActive() && g.isOrganoCost())
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice CPV Prevalente"));
			else {
				mEccezioni.addValidationField("label_CPVLotto"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_GARA_009);
			}
		} else {
			CPVEUManager cManager = new CPVEUManager(connection, logger);
			try {
				if (!cManager.checkCPV(lotto.getId_CPV(), g.getData_creazione())) {

					// MEV 25894 - se e' un'adesione ad accordo quadro, controlla solo se la CPV
					// esiste
					if (g.getCIG_ACC_QUADRO() != null && !cManager.checkCPVNoData(lotto.getId_CPV())) {
						logger.debug("Inserito valore non valido [" + lotto.getId_CPV() + "] per CPV");
						mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
					}
				} else {
					boolean ok = !"0".equals(lotto.getId_CPV().substring(2, 3))
							&& !"0".equals(lotto.getId_CPV().substring(3, 4));

					ok = ok || cManager.getBranch(lotto.getId_CPV().substring(0, 2), lotto.getId_CPV().substring(2, 3),
							lotto.getId_CPV().substring(3, 4), lotto.getId_CPV().substring(4, 5),
							lotto.getId_CPV().substring(5, 8)).size() == 0;

					if (!ok) {
						boolean old = false;

						// Se il CIG accordo quadro risale al 2007/2008 bypassa l'errore
						if (g.getCIG_ACC_QUADRO() != null && !"".equals(g.getCIG_ACC_QUADRO())) {
							LottoManager lm = new LottoManager(connection, logger);
							List<Lotto> lottoAccQ = new ArrayList<Lotto>();
							try {
								lottoAccQ = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!lottoAccQ.isEmpty()) {
								Lotto lottoAQ = lottoAccQ.get(0);
								old = lottoAQ.getDataCreazione() == null || "".equals(lottoAQ.getDataCreazione())
										|| "20090101".compareTo(lottoAQ.getDataCreazione()) > 0;
							}
						}
						if (!old) {
							logger.debug("Inserito valore non valido [" + lotto.getId_CPV() + "] per CPV");
							mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
						}
					} else // TICKET ALM #13793 - 3.04.5
						if (SimogProperties.getInstance().isDataCreatedAfter3045(g.getData_creazione())
								&& g.getCIG_ACC_QUADRO() != null && !"".equals(g.getCIG_ACC_QUADRO())) {
							LottoManager lm = new LottoManager(connection, logger);
							try {
								List<Lotto> lottoAccQ = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
								if (!lottoAccQ.isEmpty()) {
									String cpvAccQ = lottoAccQ.get(0).getId_CPV();
									if (!lotto.getId_CPV().equals(cpvAccQ)) {
										List<CpvLotto> listCpvSecAccQ = lm.selectCpvLotto(lottoAccQ.get(0).getId_Lotto());
										boolean checkCpvSec = false;
										for (CpvLotto cpvSecAccQ : listCpvSecAccQ) {
											if (lotto.getId_CPV().equals(cpvSecAccQ.getIdCpv())) {
												checkCpvSec = true;
												break;
											}
										}
										if (!checkCpvSec) {
											mEccezioni.addValidationField("label_CPVLotto"); // LABEL ERR
											mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_036);
										}
									}
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} // FINE TICKET ALM #13793 - 3.04.5
				}
			} catch (SQLException sqle) {
				logger.fatal(sqle.getMessage());
				mEccezioni.addValidationField("label_CPVLotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
			}
		}

		if (lotto.getId_Scelta_Contraente() == null || lotto.getId_Scelta_Contraente().trim().length() == 0) {
			mEccezioni.addValidationField("label_SceltaContraenteLotto"); // LABEL ERR
			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_013a);
		} else {
			try {
				Long sceltaTest = new Long(lotto.getId_Scelta_Contraente()).longValue();
				// passo null in idoss per non attivare la personalizzazione della scelta
				// contraente
				// TICKET ALM - 3.04.2 NG
				// Controllo la validita della scelta contraente per data creazione gara
				if (!sceltaContraenteValida(sceltaTest, g.getData_creazione(), g.isOrganoCost(), null)) {
					mEccezioni
					.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Scelta Contraente"));
				}
				// organi costituzionali la voce riservata e ammessa solo per OOCC
				else {
					//                  if(SimogFlags.is3028_RFWEBGL00Active()){
					//                     // la voce sarebbe valida ma se e una personalizzata devo vedere se l'ambito di applicazione e coerente
					//                     TableBean record = gm.executeSelectWhere(CONTRAENTE_REGIONE.TABLE_NAME, 
					//                           CONTRAENTE_REGIONE.DATA_FINE_VALIDITA, 
					//                           CONTRAENTE_REGIONE.ID_RECORD, null, 
					//                           CONTRAENTE_REGIONE.ID_SCELTA_AVCP + "="+lotto.getId_Scelta_Contraente()
					//                  + " AND " + CONTRAENTE_REGIONE.ID_OSSERVATORIO + " = " + idOss, null);
					//                     if(record.getFullSize() > 0){
					//                        String tipo = record.getNulledField(CONTRAENTE_REGIONE.TIPO_CONTRATTO, 0);
					//                        if(tipo != null && !tipo.contains(lotto.getTIPO_CONTRATTO_LOTTO())){
					//                           mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Scelta Contraente rispetto alla tipologia di appalto"));
					//                        }
					//                     }
					//                  }
					if (SimogFlags.isOrganiCostActive() && !g.isOrganoCost()
							&& Costanti.SCELTA_CONTRAENTE_OOCC.equals(lotto.getId_Scelta_Contraente()))
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Scelta Contraente"));
				}
			} catch (Exception e) {
				logger.fatal(e.getMessage());
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Scelta Contraente"));
			}
		}
		if (lotto.getId_Categoria_prevalente() == null || lotto.getId_Categoria_prevalente().trim().length() == 0) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Categoria Prevalente"));
		} else {
			if (!this.validaCategoriaPrevalente(lotto.getId_Categoria_prevalente(), g.getData_creazione())) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Categoria Prevalente"));
			} else {
				if (SimogFlags.isOrganiCostActive() && !g.isOrganoCost()
						&& Costanti.CATEGORIA_PREV_OOCC.equals(lotto.getId_Categoria_prevalente()))
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Categoria Prevalente"));
			}
		}

		if (isEmptyOrZero(lotto.getTIPO_CONTRATTO_LOTTO())) {
			mEccezioni.addValidationField("label_TipoContrattoLotto"); // LABEL ERR
			mEccezioni
			.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Oggetto principale del contratto"));
		} else {
			if (!Costanti.TIPO_SCHEDA_FORNITURE.equals(lotto.getTIPO_CONTRATTO_LOTTO())
					&& !Costanti.TIPO_SCHEDA_SERVIZI.equals(lotto.getTIPO_CONTRATTO_LOTTO())
					&& !Costanti.TIPO_SCHEDA_LAVORI.equals(lotto.getTIPO_CONTRATTO_LOTTO()))
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Oggetto principale del contratto"));
		}
		
		//MEV 38205 3.04.8.1
		if (lotto.getTIPO_CONTRATTO_LOTTO().equals("L") && lotto.getImporto_Lotto().compareTo(SimogProperties.getInstance().getSogliaMevBim()) >= 0) {
			if (lotto.getFLAG_USO_METODI_EDILIZIA() == null || lotto.getFLAG_USO_METODI_EDILIZIA().equals("")) {
				mEccezioni.addValidationField("label_FlagUsoMetodiEdilizia"); // LABEL ERR
				mEccezioni
				.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Flag uso di metodi e strumenti elettronici di modellazione per l'edilizia e le infrastrutture"));

			}
		}
		//MEV 38205 3.04.8.1

		// Ticket #20058 - 09 - 02 - 21

		if (SimogProperties.getInstance().isDataCreatedAfter3046(g.getData_creazione())) {
			if (isEmptyOrZero(lotto.getDurataRipetizioni())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_PREVEDE_RIP())) {
				mEccezioni.addValidationField("label_DurataRinnoviRipetizioni"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
						"Durata dei Rinnovi e delle ripetizioni in giorni"));
			} else if (!isEmptyOrZero(lotto.getDurataRipetizioni())
					&& Costanti.FLAG_VALORE_NO.equals(lotto.getFLAG_PREVEDE_RIP())) {
				mEccezioni.addValidationField("label_DurataRinnoviRipetizioni"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
						"Durata dei Rinnovi e delle ripetizioni in giorni"));
			} else if (!isEmptyOrZero(lotto.getDurataRipetizioni())) {

				if (lotto.getDurataRipetizioni() != (int) lotto.getDurataRipetizioni()) {
					mEccezioni.addValidationField("label_DurataRinnoviRipetizioni"); // LABEL ERR
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1",
							"Durata dei Rinnovi e delle ripetizioni in giorni"));
				} else if (lotto.getDurataRipetizioni() > 9999) {
					mEccezioni.addValidationField("label_DurataRinnoviRipetizioni"); // LABEL ERR
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1",
							"Durata dei Rinnovi e delle ripetizioni in giorni") + " (valori ammessi da 1 a 9999)");
				}
			}

			//MARRA MEV 34189 3.04.8 aggiunta condizione  && (lotto.getID_ESCLUSIONE() != 18 || lotto.getID_ESCLUSIONE() != 32 || g.getID_MODO_REAL() != 12).
			if (isEmptyOrZero(lotto.getDurataAffidamentoGiorni()) && (lotto.getID_ESCLUSIONE() != 18 && lotto.getID_ESCLUSIONE() != 32 && g.getID_MODO_REAL() != 12)) {
				mEccezioni.addValidationField("label_DurataAffidamentoInGiorni"); // LABEL ERR
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Durata dell'affidamento in giorni"));
			}

		} else {
			if (!isEmptyOrZero(lotto.getDurataRipetizioni())) {
				mEccezioni.addValidationField("label_DurataRinnoviRipetizioni"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
						"Durata dei Rinnovi e delle ripetizioni in giorni"));
			}

			if (!isEmptyOrZero(lotto.getDurataAffidamentoGiorni())) {
				mEccezioni.addValidationField("label_DurataAffidamentoInGiorni"); // LABEL ERR
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Durata dell'affidamento in giorni"));
			}
		}

		validaEsclusi(lotto);

		// TICKET ALM #2845-02.1 e 02.2
		String strCreazioneGara = "";
		long dataCreazioneGara = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		strCreazioneGara = g.getData_creazione();
		try {
			dataCreazioneGara = sdf.parse(strCreazioneGara).getTime();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// TICKET ALM #2846 - 3.04.3
		// Applica il controllo per le gare create in data successiva l'attivazione
		if (SimogProperties.getInstance().isDataCreatedAfter3043(strCreazioneGara)) {
			if ("null".equals(lotto.getID_MOTIVO_COLL_CIG()))
				lotto.setID_MOTIVO_COLL_CIG(null);

			int cndColsel = 0;
			if (!isEmptyOrZero(lotto.getCondizioni()) && lotto.getCondizioni().size() > 1) {
				for (CondizioneLottoBean cnd : lotto.getCondizioni()) {
					if (cnd.getIdCondizione() == Costanti.COND_CONS_COMPL
							|| cnd.getIdCondizione() == Costanti.COND_II_FASE
							|| cnd.getIdCondizione() == Costanti.COND_RIP)
						cndColsel++;
				}
			}

			if (cndColsel > 1) {
				mEccezioni.addValidationField("label_CondizioniLotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_242a.replace("$1",
						"Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
			}
			boolean err = false;

			if (!isEmptyOrZero(lotto.getId_Scelta_Contraente())
					&& lotto.getId_Scelta_Contraente().equals(Costanti.SCELTA_CONTRAENTE_AFF_DIRETTO)
					&& !Costanti.COLL_CIG_AFF_DIRETTO.equals(lotto.getID_MOTIVO_COLL_CIG())) {
				mEccezioni.addValidationField("label_MotivoCollegamentoLotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_237.replace("$1",
						"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
				err = true;
			} else // Se e' stata scelta come condizione 39 ma il motivo collegamento non e' 2,
				// invia errore
				if (!isEmptyOrZero(lotto.getCondizioni()) && lotto.getCondizioni().size() == 1
				&& lotto.getCondizioni().get(0).getIdCondizione() == Costanti.COND_CONS_COMPL
				&& !Costanti.COLL_CIG_CONS_SUPPL.equals(lotto.getID_MOTIVO_COLL_CIG())) {
					mEccezioni.addValidationField("label_MotivoCollegamentoLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_239.replace("$1",
							"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
					err = true;
				} else // Se e' stata scelta come condizione 42 ma il motivo collegamento non e' 9,
					// invia errore
					if (!isEmptyOrZero(lotto.getCondizioni()) && lotto.getCondizioni().size() == 1
					&& lotto.getCondizioni().get(0).getIdCondizione() == Costanti.COND_II_FASE
					&& !Costanti.COLL_CIG_II_FASE.equals(lotto.getID_MOTIVO_COLL_CIG())) {
						mEccezioni.addValidationField("label_MotivoCollegamentoLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_240.replace("$1",
								"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
						err = true;
					} else // Se e' stata scelta come condizione 43 ma il motivo collegamento non e' 1,
						// invia errore
						if (!isEmptyOrZero(lotto.getCondizioni()) && lotto.getCondizioni().size() == 1
						&& lotto.getCondizioni().get(0).getIdCondizione() == Costanti.COND_RIP
						&& !Costanti.COLL_CIG_RIP.equals(lotto.getID_MOTIVO_COLL_CIG())) {
							mEccezioni.addValidationField("label_MotivoCollegamentoLotto");
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_241.replace("$1",
									"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
							err = true;
						}
			// Per tutti gli altri casi, verifica che l'id sia valido
			if (!err && isEmptyOrZero(lotto.getID_MOTIVO_COLL_CIG())) {
				mEccezioni.addValidationField("label_MotivoCollegamentoLotto"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
						"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
			} else {
				try {
					if (!err && !motivoCollegamentoValido(Integer.parseInt(lotto.getID_MOTIVO_COLL_CIG()),
							g.getData_creazione())) {
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
								"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
					}

				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else {
			// Se la data e' antecedente e il campo e' valorizzato, riportare errore di
			// campo non richiesto
			if (!isEmptyOrZero(lotto.getID_MOTIVO_COLL_CIG()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
						"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
		}
		// FINE TICKET ALM - 3.04.3

		// Applica il controllo solo per le gare piu' vecchie
		if (SimogFlags.is3042Active()
				&& dataCreazioneGara < SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
			/**/
			// Se il campo Oggetto principale del contratto � "Lavori" i campi dovrebbero
			// essere valorizzati
			// I campi Triennio anno fine e Triennio anno inizio devono essere settati con
			// valori numerici e
			// la loro differenza deve essere un triennio valido
			if (isEmpty(lotto.getTRIENNIO_ANNO_FINE()) && isEmpty(lotto.getTRIENNIO_ANNO_INIZIO())) {
				if (isLavori(lotto.getTIPO_CONTRATTO_LOTTO()))
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Triennio"));
			} else {
				if (!isNumero(lotto.getTRIENNIO_ANNO_FINE()) || !isNumero(lotto.getTRIENNIO_ANNO_INIZIO())
						|| (lotto.getTRIENNIO_ANNO_INIZIO().length() != 4)
						|| (lotto.getTRIENNIO_ANNO_FINE().length() != 4))
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Triennio"));
				else {
					if ((Integer.parseInt(lotto.getTRIENNIO_ANNO_FINE()) <= Integer
							.parseInt(lotto.getTRIENNIO_ANNO_INIZIO())
							|| (Integer.parseInt(lotto.getTRIENNIO_ANNO_FINE())
									- Integer.parseInt(lotto.getTRIENNIO_ANNO_INIZIO())) != 2))
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Triennio"));
				}
			}
		}
		// FINE TICKET ALM #2845-02.1 e 02.2
		else { // TICKET ALM #2845-02.3 - se la gara e' nuova ma i campi sono stati valorizzati
			// via WS, inserisci controllo
			if (!isEmpty(lotto.getTRIENNIO_ANNO_FINE()) || !isEmpty(lotto.getTRIENNIO_ANNO_INIZIO()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Triennio"));
		}

		// TICKET ALM - 3.04.2 NG
		// Campi ammessi per le gare create successivamente l'attivazione dela 3.04.2
		if (SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(g.getData_creazione())) {
			if (isEmptyOrZero(lotto.getFLAG_DL50())) {
				mEccezioni.addValidationField("label_DL50Lotto"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
						"Il lavoro o l'acquisto di bene o servizio e' stato previsto all'interno della programmazione di cui all'art.31 D.Lgs.50"));
			} else { // #TICKET ALM #2845-02.6 e 02.7
				if (isYFlag(lotto.getFLAG_DL50())
						&& (lotto.getPRIMA_ANNUALITA() == null || "".equals(lotto.getPRIMA_ANNUALITA()))) {
					mEccezioni.addValidationField("label_PrimaAnnualitaLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_011a);
				}

				if (isNFlag(lotto.getFLAG_DL50()) && lotto.getPRIMA_ANNUALITA() != null
						&& !"".equals(lotto.getPRIMA_ANNUALITA())) {
					mEccezioni.addValidationField("label_PrimaAnnualitaLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Prima annualita'"));
				}
				if (lotto.getPRIMA_ANNUALITA() != null && !"".equals(lotto.getPRIMA_ANNUALITA())
						&& (lotto.getPRIMA_ANNUALITA().length() != 4 || !isNumber(lotto.getPRIMA_ANNUALITA()))) {
					mEccezioni.addValidationField("label_PrimaAnnualitaLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Prima annualita'"));
				}
			}
		} else {// Se la gara e' antecedente e i campi sono valorizzati, mostra errore di campo
			// non previsto
			if (!isEmptyOrZero(lotto.getFLAG_DL50()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
						"Il lavoro o l'acquisto di bene o servizio e' stato previsto all'interno della programmazione di cui all'art.31 D.Lgs.50"));

			if (lotto.getPRIMA_ANNUALITA() != null)
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Prima annualita'"));

		}
		// FINE TICKET ALM - 3.04.2 NG

		// TICKET ALM - 3.04.2 2005
		try {
			if (SimogFlags.is3042Active()
					&& SimogProperties.getInstance().isDataCreatedAfter3042(g.getData_creazione())) {
				if (isEmptyOrZero(lotto.getID_AFF_RISERVATI())
						&& lotto.getId_Scelta_Contraente().equals(Costanti.TIPO_SCELTA_CONTRAENTE_AFF_RISERVATO)) {
					mEccezioni.addValidationField("label_TipoRiservatoLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipo appalto riservato"));
				} else if (!isEmptyOrZero(lotto.getID_AFF_RISERVATI())
						&& !affidamentiRiservatiValido(lotto.getID_AFF_RISERVATI(), g.getData_creazione()))
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipo appalto riservato"));
				else if (!isEmptyOrZero(lotto.getID_AFF_RISERVATI()) && !isEmptyOrZero(lotto.getId_Scelta_Contraente())
						&& !Costanti.AFFIDAMENTO_RISERVATO.equals(lotto.getId_Scelta_Contraente())) {
					mEccezioni.addValidationField("label_TipoRiservatoLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Tipo appalto riservato")
							+ " per la procedura di scelta del contraente selezionata");
				}
			} else {
				// TICKET ALM - 3.04.2 NG
				// Se gara e' antecedente inserire blocco in caso di campo valorizzato
				if (!isEmptyOrZero(lotto.getID_AFF_RISERVATI()))
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Tipo appalto riservato"));
				// FINE TICKET ALM - 3.04.2 NG
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// TICKET ALM #2845-02.1 e 02.2
		// Non eseguire il controllo su triennio progressivo se la gara e' antecedente
		// l'attivazione della 3.04.2
		if (dataCreazioneGara < SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
			// Il campo Triennio anno progressivo deve essere settato con valore numerico di
			// lunghezza 1
			// Se il campo Oggetto principale del contratto e' "Lavori" il campo dovrebbe
			// essere valorizzato
			if (isEmpty(lotto.getTRIENNIO_PROGRESSIVO())) {
				if (isLavori(lotto.getTIPO_CONTRATTO_LOTTO())) {
					mEccezioni.addValidationField("label_ProgressivoLotto");
					mEccezioni.addValidationWarn(
							Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Progressivo nell'ambito del triennio"));
				}
			} else {
				if ( // PP lunghezza libera ! (lotto.getTRIENNIO_PROGRESSIVO().length()!= 1) ||
						(!isNumero(lotto.getTRIENNIO_PROGRESSIVO()))) {
					mEccezioni.addValidationField("label_ProgressivoLotto");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Progressivo nell'ambito del triennio"));
				}
			}
		} else {
			if (!isEmpty(lotto.getTRIENNIO_PROGRESSIVO()))
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Progressivo nell'ambito del triennio"));
		}
		// TICKET ALM #2845-02.1 e 02.2

		// TICKET ALM #3908
		if (lotto.getImporto_Lotto() != null && lotto.getImporto_Lotto().doubleValue() > Costanti.SOGLIA_BENI_CULTURALI
				&& g.getID_ESTREMA_URGENZA() == Costanti.TIPO_ESTREMA_URGENZA_BENI_CULTURALI) {
			mEccezioni.addValidationWarn(Messaggi.SIMOG_LOTTO_027);

		}
		// FINE TICKET ALM #3908

		// Il campo CUI assegnato dal sistema deve essere settato con un valore
		// alfanumerico di lunghezza 20 o 22
		if (isEmpty(lotto.getANNUALE_CUI_MININF())) {

			// TICKET ALM - 3.04.2 NG - inserito controllo bloccante solo per le gare create
			// successivamente la creazione
			if (SimogFlags.is3042Active()
					&& SimogProperties.getInstance().isDataCreatedAfter3042(g.getData_creazione())) {
				if (isYFlag(lotto.getFLAG_DL50())) {
					mEccezioni.addValidationField("label_CUILotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
							"CUI programma triennale lavori pubblici o programma biennale forniture e servizi"));
				}
			}
		} else { // TICKET ALM #2845-02.10

			if (lotto.getANNUALE_CUI_MININF().length() < 20 || lotto.getANNUALE_CUI_MININF().length() > 22) {
				mEccezioni.addValidationField("label_CUILotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1",
						"CUI programma triennale lavori pubblici o programma biennale forniture e servizi")
						+ " - il CUI deve essere lungo tra 20 e 22");
			}
		}

		// gm nuovo codice pubblicazione bando 3.02
		// controllo congruenza presenza parametri e gara con caratteristiche di
		// pubblicazione bando
		LottoManager lm = new LottoManager(connection, logger);
		// boolean pubblicabile = lm.isPubblicabile(lotto.getId_Scelta_Contraente(),
		// lotto.getImporto_Lotto(), lotto.getTIPO_CONTRATTO_LOTTO()) ;
		// if(pubblicabile && isEmpty(lotto.getLUOGO_ISTAT()) &&
		// isEmpty(lotto.getLUOGO_NUTS())){
		if (isEmpty(lotto.getLUOGO_ISTAT()) && isEmpty(lotto.getLUOGO_NUTS())) {
			// per ora emetto solo warning se vengo dai WS
			// pp organi costituzionali, warning se non valorizzati
			// TICKET ALM #10974 (MAC)
			// if(SimogFlags.isFromWS() == false && (SimogFlags.isOrganiCostActive() ==
			// false || g.isOrganoCost() == false)){
			if (SimogFlags.isOrganiCostActive() == false || g.isOrganoCost() == false) {
				mEccezioni.addValidationField("label_ISTATLotto");
				mEccezioni.addValidationField("label_NUTSLotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice ISTAT"));
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice NUTS"));
			} else {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice ISTAT"));
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice NUTS"));
			}
		}

		// TICKET ALM - 3.04.7 - controllo non più necessario in quanto, in caso di
		// inserimento dell'istat, il nuts viene ricavato automaticamente
		// entrambi valorizzati
		//		if(!isEmpty(lotto.getLUOGO_ISTAT()) && !isEmpty(lotto.getLUOGO_NUTS())){
		//			mEccezioni.addValidationField("label_ISTATLotto");
		//			mEccezioni.addValidationField("label_NUTSLotto");
		//			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_141.replace("$1", "codice ISTAT").replace("$2", "codice NUTS"));
		//		}	
		// controllo validita' se valorizzato
		if (!isEmpty(lotto.getLUOGO_ISTAT()) || isStringEmptyValue(lotto.getLUOGO_ISTAT())) {
			try {
				if (!isNumber(lotto.getLUOGO_ISTAT()))
					throw new Exception();
				else if (!istatValido(lotto.getLUOGO_ISTAT(), null))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_ISTATLotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice ISTAT"));
			}
		}
		// controllo validita' se valorizzato
		if (!isEmpty(lotto.getLUOGO_NUTS()) || isStringEmptyValue(lotto.getLUOGO_NUTS())) {
			try {
				if (!nutsValido(lotto.getLUOGO_NUTS(), g.getData_creazione()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_NUTSLotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice NUTS"));
			}
		}

		if (!isEmpty(lotto.getIMPORTO_ATTUAZIONE_SICUREZZA())) {
			if (!isNumberDecimal(lotto.getIMPORTO_ATTUAZIONE_SICUREZZA().toString())
					|| !isPositive(lotto.getIMPORTO_ATTUAZIONE_SICUREZZA())) {
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo importo attuazione sicurezza"));
			} else if (!isEmpty(lotto.getImporto_Lotto())) {
				if (lotto.getImporto_Lotto().compareTo(new BigDecimal(0)) != 0
						&& lotto.getImporto_Lotto().compareTo(new BigDecimal(1).negate()) != 0) {
					if (lotto.getIMPORTO_ATTUAZIONE_SICUREZZA().compareTo(lotto.getImporto_Lotto()) > 0) {
						mEccezioni.addValidationField("label_ImportoSicurezzaLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_208
								.replace("$1", "importo attuazione sicurezza").replace("$2", "importo lotto"));
					}
				}
			}
		}

		// TICKET ALM #13691 - 3.04.5
		if (!isEmptyOrZero(lotto.getImporto_opzioni())) {
			if (!isNumberDecimal(lotto.getImporto_opzioni().toString()) || !isPositive(lotto.getImporto_opzioni())) {
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo importo opzioni/ripetizioni"));
			} else if (!isEmpty(lotto.getImporto_Lotto())) {
				if (lotto.getImporto_Lotto().compareTo(new BigDecimal(0)) != 0
						&& lotto.getImporto_Lotto().compareTo(new BigDecimal(1).negate()) != 0) {
					if (lotto.getImporto_opzioni().compareTo(lotto.getImporto_Lotto()) > 0) {
						mEccezioni.addValidationField("label_ImportoOpzioniLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_208
								.replace("$1", "importo opzioni/ripetizioni").replace("$2", "importo lotto"));
					}
				}
			}
			// TICKET ALM #20049
			//MAC 36258 cambiato da addValidationErr a addValidationWarn
			if (Costanti.FLAG_VALORE_NO.equals(lotto.getFLAG_PREVEDE_RIP())) {
				mEccezioni.addValidationField("label_PrevedeRipetizioniLotto");
				mEccezioni.addValidationWarn(Messaggi.SIMOG_LOTTO_038);
			}
		} else if (SimogProperties.getInstance().isDataCreatedAfter30452(g.getData_creazione())
				&& Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_PREVEDE_RIP())) {
			mEccezioni.addValidationField("label_ImportoOpzioniLotto");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "importo opzioni/ripetizioni"));
		} // FINE TICKET ALM #20049

		// gm fine nuovo codice pubblicazione bando 3.02

		// gm nuovi controlli simog 3.06, si effettua la validazione solo in fase di
		// inserimento lotto
		// PP controllo bloccante solo sul web
		if (lotto.getId_Lotto() == 0) {

			// ricavo la scelta contraente equivalente
			String sceltaEquiv = lotto.getId_Scelta_Contraente();
			//         if(SimogFlags.is3028_RFWEBGL00Active()){
			//            try {
			//               sceltaEquiv = lm.getSceltaContraenteAVCP(null, Long.valueOf(sceltaEquiv));
			//            } catch (NumberFormatException e) {
			//               // TODO Auto-generated catch block
			//               e.printStackTrace();
			//            } catch (SQLException e) {
			//               // TODO Auto-generated catch block
			//               e.printStackTrace();
			//            } catch (Exception e) {
			//               // TODO Auto-generated catch block
			//               e.printStackTrace();
			//            }
			//         }
//			3.04.8 34190 fix
			if (g.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET || g.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) {
				if (sceltaEquiv != null && !"".equals(sceltaEquiv)
						&& Integer.parseInt(sceltaEquiv) != Costanti.AFF_DIR_ADESIONE) {
					if (!SimogFlags.isFromWS())
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_210.replace("$1", "campo Scelta Contraente").replace("$2",
										": Affidamento diretto in Adesione ad Accordo Quadro/Convenzione"));
					else
						mEccezioni.addValidationWarn(
								Messaggi.SIMOG_VALIDAZIONE_210.replace("$1", "campo Scelta Contraente").replace("$2",
										": Affidamento diretto in Adesione ad Accordo Quadro/Convenzione"));
				}
			}
			if (g.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE || g.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE) {
				if (sceltaEquiv != null && !"".equals(sceltaEquiv)
						&& Integer.parseInt(sceltaEquiv) != Costanti.CON_COM_ADESIONE) {
					if (!SimogFlags.isFromWS())
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_210.replace("$1", "campo Scelta Contraente").replace("$2",
										": Confronto competitivo in Adesione ad Accordo Quadro/Convenzione"));
					else
						mEccezioni.addValidationWarn(
								Messaggi.SIMOG_VALIDAZIONE_210.replace("$1", "campo Scelta Contraente").replace("$2",
										": Confronto competitivo in Adesione ad Accordo Quadro/Convenzione"));
				}
			}
		}

		// TICKET ALM #13793 - 3.04.5
		List<Lotto> lottoAccQ = new ArrayList<Lotto>();
		if (g.getCIG_ACC_QUADRO() != null && !"".equals(g.getCIG_ACC_QUADRO())) {
			try {
				lottoAccQ = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // FINE TICKET ALM #13793 - 3.04.4.1
		// TICKET ALM #4219 - 3.04.4
		// Correttezza valori CPV Secondarie (che si applicano sia per le gare post che
		// pre attivazione 3.04.4)
		if (lotto.getElencoCpvSecondarie() != null && lotto.getElencoCpvSecondarie().size() > 0) {
			for (CpvLotto el : lotto.getElencoCpvSecondarie()) {
				CPVEUManager cManager = new CPVEUManager(connection, logger);
				try {

					if (!cManager.checkCPV(el.getIdCpv(), PageHelper.getCurrentDate())) {
						mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_031.replace("$1", el.getIdCpv()));
					}

					// Verifica se la CPV secondaria sia stata gia' inidcata come CPV primaria
					if (el.getIdCpv().equals(lotto.getId_CPV()))
						mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_033.replace("$1", el.getIdCpv()));

					// TICKET ALM #13793 - 3.04.5
					if (!lottoAccQ.isEmpty()) {
						Lotto cigAccQ = lottoAccQ.get(0);
						if (!el.getIdCpv().equals(cigAccQ.getId_CPV())) {
							List<CpvLotto> listcpvSec = lm.selectCpvLotto(cigAccQ.getId_Lotto());
							boolean checkCpvSec = false;
							for (CpvLotto secCpvLotto : listcpvSec) {
								if (el.getIdCpv().equals(secCpvLotto.getIdCpv())) {
									checkCpvSec = true;
									break;
								}
							}
							if (!checkCpvSec)
								mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_037.replace("$1", el.getIdCpv()));
						}
					} // FINE TICKET ALM #13793 - 3.04.4.1

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} // FINE TICKET ALM #4219 - 3.04.4

		// TICKET ALM 3.04.4
		if (SimogProperties.getInstance().isDataCreatedAfter3044(g.getData_creazione())) {

			// TICKET ALM #4222 - 3.04.4
			if (g.getCatMerc() != null && g.getCatMerc().size() >= 1) {

				// Verifica che sia stata indicata la categoria merceologica
				if (lotto.getCOD_CATEGORIA() == null || "".equals(lotto.getCOD_CATEGORIA())) {
					mEccezioni.addValidationField("label_CatMercLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Categoria Merceologica"));
				} else {
					// Verifica che la categoria merceologica indicata sia tra quelle contenute
					// nella gara
					boolean found = false;
					for (String codCatMercGara : g.getCatMerc()) {
						if (codCatMercGara.equals(lotto.getCOD_CATEGORIA())) {
							found = true;
							break;
						}
					}

					if (!found) {
						mEccezioni.addValidationField("label_CatMercLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_032);
					} else {

						try {
							if (SimogFlags.isFromWS()
									&& SimogProperties.getInstance().isDataCreatedAfterSoggAggr(g.getData_creazione())
									&& new IniziativaManager(connection, logger).checkBloccoCIGPerIniziative(g, lotto))
								mEccezioni.addValidationErr(
										Messaggi.SIMOG_VALIDAZIONE_266.replace("$1", "Categoria Merceologica"));
							else // Se la categoria e' 999 e ci sono le autodichiarazioni flaggate a si, riporta
								// l'errore
							{
								if (Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA())
										&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagNoAdesione()))
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Autodichiarazione di non interesse ad adesione presso le iniziative dei soggetti aggregatori"));

								if (Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA())
										&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagSANonClass()))
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Autodichiarazione di non assoggettabilita' della SA agli obblighi di cui al dPCM 24 Dicembre 2015"));
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			//			//FINE TICKET ALM #4222 - 3.04.4
			//			
		} else {
			if (!isEmpty(lotto.getCOD_CATEGORIA())) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Categoria Merceologica"));
			}

			// TICKET ALM #4223 - 3.04.4
			if (lotto.getFlagNoAdesione() != null && Costanti.FLAG_VALORE_SI.equals(lotto.getFlagNoAdesione()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
						"Autodichiarazione di non interesse ad adesione presso le iniziative dei soggetti aggregatori"));
			if (lotto.getFlagSANonClass() != null && Costanti.FLAG_VALORE_SI.equals(lotto.getFlagSANonClass()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
						"Autodichiarazione di non assoggettabilita' della SA agli obblighi di cui al dPCM 24 Dicembre 2015"));

			// FINET TICKET ALM #4223 - 3.04.4
		}
		// FINE TICKET ALM 3.04.4
		
		//3.04.8 - ticket 34470 - WARNING
		if( !lotto.getId_Scelta_Contraente().equalsIgnoreCase("15") && (g.getID_ESTREMA_URGENZA() == 2 && g.getURGENZA_DL133().equalsIgnoreCase("S")) ){
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_291);
		}

		// PP B302.4
		validaRipetizioni(lotto, dataCreazioneGara);

		// UN is3031_RFWEBGL02Active se la gara e stata creata prima della data di
		// attivazione MEVCUP
		// la sezione e disabilitata (o va ignorata per i WS) e non devo fare controlli
		boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(g.getData_creazione());
		//MEV 39162 3.04.8.1
//		if (SimogProperties.getInstance().isDataCreatedAfter30481(g.getData_creazione())) { MAD 53644 3.04.13
			if ((g.getID_MODO_REAL() == 9 || g.getID_MODO_REAL() == 17 || g.getID_MODO_REAL() == 18)) {
				if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
					mEccezioni.addValidationField("label_FlagCUPLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
				}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
					mEccezioni.addValidationField("label_FlagCUPLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
							+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
							+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
							+ "con risorse Comunitarie)"));
				}
			}else if (g.getID_SVOLGIMENTO() == 6) {
				if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
					mEccezioni.addValidationField("label_FlagCUPLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
				}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
					mEccezioni.addValidationField("label_FlagCUPLotto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
							+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
							+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
							+ "con risorse Comunitarie)"));
				}
			}else {
				boolean flagCupOk = true;
				if (SimogProperties.getInstance().isCUPAttivo() && okDataAttivazioneCup)
					flagCupOk = validaFlageCodiciCUP(lotto, g.getID_MODO_REAL(), g.getID_SVOLGIMENTO());// Ticket ALM #666

				if (!flagCupOk)
					mEccezioni.addValidationField("label_FlagCUPLotto"); // LABEL ERR
			}
//		}else { MAD 53644 3.04.13
//			boolean flagCupOk = true;
//			if (SimogProperties.getInstance().isCUPAttivo() && okDataAttivazioneCup)
//				flagCupOk = validaFlageCodiciCUP(lotto, g.getID_MODO_REAL(), g.getID_SVOLGIMENTO());// Ticket ALM #666
//
//			if (!flagCupOk)
//				mEccezioni.addValidationField("label_FlagCUPLotto"); // LABEL ERR
//		}
		//fine mev
		
		

		if (SimogProperties.getInstance().isCUPAttivo() && okDataAttivazioneCup) {
			validaTipologieAppaltoLotto(lotto);
		}

		this.validaPariOpportunita(lotto, g, false);
		
		//se viene dai ws controlla che non venga inserito un motivo deroga non più attivo
		//da web è impossibile perchè i motivi deroga non più attivi non vengono visualizzati
		if (SimogFlags.isFromWS()) {
			this.validaDerogaQualificazioneSA(lotto, g);
		}
		
	}
	
	//3.04.9 MEV 40610
	private void validaQualificazioneSA(Lotto lotto, Gara g, String sessionId) {
		String currentDate = PageHelper.getCurrentDate();
		
		//3.04.9 MEV 40610 se la MEV è ATTIVA faccio partire la logica
		if (currentDate.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
			//3.04.9.2 se il CIG esiste siamo in modifica e non chiamo il servizio
			if (lotto.getCIG() != null) {
				logger.info("servizio qualificazione-sa non chiamato perchè siamo in modifica");
			}else {
				//se mockata
				if (SimogProperties.getInstance().getQualificazioneIsMock().equals("true")) {
					MockValidaQualificazioneSA(lotto, g, sessionId);
					
					//NO MOCK-------------------------------------------------------------------
				}else if (SimogProperties.getInstance().getQualificazioneIsMock().equals("false")) { //se non è mockata
					logger.info("servizio qualificazione-sa no mock");
					//3.04.9.2 se la gara è una adesione non chiama il servizio
					if (g.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET ||
							g.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET ) {
						logger.info("servizio qualificazione-sa non chiamato perchè siamo la gara è una adesione");
					}else {
						//se non è stata inserita nessuna deroga o se tramite ws è stata inserita la deroga 11 allora faccio la chiamata
						//e se la scelta del contraente è diversa da 16 allora faccio la chiamata MAD alla MEV 40610
						//e se il campo motivo del collegamento è diverso dai valori 1 o 2 allora faccio la chiamata MEV 44994 3.04.11
						//e se l'id esclusione è diverso da 34 e da 16 allora faccio la chiamata MAD alla MEV 40610
						//e se il motivo dell'estrema urgenza è 1 oppure 2 allora faccio la chiamata MAD alla MEV 40610
						//(TOLTO perchè aggiunta una voce nelle deroghe)in pratica se il flag pnrr è SI la chiamata non la faccio mai perchè la prima condizione dell' AND sarà sempre FALSE
//						lotto.getFLAG_PNRR_PNC() != null && !lotto.getFLAG_PNRR_PNC().equals("") && isNFlag(lotto.getFLAG_PNRR_PNC())
						if ((lotto.getId_Scelta_Contraente() != null && !lotto.getId_Scelta_Contraente().equals("16")) &&
								(lotto.getID_ESCLUSIONE() != 34 && lotto.getID_ESCLUSIONE() != 22) &&
								(lotto.getID_MOTIVO_COLL_CIG() != null && !lotto.getID_MOTIVO_COLL_CIG().equals("1") && !lotto.getID_MOTIVO_COLL_CIG().equals("2")) && //MEV 44994 3.04.11
								(lotto.getID_ART_REGIME() != 44 && lotto.getID_ART_REGIME() != 45) && //MEV 3.04.10
								(g.getID_ESTREMA_URGENZA() != 1 && g.getID_ESTREMA_URGENZA() != 2) &&
								((lotto.getDerogaQualificazioneSA()== null || lotto.getDerogaQualificazioneSA().equals("")) || 
								(lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()))) {
							logger.info("controlli deroganti superati");
							
							Unirest.setTimeouts(0, 0);
							HttpResponse<String> response;
							logger.info("parametri prima chiamata----------------------------");
							logger.info("Codice Fisc SA: " + g.getCF_AMMINISTRAZIONE());
							logger.info("oggetto qualificazione: " + lotto.getTIPO_CONTRATTO_LOTTO());
							logger.info("importo qualificazione: " + lotto.getImporto_Lotto());
							logger.info("anno qualificazione: " + currentDate.substring(0, 4));
							logger.info("sessionid qualificazione: " + sessionId);
							logger.info("fine parametri prima chiamata----------------------------");
							try {
								response = Unirest.get(SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
										+ g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
										+ "&importo=" + lotto.getImporto_Lotto()
										+ "&anno=" + currentDate.substring(0, 4)
										+ "&sessionId=" + sessionId
										+ "&app=" + "@anac/microfrontend")
								  .header("Accept", "application/json")
								  .asString();
								//LOG CHIAMATA-----------------------------------------------------------
								if (response != null) {
									logger.info("request URL QUALIFICAZIONE-----------" + SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
										+ g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
										+ "&importo=" + lotto.getImporto_Lotto()
										+ "&anno=" + currentDate.substring(0, 4)
										+ "&sessionId=" + sessionId
										+ "&app=" + "@anac/microfrontend");
									logger.info("response URL QUALIFICAZIONE-----------" + response.getBody());
									logger.info("status URL QUALIFICAZIONE-----------" + response.getStatus());
								}else {
									logger.info("response null URL QUALIFICAZIONE-----------");
								}
								//fine LOG CHIAMATA-----------------------------------------------------------
								
								
								if (response.getStatus()==200) {
									JSONObject jsonObj = new JSONObject(response.getBody());
									logger.info(jsonObj);
									logger.info("FlagIsQualificataKO prima chiamata N");
									if (lotto.getFlagIsQualificataKO() == null || "".equals(lotto.getFlagIsQualificataKO())) {
										lotto.setFlagIsQualificataKO("N");
									}
									
									boolean isQualificataSA = jsonObj.getBoolean("data");
									if (!isQualificataSA) {
										logger.info("SA non qualificata dopo prima chiamata");
										//se il servizio risponde false e se la cpv appartiene a 713 o 714 e il tipo di contratto è diverso da L e
										//e non è stata inserita l'autodichiarazione di qualificazione (11)
										//allora setto a L e faccio la seconda chiamata
										if ((lotto.getId_CPV().substring(0,3).equals("713") || lotto.getId_CPV().substring(0,3).equals("714"))
												&& !lotto.getTIPO_CONTRATTO_LOTTO().equals("L") && !lotto.getDerogaQualificazioneSA().equals("11")) {
											lotto.setTIPO_CONTRATTO_LOTTO("L");
											
											logger.info("parametri seconda chiamata----------------------------");
											logger.info("Codice Fisc SA: " + g.getCF_AMMINISTRAZIONE());
											logger.info("oggetto qualificazione: " + lotto.getTIPO_CONTRATTO_LOTTO());
											logger.info("importo qualificazione: " + lotto.getImporto_Lotto());
											logger.info("anno qualificazione: " + currentDate.substring(0, 4));
											logger.info("sessionid qualificazione: " + sessionId);
											logger.info("fine parametri seconda chiamata----------------------------");
											
											response = Unirest.get(SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
													+ g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
													+ "&importo=" + lotto.getImporto_Lotto()
													+ "&anno=" + currentDate.substring(0, 4)
													+ "&sessionId=" + sessionId
													+ "&app=" + "@anac/microfrontend")
														  .header("Accept", "application/json")
														  .asString();	
											//LOG CHIAMATA-----------------------------------------------------------
											if (response != null) {
												logger.info("request URL QUALIFICAZIONE-----------" + SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
													+g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
													+ "&importo=" + lotto.getImporto_Lotto()
													+ "&anno=" + currentDate.substring(0, 4)
													+ "&sessionId=" + sessionId
													+ "&app=" + "@anac/microfrontend");
												logger.info("response URL QUALIFICAZIONE-----------" + response.getBody());
												logger.info("status URL QUALIFICAZIONE-----------" + response.getStatus());
											}else {
												logger.info("response null URL QUALIFICAZIONE-----------");
											}
											//fine LOG CHIAMATA-----------------------------------------------------------
											if (response.getStatus()==200) {
												jsonObj = new JSONObject(response.getBody());
												if (lotto.getFlagIsQualificataKO() == null || "".equals(lotto.getFlagIsQualificataKO())) {
													lotto.setFlagIsQualificataKO("N");
												}
												logger.info("FlagIsQualificataKO seconda chiamata N");
												isQualificataSA = jsonObj.getBoolean("data");
												if (!isQualificataSA) {
													logger.info("SIMOG_LOTTO_040 da seconda chiamata");
													mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_040);
												}
											}//se la seconda chiamata è andata KO setto in ogni caso il flagKO a S 
											//ma solo se non è stata inserita la deroga 11 mostro il messaggio di errore
											else if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("") && lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
												lotto.setFlagIsQualificataKO("S");
											}else if (lotto.getDerogaQualificazioneSA()!= null && lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
												lotto.setFlagIsQualificataKO("S");
											}else {
												lotto.setFlagIsQualificataKO("S");
												mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
											}
										}else {
											logger.info("SIMOG_LOTTO_040 da prima chiamata");
											mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_040);
										}
									}
								}//se la prima chiamata è andata KO setto in ogni caso il flagKO a S 
								//ma solo se non è stata inserita la deroga 11 mostro il messaggio di errore
								else if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("") && lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
									lotto.setFlagIsQualificataKO("S");
								}else if (lotto.getDerogaQualificazioneSA()!= null && lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
									lotto.setFlagIsQualificataKO("S");
								}else {
									lotto.setFlagIsQualificataKO("S");
									mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
								}
								
							} catch (UnirestException e) {
								// TODO Auto-generated catch block
								if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("") && lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
									lotto.setFlagIsQualificataKO("S");
								}else {
									lotto.setFlagIsQualificataKO("S");
									mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
								}
								
								e.printStackTrace();
							} catch (Exception ex) {
								// TODO Auto-generated catch block
								if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("") && lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
									lotto.setFlagIsQualificataKO("S");
								}else {
									lotto.setFlagIsQualificataKO("S");
									mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
								}
								ex.printStackTrace();
							}
						}else if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) { //se la deroga è stata inserita tramite web dopo aver ricevuto false
							if (lotto.getFlagIsQualificataKO() == null || "".equals(lotto.getFlagIsQualificataKO())) {
								lotto.setFlagIsQualificataKO("N");
							}
							logger.info("Chiamata al servizio non fatta in quanto è stata inserita la deroga tramite web");
						}else if (lotto.getDerogaQualificazioneSA()!= null && lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
							lotto.setFlagIsQualificataKO("S");
							logger.info("Chiamata al servizio non fatta in quanto è stata inserita la deroga 11 tramite web");
						}else {
							logger.info("Chiamata al servizio non fatta");
						}
					}
					
				}
			
			}
			
			
		}
		
	}
	
	private void MockValidaQualificazioneSA(Lotto lotto, Gara g, String sessionId) {
		logger.info("servizio qualificazione-sa mock");
		//se il flag pnrr è uguale a no e (se non è stata inserita nessuna deroga o se tramite ws è stata inserita la deroga 11) allora faccio la chiamata
		//in pratica se il flag pnrr è SI la chiamata non la faccio mai perchè la prima condizione dell' AND sarà sempre FALSE
		if ((lotto.getFLAG_PNRR_PNC() != null && !lotto.getFLAG_PNRR_PNC().equals("") && isNFlag(lotto.getFLAG_PNRR_PNC())) && 
				((lotto.getDerogaQualificazioneSA()== null || lotto.getDerogaQualificazioneSA().equals("")) || 
				(lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()))) {
			
			
//			Unirest.setTimeouts(0, 0);
//			HttpResponse<String> response;
			//se il servizio risponde false e se la cpv appartiene a 713 o 714 e il tipo di contratto è diverso da L e
			//e non è stata inserita l'autodichiarazione di qualificazione (11)
			//allora setto a L e faccio la seconda chiamata
			if (SimogProperties.getInstance().getQualificazioneResponse().equals("200")) {
				//lotto.setFlagIsQualificataKO("N");
				//boolean isQualificataSA = jsonObj.getBoolean("data");
				if (SimogProperties.getInstance().getQualificazioneIsQualificata().equals("false")) {
					if ((lotto.getId_CPV().substring(0,3).equals("713") || lotto.getId_CPV().substring(0,3).equals("714"))
							&& !lotto.getTIPO_CONTRATTO_LOTTO().equals("L") && !lotto.getDerogaQualificazioneSA().equals("11")) {
						lotto.setTIPO_CONTRATTO_LOTTO("L");
//							response = Unirest.get(SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
//									+ g.getCF_UTENTE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
//									+ "&importo=" + lotto.getImporto_Lotto()
//									+ "&anno=" + currentDate.substring(0, 4)
//									+ "&sessionId=" + sessionId
//									+ "&app=" + "")
//										  .header("Accept", "application/json")
//										  .asString();
//							jsonObj = new JSONObject(response.getBody());
						if (SimogProperties.getInstance().getQualificazioneResponse().equals("200")) {
							//isQualificataSA = jsonObj.getBoolean("data");
							//lotto.setFlagIsQualificataKO("N");
							if (SimogProperties.getInstance().getQualificazioneIsQualificata().equals("false")) {
								mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_040);
							}
						}else if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("") && lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
							//lotto.setFlagIsQualificataKO("S");
						}else if (lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
							//lotto.setFlagIsQualificataKO("S");
						}else {
							//lotto.setFlagIsQualificataKO("S");
							mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
						}
					}else {
						mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_040);
					}
				}
			}else if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("") && lotto.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
				//lotto.setFlagIsQualificataKO("S");
			}else if (lotto.getDerogaQualificazioneSA()!= null && lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
				//lotto.setFlagIsQualificataKO("S");
			}else {
				//lotto.setFlagIsQualificataKO("S");
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
			}
		}else if(lotto.getDerogaQualificazioneSA()!= null && !lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) { //se la deroga è stata inserita tramite web dopo aver ricevuto false
			//lotto.setFlagIsQualificataKO("N");
		}else if (lotto.getDerogaQualificazioneSA()!= null && lotto.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
			//lotto.setFlagIsQualificataKO("S");
		}
	}
	//fine 3.04.9 MEV 40610
	
	private void validaSezPariOpportunita(Lotto lotto, Gara g, boolean isIntegra) {
		// TICKET #31047 : TICKET PARI OPPORTUNITA'
		
					// controllo 18.A documento ANAC-MOD_DATI
					if (isEmptyOrZero(lotto.getFLAG_PNRR_PNC())) {

						mEccezioni.addValidationField("label_FlagPnrrPncLotto"); // LABEL ERR
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
								"L'appalto o concessione e' afferente gli investimenti pubblici finanziati, in tutto o in parte, "
										+ "con le risorse previste dal PNRR (Piano Nazionale di Ripresa e Resilienza) e/o dal PNC "
										+ "(Piano nazionale per gli investimenti complementari)?"));

						//MEV 34696 3.04.8 IF SUCCESSIV INSERITO qui INVECE DI METTERLO ALL'INIZIO DEL METODO
					} else	if (SimogProperties.getInstance().isDataCreatedAfter3047(g.getData_creazione()) || isIntegra) {
						if (isEmpty(lotto.getFLAG_DEROGA_ADESIONE())) { //mev 37010 3.04.8.1 se vuoto faccio i controlli con flga pnnrr
							if (isNFlag(lotto.getFLAG_PNRR_PNC())) {
								
								//MEV 39162 3.04.8.1
								//if (SimogProperties.getInstance().isDataCreatedAfter30481(g.getData_creazione())) { MAD 53644 3.04.13
									if ((g.getID_MODO_REAL() == 9 || g.getID_MODO_REAL() == 17 || g.getID_MODO_REAL() == 18)) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}else if (g.getID_SVOLGIMENTO() == 6) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}
								//}
								
								//FINE MEV 39162
								
								if (!isEmpty(lotto.getFLAG_PREVISIONE_QUOTA())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Previsione nel bando di gara, nell'avviso o nell'invito, "
													+ "dell'obbligo di assicurare, in caso di aggiudicazione del contratto, una quota pari almeno al 30 per cento, "
													+ "delle assunzioni necessarie per l'esecuzione del contratto o per la realizzazione di attivita ad esso "
													+ "connesse o strumentali, sia all'occupazione giovanile sia all'occupazione femminile (articolo 47, comma 4, D.L. 77/2021)? "));
								}
								if (!isEmpty(lotto.getQuotaGiovanile())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Previsione di una quota inferiore con " + "riferimento all'occupazione giovanile"));
								}
				
								if (!isEmpty(lotto.getQuotaFemminile())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Previsione di una quota inferiore con riferimento " + "all'occupazione femminile"));
								}
								
								if (!isEmpty(lotto.getElencoMotivoDeroga())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Motivo Deroga"));
								}
								
								if (!isEmpty(lotto.getFLAG_MISURE_PREMIALI())) {
									mEccezioni.addValidationErr(
											Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", " Previsione nel bando di gara, nell'avviso o "
													+ "nell'invito di ulteriori misure premiali che attribuiscono un punteggio aggiuntivo all'offerente o al candidato"));
								}
				
								if (!isEmpty(lotto.getElencoMisurePremiali())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Misure Premiali"));
								}
								
							} else {
								
								//MEV 39162 3.04.8.1
//								if (SimogProperties.getInstance().isDataCreatedAfter30481(g.getData_creazione())) { MAD 53644 3.04.13
									if ((g.getID_MODO_REAL() == 9 || g.getID_MODO_REAL() == 17 || g.getID_MODO_REAL() == 18)) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}else if (g.getID_SVOLGIMENTO() == 6) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}
								//}
								
								//FINE MEV 39162
								
								//MEV 34704 3.04.8
								//l'errore viene sollevato solo se diverso da AQ
								if ((g.getID_MODO_REAL() != 9 && g.getID_MODO_REAL() != 17 && g.getID_MODO_REAL() != 18 && g.getID_SVOLGIMENTO() != 6)) {
									if (!isNFlag(lotto.getFLAG_PNRR_PNC())) {
											if (isNFlag(lotto.getFLAG_CUP())) {
												mEccezioni.addValidationField("label_FLAG_PNRR_PNCLotto");
												mEccezioni.addValidationField("label_FlagCUPLotto");
							
												mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_282);
											}
									}
								}
								
								//FINE MEV 34704 3.04.8
				
								// validazione previsione quota
								if (isEmptyOrZero(lotto.getFLAG_PREVISIONE_QUOTA())) {
									mEccezioni.addValidationField("label_FlagPrevisioneQuota"); // LABEL ERR
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
											"Previsione nel bando di gara, nell'avviso o nell'invito, "
													+ "dell'obbligo di assicurare, in caso di aggiudicazione del contratto, una quota pari almeno al 30 per cento, "
													+ "delle assunzioni necessarie per l'esecuzione del contratto o per la realizzazione di attivita ad esso "
													+ "connesse o strumentali, sia all'occupazione giovanile sia all'occupazione femminile (articolo 47, comma 4, D.L. 77/2021)?"));
				
								} else {
				
				
									if(isYFlag(lotto.getFLAG_PREVISIONE_QUOTA())||isNFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {	
				
										if (!isEmpty(lotto.getQuotaGiovanile())) {
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
													"Previsione di una quota inferiore con " + "riferimento all'occupazione giovanile"));
										}
				
										if (!isEmpty(lotto.getQuotaFemminile())) {
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
													"Previsione di una quota inferiore con riferimento " + "all'occupazione femminile"));
										}				
										
									}else if(isQFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {//qui
				
										Double v1 = null;
										Double v2 = null;
				
				
										if (isEmptyOrZero(lotto.getQuotaFemminile()) && isEmptyOrZero(lotto.getQuotaGiovanile())) {
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_286.replace("$1",
													"Previsione di una quota inferiore con riferimento all'occupazione femminile"));
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_286.replace("$1",
													"Previsione di una quota inferiore con riferimento all'occupazione giovanile"));
				
										} else {
											//MAC 35692 3.04.8
											if (!isEmptyOrZero(lotto.getQuotaFemminile()) && !isEmptyOrZero(lotto.getQuotaGiovanile())) {
												v1 = lotto.getQuotaFemminile().doubleValue();
												v2 = lotto.getQuotaGiovanile().doubleValue();
												if (v1 >= 30 && v2 >= 30) {
													mEccezioni.addValidationField("labelQuotaFemminile");
													mEccezioni.addValidationField("labelQuotaGiovanile");
													mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_289.replace("$1",
															"Previsione di una quota inferiore con riferimento all'occupazione femminile"));
												}
												
											}
//											if (!isEmptyOrZero(lotto.getQuotaFemminile())) {
//												v1 = lotto.getQuotaFemminile().doubleValue();						
//												if (v1 >= 30 || v1 < 0) {
//													mEccezioni.addValidationField("labelQuotaFemminile");
//													mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_284.replace("$1",
//															"Previsione di una quota inferiore con riferimento all'occupazione femminile"));
//												}
			//	
//											}
//											if (!isEmptyOrZero(lotto.getQuotaGiovanile())) {
//												v2 = lotto.getQuotaGiovanile().doubleValue();
//												if (v2 >= 30 || v2 < 0) {
//													mEccezioni.addValidationField("labelQuotaGiovanile");
//													mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_284.replace("$1",
//															"Previsione di una quota inferiore con riferimento all'occupazione giovanile"));
//												}
//											}
											//FINE MAC 35692 3.04.8
				
										}
									}
								}
								
								// validazione Motivo Deroga
								if (isQFlag(lotto.getFLAG_PREVISIONE_QUOTA()) || isNFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {
				
									if (lotto.getElencoMotivoDeroga() == null) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Motivo Deroga"));
									}
									if (lotto.getElencoMotivoDeroga().size() == 0) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104c.replace("$1", "Motivo Deroga"));
									}
								}else if(isYFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {
									if (!isEmpty(lotto.getElencoMotivoDeroga())) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Motivo Deroga"));
									}
								}
				
								// validazione Misure Premiali
								if (isEmptyOrZero(lotto.getFLAG_MISURE_PREMIALI())) {
									mEccezioni.addValidationField("label_FlagMisurePremialiLotto"); // LABEL ERR
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Previsione nel bando di gara, nell'avviso o nell'invito di "
											+ "ulteriori misure premiali che attribuiscono un punteggio aggiuntivo all'offerente o al candidato"));
				
								} else if (isYFlag(lotto.getFLAG_MISURE_PREMIALI())) {
									if (lotto.getElencoMisurePremiali() == null) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Misure Premiali"));
									}
									if (lotto.getElencoMisurePremiali().size() == 0) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104b.replace("$1", "Misure Premiali"));
									}
								}
							}
						}else { //mev 37010 3.04.8.1 altrimenti faccio i controlli su deroga adesione ignorando flag pnrr
							if (!isNFlag(lotto.getFLAG_DEROGA_ADESIONE())) {
								
								//MEV 39162 3.04.8.1
								//if (SimogProperties.getInstance().isDataCreatedAfter30481(g.getData_creazione())) { MAD 53644 3.04.13
									if ((g.getID_MODO_REAL() == 9 || g.getID_MODO_REAL() == 17 || g.getID_MODO_REAL() == 18)) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}else if (g.getID_SVOLGIMENTO() == 6) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}
								//}
								
								//FINE MEV 39162
								
								if (!isEmpty(lotto.getFLAG_PREVISIONE_QUOTA())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Previsione nel bando di gara, nell'avviso o nell'invito, "
													+ "dell'obbligo di assicurare, in caso di aggiudicazione del contratto, una quota pari almeno al 30 per cento, "
													+ "delle assunzioni necessarie per l'esecuzione del contratto o per la realizzazione di attivita ad esso "
													+ "connesse o strumentali, sia all'occupazione giovanile sia all'occupazione femminile (articolo 47, comma 4, D.L. 77/2021)? "));
								}
								if (!isEmpty(lotto.getQuotaGiovanile())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Previsione di una quota inferiore con " + "riferimento all'occupazione giovanile"));
								}
				
								if (!isEmpty(lotto.getQuotaFemminile())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
											"Previsione di una quota inferiore con riferimento " + "all'occupazione femminile"));
								}
								
								if (!isEmpty(lotto.getElencoMotivoDeroga())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Motivo Deroga"));
								}
								
								if (!isEmpty(lotto.getFLAG_MISURE_PREMIALI())) {
									mEccezioni.addValidationErr(
											Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", " Previsione nel bando di gara, nell'avviso o "
													+ "nell'invito di ulteriori misure premiali che attribuiscono un punteggio aggiuntivo all'offerente o al candidato"));
								}
				
								if (!isEmpty(lotto.getElencoMisurePremiali())) {
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Misure Premiali"));
								}
								
							} else {
								
								//MEV 39162 3.04.8.1
								//if (SimogProperties.getInstance().isDataCreatedAfter30481(g.getData_creazione())) { MAD 53644 3.04.13
									if ((g.getID_MODO_REAL() == 9 || g.getID_MODO_REAL() == 17 || g.getID_MODO_REAL() == 18)) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}else if (g.getID_SVOLGIMENTO() == 6) {
										if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
										}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
											mEccezioni.addValidationField("label_FlagCUPLotto");
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
													+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
													+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
													+ "con risorse Comunitarie)"));
										}
									}
								//}
								
								//FINE MEV 39162
								
								//MEV 34704 3.04.8
								//l'errore viene sollevato solo se diverso da AQ
								if ((g.getID_MODO_REAL() != 9 && g.getID_MODO_REAL() != 17 && g.getID_MODO_REAL() != 18 && g.getID_SVOLGIMENTO() != 6)) {
									if (!isNFlag(lotto.getFLAG_PNRR_PNC())) {
											if (isNFlag(lotto.getFLAG_CUP())) {
												mEccezioni.addValidationField("label_FLAG_PNRR_PNCLotto");
												mEccezioni.addValidationField("label_FlagCUPLotto");
							
												mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_282);
											}
									}
								}
								
								//FINE MEV 34704 3.04.8
				
								// validazione previsione quota
								if (isEmptyOrZero(lotto.getFLAG_PREVISIONE_QUOTA())) {
									mEccezioni.addValidationField("label_FlagPrevisioneQuota"); // LABEL ERR
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
											"Previsione nel bando di gara, nell'avviso o nell'invito, "
													+ "dell'obbligo di assicurare, in caso di aggiudicazione del contratto, una quota pari almeno al 30 per cento, "
													+ "delle assunzioni necessarie per l'esecuzione del contratto o per la realizzazione di attivita ad esso "
													+ "connesse o strumentali, sia all'occupazione giovanile sia all'occupazione femminile (articolo 47, comma 4, D.L. 77/2021)?"));
				
								} else {
				
				
									if(isYFlag(lotto.getFLAG_PREVISIONE_QUOTA())||isNFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {	
				
										if (!isEmpty(lotto.getQuotaGiovanile())) {
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
													"Previsione di una quota inferiore con " + "riferimento all'occupazione giovanile"));
										}
				
										if (!isEmpty(lotto.getQuotaFemminile())) {
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
													"Previsione di una quota inferiore con riferimento " + "all'occupazione femminile"));
										}				
										
									}else if(isQFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {//qui
				
										Double v1 = null;
										Double v2 = null;
				
				
										if (isEmptyOrZero(lotto.getQuotaFemminile()) && isEmptyOrZero(lotto.getQuotaGiovanile())) {
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_286.replace("$1",
													"Previsione di una quota inferiore con riferimento all'occupazione femminile"));
											mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_286.replace("$1",
													"Previsione di una quota inferiore con riferimento all'occupazione giovanile"));
				
										} else {
											//MAC 35692 3.04.8
											if (!isEmptyOrZero(lotto.getQuotaFemminile()) && !isEmptyOrZero(lotto.getQuotaGiovanile())) {
												v1 = lotto.getQuotaFemminile().doubleValue();
												v2 = lotto.getQuotaGiovanile().doubleValue();
												if (v1 >= 30 && v2 >= 30) {
													mEccezioni.addValidationField("labelQuotaFemminile");
													mEccezioni.addValidationField("labelQuotaGiovanile");
													mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_289.replace("$1",
															"Previsione di una quota inferiore con riferimento all'occupazione femminile"));
												}
												
											}
//											if (!isEmptyOrZero(lotto.getQuotaFemminile())) {
//												v1 = lotto.getQuotaFemminile().doubleValue();						
//												if (v1 >= 30 || v1 < 0) {
//													mEccezioni.addValidationField("labelQuotaFemminile");
//													mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_284.replace("$1",
//															"Previsione di una quota inferiore con riferimento all'occupazione femminile"));
//												}
			//	
//											}
//											if (!isEmptyOrZero(lotto.getQuotaGiovanile())) {
//												v2 = lotto.getQuotaGiovanile().doubleValue();
//												if (v2 >= 30 || v2 < 0) {
//													mEccezioni.addValidationField("labelQuotaGiovanile");
//													mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_284.replace("$1",
//															"Previsione di una quota inferiore con riferimento all'occupazione giovanile"));
//												}
//											}
											//FINE MAC 35692 3.04.8
				
										}
									}
								}
								
								// validazione Motivo Deroga
								if (isQFlag(lotto.getFLAG_PREVISIONE_QUOTA()) || isNFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {
				
									if (lotto.getElencoMotivoDeroga() == null) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Motivo Deroga"));
									}
									if (lotto.getElencoMotivoDeroga().size() == 0) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104b.replace("$1", "Motivo Deroga"));
									}
								}else if(isYFlag(lotto.getFLAG_PREVISIONE_QUOTA())) {
									if (!isEmpty(lotto.getElencoMotivoDeroga())) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Motivo Deroga"));
									}
								}
				
								// validazione Misure Premiali
								if (isEmptyOrZero(lotto.getFLAG_MISURE_PREMIALI())) {
									mEccezioni.addValidationField("label_FlagMisurePremialiLotto"); // LABEL ERR
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Previsione nel bando di gara, nell'avviso o nell'invito di "
											+ "ulteriori misure premiali che attribuiscono un punteggio aggiuntivo all'offerente o al candidato"));
				
								} else if (isYFlag(lotto.getFLAG_MISURE_PREMIALI())) {
									if (lotto.getElencoMisurePremiali() == null) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Misure Premiali"));
									}
									if (lotto.getElencoMisurePremiali().size() == 0) {
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104b.replace("$1", "Misure Premiali"));
									}
								}
							}
						}
							
					}
		
	}

	/**
	 * TICKET 31047: VALIDA PARI OPPORTUNITA'
	 * 
	 * @param lotto
	 * @param g
	 */
	private void validaPariOpportunita(Lotto lotto, Gara g, boolean isIntegra) {
		
		//MEV 37010 3.04.8.1 controllo su campo flag deroga adesione
		String currentDate = PageHelper.getCurrentDate();
		//SE LA MEV è ATTIVATA 
		if (currentDate.compareTo(SimogProperties.getInstance().getDataAttivazioneMev37010()) >= 0) {
				if (g.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET ||
						g.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE ||
						g.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE ||
						g.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET ) {
					
					try {
						LottoManager lm = new LottoManager(connection, logger);
						GaraManager gm = new GaraManager(connection, logger);
						Lotto lottoAQ = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO()).get(0);
						
						if (lottoAQ.getData_Pubblicazione() != null) {
							String dataPubblicazioneAQ = PageHelper.getFormattedDBDate(lottoAQ.getData_Pubblicazione());
							String dataCreazioneGaraAdesione = PageHelper.getFormattedDBDate(g.getData_creazione());
							//Se il cig AQ padre pubblicato prima delle linee guida e la gara di adesione creata dopo la data 3.04.7
							if (SimogProperties.getInstance()
									.isDataCreatedBeforeDerogaAdesione(dataPubblicazioneAQ) &&
									SimogProperties.getInstance()
									.isDataCreatedAfterDerogaAdesione(dataCreazioneGaraAdesione) &&
									SimogProperties.getInstance()
									.isDataCreatedAfter3047(dataCreazioneGaraAdesione)) {
								if (isEmpty(lotto.getFLAG_DEROGA_ADESIONE())) {
									mEccezioni.addValidationField("label_FlagDerogaAdesione");
									mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "flag deroga per adesione ad AQ/Convenzione precedente alle linee guida DPO sull'articolo 47 del DL 77/2021 e ss.mm.i."));
								}
								validaSezPariOpportunita(lotto, g, isIntegra);
							}else if (!isEmpty(lotto.getFLAG_DEROGA_ADESIONE()))  {
								mEccezioni.addValidationField("label_FlagDerogaAdesione");
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "flag deroga per adesione ad AQ/Convenzione precedente alle linee guida DPO sull'articolo 47 del DL 77/2021 e ss.mm.i."));
								validaSezPariOpportunita(lotto, g, isIntegra);
							}
							//se i dati pari opportunita sono presenti nel AQ allora il figlio eredita tutti i campi dal padre
							else if (lottoAQ.getFLAG_PNRR_PNC()!= null && !lottoAQ.getFLAG_PNRR_PNC().equals("")) {
								//mev 41375 3.04.9 se il flag del padre è si allora eredita i campi e li salva a prescindere
								//che il figlio cambi il flag pnrr a no
								if (!isNFlag(lottoAQ.getFLAG_PNRR_PNC())) {
									lotto.setFLAG_PREVISIONE_QUOTA(lottoAQ.getFLAG_PREVISIONE_QUOTA());
									lotto.setQuotaGiovanile(lottoAQ.getQuotaGiovanile());
									lotto.setQuotaFemminile(lottoAQ.getQuotaFemminile());
									MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(connection, logger);
									lotto.setElencoMotivoDeroga(motivoDerogaManager.loadManyNoFineValiditaMotivoDerogaBean(lottoAQ.getId_Lotto()));	
									lotto.setFLAG_MISURE_PREMIALI(lottoAQ.getFLAG_MISURE_PREMIALI());
									MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(connection, logger);
									lotto.setElencoMisurePremiali(misuraPremialeManager.loadManyNoFineValiditaMisuraPremialeBean(lottoAQ.getId_Lotto()));
								}
//								lotto.setFLAG_PNRR_PNC(lottoAQ.getFLAG_PNRR_PNC());
//								
//								mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111);
								//se eredita i dati il validaSezPariOpportunita(lotto, g, isIntegra); va fatto solo se eredita il si o se lo sposta a si
								if (!isNFlag(lotto.getFLAG_PNRR_PNC())) {
									validaSezPariOpportunita(lotto, g, isIntegra);
								}
							}else {//se non eredita i dati perchè non presenti
								validaSezPariOpportunita(lotto, g, isIntegra);
							}
							
							
						}else if (!isEmpty(lotto.getFLAG_DEROGA_ADESIONE()))  {
							mEccezioni.addValidationField("label_FlagDerogaAdesione");
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "flag deroga per adesione ad AQ/Convenzione precedente alle linee guida DPO sull'articolo 47 del DL 77/2021 e ss.mm.i."));
							validaSezPariOpportunita(lotto, g, isIntegra);
						}else {//se non eredita i dati perchè non presenti
							validaSezPariOpportunita(lotto, g, isIntegra);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					validaSezPariOpportunita(lotto, g, isIntegra);
				}
		}else {
			validaSezPariOpportunita(lotto, g, isIntegra);
		}
				//fine MEV 37010 3.04.8.1
		}
	
	//MEV 3.04.10 43227
	private void validaModificaDatiPerfezionamento(Lotto lotto, Gara g, boolean isIntegra) {
		String dataScadenzaPagamenti = lotto.getDATA_SCADENZA_PAGAMENTI();
		String dataPubblicazione = lotto.getData_Pubblicazione();
		String dataScadenzaRichiestaInvito = lotto.getDataScadenzaRichiestaInvito();
		//se fase 2 di proc ristretta
		if ((lotto.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(lotto.getDATA_SCADENZA_PAGAMENTI()))
				&&(lotto.getId_Scelta_Contraente().equals("2") || lotto.getId_Scelta_Contraente().equals("13") || lotto.getId_Scelta_Contraente().equals("25"))
				&& (lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito()))
				&& (lotto.getDataLetteraInvito() != null && !"".equals(lotto.getDataLetteraInvito()))) {

			// Restituisce un errore specifico nel caso in cui la data
			// di Scadenza pagamenti sia precedente alla data di pubblicazione
			// o contemporanea : non ammesso
//			3.04.8 34190 fix
	
			if ((Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) && dataPubblicazione != null
					&& dataScadenzaPagamenti != null) {
				int dateValide = dataPubblicazione.compareTo(dataScadenzaPagamenti);
				if (dateValide >= 0) {
					// PP 3.02.1.6 rilassato il controllo, solo se le date sono uguali
					if (dateValide == 0)
						mEccezioni.addValidationWarn(Messaggi.SIMOG_LOTTO_018e);
					else {
						mEccezioni.addValidationField("label_DataOfferte"); // LABEL ERR
						mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
						mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_018e);
					}
				}
			}
			validaData(dataScadenzaPagamenti, "Data di scadenza per la presentazione delle offerte",
					true);
			if (lotto.getDataLetteraInvito() != null && !"".equals(lotto.getDataLetteraInvito())) {
			validaOrdineDate(lotto.getDataLetteraInvito(), "Data della lettera di invito", dataScadenzaPagamenti,
					"Data di scadenza per la presentazione delle offerte", false, false);
			}
			
			if (SimogFlags.is3025_RFWEBGL02Active()) {
				// PP se adesione il campo ora non viene valorizzato e non va controllato
//				3.04.8 34190 fix
				if (Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) {
					String oraScadenzaPagamenti = lotto.getORA_SCADENZA();
					if (oraScadenzaPagamenti == null || "".equals(oraScadenzaPagamenti)) {
						// piccinini 30/10/2013 facoltativa se proviene dai ws altrimenti obbligatoria!
//						if (SimogFlags.isFromWS())
//							mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
//									"Ora scadenza presentazione offerte (hh:mm)"));
//						else {
							mEccezioni.addValidationField("label_OraScadenza"); // LABEL ERR
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
									"Ora scadenza presentazione offerte (hh:mm)"));
							// messo dentro altrimenti doppio errore
						//}
					} else {
						if (!isValidTime(oraScadenzaPagamenti)) {
							mEccezioni.addValidationField("label_OraScadenza"); // LABEL ERR
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1",
									"Ora scadenza presentazione offerte (hh:mm)"));
						}
					}
				}
			}
			
			//se fase 1 di proc ristretta
			}else if ((lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito()))
					&& (lotto.getId_Scelta_Contraente().equals("2") || lotto.getId_Scelta_Contraente().equals("13") || lotto.getId_Scelta_Contraente().equals("25"))) {
				validaData(dataScadenzaRichiestaInvito,
						"Data di scadenza per la presentazione della richiesta di invito", true);
				validaOrdineDate(dataPubblicazione, "Data Pubblicazione", dataScadenzaRichiestaInvito,
						"Data di scadenza per la presentazione della richiesta di invito", false, true);
				if (lotto.getDataLetteraInvito() != null && !"".equals(lotto.getDataLetteraInvito())) {
					validaOrdineDate(dataScadenzaRichiestaInvito,
							"Data di scadenza per la presentazione della richiesta di invito", lotto.getDataLetteraInvito(),
							"Data della lettera di invito", false, false);
				}
				//se gara normale
			}else  {

				
//				if (dataScadenzaRichiestaInvito != null && !"".equals(dataScadenzaRichiestaInvito)) {
//					mEccezioni.addValidationErr(Messaggi.SIMOG_MODIFICA_PERFEZIONAMENTO_001);
//				}
		
				// Restituisce un errore specifico nel caso in cui la data
				// di Scadenza pagamenti sia precedente alla data di pubblicazione
				// o contemporanea : non ammesso
//				3.04.8 34190 fix
				if ((Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) && dataPubblicazione != null
						&& dataScadenzaPagamenti != null) {
					int dateValide = dataPubblicazione.compareTo(dataScadenzaPagamenti);
					if (dateValide >= 0) {
						// PP 3.02.1.6 rilassato il controllo, solo se le date sono uguali
						if (dateValide == 0)
							mEccezioni.addValidationWarn(Messaggi.SIMOG_LOTTO_018e);
						else {
							mEccezioni.addValidationField("label_DataOfferte"); // LABEL ERR
							mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
							mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_018e);
						}
					}
				}
				validaData(dataScadenzaPagamenti, "Data di scadenza per la presentazione delle offerte",
						true);
				if (lotto.getDataLetteraInvito() != null && !"".equals(lotto.getDataLetteraInvito())) {
				validaOrdineDate(lotto.getDataLetteraInvito(), "Data della lettera di invito", dataScadenzaPagamenti,
						"Data di scadenza per la presentazione delle offerte", false, false);
				}
				
				if (SimogFlags.is3025_RFWEBGL02Active()) {
					// PP se adesione il campo ora non viene valorizzato e non va controllato
//					3.04.8 34190 fix
					if (Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) {
						String oraScadenzaPagamenti = lotto.getORA_SCADENZA();
						if (oraScadenzaPagamenti == null || "".equals(oraScadenzaPagamenti)) {
							// piccinini 30/10/2013 facoltativa se proviene dai ws altrimenti obbligatoria!
//							if (SimogFlags.isFromWS())
//								mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
//										"Ora scadenza presentazione offerte (hh:mm)"));
//							else {
								mEccezioni.addValidationField("label_OraScadenza"); // LABEL ERR
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
										"Ora scadenza presentazione offerte (hh:mm)"));
								// messo dentro altrimenti doppio errore
							//}
						} else {
							if (!isValidTime(oraScadenzaPagamenti)) {
								mEccezioni.addValidationField("label_OraScadenza"); // LABEL ERR
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1",
										"Ora scadenza presentazione offerte (hh:mm)"));
							}
						}
					}
				}
				
			}
		
		
		
		
				
				
				
				
				
				
				
	}
	
	
	
	//fine MEV 3.04.10 43227
	
	private void validaDerogaQualificazioneSA(Lotto l, Gara g) {
		String currentDate = PageHelper.getCurrentDate();
		LottoManager lm = new LottoManager(connection, logger);
		try {
			if (l.getDerogaQualificazioneSA() != null) {
				List<DerogaQualificazioneSABean> listaDeroghe = lm.getAllDerogaQualificazioneSA();
				for (DerogaQualificazioneSABean derogaQualificazioneSABean : listaDeroghe) {
					if (l.getDerogaQualificazioneSA().equals(derogaQualificazioneSABean.getIdDerogaQualificazioneSA().toString())) {
						if (derogaQualificazioneSABean.getDataFineValidita() != null) {
							//se la deroga inserita oggi ha data fine validità precedente  alla data odierna solleva l'errore
							if (currentDate.compareTo(derogaQualificazioneSABean.getDataFineValidita()) >= 0) { 
								mEccezioni.addValidationField("label_FlagDerogaAdesione");
								mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_042);
							}
						}
						if (derogaQualificazioneSABean.getDataInizioValidita() != null) {
							//se la deroga inserita oggi ha data inizio validità successiva  alla data odierna solleva l'errore
							if (currentDate.compareTo(derogaQualificazioneSABean.getDataInizioValidita()) < 0) { 
								mEccezioni.addValidationField("label_FlagDerogaAdesione");
								mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_042);
							}
						}
					}
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if (l.getDerogaQualificazioneSA() != null && l.getDerogaQualificazioneSA().equals("12")) {
//			if (currentDate.compareTo("20230901") >= 0) { //sistemare prendendo la data dal db
//				mEccezioni.addValidationField("label_FlagDerogaAdesione");
//				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_042);
//			}
//		}

		
	}
	

	private void validaEsclusi(Lotto lotto) {

		if (isEmpty(lotto.getFLAG_ESCLUSO())) {
			mEccezioni.addValidationField("label_ContrattoRegimeLotto"); // LABEL ERR
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Contratto escluso"));
		} else {
			if (Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_ESCLUSO()) && isEmptyOrZero(lotto.getID_ESCLUSIONE())) {
				mEccezioni.addValidationField("label_EsclusioneLotto"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Esclusione"));
			}
			if (Costanti.FLAG_VALORE_NO.equals(lotto.getFLAG_ESCLUSO()) && !isEmptyOrZero(lotto.getID_ESCLUSIONE())) {
				mEccezioni.addValidationField("label_EsclusioneLotto"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Esclusione")
						+ ". Il campo 'Contratto escluso' e' stato impostato a NO");
			}
		}

		GaraManager gm = new GaraManager(connection, logger);
		Gara g = null;
		try {
			g = gm.getGara(lotto.getId_Gara());
			if (!isEmptyOrZero(lotto.getID_ESCLUSIONE())) {
				// TICKET ALM - 3.04.2 NG
				// Controllo la validita' dell'art. di esclusione con la data di creazione gara
				if (!artEsclusioneValido(lotto.getID_ESCLUSIONE(), g.getData_creazione()))
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Esclusione"));
				// organi cosituzionali la voce riservata e' ammessa solo per OOCC
				else {
					if (SimogFlags.isOrganiCostActive() && !g.isOrganoCost()
							&& Costanti.ART_ESCLUSIONE_OOCC.equals(String.valueOf(lotto.getID_ESCLUSIONE()).trim()))
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Articolo esclusione"));
				}
			}

			// TICKET ALM - 3.04.2 2005
			if (SimogFlags.is3042Active()
					&& SimogProperties.getInstance().isDataCreatedAfter3042(g.getData_creazione())) {
				if (isEmptyOrZero(lotto.getFLAG_REGIME())) {// Se flag non valorizzato
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
							"Contratto regime particolare di appalto (speciale o alleggerito)"));
				} else if (isYFlag(lotto.getFLAG_REGIME()) && isYFlag(lotto.getFLAG_ESCLUSO())) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_029
							.replace("$2", "Contratto regime particolare di appalto (speciale o alleggerito)")
							.replace("$1", "Contratto escluso o rientrante nel regime alleggerito"));
				} else // Se flag e' Si ma non e' stato indicato un articolo
					if (isEmptyOrZero(lotto.getID_ART_REGIME()) && isYFlag(lotto.getFLAG_REGIME())) {
						mEccezioni.addValidationField("label_RegimeParticolareLotto"); // LABEL ERR
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Regime particolare di appalto"));
					} else if (isNFlag(lotto.getFLAG_REGIME()) && !isEmptyOrZero(lotto.getID_ART_REGIME())) { // Se flag e'
						// No ma e'
						// stato
						// indicato
						// un
						// articolo
						mEccezioni.addValidationField("label_RegimeParticolareLotto"); // LABEL ERR
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
								"Regime particolare di appalto")
								+ ".  Il campo 'Contratto regime particolare di appalto (speciale o alleggerito)' e' stato impostato a NO");
					} else if (!isEmptyOrZero(lotto.getID_ART_REGIME())
							&& !articoliRegimeValido(lotto.getID_ART_REGIME(), g.getData_creazione())) { // Se e' stato
						// indicato un
						// articolo non
						// valido
						mEccezioni.addValidationField("label_RegimeParticolareLotto"); // LABEL ERR
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Regime particolare di appalto"));
					} else if (!isEmptyOrZero(g.getID_ALLEGATO_IX()) && !isEmptyOrZero(lotto.getID_ART_REGIME())
							&& !artRegimeConsentito(lotto.getID_ART_REGIME())) { // Se allegato IX e' stato impostato,
						// selezionare solo una voce consentita
						mEccezioni.addValidationField("label_RegimeParticolareLotto"); // LABEL ERR
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_234.replace("$1", "Regime particolare di appalto"));
					}
				// Se e' selezionata una voce relativa all'allegato IX ma l'allegato IX in gara
				// non stato selezionat, mostra warning
				if (!isEmptyOrZero(lotto.getID_ART_REGIME()) && artRegimeConsentito(lotto.getID_ART_REGIME())
						&& isEmptyOrZero(g.getID_ALLEGATO_IX()))
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_235.replace("$1",
							"Modalita' di indizione servizi di cui all'allegato IX"));

			} else {
				// Se la gara e' antecedente e i campi sono valorizzati, inserire blocco per
				// campi non previsti
				if (!isEmptyOrZero(lotto.getFLAG_REGIME())) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
							"Contratto regime particolare di appalto (speciale o alleggerito)"));
				}
				if (!isEmptyOrZero(lotto.getID_ART_REGIME())) {
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Regime particolare di appalto"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// FINE TICKET ALM - 3.04.2 NG

	}

	private void validaRipetizioni(Lotto lotto, long dataCreazioneGara) {

		// TICKET ALM - 3.04.3 #7849
		if (SimogFlags.is3043Active()
				&& dataCreazioneGara < SimogProperties.getInstance().getDataAttivazione3043Timestamp()) {
			// i flag sono in alternativa
			if (!isEmpty(lotto.getFLAG_PREVEDE_RIP()) && !isEmpty(lotto.getFLAG_RIPETIZIONE())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_PREVEDE_RIP())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_RIPETIZIONE())) {
				mEccezioni.addValidationField("label_PrevedeRipetizioniLotto"); // LABEL ERR
				mEccezioni.addValidationField("label_RipetizioneLotto"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_024);
			}
		} else {
			if (!isEmpty(lotto.getFLAG_PREVEDE_RIP()) && !isEmptyOrZero(lotto.getID_MOTIVO_COLL_CIG())
					&& !Costanti.COLL_CIG_NESSUNA.equals(lotto.getID_MOTIVO_COLL_CIG())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_PREVEDE_RIP()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_024a);

			// Controllo FLAG_RIPETIZIONE non richiesto
			if (!isEmpty(lotto.getFLAG_RIPETIZIONE()))
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Ripetizione di precedente contratto"));
		}
		// FINE TICKET ALM - 3.04.3 #7849

		// se flag ripetizione e' SI allora il CIG e' obbligatorio
		if (SimogFlags.is3043Active()
				&& dataCreazioneGara < SimogProperties.getInstance().getDataAttivazione3043Timestamp()) {
			if (!isEmpty(lotto.getFLAG_RIPETIZIONE()) && Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_RIPETIZIONE())
					&& isEmpty(lotto.getCIG_ORIGINE_RIP())) {
				mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "CIG collegato").replace("$2",
						"Ripetizione di precedente contratto") + " a SI");
			}
			// se flag ripetizione non e SI allora il CIG non va inserito
			if (!Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_RIPETIZIONE()) && !isEmpty(lotto.getCIG_ORIGINE_RIP())) {
				mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_204.replace("$1", "CIG collegato"));
			}
		} else {
			if (!isEmptyOrZero(lotto.getID_MOTIVO_COLL_CIG())
					&& !Costanti.COLL_CIG_NESSUNA.equals(lotto.getID_MOTIVO_COLL_CIG())
					&& isEmpty(lotto.getCIG_ORIGINE_RIP())) {
				mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "CIG collegato").replace("$2",
						"L'appalto deriva da una delle seguenti ipotesi di collegamento?"));
			}
			if (!isEmptyOrZero(lotto.getID_MOTIVO_COLL_CIG())
					&& Costanti.COLL_CIG_NESSUNA.equals(lotto.getID_MOTIVO_COLL_CIG())
					&& !isEmpty(lotto.getCIG_ORIGINE_RIP())) {
				mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_204.replace("$1", "CIG Collegato"));
			}
		}

		// TICKET ALM #4210
		// if(!isEmpty(lotto.getId_Scelta_Contraente()) &&
		// lotto.getId_Scelta_Contraente().equals(Costanti.SCELTA_CONTRAENTE_AFF_DIRETTO)
		// && isEmpty(lotto.getCIG_ORIGINE_RIP()))
		// mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "CIG
		// collegato").replace("$2", "la scelta del contraente")+" a 'Affidamento
		// diretto per variante superiore al 20% dell'importo contrattuale'");
		// FINE TICKET ALM #4210

		// se flag ripetizione e SI e cig valorizzato deve essere esistente e con flag
		// prevede ripetizioni = SI
		if (((!isEmpty(lotto.getFLAG_RIPETIZIONE()) && Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_RIPETIZIONE())
				|| (!isEmptyOrZero(lotto.getID_MOTIVO_COLL_CIG())
						&& !Costanti.COLL_CIG_NESSUNA.equals(lotto.getID_MOTIVO_COLL_CIG()))))
				&& !isEmpty(lotto.getCIG_ORIGINE_RIP())) {
			LottoManager lm = new LottoManager(connection, logger);
			try {
				List<Lotto> lista = null;

				// TICKET ALM - 3.04.3
				// Verifica se il cig indicato esista come SmartCig
				if (SimogFlags.is3043Active() && lotto.getCIG_ORIGINE_RIP().trim().length() == 10
						&& Character.isLetter(lotto.getCIG_ORIGINE_RIP().charAt(0))) {
					//                	  BdncpManager bm = new BdncpManager(connection,logger);
					//                	  String retVal = bm.checkSmartCig(lotto.getCIG_ORIGINE_RIP());
					//                	  if(isEmpty(retVal) || "".equals(retVal))
					//	                         mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007.replace("$1", "non esistente"));
					logger.debug("Indicato SmartCig come cig collegato");

				}
				// FINE TICKET ALM - 3.04.3
				else {

					// cig errato deve essere lungo 10
					if (lotto.getCIG_ORIGINE_RIP().trim().length() != 10)
						lista = new ArrayList<Lotto>();
					else
						lista = lm.getLottoByCigWS(lotto.getCIG_ORIGINE_RIP());

					// indicato se stesso come cig
					if (lotto.getCIG() != null
							&& (lotto.getCIG() + lotto.getCIG_kkk()).equals(lotto.getCIG_ORIGINE_RIP().trim())) {
						mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007a.replace("$1", "CIG Collegato"));
					} else {
						if (lista.size() == 0 || !isEmpty(lista.get(0).getDATA_CANCELLAZIONE_LOTTO())
								|| !isEmpty(lista.get(0).getDATA_INIB_PAGAMENTO())) {
							mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007a.replace("$1", "CIG Collegato"));
						} else if (lista.size() > 0
								&& (lista.get(0).getFLAG_PREVEDE_RIP() == null
								|| Costanti.FLAG_VALORE_NO.equals(lista.get(0).getFLAG_PREVEDE_RIP()))
								&& Costanti.COLL_CIG_RIP.equals(lotto.getID_MOTIVO_COLL_CIG())) {
							mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007a.replace("$1", "CIG Collegato"));
						} else if (lista.size() > 0
								&& Costanti.COLL_CIG_II_FASE.equals(lotto.getID_MOTIVO_COLL_CIG())) { // TICKET ALM
							// #13575 -
							// 3.04.4.2
							GaraManager gm = new GaraManager(connection, logger);
							Gara garaCigColl = gm.getGara(lista.get(0).getId_Gara());
							if (garaCigColl.getID_MODO_REAL() != Costanti.ID_MODO_REAL_IDEE) {
								mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
								mEccezioni
								.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007b.replace("$1", "CIG Collegato")
										.replace("$2", "Concorsi di progettazione/Concorsi di idee"));
							} // TICKET ALM #19206
						} else if (lista.size() > 0
								&& Costanti.ID_MOTIVO_PRECOM.equals(lotto.getID_MOTIVO_COLL_CIG())) {
							GaraManager gm = new GaraManager(connection, logger);
							Gara garaCigColl = gm.getGara(lista.get(0).getId_Gara());
							if (garaCigColl.getID_MODO_REAL() != Costanti.ID_MODO_PRECOM) {
								mEccezioni.addValidationField("label_CIGCollegato"); // LABEL ERR
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007b
										.replace("$1", "CIG Collegato").replace("$2", "Appalto pre-commerciale"));
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param art esclusione
	 * @param o   deve essere Timestamp o String [yyyymmdd] per l'estensione della
	 *            validita  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean artEsclusioneValido(long id, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);
		// TICKET ALM - 3.04.2 2005
		if (SimogFlags.is3042Active())
			return adb
					.getTipologicaWithDP(ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE,
							ART_ESCLUSIONE.DESCRIZIONE, ART_ESCLUSIONE.DATA_INIZIO_VALIDITA,
							ART_ESCLUSIONE.DATA_FINE_VALIDITA, o, ART_ESCLUSIONE.REGIME_ESCLUSIONE, "E")
					.containsKey(String.valueOf(id));
		else
			return adb.getTipologica(ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE,
					ART_ESCLUSIONE.DESCRIZIONE, ART_ESCLUSIONE.DATA_FINE_VALIDITA, o).containsKey(String.valueOf(id));
	}

	private void validaPerf(Lotto lotto) {

		GaraManager gm = new GaraManager(connection, logger);
		Gara g = null;
		try {
			g = gm.getGara(lotto.getId_Gara());
		} catch (Exception e) {
			logger.debug("Errore durante il recupero della gara per validazione perfezionamento");
			e.printStackTrace();
		}

		prevalida(g, lotto);

		String dataScadenzaPagamenti = lotto.getDATA_SCADENZA_PAGAMENTI();
		String dataPubblicazione = lotto.getData_Pubblicazione();
		
	////3.04.11 MEV 44999
		String currentDate = PageHelper.getCurrentDate();
		if (Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()
				&& Costanti.MODOREAL_ADESIONE != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE != g.getID_MODO_REAL()) {
		
			
			if (SimogProperties.isDataAfterAttivazioneBloccoCig(currentDate)) {
				// MEV 46181 3.04.11
				boolean isSoggNonBloccato = SimogProperties.getInstance().isSoggettoNonBloccato(g.getCF_AMMINISTRAZIONE());			
		 		//se è un soggetto bloccato quindi da bloccare
				if(!isSoggNonBloccato) {
		 			//se la data di pubblicazione è successiva o uguale alla data attivazione blocco CIG
					if (SimogProperties.isDataAfterAttivazioneBloccoCig(dataPubblicazione)) {
		    			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_020d);		 
					}
		 		}
				
			}
		
		}
	//// fine 3.04.11 MEV 44999
		
	

		if (SimogFlags.is3025_RFWEBGL02Active()) {
			// PP se adesione il campo ora non viene valorizzato e non va controllato
//			3.04.8 34190 fix
			if (Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) {
				String oraScadenzaPagamenti = lotto.getORA_SCADENZA();
				if (oraScadenzaPagamenti == null || "".equals(oraScadenzaPagamenti)) {
					// piccinini 30/10/2013 facoltativa se proviene dai ws altrimenti obbligatoria!
					if (SimogFlags.isFromWS())
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
								"Ora scadenza presentazione offerte (hh:mm)"));
					else {
						mEccezioni.addValidationField("label_OraScadenza"); // LABEL ERR
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
								"Ora scadenza presentazione offerte (hh:mm)"));
						// messo dentro altrimenti doppio errore
					}
				} else {
					if (!isValidTime(oraScadenzaPagamenti)) {
						mEccezioni.addValidationField("label_OraScadenza"); // LABEL ERR
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1",
								"Ora scadenza presentazione offerte (hh:mm)"));
					}
				}
			}
		}
		// controllo solo se non e adesione
//		3.04.8 34190 fix
		if (Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) {
			if (dataScadenzaPagamenti == null || "".equals(dataScadenzaPagamenti)) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_021b);
				// messo dentro altrimenti doppio errore
			} else {
				String data = PageHelper.formatDateOrNull(dataScadenzaPagamenti);
				if (!isDate(data)) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_021b);
				}
			}
		}
//		3.04.8 34190 fix
		if (dataPubblicazione == null || "".equals(dataPubblicazione)) {
			if (Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) {
				mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_021a);
			} else {
				mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_021a.replace("pubblicazione",
						"di adesione all'accordo quadro/convenzione"));
			} // messo dentro altrimenti doppio errore
		} else {
			// controllo formato date
			String data = PageHelper.formatDateOrNull(dataPubblicazione);
			if (!isDate(data)) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_021a);
			}
			
		////3.04.11 MEV 44999
			if (Costanti.MODOREAL_ADESIONE_NOCOMPET == g.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == g.getID_MODO_REAL()
					|| Costanti.MODOREAL_ADESIONE == g.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE == g.getID_MODO_REAL()) {
					
							if (SimogProperties.isDataAfterAttivazioneBloccoCig(currentDate)) {
								// MEV 46181 3.04.11
								boolean isSoggNonBloccato = SimogProperties.getInstance().isSoggettoNonBloccato(g.getCF_AMMINISTRAZIONE());			
						 		//se è un soggetto bloccato quindi da bloccare
								if(!isSoggNonBloccato) {
									LottoManager lm = new LottoManager(connection, logger);
									
									List<Lotto> listaLotti;
									try {
										listaLotti = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
										if (listaLotti != null && !listaLotti.isEmpty()) {
											Lotto lottoAccQ = listaLotti.get(0);
											if (lottoAccQ.getData_Pubblicazione() != null && !"".equals(lottoAccQ.getData_Pubblicazione())) {
												//se la data di pubblicazione è successiva o uguale alla data attivazione blocco CIG
												if (SimogProperties.isDataAfterAttivazioneBloccoCig(lottoAccQ.getData_Pubblicazione())) {
									    			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_020d);		 
												}
											}
										}
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										logger.error("errore: 3.04.11 MEV 44999", e);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										logger.error("errore: 3.04.11 MEV 44999" , e);
									}
								}
								
								
							}
							
			}
		//// fine 3.04.11 MEV 44999

			// TICKET ALM #13518 - 3.04.5
//			3.04.8 34190 fix
			if (Costanti.MODOREAL_ADESIONE_NOCOMPET == g.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == g.getID_MODO_REAL()) {
//				////3.04.11 MEV 44999
//				if (SimogProperties.isDataAfterAttivazioneBloccoCig(currentDate)) {
//					LottoManager lm = new LottoManager(connection, logger);
//					
//					List<Lotto> listaLotti;
//					try {
//						listaLotti = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
//						if (listaLotti != null && !listaLotti.isEmpty()) {
//							Lotto lottoAccQ = listaLotti.get(0);
//							if (lottoAccQ.getData_Pubblicazione() != null && !"".equals(lottoAccQ.getData_Pubblicazione())) {
//								//se la data di pubblicazione è successiva o uguale alla data attivazione blocco CIG
//								if (SimogProperties.isDataAfterAttivazioneBloccoCig(lottoAccQ.getData_Pubblicazione())) {
//					    			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_020d);		 
//								}
//							}
//						}
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						logger.error("errore: 3.04.11 MEV 44999", e);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						logger.error("errore: 3.04.11 MEV 44999" , e);
//					}
//					
//				}
//				//// fine 3.04.11 MEV 44999
				AggiudicazioniManager am = new AggiudicazioniManager(connection, logger);
				try {
					List<AggiudicazioneBean> listAb = am.getAggiudicazioniByCIG(g.getCIG_ACC_QUADRO());
					if (!listAb.isEmpty()) {
						AggiudicazioneBean ab = listAb.get(listAb.size() - 1);
						String dataAgg = ab.getDataVerbaleAggiudicazione();
					
						if (dataPubblicazione.compareTo(dataAgg) < 0) {

							// TICKET ALM - 3.04.7
							if (!isYFlag(g.getURGENZA_DL133())) {
								mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
								mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_021a
										+ ": deve essere uguale o successiva alla data di aggiudicazione dell'accordo quadro");
							} else if (g.getID_ESTREMA_URGENZA() == Costanti.TIPO_ESTREMA_URGENZA_L120) {
								LottoManager lm = new LottoManager(connection, logger);
								try {
									List<Lotto> listaLotti = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
									if (listaLotti != null && !listaLotti.isEmpty()) {
										Lotto lottoAccQ = listaLotti.get(0);
										if (lottoAccQ.getData_Pubblicazione() != null
												&& lottoAccQ.getDATA_SCADENZA_PAGAMENTI() != null) {
											// applica l'errore per tutte le gare pubblicate entro (<) il 17/07/2020
											// e per le quali e' scaduta la data di scadenza di presentazione delle
											// offerte.
											// e per tutte le gare pubblicate successivamente (>) il 30/06/2023
											if ((lottoAccQ.getData_Pubblicazione()
													.compareTo(Costanti.DATA_INIZIO_DL120) < 0
													&& lottoAccQ.getDATA_SCADENZA_PAGAMENTI()
													.compareTo(PageHelper.getCurrentDate()) < 0)
													|| lottoAccQ.getData_Pubblicazione()
													.compareTo(Costanti.DATA_FINE_DL120) > 0) {
												mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
												mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_021a
														+ ": deve essere uguale o successiva alla data di aggiudicazione dell'accordo quadro");
											}
										}
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					} else {
						// 31051 se la gara non ha il flag di estrema urgenza e la modalita di
						// realizzazione
						// non afferisce al PPP, blocca il perfezionamento
						//MAC 35479 3.04.8 cambiato da addValidationErr a addValidationWarn
//						if (!isYFlag(g.getURGENZA_DL133()))
//							mEccezioni.addValidationWarn(Messaggi.SIMOG_LOTTO_039);
						// MEV 37107 3.04.8.1 
						
						if (!isYFlag(g.getURGENZA_DL133()) ||
								(!(currentDate.compareTo(SimogProperties.getInstance().getDataAttivazionePPP())>= 0)
								&& !isIdModRealPPP(g.getID_MODO_REAL())) ){
							mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_039);
						}

				
			
					} 
			
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Restituisce un errore specifico nel caso in cui la data
		// di Scadenza pagamenti sia precedente alla data di pubblicazione
		// o contemporanea : non ammesso
//		3.04.8 34190 fix
		if ((Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) && dataPubblicazione != null
				&& dataScadenzaPagamenti != null) {
			int dateValide = dataPubblicazione.compareTo(dataScadenzaPagamenti);
			if (dateValide >= 0) {
				// PP 3.02.1.6 rilassato il controllo, solo se le date sono uguali
				if (dateValide == 0)
					mEccezioni.addValidationWarn(Messaggi.SIMOG_LOTTO_018e);
				else {
					mEccezioni.addValidationField("label_DataOfferte"); // LABEL ERR
					mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
					mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_018e);
				}
			}
		}

		// is3028_RFWEBGL08Active
		validaIntervalloDate(lotto);

		String dataScadenzaRichiestaInvito = lotto.getDataScadenzaRichiestaInvito();
		String dataLetteraInvito = lotto.getDataLetteraInvito();
		if (!isEmpty(dataScadenzaRichiestaInvito)) {
			mEccezioni.addValidationField("label_DataOfferte"); // LABEL ERR
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
					"Data di scadenza per la presentazione della richiesta di invito"));
		}
		if (!isEmpty(dataLetteraInvito)) {
			mEccezioni.addValidationField("label_DataLetteraInvito"); // LABEL ERR
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Data della lettera di invito"));
		}

	}
	}
		
		//MEV 37107 3.04.8.1 ci siamo portati il metodo da SimogValidator
		private boolean isIdModRealPPP(int idModReal) {
			String idModRealPPP = SimogProperties.getInstance().getIdModRealPPP();
			String[] arrIdModRealPPP = idModRealPPP.split(";");
			boolean res = false;

			for (String elIdPPP : arrIdModRealPPP) {
				int intIDPPP = Integer.parseInt(elIdPPP);
				if (intIDPPP == idModReal) {
					res = true;
					break;
				}
			}

			return res;

//			   return idModReal==Costanti.MODOREAL_CONCESSIONE_LAVORI || 
//					   idModReal==Costanti.MODOREAL_CONCESSIONE_SF || 
//					   idModReal==Costanti.MODOREAL_FINANZA_DI_PROGETTO ||
//					   idModReal==Costanti.MODOREAL_LOCFIN_OPEREPUBBLICHE ||
//					   idModReal==Costanti.MODOREAL_DISPONIBILITA;
		}

	private void validaPerfSenzaLotti(Lotto lotto) {
		GaraManager gm = new GaraManager(connection, logger);
		Gara g = null;
		try {
			g = gm.getGara(lotto.getId_Gara());
		} catch (Exception e) {
			logger.debug("Errore durante il recupero della gara in validaPerfSenzaLotti");
			e.printStackTrace();
		}
		if (lotto != null) {
			String dataScadenzaPagamenti = lotto.getDATA_SCADENZA_PAGAMENTI();
			String dataPubblicazione = lotto.getData_Pubblicazione();
			if ((dataScadenzaPagamenti != null && dataScadenzaPagamenti.trim().length() != 0))
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_204.replace("$1", "Data scadenza presentazione offerte"));
			if (dataPubblicazione != null && dataPubblicazione.trim().length() != 0) {
				mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_204.replace("$1", "Data pubblicazione"));
			}
			
			////3.04.11 MEV 44999
			String currentDate = PageHelper.getCurrentDate();
			if (SimogProperties.isDataAfterAttivazioneBloccoCig(currentDate)) {
				// MEV 46181 3.04.11
				boolean isSoggNonBloccato = SimogProperties.getInstance().isSoggettoNonBloccato(g.getCF_AMMINISTRAZIONE());			
		 		//se è un soggetto bloccato quindi da bloccare
				if(!isSoggNonBloccato) {
					//se la data di pubblicazione è successiva o uguale alla data attivazione blocco CIG
					if (SimogProperties.isDataAfterAttivazioneBloccoCig(dataPubblicazione)) {
		    			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_020d);		 
					}
				}
				
			}
		//// fine 3.04.11 MEV 44999
			// is3028_RFWEBGL08Active
			validaIntervalloDate(lotto);
		}
	}

	private void validaIntervalloDate(Lotto lotto) {
		String dataScadenzaPagamenti = lotto.getDATA_SCADENZA_PAGAMENTI();
		String dataPubblicazione = lotto.getData_Pubblicazione();
		if (SimogFlags.is3028_RFWEBGL08Active()) {
			// errata implementazione
			//          if (dataScadenzaPagamenti != null && dataScadenzaPagamenti.trim().length() != 0
			//               && dataPubblicazione != null && dataPubblicazione.trim().length() != 0
			//               && isDate(dataPubblicazione) && isDate(dataScadenzaPagamenti)
			//          ){
			//             long giorniDiff = (PageHelper.getCalendarFromStringDate(dataScadenzaPagamenti).getTimeInMillis()
			//                               - PageHelper.getCalendarFromStringDate(dataPubblicazione).getTimeInMillis())
			//                               / GIORNO_MILLISEC;
			//             
			//             if(giorniDiff < 0 || giorniDiff > getGiorni()) 
			//                mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Intervallo temporale tra Data pubblicazione e Data scadenza presentazione offerte"));    
			//          }
			if (dataPubblicazione != null && dataPubblicazione.trim().length() != 0 && isDate(dataPubblicazione)) {
				long giorniDiff = (PageHelper.getCalendarFromStringDate(dataPubblicazione).getTimeInMillis()
						- PageHelper.getCalendarFromStringDate(PageHelper.getCurrentDate()).getTimeInMillis())
						/ GIORNO_MILLISEC;

				if (giorniDiff > getGiorni()) {
					mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Data pubblicazione"));
				}
			}

			if (dataScadenzaPagamenti != null && dataScadenzaPagamenti.trim().length() != 0
					&& isDate(dataScadenzaPagamenti)) {
				long giorniDiff = (PageHelper.getCalendarFromStringDate(dataScadenzaPagamenti).getTimeInMillis()
						- PageHelper.getCalendarFromStringDate(PageHelper.getCurrentDate()).getTimeInMillis())
						/ GIORNO_MILLISEC;

				if (giorniDiff > getGiorni())
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Data scadenza presentazione offerte"));
			}
		}
	}

	private void validaCanc(Lotto lotto) {
		if (lotto != null) {
			String id_motivazione = lotto.getId_motivazione();
			String note_canc = lotto.getNoteCancellazione();
			MotivazioniBean motiviCanc = new MotivazioniBean();
			motiviCanc.loadAll(this.connection, logger, false);
			if (id_motivazione == null || id_motivazione.trim().length() == 0) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_023.replace("$1", "Motivazione"));
				return;
			} else if (motiviCanc.isNotaObbligatoria(id_motivazione)
					&& (note_canc == null || note_canc.trim().length() == 0)) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_023.replace("$1", "Nota"));
				return;
			}
			if (note_canc.trim().length() > 1000) { // maxlength campo note 1000 caratteri
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_184.replace("$1", "Note").replace("$2", "1000"));
				return;
			}
		}
	}

	private void validaCancSenzaLotti(Lotto lotto) {
		if (lotto != null) {
			String id_motivazione = lotto.getId_motivazione();
			String note_canc = lotto.getNoteCancellazione();
			if ((id_motivazione != null && id_motivazione.trim().length() != 0))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_204.replace("$1", "Motivazione"));
			if (note_canc != null && note_canc.trim().length() != 0)
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_204.replace("$1", "Note"));
		}
	}

	private void validaContrattiEsclusi(Lotto lotto) {

		validaEsclusi(lotto);

	}
	
	
	/* MAD 68089 3.04.16 Inizio */
	private void validaCategoriaSoa(Lotto lotto) {

		return; //- Al momento non valido, devo controllare se sono state cambiate le categorie soa

	}
	/* MAD 68089 3.04.16 Fine */
	
	
	private void validaCPV(Lotto lotto, Gara g) {
		if (lotto.getId_CPV() == null || lotto.getId_CPV().trim().length() == 0) {
			// pp organi costituzionali, CPV facoltativo
			if (SimogFlags.isOrganiCostActive() && g.isOrganoCost())
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice CPV Prevalente"));
			else {
				mEccezioni.addValidationField("label_CPVLotto"); // LABEL ERR
				mEccezioni.addValidationErr(Messaggi.SIMOG_GARA_009);
			}
		} else {
			CPVEUManager cManager = new CPVEUManager(connection, logger);
			try {
				if (!cManager.checkCPV(lotto.getId_CPV(), g.getData_creazione())) {

					// MEV 25894 - se e' un'adesione ad accordo quadro, controlla solo se la CPV
					// esiste
					if (g.getCIG_ACC_QUADRO() != null && !cManager.checkCPVNoData(lotto.getId_CPV())) {
						logger.debug("Inserito valore non valido [" + lotto.getId_CPV() + "] per CPV");
						mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
					}
				} else {
					boolean ok = !"0".equals(lotto.getId_CPV().substring(2, 3))
							&& !"0".equals(lotto.getId_CPV().substring(3, 4));

					ok = ok || cManager.getBranch(lotto.getId_CPV().substring(0, 2), lotto.getId_CPV().substring(2, 3),
							lotto.getId_CPV().substring(3, 4), lotto.getId_CPV().substring(4, 5),
							lotto.getId_CPV().substring(5, 8)).size() == 0;

					if (!ok) {
						boolean old = false;

						// Se il CIG accordo quadro risale al 2007/2008 bypassa l'errore
						if (g.getCIG_ACC_QUADRO() != null && !"".equals(g.getCIG_ACC_QUADRO())) {
							LottoManager lm = new LottoManager(connection, logger);
							List<Lotto> lottoAccQ = new ArrayList<Lotto>();
							try {
								lottoAccQ = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!lottoAccQ.isEmpty()) {
								Lotto lottoAQ = lottoAccQ.get(0);
								old = lottoAQ.getDataCreazione() == null || "".equals(lottoAQ.getDataCreazione())
										|| "20090101".compareTo(lottoAQ.getDataCreazione()) > 0;
							}
						}
						if (!old) {
							logger.debug("Inserito valore non valido [" + lotto.getId_CPV() + "] per CPV");
							mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
						}
					} else // TICKET ALM #13793 - 3.04.5
						if (SimogProperties.getInstance().isDataCreatedAfter3045(g.getData_creazione())
								&& g.getCIG_ACC_QUADRO() != null && !"".equals(g.getCIG_ACC_QUADRO())) {
							LottoManager lm = new LottoManager(connection, logger);
							try {
								List<Lotto> lottoAccQ = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
								if (!lottoAccQ.isEmpty()) {
									String cpvAccQ = lottoAccQ.get(0).getId_CPV();
									if (!lotto.getId_CPV().equals(cpvAccQ)) {
										List<CpvLotto> listCpvSecAccQ = lm.selectCpvLotto(lottoAccQ.get(0).getId_Lotto());
										boolean checkCpvSec = false;
										for (CpvLotto cpvSecAccQ : listCpvSecAccQ) {
											if (lotto.getId_CPV().equals(cpvSecAccQ.getIdCpv())) {
												checkCpvSec = true;
												break;
											}
										}
										if (!checkCpvSec) {
											mEccezioni.addValidationField("label_CPVLotto"); // LABEL ERR
											mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_036);
										}
									}
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} // FINE TICKET ALM #13793 - 3.04.5
				}
			} catch (SQLException sqle) {
				logger.fatal(sqle.getMessage());
				mEccezioni.addValidationField("label_CPVLotto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
			}
		}
		
		// TICKET ALM #4219 - 3.04.4
				// Correttezza valori CPV Secondarie (che si applicano sia per le gare post che
				// pre attivazione 3.04.4)
				if (lotto.getElencoCpvSecondarie() != null && lotto.getElencoCpvSecondarie().size() > 0) {
					for (CpvLotto el : lotto.getElencoCpvSecondarie()) {
						CPVEUManager cManager = new CPVEUManager(connection, logger);
						try {

							if (!cManager.checkCPV(el.getIdCpv(), PageHelper.getCurrentDate())) {
								mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_031.replace("$1", el.getIdCpv()));
							}

							// Verifica se la CPV secondaria sia stata gia' inidcata come CPV primaria
							if (el.getIdCpv().equals(lotto.getId_CPV()))
								mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_033.replace("$1", el.getIdCpv()));
							LottoManager lm = new LottoManager(connection, logger);
							List<Lotto> lottoAccQ;
							try {
								lottoAccQ = lm.getLottoByCigWS(g.getCIG_ACC_QUADRO());
							
								// TICKET ALM #13793 - 3.04.5
								if (!lottoAccQ.isEmpty()) {
									Lotto cigAccQ = lottoAccQ.get(0);
									if (!el.getIdCpv().equals(cigAccQ.getId_CPV())) {
										List<CpvLotto> listcpvSec = lm.selectCpvLotto(cigAccQ.getId_Lotto());
										boolean checkCpvSec = false;
										for (CpvLotto secCpvLotto : listcpvSec) {
											if (el.getIdCpv().equals(secCpvLotto.getIdCpv())) {
												checkCpvSec = true;
												break;
											}
										}
										if (!checkCpvSec)
											mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_037.replace("$1", el.getIdCpv()));
									}
								} // FINE TICKET ALM #13793 - 3.04.4.1
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} // FINE TICKET ALM #4219 - 3.04.4
		

	}

	@Override
	public boolean validaTipologica(String nomeTabella, String campoId, String campoDescrizione, String campoValidita,
			Object data, Object id) {
		return super.validaTipologica(nomeTabella, campoId, campoDescrizione, campoValidita, data, id);
	}

	/**
	 * Metodo che si occupa di validare tutte le categoria ovvero controlla
	 * l'esistenza di tutti gli id contenuti nella mappa
	 * 
	 * @return boolean
	 */
	public boolean validaCategorie(Map<String, String> categorie, Object data, Gara g) {
		Set<String> id_categorie = categorie.keySet();
		int local_error = 0;
		boolean valida = true;
		int counter = 1;
		for (String id_categoria : id_categorie) {
			if (id_categoria == null) {
				return false;
			}
			valida = validaTipologica(CATEGORIA.TABLE_NAME, CATEGORIA.ID_CATEGORIA, CATEGORIA.DESCRIZIONE,
					CATEGORIA.DATA_FINE_VALIDITA, data, id_categoria);
			if (!valida) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Categoria"),
						counter);
				local_error++;
			} else {
				if (SimogFlags.isOrganiCostActive() && !g.isOrganoCost()
						&& Costanti.CATEGORIA_PREV_OOCC.equals(id_categoria))
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Categoria"));
			}

			counter++;
		}
		return local_error == 0;
	}

	/**
	 * @param id categoria prevalente
	 * @return
	 */
	private boolean validaCategoriaPrevalente(String id_categoria, Object data) {
		if (id_categoria != null) {
			return super.validaTipologica(CATEGORIA.TABLE_NAME, CATEGORIA.ID_CATEGORIA, CATEGORIA.DESCRIZIONE,
					CATEGORIA.DATA_FINE_VALIDITA, data, id_categoria);
		}
		return false;
	}

	/**
	 * Prevalidazione del lotto prima delle verifiche specifiche di ogni tipologia
	 * di validazione
	 * 
	 * @param lotto
	 */

	private void prevalida(Gara g, Lotto lotto) {
		// ticket 31061
		if (g.isPPPGara() && isNFlag(lotto.getFLAG_CUP())) {
			mEccezioni.addValidationErr(
					"ERROR - La gara appartiene afferisce al PPP e il/i lotto/i hanno il campo CUP obbligatorio.");
		}
	}

	public boolean prevalidaPPP(Gara g, Lotto lotto) {
		boolean isPrevalidPPP = true;
		// ticket 31061
		if (g.isPPPGara() && isNFlag(lotto.getFLAG_CUP())) {
			isPrevalidPPP = false;
		}

		return isPrevalidPPP;
	}

	/**
	 * Validatore del perfezionamento del lotto (Procedura Ristretta)
	 * 
	 * @param lotto
	 */
	private void validaPerfProceduraRistretta(Lotto lotto, boolean fase2) {

		GaraManager gm = new GaraManager(connection, logger);
		Gara g = null;
		try {
			g = gm.getGara(lotto.getId_Gara());
		} catch (Exception e) {
			logger.debug("Errore durante il recupero della gara per validazione perfezionamento");
			e.printStackTrace();
		}

		prevalida(g, lotto);

		String dataPubblicazione = lotto.getData_Pubblicazione();
		String dataScadenzaRichiestaInvito = lotto.getDataScadenzaRichiestaInvito();
		String dataLetteraInvito = lotto.getDataLetteraInvito();
		String dataScadenzaPagamenti = lotto.getDATA_SCADENZA_PAGAMENTI();
		
		////3.04.11 MEV 44999
		String currentDate = PageHelper.getCurrentDate();
		if (SimogProperties.isDataAfterAttivazioneBloccoCig(currentDate)) {
			// MEV 46181 3.04.11
			boolean isSoggNonBloccato = SimogProperties.getInstance().isSoggettoNonBloccato(g.getCF_AMMINISTRAZIONE());			
	 		//se è un soggetto bloccato quindi da bloccare
			if(!isSoggNonBloccato) {
				//se la data di pubblicazione è successiva o uguale alla data attivazione blocco CIG
				if (SimogProperties.isDataAfterAttivazioneBloccoCig(dataPubblicazione)) {
	    			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_020d);		 
				}
			}
			
		}
		//// fine 3.04.11 MEV 44999

		boolean esito = validaData(dataPubblicazione, "Data Pubblicazione", true);
		if (!esito)
			mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR

		if (fase2) {
			if (Costanti.DEFAULT_DATA_PUBB_TED.equals(dataPubblicazione)) {
				mEccezioni.addValidationErr(
						"SERVICE_ERROR_106 - Impossibile procedere con la seconda fase: il processo di pubblicazione del formulario attinente alla prima fase e ancora in corso.");
			}
		}

		esito = validaData(dataScadenzaRichiestaInvito,
				"Data di scadenza per la presentazione della richiesta di invito", true);
		if (!esito)
			mEccezioni.addValidationField("label_DataRichiestaInvito"); // LABEL ERR

		esito = validaData(dataLetteraInvito, "Data della lettera di invito", fase2 || !isEmpty(dataScadenzaPagamenti));
		if (!esito)
			mEccezioni.addValidationField("label_DataLetteraInvito"); // LABEL ERR

		esito = validaData(dataScadenzaPagamenti, "Data di scadenza per la presentazione delle offerte",
				fase2 || !isEmpty(dataLetteraInvito));
		if (!esito)
			mEccezioni.addValidationField("label_DataOfferte"); // LABEL ERR

		esito = validaOrdineDate(dataPubblicazione, "Data Pubblicazione", dataScadenzaRichiestaInvito,
				"Data di scadenza per la presentazione della richiesta di invito", false, true);
		if (!esito) {
			mEccezioni.addValidationField("label_DataPubblicazione"); // LABEL ERR
			mEccezioni.addValidationField("label_DataRichiestaInvito"); // LABEL ERR
		}

		esito = validaOrdineDate(dataScadenzaRichiestaInvito,
				"Data di scadenza per la presentazione della richiesta di invito", dataLetteraInvito,
				"Data della lettera di invito", false, false);
		if (!esito) {
			mEccezioni.addValidationField("label_DataLetteraInvito"); // LABEL ERR
			mEccezioni.addValidationField("label_DataRichiestaInvito"); // LABEL ERR
		}

		esito = validaOrdineDate(dataLetteraInvito, "Data della lettera di invito", dataScadenzaPagamenti,
				"Data di scadenza per la presentazione delle offerte", false, false);
		if (!esito) {
			mEccezioni.addValidationField("label_DataLetteraInvito"); // LABEL ERR
			mEccezioni.addValidationField("label_DataOfferte"); // LABEL ERR
		}

		/*
		 * Valida ora scadenza e intervallo date solo se la data scandenza pagamenti e'
		 * valorizzata
		 */
		if (!isEmpty(dataScadenzaPagamenti)) {

			// PP se adesione, il campo ora non viene valorizzato e non va controllato
//			3.04.8 34190 fix
			if (Costanti.MODOREAL_ADESIONE_NOCOMPET != g.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != g.getID_MODO_REAL()) {
				String oraScadenzaPagamenti = lotto.getORA_SCADENZA();
				esito = validaOra(oraScadenzaPagamenti, "Ora scadenza presentazione offerte (hh:mm)",
						// piccinini 30/10/2013 facoltativa se proviene dai ws altrimenti obbligatoria!
						!SimogFlags.isFromWS());
				if (!esito)
					mEccezioni.addValidationField("label_OraScadenza"); // LABEL ERR
			}

			// is3028_RFWEBGL08Active
			validaIntervalloDate(lotto);
		}
	}

	/**
	 * Validatore del perfezionamento del lotto (Procedura Mista)
	 * 
	 * @param lotto
	 */
	private void validaPerfProceduraMista(Lotto lotto) {
		boolean proceduraStandard = !isEmpty(lotto.getData_Pubblicazione())
				&& !isEmpty(lotto.getDATA_SCADENZA_PAGAMENTI()) && isEmpty(lotto.getDataScadenzaRichiestaInvito())
				&& isEmpty(lotto.getDataLetteraInvito());

		boolean proceduraRistrettaCompleta = !isEmpty(lotto.getData_Pubblicazione())
				&& !isEmpty(lotto.getDataScadenzaRichiestaInvito());

		if (proceduraStandard)
			validaPerf(lotto);
		else if (proceduraRistrettaCompleta)
			validaPerfProceduraRistretta(lotto, false);
		else
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_221);
	}

	/**
	 * Validazione delle tipologie di appalto del lotto
	 * 
	 * @param lotto
	 */
	private void validaTipologieAppaltoLotto(Lotto lotto) {
		Gara gara = null;
		GaraManager gm = new GaraManager(connection, logger);
		try {
			gara = gm.getGara(lotto.getId_Gara());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Validazione LAVORI
		if (isEmpty(lotto.getElencoTipoAppaltoLottoL())) {
			if (isLavori(lotto.getTIPO_CONTRATTO_LOTTO()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Lavoro"));
		} else {
			try {
				AggiudicazioniManager aMan = new AggiudicazioniManager(connection, logger);
				int idx = 0;
				for (TipoAppaltoAggBean item : lotto.getElencoTipoAppaltoLottoL()) {
					if (!aMan.caricaLottoComboAppalto(Costanti.TIPO_SCHEDA_LAVORI, gara.getData_creazione())
							.containsKey(String.valueOf(item.getIdAppalto())))
						mEccezioni.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Lavoro"), ++idx);
				}
			} catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Lavoro"));
			}
		}

		// Validazione SERVIZI E FORNITURE
		if (isEmpty(lotto.getElencoTipoAppaltoLottoF())) {
			if (!isLavori(lotto.getTIPO_CONTRATTO_LOTTO()))
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_110);
		} else {
			try {
				AggiudicazioniManager aMan = new AggiudicazioniManager(connection, logger);
				int idx = 0;
				for (TipoAppaltoAggBean item : lotto.getElencoTipoAppaltoLottoF()) {
					if (!aMan.caricaLottoComboAppalto(Costanti.TIPO_SCHEDA_FORNITURE, gara.getData_creazione())
							.containsKey(String.valueOf(item.getIdAppalto())))
						mEccezioni.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di acquisizione"), ++idx);
				}
			} catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di acquisizione"));
			}
		}

		// TICKET ALM - 3.04.2 NG
		if (SimogFlags.is3042Active()) {

			String strCreazioneGara = gara.getData_creazione();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			long dataCreazioneGara = 0;
			try {
				dataCreazioneGara = sdf.parse(strCreazioneGara).getTime();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Campo non previsto se la gara rientra nel vecchio codice degli appalti
			if (!SimogProperties.getInstance().isDataCreatedAfter3042(strCreazioneGara)) {
				if (!isEmptyOrZero(lotto.getCondizioni()))
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
							"Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
			} else // Controlli da applicare solo se la gara fa parte del nuovo codice degli
				// appalti
			{

				if (!"".equals(lotto.getId_Scelta_Contraente())
						&& !Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_ESCLUSO())) { // TICKET ALM #11165 - 3.04.4

					// Campo da non inserire se lotto non e' in procedura negoziata
					if ((Costanti.COND_SPB != Integer.parseInt(lotto.getId_Scelta_Contraente())
							&& Costanti.COND_SPBG != Integer.parseInt(lotto.getId_Scelta_Contraente()))
							&& !isEmptyOrZero(lotto.getCondizioni())) {
						mEccezioni.addValidationField("label_CondizioniLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
								"Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
					} else // Campo obbligatorio se lotto e' in procedura negoziata
						if ((Costanti.COND_SPB == Integer.parseInt(lotto.getId_Scelta_Contraente())
						|| (Costanti.COND_SPBG == Integer.parseInt(lotto.getId_Scelta_Contraente())
						&& gara.getID_MODO_GARA() != 2))
								&& isEmptyOrZero(lotto.getID_ESCLUSIONE()) && isEmptyOrZero(lotto.getCondizioni())) {
							mEccezioni.addValidationField("label_CondizioniLotto");
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
									"Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
						} else if (!isEmptyOrZero(lotto.getCondizioni())) {// Verificare se gli id sono validi

							int i = 0;
							try {
								for (CondizioneLottoBean bean : lotto.getCondizioni()) {
									i++;
									if (!condizioniLottoValido((int) bean.getIdCondizione(), gara.getData_creazione()))
										mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
												"Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara")
												+ ". Elemento: " + (i));

								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
				}
			}

		} // FINE TICKET ALM - 3.04.2 NG

	}

	private void validaModificaDatiCup(Lotto lotto) {
		// Ticket ALM #666
		// Recupero la gara per recuperare la modalita di realizzazione
		GaraManager gm = new GaraManager(connection, logger);
		Gara g = null;
		try {
			g = gm.getGara(lotto.getId_Gara());
			
			//MEV 39162 3.04.8.1
			//if (SimogProperties.getInstance().isDataCreatedAfter30481(g.getData_creazione())) { MAD 53644 3.04.13
				if ((g.getID_MODO_REAL() == 9 || g.getID_MODO_REAL() == 17 || g.getID_MODO_REAL() == 18)) {
					if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
						mEccezioni.addValidationField("label_FlagCUPLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
					}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
						mEccezioni.addValidationField("label_FlagCUPLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
								+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
								+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
								+ "con risorse Comunitarie)"));
					}
				}else if (g.getID_SVOLGIMENTO() == 6) {
					if (isYFlag(lotto.getFLAG_CUP()) || (lotto.getElencoCup() != null && !lotto.getElencoCup().isEmpty())) {
						mEccezioni.addValidationField("label_FlagCUPLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_295);
					}else if(lotto.getFLAG_CUP() == null || lotto.getFLAG_CUP().equals("")) {
						mEccezioni.addValidationField("label_FlagCUPLotto");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
								+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
								+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
								+ "con risorse Comunitarie)"));
					}
				}else {
					validaFlageCodiciCUP(lotto, g.getID_MODO_REAL(), g.getID_SVOLGIMENTO());
				}
//			}else { MAD 53644 3.04.13
//				validaFlageCodiciCUP(lotto, g.getID_MODO_REAL(), g.getID_SVOLGIMENTO());
//			}
			//fine mev
			
			
			
		} catch (Exception e) {
			logger.debug("Errore durante il recupero della gara per il cofronto degli importi");
			e.printStackTrace();
		}

		validaTipologieAppaltoLotto(lotto);
		//validaPariOpportunita(lotto, g, false);

	}

	// TICKET ALM #3835
	private boolean affidamentiRiservatiValido(int id, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);

		return adb
				.getTipologica(AFFIDAMENTI_RISERVATI.TABLE_NAME, AFFIDAMENTI_RISERVATI.ID_AFF_RISERVATI,
						AFFIDAMENTI_RISERVATI.DESCRIZIONE, AFFIDAMENTI_RISERVATI.DATA_FINE_VALIDITA, o)
				.containsKey(String.valueOf(id));
	}

	private boolean condizioniLottoValido(int id, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);
		return adb
				.getTipologicaWithData(CONDIZIONI.TABLE_NAME, CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE,
						CONDIZIONI.DATA_INIZIO_VALIDITA, CONDIZIONI.DATA_FINE_VALIDITA, o)
				.containsKey(String.valueOf(id));

	}

	// FINE TICKET ALM #3835

	// TICKET ALM - 3.04.2 2005
	private boolean articoliRegimeValido(int id, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);

		return adb
				.getTipologicaWithDP(ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE,
						ART_ESCLUSIONE.DESCRIZIONE, ART_ESCLUSIONE.DATA_INIZIO_VALIDITA,
						ART_ESCLUSIONE.DATA_FINE_VALIDITA, o, ART_ESCLUSIONE.REGIME_ESCLUSIONE, "P")
				.containsKey(String.valueOf(id));
	}
	// FINE TICKET ALM - 3.04.2 2005

	// TICKET ALM - 3.04.3
	private boolean motivoCollegamentoValido(int id, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);

		return adb.getTipologicaWithData(MOTIVO_COLLEGAMENTO.TABLE_NAME, MOTIVO_COLLEGAMENTO.ID_MOTIVO,
				MOTIVO_COLLEGAMENTO.DESCRIZIONE, MOTIVO_COLLEGAMENTO.DATA_INIZIO_VALIDITA,
				MOTIVO_COLLEGAMENTO.DATA_FINE_VALIDITA, o).containsKey(String.valueOf(id));
	}
	// FINE TICKET ALM - 3.04.3

	// TICKET ALM #3834
	private boolean artRegimeConsentito(int id_ART_REGIME) {

		return id_ART_REGIME == Costanti.ART_REGIME_SERVIZI_SOCIALI || id_ART_REGIME == Costanti.ART_REGIME_RISERVATI
				|| id_ART_REGIME == Costanti.ART_REGIME_RISTORAZIONE;
	}
	
	

}
