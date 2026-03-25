<%@ page import="java.util.Locale" %>
<%
	// Carica il calendar corretto in base alla locale
	// Usa le variabili currentLocale e lang già dichiarate nel file includente
	// (newbasicHeader.inc o basicHeader.inc)
	// NOTA: Questo file deve essere incluso DOPO un file header che dichiara currentLocale
	// Se lang non è dichiarata, la calcola da currentLocale
	String calendarLang = (lang != null) ? lang : currentLocale.getLanguage();
	String calendarFile = "ar".equals(calendarLang) ? "calendar-ar.js" : "calendar-it.js";
%>
<script type="text/javascript" src="calendar/<%= calendarFile %>"></script>

