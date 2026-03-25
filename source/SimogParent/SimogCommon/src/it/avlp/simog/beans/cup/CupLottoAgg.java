package it.avlp.simog.beans.cup;

import it.avlp.simog.db.generated.CUP_LOTTO_AGG;

import java.sql.Timestamp;

public class CupLottoAgg implements CUP_LOTTO_AGG {

   private Long idCupLottoAgg;
   private Timestamp dataInizioCup;
   private Timestamp dataFineCup;
   private Long idLotto;
   private Long idAggiudicazione;
   private Timestamp dataInizioAgg;
   private String cup;
   private String okUtente;
   private int idStato;
   
   public CupLottoAgg() {
      super();
   }

   /**
    * Costruttore 
    * 
    * @param idCupLottoAgg
    * @param dataInizioCup
    * @param dataFineCup
    * @param idLotto
    * @param idAggiudicazione
    * @param dataInizioAgg
    * @param cup
    * @param okUtente
    */
   public CupLottoAgg(Long idCupLottoAgg, Timestamp dataInizioCup, Timestamp dataFineCup, Long idLotto, Long idAggiudicazione, Timestamp dataInizioAgg, String cup, String okUtente, int idStato) 
   {
      this.idCupLottoAgg = idCupLottoAgg;
      this.dataInizioCup = dataInizioCup;
      this.dataFineCup = dataFineCup;
      this.idLotto = idLotto;
      this.idAggiudicazione = idAggiudicazione;
      this.dataInizioAgg = dataInizioAgg;
      this.cup = cup == null ? null : cup.toUpperCase();
      this.okUtente = okUtente;
      this.idStato = idStato;
   }
   
   public CupLottoAgg(CupLottoAggExt cupLottoAggExt) {
      this.idCupLottoAgg = cupLottoAggExt.getIdCupLottoAgg();
      this.dataInizioCup = cupLottoAggExt.getDataInizioCup();
      this.dataFineCup = cupLottoAggExt.getDataFineCup();
      this.idLotto = cupLottoAggExt.getIdLotto();
      this.idAggiudicazione = cupLottoAggExt.getIdAggiudicazione();
      this.dataInizioAgg = cupLottoAggExt.getDataInizioAgg();
      this.cup = cupLottoAggExt.getCup();
      this.okUtente = cupLottoAggExt.getOkUtente();
      this.idStato = cupLottoAggExt.getIdStato();
   }
   
   public Long getIdCupLottoAgg() {
      return idCupLottoAgg;
   }
   public void setIdCupLottoAgg(Long idCupLottoAgg) {
      this.idCupLottoAgg = idCupLottoAgg;
   }
   public Timestamp getDataInizioCup() {
      return dataInizioCup;
   }
   public void setDataInizioCup(Timestamp dataInizioCup) {
      this.dataInizioCup = dataInizioCup;
   }
   public Timestamp getDataFineCup() {
      return dataFineCup;
   }
   public void setDataFineCup(Timestamp dataFineCup) {
      this.dataFineCup = dataFineCup;
   }
   public Long getIdLotto() {
      return idLotto;
   }
   public void setIdLotto(Long idLotto) {
      this.idLotto = idLotto;
   }
   public Long getIdAggiudicazione() {
      return idAggiudicazione;
   }
   public void setIdAggiudicazione(Long idAggiudicazione) {
      this.idAggiudicazione = idAggiudicazione;
   }
   public Timestamp getDataInizioAgg() {
      return dataInizioAgg;
   }
   public void setDataInizioAgg(Timestamp dataInizioAgg) {
      this.dataInizioAgg = dataInizioAgg;
   }
   public String getCup() {
      return cup;
   }
   public void setCup(String cup) {
      this.cup = cup == null ? null : cup.toUpperCase();
   }
   public String getOkUtente() {
      return okUtente;
   }
   public void setOkUtente(String okUtente) {
      this.okUtente = okUtente;
   }
   public int getIdStato() {
      return idStato;
   }
   public void setIdStato(int idStato) {
      this.idStato = idStato;
   }
   
}
