package mundo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.SwingWorker;

public class ThreadListar extends SwingWorker<Archivo[], String>{

	private Socket socket;

	private InputStream in;

	private PrintWriter out;
	
	private Categoria cat;

	public ThreadListar(Socket pSocket, InputStream pIn, PrintWriter pOut, Categoria pCat)
	{
		socket=pSocket;
		in=pIn;
		out=pOut;
		cat=pCat;
	}

	@Override
	protected Archivo[] doInBackground() throws Exception {
		out.println(cat.darNombre());
		out.flush();
		
		byte[] buffer = new byte[1024]; // or 4096, or more
		BufferedReader bf= new BufferedReader(new InputStreamReader(in));
		String text=bf.readLine();
		char[] chars= text.toCharArray();
		text=text.trim();

		String[] archivoStr= text.split(";");
		Archivo[] archivos= new Archivo[archivoStr.length];
		for (int i = 0; i < archivoStr.length; i++) 
		{
			String[] datos= archivoStr[i].trim().split(":::");
			archivos[i]= new Archivo(datos[0],Integer.parseInt(datos[1]));
		}
		return archivos;
	}

}
