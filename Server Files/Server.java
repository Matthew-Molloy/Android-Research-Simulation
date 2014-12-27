import java.io.IOException;
import java.io.PrintStream;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {

	Socket csocket;
	static int portNum = 9586;
	static volatile int[][] processedData;
	static volatile int row = -1;
	static int numPlayers = 0;
    static volatile int check = 0;
    

   Server(Socket csocket) {
      this.csocket = csocket;
   }

   public static void main(String args[]) 
   throws Exception {
      ServerSocket ssock = new ServerSocket(portNum);
      Scanner user_input = new Scanner(System.in);
      System.out.println("How many clients?");
      numPlayers = Integer.parseInt(user_input.nextLine());
      processedData = new int[numPlayers][6];
      
      System.out.println("Server active");
      while (true) {
         Socket sock = ssock.accept();
         System.out.println("Client Connected");
         new Thread(new Server(sock)).start();
      }
   }

   public void run() {
      try {
    	  BufferedWriter dataout =  new BufferedWriter(new OutputStreamWriter(csocket.getOutputStream()));
    	  BufferedReader datain = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
          String input = "";
          String delims = "[ ]+";
          String[] inputNums = new String[10];
          int secondsLeft;
          row++;
          String r = Integer.toString(row);
	        dataout.write(r, 0, r.length());
	        dataout.newLine();
	        dataout.flush();
          
          while( !(input.equals( "exit" )) ) {
              input = datain.readLine();
	          inputNums = input.split(delims);
	          process(inputNums);
	          check++;
	          
	          while( check != numPlayers ) {
	        	  // busy wait
	          }
	          
	          r = Integer.toString(calculateStatus(inputNums));
	          input = "";
	          dataout.write(r, 0, r.length());
	          dataout.newLine();
	          dataout.flush();
	          resetCheck();
         }
         csocket.close();
      } catch (IOException e) {
         System.out.println(e);
      }
   }

   public void process(String[] inputNums) {
	   // inputNums[0] is player id
	   // inputNums[1] is resources spent
	   // inputNums[2] is resources pre-total
	   // inputNums[3] is resources post-total
	   // inputNums[4] is information spent
	   // inputNums[5] is information pre-total
	   // inputNums[6] is information post-total
	   // inputNums[7] is rowIndex
	   System.out.println("----------------------------------------------------------------");
	   System.out.println("Player ID: " + inputNums[0] + "\n" + "Invested: " + inputNums[1] + " Old Total: " + inputNums[2] +
				" New Total: " + inputNums[3] + "\n" + "Shared: " + inputNums[4] + " Old Total: "
				+ inputNums[5] + " New Total: " + inputNums[6] + " Row Index: " + inputNums[7] );
	   
	   // store into 2d array
	   int rowIndex = Integer.parseInt(inputNums[7]);
	   processedData[rowIndex][0] = Integer.parseInt(inputNums[0]);
	   processedData[rowIndex][1] = Integer.parseInt(inputNums[1]);
	   processedData[rowIndex][2] = Integer.parseInt(inputNums[4]);
   }

   public int calculateStatus(String[] inputNums) {
	   int S_server = 0;
	   int totalResourcesSpent = 0;
	   int totalInfoShared = 0;
	   int rowIndex = Integer.parseInt(inputNums[7]);
	   
	   // add up resources and info
	   for( int i = 0; i < row + 1; i++ ) {
		   totalResourcesSpent = totalResourcesSpent + processedData[i][1];
		   totalInfoShared = totalInfoShared + processedData[i][2];
	   }
	   
	   // System.out.println("------------------------Round Result----------------------------");
	   System.out.println("Total Resources Spent: " + totalResourcesSpent);
	   System.out.println("Total Info Shared: " + totalInfoShared);
	   
	   // calculate calculation
	   S_server = totalInfoShared - processedData[rowIndex][2];
	   System.out.println("Original shared & row: " + rowIndex + " " + processedData[rowIndex][2] );
	   System.out.println("S_server " + S_server);

	   // store calculation
	   processedData[rowIndex][3] = S_server;
	   return S_server;
   }
   
   public synchronized void resetCheck() {
	   check = 0;
   }
}






