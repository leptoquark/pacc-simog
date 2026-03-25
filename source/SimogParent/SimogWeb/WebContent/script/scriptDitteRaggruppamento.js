<script type="text/javascript">//gm aggiunto per il popup ditte raggruppamento
/* La funzione e' usata per aggiungere una riga alla tabella ditte raggruppamento
* tramite il parametro "prefix". Ottiene l'id della nuova riga. Ritorna
* un oggetto di tipo TR, che viene "appeso" al body della tabella
* determinata a inizio funzione. I td creati contengono i campi dell'array
* passato come parametro.*/
function addRowDittaRaggruppamentoOnLoad(args, argshidden, array, readonly, prefix){
	if(prefix == "<%= PSBD.DITTA_RAGGRUPPAMENTO %>"){
		var idnext = getRowIndex(prefix);
		var idtable = "idTabella"+prefix;
		var table = document.getElementById(idtable);
		var newid = "row"+prefix+(parseInt(idnext)+1);
		var tbody = table.getElementsByTagName("tbody")[0];	
		var row = document.createElement("tr");
		row.setAttribute("id",newid,0);
		var tdmod = document.createElement("td");
		tdmod.className = "hmenu";
		tdmod.setAttribute("nowrap","nowrap");
		var argsString = ricreaStringa(args);
		var argshiddenString = ricreaStringa(argshidden);
		//se readonly è false o null i tasti sono abilitati
		if(readonly!='true'){
			var linkmod =document.createElement("A");
			linkmod.appendChild(document.createTextNode("Modifica"));
		    linkmod.setAttribute("href","javascript:setForModifyRow('"+newid+"',["+argsString+"],["+argshiddenString+"],'"+prefix+"')",0);
		    tdmod.appendChild(linkmod);
		    var linkcanc =document.createElement("A");
		    linkcanc.appendChild(document.createTextNode("Cancella"));
		    linkcanc.setAttribute("href","javascript:deleteRow('"+newid+"',["+argsString+"],["+argshiddenString+"],'"+prefix+"')",0);
		    tdmod.appendChild(linkcanc);
		}
		row.appendChild(tdmod);
			
		var already = false;
		var preForReq = "";
		//creo i td con i valori passati dall'array
		for(var i=0; i<args.length; i++) {
		    var td1 = document.createElement("td");
		    td1.setAttribute("nowrap","nowrap");
		    td1.className = "garaTd";
		    var idtd = newid + args[i];
		    if(args[i]=="<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>"){
		    	if(array[i]=='null'){
		    		array[i]="IT";
		    	}
		    }
		    td1.appendChild(document.createTextNode(array[i]));
		    td1.setAttribute("id",idtd,0);
		    row.appendChild(td1);
		}	
		//creo i td hidden con i valori passati dall'array
		for(var j=0; j<argshidden.length; j++) {
		    var td1 = document.createElement("td");
		    var idtd = newid + argshidden[j];
		    td1.appendChild(document.createTextNode(array[i]));
		    td1.style.display = "none";
		    td1.setAttribute("id",idtd,0);
		    row.appendChild(td1);
		    i++;
		}		
		tbody.appendChild(row);
		appendHidden(row,newid,prefix,getArray(prefix,false),true);
		var idDIVTabella = "DIVTabella"+prefix;
		var numOfRows = idnext + 1;
		if(readonly!='true'){
    		hideSezioneAggiungi(args,argshidden,prefix);
		}
		setModificato(prefix);
		document.getElementById("selected"+prefix).value = 0;
	}
}
	
	/* Mostra la sezione Aggiungi della tabella individuata dal parametro "prefix". */
function showSezioneAggiungiRaggruppamento(args,argshidden,prefix){
    var idsezione = "divAgg"+prefix;
	var idpulsante = "showHide"+prefix+"Button";
	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	showElement(idsezione);
	var pulsante = document.getElementById(idpulsante);
	pulsante.innerHTML = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('button.cancel') : "Annulla";
	pulsante.href = "javascript:hideSezioneAggiungiRaggruppamento(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
}
	/* Nasconde la sezione Aggiungi della tabella individuata dal
	 * parametro "prefix" e resetta i campi della sezione. */
function hideSezioneAggiungiRaggruppamento(args,argshidden,prefix){
	var idsezione = "divAgg"+prefix;
	var idpulsante = "showHide"+prefix+"Button";
	var titolo = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('title.addDitta') : "Aggiungi "+" ditta nel raggruppamento";

	if(prefix == "<%= PSBD.REQUISITO %>")
	titolo = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('title.addRequisito') : "Aggiungi Requisito";
	else if(prefix == "<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>")
	titolo = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('title.addPosizione') : "Aggiungi Posizione";

	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	hideElement(idsezione);
	var pulsante = document.getElementById(idpulsante);
	pulsante.innerHTML = titolo;
	pulsante.href = "javascript:showSezioneAggiungiRaggruppamento(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
	var array = ricreaArray(args,argshidden);
	resetParameters(prefix,array);
	setButtonForAddRaggruppamento(args,argshidden,prefix);
	if(prefix == "<%= PSBD.RESPONSABILE %>" ||
	prefix == "<%= PSBD.AGGIUDICATARIO %>" ||
	prefix == "<%= PSBD.PRESTAZIONE %>" ||
	prefix == "<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>"){
	resetFormModified("selected"+prefix);
	}
	var modifica = document.getElementById("inModifica"+prefix);
	if(modifica != null)
	modifica.parentNode.removeChild(modifica);
	}
	 
	 /*metodo usato per settare il link del pulsante "Aggiungi" */
function setButtonForAddRaggruppamento(args,argshidden,prefix) {
	var button = document.getElementById("AddMod"+prefix);
	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	button.href = "javascript:addRowDittaRaggruppamento(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
	button.innerHTML = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('button.add') : "Aggiungi";
}
	   
function checkBeforeAddRaggruppamento(prefix){
		if(((prefix == "<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>")||
		(prefix == "<%= PSBD.RESPONSABILE %>")||
		(prefix == "<%= PSBD.PRESTAZIONE %>")||
		(prefix == "<%= PSBD.AGGIUDICATARIO %>")||
		("<%= PSBD.REQUISITO %>" == prefix))&& !checkElement(prefix,getArrayRaggruppamento(prefix,true))){
		var message = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.alreadySelected') : "Hai gia' selezionato questo soggetto";
		var plus = ".";
		if(("<%= PSBD.REQUISITO %>" == prefix)){
			message = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.alreadyInsertedRequisito') : "Hai gia' inserito un requisito per questa categoria";
		}else if((prefix == "<%= PSBD.RESPONSABILE %>") || (prefix == "<%= PSBD.PRESTAZIONE %>"))
			plus = (typeof i18n !== 'undefined' && i18n.get) ? " " + i18n.get('error.alreadySelectedRole') : " per il ruolo in questione."; 
		if (typeof i18n !== 'undefined' && i18n.alert) { 
			if(("<%= PSBD.REQUISITO %>" == prefix)){
				i18n.alert('error.alreadyInsertedRequisito');
			} else if((prefix == "<%= PSBD.RESPONSABILE %>") || (prefix == "<%= PSBD.PRESTAZIONE %>")){
				i18n.alert('error.alreadySelectedRole');
			} else {
				i18n.alert('error.alreadySelected');
			}
		} else {
			alert(message+plus);
		}
			return false;
		}
		return true;
}
	
	function getArrayRaggruppamento(prefix,isCheck){
		var arr = new Array();
		if("<%= PSBD.AGGIUDICATARIO %>" == prefix){
		arr[0]="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>";
		arr[1]="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>";
		if(!isCheck){
		arr[2]="<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>";
		//arr[3]="<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>";
		//arr[4]="<%= PSBD.FIELD_NAME_AGG_PERCENTUALE %>";
		//arr[3]="<%= PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO %>";
		//arr[6]="<%= PSBD.FIELD_NAME_AGG_CF_AUSILIARIA %>";
		arr[3]="<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>";
		arr[4]="<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>";
		//arr[9]="<%= PSBD.FIELD_NAME_AGG_PERCENTUALE %>";
		arr[5]="<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>";
		arr[6] = "<%= PSBD.FIELD_NAME_ANAGOE %>";
		//gm aggiunto per raggruppamenti di impresa
		//arr[11]="<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>";
		}}}

</script>