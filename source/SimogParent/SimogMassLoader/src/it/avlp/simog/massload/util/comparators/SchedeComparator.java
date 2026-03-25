package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType;

import java.util.Comparator;

/**
 * Classe nata dalla necessita- di ordinare le schede da 
 * eliminare in "ordine inverso" ovverosia per metafora
 * dalle foglie verso la radice.
 * @author vletizia
 *
 */

public class SchedeComparator implements Comparator<RecIdSchedaElimType>{

	public int compare(RecIdSchedaElimType o1, RecIdSchedaElimType o2) {
		
		if(!o2.getCIG().equals(o1.getCIG())){
			
			return o1.getCIG().compareTo(o2.getCIG());
		}
		else{
			
			if(o2.getCUI().equals(o1.getCUI())){
				
				if(!o1.isSetSCHEDA() && o2.isSetSCHEDA()) return 1;
				
				if(!o2.isSetSCHEDA() && o1.isSetSCHEDA() ) return -1;
				
				if(o2.isSetSCHEDA() && o1.isSetSCHEDA()){
					if(o2.getCIG().equals(o1.getCIG()) && o2.getCUI().equals(o1.getCUI())){
						return comparaStringhe(o1.getSCHEDA().toString(), o2.getSCHEDA().toString());
						
					}
				}
			}
			else{
				// se il cui e' vuoto deve essere messo dopo
				if(o1.getCUI() == null || (o1.getCUI() != null && o1.getCUI().equals(""))){
					return 1;
				}else{
				// altrimenti confronta
					return o1.getCUI().compareTo(o2.getCUI());
				}
			}

		}		
		return 0;
	}
	
	private int comparaStringhe(String uno, String due){
		
		if(uno.equals(due)) return 0;
		
		if(IdentificativoSchede.DATI_COMUNI.equals(uno)) return this.comparaDatiComuni(due);
		if(IdentificativoSchede.AGGIUDICAZIONE.equals(uno)) return this.comparaAggiudicazione(due);
		if(IdentificativoSchede.SOTTOSOGLIA.equals(uno)) return this.comparaAggiudicazione(due);
		if(IdentificativoSchede.ESCLUSO.equals(uno)) return this.comparaAggiudicazione(due);
		if(IdentificativoSchede.FASE_INIZIALE.equals(uno)) return this.comparaInizioLavori(due);
		if(IdentificativoSchede.STIPULA.equals(uno)) return this.comparaStipula(due);
		if(IdentificativoSchede.STATO_AVANZAMENTO.equals(uno)) return this.comparaAvanzamento(due);
		if(IdentificativoSchede.FINE_LAVORI.equals(uno)) return this.comparaConclusione(due);
		if(IdentificativoSchede.COLLAUDO.equals(uno)) return this.comparaCollaudo(due);
		if(IdentificativoSchede.IPOTESI_RECESSO.equals(uno)) return this.comparaRecesso(due);
		if(IdentificativoSchede.ACCORDO_BONARIO.equals(uno)) return this.comparaAccordo(due);
		if(IdentificativoSchede.SOSPENSIONE.equals(uno)) return this.comparaSospesione(due);
		if(IdentificativoSchede.VARIANTE.equals(uno)) return this.comparaVariante(due);
		if(IdentificativoSchede.SUBAPPALTO.equals(uno)) return this.comparaSubAppalto(due);
		
		return 0;
	}

	/**
	 * Deve essere sempre messo in fondo
	 * 
	 * @param due
	 * @return
	 */
	private int comparaDatiComuni(String due){
		return 1;
	}
	/**
	 * @param due
	 * @return
	 */
	private int comparaAggiudicazione(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due)) return -1;
		return 1;
	}
	private int comparaInizioLavori(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)
		|| IdentificativoSchede.IPOTESI_RECESSO.equals(due) || IdentificativoSchede.VARIANTE.equals(due) 
		|| IdentificativoSchede.SUBAPPALTO.equals(due)) return -1;
		return 1;		
	}
	
	private int comparaStipula(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)
		|| IdentificativoSchede.IPOTESI_RECESSO.equals(due) || IdentificativoSchede.VARIANTE.equals(due) 
		|| IdentificativoSchede.SUBAPPALTO.equals(due)) return -1;
		return 1;		
	}
	private int comparaAvanzamento(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)
		|| IdentificativoSchede.FASE_INIZIALE.equals(due)|| IdentificativoSchede.IPOTESI_RECESSO.equals(due) 
		|| IdentificativoSchede.VARIANTE.equals(due) || IdentificativoSchede.SUBAPPALTO.equals(due)
		|| IdentificativoSchede.SOSPENSIONE.equals(due) || IdentificativoSchede.ACCORDO_BONARIO.equals(due)) return -1;
		return 1;		
	}
	private int comparaConclusione(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)
		|| IdentificativoSchede.FASE_INIZIALE.equals(due) || IdentificativoSchede.STATO_AVANZAMENTO.equals(due)
		|| IdentificativoSchede.IPOTESI_RECESSO.equals(due) || IdentificativoSchede.VARIANTE.equals(due) 
		|| IdentificativoSchede.SUBAPPALTO.equals(due) || IdentificativoSchede.SOSPENSIONE.equals(due) 
		|| IdentificativoSchede.ACCORDO_BONARIO.equals(due)) return -1;
		return 1;		
	}
	private int comparaCollaudo(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)
		|| IdentificativoSchede.FASE_INIZIALE.equals(due) || IdentificativoSchede.STATO_AVANZAMENTO.equals(due)
		|| IdentificativoSchede.FINE_LAVORI.equals(due)|| IdentificativoSchede.IPOTESI_RECESSO.equals(due) 
		|| IdentificativoSchede.VARIANTE.equals(due) || IdentificativoSchede.SUBAPPALTO.equals(due)
		|| IdentificativoSchede.SOSPENSIONE.equals(due) || IdentificativoSchede.ACCORDO_BONARIO.equals(due)) return -1;
		return 1;				
	}
	
	private int comparaRecesso(String due){
		if(IdentificativoSchede.VARIANTE.equals(due) || IdentificativoSchede.SUBAPPALTO.equals(due)) return 0;
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)) return -1;
		return 1;
	}
	private int comparaSubAppalto(String due){
		if(IdentificativoSchede.IPOTESI_RECESSO.equals(due) || IdentificativoSchede.VARIANTE.equals(due)) return 0;
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)) return -1;
		return 1;
	}
	private int comparaVariante(String due){
		if(IdentificativoSchede.IPOTESI_RECESSO.equals(due) || IdentificativoSchede.SUBAPPALTO.equals(due)) return 0;
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)) return -1;
		return 1;
	}
	
	private int comparaAccordo(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)|| IdentificativoSchede.FASE_INIZIALE.equals(due)) return -1;
		if(IdentificativoSchede.SOSPENSIONE.equals(due)) return 0;
		return 1;
		
	}
	private int comparaSospesione(String due){
		if(IdentificativoSchede.DATI_COMUNI.equals(due) || IdentificativoSchede.AGGIUDICAZIONE.equals(due)|| IdentificativoSchede.FASE_INIZIALE.equals(due)) return -1;
		if(IdentificativoSchede.ACCORDO_BONARIO.equals(due)) return 0;
		return 1;
		
	}
	
}
