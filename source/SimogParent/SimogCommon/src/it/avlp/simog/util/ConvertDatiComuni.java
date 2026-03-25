package it.avlp.simog.util;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;

public class ConvertDatiComuni extends ConversionUtils{

	private static ConvertDatiComuni convertDatiComuni = null;
	
	public synchronized static ConvertDatiComuni getInstance(){
		if(convertDatiComuni == null) convertDatiComuni = new ConvertDatiComuni();
		return convertDatiComuni; 
	}
	private ConvertDatiComuni(){}
	
	/**
	 * @param datiComuni
	 * @return
	 */
	public InfoComuniBean converti(DatiComuniType datiComuni){
		InfoComuniBean infoComuni = new InfoComuniBean();
		infoComuni.setCfAmmAgente(datiComuni.getCFAMMAGENTE());		
		infoComuni.setCfRup(datiComuni.getCFRUP());	
		// PATCH - VL - 09-02-2010 PROBLEMA SOMMA URGENZA
		String realCig = CIGBean.getRealCIG(datiComuni.getCIG());
		infoComuni.setCig(realCig.substring(0,7));		
//		infoComuni.setCig(datiComuni.getCIG().substring(0,7));	
		infoComuni.setDenAmmAgente(datiComuni.getDENAMMAGENTE());
		infoComuni.setFlagEnteSpeciale(datiComuni.getFLAGENTESPECIALE().toString());
		infoComuni.setFlagSAAgente(datiComuni.getFLAGSAAGENTE().toString());
		infoComuni.setIdCategSa(datiComuni.getIDCATEGSA());
		infoComuni.setTipoContratto(datiComuni.getTIPOCONTRATTO().toString());
		if(datiComuni.getIDTIPOLOGIASA() != null){
			infoComuni.setTipologiaSA(Long.parseLong(setDefault(datiComuni.getIDTIPOLOGIASA())));
		}
		infoComuni.setEsitoProcedura(datiComuni.getESITOPROCEDURA() != null ? datiComuni.getESITOPROCEDURA().toString() : EsitoEnum.AGGIUDICATA.codice());
		
		//dati che vengono sovrascritti con i dati del lotto
		infoComuni.setCfAmministrazione(datiComuni.getCFAMM());
		infoComuni.setDenAmministrazione(datiComuni.getDENAMM());
		infoComuni.setCfStazioneAppaltante(datiComuni.getCFSA());
		infoComuni.setDenStazioneAppaltante(datiComuni.getDENSA());
		infoComuni.setCodiceCC(datiComuni.getCODICECC());
		infoComuni.setDenomCC(datiComuni.getDENOMCC());
		
		//gm altri tre campi da gestire per dati comuni 
//		if(datiComuni.getMODOREALIZZAZIONE() != null){
//			infoComuni.setID_MODO_REAL(Integer.parseInt(setDefault(datiComuni.getMODOREALIZZAZIONE())));
//		}
		if(datiComuni.getFLAGESCLUSO() != null){
			infoComuni.setFLAG_ESCLUSO(String.valueOf(setDefault(datiComuni.getFLAGESCLUSO().toString())));
		}
		if(datiComuni.getIDESCLUSIONE() != null){
			infoComuni.setID_ESCLUSIONE(Integer.parseInt(setDefault(datiComuni.getIDESCLUSIONE())));
		}
		//gm fine altri tre campi da gestire per dati comuni 
		
		if(datiComuni.getIDSCHEDASIMOG() != null && !"".equals(datiComuni.getIDSCHEDASIMOG())){
			infoComuni.setIdInfo(Long.parseLong(datiComuni.getIDSCHEDASIMOG()));
		}
		infoComuni.setIdLocale(datiComuni.getIDSCHEDALOCALE());
		// end
		
		if(datiComuni.getTIPOLOGIAPROCEDURA() !=  null)
			infoComuni.setTipologiaProcedura(Long.parseLong(datiComuni.getTIPOLOGIAPROCEDURA()));
		
		infoComuni.setDurataConvenzione(datiComuni.getDURATAACCQUADROCONVENZIONE());
		if(datiComuni.getFLAGCENTRALESTIPULA() != null)
			infoComuni.setFlagProcedeStipula(datiComuni.getFLAGCENTRALESTIPULA().toString());
		
		return infoComuni;
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
			pubblicazioneBean.setDataBore(PageHelper.getFormattedCalendarDate(pubblicazione.getDATABORE()));
			pubblicazioneBean.setPeriodici(pubblicazione.getPERIODICI());
			
			// PP adds per gestire i campi di pubblicazione bando
			pubblicazioneBean.setLinkSitoCommittente(pubblicazione.getLINKSITO());
			pubblicazioneBean.setNumeroGuce(pubblicazione.getNUMEROGUCE());
			pubblicazioneBean.setNumeroGuri(pubblicazione.getNUMEROGURI());
			pubblicazioneBean.setNumeroBore(pubblicazione.getNUMEROBORE());
			pubblicazioneBean.setLinkAffidamentoDiretto(pubblicazione.getLINKAFFIDAMENTODIRETTO());
	
			if(pubblicazione.getFLAGBENICULT() !=null)
				pubblicazioneBean.setFlag_benicult(pubblicazione.getFLAGBENICULT().toString());
			if(pubblicazione.getFLAGSOSPESO() !=null)
				pubblicazioneBean.setFlag_sospeso(pubblicazione.getFLAGSOSPESO().toString());
		}
		
		return pubblicazioneBean;
	}

}
