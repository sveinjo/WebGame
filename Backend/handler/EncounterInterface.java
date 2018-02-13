package handler;

import tools.*;
import java.net.*;
import java.io.*;
import java.util.*;
//import com.stevesoft.pat.*;

class EncounterInterface extends CombatInterface implements Runnable
{

  public EncounterInterface(ModelCache mCache, Socket s)
  {
    super(mCache, s);
    //modelCache = mCache;

  }

  public void serviceClient()
  {
    try
    {
      String inputLine = "";
      //String sTemp;
      //StringTokenizer st = null;

      while ((inputLine = this.socketReadLine()) != null)
      {
        serviceCombat(inputLine);
        if (inputLine.equals(cVar.readyCombat))
        {
          //System.out.println("Combat readiness aknowledged");
          model.getPlayer(playerId).isReady = true;

          if (model.isCombatReady())
          {
            model.nextAction();
            //model.rollInitiative();
            //model.combatLoop();
          }
        }
      }

    }
    catch (Exception exp)
    {
      exp.printStackTrace();
      performCleanup();
    }

  }

  public void run ()
  {
    try
    {
      serviceClient();
    }
    catch (Exception e)
    {
      System.err.println ("SrvX: Exception sending a string to the "
      + "client: ");
       e.printStackTrace();
    }

    performCleanup();
    System.out.println("Interface has been cleaned");
  }
  

  /*public void processData(String inputData)
  {
    sendData("-return data");
    serviceClient();
  }*/
}
