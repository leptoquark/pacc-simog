/**
 * Funzioni per la gestione dei soggetti aggregatori
 * 
 */

//TICKET ALM #4222 - 3.04.4
//Metodo che chiama in modo asincrono la servlet che verifica la possibilita di adesione a una iniziativa
function doCallSoggAggrGara(categorie,action) {

        var from = "gara";
	
	    var idSA = $("input[name=idStazioneAppaltante]:checked").val();
	    var cigAQ = $("input[name=CIGQUADRO]").val();
	   
		      $.get("SrvVerificaIniziativeSoggAggr", {
		             categorieSel : categorie,
		             saSelezionata : idSA,
		             cigAccordoQuadro : cigAQ,
		             fromPage : from
		         }, function(responseText) {        
		               if(noPopUpAppear(responseText)){
			               if(action==null)
		            	      validateAndAction();
			               else
			            	   doAction(action);
		               } else
		            	   opendialogSoggAggr(responseText,from);
		                   
		        });
}

function doCallSoggAggrLotto(action,gara,isAdmin,is3044) {
	console.log("doCallSoggAggrLotto");
	/* MEV 46487  3.04.11 */
	if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('warning.cigValid'); } else { alert("AVVISO: Si ricorda il CIG generato e' valido solo per i casi disciplinati dalla delibera 582 del 13 dicembre 2023 e che pertanto la pubblicazione deve essere completata entro 48 ore dalla relativa data di creazione"); }
	
	 if( $("input[name=NrCPV]") != null){
		 var numCpvSec = $("input[name=NrCPV]").val();
		 if(numCpvSec==0) {
			 if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.verifyCPV'); } else { alert("Verificare di aver inserito correttamente tutte le CPV (prevalente e secondarie) nella scheda lotto"); }
		 }
	 }

    var from = "lotto";
    var categoria = $("select[name=idCatLotto]").val();
    
    var hiddenaction = null;
    if( $("input[name=FIELD_NAME_CIG_INIZIATIVA_SEL]") != null)
    	hiddenaction = $("input[name=action]").val();
    	
    //MEV 3.04.10 43227
    if(hiddenaction!="modificaDatiCup" && hiddenaction!="modificaDatiPerfezionamento" && is3044 && (categoria == null || categoria =="")){
    	if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.selectCategory'); } else { alert("Selezionare un valore nel campo 'Categoria merceologica di cui al DPCM soggetti aggregatori'"); }
    	return;
    }
    
    var importoLotto = $("input[name=importoLottoEuro]").val();
    
  //MEV 3.04.10 43227
    if(hiddenaction!="modificaDatiCup" && hiddenaction!="modificaDatiPerfezionamento" && (importoLotto == null || importoLotto =="")){
    	if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.importoLottoRequired'); } else { alert("SIMOG_LOTTO_014 - Il campo 'Importo Lotto' deve essere valorizzato"); }
    	return;
    }
    
    var cigIniz = $("input[name=FIELD_NAME_CIG_INIZIATIVA_SEL]").val();
    var flagNoDpcm = $("input[name=FIELD_NAME_FLAG_SA_NO_DPCM]").val();
    var flagNoClass = $("input[name=FIELD_NAME_FLAG_SA_NO_CLASSIFICATA]").val();
    
    var cigOrAutodichiarazioneNotSet = cigIniz == "" && flagNoDpcm=="" && flagNoClass=="";

  //MEV 3.04.10 43227
     //Se il RUP ha gia' scelto di non aderire a nessuna iniziativa, non rieffettuare il controllo
     if(hiddenaction!="modificaDatiCup" && hiddenaction!="modificaDatiPerfezionamento" && hiddenaction!="modificaRipetizioni" && cigOrAutodichiarazioneNotSet && is3044 && !isAdmin && categoria!="999"){
	      $.get("SrvVerificaIniziativeSoggAggr", {
	             categorieSel : categoria,
	             idGara : gara,
	             fromPage : from,
	             importo : importoLotto
	         }, function(responseText) {        
	               if(noPopUpAppear(responseText)){
		               if(action==null)
	            	      validateAndAction();
		               else
		            	   doActionModifica(action);
	               } else
	            	   opendialogSoggAggr(responseText,from);
	                   
	        });
     } else {
    	 if(action==null)
   	         validateAndAction();
          else
       	     doActionModifica(action);
         }	
}

function noPopUpAppear(responseText) {
	if(responseText=="NESSUNA INIZIATIVA" || responseText=="INIZIATIVA PRESENTE" || responseText=="SA IS SOGG.AGGR.")
		return true;
	return false;
}


function opendialogSoggAggr(iniziative,from) {
    var divDialog = '#dialogSoggAggr';
    
	  var $dialog = $(divDialog)
	  .html('<iframe id="popupiframe" style="border: 0px; " src="SrvCaricaIniziativeSoggAggr?iniziative='+iniziative+'&from='+from+'" width="100%" height="100%"></iframe>')
	  .dialog({
	    title: "ANAC: Autorit&agrave; Nazionale Anticorruzione",
	    autoOpen: false,
	    dialogClass: 'no-close',
	    modal: true,
	    height: 700,
	    minWidth: 1100,
	    minHeight: 600,
	    draggable:true,
	  /*  close: function () { 
          var iframe = document.getElementById('popupiframe');
          var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
          var cmdConf = innerDoc.getElementById('cmdConf').disabled;
          
          setRetVal('id_info',true,idDialog,cmdConf); 
          $(this).empty();
      
      },*/
	   /* buttons: { "Ok": function () {         $(this).dialog("close"); } }*/
	  });
	  $dialog.dialog('open');
	}


function resetSoggAggrFields() {
	 $("input[name='FIELD_NAME_FLAG_SA_NO_DPCM']").val("");
     $("input[name='FIELD_NAME_FLAG_SA_NO_CLASSIFICATA']").val("");
  	 $("input[name='FIELD_NAME_CIG_INIZIATIVA_SEL']").val("");
}