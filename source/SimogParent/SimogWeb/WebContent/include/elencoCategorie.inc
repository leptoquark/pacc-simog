<% int i = 0; 
String[] scorporabili = request.getParameterValues(ParametriServlet.FIELD_NAME_CATEGORIA);
    String prevFromReq = request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_PREVALENTE);
    if(prevFromReq != null){
    	categoriaPrevalente = prevFromReq;
    }
    %>
<% TableBeanRow currentCSRow = null; %>
	<div class="elencoCategorie">
		<table width="100%">
			<tr>
				<th>Descrizione Categoria</th>
				<th>Scorporabile</th>
				<th>Prevalente</th>
			</tr>
			<% for ( i = 0; i < listaCategorie.getTableSize(); i++ ) { %>
				<% String tableBeanClass = ( i % 2 == 0 ) ? "TableBeanOdd" : "TableBeanEven"; %>
				<tr class="<%= tableBeanClass %>">
				<% currentCSRow = listaCategorie.getRow(i); %>
				<% String currentIdCatScorp = currentCSRow.getNulledField(CATEGORIA.ID_CATEGORIA);
					
				%>
				<% String checked = (scorporabili == null && categorie.contains(currentIdCatScorp)) ? " checked" : ""; %>
				<td><label for="categoria[<%= i %>]"><%= currentCSRow.getNulledField(CATEGORIA.DESCRIZIONE) %></label></td>
				<td><input type="checkbox" name="<%= ParametriServlet.FIELD_NAME_CATEGORIA %>" value="<%= currentIdCatScorp %>" id="ChCategoria[<%= currentIdCatScorp %>]" <h:checkElement list="<%=scorporabili%>" element="<%=currentIdCatScorp %>"/> <%= checked%>  <%=disabledTranneRipetiz %> <%=disabledTrannePariOpportunita%> <%=disabledTranneEscluso %>  <%= disabledTranneDatiCup %>  ></td>

				<% String selected = categoriaPrevalente.equalsIgnoreCase(currentIdCatScorp) ? " checked" : ""; %>
				<td><input type="radio" name="<%= ParametriServlet.FIELD_NAME_CATEGORIA_PREVALENTE %>" value="<%= currentIdCatScorp %>" id="SelCategoria[<%= currentIdCatScorp %>]"<%= selected %>  <%=disabledTranneRipetiz %> <%=disabledTranneEscluso %> <%=disabledTrannePariOpportunita%> <%= disabledTranneDatiCup %> ></td>
				</tr>
			<% } %>
		</table>
	</div>