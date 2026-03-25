package it.avlp.simog.flusso;

import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.flusso.interfaccie.IOperazioniSchedaMassloader;
import it.avlp.simog.flusso.interfaccie.IOperazioniSchedaWeb;
import it.avlp.simog.validatore.TipoFlusso;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class WorkFlowController {

//	public List<SchedaSpecificaValidationBean> esitiOperazioni;
	
	private int progressivoSchedaCompleta;
	
	public List<SchedaSpecificaValidationBean> getEsitiOperazioni(){
		return workflow != null ? workflow.esitiOperazioni : new ArrayList<SchedaSpecificaValidationBean>();
	}

	private TipoFlusso tipoFlusso = TipoFlusso.AGGIUDICAZIONE;
	
	private _WorkFlow workflow;
	
	public WorkFlowController() {
		
		super();
		workflow = WorkFlowFactory.getWorkflow(tipoFlusso);
	}

	public WorkFlowController(TipoFlusso tipoFlusso) {
		super();
		this.tipoFlusso = tipoFlusso;
		Logger.getLogger("MASSLOADER_LOGGER").info("Richiesto workflow di tipo: " + (tipoFlusso != null ? tipoFlusso.name() : "null"));
		workflow = WorkFlowFactory.getWorkflow(tipoFlusso);
	}

	/**
	 * Metodo centralizzato (rispetto alle operazioni) per il controllo che l'operazione
	 * di cui in ingresso per la scheda di cui in ingresso per la situazione di cui
	 * in ingresso sia possibile.
	 * 
	 * 
	 * @param operazione
	 * @param identificativo
	 * @param situazioneAttuale
	 * @return
	 */
	public boolean isOperazioneEffettuabile(OperazioneScheda operazione,IdentificativoSchede identificativo, SituazioneSchedeAttuale situazioneAttuale){
		
		int operazioneCorrente = operazione.getIndiceOperazione();
		boolean esito = false;
		workflow.esitiOperazioni.clear();
		
		switch (operazioneCorrente) {
			case IOperazioniSchedaMassloader.INDICE_INSERIMENTO:
				esito = workflow.isInseribile(identificativo, situazioneAttuale);
				break;
			case IOperazioniSchedaMassloader.INDICE_MODIFICA:
				esito = workflow.isModificabile(identificativo, situazioneAttuale);
				break;
			case IOperazioniSchedaMassloader.INDICE_ELIMINAZIONE:
				esito = workflow.isCancellabile(identificativo, situazioneAttuale);
				break;
			case IOperazioniSchedaWeb.INDICE_CREAZIONE:
				break;
			case IOperazioniSchedaWeb.INDICE_CONFERMA:
				break;
			case IOperazioniSchedaWeb.INDICE_PRESA_IN_CARICO:
				break;
			case IOperazioniSchedaWeb.INDICE_RICHIESTA_ANNULLAMENTO:
				break;
		}
		return esito;
	}
	

	/**
	 * Metodo che si occupa di verificare che non ci sia nessuna scheda in richiesta annullamento 
	 * o in richiesta di cancellazione, che sono gli unici casi in cui una cancellazione "massiva"
	 * da cig o da cui non possa essere effettuata.
	 * Verifica anche che la scheda in oggetto dati comuni o aggiudicazione esista..
	 * 
	 * @param situazioneAttuale
	 * @param identificativo: dovrebbe essere dati comuni o aggiudicazione, altri valori non sortiscono effetti, non ne viene
	 * 			in sintesi controllata l'esistenza.(NOTA: deve essere in questo caso nullo)
	 * @return
	 */
	public EsitoControlloStatiSchede isNotFlussoInRichiesta(SituazioneSchedeAttuale situazioneAttuale, IdentificativoSchede identificativo, String cig, String cui){
		return workflow.isNotFlussoInRichiesta(situazioneAttuale, identificativo, cig, cui);
	}
	
	/**
	 * Metodo che si occupa di verificare che non ci sia nessuna scheda in richiesta annullamento 
	 * o in richiesta di cancellazione, che sono gli unici casi in cui una cancellazione "massiva"
	 * da cig o da cui non possa essere effettuata.
	 * 
	 * @param situazioneAttuale
	 * @param identificativo: dovrebbe essere dati comuni o aggiudicazione, altri valori non sortiscono effetti, non ne viene
	 * 			in sintesi controllata l'esistenza.(NOTA: non deve comunque essere nullo)
	 * @return
	 */
	public EsitoControlloStatiSchede isNotFlussoInDefinizione(SituazioneSchedeAttuale situazioneAttuale){

		return workflow.isNotFlussoInDefinizione(situazioneAttuale);
	}

	public int getProgressivoSchedaCompleta() {
		return progressivoSchedaCompleta;
	}

	public void setProgressivoSchedaCompleta(int progressivoSchedaCompleta) {
		this.progressivoSchedaCompleta = progressivoSchedaCompleta;
		if(this.workflow != null)
			this.workflow.progressivoSchedaCompleta = progressivoSchedaCompleta;
	}

}
