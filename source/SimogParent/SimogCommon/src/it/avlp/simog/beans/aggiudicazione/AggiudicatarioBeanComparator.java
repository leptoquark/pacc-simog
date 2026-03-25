package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.db.Costanti;

import java.util.Comparator;

public class AggiudicatarioBeanComparator implements Comparator<AggiudicatarioBean>{

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	/*
	public int compare(AggiudicatarioBean o1, AggiudicatarioBean o2) {
		int i = 1;
		if(this.compare(o1.getSoggettoPartecipante(), o2.getSoggettoPartecipante(), i) == 0){
			i = 2;
			if(this.compare(o1.getSoggettoPartecipante(), o2.getSoggettoPartecipante(), 2) == 0){
				return 0;
			}else{
				return this.compare(o1.getSoggettoPartecipante(), o2.getSoggettoPartecipante(), i);
			}
		}else{
			return this.compare(o1.getSoggettoPartecipante(), o2.getSoggettoPartecipante(), i);
		}
	}
	*/
	
	public int compare(AggiudicatarioBean agg1, AggiudicatarioBean agg2) {
		
		if(agg1.getSoggettoPartecipante().getCodiceFiscale().compareTo(agg2.getSoggettoPartecipante().getCodiceFiscale())==0){
			String idStato1 = (agg1.getSoggettoPartecipante().getId_stato()==null || "".equals(agg1.getSoggettoPartecipante().getId_stato())) ? Costanti.CODICE_STATO_ITALIANO : agg1.getSoggettoPartecipante().getId_stato();
			String idStato2 = (agg2.getSoggettoPartecipante().getId_stato()==null || "".equals(agg2.getSoggettoPartecipante().getId_stato())) ? Costanti.CODICE_STATO_ITALIANO : agg2.getSoggettoPartecipante().getId_stato();
			return idStato1.compareTo(idStato2);
		}
		else
			return agg1.getSoggettoPartecipante().getCodiceFiscale().compareTo(agg2.getSoggettoPartecipante().getCodiceFiscale());
	}
	
	
	/**
	 * il compare lo effettuo sul nested bean in quanto non risulta possibile al momento
	 * ottenere un valore univoco per aggiudicatari distinti
	 * 
	 * @param o1 SoggettoPartecipanteBean
	 * @param o2 SoggettoPartecipanteBean
	 * @param i int
	 * @return int seguendo le regole del comparator
	 */ 
	private int compare(SoggettoPartecipanteBean o1, SoggettoPartecipanteBean o2,int i){
		switch (i) {
		case 1:return o1.getCodiceFiscale().compareTo(o2.getCodiceFiscale());
		case 2:return compareIdStato(o1.getId_stato(), o2.getId_stato());
		default:return 0;
		}
	}
	
	/**
	 * Il confronto tiene conto del fatto che id_stato = null se il soggetto è italiano
	 * ed vita il null pointer exception
	 * @param id1
	 * @param id2
	 * @return
	 */
	private int compareIdStato(String id1, String id2){
		if(id1 == null) id1 = Costanti.CODICE_STATO_ITALIANO;
		if(id2 == null) id2 = Costanti.CODICE_STATO_ITALIANO;
		return id1.compareTo(id2);
	}
}
