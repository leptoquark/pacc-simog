<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="../errore.jsp"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="../include/basicHeader.inc"%>

<%@ include file="../include/controlloSessione.inc"%>
<title>Elenco CIG</title>
<base target="_self" />
</head>

<%@ page import="it.avlp.simog.beans.*"%>
<%@ page
	import="it.avlp.simog.common.servlet.*,it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.util.*"%>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.db.generated.*"%>

<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%try{ %>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js"%>
<%@ include file="/script/domUtils.js"%>
<%@ include file="../include/gestisciErrore.inc"%>
<%@ include file="/script/AjaxPaesi.js"%>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script> 
<script type="text/javascript" src="script/pageutils.js"></script>  
<script type="text/javascript">  
 
function loadCig(){
 	 var idRigaAgg = "hidden_lista_cig"; 
 	 var cigselected = window.parent.jQuery('#hidden_lista_cig').val(); 
 		document.getElementById(cigselected).checked=true;  
}

function salvaCig(prefix){
	var idtable = "idTabella"+prefix;
	var table = document.getElementById(idtable); 
	var record = "";  
	var messaggio="";
	if(table != null){ 
		 var radios = document.getElementsByName("radiocig");
		for (var i = 0, length = radios.length; i < length; i++){
		 if (radios[i].checked) { 
			  record = radios[i].id; 
			  console.log(record);
			  break;
		 }
		} 
		 if(record != ""){    
	       window.parent.jQuery('#hidden_lista_cig').val(record); 
	       idDialog = "dialogcig";
	       chiudiPopUp(idDialog);
		 } else{
			 messaggio = "nessun cig scelto";
			 if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.errorRow', {row: messaggio}); } else { alert("Errore riga: " + messaggio); }
		 } 
	} 
}
</script>
<body> 
<div class="bodypage-e">
	<form action="elencoCig" method="post" name="addcig" id="addcig">  
					<fieldset>				
						<legend>Elenco Iniziative Soggetti Aggregatori </legend>
						<table id="idTabellaCigsecondari"> 
							<tbody>
								<tr><th></th>
									<th>CIG</th>
									<th>Oggetto Iniziativa</th>
									<th>Soggetto Aggregatore</th>
								</tr> 
								<tr>
									<td><input type="radio" id="radio-1" name="radiocig" value="A" /></td>
									<td>CIG 1</td>
									<td>Fornitura Macchinari Medici</td>
									<td>Consip</td>
								</tr>
								<tr>
									<td><input type="radio" id="radio-2" name="radiocig" value="B" /></td>
									<td>CIG 2</td>
									<td>Servizi Assistenziali</td>
									<td>Anac</td>
								</tr>
								 
							</tbody>
						</table> 
					</fieldset> 
					<fieldset>
						<input type="checkbox" name="autodichiarazione" value="1"/> Autodichiarazione SA
					</fieldset> 
					<div align="left" style="margin: 2px;padding: 2px"> 
						<input
						type="button" id="salva" name="salva" value="Salva"
						onclick="salvaCig('Cigsecondari')"> 
						<input type="button" value="Annulla" onclick="chiudiPopUp()" />
					</div>
		</form>
</div>
 <script type="text/javascript">
  window.onload = function(){loadCig();}
</script>
</body>
<%} catch(Exception e){e.printStackTrace();} %>
</html>