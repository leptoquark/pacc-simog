package it.avlp.simog.beans;


import java.sql.Timestamp;

/**
 * Tiene memoria dei parametri con i quali sono stati effettuate
 * le cancellazioni
 * 
 * @author vletizia
 *
 */
public class IdsScheda {

	private String cui;
	private String cig;
	private String idLocale;	
	private String idScheda;
	private long idAggiudicazione;
	private IdentificativoSchede identificativo;

	private long idRecordDb;
	private Timestamp dataInizioRecord;

	public String toString(){
		String toString = "Riepilogo IdsScheda: \r\n";
		toString += "\t nomeScheda: "+ (identificativo == null ? "IDNULL???" : identificativo.getNomeScheda())+"\r\n";
		toString += "\t cui: "+cui+"\r\n";
		toString += "\t cig: "+cig+"\r\n";
		toString += "\t idLocale: "+idLocale+"\r\n";
		toString += "\t idScheda: "+idScheda+"\r\n";
		toString += "\t idAggiudicazione: "+idAggiudicazione+"\r\n";
		return toString;
	}
	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	public String getIdScheda() {
		return idScheda;
	}

	public void setIdScheda(String idScheda) {
		this.idScheda = idScheda;
	}

	public IdentificativoSchede getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(IdentificativoSchede identificativo) {
		this.identificativo = identificativo;
	}

	public long getIdaggiudicazione() {
		return idAggiudicazione;
	}

	public void setIdaggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public long getIdRecordDb() {
		return idRecordDb;
	}
	public void setIdRecordDb(long idRecordDb) {
		this.idRecordDb = idRecordDb;
	}
	public Timestamp getDataInizioRecord() {
		return dataInizioRecord;
	}
	public void setDataInizioRecord(Timestamp dataInizioRecord) {
		this.dataInizioRecord = dataInizioRecord;
	}

	
	
}
