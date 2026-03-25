<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
	<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util" %>
		<%@ include file="include/newbasicHeader.inc" %>
			<%@ include file="include/controlloSessione.inc" %>

				<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
					<%@ page import="it.avlp.simog.db.generated.*" %>
						<%@ page import="it.avlp.simog.db.generated.*" %>
							<%@ page import="it.avlp.simog.db.Costanti" %>
								<%@ page import="it.avlp.simog.util.MessageHelper" %>

									<title>
										<utils:message key="rubrica.rubricaOperatoriEconomici" />
									</title>
									<script type="text/javascript">
										< !-
											function getKey(e) {
												var keynum;
												var keychar;
												var numcheck;
												if (window.event) // IE
												{
													keynum = e.keyCode;
												}
												else if (e.which) // Netscape/Firefox/Opera
												{
													keynum = e.which;
												}
												//keychar = String.fromCharCode(keynum);
												return keynum;
											}
										function trim(stringa) {
											while (stringa.substring(0, 1) == ' ') {
												stringa = stringa.substring(1, stringa.length);
											}
											while (stringa.substring(stringa.length - 1, stringa.length) == ' ') {
												stringa = stringa.substring(0, stringa.length - 1);
											}
											tmpStringa = "";
											for (i = 0; i < stringa.length; i = i + 1) {
												if (stringa.charAt(i) == ' ' || stringa.charAt() == '%') { }
												else tmpStringa = tmpStringa + stringa.charAt(i);

											}
											return tmpStringa;
										}
										function submitRubrica(operazione) {
											document.rubrica.action = "rubrica?operazione=" + operazione;
											document.rubrica.submit();
										}
										function controlloDatiRicerca() {
											codFiscale = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>").value;
											codFiscale = trim(codFiscale);
											cognome = document.getElementById("denominazione").value;
											cognome = trim(cognome);
											if (codFiscale == "" && cognome == "") {
												alert("<%= MessageHelper.getMessage(request, "rubrica.msg.inserireFiltri") %>");
												return;
											}
											else if (cognome.length < 2 && codFiscale == "") {
												alert('<%= MessageHelper.getMessage(request, "rubrica.msg.filtroCognomeCorto") %>');
												return;
											}
											else {
												document.getElementById('cerca').disabled = true;
												submitRubrica('view');
											}
											// document.rubrica.action =  "rubrica?operazione=view";
											// document.rubrica.submit();
										}
										function submitIfKeyPress(e, key, operaz, thisElem) {
											if (thisElem.value != "" && getKey(e) == key) {
												submitRubrica(operaz)
											}
										}
										//-->
									</script>


									</head>
									<%@ include file="script/domUtils.js" %>
										<%@ include file="/script/AjaxPaesi.js" %>
											<% int indiceTab=0; %>

												<body onload="ajaxRead('comboPaesi')">
													<div id="gabbia">
														<%@ include file="include/header.inc" %>
															<%@ include file="include/menu/menuRubrica.inc" %>

																<% int maxRigheVisualizzabili=Integer.parseInt(
																	(String)request.getAttribute(
																	ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) );
																	%>

																	<% Integer
																		startRowInt=(Integer)request.getAttribute(
																		ParametriServlet.START_ROW ); %>
																		<% int startRow=startRowInt.intValue(); %>

																			<% TableBean
																				tableBean=(TableBean)request.getAttribute(ParametriServlet.TABLEBEAN);
																				int
																				tableBeanSize=tableBean.getFullSize();
																				long resto=(tableBeanSize %
																				maxRigheVisualizzabili); long
																				fineElenco=tableBeanSize - resto -
																				maxRigheVisualizzabili - (resto==0 ?
																				maxRigheVisualizzabili : 0) ; %>

																				<% int righeVisualizzate=startRow +
																					tableBean.getTableSize();%>
																					<% if ( righeVisualizzate>
																						tableBeanSize ) { %>
																						<% righeVisualizzate=tableBeanSize;
																							%>
																							<% } %>


																								<form action="rubrica"
																									method="post"
																									name="rubrica"
																									id="rubrica">
																									<input type="hidden"
																										name="paginazione"
																										id="paginazione"
																										value="ricercaPagine">
																									<div id="bodypage">
																										<div
																											class="bodypage-e">
																											<h1>Rubrica
																												Operatori
																												Economici
																											</h1>
																											<br />
																											<%@ include
																												file="include/gestisciErrore.inc"
																												<utils:message
																												key="scheda.inizioElenco" />
																											</a>
																											</li>
																											<% } %>

																												<% if (
																													righeVisualizzate>
																													maxRigheVisualizzabili
																													)
																													{
																													%>
																													<li><a href="rubrica?operazione=view&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>"
																															title="<utils:message key="
																															scheda.precedenti" />">
																														<utils:message
																															key="scheda.precedenti" />
																														</a>
																													</li>
																													<% } else
																														{%>
																														<li><a id="disabledMenu"
																																title="<utils:message key="
																																scheda.precedenti" />">
																															<utils:message
																																key="scheda.precedenti" />
																															</a>
																														</li>
																														<% }
																															%>

																															<% if
																																(
																																tableBeanSize
																																-
																																righeVisualizzate>
																																0
																																)
																																{
																																%>
																																<li><a href="rubrica?operazione=view&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>"
																																		title="<utils:message key="
																																		scheda.successive" />">
																																	<utils:message
																																		key="scheda.successive" />
																																	</a>
																																</li>
																																<% } else
																																	{%>
																																	<li><a id="disabledMenu"
																																			title="<utils:message key="
																																			scheda.successive" />">
																																		<utils:message
																																			key="scheda.successive" />
																																		</a>
																																	</li>
																																	<% }
																																		%>

																																		<% if
																																			(
																																			righeVisualizzate
																																			!=tableBeanSize
																																			)
																																			{
																																			%>
																																			<li><a href="rubrica?operazione=view&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>"
																																					title="<utils:message key="
																																					scheda.fineElenco" />">
																																				<utils:message
																																					key="scheda.fineElenco" />
																																				</a>
																																			</li>
																																			<% } else
																																				{%>
																																				<li><a id="disabledMenu"
																																						title="<utils:message key="
																																						scheda.fineElenco" />">
																																					<utils:message
																																						key="scheda.fineElenco" />
																																					</a>
																																				</li>
																																				<% }
																																					%>
																																					<% }
																																						%>
																																						<%--
																																							<p>
																																							<%= "startRowInt "
																																								+
																																								(startRowInt)%>
																																								</p>
																																								<p>
																																									<%= "righeVisualizzate "
																																										+
																																										(righeVisualizzate)%>
																																								</p>
																																								<p>
																																									<%= "tableBean.getFullSize()  - resto "
																																										+
																																										(tableBean.getFullSize()
																																										-
																																										resto)%>
																																								</p>
																																								<p>
																																									<%= "fine elenco "
																																										+
																																										fineElenco%>
																																								</p>
																																								<p>
																																									<%= "if( "
																																										+righeVisualizzate+"
																																										< "+(tableBean.getFullSize() - resto)+"
																																										)"%>
																																								</p>
																																								--%>
																										</div>

																										<div>
																											<fieldset>
																												<legend>
																													<utils:message
																														key="rubrica.filtriNominali" />
																												</legend>
																												<table>
																													<tr>
																														<td class="detailHelp"
																															colspan="2">
																															<utils:message
																																key="rubrica.inserireCodiceFiscalePartitaIva" />
																														</td>
																													</tr>
																													<tr>
																														<td>
																															<input
																																tabindex="<%= ++indiceTab%>"
																																size="50"
																																type="text"
																																title="Codice Fiscale"
																																id="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>"
																																name="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>"
																																onkeypress="submitIfKeyPress(event,'13','view',this)">
																														</td>
																													</tr>
																													<tr>
																														<td class="detailHelp"
																															colspan="2">
																															<utils:message
																																key="rubrica.inserireDenominazioneSoggetto" />
																														</td>
																													</tr>
																													<tr>
																														<td>
																															<input
																																tabindex="<%= ++indiceTab%>"
																																size="50"
																																type="text"
																																title="<utils:message key="
																																table.denominazione" />"
																															id="denominazione"
																															name="
																															<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE
																																%>
																																"
																																onkeypress="submitIfKeyPress(event,'13','view',this)">
																														</td>
																													</tr>
																													<tr>
																														<td class="detailHelp"
																															colspan="2">
																															Selezionare
																															un
																															paese
																															(
																															Se
																															Operatore
																															Estero
																															)
																														</td>
																													</tr>
																													<tr>
																														<td
																															id="comboPaesi">

																														</td>
																													</tr>
																												</table>
																											</fieldset>

																										</div>

																										<div>
																											<fieldset>
																												<legend>
																													<utils:message
																														key="rubrica.elencoOperatoriEconomici" />
																												</legend>
																												<div
																													class="scrollDyn">
																													<% TableBeanRow
																														currentRow=null;
																														%>
																														<% TableBeanRow
																															previousRow=null;
																															%>
																															<% int
																																id_partecipante=0;
																																%>
																																<% if(tableBean!=null)
																																	{
																																	%>
																																	<table
																																		class="gara">
																																		<tr>
																																			<th class="garaTh"
																																				width="40%">
																																				<utils:message
																																					key="table.denominazione" />
																																			</th>
																																			<th class="garaTh"
																																				width="10%">
																																				<utils:message
																																					key="table.codicePaese" />
																																			</th>
																																			<th class="garaTh"
																																				width="20%">
																																				<utils:message
																																					key="rubrica.codiceFiscalePartitaIva" />
																																			</th>
																																			<th class="garaTh"
																																				width="1%">
																																			</th>
																																		</tr>

																																		<% for
																																			(
																																			int
																																			rowIndex=0;
																																			rowIndex
																																			<
																																			tableBean.getTableSize();
																																			rowIndex++
																																			)
																																			{
																																			%>
																																			<% currentRow=tableBean.getRow(rowIndex);
																																				%>
																																				<% int
																																					counter=0;
																																					%>
																																					<% id_partecipante=Integer.parseInt(currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));
																																						%>
																																						<tr>
																																							<td
																																								class="garaTd">
																																								<%=
																																									PageHelper.formattaTesto(currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DENOMINAZIONE))%>
																																							</td>
																																							<td
																																								class="garaTd">
																																								<% if
																																									(
																																									currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO)
																																									!=""
																																									)
																																									{%>
																																									<%=currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO)%>
																																										<% }else{
																																											%>
																																											<%=Costanti.CODICE_STATO_ITALIANO
																																												%>
																																												<% }
																																													%>
																																							</td>
																																							<td
																																								%>
																												</div>
																											</fieldset>
																										</div>

																										<input
																											type="button"
																											id="cerca"
																											name="<%= ParametriServletRubrica.OPERAZIONE %>"
																											value="<utils:message key="
																											button.cerca" />"
																										onclick="controlloDatiRicerca();">
																										<input
																											type="button"
																											name="<%= ParametriServletRubrica.OPERAZIONE %>"
																											value="<utils:message key="
																											button.aggiungiRubrica" />"
																										onclick="submitRubrica('Aggiungi
																										alla
																										rubrica');">
																									</div>
													</div>
													</form>

													<%@ include file="include/newfooter.inc" %>
														</div>

												</body>


												<%@page import="it.avlp.simog.util.PageHelper" %>

													</html>