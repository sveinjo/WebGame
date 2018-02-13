package handler;

//import tools.*;

public class LocationModel extends PlayerHandler
{                                  
  private String locationData;

  public LocationModel(String strLocationId)
  {
    super();                                                   
    locationData = getLocationData(locationId);
    this.setMap(locationData);
  }


  public String enterLocation(int posX, int posY)
  {
    // Check if location has been initialized, if not get from dbase
    String str = this.defineRoom(posX, posY);

    // return location data string

    //int iLength = str.length();
    //str = str.substring(0, iLength-1);
    return(str);

  }
} 