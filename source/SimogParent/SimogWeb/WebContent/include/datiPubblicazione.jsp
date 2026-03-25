	<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE%>" value='<c:out value="${pubblicazione.idPubblicazione}"/>'>
	<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB%>" value='<c:out value="${pubblicazione.dataInizioPubblicazione}"/>'>
	<c:set var="readonlyPub" value="${readonly or pubbNonModificabileDatiComuni}" />
	<c:set var="readonlyPubStr" value="${readonlyPub ? 'readonly' : ''} " />
	<tr>
	     <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataGUCE") %>>Gazzetta Ufficiale Comunità Europea - GUCE</label></td>
 			<td>
				<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')" 
				    ${readonlyPubStr}
					type="text" id="inputGazzettaCE" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>" 
					onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuce}'/>">
				<%-- vecchio controllo <c:if test="${hide == false}"> --%>
				<c:if test="${readonlyPub ne true}">
					<img src="calendar/img.gif" id="calendarGazzettaCE" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					<script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaCE",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaCE",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					</script>
				</c:if>
			</td>
	   	</tr>
	   	<tr>
	   	
	     <td><label >Gazzetta Ufficiale o Bollettino Regionale</label></td>
 			<td>
				<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				  ${readonlyPubStr}
					type="text" id="inputGazzettaBR" name="<%=  ParametriServlet.FIELD_NAME_BOLLETTINO_REGIONALE %>" 
					onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataBore}'/>">
				<c:if test="${readonlyPub ne true}">
					<img src="calendar/img.gif" id="calendarGazzettaBR" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					<script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaBR",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaBR",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					</script>
				</c:if>
			</td>
	   	</tr>
	   	<tr>
	     <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataGURI") %>>Gazzetta Ufficiale Repubblica Italiana - GURI</label></td>
 			<td>
				<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				  ${readonlyPubStr}
					type="text" id="inputGazzettaRI" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>" 
					onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuri}'/>">
				<c:if test="${readonlyPub ne true}">
					<img src="calendar/img.gif" id="calendarGazzettaRI" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					<script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaRI",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaRI",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					</script>
				</c:if>
			</td>
	   	</tr>
	   	<tr>
	     <td><label >Albo pretorio del Comune ove si eseguono i lavori</label></td>
 			<td>
				<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				${readonlyPubStr}
					type="text" id="inputAP" name="<%=  ParametriServlet.FIELD_NAME_ALBO_PRETORIO %>" 
					onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataAlbo}'/>">
				<c:if test="${readonlyPub ne true}">
					<img src="calendar/img.gif" id="calendarAP" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					<script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputAP",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarAP",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					</script>
				</c:if>
			</td>
	   	</tr>
   		<tr>
 			<th><label >Quotidiani nazionali</label></th>
 			<td>
				<input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_NAZIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;"  
				 ${readonlyPubStr}
				type="text" value="<c:out value='${pubblicazione.quotidianiNaz}'/>" onblur="validateNumber(this)" maxlength="9"/>
			</td>
	   	</tr>
	   	<tr>
 			<th><label >Quotidiani locali</label></th>
 			<td>
				<input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_REGIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" 
				 ${readonlyPubStr}
				type="text" value="<c:out value='${pubblicazione.quotidianiReg}'/>"  onblur="validateNumber(this)" maxlength="9"/>
			</td>
	   	</tr>
	   	<tr>
 			<th><label >Periodici</label></th>
 			<td>
				<input  name="<%=  ParametriServlet.FIELD_NAME_PERIODICI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" 
				${readonlyPubStr}
				type="text" value="<c:out value='${pubblicazione.periodici}'/>"  onblur="validateNumber(this)" maxlength="4"/>
			</td>
	   	</tr>
	   	<tr>
			<th><label >Profilo del Committente</label></th>
		  	<td>
		  	    <u:selectBooleanRadio name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE%>" 
			    value="${pubblicazione.profiloCommitente}" trueId="check1Y" 
			    falseId="check1N" readonly="${readonlyPub}" 
			    tabindex="<%=++indiceTab%>" />
			    <%indiceTab++; %>			
  			</td>
		</tr>
		<tr>
			<th><label >Sito Informatico Ministero Infrastrutture<br>e piattaforma digitale ANAC tramite i sistemi<br>informatizzati regionali</label></th>
		  	<td>
		  	   <u:selectBooleanRadio name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP%>" 
			    value="${pubblicazione.sitoMinisteroInfTrasp}" trueId="check2Y" 
			    falseId="check2N" readonly="${readonlyPub}" 
			    tabindex="<%=++indiceTab%>" />
			    <%indiceTab++; %>
  			</td>
		</tr>
		<tr>
			<th><label >Sito Informatico Osservatorio Contratti Pubblici</label></th>
		  	<td>
		  	    <u:selectBooleanRadio name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP%>" 
			    value="${pubblicazione.sitoOsservatorioCP}" trueId="check3Y" 
			    falseId="check3N" readonly="${readonlyPub}" 
			    tabindex="<%=++indiceTab%>" />
			    <%indiceTab++; %>
  			</td>
		</tr>