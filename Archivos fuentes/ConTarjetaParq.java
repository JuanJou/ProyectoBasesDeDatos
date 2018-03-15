import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ConTarjetaParq extends JFrame {

	private JPanel contentPan;
	private Parquimetro parq;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConTarjetaParq frame = new ConTarjetaParq();
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
	public ConTarjetaParq() {
		parq=Parquimetro.getInstancia();
		
		setBounds(450, 350, 418, 182);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		JComboBox CBParq = new JComboBox();
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setEnabled(false);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet r=parq.conectar((String)comboBox.getSelectedItem(),obtenerID((String)CBParq.getSelectedItem()));
					r.next();
					String mensaje=null;
					System.out.println(r.getMetaData().getColumnCount());
					if (r.getString(1).equals("SQLEXCEPTION!, transacci�n abortada")){
						throw new SQLException();
					}
					if (r.getString(1).equals("Error")){
						mensaje="Se produjo un error en, "+r.getString(2);
					}
					else{
						if (r.getString(1).equals("Apertura")){
							if (r.getString(2).equals("Error"))
								mensaje="Saldo insuficiente en la tarjeta, no se abrir� el estacionamiento";
							else
								mensaje="Se realiz� con �xito la apertura del estacionamiento para el autom�vil: "+r.getString(4)+", tiempo restante: "+r.getString(3)+" minutos";
						}
						else{
							if (r.getString(2).equals("Error"))
								mensaje="Se cerr� el estacionamiento, pero lleg� al tope de saldo m�nimo: -999.99";
							else
								mensaje="Se realiz� con �xito el cierre del estacionamiento para el autom�vil: "+r.getString(4)+", saldo restante: $"+r.getString(3)+", transcurrieron: "+r.getString(5)+" minutos";
						}
							
					}
					JOptionPane.showMessageDialog(null,mensaje,"Conectar",JOptionPane.NO_OPTION);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			/**
			 * Recibe el string seleccionado del ComboBox de parquimetros y obtiene el id de parquimetro contenido en el mismo
			 * @param SI String seleccionado
			 * @return String con el valor del id del parquimetro seleccionado
			 */
			private String obtenerID(String SI) {
				boolean encontre=false;
				boolean coma=false;
				int j=0;
				int i=0;
				while(!encontre){
					if(SI.charAt(i)==':'){
						j=i;
						while (!coma){
							coma=SI.charAt(j)==',';
							j++;
						}
						return SI.substring(i+1,j-1);
					}
					i++;
				}
				return null;
			}
		});
		btnConectar.setBounds(266, 36, 88, 49);
		contentPane.add(btnConectar);
		

		
		comboBox.setBounds(106, 101, 123, 20);
		contentPane.add(comboBox);
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnConectar.setEnabled(true);
				
			}
			
		});
		
		
		CBParq.setBounds(106, 36, 123, 20);
		contentPane.add(CBParq);
		CBParq.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					ResultSet r=Admin.getInstancia().obtenerTarjetas();
				
					comboBox.removeAllItems();
					while(r.next()){
						comboBox.addItem(r.getString(1));
					}
				}
				catch(SQLException w){
					w.printStackTrace();
				}
			}
			
		});
		
		JComboBox CBUbicaciones = new JComboBox();
		CBUbicaciones.setMaximumRowCount(20);
		cargarUbicaciones(CBUbicaciones);
		CBUbicaciones.setBounds(106, 8, 123, 20);
		contentPane.add(CBUbicaciones);
		CBUbicaciones.setSelectedIndex(-1);
		CBUbicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					//Se selecciona una ubicacion y se cargan los parquimetros de la misma
					String ub=(String)CBUbicaciones.getSelectedItem();
					String[] txt=transformarUbicacion(ub);
					java.sql.ResultSet r=Admin.getInstancia().obtenerParq(txt[0],txt[1]);
					CBParq.removeAllItems();
					while (r.next()){
						CBParq.addItem("Id:"+r.getString(1)+", Altura"+r.getString(2));
					}
					CBParq.setSelectedIndex(0);
				}
				catch (SQLException e){
					System.out.println(e.getMessage());
				}
			}
			
			/**
			 * Recibe la ubicacion seleccionada del ComboBox y la divide en calle altura
			 * @param ub String seleccionado
			 * @return Arreglo donde el primer elemento es la calle y el segundo la altura
			 */
			private String[] transformarUbicacion(String ub) {
				boolean encontre=false;int i=0;char c;
				String[] ret=new String[2]; 
				while(!encontre){
					c=ub.charAt(i);
					if (c==','){
						ret[0]=ub.substring(0,i);
						ret[1]=ub.substring(i+1);
						return ret;
					}
					i++;
				}
				return null;
			}
		});
		
		
		JLabel lblIdTarjeta = new JLabel("Ubicaci\u00F3n:");
		lblIdTarjeta.setBounds(10, 11, 62, 14);
		contentPane.add(lblIdTarjeta);
		
		JLabel lblIdParquimetro = new JLabel("ID parquimetro:");
		lblIdParquimetro.setBounds(10, 39, 88, 14);
		contentPane.add(lblIdParquimetro);
		
		
		JLabel lblIdTarjeta_1 = new JLabel("ID tarjeta:");
		lblIdTarjeta_1.setBounds(10, 104, 62, 14);
		contentPane.add(lblIdTarjeta_1);
	}
	
	
	/**
	 * Carga en el ComboBox las ubicaciones que se obtienen del servidor
	 * @param CBUbicaciones ComboBox a cargar
	 */
	private void cargarUbicaciones(JComboBox CBUbicaciones) {
		try {
			ResultSet res=Admin.getInstancia().retornarUbicaciones();
			int pos=0;
			while(res.next()){
				CBUbicaciones.addItem(res.getString(2)+","+res.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
