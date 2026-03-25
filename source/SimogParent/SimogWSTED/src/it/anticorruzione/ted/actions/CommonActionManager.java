package it.anticorruzione.ted.actions;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.exception.TEDErrorException;
import it.anticorruzione.ted.json.TEDNoticeInformation;
import it.anticorruzione.ted.rest.ITEDConnection;
import it.anticorruzione.ted.rest.TEDConnection;
import it.anticorruzione.ted.service.ITEDDbService;
import it.anticorruzione.ted.service.impl.TEDDbService;
import it.anticorruzione.ted.util.UtilityClass;
import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.beans.DataNotice;

public class CommonActionManager {
	final static String statusSE = "SERVICE_ERROR";
	final static String tedErr = "TED_ERROR";
	ITEDDbService tedDb = new TEDDbService();
	ITEDConnection conn = new TEDConnection();
	
	private boolean sbloccaGara = false;
	private boolean pubblicaGara = false;
	
	public String noDocExtValidation(String no_doc_ext) {

		//B1 - verifica obbligatorietà e formato no_doc_ext
		if(no_doc_ext==null || "".equals(no_doc_ext)) {
			return statusSE+"_000 - no_doc_ext: Il campo è obbligatorio";
		} else if(!no_doc_ext.matches("(20\\d{2}\\-\\d{6})")) {
			return statusSE+"_000 - no_doc_ext: Formato non valido (richiesto yyyy-xxxxxx)";
		} 
		
		return null;
	}


	public String noDocOjsValidation(String no_doc_ojs, TypeNoticeEnum typeNotice, long idLotto, long idGara) {
		
		if(no_doc_ojs==null || "".equals(no_doc_ojs)) {
			return "SERVICE_ERROR_P08 - no_doc_ojs: Il campo è obbligatorio";
		} else if(!no_doc_ojs.matches("(19|20)\\d{2}/S \\d{3}-\\d{6}")) {
			return "SERVICE_ERROR_P08 - no_doc_ojs: Formato non valido (richiesto yyyy/S xxx-xxxxxx)";
		} else { 
			TEDNotice ojsNotice = tedDb.findNoticeByNoDocOjsByLotto(no_doc_ojs,typeNotice,idLotto);
			if(ojsNotice==null) {
				ojsNotice = tedDb.findNoticeByNoDocOjsByGara(no_doc_ojs,typeNotice,idGara);
			if(ojsNotice==null) {
				String ris = "SERVICE_ERROR_P08 - no_doc_ojs: Il numero di pubblicazone del formulario è inesistente";
				if(idLotto!=0)
					ris+=" o non è associato al CIG indicato\n";
				return ris;
				}
			}
		}
		return "";
	}

	public String garaValidation(String id_gara) {


		if(id_gara==null)
			return statusSE+"_000 - Numero gara: il campo e' obbligatorio";
		else if(!PageHelper.isNumeric(id_gara))
			return statusSE+"_000 - Numero gara: formato non valido";
		
		return null;
	}
	
	protected String createNoDocExt() {
		return tedDb.createNoDocExt();
	}
	
	public ResponseMessageTED saveAndSendToTED(DataNotice dataNotice, 
														String noticeXml,
														TypeNoticeEnum typeNotice, Lotto lotto) {
		

		ResponseMessageTED ris = new ResponseMessageTED();
		String xml  = UtilityClass.trimma(noticeXml);
		long idlotto =  0;
		if(lotto!=null)
			idlotto=lotto.getId_Lotto();
		
		TEDNotice newNotice = tedDb.insertTEDNotice(dataNotice.getNoDocExt(),dataNotice.getGara().getId_Gara(),idlotto,typeNotice);
		if(newNotice!=null) {
			  boolean res = tedDb.insertSubmit(xml, newNotice.getIdTedNotice());
			  if(!res) {
				  //TODO fai scattare eccezione?
			  }
		}
		
	    TEDNoticeInformation res;
		try {
			res = conn.submit(UtilityClass.encodeString(xml));
			
		    boolean esito =  tedDb.insertTEDStatus(res,newNotice.getIdTedNotice());
		    if(esito) {
		    	
		    	//Converti la stringa in data
//		    	Date gmtDate = UtilityClass.stringTEDtoDate(res.getReceived_at());
//		    	Date cestDate = UtilityClass.convertGmtToCest(gmtDate);
		    	Date receive = new Date();
		    	
		    	//Aggiorna notice con il submission id
		    	newNotice.setSubmissionId(res.getSubmission_id());
		    	newNotice.setReceivedAt(receive);
		    	tedDb.updateTEDNotice(newNotice);
		    	
				ris.setSuccess(true);
				ris.setStatus(res.getStatus());
				ris.setStatus_msg("Il formulario e' stato ricevuto e preso in carico dal TED");
				ris.setNo_doc_ext(dataNotice.getNoDocExt());
				ris.setData_received(UtilityClass.dateToString(receive));
				return ris;
		    } 
		} catch (TEDErrorException e) {
			e.printStackTrace();
			ris.setSuccess(false);
			ris.setStatus("TED_ERROR");
			ris.setStatus_msg(e.getStatusMessage());
			return ris;
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		return ris;
	}
	
	public synchronized boolean checkPreviousPublication(TEDNotice notice) throws Exception {
		
		if(notice!=null) {
			TEDStatus lastStatus = tedDb.getLastTEDStatus(notice.getIdTedNotice());
			if(lastStatus!=null) {
				StatusNoticeEnum currentStatus = lastStatus.getStatusNoticeEnum();
				if(currentStatus.equals(StatusNoticeEnum.IN_PROGRESS) || currentStatus.equals(StatusNoticeEnum.RECEIVED)) {
					
					TEDNoticeInformation newStatus = conn.getNotice(notice.getSubmissionId());
					tedDb.insertTEDStatus(newStatus, lastStatus.getIdTedNotice());
					if(newStatus.getStatus().equals(StatusNoticeEnum.IN_PROGRESS.getStrStatus()) || 
							newStatus.getStatus().equals(StatusNoticeEnum.RECEIVED.getStrStatus())) {
						if(conn.stopPublication(notice.getSubmissionId()))
						   tedDb.cancelPublication(notice.getNoDocExt(),"Cancellato a favore di nuovo formulario");
					}
				} else if(currentStatus.equals(StatusNoticeEnum.PUBLISHED))
					return false;
			}
		}
		
		return true;
		
	}
	
	public void sbloccaGaraLotto(long idGara, Connection connection, Logger logger) {
		try{
      		SimogProperties config = SimogProperties.getInstance();
      		PubblicazioneManager pubblicazione = new PubblicazioneManager(connection, logger);
      		pubblicazione.sbloccaPubblicazione(idGara);
      		
      		GaraManager garaManager= new GaraManager(connection, logger);
      		LottoManager lottoManager = new LottoManager(connection, logger);
      		
      		lottoManager.sbloccaLotto(idGara);
      		garaManager.sbloccaGara(idGara);
      		
      		Gara gara = garaManager.getGara(idGara);
      		
            if (config.isCUPLotto(gara.getData_creazione())){
               TipoAppaltoManager tam = new TipoAppaltoManager(connection, logger);
               tam.sbloccaTipiAppalto(gara.getId_Gara());
            }
           
            if (config.isCUPLotto(gara.getData_creazione())){
               CupLottoAggManager cam = new CupLottoAggManager(connection, logger);
               cam.sbloccaCup(gara.getId_Gara());
            }
      		
      		if(config.getDataRequisiti().compareTo(PageHelper.getCurrentDate())<=0){
      			RequisitiGLManager requisitiGLManager = new RequisitiGLManager(connection, logger);
      			
      			RequisitiGLAction rqa = new RequisitiGLAction(connection, logger);
      			
      			boolean revocaLogica = true; // Revoca sempre logica
      			
      			// revoca dei requisiti automatici "AR"
      			if( !revocaLogica )
      			   requisitiGLManager.deleteDocumentiByTipoUso(idGara, RequisitoGara.TIPO_USO_AR);
      			requisitiGLManager.revocaRequisitiGaraByGaraAndTipoUso(idGara, RequisitoGara.TIPO_USO_AR, revocaLogica);
      
                   // carico tutti i requisiti esistenti, compresi i documenti
                   List<RequisitoGara> lista = rqa.getRequisitoGaraList(idGara);
                  
                   // revoco i requisiti esistenti
                   rqa.revocaRequisitiByGara(idGara, revocaLogica);
                  
                   // inserisco di nuovo i requisiti, uso il manager perchè l'action fa la validazione
                   requisitiGLManager.insertRequisitiGara(lista, idGara);
                  
                   // inserisco i documenti associati
                   requisitiGLManager.insertDocumentiRequisito(lista, idGara);
                  
                   // duplico i riferimenti anche ai record non master
                      rqa.insertDocumentiNonMaster(lista, idGara);
      		}
      		
      	}catch(Exception e){
      		e.printStackTrace();
      		logger.error(e.getMessage());
      	}		
	}
	
	public boolean isSbloccaGara() {
		return sbloccaGara;
	}


	
	public boolean isPubblicaGara() {
		return pubblicaGara;
	}


	public void setSbloccaGara(boolean sbloccaGara) {
		this.sbloccaGara = sbloccaGara;
	}


	public void setPubblicaGara(boolean pubblicaGara) {
		this.pubblicaGara = pubblicaGara;
	}
	
}
