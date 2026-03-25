package it.anticorruzione.ted.validator;

import java.util.Date;
import java.util.List;

import it.anticorruzione.ted.service.ITEDDbService;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoRettifica;
import it.avlp.simog.ws.massload.xmlbeans.RettificaCpvSecType;
import it.avlp.simog.ws.massload.xmlbeans.RettificaType;

public class F14Validator {

	public static String valida(FormularioAvvisoRettifica formularioRettifica, ITEDDbService tedDb) {
		String err = "";
		
		if(formularioRettifica==null)
			return "SERVICE_ERROR_FR09 - Non sono stati specificati dati per il formulario di rettifica";
		
		//Verifica se sono stati inseriti CIG e verificare se esistono
		for(RettificaType rettifica : formularioRettifica.getRETTIFICA()) {
			if(rettifica.getCIGRETTIFICA()!=null 
					&& rettifica.getCIGRETTIFICA().getValue()!=null 
					&& !"".equals(rettifica.getCIGRETTIFICA().getValue())) {
				String cigRettifica = rettifica.getCIGRETTIFICA().getValue();
				String lotNo = tedDb.getLotNo(cigRettifica);
				if(lotNo==null)
					err+="SERVICE_ERROR_091b – CIG rettifica – il CIG indicato è inesistente\n";
			}
			
			
			int countInsert=0;
			String oldValueText = rettifica.getOLDVALUETEXT();
			String newValueText = rettifica.getNEWVALUETEXT();
			String oldCpv = rettifica.getOLDMAINCPV();
			String newCpv = rettifica.getNEWMAINCPV();
			List<RettificaCpvSecType> listaCpv = rettifica.getRETTIFICACPVSEC();
			Date oldDate = rettifica.getOLDVALUEDATE()!=null ? rettifica.getOLDVALUEDATE().getValue() : null;
			Date newDate = rettifica.getNEWVALUEDATE()!=null ? rettifica.getNEWVALUEDATE().getValue() : null;
			String oldTime = rettifica.getOLDVALUETIME()!=null ? rettifica.getOLDVALUETIME().getValue() : null;
			String newTime = rettifica.getNEWVALUETIME()!=null ? rettifica.getNEWVALUETIME().getValue() : null;
			
			if(oldValueText!=null && !"".equals(oldValueText)) {
				countInsert++;
				if(newValueText==null || "".equals(newValueText))
					err+="SERVICE_ERROR_094 – Testo nuovo da inserire – il campo è obbligatorio\n";
			} else if(newValueText!=null && !"".equals(newValueText))
					err+="SERVICE_ERROR_095 – Testo nuovo da inserire – campo non previsto\n";
			
			if(oldCpv!=null && !"".equals(oldCpv)) {
				countInsert++;
				if(newCpv==null || "".equals(newCpv))
					err+="SERVICE_ERROR_096 – CPV da inserire – il campo è obbligatorio\n";
			} else if(newCpv!=null && !"".equals(newCpv))
					err+="SERVICE_ERROR_097 – CPV da inserire – campo non previsto\n";
			
			if(oldDate!=null) {
				countInsert++;
				if(newDate==null)
					err+="SERVICE_ERROR_098 – Data da inserire – il campo è obbligatorio\n";
				
				if(oldTime!=null && newTime==null)
					err+="SERVICE_ERROR_098a – Ora nuova da inserire – il campo è obbligatorio\n";
				else if(oldTime==null && newTime!=null)
					err+="SERVICE_ERROR_099b – Ora nuova da inserire – campo non previsto\n";
					
			} else { 
				  if(newDate!=null)
					err+="SERVICE_ERROR_099 – Data da inserire – campo non previsto\n";
				  if(oldTime!=null)
					err+="SERVICE_ERROR_098a – Ora precedentemente inserita – campo non previsto\n";
				  if(newTime!=null)
					err+="SERVICE_ERROR_099b – Ora nuova da inserire – campo non previsto\n";
			 }
			
			if(listaCpv!=null && listaCpv.size()>0) {
				countInsert++;
				int countCpv=1;
				for(RettificaCpvSecType cpvSec : listaCpv) {
					String oldCpvSec = cpvSec.getOLDMAINCPVSEC();
					String newCpvSec = cpvSec.getNEWMAINCPVSEC();
					if(oldCpvSec!=null && !"".equals(oldCpvSec)) {
						
						if(newCpvSec==null || "".equals(newCpvSec))
							err+="SERVICE_ERROR_097b – CPV secondaria da inserire – il campo è obbligatorio (riga "+(countCpv)+")\n";
					} else 
							err+="SERVICE_ERROR_096a – CPV secondaria da rettificare – il campo è obbligatorio (riga "+(countCpv)+")\n";
				}
			}
			
			if(countInsert==0)
				err+="SERVICE_ERROR_091c – testo, CPV, data da rettificare – richiesto un valore";
			else if(countInsert>1)
				err+="SERVICE_ERROR_093 – testo, CPV, data da rettificare – un solo valore ammesso";
			
		}
		
		return err;
	}
	
}
