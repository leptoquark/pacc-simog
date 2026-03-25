package it.avlp.simog.beans;

import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletStipula;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * Classe che contiene gli identificativi di ogni scheda, e' strutturata in modo tale
 * da permettere solamente l'istanziazione delle schede realmente esistenti, vincolandole
 * ad un indice oltre che ad un nome.
 * Aggiunta la possibilita' di ricavare altre decodifiche in particolare:
 * - decodifica vn -> nomenclatura schede attuale per il flusso sul SimogWeb
 * - decodifica blocco -> nomenclatura schede attuale per la gestione delle richieste annullamento
 * TODO: unificare la codifica.
 * 
 * @author vletizia
 *
 */
public class IdentificativoSchede {

	public final static String DATI_COMUNI 	= "DATI_COMUNI";
	public final static int INDICE_DATI_COMUNI 	= 0;
	
	public final static String AGGIUDICAZIONE = "AGGIUDICAZIONE";
	public final static int INDICE_AGGIUDICAZIONE = 1;
	
	public final static String ACCORDO_BONARIO 	= "ACCORDO_BONARIO";
	public final static int INDICE_ACCORDO_BONARIO 	= 2;
	
	public final static String STATO_AVANZAMENTO = "STATO_AVANZAMENTO";
	public final static int INDICE_STATO_AVANZAMENTO 	= 3;
	
	public final static String COLLAUDO = "COLLAUDO";
	public final static int INDICE_COLLAUDO = 4;
	
	public final static String FINE_LAVORI 	= "FINE_LAVORI";
	public final static int INDICE_FINE_LAVORI 	= 5;
	
	public final static String FASE_INIZIALE = "FASE_INIZIALE";
	public final static int INDICE_FASE_INIZIALE = 6;
	
	public final static String IPOTESI_RECESSO 	= "IPOTESI_RECESSO";
	public final static int INDICE_IPOTESI_RECESSO 	= 7;
	
	public final static String SOSPENSIONE 	= "SOSPENSIONE";
	public final static int INDICE_SOSPENSIONE 	= 8;
	
	public final static String SUBAPPALTO = "SUBAPPALTO";
	public final static int INDICE_SUBAPPALTO = 9;
	
	public final static String VARIANTE = "VARIANTE";
	public final static int INDICE_VARIANTE 		= 10;
	
	public final static String STIPULA = "STIPULA";
	public final static int INDICE_STIPULA 		= 11;
	
	public final static String ADESIONE = "ADESIONE";
	public final static int INDICE_ADESIONE 	= 12;

	public final static String SOTTOSOGLIA = "SOTTOSOGLIA";
	public final static int INDICE_SOTTOSOGLIA 		= 13;
	
	public final static String ESCLUSO = "ESCLUSO";
	public final static int INDICE_ESCLUSO 		= 14;

	private static ArrayList<IdentificativoSchede> asList = new ArrayList<IdentificativoSchede>();
	private static ArrayList<IdentificativoSchede> asListSoloMultiple = new ArrayList<IdentificativoSchede>();
	private static ArrayList<IdentificativoSchede> asListSoloSingole = new ArrayList<IdentificativoSchede>();
	// valorizzazione lista.
	static {
		asList.add(INDICE_DATI_COMUNI,new IdentificativoSchede(DATI_COMUNI,INDICE_DATI_COMUNI));
		asList.add(INDICE_AGGIUDICAZIONE,new IdentificativoSchede(AGGIUDICAZIONE,INDICE_AGGIUDICAZIONE));
		asList.add(INDICE_ACCORDO_BONARIO,new IdentificativoSchede(ACCORDO_BONARIO,INDICE_ACCORDO_BONARIO));
		asList.add(INDICE_STATO_AVANZAMENTO,new IdentificativoSchede(STATO_AVANZAMENTO,INDICE_STATO_AVANZAMENTO));
		asList.add(INDICE_COLLAUDO,new IdentificativoSchede(COLLAUDO,INDICE_COLLAUDO));
		asList.add(INDICE_FINE_LAVORI,new IdentificativoSchede(FINE_LAVORI,INDICE_FINE_LAVORI));
		asList.add(INDICE_FASE_INIZIALE,new IdentificativoSchede(FASE_INIZIALE,INDICE_FASE_INIZIALE));
		asList.add(INDICE_IPOTESI_RECESSO,new IdentificativoSchede(IPOTESI_RECESSO,INDICE_IPOTESI_RECESSO));
		asList.add(INDICE_SOSPENSIONE,new IdentificativoSchede(SOSPENSIONE,INDICE_SOSPENSIONE));
		asList.add(INDICE_SUBAPPALTO,new IdentificativoSchede(SUBAPPALTO,INDICE_SUBAPPALTO));
		asList.add(INDICE_VARIANTE,new IdentificativoSchede(VARIANTE,INDICE_VARIANTE));
		asList.add(INDICE_STIPULA,new IdentificativoSchede(STIPULA,INDICE_STIPULA));
		asList.add(INDICE_ADESIONE,new IdentificativoSchede(ADESIONE,INDICE_ADESIONE));
		asList.add(INDICE_SOTTOSOGLIA,new IdentificativoSchede(SOTTOSOGLIA,INDICE_SOTTOSOGLIA));
		asList.add(INDICE_ESCLUSO,new IdentificativoSchede(ESCLUSO,INDICE_ESCLUSO));
		
		asListSoloMultiple.add(new IdentificativoSchede(ACCORDO_BONARIO,INDICE_ACCORDO_BONARIO));
		asListSoloMultiple.add(new IdentificativoSchede(STATO_AVANZAMENTO,INDICE_STATO_AVANZAMENTO));
		asListSoloMultiple.add(new IdentificativoSchede(IPOTESI_RECESSO,INDICE_IPOTESI_RECESSO));
		asListSoloMultiple.add(new IdentificativoSchede(SOSPENSIONE,INDICE_SOSPENSIONE));
		asListSoloMultiple.add(new IdentificativoSchede(SUBAPPALTO,INDICE_SUBAPPALTO));
		asListSoloMultiple.add(new IdentificativoSchede(VARIANTE,INDICE_VARIANTE));
		
		asListSoloSingole.add(new IdentificativoSchede(DATI_COMUNI,INDICE_DATI_COMUNI));
		asListSoloSingole.add(new IdentificativoSchede(AGGIUDICAZIONE,INDICE_AGGIUDICAZIONE));
		asListSoloSingole.add(new IdentificativoSchede(COLLAUDO,INDICE_COLLAUDO));
		asListSoloSingole.add(new IdentificativoSchede(FINE_LAVORI,INDICE_FINE_LAVORI));
		asListSoloSingole.add(new IdentificativoSchede(FASE_INIZIALE,INDICE_FASE_INIZIALE));
		asListSoloSingole.add(new IdentificativoSchede(STIPULA,INDICE_STIPULA));
		asListSoloSingole.add(new IdentificativoSchede(ADESIONE,INDICE_ADESIONE));
		asListSoloSingole.add(new IdentificativoSchede(SOTTOSOGLIA,INDICE_SOTTOSOGLIA));
		asListSoloSingole.add(new IdentificativoSchede(ESCLUSO,INDICE_ESCLUSO));		
	}
	
	private String nomeScheda;
	private int indiceScheda;
	private String blocco;
	
   public static final String TAB_INFO_COMUNI = "ATabInfoComuni";
	public static final String TAB_AGGIUDICAZIONE = "BTabAggiudicazione";
	public static final String TAB_INIZIO_LAVORI = "CTabInizioLavori";
	public static final String TAB_AVANZAMENTO = "DTabAvanzamento";
	public static final String TAB_FINELAVORI = "ETabFineLavori";
	public static final String TAB_COLLAUDO = "FTabCollaudo";
	public static final String TAB_SOSPENSIONE = "GSospensione";
	public static final String TAB_VARIANTE = "HVariante";
	public static final String TAB_ACCORDO = "IAccordo";
	public static final String TAB_SUBAPPALTO = "LSubappalto";
	public static final String TAB_RITARDO = "MRitardo";
	public static final String TAB_STIPULA = "NStipula";
	public static final String TAB_ADESIONE = "OTabAdesione";
	public static final String TAB_SOTTOSOGLIA = "BSottosoglia";
	public static final String TAB_ESCLUSI = "BEsclusi";
	
	/**
	 * Costruttore principale privato in modo da evitare istanziazione con parametri "fantasiosi"
	 * 
	 * @param nomeScheda
	 * @param indiceScheda
	 * @throws NotFound 
	 */
	private IdentificativoSchede(String nomeScheda, int indiceScheda){
		this.nomeScheda = nomeScheda;
		this.indiceScheda = indiceScheda;
		try {
         this.blocco = getDecodificaBlocco();
      } catch (NotFound e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
	public static IdentificativoSchede getById(int idScheda){
		if(idScheda >= asList.size())
			return null;
		else return asList.get(idScheda);
	}
	/**
	 * @return L'identificativo della scheda "dati comuni"
	 */
	public static IdentificativoSchede getDatiComuni(){
		return asList.get(INDICE_DATI_COMUNI);
	}
	/**
	 * @return L'identificativo della scheda "aggiudicazione"
	 */
	public static IdentificativoSchede getAggiudicazione(){
		return asList.get(INDICE_AGGIUDICAZIONE);
	}
	/**
	 * @return L'identificativo della scheda "accordi"
	 */
	public static IdentificativoSchede getAccordi(){
		return asList.get(INDICE_ACCORDO_BONARIO);
	}
	/**
	 * @return L'identificativo della scheda "avanzamenti"
	 */
	public static IdentificativoSchede getAvanzamenti(){
		return asList.get(INDICE_STATO_AVANZAMENTO);
	}
	/**
	 * @return L'identificativo della scheda "collaudo"
	 */
	public static IdentificativoSchede getCollaudo(){
		return asList.get(INDICE_COLLAUDO);
	}
	/**
	 * @return L'identificativo della scheda "conclusione"
	 */
	public static IdentificativoSchede getConclusione(){
		return asList.get(INDICE_FINE_LAVORI);
	}
	/**
	 * @return L'identificativo della scheda "inizio lavori"
	 */
	public static IdentificativoSchede getInizioLavori(){
		return asList.get(INDICE_FASE_INIZIALE);
	}
	/**
	 * @return L'identificativo della scheda "ritardo"
	 */
	public static IdentificativoSchede getRitardo(){
		return asList.get(INDICE_IPOTESI_RECESSO);
	}
	/**
	 * @return L'identificativo della scheda "sospensioni"
	 */
	public static IdentificativoSchede getSospensioni(){
		return asList.get(INDICE_SOSPENSIONE);
	}
	/**
	 * @return L'identificativo della scheda "subAppalti"
	 */
	public static IdentificativoSchede getSubAppalti(){
		return asList.get(INDICE_SUBAPPALTO);
	}
	/**
	 * @return L'identificativo della scheda "varianti"
	 */
	public static IdentificativoSchede getVarianti(){
		return asList.get(INDICE_VARIANTE);
	}
	/**
	 * @return L'identificativo della scheda "stipula"
	 */
	public static IdentificativoSchede getStipula(){
		return asList.get(INDICE_STIPULA);
	}
	/**
	 * @return L'identificativo della scheda "adesione"
	 */
	public static IdentificativoSchede getAdesione(){
		return asList.get(INDICE_ADESIONE);
	}
	
	/**
	 * @return L'identificativo della scheda "contratti sottosoglia"
	 */
	public static IdentificativoSchede getSottosoglia(){
		return asList.get(INDICE_SOTTOSOGLIA);
	}
	
	/**
	 * @return L'identificativo della scheda "contratto escluso"
	 */
	public static IdentificativoSchede getEscluso(){
		return asList.get(INDICE_ESCLUSO);
	}
	
	/**
	 * Per il Massloader in modo da recuperare subito l'identificativo scheda in base al
	 * parametro SCHEDA nelle schede da cancellare.
	 * Attenzione le stringhe vaide sono solamente le costanti dichiarate in questa classe
	 * 
	 * @param nomeScheda
	 * @return
	 * @throws NotFound
	 */
	public static IdentificativoSchede findIdentificativoByName(String nomeScheda) throws NotFound{
		for (IdentificativoSchede identCorrente : asList){
			if(identCorrente.getNomeScheda().equals(nomeScheda)) return identCorrente;
		}throw new NotFound();
	}

    /**
     * recuoera l'identificativo in base al blocco
     * @param nomeScheda
     * @return
     * @throws NotFound
     */
    public static IdentificativoSchede findIdentificativoByBlocco(String blocco) throws NotFound{
        for (IdentificativoSchede identCorrente : asList){
            if(identCorrente.getBlocco().equals(blocco)) return identCorrente;
        }throw new NotFound();
    }

    /**
	 * Metodo che permette di iterare un' arraylist contenente tutti gli identificativi di
	 * scheda permessi.
	 * 
	 * Non viene restituita la lista per evitarne la modifica impropria.
	 * 
	 * @return
	 */
	public static Iterator<IdentificativoSchede> iterator(){
		return asList.iterator();
	}
	/**
	 * Metodo che permette di iterare un' arraylist contenente tutti gli identificativi delle sole schede multiple.
	 * Attenzione la lista delle schede sole multiple non e' strutturata come quella che contiene tutti i nomi schede
	 * la posizione non corrisponde ad una scheda in questo caso occorre fare affidamento al nome scheda
	 * 
	 * Non viene restituita la lista per evitarne la modifica impropria.
	 * 
	 * @return
	 */
	public static Iterator<IdentificativoSchede> iteratorSoleSchedeMultiple(){
		return asListSoloMultiple.iterator();
	}
	/**
	 * Metodo che permette di iterare un' arraylist contenente tutti gli identificativi delle schede singole.
	 * Attenzione la lista delle schede sole singole non e' strutturata come quella che contiene tutti i nomi schede
	 * la posizione non corrisponde ad una scheda in questo caso occorre fare affidamento al nome scheda
	 * 
	 * Non viene restituita la lista per evitarne la modifica impropria.
	 * 
	 * @return
	 */	
	public static Iterator<IdentificativoSchede> iteratorSoleSchedeSingole(){
		return asListSoloSingole.iterator();
	}
	/**
	 * @return Il nome della scheda dell'Identificativo corrente (istanza corrente)
	 */
	public String getNomeScheda() {
		return nomeScheda;
	}

	/**
	 * @return L'indice della scheda dell'Identificativo corrente (istanza corrente)
	 */
	public int getIndiceScheda() {
		return indiceScheda;
	}

	   public String getBlocco() {
	      return blocco;
	   }

	/**
	 * Ritorna la codifica "blocco dati" dellidentificativo attuale
	 * 
	 * @return
	 */
	public String getDecodificaBlocco() throws NotFound{
		//identificativi delle schede per richiesta annullamento
		if(this.indiceScheda == INDICE_DATI_COMUNI)return TAB_INFO_COMUNI;
		if(this.indiceScheda == INDICE_AGGIUDICAZIONE)return TAB_AGGIUDICAZIONE;
		if(this.indiceScheda == INDICE_FASE_INIZIALE)return TAB_INIZIO_LAVORI;
		if(this.indiceScheda == INDICE_STATO_AVANZAMENTO)return TAB_AVANZAMENTO;
		if(this.indiceScheda == INDICE_FINE_LAVORI)return TAB_FINELAVORI;
		if(this.indiceScheda == INDICE_COLLAUDO)return TAB_COLLAUDO;
		if(this.indiceScheda == INDICE_SOSPENSIONE)return TAB_SOSPENSIONE;
		if(this.indiceScheda == INDICE_VARIANTE)return TAB_VARIANTE;
		if(this.indiceScheda == INDICE_ACCORDO_BONARIO)return TAB_ACCORDO;
		if(this.indiceScheda == INDICE_SUBAPPALTO)return TAB_SUBAPPALTO;
		if(this.indiceScheda == INDICE_IPOTESI_RECESSO)return TAB_RITARDO;
		if(this.indiceScheda == INDICE_STIPULA)return TAB_STIPULA;
		if(this.indiceScheda == INDICE_ADESIONE)return TAB_ADESIONE;
		if(this.indiceScheda == INDICE_SOTTOSOGLIA)return TAB_SOTTOSOGLIA;
		if(this.indiceScheda == INDICE_ESCLUSO)return TAB_ESCLUSI;
		throw new NotFound();
	}
	
	/**
	 * Ritorna la codifica "vn" usata nel workflow dell'identificativo attuale
	 * 
	 * @return
	 */
	public String getDecodificaVN() throws NotFound{
		if(this.indiceScheda == INDICE_DATI_COMUNI)return "Dati Comuni";
		if(this.indiceScheda == INDICE_AGGIUDICAZIONE)return "Aggiudicazione";
		if(this.indiceScheda == INDICE_FASE_INIZIALE)return "Fase Iniziale";
		if(this.indiceScheda == INDICE_STATO_AVANZAMENTO)return "Avanzamento";
		if(this.indiceScheda == INDICE_FINE_LAVORI)return "Conclusione";
		if(this.indiceScheda == INDICE_COLLAUDO)return "Collaudo";		
		if(this.indiceScheda == INDICE_ACCORDO_BONARIO)return "Accordi Bonari";
		if(this.indiceScheda == INDICE_SOSPENSIONE)return "Sospensioni";
		if(this.indiceScheda == INDICE_VARIANTE)return "Modifica Contrattuale";
		if(this.indiceScheda == INDICE_SUBAPPALTO)return "Subappalto";
		if(this.indiceScheda == INDICE_IPOTESI_RECESSO)return "Istanza di Recesso";
		if(this.indiceScheda == INDICE_STIPULA)return "Stipula";
		if(this.indiceScheda == INDICE_ADESIONE)return "Adesione";
		if(this.indiceScheda == INDICE_SOTTOSOGLIA)return "Aggiudicazione (sotto 150)";
		if(this.indiceScheda == INDICE_ESCLUSO)return "Aggiudicazione (esclusi)";
		throw new NotFound();
	}
	
	/***
	 * ritorna i nomi schede utilizzati nel frontend
	 * 
	 * @return mappa con blocco dati e nome scheda
	 */
	public static HashMap<String, String> getSchede(){
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			map.put(IdentificativoSchede.TAB_INFO_COMUNI,getDatiComuni().getDecodificaVN());
			map.put(IdentificativoSchede.TAB_AGGIUDICAZIONE,getAggiudicazione().getDecodificaVN());	
			map.put(IdentificativoSchede.TAB_INIZIO_LAVORI,getInizioLavori().getDecodificaVN());
			map.put(ParametriServletAvanzamento.TAB_AVANZAMENTO,getAvanzamenti().getDecodificaVN());
			map.put(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI,getConclusione().getDecodificaVN());	
			map.put(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO,getCollaudo().getDecodificaVN());
			map.put(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI,getSospensioni().getDecodificaVN());
			map.put(ParametriServletVariante.TAB_SCHEDA_VARIANTE,getVarianti().getDecodificaVN());
			map.put(ParametriServletAccordo.TAB_SCHEDA_ACCORDO,getAccordi().getDecodificaVN());
			map.put(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI,getSubAppalti().getDecodificaVN());	
			map.put(ParametriServletR129.TAB_SCHEDA_R129,getRitardo().getDecodificaVN());
			map.put(ParametriServletStipula.TAB_STIPULA,getStipula().getDecodificaVN());
			map.put(IdentificativoSchede.TAB_ADESIONE,getAdesione().getDecodificaVN());	
			map.put(PSBD.TAB_SOTTOSOGLIA,getSottosoglia().getDecodificaVN());
			map.put(PSBD.TAB_ESCLUSI,getEscluso().getDecodificaVN());
		} catch (NotFound e) {}			
		return map;
	}

	/**
	 * Il metodo permette di risalire al nome della tabella 
	 * associato alla stringa in ingresso
	 * 
	 * @param nome String in base alla quale e' mappato il nome della tabella 
	 * @return String relativa al nome della Tabella
	 */
	
	public static String getScheda(String nome){ 
		return getSchede().get(nome);
	}
	
//	/**
//	 * Ritorna il nome della tabella per la nomenclatura selezionata
//	 * 
//	 * @return
//	 */
//	public String getDecodificaTableName() throws NotFound{
//		if(this.indiceScheda == INDICE_DATI_COMUNI)return it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_AGGIUDICAZIONE)return it.avlp.simog.db.generated.AGGIUDICAZIONI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_FASE_INIZIALE)return it.avlp.simog.db.generated.INIZIO_LAVORI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_STATO_AVANZAMENTO)return it.avlp.simog.db.generated.STATI_AVANZ.TABLE_NAME;
//		if(this.indiceScheda == INDICE_FINE_LAVORI)return it.avlp.simog.db.generated.FINE_LAVORI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_COLLAUDO)return it.avlp.simog.db.generated.COLLAUDO.TABLE_NAME;		
//		if(this.indiceScheda == INDICE_ACCORDO_BONARIO)return it.avlp.simog.db.generated.ACCORDI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_SOSPENSIONE)return it.avlp.simog.db.generated.SOSPENSIONI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_VARIANTE)return it.avlp.simog.db.generated.VARIANTI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_SUBAPPALTO)return it.avlp.simog.db.generated.SUBAPPALTI.TABLE_NAME;
//		if(this.indiceScheda == INDICE_IPOTESI_RECESSO)return it.avlp.simog.db.generated.R129.TABLE_NAME;
//		throw new NotFound();
//	}
	
//	/**
//	 * For valid tablename see:
//	 * 	it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.AGGIUDICAZIONI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.INIZIO_LAVORI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.STATI_AVANZ.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.FINE_LAVORI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.COLLAUDO.TABLE_NAME;		
//	 *	it.avlp.simog.db.generated.ACCORDI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.SOSPENSIONI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.VARIANTI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.SUBAPPALTI.TABLE_NAME;
//	 *	it.avlp.simog.db.generated.R129.TABLE_NAME;
//	 *
//	 * @param tableName
//	 * @return IdentificativoSchede che rappresenta l'identificativo della scheda cercato
//	 */
//	public static IdentificativoSchede getIdentificativoSchedeByNomeScheda(String tableName) throws NotFound{
//		
//		if(it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(DATI_COMUNI, INDICE_DATI_COMUNI);
//		if(it.avlp.simog.db.generated.AGGIUDICAZIONI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(AGGIUDICAZIONE, INDICE_AGGIUDICAZIONE);
//		if(it.avlp.simog.db.generated.INIZIO_LAVORI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(FASE_INIZIALE, INDICE_FASE_INIZIALE);
//		if(it.avlp.simog.db.generated.STATI_AVANZ.TABLE_NAME.equals(tableName))return new IdentificativoSchede(STATO_AVANZAMENTO, INDICE_STATO_AVANZAMENTO);
//		if(it.avlp.simog.db.generated.FINE_LAVORI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(FINE_LAVORI, INDICE_FINE_LAVORI);
//		if(it.avlp.simog.db.generated.COLLAUDO.TABLE_NAME.equals(tableName))return new IdentificativoSchede(COLLAUDO, INDICE_COLLAUDO);		
//		if(it.avlp.simog.db.generated.ACCORDI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(ACCORDO_BONARIO, INDICE_ACCORDO_BONARIO);
//		if(it.avlp.simog.db.generated.SOSPENSIONI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(SOSPENSIONE, INDICE_SOSPENSIONE);
//		if(it.avlp.simog.db.generated.VARIANTI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(VARIANTE, INDICE_VARIANTE);
//		if(it.avlp.simog.db.generated.SUBAPPALTI.TABLE_NAME.equals(tableName))return new IdentificativoSchede(SUBAPPALTO, INDICE_SUBAPPALTO);
//		if(it.avlp.simog.db.generated.R129.TABLE_NAME.equals(tableName))return new IdentificativoSchede(IPOTESI_RECESSO, INDICE_IPOTESI_RECESSO);
//		
//		throw new NotFound();
//	}
	
	public String toString(){
		String riepilogo = "Riepilogo dell'Identificativo attuale: \r\n";
		riepilogo += "\tnomeScheda: "+nomeScheda+"\r\n";
		riepilogo += "\tindiceScheda: "+indiceScheda+"\r\n";
		return riepilogo;
	}
}
