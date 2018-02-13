
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package handler;

import java.util.*;
import java.awt.Point;

public interface TurnListener extends EventListener
{

  public void broadcastAction(TurnEvent e);

  //public void requestAction(String playerId, String entityId, Point entityPos);
  public void pointcastAction(String playerId, TurnEvent e);

  public void refreshAction();

  public String getPlayerId();
} 