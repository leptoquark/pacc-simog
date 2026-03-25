package it.avlp.simog.flusso;

import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.errormessage.Messaggi;

import java.util.ArrayList;

public class WorkFlowStipula extends _WorkFlow {

	@Override
	public boolean isInseribile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale) {
		int switcher = identificativo.getIndiceScheda();
		boolean esito = false;
		boolean temp = false;	
		switch (switcher) {
			case IdentificativoSchede.INDICE_DATI_COMUNI:
				/**
				 * Dati Comuni:
				 * -- Non esistono dati comuni
				 **/
				esito = !situazioneAttuale.getStatoDatiComuni().isEsistenteDb();
				if(!esito){
					this.addError(situazioneAttuale.getStatoDatiComuni(), IdentificativoSchede.DATI_COMUNI, 
					Messaggi.SIMOG_MASSLOADER_196);
				}
				break;
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
				/**
				 * Aggiudicazioni:
				 * -- Dati comuni confermati, Non esiste aggiudicazione, Esito procedura = "aggiudicata"
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoDatiComuni());
				if(!temp){
					this.addError(situazioneAttuale.getStatoAggiudicazione(), IdentificativoSchede.AGGIUDICAZIONE, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.DATI_COMUNI)
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.AGGIUDICAZIONE));
				}
				temp = !situazioneAttuale.getStatoAggiudicazione().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoAggiudicazione(), IdentificativoSchede.AGGIUDICAZIONE, 
							Messaggi.SIMOG_MASSLOADER_196);
				}
				temp = situazioneAttuale.getStatoDatiComuni().isAggiudicata();
				if(!temp){
					this.addError(situazioneAttuale.getStatoAggiudicazione(), IdentificativoSchede.AGGIUDICAZIONE, 
							Messaggi.SIMOG_MASSLOADER_183);
				}
				
				esito = controlloSchedaConfermata(situazioneAttuale.getStatoDatiComuni()) 
						&& !situazioneAttuale.getStatoAggiudicazione().isEsistenteDb()
						&& situazioneAttuale.getStatoDatiComuni().isAggiudicata();
			
				break;


			
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_FINE_LAVORI:
				/**
				 * Conclusione:
				 * -- aggiudicazione o inizio lavori o avanzamento confermati, Non esistono conclusione, collaudo, 
				 * -- sse esite sospensioni deve essere confermata
				 */
//				temp = schedaEsistenteEConfermata(situazioneAttuale.getStatoStipula());
//				
//				if(!temp){
//					String avviso = Messaggi.SIMOG_MASSLOADER_173
//						.replace("$1", "("  +IdentificativoSchede.STIPULA 
//									 +")"
//								) 
//						.replace("$2", "Inserire")
//						.replace("$3", IdentificativoSchede.FINE_LAVORI);
//
//					this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, avviso);
//				}
				temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, 
							Messaggi.SIMOG_MASSLOADER_196);
				}
				temp = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.FINE_LAVORI)
							.replace("$2", IdentificativoSchede.COLLAUDO)
							.replace("$3", "Inseribile")
					);	
				}
				
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				
				esito =  !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						&& controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione())
						//&& controlloSchedaConfermata(situazioneAttuale.getStatoStipula())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
//						
				break;
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_STIPULA:
				/**
				 * Inizio:
				 * -- Aggiudicazione confermata, Non esistono conclusione, avanzamento, inizio, sse esite r129 deve essere confermata
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione());
				if(!temp){
					this.addError(situazioneAttuale.getStatoStipula(), IdentificativoSchede.STIPULA, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.AGGIUDICAZIONE) 
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.STIPULA));							
				}
				temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoStipula(), IdentificativoSchede.STIPULA, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.STIPULA)
							.replace("$2", IdentificativoSchede.FINE_LAVORI)
							.replace("$3", "Inseribile")
					);							
				}
				
				temp = !situazioneAttuale.getStatoStipula().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoStipula(), IdentificativoSchede.STIPULA, 
							Messaggi.SIMOG_MASSLOADER_196);					
				}
				
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addError(situazioneAttuale.getStatoStipula(), IdentificativoSchede.STIPULA, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				esito = controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione())
						&& !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						
						&& !situazioneAttuale.getStatoStipula().isEsistenteDb()
					
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
				break;

/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_SUBAPPALTO:
				/**
				 * SubAppalto:
				 * -- aggiudicazione confermata, Non esite Collaudo, sse esite subappalto deve essere confermato
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSubAppalti(), IdentificativoSchede.SUBAPPALTO, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.AGGIUDICAZIONE) 
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.SUBAPPALTO));					
				}
				temp = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoSubAppalti());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSubAppalti(), IdentificativoSchede.SUBAPPALTO, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.SUBAPPALTO));						
				}
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSubAppalti(), IdentificativoSchede.SUBAPPALTO, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				
				esito = controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione())
						
						&& nonEsisteOConfermata(situazioneAttuale.getStatoSubAppalti())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
				break;
				
				//TICKET ALM #14626 - 3.04.5
			case IdentificativoSchede.INDICE_VARIANTE:
				/**
				 * Variante:
				 * -- aggiudicazione confermata, Non esite Collaudo, sse esite variante deve essere confermata
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoVarianti(), IdentificativoSchede.VARIANTE, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.AGGIUDICAZIONE) 
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.VARIANTE));					
				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoVarianti());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoVarianti(), IdentificativoSchede.VARIANTE, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.VARIANTE));					
				}
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoVarianti(), IdentificativoSchede.VARIANTE, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione())
						&& !situazioneAttuale.getStatoCollaudo().isEsistenteDb()
						&& nonEsisteOConfermata(situazioneAttuale.getStatoVarianti())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
			
				break;	
				
			default:
				this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_207.replace("$1","Stipula"));
		}
		return esito;
	}

	@Override
	public boolean isCancellabile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale) {
		int switcher = identificativo.getIndiceScheda();
		boolean esito = false;
		boolean temp = false;
		
		switch (switcher) {
			case IdentificativoSchede.INDICE_DATI_COMUNI:
				/**
				 * Dati Comuni:
				 * -- Non esiste aggiudicazione
				 **/
				esito = !situazioneAttuale.getStatoAggiudicazione().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.DATI_COMUNI, 
						IdentificativoSchede.AGGIUDICAZIONE, situazioneAttuale.getStatoDatiComuni());
				break;
			case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
				/**
				 * Aggiudicazioni:
				 * -- Non esistono stipula,conclusione,r129,subappalti,variante
				 */
				esito = !situazioneAttuale.getStatoStipula().isEsistenteDb()
						&& !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						
						&& !isEsistente(situazioneAttuale.getStatoSubAppalti());
						
				// controllo che sia stato rilevato errore prima, prestazioni
				if(!esito){
					temp = !situazioneAttuale.getStatoStipula().isEsistenteDb();
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.STIPULA,  situazioneAttuale.getStatoAggiudicazione());
	
					temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoAggiudicazione());
	
					
	
					temp = !isEsistente(situazioneAttuale.getStatoSubAppalti());
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.SUBAPPALTO, situazioneAttuale.getStatoAggiudicazione());
	
				}
				break;
				
			case IdentificativoSchede.INDICE_STIPULA:
				/**
				 * Conclusione:
				 * -- Non esiste conclusione
				 */
				esito = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.STIPULA, 
						IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoStipula());
				break;
				
	        case IdentificativoSchede.INDICE_FINE_LAVORI:
               /**
                * Conclusione:
                * -- Non esiste Collaudo
                */
               esito = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
               addErrore178SeNecessario(!esito, IdentificativoSchede.FINE_LAVORI, 
                       IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoConclusione());
               break;
               
			case IdentificativoSchede.INDICE_SUBAPPALTO:
				/**
				 * SubAppalto:
				 * -- Non esiste Collaudo
				 */
				esito = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.SUBAPPALTO, 
						IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoSubAppalti());
				break;
			//TICKET ALM #14626 - 3.04.5
			case IdentificativoSchede.INDICE_VARIANTE:
				/**
				 * Variante:
				 * -- Non esiste Collaudo
				 */
				esito = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.VARIANTE, 
						IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoVarianti());
				break;
				
			default:
				this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_207.replace("$1","Stipula"));
			
		}
		return esito;
	}

	@Override
	public EsitoControlloStatiSchede isNotFlussoInRichiesta(
			SituazioneSchedeAttuale situazioneAttuale,
			IdentificativoSchede identificativo, String cig, String cui) {
		EsitoControlloStatiSchede esitoStati = new EsitoControlloStatiSchede();
		boolean esito = true;

		if(identificativo != null){
			// controllo esistenza scheda "minimale" ovverosia se esiste la scheda di cui riferimenti passati dal cliente.
			if(identificativo.getIndiceScheda() == IdentificativoSchede.getDatiComuni().getIndiceScheda()){			
				if(!situazioneAttuale.getStatoDatiComuni().isEsistenteDb()){
					esitoStati.setEsitoOperazione(false);
					situazioneAttuale.getStatoDatiComuni().setCig(cig);
					situazioneAttuale.getStatoDatiComuni().setCui(cui);
					esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBean(Messaggi.SIMOG_MASSLOADER_187, IdentificativoSchede.DATI_COMUNI, situazioneAttuale.getStatoDatiComuni()));
					esitoStati.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_197.replace("$1", "CIG"));
					return esitoStati;
				}
			}
			if(identificativo.getIndiceScheda() == IdentificativoSchede.getAggiudicazione().getIndiceScheda()){			
				if(!situazioneAttuale.getStatoAggiudicazione().isEsistenteDb()){
					esitoStati.setEsitoOperazione(false);
					situazioneAttuale.getStatoAggiudicazione().setCig(cig);
					situazioneAttuale.getStatoAggiudicazione().setCui(cui);
					esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBean(Messaggi.SIMOG_MASSLOADER_186, IdentificativoSchede.AGGIUDICAZIONE, situazioneAttuale.getStatoAggiudicazione()));
					esitoStati.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_197.replace("$1", "CUI"));
					return esitoStati;
				}
			}
			
		}
		// end
		
		
		/** Conclusione **/
		if(situazioneAttuale.getStatoConclusione().isEsistenteDb()){
			
			if(situazioneAttuale.getStatoConclusione().isInRichiestaAnnullamento()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoConclusione()));
			}
			if(situazioneAttuale.getStatoConclusione().isInRichiestaCancellazione()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoConclusione()));
			}
		}
		
		/** Stipula **/
		if(situazioneAttuale.getStatoStipula().isEsistenteDb()){
			
			if(situazioneAttuale.getStatoStipula().isInRichiestaAnnullamento()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.STIPULA, situazioneAttuale.getStatoStipula()));
			}
			if(situazioneAttuale.getStatoStipula().isInRichiestaCancellazione()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.STIPULA, situazioneAttuale.getStatoStipula()));
			}

		}	
		
		/** SubAppalti **/
		ArrayList<StatoScheda> statiSchede = situazioneAttuale.getStatoSubAppalti();
		if(statiSchede != null && statiSchede.size() > 0){
			for(StatoScheda statoCorrente : statiSchede){
				if(statoCorrente.isEsistenteDb()){
					if(statoCorrente.isInRichiestaAnnullamento()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.SUBAPPALTO, statoCorrente));
					}
					if(statoCorrente.isInRichiestaCancellazione()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.SUBAPPALTO, statoCorrente));
					}
				}
			}
		}
		
		/** Aggiudicazione **/
		if(situazioneAttuale.getStatoAggiudicazione().isEsistenteDb()){
			
			if(situazioneAttuale.getStatoAggiudicazione().isInRichiestaAnnullamento()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.AGGIUDICAZIONE, situazioneAttuale.getStatoAggiudicazione()));
			}
			if(situazioneAttuale.getStatoAggiudicazione().isInRichiestaCancellazione()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.AGGIUDICAZIONE, situazioneAttuale.getStatoAggiudicazione()));
			}

		}	
		
		/** Dati comuni **/
		if(situazioneAttuale.getStatoDatiComuni().isEsistenteDb()){
			
			if(situazioneAttuale.getStatoDatiComuni().isInRichiestaAnnullamento()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.DATI_COMUNI, situazioneAttuale.getStatoDatiComuni()));
			}
			if(situazioneAttuale.getStatoDatiComuni().isInRichiestaCancellazione()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.DATI_COMUNI, situazioneAttuale.getStatoDatiComuni()));;
			}
		}
		esitoStati.setEsitoOperazione(esito);
		return esitoStati;
	}
	
	public EsitoControlloStatiSchede isNotFlussoInDefinizione(SituazioneSchedeAttuale situazioneAttuale){

		EsitoControlloStatiSchede esitoStati = new EsitoControlloStatiSchede();
		boolean esito = true;
		
		
		/** Conclusione **/
		if(situazioneAttuale.getStatoConclusione().isEsistenteDb())
			if(!situazioneAttuale.getStatoConclusione().isConfermata()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoConclusione()));
			}
		
		
		/** Stipula **/
		if(situazioneAttuale.getStatoStipula().isEsistenteDb())
			if(!situazioneAttuale.getStatoStipula().isConfermata()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.STIPULA, situazioneAttuale.getStatoStipula()));
			}
		
		
		/** SubAppalti **/
		ArrayList<StatoScheda> statiSchedeSubappalti = situazioneAttuale.getStatoSubAppalti();
		if(statiSchedeSubappalti != null && statiSchedeSubappalti.size() > 0){
			for(StatoScheda statoCorrente : statiSchedeSubappalti){
				if(statoCorrente.isEsistenteDb())
					if(!statoCorrente.isConfermata()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.SUBAPPALTO, statoCorrente));
					}
			}
		}
		
		/** Aggiudicazione **/
		if(situazioneAttuale.getStatoAggiudicazione().isEsistenteDb())
			if(!situazioneAttuale.getStatoAggiudicazione().isConfermata()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.AGGIUDICAZIONE, situazioneAttuale.getStatoAggiudicazione()));
			}
		
		
		/** Dati comuni **/
		if(situazioneAttuale.getStatoDatiComuni().isEsistenteDb())
			if(!situazioneAttuale.getStatoDatiComuni().isConfermata()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.DATI_COMUNI, situazioneAttuale.getStatoDatiComuni()));
			}
		
		esitoStati.setEsitoOperazione(esito);
		return esitoStati;
	}
	

	@Override
	public boolean isModificabile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale) {
		
		return true;
	}

}
