package it.avlp.simog.beans;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;

import java.sql.Timestamp;

public class ResponsabileBean implements VO{

	// responsabile
	
	private long 		idScheda;
	private Timestamp 	dataInizioScheda;
	private int 		idRuolo;
	private String 		descrizioneRuolo;
	private int 		idStato;
	private SoggettoResponsabileBean soggettoResponsabile;
	private SoggettoPartecipanteBean soggettoPartecipante;
	
	
	/* Prestazioni esterne  */
	
	private String 		sezione;
	private String 		cigProgEsterna;
	private String 		dataAffProgEsterna;
	private String 		dataConsProgEsterna;
	
	/* campi raggruppamento */
	private long idGruppo;
	private boolean mandante;
	private String ditteRaggruppamentoString;
	
	public long getIdScheda() {
		return idScheda;
	}

	public void setIdScheda(long idAggiudicazione) {
		this.idScheda = idAggiudicazione;
	}

	public Timestamp getDataInizioScheda() {
		return dataInizioScheda;
	}

	public void setDataInizioScheda(Timestamp dataInizioAggiudicazione) {
		this.dataInizioScheda = dataInizioAggiudicazione;
	}

	public int getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getCigProgEsterna() {
		return cigProgEsterna;
	}

	public void setCigProgEsterna(String cigProgEsterna) {
		this.cigProgEsterna = cigProgEsterna;
	}

	public String getDataAffProgEsterna() {
		return dataAffProgEsterna;
	}

	public void setDataAffProgEsterna(String dataAffProgEsterna) {
		this.dataAffProgEsterna = dataAffProgEsterna;
	}

	public String getDataConsProgEsterna() {
		return dataConsProgEsterna;
	}

	public void setDataConsProgEsterna(String dataConsProgEsterna) {
		this.dataConsProgEsterna = dataConsProgEsterna;
	}

	public SoggettoResponsabileBean getSoggettoResponsabile() {
		return soggettoResponsabile;
	}

	public void setSoggettoResponsabile(
			SoggettoResponsabileBean soggettoResponsabile) {
		this.soggettoResponsabile = soggettoResponsabile;
	}
	
	public SoggettoPartecipanteBean getSoggettoPartecipante() {
		return soggettoPartecipante;
	}

	public void setSoggettoPartecipante(SoggettoPartecipanteBean soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}

	public long getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(long idGruppo) {
		this.idGruppo = idGruppo;
	}

	public boolean isMandante() {
		return mandante;
	}

	public void setMandante(boolean mandante) {
		this.mandante = mandante;
	}
	
	public void setDitteRaggruppamentoString (String ditteRaggruppamentoString){
		this.ditteRaggruppamentoString = ditteRaggruppamentoString;
	}
	public String getDitteRaggruppamentoString(){
		return ditteRaggruppamentoString;
	}
}
