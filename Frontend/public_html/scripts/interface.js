
//var combatArray = new Array();
//var counter = 0;	

var vTimeout;
var vTimeout2;
	
size = 120;	
offset = 0;

iDelay = 10;
lastFocus = null;
lastMarked = null;
turnEntity = null;

dInterface = null;
nwDiv = null;
nDiv = null;
neDiv = null;
wDiv = null;
eDiv = null;
swDiv = null;
sDiv = null;
seDiv = null;


function createPlayfield(sizeX, sizeY) 
{
	
	width = eval(sizeX * size);
	height = eval(sizeY * size);
	wInitialize(width + 200, height + 200);
	//wCreateLayer(850,0,250,200,'<a onClick="javascript:move(7);">left</a><BR><a onClick="javascript:move(1);">up</a><BR><a onClick="javascript:move(3);">right</a><BR><a onClick="javascript:move(5);">down</a><BR><a onClick="javascript:dInterface.show();">refresh</a>', null, 'testLayer');
	
	wCreateLayer(offset, offset, width, height, '<table onMouseOver="cancelTimeout();" width="' + width + '" height="' + height + '" background="pictures/chessboard.png"><TR><TD></TD></TR></table>', null, 'playfield');	
	//wCreateLayer(offset, offset, width, height, '<table width="' + width + '" height="' + height + '" background="pictures/test1.JPG"><TR><TD></TD></TR></table>', null, 'playfield');	
	
	createInterface();
	
	var tName;
	for (var x=0; x < sizeX; x = x + 1) 
	{
		for (var y=0; y < sizeY; y = y + 1) 
		{
			tName = 'tile' + x + '_' + y;
			wCreateLayer(x * size, y * size, size, size, '<img onMouseOver="cancelTimeout();" src="pictures/black.gif">', 'playfield', tName);
			//wCreateLayer(x * size, y * size, size, size, '<img src="pictures/black.gif">', 'playfield', tName);
					
		}
	}

		
}

function defineRoom(strRoom)
{
	//alert(strRoom);
	var aTemp = strRoom.split(" ");
	for (var n=0; n < aTemp.length; n = n + 2)
	{
		//tileDiv = new DynLayer('tile' + aTemp[n] + '_' + aTemp[n+1]);
		//tileDiv = wMakeDynLayer('tile' + aTemp[n] + '_' + aTemp[n+1], 'playField')
		
		//tileDiv.hide();
		
		wDestroyLayer('tile' + aTemp[n] + '_' + aTemp[n+1], 'playfield');
					
	}
		
}

function getApplet()
{
	return(parent.chatframe.document.applets[0]);
}

function cancelTimeout()
{
	clearTimeout(vTimeout);
	//hideBlock();
	//hideLastAction();	
}

//----------------------------------------

function normalizeOverlays(strId)
{
	overlay = wMakeDynLayer(strId + 'Overlay', strId);
	lastOverlay = wMakeDynLayer(lastFocus + 'Overlay', strId);
	
	
	wSetHTML(overlay, '<img onMouseOut="cancelTimeout()" onMouseOver="javascript:showAction(\'' + strId + '\')" src="pictures/empty.gif" border="0">');
	wSetHTML(lastOverlay, '<img onMouseOut="cancelTimeout()" onMouseOver="javascript:showAction(\'' + lastFocus + '\')" src="pictures/empty.gif" border="0">');	
}



//---------------------------

function hideLastAction()
{
	/*//if (lastMarked != null)
	try
	{		
		lMarked = wMakeDynLayer(lastMarked + 'Overlay', lastMarked);	
		//wSetHTML(lMarked, '<img onMouseOver="javascript:showAction(\'' + lastMarked + '\')" src="pictures/empty.gif" border="0">');
		wSetHTML(overlay, '<img onMouseOut="cancelTimeout()" onMouseOver="javascript:showAction(\'' + lastMarked + '\')" src="pictures/empty.gif" border="0">');

		lFocus = wMakeDynLayer(lastFocus + 'Overlay', lastFocus);	
		wSetHTML(lFocus, '<img onMouseOut="cancelTimeout()" onMouseOver="javascript:showAction(\'' + lastFocus + '\')" src="pictures/empty.gif" border="0">');
	}
	catch(exp)
	{}
	*/
}

function showAction(strId)
{
	cancelTimeout();
	sAction = 'showAction2("' + strId + '");';
	vTimeout = setTimeout(sAction, iDelay);	
	//alert(bTimeout);
}

function hideAction(strId)
{
	//cancelTimeout();
	
	try
	{		
		normalizeOverlays(strId);
	}
	catch (exp)
	{}
}



function showAction2(strId)
{	
	//hideBlock();
	hideLastAction();
	
	try
	{
		iAction = getApplet().checkAction(strId);
		if (iAction == 1)
		{	
			//overlay = new DynLayer(strId + 'Overlay');
			overlay = wMakeDynLayer(strId + 'Overlay', strId);
			//overlay.write('<img onClick="javascript:attack(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/fight.gif" border="0">');
			wSetHTML(overlay, '<img onClick="javascript:attack(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/fight.gif" border="0">');
		}
		else if (iAction == 2)
		{
			//Defend
			overlay = wMakeDynLayer(strId + 'Overlay', strId);			
			wSetHTML(overlay, '<img onClick="javascript:defend(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/defend.gif" border="0">');
		}
		else if (iAction >= 10)
		{
			//Switch				
			eSwitch = wMakeDynLayer(strId + 'Overlay');
			eSelf = wMakeDynLayer(lastFocus + 'Overlay');		
			//alert(entityTemp2.x / size + " --- " + entityTemp.x / size);
			if (iAction == 10)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_nw.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_se.gif" border="0">');
			}
			else if (iAction == 11)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_n.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_s.gif" border="0">');
			}
			else if (iAction == 12)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_ne.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_sw.gif" border="0">');
			}
			else if (iAction == 13)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_e.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_w.gif" border="0">');
			}
			else if (iAction == 14)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_se.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_nw.gif" border="0">');
			}
			else if (iAction == 15)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_s.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_n.gif" border="0">');
			}
			else if (iAction == 16)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_sw.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_ne.gif" border="0">');
			}
			else if (iAction == 17)
			{
				wSetHTML(eSwitch, '<img onClick="moveSwitch(\'' + strId + '\')" onMouseOut="javascript:hideAction(\'' + strId + '\')" src="pictures/switch_w.gif" border="0">');
				wSetHTML(eSelf, '<img src="pictures/switch_e.gif" border="0">');
			}
		}		
		lastMarked = strId;
		
		
	}
	catch(exp)
	{
		//alert("Exception");	
	}
	
}

//-----------------------------------------

function createCombatTile(iPosX, iPosY)
{		
	counter = 0;		
	
	posX = iPosX * size + offset;
	posY = iPosY * size + offset;	
	
	sEntity = getApplet().getEntity(eval(iPosX), eval(iPosY));	
	
	try
	{
		aEntity = sEntity.split(" ");
		
		sName = aEntity[0];	
		sTeam = aEntity[1];
		sLifePercentage = (aEntity[2] * 100) / aEntity[3];
		
		sProtection = aEntity[4];
		if (eval(sProtection) <= 9)
			sProtection = "&nbsp;" + sProtection;
		
		sStatus = aEntity[5];
		sDescription = ""
		for (var i=6; i < aEntity.length; i = i + 1)
		{
			if (i >= aEntity.length - 1)
				sDescription += aEntity[i];	
			else
				sDescription += aEntity[i] + " ";	
		}
		
		if(wDynLayerTest(sName) == false)
		{	
			wCreateLayer(posX, posY, size, size, '', null, sName);
			
		
			if (sTeam == "null")
			{
				wCreateLayer(7, 7, 106, 93,'<img src="pictures/Monster.png" border="0">', sName, sName+'Picture');				
				lifeColor = "red";
			}
			else
			{
				wCreateLayer(7, 7, 106, 93,'<img src="pictures/' + sDescription + '.png" border="0">', sName, sName+'Picture');
				lifeColor = "green";					
			}
			wCreateLayer(0, 0, size, size,'<img src="pictures/frame.gif" border="0">', sName, sName+'Frame');
			wCreateLayer(8, 101, 92, 11,'<table width=92 height=11 border=0 cellspacing=0><tr><td width=' + sLifePercentage + '% BGCOLOR=' + lifeColor + '></td></tr></table>', sName, sName+'Life');	
			
			wCreateLayer(0, 98, 120, 14,'<table width=120 border=0 cellspacing=0><tr><td><font class="charName"><center>' + sDescription + '</center></font></td></tr></table>', sName, sName+'Desc');	
			wCreateLayer(98, 98, 17, 18,'<img src="pictures/shield.gif" border="0">', sName, sName+'Shield');	
			
			wCreateLayer(100, 98, 13, 18,'<table width=120 border=0 cellspacing=0><tr><td><font class="pointCounter">' + sProtection + '</font></td></tr></table>',sName, sName+'AC');				
			wCreateLayer(0, 0, size, size,'<img src="pictures/empty.gif" border="0">', sName, sName+'Status');
			wCreateLayer(0, 0, size, size,'<img src="pictures/empty.gif" border="0" onMouseOut="cancelTimeout()" onMouseOver="showAction(\'' + sName + '\')">', sName, sName+'Overlay');
			if (sTeam != "null")
			{
				wCreateLayer(98, 98, 17, 18,'<img onMouseDown="getEquipment(\'' + sName + '\')" src="pictures/empty.gif" border="0">', sName, sName+'Shield');	
				//wCreateLayer(38, 92, 44, 10,'<img onMouseDown="getEquipment(\'' + sName + '\')" src="pictures/menu_toggle.gif" border="0">', sName, sName+'Shield');	
			}
		}
		else
		{
			dLayer = wMakeDynLayer(sName);			
			wSetLocation(dLayer, posX, posY);

			dLife = wMakeDynLayer(sName + 'Life', sName);
			if (sTeam == "null")
			{
				//lifeColor = "#666666";
				lifeColor = "#3333ff";
				//lifeColor = "blue";
			}
			else
			{
				lifeColor = "red";
			}

			if (sLifePercentage == 0)
			{
				wSetHTML(dLife, '');				
			}
			else
			{
				wSetHTML(dLife, '<table width=95 height=11 border=0 cellspacing=0><tr><td width=' + sLifePercentage + '% BGCOLOR=' + lifeColor + '></td><td></td></tr></table>');
			}

			dStatus = wMakeDynLayer(sName + 'Status', sName);
			if (sStatus == 1)
			{
				wSetHTML(dStatus, '<img src="pictures/status_defend.gif" border="0">');
			}
			else
			{
				wSetHTML(dStatus, '<img src="pictures/empty.gif" border="0">');
			}
		}
	}
	catch(exp)
	{
		//alert("Exception");
	}
}

function removeCombatTile(attacker, defender)
{
	wDestroyLayer(defender);				
}



function initializeCombat()
{
		
	//str = parent.chatframe.TestApplet.actionGetCombatants();
	str = getApplet().actionGetCombatants();

	//str = "2 2 3 2 4 2";
	//alert(str);
	if (str != "")
	{
		strArray = str.split(" ");	
	
		for (var i=0; i < strArray.length; i = i + 2)
		{			
			createCombatTile(strArray[i], strArray[i+1]);		
		}
		//parent.chatframe.TestApplet.actionSendCombatReady();	
	}
	else
	{
		//initializeCombat();
		
	}
	//wSetVisible(dInterface, true);
	
}

//----------------------------------------------------

function normalizeLastFocus(id)
{
	
	//alert(id);
	if ((lastFocus != null) && (lastFocus != id))
	{
	try
	{
		//dLast = new DynLayer(lastFocus + 'Frame');
		dLast = wMakeDynLayer(lastFocus + 'Frame', lastFocus);
		//dLast.write('<img src="pictures/frame.gif" border="0">');
		wSetHTML(dLast, '<img src="pictures/frame.gif" border="0">');
		
		normalizeOverlays(id);
		
		//dLastOverlay = wMakeDynLayer(lastFocus + 'Overlay', lastFocus);		
		//wSetHTML(dLastOverlay, '<img onMouseOut="cancelTimeout()" onMouseOver="javascript:showAction(\'' + lastFocus + '\')" src="pictures/empty.gif" border="0">');
	}
	catch (exp)
	{
		alert("exp");
	}
	}
	
}


function requestAction(id, iPosX, iPosY)
{
	turnEntity = id;
	
	normalizeLastFocus(id);	

	posX = iPosX * size;
	posY = iPosY * size;	
	
	//dInterface.moveTo(eval(posX) - size + offset, eval(posY) - size + offset,2,1);
	wSetLocation(dInterface, eval(posX) - size + offset, eval(posY) - size + offset);

	//dInterface.show();
	wSetVisible(dInterface, true);
	
	//dTemp = new DynLayer(id + 'Frame');
	//dTemp.write('<img src="pictures/outline.gif" border="0">');
	
	//lastFocus = id;

	dTemp = wMakeDynLayer(id + 'Frame', id)
	wSetHTML(dTemp, '<img src="pictures/outline.gif" border="0">');

	//dTempOverlay = wMakeDynLayer(id + 'Overlay', id)
	//wSetHTML(dTempOverlay, '<img onMouseOut="cancelTimeout()" onMouseOver="javascript:showAction(\'' + id + '\')" src="pictures/empty.gif" border="0">');
	//normalizeOverlays(id);
	
	lastFocus = id;
	
}

//----------------------------------------------

function hideBlock()
{	
	cancelTimeout();
	hideLastAction();
	var posString;	
	
	wSetHTML(nwDiv, '<img onMouseOver="javascript:showBlock(0)" src="pictures/empty.gif" border="0">');		
	wSetHTML(nDiv, '<img onMouseOver="javascript:showBlock(1)" src="pictures/empty.gif" border="0">');
	wSetHTML(neDiv, '<img onMouseOver="javascript:showBlock(2)" src="pictures/empty.gif" border="0">');		
	wSetHTML(eDiv, '<img onMouseOver="javascript:showBlock(3)" src="pictures/empty.gif" border="0">');		
	wSetHTML(seDiv, '<img onMouseOver="javascript:showBlock(4)" src="pictures/empty.gif" border="0">');		
	wSetHTML(sDiv, '<img onMouseOver="javascript:showBlock(5)" src="pictures/empty.gif" border="0">');		
	wSetHTML(swDiv, '<img onMouseOver="javascript:showBlock(6)" src="pictures/empty.gif" border="0">');		
	wSetHTML(wDiv, '<img onMouseOver="javascript:showBlock(7)" src="pictures/empty.gif" border="0">');			
	
}

/*function showAction(strId)
{
	cancelTimeout();
	sAction = 'showAction2("' + strId + '");';
	vTimeout = setTimeout(sAction, iDelay);	
	//alert(bTimeout);
}*/

function showBlock(blockPos)
{
	cancelTimeout();
	sAction = 'showBlock2("' + blockPos + '");';
	vTimeout = setTimeout(sAction, iDelay);		
	
}

function showBlock2(strBlockPos)
{
	cancelTimeout();
	hideBlock();
	
	var blockPos = eval(strBlockPos);
	var posString;
	
	switch(blockPos)
	{
		case 0:				
			posString = "NW";
			//nwDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(nwDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');
			break;
		case 1:
			posString = "N";
			//nDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(nDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');
			break;
		case 2:
			posString = "NE";
			//neDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(neDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			break;
		case 3:
			posString = "E";
			//eDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(eDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			break;
		case 4:
			posString = "SE";
			//seDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(seDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			break;
	  case 5:
			posString = "S";
			//sDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(sDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			break;
		case 6:
			posString = "SW";
			//swDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(swDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			break;
		case 7:
			posString = "W";
			//wDiv.write('<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			wSetHTML(wDiv, '<img onMouseOut="javascript:hideBlock()" onClick="javascript:move(' + blockPos + ')" src="pictures/' + posString + '.GIF" border="0">');			
			break;
	}
	
	//dTemp = new DynLayer(posString);
	
	
		
}

function createInterface()
{	

	wCreateLayer(offset, offset, eval(size) * 3, eval(size) * 3,'', 'playfield', 'wInterface');
	
	//dInterface = new DynLayer('interface');
	
	dInterface = wMakeDynLayer('wInterface', 'playfield');
	//dInterface = wMakeDynLayer('wInterface', null);
	
	//dInterface.hide();
	//wSetVisible(dInterface, false);		

	wCreateLayer(0, 0, size, size,'<img onMouseOver="javascript:showBlock(eval(0))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'NW');	
	wCreateLayer(0 + size, 0, size, size,'<img onMouseOver="javascript:showBlock(eval(1))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'N');
	wCreateLayer(0 + eval(size) * 2, 0, size, size,'<img onMouseOver="javascript:showBlock(eval(2))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'NE');
	wCreateLayer(0, size, size, size,'<img onMouseOver="javascript:showBlock(eval(7))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'W');
	
	wCreateLayer(eval(size) * 2, size, size, size,'<img onMouseOver="javascript:showBlock(eval(3))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'E');
	wCreateLayer(0, eval(size) * 2, size, size,'<img onMouseOver="javascript:showBlock(eval(6))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'SW');
	wCreateLayer(size, eval(size) * 2, size, size,'<img onMouseOver="javascript:showBlock(eval(5))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'S');
	wCreateLayer(eval(size) * 2, eval(size) * 2, size, size,'<img onMouseOver="javascript:showBlock(eval(4))" src="pictures/empty.gif" border="0">', 'playfield.wInterface', 'SE');	
	

	nwDiv = wMakeDynLayer("NW", 'playfield.wInterface');
	nDiv = wMakeDynLayer("N", 'playfield.wInterface');
	neDiv = wMakeDynLayer("NE", 'playfield.wInterface');
	wDiv = wMakeDynLayer("W", 'playfield.wInterface');
	eDiv = wMakeDynLayer("E", 'playfield.wInterface');
	swDiv = wMakeDynLayer("SW", 'playfield.wInterface');
	sDiv = wMakeDynLayer("S", 'playfield.wInterface');
	seDiv = wMakeDynLayer("SE", 'playfield.wInterface');
	
	
	
}

function clearInterface()
{
	//alert("heisann");
	//hideBlock();
	wSetVisible(dInterface, false);
	dTemp = wMakeDynLayer(turnEntity + 'Frame', turnEntity);
	wSetHTML(dTemp, '<img src="pictures/frame.gif" border="0">');
	try
	{
	//if (lastMarked != null)
	//{
		normalizeOverlays(lastMarked);
	//}
	}
	catch(exp)
	{}
	normalizeOverlays(turnEntity);
}

//------------------------------------------------

function showStatPanel(entityId, parameters)
{
	pArray = parameters.split(";");
	extra = pArray[1];
	head = pArray[3];
	hand = pArray[5];
	armor = pArray[7];
	offHand = pArray[9];
	arms = pArray[11];
	feet = pArray[13];
	entityLayer = wMakeDynLayer(entityId);
	//alert(getX(eLayer));
	x = getX(entityLayer);
	y = getY(entityLayer);
		
	if(wDynLayerTest('UI_equipment') == false)
	{
		//alert("kljhlkj");	
	
		wCreateLayer(x - size - (size / 2), y - (size / 2), size * 2, size * 2,'<img src="pictures/UI_stats.png" border="0">', null, 'UI_stats');		
		wCreateLayer(x + (size / 2), y - (size / 2), size * 3, size * 4,'<img onMouseDown="hideStatPanel()" src="pictures/UI_equipment.png" border="0">', null, 'UI_equipment');		
		
		statLayer = wMakeDynLayer('UI_stats');
		equipmentLayer = wMakeDynLayer('UI_equipment');
		
		wSetVisible(statLayer, false);
		
		/*wCreateLayer(15, 33, size * 2, size * 2,'<font class="stats">123 / 456 - 789 / 000</font>', 'UI_stats', 'UI_attack');		
		wCreateLayer(130, 30, size * 2, size * 2,'<font class="stats2">123 / 456 - 789 / 000</font>', 'UI_stats', 'UI_attack');		
		wCreateLayer(130, 39, size * 2, size * 2,'<font class="stats2">123 / 456 - 789 / 000</font>', 'UI_stats', 'UI_attack2');		
		wCreateLayer(16, 89, size * 2, size * 2,'<font class="stats">00 - 00</font>', 'UI_stats', 'UI_damage');		
		wCreateLayer(178, 89, size * 2, size * 2,'<font class="stats">723 - 456</font>', 'UI_stats', 'UI_ac');		
		*/
		//updateEquipment(entityId);
		
		wCreateLayer(38, 33, 187, 14,'<font class="stats">' + hand + '</font>', 'UI_equipment', 'UI_hand');
		wCreateLayer(35, 28, 194, 24,'<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[4] + ');">', 'UI_equipment', 'UI_hand_click');
		handLayer = wMakeDynLayer('UI_hand');
		handClick = wMakeDynLayer('UI_hand_click');
		
		wCreateLayer(38, 61, 187, 14,'<font class="stats">' + offHand + '</font>', 'UI_equipment', 'UI_offHand');
		wCreateLayer(35, 56, 194, 24,'<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[8] + ');">', 'UI_equipment', 'UI_offHand_click');
		offHandLayer = wMakeDynLayer('UI_offHand');
		offHandClick = wMakeDynLayer('UI_offHand_click');
		
		wCreateLayer(38, 89, 187, 14,'<font class="stats">' + armor + '</font>', 'UI_equipment', 'UI_armor');
		wCreateLayer(35, 84, 194, 24,'<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[6] + ');">', 'UI_equipment', 'UI_armor_click');
		armorLayer = wMakeDynLayer('UI_armor');
		armorClick = wMakeDynLayer('UI_armor_click');
		
		wCreateLayer(38, 117, 187, 14,'<font class="stats">' + head + '</font>', 'UI_equipment', 'UI_head');
		wCreateLayer(35, 112, 194, 24,'<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[2] + ');">', 'UI_equipment', 'UI_head_click');
		headLayer = wMakeDynLayer('UI_head');
		headClick = wMakeDynLayer('UI_head_click');
		
		wCreateLayer(38, 145, 187, 14,'<font class="stats">' + arms + '</font>', 'UI_equipment', 'UI_arms');
		wCreateLayer(35, 140, 194, 24,'<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[10] + ');">', 'UI_equipment', 'UI_arms_click');
		armsLayer = wMakeDynLayer('UI_arms');
		armsClick = wMakeDynLayer('UI_arms_click');
		
		wCreateLayer(38, 173, 187, 14,'<font class="stats">' + feet + '</font>', 'UI_equipment', 'UI_feet');
		wCreateLayer(35, 168, 194, 24,'<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[12] + ');">', 'UI_equipment', 'UI_feet_click');
		feetLayer = wMakeDynLayer('UI_feet');
		feetClick = wMakeDynLayer('UI_feet_click');
			
		wCreateLayer(38, 201, 187, 14,'<font class="stats">' + extra + '</font>', 'UI_equipment', 'UI_extra');
		wCreateLayer(35, 196, 194, 24,'<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[0] + ');">', 'UI_equipment', 'UI_extra_click');
		extraLayer = wMakeDynLayer('UI_extra');
		extraClick = wMakeDynLayer('UI_extra_click');
	}
	else
	{
		wSetLocation(statLayer, x - size - (size / 2), y - (size / 2));
		wSetLocation(equipmentLayer, x + (size / 2), y - (size / 2));
		wSetVisible(equipmentLayer, true);
		//wSetVisible(statLayer, true)
		wSetHTML(handLayer, '<font class="stats">' + hand + '</font>');
		wSetHTML(handClick, '<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[4] + ');">');
		
		wSetHTML(offHandLayer, '<font class="stats">' + offHand + '</font>');
		wSetHTML(offHandClick, '<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[8] + ');">');
		
		wSetHTML(armorLayer, '<font class="stats">' + armor + '</font>');
		wSetHTML(armorClick, '<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[6] + ');">');
		
		wSetHTML(headLayer, '<font class="stats">' + head + '</font>');		
		wSetHTML(headClick, '<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[2] + ');">');
		
		wSetHTML(armsLayer, '<font class="stats">' + arms + '</font>');
		wSetHTML(armsClick, '<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[10] + ');">');
		
		wSetHTML(feetLayer, '<font class="stats">' + feet + '</font>');
		wSetHTML(feetClick, '<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[12] + ');">');
		
		wSetHTML(extraLayer, '<font class="stats">' + extra + '</font>');
		wSetHTML(extraClick, '<img src="pictures/UI_textline.gif" onMouseDown="showInfoPanel(' + pArray[0] + ');">');
		
		
	}
}

function showInfoPanel(invId)
{
	//invId = 5;
	//alert(invId);
	//entityLayer = wMakeDynLayer(entityId);
	//alert(getX(eLayer));
	if(invId != null)
	{
		strItem = getApplet().getInventoryItem(invId);
		iArray = strItem.split(";");
		
		x = getX(entityLayer);
		y = getY(entityLayer);
	
		if(wDynLayerTest('UI_info') == false)
		{
			//wCreateLayer(x + (size / 2), y - (size / 2), size * 3, size * 4,'<img onMouseDown="hideInfoPanel()" src="pictures/UI_stats.png" border="0">', null, 'UI_info');
			wCreateLayer(0, 0, size * 3, size * 4,'<img onMouseDown="hideInfoPanel()" src="pictures/UI_info.png" border="0">', 'UI_equipment', 'UI_info');		
			infoLayer = wMakeDynLayer('UI_info');
			//wCreateLayer(67, 109, 106, 14,'<table width=106 border=0 cellspacing=0><tr><td><font class="charName"><center>' + iArray[1] + '</center></font></td></tr></table>', 'UI_info', 'UI_info_type');				
			//infoTypeLayer = wMakeDynLayer('UI_info_type');
			wCreateLayer(67, 109, 106, 28,'<table width=106 border=0 cellspacing=0><tr><td><font class="itemName"><center>' + iArray[0] + '</center></font></td></tr></table>', 'UI_info', 'UI_info_name');
			infoNameLayer = wMakeDynLayer('UI_info_name');
			wCreateLayer(67, 18, 106, 93,'<img src="pictures/Items/' + iArray[0] + '.png">', 'UI_info', 'UI_info_picture');
			infoPictureLayer = wMakeDynLayer('UI_info_picture');
		}
		else
		{
			wSetVisible(infoLayer, true);
			wSetHTML(infoNameLayer, '<table width=106 border=0 cellspacing=0><tr><td><font class="itemName"><center>' + iArray[0] + '</center></font></td></tr></table>');
			wSetHTML(infoPictureLayer, '<img src="pictures/Items/' + iArray[0] + '.png">');
		}
		//alert(getInventoryItem(invId));
	}
}

function getInventoryItem(itemId)
{
	strItem = getApplet().getInventoryItem(itemId);
	/*rValue += i.name + cVar.separatorParagraph;
    rValue += i.type + cVar.separatorParagraph;
    rValue += i.priLocation + cVar.separatorParagraph;
    rValue += i.secLocation + cVar.separatorParagraph;
    rValue += i.initiative + cVar.separatorParagraph;
    rValue += i.attack + cVar.separatorParagraph;
    rValue += i.damage + cVar.separatorParagraph;
    rValue += i.defence + cVar.separatorParagraph;
    rValue += i.protection;	*/
  return(strItem);
}

function getEquipment(entityId)
{
	strHead = getApplet().actionGetInventory(entityId);
	//wCreateLayer(38, 33, size * 2, size * 2,'<font class="stats">' + 'strHead' + '</font>', 'UI_equipment', 'UI_head');
}

function hideInfoPanel()
{	
	wSetVisible(infoLayer, false)
}

function hideStatPanel()
{	
	wSetVisible(equipmentLayer, false)
}

//----------------------------------------------------

function nocontextmenu() {
  event.cancelBubble = true, event.returnValue = false;

  return false;
} 

document.oncontextmenu = nocontextmenu;
