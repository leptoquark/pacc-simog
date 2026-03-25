package it.avlp.simog.massload.util.conversion.impl;

import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.util.ConversionUtils;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;

public class ConvertAvanzamento extends ConversionUtils{

	private static ConvertAvanzamento convertAvanzamento = null;
	
	public synchronized static ConvertAvanzamento getInstance(){
		if(convertAvanzamento == null) convertAvanzamento = new ConvertAvanzamento();
		return convertAvanzamento;
	}
	
	private ConvertAvanzamento(){}
	
	public AvanzamentoBean converti(AvanzamentoType avanzamento, int numeroAvanzamento) {
		
		AvanzamentoBean avanzamentobean = new AvanzamentoBean();		
		avanzamentobean.setDataAnticipazione(PageHelper.getFormattedCalendarDate(avanzamento.getDATAANTICIPAZIONE()));
		avanzamentobean.setDataCertificato(PageHelper.getFormattedCalendarDate(avanzamento.getDATACERTIFICATO()));
		avanzamentobean.setDataRaggiungimento(PageHelper.getFormattedCalendarDate(avanzamento.getDATARAGGIUNGIMENTO()));
		avanzamentobean.setFlagPagamento(avanzamento.getFLAGPAGAMENTO().toString());
		avanzamentobean.setFlagRitardo(avanzamento.getFLAGRITARDO().toString());
		avanzamentobean.setImportoAnticipazione(avanzamento.getIMPORTOANTICIPAZIONE());
		
		// MOD PP 15.0.2009 l'attributo e' optional ma il validatore lo vuole, default a zero
		if(avanzamento.getIMPORTOCERTIFICATO() == null)
			avanzamentobean.setImportoCertificato(new BigDecimal(0));
		else
			avanzamentobean.setImportoCertificato(avanzamento.getIMPORTOCERTIFICATO());
		
		avanzamentobean.setImportoSal(avanzamento.getIMPORTOSAL());
//		XX-X :  numero calcolato tramite verifica sul db
		avanzamentobean.setNumeroAvanzamento(numeroAvanzamento);

		avanzamentobean.setNumeroGiorniProroga(avanzamento.getNUMGIORNIPROROGA());
		avanzamentobean.setNumeroGiorniScost(avanzamento.getNUMGIORNISCOST());
		avanzamentobean.setDenomStatoAvanz(avanzamento.getDENOMAVANZAMENTO());
		
		
		if(avanzamento.getIDSCHEDASIMOG() != null && !"".equals(avanzamento.getIDSCHEDASIMOG())){
			avanzamentobean.setIdAvanzamento(Long.parseLong(avanzamento.getIDSCHEDASIMOG()));
		}
		avanzamentobean.setIdLocale(avanzamento.getIDSCHEDALOCALE());
		return avanzamentobean;
	}
}
