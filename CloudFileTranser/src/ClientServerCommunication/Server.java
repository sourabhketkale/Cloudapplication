package ClientServerCommunication;
import java.io.*;
import java.net.*;

import connectionMongoDB.ConnectionClass;

public class Server implements Runnable {

    private Socket clientSocket;
    private BufferedReader in = null;


    public Server(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        boolean done = false;
        try{
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));

        }
        catch(Exception e){}

        String clientSelection;

        while (!done) {

            try {

                clientSelection = "";

                while ((clientSelection = in.readLine()) != null) {
                    switch (clientSelection) {
                    case "upload":
                        receiveFileFromClient();
                        break;
                    case "download":
                        String outGoingFileName;
                        outGoingFileName = in.readLine();
                        while (outGoingFileName  != null || clientSelection.equals("download")) {
                            sendFileToClient(outGoingFileName);
                        }
                        System.out.println("Please Enter valid filename");
                        break;
                    default:
                        System.out.println("Wrong action...");
                        break;
                    }
                }

            } catch (IOException ex) {
                System.err.println("Erro--" + ex);
            }
        }
    }

    
    public void receiveFileFromClient() {
        try {
            int bytesRead;

            DataInputStream clientData = new DataInputStream(
                    clientSocket.getInputStream());

            String fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(
                    ("received_from_client_" + fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0
                    && (bytesRead = clientData.read(buffer, 0,
                            (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.flush();
            output.close();

            System.out.println("File " + fileName + " received from client.");

            ConnectionClass cc = new ConnectionClass();
            cc.saveFile(fileName);

        } catch (IOException ex) {
        
            System.err.println("Error." + ex);
        }
    }

    public void sendFileToClient(String fileName) {
        try {
        	
            ConnectionClass cc = new ConnectionClass();  
            String file = cc.retrieveFile(fileName);
        	
            File myFile = new File(file);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = clientSocket.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            
           // bis.close();
           // dis.close();
            dos.flush();
           // os.flush();
            

            System.out.println("File " + fileName + " send to client.");
 
        } catch (Exception e) {
            System.err.println("Error! " + e);
        }
    }
}