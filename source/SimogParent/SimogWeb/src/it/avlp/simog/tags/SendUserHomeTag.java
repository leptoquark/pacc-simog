/**
 * 
 */
package it.avlp.simog.tags;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Dorjan
 *
 */
public class SendUserHomeTag extends TagSupport {
	
	
	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		Utente user = (Utente)session.getAttribute(ParametriServlet.UTENTE);
		try{
			
		
			if(user == null)
				return EVAL_PAGE;
			else{
//				if(user.getProfilo().equals(ProfiloUtente.AMMINISTRATORE))
//					pageContext.forward(ParametriServlet.JSP_AMM_HOME);
//				
//				if(user.getProfilo().equals(ProfiloUtente.AVLP))
//					pageContext.forward(ParametriServlet.JSP_AVCP_HOME);
//				
//				if(user.getProfilo().equals(ProfiloUtente.RUP))
//					pageContext.forward(ParametriServlet.JSP_RUP_CS_HOME);
//				
//				if(user.getProfilo().equals(ProfiloUtente.CS))
//					pageContext.forward(ParametriServlet.JSP_RUP_CS_HOME);
//				
//				if(user.getProfilo().equals(ProfiloUtente.RSSA))
//					pageContext.forward(ParametriServlet.JSP_RSSA_HOME);
				
//				 PP pageContext.getSession().invalidate();
				
//				 PP pageContext.forward(ParametriServlet.JSP_ERRORE);
				
			}
			
			return EVAL_PAGE;
		
		}catch (Exception e){
			throw new JspException(e);
		}
	}


}
