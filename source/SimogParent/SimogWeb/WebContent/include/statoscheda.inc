<th><label for="">&nbsp;STATO</label></th> 
<td width="100%">
	<c:choose>
	<c:when test='${statoann eq true}'>
	  	<p class="attenzione">
	</c:when>
	<c:when test='${statoid == "1"}'>
     	<p class="avviso">
	</c:when>
	<c:when test='${statoid == "2"}'>
    	<p class="errore">
	</c:when>
	<c:otherwise>
		<p class="nonesiste">
	</c:otherwise>
	</c:choose> 
  	<c:out value="${statodesc}" default="Scheda inesistente"/>
  	</p> 
</td>