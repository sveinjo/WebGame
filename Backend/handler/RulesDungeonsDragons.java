package handler;

import tools.*;
import java.util.*;
import java.awt.Point;


public class RulesDungeonsDragons extends RulesCustom
{
  private Random random;
  private int currentInitiative = 0;
  private int currentRound = 0;
  private int bestInitiative, worstInitiative;

  protected Hashtable tInitiative = new Hashtable();


  public RulesDungeonsDragons()
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

  public Vector getNextActor()
  {
    //Initialize Round 1
    if (currentRound == 0)
    {
      setInitiativeBounds();
      currentRound++;
      currentInitiative = bestInitiative;
    }

    //Set currentInitiative to the next initiative
    while(!tInitiative.containsValue(new Integer(currentInitiative)))
    {
      currentInitiative--;
    }

    //Get the entities to act next
    Integer i = new Integer(currentInitiative);
    Enumeration e = tInitiative.keys();
    String sTemp = null;
    Vector rValue = new Vector();
    while(e.hasMoreElements())
    {
      sTemp = (String)e.nextElement();
      if (tInitiative.get(sTemp).equals(i))
      {
        rValue.add(sTemp);
        //break;
      }
    }

    // End of initiative reinitialization
    currentInitiative--;
    if (currentInitiative < worstInitiative)
    {
      setInitiativeBounds();
      currentRound++;
      currentInitiative = bestInitiative;
    }

    return(rValue);
  }

  private void setInitiativeBounds()
  {
    Collection c = tInitiative.values();

    Object[] o = c.toArray();
    Arrays.sort(o);

    //min = o[0];
    //max = o[c.size() - 1];

    bestInitiative = ((Integer)o[c.size() - 1]).intValue();
    worstInitiative = ((Integer)o[0]).intValue();
    for (int n = 0; n < c.size(); n++)
    {
      System.out.println("+++" + o[n]);
    }
  }

  public void addInitiative(PCharacter entity)
  {
    int initiative = rollDice(1, 20); //+ entity.dexterity; // + improvedInitiativeFeat
    tInitiative.put(entity.id, new Integer(initiative + currentInitiative));
  }

  public void removeInitiative(String entityId)
  {
    tInitiative.remove(entityId);
  }

  private int resolveToHit(PCharacter eAtt, PCharacter eDef)
  {
    int rValue = this.attackMissDodge;
    int attack = rollDice(1, 20) + eAtt.weaponAttack + eAtt.strengthModifier;
    if (attack >= 10 + eDef.armorBase + eDef.armorNatural + eDef.weaponDefence + eDef.dexterityModifier)
      rValue = this.attackHit;
    return(rValue);
  }

  private int resolveDamage(PCharacter eAtt, PCharacter eDef)
  {
    int rValue = rollDice(eAtt.weaponDamageMin, eAtt.weaponDamage) + eAtt.strengthModifier;
    return(rValue);
  }

  public Point attack(PCharacter eAtt, PCharacter eDef)
  {
    Point rValue = new Point();
    int toHitValue = resolveToHit(eAtt, eDef);
    int damage = 0;

    rValue.x = toHitValue;

    if (toHitValue >= this.attackHit)
    {
      damage = resolveDamage(eAtt, eDef);
      rValue.y = damage;
      if (damage > 0)
      {
        eDef.curHP = eDef.curHP - damage;
        if (eDef.curHP <= 0)
        {
          rValue.x += this.attackKill;
        }
      }
    }
    return(rValue);
  }
}