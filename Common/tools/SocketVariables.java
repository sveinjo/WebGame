package tools;

public class SocketVariables {
  //sockets
  public final String separator = "|";
  public final String separatorParagraph = ";";
  public final String separatorEntity = "@";
  public final String turnTerminator = "/endturn";
  public final String dataTerminator = "/dataend";

  public final String getLocationSize = "/getsize";
  public final String getLocationData = "/getdata";
  public final String getPlayerInventory = "/getinv";
  public final String getCombatants = "/getcom";
  public final String getEntityInventory = "/getentinv";

  public final String statusUpdate = "/status";

  public final String readyCombat = "/readycom";
  public final String requestAction = "/reqaction";
  //public final String requestRefresh = "/reqrefresh";
  public final String actionMove = "/amove";
  public final String actionDefend = "/adefend";
  public final String actionAttack = "/aattack";
  public final String actionKill = "/akill";
  public final String actionSwitch = "/aswitch";

  //datagram
  public final int chatLength = 512;
  public final String chatRegistration = "/register";
  public final String chatDisconnect = "/disconnect";

  public final String areaTag = "/area";

  public SocketVariables()
  {
  }
}