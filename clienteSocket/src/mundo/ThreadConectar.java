package mundo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.SwingWorker;

public class ThreadConectar extends SwingWorker<Boolean, String>{

	private Socket socket;
	
	private InputStream in;
	
	private PrintWriter out;
	public ThreadConectar(Socket pSocket, InputStream pIn, PrintWriter pOut)
	{
		socket=pSocket;
		in=pIn;
		out=pOut;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		
		out.println("Holiwi");
		out.flush();

		byte[] buffer = new byte[6]; // or 4096, or more
		BufferedReader bf= new BufferedReader(new InputStreamReader(in));
		
		String text=bf.readLine();
		System.out.println("Llega conectar "+text);
		if(text.equals("Holiwi"))
		{
			return true;
		}
		return false; 
	}
}
