package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import mundo.Categoria;
import mundo.Cliente;

public class PanelCategorias extends JPanel implements ActionListener{

	private InterfazCliente interfaz;

	private Categoria[] categorias;
	
	private JPanel panelAux1;
	
	private JPanel panelAux2;
	
	private JButton[] botonesArchivos;
	public PanelCategorias(InterfazCliente pInter, Categoria[] pCategorias) 
	{
		interfaz=pInter;
		categorias=pCategorias;
		botonesArchivos= new JButton[categorias.length];
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200,500));
		setBorder( new TitledBorder( "Categorias" ) );
		
		panelAux1= new JPanel();
		panelAux1.setLayout(new GridLayout(1,categorias.length));
		panelAux2= new JPanel();
		panelAux2.setLayout(new GridLayout(1,categorias.length));

		for (int i = 0; i < pCategorias.length; i++)
		{
			JTextField txtTemp= new JTextField(categorias[i].darNombre());
			txtTemp.setSize(new Dimension(50,20));
			txtTemp.setEditable(false);
			panelAux1.add(txtTemp);
			
			botonesArchivos[i]= new JButton(new ImageIcon(((new ImageIcon(categorias[i].darRutaImg())).getImage()).getScaledInstance(250, 300, java.awt.Image.SCALE_SMOOTH)));
			botonesArchivos[i].addActionListener(this);
			botonesArchivos[i].setActionCommand(i+"");
			panelAux2.add(botonesArchivos[i]);
		}
		
		add(panelAux2, BorderLayout.CENTER);
		add(panelAux1, BorderLayout.NORTH);
		updateUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int comando=Integer.parseInt(e.getActionCommand());
		interfaz.listar(categorias[comando]);
	}
	public void deshabilitarBotones() {
		for (int i = 0; i < botonesArchivos.length; i++) {
			botonesArchivos[i].setEnabled(false);
		}
	}
	
	public void habilitarBotones()
	{
		for (int i = 0; i < botonesArchivos.length; i++) {
			botonesArchivos[i].setEnabled(true);
		}
	}

}
