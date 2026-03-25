<% it.avlp.simog.beans.Utente user = (it.avlp.simog.beans.Utente) session.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.UTENTE); 
if ( user == null ) {
it.avlp.simog.beans.ErrorBean errorBean = new it.avlp.simog.beans.ErrorBean (it.avlp.simog.errormessage.Messaggi.SIMOG_LOGIN_003);
request.setAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN, errorBean);
pageContext.forward(it.avlp.simog.common.servlet.ParametriServlet.JSP_ERRORE);
return;
}
else{
Boolean SERVICE_AVAILABLE = session.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.SERVICE_AVAILABLE)!= null ?
(Boolean)session.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.SERVICE_AVAILABLE): new Boolean(false);
if(!(user.isAmministratore() || SERVICE_AVAILABLE.booleanValue())){
it.avlp.simog.beans.ErrorBean errorBean = new it.avlp.simog.beans.ErrorBean (it.avlp.simog.errormessage.Messaggi.SIMOG_LOGIN_005);
request.setAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN, errorBean);
pageContext.forward("ServizioNonDisponibile.jsp");			
return;
}
} %>