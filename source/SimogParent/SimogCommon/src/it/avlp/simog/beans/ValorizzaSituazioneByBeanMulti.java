package it.avlp.simog.beans;

import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfaccia che definisce la valorizzazione tramite lista di bean degli stati relativi
 * alla scheda scelta,  per l'oggetto situazioneSchedeAttuale
 * 
 * @author vletizia
 *
 */
public interface ValorizzaSituazioneByBeanMulti {
	
	public void setStatoAvanzamento(List<AvanzamentoBean> listOfAvanzamenti, ArrayList<Integer>  inRichiesta );
	public void setStatoRitardo(List<R129Bean> listOfR129, ArrayList<Integer>  inRichiesta );
	public void setStatoAccordi(List<AccordoBean> listOfAccordi, ArrayList<Integer>  inRichiesta );
	public void setStatoSospensioni(List<SospensioniBean> listOfSospensioni, ArrayList<Integer>  inRichiesta );
	public void setStatoVarianti(List<VarianteBean> listOfVarianti, ArrayList<Integer>  inRichiesta );
	public void setStatoSubAppalti(List<SubappaltiBean> listOfSubAppalti, ArrayList<Integer>  inRichiesta );

}
