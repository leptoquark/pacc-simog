<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%  if (user!=null){ %>
<div style="background-color:yellow;display: none;">
			<table width="100%" border="1" cellpadding="5"><tr>
			<td><img src="img/simogWarning_little.jpg" width="30px" height="30px" align="left"></td>
			<td align="left" valign="top"><p style="color:rgb(180,0,28);">
			<!-- inserire il messaggio di avviso dentro la tag strong -->	
				<strong></strong>
			</p></td></tr> 
			</table>
</div>

<div id="topmenu"  style="background-image: img/logosimog.gif"> 
<ul>

<li><a href="<%= request.getSession().getAttribute(it.avlp.simog.common.servlet.ParametriServlet.LOGOUT_URL)%>" accesskey="0"><utils:message key="menu.logout" /></a></li>

<% if(session.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.CHANGEPROF_URL) != null) {%>
	<li><a href="<%= request.getSession().getAttribute(it.avlp.simog.common.servlet.ParametriServlet.CHANGEPROF_URL)%>" accesskey="1"><utils:message key="menu.cambiaProfilo" /></a></li>
<%}%>

<% if(user.isRSSA()){ %>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.JSP_GESTIONE_GARE_RSSA %>" accesskey="2"><utils:message key="menu.gestioneGare" /></a></li>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_RSSA %>" title="<utils:message key="menu.guidaServizio" plain="true" /> SIMOG" target="_blank" accesskey="9"><utils:message key="menu.guidaServizio" /><img src="img/icon14bc.gif"></a></li>
<%}%>
<% if(user.isRUP() || user.isCS()){ %>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.JSP_GESTIONE_SCHEDE %>" accesskey="3"><utils:message key="menu.gestioneSchede" /></a></li>
	<li><a href="elencoCig" accesskey="6"><utils:message key="menu.elencoCig" /></a></li>
	<li><a href="elencoCigIntegrazioneCup" accesskey="6"><utils:message key="menu.listaCigCup" /></a></li>
<li><a href="rubrica?<%= it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE%>=view&from=menu" accesskey="4"><utils:message key="menu.rubricaOperatori" /></a></li>
<li><a href="rubricaResponsabili?<%= it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE%>=view&from=menu" accesskey="5"><utils:message key="menu.rubricaIncaricati" /></a></li>
<% String href = it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_RUP;
	if(user.isOrgano())
			href = it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_RUPOC;
%>
<li><a href="<%= href %>" title="<utils:message key="menu.guidaServizio" plain="true" /> SIMOG" target="_blank" accesskey="9"><utils:message key="menu.guidaServizio" /><img src="img/icon14bc.gif"></a></li>
<%}%>
<!--<%if(user.isAVLP()){ %>-->
<!--<li><a href="transazioniManager.jsp" accesskey="2">Visualizza transazioni</a></li>-->
<!--<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_AVLP %>" title="Guida al Servizio SIMOG" target="_blank" accesskey="9">Guida al Servizio<img src="img/icon14bc.gif"></a></li>-->
<!--<%}%>	-->
<%if(user.isAmministratore()){ %>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.JSP_GESTIONE_GARE_EXT %>" accesskey="2"><utils:message key="menu.gestioneGare" /></a></li>	
<li><a href="consultazioneLog.jsp" accesskey="3"><utils:message key="menu.consultaLog" /></a></li>
<li><a href="consultaLogOperazioni.jsp" accesskey="4"><utils:message key="menu.consultaLogSchede" /></a></li>
<li><a href="tabelleManager.jsp" accesskey="5"><utils:message key="menu.tabelleServizio" /></a></li>
<li><a href="richiestaAnnullamento?<%= it.avlp.simog.common.servlet.ParametriServletRichAnnullamento.OPERAZIONE%>=viewChoose" accesskey="6"><utils:message key="menu.richiesteModifica" /></a></li>
<!-- <li><a href="SrvServizio" accesskey="7">ON/OFF Servizio</a></li>-->
<li><a href="richiesteCancellazione?<%= it.avlp.simog.common.servlet.ParametriServletRichCancellazione.OPERAZIONE%>=viewChoose" accesskey="7"><utils:message key="menu.richiesteCancellazione" /></a></li>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_ADMIN %>" title="<utils:message key="menu.guidaServizio" plain="true" /> SIMOG" target="_blank" accesskey="9"><utils:message key="menu.guidaServizio" /><img src="img/icon14bc.gif"></a></li>		
<%}%>
<%if(user.isOssReg() || user.isRASA()){ %>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.JSP_GESTIONE_SCHEDE %>" accesskey="2"><utils:message key="menu.consultazioneSchede" /></a></li>
<%}%>
<%if(user.isOssReg() == true && it.avlp.simog.beans.ProfiloEnum.REGIONE_999.equals(user.getAdminOr()) == false ){ %>
<!-- obino 16.02.2016 
<li><a href="richiestaAnnullamento?<%= it.avlp.simog.common.servlet.ParametriServletRichAnnullamento.OPERAZIONE%>=viewChoose" accesskey="3">Richieste Modifica</a></li>
<li><a href="richiesteCancellazione?<%= it.avlp.simog.common.servlet.ParametriServletRichCancellazione.OPERAZIONE%>=viewChoose" accesskey="4">Richieste Cancellazione</a></li>		
-->
<%}%>
<%if(user.isOssReg()){ %>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_OSR %>" title="<utils:message key="menu.guidaServizio" plain="true" /> SIMOG" target="_blank" accesskey="9"><utils:message key="menu.guidaServizio" /><img src="img/icon14bc.gif"></a></li>
<%}%>
<%if(user.isRASA()){ %>
<li><a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_RASA %>" title="<utils:message key="menu.guidaServizio" plain="true" /> SIMOG" target="_blank" accesskey="9"><utils:message key="menu.guidaServizio" /><img src="img/icon14bc.gif"></a></li>
<%}%>
</ul>
</div>
<%}%>
