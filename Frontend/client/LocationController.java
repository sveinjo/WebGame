package client;

import java.util.*;

public class LocationController extends SocketController
{

  protected LocationController()
  {
    //super();
  }
  
  /*public String getLocationData()
  {

    this.socketWriteLine(cVar.getLocationData);
    //String temp = recieveTransmission();
    //System.out.println("loc: " + temp);

    return ("temp");
  }*/

  protected void serviceLocation(String responseLine)
  {
    String parameters = "";
    StringTokenizer st;

    if (responseLine.equals(cVar.getLocationSize))
    {
      parameters = this.socketReadLine();
      System.out.println(parameters);
      st = new StringTokenizer(parameters);

      model.initializeLocation(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
      this.fireStateChanged(new ClientRefreshEvent(this, cVar.getLocationSize, parameters));
    }
    else if (responseLine.equals(cVar.getLocationData))
    {
      parameters = this.socketReadLine();
      System.out.println(parameters);
      this.fireStateChanged(new ClientRefreshEvent(this, cVar.getLocationData, parameters));
    }
  }

  /*public String enterLocation()
  {
    createSocketConnection();
    initializeChat();
    String sLocation = getLocationData();
    //String sCombat = getCombatants();
    //System.out.println(sCombat);
    initializeCombatants();
    getCombatants();


    return(sLocation);

  } */


} 