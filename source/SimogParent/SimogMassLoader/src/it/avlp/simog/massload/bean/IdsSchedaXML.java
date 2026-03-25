/**
 * 
 */
package it.avlp.simog.massload.bean;

import it.avlp.simog.beans.IdsScheda;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.flusso.bean.CrossedFields;
import it.avlp.simog.massload.util.conversion.SituazioneAttualeSchedeXml;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;

import java.util.ArrayList;

/**
 * Entita' al livello del cig..
 * 
 * @author vletizia
 *
 */
public class IdsSchedaXML extends IdsScheda implements Cloneable{

	private DatiAggiudicazioneType scheda;
	private boolean isPresentSomeSchedaCompleta;
	private SituazioneSchedeAttuale situazioneAttuale;
	private SituazioneAttualeSchedeXml situazioneAttualeXml;
	private int cardinalitaSchedaCompleta;
	private int cardinalitaSchedaCig;
	private boolean needCancellazione;
	
	// questi array hanno lo stesso ordinameto dei relativi array nel tipo xml il valore conterra' la posizione originale
	// nel caso degli "ibridi", ovvero sia le schede multiple che contengono delle schede da modificare e da inserire
	// altrimenti saranno null..
	private int[] avanzamentiPosizioneModifica;
	private int[] accordiPosizioneModifica;
	private int[] ritardiPosizioneModifica;
	private int[] sospensioniPosizioneModifica;
	private int[] subappaltiPosizioneModifica;
	private int[] variantiPosizioneModifica;
	
	private int[] avanzamentiPosizioneInserimento;
	private int[] accordiPosizioneInserimento;
	private int[] ritardiPosizioneInserimento;
	private int[] sospensioniPosizioneInserimento;
	private int[] subappaltiPosizioneInserimento;
	private int[] variantiPosizioneInserimento;
	
	private CrossedFields crossFieldsForInfoComuniValidation;
	/**
	 * 
	 */
	public IdsSchedaXML() {
		super();
	}
	
	public DatiAggiudicazioneType getScheda() {
		return scheda;
	}

	public void setScheda(DatiAggiudicazioneType scheda) {
		this.scheda = scheda;
	}

	public boolean isPresentSomeSchedaCompleta() {
		return isPresentSomeSchedaCompleta;
	}

	public void setPresentSomeSchedaCompleta(boolean isPresentSomeCui) {
		this.isPresentSomeSchedaCompleta = isPresentSomeCui;
	}
	
	public SituazioneSchedeAttuale getSituazioneAttuale() {
		return situazioneAttuale;
	}

	public void setSituazioneAttuale(SituazioneSchedeAttuale situazioneAttuale) {
		this.situazioneAttuale = situazioneAttuale;
	}

	public int getCardinalitaSchedaCompleta() {
		return cardinalitaSchedaCompleta;
	}

	public void setCardinalitaSchedaCompleta(int cardinalitaSchedaCompleta) {
		this.cardinalitaSchedaCompleta = cardinalitaSchedaCompleta;
	}

	public int getCardinalitaSchedaCig() {
		return cardinalitaSchedaCig;
	}

	public void setCardinalitaSchedaCig(int cardinalitaSchedaCig) {
		this.cardinalitaSchedaCig = cardinalitaSchedaCig;
	}

	public SituazioneAttualeSchedeXml getSituazioneAttualeXml() {
		return situazioneAttualeXml;
	}

	public void setSituazioneAttualeXml(
			SituazioneAttualeSchedeXml situazioneAttualeXml) {
		this.situazioneAttualeXml = situazioneAttualeXml;
	}


	public int[] getAvanzamentiPosizioneModifica() {
		return avanzamentiPosizioneModifica;
	}

	public void setAvanzamentiPosizioneModifica(int[] avanzamentiPosizioneModifica) {
		this.avanzamentiPosizioneModifica = avanzamentiPosizioneModifica;
	}

	public int[] getAccordiPosizioneModifica() {
		return accordiPosizioneModifica;
	}

	public void setAccordiPosizioneModifica(int[] accordiPosizioneModifica) {
		this.accordiPosizioneModifica = accordiPosizioneModifica;
	}

	public int[] getRitardiPosizioneModifica() {
		return ritardiPosizioneModifica;
	}

	public void setRitardiPosizioneModifica(int[] ritardiPosizioneModifica) {
		this.ritardiPosizioneModifica = ritardiPosizioneModifica;
	}

	public int[] getSospensioniPosizioneModifica() {
		return sospensioniPosizioneModifica;
	}

	public void setSospensioniPosizioneModifica(int[] sospensioniPosizioneModifica) {
		this.sospensioniPosizioneModifica = sospensioniPosizioneModifica;
	}

	public int[] getSubappaltiPosizioneModifica() {
		return subappaltiPosizioneModifica;
	}

	public void setSubappaltiPosizioneModifica(int[] subappaltiPosizioneModifica) {
		this.subappaltiPosizioneModifica = subappaltiPosizioneModifica;
	}

	public int[] getVariantiPosizioneModifica() {
		return variantiPosizioneModifica;
	}

	public void setVariantiPosizioneModifica(int[] variantiPosizioneModifica) {
		this.variantiPosizioneModifica = variantiPosizioneModifica;
	}

	public int[] getAvanzamentiPosizioneInserimento() {
		return avanzamentiPosizioneInserimento;
	}

	public void setAvanzamentiPosizioneInserimento(
			int[] avanzamentiPosizioneInserimento) {
		this.avanzamentiPosizioneInserimento = avanzamentiPosizioneInserimento;
	}

	public int[] getAccordiPosizioneInserimento() {
		return accordiPosizioneInserimento;
	}

	public void setAccordiPosizioneInserimento(int[] accordiPosizioneInserimento) {
		this.accordiPosizioneInserimento = accordiPosizioneInserimento;
	}

	public int[] getRitardiPosizioneInserimento() {
		return ritardiPosizioneInserimento;
	}

	public void setRitardiPosizioneInserimento(int[] ritardiPosizioneInserimento) {
		this.ritardiPosizioneInserimento = ritardiPosizioneInserimento;
	}

	public int[] getSospensioniPosizioneInserimento() {
		return sospensioniPosizioneInserimento;
	}

	public void setSospensioniPosizioneInserimento(
			int[] sospensioniPosizioneInserimento) {
		this.sospensioniPosizioneInserimento = sospensioniPosizioneInserimento;
	}

	public int[] getSubappaltiPosizioneInserimento() {
		return subappaltiPosizioneInserimento;
	}

	public void setSubappaltiPosizioneInserimento(
			int[] subappaltiPosizioneInserimento) {
		this.subappaltiPosizioneInserimento = subappaltiPosizioneInserimento;
	}

	public int[] getVariantiPosizioneInserimento() {
		return variantiPosizioneInserimento;
	}

	public void setVariantiPosizioneInserimento(int[] variantiPosizioneInserimento) {
		this.variantiPosizioneInserimento = variantiPosizioneInserimento;
	}

	/**
	 * metodo util per convertire un array list di Integer ad una array di int
	 * @param posizioni
	 * @return
	 */
	public int[] convertiPosizioneArray(ArrayList<Integer> posizioni){
		int[] arrayGenerico = new int[posizioni.size()];
		int i = 0;
		for(Integer intCorrente : posizioni){
			arrayGenerico[i] = intCorrente.intValue();
			i++;
		}return arrayGenerico;
	}
	/**
	 * Metodo per evitare l'ereditarieta' restituisce un'altra istanza valorizzata allo stesso modo della corrente.
	 * 
	 * - rimangono vincolati:
	 * 
	 * 		* situazioneAttuale
	 * 		* situazioneAttualeXml
	 * 
	 * 
	 * 
	 * @see java.lang.Object#clone()
	 */
	public IdsSchedaXML clone() throws CloneNotSupportedException {
		IdsSchedaXML idScheda = (IdsSchedaXML)super.clone();
		idScheda.cardinalitaSchedaCig = this.cardinalitaSchedaCig;
		idScheda.cardinalitaSchedaCompleta = this.cardinalitaSchedaCompleta;
		idScheda.isPresentSomeSchedaCompleta = this.isPresentSomeSchedaCompleta;
		idScheda.scheda = this.scheda;
		idScheda.situazioneAttuale = this.situazioneAttuale;
		idScheda.situazioneAttualeXml = this.situazioneAttualeXml;
		idScheda.setCig(super.getCig());
		idScheda.setCui(super.getCui());
		idScheda.setDataInizioRecord(super.getDataInizioRecord());
		idScheda.setIdaggiudicazione(super.getIdaggiudicazione());
		idScheda.setIdentificativo(super.getIdentificativo());
		idScheda.setIdLocale(super.getIdLocale());
		idScheda.setIdRecordDb(super.getIdRecordDb());
		idScheda.setIdScheda(super.getIdScheda());
		idScheda.setCrossFieldsForInfoComuniValidation(this.crossFieldsForInfoComuniValidation);

		return idScheda;
	}

	public boolean isNeedCancellazione() {
		return needCancellazione;
	}

	public void setNeedCancellazione(boolean needCancellazione) {
		this.needCancellazione = needCancellazione;
	}

	public CrossedFields getCrossFieldsForInfoComuniValidation() {
		return crossFieldsForInfoComuniValidation;
	}

	public void setCrossFieldsForInfoComuniValidation(
			CrossedFields crossFieldsForInfoComuniValidation) {
		this.crossFieldsForInfoComuniValidation = crossFieldsForInfoComuniValidation;
	}
	

}
