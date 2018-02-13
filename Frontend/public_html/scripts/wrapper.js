
function wInitialize()
{}

function wCreateLayer(posX, posY, sizeX, sizeY, html, parentId, id)
{		
	if (parentId == null)
		parentId = '';
	createLayer(id, parentId, posX, posY, sizeX, sizeY, html);	
}

function wCreateChild(posX, posY, sizeX, sizeY, html, parentId, id)
{
	c = new DynLayer(html, posX, posY, sizeX, sizeY);
	c.setID(id);
	parentId.addChild(c, id);	
}



function wDestroyLayer(id, parentId)
{
	
	destroyLayer(id, parentId)
}

function wMakeDynLayer(id, parentId)
{
	rLayer = new DynLayer(id);
	return(rLayer);
}

function wSetVisible(layerId, bValue)
{
	if (bValue == true)
		layerId.show();
	else
		layerId.hide();
}

function wSetLocation(layerId, posX, posY)
{
	layerId.moveTo(posX, posY);
}

function wSetHTML(layerId, html)
{
	layerId.write(html);
}

function wDynLayerTest(layerId)
{
	bValue = true;

	//sName = eval(layerId);
	//if(DynLayerTest(sName) == false)
	if(DynLayerTest(layerId) == false)
		bValue = false;
	return(bValue);
}

function getX(layerId)
{
	return(layerId.x);
}

function getY(layerId)
{	
	return(layerId.y);
}