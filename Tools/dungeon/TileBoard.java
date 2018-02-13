package dungeon;

import java.awt.*;
import java.awt.event.*;

import tools.*;

public class TileBoard extends MapPanel
{
  //LocationStructBuild location;


  int object = 0;

  private Point lastPointDrawn = null;



  public void setObject(int objectId)
  {
    object = objectId;
  }

  public TileBoard(LocationStructBuild locationStruct)
  {
    //location = new LocationStruct(sizeX, sizeY, cellSize);
    location = locationStruct;
    cellSize = 25;
    

    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {

    //this.setBackground(Color.red);
    this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

      public void mouseDragged(MouseEvent e) {
        this_mouseDragged(e);
      }
    });
    this.addMouseListener(new java.awt.event.MouseAdapter() {

      

      public void mousePressed(MouseEvent e) {
        this_mousePressed(e);
      }
    });
  }


  public void paint(Graphics g)
  {
    int posX, posY;

    for (posY=0; posY < location.sizeY; posY++)
    {

      for (posX=0; posX < location.sizeX; posX++)
      {
        if (location.cellTable[posX][posY].structure == location.sWall)
        {
          g.setColor(Color.darkGray);
          g.fillRect(posX*cellSize, posY*cellSize, cellSize, cellSize);
        }
        else if (location.cellTable[posX][posY].structure == location.sBorder)
        {
          g.setColor(Color.blue);
          g.fillRect(posX*cellSize, posY*cellSize, cellSize, cellSize);
        }
        else if (location.cellTable[posX][posY].structure == location.sFloor)
        {
          g.setColor(Color.red);
          g.fillRect(posX*cellSize, posY*cellSize, cellSize, cellSize);
        }
        else if (location.cellTable[posX][posY].structure == 4)
        {
          g.setColor(Color.green);
          g.fillRect(posX*cellSize, posY*cellSize, cellSize, cellSize);
        }
        g.setColor(Color.black);
        g.drawRect(posX*cellSize, posY*cellSize, cellSize, cellSize);

      }
    }
  }

  void this_mousePressed(MouseEvent e)
  {
    Point temp = location.calculateZone(e.getX(), e.getY(), this.cellSize);
    if (object == -1)
    {
      System.out.println("Reading: " + temp.x + ", " + temp.y + ": " + location.cellTable[temp.x][temp.y].structure + ", " + location.cellTable[temp.x][temp.y].checked );
    }
    else if (object == -2)
    {
      //System.out.println("Defining room: ");
      location.defineRoom(temp.x, temp.y);
      this.repaint();

    }
    else
    {
      drawStructure(temp.x, temp.y);
      lastPointDrawn = temp;
    }
    /*
    Point temp = location.calculateZone(e.getX(), e.getY());

    System.out.println(temp);
    location.room[temp.x][temp.y].structure = object;
    repaint(temp.x*cellSize, temp.y*cellSize, cellSize, cellSize);
    */
  }

  void this_mouseDragged(MouseEvent e)
  {
    Point temp = location.calculateZone(e.getX(), e.getY(), cellSize);
    if (!lastPointDrawn.equals(temp))
    {
      drawStructure(temp.x, temp.y);
      lastPointDrawn = temp;
      System.out.println("---");
    }
    //drawStructure(e.getX(), e.getY());
  }

  public void drawStructure(int posX, int posY)
  {
    //Point temp = location.calculateZone(posX, posY);

    //System.out.println(location.room[posX][posY].structure);
    location.cellTable[posX][posY].structure = object;
    repaint(posX*cellSize, posY*cellSize, cellSize, cellSize);
  }
}