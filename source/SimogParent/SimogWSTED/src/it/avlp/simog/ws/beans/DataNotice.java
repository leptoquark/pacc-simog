package it.avlp.simog.ws.beans;

import java.util.Calendar;
import java.util.List;

import it.anticorruzione.ted.beans.AwardedContract;
import it.anticorruzione.ted.beans.LottoTED;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazione;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoModifica;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoRettifica;

/**
 * Classe contenitore di tutti i dati da inviare al TED
 * @author spado
 *
 */
public class DataNotice {

	private Gara gara;
	private List<LottoTED> listaLotti;
	private DeltaGaraTED deltaGaraTED;
	private String oraScadenzaPag;
	private FormularioAvvisoAggiudicazione formularioAgg;
	private FormularioAvvisoRettifica formularioRettifica;
	private FormularioAvvisoModifica formularioModifica;
	private String noDocExt;
	private String noticeNumberOjs;
	private String originalNoDocExt;
	private String originalDataDispatch;
	private String cigModifica;
	private AwardedContract awardedContract;
	
      //Utente utilizzato per l'accesso al TED
		private String esenderlogin;
		
		
		//Nome dell'organizzazione (da parametrizzare)
		private String organization = "ANAC";
		
		//Mail dell'organizzazione (da parametrizzare)
	private String email = "TEDeSender@anticorruzione.it";
	
	public Gara getGara() {
		return gara;
	}
	public void setGara(Gara gara) {
		this.gara = gara;
	}

	public DeltaGaraTED getDeltaGaraTED() {
		return deltaGaraTED;
	}
	public void setDeltaGaraTED(DeltaGaraTED deltaGaraTED) {
		this.deltaGaraTED = deltaGaraTED;
	}

	public void setFormularioAgg(FormularioAvvisoAggiudicazione formularioAgg) {
		this.formularioAgg=formularioAgg;
	}
	public FormularioAvvisoAggiudicazione getFormularioAgg() {
		return this.formularioAgg;
	}
	public String getNoticeNumberOjs() {
		return noticeNumberOjs;
	}
	public void setNoticeNumberOjs(String noticeNumberOjs) {
		this.noticeNumberOjs = noticeNumberOjs;
	}
	public String getOriginalNoDocExt() {
		return originalNoDocExt;
	}
	public void setOriginalNoDocExt(String noDocExt) {
		this.originalNoDocExt = noDocExt;
	}
	
	public String getEsenderlogin() {
		return esenderlogin;
	}

	public void setEsenderlogin(String esenderlogin) {
		this.esenderlogin = esenderlogin;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organisation) {
		this.organization = organisation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getOriginalDataDispatch() {
		return originalDataDispatch;
	}
	public void setOriginalDataDispatch(String originalDataDispatch) {
		this.originalDataDispatch = originalDataDispatch;
	}
	public FormularioAvvisoRettifica getFormularioRettifica() {
		return formularioRettifica;
	}
	public void setFormularioRettifica(FormularioAvvisoRettifica formularioRettifica) {
		this.formularioRettifica = formularioRettifica;
	}
	public String getNoDocExt() {
		return noDocExt;
	}
	public void setNoDocExt(String noDocExt) {
		this.noDocExt = noDocExt;
	}
	public List<LottoTED> getListaLotti() {
		return listaLotti;
	}
	public void setListaLotti(List<LottoTED> listaLotti) {
		this.listaLotti = listaLotti;
	}
	public String getOraScadenzaPag() {
		return oraScadenzaPag;
	}
	public void setOraScadenzaPag(String oraScadenzaPag) {
		this.oraScadenzaPag = oraScadenzaPag;
	}
	public FormularioAvvisoModifica getFormularioModifica() {
		return formularioModifica;
	}
	public void setFormularioModifica(FormularioAvvisoModifica formularioModifica) {
		this.formularioModifica = formularioModifica;
	}
	public String getCigModifica() {
		return cigModifica;
	}
	public void setCigModifica(String cigModifica) {
		this.cigModifica = cigModifica;
	}
	public AwardedContract getAwardedContract() {
		return awardedContract;
	}
	public void setAwardedContract(AwardedContract awardedContract) {
		this.awardedContract = awardedContract;
	}

	
}
