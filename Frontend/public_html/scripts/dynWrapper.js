dynapi.library.setPath('dynApi30/');
dynapi.library.include('dynapi.api');
//DynAPI.setLibraryPath('dynApi25/lib/');
//DynAPI.include('dynapi.api.*');


var base;

function wInitialize(width, height)
{
	base = new DynLayer(null, 0, 0, width, height);
	dynapi.document.addChild(base);	
}

function wCreateLayer(posX, posY, sizeX, sizeY, html, parentId, id)
{			
	if (parentId == null)
	{
		parentId = base;
	}
	else
	{
		parentId = eval('base.' + parentId);		
	}
	wCreateChild(posX, posY, sizeX, sizeY, html, parentId, id);
}

function wCreateChild(posX, posY, sizeX, sizeY, html, parentId, id)
{
	c = new DynLayer(html, posX, posY, sizeX, sizeY);
	c.setID(id);
	parentId.addChild(c, id);	
}

function test(layerId)
{
	var tempLayer = eval('base.' + layerId);	
	alert(tempLayer.id);
	alert(tempLayer);
	//base.deleteChild(tempLayer);
	//alert(document.getAllChildren());
}

function wDestroyLayer(id, parentId)
{
	if (parentId == null)
	{
		id = eval('base.' + id);
	}
	else
	{
		id = eval('base.' + parentId + '.' + id);
	}
	//parentId = eval(parentId);

	base.deleteChild(id);
}

function wMakeDynLayer(id, parentId)
{
	if (parentId == null)
		id = eval('base.' + id);
	else
		id = eval('base.' + parentId + '.' + id);
	return(id);
}

function wSetVisible(layerId, bValue)
{
	layerId.setVisible(bValue);
}

function wSetLocation(layerId, posX, posY)
{
	layerId.setLocation(posX, posY);
}

function wSetHTML(layerId, html)
{
	layerId.setHTML(html);
}

function wDynLayerTest(layerId)
{
	rValue = true;
	if(eval('base.' + layerId) == null)
	{	
		rValue = false;
		//alert("Don't exist");
	}
	else
	{
		//alert("Exists");
	}
	return(rValue);
}

function getX(layerId)
{
	return(layerId.getX());
}

function getY(layerId)
{	
	return(layerId.getY());
}
