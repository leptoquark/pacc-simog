package it.avlp.simog.flusso;

import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.errormessage.Messaggi;

import java.util.ArrayList;
import java.util.List;

public abstract class _WorkFlow {

	public List<SchedaSpecificaValidationBean> esitiOperazioni = new ArrayList<SchedaSpecificaValidationBean>();

	public int progressivoSchedaCompleta;

	public abstract boolean isInseribile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale);

	public abstract boolean isCancellabile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale);

	public abstract EsitoControlloStatiSchede isNotFlussoInRichiesta(
			SituazioneSchedeAttuale situazioneAttuale,
			IdentificativoSchede identificativo, String cig, String cui);

	public abstract boolean isModificabile(IdentificativoSchede identificativo,
			SituazioneSchedeAttuale situazioneAttuale);
	
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
	public abstract EsitoControlloStatiSchede isNotFlussoInDefinizione(SituazioneSchedeAttuale situazioneAttuale);

	/**
	 * Controlla che la scheda (lista di Stati schede afferenti alla stessa
	 * tipologia di scheda) non esista o siano tutte confermate.
	 * 
	 * @param statiSchedaAttuale
	 * @return
	 */
	protected boolean nonEsisteOConfermata(
			ArrayList<StatoScheda> statiSchedaAttuale) {
		boolean isEsistente = false;
		boolean isConfermata = false;
		if (statiSchedaAttuale != null && statiSchedaAttuale.size() > 0) {
			isEsistente = true;
			isConfermata = true;
			for (StatoScheda statoSchedaCorrente : statiSchedaAttuale) {
				isConfermata = isConfermata
						|| statoSchedaCorrente.isAsConfermata();
			}
		}
		return !isEsistente || (isEsistente && isConfermata);
	}

	/**
	 * Controlla che la scheda sia esistente, in particolare trattandosi di una
	 * scheda multipla che puo avere uno o piu stati scheda controllo la non
	 * nullita' della lista di stati e che la sua dimensione sia maggiore di
	 * zero.
	 * 
	 * @param listaDiStati
	 * @return
	 */
	protected boolean isEsistente(ArrayList<StatoScheda> listaDiStati) {
		if (listaDiStati != null) {
			for (StatoScheda statoCorrente : listaDiStati) {
				if (statoCorrente.isEsistenteDb())
					return true;
			}
		}
		return false;
	}

	/**
	 * Controlla che la scheda passata non sia in definizione ed in particolare
	 * sia confermata o come confermata (XML dati corretti) su una lista di
	 * stati
	 * 
	 * @param statoSchede
	 * @return
	 */
	protected boolean schedaEsistenteEConfermata(
			ArrayList<StatoScheda> statoSchede) {
		if (statoSchede != null && statoSchede.size() > 0) {
			for (StatoScheda statoSchedaCorrente : statoSchede) {
				if (!schedaEsistenteEConfermata(statoSchedaCorrente))
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Controlla che la scheda passata non sia in definizione ed in particolare
	 * sia confermata o come confermata (XML dati corretti)
	 * 
	 * @param statoSchedaPadre
	 * @return
	 */
	protected boolean schedaEsistenteEConfermata(StatoScheda statoSchedaPadre) {
		return !statoSchedaPadre.isInDefinizione()
				&& controlloSchedaConfermata(statoSchedaPadre);
	}

	/**
	 * Merge tra condizioni massloader e web per il massloader asConfermata, e'
	 * come se la scheda esistesse e fosse confermata (come condizione per
	 * verifica padre solamente)
	 * 
	 * @param statoScheda
	 * @return
	 */
	protected boolean controlloSchedaConfermata(StatoScheda statoScheda) {
		return statoScheda.isConfermata() || statoScheda.isAsConfermata();
	}

	/**
	 * Aggiunge un validation bean (SchedaSpecificaValidationBean) alla lista di
	 * classe
	 * 
	 * @param statoSchedaCorrente
	 *            : deve essere la scheda che non permette l'inserimento
	 * @param nomeScheda
	 * @param messaggio
	 */
	protected void addError(StatoScheda statoSchedaCorrente, String nomeScheda,
			String messaggio) {
		this.esitiOperazioni.add(SchedaSpecificaValidationBean
				.getThisKindOfValidationBeanErr(statoSchedaCorrente, 0,
						progressivoSchedaCompleta, 0, nomeScheda, messaggio));
	}

	/**
	 * A differenza dell'altro non prende gli id dallo stato corrente, utile per
	 * le schede multiple delle quali altrimenti occorrerebbe risalire alla
	 * scheda specifica in errore.
	 * 
	 * 22-01-2010: a questo metodo passo anche lo stato che non gli appartiene
	 * (diciamo aggiudicazione) cosi da non rischiare di incorrere in una lista
	 * vuota e nullpointer..
	 * 
	 * @param statoSchedaCorrente
	 *            : deve essere dati comuni o aggiudicazione !
	 * @param nomeScheda
	 * @param messaggio
	 */
	protected void addErrorForMulti(ArrayList<StatoScheda> listaStatiScheda,
			String nomeScheda, String messaggio) {
		if (listaStatiScheda != null && !listaStatiScheda.isEmpty()) {
			for (StatoScheda statoSchedaCorrente : listaStatiScheda) {
				this.addError(statoSchedaCorrente, nomeScheda, messaggio);
			}
		}
		// 22-01-2010: correzione prende una lista di stati
		// this.esitiOperazioni.add(
		// SchedaSpecificaValidationBean.getThisKindOfValidationBeanErrMulti(
		// statoSchedaCorrente, 0, progressivoSchedaCompleta, 0,
		// nomeScheda,messaggio)
		// );
	}

	/**
	 * Costruisce un oggetto di errore con messaggio 178
	 * 
	 * @param aggiungiErrore
	 * @param schedaErrore
	 * @param schedaCausa
	 * @param statoSchedaCorrente
	 */
	protected void addErrore178SeNecessario(boolean aggiungiErrore,
			String schedaErrore, String schedaCausa,
			StatoScheda statoSchedaCorrente) {
		if (aggiungiErrore) {
			String messaggio = Messaggi.SIMOG_MASSLOADER_178
					.replace("$1", schedaErrore).replace("$2", schedaCausa)
					.replace("$3", "Cancellabile");
			this.addError(statoSchedaCorrente, schedaErrore, messaggio);
		}
	}

	/**
	 * Costruisce un oggetto di errore con messaggio 178 prendendo il primo
	 * stato contenuto nella lista degli stati
	 * 
	 * @param aggiungiErrore
	 * @param schedaErrore
	 * @param schedaCausa
	 * @param statiSchedaCorrente
	 */
	protected void addErrore178SeNecessario(boolean aggiungiErrore,
			String schedaErrore, String schedaCausa,
			ArrayList<StatoScheda> statiSchedaCorrente) {
		if (aggiungiErrore) {
			String messaggio = Messaggi.SIMOG_MASSLOADER_178
					.replace("$1", schedaErrore).replace("$2", schedaCausa)
					.replace("$3", "Cancellabile");
			StatoScheda statoSchedaCorrente = new StatoScheda();
			if (statiSchedaCorrente != null && !statiSchedaCorrente.isEmpty()) {
				statoSchedaCorrente = statiSchedaCorrente.get(0);
			}
			this.addError(statoSchedaCorrente, schedaErrore, messaggio);
		}
	}

}
