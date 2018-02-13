package handler;


import tools.*;
import java.awt.Point;
import java.util.*;

public class DataConnectorProxy extends LocationStructAdvanced
{
  private int eCounter = 0;


  public DataConnectorProxy()
  { 
    System.out.println("WARNING: This version of DataConnector is only for testing!");
  }

  protected String getLocationData(String locationId)
  {
    String locationData = "7 5 99999999000009900000990000099999999";
    //String locationData = "19 19 9999999999999999999900099990009999999990002002000999999999000999900099999999992999990002002999999200099000999099999909999900099909999992020020009990999999990999000999099999999099992999909999999909999099000009999990900029999099999909999990999909999990999900000990999999099990000099099999020002000002029999999999900000999999999999990000099999999999999999999999999";

    return(locationData);
  }

  protected void initPlayerEntities(String playerId)
  {
    if (this.getEntity(4, 2) == null)
    {
      createEntity(4, 2, playerId, playerId, 10, 10);
    }
    else
    {
      createEntity(4, 1, playerId, playerId, 10, 10);
    }
  }

  

  protected void initComputerEntities(String locationId)
  {
    createEntity(2, 2, "Monster ting", null, 10, 10);
  }

  private void createEntity(int x, int y, String description, String owner, int currHP, int maxHP)
  {
    String sId = "ent" + eCounter;
    this.cellTable[x][y].entity = new Entity(sId, description, owner, currHP, maxHP);
    this.tEntities.put(sId, new Point(x,y));
    eCounter++;

    //return(returnValue);
  }
}