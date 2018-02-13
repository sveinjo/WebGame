package handler;

import java.util.*;
import java.net.*;
import java.io.*;
import tools.SocketVariables;   

public class ChatCaster implements Runnable
{
  public int port;
  public Hashtable tPlayers = null;
  private DatagramSocket socket = null;
  private SocketVariables cVar = new SocketVariables();
  //private Hashtable tPlayers = new Hashtable();

  public ChatCaster()
  {
    tPlayers = new Hashtable();
    try
    {
      socket = new DatagramSocket();
            //System.out.println(socket.getPort());
            port = socket.getLocalPort();
    }
    catch (Exception exp)
    {
      //exp.printStackTrace();
    }
  }

  /*public ChatCaster(Hashtable players)
  {
    tPlayers = players;
    try
    {
      socket = new DatagramSocket();
            //System.out.println(socket.getPort());
            port = socket.getLocalPort();
    }
    catch (Exception exp)
    {
      //exp.printStackTrace();
    }
  }*/

  

  public void broadcastMessage(String message)
  {
    
    //message = message + " " + port;
    DatagramPacket recvPacket, sendPacket;

    Enumeration enumPlayers = tPlayers.elements();
    Player player = null;
    byte outData[] = message.getBytes();

    while (enumPlayers.hasMoreElements())
    {
      player = (Player)enumPlayers.nextElement();
      sendPacket = new DatagramPacket(outData, outData.length, player.host, player.port);
      try
      {
                socket.send( sendPacket );
      }
      catch (Exception exp)
      {

        exp.printStackTrace();
      }
      //System.out.println(player.playerId + " " + player.host + " " + player.port);
    }
  }
  private void determineAction(DatagramPacket packet)
  {
    String inData = new String(packet.getData()).trim();

    if (inData.startsWith("/"))
    {
      StringTokenizer st = new StringTokenizer(inData, " ");
      String temp = st.nextToken();

      if (temp.equals(cVar.chatRegistration))
      {
        String playerId = st.nextToken();
        Player player = null;
        if (tPlayers.containsKey(playerId))
        {
          player = (Player)tPlayers.get(playerId);
          player.host = packet.getAddress();
          player.port = packet.getPort();
        }
        broadcastMessage(cVar.areaTag + " " + player.playerName + " enters.");
      }
      else if (temp.equals(cVar.chatDisconnect))
      {
        if  (tPlayers.isEmpty())
        {
          performCleanup();
        }
      }
    }
    else
    {
      broadcastMessage(inData);
      //System.out.println(inData);
    }
    //}
  }

  public void run()
  {
    DatagramPacket recvPacket;

    try
    {
      while (socket != null)
      {
        recvPacket= new DatagramPacket(new byte[cVar.chatLength], cVar.chatLength);
        socket.receive(recvPacket);
        this.determineAction(recvPacket);

      }
    }
    catch (SocketException se)
    {
      System.out.println("Error in SimpleDatagramServer: " + se);
    }
    catch (IOException ioe)
    {
      System.out.println("Error in SimpleDatagramServer: " + ioe);
    }
    System.out.println("chatcaster finished!");
  }

  public void performCleanup()
  {
    socket.close();
    socket = null;
    //System.out.println("---destruction---");
  }
}