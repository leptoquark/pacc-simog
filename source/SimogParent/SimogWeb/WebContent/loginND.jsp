<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="it.avlp.simog.util.MessageHelper"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%
	String msgPassword = MessageHelper.getMessage(request, "avviso.digitarePassword");
	String msgPopup = MessageHelper.getMessage(request, "avviso.popupBloccato");
	String msgErroreCF = MessageHelper.getMessage(request, "dettaglio.erroreCodiceFiscale");
%>

<script type="text/javascript">
<!--
function f_setfocus( aForm )
{
	if( aForm.elements[0]!=null) {
		var i;
		var max = aForm.length;
		for( i = 0; i < max; i++ ) {
		
			if( aForm.elements[ i ].type == "text" &&
				!aForm.elements[ i ].disabled &&
				!aForm.elements[ i ].readOnly ) {
				aForm.elements[ i ].focus();
				break;
			}
		}
	}
}

function controllaCF(campo) {
	cf=campo.value.toUpperCase()
	var validi, i, s, set1, set2, setpari, setdisp
	validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
	for( i = 0; i < 16; i++ ) {
		if( validi.indexOf( cf.charAt(i) ) == -1 ) {
			alert("<%= msgErroreCF %>")
			return false
		}
	}
	/*set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ"
	setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX"
	s = 0;
	for( i = 1; i <= 13; i += 2 )
		s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	for( i = 0; i <= 14; i += 2 )
		s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) ) {
		alert("ERRORE: codice fiscale formalmente errato")
		return false
	}*/
	return true
}

function checkCampi() {

	campoCF = document.forms[0].elements["<%= ParametriServlet.FIELD_NAME_LOGIN %>"] 
	campoPW = document.forms[0].elements["<%= ParametriServlet.FIELD_NAME_PASS %>"]
	
	if (!controllaCF(campoCF)){
		campoCF.focus()
	} else {
		if (campoPW.value == "" || campoPW.value==null){
			alert("<%= msgPassword %>");
			campoPW.focus()
		} else {
			document.forms[0].action="checkAuthentication"
			document.forms[0].submit()
			}
		}
}

//-->
</script>
</head>
<body onload="f_setfocus(document.forms[0])">
<div id="gabbia">  
<div id="header">
</div>
	<div id="bodypage">	
		<div class="bodypage-e">
			<h4><utils:message key="login.title" /> <font color="red"><big><%=SimogProperties.getInstance().getAmbiente()%></big></font></h4>
			<br>
			<%@ include file="include/gestisciErrore.inc" %>
			<table width="86%" border="1" cellpadding="5"><tr>
			<td><img src="img/simogWarning_little.jpg" width="60px" height="55px" align="left" alt="img simog warning"></td>
			<td align="left" valign="top"><p style="color:rgb(180,0,28);">
			<br>
			<br>
			<br>
			<br>
			<br>
				<big><strong>Avviso: Il servizio � temporaneamente sospeso</strong></big>
			</p></td></tr>
			</table>
		</div>		
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
<SCRIPT type="text/javascript">
<!--
var popupblock = false;
var test = window.open('','','top=9000,left=9000,width=1,height=1');
if (!test) popupblock = true; else test.close();
if (popupblock) alert("<%= msgPopup %>");
//-->
</script>
</body>
</html>
