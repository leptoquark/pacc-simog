<%@page import="it.avlp.simog.common.servlet.ParametriCup"%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 
	Nella pagina che include elencoCPVSecondarie.jsp deve esserci la definizione delle variabili: 
	* elencoCPVSecondarie : elenco degli oggetti di tipo CupLottoAgg
	* readonlyCpv : condizioni per la disabilitazione del componente e visualizzazione in sola lettura
	* idLotto : id del lotto a cui si fa riferimento
 -->
<jsp:useBean id="elencoCPVSecondarie" type="java.util.LinkedList" class="java.util.LinkedList" scope="request"></jsp:useBean>

<!-- le variabili sono impostate dal chiamante -->
<%-- <c:set var="readonlyCpv" value="${(readonly eq true)}" />	 --%>
<%-- <c:set var="readonlyCpvStr" value="${readonlyCpv eq true? 'readonly' : ''} " /> --%>
		
<% String prefixCpv = "CPV"; %>


<script type="text/javascript">
		
	
	function callCpvDesc(args,argshidden,prefix) {
		var cpvVal = $("#"+prefix).val();
		     if(validateCpv(cpvVal)) {
					 $.get("SrvLoadCpvSecDesc", {
						 idCPV : cpvVal
			         }, function(responseText) {        
			               if(responseText=="KO")
				               alert("La CPV indicata non e' valida")
				            else
					            addRowCpv(args,argshidden,prefix,responseText,cpvVal);
			                   
			        });
				}

	   }
	 
	 
   function validateCpv(cpvVal){
	   
		if(cpvVal==null || cpvVal=='') {
			alert("Inserire il codice CPV");
			return false;
		} else {
           var foundcpv = false;
           var values = $("input[name*='CodCPV']").map(function(){return $(this).val();}).get();

           for(var i=0;i<values.length;i++) {
	               var cpvins = values[i];
	               if(cpvins==cpvVal)
	            	   foundcpv=true;
               }
			
			if(foundcpv){
				alert("La CPV con codice "+cpvVal+" e' stata gia' inserita come CPV secondaria");
				return false;
	          } else {
                     var cpvPrimaria = $("input[name='cpv']").val();
                     if(cpvPrimaria==cpvVal) {
                    	 alert("La CPV con codice "+cpvVal+" e' stata gia' inserita come CPV prevalente");
                    	 return false;
                         }
		          }
		}

		return true;
   }
	
	function addRowCpv(args,argshidden,prefix,desc,cpvVal) {

           var countMax = $("#maxCpvIndex").val();
           var countInt = parseInt(countMax);
           $("#tbodyCpvTab").append(createTableCPVRow(countInt+1,cpvVal,desc));
           $("#maxCpvIndex").val(countInt+1);
           countMax = $("#ModificatoCPV").val();
           countInt = parseInt(countMax);
           $("#ModificatoCPV").val(countInt+1);
           $('#divAggCPV').css('display', 'none');
           $("#"+prefix).val("");
           $("#showHideCPVButton").html("Aggiungi CPV");
			
	}

function deleteRowCpv(idx){
	if(confirm("confermare l'operazione?")) {
		$("#"+idx).remove();
		 var count = $("#ModificatoCPV").val();
	     var countInt = parseInt(count);
	     $("#ModificatoCPV").val(countInt-1);
	}
}
	
    function createTableCPVRow(index,cpv,desc){
        return '<tr id="rowCPV'+index+'">'+
                       '<td nowrap="nowrap" class="hmenu"><a title="Cancella CPV" href="javascript:deleteRowCpv(\'rowCPV'+index+'\')">Cancella</></td>'+
                       '<td nowrap class="garaTd" id="CPV'+index+'CodCPV">'+(cpv)+'</td>'+
                       '<td nowrap class="garaTd" id="CPV'+index+'DescrizioneCPV">'+(desc)+'</td>'+
                       '<td><input type="hidden" name="rowCPV'+index+'CodCPV" value="'+(cpv)+'" /><input type="hidden" name="rowCPV'+index+'DescrizioneCPV" value="'+(desc)+'" /></td>'
                '</tr>';
        }

	function showSezioneAggiungiCpv(){
		   var cssProp = $('#divAggCPV').css('display');
		   if(cssProp=="none"){
			    cssProp = $('#divAggCPV').css('display','block');
			    $("#showHideCPVButton").html("Annulla");
		   } else {
			   cssProp = $('#divAggCPV').css('display','none');
			   $("#showHideCPVButton").html("Aggiungi CPV");
		   }
		}


	

</script>

<c:set var="prefixCpv" value="<%= prefixCpv %>" scope="page" />
	<div id="DIVTabella<%= prefixCpv %>" class="scrollTabs" style="height: 150px; width: 99%; display: block;"><!-- class="scrollTabs"  -->
		<table id="idTabella<%= prefixCpv %>" width="100%" >
			<tbody id="tbodyCpvTab">
			<tr> 
			<c:if test="${readonlyCpv ne true }">
				<th class="garaTh" width="125">Azione</th>
			</c:if>
			<th class="garaTh">CPV</th>
			<th class="garaTh">Descrizione</th>
	
			</tr>
			<c:set var="counter" value='0' scope="page"/>
			<c:set var="cupTot" value='0' scope="page"/>
			<c:forEach var="cpvCorrente" items="${elencoCPVSecondarie}">
				<c:set var="id" value="row${prefixCpv}${counter}" scope="page"/>
				<tr id="<c:out value="${id}" />">
					
					<c:if test="${readonlyCpv ne true }">
						<td nowrap="nowrap" class="hmenu">
<%-- 							<a title="Modifica <%= prefixCpv %>" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= ParametriCup.argsCup %>],[<%=ParametriCup.argsCupNascosti%>],'<%=prefixCpv%>')">Modifica</> --%>
							<a title="Cancella <%= prefixCpv %>" href="javascript:deleteRowCpv('<c:out value="${id}" />')">Cancella</>
						</td>
					</c:if>
					
					
					<td nowrap class="garaTd" id="<c:out value="${id}" />CodCPV"><c:out value="${cpvCorrente.idCpv}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" />DescrizioneCPV"><c:out value="${cpvCorrente.descrizione}" /></td>
					<td><input type="hidden" name="<c:out value="${id}" />CodCPV" value="${cpvCorrente.idCpv}" />
					    <input type="hidden" name="<c:out value="${id}" />DescrizioneCPV" value="${cpvCorrente.descrizione}" />
				   </td>
			
				</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
			</c:forEach> 
			</tbody>		
			</table> 			
		</div>	 	
		
		<c:if test="${readonlyCpv ne true }">
			<div class="hmenu">
				<a id="showHide<%= prefixCpv %>Button" href="javascript:showSezioneAggiungiCpv()" title="Aggiungi CPV">Aggiungi CPV</a>
			</div>
		</c:if>
		
		<div class="detailHelp" id="divAgg<%= prefixCpv %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">					
				<tr>
				<td>
					<input type="text" id="<%=prefixCpv%>Field" value="" size="30" maxlength="10"/><a class="getCPV" href="#"  onclick="apripopup('ricercaCPV.jsp?nameField=<%=prefixCpv%>Field', '<%=prefixCpv%>')" title="Lista CPV Correnti"><img src="img/icon_info_sml.gif"></a>
				</td>						
				</tr>
				<tr><td class="hmenu">
						<a id="AddMod<%= prefixCpv %>" href="javascript:callCpvDesc(['CPV'],['CPV','DESCRIZIONE_CPV'],'<%=prefixCpv%>Field')">Aggiungi</a>
					</td>
				</tr>							
			</table>
			<input type="hidden" id="Modificato<%= prefixCpv %>" name ="Nr<%= prefixCpv %>" value="${ counter }"/>
			<input type="hidden" id="maxCpvIndex" name ="maxCpvIndex" value="${ counter -1 }"/>
		</div>
	