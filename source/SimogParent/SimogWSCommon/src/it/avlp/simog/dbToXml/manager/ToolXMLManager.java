package it.avlp.simog.dbToXml.manager;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.xmlbeans.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;

public class ToolXMLManager {

		/* spacciatore */
	
		/******************************************************************
		 * verifica l'esistenza nell'oggetto dell'elemento indicato da cf
		 * @param o Object 
		 * @param cf String 
		 * @return boolean
		 */
		public static boolean checkExist(Object o,String cf,String codice_paese){
			if(o instanceof ResponsabiliType){
				return ToolXMLManager.checkR(((ResponsabiliType)o),cf);
			}else if(o instanceof AggiudicatariType){
				return ToolXMLManager.checkA(((AggiudicatariType)o),cf,codice_paese);
			}return false;
		}
		
		/********************************************************
		 * Verifica la presensa del Responsabile
		 * @param rt ResponsabiliType
		 * @param cf String
		 * @return boolean
		 */
		public static boolean checkR(ResponsabiliType rt,String cf){
			ResponsabileType[] r1t = rt.getResponsabileArray();
			for(int i = 0; i<r1t.length; i++){
				ResponsabileType resp = r1t[i];
				if(cf.equalsIgnoreCase(resp.getCODICEFISCALERESPONSABILE())){
					return true;
				}
			}
			return false;
		}
		/*********************************************************
		 * Verifica la presenza dell'aggiudicatario
		 * @param at AggiudicatariType 
		 * @param cf String 
		 * @return boolean
		 */
		public static boolean checkA(AggiudicatariType at,String cf,String codice_paese){
			AggiudicatarioType[] a1t = at.getAggiudicatarioArray();
			for(int i = 0; i<a1t.length; i++){
				AggiudicatarioType agg = a1t[i];
				if(codice_paese == null){codice_paese = "";}				
				if(agg.getCODICEFISCALEAGGIUDICATARIO().equalsIgnoreCase(cf) &&
						agg.getCODICESTATO().equals(codice_paese)){
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Si e' deciso di sovrascrivere le anagrafiche, invece di non scrivere se risulta gia presente
		 * l'accoppiata codice fiscale - codice paese, per essere sicuri di avere l'ultima anagrafica (ultima modifica),
		 * nel caso ad esempio di piu di una aggiudicazione si assume che la prima non sia valida o deserta, e che la
		 * seconda sia quella valida, di conseguenza dovrebbe risultare(ritornare) l'anagrafica puntata dall'ultima 
		 * aggiudicazione, da cui sovrascrittura.
		 * 
		 * @param ait
		 * @param spb
		 * @return
		 */
		public static String sovrascriviAnagraficaPartecipante(AggiudicatariType ait, SoggettoPartecipanteBean spb){
			
			AggiudicatarioType[] arrayDiAggiudicatari = ait.getAggiudicatarioArray();
			int posizione = -1;
			String cf = spb.getCodiceFiscale();
			String codice_paese = spb.getId_stato();
			
			for(int i = 0; i < arrayDiAggiudicatari.length; i++){
				AggiudicatarioType agg = arrayDiAggiudicatari[i];
				if(codice_paese == null){codice_paese = "";}				
				if(agg.getCODICEFISCALEAGGIUDICATARIO().equalsIgnoreCase(cf) &&
						agg.getCODICESTATO().equals(codice_paese)){
					posizione = i;
				}
			}
			String e = null;
			if(posizione >= 0){
				AggiudicatarioType aggiudicatarioDaSovrascrivere = ait.getAggiudicatarioArray(posizione);
				//setting methods
				aggiudicatarioDaSovrascrivere.setCAMERACOMMERCIO(spb.getCameraCommercio());
				aggiudicatarioDaSovrascrivere.setCAP(spb.getCap());				
				aggiudicatarioDaSovrascrivere.setCFRAPPRESENTANTE(spb.getCfRappresentante());
				aggiudicatarioDaSovrascrivere.setCODICEFISCALEAGGIUDICATARIO(spb.getCodiceFiscale());
				aggiudicatarioDaSovrascrivere.setCITTA(spb.getCitta());
				aggiudicatarioDaSovrascrivere.setCIVICO(spb.getCivico());				
				aggiudicatarioDaSovrascrivere.setCOGNOME(spb.getCognome());
				aggiudicatarioDaSovrascrivere.setDENOMINAZIONE(spb.getDenominazione());			
				aggiudicatarioDaSovrascrivere.setINDIRIZZO(spb.getIndirizzo());
				aggiudicatarioDaSovrascrivere.setNOME(spb.getNome());
				aggiudicatarioDaSovrascrivere.setPARTITAIVA(spb.getPartitaIva());
				aggiudicatarioDaSovrascrivere.setPROVINCIA(spb.getProvincia());
				/** adds 26092008 */
				if(spb.getId_stato() != null && ! Costanti.CODICE_STATO_ITALIANO.equals(spb.getId_stato())){
					aggiudicatarioDaSovrascrivere.setCODICESTATO(spb.getId_stato());
					//caso in cui la flag esteri � settata correttamente
					if(spb.getFlagEsteri() != null && !"".equals(spb.getFlagEsteri())){
						aggiudicatarioDaSovrascrivere.setSOGGETTOESTERO(FlagSNType.Enum.forString(spb.getFlagEsteri()));
					//caso in cui la flag esteri non risulta settata
					}else{
						aggiudicatarioDaSovrascrivere.setSOGGETTOESTERO(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_SI));
					}
					e = spb.getId_stato();
				}else{
					aggiudicatarioDaSovrascrivere.setCODICESTATO("");
					aggiudicatarioDaSovrascrivere.setSOGGETTOESTERO(FlagSNType.Enum.forString("N"));
					e = "";
				}
				//sovrascrivo la posizione con il bean modificato
				ait.setAggiudicatarioArray(posizione, aggiudicatarioDaSovrascrivere);
				return e;
			}return "";		
		}
		
		/**
		 * @see sovrascriviAnagraficaPartecipante
		 * 
		 * @param rit
		 * @param srb
		 */
		public static void sovrascriviAnagraficaResponsabile(ResponsabiliType rit, SoggettoResponsabileBean srb){
			
			ResponsabileType[] arrayDiResponsabili = rit.getResponsabileArray();
			String codice_fiscale = srb.getCodiceFiscaleResponsabile();
			int posizione = -1;
			for(int i = 0; i < arrayDiResponsabili.length; i++){
				ResponsabileType res = arrayDiResponsabili[i];
				if(res.getCODICEFISCALERESPONSABILE().equalsIgnoreCase(codice_fiscale)){
					posizione = i;
				}
			}
			
			if(posizione >= 0){
				ResponsabileType rp = rit.getResponsabileArray(posizione);
				rp.setCODICEFISCALERESPONSABILE(srb.getCodiceFiscaleResponsabile());
				rp.setINDIRIZZO(srb.getIndirizzo());
				rp.setNOME(srb.getNome());
				rp.setTELEFONO(srb.getTelefono());
				rp.setEMAIL(srb.getEmail());
				rp.setFAX(srb.getFax());
				rp.setCODICEISTATCOMUNE(srb.getComuneIstat());
				rp.setCOGNOME(srb.getCognome());
				rp.setCAP(srb.getCap());
				// sovrascrivo
				rit.setResponsabileArray(posizione, rp);
			}
		}
		
		
}
