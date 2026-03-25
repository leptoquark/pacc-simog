package it.avlp.simog.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ContainsTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7136761897995329182L;
	private Object[] list;
	private Object element;
	
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try{	
			for(int i = 0; i < list.length; i++){
					if(list[i].toString().equals(element.toString()))
						out.print(" checked ");		   
			}
			
		}catch(NullPointerException npe){
			return SKIP_BODY;
		}catch(IOException ioe){
			throw new JspException(ioe);
			
		}
		return SKIP_BODY;
	}
	
	
	
	
	
	public Object[] getList() {
		return list;
	}
	public void setList(Object[] list) {
		this.list = list;
	}
	public Object getElement() {
		return element;
	}
	public void setElement(Object element) {
		this.element = element;
	}
}
