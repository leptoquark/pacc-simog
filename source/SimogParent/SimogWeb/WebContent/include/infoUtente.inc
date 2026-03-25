<% //@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.beans.StazioneAppaltante"%>
<%@ page import="it.avlp.simog.beans.ProfiloEnum"%>
<% if(user != null ){ %>
<%@page import="java.util.Hashtable"%>
<div class="infoBlock">
<div class="inlineInfo">
<ul>
<li title="<%= user.getLogin() %>">Utente:&nbsp;<strong><%= user.getCognome() %> <%= user.getNome() %></strong></li>
<li title="<%=user.getAdminOr() %>">Profilo corrente:<strong><%= user.getProfiloEnum().name() %></strong></li>
</ul>
</div>
<div class="rightLineInfo">
<ul>
<% Hashtable amm = user.getAmministrazioniByProfilo(user.getProfiloEnum());
if(ProfiloEnum.RASA.name().equals(user.getProfiloEnum().name()) &&  user.getDenomRasa()!=null && !"".equals(user.getDenomRasa())){ %>
	<li><%= user.getDenomRasa() %></li>
<%} else if(amm != null) {
	Hashtable uff = user.getUfficiByProfilo(user.getProfiloEnum());
	if(uff != null && amm.keySet().size() == 1 && uff.keySet().size() == 1){
		String lAmm = (String) amm.get(amm.keySet().toArray()[0]);
		StazioneAppaltante lStaz = (StazioneAppaltante) uff.get(uff.keySet().toArray()[0]);
		lAmm = lAmm + " - " + lStaz.getDenominazione();
		String desc = lStaz.getAmministrazione().getCodiceFiscale() + " (" + lStaz.getAmministrazione().getId_osservatorio() + ") - " + lStaz.getIdUfficio();
		%>
		<li title="<%=desc %>"><%= lAmm %></li>
<% }
	else{
		String listUff = "";
		for(java.util.Enumeration e = amm.keys(); e.hasMoreElements();){
			String chiave = (String)e.nextElement(); 
			String ammin = (String) amm.get(chiave);
			listUff+=ammin+" ";
			if(listUff.length()<160){
%>
<li title="<%=chiave %>"><%= ammin %></li>
<%} else {
        %>
       <li>...</li>      		
        <%break;
        }%>	
<%}%>		
<%}%>		
<%}%>					 
</ul>
</div>
</div>
<%}%>