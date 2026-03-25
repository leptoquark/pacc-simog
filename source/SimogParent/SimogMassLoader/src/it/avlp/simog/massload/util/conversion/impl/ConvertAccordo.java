package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

public class ConvertAccordo extends ConversionUtils {

	private static ConvertAccordo convertAccordo = null;
	
	public synchronized static ConvertAccordo getInstance(){
		if(convertAccordo == null) convertAccordo = new ConvertAccordo();
		return convertAccordo;
	}
	private ConvertAccordo(){}
	
	/**
	 * @param accordo
	 * @return
	 */
	public AccordoBean converti(AccordoBonarioType accordo) {
		AccordoBean accordobonario = new AccordoBean();
		accordobonario.setDataAccordo(PageHelper.getFormattedCalendarDate(accordo.getDATAACCORDO()));
		accordobonario.setNumeroRiserve(accordo.getNUMRISERVE());
		accordobonario.setOneriDerivanti(accordo.getONERIDERIVANTI());
		
		if(accordo.getIDSCHEDASIMOG() != null && !"".equals(accordo.getIDSCHEDASIMOG())){
			accordobonario.setIdAccordo(Long.parseLong(accordo.getIDSCHEDASIMOG()));
		}
		accordobonario.setIdLocale(accordo.getIDSCHEDALOCALE());
		
		return accordobonario;
	}

}
