package it.avlp.simog.beans;

import it.avlp.simog.db.generated.EAGG_MOTIVI;

import java.sql.Date;

	/**
	 * Ogni oggetto di questa classe viene utilizzato
	 * per la memorizzazione temporanea e la successiva
	 * archiviazione delle informazioni relative ai motivi 
	 */

public class EaggMotivi implements EAGG_MOTIVI, Cloneable {


   private long fCOD_MOTIVO;
   private String fCODICE;
   private String fDESCRIZIONE;
   private Date fDATA_INIZIO_VALIDITA;
   private Date fDATA_FINE_VALIDITA;
   private String fDATA_ULTIMA_MODIFICA;

   @Override
   public long getCOD_MOTIVO() {
      
      return fCOD_MOTIVO;
   }

   @Override
   public String getCODICE() {
      
      return fCODICE;
   }

   @Override
   public String getDESCRIZIONE() {
     
      return fDESCRIZIONE;
   }

   @Override
   public Date getDATA_INIZIO_VALIDITA() {
      // 
      return fDATA_INIZIO_VALIDITA;
   }

   @Override
   public Date getDATA_FINE_VALIDITA() {
      // 
      return fDATA_FINE_VALIDITA;
   }

   @Override
   public String getDATA_ULTIMA_MODIFICA() {
      // 
      return fDATA_ULTIMA_MODIFICA;
   }

   public void setCOD_MOTIVO(long fCOD_MOTIVO) {
      this.fCOD_MOTIVO = fCOD_MOTIVO;
   }

   public void setCODICE(String fCODICE) {
      this.fCODICE = fCODICE;
   }

   public void setDESCRIZIONE(String fDESCRIZIONE) {
      this.fDESCRIZIONE = fDESCRIZIONE;
   }

   public void setDATA_INIZIO_VALIDITA(Date fDATA_INIZIO_VALIDITA) {
      this.fDATA_INIZIO_VALIDITA = fDATA_INIZIO_VALIDITA;
   }

   public void setDATA_FINE_VALIDITA(Date fDATA_FINE_VALIDITA) {
      this.fDATA_FINE_VALIDITA = fDATA_FINE_VALIDITA;
   }

   public void setDATA_ULTIMA_MODIFICA(String fDATA_ULTIMA_MODIFICA) {
      this.fDATA_ULTIMA_MODIFICA = fDATA_ULTIMA_MODIFICA;
   }
}

