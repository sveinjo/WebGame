package tools;

import java.io.*;
import java.net.*;


public class SocketManipulator
{
  private BufferedReader inbound = null;
  //private OutputStream outbound = null;
  private DataOutputStream outbound = null;
  protected Socket socket;



  public SocketManipulator()
  {}

  public SocketManipulator(Socket s)
  {
    assignSocket(s);
  }

  public void assignSocket(Socket s)
  {
    socket = s;
    this.initializeConnection();
  }

  protected void initializeConnection()
  {
    try
    {
      // Acquire the input stream
      inbound = new BufferedReader(
      new InputStreamReader(socket.getInputStream()) );
      //outbound = client.getOutputStream();
      outbound = new DataOutputStream(socket.getOutputStream() );


    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }
  }

  protected void performCleanup()
  {
    //System.out.println ("SrvX: Disconnecting: " + socket.getInetAddress () + ":" + socket.getPort ());
      try
      {
        outbound.close();
        inbound.close();
        socket.close();
        //socket = null;
      }
      catch (Exception exp)
      {
        exp.printStackTrace();
      }


      System.out.println("closing socket: " + socket.getPort());
  }

  protected String socketReadLine()
  {
    String returnValue = null;
    try
    {
      returnValue = inbound.readLine();
    }
    catch (Exception exp)
    {
      //exp.printStackTrace();
    }
    return(returnValue);
  }

  synchronized protected void socketWriteLine(String s)
  {

    try
    {
      outbound.writeBytes(s + "\n");
    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }

  }
}