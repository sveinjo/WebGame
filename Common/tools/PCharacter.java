package tools;

import java.util.*;


public class PCharacter extends PCharacterDD
{
  private SocketVariables cVar = new SocketVariables();

  /*public int strength = 0;
  public int magic = 0;
  public int constitution = 0;
  public int willpower = 0;
  public int agility = 0;
  public int dexterity = 0;*/

  public String race;

  public String weapon = "None";
  public int weaponInitiative = 0;
  public int weaponRange = 1;
  public int weaponAttack = 0;
  public int weaponDamageMin = 0;
  public int weaponDamage = 0;
  public int weaponDefence = 0;
  

  private Vector statusChain = new Vector();

  public Item[] equipment = new Item[7];

  // Status Flags (binary):
  private boolean bDefending = false; // 1

  /*public PCharacter(String sId, String sDescription, String sGender, String strOwner, int iCurHP, int iMaxHP,
                    int iNaturalArmor, int iBaseArmor,
                    String sWeapon, int wInitiative, int wRange, int wDamageMin, int wDamageMax)*/
  /*public PCharacter(String sId, String sDescription, String sRace, String sGender, String strOwner,
                    //int iStrength, int iMagic, int iConstitution, int iWillpower, int iAgility, int iDexterity,
                    int iCurHP, int iMaxHP,
                    int iNaturalArmor)*/
  public PCharacter(String sId, String sDescription, String sRace, String sGender, String strOwner,
                    int iCurHP, int iMaxHP,
                    int iNaturalArmor)
  {
    //super(sId, sDescription, sGender, strOwner, iCurHP, iMaxHP, iNaturalArmor);

    /*super(sId, sDescription, sRace, sGender, strOwner,
    //iStrength, iDexterity, iConstitution, iIntelligence, iWisdom, iCharisma,
    //iStrength, iMagic, iConstitution, iWillpower, iAgility, iDexterity,
    iCurHP, iMaxHP,
    iNaturalArmor);*/
    super(sId, sDescription, sRace, sGender, strOwner,
    iCurHP, iMaxHP,
    iNaturalArmor);

    race = sRace;
    armorNatural = iNaturalArmor;

    /*strength = iStrength;
    magic = iMagic;
    constitution = iConstitution;
    willpower = iWillpower;
    agility = iAgility;
    dexterity = iDexterity;*/

    armorBase = armorNatural;

    //armorBase = iBaseArmor;
    //weapon = sWeapon;
    //weaponInitiative = wInitiative;
    //weaponRange = wRange;
    //weaponDamageMin = wDamageMin;
    //weaponDamageMax = wDamageMax;

    //System.out.println(description + " " + id + " opprettet!");
    //System.out.println(weapon);
  }

  public int addStatus(EntityStatus status)
  {
    statusChain.add(status);
    if ((status.id).equals(cVar.actionDefend))
    {
      bDefending = true;
      armorBase += status.bonusArmorBase;
    }
    setStatusFlags();
    return(statusBits);
  }

  private void resetStatusFlags()
  {
    this.bDefending = false;
  }

  private void resetBuffs(EntityStatus status)
  {
    if ((status.id).equals(cVar.actionDefend))
    {
      this.armorBase -= status.bonusArmorBase;
    }
  }

  private int setStatusFlags()
  {
    resetStatusFlags();
    Enumeration e = statusChain.elements();
    EntityStatus status;

    while(e.hasMoreElements())
    {
      status = (EntityStatus)e.nextElement();
      if ((status.id).equals(cVar.actionDefend))
      {
        this.bDefending = true;
      }
    }

    setStatusBits();

    return(statusBits);

  }

  public int updateCounters(int iStatusType)
  {
    //resetFlags();
    Enumeration e = statusChain.elements();
    EntityStatus status;
    if (statusChain.size() > 0)
    {
      while(e.hasMoreElements())
      {
        status = (EntityStatus)e.nextElement();
        if (status.type == iStatusType)
        {
          status.duration -= 1;
          if (status.duration == 0)
          {
            resetBuffs(status);
            System.out.println("Removing Status: " + status.id);
            statusChain.removeElement(status);
          }
        }
      }
    }
    else
    {
      statusBits = -1;
    }
    //setStatusBits();
    setStatusFlags();

    return(statusBits);

  }

  private void setStatusBits()
  {
    statusBits = 0;
    if (this.bDefending)
      this.statusBits = 1;
  }

  public void addEquipment(Item item, int position)
  {
    System.out.println("---***---");

    if (position == item.priLocation | position == item.secLocation)
    {
      if (equipment[position - 1] != null)
      {
        //remove item
      }
      this.equipment[position - 1] = item;

      armorBase += item.protection;
      //armorHead = 0;
      //armorHands = 0;
      //armorLegs = 0;

      switch(position)
      {
        case (3):
        {
          weapon = item.name;
        }
      }
      //weaponRange = 1;

      weaponInitiative += item.initiative;
      weaponAttack += item.attack;
      weaponDamageMin += item.damageMin;
      weaponDamage += item.damage;
      weaponDefence += item.defence;

    }
  }

}