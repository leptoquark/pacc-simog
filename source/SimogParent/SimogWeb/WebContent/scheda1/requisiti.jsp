<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<% String prefixReq = PSBD.REQUISITO; %>
<script type="text/javascript">
function checkFlags(oForm,campoId){
campo = getElementById(campoId);
if(campo.id == "<%=PSBD.S_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA%>" && campo.checked){
document.forms[oForm.name].<%= PSBD.S_FIELD_NAME_SISTEMA_QUALIFICAZIONE %>.checked=false;
document.forms[oForm.name].<%= PSBD.N_FIELD_NAME_SISTEMA_QUALIFICAZIONE %>.checked=true;}
else if(campo.id == "<%= PSBD.S_FIELD_NAME_SISTEMA_QUALIFICAZIONE %>" && campo.checked){
document.forms[oForm.name].<%=PSBD.S_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA%>.checked=false;
document.forms[oForm.name].<%=PSBD.N_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA%>.checked=true;}
}
</script>
<c:set var="prefixReq" value="<%= prefixReq %>" scope="page" />
<c:if test="${flagEnteSpeciale == 'O'}">
 	<c:set var="disabledReq" value="${'disabled'}" />
</c:if>
<c:if test="${flagEnteSpeciale == 'S'}">
 	<c:set var="disabledReq" value="${disabled}" />
</c:if>
<table>		
	<colgroup>
		<col width="20%"/>
		<col width="40%"/>
		<col width="40%"/>
	</colgroup>	
	<tr>
		<th rowspan="2"><utils:message key="scheda.requisitiSettoriSpeciali" /></th>		
		<th ><label for="<%= PSBD.FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA %>"><utils:message key="scheda.criteriSelezioneStabilitiSA" /></label></th>
		<td >
			<u:selectBooleanRadio name="<%= PSBD.FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA %>"  onclick="javascript:checkFlags(this.form,this.id);"
		   			  value="${aggiudicazione.criteriSelezioneStabilitiSA}" trueId="<%= PSBD.S_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA %>" 
		  			  falseId="<%= PSBD.N_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA %>" readonly="${readonly or flagEnteSpeciale == 'O'}" 
		  			  tabindex="<%=++iT%>" onchange="setFormModified('Modificato0')" />
		   			 <%iT++; %>
			
		</td>
	</tr>			
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_SISTEMA_QUALIFICAZIONE %>"><utils:message key="scheda.sistemaQualificazioneInterno" /></label></th>
		<td>
			<u:selectBooleanRadio name="<%= PSBD.FIELD_NAME_SISTEMA_QUALIFICAZIONE %>"  onclick="javascript:checkFlags(this.form,this.id);"
		   			  value="${aggiudicazione.sistemaQualificazione}" trueId="<%= PSBD.S_FIELD_NAME_SISTEMA_QUALIFICAZIONE %>" 
		  			  falseId="<%= PSBD.N_FIELD_NAME_SISTEMA_QUALIFICAZIONE %>"  readonly="${readonly or flagEnteSpeciale == 'O'}"  
		  			  tabindex="<%=++iT%>" onchange="setFormModified('Modificato0')" />
		   			 <%iT++; %>
			
		</td>
	</tr>
</table>
<label style="color:black;">
<c:choose>
	<c:when test="${datiGara.tipoContratto == 'S' || datiGara.tipoContratto == 'F' }">Eventuale componente lavori (Settori ordinari o settori speciali che utilizzano il medesimo sistema di qualificazione)</c:when>
	<c:when test="${datiGara.tipoContratto == 'L' }">Componente lavori (Settori ordinari o settori speciali che utilizzano il medesimo sistema di qualificazione)</c:when>
</c:choose>
</label><br>
<hr>        
<!--<div class="detailHelp" style="border: 1px solid #cfcfcf; width: 99%;">	-->
	    <!-- MEV 34181 - 3.04.8.1 fase 2 -->
	<%-- <div id="DIVTabella<%= prefixReq %>" class="scrollTabs" style="height: 200px; width: 99%;">
		<table id="idTabella<%= prefixReq %>">
		<tbody>
			<tr>
				<th width="125">Azione</th>
				<th class="garaTh">Categoria</th>
				<th class="garaTh">Descrizione</th>
				<th class="garaTh">Classe d'Importo</th>
				<th class="garaTh">Prevalente</th>						
				<th class="garaTh">Scorporabile</th>
				<th class="garaTh">SubAppaltabile</th>
			</tr>
			<c:set var="counter" value='0' scope="page"/>
			<c:forEach var="requisitoCorrente" items="${requisiti}">
				<c:set var="id" value="row${prefixReq}${counter}" scope="page"/>
			<tr id="<c:out value="${id}" />">
				<c:if test="${readonly ne true}">
					<td nowrap class="hmenu">
						<a title="<utils:message key="button.modifica" />" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSBD.argsReq %>],[<%=PSBD.argsReqNascosti%>],'<%=prefixReq%>')" title="<utils:message key="button.modifica" />"><utils:message key="button.modifica" /></a>
						&nbsp;<a title="<utils:message key="button.cancella" />" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSBD.argsReq %>],[<%=PSBD.argsReqNascosti%>],'<%=prefixReq%>')"  title="<utils:message key="button.cancella" />"><utils:message key="button.cancella" /></a></td>					
				</c:if>
				<c:if test="${readonly eq true}"><td>&nbsp;</td></c:if>
				<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_CATEGORIA %>"><c:out value="${requisitoCorrente.idCategoria}" /></td>
				<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>"><c:out value="${requisitoCorrente.descCategoria}" /></td>																
				<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>"><c:out value="${requisitoCorrente.importoDa}" /></td>
				<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PREVALENTE %>"><c:out value="${requisitoCorrente.prevalente == 'S' ? 'SI' : (requisitoCorrente.prevalente == 'N' ? 'NO' : ' ')}" /></td>
				<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_SCORPORABILE %>"><c:out value="${requisitoCorrente.scorporabile == 'S' ? 'SI' : (requisitoCorrente.scorporabile == 'N' ? 'NO' : ' ')}" /></td>
				<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_SUBAPPALTABILE %>"><c:out value="${requisitoCorrente.subAppaltabile == 'S' ? 'SI' : (requisitoCorrente.subAppaltabile == 'N' ? 'NO' : ' ')}" /></td>
					
				<td style="display: none;" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>"><c:out value="${requisitoCorrente.classeImporto}" /></td>
				<td style="display: none;" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>"><c:out value="${requisitoCorrente.importoDa}" /></td>	
				<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_CATEGORIA %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_CATEGORIA %>" value="<c:out value="${requisitoCorrente.idCategoria}" />"></td>
				<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" value="<c:out value="${requisitoCorrente.descCategoria}" />"></td>										
				<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PREVALENTE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PREVALENTE %>" value="<c:out value="${requisitoCorrente.prevalente}" />"></td>
				<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_SCORPORABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_SCORPORABILE %>" value="<c:out value="${requisitoCorrente.scorporabile}" />"></td>
				<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_SUBAPPALTABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_SUBAPPALTABILE %>" value="<c:out value="${requisitoCorrente.subAppaltabile}" />"></td>
				<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>" value="<c:out value="${requisitoCorrente.classeImporto}" />"></td>
				<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>" value="<c:out value="${requisitoCorrente.importoDa}" />"></td>				
			</tr>
			<c:set var="counter" value="${counter + 1}" scope="page"/>
			</c:forEach>							
		</tbody>
		</table>
	</div> 
	<!-- FINE 3.04.8.1 MAC 34181 -->
	--%>
	<c:if test="${readonly ne true}">
		<div class="hmenu"><a id="showHide<%= prefixReq %>Button" title="<utils:message key="scheda.aggiungiRequisito" />" href="javascript:showSezioneAggiungi([<%= PSBD.argsReq %>],[<%=PSBD.argsReqNascosti%>],'<%=prefixReq%>')" ><utils:message key="scheda.aggiungiRequisito" /></a></div>
	</c:if>
	<!-- 3.04.8.1 MAC 34181 -->
	<%-- <div class="detailHelp" id="divAgg<%= prefixReq %>" style="display: none; border: 1px solid #cfcfcf;"> 
		<table width="100%">
			<tr>
				<th><label >Categoria</label></th>
				<td colspan="2">
					<select onchange="setFormModified('Modificato<%=prefixReq%>')"  id="<%= PSBD.FIELD_NAME_ID_CATEGORIA%>" CLASS="BOTTONE">
						<option value=" "></option>
						<u:options name="<%= ParametriServlet.CATEGORIA_BEAN %>" scope="request"/>
					</select>
				</td>					
			</tr>	
			<tr>
				<th><label><utils:message key="scheda.classeImporto" /></label></th>
				<td colspan="2">
					<select onchange="setFormModified('Modificato<%=prefixReq%>')"  id="<%= PSBD.FIELD_NAME_CLASSE_IMPORTO%>" CLASS="BOTTONE">
						<option value=" "></option>
						<u:options name="<%= ParametriServlet.CLASSI_IMPORTO_BEAN %>" scope="request"/>
					</select>
				</td>					
			</tr>	
			<tr style="display: none;">
				<td>
					<input disabled type="text" id="<%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO%>" value=""/>
				</td>							
			</tr>	
			<tr>
			<th><label>La categoria � Prevalente</label></th>
				<td>
				    <select onchange="setFormModified('Modificato<%=prefixReq%>')" id="<%= PSBD.FIELD_NAME_PREVALENTE %>" Class="BOTTONE">
						<option value=""></option>
						<option value="S">SI</option>
						<option value="N">NO</option>
				    </select>
				</td>
			</tr>
			<tr>
				<th><label>La categoria � Scorporabile</label></th>
				<td>
				    <select onchange="setFormModified('Modificato<%=prefixReq%>')" id="<%= PSBD.FIELD_NAME_SCORPORABILE %>" Class="BOTTONE">
						<option value=""></option>
						<option value="S">SI</option>
						<option value="N">NO</option>
				    </select>
				</td>
			</tr>
			<tr>
				<th><label>La categoria � Sub-Appaltabile</label></th>
				<td>
					<select onchange="setFormModified('Modificato<%=prefixReq%>')" id="<%= PSBD.FIELD_NAME_SUBAPPALTABILE %>" Class="BOTTONE"> 
						<option value=""></option>
						<option value="S">SI</option>
						<option value="N">NO</option>
				    </select>
				</td>
			</tr>												
			<tr><td class="hmenu"><a id="AddMod<%= prefixReq %>" href="javascript:addRow([<%= PSBD.argsReq %>],[<%=PSBD.argsReqNascosti%>],'<%=prefixReq%>')"><utils:message key="button.aggiungi" /></a></td></tr>
		</table>			
		<input type="hidden" id="Modificato<%= prefixReq %>" name ="Modificato<%= prefixReq %>" value="0">
	</div> 
	<!-- FINE 3.04.8.1 MAC 34181 -->
	--%>
<!--/div-->			
<input type="hidden" id="Modificato3" name ="Modificato3" value="<c:out value="${param['modificato3']}" />">
<input type="hidden" id="selected<%= prefixReq %>" value="0" />