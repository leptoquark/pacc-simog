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

public interface LoadByIdSimog extends LoadByCommon{


	public InfoComuniBean loadInfoComuni(String idSimog);
	public AggiudicazioneBean loadAggiudicazione(String idSimog);
	public AccordoBean loadAccordo(String idSimog);
	public AvanzamentoBean loadAvanzamento(String idSimog);
	public CollaudoBean loadCollaudo(String idSimog);
	public ConclusioneBean loadConclusione(String idSimog);
	public InizioLavoriBean loadInizioLavori(String idSimog);
	public R129Bean loadRecesso(String idSimog);
	public SospensioniBean loadSospensione(String idSimog);
	public SubappaltiBean loadSubAppalto(String idSimog);
	public VarianteBean loadVariante(String idSimog);
}
