package it.avlp.simog.beans;

import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;

public class DatiEconomiciBean implements VO{
	
   private BigDecimal  importoLavori = new BigDecimal(0);
   private BigDecimal  importoServizi = new BigDecimal(0);
   private BigDecimal  importoForniture = new BigDecimal(0);
   private BigDecimal  importoSicurezza = new BigDecimal(0);
   private BigDecimal  importoNonAssog = new BigDecimal(0);
   private BigDecimal  importoDisposizione = new BigDecimal(0);
   private BigDecimal  importoProgettazione = new BigDecimal(0);
   private BigDecimal  importoComplessivo = new BigDecimal(0); // attenzione ha senso solo per i sottosoglia
   private BigDecimal  importoAggiudicazione = new BigDecimal(0);
   private String      dataVerbaleAggiudicazione = "";
   private String      dataVerbaleAggiudicazioneMin = "99999999";

   private List<AggiudicatarioBean> aggiudicatari;

   public BigDecimal getImportoLavori() {
      return importoLavori;
   }

   public void setImportoLavori(BigDecimal importoLavori) {
      this.importoLavori = importoLavori;
   }

   public BigDecimal getImportoServizi() {
      return importoServizi;
   }

   public void setImportoServizi(BigDecimal importoServizi) {
      this.importoServizi = importoServizi;
   }

   public BigDecimal getImportoForniture() {
      return importoForniture;
   }

   public void setImportoForniture(BigDecimal importoForniture) {
      this.importoForniture = importoForniture;
   }

   public BigDecimal getImportoSicurezza() {
      return importoSicurezza;
   }

   public void setImportoSicurezza(BigDecimal importoSicurezza) {
      this.importoSicurezza = importoSicurezza;
   }

   public BigDecimal getImportoNonAssog() {
      return importoNonAssog;
   }

   public void setImportoNonAssog(BigDecimal importoNonAssog) {
      this.importoNonAssog = importoNonAssog;
   }

   public BigDecimal getImportoDisposizione() {
      return importoDisposizione;
   }

   public void setImportoDisposizione(BigDecimal importoDisposizione) {
      this.importoDisposizione = importoDisposizione;
   }

   public BigDecimal getImportoProgettazione() {
      return importoProgettazione;
   }

   public void setImportoProgettazione(BigDecimal importoProgettazione) {
      this.importoProgettazione = importoProgettazione;
   }

   public BigDecimal getImportoComplessivo() {
      return importoComplessivo;
   }

   public void setImportoComplessivo(BigDecimal importoComplessivo) {
      this.importoComplessivo = importoComplessivo;
   }

   public BigDecimal getImportoAggiudicazione() {
      return importoAggiudicazione;
   }

   public void setImportoAggiudicazione(BigDecimal importoAggiudicazione) {
      this.importoAggiudicazione = importoAggiudicazione;
   }

   public String getDataVerbaleAggiudicazione() {
      return dataVerbaleAggiudicazione;
   }

   public String getDataAggiudicazioneStr() {
      return PageHelper.getViewDate(dataVerbaleAggiudicazione);
   }
   public void setDataVerbaleAggiudicazione(String dataVerbaleAggiudicazione) {
      this.dataVerbaleAggiudicazione = dataVerbaleAggiudicazione;
   }

   public List<AggiudicatarioBean> getAggiudicatari() {
      return aggiudicatari;
   }

   public void setAggiudicatari(List<AggiudicatarioBean> aggiudicatari) {
      this.aggiudicatari = aggiudicatari;
   }

   public String getImportoLavoriStr() {
      return PageHelper.formattaImporto(importoLavori);
   }
   public String getImportoServiziStr() {
      return PageHelper.formattaImporto(importoServizi);
   }
   public String getImportoFornitureStr() {
      return PageHelper.formattaImporto(importoForniture);
   }
   public String getImportoSicurezzaStr() {
      return PageHelper.formattaImporto(importoSicurezza);
   }
   public String getImportoNonAssogStr() {
      return PageHelper.formattaImporto(importoNonAssog);
   }
   public String getImportoDisposizioneStr() {
      return PageHelper.formattaImporto(importoDisposizione);
   }
   public String getImportoProgettazioneStr() {
      return PageHelper.formattaImporto(importoProgettazione);
   }
   public String getImportoComplessivoStr() {
      return PageHelper.formattaImporto(importoComplessivo);
   }
   public String getImportoAggiudicazioneStr() {
      return PageHelper.formattaImporto(importoAggiudicazione);
   }
   public BigDecimal getSubTotale() {
      return importoLavori.add(importoServizi).add(importoForniture).add(importoComplessivo);
   }
   public BigDecimal getImportoComplessivoApp() {
      return importoLavori.add(importoServizi).add(importoForniture).add(importoComplessivo)
            .add(importoSicurezza).add(importoNonAssog).add(importoProgettazione);
   }
   public BigDecimal getImportoIntervento() {
      return importoLavori.add(importoServizi).add(importoComplessivo)
               .add(importoForniture).add(importoSicurezza).add(importoNonAssog)
               .add(importoDisposizione).add(importoProgettazione);
   }
   public String getSubTotaleStr() {
      return PageHelper.formattaImporto(this.getSubTotale());
   }
   public String getImportoComplessivoAppStr() {
      return PageHelper.formattaImporto(this.getImportoComplessivoApp());
   }
   public String getImportoInterventoStr() {
      return PageHelper.formattaImporto(this.getImportoIntervento());
   }

   public String getDataVerbaleAggiudicazioneMin() {
      return dataVerbaleAggiudicazioneMin;
   }

   public void setDataVerbaleAggiudicazioneMin(String dataVerbaleAggiudicazioneMin) {
      this.dataVerbaleAggiudicazioneMin = dataVerbaleAggiudicazioneMin;
   }
   public String getDataAggiudicazioneMinStr() {
      return PageHelper.getViewDate(dataVerbaleAggiudicazioneMin);
   }
}

