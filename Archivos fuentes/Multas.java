import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import quick.dbtable.DBTable;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class Multas extends JFrame {

	private JPanel contentPane;
	private Admin A;
	private Inspector i;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multas frame = new Multas(null);
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
	public Multas(Inspector i) {
		setBounds(100, 100, 466, 363);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		a=Admin.getInstancia();
		this.i=i;
		
		DBTable multa=new DBTable();
		multa.setEditable(false);
		multa.setRowCountSql("");
		multa.setBounds(23, 68, 404, 246);
		contentPane.add(multa);
		
		JComboBox CBParq = new JComboBox();
		CBParq.setBounds(150, 23, 117, 20);
		contentPane.add(CBParq);
		
		JButton btnGenerarMultas = new JButton("Generar multas");
		btnGenerarMultas.setEnabled(false);
		btnGenerarMultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    try{	
			    	//Segun el parquimetro seleccionado se generan las multas a las patentes ingresadas si es que corresponde
						String id=obtenerID((String)CBParq.getSelectedItem());	
			    		ResultSet res=i.chequearParquimetro(id);
			    		if (res!=null)
			    			multa.refresh(res);
						 for (int i = 0; i < multa.getColumnCount(); i++)
						 {  		   		  
							 if	 (multa.getColumn(i).getType()==Types.TIME)  
							 {    		 
								 multa.getColumn(i).setType(Types.CHAR);  
				 	       	 }
							 if	 (multa.getColumn(i).getType()==Types.DATE)
							 {
								 multa.getColumn(i).setDateFormat("dd/MM/YYYY");
							 }
							 multa.getColumn(i).setMaxWidth(100);
							 multa.getColumn(i).setMinWidth(80);
				         }     	     	  
				btnGenerarMultas.setEnabled(false);
			    }
				catch(Exception e){
					System.out.println(e.getMessage());
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
		btnGenerarMultas.setBounds(285, 22, 123, 23);
		contentPane.add(btnGenerarMultas);
		
		
		JComboBox CBUbicaciones = new JComboBox();
		CBUbicaciones.setMaximumRowCount(20);
		cargarUbicaciones(CBUbicaciones);
		CBUbicaciones.setBounds(23, 23, 117, 20);
		contentPane.add(CBUbicaciones);
		CBUbicaciones.setSelectedIndex(-1);
		
		JLabel lblUbicaciones = new JLabel("Ubicaciones");
		lblUbicaciones.setForeground(new Color(0, 0, 0));
		lblUbicaciones.setBounds(23,5, 75, 14);
		contentPane.add(lblUbicaciones);
		
		JLabel lblParquimetros = new JLabel("Parquimetros");
		lblParquimetros.setForeground(new Color(0, 0, 0));
		lblParquimetros.setBounds(150, 5, 90, 14);
		contentPane.add(lblParquimetros);
		
		
		CBUbicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					//Se selecciona una ubicacion y se cargan los parquimetros de la misma
					btnGenerarMultas.setEnabled(true);
					String ub=(String)CBUbicaciones.getSelectedItem();
					String[] txt=transformarUbicacion(ub);
					java.sql.ResultSet r=a.obtenerParq(txt[0],txt[1]);
					CBParq.removeAllItems();
					while (r.next()){
						CBParq.addItem("Id:"+r.getString(1)+", Altura"+r.getString(2));
					}
					CBParq.setVisible(true);
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
		
		
		
		
		
	}
	
	/**
	 * Carga en el ComboBox las ubicaciones que se obtienen del servidor
	 * @param CBUbicaciones ComboBox a cargar
	 */
	private void cargarUbicaciones(JComboBox CBUbicaciones) {
		try {
			ResultSet res=a.retornarUbicaciones();
			int pos=0;
			while(res.next()){
				CBUbicaciones.addItem(res.getString(2)+","+res.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
