package handler;

import java.net.InetAddress;
import java.util.*;
import tools.Item;

public class Player
{

  public boolean isReady = false;
  //public boolean isPresent = false;

  public String playerId;
  public InetAddress host;
  public int port;
  public String playerName;

  public Hashtable tInventory = new Hashtable();
  public Vector vInventoryOrder = new Vector();

  public Player(String strPlayerId)
  {
    playerId = strPlayerId;
    playerName = strPlayerId;
  }

  public void addInventoryItem(String itemId, Item item)
  {
    //System.out.println("adding item: " + item.name);
    tInventory.put(itemId, item);
    vInventoryOrder.add(itemId);
  }

  public Item getInventoryItem(String itemId)
  {
    Item rValue = (Item)tInventory.get(itemId);
    return(rValue);
  }
} 