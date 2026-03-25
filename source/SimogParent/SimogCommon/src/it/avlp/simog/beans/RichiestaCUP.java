package it.avlp.simog.beans;

import java.sql.Timestamp;

public class RichiestaCUP {

	private long ID_RICHIESTA;

	private Timestamp DATA_RICHIESTA;
	private String CUP;
	private Timestamp ULT_DATA_WS;
	private Timestamp DATA_ESITO;
	private String ESITO_RICHIESTA;
	private String VALIDO;
	private Timestamp DATA_RICONCIL;
	private String UTE_RICONCIL;
	private String stato;
	private String tematica;
	private String idTematica;
	private String COD_INVESTIMENTO_RGS;
	private String FLAG_PNRR_PNC_RGS;

	public long getID_RICHIESTA() {
		return ID_RICHIESTA;
	}

	public void setID_RICHIESTA(long iD_RICHIESTA) {
		ID_RICHIESTA = iD_RICHIESTA;
	}

	public Timestamp getDATA_RICHIESTA() {
		return DATA_RICHIESTA;
	}

	public void setDATA_RICHIESTA(Timestamp dATA_RICHIESTA) {
		DATA_RICHIESTA = dATA_RICHIESTA;
	}

	public String getCUP() {
		return CUP;
	}

	public void setCUP(String cup) {
		CUP = cup;
	}

	public Timestamp getULT_DATA_WS() {
		return ULT_DATA_WS;
	}

	public void setULT_DATA_WS(Timestamp uLT_DATA_WS) {
		ULT_DATA_WS = uLT_DATA_WS;
	}

	public Timestamp getDATA_ESITO() {
		return DATA_ESITO;
	}

	public void setDATA_ESITO(Timestamp dATA_ESITO) {
		DATA_ESITO = dATA_ESITO;
	}

	public String getESITO_RICHIESTA() {
		return ESITO_RICHIESTA;
	}

	public void setESITO_RICHIESTA(String eSITO_RICHIESTA) {
		ESITO_RICHIESTA = eSITO_RICHIESTA;
	}

	public String getVALIDO() {
		return VALIDO;
	}

	public void setVALIDO(String vALIDO) {
		VALIDO = vALIDO;
	}

	public Timestamp getDATA_RICONCIL() {
		return DATA_RICONCIL;
	}

	public void setDATA_RICONCIL(Timestamp dATA_RICONCIL) {
		DATA_RICONCIL = dATA_RICONCIL;
	}

	public String getUTE_RICONCIL() {
		return UTE_RICONCIL;
	}

	public void setUTE_RICONCIL(String uTE_RICONCIL) {
		UTE_RICONCIL = uTE_RICONCIL;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getTematica() {
		return tematica;
	}

	public void setTematica(String tematica) {
		this.tematica = tematica;
	}

	public String getCOD_INVESTIMENTO_RGS() {
		return COD_INVESTIMENTO_RGS;
	}

	public void setCOD_INVESTIMENTO_RGS(String cOD_INVESTIMENTO_RGS) {
		COD_INVESTIMENTO_RGS = cOD_INVESTIMENTO_RGS;
	}

	public String getFLAG_PNRR_PNC_RGS() {
		return FLAG_PNRR_PNC_RGS;
	}

	public void setFLAG_PNRR_PNC_RGS(String fLAG_PNRR_PNC_RGS) {
		FLAG_PNRR_PNC_RGS = fLAG_PNRR_PNC_RGS;
	}

	public void setIdTematica(String string) {
		this.idTematica = string;
	}
	public String getIdTematica() {
		return idTematica ;
	}

}
