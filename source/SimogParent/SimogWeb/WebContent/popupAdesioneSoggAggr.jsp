<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@page import="it.avlp.simog.beans.IniziativaSoggAggr"%>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="include/basicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<title>SIMOG - Visualizzazione elenco delle iniziative disponibili</title>
<base target="_self" />

<%				
		ArrayList listaIniziative =(ArrayList) request.getAttribute(ParametriServlet.LISTA_INIZIATIVE_DISPONIBILI);
        String from = (String)request.getAttribute("from");


%>

<script type="text/javascript" src="xtree/treeutils.js"></script>
<!-- TICKET ALM #4222 - 3.04.4 - Lib jquery per chiamata asincrona soggetti aggregatori -->
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<%@ include file="include/i18n-init.inc" %>
<script type="text/javascript">


//TB: gestione popup modale
function closePopup(annulla,from) {
       if(!annulla){
             var selValue = $('input[name=soggAggr]:checked').val();
            // alert(from)
             if(from=="gara") {
            	var flagNoIniziativa = $('input[name=noSelIniz]').prop('checked');
            	 if(!flagNoIniziativa && 
                    	     (selValue==null || selValue=="")
                   ){
                     alert("<%= it.avlp.simog.util.MessageHelper.getMessage(request, "popup.selezionareElementoLista") %>");
                     return;
                 } else {
                     if(from=="gara") {
                          if(flagNoIniziativa) 
                        	  window.parent.jQuery("#idNoCheckSoggAggrByUser").val("OK");
                           else {
                               var cigAccordoQuadro = selValue.split("##")[0];
                               var flagComp = selValue.split("##")[1];
                               var idModoReal = null;
                               if(flagComp=="S")
                            	   idModoReal = "2";
                               else if(flagComp=="N")
                            	   idModoReal = "11";
                  
                               window.parent.jQuery("input[name='CIGQUADRO']").val(cigAccordoQuadro);
                               if(idModoReal != null)
                            	    window.parent.jQuery("select[name='modoRealizzazione']").val(idModoReal);
                           }
						 window.parent.jQuery("#dialogSoggAggr").dialog("close");
                       } 
                  }
              } else {
                      var flagSaNonSoggetta = $('input[name=checkSaNonSoggetta]').prop('checked');
                      var flagSaNonClassificata = $('input[name=checkSaNonClassificata]').prop('checked');
                  //   alert(flagSaNonSoggetta);
                  //   alert(flagSaNonSoggetta);
                    if(!flagSaNonSoggetta && !flagSaNonClassificata && (selValue==null || selValue==""))
                        if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.selectInitiative'); } else { alert("Selezionare una iniziativa o almeno una autodichiarazione"); }
                    else {
                             //Se sono state selezionate le autodichiarazioni, queste valgono per prime rispetto all'iniziativa
                             if(flagSaNonSoggetta || flagSaNonClassificata) {
                            	 if(flagSaNonSoggetta)
                            	      window.parent.jQuery("input[name='FIELD_NAME_FLAG_SA_NO_DPCM']").val("OK");

                                 if(flagSaNonClassificata)
                                      window.parent.jQuery("input[name='FIELD_NAME_FLAG_SA_NO_CLASSIFICATA']").val("OK");
                             } else {
                            	 var cigAccordoQuadro = selValue.split("##")[0];
                            	 window.parent.jQuery("input[name='FIELD_NAME_CIG_INIZIATIVA_SEL']").val(cigAccordoQuadro);
                             }
							 window.parent.jQuery("#dialogSoggAggr").dialog("close");
                        }
                      
                  }

           } else
		window.parent.jQuery("#dialogSoggAggr").dialog("close");
	
}

function enableOrDisbleList(cb,otherCb){
	var disableList = cb.checked || (otherCb!=null && $("#"+otherCb).prop('checked'));

	if(disableList) {
		$("input[name='soggAggr']").prop("disabled", true);
		$("input[name='soggAggr']").prop("checked", false);
	 } else
		 $("input[name='soggAggr']").prop("disabled", false);
}

</script>

</head>
<body>


<h1>Elenco iniziative</h1>
	<%@ include file="include/gestisciErrore.inc" %>

	<br><br>	

	<div class="gara">
		<fieldset>
		<legend><utils:message key="popup.iniziativeDisponibili" /></legend>
			<div class="elencoCategorie" style="height:150px;">
				<table background="#F1F2F8" class="TableBean" cellpadding="3" style="width:95%;">
					<tr>
						<th class='TableBeanTitle'><utils:message key="table.selezione" /></th>
						<th class='TableBeanTitle'><utils:message key="popup.cigIniziativa" /></th>
						<th class='TableBeanTitle'><utils:message key="popup.oggettoIniziativa" /></th>
						<th class='TableBeanTitle'><utils:message key="popup.soggettoAggregatore" /></th>
						<th class='TableBeanTitle'><utils:message key="popup.note" /></th>
						<th class='TableBeanTitle'><utils:message key="popup.link" /></th>
					</tr>
				<%
					for(int j=0;j<listaIniziative.size();j++){
						IniziativaSoggAggr iniz = (IniziativaSoggAggr)listaIniziative.get(j);
				%>
					<tr class="TableBeanOdd">
						<td width="5%" ><input type="radio" name="soggAggr" value="<%= iniz.getCIG()+"##"+iniz.getFlagConfrontoComp() %>" /></td>
						<td width="10%" align="left"><%= iniz.getCIG() %></td>
						<td width="25%" align="left"><%= iniz.getDescrizioneIniziativa() %></td>
						<td width="20%" align="left"><%= iniz.getDescrizioneSoggAggr() %></td>
						<td width="30%" align="left"><%= iniz.getNote() %></td>
                        <td width="10%" align="left"><a href="<%= iniz.getLink() %>" target="_blank">Link</a></td>
					</tr>		
				<% } %>
				</table>		
			</div>
	
			</fieldset>
		</div>
	
		  
		  <%if("lotto".equals(from)) { %>
		     <table>
		     <tr>
			   <td>
                  <label><p style="font-weight: bold;"><a href="<%= SimogProperties.getInstance().getLinkEntiagg() %>" target="_blank">&nbsp;<img title="Cliccare per informazioni estese" src="img/info-32x32.png" /></a>Il sottoscritto, consapevole che la falsit&agrave; in atti e le dichiarazioni mendaci sono punite ai sensi del codice penale e delle leggi speciali in materia, dichiara:</label>
			   </td>
		     </tr>
			<tr>
			   <td>
			     <input type="checkbox" onchange="enableOrDisbleList(this,'checkSaNonClassificata')" id="checkSaNonSoggetta" name="checkSaNonSoggetta">1. che questa stazione appaltante non � soggetta agli obblighi del DPCM 24 dicembre 2015 e ss.mm.ii.
			   </td>
		     </tr>
		     <tr>
			   <td>
			   	<input type="checkbox" onchange="enableOrDisbleList(this,'checkSaNonSoggetta')" id="checkSaNonClassificata" name="checkSaNonClassificata">2. che nessuna delle iniziative disponibili presso i soggetti aggregatori di riferimento ha caratteristiche in grado di soddisfare i fabbisogni di questa stazione appaltante
			   </td>
		     </tr>
		     </table>
		  
		  <% } else if("gara".equals(from)) {
				%> <table>
				<tr>
				   <td>
				     <input type="checkbox" onchange="enableOrDisbleList(this)" id="noSelIniz" name="noSelIniz"><label for="noSelIniz">Nessuna iniziativa da selezionare</label>
				   </td>
			     </tr>
					</table>
				 <% } %>	
		<table>
			<tr><td>
				<div class="hmenu">
					<ul>
					<li><a href="javascript:closePopup(false,'<%= from %>');"><utils:message key="popup.confermaSelezione" /></a></li>
					</ul>
				</div>	</td><td>&nbsp;</td><td>
				<div class="hmenu">
					<ul>
					<li><a href="javascript:closePopup(true,'<%= from %>');"><utils:message key="button.annulla" /></a></li>
					</ul>
				</div>	
			</td></tr>
		</table>

</body>
</html>