package it.avlp.simog.tags;

import javax.servlet.jsp.tagext.TagSupport;

public class ViewRulesTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoScheda;
	private String mostraCon;
	
	
	public int doStartTag(){
		int i = 0;
		if(tipoScheda.trim().equals("") || mostraCon.length() % 2 == 1)
			return EVAL_BODY_INCLUDE;
		while(i +2 <= mostraCon.length()){
			
			if(mostraCon.substring(i, i+2).equalsIgnoreCase(tipoScheda))
				return EVAL_BODY_INCLUDE;
			
			i+=2;
		}
		
		
		return SKIP_BODY;
	}

	
	
	public String getTipoScheda() {
		return tipoScheda;
	}

	public void setTipoScheda(String tipoScheda) {
		this.tipoScheda = tipoScheda;
	}

	public String getMostraCon() {
		return mostraCon;
	}

	public void setMostraCon(String mostraCon) {
		this.mostraCon = mostraCon;
	}
	
	
	
	

}
