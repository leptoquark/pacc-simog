package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.util.ConversionUtils;

import java.util.ArrayList;
import java.util.List;

public class ConvertAnagrafiche extends ConversionUtils{	
	
	
	public static ConvertAnagrafiche convertAnagrafiche = null;
	
	public synchronized static ConvertAnagrafiche getInstance(){
		if(convertAnagrafiche == null) convertAnagrafiche = new ConvertAnagrafiche();
		return convertAnagrafiche;
	}
	
	private ConvertAnagrafiche(){}
	/**
	 * @param at
	 * @return
	 */
	public List<SoggettoPartecipanteBean> convertiAnagraficheAggiudicatari(AggiudicatarioType[] arrayOfAnagrafichePartecipanti){
		
		List<SoggettoPartecipanteBean> anagraficheAggiudicatari = new ArrayList<SoggettoPartecipanteBean>();
		
		for(int i = 0; i < arrayOfAnagrafichePartecipanti.length; i++){
			
			SoggettoPartecipanteBean soggpartecipante = new SoggettoPartecipanteBean();
			AggiudicatarioType anagrafica = arrayOfAnagrafichePartecipanti[i];
			soggpartecipante.setCameraCommercio(anagrafica.getCAMERACOMMERCIO());
			soggpartecipante.setCap(anagrafica.getCAP());
			soggpartecipante.setCfRappresentante(anagrafica.getCFRAPPRESENTANTE());
			soggpartecipante.setCitta(anagrafica.getCITTA());
			soggpartecipante.setCivico(anagrafica.getCIVICO());
			soggpartecipante.setCodiceFiscale(anagrafica.getCODICEFISCALEAGGIUDICATARIO());
			soggpartecipante.setCognome(anagrafica.getCOGNOME());
			soggpartecipante.setDenominazione(anagrafica.getDENOMINAZIONE());
			soggpartecipante.setIndirizzo(anagrafica.getINDIRIZZO());
			soggpartecipante.setNome(anagrafica.getNOME());
			soggpartecipante.setPartitaIva(anagrafica.getPARTITAIVA());
			soggpartecipante.setProvincia(anagrafica.getPROVINCIA());
			//gm normalizzazione codice stato italiano
			String codiceStato = anagrafica.getCODICESTATO();
			if (codiceStato==null || codiceStato.equals(Costanti.CODICE_STATO_ITALIANO))
				codiceStato = "";
			soggpartecipante.setId_stato(codiceStato);
			
			//se flag estero si setta il valore del bean
			if(Costanti.FLAG_VALORE_SI.equals(anagrafica.getSOGGETTOESTERO().toString())){
				soggpartecipante.setFlagEsteri(anagrafica.getSOGGETTOESTERO().toString());
			}
			anagraficheAggiudicatari.add(soggpartecipante);
			
		}return anagraficheAggiudicatari;
	}
	/**
	 * @param rt
	 * @return
	 */
	public List<SoggettoResponsabileBean> convertiAnagraficheResponsabili(ResponsabileType[] arrayOfAnagraficheResponsabili){
		List<SoggettoResponsabileBean> anagraficheResponsabili = new ArrayList<SoggettoResponsabileBean>();
		
		for(int i = 0; i < arrayOfAnagraficheResponsabili.length; i++){
			
			SoggettoResponsabileBean soggresponsabile = new SoggettoResponsabileBean();
			ResponsabileType anagrafica = arrayOfAnagraficheResponsabili[i];
			
			//gestione codice istat di piu di 6 caratteri
			soggresponsabile.setComuneIstat(setCodiceIstatDaSei(anagrafica.getCODICEISTATCOMUNE()));
			soggresponsabile.setCap(anagrafica.getCAP());
			soggresponsabile.setCodiceFiscaleResponsabile(anagrafica.getCODICEFISCALERESPONSABILE());
			soggresponsabile.setCognome(anagrafica.getCOGNOME());
			soggresponsabile.setEmail(anagrafica.getEMAIL());
			soggresponsabile.setFax(anagrafica.getFAX());
			soggresponsabile.setIndirizzo(anagrafica.getINDIRIZZO());
			soggresponsabile.setNome(anagrafica.getNOME());
			soggresponsabile.setTelefono(anagrafica.getTELEFONO());	
			if(anagrafica.getSOGGETTOESTERO() == null) {
				soggresponsabile.setFlagSoggettoEstero(Costanti.FLAG_VALORE_NO);
			}else {				
				soggresponsabile.setFlagSoggettoEstero(String.valueOf(anagrafica.getSOGGETTOESTERO()));
			}
			
			anagraficheResponsabili.add(soggresponsabile);
			
		}return anagraficheResponsabili;
	}
}
