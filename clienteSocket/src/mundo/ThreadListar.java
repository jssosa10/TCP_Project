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
		System.out.println("getdir:" + cat.darNombre());
		out.println("getdir:" + cat.darNombre());	
		out.flush();
		byte[] buffer = new byte[1024]; // or 4096, or more
		BufferedReader bf= new BufferedReader(new InputStreamReader(in));
		String text=bf.readLine();
		System.out.println("texto recibido "+ text);
		text=text.trim();
		if(text.trim().isEmpty())
		{
			return null;
		}
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
