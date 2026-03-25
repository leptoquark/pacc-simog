package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.gestioneannullamentomanager.AnnullamentoManager;
import it.avlp.simog.util.SimogProperties;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class BaseSharedAction {
	
	public enum AVCPassSemaforo{
      VERDE ("-1", "Verde")
    , GIALLO("0","Giallo")
    , ROSSO("1","Rosso");
      
      private String codice;
      private String descrizione;
      
      public String codice() {return getCodice();}
      public String descrizione() {return descrizione;}
      
      AVCPassSemaforo(String codProfilo, String descrizione){
        this.setCodice(codProfilo);
        this.descrizione = descrizione;
      }
      public static AVCPassSemaforo getEnumBycodice(String codInp) 
      {
         AVCPassSemaforo[] lista = values();
        for(int i=0; i<lista.length;i++) {
           if (lista[i].codice().equals(codInp))
              return lista[i];
        }
        return null;
      }
      public String getCodice() {
         return codice;
      }
      public void setCodice(String codice) {
         this.codice = codice;
      }
   }

   public enum AVCPassStatus {
   
      PERFEZIONATO ("0", "Perfezionato", AVCPassSemaforo.VERDE)
    , COMMISSIONE("1","Commissione", AVCPassSemaforo.VERDE) // Alcune funzioni sono possibili, non può essere annullato, 
                                                             //revocato o modificate le date di pubblicazione e scadenza offerte
                                                             //  16.07.2013 per ora solo rosso o verde !!!
                                                             // 23.07.2014 Pizziconi richiesto passaggio a VERDE
    , LOTTO_DESERTO("2","Lotto deserto", AVCPassSemaforo.VERDE) 
    , ACQUISIZIONE_PARTECIPANTE("3","Acquisizione partecipante", AVCPassSemaforo.ROSSO)
    , FINE_ACQUISIZIONE_PARTECIPANTE("4","Fine Acquisizione partecipante", AVCPassSemaforo.ROSSO)
    , GRADUATORIA("5","Graduatoria", AVCPassSemaforo.ROSSO)
    , AGGIUDICAZIONE("6","Aggiudicazione", AVCPassSemaforo.ROSSO)
    , AGGIUDICATO("7","Aggiudicato", AVCPassSemaforo.VERDE) // 18.06.2013 richiesta mdifica da Pizziconi AVCPassSemaforo.ROSSO
    , ANNULLATO("8","Annullato", AVCPassSemaforo.VERDE)
    , ARCHIVIATO("9","Archiviato", AVCPassSemaforo.VERDE)
    ;
   
     private String codice;
     private String descrizione;
     private AVCPassSemaforo semaforo;
      
     public String codice() {return codice;}
     public String descrizione() {return descrizione;}
   
     AVCPassStatus(String codProfilo, String descrizione, AVCPassSemaforo semaforo){
       this.codice = codProfilo;
       this.descrizione = descrizione;
       this.setSemaforo(semaforo);
     }
   
     public static AVCPassStatus getEnumBycodice(String codInp) 
     {
       AVCPassStatus[] lista = values();
       for(int i=0; i<lista.length;i++) {
          if (lista[i].codice().equals(codInp))
             return lista[i];
       }
       return null;
     }
      public AVCPassSemaforo getSemaforo() {
         return semaforo;
      }
      public void setSemaforo(AVCPassSemaforo semaforo) {
         this.semaforo = semaforo;
      }
   }

   public enum AVCPassEsiti {
      
      ERR_GENERICO_SISTEMA ("Si e' verificato un errore imprevisto durante l'elaborazione", AVCPassSemaforo.ROSSO)
    , ERR_CAMPI_CIG_CODICE_GARA_ENTRAMBI_PRESENTI("E' necessario inserire, obbligatoriamente, solamente uno  dei campi 'CIG' o 'Codice Gara'", AVCPassSemaforo.ROSSO)
    , ERR_CAMPI_CIG_CODICE_GARA_XOR("Uno dei campi 'CIG' o 'Codice Gara' deve essere valorizzato", AVCPassSemaforo.ROSSO) 
    , ERR_LOTTO_NON_TROVATO_CIG("Lotto non trovato", AVCPassSemaforo.VERDE)
    , ERR_GARA_NON_TROVATA_PER_CODICE("Gara non trovata", AVCPassSemaforo.VERDE)
    ;
   
     private String descrizione;
     private AVCPassSemaforo semaforo;
      
     public String descrizione() {return descrizione;}
   
     AVCPassEsiti(String descrizione, AVCPassSemaforo semaforo){
       this.descrizione = descrizione;
       this.setSemaforo(semaforo);
     }
   
     public static AVCPassEsiti getEnumBycodice(String codInp) 
     {
        AVCPassEsiti[] lista = values();
       for(int i=0; i<lista.length;i++) {
          if (lista[i].name().equals(codInp))
             return lista[i];
       }
       return null;
     }
     
      public AVCPassSemaforo getSemaforo() {
         return semaforo;
      }
      public void setSemaforo(AVCPassSemaforo semaforo) {
         this.semaforo = semaforo;
      }
   }
   
   protected Connection connection;
	protected Logger logger;
	
	/******************************************************************************
	 * Action di base
	 * 
	 * @param activeConnection Connection
	 * @param logger Logger
	 */
	protected BaseSharedAction(Connection activeConnection, Logger logger){
		this.connection = activeConnection;
		this.logger = logger;
	}
	
	/****************************************************************************
	 * Ritorna un booleano a seconda se una data sia stata inserita o meno 
	 * @param year String
	 * @param month String
	 * @param day String
	 * @return boolean
	 */
//	protected boolean isSelected(String year,String month,String day){
//		if(!year.equals("")&&!month.equals("")&&!day.equals(""))
//			return true;
//		return false;
//	}
	
	/*****************************************************************************
	 * Gestisce la richeista di annullamento
	 * @param blocco String
	 * @param cfUtente String
	 * @param motivazione String 
	 * @param id_record String
	 * @param data_inizio_record Timestamp
	 * @param idLotto String
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean scriviAnnullamento(RichiestaAnnullamento bean) throws ActionException{
		AccessiDB accessiDB = new AccessiDB(connection,logger);
		try{
			return accessiDB.richiediAnnullamento(bean);
		}
		catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error("richiediAnnullamento: "+e.getMessage());
			throw new ActionException(e);
		}
	}
	
	//added un modo centralizzato per recuperare Aggiudicaizone che mi serve per tutte le validazioni 
	/***********************************************************************************************
	 * restituisce un aggiudicazione individuandola tramite Id e data di inizio
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @return AggiudicazioneBean
	 */
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg){
		AggiudicazioniManager aggMan = new AggiudicazioniManager(connection,logger);
		AggiudicazioneBean aggBea = null;
		try{
			aggBea = aggMan.getAggiudicazioni(idAggiudicazione, dataInizioAgg, false);
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}return aggBea;
	}
	//VL - [28 marz.]: added un modo centralizzato per recuperare InfoComuni che mi serve per tutte le validazioni 
	/************************************************************************************************
	 * Ottiene le infocomuni tramite id e data inizio 
	 * @param idInfoComuni long
	 * @param dataInizioInfo Timestamp
	 * @return InfoComuniBean
	 */
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo){
		InfoComuniManager infMan = new InfoComuniManager(connection,logger);
		InfoComuniBean infBea = null;
		try{
			infBea = infMan.load(idInfoComuni, dataInizioInfo);
		}catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
		return infBea;
	}
	
	
	//FG aggiunto per recuperare i dati della scheda avanzamenti dalla scheda conclusioni
	/*****************************************************************************************************
	 * Ottiene le informazioni degli avanzamenti associete all'aggiudicazione  
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List<AvanzamentoBean>
	 * @throws ActionException
	 */
	public List<AvanzamentoBean> getAvanzamenti(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException{
		List<AvanzamentoBean> avanzamenti = new ArrayList<AvanzamentoBean>();
		AvanzamentoManager am = new AvanzamentoManager(connection,logger);
		try{
			avanzamenti = am.loadMany(idAggiudicazione, dataInizioAggiudicazione);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
		return avanzamenti;
	}	
	
	
	//FG aggiunto per recuperare i dati della scheda InizioLavori per la scheda Conclusione
	//il metodo � utilizzato anche dalla scheda Sospensioni
	/*****************************************************************************************************
	 * Ottiene le informazioni di InizioLavori associete all'aggiudicazione  
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return InizioLavoriBean
	 * @throws ActionException
	 */
	public InizioLavoriBean getInizioLavori(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException{
		InizioLavoriBean inizioLavori = new InizioLavoriBean();
		InizioLavoriManager im = new InizioLavoriManager(connection,logger);
		try{
			inizioLavori = im.load(idAggiudicazione, dataInizioAggiudicazione);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
		return inizioLavori;
	}	
	
	//UN - [12 Genn.]: added in modo centralizzato per recuperare la tableBean per tutte le schede 
	/************************************************************************************************
	 * Recupera le informazioni sulle richieste di annullamento relative ad una particolare scheda 
	 * @param id_scheda String
	 * @param data_inizio String
	 * @param blocco String
	 * @return TableBean
	 */
	public TableBean getRichAnnByScheda(String id_scheda, String blocco, boolean cancellazioni)throws ActionException {
		TableBean tab = null;
		try{
			AnnullamentoManager aman = new AnnullamentoManager(connection, logger);
			tab = aman.getDettaglioRichAnnByScheda(id_scheda, blocco,  cancellazioni, false);
		}catch (SQLException e) {
			logger.error(e);
			throw new ActionException(e);
		}
		return tab;
	}
 
	/************************************************************************************************
	 * Recupera le informazioni sulle variazioni in corso d'opera relative ad una particolare scheda 
	 * @param id_scheda String
	 * @param data_inizio String
	 * @param blocco String
	 * @return TableBean
	 */
	public TableBean getVarAnagByScheda(String id_scheda, String blocco)throws ActionException {
		TableBean tab = null;
		try{
			AnnullamentoManager aman = new AnnullamentoManager(connection, logger);
			tab = aman.getDettaglioRichAnnByScheda(id_scheda, blocco,  false, true);
		}catch (SQLException e) {
			logger.error(e);
			throw new ActionException(e);
		}
		return tab;
	}

	/************************************************************************************************
	 * Recupera le informazioni sulle richieste di annullamento relative ad una particolare scheda 
	 * usta solo per il pannello dettaglio richieste
	 * @param id_scheda String
	 * @param data_inizio String
	 * @param blocco String
	 * @return TableBean
	 */
	public TableBean hasSchedaRichDelete(String id_scheda, String blocco, boolean cancellazioni)throws ActionException {
		TableBean tab = null;
		try{
			AnnullamentoManager aman = new AnnullamentoManager(connection, logger);
			tab = aman.hasSchedaRichDelete(id_scheda, blocco,  cancellazioni);
		}catch (SQLException e) {
			logger.error(e);
			throw new ActionException(e);
		}
		return tab;
	}

	/************************************************************************************************
	 * Verifica se per la scheda e' possibile effettuare una richiesta di cancellazione 
	 *
	 * @param blocco String
	 * @param idScheda long
	 * @param dataInizio Timestamp
	 * @param idStato long
	 * @param tipoSettore String
	 * @param tipoContratto String
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean isCancellabile(String blocco, long idScheda, Timestamp dataInizio, long idStato,  
								  String tipoSettore, String tipoContratto,
								  long idAggiudicazione, Timestamp dataInizioAgg)throws ActionException {
		boolean ret = false;

		try{
			AccessiDB accessiDB = new AccessiDB(connection,logger);

			if ((idStato == StatiScheda.CONFERMATO || idStato == StatiScheda.IN_DEFINIZIONE)
					&& (hasSchedaRichDelete(String.valueOf(idScheda), blocco, false).getFullSize() == 0
						&& hasSchedaRichDelete(String.valueOf(idScheda), blocco, true).getFullSize() == 0)
					&& !accessiDB.thereAreDependencies(blocco, idAggiudicazione, dataInizioAgg, tipoSettore, tipoContratto)
				){
				ret = true;
			}
		}catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}
		return ret;
	}
	
	  /*********************************************************************
    * Il metodo serve per verificare se la gara prevede lettera di invito oppure no.
    * Se almeno un lotto rientra nella casistica ritorna true cioè è possibile
    * inserire la lettera di invito, altrimenti essa non deve essere inserita.
    * 
    * @return  boolean
    */
   
   public boolean isInvitabile(Map<String,Lotto> mappaLotti){
      boolean invitabile = false;
      //gm controllo che il flag beni culturali sia stato valorizzato
      //if(pubblicazione!=null && pubblicazione.getFlag_benicult()!=null && Costanti.FLAG_VALORE_SI.equalsIgnoreCase(pubblicazione.getFlag_benicult())){
      //gm se la gara contiene dei lotti, si effettua la scansione dei lotti
          if(mappaLotti!=null && !mappaLotti.isEmpty()){
             Collection<Lotto> elencoLotti = mappaLotti.values();
             for(Lotto lotto : elencoLotti){
                //gm se ho almeno un lotto non cancellato, verifico che rientri nella casistica
                if(lotto.getDATA_CANCELLAZIONE_LOTTO()==null || "".equals(lotto.getDATA_CANCELLAZIONE_LOTTO())){
                   
                   long sceltaEquiv = Long.valueOf(lotto.getId_Scelta_Contraente());
                   
                   invitabile = invitabile || (sceltaEquiv == Costanti.PROC_NEG_NO_PP);
                }
             }
          }
      //}
       return invitabile;
   }
   
	/********************************************************************************************
	 * Carica le informazioni relative alle motivazioni VCO
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadMotiviVCO(Object o) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			return man.loadMotiviVCO(o);
		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}
    //TICKET ALM #2847
	protected boolean isBandoObbligatorio(String sceltaContraente, BigDecimal lottoImporto, String tipoContratto, long idGara){
      boolean bandoObbligatorio = false;
      try{
    
         String scelta = sceltaContraente;
         
//         // ricavo la scelta contraente equivalente
//         if(SimogFlags.is3028_RFWEBGL00Active()){
//            scelta = lm.getSceltaContraenteAVCP(null, Long.valueOf(sceltaContraente));
//         }

        int idSceltaContraente = Integer.parseInt(scelta);
        
        //TICKET ALM #2847
        GaraManager gm = new GaraManager(connection, logger);
        String strCreazioneGara = gm.getGara(idGara).getData_creazione();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long dataCreazioneGara = sdf.parse(strCreazioneGara).getTime();
        //Effettua il controllo con le nuove voci in caso di gara creata successivamente
        if(SimogFlags.is3042Active() && dataCreazioneGara >= SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
        	if(idSceltaContraente==Costanti.PROC_APE || 
        	   idSceltaContraente==Costanti.PROC_RIS || 
        	   idSceltaContraente==Costanti.DIA_COMP || 
        	   idSceltaContraente==Costanti.PROC_COMP_NEG || 
        	   idSceltaContraente==Costanti.PROC_NEG_SS){
        	           try{
        	                if(Costanti.TIPO_SCHEDA_LAVORI.equals(tipoContratto)){ 
        	                 if(lottoImporto != null && (lottoImporto.floatValue()>=Costanti.IMPORTO_LOTTO_500000 || lottoImporto.floatValue()==Costanti.IMPORTO_FUORI_SCALA))             
        	                    bandoObbligatorio = true;  
        	                }
        	            }
        	            catch (Exception e){
        	              bandoObbligatorio = bandoObbligatorio || false;
        	            }
        	          }
        } else {//Altrimenti effettua il controllo con le vecchie voci
          if(idSceltaContraente==Costanti.PROC_APE || idSceltaContraente==Costanti.PROC_RIS
           || idSceltaContraente==Costanti.DIA_COMP || idSceltaContraente==Costanti.PROC_NEG_PP){
           try{
                if(Costanti.TIPO_SCHEDA_LAVORI.equals(tipoContratto)){ 
                 if(lottoImporto != null && (lottoImporto.floatValue()>=Costanti.IMPORTO_LOTTO_500000 || lottoImporto.floatValue()==Costanti.IMPORTO_FUORI_SCALA))             
                    bandoObbligatorio = true;  
                }
            }
            catch (Exception e){
              bandoObbligatorio = bandoObbligatorio || false;
            }
          }
        } //FINE TICKET ALM #2847
      }
      catch(Exception e){
        bandoObbligatorio = bandoObbligatorio || false;
      }
      return bandoObbligatorio;
  }

	  /*********************************************************************
    * Il metodo serve per verificare se la gara è pubblicabile oppure perfezionabile.
    * Se almeno un lotto rientra nella casistica ritorna true cioè pubblicabile, 
    * altrimenti false cioè perfezionabile.
    * 
    * @return  boolean
    */
   
   public boolean isPubblicazione(Map<String,Lotto> mappaLotti){
      boolean pubblicabile = false;
      LottoManager lm = new LottoManager(connection, logger);
      
      //boolean allCancellati = true;
      //gm se la gara contiene dei lotti, si effettua la scansione dei lotti
      if(mappaLotti!=null && !mappaLotti.isEmpty()){
         Collection<Lotto> elencoLotti = mappaLotti.values();
         for(Lotto lotto : elencoLotti){
            //gm se ho almeno un lotto non cancellato, verifico che rientri nella casistica
            if(lotto.getDATA_CANCELLAZIONE_LOTTO()==null || "".equals(lotto.getDATA_CANCELLAZIONE_LOTTO())){
                pubblicabile = pubblicabile || lm.isPubblicabile(lotto.getId_Scelta_Contraente(), lotto.getImporto_Lotto(), lotto.getTIPO_CONTRATTO_LOTTO());
                 //allCancellati = false;
            }
         }
         //gm se tutti i lotti risultano cancellati, la gara è pubblicabile
         //if(allCancellati)
         // pubblicabile = true;
      }
      //gm altrimenti è una gara senza lotti quindi pubblicabile
        //else
      //    pubblicabile = true;
       return pubblicabile;
   }
   
   /***
    * Ritorna il bean Gara a partire dall'aggiudicazione
    * @param idAgg
    * @param dataIniAgg
    * @return Gara
    */
   public Gara getGaraByAgg(long idAgg, Timestamp dataIniAgg){
      Gara retVal = null;
      InfoComuniBean icb = null;
      AggiudicazioniManager aggMan = new AggiudicazioniManager(connection,logger);
      AggiudicazioneBean agg = null;
      try{
         agg = aggMan.getAggiudicazioni(idAgg, dataIniAgg, true);
      } catch (SQLException e) {
         logger.fatal(e.getMessage());
         //e.printStackTrace();
      }
      
      if(agg!=null)
         icb = getInfoComuni(agg.getIdInfo(), agg.getDataInizioInfo());
      
      if(icb!=null){
         LottoManager lm = new LottoManager(connection, logger);
            
         try {
            Lotto lotto = lm.getLotto(icb.getIdLotto());
            if(lm != null){
               GaraManager gm = new GaraManager(connection, logger);
               retVal = gm.getGara(lotto.getId_Gara());
            }

         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
      }
      return retVal;
   }
   
   
   public InfoGaraBean getInfoGaraBeanByLotto(long idLotto) throws ActionException{
      try{
         return new InfoComuniManager(connection, logger).loadInfoGara(idLotto);
      }catch (SQLException e){
         e.printStackTrace();
         throw new ActionException(e);
      }
   }
}
