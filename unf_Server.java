
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Server {

    ServerSocket ServerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    int menuItem;

    Server() {
    }

    void run() {

        String cmd = "";

        try {

            ServerSocket = new ServerSocket(20904, 10);
            System.out.println("Waiting for connection");

            connection = ServerSocket.accept();
            System.out.println("Connected: " + connection.getInetAddress().getHostName());

            in = new ObjectInputStream(connection.getInputStream());
            menuItem = (int) in.readObject();
            System.out.println("Read from client: " + menuItem);

            switch (menuItem) {
                case 1:
                    System.out.print("\nDate: ");
                    cmd = "date";
                    break;

                case 2:
                    System.out.print("\nuptime: ");
                    cmd = "uptime";
                    break;
                case 3:
                    System.out.print("\nfree memory: \n");
                    cmd = "free";
                    break;
                case 4:
                    cmd = "netstat";
                    break;
                case 5:
                    cmd = "who";
                    break;
                case 6:
                    cmd = "ps";
                    break;
                case 7:
                    //  p = r.exec("ls");
                    break;
                //  break;

                default:
                    System.out.println("Invalid selection.");
                    break;
            } // end switch menu

            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = pb.start();
            // process input stream
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            ////////////////////////////
            // run the local command "date"
            //p = r.exec("date");
            System.out.println("running local command: " + cmd);
            ///
            ///////p = r.exec("c:/utils/mydate.bat");
            ///
            //is = new BufferedReader(new InputStreamReader(p.getInputStream()));
            boolean moreLines = true;
            //while ((line = br.readLine()) != null) {
            while (moreLines) {
                line = br.readLine();
                if (line != null) {
                    System.out.println("server>" + line);
                    //sendMessage(line);
                    try {
                        out.writeObject(line);
                        out.flush();
                    } catch (IOException ioException) {
                        //ioException.printStackTrace();
                    } // end try/catch
                    System.out.println("Sending: " + line);
                } else {
                    System.out.println("\nLINE is NULL\n");
                    moreLines = false;
                } // end if/else
                System.out.println(line);
            //out = new ObjectOutputStream(connection.getOutputStream());
                //out.flush();

                p.waitFor();  // wait for process to complete
          //      System.out.println("Closing Connection....");
         //       connection.close();  // close this connection
                //} catch (IOException ioException) {
            } // end WHILE loop

        } catch (IOException ioException) {
            /*    System.out.println("Closing Connection: "+ ioException);
             try {
             connection.close();  // close this connection
             } catch (IOException ex) {
             //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
             }
             */
        } catch (Exception allE) {
            allE.printStackTrace();
        }
        System.out.println("End of RUN");
    }
    // } //end class Server

    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

     public static void main(String args[])
     {
     Server server = new Server();
//     while(true){
     server.run();
 //    }
     }
///////////////}
} //end class Server

