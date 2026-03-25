package it.avlp.simog.flusso;

import it.avlp.simog.flusso.interfaccie.IOperazioniSchedaMassloader;
import it.avlp.simog.flusso.interfaccie.IOperazioniSchedaWeb;

/**
 * @author vletizia
 *
 */
public class OperazioneScheda implements IOperazioniSchedaMassloader, IOperazioniSchedaWeb {

	private String operazioneCorrente;
	private int indiceOperazione;
	
	private OperazioneScheda(String OPERAZIONE, int INDICE_OPERAZIONE){
		this.operazioneCorrente = OPERAZIONE;
		this.indiceOperazione = INDICE_OPERAZIONE;
	}
	public String getNomeOperazione(){
		return this.operazioneCorrente;
	}
	public int getIndiceOperazione(){
		return this.indiceOperazione;
	}
	
	public static OperazioneScheda getInserimento(){
		return new OperazioneScheda(IOperazioniSchedaMassloader.INSERIMENTO,IOperazioniSchedaMassloader.INDICE_INSERIMENTO);
	}
	
	public static OperazioneScheda getModifica(){
		return new OperazioneScheda(IOperazioniSchedaMassloader.MODIFICA,IOperazioniSchedaMassloader.INDICE_MODIFICA);
	}
	
	public static OperazioneScheda getCancellazione(){
		return  new OperazioneScheda(IOperazioniSchedaMassloader.ELIMINAZIONE,IOperazioniSchedaMassloader.INDICE_ELIMINAZIONE);
	}
	
	public static OperazioneScheda getCreazione(){
		return new OperazioneScheda(IOperazioniSchedaWeb.CREAZIONE,IOperazioniSchedaWeb.INDICE_CREAZIONE);
	}
	
	public static OperazioneScheda getConferma(){
		return new OperazioneScheda(IOperazioniSchedaWeb.CONFERMA,IOperazioniSchedaWeb.INDICE_CONFERMA);
	}
	
	public static OperazioneScheda getPresaInCarico(){
		return  new OperazioneScheda(IOperazioniSchedaWeb.PRESA_IN_CARICO,IOperazioniSchedaWeb.INDICE_PRESA_IN_CARICO);
	}
	
	public static OperazioneScheda getRichiestaAnnullamento(){
		return  new OperazioneScheda(IOperazioniSchedaWeb.RICHIESTA_ANNULLAMENTO,IOperazioniSchedaWeb.INDICE_RICHIESTA_ANNULLAMENTO);
	}

	public static OperazioneScheda getVariazioneAnag(){
		return  new OperazioneScheda(IOperazioniSchedaWeb.VARIAZIONE_ANAG,IOperazioniSchedaWeb.INDICE_VARIAZIONE_ANAG);
	}
    public static OperazioneScheda getVariazioneSA(){
       return  new OperazioneScheda(IOperazioniSchedaWeb.VARIAZIONE_SA,IOperazioniSchedaWeb.INDICE_VARIAZIONE_SA);
   }
}
