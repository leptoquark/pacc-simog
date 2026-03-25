<SCRIPT type="text/javascript">
<!--
function ajaxRead(id){
  var xmlObj = null;
  function callback(){
    if (xmlObj.readyState == 4 && xmlObj.status == 200){
        el = document.getElementById(id);
        el.innerHTML = xmlObj.responseText;
    }
  } 
  var ua = navigator.userAgent.toLowerCase();
   if (!window.ActiveXObject){
     xmlObj = new XMLHttpRequest();
   }else if (ua.indexOf('msie 5') == -1){
     xmlObj = new ActiveXObject("Msxml2.XMLHTTP");
   }else{
     xmlObj = new ActiveXObject("Microsoft.XMLHTTP"); }
   xmlObj.onreadystatechange = callback;
   xmlObj.open("GET", 'Paesi', true);
   xmlObj.send(null);
   
  
                 
}
//-->
	
</SCRIPT>