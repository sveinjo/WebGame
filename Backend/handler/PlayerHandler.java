package handler;

import tools.*;
import java.util.*;

//public class PlayerHandler extends DataConnector
public class PlayerHandler extends DataConnectorDD
{
  protected ChatCaster chat = null;
  protected Thread chatThread = null;
  protected String locationId = null;
  protected SocketVariables cVar = new SocketVariables();
  private transient Vector areaListeners;

  //protected DataConnector dataConn = new DataConnector();
  //protected DataConnectorProxy dataConn = new DataConnectorProxy();

  public PlayerHandler()
  { 
  }

  public Player getPlayer(String strPlayerId)
  {
    Player rValue = (Player)this.chat.tPlayers.get(strPlayerId);
    return(rValue);
  }

  private void addPlayer(String strPlayerId)
  {
    //boolean retFlag = true;

    //System.out.println(chat);
    if (chat.tPlayers.containsKey(strPlayerId))
    {
      //retFlag = false;
    }
    else
    {
      chat.tPlayers.put(strPlayerId, new Player(strPlayerId));
    }
    //System.out.println("players: " + tPlayers.size());
    //return(retFlag);

  }

  // removes a player from the area. If this is the last player the method
  // returns a boolean true
  private String removePlayer(String playerId)
  {
    String returnFlag = null;
    if (chat.tPlayers.containsKey(playerId))
    {
      Player player = (Player)chat.tPlayers.get(playerId);


      chat.tPlayers.remove(playerId);
      //System.out.println(cVar.areaTag + " " + player.playerName + " leaves.");
      chat.broadcastMessage(cVar.areaTag + " " + player.playerName + " leaves.");
      if (chat.tPlayers.size() == 0)
      {

        //chatThread.stop();
        //chatThread.destroy();
        //chat.destroy();

        returnFlag = locationId;
        this.fireLocationClear(locationId);

      }
    }
    return (returnFlag);
  }

  public synchronized String modifyPlayerList(boolean modifyFlag, String strPlayerId)
  {
    if (modifyFlag)
    {
      addPlayer(strPlayerId);
      return(null);
    }
    else
    {
      return(removePlayer(strPlayerId));
    }
  }

  public synchronized void removeAreaListener(AreaListener l)
  {
    if(areaListeners != null && areaListeners.contains(l))
    {
      Vector v = (Vector) areaListeners.clone();
      v.removeElement(l);
      areaListeners = v;
    }
  }

  public synchronized void addAreaListener(AreaListener l)
  {
    Vector v = areaListeners == null ? new Vector(2) : (Vector) areaListeners.clone();
    if(!v.contains(l))
    {
      v.addElement(l);
      areaListeners = v;
    }
  }

  protected void fireLocationClear(String locationId)
  {
    if(areaListeners != null)
    {
      Vector listeners = areaListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((AreaListener) listeners.elementAt(i)).locationClear(locationId);
      }
    }
  }

  protected void fireSignificantAction(AreaEvent e)
  {
    if(areaListeners != null)
    {
      Vector listeners = areaListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((AreaListener) listeners.elementAt(i)).significantAction(e);
      }
    }
  }

} 