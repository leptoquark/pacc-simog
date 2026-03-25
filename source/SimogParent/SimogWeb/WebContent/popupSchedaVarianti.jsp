<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="../errore.jsp"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- MEV 34191 PICCA 3.04.8 nuova popup  -->
<html>
<head>

<%@ include file="/script/domUtilsNew.js"%>

<%@ include file="../include/gestisciErrore.inc"%>
<%@ include file="../include/basicHeader.inc"%>
<%@ include file="../include/controlloSessione.inc"%>

<title><%= request.getParameter("titlePopup") %></title>
<base target="_self" />

<script type="text/javascript" src="script/pageutils.js"></script>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>


</head>


<body>
	<c:set var="utenteBean" scope="page" value="${sessionScope['UTENTE']}"></c:set>

	<p>
		Si ricorda che per i contratti pubblici di importo superiore alla
		soglia comunitaria, se la variante in corso d&acuteopera eccede il 10%
		dell&acuteimporto originario del contratto, e&acute necessario fornire il link &quotURL documentazione varianti in corso d&acuteopera&quot.
	</p>
	
	</div>
	</td>
	</tr>
	</table>
	</div>




</body>
</html>
