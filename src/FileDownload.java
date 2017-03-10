import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FileDownload extends Thread {
        private Socket socket;
        private int clientNumber;
        private String sharedFilePath;

        public FileDownload(Socket socket, int clientNumber,String sharedFilePath) {
        	System.out.println("Server with" + socket +"Started");
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.sharedFilePath = sharedFilePath;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        
        public void run() {
            try {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                
                while (true) {
                    String input = in.readLine();
                    
                    
                    File file = new File(sharedFilePath+"\\"+input);
                    if(file.exists())
                    {
                    	FileInputStream fis = new FileInputStream(file);
                    	byte[] data = new byte[(int) file.length()];
                    	fis.read(data);
                    	fis.close();
                    	String str = new String(data, "UTF-8");
                    	out.println(str);
                    }
                    else
                    	out.println("No Such File With Me");
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }
        
        private void log(String message) {
            System.out.println(message);
        }
}
