
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client2 extends Thread implements ActionListener{
    /**
	 * 
	 */
		private JTextArea textArea;
		private JButton button;
		private JButton button1;
		private JButton button2;
		private JButton button3;
		private JFrame frame;
		private JTextField field1;
		private String inputString;
		private JPanel panel;
		String sharedFilePath;
	    String outputPath;
	    String ipAddress;
	    int port;
	    int clientId;
	    int serverPort;
	    String serverAddress;
	    RegisterInfo register;
		@SuppressWarnings("unused")
		private Socket socket;
		private JTextArea textArea1;
		private static int var;
	    public int registerToMainServer() throws UnknownHostException, IOException {
	    	//int serverPort = 7787;
	    	//String serverAddress = ipAddress;
	        Socket s = new Socket(serverAddress, serverPort);
	        BufferedReader in1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
	       PrintWriter out1 = new PrintWriter(s.getOutputStream(), true);
	       try {
	    	   String filesList = "";
	    	   File folder = new File(sharedFilePath);
	 		   File[] listOfFiles = folder.listFiles(); 	  
	 		 for (int i = 0; i < listOfFiles.length; i++) 
	 		  {
	 			 filesList += listOfFiles[i].getName() + "---";
	 		  }
	 		 	System.out.println(filesList);
	            String inputToServer = "register,"+port+","+ipAddress+","+sharedFilePath +"," + filesList;
			    out1.println(inputToServer);
			    String responseFromServer = in1.readLine();
			    clientId = Integer.parseInt(responseFromServer);
			    System.out.println("My Client Id is:"+clientId);
			    textArea1.append("Client Id is:" + clientId);
			    s.close();
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	            return clientId;
	    }
	    
	    /*
	     * Query format sending to server is
	     * query,"filename/all files",files from which clientId, its own clientId
	     * for all files input needed is all files, from which client shared files needed
	     * for particular file just give filename
	     */
	    public String query() throws UnknownHostException, IOException
	    {
	    	String responseFromServer = "";
	    	Socket s = new Socket(serverAddress, serverPort);
	    	BufferedReader in1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    	PrintWriter out1 = new PrintWriter(s.getOutputStream(), true);
	    	try {
	    	   System.out.println("Enter File Name");
	    	   String inputToServer = "query,"+ inputString +","+clientId;
		       System.out.println("Query To Server is:  " + inputToServer);
		       textArea.append("\n Query To Server is:  " + inputToServer +"\n");
			   out1.println(inputToServer);
			   responseFromServer = in1.readLine();
			   System.out.println("Query Hit From Server is:  " + responseFromServer);
			   textArea.append("\n In Server Query Hit for File:"+  inputString);
			   if(inputToServer.contains("all files"))
				   textArea.append("\n Shared Files are:"+responseFromServer);
			   else {
				   if(responseFromServer.contains("No Client with Such a File"))
					   textArea.append("\n No Client with Such a File");
				   else {
					   textArea.append("\n Query Hit From Server is:  " );
					   textArea.append("\n--------------------------------------------------------------------------------------------");
					   textArea.append("\n|  Client Id |  IP Address | Server Port |     fileName   |           Size            |");
					   textArea.append("\n--------------------------------------------------------------------------------------------\n");
				   String split[] = responseFromServer.split("-----");
				   for(int i=0;i<split.length-1;i++)
				   {
					   textArea.append(split[i] +"\n");
					   System.out.println(split[i]);
				   }
				   textArea.append("--------------------------------------------------------------------------------------------\n");
				   System.out.println("Num of Hits is:" + split[split.length-1]);
				   textArea.append("\n Num of Hits is:" +split[split.length-1]+"\n" );
				   }
			   }
			   s.close();
	       }
	       catch(Exception e)
	       {
	       	e.printStackTrace();
	       }
	       return responseFromServer;
	    }

	 public Client2(int port,String ipAddress,String sharedFilePath,String outputPath) 
	    { 
		 this.port = port;
	    	this.ipAddress = ipAddress;
	    	this.sharedFilePath = sharedFilePath;
	    	this.outputPath = outputPath;
	    	serverAddress = "localhost";
	    	serverPort = 7787;
	    	start();
	    	System.out.println("Client Server Starting Done");
 	   	frame = new JFrame("Client1");
 	   	panel = new JPanel();
 	   	panel.setLayout(new FlowLayout());
 	
 	   	button = new JButton();
 	   	button.setPreferredSize(new Dimension(500, 22));
 	   	button.setText("Register to server");
 	   	panel.add(button);
 	   	button.setActionCommand("register");
 	   	button.addActionListener((ActionListener) this);
 	   	
 	   	textArea1 = new JTextArea(5,45);
 	   	textArea1.setEditable(false);
 	   	textArea1.append("Client's Server Starting at port no. : "+port+"\n");
 	   	panel.add(textArea1);
 	   	
 	   	frame.add(panel);
 	   	field1 = new JTextField(45);
 	   	panel.add(field1, BorderLayout.WEST);
 	    
 	   	frame.setSize(550, 450);
 	   	frame.setLocationRelativeTo(null);
 	   	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	   	frame.setVisible(true);

    }
  

	public void actionPerformed(ActionEvent event) 
    {
        String command = event.getActionCommand();

        if (command.equals("register"))
        {
    
        	try {
           	
        		button.setEnabled(false);
        		textArea1.append("Connecting to Server with ");
        		button1 = new JButton();
        		textArea = new JTextArea(10,45);
        		panel.add(textArea);
        		panel.add(new JScrollPane(textArea));
        		textArea.setEditable(false);
        	   	button1.setPreferredSize(new Dimension(500, 22));
        		button1.setText("Query to server");
        		button1.setActionCommand("query");
        		button1.addActionListener((ActionListener) this);
        		panel.add(button1);
        		registerToMainServer();
        		textArea1.append("\nQUERY           :  enter the file name");
        		textArea1.append("\nPING               :  enter the client ID");
        		textArea1.append("\nDOWNLOAD :  enter the port number, IP address, required File\n");
        	} 
        	catch (UnknownHostException u) {
        		u.printStackTrace();
        		}
        	
        	catch (IOException io) {
        		io.printStackTrace();
        		}	
        }
        if (command.equals("query"))
        {
        	inputString = field1.getText() ;
    		field1.setText("") ;
    		
    		if(var!=0)
    		{ 
    			textArea.append("\n  \t\t  ************ NEW QUERY ************\n");
    			textArea.append("\n\" " + inputString+"\"" +" sending query to server\n");
    		}
    		
    		else
    		{
    		textArea.append("\n\" " + inputString+"\"" +" sending query to server\n");
    		button2 = new JButton();
    		panel.add(button2);
    		button2.setPreferredSize(new Dimension(500, 22));
    		button2.setText("ping");
    		button2.setActionCommand("PING(optional)");
    		button2.addActionListener((ActionListener) this);  
    		button3 = new JButton();
        	button3.setPreferredSize(new Dimension(500, 22));
        	button3.setText("Transfer the data from another Client");
        	button3.setActionCommand("transfer");
        	button3.addActionListener((ActionListener) this);
        	panel.add(button3);
        	var++;
    		}
            	
        		try {
					query();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
        if(command.equals("ping"))
        {
        	inputString = field1.getText() ;
        	System.out.println(inputString);
        	field1.setText("") ;
        	textArea.append("Client Header" +inputString +" Verifying whether client is in active state or not\n");
        	try
        	{
        		/*
        		 * 
        		 * for ping ping,clientid(requesting one), ipaddress, port
        		 * sending to server is ping,senders client id, clientid(requesting one), ipaddress, port
        		 * input needed is clientid(requesting one), ipaddress, port
        		 */
        		// requestedclientid, ipaddress, port
        		Socket s = new Socket(serverAddress,serverPort);
        		BufferedReader in1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
    	    	PrintWriter out1 = new PrintWriter(s.getOutputStream(), true);
    	    	String pingInput = "ping,"+clientId + "," + inputString;
    	    	out1.println(pingInput);
    	    	String pong = in1.readLine();
    	    //	textArea.append("Client is Available for Downlaod\n");
    	    	if(pong.contains("No such Client Available") || pong.contains("Requested Client Not available for Download"))
    	    		textArea.append(pong);
    	    	else
    	    	{
    	    		String split[] = pong.split(",");
    	    		textArea.append("Files Count is:" + split[3] +"\n");
    	    		textArea.append("Files List is:" + split[4] +"\n");
    	    	}       		
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	/*
        	String split[] = inputString.split(",");
        	try{
				int port = Integer.parseInt(split[1]);
				String toConnectserverAddress = split[2];
				
				Socket testSocket = new Socket(toConnectserverAddress,port);
				testSocket.close();
				
				textArea.append("Client is Available for Download\n");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					textArea.append("Client is Not Available for Download\n");
				}
				*/
        }
        if(command.equals("transfer"))
        {
        	inputString = field1.getText() ;
        	field1.setText("") ;
        	textArea.append(inputString +" connecting to another client\n");
        	  
        	String split[] = inputString.split(",");
        	try {
				try {
					connectToServer(Integer.parseInt(split[0]),split[1]);
				} catch (IOException e) {
						e.printStackTrace();
				}
			} catch (NumberFormatException e) {
						e.printStackTrace();
			}
        }
        
        }
	public void connectToServer(int portNo,String ServerAddress) throws IOException {
		Socket socket = new Socket(ServerAddress, portNo);
        BufferedReader in1 = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out1 = new PrintWriter(socket.getOutputStream(), true);
        for (int i = 0; i < 3; i++) {
            System.out.println(in1.readLine() + "\n");
        }
        try {
	        String split[] = inputString.split(",");
	        System.out.println("Enter File You need");
		        out1.println(split[2]);
		        String response = in1.readLine();
		        if(response.contains("No Such File With Me"))
		        {
		        	System.out.println("No Such File With Me");
		        	textArea.append("No Such File With Me");
		        	System.exit(0);
		        }
		        File file = new File(outputPath+"\\"+split[2]);
	            BufferedWriter output = new BufferedWriter(new FileWriter(file));
	            output.write(response);
	            output.flush();
	            output.close();
	            /*
		        FileWriter writer = new FileWriter(outputPath+"\\"+split[2]);
		        writer.append(response);
		        writer.flush();
		        writer.close();
		        */
		        System.out.println("File is Successfully Downloaded" + response);
		        textArea.append("File is Successfully Downloaded\n");
		        socket.close();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
    }  

		public void run()
        {
        	 System.out.println("Client Server Started at socket"+port);
    		 int clientNumber = 0;
    		 ServerSocket listener =null;
    		 try 
             {
    		 listener = new ServerSocket(port);	 
                 while (true) 
                 {
                	 System.out.println("Client Sever Waiting for Client");
                     new FileDownload(listener.accept(), clientNumber++,sharedFilePath).start();
                 }
             }
    		 catch(Exception e)
    		 {
    			 e.printStackTrace();
    		 }
    		 finally
    		 {
                 try 
                 {
    				listener.close();
    			 } 
                 catch (IOException e) 
                 {
    				e.printStackTrace();
    			 }
             }	
    		 
    }

  	public static void main(String[] args) throws Exception 
    {
		new Client2(2223,"localhost","input2","output2");
		
			
    }
}


