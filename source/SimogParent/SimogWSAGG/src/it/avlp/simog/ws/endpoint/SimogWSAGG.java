package it.avlp.simog.ws.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import it.anticorruzione.simog.ws.util.IniziativaConverter;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.actions.LoginActionManager;
import it.avlp.simog.common.beans.ResponseInserisciGara;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.validatore.IniziativaValidator;
import it.avlp.simog.ws.beans.ResponseComunicaIniziativa;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.massload.xmlbeans.CategoriaType;
import it.avlp.simog.ws.massload.xmlbeans.GaraWS;
import it.avlp.simog.ws.massload.xmlbeans.IniziativaType;
import it.avlp.simog.ws.massload.xmlbeans.LuogoIstatType;

@WebService(targetNamespace = "xmlbeans.massload.simog.avlp.it")
public class SimogWSAGG {
	public OutputStream os;
	
	    //TICKET ALM - 3.04.4
		@WebMethod
		public ResponseComunicaIniziativa comunicaIniziativa(@WebParam(name = "iniziativa") IniziativaType iniziativa ) {

			
			Connection con = null;
			Logger logger = null;
			ConnectionWSManager cwsm = null;
			logger = LoggerManager.getInstance().getLogger();
			logger.info("-----------	begin  	---------------");
			ResponseComunicaIniziativa ris = new ResponseComunicaIniziativa();
			WSSessionManager wsm = null;
			WsSessions wss = new WsSessions();
			
			try {
//				JAXBElement<GaraWS> gt = gof.createGara(datiGara);
				JAXBContext jaxbContext = JAXBContext.newInstance(IniziativaType.class);
				Marshaller m = jaxbContext.createMarshaller();
				os = new ByteArrayOutputStream();
				m.marshal(iniziativa, os);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			
			
			try {
			    cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
				wsm = new WSSessionManager(logger,cwsm);
				con = cwsm.getConnection();
				String msgReturn = "";
				IniziativaSoggAggr iniziativaBean = IniziativaConverter.convertiIniziativa(iniziativa);
				IniziativaValidator validator = new IniziativaValidator(con, logger);
				
				if(validator.valida(iniziativaBean, null)) {
					IniziativaManager im = new IniziativaManager(con,logger);
					//Verifica se l'iniziativa sia gia' presente nel db
					long idIniziativa = im.checkIniziativa(iniziativaBean.getCIG());
					if(idIniziativa==0) {
						//Inserisci iniziativa
						 idIniziativa = im.insertIniziativa(iniziativaBean);
						 msgReturn = Messaggi.SIMOG_VALIDAZIONE_258;
					} else {
						iniziativaBean.setIdIniziativa(idIniziativa);
						
						//Aggiorna iniziativa
						im.updateIniziativa(iniziativaBean);
						
						//Aggiorna ambiti lotto
						im.deleteAmbitiLotto(idIniziativa);
						im.deleteTerritoriIniziativa(idIniziativa);
						im.deleteCategorieIniziativa(idIniziativa);
						msgReturn = Messaggi.SIMOG_VALIDAZIONE_259;
					}
					
					
					//inserisci ambiti lotto
					for(String idAmbito : iniziativaBean.getAmbitoLotto()) {
						long idAmbitoLong = im.selectAmbitoLottoByCod(idAmbito);
					   im.insertAmbitoLotto(idIniziativa, idAmbitoLong);
					}
					//inserisci territori iniziativa
					for(String regione : iniziativaBean.getListaTerritoriIniziativa())
						im.insertTerritorioIniziativa(idIniziativa, regione);
					
					//inserisci categorie iniziativa
					for(String cat : iniziativaBean.getListaCatIniziativa())
						im.insertCategoriaIniziativa(idIniziativa, Long.parseLong(cat));
					
					ris.success=true;
					ris.setError(msgReturn);
				} else {
					IniziativaManager im = new IniziativaManager(con,logger);
					im.insertErr(iniziativaBean.getIdGara(),iniziativaBean.getCIG(),iniziativaBean.getDescrizioneSoggAggr(),validator.getErrors());
					ris.success=false;
					ris.setError(validator.getErrors());
				}
					
		
		
			}catch(Exception e) {
				e.printStackTrace();
				ris.success=false;
				//3.04.8
				ris.setError(Messaggi.SIMOG_MASSLOADER_205 + e);
			} finally{
				if(cwsm != null){
					cwsm.closeConnection();
				}
			}

			return ris;
		} 

		
}
