ns4 = (document.layers)? true:false
ie4 = (document.all)? true:false

function editLayer(id,trigger,lax,lay,content)
{

// Layer visible
if (trigger=="1"){
	if (document.layers) document.layers[''+id+''].visibility = "show"
	else if (document.all) document.all[''+id+''].style.visibility = "visible"
	else if (document.getElementById) document.getElementById(''+id+'').style.visibility = "visible"				
	}
// Layer hidden
else if (trigger=="0"){
	if (document.layers) document.layers[''+id+''].visibility = "hide"
	else if (document.all) document.all[''+id+''].style.visibility = "hidden"
	else if (document.getElementById) document.getElementById(''+id+'').style.visibility = "hidden"				
	}
// Set horizontal position	
if (lax){
	if (document.layers){document.layers[''+id+''].left = lax}
	else if (document.all){document.all[''+id+''].style.left=lax}
	else if (document.getElementById){document.getElementById(''+id+'').style.left=lax+"px"}
	}
// Set vertical position
if (lay){
	if (document.layers){document.layers[''+id+''].top = lay}
	else if (document.all){document.all[''+id+''].style.top=lay}
	else if (document.getElementById){document.getElementById(''+id+'').style.top=lay+"px"}
	}
// change content

if (content){
if (document.layers){
	sprite=document.layers[''+id+''].document;
	// add father layers if needed! document.layers[''+father+'']...
  	sprite.open();
  	sprite.write(content);
  	sprite.close();
	}
else if (document.all) document.all[''+id+''].innerHTML = content;
else if (document.getElementById){
	//Thanx Reyn!
	rng = document.createRange();
	el = document.getElementById(''+id+'');
	rng.setStartBefore(el);
	htmlFrag = rng.createContextualFragment(content)
	while(el.hasChildNodes()) el.removeChild(el.lastChild);
	el.appendChild(htmlFrag);
	// end of Reyn ;)
	}
}
}

function setVisibility(id, trigger)
{
	// Layer visible
if (trigger=="1"){
	if (document.layers) document.layers[''+id+''].visibility = "show"
	else if (document.all) 
	{
		document.all[''+id+''].style.visibility = "visible";		
	}
	else if (document.getElementById) document.getElementById(''+id+'').style.visibility = "visible"				
	}
// Layer hidden
else if (trigger=="0"){
	if (document.layers) document.layers[''+id+''].visibility = "hide"
	else if (document.all) 
	{
		document.all[''+id+''].style.visibility = "visible";
		document.all[''+id+''].filters.item(0).Apply();	
		document.all[''+id+''].style.visibility = "hidden";
		document.all[''+id+''].filters.item(0).Play();	
	}
	else if (document.getElementById) document.getElementById(''+id+'').style.visibility = "hidden"				
	}	
}

function setPosX(id,lax)
{
// Set horizontal position	
if (lax){
	if (document.layers){document.layers[''+id+''].left = lax}
	else if (document.all){document.all[''+id+''].style.left=lax}
	else if (document.getElementById){document.getElementById(''+id+'').style.left=lax+"px"}
	}
	
}

function changeContent(id,content)
{
if (content){
if (document.layers){
	sprite=document.layers[''+id+''].document;
	// add father layers if needed! document.layers[''+father+'']...
  	sprite.open();
  	sprite.write(content);
  	sprite.close();
	}
else if (document.all) document.all[''+id+''].innerHTML = content;
else if (document.getElementById){
	//Thanx Reyn!
	rng = document.createRange();
	el = document.getElementById(''+id+'');
	rng.setStartBefore(el);
	htmlFrag = rng.createContextualFragment(content)
	while(el.hasChildNodes()) el.removeChild(el.lastChild);
	el.appendChild(htmlFrag);
	// end of Reyn ;)
	}
}
}