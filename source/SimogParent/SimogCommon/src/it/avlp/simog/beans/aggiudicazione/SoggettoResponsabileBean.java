package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.Base64Coder;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

/***************************************************************************
 * La classe definisce e gestisce i parametri seguenti per la 
 * modellazione del Soggetto Responsabile:
 * <ul>
 * <li> long : idResponsabile;
 * <li>Timestamp : dataInizioRes;
 * <li>String : codiceFiscaleResponsabile;
 * <li>String : cognome;
 * <li>String : nome;
 * <li>String : telefono;
 * <li>String : email;
 * <li>String  : fax;
 * <li>Timestamp : dataFineRes;
 * <li>String : indirizzo;
 * <li>String : cap;
 * <li>String : comuneIstat;
 * </ul>
 * Con i relativi metodi di get e set
 * 
 * @author Steponweb
 *
 */
public class SoggettoResponsabileBean {
	
	private long idResponsabile;
	private Timestamp dataInizioRes;
	private String codiceFiscaleResponsabile;
	private String cognome;
	private String nome;
	private String telefono;
	private String email;
	private String  fax;
	private Timestamp dataFineRes;
	private String indirizzo;
	private String cap;
	private String comuneIstat;
	
	private boolean modifica;
	private String codiceStato;
	private String flagSoggettoEstero;
	
	public long getIdResponsabile() {
		return idResponsabile;
	}
	public void setIdResponsabile(long idResponsabile) {
		this.idResponsabile = idResponsabile;
	}
	public Timestamp getDataInizioRes() {
		return dataInizioRes;
	}
	public void setDataInizioRes(Timestamp dataInizioRes) {
		this.dataInizioRes = dataInizioRes;
	}
	public String getCodiceFiscaleResponsabile() {
		return codiceFiscaleResponsabile;
	}
	public void setCodiceFiscaleResponsabile(String codiceFiscaleResponsabile) {
		this.codiceFiscaleResponsabile = codiceFiscaleResponsabile;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Timestamp getDataFineRes() {
		return dataFineRes;
	}
	public void setDataFineRes(Timestamp dataFineRes) {
		this.dataFineRes = dataFineRes;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getComuneIstat() {
		return comuneIstat;
	}
	public void setComuneIstat(String comuneIstat) {
		this.comuneIstat = comuneIstat;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public boolean isModifica() {
		return modifica;
	}
	public void setModifica(boolean modifica) {
		this.modifica = modifica;
	}
	
	public String getDatiModifica(){
		
		String ret="";
		
		if (modifica) {
			String [] dati = new String[10];
			
			// costruzione della stringa così come arriva dalla pagina
			dati[0] = String.valueOf(idResponsabile).trim();
			dati[1] = codiceFiscaleResponsabile;
			dati[2] = cognome;
			dati[3] = nome;
			dati[4] = telefono;
			dati[5] = fax;
			dati[6] = email;
			dati[7] = indirizzo;
			dati[8] = cap;
			dati[9] = comuneIstat;
			
			ret = Base64Coder.encodeString(StringUtils.join(dati, PSBD.SEP_VARANAG));
		}
		return ret;
	}
	public String getCodiceStato() {
		return codiceStato;
	}
	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}
	public String getFlagSoggettoEstero() {
		return flagSoggettoEstero;
	}
	public void setFlagSoggettoEstero(String flagSoggettoEstero) {
		this.flagSoggettoEstero = flagSoggettoEstero;
	}
}
