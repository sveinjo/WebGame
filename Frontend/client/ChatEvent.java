
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package client;

import java.util.*;

public class ChatEvent extends EventObject
{
  public String text = null;

  public ChatEvent(Object source, String chatLine)
  {
    super(source);
    text = chatLine;
  }
} 