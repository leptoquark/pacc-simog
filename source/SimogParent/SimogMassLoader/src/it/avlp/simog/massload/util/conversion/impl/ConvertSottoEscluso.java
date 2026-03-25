package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.SottoEsclusoType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

import java.util.ArrayList;
import java.util.List;

public class ConvertSottoEscluso extends ConversionUtils {

	private static ConvertSottoEscluso convertSottosoglia = null;
	
	
	private ConvertSottoEscluso(){}
	
	public synchronized static ConvertSottoEscluso getInstance(){
		if(convertSottosoglia == null) convertSottosoglia = new ConvertSottoEscluso();
		return convertSottosoglia;
	}
	/**
	 * Nota che i dati appartenenti ai dati comuni quali:
	 * - cig
	 * - flag ente speciale
	 * -
	 * see it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness#converti(java.lang.Object)
	 * @throws ClassCastException
	 */
	public AggiudicazioneBean converti(SottoEsclusoType sottosoglia, String tipoSettore, TipoAggiudicazione sottotipo){
		AggiudicazioneBean appaltoBean = new AggiudicazioneBean();

			appaltoBean.setAstaElettronica(sottosoglia.getASTAELETTRONICA().toString());
			
			appaltoBean.setSottotipo(sottotipo);
			
			appaltoBean.setCup(sottosoglia.getCUP());
			appaltoBean.setDataStipula(PageHelper.getFormattedCalendarDate( sottosoglia.getDATASTIPULA()));
			appaltoBean.setDataVerbaleAggiudicazione(PageHelper.getFormattedCalendarDate(sottosoglia.getDATAAGGIUDICAZIONE()));

			appaltoBean.setDurataContrattuale(sottosoglia.getDURATACONTRATTUALE());
			
			if(sottosoglia.getIDSCELTACONTRAENTE() != null)
			appaltoBean.setIdSceltaContraente(Integer.parseInt(this.setDefault(sottosoglia.getIDSCELTACONTRAENTE())));
			
			appaltoBean.setImportoAggiudicazione(sottosoglia.getIMPORTOAGGIUDICAZIONE());
			appaltoBean.setImportoComplessivo(sottosoglia.getIMPORTOCOMPLESSIVO());
			appaltoBean.setImportoDisposizione(sottosoglia.getIMPORTODISPOSIZIONE());
		
			// PP 3.02.1.6
			appaltoBean.setImportoAttuazioneSicurezza(sottosoglia.getIMPORTOATTUAZIONESICUREZZA());

			//XXX: gestione istat con piu di 6 caratteri
			appaltoBean.setLuogoIstat(setCodiceIstatDaSei(sottosoglia.getLUOGOISTAT()));
			appaltoBean.setLuogoNuts(sottosoglia.getLUOGONUTS());
		
			
			//XXX: ids		
			if(sottosoglia.getIDSCHEDASIMOG() != null && !"".equals(sottosoglia.getIDSCHEDASIMOG())){
				appaltoBean.setIdAggiudicazione(Long.parseLong(sottosoglia.getIDSCHEDASIMOG()));
			}
			appaltoBean.setIdLocale(sottosoglia.getIDSCHEDALOCALE());
		
			if(sottosoglia.getPERCOFFAUMENTO() !=null)
				appaltoBean.setPercOffAumento(sottosoglia.getPERCOFFAUMENTO());
			
			if(sottosoglia.getPERCRIBASSOAGG() !=null)
				appaltoBean.setPercRibassoAgg(sottosoglia.getPERCRIBASSOAGG());
			
			
			
			appaltoBean.setTermineContrattuale(PageHelper.getFormattedCalendarDate(sottosoglia.getTERMINECONTRATTUALE()));
			return appaltoBean;
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
