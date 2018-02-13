package tools;

import java.awt.Point;
import java.util.*;

public class LocationStruct
{
  public LocationCell[][] cellTable;
  //int cellSize;
  public int sizeX, sizeY;
  protected String seperator = " ";

  public final int sBorderValue = 2;

  public final int sSpace = 0;
  public final int sFloor = 1;
  public final int sBorder = 2;
  public final int sWall = 9;

  private String returnString = "";

  protected Hashtable tEntities = new Hashtable();
  //protected Hashtable tInitiative = new Hashtable();
  public LocationStruct()
  {
  }

  public Entity getEntity(int posX, int posY)
  {
    Entity rValue = (Entity)this.cellTable[posX][posY].entity;
    return(rValue);
  }

  public Entity getEntity(String entityId)
  {
    Point p = (Point)tEntities.get(entityId);
    Entity e = (Entity)this.cellTable[p.x][p.y].entity;
    return (e);
  }

  /*protected void setEntityPos2(Entity eEntity, int x, int y, boolean flag)
  {
    Point p = (Point)tEntities.get(eEntity.id);

    if (flag)
      cellTable[p.x][p.y].entity = null;
    p.x = x;
    p.y = y;
    tEntities.put(eEntity.id, p);
    this.cellTable[x][y].entity = eEntity;
    System.out.println("***" + this.cellTable[x][y].entity.description);
  }*/

  protected void setEntityPos(Entity eEntity, int x, int y)
  {
    Point p = (Point)tEntities.get(eEntity.id);

    if (this.getEntity(x, y) == null)
      cellTable[p.x][p.y].entity = null;
    else
    {
      Entity eReplacement = getEntity(x, y);
      Point p2 = (Point)tEntities.get(eReplacement.id);
      p2.x = p.x;
      p2.y = p.y;
      this.cellTable[p.x][p.y].entity = eReplacement;
    }
    p.x = x;
    p.y = y;
    //tEntities.put(eEntity.id, p);
    this.cellTable[x][y].entity = eEntity;
    System.out.println("***" + this.cellTable[x][y].entity.description);
  }

  public Point getEntityPos(String entityId)
  {
    Point p = (Point)tEntities.get(entityId);
    return(p);
  }

  /*private void addEntity(String entityId, int x, int y, String description, String gender, String owner, int cHP, int mHP, int protection)
  {
    System.out.println("Adding entity: " + x + " " + y);
    Entity eTemp = new Entity(entityId, description, gender, owner, cHP, mHP, protection);
    cellTable[x][y].entity = eTemp;
    tEntities.put(entityId, new Point(x, y));
  }*/

  /*public void addEntity(String entityId, int x, int y, String description, String owner, int cHP, int mHP, String sWeapon, int wInitiative, int wRange, int wDamageMin, int wDamageMax)
  {
    Entity eTemp = new PCharacter(entityId, description, owner, cHP, mHP, sWeapon, wInitiative, wRange, wDamageMin, wDamageMax);
    cellTable[x][y].entity = eTemp;
    tEntities.put(entityId, new Point(x, y));
  }*/

  protected void removeEntity(int x, int y)
  {
    Entity eTemp = getEntity(x, y);
    String eId = eTemp.id;
    tEntities.remove(eId);
    cellTable[x][y].entity = null;
  }

  /*public LocationStruct(int iSizeX, int iSizeY)
  {
    sizeX = iSizeX;
    sizeY = iSizeY;
    cellTable = new LocationCell[sizeX][sizeY];
    this.initializeLocation();

  }*/

  /*public void setMap(String mapString)
  {
    System.out.println("Attempting to initialize map");
    StringTokenizer st = new StringTokenizer(mapString);

    // Get dimensions
    sizeX = Integer.parseInt(st.nextToken());
    sizeY = Integer.parseInt(st.nextToken());
    cellTable = new LocationCell[sizeX][sizeY];
    initializeLocation();

    String token = st.nextToken();
    //System.out.println(token.substring(0,1));
    int n, m;
    int counter = 0;
    //char temp;
    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {
        cellTable[n][m].structure = Integer.parseInt(token.substring(counter, counter+1));
        if (cellPoll(n, m) == this.sWall)
          cellMark(n, m);
        //System.out.println(token.substring(counter, counter+1));
        counter++;
      }
    }
  } */




  public Point calculateZone(int x, int y, int cellSize)
  {
    int tempX, tempY;
    tempX = x/cellSize;
    tempY = y/cellSize;
    return (new Point(tempX, tempY));
  }

  public void initializeLocation(int iSizeX, int iSizeY)
  {
    sizeX = iSizeX;
    sizeY = iSizeY;
    cellTable = new LocationCell[sizeX][sizeY];
    int n, m;
    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {
        //g.drawRect(n*cellSize, m*cellSize, cellSize, cellSize);
        cellTable[n][m] = new LocationCell();
      }
    }
  }

  /*protected void initializeLocation()
  {
    int n, m;
    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {
        //g.drawRect(n*cellSize, m*cellSize, cellSize, cellSize);
        cellTable[n][m] = new LocationCell();
      }
    }
  }*/

  protected void fillNearPoint(int x, int y)
  {
    int fillCol = this.sFloor;
    int boundCol = this.sWall;
    int curCol;

    if (cellIsChecked(x, y) == false)
    {
      //this.cellMark(x, y, fillCol);
      this.cellMark(x, y);

      returnString += x + " " + y + " ";
      //System.out.println("+");
      fillNearPoint(x+1, y);
      fillNearPoint(x-1, y);
      fillNearPoint(x, y+1);
      fillNearPoint(x, y-1);
    }
    //else
      //System.out.println("-");

    //return(rString);
  }

  // Highly temporary variable
  // 2 2 3 2 4 2 5 2 5 3 4 3 3 3 2 3 1 3 1 2 1 1 2 1 3 1 4 1 5 1
  String tempRoom = null;

  synchronized public String defineRoom(int posX, int posY)
  {
    /*if (returnString != null)
    {
    returnString = "";

    //String tempString = "";
    int iLeft, iRight, iTop, iBottom, tempX, tempY;
    System.out.println("Defining Room: " + posX + ", " + posY);
    //System.out.println(dissectPoint(evaluatePoint(posX, posY), dir_SE));

    fillNearPoint(posX, posY);

    //tempString = returnString;
    //System.out.println("tempString: " + tempString);

    returnString = returnString.trim();
    //returnString.substring(0, returnString.length()-1);

    tempRoom = new String(returnString);
    }
    else
      returnString = tempRoom;
    return(returnString);     */

    // Foreløbig er romtegnerutingen en hack. Romstørrelsen settes i det formatet som ligger i databasen, men fylles med svarte tiles. Disee må fjernes individuelt, og dette gjøres her.
    // Hvert tall-par er en kordinat for en svart tile som skal fjærnes. Rekkefølgen er litt hipp som happ.
    return("2 2 3 2 4 2 5 2 5 3 4 3 3 3 2 3 1 3 1 2 1 1 2 1 3 1 4 1 5 1 5 4 4 4 3 4 2 4 1 4 1 5 2 5 3 5 4 5 5 5");
  }


  protected boolean cellIsChecked(int posX, int posY)
  {
    boolean returnValue = true;
    if (posX >= 0 && posX < sizeX && posY >= 0 && posY < sizeY)
    {
      returnValue = this.cellTable[posX][posY].checked;
    }
    return(returnValue);

  }

  protected int cellPoll(int posX, int posY)
  {
    int returnValue = this.sWall;
    if (posX >= 0 && posX < sizeX && posY >= 0 && posY < sizeY)
    {
      returnValue = this.cellTable[posX][posY].structure;
    }
    return(returnValue);

  }

  protected void cellMark(int posX, int posY, int structureId)
  {
    cellTable[posX][posY].structure = structureId;
    if (structureId == this.sSpace)
      cellTable[posX][posY].checked = false;
    else
      cellTable[posX][posY].checked = true;
  }

  protected void cellMark(int posX, int posY)
  { 
    cellTable[posX][posY].checked = true;
  }

  public boolean isInRange(int aX, int aY, int dX, int dY)
  {
    int range = 1;

    boolean rValue = false;
    if ((Math.abs(dX - aX) <= range) && (Math.abs(dY - aY) <= range))
    {
      rValue = true;
    }
    return(rValue);
  }

  /*public boolean isInRangeOld(int aX, int aY, int dX, int dY)
  {
    Vector p = new Vector();
    Point defender =  new Point(dX, dY);
    Point temp;
    boolean rValue = false;

    //check facing of attacker
    int facing = 7;

    //check legal zone of attack
    int zoneLevel = 1;

    //check range
    int range = 1;

    int a, b, c, d, n, m;
    a = 0; b = 0; c = 0; d = 0;

    switch (facing)
    {
      case 1:
      {
        a = -1; b = 1; c = -1; d = 0;
        break;
      }
      case 3:
      {
        a = 1;  b = 0; c = -1; d = 1;
        break;
      }
      case 5:
      {
        a = -1; b = 1; c = 1 ; d = 0;
        break;
      }
      case 7:
      {
        a = -1; b = 0; c = -1; d = 1;
        break;
      }
    }

    for (n = 1; n <= range; n++)
    {
      for (m = 0; m < (n*2) + 1; m++)
      {
        //System.out.println((aX+(n*a)+(m*b)) + ", " + (aY+(n*c)+(m*d)));
        //p.add(new Point(aX+(n*a)+(m*b), aY+(n*c)+(m*d)));

        temp = new Point(aX+(n*a)+(m*b), aY+(n*c)+(m*d));
        if (temp.equals(defender))
        {
          rValue = true;
        }
      }
    }
    return(rValue);
  } */

  /*protected String getLocationData(String locationId)
  {
    return("19 19 9999999999999999999900099990009999999990002002000999999999000999900099999999992999990002002999999200099000999099999909999900099909999992020020009990999999990999000999099999999099992999909999999909999099000009999990900029999099999909999990999909999990999900000990999999099990000099099999020002000002029999999999900000999999999999990000099999999999999999999999999");
  }*/
} 