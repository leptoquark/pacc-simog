


<%@page import="it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI"%>
<%@page import="it.avlp.simog.db.generated.STATI_ESTERI"%>
<%@page import="java.util.*"%>

<%@page import="it.avlp.simog.util.PageHelper"%>
<tr>
	<th><label>Paese Operatore Economico</label></th>
	<td colspan="3"> 
	<% 
	String id_stato = (String)request.getAttribute(SOGGETTI_PARTECIPANTI.ID_STATO);
	String disabled = "";
	Map<String,String> m = (Map)request.getAttribute(STATI_ESTERI.TABLE_NAME);
	Set<String> set = m.keySet();
	ArrayList<String> l = new ArrayList<String>(set);
	Collections.sort(l);
	//if((id_stato == null || id_stato.equals("")) && m.size() == 1){ disabled = "disabled";} 
	if( m.size() == 1 || flag == "N"){ disabled = "disabled"; } // flag è una variabile esterna a questo pezzo di jsp (radioBoxPaesi.jsp) 
	%>  
	<div id="divText"  style="display: block">
	  <input type="text" readonly id="textPaese" value="Italia">
	</div>
	<div id="divCombo"   style="display: none">
	<select 
			id="<%=SOGGETTI_PARTECIPANTI.ID_STATO %>" 
			name="<%=SOGGETTI_PARTECIPANTI.ID_STATO %>" 
			 >
		<option></option>
		<% 
		for(String s : l){
			//if(((id_stato == null || id_stato.equals("")) && m.size() == 1)
		    if((m.size() == 1) || ((id_stato != null && id_stato.equals((String)m.get(s))) && m.size() > 1)){
				//disabled perche una volta deciso il paese non si puo cambiare
				%><option selected="selected" value="<%= (String)m.get(s)%>"><%=s %></option><% 
			}else{
				%><option value="<%= (String)m.get(s)%>"><%=s %></option><%
			}
		}%>		
	</select>
	</div>
	</td>
</tr>
<script>
	disable('<%=flag %>');
</script>