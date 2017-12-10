import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import quick.dbtable.DBTable;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class VentanaConsultas extends JFrame {

	private JPanel contentPane;
	private JTextField txtConsultas;
	private DBTable tabla;
	private java.sql.Connection con;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaConsultas frame = new VentanaConsultas();
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
	public VentanaConsultas() {
		setBounds(100, 100, 661, 448);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtConsultas = new JTextField();
		txtConsultas.setBounds(10, 11, 507, 59);
		contentPane.add(txtConsultas);
		
		tabla=new DBTable();
		tabla.setBackground(new Color(255, 204, 153));
		contentPane.add(tabla);
		tabla.setBounds(10, 115, 507, 284);
		tabla.setEditable(false);
		
		JButton btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refrescarTabla();
			}
		});
		btnEjecutar.setBounds(428, 81, 89, 23);
		contentPane.add(btnEjecutar);
		

		
		JList atributos = new JList();
		atributos.setForeground(Color.BLACK);
		atributos.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		atributos.setBounds(527, 269, 108, 116);
		contentPane.add(atributos);
		
		JList list = new JList();
		list.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		list.setBounds(527, 11, 108, 236);
		contentPane.add(list);
		ListSelectionModel l=list.getSelectionModel();
		l.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					String tab=(String)list.getSelectedValue();
					rellenarTabla(tab);
					llenarAtributos(tab,atributos);
				}
			}
			
		});

		conectarBD();
		
		cargarTablas(list);
		
	}
	
	 /**
	  * Rellena la lista de atributos cuando se selecciona una tabla
	  * @param tab Nombre de la tabla
	  * @param atributos Lista de atributos a rellenar
	  */
	 protected void llenarAtributos(String tab, JList atributos) {
		try{
			//Muestra atributos de la tabla seleccionada
			java.sql.Statement s=con.createStatement();
			java.sql.ResultSet res=s.executeQuery("DESCRIBE "+tab+";");
			DefaultListModel l=new DefaultListModel();
			while(res.next()){
				l.addElement(res.getString(1));
			}
			atributos.setModel(l);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Completa la DBtable cuando se selecciona una tabla
	 * @param tab Nombre de la tabla de la que se obtendrá la información 
	 */
	protected void rellenarTabla(String tab) {
		 try{
			 //Si se selecciona una tabla se la muestra en la DBTable
			 tabla.setSelectSql("SELECT * FROM "+tab+";");
			 tabla.createColumnModelFromQuery();
			 for (int i = 0; i < tabla.getColumnCount(); i++)
			 {  		   		  
				 if	 (tabla.getColumn(i).getType()==Types.TIME)  
				 {    		 
					 tabla.getColumn(i).setType(Types.CHAR);  
	 	       	 }
				 if	 (tabla.getColumn(i).getType()==Types.DATE)
				 {
					 tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
				 }
				 tabla.getColumn(i).setMaxWidth(250);
				 tabla.getColumn(i).setMinWidth(80);
	         }     	     	  
	   	  tabla.refresh();
   	  }
		 catch(SQLException e){
			 System.out.println(e.getMessage());
		 }
	}

	/**
	 * Completa el JList de tablas con las tablas que se encuentran en el servidor
	 * @param list Lista a completar
	 */
	private void cargarTablas(JList list) {
		 try{
			 //Muestra las tablas de la base de datos
			 String tabl="SHOW TABLES";
			 DefaultListModel modelo=new DefaultListModel();
			 java.sql.Statement s =con.createStatement();
			 java.sql.ResultSet res=s.executeQuery(tabl);
			 while (res.next()){
					modelo.addElement(res.getString(1));
			 }
			 list.setModel(modelo);
		 }
		 catch(SQLException e){
			 System.out.println(e.getMessage());
		 }
	}

	/**
	 * Toma la instrucción mysql que se ingresó en el JTextField y la ejecuta, también muestra la información en la DBTable
	 */
	private void refrescarTabla()
	   {
	      try
	      {    
	    	  //Ejecuta una instruccion mysql en el servidor y muestra por pantalla el resultado si corresponde
	    	  Statement consulta=con.createStatement();
	    	  consulta.execute(this.txtConsultas.getText().trim());
	    	  if (consulta.getResultSet()!=null)
	    		  tabla.refresh(consulta.getResultSet());
	    	  else
	    		 tabla.refresh();
   	    
	    	  for (int i = 0; i < tabla.getColumnCount(); i++)
	    	  { 	   		  
	    		 if	 (tabla.getColumn(i).getType()==Types.TIME)  
	    		 {    		 
	    		  tabla.getColumn(i).setType(Types.CHAR);  
	  	       	 }
	    		 if	 (tabla.getColumn(i).getType()==Types.DATE)
	    		 {
	    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
	    		 }
	    		 tabla.getColumn(i).setMaxWidth(250);
				 tabla.getColumn(i).setMinWidth(80);
	          }    	     	  
	          JOptionPane.showMessageDialog(null,"Consulta realizada con éxito","Éxito",JOptionPane.INFORMATION_MESSAGE);    	  
	       }
	      catch (SQLException ex)
	      {
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	         JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                                       "Error, SQLException: "+ex.getMessage(), 
	                                       "Error al ejecutar la consulta.",
	                                       JOptionPane.ERROR_MESSAGE);
	         
	      }
	      catch(Exception e){
	    	  JOptionPane.showMessageDialog(null,"Consulta realizada con éxito","Éxito",JOptionPane.INFORMATION_MESSAGE);
	      }
	   }
	 
	
	/**
	 * Conecta a java con el servidor 
	 */
	 private void conectarBD()
	   {
	         try
	         {
	            String driver ="com.mysql.jdbc.Driver";
	        	String servidor = "localhost:3306";
	            String baseDatos = "parquimetros";
	            String usuario = "admin";
	            String clave = "admin";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
	    
	            tabla.connectDatabase(driver, uriConexion, usuario, clave);
	            con=java.sql.DriverManager.getConnection(uriConexion,usuario,clave);
	           
	         }
	         catch (SQLException ex)
	         {
	             JOptionPane.showMessageDialog(this,
	                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
	                                           "Error",
	                                           JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	         catch (ClassNotFoundException e)
	         {
	            e.printStackTrace();
	         }
	      
	   }
}
