package it.avlp.simog.beans;

	/**
	 * Classe Bean (contenitore) che rappresenta una collaborazione
	 * oltre hai metodi getter e setter 
	 * per verificarne il contenuto
	 * 
	 * **/

public class CUPLOTTO {
	private String CIG;
	private CodiciCup[] CODICICUP;
	
	//--- costruttore no-args come da specifiche rpc-1.1 ---//
	public CUPLOTTO(){}

   public String getCIG() {
      return CIG;
   }

   public void setCIG(String cIG) {
      CIG = cIG;
   }

   public CodiciCup[] getCODICICUP() {
      return CODICICUP;
   }

   public void setCODICICUP(CodiciCup[] cODICICUP) {
      CODICICUP = cODICICUP;
   }

}
