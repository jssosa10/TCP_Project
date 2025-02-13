package mundo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingWorker;

public class ThreadDescarga extends SwingWorker<Void,Void> implements InterfazCanceladora{


	private Archivo actual;
	private PrintWriter outP;
	private InputStream in;
	private FileOutputStream out;
	private Socket socket;
	private File archivoFinal;
	private volatile boolean cancelado;
	private Categoria categoria;
	private InterfazProgreso progresor;
	private InterfazCompletado interfazCompletado;

	public final static int TAM_BUFFER=1024;
	public ThreadDescarga(Socket s, InputStream pIn, PrintWriter pOut, Archivo a, Categoria c, InterfazProgreso pProgresor, InterfazCompletado completador)
	{
		socket=s;
		in=pIn;
		outP=pOut;
		actual=a;
		progresor= pProgresor;
		categoria=c;
		interfazCompletado=completador;
	}	

	@Override
	public Void doInBackground() throws Exception {
		cancelado=false;
		try {
			File dir= new File("./docs/"+ categoria.darNombre());
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			String ruta= "./docs/"+categoria.darNombre()+"/"+actual.darRuta();
			archivoFinal = new File(ruta);
			archivoFinal.createNewFile();
			out = new FileOutputStream(archivoFinal.getPath());
			System.out.println("Se va a escribir:"+categoria.darNombre()+"/"+actual.darRuta());
			outP.println("getfile:"+categoria.darNombre()+"/"+actual.darRuta());		

			int count;
			byte[] buffer = new byte[1024]; // or 4096, or more
			long cantBytes=0;

			while ((count = in.read(buffer)) > 0)
			{
				out.write(buffer, 0, count);
				for (int i = 0; i < buffer.length; i++) {
					System.out.println("bytes recibidos: "+buffer[i]);
					cantBytes++;
				}				
				if(cancelado)
				{
					break;
				}
				else
				{
					outP.println("ok");
				}
				int porcentaje= (int) (cantBytes*100/actual.darTamano());
				progresor.actualizarProgreso(porcentaje);
				if(cantBytes>=actual.darTamano())
				{
					break;
				}
			}
			out.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void done()
	{
		if(cancelado)
		{
			outP.println("Cancelar");
			BufferedReader bf= new BufferedReader(new InputStreamReader(in));
			String text="";
			try {
				System.out.println(bf.readLine());
				text = bf.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("fin descarga "+ text);
			interfazCompletado.completado(null);
		}
		else
		{
			interfazCompletado.completado(archivoFinal);
		}
	}

	@Override
	public void cancelar() {
		cancelado=true;
	}


}
