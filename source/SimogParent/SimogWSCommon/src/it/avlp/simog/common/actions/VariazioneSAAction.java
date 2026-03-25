package it.avlp.simog.common.actions;

import it.avcp.anagrafe.AnagrafeWS.AnagrafeWSClient;
import it.avlp.simog.beans.Amministrazione;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.common.action.BaseSharedAction;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.SimogProperties;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;

public class VariazioneSAAction extends BaseSharedAction {

    private SimogProperties config;

	public VariazioneSAAction(Connection activeConnection, Logger logger, SimogProperties conf) {
		super(activeConnection, logger);
		this.config = conf;
	}
	
	public List<StazioneAppaltante> getSAList(Amministrazione amm, String url, String user, String pwd) throws Exception{
		List<StazioneAppaltante> saList = new ArrayList<StazioneAppaltante>();
		//StazioneAppaltante sa = new StazioneAppaltante();
		
		try {
			
			AnagrafeWSClient cli = new AnagrafeWSClient(new URL(url), user, pwd, logger);
			saList = cli.getListaSA(amm);
			
		} catch (AxisFault e) {
			logger.error(e.getFaultString());
			throw new AxisFault (e.getFaultString());
			
		} catch (Exception e) {
			logger.error(e);
			logger.error(e.getCause());
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
		
		/*
		sa.setAmministrazione(amm);
		sa.setIdUfficio("1");
		sa.setDenominazione("denominazione1");
		saList.add(sa);
		
		sa = new StazioneAppaltante();
		sa.setAmministrazione(amm);
		sa.setIdUfficio("2");
		sa.setDenominazione("denominazione2");
		saList.add(sa);
		
		sa = new StazioneAppaltante();
		sa.setAmministrazione(amm);
		sa.setIdUfficio("3");
		sa.setDenominazione("denominazione3");
		saList.add(sa);
		
		sa = new StazioneAppaltante();
		sa.setAmministrazione(amm);
		sa.setIdUfficio("4");
		sa.setDenominazione("denominazione4");
		saList.add(sa);
		
		sa = new StazioneAppaltante();
		sa.setAmministrazione(amm);
		sa.setIdUfficio("5");
		sa.setDenominazione("denominazione5");
		saList.add(sa);
		*/
		//get the list
		
		return saList;
	}

	public boolean doVariazione(Long idGara, Long idMotivo,
			StazioneAppaltante sa) throws ActionException {
		GaraManager gMan = new GaraManager(connection, logger);
		
		
		try {
           
		   Gara gara = gMan.getGara(idGara);
           boolean prevOOCC = gMan.isOrganoCost(gara.getCF_AMMINISTRAZIONE(), null);

           gMan.updateStazioneAppaltante(idGara,idMotivo,sa);
		        
		    if(SimogFlags.isGRIGLIA_CONTRIBUTO()){
		       if(prevOOCC != gMan.isOrganoCost(sa.getCodiceFiscaleAmministrazione(), null)){
		           gara = gMan.getGara(idGara); // rileggo con i nuovi dati
		          // devo ricalcolare i contributi di tutti i lotti e della gara
      	           LottoManager lm = new LottoManager(connection, logger);
      	           Map<String, Lotto> lotti = lm.getMappaLotti(gara.getId_Gara());
      	           GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient (config.getContributoUrl(), connection, logger);
      	           gcwbc.ricalcola(gara, lotti);
      		           
      	           // devo aggiornare i lotti, per i quali dovrebbe essere stato ricalcolato il contributo
      	           for (Iterator<Lotto> iterator = lotti.values().iterator(); iterator.hasNext();) {
      	             Lotto elem = iterator.next();
      	             lm.modificaLotto(elem);
      	          }
      		           
      	           gMan.saveGara(gara);
	            }
	        }

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new ActionException(e);
		}
		
		
		return true;
	}
}
