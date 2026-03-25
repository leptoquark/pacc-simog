/* This script and many more are available free online at
The JavaScript Source!! http://javascript.internet.com
Created by: Steve Chipman | http://slayeroffice.com/ */
$(document).ready(function () {


// constants to define the title of the alert and button text.
var ALERT_TITLE = "ATTENZIONE!";
var ALERT_BUTTON_TEXT = "OK";

// over-ride the alert method only if this a newer browser.
// Older browser will see standard alerts
if(document.getElementById) {
  window.customAlert = function(txt) {
    createCustomAlert(txt);
  }
}



function disableQuote() {
	
	var isEdit = $("#cigLottoEdit").length > 0;
	if ($('#flagPrevisioneQuotaHere').prop('disabled')) {
		
		
			console.log("entrato dis 1");
			document.getElementById('quota_giovanile').disabled=true;
			document.getElementById('quota_femminile').disabled=true;
		

		
	} else {
		if ($("#flagPrevisioneQuotaHere").length > 0  &&  document.getElementById('flagPrevisioneQuotaHere').value == "Q"){
			
			document.getElementById('quota_giovanile').disabled=false;
			document.getElementById('quota_femminile').disabled=false;
			//$(".checkboxfieldsMD").prop("checked", false);
			$(".checkboxfieldsMD").prop("disabled", false);
			
			if (document.getElementById('urgenzaDL133_session')) {
				if (!isEdit && document.getElementById('urgenzaDL133_session').value == 'S'){
					$(".checkboxfieldsMD").prop("checked", false);
					$("#Id_Motivo_Deroga3").prop("checked", true); 			
				}
			}
			
			
		} else if($("#flagPrevisioneQuotaHere").length > 0  &&  document.getElementById('flagPrevisioneQuotaHere').value == "N"){
			document.getElementById('quota_giovanile').disabled=true;
			document.getElementById('quota_femminile').disabled=true;
			document.getElementById('quota_giovanile').value=null;
			document.getElementById('quota_femminile').value=null;
			//$('.checkboxfieldsMD').prop('checked', false);
			$( ".checkboxfieldsMD" ).prop("disabled", false);	

			if (!isEdit && document.getElementById('urgenzaDL133_session').value == 'S'){
				$(".checkboxfieldsMD").prop("checked", false);
				$("#Id_Motivo_Deroga3").prop("checked", true); 			
			}

		} else {
		
			if ($("#quota_giovanile").length > 0 ) {
				document.getElementById('quota_giovanile').disabled=true;
				document.getElementById('quota_giovanile').value=null;
			}
			if ($("#quota_femminile").length > 0 ){
				document.getElementById('quota_femminile').disabled=true;
				document.getElementById('quota_femminile').value=null;
			}

			if ($(".checkboxfieldsMD").length > 0){
				$(".checkboxfieldsMD").prop("checked", false); 			
				$(".checkboxfieldsMD").prop("disabled", true);
			}

		}

	}
	
	
}

function disableMisurePremiali() {
	
	if ($('#flagPrevisioneQuotaHere').prop('disabled')) {
		$(".checkboxfieldsMP").prop("disabled", true);
	}else {
		if (document.getElementById('flagMisurePremialiHere') != null &&
				document.getElementById('flagMisurePremialiHere').value == "S"){
			$(".checkboxfieldsMP").prop("disabled", false);
		} else {
			if ($(".checkboxfieldsMP").length > 0){
				$(".checkboxfieldsMP").prop("checked", false);
				$(".checkboxfieldsMP").prop("disabled", true);
			}
		}
	}
	
	
	
	
}





function createCustomAlert(txt) {
  // shortcut reference to the document object
  d = document;

  // if the modalContainer object already exists in the DOM, bail out.
  if(d.getElementById("modalContainer")) return;

  // create the modalContainer div as a child of the BODY element
  mObj = d.getElementsByTagName("body")[0].appendChild(d.createElement("div"));
  mObj.id = "modalContainer";
   // make sure its as tall as it needs to be to overlay all the content on the page
  mObj.style.height = document.documentElement.scrollHeight + "px";

  // create the DIV that will be the alert 
  alertObj = mObj.appendChild(d.createElement("div"));
  alertObj.id = "alertBox";

  // create an H1 element as the title bar
  h1 = alertObj.appendChild(d.createElement("h1"));
  h1.appendChild(d.createTextNode(ALERT_TITLE));

  // create a paragraph element to contain the txt argument
  msg = alertObj.appendChild(d.createElement("p"));
  msg.innerHTML = txt;

  // create an anchor element to use as the confirmation button.
  btn = alertObj.appendChild(d.createElement("a"));
  btn.id = "closeBtn";
  btn.appendChild(d.createTextNode(ALERT_BUTTON_TEXT));
  btn.href = "#";
  // set up the onclick event to remove the alert when the anchor is clicked
  btn.onclick = function() { removeCustomAlert();return false; }

  // MSIE doesnt treat position:fixed correctly, so this compensates for positioning the alert
  if(d.all && !window.opera) alertObj.style.top = document.documentElement.scrollTop + "px";
  // center the alert box
 alertObj.style.Left = (d.documentElement.scrollWidth - alertObj.style.Width)/2 + "px";
  
}



// removes the custom alert from the DOM
function removeCustomAlert() {
  document.getElementsByTagName("body")[0].removeChild(document.getElementById("modalContainer"));
}

$("#flagPrevisioneQuotaHere").change(
	disableQuote
);

$("#flagMisurePremialiHere").change(
	disableMisurePremiali
);



disableQuote(); 
disableMisurePremiali(); 

});



