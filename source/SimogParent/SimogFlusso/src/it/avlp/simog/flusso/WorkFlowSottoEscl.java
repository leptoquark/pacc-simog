package it.avlp.simog.flusso;

import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.validatore.TipoFlusso;

public class WorkFlowSottoEscl extends _WorkFlow {

	public WorkFlowSottoEscl(boolean escluso) {
		super();
		this.tipoFlusso = escluso ? TipoFlusso.ESCLUSO : TipoFlusso.SOTTOSOGLIA;
	}

	private TipoFlusso tipoFlusso;

	public TipoFlusso getTipoFlusso() {
		return tipoFlusso;
	}

	// public void setTipoFlusso(TipoFlusso tipoFlusso) {
	// this.tipoFlusso = tipoFlusso;
	// }

	@Override
	public boolean isInseribile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale) {
		int switcher = identificativo.getIndiceScheda();
		boolean esito = false;
		boolean temp = false;
		switch (switcher) {
		case IdentificativoSchede.INDICE_DATI_COMUNI:
			/**
			 * Dati Comuni: -- Non esistono dati comuni
			 **/
			esito = !situazioneAttuale.getStatoDatiComuni().isEsistenteDb();
			if (!esito) {
				this.addError(situazioneAttuale.getStatoDatiComuni(),
						IdentificativoSchede.DATI_COMUNI,
						Messaggi.SIMOG_MASSLOADER_196);
			}
			break;
		/*****************************************************************************************************************/
		case IdentificativoSchede.INDICE_SOTTOSOGLIA:

		    // PP controllo se la scheda è coerente con il flusso
		   if(tipoFlusso.name().equals(TipoFlusso.SOTTOSOGLIA.name())){
      			/**
      			 * Aggiudicazioni: -- Dati comuni confermati, Non esiste
      			 * aggiudicazione, Esito procedura = "aggiudicata"
      			 */
      			temp = controlloSchedaConfermata(situazioneAttuale
      					.getStatoDatiComuni());
      			if (!temp) {
      				this.addError(
      						situazioneAttuale.getStatoSottosoglia(),
      						IdentificativoSchede.SOTTOSOGLIA,
      						Messaggi.SIMOG_MASSLOADER_173
      								.replace("$1", IdentificativoSchede.DATI_COMUNI)
      								.replace("$2", "Inserire")
      								.replace("$3", IdentificativoSchede.SOTTOSOGLIA));
      			}
      			temp = !situazioneAttuale.getStatoSottosoglia().isEsistenteDb();
      			if (!temp) {
      				this.addError(situazioneAttuale.getStatoSottosoglia(),
      						IdentificativoSchede.SOTTOSOGLIA,
      						Messaggi.SIMOG_MASSLOADER_196);
      			}
      			temp = situazioneAttuale.getStatoDatiComuni().isAggiudicata();
      			if (!temp) {
      				this.addError(situazioneAttuale.getStatoSottosoglia(),
      						IdentificativoSchede.SOTTOSOGLIA,
      						Messaggi.SIMOG_MASSLOADER_183);
      			}
      
      			esito = controlloSchedaConfermata(situazioneAttuale
      					.getStatoDatiComuni())
      					&& !situazioneAttuale.getStatoSottosoglia().isEsistenteDb()
      					&& situazioneAttuale.getStatoDatiComuni().isAggiudicata()
      					&& TipoFlusso.SOTTOSOGLIA.equals(tipoFlusso);
      
      			break;
		   }
		case IdentificativoSchede.INDICE_ESCLUSO:

           // PP controllo se la scheda è coerente con il flusso
          if(tipoFlusso.name().equals(TipoFlusso.ESCLUSO.name())){
			/**
			 * Aggiudicazioni: -- Dati comuni confermati, Non esiste
			 * aggiudicazione, Esito procedura = "aggiudicata"
			 */
			temp = controlloSchedaConfermata(situazioneAttuale
					.getStatoDatiComuni());
			if (!temp) {
				this.addError(
						situazioneAttuale.getStatoEscluso(),
						IdentificativoSchede.ESCLUSO,
						Messaggi.SIMOG_MASSLOADER_173
								.replace("$1", IdentificativoSchede.DATI_COMUNI)
								.replace("$2", "Inserire")
								.replace("$3", IdentificativoSchede.ESCLUSO));
			}
			temp = !situazioneAttuale.getStatoEscluso().isEsistenteDb();
			if (!temp) {
				this.addError(situazioneAttuale.getStatoEscluso(),
						IdentificativoSchede.ESCLUSO,
						Messaggi.SIMOG_MASSLOADER_196);
			}
			temp = situazioneAttuale.getStatoDatiComuni().isAggiudicata();
			if (!temp) {
				this.addError(situazioneAttuale.getStatoEscluso(),
						IdentificativoSchede.ESCLUSO,
						Messaggi.SIMOG_MASSLOADER_183);
			}

			esito = controlloSchedaConfermata(situazioneAttuale
					.getStatoDatiComuni())
					&& !situazioneAttuale.getStatoEscluso().isEsistenteDb()
					&& situazioneAttuale.getStatoDatiComuni().isAggiudicata()
					&& TipoFlusso.ESCLUSO.equals(tipoFlusso);

			break;
          }
          /*****************************************************************************************************************/
		case IdentificativoSchede.INDICE_FASE_INIZIALE:
			/**
			 * Inizio:
			 * -- Aggiudicazione confermata, Non esistono conclusione, avanzamento, inizio, sse esite r129 deve essere confermata
			 */
			temp = controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione());
			if(!temp){
				this.addError(situazioneAttuale.getStatoInizioLavori(), IdentificativoSchede.FASE_INIZIALE, 
						Messaggi.SIMOG_MASSLOADER_173
						.replace("$1", IdentificativoSchede.AGGIUDICAZIONE) 
						.replace("$2", "Inserire")
						.replace("$3", IdentificativoSchede.FASE_INIZIALE));							
			}
			temp = !situazioneAttuale.getStatoInizioLavori().isEsistenteDb();
			if(!temp){
				this.addError(situazioneAttuale.getStatoInizioLavori(), IdentificativoSchede.FASE_INIZIALE, 
						Messaggi.SIMOG_MASSLOADER_196);					
			}
			temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
			if(!temp){
				this.addError(situazioneAttuale.getStatoInizioLavori(), IdentificativoSchede.FASE_INIZIALE, 
						Messaggi.SIMOG_MASSLOADER_176);
			}
			
			temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
			if(! temp ){
				this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
			}
			
			esito = temp;
			
			break;
/*****************************************************************************************************************/
		default:
			esito = false;
			this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_207.replace("$1", "Contratto " + tipoFlusso));

		}
		return esito;
	}

	@Override
	public boolean isCancellabile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale) {
		int switcher = identificativo.getIndiceScheda();
		boolean esito = false;
	

		switch (switcher) {
		case IdentificativoSchede.INDICE_DATI_COMUNI:
			/**
			 * Dati Comuni: -- Non esiste aggiudicazione
			 **/
			esito = TipoFlusso.ESCLUSO.equals(tipoFlusso) ? !situazioneAttuale
					.getStatoEscluso().isEsistenteDb() : !situazioneAttuale
					.getStatoSottosoglia().isEsistenteDb();
			addErrore178SeNecessario(
					!esito,
					IdentificativoSchede.DATI_COMUNI,
					TipoFlusso.ESCLUSO.equals(tipoFlusso) ? IdentificativoSchede.ESCLUSO
							: IdentificativoSchede.SOTTOSOGLIA,
					situazioneAttuale.getStatoDatiComuni());
			break;

		case IdentificativoSchede.INDICE_SOTTOSOGLIA:
           // PP controllo se la scheda è coerente con il flusso
          if(tipoFlusso.name().equals(TipoFlusso.SOTTOSOGLIA.name())){
			esito = TipoFlusso.SOTTOSOGLIA.equals(tipoFlusso);
			break;
          }
		case IdentificativoSchede.INDICE_ESCLUSO:
           // PP controllo se la scheda è coerente con il flusso
          if(tipoFlusso.name().equals(TipoFlusso.ESCLUSO.name())){
			esito = TipoFlusso.ESCLUSO.equals(tipoFlusso);
			break;
          }
		default:
			this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_207.replace("$1", "Contratto " + tipoFlusso));
		}

		return esito;
	}

	@Override
	public EsitoControlloStatiSchede isNotFlussoInRichiesta(
			SituazioneSchedeAttuale situazioneAttuale,
			IdentificativoSchede identificativo, String cig, String cui) {
		EsitoControlloStatiSchede esitoStati = new EsitoControlloStatiSchede();
		boolean esito = true;

		if (identificativo != null) {
			// controllo esistenza scheda "minimale" ovverosia se esiste la
			// scheda di cui riferimenti passati dal cliente.
			if (identificativo.getIndiceScheda() == IdentificativoSchede
					.getDatiComuni().getIndiceScheda()) {
				if (!situazioneAttuale.getStatoDatiComuni().isEsistenteDb()) {
					esitoStati.setEsitoOperazione(false);
					situazioneAttuale.getStatoDatiComuni().setCig(cig);
					situazioneAttuale.getStatoDatiComuni().setCui(cui);
					esitoStati
							.addListOfValidationBeans(EsitoControlloStatiSchede
									.getValidationBean(
											Messaggi.SIMOG_MASSLOADER_187,
											IdentificativoSchede.DATI_COMUNI,
											situazioneAttuale
													.getStatoDatiComuni()));
					esitoStati.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_197
							.replace("$1", "CIG"));
					return esitoStati;
				}
			}

			if (identificativo.getIndiceScheda() == IdentificativoSchede
					.getSottosoglia().getIndiceScheda()) {
				if (!situazioneAttuale.getStatoSottosoglia().isEsistenteDb()) {
					esitoStati.setEsitoOperazione(false);
					situazioneAttuale.getStatoSottosoglia().setCig(cig);
					situazioneAttuale.getStatoSottosoglia().setCui(cui);
					esitoStati
							.addListOfValidationBeans(EsitoControlloStatiSchede
									.getValidationBean(
											Messaggi.SIMOG_MASSLOADER_186,
											IdentificativoSchede.SOTTOSOGLIA,
											situazioneAttuale
													.getStatoSottosoglia()));
					esitoStati.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_197
							.replace("$1", "CUI"));
					return esitoStati;
				}
			}

			if (identificativo.getIndiceScheda() == IdentificativoSchede
					.getEscluso().getIndiceScheda()) {
				if (!situazioneAttuale.getStatoEscluso().isEsistenteDb()) {
					esitoStati.setEsitoOperazione(false);
					situazioneAttuale.getStatoEscluso().setCig(cig);
					situazioneAttuale.getStatoEscluso().setCui(cui);
					esitoStati
							.addListOfValidationBeans(EsitoControlloStatiSchede
									.getValidationBean(
											Messaggi.SIMOG_MASSLOADER_186,
											IdentificativoSchede.ESCLUSO,
											situazioneAttuale.getStatoEscluso()));
					esitoStati.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_197
							.replace("$1", "CUI"));
					return esitoStati;
				}
			}
		}
		// end

		/** Sottosoglia **/
		if (situazioneAttuale.getStatoSottosoglia().isEsistenteDb()) {

			if (situazioneAttuale.getStatoSottosoglia()
					.isInRichiestaAnnullamento()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInRichAnn(
								IdentificativoSchede.SOTTOSOGLIA,
								situazioneAttuale.getStatoSottosoglia()));
			}
			if (situazioneAttuale.getStatoSottosoglia()
					.isInRichiestaCancellazione()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInCanc(
								IdentificativoSchede.SOTTOSOGLIA,
								situazioneAttuale.getStatoSottosoglia()));
			}

		}

		/** Escluso **/
		if (situazioneAttuale.getStatoEscluso().isEsistenteDb()) {

			if (situazioneAttuale.getStatoEscluso().isInRichiestaAnnullamento()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInRichAnn(
								IdentificativoSchede.ESCLUSO,
								situazioneAttuale.getStatoEscluso()));
			}
			if (situazioneAttuale.getStatoEscluso()
					.isInRichiestaCancellazione()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInCanc(IdentificativoSchede.ESCLUSO,
								situazioneAttuale.getStatoEscluso()));
			}

		}
		/** Dati comuni **/
		if (situazioneAttuale.getStatoDatiComuni().isEsistenteDb()) {

			if (situazioneAttuale.getStatoDatiComuni()
					.isInRichiestaAnnullamento()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInRichAnn(
								IdentificativoSchede.DATI_COMUNI,
								situazioneAttuale.getStatoDatiComuni()));
			}
			if (situazioneAttuale.getStatoDatiComuni()
					.isInRichiestaCancellazione()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInCanc(
								IdentificativoSchede.DATI_COMUNI,
								situazioneAttuale.getStatoDatiComuni()));
				;
			}
		}
		esitoStati.setEsitoOperazione(esito);
		return esitoStati;
	}

	@Override
	public boolean isModificabile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EsitoControlloStatiSchede isNotFlussoInDefinizione(
			SituazioneSchedeAttuale situazioneAttuale) {

		EsitoControlloStatiSchede esitoStati = new EsitoControlloStatiSchede();
		boolean esito = true;

		/** Sottosoglia **/
		if (situazioneAttuale.getStatoSottosoglia().isEsistenteDb())
			if (!situazioneAttuale.getStatoSottosoglia().isConfermata()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInDefinizione(
								IdentificativoSchede.SOTTOSOGLIA,
								situazioneAttuale.getStatoSottosoglia()));
			}

		/** Esclusi **/
		if (situazioneAttuale.getStatoEscluso().isEsistenteDb())
			if (!situazioneAttuale.getStatoEscluso().isConfermata()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInDefinizione(
								IdentificativoSchede.ESCLUSO,
								situazioneAttuale.getStatoEscluso()));
			}

		/** Dati comuni **/
		if (situazioneAttuale.getStatoDatiComuni().isEsistenteDb())
			if (!situazioneAttuale.getStatoDatiComuni().isConfermata()) {
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede
						.getValidationBeanInDefinizione(
								IdentificativoSchede.DATI_COMUNI,
								situazioneAttuale.getStatoDatiComuni()));
			}

		esitoStati.setEsitoOperazione(esito);
		return esitoStati;
	}

}
