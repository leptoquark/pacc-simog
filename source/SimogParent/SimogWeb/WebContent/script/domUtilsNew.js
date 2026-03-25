<script type="text/javascript"> 
//<!--
/*******************************************************************************
 * Browser Utility
 */

var isNav4, isNav6, isIE4;
function setBrowser() {  isIE4 = false;  isNav4 = false;  isNav6 = false;  if (navigator.appName.indexOf("Explorer") >= 0 || navigator.appName.indexOf("MSIE") >= 0) {  isIE4 = true;  } else {  isNav4 = true;  isNav6 = true;  } }

setBrowser();


/*******************************************************************************
 * Common Utility
 */
function showElement(id) {  setIdProperty(id, "display", "block"); }

function hideElement(id) {  setIdProperty(id, "display", "none"); }

function ricreaStringa(parametri) { 
	var stringa = ""; 
	if (parametri.length == 0)
		return stringa;
	var stringa = "'"; 
	for(var i=0;i<parametri.length;i++)  { 
		if(parametri[i]!="")  {
			stringa = stringa + parametri[i];
			if(i<(parametri.length-1))  { 
				stringa = stringa + "','";
			}  
		}  
	}  
	stringa = stringa + "'";
	return stringa; 
}

function ricreaArray(parametri,parametri2) {
	var array = new Array(); 
	for(var i=0;i<parametri.length;i++)  { 
		array[i] = parametri[i];
	} 
	var index = parametri.length;
	for(var i=0;i<parametri2.length;i++)  { 
		array[index++] = parametri2[i];
	} 
	return array; 
}

function getRowIndex(prefix) { 
	var idtable = "idTabella"+prefix;
	var table = document.getElementById(idtable);
	var rowList = table.rows;
	var idnext = -1; 
	for(var i=0;i<rowList.length;i++)  {
		var row = rowList.item(i); 
		var id = row.getAttribute("id");
		if(id != null && id != "")  { 
			var idnum =(id.split("row"+prefix))[1];
			if(parseInt(idnum) >= parseInt(idnext))  { 
				idnext = idnum;  
			}  
		} 
	}  
	return idnext; 
}

function setModificato(prefix) {
	var index = 0;
	setFormModified("Modificato"+index);					
}

function cercaIndex(array, index, value) {
	if (index == array.length)
		return - 1;
	if (array[index].value == value)
		return index;
	return cercaIndex(array, (index + 1), value); 
}


function isSelectTag(element){
	if( element == null ) return false;
	return element.tagName.toLowerCase() == 'select';
}


/*******************************************************************************
 * Show Menu Utility
 */

function getStyleBySelector( selector ) {  
	if (!isNav6)  {  
		return null;  
	}  
	var sheetList = document.styleSheets;  
	var ruleList;  
	var i, j;  
	for (i=sheetList.length-1;i >= 0;i--)  {  
		ruleList = sheetList[i].cssRules;  
		for (j=0;j<ruleList.length;j++)  {  
			if (ruleList[j].type == CSSRule.STYLE_RULE && ruleList[j].selectorText == selector)  {  
				return ruleList[j].style;  
			}  
		}  
	}  
	return null; 
}

function getIdProperty(id, property) {  var styleObject = document.getElementById(id);  if (styleObject != null) {  styleObject = styleObject.style;  if (styleObject[property]) {  return styleObject[property];  }  }  styleObject = getStyleBySelector("#" + id);  return (styleObject != null) ? styleObject[property] : null; }
function setIdProperty(id, property, value) {  var styleObject = document.getElementById(id);  if (styleObject != null) {  styleObject = styleObject.style;  styleObject[property] = value;  } }


function showMenu(id) {
	if(document.getElementById(id) != null)  {
		if (getIdProperty(id, "display") != "block")  {
			setIdProperty(id, "display", "block");
			document.images["img"+id].src = "img/minus.gif";
		}  else  {
			setIdProperty(id, "display", "none");
			document.images["img"+id].src = "img/plus.gif";  
		}  
	} 
}



/*******************************************************************************
 * Show Sezione Utility
 */
function showSezioneAggiungi(args,argshidden,prefix) {
	var idsezione = "divAgg"+prefix;
	var idpulsante = "showHide"+prefix+"Button";
	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	showElement(idsezione);
	var pulsante = document.getElementById(idpulsante); 
	pulsante.innerHTML="Annulla"; 
	pulsante.href = "javascript:hideSezioneAggiungi(["+argsString+"],["+argshiddenString+"],'"+prefix+"')"; 
}

function hideSezioneAggiungi(args,argshidden,prefix) {
	var idsezione = "divAgg"+prefix;
	var idpulsante = "showHide"+prefix+"Button";
	var titolo = "Aggiungi "+ prefix;
	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	hideElement(idsezione);
	var pulsante = document.getElementById(idpulsante);
	if( pulsante != null ){
		pulsante.innerHTML = titolo; 
		pulsante.href = "javascript:showSezioneAggiungi(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
		var array = ricreaArray(args,argshidden);
		resetParameters(prefix,array);
		setButtonForAdd(args,argshidden,prefix);
		var modifica = document.getElementById("inModifica"+prefix);
		if(modifica != null){
			modifica.parentNode.removeChild(modifica);
		}
	}
}

function resetParameters(prefix,parametri) {
	var preForReq = "";
	for(var i=0;i<parametri.length;i++)  {
		var element = document.getElementById(preForReq+parametri[i]);
		if(element != null)  {
			element.value = "";
			element.selectedIndex = 0;
		}   
	}
}

function setButtonForAdd(args,argshidden,prefix) { 
	var button = document.getElementById("AddMod"+prefix);
	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden); 
	button.href = "javascript:addRow(["+argsString+"],["+argshiddenString+"],'"+prefix+"')"; 
	button.innerHTML="Aggiungi"; 
}


/*******************************************************************************
 * Modify Utility
 */

function setForModifyRow(idrow, args, argshidden, prefix) {  
	var array = ricreaArray(args, argshidden); 
	resetParameters(prefix, array);  
	var preForReq = "";  
	for (var j = 0; j < args.length; j++) {  
		var idtd = idrow + args[j];  
		var field = document.getElementById(preForReq + args[j]);
		var td = document.getElementById(idtd);
		
		if( td != null &&  field != null ){
			// SELECT
			if( isSelectTag(field) ){	
				var tablevalue = "";  
				if (td.childNodes[0] != null) {
					tablevalue = td.childNodes[0].data; //.trim()
				}
				var optarray = field.options;  
				field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  
			} 
			else { // Tutti gli altri casi
				var tablevalue = "";  
				if (td.childNodes[0] != null) {
					tablevalue = td.childNodes[0].data; //.trim()
				}
				if (field != null) {
					field.value = tablevalue;
				}  
			}
		}
	}  
	
	var button = document.getElementById("AddMod" + prefix);  
	var argsString = ricreaStringa(args);  
	var argshiddenString = ricreaStringa(argshidden);  
	button.href = "javascript:modifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')";  
	button.innerHTML = "Modifica";  
	var idtable = "idTabella" + prefix;  
	var table = document.getElementById(idtable);  
	var inmodifica = document.getElementById("inModifica" + prefix);  
	if (inmodifica == null) {  
		inmodifica = document.createElement("TD");  
		inmodifica.style.display = "none";  
		inmodifica.setAttribute("id", ("inModifica" + prefix), 0);  
		table.appendChild(inmodifica);  
	}  
	inmodifica.value = idrow;  
	showSezioneAggiungi(args, argshidden, prefix); 
	afterSetForModifyRow(idrow, args, argshidden, prefix);
}

function modifyRow(idrow, args, argshidden, prefix) {  
	if (checkBeforeAdd(prefix)) {  
		var row = document.getElementById(idrow);  
		var id = row.getAttribute("id");  
		fillRow(id, args, argshidden, prefix);  
		appendHiddenTyped(row, id, prefix, argshidden, false);  
		setModificato(prefix);  
		hideSezioneAggiungi(args, argshidden, prefix);
		
		afterModifyRow(idrow,args,argshidden,prefix,row);
	}  
}


function fillRow(idrow, args, argshidden, prefix) {
	var preForReq = ""; 
	for (var i = 0; i < args.length; i++) {
		var idtd = idrow + args[i]; 
		var element = document.getElementById(preForReq + args[i]);
		var td = document.getElementById(idtd);
		var contenuto = "";
		
		if( element != null ){
			if( isSelectTag(element) ){							//SELECT
				contenuto = getSelectValue(element, false); 
			} else{												//TEXT
				contenuto = element.value;
			}
		}
			
		if (td.childNodes[0] != null) {  
			td.childNodes[0].data = contenuto;  
		} else { 
			td.appendChild(document.createTextNode(contenuto));  
		}  
	}
}

function getSelectValue(elem, flagValue){  
	var ret = "";  
	if(elem.selectedIndex >=0){  
		var selectedelement = elem.options[elem.selectedIndex];  
		ret = selectedelement.text;  
		if (flagValue == true) {  
			ret = selectedelement.value;  
		}  
	}  
	return ret; 
}

function afterSetForModifyRow(idrow, args, argshidden, prefix) {}
function afterModifyRow(idrow,args,argshidden,prefix,row){}
/*******************************************************************************
 * Delete Utility
 */
function deleteRow(idRow,args,argshidden,prefix) {
	if(confirm("Si sta per cancellare il record. Proseguire?"))  {
		var modifica = document.getElementById("inModifica"+prefix);
		if((modifica != null) && (modifica.value == idRow))  {
			hideSezioneAggiungi(args,argshidden,prefix);
		}  
		var row = document.getElementById(idRow); 
		row.parentNode.removeChild(row); 
		var idnext = document.getElementById("idTabella"+prefix).rows.length-1;
		var idDIVTabella = "DIVTabella"+prefix;  
		var numOfRows = idnext + 1;
		setModificato(prefix); 
	} 
}

/*******************************************************************************
 * addRow Utility
 */

function addRow(args,argshidden,prefix) {
	if(checkBeforeAdd(prefix))  {
		if( validateRadio(prefix) )  
		{
			var idnext=getRowIndex(prefix); 
			var idtable="idTabella"+prefix;
			var table=document.getElementById(idtable); 
			var newid="row"+prefix+(parseInt(idnext)+1);
			var tbody=table.getElementsByTagName("TBODY")[0];
			var row=createRow(newid,args,argshidden,prefix); 
			tbody.appendChild(row);
			appendHiddenTyped(row,newid,prefix,argshidden,true);
			var idDIVTabella="DIVTabella"+prefix;
			var numOfRows=idnext+1;
			hideSezioneAggiungi(args,argshidden,prefix);
			setModificato(prefix); 
			document.getElementById("selected"+prefix).value=0;
			
			afterAddRow(newid,args,argshidden,prefix,row);
		}  
	} 
}



function checkBeforeAdd(prefix){
	return true;
}

function validateRadio(prefix) { 
	return true; 
}

function appendHidden(row,idrow,prefix,parametri,creazione) {
	for(var j = 0;j<parametri.length;j++)  {
		var idElement = idrow+parametri[j];
		var element = document.getElementById(idElement);
		var changevalue;
		var tdvalue = ""; 
		if(element.childNodes[0] != null){
			tdvalue = element.childNodes[0].data;
		}
		changevalue = tdvalue;  
		if(creazione)  { 
			var td = document.createElement("TD"); 
			td.style.display = "none";
			var hidden = document.createElement("INPUT");
			hidden.setAttribute("name",idElement,0);
	    	hidden.setAttribute("id","hidden"+idElement,0);
			hidden.setAttribute("type","hidden",0);
			hidden.value = changevalue; 
			td.appendChild(hidden);
			row.appendChild(td); 
		}  else  {  
			var hidden = document.getElementById("hidden"+idElement); 
			hidden.value = changevalue; 
		}  
	} 
}


function appendHiddenTyped(row,idrow,prefix,parametri,creazione) {
	for(var j = 0;j<parametri.length;j++)  {
		var idElement = idrow+parametri[j];
		var field = document.getElementById(parametri[j]);
		if( field != null ){
			var changevalue = field.value;
			if(creazione)  { 
				var td = document.createElement("TD"); 
				td.style.display = "none";
				var hidden = document.createElement("INPUT");
				hidden.setAttribute("name",idElement,0);
		    	hidden.setAttribute("id","hidden"+idElement,0);
				hidden.setAttribute("type","hidden",0);
				hidden.value = changevalue; 
				td.appendChild(hidden);
				row.appendChild(td); 
			}  else  {  
				var hidden = document.getElementById("hidden"+idElement); 
				hidden.value = changevalue; 
			}
		}  
	} 
}


function createRow(idrow, args, argshidden, prefix) {  
	var row = document.createElement("TR");  
	row.setAttribute("id", idrow, 0);  
	var tdmod = document.createElement("TD");  
	tdmod.className = "hmenu";  
	tdmod.setAttribute("nowrap", "nowrap");  
	var linkmod = document.createElement("A");  
	linkmod.setAttribute("id",idrow + "SetForModifyRow");
	linkmod.appendChild(document.createTextNode("Modifica"));  
	var argsString = ricreaStringa(args);  
	var argshiddenString = ricreaStringa(argshidden);  
	linkmod.setAttribute("href", "javascript:setForModifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  
	tdmod.appendChild(linkmod); 
	tdmod.appendChild(document.createTextNode("\n")); 
	var linkcanc = document.createElement("A");  
	linkcanc.appendChild(document.createTextNode("Cancella"));  
	linkcanc.setAttribute("href", "javascript:deleteRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  
	tdmod.appendChild(linkcanc);  
	row.appendChild(tdmod);  
	var preForReq = "";  
	for (var i = 0; i < args.length; i++) {  
		var td1 = document.createElement("TD");  
		td1.setAttribute("nowrap", "nowrap");  
		td1.setAttribute("id", idrow + args[i]); 
		td1.className = "garaTd";  
		var idtd = idrow + args[i];  
		var element = document.getElementById(preForReq + args[i]);
		var contenuto = "";
		
		if( element != null ){
			if( isSelectTag(element) ){ 						//SELECT
				contenuto = getSelectValue(element, false); 
			} else{												//TEXT
				contenuto = element.value;
			}
		}
		
		td1.appendChild(document.createTextNode(contenuto));  
		row.appendChild(td1);
	}
	return row; 
}

function afterAddRow(newid,args,argshidden,prefix,row){}

/*******************************************************************************
 * AppendRow  Utility
 */
function appendRow(idrow, args, argshidden, prefix, row) {  
	var tdmod = document.createElement("TD");  
	tdmod.className = "hmenu";  
	tdmod.setAttribute("nowrap", "nowrap");
	tdmod.setAttribute("align", "center");
	var linkmod = document.createElement("A");
	linkmod.setAttribute("id",idrow + "SetForModifyRow");
	linkmod.appendChild(document.createTextNode("Modifica"));  
	var argsString = ricreaStringa(args);  
	var argshiddenString = ricreaStringa(argshidden);  
	linkmod.setAttribute("href", "javascript:setForModifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  
	tdmod.appendChild(linkmod); 
	tdmod.appendChild(document.createTextNode("\n")); 
	var linkcanc = document.createElement("A");  
	linkcanc.appendChild(document.createTextNode("Cancella"));  
	linkcanc.setAttribute("href", "javascript:deleteRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  
	tdmod.appendChild(linkcanc);  
	row.appendChild(tdmod);  
	var preForReq = "";  
	for (var i = 0; i < args.length; i++) {  
		var td1 = document.createElement("TD");  
		td1.setAttribute("nowrap", "nowrap");
		td1.setAttribute("id", idrow + args[i]); 
		td1.className = "garaTd";  
		var idtd = idrow + args[i];  
		var element = document.getElementById(preForReq + args[i]);
		var contenuto = "";

		if( element != null ){
			if( isSelectTag(element) ){ 							//SELECT
				contenuto = getSelectValue(element, false); 
			} else{													//TEXT
				contenuto = element.value;
			}
		}
		
		td1.appendChild(document.createTextNode(contenuto));  
		row.appendChild(td1);
	}
	return row; 
}

/*******************************************************************************
 * addRowByArray  Utility
 */

function addRowByArray(args,argshidden,prefix, array) {
	var idnext=getRowIndex(prefix); 
	var idtable="idTabella"+prefix;
	var table=document.getElementById(idtable); 
	var newid="row"+prefix+(parseInt(idnext)+1);
	var tbody=table.getElementsByTagName("TBODY")[0];
	var row=createRowByArray(newid,args,argshidden,prefix,array); 
	tbody.appendChild(row);
	//creo i td hidden con i valori passati dall'array	
	createRowHiddenByArray(row, newid, args,argshidden, array);
	appendHidden(row,newid,prefix,argshidden,true);
	var idDIVTabella="DIVTabella"+prefix;
	var numOfRows=idnext+1;
	hideSezioneAggiungi(args,argshidden,prefix);
	return newid; 
}

function createRowByArray(idrow, args, argshidden, prefix, array) {  
	var row = document.createElement("TR");  
	row.setAttribute("id", idrow, 0);  
	var tdmod = document.createElement("TD");  
	tdmod.className = "hmenu";  
	tdmod.setAttribute("id", idrow + "ActionCol");
	tdmod.setAttribute("nowrap", "nowrap");  
	var linkmod = document.createElement("A");  
	linkmod.setAttribute("id",idrow + "SetForModifyRow");
	linkmod.appendChild(document.createTextNode("Modifica"));  
	var argsString = ricreaStringa(args);  
	var argshiddenString = ricreaStringa(argshidden);  
	linkmod.setAttribute("href", "javascript:setForModifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  
	tdmod.appendChild(linkmod); 
	tdmod.appendChild(document.createTextNode("\n")); 
	var linkcanc = document.createElement("A");  
	linkcanc.appendChild(document.createTextNode("Cancella"));
	linkcanc.setAttribute("id",idrow + "DeleteRow");
	linkcanc.setAttribute("href", "javascript:deleteRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  
	tdmod.appendChild(linkcanc);  
	row.appendChild(tdmod);  
	var preForReq = "";  
	for (var i = 0; i < args.length; i++) {  
		var td1 = document.createElement("TD");  
		//td1.setAttribute("nowrap", "nowrap");  
		td1.setAttribute("id", idrow + args[i]); 
		td1.className = "garaTd";  
		td1.appendChild(document.createTextNode(array[i]));  
		row.appendChild(td1);
	}
	return row; 
}

function createRowHiddenByArray(row, newid, args, argshidden, array) {
	var offset = args.length;
	for(var j=offset; j<argshidden.length; j++) {
	    var td1 = document.createElement("TD");
	    var idtd = newid + argshidden[j];
	    td1.appendChild(document.createTextNode(array[j]));
	    td1.style.display = "none";
	    td1.setAttribute("id",idtd,0);
	    row.appendChild(td1);
	}	
}


/*******************************************************************************
 * PopUp  Utility
 */
function chiudiPopUp() {  
	if (isNav6) {  
		window.opener.focus();  
	} else {  
		window.dialogArguments.Sender.focus();  
	}  
	window.close(); 
}

//-->
</script>