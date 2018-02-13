
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package handler;

import java.util.*;

public interface AreaListener extends EventListener
{

  public void locationClear(String locationId);

  public void significantAction(AreaEvent e);
} 