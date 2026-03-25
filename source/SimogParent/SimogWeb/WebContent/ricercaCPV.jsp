<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="/include/basicHeader.inc" %>
<%@ include file="/include/controlloSessione.inc" %>
<link rel="stylesheet" href="theme/stile.css"/>
<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>

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
   var dest =  document.getElementById('CPV_ID');
	var oldId = dest.value;
	if(oldId.length == 10){
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

<title><utils:message key="ricerca.ricercaCPV" /></title>
<base target="_self">

<% String nameField = (String)request.getParameter("nameField"); 

   boolean cpvSecondarie = "CPVField".equals(nameField);
%>

</head>

<body onload="search('sel_CPV','CPV_ID')">
	<form id="IdFormPopup" action="<%=ParametriServlet.SRV_RICERCA_CPV%>"  method="post">
	<div class="bodypage-e">
	<fieldset style="margin: 2px;padding: 2px;" > 
	<fieldset style="margin: 2px;padding: 2px">
	<legend><utils:message key="ricerca.elencoCPV" /></legend>
	<div style="overflow: scroll; height: 250px;width: 100%;margin: 0px;padding: 0px;" >
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
		var tree = new WebFXLoadTree("CPV", "<%= request.getContextPath() %>/ricercaCPV?idCpv=00000000");  
		//document.write(tree);
		tree.write();
	</script>
	</div>
	</fieldset>	 
	<fieldset style="margin: 2px;padding: 2px">
	<legend><utils:message key="ricerca.ricercaCPV" /></legend>
	<div>
		<table>
			<tr>	
				<input type="hidden" name="idCpv" value="00000000"  id=""/>
				<td ><fieldset style="margin: 2px;padding: 2px;"><input type="text" name="pattern" value="<u:requestParameter property="pattern"/>"  id="pattern_ID"/>
            <input type="submit" value="<utils:message key="button.cerca" plain="true" />" /></fieldset> </td>
			</tr>
			<jsp:useBean id="CPV_LIST" scope="session" class="java.util.ArrayList" />
			 <tr>  
				<td align="left">
					<div style="overflow: auto;height: 155px;width: 700px;margin: 0px;padding: 0px">
					<table width="100%">
						<c:forEach items="${CPV_LIST}" var="cpv">
						   <tr  onclick="selectRow(this);" id="<c:out value='${cpv.idDiv}${cpv.idGrp}${cpv.idCls}${cpv.idCtg}${cpv.idVox}-${cpv.check}'/>">	 
							   <td width="15%"><c:out value="${cpv.idDiv}${cpv.idGrp}${cpv.idCls}${cpv.idCtg}${cpv.idVox}-${cpv.check}"></c:out> </td>
							   <td> <c:out value="${cpv.descr}"></c:out></td>
						   </tr>
						</c:forEach>
					</table></div>
				<%session.removeAttribute("CPV_LIST"); %>
				</td>
			</tr>
		</table>
	</div>
	</fieldset>

	<div align="left" style="margin: 2px;padding: 2px">
			<utils:message key="ricerca.cpvSelezionato" />: <input type="text" name="CPV_NAME" value="" maxlength="10" id="CPV_ID" readonly="readonly"/>
			<input type="button" value="<utils:message key="button.conferma" plain="true" />" onclick="setRetVal('<%= cpvSecondarie ? "CPVField" : "CPV_IDDesc" %>')" />
			<input type="button" value="<utils:message key="button.annulla" plain="true" />" onclick="chiudiPopUp()" />
			<input type="text" style="display:none;" name="CPV_NAMEDESC" value="" id="CPV_IDDesc" />
	</div>
	</fieldset> 
 	</form>	
</body>
</html>
	