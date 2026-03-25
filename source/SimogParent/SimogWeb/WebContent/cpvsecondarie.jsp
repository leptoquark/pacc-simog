<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="/include/basicHeader.inc" %>
<%@ include file="/include/controlloSessione.inc" %>
<link rel="stylesheet" href="theme/stile.css"/>
<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %> 
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script> 
<script type="text/javascript" src="xtree/treeutils.js"></script> 
<script type="text/javascript" src="xtree/xtree2.js"></script>
<script type="text/javascript" src="xtree/xmlextras.js"></script>
<script type="text/javascript" src="xtree/xloadtree2.js"></script>
<link type="text/css" rel="stylesheet" href="xtree/xtree2.css" /> 
<link rel="stylesheet" href="theme/jquery-ui-popup.css" /> 

<title><utils:message key="cpv.cpvSecondarie" /></title>
<base target="_self">
<script type="text/javascript">
function getSender(wnd) {
	if(wnd.dialogArguments)
		return wnd.dialogArguments.Sender;
	else return wnd.opener;
}

function loadCpv(){
	var padre = getSender(window); //window.opener; 
	 var idRigaAgg = "hidden_lista_cpv_secondarie1"; 
	 console.log("idRigaAgg:"+idRigaAgg);
	var rigaAgg = window.showModalDialog ? padre.document.getElementById(idRigaAgg) : window.opener.document.getElementById(idRigaAgg).value;
	if(rigaAgg!=null && rigaAgg!=""){
      var campi = rigaAgg.split("~");
      //l'ultima riga contiene campi nulli e non ci interessano
      for (i=0;i<campi.length-1;i++){
         var listaCpv = campi[i].split("|");
         console.log(listaCpv);
      }
	}
}

function salvaCpv(prefix){
	var idtable = "idTabella"+prefix;
	var table = document.getElementById(idtable);
	var lista = "";
	var record = "";
	//var cellaAvvalimento = 4;
	var cellaId = 4;
	var errori = false;
	var messaggio="";
	if(table != null){
	    var numrows = table.rows.length;
	    //la riga zero contiene i campi th e non ci interessano
	    for(i=1;i<numrows;i++){
	    	 var cells = table.rows[i].getElementsByTagName("td");
	    	 //la riga zero contiene i campi azione e non ci interessano
			 for (j=1; j<cells.length;j++) {
				 var valore = cells[j].getElementsByTagName("input")[0].value;
				 
				 if(j==cellaId){
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
		 console.log(lista);
		 if(errori == false){  
			 
	       var padre = getSender(window); //window.opener;
	       var idRigaAgg = "hidden_lista_cpv_secondarie1";
	       var listaCpv = padre.document.getElementById(idRigaAgg);
           listaCpv.value = lista;
           window.close(); 
		 }
		 else{
			 if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.errorRow', {row: messaggio}); } else { alert("Errore riga: " + messaggio); }
		 }
	} 
}
 
	 
</script>
</head>

<body onload="">    
<div class="bodypage-e">
	<form id="IdFormPopup" action=""  method="post">
	<div class="bodypage-e">
	 <fieldset style="margin: 2px;padding: 2px">
	<legend><utils:message key="cpv.cpvSecondarie" /></legend>
	<fieldset style="margin: 2px;padding: 2px">
		<div id="DIVTabellaCpv" class="scrollTabs" style="height: 200px; width: 99%;">
			<table id="idTabellaCpv">
				<tbody>
				    <tr>
						<th width="125" class="garaTh"><utils:message key="table.azione" /></th>			
						<th class="garaTh"><utils:message key="cpv.cpv" /></th>		 
						<th width="125" class="garaTh"><utils:message key="cpv.descrizione" /></th>	
					 </tr> 
				 </tbody> 			
			</table>  
	     </div> 
	</fieldset>  
 	<div class="detailHelp" id="divAgg" style="border: 1px solid #cfcfcf;">
			<table width="100%">					
				<tr>
					<td>
						<input type="text" id="sel_CPV"  maxlength="12"  name="<%= ParametriServlet.FIELD_NAME_CPV %>" 
						   value=""  
						   id="sel_CPV" >			
						<!-- <input type="button" value="Cerca" onclick="apripopup('ricercaCPV.jsp', 'sel_CPV')" /> 	 -->
			 			<a class="getCPV" href="#"  onclick="apripopup('ricercaCPV.jsp', 'sel_CPV')" title="<utils:message key="ricerca.elencoCPV" plain="true" />"><img src="img/icon_info_sml.gif"></a> 
					 </td> 
				</tr>
				<tr>
					<td class="hmenu">
						<a id="AddMod" href="#" onclick="addRowCpv()"><utils:message key="button.aggiungi" /></a>
					</td>
				</tr>							
			</table>
			<input type="hidden" id="Modificato" name ="Modificato" value="0">
	</div>		
	<div align="left" style="margin: 2px;padding: 2px"> 
		<input
			type="button" id="salva" name="salva" value="<utils:message key="button.salva" plain="true" />"
			onclick="salvaCpv('Cpv')"> 
		<input type="button" value="<utils:message key="button.annulla" plain="true" />" onclick="chiudiPopUp()" />
	</div>
	</fieldset> 
 </div>
 </form>
 </div>
 <script type="text/javascript">
  window.onload = function(){loadCpv();}
</script>
</body>
</html>
	