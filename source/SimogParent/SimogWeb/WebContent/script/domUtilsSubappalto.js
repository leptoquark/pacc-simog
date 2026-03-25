<script type = "text/javascript">
function setIdProperty(id, property, value) {  var styleObject = document.getElementById(id);  if (styleObject != null) {  styleObject = styleObject.style;  styleObject[property] = value;  } }
function showElement(id) {  setIdProperty(id, "display", "block"); }
function hideElement(id) {  setIdProperty(id, "display", "none"); }
function modAnagAgg(){ apriPopUpMod('rubrica','<%= ParametriServletSubappalti.TAB_SUBAFFIDATARIO %>',document.getElementById('<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE%>'),document.getElementById('<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG%>'),'Rubrica Operatori Economici',document.getElementById('<%= PSBD.FIELD_NAME_ANAGOE %>').value); }
function ricreaStringa(parametri) {  var stringa = "";  if (parametri.length == 0) return stringa;  var stringa = "'";  for (var i = 0; i < parametri.length; i++) {  if (parametri[i] != "") {  stringa = stringa + parametri[i];  if (i < (parametri.length - 1)) {  stringa = stringa + "','";  }  }  }  stringa = stringa + "'";  return stringa; }
function ricreaArray(parametri, parametri2) {  var array = new Array();  for (var i = 0; i < parametri.length; i++) {  array[i] = parametri[i];  }  var index = parametri.length;  for (var i = 0; i < parametri2.length; i++) {  array[index++] = parametri2[i];  }  return array; }
function createRow(idrow, args, argshidden, prefix) {
    var row = document.createElement("tr");
    console.log("idrow:"+idrow);
    row.setAttribute("id", idrow, 0);
    var tdmod = document.createElement("td");
    tdmod.className = "hmenu";
    tdmod.setAttribute("nowrap", "nowrap");
    var linkmod = document.createElement("A");
    linkmod.appendChild(document.createTextNode("Modifica"));
    var argsString = ricreaStringa(args);
    var argshiddenString = ricreaStringa(argshidden);
    linkmod.setAttribute("href", "javascript:setForModifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);
    tdmod.appendChild(linkmod);
    var spanElement = document.createElement("span");
    spanElement.innerHTML = "&nbsp;&nbsp;";
    tdmod.appendChild(spanElement);
    var linkcanc = document.createElement("a");
    linkcanc.appendChild(document.createTextNode("Cancella"));
    linkcanc.setAttribute("href", "javascript:deleteRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);
    tdmod.appendChild(linkcanc); 
    row.appendChild(tdmod); 
    var already = false;
    var preForReq = "";
    
    for (var i = 0; i < args.length; i++) {
        var td1 = document.createElement("td");
        td1.setAttribute("nowrap", "nowrap");
        td1.className = "garaTd";
        var idtd = idrow + args[i];

        var element = document.getElementById(preForReq + args[i]);
        console.log("element demo:"+element);
        if ( "<%= PSBD.FIELD_NAME_AGG_RUOLO %>" == args[i] || "<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[i] || "<%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" == args[i] || "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>" == args[i] ) {
            if ("<%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" == args[i]) {
                element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_ID_CATEGORIA %>");
            }
            var contenuto = "";
            if ("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[i]) {
                contenuto = getSelectValue(element, true);
            } else {
                contenuto = getSelectValue(element, false);
            }
            td1.appendChild(document.createTextNode(contenuto));
            td1.setAttribute("id", idtd, 0);
        } else if ("<%= PSBD.FIELD_NAME_AGG_TIPO %>" == args[i]) {
            var idElement = document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>").value;
            var textElement = "";
            textElement = getTipoAggiudicatario(idElement);
            td1.appendChild(document.createTextNode(textElement));
            td1.setAttribute("id", idtd, 0);
        } else if ("<%= PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" == args[i]) {
            var text = document.getElementById(args[i]).value;
            var input = document.createElement("INPUT");
            input.setAttribute("id", idtd, 0);
            input.setAttribute("type", "text", 0);
            input.setAttribute("maxlength", "1024", 0);
            input.setAttribute("value", text, 0);
            input.disabled = true;
            td1.appendChild(input);
        } else if ("<%= PSBD.FIELD_NAME_MOTIVAZIONE %>" == args[i]) {
            var text = document.getElementById(args[i]).value;
            var input = document.createElement("TEXTAREA");
            input.setAttribute("id", idtd, 0);
            input.setAttribute("rows", "2", 0);
            input.setAttribute("cols", "40", 0);
            input.value = text;
            input.disabled = true;
            td1.appendChild(input);
        } else {
            if ("<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" == args[i]) {
                var testo = document.getElementById(preForReq + args[i]).value;
                if (testo == "0") {
                    testo = "";
                }
                td1.appendChild(document.createTextNode(testo));
                td1.setAttribute("id", idtd, 0);
            } else {
                td1.appendChild(document.createTextNode(document.getElementById(preForReq + args[i]).value));
                td1.setAttribute("id", idtd, 0);
            }
        }
        row.appendChild(td1);
    } 
    if (prefix == "<%=PSBD.SUBAFFIDATARIO%>") {
        var tdmod = document.createElement("td");
        tdmod.className = "hmenu";
        tdmod.setAttribute("nowrap", "nowrap");
         tdmod.setAttribute("id", idrow + "<%=PSBD.FIELD_NAME_AGG_PARAMETRI_GRUPPI%>", 0); {
            var argsString = ricreaStringa(args);
            var argshiddenString = ricreaStringa(argshidden);
            var parametri = "<%= PSBD.ID_TABELLA_AFFIDATARI %>=" + idrow + "&<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>").value + "&<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>=" + document.getElementById("<%= ParametriServletSubappalti.FIELD_NAME_CF_DITTA %>").value + "&<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>").value + "&<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>").value;
             linkmod.setAttribute("href", "javascript:apriPopUpRubricaDittaAusiliaria('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "','rubricaRaggruppamento','<%= ParametriServletSubappalti.TAB_SUBAFFIDATARIO  %>','Rubrica Raggruppamento Impresa','" + parametri + "')");
            tdmod.appendChild(linkmod);
        }
        row.appendChild(tdmod);
        var tdaux = document.createElement("td");
        tdaux.style.display = "none";
        var idaux = idrow + "<%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>";
        var hidden = document.createElement("INPUT");
        hidden.setAttribute("name", idaux, 0);
        hidden.setAttribute("id", "hidden" + idaux, 0);
        hidden.setAttribute("type", "hidden", 0);
        hidden.value = "";
        tdaux.appendChild(hidden);
        row.appendChild(tdaux);
    } 
    for (var i = 0; i < argshidden.length; i++) {
        var td1 = document.createElement("td");
        var idtd = idrow + argshidden[i];
        var element = document.getElementById(preForReq + argshidden[i]);
        if ("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" == argshidden[i]) { 
        	selector = document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>");
            var descrizione = selector[selector.selectedIndex].value;
            td1.appendChild(document.createTextNode(descrizione));
        } else if ("<%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>" == argshidden[i]) {
            element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>");
            var idruolo = getSelectValue(element, true);
            td1.appendChild(document.createTextNode(idruolo));
        } else if ("<%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" == argshidden[i]) {
            element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>");
            var idruolo = getSelectValue(element, true);
            td1.appendChild(document.createTextNode(idruolo));
        } else {
            if (element != null) {
                td1.appendChild(document.createTextNode(element.value));
            }
        }
        td1.style.display = "none";
        td1.setAttribute("id", idtd, 0);
        row.appendChild(td1);
    }
    return row;
}
function modifyRow(idrow, args, argshidden, prefix) {  if (document.getElementById("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI%>").selectedIndex <= 0) {  alert("E' necessario scegliere un soggetto per poter proseguire.");  } else if (prefix == "<%= PSBD.SUBAFFIDATARIO %>" && document.getElementById("<%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>").value!='' && document.getElementById("<%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>").value!=''){ alert("Sono stati indicati sia Ribasso aggiudicazione che Offerta in aumento."); } else if (prefix == "<%= PSBD.SUBAFFIDATARIO %>" && document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG%>").selectedIndex == 0){ alert("SIMOG_VALIDAZIONE_104 - Tipologia del soggetto aggiudicatario/affidatario: selezionare un valore tra quelli previsti."); }  }
function showSezioneAggiungi(args, argshidden, prefix) {   var idsezione = "divAgg" + prefix;    var argsString = ricreaStringa(args);  var argshiddenString = ricreaStringa(argshidden);  showElement(idsezione);  }
function hideSezioneAggiungi(args, argshidden, prefix) {  var idsezione = "divAgg" + prefix;  var idpulsante = "showHide" + prefix + "Button";  var titolo = "Aggiungi " + prefix;    var argsString = ricreaStringa(args);  var argshiddenString = ricreaStringa(argshidden);  hideElement(idsezione);  var pulsante = document.getElementById(idpulsante);  pulsante.innerHTML = titolo;  pulsante.href = "javascript:showSezioneAggiungi([" + argsString + "],[" + argshiddenString + "],'" + prefix + "')";  var array = ricreaArray(args, argshidden);  resetParameters(prefix, array);  setButtonForAdd(args, argshidden, prefix);  if (prefix == "<%= PSBD.RESPONSABILE %>" || prefix == "<%= PSBD.SUBAFFIDATARIO %>" ) {  resetFormModified("selected" + prefix);  }  var modifica = document.getElementById("inModifica" + prefix);  if (modifica != null) modifica.parentNode.removeChild(modifica); }
function MyDialogArguments() {  this.Sender = null; }
function apriPopUpRubrica(url, tabName, titlePopup) {
    var paramsString = "?" + "<%=PSBD.TAB%>=" + tabName + "&titleRubrica=" + titlePopup + "&<%=ParametriServletRubrica.OPERAZIONE%>=" + "Cerca in rubrica" + "&from=menu";
    dialogArgs = new MyDialogArguments();
    dialogArgs.Sender = window;
    if (!window.showModalDialog) {
        var windowprops = "width=800,height=600, chrome,centerscreen,dependent=yes,resizable=yes,scrollbars=yes,location=no,status=no,directories=no,menubar=no,toolbar=no,modal=yes,dialog=yes";
        popup = window.open(url + paramsString, dialogArgs, windowprops);
        popup.focus();
    } else {
        var windowprops = "dialogWidth: 800px; dialogHeight: 600px; center: 1; scroll: 1; help: 1; status: 0;";
        popup = window.showModalDialog(url + paramsString, dialogArgs, windowprops);
    }
}
function deleteRow(idRow, args, argshidden, prefix) {  if (confirm("Si sta per cancellare il record. Proseguire?")) {  var modifica = document.getElementById("inModifica" + prefix);  if ((modifica != null) && (modifica.value == idRow)) {  hideSezioneAggiungi(args, argshidden, prefix);  }  var row = document.getElementById(idRow);  row.parentNode.removeChild(row);  var idnext = document.getElementById("idTabella" + prefix).rows.length - 1;  var idDIVTabella = "DIVTabella" + prefix;  var numOfRows = idnext + 1;  if ("<%= PSBD.CONTENZIOSO %>" != prefix) {  if (numOfRows >= 3) {  setIdProperty(idDIVTabella, "height", "200px");  } else {  setIdProperty(idDIVTabella, "height", "150px");  }  }  setModificato(prefix);  } }
function getArray(prefix, isCheck) {  var arr = new Array();  if ("<%= PSBD.SUBAFFIDATARIO %>" == prefix) {  arr[0] = "<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>";  arr[1] = "<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>";  if (!isCheck) {  arr[2] = "<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>";  arr[3] = "<%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>";  arr[4] = "<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>";  arr[5] = "<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>";  arr[6] = "<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>";  arr[7] = "<%= PSBD.FIELD_NAME_AGG_TIPO %>";  arr[8] = "<%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>";  arr[9] = "<%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>";  arr[10] = "<%= PSBD.FIELD_NAME_ANAGOE %>";  }  }  else if (("<%= PSBD.DITTA_RAGGRUPPAMENTO %>" == prefix)) {  arr[0] = "<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>";  arr[1] = "<%= PSBD.FIELD_NAME_ANAGOE %>";  }  return arr; }
function getRowIndex(prefix) {  var idtable = "idTabella" + prefix;  var table = document.getElementById(idtable);  var rowList = table.rows;  var idnext = -1;  for (var i = 0; i < rowList.length; i++) {  var row = rowList.item(i);  var id = row.getAttribute("id");  if (id != null && id != "") {  var idnum = (id.split("row" + prefix))[1];  if (parseInt(idnum) >= parseInt(idnext)) {  idnext = idnum;  }  }  }  return idnext; }
function checkBeforeAdd(prefix) {  if (prefix == "<%= PSBD.SUBAFFIDATARIO %>" && !checkElement(prefix, getArray(prefix, true))) {  var message = "Hai gia' selezionato questo soggetto";  var plus = ".";  if (("<%= PSBD.REQUISITO %>" == prefix)) {  message = "Hai gia' inserito un requisito per questa categoria";  }   alert(message + plus);  return false;  }  return true; }
function validateRadio(prefix) {  if (prefix == "<%= PSBD.SUBAFFIDATARIO %>" ) return true;  else return false; }
function checkElement(prefix, parameters) {  var idtable = "idTabella" + prefix;  var table = document.getElementById(idtable);  if (table != null) {  var numrows = table.rows.length;  if (numrows == 0) return true;  var esiste = true;  for (var i = 0; i < numrows; i++) {  var uguale = false;  for (var j = 0; j < parameters.length; j++) {  var idElem = "row" + prefix + i + parameters[j];  var element = document.getElementById(idElem);  var inmodifica = document.getElementById("inModifica" + prefix);  if (element != null && (inmodifica == null || inmodifica.value != ("row" + prefix + i))) {  var value = "";  if (element.childNodes[0] != null) value = element.childNodes[0].data;  var field = document.getElementById(parameters[j]);  var fieldvalue;  if (prefix == "<%= PSBD.DITTA_AUSILIARIA %>") {  value = element.innerHTML;  }  if ("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>");  fieldvalue = getSelectValue(field, false);  var id = "hiddenrow" + prefix + i + "<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>";  field = document.getElementById(id).value;  value = field;  } else if ("<%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_AGG_RUOLO %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" == parameters[j]) {  fieldvalue = getSelectValue(field, false);  } else if ("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>");  fieldvalue = getSelectValue(field, true);  } else {  fieldvalue = field.value;  }  if (value != fieldvalue) {  uguale = false;  break;  } else {  uguale = true;  }  } else {  j++;  }  }  if (uguale == true) {  return false;  }  }  return true;  } }
function getTipoAggiudicatario(idElement) {  var textElement = "";  if (idElement == 1) {  textElement = "<%= PSBD.FIELD_NAME_AGG_ATI%>";  } else if (idElement == 2) {  textElement = "<%= PSBD.FIELD_NAME_AGG_CONSORZIO%>";  } else if (idElement == 3) {  textElement = "<%= PSBD.FIELD_NAME_AGG_IMPRESA_SINGOLA%>";  } else if (idElement == 4) {  textElement = "<%= PSBD.FIELD_NAME_AGG_GEIE%>";  } else if (idElement == 5) {  textElement = "<%= PSBD.FIELD_NAME_ASSOCIAZIONE_CATEGORIA%>";  } else {  textElement = "";  }  return textElement; }
function addRow(args, argshidden, prefix) {
    if (prefix == "<%= PSBD.SUBAFFIDATARIO %>" && (document.getElementById("selected" + prefix).value == 0)) {
        alert("E' necessario scegliere un soggetto per poter proseguire.");
    } else if (prefix == "<%= PSBD.SUBAFFIDATARIO %>" && document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG%>").selectedIndex == 0) {
        alert("SIMOG_VALIDAZIONE_104 - Tipologia del soggetto aggiudicatario/affidatario: selezionare un valore tra quelli previsti.");
    } else {
        if (checkBeforeAdd(prefix)) {
            if (validateRadio(prefix)) {
                var idnext = getRowIndex(prefix); 
                var idtable = "idTabella" + prefix; 
                console.log("idTabella" +idtable);
                var table = document.getElementById(idtable);
                var newid = "row" + prefix + (parseInt(idnext) + 1);
                var tbody = table.getElementsByTagName("tbody")[0];
                var row = createRow(newid, args, argshidden, prefix);
                tbody.appendChild(row);
                appendHidden(row, newid, prefix, getArray(prefix, false), true);
                var idDIVTabella = "DIVTabella" + prefix;
                var numOfRows = idnext + 1;
                hideSezioneAggiungi(args, argshidden, prefix);
                setModificato(prefix);
                document.getElementById("selected" + prefix).value = 0;
            }
        }
    }
}
function resetParameters(prefix, parametri) {  var preForReq = "";  for (var i = 0; i < parametri.length; i++) {  var element = document.getElementById(preForReq + parametri[i]);  if (element != null) {  if (("<%= PSBD.FIELD_NAME_PREVALENTE %>" == parametri[i]) || ("<%= PSBD.FIELD_NAME_SCORPORABILE %>" == parametri[i]) || ("<%= PSBD.FIELD_NAME_SUBAPPALTABILE %>" == parametri[i])) {  element.value = "";  }  if (("<%= PSBD.S_FIELD_NAME_CONTENZIOSO_GARA %>" == parametri[i]) || ("<%= PSBD.N_FIELD_NAME_CONTENZIOSO_GARA %>" == parametri[i]) || ("<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" == parametri[i])) {  element.checked = false;  if ("<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" == parametri[i]) {  document.getElementById("<%= PSBD.N_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>").checked = false;  }  } else if ("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" == parametri[i] || "<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG%>" == parametri[i] || "<%= PSBD.FIELD_NAME_AGG_RUOLO%>" == parametri[i] || "<%= PSBD.FIELD_NAME_ID_CATEGORIA%>" == parametri[i] || "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO%>" == parametri[i] || "<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>" == parametri[i] || "<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI%>" == parametri[i] || "<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>" == parametri[i]) {  element.selectedIndex = 0;  } else {  element.value = "";  }  }  } }
function setAndSave(idform, tabName) {  if (!hasErrors(document.forms[0])) {  if (validateRadios(tabName)) {  if (confirm("I dati verranno salvati. Procedere?")) {  if (tabName == "<%=PSBD.TAB_AGGIUDICAZIONE%>") {  creaHidden(idform, "<%= PSBD.RESPONSABILE %>");  creaHidden(idform, "<%= PSBD.FINANZIAMENTO %>");  creaHidden(idform, "<%= PSBD.AGGIUDICATARIO %>"); creaHidden(idform, "<%= PSBD.SUBAFFIDATARIO %>"); creaHidden(idform, "<%= PSBD.REQUISITO %>");  creaHidden(idform, "<%= PSBD.PRESTAZIONE %>");  }    document.getElementById('<%=PSBD.TAB%>').value = tabName;  document.getElementById('<%=PSBD.ACTION_TYPE%>').value = "<%=PSBD.ACTION_SALVA%>";  document.getElementById(idform).submit();  }  }  } }
function setForModifyRow(idrow, args, argshidden, prefix) {
    var array = ricreaArray(args, argshidden);
    resetParameters(prefix, array);
    var preForReq = "";
    for (var j = 0; j < args.length; j++) {
        var idtd = idrow + args[j];
        var field = document.getElementById(preForReq + args[j]);
        if ("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[j]) {
            var td = document.getElementById(idtd);
            var tablevalue = "";
            if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;
            var optarray = field.options;
            field.selectedIndex = cercaIndex(optarray, 0, tablevalue);
        } else if ("<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>" == args[j]) {
            var td = document.getElementById(idtd);
            var tablevalue = "";
            if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;
            tablevalue = tablevalue.split(" ")[0];
            var optarray = field.options;
            field.selectedIndex = cercaIndex(optarray, 0, tablevalue);
        } if ("<%= PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" == args[j] || "<%= PSBD.FIELD_NAME_MOTIVAZIONE %>" == args[j]) {
            var td = document.getElementById(idtd);
            if (td != null) {
                var text = td.value;
                if (field != null) {
                    field.value = text;
                }
            }
        }
        if ("<%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>" == args[j] ) {
            var td = document.getElementById("hidden" + idtd);
            var td = document.getElementById(idtd);
            if (td != null) {
                if (td.innerHTML != null && td.innerHTML != "") field.value = td.innerHTML;
                else field.value = "";
            }
        } 
        if ("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" == args[j] ) {
            var td = document.getElementById(idtd);
            if (td != null) {
                if (td.innerHTML != null && td.innerHTML != "") field.value = td.innerHTML;
                else field.value = "IT";
            }
        } else if ("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" == args[j]) {
            var td = document.getElementById("hidden" + idtd);
            if (td != null) {
                var text = td.value;
                if (field != null) {
                    if (text != "") field.value = text;
                    else field.value = "IT";
                }
            }
        } else {
            var td = document.getElementById(idtd);
            if (td != null) {
                var tablevalue = "";
                if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;
                if (field != null) {
                    field.value = tablevalue;
                }
            }
        }
    }
    for (var j = 0; j < argshidden.length; j++) {
        var idtd = idrow + argshidden[j];
        var td = document.getElementById(idtd);
        if (td != null) {
            var tablevalue = "";
            if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;
            var field = document.getElementById(preForReq + argshidden[j]);
            if ("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" == argshidden[j]) {
                var td = document.getElementById(idtd);
                var optarray = field.options;
                field.selectedIndex = cercaIndex(optarray, 0, tablevalue);
            } else if ("<%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>" == argshidden[j]) {
                field = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>");
                var optarray = field.options;
                field.selectedIndex = cercaIndex(optarray, 0, tablevalue);
            } else {
                field.value = tablevalue;
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
}
function apriPopUpRubricaDA( url, tabName, titlePopup, cf_ditta, idSubappalto) {
    var paramsString = "?" + "<%=PSBD.TAB%>=" + tabName + "&titleRubrica=" + titlePopup + "&<%=ParametriServletRubrica.OPERAZIONE%>=" + "Cerca in rubrica" + "&from=menu" + "&CF_DITTA=" + cf_ditta + "&idSubappalto=" +idSubappalto;
    dialogArgs = new MyDialogArguments();
    dialogArgs.Sender = window;
    if (!window.showModalDialog) {
    	var $dialog = $("#dialogDitteAusiliarie").html('<iframe style="border: 0px; " src="' + url + paramsString + '" width="100%" height="100%"></iframe>').dialog({
            title: "ANAC: Autorit&agrave; Nazionale Anticorruzione",
            autoOpen: !1,
            dialogClass: "no-close",
            modal: !0,
            height: 700,
            minWidth: 1100,
            minHeight: 600,
            draggable: !0
        });
        $dialog.dialog("open");
    } else {
        var windowprops = "dialogWidth: 800px; dialogHeight: 600px; center: 1; scroll: 1; help: 1; status: 0;";
        popup = window.showModalDialog(url + paramsString,'_blank', dialogArgs, windowprops);
    }
}
 
</script>
