package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PanelVolver extends JPanel implements ActionListener{

	private InterfazCliente interfaz;
	private JButton butCancelar;
	
	public final static String VOLVER= "Volver";
	
	public PanelVolver(InterfazCliente interfazCliente)
	{
		interfaz=interfazCliente;
		setLayout(new GridLayout(1, 1));
		butCancelar= new JButton("Volver");
		butCancelar.addActionListener(this);
		butCancelar.setActionCommand(VOLVER);
		add(butCancelar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String accion= e.getActionCommand();
		if(accion.equals(VOLVER))
		{
			interfaz.volver();
		}
		
	}

	
}
