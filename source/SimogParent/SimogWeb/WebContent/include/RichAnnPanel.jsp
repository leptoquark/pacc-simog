<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@page import="it.avlp.simog.db.advanced.TableBean"%>
<%@page import="it.avlp.simog.db.advanced.TableBeanRow"%>
<%@page import="it.avlp.simog.db.generated.RICHIESTA_ANNULLAMENTO"%>	
<%@page import="it.avlp.simog.beans.RichiestaAnnullamento"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<br/>

<!-- PANNELLO CON I DETTAGLI DELLE RICHIESTE DI ANNULLAMENTO RELATIVI ALLA SCHEDA -->

<%	TableBean tabRichAnn = (TableBean)request.getAttribute(PSBD.TAB_RICHANN);
TableBeanRow currentRow = null;	
if(tabRichAnn!=null && tabRichAnn.getRowsCount() > 0) {%>
<div class="scrollInside">		
	<div class="gara">
    <div id="PanelHead">
	<label style="color: black; letter-spacing: 0.2em;" onclick="showElem('PanelBody')">
	<img id="imgPanelBody" src="img/plus.gif"/> DETTAGLI RICHIESTE MODIFICA / CANCELLAZIONE</label>
	</div>
	<div id="PanelBody">
		<br/>
		<table class="TableBean">
		<tr>
			<td class="garaTh"><b>Tipo</b></td>
			<td class="garaTh"><b>Richiedente</b></td>
			<td class="garaTh"><b>Data Richiesta</b></td>
			<td class="garaTh"><b>Motivazione Richiesta</b></td>
			<td class="garaTh"><b>Data Esito</b></td>
			<td class="garaTh"><b>Esito</b></td>
<!--					<td class="garaTh"><b>Decisore</b></td>-->
			<td class="garaTh"><b>Motivazione Esito</b></td>
		</tr>
<%
for (int rowIndex = 0; rowIndex < tabRichAnn.getTableSize(); rowIndex++ ) { 
currentRow = tabRichAnn.getRow(rowIndex); 
String data_inizio = it.avlp.simog.util.PageHelper.getFormattedDateTime(currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.DATA_INIZIO)); 
String richiedente = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.RICHIEDENTE); 
String motivoRichiesta = PageHelper.formattaTesto(currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA));  
String esito = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.ESITO); 
String decisore = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.DECISORE);
String motivoEsito = PageHelper.formattaTesto(currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.MOTIVO_ESITO));
String dataFine = it.avlp.simog.util.PageHelper.getFormattedDateTime(currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.DATA_FINE));
String idMotivoAnn = (SimogFlags.is30230_RFWEBSC03Active() ? currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.ID_MOTIVO_RICH) : "");

String tipo = currentRow.getNulledField(RICHIESTA_ANNULLAMENTO.CANCELLAZIONE); 
tipo = "".equals(tipo) ? "Modifica" : tipo.equals(Costanti.FLAG_VALORE_SI) ? "Canc.Completa" : "Cancellazione";
%>
		<tr>
			<td valign="top" nowrap class="garaTd"><%= tipo %> </td>
			<td valign="top" nowrap class="garaTd"><%= richiedente %> </td>
			<td valign="top" nowrap class="garaTd"><%= data_inizio %> </td>
<%
	String desc = "";
	if (SimogFlags.is30230_RFWEBSC03Active()){
	   if("1".equals(idMotivoAnn))
	   	desc = "Errore materiale";
	   else if("2".equals(idMotivoAnn))
	      desc = "Duplicazione della scheda";
  	   else if("3".equals(idMotivoAnn))
  	      desc = "Scheda non dovuta";
  	   else if("4".equals(idMotivoAnn))
 	      desc = "Cancellazione scheda a ritroso";
  	   else if("5".equals(idMotivoAnn))
	      desc = "Altro";

	   if("".equals(idMotivoAnn))
	   	desc = motivoRichiesta;
	   else if(!"".equals(motivoRichiesta))
			desc = desc + "&nbsp;(" + motivoRichiesta + ")";
	}
	else {
		desc = motivoRichiesta;
	}
%>			
			<td valign="top" 		class="garaTd"><%= desc %> </td>
			<td valign="top" nowrap class="garaTd"><%= dataFine %> </td>
			<td valign="top" nowrap class="garaTd"><%= !"".equals(esito) ? (RichiestaAnnullamento.RICHIESTA_ACCETTATA.equals(esito) ? "Accettata" : "Rifiutata") : "In corso" %> </td>
<!--					<td valign="top" nowrap class="garaTd"><%= decisore %> </td>-->
			<td	valign="top" class="garaTd"><%= motivoEsito %> </td>		
		</tr>
<% } %>
		</table>
		</div>
	</div>
</div>
<br/>
<% } %>