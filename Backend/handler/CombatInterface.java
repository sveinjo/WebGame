package handler;

import java.net.*;
import tools.*;
import java.util.*;
import java.awt.*;

public class CombatInterface extends LocationInterface implements TurnListener
{
  public CombatInterface(ModelCache mCache, Socket s)
  {
    super(mCache, s);
    this.model.addTurnListener(this);
    //model.createEntity(4, 2, this.playerId, this.playerId, 10, 10);


    this.sendData(cVar.getPlayerInventory, model.addPlayerInventory(playerId));

    model.addPlayerEntities(this.playerId);

  }

  public void refreshAction()
  {
    // Refresh when new player enters
    //if ((e.action).equals("needsRefresh"))
    //{
      String cData = model.getCombatants();
      sendData(cVar.getCombatants, cData);
    //}
  } 

  public void broadcastAction(TurnEvent e)
  {
    //System.out.println(playerId + " broadCastAction kicking in!");
    if (e.parameter == null)
      e.parameter = "";

    // Refresh when new player enters
    /*if ((e.action).equals("needsRefresh"))
    {
      String cData = model.getCombatants();
      sendData(cVar.getCombatants, cData);
    }*/

    this.sendData(e.action, e.entity.id + " " + e.parameter);

    //String cData = model.getCombatants();
    //sendData(cVar.getCombatants, cData);

  }

  /*public void requestAction(String strPlayerId, String entityId, Point entityPos)
  {


    if(strPlayerId.equals(this.playerId))
    {
      this.sendData(cVar.requestAction, entityId + " " + entityPos.x + " " + entityPos.y);
    }
  }*/

  public void pointcastAction(String playerId, TurnEvent e)
  {

    if (e.parameter == null)
      e.parameter = "";




    if((e.action).equals(cVar.requestAction))
    {

      Point entityPos = (Point)e.parameter;
      this.sendData(cVar.requestAction, e.entity.id + " " + entityPos.x + " " + entityPos.y);
    }
    else
    {
      this.sendData(e.action, e.entity.id + " " + e.parameter);
    }


    /*else if ((e.action).equals(cVar.getEntityInventory))
    {
      sTemp = this.socketReadLine();
      //model.actionSwitchEntity(sTemp);
      model.getEntityInventory(this.playerId, sTemp);
      //refreshAction();
    }*/
  }

  public String getPlayerId()
  {
    return(this.playerId);
  }

  public void serviceCombat(String inputLine)
  {

    if (model.isTurnPlayer(this.playerId))
    {
      String sTemp;

      System.out.println("--- " + inputLine);
        /*if (inputLine.equals(cVar.turnTerminator))
        {
          System.out.println(inputLine);
          processData(inputLine);

          break;
        }*/
        /*else if (inputLine.equals(cVar.getLocationData))
        {
          sendData(getLocationData());
          //break;
        }*/
        //else if (inputLine.equals(cVar.getCombatants))



        //if (inputLine.equals(cVar.getCombatants))
        //{
          //String cData = model.getCombatants();
          //sendData(cVar.getCombatants, cData);
          //break;
        //}

      if (inputLine.equals(cVar.actionMove))
      {
        sTemp = this.socketReadLine();
        model.actionMoveEntity(sTemp);
      }
      else if (inputLine.equals(cVar.actionAttack))
      {
        sTemp = this.socketReadLine();
        model.actionAttackEntity(sTemp);
      }
      else if (inputLine.equals(cVar.actionDefend))
      {
        //sTemp = this.socketReadLine();
        model.actionDefend();
      }
      else if (inputLine.equals(cVar.actionSwitch))
      {
        sTemp = this.socketReadLine();
        model.actionSwitchEntity(sTemp);
        //refreshAction();
      }
      else if (inputLine.equals(cVar.getEntityInventory))
      {
        sTemp = this.socketReadLine();
        //model.actionSwitchEntity(sTemp);
        model.getEntityInventory(this.playerId, sTemp);
        //refreshAction();
      }
    }
  }


}