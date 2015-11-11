import java.io.*;
import java.net.*;


public class ServerMaster {

    private static ServerSocket serverSock;
    private static Socket clientSock = null;

    public static void main(String[] args) throws IOException {

        try {
            serverSock = new ServerSocket(5555);
            System.out.println("Server has been started...!!!");
        } catch (Exception e) {
            System.err.println("Port is in use already!!");
            System.exit(1);
        }

        while (true) {
            try {
                clientSock = serverSock.accept();
                System.out.println("Conection Accepted : " + clientSock);

                Thread t = new Thread(new ClientConnection(clientSock));

                t.start();

            } catch (Exception e) {
                System.err.println("Conection Error!!!");
            }
        }
    }
}

