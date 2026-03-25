function abilitaImporto(radio) {
val = radio.value;
euro = document.getElementById("euro");	
if( val == 'SI'){
euro.disabled = false;
} else {
euro.value = "";
euro.disabled = true;
euro.style.borderColor = ''; //deMarkField
}}

// controlla la selezione di almeno un checkbox di una lista
function checkBox(){
l = document.getElementById('cancella').id_documento.length;
	
if(l)
for(i=0;i<l;i++){
if(document.getElementById('cancella').id_documento[i].checked)
return (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.deleteDocuments') : confirm('Eliminare i documenti selezionati?');
}
else
if(document.getElementById('cancella').id_documento.checked)
return (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.deleteDocuments') : confirm('Eliminare i documenti selezionati?');
if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.selectFile'); } else { alert('Selezionare almeno un file'); }
return false;
}

/* verifica se � possibile modificare il valore del campo se la gara o il lotto sono stati comunicati a riscossione*/
function checkChange(obj, obj2, dataComun, admin) {

if( dataComun != null && dataComun != "null" && dataComun != "" && !admin){
if( obj.type == "radio"){
obj.checked = obj.defaultChecked;
obj2.checked = !obj.checked;
}
else{
obj.value = obj.defaultValue;
}
if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.amountModificationBlocked'); } else { alert("Impossibile modificare l'importo in quanto il dato e' stato trasmesso al sistema Riscossione!"); }
return false;
}

if( obj.type == "radio"){
abilitaImporto(obj);
}
return true;
}

function replaceWordChars(text) {
    var s = text.value;
    // smart single quotes and apostrophe
    s = s.replace(/[\u2018|\u2019|\u201A]/g, "\'");
    // smart double quotes
    s = s.replace(/[\u201C|\u201D|\u201E]/g, "\'");
    // ellipsis
    s = s.replace(/\u2026/g, "...");
    // dashes
    s = s.replace(/[\u2013|\u2014]/g, "-");
    // circumflex
    s = s.replace(/\u02C6/g, "^");
    // open angle bracket
    s = s.replace(/\u2039/g, "<");
    // close angle bracket
    s = s.replace(/\u203A/g, ">");
    // spaces
    s = s.replace(/[\u02DC|\u00A0]/g, " ");
    s = s.replace(/[\u00C2|\u00B0]/g, "^");
    text.value = s;
}

