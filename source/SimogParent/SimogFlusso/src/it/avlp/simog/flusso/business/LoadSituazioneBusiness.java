package it.avlp.simog.flusso.business;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.flusso.bean.CrossedFields;
import it.avlp.simog.flusso.business.impl.LoadSituazioneByIdLocale;
import it.avlp.simog.flusso.business.impl.LoadSituazioneByIdScheda;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe di business che raggruppa tutti i tipi di caricamento delle situazioni
 * 
 * @author vletizia
 *
 */
public class LoadSituazioneBusiness {

	private Connection con;
	private Logger logger;
	private OrigineSchedaEnum origine;
	
	public LoadSituazioneBusiness(Connection con, Logger logger, OrigineSchedaEnum origine) {
		super();
		this.con = con;
		this.logger = logger;
		this.origine = origine;
	}
	/**
	 * Carica La situazione attuale del flusso di schede in base ad un cig
	 * NOTA: che il caricamento carica anche le schede in definizione (che comprende quelle "in richiesta")
	 * 
	 * @param CIG
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<SituazioneSchedeAttuale> loadSituazioneByCIG(String CIG) throws SQLException,Exception{
		LoadSituazioneByIdScheda loader = new LoadSituazioneByIdScheda(con, logger);
		return loader.loadSituazioneByCIG(CIG);
	}
	/**
	 *  Carica La situazione attuale del flusso di schede in base ad un cui
	 *  NOTA: che il caricamento carica anche le schede in definizione (che comprende quelle "in richiesta")
	 *  
	 * @param CUI
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public SituazioneSchedeAttuale loadSituazioneByCUI(String CUI) throws SQLException,Exception{
		LoadSituazioneByIdScheda loader = new LoadSituazioneByIdScheda(con, logger);
		return loader.loadSituazioneByCUI(CUI);
	}
	
	/**
	 *  Carica La situazione attuale del flusso di schede in base a riferimenti simog
	 *  NOTA: che il caricamento carica anche le schede in definizione (che comprende quelle "in richiesta")
	 *  
	 * @param identificativo
	 * @param cui
	 * @param idScheda
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public SituazioneSchedeAttuale loadSituazioneAttualeByIdSimog(IdentificativoSchede identificativo, String cui, String idScheda) throws SQLException, Exception{
		LoadSituazioneByIdScheda loader = new LoadSituazioneByIdScheda(con, logger);
		return loader.loadSchedaByRefs(identificativo, cui, idScheda);
	}
	/**
	 *  Carica La situazione attuale del flusso di schede in base ad un idLocale + i riferimenti simog per renderlo univoco
	 *  NOTA: che il caricamento carica anche le schede in definizione (che comprende quelle "in richiesta")
	 *  
	 * @param identificativo
	 * @param CIG
	 * @param CUI
	 * @param idAggiudicazione
	 * @param idLocale
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public SituazioneSchedeAttuale loadSituazioneAttualeByIdLocale(IdentificativoSchede identificativo, String CIG, String CUI,String idAggiudicazione, String idLocale) throws SQLException, Exception{
		LoadSituazioneByIdLocale loaderByIdLocale = new LoadSituazioneByIdLocale(con, logger);
		AggiudicazioniManager aggManager = new AggiudicazioniManager(con, logger);
		AggiudicazioneBean aggBean = aggManager.getAggiudicazioneByProgAndCui(CUI, false);
		return loaderByIdLocale.loadSchedaByIdLocale(identificativo, CIG, CUI, String.valueOf(aggBean.getIdAggiudicazione()), idLocale);
	}
	
	/**
	 * Serve a caricare i campi cross scheda, il loro contenitore sara' lo stato scheda (campi specifici infocomuni)
	 * <strong>NOTA:</strong> uso per recuperare il lotto getLottoByCigWS che ritorna una lista di lotti ordinati in modo decrescente
	 * 	per cig cicle, uso solo il primo elemento della lista !
	 * @param cig
	 * @return CrossedFields contenitore campi, puo essere vuoto qualora non si siano trovati lotti per il cig
	 * @throws SQLException
	 * @throws Exception
	 */
	public CrossedFields loadCrossedFieldsForInfoComuni(String cig, String CUI) throws SQLException, Exception{


		CrossedFields crossFields = new CrossedFields();
		LottoManager lottoManager = new LottoManager(con, logger);
		// PATCH - VL - correzione dell'invocazione multipla del metodo getLottoByWs..
		List<Lotto> listaDiLotti = lottoManager.getLottoByCigWS(cig);
		
		Lotto lotto = listaDiLotti != null && listaDiLotti.size() > 0 
						? listaDiLotti.get(0) : null;		
		if(lotto != null){
		   
         //is3028_RFWEBSC00Active
		   crossFields.setLuogoIstat(lotto.getLUOGO_ISTAT());
         crossFields.setLuogoNuts(lotto.getLUOGO_NUTS());
         crossFields.setIdSceltaContraente(Long.valueOf(lotto.getId_Scelta_Contraente()));
         
         GaraManager garaManager = new GaraManager(con, logger);
         Gara gara = garaManager.getGara(lotto.getId_Gara());

// PP spostato nel chiamante per poter fare il ciclo sui cig         
//         //verifica blocco AVCPASS
//         if(SimogFlags.is3028_RFWEBGL07Active()){
//            // controllo solo se non è avcpass che sta mandando i dati
//            if (this.origine.code() != OrigineSchedaEnum.AVCPASS.code()){
//               GaraLottoManager glm = new GaraLottoManager(con, logger);
//               if(glm.isAVCPass(null, listaDiLotti, AVCPassFunzioneEnum.ML_SCHEDA_AGGIUNTIVE_UPDATE.getCodice())){
//                  throw new CigException( cig, CUI, 1, new Exception(Messaggi.SIMOG_AVCPASS_001));
//               }
//            }
//         }
//         
			crossFields.setID_ESCLUSIONE(lotto.getID_ESCLUSIONE());
			crossFields.setFLAG_ESCLUSO(lotto.getFLAG_ESCLUSO());
			crossFields.setIdLotto(lotto.getId_Lotto());
			crossFields.setID_MODO_REAL(gara.getID_MODO_REAL());
			
			//.. [6]
			crossFields.setCfAmministrazione(gara.getCF_AMMINISTRAZIONE());
			crossFields.setDenAmministrazione(gara.getDENOM_AMMINISTRAZIONE());
			crossFields.setCfStazioneAppaltante(gara.getCF_AMMINISTRAZIONE());
			crossFields.setDenStazioneAppaltante(gara.getDENOM_AMMINISTRAZIONE());
			
			crossFields.setCodiceCC(gara.getID_STAZIONE_APPALTANTE());
			crossFields.setDenomCC(gara.getDENOM_STAZIONE_APPALTANTE());
			
			crossFields.setTipoSettore(lotto.getTIPO_CONTRATTO_LOTTO());
			crossFields.setFlagOrdinario(gara.getTIPO_SCHEDA_GARA());
			
			// BANDI - PP - aggiunti campi pubblicazione
			PubblicazioneManager pubMan = new PubblicazioneManager(con, logger );
			if(gara.getIdPubblicazione() > 0){
				PubblicazioneBean pubBean = pubMan.getPubblicazione(gara.getIdPubblicazione(), gara.getDataInizioPubblicazione());
				crossFields.setPubblicazione(pubBean);
			}
		}
// se non trovo il cig lo mando avanti tanto poi c'è un altro controllo gestito meglio		
//		else
//			throw new CigException( cig, CUI, 1, new Exception(Messaggi.SIMOG_VALIDAZIONE_008));
		
		return crossFields;
	}
	
}
