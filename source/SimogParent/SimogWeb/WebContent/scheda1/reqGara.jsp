<%@ page import="it.avlp.simog.db.SimogFlags"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ page import="it.avlp.simog.db.Costanti"%>
<%@ page import="it.avlp.simog.common.servlet.PSReq"%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%try{ %>

<% String prefixReqGara = PSReq.PREFIX_REQUISITO_GARA; 
	boolean allowMod = user.isRUP() && !"VIS".equals(fromRicerca);
	
	if(SimogFlags.is3028_RFWEBGL02Active() && allowMod && !"".equals(garaDataPerfezionamento))
	   allowMod = false;
	if(SimogFlags.is3028_RFWEBGL07Active() && allowMod && bloccoAVCPASS)
	   allowMod = false;
	if( SimogFlags.is3030_RFWEBGL02Active() ){
	   allowMod = true;
	}
%>

<c:set var="readonlyReqGara" value="${(readonly eq true)}" />	
<c:set var="readonlyReqGaraStr" value="${readonlyReqGara eq true ? 'readonly' : ''} " />
<c:set var="prefixReqGara" value="<%= prefixReqGara %>" scope="page" />
<c:set var="listaRequisitoGara" value="<%= PSReq.LISTA_REQUISITI_GARA %>" scope="page" />
<c:set var="requisitiMap" value="<%= PSReq.MAPPA_REQUISITI %>" scope="page" />
<c:set var="requisitiPerTipologiaMap" value="<%= PSReq.MAPPA_REQUISITI_PER_TIPOLOGIA %>" scope="page" />
<c:set var="requisitiOBMap" value="<%= PSReq.MAPPA_REQUISITI_OB %>" scope="page" />
<c:set var="requisitiUsoMap" value="<%= PSReq.MAPPA_REQ_F_USO %>" scope="page" />
<c:set var="reqDocOBMap" value="<%= PSReq.MAPPA_REQ_DOC_OB %>" scope="page" />
<c:set var="listaLotti" value="<%= PSReq.LISTA_LOTTI %>" scope="page" />
<c:set var="maxPageElements" value="<%= PSReq.ELEMENT_FOR_PAGE %>" scope="page" />
<c:set var="showMultiselectHeader" value="<%= allowMod %>" scope="page" />

<c:set var="uso_ob" value="<%= PSReq.USO_OB %>" scope="page" />
<c:set var="uso_om" value="<%= PSReq.USO_OM %>" scope="page" />
<c:set var="uso_ar" value="<%= PSReq.USO_AR %>" scope="page" />
<c:set var="uso_fa" value="<%= PSReq.USO_FA %>" scope="page" />

<fmt:parseNumber var="maxPages" value="${(fn:length(requestScope[listaLotti]) / maxPageElements)}" type="number" pattern="#" integerOnly="true"/>

<%@ include file="/script/domUtilsRequisiti.js" %>

<style>
	.precaricatoStyle td {
		background-color: #dddddd;
	}
	.precaricatoStyle td#tdRadio {
		background-color: #f1f2f8;
	}
	.precaricatoStyle td#tdAction {
		background-color: #f1f2f8;
	}
</style>

<script type="text/javascript">
//<!--

var MESSAGE_NO_SELECT_REQUISITO = "Selezionare un requisito";
var MESSAGE_OPERAZIONE_EFFETTUATA = "Operazione effettuata";
var MESSAGE_CONFERMA_ASSOCIAZIONE = "Confermi l'associazione ?";
var MESSAGE_CONFERMA_ELIMINAZIONE = "Confermi l'eliminazione ?";

var maxPage = <c:out value="${maxPages}"/>; //Numero di pagine
var currentPage = 0; //Indice pagina corrente

function afterSetForModifyRow(idrow, args, argshidden, prefix) {
	//setta la combo "Requisito"
	var field = document.getElementById("<%= PSReq.FIELD_NAME_REQ_REQUISITO %>");
	var hiddenField = document.getElementById("hidden" + idrow + "<%= PSReq.FIELD_NAME_REQ_REQUISITO %>");
		
	if( field != null && hiddenField != null ){
		field.disabled = false;

<% if( SimogFlags.is3028_RFWEBGL03Active() ){ %>
		var tipoUso = document.getElementById("hidden" + idrow + "<%= PSReq.FIELD_NAME_REQ_TIPO_USO %>");
		var fieldDesc = document.getElementById("<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>");
		if( tipoUso.value == "<%= PSReq.USO_FA %>") { 
			field.disabled = false; 
			fieldDesc.disabled = false;
		} else { 
			field.disabled = true;  
			fieldDesc.disabled = true;
		}
<% } %>

		field.value = hiddenField.value;
	}
}

function afterAddRow(newid, args, argshidden, prefix) {
	//Valorizzare il campo listaDocumenti
	var selectReqDocMap = document.getElementById("<%= PSReq.FIELD_NAME_REQ_DOC_OB_MAP %>");
	var hiddenReqCodice = document.getElementById("hidden" + newid + "<%= PSReq.FIELD_NAME_REQ_REQUISITO %>");
	var hiddenListaDocumenti = document.getElementById("hidden" + newid + "<%= PSReq.FIELD_NAME_REQ_DOC_LISTA_DOCUMENTI %>");
	var targetIndex = cercaIndexValue(selectReqDocMap, 0, hiddenReqCodice.value);
	hiddenListaDocumenti.value = selectReqDocMap.options[targetIndex].text;

	//Privilegi Modifica-Cancellazione
	var selectReqUsoMap = document.getElementById("<%= PSReq.FIELD_NAME_REQ_F_USO_MAP %>");
	var reqIndex = cercaIndexValue(selectReqUsoMap, 0, hiddenReqCodice.value);
	var reqTipoUsoHidden = document.getElementById("hidden"+newid+"<%= PSReq.FIELD_NAME_REQ_TIPO_USO %>");
	reqTipoUsoHidden.value = selectReqUsoMap.options[reqIndex].text;
	var linkmod = document.getElementById(newid+"SetForModifyRow");
<% if(!SimogFlags.is3028_RFWEBGL03Active()){ %>	
	if( reqTipoUsoHidden.value != "<%= PSReq.USO_FA %>" ){
		linkmod.id = "disabledMenu";
		linkmod.href = null;
	}
<%}%>	
	<%-- linkmod.hidden = (reqTipoUsoHidden.value != "<%= PSReq.USO_FA %>" ); --%> 
	var reqAssegnabile = (reqTipoUsoHidden.value == "<%= PSReq.USO_FA %>" );
	var radio = document.getElementById("radio"+newid);
	radio.disabled = !reqAssegnabile;
	
	if( reqAssegnabile ) {
		//Aggiungere il requisito alla select accanto al Lotto
		var oRows = document.getElementById("idTabella<%= prefixReqGara %>Lotti").getElementsByTagName("tr");
		for (var i = 0; i < oRows.length-1; i++) {  
			var select = document.getElementById("idSelect" + prefix + "SelectLotto" + i);
			if (select != null) {
				var key = document.getElementById("radio" + newid).value;
				var value = document.getElementById(newid + "<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>").childNodes[0].data;
				if( key != null && value != null ){
					var option = document.createElement("option");
					option.setAttribute("value", key);
					option.appendChild(document.createTextNode(value));
					select.appendChild(option);
					$("#"+select.id).multiselect("refresh");
				}
			}
		}
	}
	// var indexReq = newid.charAt( newid.length-1 );
	var indexReq = newid.replace("row<%= prefixReqGara %>","");
	aggiornaCampoStato(prefix, indexReq)
}

function beforeDeleteRow(idrow, args, argshidden, prefix) {
	//Elimina il requisito dalla select accanto al Lotto
	var oRows = document.getElementById("idTabella<%= prefixReqGara %>Lotti").getElementsByTagName("tr");
	for (var i = 0; i < oRows.length-1; i++) {  
		var select = document.getElementById("idSelect"+prefix+"SelectLotto"+i);
		if (select != null) {
			var radioValue = document.getElementById("radio" + idrow).value;
			var targetOptionIndex = cercaIndexValue(select.options, 0, radioValue);
			if( targetOptionIndex >= 0 ){
				var option = select.options[targetOptionIndex];
				select.removeChild(option);
				$("#"+select.id).multiselect("refresh");
			}
		}
	}
}


//Abilita il campo descrizione
function editDescrizione(requisito, idElement, fromSelect){
	var element = document.getElementById(idElement);
	 if( requisito.value > <%= PSReq.MARKER_999 %> ){
		element.readOnly = "";

		if(fromSelect)
			element.value = "";

	} else {
		element.value = getSelectValue(requisito, false);
		element.readOnly = "readonly";
	}


     var found = false;
	 var elements = requisito.options;

	    for(var i = 0; i < elements.length; i++){
		      if(elements[i].value==requisito.value){
	             var optGroup = elements[i].parentElement;
	             if(optGroup.label == "REQUISITO DI ORDINE GENERALE"){
	            	 found = true;
	            	 $("select[name=FLG_AVVALIMENTO]").val('N');
	            	 $("select[name=FLG_AVVALIMENTO]").prop("disabled",true); 
	            	 break;     
	             }
			   }
	    }

	    if(!found)
	    	$("select[name=FLG_AVVALIMENTO]").removeAttr("disabled");
    
}


function getMaxRowIndex(){
	var oRows = document.getElementById("idTabella<%= prefixReqGara %>").getElementsByTagName("tr");
	var lastTRid = oRows[oRows.length-1].id;
	var numero = lastTRid.replace("row<%= prefixReqGara %>","");
	return numero;
	//return parseInt(lastTRid.charAt( lastTRid.length-1 ));
}

//Segna/Elimina il requisito ai lotti selezionati
function impostaReqLottiSel(idrow, prefix, selected){
	var radioSelectedValue = document.getElementById("requisitoSelezionato").value;
	var oRows = document.getElementById("idTabella<%= prefixReqGara %>Lotti").getElementsByTagName("tr");
	for (var i = 0; i < oRows.length-1; i++) {  
		var checkLotto = document.getElementById("check" + idrow + i);
		if( checkLotto.checked ){
			var select = document.getElementById("idSelect" + idrow + i);
			if (select != null) {
				var indexSelectedValue = cercaIndexValue(select.options, 0, radioSelectedValue);
				if(select.options[indexSelectedValue].selected != selected){
					select.options[indexSelectedValue].selected = selected;
					$("#"+select.id).multiselect("refresh");
				}
			}
		}
	}	
	aggiornaCampoStato(prefix, radioSelectedValue);
	alert(MESSAGE_OPERAZIONE_EFFETTUATA);
}

function assegnaReqLottiSel(idrow, prefix){ 
	if (confirm(MESSAGE_CONFERMA_ASSOCIAZIONE)){
		var radioSelectedValue = document.getElementById("requisitoSelezionato").value;
		if(radioSelectedValue == -1){
			alert(MESSAGE_NO_SELECT_REQUISITO);
			return;
		}
		impostaReqLottiSel(idrow, prefix, true); 
		resetRadioCheckbox();
	}
}

function eliminaReqLottiSel(idrow, prefix){ 
	if (confirm(MESSAGE_CONFERMA_ELIMINAZIONE)){
		var radioSelectedValue = document.getElementById("requisitoSelezionato").value;
		if(radioSelectedValue == -1){
			alert(MESSAGE_NO_SELECT_REQUISITO);
			return;
		}		
		impostaReqLottiSel(idrow, prefix, false); 
		resetRadioCheckbox();
	}
}

//Elimina il requisito selezionato da tutti i lotti
function eliminaReqTuttiLotti(idrow, prefix){
	if (confirm(MESSAGE_CONFERMA_ELIMINAZIONE)){
		var radioSelectedValue = document.getElementById("requisitoSelezionato").value;
		if(radioSelectedValue == -1){
			alert(MESSAGE_NO_SELECT_REQUISITO);
			return;
		}
		var oRows = document.getElementById("idTabella<%= prefixReqGara %>Lotti").getElementsByTagName("tr");
		for (var i = 0; i < oRows.length-1; i++) {  
			var select = document.getElementById("idSelect" + idrow + i);
			if (select != null) {
				var indexSelectedValue = cercaIndexValue(select.options, 0, radioSelectedValue);
				select.options[indexSelectedValue].selected = false;
				$("#"+select.id).multiselect("refresh");
			}
		}	
		aggiornaCampoStato(prefix, radioSelectedValue);
		resetRadioCheckbox();
		alert(MESSAGE_OPERAZIONE_EFFETTUATA);
	}
}

//Elimina tutti i requisiti da tutti i lotti
function eliminaTuttiReqTuttiLotti(idrow, prefix){
	if (confirm(MESSAGE_CONFERMA_ELIMINAZIONE)){
		var oRows = document.getElementById("idTabella<%= prefixReqGara %>Lotti").getElementsByTagName("tr");
		for (var i = 0; i < oRows.length-1; i++) {  
			var select = document.getElementById("idSelect" + idrow + i);
			if (select != null) {
				$("#"+select.id+" option").attr("selected",false);
				$("#"+select.id).multiselect("uncheckAll");
				$(".requisitiRadio").each(function(index){
					aggiornaCampoStato('<%= prefixReqGara %>', this.value);
				});
			}
		}		
		resetRadioCheckbox();
		alert(MESSAGE_OPERAZIONE_EFFETTUATA);
	}		
}

function resetRadioCheckbox(){
	$(".requisitiRadio").attr('checked',false);
	$(".checkboxLotto").attr('checked',false);
	$("#requisitoSelezionato").attr("value","-1");
}

function salva(){
	if(confirm("I dati saranno salvati, Confermi?")){
		document.requisitiGaraForm.<%= PSReq.MAX_INDEX_REQUISTI %>.value = getMaxRowIndex();
		document.requisitiGaraForm.<%= PSReq.SRV_ACTION_NAME %>.value = "<%= PSReq.ACTION_SALVA %>";
		document.requisitiGaraForm.submit();
	}
}

function elimina(){
	if(confirm("Tutti i requisiti saranno eliminati, Confermi?")){
		document.requisitiGaraForm.<%= PSReq.MAX_INDEX_REQUISTI %>.value = getMaxRowIndex();
		document.requisitiGaraForm.<%= PSReq.SRV_ACTION_NAME %>.value = "<%= PSReq.ACTION_ELIMINA %>";
		document.requisitiGaraForm.submit();
	}
}

function checkBeforeAdd(prefix){
	var reqField = document.getElementById("<%= PSReq.FIELD_NAME_REQ_REQUISITO %>");
	if( reqField == null || reqField.value == null || reqField.value == "" ){
		alert("Requisito: Campo non valorizzato");
		return false;
	}
	var descrField = document.getElementById("<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>");
	if( descrField == null || descrField.value == null || descrField.value == "" ){
		alert("Descrizione: Campo non valorizzato");
		return false;
	}
	return true;
}

function apriPopUpDocumentiRequisito(idrow, args, argshidden, prefix, url, titlePopup, parametri) {
	parametri += "reqDescrizione=" + document.getElementById(idrow+"<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>").innerHTML;
	parametri += "&reqValore=" + document.getElementById(idrow+"<%= PSReq.FIELD_NAME_REQ_VALORE %>").innerHTML;
	var reqTipoUsoHidden = document.getElementById("hidden"+idrow+"<%= PSReq.FIELD_NAME_REQ_TIPO_USO %>");
	if( reqTipoUsoHidden != null ){ parametri += "&reqUso=" + reqTipoUsoHidden.value; }
	else { parametri += "&reqUso=F"}
	var reqCodiceHidden = document.getElementById("hidden"+idrow+"<%= PSReq.FIELD_NAME_REQ_REQUISITO %>");
	if( reqCodiceHidden != null ){ parametri += "&reqCodice=" + reqCodiceHidden.value; }
	parametri += "&<%= ParametriServlet.FROM_RICERCA %>=<%= fromRicerca %>" ;
	parametri += "&<%= PSReq.BLOCCO_AVCPASS %>=<%= bloccoAVCPASS %>" ;
	apriPopUp(idrow, args, argshidden, prefix, url, titlePopup, parametri);
}

/** Paginazione **/

function nextPage(){
	if(currentPage < maxPage){
		$('.pagina'+currentPage).hide();
		$("select[tabindex='"+currentPage+"']").multiselect("destroy");
		currentPage++;
		$("select[tabindex='"+currentPage+"']").css('width', '200px');
		$("select[tabindex='"+currentPage+"']").multiselect({header: <c:out value="${showMultiselectHeader}"/> });
		$('.pagina'+currentPage).show();	
		setNavPaginazione();
		setShowButton();
	}
}

function prevPage(){
	if(currentPage > 0){
		$('.pagina'+currentPage).hide();
		$("select[tabindex='"+currentPage+"']").multiselect("destroy");
		currentPage--;
		$("select[tabindex='"+currentPage+"']").css('width', '200px');
		$("select[tabindex='"+currentPage+"']").multiselect({header: <c:out value="${showMultiselectHeader}"/> });
		$('.pagina'+currentPage).show();	
		setNavPaginazione();
		setShowButton();
	}
}

function firstPage(){
	if( currentPage != 0 ){
		$("#idTabella<%= prefixReqGara %>Lotti tbody tr").hide();
		$(".selectLotto").multiselect("destroy");
		currentPage = 0;
		$("select[tabindex='0']").css('width', '200px');
		$("select[tabindex='0']").multiselect({header: <c:out value="${showMultiselectHeader}"/> });
		$(".pagina0").show();
		setNavPaginazione();
		setShowButton();
	}
}

function lastPage(){
	if( currentPage < maxPage ){
		$("#idTabella<%= prefixReqGara %>Lotti tbody tr").hide();
		$(".selectLotto").multiselect("destroy");
		currentPage = maxPage;
		$("select[tabindex='"+maxPage+"']").css('width', '200px');
		$("select[tabindex='"+maxPage+"']").multiselect({header: <c:out value="${showMultiselectHeader}"/> });
		$(".pagina"+maxPage).show();	
		setNavPaginazione();
		setShowButton();
	}
}

//Gestisce la label che indica la pagina corrente
function setNavPaginazione(){
	var navPage = document.getElementById("navPaginazione");
	navPage.innerHTML = "Pagina " + (currentPage+1) + " di " + (maxPage+1);
}

// Gestisce la disabilitazione dei pulsanti di paginazione
function setShowButton(){
	if(currentPage == maxPage){
		$('#fisrt a').removeAttr("id");
		$('#prev a').removeAttr("id");
		$('#next a').attr("id","disabledMenu");
		$('#last a').attr("id","disabledMenu");
	} 
	else if(currentPage == 0){
		$('#fisrt a').attr("id","disabledMenu");
		$('#prev a').attr("id","disabledMenu");
		$('#next a').removeAttr("id");
		$('#last a').removeAttr("id");	
	}
	else {
		$('#fisrt a').removeAttr("id");
		$('#prev a').removeAttr("id");
		$('#next a').removeAttr("id");
		$('#last a').removeAttr("id");	
	}
}


function setLabel(idElem, text){
	var elem = document.getElementById(idElem);
	elem.innerHTML = text;
} 

function aggiornaCampoStato(prefix, indexReq){
	var numAssegnazioni = $(".selectLotto option[value='" + indexReq + "']:selected").length;
	if(numAssegnazioni == 0){
		setStato("row"+prefix+indexReq, "Valido per tutti i lotti");
	} else {
		setStato("row"+prefix+indexReq, "Assegnato a " + numAssegnazioni + " lotti");
	}
}

//Setta il campo stato della tabella Requisiti
function setStato(idrow, text){
	var stato = document.getElementById(idrow + "Stato");
	stato.childNodes[0].data = text;
}

//Seleziona/Deseleziona tutti i checkbox lotto
function allSelect(checked){
	$(".checkboxLotto[tabindex='" + currentPage + "']").each(function(){
		this.checked = checked;  
	})
	var selezionati = $(".checkboxLotto:checked").length;
	setLabel("labelSelezionati","Selezionati " + selezionati + " lotti");	
}

<% if( SimogFlags.is3028_RFWEBGL03Active() ){ %>
	// Abilita campi del requisito che potrebbero essere disabilitati
	function afterShowSezioneAggiungi(args,argshidden,prefix) {
		var fieldReq = document.getElementById("<%= PSReq.FIELD_NAME_REQ_REQUISITO %>");
		var fieldDesc = document.getElementById("<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>");
		fieldReq.disabled = false;
		fieldDesc.disabled = false;
	
		// is3029_MAC01Active preseleziono si su avvalimento, solo in aggiunta
		<% if( SimogFlags.is3029_MAC01Active() ){ %>
			var fieldAvv = document.getElementById("<%= PSReq.FIELD_NAME_REQ_AVVALIMENTO%>");
			var bottone = document.getElementById("AddModRequisitoGara");
			
			if(bottone.text != "Modifica")
				fieldAvv.selectedIndex = 1;
		<% } %>
	}
<% } %>

//-->
</script>

<form id="idRequisitiGaraForm"" name="requisitiGaraForm" action="<%= ParametriServlet.SRV_VISUALIZZA_DETTAGLIO %>" method="post">
<input type="hidden" name="<%= PSReq.SRV_ACTION_NAME %>" value=""/>

<div class="scrollInside">		
	<div class="gara">
	   <div id="PanelHead">
			<label style="color: black; letter-spacing: 0.2em;" onclick="showElem('PanelBody')">
			<img id="imgPanelBody" src="img/minus.gif"/> REQUISITI DEFINITI</label>
		</div>
		<div id="PanelBody" style="display: block;">
			<br/>
			<div id="DIVTabella<%= prefixReqGara %>"  class="scrollTabs" style="height: 200px; width: 99%; border: 0px;">
				<table id="idTabella<%= prefixReqGara %>">
					<tbody>
					<tr>
						<% if( allowMod ){ %>
						<th class="garaTh">Sel</th>
						<th class="garaTh" width="125px">Azione</th>
						<% } %>
						<th class="garaTh" width="30%" >Requisito</th>
						<th class="garaTh" width="10%" >Valore</th>
						<th class="garaTh" width="90px">Esclusione</th>
						<th class="garaTh" width="90px">Comprova offerta</th>
						<th class="garaTh" width="90px">Avvalimento</th>
						<th class="garaTh" width="90px">Bando tipo</th>
						<th class="garaTh" width="90px">Riservatezza</th>
						<th class="garaTh">Stato</th>
					</tr>

					<c:set var="counter" value="0" scope="page"/>
					<c:forEach items="${requestScope[listaRequisitoGara]}" var="requisito">
						<c:set var="id" value="row${prefixReqGara}${counter}" scope="page"/>
						<c:set var="precaricato" value="${requisito.tipoUso ne uso_fa}" scope="page"/>
						<c:set var="precaricatoStyle" value="${precaricato? 'class=precaricatoStyle' : ''}" scope="page"/>
						
						<tr id="<c:out value="${id}"/>" <c:out value="${precaricatoStyle}"/>>
							<% if( allowMod ){ %>
							<td id="tdRadio" nowrap="nowrap" align="center" class="garaTd" valign="top">
							
								<input type="radio"
										name="<%= prefixReqGara %>Radio"
										class="requisitiRadio"
										id="radio<c:out value="${id}"/>" 
										value="<c:out value="${counter}"/>"
										onclick="document.getElementById('requisitoSelezionato').value = this.value;"
										<c:out value="${precaricato ? 'disabled' : ''}"/>
										/>
							</td>
							
							<td id="tdAction"  nowrap="nowrap" align="center" class="hmenu">
<% if(SimogFlags.is3028_RFWEBGL03Active()){ %>
								<a id="<c:out value="${id}"/>SetForModifyRow" title="Modifica<%= prefixReqGara %>" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSReq.argsReqGara %>],[<%=PSReq.argsReqGaraNascosti%>],'<%=prefixReqGara%>');editDescrizione(document.getElementById('<%= PSReq.FIELD_NAME_REQ_REQUISITO %>'), '<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>', false);">Modifica</a>
								<c:if test="${not precaricato}">
									<a id="<c:out value="${id}"/>DeleteRow" title="Cancella<%= prefixReqGara %>" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSReq.argsReqGara %>],[<%=PSReq.argsReqGaraNascosti%>],'<%=prefixReqGara%>')">Cancella</a>
								</c:if>
								<c:if test="${precaricato}">
									<c:if test="${requisito.tipoUso eq uso_om}">
								  		<a id="<c:out value="${id}"/>DeleteRow" title="Cancella<%= prefixReqGara %>" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSReq.argsReqGara %>],[<%=PSReq.argsReqGaraNascosti%>],'<%=prefixReqGara%>')">Cancella</a>
									</c:if>
									<c:if test="${requisito.tipoUso ne uso_om}">
								   	<a id="disabledMenu" title="Cancella<%= prefixReqGara %>">Cancella</a>
								   </c:if>
								</c:if>
<% } else { %>
								<c:if test="${not precaricato}">
									<a id="<c:out value="${id}"/>SetForModifyRow" title="Modifica<%= prefixReqGara %>" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSReq.argsReqGara %>],[<%=PSReq.argsReqGaraNascosti%>],'<%=prefixReqGara%>');editDescrizione(document.getElementById('<%= PSReq.FIELD_NAME_REQ_REQUISITO %>'), '<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>', false);">Modifica</a>
									<a id="<c:out value="${id}"/>DeleteRow" title="Cancella<%= prefixReqGara %>" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSReq.argsReqGara %>],[<%=PSReq.argsReqGaraNascosti%>],'<%=prefixReqGara%>')">Cancella</a>
								</c:if>
								<c:if test="${precaricato}">
									<a id="disabledMenu" title="Modifica<%= prefixReqGara %>">Modifica</a>
									<c:if test="${requisito.tipoUso eq uso_om}">
								  		<a id="<c:out value="${id}"/>DeleteRow" title="Cancella<%= prefixReqGara %>" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSReq.argsReqGara %>],[<%=PSReq.argsReqGaraNascosti%>],'<%=prefixReqGara%>')">Cancella</a>
									</c:if>
									<c:if test="${requisito.tipoUso ne uso_om}">
								   	<a id="disabledMenu" title="Cancella<%= prefixReqGara %>">Cancella</a>
								   </c:if>
								</c:if>
<% } %>
							</td>
							<% } %>
							
							<td class="garaTd" id="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>"><c:out value="${requisito.descrizione}"/></td>
							<td class="garaTd" id="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_VALORE %>"><c:out value="${requisito.valore}"/></td>
							<td nowrap class="garaTd" id="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_ESCLUSIONE %>"><c:out value="${requisito.flag_esclusione == 'S' ? 'SI' : 'NO'}"/></td>
							<td nowrap class="garaTd" id="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_COMPROVAOFF %>"><c:out value="${requisito.flag_comprova_offerta == 'S' ? 'SI' : 'NO'}"/></td>
							<td nowrap class="garaTd" id="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_AVVALIMENTO %>"><c:out value="${requisito.flag_avvalimento == 'S' ? 'SI' : 'NO'}"/></td>
							<td nowrap class="garaTd" id="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_BANDO_TIPO %>"><c:out value="${requisito.flag_bando_tipo == 'S' ? 'SI' : 'NO'}"/></td>
							<td nowrap class="garaTd" id="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_RISERVATEZZA %>"><c:out value="${requisito.flag_riservatezza == 'S' ? 'SI' : 'NO'}"/></td>
							
							<c:if test="${fn:length(requisito.lotti_associati) ne 0}">
								<td nowrap class="garaTd" id="<c:out value="${id}"/>Stato">Assegnato a <c:out value="${fn:length(requisito.lotti_associati)}"/> lotti</td>
							</c:if>
							<c:if test="${fn:length(requisito.lotti_associati) eq 0}">
								<td nowrap class="garaTd" id="<c:out value="${id}"/>Stato">Valido per tutti i lotti</td>
							</c:if>
							
							<td nowrap="nowrap" align="center" class="hmenu">
								<a title="apriDocumenti<%= prefixReqGara %>" href="javascript:apriPopUpDocumentiRequisito('<c:out value="${id}"/>', '', '', '', '<%= PSReq.SRV_REQUISITI_GL %>', 'Elenco documenti', '')">Documenti</a>
							</td>
							
							<td style="display: none">
							<% if( allowMod ){ %>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>" value="<c:out value="${requisito.descrizione}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_VALORE %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_VALORE %>" value="<c:out value="${requisito.valore}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_ESCLUSIONE %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_ESCLUSIONE %>" value="<c:out value="${requisito.flag_esclusione}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_COMPROVAOFF %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_COMPROVAOFF %>" value="<c:out value="${requisito.flag_comprova_offerta}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_AVVALIMENTO %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_AVVALIMENTO %>" value="<c:out value="${requisito.flag_avvalimento}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_BANDO_TIPO %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_BANDO_TIPO %>" value="<c:out value="${requisito.flag_bando_tipo}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_RISERVATEZZA %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_RISERVATEZZA %>" value="<c:out value="${requisito.flag_riservatezza}"/>"/>
							<% } %>	
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_ID %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_ID %>" value="<c:out value="${requisito.codice_requisito_gara}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_REQUISITO %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_REQUISITO %>" value="<c:out value="${requisito.codice_dettaglio_FE}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_TIPO_USO %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_TIPO_USO %>" value="<c:out value="${requisito.tipoUso}"/>"/>
								<input type="hidden" name="<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_DOC_LISTA_DOCUMENTI %>" id="hidden<c:out value="${id}"/><%= PSReq.FIELD_NAME_REQ_DOC_LISTA_DOCUMENTI %>" value="<c:out value="${requisito.listaDocumentiString}"/>"/>
							</td>
			 			</tr>											
	
						<c:set var="counter" value="${counter + 1}" scope="page"/>

					</c:forEach>
					</tbody>		
				</table>
				<input type="hidden" name="<%= PSReq.MAX_INDEX_REQUISTI %>" value="<c:out value="${counter}"/>"/> 			
			</div>	
 	
		<% if( allowMod ){ %>
		
<!---- START :: GESTIONE INSERIMENTO REQUISITI ---->

			<p>&nbsp;</p>
			<div class="hmenu">
				<a id="showHide<%= prefixReqGara %>Button" 
					href="javascript:showSezioneAggiungi([<%= PSReq.argsReqGara %>],[<%= PSReq.argsReqGaraNascosti %>],'<%=prefixReqGara%>')" 
					title="Aggiungi Requisito">Aggiungi Requisito</a>
			</div>

			<div class="detailHelp" id="divAgg<%= prefixReqGara %>" style="display: none; border: 1px solid #cfcfcf;">
				<table width="100%">				
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_REQUISITO %>">Requisito</label></th>
					<td colspan="2">
					
						<select id="<%= PSReq.FIELD_NAME_REQ_REQUISITO %>" 
								  class="BOTTONE" 
								  style="width: 100%"
								  onchange="editDescrizione(this, '<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>', true)">
							<option value=""></option>
<% if( SimogFlags.is3028_RFWEBGL05Active() ){ %>							
							<c:forEach items="${requestScope[requisitiPerTipologiaMap]}" var="map">
							<c:set var="t_requisitiMap" value="${map.value}" scope="page" />
							<optGroup label="${map.key}">
								<c:forEach items="${t_requisitiMap}" var="tmap">
									<option value="<c:out value="${tmap.key}"/>" title="<c:out value="${tmap.value}"/>"><c:out value="${tmap.value}"/></option>
								</c:forEach>
							</optGroup>
							</c:forEach>
<% } else { %>
							<c:forEach items="${requestScope[requisitiMap]}" var="map">
								<option value="<c:out value="${map.key}"/>" title="<c:out value="${map.value}"/>"><c:out value="${map.value}"/></option>
							</c:forEach>
<% } %>							
							<!-- Caricamento requisiti obbligatori [nascosti] -->
							<c:forEach items="${requestScope[requisitiOBMap]}" var="map">
								<option hidden="true" value="<c:out value="${map.key}"/>"><c:out value="${map.value}"/></option>
							</c:forEach>
							<!-- -->
						</select>
						
						<div style="display:none;">
							<!-- Mappa dei documenti standard di un requisito -->
							<select id="<%= PSReq.FIELD_NAME_REQ_DOC_OB_MAP%>" hidden="true">
								<c:forEach items="${requestScope[reqDocOBMap]}" var="map">
									<option hidden="true" value="<c:out value="${map.key}"/>"><c:out value="${map.value}"/></option>
								</c:forEach>								
							</select>
							
							<!-- Mappa dei tipi uso di un requisito -->						
							<select id="<%= PSReq.FIELD_NAME_REQ_F_USO_MAP%>" hidden="true">
								<c:forEach items="${requestScope[requisitiUsoMap]}" var="map">
									<option hidden="true" value="<c:out value="${map.key}"/>"><c:out value="${map.value}"/></option>
								</c:forEach>								
							</select>
						</div>
						
					</td>	
				</tr>
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>">Descrizione</label></th>
					<td>						
						<input type="text" readonly="readonly"						
								id="<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>" 
								name="<%= PSReq.FIELD_NAME_REQ_DESCRIZIONE %>" 
								maxlength="1024" 
								style="width: 100%"/>
					</td>
				</tr>	
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_VALORE %>">Valore</label></th>
					<td>
						<input type="text" 
								id="<%= PSReq.FIELD_NAME_REQ_VALORE %>" 
								name="<%= PSReq.FIELD_NAME_REQ_VALORE %>" 
								maxlength="20" 
								style="width: 100%"/>
					</td>
				</tr>		
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_ESCLUSIONE %>">Esclusione</label></th>
					<td>
						<select id="<%= PSReq.FIELD_NAME_REQ_ESCLUSIONE %>" name="<%= PSReq.FIELD_NAME_REQ_ESCLUSIONE %>">
							<option value="<%= Costanti.FLAG_VALORE_NO %>" selected="selected">NO</option>
							<option value="<%= Costanti.FLAG_VALORE_SI %>">SI</option>
						</select>
					</td>
				</tr>	
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_COMPROVAOFF %>">Comprova offerta</label></th>
					<td>
						<select id="<%= PSReq.FIELD_NAME_REQ_COMPROVAOFF %>" name="<%= PSReq.FIELD_NAME_REQ_COMPROVAOFF %>">
							<option value="<%= Costanti.FLAG_VALORE_NO %>" selected="selected">NO</option>
							<option value="<%= Costanti.FLAG_VALORE_SI %>">SI</option>
						</select>
					</td>
				</tr>					
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_AVVALIMENTO %>">Avvalimento</label></th>
					<td>
						<select id="<%= PSReq.FIELD_NAME_REQ_AVVALIMENTO %>" name="<%= PSReq.FIELD_NAME_REQ_AVVALIMENTO %>">
							<option value="<%= Costanti.FLAG_VALORE_NO %>" selected="selected">NO</option>
							<option value="<%= Costanti.FLAG_VALORE_SI %>">SI</option>
						</select>
					</td>
				</tr>					
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_BANDO_TIPO %>">Bando tipo</label></th>
					<td>
						<select id="<%= PSReq.FIELD_NAME_REQ_BANDO_TIPO %>" name="<%= PSReq.FIELD_NAME_REQ_BANDO_TIPO %>">
							<option value="<%= Costanti.FLAG_VALORE_NO %>" selected="selected">NO</option>
							<option value="<%= Costanti.FLAG_VALORE_SI %>">SI</option>
						</select>
					</td>
				</tr>					
				<tr>
					<th width="15%"><label for="<%= PSReq.FIELD_NAME_REQ_RISERVATEZZA %>">Riservatezza</label></th>
					<td>
						<select id="<%= PSReq.FIELD_NAME_REQ_RISERVATEZZA %>" name="<%= PSReq.FIELD_NAME_REQ_RISERVATEZZA %>">
							<option value="<%= Costanti.FLAG_VALORE_NO %>" selected="selected">NO</option>
							<option value="<%= Costanti.FLAG_VALORE_SI %>">SI</option>
						</select>
					</td>
				</tr>													
						
				<tr>
					<td class="hmenu">
						<a id="AddMod<%= prefixReqGara %>" href="javascript:addRow([<%= PSReq.argsReqGara %>],[<%= PSReq.argsReqGaraNascosti %>],'<%=prefixReqGara%>')">Aggiungi</a>
					</td>
				</tr>
				</table>

				<input type="hidden" id="Modificato<%= prefixReqGara %>" name ="Modificato<%= prefixReqGara %>" value="0">
			</div>
			
<!---- END :: GESTIONE INSERIMENTO REQUISITI ---->

		<% } %>
		
		</div>
	</div>	
</div>

<% if( allowMod ){ %>
<p>
	<input type="button" value="Conferma Requisiti" onclick="salva()"/>
	<input type="button" value="Elimina tutti i Requisiti" onclick="elimina()"/>
</p>
<% } %>
	
<div class="scrollInside">		
	<div class="gara">
	   <div id="PanelHead2">
			<label style="color: black; letter-spacing: 0.2em;" onclick="showElem('PanelBody2')">
			<img id="imgPanelBody2" src="img/minus.gif"/> ELENCO LOTTI</label>
			<br/>
		</div>
		<div id="PanelBody2">
			<br/>
			
<% if( allowMod ){ %>			
			<div class="hmenu">
<%-- 				<a id="showHide<%= prefixReqGara %>Button" href="javascript:showSezioneAggiungi([<%= PSReq.argsReqGara %>],[<%= PSReq.argsReqGaraNascosti %>],'<%=prefixReqGara%>')" >Assegna selezionato a tutti</a> --%>
				<a id="showHide<%= prefixReqGara %>Button" href="javascript:assegnaReqLottiSel('<%=prefixReqGara%>SelectLotto','<%=prefixReqGara%>')">Assegna selezionato a lotti selezionati</a>
<%-- 				<a id="showHide<%= prefixReqGara %>Button" href="javascript:showSezioneAggiungi([<%= PSReq.argsReqGara %>],[<%= PSReq.argsReqGaraNascosti %>],'<%=prefixReqGara%>')" >Assegna tutti i requisiti</a> --%>
			</div>	
			<div class="hmenu">
				<a id="showHide<%= prefixReqGara %>Button" href="javascript:eliminaReqLottiSel('<%=prefixReqGara%>SelectLotto','<%=prefixReqGara%>')" >Elimina selezionato da lotti selezionati</a>
				<a id="showHide<%= prefixReqGara %>Button" href="javascript:eliminaReqTuttiLotti('<%=prefixReqGara%>SelectLotto','<%=prefixReqGara%>')" >Elimina selezionato da tutti</a>
				<a id="showHide<%= prefixReqGara %>Button" href="javascript:eliminaTuttiReqTuttiLotti('<%=prefixReqGara%>SelectLotto','<%=prefixReqGara%>')" >Elimina tutte le associazioni</a>
			</div>
<% } %>				
			<c:if test="${fn:length(requestScope[listaLotti]) > maxPageElements}">
				<h3 id="navPaginazione">Pagina 1 di <c:out value="${maxPages+1}"/> </h3>
				<div class="hmenu">
				<ul>
					<li id="fisrt"><a id="disabledMenu" href="javascript:firstPage()" title="Visualizza prima pagina">Inizio elenco</a></li>
					<li id="prev"><a id="disabledMenu" href="javascript:prevPage()" title="Visualizza precedente">Precedenti</a></li>
					<li id="next"><a href="javascript:nextPage()" title="Visualizza successive">Successive</a></li>
					<li id="last"><a href="javascript:lastPage()" title="Visualizza ultima pagina">Fine elenco</a></li>
				</ul>
				</div>
			</c:if>
<% if( allowMod ){ %>			
			<small id="labelSelezionati">Selezionati 0 lotti</small>
<% } %>					
			<div id="DIVTabella<%= prefixReqGara %>Lotti"  class="scrollTabs"  style="height: 200px; width: 99%; border: 0px;">
				<table id="idTabella<%= prefixReqGara %>Lotti">
					<thead>
					<tr>
						<% if( allowMod ){ %>
						<th class="garaTh" width="130">
							Sel 
						<!-- Seleziona/deselezione tutti [DISATTIVATO] 
							[<a href="javascript:allSelect(true)">tutti</a>] 
							[<a href="javascript:allSelect(false)">nessuno</a>]
						-->
						<% } %>
						<th class="garaTh">CIG</th>
						<th class="garaTh">Importo lotto</th>
						<th class="garaTh">Oggetto</th>
					</tr>
					</thead>
					<tbody>
					<c:set var="lcounter" value="0" scope="page"/>
					
					<c:forEach items="${requestScope[listaLotti]}" var="lotto">
					
						<c:set var="idrow" value="${prefixReqGara}SelectLotto${lcounter}" scope="page"/>

						<fmt:parseNumber var="currentPage" value="${lcounter div maxPageElements}" type="number" pattern="#" integerOnly="true"/>

						<tr class="pagina<c:out value="${currentPage}"/>">
							<% if( allowMod ){ %>
							<td nowrap class="garaTd">
								<input type="checkbox" 
									tabindex="<c:out value="${currentPage}"/>"
									class="checkboxLotto"
									id="check<c:out value="${idrow}"/>" 
									value="<c:out value="${lotto.id_Lotto}"/>"/>
							</td>
							<% } %>
							<td nowrap class="garaTd"><c:out value="${lotto.CIG}${lotto.CIG_kkk}"/></td>
							<td nowrap class="garaTd"><c:out value="${lotto.importo_Lotto}"/></td>
							<td class="garaTd" width="30%"><c:out value="${lotto.oggetto}"/></td>
							<td id="col<c:out value="${idrow}"/>">
								<select id="idSelect<c:out value="${idrow}"/>" 
										tabindex="<c:out value="${currentPage}"/>"
										title="Seleziona requisiti"
										class="selectLotto"
										name="select<c:out value="${idrow}"/>" 
										multiple="multiple">
										
								<c:set var="rcounter" value="0" scope="page"/>
								
								<c:forEach items="${requestScope[listaRequisitoGara]}" var="requisito">
								
									<c:set var="selected" value="${u:contains(requisito.lotti_associati, lotto.id_Lotto) ? 'selected' : ''}"/>
									<c:set var="precaricato2" value="${requisito.tipoUso ne uso_fa}" />
									<c:set var="usoAR" value="${requisito.tipoUso eq uso_ar}" scope="page"/>
									
									<c:if test="${not precaricato2}">
										<option id="optL<c:out value="${lcounter}"/>R<c:out value="${rcounter}"/>" 
<%-- 											  title="<c:out value="${requisito.descrizione}"/>" --%>
										     value="<c:out value="${rcounter}"/>" <c:out value="${selected}"/> <%= !allowMod ? "disabled" : "" %>>
											<c:out value="${requisito.descrizione}"/>
										</option>
									</c:if>
									
									<c:if test="${precaricato2 and usoAR}">
										<option id="optL<c:out value="${lcounter}"/>R<c:out value="${rcounter}"/>" 
<%-- 											  title="<c:out value="${requisito.descrizione}"/>" --%>
										     value="<c:out value="${rcounter}"/>" <c:out value="${selected}"/> <%= !allowMod || (Boolean)pageContext.getAttribute("usoAR") ? "disabled" : "" %>>
											<c:out value="${requisito.descrizione}"/>
										</option>
									</c:if>									
									
									<c:set var="rcounter" value="${rcounter+1}" scope="page"/>
								
								</c:forEach>
								
								</select>
								
							</td>
							<td style="display: none">
								<input name="idLotto<c:out value="${idrow}"/>" 
										type="hidden" 
										id="idLotto<c:out value="${idrow}"/>" 
										value="<c:out value="${lotto.id_Lotto}"/>"/>
							</td>
						</tr>

						<c:set var="lcounter" value="${lcounter+1}" scope="page"/>
						
					</c:forEach>		
					
					<input type="hidden" name="<%= PSReq.NUM_LOTTI %>" value="<c:out value="${fn:length(requestScope[listaLotti])}"/>"/> 	
									
					</tbody>		
				</table> 	
				
				<c:if test="${fn:length(requestScope[listaLotti]) > maxPageElements}">
					<br/>
					<div class="hmenu">
					<ul>
						<li id="fisrt"><a id="disabledMenu" href="javascript:firstPage()" title="Visualizza prima pagina">Inizio elenco</a></li>
						<li id="prev"><a id="disabledMenu" href="javascript:prevPage()" title="Visualizza precedente">Precedenti</a></li>
						<li id="next"><a href="javascript:nextPage()" title="Visualizza successive">Successive</a></li>
						<li id="last"><a href="javascript:lastPage()" title="Visualizza ultima pagina">Fine elenco</a></li>
					</ul>
					</div>
				</c:if>
						
			</div>	 	
		
		</div>
	</div>	
</div>

<% if( allowMod ){ %>
<p>
	<input type="button" value="Conferma Requisiti" onclick="salva()"/>
	<input type="button" value="Elimina tutti i Requisiti" onclick="elimina()"/>
</p>
<% } %>

</form>

<script type="text/javascript">
//<!--
$(document).ready(function(){

	/** Paginazione **/
	
	$("#idTabella<%= prefixReqGara %>Lotti tbody tr").hide();
	$(".pagina0").show();
	
	$(".checkboxLotto").click(function() {
		var selezionati = $(".checkboxLotto:checked").length;
		setLabel("labelSelezionati","Selezionati " + selezionati + " lotti");
	});
	
	/** Componente multiselect **/

	$("select[tabindex='0']").css('width', '200px');
	$("select[tabindex='0']").multiselect({header: <c:out value="${showMultiselectHeader}"/> });
	
	$('.selectLotto').bind("multiselectclick", function(event, ui){
		// Propaga la selezione dal componente "jquery multiselect" alla "select html" sottostante
		$("#"+ this.id + " option[value='" + ui.value + "']").attr("selected",ui.checked);
		aggiornaCampoStato('<%= prefixReqGara %>', ui.value);
	});
	$('.selectLotto').bind("multiselectcheckall", function(event, ui){
		$(".requisitiRadio").each(function(index){
			aggiornaCampoStato('<%= prefixReqGara %>', this.value);
		});
	});
	$('.selectLotto').bind("multiselectuncheckall", function(event, ui){
		$(".requisitiRadio").each(function(index){
			aggiornaCampoStato('<%= prefixReqGara %>', this.value);
		});
	});
	
	//$("#<%= PSReq.FIELD_NAME_REQ_REQUISITO %>").multiselect({minWidth: "1110", header: false, multiple: false, selectedList: 1});
});
//-->
</script>


<input type="hidden" id="selected<%= prefixReqGara %>" value="0" />
<input type="hidden" id="requisitoSelezionato" value="-1" />

<% } catch (Exception e){e.printStackTrace();} %>
