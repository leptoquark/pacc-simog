<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%
	// Determina la lingua corrente con priorità:
	// 1. Parametro richiesta (?locale=ar)
	// 2. Attributo richiesta (impostato da LocaleFilter)
	// 3. Sessione (impostata da LocaleFilter)
	// 4. Default italiano
	java.util.Locale currentLocale = null;
	
	// 1. Controlla parametro richiesta (PRIORITÀ MASSIMA)
	String localeParam = request.getParameter("locale");
	if (localeParam != null && !localeParam.isEmpty()) {
		if ("ar".equalsIgnoreCase(localeParam)) {
			currentLocale = new java.util.Locale("ar");
		} else if ("it".equalsIgnoreCase(localeParam)) {
			currentLocale = new java.util.Locale("it");
		}
	}
	
	// 2. Controlla attributo richiesta (impostato da LocaleFilter)
	if (currentLocale == null) {
		currentLocale = (java.util.Locale) request.getAttribute("locale");
	}
	
	// 3. Controlla sessione (impostata da LocaleFilter)
	if (currentLocale == null) {
		currentLocale = (java.util.Locale) session.getAttribute("userLocale");
	}
	
	// 4. Default italiano
	if (currentLocale == null) {
		currentLocale = new java.util.Locale("it");
	}
	
	String lang = currentLocale.getLanguage();
	String dir = "ar".equals(lang) ? "rtl" : "ltr";
	
	// Salva in pageContext per evitare duplicati se basicHeader.inc viene incluso dopo
	pageContext.setAttribute("currentLocale", currentLocale);
	pageContext.setAttribute("lang", lang);
	pageContext.setAttribute("dir", dir);
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="<%= lang %>" xml:lang="<%= lang %>" dir="<%= dir %>">
<head>
<% response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.addHeader("Cache-Control", "pre-check=0, post-check=0");
response.setDateHeader("Expires", 0); %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="description" content="Servizi Autorita' Contratti Pubblici" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/newstile.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/screen.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/footer.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/rtl.css" type="text/css" />
<link rel="shortcut icon" type="image/png" href="img/favicon.png" />

 <div id="banner">
	      <div class="PORTLET-FRAGMENT"  style="">
	       

<!-- Start Time = 1469437556684 -->
	<div id="div_head"> 
	<div id="topbar">
	<div id="internal">
	    <table><tr><td class="nameApp">
			   <span>Simog</span></td>
			    <!-- Menu top --> 
				<td><ul class="menu_head1"> 
				<li><a href="https://www.anticorruzione.it/accessibilit%C3%A0"><utils:message key="header.accessibilita" /></a></li><li><a href="https://www.anticorruzione.it/contattaci"><utils:message key="header.contattaci" /></a></li><li><a href="https://www.anticorruzione.it/privacy-cookies"><utils:message key="header.privacyCookies" /></a></li>
				</ul></td>
				<!-- Selettore lingua sempre visibile nell'header -->
				<td style="text-align: right; padding-left: 20px; vertical-align: middle;">
					<%@ include file="selectLanguage.jsp" %>
				</td>
		      </tr>
			</table>			
		<!-- Fine Menu top --> 
	</div>
	
	   
	</div>
	
<img src="img/logo.jpg" usemap="#Map" alt="ANAC" /> 
<img src="img/pacc.png" alt="PACC" style="margin-left: 10px;" />
			<map name="Map" id="Map"> 
	      <area shape="rect" coords="75,18,335,124" href="http://www.anticorruzione.it" alt="" /> 
		  </map> 
		   <span class="nascosto">ANAC</span>
						
	

	</div>


	      </div>
	    </div>  
      
	  

  	  <div id="toolbar">
	      <div class="PORTLET-FRAGMENT"  style="">
	       <div id="UINavigationPortlet" style="min-width:300px" class="UINavigationPortlet">

<div id="header_bottom"> 
<ul id="menu_header_bottom"> 
<li class="primo"><a href="https://www.anticorruzione.it/anticorruzione"><utils:message key="header.anticorruzione" /></a></li><li><a href="https://www.anticorruzione.it/per-le-amministrazioni-pubbliche"><utils:message key="header.serviziAmministrazioniPubbliche" /></a></li><li><a href="https://www.anticorruzione.it/mission-e-competenze"><utils:message key="header.missioneCompetenze" /></a></li><li><a href="https://www.anticorruzione.it/amministrazione-trasparente"><utils:message key="header.amministrazioneTrasparente" /></a></li>
		
	</ul> 
 </div> 
</div>

	      </div>
	    </div>  
      
      
<div id="breadcrumb">
<p><a class="primo" href="http://www.anticorruzione.it/"><utils:message key="header.home" /></a>
/ <a href="https://www.anticorruzione.it/per-le-amministrazioni-pubbliche"><utils:message key="header.serviziAmministrazioniPubbliche" /></a>
/ <a href="https://simog.anticorruzione.it"><utils:message key="header.servizioSimog" /></a>
</div>
<noscript>&#10; <div style="font-size: medium;  top: 50%; left: 50%; margin-left: auto; margin-top: auto;  background-color: white; border: 4px solid red; padding: 20px; "><center>&#10; <utils:message key="header.attenzioneJavaScript" />&#10; </center></div>&#10;</noscript>
