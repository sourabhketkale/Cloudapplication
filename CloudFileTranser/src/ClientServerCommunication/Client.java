package ClientServerCommunication;
import com.cloudapplication.gui.*;
import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;


public class Client  {

    private static Socket sock;
    private static String fileName;
    private static BufferedReader bufferReader;
    private static PrintStream os;
    
   
    FileInputStream in;
    int i;
    File masterFile;
   
    public static void main(String[] args) throws IOException {

    	/* 
    	 System.out.println(client.filepathgetter());*/
    	
        try {
            sock = new Socket("127.0.0.1", 5555);
            bufferReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            System.err.println("Error - Try again.");
            System.exit(1);
        }
        
        EventQueue.invokeLater(new Runnable() {
 			public void run() {
 				try {
 					Applicationmainpage window = new Applicationmainpage();
 					window.getFrmCloudApplication().setVisible(true);
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 		});

  
        os = new PrintStream(sock.getOutputStream());

        boolean done = false;

        while (!done) {
            try {

                switch (selectAction()) {
                case "upload":
                    os.println("upload");
                    Client client= new Client();
                    os.println(client.filepathgetter());
                    sendFile(client.filepathgetter());
                    break;
                case "download":
                    os.println("download");
                    System.out.print("Please enter the file name you wish to download: ");
                    fileName = bufferReader.readLine();
                    
                    receiveFile(fileName);
                    break;
                case "quit":
                    done = true;
                    os.println("quit");
                    System.out.println("Connection closed");
                    break;
                }
            } catch (Exception e) {
                System.err.println("Wrong action");
            }
        }

        sock.close();
    }


    public static String selectAction() throws IOException {
        System.out.println("");
        System.out.println("upload - Upload File.");
        System.out.println("download - Download File.");
        System.out.println("quit - To Exit.");
        System.out.print("\nSelect one Option: ");

        return bufferReader.readLine();
    }

    public static void sendFile(String filePath) {
        try {
           /* System.out.print("Please enter the filename that you wish to upload  : ");
            fileName = bufferReader.readLine();*/

            File masterFile = new File(filePath);
            byte[] mybytearray = new byte[(int) masterFile.length()];

            FileInputStream fis = new FileInputStream(masterFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = sock.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(masterFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();


            System.out.println("File " + filePath
                    + " send to server.");
        } catch (Exception e) {
            System.err.println("ERROR! " + e);
        }
    }

    public static void receiveFile(String fileName) {
        try {
            int bytesRead;
            InputStream in = sock.getInputStream();

            DataInputStream clientData = new DataInputStream(in);

            fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(("received_from_server_" + fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0
                    && (bytesRead = clientData.read(buffer, 0,
                            (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.flush();

            System.out
                    .println("File " + fileName + " received from Server.");
        } catch (IOException ex) {

        }
    }
    
    public String filepathgetter(){
    	
    	String filepath="";
    	Filechooser filechooser = new  Filechooser();
    	filepath = filechooser.fileselecthandler();
    	return filepath;
    	
    }
}