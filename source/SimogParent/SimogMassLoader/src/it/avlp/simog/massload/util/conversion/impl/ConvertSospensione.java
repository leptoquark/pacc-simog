package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

public class ConvertSospensione extends ConversionUtils {

	private static ConvertSospensione convertSospensione = null;
	
	public synchronized static ConvertSospensione getInstance(){
		if(convertSospensione == null) convertSospensione = new ConvertSospensione();
		return convertSospensione;
	}
	
	private ConvertSospensione(){}
	
	public SospensioniBean converti(SospensioneType sospensione) {
		SospensioniBean sospensionebean = new SospensioniBean();		
		sospensionebean.setDataVerbRipr(PageHelper.getFormattedCalendarDate(sospensione.getDATAVERBRIPR()));
		sospensionebean.setDataVerbSosp(PageHelper.getFormattedCalendarDate(sospensione.getDATAVERBSOSP()));
		sospensionebean.setFlagRiserve(sospensione.getFLAGRISERVE().toString());
		sospensionebean.setFlagSuperoTemp(sospensione.getFLAGSUPEROTEMPO().toString());
		sospensionebean.setFlagVerbale(sospensione.getFLAGVERBALE().toString());
		sospensionebean.setIdMotivoSosp(Long.parseLong(this.setDefault(sospensione.getIDMOTIVOSOSP())));
		
		if(sospensione.getIDSCHEDASIMOG() != null && !"".equals(sospensione.getIDSCHEDASIMOG())){
			sospensionebean.setIdSospensione(Long.parseLong(sospensione.getIDSCHEDASIMOG())) ;
		}
		sospensionebean.setIdLocale(sospensione.getIDSCHEDALOCALE());
		return sospensionebean;
	}

}
