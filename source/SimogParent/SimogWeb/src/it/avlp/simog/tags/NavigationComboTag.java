package it.avlp.simog.tags;

import it.avlp.simog.beans.NavigationBean;
import it.avlp.simog.db.SimogFlags;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class NavigationComboTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3024157676894350046L;
	
	private String loadStyle;
	private String newStyle;
	private String disabledStyle;
	private String pendingStyle;
	private long aggiudId;
	private boolean delegante;
	
	public static final String MARKER = " (!ERR!)";
	public static final String OKMARKER = "";

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		 JspWriter out = pageContext.getOut();
		 LinkedHashMap<String,NavigationBean> navigationMap = null;
		 
		 String style =null;
		 String value =null;
		 String marker=null;
		 try{
			 HashMap<String, LinkedHashMap<String, NavigationBean>> navSMap = (HashMap<String, LinkedHashMap<String, NavigationBean>>)pageContext.getVariableResolver().resolveVariable("navigationMap");
			 navigationMap = navSMap.get(String.valueOf(aggiudId));
			 Set<String> keys = navigationMap.keySet();
			 NavigationBean scheda= null;
			 for(String tab: keys){
				 boolean show = true;
				 scheda = navigationMap.get(tab);
				 style = "";

                marker = (!scheda.isValido() ? MARKER : OKMARKER);
           
             
				 if (!scheda.isPresent() && scheda.isReadonly() ){
					 continue;
				 }				 
				 else if(scheda.isDone() /*|| scheda.isReadonly()*/){
					 if(disabledStyle!= null) style=" style='" + disabledStyle + "' ";
				 }
				 else if(scheda.isPresent() && !scheda.isRichAnn()){
					 if(loadStyle!= null) style=" style='" + loadStyle +  "' ";
				 }
				 else if(scheda.isPresent() && scheda.isRichAnn()){
					 if(pendingStyle != null) style=" style='" + pendingStyle +  "' ";
				 }
				 else{
					 if(newStyle!= null) style=" style='" + newStyle +  "' ";
					 show = !delegante;
				 }
				 
				 value = " value='" + scheda.getPath() + "' ";
				 
				 if(show)
				    out.println("<option" + style + value + ">" + scheda.getViewName() + marker + "</option>");
				
			 }
		 }catch (Exception e) {
			 //e.printStackTrace();
			return SKIP_BODY;
		}
	
		 return SKIP_BODY;
	}
	
	public void setLoadStyle(String loadStyle) {
		this.loadStyle = loadStyle;
	}

	public void setNewStyle(String newStyle) {
		this.newStyle = newStyle;
	}

	public void setDisabledStyle(String disabledStyle) {
		this.disabledStyle = disabledStyle;
	}

	public void setAggiudId(long aggiudId) {
		this.aggiudId = aggiudId;
	}

	public String getPendingStyle() {
		return pendingStyle;
	}

	public void setPendingStyle(String pendingStyle) {
		this.pendingStyle = pendingStyle;
	}

	public boolean isDelegante() {
		return delegante;
	}

	public void setDelegante(boolean delegante) {
		this.delegante = delegante;
	}
	

}
