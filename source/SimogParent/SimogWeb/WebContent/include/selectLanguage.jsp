<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%
	// Usa la variabile currentLocale già dichiarata nel file includente (newbasicHeader.inc)
	// Non dichiarare di nuovo per evitare conflitti
	String currentLang = currentLocale.getLanguage();
	
	// Costruisce l'URL corrente preservando i parametri esistenti
	String currentUrl = request.getRequestURI();
	String queryString = request.getQueryString();
	StringBuilder urlBuilder = new StringBuilder(currentUrl);
	
	// Gestisce i parametri esistenti
	if (queryString != null && !queryString.isEmpty()) {
		// Rimuove il parametro locale esistente se presente
		queryString = queryString.replaceAll("(^|&)locale=[^&]*", "");
		// Rimuove eventuali "&" iniziali o finali
		queryString = queryString.replaceAll("^&+|&+$", "");
		
		if (!queryString.isEmpty()) {
			urlBuilder.append("?").append(queryString);
		}
	}
	
	// Aggiunge il parametro locale
	String separator = (urlBuilder.indexOf("?") >= 0 ? "&" : "?");
	String urlIt = urlBuilder.toString() + separator + "locale=it";
	String urlAr = urlBuilder.toString() + separator + "locale=ar";
%>
<div class="language-selector" style="display: inline-block; vertical-align: middle; white-space: nowrap;">
	<span class="lang-label" style="color: #fff; margin-right: 5px; font-size: 0.889em;"><utils:message key="lang.selezionaLingua" />:</span>
	<a href="<%= urlIt %>" 
	   class="lang-link <%= "it".equals(currentLang) ? "lang-active" : "" %>"
	   title="<utils:message key="lang.italiano" plain="true" />"
	   style="<%= "it".equals(currentLang) ? "font-weight: bold; text-decoration: none; color: #fff; background-color: rgba(255,255,255,0.2); padding: 2px 8px; border-radius: 3px;" : "text-decoration: underline; color: #fff;" %>; margin: 0 3px; font-size: 0.889em;">
		<utils:message key="lang.italiano" />
	</a>
	<span class="lang-separator" style="color: #fff; margin: 0 5px; font-size: 0.889em;">|</span>
	<a href="<%= urlAr %>" 
	   class="lang-link <%= "ar".equals(currentLang) ? "lang-active" : "" %>"
	   title="<utils:message key="lang.arabo" plain="true" />"
	   style="<%= "ar".equals(currentLang) ? "font-weight: bold; text-decoration: none; color: #fff; background-color: rgba(255,255,255,0.2); padding: 2px 8px; border-radius: 3px;" : "text-decoration: underline; color: #fff;" %>; margin: 0 3px; font-size: 0.889em; direction: rtl; display: inline-block;">
		<utils:message key="lang.arabo" />
	</a>
</div>

