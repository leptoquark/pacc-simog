<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*"%>
<%@ page import="it.avlp.simog.db.Costanti"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>

<%@ include file="/script/domUtilsNew.js" %>

<%@ include file="../include/gestisciErrore.inc" %>
<%@ include file="../include/basicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>

	<title><%= request.getParameter("titlePopup") %></title>
	<base target="_self" />

<script type="text/javascript" src="script/pageutils.js"></script>
<%@ include file="../include/i18n-init.inc" %>

<script type="text/javascript">
//<!--
function getSender(wnd) {
	if(wnd.dialogArguments)
		return wnd.dialogArguments.Sender;
	else return wnd.opener;
}

function loadDocumenti(idrow, args, argshidden, prefix, readonly){
	var padre = getSender(window); //window.opener;
	var idDocField = "hidden" + idrow + "<%= PSReq.FIELD_NAME_REQ_DOC_LISTA_DOCUMENTI %>";
	var docField = padre.document.getElementById(idDocField);
	if( docField!=null && docField!="" ){
		var docs = docField.value.split("~");
		var campi = null;
		for (i=0; i<docs.length-1; i++){
			 campi = docs[i].split("|");
			 var newid = addRowByArray(args,argshidden,prefix, campi);
			 if(readonly){
			 	var aCol = document.getElementById(newid+"ActionCol");
			 	aCol.parentNode.removeChild(aCol);
			 } else {
				 hideActionButton(newid, campi[6]);
			 }
		}
	}
}

function hideActionButton(newid, codiceComposto){
	 var codice = codiceComposto.split("_")[1];
	 var selectDocOB = document.getElementById("mappaDocumentiObbligatori");
	 var targetIndex = cercaIndex(selectDocOB.options, 0, codice);
	 if( targetIndex >= 0 ){
		 document.getElementById(newid+"SetForModifyRow").href="javascript:void(null)";
		 document.getElementById(newid+"SetForModifyRow").id="disabledMenu";
		 document.getElementById(newid+"DeleteRow").href="javascript:void(null)";
		 document.getElementById(newid+"DeleteRow").id="disabledMenu";
	 }
}

function salvaDocumenti(idrow, parametri, prefix){
	var idtable = "idTabella"+prefix;
	var table = document.getElementById(idtable);
	var lista = "";
	var record = "";
	if(table != null){
		var numrows = table.rows.length; 
		//meno la prima riga delle intestazioni
		for(i=1; i<numrows; i++){
			var rowId = table.rows[i].id;
			record="";
			for(j=0; j<parametri.length; j++){
				var idParam = "hidden" + rowId + parametri[j];
				var param = document.getElementById(idParam);
				if( param != null){
					record = record + param.value + "|";
				}
			}
			lista = lista + record + "~";
		}
	   var padre = getSender(window); //window.opener;
	   var idDocField = "hidden" + idrow + "<%= PSReq.FIELD_NAME_REQ_DOC_LISTA_DOCUMENTI %>";
	   var docField = padre.document.getElementById(idDocField);
	   docField.value = lista;
	   window.close();
	}
}

//Abilita il campo descrizione
function editDocumento(tipoDoc, fields){
	if( tipoDoc.value.split("_")[1] == "<%= PSReq.CODICE_REQUISITO_NON_CODIFICATO %>" ){
		for(i=0; i<fields.length; i++){
			var element = document.getElementById(fields[i]);
			element.readOnly = "";
			element.value = "";
		}
	} else {
		for(i=0; i<fields.length; i++){
			var element = document.getElementById(fields[i]);
			element.readOnly = "readOnly";
			element.value = "";
			if( fields[i] == "<%= PSReq.FIELD_NAME_REQ_DOC_DESCRIZIONE %>" ){
				element.value = getSelectValue(tipoDoc, false);
			}
		}		
	}
}

function checkBeforeAdd(prefix){
	var tipoField = document.getElementById("<%= PSReq.FIELD_NAME_REQ_DOC_TIPO %>");
	if( tipoField == null || tipoField.value == null || tipoField.value == "" ){
		if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.selectDocumentType'); } else { alert("Tipo Documento: Campo non valorizzato"); }
		return false;
	}
	var descrField = document.getElementById("<%= PSReq.FIELD_NAME_REQ_DOC_DESCRIZIONE %>");
	if( descrField == null || descrField.value == null || descrField.value == "" ){
		if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.selectDescription'); } else { alert("Descrizione: Campo non valorizzato"); }
		return false;
	}
	return true;
}

function afterSetForModifyRow(idrow, args, argshidden, prefix){
	//setta la combo "Tipo Documento""
	var field = document.getElementById("<%= PSReq.FIELD_NAME_REQ_DOC_TIPO %>");
	var hiddenField = document.getElementById("hidden" + idrow + "<%= PSReq.FIELD_NAME_REQ_DOC_TIPO %>");
	//alert("field.value [" + field.value + "]  hiddenField.value[" + hiddenField.value+"]");
	if( field != null && hiddenField != null ){
		field.value = hiddenField.value;
		//abilita/disabilita i campi
		if( field.value.split("_")[1] == "<%= PSReq.CODICE_REQUISITO_NON_CODIFICATO %>" ){
			for(i=0; i<args.length; i++){
				var element = document.getElementById(args[i]);
				element.readOnly = "";
			}			
		} else {
			for(i=0; i<args.length; i++){
				var element = document.getElementById(args[i]);
				element.readOnly = "readOnly";
			}				
		}
	}
}

//-->
</script>

</head>
  
<% //Gestione documenti requisiti precaricati
	String reqTipoUso = request.getParameter("reqUso");
	boolean modificabile = false;
	
	if (SimogFlags.is3028_RFWEBGL05Active())
	   modificabile = true;
	else
	   modificabile = reqTipoUso != null ? PSReq.USO_FA.equals(reqTipoUso) : false;
	
	
	modificabile = modificabile && !"VIS".equals(request.getParameter(ParametriServlet.FROM_RICERCA));
	modificabile = modificabile && !"true".equals(request.getParameter(PSReq.BLOCCO_AVCPASS));
%>
<% String idrow = request.getParameter("idrow"); %>
<c:set var="listaDocumenti" value="<%= PSReq.LISTA_DOCUMENTI %>" scope="page" />
<c:set var="listaDocumentiOB" value="<%= PSReq.LISTA_DOCUMENTI_OB %>" scope="page" />


<body style="min-width: 650px;">

	<fieldset>				
	<legend>Requisito</legend>
	<table style="font-size: x-small; width: 100%">
	  <tbody>
	    <tr>	
			<th class="garaTh" align="left" width="30%">Descrizione</th>					
		   <td class="garaTd" align="left"><%= request.getParameter("reqDescrizione") %></td>
		 </tr>
	    <tr>	
			<th class="garaTh" align="left" width="30%">Valore</th>					
		   <td class="garaTd" align="left"><%= request.getParameter("reqValore") %></td>
		 </tr>		 
	</table>
	</fieldset>

	<fieldset>
		<legend><utils:message key="scheda.elencoDocumenti" /></legend>
	
		<% String prefixDoc = PSReq.PREFIX_REQUISITO_DOC; %>
		<c:set var="prefixDoc" value="<%= prefixDoc %>" scope="page" />	
		
		<div id="DIVTabella<%= prefixDoc %>" class="scrollTabs" style="height: 200px; width: 99%;">
			<table id="idTabella<%=prefixDoc%>" style="font-size: x-small; width: 100%">
				<thead>
					<tr>
						<% if( user.isRUP()  && modificabile ){ %>
						<th width="125px"  align="left">Azione</th>
						<% } %>
						<th class="garaTh" align="left">Descrizione</th>
						<th class="garaTh" align="left">Emettitore</th>
						<th class="garaTh" align="left">Telefono</th>
						<th class="garaTh" align="left">Fax</th>
						<th class="garaTh" align="left">Mail</th>
						<th class="garaTh" align="left">Mail Pec</th>
					</tr>
				</thead>
				<tbody>
					<%-- documenti precaricati all'apertura del popup --%>			
				</tbody>
			</table>
		</div>
	</fieldset>
		
<% if( user.isRUP() && modificabile){ %>
	<div class="hmenu">
		<a id="showHide<%= prefixDoc %>Button" href="javascript:showSezioneAggiungi([<%= PSReq.argsReqGaraDoc %>],[<%= PSReq.argsReqGaraDocNascosti %>],'<%= prefixDoc %>')" title="<utils:message key="scheda.aggiungiDocumento" plain="true" />"><utils:message key="scheda.aggiungiDocumento" /></a>
	</div>

	<div id="divAgg<%= prefixDoc %>"
		style="background-color: #E5E5E5; display: none; border: 1px solid #cfcfcf;">
		<table style="font-size: x-small;">
			<tr>
				<th><label>Tipo Documento</label></th>
				<td>
					<select id="<%= PSReq.FIELD_NAME_REQ_DOC_TIPO %>"
						style="width: 365px"
						onchange="editDocumento(this,[<%= PSReq.argsReqGaraDoc %>])">
						<option value=""></option>
						
						<c:forEach items="${requestScope[listaDocumenti]}" var="documento">
						
							<option value="<c:out value="${documento.codice_tipo_doc}_${documento.codice}"/>" title="<c:out value="${documento.descrizione_documento}"/>">
								<c:out value="${documento.descrizione_documento}"/>
							</option>
						
						</c:forEach>
					</select>
					
					<div style="display:none;">
					<select id="mappaDocumentiObbligatori" hidden="true">
						<c:forEach items="${requestScope[listaDocumentiOB]}" var="documento">
							<option value="<c:out value="${documento.codice}"/>">
								<c:out value="${documento.codice}"/>
							</option>
						</c:forEach>
					</select>				
					</div>
				</td>
			</tr>
			<tr>
				<th><label>Descrizione</label></th>
				<td><input readOnly type="text" id="<%= PSReq.FIELD_NAME_REQ_DOC_DESCRIZIONE %>" maxlength="800" size="80" value="" /></td>
			</tr>
			<tr>
				<th><label>Emettitore</label></th>
				<td><input readOnly type="text" id="<%= PSReq.FIELD_NAME_REQ_DOC_EMETTITORE %>" maxlength="300" size="80" value="" /></td>
			</tr>
			<tr>
				<th><label>Telefono</label></th>
				<td><input readOnly type="text" id="<%= PSReq.FIELD_NAME_REQ_DOC_TELEFONO %>" maxlength="20" size="20" value="" onblur="validateNumber(this)"/></td>
			</tr>
			<tr>
				<th><label>Fax</label></th>
				<td><input readOnly type="text" id="<%= PSReq.FIELD_NAME_REQ_DOC_FAX %>" maxlength="20" size="20" value="" onblur="validateNumber(this)"/></td>
			</tr>
			<tr>
				<th><label>Mail</label></th>
				<td><input readOnly type="text" id="<%= PSReq.FIELD_NAME_REQ_DOC_MAIL %>" maxlength="80" size="80" value="" /></td>
			</tr>
			<tr>
				<th><label>Mail Pec</label></th>
				<td><input readOnly type="text" id="<%= PSReq.FIELD_NAME_REQ_DOC_MAIL_PEC %>" maxlength="80" size="80" value="" /></td>
			</tr>
			<tr>
				<td class="hmenu">
					<a id="AddMod<%= prefixDoc %>" href="javascript:addRow([<%= PSReq.argsReqGaraDoc %>],[<%= PSReq.argsReqGaraDocNascosti %>],'<%=prefixDoc%>')">Aggiungi</a>
				</td>
			</tr>
		</table>
	</div>
<% } %>

<br/>

<input type="hidden" id="selected<%= prefixDoc %>" value="0" />
<input type="hidden" id="Modificato0" name ="Modificato0>" value="0">

</fieldset>

<input type="button" value="Torna" onclick="chiudiPopUp()">

<% if( user.isRUP() && modificabile){ %>
<input type="button" 
	id="salva" 
	name="salva" 
	value="Salva"
	onclick="javascript:salvaDocumenti('<%= idrow %>',[<%= PSReq.argsReqGaraDocNascosti %>],'<%= prefixDoc %>');">
<% } %>
	
<script type="text/javascript">
//<!--
	window.onload = function(){
		loadDocumenti('<%= idrow %>', [<%= PSReq.argsReqGaraDoc %>], [<%= PSReq.argsReqGaraDocNascosti %>],'<%= prefixDoc %>',<%= !user.isRUP() || !modificabile %>);
	}
//-->
</script>

</body>
</html>
	