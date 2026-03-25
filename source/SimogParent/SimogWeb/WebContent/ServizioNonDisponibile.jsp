<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="it" xml:lang="it">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="description" content="Servizi Autorita Contratti Pubblici" />
<meta nosave="true">
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<link rel="stylesheet" href="theme/newstile.css"/>
<link rel="stylesheet" href="theme/screen.css"/>
<%@ include file="include/newbasicHeader.inc" %>
<% session.invalidate(); %>
<body>
<div id="gabbia">

<div id="header">
	<div id="topmenu">
	<ul>
		<li><a href="javascript:history.back();"><utils:message key="button.indietro" /></a>
	</ul>
	</div>
</div>

	<div id="bodypage">	

	<div class="bodypage-e">
	<h5><utils:message key="servizio.servizioNonDisponibile" /></h5>
	
	<div class="bodypage-b">
		<img src="img/simogWarning.jpg">
	</div>	

			<br><br><br><br>
			
	<div class="bodypage-d">
		<h4>Possibili Cause</h4>
			<ul>
			<div class="menu-c">
			<li>Il servizio � attivo dalle 06:00 alle 22:00.</li>
			</ul>
	</div>
	

			
	</div>


	
	</div>
		<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>