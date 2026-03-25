<script type="text/javascript">
<!--
function showElem(id){
	if(document.getElementById(id) != null){
	   var style = document.getElementById(id).style;
		     if (style["display"] != "block")
        {
	        style["display"] = "block"
	        document.images["img"+id].src = "img/minus.gif";	        
        }
        else
        {
	        style["display"] = "none"
	        document.images["img"+id].src = "img/plus.gif";
        }
   	}
}

//-->
</script>

<% it.avlp.simog.beans.AllValidationBeans newsBean = (it.avlp.simog.beans.AllValidationBeans) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.NEWSBEAN); %>
<% if ( newsBean != null ) { %>
	<% String buffer = "";
		if (newsBean.getSize()> 0){
			buffer = buffer + "<table width=\"100%\"><tr style=\"width: 100%\"><td><div>"+
			"<label class=\"news\" onclick=\"showElem('TAB_NEWS_INFO')\">"+
			"<img src=\"img/minus.gif\" id=\"imgTAB_NEWS_INFO\"/>"+
			"NOVITA' E AVVISI</label></div><br>"; 
			buffer = buffer + "<div id=\"TAB_NEWS_INFO\" class=\"news\" style=\"display: block;\">";
			buffer = buffer + "<ul>";
			for( int i=0; i < newsBean.getSize(); i++) {
				buffer = buffer + "<li>" + newsBean.get(i).getMessage() + "</li>"; 
			} 
			buffer = buffer + "</ul>";
			buffer = buffer + "</div></td></tr></table>";	
		}
	%> 
	<%= buffer %>
<% } %>