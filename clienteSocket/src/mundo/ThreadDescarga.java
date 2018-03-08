package mundo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
		System.out.println("progre "+ progresor);
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
			System.out.println(ruta);
			archivoFinal = new File(ruta);
			System.out.println("ruta M "+ actual.darRuta());
			out = new FileOutputStream(archivoFinal.getPath());
			outP.println("DESCARGA:::" + categoria.darNombre()+":::"+actual.darRuta());			
			int count;
			byte[] buffer = new byte[1024]; // or 4096, or more
			int cantBytes=0;

			while ((count = in.read(buffer)) > 0 && !cancelado)
			{
				out.write(buffer, 0, count);
				for (int i = 0; i < buffer.length; i++) {
					System.out.println("bytes recibidos: "+buffer[i]);
					cantBytes++;
				}				
				int porcentaje= cantBytes*100/actual.darTamano();
				progresor.actualizarProgreso(porcentaje);
				if(cantBytes>=actual.darTamano())
				{
					System.out.println("sniffiwi");
					//break;
				}
			}
			System.out.println("sale solo");
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
