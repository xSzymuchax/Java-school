import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connector implements NetConnection{
	Socket socket;
	@Override
	public void connect(String host, int port) {
		try {
			socket = new Socket(host, port);
			System.out.println("POLACZONO");
			BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );	
			OutputStreamWriter sender = new OutputStreamWriter(socket.getOutputStream());
			String line;
			
			Thread t = new Thread(new Runnable(){
				 public void run(){
					 try {
						 String line;
						while ( ( line = br.readLine() ) != null ) {
								System.out.println( line );  // to jest odczyt blokujacy !!! http://stackoverflow.com/questions/2776724/java-bufferedreader-readline-blocking
							}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
			});
			t.start();
			
			while ( ( line = br.readLine() ) != null ) {
				System.out.println( line );  // to jest odczyt blokujacy !!! http://stackoverflow.com/questions/2776724/java-bufferedreader-readline-blocking
			}
			
			
		}
		catch(Exception e) {
			System.out.println("BLAD: " + e.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		//"172.30.24.101"
		//8080
		//Connector c = new Connector();
		//c.connect("172.30.24.101", 8080);

	}
}
