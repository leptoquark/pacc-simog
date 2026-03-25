package it.avlp.simog.massload.util;

import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.SchedaVariante;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.flusso.bean.CrossedFields;
import it.avlp.simog.massload.bean.schede.TutteLeSchede;
import it.avlp.simog.massload.caricamento.CaricamentoBusiness;
import it.avlp.simog.massload.caricamento.SchedeConstructor;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.util.conversion.SituazioneAttualeSchedeXml;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe nata dalla necessita' di trovare un punto di "merge"
 * tra i dati contenuti nel tipo DatiAggiudicazioneType e quelli contenuti
 * nel db.</p>
 * 
 * Nel caso dell'inserimento i dati non presenti sul xml devono essere recuperati
 * dal db. </p>
 * 
 * Nel caso invece della modifica invece occorrera' valorizzare il bean
 * caricato dal db con i valori recuperati dal tipo xml. </p>
 * 
 * Per poter capire a piu alto livello cosa va' caricato e cosa no
 * utilizzero' due oggetti che mi offrono una panoramica sui dati presenti
 * nel db e quelli presenti nel tipo xml.
 * 
 * @author vletizia
 *
 */
public class DataMerger {

	private ConvertXMLtoBeanBusiness converter;
	private CaricamentoBusiness loader;
	
	public List<ValidationBean> listOfDuplicateWarning;
	public boolean containsDuplicate = false;
	
	/**
	 * Classe nata dalla necessita' di trovare un punto di "merge"
	 * tra i dati contenuti nel tipo DatiAggiudicazioneType e quelli contenuti
	 * nel db.</p>
	 * 
	 * Nel caso dell'inserimento i dati non presenti sul xml devono essere recuperati
	 * dal db. </p>
	 * 
	 * Nel caso invece della modifica invece occorrera' valorizzare il bean
	 * caricato dal db con i valori recuperati dal tipo xml. </p>
	 * 
	 * Per poter capire a piu alto livello cosa va' caricato e cosa no
	 * utilizzero' due oggetti che mi offrono una panoramica sui dati presenti
	 * nel db e quelli presenti nel tipo xml, oggetti richiesti in fase di
	 * esecuzione dell'operazione.
	 * 
	 * @param converter
	 * @param loader
	 */
	public DataMerger(ConvertXMLtoBeanBusiness converter, CaricamentoBusiness loader){
		this.converter = converter;
		this.loader = loader;
		this.listOfDuplicateWarning = new ArrayList<ValidationBean>();
	}
	
	/**
	 * Effettua il merge tra i dati del db e aggiorna la situazione attuale, i dati dal db hanno la precedenza,
	 * 	se quindi esiste la scheda sul db la carico da li' (concetto valido solo per le schede singole)
	 * 
	 * NOTA: i dati provenienti dal DB sono considerati validi, ho un flag che permette
	 * 			di evitarne la validazione (non carico proprio tutto in questo caso).
	 * 
	 * NOTA: in questa sezione vengono valorizzati dati sullo stato scheda che vengono dall'xml, mi serve per la
	 * 			validazione e per eventuali feedback di errore, che altrimenti non avrebbero riferimenti
	 * 
	 * <strong>Attenzione</strong>
	 * Per ragioni varie, se si vuole essere certi della presenza della scheda se lo stato e' esitente
	 * occorre 
	 * 
	 * @param datiAggiudicazione
	 * @param situazioneAttualeDb
	 * @param situazioneAttualeXml
	 * @return
	 * @throws Exception
	 */
	public TutteLeSchede mergePerInserimento(DatiAggiudicazioneType datiAggiudicazione,
												SituazioneSchedeAttuale situazioneAttualeDb, 
												SituazioneAttualeSchedeXml situazioneAttualeXml, 
												CrossedFields crossedFields,
												List<SoggettoResponsabileBean> listOfAnagraficheResponsabili,
												List<SoggettoPartecipanteBean> listOfAnagrafichePartecipanti,
												InfoGaraBean infoGara) throws Exception{
		
		TutteLeSchede tutteLeSchede = new TutteLeSchede();
		SchedeConstructor builder = new SchedeConstructor(converter);
		
		// PATCH - VL - more info for feedback
		// c'e' sul xml solammente se dati comuni sono in inserimento, per via della separazione delle schede che effettuo a monte.
		String cigFromXML = datiAggiudicazione.getDatiComuni() != null ? datiAggiudicazione.getDatiComuni().getCIG() : null;
		// se il cig preso dal xml e' nullo, i dati comuni sono in modifica quindi ce l'ho sulla situazione attuale
		String cig = cigFromXML != null ? cigFromXML : situazioneAttualeDb.getStatoDatiComuni().getCig();
		// se ho rilevato la presenza del cui nel file xml vuol dire che c'e' una schedaCompleta (tag xml) altrimenti provo a prenderlo dallo stato,
		// NOTA: potrebbe non essere presente il cui
		String cui = situazioneAttualeXml.isPresentCUI() ? datiAggiudicazione.getSchedaCompletaArray(0).getCUI() : situazioneAttualeDb.getStatoAggiudicazioneSottotipo().getCui();

		InfoComuniBean infoComuniBean = null;
		AggiudicazioneBean aggiudicazione = null;
		List<AggiudicatarioBean> aggiudicatari = null;
		
		if(situazioneAttualeDb.getStatoDatiComuni().isEsistenteDb()){
			infoComuniBean = loader.caricaDatiComuni(situazioneAttualeDb.getStatoDatiComuni().getIdRecord());
		}else{
			if(situazioneAttualeXml.isPresentDatiComuni()){
				// qui devo aggiornare lo stato della scheda dati comuni
				situazioneAttualeDb.getStatoDatiComuni().impostaComeEsistenteEDaXml(cig ,cui,situazioneAttualeXml.getDatiComuniIdLocale());
				// FLAG  validazione per "ruoli" ESITO PROCEDURA, TIPO LAVORI, SETTORI SPECIALI..
				situazioneAttualeDb.getStatoDatiComuni().setAggiudicata(EsitoEnum.AGGIUDICATA.codice().equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA() != null ? datiAggiudicazione.getDatiComuni().getESITOPROCEDURA().toString(): EsitoEnum.AGGIUDICATA.codice()));
				situazioneAttualeDb.getStatoDatiComuni().setLavori(Costanti.TIPO_SCHEDA_LAVORI.equals(datiAggiudicazione.getDatiComuni().getTIPOCONTRATTO().toString()));
				situazioneAttualeDb.getStatoDatiComuni().setSettoriSpeciali(Costanti.TIPO_ENTE_SPECIALE.equals(datiAggiudicazione.getDatiComuni().getFLAGENTESPECIALE().toString()));
			}
		}

		if(situazioneAttualeDb.getStatoAggiudicazione().isEsistenteDb()){
			aggiudicazione = loader.caricaAggiudicazione(situazioneAttualeDb.getStatoAggiudicazione().getIdRecord());
			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			//gm nuovo codice isFromDb
			//situazioneAttualeDb.getStatoAggiudicazione().setFromDb(true);
		}
		else{
			if(situazioneAttualeXml.isPresentAggiudicazione()){
				// qui devo aggiornare lo stato della scheda aggiudicazione
				situazioneAttualeDb.getStatoAggiudicazione().impostaComeEsistenteEDaXml(cig ,cui,situazioneAttualeXml.getAggiudicazioneIdLocale());
				//gm nuovo codice isFromDb
				//situazioneAttualeDb.getStatoAggiudicazione().setFromDb(false);
			}
		}
		
		Scheda_A schedaA = builder.constructSchedaA(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);
		if(builder.containsDuplicate){
			this.containsDuplicate = true;;
			this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
		}
		
		
		if(situazioneAttualeDb.getStatoAdesione().isEsistenteDb()){
			aggiudicazione = loader.caricaAggiudicazione(situazioneAttualeDb.getStatoAdesione().getIdRecord());
			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			//gm nuovo codice isFromDb
			//situazioneAttualeDb.getStatoAdesione().setFromDb(true);
		}
		else{
			if(situazioneAttualeXml.isPresentAdesione()){
				// qui devo aggiornare lo stato della scheda aggiudicazione
				situazioneAttualeDb.getStatoAdesione().impostaComeEsistenteEDaXml(cig ,cui,situazioneAttualeXml.getAdesioneIdLocale());
				//gm nuovo codice isFromDb
				//situazioneAttualeDb.getStatoAdesione().setFromDb(false);
			
				// PP aggiudicatari dall'accordo quadro
				//TICKET ALM - 3.04.3 #4113
				if(!SimogFlags.is3043Active())
				     aggiudicatari = getAggiudicatariAQ(infoGara);
				else  
					aggiudicatari = converter.convertiAggiudicatari(datiAggiudicazione.getSchedaCompletaArray()[0].getAdesione().getAggiudicatariArray(), true);
				//FINE TICKET ALM - 3.04.3 #4113
			}
		}
		if(situazioneAttualeDb.getStatoAdesione().isEsistenteDb() || situazioneAttualeXml.isPresentAdesione() ){
		schedaA = builder.constructSchedaAdesione(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);
		if(builder.containsDuplicate){
			this.containsDuplicate = true;;
			this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
		}
		}
		
		if(situazioneAttualeDb.getStatoSottosoglia().isEsistenteDb()){
			aggiudicazione = loader.caricaAggiudicazione(situazioneAttualeDb.getStatoSottosoglia().getIdRecord());
			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			//gm nuovo codice isFromDb
			//situazioneAttualeDb.getStatoSottosoglia().setFromDb(true);
		}
		else{
			if(situazioneAttualeXml.isPresentSottosoglia()){
				// qui devo aggiornare lo stato della scheda aggiudicazione
				situazioneAttualeDb.getStatoSottosoglia().impostaComeEsistenteEDaXml(cig ,cui,situazioneAttualeXml.getSottosogliaIdLocale());
				//gm nuovo codice isFromDb
				//situazioneAttualeDb.getStatoSottosoglia().setFromDb(false);
			}
		}
		if(situazioneAttualeDb.getStatoSottosoglia().isEsistenteDb() || situazioneAttualeXml.isPresentSottosoglia() ){
		schedaA = builder.constructSchedaSottosoglia(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);
		if(builder.containsDuplicate){
			this.containsDuplicate = true;;
			this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
		}
		}
		
		if(situazioneAttualeDb.getStatoEscluso().isEsistenteDb()){
			aggiudicazione = loader.caricaAggiudicazione(situazioneAttualeDb.getStatoEscluso().getIdRecord());
			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());	
			//gm nuovo codice isFromDb
			//situazioneAttualeDb.getStatoEscluso().setFromDb(true);
		}
		else{
			if(situazioneAttualeXml.isPresentEscluso()){
				// qui devo aggiornare lo stato della scheda aggiudicazione
				situazioneAttualeDb.getStatoEscluso().impostaComeEsistenteEDaXml(cig ,cui,situazioneAttualeXml.getEsclusoIdLocale());
				//gm nuovo codice isFromDb
				//situazioneAttualeDb.getStatoEscluso().setFromDb(false);
			}
		}
		if(situazioneAttualeDb.getStatoEscluso().isEsistenteDb() || situazioneAttualeXml.isPresentEscluso() ){
		schedaA = builder.constructSchedaEscluso(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);
		if(builder.containsDuplicate){
			this.containsDuplicate = true;;
			this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
		}
		}
		
      if(// SimogProperties.getInstance().isCUPLotto(infoGara.getDataCreazioneGara()) && 
            SimogProperties.getInstance().isCUPAttivo()) 
      {
         // lo faccio sempre se nn ci sono i dati sull'xml  if(infoGara.getIdAggiudicazione() <= 0){ // nuova aggiudicazione
            
            //Prevalorizzazione CUP dell'aggiudicazione con quelli del lotto (competenza lotto)
// PP non va bene faccio comandare flag_cup            if( SimogFlags.is3031_RFWEBGL02Active() && (schedaA.getElencoCup() == null || schedaA.getElencoCup().size() == 0)){
            if(SimogFlags.is3031_RFWEBGL02Active() 
                  && (schedaA.getFlagCUP() == null || "".equals(schedaA.getFlagCUP()))){
               
               schedaA.setElencoCup(loader.caricaElencoCup(infoGara.getIdLotto()));
               schedaA.setFlagCUP(loader.caricaFlagCup(infoGara.getIdLotto()));
            }
            
            //Prevalorizzaione tipologie appalto con quelli del lotto (competenza lotto)
            if( SimogFlags.is3031_RFWEBGL00Active() && schedaA.getAggiudicazione() != null 
                  && TipoAggiudicazione.A.equals(schedaA.getAggiudicazione().getSottotipo())
                  && schedaA.getTipoAppalto().size() == 0){
               schedaA.setTipoLavoro(loader.caricaTipilotto(infoGara.getIdLotto(),infoGara.getTIPO_SCHEDA_GARA(), Costanti.TIPO_SCHEDA_LAVORI));
               schedaA.setTipoFS(loader.caricaTipilotto(infoGara.getIdLotto(),infoGara.getTIPO_SCHEDA_GARA(), Costanti.TIPO_SCHEDA_SERVIZI));
               schedaA.getTipoFS().addAll(loader.caricaTipilotto(infoGara.getIdLotto(),infoGara.getTIPO_SCHEDA_GARA(), Costanti.TIPO_SCHEDA_FORNITURE));
            }
//         }
      }              

		
		/*      
		 * Se e una riaggiudicazione vengono reimpostati ai valori presenti sul database varie sezioni della scheda A
		 * 
		 * 
		 */
		
		if(schedaA.getAggiudicazione() != null && schedaA.getInfoComuni()!= null && schedaA.getAggiudicazione().getProgCuiRiaggiudicato() > 0){
			AggiudicazioneBean fromXml = schedaA.getAggiudicazione();
			long idAggOld;
			Timestamp dataInizioOld;
			String newCui = cig + "-" + fromXml.getProgCuiRiaggiudicato();
			AggiudicazioneBean riaggiudicata = loader.caricaAggiudicazione(newCui);
			
			idAggOld = riaggiudicata.getIdAggiudicazione();
			dataInizioOld = riaggiudicata.getDataInizioAggiudicazione();
			riaggiudicata.setIdAggiudicazione(fromXml.getIdAggiudicazione());
			riaggiudicata.setDataInizioAggiudicazione(fromXml.getDataInizioAggiudicazione());
			riaggiudicata.setDataFineAggiudicazione(fromXml.getDataFineAggiudicazione());
			riaggiudicata.setIdStato(fromXml.getIdStato());
			riaggiudicata.setDescrizioneStato(fromXml.getDescrizioneStato());
			riaggiudicata.setImportoAggiudicazione(fromXml.getImportoAggiudicazione());
			riaggiudicata.setPercOffAumento(fromXml.getPercOffAumento());
			riaggiudicata.setPercRibassoAgg(fromXml.getPercRibassoAgg());
			riaggiudicata.setProgCuiRiaggiudicato(fromXml.getProgCuiRiaggiudicato());
			riaggiudicata.setModalitaRiaggiudicazione(fromXml.getModalitaRiaggiudicazione());
			riaggiudicata.setIdLocale(fromXml.getIdLocale());
			//il sistema non ha mai permesso di cambiare la scelta del contraente nella riaggiudicazione
			//ma se si decide di permetterlo il codice da mettere segue
			//riaggiudicata.setIdSceltaContraente(fromXml.getIdSceltaContraente());
			
			//TICKET ALM #4231
			riaggiudicata.setDataVerbaleAggiudicazione(PageHelper.getViewDate(fromXml.getDataVerbaleAggiudicazione()));
			
			if(fromXml.getFlagRichSubappalto() == null)
			{
				 riaggiudicata.setFlagRichSubappalto(Costanti.FLAG_VALORE_NO);
			} else {
				riaggiudicata.setFlagRichSubappalto(fromXml.getFlagRichSubappalto());
			}
			//FINE TICKET ALM #4231
			
			
			// patch PP il cui deve essere quello dell'aggiudicazione che riaggiudica!
			riaggiudicata.setProgCUI(fromXml.getProgCUI());
			
			schedaA.setAggiudicazione(riaggiudicata);
			schedaA.setFinanziamenti(loader.caricaFinanziamenti(idAggOld, dataInizioOld));

			if(SimogFlags.is3031_RFWEBGL02Active()
               && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
               && SimogProperties.getInstance().isCUPAttivo()){
            schedaA.setTipoLavoro(loader.caricaTipiLavoro(idAggOld, dataInizioOld,schedaA.getInfoComuni().getFlagEnteSpeciale()));
            schedaA.setTipoFS(loader.caricaTipiFS(idAggOld, dataInizioOld,schedaA.getInfoComuni().getFlagEnteSpeciale()));
         }
			
			schedaA.setCondizioni(loader.caricaCondizioni(idAggOld, dataInizioOld));
			schedaA.setRequisiti(loader.caricaRequisiti(idAggOld, dataInizioOld));
			
         if(SimogFlags.is3031_RFWEBGL02Active()
               && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
               && SimogProperties.getInstance().isCUPAttivo()){
            
            schedaA.setElencoCup(loader.caricaElencoCup(idAggOld, dataInizioOld));
            schedaA.setFlagCUP(loader.caricaFlagCup(infoGara.getIdLotto()));
			}
		}
		
		
		
		SchedaInizioLavori schedaInizio = null;	
		if(situazioneAttualeDb.getStatoInizioLavori().isEsistenteDb()){
			InizioLavoriBean inizioLavori = loader.caricaInizioLavori(situazioneAttualeDb.getStatoInizioLavori().getIdAggiudicazione(), situazioneAttualeDb.getStatoInizioLavori().getDataInizioAggiudicazione());
			schedaInizio = new SchedaInizioLavori();
			schedaInizio.setDatiInizio(inizioLavori);
		}else{
			if(situazioneAttualeXml.isPresentInizioLavori()){
				schedaInizio = builder.constructSchedaInizio(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaA.getAggiudicatari());
				situazioneAttualeDb.getStatoInizioLavori().impostaComeEsistenteEDaXml(cig ,cui, situazioneAttualeXml.getInizioLavoriIdLocale());
				if(builder.containsDuplicate){
					this.containsDuplicate = true;;
					this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
				}
			}
		}
		
		SchedaStipula schedaStipula = null;	
		if(situazioneAttualeDb.getStatoStipula().isEsistenteDb()){
			StipulaBean stipula = loader.caricaStipula(situazioneAttualeDb.getStatoStipula().getIdAggiudicazione(), situazioneAttualeDb.getStatoStipula().getDataInizioAggiudicazione());
			schedaStipula = new SchedaStipula();
			schedaStipula.setStipula(stipula);
		}else{
			if(situazioneAttualeXml.isPresentStipula()){
				schedaStipula = builder.constructSchedaStipula(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaA.getAggiudicatari());
				situazioneAttualeDb.getStatoStipula().impostaComeEsistenteEDaXml(cig ,cui, situazioneAttualeXml.getStipulaIdLocale());
				if(builder.containsDuplicate){
					this.containsDuplicate = true;;
					this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
				}
			}
		}
		/**
		 * XXX VL il merge in inserimento lo devo fare ?
		 * in verita' visto che queste sono tutte i inserimento mi interessano solo le schede del xml
		 * le sole schede(multiple) che dovrei caricare sono quelle propedeutiche ad altre schede..(accordi ?)
		 * Altrimenti per le schede multiple non ha senso.. 
		 * Usare impostaDaXML.. (cosi posso distinguere quali da db e quali da xml)
		 * Ricorda pero' che la validazione viene fatta su tutta la lista.. e quindi ?
		 */
		SchedaAvanzamento schedeAvanzamento = null;

		if(situazioneAttualeXml.isPresentAvanzamenti()){
			//XX-X validazione posizionale, il primo puo valorizzare la somma/data anticipata le altre no..
		   int positionStart = loader.getNextAvanzamento(situazioneAttualeDb.getStatoAggiudicazioneSottotipo().getIdRecord(), situazioneAttualeDb.getStatoAggiudicazioneSottotipo().getDataInizioRecord());

			//gm modifica necessaria per confrontare l'avanzamento anche con varianti presenti in xml 
			List<VarianteBean> tutteLeVariantiPerAvanzamento = null;
			List <VarianteBean> variantiDaXmlPerAvanzamento = null;
			List <VarianteBean> variantiDaDbPerAvanzamento = null;
			//se presenti varianti da xml, le carico
			if(situazioneAttualeXml.isPresentVarianti()){
				SchedaVariante schedeVarianteXmlPerAvanzamento = null;		
				schedeVarianteXmlPerAvanzamento = builder.constructSchedaVarianti(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaInizio != null ? schedaInizio.getDatiInizio() : null);
			    variantiDaXmlPerAvanzamento = schedeVarianteXmlPerAvanzamento.getVarianti();
			}
			//se presenti varianti da db, le carico
			variantiDaDbPerAvanzamento = loader.caricaVarianti(situazioneAttualeDb.getStatoAggiudicazioneSottotipo().getIdRecord(), situazioneAttualeDb.getStatoAggiudicazioneSottotipo().getDataInizioRecord());
	    	//uso il nuovo metodo per gestire le varianti da db e da xml
			tutteLeVariantiPerAvanzamento = mergeVariantiPerSchedaAvanzamento(variantiDaDbPerAvanzamento, variantiDaXmlPerAvanzamento, situazioneAttualeXml.isPresentDatiComuniIdLocale(), situazioneAttualeXml.isPresentDatiComuniIdSimog());
			//gm fine modifica 

			/*
			schedeAvanzamento = builder.constructSchedaAvanzamenti(datiAggiudicazione, false, schedaA.getInfoComuni(), 
					schedaA.getAggiudicazione(), positionStart,
					loader.caricaVarianti(situazioneAttualeDb.getStatoAggiudicazione().getIdRecord(), situazioneAttualeDb.getStatoAggiudicazione().getDataInizioRecord()));
			situazioneAttualeDb.setStatoAvanzamento(StatoScheda.impostaTutteDaXML(cig,cui, schedeAvanzamento.getAvanzamenti(), AvanzamentoBean.class));
		    */
			schedeAvanzamento = builder.constructSchedaAvanzamenti(datiAggiudicazione, false, schedaA.getInfoComuni(), 
					schedaA.getAggiudicazione(), positionStart, tutteLeVariantiPerAvanzamento);
			situazioneAttualeDb.setStatoAvanzamento(StatoScheda.impostaTutteDaXML(cig,cui, schedeAvanzamento.getAvanzamenti(), AvanzamentoBean.class));
		
		}

		
		SchedaConclusione schedaConclusione = null;
		if(situazioneAttualeDb.getStatoConclusione().isEsistenteDb()){
			ConclusioneBean conclusione = loader.caricaConclusione(situazioneAttualeDb.getStatoConclusione().getIdAggiudicazione(), situazioneAttualeDb.getStatoConclusione().getDataInizioAggiudicazione());
			schedaConclusione = new SchedaConclusione();
			schedaConclusione.setConclusione(conclusione);
		}else{
			if(situazioneAttualeXml.isPresentConclusione()){
				schedaConclusione = builder.constructSchedaConclusione(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione());
				situazioneAttualeDb.getStatoConclusione().impostaComeEsistenteEDaXml(cig,cui, situazioneAttualeXml.getConclusioneIdLocale());
			}
		}
		
		SchedaAccordo schedeAccordo = null;

		if(situazioneAttualeXml.isPresentAccordi()){
			schedeAccordo = builder.constructSchedaAccordi(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaInizio != null ? schedaInizio.getDatiInizio() : null);
			situazioneAttualeDb.setStatoAccordi(StatoScheda.impostaTutteDaXML(cig,cui, schedeAccordo.getAccordi(),AccordoBean.class));

		}

		
		SchedaCollaudo schedaCollaudo = null;
		if(situazioneAttualeDb.getStatoCollaudo().isEsistenteDb()){
			CollaudoBean collaudo = loader.caricaCollaudo(situazioneAttualeDb.getStatoCollaudo().getIdAggiudicazione(), situazioneAttualeDb.getStatoCollaudo().getDataInizioAggiudicazione());		
			schedaCollaudo = new SchedaCollaudo();
			schedaCollaudo.setCollaudo(collaudo);
		}else{
			if(situazioneAttualeXml.isPresentCollaudo()){
				schedaCollaudo = builder.constructSchedaCollaudo(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(),
						schedaConclusione != null ? schedaConclusione.getConclusione() : null, schedeAccordo != null ? schedeAccordo.getAccordi() :  null,
						schedaInizio != null ? schedaInizio.getDatiInizio() : null);
				situazioneAttualeDb.getStatoCollaudo().impostaComeEsistenteEDaXml(cig,cui, situazioneAttualeXml.getCollaudoIdLocale());
				if(builder.containsDuplicate){
					this.containsDuplicate = true;;
					this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
				}
			}
		}
		
		SchedaR129 schedeRitardo = null;

		if(situazioneAttualeXml.isPresentRitardo()){
			schedeRitardo = builder.constructSchedaRitardi(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione());
			situazioneAttualeDb.setStatoRitardo(StatoScheda.impostaTutteDaXML(cig ,cui,schedeRitardo.getR129s(), R129Bean.class));
		}

		
		SchedaSospensione schedeSospensione = null;

		if(situazioneAttualeXml.isPresentSospensioni()){
			schedeSospensione = builder.constructSchedaSospensioni(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaInizio != null ? schedaInizio.getDatiInizio() : null);
			situazioneAttualeDb.setStatoSospensioni(StatoScheda.impostaTutteDaXML(cig,cui, schedeSospensione.getSospensioni(), SospensioniBean.class));
		}

		
		SchedaSubAppalti schedeSubAppalto = null;

		if(situazioneAttualeXml.isPresentSubAppalti()){
		   
		   List<AggiudicatarioBean> refAgg = null;
		   if(schedaA.getAggiudicatari() != null)
		      refAgg = schedaA.getAggiudicatari();
			schedeSubAppalto = builder.constructSchedaSubAppalti(datiAggiudicazione, false, 
			                        schedaA.getInfoComuni(), 
			                        schedaA.getAggiudicazione(), 
			                        schedaInizio != null ? schedaInizio.getDatiInizio() : null, 
			                        schedaA.getAggiudicatari() != null 
			                           ? schedaA.getAggiudicatari() 
			                           : null);
			situazioneAttualeDb.setStatoSubAppalti(StatoScheda.impostaTutteDaXML(cig,cui, schedeSubAppalto.getSubAppalti(), SubappaltiBean.class));

		}

		
		SchedaVariante schedeVariante = null;

		if(situazioneAttualeXml.isPresentVarianti()){
			schedeVariante = builder.constructSchedaVarianti(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaInizio != null ? schedaInizio.getDatiInizio() : null);
			situazioneAttualeDb.setStatoVarianti(StatoScheda.impostaTutteDaXML(cig,cui, schedeVariante.getVarianti(), VarianteBean.class));
			if(builder.containsDuplicate){
				this.containsDuplicate = true;
				this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
			}
		}


		tutteLeSchede.setSchedaA(schedaA);
		tutteLeSchede.setSchedaStipula(schedaStipula);
		tutteLeSchede.setSchedaCollaudo(schedaCollaudo);
		tutteLeSchede.setSchedaConclusione(schedaConclusione);
		tutteLeSchede.setSchedaInizio(schedaInizio);
		tutteLeSchede.setSchedeAccordo(schedeAccordo);
		tutteLeSchede.setSchedeAvanzamento(schedeAvanzamento);
		tutteLeSchede.setSchedeRitardi(schedeRitardo);
		tutteLeSchede.setSchedeSospensione(schedeSospensione);
		tutteLeSchede.setSchedeSubAppalto(schedeSubAppalto);
		tutteLeSchede.setSchedeVariante(schedeVariante);
		
		tutteLeSchede.setListOfAnagrafichePartecipanti(listOfAnagrafichePartecipanti);
		tutteLeSchede.setListOfAnagraficheResponsabili(listOfAnagraficheResponsabili);
		
		return tutteLeSchede;
	}
	
	/**
	 * Effettua il merge tra i dati del db e aggiorna la situazione attuale, i dati dal db hanno la precedenza,
	 * 	se quindi esiste la scheda sul db la carico da li' (concetto valido solo per le schede singole)
	 * 
	 * NOTA: i dati provenienti dal DB sono considerati validi, ho un flag che permette
	 * 			di evitarne la validazione (non carico proprio tutto in questo caso).
	 * 
	 * NOTA: in questa sezione vengono valorizzati dati sullo stato scheda che vengono dall'xml, mi serve per la
	 * 			validazione e per eventuali feedback di errore, che altrimenti non avrebbero riferimenti
	 * 
	 * 
	 * <strong>Attenzione</strong>
	 * Per ragioni varie, se si vuole essere certi della presenza della scheda se lo stato e' esitente
	 * occorre 
	 * 
	 * @param datiAggiudicazione
	 * @param situazioneAttualeDb
	 * @param situazioneAttualeXml
	 * @return
	 * @throws Exception
	 */
	public TutteLeSchede mergePerModifica(DatiAggiudicazioneType datiAggiudicazione,
												SituazioneSchedeAttuale situazioneAttualeDb, 
												SituazioneAttualeSchedeXml situazioneAttualeXml, 
												CrossedFields crossedFields,
												List<SoggettoResponsabileBean> listOfAnagraficheResponsabili,
												List<SoggettoPartecipanteBean> listOfAnagrafichePartecipanti,
												InfoGaraBean infoGara) throws Exception{
		
		TutteLeSchede tutteLeSchede = new TutteLeSchede();
		SchedeConstructor builder = new SchedeConstructor(converter);
		String cig = null;

		InfoComuniBean infoComuniBean = null;
		AggiudicazioneBean aggiudicazione = null;
		List<AggiudicatarioBean> aggiudicatari = null;
	   Scheda_A schedaA = null;

		if(situazioneAttualeDb.getStatoDatiComuni().isEsistenteDb()){
			infoComuniBean = loader.caricaDatiComuni(situazioneAttualeDb.getStatoDatiComuni().getIdRecord());
			if(situazioneAttualeXml.isPresentDatiComuni()){
				cig = datiAggiudicazione.getDatiComuni().getCIG();
				// FLAGs  validazione per "ruoli" ESITO PROCEDURA, TIPO LAVORI, SETTORI SPECIALI..
				situazioneAttualeDb.getStatoDatiComuni().setAggiudicata(EsitoEnum.AGGIUDICATA.codice().equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA() != null ? datiAggiudicazione.getDatiComuni().getESITOPROCEDURA().toString(): EsitoEnum.AGGIUDICATA.codice()));
				situazioneAttualeDb.getStatoDatiComuni().setLavori(Costanti.TIPO_SCHEDA_LAVORI.equals(datiAggiudicazione.getDatiComuni().getTIPOCONTRATTO().toString()));
				situazioneAttualeDb.getStatoDatiComuni().setSettoriSpeciali(Costanti.TIPO_ENTE_SPECIALE.equals(datiAggiudicazione.getDatiComuni().getFLAGENTESPECIALE().toString()));

//	XX-X: setting delle rif per le schede			
				InfoComuniBean infoFromXml = converter.converti(datiAggiudicazione.getDatiComuni());
				PubblicazioneBean pubFromXml = converter.converti(datiAggiudicazione.getPubblicazione());
				infoFromXml.setIdInfo(infoComuniBean.getIdInfo());
				infoFromXml.setDataInizioInfo(infoComuniBean.getDataInizioInfo());
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(infoComuniBean.getIdLocale() != null){
					infoFromXml.setIdLocale(infoComuniBean.getIdLocale());
				}
				pubFromXml.setIdPubblicazione(infoComuniBean.getPubblicazione().getIdPubblicazione());
				pubFromXml.setDataInizioPubblicazione(infoComuniBean.getPubblicazione().getDataInizioPubblicazione());
				infoFromXml.setPubblicazione(pubFromXml);
				//PP
				infoFromXml.setIdLotto(infoComuniBean.getIdLotto());
				infoComuniBean = infoFromXml;
			}
			
	      // PP costruisco i dati comuni 
	      schedaA = builder.constructSchedaA(datiAggiudicazione,infoComuniBean, null, null, crossedFields, infoGara);
		}
		
		if(situazioneAttualeDb.getStatoAggiudicazione().isEsistenteDb()){
			aggiudicazione = loader.caricaAggiudicazione(situazioneAttualeDb.getStatoAggiudicazione().getIdRecord());
//			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			if(situazioneAttualeXml.isPresentAggiudicazione()){
				
//				XX-X: setting delle rif per le schede		
				AggiudicazioneBean aggFromXml = converter.converti(datiAggiudicazione.getSchedaCompletaArray(0).getAggiudicazione().getAppalto());
				aggFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
				aggFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setIdInfo(aggiudicazione.getIdInfo());
				aggFromXml.setDataInizioInfo(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setSottotipo(TipoAggiudicazione.A);
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(aggiudicazione.getIdLocale() != null){
					aggFromXml.setIdLocale(aggiudicazione.getIdLocale());
				}
				
				List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(datiAggiudicazione.getSchedaCompletaArray(0).getAggiudicazione().getAggiudicatariArray(), true);
				// questo per assegnare gli idAggiudicazione, ovviamente ignoro gli aggiudicatari non piu presenti
				for(AggiudicatarioBean aggrFromXml : listOfAggiudicatari){

					aggrFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
					aggrFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());

				}
				aggiudicatari = listOfAggiudicatari;
				
			}else{
				aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			}

	       schedaA = builder.constructSchedaA(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);

		}
		
		if(situazioneAttualeXml.isPresentAggiudicazione()){
			if(builder.containsDuplicate){
				this.containsDuplicate = true;
				this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
			}
		}
		
		if(situazioneAttualeDb.getStatoAdesione().isEsistenteDb()){
			aggiudicazione = loader.caricaAdesione(situazioneAttualeDb.getStatoAdesione().getIdRecord());
//			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			if(situazioneAttualeXml.isPresentAdesione()){
				
//				XX-X: setting delle rif per le schede		
				AggiudicazioneBean aggFromXml = converter.converti(datiAggiudicazione.getSchedaCompletaArray(0).getAdesione().getAppalto());
				aggFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
				aggFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setIdInfo(aggiudicazione.getIdInfo());
				aggFromXml.setDataInizioInfo(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setSottotipo(TipoAggiudicazione.Q);
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(aggiudicazione.getIdLocale() != null){
					aggFromXml.setIdLocale(aggiudicazione.getIdLocale());
				}
				
				List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(datiAggiudicazione.getSchedaCompletaArray(0).getAdesione().getAggiudicatariArray(), true);
				// questo per assegnare gli idAggiudicazione, ovviamente ignoro gli aggiudicatari non piu presenti

	           // PP aggiudicatari dall'accordo quadro
				//TICKET ALM - 3.04.3 #4113
				if(!SimogFlags.is3043Active())
				   listOfAggiudicatari = getAggiudicatariAQ(infoGara);
            
				for(AggiudicatarioBean aggrFromXml : listOfAggiudicatari){

					aggrFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
					aggrFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());

				}
				aggiudicatari = listOfAggiudicatari;
				
			}else{
				aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			}

	      // PP (interna) errore su validazione scheda inizio per fattispecie ADESIONE
	      schedaA = builder.constructSchedaAdesione(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);
		}

		if(situazioneAttualeXml.isPresentAdesione()){
			if(builder.containsDuplicate){
				this.containsDuplicate = true;;
				this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
			}
		}
		
		if(situazioneAttualeDb.getStatoSottosoglia().isEsistenteDb()){
			aggiudicazione = loader.caricaSottosoglia(situazioneAttualeDb.getStatoSottosoglia().getIdRecord());
//			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			if(situazioneAttualeXml.isPresentSottosoglia()){
				
//				XX-X: setting delle rif per le schede		
				AggiudicazioneBean aggFromXml = converter.converti(datiAggiudicazione.getSchedaCompletaArray(0).getSottosoglia().getAppalto(), TipoAggiudicazione.S);
				aggFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
				aggFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setIdInfo(aggiudicazione.getIdInfo());
				aggFromXml.setDataInizioInfo(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setSottotipo(TipoAggiudicazione.S);
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(aggiudicazione.getIdLocale() != null){
					aggFromXml.setIdLocale(aggiudicazione.getIdLocale());
				}
				
				List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(datiAggiudicazione.getSchedaCompletaArray(0).getSottosoglia().getAggiudicatariArray(), true);
				// questo per assegnare gli idAggiudicazione, ovviamente ignoro gli aggiudicatari non piu presenti
				for(AggiudicatarioBean aggrFromXml : listOfAggiudicatari){

					aggrFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
					aggrFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());

				}
				aggiudicatari = listOfAggiudicatari;
				
			}else{
				aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			}

			// PP (interna) errore su validazione scheda inizio per fattispecie ADESIONE
	      schedaA = builder.constructSchedaSottosoglia(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);
		}

		
		if(situazioneAttualeXml.isPresentSottosoglia()){
			if(builder.containsDuplicate){
				this.containsDuplicate = true;;
				this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
			}
		}
		
		if(situazioneAttualeDb.getStatoEscluso().isEsistenteDb()){
			aggiudicazione = loader.caricaEscluso(situazioneAttualeDb.getStatoEscluso().getIdRecord());
//			aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			if(situazioneAttualeXml.isPresentEscluso()){
				
//				XX-X: setting delle rif per le schede		
				AggiudicazioneBean aggFromXml = converter.converti(datiAggiudicazione.getSchedaCompletaArray(0).getEscluso().getAppalto(), TipoAggiudicazione.E);
				aggFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
				aggFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setIdInfo(aggiudicazione.getIdInfo());
				aggFromXml.setDataInizioInfo(aggiudicazione.getDataInizioAggiudicazione());
				aggFromXml.setSottotipo(TipoAggiudicazione.E);
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(aggiudicazione.getIdLocale() != null){
					aggFromXml.setIdLocale(aggiudicazione.getIdLocale());
				}
				
				List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(datiAggiudicazione.getSchedaCompletaArray(0).getEscluso().getAggiudicatariArray(), true);
				// questo per assegnare gli idAggiudicazione, ovviamente ignoro gli aggiudicatari non piu presenti
				for(AggiudicatarioBean aggrFromXml : listOfAggiudicatari){

					aggrFromXml.setIdAggiudicazione(aggiudicazione.getIdAggiudicazione());
					aggrFromXml.setDataInizioAggiudicazione(aggiudicazione.getDataInizioAggiudicazione());

				}
				aggiudicatari = listOfAggiudicatari;
				
			}else{
				aggiudicatari = loader.caricaAggiudicatari(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
			}

	      // PP (interna) errore su validazione scheda inizio per fattispecie ADESIONE
	      schedaA =  builder.constructSchedaEscluso(datiAggiudicazione,infoComuniBean, aggiudicazione, aggiudicatari, crossedFields, infoGara);
		}

		if(situazioneAttualeXml.isPresentEscluso()){
			if(builder.containsDuplicate){
				this.containsDuplicate = true;;
				this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
			}
		}
		
      if(// SimogProperties.getInstance().isCUPLotto(infoGara.getDataCreazioneGara()) && 
            SimogProperties.getInstance().isCUPAttivo()) 
      {
         // lo faccio sempre se nn ci sono i dati sull'xml  if(infoGara.getIdAggiudicazione() <= 0){ // nuova aggiudicazione
            
            //Prevalorizzazione CUP dell'aggiudicazione con quelli del lotto (competenza lotto)
      // PP non va bene faccio comandare flag_cup            if( SimogFlags.is3031_RFWEBGL02Active() && (schedaA.getElencoCup() == null || schedaA.getElencoCup().size() == 0)){
         if(schedaA != null
               && (schedaA.getFlagCUP() == null || "".equals(schedaA.getFlagCUP()))){
               
               schedaA.setElencoCup(loader.caricaElencoCup(infoGara.getIdLotto()));
               schedaA.setFlagCUP(loader.caricaFlagCup(infoGara.getIdLotto()));
            }
            
            //Prevalorizzaione tipologie appalto con quelli del lotto (competenza lotto)
            if( schedaA != null
                  && schedaA.getAggiudicazione() != null 
                  && TipoAggiudicazione.A.equals(schedaA.getAggiudicazione().getSottotipo())
                  && schedaA.getTipoAppalto().size() == 0){
               schedaA.setTipoLavoro(loader.caricaTipilotto(infoGara.getIdLotto(),infoGara.getTIPO_SCHEDA_GARA(), Costanti.TIPO_SCHEDA_LAVORI));
               schedaA.setTipoFS(loader.caricaTipilotto(infoGara.getIdLotto(),infoGara.getTIPO_SCHEDA_GARA(), Costanti.TIPO_SCHEDA_SERVIZI));
               schedaA.getTipoFS().addAll(loader.caricaTipilotto(infoGara.getIdLotto(),infoGara.getTIPO_SCHEDA_GARA(), Costanti.TIPO_SCHEDA_FORNITURE));
            }
//         }
      }              

      /*      
       * Se e una riaggiudicazione vengono reimpostati ai valori presenti sul database varie sezioni della scheda A
       * 
       * 
       */
      
      if(schedaA.getAggiudicazione() != null && schedaA.getInfoComuni()!= null && schedaA.getAggiudicazione().getProgCuiRiaggiudicato() > 0){
         AggiudicazioneBean fromXml = schedaA.getAggiudicazione();
         long idAggOld;
         Timestamp dataInizioOld;
         String newCui = cig + "-" + fromXml.getProgCuiRiaggiudicato();
         AggiudicazioneBean riaggiudicata = loader.caricaAggiudicazione(newCui);
         
         idAggOld = riaggiudicata.getIdAggiudicazione();
         dataInizioOld = riaggiudicata.getDataInizioAggiudicazione();
         riaggiudicata.setIdAggiudicazione(fromXml.getIdAggiudicazione());
         riaggiudicata.setDataInizioAggiudicazione(fromXml.getDataInizioAggiudicazione());
         riaggiudicata.setDataFineAggiudicazione(fromXml.getDataFineAggiudicazione());
         riaggiudicata.setIdStato(fromXml.getIdStato());
         riaggiudicata.setDescrizioneStato(fromXml.getDescrizioneStato());
         riaggiudicata.setImportoAggiudicazione(fromXml.getImportoAggiudicazione());
         riaggiudicata.setPercOffAumento(fromXml.getPercOffAumento());
         riaggiudicata.setPercRibassoAgg(fromXml.getPercRibassoAgg());
         riaggiudicata.setProgCuiRiaggiudicato(fromXml.getProgCuiRiaggiudicato());
         riaggiudicata.setModalitaRiaggiudicazione(fromXml.getModalitaRiaggiudicazione());
         riaggiudicata.setIdLocale(fromXml.getIdLocale());
         
           //TICKET ALM #4231
			riaggiudicata.setDataVerbaleAggiudicazione(PageHelper.getViewDate(fromXml.getDataVerbaleAggiudicazione()));
			
			if(fromXml.getFlagRichSubappalto() == null)
			{
				 riaggiudicata.setFlagRichSubappalto(Costanti.FLAG_VALORE_NO);
			} else {
				riaggiudicata.setFlagRichSubappalto(fromXml.getFlagRichSubappalto());
			}
			//FINE TICKET ALM #4231
         
         // patch PP il cui deve essere quello dell'aggiudicazione che riaggiudica!
         riaggiudicata.setProgCUI(fromXml.getProgCUI());
         
         schedaA.setAggiudicazione(riaggiudicata);
         schedaA.setFinanziamenti(loader.caricaFinanziamenti(idAggOld, dataInizioOld));

         if(SimogFlags.is3031_RFWEBGL02Active()
               && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
               && SimogProperties.getInstance().isCUPAttivo()){
            schedaA.setTipoLavoro(loader.caricaTipiLavoro(idAggOld, dataInizioOld,schedaA.getInfoComuni().getFlagEnteSpeciale()));
            schedaA.setTipoFS(loader.caricaTipiFS(idAggOld, dataInizioOld,schedaA.getInfoComuni().getFlagEnteSpeciale()));
         }
         
         schedaA.setCondizioni(loader.caricaCondizioni(idAggOld, dataInizioOld));
         schedaA.setRequisiti(loader.caricaRequisiti(idAggOld, dataInizioOld));
         
         if(SimogFlags.is3031_RFWEBGL02Active()
               && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
               && SimogProperties.getInstance().isCUPAttivo()){
            
            schedaA.setElencoCup(loader.caricaElencoCup(idAggOld, dataInizioOld));
            schedaA.setFlagCUP(loader.caricaFlagCup(infoGara.getIdLotto()));
         }
      }
      
		
		SchedaInizioLavori schedaInizio = null;	
		InizioLavoriBean inizioLavori = null;
		if(situazioneAttualeDb.getStatoInizioLavori().isEsistenteDb()){
			inizioLavori = loader.caricaInizioLavori(situazioneAttualeDb.getStatoInizioLavori().getIdAggiudicazione(), situazioneAttualeDb.getStatoInizioLavori().getDataInizioAggiudicazione());
			if(situazioneAttualeXml.isPresentInizioLavori()){
				schedaInizio = builder.constructSchedaInizio(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaA.getAggiudicatari());

//				XX-X: setting delle rif per le schede		
				schedaInizio.getDatiInizio().setIdInizioLavori(inizioLavori.getIdInizioLavori());
				schedaInizio.getDatiInizio().setDataInizioLavori(inizioLavori.getDataInizioLavori());
				schedaInizio.getDatiInizio().setIdAggiudicazione(inizioLavori.getIdAggiudicazione());
				schedaInizio.getDatiInizio().setDataInizioAggiudicazione(inizioLavori.getDataInizioAggiudicazione());
				
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(inizioLavori.getIdLocale() != null){
					schedaInizio.getDatiInizio().setIdLocale(inizioLavori.getIdLocale());
				}
				
				if(builder.containsDuplicate){
					this.containsDuplicate = true;
					this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
				}
			}
		}
		
		SchedaStipula schedaStipula = null;	
		StipulaBean stipula = null;
		if(situazioneAttualeDb.getStatoStipula().isEsistenteDb()){
			stipula = loader.caricaStipula(situazioneAttualeDb.getStatoStipula().getIdAggiudicazione(), situazioneAttualeDb.getStatoStipula().getDataInizioAggiudicazione());
			if(situazioneAttualeXml.isPresentStipula()){
				schedaStipula = builder.constructSchedaStipula(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaA.getAggiudicatari());

//				XX-X: setting delle rif per le schede		
				schedaStipula.getStipula().setIdStipula(stipula.getIdStipula());
				schedaStipula.getStipula().setDataInizioStipula(stipula.getDataInizioStipula());
				schedaStipula.getStipula().setIdAggiudicazione(stipula.getIdAggiudicazione());
				schedaStipula.getStipula().setDataInizioAggiudicazione(stipula.getDataInizioAggiudicazione());
				
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(stipula.getIdLocale() != null){
					schedaStipula.getStipula().setIdLocale(stipula.getIdLocale());
				}
				
				if(builder.containsDuplicate){
					this.containsDuplicate = true;
					this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
				}
			}
		}
		SchedaAvanzamento schedeAvanzamento = null;
		if(situazioneAttualeDb.getStatoAvanzamento() != null && situazioneAttualeDb.getStatoAvanzamento().size() > 0){
			List<AvanzamentoBean> listOfAvanzamenti = loader.caricaAvanzamenti(situazioneAttualeDb.getStatoAvanzamento().get(0).getIdAggiudicazione(), situazioneAttualeDb.getStatoAvanzamento().get(0).getDataInizioAggiudicazione());	
			if(situazioneAttualeXml.isPresentAvanzamenti()){
				
				//TODO: rimozione di position start siccome � in modifica non serve calcolarlo viene comunque sovrascritto sotto (lines: 449 e 461)
				int positionStart = 1;
				//XX-X validazione posizionale, il primo puo valorizzare la somma/data anticipata le altre no..				
				if(situazioneAttualeDb.getStatoAvanzamento() != null && situazioneAttualeDb.getStatoAvanzamento().size() > 0){
					positionStart = listOfAvanzamenti.size();
				}
				
				//gm modifica necessaria per confrontare l'avanzamento anche con varianti presenti in xml 
				List<VarianteBean> tutteLeVariantiPerAvanzamento = null;
				List <VarianteBean> variantiDaXmlPerAvanzamento = null;
				List <VarianteBean> variantiDaDbPerAvanzamento = null;
				//se presenti varianti da xml, le carico
				if(situazioneAttualeXml.isPresentVarianti()){
					SchedaVariante schedeVarianteXmlPerAvanzamento = null;		
					schedeVarianteXmlPerAvanzamento = builder.constructSchedaVarianti(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), schedaInizio != null ? schedaInizio.getDatiInizio() : null);
				    variantiDaXmlPerAvanzamento = schedeVarianteXmlPerAvanzamento.getVarianti();
				}
				//se presenti varianti da db, le carico
			   variantiDaDbPerAvanzamento = loader.caricaVarianti(situazioneAttualeDb.getStatoAggiudicazioneSottotipo().getIdRecord(), situazioneAttualeDb.getStatoAggiudicazioneSottotipo().getDataInizioRecord());
		    	//uso il nuovo metodo per gestire le varianti da db e da xml
				tutteLeVariantiPerAvanzamento = mergeVariantiPerSchedaAvanzamento(variantiDaDbPerAvanzamento, variantiDaXmlPerAvanzamento, situazioneAttualeXml.isPresentDatiComuniIdLocale(), situazioneAttualeXml.isPresentDatiComuniIdSimog());
				//gm fine modifica 
				
				/*
				schedeAvanzamento = builder.constructSchedaAvanzamenti(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), positionStart,
						loader.caricaVarianti(situazioneAttualeDb.getStatoAggiudicazione().getIdRecord(), situazioneAttualeDb.getStatoAggiudicazione().getDataInizioRecord()));			
				*/
				schedeAvanzamento = builder.constructSchedaAvanzamenti(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), positionStart,
						tutteLeVariantiPerAvanzamento);			
			
				
				List<AvanzamentoBean> listAvaFromXml = schedeAvanzamento.getAvanzamenti();
//				XX-X: setting delle rif per le schede	
				// XX-X: Assegnamo gli id corretti ai bean correti
				for(AvanzamentoBean avaFromXml : listAvaFromXml){
					for(AvanzamentoBean avaFromDb : listOfAvanzamenti){
						if(avaFromXml.getIdAvanzamento() > 0){
							if(avaFromDb.getIdAvanzamento() == avaFromXml.getIdAvanzamento()){
									
								avaFromXml.setIdAvanzamento(avaFromDb.getIdAvanzamento());
								avaFromXml.setDataInizioAvanzamento(avaFromDb.getDataInizioAvanzamento());
								avaFromXml.setIdAggiudicazione(avaFromDb.getIdAggiudicazione());
								avaFromXml.setDataInizioAggiudicazione(avaFromDb.getDataInizioAggiudicazione());
								avaFromXml.setIdLocale(avaFromDb.getIdLocale());
								// override il settaggio del numero avanzamento
								avaFromXml.setNumeroAvanzamento(avaFromDb.getNumeroAvanzamento());
							}
						}
						if(avaFromXml.getIdLocale() != null && !avaFromXml.getIdLocale().equals("")){
							if(avaFromXml.getIdLocale().equals(avaFromDb.getIdLocale())){

								avaFromXml.setIdAvanzamento(avaFromDb.getIdAvanzamento());
								avaFromXml.setDataInizioAvanzamento(avaFromDb.getDataInizioAvanzamento());
								avaFromXml.setIdAggiudicazione(avaFromDb.getIdAggiudicazione());
								avaFromXml.setDataInizioAggiudicazione(avaFromDb.getDataInizioAggiudicazione());
								avaFromXml.setIdLocale(avaFromDb.getIdLocale());
								// override il settaggio del numero avanzamento
								avaFromXml.setNumeroAvanzamento(avaFromDb.getNumeroAvanzamento());
							}
						}

					}
				}
			}
		}
		
		SchedaConclusione schedaConclusione = null;
		ConclusioneBean conclusione = null;
		if(situazioneAttualeDb.getStatoConclusione().isEsistenteDb()){
			conclusione = loader.caricaConclusione(situazioneAttualeDb.getStatoConclusione().getIdAggiudicazione(), situazioneAttualeDb.getStatoConclusione().getDataInizioAggiudicazione());
			if(situazioneAttualeXml.isPresentConclusione()){
				schedaConclusione = builder.constructSchedaConclusione(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione());
//				XX-X: setting delle rif per le schede	
				schedaConclusione.getConclusione().setIdUltim(conclusione.getIdUltim());
				schedaConclusione.getConclusione().setDataIniUltim(conclusione.getDataIniUltim());
				schedaConclusione.getConclusione().setIdAggiudicazione(conclusione.getIdAggiudicazione());
				schedaConclusione.getConclusione().setDataInizioAggiudicazione(conclusione.getDataInizioAggiudicazione());
				
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(conclusione.getIdLocale() != null){
					schedaConclusione.getConclusione().setIdLocale(conclusione.getIdLocale());
				}
			}
		}
		
		SchedaAccordo schedeAccordo = null;
		if(situazioneAttualeDb.getStatoAccordi() != null && situazioneAttualeDb.getStatoAccordi().size() > 0){
			List<AccordoBean> listOfAccori = loader.caricaAccordi(situazioneAttualeDb.getStatoAccordi().get(0).getIdAggiudicazione(), situazioneAttualeDb.getStatoAccordi().get(0).getDataInizioAggiudicazione());
//			XX-X: setting delle rif per le schede	
			if(situazioneAttualeXml.isPresentAccordi()){
				schedeAccordo = builder.constructSchedaAccordi(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), inizioLavori);
				List<AccordoBean> listOfAccordiFromXml = schedeAccordo.getAccordi();
				for(AccordoBean accFromXml : listOfAccordiFromXml){
					for(AccordoBean accFromDb : listOfAccori){
//						if(accFromXml.getIdAccordo() != 0){
						if(accFromXml.getIdAccordo() > 0){
							if(accFromXml.getIdAccordo() == accFromDb.getIdAccordo()){
								accFromXml.setIdAccordo(accFromDb.getIdAccordo());
								accFromXml.setDataInizioAccordo(accFromDb.getDataInizioAccordo());
								accFromXml.setIdAggiudicazione(accFromDb.getIdAggiudicazione()); 
								accFromXml.setDataInizioAggiudicazione(accFromDb.getDataInizioAggiudicazione());
								accFromXml.setIdLocale(accFromDb.getIdLocale());
							}
						}
						if(accFromXml.getIdLocale() != null && !accFromXml.getIdLocale().equals("")){
							if(accFromXml.getIdLocale().equals(accFromDb.getIdLocale())){
								accFromXml.setIdAccordo(accFromDb.getIdAccordo());
								accFromXml.setDataInizioAccordo(accFromDb.getDataInizioAccordo());
								accFromXml.setIdAggiudicazione(accFromDb.getIdAggiudicazione()); 
								accFromXml.setDataInizioAggiudicazione(accFromDb.getDataInizioAggiudicazione());	
							}
						}
					}
				}

			}
		}
		
		SchedaCollaudo schedaCollaudo = null;
		if(situazioneAttualeDb.getStatoCollaudo().isEsistenteDb()){
			CollaudoBean collaudo = loader.caricaCollaudo(situazioneAttualeDb.getStatoCollaudo().getIdAggiudicazione(), situazioneAttualeDb.getStatoCollaudo().getDataInizioAggiudicazione());		
//			XX-X: setting delle rif per le schede	
			if(situazioneAttualeXml.isPresentCollaudo()){
				schedaCollaudo = builder.constructSchedaCollaudo(datiAggiudicazione, false, 
				      schedaA.getInfoComuni(), schedaA.getAggiudicazione(), 
				      situazioneAttualeXml.isPresentConclusione() ? schedaConclusione.getConclusione() : conclusione, 
				      schedeAccordo != null ? schedeAccordo.getAccordi() :  null,
				      schedaInizio != null ? schedaInizio.getDatiInizio() : null);
				
				schedaCollaudo.getCollaudo().setIdCollaudo(collaudo.getIdCollaudo());
				schedaCollaudo.getCollaudo().setDataIniColl(collaudo.getDataIniColl());
				schedaCollaudo.getCollaudo().setIdAggiudicazione(collaudo.getIdAggiudicazione());
				schedaCollaudo.getCollaudo().setDataIniAggiudicazione(collaudo.getDataIniAggiudicazione());
				
				// XXX - VL - PATCH - retrocompatibilita' id locale
				if(collaudo.getIdLocale() != null){
					schedaCollaudo.getCollaudo().setIdLocale(collaudo.getIdLocale());
				}
				
				if(builder.containsDuplicate){
					this.containsDuplicate = true;;
					this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
				}
			}
		}
		
		SchedaR129 schedeRitardo = null;
		if(situazioneAttualeDb.getStatoRitardo() != null && situazioneAttualeDb.getStatoRitardo().size() > 0){
			List<R129Bean> r129s = loader.caricaRitardi(situazioneAttualeDb.getStatoRitardo().get(0).getIdAggiudicazione(), situazioneAttualeDb.getStatoRitardo().get(0).getDataInizioAggiudicazione());

			if(situazioneAttualeXml.isPresentRitardo()){
				schedeRitardo = builder.constructSchedaRitardi(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione());
				List<R129Bean> listOfRFromXml = schedeRitardo.getR129s();
				for(R129Bean rFromXml : listOfRFromXml){
					for(R129Bean rFromDb : r129s){
						if(rFromXml.getIdRecord() > 0){
							if(rFromXml.getIdRecord() == rFromDb.getIdRecord()){
								rFromXml.setIdRecord(rFromDb.getIdRecord());
								rFromXml.setDataInizioRecord(rFromDb.getDataInizioRecord());
								rFromXml.setIdAggiudicazione(rFromDb.getIdAggiudicazione()); 
								rFromXml.setDataInizioAggiudicazione(rFromDb.getDataInizioAggiudicazione());
								rFromXml.setIdLocale(rFromDb.getIdLocale());
							}
						}
						if(rFromXml.getIdLocale() != null && !rFromXml.getIdLocale().equals("")){
							if(rFromXml.getIdLocale().equals(rFromDb.getIdLocale())){
								rFromXml.setIdRecord(rFromDb.getIdRecord());
								rFromXml.setDataInizioRecord(rFromDb.getDataInizioRecord());
								rFromXml.setIdAggiudicazione(rFromDb.getIdAggiudicazione()); 
								rFromXml.setDataInizioAggiudicazione(rFromDb.getDataInizioAggiudicazione());
								rFromXml.setIdLocale(rFromDb.getIdLocale());
							}
						}
					}
				}
			}
		}
		
		SchedaSospensione schedeSospensione = null;
		if(situazioneAttualeDb.getStatoSospensioni() != null && situazioneAttualeDb.getStatoSospensioni().size() > 0){
			List<SospensioniBean> sospensioni = loader.caricaSospensioni(situazioneAttualeDb.getStatoSospensioni().get(0).getIdAggiudicazione(), situazioneAttualeDb.getStatoSospensioni().get(0).getDataInizioAggiudicazione());

			if(situazioneAttualeXml.isPresentSospensioni()){
				schedeSospensione = builder.constructSchedaSospensioni(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), inizioLavori);
				List<SospensioniBean> listOfSosFromXml = schedeSospensione.getSospensioni();
				for(SospensioniBean sosFromXml : listOfSosFromXml){
					for(SospensioniBean sosFromDb : sospensioni){
						if(sosFromXml.getIdSospensione() > 0){
							if(sosFromXml.getIdSospensione() == sosFromDb.getIdSospensione()){
								sosFromXml.setIdSospensione(sosFromDb.getIdSospensione());
								sosFromXml.setDataInizioSosp(sosFromDb.getDataInizioSosp());
								sosFromXml.setIdAggiudicazione(sosFromDb.getIdAggiudicazione()); 
								sosFromXml.setDataInizioAggiudicazione(sosFromDb.getDataInizioAggiudicazione());
								sosFromXml.setIdLocale(sosFromDb.getIdLocale());
							}
						}
						if(sosFromXml.getIdLocale() != null && !sosFromXml.getIdLocale().equals("")){
							if(sosFromXml.getIdLocale().equals(sosFromDb.getIdLocale())){
								sosFromXml.setIdSospensione(sosFromDb.getIdSospensione());
								sosFromXml.setDataInizioSosp(sosFromDb.getDataInizioSosp());
								sosFromXml.setIdAggiudicazione(sosFromDb.getIdAggiudicazione()); 
								sosFromXml.setDataInizioAggiudicazione(sosFromDb.getDataInizioAggiudicazione());
								sosFromXml.setIdLocale(sosFromDb.getIdLocale());
							}
						}
					}
				}
			}
		}
		
		SchedaSubAppalti schedeSubAppalto = null;
		if(situazioneAttualeDb.getStatoSubAppalti() != null && situazioneAttualeDb.getStatoSubAppalti().size() > 0){
			List<SubappaltiBean> subAppalti = loader.caricaSubAppalti(situazioneAttualeDb.getStatoSubAppalti().get(0).getIdAggiudicazione(), situazioneAttualeDb.getStatoSubAppalti().get(0).getDataInizioAggiudicazione());

			if(situazioneAttualeXml.isPresentSubAppalti()){
				schedeSubAppalto = builder.constructSchedaSubAppalti(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), inizioLavori, schedaA.getAggiudicatari());
				List<SubappaltiBean> listOfSubFromXml = schedeSubAppalto.getSubAppalti();
				for(SubappaltiBean subFromXml : listOfSubFromXml){
					for(SubappaltiBean subFromDb : subAppalti){
						if(subFromXml.getIdRecord() > 0){
							if(subFromXml.getIdRecord() == subFromDb.getIdRecord()){
								subFromXml.setIdRecord(subFromDb.getIdRecord());
								subFromXml.setDataInizioRecord(subFromDb.getDataInizioRecord());
								subFromXml.setIdAggiudicazione(subFromDb.getIdAggiudicazione()); 
								subFromXml.setDataInizioAggiudicazione(subFromDb.getDataInizioAggiudicazione());
								subFromXml.setIdLocale(subFromDb.getIdLocale());
							}
						}
						if(subFromXml.getIdLocale() != null && !subFromXml.getIdLocale().equals("")){
							if(subFromXml.getIdLocale().equals(subFromDb.getIdLocale())){
								subFromXml.setIdRecord(subFromDb.getIdRecord());
								subFromXml.setDataInizioRecord(subFromDb.getDataInizioRecord());
								subFromXml.setIdAggiudicazione(subFromDb.getIdAggiudicazione()); 
								subFromXml.setDataInizioAggiudicazione(subFromDb.getDataInizioAggiudicazione());
								subFromXml.setIdLocale(subFromDb.getIdLocale());
							}
						}
					}
				}
			}
		}
		
		SchedaVariante schedeVariante = null;
		if(situazioneAttualeDb.getStatoVarianti() != null && situazioneAttualeDb.getStatoVarianti().size() > 0){
			List<VarianteBean> varianti = loader.caricaVarianti(situazioneAttualeDb.getStatoVarianti().get(0).getIdAggiudicazione(), situazioneAttualeDb.getStatoVarianti().get(0).getDataInizioAggiudicazione());

			if(situazioneAttualeXml.isPresentVarianti()){
				schedeVariante = builder.constructSchedaVarianti(datiAggiudicazione, false, schedaA.getInfoComuni(), schedaA.getAggiudicazione(), inizioLavori);
				if(builder.containsDuplicate){
					this.containsDuplicate = true;
					this.listOfDuplicateWarning.addAll(builder.listOfDuplicateWarning);
				}
				List<VarianteBean> listOfVarFromXml = schedeVariante.getVarianti();
				for(VarianteBean varFromXml : listOfVarFromXml){
					for(VarianteBean varFromDb : varianti){
						// TODO: VL - NOTA: nel caso in cui l'inserimento sia stato effettuato con id_locale entrer� in entrambe le condizioni,
						// 			perche' sara' valorizzato nel primo if l'id_locale, quindi verra' rispettata la condizione del secondo if
						if(varFromXml.getIdVariante() != 0){
							if(varFromDb.getIdVariante().equals(varFromXml.getIdVariante().longValue())){
								varFromXml.setIdVariante(varFromDb.getIdVariante());
								varFromXml.setDataInizioVar(varFromDb.getDataInizioVar());
								varFromXml.setIdAggiudicazione(varFromDb.getIdAggiudicazione()); 
								varFromXml.setDataInizioAggiudicazione(varFromDb.getDataInizioAggiudicazione());
								varFromXml.setIdLocale(varFromDb.getIdLocale());
							}
						}
						if(varFromXml.getIdLocale() != null && !varFromXml.getIdLocale().equals("")){
							if(varFromXml.getIdLocale().equals(varFromDb.getIdLocale())){
								varFromXml.setIdVariante(varFromDb.getIdVariante());
								varFromXml.setDataInizioVar(varFromDb.getDataInizioVar());
								varFromXml.setIdAggiudicazione(varFromDb.getIdAggiudicazione()); 
								varFromXml.setDataInizioAggiudicazione(varFromDb.getDataInizioAggiudicazione());
								varFromXml.setIdLocale(varFromDb.getIdLocale());
							}
						}
					}
				}
			}
		}

		tutteLeSchede.setSchedaA(schedaA);
		tutteLeSchede.setSchedaCollaudo(schedaCollaudo);
		tutteLeSchede.setSchedaStipula(schedaStipula);
		tutteLeSchede.setSchedaConclusione(schedaConclusione);
		tutteLeSchede.setSchedaInizio(schedaInizio);
		tutteLeSchede.setSchedeAccordo(schedeAccordo);
		tutteLeSchede.setSchedeAvanzamento(schedeAvanzamento);
		tutteLeSchede.setSchedeRitardi(schedeRitardo);
		tutteLeSchede.setSchedeSospensione(schedeSospensione);
		tutteLeSchede.setSchedeSubAppalto(schedeSubAppalto);
		tutteLeSchede.setSchedeVariante(schedeVariante);
		
		tutteLeSchede.setListOfAnagrafichePartecipanti(listOfAnagrafichePartecipanti);
		tutteLeSchede.setListOfAnagraficheResponsabili(listOfAnagraficheResponsabili);
		
		return tutteLeSchede;
	}
	
	private List<AggiudicatarioBean> getAggiudicatariAQ(InfoGaraBean infoGara) {
      
	   return this.loader.getAggiudicatariAQ(infoGara);
	   
   }

   private List<VarianteBean> mergeVariantiPerSchedaAvanzamento (List<VarianteBean> variantiDaDb, List<VarianteBean> variantiDaXml, boolean usingIdLocale, boolean usingIdSimog){
		
		List<VarianteBean> result = new ArrayList<VarianteBean>();
		//se sto usando idLocale o idSimog
		if(usingIdLocale || usingIdSimog){
			
			//mantengo tutte le varianti da xml
			if (variantiDaXml != null)
				result = variantiDaXml;
			
			//variabile per verificare la presenza delle varianti del db nella lista delle varianti xml
			boolean presente = false;
			if(variantiDaDb!=null && variantiDaDb.size()>0){
				for(VarianteBean variante : variantiDaDb){
					if(result!=null && result.size()>0){
						for(VarianteBean varianteRes : result){
        					if(usingIdLocale){
        						//controllo idLocale != null perchè le varianti inserite da web non hanno idLocale 
		        				if(variante.getIdLocale()!=null && variante.getIdLocale().equals(varianteRes.getIdLocale()))
		        					presente = true;
				        	}
        					if(usingIdSimog){
		        				if(variante.getIdVariante().longValue() == varianteRes.getIdVariante().longValue())
		        					presente = true;
				        	}
						}
					}
					//se non ho trovato la variante nella lista, la aggiungo
					//(le varianti da web vengono inserite automaticamente con inserimento per idLocale, 
					//perchè variante.getIdLocale==null e usingIdLocale=true, quindi presente=false)
					if(!presente)
						result.add(variante);
					presente = false;
				}
			}
		}
		//altrimenti si vuole eliminare le varianti dal db ed inserire solo quelle da xml
		else
			result = variantiDaXml;
		return result;
	}
}
