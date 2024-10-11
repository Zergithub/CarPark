import java.net.*;
import java.io.*;





public class ActionServer {
  public static void main(String[] args) throws IOException {

	ServerSocket ActionServerSocket = null;
    boolean listening = true;
    String ActionServerName = "ActionServer";
    int ActionServerNumber = 4545;
    
    int SharedVariable = 5;
    //int CheckSpace = 0

    //Create the shared object in the global scope...
    
    SharedActionState ourSharedActionStateObject = new SharedActionState(SharedVariable);
        
    // Make the server socket

    try {
      ActionServerSocket = new ServerSocket(ActionServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + ActionServerName + " specified port.");
      System.exit(-1);
    }
    System.out.println(ActionServerName + " started");

    //Got to do this in the correct order with only four clients!  Can automate this...
    
    while (listening) {
        Socket clientSocket = ActionServerSocket.accept();
        System.out.println("New " + ActionServerName + " thread started.");
        new ActionServerThread(clientSocket, "ActionServerThread1", ourSharedActionStateObject).start();

        clientSocket = ActionServerSocket.accept();
        System.out.println("New " + ActionServerName + " thread started.");
        new ActionServerThread(clientSocket, "ActionServerThread2", ourSharedActionStateObject).start();

        clientSocket = ActionServerSocket.accept();
        System.out.println("New " + ActionServerName + " thread started.");
        new ActionServerThread(clientSocket, "ActionServerThread3", ourSharedActionStateObject).start();

        clientSocket = ActionServerSocket.accept();
        System.out.println("New " + ActionServerName + " thread started.");
        new ActionServerThread(clientSocket, "ActionServerThread4", ourSharedActionStateObject).start();
    }
  }
}
