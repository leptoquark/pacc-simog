<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="/include/basicHeader.inc" %>
<%@ include file="/include/controlloSessione.inc" %>
<link rel="stylesheet" href="theme/stile.css"/>
<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>


<%@ include file="include/basicHeader.inc" %>


<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="xtree/treeutils.js"></script>

<script type="text/javascript" src="xtree/xtree2.js"></script>
<script type="text/javascript" src="xtree/xmlextras.js"></script>
<script type="text/javascript" src="xtree/xloadtree2.js"></script>
<link type="text/css" rel="stylesheet" href="xtree/xtree2.css" />
<script type="text/javascript">

function selectRow(row){
    var dest =  document.getElementById('ISTAT_ID');
	var oldId = dest.value;
	if(oldId.length == 6){
		old = document.getElementById(oldId);
		if(old){
			old.style.color="";
			old.style.backgroundColor="";
		}
	}
	row.style.color="white";
	row.style.backgroundColor="blue";
    dest.value = row.id;
}
</script>


<title><utils:message key="ricerca.ricercaISTAT" /></title>
<base target="_self">
</head>

<body onload="search('sel_ISTAT','ISTAT_ID')">

		<form id="IdFormPopup" action="<%=ParametriServlet.SRV_RICERCA_ISTAT%>"  method="post">
		<div class="bodypage-e">
		<fieldset style="margin: 2px;padding: 2px;" > 
		<fieldset style="margin: 2px;padding: 2px">
				<legend><utils:message key="ricerca.elencoISTAT" /></legend>
		<div style="overflow: scroll; height: 160px;width: 560px;margin: 0px;padding: 0px" >
		 <script type="text/javascript">

			// modifica configurazione base
			webFXTreeConfig.rootIcon			= "xtree/images/folder.png";
			webFXTreeConfig.openRootIcon		= "xtree/images/openfolder.png";
			webFXTreeConfig.folderIcon			= "xtree/images/folder.png";
			webFXTreeConfig.openFolderIcon	= "xtree/images/openfolder.png";
			webFXTreeConfig.fileIcon			= "xtree/images/file.png";
			webFXTreeConfig.lMinusIcon			= "xtree/images/Lminus.png";
			webFXTreeConfig.lPlusIcon			= "xtree/images/Lplus.png";
			webFXTreeConfig.tMinusIcon			= "xtree/images/Tminus.png";
			webFXTreeConfig.tPlusIcon			= "xtree/images/Tplus.png";
			webFXTreeConfig.iIcon				= "xtree/images/I.png";
			webFXTreeConfig.lIcon				= "xtree/images/L.png";
			webFXTreeConfig.tIcon				= "xtree/images/T.png";
			webFXTreeConfig.plusIcon			= "xtree/images/plus.png";
			webFXTreeConfig.minusIcon			= "xtree/images/minus.png";
			webFXTreeConfig.blankIcon			= "xtree/images/blank.png";
			webFXTreeConfig.loadingIcon 		= "xtree/images/loading.gif";
		 	
			var tree = new WebFXLoadTree("ISTAT", "<%= request.getContextPath() %>/ricercaIstat?idIstat=0000000000");  
			// document.write(tree);
			tree.write();
		</script>
		</div>
		</fieldset>
		 
			<!--  <fieldset style="margin: 2px;padding: 2px"> -->
			<fieldset>
				<legend><utils:message key="ricerca.ricercaISTAT" /></legend>
				
				
				
				
				<div>

					<table>
					<tr>	
					        <input type="hidden" name="idIstat" value="0000000000"  id=""/>
						<td ><fieldset style="margin: 2px;padding: 2px;"><input type="text" name="pattern" value="<u:requestParameter property="pattern"/>"  id="pattern_ID"/>
		                <input type="submit" value="<utils:message key="button.cerca" plain="true" />" /></fieldset> </td>
					</tr>
					 <jsp:useBean id="ISTAT_LIST" scope="session" class="java.util.LinkedList" type="java.util.LinkedList"/>
					 <tr>  
						<td align="left">
						<div style="overflow: auto;height: 155px;width: 565px;margin: 0px;padding: 0px">
						<table width="100%">
						<c:forEach items="${ISTAT_LIST}" var="istat">
						   <tr  onclick="selectRow(this);" id="<c:out value='${istat.idComune}'/>">	 
							   <td width="15%"><c:out value="${istat.idComune}"></c:out> </td>
							   <td> <c:out value="${istat.denomComune}"></c:out></td>
						   </tr>
						</c:forEach>
						
						
						
						
						</table></div>
						<%session.removeAttribute("ISTAT_LIST"); %> 
						</td>
					</tr>

						</table>
			</div>
		
	</fieldset>
		
		
		<div align="left" style="margin: 2px;padding: 2px">
			<utils:message key="ricerca.istatSelezionato" />: <input type="text" name="ISTAT_NAME" value="" maxlength="10" id="ISTAT_ID" readonly="readonly"/>
		<input type="button" value="<utils:message key="button.conferma" plain="true" />" onclick="setRetVal('ISTAT_ID')" />
		<input type="button" value="<utils:message key="button.annulla" plain="true" />" onclick="chiudiPopUp()" />
		</div>
		
		
			
			  
			</fieldset>



 </div>
 </form>	

</body>
</html>
	