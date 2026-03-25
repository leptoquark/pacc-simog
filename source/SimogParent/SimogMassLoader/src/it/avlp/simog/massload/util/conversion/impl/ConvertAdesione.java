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
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.FinanziamentoType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.RequisitoType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

import java.util.ArrayList;
import java.util.List;

public class ConvertAdesione extends ConversionUtils {

	private static ConvertAdesione convertAggiudicazione = null;
	
	
	private ConvertAdesione(){}
	
	public synchronized static ConvertAdesione getInstance(){
		if(convertAggiudicazione == null) convertAggiudicazione = new ConvertAdesione();
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
	public AggiudicazioneBean converti(AppaltoAdesioneType appalto, String tipoSettore){
		AggiudicazioneBean appaltoBean = new AggiudicazioneBean();

			
			// PP patch retrocompatibilità
			if(appalto.getCODSTRUMENTO() != null && !"".equals(appalto.getCODSTRUMENTO().trim())){
				appaltoBean.setCodStrumento(appalto.getCODSTRUMENTO().toString());
			} 
			
			appaltoBean.setSottotipo(TipoAggiudicazione.Q);
			appaltoBean.setFlagRichSubappalto(appalto.getFLAGRICHSUBAPPALTO().toString());
			appaltoBean.setImportoAggiudicazione(appalto.getIMPORTOAGGIUDICAZIONE());
			appaltoBean.setImportoForniture(appalto.getIMPORTOFORNITURE());
			appaltoBean.setImportoLavori(appalto.getIMPORTOLAVORI());
			appaltoBean.setImportoServizi(appalto.getIMPORTOSERVIZI());
			//XXX: gestione istat con piu di 6 caratteri
			appaltoBean.setLuogoIstat(setCodiceIstatDaSei(appalto.getLUOGOISTAT()));
			appaltoBean.setLuogoNuts(appalto.getLUOGONUTS());
			//XXX: ids		
			if(appalto.getIDSCHEDASIMOG() != null && !"".equals(appalto.getIDSCHEDASIMOG())){
				appaltoBean.setIdAggiudicazione(Long.parseLong(appalto.getIDSCHEDASIMOG()));
			}
			appaltoBean.setIdLocale(appalto.getIDSCHEDALOCALE());
			if(appalto.getPERCOFFAUMENTO() !=null)
				appaltoBean.setPercOffAumento(appalto.getPERCOFFAUMENTO());
			
			if(appalto.getPERCRIBASSOAGG() !=null)
				appaltoBean.setPercRibassoAgg(appalto.getPERCRIBASSOAGG());
			
			appaltoBean.setDataVerbaleAggiudicazione(PageHelper.getFormattedCalendarDate(appalto.getDATAAGGIUDICAZIONE()));
			
         // PP 10.05.2016 forzatura per le adesioni
         appaltoBean.setIdSceltaContraente(Costanti.AFF_DIR_ADESIONE);
         			
         //ALM #647
         if(appalto.getIMPORTOATTUAZIONESICUREZZA()!=null)
        	 appaltoBean.setImportoAttuazioneSicurezza(appalto.getIMPORTOATTUAZIONESICUREZZA());
         if(appalto.getIMPORTOPROGETTAZIONE()!=null)
        	 appaltoBean.setImportoProgettazione(appalto.getIMPORTOPROGETTAZIONE());
         if(appalto.getIMPNONASSOG()!=null)
        	 appaltoBean.setImportoNonAssog(appalto.getIMPNONASSOG());
         //Fine ALM #647
         
			return appaltoBean;
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
		}return listOfAggiudicatari;
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
	
	public List<DittaAusiliariaBean> convertiDitteAusiliarie(DittaAusiliariaType[] aggiudicatario)throws Exception{
		List<DittaAusiliariaBean> listOfAggiudicatari = new ArrayList<DittaAusiliariaBean>();
		for(int i = 0; i < aggiudicatario.length; i++){
			listOfAggiudicatari.add(this.convertiDittaAusiliaria(aggiudicatario[i])); 
		}return listOfAggiudicatari;
	}

	private DittaAusiliariaBean convertiDittaAusiliaria(DittaAusiliariaType dittaAusiliaria)throws Exception{
		DittaAusiliariaBean dittaAusiliariaBean = new DittaAusiliariaBean();
	
		dittaAusiliariaBean.setCfAusiliaria(dittaAusiliaria.getCODICEFISCALEAUSILIARIA());
		dittaAusiliariaBean.setFlagAvvalimento(dittaAusiliaria.getFLAGAVVALIMENTO().toString());
		dittaAusiliariaBean.setCodiceFiscaleAggiudicatario(dittaAusiliaria.getCODICEFISCALEAGGIUDICATARIO());
		
		//gm normalizzazione codice stato italiano dell'aggiudicatario
		String codiceStatoAgg = dittaAusiliaria.getCODICESTATOAGGIUDICATARIO();
		if (codiceStatoAgg==null || codiceStatoAgg.equals(Costanti.CODICE_STATO_ITALIANO))
			codiceStatoAgg = "";
		dittaAusiliariaBean.setId_statoAggiudicatario(codiceStatoAgg);
		
//		aggiudicatariobean.setIdTipoAgg(Long.parseLong(this.setDefault(aggiudicatario.getIDTIPOAGG())));
//		if(aggiudicatario.getRUOLO() != null)
//			aggiudicatariobean.setRuolo(aggiudicatario.getRUOLO().toString());
		
		/* - nested - */
			SoggettoPartecipanteBean soggpartecipante = new SoggettoPartecipanteBean();
			String codiceFiscale = dittaAusiliaria.getCODICEFISCALEAUSILIARIA();
			soggpartecipante.setCodiceFiscale(codiceFiscale);

			//gm normalizzazione codice stato italiano della ditta ausiliaria
			String codiceStato = dittaAusiliaria.getCODICESTATOAUSILIARIA();
			if (codiceStato==null || codiceStato.equals(Costanti.CODICE_STATO_ITALIANO))
				codiceStato = "";
			soggpartecipante.setId_stato(codiceStato);
			
//			if("".equals(codiceStato) || Costanti.CODICE_STATO_ITALIANO.equalsIgnoreCase(codiceStato)){
//				soggpartecipante.setFlagEsteri(FlagSNType.N.toString());
//			}else{
//				soggpartecipante.setFlagEsteri(FlagSNType.S.toString());
//			}
			dittaAusiliariaBean.setSoggettoPartecipante(soggpartecipante);
		return dittaAusiliariaBean;
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
		SoggettoResponsabileBean soggresponsabile = new SoggettoResponsabileBean();
		soggresponsabile.setCodiceFiscaleResponsabile(codiceFiscale);
		responsabilebean.setSoggettoResponsabile(soggresponsabile);
		return responsabilebean;
	}



}
