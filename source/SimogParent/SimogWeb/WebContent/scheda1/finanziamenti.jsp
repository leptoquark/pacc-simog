<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="readonlyFin" value="${(readonly eq true)}" />	
<c:set var="readonlyFinStr" value="${readonlyFin eq true? 'readonly' : ''} " />
		
<% String prefixFin = PSBD.FINANZIAMENTO; %>
<script type="text/javascript">
//<!--
function modifyRowFin(i,a,o,c){modifyRow(i,a,o,c),calcolaFin(),gt("AddMod"+c).href="javascript:calcolaTotFin(["+ricreaStringa(a)+"],["+ricreaStringa(o)+"],'"+c+"')"}
function setForModifyRowFin(i,o,r,t){setForModifyRow(i,o,r,t),gt("AddMod"+t).href="javascript:modifyRowFin('"+i+"',["+ricreaStringa(o)+"],["+ricreaStringa(r)+"],'"+t+"')"}
function deleteRowFin(a,e,i,c){deleteRow(a,e,i,c),calcolaFin(),gt("AddMod"+c).href="javascript:calcolaTotFin(["+ricreaStringa(e)+"],["+ricreaStringa(i)+"],'"+c+"')"}
function calcolaTotFin(r,a,t){addRow(r,a,t),calcolaFin(),gt("AddMod"+t).href="javascript:calcolaTotFin(["+ricreaStringa(r)+"],["+ricreaStringa(a)+"],'"+t+"')";var n=gt("idTabellaFinanziamento").rows;for(i=1;i<n.length;i++){var c=n[i].id;n[i].firstChild.children[0].href="javascript:setForModifyRowFin('"+c+"',["+ricreaStringa(r)+"],["+ricreaStringa(a)+"],'"+t+"')",n[i].firstChild.children[1].href="javascript:deleteRowFin('"+c+"',["+ricreaStringa(r)+"],["+ricreaStringa(a)+"],'"+t+"')"}}
function calcolaFin(){var e=0;for(righe=gt("idTabellaFinanziamento").rows,td=gt("idTabellaFinanziamento"),i=1;i<righe.length;i++){var a=righe[i].cells[2].innerText||righe[i].cells[2].textContent;""!=a&&(isNaN(parseFloat(a))||(a=(a=a.replace(/\./g,"").replace(".","")).replace(",","."),e+=parseFloat(a)))}gt("finTotale").value=e,gt("finTotale").value=gt("finTotale").value.replace(".",","),intValidateAmount(gt("finTotale"),3)}
//-->
</script>

<c:set var="prefixFin" value="<%= prefixFin %>" scope="page" />
			
	<div id="DIVTabella<%= prefixFin %>"  class="scrollTabs" style="height: 200px; width: 99%;"><!-- class="scrollTabs"  -->
		<table id="idTabella<%= prefixFin %>">
			<tbody>
			<tr>
			<th width="125"><utils:message key="table.azione" /></th>
			<th class="garaTh"><utils:message key="scheda.tipoFinanziamento" /></th>
			<th class="garaTh"><utils:message key="scheda.importoFinanziamento" /></th>
			</tr>
			<c:set var="counter" value='0' scope="page"/>
			<c:set var="finTot" value='0' scope="page"/>
			<c:forEach var="finCorrente" items="${finanziamenti}">
				<c:set var="id" value="row${prefixFin}${counter}" scope="page"/>
				<tr id="<c:out value="${id}" />">
					
					<c:if test="${readonlyFin ne true }">	
						<td nowrap="nowrap" class="hmenu">
							<a title="<utils:message key="button.modifica" />" href="javascript:setForModifyRowFin('<c:out value="${id}" />',[<%= PSBD.argsFin %>],[<%=PSBD.argsFinNascosti%>],'<%=prefixFin%>')"><utils:message key="button.modifica" /></a>
							<a title="<utils:message key="button.cancella" />" href="javascript:deleteRowFin('<c:out value="${id}" />',[<%= PSBD.argsFin %>],[<%=PSBD.argsFinNascosti%>],'<%=prefixFin%>')"><utils:message key="button.cancella" /></a></td>
					</c:if>
					<c:if test="${readonlyFin eq true }">	
						<td>&nbsp;</td>
					</c:if>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>"><c:out value="${finCorrente.descrizione}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO %>"><c:out value="${finCorrente.importoStr}" /></td>
					<td nowrap style="display: none" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>"><c:out value="${finCorrente.idFinanziamento}" /></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>" value="<c:out value="${finCorrente.descrizione}" />"></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" value="<c:out value="${finCorrente.idFinanziamento}" />"></td>
					<td nowrap style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO %>" value="<c:out value="${finCorrente.importo}" />"></td>
					</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
				<c:set var="finTot" value="${finTot + finCorrente.importo}" scope="page"/>
			</c:forEach> 
			</tbody>		
			</table> 			
		</div>	 	
		<div >
			<label style="color:black;"><utils:message key="scheda.importoTotaleFinanziamento" /></label>
			<input disabled type="text" id="finTotale" style="color:black;border:0;text-align:right;font-weight: bold;width:100px;"	
			<%	String finanziamentoTotale="";
			if(pageContext.getAttribute("finTot") != null && pageContext.getAttribute("finTot") instanceof BigDecimal){
		   	   finanziamentoTotale=PageHelper.formattaImporto((BigDecimal)pageContext.getAttribute("finTot"));} %>
			value="<c:out value="<%= finanziamentoTotale %>"/>"/>		
			<br><br>		
		</div>
		<c:if test="${readonlyFin ne true }">	
			<div class="hmenu"><a id="showHide<%= prefixFin %>Button" href="javascript:showSezioneAggiungi([<%= PSBD.argsFin %>],[<%= PSBD.argsFinNascosti %>],'<%=prefixFin%>')" title="<utils:message key="scheda.aggiungiFinanziamento" />"><utils:message key="scheda.aggiungiFinanziamento" /></a></div>
		</c:if>
		<div class="detailHelp" id="divAgg<%= prefixFin %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">				
				<tr>
				<th><label for="<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>"><utils:message key="scheda.tipoFinanziamento" /></label></th>
				<td colspan="2">
				<select id="<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO%>" CLASS="BOTTONE" onchange="setFormModified('Modificato<%=prefixFin%>')">
					<option></option>
					<u:options name="<%= ParametriServlet.TIPO_FINANZIAMENTO_BEAN %>" scope="request" />
				</select>
				</td>					
				</tr>		
				<tr style="display: none;">
				<td>
					<input disabled type="text" id="<%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" value=""/>
				</td>							
				</tr>
				<tr>
				<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO %>"><utils:message key="scheda.importoFinanziamento" /></label></th>
				<td>
					<input type="text" id="<%= PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO %>" value="" onchange="setFormModified('Modificato<%=prefixFin%>')" onblur="validateAmount(this)" />
				</td>
				</tr>	
				<tr><td class="hmenu"><a id="AddMod<%= prefixFin %>" href="javascript:calcolaTotFin([<%= PSBD.argsFin %>],[<%= PSBD.argsFinNascosti %>],'<%=prefixFin%>')"><utils:message key="button.aggiungi" /></a></td></tr>							
			</table>
		<input type="hidden" id="Modificato<%= prefixFin %>" name ="Modificato<%= prefixFin %>" value="0">
		</div>
<input type="hidden" id="Modificato7" name ="Modificato7" value="<c:out value="${param['modificato7']}" />">
<input type="hidden" id="selected<%= prefixFin %>" value="0" /> 