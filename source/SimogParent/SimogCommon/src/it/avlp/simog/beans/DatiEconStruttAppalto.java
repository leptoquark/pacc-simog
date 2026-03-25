package it.avlp.simog.beans;

@Deprecated
public class DatiEconStruttAppalto {

	
	public String importoComplessivo    = null;
    public String procSceltaContraente  = null;
	public String condGiustifRicorso    = null;
	public String criteriAggiudicazione = null;

	public DatiEconStruttAppalto(String importoComplessivo
								,String procSceltaContraente
								,String condGiustifRicorso
								,String criteriAggiudicazione) {
		this.importoComplessivo        = importoComplessivo	   ;
		this.procSceltaContraente      = procSceltaContraente  ;
		this.condGiustifRicorso        = condGiustifRicorso    ;
		this.criteriAggiudicazione     = criteriAggiudicazione ;
	}

	public DatiEconStruttAppalto() {
	}

	public String getCondGiustifRicorso() {
		return condGiustifRicorso;
	}

	public void setCondGiustifRicorso(String condGiustifRicorso) {
		this.condGiustifRicorso = condGiustifRicorso;
	}

	public String getCriteriAggiudicazione() {
		return criteriAggiudicazione;
	}

	public void setCriteriAggiudicazione(String criteriAggiudicazione) {
		this.criteriAggiudicazione = criteriAggiudicazione;
	}

	public String getImportoComplessivo() {
		return importoComplessivo;
	}

	public void setImportoComplessivo(String importoComplessivo) {
		this.importoComplessivo = importoComplessivo;
	}

	public String getProcSceltaContraente() {
		return procSceltaContraente;
	}

	public void setProcSceltaContraente(String procSceltaContraente) {
		this.procSceltaContraente = procSceltaContraente;
	}

	
}
