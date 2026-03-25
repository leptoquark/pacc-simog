<SCRIPT type="text/javascript">


//gm aggiunto per il popup ditte ausiliarie
/* La funzione e' usata per aggiungere una riga alla tabella ditte ausiliarie
* tramite il parametro "prefix". Ottiene l'id della nuova riga. Ritorna
* un oggetto di tipo TR, che viene "appeso" al body della tabella
* determinata a inizio funzione. I td creati contengono i campi dell'array
* passato come parametro.*/
function addRowDittaAusiliariaOnLoad(args, argshidden, array, readonly, prefix){
	if(prefix == "<%= PSBD.DITTA_AUSILIARIA %>"){
		var idnext = getRowIndex(prefix);
		var idtable = "idTabella"+prefix;
		var table = document.getElementById(idtable);
		var newid = "row"+prefix+(parseInt(idnext)+1);
		var tbody = table.getElementsByTagName("TBODY")[0];	
		var row = document.createElement("TR");
		row.setAttribute("id",newid,0);
		var tdmod = document.createElement("TD");
		tdmod.className = "hmenu";
		tdmod.setAttribute("nowrap","nowrap");
		var argsString = ricreaStringa(args);
		var argshiddenString = ricreaStringa(argshidden);
		//se readonly e' false o null i tasti sono abilitati
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
		    var td1 = document.createElement("TD");
		    td1.setAttribute("nowrap","nowrap");
		    td1.className = "garaTd";
		    var idtd = newid + args[i];
		    if(args[i]=="<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>"){
		    	if(array[i]=='null'){
		    		array[i]="IT";
		    	}
		    }
		    if(args[i]=="<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>"){
		    	if(array[i]=="1"){
		    		array[i]="<%= PSBD.REQUISITI_FLAG_AVVALIMENTO %>";
		    	}
		    	else if(array[i]=="2"){
		    		array[i]="<%= PSBD.ATTTESTAZIONE_FLAG_AVVALIMENTO %>";
		    	}
		    	else if(array[i]=="3"){
		    		array[i]="<%= PSBD.ENTRAMBI_FLAG_AVVALIMENTO %>";
		    	}
		    	else if(array[i]=="0"){
		    		array[i]="<%= PSBD.NESSUNO_FLAG_AVVALIMENTO %>";
		    	}
		    	idtd = newid + args[i].substring(2);
		    }
		    td1.appendChild(document.createTextNode(array[i]));
		    td1.setAttribute("id",idtd,0);
		    row.appendChild(td1);
		}	
		//creo i td hidden con i valori passati dall'array
		for(var j=0; j<argshidden.length; j++) {
		    var td1 = document.createElement("TD");
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
	function showSezioneAggiungiAusiliaria(args,argshidden,prefix){
    var idsezione = "divAgg"+prefix;
	var idpulsante = "showHide"+prefix+"Button";
	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	showElement(idsezione);
	var pulsante = document.getElementById(idpulsante);
	pulsante.innerHTML="Annulla";
	pulsante.href = "javascript:hideSezioneAggiungiAusiliaria(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
	}
	/* Nasconde la sezione Aggiungi della tabella individuata dal
	 * parametro "prefix" e resetta i campi della sezione. */
	function hideSezioneAggiungiAusiliaria(args,argshidden,prefix){
	var idsezione = "divAgg"+prefix;
	var idpulsante = "showHide"+prefix+"Button";
	var titolo = "Aggiungi "+" ditta ausiliaria";

	if(prefix == "<%= PSBD.REQUISITO %>")
	titolo = "Aggiungi Requisito";
	else if(prefix == "<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>")
	titolo = "Aggiungi Posizione";

	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	hideElement(idsezione);
	var pulsante = document.getElementById(idpulsante);
	pulsante.innerHTML = titolo;
	pulsante.href = "javascript:showSezioneAggiungiAusiliaria(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
	var array = ricreaArray(args,argshidden);
	resetParameters(prefix,array);
	setButtonForAddAusiliaria(args,argshidden,prefix);
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
	function setButtonForAddAusiliaria(args,argshidden,prefix) {
	var button = document.getElementById("AddMod"+prefix);
	var argsString = ricreaStringa(args);
	var argshiddenString = ricreaStringa(argshidden);
	button.href = "javascript:addRowDittaAusiliaria(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
	button.innerHTML="Aggiungi";}
	   
	function checkBeforeAddAusiliaria(prefix){
		if(((prefix == "<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>")||
		(prefix == "<%= PSBD.RESPONSABILE %>")||
		(prefix == "<%= PSBD.PRESTAZIONE %>")||
		(prefix == "<%= PSBD.AGGIUDICATARIO %>")||
		("<%= PSBD.REQUISITO %>" == prefix))&& !checkElement(prefix,getArrayAusiliaria(prefix,true))){
		var message = "Hai gia' selezionato questo soggetto";
		var plus = ".";
		if(("<%= PSBD.REQUISITO %>" == prefix)){
		message = "Hai gia' inserito un requisito per questa categoria";
		}else if((prefix == "<%= PSBD.RESPONSABILE %>") || (prefix == "<%= PSBD.PRESTAZIONE %>"))
		plus = " per il ruolo in questione."; 
		alert(message+plus);
		return false;
		}return true;}
	
	function getArrayAusiliaria(prefix,isCheck){
		var arr = new Array();
		if("<%= PSBD.AGGIUDICATARIO %>" == prefix){
		arr[0]="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>";
		arr[1]="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>";
		if(!isCheck){
		arr[2]="<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>";
		//arr[3]="<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>";
		//arr[4]="<%= PSBD.FIELD_NAME_AGG_PERCENTUALE %>";
		arr[3]="<%= PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO %>";
		//arr[6]="<%= PSBD.FIELD_NAME_AGG_CF_AUSILIARIA %>";
		arr[4]="<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>";
		arr[5]="<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>";
		//arr[9]="<%= PSBD.FIELD_NAME_AGG_PERCENTUALE %>";
		arr[6]="<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>";
		arr[7] = "<%= PSBD.FIELD_NAME_ANAGOE %>";
		//gm aggiunto per raggruppamenti di impresa
		//arr[11]="<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>";
		}}}

</SCRIPT>