package handler;

import tools.*;
import java.awt.Point;
import java.util.*;
import java.sql.*;

public class DataConnectorDD extends DataConnector
{

  public DataConnectorDD()
  {
    super();
  }

  protected void initPlayerInventory(Player player)
  { 
    String playerId = player.playerId;
    String sql;
    sql = "SELECT * FROM _PLAYERS " +
          "INNER JOIN _inventories ON (_PLAYERS.pId = _inventories.invOwner) " +
          "INNER JOIN dd_equipment ON (_inventories.invItem = dd_equipment.eId) " +
          "INNER JOIN _equipment_type ON (dd_equipment.eType = _equipment_type.tId) " +
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

        player.addInventoryItem(invId, new Item(invId, rs.getString("eName"), rs.getString("tName"), rs.getInt("tPriLocation"), rs.getInt("tSecLocation"), rs.getInt("eProtection"), 0, 0, rs.getInt("eDamageMin"), rs.getInt("eDamage"), rs.getInt("eDefence")));
        System.out.println("kjhgkjhg");
      }
    }
    catch(Exception exp)
    {}
  }

  protected Vector initPlayerEntities(Player player)
  {
    Vector rValue = new Vector();
    String playerId = player.playerId;
    System.out.println("initialising player entities");
    int x = 3;
    int y = 2;

    String sSQL;

    sSQL = "SELECT _characters.*, c_races.*, c_armor_natural.*, dd_abilities.* " +
           "FROM dd_abilities " +
           "   INNER JOIN _characters ON (dd_abilities.aCharacter = _characters.cId) " +
           "   INNER JOIN _PLAYERS ON (_characters.cOwner = _PLAYERS.pId) " +
           "   INNER JOIN c_races ON (_characters.cRace = c_races.rId) " +
           "   INNER JOIN c_armor_natural ON (c_races.rArmor = c_armor_natural.anId) " +
           "WHERE " +
           "      (pHandle = '" + playerId + "') ";

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
          //createEntity(x, y, rs.getString("cHandle"), playerId, rs.getInt("cHP"), rs.getInt("cMaxHP"));

          int stat1 = 0; //strength
          int stat2 = 0; //dexterity
          int stat3 = 0; //constitution
          int stat4 = 0; //intelligence
          int stat5 = 0; //wisdom
          int stat6 = 0; //charisma

          /*PCharacter eTemp = (PCharacter)addEntity("ent" + eCounter, x, y, rs.getString("cHandle"), rs.getString("rName"), rs.getString("cGender"), playerId,
                              stat1, stat2, stat3, stat4, stat5, stat6,
                              rs.getInt("cHP"), rs.getInt("cMaxHP"),
                              rs.getInt("anProtection"));*/

          PCharacter eTemp = new PCharacter("ent" + eCounter, rs.getString("cHandle"), rs.getString("rName"), rs.getString("cGender"), playerId,
                              rs.getInt("cHP"), rs.getInt("cMaxHP"),
                              rs.getInt("anProtection"));
          Point pTemp = new Point(x, y);

          //eTemp.addEquipment((Item)tInventory.get(rs.getString("cBaseArmor")), 4);
          String cWeapon = rs.getString("_characters.cWeapon");
          String cShieldHand = rs.getString("_characters.cShieldHand");
          String cBaseArmor = rs.getString("_characters.cBaseArmor");
          String cHead = rs.getString("_characters.cHead");
          String cArms = rs.getString("_characters.cArms");
          String cLegs = rs.getString("_characters.cLegs");
          String cAccessory = rs.getString("_characters.cAccessory");

          eTemp.strength = rs.getInt("aStrength");
          eTemp.dexterity = rs.getInt("aDexterity");
          eTemp.constitution = rs.getInt("aConstitution");
          eTemp.intelligence = rs.getInt("aIntelligence");
          eTemp.wisdom = rs.getInt("aWisdom");
          eTemp.charisma = rs.getInt("aCharisma");

          eTemp.strengthModifier = eTemp.evaluateAbilityModifier(eTemp.strength);
          eTemp.dexterityModifier = eTemp.evaluateAbilityModifier(eTemp.dexterity);
          eTemp.constitutionModifier = eTemp.evaluateAbilityModifier(eTemp.constitution);
          eTemp.intelligenceModifier = eTemp.evaluateAbilityModifier(eTemp.intelligence);
          eTemp.wisdomModifier = eTemp.evaluateAbilityModifier(eTemp.wisdom);
          eTemp.charismaModifier = eTemp.evaluateAbilityModifier(eTemp.charisma);

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

          rValue.add(pTemp);
          rValue.add(eTemp);
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
    return(rValue);
  }
}