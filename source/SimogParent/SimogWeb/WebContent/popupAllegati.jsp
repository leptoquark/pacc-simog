<%try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="include/basicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title><utils:message key="popup.gestioneAllegatiGara" /> [<%= (String) request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA) %>]</title>
<base target="_self" />

<script type="text/javascript" src="xtree/treeutils.js"></script>
<%@ include file="include/i18n-init.inc" %>

<script type="text/javascript">
<!--
	function invioFile(){
		if(document.getElementById('InviaFileAggiornamento').putFile.value == ''){
			if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.selectFile'); } else { alert('Selezionare un file'); }
			return false;
		}
		
		document.body.style.cursor = 'wait';
		
		return true;
	}

	function disabilita(obj){
		//var obj = document.getElementById("cmdConf")
		obj.innerHTML = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('message.wait') : "Attendere prego ...";
		obj.disabled="disabled";
	}
	
	function abortFile(id){
	
	document.forms[0].action='<%= ParametriServlet.SRV_GESTISCI_ALLEGATI %>?action=abort&<%=ParametriServlet.IDALLEGATO %>=' + id;
    document.forms[0].target="";
	document.forms[0].submit();	
	}
	
	function viewFile(id){
		document.forms[0].action='<%= ParametriServlet.SRV_GESTISCI_ALLEGATI %>?action=view&<%=ParametriServlet.IDALLEGATO %>=' + id;
		document.forms[0].target="_blank";
		document.forms[0].submit();	
		var t=setTimeout("window.close()",2000); // patch chrome
	}
//-->
</script>
</head>
<body>
	<form onsubmit="disabilita(this.cmdConf);" id="InviaFileAggiornamento" ENCTYPE="multipart/form-data" method="POST" action="<%= ParametriServlet.SRV_GESTISCI_ALLEGATI %>?action=save">

	<div class="bodypage">
	<div class="bodypage-e">
	
	<h1>Gestione Allegati</h1>
	<%@ include file="include/gestisciErrore.inc" %>

	<br><br>
	<div class="hmenu">
		<ul>
		<li><a href="javascript:setRetVal('<%= ParametriServlet.FIELD_NAME_ID_INFO %>', false,'<%=(String)request.getSession().getAttribute(ParametriServlet.RETFIELD) %>', document.getElementById('cmdConf').disabled );">Chiudi</a></li>
		</ul>
	</div>		

	<div class="gara">
		<fieldset>
		<legend>Allegati inclusi</legend>
			
	<%	String disabled = "";				
		ArrayList listaDocumenti =(ArrayList) request.getAttribute(ParametriServlet.DOCUMENTI);
		if(listaDocumenti != null && listaDocumenti.size()>0){
			disabled = "disabled";
	%>
		
			<div class="elencoCategorie" style="height:150px;">
				<table background="#F1F2F8" class="TableBean" cellpadding="3" style="width:90%;">
					<tr>
						<th class='TableBeanTitle'>Tipo Documento</th>
						<th class='TableBeanTitle'>Nome Documento</th>
						<th class='TableBeanTitle'>Note</th>
					</tr>
				<%
					for(int j=0;j<listaDocumenti.size();j++){
						AllegatoBean doc = (AllegatoBean)listaDocumenti.get(j);
				%>
					<tr class="TableBeanOdd">
						<% String descr = PubblicazioneBean.TipoDocumento.getEnumByTipo(doc.getTipoDoc()).getDescr(); %>
						<td><%= descr %></td>
						<td align="left"><%=doc.getNomeFile() %></td>
						<td align="left"><%=doc.getNote() %></td>
						<td><button type="button" name="elimina" onclick="abortFile(<%=doc.getIdAllegato() %>);">Elimina </button></td>
						<td><button type="button" name="visualizza" onclick="viewFile(<%=doc.getIdAllegato() %>);">Visualizza </button></td>
					</tr>	
				<%	}	%>
				</table>		
			</div>
			<% } else out.println("<BIG>NON SONO DISPONIBILI ALLEGATI PER LA GARA</BIG>"); 	%>
			</fieldset>
		</div>
		<div class="testo">
			<fieldset>
			<input type="hidden" id="<%=ParametriServlet.NOMEFILE %>" name="<%=ParametriServlet.NOMEFILE %>" value="<%=(String)request.getSession().getAttribute(ParametriServlet.NOMEFILE) %>" />
			<input type="hidden" id="<%=ParametriServlet.TIPODOC %>" name="<%=ParametriServlet.TIPODOC %>" value="<%=(String)request.getSession().getAttribute(ParametriServlet.TIPODOC) %>" />
			<input type="hidden" id="<%=ParametriServlet.RETFIELD %>" name="<%=ParametriServlet.RETFIELD %>" value="<%=(String)request.getSession().getAttribute(ParametriServlet.RETFIELD) %>" />
			<input type="hidden" id="<%=ParametriServlet.SESSION_ID_GARA %>" name="<%=ParametriServlet.SESSION_ID_GARA %>" value="<%=(String)request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA) %>" />
			<% // salvo id allegato per il ritorno
				String currId = "";
				if(listaDocumenti != null && listaDocumenti.size() > 0){
					AllegatoBean doc = (AllegatoBean)listaDocumenti.get(0);
					currId = String.valueOf(doc.getIdAllegato());
				}
			%>
			<input type="hidden" id="<%=ParametriServlet.FIELD_NAME_ID_INFO %>" value="<%= currId %>" />

			<legend>Carica nuovo allegato</legend>
			<table>
			  <tr>
			  	<th>Selezionare il file da allegare</th>		
				<td><input type="file" name="putFile" <%= disabled %>></td>
			  </tr>
			  <%-- gm le note dell'allegato rettifica saranno gestite in jsp e non nel popup--%> 
			  <%if (!((String)request.getSession().getAttribute(ParametriServlet.RETFIELD)).equals(ParametriServlet.ALLEGATO_RETTIFICA)) {%>
			  <tr>
			    <th>Note all'allegato</th>
			    <td>
			    	<input <%= disabled %>  size="100%" type="text" maxlength="250" name="<%=ParametriServlet.NOTEALL %>" value="<%=(String)request.getSession().getAttribute(ParametriServlet.NOTEALL) %>" />
			    </td>
			  </tr>
			  <% } else {%>
		       	<input type="hidden" name="<%=ParametriServlet.NOTEALL %>" value="" />	  
			  <% } %>
			  <tr>
			    <td>
			    	<button <%= disabled %> type="submit" id="cmdConf" name="conferma" onclick="return invioFile();">Aggiungi Allegato </button>
			    </td>
			  </tr>
			</table>
			</fieldset>
		</div>
		<div id="warnChrome" style="display:none">
			<table width="100%"  cellpadding="5">
				<tr>
				<td rowspan="3">
					<img src="img/simogWarning_little.jpg" width="60px" height="55px" align="left" alt="img simog warning">
				</td>
				</tr>
				<tr>
					<td align="left" valign="top"><p style="color:rgb(180,0,28);">
						<strong>ATTENZIONE:</strong>Il browser Chrome non gestisce correttamente la finestra pop-up, dopo l'inserimento dell'allegato chiudere la finestra pop-up e premere il tasto F5 per effettuare l'aggiornamento della pagina di pubblicazione.
					</td>
				</tr>
			</table>		
		</div>
	</div>
	</div>
	</form>
	
	<script type="text/javascript">
		window.resizeTo(800, 600);
        if(window.showModalDialog){
		    window.onunload=function(){setRetVal('<%=  ParametriServlet.FIELD_NAME_ID_INFO %>', true,'<%=(String)request.getSession().getAttribute(ParametriServlet.RETFIELD) %>');}; 
		}
        window.returnValue = "";
		var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
		
		if(is_chrome == true){
			var style = document.getElementById("warnChrome").style;
			style["display"] = "block";
		}
					
/***		
		var xWin; 
		xWin = getSender(window);
		
		if (xWin){
			var dest = xWin.document.getElementById('<%=(String)request.getSession().getAttribute(ParametriServlet.RETFIELD) %>');
			dest.value='<%= (String)request.getSession().getAttribute(ParametriServlet.IDALLEGATO)  == null ? "" : (String)request.getSession().getAttribute(ParametriServlet.IDALLEGATO) %>';
			var dest2 = xWin.document.getElementById('<%=(String)request.getSession().getAttribute(ParametriServlet.RETFIELD) %><%=ParametriServlet.DESCPREF %>');
			dest2.value='<%= (String)request.getSession().getAttribute(ParametriServlet.IDALLEGATO) == null ? "" : "PRESENTE"%>';
		}
		else
			if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.xwinNotFound'); } else { alert("XWIN not found!"); }
***/			
	</script>
</body>
<%@page import="it.avlp.simog.beans.AllegatoBean"%>
<%@page import="it.avlp.simog.beans.PubblicazioneBean"%>
</html>
<% } catch (Exception e) {e.printStackTrace();}%>
