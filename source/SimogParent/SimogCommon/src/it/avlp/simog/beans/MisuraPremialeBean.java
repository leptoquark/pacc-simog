package it.avlp.simog.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.MISURA_PREMIALE;

public class MisuraPremialeBean {
	private Long idMisuraPremiale;
	private String descrizione;
	private String dataInizioValidita;
	private String dataFineValidita;
	private String dataUltimaModifica;
	
	public String getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(String dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public String getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setIdMisuraPremiale(Long id) {
		idMisuraPremiale=id;
	}
	

	public Long getIdMisuraPremiale() {
		return idMisuraPremiale;
	}
	
	private List<MisuraPremialeBean> misure = new ArrayList<MisuraPremialeBean>(); 
	
	
	public MisuraPremialeBean(final Long idMisuraPremiale, 
							final String descrizione,
							final String dataUltimaModifica,
							final String dataFineValidita,
							final String dataInizioValidita) {
		this.idMisuraPremiale= idMisuraPremiale;
		this.descrizione = descrizione;
		this.dataUltimaModifica = dataUltimaModifica;
		this.dataFineValidita = dataFineValidita;
		this.dataInizioValidita = dataInizioValidita;
	}

	public MisuraPremialeBean() { }

	

	/*
	 * caricamento motivazioni dal database
	 */
	public void loadAllHere(Connection conn, Logger logger, boolean perGara) {
		
		AccessiDB adb = new AccessiDB(conn, logger);
		
		try {			 
			List<MisuraPremialeBean> misurePremiale = adb.getMisuraPremialeWithDataById(MISURA_PREMIALE.TABLE_NAME,null);
			
			for (MisuraPremialeBean misuraPremialeBean : misurePremiale) {
				
					this.misure.add(misuraPremialeBean);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
	 
  
  /*
   * caricamento motivazioni per il frontend
   */
  public Map<String,String> loadMisuraPremiale()
  {
  	Map<String,String> rs = new HashMap<String, String>();
  	for (MisuraPremialeBean misuraPremialeBean : misure) {
 		 rs.put(misuraPremialeBean.getIdMisuraPremiale().toString(), misuraPremialeBean.getDescrizione());

  	}
  	return rs;
  }
}
