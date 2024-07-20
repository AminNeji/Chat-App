package chatapp;
import java.net.*;
import java.io.*;
import java.util.*;
public class chatserver {
	public static List<ClientHandler> clients=new ArrayList<>();
	
	public static void main(String []Args) throws IOException{
		try (ServerSocket serversocket = new ServerSocket(2000)) {
			System.out.println("Server started . waiting for clients");
			while(true) {
				Socket clientsocket=serversocket.accept();
				System.out.println("client connected"+clientsocket);
				ClientHandler cthread = new ClientHandler(clientsocket,clients);
				clients.add(cthread);
				new Thread(cthread).start();
			}
		}
	}

}
class ClientHandler implements Runnable{
	private Socket clientsocket;
	private List<ClientHandler> clients;
	private PrintWriter out;
	private BufferedReader in;
	ClientHandler(Socket socket,List<ClientHandler> clients)throws IOException{
		this.clientsocket=socket;
		this.clients=clients;
		this.out=new PrintWriter(clientsocket.getOutputStream(),true);
		this.in=new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
		
	}
	public void run() {
		try {
			String inline;
			while((inline=in.readLine())!=null) {
				for(ClientHandler clienth:clients) {
					clienth.out.println(inline);
				}
			}
		}
		catch(IOException e) {
			System.out.println("error");
		}
		finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.close();
			try {
				clientsocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
