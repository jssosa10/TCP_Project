 package mundo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;


public class Cliente {

	public final static int PUERTO = 9000;      
	public final static String SERVIDOR = "35.173.252.14";  

	public final static int DESCONECTADO = 0;      
	public final static int CONECTADO= 1;
	public final static int TRANSFIRIENDO= 2;


	public final static int NUM_CATEGORIAS=3;
	public final static String[] CATEGORIAS= {"GRANDE","MEDIANO","PEQUENO"};
	public final static String[] RUTAS_CATEGORIA= {"./data/grande.png","./data/mediano.png","./data/pequeno.png"};

	private Categoria[] listaCategorias;
	private int estado;
	private Categoria categoriaActual;

	private Socket socket;
	private PrintWriter out;
	private InputStream in;
	
	private InterfazProgreso progresor;
	private InterfazEstado estador;
	private InterfazCanceladora interfazCanceladora;

	public Cliente(InterfazProgreso pProgresor,InterfazEstado pEstador)
	{
		progresor=pProgresor;
		estador= pEstador;
		
		listaCategorias= new Categoria[NUM_CATEGORIAS];	
		for (int i = 0; i < listaCategorias.length; i++) {
			listaCategorias[i]= new Categoria(CATEGORIAS[i],RUTAS_CATEGORIA[i]);
		}
	}
	
	public boolean conectarse() throws UnknownHostException, IOException, InterruptedException, ExecutionException
	{
		socket= new Socket(SERVIDOR,PUERTO);
		estado=CONECTADO;

		in = socket.getInputStream();
		out = new PrintWriter(socket.getOutputStream(), true);
		ThreadConectar con= new ThreadConectar(socket,in,out);
		con.execute();
		estador.actualizarEstado(estado);
		return con.get();
	}

	public Categoria[] darCategorias() 
	{
		return listaCategorias;
	}
	
	public int darEstado()
	{
		return estado;
	}

	public void descargar( Archivo archivo, InterfazCompletado completador) throws Exception {
		validarBloqueado();
		estado=TRANSFIRIENDO;
		ThreadDescarga t=new ThreadDescarga(socket,in,out,archivo,categoriaActual, progresor, completador);
		t.execute();
		estador.actualizarEstado(estado);
		interfazCanceladora=t;
	}
	
	public Categoria darCategoriaActual()
	{
		return categoriaActual;
	}
	
	public void validarBloqueado() throws Exception
	{
		System.out.println("llega cat");
		int p= in.available();
		System.out.println("pe "+p);
		if(p>0)
		{		
			System.out.println("shega2");
			BufferedReader bf= new BufferedReader(new InputStreamReader(in));
			if(bf.readLine().equals("TIMEOUT"))
			{
				throw new Exception();
			}
		}
	}

	public Archivo[] listar(Categoria cat) throws Exception { 
		validarBloqueado();
		categoriaActual= cat;
		ThreadListar list= new ThreadListar(socket,in,out,cat);
		list.execute();
		return list.get();
	}

	public void desconectar() throws IOException {
		new ThreadDesconectar(socket,in,out).execute();		
		estado=DESCONECTADO;
		estador.actualizarEstado(estado);
	}

	public void cancelar() {
		interfazCanceladora.cancelar();
		estado=CONECTADO;
		estador.actualizarEstado(estado);
		out.println("CANCELAR");
	}
}
