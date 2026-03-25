<%@page import="it.avlp.simog.common.servlet.ParametriCup"%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 
	Nella pagina che include elencoCUP.jsp deve esserci la definizione delle variabili: 
	* elencoCup : elenco degli oggetti di tipo CupLottoAgg
	* readonlyCup : condizioni per la disabilitazione del componente e visualizzazione in sola lettura
	* idLotto : id del lotto a cui si fa riferimento
 -->
<jsp:useBean id="elencoCup" type="java.util.LinkedList" class="java.util.LinkedList" scope="request"></jsp:useBean>

<!-- le variabili sono impostate dal chiamante -->
<%-- <c:set var="readonlyCup" value="${(readonly eq true)}" />	 --%>
<%-- <c:set var="readonlyCupStr" value="${readonlyCup eq true? 'readonly' : ''} " /> --%>
		
<% String prefixCup = ParametriCup.ELENCO_CUP; %>

<%--@ include file="/script/domUtilsNew.js" --%>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<script type="text/javascript">
//<!--		
    function confirmCup(prfx_valido, prfx_okutente)
    {
        $("input[id^='hiddenrowCUP'][id$='" + prfx_valido + "']").each(function(idx, item){
            if( item.value == "S" ){
                $("input[id='hiddenrowCUP" + idx + prfx_okutente + "']").attr("value","S");
                $("td[id='rowCUP" + idx + prfx_okutente + "']").html("Si");
                $("td[id='rowCUP" + idx + prfx_okutente + "']").css("background-color","#BBFFBB");
            }
        });       
        alert("I codici CUP validati sono stati confermati, la conferma sara' memorizzata a fronte del salvataggio della scheda");
    } 	
	
	function addRowCup(args,argshidden,prefix) {
		//Ticket ALM #2159
		if(document.getElementById(prefix).value==null || document.getElementById(prefix).value=='')
			alert("Inserire il codice CUP");
		else //Fine Ticket ALM #2159
			if(checkBeforeAdd(prefix))  {
				if( validateRadio(prefix) )  
				{
					var idnext=getRowIndex(prefix); 
					var idtable="idTabella"+prefix;
					var table=document.getElementById(idtable); 
					var newid="row"+prefix+(parseInt(idnext)+1);
					var tbody=table.getElementsByTagName("TBODY")[0];
					var row=createRow(newid,args,argshidden,prefix); 
					tbody.appendChild(row);
					appendHiddenTyped(row,newid,prefix,argshidden,true);
					var idDIVTabella="DIVTabella"+prefix;
					var numOfRows=idnext+1;
					hideSezioneAggiungi(args,argshidden,prefix);
					setModificato(prefix); 
					document.getElementById("selected"+prefix).value=0;
					afterAddRow(newid,args,argshidden,prefix,row);
				}  
			} 
	}
	
	function appendHiddenTyped(row,idrow,prefix,parametri,creazione) {
		for(var j = 0;j<parametri.length;j++)  {
			var idElement = idrow+parametri[j];
			var field = document.getElementById(parametri[j]);
			if( field != null ){
				var changevalue = field.value;
				if(creazione)  { 
					var td = document.createElement("TD"); 
					td.style.display = "none";
					var hidden = document.createElement("INPUT");
					hidden.setAttribute("name",idElement,0);
			    	hidden.setAttribute("id","hidden"+idElement,0);
					hidden.setAttribute("type","hidden",0);
					hidden.value = changevalue; 
					td.appendChild(hidden);
					row.appendChild(td); 
				}  else  {  
					var hidden = document.getElementById("hidden"+idElement); 
					hidden.value = changevalue; 
				}
			}  
		} 
	}
	

	function afterAddRow(newid,args,argshidden,prefix,row){
		//imposta il parametro numeri di righe
		var counter = $("input[name='<%= ParametriCup.NR_RIGHE_CUP %>']").val();
		counter++;
		$("input[name='<%= ParametriCup.NR_RIGHE_CUP %>']").val(counter);
		//setta le colonne con i valori di default
		var  orbj = $("a[href*='setForModifyRow'][href*='rowCUP']")[0];
		var par = orbj.parentNode;
		par = par.parentNode;
		orbj.parentNode.removeChild(orbj);
		var ftd1 = document.createElement("td");
		ftd1.innerHTML = "No";
		ftd1.className="garaTd";
		par.appendChild(ftd1);		
		var ftd2 = document.createElement("td");
		ftd2.innerHTML = "&nbsp;";
		ftd2.className="garaTd";
		par.appendChild(ftd2);		
		var ftd3 = document.createElement("td");
		ftd3.innerHTML = "&nbsp;";
		ftd3.className="garaTd";
		par.appendChild(ftd3);		
		var ftd4 = document.createElement("td");
		ftd4.innerHTML = "&nbsp;";
		ftd4.className="garaTd";
		par.appendChild(ftd4);			
		//impostiamo l'addRow personalizzato (addRowCup)
		var addRowElem = $("#AddMod"+prefix);
		//var href = addRowElem.attr("href").replace("addRow", "addRowCup"); //ALM #2322
		addRowElem.attr("href",addRowElem.attr("href"));
	}

	
//-->
</script>

<c:set var="prefixCup" value="<%= prefixCup %>" scope="page" />
	<div id="DIVTabella<%= prefixCup %>" class="scrollTabs" style="height: 150px; width: 99%; display: block;"><!-- class="scrollTabs"  -->
		<table id="idTabella<%= prefixCup %>" width="100%" >
			<tbody>
			<tr> 
			<c:if test="${readonlyCup ne true }">
				<th class="garaTh" width="125">Azione</th>
			</c:if>
			<th class="garaTh">CUP</th>
			<th class="garaTh">Confermato</th>
			<th class="garaTh">Valido</th>
			<th class="garaTh">Dati DIPE</th>
			<th class="garaTh">Tematica PNRR</th>
			</tr>
			<c:set var="counter" value='0' scope="page"/>
			<c:set var="cupTot" value='0' scope="page"/>
			<c:set var="notConfirmed" value="${false}" scope="page"/>
			<c:forEach var="cupCorrente" items="${elencoCup}">
				<c:set var="id" value="row${prefixCup}${counter}" scope="page"/>
				<tr id="<c:out value="${id}" />">
					
					<c:if test="${readonlyCup ne true }">	
						<td nowrap="nowrap" class="hmenu">
<%-- 							<a title="Modifica <%= prefixCup %>" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= ParametriCup.argsCup %>],[<%=ParametriCup.argsCupNascosti%>],'<%=prefixCup%>')">Modifica</> --%>
							<a title="Cancella <%= prefixCup %>" href="javascript:deleteRow('<c:out value="${id}" />',[<%= ParametriCup.argsCup %>],[<%=ParametriCup.argsCupNascosti%>],'<%=prefixCup%>')">Cancella</>
						</td>
					</c:if>
					
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_CUP %>"><c:out value="${cupCorrente.cup}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_OK_UTENTE %>"><c:out value="${cupCorrente.okUtente == 'S' ? 'Si' : (cupCorrente.okUtente == 'N' ? 'No' : '')}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_VALIDO %>"><c:out value="${cupCorrente.datiDIPE.VALIDO == 'S' ? 'Si' : (cupCorrente.datiDIPE.VALIDO == 'N' ? 'No' : '')}" /></td>
					<td class="garaTd" id="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_DATIDIPE %>"><c:out value="${cupCorrente.datiDIPE.ESITO_RICHIESTA}" /></td>
					<td class="garaTd" id="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_TEMATICA %>"><c:out value="${cupCorrente.datiDIPE.tematica}" /></td>

					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_CUP %>" name="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_CUP %>" value="<c:out value="${cupCorrente.cup}" />"></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_ID_LOTTO %>" name="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_ID_LOTTO %>" value="<c:out value="${cupCorrente.idLotto}" />"></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_ID_AGG %>" name="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_ID_AGG %>" value="<c:out value="${cupCorrente.idAggiudicazione}" />"></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_DATA_INIZIO_AGG %>" name="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_DATA_INIZIO_AGG %>" value="<c:out value="${cupCorrente.dataInizioAgg}" />"></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_OK_UTENTE %>" name="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_OK_UTENTE %>" value="<c:out value="${cupCorrente.okUtente}" />"></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_VALIDO %>" name="<c:out value="${id}" /><%= ParametriCup.FIELD_NAME_VALIDO %>" value="<c:out value="${cupCorrente.datiDIPE.VALIDO}" />"></td>				
				</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
				<c:set var="notConfirmed" value="${notConfirmed || (cupCorrente.datiDIPE.VALIDO eq 'S' && cupCorrente.okUtente ne 'S')}" scope="page"/>
			</c:forEach> 
			</tbody>		
			</table> 			
		</div>	 	
		
		<c:if test="${readonlyCup ne true }">
			<div class="hmenu">
				<a id="showHide<%= prefixCup %>Button" href="javascript:showSezioneAggiungi([<%= ParametriCup.argsCup %>],[<%= ParametriCup.argsCupNascosti %>],'<%=prefixCup%>')" title="Aggiungi CUP">Aggiungi CUP</a>
				<c:if test="${notConfirmed eq true }">
					<a id="ConfirmCup<%= prefixCup %>" href="javascript:confirmCup('<%= ParametriCup.FIELD_NAME_VALIDO %>', '<%= ParametriCup.FIELD_NAME_OK_UTENTE %>')">Conferma CUP</a>
				</c:if>
				<c:if test="${notConfirmed eq false }">
					<a id="disabledMenu">Conferma CUP</a>
				</c:if>
			</div>
		</c:if>
		
		<div class="detailHelp" id="divAgg<%= prefixCup %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">					
				<tr>
				<td>
					<input type="text" id="<%= ParametriCup.FIELD_NAME_CUP %>" value="" size="30" maxlength="15"/>
				</td>						
				</tr>
				<tr><td class="hmenu">
						<a id="AddMod<%= prefixCup %>" href="javascript:addRowCup([<%= ParametriCup.argsCup %>],[<%= ParametriCup.argsCupNascosti %>],'<%=prefixCup%>')">Aggiungi</a>
					</td>
				</tr>							
			</table>
			<input type="hidden" id="Modificato<%= prefixCup %>" name ="Modificato<%= prefixCup %>" value="0">
		</div>
		
<input type="hidden" id="Modificato7" name ="Modificato7" value="<c:out value="${param['modificato7']}" />">
<input type="hidden" id="selected<%= prefixCup %>" value="0" /> 
<input type="hidden" name="<%= ParametriCup.NR_RIGHE_CUP %>" value="${counter}" /> 