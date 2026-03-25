package it.avlp.simog.validatore;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.SchedaVariante;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.MOTIVI_VARIANTE;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;

public class VariantiValidator extends SimogValidator {

	public VariantiValidator(Connection connection, Logger logger) {
		super(connection, logger);

	}

	@Override
	public boolean valida(Object bean, String section) {
		if (bean != null) {
			SchedaVariante svarBea = (SchedaVariante) bean;
			valida(svarBea);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}
		return false;
	}

	private void valida(SchedaVariante svarBea) {
		// MEV 37328 - 3.04.8.1 FASE 2
		GaraManager gm = new GaraManager(connection, logger);
		LottoManager lt = new LottoManager(connection, logger);
		Gara gara = new Gara();
		try {
			Lotto lotto = lt.getLotto(svarBea.getInfoComuni().getIdLotto());
			gara = gm.getGara(lotto.getId_Gara());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean isOsservCompetente = SimogProperties.getInstance()
				.isOsservatorioRegionaleCompetente(gara.getID_OSSERVATORIO());

		if (isOsservCompetente) {
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_294);
		}
		// FINE MEV 37328
		List<VarianteBean> lvar = svarBea.getVarianti();
		int progressivo = 1;
		for (VarianteBean var : lvar) {
			valida(svarBea, var, progressivo);
			progressivo++;
		}
	}

	/*****************************************************************************************************
	 * Validatore per la scheda Varianti
	 * 
	 * @param svarBea SchedaVariante
	 * @param vb      VarianteBean
	 */
	private void valida(SchedaVariante svarBea, VarianteBean vb, int progressivo) {
		// rif: campo 9 inizio lavori (data stipula)
		InizioLavoriBean ib = svarBea.getInizioLavori();
		String dataInizioLavoriStipula = null;
		if (ib != null) {
			// PP modificato a seguito richiesta del 10.11.08 dataInizioLavoriStipula =
			// ib.getDataStipula();
			dataInizioLavoriStipula = ib.getDataVerbaleInizio();
		}
		if (isEmpty(vb.getDataVerbaleApprovazione())) {
			// err campo obligatorio
			mEccezioni.addValidationField("label_DataModificContrattuale");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
					"Scheda Modifica Contrattuale - Data verbale approvazione modifica contrattuale "));
		} else {
			// solo nel caso del bean necessario non sia nullo (che da flusso potrebbe
			// essere)
			if (ib != null) {
				if (isDateBigger(dataInizioLavoriStipula, vb.getDataVerbaleApprovazione())) {
					// err data antecedente bla bla 30.05.2012 Baratta rilassato a warning
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_155
							.replace("$1",
									"Scheda Modifica Contrattuale - Data verbale approvazione modifica contrattuale ")
							.replace("$2", "effettivo inizio"));

				}
			}
		}

		// TICKET ALM #2847 - Varianti
		LottoManager lm = new LottoManager(connection, logger);
		GaraManager gm = new GaraManager(connection, logger);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long dataCreazione = 0;
		String dataCreazioneStr = "";
		try {
			Gara gara = gm.getGara(lm.getLotto(svarBea.getInfoComuni().getIdLotto()).getId_Gara());
			dataCreazioneStr = gara.getData_creazione();
			dataCreazione = sdf.parse(dataCreazioneStr).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TICKET ALM - 3.04.3 #4195
		if (SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneStr)) {
			if (isEmpty(vb.getEmvb())) {
				// ! non e' stato selezionato nulla controlla campo 4
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Modifica Contrattuale - Motivazioni "));
			} else if (verificaSelezioneAltreMotivazioni(vb.getEmvb())) // Verifica se e' stata selezionata la voce 8
			{
				// Se e' stata selezionata la voce 8 ma non sono indicate altre motivazioni,
				// mostra un errore
				if (isEmpty(vb.getAltreMotivazioni()))
					// selezionare un capo nel drop down oppure scrivere qualcosa nel campo 4
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_252a);

			}
		} else {
			if (isEmpty(vb.getEmvb())) {
				// ! non e' stato selezionato nulla controlla campo 4
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Variante - Motivazioni "));
				if (isEmpty(vb.getAltreMotivazioni())) {
					// selezionare un capo nel drop down oppure scrivere qualcosa nel campo 4
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_159.replace("$1", "Scheda Variante - Altre motivazioni "));
				}
			} else {

				this.validaEventiMotiviVariantiWithData(vb.getEmvb(), dataCreazioneStr, dataCreazioneStr);

			}
		}
		// FINE TICKET ALM - 3.04.3 #4195

		if (!isEmpty(vb.getEmvb())) {

			this.validaEventiMotiviVariantiWithData(vb.getEmvb(), dataCreazioneStr, dataCreazioneStr);
			this.validaSelezioneRevisioniPrezzi(vb.getEmvb(), vb);// MEV 34469 3.04.8

		}
		// FINE TICKET ALM #2847 - Varianti

//		MEV 34191 3.04.8
//		if (vb.getLinkVarianti() == null || vb.getLinkVarianti().equals("")) {
//			if (verificaSelezioneMotiviLinkVarianti(vb.getEmvb())) {
//				mEccezioni.addValidationField("label_linkVarianti");
//				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_288);
//			}
//		}
//		FINE MEV 34191 3.04.8

		// TICKET ALM - 3.04.3 PT
		if (SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneStr)) {
			
			if (vb.getCigProcedura() != null && !"".equals(vb.getCigProcedura())
					&& vb.getCigProcedura().trim().length() < 10) {
				mEccezioni.addValidationField("label_CIGProcedura");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007.replace("$1", vb.getCigProcedura()));
			} else if (verificaSelezioneProrogaTecnica(vb.getEmvb())
					&& (vb.getCigProcedura() == null || "".equals(vb.getCigProcedura()))
					&& (vb.getAltreMotivazioni() == null || "".equals(vb.getAltreMotivazioni().trim()))) {
				mEccezioni.addValidationField("label_CIGProcedura");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_252);
			} else if (!verificaSelezioneProrogaTecnica(vb.getEmvb())
					&& (vb.getCigProcedura() != null && !"".equals(vb.getCigProcedura()))
					&& (vb.getAltreMotivazioni() != null && !"".equals(vb.getAltreMotivazioni().trim()))) {
				mEccezioni.addValidationField("label_CIGProcedura");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Cause della modifica contrattuale"));
			} else if (!verificaSelezioneProrogaTecnica(vb.getEmvb()) && !isEmpty(vb.getCigProcedura())) {
				mEccezioni.addValidationField("label_CIGProcedura");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "CIG della nuova procedura avviata"));
			} else if (vb.getCigProcedura() != null && !"".equals(vb.getCigProcedura())) { //// 3.04.15 MAD 61769
				try {
					if (lm.getLottoByCigWS(vb.getCigProcedura().trim()).size() <= 0) {
						if (!isCIGPCP(vb.getCigProcedura().trim())) {
							mEccezioni.addValidationField("label_CIGProcedura");
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_008);
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // FINE //3.04.15 MAD 61769
		} else if (vb.getCigProcedura() != null && !"".equals(vb.getCigProcedura())) {
			mEccezioni.addValidationField("label_CIGProcedura");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "CIG della nuova procedura avviata"));
			// FINE TICKET ALM - 3.04.3 PT
		}

		if (!((vb.getImpRidetLavori() != null && vb.getImpRidetLavori().compareTo(new BigDecimal(0)) > 0)
				|| (vb.getImpRidetServizi() != null && vb.getImpRidetServizi().compareTo(new BigDecimal(0)) > 0)
				|| (vb.getImpRidetFornit() != null && vb.getImpRidetFornit().compareTo(new BigDecimal(0)) > 0))) {
			// err (nel caso siano tutti zero..)
			mEccezioni.addValidationField("label_ImportoRideterminato");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_157.replace("$1",
					"Scheda Modifica Contrattuale - Importo rideterminato lavori/servizi/forniture "));
		}

		if (isEmpty(vb.getImpSicurezza()) || vb.getImpSicurezza().compareTo(new BigDecimal(0)) == 0) {
			// warn
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_158
					.replace("$1", "Scheda Modifica Contrattuale - Importo sicurezza ").replace("$2", "sicurezza"));
		}
		if (isEmpty(vb.getImpProgettazione()) || vb.getImpProgettazione().compareTo(new BigDecimal(0)) == 0) {
			// warn
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_158
					.replace("$1", "Scheda Modifica Contrattuale - Importo progettazione ")
					.replace("$2", "progettazione"));
		}
		if (isEmpty(vb.getUlterioriSomme()) || vb.getUlterioriSomme().compareTo(new BigDecimal(0)) == 0) {// MAC #7431
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_158
					.replace("$1", "Scheda Variante - Ulteriori Somme ").replace("$2", "ulteriore somma"));
		}
		if (isEmpty(vb.getImpDisposizione()) || vb.getImpDisposizione().compareTo(new BigDecimal(0)) == 0) {
			// warn
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_158
					.replace("$1", "Scheda Modifica Contrattuale - Importo complessivo disposizione ")
					.replace("$2", "disposizione"));
		}

		if (!isEmpty(vb.getDataAttoAggiuntivo())) {
			// controllo che sia inserito il campo 9 di fase iniziale
			if (!isEmpty(dataInizioLavoriStipula)) {
				if (isDateBigger(dataInizioLavoriStipula, vb.getDataAttoAggiuntivo())) {
					// err data antecedente bla bla
					// Ticket ALM #644
					// mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1",
					// "Scheda Modifica Contrattuale - Data atto aggiuntivo
					// ").replace("$2","effettivo inizio"));
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_155
							.replace("$1", "Scheda Modifica Contrattuale - Data atto aggiuntivo ")
							.replace("$2", "effettivo inizio"));
					// Fine Ticket ALM #644

				}
			}
		}
		if (!isEmpty(vb.getNumGiorniProroga())) {
			if (!isNumber("" + vb.getNumGiorniProroga())) {
				// err
				mEccezioni.addValidationField("label_SchedaModificaContrattuale");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1",
						"Scheda Modifica Contrattuale - Numero giorni di proroga "));
			}

			if (SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneStr)) {
				if (vb.getNumGiorniProroga() <= 0 && verificaSelezioneProrogaTecnica(vb.getEmvb())) {
					mEccezioni.addValidationField("label_GiorniProroga");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
							"Scheda Modifica Contrattuale - Numero giorni di proroga "));
				}
//					else if(vb.getNumGiorniProroga()>0 && !verificaSelezioneProrogaTecnica(vb.getEmvb())) {
//						mEccezioni.addValidationField("label_GiorniProroga");
//				        mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Scheda Modifica Contrattuale - Numero giorni di proroga "));
//					}

			}

			if (SimogFlags.is3028_RFWEBSC02Active()) {
				if (vb.getNumGiorniProroga() > MAX_GIORNI)
					mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1",
							"Scheda Modifica Contrattuale - Numero giorni di proroga"), progressivo);
			} // TICKET ALM - 3.04.3 #4195
		} else if (SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneStr)
				&& verificaSelezioneProrogaTecnica(vb.getEmvb())) {
			// Se il campo non e' stato valorizzato ma e' stata selezionata la proroga
			// tecnica, mostra errore bloccante (controllo solo per gare create > 3.04.3)
			mEccezioni.addValidationField("label_GiorniProroga");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
					"Scheda Modifica Contrattuale - Numero giorni di proroga "));

		}
	}

	//// 3.04.15 MAD 61769
	private boolean isCIGPCP(String cigPCP) {
		String currentDate = PageHelper.getCurrentDate();
			logger.info("metodo isCIGPCP");

			Unirest.setTimeouts(0, 0);
			HttpResponse<String> response;
			logger.info("parametri chiamata CIG PCP----------------------------");
			logger.info("cigPCP: " + cigPCP);
			try {
				response = Unirest.get(SimogProperties.getInstance().getCigPcpUrl() + cigPCP)
						.header("Accept", "application/json").asString();
				if (response != null) {
					logger.info("request URL CIG_PCP-----------" + SimogProperties.getInstance().getCigPcpUrl()
							+ cigPCP);
					logger.info("response URL CIG_PCP-----------" + response.getBody());
					logger.info("status URL CIG_PCP-----------" + response.getStatus());
				} else {
					logger.info("response null URL CIG_PCP-----------");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VARIANTI_041);
					return false;
				}

				if (response.getStatus() == 200) {
					if (response.getBody().substring(1, response.getBody().length()-1) != null && !"".equals(response.getBody().substring(1, response.getBody().length()-1))) {
						JSONObject jsonObj = new JSONObject(response.getBody().substring(1, response.getBody().length()-1));
						logger.info(jsonObj);
						return true;
					}else {
						return false;
					}
					
				} else {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VARIANTI_041);
					return false;
				}

			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("UnirestException URL CIG_PCP-----------" + e);
				mEccezioni.addValidationErr(Messaggi.SIMOG_VARIANTI_041);
				return false;
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				logger.info("Exception URL CIG_PCP-----------" + ex);
				return false;
			}		
	}

	private boolean verificaSelezioneProrogaTecnica(List<EventiMotiviVariantiBean> emvb) {
		if (emvb == null)
			return false;
		for (EventiMotiviVariantiBean evento : emvb) {
			if (evento.getIdMotivoVariante().intValue() == Costanti.PROROGA_TECNICA)
				return true;
		}
		return false;
	}

	// MEV 34191 3.04.8
//	private boolean verificaSelezioneMotiviLinkVarianti(List<EventiMotiviVariantiBean> emvb) {
//		if(emvb==null) return false;
//		for(EventiMotiviVariantiBean evento : emvb) {
//			if(evento.getIdMotivoVariante().intValue()==Costanti.ALTRE_CAUSE || evento.getIdMotivoVariante().intValue()==Costanti.SOPRAVVENUTE_ESIGENZE)
//				return true;
//		}
//		return false;
//	}

	// TICKET ALM - 3.04.3 #4195
	private boolean verificaSelezioneAltreMotivazioni(List<EventiMotiviVariantiBean> emvb) {
		if (emvb == null)
			return false;
		for (EventiMotiviVariantiBean evento : emvb) {
			if (evento.getIdMotivoVariante().intValue() == Costanti.ALTRE_CAUSE)
				return true;
		}
		return false;
	}

	// TICKET ALM #2847 - Varianti
	public boolean validaEventiMotiviVariantiWithData(List<EventiMotiviVariantiBean> eventi, Object o,
			String dataCreazione) {
		int local_error = 0;
		boolean valida = true;
		int counter = 1;

		for (EventiMotiviVariantiBean evento : eventi) {

			if (evento.getIdMotivoVariante() != null) {

				// TICKET ALM #11507 - 3.04.4
				if (dataCreazione.compareTo(Costanti.DATA_DL50) <= 0
						|| SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazione))
					valida = super.validaTipologicaWithData(MOTIVI_VARIANTE.TABLE_NAME, MOTIVI_VARIANTE.ID_MOTIVO_VAR,
							MOTIVI_VARIANTE.DESCRIZIONE, MOTIVI_VARIANTE.DATA_INIZIO_VALIDITA,
							MOTIVI_VARIANTE.DATA_FINE_VALIDITA, o, evento.getIdMotivoVariante());
				else {
					if (evento.getIdMotivoVariante() == Costanti.PROROGA_TECNICA)
						valida = false;
					else
						valida = super.validaTipologicaNoData(MOTIVI_VARIANTE.TABLE_NAME, MOTIVI_VARIANTE.ID_MOTIVO_VAR,
								MOTIVI_VARIANTE.DESCRIZIONE, evento.getIdMotivoVariante());
				}

			} else
				valida = false;

			if (!valida) {
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Motivo Modifica Contrattuale"), counter);
				local_error++;
			}
			counter++;
		}
		return local_error == 0;
	}

	// MEV 34469 3.04.8
	private boolean validaSelezioneRevisioniPrezzi(List<EventiMotiviVariantiBean> emvb, VarianteBean vb) {
		if (emvb == null)
			return false;
		for (EventiMotiviVariantiBean evento : emvb) {
			if (evento.getIdMotivoVariante().intValue() == Costanti.MOTIVO_REVISIONE_PREZZI) {
				if (vb.getIdMotivoRevPrezzi() == null || vb.getIdMotivoRevPrezzi().equals("")) {
					mEccezioni.addValidationField("label_motivoRevPrezzi");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_290.replace("$1", "Scheda Variante - Motivo Revisioni Prezzi "));
				}
			}
		}
		return false;
	}

}
