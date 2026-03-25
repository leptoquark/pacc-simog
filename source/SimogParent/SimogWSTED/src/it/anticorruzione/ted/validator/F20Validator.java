package it.anticorruzione.ted.validator;

import java.util.Date;

import it.anticorruzione.ted.service.ITEDDbService;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoModifica;
import it.avlp.simog.ws.massload.xmlbeans.ModificaType;

public class F20Validator {

	public static String valida(FormularioAvvisoModifica formularioModifica, ITEDDbService tedDb) {

		if(formularioModifica==null || formularioModifica.getMODIFICA()==null)
			return "SERVICE_ERROR_000 - Richiesto formulario di modifica\n";
		
		ModificaType modificaType = formularioModifica.getMODIFICA();
		if(modificaType.getDURATACONTRATTOGIORNI()!=null && modificaType.getDURATACONTRATTOMESI()!=null)
			return "SERVICE_ERROR_088 – Durata contratto in mesi e durata contratto in giorni – un solo valore richiesto\n";
		
		if(modificaType.getDURATACONTRATTOGIORNI()==null && 
				modificaType.getDURATACONTRATTOMESI()==null &&
				modificaType.getINIZIOCONTRATTOLOTTO()==null)
			return "SERVICE_ERROR_089 – Data di inizio contratto – il campo è obbligatorio\n";
		
		if((modificaType.getDURATACONTRATTOGIORNI()!=null || modificaType.getDURATACONTRATTOMESI()!=null) 
				&& modificaType.getINIZIOCONTRATTOLOTTO()!=null)
			return "SERVICE_ERROR_090 – Data di inizio contratto – campo non richiesto\n";
		
		if((modificaType.getDURATACONTRATTOGIORNI()!=null || modificaType.getDURATACONTRATTOMESI()!=null) 
				&& modificaType.getFINECONTRATTOLOTTO()!=null)
			return "SERVICE_ERROR_091 – Data di fine contratto – campo non richiesto\n";
		
		if(modificaType.getINIZIOCONTRATTOLOTTO()!=null && modificaType.getFINECONTRATTOLOTTO()!=null) {
			Date datainizio = modificaType.getINIZIOCONTRATTOLOTTO().getValue();
			Date datafine = modificaType.getFINECONTRATTOLOTTO().getValue();
			
			if(datainizio.after(datafine))
				return "SERVICE_ERROR_091 – Data di inizio contratto – la data inizio non può essere successive alla data fine\n";
		}
		
		return null;
	}

}
