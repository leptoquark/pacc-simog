package it.avlp.simog.beans.comparators;

import it.avlp.simog.beans.RequisitoGara;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RequisitoGaraDuplicatiAssLottiComparator implements Comparator<RequisitoGara> {

   /*
    * Questo comparatore verifica che due requisti uguali
    * siano associati a gruppi di lotti differenti.
    *  
    *  0 - requisito non valido
    * >0 - requisito valido
    * 
    */
   
   public int compare(RequisitoGara req0, RequisitoGara req1) {

      int result = 0;

      /* Requisiti uguali */
      result += req0.getCodice_dettaglio() == req1.getCodice_dettaglio() ? 0 : 1;
      result += req0.getDescrizione().equalsIgnoreCase(req1.getDescrizione()) ? 0 : 1;

      /* Associati a lotti diversi */
      if( result == 0 && req0.getLotti_associati().size() > 0 && req1.getLotti_associati().size() > 0 ){
         
         List<Long> intersezioneListeLotti = new LinkedList<Long>(req0.getLotti_associati());
         intersezioneListeLotti.retainAll(req1.getLotti_associati());
         
         if( intersezioneListeLotti.size() == 0 ){
            result++;
         }
      }
      return result;
   }
   
}
