package tools;

public class EntityStatus
{
  public static final int typeAction = 0; // Action types decreses/expires at beginning of round, are usually of duration one, and always of range self.
  public static final int typeSpell = 1;  // Spell types decreases at the end of round of target. 

  public String id;
  public int type;
  public int duration;
  public int bonusArmorBase;

  public EntityStatus(String sId, int iStatusType, int iDuration, int iBonusArmorBase)
  {
    id = sId;
    type = iStatusType;
    duration = iDuration;
    bonusArmorBase = iBonusArmorBase;
  }
} 