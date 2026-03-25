package it.avlp.simog.massload.util.multichecker;

import it.avlp.simog.massload.util.comparators.MultiComparator;

import java.util.ArrayList;

public class MultiAdder {

	private MultiComparator comparator;
	private ArrayList<Object> senzaDuplicati;
	private int posizionePrimoDuplicato;
	
	/**
	 * @param comparator - istanza del comparatore atto al tipo
	 * @param length - lunghezza max array
	 */
	public MultiAdder(MultiComparator comparator,int length){
		this.comparator = comparator;
		senzaDuplicati = new ArrayList<Object>();
	}
	/**
	 * Controlla se esistono dei duplicati
	 * 
	 * @param os
	 * @return
	 */
	public boolean containsDuplicate(Object[] os){
		boolean esito = false;
		boolean temp = true;
		for(int i = 0;i<os.length;i++){
			temp = this.isAlreadyAdded(os[i]);
			//se la sub funzione non ha mai trovato duplicati e adesso si
			//setta esito a true
			if(!esito && temp){	
				esito = true;
				this.posizionePrimoDuplicato = i+1;
			}
		//se non entra mai nell'if ritorna false
		}
		senzaDuplicati.trimToSize();;
		return esito;	
	}
	
	/**
	 * se l' oggetto non e' uguale a nessuno nell'array locale
	 *  lo aggiunge all'array locale.
	 * 
	 * @param o
	 * @return
	 */
	private boolean isAlreadyAdded(Object o){
		boolean esito = false;
		//se l'array locale (senza duplicati) e' vuoto aggiugi
		if(this.senzaDuplicati.size() == 0){
			this.senzaDuplicati.add(o);
		//altrimenti itera sull'array locale per vedere se sono uguali
		}else{
			for(Object local : this.senzaDuplicati){

				try {
					if(comparator.equals(local, o)){
						esito = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			//se non ne e' stato trovato nessuno uguale aggiungi all'array locale
			if(!esito){
				this.senzaDuplicati.add(o);
			}
		}return esito;
	}

	public int getSize(){
		return senzaDuplicati.size();
	}
	/**
	 * @return the posizionePrimoDuplicato
	 */
	public int getPosizionePrimoDuplicato() {
		return posizionePrimoDuplicato;
	}
	public void setSenzaDuplicati(Object[] array,Class<?> classe){
		Object[] o = this.senzaDuplicati.toArray();
		for(int i = 0; i <o.length; i++){
			array[i] = classe.cast(o[i]);
		}
	}
}
