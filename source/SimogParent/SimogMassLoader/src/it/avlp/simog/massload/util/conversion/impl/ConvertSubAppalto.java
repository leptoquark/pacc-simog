package it.avlp.simog.massload.util.conversion.impl;

import java.util.ArrayList;
import java.util.List;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

public class ConvertSubAppalto extends ConversionUtils {

	private static ConvertSubAppalto convertSubAppalto = null;
	
	public synchronized static ConvertSubAppalto getInstance(){
		if(convertSubAppalto == null) convertSubAppalto = new ConvertSubAppalto();
		return convertSubAppalto;
	}
	
	private ConvertSubAppalto(){}
	
	public SubappaltiBean converti(SubappaltoType subbappalto) {
		SubappaltiBean subbappaltobean = new SubappaltiBean();		
		subbappaltobean.setCfDitta(subbappalto.getCFDITTA());
		if(subbappalto.getFLAGDITTASUBESTERA() != null) {
			subbappaltobean.setFlagDittaSubEstera(subbappalto.getFLAGDITTASUBESTERA().toString());// MEV 36771 3.04.8.1
		}
		subbappaltobean.setDataAutorizzazione(PageHelper.getFormattedCalendarDate(subbappalto.getDATAAUTORIZZAZIONE()));
		if(subbappalto.getIDCATEGORIA() != null)
			subbappaltobean.setIdCategoria(subbappalto.getIDCATEGORIA().toString());
		subbappaltobean.setIdCpv(subbappalto.getIDCPV());
		//inizializzo lista subappaltatori
		List<SubappaltatoreBean> subappaltatori = new ArrayList<SubappaltatoreBean>();
		SoggSubappaltatoreType[] listSubappaltatoriFromXml = subbappalto.getSubappaltatoreArray();
		for (SoggSubappaltatoreType s : listSubappaltatoriFromXml) {
			
			SubappaltatoreBean b = new SubappaltatoreBean();
			SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
			sogg.setCodiceFiscale(s.getCODICEFISCALESUBAPPALTATORE());
			b.setSoggettoPartecipante(sogg);
			subappaltatori.add(b);
		}
		subbappaltobean.setSubappaltatori(subappaltatori);
		subbappaltobean.setImportoEffettivo(subbappalto.getIMPORTOEFFETTIVO());
		subbappaltobean.setImportoPresunto(subbappalto.getIMPORTOPRESUNTO());
		subbappaltobean.setOggettoSubappalto(subbappalto.getOGGETTOSUBAPPALTO());
		
		if(subbappalto.getIDSCHEDASIMOG() != null && !"".equals(subbappalto.getIDSCHEDASIMOG())){

			subbappaltobean.setIdRecord(Long.parseLong(subbappalto.getIDSCHEDASIMOG()));
		}
		subbappaltobean.setIdLocale(subbappalto.getIDSCHEDALOCALE());
		subbappaltobean.setCfAggiudicatario(subbappalto.getCODICEFISCALEAGGIUDICATARIO());
		
		return subbappaltobean;
	}

}
