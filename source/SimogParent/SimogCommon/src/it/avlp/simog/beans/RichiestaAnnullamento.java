package it.avlp.simog.beans;

import java.sql.Timestamp;

public class RichiestaAnnullamento {
	
	public final static String RICHIESTA_ACCETTATA = "A";
	public final static String RICHIESTA_RIFIUTATA = "R";

    public final static String MOTIVO_ESITO_DIRETTO = "Accettata da procedura SIMOG";

    private long id_richiesta;
	private Timestamp data_inizio;
	private String id_record;
	private Timestamp data_inizio_record;
	private String blocco;
	private String richiedente;
	private String motivo_richiesta;
	private Timestamp data_fine;
	private String esito;
	private String motivo_esito;
	private String decisore;
	private String id_lotto;
	private String id_info;
	private Timestamp data_inizio_info;
	private String id_pub;
	private Timestamp data_inizio_pub;
	
	private String cancellazione;
	private String idMotivo;
	
	public String getIdMotivo() {
      return idMotivo;
   }
   public void setIdMotivo(String idMotivo) {
      this.idMotivo = idMotivo;
   }
   public long getId_richiesta() {
		return id_richiesta;
	}
	public void setId_richiesta(long id_richiesta) {
		this.id_richiesta = id_richiesta;
	}
	public Timestamp getData_inizio() {
		return data_inizio;
	}
	public void setData_inizio(Timestamp data_inizio) {
		this.data_inizio = data_inizio;
	}
	public String getId_record() {
		return id_record;
	}
	public void setId_record(String id_record) {
		this.id_record = id_record;
	}
	public Timestamp getData_inizio_record() {
		return data_inizio_record;
	}
	public void setData_inizio_record(Timestamp data_inizio_record) {
		this.data_inizio_record = data_inizio_record;
	}
	public String getBlocco() {
		return blocco;
	}
	public void setBlocco(String blocco) {
		this.blocco = blocco;
	}
	public String getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}
	public String getMotivo_richiesta() {
		return motivo_richiesta;
	}
	public void setMotivo_richiesta(String motivo_richiesta) {
		this.motivo_richiesta = motivo_richiesta;
	}
	public Timestamp getData_fine() {
		return data_fine;
	}
	public void setData_fine(Timestamp data_fine) {
		this.data_fine = data_fine;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getMotivo_esito() {
		return motivo_esito;
	}
	public void setMotivo_esito(String motivo_esito) {
		this.motivo_esito = motivo_esito;
	}
	public String getDecisore() {
		return decisore;
	}
	public void setDecisore(String decisore) {
		this.decisore = decisore;
	}
	public String getId_lotto() {
		return id_lotto;
	}
	public void setId_lotto(String id_lotto) {
		this.id_lotto = id_lotto;
	}
	public String getId_info() {
		return id_info;
	}
	public void setId_info(String id_info) {
		this.id_info = id_info;
	}
	public Timestamp getData_inizio_info() {
		return data_inizio_info;
	}
	public void setData_inizio_info(Timestamp data_inizio_info) {
		this.data_inizio_info = data_inizio_info;
	}
	public String getId_pub() {
		return id_pub;
	}
	public void setId_pub(String id_pub) {
		this.id_pub = id_pub;
	}
	public Timestamp getData_inizio_pub() {
		return data_inizio_pub;
	}
	public void setData_inizio_pub(Timestamp data_inizio_pub) {
		this.data_inizio_pub = data_inizio_pub;
	}
	public String getCancellazione() {
		return cancellazione;
	}
	public void setCancellazione(String cancellazione) {
		this.cancellazione = cancellazione;
	}
	
	
}
