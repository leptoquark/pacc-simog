package it.anticorruzione.ted.validator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.util.UtilityClass;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.ws.massload.xmlbeans.AppaltoTypeAgg;
import it.avlp.simog.ws.massload.xmlbeans.ContraenteType;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazione;
import it.avlp.simog.ws.massload.xmlbeans.ValoreAppaltoType;

public class F03Validator {

	public static String validate(String id_gara, 
								String cig, 
								Connection con, 
								Logger logger, 
								FormularioAvvisoAggiudicazione formularioAggiudicazione) throws SQLException, Exception {
		String error = "";
		
		List<AppaltoTypeAgg> listaAppalti = formularioAggiudicazione.getAPPALTOAVVAGG();
		
		boolean awardedContract = false;
		for(AppaltoTypeAgg appalto : listaAppalti) {
					
			if(!appalto.getCIGAGG().getValue().equals(cig)) {
				LottoManager lm = new LottoManager(con,logger);
				List<Lotto> listaLotti = lm.getLottoByCigWS(appalto.getCIGAGG().getValue());
				if(listaLotti==null || listaLotti.isEmpty())
					error+="SERVICE_ERROR_010a - CIG dell'appalto - CIG inesistente o non di competenza\n";
			}
			
			if(id_gara!=null) {
				LottoManager lm = new LottoManager(con,logger);
				List<Lotto> listaLotti = lm.getListaLotti(Long.valueOf(id_gara));
				boolean cigGara=false;
				for(Lotto l : listaLotti) {
					if(l.getFullCIG().equals(appalto.getCIGAGG().getValue())) {
						cigGara=true;
						break;
					}
				}
				
				if(!cigGara)
					error+="SERVICE_ERROR_010b - CIG dell'appalto - CIG non appartenente alla gara\n";
			}
			
			InfoComuniManager icm = new InfoComuniManager(con,logger);
			InfoComuniBean datiComuni = icm.getInfoComuniByCig(appalto.getCIGAGG().getValue());
			AggiudicatarioManager aggm = new AggiudicatarioManager(con, logger);
			if(appalto.getAWARDEDCONTRACT().value().equals(Costanti.FLAG_VALORE_SI)) {
				if(Costanti.AGGIUDICATA != Integer.valueOf(datiComuni.getEsitoProcedura()))
					error+="SERVICE_ERROR_000 - Impossibile procedere con l'invio del formulario: su Simog la scheda dati "
							+ "comuni del cig "+appalto.getCIGAGG().getValue()+" non risulta avere 'Aggiudicata' come esito\n";
				else {
					AggiudicazioniManager am = new AggiudicazioniManager(con,logger);
					List<AggiudicazioneBean> listaAgg = am.getAggiudicazioniByCIG(appalto.getCIGAGG().getValue());
					if(listaAgg==null || listaAgg.isEmpty())
						error+="SERVICE_ERROR_000 - Impossibile inviare il formulario di aggiudicazione: "
								+ "su Simog non risulta presente una scheda aggiudicazione confermata per il CIG "+appalto.getCIGAGG().getValue()+"\n";
					else {
						List<AggiudicatarioBean> aggiudicatari = aggm.loadMany(listaAgg.get(0).getIdAggiudicazione(), 
																				listaAgg.get(0).getDataInizioAggiudicazione(), 
																				false);
						for(AggiudicatarioBean agg : aggiudicatari) {
							if(agg.getSoggettoPartecipante().getCitta()==null || "".equals(agg.getSoggettoPartecipante().getCitta()))
								error+="SERVICE_ERROR_000 - Aggiudicatario "+agg.getSoggettoPartecipante().getDenominazione()+" : nella rubrica di questo soggetto non è indicata la città. Procedere con l'aggiornamento della rubrica OE di Simog\n";
						}
						
						if(appalto.getAWARDEDNOTICE() !=null && appalto.getAWARDEDNOTICE().size() > 0) {
							for(ContraenteType contraente : appalto.getAWARDEDNOTICE()) {
								String nationalId = contraente.getADDRESSCONTRACTOR().getNATIONALID();
								
								boolean founded=false;
								for(AggiudicatarioBean agg : aggiudicatari) {
									if(nationalId.equals(agg.getSoggettoPartecipante().getCodiceFiscale())) {
										founded=true;
										break;
									}
								}
								
								if(!founded) {
									error+="SERVICE_ERROR_100 - Impossibile inviare il formulario di aggiudicazione: "
											+ "l'aggiudicatario con codice fiscale "+nationalId+" non risulta presente nella scheda aggiudicazione su Simog";
								}
								
							}
						}
						
					}
				}
				
				awardedContract=true;
				if(appalto.getPROCUREMENTUNSUCCESSFUL()!=null && !"".equals(appalto.getPROCUREMENTUNSUCCESSFUL().getValue()))
					error+="SERVICE_ERROR_012 - Motivo per il quale l'appalto non è stato aggiudicato - campo non previsto\n";
				if(appalto.getAWARDEDNOTICE()==null || appalto.getAWARDEDNOTICE().isEmpty())
					error+="SERVICE_ERROR_012b - Informazioni sul/i contraente/i - richiesto almeno un contraente\n";
				
				if(appalto.getLIKELYSUBCONTRACTED()!=null && appalto.getLIKELYSUBCONTRACTED().value().equals(Costanti.FLAG_VALORE_SI)) {
					if(appalto.getVALSUBCONTRACTING()==null)
						error+="SERVICE_ERROR_022 - Valore del contratto d'appalto da subappaltare a terzi - il campo è obbligatorio\n";
					
				} else if(appalto.getLIKELYSUBCONTRACTED()!=null && appalto.getLIKELYSUBCONTRACTED().value().equals(Costanti.FLAG_VALORE_NO)) {
					if(appalto.getVALSUBCONTRACTING()!=null)
						error+="SERVICE_ERROR_023 - Valore del contratto d'appalto da subappaltare a terzi - campo non previsto\n";
					if(appalto.getPCTSUBCONTRACTING()!=null)
						error+="SERVICE_ERROR_024 - Percentuale del contratto d'appalto da subappaltare - campo non previsto\n";
					if(appalto.getINFOADDSUBCONTRACTING()!=null)
						error+="SERVICE_ERROR_025 - Breve descrizione della porzione del contratto d'appalto da subappaltare - campo non previsto\n";
				}
				
				if(appalto.getDATECONCLUSIONCONTRACT()==null)
					error+="SERVICE_ERROR_025b - Data di stipula del contratto d'appalto - il campo è obbligatorio\n";
				else {
					Date data = appalto.getDATECONCLUSIONCONTRACT().getValue();
					Date dataNoTime = UtilityClass.getDateWithoutTime(data);
					Date current = UtilityClass.getDateWithoutTime(PageHelper.getCurrentUtilDate());
					if(dataNoTime.after(current))
						error+="SERVICE_ERROR_025d - Data di stipula del contratto d'appalto - deve essere uguale o antecedente alla data corrente\n";
				}
				
			} else {
				if(EsitoEnum.AGGIUDICATA.codice().equals(datiComuni.getEsitoProcedura()) || 
						EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice().equals(datiComuni.getEsitoProcedura()))
					error+="SERVICE_ERROR_000 - Impossibile procedere con l'invio del formulario: su Simog la scheda dati "
							+ "comuni del cig "+appalto.getCIGAGG().getValue()+" risulta avere 'Aggiudicata' o 'Proposta di Aggiudicazione' come esito";
				
				if(appalto.getPROCUREMENTUNSUCCESSFUL()==null)
					error+="SERVICE_ERROR_011 - Motivo per il quale l'appalto non è stato aggiudicato - il campo è obbligatorio\n";
				if(appalto.getDATECONCLUSIONCONTRACT()!=null)
					error+="SERVICE_ERROR_025c - Data di stipula del contratto d'appalto - campo non previsto\n";
					
			}
					
			
		}
		
		if(formularioAggiudicazione.getVALOREAPPALTO()==null && awardedContract)
			error+="SERVICE_ERROR_008 - Valore dell'appalto - il campo è obbligatorio\n";
		else if(formularioAggiudicazione.getVALOREAPPALTO()!=null && !awardedContract)
			error+="SERVICE_ERROR_008 - Valore dell'appalto - campo non richiesto\n";
		
		if(formularioAggiudicazione.getVALOREAPPALTO()!=null) {
			ValoreAppaltoType valAppalto = formularioAggiudicazione.getVALOREAPPALTO();
			if(valAppalto.getVALTOTAL()!=null && valAppalto.getVALRANGETOTALLOW()!=null)
				error+="SERVICE_ERROR_072 – Inserire valore totale dell'appalto o l'offerta più bassa\n";
			if(valAppalto.getVALTOTAL()!=null && !awardedContract)
				error+="SERVICE_ERROR_073 – Valore totale dell'appalto – campo non previsto\n";
			if(valAppalto.getVALTOTAL()!=null && valAppalto.getVALTOTAL().getValue().doubleValue()==0)
				error+="SERVICE_ERROR_074 – Valore totale dell'appalto – indicare un valore maggiore di zero\n";
			
			if(valAppalto.getVALRANGETOTALLOW()!=null) {
			
				if(!awardedContract) 
					error+="SERVICE_ERROR_076 – Offerta più bassa – campo non previsto\n";
				if(valAppalto.getVALRANGETOTALLOW().getValue().doubleValue()==0)
					error+="SERVICE_ERROR_077 – Offerta più bassa – indicare un valore maggiore di zero\n";
				if(valAppalto.getVALRANGETOTALHIGH()==null)
					error+="SERVICE_ERROR_078 – Offerta più alta – il campo è obbligatorio\n";
				else if(valAppalto.getVALRANGETOTALHIGH().getValue().doubleValue()<valAppalto.getVALRANGETOTALLOW().getValue().doubleValue())
					error+="SERVICE_ERROR_080 - l'offerta più alta non può essere inferiore all'offerta più bassa\n";
				
		    } else if(valAppalto.getVALRANGETOTALHIGH()!=null)
		    	    error+="SERVICE_ERROR_079 – Offerta più alta – campo non previsto\n";
		}
		
		return error;
	}

}
