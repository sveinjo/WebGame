
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package client;

import java.util.*;

public interface ChatListener extends EventListener
{

  public void chatLineRecieved(ChatEvent e);

  public void chatConnectionVerified();
} 