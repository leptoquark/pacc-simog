package it.avlp.simog.beans;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.MOTIVI_CANCELLAZIONE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class MotivazioniBean {
	private String codice;
	private String descrizione;
	private String notaObb;
	
	private List<MotivazioniBean> motivi = new ArrayList<MotivazioniBean>(); 
	
	
	public MotivazioniBean(final String codice, final String descrizione, final String notaObb) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.notaObb = notaObb;
	}

	public MotivazioniBean() {
	}

	public String getCodice() {
		return codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public String getNotaObb() {
		return notaObb;
	}

	/*
	 * caricamento motivazioni dal database
	 */
	public void loadAll(Connection conn, Logger logger, boolean perGara) {
		
		AccessiDB adb = new AccessiDB(conn, logger);
		
		try {			
			Map <String, String> motivi = adb.getTipologicaAlias(MOTIVI_CANCELLAZIONE.TABLE_NAME, 
						MOTIVI_CANCELLAZIONE.ID_MOTIVO_CANC, 
						MOTIVI_CANCELLAZIONE.DESCRIZIONE 
							+ " + ';' + " + MOTIVI_CANCELLAZIONE.GARA_LOTTO
							+ " + ';' + " + MOTIVI_CANCELLAZIONE.NOTA_OBBLIGATORIA, 
						MOTIVI_CANCELLAZIONE.DATA_FINE_VALIDITA, null, MOTIVI_CANCELLAZIONE.DESCRIZIONE);
			
			for (Iterator iter = motivi.keySet().iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				
				
				String descr = motivi.get(element);
				String[] campi = descr.split(";");
				String desc = campi[0];
				String gara = campi[1];
				String nota = campi[2];
				
				if ((perGara && "G".equals(gara)) || (!perGara && "L".equals(gara))){
					MotivazioniBean rek = new MotivazioniBean(element, desc, nota);
					this.motivi.add(rek);
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
	 
	
  public boolean isNotaObbligatoria(String codice) 
  {	  
	for (Iterator iter = this.motivi.iterator(); iter.hasNext();) {
		MotivazioniBean element = (MotivazioniBean) iter.next();
		if(element.getCodice().equals(codice) && Costanti.FLAG_VALORE_SI.equals(element.getNotaObb()))
			return true;
	}
	  
  	return false;
  }
  
  /*
   * caricamento motivazioni per il frontend
   */
  public Map<String,String> loadMotivazioni()
  {
  	Map<String,String> rs = new HashMap<String, String>();
  	for (Iterator iter = this.motivi.iterator(); iter.hasNext();) {
  		MotivazioniBean element = (MotivazioniBean) iter.next();
  		 rs.put(element.getCodice(), element.getDescrizione());
  	}
  	return rs;
  }
}
