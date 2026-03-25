package it.avlp.simog.tags;



import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.util.PageHelper;


public class GetRequestParameterTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8988650274111246252L;
	private String property, defaultValue;

	public void setProperty(String valore) {
		this.property = valore;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int doStartTag() throws JspException {
		ServletRequest request = (ServletRequest) pageContext
				.getRequest();
		String value = request.getParameter(property);
		if (this.getDefaultValue() == null)
			this.setDefaultValue("");

		try {
			if(value!=null && (ParametriServlet.FIELD_NAME_OGGETTO_GARA.equals(property) || ParametriServlet.FIELD_NAME_OGGETTO_LOTTO.equals(property)))
				pageContext.getOut().print(PageHelper.formattaTesto(value));
			else
				pageContext.getOut().print(value == null ? this.getDefaultValue() : value.replace("\"", "'"));

		} catch (IOException ioException) {
			throw new JspException(ioException.getMessage());
		}
		return SKIP_BODY;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
}