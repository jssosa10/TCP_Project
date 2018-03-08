package interfaz;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mundo.Cliente;
import mundo.InterfazEstado;

public class PanelEstado extends JPanel implements InterfazEstado{

	private JPanel panelTemp;	
	private ImageIcon estadoImg;	
	private JTextField estadoTxt;
	private JLabel lblImg;
	
	public final static String IMG_DESCONECTADO="./data/desconectado.png";
	public final static String IMG_CONECTADO="./data/conectado.png";
	public final static String IMG_TRANSFIRIENDO="./data/transfiriendo.png";
	
	public PanelEstado() {
		setLayout(new GridLayout(1, 2));
		JTextField texto= new JTextField("Estado");
		texto.setEditable(false);
		add(texto);
		panelTemp= new JPanel();
		panelTemp.setLayout(new GridLayout(1,2));
		estadoImg= new ImageIcon(IMG_DESCONECTADO);
		estadoTxt= new JTextField("Desconectado");
		estadoTxt.setEditable(false);
		lblImg=new JLabel(estadoImg);
		panelTemp.add(lblImg);
		panelTemp.add(estadoTxt);
		add(panelTemp);
	}
	
	@Override
	public void actualizarEstado(int estado) {
		if(estado== Cliente.DESCONECTADO)
		{
			estadoTxt.setText("Desconectado");
			estadoImg= new ImageIcon(IMG_DESCONECTADO);
		}
		else if(estado== Cliente.CONECTADO)
		{
			estadoTxt.setText("Conectado");
			estadoImg= new ImageIcon(IMG_CONECTADO);
		}
		else if(estado== Cliente.TRANSFIRIENDO)
		{
			estadoTxt.setText("Transfiriendo");
			estadoImg= new ImageIcon(IMG_TRANSFIRIENDO);
		}
		estadoTxt.setEditable(false);
		lblImg=new JLabel(estadoImg);
		panelTemp.removeAll();
		panelTemp.add(lblImg);
		panelTemp.add(estadoTxt);
		updateUI();
	}

}
