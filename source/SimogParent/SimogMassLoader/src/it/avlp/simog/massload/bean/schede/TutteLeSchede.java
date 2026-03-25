package it.avlp.simog.massload.bean.schede;

import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.variante.SchedaVariante;

import java.util.List;

public class TutteLeSchede {

	private Scheda_A schedaA;
//	private Scheda_A schedaSottosoglia;
//	private Scheda_A schedaEscluso;
	private SchedaInizioLavori schedaInizio;
	private SchedaAvanzamento schedeAvanzamento;
	private SchedaConclusione schedaConclusione;
	private SchedaCollaudo schedaCollaudo;
	private SchedaAccordo schedeAccordo;
	private SchedaR129 schedeRitardi;
	private SchedaSospensione schedeSospensione;
	private SchedaSubAppalti schedeSubAppalto;
	private SchedaVariante schedeVariante;
	private	SchedaStipula	schedaStipula;
	private List<SoggettoPartecipanteBean> listOfAnagrafichePartecipanti;
	private List<SoggettoResponsabileBean> listOfAnagraficheResponsabili;
	
	public Scheda_A getSchedaA() {
		return schedaA;
	}
	public void setSchedaA(Scheda_A schedaA) {
		this.schedaA = schedaA;
	}
	public SchedaInizioLavori getSchedaInizio() {
		return schedaInizio;
	}
	public void setSchedaInizio(SchedaInizioLavori schedaInizio) {
		this.schedaInizio = schedaInizio;
	}
	public SchedaAvanzamento getSchedeAvanzamento() {
		return schedeAvanzamento;
	}
	public void setSchedeAvanzamento(SchedaAvanzamento schedeAvanzamento) {
		this.schedeAvanzamento = schedeAvanzamento;
	}
	public SchedaConclusione getSchedaConclusione() {
		return schedaConclusione;
	}
	public void setSchedaConclusione(SchedaConclusione schedaConclusione) {
		this.schedaConclusione = schedaConclusione;
	}
	public SchedaCollaudo getSchedaCollaudo() {
		return schedaCollaudo;
	}
	public void setSchedaCollaudo(SchedaCollaudo schedaCollaudo) {
		this.schedaCollaudo = schedaCollaudo;
	}
	public SchedaAccordo getSchedeAccordo() {
		return schedeAccordo;
	}
	public void setSchedeAccordo(SchedaAccordo schedeAccordo) {
		this.schedeAccordo = schedeAccordo;
	}
	public SchedaR129 getSchedeRitardi() {
		return schedeRitardi;
	}
	public void setSchedeRitardi(SchedaR129 schedeRitardi) {
		this.schedeRitardi = schedeRitardi;
	}
	public SchedaSospensione getSchedeSospensione() {
		return schedeSospensione;
	}
	public void setSchedeSospensione(SchedaSospensione schedeSospensione) {
		this.schedeSospensione = schedeSospensione;
	}
	public SchedaSubAppalti getSchedeSubAppalto() {
		return schedeSubAppalto;
	}
	public void setSchedeSubAppalto(SchedaSubAppalti schedeSubAppalto) {
		this.schedeSubAppalto = schedeSubAppalto;
	}
	public SchedaVariante getSchedeVariante() {
		return schedeVariante;
	}
	public void setSchedeVariante(SchedaVariante schedeVariante) {
		this.schedeVariante = schedeVariante;
	}
	public List<SoggettoPartecipanteBean> getListOfAnagrafichePartecipanti() {
		return listOfAnagrafichePartecipanti;
	}
	public void setListOfAnagrafichePartecipanti(
			List<SoggettoPartecipanteBean> listOfAnagrafichePartecipanti) {
		this.listOfAnagrafichePartecipanti = listOfAnagrafichePartecipanti;
	}
	public List<SoggettoResponsabileBean> getListOfAnagraficheResponsabili() {
		return listOfAnagraficheResponsabili;
	}
	public void setListOfAnagraficheResponsabili(
			List<SoggettoResponsabileBean> listOfAnagraficheResponsabili) {
		this.listOfAnagraficheResponsabili = listOfAnagraficheResponsabili;
	}
//	public Scheda_A getSchedaSottosoglia() {
//		return schedaSottosoglia;
//	}
//	public void setSchedaSottosoglia(Scheda_A schedaSottosoglia) {
//		this.schedaSottosoglia = schedaSottosoglia;
//	}
//	public Scheda_A getSchedaEscluso() {
//		return schedaEscluso;
//	}
//	public void setSchedaEscluso(Scheda_A schedaEscluso) {
//		this.schedaEscluso = schedaEscluso;
//	}
	public SchedaStipula getSchedaStipula() {
		return schedaStipula;
	}
	public void setSchedaStipula(SchedaStipula schedaStipula) {
		this.schedaStipula = schedaStipula;
	}
	
	
}
