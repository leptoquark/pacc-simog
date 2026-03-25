<%@page import="it.avlp.simog.db.advanced.TableBean"%>
<%@page import="it.avlp.simog.db.advanced.TableBeanRow"%>
<%@page import="it.avlp.simog.db.generated.RICHIESTA_ANNULLAMENTO"%>	
<%@page import="it.avlp.simog.beans.RichiestaAnnullamento"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<br/>
<script type="text/javascript">
<!--
function invia(vid, vdt) {
var url = document.forms[0].action + "?<%=PSBD.ACTION_TYPE%>=<%=PSBD.ACTION_HST_SCHEDA%>&toDo=<%=PSBD.ACTION_HST_SCHEDA%>&vid=" + vid + "&vdt=" + vdt;
window.open(url,"_blank","menubar=no,toolbar=no,scrollbars=yes,location=no");
}
//-->
</script>
<%	TableBean tabVarAnag = (TableBean)request.getAttribute(PSBD.TAB_VARANAG);
TableBeanRow curRow = null;	
if(tabVarAnag!=null && tabVarAnag.getRowsCount() > 0) {%>
<div class="scrollInside">		
<div class="gara">
<div id="VAPanelHead">
<label style="color: black; letter-spacing: 0.2em;" onclick="showElem('VAPanelBody')">
<img id="imgVAPanelBody" src="img/plus.gif"/> VARIAZIONI IN CORSO D'OPERA EFFETTUATE</label>
</div>
<div id="VAPanelBody">
<br/>
<table class="TableBean">
<tr>
<td class="garaTh"><b>Effettuata da</b></td>
<td class="garaTh"><b>In data</b></td>
<td class="garaTh"><b>Motivazione</b></td>
<td> &nbsp;</td>
</tr>
<%
for (int rowIndex = 0; rowIndex < tabVarAnag.getTableSize(); rowIndex++ ) { 
curRow = tabVarAnag.getRow(rowIndex); 
String vdata_inizio = it.avlp.simog.util.PageHelper.getFormattedDateTime(curRow.getNulledField(RICHIESTA_ANNULLAMENTO.DATA_INIZIO)); 
String vrichiedente = curRow.getNulledField(RICHIESTA_ANNULLAMENTO.RICHIEDENTE); 
String vid = curRow.getNulledField(RICHIESTA_ANNULLAMENTO.ID_RECORD); 
String vdt = curRow.getNulledField(RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD); 
String vmotivoEsito = PageHelper.formattaTesto(curRow.getNulledField(RICHIESTA_ANNULLAMENTO.MOTIVO_ESITO));
%>
<tr>
<td valign="top" nowrap class="garaTd"><%= vrichiedente %> </td>
<td valign="top" nowrap class="garaTd"><%= vdata_inizio %> </td>
<td valign="top" class="garaTd"><%= vmotivoEsito %> </td>		
<td valign="top" class="garaTd">
<input type="button" value="VISUALIZZA SCHEDA" onclick="javascript:invia('<%=vid %>', '<%=vdt %>');" />
</td>		
</tr>
<% } %>
</table>
</div>
</div>
</div>
<br/>
<% } %>