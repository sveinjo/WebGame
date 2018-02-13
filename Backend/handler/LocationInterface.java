package handler;

import java.net.*;

public class LocationInterface extends SocketInterface
{

  public LocationInterface(ModelCache mCache, Socket s)
  {
    super(mCache, s);
    this.sendData(cVar.getLocationSize, model.sizeX + " " + model.sizeY);
    this.sendData(cVar.getLocationData, getLocationData());
  }

  protected String getLocationData()
  {
    String locationData = model.enterLocation(2,2);
    return(locationData);
  }

  public void performCleanup()
  {
    super.performCleanup();

    //controller.leaveZone(playerId);


    String modelIdToBeRemoved = leaveZone(playerId);
    /*if (modelIdToBeRemoved != null)
    {
      modelCache.removeModel(modelIdToBeRemoved);
    }*/
  }

  public String leaveZone(String playerId)
  {

    //return(model.removePlayer(playerId));
    return(model.modifyPlayerList(false, playerId));
  }
}