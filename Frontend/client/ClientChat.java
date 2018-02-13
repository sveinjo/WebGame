package client;

import java.util.*;
import java.net.*;
import tools.SocketVariables;

public class ClientChat implements Runnable
{
  private transient Vector chatListeners;
  private DatagramPacket recvPacket, sendPacket;
  private DatagramSocket socket = null;
  private SocketVariables cVar = new SocketVariables();
  private int chatPort;
  private String playerId;
  private InetAddress hostAddress = null;

  public ClientChat(String host, int iChatPort, String strPlayerId)
  {
    playerId = strPlayerId;
    chatPort = iChatPort;
    //socket = clientSocket;
    try
    {
      //hostAddress = InetAddress.getByName("localhost");
      hostAddress = InetAddress.getByName(host);
      socket = new DatagramSocket();

      //jbInit();
      sendInitializeMessage();
      //run();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /*public void stopChatThread()
  {
    
    socket = null;
  }*/

  private void sendInitializeMessage()
  {
    sendChatMessage(cVar.chatRegistration + " " + playerId);


  }

  public void sendChatMessage(String chatMessage)
  {
  try
    {



      String userString = chatMessage;
      //textArea1.setText(userString);
                //if (userString == null || userString.equals(""))
                //    return;

                byte sendbuf[] = userString.getBytes();
                sendPacket = new DatagramPacket(sendbuf, sendbuf.length, hostAddress, chatPort );
                socket.send( sendPacket );
      //this.run();
    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }
  }


  public synchronized void addChatListener(ChatListener l)
  {
    Vector v = chatListeners == null ? new Vector(2) : (Vector) chatListeners.clone();
    if(!v.contains(l))
    {
      v.addElement(l);
      chatListeners = v;
    }
    //System.out.println("chatlistener added");
  }

  public synchronized void removeChatListener(ChatListener l)
  {
    if(chatListeners != null && chatListeners.contains(l))
    {
      Vector v = (Vector) chatListeners.clone();
      v.removeElement(l);
      chatListeners = v;
    }
  }

  protected void fireChatLineRecieved(ChatEvent e)
  {
    if(chatListeners != null)
    {
      Vector listeners = chatListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((ChatListener) listeners.elementAt(i)).chatLineRecieved(e);
      }
    }
  }

  protected void fireChatConnectionVerified()
  {
    if(chatListeners != null)
    {
      Vector listeners = chatListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
      {
        ((ChatListener) listeners.elementAt(i)).chatConnectionVerified();
      }
    }
  }

  public void stopExecution()
  {
    socket.close();
    //socket.disconnect();
    socket = null;
  }

  public void run()
  {
    DatagramPacket recvPacket;

    try
    {

      // Recieve the first transmission to assure connection is made,
      // then fire ChatConnectionVerified to let the interface know.
      recvPacket= new DatagramPacket(new byte[cVar.chatLength], cVar.chatLength);
      socket.receive(recvPacket);
      //this.fireChatConnectionVerified(new ChatEvent(this, ""));
      this.fireChatConnectionVerified();

      while (socket != null)
      {

        recvPacket= new DatagramPacket(new byte[cVar.chatLength], cVar.chatLength);
        socket.receive(recvPacket);

        String chatLine = new String(recvPacket.getData(), 0, recvPacket.getLength());
        //System.out.print(chatLine + "\n");


        this.fireChatLineRecieved(new ChatEvent(this, chatLine));
      }

    }
    catch (Exception e)
    {
      System.out.println("Chat-thread stopping");
      //e.printStackTrace();
    }

    //System.out.println("Chat-thread stopped");
  }
}