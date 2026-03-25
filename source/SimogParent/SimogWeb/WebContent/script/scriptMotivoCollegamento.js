/**
 * 
 */

//TICKET ALM - 3.04.3 #2846
function clearSelected(comboSorgente){
    var elements = document.getElementById(comboSorgente).options;

    for(var i = 0; i < elements.length; i++){
      elements[i].selected = false;
      elements[i].removeAttribute("selected");
    }
  }
//impostazione valore comboDestinazione in base al valore comboSorgente
function setCombo(comboSorgente){

	if (typeof comboSorgente == 'string' || comboSorgente instanceof String) {
       if(comboSorgente == 'sel_CONTRAENTE') {
	      if (document.getElementById(comboSorgente).value == 31) {
	    	  clearSelected('sel_MOTIVO');
              var opts2 = document.getElementById('sel_MOTIVO').options;
              for(var opt2, y = 0; opt2 = opts2[y];y++){
            	  if(opt2.value == 8) {
            		  document.getElementById('sel_MOTIVO').selectedIndex = y;
            		  document.getElementById('sel_MOTIVO').options[y].setAttribute("selected","selected");
            		  document.getElementById('sel_MOTIVO').disabled = true;
            		  document.getElementById('ID_motivoCollegamento').value = 8;
            		  break;
            	  }
              }
	    	  
	      }  else {
	    	  document.getElementById('sel_MOTIVO').disabled = false;
	      }
       } else if(comboSorgente == 'sel_FLAG_PREVEDE_RIP') {
    	   if (document.getElementById('sel_MOTIVO').disabled==false) {
    		   clearSelected('sel_MOTIVO');
    		   if(document.getElementById(comboSorgente).value == 'S'){
    			   var opts2 = document.getElementById('sel_MOTIVO').options;
    	              for(var opt2, y = 0; opt2 = opts2[y];y++){
    	            	  if(opt2.value == 10) {
    	            		  document.getElementById('sel_MOTIVO').selectedIndex = y;
    	            		  document.getElementById('sel_MOTIVO').options[y].setAttribute("selected","selected");
    	            		  document.getElementById('sel_MOTIVO').disabled = true;
    	            		  document.getElementById('ID_motivoCollegamento').value = 10;
    	            		  break;
    	            	  }
    	              }
    		   } else {
	    	      document.getElementById('sel_MOTIVO').disabled = false;
	           }
    		       
    	   }
       }
	    
	}
}


function setMotivo(){
	document.getElementById('ID_motivoCollegamento').value = document.getElementById('sel_MOTIVO').value;
}

function checkDisable(){
	
	if(document.getElementById('sel_CONTRAENTE')==null)
		return;
	
	var motivoSel = document.getElementById('sel_CONTRAENTE').value;
	if(motivoSel== 31 || motivoSel==35)
		document.getElementById('sel_MOTIVO').disabled = true;
}
//FINE TICKET ALM - 3.04.3 #2846