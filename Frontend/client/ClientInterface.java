package client;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import tools.*;
import javax.swing.*;
import netscape.javascript.*;
import javax.swing.border.*;
import java.util.*;
import java.beans.*;
import javax.swing.event.*;

public class ClientInterface extends Applet implements ChatListener, ClientRefreshListener {

  public String strTest = "Heisann";
  //Parameters
  String strLocationId;
  String strPlayerId;
  String strHost;
  String strPort;

  Thread controllerThread;
  SocketVariables cVar = new SocketVariables();
  JSObject win = null;

  boolean isStandalone = false;
  JTextArea chatWindow = new JTextArea();
  //JEditorPane chatWindow = new JEditorPane();

  ClientController controller = null;


  //Interface variables

  Panel panelNet = new Panel();
  Button bEnter = new Button();
  Button bLeave = new Button();
  Button bEndTurn = new Button();
  BorderLayout borderLayoutMain = new BorderLayout();
  Label labelNet = new Label();
  Panel panelContainTop = new Panel();
  TextField textFieldChat = new TextField();
  Panel panelChatWindow = new Panel();
  BorderLayout borderLayout1 = new BorderLayout();
  //Panel mapPanel = null;
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  Button bScript = new Button();
  TitledBorder titledBorder1;
  Border border1;


  // Interface Code

  //Component initialization
  private void jbInit() throws Exception {

    //mapPanel = new MapPanel(controller.model);

    titledBorder1 = new TitledBorder("");
    border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.lightGray,Color.black,Color.black);
    this.setSize(new Dimension(400, 272));
    
    this.setLayout(borderLayoutMain);


    bEnter.setLabel("Enter");
    bEnter.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        bEnter_actionPerformed(e);
      }
    });
    bLeave.setLabel("Leave");
    bLeave.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        bLeave_actionPerformed(e);
      }
    });
    bEndTurn.setLabel("End Turn");
    bEndTurn.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        bEndTurn_actionPerformed(e);
      }
    });
    labelNet.setText("Net Code");
    //textFieldChat.setText("textField1");
    
    
    textFieldChat.addKeyListener(new java.awt.event.KeyAdapter()
    {


      public void keyPressed(KeyEvent e)
      {
        textFieldChat_keyPressed(e);
      }
    });
    //chatWindow.setLineWrap(true);
    chatWindow.setLineWrap(true);
    chatWindow.setRows(6);
    chatWindow.setDoubleBuffered(true);
    chatWindow.setBackground(Color.black);
    chatWindow.setBorder(null);
    chatWindow.setForeground(Color.white);
    chatWindow.setEditable(false);
    //chatWindow.setEditable(false);
    chatWindow.setFont(new java.awt.Font("SansSerif", 0, 12));


    //chatWindow.setPreferredSize(new Dimension(396, 100));
    panelChatWindow.setLayout(borderLayout1);
    panelContainTop.setLayout(borderLayout2);
    jScrollPane1.setAutoscrolls(true);
    jScrollPane1.setBorder(null);
    //jScrollPane1.setBorder(null);
    jScrollPane1.setDoubleBuffered(true);
    bScript.setLabel("Script");
    bScript.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        //bScript_actionPerformed(e);
      }
    });

    this.add(panelContainTop, BorderLayout.SOUTH);
    //panelContainTop.add(mapPanel, BorderLayout.CENTER);
    panelContainTop.add(panelNet, BorderLayout.NORTH);
    panelNet.add(labelNet, null);
    panelNet.add(bEnter, null);
    panelNet.add(bLeave, null);
    panelNet.add(bEndTurn, null);
    panelNet.add(bScript, null);
    this.add(panelChatWindow, BorderLayout.NORTH);
    panelChatWindow.add(textFieldChat, BorderLayout.SOUTH);
    panelChatWindow.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(chatWindow, null);
  }


  // static initializer for setting look & feel
  static {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {}
  }

  void bEnter_actionPerformed(ActionEvent e)
  {
    //actionEnterArea();
  }

  void bLeave_actionPerformed(ActionEvent e)
  {
    //actionLeaveArea();
  }

  void bEndTurn_actionPerformed(ActionEvent e)
  {
    //actionEndTurn();
  }

  
  /*****************
  * Applet Methods *
  *****************/
  //Construct the applet
  public ClientInterface()
  {
  }

  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Initialize the applet
  public void init()
  {
    try { strLocationId = this.getParameter("locationId", ""); } catch (Exception e) { e.printStackTrace(); }
    try { strPlayerId = this.getParameter("playerId", ""); } catch (Exception e) { e.printStackTrace(); }
    try { strHost = this.getParameter("host", ""); } catch (Exception e) { e.printStackTrace(); }
    try { strPort = this.getParameter("port", ""); } catch (Exception e) { e.printStackTrace(); }
    try
    {
      controller = new ClientController(this, this, strHost, Integer.parseInt(strPort), strPlayerId, strLocationId);
      controllerThread = new Thread(controller);
      controllerThread.start();
      jbInit();

    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
  }

  //Get Applet information
  public String getAppletInfo()
  {
    return "Applet Information";
  }

  //Get parameter info
  public String[][] getParameterInfo()
  {
    return null;
  }

  //Cleanup on exit
  public void stop()
  {
    System.out.println("stopping excecution");
    controller.breakConnection();
  }

  public void chatConnectionVerified()
  {
    JSObject win = JSObject.getWindow(this);
    win.call("refreshClient", null);

    //this.actionGetCombatants();
    this.actionSendCombatReady();
  }

  public void chatLineRecieved(ChatEvent e)
  {
    if (e.text.equals("ninja"))
      chatWindow.setForeground(Color.red);

    //chatWindow.setText(e.text + "\n" + chatWindow.getText());

    chatWindow.append("\n" + e.text);

    //chatWindow.selectAll();
    int lastPos = chatWindow.getDocument().getLength();
    chatWindow.select(lastPos, lastPos);

    System.out.println("Chatline recieved: " + e.text);
    
  }

  void textFieldChat_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == 10) // Enter-key
    {
      actionSendChatMessage();
    }
  }

  /***************
  * User methods *
  ****************/

  public String getEntity(String posX, String posY)
  {
    System.out.println("getting entity");
    Entity e = null;
    String rString = null;
    if (posX != null && posY != null)
    {
      e = controller.getEntity(Integer.parseInt(posX), Integer.parseInt(posY));
      rString = e.id + " " + e.owner + " " + e.curHP + " " + e.maxHP + " " + e.armorBase + " " +
      e.statusBits + " " +
      e.description;
    }
    else
    {
      System.out.println("not gonna happen");
    }

    return(rString);
    //return("yuhuu");
  }

  public String getInventoryItem(String itemId)
  {
    String rValue = "";
    Item i = controller.getInventoryItem(itemId);
    rValue += i.name + cVar.separatorParagraph;
    rValue += i.type + cVar.separatorParagraph;
    rValue += i.priLocation + cVar.separatorParagraph;
    rValue += i.secLocation + cVar.separatorParagraph;
    rValue += i.initiative + cVar.separatorParagraph;
    rValue += i.attack + cVar.separatorParagraph;
    rValue += i.damage + cVar.separatorParagraph;
    rValue += i.defence + cVar.separatorParagraph;
    rValue += i.protection;
    return(rValue);

  }

  public void actionGetInventory(String entityId)
  {
    controller.actionGetInventory(entityId);
    //Entity e = controller.getEntity(entityId);
    //String rValue = "" + e.equipment[1].name;
    //return("rValue");
  }

  public String test(String entityId)
  {
    //labelNet.setText("Testing communication");
    controller.getEntity(entityId);
    return("methodValue");
  }

  public String actionSendChatMessage()
  {
    controller.sendChatMessage(textFieldChat.getText());
    //textFieldChat.setText("");
    return("---");
  }

  /*public void actionEndTurn()
  {
    System.out.println(controller.endTurn());
  }*/

  /*public void actionEnterArea()
  {
    controller.initialize();

  }*/

  public String actionGetCombatants()
  {
    System.out.println("getting combatants!");
    String strReturn = controller.getCombatants();

    return(strReturn);
  }

  /*public String actionGetInventory(String entityId)
  {
    return("Digert Sverd");
  }*/

  public void actionSendCombatReady()
  {
    System.out.println("ready for combat");
    controller.sendCombatReady();
    //controllerThread.start();
  }

  /*public void actionLeaveArea()
  {
    controller.breakConnection();
  }*/

  /*public void actionMove(int posX, int posY, int newPosX, int newPosY)
  {
    System.out.println("Move");
    controller.actionMove(posX, posY, newPosX, newPosY);
  }*/

  public void actionMove(int direction)
  {
    System.out.println("Move direction");
    controller.actionMove(direction);
  }

  public void actionAttack(String defenderId)
  {
    //System.out.println(posX + ", " + posY + " is attacking: " + targetX + ", " + targetY);
    controller.actionAttack(defenderId);
  }

  public void actionDefend()
  {
    controller.actionDefend();
  }

  public void actionSwitch(String targetId)
  {
    controller.actionSwitch(targetId);
  }

  /*void bScript_actionPerformed(ActionEvent e)
  {
    JSObject win = JSObject.getWindow(this);
    //JSObject doc = (JSObject) win.getMember("document");
    //JSObject loc = (JSObject) doc.getMember("location");

    //String s = (String) loc.getMember("href");  // document.location.href
    //win.call("f", null);		  	     // Call f() in HTML page
    //win.call("createMyLayer1", null);
    //win.call("enterLocation", null);
    win.call("refreshClient", null);
  }*/

  private void decodeEvent(ClientRefreshEvent e)
  { 
    if (win == null)
      win = JSObject.getWindow(this);
      
    StringTokenizer st;


    if ((e.type).equals(cVar.requestAction))
      {

        System.out.println(controller.playerId + ", Requesting action " + e.parameters);
        st = new StringTokenizer(e.parameters);

        String[] aVar = new String[3];
        aVar[0] = st.nextToken();
        aVar[1] = st.nextToken();
        aVar[2] = st.nextToken();
        win.call("requestAction", aVar);
        //win.call("refreshClient", null);
      }
      else if ((e.type).equals(cVar.actionKill))
      {
        System.out.println("---***" + e.parameters);
        st = new StringTokenizer(e.parameters);
        String[] aVar = new String[2];
        aVar[0] = st.nextToken();
        aVar[1] = st.nextToken();
        win.call("removeEntity", aVar);
      }
      else if ((e.type).equals(cVar.getLocationSize))
      {
        st = new StringTokenizer(e.parameters);
        String[] aVar = new String[2];
        aVar[0] = st.nextToken();
        aVar[1] = st.nextToken();
        win.call("createPlayfield", aVar);
      }
      else if ((e.type).equals(cVar.getLocationData))
      {
        //st = new StringTokenizer(e.parameters);
        String[] aVar = new String[1];
        aVar[0] = e.parameters;

        win.call("defineRoom", aVar);
      }
      else if ((e.type).equals(cVar.getEntityInventory))
      {
        System.out.println("Test method");
        
        st = new StringTokenizer(e.parameters, cVar.separatorEntity);
        String[] aVar = new String[7];

        aVar[0] = st.nextToken();
        aVar[1] = st.nextToken();
        /*aVar[2] = st.nextToken();
        aVar[3] = st.nextToken();
        aVar[4] = st.nextToken();
        aVar[5] = st.nextToken();
        aVar[6] = st.nextToken();
        */
        win.call("displayInventory", aVar);

      }
      else
      {
        System.out.println("State Changed");
        //JSObject win = JSObject.getWindow(this);
        win.call("refreshClient", null);
      }
  }

  public void stateChanged(ClientRefreshEvent e)
  {
    System.out.println("state changed: " + e.type);
    String sTemp = e.type;
    try
    {
      decodeEvent(e);
    }
    catch (Exception exp)
    {
      int i = 999999999;
      int n = 2100000000;

      System.out.println("--------------------noe gikk galt----------------------------:" + sTemp + " " + win);
      //exp.printStackTrace();
      decodeEvent(e);
    }
  }

  public int checkAction(String strId)
  {
    final int aNone = 0;
    final int aFight = 1;
    final int aDefend = 2;
    final int aSwitch = 3;

    int rValue = aNone;

    if (controller.isTurnActionLegal())
    {
    //System.out.println("turn: " + strId);
    try
    {
      Entity eTemp = controller.getEntity(strId);


      if (!(eTemp.owner).equals(strPlayerId))
      {
      //System.out.println(eTemp.owner);
        if(controller.checkRange(strId))
          rValue = aFight;
      }
      else
      {
        // Bring up team/self related options
        if (controller.isTurnEntity(strId))
        {
          // self
          rValue = aDefend;
        }
        else
        {
          // team
          rValue = 10 + controller.getSwitchSide(strId);
          //rValue = aSwitch;
        }
      }
    }
    catch(Exception exp)
    {
      exp.printStackTrace();
    }
    }
    else
    {
      rValue = 0;
    }
    //return(rValue);
    return(rValue);
  }


}
