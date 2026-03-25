<script type="text/javascript">

var blnDOM = false, blnIE4 = false, blnNN4 = false;

if (document.layers) blnNN4 = true;
else if (document.all) blnIE4 = true;
else if (document.getElementById) blnDOM = true;

function getKeycode(e)
{
  var key=0;
  
  if (blnNN4)
  {
    key = e.which
  }
  if (blnDOM)
  {
    key = e.which
  }
  if (blnIE4)
  {
    key = event.keyCode
  }

  if(key==116 || key==8 || (key==82 && e.ctrlKey==1)) 
  {
    return false;
  }
}

document.onkeypressed = getKeycode
if (blnNN4) document.captureEvents(Event.KEYDOWN)

</script>
