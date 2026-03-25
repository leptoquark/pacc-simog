package it.avlp.simog.flusso;

import org.apache.log4j.Logger;

import it.avlp.simog.validatore.TipoFlusso;

public class WorkFlowFactory {
	
	/**Metodo per ottenere il workflow in base al tipo di flusso
	 * @param tipo tipo di flusso
	 * @return {@link _WorkFlow} il controller del flusso
	 */
	public static _WorkFlow getWorkflow(TipoFlusso tipo){
		
		switch(tipo){
		case AGGIUDICAZIONE:
			return new WorkFlowAggiudicazione();
		
		case STIPULA:
			return new WorkFlowStipula();
		
		case ADESIONE:
			return new WorkFlowAdesione();
		
		case SOTTOSOGLIA:
			return new WorkFlowSottoEscl(false);
			
		case ESCLUSO:
			return new WorkFlowSottoEscl(true);
		default:
			throw new RuntimeException("No such workflow");
			
		}
	}

}
