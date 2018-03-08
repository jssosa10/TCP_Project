package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import mundo.Categoria;
import mundo.InterfazProgreso;

public class PanelProgreso extends JPanel implements InterfazProgreso, ActionListener{

	private InterfazCliente interfaz;
	private JProgressBar progreso;
	private JButton butCancelar;

	public final static String CANCELAR= "CANCELAR";
	public final static String COMPLETADO= "COMPLETADO";

	public PanelProgreso(InterfazCliente interfazCliente)
	{
		interfaz=interfazCliente;
		setLayout(new GridLayout(2, 1));
		butCancelar= new JButton("Cancelar");
		butCancelar.addActionListener(this);
		butCancelar.setActionCommand(CANCELAR);
		progreso= new JProgressBar();
		add(progreso);
		add(butCancelar);
		progreso.setStringPainted(true);
		progreso.setValue(0);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String accion= e.getActionCommand();
		if(accion.equals(CANCELAR))
		{
			interfaz.cancelar();
		}
		if(accion.equals(COMPLETADO))
		{
			interfaz.volver2();
		}

	}

	@Override
	public void actualizarProgreso(int porcentaje) {
		progreso.setValue(porcentaje);
	}

	public void complete() {
		butCancelar.setText("Completado (Volver)");
		butCancelar.setActionCommand(COMPLETADO);
		butCancelar.setBackground(Color.GREEN);
		updateUI();
	}
	
	public void restaurar() {
		butCancelar.setText("Cancelar");
		butCancelar.setActionCommand(CANCELAR);
		butCancelar.setBackground(null);
		updateUI();
	}




}
