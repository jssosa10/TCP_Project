package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import mundo.Archivo;
import mundo.Categoria;

public class PanelArchivos extends JPanel implements ActionListener{

	private InterfazCliente interfaz;
	private Categoria categoriaActual;
	private Archivo[] archivos;	
	private JPanel panelAux1;	
	private JPanel panelAux2;	
	private JPanel panelAux3;	
	private JButton[] botonesArchivos;


	public PanelArchivos(InterfazCliente pInter, Archivo[] pArchivos, Categoria categoria) 
	{
		categoriaActual=categoria;
		interfaz=pInter;
		archivos=pArchivos;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200,500));
		setBorder( new TitledBorder( "Archivos de la categoria "+ categoriaActual.darNombre()) );

		if(pArchivos!=null)
		{
			botonesArchivos= new JButton[archivos.length];
			panelAux1= new JPanel();
			panelAux1.setLayout(new GridLayout(1,archivos.length));
			panelAux2= new JPanel();
			panelAux2.setLayout(new GridLayout(1,archivos.length));
			panelAux3= new JPanel();
			panelAux3.setLayout(new GridLayout(1,archivos.length));		
			for (int i = 0; i < pArchivos.length; i++)
			{
				JTextField txtTemp= new JTextField("Nombre: "+archivos[i].darRuta());
				txtTemp.setSize(new Dimension(50,20));
				txtTemp.setEditable(false);
				panelAux1.add(txtTemp);

				botonesArchivos[i]= new JButton(new ImageIcon(((new ImageIcon(archivos[i].darRutaImg())).getImage()).getScaledInstance(250, 300, java.awt.Image.SCALE_SMOOTH)));
				botonesArchivos[i].addActionListener(this);
				botonesArchivos[i].setActionCommand(i+"");
				panelAux2.add(botonesArchivos[i]);

				JTextField txtTemp2= new JTextField("Tama�o: "+ archivos[i].darTamano()+ " bytes");
				txtTemp2.setSize(new Dimension(50,20));
				txtTemp2.setEditable(false);
				panelAux3.add(txtTemp2);
			}
			add(panelAux2, BorderLayout.CENTER);
			add(panelAux1, BorderLayout.NORTH);
			add(panelAux3, BorderLayout.SOUTH);
		}
		else
		{			
			ImageIcon vacio= new ImageIcon("./data/solo.jpg");
			JLabel lblVacio= new JLabel(vacio);
			JPanel panelArchivo= new JPanel();
			panelArchivo.add(lblVacio);
			add(lblVacio, BorderLayout.CENTER);
			setVisible(true);
		}

		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int comando=Integer.parseInt(e.getActionCommand());
		interfaz.descargar(archivos[comando]);
	}
	public void deshabilitarBotones() {
		for (int i = 0; i < botonesArchivos.length; i++) {
			botonesArchivos[i].setEnabled(false);
		}
	}

	public void habilitarBotones()
	{
		if(botonesArchivos!=null)
		{
			for (int i = 0; i < botonesArchivos.length; i++) {
				botonesArchivos[i].setEnabled(true);
			}
		}
	}

}
