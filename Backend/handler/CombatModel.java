package handler;

import tools.*;
import java.util.*;
import java.awt.Point;

public class CombatModel extends LocationModel
{
  private RulesDungeonsDragons rules = new RulesDungeonsDragons();

  private String turnPlayer;
  private String turnEntity;
  private int movesLeftCounter = 0;

  private transient Vector turnListeners;
  private Vector vInitiative = new Vector();
  private Enumeration eInitiative = null;
  //protected int currentInitiative = 0;
  //protected Hashtable tInitiative = new Hashtable();

  private final int attackFumble = 0;
  private final int attackMiss = 1;
  private final int attackHit = 10;
  private final int attackCritical = 20;

  //private int currentInitiative = 0;

  public CombatModel(String strLocationId)
  {
    super(strLocationId);

    initComputerEntities(strLocationId);

    //initInitiative();
    //this.rollInitiative();
    //this.combatLoop();
  }

  public void addPlayerEntities(String playerId)
  {
    //initPlayerInventory(this.getPlayer(playerId));

    //initPlayerEntities(playerId);
    Vector v = initPlayerEntities(this.getPlayer(playerId));
    Enumeration e = v.elements();

    Point p;
    PCharacter pc;

    while(e.hasMoreElements())
    {
      p = (Point)e.nextElement();
      pc = (PCharacter)e.nextElement();
      //this.addEntity(pc.id, p.x, p.y, pc.description, pc.race, pc.gender, pc.owner, 0, 0, 0, 0, 0, 0, pc.curHP, pc.maxHP, pc.armorNatural);
      this.addEntity(pc, p.x, p.y);
    }

    fireRefreshAction();
  }

  protected void removeEntity(int x, int y)
  {

    Entity eTemp = getEntity(x, y);
    String eId = eTemp.id;
    //tInitiative.remove(eId);
    rules.removeInitiative(eId);
    super.removeEntity(x, y);
  }

  public Entity addEntity(PCharacter entity, int x, int y)
  {
    //super.addEntity(entityId, x, y, description, owner, cHP, mHP, sWeapon, wInitiative, wRange, wDamageMin, wDamageMax);
    //Entity eTemp = entity;
    cellTable[x][y].entity = entity;
    tEntities.put(entity.id, new Point(x, y));

    // Add initiative
    //tInitiative.put(entityId, new Integer(rollDice(1, 10) + currentInitiative));
    rules.addInitiative(entity);
    return(entity);
  }

  public Entity addEntity(String entityId, int x, int y, String description, String race, String gender, String owner,
                          //int iStrength, int iMagic, int iConstitution, int iWillpower, int iAgility, int iDexterity,
                          int cHP, int mHP,
                          int iNaturalArmor)
  {
    Entity eTemp = super.addEntity(entityId, x, y, description, race, gender, owner,
                          //iStrength, iMagic, iConstitution, iWillpower, iAgility, iDexterity,
                          //0,0,0,0,0,0,
                          cHP, mHP,
                          iNaturalArmor);
    // Add initiative
    //tInitiative.put(entityId, new Integer(rollDice(1, 10) + currentInitiative));
    //rules.addInitiative(entityId, rollDice(1, 10));
    rules.addInitiative((PCharacter)eTemp);
    return(eTemp);
  }



  public String addPlayerInventory(String playerId)
  {
    String rValue;
    Player player = getPlayer(playerId);
    initPlayerInventory(player);

    Enumeration iKeys = player.vInventoryOrder.elements();
    Item item;
    String sTemp = "";
    while(iKeys.hasMoreElements())
    {
      item = (Item)player.tInventory.get(iKeys.nextElement());
      sTemp += item.invId + "," + item.name + "," + item.type + "," + item.priLocation + "," + item.secLocation + "," + item.protection + "," + item.initiative + "," + item.attack + "," + item.damage + "," + item.defence + ";";

    }
    rValue = sTemp;
    return(rValue);
  }

  /*private Entity getEntity(String entityId)
  {
    Point p = (Point)tEntities.get(entityId);
    Entity e = this.cellTable[p.x][p.y].entity;
    return (e);
  }*/

  public boolean isTurnPlayer(String playerId)
  {
    boolean rValue = false;
    if (playerId.equals(turnPlayer))
      rValue = true;
    return(rValue);
  }

  public void actionDefend()
  {
    PCharacter e = (PCharacter)getEntity(turnEntity);

    //this.fireBroadcastAction(new TurnEvent(this, cVar.statusUpdate, turnEntity, String.valueOf(e.addStatus(new EntityStatus(cVar.actionDefend, EntityStatus.typeAction, 1, 4)))));
    this.fireBroadcastAction(new TurnEvent(this, cVar.statusUpdate, e, String.valueOf(e.addStatus(new EntityStatus(cVar.actionDefend, EntityStatus.typeAction, 1, 4)))));



    String message = this.getEntity(turnEntity).description + " takes a defensive stance.";
    chat.broadcastMessage(message);
    endTurn();
    //nextInitiative();
  }


  /*private int rollToHit(PCharacter attacker, PCharacter defender)
  {
    int rValue = 0;

    int iAttackRoll = rollDice(1, 20);
    int iAttackRating = attacker.weaponAttack + attacker.dexterity;

    int iDefence = 10;

    if (iAttackRoll == 20)
    {
      rValue = this.attackCritical;
    }
    else if (iAttackRoll == 1)
    {
      rValue = this.attackFumble;
    }
    else
    {
      if (iAttackRoll + iAttackRating >= iDefence)
        rValue = this.attackHit;
      else
        chat.broadcastMessage(attacker.description + " attacks " + defender.description + " but misses!");
    }
    return(rValue);
  } */

  /*private int rollAttackDamage(PCharacter attacker, PCharacter defender)
  {
    int rValue = 0;
    int damageRoll = rollDice(0, attacker.weaponDamage);
    int damageModifier = attacker.strength;
    int defence = defender.armorBase + defender.constitution;

    int damage = damageRoll + damageModifier - defence;

    if (damage > 0)
    {
      rValue = damage;
    }

    return(rValue);
  } */

  public void actionAttackEntity(String defenderId)
  {
    Point pAtt = getEntityPos(turnEntity);
    Point pDef = getEntityPos(defenderId);

    int attackerX = pAtt.x;
    int attackerY = pAtt.y;
    int defenderX = pDef.x;
    int defenderY = pDef.y;

    PCharacter eAtt = (PCharacter)this.getEntity(attackerX, attackerY);

    if(isInRange(attackerX, attackerY, defenderX, defenderY) && isTurnPlayer(eAtt.owner))
    {
      PCharacter eDef = (PCharacter)this.getEntity(defenderX, defenderY);
      Point result = rules.attack(eAtt, eDef);

      if(result.x >= rules.attackHit)
      {
        int damage = result.y;
        if (damage > 0)
        {
          if (result.x >= rules.attackKill)
          {
            this.removeEntity(defenderX, defenderY);
          }

          //Account for gender differenses
          String sGender = "its";
          if ((eAtt.gender).equals("m"))
            sGender = "his";
          else if ((eAtt.gender).equals("f"))
            sGender = "her";

          String parameters = defenderX + " " + defenderY + " " + damage;
          fireBroadcastAction(new TurnEvent(this, cVar.actionAttack, eAtt, parameters));
          this.chat.broadcastMessage(eAtt.description + " attacks " + eDef.description + " with " + sGender + " " + eAtt.weapon + " for " + damage + " points of damage!");
        }
        else
        {
          this.chat.broadcastMessage(eAtt.description + " strikes but the " + eAtt.weapon + " is deflected by " + eDef.description + "'s armor!");
        }
      }
      else
      {
        chat.broadcastMessage(eAtt.description + " attacks " + eDef.description + " but misses!");
      }
      endTurn();
    }
  }




  //Direction correspond to the 8 positions of the compass, starting with NW = 0, round clockwise to W = 7
  private boolean moveEntity(int posX, int posY, int direction)
  {
    boolean rValue = false;

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
    Entity eTemp = cellTable[posX][posY].entity;
    if (eTemp != null)
    {
      if (cellTable[posX + x][posY + y].entity == null && cellTable[posX + x][posY + y].structure != 9 && isTurnPlayer(eTemp.owner))
      {
        Point p = (Point)tEntities.get(eTemp.id);
        setEntityPos(eTemp, p.x + x, p.y + y);
        rValue = true;
      }
      else
      {
        System.out.println("kan ikke flytte hit");
      }
    }
    else
    {
      System.out.println("Ingen entity her!");
    }
    return(rValue);
  }

  public void actionMoveEntity(String sMovement)
  {
    Point p = getEntityPos(turnEntity);
    int posX = p.x;
    int posY = p.y;
    int direction = Integer.parseInt(sMovement);

    Entity e = getEntity(posX, posY);
    if (e != null)
    {
      String entityId = e.id;

      if(moveEntity(posX, posY , direction))
      {
        movesLeftCounter --;
        nextAction();

        fireBroadcastAction(new TurnEvent(this, cVar.actionMove, e, String.valueOf(direction)));
        this.chat.broadcastMessage(e.description + " has moved!");
      }
    }
  }



  public void actionSwitchEntity(String parameters)
  {
    Entity e1 = getEntity(turnEntity);
    Entity e2 = getEntity(parameters);

    Point actor = getEntityPos(turnEntity);
    Point target = getEntityPos(parameters);

    if ((Math.abs(target.x - actor.x) <= 1) && (Math.abs(target.y - actor.y) <= 1) && ((e1.owner).equals(e2.owner)))
    {
      System.out.println("Entities are allowed to switch");

      Entity tempId = getEntity(actor.x, actor.y);
      setEntityPos(getEntity(target.x, target.y), actor.x, actor.y);

      fireBroadcastAction(new TurnEvent(this, cVar.actionSwitch, e1, parameters));
      //fireRefreshAction();
      endTurn();
      this.chat.broadcastMessage("switching");
    }
  }

  public String getCombatants()
  {
    String s = cVar.separator;
    String returnString = "";
    int n, m;
    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {
        //g.drawRect(n*cellSize, m*cellSize, cellSize, cellSize);
        //cellTable[n][m] = new LocationCell();
        if ((cellTable[n][m].entity) != null)
        {
          Entity e = cellTable[n][m].entity;
          returnString += e.id + s + n + s + m + s + encodeEntityData(e) + cVar.separatorParagraph;
          //System.out.println(returnString);
        }
      }
    }
    returnString = returnString.trim();
    //returnString.substring(0, returnString.length()-1);
    return(returnString);
  }

  private String encodeEntityData(Entity e)
  {
    String s = cVar.separator;
    //return (e.owner + " " + e.description + "'" + e.curHP + " " + e.maxHP + " " + e.armorBase + " " + e.gender);
    return (e.owner + s + e.description + s + e.curHP + s + e.maxHP + s + e.armorBase + s + e.gender);
  }

  public void getEntityInventory(String playerId, String entityId)
  {

    String rValue = "";
    PCharacter e = (PCharacter)getEntity(entityId);

    //if ((e.owner).equals(playerId))
    if (e.owner != null && (e.owner).equals(playerId))

    {
      Item i;
      int n;

      for(n = 0; n < 7; n++)
      {
        if (e.equipment[n] != null)
        {
          i = (Item)e.equipment[n];
          rValue += i.invId + cVar.separator;
        }
        else
        {
          rValue += "0" + cVar.separator;
        }
      }
      rValue = rValue.substring(0, rValue.length() - 1);
      System.out.println(rValue);
      //fireBroadcastAction(new TurnEvent(this, cVar.getEntityInventory, e, rValue));
      firePointcastAction(playerId, new TurnEvent(this, cVar.getEntityInventory, e, rValue));
      //return(rValue);
    }
  }

  public void nextAction()
  {
    if (this.movesLeftCounter == 0)
    {
      /*String nextActor = rules.getNextActor();
      if (nextActor != null)
        turnEntity = nextActor;
      */
      Vector actors = rules.getNextActor();

      String nextActor = (String)actors.firstElement();
      System.out.println(nextActor);
      movesLeftCounter = getEntity(nextActor).movement;
      turnEntity = nextActor;
    }
    //turnEntity = "ent1";
    requestAction(turnEntity);
    //requestAction("ent1");
  }


  private void endTurn()
  {
    movesLeftCounter = 0;
    nextAction();
  }

  /*private String getNewInitiativeEntity()
  {
    Integer i = new Integer(currentInitiative);
    Enumeration e = tInitiative.keys();
    String sTemp = null;
    String rValue = null;

    while(e.hasMoreElements())
    {
      sTemp = (String)e.nextElement();
      if (tInitiative.get(sTemp).equals(i))
      {
        rValue = sTemp;
        break;
      }
    }
    System.out.println(currentInitiative);
    turnEntity = rValue;
    movesLeftCounter = getEntity(rValue).movement;
    return(rValue);
  }  */

  private void requestAction(String entityId)
  {
    Point p;


    //PCharacter e = (PCharacter)getEntity(rules.getTurnEntity());
    PCharacter e = (PCharacter)getEntity(entityId);

    //update counters
    int statusBits = e.updateCounters(EntityStatus.typeAction);
    if (statusBits != -1)
      this.fireBroadcastAction(new TurnEvent(this, cVar.statusUpdate, e, String.valueOf(statusBits)));


    if (isPlayerControlled(entityId))
      {
        p = (Point)tEntities.get(entityId);

        //this.fireRequestAction(this.cellTable[p.x][p.y].entity.owner, this.cellTable[p.x][p.y].entity.id, p);

        //turnPlayer = playerId;
        turnPlayer = e.owner;
        this.firePointcastAction(turnPlayer, new TurnEvent(this, cVar.requestAction, e, p));
        System.out.println("Waiting for " + entityId + " input");
      }
      else
      {
        actionDefend();
      }
  }



  /*public void combatLoop()
  {
    Point p;

    String strInitiative = "";
    while(eInitiative.hasMoreElements())
    {
      strInitiative = nextInitiative();
      if (isPlayerControlled(strInitiative))
      {
        p = (Point)tEntities.get(strInitiative);

        this.fireRequestAction(this.cellTable[p.x][p.y].entity.owner, this.cellTable[p.x][p.y].entity.id, p);
        System.out.println("Waiting for " + strInitiative + " input");
      }
      else
      {
        this.fireBroadcastAction(new TurnEvent(this, cVar.actionDefend, strInitiative, null));
        System.out.println("Computer blocks");
        String message = this.getEntity(strInitiative).description + " blocks.";
        chat.broadcastMessage(message);
      }
    }

  } */

  private boolean isPlayerControlled(String entityId)
  {
    //System.out.println("bøøh");
    boolean rValue = true;
    Point p = (Point)this.tEntities.get(entityId);
    Entity e = this.cellTable[p.x][p.y].entity;
    if (e.owner == null)
    {
      rValue = false;

    }

    return(rValue);
  }


  /*public void test()
  {
    this.fireBroadcastAction(new TurnEvent(this));
  }*/

  public synchronized void removeTurnListener(TurnListener l)
  {
    if(turnListeners != null && turnListeners.contains(l))
    {
      Vector v = (Vector) turnListeners.clone();
      v.removeElement(l);
      turnListeners = v;
    }
  }

  public synchronized void addTurnListener(TurnListener l)
  {
    Vector v = turnListeners == null ? new Vector(2) : (Vector) turnListeners.clone();
    if(!v.contains(l))
    {
      v.addElement(l);
      turnListeners = v;
    }
  }

  public boolean isCombatReady()
  {
    boolean rValue = true;
    Enumeration ePlayers = this.chat.tPlayers.elements();
    Player p = null;

    while(ePlayers.hasMoreElements())
    {
      p = (Player)ePlayers.nextElement();
      if (!p.isReady)
      {
        rValue = false;
        System.out.println("Combat is illegal");
      }
    }

    return(rValue);
  }





  public void fireBroadcastAction(TurnEvent e)
  {
    if(turnListeners != null)
    {
      Vector listeners = turnListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((TurnListener) listeners.elementAt(i)).broadcastAction(e);
      }
    }
  }

  /*public void fireRequestAction(String playerId, String entityId, Point entityPos)
  {
    turnPlayer = playerId;
    if(turnListeners != null)
    {
      Vector listeners = turnListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((TurnListener) listeners.elementAt(i)).requestAction(playerId, entityId, entityPos);
      }
    }
  }*/

  public void firePointcastAction(String playerId, TurnEvent e)
  {
    //turnPlayer = playerId;
    if(turnListeners != null)
    {
      Vector listeners = turnListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        if ((((TurnListener)listeners.elementAt(i)).getPlayerId()).equals(playerId))
        //if ((((CombatInterface)listeners.elementAt(i)).playerId).equals(playerId))
        {
          ((TurnListener) listeners.elementAt(i)).pointcastAction(playerId, e);
        }
      }
    }
  }

  public void fireRefreshAction()
  {
    if(turnListeners != null)
    {
      Vector listeners = turnListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((TurnListener) listeners.elementAt(i)).refreshAction();
      }
    }
  }


} 