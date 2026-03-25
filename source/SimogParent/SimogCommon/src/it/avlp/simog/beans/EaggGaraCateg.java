package it.avlp.simog.beans;

import it.avlp.simog.db.generated.EAGG_GARA_CATEGORIE;

	/**
	 * Ogni oggetto di questa classe viene utilizzato
	 * per la memorizzazione temporanea e la successiva
	 * archiviazione delle informazioni relative alle categorie merceologiche associate alla gara
	 */

public class EaggGaraCateg implements EAGG_GARA_CATEGORIE, Cloneable {


   private long fCOD_GARA_CATEG;
   private long fCOD_GARA;
   private long fCOD_CATEGORIA;
   
   public long getCOD_GARA_CATEG() {
      return fCOD_GARA_CATEG;
   }
   public void setCOD_GARA_CATEG(long fCOD_GARA_CATEG) {
      this.fCOD_GARA_CATEG = fCOD_GARA_CATEG;
   }
   public long getCOD_GARA() {
      return fCOD_GARA;
   }
   public void setCOD_GARA(long fCOD_GARA) {
      this.fCOD_GARA = fCOD_GARA;
   }
   public long getCOD_CATEGORIA() {
      return fCOD_CATEGORIA;
   }
   
   public void setCOD_CATEGORIA(long fCOD_CATEGORIA) {
      this.fCOD_CATEGORIA = fCOD_CATEGORIA;
   }
}

