package client;

import java.util.*;
import tools.*;
import java.awt.Point;
import java.lang.Math.*;

public class CombatController extends LocationController //implements Runnable
{
  private boolean turnActionLegal = false;
  private String turnEntityId = null;

  protected void initialize()
  {
    //super.initialize();
    //sendRequest(cVar.getCombatants);

  }

  public boolean isTurnEntity(String entityId)
  {
    boolean rValue = false;
    if (turnEntityId.equals(entityId))
      rValue = true;
    return (rValue);
  }

  private void setTurnActionLegal(String sParameters)
  {
    StringTokenizer st = new StringTokenizer(sParameters);
    turnEntityId = st.nextToken();
    turnActionLegal = true;
  }

  private void setTurnActionOver()
  {
    //sendRequest(cVar.getCombatants);
    turnActionLegal = false;
  }

  private void sendRequest(String request)
  {
    System.out.println("-+> " + request);
    this.socketWriteLine(request);
  }

  protected CombatController()
  {
    //super();
  }



  public String getCombatants()
  {
    int n, m;
    String returnString = "";
    for (m=0; m < model.sizeY; m++)
    {
      for (n=0; n < model.sizeX; n++)
      {
        if(model.cellTable[n][m].entity != null)
        {
          returnString += n + " " + m + " ";
        }
      }
    }
    returnString = returnString.trim();
    System.out.println(returnString);
    return(returnString);
  }

  public void sendCombatReady()
  {
    this.socketWriteLine(cVar.readyCombat);
    //this.recieveCombatActions();
  }

  public void serviceCombat(String responseLine)
  {
    String parameters = "";
    StringTokenizer st;

    if (responseLine.equals(cVar.requestAction))
    {
      parameters = this.socketReadLine();
      this.setTurnActionLegal(parameters);
      this.fireStateChanged(new ClientRefreshEvent(this, cVar.requestAction, parameters));
    }
    else if (responseLine.equals(cVar.statusUpdate))
    {
      parameters = this.socketReadLine();
      st = new StringTokenizer(parameters);
      model.updateStatus(st.nextToken(), Integer.parseInt(st.nextToken()));
      //Entity e = model.getEntity(tur);

      System.out.println("*** Status-change parameters: " + parameters);

      this.fireStateChanged(new ClientRefreshEvent(this, cVar.getCombatants, "parameters"));
    }
    else if (responseLine.equals(cVar.getCombatants))
    {
      parameters = this.socketReadLine();
      System.out.println(parameters);
      this.initializeCombatants(parameters);
      this.fireStateChanged(new ClientRefreshEvent(this, cVar.getCombatants, "parameters"));
    }
    else if (responseLine.equals(cVar.actionDefend))
    {
      parameters = this.socketReadLine();
    }
    else if (responseLine.equals(cVar.actionMove))
    {
      parameters = this.socketReadLine();
      st = new StringTokenizer(parameters);
      model.moveEntity(st.nextToken(), Integer.parseInt(st.nextToken()));
      //System.out.println("Action move has happened");
      this.fireStateChanged(new ClientRefreshEvent(this, cVar.actionMove, parameters));
    }
    else if (responseLine.equals(cVar.actionSwitch))
    {
      parameters = this.socketReadLine();
      st = new StringTokenizer(parameters);
      model.switchEntities(st.nextToken(), st.nextToken());
      System.out.println("Action switch has happened");
      this.fireStateChanged(new ClientRefreshEvent(this, cVar.actionSwitch, parameters));
    }
    else if (responseLine.equals(cVar.getEntityInventory))
    {
      parameters = this.socketReadLine();
      //System.out.println(parameters);
      st = new StringTokenizer(parameters);
      String entityId = st.nextToken();
      model.initializeEntityInventory(entityId, st.nextToken());
      String strInventory = model.getInventory(entityId);
      System.out.println(strInventory);
      this.fireStateChanged(new ClientRefreshEvent(this, cVar.getEntityInventory, entityId + cVar.separatorEntity + strInventory));
    }
    else if (responseLine.equals(cVar.actionAttack))
    {
      parameters = this.socketReadLine();
      st = new StringTokenizer(parameters);
      String sAttacker = st.nextToken();
      String sDefender = model.damageEntity(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
      if (sDefender == null)
      {
        this.fireStateChanged(new ClientRefreshEvent(this, cVar.actionAttack, parameters));
      }
      else
      {
        this.fireStateChanged(new ClientRefreshEvent(this, cVar.actionKill, sAttacker + " " + sDefender));
      }
    }
    else if (responseLine.equals(cVar.getPlayerInventory))
    {
      parameters = this.socketReadLine();
      model.initializeInventory(parameters);
      System.out.println(parameters);
      //this.initializeCombatants(parameters);
      //this.fireStateChanged(new ClientRefreshEvent(this, cVar.getCombatants, "parameters"));
    }
  }

  /*public void actionMove(int posX, int posY, int newPosX, int newPosY)
  {
    if(isTurnActionLegal())
    {
      String temp =  posX + " " + posY + " " + newPosX + " " + newPosY;
      sendAction(cVar.actionMove, temp);
      //System.out.println("Move request sendt: " + temp);
      setTurnActionOver();
    }
  }*/

  public void actionMove(int direction)
  {
    String temp = String.valueOf(direction);
    sendAction(cVar.actionMove, temp);
    //System.out.println("Move request sendt: " + temp);
    //setTurnActionOver();
  }

  public void actionAttack(String defenderId)
  {
    //Point p = model.getEntityPos(defenderId);
    //String temp =  posX + " " + posY + " " + p.x + " " + p.y;
    sendAction(cVar.actionAttack, defenderId);
    //setTurnActionOver();

  }

  public void actionDefend()
  {
    sendAction(cVar.actionDefend, "");
  }

  public void actionSwitch(String targetId)
  {
    System.out.println("Switching: " + targetId);
    sendAction(cVar.actionSwitch, targetId);
  }

  public void actionGetInventory(String targetId)
  {
    sendRequest(cVar.getEntityInventory, targetId);
  }

  public Item getInventoryItem(String itemId)
  {
    return(model.getInventoryItem(itemId));
  }

  private void sendAction(String action, String parameters)
  {

    parameters = parameters.trim();
    System.out.println("--send-> " + action + " " + parameters);
    setTurnActionOver();

    this.socketWriteLine(action);
    this.socketWriteLine(parameters);


  }

  private void sendRequest(String request, String parameters)
  {

    parameters = parameters.trim();
    System.out.println("r-send-> " + request + " " + parameters);

    this.socketWriteLine(request);
    this.socketWriteLine(parameters);


  }

  public boolean isTurnActionLegal()
  {
    boolean rValue = turnActionLegal;
    return(rValue);
  }

  public int getSwitchSide(String targetEntityId)
  {
    int rValue = -1;

    Point target = model.getEntityPos(targetEntityId);
    Point actor = model.getEntityPos(turnEntityId);

    if ((Math.abs(target.x - actor.x) <= 1) && (Math.abs(target.y - actor.y) <= 1))
    {
    if ((target.x < actor.x) && (target.y < actor.y))
      rValue = 0;
    else if ((target.x == actor.x) && (target.y < actor.y))
      rValue = 1;
    else if ((target.x > actor.x) && (target.y < actor.y))
      rValue = 2;
    else if ((target.x < actor.x) && (target.y == actor.y))
      rValue = 7;
    else if ((target.x > actor.x) && (target.y == actor.y))
      rValue = 3;
    else if ((target.x < actor.x) && (target.y > actor.y))
      rValue = 6;
    else if ((target.x == actor.x) && (target.y > actor.y))
      rValue = 5;
    else if ((target.x > actor.x) && (target.y > actor.y))
      rValue = 4;
    }
    //System.out.println(rValue);
    //System.out.println(target.x + " " + target.y + "---" + actor.x + " " + actor.y);
    return(rValue);
  }

  private void initializeCombatants(String temp)
  {
    //System.out.println("Initializing combatants: " + temp);
    model.purge();
    splitCombatants(temp);
  }

  private void splitCombatants(String strServer)
  {
    System.out.println("splitCombatants");
    StringTokenizer st = new StringTokenizer(strServer, cVar.separatorParagraph);
    while(st.hasMoreTokens())
    {
      String temp = st.nextToken();
      updateCombatant(temp);
    }
  }

  private void updateCombatant(String strServer)
  {
    System.out.println("updateCombatant: " + strServer);
    String s;
    StringTokenizer st = new StringTokenizer(strServer, cVar.separator);

    String id = st.nextToken();
    int x = Integer.parseInt(st.nextToken());
    int y = Integer.parseInt(st.nextToken());
    String owner = st.nextToken();
    //s = st.nextToken("'");
    //String desc = s.substring(1, s.length());
    String desc = st.nextToken();

    //s = st.nextToken(" ");

    //int cHP = Integer.parseInt(s.substring(1, s.length()));
    int cHP = Integer.parseInt(st.nextToken());
    int mHP = Integer.parseInt(st.nextToken());
    int protection = Integer.parseInt(st.nextToken());
    String gender = st.nextToken();

    //model.cellTable[x][y].entity = new Entity(id, desc, owner, cHP, mHP);
    model.addEntity(id, x, y, desc, gender, owner, cHP, mHP, protection);
  }

  private void updateInventory()
  {}

  public boolean checkRange(String entityId)
  {
    boolean rValue = false;
    //if (turnActionLegal)
    //{
      //System.out.println("***Turn legal: " + turnEntityId);
      Point pAtt = model.getEntityPos(turnEntityId);
      Point pDef = model.getEntityPos(entityId);

      rValue = model.isInRange(pAtt.x, pAtt.y, pDef.x, pDef.y);
    //}

    //System.out.println("---" + turnActionLegal + " " + turnEntityId);
    return(rValue);
  }


}