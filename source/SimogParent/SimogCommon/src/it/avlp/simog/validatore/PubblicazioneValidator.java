package it.avlp.simog.validatore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.util.SimogProperties;

public class PubblicazioneValidator extends SimogValidator  {

	public PubblicazioneValidator(Connection connection, Logger logger) {
		super(connection, logger);	
	}
	
	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			PubblicazioneBean pubblicazione = (PubblicazioneBean)bean;
			valida(pubblicazione, section);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0;
		}
		return false;
	}
	
	private void valida(PubblicazioneBean pubblicazione, String section){
		
	}
	
	/**********************************************************************************************
	 * Validatore per i file allegati di pubblicazione
	 * <ul>
	 *  <li>String allBando
	 *  <li>String allDisci
	 *  <li>String allInvito
	 * </ul>
	 * @param String allBando, String allDisci, String allInvito, boolean pubblicabile, boolean invitabile
	 */
	public void validaAllegati(String allBando, String allDisci, String allInvito, boolean pubblicabile,
			boolean invitabile, boolean bandoObbligatorio, boolean procCompleta) {
		
       // controllo allegati per procedura completa (pubblicazione fase 1 e fase2)
       if( procCompleta ){
          //controllo che l'allegato bando di gara sia obbligatorio o facoltativo
          if(bandoObbligatorio){
              //se è obbligatorio il messaggio è di errore
              if(isEmpty(allBando)){
					mEccezioni.addValidationField("label_Bando");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara")
							.replace("$2", "l'inserimento dell'allegato"));
              }
          }
          //se non è obbligatorio il messaggio è di warning
          else{
              if(isEmpty(allBando)){
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara")
							.replace("$2", "l'inserimento dell'allegato"));
              }
          }
          if(isEmpty(allDisci)){
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Disciplinare").replace("$2",
						"l'inserimento dell'allegato"));
          }
          if(isEmpty(allInvito)){
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_216.replace("$1",
						"prevede la pubblicazione della lettera di invito"));
         }        
       }
       //controllo allegati per gara pubblicabile
       else if(pubblicabile){
			//controllo che l'allegato bando di gara sia obbligatorio o facoltativo
			if(bandoObbligatorio){
				//se è obbligatorio il messaggio è di errore
    	    	if(isEmpty(allBando)){
					mEccezioni.addValidationField("label_Bando");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara")
							.replace("$2", "l'inserimento dell'allegato"));
		        }
			}
			//se non è obbligatorio il messaggio è di warning
			else{
				if(isEmpty(allBando)){
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara")
							.replace("$2", "l'inserimento dell'allegato"));
		        }
			}
            if(isEmpty(allDisci)){
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Disciplinare").replace("$2",
						"l'inserimento dell'allegato"));
		    }
            if(!isEmpty(allInvito)){
				mEccezioni.addValidationField("label_SceltaContraente");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_216.replace("$1",
						"non prevede la pubblicazione della lettera di invito"));
			}
		}	
		//controllo allegati per gara invitabile
		else if(invitabile){
        	if(!isEmpty(allBando)){
				mEccezioni.addValidationField("label_Bando");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Bando di Gara"));		
			}
	        if(!isEmpty(allDisci)){
				mEccezioni.addValidationField("label_Disciplinare");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Disciplinare"));	
			}
	        if(isEmpty(allInvito)){
				mEccezioni.addValidationField("label_Invito");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_216.replace("$1",
						"prevede la pubblicazione della lettera di invito"));
		    }
        }
        //controllo allegati per gara non pubblicabile nè invitabile
		else{
			if(!isEmpty(allBando)){
				mEccezioni.addValidationField("label_Bando");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Bando di Gara"));		
			}
	        if(!isEmpty(allDisci)){
				mEccezioni.addValidationField("label_Disciplinare");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Disciplinare"));	
			}
        	if(!isEmpty(allInvito)){
				mEccezioni.addValidationField("label_Invito");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_216.replace("$1",
						"non prevede la pubblicazione della lettera di invito"));
			}
        }
	}
	
	/**********************************************************************************************
	 * Validatore per il file allegato di rettifica
	 * <ul>
	 *  <li>String allRettifica
	 * </ul>
	 * @param String allRettifica
	 */
	public void validaAllegatiRettifica(String allRettifica, String noteRettifica){
		if(isEmpty(allRettifica)){
			mEccezioni.addValidationField("label_Rettifica");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Rettifica").replace("$2",
					"l'inserimento dell'allegato"));
	    }
		if(isEmpty(noteRettifica)){
			mEccezioni.addValidationField("label_NoteRettifica");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Note della Rettifica")
					.replace("$2", "l'inserimento delle note all'allegato"));
		}
	}
	
	/**********************************************************************************************
	 * Validatore per il file allegato di avviso aggiudicazione
	 * <ul>
	 *  <li>String allAvviso
	 * </ul>
	 * @param String allAvviso
	 */
	public void validaAllegatiAvviso(String allAvviso){
		if(isEmpty(allAvviso)){
			mEccezioni.addValidationField("label_AvvisoAggiudicazione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Avviso di Aggiudicazione")
					.replace("$2", "l'inserimento dell'allegato"));
	    }
	}
	
	/**********************************************************************************************
	 * Validatore per gli allegati provenienti dai WS
	 * <ul>
	 *  <li>list <AllegatoBean> allegati
	 *  <li>String tipoOperazione
	 * </ul>
	 * @param list <AllegatoBean>
	 * @param String tipoOperazione
	 */
	public void validaAllegatiWS(ArrayList <AllegatoBean> allegati, String tipoOperazione, boolean isBandoObbligatorio){
		//mi aspetto uno e un solo solo bando, 0..1 disciplinare e nient'altro
		if(ParametriServlet.PUBBLICAZIONE_BANDO_GARA.equals(tipoOperazione)){
			int bando = 0;
			int disciplinare = 0;
			int altro = 0;
			for(AllegatoBean allegato : allegati){
				if(PubblicazioneBean.TipoDocumento.BANDO.getCodice().equals(allegato.getTipoDoc()))
					bando ++;
				else if(PubblicazioneBean.TipoDocumento.DISCIPLINARE.getCodice().equals(allegato.getTipoDoc()))
					disciplinare ++;
				else
					altro ++;		
			}
			if(isBandoObbligatorio){
    			if(bando<1)
            		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara").replace("$2", "l'inserimento dell'allegato"));
			}
    		if(bando>1)
           		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara").replace("$2", "l'inserimento di un solo allegato"));
	   		if(disciplinare>1)
           		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Disciplinare").replace("$2", "l'inserimento di un solo allegato"));
            if(altro>0)
          		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "tipo di alcuni allegati inseriti"));        		
	    }
		if(ParametriServlet.PUBBLICAZIONE_LETT_INV.equals(tipoOperazione)){
			int letteraInvito = 0;
			int altro = 0;
			for(AllegatoBean allegato : allegati){
				if(PubblicazioneBean.TipoDocumento.LETTERA_INVITO.getCodice().equals(allegato.getTipoDoc()))
					letteraInvito ++;
				else
					altro ++;	
			}
			if(letteraInvito<1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Lettera d'invito").replace("$2", "l'inserimento dell'allegato"));
			if(letteraInvito>1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Lettera d'invito").replace("$2", "l'inserimento di un solo allegato"));
            if(altro>0)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "tipo di alcuni allegati inseriti"));        	
		}
		if(ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoOperazione)){
			int avviso = 0;
			int altro = 0;
			for(AllegatoBean allegato : allegati){
				if(PubblicazioneBean.TipoDocumento.AVVISO.getCodice().equals(allegato.getTipoDoc()))
					avviso ++;
				else
					altro ++;	
			}
			if(avviso<1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Avviso di Aggiudicazione").replace("$2", "l'inserimento dell'allegato"));
			if(avviso>1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Avviso di Aggiudicazione").replace("$2", "l'inserimento di un solo allegato"));
            if(altro>0)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "tipo di alcuni allegati inseriti"));        
		}
		if(ParametriServlet.PUBBLICAZIONE_RETTIFICA.equals(tipoOperazione)){
			int rettifica = 0;
			int altro = 0;
			for(AllegatoBean allegato : allegati){
				if(PubblicazioneBean.TipoDocumento.RETTIFICA.getCodice().equals(allegato.getTipoDoc()))
					rettifica ++;
				else
					altro ++;	
			}
			if(rettifica<1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Rettifica").replace("$2", "l'inserimento dell'allegato"));
			if(rettifica>1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Rettifica").replace("$2", "l'inserimento di un solo allegato"));
            if(altro>0)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "tipo di alcuni allegati inseriti"));        
	
		}
		if(ParametriServlet.PUBBLICAZIONE_RETTIFICA_AVVISO_AGG.equals(tipoOperazione)){
			int rettificaAvv = 0;
			int altro = 0;
			for(AllegatoBean allegato : allegati){
				if(PubblicazioneBean.TipoDocumento.RETTIFICAAVVISO.getCodice().equals(allegato.getTipoDoc()))
					rettificaAvv ++;
				else
					altro ++;	
			}
			if(rettificaAvv<1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Rettifica Avviso").replace("$2", "l'inserimento dell'allegato"));
			if(rettificaAvv>1)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Rettifica Avviso").replace("$2", "l'inserimento di un solo allegato"));
            if(altro>0)
        		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "tipo di alcuni allegati inseriti"));        
		}
		if(ParametriServlet.PUBBLICAZIONE_PROCEDURA_RISTRETTA_COMPLETA.equals(tipoOperazione)){
           int bando = 0;
           int disciplinare = 0;
           int letteraInvito = 0;
           int altro = 0;
           for(AllegatoBean allegato : allegati){
               if(PubblicazioneBean.TipoDocumento.BANDO.getCodice().equals(allegato.getTipoDoc()))
                   bando ++;
               else if(PubblicazioneBean.TipoDocumento.DISCIPLINARE.getCodice().equals(allegato.getTipoDoc()))
                   disciplinare ++;
               else if(PubblicazioneBean.TipoDocumento.LETTERA_INVITO.getCodice().equals(allegato.getTipoDoc()))
                  letteraInvito ++;
               else
                   altro ++;       
           }
           if(isBandoObbligatorio){
               if(bando<1)
                   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara").replace("$2", "l'inserimento dell'allegato"));
           }
           if(bando>1)
               mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Bando di Gara").replace("$2", "l'inserimento di un solo allegato"));
           if(disciplinare>1)
               mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Disciplinare").replace("$2", "l'inserimento di un solo allegato"));
           if(letteraInvito<1)
              mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Lettera d'invito").replace("$2", "l'inserimento dell'allegato"));
          if(letteraInvito>1)
              mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Lettera d'invito").replace("$2", "l'inserimento di un solo allegato"));
           if(altro>0)
               mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "tipo di alcuni allegati inseriti"));
		}
		
	}
	
	/**********************************************************************************************
	 * Validatore per la Pubblicazione avanzata, effettua il controllo sui campi
	 * <ul>
	 *  <li>Data GUCE
	 *  <li>Data GURI
	 *  <li>Data BORE
	 *  <li>Numero GUCE
	 *  <li>Numero GURI
	 *  <li>Numero BORE
	 *  <li>Link sito committente
	 * </ul>
	 * @param pubblicazione PubblicazioneBean
	 */
	//gm nuovo metodo per la pubblicazione bando 3.0
	public void validaPubblicazione(PubblicazioneBean pubblicazione, String tipoPubblicazione, String tipoOperazione){
		
		//controlli comuni per tutti		
		if (!isEmpty(pubblicazione.getDataGuce()) && isEmpty(pubblicazione.getNumeroGuce())) {
			mEccezioni.addValidationField("label_DataGUCE");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Numero GUCE").replace("$2", "Data GUCE"));
		}
		if (isEmpty(pubblicazione.getDataGuce()) && !isEmpty(pubblicazione.getNumeroGuce())) {
			mEccezioni.addValidationField("label_NumeroGUCE");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Data GUCE").replace("$2", "Numero GUCE"));
		}

		if (!isEmpty(pubblicazione.getDataBore()) && isEmpty(pubblicazione.getNumeroBore())) {
			mEccezioni.addValidationField("label_DataBORE");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Numero BORE").replace("$2", "Data BORE"));
		}
		if (isEmpty(pubblicazione.getDataBore()) && !isEmpty(pubblicazione.getNumeroBore())) {
			mEccezioni.addValidationField("label_NumeroBORE");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Data BORE").replace("$2", "Numero BORE"));
		}

		if (!isEmpty(pubblicazione.getDataGuri()) && isEmpty(pubblicazione.getNumeroGuri())) {
			mEccezioni.addValidationField("label_DataGURI");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Numero GURI").replace("$2", "Data GURI"));
		}
		if (isEmpty(pubblicazione.getDataGuri()) && !isEmpty(pubblicazione.getNumeroGuri())) {
			mEccezioni.addValidationField("label_NumeroGURI");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Data GURI").replace("$2", "Numero GURI"));
		}
		/*
		if(isYFlag(pubblicazione.getProfiloCommitente())&&isEmpty(pubblicazione.getLinkSitoCommittente()))
    		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Link Sito Committente").replace("$2", "Profilo del committente"));	
    	if(!isYFlag(pubblicazione.getProfiloCommitente())&&!isEmpty(pubblicazione.getLinkSitoCommittente()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Profilo del committente").replace("$2", "Link Sito Committente"));
        */	
		//link sito committente
		if(!isEmpty(pubblicazione.getLinkSitoCommittente())){
			try { 
			    URL url = new URL(pubblicazione.getLinkSitoCommittente().replace("\\", "/")); 
			    java.net.URLConnection conn = url.openConnection(); 
			    conn.connect(); 
			} catch (MalformedURLException e) { 
			      mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Link Sito Committente"));
			} catch (IOException e) { 
			      mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Link Sito Committente"));
			} 
		}
		
		if(ParametriServlet.SRV_GESTIONE_RETTIFICA.equals(tipoOperazione)){
			if(//ParametriServlet.PUBBLICAZIONE_BANDO_GARA.equals(tipoPubblicazione) ||
			    //ParametriServlet.PUBBLICAZIONE_LETT_INV.equals(tipoPubblicazione) ||
			   	ParametriServlet.PUBBLICAZIONE_RETTIFICA.equals(tipoPubblicazione)){
				// gm nuovo codice estensione pubblicazione bandi, vale per tutte le rettifiche
				// escluse quelle di avvisi aggiudicazione
				if (!isFlag(pubblicazione.getFlag_sospeso())) {
					mEccezioni.addValidationField("label_PubblicazioneRettifica");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
							"La pubblicazione della rettifica comporta modifica dei dati di gara e lotto"));
				}
	        }
	    }
		
		// questo controllo non vale per gli avvisi di aggiudicazione in
		// pubblicazione/perfezionamento
		//TICKET ALM #3922-06.1
	   // if(ParametriServlet.SRV_BANDO_GARA.equals(tipoOperazione)){
		 if(ParametriServlet.SRV_BANDO_GARA.equals(tipoOperazione) && !SimogFlags.is3042Active()){
	    	if (!ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione)){
				// gm nuovo codice estensione pubblicazione bandi, non vale per rettifica e
				// avvisi aggiudicazione
	            if(!isFlag(pubblicazione.getFlag_benicult())){
					mEccezioni.addValidationField("label_ProcedurNegoziata");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
							"Procedura negoziata ex art. 204 comma 1 D.Lgs. 163/2006"));
	            }
	    	}
	    }
	    /*
		if(ParametriServlet.PUBBLICAZIONE_BANDO_GARA.equals(tipoPubblicazione) ||
		    ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione) ||
		    ParametriServlet.SRV_GESTIONE_RETTIFICA.equals(tipoOperazione)){
	
		    //numero GURI obbligatorio
		    if(isEmpty(pubblicazione.getNumeroGuri())){
			    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "numero GURI"));		
		    }
		    //data GURI obbligatoria
		    if(isEmpty(pubblicazione.getDataGuri())){
		      	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Data GURI"));		
		    }
		    //flag sito committente obbligatorio
		    
		    if(!isYFlag(pubblicazione.getProfiloCommitente())){
			    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_210.replace("$1", "Profilo del committente").replace("$2", "SI"));			
		    }
		    
		    //link sito committente obbligatorio
		    if(isEmpty(pubblicazione.getLinkSitoCommittente())){
		        mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Link Sito Committente"));
		    }
		}
		
		if(ParametriServlet.PUBBLICAZIONE_LETT_INV.equals(tipoPubblicazione)){
	     	if(!isNFlag(pubblicazione.getSitoOsservatorioCP()))
	    		mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_210.replace("$1", "Sito Informatico Osservatorio").replace("$2", "NO"));			
	    }
	    */ 
	}
	
	/**********************************************************************************************
	 * Validatore senza la Pubblicazione, controlla che i campi di pubblicazione
	 * siano tutti vuoti
	 *
	 * @param pubblicazione PubblicazioneBean
	 */
	public void validaSenzaPubblicazione(PubblicazioneBean pubblicazione){
		if (!isEmptyPubblicazione(pubblicazione)) {
			mEccezioni.addValidationField("label_Pubblicita'Appalto");
			mEccezioni.addValidationErr(Messaggi.SIMOG_GARA_022);
	}
	}
	//gm fine nuovo metodo per la pubblicazione bando 3.0
	
	//gm nuovo metodo per la pubblicazione bando 3.02
	public boolean isEmptyPubblicazione (PubblicazioneBean pubblicazione){
		boolean result;
		if(pubblicazione!=null){
    		if(!isEmptyOrZero(pubblicazione.getDataGuce())||
	    			!isEmpty(pubblicazione.getDataGuri()) ||
		    		!isEmpty(pubblicazione.getDataBore()) ||
			    	!isEmpty(pubblicazione.getNumeroGuce()) ||
			    	!isEmpty(pubblicazione.getNumeroGuri()) ||
			    	!isEmpty(pubblicazione.getNumeroBore()) ||
			    	!isEmpty(pubblicazione.getDataAlbo()) ||
			    	!isEmptyOrZero(pubblicazione.getQuotidianiNaz()) ||
			    	!isEmptyOrZero(pubblicazione.getQuotidianiReg()) ||
			    	!isEmptyOrZero(pubblicazione.getPeriodici()) ||
			    	//isFlag(pubblicazione.getProfiloCommitente()) ||
			    	isFlag(pubblicazione.getSitoMinisteroInfTrasp()) ||
			    	//isFlag(pubblicazione.getSitoOsservatorioCP()) ||
			    	//gm nuovo codice estensione pubblicazione bandi
			    	isFlag(pubblicazione.getFlag_benicult()) ||
			    	isFlag(pubblicazione.getFlag_sospeso()) ||
			    	!isEmpty(pubblicazione.getLinkSitoCommittente()))
			    result = false;
    		else
    			return true;
		}
		else 
			result = true;
		return result;
	}
	
	//MARRA MEV 34470 3.04.8
			public void validaBandiEContratti (Gara gara, String linkAffidamentoDiretto)
			{
			 	if(gara.getID_ESTREMA_URGENZA() == 2 && gara.getURGENZA_DL133().equalsIgnoreCase("S"))
		    	{
		    		if(linkAffidamentoDiretto == null || linkAffidamentoDiretto.isEmpty())//fix MARRA MEV 34470 3.04.8
		    		{
		    			mEccezioni.addValidationField("label_BandiEContratti");
		    			mEccezioni.addValidationErr(Messaggi.SIMOG_BANDI_CONTRATTI_001);
		    		}
		    		
		    		if(linkAffidamentoDiretto != null && !linkAffidamentoDiretto.isEmpty() && !isValidUrl(linkAffidamentoDiretto))//fix MARRA MEV 34470 3.04.8
		    		{
		    			mEccezioni.addValidationField("label_BandiEContratti");
		    			mEccezioni.addValidationErr(Messaggi.SIMOG_BANDI_CONTRATTI_001b);		 
		    		}
		    	}
			 	else
			 	{
			 		if(linkAffidamentoDiretto != null && !linkAffidamentoDiretto.isEmpty() && !isValidUrl(linkAffidamentoDiretto))//fix MARRA MEV 34470 3.04.8
		    		{
		    			mEccezioni.addValidationField("label_BandiEContratti");
		    			mEccezioni.addValidationErr(Messaggi.SIMOG_BANDI_CONTRATTI_001b);		 
		    		}
			 	}
				
			}
			
			//3.04.11 MEV 44999
			public void controlloBloccoCig(String dataPubblicazione) {
				//se la data di pubblicazione è successiva o uguale alla data attivazione blocco CIG
				if (SimogProperties.isDataAfterAttivazioneBloccoCig(dataPubblicazione)) {
	    			mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_020d);		 
				}
			}
		
		private boolean isValidUrl(String linkAffidamentoDiretto)
		{
			Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?(https://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  

			if(p.matcher(linkAffidamentoDiretto).matches())
			  return true;
			else
			  return false;
		}
		//FINE MEV 34470 3.04.8
	
}
