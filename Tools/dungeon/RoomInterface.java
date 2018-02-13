package dungeon;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
//import com.borland.jbcl.layout.*;
import tools.*;

public class RoomInterface extends JFrame {

  LocationStructBuild locationStruct = null;
  TileBoard tileBoard = null;

  JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  JMenuItem menuFileExit = new JMenuItem();
  JMenu menuHelp = new JMenu();
  JMenuItem menuHelpAbout = new JMenuItem();
  JToolBar toolBar = new JToolBar();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;
  JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  Border border1;
  TitledBorder titledBorder1;
  JSplitPane jSplitPane1 = new JSplitPane();
  Panel panel1 = new Panel();
  Choice choiceStructure = new Choice();
  TextField scriptField = new TextField();

  //Construct the frame
  public RoomInterface() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);

    locationStruct = new LocationStructBuild(1,127);
    tileBoard = new TileBoard(locationStruct);

    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  public void fillInChoices()
  {
    choiceStructure.addItem("Space");
    choiceStructure.addItem("Wall");
    choiceStructure.addItem("Boundary");
    choiceStructure.addItem("Read");
    choiceStructure.addItem("Define");    
  }

  //Component initialization
  private void jbInit() throws Exception  {
    image1 = new ImageIcon(dungeon.RoomInterface.class.getResource("openFile.gif"));
    image2 = new ImageIcon(dungeon.RoomInterface.class.getResource("closeFile.gif"));
    image3 = new ImageIcon(dungeon.RoomInterface.class.getResource("help.gif"));
    border1 = BorderFactory.createLineBorder(Color.white,4);
    titledBorder1 = new TitledBorder("");
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(400, 300));
    this.setTitle("Room Generator");
    statusBar.setText(" ");
    menuFile.setText("File");
    menuFileExit.setText("Exit");
    menuFileExit.addActionListener(new ActionListener()  {

      public void actionPerformed(ActionEvent e) {
        fileExit_actionPerformed(e);
      }
    });
    menuHelp.setText("Help");
    menuHelpAbout.setText("About");
    menuHelpAbout.addActionListener(new ActionListener()  {

      public void actionPerformed(ActionEvent e) {
        helpAbout_actionPerformed(e);
      }
    });
    jButton1.setIcon(image1);
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton1.setToolTipText("Open File");
    jButton2.setIcon(image2);
    jButton2.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton2.setToolTipText("Close File");
    jButton3.setIcon(image3);
    jButton3.setToolTipText("Help");
    //testTile.setLabel("button1");
    jSplitPane1.setLeftComponent(tileBoard);
    choiceStructure.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        choiceStructure_itemStateChanged(e);
      }
    });
    scriptField.setText("19 19 99999999999999999999000999900099999999900000000009999999990009999000999999999909999900000009999" +
    "99000099000999099999909999900099909999990000000009990999999990999000999099999999099990999909999999909" +
    "99909900000999999090000999909999990999999099990999999099990000099099999909999000009909999900000000000" +
    "0009999999999900000999999999999990000099999999999999999999999999");
    toolBar.add(jButton1);
    toolBar.add(jButton2);
    toolBar.add(jButton3);
    menuFile.add(menuFileExit);
    menuHelp.add(menuHelpAbout);
    menuBar1.add(menuFile);
    menuBar1.add(menuHelp);
    this.setJMenuBar(menuBar1);
    this.getContentPane().add(toolBar, BorderLayout.NORTH);
    this.getContentPane().add(statusBar, BorderLayout.WEST);
    this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(tileBoard, JSplitPane.LEFT);
    jSplitPane1.add(panel1, JSplitPane.RIGHT);
    panel1.add(choiceStructure, null);
    this.getContentPane().add(scriptField, BorderLayout.SOUTH);
    this.fillInChoices();
    jSplitPane1.setDividerLocation(200);
  }

  //File | Exit action performed
  public void fileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  //Help | About action performed
  public void helpAbout_actionPerformed(ActionEvent e) {
    RoomInterface_AboutBox dlg = new RoomInterface_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }

  //Overridden so we can exit on System Close
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
    }
  }

  void choiceStructure_itemStateChanged(ItemEvent e) {

    System.out.println(e.getItem() + " " + e.getID());
    if (e.getItem().equals("Wall"))
      tileBoard.setObject(locationStruct.sWall);
    else if (e.getItem().equals("Boundary"))
      tileBoard.setObject(locationStruct.sBorder);
    else if (e.getItem().equals("Read"))
      tileBoard.setObject(-1);
    else if (e.getItem().equals("Define"))
      tileBoard.setObject(-2);
    else
      tileBoard.setObject(locationStruct.sSpace);

  }

  void jButton2_actionPerformed(ActionEvent e) {
    scriptField.setText(locationStruct.getMap());
    tileBoard.repaint();
    
  }

  void jButton1_actionPerformed(ActionEvent e) {
    locationStruct.setMap(scriptField.getText());
    tileBoard.repaint();
  }

  
}
