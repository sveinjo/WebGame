package tools;

import java.util.*;

public class Item
{
  public String invId;
  public String name;
  public String type;
  public int priLocation;
  public int secLocation;
  public int protection;
  public int initiative;
  public int attack;
  public int damage;
  public int defence;

  //Dungeons and Dragons
  public int damageMin = 0;

  public Vector itemProperties;

  public Item(String strId, String strName, String strType, int iPriLocation, int iSecLocation, int iProtection,
              int iInitiative, int iAttack, int iDamageMin, int iDamage, int iDefence)
  {
    invId = strId;
    name = strName;
    type = strType;
    priLocation = iPriLocation;
    secLocation = iSecLocation;
    protection = iProtection;
    initiative = iInitiative;
    attack = iAttack;
    damage = iDamage;
    defence = iDefence;

    damageMin = iDamageMin;

    //priLocation = 4;
    //protection = 12;
  }
} 