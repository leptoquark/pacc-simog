function browserType(){var agt=navigator.userAgent.toLowerCase();var is_major = parseInt(navigator.appVersion);var is_minor = parseFloat(navigator.appVersion);var is_nav  = ((agt.indexOf('mozilla')!=-1) && (agt.indexOf('spoofer')==-1)&& (agt.indexOf('compatible') == -1) && (agt.indexOf('opera')==-1)&& (agt.indexOf('webtv')==-1) && (agt.indexOf('hotjava')==-1));var is_gecko = (agt.indexOf('gecko') != -1);if(is_nav || is_gecko) return "mozilla";else return "iexplorer";} 
function focusOnField(element){var cmdFocus = "";var cmdSelect = "";if(browserType() == "mozilla"){if (element.id != "") {cmdFocus = "document.getElementById('"+element.id+"').focus();";cmdSelect = "document.getElementById('"+element.id+"').select();";} else {cmdFocus = "document.getElementsByName('"+element.name+"')[0].focus();";cmdSelect = "document.getElementsByName('"+element.name+"')[0].select();";}setTimeout(cmdFocus,1);setTimeout(cmdSelect,1);}else {element.focus();element.select();}}
function chComboStateOnFieldChange(comboID,radioId){if(document.getElementById(radioId).checked)document.getElementById(comboID).disabled=false;else {document.getElementById(comboID).selectedIndex=0;document.getElementById(comboID).disabled=true;}}
function disComboIfRadio(comboID,radioID){if(document.getElementById(radioID).checked){document.getElementById(comboID).selectedIndex=0;document.getElementById(comboID).disabled=true;} else document.getElementById(comboID).disabled=false;}
function chElemStateOnRadioChange(elemsArray, radioYid) {  for (var i = 0; i < elemsArray.length; i++) {  if (document.getElementById(radioYid).checked == false) {  if(document.getElementById(elemsArray[i]).type.toLowerCase() != "radio")  document.getElementById(elemsArray[i]).value = "";  document.getElementById(elemsArray[i]).disabled = true; } else document.getElementById(elemsArray[i]).disabled = false; } }
function changePage(servletdacaricare,idmodificato) {   var scelta = true;if (document.forms[0].elements[idmodificato].value==1) {scelta = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.dataChanged') : confirm("Attenzione ! I dati potrebbero essere cambiati. Prosegui con il rischio di perdere i dati?")}if(scelta) {document.forms[0].action = servletdacaricare;document.forms[0].submit();}}
function setFormModified(idmodificato) {   if(document.getElementById(idmodificato))document.getElementById(idmodificato).value = 1;}
function validateAndAction(){if(!hasErrors(document.forms[0])){document.forms[0].submit();}}
function doAction(toDo){
var msg = "";var actElem =  document.getElementById('toDo');
if(!actElem)
actElem =  document.getElementById('tipoAzione');
if(toDo=="Salva" || toDo=="save" || toDo == "SalvaGlobale")msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.saveConfirm') : "I dati verranno salvati. Procedere?";
else if(toDo =="loadJspAnnullamento"){var codiceContratto = document.getElementById("CODICE_CONTRATTO");if(codiceContratto==null || codiceContratto.value==""){actElem.value=toDo;document.forms[0].submit();return;}else{msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.multiLottoWarning') : "ATTENZIONE: L'Aggiudicazione fa parte di un contratto multi lotto. Procedendo oltre, la presente Aggiudicazione e tutte le altre Aggiudicazioni partecipanti al contratto saranno svincolate dallo stesso. Si desidera proseguire?";}}
else if(toDo =="salvaBandoGara"){var p = isPubblicazione();if(p==true){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.publishGara') : "ATTENZIONE: la gara verra' pubblicata sul sito informatico dell'Autorita'. Successive modifiche saranno possibili solo mediante avvisi di rettifica. Si vuole procedere?";}}
else if(toDo =="loadJspCancellazione"){var codiceContratto = document.getElementById("CODICE_CONTRATTO");if(codiceContratto==null || codiceContratto.value==""){actElem.value=toDo;document.forms[0].submit();return;}else{msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.multiLottoWarning') : "ATTENZIONE: L'Aggiudicazione fa parte di un contratto multi lotto. Procedendo oltre, la presente Aggiudicazione e tutte le altre Aggiudicazioni partecipanti al contratto saranno svincolate dallo stesso. Si desidera proseguire?";}}
else if(toDo =="riaggiudica"){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.riaggiudicaConfirm') : "Verra creata una scheda di riaggiudicazione. La scheda corrente non sara piu modificabile. Procedere?";
var confirmMsg = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.riaggiudicaConfirm') : confirm(msg);
if(confirmMsg){actElem.value=toDo;document.forms[0].submit();}return;}
else if(toDo =="variazioni_anagrafiche"){actElem.value=toDo;document.forms[0].submit();return;}
else if(toDo =="variazioni_anagrafiche_save"){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.saveConfirm') : "I dati verranno salvati. Procedere?";var confirmMsg = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.saveConfirm') : confirm(msg);
if(confirmMsg){actElem.value=toDo;document.forms[0].submit();}return;}
else if(toDo=="salvaRettificaBando"){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.rectifyConfirm') : "La rettifica sara' confermata. I dati di pubblicazione precedenti saranno modificati. Procedere?";var confirmMsg = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.rectifyConfirm') : confirm(msg);
if(confirmMsg){actElem.value=toDo;document.forms[0].submit();}return;}
else msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.confirmConfirm') : "I dati verranno confermati. Procedere?";
var confirmMsg = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.confirmConfirm') : confirm(msg);
if(confirmMsg){actElem.value=toDo;document.forms[0].submit();}}
function reimpostaForm(toDo){var actElem =  document.getElementById('toDo');if(!actElem)actElem =  document.getElementById('tipoAzione');var confirmMsg = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.changesLost') : confirm("Attenzione! In questo modo tutte le modifiche apportate al documento andranno perse. Continuare?");if(confirmMsg){clearForm(document.forms[0]);actElem.value=toDo;document.forms[0].submit();}}
function clearForm(oForm) {var elements = oForm.elements;oForm.reset();for(i=0; i<elements.length; i++) {field_type = elements[i].type.toLowerCase();switch(field_type) {case "text": case "password": case "textarea":elements[i].value = ""; break;case "radio":case "checkbox":if (elements[i].checked) {elements[i].checked = false; }break;case "select-one":case "select-multi":elements[i].selectedIndex = -1;break;default: break;}}}
function hasErrors(form){var ret = false;for (var i=0;i<form.length;i++){ if(form.elements[i].type=='text' && form.elements[i].style.borderColor=='red red red red'){ret = true;break;}}return ret;}
function checkAndAction(prefix,radioNo, toDo){if(!hasErrors(document.forms[0])){if(validateYNRadios(prefix,radioNo)){doAction(toDo);}}}
function checkAndActionWithTabInfo(prefix,nrRadio, toDo ,tab ){if(!hasErrors(document.forms[0])){if(validateYNRadios(prefix,nrRadio)){document.getElementById('tab').value=tab;doAction(toDo);}}}
function validateYNRadios(prefix,radioNo){var elementY;var elementN;var elementP;for(var i = 1; i <= radioNo; i++){elementY = document.getElementById(prefix+i+'Y');elementN = document.getElementById(prefix+i+'N');elementP = document.getElementById(prefix+i+'P');if(!(elementY.checked || elementN.checked ||(elementP != null && elementP.checked))){if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.selectYesNo'); } else { alert("Selezionare almeno un opzione per tutti i campi Si/No"); }return false;}}return true;}
function deMarkField(field){field.style.borderColor = '';}
function markField(field){field.style.borderColor = 'red';}
function checkDots(field){var dotCount=0;var msg="";for(var i=0;i<field.length;i++) {if (field.charAt(i)==',')dotCount++;}if (dotCount >1) {msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.decimalPart') : "Errore: Il campo puo' contenere solo una parte decimale."; } else msg="-1";return msg;}
function addMyDotsFromCommaString(field){return addDots(field.substring(0,field.indexOf(',')))+field.substring(field.indexOf(','));}
function checkDotsNotCommas(field){var msg="";var commaPosition = field.indexOf(',');var toProcess = "";var endsWithCommas = "";var bool=true;if(commaPosition != -1){endsWithCommas = field.substring(commaPosition);toProcess = field.substring(0,commaPosition);dotPosition = field.indexOf('.');if(dotPosition != -1){bool = checkDotsPosition(toProcess);if(!bool){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.thousandsFormat') : 'Errore: I punti delle migliaia non sono correttamente posizionati, controllare.'+toProcess;}if(bool) msg="1";}else{msg="0";}}else{toProcess = field;dotPosition = field.indexOf('.');if(dotPosition != -1){bool = checkDotsPosition(toProcess);if(!bool){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.thousandsFormat') : 'Errore: I punti delle migliaia non sono correttamente posizionati, controllare.'+toProcess;}if(bool) msg="1";}else{msg="0";}}return msg;}
function checkDotsPosition(stringa){if(stringa.indexOf('..') != -1) return false;if(stringa.length > 3){if(stringa.charAt(stringa.length-4) == "."){return true && checkDotsPosition(stringa.substring(0,stringa.length-4));}else{return false;}}else{return true;}}
function addDots(stringa){var toProcess = stringa;if(toProcess.length > 3){return addDots(toProcess.substring(0,toProcess.length-3))+"."+toProcess.substring(toProcess.length-3);}else{return toProcess;}}
function checkValidDot(element,decimal){var hint = '\n(Specificare la parte decimale o eliminare il punto)';var msg="";if(element.charAt(element.length-1) == ","){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.invalidValue') : 'Errore: Il campo contiene un valore non valido.';if(decimal){msg=msg+ +hint;}}if(msg=="" && element.charAt(0) == ","){if(element.length == 1){msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.invalidValue') : 'Errore: Il campo contiene un valore non valido.';}else{element.value = "0"+element.value;}}return msg;}
function setDecimals(fieldValue,decimals){var retVal = fieldValue;if(retVal == "") return retVal;if(retVal.indexOf(',') == 0) retVal = "0" + retVal;if(retVal.indexOf(',') == -1) retVal = retVal + ",";var inizio = retVal.substring(retVal.indexOf(',')+1).length;for(var i = inizio;i < decimals; i++){retVal = retVal + "0";}return retVal;}
function checkDecimals(element,maxDecimals){var numDec = 0;var msg="";virg=element.indexOf(',');if(virg>=0) numDec = ((element.length-1) - virg);if (numDec > maxDecimals) {msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.decimalsAllowed', {maxDecimals: maxDecimals}) : "Errore: il numero di decimali ammessi e' "+maxDecimals;}else{var times = maxDecimals - numDec;element.value = setDecimals(element,times);}return msg;}
function checkEntirePart(element,maxNumber){var msg="";if ((element.indexOf(',') == -1 && element.length > maxNumber) || (element.indexOf(',') > maxNumber)) {msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.numberTooLarge') : "Errore: Il numero inserito e' troppo grande";}return msg;}
function checkAcceptableNumber(fieldValue,allowedChars){var found=0,i=0;var msg="";for (i=0;i<fieldValue.length;i++) { if (allowedChars.indexOf(fieldValue.charAt(i)) == -1) { msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.invalidChars') : 'Errore: Il campo contiene caratteri non validi.';break;}}return msg;}
function validateNumber(element){var fieldValue = element.value;var msg = "";var good=true;msg = checkPositive(fieldValue);if(msg==""){var allowedChars="1234567890";msg=checkAcceptableNumber(fieldValue,allowedChars);}if (msg != ""){markField(element);alert(msg);focusOnField(element);good=false;}else deMarkField(element);return good;}
function validateAmount(element) {return intValidateAmount(element, 3);}
function validateAmount2Dots(element) {return intValidateAmount(element, 2);}


function intValidateAmount(element, decim) {var fieldValue = element.value;var msg = "";var good=true;var dotNumber=0;var dotPosition=0;msg = checkPositive(fieldValue);if (msg=="") {var allowedChars=".,1234567890";msg=checkAcceptableNumber(fieldValue,allowedChars);}if(msg=="") {msg = checkDots(fieldValue);if (!isNaN(msg)) {dotNumber=parseInt(msg); msg="";}}if(msg=="") {msg = checkDotsNotCommas(fieldValue);if( !isNaN(msg)) {dotPosition=parseInt(msg); msg="";}}if(msg=="") msg = checkValidDot(fieldValue,true); if(msg=="") msg = checkEntirePart(fieldValue,15);if(msg=="") msg = checkDecimals(fieldValue,decim);if(msg=="" && dotNumber == 1){if(dotPosition == 0){if(fieldValue.indexOf(',') != -1){fieldValue = addDots(fieldValue.substring(0,fieldValue.indexOf(',')))+fieldValue.substring(fieldValue.indexOf(','));}else{fieldValue = addDots(fieldValue);}}}if(msg=="" && dotNumber == -1){if(dotPosition == 0){if(fieldValue.indexOf(',') != -1){fieldValue = addDots(fieldValue.substring(0,fieldValue.indexOf(',')))+fieldValue.substring(fieldValue.indexOf(','));}else{fieldValue = addDots(fieldValue);}}element.value = setDecimals(fieldValue,decim);}if (msg != ""){markField(element);alert(msg);focusOnField(element);good=false;}else{deMarkField(element);}return good;}
function validatePercentage(element, noSup) {  var msg = "";  var good = true;  var lfloat = new Number(0);  var fieldValue = element.value;  var fValue = element.value.trim().replace(/,/g, ".");  if (fValue.length > 0) {  if (isNaN(fValue)) msg = "*";  if (msg == "") {  lfloat = parseFloat(fValue);  }  if (msg == "") {  if (lfloat < 0 || lfloat > 100){  if(noSup)  msg = "";  else  msg = "*";  }  }  if (msg == "") {  msg = checkDecimals(fieldValue, 5);  }  if (msg == "") {  msg = checkEntirePart(fieldValue, 3);  }  }  if (msg == "*") msg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.percentageInvalid') : "Valore percentuale non corretto";  if (msg != "") {  markField(element);  if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert(msg); } else { alert(msg); }  focusOnField(element);  good = false;  } else {  element.value = setDecimals(fieldValue, 5);  deMarkField(element);  }  return good; }
function somma1(){var primo;var secondo;var terzo;var field;if(document.getElementById('1').value == ""){primo = 0;}else{primo = parseFloat(document.getElementById('1').value.replace(/\./g,"").replace(',','.'));}if(document.getElementById('2').value == ""){secondo = 0;}else{secondo = parseFloat(document.getElementById('2').value.replace(/\./g,"").replace(',','.'));}if(document.getElementById('3').value == ""){terzo = 0;}else{terzo = parseFloat(document.getElementById('3').value.replace(/\./g,"").replace(',','.'));}field = parseFloat(primo + secondo + terzo).toFixed(3);document.FormCollaudo.sub.value = addMyDotsFromCommaString(field.replace('.',','));return true;}

function somma2(){
var quarto;
var quinto;
var sub = parseFloat(document.FormCollaudo.sub.value.replace(/\./g,"").replace(',','.'));
var field;
if(document.getElementById('4').value == ""){
quarto = 0;
}
else{
quarto = parseFloat(document.getElementById('4').value.replace(/\./g,"").replace(',','.'));
}
if(document.getElementById('5').value == ""){
quinto = 0;
}
else{
quinto = parseFloat(document.getElementById('5').value.replace(/\./g,"").replace(',','.'));
}

field = parseFloat(quarto + quinto + sub).toFixed(3);

document.FormCollaudo.sub2.value = addMyDotsFromCommaString(field.replace('.',','));
return true;
}

function somma3(){
var sesto;
var sub2 = parseFloat(document.FormCollaudo.sub2.value.replace(/\./g,"").replace(',','.'));
var field;
if(document.getElementById('6').value == ""){
sesto = 0;
}
else{
sesto = parseFloat(document.getElementById('6').value.replace(/\./g,"").replace(',','.'));
}
field = parseFloat(sesto + sub2).toFixed(3);

document.FormCollaudo.finale.value = addMyDotsFromCommaString(field.replace('.',','));
return true;
}

function checkPositive(fieldValue){
var msg = "";
if(fieldValue.length > 0){
if(fieldValue.charAt(0) == '-'){
 msg = "Errore: il valore non puo' essere negativo.";
}}
return msg;
}

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
}}}

function goToSelectedResource(combo) {
var pick = combo.options[combo.selectedIndex].value;
if (pick != "") 
window.location.replace(pick);
}

function visualizza(url){
 var finestra = window.open(url,"window","scrollbars=1,width=550,height=250,left=240,top=180");
}

function confirmAction(azione){   
var confirmMsg = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.confirmOperation') : confirm("Si vuole confermare l'operazione ?");
if (confirmMsg) {
window.location.href=azione;
}}