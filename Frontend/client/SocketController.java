package client;

import java.net.*;
import tools.*;
import java.util.*;

public class SocketController extends tools.SocketManipulator
{
  protected SocketVariables cVar = new SocketVariables();

  protected ChatListener chatListener = null;
  protected String host = null;
  protected int socketPort;
  public String playerId = null;
  protected String locationId = null;

  private Thread chatThread = null;





  private int chatPort; // might not be needed
  protected ClientChat chat = null;

  protected ClientModel model = null;
  private transient Vector clientRefreshListeners;

  protected SocketController()
  {
    super();
    model = new ClientModel(playerId, locationId);
  }

  protected SocketController(String strHost, int iPort, String strPlayerId, String strLocationId)
  {


    //chatListener = classChatListener;

    host = strHost;
    socketPort = iPort;
    playerId = strPlayerId;
    locationId = strLocationId;

    model = new ClientModel(strPlayerId, strLocationId);
    //this.enterLocation();
  }

  protected void initializeChat(ChatListener chatListener)
  {


    chat.addChatListener(chatListener);
    chatThread = new Thread(chat);
    chatThread.start();
    //System.out.println("initializing chat");
  }

  protected void performCleanup()
  {
    super.performCleanup();
    chat.sendChatMessage(cVar.chatDisconnect + " " + chatPort);
    //chatThread.stop();
    chat.stopExecution();
  }

  public void sendChatMessage(String inputString)
  {
    //System.out.println("sending message");
    chat.sendChatMessage(inputString);
  }

  protected void initializeSocketConnection()
  {
    try
    {
      this.assignSocket(new Socket(host, socketPort));

      // Identify player
      this.socketWriteLine(playerId);
      this.socketWriteLine(locationId);

      // Recieve chat-port
      chatPort = Integer.parseInt(this.socketReadLine());
      System.out.println("chat port: " + chatPort);

    }
    catch (Exception exp)
    {
      System.out.println(exp);
    }
    chat = new ClientChat(host, chatPort, playerId);
    //initializeChat();
  }

  /*protected String recieveTransmission()
  {
    String rValue = "";
    String responseLine = "";
    // Read the response

    try
    {
      while ((responseLine = this.socketReadLine()) != null)

      {
        System.out.println(responseLine);

        if (responseLine.equals(cVar.dataTerminator))
        {
          //Regex rx = new Regex("i", "");
          //inputLine = rx.replaceFirst(inputLine);

          //sendData();
          //model =
          break;
        }
        rValue += responseLine;
      }
    }
    catch (Exception exp)
    {}
    //System.out.println(rValue);
    return(rValue);
  }*/

  public void breakConnection()
  {

    performCleanup();
    //model = null;
  }

  public synchronized void removeclientRefreshListener(ClientRefreshListener l)
  {
    if(clientRefreshListeners != null && clientRefreshListeners.contains(l))
    {
      Vector v = (Vector) clientRefreshListeners.clone();
      v.removeElement(l);
      clientRefreshListeners = v;
    }
  }

  public synchronized void addclientRefreshListener(ClientRefreshListener l)
  {
    Vector v = clientRefreshListeners == null ? new Vector(2) : (Vector) clientRefreshListeners.clone();
    if(!v.contains(l))
    {
      v.addElement(l);
      clientRefreshListeners = v;
    }
  }

  protected void fireStateChanged(ClientRefreshEvent e)
  {
    //System.out.println("Fire state changed: " + e.type + " " + e.parameters);
    if(clientRefreshListeners != null)
    {
      Vector listeners = clientRefreshListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((ClientRefreshListener) listeners.elementAt(i)).stateChanged(e);
      }
    }
  }

} 