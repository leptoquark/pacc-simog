package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.massload.xmlbeans.RecMotivoVarType;
import it.avlp.simog.massload.xmlbeans.RecVarianteType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

import java.util.ArrayList;
import java.util.List;

public class ConvertVariante extends ConversionUtils {

	private static ConvertVariante convertVariante = null;
	
	public synchronized static ConvertVariante getInstance(){
		if(convertVariante == null) convertVariante = new ConvertVariante();
		return convertVariante;
	}
	
	private ConvertVariante(){}
	
	/**
	 * @param variante
	 * @return
	 */
	public VarianteBean converti(RecVarianteType variante) {

		VarianteBean variantebean = new VarianteBean();		
		variantebean.setAltreMotivazioni(variante.getALTREMOTIVAZIONI());
		variantebean.setDataAttoAggiuntivo(PageHelper.getFormattedCalendarDate(variante.getDATAATTOAGGIUNTIVO()));
		variantebean.setDataVerbaleApprovazione(PageHelper.getFormattedCalendarDate(variante.getDATAVERBAPPR()));
		variantebean.setImpDisposizione(variante.getIMPDISPOSIZIONE());
		variantebean.setImpProgettazione(variante.getIMPPROGETTAZIONE());
		variantebean.setImpRidetFornit(variante.getIMPRIDETFORNIT());
		variantebean.setImpRidetLavori(variante.getIMPRIDETLAVORI());
		variantebean.setImpRidetServizi(variante.getIMPRIDETSERVIZI());
		variantebean.setImpSicurezza(variante.getIMPSICUREZZA());
		variantebean.setImpDisposizione(variante.getIMPDISPOSIZIONE());
		variantebean.setImpProgettazione((variante.getIMPPROGETTAZIONE()));
		variantebean.setImpRidetFornit(variante.getIMPRIDETFORNIT());
		variantebean.setImpRidetLavori(variante.getIMPRIDETLAVORI());
		variantebean.setImpRidetServizi(variante.getIMPRIDETSERVIZI());
		variantebean.setImpSicurezza(variante.getIMPSICUREZZA());
		variantebean.setNumGiorniProroga(variante.getNUMGIORNIPROROGA());
        variantebean.setUlterioriSomme(variante.getULTERIORISOMME()); //Ticket ALM #651
		variantebean.setCigProcedura(variante.getCIGPROCEDURA()); //TICKET ALM - 3.04.3 PT
		if(variante.getLINKVARIANTI() != null && !"".equals(variante.getLINKVARIANTI())){
			variantebean.setLinkVarianti(variante.getLINKVARIANTI()); //MEV 34191 3.04.8
		}
		
		if (variante.getIDMOTIVOREVPREZZI() != null) {
			variantebean.setIdMotivoRevPrezzi(variante.getIDMOTIVOREVPREZZI().toString()); //MEV 34469 3.04.8
		}
		
		if(variante.getIDSCHEDASIMOG() != null && !"".equals(variante.getIDSCHEDASIMOG())){
			variantebean.setIdVariante(Long.parseLong(variante.getIDSCHEDASIMOG()));
		}
		variantebean.setIdLocale(variante.getIDSCHEDALOCALE());
		return variantebean;
	}
	
	/**
	 * @param rmotivi
	 * @return
	 */
	public List<EventiMotiviVariantiBean> convertiEventi(RecMotivoVarType[] rmotivi){
		List<EventiMotiviVariantiBean> eventi = new ArrayList<EventiMotiviVariantiBean>();
		for(int i = 0; i<rmotivi.length; i++){
			RecMotivoVarType rmotivo = rmotivi[i];
			EventiMotiviVariantiBean motivo = new EventiMotiviVariantiBean();
			motivo.setIdMotivoVariante(Long.parseLong(this.setDefault(rmotivo.getIDMOTIVOVAR())));
			eventi.add(motivo);
		}
		return eventi;
	}

}
