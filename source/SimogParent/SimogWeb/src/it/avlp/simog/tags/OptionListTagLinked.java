package it.avlp.simog.tags;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class OptionListTagLinked extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6504020878878692499L;
	
	private String name;
	private String scope = "request";
	private String value = "";
	private String valLocation="scoped";
	private String revert;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		LinkedHashMap<String, String> optionList = null;
		String checked = null;
		if(name == null)
			return SKIP_BODY;
		try{
		
			optionList= (LinkedHashMap<String, String>) pageContext.getVariableResolver().resolveVariable(name);
			if("scoped".equalsIgnoreCase(valLocation))
				checked = String.valueOf(pageContext.getVariableResolver().resolveVariable(value));
			else checked = value;
	
			if(optionList == null)
	            return SKIP_BODY;
			
			Object[] keys = null;
			
			keys = optionList.keySet().toArray();
			
			for(int i = 0; i < keys.length; i++){

				if("yes".equalsIgnoreCase(revert)){
					out.print("	<option value=\"" + optionList.get(keys[i]) );
					if(checked != null && checked.equalsIgnoreCase(optionList.get(keys[i])) ){
						out.println( "\" selected=\"selected\" >" + keys[i] + "</option>" );
					}
					else
						out.println( "\">" + keys[i] + "</option>" );

				}
				else{
					out.print("	<option value=\"" + keys[i] );
					
					if(checked != null && checked.equalsIgnoreCase(keys[i].toString()) ){
						out.println( "\" selected=\"selected\" >" + optionList.get(keys[i]) + "</option>" );
					}
					else
						out.println( "\">" + optionList.get(keys[i]) + "</option>" );
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
	
}
