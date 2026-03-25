<% MessageBean messageBean = (it.avlp.simog.beans.MessageBean) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN); %>
<% if ( messageBean != null ) { %>
<% if ( messageBean instanceof it.avlp.simog.beans.AllValidationBeans ){
String buffer = "";
boolean hasErrors = false;
it.avlp.simog.beans.AllValidationBeans beanIn = (it.avlp.simog.beans.AllValidationBeans) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);		

it.avlp.simog.beans.AllValidationBeans beanWrk = null;

beanWrk = beanIn.getAllBySeverity(it.avlp.simog.beans.ValidationBean.VALBEAN_SEV_INFO);			
if (beanWrk.getSize()> 0){
buffer = buffer + "<table width=\"100%\"><tr style=\"width: 100%\"><td><div>"+
"<label class=\"avviso\" onclick=\"showElem('TAB_MESSAGGI_INFO')\">"+
"<img src=\"img/minus.gif\" id=\"imgTAB_MESSAGGI_INFO\"/>"+
"INFORMAZIONI</label></div><br>"; 
buffer = buffer + "<div id=\"TAB_MESSAGGI_INFO\" class=\"avviso\" style=\"display: block;\">";
buffer = buffer + "<ul>";
for( int i=0; i < beanWrk.getSize(); i++) {
buffer = buffer + "<li>" + beanWrk.get(i).getMessage() + (beanWrk.get(i).getElemento() > 0 ? " (riga " + String.valueOf(beanWrk.get(i).getElemento()) + ")" : "") + "</li>"; 
} 
buffer = buffer + "</ul>";
buffer = buffer + "</div></td></tr></table>";				
} 

beanWrk = beanIn.getAllBySeverity(it.avlp.simog.beans.ValidationBean.VALBEAN_SEV_ERR);			
if (beanWrk.getSize()> 0){ 
buffer = buffer + "<table width=\"100%\"><tr style=\"width: 100%\"><td><div>"+
"<label class=\"errore\" onclick=\"showElem('TAB_MESSAGGI_ERRORE')\">"+
"<img src=\"img/minus.gif\" id=\"imgTAB_MESSAGGI_ERRORE\"/>"+
"ERRORI</label></div><br>"; 
buffer = buffer + "<div id=\"TAB_MESSAGGI_ERRORE\" class=\"errore\" style=\"display: block;\">";
buffer = buffer + "<ul>";
for( int i=0; i < beanWrk.getSize(); i++) {
buffer = buffer + "<li>" + beanWrk.get(i).getMessage() + (beanWrk.get(i).getElemento() > 0 ? " (riga " + String.valueOf(beanWrk.get(i).getElemento()) + ")" : "") + "</li>"; 
} 
buffer = buffer + "</ul>";
buffer = buffer + "</div></td></tr></table>";
hasErrors = true;
} 
beanWrk = beanIn.getAllBySeverity(it.avlp.simog.beans.ValidationBean.VALBEAN_SEV_WARN);			
if (beanWrk.getSize()> 0){ 
buffer = buffer + "<table width=\"100%\"><tr style=\"width: 100%\"><td><div>"+
"<label class=\"attenzione\" onclick=\"showElem('TAB_MESSAGGI_WARNING')\">"+
"<img src=\"img/" + (hasErrors ? "plus" : "minus") + ".gif\" id=\"imgTAB_MESSAGGI_WARNING\"/>"+
"AVVISI</label></div><br>"; 
buffer = buffer + "<div id=\"TAB_MESSAGGI_WARNING\" class=\"attenzione\" style=\"display: " + (hasErrors ? "none" : "block") + ";\">";
buffer = buffer + "<ul>";
for( int i=0; i < beanWrk.getSize(); i++) {
	buffer = buffer + "<li>" + beanWrk.get(i).getMessage() + (beanWrk.get(i).getElemento() > 0 ? " (riga " + String.valueOf(beanWrk.get(i).getElemento()) + ")" : "") + "</li>"; 
} 
buffer = buffer + "</ul>";
buffer = buffer + "</div></td></tr></table>";
} %> 
<%= buffer %>
<% } else if ( messageBean instanceof it.avlp.simog.beans.ErrorBean ) { 
String stack = "";
Throwable exc = ((ErrorBean)messageBean).getError();
if (exc !=null) stack=exc.getStackTrace().toString();%>
<%@page import="it.avlp.simog.beans.ErrorBean"%>
<div class="errore" title="<%= stack %> ">
<%= messageBean.getMessage() %> 
</div>
<% } else if ( messageBean instanceof it.avlp.simog.beans.MessageBean && messageBean.getMessage()!=null){ %>
<%@page import="it.avlp.simog.beans.MessageBean"%>
<div class="avviso">
	<%= messageBean.getMessage() %>
</div>	
<% } %>
<% } %>
