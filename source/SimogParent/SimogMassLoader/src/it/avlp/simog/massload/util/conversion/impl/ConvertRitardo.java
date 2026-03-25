package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

public class ConvertRitardo  extends ConversionUtils{

	private static ConvertRitardo convertRitardo = null;
	
	public synchronized static ConvertRitardo getInstance(){
		if(convertRitardo == null) convertRitardo = new ConvertRitardo();
		return convertRitardo;
	}
	
	private ConvertRitardo(){}
	
	public R129Bean converti(RitardoType ritardo) {
		R129Bean ritardobean = new R129Bean();		
		ritardobean.setDataConsegna(PageHelper.getFormattedCalendarDate(ritardo.getDATACONSEGNA()));
		ritardobean.setDataIstRecesso(PageHelper.getFormattedCalendarDate(ritardo.getDATAISTRECESSO()));
		ritardobean.setDataTermine(PageHelper.getFormattedCalendarDate(ritardo.getDATATERMINE()));
		ritardobean.setDurataSospensione(ritardo.getDURATASOSP());
		
		if(ritardo.getFLAGACCOLTA()!=null)
			ritardobean.setFlagAccolta(ritardo.getFLAGACCOLTA().toString());
		
		ritardobean.setFlagRipresa(ritardo.getFLAGRIPRESA().toString());
		ritardobean.setFlagRiserva(ritardo.getFLAGRISERVA().toString());
		ritardobean.setFlagTardiva(ritardo.getFLAGTARDIVA().toString());
		ritardobean.setImportoOneri(ritardo.getIMPORTOONERI());
		ritardobean.setImportoSpese(ritardo.getIMPORTOSPESE());
		ritardobean.setMotivoSospensione(ritardo.getMOTIVOSOSP());
		ritardobean.setTipoComunicazione(ritardo.getTIPOCOMUN().toString());
		
		if(ritardo.getIDSCHEDASIMOG() != null && !"".equals(ritardo.getIDSCHEDASIMOG())){		
			ritardobean.setIdRecord(Long.parseLong(ritardo.getIDSCHEDASIMOG()));
		}
		ritardobean.setIdLocale(ritardo.getIDSCHEDALOCALE());
		return ritardobean;
	}

}
