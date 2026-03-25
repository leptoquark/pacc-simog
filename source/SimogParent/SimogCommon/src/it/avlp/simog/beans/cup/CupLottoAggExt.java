package it.avlp.simog.beans.cup;

import it.avlp.simog.beans.RichiestaCUP;

public class CupLottoAggExt extends CupLottoAgg {
 
   
   public CupLottoAggExt(CupLottoAgg cupLottoAgg) {
      super(cupLottoAgg.getIdCupLottoAgg(),
            cupLottoAgg.getDataInizioCup(),
            cupLottoAgg.getDataFineCup(),
            cupLottoAgg.getIdLotto(),
            cupLottoAgg.getIdAggiudicazione(),
            cupLottoAgg.getDataInizioAgg(),
            cupLottoAgg.getCup(),
            cupLottoAgg.getOkUtente(),
            cupLottoAgg.getIdStato()
           );
   }

   public CupLottoAggExt() {}

   private String cig;
   private RichiestaCUP datiDIPE;

   public String getCig() {
      return cig;
   }

   public void setCig(String cig) {
      this.cig = cig;
   }
   
   public RichiestaCUP getDatiDIPE() {
      return datiDIPE;
   }

   public void setDatiDIPE(RichiestaCUP datiDIPE) {
      this.datiDIPE = datiDIPE;
   }

}
