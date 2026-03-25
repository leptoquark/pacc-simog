package it.avlp.simog.beans;




import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.util.SimogProperties;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


	/**
	 * Ogni oggetto di questa classe viene utilizzato
	 * per la memorizzazione temporanea e la successiva
	 * archiviazione delle informazioni relative ad una gara
	 * ed ai lotti relativi
	 */

public class Gara implements GARA, Cloneable {

	private static final long serialVersionUID = 1L;
	
//	private String data_comunicazione = null;
	private long idGara = 0L;
	private String cfUtente = null;
	private String oggetto = null;
	private String dataCreazione = null;
	private String cfAmministrazione = null;
	private String idStazioneAppaltante = null;

	private String denom_amministrazione;
	private String denom_stazione_appaltante;	

	private String id_osservatorio;
	private String DATA_CANCELLAZIONE_GARA = null;
	private String DATA_COMUNICAZIONE = null;
	private String DATA_INIB_PAGAMENTO = null;
	private String DATA_TERMINE_PAGAMENTO = null;
	private String DATA_CONFERMA = null;
	
	private long ID_STATO = 0L;
	private BigDecimal IMPORTO_GARA;
	private BigDecimal IMPORTO_SA;
	
	private String TIPO_SCHEDA_GARA;
	private int ID_MODO_GARA;
	private int ID_MODO_REAL;
	private int ID_MOTIVAZIONE_CANC;
	
	private String NOTE_CANC_GARA;
	private String CIG_ACC_QUADRO;
	
	//MAC 42787  3.04.9.2
	private String LINK_AFFIDAMENTO_DIRETTO;
   
	//gm aggiunto per pubblicazione bando
	private String DATA_PERFEZIONAMENTO_BANDO;
	
	//gm nuovo codice pubblicazione bando 3.0
	private long idPubblicazione;
	private Timestamp dataInizioPubblicazione;
	//gm fine nuovo codice pubblicazione bando 3.0
	
	//gm nuovo campo simog 3.04
	private Integer numeroLotti = new Integer(0);
	
	//nuovo campo simog 22/02/2019
	private Integer durataGiorni = new Integer(0);

	//gm nuovo campo simog 3.06
	private long idMotivoVar;
	
	// PP rgani costituzionali
	private boolean organoCost;
	
	// is3028_RFWSSC00Active
	private String PROVV_PRESA_CARICO;
	
	//is3031_ESCL_AVCPASS
	private String ESCLUSO_AVCPASS;
	
	// INT85
	private int sceltaLegge89;
	private String tipoSA;
	
   // INT87
   private String URGENZA_DL133;

   // is30350_RFWEBGL01Active
   private int COD_MOTIVO_EAGG;
   private List<String> catMerc = new ArrayList();
   
   // TICKET ALM #664
   private int ID_SVOLGIMENTO;
   // FINE TICKET ALM #664
   
   //TICKET ALM #3832
   private int ID_ESTREMA_URGENZA;
   //FINE TICKET ALM #3832
   
   //TICKET ALM #3834
   private int ID_ALLEGATO_IX;
   //FINE TICKET ALM #3834
   
   //TICKET ALM #659 - 3.04.4
   //Campi funzione delega
   private String flagSAAgente;
   private int ID_F_DELEGATE;
   private String CF_AMM_AGENTE;
   private String DEN_AMM_AGENTE;

   //TICKET ALM #12088 - 3.04.5
   private String codiceAusa;
   
   public Gara(String dataCreazione, long idGara, String cfUtente, String oggetto, String cfSA) {
		super();
	this.dataCreazione = dataCreazione;
	this.idGara = idGara;
	this.cfUtente = cfUtente;
	this.oggetto = oggetto;
	this.idStazioneAppaltante = cfSA;
	}



	public Gara() {}
	
    public Gara(long idGara) { this.idGara = idGara; }

    public long getId_Gara() {
		return idGara;
	}
	public void setIdGara(long idGara) {
		this.idGara = idGara;
	}
	
	public String getData_creazione() {
		return dataCreazione;
	}


//	public String getData_comunicazione() {
//		return data_comunicazione;
//	}

	
	public String getCF_UTENTE() {
		return cfUtente;
	}


	public String getOggetto() {
		return oggetto;
	}

	
	public void setData_creazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


//	public void setData_comunicazione(String data_comunicazione) {
//		this.data_comunicazione = data_comunicazione;
//	}

	
	public void setCF_UTENTE(String cfUtente) {
		this.cfUtente = cfUtente.toUpperCase();
	}


	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}



	public String getCF_AMMINISTRAZIONE() {
		return cfAmministrazione;
	}


	public void setCF_AMMINISTRAZIONE( String cfAmministrazione) {
		this.cfAmministrazione = cfAmministrazione;
	}

	public void setID_STAZIONE_APPALTANTE( String idStazioneAppaltante ) {
		this.idStazioneAppaltante = idStazioneAppaltante;
	}

	public String getID_STAZIONE_APPALTANTE() {
		return idStazioneAppaltante;
	}

	public String getDENOM_STAZIONE_APPALTANTE() {
		return denom_stazione_appaltante;
	}

	public String getDENOM_AMMINISTRAZIONE() {
		return denom_amministrazione;
	}
		
	/**
	 * @param denom_amministrazione The denom_amministrazione to set.
	 */
	public void setDENOM_AMMINISTRAZIONE(String denom_amministrazione) {
		this.denom_amministrazione = denom_amministrazione;
	}

	/**
	 * @param denom_stazione_appaltante The denom_stazione_appaltante to set.
	 */
	public void setDENOM_STAZIONE_APPALTANTE(String denom_stazione_appaltante) {
		this.denom_stazione_appaltante = denom_stazione_appaltante;
	}


	public String getID_OSSERVATORIO(){
		return id_osservatorio;
	}

	public void setID_OSSERVATORIO(String id_osservatorio) {
		this.id_osservatorio = id_osservatorio;
	}
	

	public String getDATA_CANCELLAZIONE_GARA() {
		return DATA_CANCELLAZIONE_GARA;
	}



	public void setDATA_CANCELLAZIONE_GARA(String data_cancellazione_gara) {
		DATA_CANCELLAZIONE_GARA = data_cancellazione_gara;
	}



	public String getDATA_COMUN() {
		return DATA_COMUNICAZIONE;
	}



	public void setDATA_COMUN(String data_comunicazione) {
		DATA_COMUNICAZIONE = data_comunicazione;
	}



	public String getDATA_INIB_PAGAM() {
		return DATA_INIB_PAGAMENTO;
	}



	public void setDATA_INIB_PAGAM(String data_inib_pagamento) {
		DATA_INIB_PAGAMENTO = data_inib_pagamento;
	}



	public long getID_STATO_GARA() {
		return ID_STATO;
	}



	public void setID_STATO_GARA(long id_stato) {
		ID_STATO = id_stato;
	}



	public BigDecimal getIMPORTO_GARA() {
		return IMPORTO_GARA;
	}



	public void setIMPORTO_GARA(BigDecimal importo_gara) {
		IMPORTO_GARA = importo_gara;
	}



	public BigDecimal getIMPORTO_SA_GARA() {
		return IMPORTO_SA;
	}



	public void setIMPORTO_SA_GARA(BigDecimal importo_sa) {
		IMPORTO_SA = importo_sa;
	}



	public String getDATA_TERMINE_PAGAMENTO() {
		return DATA_TERMINE_PAGAMENTO;
	}



	public void setDATA_TERMINE_PAGAMENTO(String data_termine_pagamento) {
		DATA_TERMINE_PAGAMENTO = data_termine_pagamento;
	}



	public String getDATA_CONFERMA_GARA() {
		return DATA_CONFERMA;
	}



	public void setDATA_CONFERMA_GARA(String data_conferma) {
		DATA_CONFERMA = data_conferma;
	}
	

	public int getID_MODO_GARA() {
		return ID_MODO_GARA;
	}



	public void setID_MODO_GARA(int id_modo_gara) {
		ID_MODO_GARA = id_modo_gara;
	}



	public int getID_MODO_REAL() {
		return ID_MODO_REAL;
	}



	public void setID_MODO_REAL(int id_modo_real) {
		ID_MODO_REAL = id_modo_real;
	}



	public int getID_MOTIVAZIONE_CANC() {
		return ID_MOTIVAZIONE_CANC;
	}



	public void setID_MOTIVAZIONE_CANC(int id_motivazione_canc) {
		ID_MOTIVAZIONE_CANC = id_motivazione_canc;
	}



	public String getTIPO_SCHEDA_GARA() {
		return TIPO_SCHEDA_GARA;
	}



	public void setTIPO_SCHEDA_GARA(String tipo_scheda_gara) {
		TIPO_SCHEDA_GARA = tipo_scheda_gara;
	}



	public String getNOTE_CANC_GARA() {
		return NOTE_CANC_GARA;
	}



	public void setNOTE_CANC_GARA(String note_canc_gara) {
		NOTE_CANC_GARA = note_canc_gara;
	}



	public String getCIG_ACC_QUADRO() {
		return CIG_ACC_QUADRO;
	}

	public void setCIG_ACC_QUADRO(String cig_acc_quadro) {
		CIG_ACC_QUADRO = cig_acc_quadro == null ? cig_acc_quadro : cig_acc_quadro.trim();  // PP patch accordo quadro a blank
	}
    //gm aggiunto per pubblicazione bando
	public String getDATA_PERFEZIONAMENTO_BANDO() {
		return DATA_PERFEZIONAMENTO_BANDO;
	}
	
    //gm aggiunto per pubblicazione bando
	public void setDATA_PERFEZIONAMENTO_BANDO(String data_perf_bando) {
		DATA_PERFEZIONAMENTO_BANDO = data_perf_bando;
	}

	public long getIdPubblicazione() {
		return idPubblicazione;
	}
	public void setIdPubblicazione(long idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}
	public Timestamp getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}
	public void setDataInizioPubblicazione(Timestamp dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	
	public Integer getNumeroLotti(){
		return this.numeroLotti;
	}
	public void setNumeroLotti(Integer numeroLotti) {
		this.numeroLotti = numeroLotti;
	}
	
	public Integer getDurataGiorni(){
		return this.durataGiorni;
	}
	public void setDurataGiorni(Integer durataGiorni) {
		this.durataGiorni = durataGiorni;
	}

	public long getIdMotivoVariazioneSA() {
		return idMotivoVar;
	}
	public void setIdMotivoVariazioneSA(long idMotivoVar) {
		this.idMotivoVar = idMotivoVar;
	}

	public boolean isOrganoCost() {
		return organoCost;
	}

	public void setOrganoCost(boolean organoCost) {
		this.organoCost = organoCost;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

   public String getPROVV_PRESA_CARICO() {
      return PROVV_PRESA_CARICO;
   }

   public void setPROVV_PRESA_CARICO(String pROVV_PRESA_CARICO) {
      PROVV_PRESA_CARICO = pROVV_PRESA_CARICO;
   }

   public String getESCLUSO_AVCPASS() {
      return ESCLUSO_AVCPASS;
   }

   public void setESCLUSO_AVCPASS(String eSCLUSO_AVCPASS) {
      ESCLUSO_AVCPASS = eSCLUSO_AVCPASS;
   }

   // INT85
   public void setSCELTA_LEGGE89(int sceltaLegge89) {
      this.sceltaLegge89 = sceltaLegge89;
   }
  
   public int getSCELTA_LEGGE89() {
      return sceltaLegge89;
   }

   public String getTIPOSA_BDNCP() {
      return tipoSA;
   }

   public void setTIPOSA_BDNCP(String tipoSA) {
      this.tipoSA = tipoSA;
   }
   // INT85 fine


   // INT87
   public String getURGENZA_DL133() {
      return URGENZA_DL133;
   }
   public void setURGENZA_DL133(String urgenzaDL133) {
      this.URGENZA_DL133 = urgenzaDL133;
   }
   // INT87 FINE

   //is30350_RFWEBGL01Active
   public int getCOD_MOTIVO_EAGG (){
      return COD_MOTIVO_EAGG;
   }
   
   public void setCOD_MOTIVO_EAGG(int codMotivoEagg){
      this.COD_MOTIVO_EAGG = codMotivoEagg;
   }



   public List<String> getCatMerc() {
      return catMerc;
   }



   public void setCatMerc(List<String> list) {
      this.catMerc = list;
   }

   //TICKET ALM #664
   public int getID_SVOLGIMENTO() {
	return ID_SVOLGIMENTO;
    } 

	public void setID_SVOLGIMENTO(int idSvolgimento) {
		ID_SVOLGIMENTO = idSvolgimento;
	}
   //FINE TICKET ALM #664

 //TICKET ALM #3832
   public int getID_ESTREMA_URGENZA() {
	return ID_ESTREMA_URGENZA;
    } 

	
	
	public void setID_ESTREMA_URGENZA(int idEstremaUrgenza) {
		ID_ESTREMA_URGENZA = idEstremaUrgenza;
	}
   //FINE TICKET ALM #3832
	
	
	   //TICKET ALM #3834
	   public int getID_ALLEGATO_IX() {
		return ID_ALLEGATO_IX;
	    } 

		
		
		public void setID_ALLEGATO_IX(int idAllegatoIX) {
			ID_ALLEGATO_IX = idAllegatoIX;
		}
	   //FINE TICKET ALM #3834

   public String[] getCatMercArray() {
      String[] ret = catMerc.toArray(new String[catMerc.size()]);
      return ret;
   }


    //TICKET ALM #659 - 3.04.4
	public int getID_F_DELEGATE() {
		return ID_F_DELEGATE;
	}



	public void setID_F_DELEGATE(int iD_F_DELEGATE) {
		ID_F_DELEGATE = iD_F_DELEGATE;
	}



	public String getFlagSAAgente() {
		return flagSAAgente;
	}



	public void setFlagSAAgente(String flagSAAgente) {
		this.flagSAAgente = flagSAAgente;
	}



	public String getCF_AMM_AGENTE() {
		return CF_AMM_AGENTE;
	}



	public void setCF_AMM_AGENTE(String cF_AMM_AGENTE) {
		CF_AMM_AGENTE = cF_AMM_AGENTE;
	}



	public String getDEN_AMM_AGENTE() {
		return DEN_AMM_AGENTE;
	}



	public void setDEN_AMM_AGENTE(String dEN_AMM_AGENTE) {
		DEN_AMM_AGENTE = dEN_AMM_AGENTE;
	}

	//FINE TICKET ALM #659 - 3.04.4

   
	public String getCodiceAusa() {
		return codiceAusa;
	}



	public void setCodiceAusa(String codiceAusa) {
		this.codiceAusa = codiceAusa;
	}


	
	//TICKET 31061
	public boolean isPPPGara() {
		String[] PPPGare = SimogProperties.getInstance().getIdModRealPPP().split(";");
		
		boolean isPPPGara = false;
		for (String PPPGara: PPPGare) {
			if (Integer.parseInt(PPPGara) == this.getID_MODO_REAL()) {
				isPPPGara = true;
				break;
			}
		}
		
		return isPPPGara;
	}


	   //MAC 42787  3.04.9.2
	public String getLINK_AFFIDAMENTO_DIRETTO() {
		return LINK_AFFIDAMENTO_DIRETTO;
	}


	   //MAC 42787  3.04.9.2
	public void setLINK_AFFIDAMENTO_DIRETTO(String lINK_AFFIDAMENTO_DIRETTO) {
		LINK_AFFIDAMENTO_DIRETTO = lINK_AFFIDAMENTO_DIRETTO;
	}
	
}

