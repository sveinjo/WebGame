package handler;

import tools.*;
import java.net.*;
import java.io.*;
import java.util.*;


class SocketInterface extends tools.SocketManipulator
{

  protected SocketVariables cVar = new SocketVariables();
  protected String playerId;
  //protected ModelCache modelCache = null;
  //protected EncounterController controller;
  protected EncounterModel model;

  public SocketInterface (ModelCache mCache, Socket s)
  {
    super(s);
    //modelCache = mCache;


    try
    {
      initializeConnection();

      // read model parameters
      playerId = this.socketReadLine();
      String locationId = this.socketReadLine();

      // create model
      model = mCache.getModel(playerId, locationId);
      //controller = new EncounterController(model);
      //System.out.println(playerId + " - " + locationId);

      // Send the chat-port
      this.socketWriteLine(String.valueOf(model.getChatPort()));
      
    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }
  }

  synchronized protected void sendData(String dataString)
  {
    dataString = dataString.trim();
    System.out.println(playerId + "--> " + dataString);
    StringBuffer buffer;

    try
    {
      this.socketWriteLine(dataString);
    }
    catch (Exception exp)
    {}
  }

  synchronized protected void sendData(String dataType, String parameters)
  {
    parameters = parameters.trim();
    System.out.println(playerId + "--> " + dataType + ": " + parameters);
    try
    {
      this.socketWriteLine(dataType + "\n" + parameters);
    }
    catch (Exception exp)
    {}
  }







  






}

