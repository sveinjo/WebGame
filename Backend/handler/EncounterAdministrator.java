package handler;

import java.net.*;
import java.io.*;

class EncounterAdministrator
{
  private static final int serverPort = 9999;
  static ModelCache mCache = new ModelCache();

  public static void main (String argv[])
  {
    Socket sock;
    ServerSocket ssock;

    //Thread thServx;
    //EncounterInterface servx;

    System.out.println ("Serv: Initializing to port " + serverPort);

    try
    {
      ssock = new ServerSocket (serverPort);

      while (true)
      {
        //System.out.println ("Serv: Waiting for a connection...");
        sock = ssock.accept ();
        //System.out.println ("Serv: Received a connection");
        //System.out.println(ssock.getLocalPort());
        //System.out.println(sock.getLocalPort());
        //System.out.println(sock.getPort());

        mCache.createNewInstance(sock);
        //servx = new EncounterInterface (mCache, sock);

        //thServx = new Thread (servx);
        //thServx.start ();

      }
    }
    catch (Exception e)
    {
      System.err.println ("Serv: Exception in main loop: " + e);
    }
  }
}



