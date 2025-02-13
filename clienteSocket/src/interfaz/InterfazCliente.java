package interfaz;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import mundo.Archivo;
import mundo.Categoria;
import mundo.Cliente;
import mundo.InterfazCanceladora;
import mundo.InterfazCompletado;

public class InterfazCliente extends JFrame implements InterfazCompletado{

	private PanelProgreso panelProgreso;
	private PanelCategorias panelCategorias;	
	private PanelEstado panelEstado;	
	private PanelIniciar panelIniciar;
	private PanelArchivos panelArchivos;
	private PanelDesconectar panelDesconectar;
	private PanelVolver panelVolver;

	private Cliente mundo;	


	public InterfazCliente()
	{		
		setLayout(new BorderLayout());
		setVisible(true);
		setSize(new Dimension(800,500));	

		panelIniciar= new PanelIniciar(this);
		panelProgreso= new PanelProgreso(this);
		panelEstado= new PanelEstado();

		add(panelIniciar, BorderLayout.CENTER);	
		add(panelEstado, BorderLayout.NORTH);

	}

	public void iniciar()
	{

		mundo= new Cliente(panelProgreso, panelEstado);
		try {
			boolean b= mundo.conectarse();
			if(!b)
			{
				JOptionPane.showMessageDialog(this,"No se recibi� lo que se esperaba" , "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		panelIniciar.invalidate();
		remove(panelIniciar);

		panelCategorias= new PanelCategorias(this, mundo.darCategorias());
		panelCategorias.setSize(new Dimension(800,500));
		add(panelCategorias, BorderLayout.CENTER);
		panelDesconectar= new PanelDesconectar(this);
		add(panelDesconectar, BorderLayout.SOUTH);
		validate();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfazCliente i=new InterfazCliente();
		i.setVisible(true);
	}

	public void listar(Categoria cat) {
		try {
			Archivo[] resp= mundo.listar(cat);
			panelArchivos= new PanelArchivos(this,resp, mundo.darCategoriaActual());
			panelArchivos.habilitarBotones();
			panelCategorias.invalidate();
			panelDesconectar.invalidate();
			remove(panelCategorias);
			remove(panelDesconectar);
			panelVolver= new PanelVolver(this);			
			add(panelArchivos, BorderLayout.CENTER);
			add(panelVolver, BorderLayout.SOUTH);	
			validate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			panelEstado.actualizarEstado(Cliente.DESCONECTADO);
			JOptionPane.showMessageDialog(this, "Hubo timeout");
		}
	}

	public void descargar(Archivo archivo) {
		panelArchivos.deshabilitarBotones();
		panelVolver.invalidate();
		remove(panelVolver);
		add(panelProgreso, BorderLayout.SOUTH);
		validate();
		try {
			mundo.descargar(archivo, this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			panelEstado.actualizarEstado(Cliente.DESCONECTADO);
			JOptionPane.showMessageDialog(this, "Hubo timeout");
		}
	}


	public void cancelar() {
		mundo.cancelar();
		panelArchivos.habilitarBotones();
		panelProgreso.invalidate();
		remove(panelProgreso);
		panelVolver= new PanelVolver(this);			
		add(panelVolver, BorderLayout.SOUTH);	
		validate();
	}

	public void validarBloqueado() throws Exception
	{
		mundo.validarBloqueado();
		panelEstado.actualizarEstado(Cliente.DESCONECTADO);
		JOptionPane.showMessageDialog(this, "Hubo timeout");
	}

	public void desconectar() {
		try {
			mundo.desconectar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("llega desconectar");
		panelDesconectar.invalidate();
		if(panelArchivos!=null)
		{
			panelArchivos.invalidate();
		}
		if(panelCategorias!=null)
		{
			panelCategorias.invalidate();
		}
		removeAll();
		System.out.println("llega2");
		add(panelEstado, BorderLayout.NORTH);
		add(panelIniciar, BorderLayout.CENTER);	
		add(panelEstado, BorderLayout.NORTH);

		validate();
	}

	public void volver() 
	{
		panelArchivos.invalidate();
		panelVolver.invalidate();
		remove(panelArchivos);
		remove(panelVolver);

		panelCategorias= new PanelCategorias(this, mundo.darCategorias());
		panelCategorias.setSize(new Dimension(800,500));
		add(panelCategorias, BorderLayout.CENTER);
		panelDesconectar= new PanelDesconectar(this);
		add(panelDesconectar, BorderLayout.SOUTH);
		validate();
	}

	@Override
	public void completado(File archivoFinal) {
		if(archivoFinal==null)
		{

		}
		else
		{
			panelProgreso.complete();	
			try {
				Desktop.getDesktop().open(new File(archivoFinal.getParent()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void volver2()
	{
		panelArchivos.invalidate();
		panelProgreso.invalidate();
		panelProgreso.restaurar();
		remove(panelArchivos);
		remove(panelProgreso);

		panelCategorias= new PanelCategorias(this, mundo.darCategorias());
		panelCategorias.setSize(new Dimension(800,500));
		add(panelCategorias, BorderLayout.CENTER);
		panelDesconectar= new PanelDesconectar(this);
		add(panelDesconectar, BorderLayout.SOUTH);
		validate();
	}

}
