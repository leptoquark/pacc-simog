package it.avlp.simog.validatore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.luogo.IstatManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.TIPO_AMBITO_LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;

public class IniziativaValidator extends SimogValidator {

	public String errors;
	
	public IniziativaValidator(Connection connection, Logger logger) {
		super(connection, logger);
		errors = "";
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			IniziativaSoggAggr iniziativa = (IniziativaSoggAggr)bean;
			valida(iniziativa);
			boolean noErr = mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0;
			if(noErr)
				return true;
			else {
				for(int i=0;i<mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize();i++) {
					errors +=  mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).get(i).getMessage();
				}

			}
		}return false;
	}

	private void valida(IniziativaSoggAggr iniziativa) {
		//Controlli obbligatarieta'
		if(iniziativa.getIdGara()==0)
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Numero Gara"));
		else if(iniziativa.getCIG()==null || "".equals(iniziativa.getCIG()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "CIG"));
		else if(iniziativa.getStatoIniziativa()==null || "".equals(iniziativa.getStatoIniziativa()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Stato Iniziativa")+" correttamente");
		else if(iniziativa.getCIG().length()!=10) //Controlli formato parametri
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007.replace("$1", "la lunghezza non e' corretta"));
		else if(iniziativa.getListaCatIniziativa().isEmpty())
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_245);
		else if(iniziativa.getListaTerritoriIniziativa().isEmpty())
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_246);
		else if(iniziativa.getAmbitoLotto().isEmpty())
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_246a);
		else {//Controlli dei dati gara su Simog
			GaraManager gm = new GaraManager(connection,logger);
			try {
				Gara gara = gm.getGara(iniziativa.getIdGara());
				if(gara==null)
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_248);
				else if(!SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL()) && !SimogFlags.isSvolgimentoAccordoQuadro(gara.getID_SVOLGIMENTO()))
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_249);
				else {
					//Controlli dei dati lotto su Simog
					//Verifica esistenza CIG nella gara indicata
					LottoManager lm = new LottoManager(connection,logger);
					List<Lotto> listaLotti = lm.getListaCIGByIdGara(iniziativa.getIdGara());
					boolean cigFound=false;
					for(Lotto lotto : listaLotti)
					{
						if(iniziativa.getCIG().equals(lotto.getCIG()+lotto.getCIG_kkk())) {
							cigFound=true;
							break;
						}
					}
					if(!cigFound)
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_250);
					
					//Verifica correttezza territori
					List<String> listaTerritori = iniziativa.getListaTerritoriIniziativa();
					IstatManager im = new IstatManager(connection,logger);
					for(String terr : listaTerritori) {
						if(!im.isRegioneValid(terr))
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_251.replace("$1", terr));
					}
					
					//Verifica correttezza categorie merceologiche DPCM
					List<String> listaCategorie = iniziativa.getListaCatIniziativa();
					String[] catArray = new String[listaCategorie.size()];
					for(int i=0;i<listaCategorie.size();i++) 
						catArray[i] = listaCategorie.get(i);
				    validaCategorie(catArray,PageHelper.getCurrentDate());
						
					//Verifica correttezza ambiti lotto
				    List<String> ambitiLotto = iniziativa.getAmbitoLotto();
				    String[] arrayAmbiti = new String[ambitiLotto.size()];
				    for(int i=0;i<ambitiLotto.size();i++)
				    	arrayAmbiti[i] = ambitiLotto.get(i);
				    validaAmbitiLotto(arrayAmbiti, PageHelper.getCurrentDate());
				}
			} catch (SQLException e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_MASSLOADER_205);
				e.printStackTrace();
			} catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_MASSLOADER_205);
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}
	   /**
	    * Metodo che si occupa di validare tutte le categorie ovvero controlla l'esistenza 
	    * di tutti gli id contenuti nella mappa
	    * 
	    * @return boolean
	    */
	   public boolean validaCategorie(String[] categorie, Object data){
	      int local_error = 0;
	      boolean valida = true;
	      for (int i = 0; i < categorie.length; i++) {
	         valida = validaTipologica(EAGG_CATEGORIE.TABLE_NAME, EAGG_CATEGORIE.COD_CATEGORIA, EAGG_CATEGORIE.DESCRIZIONE, EAGG_CATEGORIE.DATA_INIZIO_VALIDITA, EAGG_CATEGORIE.DATA_FINE_VALIDITA, PageHelper.parseTimeYMD((String) data), categorie[i]);
	         if(!valida){ 
	            mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Categoria merceologica"), i);
	            local_error++;
	         }
	      }
	      return local_error == 0;
	   }
	   
	   public boolean validaAmbitiLotto(String[] ambiti, Object data) {
		   int local_error = 0;
		      boolean valida = true;
		      for (int i = 0; i < ambiti.length; i++) {
		         valida = validaTipologica(TIPO_AMBITO_LOTTO.TABLE_NAME, TIPO_AMBITO_LOTTO.COD_AMBITO_LOTTO, TIPO_AMBITO_LOTTO.DESCRIZIONE, TIPO_AMBITO_LOTTO.DATA_INIZIO, TIPO_AMBITO_LOTTO.DATA_FINE, PageHelper.parseTimeYMD((String) data), ambiti[i]);
		         if(!valida){ 
		            mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Categoria merceologica"), i);
		            local_error++;
		         }
		      }
		      return local_error == 0;
	   }
	   
	   public String getErrors() {
		   return errors;
	   }
}
