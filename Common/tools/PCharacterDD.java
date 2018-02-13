package tools;

//import tools.*;

public class PCharacterDD extends Entity
{
  public int strength = 0;
  public int dexterity = 0;
  public int constitution = 0;
  public int intelligence = 0;
  public int wisdom = 0;
  public int charisma = 0;

  public int strengthModifier = 0;
  public int dexterityModifier = 0;
  public int constitutionModifier = 0;
  public int intelligenceModifier = 0;
  public int wisdomModifier = 0;
  public int charismaModifier = 0;

  public PCharacterDD(String sId, String sDescription, String sRace, String sGender, String strOwner,
                    //int iStrength, int iDexterity, int iConstitution, int iIntelligence, int iWisdom, int iCharisma,
                    int iCurHP, int iMaxHP,
                    int iNaturalArmor)
  {
    /*super(sId, sDescription, sRace, sGender, strOwner,
    iStrength, iDexterity, iConstitution, iIntelligence, iWisdom, iCharisma,
    iCurHP, iMaxHP,
    iNaturalArmor);*/

    super(sId, sDescription, sGender, strOwner, iCurHP, iMaxHP, iNaturalArmor);
  }

  public int evaluateAbilityModifier(int abilityValue)
  {
    int rValue = -5;
    int n;
    for(n = 1; n < abilityValue; n = n + 2)
    {
      rValue++;
    }
    System.out.println("Ability: " + abilityValue + ", modifier: " + rValue);
    return(rValue);
  }
} 