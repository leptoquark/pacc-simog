<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="../include/basicHeader.inc" %>

<%@ include file="../include/controlloSessione.inc" %>
<title><%= request.getAttribute("titleRubrica") %></title>
<base target="_self" />
</head>

<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>

<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="it.avlp.simog.db.Costanti"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/scriptDitteAusiliarie.js" %>
<%@ include file="/script/domUtils.js" %>
<%@ include file="../include/gestisciErrore.inc" %>
<%@ include file="/script/AjaxPaesi.js" %>
<script type="text/javascript" src="script/pageutils.js"></script>

<script type="text/javascript">
function getSender(wnd) {
	if(wnd.dialogArguments)
		return wnd.dialogArguments.Sender;
	else return wnd.opener;
}

function loadDitteAusiliarie(){
	var padre = getSender(window); //window.opener;

	var readonlyAffid = window.showModalDialog ? padre.document.getElementById("<%= PSBD.FIELD_NAME_READONLY_AFFIDATARIO %>") : window.parent.jQuery("#<%= PSBD.FIELD_NAME_READONLY_AFFIDATARIO %>")[0];
	//id � il nome della riga dell'aggiudicatario nella tabella di affidatario.jsp
	var id = "<%= request.getParameter(PSBD.ID_TABELLA_AFFIDATARI) %>";
	var idRigaAgg = "hidden" + id + "<%=PSBD.FIELD_NAME_AGG_LISTA_AUSILIARIE%>";
	var rigaAgg = window.showModalDialog ? padre.document.getElementById(idRigaAgg) : window.parent.jQuery("#"+idRigaAgg)[0];
   
	if(rigaAgg!=null && rigaAgg!=""){
      var campi = rigaAgg.value.split("~");
      //l'ultima riga contiene campi nulli e non ci interessano
      for (i=0;i<campi.length-1;i++){
         var dittaAux = campi[i].split("|");
         addRowDittaAusiliariaOnLoad([<%=PSBD.argsAusiliarie%>],[<%=PSBD.argsAusiliarieNascosti%>],dittaAux,readonlyAffid.value,"<%=PSBD.DITTA_AUSILIARIA%>");
      }
	}
    
}

function salvaDitteAusiliarie(prefix){
	var idtable = "idTabella"+prefix;
	var table = document.getElementById(idtable);
	var lista = "";
	var record = "";
	var cellaAvvalimento = 4;
	var cellaIdSogg = 5;
	var errori = false;
	var messaggio="";
	if(table != null){
	    var numrows = table.rows.length;
	    //la riga zero contiene i campi th e non ci interessano
	    for(i=1;i<numrows;i++){
	    	 var cells = table.rows[i].getElementsByTagName("td");
	    	 //la riga zero contiene i campi azione e non ci interessano
			 for (j=1; j<cells.length-1;j++) {
				 var valore = cells[j].innerHTML;
				 if(j==cellaAvvalimento){
					 if(cells[j].innerHTML=="<%=PSBD.REQUISITI_FLAG_AVVALIMENTO%>"){
						 valore="1";
					 }
					 else if(cells[j].innerHTML=="<%=PSBD.ATTTESTAZIONE_FLAG_AVVALIMENTO%>"){
						 valore="2";
					 }
					 else if(cells[j].innerHTML=="<%=PSBD.ENTRAMBI_FLAG_AVVALIMENTO%>"){
						 valore="3";
					 }
					 else if(cells[j].innerHTML=="<%=PSBD.NESSUNO_FLAG_AVVALIMENTO%>"){
						 valore="0";
					 }
				 }
				 if(j==cellaIdSogg){
					 if(cells[j].innerHTML == null || cells[j].innerHTML == ""){
						 errori = true;
					    messaggio = messaggio + i + " ";
					 }	 
				 }
             record = record + valore + "|";
		    }
			 lista = lista + record + "~";
          //reset del record
			 record = "";
		 }
		 if(errori == false){ 
	       var padre = getSender(window); //window.opener;
	       var id = "<%= request.getParameter(PSBD.ID_TABELLA_AFFIDATARI) %>";
		    var idRigaAgg = "hidden" + id + "<%=PSBD.FIELD_NAME_AGG_LISTA_AUSILIARIE%>";
		       if(window.showModalDialog) {
		         var listaDitteAux = padre.document.getElementById(idRigaAgg);
		         listaDitteAux.value = lista;
		         window.close();
            } else { 
                  
                  window.parent.jQuery("#"+idRigaAgg).val(lista);  
                  window.parent.jQuery("#dialogDitteAusiliarie").dialog("close");
                  window.parent.jQuery("#dialogDitteAusiliarie").empty();
             
            }
		 }
		 else{
			 alert("<%= MessageHelper.getMessage(request, "scheda.erroreDittaAusiliariaNonPresente") %> " + messaggio);
		 }
	} 
}
function modAnagAgg(){
	apriPopUpMod('rubrica','<%= PSBD.TAB_AFFIDATARIO %>',document.getElementById('<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE%>'),document.getElementById('<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG%>'),'<%= MessageHelper.getMessage(request, "rubrica.rubricaOperatoriEconomici") %>',document.getElementById('<%= PSBD.FIELD_NAME_ANAGOE %>').value);
}		

</script>

<body style="min-width: 650px;">

<c:set var="readonlyAffid" value="<%=request.getParameter(PSBD.FIELD_NAME_READONLY_AFFIDATARIO) %>" scope="page"/>

<fieldset>				
<legend><utils:message key="scheda.datiAggiudicatario" /></legend>
<table>
  <tbody>
    <tr>	
		<th class="garaTh">Denominazione</th>					
		<th class="garaTh">Codice Fiscale</th>		
		<th class="garaTh">Codice Paese</th>
		<%-- <th class="garaTh">Ruolo</th>
		<th class="garaTh">Richiesta Ricorso Avvalimento</th>
		<th class="garaTh">Codice gruppo</th>--%>
	 </tr>
	 <tr>
	   <td class="garaTd"><%=request.getParameter(PSBD.FIELD_NAME_AGG_DENOMINAZIONE) %></td>
	   <td class="garaTd"><%=request.getParameter(PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO) %></td>
	   <%String idStato =  request.getParameter(PSBD.FIELD_NAME_AGG_ID_PAESE); //TICKET ALM #4365%>
	   <td class="garaTd">
	    <%if(idStato!=null && !idStato.equals("")){ %><%=idStato%> <!-- TICKET ALM #4365 -->
	     <%}else{ %>IT<%} %>
	   </td>
	   <%String temp =  request.getParameter(PSBD.ID_TABELLA_AFFIDATARI);%>
	   <%String ruolo =  request.getParameter(PSBD.FIELD_NAME_AGG_RUOLO);%>
	   <%-- 
	   <td class="garaTd">
			<%if("1".equals(ruolo)){ %>Mandataria
			<%}else if("2".equals(ruolo)){ %>Mandante
			<%}else{ %>&nbsp;<%} %>
		</td>	
	   <td class="garaTd"><%=request.getParameter(PSBD.FIELD_NAME_AGG_ID_GRUPPO) %></td>
	   --%>
	   <td style="display: none;"><input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" value="<%=request.getParameter(PSBD.FIELD_NAME_AGG_DENOMINAZIONE) %>" /></td>
	   <td style="display: none;"><input type="hidden" name="<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" value="<%=request.getParameter(PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO) %>" /></td>
	   <td style="display: none;"><input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_ID_STATO %>" value="<%=request.getParameter(PSBD.FIELD_NAME_AGG_ID_STATO) %>" /></td>
	   <td style="display: none;"><input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_RUOLO %>" value="<%=request.getParameter(PSBD.FIELD_NAME_AGG_RUOLO) %>" /></td>
 	   <%-- <td style="display: none;"><input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" value="<%=request.getParameter(PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO) %>" /></td> --%>
 	   <td style="display: none;"><input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" value="<%=request.getParameter(PSBD.FIELD_NAME_AGG_ID_GRUPPO) %>" /></td>
		<td style="display: none;"><input type="hidden" name="<%= PSBD.FIELD_NAME_ANAGOE %>" ></td>                 
	   
	 </tr>
</table>
</fieldset>

<fieldset>
<legend><utils:message key="scheda.elencoDitteAusiliarie" /></legend>
<% String prefixAgg = PSBD.DITTA_AUSILIARIA; %>
		<c:set var="prefixAgg" value="<%= prefixAgg %>" scope="page" />	
		<div id="DIVTabella<%= prefixAgg %>" class="scrollTabs" style="height: 200px; width: 99%;">
			<table id="idTabella<%= prefixAgg %>">
				<tbody>
    <tr>
		<th width="125"><utils:message key="table.azione" /></th>			
		<th class="garaTh"><utils:message key="table.denominazione" /></th>					
		<th class="garaTh"><utils:message key="table.codiceFiscale" /></th>		
		<th class="garaTh"><utils:message key="table.codicePaese" /></th>
		<th class="garaTh"><utils:message key="scheda.richiestaRicorsoAvvalimento" /></th>	
	 </tr>
	 
													
</table>

</fieldset>
      </div>	
      <c:if test="${readonlyAffid ne true}">			  				
		<c:if test="${hide != true}">	
			<div class="hmenu"><a id="showHide<%= prefixAgg %>Button" href="javascript:showSezioneAggiungi([<%= PSBD.argsAusiliarie %>],[<%=PSBD.argsAusiliarieNascosti%>],'<%= prefixAgg %>')" title="<utils:message key="button.aggiungi" plain="true" /> <%= prefixAgg %>"><utils:message key="scheda.aggiungiDittaAusiliaria" /></a></div>
		</c:if>
		</c:if>							
		<div  id="divAgg<%= prefixAgg %>" style="background-color:#E5E5E5; display: none; border: 1px solid #cfcfcf;">
			<table>
				<tr>
					<th><label for=""><utils:message key="scheda.codiceFiscaleDittaAusiliaria" /></label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixAgg%>')" /> 	
					   <%-- Aggiunto un campo con il codice paese  --%>   
					    <input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_PAESE%>" maxlength="2" size="2" value="" disabled onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
						<div class="hmenu"><a title="<utils:message key="scheda.modificaAnagrafica" plain="true" />" href="javascript:modAnagAgg();"><utils:message key="scheda.modificaAnagrafica" /></a></div></td>
					</td>				
					<td>
						<div class="hmenu"><a title="<utils:message key="scheda.cercaInRubrica" plain="true" />" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_DITTA_AUSILIARIA %>','<%= MessageHelper.getMessage(request, "rubrica.rubricaOperatoriEconomici") %>')"><utils:message key="scheda.cercaInRubrica" /></a></div>						
					</td>					
				</tr>
				<tr>
					<th><label><utils:message key="scheda.denominazioneDittaAusiliaria" /></label></th>
					<td>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr style="display: none;">
					<th><label>Aggiudicatario</label></th>
					<td>
						<input type="text" disabled id="<%= PSBD.FIELD_NAME_DESCRIZIONE %>" maxlength="16" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>			
					<td>&nbsp;</td>		
				</tr>
				
				<tr style="display: none;">
					<th><label>Percentuale</label></th>
					<td>
						<input onblur="validatePercentage(this)" type="text" id="<%= PSBD.FIELD_NAME_AGG_PERCENTUALE %>"
							size="6" maxlength="6"  style="text-align: right" value=""  onchange="setFormModified('Modificato<%=prefixAgg%>')" />%
					</td>
					<td>&nbsp;</td>		
				</tr>
				<tr>
					<th rowspan="2"><label><utils:message key="scheda.dittaAusiliataAvvalimento" /></label></th>
					<td>
						<input type="checkbox" id ="<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" value="1"  onchange="setFormModified('Modificato<%=prefixAgg%>')" /><utils:message key="scheda.perIRequisiti" />
					</td>
					<td>&nbsp;</td>		
				</tr>
				<tr>
					<td>			
						<input type="checkbox" id ="<%= PSBD.N_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" value="2"  onchange="setFormModified('Modificato<%=prefixAgg%>')" /><utils:message key="scheda.perLAttestazione" />
					</td>
					<td>&nbsp;</td>		
				</tr>	
				
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td>&nbsp;</td>		
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td>&nbsp;</td>		
				</tr>
				<tr style="display: none;">	
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td>&nbsp;</td>		
				</tr>
				<tr style="display: none;">	
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td>&nbsp;</td>		
				</tr>		
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_ANAGOE %>"  onchange="setFormModified('Modificato<%=prefixAgg%>')"/>
					</td>
				</tr>
				<tr>
					<td class="hmenu"><a id="AddMod<%= prefixAgg %>" href="javascript:addRow([<%= PSBD.argsAusiliarie %>],[<%=PSBD.argsAusiliarieNascosti%>],'<%=prefixAgg%>')"><utils:message key="button.aggiungi" /></a></td>
					<td>&nbsp;</td>		
				</tr>
			</table>	
			<input type="hidden" id="Modificato<%= prefixAgg %>" name ="Modificato<%= prefixAgg %>" value="0">			
		</div>		
<!--</fieldset> -->
<input type="hidden" id="Modificato2" name ="Modificato2" value="<c:out value="${param['modificato2']}" />">
<input type="hidden" id="selected<%= prefixAgg %>" value="0" />
<input type="hidden" id="<%= PSBD.VAR_ANN %>" name ="<%= PSBD.VAR_ANN %>" value="<c:out value="${variazioniAnagrafiche}" />">

<p></p>
</div>
</div>
<c:if test="${readonlyAffid ne true}">			
<input
	type="button" id="salva" name="salva" value="<utils:message key="button.salva" plain="true" />"
	onclick="javascript:salvaDitteAusiliarie('<%=prefixAgg%>');">
</c:if>	
<script type="text/javascript">
  window.onload = function(){loadDitteAusiliarie();}
</script>
	
<input type="button"
	value="<utils:message key="rubrica.torna" plain="true" />" onclick="chiudiPopUp('dialogDitteAusiliarie')"></fieldset>

</body>
</html>
	