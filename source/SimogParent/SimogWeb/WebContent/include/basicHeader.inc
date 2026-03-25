<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="java.util.Locale" %>
<%
	// Verifica se le variabili sono già state definite (da newbasicHeader.inc o da un precedente include)
	// Usa pageContext per verificare se sono già state definite
	java.util.Locale currentLocale = (java.util.Locale) pageContext.getAttribute("currentLocale");
	String lang = (String) pageContext.getAttribute("lang");
	String dir = (String) pageContext.getAttribute("dir");
	
	// Se non sono già definite, le definiamo
	if (currentLocale == null) {
		// Determina la lingua corrente con priorità (stessa logica di newbasicHeader.inc)
		
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
		
		lang = currentLocale.getLanguage();
		dir = "ar".equals(lang) ? "rtl" : "ltr";
		
		// Salva in pageContext per evitare duplicati
		pageContext.setAttribute("currentLocale", currentLocale);
		pageContext.setAttribute("lang", lang);
		pageContext.setAttribute("dir", dir);
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="<%= lang %>" xml:lang="<%= lang %>" dir="<%= dir %>">
<head>
<!--<script type="text/javascript" src="https://getfirebug.com/firebug-lite.js"></script>-->

<% response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.addHeader("Cache-Control", "pre-check=0, post-check=0");
response.setDateHeader("Expires", 0); %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="description" content="Servizi Autorita Contratti Pubblici" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/stile.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/newstile.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/screen.css" type="text/css" />
<link rel="shortcut icon" href="img/favicon.ico" >
<link rel="icon" href="img/animated_favicon1.gif" type="image/gif" >