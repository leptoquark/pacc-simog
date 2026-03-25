<fieldset>
	<table width="100%">
		<tr>
	     	<td colspan="2"><p class="detailHelp">Informazioni Gara/Lotto</p></td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_OGGETTO_GARA %>">Oggetto Gara</label></th>
			<td>
				<%= row.getNulledField(GARA.OGGETTO) %>
			</td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>">Data di Pubblicazione Lotto</label></th>
			<td>
				<%= PageHelper.getFormattedDate(row.getNulledField(LOTTO.DATA_PUBBLICAZIONE)) %>
			</td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>">Codice Identificativo Stazione Appaltante</label></th>
			<td>
				<%= row.getNulledField(GARA.ID_STAZIONE_APPALTANTE) %>
			</td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_DENOMINAZIONE_STAZIONE_APPALTANTE %>">Denominazione Stazione Appaltante</label></th>
			<td>
				<%= row.getNulledField(GARA.DENOM_STAZIONE_APPALTANTE) %>
			</td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>">Codice Fiscale Amministrazione</label></th>
			<td>
				<%= row.getNulledField(GARA.CF_AMMINISTRAZIONE) %>
			</td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_DENOMINAZIONE_AMMINISTRAZIONE %>">Denominazione Amministrazione</label></th>
			<td>
				<%= row.getNulledField(GARA.DENOM_AMMINISTRAZIONE) %>
			</td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_CPV %>">Categoria e Tipologia di Intervento (CPV)</label></th>
			<td>
				<%= row.getNulledField(CPV.TABLE_NAME) %>
			</td>
		</tr>
		<tr>
			<th><label for="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>">Scelta del Contraente</label></th>
			<td>
				<%= row.getNulledField(SCELTA_CONTRAENTE.TABLE_NAME) %>
			</td>
		</tr>
	</table>
</fieldset>