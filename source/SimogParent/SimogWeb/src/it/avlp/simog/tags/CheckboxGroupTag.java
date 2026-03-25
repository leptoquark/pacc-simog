package it.avlp.simog.tags;

import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CheckboxGroupTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8889795158062563764L;
	private String fieldName;
	private String campoId;
	private String campoVisualizzato;
	private TableBean listaCheckBox;
	private TableBean checkedList;
	private String disabled = "";
	private String onchange = null;
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try{
			if(listaCheckBox == null)
				return SKIP_BODY;
			
			
			for(int i=0; i < listaCheckBox.getRowsCount(); i++){
				out.println("<tr>");
				TableBeanRow tbr = listaCheckBox.getRow(i);
				out.print("<th><label>" + tbr.getNulledField(campoVisualizzato)+ "</label></th>");
				out.print("<td><input " + disabled + "  type=\"checkbox\" name =\"" + fieldName + i + "\" value=\"" );
				out.print(tbr.getNulledField(campoId) + "\" " );
				if(onchange!=null)
					out.print(onchange);
				if(containsValue(checkedList, campoId, tbr.getNulledField(campoId)))
					out.print(" checked /></td>");
				else 
					out.print(" /></td>");
				
				out.println("</tr>");
			}
		}catch (Exception e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}
	
	
	
	
	/*******************************************************************************
	 * Verifica se il campo espresso da nomecampo di una tabella indicata 
	 * da table risulta avere un determinato valore indicato da value 
	 * @param table TableBean
	 * @param nomeCampo nomeCampo
	 * @param value String
	 * @return boolean
	 */
	private boolean containsValue(TableBean table,String nomeCampo,  String value) {
		TableBeanRow tbr = null;
		if(table == null)
			return false;
		for(int i=0; i< table.getRowsCount(); i++){
			tbr = table.getRow(i);
			String valore = tbr.getNulledField(nomeCampo);
			if(valore.equalsIgnoreCase(value))
				return true;
		}
		return false;
	}
	
	
	
	public TableBean getListaCheckBox() {
		return listaCheckBox;
	}
	public void setListaCheckBox(TableBean listaCheckBox) {
		this.listaCheckBox = listaCheckBox;
	}
	
	public void setCheckedList(TableBean checkedList) {
		this.checkedList = checkedList;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getCampoId() {
		return campoId.toUpperCase();
	}

	public void setCampoId(String campoId) {
		this.campoId = campoId.toUpperCase();
	}

	public String getCampoVisualizzato() {
		return campoVisualizzato.toUpperCase();
	}

	public void setCampoVisualizzato(String campoVisualizzato) {
		this.campoVisualizzato = campoVisualizzato.toUpperCase();
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = "onchange = \""+onchange+"\"";
	}

}
