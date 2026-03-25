package it.avlp.simog.beans;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;

public class AllegatoBean {

	private int idAllegato;
	private int idGara;
	private String nomeFile;
	private String tipoDoc;
	private String note;
	private String esitoCheck;
	private Timestamp dataUpload;
	private long idPubblicazione;
	private Timestamp dataInizioPubblicazione;
	private byte [] bout;
	private boolean fromWS;
	
	//MEV 34186 3.04.8
	private String pathFile;
	//
		
	public Timestamp getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(Timestamp dataUpload) {
		this.dataUpload = dataUpload;
	}
	public String getEsitoCheck() {
		return esitoCheck;
	}
	public void setEsitoCheck(String esitoCheck) {
		this.esitoCheck = esitoCheck;
	}
	public int getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(int idAllegato) {
		this.idAllegato = idAllegato;
	}
	public int getIdGara() {
		return idGara;
	}
	public void setIdGara(int idGara) {
		this.idGara = idGara;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public Timestamp getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}
	public void setDataInizioPubblicazione(Timestamp dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	public long getIdPubblicazione() {
		return idPubblicazione;
	}
	public void setIdPubblicazione(long idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}
	public byte []getBout() {
		return bout;
	}
	public void setBout(byte [] bout) {
		this.bout = bout;
	}
	public void setBout(ByteArrayOutputStream bout2) {
		this.bout = bout2.toByteArray();
	}
	public boolean isFromWS() {
		return fromWS;
	}
	public void setFromWS(boolean fromWS) {
		this.fromWS = fromWS;
	}
	
	//MEV 34186 3.04.8
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	// FINE MEV
}
