package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.DittaAusiliariaManager;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DittaAusiliariaAction extends BaseSharedAction {
	public static String CLAZZ = "DittaAusiliariaAction";
	public DittaAusiliariaAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}
	
	public void saveList (AggiudicatarioBean agg)throws ActionException  {
		String mtd = "saveList";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection, logger);
		List<DittaAusiliariaBean> ditteAux = agg.getDitteAusiliarie();
		if(ditteAux!=null && !ditteAux.isEmpty()){
    		try{
        		//dittaAusiliariaManager.deleteDitteAusiliarieByAggiudicatario(idAggiudicatario, dataInizioAggiudicatario);
	        	for(DittaAusiliariaBean dittaAusiliaria:ditteAux){
	    	    	dittaAusiliaria.setIdAggiudicatario(agg.getIdAggiudicatario());
	    		    dittaAusiliaria.setDataInizioAggiudicatario(agg.getDataInizioAggiudicatario());
	    		    dittaAusiliaria.setIdAggiudicazione(agg.getIdAggiudicazione());
	    		    dittaAusiliaria.setDataInizioAggiudicazione(agg.getDataInizioAggiudicazione());
		    	    dittaAusiliariaManager.update(dittaAusiliaria, false);
	    	    }
	        }
		    catch (Exception e) {
//			    log come fatal demandato al chiamante
			    logger.error(logPrefix, e);
			    throw new ActionException(e);
		    }
		}
	}
	/*
	public void save(long idAggiudicatario, Timestamp dataInizioAggiudicatario, DittaAusiliariaBean dittaAusiliaria)throws ActionException  {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		try{
		    DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection, logger);
		    dittaAusiliaria.setIdAggiudicatario(idAggiudicatario);
	    	dittaAusiliaria.setDataInizioAggiudicatario(dataInizioAggiudicatario);	  
		    dittaAusiliariaManager.update(dittaAusiliaria, false);
	    }
	    catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
	}
	*/
    public void confirmList (AggiudicatarioBean agg)throws ActionException  {
    	String mtd = "confirmList";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection, logger);
		List<DittaAusiliariaBean> ditteAux = agg.getDitteAusiliarie();
		if(ditteAux!=null && !ditteAux.isEmpty()){
    		try{
        		//dittaAusiliariaManager.deleteDitteAusiliarieByAggiudicatario(idAggiudicatario, dataInizioAggiudicatario);
	        	for(DittaAusiliariaBean dittaAusiliaria:ditteAux){
	    	    	dittaAusiliaria.setIdAggiudicatario(agg.getIdAggiudicatario());
	    		    dittaAusiliaria.setDataInizioAggiudicatario(agg.getDataInizioAggiudicatario());
	    		    dittaAusiliaria.setIdAggiudicazione(agg.getIdAggiudicazione());
	    		    dittaAusiliaria.setDataInizioAggiudicazione(agg.getDataInizioAggiudicazione());
		    	    dittaAusiliariaManager.update(dittaAusiliaria, true);
	    	    }
	        }
		    catch (Exception e) {
//			    log come fatal demandato al chiamante
			    logger.error(logPrefix, e);
			    throw new ActionException(e);
		    }
		}
	}
    /*
    public void confirm(long idAggiudicatario, Timestamp dataInizioAggiudicatario, DittaAusiliariaBean dittaAusiliaria)throws ActionException  {
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		try{
	    	DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection, logger);
		    dittaAusiliaria.setIdAggiudicatario(idAggiudicatario);
	    	dittaAusiliaria.setDataInizioAggiudicatario(dataInizioAggiudicatario);	  
		    dittaAusiliariaManager.update(dittaAusiliaria, true);
	    }
	    catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
	}
    */
    public void deleteListByAggiudicazione (long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws ActionException  {
		String mtd = "deleteListByAggiudicazione";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection, logger);
		try{
    		dittaAusiliariaManager.deleteDitteAusiliarieByAggiudicazione(idAggiudicazione, dataInizioAggiudicazione);
	    }
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
	}
    
    /****************************************************************************************************
	 * Crea la ditteAusiliarieString dell'<code>AggiudicatarioBean</code> inserendo i valori relativi
	 * alla lista List<DittaAusiliariaBean> passata in input
	 * @param List<DittaAusiliariaBean>
	 * @return String ditteAusiliarieString
	 */
	public String creaDitteAusiliarieString(long idAggiudicatario, Timestamp dataInizioAggiudicatario, boolean ignoraStato)throws ActionException  {
		String mtd = "creaDitteAusiliarieString";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		String ditteAusiliarieString = "";
        DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection, logger);
        List<DittaAusiliariaBean> listaDitte = new ArrayList<DittaAusiliariaBean>();
        try{
        	listaDitte = dittaAusiliariaManager.loadMany(idAggiudicatario, dataInizioAggiudicatario, ignoraStato);
	    }
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		if(listaDitte!=null && !listaDitte.isEmpty()){
			String record = "";
			for(DittaAusiliariaBean dittaBean : listaDitte){
				record = record + 
				dittaBean.getSoggettoPartecipante().getDenominazione() + "|" +
				dittaBean.getSoggettoPartecipante().getCodiceFiscale() + "|" +
				dittaBean.getSoggettoPartecipante().getId_stato() + "|" +
				dittaBean.getFlagAvvalimento() + "|" +
				String.valueOf(dittaBean.getSoggettoPartecipante().getIdSoggettoPartecipante()) + "|" +
				PageHelper.formatTimeStamp(dittaBean.getSoggettoPartecipante().getDataInizioSogg()) + "|";
				ditteAusiliarieString = ditteAusiliarieString + record + "~";
				record = "";
			}
		}	
        return ditteAusiliarieString;
	}
	
	/****************************************************************************************************
	 * Ottiene la ditteAusiliarieStringRetrocompatibile dell'<code>AggiudicatarioBean</code>
	 * passando in input la ditteAusiliarieString ed aggiungendovi nel modo corretto il cf_ausiliaria
	 * dell'aggiudicatario, se questo è presente 
	 * @param String ditteAusiliarieString
	 * @return String ditteAusiliarieStringRetrocompatibile
	 */
    public String getDitteAusiliarieStringRetrocompatibile (String ditteAusiliarieString, String cf_ausiliaria, String flag_avvalimento) throws ActionException{
    	String ditteAusiliarieStringRetrocompatibile = ditteAusiliarieString;
    	String temp = "";
		//il formato della ditteAusiliarieString è: denominazione|codice_fiscale|id_stato|flag_avvalimento|id_soggetto_partecipante|data_inizio_sogg|~ 
	   	//a questo valore verrà aggiunta un'altra stringa con i valori del soggetto partecipante trovato
		//con cf_ausiliaria, se il soggetto non è stato trovato viene inserito solo cf_ausiliaria e flag_avvalimento    	
    	if(cf_ausiliaria!=null && !"".equals(cf_ausiliaria)){
    		if(flag_avvalimento==null || "".equals(flag_avvalimento))
				flag_avvalimento = "";
    		DittaAusiliariaManager ditMan = new DittaAusiliariaManager(connection, logger);
			try{
				SoggettoPartecipanteBean soggTemp = ditMan.loadSoggettoPartecipanteByCF(cf_ausiliaria);
				//se non ho trovato il soggetto inserisco solo i dati di cf_ausiliaria e flag_avvalimento
				if(soggTemp==null || soggTemp.getIdSoggettoPartecipante()==0 || soggTemp.getDataInizioSogg()==null)
					temp = "|"+cf_ausiliaria+"||"+flag_avvalimento+"|||~";
				//altrimenti aggiorno con i dati del soggetto la ditteAusiliarieStringRetrocompatibile
				else{
					temp = soggTemp.getDenominazione()+"|"+soggTemp.getCodiceFiscale()+"|"+soggTemp.getId_stato()+"|"+
					    flag_avvalimento+"|"+String.valueOf(soggTemp.getIdSoggettoPartecipante())+"|"+soggTemp.getDataInizioSogg()+"|~";		
			    }
			}
			catch(Exception e){
				logger.error(e);
				throw new ActionException(e);
			}
         	ditteAusiliarieStringRetrocompatibile = ditteAusiliarieStringRetrocompatibile+temp;
    	}
    	return ditteAusiliarieStringRetrocompatibile;
	}
	
	
	/****************************************************************************************************
	 * Crea la lista delle ditte ausiliarie dell'<code>AggiudicatarioBean</code> inserendo i valori relativi
	 * alla stringa ditteAusiliarieString passate in input
	 * @param String ditteAusiliarieString
	 * @return List<DittaAusiliariaBean>
	 */
	public List<DittaAusiliariaBean> creaListaDitteAusiliarie(String ditteAusiliarieString, long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws ActionException  {
		//array dei parametri attesi nel ditteAusiliarieString
		String [] parametriAttesi ={PSBD.FIELD_NAME_AGG_DENOMINAZIONE,PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO,PSBD.FIELD_NAME_AGG_ID_PAESE,
				PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO,PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE,PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG
				, PSBD.FIELD_NAME_ANAGOE};
		List<DittaAusiliariaBean> ditteAusiliarie = new ArrayList<DittaAusiliariaBean>();
		if(ditteAusiliarieString!=null && !"".equals(ditteAusiliarieString)){
			//ogni elemento dell'array sarà una ditta ausiliaria
			String [] ditteAux = ditteAusiliarieString.split("~");
			for(int i=0; i<ditteAux.length; i++){
				//ogni elemento dell'array sarà un campo della ditta ausiliaria
				String [] dittaAux = ditteAux[i].split("\\|");
				if(dittaAux!=null){
					DittaAusiliariaBean dittaBean = new DittaAusiliariaBean();
					SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
					for(int j=0; j<dittaAux.length && j<parametriAttesi.length; j++){	    
					    if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_DENOMINAZIONE)){
					    	sogg.setDenominazione(dittaAux[j]);
					    }
					    else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO)){
					    	sogg.setCodiceFiscale(dittaAux[j]);
					    }
					    else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_ID_PAESE)){
					    	sogg.setId_stato(dittaAux[j]);
					    }
					    else if(parametriAttesi[j].equals(PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO)){ 
					        String flagAvv=dittaAux[j];
					        //se il flag dovesse arrivare decodificato, in questo modo lo riconverto in varchar(1)
					        //altrimenti lo lascio inalterato
						    if(dittaAux[j].equals(PSBD.REQUISITI_FLAG_AVVALIMENTO))
							    flagAvv = "1";
						    else if(dittaAux[j].equals(PSBD.ATTTESTAZIONE_FLAG_AVVALIMENTO))
							    flagAvv="2";
						    else if(dittaAux[j].equals(PSBD.ENTRAMBI_FLAG_AVVALIMENTO))
							    flagAvv="3";
						    else if(dittaAux[j].equals(PSBD.NESSUNO_FLAG_AVVALIMENTO))
							    flagAvv="0";
						    dittaBean.setFlagAvvalimento(flagAvv);
					    }
					    else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE)){ 
					    	sogg.setIdSoggettoPartecipante(Long.parseLong(dittaAux[j]));
					    }	
						else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG)){ 
							sogg.setDataInizioSogg(PageHelper.parseTime(dittaAux[j]));
						}else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_ANAGOE)){ 
							//sogg.setDataInizioSogg(PageHelper.parseTime(dittaAux[j]));
				    		// PP se esistono variazioni anagrafiche le memorizzo
				    		String datiAnag = dittaAux[j];
				    		if (datiAnag != null && !"".equals(datiAnag) && !"*".equals(datiAnag)){
				    							    			
				    			String [] val = Base64Coder.decodeString(datiAnag).split(PSBD.SEP_VARANAG_S,-1);
				    			
				    			sogg.setIdSoggettoPartecipante(Long.parseLong(val[0]));
				    			sogg.setCodiceFiscale(val[1]);
				    			sogg.setDenominazione(val[2]);
				    			sogg.setCameraCommercio(val[3]);
				    			sogg.setPartitaIva(val[4]);
				    			sogg.setIndirizzo(val[5]);
				    			sogg.setCivico(val[6]);
				    			sogg.setCitta(val[7]);
				    			sogg.setProvincia(val[8]);
				    			sogg.setCap(val[9]);
				    			sogg.setCfRappresentante(val[10]);
				    			sogg.setCognome(val[11]);
				    			sogg.setNome(val[12]);
				    			sogg.setId_stato(val[13]);
				    			
				    			// setto il flag per indicare la successiva modifica
				    			sogg.setModifica(true);
				    		}
						}
				    }
					dittaBean.setSoggettoPartecipante(sogg);			
    				if(idAggiudicazione!=0){
	    				dittaBean.setIdAggiudicazione(idAggiudicazione);
		    		}
			    	if(dataInizioAggiudicazione!=null){
				    	dittaBean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				    }
					ditteAusiliarie.add(dittaBean);
				}
			}
		}	
		return ditteAusiliarie;
	}
	
	/*************************************************************************************************************
	 * Gestisce la richiesta di annullamento effettuando la storicizzazione del bean. 
	 * 
	 * @param bean
	 * @param datavecchia
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean, AggiudicatarioBean agg, Timestamp datavecchia) throws ActionException {
		DittaAusiliariaManager ditMan = new DittaAusiliariaManager(connection, logger);
		try {		
			return ditMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), agg, datavecchia);
		} catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}		
	}	
}
	