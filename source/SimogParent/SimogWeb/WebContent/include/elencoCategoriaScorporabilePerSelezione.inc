<% int i = 0; %>
<% TableBeanRow currentCSRow = null; %>
<%  String[] scorporabili = request.getParameterValues(ParametriServlet.FIELD_NAME_CATEGORIA);
    String prevFromReq = request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_PREVALENTE);
%>
	<%@page import="it.avlp.simog.db.Costanti"%>
<div class="elencoCategorie">
		<table>
			<tr>
				<th>Descrizione Categoria</th>
				<th>Scorporabile</th>
				<th>Prevalente</th>
			</tr>
			<% for ( i = 0; i < listaCategorie.getTableSize(); i++ ) { %>
				<% String tableBeanClass = ( i % 2 == 0 ) ? "TableBeanOdd" : "TableBeanEven"; %>
				<% String selected = ( i == 0 && prevFromReq == null ) ? "checked" : ""; %>
				<tr class="<%= tableBeanClass %>">
				<% currentCSRow = listaCategorie.getRow(i); %>
				<% String currentIdCatScorp = currentCSRow.getNulledField(CATEGORIA.ID_CATEGORIA); %>
				<td><label for="categoria[<%= i %>]"><%= currentCSRow.getNulledField(CATEGORIA.DESCRIZIONE) %></label></td>
				<td><input type="checkbox" name="<%= ParametriServlet.FIELD_NAME_CATEGORIA %>" value="<%= currentIdCatScorp %>" id="ChCategoria[<%= currentIdCatScorp %>]" <h:checkElement list="<%=scorporabili%>" element="<%=currentIdCatScorp %>"/> ></td>
				<td><input type="radio" name="<%= ParametriServlet.FIELD_NAME_CATEGORIA_PREVALENTE %>" value="<%= currentIdCatScorp %>" <%= currentIdCatScorp.equals(prevFromReq) ? "checked" : selected %> id="SelCategoria[<%= currentIdCatScorp %>]"></td>
				</tr>
			<% } %>
		</table>
	</div>