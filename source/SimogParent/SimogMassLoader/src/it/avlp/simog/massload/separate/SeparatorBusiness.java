package it.avlp.simog.massload.separate;

import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.util.conversion.SituazioneAttualeSchedeXml;
import it.avlp.simog.massload.xmlbeans.AccordiBonariType;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AdesioneType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType;
import it.avlp.simog.massload.xmlbeans.AppaltoType;
import it.avlp.simog.massload.xmlbeans.AvanzamentiType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.DatiInizioType;
import it.avlp.simog.massload.xmlbeans.DatiStipulaType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.RitardiType;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaEsclusoType;
import it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SospensioniType;
import it.avlp.simog.massload.xmlbeans.SottoEsclusoType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.massload.xmlbeans.SubappaltiType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;
import it.avlp.simog.massload.xmlbeans.VariantiType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Lo scopo di questa classe e' modificare (clonare) il tipo xml
 * DatiAggiudicazioneType, estraendo (separando) le schede che devono essere
 * modificate da quelle che devono essere inserite.
 * 
 * @author vletizia
 *
 */
public class SeparatorBusiness {
	
	/**
	 * Lo scopo di questa classe e' modificare (clonare) il tipo xml
	 * DatiAggiudicazioneType, estraendo (separando) le schede che devono essere
	 * modificate da quelle che devono essere inserite. 
	 */
	public SeparatorBusiness(){}
	
	public static final int SCHEDEINMODIFICA = 0;
	public static final int SCHEDEININSERIMENTO = 1;
	/**
	 * Ritorna una mappa con due liste una contenente le schede in modifica[0] e l'altra le schede in inserimento[1] </p>
	 * 
	 * <strong>Nota</strong>: il tipo "IdsSchedaXML" contiene tutte le reference per tracciare la posizione originale, 
	 * ivi comprese le schede multiple, dato che qui si scompone il tipo in 2 parti nel caso in cui ci sia almeno una 
	 * scheda in modifica.</p>
	 * 
	 * <strong>Attenzione</strong>: supporta solamente il tipo DatiAggiudicazioneType con una sola "scheda completa"</p>
	 * 
	 * - <strong>in modifica se</strong>:</p>
	 * 	* c'e' l'id_simog</p>
	 *  * c'e' l'id locale ed e' presente la scheda sul db</p>
	 *  
	 * - <strong>in inserimento altrimenti</strong>	</p>
	 * 	<strong>NOTA</strong>: le schede in inserimento potrebbero richiedere la cancellazione dei dati sul db, qualora presenti
	 * 			in questo caso rientrano gli osservatori regionali che non supportano la modifica tramite 
	 * 			id_locale o id_simog.
	 * 
	 * XXX: VL (1) - ho modificato la situazioneXML ora ho ulteriori informazioni quali la presenza o meno del id Locale e simog
	 * 			sarebbe opportuno modificare questa funzione di conseguenza (per evitare il moltiplicarsi di iterazioni)
	 * 
	 * 
	 * @param listOfSchede
	 * @return Map&lt;Integer, ArrayList&lt;IdsSchedaXML&gt;&gt; -> la dimensione e' 2 in posizione 0 ci sono le schede in modifica, in posizione 1 ci sono le schede in inserimento
	 * @throws Exception
	 */
	public Map<Integer, ArrayList<IdsSchedaXML>> separaSchede(List<IdsSchedaXML> listOfSchede) throws Exception{ 
		
		ArrayList<IdsSchedaXML> listOfSchedeInModifica = new ArrayList<IdsSchedaXML>();
		ArrayList<IdsSchedaXML> listOfSchedeInInserimento = new ArrayList<IdsSchedaXML>();
		
		
		
		for(IdsSchedaXML idScheda : listOfSchede){
			
			DatiAggiudicazioneType datiAggiudicazioneCorrente = idScheda.getScheda();
			SituazioneSchedeAttuale situazioneAttuale = idScheda.getSituazioneAttuale();
			SituazioneAttualeSchedeXml situazioneAttualeXml = idScheda.getSituazioneAttualeXml();
			boolean isPresentSchedaCompleta = situazioneAttualeXml.isPresentSchedaCompleta();

			// clono l'oggetto in modo da avere una copia senza legami 
			IdsSchedaXML newInstanceModifica = idScheda.clone();
			// sovrascrivo l'oggetto che andro' a settare all'interno di questo metodo.
			newInstanceModifica.setScheda(DatiAggiudicazioneType.Factory.newInstance());
			// creo una nuova situazione attuale cosi da poterne costruire una ad hoc per i nuovi dati aggiudicazione
			SituazioneAttualeSchedeXml modificaSituazioneXml = new SituazioneAttualeSchedeXml();
			
			// clono l'oggetto in modo da avere una copia senza legami 
			IdsSchedaXML newInstanceInserimento = idScheda.clone();
			// sovrascrivo l'oggetto che andro' a settare all'interno di questo metodo.
			newInstanceInserimento.setScheda(DatiAggiudicazioneType.Factory.newInstance());
			// creo una nuova situazione attuale cosi da poterne costruire una ad hoc per i nuovi dati aggiudicazione
			SituazioneAttualeSchedeXml inserisciSituazioneXml = new SituazioneAttualeSchedeXml();
			// -- end
			
			
			
/**	dati Comuni **/
			// la gestione della modifica dei dati comuni la faccio a parte in quanto cardinalita' diversa dalle schedeComplete
			boolean isDatiComuniModifica = 
				situazioneAttualeXml.isPresentDatiComuni()  
				&& (
						datiAggiudicazioneCorrente.getDatiComuni().isSetIDSCHEDASIMOG()
						|| (datiAggiudicazioneCorrente.getDatiComuni().isSetIDSCHEDALOCALE() 
								&& situazioneAttuale.getStatoDatiComuni().isEsistente()	
							)
					)
					;

			if(isDatiComuniModifica){
				newInstanceModifica.getScheda().setDatiComuni(datiAggiudicazioneCorrente.getDatiComuni());
				newInstanceModifica.getScheda().setPubblicazione(datiAggiudicazioneCorrente.getPubblicazione());
				// aggiornamento stato situazione xml - occorre effettuarlo per ogni scheda [ casi ibridi modifica/inserimento ]
				modificaSituazioneXml.setStatoXmlDatiComuni(situazioneAttualeXml);
				
			}else{
				newInstanceInserimento.getScheda().setDatiComuni(datiAggiudicazioneCorrente.getDatiComuni());
				newInstanceInserimento.getScheda().setPubblicazione(datiAggiudicazioneCorrente.getPubblicazione());
				// aggiornamento stato situazione xml - occorre effettuarlo per ogni scheda [ casi ibridi modifica/inserimento ]
				inserisciSituazioneXml.setStatoXmlDatiComuni(situazioneAttualeXml);
			}

			//
			if(datiAggiudicazioneCorrente.getSchedaCompletaArray().length > 1) throw new Exception("Dimensione inaspettata");
			
			
			if(isPresentSchedaCompleta){

				SchedaCompletaType schedaCompletaCorrente = datiAggiudicazioneCorrente.getSchedaCompletaArray(0);
				
				newInstanceModifica.getScheda().setSchedaCompletaArray(new SchedaCompletaType[]{SchedaCompletaType.Factory.newInstance()});				
				newInstanceInserimento.getScheda().setSchedaCompletaArray(new SchedaCompletaType[]{SchedaCompletaType.Factory.newInstance()});				
	
/** aggiudicazione **/	
				boolean isAggiudicazioneModifica = false;
				if(situazioneAttualeXml.isPresentAggiudicazione()){
					isAggiudicazioneModifica = separaAggiudicazioni(isAggiudicazioneModifica, schedaCompletaCorrente.getAggiudicazione(), idScheda, newInstanceInserimento, newInstanceModifica);
					
					situazioneAttualeXml.setMultilotto(schedaCompletaCorrente.getAggiudicazione().getAppalto().getFLAGAGGIUDPRINCIPALE() != null 
							&& schedaCompletaCorrente.getAggiudicazione().getAppalto().getCODICECONTRATTO() != null 
							&& schedaCompletaCorrente.getAggiudicazione().getAppalto().getCODICECONTRATTO().trim().length() > 0 );
					
					boolean principale = Costanti.FLAG_VALORE_SI.equals(String.valueOf(schedaCompletaCorrente.getAggiudicazione().getAppalto().getFLAGAGGIUDPRINCIPALE()));
					situazioneAttualeXml.setPrincipale(principale);
				}
				if(isAggiudicazioneModifica){
					modificaSituazioneXml.setStatoXmlAggiudicazione(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlAggiudicazione(situazioneAttualeXml);
				}
				
/** aggiudicazione **/	
				boolean isAdesioneModifica = false;
				if(situazioneAttualeXml.isPresentAdesione()){
					isAdesioneModifica = separaAdesioni(isAggiudicazioneModifica, schedaCompletaCorrente.getAdesione(), idScheda, newInstanceInserimento, newInstanceModifica);
				}
				if(isAdesioneModifica){
					modificaSituazioneXml.setStatoXmlAdesione(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlAdesione(situazioneAttualeXml);
				}
/** sottosoglia **/	
				boolean isSottosogliaModifica = false;
				if(situazioneAttualeXml.isPresentSottosoglia()){
					isSottosogliaModifica = separaSottosoglia(isSottosogliaModifica, schedaCompletaCorrente.getSottosoglia(), idScheda, newInstanceInserimento, newInstanceModifica);
				}
				if(isSottosogliaModifica){
					modificaSituazioneXml.setStatoXmlSottosoglia(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlSottosoglia(situazioneAttualeXml);
				}
/** Escluso **/	
				boolean isEsclusoModifica = false;
				if(situazioneAttualeXml.isPresentEscluso()){
					isEsclusoModifica = separaEsclusi(isEsclusoModifica, schedaCompletaCorrente.getEscluso(), idScheda, newInstanceInserimento, newInstanceModifica);
				}
				if(isEsclusoModifica){
					modificaSituazioneXml.setStatoXmlEscluso(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlEscluso(situazioneAttualeXml);
				}

/** Inizio Lavori **/				
				boolean isInizioModifica = false;
				if(situazioneAttualeXml.isPresentInizioLavori()){
					isInizioModifica = separaInizioLavori(isInizioModifica, schedaCompletaCorrente.getDatiInizio(), idScheda , newInstanceInserimento, newInstanceModifica);
				}	
				if(isInizioModifica){
					modificaSituazioneXml.setStatoXmlInizio(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlInizio(situazioneAttualeXml);
				}
				
/** Stipula **/				
				boolean isStipulaModifica = false;
				if(situazioneAttualeXml.isPresentStipula()){
					isStipulaModifica = separaStipula(isStipulaModifica, schedaCompletaCorrente.getDatiStipula(), idScheda , newInstanceInserimento, newInstanceModifica);
				}	
				if(isStipulaModifica){
					modificaSituazioneXml.setStatoXmlStipula(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlStipula(situazioneAttualeXml);
				}

/**	avanzamenti **/

				boolean isAvanzamentiModifica = false;				
				if(situazioneAttualeXml.isPresentAvanzamenti()){
					isAvanzamentiModifica = separaAvanzamenti(isAvanzamentiModifica, schedaCompletaCorrente.getDatiAvanzamenti().getAvanzamentoArray(), idScheda, newInstanceInserimento, newInstanceModifica);
				}

				modificaSituazioneXml.setStatoXmlAvanzamenti(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, false);
				inserisciSituazioneXml.setStatoXmlAvanzamenti(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, true);

				// PP 31/01/2012 devo impostare anche la situazione dei daticomuni usata nella gestione del merge varianti
				modificaSituazioneXml.setStatoXmlDatiComuni(situazioneAttualeXml);
				inserisciSituazioneXml.setStatoXmlDatiComuni(situazioneAttualeXml);
				
/** conclusione **/
				boolean isConclusioneModifica = false;
				if(situazioneAttualeXml.isPresentConclusione()){
					isConclusioneModifica = separaConclusione(isConclusioneModifica, schedaCompletaCorrente.getDatiConclusione(), idScheda, newInstanceInserimento, newInstanceModifica);
				}
				if(isConclusioneModifica){
					modificaSituazioneXml.setStatoXmlConclusione(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlConclusione(situazioneAttualeXml);
				}
/** collaudo **/
				boolean isCollaudoModifica = false;
				if(situazioneAttualeXml.isPresentCollaudo()){
					isCollaudoModifica = separaCollaudo(isCollaudoModifica, schedaCompletaCorrente.getDatiCollaudo(), idScheda, newInstanceInserimento, newInstanceModifica);
				}
				if(isCollaudoModifica){
					modificaSituazioneXml.setStatoXmlCollaudo(situazioneAttualeXml);
				}else{
					inserisciSituazioneXml.setStatoXmlCollaudo(situazioneAttualeXml);
				}
/** accordi **/	
				boolean isAccordiModifica = false;				
				if(situazioneAttualeXml.isPresentAccordi()){
					isAccordiModifica = separaAccordi(isAccordiModifica, schedaCompletaCorrente.getDatiAccordi().getAccordoBonarioArray(), idScheda, newInstanceInserimento, newInstanceModifica);
				}

				modificaSituazioneXml.setStatoXmlAccordi(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, false);
				inserisciSituazioneXml.setStatoXmlAccordi(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, true);

/** ritardi **/	
				boolean isRitardiModifica = false;
				if(situazioneAttualeXml.isPresentRitardo()){
					isRitardiModifica = separaRitardi(isRitardiModifica, schedaCompletaCorrente.getDatiRitardi().getRitardoArray(), idScheda, newInstanceInserimento, newInstanceModifica);
				}

				modificaSituazioneXml.setStatoXmlRitardi(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, false);
				inserisciSituazioneXml.setStatoXmlRitardi(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, true);

/** sospensioni **/	
				boolean isSospensioniModifica = false;			
				if(situazioneAttualeXml.isPresentSospensioni()){
					isSospensioniModifica = separaSospensioni(isSospensioniModifica, schedaCompletaCorrente.getDatiSospensioni().getSospensioneArray(), idScheda, newInstanceInserimento, newInstanceModifica);
				}

				modificaSituazioneXml.setStatoXmlSospensioni(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, false);
				inserisciSituazioneXml.setStatoXmlSospensioni(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, true);


/** subappalti **/	
				boolean isSubappaltiModifica = false;
				if(situazioneAttualeXml.isPresentSubAppalti()){
					separaSubappalti(isSubappaltiModifica, schedaCompletaCorrente.getDatiSubappalti().getSubappaltoArray(), idScheda, newInstanceInserimento, newInstanceModifica);
				}

				modificaSituazioneXml.setStatoXmlSubappalti(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, false);
				inserisciSituazioneXml.setStatoXmlSubappalti(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, true);

/** varianti **/
				boolean isVariantiModifica = false;
				if(situazioneAttualeXml.isPresentVarianti()){
					separaVarianti(isVariantiModifica, schedaCompletaCorrente.getDatiVarianti().getVarianteArray(), idScheda, newInstanceInserimento, newInstanceModifica);
				}

				modificaSituazioneXml.setStatoXmlVarianti(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, false);
				inserisciSituazioneXml.setStatoXmlVarianti(situazioneAttualeXml, newInstanceInserimento, newInstanceModifica, true);


				
	//			 SE RISULTA QUALCHE SCHEDA IN MODIFICA 
				if(isDatiComuniModifica || isAggiudicazioneModifica   || isAdesioneModifica 
						|| isSottosogliaModifica|| isEsclusoModifica
						|| isInizioModifica || isStipulaModifica
						|| isAvanzamentiModifica || isConclusioneModifica
						|| isCollaudoModifica || isAccordiModifica 
						|| isRitardiModifica || isSospensioniModifica 
						|| isSubappaltiModifica || isVariantiModifica){

					// MODIFICA
					// setto il flag di cancellazione a false
					newInstanceModifica.setNeedCancellazione(false);
					// aggiungo alla lista
					listOfSchedeInModifica.add(newInstanceModifica);
					// aggiorno la situazione xml
					newInstanceModifica.setSituazioneAttualeXml(modificaSituazioneXml);
					
					// INSERIMENTO
					// setto il flag di cancellazione a false
					newInstanceInserimento.setNeedCancellazione(false);
					// aggiungo alla lista
					listOfSchedeInInserimento.add(newInstanceInserimento);
					// aggiorno la situazione xml
					newInstanceInserimento.setSituazioneAttualeXml(inserisciSituazioneXml);
					
				}else{
					/** 
					 * Setting delle posizioni originali nel file xml delle schede multiple, mi serve in caso di errori o warn nel feedback
					 * per valorizzare il campo "progressivo" !
					 * NOTA: nell'if sopra era gia previsto, nel solo inserimento invece no perche utilizzava idSchedaXml originale
					 * 			e quindi senza gli array delle liste valorizzate.
					 * **/
					idScheda.setAccordiPosizioneInserimento(newInstanceInserimento.getAccordiPosizioneInserimento());
					idScheda.setAvanzamentiPosizioneInserimento(newInstanceInserimento.getAvanzamentiPosizioneInserimento());
					idScheda.setRitardiPosizioneInserimento(newInstanceInserimento.getRitardiPosizioneInserimento());
					idScheda.setSospensioniPosizioneInserimento(newInstanceInserimento.getSospensioniPosizioneInserimento());
					idScheda.setSubappaltiPosizioneInserimento(newInstanceInserimento.getSubappaltiPosizioneInserimento());
					idScheda.setVariantiPosizioneInserimento(newInstanceInserimento.getVariantiPosizioneInserimento());
					
					// SE NON SONO STATE RILEVATE MODIFICHE ALLORA E' TUTTO INSERIMENTO
					/** XX-X: qui rientra il caso della cancellazione pre inserimento! **/

					this.fillIsSchedaInserimentoSenzaId(idScheda);
					listOfSchedeInInserimento.add(idScheda);
				}
			
			}else{
				if(isDatiComuniModifica){
					// MODIFICA
					// setto il flag di cancellazione a false
					newInstanceModifica.setNeedCancellazione(false);
					// aggiungo alla lista
					listOfSchedeInModifica.add(newInstanceModifica);
				}else{
					this.fillIsSchedaInserimentoSenzaId(idScheda);
					listOfSchedeInInserimento.add(idScheda);
				}
			}
		}
			
		Map<Integer, ArrayList<IdsSchedaXML>> schedeSeparate = new TreeMap<Integer, ArrayList<IdsSchedaXML>>();
		
		schedeSeparate.put(SeparatorBusiness.SCHEDEINMODIFICA, listOfSchedeInModifica);
		schedeSeparate.put(SeparatorBusiness.SCHEDEININSERIMENTO, listOfSchedeInInserimento);
		return schedeSeparate;
	}
	
	/**
	 * Metodo che si occupa di settare nell'oggetto di cui primo parametro il flag
	 * che determina se occorra effettuare una cancellazione by CIG prima dell'inserimento
	 * Filosofia butta tutto e reinserisci, NOTA che al momento si e' pensato di effettuare
	 * la cancellazione LOGICA dei record precedenti.
	 * 
	 * NOTA: questa funzione viene chiamata, nel caso dell'assenza di id e in presenza di soli id_locale
	 * 
	 * @param idScheda
	 */
	private void fillIsSchedaInserimentoSenzaId(IdsSchedaXML idScheda){
		
		SituazioneAttualeSchedeXml situazioneAttualeXml = idScheda.getSituazioneAttualeXml();
		SituazioneSchedeAttuale situazioneAttualeDb = idScheda.getSituazioneAttuale();
		boolean needCancellazione = false;
		
		// obligatorio.. NON posso cancellare tutto ogni volta che mi mandano dati comuni..
		// NON entra qua dentro se solo dati comuni..
		needCancellazione = needCancellazione
			|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentDatiComuni(), 
											situazioneAttualeXml.isPresentDatiComuniIdLocale(), 
											situazioneAttualeDb.getStatoDatiComuni().isEsistente());

		
		if(situazioneAttualeXml.isPresentSchedaCompleta()){
			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentAggiudicazione(), 
												situazioneAttualeXml.isPresentAggiudicazioneIdLocale(),
												situazioneAttualeDb.getStatoAggiudicazione().isEsistente() );
			
			needCancellazione = needCancellazione 
			|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentAdesione(), 
											situazioneAttualeXml.isPresentAdesioneIdLocale(),
											situazioneAttualeDb.getStatoAdesione().isEsistente() );
			
			needCancellazione = needCancellazione 
			|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentSottosoglia(), 
											situazioneAttualeXml.isPresentSottosogliaIdLocale(),
											situazioneAttualeDb.getStatoSottosoglia().isEsistente() );
			needCancellazione = needCancellazione 
			|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentEscluso(), 
											situazioneAttualeXml.isPresentEsclusoIdLocale(),
											situazioneAttualeDb.getStatoEscluso().isEsistente() );

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentInizioLavori(), 
												situazioneAttualeXml.isPresentInizioLavoriIdLocale(), 
												situazioneAttualeDb.getStatoInizioLavori().isEsistente());
			
			needCancellazione = needCancellazione 
			|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentStipula(), 
											situazioneAttualeXml.isPresentStipulaIdLocale(), 
											situazioneAttualeDb.getStatoStipula().isEsistente());

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentAvanzamenti(), 
												situazioneAttualeXml.getIsPresentAvanzamentiIdLocale(),
												situazioneAttualeDb.getStatoAvanzamento() != null 
													&& situazioneAttualeDb.getStatoAvanzamento().size() > 0);

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentConclusione(), 
												situazioneAttualeXml.isPresentConclusioneIdLocale(),
												situazioneAttualeDb.getStatoConclusione().isEsistente());

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentCollaudo(), 
												situazioneAttualeXml.isPresentCollaudoIdLocale(),
												situazioneAttualeDb.getStatoCollaudo().isEsistente());

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentAccordi(),
												situazioneAttualeXml.getIsPresentAccordiIdLocale(),
												situazioneAttualeDb.getStatoAccordi() != null 
													&& situazioneAttualeDb.getStatoAccordi().size() > 0);

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentRitardo(), 
												situazioneAttualeXml.getIsPresentRitardoIdLocale(),
												situazioneAttualeDb.getStatoRitardo() != null 
													&& situazioneAttualeDb.getStatoRitardo().size() > 0);

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentSospensioni(), 
												situazioneAttualeXml.getIsPresentSospensioniIdSimog(),
												situazioneAttualeDb.getStatoSospensioni() != null 
													&& situazioneAttualeDb.getStatoSospensioni().size() > 0);

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentSubAppalti(), 
												situazioneAttualeXml.getIsPresentSubAppaltiIdLocale(),
												situazioneAttualeDb.getStatoSubAppalti() != null 
													&& situazioneAttualeDb.getStatoSubAppalti().size() > 0);

			
			needCancellazione = needCancellazione 
				|| this.needSchedaCancellazione(situazioneAttualeXml.isPresentVarianti(), 
												situazioneAttualeXml.getIsPresentVariantiIdLocale(),
												situazioneAttualeDb.getStatoVarianti() != null 
													&& situazioneAttualeDb.getStatoVarianti().size() > 0);

		}

		idScheda.setNeedCancellazione(needCancellazione);
	}
	
	/**
	 * Questo metodo lavora per la scheda "Singola"
	 * Tre casi:
	 * (1) - Se NON e' presente la scheda sul db NON c'e' bisogno di cancellazione return false;</p>
	 * (2) - Se e' presente la scheda sul db ed e' presente la scheda nel xml ed NON e' presente 
	 * 			idLocale deve essere cancellata quindi ritorno true</p>
	 * (3) - Se e' presente la scheda sul db ed e' presente la scheda nel xml ed e' presente 
	 * 			idLocale NON deve essere cancellata quindi ritorno false</p>
	 * (4) - Se e presente la scheda sul db e non e' presente la scheda sull'xml return true 
	 * 
	 * 
	 * @param isSchedaPresentOnXml
	 * @param isPresentIdLocale
	 * @param isSchedaPresentOnDb
	 * @return
	 */
// FIXME: !* PP MA IL CORRISPONDENTE CON ID SIMOG ???	
	private boolean needSchedaCancellazione(boolean isSchedaPresentOnXml, boolean isPresentIdLocale, boolean isSchedaPresentOnDb ){
		// se la scheda e' presente nel file xml
		if(isSchedaPresentOnXml){
			
			// se la scheda NON e' presente sul db ritorna false
			if(!isSchedaPresentOnDb) return false;
			
			// ritorna true solamente se non e' presente l'idLocale (quindi gestione senza id)
			return !isPresentIdLocale;
			
		// se la scheda non e' presente sul file xml
		}else{
			
			// se e' presente sul db
			if(isSchedaPresentOnDb) return true;
		}
		
		// altrimenti ritorna false
		return false;
	}
	/**
	 * Questo metodo lavora per la scheda "Multipla", qui il discriminante sono schede senza 
	 * idLocale e la presenza sul db di schede dello stesso tipo, siccome non posso individuare
	 * se si tratti di una scheda gia presente sul db devo effettuare la modifica.
	 * 
	 * (1) - Se NON sono presenti schede dello stesso tipo sul db NON c'e' bisogno di cancellazione return false;</p>
	 * (2) - Se e' presente una scheda dello stesso tipo sul db ed e' presente la scheda nel xml ed NON e' presente 
	 * 			idLocale deve essere cancellata quindi ritorno true</p>
	 * (3) - Se e' presente la scheda sul db ed e' presente la scheda nel xml ed e' presente 
	 * 			idLocale NON deve essere cancellata quindi ritorno false</p>
	 * 
	 * @param isPresentScheda
	 * @param arePresentIdLocale
	 * @param isPresentSomeSchedaOfSameKindOnDb
	 * @return
	 */
//	FIXME: !* PP MA IL CORRISPONDENTE CON ID SIMOG ???	
	private boolean needSchedaCancellazione(boolean isPresentScheda, boolean[] arePresentIdLocale, boolean isPresentSomeSchedaOfSameKindOnDb){

		
		
		// Se la scheda e' presente sul xml
		if(isPresentScheda){		
			
			// se NON e' presente qualche scheda dello stesso tipo sul db NON c'e' bisogno di cancellazione
			if(!isPresentSomeSchedaOfSameKindOnDb) return false;
			
			boolean esito = true;
			
			// altrimenti controlla se esistono gli id_locali (gestione id_locale)
			for(int i = 0; i < arePresentIdLocale.length; i++){
				esito = esito && !arePresentIdLocale[i];
			}			
			return esito;
		
		// se non e' presente la scheda sul xml ma e' presente sul db devi cancellare	
		}else{
			if(isPresentSomeSchedaOfSameKindOnDb) return true;
		}
		
		// altri casi false
		return false;

	}
	
	/**
	 * @param isAvanzamentiModifica
	 * @param arrayOfAvanzamento
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaAvanzamenti(boolean isAvanzamentiModifica, AvanzamentoType[] arrayOfAvanzamento, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		// liste switch inserimento / modifica
		ArrayList<AvanzamentoType> listOfAvanzamentiModifica = new ArrayList<AvanzamentoType>();
		ArrayList<AvanzamentoType> listOfAvanzamentiInserimento = new ArrayList<AvanzamentoType>();
		// liste per la posizione (serve in caso di fallimento operazione devo indicare indice dell'elemento)
		ArrayList<Integer> listaPosModifica = new ArrayList<Integer>();
		ArrayList<Integer> listaPosInserimento = new ArrayList<Integer>();
		
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		for(int i = 0; i < arrayOfAvanzamento.length; i++){
					
			AvanzamentoType schedaAvanzamento = arrayOfAvanzamento[i];
			// variabile che viene valorizzata all'interno del "situazioneAttuale.controlla" in modo tale di poter accedere per posizione alla scheda di interesse
			Integer index = new Integer(0);
			boolean bindIdLocale = false;
			// se e' settato l'id locale
			if(schedaAvanzamento.isSetIDSCHEDALOCALE() && !schedaAvanzamento.isSetIDSCHEDASIMOG()){
				// controlla se trovi l'id locale di questa scheda dalla situazione caricata dal db
				bindIdLocale = situazioneAttuale.controlla(schedaAvanzamento.getIDSCHEDALOCALE(), situazioneAttuale.getStatoAvanzamento(), index);
				if(bindIdLocale){
					// se l'hai trovata controlla che lo stato sia esistente
					bindIdLocale = bindIdLocale && situazioneAttuale.getStatoAvanzamento().get(index.intValue()).isEsistente();
					// se e' vero e' ua modifica
					if(bindIdLocale){
						isAvanzamentiModifica = true;
						listOfAvanzamentiModifica.add(schedaAvanzamento);
						listaPosModifica.add(i);
					}
					// se e' falso e' un inserimento
					else{
						listOfAvanzamentiInserimento.add(schedaAvanzamento);
						listaPosInserimento.add(i);
					}
					// se e' falso e' un inserimento
				}else{
					listOfAvanzamentiInserimento.add(schedaAvanzamento);
					listaPosInserimento.add(i);
				}
			// se e' settato l'id simog e' una modifica
			}else if(!schedaAvanzamento.isSetIDSCHEDALOCALE() && schedaAvanzamento.isSetIDSCHEDASIMOG()){
				isAvanzamentiModifica = true;
				listaPosModifica.add(i);
				listOfAvanzamentiModifica.add(schedaAvanzamento);
			// se NON sono settati ne l'id locale ne l'id simog e' un'inserimento
			}else if(schedaAvanzamento.isSetIDSCHEDALOCALE() && schedaAvanzamento.isSetIDSCHEDASIMOG()){
				isAvanzamentiModifica = true;
				listaPosModifica.add(i);
				listOfAvanzamentiModifica.add(schedaAvanzamento);
			// se NON sono settati ne l'id locale ne l'id simog e' un'inserimento
			}else if(!schedaAvanzamento.isSetIDSCHEDALOCALE() && !schedaAvanzamento.isSetIDSCHEDASIMOG()){
				listOfAvanzamentiInserimento.add(schedaAvanzamento);
				listaPosInserimento.add(i);
			}
		}
		if(listOfAvanzamentiModifica.size() > 0){
			AvanzamentiType avanzamentiModifica = AvanzamentiType.Factory.newInstance();
			avanzamentiModifica.setAvanzamentoArray((AvanzamentoType[])listOfAvanzamentiModifica.toArray(new AvanzamentoType[listOfAvanzamentiModifica.size()]));
			
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiAvanzamenti(avanzamentiModifica);
			newInstanceModifica.setAvanzamentiPosizioneModifica(newInstanceModifica.convertiPosizioneArray(listaPosModifica));
		}
		if(listOfAvanzamentiInserimento.size() > 0){
			AvanzamentiType avanzamentiInserimento = AvanzamentiType.Factory.newInstance();
			avanzamentiInserimento.setAvanzamentoArray((AvanzamentoType[])listOfAvanzamentiInserimento.toArray(new AvanzamentoType[listOfAvanzamentiInserimento.size()]));
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiAvanzamenti(avanzamentiInserimento);
			newInstanceInserimento.setAvanzamentiPosizioneInserimento(newInstanceInserimento.convertiPosizioneArray(listaPosInserimento));
		}
		return isAvanzamentiModifica;
	}
	/**
	 * @param isAggiudicazioneModifica
	 * @param aggiudicazione
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaAggiudicazioni(boolean isAggiudicazioneModifica, AggiudicazioneType aggiudicazione, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){

		AppaltoType appalto = aggiudicazione.getAppalto();
		
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		isAggiudicazioneModifica = appalto.isSetIDSCHEDASIMOG()
										|| (appalto.isSetIDSCHEDALOCALE() 
												&& situazioneAttuale.getStatoAggiudicazione().isEsistente()	);

		// se non e' in modifica, devo controllare se esiste idLocale
		if(!isAggiudicazioneModifica){
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setAggiudicazione(aggiudicazione);
		}else{
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setAggiudicazione(aggiudicazione);
		}return isAggiudicazioneModifica;
	}
	
	/**
	 * @param isAdesioneModifica
	 * @param adesione
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaAdesioni(boolean isAdesioneModifica, AdesioneType adesione, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){

		AppaltoAdesioneType appalto = adesione.getAppalto();
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		isAdesioneModifica = appalto.isSetIDSCHEDASIMOG()
										|| (appalto.isSetIDSCHEDALOCALE() 
												&& situazioneAttuale.getStatoAdesione().isEsistente()	);

		// se non e' in modifica, devo controllare se esiste idLocale
		if(!isAdesioneModifica){
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setAdesione(adesione);
		}else{
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setAdesione(adesione);
		}return isAdesioneModifica;
	}
	
	/**
	 * @param isSottosogliaModifica
	 * @param sottosoglia
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaSottosoglia(boolean isSottosogliaModifica, SchedaSottosogliaType sottosoglia, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){

		SottoEsclusoType appalto = sottosoglia.getAppalto();
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		
		isSottosogliaModifica = appalto.isSetIDSCHEDASIMOG()
										|| (appalto.isSetIDSCHEDALOCALE() 
												&& situazioneAttuale.getStatoSottosoglia().isEsistente()	);

		// se non e' in modifica, devo controllare se esiste idLocale
		if(!isSottosogliaModifica){
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setSottosoglia(sottosoglia);
		}else{
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setSottosoglia(sottosoglia);
		}return isSottosogliaModifica;
	}
	
	/**
	 * @param isEsclusoModifica
	 * @param escluso
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaEsclusi(boolean isEsclusoModifica, SchedaEsclusoType escluso, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){

		SottoEsclusoType appalto = escluso.getAppalto();
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		isEsclusoModifica = appalto.isSetIDSCHEDASIMOG()
										|| (appalto.isSetIDSCHEDALOCALE() 
												&& situazioneAttuale.getStatoEscluso().isEsistente()	);

		// se non e' in modifica, devo controllare se esiste idLocale
		if(!isEsclusoModifica){
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setEscluso(escluso);
		}else{
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setEscluso(escluso);
		}return isEsclusoModifica;
	}
	/**
	 * @param isInizioModifica
	 * @param datiInizio
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaInizioLavori(boolean isInizioModifica, DatiInizioType datiInizio, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();

		InizioType inizio = datiInizio.getInizio();
		isInizioModifica =  inizio.isSetIDSCHEDASIMOG()
								|| (inizio.isSetIDSCHEDALOCALE() 
										&& situazioneAttuale.getStatoInizioLavori().isEsistente()	);

		// se non e' in modifica, devo controllare se esiste idLocale
		if(!isInizioModifica){
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiInizio(datiInizio);
		}else{
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiInizio(datiInizio);
		}return isInizioModifica;
	}
	
	/**
	 * @param isStipulaModifica
	 * @param datiInizio
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaStipula(boolean isStipulaModifica, DatiStipulaType datiInizio, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();

		StipulaType stipula = datiInizio.getStipula();
		isStipulaModifica =  stipula.isSetIDSCHEDASIMOG()
								|| (stipula.isSetIDSCHEDALOCALE() 
										&& situazioneAttuale.getStatoStipula().isEsistente()	);

		// se non e' in modifica, devo controllare se esiste idLocale
		if(!isStipulaModifica){
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiStipula(datiInizio);
		}else{
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiStipula(datiInizio);
		}return isStipulaModifica;
	}
	/**
	 * @param isConclusioneModifica
	 * @param conclusione
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaConclusione(boolean isConclusioneModifica, ConclusioneType conclusione, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();

		isConclusioneModifica = conclusione.isSetIDSCHEDASIMOG()
			|| (conclusione.isSetIDSCHEDALOCALE()
					&& situazioneAttuale.getStatoConclusione().isEsistente());

		if(isConclusioneModifica){
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiConclusione(conclusione);
		}else{
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiConclusione(conclusione);

		}return isConclusioneModifica;
	}
	/**
	 * @param isCollaudoModifica
	 * @param datiCollaudo
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaCollaudo(boolean isCollaudoModifica, DatiCollaudoType datiCollaudo, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();

		CollaudoType collaudo = datiCollaudo.getCollaudo();
		isCollaudoModifica = collaudo.isSetIDSCHEDASIMOG()
			|| (collaudo.isSetIDSCHEDALOCALE()
				&& situazioneAttuale.getStatoCollaudo().isEsistente());

		if(isCollaudoModifica){
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiCollaudo(datiCollaudo);
		}else{
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiCollaudo(datiCollaudo);
		}return isCollaudoModifica;
	}
	
	/**
	 * @param isAccordiModifica
	 * @param arrayOfAccordi
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaAccordi(boolean isAccordiModifica, AccordoBonarioType[] arrayOfAccordi, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		ArrayList<AccordoBonarioType> listOfAccordiModifica = new ArrayList<AccordoBonarioType>();
		ArrayList<AccordoBonarioType> listOfAccordiInserimento = new ArrayList<AccordoBonarioType>();
		
		ArrayList<Integer> listaPosModifica = new ArrayList<Integer>();
		ArrayList<Integer> listaPosInserimento = new ArrayList<Integer>();
		for(int i1 = 0; i1 < arrayOfAccordi.length; i1++){
					
			AccordoBonarioType schedaAccordo = arrayOfAccordi[i1];
			// valorizzato in "situazioneAttuale.controlla"
			Integer index = new Integer(0);
			boolean bindIdLocale = false;
			// se e' settato l'id locale
			if(!schedaAccordo.isSetIDSCHEDASIMOG() && schedaAccordo.isSetIDSCHEDALOCALE()){
				// controlla se trovi l'id locale di questa scheda dalla situazione caricata dal db
				bindIdLocale = situazioneAttuale.controlla(schedaAccordo.getIDSCHEDALOCALE(), situazioneAttuale.getStatoAccordi(), index);
				if(bindIdLocale){
					// se l'hai trovata controlla che lo stato sia esistente
					bindIdLocale = bindIdLocale && situazioneAttuale.getStatoAccordi().get(index.intValue()).isEsistente();
					if(bindIdLocale){
						listOfAccordiModifica.add(schedaAccordo);
						listaPosModifica.add(i1);
						isAccordiModifica = true;
					}
					else{
						listOfAccordiInserimento.add(schedaAccordo);
						listaPosInserimento.add(i1);
					}
				}else{
					listOfAccordiInserimento.add(schedaAccordo);
					listaPosInserimento.add(i1);
				}
			}else if(schedaAccordo.isSetIDSCHEDASIMOG() && !schedaAccordo.isSetIDSCHEDALOCALE()){
				listOfAccordiModifica.add(schedaAccordo);
				listaPosModifica.add(i1);
				isAccordiModifica = true;
			}else if(schedaAccordo.isSetIDSCHEDASIMOG() && schedaAccordo.isSetIDSCHEDALOCALE()){
				listOfAccordiModifica.add(schedaAccordo);
				listaPosModifica.add(i1);
				isAccordiModifica = true;
			}else if(!schedaAccordo.isSetIDSCHEDALOCALE() && !schedaAccordo.isSetIDSCHEDASIMOG()){
				listOfAccordiInserimento.add(schedaAccordo);
				listaPosInserimento.add(i1);
			}
		}
		if(listOfAccordiModifica.size() > 0){
			AccordiBonariType accordiModifica = AccordiBonariType.Factory.newInstance();
			accordiModifica.setAccordoBonarioArray((AccordoBonarioType[])listOfAccordiModifica.toArray(new AccordoBonarioType[listOfAccordiModifica.size()]));
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiAccordi(accordiModifica);
			newInstanceModifica.setAccordiPosizioneModifica(newInstanceModifica.convertiPosizioneArray(listaPosModifica));

		}
		if(listOfAccordiInserimento.size() > 0){
			AccordiBonariType accordiInserimento = AccordiBonariType.Factory.newInstance();
			accordiInserimento.setAccordoBonarioArray((AccordoBonarioType[])listOfAccordiInserimento.toArray(new AccordoBonarioType[listOfAccordiInserimento.size()]));
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiAccordi(accordiInserimento);
			newInstanceInserimento.setAccordiPosizioneInserimento(newInstanceInserimento.convertiPosizioneArray(listaPosInserimento));

		}return isAccordiModifica;

	}
	
	/**
	 * @param isRitardiModifica
	 * @param arrayOfritardi
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaRitardi(boolean isRitardiModifica, RitardoType[] arrayOfritardi, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		ArrayList<RitardoType> listOfRitardiModifica = new ArrayList<RitardoType>();
		ArrayList<RitardoType> listOfRitardiInserimento = new ArrayList<RitardoType>();
		
		ArrayList<Integer> listaPosModifica = new ArrayList<Integer>();
		ArrayList<Integer> listaPosInserimento = new ArrayList<Integer>();
		for(int i1 = 0; i1 < arrayOfritardi.length; i1++){
					
			RitardoType schedaRitardo = arrayOfritardi[i1];
			Integer index = new Integer(0);
			boolean bindIdLocale = false;
			// se e' settato l'id locale
			if(!schedaRitardo.isSetIDSCHEDASIMOG() && schedaRitardo.isSetIDSCHEDALOCALE()){
				// controlla se trovi l'id locale di questa scheda dalla situazione caricata dal db
				bindIdLocale = situazioneAttuale.controlla(schedaRitardo.getIDSCHEDALOCALE(), situazioneAttuale.getStatoRitardo(), index);
				if(bindIdLocale){
					// se l'hai trovata controlla che lo stato sia esistente
					bindIdLocale = bindIdLocale && situazioneAttuale.getStatoRitardo().get(index.intValue()).isEsistente();
					if(bindIdLocale){
						listOfRitardiModifica.add(schedaRitardo);
						listaPosModifica.add(i1);
						isRitardiModifica = true;
					}
					else{
						listOfRitardiInserimento.add(schedaRitardo);
						listaPosInserimento.add(i1);
					}
				}
				else{
					listOfRitardiInserimento.add(schedaRitardo);
					listaPosInserimento.add(i1);
				}
			}else if(schedaRitardo.isSetIDSCHEDASIMOG() && !schedaRitardo.isSetIDSCHEDALOCALE()){
				isRitardiModifica = true;
				listOfRitardiModifica.add(schedaRitardo);
				listaPosModifica.add(i1);
			}else if(schedaRitardo.isSetIDSCHEDASIMOG() && schedaRitardo.isSetIDSCHEDALOCALE()){
				isRitardiModifica = true;
				listOfRitardiModifica.add(schedaRitardo);
				listaPosModifica.add(i1);
			}else if(!schedaRitardo.isSetIDSCHEDALOCALE() && !schedaRitardo.isSetIDSCHEDASIMOG()){
				listOfRitardiInserimento.add(schedaRitardo);
				listaPosInserimento.add(i1);
			}
		}
		if(listOfRitardiModifica.size() > 0){
			RitardiType ritardiModifica = RitardiType.Factory.newInstance();
			ritardiModifica.setRitardoArray((RitardoType[]) listOfRitardiModifica.toArray(new RitardoType[listOfRitardiModifica.size()]));
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiRitardi(ritardiModifica);
			newInstanceModifica.setRitardiPosizioneModifica(newInstanceModifica.convertiPosizioneArray(listaPosModifica));

		}
		if(listOfRitardiInserimento.size() > 0){
			RitardiType ritardiInserimento = RitardiType.Factory.newInstance();
			ritardiInserimento.setRitardoArray((RitardoType[]) listOfRitardiInserimento.toArray(new RitardoType[listOfRitardiInserimento.size()]));
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiRitardi(ritardiInserimento);
			newInstanceInserimento.setRitardiPosizioneInserimento(newInstanceInserimento.convertiPosizioneArray(listaPosInserimento));

		}return isRitardiModifica;
	}
	
	public boolean separaSospensioni(boolean isSospensioniModifica, SospensioneType[] arrayOfSospensioni, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
	
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		ArrayList<SospensioneType> listOfSospensioniModifica = new ArrayList<SospensioneType>();
		ArrayList<SospensioneType> listOfSospensioniInserimento = new ArrayList<SospensioneType>();
		
		ArrayList<Integer> listaPosModifica = new ArrayList<Integer>();
		ArrayList<Integer> listaPosInserimento = new ArrayList<Integer>();
		for(int i1 = 0; i1 < arrayOfSospensioni.length; i1++){
					
			SospensioneType schedaSospensione = arrayOfSospensioni[i1];
			Integer index = new Integer(0);
			boolean bindIdLocale = false;
			// se e' settato l'id locale
			if(!schedaSospensione.isSetIDSCHEDASIMOG() && schedaSospensione.isSetIDSCHEDALOCALE()){
				// controlla se trovi l'id locale di questa scheda dalla situazione caricata dal db
				bindIdLocale = situazioneAttuale.controlla(schedaSospensione.getIDSCHEDALOCALE(), situazioneAttuale.getStatoSospensioni(), index);
				if(bindIdLocale){
					// se l'hai trovata controlla che lo stato sia esistente
					bindIdLocale = bindIdLocale && situazioneAttuale.getStatoSospensioni().get(index.intValue()).isEsistente();
					if(bindIdLocale){
						listOfSospensioniModifica.add(schedaSospensione);
						listaPosModifica.add(i1);
						isSospensioniModifica = true;
					}
					else{
						listOfSospensioniInserimento.add(schedaSospensione);
						listaPosInserimento.add(i1);
					}
				}
				else{
					listOfSospensioniInserimento.add(schedaSospensione);
					listaPosInserimento.add(i1);
				}
			}else if(schedaSospensione.isSetIDSCHEDASIMOG() && !schedaSospensione.isSetIDSCHEDALOCALE()){
				listOfSospensioniModifica.add(schedaSospensione);
				listaPosModifica.add(i1);
				isSospensioniModifica = true;
			}else if(schedaSospensione.isSetIDSCHEDASIMOG() && schedaSospensione.isSetIDSCHEDALOCALE()){
				listOfSospensioniModifica.add(schedaSospensione);
				listaPosModifica.add(i1);
				isSospensioniModifica = true;
			}else if(!schedaSospensione.isSetIDSCHEDALOCALE() && !schedaSospensione.isSetIDSCHEDASIMOG() ){
				listOfSospensioniInserimento.add(schedaSospensione);
				listaPosInserimento.add(i1);
			}
		}
		if(listOfSospensioniModifica.size() > 0){
			SospensioniType sospensioniModifica = SospensioniType.Factory.newInstance();
			sospensioniModifica.setSospensioneArray((SospensioneType[])listOfSospensioniModifica.toArray(new SospensioneType[listOfSospensioniModifica.size()]));
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiSospensioni(sospensioniModifica);
			newInstanceModifica.setSospensioniPosizioneModifica(newInstanceModifica.convertiPosizioneArray(listaPosModifica));

		}
		if(listOfSospensioniInserimento.size() > 0){
			SospensioniType sospensioniInserimento = SospensioniType.Factory.newInstance();
			sospensioniInserimento.setSospensioneArray((SospensioneType[])listOfSospensioniInserimento.toArray(new SospensioneType[listOfSospensioniInserimento.size()]));
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiSospensioni(sospensioniInserimento);
			newInstanceInserimento.setSospensioniPosizioneInserimento(newInstanceInserimento.convertiPosizioneArray(listaPosInserimento));
		}return isSospensioniModifica;

	}
	/**
	 * @param isSubappaltiModifica
	 * @param arrayOfSubAppalti
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaSubappalti(boolean isSubappaltiModifica, SubappaltoType[] arrayOfSubAppalti, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();
		ArrayList<SubappaltoType> listOfSubappaltoModifica = new ArrayList<SubappaltoType>();				
		ArrayList<SubappaltoType> listOfSubappaltoInserimento = new ArrayList<SubappaltoType>();
		
		ArrayList<Integer> listaPosModifica = new ArrayList<Integer>();
		ArrayList<Integer> listaPosInserimento = new ArrayList<Integer>();

		for(int i1 = 0; i1 < arrayOfSubAppalti.length; i1++){
					
			SubappaltoType schedaSubAppalto = arrayOfSubAppalti[i1];
			Integer index = new Integer(0);
			boolean bindIdLocale = false;
			// se e' settato l'id locale
			if(!schedaSubAppalto.isSetIDSCHEDASIMOG() && schedaSubAppalto.isSetIDSCHEDALOCALE()){
				// controlla se trovi l'id locale di questa scheda dalla situazione caricata dal db
				bindIdLocale = situazioneAttuale.controlla(schedaSubAppalto.getIDSCHEDALOCALE(), situazioneAttuale.getStatoSubAppalti(), index);
				if(bindIdLocale){
					// se l'hai trovata controlla che lo stato sia esistente
					bindIdLocale = bindIdLocale && situazioneAttuale.getStatoSubAppalti() .get(index.intValue()).isEsistente();
					if(bindIdLocale){
						listOfSubappaltoModifica.add(schedaSubAppalto);
						listaPosModifica.add(i1);
						isSubappaltiModifica = true;
					}
					else{
						listOfSubappaltoInserimento.add(schedaSubAppalto);
						listaPosInserimento.add(i1);
					}
				}
				else{
					listOfSubappaltoInserimento.add(schedaSubAppalto);
					listaPosInserimento.add(i1);
				}
			}else if(schedaSubAppalto.isSetIDSCHEDASIMOG() && !schedaSubAppalto.isSetIDSCHEDALOCALE()){
				listOfSubappaltoModifica.add(schedaSubAppalto);
				listaPosModifica.add(i1);
				isSubappaltiModifica = true;
			}else if(schedaSubAppalto.isSetIDSCHEDASIMOG() && schedaSubAppalto.isSetIDSCHEDALOCALE()){
				listOfSubappaltoModifica.add(schedaSubAppalto);
				listaPosModifica.add(i1);
				isSubappaltiModifica = true;
			}else if(!schedaSubAppalto.isSetIDSCHEDALOCALE() && !schedaSubAppalto.isSetIDSCHEDASIMOG() ){
				listOfSubappaltoInserimento.add(schedaSubAppalto);
				listaPosInserimento.add(i1);
			}
		}
		if(listOfSubappaltoModifica.size() > 0){
			SubappaltiType subappaltiModifica = SubappaltiType.Factory.newInstance();
			subappaltiModifica.setSubappaltoArray((SubappaltoType[]) listOfSubappaltoModifica.toArray(new SubappaltoType[listOfSubappaltoModifica.size()]));
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiSubappalti(subappaltiModifica);
			newInstanceModifica.setSubappaltiPosizioneModifica(newInstanceModifica.convertiPosizioneArray(listaPosModifica));

		}
		if(listOfSubappaltoInserimento.size() > 0){
			SubappaltiType subappaltiInserimento = SubappaltiType.Factory.newInstance();
			subappaltiInserimento.setSubappaltoArray((SubappaltoType[]) listOfSubappaltoInserimento.toArray(new SubappaltoType[listOfSubappaltoInserimento.size()]));
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiSubappalti(subappaltiInserimento);
			newInstanceInserimento.setSubappaltiPosizioneInserimento(newInstanceInserimento.convertiPosizioneArray(listaPosInserimento));

		}return isSubappaltiModifica;

	}
	/**
	 * @param isVariantiModifica
	 * @param arrayOfVarianti
	 * @param originale
	 * @param newInstanceInserimento
	 * @param newInstanceModifica
	 * @return
	 */
	public boolean separaVarianti(boolean isVariantiModifica, VarianteType[] arrayOfVarianti, IdsSchedaXML originale, IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica ){
		SituazioneSchedeAttuale situazioneAttuale = originale.getSituazioneAttuale();

		ArrayList<VarianteType> listOfVariantiModifica = new ArrayList<VarianteType>();			
		ArrayList<VarianteType> listOfVariantiInserimento = new ArrayList<VarianteType>();
		
		ArrayList<Integer> listaPosModifica = new ArrayList<Integer>();
		ArrayList<Integer> listaPosInserimento = new ArrayList<Integer>();
		for(int i1 = 0; i1 < arrayOfVarianti.length; i1++){
					
			VarianteType schedaVariante= arrayOfVarianti[i1];
			Integer index = new Integer(0);
			boolean bindIdLocale = false;
			// se e' settato l'id locale
			if(!schedaVariante.getVariante().isSetIDSCHEDASIMOG() && schedaVariante.getVariante().isSetIDSCHEDALOCALE()){
				// controlla se trovi l'id locale di questa scheda dalla situazione caricata dal db
				bindIdLocale = situazioneAttuale.controlla(schedaVariante.getVariante().getIDSCHEDALOCALE(), situazioneAttuale.getStatoVarianti(), index);
				if(bindIdLocale){
					// se l'hai trovata controlla che lo stato sia esistente
					bindIdLocale = bindIdLocale && situazioneAttuale.getStatoVarianti() .get(index.intValue()).isEsistente();
					if(bindIdLocale){
						listOfVariantiModifica.add(schedaVariante);
						listaPosModifica.add(i1);
						isVariantiModifica = true;
					}
					else{
						listOfVariantiInserimento.add(schedaVariante);
						listaPosInserimento.add(i1);
					}
				}
				else{
					listOfVariantiInserimento.add(schedaVariante);
					listaPosInserimento.add(i1);
				}
			}else if(schedaVariante.getVariante().isSetIDSCHEDASIMOG() && !schedaVariante.getVariante().isSetIDSCHEDALOCALE()){
				listOfVariantiModifica.add(schedaVariante);
				listaPosModifica.add(i1);
				isVariantiModifica = true;
			}else if(schedaVariante.getVariante().isSetIDSCHEDASIMOG() && schedaVariante.getVariante().isSetIDSCHEDALOCALE()){
				listOfVariantiModifica.add(schedaVariante);
				listaPosModifica.add(i1);
				isVariantiModifica = true;
			}else if(!schedaVariante.getVariante().isSetIDSCHEDALOCALE() && !schedaVariante.getVariante().isSetIDSCHEDASIMOG()){
				listOfVariantiInserimento.add(schedaVariante);
				listaPosInserimento.add(i1);
			}
		}
		if(listOfVariantiModifica.size() > 0){
			VariantiType variantiModifica = VariantiType.Factory.newInstance();
			variantiModifica.setVarianteArray((VarianteType[]) listOfVariantiModifica.toArray(new VarianteType[listOfVariantiModifica.size()]));
			newInstanceModifica.getScheda().getSchedaCompletaArray(0).setDatiVarianti(variantiModifica);
			newInstanceModifica.setVariantiPosizioneModifica(newInstanceModifica.convertiPosizioneArray(listaPosModifica));

		}
		if(listOfVariantiInserimento.size() > 0){
			VariantiType variantiInserimento = VariantiType.Factory.newInstance();
			variantiInserimento.setVarianteArray((VarianteType[]) listOfVariantiInserimento.toArray(new VarianteType[listOfVariantiInserimento.size()]));
			newInstanceInserimento.getScheda().getSchedaCompletaArray(0).setDatiVarianti(variantiInserimento);
			newInstanceInserimento.setVariantiPosizioneInserimento(newInstanceInserimento.convertiPosizioneArray(listaPosInserimento));

		}return isVariantiModifica;
	}
}
