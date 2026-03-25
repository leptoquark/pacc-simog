package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

import java.util.ArrayList;
import java.util.List;

public class ConvertCollaudo extends ConversionUtils {

	private static ConvertCollaudo convertCollaudo = null;
	
	public synchronized static ConvertCollaudo getInstance(){
		if(convertCollaudo == null) convertCollaudo = new ConvertCollaudo();
		return convertCollaudo;
	}
	
	private ConvertCollaudo(){}
	
	/**
	 * @param collaudo
	 * @return
	 */
	public CollaudoBean converti(CollaudoType collaudo) {
		CollaudoBean collaudoBean = new CollaudoBean();
		collaudoBean.setDataIniOper(PageHelper.getFormattedCalendarDate(collaudo.getDATAINIZIOOPER())); //req
		collaudoBean.setDataNominaColl(PageHelper.getFormattedCalendarDate(collaudo.getDATANOMINACOLL())); //req
		collaudoBean.setDataRegolareEsec(PageHelper.getFormattedCalendarDate(collaudo.getDATAREGOLAREESEC())); // req		
		collaudoBean.setEsitoCollaudo(collaudo.getESITOCOLLAUDO().toString()); //req		
		collaudoBean.setImpFinaleLavori(collaudo.getIMPFINALELAVORI()); //opt		
		collaudoBean.setImpFinaleSicur(collaudo.getIMPFINALESECUR()); //req
		collaudoBean.setImpProgettazione(collaudo.getIMPPROGETTAZIONE());  //req
		if(collaudo.getMODOCOLLAUDO() != null)
			collaudoBean.setModoCollaudo(collaudo.getMODOCOLLAUDO().toString());
		
		if(collaudo.getDATACERTCOLLAUDO() != null){ collaudoBean.setDataCertCollaudo(PageHelper.getFormattedCalendarDate(collaudo.getDATACERTCOLLAUDO())); }//optional
		if(collaudo.getDATACOLLAUDOSTAT() != null){ collaudoBean.setDataCollaudoStat(PageHelper.getFormattedCalendarDate(collaudo.getDATACOLLAUDOSTAT())); }//optional
		if(collaudo.getDATADELIBERA() != null){ collaudoBean.setDataDelibera(PageHelper.getFormattedCalendarDate(collaudo.getDATADELIBERA())); }//optional	
		if(collaudo.getIMPFINALESERVIZI() != null){ collaudoBean.setImpFinaleServizi(collaudo.getIMPFINALESERVIZI()); }//opt
		if(collaudo.getIMPDISPOSIZIONE() != null){ collaudoBean.setImpDisposizione(collaudo.getIMPDISPOSIZIONE()); }//opt
		if(collaudo.getIMPFINALEFORNIT() != null){ collaudoBean.setImpFinaleFornit(collaudo.getIMPFINALEFORNIT()); }//opt
		//all optionals 
		if(collaudo.getAMMIMPORTODEF() != null){ collaudoBean.setAmmImportoDef(collaudo.getAMMIMPORTODEF());}
		if(collaudo.getAMMIMPORTORICH() != null){ collaudoBean.setAmmImportoRich(collaudo.getAMMIMPORTORICH());}
		collaudoBean.setAmmNumDaDef(collaudo.getAMMNUMDADEF());
		collaudoBean.setAmmNumDefinite(collaudo.getAMMNUMDEFINITE());
		
		if(collaudo.getARBIMPORTODEF() != null){ collaudoBean.setArbImportoDef(collaudo.getARBIMPORTODEF());}
		if(collaudo.getARBIMPORTORICH() != null){ collaudoBean.setArbImportoRich(collaudo.getARBIMPORTORICH());}
		collaudoBean.setArbNumDaDef(collaudo.getARBNUMDADEF());
		collaudoBean.setArbNumDefinite(collaudo.getARBNUMDEFINITE());		
		
		if(collaudo.getGIUIMPORTODEF() != null){ collaudoBean.setGiuImportoDef(collaudo.getGIUIMPORTODEF());}
		if(collaudo.getGIUIMPORTORICH() != null)collaudoBean.setGiuImportORich(collaudo.getGIUIMPORTORICH());
		collaudoBean.setGiuNumDaDef(collaudo.getGIUNUMDADEF());
		collaudoBean.setGiuNumDefinite(collaudo.getGIUNUMDEFINITE());	
			
		if(collaudo.getTRAIMPORTODEF() != null){ collaudoBean.setTraImportoDef(collaudo.getTRAIMPORTODEF());}
		if(collaudo.getTRAIMPORTORICH() != null){ collaudoBean.setTraImportoRich(collaudo.getTRAIMPORTORICH());}
		collaudoBean.setTraNumDaDef(collaudo.getTRANUMDADEF());
		collaudoBean.setTraNumDefinite(collaudo.getTRANUMDEFINITE());
		
		if(collaudo.getLAVORIESTESI() != null){ 
			collaudoBean.setFlagLavoriEstesi(collaudo.getLAVORIESTESI().toString()); 
		}
		else{
			// PP patch compatibilita' massloader
			collaudoBean.setFlagLavoriEstesi("N");
		}
		
		if(collaudo.getIDSCHEDASIMOG() != null && !"".equals(collaudo.getIDSCHEDASIMOG())){
			collaudoBean.setIdCollaudo(Long.parseLong(collaudo.getIDSCHEDASIMOG()));
		}
		collaudoBean.setIdLocale(collaudo.getIDSCHEDALOCALE());
		
		return collaudoBean;
	}
	
	public List<ResponsabileBean> convertiIncaricati(IncaricatoType[] incaricato) throws Exception{
		List<ResponsabileBean> listOfIncaricati = new ArrayList<ResponsabileBean>();
		for(int i = 0; i < incaricato.length; i++){
			listOfIncaricati.add(this.convertiIncaricato(incaricato[i]));
		}return listOfIncaricati;
	}
	
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
