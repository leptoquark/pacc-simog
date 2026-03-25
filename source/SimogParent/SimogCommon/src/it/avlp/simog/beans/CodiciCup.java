package it.avlp.simog.beans;

	/**
	 * Classe Bean (contenitore) che rappresenta una collaborazione
	 * oltre hai metodi getter e setter offre un metodo toString()
	 * per verificarne il contenuto
	 * 
	 * **/

public class CodiciCup {
	private String CUP;
	private String ID_RICHIESTA;
	private String DATI_DIPE;
	private String VALIDO;
   private String OK_UTENTE;
	
	//--- costruttore no-args come da specifiche rpc-1.1 ---//
	public CodiciCup(){}
//	public String toString(){
//		String dati = "";
//		dati += "Denominazione Azienda : "+azienda_denominazione	+"\r\n";
//		dati += "Codice Fiscale Azienda: "+azienda_codiceFiscale	+"\r\n";
//		dati += "Id osservatorio       : "+idOsservatorio			+"\r\n";
//		dati += "Denominazione ufficio : "+ufficio_denominazione	+"\r\n";
//		dati += "Id Ufficio            : "+ufficio_id				+"\r\n";
//		dati += "Profilo Ufficio       : "+ufficio_profilo		+"\r\n";
//		return dati;
//	}	

   public String getID_RICHIESTA() {
      return ID_RICHIESTA;
   }

   public void setID_RICHIESTA(String iD_RICHIESTA) {
      ID_RICHIESTA = iD_RICHIESTA;
   }

   public String getCUP() {
      return CUP;
   }

   public void setCUP(String cUP) {
      CUP = cUP == null ? null : cUP.toUpperCase();
   }

   public String getDATI_DIPE() {
      return DATI_DIPE;
   }

   public void setDATI_DIPE(String dATI_DIPE) {
      DATI_DIPE = dATI_DIPE;
   }

   public String getVALIDO() {
      return VALIDO;
   }

   public void setVALIDO(String vALIDO) {
      VALIDO = vALIDO;
   }

   public String getOK_UTENTE() {
      return OK_UTENTE;
   }

   public void setOK_UTENTE(String oK_UTENTE) {
      OK_UTENTE = oK_UTENTE;
   }
}
