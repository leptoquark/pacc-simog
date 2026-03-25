<%@page import="it.avlp.simog.db.SimogFlags" %>
	<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
		<%@ include file="include/newbasicHeader.inc" %>
			<%@ page import="it.avlp.simog.common.servlet.*" %>
				<%@page import="it.avlp.simog.util.SimogProperties" %>
					<%@page import="it.avlp.simog.util.MessageHelper" %>
						<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util" %>
							<% String msgErroreCF=MessageHelper.getMessage(request, "dettaglio.erroreCodiceFiscale" );
								String msgPassword=MessageHelper.getMessage(request, "avviso.digitarePassword" ); String
								msgPopup=MessageHelper.getMessage(request, "avviso.popupBloccato" ); %>

								<script type="text/javascript" src="xtree/treeutils.js"></script>

								<!-- TB: ticket popup modali. Import css e js -->
								<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
								<script type="text/javascript" src="script/other/jquery.js"></script>
								<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
								<!-- fine import popup modali -->

								<script type="text/javascript">
									<!--
									
									// check auth type
									function chkAuth(form)
									{
									<% if(SimogFlags.is3024IAMActive()){ %>
											location.replace("<%= SimogProperties.getInstance().getSamlLoginUrl() %>");
									<% } else {%>
											// set focus su username
											f_setfocus(form);
									<% } %> 	
									}
									
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
											alert("<%= MessageHelper.getMessage(request, "js.error.codiceFiscaleInvalid") %>")
											return false
										}*/
									return true
}

									function checkCampi() {

										campoCF = document.forms[0].elements["<%= ParametriServlet.FIELD_NAME_LOGIN %>"]
										campoPW = document.forms[0].elements["<%= ParametriServlet.FIELD_NAME_PASS  %>"]

										if (!controllaCF(campoCF)) {
											campoCF.focus()
										} else {
											if (campoPW.value == "" || campoPW.value == null) {
												alert("<%= msgPassword %>");
												campoPW.focus()
											} else {
												document.forms[0].action = "checkAuthentication"
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
												<h4>
													<utils:message key="login.accessoSimog" />
													<font color="red"><big>

															<%=SimogProperties.getInstance().getAmbiente() %>
														</big></font>
												</h4>
												<br>
												<%@ include file="include/gestisciErrore.inc" %>

													<!-- 			<table width="100%"  cellpadding="5"> -->
													<!-- 				<tr> -->
													<!-- 				<td rowspan="3"> -->
													<!-- 					<img src="img/simogWarning_little.jpg" width="60px" height="55px" align="left" alt="img simog warning"> -->
													<!-- 				</td> -->
													<!-- 				</tr> -->
													<!-- 				<tr> -->
													<!-- 					<td align="left" valign="top"><p style="color:rgb(180,0,28);"> -->
													<!-- 						<strong>Avvisi<br><br>L'accesso al sistema � consentito ai soli utenti che, in sede di -->
													<!-- 						iscrizione all'anagrafe, abbiano selezionato il profilo di Responsabile Unico del Procedimento (RUP).</strong> -->
													<!-- 					</td> -->
													<!-- 				</tr> -->
													<!-- 				<tr> -->
													<!-- 					<td><p style="color:rgb(180,0,28);"> -->
													<!-- 						<strong>Si avvisa che attraverso il sistema SIMOG � possibile acquisire il CIG anche per appalti aggiudicati prima del 7/9/2010 o per gare indette prima di tale data, secondo quanto previsto dalle norme vigenti in merito alla tracciabilit� dei flussi finanziari (vedi anche relative <a target="_new" href="http://www.anticorruzione.it/portal/public/classic/MenuServizio/FAQ/ContrattiPubblici/FAQtracciabilita">FAQ sul sito www.anticorruzione.it</a>). In tal caso, occorre inserire nell'apposito campo la data di pubblicazione del bando anche se antecedente al 7/9/2010.</font></strong></p> -->
													<!-- 					</td> -->
													<!-- 				</tr> -->
													<!-- 			</table> -->
													<!-- MEV XXXXX 3.04.11 -->
													<!-- <strong><font color="#FF0000">NOTA: Per effetto della delibera ANAC n. 1/2017 i CIG non perfezionati saranno cancellati automaticamente dal sistema decorsi 90 giorni dalla relativa data di creazione. Detta disposizione si applica ai soli CIG creati a decorrere dal 16 febbraio 2017, data di entrata in vigore della delibera. 
Si invitano gli utenti a tenere aggiornato il proprio recapito e-mail al fine di ricevere tempestivamente le notifiche previste all'approssimarsi della scadenza e in occasione della cancellazione. <br>
Per quanto attiene ai CIG acquisiti in data antecedente l'entrata in vigore della delibera, saranno disponibili a breve ulteriori comunicazioni e risposte a quesiti frequenti.
</font></strong> -->
													<!-- <br><br> --><a
														href="https://www.anticorruzione.it/chiedilo-ad-anac"
														target="_blank">
														<utils:message key="login.faq" />
													</a><br><br>
													<div id="dialog"></div>
													<p>
														<utils:message key="login.identificarsi" />
													</p>
													<div> <!-- MEV 44995 3.04.11 -->
														<strong>
															<font color="#FF0000">
																<utils:message key="login.avvisoDeliberaPre" /> <a
																	href="https://www.anticorruzione.it/-/delibera-n.-582-del-13-dicembre-2023-adozione-comunicato-relativo-avvio-processo-digitalizzazione"
																	target="_blank">
																	<utils:message key="login.avvisoDeliberaLink" />
																</a>
															</font>
														</strong>
													</div>
													<!-- MAD 56200 3.04.13 - Avviso login: in arabo sempre i18n, in italiano da config se presente -->
													<%
													String langAvviso = (String) pageContext.getAttribute("lang");
													if (langAvviso == null) {
														java.util.Locale locAvviso = (java.util.Locale) request.getAttribute("locale");
														if (locAvviso == null) locAvviso = (java.util.Locale) session.getAttribute("userLocale");
														langAvviso = (locAvviso != null) ? locAvviso.getLanguage() : "it";
													}
													if ("ar".equals(langAvviso)) { %>
														<br>
														<div>
															<strong><font color="#FF0000"><utils:message key="avviso.login.titolo" /></font></strong><br>
															<strong><utils:message key="avviso.login.intro" /></strong>
															<p><span style="margin-left:20px"><utils:message key="avviso.login.punto1" /></span></p>
															<p><span style="margin-left:20px"><utils:message key="avviso.login.punto2" /></span></p>
															<p><span style="margin-left:20px"><utils:message key="avviso.login.punto3" /></span></p>
															<p><strong><utils:message key="avviso.login.nota1" /></strong></p>
															<p><strong><utils:message key="avviso.login.nota2" /></strong></p>
															<br>
														</div>
													<% } else {
														String avvisoPaginaLogin=SimogProperties.getInstance().getAvvisoPaginaLogin();
														boolean avvisoCupCpv=Boolean.parseBoolean(SimogProperties.getInstance().getAvvisoCupCpv());
														if (avvisoPaginaLogin != null && !avvisoPaginaLogin.trim().isEmpty()){ %>
														<br>
														<div>
															<% if (avvisoCupCpv){ String []
																avviso=avvisoPaginaLogin.split("\n"); int
																length=avviso.length; int index=0; for (String line :
																avviso){ if (index==0){ String[] line1=line.split(":");
																%>
																<strong>
																	<font color="#FF0000">
																		<%= line1[0] %>:
																	</font>
																</strong>
																<strong>
																	<%= line1[1] %>
																</strong>

																<% } else if (line.length() > 0 && Character.isDigit(line.charAt(0))){ %>
																	<span style="margin-left:20px">
																		<%= line %>
																	</span><br>
																	<% } else { %>
																		<p>
																			<%= line %>
																		</p>

																		<%} index ++; }} if (!avvisoCupCpv){ String[]
																			avviso1=avvisoPaginaLogin.split(":", 2) ; %>

																			<strong>
																				<font color="#FF0000">
																					<%= avviso1[0] %>:
																				</font>
																			</strong>
																			<strong>
																				<%= avviso1[1] %>
																			</strong><br>

																			<%} } %>
																				<br>
														</div>
													<% } %>
														<p style="margin-bottom:5px;"><strong>
																<utils:message key="login.credenziali" />
															</strong></p>
														<form name="frmLogin" method="post"
															action="javascript:checkCampi();">
															<fieldset id="fldlogin">
																<legend>
																	<utils:message key="login.datiAutenticazione" />
																</legend>
																<table>
																	<tr>
																		<td>
																			<label
																				for="<%= ParametriServlet.FIELD_NAME_LOGIN %>"><strong>
																					<utils:message
																						key="login.codiceFiscale" />
																				</strong></label>
																		</td>
																		<td>
																			<input
																				name="<%= ParametriServlet.FIELD_NAME_LOGIN %>"
																				type="text">
																		</td>
																		<td style="padding-left: 10px;"><a
																				href="https://servizi.anticorruzione.it"
																				title="<utils:message key="login.gestioneUtenza" plain="true" />">
																			<utils:message key="login.gestioneUtenza" />
																			</a>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<label
																				for="<%= ParametriServlet.FIELD_NAME_PASS %>"><strong>
																					<utils:message
																						key="login.password" />
																				</strong></label>
																		</td>
																		<td>
																			<input
																				name="<%= ParametriServlet.FIELD_NAME_PASS %>"
																				type="password" maxlength="24">
																		</td>
																		<td style="padding-left: 10px;"><a
																				href="https://servizi.anticorruzione.it/portal/classic/GestioneUtenti/RecuperoPassword"
																				title="<utils:message key="login.recuperaPassword" plain="true" />">
																			<utils:message
																				key="login.recuperaPassword" /></a>
																		</td>
																	</tr>
																	<!-- 			<tr><td><a href="https://servizi.avcp.it/portal/classic/GestioneUtenti/RecuperoPassword" title="Richiesta recupero password">Recupera password</a></td></tr> -->
																	<tr>
																		<td colspan="2">&nbsp;
																			<!--			<a href="https://anagrafe.avcp.it/GuidaIscrizione.pdf" title="Consulta la guida all'iscrizione">Guida all'iscrizione</a>-->
																		</td>
																	</tr>
																</table>
															</fieldset>
															<input TYPE="submit" value="<utils:message key="button.accedi" plain="true" />">
														</form>
											</div>
										</div>
										<%@ include file="include/newfooter.inc" %>
									</div>
									<SCRIPT type="text/javascript">
										<!-
										var popupblock = false;
										var test = window.open("checkPopUp.html","CheckPopUp",'top=9000,left=9000,width=200,height=100');
										if (test==null || typeof(test)=="undefined") popupblock = true; else test.close();
										if (popupblock) alert("<%= msgPopup %>");
										//-->
									</script>
								</body>

								</html>
