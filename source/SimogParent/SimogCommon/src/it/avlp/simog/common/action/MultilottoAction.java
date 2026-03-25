package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.r129.R129Manager;
import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
         
public class MultilottoAction //extends BaseAction 
{
	protected Connection connection;
	protected Logger logger;
	protected AllValidationBeans mEccezioni;

	public MultilottoAction(Connection activeConnection, Logger logger) {
		this.connection = activeConnection;
		this.logger = logger;
		this.mEccezioni = new AllValidationBeans();
	}
	
	/*********************************************************************
	 * il metodo si occupa di restituire il parametro mEccezioni 
	 * definito nella classe contenente i messaggi relativi alle accezioni rilevate
	 * 
	 * @return  AllValidatorBeans
	 */
	public AllValidationBeans getEccezioni() {
		return mEccezioni;
	}
	
	/*********************************************************************
	 * il metodo si occupa di verificare se l'aggiudicazione corrente può 
	 * far parte di un appalto multilotto
	 * @param AggiudicazioneBean aggCorrente : l'Aggiudicazione da verificare
	 * @param List<AggiudicazioneBean> aggiudicazioniStessaGara : la lista delle Aggiudicazioni per la stessa gara, 
	 * comprende anche l'aggCorrente
	 * @return  boolean
	 */
	public Map<String,List<AggiudicazioneBean>> getMappaMultilotto(AggiudicazioneBean aggCorrente, List<AggiudicazioneBean> aggiudicazioniStessaGara){
		Map<String,List<AggiudicazioneBean>> mappaMultilotto = new HashMap <String,List<AggiudicazioneBean>>();
	    
		//gm se fa già parte di un gruppo  
		if(isFlag(aggCorrente.getFlagAggiudPrincipale()) || !isEmpty(aggCorrente.getCodiceContratto()))
			return mappaMultilotto;
		
		//gm se ha già altre schede inserite
		boolean hasSchede = false;
		hasSchede = controlloSchedeAvanzate(aggCorrente.getIdAggiudicazione(), aggCorrente.getDataInizioAggiudicazione());
		if(hasSchede)
			return mappaMultilotto;
		
    	//gm per avere un multilotto devono esserci almeno due aggiudicazioni
		if(aggiudicazioniStessaGara.size()>1){
			AggiudicatarioManager aggman = new AggiudicatarioManager(connection, logger);
			Map <Long,List<AggiudicatarioBean>> mappaAggiudicatariPerAggiudicazione = new HashMap <Long,List<AggiudicatarioBean>>();
	    	for(AggiudicazioneBean aggiudicazione : aggiudicazioniStessaGara){
	    		try{
	    			List<AggiudicatarioBean> listaAggiudicatari = new ArrayList<AggiudicatarioBean>();
	    			listaAggiudicatari = aggman.loadMany(aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione(), false);
	    			mappaAggiudicatariPerAggiudicazione.put(Long.valueOf(aggiudicazione.getIdAggiudicazione()), listaAggiudicatari);
	    		}
	    		catch(Exception e){
	    			e.printStackTrace();
	    			return mappaMultilotto;
	    		}
	    	}
	    	//gm per avere un multilotto devono esserci almeno due liste di aggiudicatari
	    	if(mappaAggiudicatariPerAggiudicazione.size()>1){
				Set <Long> setIdAggiudicazioni = new HashSet <Long>();
				//filtro la mappa mantenendo solo le aggiudicazioni che hanno stessi Aggiudicatari
				setIdAggiudicazioni = filtraPerStessiAggiudicatari(Long.valueOf(aggCorrente.getIdAggiudicazione()), mappaAggiudicatariPerAggiudicazione);
	    	    if(setIdAggiudicazioni.size()>1){
	    	    	Map <String,List<AggiudicazioneBean>> mappaAggiudicazioniSeparate = new HashMap <String,List<AggiudicazioneBean>>();
	    	    	mappaAggiudicazioniSeparate = separaPerCodiceContratto(setIdAggiudicazioni, aggiudicazioniStessaGara);
	    	    	if(mappaAggiudicazioniSeparate.size()>0){
	    	    		mappaMultilotto = mappaAggiudicazioniSeparate;
	    	    	}
	    	    	else{
	    	    		return mappaMultilotto;
	    	    	}
	    	    }
	    	}
   		}
		return mappaMultilotto;
	}
	
	
	/**********************************************************************************************
	 * Ritorna una mappa delle Aggiudicazioni che possono essere associate 
	 * all'aggiudicazione corrente in base al codice contratto (per ogni codice, una lista),
	 * vanno aggiunte anche le aggiudicazioni associabili che non hanno un codice contratto
	 * (in questo caso si aggiunge codice contratto = "" nella mappa)
	 * @param Set<Long> setIdAggiudicazioni : gli idAggiudicazione che hanno stessi Aggiudicatari dell'Aggiudicazione corrente
	 * @param List<AggiudicazioneBean> aggiudicazioniStessaGara : le Aggiudicazioni che appartengono alla stessa gara dell'Aggiudicazione corrente
	 * @return Map <String,List<AggiudicazioneBean>> : la mappa contenente i codici contratto, e per ognuno di essi, la lista delle Aggiudicazioni
	 * con stesso codiceContratto e con stessi Aggiudicatari dell'Aggiudicazione corrente
	 */
	private Map <String,List<AggiudicazioneBean>> separaPerCodiceContratto(Set<Long> setIdAggiudicazioni, List<AggiudicazioneBean> aggiudicazioniStessaGara){
		Map <String,List<AggiudicazioneBean>> risultato = new HashMap <String,List<AggiudicazioneBean>>();
		
		for(Long idAgg : setIdAggiudicazioni){
			for(AggiudicazioneBean aggiudicazione : aggiudicazioniStessaGara){
				if(idAgg.equals(Long.valueOf(aggiudicazione.getIdAggiudicazione()))){
					String codiceContratto = aggiudicazione.getCodiceContratto();
					if(codiceContratto==null){
						codiceContratto="";
					}
					if(risultato.containsKey(codiceContratto)){
						List<AggiudicazioneBean> aggiudicazioniPerCodice = risultato.get(codiceContratto);
						aggiudicazioniPerCodice.add(aggiudicazione);
						risultato.put(codiceContratto, aggiudicazioniPerCodice);
					}
					else{
						List<AggiudicazioneBean> aggiudicazioniPerCodice = new ArrayList<AggiudicazioneBean>();
						aggiudicazioniPerCodice.add(aggiudicazione);
						risultato.put(codiceContratto, aggiudicazioniPerCodice);
					}
				}
			}			
		}
		//gm se alla fine per il codice contratto "" ho solo un'aggiudicazione, 
		//allora è solo quella corrente e non posso creare un multilotto, quindi la rimuovo
		if(risultato.get("").size()<2)
			risultato.remove("");
		return risultato;
	}
	
	
	/**********************************************************************************************
	 * Ritorna la Mappa delle Aggiudicazioni con relativi Aggiudicatari associabili all'Aggiudicazione corrente
	 * perchè appartengono alla stessa gara ed hanno gli stessi aggiudicatari
	 */
	private Set <Long> filtraPerStessiAggiudicatari(Long idAggCorrente, Map <Long,List<AggiudicatarioBean>> mappaAggiudicatariPerAggiudicazione){
		Set <Long> risultato = new HashSet <Long>();
		risultato.add(idAggCorrente);
		
		List<AggiudicatarioBean> aggiudicatariCorrenti = new ArrayList<AggiudicatarioBean>(); 
		aggiudicatariCorrenti =	mappaAggiudicatariPerAggiudicazione.get(idAggCorrente);
		Set <Long> setId = new HashSet<Long>();
		setId = mappaAggiudicatariPerAggiudicazione.keySet();
		for(Long idAgg : setId){
			//se l'idAggiudicazione del Set è diverso da quello corrente
			if(!idAgg.equals(idAggCorrente)){
				//ed i suoi Aggiudicatari sono gli stessi di quelli dell'Aggiudicazione corrente
				if(comparaAggiudicatari(aggiudicatariCorrenti,mappaAggiudicatariPerAggiudicazione.get(idAgg)))
					risultato.add(idAgg);
			}
		}	
		return risultato;
	}
	
	/**********************************************************************************************
	 * Verifica che le due liste di aggiudicatari contengano gli stessi aggiudicatari
	 */
    private boolean comparaAggiudicatari(List<AggiudicatarioBean> listaCorrente, List<AggiudicatarioBean> listaProva){
    	boolean uguali = true;
    	boolean trovato = false;
    	
    	//se le dimensioni delle liste sono diverse non ci sono gli stessi aggiudicatari
    	if(listaCorrente.size()!=listaProva.size())
    		return false;
    	for(AggiudicatarioBean aggCorrente : listaCorrente){
    		for(AggiudicatarioBean aggProva : listaProva){
    			//se gli aggiudicatari hanno lo stesso soggetto partecipante
    			if(aggCorrente.getSoggettoPartecipante().getIdSoggettoPartecipante() == aggProva.getSoggettoPartecipante().getIdSoggettoPartecipante()){
    				//ed hanno anche lo stesso tipo
    				if(aggCorrente.getIdTipoAgg()==aggProva.getIdTipoAgg()){
    					trovato = true;
    				}
    			}
    		}
    		if(!trovato)
    			uguali = false;
    		else
    			trovato = false;
    	}
    	return uguali;
    }
    /**
     * Il metodo verifica che per l'aggiudicazione corrente non esistano schede avanzate,
     * come inizioLavori, subappalti, varianti o recessi 
     * @param idAggiudicazione
     * @param dataInizioAggiudicazione
     * @return boolean
     */
    private boolean controlloSchedeAvanzate(long idAggiudicazione, Timestamp dataInizioAggiudicazione){
    	boolean hasSchede = false;
    	
    	InizioLavoriBean ilb = null;
		InizioLavoriManager inizioM = new InizioLavoriManager(connection, logger);
		try{
    		ilb = inizioM.load(idAggiudicazione, dataInizioAggiudicazione);
    		//gm se ha già una scheda inizio lavori
	    	if(ilb.getIdInizioLavori()>0)
	     		hasSchede = true;
		
	    	List<SubappaltiBean> listaSb = null;
	    	SubappaltiManager subappaltiM = new SubappaltiManager(connection, logger);
			listaSb = subappaltiM.loadMany(idAggiudicazione, dataInizioAggiudicazione);		
	    	//gm se ha già una scheda subappalti
	    	if(listaSb.size()>0)
	    		hasSchede = true;
		
    		List<VarianteBean> listaVb = null;
	    	VarianteManager varianteM = new VarianteManager(connection, logger);
			listaVb = varianteM.loadMany(idAggiudicazione, dataInizioAggiudicazione);
	    	//gm se ha già una scheda variante
	    	if(listaVb.size()>0)
    			hasSchede = true;
		
    		List<R129Bean> listaR = null;
    		R129Manager recessoM = new R129Manager(connection, logger);
			listaR = recessoM.loadMany(idAggiudicazione, dataInizioAggiudicazione);
	    	//gm se ha già una scheda recesso
	    	if(listaR.size()>0)
	    		hasSchede = true;
		}
		catch (Exception e){
    		e.printStackTrace();
    	}
    	return hasSchede;
    }
    
    public void aggiungiAlGruppo(String codiceSelezionato, String idAggiudicazioneCorrente) throws Exception{
    	try{
    		long idAgg = Long.parseLong(idAggiudicazioneCorrente);
    		MultilottoManager mm = new MultilottoManager(connection, logger);
    		mm.aggiungiAlGruppo(codiceSelezionato, Costanti.FLAG_VALORE_NO, idAgg);
    	}
    	catch (Exception e){
    		throw e;
    	}
    }
    
    public void creaNuovoGruppo(List<String> idAggiudicazioniDaAggiungere, Set <String> setCodiciContratto, String idAggiudicazioneCorrente) throws Exception{
        try{
        	String newCodice = "";
        	newCodice = generaCodiceContratto(setCodiciContratto); 	
        	
        	MultilottoManager mm = new MultilottoManager(connection, logger);
        	
        	String flagAggPrincipale = null;
        	String idAggiudicazionePrincipale = null;
        	idAggiudicazionePrincipale = trovaIdAggiudicazionePrincipale(idAggiudicazioniDaAggiungere);
        	
        	for(String idAggDaAggiungere : idAggiudicazioniDaAggiungere){
        		if(idAggiudicazionePrincipale.equals(idAggDaAggiungere))
            		flagAggPrincipale=Costanti.FLAG_VALORE_SI;
            	else
            		flagAggPrincipale=Costanti.FLAG_VALORE_NO;
            	mm.aggiungiAlGruppo(newCodice, flagAggPrincipale, Long.parseLong(idAggDaAggiungere));
        	}
    	}
    	catch (Exception e){
    		throw e;
    	}
    }
    
    public void modificaGruppo(List <AggiudicazioneBean> listaAggiudicazioniStessoContratto, List<String> aggiudicazioniDaEliminare) throws Exception{
        try{
        	MultilottoManager mm = new MultilottoManager(connection, logger);
        	AggiudicazioneBean aggPrincipale = new AggiudicazioneBean();
        	List<String> aggiudicazioniRestanti = new ArrayList<String>();
        	for(AggiudicazioneBean agg : listaAggiudicazioniStessoContratto){
        		//cerco l'aggiudicazione principale
        		if(Costanti.FLAG_VALORE_SI.equals(agg.getFlagAggiudPrincipale()))
        			aggPrincipale = agg;
           	    //e creo la lista di idAggiudicazioni da non modificare
        		if(!aggiudicazioniDaEliminare.contains(String.valueOf(agg.getIdAggiudicazione())))
        			aggiudicazioniRestanti.add(String.valueOf(agg.getIdAggiudicazione()));
        	}
        	//se tra le aggiudicazioni sto eliminando quella principale
        	if(aggiudicazioniDaEliminare.contains(String.valueOf(aggPrincipale.getIdAggiudicazione()))){
        		
        		String newIdAggPrincipale = "";
        		//calcolo la nuova aggiudicazione principale
        		newIdAggPrincipale = trovaIdAggiudicazionePrincipale(aggiudicazioniRestanti);
            	AggiudicazioneBean newAggPrincipale = new AggiudicazioneBean();
            	for(AggiudicazioneBean aggiud : listaAggiudicazioniStessoContratto){
            		if(String.valueOf(aggiud.getIdAggiudicazione()).equals(newIdAggPrincipale))
            			newAggPrincipale = aggiud;
            	}
            	//aggiorno lo stato della nuova aggiudicazione principale
            	mm.aggiungiAlGruppo(newAggPrincipale.getCodiceContratto(), Costanti.FLAG_VALORE_SI, newAggPrincipale.getIdAggiudicazione());
        	}
        	//per le altre aggiudicazioni da eliminare, se esistono
        	if(aggiudicazioniDaEliminare.size()>0){
        		for(String idAggEliminare : aggiudicazioniDaEliminare){
        			mm.eliminaDalGruppo(Long.parseLong(idAggEliminare));
        		}
        	}   	
        }
        catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public String generaCodiceContratto(Set<String>codiciContratto) throws Exception{
    	String newCodice = "";
    	//se ho già una lista dei codici contratto per aggiudicazioni relative alla stessa gara
        if(codiciContratto != null && codiciContratto.size()>0){
        	//cerco il codice di valore maggiore
        	String maxCodice = "";
            for(String codice : codiciContratto){
        	   if(codice.compareTo(maxCodice)>0)
        		   maxCodice = codice;
            }
            //se il codice max è una stringa vuota
            if("".equals(maxCodice))
            	newCodice = "1";
            else{
            	//se il codice max è un numero, il nuovo codice sarà il numero successivo
            	if(isNumber(maxCodice))
                    newCodice = String.valueOf((Integer.parseInt(maxCodice))+1);
            	//se il codice max non è un numero, il nuovo codice sarà
            	//lo stesso del vecchio con l'ultimo carattere successivo in valore ASCII 
            	else{
            		newCodice = maxCodice.substring(0, maxCodice.length()-1).concat(String.valueOf((char)(((int)(maxCodice.charAt(maxCodice.length()-1)))+1)));
            	}
            }
        }
        else{
        	newCodice = "";
        }
        return newCodice;
    }
    
    public String trovaIdAggiudicazionePrincipale(List<String> aggiudicazioniDaAggiungere){
    	String idAggPrincipale = "";
    	BigDecimal importoLotto = null;
    	BigDecimal importoLottoMax = null;
    	
    	MultilottoManager mm = new MultilottoManager(connection, logger);
        for (String idAgg : aggiudicazioniDaAggiungere){
        	//la prima aggiudicazione che trovo con importo non determinato sarà automaticamente la principale
        	if(importoLottoMax!=null && importoLottoMax.compareTo(new BigDecimal(-1))==0)
        		break;
        	try{
            	importoLotto = mm.getImportoLottoByIdAggiudicazione(Long.parseLong(idAgg));
            	if(importoLotto!=null){
            		if(importoLottoMax!=null){
            			//se l'importo è > dell'importo max oppure non è determinato, aggiorno l'Aggiudicazione principale
            			if(importoLotto.compareTo(new BigDecimal(-1))==0 || importoLotto.compareTo(importoLottoMax)>0){
            				importoLottoMax = importoLotto;
            				idAggPrincipale = idAgg;
            			}
            		}
            		else{
            			//se l'importo max è null, lo aggiorno con il valore dell'importo lotto trovato
            			importoLottoMax = importoLotto;
            			idAggPrincipale = idAgg;
            		}
            	}
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        }
    	return idAggPrincipale;
    }
    
    public void validaNuovoGruppo(List<String> aggiudicazioniDaAggiungere){
    	if(aggiudicazioniDaAggiungere.size()<2)
    		this.mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Nuovo Contratto Multilotto").replace("$2", "l'inserimento di almeno due Aggiudicazioni nel gruppo"));
    }
    
    public void validaModificaGruppo(List <AggiudicazioneBean> listaAggiudicazioniStessoContratto, List<String> aggiudicazioniDaEliminare){
    	boolean trovato = false;
    	int riga = 0;
    	for(String idAgg : aggiudicazioniDaEliminare){
    	    riga++;
    	    for(AggiudicazioneBean agg : listaAggiudicazioniStessoContratto){	
    		    if(String.valueOf(agg.getIdAggiudicazione()).equals(idAgg)){
    		    	trovato = true;
    		    }
    		}
    	    if(!trovato)
        		this.mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Modifica Contratto Multilotto").replace("$2", "l'eliminazione di Aggiudicazioni solo se fanno parte del gruppo"),riga);
   		    else
   		    	trovato = false;
    	}
    	if(aggiudicazioniDaEliminare.size()==0)
    		this.mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Modifica Contratto Multilotto").replace("$2", "l'eliminazione di almeno una Aggiudicazioni dal gruppo"));
    	if(listaAggiudicazioniStessoContratto.size() - aggiudicazioniDaEliminare.size()==1)
    		this.mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Modifica Contratto Multilotto").replace("$2", "l'eliminazione di tutte le Aggiudicazioni dal gruppo, oppure almeno due Aggiudicazioni devono restare nel gruppo."));
    }
    
    public List<String> fromArrayToList (String [] lista){
    	List<String> result = new ArrayList<String>();
    	if(lista!=null && lista.length>0){
        	for(String elem : lista)
        		result.add(elem);
    	}
    	return result; 
    }
	
	/******************************************************************************************
	 * Controlla  il valore del flag in base ai valori standard
	 * 
	 * @param val String
	 * @return boolean - true se uguale a valore SI o NO altrimenti false
	 */
	private boolean isFlag(String val){
		
		return val != null && (Costanti.FLAG_VALORE_SI.equals(val) || Costanti.FLAG_VALORE_NO.equals(val));
	}
	
	private boolean isEmpty(Object o){
		if( o instanceof Collection<?>)
			return ((Collection<?>)o).size() == 0;
		else 
			return(o == null || (o.toString().trim().length() == 0));
	}
	
	private boolean isNumber(String str){
		if(str == null)
			return false;
		for(int i=0; i<str.length(); i++ ){
	        if( str.charAt(i) < '0' || str.charAt(i) > '9' )
	            return false;
	}
	return true;
	}
	
}
