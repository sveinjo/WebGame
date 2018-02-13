package handler;

import java.util.*;
import java.net.*;
import java.awt.Point;

public class ModelCache implements AreaListener
{
  Hashtable tModel = new Hashtable();
  //Hashtable tThreads = new Hashtable();

  Thread thServx;
  EncounterInterface servx;


  public ModelCache()
  {


  }

  public EncounterModel getModel(String playerId, String locationId)
  {
    EncounterModel em;
    /*if (tModel.containsKey(locationId))
    {
      em = (EncounterModel)tModel.get(locationId);
      em.addPlayer(playerId);
    }
    else
    {
      em = new EncounterModel(locationId);
      em.addPlayer(playerId);
      tModel.put(locationId, em);
    }*/
    try
    {
      em = (EncounterModel)tModel.get(locationId);
      //em.addPlayer(playerId);
      em.modifyPlayerList(true, playerId);
    }
    catch (Exception exp)
    {

      em = new EncounterModel(locationId);
      em.addAreaListener(this);

      em.modifyPlayerList(true, playerId);
      tModel.put(locationId, em);
    }
    return(em);
  }



  public void createNewInstance(Socket socket)
  {
    int iPort = socket.getPort();
    
    servx = new EncounterInterface (this, socket);

        thServx = new Thread (servx);
        thServx.start ();
    //tThreads.put(new Integer(iPort), thServx);
    System.out.println("Adding thread: " + iPort);
  }

  public void removeModel(String modelId)
  {
    tModel.remove(modelId);
    System.out.println("Removing model: " + modelId + " from cache!");
  }

  public void locationClear(String locationId)
  {
    removeModel(locationId);
    System.out.println("Location: " + locationId + " is clear!");

  }

  public void significantAction(AreaEvent e)
  {
  }

  /*public void removeThread(String threadId)
  {
    tThreads.remove(threadId);
    System.out.println("Removing thread: " + threadId + " from cache!");
  }*/
} 