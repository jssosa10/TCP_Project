package interfaz;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingWorker;

public class ThreadDesconectar extends SwingWorker<Void, Void>{

	private Socket socket;	
	private InputStream in;	
	private PrintWriter out;

	public ThreadDesconectar(Socket pSocket, InputStream pIn, PrintWriter pOut)
	{
		socket=pSocket;
		in=pIn;
		out=pOut;		
	}


	@Override
	protected Void doInBackground() throws Exception {
		out.println("Adios");

		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
