package it.anticorruzione.ted.validator;

import it.anticorruzione.ted.db.entity.Lotto;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.DescrizioneAppaltoType;

public class DeltaLottoValidator {

	public static String validate(DeltaLottoTED deltaLottoTED, Lotto lotto, boolean multilotto) {
		String res = DeltaLottoValidator.validateDescrizioneAppalto(deltaLottoTED.getDESCRIZIONEAPPALTO(), multilotto, lotto);
		
		return res;
	}

	private static String validateDescrizioneAppalto(DescrizioneAppaltoType descrizioneappalto, boolean multilotto, Lotto lotto) {
		String res ="";
		String tipoProcedura = lotto.getIdSceltaContraente();
		
		if(multilotto && descrizioneappalto.getTITOLOAPPALTO()==null)
			res+="SERVICE_ERROR_044b – Titolo dell’appalto – il campo è obbligatorio\n";
		
		if(descrizioneappalto.getCRITERIOAGGLOTTO().getValue().equals("2")) {
			if(descrizioneappalto.getCRITERIOQUALITA()!=null && !descrizioneappalto.getCRITERIOQUALITA().isEmpty())
				res+="SERVICE_ERROR_045 – Criteri qualità – campo non richiesto\n";
			if(descrizioneappalto.getTIPOCRITERIO()!=null)
				res+="SERVICE_ERROR_047 – Tipo criterio – campo non richiesto\n";
			if(descrizioneappalto.getCRITERIOCOSTO()!=null && !descrizioneappalto.getCRITERIOCOSTO().isEmpty())
				res+="SERVICE_ERROR_047a – Criteri costo – campo non richiesto\n";
			if(descrizioneappalto.getCRITERIOPREZZO()!=null)
				res+="SERVICE_ERROR_049 – Criteri prezzo – campo non richiesto\n";
		} else if(descrizioneappalto.getCRITERIOAGGLOTTO().getValue().equals("1")) {
			if(descrizioneappalto.getTIPOCRITERIO()==null)
				res+="SERVICE_ERROR_046 – Criteri qualità – il campo è obbligatorio\n";
		}
		
		if(descrizioneappalto.getTIPOCRITERIO()!=null) {
			if(descrizioneappalto.getTIPOCRITERIO().getValue().equals("1")) {
				if(descrizioneappalto.getCRITERIOCOSTO()==null || descrizioneappalto.getCRITERIOCOSTO().size()==0)
					res+="SERVICE_ERROR_046a – Criteri costo – richiesto almeno un criterio\n";
				if(descrizioneappalto.getCRITERIOPREZZO()!=null)
					res+="SERVICE_ERROR_049 – Criteri prezzo – campo non richiesto\n";
			} else if(descrizioneappalto.getTIPOCRITERIO().getValue().equals("2")) {
				if(descrizioneappalto.getCRITERIOPREZZO()==null)
					res+="SERVICE_ERROR_048 – Criteri prezzo – richiesto un criterio\n";
				if(descrizioneappalto.getCRITERIOCOSTO()!=null && !descrizioneappalto.getCRITERIOCOSTO().isEmpty())
					res+="SERVICE_ERROR_047a – Criteri costo – campo non richiesto\n";
			}
		}
		
		if(tipoProcedura.equals(String.valueOf(Costanti.PROC_APE)) && descrizioneappalto.getNUMCANDIDATIPREVISTI()!=null)
			res+="SERVICE_ERROR_052 – Numero candidati previsti – campo non richiesto\n";
		if(tipoProcedura.equals(String.valueOf(Costanti.PROC_APE)) && descrizioneappalto.getMINNUMCANDIDATIPREVISTI()!=null)
			res+="SERVICE_ERROR_053 – Numero minimo di candidati previsti – campo non richiesto\n";	
		else if(descrizioneappalto.getNUMCANDIDATIPREVISTI()!=null && descrizioneappalto.getMINNUMCANDIDATIPREVISTI()!=null)
			res+="SERVICE_ERROR_053 – Numero minimo di candidati previsti – campo non richiesto\n";
		if(tipoProcedura.equals(String.valueOf(Costanti.PROC_APE)) && descrizioneappalto.getMAXNUMCANDIDATIPREVISTI()!=null)
			res+="SERVICE_ERROR_054 – Numero massimo di candidati previsti – campo non richiesto\n";
		else if(descrizioneappalto.getNUMCANDIDATIPREVISTI()!=null && descrizioneappalto.getMAXNUMCANDIDATIPREVISTI()!=null)
			res+="SERVICE_ERROR_054 – Numero massimo di candidati previsti – campo non richiesto\n";
		if(descrizioneappalto.getMINNUMCANDIDATIPREVISTI()!=null && descrizioneappalto.getMAXNUMCANDIDATIPREVISTI()!=null) {
			if(descrizioneappalto.getMAXNUMCANDIDATIPREVISTI()<=descrizioneappalto.getMINNUMCANDIDATIPREVISTI())
				res+="SERVICE_ERROR_055 – Numero massimo di candidati previsti – deve essere maggiore del numero minimo di candidati previsti\n";
		}
		
		if(tipoProcedura.equals(String.valueOf(Costanti.PROC_APE)) && descrizioneappalto.getCRITERIMAXNUMCANDIDATI()!=null)
			res+="SERVICE_ERROR_057 – Criteri per selezione candidati – campo non richiesto\n";
		else if(descrizioneappalto.getCRITERIMAXNUMCANDIDATI()!=null && 
				descrizioneappalto.getNUMCANDIDATIPREVISTI()==null &&
				descrizioneappalto.getMINNUMCANDIDATIPREVISTI()==null &&
				descrizioneappalto.getMAXNUMCANDIDATIPREVISTI()==null)
			res+="SERVICE_ERROR_057 – Criteri per selezione candidati – campo non richiesto\n";
		else if(descrizioneappalto.getCRITERIMAXNUMCANDIDATI()==null && 
				(descrizioneappalto.getNUMCANDIDATIPREVISTI()!=null || 
				descrizioneappalto.getMINNUMCANDIDATIPREVISTI()!=null || 
				descrizioneappalto.getMAXNUMCANDIDATIPREVISTI()!=null))
			res+="SERVICE_ERROR_056 – Criteri per selezione candidati – il campo è obbligatorio\n";
		
		if(Costanti.FLAG_VALORE_SI.equals(lotto.getFlagPrevedeRip()) && 
				(descrizioneappalto.getDESCRIZIONEOPZIONI()==null || "".equals(descrizioneappalto.getDESCRIZIONEOPZIONI())))
			res+="SERVICE_ERROR_058 – Descrizione delle opzioni – il campo è obbligatorio\n";
		else if(!Costanti.FLAG_VALORE_SI.equals(lotto.getFlagPrevedeRip()) && descrizioneappalto.getDESCRIZIONEOPZIONI()!=null)
			res+="SERVICE_ERROR_059 – Descrizione delle opzioni – campo non richiesto\n";
		
		if(Costanti.FLAG_VALORE_SI.equals(descrizioneappalto.getFLAGAPPALTOPROGETTOUE().value()) 
				&& (descrizioneappalto.getAPPALTOPROGETTOUE()==null || "".equals(descrizioneappalto.getAPPALTOPROGETTOUE())))
				res+="SERVICE_ERROR_060 – Numero o riferimento del progetto – il campo è obbligatorio\n";
		else if(Costanti.FLAG_VALORE_NO.equals(descrizioneappalto.getFLAGAPPALTOPROGETTOUE().value())  && descrizioneappalto.getAPPALTOPROGETTOUE()!=null)
			res+="SERVICE_ERROR_061 – Numero o riferimento del progetto – campo non richiesto\n";
		
		return res;
	}
	
}
