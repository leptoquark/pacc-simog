<script type="text/javascript"> 
var isNav4, isNav6, isIE4;
function setBrowser() {  isIE4 = false;  isNav4 = false;  isNav6 = false;  if (navigator.appName.indexOf("Explorer") >= 0 || navigator.appName.indexOf("MSIE") >= 0) {  isIE4 = true;  } else {  isNav4 = true;  isNav6 = true;  } }

    setBrowser();
    
    function getStyleBySelector( selector ) {  if (!isNav6)  {  return null;  }  var sheetList = document.styleSheets;  var ruleList;  var i, j;  for (i=sheetList.length-1;i >= 0;i--)  {  ruleList = sheetList[i].cssRules;  for (j=0;j<ruleList.length;j++)  {  if (ruleList[j].type == CSSRule.STYLE_RULE && ruleList[j].selectorText == selector)  {  return ruleList[j].style;  }  }  }  return null; }
	   
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
	
    
    function showMenuNoCheck(id) {
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
	
    function showElement(id) {  setIdProperty(id, "display", "block"); }
	
    function hideElement(id) {  setIdProperty(id, "display", "none"); }
	
	function addRow(args,argshidden,prefix) {
		if(((prefix=="<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>")||(prefix=="<%= PSBD.RESPONSABILE %>")||(prefix=="<%= PSBD.PRESTAZIONE %>")||(prefix=="<%= PSBD.AGGIUDICATARIO %>")||(prefix=="<%= PSBD.DITTA_AUSILIARIA %>"))&&(document.getElementById("selected"+prefix).value==0))
		{  alert("E' necessario scegliere un soggetto per poter proseguire.");
		}  else  {
			if(checkBeforeAdd(prefix))  {
				if((prefix=="<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>")||validateRadio(prefix))  {
					var idnext=getRowIndex(prefix); 
					var idtable="idTabella"+prefix;
					var table=document.getElementById(idtable); 
					var newid="row"+prefix+(parseInt(idnext)+1);
					var tbody=table.getElementsByTagName("TBODY")[0];
					var row=createRow(newid,args,argshidden,prefix); 
					tbody.appendChild(row);
					appendHidden(row,newid,prefix,getArray(prefix,false),true);
					var idDIVTabella="DIVTabella"+prefix;
					var numOfRows=idnext+1;
					hideSezioneAggiungi(args,argshidden,prefix);
					setModificato(prefix); 
					document.getElementById("selected"+prefix).value=0;  
					}  
				} 
			} 
		}
	
	
	
	
	function getArray(prefix,isCheck) {
		  var arr = new Array();  
		  if("<%= PSBD.AGGIUDICATARIO %>" == prefix)  {
		  arr[0]="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>"; 
		  arr[1]="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>";
		  if(!isCheck)  {
		  arr[2]="<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>";
		  arr[3]="<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>";
		  arr[4]="<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>";
		 } return arr; }}
	
	
	function validateRadio(prefix) {
		if(prefix == "<%= PSBD.AGGIUDICATARIO %>"){
			return true;

		}	
		else return false; 
	}
	
	function setModificato(prefix) {
		var index = 0;
		 if(prefix == "<%= PSBD.AGGIUDICATARIO %>")  { 
				index = 2; 
							} 
		 setFormModified("Modificato"+index);					
	}
	
	function setButtonForAdd(args,argshidden,prefix) { 
		var button = document.getElementById("AddMod"+prefix);
		var argsString = ricreaStringa(args);
		var argshiddenString = ricreaStringa(argshidden); 
		button.href = "javascript:addRow(["+argsString+"],["+argshiddenString+"],'"+prefix+"')"; 
		button.innerHTML="Aggiungi"; 
		}
	
	function deleteRow(idRow,args,argshidden,prefix) {
		if(confirm("Si sta per cancellare il record. Proseguire?"))  {
			var modifica = document.getElementById("inModifica"+prefix);
			if((modifica != null) && (modifica.value == idRow))  {
				hideSezioneAggiungi(args,argshidden,prefix);
				}  var row = document.getElementById(idRow); 
				row.parentNode.removeChild(row); 
				var idnext = document.getElementById("idTabella"+prefix).rows.length-1;
				var idDIVTabella = "DIVTabella"+prefix;  var numOfRows = idnext + 1;
				setModificato(prefix); 
				} 
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
		var titolo = "Aggiungi "+" Invitato";
		var argsString = ricreaStringa(args);
		var argshiddenString = ricreaStringa(argshidden);
		hideElement(idsezione);
		var pulsante = document.getElementById(idpulsante);
		pulsante.innerHTML = titolo; 
		pulsante.href = "javascript:showSezioneAggiungi(["+argsString+"],["+argshiddenString+"],'"+prefix+"')";
		var array = ricreaArray(args,argshidden);
		resetParameters(prefix,array);
		setButtonForAdd(args,argshidden,prefix); 
		if(prefix == "<%= PSBD.AGGIUDICATARIO %>")  { 
			resetFormModified("selected"+prefix);
			}
		var modifica = document.getElementById("inModifica"+prefix);
		if(modifica != null)
			modifica.parentNode.removeChild(modifica); 
		}
	
	function resetParameters(prefix,parametri) {
		var preForReq = "";
		for(var i=0;i<parametri.length;i++)  {
			var element = document.getElementById(preForReq+parametri[i]);
			if(element != null)  {
				element.value = ""; 
			}   
		}
	}
	
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
			}  stringa = stringa + "'";
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
	
	
	function MyDialogArguments() {
		this.Sender = null; 
		}
	
	function apriPopUpRubrica(o, e, i) {
	    var r = "?<%=PSBD.TAB%>=" + e + "&titleRubrica=" + i + "&<%=ParametriServletRubrica.OPERAZIONE%>=Cerca in rubrica&from=menu";
	    if (dialogArgs = new MyDialogArguments, dialogArgs.Sender = window, window.showModalDialog) {
	        a = "dialogWidth: 800px; dialogHeight: 600px; center: 1; scroll: 1; help: 1; status: 0;";
	        popup = window.showModalDialog(o + r, dialogArgs, a)
	    } else {
	        var a = "width=800,height=600, chrome,centerscreen,dependent=yes,resizable=yes,scrollbars=yes,location=no,status=no,directories=no,menubar=no,toolbar=no,modal=yes,dialog=yes";
	        popup = window.open(o + r, dialogArgs, a), popup.focus()
	    }
	}
	
	function chiudiPopUp() {
		if(isNav6)  {
			window.opener.focus(); 
			}  else  {
				window.dialogArguments.Sender.focus();  
				}  
		window.close(); 
		}
	
	function selectElement(prefix,parameters) { 
		var idSelected = "selected"+prefix;
		if(isNav6)  {
			for(var i = 0;i<parameters.length;i++)  {
				window.opener.document.getElementById(parameters[i]).value = parameters[++i];
				}
			var selecteditem = window.opener.document.getElementById(idSelected);
			selecteditem.value = 1;
			var modificato = window.opener.document.getElementById("Modificato"+prefix);
			modificato.value = 1; 
			}  else  { 
				for(var i = 0;i<parameters.length;i++)  { 
					window.dialogArguments.Sender.document.getElementById(parameters[i]).value = parameters[++i];
					} 
				var selecteditem = window.dialogArguments.Sender.document.getElementById(idSelected);
				selecteditem.value = 1;
				var modificato = window.dialogArguments.Sender.document.getElementById("Modificato"+prefix);
				modificato.value = 1;
				}
		window.close();
		return false;
		}
	
	function checkBeforeAdd(prefix) {
		if((prefix == "<%= PSBD.AGGIUDICATARIO %>")&& !checkElement(prefix,getArray(prefix,true)))  {
			var message = "Hai gia' selezionato questo soggetto";
			alert(message); 
			return false;  
			}  
		return true; 
		}
	
	function appendHidden(row,idrow,prefix,parametri,creazione) {
		for(var j = 0;j<parametri.length;j++)  {
			var idElement = idrow+parametri[j];
			var element = document.getElementById(idElement);
			var changevalue;
			var tdvalue = ""; 
			if(element.childNodes[0] != null)
				tdvalue = element.childNodes[0].data; 
				changevalue = tdvalue;  
				if(creazione)  { 
					var td = document.createElement("TD"); 
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
    
	
	
	function creaHidden(idform,prefix) {
		var idtable = "idTabella"+prefix;
		var table = document.getElementById(idtable);
		if(table != null)  { 
			var numrows = table.rows.length;
			setHiddenElement(("nrRighe"+prefix),(numrows-1),idform); 
			} 
		}
	
	
    function setHiddenElement(idHidden,value,idform) {  var form = document.getElementById(idform);  var rowshidden = document.createElement("INPUT");  rowshidden.setAttribute("name",idHidden,0);  rowshidden.setAttribute("type","hidden",0);  rowshidden.setAttribute("value",value,0);  form.appendChild(rowshidden); }
	
    function setAndSave(idform, tabName) { 
    	if(!hasErrors(document.forms[0]))  { 
    		if(validateRadios(tabName))  { 
    			if (confirm ("I dati verranno salvati. Procedere?"))  { 
    					creaHidden(idform,"<%= PSBD.AGGIUDICATARIO %>");
    					document.getElementById('<%=PSBD.TAB%>').value=tabName; 
    								document.getElementById('<%=PSBD.ACTION_TYPE%>').value="<%=PSBD.ACTION_SALVA%>";
    								document.getElementById(idform).submit(); 
    								}  
    			} 
    		} 
    	}
    
    
	
    function setBitWise(idhidden, checkbox) {  var hidden = document.getElementById(idhidden);  if(checkbox.checked) hidden.value = parseInt(hidden.value) + parseInt(checkbox.value);  else hidden.value = parseInt(hidden.value) - parseInt(checkbox.value); }
	
    
    function setAndConfirm(idform,tabName) {
    	if(!hasErrors(document.forms[0]))  {
    		if(validateRadios(tabName))  { 
    			if (confirm("I dati verranno confermati. Procedere?"))  {
    					creaHidden(idform,"<%= PSBD.RESPONSABILE %>");
    					creaHidden(idform,"<%= PSBD.FINANZIAMENTO %>");
    					creaHidden(idform,"<%= PSBD.AGGIUDICATARIO %>");
    					creaHidden(idform,"<%= PSBD.REQUISITO %>");
    					creaHidden(idform,"<%= PSBD.PRESTAZIONE %>");
    					creaHidden(idform,"<%= PSBD.INVITATO %>");
    					
    				
    				document.forms[0].elements['<%=PSBD.TAB%>'].value=tabName; 
    				document.forms[0].elements['<%=PSBD.ACTION_TYPE%>'].value="<%=PSBD.ACTION_CONFERMA%>"; 
    				document.forms[0].submit();  
    				}  
    			}  
    		} 
    	}
	
    
    
    
    
    
    function validateRadios(tabname) {
    	if("<%= PSBD.TAB_INFO_COMUNI%>" == tabname)  {
    		return (checkRadio('<%= ParametriServlet.S_FIELD_NAME_SITO_MIN_INF_TRASP %>','<%= ParametriServlet.N_FIELD_NAME_SITO_MIN_INF_TRASP %>') && checkRadio('<%= ParametriServlet.S_FIELD_NAME_SITO_OSSERVATORIO_CP %>','<%= ParametriServlet.N_FIELD_NAME_SITO_OSSERVATORIO_CP %>') && checkRadio('<%= ParametriServlet.S_FIELD_NAME_PROFILO_COMMITTENTE %>','<%= ParametriServlet.N_FIELD_NAME_PROFILO_COMMITTENTE %>'));  }  else if("<%= PSBD.TAB_AGGIUDICAZIONE%>" == tabname)  {
    			var flagEnte = '${schedaA.infoComuni.flagEnteSpeciale}';
    			return (checkRadio('<%= PSBD.S_FIELD_NAME_ASTA_ELETTRONICA %>','<%= PSBD.N_FIELD_NAME_ASTA_ELETTRONICA %>') && ((flagEnte != 'S') || checkRadio('<%= PSBD.S_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA %>','<%= PSBD.N_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA %>')) && ((flagEnte != 'S') || checkRadio('<%= PSBD.S_FIELD_NAME_SISTEMA_QUALIFICAZIONE %>','<%= PSBD.N_FIELD_NAME_SISTEMA_QUALIFICAZIONE %>')) && checkRadio('check2Y','check2N') && checkRadio('check3Y','check3N') && checkRadio('check4Y','check4N') && checkRadio('affidatarioY','affidatarioN'));  
    			}  else if("<%= PSBD.TAB_ADESIONE%>" == tabname)  {  
    				return checkRadio('affidatarioY','affidatarioN'); 
    				}  else if("<%= ParametriServletInizioLavori.TAB_INIZIO_LAVORI%>" == tabname)  {
    					return (checkRadio('<%= ParametriServlet.S_FIELD_NAME_SITO_MIN_INF_TRASP %>','<%= ParametriServlet.N_FIELD_NAME_SITO_MIN_INF_TRASP %>') && checkRadio('<%= ParametriServlet.S_FIELD_NAME_SITO_OSSERVATORIO_CP %>','<%= ParametriServlet.N_FIELD_NAME_SITO_OSSERVATORIO_CP %>') && checkRadio('<%= ParametriServletInizioLavori.SI_FLAG_RISERVA %>','<%= ParametriServletInizioLavori.NO_FLAG_RISERVA %>') && checkRadio('<%= ParametriServlet.S_FIELD_NAME_PROFILO_COMMITTENTE %>','<%= ParametriServlet.N_FIELD_NAME_PROFILO_COMMITTENTE %>'));  
    					}  else if("<%= PSBD.TAB_SOTTOSOGLIA%>" == tabname)  {
    						return checkRadio('<%= PSBD.S_FIELD_NAME_ASTA_ELETTRONICA %>','<%= PSBD.N_FIELD_NAME_ASTA_ELETTRONICA %>');  
    						}  else if("<%= PSBD.TAB_ESCLUSI%>" == tabname)  {
    							return checkRadio('<%= PSBD.S_FIELD_NAME_ASTA_ELETTRONICA %>','<%= PSBD.N_FIELD_NAME_ASTA_ELETTRONICA %>');  
    							}  return true; 
    							}
	
    
    
    
    
    
    
    
    function validateRadio(prefix) { 
    	if(prefix == "<%= PSBD.AGGIUDICATARIO %>") 
    		return true; 
    	else return false; 
    	}
	
    
    
    
    
	function checkRadio(idYes,idNo) { 
		var elemYes = document.getElementById(idYes);
		var elemNo = document.getElementById(idNo);
		if((!elemYes.checked) && (!elemNo.checked))  {
			alert("Selezionare almeno un opzione per tutti i campi Si/No");
			return false;  
			} 
		return true; 
		}
	
	
	
	function setAndVaria(idform,tabName) {
		if (confirm("I dati verranno salvati. Procedere?"))  {

				creaHidden(idform,"<%= PSBD.RESPONSABILE %>");
				creaHidden(idform,"<%= PSBD.FINANZIAMENTO %>");
				creaHidden(idform,"<%= PSBD.AGGIUDICATARIO %>"); 
				creaHidden(idform,"<%= PSBD.REQUISITO %>"); 
				creaHidden(idform,"<%= PSBD.PRESTAZIONE %>");  
				}
			
			document.forms[0].elements['<%=PSBD.TAB%>'].value=tabName;
			document.forms[0].elements['<%=PSBD.ACTION_TYPE%>'].value="<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE%>";
			document.forms[0].submit();  
			}
	
	
	function getSelectValue(elem, flagValue){  var ret = "";  if(elem.selectedIndex >=0){  var selectedelement = element.options[element.selectedIndex];  ret = selectedelement.text;  if (flagValue == true) {  ret = selectedelement.value;  }  }  return ret; }
	
	
	
	function checkElement(prefix, parameters) {  var idtable = "idTabella" + prefix;  var table = document.getElementById(idtable);  if (table != null) {  var numrows = table.rows.length;  if (numrows == 0) return true;  var esiste = true;  for (var i = 0; i < numrows; i++) {  var uguale = false;  for (var j = 0; j < parameters.length; j++) {  var idElem = "row" + prefix + i + parameters[j];  var element = document.getElementById(idElem);  var inmodifica = document.getElementById("inModifica" + prefix);  if (element != null && (inmodifica == null || inmodifica.value != ("row" + prefix + i))) {  var value = "";  if (element.childNodes[0] != null) value = element.childNodes[0].data;  var field = document.getElementById(parameters[j]);  var fieldvalue;  if (prefix == "<%= PSBD.DITTA_AUSILIARIA %>") {  value = element.innerHTML;  }  if ("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>");  fieldvalue = getSelectValue(field, false);  var id = "hiddenrow" + prefix + i + "<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>";  field = document.getElementById(id).value;  value = field;  } else if ("<%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_AGG_RUOLO %>");  fieldvalue = getSelectValue(field, true);  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" == parameters[j]) {  fieldvalue = getSelectValue(field, false);  } else if ("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == parameters[j]) {  field = document.getElementById("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>");  fieldvalue = getSelectValue(field, true);  } else {  fieldvalue = field.value;  }  if (value != fieldvalue) {  uguale = false;  break;  } else {  uguale = true;  }  } else {  j++;  }  }  if (uguale == true) {  return false;  }  }  return true;  } }
	
	function fillRow(idrow, args, argshidden, prefix) {
		var already = false;
		var preForReq = ""; 
		for (var i = 0; i < args.length; i++) {
			var idtd = idrow + args[i]; 
			var element = document.getElementById(preForReq + args[i]);
			var td = document.getElementById(idtd);
			if ("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" == args[i] || "<%= PSBD.FIELD_NAME_AGG_RUOLO %>" == args[i] || "<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[i] || "<%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" == args[i] || "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>" == args[i] || "<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>" == args[i] || "<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>" == args[i]) {  if ("<%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" == args[i]) { 
				element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_ID_CATEGORIA %>");  
				} 
			var contenuto = "";
			if ("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[i]) {
				contenuto = getSelectValue(element, true); 
				} else { 
					contenuto = getSelectValue(element, false);  
					}  if (td.childNodes[0] != null) {  
						td.childNodes[0].data = contenuto;  
						} else { 
							td.appendChild(document.createTextNode(contenuto));  
							}  
					} else if (("<%= PSBD.S_FIELD_NAME_CONTENZIOSO_GARA %>" == args[i]) || ("<%= PSBD.N_FIELD_NAME_CONTENZIOSO_GARA %>" == args[i])) {
						if (already == false) { 
							var idField = args[i].substring(2); 
							var field = document.getElementById(idrow + idField);
							if (element.checked == true) { 
								if (element.value == "S") {  field.childNodes[0].data = "SI";
								} else {
									field.childNodes[0].data = "NO";
									}  
								} else {
									if (element.value == "S") {
										field.childNodes[0].data = "NO";  
										} else {
											field.childNodes[0].data = "SI"; 
											}  
									}  
							}  
						} else if (("<%= PSBD.FIELD_NAME_PREVALENTE %>" == args[i]) || ("<%= PSBD.FIELD_NAME_SCORPORABILE %>" == args[i]) || ("<%= PSBD.FIELD_NAME_SUBAPPALTABILE %>" == args[i])) {
							var idField = args[i]; 
							var field = document.getElementById(idrow + idField);
							if (element.value == "S") field.childNodes[0].data = "SI";
							else if (element.value == "N") field.childNodes[0].data = "NO";
							else field.childNodes[0].data = ""; 
							} else if ("<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" == args[i]) {
								var noElement = document.getElementById("<%= PSBD.N_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>");
								var idField = args[i].substring(2);
								var field = document.getElementById(idrow + idField);
								if (element.checked == true) { 
									if (noElement.checked == true) {  
										field.childNodes[0].data = "<%= PSBD.ENTRAMBI_FLAG_AVVALIMENTO %>";  
										} else {
											field.childNodes[0].data = "<%= PSBD.REQUISITI_FLAG_AVVALIMENTO %>";  
											}  
									} else { 
										if (noElement.checked == true) { 
											field.childNodes[0].data = "<%= PSBD.ATTTESTAZIONE_FLAG_AVVALIMENTO %>"; 
											} else {
												field.childNodes[0].data = "<%= PSBD.NESSUNO_FLAG_AVVALIMENTO %>"; 
												} 
										}  
								} else if ("<%= PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" == args[i] || "<%= PSBD.FIELD_NAME_MOTIVAZIONE %>" == args[i]) {
									td.value = element.value;
									} else if ("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>" == args[i]) {
										var codFiscale = getSelectValue(element, false);
										if (td.childNodes[0] != null) {
											td.childNodes[0].data = codFiscale; 
											} else { 
												td.appendChild(document.createTextNode(codFiscale)); 
												}  
										} else { 
											if (td.childNodes[0] != null) { 
												td.childNodes[0].data = element.value; 
												} else {  
													td.appendChild(document.createTextNode(element.value)); 
													}  if ("<%= PSBD.FIELD_NAME_AGG_PERCENTUALE %>" == args[i]) {  
														td.style.display = "none"; 
														}  
													} 
			}  if (prefix == "<%= PSBD.AGGIUDICATARIO%>" && (document.getElementById("sottosogliaEsclusi") == null || "no" == document.getElementById("sottosogliaEsclusi").value)) {
				var argsString = ricreaStringa(args);
				var argshiddenString = ricreaStringa(argshidden);
				var flagsiElement = document.getElementById("<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>");
				var flagnoElement = document.getElementById("<%= PSBD.N_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>");
				var avvalimento = "";
				if (flagsiElement.checked == true) {
					if (flagnoElement.checked == true) {
						avvalimento = "3"; 
						} else { 
							avvalimento = "1";  
							}  
					} else {  
						if (flagnoElement.checked == true) { 
							avvalimento = "2";  
							} else {  avvalimento = "0";  
							} 
						} 
						var td = document.getElementById(idrow + "<%= PSBD.FIELD_NAME_AGG_PARAMETRI_AUSILIARIE%>");
						var den = document.getElementById("<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>").value;
						var cf = document.getElementById("<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>").value;  
						var parametri = "<%= PSBD.ID_TABELLA_AFFIDATARI %>=" + idrow + "&<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>=" + den + "&<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>=" + cf + "&<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>").value + "&<%= PSBD.FIELD_NAME_AGG_RUOLO %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_RUOLO %>").value + "&<%= PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO %>=" + avvalimento + "&<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>").value;  td.innerHTML = "<a href=\"javascript:apriPopUpRubricaDittaAusiliaria('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "','rubricaDittaAusiliaria','<%= PSBD.TAB_AFFIDATARIO %>','Rubrica Operatore Economico','" + parametri + "')\">Gestione Ditte Ausiliarie</a>";  }  for (var i = 0; i < argshidden.length; i++) {  var idtd = idrow + argshidden[i];  var element = document.getElementById(preForReq + argshidden[i]);  if (element != null) {  var td = document.getElementById(idtd);  if ("<%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" == argshidden[i]) {  element = document.getElementById("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>");  var idruolo = getSelectValue(element, true);  td.childNodes[0].data = idruolo;  } else if ("<%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>" == argshidden[i]) {  element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>");  var idruolo = getSelectValue(element, true);  td.childNodes[0].data = idruolo;  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" == argshidden[i]) {  element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_AGG_RUOLO %>");  var idruolo = getSelectValue(element, true);  if (td.childNodes[0] != null) {  td.childNodes[0].data = idruolo;  } else {  td.appendChild(document.createTextNode(idruolo));  }  } else if ("<%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" == argshidden[i]) {  element = document.getElementById("<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>");  var idruolo = getSelectValue(element, true);  td.childNodes[0].data = idruolo;  } else if ("<%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" == argshidden[i]) {  element = document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>");  var idruolo = getSelectValue(element, true);  td.childNodes[0].data = idruolo;  } else {  if (td.childNodes[0] != null) {  td.childNodes[0].data = element.value;  } else {  td.appendChild(document.createTextNode(element.value));  }  }  td.style.display = "none";  }  } }
		
	/// Rinaldo tiket 654 //////////
	function createRow(idrow, args, argshidden, prefix) {  var row = document.createElement("TR");  row.setAttribute("id", idrow, 0);  var tdmod = document.createElement("TD");  tdmod.className = "hmenu";  tdmod.setAttribute("nowrap", "nowrap");  var linkmod = document.createElement("A");  linkmod.appendChild(document.createTextNode("Modifica"));  var argsString = ricreaStringa(args);  var argshiddenString = ricreaStringa(argshidden);  linkmod.setAttribute("href", "javascript:setForModifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  tdmod.appendChild(linkmod);  var linkcanc = document.createElement("A");  linkcanc.appendChild(document.createTextNode("Cancella"));  linkcanc.setAttribute("href", "javascript:deleteRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')", 0);  tdmod.appendChild(linkcanc);  row.appendChild(tdmod);  var already = false;  var preForReq = "";  for (var i = 0; i < args.length; i++) {  var td1 = document.createElement("TD");  td1.setAttribute("nowrap", "nowrap");  td1.className = "garaTd";  var idtd = idrow + args[i];  var element = document.getElementById(preForReq + args[i]);  if ("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" == args[i] || "<%= PSBD.FIELD_NAME_AGG_RUOLO %>" == args[i] || "<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[i] || "<%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" == args[i] || "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>" == args[i] || "<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>" == args[i] || "<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>" == args[i]) {  if ("<%= PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA %>" == args[i]) {  element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_ID_CATEGORIA %>");  }  var contenuto = "";  if ("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[i]) {  contenuto = getSelectValue(element, true);  } else {  contenuto = getSelectValue(element, false);  }  td1.appendChild(document.createTextNode(contenuto));  td1.setAttribute("id", idtd, 0);  } else if (("<%= PSBD.S_FIELD_NAME_CONTENZIOSO_GARA %>" == args[i]) || ("<%= PSBD.N_FIELD_NAME_CONTENZIOSO_GARA %>" == args[i])) {  if (already == false) {  var idField = args[i].substring(2);  idtd = idrow + idField;  if (element.checked == true) {  if (element.value == "S") {  td1.appendChild(document.createTextNode("SI"));  } else {  td1.appendChild(document.createTextNode("NO"));  }  } else {  if (element.value == "S") {  td1.appendChild(document.createTextNode("NO"));  } else {  td1.appendChild(document.createTextNode("SI"));  }  }  already = true;  } else {  already = false;  td1.style.display = "none";  }  td1.setAttribute("id", idtd, 0);  } else if (("<%= PSBD.FIELD_NAME_PREVALENTE %>" == args[i]) || ("<%= PSBD.FIELD_NAME_SCORPORABILE %>" == args[i]) || ("<%= PSBD.FIELD_NAME_SUBAPPALTABILE %>" == args[i])) {  var idField = args[i];  idtd = idrow + idField;  if (element.value == "S") td1.appendChild(document.createTextNode("SI"));  else if (element.value == "N") td1.appendChild(document.createTextNode("NO"));  else td1.appendChild(document.createTextNode(""));  td1.setAttribute("id", idtd, 0);  } else if ("<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" == args[i]) {  var noElement = document.getElementById("<%= PSBD.N_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>");  var idField = args[i].substring(2);  idtd = idrow + idField;  if (element.checked == true) {  if (noElement.checked == true) {  td1.appendChild(document.createTextNode("<%= PSBD.ENTRAMBI_FLAG_AVVALIMENTO %>"));  } else {  td1.appendChild(document.createTextNode("<%= PSBD.REQUISITI_FLAG_AVVALIMENTO %>"));  }  } else {  if (noElement.checked == true) {  td1.appendChild(document.createTextNode("<%= PSBD.ATTTESTAZIONE_FLAG_AVVALIMENTO %>"));  } else {  td1.appendChild(document.createTextNode("<%= PSBD.NESSUNO_FLAG_AVVALIMENTO %>"));  }  }  td1.setAttribute("id", idtd, 0);  } else if ("<%= PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" == args[i]) {  var text = document.getElementById(args[i]).value;  var input = document.createElement("INPUT");  input.setAttribute("id", idtd, 0);  input.setAttribute("type", "text", 0);  input.setAttribute("maxlength", "1024", 0);  input.setAttribute("value", text, 0);  input.disabled = true;  td1.appendChild(input);  } else if ("<%= PSBD.FIELD_NAME_MOTIVAZIONE %>" == args[i]) {  var text = document.getElementById(args[i]).value;  var input = document.createElement("TEXTAREA");  input.setAttribute("id", idtd, 0);  input.setAttribute("rows", "2", 0);  input.setAttribute("cols", "40", 0);  input.value = text;  input.disabled = true;  td1.appendChild(input);  } else if ("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>" == args[i]) {  var testo = getSelectValue(document.getElementById(preForReq + args[i]), false);  td1.appendChild(document.createTextNode(testo));  td1.setAttribute("id", idtd, 0);  } else {  if ("<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" == args[i]) {  var testo = document.getElementById(preForReq + args[i]).value;  if (testo == "0") {  testo = "";  }  td1.appendChild(document.createTextNode(testo));  td1.setAttribute("id", idtd, 0);  } else {  td1.appendChild(document.createTextNode(document.getElementById(preForReq + args[i]).value));  td1.setAttribute("id", idtd, 0);  }  }  if ("<%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>" == args[i]) {  td1.style.display = "none";  }  row.appendChild(td1);  }  if (prefix == "<%=PSBD.AGGIUDICATARIO%>" && (document.getElementById("sottosogliaEsclusi") == null || "no" == document.getElementById("sottosogliaEsclusi").value)) {  var tdmod = document.createElement("TD");  tdmod.className = "hmenu";  tdmod.setAttribute("nowrap", "nowrap");  tdmod.setAttribute("id", idrow + "<%=PSBD.FIELD_NAME_AGG_PARAMETRI_AUSILIARIE%>", 0);  var linkmod = document.createElement("A");  linkmod.appendChild(document.createTextNode("Gestione Ditte Ausiliarie"));  var argsString = ricreaStringa(args);  var argshiddenString = ricreaStringa(argshidden);  var parametri = "<%= PSBD.ID_TABELLA_AFFIDATARI %>=" + idrow + "&<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>").value + "&<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>=" + document.getElementById("<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>").value + "&<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>").value + "&<%= PSBD.FIELD_NAME_AGG_RUOLO %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_RUOLO %>").value + "&<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>=" + document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>").value;  linkmod.setAttribute("href", "javascript:apriPopUpRubricaDittaAusiliaria('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "','rubricaDittaAusiliaria','<%= PSBD.TAB_AFFIDATARIO %>','Rubrica Operatore Economico','" + parametri + "')");  tdmod.appendChild(linkmod);  row.appendChild(tdmod);  var tdaux = document.createElement("TD");  tdaux.style.display = "none";  var idaux = idrow + "<%= PSBD.FIELD_NAME_AGG_LISTA_AUSILIARIE %>";  var hidden = document.createElement("INPUT");  hidden.setAttribute("name", idaux, 0);  hidden.setAttribute("id", "hidden" + idaux, 0);  hidden.setAttribute("type", "hidden", 0);  hidden.value = "";  tdaux.appendChild(hidden);  row.appendChild(tdaux);  }  for (var i = 0; i < argshidden.length; i++) {  var td1 = document.createElement("TD");  var idtd = idrow + argshidden[i];  var element = document.getElementById(preForReq + argshidden[i]);  if ("<%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" == argshidden[i]) {  element = document.getElementById("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>");  var idruolo = getSelectValue(element, true);  td1.appendChild(document.createTextNode(idruolo));  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" == argshidden[i]) {  element = document.getElementById("<%= PSBD.FIELD_NAME_AGG_RUOLO %>");  var idruolo = getSelectValue(element, true);  td1.appendChild(document.createTextNode(idruolo));  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" == argshidden[i]) {  var descrizione = getSelectValue(element, true);  td1.appendChild(document.createTextNode(descrizione));  } else if ("<%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>" == argshidden[i]) {  element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>");  var idruolo = getSelectValue(element, true);  td1.appendChild(document.createTextNode(idruolo));  } else if ("<%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>" == argshidden[i]) {  element = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>");  var idruolo = getSelectValue(element, true);  td1.appendChild(document.createTextNode(idruolo));  } else if ("<%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" == argshidden[i]) {  element = document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>");  var idruolo = getSelectValue(element, true);  td1.appendChild(document.createTextNode(idruolo));  } else {  if (element != null) {  td1.appendChild(document.createTextNode(element.value));  }  }  td1.style.display = "none";  td1.setAttribute("id", idtd, 0);  row.appendChild(td1);  }  return row; }
	////////////////////////////////
	
	function modifyRow(idrow, args, argshidden, prefix) {  if (prefix == "<%= ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>" && document.getElementById("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI%>").selectedIndex <= 0) {  alert("E' necessario scegliere un soggetto per poter proseguire.");  } else {  if (checkBeforeAdd(prefix)) {  var row = document.getElementById(idrow);  var id = row.getAttribute("id");  fillRow(id, args, argshidden, prefix);  appendHidden(row, id, prefix, getArray(prefix, false), false);  setModificato(prefix);  hideSezioneAggiungi(args, argshidden, prefix);  }  } }
	
	function getSelectValue(elem, flagValue) {  var ret = "";  if (elem.selectedIndex >= 0) {  var sele = elem.options[elem.selectedIndex];  ret = sele.text;  if (flagValue == true) {  ret = sele.value;  }  }  return ret; }
    
	function setForModifyRow(idrow, args, argshidden, prefix) {  
		var array = ricreaArray(args, argshidden); 
		resetParameters(prefix, array);  var preForReq = "";  for (var j = 0; j < args.length; j++) {  var idtd = idrow + args[j];  var field = document.getElementById(preForReq + args[j]);  if ("<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" == args[j]) {  idtd = idrow + "<%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>";  var td = document.getElementById(idtd);  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else if ("<%= PSBD.FIELD_NAME_AGG_RUOLO %>" == args[j]) {  idtd = idrow + "<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>";  var td = document.getElementById(idtd);  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else if ("<%= PSBD.FIELD_NAME_TIPO_FINANZIAMENTO %>" == args[j]) {  idtd = idrow + "<%= PSBD.FIELD_NAME_DES_FINANZIAMENTO %>";  var td = document.getElementById(idtd);  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else if ("<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>" == args[j]) {  idtd = idrow + "<%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>";  var td = document.getElementById(idtd);  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else if ("<%= PSBD.FIELD_NAME_ID_CATEGORIA %>" == args[j]) {  var td = document.getElementById(idtd);  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else if ("<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>" == args[j]) {  var td = document.getElementById(idtd);  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  tablevalue = tablevalue.split(" ")[0];  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else if (("<%= PSBD.S_FIELD_NAME_CONTENZIOSO_GARA %>" == args[j]) || ("<%= PSBD.N_FIELD_NAME_CONTENZIOSO_GARA %>" == args[j])) {  var idField = args[j].substring(2);  idtd = idrow + idField;  var td = document.getElementById(idtd);  var tablefield = td.childNodes[0].data;  if (tablefield == "SI") {  if (field.value == "S") {  field.checked = true;  already = true;  } else {  field.checked = false;  }  } else {  if (field.value == "S") {  field.checked = false;  } else {  field.checked = true;  already = true;  }  }  } else if (("<%= PSBD.FIELD_NAME_PREVALENTE %>" == args[j])  || ("<%= PSBD.FIELD_NAME_SCORPORABILE %>" == args[j])  || ("<%= PSBD.FIELD_NAME_SUBAPPALTABILE %>" == args[j])) {  var idField = args[j];  idtd = idrow + idField;  var td = document.getElementById(idtd);  var tablefield = td.childNodes[0].data;  if (tablefield == "SI") field.value = "S";  else if (tablefield == "NO") field.value = "N";  else field.value = "";  already = true;  field.selectedIndex = cercaIndex(field.options, 0, field.value);  } else if ("<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" == args[j]) {  var noElement = document.getElementById("<%= PSBD.N_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>");  var idField = args[j].substring(2);  idtd = idrow + idField;  var td = document.getElementById(idtd);  var tablefield = td.childNodes[0].data;  if (tablefield == "<%= PSBD.ENTRAMBI_FLAG_AVVALIMENTO %>") {  field.checked = true;  noElement.checked = true;  } else if (tablefield == "<%= PSBD.REQUISITI_FLAG_AVVALIMENTO %>") {  field.checked = true;  noElement.checked = false;  } else if (tablefield == "<%= PSBD.ATTTESTAZIONE_FLAG_AVVALIMENTO %>") {  field.checked = false;  noElement.checked = true;  } else {  field.checked = false;  noElement.checked = false;  }  } else if ("<%= PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" == args[j] || "<%= PSBD.FIELD_NAME_MOTIVAZIONE %>" == args[j]) {  var td = document.getElementById(idtd);  if (td != null) {  var text = td.value;  if (field != null) {  field.value = text;  }  }  }  if ("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>" == args[j]) {  var td = document.getElementById("hidden" + idtd);  if (td != null) {  var text = td.value;  if (field != null) {  for (i = 0; i < field.length; i++) if (field.options[i].text == text) {  field.selectedIndex = i;  break;  }  }  }  }  if ("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" == args[j] && prefix == "<%=PSBD.DITTA_AUSILIARIA%>") {  var td = document.getElementById(idtd);  if (td != null) {  if (td.innerHTML != null && td.innerHTML != "") field.value = td.innerHTML;  else field.value = "IT";  }  } else if ("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" == args[j] && prefix != "<%=PSBD.DITTA_AUSILIARIA%>") {  var td = document.getElementById("hidden" + idtd);  if (td != null) {  var text = td.value;  if (field != null) {  if (text != "") field.value = text;  else field.value = "IT";  }  }  } else {  var td = document.getElementById(idtd);  if (td != null) {  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  if (field != null) {  field.value = tablevalue;  }  }  }  }  for (var j = 0; j < argshidden.length; j++) {  var idtd = idrow + argshidden[j];  var td = document.getElementById(idtd);  if (td != null) {  var tablevalue = "";  if (td.childNodes[0] != null) tablevalue = td.childNodes[0].data;  var field = document.getElementById(preForReq + argshidden[j]);  if ("<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" == argshidden[j]) {  var td = document.getElementById(idtd);  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else if ("<%= PSBD.FIELD_NAME_ID_CLASSE_IMPORTO %>" == argshidden[j]) {  field = document.getElementById(preForReq + "<%= PSBD.FIELD_NAME_CLASSE_IMPORTO %>");  var optarray = field.options;  field.selectedIndex = cercaIndex(optarray, 0, tablevalue);  } else {  field.value = tablevalue;  }  }  }  var button = document.getElementById("AddMod" + prefix);  var argsString = ricreaStringa(args);  var argshiddenString = ricreaStringa(argshidden);  button.href = "javascript:modifyRow('" + idrow + "',[" + argsString + "],[" + argshiddenString + "],'" + prefix + "')";  button.innerHTML = "Modifica";  var idtable = "idTabella" + prefix;  var table = document.getElementById(idtable);  var inmodifica = document.getElementById("inModifica" + prefix);  if (inmodifica == null) {  inmodifica = document.createElement("TD");  inmodifica.style.display = "none";  inmodifica.setAttribute("id", ("inModifica" + prefix), 0);  table.appendChild(inmodifica);  }  inmodifica.value = idrow;  showSezioneAggiungi(args, argshidden, prefix); }
	
	function cercaIndex(array, index, value) {
		if (index == array.length)
			return - 1;
		if (array[index].value == value)
			return index;
		return cercaIndex(array, (index + 1), value); 
		}

	
	function hasErrors(form){
		var ret = false;
		for (var i=0;i<form.length;i++){ 
		if(form.elements[i].type=='text' && form.elements[i].style.borderColor=='red red red red'){
		ret = true;
		break;
		}}
		return ret;
		}
	function gt(e){return document.getElementById(e);}
	
</script>