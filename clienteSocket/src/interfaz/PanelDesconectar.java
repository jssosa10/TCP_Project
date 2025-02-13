package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PanelDesconectar extends JPanel implements ActionListener{

	private InterfazCliente interfaz;
	private JButton butCancelar;
	
	public final static String DESCONECTAR= "DESCONECTAR";
	
	public PanelDesconectar(InterfazCliente interfazCliente)
	{
		interfaz=interfazCliente;
		setLayout(new GridLayout(1, 1));
		butCancelar= new JButton("Desconectarse");
		butCancelar.addActionListener(this);
		butCancelar.setActionCommand(DESCONECTAR);
		add(butCancelar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String accion= e.getActionCommand();
		if(accion.equals(DESCONECTAR))
		{
			try {
				interfaz.validarBloqueado();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(interfaz, "Hubo timeout");
				return;
			}
			interfaz.desconectar();
		}
		
	}

	
}
