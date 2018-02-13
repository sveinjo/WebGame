package tools;

//import dungeon.*;
import java.awt.*;
public class MapPanel extends Panel
{
  protected LocationStruct location = new LocationStruct();
  protected int cellSize = 20;

  public MapPanel()
  {}

  public MapPanel(LocationStruct locationStruct)
  {
    location = locationStruct;
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
        //g.setColor(Color.black);
        //g.drawRect(posX*cellSize, posY*cellSize, cellSize, cellSize);

      }
    }

  }
}