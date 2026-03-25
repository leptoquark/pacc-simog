package it.avlp.simog.beans;

public class LuogoEsecuzione  {
	
	
	protected String luogoEsecIstatNuts = null;
	protected String codLuogoEsecuzione = null;
	
	
	public LuogoEsecuzione(String luogoEsecIstatNuts,String codLuogoEsecuzione) {
           this.luogoEsecIstatNuts = luogoEsecIstatNuts	;
           this.codLuogoEsecuzione = codLuogoEsecuzione ;	
	}

    public LuogoEsecuzione() {}
	
	
	public String getCodLuogoEsecuzione() {
		return codLuogoEsecuzione;
	}


	public void setCodLuogoEsecuzione(String codLuogoEsecuzione) {
		this.codLuogoEsecuzione = codLuogoEsecuzione;
	}


	public String getLuogoEsecIstatNuts() {
		return luogoEsecIstatNuts;
	}


	public void setLuogoEsecIstatNuts(String luogoEsecIstatNuts) {
		this.luogoEsecIstatNuts = luogoEsecIstatNuts;
	}



	


}
