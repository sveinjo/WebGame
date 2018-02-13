
function moveSwitch(entityId)
{
	
	//alert("switching " + entityId);
	getApplet().actionSwitch(entityId);	
	normalizeOverlays(entityId);
	clearInterface();
}

function defend()
{		
	getApplet().actionDefend();	
	//normalizeOverlays(entityId)
	clearInterface();
}

function move(direction)
{	
	//posX = ((getX(dInterface)) / size) + 1;
	//posY = ((getY(dInterface)) / size) + 1;
	
	wSetVisible(dInterface, false);		

	//getApplet().actionMove(posX, posY, direction);
	getApplet().actionMove(direction);

	//hideBlock();
	clearInterface();
}



function attack(strDefender)
{
	
	//normalizeOverlays(strDefender);
	//posX = ((dInterface.x) / size) + 1;
	//posY = ((dInterface.y) / size) + 1;	
	
	//getApplet().actionAttack(posX, posY, strDefender);
	getApplet().actionAttack(strDefender);
	
	//hideAction(strDefender);
	clearInterface();
	
}



/*function test()
{	
	//mylayer = new DynLayer('ent1' + 'Life', 'ent1')		
	//mylayer.write('<table width=95 height=11 border=0 cellspacing=0><tr><td width=50% BGCOLOR="#ff0000"></td><td></td></tr></table>')
	
	//dInterface.hide();
	//parent.chatframe.TestApplet.actionMove(4, 2, 3, 2);
	
	//dInterface = new DynLayer('interface');		
	
	alert("test");
}*/


