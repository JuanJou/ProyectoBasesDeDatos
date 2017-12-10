import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.List;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.UIManager;

public class UnidadInspector extends JFrame {

	private JPanel contentPane;
	private JTextField usuario;
	private JPasswordField passwordField;
	private JFormattedTextField PatenteNumero;
	private Inspector i;
	private Admin a;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnidadInspector frame = new UnidadInspector();
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
	public UnidadInspector() {
		a=Admin.getInstancia();
		
		setBounds(100, 100, 404, 470);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 368, 350);
		contentPane.add(panel);
		panel.setVisible(false);
		panel.setBackground(new Color(204, 204, 255));
		panel.setLayout(null);
		
		try {
			PatenteNumero = new JFormattedTextField(new MaskFormatter("UUU###"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		PatenteNumero.setBounds(89, 21, 75, 20);
		panel.add(PatenteNumero);
		PatenteNumero.setColumns(10);	
		
		JLabel lblPatente = new JLabel("Patente:");
		lblPatente.setBounds(31, 24, 48, 14);
		panel.add(lblPatente);
		lblPatente.setForeground(new Color(0, 0, 0));
		lblPatente.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JButton btnEliminar = new JButton("Eliminar patente");
		
		List ListaPatentes = new List();
		ListaPatentes.setBounds(10, 88, 110, 190);
		panel.add(ListaPatentes);
		
		JLabel lblPatentesIngresadas = new JLabel("Patentes ingresadas");
		lblPatentesIngresadas.setBounds(10, 68, 116, 14);
		panel.add(lblPatentesIngresadas);
		lblPatentesIngresadas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPatentesIngresadas.setForeground(new Color(0, 0, 0));
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pat=ListaPatentes.getSelectedItem();
				if (pat!=null){
					int sel=JOptionPane.showConfirmDialog(null,"Se eliminara la patente: "+pat,"Confirmar",JOptionPane.OK_CANCEL_OPTION);
					if (sel==0){
						ListaPatentes.remove(pat);
						i.removePatente(pat);
						if (i.isEmpty())
							btnEliminar.setEnabled(false);
					}
				}
			}
		});
		btnEliminar.setBounds(126, 154, 126, 23);
		panel.add(btnEliminar);
		btnEliminar.setEnabled(false);
		
		JButton btnIngresar_1 = new JButton("Ingresar");
		btnIngresar_1.setForeground(new Color(0, 0, 0));
		btnIngresar_1.setBackground(UIManager.getColor("Button.light"));
		btnIngresar_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIngresar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(!PatenteNumero.getText().equals(null)){
						if (a.estaPatente(PatenteNumero.getText())){
							i.addPatente(PatenteNumero.getText());
							ListaPatentes.add(PatenteNumero.getText());
							PatenteNumero.setValue(null);
							btnEliminar.setEnabled(true);
						}
						else{
							JOptionPane.showMessageDialog(null,"La patente no corresponde a un automóvil registrado","Error",JOptionPane.ERROR_MESSAGE);
							PatenteNumero.setValue(null);
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Error, ","Error",JOptionPane.ERROR_MESSAGE);
						PatenteNumero.setValue(null);
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null,"Patente ya ingresada","Error",JOptionPane.ERROR_MESSAGE);
					PatenteNumero.setValue(null);
				}
			}
		});
		btnIngresar_1.setBounds(213, 20, 92, 23);
		panel.add(btnIngresar_1);
		
		JButton btnConectar = new JButton("Conectar parquimetros");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Multas m=new Multas(i);
				m.setVisible(true);
			}
		});
		btnConectar.setBounds(177, 316, 179, 23);
		panel.add(btnConectar);
		
		
		
		
		JPanel panelLog = new JPanel();
		panelLog.setBackground(new Color(204, 204, 255));
		panelLog.setBounds(10, 11, 368, 77);
		contentPane.add(panelLog);
		panelLog.setLayout(null);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 38, 68, 14);
		panelLog.add(lblContrasea);
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setForeground(new Color(0, 0, 0));
		
		JLabel lblLegajo = new JLabel("Legajo:");
		lblLegajo.setBounds(10, 9, 41, 14);
		panelLog.add(lblLegajo);
		lblLegajo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLegajo.setForeground(new Color(0, 0, 0));
		
		passwordField = new JPasswordField();
		passwordField.setBounds(88, 35, 86, 20);
		panelLog.add(passwordField);
		
		usuario = new JTextField();
		usuario.setBounds(88, 6, 86, 20);
		panelLog.add(usuario);
		usuario.setColumns(10);
		
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogIn.setBounds(201, 21, 80, 23);
		panelLog.add(btnLogIn);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i=new Inspector();
				boolean entro=i.ingresarUsuario(usuario.getText(),passwordField.getText());
				if (entro){
					panel.setVisible(true);
					panelLog.setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null,"Contraseña incorrecta","Error",JOptionPane.ERROR_MESSAGE);
					passwordField.setText("");
				}
			}
		});
	}
	
	
}
