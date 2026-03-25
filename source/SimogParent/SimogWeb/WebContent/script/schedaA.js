function checkForShow(elements){
for(var i = 0; i<elements.length; i++){
var name = elements[i];
if(elements[++i] == 'true'){
showMenuNoCheck(name);
}}}
function valutaSubTotale() { try{
var somma = 0; var lavori; var servizi; var forniture; var sicurezza; var progettazione; var disposizione; var nonAssoggettato;
if (document.getElementById('IMPORTO_LAVORI').value == "" ) 
lavori = parseFloat('0');
else  lavori = parseFloat(document.getElementById('IMPORTO_LAVORI').value.replace(/\./g,"").replace(',','.'));
if (document.getElementById('IMPORTO_SERVIZI').value == "" ) 
servizi = parseFloat('0');
else  servizi = parseFloat(document.getElementById('IMPORTO_SERVIZI').value.replace(/\./g,"").replace(',','.'));
if (document.getElementById('IMPORTO_FORNITURE').value == "" ) 
forniture = parseFloat('0');
else  forniture = parseFloat(document.getElementById('IMPORTO_FORNITURE').value.replace(/\./g,"").replace(',','.'));
if (document.getElementById('IMPORTO_ATTUAZIONE_SICUREZZA').value == "" ) 
sicurezza = parseFloat('0');
else  sicurezza = parseFloat(document.getElementById('IMPORTO_ATTUAZIONE_SICUREZZA').value.replace(/\./g,"").replace(',','.'));
if (document.getElementById('IMPORTO_PROGETTAZIONE').value == "" ) 
progettazione = parseFloat('0');
else  progettazione = parseFloat(document.getElementById('IMPORTO_PROGETTAZIONE').value.replace(/\./g,"").replace(',','.'));
if (document.getElementById('IMPORTO_DISPOSIZIONE').value == "" ) 
disposizione = parseFloat('0');
else  disposizione = parseFloat(document.getElementById('IMPORTO_DISPOSIZIONE').value.replace(/\./g,"").replace(',','.'));
if (document.getElementById('IMP_NON_ASSOG').value == "" ) 
nonAssoggettato = parseFloat('0');
else  nonAssoggettato = parseFloat(document.getElementById('IMP_NON_ASSOG').value.replace(/\./g,"").replace(',','.'));

somma = parseFloat(lavori+servizi+forniture) ;
document.getElementById("SubTotale").value = addMyDotsFromCommaString(somma.toFixed(3).replace('.',','));
somma = parseFloat(somma + sicurezza + progettazione + nonAssoggettato);
document.getElementById("ImpCompAppalto").value = addMyDotsFromCommaString(somma.toFixed(3).replace('.',','));
somma = parseFloat(somma + disposizione);
document.getElementById("ImpCompIntervento").value = addMyDotsFromCommaString(somma.toFixed(3).replace('.',','));
checkIfTwoFields(document.getElementById('ribAgg'),document.getElementById('percAum'));
}catch (e) {/*ignored*/};
return true;}

function valutaSubTotaleSottoEsclusi() {
	var somma = 0; var complessivo; var disposizione;
	if (document.getElementById('IMPORTO_COMPLESSIVO').value == "" ) 
	complessivo = parseFloat('0');
	else  complessivo = parseFloat(document.getElementById('IMPORTO_COMPLESSIVO').value.replace(/\./g,"").replace(',','.'));
	if (document.getElementById('IMPORTO_DISPOSIZIONE').value == "" ) 
		disposizione = parseFloat('0');
	else  disposizione = parseFloat(document.getElementById('IMPORTO_DISPOSIZIONE').value.replace(/\./g,"").replace(',','.'));

	// PP 3.02.1.6
	if (document.getElementById('IMPORTO_ATTUAZIONE_SICUREZZA')){
		if (document.getElementById('IMPORTO_ATTUAZIONE_SICUREZZA').value == "" ) 
			sicurezza = parseFloat('0');
		else  sicurezza = parseFloat(document.getElementById('IMPORTO_ATTUAZIONE_SICUREZZA').value.replace(/\./g,"").replace(',','.'));
	} 
	else sicurezza = parseFloat('0');
	
	somma = parseFloat(complessivo + disposizione + sicurezza) ;
	document.getElementById("ImpCompIntervento").value = addMyDotsFromCommaString(somma.toFixed(3).replace('.',','));
	checkIfTwoFields(document.getElementById('ribAgg'),document.getElementById('percAum'));
	return true;}

function replaceAll(str, cerca, sostituisci) {
  return str.split(cerca).join(sostituisci);
}	
	
	
function calcoloAggiudicazione(element,element1, prefix){
	if (typeof document.getElementById('check1G')!=='undefined' && document.getElementById('check1G')!=null 
			&& document.getElementById('check1G').checked){
		var importo=0;
		var exitNext=false;
		
		var indiciValidi = [];
		
		for(y=0;y<=100;y++){
			var id2 = "hiddenrow" + prefix + y + "IMPORTO_AGGIUDICATARIO";
			if (typeof document.getElementById(id2)!=='undefined' && document.getElementById(id2)!=null 
					&& document.getElementById(id2).value!=''){
				indiciValidi.push(y);
			}
		}
		
		for (i=0;i<indiciValidi.length;i++){
			var z = indiciValidi[i];
			var id = "hiddenrow" + prefix + z + "IMPORTO_AGGIUDICATARIO";
			if (typeof document.getElementById(id)!=='undefined' && document.getElementById(id)!=null 
				&& document.getElementById(id).value!=''){
				 exitNext=true;
				//importo = importo + parseFloat((document.getElementById(id).value.replace('.','')).replace(',','.'));
				importo = importo + parseFloat(replaceAll(document.getElementById(id).value,'.','').replace(',','.'));
//				alert("x importo:"+importo);
			}
		}
	
		if (exitNext){
			var value = importo.toLocaleString('it-IT', {minimumFractionDigits: 3});
			document.getElementById('euroHidden').value = value;
			document.getElementById('euro').value = value;	
		}
		
	}else{
		if((element.value != "") && (element1.value != "")){
			if(parseFloat(element.value.replace(',','.')) == 0 && parseFloat(element1.value.replace(',','.')) == 0){
				return setImportoDiAggiudicazione(element);
			}else{
				validatePercentage(element);
				validatePercentage(element1, true);
				document.getElementById('euroHidden').value = '???????????';
				return false;}
		}else if(element.value != ""){
			return setImportoDiAggiudicazione(element);
		}else if(element1.value != ""){
			return setImportoDiAggiudicazione(element1, true);
		}else{
			document.getElementById('euroHidden').value = '???????????';
		return false;}}}