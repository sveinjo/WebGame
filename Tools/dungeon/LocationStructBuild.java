package dungeon;

import tools.*;
import java.util.*;

public class LocationStructBuild extends LocationStruct
{
  private final int dir_NW = 8;
  private final int dir_N = 7;
  private final int dir_NE = 6;
  private final int dir_W = 5;
  private final int dir_E = 4;
  private final int dir_SW = 3;
  private final int dir_S = 2;
  private final int dir_SE = 1;

  public LocationStructBuild (int iSizeX, int iSizeY)
  {
    super(iSizeX, iSizeY);
  }

  public void setMap(String mapString)
  {
    System.out.println("Attempting to initialize map");
    StringTokenizer st = new StringTokenizer(mapString);

    // Get dimensions
    sizeX = Integer.parseInt(st.nextToken());
    sizeY = Integer.parseInt(st.nextToken());
    cellTable = new LocationCell[sizeX][sizeY];
    initializeLocation();

    String token = st.nextToken();
    //System.out.println(token.substring(0,1));
    int n, m;
    int counter = 0;
    //char temp;
    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {
        cellTable[n][m].structure = Integer.parseInt(token.substring(counter, counter+1));
        if (cellPoll(n, m) == this.sWall)
          cellMark(n, m);
        //System.out.println(token.substring(counter, counter+1));
        counter++;
      }
    }
  }

  protected void unMarkLocation()
  {
    int n, m;
    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {
        //g.drawRect(n*cellSize, m*cellSize, cellSize, cellSize);

        if (cellTable[n][m].structure <= this.sBorderValue)
        {
          cellTable[n][m].structure = this.sSpace;
          cellTable[n][m].checked = false;
        }
      }
    }
  }



  public String validateMap()
  {
    this.unMarkLocation();
    String temp = "";

    // Specify map-dimensions
    temp += sizeX + seperator + sizeY + seperator;

    // generate map
    int n, m;

    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {

        if (cellTable[n][m].structure <= this.sBorderValue)
        {
          if (cellTable[n][m].checked == false)
          {
            findBorders(n,m);
          }
        }
      }
    }
    return(temp);
  }

  private boolean checkIfRoom(int posX, int posY)
  {
    int iBorder = 3;
    boolean returnValue = false;
        // Sør-øst                                                                            Nord-øst                                                                             Nord-vest                                                                            Sør-vest
    if ((checkCell(posX, posY+1, iBorder) && checkCell(posX+1, posY+1, iBorder) && checkCell(posX+1, posY, iBorder)) || (checkCell(posX+1, posY-1, iBorder) && checkCell(posX+1, posY, iBorder) && checkCell(posX, posY-1, iBorder)) || (checkCell(posX, posY-1, iBorder) && checkCell(posX-1, posY-1, iBorder) && checkCell(posX-1, posY, iBorder)) || (checkCell(posX, posY+1, iBorder) && checkCell(posX-1, posY+1, iBorder) && checkCell(posX-1, posY, iBorder)))
    {
      returnValue = true;
    }
    return (returnValue);
  }

  private void findBorders(int posX, int posY)
  {
    int iBorder = 3;
    // If a room
    if (checkIfRoom(posX, posY))
    {
      //this.markCell(posX, posY, this.sFloor);
    }

    // If a Horizontal Corridor
    else if ((!checkCell(posX, posY+1, iBorder) && (!checkCell(posX, posY-1, iBorder))) || (checkCell(posX, posY+1, iBorder) && !checkCell(posX, posY-1, iBorder)) || (!checkCell(posX, posY+1, iBorder) && checkCell(posX, posY-1, iBorder)))
    {
      // Start of horizontal corridor
      if (checkIfRoom(posX-1, posY) && (checkCell(posX+1, posY, iBorder)))
      {
        cellMark(posX, posY, this.sBorder);
      }
      // horizontal T-cross up
      else if (!checkCell(posX, posY+1, iBorder) && checkCell(posX, posY-1, iBorder) && (checkCell(posX-1, posY, iBorder) || checkCell(posX+1, posY, iBorder)))
      {
        cellMark(posX, posY, this.sBorder);
      }
      // horizontal T-cross down
      else if (checkCell(posX, posY+1, iBorder) && !checkCell(posX, posY-1, iBorder) && (checkCell(posX-1, posY, iBorder) || checkCell(posX+1, posY, iBorder)))
      {
        cellMark(posX, posY, this.sBorder);
      }
      // End of horizontal corridor
      else if (checkIfRoom(posX+1, posY) && checkCell(posX-1, posY, iBorder))
      {

          cellMark(posX, posY, this.sBorder);
      }
    }
    // If a Vertical Corridor
    else if ((!checkCell(posX+1, posY, iBorder) && (!checkCell(posX-1, posY, iBorder))) || (checkCell(posX+1, posY, iBorder) && !checkCell(posX-1, posY, iBorder)) || (!checkCell(posX+1, posY, iBorder) && checkCell(posX-1, posY, iBorder)))
    {
      // Start of vertical corridor
      if (checkIfRoom(posX, posY-1))
      {
        cellMark(posX, posY, this.sBorder);
      }
      // vertical T-cross right
      else if (!checkCell(posX-1, posY, iBorder) && checkCell(posX+1, posY, iBorder) && (checkCell(posX, posY-1, iBorder) || checkCell(posX, posY+1, iBorder)))
      {
        cellMark(posX, posY, this.sBorder);
      }
      // vertical T-cross left
      else if (checkCell(posX-1, posY, iBorder) && !checkCell(posX+1, posY, iBorder) && (checkCell(posX, posY-1, iBorder) || checkCell(posX, posY+1, iBorder)))
      {
        cellMark(posX, posY, this.sBorder);
      }
      // End of vertical corridor
      else if (checkIfRoom(posX, posY+1))
      {
        cellMark(posX, posY, this.sBorder);
      }
    }
  }

  

  /**
  * Returns true if cell is below borderValue, eg. "blank", and false if not
  */
  private boolean checkCell(int posX, int posY, int borderValue)
  {
    boolean returnValue = true;
    if (posX < 0 || posY < 0 || posX >= sizeX || posY >= sizeY )
    {
      return(false);
    }
    else
    {
      //if ((this.cellTable[posX][posY].structure == 0) || (this.cellTable[posX][posY].structure == 2) || (this.cellTable[posX][posY].structure == 3))
      if (this.cellTable[posX][posY].structure < sBorderValue)
        returnValue = true;
      else
        returnValue = false;
    }
    return(returnValue);
  }


  public String getMap()
  {
    String temp = "";
    this.validateMap();

    // Specify map-dimensions
    temp += sizeX + seperator + sizeY + seperator;

    // generate map
    int n, m;                                           
    for (m=0; m < sizeY; m++)
    {

      for (n=0; n < sizeX; n++)
      {
        temp += cellTable[n][m].structure;
        /*if (cellTable[n][m].structure == 1)
          temp += "1";
        else
          temp += "0";*/
      }
    }
    return(temp);
  }

  


  

  private boolean dissectPoint(String pointString, int direction)
  {
    boolean returnValue = true;
    String tempString = "";

    if (direction == this.dir_NW)
    {
      tempString = pointString.substring(0,1);
    }
    else if (direction == this.dir_N)
    {
      tempString = pointString.substring(1,2);
    }
    else if (direction == this.dir_NE)
    {
      tempString = pointString.substring(2,3);
    }
    else if (direction == this.dir_W)
    {
      tempString = pointString.substring(3,4);
    }
    else if (direction == this.dir_E)
    {
      tempString = pointString.substring(4,5);
    }
    else if (direction == this.dir_SW)
    {
      tempString = pointString.substring(5,6);
    }
    else if (direction == this.dir_S)
    {
      tempString = pointString.substring(6,7);
    }
    else if (direction == this.dir_SE)
    {
      tempString = pointString.substring(7,8);
    }

    if (tempString.equals("1"))
      returnValue = false;

    return(returnValue);
  }


  /*private String evaluatePoint(int posX, int posY)
  {
    int iBorder = 2;
    String returnString = "";
    if (this.checkCell(posX-1, posY-1, iBorder))
      returnString += "0";
    else
      returnString += "1";
    if (this.checkCell(posX, posY-1, iBorder))
      returnString += "0";
    else
      returnString += "1";
    if (this.checkCell(posX+1, posY-1, iBorder))
      returnString += "0";
    else
      returnString += "1";
    if (this.checkCell(posX-1, posY, iBorder))
      returnString += "0";
    else
      returnString += "1";
    if (this.checkCell(posX+1, posY, iBorder))
      returnString += "0";
    else
      returnString += "1";
    if (this.checkCell(posX-1, posY+1, iBorder))
      returnString += "0";
    else
      returnString += "1";
    if (this.checkCell(posX, posY+1, iBorder))
      returnString += "0";
    else
      returnString += "1";
    if (this.checkCell(posX+1, posY+1, iBorder))
      returnString += "0";
    else
      returnString += "1";

    System.out.println(returnString);
    return(returnString);
  }*/
}