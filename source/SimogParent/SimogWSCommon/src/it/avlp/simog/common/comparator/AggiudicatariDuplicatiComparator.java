package it.avlp.simog.common.comparator;

import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;

import java.util.Comparator;

public class AggiudicatariDuplicatiComparator implements Comparator<AggiudicatarioType> {

   public int compare(AggiudicatarioType o1, AggiudicatarioType o2) {
      
      boolean stessoCF = o1.getCODICEFISCALEAGGIUDICATARIO().equalsIgnoreCase(o2.getCODICEFISCALEAGGIUDICATARIO());
      
      String cf1 = !o1.getCODICESTATO().isEmpty() ? o1.getCODICESTATO() : Costanti.CODICE_STATO_ITALIANO;
      String cf2 = !o2.getCODICESTATO().isEmpty() ? o2.getCODICESTATO() : Costanti.CODICE_STATO_ITALIANO;
      boolean stessoCodiceStato = cf1.equalsIgnoreCase(cf2);
      
      return stessoCF && stessoCodiceStato ? 0 : 1;
   }
}
