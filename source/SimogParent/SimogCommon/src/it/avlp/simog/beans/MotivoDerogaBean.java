package it.avlp.simog.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;

public class MotivoDerogaBean {
	private Long idMotivoDeroga;
	private String dataUltimaModifica;
	private String dataInizioValidita;
	private String dataFineValidita;
	private String descrizione;
	
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

	public List<MotivoDerogaBean> getMotivi() {
		return motivi;
	}

	public void setMotivi(List<MotivoDerogaBean> motivi) {
		this.motivi = motivi;
	}

	public Long getIdMotivoDeroga() {
		return idMotivoDeroga;
	}
	
	public void setIdMotivoDeroga(Long idMotivoDeroga) {
		this.idMotivoDeroga = idMotivoDeroga;
	}



	private List<MotivoDerogaBean> motivi = new ArrayList<MotivoDerogaBean>(); 
	
	
	public MotivoDerogaBean(final Long idMotivoDeroga, 
							final String descrizione,
							final String dataUltimaModifica,
							final String dataFineValidita,
							final String dataInizioValidita) {
		this.idMotivoDeroga = idMotivoDeroga;
		this.descrizione = descrizione;
		this.dataUltimaModifica = dataUltimaModifica;
		this.dataFineValidita = dataFineValidita;
		this.dataInizioValidita = dataInizioValidita;
	}

	public MotivoDerogaBean() { }

	

	/*
	 * caricamento motivazioni dal database
	 */
	public void loadAllMotivo(Connection conn, Logger logger, boolean perGara) {
		
		AccessiDB adb = new AccessiDB(conn, logger);
		
		try {			
			Map <String, String> motivi = adb.getMotivoDerogaWithData(MOTIVO_DEROGA.TABLE_NAME, 
																	  MOTIVO_DEROGA.ID_MOTIVO, 
																	  MOTIVO_DEROGA.DESCRIZIONE, 
																	  MOTIVO_DEROGA.DATA_INIZIO_VALIDITA, 
																	  MOTIVO_DEROGA.DATA_FINE_VALIDITA, 
																	  MOTIVO_DEROGA.DATA_ULTIMA_MODIFICA);
			
			for (Iterator iter = motivi.keySet().iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				
				
				String motiviS = motivi.get(element);
				String[] campi = motiviS.split(";");
				//check campi che passa
				Long id = Long.parseLong(campi[0]);
				String desc = campi[1];
				String dataI = campi[2];
				String dataF = campi[3];
				String dataU = campi[4];
				//TODO: Controllare le condizioni da usare
				//if ((perGara && "G".equals(gara)) || (!perGara && "L".equals(gara))){
					MotivoDerogaBean rek = new MotivoDerogaBean(id, desc, dataI, dataF, dataU);
					this.motivi.add(rek);
				//}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
	 
  
  /*
   * caricamento motivazioni per il frontend
   */
  public Map<String,String> loadMotiviDeroga()
  {
  	Map<String,String> rs = new HashMap<String, String>();
  	for (Iterator iter = this.motivi.iterator(); iter.hasNext();) {
  		MotivoDerogaBean element = (MotivoDerogaBean) iter.next();
  		 rs.put(element.getIdMotivoDeroga().toString(), element.getDescrizione());
  	}
  	return rs;
  }
}
