package it.avlp.simog.beans;

import java.io.Serializable;

public class InfoRettificaBean implements Serializable{
	
 /**
	 * 
	 */
	private static final long serialVersionUID = 806269016656930732L;
	
	
private Long   idGara;
 private Long   idPubblicazione;
 private String tipoOperazione;
 private String flagSospeso;
 private String dataPubblicazione;
 private String dataScadenzaPagamenti;
 private String dataScadenzaInvito;
 
public Long getIdGara() {
	return idGara;
}
public Long getIdPubblicazione() {
	return idPubblicazione;
}
public String getTipoOperazione() {
	return tipoOperazione;
}
public String getFlagSospeso() {
	
	if(this.flagSospeso == null) {
		this.flagSospeso = "";
	}
	
	
	return flagSospeso;
}
public String getDataPubblicazione() {
	return dataPubblicazione;
}
public String getDataScadenzaPagamenti() {
	return dataScadenzaPagamenti;
}
public void setIdGara(Long idGara) {
	this.idGara = idGara;
}
public void setIdPubblicazione(Long idPubblicazione) {
	this.idPubblicazione = idPubblicazione;
}
public void setTipoOperazione(String tipoOperazione) {
	this.tipoOperazione = tipoOperazione;
}
public void setFlagSospeso(String flagSospeso) {
	this.flagSospeso = flagSospeso;
}
public void setDataPubblicazione(String dataPubblicazione) {
	this.dataPubblicazione = dataPubblicazione;
}
public void setDataScadenzaPagamenti(String dataScadenzaPagamenti) {
	this.dataScadenzaPagamenti = dataScadenzaPagamenti;
}
public String getDataScadenzaInvito() {
	return dataScadenzaInvito;
}
public void setDataScadenzaInvito(String dataScadenzaInvito) {
	this.dataScadenzaInvito = dataScadenzaInvito;
}


}
