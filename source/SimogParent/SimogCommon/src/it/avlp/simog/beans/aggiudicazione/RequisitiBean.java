package it.avlp.simog.beans.aggiudicazione;

import java.sql.Timestamp;

/*******************************************************************************
 * La classe definisce e gestisce i seguenti attributi realtivi ai Requisiti:
 * <ul> 
 *   <li>long : idRequisito
 *   <li>Timestamp : dataInizioRequisito
 *	 <li>String : sezione
 *   <li>String : idCategoria
 *	 <li>String : classeImporto
 *   <li>String : prevalente
 *   <li>String : scorporabile
 *	 <li>String : subAppaltabile
 *	 <li>Timestamp : dataFineRequisito
 *	 <li>int : idStato
 *	 <li>long : idAggiudicazione
 *	 <li>Timestamp : dataInizioAggiudicazione
 *	 <li>String : descCategoria
 *	 <li>String : importoDa
 * </ul>
 *con i relativi metodi di get e set.
 * @author Steponweb
 *
 */
public class RequisitiBean  {
	
	private long idRequisito;
	private Timestamp dataInizioRequisito;
	private String sezione;
	private String idCategoria;
	private String classeImporto;
	private String prevalente;
	private String scorporabile;
	private String subAppaltabile;
	private Timestamp dataFineRequisito;
	private int idStato;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private String descCategoria;
	private String importoDa;
	
	public String getDescCategoria() {
		return descCategoria;
	}
	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}
	public String getImportoDa() {
		return importoDa;
	}
	public void setImportoDa(String importoDa) {
		this.importoDa = importoDa;
	}
	public long getIdRequisito() {
		return idRequisito;
	}
	public void setIdRequisito(long idRequisito) {
		this.idRequisito = idRequisito;
	}
	public Timestamp getDataInizioRequisito() {
		return dataInizioRequisito;
	}
	public void setDataInizioRequisito(Timestamp dataInizioRequisito) {
		this.dataInizioRequisito = dataInizioRequisito;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getClasseImporto() {
		return classeImporto;
	}
	public void setClasseImporto(String classeImporto) {
		this.classeImporto = classeImporto;
	}
	public String getPrevalente() {
		return prevalente;
	}
	public void setPrevalente(String prevalente) {
		this.prevalente = prevalente;
	}
	public String getScorporabile() {
		return scorporabile;
	}
	public void setScorporabile(String scorporabile) {
		this.scorporabile = scorporabile;
	}
	public Timestamp getDataFineRequisito() {
		return dataFineRequisito;
	}
	public void setDataFineRequisito(Timestamp dataFineRequisito) {
		this.dataFineRequisito = dataFineRequisito;
	}
	public int getIdStato() {
		return idStato;
	}
	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
	public long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	
	public String getSubAppaltabile() {
		return subAppaltabile;
	}
	public void setSubAppaltabile(String subAppaltabile) {
		this.subAppaltabile = subAppaltabile;
	}

	

}
