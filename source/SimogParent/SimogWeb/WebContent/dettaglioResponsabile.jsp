<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*"%>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.Costanti" %>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="it.avlp.simog.util.ObjectIntrospector"%>
<html>
<head>
	<!-- main calendar program -->
	<script type="text/javascript" src="calendar/calendar.js"></script>
	
	<!-- language for the calendar -->
	<%@ include file="include/calendar-dynamic.inc" %>

	<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
	<script type="text/javascript" src="calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="script/pageutils.js"></script>
	<script type="text/javascript" src="xtree/treeutils.js"></script>

<title><utils:message key="rubrica.rubricaIncaricati" /></title>
</head>
<%@ include file="include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %> 

<script type="text/javascript">
 
function winconf(){
		
	var x=window.confirm("<%= MessageHelper.getMessage(request, "rubrica.confermaEliminazioneSoggetto") %>")
	alert(x)
}

</script>
<body>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->

<div id="gabbia">
<%//FIXME: controllare quali funzioni sono da levare p0erche gia importate con l'include dei files [*.js] %>
<script type="text/javascript">
	
	function doSubmit(action){
		document.getElementById('operazione').value=action;
		document.forms[0].submit();
	}
</script>

<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuDettRubrica.inc" %>

<form action="rubricaResponsabili"  method="post" name="rubrica" id="rubrica">
<input type="hidden" id="operazione" name="<%= ParametriServletRubrica.OPERAZIONE %>" value = "" />
<div id="bodypage">
<div class="bodypage-e">
<%@ include file="include/gestisciErrore.inc" %>

<div class="testo">

<%if("viewDetail".equals(request.getParameter("operazione"))){
	
	if (request.getAttribute(ParametriServlet.STORICORESPONSABILE) != null && request.getAttribute(ParametriServlet.STORICORESPONSABILE) != "") {
	 TableBean tab = (TableBean) request.getAttribute(ParametriServlet.STORICORESPONSABILE); 
	 if (!tab.isEmpty()) {%>
		
		<fieldset>
		<legend><utils:message key="rubrica.storicoModifiche" /></legend>	
				<div align="center" class="scrollLittle">
					<% tab.printHTMLTable(new java.io.PrintWriter(out)); %>
				</div>
	</fieldset>		
<%} }}  %>

<fieldset>
	<legend><utils:message key="rubrica.dettaglioIncaricato" /></legend>

	<table cellpadding="3">
			<tbody>
<%
if("viewDetail".equals(request.getParameter("operazione"))){
		TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
		
		int rowIndex=0;		
		TableBeanRow currentRow = tableBean.getRow(rowIndex);
		 if ( rowIndex == 0 ) { 
			 
		 %><% int indiceTab = 0; %>
				<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.ID_RESPONSABILE) %>">
				<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES) %>">
				<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE) %>">
				
				<input type="hidden" id="ModificaResponsabile" name="ModificaResponsabile" value="0"/>
				
				<tr>
					<th><label for="">Codice Fiscale</label></th>
					<td><input onchange="setFormModified('ModificaResponsabile')" type="text" disabled id="cFiscale" value="<%=currentRow.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE)%>" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Cognome</label></th>
					<td>
						<input  onchange="setFormModified('ModificaResponsabile')" id="Cognome" type="text" maxlength="50" name  ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.COGNOME) %>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Nome</label></th>
					<td>
						<input onchange="setFormModified('ModificaResponsabile')" id="Nome"  type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.NOME) %>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Telefono</label></th>
					<td>
						<input onchange="setFormModified('ModificaResponsabile')" id="Telefono" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.TELEFONO) %>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Fax</label></th>
					<td>
						<input onchange="setFormModified('ModificaResponsabile')" id="Fax" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_FAX %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.FAX) %>" >
					</td>
				</tr>
				<tr>
					<th><label for="">Email</label></th>
					<td>
						<input onchange="setFormModified('ModificaResponsabile')" id="Email" type="text" maxlength="64" name ="<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.EMAIL) %>" >
					</td>	
				</tr>
		
				<tr>
					<th><label for="">Indirizzo</label></th>
					<td>
						<input onchange="setFormModified('ModificaResponsabile')" id="Indirizzo" type="text" maxlength="100" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.INDIRIZZO) %>" >
					</td>	
				</tr>				
				<tr>
					<th><label for="">Cap</label></th>
					<td>
						<input onchange="setFormModified('ModificaResponsabile')" id="Cap" type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.CAP) %>" >
					</td>	
				</tr>
				<% // id?!? %>
				<input type="hidden" id="canSearch" value="true" />
				<tr>
					<th><label for="">Codice Istat del Comune</label></th>
				<td >
				<input onchange="setFormModified('ModificaResponsabile')" 
					   maxlength="9" 
					   tabindex="<%=++indiceTab%>" <c:out value="${disabled}" /> 
					   type="text" 
					   name="<%= ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT %>" 
					   value="<%=currentRow.getNulledField(SOGGETTI_RESPONSABILI.COMUNE_ISTAT) %>" 
					   id="sel_ISTAT" onblur="searchLE(this.id, 'ricercaIstat.jsp', 'isNotIstat')" 
				   onkeyup="checkKeyLE(event, this, 'ricercaIstat.jsp', 'isNotIstat')"/>
				<c:if test="${hide != true}">
					<a class="getCPV" href="#"  onclick="apripopup('ricercaIstat.jsp','sel_ISTAT')" title="Lista codici ISTAT"><img src="img/icon_info_sml.gif" onclick="apripopup('ricercaIstat.jsp','sel_ISTAT')"></a>
				</c:if>
			</td>	
				</tr>
				<% //value="<c:out value="${aggiudicazione.luogoIstat}" />"  %> 
				<% 	
		 } %>
		</tbody>
</table>
</fieldset>

</div>
<input type="button"  onclick = "doSubmit('Modifica')"  value="Modifica">
<input type="button"  onclick = "doSubmit('Cancella')"  value="Cancella">
<input type="button"  onclick = "doSubmit('Indietro')"  value="Indietro">


<% } else if ("Aggiungi alla rubrica".equals(request.getParameter("operazione"))) { %>
<% int indiceTab = 0; %>

<%
  String flag = (String)request.getAttribute(ParametriServlet.FLAG_ESTERO);
  if(flag == null || "".equals(flag)){flag = Costanti.FLAG_VALORE_NO;}
  request.setAttribute("flag", flag);
  String disabled1 = (String)request.getAttribute("disabled");
  if(disabled1 == null){disabled1 ="";}
%>
<tr>
	<th><label>Incaricato Estero</label></th>
	<td>
		<input onchange="disable('S')" 
				id="<%= ParametriServlet.FLAG_ESTERO%>" 
				type="radio" name="<%= ParametriServlet.FLAG_ESTERO%>" 
				value="<%=Costanti.FLAG_VALORE_SI%>" 
				<%= flag.equals( Costanti.FLAG_VALORE_SI) ? "checked" : ""%>  
				<%=disabled1 %>/>SI 
		<input onchange="disable('N')" 
				id="<%= ParametriServlet.FLAG_ESTERO %>" 
				type="radio" name="<%= ParametriServlet.FLAG_ESTERO%>" 
				value="<%=Costanti.FLAG_VALORE_NO%>" 
				<%= flag.equals( Costanti.FLAG_VALORE_NO) ? "checked" : ""%> 
				<%=disabled1 %>/>NO
	</td>	
</tr>

<tr>
	<th><label for="">Codice Fiscale</label></th>
	<td>
		<input id="cFiscale" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Cognome</label></th>
	<td>
		<input id="Cognome" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Nome</label></th>
	<td>
		<input id="Nome" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_NOME %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Telefono</label></th>
	<td>
		<input id="Telefono" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_TELEFONO%>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Fax</label></th>
	<td>
		<input id="Fax" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_FAX %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_FAX %>' />" />
	</td>
</tr>
<tr>	
	<th><label for="">Email</label></th>
	<td>
		<input id="Email" type="text" maxlength="64" name ="<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>' />" />
	</td>
</tr>
<tr>
	<th><label for="">Indirizzo</label></th>
	<td>
		<input id="Indirizzo" type="text" maxlength="100" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>' />" />
	</td>	
</tr>				
<tr>
	<th><label for="">Cap</label></th>
	<td>
		<input id="Cap" type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CAP %>' />" />
	</td>	
</tr>
<%
String istat = (String)request.getAttribute(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT) ;
if(istat==null)
	istat = (String)request.getParameter(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT) ;

if(istat == null){istat = "";}
%>
<input type="hidden" id="canSearch" value="true" />
<tr>
	<th><label for="">Comune Istat</label></th>
	<td >
		<input 
			   maxlength="9" 
			   tabindex="<%=++indiceTab%>" <c:out value="${disabled}" /> 
			   type="text" name = "<%= ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT %>"
			   value="<%=istat %>" 
			   id="sel_ISTAT" onblur="searchLE(this.id, 'ricercaIstat.jsp', 'isNotIstat')" 
				   onkeyup="checkKeyLE(event, this, 'ricercaIstat.jsp', 'isNotIstat')"/>
		<c:if test="${hide != true}">
			<a class="getCPV" href="#"  onclick="apripopup('ricercaIstat.jsp','sel_ISTAT')" 
				title="Lista codici ISTAT"><img src="img/icon_info_sml.gif"></a>
		</c:if>
	</td>
</tr>

</tbody>
</table>
</fieldset>

</div>
<input type="button" onclick="doSubmit('Salva')"   value="Salva">
<input type="button" onclick="doSubmit('Indietro')"  value="Indietro">

<% } %>
</div>
</div>

</form>
<%@ include file="include/newfooter.inc" %>
</div>


</body>
</html>
