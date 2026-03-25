package it.anticorruzione.ted.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.ws.massload.xmlbeans.CondizioniPartecipazioneType;
import it.avlp.simog.ws.massload.xmlbeans.DatiProceduraType;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.EntitaAppaltoType;
import it.avlp.simog.ws.massload.xmlbeans.InfoAmministrativeType;

public class DeltaGaraValidator {

	public static String validate(DeltaGaraTED deltaGaraTED, Gara gara, List<Lotto> listaLotti) {
		
		String res = DeltaGaraValidator.validateEntitaAppalto(deltaGaraTED.getENTITAAPPALTO(), gara.getNumeroLotti()>1);
		
		if(deltaGaraTED.getINFOAGGIUNTIVE().getValue().equals("2") && deltaGaraTED.getALTROINDIRIZZOIA()==null)
			res+="SERVICE_ERROR_003 – Altro indirizzo per ulteriori info su documentazione gara – Campo non richiesto\n";
		
		if(deltaGaraTED.getURLVERSIONEELETTRONICA()!=null && !UtilityClass.isUrlValid(deltaGaraTED.getURLVERSIONEELETTRONICA()))
			res+="SERVICE_ERROR_004 – indirizzo invio offerte e domande – URL non valida\n";
		
		if(deltaGaraTED.getURLSTRUMENTI()!=null && !UtilityClass.isUrlValid(deltaGaraTED.getURLSTRUMENTI()))
			res+="SERVICE_ERROR_004 – Indirizzo strumenti – URL non valida\n";
		
		if(deltaGaraTED.getTIPOAMMAGG().getValue().equals("7") && deltaGaraTED.getALTROTIPOAMMAGG()==null)
			res+="SERVICE_ERROR_004a – Altro tipo di amministrazione aggiudicatrice – il campo è obbligatorio\n";
		else if(!deltaGaraTED.getTIPOAMMAGG().getValue().equals("7") && deltaGaraTED.getALTROTIPOAMMAGG()!=null)
			res+="SERVICE_ERROR_005 – Altro tipo di amministrazione aggiudicatrice – campo non richiesto\n";
			
		if(deltaGaraTED.getDATIAMMAGGIUDICATRICE() != null && 
		   deltaGaraTED.getDATIAMMAGGIUDICATRICE().size() > 1) {
			
			if(deltaGaraTED.getNORMATIVEAPPCONGIUNTO() == null || "".equals(deltaGaraTED.getNORMATIVEAPPCONGIUNTO()))
			    res += "SERVICE_ERROR_005a - Nel caso di appalto congiunto che coinvolge diversi paesi indicare le normative nazionali sugli appalti in vigore - il campo è obbligatorio\n";
		
			Map<String,String> datiAmmAgg = new HashMap<String,String>();
			
			for(it.avlp.simog.ws.massload.xmlbeans.AddrS1Type ammAgg : deltaGaraTED.getDATIAMMAGGIUDICATRICE()) {
				String nationalId = ammAgg.getNATIONALID();
				if(!datiAmmAgg.containsKey(nationalId)) {
					datiAmmAgg.put(nationalId, nationalId);
				} else {
					res += "SERVICE_ERROR_103 - E' presente una o piu' amministrazioni aggiudicatrici con lo stesso codice fiscale\n";
				}
			}
		
		} else if(deltaGaraTED.getDATIAMMAGGIUDICATRICE() != null && 
				deltaGaraTED.getDATIAMMAGGIUDICATRICE().size() == 1 &&
				(deltaGaraTED.getNORMATIVEAPPCONGIUNTO() != null && !"".equals(deltaGaraTED.getNORMATIVEAPPCONGIUNTO()))
				)
			res += "SERVICE_ERROR_005b - Nel caso di appalto congiunto che coinvolge diversi paesi indicare le normative nazionali sugli appalti in vigore - campo non richiesto\n";

		
		if(deltaGaraTED.getSETTOREPRINCIPALE().getValue().equals("11") && deltaGaraTED.getALTROSETTOREPRINCIPALE()==null)
			res+="SERVICE_ERROR_006  - Altro settore – il campo è obbligatorio\n";
		else if(!deltaGaraTED.getSETTOREPRINCIPALE().getValue().equals("11") && deltaGaraTED.getALTROSETTOREPRINCIPALE()!=null)
			res+="SERVICE_ERROR_007  - Altro settore – campo non richiesto\n";
		
		if(Costanti.FLAG_VALORE_SI.equals(deltaGaraTED.getALTREINFO().getAPPALTORINNOVABILE().value()) 
				&& deltaGaraTED.getALTREINFO().getTEMPOSTIMATOPROSSIMIBANDI()==null)
			res+="SERVICE_ERROR_010  - Tempo stimato prossimi avvisi – il campo è obbligatorio\n";
		
		res+=DeltaGaraValidator.validateCondizioniPartecipazione(deltaGaraTED.getCONDIZIONIPARTECIPAZIONE(),deltaGaraTED.getENTITAAPPALTO().getTIPOCONTRATTOAPPALTO());
		
		res+=DeltaGaraValidator.validateDatiProceduraType(deltaGaraTED.getDATIPROCEDURA(), 
															listaLotti,
															gara.getIdModoReal(), 
															gara.getIdSvolgimento(),
															deltaGaraTED.getAPPALTOCC().value());
		
		res+=DeltaGaraValidator.validateInfoAmministrativeType(deltaGaraTED.getINFOAMMINISTRATIVE(),deltaGaraTED.getDATIPROCEDURA().getTIPOPROCEDURA());
		
		return res;
	}
	


	private static String validateInfoAmministrativeType(InfoAmministrativeType info,
			String tipocontrattoappalto) {
		String res="";
		
		if(info.getPERIODOVALIDITAOFFERTE()==null && info.getMESIVALIDITAOFFERTE()==null) 
		   res+="SERVICE_ERROR_081a – data vincolo offerta e validità offerta in mesi – Un valore richiesto\n";
		else if(info.getPERIODOVALIDITAOFFERTE()!=null && info.getMESIVALIDITAOFFERTE()!=null)
			res+="SERVICE_ERROR_081 – data vincolo offerta e validità offerta in mesi – inserire un solo valore\n";
		
		if(tipocontrattoappalto.equals("1")) {
			if(info.getDATAAPERTURAOFFERTE()==null)
			   res+="SERVICE_ERROR_082 – Data aperture offerte – il campo è obbligatorio\n";
			if(info.getORAAPERTURAOFFERTE()==null)
				res+="SERVICE_ERROR_084 – Ora aperture offerte – il campo è obbligatorio\n";
		} else if(!tipocontrattoappalto.equals("1")) {
			if(info.getDATAAPERTURAOFFERTE()!=null)
			   res+="SERVICE_ERROR_083 – Data aperture offerte – campo non richiesto\n";
			if(info.getORAAPERTURAOFFERTE()!=null)
				res+="SERVICE_ERROR_085 – Ora aperture offerte – campo non richiesto\n";
			
			if(info.getLUOGOAPERTURAOFFERTE()!=null)
				res+="SERVICE_ERROR_086 – Luogo aperture offerte – campo non richiesto\n";
			if(info.getPERSONEAPERTURAOFFERTE()!=null)
				res+="SERVICE_ERROR_087 – Informazioni relative alle persone ammesse e alla procedura di aperture – campo non richiesto\n";
		}
		
		return res;
	}



	private static String validateDatiProceduraType(DatiProceduraType datiprocedura, 
													List<Lotto> listaLotti, 
													Long idModoReal, 
													Long idSvolgimento, String flagAppaltoCC) {
		String res = "";
		String tipoProc = datiprocedura.getTIPOPROCEDURA();
		boolean checkSceltaContraente=false;
		for(Lotto lotto : listaLotti) {
			checkSceltaContraente = UtilityClass.checkTipoSceltaContraente(tipoProc, lotto.getIdSceltaContraente());
			if(checkSceltaContraente)
				break;
		}
		
		if(!checkSceltaContraente)
			res += "SERVICE_ERROR_036a – Tipo di procedura – il lotto (o almeno uno dei lotti in caso di multilotto) deve avere la scelta del contraente coincidente con il tipo di procedura\n";
		
		if(tipoProc.equals("1") || tipoProc.equals("2") || tipoProc.equals("3")) {
			if(datiprocedura.getFLAGPROCEDURAACCELLERATA()==null)
				res+="SERVICE_ERROR_036b – procedura accellarata – il campo è obbligatorio\n";
		} else if(datiprocedura.getFLAGPROCEDURAACCELLERATA()!=null)
			res+="SERVICE_ERROR_037 – Procedura accellerata – valore non richiesto\n";
		
		if(datiprocedura.getFLAGPROCEDURAACCELLERATA()!=null) {
		if(Costanti.FLAG_VALORE_SI.equals(datiprocedura.getFLAGPROCEDURAACCELLERATA().value())) {
			if(datiprocedura.getMOTIVAZIONEPROCEDURAACCELLERATA()==null || "".equals(datiprocedura.getMOTIVAZIONEPROCEDURAACCELLERATA()))
				res+="SERVICE_ERROR_038 – Motivazione procedura accellerata – il campo è obbligatorio\n";
			} else if(Costanti.FLAG_VALORE_NO.equals(datiprocedura.getFLAGPROCEDURAACCELLERATA().value())) {
			if(datiprocedura.getMOTIVAZIONEPROCEDURAACCELLERATA()!=null)
				res+="SERVICE_ERROR_039 – Motivazione procedura accellerata – campo non richiesto\n";
			}
		}
		
		if(SimogFlags.isAccordoQuadroOrConvenzione(idModoReal.intValue())) {
			if(datiprocedura.getTIPOOPERATORIAQ()==null)
				res+="SERVICE_ERROR_040 – tipologia operatore – il campo è obbligatorio\n";
			else if(datiprocedura.getTIPOOPERATORIAQ().getValue().equals("2") && datiprocedura.getNUMMAXPARTECIPANTIAQ()==null)
				res+="SERVICE_ERROR_041 – Numero massimo partecipanti – il campo è obbligatorio\n";
			else if(!datiprocedura.getTIPOOPERATORIAQ().getValue().equals("2") && datiprocedura.getNUMMAXPARTECIPANTIAQ()!=null)
				res+="SERVICE_ERROR_041b – Numero massimo partecipanti – campo non previsto\n";
		} else {
			if(datiprocedura.getTIPOOPERATORIAQ()!=null)
				res+="SERVICE_ERROR_040b – tipologia operatore – campo non richiesto\n";
			if(datiprocedura.getNUMMAXPARTECIPANTIAQ()!=null)
				res+="SERVICE_ERROR_041b – Numero massimo partecipanti – campo non previsto\n";
		}
		if(Costanti.FLAG_VALORE_SI.equals(flagAppaltoCC) 
				&& Costanti.SVOLGIMENTO_SDA==idSvolgimento.intValue()
				&& datiprocedura.getALTRIACQUIRENTISISDINAMICO()==null)
			res+="SERVICE_ERROR_042 – SDA committenti – il campo è obbligatorio\n";
		
		if(datiprocedura.getREDUCTIONRECOURSE()!=null) {
		if(Costanti.FLAG_VALORE_SI.equals(datiprocedura.getREDUCTIONRECOURSE().value())) {
			if(tipoProc.equals("1") || tipoProc.equals("2"))
				res+="SERVICE_ERROR_042a – Riduzione soluzioni – campo non richiesto\n";
		}
		}
		
		if(datiprocedura.getAGGIUDICAZIONESENZANEGOZIAZIONE()!=null) {
		if(Costanti.FLAG_VALORE_SI.equals(datiprocedura.getAGGIUDICAZIONESENZANEGOZIAZIONE().value()))
			if(!tipoProc.equals("3"))
				res+="SERVICE_ERROR_042b – Facoltà di aggiudicare senza negoziazione – campo non richiesto\n";
		}
		
		if(idSvolgimento.intValue()==Costanti.SVOLGIMENTO_ASTA_ELETTRONICA) {
			if(datiprocedura.getNOTEASTAELETTRONICA()==null || "".equals(datiprocedura.getNOTEASTAELETTRONICA()))
				res+="SERVICE_ERROR_043 – Note asta elettronica – il campo è obbligatorio\n";
		}
		
		return res;
	}



	public static String validateEntitaAppalto(EntitaAppaltoType entitaAppalto, boolean multilotto) {
		String res = "";

		if(entitaAppalto.getCPVGARA().length()>=2) {
			String startCpv = entitaAppalto.getCPVGARA().substring(0,2);
			int intCpv = Integer.parseInt(startCpv);
			if(entitaAppalto.getTIPOCONTRATTOAPPALTO().equals("3")) {
				if(intCpv!=48 && intCpv>44)
					res+="SERVICE_ERROR_000 - CPV Gara : la CPV indicata non è coerente con il tipo contratto appalto (Forniture: CPV da 0 a 44 e che iniziano con 48. Lavori: CPV con 45. Servizi: da 49 a 98)";
			} else if(entitaAppalto.getTIPOCONTRATTOAPPALTO().equals("1") && intCpv!=45) 
				   res+="SERVICE_ERROR_000 - CPV Gara : la CPV indicata non è coerente con il tipo contratto (Forniture: CPV da 0 a 44 e che iniziano con 48. Lavori: CPV con 45. Servizi: da 49 a 98)";
			else  if(entitaAppalto.getTIPOCONTRATTOAPPALTO().equals("2"))  {
				if(intCpv < 49 || intCpv > 98)
					 res+="SERVICE_ERROR_000 - CPV Gara : la CPV prevalente indicata non è coerente con il tipo contratto (Forniture: CPV da 0 a 44 e che iniziano con 48. Lavori: CPV con 45. Servizi: da 49 a 98)";	
			}
	    }
		
		if(entitaAppalto.getMAXLOTTIPARTECIPAZIONE()==null && multilotto)
			res += "SERVICE_ERROR_062 – Modalità presentazione offerte in caso di più lotti – il campo è obbligatorio\n";
		else if(entitaAppalto.getMAXLOTTIPARTECIPAZIONE()!=null) {
			if(!multilotto)
			     res += "SERVICE_ERROR_063 – Modalità presentazione offerte in caso di più lotti – campo non previsto\n";
		
			if(entitaAppalto.getMAXLOTTIPARTECIPAZIONE().getValue().equals("2")) {
				if(entitaAppalto.getNUMMAXLOTTIPARTECIPAZIONE()==null)
					res += "SERVICE_ERROR_064 – Massimo numero di lotti per la quale si possono presentare offerte – il campo è obbligatorio\n";
			} else if(entitaAppalto.getNUMMAXLOTTIPARTECIPAZIONE()!=null)
				res += "SERVICE_ERROR_065 – Massimo numero di lotti per la quale si possono presentare offerte – campo non previsto\n";
		}
		
		if(multilotto && entitaAppalto.getNUMMAXLOTTIOFFERENTE()==null)
			res += "SERVICE_ERROR_066 – Massimo numero di lotti che possono essere aggiudicati a un offerente – il campo è obbligatorio\n";
		else if(!multilotto && entitaAppalto.getNUMMAXLOTTIOFFERENTE()!=null)
			res += "SERVICE_ERROR_067 – Massimo numero di lotti che possono essere aggiudicati a un offerente – campo non previsto\n";
		
		if(multilotto && entitaAppalto.getFLAGSAAGGGRUPPILOTTI()==null)
			res += "SERVICE_ERROR_068 – Combinazione lotti o gruppi di lotti – il campo è obbligatorio\n";
		else if(entitaAppalto.getFLAGSAAGGGRUPPILOTTI()!=null) {
			if(!multilotto)
			    res += "SERVICE_ERROR_069 – Combinazione lotti o gruppi di lotti – campo non previsto\n";
			
			if(Costanti.FLAG_VALORE_SI.equals(entitaAppalto.getFLAGSAAGGGRUPPILOTTI().value()) && 
					(entitaAppalto.getSAAGGGRUPPILOTTI()==null || "".equals(entitaAppalto.getSAAGGGRUPPILOTTI())))
				res += "SERVICE_ERROR_070 – Riferimenti a lotti da combinare – il campo è obbligatorio\n";
			else if(Costanti.FLAG_VALORE_NO.equals(entitaAppalto.getFLAGSAAGGGRUPPILOTTI().value()) && entitaAppalto.getSAAGGGRUPPILOTTI()!=null)
				res += "SERVICE_ERROR_071 – Riferimenti a lotti da combinare – campo non previsto\n";
		}
		
		
		return res;
	}
	
	
	private static String validateCondizioniPartecipazione(CondizioniPartecipazioneType cond, String tipoContratto) {
		String res ="";
		if(Costanti.FLAG_VALORE_NO.equals(cond.getCRITERIECONOMICI().value()) ) {
			
			if(cond.getELENCOCRITERIECONOMICI()==null || "".equals(cond.getELENCOCRITERIECONOMICI()))
			    res+="SERVICE_ERROR_025 - Breve elenco dei criteri – il campo è obbligatorio\n";
			if(cond.getLIVELLICRITERIECONOMICI()==null || "".equals(cond.getLIVELLICRITERIECONOMICI()))
				res+="SERVICE_ERROR_027 - Livelli minimi richiesti – il campo è obbligatorio\n";
			
		} else if(Costanti.FLAG_VALORE_SI.equals(cond.getCRITERIECONOMICI().value()) ) {
			if(cond.getELENCOCRITERIECONOMICI()!=null)
			    res+="SERVICE_ERROR_026 - Breve elenco dei criteri – campo non richiesto\n";
			if(cond.getLIVELLICRITERIECONOMICI()!=null)
				res+="SERVICE_ERROR_028 - Livelli minimi richiesti – campo non richiesto\n";
		}
		
       if(Costanti.FLAG_VALORE_NO.equals(cond.getCRITERITECNICI().value()) ) {
			
			if(cond.getELENCOCRITERITECNICI()==null || "".equals(cond.getELENCOCRITERITECNICI()))
			    res+="SERVICE_ERROR_029 - Breve elenco dei criteri – il campo è obbligatorio\n";
			if(cond.getLIVELLICRITERITECNICI()==null || "".equals(cond.getLIVELLICRITERITECNICI()))
				res+="SERVICE_ERROR_031 - Livelli minimi richiesti – il campo è obbligatorio\n";
			
		} else if(Costanti.FLAG_VALORE_SI.equals(cond.getCRITERITECNICI().value()) ) {
			if(cond.getELENCOCRITERITECNICI()!=null)
			    res+="SERVICE_ERROR_030 - Breve elenco dei criteri – campo non richiesto\n";
			if(cond.getLIVELLICRITERITECNICI()!=null)
				res+="SERVICE_ERROR_032 - Livelli minimi richiesti – campo non richiesto\n";
		}
       
       if(tipoContratto.equals("2") && cond.getFLAGPROFESSIONESERVIZI()==null)
    	   res+="SERVICE_ERROR_033 – Prestazione riservata ad una particolare professione – il campo è obbligatorio\n";
       else if(!tipoContratto.equals("2") && cond.getFLAGPROFESSIONESERVIZI()!=null)
    	   res+="SERVICE_ERROR_034 – Prestazione riservata ad una particolare professione – campo non previsto\n";
		
       if(cond.getFLAGPROFESSIONESERVIZI()!=null) {
	       if(Costanti.FLAG_VALORE_SI.equals(cond.getFLAGPROFESSIONESERVIZI().value())) {
	    	   if(cond.getPROFESSIONESERVIZI()==null || "".equals(cond.getPROFESSIONESERVIZI()))
	    		   res+="SERVICE_ERROR_035 – Professione servizi – il campo è obbligatorio\n";
	       } else if(Costanti.FLAG_VALORE_NO.equals(cond.getFLAGPROFESSIONESERVIZI().value())){
	    	   if(cond.getPROFESSIONESERVIZI()!=null)
	    		   res+="SERVICE_ERROR_036 – Professione servizi – campo non previsto\n";
	       }
       }
       
		return res;
	}
}
