package it.avlp.simog.massload.bean;

import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.util.PageHelper;

/**
 * Classe Wrapper per Pubblicazione 
 * offre funzione tipo trasformazione da beanXML a relativi bean
 * usati anche nella parte web del progetto.
 * 
 * l'inserimento e la validazione di pubblicazione viene fatto con la schedaA 
 * (aggiudicazione), poiche e' il modo piu' pulito e comunque il validatore
 * prende come oggetto tutta l' ex schedaA ceh necessita dei bean qui presenti
 * 
 **/

public class Pubblicazione {
//come dati comuni
	private PubblicazioneBean pubblicazione;
	
	public Pubblicazione(){
		this.pubblicazione = new PubblicazioneBean();
		
	}
	
	public PubblicazioneBean getPubblicazione() {
		return pubblicazione;
	}
	public void setPubblicazione(PubblicazioneBean pubblicazione) {
		this.pubblicazione = pubblicazione;
	}
	public void add(PubblicazioneType pubblicazione){
//		StuffDispatcher.getInstance().getLogger().debug("eseguendo: [void add(PubblicazioneType pubblicazione)]");
		this.pubblicazione.setDataAlbo(PageHelper.getFormattedCalendarDate(pubblicazione.getDATAALBO()));
		//StuffDispatcher.getInstance().getLogger().debug("[DataAlbo]: "+"unformatted-> "+pubblicazione.getDATAALBO()+" , formatted-> "+PageHelper.getFormattedCalendarDate(pubblicazione.getDATAALBO()));
		this.pubblicazione.setDataGuce(PageHelper.getFormattedCalendarDate(pubblicazione.getDATAGUCE()));
		//StuffDispatcher.getInstance().getLogger().debug("[DataGUCE]: "+"unformatted-> "+pubblicazione.getDATAALBO()+" , formatted-> "+PageHelper.getFormattedCalendarDate(pubblicazione.getDATAGUCE()));
		this.pubblicazione.setDataGuri(PageHelper.getFormattedCalendarDate(pubblicazione.getDATAGURI()));
		//StuffDispatcher.getInstance().getLogger().debug("[DataGURI]: "+"unformatted-> "+pubblicazione.getDATAALBO()+" , formatted-> "+PageHelper.getFormattedCalendarDate(pubblicazione.getDATAGURI()));
		//this.pubblicazione.setDataInizioPubblicazione(dataInizioPubblicazione);
		//this.pubblicazione.setIdPubblicazione(idPubblicazione);
		this.pubblicazione.setProfiloCommitente(pubblicazione.getPROFILOCOMMITTENTE().toString());
		this.pubblicazione.setQuotidianiNaz(pubblicazione.getQUOTIDIANINAZ());
		this.pubblicazione.setQuotidianiReg(pubblicazione.getQUOTIDIANIREG());
		this.pubblicazione.setSitoMinisteroInfTrasp(pubblicazione.getSITOMINISTEROINFTRASP().toString());
		this.pubblicazione.setSitoOsservatorioCP(pubblicazione.getSITOOSSERVATORIOCP().toString());
		
		this.pubblicazione.setDataBore(PageHelper.getFormattedCalendarDate(pubblicazione.getDATABORE()));
		this.pubblicazione.setPeriodici(pubblicazione.getPERIODICI());
	}

}
