package it.avlp.simog.massload.cancellazione.interfaccie;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;

/**
 * Definisce il caricamento da idLocale
 * 
 * @author vletizia
 *
 */
public interface LoadByIdLocale extends LoadByCommon{

	public InfoComuniBean loadInfoComuni(String cig, String idLocale);
	
	public AggiudicazioneBean loadAggiudicazione(String cui, String idLocale);
	
	public AccordoBean loadAccordo(String idAggiudicazione, String idLocale);
	
	public AvanzamentoBean loadAvanzamento(String idAggiudicazione, String idLocale);
	
	public CollaudoBean loadCollaudo(String idAggiudicazione, String idLocale);
	
	public ConclusioneBean loadConclusione(String idAggiudicazione, String idLocale);
	
	public InizioLavoriBean loadInizioLavori(String idAggiudicazione, String idLocale);
	
	public R129Bean loadRecesso(String idAggiudicazione, String idLocale);
	
	public SospensioniBean loadSospensione(String idAggiudicazione, String idLocale);
	
	public SubappaltiBean loadSubAppalto(String idAggiudicazione, String idLocale);
	
	public VarianteBean loadVariante(String idAggiudicazione, String idLocale);
}
