package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.massload.xmlbeans.AppaltoType;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.FinanziamentoType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.RequisitoType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConvertAggiudicazione extends ConversionUtils {

	private static ConvertAggiudicazione convertAggiudicazione = null;
	
	
	private ConvertAggiudicazione(){}
	
	public synchronized static ConvertAggiudicazione getInstance(){
		if(convertAggiudicazione == null) convertAggiudicazione = new ConvertAggiudicazione();
		return convertAggiudicazione;
	}
	/**
	 * Nota che i dati appartenenti ai dati comuni quali:
	 * - cig
	 * - flag ente speciale
	 * -
	 * see it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness#converti(java.lang.Object)
	 * @throws ClassCastException
	 */
	public AggiudicazioneBean converti(AppaltoType appalto, String tipoSettore){
		AggiudicazioneBean appaltoBean = new AggiudicazioneBean();
		
			appaltoBean.setAstaElettronica(appalto.getASTAELETTRONICA().toString());
			// PP patch retrocompatibilità
			if(appalto.getCODSTRUMENTO() != null && !"".equals(appalto.getCODSTRUMENTO().trim())){
				appaltoBean.setCodStrumento(appalto.getCODSTRUMENTO().toString());
			} 
			
			appaltoBean.setCup(appalto.getCUP());
			appaltoBean.setDataInvito(PageHelper.getFormattedCalendarDate(appalto.getDATAINVITO()));
			appaltoBean.setDataManifInteresse(PageHelper.getFormattedCalendarDate(appalto.getDATAMANIFINTERESSE()));
			if(appalto.getDATASCADENZAPRESOFFERTA() != null && !"".equals(appalto.getDATASCADENZAPRESOFFERTA().toString())){
				appaltoBean.setDataScadenzaPresOfferta(PageHelper.getFormattedCalendarDate(appalto.getDATASCADENZAPRESOFFERTA()));
			}

			appaltoBean.setDataScadenzaRichiestaInvito(PageHelper.getFormattedCalendarDate(appalto.getDATASCADENZARICHIESTAINVITO()));
			appaltoBean.setDataVerbaleAggiudicazione(PageHelper.getFormattedCalendarDate(appalto.getDATAVERBAGGIUDICAZIONE()));
			//gm forzo il valore del flag perchè il controllo è obsoleto
			appaltoBean.setFlagAccordoQuadro(Costanti.FLAG_VALORE_NO);
			//appaltoBean.setFlagAccordoQuadro(appalto.getFLAGACCORDOQUADRO().toString());
			appaltoBean.setFlagRichSubappalto(appalto.getFLAGRICHSUBAPPALTO().toString());
			
			// PP patch valore per evitare errori di conversione del numero
			if(appalto.getIDMODOGARA() != null)
			   appaltoBean.setIdModalitaGara(Long.parseLong(this.setDefault(appalto.getIDMODOGARA())));
			
			//controlli - numberformatexception
			if(appalto.getIDMODOINDIZIONE() != null){
				appaltoBean.setIdModoIndizione(Integer.parseInt(this.setDefaultAncheSeNonValido(appalto.getIDMODOINDIZIONE()))); //opt
			}
			
			//TICKET ALM #3835
            //In aggiudicazione il campo scelta del contraente e' eliminato e quindi potrebbe essere null
			if(appalto.getIDSCELTACONTRAENTE() != null) {
			   appaltoBean.setIdSceltaContraente(Integer.parseInt(this.setDefault(appalto.getIDSCELTACONTRAENTE())));
			}//FINE TICKET ALM #3835
			
			appaltoBean.setIdTipoPrestazione(Integer.parseInt(this.setDefault(appalto.getIDTIPOPRESTAZIONE())));
			appaltoBean.setImportoAggiudicazione(appalto.getIMPORTOAGGIUDICAZIONE());
			appaltoBean.setImportoAttuazioneSicurezza(appalto.getIMPORTOATTUAZIONESICUREZZA());
			appaltoBean.setImportoDisposizione(appalto.getIMPORTODISPOSIZIONE());
			appaltoBean.setImportoForniture(appalto.getIMPORTOFORNITURE());
			appaltoBean.setImportoLavori(appalto.getIMPORTOLAVORI());
			appaltoBean.setImportoProgettazione(appalto.getIMPORTOPROGETTAZIONE());
			appaltoBean.setImportoServizi(appalto.getIMPORTOSERVIZI());
			//XXX: gestione istat con piu di 6 caratteri
			appaltoBean.setLuogoIstat(setCodiceIstatDaSei(appalto.getLUOGOISTAT()));
			appaltoBean.setLuogoNuts(appalto.getLUOGONUTS());
			appaltoBean.setNumImpEscluseInsufGiust(appalto.getNUMIMPESCLINSUFGIUST());
			appaltoBean.setNumImpreseInvitate(appalto.getNUMIMPRESEINVITATE());
			appaltoBean.setNumImpreseOfferenti(appalto.getNUMIMPRESEOFFERENTI());
			appaltoBean.setNumImpreseRichiedenti(appalto.getNUMIMPRESERICHIEDENTI());
			appaltoBean.setNumManifInteresse(appalto.getNUMMANIFINTERESSE());
			appaltoBean.setNumOfferteAmmesse(appalto.getNUMOFFERTEAMMESSE());
			appaltoBean.setNumOfferteEscluse(appalto.getNUMOFFERTEESCLUSE());
			appaltoBean.setNumOfferteFuoriSoglia(appalto.getNUMOFFERTEFUORISOGLIA());
//			appaltoBean.setOffertaMassimo(appalto.getOFFERTAMASSIMO());
//			appaltoBean.setOffertaMinima(appalto.getOFFERTAMINIMA());
			appaltoBean.setPreinformazione(appalto.getPREINFORMAZIONE().toString());
			appaltoBean.setProceduraAcc(appalto.getPROCEDURAACC().toString());
			
			//XXX: ids		
			if(appalto.getIDSCHEDASIMOG() != null && !"".equals(appalto.getIDSCHEDASIMOG())){
				appaltoBean.setIdAggiudicazione(Long.parseLong(appalto.getIDSCHEDASIMOG()));
			}
			appaltoBean.setIdLocale(appalto.getIDSCHEDALOCALE());

			
			appaltoBean.setTermineRidotto(appalto.getTERMINERIDOTTO().toString());

			if(appalto.getOFFERTAMASSIMO() != null)
				appaltoBean.setOffertaMassimo(appalto.getOFFERTAMASSIMO());
			
			if(appalto.getOFFERTAMINIMA()!=null)
				appaltoBean.setOffertaMinima(appalto.getOFFERTAMINIMA());
			
			if(appalto.getPERCOFFAUMENTO() !=null)
				appaltoBean.setPercOffAumento(appalto.getPERCOFFAUMENTO());
			
			if(appalto.getPERCRIBASSOAGG() !=null)
				appaltoBean.setPercRibassoAgg(appalto.getPERCRIBASSOAGG());
			
			appaltoBean.setImportoNonAssog(appalto.getIMPNONASSOG());

//			// PP patch comp massloader informazioni non richieste per settori ordinari
//			if(Costanti.FLAG_VALORE_SI.equals(daticomunibean.getFlagEnteSpeciale())){	
				
				// PP 16.04.2009 preimposto a N se il valore non esiste (attributi opzionali)
//				if(appalto.getCRITERISELEZIONESTABILITISA()==null || "".equals(appalto.getCRITERISELEZIONESTABILITISA())){
//					appaltoBean.setCriteriSelezioneStabilitiSA(FlagSNType.N.toString());
//				}else{
					if(appalto.getCRITERISELEZIONESTABILITISA()!=null && Costanti.TIPO_ENTE_SPECIALE.equals(tipoSettore))
						appaltoBean.setCriteriSelezioneStabilitiSA(appalto.getCRITERISELEZIONESTABILITISA().toString());
//				}
				
//				if(appalto.getSISTEMAQUALIFICAZIONE()==null || "".equals(appalto.getSISTEMAQUALIFICAZIONE())){
//					appaltoBean.setSistemaQualificazione(FlagSNType.N.toString()); 
//				}else{
					if(appalto.getSISTEMAQUALIFICAZIONE()!=null && Costanti.TIPO_ENTE_SPECIALE.equals(tipoSettore))
						appaltoBean.setSistemaQualificazione(appalto.getSISTEMAQUALIFICAZIONE().toString());
//				}
//			}
			if(appalto.getVALSOGLIAANOMALIA() != null)
				appaltoBean.setValSogliaAnomalia(appalto.getVALSOGLIAANOMALIA());//TICKET ALM #11506 - 3.04.3.2

			// PP patch comp massloader informazione non richiesta per modo gara "offerta economicamente piu vantaggiosa"
			if(String.valueOf(Costanti.OFFERTA_VANTAGGIOSA).equals(appalto.getIDMODOGARA()) && appaltoBean.getValSogliaAnomalia() != null){			
				appaltoBean.setValSogliaAnomalia(null);
			}
			if(appalto.getOPEREURBANIZSCOMPUTO() != null)
				appaltoBean.setOpereUrbanizzazione(appalto.getOPEREURBANIZSCOMPUTO().toString());
			appaltoBean.setSottotipo(TipoAggiudicazione.A);
			appaltoBean.setProgCuiRiaggiudicato(appalto.getPROGCUIRIAGGIUDICATO());
			
			//XXX: ids		
			if(appalto.getMODALITARIAGGIUDICAZIONE() != null && !"".equals(appalto.getMODALITARIAGGIUDICAZIONE())){
				appaltoBean.setModalitaRiaggiudicazione(Integer.parseInt(appalto.getMODALITARIAGGIUDICAZIONE()));
			}
			
			if(appalto.getFLAGAGGIUDPRINCIPALE() != null)
				appaltoBean.setFlagAggiudPrincipale(appalto.getFLAGAGGIUDPRINCIPALE().toString());
			
			appaltoBean.setCodiceContratto(appalto.getCODICECONTRATTO());
			
			
			//TICKET ALM #14639 - 3.04.5
			if(appalto.getRELAZIONEUNICA()!=null)
		     	appaltoBean.setRelazioneUnica(appalto.getRELAZIONEUNICA().toString());
			
			
			return appaltoBean;
	}
	

	private AggiudicazioneBean getRiaggiudicazione(
			AggiudicazioneBean appaltoBean) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Aggiunge n forniture
	 * 
	 * @param tipoAppalto
	 */
	public List<TipoAppaltoAggBean> convertiTipoAppaltiForniture(TipiAppaltoType[] tipoAppalto){
		List<TipoAppaltoAggBean> tipiAppaltoForn = new ArrayList<TipoAppaltoAggBean>();
		for(int i = 0; i< tipoAppalto.length;i++){
			tipiAppaltoForn.add(this.convertiTipoAppaltiForniture(tipoAppalto[i]));
		}return tipiAppaltoForn;
	}	
	/**
	 * Aggiunge una fornitura
	 * 
	 * @param tipoAppalto
	 */
	private TipoAppaltoAggBean convertiTipoAppaltiForniture(TipiAppaltoType tipoAppalto){
		TipoAppaltoAggBean tipoAppaltobean = new TipoAppaltoAggBean();
		tipoAppaltobean.setIdAppalto(Long.parseLong(this.setDefault(tipoAppalto.getIDAPPALTO())));
		return tipoAppaltobean;	
	}
	/**
	 * Aggiunge n forniture
	 * 
	 * @param tipoAppalto
	 */
	public List<TipoAppaltoAggBean> convertiTipoAppaltiLavori(TipiAppaltoType[] tipoAppalto){
		List<TipoAppaltoAggBean> tipiAppaltoLav = new ArrayList<TipoAppaltoAggBean>();
		for(int i = 0; i< tipoAppalto.length;i++){
			tipiAppaltoLav.add(this.convertiTipoAppaltiLavori(tipoAppalto[i]));
		}return tipiAppaltoLav;
	}
	
	/**
	 * @param tipoAppalto
	 * @return
	 */
	private TipoAppaltoAggBean convertiTipoAppaltiLavori(TipiAppaltoType tipoAppalto){
		TipoAppaltoAggBean tipoAppaltobean = new TipoAppaltoAggBean();
		tipoAppaltobean.setIdAppalto(Long.parseLong(this.setDefault(tipoAppalto.getIDAPPALTO())));	
		return tipoAppaltobean;
	}
	
	/**
	 * Converte una lista di requisiti
	 * 
	 * @param requisiti
	 * @return
	 */
	public List<RequisitiBean> convertiRequisiti(RequisitoType[] requisiti){
		List<RequisitiBean> listOfRequisiti = new ArrayList<RequisitiBean>();
		for(int i = 0; i < requisiti.length;i++){
			listOfRequisiti.add(this.convertiRequisito(requisiti[i]));
		}return listOfRequisiti;
	}
	
	/**
	 * Converte un singolo requisito
	 * 
	 * @param requisito
	 * @return
	 */
	private RequisitiBean convertiRequisito(RequisitoType requisito){
		RequisitiBean requisitibean = new RequisitiBean();		
		requisitibean.setClasseImporto(requisito.getCLASSEIMPORTO().toString());
		requisitibean.setIdCategoria(requisito.getIDCATEGORIA().toString());
		
		if (requisito.getPREVALENTE()!=null)
			requisitibean.setPrevalente(requisito.getPREVALENTE().toString());
		
		if (requisito.getSCORPORABILE()!=null)
			requisitibean.setScorporabile(requisito.getSCORPORABILE().toString());
		
		if (requisito.getSUBAPPALTABILE()!=null)
			requisitibean.setSubAppaltabile(requisito.getSUBAPPALTABILE().toString());
		
		return requisitibean;
	}

	private TipoFinanziamentoBean convertiFinanziamento(FinanziamentoType finanziamento){
		TipoFinanziamentoBean finanziamentobean = new TipoFinanziamentoBean();
		
		finanziamentobean.setIdFinanziamento(finanziamento.getIDFINANZIAMENTO().toString());
		finanziamentobean.setImporto(finanziamento.getIMPORTOFINANZIAMENTO());
		
		return finanziamentobean;
	}
	public List<TipoFinanziamentoBean> convertiFinanziamenti(FinanziamentoType[] finanziamenti){
		List<TipoFinanziamentoBean> listOfFinanziamenti = new ArrayList<TipoFinanziamentoBean>();
		for(int i = 0;i < finanziamenti.length; i++){
			listOfFinanziamenti.add(this.convertiFinanziamento(finanziamenti[i]));
		}return listOfFinanziamenti;
	}
	public List<CondizioneAggBean> convertiCondizioni(CondizioneType[] condizioni){
		List<CondizioneAggBean> listOfCondizioni = new ArrayList<CondizioneAggBean>();
		for(int i = 0; i <condizioni.length; i++){
			listOfCondizioni.add(this.convertiCondizione(condizioni[i]));
		}return listOfCondizioni;
	}
	private CondizioneAggBean convertiCondizione(CondizioneType condizione){
		CondizioneAggBean condizionebean = new CondizioneAggBean();
		condizionebean.setIdCondizione(Long.parseLong(this.setDefault(condizione.getIDCONDIZIONE())));		
		return condizionebean;
	}
	public List<AggiudicatarioBean> convertiAggiudicatari(SoggAggiudicatarioType[] aggiudicatario)throws Exception{
		List<AggiudicatarioBean> listOfAggiudicatari = new ArrayList<AggiudicatarioBean>();
		for(int i = 0; i < aggiudicatario.length; i++){
			listOfAggiudicatari.add(this.convertiAggiudicatario(aggiudicatario[i]));
		}
		
		// PP 14.03.2011 patch condivisa con Obino per codice gruppo
		// viene impostato a 1 per tutti i soggetti che hanno tipologia ATI o CONSORZIO
		// se non e' impostato per nessuno (=0)
		boolean hasAti = false;
		for(int i = 0; i < listOfAggiudicatari.size(); i++){
			if (listOfAggiudicatari.get(i).getIdGruppo() > 0 
			   && (listOfAggiudicatari.get(i).getIdTipoAgg() == Costanti.TIPODITTA_LIKE_ATI || listOfAggiudicatari.get(i).getIdTipoAgg() == Costanti.TIPODITTA_LIKE_CONSORZIO))
			hasAti = true;
			break;
		}	
		if(!hasAti){
			for(int i = 0; i < listOfAggiudicatari.size(); i++){
				//TICKET ALM #3764
				/*if (listOfAggiudicatari.get(i).getIdGruppo() == 0 
				   && (listOfAggiudicatari.get(i).getIdTipoAgg() == Costanti.TIPODITTA_LIKE_ATI || listOfAggiudicatari.get(i).getIdTipoAgg() == Costanti.TIPODITTA_LIKE_CONSORZIO))
					listOfAggiudicatari.get(i).setIdGruppo(1);*/
				if (listOfAggiudicatari.get(i).getIdGruppo() == 0)
				{
					if(listOfAggiudicatari.get(i).getIdTipoAgg() == Costanti.TIPODITTA_LIKE_ATI)
						listOfAggiudicatari.get(i).setIdGruppo(1);
					
					if(listOfAggiudicatari.get(i).getIdTipoAgg() == Costanti.TIPODITTA_LIKE_CONSORZIO)
						listOfAggiudicatari.get(i).setIdGruppo(2);
				}
				//FINE TICKET ALM #3764
			}	
		}
		
		return listOfAggiudicatari;
	}
	
	public List<DittaAusiliariaBean> convertiDitteAusiliarie(DittaAusiliariaType[] aggiudicatario)throws Exception{
		List<DittaAusiliariaBean> listOfAggiudicatari = new ArrayList<DittaAusiliariaBean>();
		for(int i = 0; i < aggiudicatario.length; i++){
			listOfAggiudicatari.add(this.convertiDittaAusiliaria(aggiudicatario[i])); 
		}return listOfAggiudicatari;
	}
	
	private DittaAusiliariaBean convertiDittaAusiliaria(DittaAusiliariaType aggiudicatario)throws Exception{
		DittaAusiliariaBean aggiudicatariobean = new DittaAusiliariaBean();
		
		aggiudicatariobean.setCfAusiliaria(aggiudicatario.getCODICEFISCALEAUSILIARIA());
		aggiudicatariobean.setFlagAvvalimento(aggiudicatario.getFLAGAVVALIMENTO().toString());
		aggiudicatariobean.setCodiceFiscaleAggiudicatario(aggiudicatario.getCODICEFISCALEAGGIUDICATARIO());
		
		//gm normalizzazione codice stato italiano dell'aggiudicatario
		String codiceStatoAgg = aggiudicatario.getCODICESTATOAGGIUDICATARIO();
		if (codiceStatoAgg==null || codiceStatoAgg.equals(Costanti.CODICE_STATO_ITALIANO))
			codiceStatoAgg = "";
		aggiudicatariobean.setId_statoAggiudicatario(codiceStatoAgg);
		
//		aggiudicatariobean.setIdTipoAgg(Long.parseLong(this.setDefault(aggiudicatario.getIDTIPOAGG())));
//		if(aggiudicatario.getRUOLO() != null)
//			aggiudicatariobean.setRuolo(aggiudicatario.getRUOLO().toString());
		
		/* - nested - */
			SoggettoPartecipanteBean soggpartecipante = new SoggettoPartecipanteBean();
			String codiceFiscale = aggiudicatario.getCODICEFISCALEAUSILIARIA();
			soggpartecipante.setCodiceFiscale(codiceFiscale);

			//gm normalizzazione codice stato italiano della ditta ausiliaria
			String codiceStato = aggiudicatario.getCODICESTATOAUSILIARIA();
			if (codiceStato==null || codiceStato.equals(Costanti.CODICE_STATO_ITALIANO))
				codiceStato = "";
			soggpartecipante.setId_stato(codiceStato);
			
//			if("".equals(codiceStato) || Costanti.CODICE_STATO_ITALIANO.equalsIgnoreCase(codiceStato)){
//				soggpartecipante.setFlagEsteri(FlagSNType.N.toString());
//			}else{
//				soggpartecipante.setFlagEsteri(FlagSNType.S.toString());
//			}
			aggiudicatariobean.setSoggettoPartecipante(soggpartecipante);
		return aggiudicatariobean;
	}
	
	
	private AggiudicatarioBean convertiAggiudicatario(SoggAggiudicatarioType aggiudicatario)throws Exception{
		AggiudicatarioBean aggiudicatariobean = new AggiudicatarioBean();
		
		aggiudicatariobean.setCfAusiliaria(aggiudicatario.getCFAUSILIARIA());
		aggiudicatariobean.setFlagAvvalimento(aggiudicatario.getFLAGAVVALIMENTO().toString());
		aggiudicatariobean.setIdTipoAgg(Long.parseLong(this.setDefault(aggiudicatario.getIDTIPOAGG())));
		if(aggiudicatario.getRUOLO() != null)
			aggiudicatariobean.setRuolo(aggiudicatario.getRUOLO().toString());
		
		/* - nested - */
			SoggettoPartecipanteBean soggpartecipante = new SoggettoPartecipanteBean();
			String codiceFiscale = aggiudicatario.getCODICEFISCALEAGGIUDICATARIO();
			soggpartecipante.setCodiceFiscale(codiceFiscale);
			
			//gm normalizzazione codice stato italiano
			String codiceStato = aggiudicatario.getCODICESTATO();
			if (codiceStato==null || codiceStato.equals(Costanti.CODICE_STATO_ITALIANO))
				codiceStato = "";
			soggpartecipante.setId_stato(codiceStato);
			
			if("".equals(codiceStato) || Costanti.CODICE_STATO_ITALIANO.equalsIgnoreCase(codiceStato)){
				soggpartecipante.setFlagEsteri(FlagSNType.N.toString());
			}else{
				soggpartecipante.setFlagEsteri(FlagSNType.S.toString());
			}
			aggiudicatariobean.setSoggettoPartecipante(soggpartecipante);
			aggiudicatariobean.setIdGruppo(aggiudicatario.getIDGRUPPO());
			
			//Ticket ALM #654
			if(aggiudicatario.getIMPORTOAGGIUDICAZIONE()!=null)
				aggiudicatariobean.setImpAggiudicatario(aggiudicatario.getIMPORTOAGGIUDICAZIONE());
			if(aggiudicatario.getPERCOFFAUMENTO()!=null)
				aggiudicatariobean.setPercAumentoAggiudicatario(aggiudicatario.getPERCOFFAUMENTO());
			if(aggiudicatario.getPERCRIBASSOAGG()!=null)
				aggiudicatariobean.setPercRibassoAggiudicatario(aggiudicatario.getPERCRIBASSOAGG());
			//Fine Ticket ALM #654
			
		return aggiudicatariobean;
	}
	

	
	/**
	 * Converte un array di incaricati (XML type) in una lista di incaricati (simog bean type)
	 * 
	 * @param incaricato
	 * @return
	 * @throws Exception
	 */
	public List<ResponsabileBean> convertiIncaricati(IncaricatoType[] incaricato) throws Exception{
		List<ResponsabileBean> listOfIncaricati = new ArrayList<ResponsabileBean>();
		for(int i = 0; i <incaricato.length;i++){
			listOfIncaricati.add(this.convertiIncaricato(incaricato[i]));
		}return listOfIncaricati;
	}	
	/**
	 * Converte un singolo incaricato da xml type a simog bean
	 * 
	 * @param incaricato
	 * @return
	 * @throws Exception
	 */
	private ResponsabileBean convertiIncaricato(IncaricatoType incaricato) throws Exception{
		ResponsabileBean responsabilebean = new ResponsabileBean();
		
		responsabilebean.setCigProgEsterna(incaricato.getCIGPROGESTERNA());
		responsabilebean.setDataAffProgEsterna(PageHelper.getFormattedCalendarDate(incaricato.getDATAAFFPROGESTERNA()));
		responsabilebean.setDataConsProgEsterna(PageHelper.getFormattedCalendarDate(incaricato.getDATACONSPROGESTERNA()));
		responsabilebean.setIdRuolo(Integer.parseInt(this.setDefault(incaricato.getIDRUOLO())));
		responsabilebean.setSezione(incaricato.getSEZIONE().toString());
		
		String codiceFiscale = incaricato.getCODICEFISCALERESPONSABILE();
		
		
		
		if(incaricato.getPERSONAGIURIDICA() != null && incaricato.getPERSONAGIURIDICA().equals(FlagSNType.S)){
			SoggettoPartecipanteBean spb = new SoggettoPartecipanteBean();
			spb.setCodiceFiscale(incaricato.getCODICEFISCALERESPONSABILE());
			
			//gm normalizzazione codice stato italiano
			String codiceStato = incaricato.getCODICESTATO();
			if (codiceStato==null || codiceStato.equals(Costanti.CODICE_STATO_ITALIANO))
				codiceStato = "";
			spb.setId_stato(codiceStato);
		
			responsabilebean.setSoggettoPartecipante(spb);
		}else {
			SoggettoResponsabileBean soggresponsabile = new SoggettoResponsabileBean();
			soggresponsabile.setCodiceFiscaleResponsabile(codiceFiscale);
			responsabilebean.setSoggettoResponsabile(soggresponsabile);
		}
		
		//TICKET ALM #10571 - 3.04.5
		if(incaricato.getIDGRUPPOINCARICATO() != 0)
			responsabilebean.setIdGruppo(incaricato.getIDGRUPPOINCARICATO());

		if(incaricato.isSetMANDANTE()) {
			String boolStr = incaricato.getMANDANTE().toString();
			if(FlagSNType.S.toString().equals(boolStr))
		       responsabilebean.setMandante(true);
		}
		
		return responsabilebean;
	}



}
