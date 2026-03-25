package it.avlp.simog.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.util.SimogProperties;

/**
 * Servlet implementation class SrvCaricaIniziative
 */
public class SrvVerificaIniziativeSoggAggr extends ServletBase implements ParametriServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		perform(request, response);
		
		
		
	}


	@Override
	protected void perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
        Connection currentActiveSession = null;
        List<String> listaCategorie = new ArrayList<String>();
        List<String> listaTerritori = new ArrayList<String>();
        List<IniziativaSoggAggr> iniziative = new ArrayList<IniziativaSoggAggr>();
        
        String saSelezionata = "";
        String cigAccordoQuadro = "";
        String idGara = "";
        String res="";
        String osservatorio = "";
        String cfAmministrazione = "";
        double importoLotto = 0;
        Gara g = null;
        
        //Recupero parametri dalla request
        String categorieSel = request.getParameter("categorieSel");
        String fromPage = request.getParameter("fromPage");
        
        
        boolean cigIsIniziativa = false;
        boolean cfIsSoggAggrOrEsclusa = false;
        System.out.println("TECHNIS 1");
        //Se la richiesta e' da gara, potrebbero esserci piu' categorie selezionate. Recupera gli altri dati dalla request
        if("gara".equals(fromPage)) {
           if (categorieSel != null) {
               categorieSel = categorieSel.length() > 0 ? categorieSel.substring(0, categorieSel.length()-1) : categorieSel;
	           //Recupera le categorie
		       listaCategorie = Arrays.asList(categorieSel.split("\\|"));
           }
           saSelezionata = request.getParameter("saSelezionata");
           cigAccordoQuadro = request.getParameter("cigAccordoQuadro");
        } else {
        	idGara = request.getParameter("idGara");
        	if (categorieSel != null) {
        	    listaCategorie.add(categorieSel);
        	}
        	String importoParam = request.getParameter("importo");
        	if (importoParam != null && !importoParam.isEmpty()) {
        	    try {
        	        importoLotto = Double.parseDouble(importoParam.replace(".", "").replace(",", "."));
        	    } catch (NumberFormatException e) {
        	        logger.warn("Parametro importo non numerico: " + importoParam);
        	    }
        	}
        	System.out.println("TECHNIS 2");
        }
        
        try {
			currentActiveSession = getSimogConnection(request.getSession().getId(),  getClass().getName());
	
	        if("gara".equals(fromPage)) {
	        	//Recupera il cf dell'amministrazione della sa selezionata
	    		 cfAmministrazione = currentUser.getCodiceFiscaleAmministrazioneByIdUfficio(saSelezionata);
	        } else {
	        	GaraManager gm = new GaraManager(currentActiveSession,logger);
	        	g = gm.getGara(Long.parseLong(idGara));
	        	cigAccordoQuadro = g.getCIG_ACC_QUADRO();
	        	cfAmministrazione = g.getCF_AMMINISTRAZIONE();
	        	System.out.println("TECHNIS 3");
	        }
        
	        
	      //Recupera l'osservatorio regionale della stazione appaltante selezionata (in caso di richiesta da gara)
			 if("gara".equals(fromPage)) 
			    osservatorio = currentUser.getOsservatori().get(cfAmministrazione);
			 else
				osservatorio = g.getID_OSSERVATORIO();

			if(osservatorio!=null && !ProfiloEnum.REGIONE_099.equals(osservatorio)) {
				if(osservatorio.length()==3)
					listaTerritori.add(osservatorio.substring(1));
			}    
     
        //Verifica che, se indicato, che il cig accordo quadro non faccia parte dell'elenco delle iniziative
		IniziativaManager im = new IniziativaManager(currentActiveSession,logger); 
	
			if(cigAccordoQuadro!=null && !"".equals(cigAccordoQuadro)) {
				cigIsIniziativa = im.getIniziative(cigAccordoQuadro, listaTerritori, listaCategorie, null,null, true).size() > 0;
				//Se non e' una iniziativa, verifica che il CIG indicato non sia in carico a un soggetto aggregatore
			if(!cigIsIniziativa)	
				cigIsIniziativa = im.checkCigSoggAggr(cigAccordoQuadro);
			}
			
		//Se il cig accordo quadro non e' stato indicato o non e' presente tra la lista delle iniziative o non e' a carico di un soggetto aggregatore
        if(!cigIsIniziativa) {
      
        	   //Verifica che la SA non sia un soggetto aggregatore oppure se fa parte delle SA escluse dal DPCM (es. Bolzano)
        	   cfIsSoggAggrOrEsclusa = im.checkSASoggAggr(cfAmministrazione,saSelezionata) || SimogProperties.getInstance().isCfEsclusa(cfAmministrazione);
        	   System.out.println("TECHNIS 6 cfIsSoggAggrOrEsclusa "+cfIsSoggAggrOrEsclusa);
        	   //Verifica se per questa categoria viene superata la soglia massima consentita
        	   if(!cfIsSoggAggrOrEsclusa && !"gara".equals(fromPage))
        		   cfIsSoggAggrOrEsclusa = im.callFGetEAGGCategorieSoglie(cfAmministrazione,categorieSel, importoLotto);
        	   
        	   System.out.println("TECHNIS 7 cfIsSoggAggrOrEsclusa "+cfIsSoggAggrOrEsclusa);
        	   
        	   if(!cfIsSoggAggrOrEsclusa) {
        	   
					iniziative = im.getIniziative(null, listaTerritori, listaCategorie, null, null, true);
					
			        for(IniziativaSoggAggr iniz : iniziative)
			        	res+=iniz.printDataToSimogWeb();
			        if("".equals(res))
			        	res+="NESSUNA INIZIATIVA";
        	   } else {
        		   res+="SA IS SOGG.AGGR.";
        	   }
	        } else
	        	res+="INIZIATIVA PRESENTE";
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().append(res);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append(res);
		} finally {
			closeConnection(request.getSession().getId(),getClass().getName());
		}
        System.out.println("TECHNIS end res "+res);
		response.getWriter().append(res);
		
	}



}
