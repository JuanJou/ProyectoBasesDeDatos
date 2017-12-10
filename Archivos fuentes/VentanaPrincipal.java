import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import quick.dbtable.DBTable;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Color;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private DBTable base;
	private JButton btnIngresar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	public VentanaPrincipal() {
		base= new DBTable();
		setBounds(350, 350, 464, 87);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(355, 7, 89, 23);
		contentPane.add(btnIngresar);
		
		passwordField = new JPasswordField();
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String driver ="com.mysql.jdbc.Driver";
	        	String servidor = "localhost:3306";
	            String baseDatos = "parquimetros";
	            String usuario = "admin";
	            String clave = passwordField.getText();
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
	   
	            try {
					base.connectDatabase(driver, uriConexion, usuario, clave);
					VentanaConsultas v=new VentanaConsultas();
					v.setVisible(true);
					dispose();
				} catch (ClassNotFoundException | SQLException e) {
					JOptionPane.showMessageDialog(null,"Contraseña incorrecta","Error",JOptionPane.ERROR_MESSAGE);
					passwordField.setText("");
				}
			}
		});
		passwordField.setBounds(252, 8, 93, 20);
		contentPane.add(passwordField);
		
		JLabel lblIngreseLaContrasea = new JLabel("Ingrese la contrase\u00F1a del administrador:");
		lblIngreseLaContrasea.setBounds(10, 11, 232, 14);
		contentPane.add(lblIngreseLaContrasea);
		
		
	}
}
