function yadm(idlist)
{
	var parentClass='isParent';
	var activeParentClass='isActive';
	var preventHoverClass='nohover';	
	var indicateJSClass='dhtml';		
	var toHideClass='hiddenChild';	
	var toShowClass='shownChild';		
	var currentClass='current';		
	var d=document.getElementById(idlist);	

	if(!document.getElementById && !document.createTextNode){return;}

	if(d)
	{
		d.className+=d.className==''?indicateJSClass:' '+indicateJSClass;
		var lis,i,firstUL,j,apply;

		lis=d.getElementsByTagName('li');
		for(i=0;i<lis.length;i++)
		{
			firstUL=lis[i].getElementsByTagName('ul')[0]
			if(firstUL)
			{
				lis[i].childNodes[0].onclick=function(){return false;}
				lis[i].className+=lis[i].className==''?parentClass:' '+parentClass;
				apply=true;
				if(new RegExp('\\b'+currentClass+'\\b').test(lis[i].className)){apply=false;}
				if(apply)
				{
					for(j=0;j<firstUL.getElementsByTagName('li').length;j++)
					{
						if(new RegExp('\\b'+currentClass+'\\b').test(firstUL.getElementsByTagName('li')[j].className)){apply=false;break}
					}
				}
				if(apply)
				{
					firstUL.className+=firstUL.className==''?toHideClass:' '+toHideClass;
					if(new RegExp('\\b'+preventHoverClass+'\\b').test(d.className))
					{
						lis[i].onclick=function(){doyadm(this);}
					} else {
						lis[i].onclick=function(){doyadm(this);}
						lis[i].onmouseover=function(){doyadm(this);}
						lis[i].onmouseout=function(){doyadm(null);}
					}
				} else {
					lis[i].keepopen=1;
					firstUL.className+=firstUL.className==''?toShowClass:' '+toShowClass;
					lis[i].className=lis[i].className.replace(parentClass,activeParentClass);
				}
			}
		}
	}

	function doyadm(o)
	{
		var childUL,isobj,swap;
		lis=d.getElementsByTagName('li');
		for(i=0;i<lis.length;i++)
		{
			isobj=lis[i]==o;
			swap=function(tmpobj,tmporg,tmprep)
			{
				tmpobj.className=tmpobj.className.replace(tmporg,tmprep)		
			}
			if(!lis[i].keepopen)
			{
				childUL=lis[i].getElementsByTagName('ul')[0];
				if(childUL)	
				{	
					if(new RegExp('\\b'+preventHoverClass+'\\b').test(d.className))
					{
						if(new RegExp('\\b'+activeParentClass+'\\b').test(lis[i].className))
						{
							swap(childUL,isobj?toShowClass:toHideClass,isobj?toHideClass:toShowClass);		
							swap(lis[i],isobj?activeParentClass:parentClass,isobj?parentClass:activeParentClass);		
						} else {
	
							swap(childUL,isobj?toHideClass:toShowClass,isobj?toShowClass:toHideClass);		
							swap(lis[i],isobj?parentClass:activeParentClass,isobj?activeParentClass:parentClass);		
						}
					} else {
							swap(childUL,isobj?toHideClass:toShowClass,isobj?toShowClass:toHideClass);		
							swap(lis[i],isobj?parentClass:activeParentClass,isobj?activeParentClass:parentClass);		
					}
				} 
			}
		}
	}
}

function loadLists(lists){
	for(var i = 0;i<lists.length;i++)
		yadm(lists[i]);
} 