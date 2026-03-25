package it.avlp.simog.tags;

import it.avlp.simog.db.Costanti;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SelectBooleanRadio extends TagSupport {
	
	
	
	private String trueId;
	private String trueLabel = "SI";
	private String falseLabel = "NO";
	private String falseId;
	private String name;
	private String scope = "request";
	private String value = "";
	private boolean disabled;
	private boolean readonly;
	private boolean rendered = true;
	private String trueVal = Costanti.FLAG_VALORE_SI;
	private String falseVal = Costanti.FLAG_VALORE_NO;
	private Integer tabindex;
	private String onchange;
	
	private String onclick;
	private boolean triState;



	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
	
		
		if(name == null || !rendered)
			return SKIP_BODY;
		if(trueId == null)
			trueId = name + trueVal;
		if(falseId == null)
			falseId = name + falseVal;
		
		try{
			out.println(getRadioButton(trueId, trueVal.equals(value), trueLabel, trueVal));
			
			out.println(getRadioButton(falseId,  falseVal.equals(value), falseLabel,falseVal));
			
			if(triState)
				out.println(getRadioButton(trueId + "dummy",  false, "N/A",""));
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		
		return SKIP_BODY;
		
	}
	
	private String getRadioButton(String id, boolean checked, String label, String value){
		StringBuffer sb = new StringBuffer();
		sb.append("<input type=\"radio\" name =\"" + name + "\" id=\"" + id + "\" value=\"" + value + "\" \n" );
		if(disabled)
			sb.append(" disabled =\"true\" ");
		
		if(tabindex != null){
			sb.append(" tabindex =\"" +  tabindex + "\" ");
			tabindex++;
		}
		if(onchange != null)
			sb.append(" onchange =\"" +  onchange + "\" ");
		
		//TICKET ALM #19118
		if(onclick != null) {
			if(!"FLAG_RICH_SUBAPPALTO".equals(name))
			sb.append(" onclick =\"" +  onchange + "\" ");
			else if(id.equals(falseId)) 
				 sb.append(" onclick =\"" +  onchange + "\" ");
			else
				sb.append(" onclick =\"" +  onclick + "\" ");
		   }
		
		if(checked )
			sb.append(" checked =\"true\" ");
		
		if(readonly && !checked)
				sb.append(" disabled =\"true\" ");
			
		sb.append("/>" + label);
		
		//System.out.println(sb.toString());
		return sb.toString();
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


	
	
//	private Object invokeEL(String el)throws Exception {
//		Object ret = null;
//		String[] parts = el.split(".");
//		if(parts.length == 0)
//			throw new ELException("could not resolve el [" + el + "]");
//		
//		ret = resolveVariable(parts[0]);
//		if(ret == null)
//			throw new ELException("could not resolve el [" + el + "]");
//	
//		for(int i = 1; i < parts.length; i++){
//			ret = callGetter(ret, parts[i]);
//			
//		}
//		
//		
//		return ret;
//	}
//	
//	private Object callGetter(Object o, String fieldName)throws Exception{
//		if(o == null)
//			return null;
//		Class c = o.getClass();
//		Method m = c.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
//		
//		return m.invoke(o);
//	}
//	
//	 private Object resolveVariable(String name) throws Exception {
//		return pageContext
//			.getVariableResolver().resolveVariable(name);
//	 }

	public String getTrueId() {
		return trueId;
	}

	public void setTrueId(String nameYes) {
		this.trueId = nameYes;
	}

	public String getFalseId() {
		return falseId;
	}

	public void setFalseId(String nameNo) {
		this.falseId = nameNo;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getTrueVal() {
		return trueVal;
	}

	public void setTrueVal(String trueVal) {
		this.trueVal = trueVal;
	}

	public String getFalseVal() {
		return falseVal;
	}

	public void setFalseVal(String falseVal) {
		this.falseVal = falseVal;
	}

	public String getTrueLabel() {
		return trueLabel;
	}

	public void setTrueLabel(String trueLabel) {
		this.trueLabel = trueLabel;
	}

	public String getFalseLabel() {
		return falseLabel;
	}

	public void setFalseLabel(String falseLabel) {
		this.falseLabel = falseLabel;
	}

	public Integer getTabindex() {
		return tabindex;
	}

	public void setTabindex(Integer tabindex) {
		this.tabindex = tabindex;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public boolean isTriState() {
		return triState;
	}

	public void setTriState(boolean triState) {
		this.triState = triState;
	}
}