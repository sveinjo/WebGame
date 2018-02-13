package handler;

import tools.*;
import java.util.*;
import java.awt.Point;

public class RulesCustom
{
  //public int movesLeftCounter = 0;
  //private String turnPlayer;
  //private String turnEntity;

  protected int currentInitiative = 0;
  protected Hashtable tInitiative = new Hashtable();

  public final int attackFumble = 1;
  public final int attackMissDodge = 2;
  public final int attackMissBlock = 4;
  public final int attackMissArmor = 8;
  public final int attackHitTouch = 16;
  public final int attackHit = 32;
  public final int attackCritical = 64;
  public final int attackKill = 128;
  public final int attackCleave = 256;

  private Random random;
  //private SocketVariables cVar = new SocketVariables();

  public RulesCustom()
  {
    random = new Random();
  }

  protected int rollDice(int low, int high)
  {
    int root = high - low + 1;
    int rValue = random.nextInt(root) + low;
    //System.out.println("***Random***: " + rValue);
    return(rValue);
  }

  public void addInitiative(PCharacter entity)
  {
    int initiative = rollDice(1, 10);
    tInitiative.put(entity.id, new Integer(initiative + currentInitiative));
    //tInitiative.put(entityId, new Integer(rollDice(1, 10) + currentInitiative));
  }

  public void removeInitiative(String entityId)
  {
    tInitiative.remove(entityId);
  }

  /*public String getNextActor()
  {
    String rValue = null;
    while(!tInitiative.containsValue(new Integer(currentInitiative)))
    {
      currentInitiative++;
    }
    String iEntity = getNewInitiativeEntity();
    tInitiative.put(iEntity, new Integer(currentInitiative + 10));
    rValue = iEntity;

    return(rValue);
  }*/

  private String getNewInitiativeEntity()
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
    //movesLeftCounter = getEntity(rValue).movement;
    return(rValue);
  }

  private int resolveToHit(PCharacter attacker, PCharacter defender)
  {
    int rValue = 0;

    int iAttackRoll = rollDice(1, 20);
    int iAttackRating = attacker.weaponAttack; // + attacker.dexterity;

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
    }
    return(rValue);
  }

  private int resolveDamage(PCharacter attacker, PCharacter defender)
  {
    
    int rValue = 0;
    int damageRoll = rollDice(0, attacker.weaponDamage);
    int damageModifier = 0; // + attacker.strength;
    int defence = defender.armorBase; // + defender.constitution;

    int damage = damageRoll + damageModifier - defence;

    if (damage > 0)
    {
      rValue = damage;
    }

    return(rValue);
  }

  /*public Point attack(PCharacter eAtt, PCharacter eDef)
  {
    Point rValue = new Point();

    if (resolveToHit(eAtt, eDef) >= this.attackHit)
    {
      int damage = resolveDamage(eAtt, eDef);
      rValue.x += this.attackHit;
      if (damage > 0)
      {
        rValue.y = damage;
        eDef.curHP = eDef.curHP - damage;

        //Update initiative
        tInitiative.put(eAtt.id, new Integer(((Integer)tInitiative.get(eAtt.id)).intValue() + eAtt.weaponInitiative));

        if (eDef.curHP <= 0)
        {
          rValue.x += this.attackKill;
        }
      }
      else
      {
        rValue.y = 0;
      }
    }
    else
    {
      rValue.x = this.attackMissDodge;
    }
    return(rValue);
  } */


}