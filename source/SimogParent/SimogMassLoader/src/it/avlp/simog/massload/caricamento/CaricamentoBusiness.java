package it.avlp.simog.massload.caricamento;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.accordo.AccordoManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.FinanziamentoManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.r129.R129Manager;
import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.CodiciCup;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.action.AggiudicatarioAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.generated.PRESTAZIONI_PER_CATEGORIA;
import it.avlp.simog.garamanager.lotto.LottoManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe da usare in fase di caricamento delle schede gia esistenti per un flusso.
 * Non e' previsto qui il caricamento per idSimog e idLocale
 * 
 * @author vletizia
 *
 */
public class CaricamentoBusiness {

	private Connection con;
	private Logger logger;
	
	
	
	/**
	 * Classe di business da usare in fase di caricamento delle schede gia esistenti per un flusso.
	 * Non e' previsto qui il caricamento per idLocale.
	 * 
	 * @param con
	 * @param logger
	 */
	public CaricamentoBusiness(Connection con, Logger logger) {
		this.con = con;
		this.logger = logger;
	}

	/**
	 * Carica gli infoComuni comprensivi del bean di pubblicazione
	 * 
	 * @param CIG
	 * @return
	 * @throws Exception
	 */
	public InfoComuniBean caricaDatiComuni(String CIG) throws Exception{
		InfoComuniManager manager = new InfoComuniManager(con, logger);
		InfoComuniBean infobean = manager.getInfoComuniByCig(CIG);
		PubblicazioneManager pmanager = new PubblicazioneManager(con, logger);
		PubblicazioneBean pubbean = pmanager.loadByIdSimog(infobean.getIdInfo());
		infobean.setPubblicazione(pubbean);
		return infobean;
	}
	public InfoComuniBean caricaDatiComuni(long idInfo) throws Exception{
		InfoComuniManager manager = new InfoComuniManager(con, logger);
		InfoComuniBean infobean = manager.loadByIdSimog(idInfo);
		PubblicazioneManager pmanager = new PubblicazioneManager(con, logger);
		PubblicazioneBean pubbean = pmanager.loadByIdSimog(infobean.getIdInfo());
		infobean.setPubblicazione(pubbean);
		return infobean;
	}
	
	/**
	 * @param CUI
	 * @return
	 * @throws Exception
	 */
	public AggiudicazioneBean caricaAggiudicazione(String CUI) throws Exception{
		
		AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
		// non ci interessa perche abbiamo controllato preventivamente eventuali stato incongrui (in definizione e via dicendo)
		return am.getAggiudicazioneByProgAndCui(CUI, false);

	}
	
	/**
	 * @param CIG
	 * @return
	 * @throws Exception
	 */
	public List<AggiudicazioneBean> caricaAggiudicazioniByCIG(String CIG) throws Exception{
		
		AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
		// non ci interessa perche abbiamo controllato preventivamente eventuali stato incongrui (in definizione e via dicendo)
		return am.getAggiudicazioniByCIG(CIG);

	}
	
	/**
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	public AggiudicazioneBean caricaAggiudicazione(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws Exception{
		
		AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
		return am.getAggiudicazioni(idAggiudicazione, dataInizioAggiudicazione, false);

	}
	public AggiudicazioneBean caricaAggiudicazione(long idAggiudicazione) throws Exception{
		
		AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
		return am.loadByIdSimog(idAggiudicazione);

	}
	
	public AggiudicazioneBean caricaAdesione(long idAggiudicazione) throws Exception{
		
		return caricaAggiudicazione(idAggiudicazione);

	}
	
	public AggiudicazioneBean caricaSottosoglia(long idAggiudicazione) throws Exception{
		
		return caricaAggiudicazione(idAggiudicazione);

	}
	
	public AggiudicazioneBean caricaEscluso(long idAggiudicazione) throws Exception{
		
		return caricaAggiudicazione(idAggiudicazione);

	}
	public List<AggiudicatarioBean> caricaAggiudicatari(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws Exception{
		
		AggiudicatarioManager am = new AggiudicatarioManager(con, logger);
		return am.loadMany(idAggiudicazione, dataInizioAggiudicazione, false);
	}
	public List<AccordoBean> caricaAccordi(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{
		
		AccordoManager am = new AccordoManager(con, logger);
		return am.loadMany(idAggiudicazione, dataInizioAgg);
		
	}
	
	public List<AvanzamentoBean> caricaAvanzamenti(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws Exception{

		AvanzamentoManager am = new AvanzamentoManager(con, logger);
		return am.loadMany(idAggiudicazione, dataInizioAggiudicazione);
		
	}
	
	public int getNextAvanzamento(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws Exception{
		AvanzamentoManager am = new AvanzamentoManager(con, logger);
		return am.getNextAvanzamento(idAggiudicazione, dataInizioAggiudicazione);
	}
	
	public CollaudoBean caricaCollaudo(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{
		
		CollaudoManager cm = new CollaudoManager(con, logger);
		return cm.load(idAggiudicazione, dataInizioAgg);
		
	}
	
	public ConclusioneBean caricaConclusione(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{
		
		ConclusioniManager cm = new ConclusioniManager(con, logger);
		return cm.load(idAggiudicazione, dataInizioAgg);
		
	}
	public InizioLavoriBean caricaInizioLavori(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws Exception {

		InizioLavoriManager ilm = new InizioLavoriManager(con, logger);
		return ilm.load(idAggiudicazione, dataInizioAggiudicazione);

	}
	
	public StipulaBean caricaStipula(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws Exception {

		StipulaManager ilm = new StipulaManager(con, logger);
		return ilm.load(idAggiudicazione, dataInizioAggiudicazione);

	}
	
	public List<R129Bean> caricaRitardi(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{

		R129Manager rm = new R129Manager(con, logger);
		return rm.loadMany(idAggiudicazione, dataInizioAgg);
		
	}
	
	public List<SospensioniBean> caricaSospensioni(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{
		
		SospensioniManager sm = new SospensioniManager(con, logger);
		return sm.loadMany(idAggiudicazione, dataInizioAgg);
	}
	
	public List<SubappaltiBean> caricaSubAppalti(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{

		SubappaltiManager sm = new SubappaltiManager(con, logger);
		return sm.loadMany(idAggiudicazione, dataInizioAgg);
	}
	
	public List<VarianteBean> caricaVarianti(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{

		VarianteManager vm = new VarianteManager(con, logger);
		return vm.loadMany(idAggiudicazione, dataInizioAgg);
	}
	
	public List<TipoFinanziamentoBean> caricaFinanziamenti(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{

		FinanziamentoManager fm = new FinanziamentoManager(con, logger);
		return fm.loadMany(idAggiudicazione, dataInizioAgg, false);
	}
	
	public List<CondizioneAggBean> caricaCondizioni(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{

		CondizioniManager cm = new CondizioniManager(con, logger);
		return cm.loadMany(idAggiudicazione, dataInizioAgg, false);
	}
	
	public List<RequisitiBean> caricaRequisiti(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{

		RequisitiManager rm = new RequisitiManager(con, logger);
		return rm.loadMany(idAggiudicazione, dataInizioAgg, false);
	}
	
	public List<TipoAppaltoAggBean> caricaTipiLavoro(long idAggiudicazione, Timestamp dataInizioAgg, String tipoEnte) throws Exception{

		TipoAppaltoManager tam = new TipoAppaltoManager(con, logger);
		return tam.loadManyL(idAggiudicazione, dataInizioAgg, tipoEnte, false);
	}
	public List<TipoAppaltoAggBean> caricaTipiFS(long idAggiudicazione, Timestamp dataInizioAgg, String tipoEnte) throws Exception{

		TipoAppaltoManager tam = new TipoAppaltoManager(con, logger);
		return tam.loadManyFS(idAggiudicazione, dataInizioAgg, tipoEnte, false);
	}

  public List<TipoAppaltoAggBean> caricaTipilotto(long idLotto, String tipoEnte, String tipoScheda) throws Exception{

	      TipoAppaltoManager tam = new TipoAppaltoManager(con, logger);
	      return tam.loadMany(idLotto, tipoScheda, tipoEnte, false);
	   }

   public List<CupLottoAggExt> caricaElencoCup(long idAggiudicazione, Timestamp dataInizioAgg) throws Exception{

      CupLottoAggManager rm = new CupLottoAggManager(con, logger);
      return rm.getElencoCup(null, idAggiudicazione, dataInizioAgg, false);
   }
	
   public List<CupLottoAggExt> caricaElencoCup(long idLotto) throws Exception{

      CupLottoAggManager rm = new CupLottoAggManager(con, logger);
      return rm.getElencoCup(idLotto, null, null, false);
   }

   public String caricaFlagCup(long idLotto) throws Exception{
      LottoManager lm = new LottoManager(con, logger); 
      Lotto lotto = lm.getLotto(idLotto);
      
      return lotto.getFLAG_CUP();
   }

   public List<AggiudicatarioBean> getAggiudicatariAQ(InfoGaraBean info) {
      List<AggiudicatarioBean> ret = new ArrayList<AggiudicatarioBean>();
      
      LottoManager lm = new LottoManager(con, logger); 
      try {
         List<Lotto> lotto = lm.getLottoByCigWS(info.getCIG_ACC_QUADRO());
         
         if(lotto != null && lotto.size() > 0){
            List <AggiudicazioneBean> aggiudicazioni = new ArrayList<AggiudicazioneBean>();
            AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();
            AggiudicazioniManager am = new AggiudicazioniManager(con,logger);
            InfoComuniBean icb = new InfoComuniBean();
            InfoComuniManager icm = new InfoComuniManager(con,logger);
            try{
               icb = icm.getInfoComuniByCig(lotto.get(0).getFullCIG());
            }
            catch (Exception e){
               logger.error("Impossibile ottenere l'InfoComuniBean dell'accordo quadro", e);
            }
            
            try{
               aggiudicazioni = am.getAggiudicazioniList(icb.getIdInfo(), icb.getDataInizioInfo());
            }
            catch (Exception e){
               logger.error("Impossibile ottenere la lista di aggiudicazioni dell'accordo quadro", e);
            }
            for(AggiudicazioneBean agg : aggiudicazioni){
               if(agg.getIdStato()==StatiScheda.CONFERMATO){
                  try{
                     aggiudicazioneAccQuadro = am.getAggiudicazioni(agg.getIdAggiudicazione(), agg.getDataInizioAggiudicazione(), false);
                     // Ticket#2015050810000322 PP prendo l'ultima che trovo   break;
                       //aggiudicazioneAccQuadro = aggiudicazioni.get(0);
                  }
                  catch (Exception e){
                     logger.error("Impossibile ottenere l'aggiudicazione dell'accordo quadro", e);
                  }
               }
            }
            // alla fine gli aggiudicatari
            if (aggiudicazioneAccQuadro.getIdAggiudicazione()>0){
               // PP va usata la action! AggiudicatarioManager am = new AggiudicatarioManager(connection,logger);
               AggiudicatarioAction aa = new AggiudicatarioAction(con, logger);
               
               try{
                  //PP va usata la action!  aggiudicatariAccQuadro = am.loadMany(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
                  ret = aa.loadMany(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
               }
               catch (Exception e){
                  logger.error("Impossibile ottenere gli aggiudicatari dell'accordo quadro", e);
               }     
            }
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return ret;
   }

}
