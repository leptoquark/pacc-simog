package it.avlp.simog.ws.commons;

import it.avcp.simog.auth.XmlManager;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.util.Encoder;
import it.avlp.simog.ws.xmlbeans.CheckLoginType;

import java.sql.Connection;

import org.apache.log4j.Logger;

	/***********************************************************************
	 * Classe che si occupa della gestione del "ticket" che e' 
	 * l'identificativo di sessione. Gestisce le seguenti variabili
	 * <lu>
	 * <li> logger : Logger
	 * <li> colls : Collaborazioni
	 * <li> coll : Collaborazione
	 * <li> valido : Boolean
	 * <li> adminOr : String
	 * </lu>
	 *
	 */

public class TicketManager {
	//Nuove variabili definite per distinguere la validazione dell'utente in base all'azione richiesta
	public static final int CONSULTA_GARA = 0;
// PP eliminato	public static final int INSERISCI_GARA_LOTTO = 1;
	public static final int MODIFICA_LOTTO = 2;
	public static final int PERFEZIONA_LOTTO = 3;
	public static final int GENERA_CIG = 4;
	public static final int INSERISCI_LOTTO = 5;
	//nuova normativa
	public static final int INSERISCI_GARA = 6;
	public static final int MODIFICA_GARA = 7;
	public static final int PERFEZIONA_GARA = 8;
	
	// P.C. cancella gara e cancella lotto
	public static final int CANCELLA_GARA = 9;
	public static final int CANCELLA_LOTTO = 10;
	public static final int PUBBLICAZIONE_BANDO = 11;
	
	// is3025_REQUISITIActive
	public static final int INVIA_REQUISITI = 12;

	// integrazione loaderappalto e wspdd
    public static final int CONSULTA_CIG = 13;
	public static final int MASSLOADER_WS = 14;
   public static final int PRESA_CARICO = 15;

   // integrazione dati CUP per il pregresso
   public static final int INTEGRA_CUP = 16;
   
   // consultazione iniziativa
   public static final int CONSULTA_INIZIATIVA = 17;
   
   //Servizi TED
   public static final int PUBBLICA_GARA_TED = 18;
   public static final int VERIFICA_TED = 19;
   public static final int RETTIFICA_TED = 20;
   public static final int CANCELLA_TED = 21;
   public static final int AGG_TED = 22;
   public static final int MODIFICA_TED = 23;
   
   public static final int INTEGRA_PARI_OPPORTUNITA = 24; //MEV 37010 3.04.8.1
   
   public static final int MODIFICA_DATI_PERFEZIONAMENTO = 25;
   
   public static final int MODIFICA_CPV = 26;
   public static final int MODIFICA_CAT_SOA = 27; /* MAD 68089 3.04.16 */
   
	// finta azione
    public static final int FAKE_ACTION = 999;

	//nuove variabili definite per evitare l'iterazione ripetuta
	private boolean isOsservatorioCentrale;
	private boolean isOsservatorioRegionale;
	
	// XXX: Introduzione di una nuova variabile per implementare il comportamento relativo
	// agli osservatori, se e' un'osservatorio e non viene passata una collaborazione (stringa) vuota per l'indice
	// devo desumenre che si agisce per osservatorio con tutto cio che comporta.
	private boolean isOsservatorio;
	private boolean operaComeOsservatorio;
	
	private boolean isOssAndColl;
	private boolean isColl;
	private boolean isOnlyOss;
	private String codiceRegione;
	//end 
	private Logger logger;
	private Collaborazioni colls = null;
	private Collaborazione coll = null;
	private boolean valido = false;
	private String adminOr = null;

	/*	costruttore	*/
	/**
	 * Costruttore
	 * 
	 */
	public TicketManager(){
		this.logger=LoggerManager.getInstance().getLogger();
	}

	/**
	 * metodo per gestire in modo distinto la validazione della sessione in base all'azione richiesta.
	 * 
	 * @see le costanti di classe per il secondo parametro in ingresso
	 * @param wss
	 * @param requestedAction
	 * @throws SimogWSException
	 */	
	public void validateRequestedActionByProfile(WsSessions wss,int requestedAction)throws SimogWSException{
		//cotrolla i profili
		this.checkProfilesAndTypes(wss);
		//controlla relazione profili/azione
		switch (requestedAction) {
		case CONSULTA_GARA:
			validateRequest_consultaGara(wss);
			break;
//	PP eliminato	case INSERISCI_GARA_LOTTO:
//			validateRequest_inserisciGaraLotto(wss);	
//			break;
		case MODIFICA_LOTTO:
			validateRequest_modificaLotto(wss);
			break;
		case PERFEZIONA_LOTTO:
		   validateRequest_perfezionaLotto(wss);
		   break;
		case GENERA_CIG:
			validateRequest_generaCig(wss);
			break;
		case INSERISCI_LOTTO:
			validateRequest_inserisciLotto(wss);
			break;
		case INSERISCI_GARA:
			validateRequest_inserisciGara(wss);
			break;
		case MODIFICA_GARA:
			validateRequest_modificaGara(wss);
			break;
		case PERFEZIONA_GARA:
           validateRequest_perfezionaGara(wss);
           break;
		case CANCELLA_GARA:
			validateRequest_cancellaGara(wss);
			break;
		case CANCELLA_LOTTO:
			validateRequest_cancellaLotto(wss);
			break;
		case PUBBLICAZIONE_BANDO:
			validateRequest_pubblicazioneBando(wss);
			break;
		// is3025_REQUISITIActive	
        case INVIA_REQUISITI:
           validateRequest_inviaRequisiti(wss);
           break;
        case CONSULTA_CIG:
           validateRequest_consultaGara(wss);
           break;
        case MASSLOADER_WS:
           validateRequest_massloader(wss);
           break;
        // is3028_RFWSSC00Active           
        case PRESA_CARICO:
           validateRequest_presaCarico(wss);
           break;           
        case INTEGRA_CUP:
           validateRequest_integraCUP(wss);
           break;
        case INTEGRA_PARI_OPPORTUNITA: //MEV 37010 3.04.8.1
            validateRequest_integraPariOpportunita(wss);
            break;
        case MODIFICA_CPV: //MEV 3.04.13
            validateRequest_modificaCPV(wss);
            break;
        case MODIFICA_DATI_PERFEZIONAMENTO: //MEV 3.04.10 43227
            validateRequest_modificaDatiPerfezionamento(wss);
            break;
        case CONSULTA_INIZIATIVA:
        	validateRequest_consultaIniziativa(wss);
        	break;
        case PUBBLICA_GARA_TED:
        	validateRequest_pubblicaGaraTED(wss);
        	break;
        case VERIFICA_TED:
        	validateRequest_verificaTED(wss);
        	break;
        case CANCELLA_TED:
        	validateRequest_cancellaTED(wss);
        	break;
        case RETTIFICA_TED:
        	validateRequest_rettificaTED(wss);
        	break;
        case AGG_TED:
        	validateRequest_aggiudicazioneTED(wss);
        	break;
        case MODIFICA_CAT_SOA: /* MAD 68089 3.04.16 Inizio */
        	validateRequest_modificaCatSoa(wss);
        	break; /* MAD 68089 3.04.16 Fine */

        default:
			throw new SimogWSException("L'azione richiesta non esiste");
		}
		if(!this.valido)
			throw new SimogWSException(ErrorManager.SIMOGWS_TICKETMANAGER_APP_02);
	}
	/**
	 * Metodo che si occupa di controllare i tipi e i profili attivi e validi
	 * 
	 * @param wss WsSessions
	 * @throws SimogWSException
	 */
	private void checkProfilesAndTypes(WsSessions wss)throws SimogWSException{
		//prendo la stringa xml del login dall'oggetto WsSession
		String xml = wss.getXmlAuth();
		//oggetto per la conversione e vallidazione della stringaxml di cui sopra
		XmlManager xm = new XmlManager(logger);
		//se la validazione della stringa xml ha esito positivo
		if(xm.validaXsd(xml)){
			//se lo stato dell'utente e' valido
			if(xm.validaXml()){
				//prendo l'xmlbean dall'oggetto di conversione/verifica (ritorna non null solo se la validazione ha avuto esito positivo)
				CheckLoginType clt = xm.getXmlBean();
				//prendo la lista delle collaboriazioni dal xmlbean
				Collaborazioni colls = new CollaborazioniManager().getCollaborazioni(clt);
				//controllo se e' un'osservatorio centrale
				this.isOsservatorioCentrale = clt.getSoggetto().getAdminOr().equalsIgnoreCase(ProfiloEnum.REGIONE_099) || 
												clt.getSoggetto().getAdminOr().equalsIgnoreCase(ProfiloEnum.REGIONE_999);
				//controllo se e' un'osservatorio regionale
				this.isOsservatorioRegionale = !clt.getSoggetto().getAdminOr().equalsIgnoreCase(ProfiloEnum.REGIONE_099) && 
												!clt.getSoggetto().getAdminOr().equalsIgnoreCase(ProfiloEnum.REGIONE_999) &&
												!clt.getSoggetto().getAdminOr().equalsIgnoreCase( ProfiloEnum.REGIONE_ZERO) &&
												clt.getSoggetto().getAdminOr() != null;
				
				//se e' osservatorio regionale valorizza il codice regione
				if(this.isOsservatorioRegionale){
					this.codiceRegione = clt.getSoggetto().getAdminOr();
				}
				
				this.isOsservatorio = this.isOsservatorioCentrale || this.isOsservatorioRegionale;
				this.operaComeOsservatorio = this.isOsservatorio && wss.getCollaborazione() == -1;
				//se esistono delle collaborazioni allora il profilo ha delle collaborazioni
				this.isColl = colls != null && colls.getCollaborazioni().length > 0;
				//e quindi le memorizzo in locale
				if(isColl){	this.colls = colls;	}
				//se ha collaborazioni ed e' osservatorio
				isOssAndColl = isColl && (isOsservatorioCentrale || isOsservatorioRegionale);
				//se non ha delle collaboriazioni ed e' osservatorio e' solo osservatorio
				isOnlyOss = !isColl && (isOsservatorioCentrale || isOsservatorioRegionale);
				//valorizzazione dell'adminOr
				this.adminOr = clt.getSoggetto().getAdminOr();
				//log di tutti gli stati rilevati
				logger.debug(this.getStatiUtente());				
				return;
			}
		}
	}
	/**
	 * metodo per la validazione del profilo per l'azione di consulta gara
	 * @param wss WsSessions
	 */
	private void validateRequest_consultaGara(WsSessions wss){
		if(isOsservatorioCentrale || isOsservatorioRegionale){this.valido = true;return;}
		if(isColl){ this.valido = hasRouloValido();}
		if(wss != null)
			System.out.println("TB validateRequest_consultaGara user: "+wss.getUserId()+" isOsservatorioCentrale: "+isOsservatorioCentrale+" isOsservatorioRegionale: "+isOsservatorioRegionale);
	}
	/**
	 * metodo per la validazione del profilo per l'azione di inserisciGaraLotto
	 * @param wss WsSessions
	 */
//	PP eliminato private void validateRequest_inserisciGaraLotto(WsSessions wss)throws SimogWSException{
//		this.valido = this.isRuoloRSSA(wss.getCollaborazione());
//	}
	/**
	 * metodo per la validazione del profilo per l'azione di cancellaGara
	 * @param wss WsSessions
	 */
	private void validateRequest_cancellaGara(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione()) || this.isRuoloAdmin(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione di modifica lotto
	 * @param wss WsSessions
	 */
	private void validateRequest_modificaLotto(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione di perfezionamento del lotto
	 * @param wss WsSessions
	 */
	private void validateRequest_perfezionaLotto(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione richiesta
	 * @param wss WsSessions
	 */
	private void validateRequest_generaCig(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione richiesta
	 * @param wss WsSessions
	 */
	private void validateRequest_inserisciLotto(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione richiesta
	 * @param wss WsSessions
	 */
	private void validateRequest_inserisciGara(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		// FIXME: PP togliere RSSA per disabilitare l'inserimento di nuove gare
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione richiesta
	 * @param wss WsSessions
	 */
	private void validateRequest_modificaGara(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione richiesta
	 * @param wss WsSessions
	 */
	private void validateRequest_perfezionaGara(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione richiesta
	 * @param wss WsSessions
	 */
	private void validateRequest_cancellaLotto(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione())|| this.isRuoloAdmin(wss.getCollaborazione());
	}
	/**
	 * metodo per la validazione del profilo per l'azione richiesta
	 * @param wss WsSessions
	 */
	private void validateRequest_pubblicazioneBando(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}

   /** is3025_REQUISITIActive
     * metodo per la validazione del profilo per l'azione richiesta
     * @param wss WsSessions
     */
    private void validateRequest_inviaRequisiti(WsSessions wss)throws SimogWSException{
        if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
    }

    /**
     * metodo per la validazione del profilo per l'azione richiesta
     * @param wss WsSessions
     */
    private void validateRequest_massloader(WsSessions wss)throws SimogWSException{
    	if(wss != null)
    		System.out.println("TB validateRequest_massloader user: "+wss.getUserId()+" operaComeOsservatorio: "+operaComeOsservatorio);
        if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRUP(wss.getCollaborazione());
    }

    /**
     * metodo per la validazione del profilo per l'azione di perfezionamento del lotto
     * @param wss WsSessions
     */
    private void validateRequest_presaCarico(WsSessions wss)throws SimogWSException{
       // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
       this.valido = this.isRuoloRUP(wss.getCollaborazione());
    }
       
    //MEV 37010 3.04.8.1
    /**
     * metodo per la validazione del profilo per l'azione di integrazione dati pari opportunita del lotto
     * @param wss WsSessions
     */
    private void validateRequest_integraPariOpportunita(WsSessions wss)throws SimogWSException{
       // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
       this.valido = this.isRuoloRUP(wss.getCollaborazione());
    }
    
  //MEV 37010 3.04.8.1
    /**
     * metodo per la validazione del profilo per l'azione di integrazione dati pari opportunita del lotto
     * @param wss WsSessions
     */
    private void validateRequest_modificaCPV(WsSessions wss)throws SimogWSException{
       // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
       this.valido = this.isRuoloRUP(wss.getCollaborazione());
    }
    
  //MEV 3.04.10 43227
    /**
     * metodo per la validazione del profilo per l'azione di modifica dati perfezionamento
     * @param wss WsSessions
     */
    private void validateRequest_modificaDatiPerfezionamento(WsSessions wss)throws SimogWSException{
       // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
       this.valido = this.isRuoloRUP(wss.getCollaborazione());
    }
    
    
    
    /**
     * metodo per la validazione del profilo per l'azione di integrazione dati cup del lotto
     * @param wss WsSessions
     */
    private void validateRequest_integraCUP(WsSessions wss)throws SimogWSException{
       // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
       this.valido = this.isRuoloRUP(wss.getCollaborazione());
    }

    private void validateRequest_verificaTED(WsSessions wss)throws SimogWSException{
        // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRUP(wss.getCollaborazione());
     }
    
    private void validateRequest_pubblicaGaraTED(WsSessions wss)throws SimogWSException{
        // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRUP(wss.getCollaborazione());
     }
    
    private void validateRequest_cancellaTED(WsSessions wss)throws SimogWSException{
        // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRUP(wss.getCollaborazione());
     }
    
    private void validateRequest_rettificaTED(WsSessions wss)throws SimogWSException{
        // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRUP(wss.getCollaborazione());
     }
    private void validateRequest_aggiudicazioneTED(WsSessions wss)throws SimogWSException{
        // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRUP(wss.getCollaborazione());
     }
    private void validateRequest_modificaCatSoa(WsSessions wss)throws SimogWSException{
        // solo il RUP if(operaComeOsservatorio){ this.valido = true; return;}
        this.valido = this.isRuoloRUP(wss.getCollaborazione());
     }
    
    
	/**
	 * metodo per la validazione del profilo per l'azione di consultazione iniziativa
	 * @param wss WsSessions
	 */
	private void validateRequest_consultaIniziativa(WsSessions wss)throws SimogWSException{
		if(operaComeOsservatorio){ this.valido = true; return;}
		this.valido = this.isRuoloRSSA(wss.getCollaborazione()) || this.isRuoloRUP(wss.getCollaborazione());
	}
    
    /*******************************************************************************************
	 * metodo per la generazione e chiamata all'inserimento del ticket
	 * @param XMLUtente : String
	 * @return String
	 * @throws SimogWSException
	 */
	public String generate(String XMLUtente)throws SimogWSException{
		logger.debug("eseguendo: generate(String XMLUtente)");
		try{
			return Encoder.encode(XMLUtente);	
		}catch(RuntimeException re){
			logger.error("errore durante la creazione del ticket "+re.getMessage());
			//re.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_TICKETMANAGER_SHA_01);
		}
	}
	/*	metodo che ritorna le collaborazioni dell'utente usato a fini di flusso dati/controlli	*/
	
	/*******************************************************************************************
	 * Restituisce le collaborazioni
	 * @return Collaborazioni
	 * @throws SimogWSException
	 */
	public Collaborazioni getCollaborazioni()throws SimogWSException{
		if(this.colls != null){
			return this.colls;
		}
		throw new SimogWSException(ErrorManager.SIMOGWS_TICKETMANAGER_NULL_03);

	}

	/**
	 * Metodo che ritorna la collaborazione dell'utente usato a fini di flusso dati/controlli
	 * e' da notare che la collaborazione viene settata altrove il metodo si occupa solamente
	 * di verificare se la variabile di classe coll [=collaborazione] non sia nulla e in tal caso 
	 * la ritorna, altrimenti lancia un'eccezione
	 * 
	 * @return Collaborazione
	 * @throws SimogWSException
	 */
	public Collaborazione getCollaborazione()throws SimogWSException{
		if(this.coll != null){
			return this.coll;
		}
		throw new SimogWSException(ErrorManager.SIMOGWS_TICKETMANAGER_NULL_04);
	}

	/**
	 * controlla che tra le collaborazioni dell'utente ci sia almeno una collaborazione con il ruolo
	 * richiesto per la consultazione di una gara. verr&agrave; comunque fatto un'ulteriore controllo al
	 * momento della richiesta della stringa xml.. in garaXMLmanager
	 * return esito
	 */
	 
	/*******************************************************************
	 * controlla che tra le collaborazioni dell'utente ci sia almeno una collaborazione con il ruolo
	 * richiesto per la consultazione di una gara. verr&agrave; comunque fatto un'ulteriore controllo al
	 * momento della richiesta della stringa xml.. in garaXMLmanager
	 * return esito
	 */
	private boolean hasRouloValido(){
		Collaborazione[] collaborazioni = colls.getCollaborazioni();
		for(int i = 0;i<collaborazioni.length;i++){			
			Collaborazione coll = collaborazioni[i];
			if(coll.getUfficio_profilo().equals(ProfiloEnum.RSSAOLD.codice()) ||
					coll.getUfficio_profilo().equals(ProfiloEnum.RUP.codice()) ||
					coll.getUfficio_profilo().equals(ProfiloEnum.RASA.codice()) ||
					coll.getUfficio_profilo().equals(ProfiloEnum.AMMINISTRATORE.codice())){
				return true;
			}
		}return false;		
		
	}
	/**
	 * Metodo che controlla che l'indice passato sia quello di un \"RSSA\"
	 * @param indice
	 * @return boolean esito controllo
	 */
	private boolean isRuoloRSSA(int indice)throws SimogWSException{
		try{
			boolean rssa = colls.getPerIndice(Integer.toString(indice)).getUfficio_profilo().equals(ProfiloEnum.RSSAOLD.codice());
			if(rssa){ this.coll = colls.getPerIndice(Integer.toString(indice));}
			return rssa;
		}catch(Exception e){
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_09);
		}
	}

	/**
	 * Metodo che controlla che l'indice passato sia quello di un RUP
	 * @param indice
	 * @return boolean esito controllo
	 */
	private boolean isRuoloRUP(int indice)throws SimogWSException{
		try{
			boolean rssa = colls.getPerIndice(Integer.toString(indice)).getUfficio_profilo().equals(ProfiloEnum.RUP.codice());
			if(rssa){ this.coll = colls.getPerIndice(Integer.toString(indice));}
			return rssa;
		}catch(Exception e){
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_09);
		}
	}

	/**
	 * Metodo che controlla che l'indice passato sia quello di un amministratore
	 * @param indice
	 * @return boolean esito controllo
	 */
	private boolean isRuoloAdmin(int indice)throws SimogWSException{
		try{
			boolean rssa = colls.getPerIndice(Integer.toString(indice)).getUfficio_profilo().equals(ProfiloEnum.AMMINISTRATORE.codice());
			if(rssa){ this.coll = colls.getPerIndice(Integer.toString(indice));}
			return rssa;
		}catch(Exception e){
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_09);
		}
	}
	private boolean isRuoloRasa(int indice)throws SimogWSException{
		try{
			boolean rssa = colls.getPerIndice(Integer.toString(indice)).getUfficio_profilo().equals(ProfiloEnum.RASA.codice());
			if(rssa){ this.coll = colls.getPerIndice(Integer.toString(indice));}
			return rssa;
		}catch(Exception e){
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_09);
		}
	}
	public String getAdminOr(){
		// PP 17.02.2010 normalizzato a tre caratteri 
		String padded_id_osservatorio = this.adminOr.trim();
		if (padded_id_osservatorio.length()== 2)  
			padded_id_osservatorio =  "0" + padded_id_osservatorio; 
		return padded_id_osservatorio; // this.adminOr;
	}
	
	public boolean isValido() {
		return valido;
	}

	public boolean isOsservatorioCentrale() {
		return isOsservatorioCentrale;
	}

	public boolean isOsservatorioRegionale() {
		return isOsservatorioRegionale;
	}

	/**
	 * Metodo che ritorna se l'utente e' osservatorio regionale ed ha anche almeno una collaborazione
	 * @return
	 */
	 public boolean isOssAndColl() {
		return isOssAndColl;
	}
	
	/**
	 * Metodo che ritorna se l'utente ha delle collaborazioni
	 * 
	 * @return boolean
	 */
	public boolean isColl() {
		return isColl;
	}

	public String getCodiceRegione() {
		return codiceRegione;
	}

	/**
	 * Metodo che controlla che ritorna se l'utente e' o meno solo un'osservatorio
	 * 
	 * @return boolean
	 */
	 public boolean isOnlyOss() {
		return isOnlyOss;
	}
	 
	 
	/**
	 * True solamente se l'utente risulta un'osservatorio (centrale o regioniale)
	 * 
	 * @return the isOsservatorio
	 */
	public boolean isOsservatorio() {
		return isOsservatorio;
	}

	/**
	 * True solamente se l'utente risulta un'osservatorio (centrale o regioniale) e l'indice collaborazione
	 * risulta pari a -1, (stringa vuota immessa dall'utente)
	 * 
	 * @return the operaComeOsservatorio
	 */
	public boolean isOperaComeOsservatorio() {
		return operaComeOsservatorio;
	}

	/**
	 * Simple status view
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuffer toString = new StringBuffer();
		toString.append("isOsservatorioCentrale : " +isOsservatorioCentrale +"\n\r") ;
		toString.append("isOsservatorioRegionale : " +isOsservatorioRegionale +"\n\r");
		toString.append("isOsservatorio : " +isOsservatorio +"\n\r");
		toString.append("operaComeOsservatorio : " +operaComeOsservatorio +"\n\r");
		toString.append("isOssAndColl : " +isOssAndColl +"\n\r");
		toString.append("isColl : " +isColl+"\n\r");
		toString.append("isOnlyOss : " +isOnlyOss +"\n\r");
		toString.append("adminOr : " +this.adminOr +"\n\r");
		return toString.toString();
	}
	/**
	 * Metodo usato a fini di logging prepara una stringa contente
	 * tutti gli stati dell'utente.
	 * 
	 * @return String
	 */private String getStatiUtente(){
		String stati = "";
		stati += "isOsservatorioCentrale : " +isOsservatorioCentrale +"\n\r";
		stati += "isOsservatorioRegionale : " +isOsservatorioRegionale +"\n\r";
		stati += "isOssAndColl : " +isOssAndColl +"\n\r";
		stati += "isColl : " +isColl+"\n\r";
		stati += "isOnlyOss : " +isOnlyOss +"\n\r";
		stati += "adminOr : " +this.adminOr +"\n\r";
		
		return stati;
	}
	 
  /*******************************************************************
     * controlla che tra le collaborazioni dell'utente ci sia almeno una collaborazione con il ruolo
     * amministratore
     * return esito
     */
    public boolean isAdmin(){
       if (colls != null && colls.getCollaborazioni() != null){
           Collaborazione[] collaborazioni = colls.getCollaborazioni();
           for(int i = 0;i<collaborazioni.length;i++){         
               Collaborazione coll = collaborazioni[i];
               if(coll.getUfficio_profilo().equals(ProfiloEnum.AMMINISTRATORE.codice())){
                   return true;
               }
           }
       }
        return false;     
    }
    public boolean isRasa(){
        if (colls != null && colls.getCollaborazioni() != null){
            Collaborazione[] collaborazioni = colls.getCollaborazioni();
            for(int i = 0;i<collaborazioni.length;i++){         
                Collaborazione coll = collaborazioni[i];
                if(coll.getUfficio_profilo().equals(ProfiloEnum.RASA.codice())){
                    return true;
                }
            }
        }
         return false;     
     }
}