import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class VentanaInicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicio frame = new VentanaInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaInicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 350, 276, 241);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 204, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaPrincipal().setVisible(true);
			}
		});
		btnAdmin.setToolTipText("");
		btnAdmin.setBounds(52, 79, 162, 23);
		contentPane.add(btnAdmin);
		
		JButton btnUnidadDeInspector = new JButton("Unidad de Inspector");
		btnUnidadDeInspector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UnidadInspector().setVisible(true);
			}
		});
		btnUnidadDeInspector.setBounds(52, 113, 162, 23);
		contentPane.add(btnUnidadDeInspector);
		
		JButton btnConectarTarjetaY = new JButton("Conectar");
		btnConectarTarjetaY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ConTarjetaParq().setVisible(true);;
			}
		});
		btnConectarTarjetaY.setBounds(52, 147, 162, 23);
		contentPane.add(btnConectarTarjetaY);
		
		JLabel lblProyectoJouglard = new JLabel("Proyecto: Jouglard, Juan Francisco");
		lblProyectoJouglard.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProyectoJouglard.setBounds(10, 11, 240, 23);
		contentPane.add(lblProyectoJouglard);
		
		JLabel lblAplicacinParquimetros = new JLabel("Aplicaci\u00F3n parquimetros");
		lblAplicacinParquimetros.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAplicacinParquimetros.setBounds(56, 45, 158, 14);
		contentPane.add(lblAplicacinParquimetros);
	}
}
