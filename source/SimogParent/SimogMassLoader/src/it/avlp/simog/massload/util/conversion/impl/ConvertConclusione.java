package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

public class ConvertConclusione extends ConversionUtils {

	private static ConvertConclusione convertConclusione = null;

	public synchronized static ConvertConclusione getInstance(){
		if(convertConclusione == null) convertConclusione = new ConvertConclusione();
		return convertConclusione;
	}
	
	private ConvertConclusione(){}
	
	/**
	 * @param conclusione
	 * @return
	 */
	public ConclusioneBean converti(ConclusioneType conclusione) {
		ConclusioneBean conclusioneBean = new ConclusioneBean();
		conclusioneBean.setDataRisoluzione(PageHelper.getFormattedCalendarDate(conclusione.getDATARISOLUZIONE()));
		conclusioneBean.setDataUltimazione(PageHelper.getFormattedCalendarDate(conclusione.getDATAULTIMAZIONE()));
		if(conclusione.getFLAGONERI() != null)
			conclusioneBean.setFlagOneri(conclusione.getFLAGONERI().toString());
		if(conclusione.getFLAGPOLIZZA() != null)
			conclusioneBean.setFlagPolizza(conclusione.getFLAGPOLIZZA().toString());
		if (conclusione.getIDMOTIVOINTERR()!=null)
			conclusioneBean.setMotiviInterruzione(Long.parseLong(this.setDefault(conclusione.getIDMOTIVOINTERR())));
		
		if (conclusione.getIDMOTIVORISOL()!=null)
			conclusioneBean.setMotiviRisoluzione(Long.parseLong(this.setDefault(conclusione.getIDMOTIVORISOL())));
		
		conclusioneBean.setNumInfMort(Long.parseLong(""+conclusione.getNUMINFMORT()));
		conclusioneBean.setNumInfortuni(Long.parseLong(""+conclusione.getNUMINFORTUNI()));
		conclusioneBean.setNumInfPerm(Long.parseLong(""+conclusione.getNUMINFPERM()));
		conclusioneBean.setOneriRisoluzione(conclusione.getONERIRISOLUZIONE());
		
		
		if(conclusione.getIDSCHEDASIMOG() != null && !"".equals(conclusione.getIDSCHEDASIMOG())){
			conclusioneBean.setIdUltim(Long.parseLong(conclusione.getIDSCHEDASIMOG()));
		}
		conclusioneBean.setIdLocale(conclusione.getIDSCHEDALOCALE());
		
		conclusioneBean.setGiorniProroga((long)conclusione.getNUMGIORNIPROROGA());
		if(conclusione.getTERMINECONTRATTULTIMAZIONE() != null)
			conclusioneBean.setTermineUltimazione(PageHelper.getFormattedCalendarDate(conclusione.getTERMINECONTRATTULTIMAZIONE()));
		if(conclusione.getDATAVERBCONSEGNAAVVIO() != null)
			conclusioneBean.setDataConsegna(PageHelper.getFormattedCalendarDate(conclusione.getDATAVERBCONSEGNAAVVIO()));
		return conclusioneBean;
	}

}
