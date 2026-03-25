package it.avlp.simog.actions;


import it.avcp.simog.managers.invitati.InvitatiManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.InvitatoBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class ElencoInvitatiAction extends BaseAction{
	
	
	protected AllValidationBeans mEccezioni;
	
	
	public ElencoInvitatiAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		this.mEccezioni = new AllValidationBeans();
	}

	
	public void cancellaInvitato(String id){
		
		Long idGara;
		idGara=Long.parseLong(id);
		InvitatiManager manager=new InvitatiManager(connection, logger);
		
		manager.cancellaInvitato(idGara);

	}
	
	public void inserisciInvitato(InvitatoBean invitato){
		
		InvitatiManager manager=new InvitatiManager(connection, logger);
		
		manager.inserisciInvitato(invitato);

	}
	
	public ArrayList<InvitatoBean> caricaInviati(String id){
		
		ArrayList<InvitatoBean> invitati=new ArrayList<InvitatoBean>();
		
		InvitatiManager manager=new InvitatiManager(connection, logger);
		
		long idG;
		idG=Long.parseLong(id);
		invitati=manager.carica(idG);
		
		return invitati;
		
	}
	

	/*public ElencoInvitatiAction(Connection activeConnection, Logger logger) {
		this.connection = activeConnection;
		this.logger = logger;
		this.mEccezioni = new AllValidationBeans();
	}*/
	
	
	public ArrayList<InvitatoBean> getBean(HttpServletRequest request) throws ActionException{
		
		
		int nrInvitati = getIntReqParameter(request, 0,PSBD.NR_RIGHE_AFFIDATARI);
		ArrayList<InvitatoBean> invitati = new ArrayList<InvitatoBean>(nrInvitati);
		String prefix = "row" + PSBD.AGGIUDICATARIO;
		
		InvitatoBean nuovoInvitato = null;
		int daleggere = nrInvitati;
		int i = 0;
		while(daleggere>0){
			String name = prefix + i + PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE;
			String idInvitString = request.getParameter(name);
			
			boolean found = false;//Ticket ALM #1490
			if(idInvitString != null){
				found=true;//Ticket ALM #1490
				nuovoInvitato = new InvitatoBean();
				
				long idInvitatoParamValue = "".equalsIgnoreCase(idInvitString)
				? 0 : Long.parseLong(idInvitString);
				
				SoggettoPartecipanteBean nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(idInvitatoParamValue);
				nuovoSoggettoPartecipante.setDataInizioSogg(getTimestampReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG));
				nuovoSoggettoPartecipante.setDenominazione(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_AGG_DENOMINAZIONE));
				nuovoSoggettoPartecipante.setCodiceFiscale(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO));
				nuovoSoggettoPartecipante.setId_stato(getStringReqParameter(request, "", prefix + i + PSBD.FIELD_NAME_AGG_ID_PAESE));
			
				//logger.debug(" "+prefix + i + PSBD.FIELD_NAME_AGG_ID_PAESE+" = "+nuovoSoggettoPartecipante.getId_stato());
				if(nuovoSoggettoPartecipante.getId_stato() == Costanti.CODICE_STATO_ITALIANO)	//X-XX: Verificare perche' arriva "IT" dal frontend
					nuovoSoggettoPartecipante.setId_stato("");
				
			
				String nome=(String) request.getAttribute(ParametriServlet.SESSION_ID_GARA);
				long idGara=Long.parseLong(nome);
		    	nuovoInvitato.setIdGara(idGara);
		    	nuovoInvitato.setSoggettoPartecipante(nuovoSoggettoPartecipante);
		        invitati.add(nuovoInvitato);
			}
			
			if(found)//Ticket ALM #1490
			    daleggere--;
			
			i++;
         }
		return invitati;
	  }
	


	public void valida(ArrayList<InvitatoBean> listaInvitati){
		
		int iAgg=0;
		int uguali = 0;
		for(InvitatoBean invitato : listaInvitati){
			
			try{
				iAgg++;
				if((isEmpty(invitato.getSoggettoPartecipante().getId_stato()) 
		    			|| Costanti.CODICE_STATO_ITALIANO.equals(invitato.getSoggettoPartecipante().getId_stato()))
		    		&& !validaPartitaIva(invitato.getSoggettoPartecipante().getCodiceFiscale()) 
		    		&& !validaCodiceFiscale(invitato.getSoggettoPartecipante().getCodiceFiscale())
		    	)
			    	throw new Exception();
			    
	        }
		    catch (Exception e) {
		        mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "lista invitati-Partita iva- Codice Fiscale"),iAgg);
	        }
		    if(invitato.getSoggettoPartecipante()==null || invitato.getSoggettoPartecipante().getIdSoggettoPartecipante()==0 || invitato.getSoggettoPartecipante().getDataInizioSogg()==null)
		        mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_214.replace("$1", "lista invitati-Partita iva"),iAgg);
		    ArrayList<InvitatoBean> invitati2 = listaInvitati;
			for(InvitatoBean invitato2 : invitati2){
				if(invitato.getSoggettoPartecipante().getIdSoggettoPartecipante()==invitato2.getSoggettoPartecipante().getIdSoggettoPartecipante())
					uguali++;
			}
			//gestione dei duplicati
			if(uguali>1)
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Soggetto partecipante"),iAgg);	
			uguali = 0;
			
		}
		
	}
	
	/*******************************************************************************************
	 * verifica validita della partita iva, controlla che il formato della partita iva 
	 * inserito sia corretto.
	 * 
	 * @param pi String partita iva da validare
	 * @return boolean - true se valida, altrimenti false
	 */
	
	public boolean validaPartitaIva(String pi)
	{    
		if(pi == null) return false;
		
	    int i, c, s;
	    if( pi.length() == 0 )  return true;
	    if (!validateLength(pi,11))
	        return false;
	    
	    if (!isNumber(pi)) 
	    	return false;
	    
	    s = 0;
	    for( i=0; i<=9; i+=2 )
	        s += pi.charAt(i) - '0';
	    for( i=1; i<=9; i+=2 ){
	        c = 2*( pi.charAt(i) - '0' );
	        if( c > 9 )  c = c - 9;
	        s += c;
	    }
	    if( ( 10 - s%10 )%10 != pi.charAt(10) - '0' )
	        return false;
	    return true;
	}
	
	
	/*************************************************************************************************
	 * Determina se la Stringa in ingresso abbia la lunghezza indicata
	 * 
	 * @param value String contenente la stringa da valutare
	 * @param requiredLength int indica la lunghezza 
	 * @return boolean - true se la stringa ha una lunghezza uguale a quella 
	 * indicata in requiredlength, false altrimenti
	 */
	
	protected boolean validateLength(String value, int requiredLength){
		return (value.length() == requiredLength);
	}
	
	
	/*************************************************************************************************
	 * Verifica se la stringa in ingresso contenga un numero, usare solo per numeri interi!
	 * 
	 * @param str String
	 * @return boolean - true se la stringa rappresenta un numero decimale, False altrimenti
	 */
	
	protected boolean isNumber(String str){
		if(str == null)
			return false;
		for(int i=0; i<str.length(); i++ ){
	        if( str.charAt(i) < '0' || str.charAt(i) > '9' )
	            return false;
	}
	return true;
	}
	
	
	/**********************************************************************************************
	 * Accerta se un oggetto sia vuoto o meno. 
	 * 
	 * @param o Object
	 * @return boolean
	 */
	protected boolean isEmpty(Object o){
		return isEmpty(o,false);
	}
	
	
	/**********************************************************************************************
	 * Accerta se un Object sia vuoto o meno. 
	 * 
	 * @param o Object
	 * @param orZero boolean 
	 * @return boolean
	 */
	private boolean isEmpty(Object o , boolean orZero){
		if( o instanceof Collection<?>)
			return ((Collection<?>)o).size() == 0;
		else 
			return(o == null || (o.toString().trim().length() == 0));
	}
	
	
	/*************************************************************************************************
	 * Validatore per il codice fiscale, si occupa di verificare che il formato del codice fiscale 
	 * inserito sia corretto.
	 * 
	 * @param codiceFiscale String contenente il codice fiscale da valutare
	 * @return boolean - true se il formato &egrave; corretto, false se il formato non e' corretto
	 */
	protected boolean validaCodiceFiscale(String codiceFiscale) {
	    int i, s, c;
	    String cf2;
	    int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
	        11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };

	    if( codiceFiscale.length() == 0 ) return true;
	    if (!validateLength(codiceFiscale,16))
	        return false;
	    cf2 = codiceFiscale.toUpperCase();
	    for( i=0; i<16; i++ ){
	        c = cf2.charAt(i);
	        if( ! ( c>='0' && c<='9' || c>='A' && c<='Z' ) )
	            return false;
	    }
	    s = 0;
	    for( i=1; i<=13; i+=2 ){
	        c = cf2.charAt(i);
	        if( c>='0' && c<='9' )
	            s = s + c - '0';
	        else
	            s = s + c - 'A';
	    }
	    for( i=0; i<=14; i+=2 ){
	        c = cf2.charAt(i);
	        if( c>='0' && c<='9' )     c = c - '0' + 'A';
	        s = s + setdisp[c - 'A'];
	    }
	    if( s%26 + 'A' != cf2.charAt(15) )
	        return false;
	    return true;
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
	
	
	public void inserisciInvitati(){
		
	}
	
}
