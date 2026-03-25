package it.avlp.simog.beans;

	/**
	 * Classe Bean (contenitore) che rappresenta una collaborazione
	 * oltre hai metodi getter e setter 
	 * per verificarne il contenuto
	 * 
	 * **/

public class TIPIAPPALTO {
	private String[] TIPOAPPALTO;
	
	//--- costruttore no-args come da specifiche rpc-1.1 ---//
	public TIPIAPPALTO(){}

   public String[] getTIPOAPPALTO() {
      return TIPOAPPALTO;
   }

   public void setTIPOAPPALTO(String[] tIPOAPPALTO) {
      TIPOAPPALTO = tIPOAPPALTO;
   }


}
