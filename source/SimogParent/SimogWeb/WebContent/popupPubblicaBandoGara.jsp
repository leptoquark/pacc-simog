<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- MEV 25895 3.04.8 nuova popup  -->
<html>
<head>

<%@ include file="/script/domUtilsNew.js" %>

<%@ include file="../include/gestisciErrore.inc" %>
<%@ include file="../include/basicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>

	<title><%= request.getParameter("titlePopup") %></title>
	<base target="_self" />

<script type="text/javascript" src="script/pageutils.js"></script>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>


</head>
  

<body>
	<c:set var="utenteBean" scope="page" value="${sessionScope['UTENTE']}"></c:set>
				    
				     <c:if test="${not empty sessionScope['UTENTE'].profili}" >
			
				<c:choose>
					<c:when test="${utenteBean.RUP}">
						Si ricorda che a seguito del perfezionamento del CIG &egrave necessario inserire le schede informative relative alle varie fasi dell&acuteappalto appena concluse a partire dalla scheda dati comuni e successive, secondo le specifiche modalit&agrave indicate nei Comunicati del Presidente dell&acuteAutorit&agrave del 4 aprile 2008, del 14 dicembre 2010 e successivi comunicati ANAC.
						<br>
						<p style="text-decoration: underline;">Si ricorda che &egrave necessario dare evidenza anche di eventuali gare annullate/revocate o procedure andate deserte, compilando la &quotScheda Dati Comuni&quot.</p>
						<br>
						Si invitano i RUP a far riferimento alla Sezione regionale dell&acuteOsservatorio di appartenenza.
						<br>
						Si ricorda, altres&igrave, che all&acuteesito dell&acuteattivit&agrave di monitoraggio espletata dall&acuteOsservatorio, la mancata compilazione di tali schede potrebbe comportare l&acuteavvio di un procedimento sanzionatorio, ai sensi dell&acuteart. 213, c. 13 d.lgs. 50/2016, da parte dell&acuteAutorit&agrave. 
						<br>
						Per qualsiasi richiesta relativa alla gestione dei CIG e alla compilazione delle schede su Simog consultare la <a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_RUP %>" target="_blank">&quotGuida al Servizio&quot</a>
				        o rivolgersi al Contact Center dell&acuteAutorit&agrave al numero verde 800 896 936.
			
						
					</c:when>
					<c:when test="${utenteBean.amministratore}">
						Si ricorda che a seguito del perfezionamento del CIG &egrave necessario inserire le schede informative relative alle varie fasi dell&acuteappalto appena concluse a partire dalla scheda dati comuni e successive, secondo le specifiche modalit&agrave indicate nei Comunicati del Presidente dell&acuteAutorit&agrave del 4 aprile 2008, del 14 dicembre 2010 e successivi comunicati ANAC.
						<br>
						<p style="text-decoration: underline;">Si ricorda che &egrave necessario dare evidenza anche di eventuali gare annullate/revocate o procedure andate deserte, compilando la &quotScheda Dati Comuni&quot.</p>
						<br>
						Si invitano i RUP a far riferimento alla Sezione regionale dell&acuteOsservatorio di appartenenza.
						<br>
						Si ricorda, altres&igrave, che all&acuteesito dell&acuteattivit&agrave di monitoraggio espletata dall&acuteOsservatorio, la mancata compilazione di tali schede potrebbe comportare l&acuteavvio di un procedimento sanzionatorio, ai sensi dell&acuteart. 213, c. 13 d.lgs. 50/2016, da parte dell&acuteAutorit&agrave. 
						<br>
						Per qualsiasi richiesta relativa alla gestione dei CIG e alla compilazione delle schede su Simog consultare la <a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_ADMIN %>" target="_blank">&quotGuida al Servizio&quot</a>
				        o rivolgersi al Contact Center dell&acuteAutorit&agrave al numero verde 800 896 936.
			
						
					</c:when>
					<c:when test="${utenteBean.RSSA}">
						Si ricorda che a seguito del perfezionamento del CIG &egrave necessario inserire le schede informative relative alle varie fasi dell&acuteappalto appena concluse a partire dalla scheda dati comuni e successive, secondo le specifiche modalit&agrave indicate nei Comunicati del Presidente dell&acuteAutorit&agrave del 4 aprile 2008, del 14 dicembre 2010 e successivi comunicati ANAC.
						<br>
						<p style="text-decoration: underline;">Si ricorda che &egrave necessario dare evidenza anche di eventuali gare annullate/revocate o procedure andate deserte, compilando la &quotScheda Dati Comuni&quot.</p>
						<br>
						Si invitano i RUP a far riferimento alla Sezione regionale dell&acuteOsservatorio di appartenenza.
						<br>
						Si ricorda, altres&igrave, che all&acuteesito dell&acuteattivit&agrave di monitoraggio espletata dall&acuteOsservatorio, la mancata compilazione di tali schede potrebbe comportare l&acuteavvio di un procedimento sanzionatorio, ai sensi dell&acuteart. 213, c. 13 d.lgs. 50/2016, da parte dell&acuteAutorit&agrave. 
						<br>
						Per qualsiasi richiesta relativa alla gestione dei CIG e alla compilazione delle schede su Simog consultare la <a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_RSSA %>" target="_blank">&quotGuida al Servizio&quot</a>
				        o rivolgersi al Contact Center dell&acuteAutorit&agrave al numero verde 800 896 936.
			
						
					</c:when>
					<c:when test="${utenteBean.AVLP}">
						Si ricorda che a seguito del perfezionamento del CIG &egrave necessario inserire le schede informative relative alle varie fasi dell&acuteappalto appena concluse a partire dalla scheda dati comuni e successive, secondo le specifiche modalit&agrave indicate nei Comunicati del Presidente dell&acuteAutorit&agrave del 4 aprile 2008, del 14 dicembre 2010 e successivi comunicati ANAC.
						<br>
						<p style="text-decoration: underline;">Si ricorda che &egrave necessario dare evidenza anche di eventuali gare annullate/revocate o procedure andate deserte, compilando la &quotScheda Dati Comuni&quot.</p>
						<br>
						Si invitano i RUP a far riferimento alla Sezione regionale dell&acuteOsservatorio di appartenenza.
						<br>
						Si ricorda, altres&igrave, che all&acuteesito dell&acuteattivit&agrave di monitoraggio espletata dall&acuteOsservatorio, la mancata compilazione di tali schede potrebbe comportare l&acuteavvio di un procedimento sanzionatorio, ai sensi dell&acuteart. 213, c. 13 d.lgs. 50/2016, da parte dell&acuteAutorit&agrave. 
						<br>
						Per qualsiasi richiesta relativa alla gestione dei CIG e alla compilazione delle schede su Simog consultare la <a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_AVLP %>" target="_blank">&quotGuida al Servizio&quot</a>
				        o rivolgersi al Contact Center dell&acuteAutorit&agrave al numero verde 800 896 936.
			
						
					</c:when>
					<c:when test="${utenteBean.ossReg}">
						Si ricorda che a seguito del perfezionamento del CIG &egrave necessario inserire le schede informative relative alle varie fasi dell&acuteappalto appena concluse a partire dalla scheda dati comuni e successive, secondo le specifiche modalit&agrave indicate nei Comunicati del Presidente dell&acuteAutorit&agrave del 4 aprile 2008, del 14 dicembre 2010 e successivi comunicati ANAC.
						<br>
						<p style="text-decoration: underline;">Si ricorda che &egrave necessario dare evidenza anche di eventuali gare annullate/revocate o procedure andate deserte, compilando la &quotScheda Dati Comuni&quot.</p>
						Si invitano i RUP a far riferimento alla Sezione regionale dell&acuteOsservatorio di appartenenza.
						<br>
						Si ricorda, altres&igrave, che all&acuteesito dell&acuteattivit&agrave di monitoraggio espletata dall&acuteOsservatorio, la mancata compilazione di tali schede potrebbe comportare l&acuteavvio di un procedimento sanzionatorio, ai sensi dell&acuteart. 213, c. 13 d.lgs. 50/2016, da parte dell&acuteAutorit&agrave. 
						<br>
						Per qualsiasi richiesta relativa alla gestione dei CIG e alla compilazione delle schede su Simog consultare la <a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_OSR %>" target="_blank">&quotGuida al Servizio&quot</a>
				        o rivolgersi al Contact Center dell&acuteAutorit&agrave al numero verde 800 896 936.
			
						
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
			</c:if> 
							     
						    
							
						</div>
					</td>
			</tr>						
		</table>
	</div>




</body>
</html>
	