<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

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
			alert("<%= MessageHelper.getMessage(request, "js.error.codiceFiscaleChars") %>")
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
		alert("<%= MessageHelper.getMessage(request, "js.error.codiceFiscaleInvalid") %>")
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
			alert("<%= MessageHelper.getMessage(request, "js.error.enterPassword") %>");
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
			<h4><utils:message key="login.accessoSimog" /> <font color="red"><big><%=SimogProperties.getInstance().getAmbiente() %></big></font></h4>
			<%@ include file="include/gestisciErrore.inc" %>
			
<div style="background-color:yellow"><br><hr>
			<table width="86%" border="1" cellpadding="5"><tr>
			<td><img src="img/simogWarning_little.jpg" width="60px" height="55px" align="left"></td>
			<td align="left" valign="top"><p style="color:rgb(180,0,28);">
				
				<strong><utils:message key="login.avvisoSospensioneServizio" /></strong>
			</p></td></tr> 
			</table>
<hr><br></div>
			
			<p><utils:message key="login.identificarsi" /></p>
			<div> <!-- MEV 44995 3.04.11 -->
				<strong>
					<font color="#FF0000">
						<utils:message key="login.avvisoDeliberaPre" /> <a href="https://www.anticorruzione.it/-/delibera-n.-582-del-13-dicembre-2023-adozione-comunicato-relativo-avvio-processo-digitalizzazione" target="_blank"><utils:message key="login.avvisoDeliberaLink" /></a>
					</font>
				</strong>
			</div>
			<p style="margin-bottom:5px;"><strong><utils:message key="login.credenziali" /></strong></p>
			<table width="86%" border="1" cellpadding="5"><tr>
			<td><img src="img/simogWarning_little.jpg" width="60px" height="55px" align="left" alt="img simog warning"></td>
			<td align="left" valign="top"><p style="color:rgb(180,0,28);">
				<strong><utils:message key="login.avvisoProfiliAccesso" /><br></strong>
			</p></td></tr>
			</table>
			<form name="frmLogin" method="post" action="javascript:checkCampi();">
			<fieldset id="fldlogin">
			<legend><utils:message key="login.datiAutenticazione" /></legend>
			<table>
			<tr> 
				<td>
				<label for="<%= ParametriServlet.FIELD_NAME_LOGIN %>"><strong><utils:message key="login.codiceFiscale" /></strong></label>
				</td>
				<td>
				<input name="<%= ParametriServlet.FIELD_NAME_LOGIN %>" type="text">
				</td>
			</tr>
			<tr>
				<td>
				<label for="<%= ParametriServlet.FIELD_NAME_PASS %>"><strong><utils:message key="login.password" /></strong></label>
				</td>
				<td>
				<input name="<%= ParametriServlet.FIELD_NAME_PASS %>" type="password" maxlength="24">
				</td>			
			</tr>
			<tr>
			<td colspan="2">&nbsp;</td>
			</tr>
			</table>
			</fieldset>
			<input TYPE="submit" value="<utils:message key="button.accedi" plain="true" />">
			</form>
		</div>		
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
