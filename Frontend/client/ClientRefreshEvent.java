
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package client;

import java.util.*;

public class ClientRefreshEvent extends EventObject
{
  public String type;
  public String parameters;

  public ClientRefreshEvent(Object source, String refreshType, String refreshParameters)
  {
    super(source);
    type = refreshType;
    parameters = refreshParameters;

  }
} 