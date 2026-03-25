package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.beans.DatiEconomiciBean;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.VO;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AggiudicazioneBean implements VO{
	
	private long 		idAggiudicazione;
	private Timestamp 	dataInizioAggiudicazione;
	private String 		codiceISTAT;
	private long 		idInfo;
	private Timestamp 	dataInizioInfo;
	private long 		idModalitaGara;
	private int 		numImpreseInvitate;
	private int 		numImpreseRichiedenti;
	private int 		numImpreseOfferenti;
	private int 		numOfferteAmmesse;
	private String 		dataVerbaleAggiudicazione;
	
	private String 		dataScadenzaRichiestaInvito;
	private String 		dataScadenzaPresOfferta;
	private Timestamp 	dataInizioPubbEsito;
	private long 		idPubblicazioneEsito;
	private Timestamp 	dataFineAggiudicazione;
	private String 		cui;
	private int 		progCUI;

	private BigDecimal 	importoAggiudicazione;

	private BigDecimal 	importoAttuazioneSicurezza;
	private long 		idSceltaContraente;
	
	private String 		sistemaQualificazione;
	private String 		criteriSelezioneStabilitiSA;
	private long 		idStato;
	
	private long 		idTipoPrestazione;
	
	private String 		descrizioneStato;
	private String 		cup;
	
	@Deprecated
	private String 		flagAccordoQuadro;
	
	private String 		luogoIstat;
	private String 		luogoNuts;
	private String 		astaElettronica;
	
	private BigDecimal 	percRibassoAgg;
	private BigDecimal 	percOffAumento;
	private String 		dataInvito;
	private int 		numManifInteresse;
	private String      dataManifInteresse;
	private BigDecimal 	importoLavori;
	private BigDecimal 	importoServizi;
	private BigDecimal 	importoForniture;
	private BigDecimal 	importoDisposizione;
	private BigDecimal 	importoProgettazione;
	private String 		flagRichSubappalto;
	private int 		numOfferteEscluse;
	private BigDecimal 	offertaMassimo;
	private BigDecimal 	offertaMinima;
	private BigDecimal 	valSogliaAnomalia;
	private int 		numOfferteFuoriSoglia;
	private int 		numImpEscluseInsufGiust;
	private String 		proceduraAcc;
	private String 		preinformazione;
	private String 		termineRidotto;
	private int 		idModoIndizione; //modo indizione
	private String 		codStrumento;
	
	private BigDecimal 	importoNonAssog;
	private BigDecimal  importoComplessivo;
	private String 		dataStipula;
	private Integer 	durataContrattuale;
	private String 		termineContrattuale;
	private TipoAggiudicazione sottotipo;
	private int progCuiRiaggiudicato;
	private int modalitaRiaggiudicazione;
	
    //gm nuovo per appalti multilotto
	private String codiceContratto;
	private String flagAggiudPrincipale;
	
	//gm nuovo per avvisi aggiudicazione
	private long idPubblicazioneAgg;
	public Timestamp dataPubblicazioneAgg;

	private boolean okCancellazione ;
	
	// PP B302.2.0
	private String idMotivoVarCO;
	private boolean validaVariazione = false;
	
	//private int idRiaggiudicazione;
	
	/*
	private Long durataConvenzione;
	
	public Long getDurataConvenzione() {
		return durataConvenzione;
	}

	public void setDurataConvenzione(Long durataConvenzione) {
		this.durataConvenzione = durataConvenzione;
	}
    */
	//gm nuovo codice 3.0
    private String opereUrbanizzazione;
    
    // un 09/04/2013
    private int origine = OrigineSchedaEnum.WEB.code();

    
    // PP data di riferimento per i validatori in caso di riaggiudicazione, deve essere quella della scheda 
    // originale
    private Timestamp dataValidatore;
    
    private String relazioneUnica; 
    

	public String getOpereUrbanizzazione() {
		return opereUrbanizzazione;
	}

	public void setOpereUrbanizzazione(String opereUrbanizzazione) {
		this.opereUrbanizzazione = opereUrbanizzazione;
	}	
        
	//gm fine nuovo codice 3.0
	
	private String idLocale;
	
	public TipoAggiudicazione getSottotipo() {
		return sottotipo;
	}

	public void setSottotipo(TipoAggiudicazione sottotipo) {
		this.sottotipo = sottotipo;
	}
	
	public String getTermineContrattuale() {
		return termineContrattuale;
	}

	public void setTermineContrattuale(String termineContrattuale) {
		this.termineContrattuale = termineContrattuale;
	}

	public String getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(String dataStipula) {
		this.dataStipula = dataStipula;
	}
	
	

	public Integer getDurataContrattuale() {
		return durataContrattuale;
	}

	public void setDurataContrattuale(Integer durataContrattuale) {
		this.durataContrattuale = durataContrattuale;
	}
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	public boolean isRichAnn(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTO_ANNULLAMENTO));
		else return false;
	}
	
	public boolean isRichDelete(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANCELLAZIONE)
					|| descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANC_TOTALE));
		else return false;
	}

	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
	}
	
	public BigDecimal getImportoComplessivo() {
		return importoComplessivo;
	}

	public void setImportoComplessivo(BigDecimal importoComplessivo) {
		this.importoComplessivo = importoComplessivo;
	}
	
	public String getImportoComplessivoStr() {
		return PageHelper.formattaImporto(importoComplessivo);
	}
	
	public String getProceduraAcc() {
		return proceduraAcc;
	}
	public void setProceduraAcc(String proceduraAcc) {
		this.proceduraAcc = proceduraAcc;
	}
	public String getPreinformazione() {
		return preinformazione;
	}
	public void setPreinformazione(String preinformazione) {
		this.preinformazione = preinformazione;
	}
	public String getTermineRidotto() {
		return termineRidotto;
	}
	public void setTermineRidotto(String termineRidotto) {
		this.termineRidotto = termineRidotto;
	}
	public int getIdModoIndizione() {
		return idModoIndizione;
	}
	public void setIdModoIndizione(int idModoGara) {
		idModoIndizione = idModoGara;
	}
	public int getNumImpEscluseInsufGiust() {
		return numImpEscluseInsufGiust;
	}
	public void setNumImpEscluseInsufGiust(int numImpEscluseInsufGiust) {
		this.numImpEscluseInsufGiust = numImpEscluseInsufGiust;
	}
	public BigDecimal getValSogliaAnomalia() {
		return valSogliaAnomalia;
	}
	public String getValSogliaAnomaliaStr() {
		return PageHelper.replaceDotsWithCommas(valSogliaAnomalia);
	}
	public void setValSogliaAnomalia(BigDecimal valSogliaAnomalia) {
		this.valSogliaAnomalia = valSogliaAnomalia;
	}
	public int getNumOfferteFuoriSoglia() {
		return numOfferteFuoriSoglia;
	}
	public void setNumOfferteFuoriSoglia(int numOfferteFuoriSoglia) {
		this.numOfferteFuoriSoglia = numOfferteFuoriSoglia;
	}
	public BigDecimal getOffertaMinima() {
		return offertaMinima;
	}
	public String getOffertaMinimaStr() {
		return PageHelper.replaceDotsWithCommas(offertaMinima);
	}
	
	public BigDecimal getOffertaMassimo() {
		return offertaMassimo;
	}
	
	public String getOffertaMassimoStr() {
		return  PageHelper.replaceDotsWithCommas(offertaMassimo);
	}
	
	public void setOffertaMassimo(BigDecimal offertaMassimo) {
		this.offertaMassimo = offertaMassimo;
	}
	public void setOffertaMinima(BigDecimal offertaMinima) {
		this.offertaMinima = offertaMinima;
	}
	public String getFlagRichSubappalto() {
		return flagRichSubappalto;
	}
	public void setFlagRichSubappalto(String flagRichSubappalto) {
		this.flagRichSubappalto = flagRichSubappalto;
	}
	public String getDataManifInteresse() {
		return dataManifInteresse;
	}
	public void setDataManifInteresse(
			String dataScadenzaPresenManifestInteresse) {
		this.dataManifInteresse = dataScadenzaPresenManifestInteresse;
	}
	public String getCodiceISTAT() {
		return codiceISTAT;
	}
	public void setCodiceISTAT(String codiceISTAT) {
		this.codiceISTAT = codiceISTAT;
	}
	public String getCriteriSelezioneStabilitiSA() {
		return criteriSelezioneStabilitiSA;
	}
	public void setCriteriSelezioneStabilitiSA(String criteriSelezioneStabilitiSA) {
		this.criteriSelezioneStabilitiSA = criteriSelezioneStabilitiSA;
	}
	public String getCui() {
		return cui;
	}
	public void setCui(String cui) {
		this.cui = cui;
	}
	public Timestamp getDataInizioInfo() {
		return dataInizioInfo;
	}
	public void setDataInizioInfo(Timestamp data_inizio_info) {
		this.dataInizioInfo = data_inizio_info;
	}
	public Timestamp getDataFineAggiudicazione() {
		return dataFineAggiudicazione;
	}
	public void setDataFineAggiudicazione(Timestamp dataFineAggiudicazione) {
		this.dataFineAggiudicazione = dataFineAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public String getViewDataInizioAggiudicazione() {
		return PageHelper.getFormattedDateFromDateTime(dataInizioAggiudicazione.toString());
		
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	
	public Timestamp getDataInizioPubbEsito() {
		return dataInizioPubbEsito;
	}
	public void setDataInizioPubbEsito(Timestamp dataInizioPubbEsito) {
		this.dataInizioPubbEsito = dataInizioPubbEsito;
	}
	
	public String getDataScadenzaPresOfferta() {
		return dataScadenzaPresOfferta;
	}
	public void setDataScadenzaPresOfferta(String dataScadenzaPresOfferta) {
		this.dataScadenzaPresOfferta = dataScadenzaPresOfferta;
	}
	public String getDataScadenzaRichiestaInvito() {
		return dataScadenzaRichiestaInvito;
	}
	public void setDataScadenzaRichiestaInvito(String dataScadenzaRichiestaInvito) {
		this.dataScadenzaRichiestaInvito = dataScadenzaRichiestaInvito;
	}
	public String getDataVerbaleAggiudicazione() {
		return dataVerbaleAggiudicazione;
	}
	public void setDataVerbaleAggiudicazione(String dataVerbaleAggiudicazione) {
		this.dataVerbaleAggiudicazione = dataVerbaleAggiudicazione;
	}
	public long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public long getIdInfo() {
		return idInfo;
	}
	public void setIdInfo(long idInfo) {
		this.idInfo = idInfo;
	}
	
	public long getIdModalitaGara() {
		return idModalitaGara;
	}
	public void setIdModalitaGara(long idModalitaGara) {
		this.idModalitaGara = idModalitaGara;
	}
	public long getIdPubblicazioneEsito() {
		return idPubblicazioneEsito;
	}
	public void setIdPubblicazioneEsito(long idPubblicazioneEsito) {
		this.idPubblicazioneEsito = idPubblicazioneEsito;
	}
	
	public long getIdSceltaContraente() {
		return idSceltaContraente;
	}
	public void setIdSceltaContraente(long idSceltaContraente) {
		this.idSceltaContraente = idSceltaContraente;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public BigDecimal getImportoAggiudicazione() {
		
		return importoAggiudicazione;
	}
	public String getImportoAggiudicazioneStr() {
		return PageHelper.formattaImporto(importoAggiudicazione);
	}
	public void setImportoAggiudicazione(BigDecimal importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}
	public BigDecimal getImportoAttuazioneSicurezza() {
		return importoAttuazioneSicurezza;
	}
	public String getImportoAttuazioneSicurezzaStr() {
		return PageHelper.formattaImporto(importoAttuazioneSicurezza);
	}
	public void setImportoAttuazioneSicurezza(BigDecimal importoAttuazioneSicurezza) {
		this.importoAttuazioneSicurezza = importoAttuazioneSicurezza;
	}
	public int getNumImpreseInvitate() {
		return numImpreseInvitate;
	}
	public void setNumImpreseInvitate(int numImpreseInvitate) {
		this.numImpreseInvitate = numImpreseInvitate;
	}
	public int getNumImpreseOfferenti() {
		return numImpreseOfferenti;
	}
	public void setNumImpreseOfferenti(int numImpreseOfferenti) {
		this.numImpreseOfferenti = numImpreseOfferenti;
	}
	public int getNumImpreseRichiedenti() {
		return numImpreseRichiedenti;
	}
	public void setNumImpreseRichiedenti(int numImpreseRichiedenti) {
		this.numImpreseRichiedenti = numImpreseRichiedenti;
	}
	public int getNumOfferteAmmesse() {
		return numOfferteAmmesse;
	}
	public void setNumOfferteAmmesse(int numOfferteAmmesse) {
		this.numOfferteAmmesse = numOfferteAmmesse;
	}
	public int getProgCUI() {
		return progCUI;
	}
	public void setProgCUI(int progCUI) {
		this.progCUI = progCUI;
	}
	
	public String getSistemaQualificazione() {
		return sistemaQualificazione;
	}
	public void setSistemaQualificazione(String sistemaQualificazione) {
		this.sistemaQualificazione = sistemaQualificazione;
	}

	/*************************************************************************************
	 * Il metodo si occupa di impostare i parametri dell'aggiudicazione passati in ingresso .  
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @param codiceISTAT
	 * @param idModalitaGara
	 * @param numImpreseInvitate
	 * @param numImpreseRichiedenti
	 * @param numImpreseOfferenti
	 * @param numOfferteAmmesse
	 * @param numImpEscluseDopoVerifica
	 * @param dataVerbaleAggiudicazione
	 * @param dataScadenzaRichiestaInvito
	 * @param dataScadenzaPresOfferta
	 * @param dataFineAggiudicazione
	 * @param CUI
	 * @param progCUI
	 * @param importoAggiudicazione
	 * @param importoAttuazioneSicurezza
	 * @param idSceltaContraente
	 * @param sistemaQualificazione
	 * @param criteriSelezioneStabilitiSA
	 * @param idStato
	 * @param dataAppVerbaleGara
	 * @param dataScadenzaPresenManifestInteresse
	 * @return AggiudicazioneBean
	 */
	public AggiudicazioneBean setAggiudicazione(
			long idAggiudicazione,
			Timestamp dataInizioAggiudicazione,
			//String dataInizioAggiudicazione,
			String codiceISTAT,
			//long idInfo,
			//String data_inizio_info,
			//Timestamp data_inizio_info,
			long idModalitaGara,
			int numImpreseInvitate,
			int numImpreseRichiedenti,
			int numImpreseOfferenti,
			int numOfferteAmmesse,
			int numImpEscluseDopoVerifica,
			String dataVerbaleAggiudicazione,
			
			String dataScadenzaRichiestaInvito,
			String dataScadenzaPresOfferta,
			
			Timestamp dataFineAggiudicazione,
			String CUI,
			int progCUI,
			
			BigDecimal importoAggiudicazione,
			
			BigDecimal importoAttuazioneSicurezza,
			long idSceltaContraente,
			
			String sistemaQualificazione,
			String criteriSelezioneStabilitiSA,
			long idStato,
			String dataAppVerbaleGara,
			String dataScadenzaPresenManifestInteresse
			
	
	){
		this.setIdAggiudicazione(idAggiudicazione);
		this.setDataInizioAggiudicazione(dataInizioAggiudicazione);
		this.setCodiceISTAT(codiceISTAT);
		this.setIdInfo(idInfo);
		this.setDataInizioInfo(dataInizioInfo);
		this.setIdModalitaGara(idModalitaGara);
		this.setNumImpreseInvitate(numImpreseInvitate);
		this.setNumImpreseRichiedenti(numImpreseRichiedenti);
		this.setNumImpreseOfferenti(numImpreseOfferenti);
		this.setNumOfferteAmmesse(numOfferteAmmesse);
		this.setDataVerbaleAggiudicazione(dataVerbaleAggiudicazione);
		
		this.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
		this.setDataScadenzaPresOfferta(dataScadenzaPresOfferta);
		this.setDataInizioPubbEsito(dataInizioPubbEsito);
		this.setIdPubblicazioneEsito(idPubblicazioneEsito);
		this.setDataFineAggiudicazione(dataFineAggiudicazione);
		this.setCui(CUI);
		this.setProgCUI(progCUI);
		this.setImportoAggiudicazione(importoAggiudicazione);
		this.setImportoAttuazioneSicurezza(importoAttuazioneSicurezza);
	
		
		this.setIdSceltaContraente(idSceltaContraente);
		
		this.setSistemaQualificazione(sistemaQualificazione);
		this.setCriteriSelezioneStabilitiSA(criteriSelezioneStabilitiSA);
		this.setIdStato(idStato);
		
		this.setDataManifInteresse(dataScadenzaPresenManifestInteresse);
		return this;
	}
	
	public long getIdTipoPrestazione() {
		return idTipoPrestazione;
	}
	public void setIdTipoPrestazione(long idTipoPrestazione) {
		this.idTipoPrestazione = idTipoPrestazione;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	@Deprecated
	public String getFlagAccordoQuadro() {
		return flagAccordoQuadro;
	}
	@Deprecated
	public void setFlagAccordoQuadro(String flagAccordoQuadro) {
		this.flagAccordoQuadro = flagAccordoQuadro;
	}
	public String getLuogoIstat() {
		return luogoIstat;
	}
	public void setLuogoIstat(String luogoIstat) {
		this.luogoIstat = luogoIstat;
	}
	public String getLuogoNuts() {
		return luogoNuts;
	}
	public void setLuogoNuts(String luogoNuts) {
		this.luogoNuts = luogoNuts;
	}
	public String getAstaElettronica() {
		return astaElettronica;
	}
	public void setAstaElettronica(String astaElettronica) {
		this.astaElettronica = astaElettronica;
	}
	
	public BigDecimal getPercRibassoAgg() {
		return percRibassoAgg;
	}
	public String getPercRibassoAggStr() {
		return PageHelper.replaceDotsWithCommas(percRibassoAgg);
	}
	public void setPercRibassoAgg(BigDecimal percRibassoAgg) {
		this.percRibassoAgg = percRibassoAgg;
	}
	public BigDecimal getPercOffAumento() {
		return percOffAumento;
	}
	public void setPercOffAumento(BigDecimal percOffAumento) {
		this.percOffAumento = percOffAumento;
	}
	public String getPercOffAumentoStr() {
		return PageHelper.replaceDotsWithCommas(percOffAumento);
	}
	public String getDataInvito() {
		return dataInvito;
	}
	public void setDataInvito(String dataInvito) {
		this.dataInvito = dataInvito;
	}
	public int getNumManifInteresse() {
		return numManifInteresse;
	}
	public void setNumManifInteresse(int numManifInteresse) {
		this.numManifInteresse = numManifInteresse;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public BigDecimal getImportoLavori() {
		return importoLavori;
	}
	public String getImportoLavoriStr() {
		return PageHelper.formattaImporto(importoLavori);
	}
	public void setImportoLavori(BigDecimal importoLavori) {
		this.importoLavori = importoLavori;
	}
	public BigDecimal getImportoServizi() {
		return importoServizi;
	}
	public String getImportoServiziStr() {
		return PageHelper.formattaImporto(importoServizi);
	}
	public void setImportoServizi(BigDecimal importoServizi) {
		this.importoServizi = importoServizi;
	}
	public BigDecimal getImportoForniture() {
		return importoForniture;
	}
	public String getImportoFornitureStr() {
		return PageHelper.formattaImporto(importoForniture);
	}
	public void setImportoForniture(BigDecimal importoForniture) {
		this.importoForniture = importoForniture;
	}
	public BigDecimal getImportoDisposizione() {
		return importoDisposizione;
	}

	/********************************************************************************************************
	 * restituisce l'importo a disposizione nel formato stringa con punti come separatori di migliaia 
	 * e virgole per la separazione con i decimali
	 * @return String
	 */
	public String getImportoDisposizioneStr() {
		//altrimenti ritorno il valore formattato (anche 0);
		//return PageHelper.replaceDotsWithCommas(importoDisposizione);
		//altrimenti ritorno il valore formattato (anche 0);
		return PageHelper.formattaImporto(importoDisposizione);
	}
	public void setImportoDisposizione(BigDecimal importoDisposizione) {
		this.importoDisposizione = importoDisposizione;
	}
	public BigDecimal getImportoProgettazione() {
		return importoProgettazione;
	}
	public String getImportoProgettazioneStr() {
		return PageHelper.formattaImporto(importoProgettazione);
	}
	public void setImportoProgettazione(BigDecimal importoProgettazione) {
		this.importoProgettazione = importoProgettazione;
	}
	public int getNumOfferteEscluse() {
		return numOfferteEscluse;
	}
	public void setNumOfferteEscluse(int numOfferteEscluse) {
		this.numOfferteEscluse = numOfferteEscluse;
	}
	
	public String getCodStrumento() {
		return codStrumento;
	}
	public void setCodStrumento(String codStrumento) {
		this.codStrumento = codStrumento;
	}
	
	public BigDecimal getImportoNonAssog() {
		return importoNonAssog;
	}
	public String getImportoNonAssogStr() {
		return PageHelper.formattaImporto(importoNonAssog);
	}
	public void setImportoNonAssog(BigDecimal importoNonAssog) {
		this.importoNonAssog = importoNonAssog;
	}

	public boolean isOkCancellazione() {
		return okCancellazione;
	}

	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}
	/*
	public int getIdRiaggiudicazione(){
		return idRiaggiudicazione;
	}
	
	public void setIdRiaggiudicazione(int idRiaggiudicazione){
		this.idRiaggiudicazione = idRiaggiudicazione;
	}
	*/

	public int getProgCuiRiaggiudicato() {
		return progCuiRiaggiudicato;
	}

	public void setProgCuiRiaggiudicato(int progCuiRiaggiudicato) {
		this.progCuiRiaggiudicato = progCuiRiaggiudicato;
	}

	public int getModalitaRiaggiudicazione() {
		return modalitaRiaggiudicazione;
	}

	public void setModalitaRiaggiudicazione(int modalitaRiaggiudicazione) {
		this.modalitaRiaggiudicazione = modalitaRiaggiudicazione;
	}	
	//gm nuovo per appalti multilotto
	public String getCodiceContratto() {
		return codiceContratto;
	}
	public void setCodiceContratto(String codiceContratto) {
		this.codiceContratto = codiceContratto;
	}
	public String getFlagAggiudPrincipale() {
		return flagAggiudPrincipale;
	}
	public void setFlagAggiudPrincipale(String flagAggiudPrincipale) {
		this.flagAggiudPrincipale = flagAggiudPrincipale;
	}
	public String getCig() {
		return this.cui.substring(2,12);
	}
	
	public long getIdPubblicazioneAgg() {
		return idPubblicazioneAgg;
	}
	public void setIdPubblicazioneAgg(long idPubblicazioneAgg) {
		this.idPubblicazioneAgg = idPubblicazioneAgg;
	}
	
	public void setDataPubblicazioneAgg(Timestamp dataPubblicazioneAgg) {
		this.dataPubblicazioneAgg = dataPubblicazioneAgg;
	}
	
	public Timestamp getDataPubblicazioneAgg() {
		return dataPubblicazioneAgg;
	}

	public String getIdMotivoVarCO() {
		return idMotivoVarCO;
	}

	public void setIdMotivoVarCO(String idMotivoVarCO) {
		this.idMotivoVarCO = idMotivoVarCO;
	}

	public boolean isValidaVariazione() {
		return validaVariazione;
	}

	public void setValidaVariazione(boolean validaVariazione) {
		this.validaVariazione = validaVariazione;
	}
	
	   // PP 3.02.3.3
    private DatiEconomiciBean datiEconomici;

    public DatiEconomiciBean getDatiEconomici() {
       return datiEconomici;
    }

    public void setDatiEconomici(DatiEconomiciBean datiEconomici) {
       this.datiEconomici = datiEconomici;
    }
     
    public boolean getHasDatiEconomici() {
       return this.datiEconomici != null;
    }

   public int getOrigine() {
      return origine;
   }

   public void setOrigine(int origine) {
      this.origine = origine;
   }
    
   public boolean isFromAVCPass(){
      return origine == OrigineSchedaEnum.AVCPASS.code();
   }

   public Timestamp getDataValidatore() {
      return dataValidatore;
   }

   public void setDataValidatore(Timestamp dataValidatore) {
      this.dataValidatore = dataValidatore;
   }

	public String getRelazioneUnica() {
		return relazioneUnica;
	}
	
	public void setRelazioneUnica(String relazioneUnica) {
		this.relazioneUnica = relazioneUnica;
	}
	   
   
   
}
