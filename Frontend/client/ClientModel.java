package client;


import tools.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;

public class ClientModel extends LocationStruct
{
  private Hashtable tInventory = new Hashtable();
  private Vector vInventoryOrder = new Vector();

  private SocketVariables cVar = new SocketVariables();

  /*public void addEntity(String entityId, int x, int y, String description, String gender, String owner, int cHP, int mHP, int protection)
  {
    super.addEntity(entityId, x, y, description, gender, owner, cHP, mHP, protection);
  }*/

  public void addEntity(String entityId, int x, int y, String description, String gender, String owner, int cHP, int mHP, int protection)
  {
    System.out.println("Adding entity: " + x + " " + y);
    Entity eTemp = new Entity(entityId, description, gender, owner, cHP, mHP, protection);
    cellTable[x][y].entity = eTemp;
    tEntities.put(entityId, new Point(x, y));
  }

  public void addInventoryItem(String itemId, Item item)
  {
    tInventory.put(itemId, item);
    vInventoryOrder.add(itemId);
  }

  public void initializeEntityInventory(String entityId, String parameters)
  {
    Entity e = getEntity(entityId);
    e.equipment = parameters;
    System.out.println(e.equipment);
  }

  public String getInventory(String entityId)
  {
    String rValue = "";
    //Vector rValue = new Vector();
    Entity e = getEntity(entityId);
    System.out.println(rValue);
    StringTokenizer st = new StringTokenizer(e.equipment, "|");
    Item i = null;
    String s = null;
    while(st.hasMoreTokens())
    {
      s = st.nextToken();

      if (!s.equals("0"))
      {
      i = getInventoryItem(s);
      System.out.println("inv: " + i.invId);

      rValue += i.invId + cVar.separatorParagraph + i.name + cVar.separatorParagraph;
      //rValue.add(i);
      }
      else
      {
        rValue += cVar.separatorParagraph + cVar.separatorParagraph;
        //rValue.add(null);
      }
    }

    return(rValue);
  }

  public Item getInventoryItem(String itemId)
  {
    return((Item)tInventory.get(itemId));
  }

  public void initializeInventory(String parameters)
  {
    String strTemp;
    StringTokenizer st = new StringTokenizer(parameters, ";");
    StringTokenizer stItem;
    String itemId;
    while(st.hasMoreTokens())
    {
      strTemp = st.nextToken();
      stItem = new StringTokenizer(strTemp, ",");
      itemId = stItem.nextToken();
      addInventoryItem(itemId, new Item(itemId, stItem.nextToken(), stItem.nextToken(), Integer.parseInt(stItem.nextToken()), Integer.parseInt(stItem.nextToken()), Integer.parseInt(stItem.nextToken()), Integer.parseInt(stItem.nextToken()), Integer.parseInt(stItem.nextToken()), 1, Integer.parseInt(stItem.nextToken()), Integer.parseInt(stItem.nextToken())));
    }

    //Item item = (Item)tInventory.get("1");
    //System.out.println("Item: " + item.invId + " " + item.name + " " + item.type + " " + item.priLocation + " " + item.secLocation + " " + item.protection + " " + item.initiative + " " + item.attack + " " + item.damage + " " + item.defence);
  }

  public ClientModel(String strPlayerId, String strLocationId)
  {
    /*sizeX = 7;
    sizeY = 5;
    cellTable = new LocationCell[sizeX][sizeY];
    this.initializeLocation();
    */
  }

  public void purge()
  {
    System.out.println("purging");
    //this.initializeLocation();
  }



  public String damageEntity(int x, int y, int amount)
  {
    String rValue = null;
    Entity eTemp = this.getEntity(x, y);
    int cHP = eTemp.curHP - amount;
    eTemp.curHP = cHP;

    if (cHP <= 0)
    {
      //eTemp.curHP = 0;
      rValue = eTemp.id;
      this.removeEntity(x, y);
    }
    return(rValue);
  }

  public void updateStatus(String entityId, int statusBits)
  {
    Entity e = getEntity(entityId);
    e.statusBits = statusBits;
  }

  public void switchEntities(String sActor, String sTarget)
  {

    Point actor = getEntityPos(sActor);
    Point target = getEntityPos(sTarget);

    /*int tx = target.x;
    int ty = target.y;
    int ax = actor.x;
    int ay = actor.y;
    */
    System.out.println(sActor + "---" + sTarget);
    Entity tempId = getEntity(actor.x, actor.y);
    /*  this.cellTable[actor.x][actor.y].entity = getEntity(target.x, target.y);
      this.cellTable[target.x][target.y].entity = tempId;

    */
    setEntityPos(getEntity(target.x, target.y), actor.x, actor.y);
    //setEntityPos(tempId, tx, ty, false);
  }

  public void moveEntity(String entityId, int direction)
  {
    int x = 0;
    int y = 0;

    switch (direction)
    {
      case 0:
      {
        x = -1;
        y = -1;
        break;
      }
      case 1:
      {
        x = 0;
        y = -1;
        break;
      }
      case 2:
      {
        x = 1;
        y = -1;
        break;
      }
      case 3:
      {
        x = 1;
        y = 0;
        break;
      }
      case 4:
      {
        x = 1;
        y = 1;
        break;
      }
      case 5:
      {
        x = 0;
        y = 1;
        break;
      }
      case 6:
      {
        x = -1;
        y = 1;
        break;
      }
      case 7:
      {
        x = -1;
        y = 0;
        break;
      }
    }
    Point p = (Point)this.getEntityPos(entityId);
    int posX = p.x;
    int posY = p.y;
    Entity eTemp = cellTable[p.x][p.y].entity;

    //update entity Table
    //Point p = (Point)tEntities.get(eTemp.id);
    p.x = p.x + x;
    p.y = p.y + y;

    //update entity grid
    cellTable[posX][posY].entity = null;
    cellTable[posX + x][posY + y].entity = eTemp;



  }
}