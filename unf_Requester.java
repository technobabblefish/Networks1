import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.Scanner;

public class Requester {

  //  InetAddress host = InetAddress.getLocalHost();
    //  Socket socket = new Socket(host.getHostName(), mySocket);
    public static void main(String[] args) {
        System.out.println("Starting client main...\n");
        int menuItem = 1;
        Socket socket;
        int mySocket = 20904; // 31337;

        /// big WHILE to end MENU
        // Menu
        Scanner menuSel = new Scanner(System.in);
        mLoop:
        while (menuItem != 7) {

            System.out.println("\n\n === MENU === \n");
            System.out.println("1. Host current Date and Time ");
            System.out.println("2. Host uptime ");
            System.out.println("3. Host memory use ");
            System.out.println("4. Host Netstat ");
            System.out.println("5. Host current users ");
            System.out.println("6. Host running processes ");
            System.out.println("7. Quit \n");
            System.out.print("\n>>>>>  ");

            menuItem = Character.getNumericValue(menuSel.next().charAt(0));  // char or int?
            System.out.print("\nSelection:  "+menuItem);
            if(menuItem == 7) break mLoop;
        //} // end MENU while

        /// 1-7
        try { /// MAIN TRY
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), mySocket);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(menuItem);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String message;
            try {  // SUB TRY
                //while( (message = (String) ois.readObject()) != null) {
                boolean moreRead = true;
                while (moreRead) {
                    message = (String) ois.readObject();
                    if (message == null) {
                        moreRead = false;
                    } else {
                        System.out.println("\n\n ===== Received from server =====\n " + message +"\n===============================\n\n");
                    }
                    ois.close();
                    oos.close();
                    socket.close();
                } // end while
            } catch (EOFException e) {
                // end of stream
                ois.close();
                oos.close();
                socket.close();
            } // END SUB TRY CATCH
            ois.close();
            oos.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println("unk host: "+e);
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ioe: "+e);
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("class: "+e);
            //e.printStackTrace();
        } /// END MAIN CATCH
        finally {
        //ois.close();
            //oos.close();
            //socket.close();
        } // END MAIN TRY CATCH FINALLY


        } // end MENU while
        System.out.println("\n\nEnd of program run.\n");
    } // end main
} //end class Requester
