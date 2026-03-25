package it.avlp.simog.beans.comparators;

import it.avlp.simog.beans.RequisitoGara;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RequisitoGaraDuplicatiAttributiComparator implements Comparator<RequisitoGara> {

   /*
    * Questo comparatore verifica che due requisti uguali
    * abbiano tutti gli attributi uguali a meno di lotti e documenti
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
      
      if( req0.getValore() != null ){
         result += req0.getValore().equalsIgnoreCase(req1.getValore()) ? 0 : 1;     
      } else {
         result += req1.getValore() == null ? 0 : 1;
      }
      
      result += req0.getFlag_esclusione().equalsIgnoreCase(req1.getFlag_esclusione()) ? 0 : 1;
      result += req0.getFlag_comprova_offerta().equalsIgnoreCase(req1.getFlag_comprova_offerta()) ? 0 : 1;
      result += req0.getFlag_avvalimento().equalsIgnoreCase(req1.getFlag_avvalimento()) ? 0 : 1;
      result += req0.getFlag_bando_tipo().equalsIgnoreCase(req1.getFlag_bando_tipo()) ? 0 : 1;
      result += req0.getFlag_riservatezza().equalsIgnoreCase(req1.getFlag_riservatezza()) ? 0 : 1;

      return result;
   }
   
}
