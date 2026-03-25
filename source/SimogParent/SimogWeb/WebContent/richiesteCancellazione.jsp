<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@page import="it.avlp.simog.db.generated.*"%>
<%@page import="it.avlp.simog.beans.*"%>
<%@page import="it.avlp.simog.gestioneannullamentomanager.*"%>		
<%@page import="it.avlp.simog.gestionecancellazionemanager.*"%>	
<%@ include file="/include/newbasicHeader.inc" %> 
<title><utils:message key="richieste.gestioneRichiesteCancellazione" /></title>
</head>
<%try{ %>
<body>

<SCRIPT type="text/javascript">
function inputRadio(){
	var radio1 = document.getElementById('radio1');
	var radio2 = document.getElementById('radio2');
	var radio3 = document.getElementById('radio3');
	
	if(radio1.checked == false && radio2.checked == false && radio3.checked == false){
		alert("<%= it.avlp.simog.util.MessageHelper.getMessage(request, "richieste.scegliereTipoRichiesta") %>");
		return false;
	}
	else {
		return true;
	}
	
}

function doAction (actionUrl, campo){
	if(!inputRadio()) return false;	
	document.forms[0].action=actionUrl+"&<%= ParametriServletRubrica.OPERAZIONE %>=Visualizza&<%=ParametriServlet.ORDER_FIELD %>="+campo;
	document.forms[0].submit();
}

</SCRIPT> 


<%@ include file="/include/controlloSessione.inc" %>
<div id="gabbia">  
<%@ include file="/include/header.inc" %>
<%@ include file="include/menu/menuRichAnn.inc" %>
	<div id="bodypage">
	<div class="bodypage-e">
		<h1><utils:message key="richieste.richiesteCancellazione" /></h1>		
		<%@ include file="/include/gestisciErrore.inc" %>
		<form action="richiesteCancellazione" method="post" name="richiesteCancellazione" id="richiesteCancellazione">
		<% TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
		 String listaVisualizzata = ""; 
		 int indiceTab = 0; 
		 String checkedListaCompleta = ""; 
		 String checkedListaDaValutare = ""; 
		 String checkedListaValutata = ""; 
		 String listaScelta = ""; 
		 TableBeanRow currentRow = null; 
		 int id_richiesta = 0; 
		 int id_record = 0; 
		 String data_inizio = ""; 
		 String blocco = ""; 
		 String richiedente = ""; 
		 String motivoRichiesta = ""; 
		 String esito = ""; 
		 String CIG = ""; 
		 String completa = ""; 
		 String data_inizio_record = "";
		 if(request.getParameter(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA)!=null){ 
		 		if(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE.equalsIgnoreCase(request.getParameter(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA)) 
				|| ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA.equalsIgnoreCase(request.getParameter(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA))) {
					checkedListaDaValutare = "checked";
					listaScelta = ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE;
				} 
				if(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE.equalsIgnoreCase(request.getParameter(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA))) {
					checkedListaValutata = "checked";
					listaScelta = ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE;
				} 
				if(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_COMPLETA.equalsIgnoreCase(request.getParameter(ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA))) {
					checkedListaCompleta = "checked";
					listaScelta = ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_COMPLETA;
				}
		  } %>


<%--	AGGIUNTO FILTRO PER LA RICERCA DI RICHESTE DI CANCELLAZIONE PER CIG --%>		
		<table>
			<tr><td>CIG Lotto</td>
				<td><input id="txt_CIG" type="text" value="" name="<%= ParametriServletRichCancellazione.FIELD_NAME_FILTRO_CIG %>" title="CIG" maxlength="10" size="10"/></td>
				<td><p class="detailHelp">Indicare il CIG del lotto di cui visualizzare le richieste di cancellazione</p></td>
			</tr>
			<tr><td>Richieste cancellazione da valutare</td><td><input id="radio1" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA %>" <%= checkedListaDaValutare %> value="<%=ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE %>"></td></tr>
			<tr><td>Richieste cancellazione valutate</td><td><input id="radio2" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA %>" <%= checkedListaValutata %> value="<%=ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE %>"></td></tr>
			<tr><td>Tutte</td><td><input id="radio3" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA %>" <%= checkedListaCompleta %> value="<%=ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_COMPLETA %>"></td></tr>
		</table>
<%-- --%>
<%--
	Richieste cancellazione da valutare<input id="radio1" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA %>" <%= checkedListaDaValutare %> value="<%=ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE %>">
	Richieste cancellazione valutate<input id="radio2" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA %>" <%= checkedListaValutata %> value="<%=ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE %>">
	Tutte<input id="radio3" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA %>" <%= checkedListaCompleta %> value="<%=ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA_COMPLETA %>">
	<br>
--%>
<div>
	<input type="submit" id="<%= ParametriServletRubrica.OPERAZIONE %>" name="<%= ParametriServletRubrica.OPERAZIONE %>" value="Visualizza" onclick="return inputRadio()">
</div>
	</form>	
	<% 
	String cig_lotto = (String)request.getAttribute( ParametriServletRichCancellazione.FIELD_NAME_FILTRO_CIG );
	String scelta = (String)request.getAttribute( ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA );
		
	if(tableBean!=null) { %> 	
<%
		int maxRigheVisualizzabili = ( (Integer)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) ).intValue(); 
   		Integer startRowInt = (Integer)request.getAttribute( ParametriServlet.START_ROW ); 
		//TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
		int tableBeanSize = tableBean.getFullSize();
		int startRow = startRowInt.intValue(); 
		int righeVisualizzate = startRow + tableBean.getTableSize();
		long resto = (tableBeanSize % maxRigheVisualizzabili);
		long fineElenco = tableBeanSize - resto - maxRigheVisualizzabili - (resto == 0 ? maxRigheVisualizzabili : 0) ;
	
		String urlScelta = scelta != null || !"".equals(scelta) ? "&"+ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA +"=" +scelta : "";
		String urlCIGLotto = cig_lotto != null || !"".equals(cig_lotto) ? "&"+ParametriServletRichCancellazione.FIELD_NAME_FILTRO_CIG +"=" +cig_lotto : "";
		
		String orderField = (String)request.getParameter(ParametriServlet.ORDER_FIELD);
		String urlOrderField = orderField != null ? "&"+ParametriServlet.ORDER_FIELD +"="+orderField : "";
		
		String jspRicerca = "richiesteCancellazione?" +ParametriServletRubrica.OPERAZIONE+ "=view"+ urlScelta + urlCIGLotto + urlOrderField;
%>
		<div class="hmenu">
			<ul>
			<% if ( startRowInt >  0 ) { %>
				<li><a href="<%= jspRicerca %>" title="Visualizza prima pagina">Inizio elenco</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza prima pagina">Inizio elenco</a></li> <% } %>

			<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Precedenti">Precedenti</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza Precedenti">Precedenti</a></li> <% } %>
			
			<% if ( tableBeanSize - righeVisualizzate > 0 ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Successive">Successive</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza Successive">Successive</a></li> <% } %>
			
			<% if ( righeVisualizzate != tableBeanSize ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>" title="Visualizza ultima pagina">Fine elenco</a></li>
			<% } 
			else {%> <li><a id="disabledMenu" title="Visualizza ultima pagina">Fine elenco</a></li> <% } %>		
			<%-- 
			<p><%= "startRowInt " + (startRowInt)%></p>
			<p><%= "righeVisualizzate " + (righeVisualizzate)%></p>
			<p><%= "tableBeanSize " + (tableBeanSize)%></p>
			<p><%= "fine elenco " + fineElenco%></p>
			<p><%= "if( "+righeVisualizzate+" < "+(tableBeanSize- resto)+" )"%></p>
			--%>
			</ul>
		</div>
	<div class="scrollInside">		
				<div class="gara">
				<table class="TableBean">
		<% String actionUrl = "richiesteCancellazione?" + urlScelta.substring(1) + urlCIGLotto; %>
		<tr>					
		<td class="TableBeanTitle"><a title="Ordina per questo campo" onclick="javascript: doAction('<%= actionUrl %>','<%= RICHIESTA_ANNULLAMENTO.RICHIEDENTE %>'); ">Richiedente</a></td>
		<td class="TableBeanTitle"><a title="Ordina per questo campo" onclick="javascript: doAction('<%= actionUrl %>','<%= RICHIESTA_ANNULLAMENTO.DATA_INIZIO %>'); ">Data Richiesta</a></td>
		<td class="TableBeanTitle"><a title="Ordina per questo campo" onclick="javascript: doAction('<%= actionUrl %>','<%= RICHIESTA_ANNULLAMENTO.BLOCCO %>'); ">Scheda</a></td>
		<td class="TableBeanTitle"><a title="Ordina per questo campo" onclick="javascript: doAction('<%= actionUrl %>','<%= LOTTO.CIG %>'); ">CIG / CUI</a></td>
		<td class="TableBeanTitle"><a title="Ordina per questo campo" onclick="javascript: doAction('<%= actionUrl %>','<%= RICHIESTA_ANNULLAMENTO.CANCELLAZIONE %>'); ">Completa</a></td>
		<% if(listaScelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE)){%>
			<td class="TableBeanTitle"><a title="Ordina per questo campo" onclick="javascript: doAction('<%= actionUrl %>','<%= RICHIESTA_ANNULLAMENTO.ESITO %>'); ">Esito</a></td>
		<% } %>	
	</tr>
		<% } %>	


	<% if(tableBean!=null) { 
	  	 for ( int rowIndex = 0; rowIndex < tableBean.getTableSize(); rowIndex++ ) { 
			 currentRow = tableBean.getRow(rowIndex); 
			 int counter = 0; 
			 id_richiesta = Integer.parseInt(currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.ID_RICHIESTA)); 
			 data_inizio = it.avlp.simog.util.PageHelper.getFormattedDateTime(currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.DATA_INIZIO)); 
			 richiedente = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.RICHIEDENTE); 
			 blocco = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.BLOCCO); 
			 motivoRichiesta = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA); 
			 id_record = Integer.parseInt(currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.ID_RECORD)); 
			 esito = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.ESITO); 
			 CIG = currentRow.getNulledField(LOTTO.CIG);
			 if(CIG.length()>10)
				 CIG = CIG.substring(2);
			 data_inizio_record = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD); 
			 completa = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.CANCELLAZIONE);
		 %>
	<tr>
		<td nowrap class="garaTd"><%= richiedente %></td>
		<td nowrap class="garaTd"><%= data_inizio %></td>
		<td nowrap class="garaTd"><%= it.avlp.simog.gestioneannullamentomanager.AnnullamentoManager.returnTableNameAnnullamento(blocco) %></td>		
		<td nowrap class="garaTd"><%= CIG %></td>
		<td nowrap class="garaTd"><%= completa %></td>
		<% if(listaScelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE)){%>
			<% if(RichiestaAnnullamento.RICHIESTA_RIFIUTATA.equals(esito)) {%>
			<td nowrap class="garaTd">Rifiutata</td>
			<% }else if(RichiestaAnnullamento.RICHIESTA_ACCETTATA.equals(esito)) {%>
			<td nowrap class="garaTd">Accettata</td>
		<% }else{ %>
			<td nowrap class="garaTd">&nbsp;</td>
		<%} }%>
		<td nowrap class="hmenu"><a href="richiesteCancellazione?<%= ParametriServletRichCancellazione.OPERAZIONE %>=viewDetail&<%= ParametriServletRichCancellazione.FIELD_NAME_ID_RICHIESTA %>=<%= id_richiesta%>&<%= ParametriServletRichCancellazione.FIELD_NAME_ID_RECORD %>=<%= id_record%>&<%= ParametriServletRichCancellazione.FIELD_NAME_BLOCCO %>=<%= blocco%>&<%= ParametriServletRichCancellazione.FIELD_NAME_SCELTA_LISTA%>=<%=listaScelta %>&<%= ParametriServletRichCancellazione.FIELD_NAME_DATA_INIZIO_RECORD%>=<%=data_inizio_record %>&<%=ParametriServletRichCancellazione.FIELD_NAME_FILTRO_CIG%>=<%=cig_lotto%>">Dettaglio</a></td>
	</tr>
<% } 
 }%>

<%}catch(Exception e){e.printStackTrace();} %>
</table>
</div>
</div>
</div>
</div>
<%@ include file="/include/newfooter.inc" %>
</div>
</body>

</html>