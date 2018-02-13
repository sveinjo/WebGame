package handler;

import java.util.*;
//import tools.SocketVariables;
//import tools.*;

public class EncounterModel extends CombatModel
{

  //public String locationId = null;


  //public int testVar = 0;
  //private ChatCaster chat = null;
  //private Hashtable tPlayers = new Hashtable();
  //private Thread chatThread = null;
  //private SocketVariables cVar = new SocketVariables();

  public EncounterModel(String strLocationId)
  {
    super(strLocationId);
    //chat = new ChatCaster(tPlayers);
    chat = new ChatCaster();

    chatThread = new Thread(chat);
    chatThread.start();
    locationId = strLocationId;

    //this.setMap(this.getLocationData());
    //this.defineRoom(2,2);

  }

  public int getChatPort()
  {
    return(chat.port);
  }
  





}