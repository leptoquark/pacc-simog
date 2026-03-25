
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI"%><script type="text/javascript">
<!--
function disable(val) {

	//var cmbPaesi = document.getElementById("<%=SOGGETTI_PARTECIPANTI.ID_STATO %>");
	//var txtPaesi = document.getElementById("textPaese");
	var divText = document.getElementById("divText");
	var divCombo = document.getElementById("divCombo");
	
	if (val == 'N') {
		//cmbPaesi.disabled = true;
		//cmbPaesi.value = "";
		showElement("divText");
		hideElement("divCombo");
	}
	else {
		showElement("divCombo");
		hideElement("divText");
		//cmbPaesi.disabled = false;
    }
}
//-->
</script>
<%
  String flag = (String)request.getAttribute(ParametriServlet.FLAG_ESTERO);
  if(flag == null || "".equals(flag)){flag = Costanti.FLAG_VALORE_NO;}
  request.setAttribute("flag", flag);
  String disabled1 = (String)request.getAttribute("disabled");
  if(disabled1 == null){disabled1 ="";}
%>
<tr>
	<th><label>Operatore Economico Estero</label></th>
<td>
<input onchange="disable('S')" 
		id="<%= ParametriServlet.FLAG_ESTERO%>" 
		type="radio" name="<%= ParametriServlet.FLAG_ESTERO%>" 
		value="<%=Costanti.FLAG_VALORE_SI%>" 
		<%= flag.equals( Costanti.FLAG_VALORE_SI) ? "checked" : ""%>  
		<%=disabled1 %>/>SI 
<input onchange="disable('N')" 
		id="<%= ParametriServlet.FLAG_ESTERO %>" 
		type="radio" name="<%= ParametriServlet.FLAG_ESTERO%>" 
		value="<%=Costanti.FLAG_VALORE_NO%>" 
		<%= flag.equals( Costanti.FLAG_VALORE_NO) ? "checked" : ""%> 
		<%=disabled1 %>/>NO
</td>	
</tr>
