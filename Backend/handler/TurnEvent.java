

//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package handler;

import java.util.*;
import tools.*;

public class TurnEvent extends EventObject
{
  public Entity entity;
  public String action;
  public Object parameter;

  public TurnEvent(Object source, String strAction, Entity strEntity, Object objParameter)
  {
    super(source);
    //entityId = strEntityId;
    action = strAction;
    entity = strEntity;
    parameter = objParameter;
  }
} 