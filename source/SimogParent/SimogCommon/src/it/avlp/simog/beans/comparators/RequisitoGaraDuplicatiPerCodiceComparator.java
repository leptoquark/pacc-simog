package it.avlp.simog.beans.comparators;

import it.avlp.simog.beans.RequisitoGara;

import java.util.Comparator;

public class RequisitoGaraDuplicatiPerCodiceComparator implements Comparator<RequisitoGara> {

   /*
    * Questo comparatore verifica che due requisiti uguali/diversi rispetto al codice requisito gara
    *  
    *  0 - requisito uguale
    * >0 - requisito diverso
    * 
    */
   
   public int compare(RequisitoGara req0, RequisitoGara req1) {

      return new Long( req0.getCodice_requisito_gara() - req1.getCodice_requisito_gara() ).intValue();

   }
   
}
