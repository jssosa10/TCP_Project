package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PanelIniciar extends JPanel implements ActionListener{

	private JButton butIniciar;
	private InterfazCliente interfaz;
	public final static String RUTA_INICIAR= "./data/start.jpg";
	public PanelIniciar(InterfazCliente i)
	{
		interfaz=i;
		setLayout(new BorderLayout());
		butIniciar= new JButton(new ImageIcon(((new ImageIcon("./data/start.jpg")).getImage()).getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH)));
		butIniciar.addActionListener(this);
		butIniciar.setActionCommand("Iniciar");
		add(butIniciar, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String com= e.getActionCommand();
		if(com.equals("Iniciar"))
		{
			interfaz.iniciar();
		}
		updateUI();
	}
}
