package it.avlp.simog.beans.conclusione;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ConclusioneBean {

	/* ref for load */
	private Long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	/* ref for status */
	private long idStato;
	private String descrizioneStato;
	/* local */
	private long motiviInterruzione;
	private long motiviRisoluzione;
	private long idUltim;
	private Timestamp dataIniUltim;
	private Timestamp dataFinUltim;
	private String dataRisoluzione;
	private String flagOneri;
	private BigDecimal oneriRisoluzione;
	private String flagPolizza;
	private String dataUltimazione;
	private Long numInfortuni;
	private Long numInfPerm;
	private Long numInfMort;
	private boolean okCancellazione ;
	
	private String idLocale;


	//gm nuovo codice 3.0
	private Long giorniProroga;
	private String termineUltimazione;	
	private String dataConsegna;
	
	public Long getGiorniProroga() {
		return giorniProroga;
	}

	public void setGiorniProroga(Long giorniProroga) {
		this.giorniProroga = giorniProroga;
	}
	
	public String getTermineUltimazione() {
		return termineUltimazione;
	}

	public void setTermineUltimazione(String termineUltimazione) {
		this.termineUltimazione = termineUltimazione;
	}
	public String getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(String dataConsegna) {
		this.dataConsegna = dataConsegna;
	}
//	gm fine nuovo codice 3.0
    
	public String getIdLocale() {
		return idLocale;
	}
	
	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	
	
	public boolean isRichAnn(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTO_ANNULLAMENTO));
		else return false;
	}

	public boolean isRichDelete(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANCELLAZIONE)
					|| descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANC_TOTALE));
		else return false;
	}
	
	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
	}
	
	public Long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(Long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public Long getIdUltim() {
		return idUltim;
	}
	public void setIdUltim(Long idUltim) {
		this.idUltim = idUltim;
	}
	public Timestamp getDataIniUltim() {
		return dataIniUltim;
	}
	public void setDataIniUltim(Timestamp dataIniUltim) {
		this.dataIniUltim = dataIniUltim;
	}
	public Timestamp getDataFinUltim() {
		return dataFinUltim;
	}
	public void setDataFinUltim(Timestamp dataFinUltim) {
		this.dataFinUltim = dataFinUltim;
	}
	public String getDataRisoluzione() {
		return dataRisoluzione;
	}
	public void setDataRisoluzione(String dataRisoluzione) {
		this.dataRisoluzione = dataRisoluzione;
	}
	public String getFlagOneri() {
		return flagOneri;
	}
	public void setFlagOneri(String flagOneri) {
		this.flagOneri = flagOneri;
	}
	public String getFlagPolizza() {
		return flagPolizza;
	}
	public void setFlagPolizza(String flagPolizza) {
		this.flagPolizza = flagPolizza;
	}
	public String getDataUltimazione() {
		return dataUltimazione;
	}
	public void setDataUltimazione(String dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}
	public Long getNumInfortuni() {
		return numInfortuni;
	}
	public void setNumInfortuni(Long numInfortuni) {
		this.numInfortuni = numInfortuni;
	}
	public Long getNumInfPerm() {
		return numInfPerm;
	}
	public void setNumInfPerm(Long numInfPerm) {
		this.numInfPerm = numInfPerm;
	}
	public Long getNumInfMort() {
		return numInfMort;
	}
	public void setNumInfMort(Long numInfMort) {
		this.numInfMort = numInfMort;
	}
	public BigDecimal getOneriRisoluzione() {
		return oneriRisoluzione;
	}
	
	public String getOneriRisoluzioneStr() {
		return PageHelper.formattaImporto(oneriRisoluzione);
	}
	public void setOneriRisoluzione(BigDecimal oneriRisoluzione) {
		this.oneriRisoluzione = oneriRisoluzione;
	}
	public Long getMotiviRisoluzione() {
		return motiviRisoluzione;
	}
	public void setMotiviRisoluzione(Long motiviRisoluzione) {
		this.motiviRisoluzione = motiviRisoluzione;
	}
	public Long getMotiviInterruzione() {
		return motiviInterruzione;
	}
	public void setMotiviInterruzione(Long motiviInterruzione) {
		this.motiviInterruzione = motiviInterruzione;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	public boolean isOkCancellazione() {
		return okCancellazione;
	}

	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}
}
