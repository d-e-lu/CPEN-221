package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Client(String hostname, int port)throws IOException{
		this.socket = new Socket(hostname, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	
    public void sendRequest(String request) throws IOException {
        String[] parts = request.split(" ");
        switch(parts[0]){
        case "RANDOMREVIEW":
        	String restaurantName = parts[1];
        	System.out.println("Hello");
        	break;
        case "GETRESTAURANT":
        	String id = parts[1];
        	break;
        case "ADDUSER":
        	break;
        case "ADDRESTAURANT":
        	break;
        case "ADDREVIEW":
        	break;
        default:
        	System.err.println("Not a valid request");
        	return;
        }
    	
    	out.print(request + "\n");
        out.flush(); // important! make sure x actually gets sent
    }
    
    public String getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }
        
        try {
            return "Hi";
        } catch (NumberFormatException nfe) {
            throw new IOException("misformatted reply: " + reply);
        }
    }
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
	public static void main(String[] args){
		try{
			Client c = new Client("localhost", 4949);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}