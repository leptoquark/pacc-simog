package it.avlp.simog.common.contributo;


import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;

import java.math.BigDecimal;
import java.util.Calendar;

/*
 * classe per il passaggio dei dati al Gestore contributo
 */
public class ParametriContributo {

	String codiceFiscale;
	BigDecimal importo;
	Calendar dataPubblicazione;
	String motivoEscusione;
	String tipoProcedura;
	String cigAccordoQuadro;
	String applicazione = "SIMOG3";
	
	int modoReal;
	long idGara;
	
	//MAC 36255 3.04.8 aggiunto motivoEsclusioneGara per calcolo contributoSA
	public ParametriContributo(Gara gara, Lotto lotto, String motivoEsclusioneGara, Calendar data) {
		this.codiceFiscale = gara.getCF_AMMINISTRAZIONE();
		this.dataPubblicazione = data;
		this.cigAccordoQuadro = gara.getCIG_ACC_QUADRO();
		this.modoReal = gara.getID_MODO_REAL();
		this.importo = gara.getIMPORTO_GARA();
		this.idGara = gara.getId_Gara();
		if (motivoEsclusioneGara != null && lotto == null) {
			this.motivoEscusione = motivoEsclusioneGara;
		}
		
		if (lotto != null){
			this.motivoEscusione = String.valueOf(lotto.getID_ESCLUSIONE()).trim();
			this.tipoProcedura = lotto.getId_Scelta_Contraente();
			this.importo = lotto.getImporto_Lotto();
		}
	}
	
	public ParametriContributo(Gara gara, Lotto lotto, Calendar data, java.sql.Connection conn, org.apache.log4j.Logger logger) {
		this.codiceFiscale = gara.getCF_AMMINISTRAZIONE();
		this.dataPubblicazione = data;
		this.cigAccordoQuadro = gara.getCIG_ACC_QUADRO();
		this.modoReal = gara.getID_MODO_REAL();
		this.importo = gara.getIMPORTO_GARA();
		this.idGara = gara.getId_Gara();
		
		if (lotto != null){
			it.avlp.simog.garamanager.lotto.LottoManager lm = new it.avlp.simog.garamanager.lotto.LottoManager(conn,logger);
			this.motivoEscusione = String.valueOf(lotto.getID_ESCLUSIONE()).trim();
			this.tipoProcedura = lm.getCodProceduraBDNCP(lotto.getId_Scelta_Contraente());
			this.importo = lotto.getImporto_Lotto();
		}
	}
	
	public String getApplicazione() {
		return applicazione;
	}
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	public String getCigAccordoQuadro() {
		return cigAccordoQuadro;
	}
	public void setCigAccordoQuadro(String cigAccordoQuadro) {
		this.cigAccordoQuadro = cigAccordoQuadro;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Calendar getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(Calendar dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getMotivoEscusione() {
		return motivoEscusione;
	}
	public void setMotivoEscusione(String motivoEscusione) {
		this.motivoEscusione = motivoEscusione;
	}
	public String getTipoProcedura() {
		return tipoProcedura;
	}
	public void setTipoProcedura(String tipoProcedura) {
		this.tipoProcedura = tipoProcedura;
	}
	public int getModoReal() {
		return modoReal;
	}
	public void setModoReal(int modoReal) {
		this.modoReal = modoReal;
	}
	public long getIdGara() {
		return idGara;
	}
	public void setIdGara(long idGara) {
		this.idGara = idGara;
	}
}


