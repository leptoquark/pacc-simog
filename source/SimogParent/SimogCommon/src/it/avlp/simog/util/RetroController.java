package it.avlp.simog.util;

import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;

/*
 * classe per il controllo del comportamento in modalità retrocompatibile 
 */
public class RetroController {
   
   /**
    * Verifica se il lotto rientra nella casistica per cui si deve utilizzare la soglia 
    * ribassata per il controllo del flusso di acquisizione
    * 
    * se la mev è attiva e la data di pubblicazione è >= 29/10/2013 allora il lotto rientra
    * nella nuova logica
    * 
    * @param dataPubb
    * @return boolean
    */
   public static boolean is3027_SOGLIA(String dataPubb){
      
      boolean retVal = false;
         if(Costanti.DATA_NUOVA_SOGLIA.compareTo(dataPubb) <= 0)
            retVal = true;
      return retVal;      
   }
}