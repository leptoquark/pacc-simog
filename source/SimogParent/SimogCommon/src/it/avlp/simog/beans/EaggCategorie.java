package it.avlp.simog.beans;

import it.avlp.simog.db.generated.EAGG_CATEGORIE;

import java.sql.Date;

	/**
	 * Ogni oggetto di questa classe viene utilizzato
	 * per la memorizzazione temporanea e la successiva
	 * archiviazione delle informazioni relative alle categorie merceologiche
	 */

public class EaggCategorie implements EAGG_CATEGORIE, Cloneable {


   private String fCODICE;
   private String fDESCRIZIONE;
   private Date fDATA_INIZIO_VALIDITA;
   private Date fDATA_FINE_VALIDITA;
   private String fDATA_ULTIMA_MODIFICA;
   private long fCOD_CATEGORIA;

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

   @Override
   public long getCOD_CATEGORIA() {
      
      return fCOD_CATEGORIA;
   }

   public void setCOD_CATEGORIA(long fCOD_CATEGORIA) {
      this.fCOD_CATEGORIA = fCOD_CATEGORIA;
   }
}

