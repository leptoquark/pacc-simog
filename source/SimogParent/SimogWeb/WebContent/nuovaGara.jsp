<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.util.*"%>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import= "it.avlp.simog.beans.*" %>
<%@ page import= "it.avlp.simog.common.servlet.*" %>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.List"%>

<%	int indiceTab = 0; %>

<title>SIMOG - <utils:message key="nuovaGara.titolo" /></title>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script> 
<script type="text/javascript" src="script/funzioni.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<script type="text/javascript" src="xtree/treeutils.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<link rel="stylesheet" href="theme/jquery-ui-popup.css" /> 
<style>
#modalContainer {
	background-color:transparent;
	position:absolute;
	width:100%;
	height:100%;
	top:0px;
	left:0px;
	z-index:10000;
}

#alertBox {
	position:relative;
	min-height:100px;
	margin-top:200px;
	border:2px solid #000;
	background-color:#F2F5F6;
	background-image:url(img/alert.png);
	background-repeat:no-repeat;
	background-position:20px 30px;
}

#modalContainer > #alertBox {
	position:fixed;
}

#alertBox h1 {
	margin:0;
	font:bold 0.9em verdana,arial;
	background-color:#BECBE6;
	border-bottom:1px solid #000;
	padding:2px 0 2px 5px;
	text-align:left;
}

#alertBox p {
	padding-left:5px;
	margin-left:55px;
}

#alertBox a {

	font-size:small;
	font-weight: bold;
}

#alertBox #closeBtn {
	display:block;
	position:relative;
	margin:5px auto;
	padding:3px;
	border:1px solid #000;
	width:70px;
	text-transform:uppercase;
	text-align:center;
	color:navy;
	background-color:#BECBE6;
	text-decoration:none;
	font-weight: bold;
}
</style>

</head>

<script type="text/javascript" src="script/customAlertBox.js"></script>

<!-- TICKET ALM #4222 - 3.04.4 - Lib jquery per chiamata asincrona soggetti aggregatori -->
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>

<script type="text/javascript">
 
//<!--
function setVal(elem){
	if(!elem.disabled && !elem.readonly){
		if(elem.checked)
			document.getElementById("hidden<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>").value = "S";
		else
			document.getElementById("hidden<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>").value = "N";
	}
}

	function convertiTesto(elem){
		document.getElementById("OggettoGara").value = document.getElementById("OggettoGara").value.split(String.fromCharCode(8364)).join("&#8364;");
	}

	function upReqVal(){
		var tmp = document.getElementById("checkIfOK");
		
		tmp.value=parseInt(tmp.value) + 1;
		
		return true;
	}

	function inputRadio(numberOfAmm){

		var counter = 0;
		while(counter < numberOfAmm){
			counter++;
			var radio = document.getElementById(counter);
			if(radio.checked == true){ return true; }			
		}
		alert("Scegliere una Stazione Appaltante");
		return false;
		
	}

	function doActionInserisci (numberOfAmm, isAdmin,isAfter3044){
		/* MEV 46487  3.04.11 */
		alert("AVVISO: Si ricorda che gli eventuali CIG generati sono validi solo per i casi disciplinati dalla delibera 582 del 13 dicembre 2023 e che pertanto la pubblicazione deve essere completata entro 48 ore dalla relativa data di creazione");
		if(numberOfAmm == null)
			numberOfAmm = 1;
			
		if(inputRadio != null && !inputRadio(numberOfAmm)) return false;
		
		setVal(document.getElementById("<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>"));

		var categorie = "";
		 $("input:checkbox[name=categoria]:checked").each(function(){
	    	    categorie = categorie+($(this).val())+"|";
	    	});
		 if(categorie.length > 0) {
			 var checkNoIniziativeByUser = $("input[name=FIELD_NAME_CHECK_INIZIATIVE]").val();
		    if(isAdmin || categorie=="999|" || !isAfter3044 || checkNoIniziativeByUser=="OK")
		validateAndAction();
		    else
		    	doCallSoggAggrGara(categorie,null); 
		 } else {
	    		alert("Scegliere almeno una Categorie merceologiche oggetto della fornitura di cui al DPCM soggetti aggregatori");
	     }
		
	}

	function MyDialogArguments() {
		this.Sender = null;
	}
	

	
	function doActionModifica(action, isAdmin,isAfter3044){
		var ammin = document.forms[0].<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>;
		var numberOfAmm = null;
		
		if(ammin != null) 
			numberOfAmm = ammin.length;
		
		if(numberOfAmm == null)
			numberOfAmm = 1;
			
		if(ammin != null && !inputRadio(numberOfAmm))return false;

		setVal(document.getElementById("<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>"));

		var categorie = "";
		 $("input:checkbox[name=categoria]:checked").each(function(){
	    	    categorie = categorie+($(this).val())+"|";
	    	});
		 if(categorie.length > 0) {

			 var checkNoIniziativeByUser = $("input[name=FIELD_NAME_CHECK_INIZIATIVE]").val();
		     console.log("checkNoIniziativeByUser "+checkNoIniziativeByUser);	 
		    if(isAdmin || categorie=="999|" || !isAfter3044 || checkNoIniziativeByUser=="OK")
			doAction(action);
		    else
		    	doCallSoggAggrGara(categorie,action); 
		 } else {
			 if(isAfter3044)
	    		alert("Scegliere almeno una Categorie merceologiche oggetto della fornitura di cui al DPCM soggetti aggregatori");
			 else
				 doAction(action);
	     }
		
/*		if(!hasErrors(document.forms[0]))
			doAction(action); */
	}	

	 
    function checkAccordoQuadro() {
        var selectedValue = $("select[name=modoRealizzazione]").val();
        if(selectedValue==17 || selectedValue==18){
        	$("select[name=strumentoSvolgimento]").val("6");
        	$('select[name=strumentoSvolgimento]').find('option[value="6"]').attr("selected",true);
        	//$("select[name=strumentoSvolgimento]").prop('disabled', 'disabled');
        	$('select[name=strumentoSvolgimento]').find('option:not(:selected)').attr('disabled', true);
         } else
        	 $('select[name=strumentoSvolgimento]').find('option:not(:selected)').attr('disabled', false);
        	 //$("select[name=strumentoSvolgimento]").prop('disabled', false);
    } 

	function buttonSave(action){

		var ammin = document.forms[0].<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>;
		var numberOfAmm = null;
		
		if(ammin != null) 
			numberOfAmm = ammin.length;
		
		if(numberOfAmm == null)
			numberOfAmm = 1;
			
		if(ammin != null && !inputRadio(numberOfAmm))return false;

		setVal(document.getElementById("<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>"));

if(!hasErrors(document.forms[0])){
			var categorie = "";
			 $("input:checkbox[name=categoria]:checked").each(function(){
		    	    categorie = categorie+($(this).val())+"|";
		    	});
			 if(categorie.length > 0) {
			    if(isAdmin || categorie.length=="999|" || !isAfter3044)
			    	doAction(action);
			    else
			    	doCallSoggAggrGara(categorie,action); 
			 } else {
		    		alert("Scegliere almeno una Categorie merceologiche oggetto della fornitura di cui al DPCM soggetti aggregatori");
		     }
			
		}
	}	
	
	function checkDelega(ctrl){
		if (ctrl.title != "null"){
			customAlert(ctrl.title)
			// PP solo avviso ctrl.checked=false;
		}
	}

 
	
function checkChangeGara(obj, obj2, dataComun, admin) {
	if( dataComun != null && dataComun != "null" && dataComun != "" && !admin){
	if( obj.type == "radio"){
		obj.checked = obj.defaultChecked;
		obj2.checked = !obj.checked;
	}
	else{
		obj.value = obj.defaultValue;
	}
	alert("Impossibile modificare l'importo in quanto il dato e' stato trasmesso al sistema Riscossione!");
	return false;
}

if( obj.type == "radio"){
	abilitaImporto(obj);
	showAlertInd();
	return true;
	}
}

function showAlertInd(){
	var elem = document.getElementById("alertInd");
	if(elem == null) return;
	
	var style = document.getElementById("alertInd").style;
	var campo = document.getElementById("euro");
	if(campo.disabled == true)
		style["display"] = "block";
	else	
		style["display"] = "none";
}

function clearSelected(comboSorgente){
    var elements = document.getElementById(comboSorgente).options;

    for(var i = 0; i < elements.length; i++){
      elements[i].selected = false;
      elements[i].removeAttribute("selected");
    }
  }

function checkSettoreGara() {
    var selectedValue = $("select[name=FLAG_ENTE_SPECIALE]").val();
    var disabledField = false;
    if(selectedValue=="O"){
    	disabledField = "disabled";
    	clearSelected('sel_MODO_INDIZIONE_GARA');
    }
    	
    $("select[name=ID_MODO_GARA]").prop('disabled', disabledField);
    
} 

function initGara(){
	checkSettoreGara();
}

//-->
</script>
<script type="text/javascript" src="script/scriptSoggAggr.js"></script>
<body onload="javascript:initGara();"">
<div id="dialogcig"></div>
<% // uso momentaneamente PSBD.LISTA_CPV_SECONDARIE2 per salvare le cig %>
<input type="hidden" id="hidden_lista_cig" name="lista_cig" value="" />

	<%  //UN Informazioni della gara passate dalla servlet inizializzaGara (per la modifica) 
		String idStazioneAppaltante = (request.getAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE): "";
		String oggettoGara = (request.getAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA): "";
		String numeroLotti = (request.getAttribute(ParametriServlet.FIELD_NAME_NUMERO_LOTTI) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_NUMERO_LOTTI): "";
		if("null".equals(numeroLotti)) numeroLotti = "";
		// TICKET ALM - 3.04.3 #659
		String durataGiorni = (request.getAttribute(ParametriServlet.FIELD_NAME_DURATA_GIORNI) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_DURATA_GIORNI): "";
		if("null".equals(durataGiorni)) durataGiorni = "";
		// FINE TICKET ALM - 3.04.3 #659
		String reqImportoGara = (request.getAttribute(ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO): "";
		String inserisciImporto = (!"-1".equals(reqImportoGara))? ParametriServlet.INSERISCI_IMPORTO_SI : ParametriServlet.INSERISCI_IMPORTO_NO;
		//UN Se esiste un id_gara allora sono nella modalit� gestionGara 
		boolean gestioneGara = (request.getAttribute(ParametriServlet.FIELD_NAME_ID_GARA) != null);
		String dataComun = (String) request.getAttribute(GARA.DATA_COMUN);
//PP old		String disableConf = (request.getAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA) != null  && 
//PP old				StatiScheda.CONFERMATO_STRING.equals(request.getAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA)) ) ? "disabled" : "";
		// campi sempre editabili
		String disableConf = "";
		
		String readOnly = !"".equals(disableConf) ? "readonly" : ""; 

		String idTipoScheda = (request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE): "";
		String idModoIndi = (request.getAttribute(PSBD.FIELD_NAME_ID_MODO_INDIZIONE) != null) ? (String)request.getAttribute(PSBD.FIELD_NAME_ID_MODO_INDIZIONE): "";
		String idModoReal = (request.getAttribute(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE ) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE): "";
		
		String idTipoSchedaPar = (request.getParameter(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE): "";
		String idModoIndiPar = (request.getParameter(PSBD.FIELD_NAME_ID_MODO_INDIZIONE) != null) ? (String)request.getParameter(PSBD.FIELD_NAME_ID_MODO_INDIZIONE): "";
		String idModoRealPar = (request.getParameter(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE ) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE): "";
		
		//TICKET ALM #664
		String idStrumentoSvolgimento = (request.getAttribute(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO ) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO): "";
		String idStrumentoSvolgimentoPar = (request.getParameter(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO): "";
		if(!"".equals(idStrumentoSvolgimentoPar)) idStrumentoSvolgimento = idStrumentoSvolgimentoPar;
		//FINE TICKET ALM #664
		
		//TICKET ALM #3832
		String estremaUrgenza = (request.getAttribute(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA ) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA ) : "";
		String estremaUrgenzaPar = (request.getParameter(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA): "";
		if(!"".equals(estremaUrgenzaPar)) estremaUrgenza = estremaUrgenzaPar;
		//FINE TICKET ALM #3832
		
	    //TICKET ALM #3834
		String modIndAllegatoIX = (request.getAttribute(ParametriServlet.FIELD_NAME_ALLEGATO_IX ) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_ALLEGATO_IX ) : "";
		String modIndAllegatoIXPar = (request.getParameter(ParametriServlet.FIELD_NAME_ALLEGATO_IX) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_ALLEGATO_IX): "";
		if(!"".equals(modIndAllegatoIXPar)) modIndAllegatoIX = modIndAllegatoIXPar;
		//FINE TICKET ALM #3834
		
		if("".equals(idTipoSchedaPar)) idTipoSchedaPar = idTipoScheda;
		if("".equals(idModoIndiPar)) idModoIndiPar = idModoIndi;
		if("".equals(idModoRealPar)) idModoRealPar = idModoReal;
		String cigQuadro = (request.getAttribute(ParametriServlet.FIELD_NAME_CIG_ACC_QUADRO) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_CIG_ACC_QUADRO): "";

		String esclusoAVCPass = (request.getAttribute(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS) != null)? (String)request.getAttribute(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS): "";
		String esclusoAVCPassPar = (request.getParameter(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS) != null) ? (String)request.getParameter(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS): "";
		if("".equals(esclusoAVCPassPar)) esclusoAVCPassPar = esclusoAVCPass;

		//INT85
		String showSezINT85Att = (String) request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85);
		String showSezINT85Par = request.getParameter(ParametriServlet.FIELD_NAME_FLAG_LEGGE85);
		if(showSezINT85Par == null || "".equals(showSezINT85Par)) showSezINT85Par = showSezINT85Att;
		boolean showSezINT85 = Costanti.FLAG_VALORE_SI.equals(showSezINT85Par);
		    
		String scelta85 = (request.getAttribute(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85): "";
		String scelta85Par = (request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85) != null)? (String)request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85): "";
		if("".equals(scelta85Par)) scelta85Par = scelta85;
// 		System.out.println("show:" + showSezINT85);
// 		System.out.println("ATT:" + showSezINT85Att);
// 		System.out.println("PAR:" + showSezINT85Par);

		// INT87
		String urgenzaDL133Att = (String)(request.getAttribute(ParametriServlet.FIELD_NAME_URGENZA_DL133));
		String urgenzaDL133Par = request.getParameter(ParametriServlet.FIELD_NAME_URGENZA_DL133);
		if("".equals(urgenzaDL133Par) || urgenzaDL133Par == null) urgenzaDL133Par = urgenzaDL133Att;
		
		String urgenzaDL133 = Costanti.FLAG_VALORE_SI.equals(urgenzaDL133Par) || "on".equals(urgenzaDL133Par)? "checked" : "";
		String action = request.getParameter(ParametriServlet.ACTION);
		if(action == null) action = (String) request.getAttribute(ParametriServlet.ACTION);
		
		boolean modificaDL133 = false;
		if(action != null && action.equals(ParametriServlet.ACTION_MODIFICA_DL133))
		   modificaDL133 = true;
	   String disabledTranneDL133 = modificaDL133 ? "disabled" : "";
	   
	 //Ticket #20055
		boolean checkRettifica= Boolean.valueOf(String.valueOf(request.getAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI)));
		if(checkRettifica){
			
			 disabledTranneDL133="";
		}
		
	//Fine
		
	   // is30350_RFWEBGL01Active
	   String eaggMotivo = (request.getAttribute(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO) != null)? (String)request.getAttribute(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO): "";
		String eaggMotivoPar = (request.getParameter(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO): "";
		if("".equals(eaggMotivoPar)) eaggMotivoPar = eaggMotivo;
		String showSezEAGGAtt = (String) request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_EAGG);
		String showSezEAGG5Par = request.getParameter(ParametriServlet.FIELD_NAME_FLAG_EAGG);
		if(showSezEAGG5Par == null || "".equals(showSezEAGG5Par)) showSezEAGG5Par = showSezEAGGAtt;
		boolean showSezEAGG = Costanti.FLAG_VALORE_SI.equals(showSezEAGG5Par);
		TableBean listaCategorie = (TableBean)request.getAttribute(ParametriServlet.EAGG_CATEGORIE_BEAN); 
		
		//TICKET ALM #659 - 3.04.4
		String flagSAAgente = request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA) != null ? (String) request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA) : "N";
		String flagSAAgentePar = (request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA): "N";
        if("S".equals(flagSAAgentePar)) flagSAAgente = flagSAAgentePar;
		
		String idFDelegate = request.getAttribute(ParametriServlet.FIELD_NAME_ID_F_DELEGATE) != null ? (String) request.getAttribute(ParametriServlet.FIELD_NAME_ID_F_DELEGATE) : "";
		String idFDelefatePar = request.getParameter(ParametriServlet.FIELD_NAME_ID_F_DELEGATE) != null ? (String) request.getParameter(ParametriServlet.FIELD_NAME_ID_F_DELEGATE) : "";
		if(!"".equals(idFDelefatePar)) idFDelegate = idFDelefatePar;
		
		String cfAmmDelegante = request.getAttribute(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE) : "";
		String cfAmmDelegantePar = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE) != null ? (String)request.getParameter(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE) : "";
		if(!"".equals(cfAmmDelegantePar)) cfAmmDelegante = cfAmmDelegantePar;
		
		String denAmmDelegante = request.getAttribute(ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE) : "";
		String denAmmDelegantePar = request.getParameter(ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE) != null ? (String)request.getParameter(ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE) : "";
		if(!"".equals(denAmmDelegantePar)) denAmmDelegante = denAmmDelegantePar;
		
		String checkSoggByUser = request.getAttribute(ParametriServlet.VALUE_CHECK_INIZIATIVE) != null ? (String)request.getAttribute(ParametriServlet.VALUE_CHECK_INIZIATIVE) : "";
	    String chechSoggByUserPar = request.getParameter(ParametriServlet.VALUE_CHECK_INIZIATIVE) != null ? (String)request.getParameter(ParametriServlet.VALUE_CHECK_INIZIATIVE) : "";
	    if(!"".equals(chechSoggByUserPar)) checkSoggByUser = chechSoggByUserPar;
	    
	    String listCatCodLottiStr = (String) request.getAttribute(ParametriServlet.EAGG_CATLOTTO) != null ? (String) request.getAttribute(ParametriServlet.EAGG_CATLOTTO) : "";
        List<String> listCatCodLotti = new ArrayList<String>();
	    if(!"".equals(listCatCodLottiStr)){
	    	String[] arrayCod = listCatCodLottiStr.split("_");
	    	for(int i=0;i<arrayCod.length;i++)
	    		listCatCodLotti.add(arrayCod[i]);
	    }

	%>

<div id="gabbia">

<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>

<% String currentDate = PageHelper.getCurrentDate(); 

MessageBean messBean = (it.avlp.simog.beans.MessageBean) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
it.avlp.simog.beans.AllValidationBeans beanErr = null;
HashMap<String, String> fieldToHighlight = new HashMap<String,String>();
if ( messBean != null ) 
if ( messBean instanceof it.avlp.simog.beans.AllValidationBeans ){
beanErr = (it.avlp.simog.beans.AllValidationBeans) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
fieldToHighlight = beanErr.getFieldToHighlight();
}
%>

<% TableBeanRow currentRow = null; %>
	<%
		String importoGaraEuro = request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO);
		if(importoGaraEuro == null)
			importoGaraEuro = "";  
		if(reqImportoGara != null)		//UN Nel caso in cui "provengo da modifica" sovrascrivo importoGaraEuro
			importoGaraEuro = reqImportoGara;
		//String importoGara = null;
		//String importoGaraCentesimi =  request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_GARA_CENTESIMI);
		//if(importoGaraCentesimi == null)
		//	importoGaraCentesimi="00";
	%>
	
	<div id="bodypage">
		<div class="bodypage-e">
		
		<form name="confermaGara" action="InserisciGara"   method="post">
		<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_CHECK_INIZIATIVE%>" id="idNoCheckSoggAggrByUser" value="<%= checkSoggByUser %>" />
			<input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />		
		<h1><utils:message key="nuovaGara.gestioneGara" /></h1>
		<%@ include file="include/gestisciErrore.inc" %>
					
		<div class="hmenu">
        <% if( request.getAttribute(ParametriServlet.FIELD_NAME_ID_GARA) != null ) { %>
			<ul><li><a title="<utils:message key="lotto.paginaPrecedente" />" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO%>
				?<%=ParametriServlet.SESSION_ID_GARA%>=<%=request.getAttribute(ParametriServlet.FIELD_NAME_ID_GARA) %>
				&<%=ParametriServlet.FROM_GARE %>=<%=Costanti.FLAG_VALORE_SI %>"><utils:message key="lotto.ritorna" /></a></li></ul>
		<% }else{ %>
	    	<ul><li><a title="<utils:message key="lotto.paginaPrecedente" />" href="<%=ParametriServlet.JSP_GESTIONE_SCHEDE%>"><utils:message key="lotto.ritorna" /></a></li></ul>
	    <% } %>
		</div>
		
		<fieldset>
		<legend><utils:message key="nuovaGara.datiGara" /></legend>
				<table width="100%" rpad="10" >
				<tr>
					<th><utils:message key="nuovaGara.data" /></th>
					<% 
					String dataCreazione = "";
					if(!gestioneGara)
						dataCreazione = (String) it.avlp.simog.util.PageHelper.getFormattedDate(currentDate);
					else
						dataCreazione = it.avlp.simog.util.PageHelper.getFormattedDate((String)  request.getAttribute(ParametriServlet.FIELD_NAME_DATA_CREAZIONE_GARA));   
					%>
					<td colspan="2"><%= dataCreazione %></td>
					<% session.setAttribute( ParametriServlet.SESSION_DATA_CREAZIONE_GARA, dataCreazione); %>
				</tr>
				<tr>
					<th>CF UTENTE</th>
					<td colspan="2"><%= user.getLogin() %></td>
				</tr>
				<% if(gestioneGara){ %>
					<tr>
						<th>NUMERO GARA</th>
						<td><strong><big><%= request.getAttribute(ParametriServlet.FIELD_NAME_ID_GARA) %></big></strong></td>
						<td>(da utilizzare in sede di versamento del contributo da parte della SA)</td>
					</tr>
				<% } %>
				<tr>
				     <td colspan="3"><p class="detailHelp">Selezionare la Stazione appaltante di riferimento</p></td>
				</tr>
  				<tr>
				<td colspan="3">
						<div class="scrollLittle">
						<table width="100%">
						<% int rowsCount = 0; 
						   int progressivoIdPerCheckRadiobox = 0; 
						   for ( java.util.Enumeration e = user.getUffici().elements(); e.hasMoreElements(); ) { %>
							<tr>
							<% progressivoIdPerCheckRadiobox++; 
							   StazioneAppaltante currentSA =  (StazioneAppaltante)e.nextElement(); 
							   String prevRequestIdSA = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE); 
							   String checked = ""; 
							   List deleghe = (List)request.getAttribute(ParametriServlet.DELEGHE_CIG); %>
							   
							<td><input <%= disableConf %> <%= disabledTranneDL133 %> onclick="checkDelega(this);" title="<%=deleghe.get(rowsCount) %>" id="<%=progressivoIdPerCheckRadiobox %>" type="radio" <%= currentSA.getIdUfficio().equals(prevRequestIdSA) || currentSA.getIdUfficio().equals(idStazioneAppaltante) ? "checked" : checked %> name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%= currentSA.getIdUfficio()%>" tabindex="<%= ++indiceTab %>"></td><td title="<%=deleghe.get(rowsCount) %>"><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>"><%=currentSA.getDenominazioneAmministrazione()%> - <%= currentSA.getDenominazione()%></label></td>
							</tr>
						 <%  rowsCount ++; } %>
						</table>
						</div>
   				</td></tr>

<%	if(SimogFlags.is30350_RFWEBGL01Active()
       && SimogProperties.getInstance().isEAGGAttivo(PageHelper.getFormattedDBDate(dataCreazione))
      ){ 
   		int i = 0; 
      	TableBeanRow currentCSRow = null;
      	String[] categorieSel = (String[]) request.getAttribute(ParametriServlet.EAGG_CATEGSEL_BEAN);
      	String[] categorieSelPar = (String[]) request.getParameterValues(ParametriServlet.EAGG_CATEGSEL_BEAN);  	
   		if(categorieSelPar == null) categorieSelPar = categorieSel;
		%>
	<%@page import="it.avlp.simog.db.Costanti"%>
			 <tr>
			 <td colspan="3">
				<div style="<%= showSezEAGG ? "display: block;" : "display: none;" %>">
				<fieldset>
				<legend><%= Costanti.EAGG_TITLE %></legend>						
				<table width="100%">
				<col width="40%">
<tr><td colspan="3">
		<table  width="100%">
				<th colspan="2"><%=Costanti.EAGG_LABEL_CATEGORIE %></th>
				<th align="right">Selezionata</th>
		</table>
<div class="elencoCategorie">
		<table  width="100%">
			<% for ( i = 0; i < listaCategorie.getTableSize(); i++ ) { 
					String tableBeanClass = ( i % 2 == 0 ) ? "TableBeanOdd" : "TableBeanEven"; %> 
				<tr class="<%= tableBeanClass %>">
				<% currentCSRow = listaCategorie.getRow(i); 
					String currentIdCat = currentCSRow.getNulledField(EAGG_CATEGORIE.COD_CATEGORIA);
					
					String disableCatLotto = "";
					for(int y=0; y<listCatCodLotti.size() && "".equals(disableCatLotto);y++){
						String catLotto = listCatCodLotti.get(y);
						if(catLotto.equals(currentIdCat))
							disableCatLotto = "style= \"pointer-events: none; opacity: 0.5;\"";	
					}
					
					%>
				<td colspan="2"><label for="categoria[<%= i %>]"><%= currentCSRow.getNulledField(EAGG_CATEGORIE.DESCRIZIONE) %></label></td>
				<td <%= !"".equals(disableCatLotto) ? "title=\"Non e' possibile deselezionare la categoria in quanto appartenente a uno o piu' lotti\"" : "" %> >

<% if(SimogFlags.is30350_UNACATEGActive()){ %>
 				<input <%= disableCatLotto %> <%= disableConf %> <%= disabledTranneDL133 %> class="cig" type="radio" name="<%= ParametriServlet.FIELD_NAME_CATEGORIA %>" value="<%= currentIdCat %>" id="ChCategoria[<%= currentIdCat %>]" <h:checkElement list="<%=categorieSel%>" element="<%=currentIdCat %>"/> ></td>
<%} else { %>
  				<input <%= disableCatLotto %> <%= disableConf %> <%= disabledTranneDL133 %> type="checkbox" class="cig"  name="<%= ParametriServlet.FIELD_NAME_CATEGORIA %>" value="<%= currentIdCat %>" id="ChCategoria[<%= currentIdCat %>]" <h:checkElement list="<%=categorieSel%>" element="<%=currentIdCat %>"/> ></td> 
<% } %>
				</tr>
			<% } %>

		</table>
</div>
</td></tr>					
  <%-- TICKET ALM #4222 - 3.04.4 --%>
<%  if( !SimogProperties.getInstance().isDataCreatedAfterSoggAggr(PageHelper.getFormattedDBDate(dataCreazione))) { %>						
<% if(! "".equals(SimogProperties.getInstance().getLinkEntiagg())) {%>
				<tr><td>&nbsp;</td></tr>
				<tr><td colspan="3"><p style="font-weight: bold;"><a href="<%= SimogProperties.getInstance().getLinkEntiagg() %>" target="_blank">&nbsp;<img title="Cliccare per informazioni estese" src="img/info-32x32.png" /></a><%=Costanti.EAGG_LABEL_INFO %></p></td></tr>
<% } %>
				<tr><td>&nbsp;</td></tr>
				<tr>
				<!-- MEV 34188 - 3.04.8.1 FASE 2 aggiunto l'attributo </*%=SimogFlags.checkHighlightField(fieldToHighlight, "label_EAGG_MOTIVI")%*/> x per marcare il tag dell'eccezione -->
					<th nowrap="nowrap"><label <%=SimogFlags.checkHighlightField(fieldToHighlight, "label_EAGG_MOTIVI")%> for="<%= ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO %>"><%=Costanti.EAGG_LABEL_MOTIVI %></label></th>
					<td>
							<select <%= disableConf %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO %>" id="sel_FIELD_NAME_EAGG_COD_MOTIVO">
							<option></option>
							<h:options name="<%= ParametriServlet.EAGG_MOTIVI_BEAN %>" scope="request" limit="200" value="<%= eaggMotivoPar %>" valLocation="tag"/>
						</select>
					</td>
				 </tr>
				</table>
				</fieldset>
				</div>
				</td></tr>
				<tr><td>&nbsp;</td></tr>
		<% } %>		<% } %>	
   				
<%	if(SimogFlags.isINT85_RFWEBGL01Active() 
      && SimogProperties.getInstance().isINT85Attivo()){ %>   				
			 <tr>
			 <td colspan="3">
				<div style="<%= showSezINT85 ? "display: block;" : "display: none;" %>">
				<fieldset>
<%-- 				<fieldset <%= showSezINT85 ? "" : "hidden" %>> --%>
				<legend style="color: red;"><%= Costanti.LEGGE89_TITLE %></legend>						
				<table width="100%">
				<col width="2%">
				<tr>
					<td colspan="2"><p style="font-weight: bold;">La stazione appaltante &egrave; un comune non capoluogo di provincia.</p>
					<br><p>Per proseguire con la creazione della gara &egrave; necessario dichiarare (con valore di autocertificazione ai fini di eventuali successive verifiche) la motivazione per cui si &egrave; abilitati ad acquisire il CIG.</p>
					</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
					<td>
					<input <%= disableConf %> <%= disabledTranneDL133 %> id="legge89_1" type="radio"  
					name="<%= ParametriServlet.FIELD_NAME_SCELTA_LEGGE85 %>" 
					value="<%= Costanti.LEGGE89_1 %>" 
					tabindex="<%= ++indiceTab %>" 
					<%= Costanti.LEGGE89_1.equals(scelta85Par) ? "checked" : "" %>  />
					</td>
					<td><%= Costanti.LEGGE89_1_DICH %></td>
					</tr>
					<tr>
					<td>
					<input <%= disableConf %> <%= disabledTranneDL133 %> id="legge89_2" type="radio"  
					name="<%= ParametriServlet.FIELD_NAME_SCELTA_LEGGE85 %>" 
					value="<%= Costanti.LEGGE89_2 %>" 
					tabindex="<%= ++indiceTab %>" 
					<%= Costanti.LEGGE89_2.equals(scelta85Par) ? "checked" : "" %> />
					</td>
					<td><%= Costanti.LEGGE89_2_DICH %></td>
					</tr>
				</table>
				<input type="hidden"  value="<%=request.getAttribute(ParametriServlet.FIELD_NAME_TIPOSA_BDNCP) %>" name="<%=ParametriServlet.FIELD_NAME_TIPOSA_BDNCP %>"/>
				</fieldset>
				</div>
				</td></tr>
				<tr><td>&nbsp;</td></tr>
<% } %>				
				<tr>
					<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_OggettoGara")? "style=\"color: red;\"" : "" %> >Oggetto della gara*</label></th><%-- #TICKET ALM #3608 --%>
					<td colspan="2">
						<input <%= readOnly %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" 
						type="text" value='<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_OGGETTO_GARA %>" 
						defaultValue="<%= oggettoGara.replaceAll("\'", "&#39;") %>"/>' name="<%= ParametriServlet.FIELD_NAME_OGGETTO_GARA %>" 
						id="OggettoGara" maxlength="1024" size=100% onchange="javascipt:replaceWordChars(this)" />
					</td>
				</tr>
				
				<% if (user.isAmministratore()){%>
				<tr>
				<th><label>Importo complessivo della gara a base d'asta o presunto</label></th>
				<% boolean importoGaraAUX = "".equals(importoGaraEuro) ? false : Double.parseDouble(Costanti.IMPORTO_FUORI_SCALA_STRING) == Double.parseDouble(PageHelper.formattaImporto(importoGaraEuro)); %>
				<% importoGaraEuro = importoGaraAUX ? "" : importoGaraEuro; %>
				<% boolean importoGaraValido = ! importoGaraAUX;
				String checkedNoImportoGara = "";
				String checkedSIInserisciGara = "";
				String disabledSIImportoGara = "";
				%>
				 <%if(importoGaraAUX || ParametriServlet.INSERISCI_IMPORTO_NO.equals(request.getParameter(ParametriServlet.FIELD_INSERISCI_IMPORTO))
						 			 || ParametriServlet.INSERISCI_IMPORTO_NO.equals(inserisciImporto) ){
					 checkedNoImportoGara = "checked";
					 disabledSIImportoGara = "disabled";
				 }
				 else {
					 checkedSIInserisciGara = "checked";
					 disabledSIImportoGara = "";
				 }

				 if(importoGaraEuro != null && !"".equals(importoGaraEuro))
					 importoGaraEuro = PageHelper.formattaImporto(new BigDecimal(importoGaraEuro));
				%>
						
				<td><table cellspacing="0" cellpadding="0">
				<tr>
				<td>
					<input <%= disableConf %> <%= disabledTranneDL133 %> id="importo_si" type="radio"  
							onclick="checkChangeGara(this, getElementById('importo_no'), '<%= dataComun %>', <%= user.isAmministratore() %>);" 
							name="<%= ParametriServlet.FIELD_INSERISCI_IMPORTO %>" tabindex="<%= ++indiceTab%>"
								value="<%= ParametriServlet.INSERISCI_IMPORTO_SI %>" <%= checkedSIInserisciGara %>/>&nbsp;
				</td>
				<td>
					<input <%= readOnly %> <%= disabledTranneDL133 %> onblur="validateAmount(this)" id="euro" type="text" style="text-align:right;" 
							onchange="checkChangeGara(this, this, '<%= dataComun %>', <%= user.isAmministratore() %>);" 
							name="<%= ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO %>" tabindex="<%= ++indiceTab%>"
					  		value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO %>" defaultValue="<%= importoGaraEuro %>" />" 
					  		<%= disabledSIImportoGara %> />
					</td>
					</tr></table>
				</td>
				<td>
					 <font color="grey">Indicare l'importo con riferimento alla totalita' dei lotti componenti la gara</font>
				</td>		
				</tr>
				<tr>
					<th><label>Importo complessivo della gara a base d'asta o presunto non disponibile</label></th>
					<td>
					   <input <%= disableConf %> <%= disabledTranneDL133 %> id="importo_no" type="radio" 
						name="<%= ParametriServlet.FIELD_INSERISCI_IMPORTO %>" tabindex="<%= ++indiceTab%>"
						value="<%= ParametriServlet.INSERISCI_IMPORTO_NO %>" 
						<%= checkedNoImportoGara %> onclick="checkChangeGara(this, getElementById('importo_si'), '<%= dataComun %>', <%= user.isAmministratore() %>);" />
					</td>		
					<td>
					<div  id="alertInd" style="color: red; display: none;">Si ricorda che la mancata indicazione dell'importo complessivo di gara comportera' l'addebito nel MAV del contributo calcolato nella misura massima prevista dalla deliberazione in vigore<br><a href="http://www.anticorruzione.it/portal/public/classic/AttivitaAutorita/AttiDellAutorita/_Atto?ca=4458" target="blank">Consulta la delibera</a></div>
					</td>	
				</tr>	
				<% } %>
				
				<tr>
					<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_NumeroLotti")? "style=\"color: red;\"" : "" %>>Numero totale Lotti*</label></th>
					<td colspan="2">
						<input <%= readOnly %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" type="text" value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NUMERO_LOTTI %>" defaultValue="<%= numeroLotti %>"/>" name="<%= ParametriServlet.FIELD_NAME_NUMERO_LOTTI %>" maxlength="10" onblur="validateNumber(this)">
					</td>
				</tr>
				<tr>
					<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_SettoreGara")? "style=\"color: red;\"" : "" %> for="<%= ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>">Settore attivita' della SA*</label></th>

					<td><select onchange="javascript: checkSettoreGara();" <%= disableConf %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" name="<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>" id="sel_FLAG_ENTE_SPECIALE">
				  			<option></option>
				  			<h:options name="listaTipiEnte" scope="request" value="<%=idTipoSchedaPar %>" valLocation="tag"/>
				  		</select>
					</td>
				</tr>
			 	<tr>
				<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_ModalitaIndizioneSettSpec")? "style=\"color: red;\"" : "" %> for="<%= PSBD.FIELD_NAME_ID_MODO_INDIZIONE %>">Modalit� di indizione (settori speciali)</label></th>
				<td>
					<select <%= disableConf %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" name="<%= PSBD.FIELD_NAME_ID_MODO_INDIZIONE %>" id="sel_MODO_INDIZIONE_GARA">
						<option></option>
						<h:options name="<%= ParametriServlet.MODO_INDIZIONE_GARA %>" scope="request" value="<%= idModoIndiPar %>" valLocation="tag"/>
					</select>
				</td>
			 </tr>
				
			 <!-- TICKET ALM - 3.04.2 NG -->
	  <%  //Mostra il nuovo campo solo se la gara e' creata dopo l'attivazione della 3.04.2
		if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazione))) { %>	
		
			<tr>
				<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_ModalitaIndizioneAllegatoIX")? "style=\"color: red;\"" : "" %> for="<%= ParametriServlet.FIELD_NAME_ALLEGATO_IX %>">Modalita' di indizione servizi di cui all'allegato IX</label></th>
				<td>
						<select <%= disableConf %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_ALLEGATO_IX %>" id="sel_ALLEGATO_IX">
						<option></option>
						<h:options name="<%= ParametriServlet.ALLEGATO_IX %>" scope="request" value="<%= modIndAllegatoIX %>" valLocation="tag"/>
					</select>
				</td>
			 </tr>	
		<% } %>
			<!-- FINE TICKET ALM - 3.04.2 NG -->
				
			 	<tr>
				<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_ModalitaRealizzazione")? "style=\"color: red;\"" : "" %> for="<%= ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE %>">Modalita' di realizzazione*</label></th>
				<td>
						<select <%= disableConf %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE %>" id="sel_MODO_REALIZZAZIONE" >
						<option></option>
						<h:options name="<%= ParametriServlet.MODO_REALIZZAZIONE %>" scope="request" value="<%= idModoRealPar %>" valLocation="tag"/>
					</select>
				</td>
			 </tr>
			 
			 <!-- TICKET ALM - 3.04.3 #659 -->
			 <!-- Ticket 20057 -->
			 <%	if(SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazione)) && !SimogProperties.getInstance().isDataCreatedAfter3046(PageHelper.getFormattedDBDate(dataCreazione))) { %>	
				<tr>
					<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_DurataConvAccordoQuadro")? "style=\"color: red;\"" : "" %> >Durata della convenzione o accordo quadro in giorni</label></th>
					<td colspan="2">
						<input <%= readOnly %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" type="text" value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DURATA_GIORNI %>" defaultValue="<%= "0".equals(durataGiorni) ? "" : durataGiorni %>"/>" name="<%= ParametriServlet.FIELD_NAME_DURATA_GIORNI %>" maxlength="10" onblur="validateNumber(this)">
					</td>
				</tr>		
				<% } %>	
			<!-- FINE TICKET ALM - 3.04.3 #659 -->

			 
			 <!-- TICKET ALM - 3.04.2 NG -->
			 <%  //Mostra il campo solo se la gara si sta creando una nuova gara (o se la gara e' creata dopo la 3.04.2)
		if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazione))) { %>	
				<tr>
					<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_StrumentiSvolgProc")? "style=\"color: red;\"" : "" %> for="<%= ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO %>">Strumenti per lo svolgimento delle procedure*</label></th>
				    <td>
							<select <%= disableConf %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO %>" id="sel_STRUMENTO_SVOLGIMENTO">
							<option></option>
							<h:options name="<%= ParametriServlet.STRUMENTO_SVOLGIMENTO %>" scope="request" value="<%= idStrumentoSvolgimento%>" valLocation="tag"/>
						</select>
					</td>
				 </tr>	
		<% } %>
			<!-- FINE TICKET ALM - 3.04.2 NG  -->
			 
				<tr>
					<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_CIG_AccQuadro")? "style=\"color: red;\"" : "" %> >CIG relativo all'accordo quadro/convenzione cui si aderisce</label></th>
					<td colspan="2">
						<input <%= readOnly %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" type="text" value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_CIG_ACC_QUADRO %>" defaultValue="<%= cigQuadro %>"/>" name="<%= ParametriServlet.FIELD_NAME_CIG_ACC_QUADRO %>" maxlength="10">
					</td>
				</tr>

				<% if( SimogFlags.is3031_ESCL_AVCPASS() ){ %>
				<tr>
				<!-- 3.04.7.1 cambiata label -->
					<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_GaraEsclusaAVCPass")? "style=\"color: red;\"" : "" %> for="<%=ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS %>">Gara esclusa dall'obbligo dell'uso del FVOE ai fini della verifica dei requisiti*</label></th>
					<td><select <%=disableConf%> <%= disabledTranneDL133 %> tabindex="<%=++indiceTab%>" name="<%=ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS %>" id="sel_ESCLUSO_AVCPASS">
		  				<option></option>
		  				<option value="N" <%= "N".equals(esclusoAVCPassPar) ? "selected" : "" %>>NO</option>
		  				<option value="S" <%= "S".equals(esclusoAVCPassPar) ? "selected" : "" %>>SI</option>
					</select></td>
				</tr>
				<% } %>
<%	if(SimogFlags.isINT87_RFSIMOGWEB01Active()
      // && (SimogProperties.getInstance().isINT87Attivo(PageHelper.getFormattedDBDate(dataCreazione)) || gestioneGara)
      ){ %>   				
			 <tr>
					<th><label >Esecuzione di affidamenti in estrema urgenza/somma urgenza</label></th>
					<td colspan="2">
					<input <%= readOnly %> type="checkbox" id ="<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>" name ="<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>" <%= urgenzaDL133 %> />
					<input type="hidden" id="hidden<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>" name="hidden<%= ParametriServlet.FIELD_NAME_URGENZA_DL133 %>"/>
					</td>
			</tr>
<% } %>		
		
			<!-- TICKET ALM - 3.04.2 NG -->
			<% //Mostra il campo solo se la gara e' nuova o se creata dopo l'attivazione della 3.04.2
		if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazione))) { %>	
		
			<tr>
				<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_MotivoUrgenza")? "style=\"color: red;\"" : "" %> for="<%= ParametriServlet.FIELD_NAME_ESTREMA_URGENZA %>">Motivo urgenza</label></th>
				<td colspan="2">
						<select <%= readOnly %> tabindex="<%= ++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_ESTREMA_URGENZA %>" id="sel_ESTREMA_URGENZA">
						<option></option>
						<h:options name="<%= ParametriServlet.ESTREMA_URGENZA %>" scope="request" value="<%= estremaUrgenza %>" valLocation="tag"/>
					</select>
				</td>
			 </tr>	
		<% } %>
			<!-- FINE TICKET ALM - 3.04.2 NG -->
		
		
				   <%-- TICKET ALM #659 - 3.04.4 --%>
				 <%  if( SimogProperties.getInstance().isDataCreatedAfter3044(PageHelper.getFormattedDBDate(dataCreazione))) { %>	
		                <tr>
						<th><label for="<%= ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA %>" >La stazione appaltante agisce per conto di altro soggetto singolo?</label></th>
					  		<td  colspan="2" > 
			   					 <select <%=disableConf%> <%= disabledTranneDL133 %> tabindex="<%=++indiceTab%>" name="<%=ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA %>" id="sel_FLAG_SA_AGENTE">
					  				<option value="N" <%= "N".equals(flagSAAgente) ? "selected" : "" %>>NO</option>
					  				<option value="S" <%= "S".equals(flagSAAgente) ? "selected" : "" %>>SI</option>
								</select>
		   					</td>		   		
				  	   </tr>
				  	   
				  	   	<tr>
							<th><label <%= !fieldToHighlight.isEmpty() && fieldToHighlight.containsKey("label_FunzioniDelegate")? "style=\"color: red;\"" : "" %> for="<%= ParametriServlet.FIELD_NAME_ID_F_DELEGATE %>">Funzioni delegate</label></th>
							<td colspan="2">
									<select <%= disableConf %> <%= disabledTranneDL133 %> tabindex="<%= ++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_ID_F_DELEGATE %>" id="sel_ID_F_DELEGATE">
									<option></option>
									<h:options name="<%= ParametriServlet.ID_F_DELEGATE %>" scope="request" value="<%= idFDelegate %>" valLocation="tag"/>
								</select>
							</td>
						 </tr>
				  	   
		                <tr>
		   					<th><label>Codice fiscale soggetto per conto del quale agisce la S.A. (in caso di soggetto singolo)</label></th>
		   					<td><input <%= readOnly %> <%= disabledTranneDL133 %> id="cfSoggettoA"   name="<%= ParametriServlet.FIELD_NAME_CF_AMM_AGENTE%>"  tabindex="<%=++indiceTab%>" 
								type="text" value="<c:out value='<%= cfAmmDelegante %>'/>" maxlength="11"/></td>
		   				</tr>
		
					   	<tr>
		   					<th><label>Denominazione dell'Amministrazione per la quale agisce la S.A.</label></th>
		   					<td><input  id="denSoggettoA"  name="<%= ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE%>"  tabindex="<%=++indiceTab%>" 
								type="text" value="<c:out value='<%= denAmmDelegante %>'/>" disabled /></td>
		   				</tr>
	   				 <% } %>
				   <%-- FINE TICKET ALM #659 - 3.04.4 --%>
				</table>
		</fieldset>
				
		<div align="left">
			<br/>
			<% if( gestioneGara ) { 
				String azione = modificaDL133 ? ParametriServlet.ACTION_MODIFICA_DL133 : ParametriServlet.ACTION_SALVA;
				//Ticket #20055
				if(checkRettifica){
					
					azione = ParametriServlet.ACTION_SALVA;
				}
			%>	
				<input <%= disableConf %> type="button"  value="Salva" onclick="doActionModifica('<%=azione %>',<%= user.isAmministratore() %>,<%= SimogProperties.getInstance().isDataCreatedAfterSoggAggr(PageHelper.getFormattedDBDate(dataCreazione)) %>);"/>
				<%-- [VL - SPOSTATO ALLA PAGINA PRECEDENTE LA CONFERMA DELLA GARA] <input <%= disableConf %> type="button"  value="Conferma" onclick="doAction('<%=ParametriServlet.ACTION_CONFERMA %>')"/> --%>
				<input type="hidden"  value="<%=request.getAttribute(ParametriServlet.FIELD_NAME_ID_GARA) %>" name="<%=ParametriServlet.FIELD_NAME_ID_GARA %>"/>
				<input type="hidden"  value="" name="toDo" id="toDo"/>
			<% }else{ %>
				<input type="button"  value="Inserisci Gara" onclick="doActionInserisci(document.forms[0].<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>.length,<%= user.isAmministratore() %>,<%= SimogProperties.getInstance().isDataCreatedAfterSoggAggr(PageHelper.getFormattedDBDate(dataCreazione)) %>); "/>
				<input type="hidden"  value="<%=ParametriServlet.ACTION_AGGIUNGI_GARA %>" name="toDo" id="toDo"/>
			<% } %>
		</div>

			<input type="hidden"  value="<%=request.getAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA) %>" name="<%=ParametriServlet.FIELD_NAME_ID_STATO_GARA %>"/>
			<input type="hidden"  value="<%=request.getAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA) %>" name="<%=ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA %>"/>
			<% if (!user.isAmministratore()){%>
				<input type="hidden"  value="<%=request.getAttribute(ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO) %>" name="<%=ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO %>"/>
			<% } %>
			<!-- TB: ticket popup modali. Import css e js -->
            <link rel="stylesheet" href="theme/jquery-ui-popup.css" />
			<div id="dialogSoggAggr"></div>
			
		</form>	
				
		</div>
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
<script type="text/javascript">
//<!--
showAlertInd();
//-->
</script>
</body>
<%@page import="it.avlp.simog.db.Costanti"%>

</html>
<% } catch (Exception e){e.printStackTrace();}%>