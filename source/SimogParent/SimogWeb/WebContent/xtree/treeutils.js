function setSel(id,val,desc=''){
	console.log("valore id:"+ id);
	
  var selection = document.getElementById(id);
  var selectiondesc = document.getElementById(id+"Desc");
  if(selection){
if(selection.value){ 
var old = document.getElementById(selection.value);
if(old){
old.style.color="";
old.style.backgroundColor="";
}}
if(val){selection.value=val;
	if(selectiondesc){
		selectiondesc.value=desc;
	}
}}}

function chiudiPopUp(idDialog){ 
 var divDialog = "#dialog";
 if(idDialog)
        divDialog = "#dialog"+idDialog;
 
   if(!window.showModalDialog) {
		   window.parent.jQuery(divDialog).dialog("close");
           window.parent.jQuery(divDialog).empty();
   } else
	window.close(); 
	

}

function deleteRow(idRow, args, argshidden, prefix) {  if (confirm("Si sta per cancellare il record. Proseguire?")) {  var modifica = document.getElementById("inModifica" + prefix);  if ((modifica != null) && (modifica.value == idRow)) {  hideSezioneAggiungi(args, argshidden, prefix);  }  var row = document.getElementById(idRow);  row.parentNode.removeChild(row);  var idnext = document.getElementById("idTabella" + prefix).rows.length - 1;  var idDIVTabella = "DIVTabella" + prefix;  var numOfRows = idnext + 1;  if ("<%= PSBD.CONTENZIOSO %>" != prefix) {  if (numOfRows >= 3) {  setIdProperty(idDIVTabella, "height", "200px");  } else {  setIdProperty(idDIVTabella, "height", "150px");  }  }  setModificato(prefix);  } }
function setModificato(prefix) {  var index = 0;  if (prefix == "<%= PSBD.RESPONSABILE %>") {  index = 1;  } else if (prefix == "<%= PSBD.REQUISITO %>" || prefix == "<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>") {  index = 3;  } else if (prefix == "<%= PSBD.AGGIUDICATARIO %>") {  index = 2;  } else if (prefix == "<%= PSBD.REQUISITO %>") {  index = 3;  } else if (prefix == "<%= PSBD.PRESTAZIONE %>") {  index = 1;  } else if (prefix == "<%= PSBD.FINANZIAMENTO %>") {  index = 7;  }  setFormModified("Modificato" + index); }
 
function getRowIndex(prefix) {  var idtable = "idTabella" + prefix;  var table = document.getElementById(idtable);  var rowList = table.rows;  var idnext = -1;  for (var i = 0; i < rowList.length; i++) {  var row = rowList.item(i);  var id = row.getAttribute("id");  if (id != null && id != "") {  var idnum = (id.split("row" + prefix))[1];  if (parseInt(idnum) >= parseInt(idnext)) {  idnext = idnum;  }  }  }  return idnext; }
function createRowCpv(idrow, args, argshidden, prefix) {
    var row = document.createElement("tr");
    row.setAttribute("id", idrow, 0);
    var tdmod = document.createElement("td");
    tdmod.className = "hmenu";
    tdmod.setAttribute("nowrap", "nowrap");
    var argsString = ricreaStringa(args);
    var argshiddenString = ricreaStringa(argshidden);
   /* var linkmod = document.createElement("a");
    linkmod.appendChild(document.createTextNode("Modifica"));   
    linkmod.setAttribute("href", "javascript:setForModifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);
    tdmod.appendChild(linkmod);
    var spanElement = document.createElement("span");
    spanElement.innerHTML = "&nbsp;&nbsp;";
    tdmod.appendChild(spanElement);*/
    var linkcanc = document.createElement("a");
    linkcanc.appendChild(document.createTextNode("Cancella"));
    linkcanc.setAttribute("href", "javascript:deleteRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);
    tdmod.appendChild(linkcanc);
    row.appendChild(tdmod);
    var already = false;
    var preForReq = ""; 
        var td1 = document.createElement("td");
        td1.setAttribute("nowrap", "nowrap");
        td1.className = "garaTd";
        var idtd = idrow + args;  
      //  var text = document.getElementById(args[i]).value;
        var input = document.createElement("input");
        input.setAttribute("id", idtd, 0);
        input.setAttribute("type", "text", 0);
        input.setAttribute("maxlength", "1024", 0);
        input.setAttribute("value", args, 0);
        input.disabled = true;
        td1.appendChild(input); 
        row.appendChild(td1);
     
    return row;
}
function ricreaStringa(parametri) {  var stringa = "";  if (parametri.length == 0) return stringa;  var stringa = "'";  for (var i = 0; i < parametri.length; i++) {  if (parametri[i] != "") {  stringa = stringa + parametri[i];  if (i < (parametri.length - 1)) {  stringa = stringa + "','";  }  }  }  stringa = stringa + "'";  return stringa; }

function addRowCpv() {
	var prefix = "Cpv";
	var args = document.getElementById('sel_CPV').value;
	var argshidden = '';
	if(document.getElementById('sel_CPV').value==null || document.getElementById('sel_CPV').value==''){
		alert("Inserire codice CPV");
	} else {  
		var idtable="idTabella"+prefix;
		var idnext=getRowIndex(prefix);  
		var table=document.getElementById(idtable); 
		var newid="row"+prefix+(parseInt(idnext)+1);
		var tbody=table.getElementsByTagName("tbody")[0];
		var row=createRowCpv(newid,args,argshidden,prefix); 
		tbody.appendChild(row);
	//	appendHiddenTyped(row,newid,prefix,argshidden,true);
		var idDIVTabella="DIVTabella"+prefix;
		var numOfRows=idnext+1;
		/*hideSezioneAggiungi(args,argshidden,prefix);
		setModificato(prefix); */
		document.getElementById("selected"+prefix).value=0;
		afterAddRowCpv(newid,args,argshidden,prefix,row);
			   
	} 
}

function afterAddRowCpv(newid,args,argshidden,prefix,row){
	//imposta il parametro numeri di righe
	var counter = $("input[name='<%= ParametriCup.NR_RIGHE_CUP %>']").val();
	counter++;
	$("input[name='<%= ParametriCup.NR_RIGHE_CUP %>']").val(counter);
	//setta le colonne con i valori di default
	var  orbj = $("a[href*='setForModifyRow'][href*='rowCUP']")[0];
	var par = orbj.parentNode;
	par = par.parentNode;
	orbj.parentNode.removeChild(orbj);
	var ftd1 = document.createElement("td");
	ftd1.innerHTML = "No";
	ftd1.className="garaTd";
	par.appendChild(ftd1);		
	var ftd2 = document.createElement("td");
	ftd2.innerHTML = "&nbsp;";
	ftd2.className="garaTd";
	par.appendChild(ftd2);		
	var ftd3 = document.createElement("td");
	ftd3.innerHTML = "&nbsp;";
	ftd3.className="garaTd";
	par.appendChild(ftd3);		
	//impostiamo l'addRow personalizzato (addRowCup)
	var addRowElem = $("#AddMod"+prefix);
 	addRowElem.attr("href",addRowElem.attr("href"));
}
function search(openerFieldId,popFieldId,defaultPopVal){
	
	if(window.showModalDialog) {
		var sender = getSender(window);
		var can = sender.document.getElementById('canSearch');
		if(can && can.value == "true"){
			document.getElementById('pattern_ID').value = sender.document.getElementById(openerFieldId).value;
			sender.document.getElementById(openerFieldId).value="";
			document.getElementById(popFieldId).value=defaultPopVal;
			sender.document.getElementById('canSearch').value = "false";
			document.forms[0].submit();
		}
	} else {
		var can = window.parent.jQuery('#canSearch').val();
		if(can && can.value == "true"){
			document.getElementById('pattern_ID').value = window.parent.jQuery('#'+openerFieldId).val();
			window.parent.jQuery('#'+openerFieldId).val("");
			document.getElementById(popFieldId).value=defaultPopVal;
			window.parent.jQuery('#canSearch').val("false");
			document.forms[0].submit();
		}
		window.parent.jQuery('#dialog').dialog({ title: document.title });
		
	}
}
function setRetVal(popFieldId, noClose, retField, cmdConfDisabled,uid){
    var selValue = '';
    var descValue = '';
    var originalField = popFieldId;
    if(originalField=="CPV_IDDesc")
    	popFieldId = "CPV_ID";
    
    if(popFieldId=="CPVField"){
    	selValue = document.getElementById("CPV_ID").value;
    }else if(document.getElementById(popFieldId)!=null) {
        selValue = document.getElementById(popFieldId).value;
    } else {
         var iframe = document.getElementById('popupiframe');
        var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
        selValue = innerDoc.getElementById(popFieldId).disabled;   
    }

	Set_Cookie( 'ALLVAL', selValue);
  
    
  	if(!window.showModalDialog){
  		if(popFieldId =='id_info') {
          /*  console.log('=====================================');
            console.log('============= START2.0 =================');
            console.log('==== CMD PARAM: '+cmdConfDisabled);
            
            if(document.getElementById('cmdConf')!=null) {
               console.log('==== CMD JS DOCUMENT: '+document.getElementById('cmdConf').disabled );
            } else {
                  var iframe = document.getElementById('popupiframe');
                 var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
                console.log('==== CMD JS IFRAME: '+innerDoc.getElementById('cmdConf').disabled );
            }*/
            
            if(cmdConfDisabled==true) {
           //     console.log('Allegato presente per '+retField+'DESC');
  			   window.parent.jQuery('#'+retField+'DESC')[0].value =  'PRESENTE';
               window.parent.jQuery("#"+retField)[0].value = selValue;
              // console.log('VALORE: '+window.parent.jQuery('#'+retField+'DESC')[0].value);
            } else {
             //   console.log('Allegato non presente per '+retField+'DESC');
  			     window.parent.jQuery('#'+retField+'DESC')[0].value =  '';
                 window.parent.jQuery("#"+retField)[0].value  = '';
               // console.log('VALORE: '+window.parent.jQuery('#'+retField+'DESC')[0].value);
            }
  		} else if(popFieldId=="CPVField")
  				window.parent.jQuery('#'+popFieldId).val(selValue);
  			else {
  		popFieldId = popFieldId.replace("_ID","");
  		popFieldId = "sel_"+popFieldId;
  		window.parent.jQuery('#'+popFieldId).val(selValue);
  		console.log("TECH OUTSIDE");
  		if(originalField=="CPV_IDDesc"){
  			console.log("TECH INSIDE");
  			descValue = document.getElementById(originalField).value;
  			console.log("TECH descValue "+descValue);
  			window.parent.jQuery('#'+popFieldId+"Desc").val(descValue);
  		}
  	}
  	
  	    }
	if (noClose && noClose == true) return;
  	chiudiPopUp(retField);
  	return;
}

function MyDialogArguments() {
	this.Sender = null;
}
       
 
function addChild(id) {
	 var liList = document.getElementById(id).getElementsByTagName("li");
     var countLi = liList.length; 
     countLi++;
  // The element into which appending will be done
     var element = document.querySelector("#"+id);
    
     // The element to be appended
     var child = document.createElement("li"); 
     var html = '<span> CPV secondaria <input type="text" name="cpv_'+ countLi +'" id="sel_CPV'+ countLi+'"><a class="getCPV" href="#"  onclick="apripopup(\'ricercaCPV.jsp?uid='+ countLi +'\', \'sel_CPV\')" ><img src="img/icon_info_sml.gif"></a>';
     
     child.innerHTML = html; 
     // append
     element.appendChild(child);
      
} 

function apripopup(path, fieldId){
	var dialogArgs = new MyDialogArguments();
	dialogArgs.Sender = window;
	
	var windowprops = "dialogWidth: 800px; dialogHeight: 600px; center: 1; scroll: 1; help: 1; status: 0;";
	var retVal = null;
	
	//TB: Ticket risoluzione popup
	if (!window.showModalDialog) {
		/*alert ("il browser non consente l'apertura di PopUp modali, inserire il codice nella pagina principale!")
		return;	*/
		opendialog(path);
	} else {
			retVal = window.showModalDialog(path,dialogArgs,windowprops); 
			retVal = Get_Cookie( 'ALLVAL' );
			Delete_Cookie( 'ALLVAL'); 
			if(fieldId != null && retVal != null) {
				var obj = document.getElementById(fieldId);
				if(retVal != "")
				obj.value = retVal;
				//patch bandi 
				if(fieldId.substr(0,5) == "ALLEG"){
					var campo = fieldId + "DESC";
					if(retVal!=null && retVal!="" && retVal!="null"){
							document.getElementById(campo).value = "PRESENTE";
					} else{
						document.getElementById(fieldId).value = "";
						document.getElementById(campo).value = "";
					}
				} 
			}
	}	
			
		  
	}

function apripopupAllegati(path, fieldId){
	var dialogArgs = new MyDialogArguments();
	dialogArgs.Sender = window;
	
	var windowprops = "dialogWidth: 800px; dialogHeight: 600px; center: 1; scroll: 1; help: 1; status: 0;";
	var retVal = null;
	
	//TB: Ticket risoluzione popup
	if (!window.showModalDialog) {
		/*alert ("il browser non consente l'apertura di PopUp modali, inserire il codice nella pagina principale!")
		return;	*/
     
		opendialogAllegati(path,fieldId);
     
	}
	else {
			retVal = window.showModalDialog(path,dialogArgs,windowprops); 
			retVal = Get_Cookie( 'ALLVAL' );
			Delete_Cookie( 'ALLVAL'); 
			if(fieldId != null && retVal != null) {
				var obj = document.getElementById(fieldId);
				if(retVal != "")
				obj.value = retVal;
				//patch bandi 
				if(fieldId.substr(0,5) == "ALLEG"){
					var campo = fieldId + "DESC";
					if(retVal!=null && retVal!="" && retVal!="null"){
							document.getElementById(campo).value = "PRESENTE";
					}
					else{
						document.getElementById(fieldId).value = "";
						document.getElementById(campo).value = "";
					}
				} 
			}
	}	
			
		  
	}


function opendialog(page, idDialog) {
      var divDialog = '#dialog';
      if(idDialog)
          divDialog = '#dialog'+idDialog;
    
	  var $dialog = $(divDialog)
	  .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
	  .dialog({
	    title: "ANAC: Autorit&agrave; Nazionale Anticorruzione",
	    autoOpen: false,
	    dialogClass: 'no-close',
	    modal: true,
	    height: 700,
	    minWidth: 1100,
	    minHeight: 600,
	    draggable:true,
	    /*close: function () { $(this).remove(); },*/
	   /* buttons: { "Ok": function () {         $(this).dialog("close"); } }*/
	  });
	  $dialog.dialog('open');
	}
    
    
 function opendialogAllegati(page, idDialog) {
      var divDialog = '#dialog';
      if(idDialog)
          divDialog = '#dialog'+idDialog;
    
	  var $dialog = $(divDialog)
	  .html('<iframe id="popupiframe" style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
	  .dialog({
	    title: "ANAC: Autorit&agrave; Nazionale Anticorruzione",
	    autoOpen: false,
	    dialogClass: 'no-close',
	    modal: true,
	    height: 700,
	    minWidth: 1100,
	    minHeight: 600,
	    draggable:true,
	    close: function () { 
            var iframe = document.getElementById('popupiframe');
            var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
            var cmdConf = innerDoc.getElementById('cmdConf').disabled;
            
            setRetVal('id_info',true,idDialog,cmdConf); 
            $(this).empty();
        
        },
	   /* buttons: { "Ok": function () {         $(this).dialog("close"); } }*/
	  });
	  $dialog.dialog('open');
	}


// ???
function checkCR(evt) {
var evt  = (evt) ? evt : ((event) ? event : null);
var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}
// ???
document.onkeypress = checkCR;

function getSender(wnd) {
	if(wnd.dialogArguments)
		return wnd.dialogArguments.Sender;
	else return wnd.opener;
}

function searchLE(patternID, jsp, func){
	  var pattern = document.getElementById(patternID).value;
	    pattern = pattern.toUpperCase();
	    var retCheck = eval(func+"('"+pattern+"');");
	    if(retCheck){
	        document.getElementById('canSearch').value="true";
	        apripopup(jsp, patternID);
	    }   
	}
	
function isNotCPV(value){
	      for(var i=0; i<value.length; i++){
	          if (value.charAt(i) <= 'Z'  && value.charAt(i) >= 'A' )
	         return true;
	    }
		return false;
	}

	function isNotIstat(value){
	      for(var i=0; i<value.length; i++){
	          if (value.charAt(i) < '0'  || value.charAt(i) > '9' )
	         return true;
	    }
		return false;
	}
	
	function isNotNuts(value){
   if (value.charAt(0) == 'I'  || value.charAt(1) == 'T' )
	    return false;
	else
		return true;
	}

	function checkKeyLE(evt, field, jsp, func){
		var evt = (evt) ? evt : ((event) ? event : null);
		var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
		if ((evt.keyCode == 13) && (node.type=="text") && (node.id==field.id))  
		{ 
			searchLE(node.id, jsp, func);
		}
	}

function Get_Cookie( check_name ) {
	var a_all_cookies = document.cookie.split( ';' );
	var a_temp_cookie = '';
	var cookie_name = '';
	var cookie_value = '';
	var b_cookie_found = false; // set boolean t/f default f
	var i = '';
	
	for ( i = 0; i < a_all_cookies.length; i++ )
	{
		a_temp_cookie = a_all_cookies[i].split( '=' );
		cookie_name = a_temp_cookie[0].replace(/^\s+|\s+$/g, '');
	
		if ( cookie_name == check_name )
		{
			b_cookie_found = true;
			if ( a_temp_cookie.length > 1 )
			{
				cookie_value = unescape( a_temp_cookie[1].replace(/^\s+|\s+$/g, '') );
			}
			return cookie_value;
			break;
		}
		a_temp_cookie = null;
		cookie_name = '';
	}
	if ( !b_cookie_found ) 
	{
		return null;
	}
}

function Set_Cookie( name, value, expires, path, domain, secure ) {
	var today = new Date();
	today.setTime( today.getTime() );
	if ( expires )
	{
		expires = expires * 1000 * 60 * 60 * 24;
	}
	var expires_date = new Date( today.getTime() + (expires) );

	document.cookie = name + "=" +escape( value ) +
		( ( expires ) ? ";expires=" + expires_date.toGMTString() : "" ) + //expires.toGMTString()
		( ( path ) ? ";path=" + path : "" ) + 
		( ( domain ) ? ";domain=" + domain : "" ) +
		( ( secure ) ? ";secure" : "" );
}

function Delete_Cookie( name, path, domain ) {
	if ( Get_Cookie( name ) ) document.cookie = name + "=" +
			( ( path ) ? ";path=" + path : "") +
			( ( domain ) ? ";domain=" + domain : "" ) +
			";expires=Thu, 01-Jan-1970 00:00:01 GMT";
}


function callCpvDescPrev(cpvField, retField) {
	var cpvVal = document.getElementById(cpvField).value;
	     if(cpvVal!=null && cpvVal!="") {
				 $.get("SrvLoadCpvSecDesc", {
					 idCPV : cpvVal
		         }, function(responseText) {        
		               if(responseText=="KO")
			               alert("La CPV indicata non e' valida")
			            else
			            	document.getElementById(retField).value = responseText;
		                   
		        });
			}

   }









