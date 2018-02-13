package tools;

public class Entity
{
  public String id;
  public String description;
  public String gender;
  public String owner;
  public int curHP;
  public int maxHP;
  //public int protection;
  public int movement;

  public int armorNatural;
  public int armorBase = 0;
  public int armorHead = 0;
  public int armorHands = 0;
  public int armorLegs = 0;

  public int statusBits = 0;

  //protected Item[] equipment = new Item[7];
  //public Item[] equipment = new Item[7];
  public String equipment = null;

  public Entity(String sId, String sDescription, String sGender, String strOwner, int iCurHP, int iMaxHP, int iArmorBase)
  {
    id = sId;
    description = sDescription;
    gender = sGender;
    owner = strOwner;
    curHP = iCurHP;
    maxHP = iMaxHP;
    //protection = iProtection;
    armorBase = iArmorBase;
    
    movement = 3;

    //addEquipment(new Item("strName", "strType", 1, 1, 1, 1, 1, 1, 1), 1);
  }

  


}