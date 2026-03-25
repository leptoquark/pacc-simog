package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.PosizioneType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vletizia
 *
 */
public class ConvertStipula extends ConversionUtils {

	private static ConvertStipula convertInizioLavori = null;
	public synchronized static ConvertStipula getInstance(){
		if(convertInizioLavori == null) convertInizioLavori = new ConvertStipula();
		return convertInizioLavori;
	}
	
	private ConvertStipula(){}
	
	/**
	 * @param stipula
	 * @return
	 */
	public StipulaBean converti(StipulaType stipula) {
		StipulaBean stipulaBean = new StipulaBean();
		stipulaBean.setDataDecorrenza(PageHelper.getFormattedCalendarDate(stipula.getDATADECORRRENZA()));
		stipulaBean.setDataScadenza(PageHelper.getFormattedCalendarDate(stipula.getDATASCADENZA()));
		stipulaBean.setDataStipulaContratto(PageHelper.getFormattedCalendarDate(stipula.getDATASTIPULA()));
		
		
		if(stipula.getIDSCHEDASIMOG() != null && !"".equals(stipula.getIDSCHEDASIMOG())){
			stipulaBean.setIdStipula(Long.parseLong(stipula.getIDSCHEDASIMOG()));
		}
		stipulaBean.setIdLocale(stipula.getIDSCHEDALOCALE());
		return stipulaBean;
	}
	
	/**
	 * @param pubblicazione
	 * @return
	 */
	public PubblicazioneBean convertiPubblicazione(PubblicazioneType pubblicazione){
		
		PubblicazioneBean pubblicazioneBean = new PubblicazioneBean();

		// PP pubblicazione è facoltativo
		if(pubblicazione != null){
			pubblicazioneBean.setDataAlbo(PageHelper.getFormattedCalendarDate(pubblicazione.getDATAALBO()));
			pubblicazioneBean.setDataGuce(PageHelper.getFormattedCalendarDate(pubblicazione.getDATAGUCE()));
			pubblicazioneBean.setDataGuri(PageHelper.getFormattedCalendarDate(pubblicazione.getDATAGURI()));
		if(pubblicazione.getPROFILOCOMMITTENTE()!=null)
			pubblicazioneBean.setProfiloCommitente(pubblicazione.getPROFILOCOMMITTENTE().toString());
			pubblicazioneBean.setQuotidianiNaz(pubblicazione.getQUOTIDIANINAZ());
			pubblicazioneBean.setQuotidianiReg(pubblicazione.getQUOTIDIANIREG());
		if(pubblicazione.getSITOMINISTEROINFTRASP()!=null)
			pubblicazioneBean.setSitoMinisteroInfTrasp(pubblicazione.getSITOMINISTEROINFTRASP().toString());
		if(pubblicazione.getSITOOSSERVATORIOCP()!=null)
			pubblicazioneBean.setSitoOsservatorioCP(pubblicazione.getSITOOSSERVATORIOCP().toString());
		}
			return pubblicazioneBean;
	}
	
	/**
	 * @param posizioni
	 * @return
	 * @throws Exception
	 */
	public List<PosizioneAggiudicatarioBean> convertiPosizioni(PosizioneType[] posizioni)throws Exception{
		List<PosizioneAggiudicatarioBean> listOfPosizioni = new ArrayList<PosizioneAggiudicatarioBean>();
		for(int i = 0; i < posizioni.length; i++){
			listOfPosizioni.add(this.convertPosizione(posizioni[i]));
		}return listOfPosizioni;
	}	
	
	/**
	 * @param posizione
	 * @return
	 * @throws Exception
	 */
	private PosizioneAggiudicatarioBean convertPosizione(PosizioneType posizione)throws Exception{

		PosizioneAggiudicatarioBean posizionebean = new PosizioneAggiudicatarioBean();		
		posizionebean.setCodiceCassa(posizione.getCODICECASSA());
		posizionebean.setCodiceINAIL(posizione.getCODICEINAIL());
		posizionebean.setCodiceINPS(posizione.getCODICEINPS());
		posizionebean.setCodiceStato(posizione.getCODICESTATO().toString());
		String codiceFiscale = posizione.getCODICEFISCALEAGGIUDICATARIO();
		String codiceStato = posizione.getCODICESTATO();
		SoggettoPartecipanteBean soggpartecipante = new SoggettoPartecipanteBean();
		soggpartecipante.setCodiceFiscale(codiceFiscale);
		soggpartecipante.setId_stato(codiceStato);
		if("".equals(codiceStato) || Costanti.CODICE_STATO_ITALIANO.equalsIgnoreCase(codiceStato)){
			soggpartecipante.setFlagEsteri(Costanti.FLAG_VALORE_NO);
		}else{
			soggpartecipante.setFlagEsteri(Costanti.FLAG_VALORE_SI);
		}
		posizionebean.setSoggettoPartecipante(soggpartecipante);
		return posizionebean;
		
	}
	
	/**
	 * @param incaricato
	 * @return
	 * @throws Exception
	 */
	public List<ResponsabileBean> convertiIncaricati(IncaricatoType[] incaricato)throws Exception{
		List<ResponsabileBean> listOfIncaricati = new ArrayList<ResponsabileBean>();
		for(int i = 0; i < incaricato.length; i++){
			listOfIncaricati.add(this.convertiIncaricato(incaricato[i]));
		}return listOfIncaricati;
	}	
	
	/**
	 * @param incaricato
	 * @return
	 * @throws Exception
	 */
	private ResponsabileBean convertiIncaricato(IncaricatoType incaricato)throws Exception{
		ResponsabileBean incaricatobean = new ResponsabileBean();		
		incaricatobean.setCigProgEsterna(incaricato.getCIGPROGESTERNA());
		incaricatobean.setDataAffProgEsterna(PageHelper.getFormattedCalendarDate(incaricato.getDATAAFFPROGESTERNA()));
		incaricatobean.setDataConsProgEsterna(PageHelper.getFormattedCalendarDate(incaricato.getDATACONSPROGESTERNA()));
		incaricatobean.setIdRuolo(Integer.parseInt(this.setDefault(incaricato.getIDRUOLO())));
		incaricatobean.setSezione(incaricato.getSEZIONE().toString());
		String codiceFiscale = incaricato.getCODICEFISCALERESPONSABILE();
		SoggettoResponsabileBean soggresponsabile = new SoggettoResponsabileBean();
		soggresponsabile.setCodiceFiscaleResponsabile(codiceFiscale);
		incaricatobean.setSoggettoResponsabile(soggresponsabile);
		return incaricatobean;
	}


}
