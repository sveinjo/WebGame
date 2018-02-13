package client;

//import java.awt.*;
//import java.io.*;
import java.net.*;
import tools.*;
import netscape.javascript.*;
//import java.util.*;

public class ClientController extends CombatController implements Runnable
{
  private ChatListener chatListener = null;


  //private Thread chatThread = null;



  //public ClientModel model = null;


  protected ClientController(ClientRefreshListener refreshListener, ChatListener interfaceClass, String strHost, int iPort, String strPlayerId, String strLocationId)
  {
    super();
    //model = new ClientModel(strPlayerId, strLocationId);

    //this.initializeChat(interfaceClass);
    chatListener = interfaceClass;
    host = strHost;
    socketPort = iPort;
    playerId = strPlayerId;
    locationId = strLocationId;
    this.addclientRefreshListener(refreshListener);

    this.initialize();




    /*model = new ClientModel(strPlayerId, strLocationId);

    //chatListener = classChatListener;
    chatListener = interfaceClass;
    host = strHost;
    socketPort = iPort;
    playerId = strPlayerId;
    locationId = strLocationId;
    //this.enterLocation();*/
  }

  public void run()
  {
    //this.recieveCombatActions();
    this.serviceServer();
  }

  private void serviceServer()
  {
    String responseLine = "";

    try
    {
      while ((responseLine = this.socketReadLine()) != null)
      {
        System.out.print("<--recieve- " + responseLine + " ");
        serviceCombat(responseLine);
        serviceLocation(responseLine);
        System.out.println();
      }
    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }
  }

  public Entity getEntity(String entityId)
  {
    return(model.getEntity(entityId));
  }

  public Entity getEntity(int posX, int posY)
  {
    //return(model.cellTable[posX][posY].entity);
    return(model.getEntity(posX, posY));
  }

  
  protected void initialize()
  {
    initializeSocketConnection();
    initializeChat(chatListener);
    super.initialize();



  }



  /*public String endTurn()
  {
    this.socketWriteLine(cVar.turnTerminator);
    return (recieveTransmission());
  }*/



  protected void performCleanup()
  {
    //chat.sendChatMessage(cVar.chatDisconnect + " " + chatPort);
    super.performCleanup();


    //chatThread.interrupt();
    //chat.stopChatThread();

    //chatThread.stop();

    //chatThread = null;

  }

  /*private void initializeChat()
  {


    chat.addChatListener(chatListener);
    chatThread = new Thread(chat);
    chatThread.start();
    System.out.println("initializing chat");
  }  */
} 