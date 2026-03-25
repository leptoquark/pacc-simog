package it.avlp.simog.beans;

import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

/**
 * Interfaccia che definisce il comportamento di valorizzazione tramite bean da
 * parte dell'oggetto situazioneschedeattuale
 * 
 * @author vletizia
 *
 */
public interface ValorizzaSituazioneByBean {

	public void setStatoDatiComuni(InfoComuniBean infoComuniBean,String cig, int inRichiesta, boolean obblighiComunicativi);
	public void setStatoAggiudicazione(AggiudicazioneBean aggiudicazioneBean, int inRichiesta);
	public void setStatoInizioLavori(InizioLavoriBean inizioLavoriBean, int inRichiesta);
	public void setStatoConclusione(ConclusioneBean conclusioneBean, int inRichiesta);
	public void setStatoCollaudo(CollaudoBean collaudoBean, int inRichiesta);
}
