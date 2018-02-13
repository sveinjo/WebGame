package tools;

import java.awt.Point;
import java.util.*;



public class LocationStructAdvanced extends LocationStruct
{
  //private Random random;

  //protected Hashtable tInventory = new Hashtable();


  public LocationStructAdvanced()
  {
    //random = new Random();
  }

  /*protected int rollDice(int low, int high)
  {
    int root = high - low + 1;
    int rValue = random.nextInt(root) + low;
    //System.out.println("***Random***: " + rValue);
    return(rValue);
  }*/

  

  /*public Entity addEntity(String entityId, int x, int y, String description, String gender, String owner, int cHP, int mHP,
                        int iNaturalArmor, int iBaseArmor,
                        String sWeapon, int wInitiative, int wRange, int wDamageMin, int wDamageMax)*/
  public Entity addEntity(String entityId, int x, int y, String description, String race, String gender, String owner,
                          //int iStrength, int iMagic, int iConstitution, int iWillpower, int iAgility, int iDexterity,
                          int cHP, int mHP,
                          int iNaturalArmor)
  {
    //super.addEntity(entityId, x, y, description, owner, cHP, mHP, sWeapon, wInitiative, wRange, wDamageMin, wDamageMax);
    Entity eTemp = new PCharacter(entityId, description, race, gender, owner,
                                  //iStrength, iMagic, iConstitution, iWillpower, iAgility, iDexterity,
                                  cHP, mHP,
                                  iNaturalArmor);
    cellTable[x][y].entity = eTemp;
    tEntities.put(entityId, new Point(x, y));

    // Add initiative
    //tInitiative.put(entityId, new Integer(rollDice(1, 10) + currentInitiative));

    return(eTemp);
  }

  public Entity getEntity(int posX, int posY)
  {
    PCharacter rValue = (PCharacter)this.cellTable[posX][posY].entity;
    return(rValue);
  }

  public Entity getEntity(String entityId)
  {
    Point p = (Point)tEntities.get(entityId);
    PCharacter e = (PCharacter)this.cellTable[p.x][p.y].entity;
    return (e);
  }
  
  public void setMap(String mapString)
  {
    System.out.println("Attempting to initialize map");
    StringTokenizer st = new StringTokenizer(mapString);

    // Get dimensions
    sizeX = Integer.parseInt(st.nextToken());
    sizeY = Integer.parseInt(st.nextToken());
    //cellTable = new LocationCell[sizeX][sizeY];
    initializeLocation(sizeX, sizeY);

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
  }


}