<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">

<head>
	<title>ANAC</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util" %>
		<meta name="description" content="Servizi Autorita Contratti Pubblici" />
		<link rel="stylesheet" type="text/css" href="/SimogWeb/theme/newstile.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="/SimogWeb/theme/screen.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="/SimogWeb/theme/footer.css" media="screen" />
</head>
<script type="text/javascript" src="xtree/treeutils.js"></script>

<body style="min-width: 500px">
	<div id="div_head">
		<h1><img align="left" src="img/logo.jpg" usemap="#Map" alt="" />
			<img align="left" src="img/pacc.png" alt="PACC" style="margin-left: 10px;" />
			<span class="nascosto">Logo ANAC</span>
		</h1>
	</div>
	<div id="gabbia">
		<div id="header">
			<%@ include file="include/menu/menuFaq.inc" %>
		</div>
		<div id="bodypage" style="padding-left: 0em">
			<div class="bodypage">
				<br><br>
				<h4><a name="up"></a>
					<utils:message key="faq.titoloFaqServizio" />
				</h4>
				<br /><br />
				<ol>
					<li>
						<strong><a href="#f_1" class="rosso">
								<utils:message key="faq.domanda1" />
							</a></strong>
					</li>
					<li>
						<strong><a href="#f_2" class="rosso">
								<utils:message key="faq.domanda2" />
							</a></strong>
					</li>

					<li>
						<strong><a href="#f_3" class="rosso">
								<utils:message key="faq.domanda3" />
							</a></strong>
					</li>
					<li>
						<strong><a href="#f_4" class="rosso">
								<utils:message key="faq.domanda4" />
							</a></strong>
					</li>
				</ol>
				<hr />
				<ol>
					<li>
						<a name="f_1"></a>
						<strong>
							<utils:message key="faq.domanda1" />
						</strong>
						<br />
						<br />
						<utils:message key="faq.risposta1" />
						<br />
						<br />
						<a href="#up"><strong>
								<utils:message key="faq.tornaSu" />
							</strong></a><br />
						<hr />
						<br />
					</li>
					<li>
						<a name="f_2"></a>
						<strong>
							<utils:message key="faq.domanda2" />
						</strong>
						<br />
						<br />
						<utils:message key="faq.risposta2" />
						<br />
						<br />
						<a href="#up"><strong>
								<utils:message key="faq.tornaSu" />
							</strong></a><br />
						<hr />
						<br />
					</li>
					<li>
						<a name="f_3"></a>
						<strong>
							<utils:message key="faq.domanda3" />
						</strong>
						<br />
						<br />
						<utils:message key="faq.risposta3" />
						<br />
						<br />
						<a href="#up"><strong>
								<utils:message key="faq.tornaSu" />
							</strong></a><br />
						<hr />
						<br />
					</li>

					<li>
						<a name="f_4"></a>
						<strong>
							<utils:message key="faq.domanda4" />
						</strong>
						<br />
						<br />
						<utils:message key="faq.risposta4" />
						<br />
						<br />
						<a href="#up"><strong>
								<utils:message key="faq.tornaSu" />
							</strong></a><br />
						<hr />
						<br />
					</li>
				</ol>
			</div>
		</div>

		<jsp:include flush="true" page="include/newfooter.inc" />

	</div>
</body>

</html>