<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<title><utils:message key="variazione.variazioneStazioneAppaltante" /></title>
</head>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet" %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%
	String msgConfermaCambioSA = MessageHelper.getMessage(request, "variazione.confermaCambioSA");
	String msgCfNonCorretto = MessageHelper.getMessage(request, "variazione.cfNonCorretto");
	String msgConfermaCambioSAJs = msgConfermaCambioSA.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n");
	String msgCfNonCorrettoJs = msgCfNonCorretto.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n");
%>

<script type="text/javascript">
<!--
	function agree(){
	   var motivazione = document.getElementById("<%=ParametriServlet.MOTIVI_VARIAZIONE_SA %>"); 
	   var idMotivazioni = document.getElementsByName("<%=ParametriServlet.ID_MOTIVO_VARIAZIONE_SA %>"); 
      for(i=0;i<idMotivazioni.length;i++){
	       idMotivazioni[i].value = motivazione.value;
      }
	   return confirm("<%= msgConfermaCambioSAJs %>");  
   }

	function checkCF(){
		
		return true; // PP disabilitato controllo 29.11.2012
		
	   var cf = document.getElementById("codiceFiscale"); 

		if(cf.value.length != 11 || isNaN(cf.value) == true){
		
			alert("<%= msgCfNonCorrettoJs %>");
			return false;
		}
		else 
			return true;
   }
//-->
</script>

<% int indiceTab = 0; %>

<%try{ %>
<body >
<div id="gabbia">
<%@ include file="include/header.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
		
		
		<div class="testo">
		
		<form action="<%= ParametriServlet.SRV_VARIAZIONE_SA %>" method="post" onsubmit="return checkCF()">

			<h1><utils:message key="variazione.variazioneStazioneAppaltante" /></h1>
			<%@ include file="include/gestisciErrore.inc" %>
			<%--gm nuovo codice simog 3.06 --%>
			<input type="hidden" name="operazione" id="operazione" value="load" />
			<input type="hidden" name="<%=ParametriServlet.SESSION_ID_GARA%>" value="<%=request.getAttribute(ParametriServlet.SESSION_ID_GARA) %>" />
					
			<fieldset>
				<legend>Criteri di Ricerca</legend>
				<table>
								
				<tr>
					<td>Codice Fiscale Stazione Appaltante</td>
					<td><input tabindex="<%=++indiceTab%>" type="text" id="codiceFiscale" name="codiceFiscale" value="${codiceFiscale}"/>
					</td>
					<td><input tabindex="<%=++indiceTab%>" type="submit" value="Cerca"/></td>	
				</tr>
						
			</table>
		 </fieldset>
		 
		 </form>
<br />
			<div class="testo" >
					<fieldset>
						<legend><utils:message key="variazione.risultatiRicerca" /> </legend>
						<h4></h4>
			<div style="width: 99%;"  >
			
			<div class="gara">	 	
			  			
		    <table align="center" width="80%"> 
		    <caption  align="top"><h3>Stazione Appaltante: ${resultList[0].amministrazione.codiceFiscale } - ${resultList[0].amministrazione.denominazioneAmministrazione }</h3></caption>  
			<tr> 
		     <th class="garaTh">Codice</th>
		     <th class="garaTh">Denominazione</th>
		     <th class="garaTh">Azioni</th>
		     </tr>
		     <c:forEach items="${resultList}" var="sa" >
		     	<tr>
		     		<td class="garaTd">${sa.idUfficio }</td>
		     		<td class="garaTd">${sa.denominazione}</td>
		     		<td class="garaTd">
		     		  <form action="<%= ParametriServlet.SRV_VARIAZIONE_SA %>" method="post" onsubmit="return agree()">
		     			 <input type="hidden" name="operazione" id="operazione" value="select" />
		     			 <input type="hidden" name="idUfficio" value="${sa.idUfficio }" />
		     			 <input type="hidden" name="denominazione" value="${sa.denominazione }" />
		     			 <input type="hidden" name="amministrazione.codiceFiscale" value="${sa.amministrazione.codiceFiscale}" />
		     			 <input type="hidden" name="amministrazione.denominazioneAmministrazione" value="${sa.amministrazione.denominazioneAmministrazione}" />
		     			 <input type="hidden" name="amministrazione.id_osservatorio" value="${sa.amministrazione.id_osservatorio}" />
		     			 <input type="hidden" name="<%=ParametriServlet.SESSION_ID_GARA%>" value="<%=request.getAttribute(ParametriServlet.SESSION_ID_GARA) %>" />
		     			 <%--gm nuovo campo simog 3.06 --%>
		             <input type="hidden" name="<%=ParametriServlet.ID_MOTIVO_VARIAZIONE_SA %>" id="<%=ParametriServlet.ID_MOTIVO_VARIAZIONE_SA %>" value=""/>
		   
		     		    <input tabindex="<%=++indiceTab%>"  type="submit" value="<utils:message key="variazione.seleziona" plain="true" />"/>
		     		    
		     		  </form>	
		     		</td>
		     	</tr>
		     </c:forEach>
		     </table>
		      </div>
		     </div>
		 	  </fieldset> 
	        </div>
	        <c:if test="${resultList[0] ne null}">
		     <div class="testo" >	
					<fieldset>
						<legend>Motivazioni </legend>
						<h4></h4>
			<div style="width: 99%;"  >		
			<div class="testo">	 		 
		    <table align="center" width="80%"> 
		     <%--gm nuovo campo simog 3.06 --%>
	         <tr>
					<td>Motivazione della variazione</td>
					<td>
						<select tabindex="<%=++indiceTab%>" style="width:100%" name="<%= ParametriServlet.MOTIVI_VARIAZIONE_SA %>" id=<%= ParametriServlet.MOTIVI_VARIAZIONE_SA %> class="BOTTONE">
							<option></option>
			  				<c:set var="idMotiviVariazione" value="${idMotivoVariazioneSA}" scope="request" />	
			  				<u:options name="motiviVariazione" scope="request" value="idMotiviVariazione"/>
						</select>
					</td>
				</tr>	
          </table>
         </div>
         </div>
         </fieldset>	
         </div>
         </c:if>
<div>
		<%@ include file="include/newfooter.inc" %>
</div>

</body>
</html>
<% }catch(Exception e) {e.printStackTrace();}%>
