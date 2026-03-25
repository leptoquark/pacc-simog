package it.avlp.simog.db;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.RetroController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ppientini
 * Variabili per l'attivazione delle nuove funzionalita' richieste e condizionare la logica del sistema
 * ATTENZIONE: l'attivazione arbitraria puo' portare a comportamenti imprevisti e corruzione della base dati
 */
public class SimogFlags {

	static boolean flagNoDate;  // impostato dal massloader
	static boolean fromWS;		// impostato dai WS per condizionare alcune regole di validazione
	static boolean fromWeb;		// impostato dal Web per condizionare alcune regole di validazione
	static boolean fromMassLoader; //impostato dal massloader per ticket ALM #3529

	

//  !*!*!*!*!*! codice per elaborazione BOLZANO, spegne i validatori e crea CIG se non esistono	
// public static boolean isBOLZANOActive(){
//    return false;
//}
	
// public static boolean isBOLZANODummyValidatorActive(){
//    return false;
//}
	
	
   public SimogFlags() {
      super();
   }
   
   //**************************************************************
   // *** da qui in poi flag nuovi sviluppi da rilasciare
   //**************************************************************


   //**************************************************************
   // *** da qui in poi flag rilasciati per verifica
   //**************************************************************

   
   //**************************************************************
   // *** da qui in poi flag sospesi 
   //**************************************************************
   
   /*** RILASCIO 3.03.0  RF-WEB_GL.02 (Gestione evoluta semaforo AVPass) */
   public static boolean is3030_RFWEBGL02Active(){
      return false;  // non ancora implementato lato AVCPASS
   }   

   
   //**************************************************************
   // *** da qui in poi flag consolidati e rilasciati in esercizio, da non disattivare!
   //**************************************************************

   // prendo la data dal sistema operativo e non da Oracle, per vedere se si risolvono i deadlock
   public static boolean isDEBUG_GETDATE(){
      return true;  
   }

   /*** RILASCIO 3.03.5.0  RF-WEB_GL.01 (Enti aggregatori) */
   public static boolean is30350_RFWEBGL01Active(){
      return true;  
   }
   public static boolean is30350_RFWSGL01Active(){
      return true;  
   }
   // chiesto da Fuligni di optare per multi scelta, richiede comunque un rilascio
   // 17 febbraio Obino dice di ripristinare
   public static boolean is30350_UNACATEGActive(){
      return false;  
   }


   /*** RILASCIO 3.03.0  RF-WEB_GL.00 (Pubblicazione Gara - Procedura ristretta o negoziata) */
   public static boolean is3030_RFWEBGL00Active(){
      return true;
   }  
   
   /*** INT87  RF-SIMOG_WEB.01 (NUOVO FLAG IN INSERIMENTO GARA) */
   public static boolean isINT87_RFSIMOGWEB01Active(){
      return true;
   }

   /*** TRITTO uso di altro oggetto per trovare il tipo_sa */
   public static boolean isNUOVO_TIPOSAActive(){
      return true;
   }
   
   /*** INT87  RF-SIMOG_WS.01 (NUOVO FLAG IN INSERIMENTO GARA) */
   public static boolean isINT87_RFSIMOGWS01Active(){
      return true;
   }

   /*** INT85  RF-WEB_GL.01 (INSERIMENTO GARA) */
   public static boolean isINT85_RFWEBGL01Active(){
      return true;
   }
   
   /*** RILASCIO 3.03.1.2  RF-WEB_GL.04 (Integrazione dati CUP) */
   public static boolean is3031_RFWEBGL04Active(){
      return true;
   }

   /*** RILASCIO 3.03.0  RF-WEB_SC.00 (Valorizzazione campi scheda "Adesione") */
   public static boolean is3030_RFWEBSC00Active(){
      return true;
   }
   
   /*** RILASCIO 3.03.0  RF-WEB_GL.03 (Interrogazione CIG non di competenza) */
   public static boolean is3030_RFWEBGL03Active(){
      return true;
   }
   
   /*** RILASCIO 3.03.0  RF-WS_GL.03 (Interrogazione CIG non di competenza su WS) */
   public static boolean is3030_RFWSGL03Active(){
      return true;
   }

   /*** OKRETRO RILASCIO 3.03.1  RNF-DB_GL.00 ( NUOVA SEZIONE "TIPOLOGIA LAVORO" SU SCHEDA LOTTO) */
   public static boolean is3031_RFWEBGL00Active(){
      return true;
   }   

   /*** RILASCIO 3.03.1  RNF-DB_GL.01 (Nuove informazioni tipologie lotto e CUP) */
   //   public static boolean is3031_RNFDBGL01Active(){
   //      return true;  
   //   }
   
   /*** RILASCIO 3.03.1  RF-WEB_GL.02 (Gestione CUP) */
   public static boolean is3031_RFWEBGL02Active(){
      return true;
   }
   
   /*** OKRETRO RILASCIO 3.03.1  RF-CM_VL.03 (Rilassamento controllo strumento di programmazione */
   public static boolean is3031_RFCMVL03Active(){
      return true;
   }
   

   /*** RILASCIO 3.04.2 (by Technis Blu)
    * Modifica etichette tipologiche 
    */
   public static boolean is3042Active() {
	   return true;
   }
   
   /*** RILASCIO 3.04.3 (by Technis Blu)
    * CUC-3.04.3
    */
	public static boolean is3043Active() {
		return true;
	}
 
   /*** (Gara esclusa dall'acquisizione obbligatoria dei requisiti ai fini AVCpass)
    l'implementazione web esiste dalla 3.02.9.5 in poi (anticipata da AVCP)
    i ws sono attivi completamente dalla 3.03.0.0 attenzione esiste ancora il 
    prepopolamento del flag se si proviene dai WS */
   public static boolean is3031_ESCL_AVCPASS(){
      return true;
   }

   /*** RILASCIO 3.02.9.2  MAC01 (modifica soglia schede a 40.000 esatte) */
   public static boolean is30292_MAC01Active(){
      return true;
   } 
   
   /*** RILASCIO 3.02.9  MAC01 (preimpostazione flag avvalimento su requisiti "F" e "OM") */
   public static boolean is3029_MAC01Active(){
      return true;
   } 

	/*** RILASCIO 3.02.8  RF-WEB_SC.00 (GESTIONE E PROTEZIONE DATI PROVENIENTI DA AVCPASS   ) */
   public static boolean is3028_RFWEBSC00Active(){
      return true;
   } 
	
    /*** RILASCIO 3.02.8  RF-WEB_GL.00 (scelta contraente personalizzata) */
    public static boolean is3028_RFWEBGL00Active(){
       return true;
    }	

    /*** RILASCIO 3.02.8  RF-WEB_GL.07 (GESTIONE SEMAFORO AVPASS) */
    public static boolean is3028_RFWEBGL07Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RF-WEB_GL.09 (presa in carico gara web ) */
    public static boolean is3028_RFWEBGL09Active(){
       return true;
    } 
    
    /*** RILASCIO 3.02.8  RF-WS_SC.00 (ws presa in carico dati comuni e gara) */
    public static boolean is3028_RFWSSC00Active(){
       return true;
    } 
    
    
    // rilasciati fase 1 19.04.2013
    /*** RILASCIO 3.02.8  RF-WS_GL.01 (integrazione consultagara) */
    public static boolean is3028_RFWSGL01Active(){
       return true;
    } 
  
    /*** RILASCIO 3.02.8  RF-DB_DT.01 (Cancellazione fisica dei requisiti) */
    public static boolean is3028_RNFDBDT01Active(){
       return true;
    } 
    
    /*** RILASCIO 3.02.8  NRF-DB_DT.04 (Classi di importo) */
    public static boolean is3028_NRFDBDT04Active(){
       return true;
    } 

     /*** RILASCIO 3.02.8  RF-WEB_GL.05 (Raggruppamento requisiti per tipologia nella combo) */
    public static boolean is3028_RFWEBGL05Active(){
       return true;
    }     
    
    /*** RILASCIO 3.02.8  RF-WEB_GL.06 (Controlli aggiuntivi sui requisiti) */
   public static boolean is3028_RFWEBGL06Active(){
      return true;
   } 
   
    /*** RILASCIO 3.02.8  RF-WEB_SC.01 (Controlli bloccanti sulle date) */
    public static boolean is3028_RFWEBSC01Active(){
       return true;
    } 
    
    /*** RILASCIO 3.02.8  RF-WEB_GL.01 (ATTIVAZIONE GESTIONE REQUISITI SOLO PER GARE INTERESSATE) */
    public static boolean is3028_RFWEBGL01Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RF-WEB_GL.02 (BLOCCO GESTIONE REQUISITI DOPO PUBBLICAZIONE) */
    public static boolean is3028_RFWEBGL02Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RF-WEB_SC.02 (controlli su giorni scostamento e giorni proroga) */
    public static boolean is3028_RFWEBSC02Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RNF-DB_DT.00 (COMPORTAMENTO DELLA FUNZIONE SBLOCCA GARA RISPETTO AI  REQUISITI) */
    public static boolean is3028_RNFDBDT00Active(){
       return true;
    }
    
    /*** RILASCIO 3.02.8  RNF-DB_DT.03 (SCRITTURA OCCORRENZE DOCUMENTI REQUISITI PER TUTTI I CIG) */
    public static boolean is3028_RNFDBDT03Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RF-WEB_GL.08 (CONTROLLO SU DATA PUBBLICAZIONE E SCADENZA OFFERTE) */
    public static boolean is3028_RFWEBGL08Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RF-WEB_GL.04 (COMPORTAMENTO FASE DI PUBBLICAZIONE  RISPETTO AI REQUISITI GARA) */
    public static boolean is3028_RFWEBGL04Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RF-WEB_AN.00 ( RAPPRESENTANTE LEGALE ESTERO) */
    public static boolean is3028_RFWEBAN00Active(){
       return true;
    }

    /*** RILASCIO 3.02.8  RF-WEB_GL.03 (GESTIONE DEGLI ATTRIBUTI PER I REQUISITI DI ORDINE GENERALE) */
    public static boolean is3028_RFWEBGL03Active(){
       return true;
    }

    /*****
     *    ATTENZIONE: il flag seguente deve essere attivo per il massloader solo dal 1 luglio 2013, per il web
     *                invece deve essere attivo dalla 3.02.7 in poi 
     *
    *** RILASCIO 3.02.7  RF-WEB_SC.00 (nuovo importo per fattispecie sottosoglia) */
    public static boolean is3027_SOGLIAActive(){
       return true;
    }

   /*** RILASCIO 3.02.6 compatibilita' IE9 non necessita di flags! */
    
    /*** RILASCIO 3.02.5  RF-WEB_GL.00 e RF-WS_GL.00 (gestione requisiti gara) */
    public static boolean is3025_REQUISITIActive(){
       return true;
    }
     
    /*** RILASCIO 3.02.5 RF-WEB_GL.02 (ora scadenza presentazione offerte)  */
    public static boolean is3025_RFWEBGL02Active(){
       return true;
    }
    
    /*** RILASCIO 3.02.4 disattivazione operation di perfezionamento */
    public static boolean is3024_NOPERFActive(){
       return true;
    }
    
    /*** RILASCIO 3.02.4 IAM  */
    public static boolean is3024IAMActive(){
       return true;
    }

    /*** RILASCIO 3.02.4 OSSN (nuovo profilo da gestire) */ 
    public static boolean isOSSNActive(){
       return true;
    }
    // era prevista per il 5 ma se non la attivo sulle jsp riappare il campo!
    /*** RILASCIO 3.02.5 RF-WEB_GL.01 (inibizione importo indeterminato) */ 
    public static boolean is3025_RFWEBGL01Active(){
       return true;
    }
    
    /*** RILASCIO 3.02.4 (le voci sono rimaste con la codifica vecchia per non toccare il codice) */

    // *** primi 5 requisiti deploy su nuovo ambiente di rilascio
    
    /** 3.02.4 requisito RF-WEB_SC.00 (nuovo flusso) */
    public static boolean is30233_RFWEBSC00Active(){
        return true;
    }

    /*** 3.02.4 requisito RF-WEB_GL.06 (art.esclusione OOCC) */
    public static boolean is30233_RFWEBGL06Active(){
        return true;
    }

    /*** 3.02.4 requisito RNF-WEB_GL.04 (Modifica etichetta "somma urgenza") */
    public static boolean is30233_NRFWEBGL04Active(){
        return true;
    }

    /*** 3.02.4 requisito RF-WEB_SC.06 (modifica tasto nuova aggiudicazione) */
    public static boolean is30233_RFWEBSC06Active(){
        return true;
    }

    /*** 3.02.4 requisito RF-WEB_GL.02 (Appalti con ripetizioni) */
    public static boolean is30233_RFWEBGL02Active(){
        return true;
    }
 
    // altri tre requisiti per il secondo rilascio (medio)
    
    /*** 3.02.4 requisito RF-WEB_SC.05 (Pannellino info multilotto) */
    public static boolean is30233_RFWEBSC05Active(){
        return true;
    }
    
    /*** 3.02.4 requisito RF-WEB_SC.04 (controlli multilotto) */
    public static boolean is30233_RFWEBSC04Active(){
        return true;
    }
    
    /*** 3.02.4 requisito RF-ML_VS.00 (funzione variazione SA) */
    public static boolean is30233_RFMLVS00Active(){
        return true;
    }
    
    /// ultimi requisiti terzo rilascio

    /*** 3.02.4 requisito RF-WEB_GL.03 (visualizzazione CIG acquisiti) */
    public static boolean is30233_RFWEBGL03Active(){
        return true;
    }
    
    /*** 3.02.4 requisito RF-WEB_GL.05 (paginazione dettaglio gara) */
    public static boolean is30233_RFWEBGL05Active(){
        return true;
    }

    /*** 3.02.4 requisito RF-WEB_SC.07 (modifica controlli variante) */
    public static boolean is30233_RFWEBSC07Active(){
        return true;
    }   

    /*** ricalcolo contributo (rilascio previsto 3.02.3.3 */
    public static boolean isGRIGLIA_CONTRIBUTO(){
       return true;
    }
    
    // *** RILASCIO 2
    /*** 3.02.3.2 requisito RF-WEB_SC.02 (controlli adesione) */
    public static boolean is30230_RFWEBSC02Active(){
        return true;
    }

    /*** 3.02.3.2 requisito RF-WEB_GL.00 (ripristino gara e lotti) */
    public static boolean is30230_RFWEBGL00Active(){
        return true;
    }
    

    // *** RILASCIO 1
    /*** 3.02.3.1 requisito RF-WS_OP.00 (filtro istat nuts) */
    public static boolean is30230_RFWSOP00Active(){
        return true;
    }

    /*** 3.02.3.1 requisito RF-ML_SC.00 (revisione controlli bloccanti) */
    public static boolean is30230_RFMLSC00Active(){
        return true;
    }
    
    /*** 3.02.3.1 requisito NRF-WEB_XX.00 (errore calcolo contributo) */
    public static boolean is30230_NRFWEBXX00Active(){
        return true;
    }

    /*** 3.02.3.1 requisito RF-WEB_GL.01 (ricerca per amministrazione) */
    public static boolean is30230_RFWEBGL01Active(){
        return true;
    }

    /*** 3.02.3.1 requisito RF-WEB_SC.03 (rich.annull dirette) */
    public static boolean is30230_RFWEBSC03Active(){
        return true;
    }

    
    
    /*** variazioni anagrafiche massloader */
    public static boolean isVarAnagMLActive(){
        return true;
    }

	/*** 3.02.2.0 motivi variazione corso d'opera  */
	public static boolean is30220Active(){
		return true;
	}
	
	/*** pulsanti per la variazione anagrafica WEB */
	public static boolean isVarAnagActive(){
		return true;
	}
	
	/*** impostato per abilitare la gestione organi costituzionali */
	public static boolean isOrganiCostActive(){
		return true;
	}

	/*** varie 3.02.1.6  */
	public static boolean is30216Active(){
		return true;
	}
	
	/***  funzione esportaElenco */
	public static boolean isEsportaCSV(){
		return true;
	}
	
	public static boolean isFlagNoDate() {
		return flagNoDate;
	}

	public static void setFlagNoDate(boolean flagNoDate) {
		SimogFlags.flagNoDate = flagNoDate;
	}

	public static boolean isFromWS() {
		return fromWS;
	}

	public static void setFromWS(boolean fromWS) {
		SimogFlags.fromWS = fromWS;
	}
	
	public static boolean isFromWeb() {
		return fromWeb;
	}

	public static void setFromWeb(boolean fromWeb) {
		SimogFlags.fromWeb = fromWeb;
	}
	
	//TICKET ALM #3529
	public static boolean isFromMassLoader() {
		return fromMassLoader;
	}
	
	public static void setFromMassLoader(boolean fromMassLoader) {
		SimogFlags.fromMassLoader = fromMassLoader;
	}
	//FINE TICKET ALM #3529
	
	//TICKET ALM #2847
	public static boolean isAccordoQuadroOrConvenzione(int idModReal) {
		return idModReal==Costanti.MODOREAL_ACCORDO || idModReal==Costanti.MODOREAL_ACCORDO_QUADRO || idModReal==Costanti.MODOREAL_CONVENZIONE;
	}
	//FINE TICKET ALM #2847
	
	public static boolean isSvolgimentoAccordoQuadro(int idStrumentoSvolgimento) {
		return idStrumentoSvolgimento==Costanti.SVOLGIMENTO_ACCORDO_QUADRO;
	}
	
	public static boolean isSvolgimentoAllowed(int idModReal) {
		return idModReal==3||idModReal==4||idModReal==5||idModReal==8||idModReal==12 || SimogFlags.isAccordoQuadroOrConvenzione(idModReal);
	}
	
	//TICKET ALM - 3.04.4
	public static boolean isAdesioneAccordoQuadro(int idModReal) {
			return idModReal==Costanti.MODOREAL_ADESIONE || idModReal==Costanti.MODOREAL_ADESIONE_NOCOMPET;
	}
	//FINE TICKET ALM - 3.04.4
	
	//3.04.8 34190 fix
	public static boolean isAdesioneConcessione(int idModReal) {
			return idModReal==Costanti.MODOREAL_CONCESSIONE || idModReal==Costanti.MODOREAL_CONCESSIONE_NOCOMPET;
	}
	//FINE 3.04.8 34190 fix
	
	public static String toStringa(){
	   
	   String out = "";
	   
	   SimogFlags sf = new SimogFlags();
//	   for (Field field : sf.getClass().getDeclaredFields()) {
//	       field.setAccessible(true);
//	       String name = field.getName();
//	       Object value = "";
//         try {
//            value = field.get(sf);
//         } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//         } catch (IllegalAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//         }
//	       out = out.concat("\n$1: $2".replace("$1", name).replace("$2", value.toString()));
//	   }
	   
      for (Method field : sf.getClass().getDeclaredMethods()) {         
         field.setAccessible(true);
         String name = field.getName();
         Object value = "";
         if("is".equals(name.substring(0, 2))){
           try {
               value = field.invoke(sf, null);
            } catch (InvocationTargetException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           } catch (IllegalArgumentException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           } catch (IllegalAccessException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           }
            out = out.concat("$1: $2\t".replace("$1", name).replace("$2", value.toString()));
         }
      }

     return out;
	   
	}

	public static String checkHighlightField(HashMap<String, String> fieldToHighlight, String key) {
		
		return !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey(key)? "style=\"color: red;\"" : "";
	}
   public static String checkHighlightField(HashMap<String, String> fieldToHighlight, String key, String alternativeColor) {
		
		return !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey(key)? "style=\"color: red;\"" : "style=\"color: "+alternativeColor+";";
	}
   

}
