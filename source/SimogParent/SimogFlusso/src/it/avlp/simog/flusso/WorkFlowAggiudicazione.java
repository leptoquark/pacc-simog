package it.avlp.simog.flusso;

import java.util.ArrayList;

import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.validatore.TipoFlusso;

public class WorkFlowAggiudicazione extends _WorkFlow {

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
			case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
				/**
				 * Accordi:
				 * -- inizio lavori confermati, non esiste Collaudo, sse esite accordi deve essere confermato
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoInizioLavori());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAccordi(), IdentificativoSchede.ACCORDO_BONARIO, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.FASE_INIZIALE)
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.ACCORDO_BONARIO));				
				}
				temp = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAccordi(), IdentificativoSchede.ACCORDO_BONARIO, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.ACCORDO_BONARIO)
							.replace("$2", IdentificativoSchede.COLLAUDO)
							.replace("$3", "Inseribile")
					);		
				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoAccordi());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAccordi(), IdentificativoSchede.ACCORDO_BONARIO, 
					Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.ACCORDO_BONARIO));					
				}
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();

				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAccordi(), IdentificativoSchede.ACCORDO_BONARIO, 
					Messaggi.SIMOG_MASSLOADER_176);					
				}
				
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoInizioLavori()) 
						&& !situazioneAttuale.getStatoCollaudo().isEsistenteDb() 
						&& nonEsisteOConfermata(situazioneAttuale.getStatoAccordi())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
			
				break;
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
				/**
				 * Avanzamento:
				 * -- Inizio lavori confermati, Non esistono conclusioni, sse esite avanzamento deve essere confermato
				 *  -- Inizio lavori confermati, Non esistono collaudi (PP ex. conclusioni), 
+				 * 		se esiste avanzamento deve essere confermato
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoInizioLavori());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAvanzamento(), IdentificativoSchede.STATO_AVANZAMENTO, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.FASE_INIZIALE)
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.STATO_AVANZAMENTO));
				}
				// PP 20101124 temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				temp = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAvanzamento(), IdentificativoSchede.STATO_AVANZAMENTO, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.STATO_AVANZAMENTO)
							.replace("$2", IdentificativoSchede.COLLAUDO)
							.replace("$3", "Inseribile")
					);	
				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoAvanzamento());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAvanzamento(), IdentificativoSchede.STATO_AVANZAMENTO, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.STATO_AVANZAMENTO));
				}
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoAvanzamento(), IdentificativoSchede.STATO_AVANZAMENTO, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoInizioLavori())
						// PP 20101124 && !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						&& !situazioneAttuale.getStatoCollaudo().isEsistenteDb()
						&& nonEsisteOConfermata(situazioneAttuale.getStatoAvanzamento())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
				
				break;
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_COLLAUDO:
				/**
				 * Collaudo:
				 * -- conclusione confermata, sse esistono accordi, varianti, subappalti devono essere confermati, non deve essere presente una scheda collaudo
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoConclusione());
				if(!temp){
					this.addError(situazioneAttuale.getStatoCollaudo(), IdentificativoSchede.COLLAUDO, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.FINE_LAVORI)
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.COLLAUDO));

				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoAccordi());
				if(!temp){
					this.addError(situazioneAttuale.getStatoCollaudo(), IdentificativoSchede.COLLAUDO, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.ACCORDO_BONARIO));

				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoVarianti());
				if(!temp){
					this.addError(situazioneAttuale.getStatoCollaudo(), IdentificativoSchede.COLLAUDO, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.VARIANTE));

				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoSubAppalti());
				if(!temp){
					this.addError(situazioneAttuale.getStatoCollaudo(), IdentificativoSchede.COLLAUDO, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.SUBAPPALTO));

				}
				// PP 20101124 anche stati avanzamento
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoAvanzamento());
				if(!temp){
					this.addError(situazioneAttuale.getStatoCollaudo(), IdentificativoSchede.COLLAUDO, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.STATO_AVANZAMENTO));
				}
							
				
				temp = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoCollaudo(), IdentificativoSchede.COLLAUDO, 
							Messaggi.SIMOG_MASSLOADER_196);
				}
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addError(situazioneAttuale.getStatoCollaudo(), IdentificativoSchede.COLLAUDO, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoConclusione())
						&& nonEsisteOConfermata(situazioneAttuale.getStatoAccordi())
						&& nonEsisteOConfermata(situazioneAttuale.getStatoVarianti())
						&& nonEsisteOConfermata(situazioneAttuale.getStatoSubAppalti())
						// PP 20101124 anche stati avanzamento
						&& nonEsisteOConfermata(situazioneAttuale.getStatoAvanzamento())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
				
				break;
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_FINE_LAVORI:
				/**
				 * Conclusione:
				 * -- aggiudicazione o inizio lavori o avanzamento confermati, Non esistono conclusione, collaudo, 
				 * -- sse esite sospensioni deve essere confermata
				 * 
				 * 
				 * 
				 */
				
				
				if(situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali()){
					esito = schedaEsistenteEConfermata(situazioneAttuale.getStatoAggiudicazione());
					if(!esito){
						String avviso = Messaggi.SIMOG_MASSLOADER_173
						.replace("$1", IdentificativoSchede.AGGIUDICAZIONE 
									

								) 
						.replace("$2", "Inserire")
						.replace("$3", IdentificativoSchede.FINE_LAVORI);
						this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, avviso);
						break;
					}
					esito = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				   if(!esito){
						
						this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, 
									Messaggi.SIMOG_MASSLOADER_196);
						break;
				   }
				}
						
				
				temp = schedaEsistenteEConfermata(situazioneAttuale.getStatoAggiudicazione())
						||	schedaEsistenteEConfermata(situazioneAttuale.getStatoInizioLavori())
						|| schedaEsistenteEConfermata(situazioneAttuale.getStatoAvanzamento());
				
				if(!temp){
					String avviso = Messaggi.SIMOG_MASSLOADER_173
						.replace("$1", "(" + IdentificativoSchede.AGGIUDICAZIONE 
									+ " oppure " +IdentificativoSchede.FASE_INIZIALE 
									+ " oppure " +IdentificativoSchede.STATO_AVANZAMENTO +")"
								) 
						.replace("$2", "Inserire")
						.replace("$3", IdentificativoSchede.FINE_LAVORI);

					this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, avviso);
				}
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
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoSospensioni());
				if(!temp){
					this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.SOSPENSIONE));
				}
// PP 26042012 ammessa conclusione anche per settori speciali
//				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali();
//				if(!temp){
//					this.addError(situazioneAttuale.getStatoConclusione(), IdentificativoSchede.FINE_LAVORI, 
//							Messaggi.SIMOG_MASSLOADER_176);
//				}
				
				
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& (
						schedaEsistenteEConfermata(situazioneAttuale.getStatoAggiudicazione())
						||	schedaEsistenteEConfermata(situazioneAttuale.getStatoInizioLavori())
						|| schedaEsistenteEConfermata(situazioneAttuale.getStatoAvanzamento())
						)
						&& !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						&& !situazioneAttuale.getStatoCollaudo().isEsistenteDb()
						&& nonEsisteOConfermata(situazioneAttuale.getStatoSospensioni())
						// PP 26042012 ammessa conclusione anche per settori speciali
						//&& !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali()
						;		
				
				break;
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
				temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoInizioLavori(), IdentificativoSchede.FASE_INIZIALE, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.FASE_INIZIALE)
							.replace("$2", IdentificativoSchede.FINE_LAVORI)
							.replace("$3", "Inseribile")
					);							
				}
				temp = !isEsistente(situazioneAttuale.getStatoAvanzamento());
				if(!temp){
					this.addError(situazioneAttuale.getStatoInizioLavori(), IdentificativoSchede.FASE_INIZIALE, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.FASE_INIZIALE)
							.replace("$2", IdentificativoSchede.STATO_AVANZAMENTO)
							.replace("$3", "Inseribile")
					);								
				}
				temp = !situazioneAttuale.getStatoInizioLavori().isEsistenteDb();
				if(!temp){
					this.addError(situazioneAttuale.getStatoInizioLavori(), IdentificativoSchede.FASE_INIZIALE, 
							Messaggi.SIMOG_MASSLOADER_196);					
				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoRitardo());
				if(!temp){
					this.addError(situazioneAttuale.getStatoInizioLavori(), IdentificativoSchede.FASE_INIZIALE, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.IPOTESI_RECESSO));
					
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
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione())
						&& !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						&& !isEsistente(situazioneAttuale.getStatoAvanzamento())
						&& !situazioneAttuale.getStatoInizioLavori().isEsistenteDb()
						&& nonEsisteOConfermata(situazioneAttuale.getStatoRitardo())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
				break;
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
				/**
				 * R129:
				 * -- aggiudicazione confermata, Non esite inizio lavori, se esite r129 deve essere confermata, 
				 * -- il contratto e' di tipo lavori
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoRitardo(), IdentificativoSchede.IPOTESI_RECESSO, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.AGGIUDICAZIONE) 
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.IPOTESI_RECESSO));										
				}
				temp = !situazioneAttuale.getStatoInizioLavori().isEsistenteDb();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoRitardo(), IdentificativoSchede.IPOTESI_RECESSO, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.IPOTESI_RECESSO)
							.replace("$2", IdentificativoSchede.FASE_INIZIALE) 
							.replace("$3", "Inseribile")
							);					
				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoRitardo());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoRitardo(), IdentificativoSchede.IPOTESI_RECESSO, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.IPOTESI_RECESSO));
				}
				temp = situazioneAttuale.getStatoDatiComuni().isLavori();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoRitardo(), IdentificativoSchede.IPOTESI_RECESSO, 
							Messaggi.SIMOG_MASSLOADER_175);
				}
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoRitardo(), IdentificativoSchede.IPOTESI_RECESSO, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione())
						&& !situazioneAttuale.getStatoInizioLavori().isEsistenteDb()
						&& nonEsisteOConfermata(situazioneAttuale.getStatoRitardo())
						&& situazioneAttuale.getStatoDatiComuni().isLavori()
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
			
				
				break;
/*****************************************************************************************************************/
			case IdentificativoSchede.INDICE_SOSPENSIONE:
				/**
				 * Sospensioni:
				 * -- inizio lavori confermati, non esiste Conclusione, sse esite Sospensioni deve essere confermata
				 */
				temp = controlloSchedaConfermata(situazioneAttuale.getStatoInizioLavori());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSospensioni(), IdentificativoSchede.SOSPENSIONE, 
							Messaggi.SIMOG_MASSLOADER_173
							.replace("$1", IdentificativoSchede.FASE_INIZIALE) 
							.replace("$2", "Inserire")
							.replace("$3", IdentificativoSchede.SOSPENSIONE));					
				}
				temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSospensioni(), IdentificativoSchede.SOSPENSIONE, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.SOSPENSIONE)
							.replace("$2", IdentificativoSchede.FINE_LAVORI)
							.replace("$3", "Inseribile")
					);								
				}
				temp = nonEsisteOConfermata(situazioneAttuale.getStatoSospensioni());
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSospensioni(), IdentificativoSchede.SOSPENSIONE, 
							Messaggi.SIMOG_MASSLOADER_167.replace("$1", IdentificativoSchede.SOSPENSIONE));
				}
				temp = !situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSospensioni(), IdentificativoSchede.SOSPENSIONE, 
							Messaggi.SIMOG_MASSLOADER_176);
				}
				
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoInizioLavori())
						&& !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						&& nonEsisteOConfermata(situazioneAttuale.getStatoSospensioni())
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
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoSubAppalti(), IdentificativoSchede.SUBAPPALTO,
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.SUBAPPALTO)
							.replace("$2", IdentificativoSchede.COLLAUDO)
							.replace("$3", "Inseribile")
					);						
				}
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
				
				temp = situazioneAttuale.isMultilotto() ?  situazioneAttuale.isPrincipale() : true;  //se multilotto verifico principale...else continuo
				if(! temp ){
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_208);
				}
				
				esito = temp
						&& controlloSchedaConfermata(situazioneAttuale.getStatoAggiudicazione())
						&& !situazioneAttuale.getStatoCollaudo().isEsistenteDb()
						&& nonEsisteOConfermata(situazioneAttuale.getStatoSubAppalti())
						&& (!situazioneAttuale.getStatoDatiComuni().isSettoriSpeciali() || situazioneAttuale.getStatoDatiComuni().isObblighiComunicativiSpeciali());
				
				
				break;
/*****************************************************************************************************************/
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
				temp = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				if(!temp){
					this.addErrorForMulti(situazioneAttuale.getStatoVarianti(), IdentificativoSchede.VARIANTE, 
							Messaggi.SIMOG_MASSLOADER_178
							.replace("$1", IdentificativoSchede.VARIANTE)
							.replace("$2", IdentificativoSchede.COLLAUDO)
							.replace("$3", "Inseribile") 
					);							
					
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
					this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_207.replace("$1", "Aggiudicazione sopra 150k"));
			
		}
		return esito;
	}

	/**
	 * Controlla che una scheda sia cancellabile
	 * Una scheda e' cancellabile solamente se nel suo flusso NON esitono schede "in richiesta", 
	 * 		oltre ai "convenzionali" vincoli di flusso.
	 * 
	 * @param identificativo
	 * @param situazioneAttuale
	 * @return
	 */
	public boolean isCancellabile(IdentificativoSchede identificativo, SituazioneSchedeAttuale situazioneAttuale){
		
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
				 * -- Non esistono inizio,conclusione,r129,subappalti,variante
				 */
				esito = !situazioneAttuale.getStatoInizioLavori().isEsistenteDb()
						&& !situazioneAttuale.getStatoConclusione().isEsistenteDb()
						&& !isEsistente(situazioneAttuale.getStatoRitardo())
						&& !isEsistente(situazioneAttuale.getStatoSubAppalti())
						&& !isEsistente(situazioneAttuale.getStatoVarianti());
				// controllo che sia stato rilevato errore prima, prestazioni
				if(!esito){
					temp = !situazioneAttuale.getStatoInizioLavori().isEsistenteDb();
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.FASE_INIZIALE,  situazioneAttuale.getStatoAggiudicazione());
	
					temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoAggiudicazione());
	
					temp = !isEsistente(situazioneAttuale.getStatoRitardo());
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.IPOTESI_RECESSO, situazioneAttuale.getStatoAggiudicazione());
	
					temp = !isEsistente(situazioneAttuale.getStatoSubAppalti());
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.SUBAPPALTO, situazioneAttuale.getStatoAggiudicazione());
	
					temp = !isEsistente(situazioneAttuale.getStatoVarianti());
					addErrore178SeNecessario(!temp, IdentificativoSchede.AGGIUDICAZIONE, 
							IdentificativoSchede.VARIANTE, situazioneAttuale.getStatoAggiudicazione());

				}
				break;
			case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
				/**
				 * Accordi:
				 * -- Non esiste Collaudo
				 */
				esito = !situazioneAttuale.getStatoCollaudo().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.ACCORDO_BONARIO, 
						IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoAccordi());
				break;
			case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
				/**
				 * Avanzamento:
				 * -- Non esiste Conclusione
				 */
				esito = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.STATO_AVANZAMENTO, 
						IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoAvanzamento());
				break;
			case IdentificativoSchede.INDICE_COLLAUDO:
				/**
				 * Collaudo:
				 * -- sempre
				 */
				esito = true;
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
			case IdentificativoSchede.INDICE_FASE_INIZIALE:
				/**
				 * Inizio:
				 * -- Non esistono avanzamento, accordi, sospensione, fine lavori
				 */
				esito = !isEsistente(situazioneAttuale.getStatoAvanzamento())
						&& !isEsistente(situazioneAttuale.getStatoAccordi())
						&& !isEsistente(situazioneAttuale.getStatoSospensioni())
						&& !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				
				if(!esito){
					temp = !isEsistente(situazioneAttuale.getStatoAvanzamento());
					addErrore178SeNecessario(!temp, IdentificativoSchede.FASE_INIZIALE, 
							IdentificativoSchede.STATO_AVANZAMENTO, situazioneAttuale.getStatoInizioLavori());
					
					temp = !isEsistente(situazioneAttuale.getStatoAccordi());
					addErrore178SeNecessario(!temp, IdentificativoSchede.FASE_INIZIALE, 
							IdentificativoSchede.ACCORDO_BONARIO, situazioneAttuale.getStatoInizioLavori());
					
					temp = !isEsistente(situazioneAttuale.getStatoSospensioni());
					addErrore178SeNecessario(!temp, IdentificativoSchede.FASE_INIZIALE, 
							IdentificativoSchede.SOSPENSIONE, situazioneAttuale.getStatoInizioLavori());
					
					temp = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
					addErrore178SeNecessario(!temp, IdentificativoSchede.FASE_INIZIALE, 
							IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoInizioLavori());
				}
				break;
			case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
				/**
				 * R129:
				 * -- Non esite inizio lavori
				 */
				esito = !situazioneAttuale.getStatoInizioLavori().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.IPOTESI_RECESSO, 
						IdentificativoSchede.FASE_INIZIALE, situazioneAttuale.getStatoRitardo());
				break;
			case IdentificativoSchede.INDICE_SOSPENSIONE:
				/**
				 * Sospensioni:
				 * -- Non esiste Conclusione
				 */
				esito = !situazioneAttuale.getStatoConclusione().isEsistenteDb();
				addErrore178SeNecessario(!esito, IdentificativoSchede.SOSPENSIONE, 
						IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoSospensioni());
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
				this.addError(situazioneAttuale.getByIdScheda(identificativo.getIndiceScheda()), identificativo.getNomeScheda(), Messaggi.SIMOG_MASSLOADER_207.replace("$1", "Aggiudicazione sopra 150k"));
			
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
		
		/** Collaudo **/
		if(situazioneAttuale.getStatoCollaudo().isEsistenteDb()){
			
			if(situazioneAttuale.getStatoCollaudo().isInRichiestaAnnullamento()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoCollaudo()));
			}
			if(situazioneAttuale.getStatoCollaudo().isInRichiestaCancellazione()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoCollaudo()));
			}

		}
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
		/** Avanzamenti **/
		ArrayList<StatoScheda> statiSchede = situazioneAttuale.getStatoAvanzamento();
		if(statiSchede != null && statiSchede.size() > 0){
			for(StatoScheda statoCorrente : statiSchede){
				if(statoCorrente.isEsistenteDb()){
					if(statoCorrente.isInRichiestaAnnullamento()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.STATO_AVANZAMENTO, statoCorrente));
					}
					if(statoCorrente.isInRichiestaCancellazione()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.STATO_AVANZAMENTO, statoCorrente));
					}
				}
			}
		}
		/** Sospensioni **/
		statiSchede = situazioneAttuale.getStatoSospensioni();
		if(statiSchede != null && statiSchede.size() > 0){
			for(StatoScheda statoCorrente : statiSchede){
				if(statoCorrente.isEsistenteDb()){
					if(statoCorrente.isInRichiestaAnnullamento()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.SOSPENSIONE, statoCorrente));
					}
					if(statoCorrente.isInRichiestaCancellazione()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.SOSPENSIONE, statoCorrente));
					}
				}
			}
		}
		/** Accordi **/
		statiSchede = situazioneAttuale.getStatoAccordi();
		if(statiSchede != null && statiSchede.size() > 0){
			for(StatoScheda statoCorrente : statiSchede){
				if(statoCorrente.isEsistenteDb()){
					if(statoCorrente.isInRichiestaAnnullamento()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.ACCORDO_BONARIO, statoCorrente));
					}
					if(statoCorrente.isInRichiestaCancellazione()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.ACCORDO_BONARIO, statoCorrente));
					}
				}
			}
		}
		/** InizioLavori **/
		if(situazioneAttuale.getStatoInizioLavori().isEsistenteDb()){
			
			if(situazioneAttuale.getStatoInizioLavori().isInRichiestaAnnullamento()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.FASE_INIZIALE, situazioneAttuale.getStatoInizioLavori()));
			}
			if(situazioneAttuale.getStatoInizioLavori().isInRichiestaCancellazione()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.FASE_INIZIALE, situazioneAttuale.getStatoInizioLavori()));
			}

		}	
		/** Ipotesi recesso  **/
		statiSchede = situazioneAttuale.getStatoRitardo();
		if(statiSchede != null && statiSchede.size() > 0){
			for(StatoScheda statoCorrente : statiSchede){
				if(statoCorrente.isEsistenteDb()){
					if(statoCorrente.isInRichiestaAnnullamento()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.IPOTESI_RECESSO, statoCorrente));
					}
					if(statoCorrente.isInRichiestaCancellazione()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.IPOTESI_RECESSO, statoCorrente));
					}
				}
			}
		}
		/** SubAppalti **/
		statiSchede = situazioneAttuale.getStatoSubAppalti();
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
		/** Varianti **/
		statiSchede = situazioneAttuale.getStatoVarianti();
		if(statiSchede != null && statiSchede.size() > 0){
			for(StatoScheda statoCorrente : statiSchede){
				if(statoCorrente.isEsistenteDb()){
					if(statoCorrente.isInRichiestaAnnullamento()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInRichAnn(IdentificativoSchede.VARIANTE, statoCorrente));
					}
					if(statoCorrente.isInRichiestaCancellazione()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInCanc(IdentificativoSchede.VARIANTE, statoCorrente));
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
		
		/** Collaudo **/
		if(situazioneAttuale.getStatoCollaudo().isEsistenteDb())			
			if(!situazioneAttuale.getStatoCollaudo().isConfermata()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.COLLAUDO, situazioneAttuale.getStatoCollaudo()));
			}

		/** Conclusione **/
		if(situazioneAttuale.getStatoConclusione().isEsistenteDb())
			if(!situazioneAttuale.getStatoConclusione().isConfermata()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.FINE_LAVORI, situazioneAttuale.getStatoConclusione()));
			}
		
		/** Avanzamenti **/// 
		ArrayList<StatoScheda> statiSchedeAvanzamenti = situazioneAttuale.getStatoAvanzamento();
		if(statiSchedeAvanzamenti != null && statiSchedeAvanzamenti.size() > 0){
			for(StatoScheda statoCorrente : statiSchedeAvanzamenti){
				if(statoCorrente.isEsistenteDb())
					if(!statoCorrente.isConfermata()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.STATO_AVANZAMENTO, statoCorrente));
					}
			}
		}
		/** Sospensioni **/
		ArrayList<StatoScheda> statiSchedeSospensioni = situazioneAttuale.getStatoSospensioni();
		if(statiSchedeSospensioni != null && statiSchedeSospensioni.size() > 0){
			for(StatoScheda statoCorrente : statiSchedeSospensioni){
				if(statoCorrente.isEsistenteDb())
					if(!statoCorrente.isConfermata()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.SOSPENSIONE, statoCorrente));
					}
			}
		}
		/** Accordi **/
		ArrayList<StatoScheda> statiSchedeAccordi = situazioneAttuale.getStatoAccordi();
		if(statiSchedeAccordi != null && statiSchedeAccordi.size() > 0){
			for(StatoScheda statoCorrente : statiSchedeAccordi){
				if(statoCorrente.isEsistenteDb())
					if(!statoCorrente.isConfermata()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.ACCORDO_BONARIO, statoCorrente));
					}
			}
		}
		/** InizioLavori **/
		if(situazioneAttuale.getStatoInizioLavori().isEsistenteDb())
			if(!situazioneAttuale.getStatoInizioLavori().isConfermata()){
				esito = false;
				esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.FASE_INIZIALE, situazioneAttuale.getStatoInizioLavori()));
			}
		
		/** Ipotesi recesso  **/
		ArrayList<StatoScheda> statiSchedeRitardi = situazioneAttuale.getStatoRitardo();
		if(statiSchedeRitardi != null && statiSchedeRitardi.size() > 0){
			for(StatoScheda statoCorrente : statiSchedeRitardi){
				if(statoCorrente.isEsistenteDb())
					if(!statoCorrente.isConfermata()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.IPOTESI_RECESSO, statoCorrente));
					
					}
			}
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
		/** Varianti **/
		ArrayList<StatoScheda> statiSchedeVarianti = situazioneAttuale.getStatoVarianti();
		if(statiSchedeVarianti != null && statiSchedeVarianti.size() > 0){
			for(StatoScheda statoCorrente : statiSchedeVarianti){
				if(statoCorrente.isEsistenteDb())
					if(!statoCorrente.isConfermata()){
						esito = false;
						esitoStati.addListOfValidationBeans(EsitoControlloStatiSchede.getValidationBeanInDefinizione(IdentificativoSchede.VARIANTE, statoCorrente));
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
		// TODO Auto-generated method stub
		return true;
	}

}
