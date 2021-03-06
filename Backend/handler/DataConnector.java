package handler;

import tools.*;
import java.awt.Point;
import java.util.*;
import java.sql.*;

public class DataConnector extends LocationStructAdvanced
{
  protected int eCounter = 0;
  

  private Connection conn;
  private String driverClassName = "com.mysql.jdbc.Driver";
  private String driverName = "mysql";
  private String dbHost = "localhost";
  private String dbName = "webgame";
  private String dbUser = "root";
  private String dbPassword = "";

  //private String dbHost = "mysql.stud.ntnu.no";
  //private String dbName = "sveinjo_webgame";
  //private String dbUser = "sveinjo_sql";
  //private String dbPassword = "3900.Ninja";
  

  public DataConnector()
  {
    try
    {
      Class.forName(driverClassName).newInstance();
      conn = DriverManager.getConnection("jdbc:" + driverName + "://" + dbHost + "/" + dbName + "?user=" + dbUser + "&password=" + dbPassword);
    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }
  }

  protected ResultSet executeQuery(String query)
  {
    ResultSet rs = null;
    try
    {
      Statement state = conn.createStatement();
      rs = state.executeQuery(query);
    }
    catch(Exception exp)
    {
      exp.printStackTrace();

    }
    return(rs);
  }

  protected String getLocationData(String locationId)
  {
    // Todo: Locationdata skal ideelt hentes fra databasen, men er forelÝbig hardkodet.
    String locationData = "7 7 9999999900000990000099000009900000990000099999999";
    //String locationData = "19 19 9999999999999999999900099990009999999990002002000999999999000999900099999999992999990002002999999200099000999099999909999900099909999992020020009990999999990999000999099999999099992999909999999909999099000009999990900029999099999909999990999909999990999900000990999999099990000099099999020002000002029999999999900000999999999999990000099999999999999999999999999";

    return(locationData);
  }

  /*protected void initPlayerEntities(Player player)
  {
    String playerId = player.playerId;
    System.out.println("initialising player entities");
    int x = 3;
    int y = 2;

    String sSQL;
    /*
    sSQL = "SELECT     _characters.cHandle, _characters.cGender, _characters.cMaxHP, _characters.cHP, _PLAYERS.pHandle, " +
           "WEAPONS.wName, WEAPONS.wInitiative, WEAPONS.wRange, WEAPONS.wBasDamageMin, " +
           "WEAPONS.wBasDamageMax, " +
           "c_armor_natural.anProtection, ARMOR_BASE.abProtection " +
           "FROM         _PLAYERS " +
           "  INNER JOIN _characters ON _PLAYERS.pId = _characters.cOwner " +
           "  INNER JOIN WEAPONS ON _characters.cWeapon = WEAPONS.wId " +
           "  INNER JOIN c_armor_natural ON _characters.cNaturalArmor = c_armor_natural.anId " +
           "  INNER JOIN ARMOR_BASE ON _characters.cBaseArmor = ARMOR_BASE.abId " +
           "           where _PLAYERS.pHandle = '" + playerId + "'";

    sSQL = "SELECT _characters.cHandle, " +
       "_characters.cGender, " +
       "_characters.cMaxHP, " +
       "_characters.cHP, " +
       "_characters.cStrength, " +
       "_characters.cMagic, " +
       "_characters.cConstitution, " +
       "_characters.cWillpower, " +
       "_characters.cAgility, " +
       "_characters.cDexterity, " +
       "_PLAYERS.pHandle, " +
       "WEAPONS.wName, " +
       "WEAPONS.wInitiative, " +
       "WEAPONS.wRange, " +
       "WEAPONS.wAttack, " +
       "WEAPONS.wDamage, " +
       "WEAPONS.wDefence, " +
       "c_armor_natural.anProtection, " +
       "ARMOR_BASE.abProtection, " +
       "ARMOR_BASE.abName " +
       "FROM ARMOR_BASE " +
       "INNER JOIN _characters ON (ARMOR_BASE.abId = _characters.cBaseArmor) " +
       "INNER JOIN WEAPONS ON (_characters.cWeapon = WEAPONS.wId) " +
       "INNER JOIN _PLAYERS ON (_characters.cOwner = _PLAYERS.pId) " +
       "INNER JOIN c_armor_natural ON (_characters.cNaturalArmor = c_armor_natural.anId) " +
       "WHERE " +
       "( " +
       "(_PLAYERS.pHandle = '" + playerId + "'))";

    // old stuff ends here

    sSQL = "SELECT _characters.cHandle, _characters.cGender, " +
       "_characters.cMaxHP, _characters.cHP, " +
       "_characters.cWeapon, _characters.cShieldHand, _characters.cBaseArmor, _characters.cHead, _characters.cArms, _characters.cLegs, _characters.cAccessory, " +
       "c_races.rName, c_races.rMovement, " +
       "c_armor_natural.anName, c_armor_natural.anProtection, " +
       "_PLAYERS.pHandle, _PLAYERS.pLocation " +
       "FROM _PLAYERS " +
       "INNER JOIN _characters ON (_PLAYERS.pId = _characters.cOwner) " +
       "INNER JOIN c_races ON (_characters.cRace = c_races.rId) " +
       "INNER JOIN c_armor_natural ON (c_races.rArmor = c_armor_natural.anId) " +
       "WHERE (pHandle = '" + playerId + "') ";

    ResultSet rs = executeQuery(sSQL);

    boolean space, character;
    try
    {
      space = true;
      character = rs.next();

      //while(space && character)
      while(character)
      {
        if (this.getEntity(x, y) == null)
        {
          int stat1 = 0; //strength
          int stat2 = 0; //magic
          int stat3 = 0; //constitution
          int stat4 = 0; //willpower
          int stat5 = 0; //agility
          int stat6 = 0; //dexterity

          //createEntity(x, y, rs.getString("cHandle"), playerId, rs.getInt("cHP"), rs.getInt("cMaxHP"));
          PCharacter eTemp = (PCharacter)addEntity("ent" + eCounter, x, y, rs.getString("cHandle"), rs.getString("rName"), rs.getString("cGender"), playerId,
                              stat1, stat2, stat3, stat4, stat5, stat6,
                              rs.getInt("cHP"), rs.getInt("cMaxHP"),
                              rs.getInt("anProtection"));

          //eTemp.addEquipment((Item)tInventory.get(rs.getString("cBaseArmor")), 4);
          String cWeapon = rs.getString("_characters.cWeapon");
          String cShieldHand = rs.getString("_characters.cShieldHand");
          String cBaseArmor = rs.getString("_characters.cBaseArmor");
          String cHead = rs.getString("_characters.cHead");
          String cArms = rs.getString("_characters.cArms");
          String cLegs = rs.getString("_characters.cLegs");
          String cAccessory = rs.getString("_characters.cAccessory");

          if(cWeapon != null)
            eTemp.addEquipment(player.getInventoryItem(cWeapon), 3);
          if(cShieldHand != null)
            eTemp.addEquipment(player.getInventoryItem(cShieldHand), 5);
          if(cBaseArmor != null)
            eTemp.addEquipment(player.getInventoryItem(cBaseArmor), 4);
          if(cHead != null)
            eTemp.addEquipment(player.getInventoryItem(cHead), 2);
          if(cArms != null)
            eTemp.addEquipment(player.getInventoryItem(cArms), 6);
          if(cLegs != null)
            eTemp.addEquipment(player.getInventoryItem(cLegs), 7);
          if(cAccessory != null)
            eTemp.addEquipment(player.getInventoryItem(cAccessory), 1);

          x++;
          eCounter++;
          //space = true;
          character = rs.next();
        }
        else
        {
          x++;

        }
      }
    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }
  }*/

  //protected void initPlayerInventory(String playerId)
  protected void initPlayerInventory(Player player)
  {
    String playerId = player.playerId;
    String sql;
    sql = "SELECT _inventories.invId, _inventories.invOrder, " +
          "c_equipment.eId, c_equipment.eName, c_equipment.eType, " +
          "c_equipment.eProtection, c_equipment.eInitiative, c_equipment.eAttack, " +
          "c_equipment.eDamage, c_equipment.eDefence, " +
          "_equipment_type.tName, _equipment_type.tPriLocation, _equipment_type.tSecLocation " +
          "FROM _PLAYERS " +
          "INNER JOIN _inventories ON (_PLAYERS.pId = _inventories.invOwner) " +
          "INNER JOIN c_equipment ON (_inventories.invItem = c_equipment.eId) " +
          "INNER JOIN _equipment_type ON (c_equipment.eType = _equipment_type.tId) " +
          "WHERE (pHandle = '" + playerId + "') " +
          "ORDER BY _inventories.invOrder ";

    ResultSet rs = executeQuery(sql);

    try
    {
      String invId;
      while(rs.next())
      {
        //tInventory.put(rs.getString("invId"), new Item(rs.getString("eName"), rs.getString("tName"), rs.getInt("tPriLocation"), rs.getInt("tSecLocation"), rs.getInt("eProtection"), rs.getInt("eInitiative"), rs.getInt("eAttack"), rs.getInt("eDamage"), rs.getInt("eDefence")));
        invId = rs.getString("invId");
        player.addInventoryItem(invId, new Item(invId, rs.getString("eName"), rs.getString("tName"), rs.getInt("tPriLocation"), rs.getInt("tSecLocation"), rs.getInt("eProtection"), rs.getInt("eInitiative"), rs.getInt("eAttack"), 1, rs.getInt("eDamage"), rs.getInt("eDefence")));
      }
    }
    catch(Exception exp)
    {}
  }

  protected void initComputerEntities(String locationId)
  {
    //addEntity("ent" + eCounter, 2, 2, "Monster ting", "gnoll", "m", null, 0, 0, 0, 0, 0, 0, 10, 10, 1);
    addEntity("ent" + eCounter, 2, 2, "Monster ting", "gnoll", "m", null, 10, 10, 1);
    
    /*String entityId, int x, int y, String description, String race, String gender, String owner,
                          int iStrength, int iMagic, int iConstitution, int iWillpower, int iAgility, int iDexterity,
                          int cHP, int mHP,
                          int iNaturalArmor*/

    eCounter++;
  }

  /*private void createEntity(int x, int y, String description, String owner, int currHP, int maxHP)
  {
    String sId = "ent" + eCounter;
    this.cellTable[x][y].entity = new Entity(sId, description, owner, currHP, maxHP);
    this.tEntities.put(sId, new Point(x,y));
    eCounter++;

    //return(returnValue);
  }*/
}