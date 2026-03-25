function getScrollXY() {
  var scrOfX = 0, scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    //Netscape compliant
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    //DOM compliant
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
    //IE6 standards compliant mode
    scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
  return [ scrOfX, scrOfY ];
}


	
 	
 	

// Browser safe opacity handling function

function setOpacity( divId, value ) {
 
  document.getElementById(divId).style.opacity = value / 10;
  document.getElementById(divId).style.filter = 'alpha(opacity=' + value * 10 + ')';
 
 
}

function setOpacityOverlay(divId, value ) {
 if(value <5){
   document.getElementById(divId).style.opacity = value / 10 ;
   document.getElementById(divId).style.filter = 'alpha(opacity=' + value * 10 + ')';
 }
 
}

function fadeInMyPopup(divId,overlayId) {

 for( var i = 0 ; i <= 100 ; i++ ){
   
   
   setTimeout( 'setOpacity("'+ divId + '",' + (i / 10) + ')' , 5 * i );
  setTimeout( 'setOpacityOverlay("' + overlayId + '",' + (i / 10) + ')' , 5 * i );
   
   }
}

function fadeOutMyPopup(divId, overlayId) {
 for( var i = 0 ; i <= 100 ; i++ ) {
  
   setTimeout( 'setOpacity("' + divId + '",' + (10 - i / 10) + ')' , 5 * i );
   setTimeout( 'setOpacityOverlay("' + overlayId + '",' + (10 - i / 10) + ')' , 5 * i );
    
 }

 setTimeout('closeMyPopup("' + divId + '","' + overlayId + '")', 800 );
}


function closeMyPopup(popupId,overlayId) {
 
 document.getElementById(popupId).style.display = "none";
 document.getElementById(overlayId).style.display = "none";
 
}

function fireMyPopup(popupId,overlayId) {
    
	 setOpacity(popupId, 0 );
	 document.getElementById(popupId).style.display = "block";
	 document.getElementById(overlayId).style.display = "block";
	 fadeInMyPopup(popupId,overlayId);
 
}

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
};
