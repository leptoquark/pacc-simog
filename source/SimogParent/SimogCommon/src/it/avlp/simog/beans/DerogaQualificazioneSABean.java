package it.avlp.simog.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//3.04.9 MEV 40610
public class DerogaQualificazioneSABean {
	private Long idDerogaQualificazioneSA;
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

	
	
	
	public Long getIdDerogaQualificazioneSA() {
		return idDerogaQualificazioneSA;
	}

	public void setIdDerogaQualificazioneSA(Long idDerogaQualificazioneSA) {
		this.idDerogaQualificazioneSA = idDerogaQualificazioneSA;
	}




	private List<DerogaQualificazioneSABean> deroghe = new ArrayList<DerogaQualificazioneSABean>(); 
	
	
	public DerogaQualificazioneSABean(final Long idDerogaQualificazioneSA, 
							final String descrizione,
							final String dataUltimaModifica,
							final String dataFineValidita,
							final String dataInizioValidita) {
		this.idDerogaQualificazioneSA= idDerogaQualificazioneSA;
		this.descrizione = descrizione;
		this.dataUltimaModifica = dataUltimaModifica;
		this.dataFineValidita = dataFineValidita;
		this.dataInizioValidita = dataInizioValidita;
	}

	public DerogaQualificazioneSABean() { }

	

	/*
	 * caricamento motivazioni dal database
	 */
//	public void loadAllHere(Connection conn, Logger logger, boolean perGara) {
//		
//		AccessiDB adb = new AccessiDB(conn, logger);
//		
//		try {			 
//			List<DerogaQualificazioneSABean> misurePremiale = adb.getMisuraPremialeWithDataById(MISURA_PREMIALE.TABLE_NAME,null);
//			
//			for (DerogaQualificazioneSABean misuraPremialeBean : misurePremiale) {
//				
//					this.deroghe.add(misuraPremialeBean);
//			}
//			
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}	
//	}
	 
  
  /*
   * caricamento motivazioni per il frontend
   */
  public Map<String,String> loadDerogaQualificazioneSA()
  {
  	Map<String,String> rs = new HashMap<String, String>();
  	for (DerogaQualificazioneSABean derogaQualificazioneSA : deroghe) {
 		 rs.put(derogaQualificazioneSA.getIdDerogaQualificazioneSA().toString(), derogaQualificazioneSA.getDescrizione());

  	}
  	return rs;
  }
}
