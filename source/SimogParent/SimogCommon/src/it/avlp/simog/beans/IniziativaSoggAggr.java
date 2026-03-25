package it.avlp.simog.beans;

import java.util.List;

import it.avlp.simog.db.generated.INIZIATIVE_SOGG_AGGR;

public class IniziativaSoggAggr implements INIZIATIVE_SOGG_AGGR {

	private long idIniziativa;
	private long idGara;
	private String CIG;
	private String descrizioneSoggAggr;
	private String descrizioneIniziativa;
	private List<String> ambitoLotto;
	private String ssaaRif;
	private String statoIniziativa;
	private String flagConfrontoComp;
	private String note;
	private String link;
	
	private List<String> listaCatIniziativa;
	private List<String> listaTerritoriIniziativa;
	
	//Separatori per la visualizzazione dei dati su SimogWeb
	private String separatorField = "~~";
	
	@Override
	public long getIdIniziativa() {
		return idIniziativa;
	}

	@Override
	public long getIdGara() {
		return idGara;
	}

	@Override
	public String getCIG() {
		return CIG;
	}

	@Override
	public String getDescrizioneSoggAggr() {
		return descrizioneSoggAggr;
	}

	
	public List<String> getAmbitoLotto() {
		return ambitoLotto;
	}

	@Override
	public String getSSAARif() {
		return ssaaRif;
	}

	@Override
	public String getStatoIniziativa() {
		return statoIniziativa;
	}

	@Override
	public String getFlagConfrontoComp() {
		return flagConfrontoComp;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public String getLink() {
		return link;
	}

	@Override
	public void setIdIniziativa(long idIniziativa) {
		this.idIniziativa=idIniziativa;
		
	}

	@Override
	public void setIdGara(long idGara) {
		this.idGara = idGara;
	}

	@Override
	public void setCIG(String CIG) {
		this.CIG = CIG;
		
	}

	@Override
	public void setDescrizioneSoggAggr(String descrizione) {
		this.descrizioneSoggAggr = descrizione;
		
	}


	public void setAmbitoLotto(List<String> ambito) {
		this.ambitoLotto = ambito;
		
	}

	@Override
	public void setSSAARif(String ssaarif) {
		this.ssaaRif=ssaarif;
		
	}

	@Override
	public void setStatoIniziativa(String stato) {
        this.statoIniziativa=stato;		
	}

	@Override
	public void setFlagConfrontoComp(String flagConfrontoComp) {
	    this.flagConfrontoComp=flagConfrontoComp;
		
	}

	@Override
	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public void setLink(String link) {
		this.link=link;
		
	}

	public List<String> getListaCatIniziativa() {
		return listaCatIniziativa;
	}

	public void setListaCatIniziativa(List<String> listaCatIniziativa) {
		this.listaCatIniziativa = listaCatIniziativa;
	}

	public List<String> getListaTerritoriIniziativa() {
		return listaTerritoriIniziativa;
	}

	public void setListaTerritoriIniziativa(List<String> listaTerritoriIniziativa) {
		this.listaTerritoriIniziativa = listaTerritoriIniziativa;
	}

	public String getDescrizioneIniziativa() {
		return descrizioneIniziativa;
	}

	public void setDescrizioneIniziativa(String descrizioneIniziativa) {
		this.descrizioneIniziativa = descrizioneIniziativa;
	}
	
	public String printDataToSimogWeb() {
		return this.idIniziativa+this.separatorField;
	}
	

}
