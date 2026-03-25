package it.avlp.simog.tags;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import it.avlp.simog.common.servlet.ParametriServlet;

public class OptionListTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6504020878878692499L;
	
	private String name;
	private String scope = "request";
	private String value = "";
	private String valLocation="scoped";
	private String revert;
   private String limit;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		Map<String, String> optionList = null;
		String checked = null;
		if(name == null)
			return SKIP_BODY;
		try{
		
			optionList= (Map<String, String>) pageContext.getVariableResolver().resolveVariable(name);
			if("scoped".equalsIgnoreCase(valLocation))
				checked = String.valueOf(pageContext.getVariableResolver().resolveVariable(value));
			else checked = value;
	
			if(optionList == null)
	            return SKIP_BODY;
			
			Object[] keys = null;
			
			keys = optionList.keySet().toArray();
			
			//ticket #31063
			if(!ParametriServlet.MOTIVO_COLLEGAMENTO_BEAN.equals(name)){
				Arrays.sort(keys); 				
			}
						
			for(int i = 0; i < keys.length; i++){
			   String optVal = optionList.get(keys[i]) != null ? (String) optionList.get(keys[i]) : "";
			   int lim = limit == null ? 0 : Integer.valueOf((String) limit);
			   
			   int lung = lim == 0 
			         ? optVal.length() 
			         : optVal.length() < lim 
			            ? optVal.length() 
			            : lim;
			   
			   String lTitle = limit == null ? "" : "title=\"" + optVal + "\"";
            String lTesto = limit == null ? optVal : (lung > 0 ? optVal.substring(0, lung) : optVal);
			   
				if("yes".equalsIgnoreCase(revert)){
					out.print("	<option " + lTitle + " value=\"" + optVal );
					if(checked != null && checked.equalsIgnoreCase(optVal) ){
						out.println( "\" selected=\"selected\" >" + keys[i] + "</option>" );
					}
					else
						out.println( "\">" + keys[i] + "</option>" );

				}
				else{
					out.print("	<option " + lTitle + " value=\"" + (keys[i] != null ? keys[i] : "") );

					if(checked != null && keys[i] != null && checked.equalsIgnoreCase(keys[i].toString()) ){
						out.println( "\" selected=\"selected\" >" + lTesto + "</option>" );
					}
					else
						out.println( "\">" + lTesto + "</option>" );
				}
			}
			
			
			
		}catch (Exception e) {
			//e.printStackTrace();
			throw new JspException(e);
		}
		
		return SKIP_BODY;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValLocation(String valLocation) {
		this.valLocation = valLocation;
	}

	public String getRevert() {
		return revert;
	}

	public void setRevert(String revert) {
		this.revert = revert;
	}

   public String getLimit() {
      return limit;
   }

   public void setLimit(String limit) {
      this.limit = limit;
   }
	
}
