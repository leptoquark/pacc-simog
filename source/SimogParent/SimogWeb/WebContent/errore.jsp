<%@ page contentType="text/html; charset=UTF-8" language="java" %>
	<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util" %>
		<%@ include file="include/newbasicHeader.inc" %>
			<!DOCTYPE html
				PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
			<%@page import="it.avlp.simog.util.SimogProperties" %>
				<html xmlns="http://www.w3.org/1999/xhtml" lang="it" xml:lang="it">

				<head>
					<meta http-equiv="Expires" content="0" />
					<meta http-equiv="Pragma" content="no-cache" />
					<meta http-equiv="Cache-Control" content="no-cache" />
					<meta name="description" content="Servizi Autorita Contratti Pubblici" />
					<meta nosave="true">
					<link rel="stylesheet" href="theme/newstile.css" />
					<link rel="stylesheet" href="theme/footer.css" />
					<%@ page import="it.avlp.simog.common.servlet.ParametriServlet" %>
						<% String loginUrl=SimogProperties.getInstance().getSamlLoginUrl();
							//(String)request.getSession().getAttribute(ParametriServlet.LOGIN_URL); if(loginUrl==null)
							loginUrl=ParametriServlet.JSP__LOGIN; %>

				<body>
					<div id="gabbia">

						<div id="header">
							<div id="topmenu">
								<ul>
									<li><a href="<%= loginUrl %>">
											<utils:message key="errore.tornaLogin" />
										</a>
								</ul>
							</div>
						</div>

						<div id="bodypage">
							<div class="bodypage-e">
								<h5>
									<utils:message key="errore.titolo" />
								</h5>

								<div>
									<%@ include file="include/gestisciErrore.inc" %>
								</div>

								<div class="bodypage-b">
									<img src="img/simogWarning.jpg">
								</div>


								<div class="bodypage-d">


									<h4>
										<utils:message key="errore.possibiliCause" />
									</h4>
									<ul>
										<div class="menu-c">
											<li>
												<utils:message key="errore.causa1" />
												<ul>
													<li>
														<utils:message key="errore.soluzione1" /> <A
															href="<%=loginUrl %>" title="<utils:message key="
															login.accessoSimog" />">
														<utils:message key="login.accessoSimog" /></A>
													</li>
												</ul>
											</li>
										</div>
										<div class="menu-c">
											<li>
												<utils:message key="errore.causa2" />
												<ul>
													<li>
														<utils:message key="errore.soluzione2" />
													</li>
												</ul>
											</li>
										</div>
										<div class="menu-c">
											<li>
												<utils:message key="errore.causa3" />
												<ul>
													<li>
														<utils:message key="errore.soluzione3" />
													</li>
												</ul>
											</li>
										</div>
									</ul>
								</div>



							</div>



						</div>
						<%@ include file="include/newfooter.inc" %>
					</div>
				</body>

				</html>