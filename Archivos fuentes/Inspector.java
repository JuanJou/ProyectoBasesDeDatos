import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

public class Inspector {

	private java.sql.Connection con;
	private java.sql.Statement consulta;
	private List<String> patentes;
	private String legajo;
	
	
	/**
	 * Conecta a java con la base de datos con el usuario inspector
	 */
	public Inspector(){
		patentes=new ArrayList<String>();
		String servidor = "localhost:3306";
		String baseDatos = "parquimetros";
		String usuario = "inspector";
		String clave = "inspector";
		String url = "jdbc:mysql://" + servidor + "/" + baseDatos;
		try {
			con=java.sql.DriverManager.getConnection(url,usuario,clave);
			consulta=con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logea a un inspector
	 * @param legajo Numero de legajo
	 * @param password Contraseña asociada
	 * @return True si se puedo logear exitosamente, False en caso contrario
	 */
	public boolean ingresarUsuario(String legajo, String password) {
		try {
			String c="SELECT password FROM inspectores WHERE legajo="+legajo+";";
			java.sql.ResultSet res=consulta.executeQuery(c);
			res.next();
			String pass=res.getString(1);
			String calc=TestEncriptarMD5.encriptaEnMD5(password);
			if (calc.equals(pass)){
				this.legajo=legajo;
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}
	
	
	/**
	 * Chequea un parquimetro y hace las multas a los autos correspondientes
	 * @param idParquimetro Parquimetro con el que se conecto
	 * @return ResultSet de las multas realizadas
	 * @throws Exception
	 */
	public ResultSet chequearParquimetro(String idParquimetro) throws Exception{
		try {
			String cons;
			
			
			cons="SELECT calle,altura FROM parquimetros WHERE id_parq="+idParquimetro+";";
			java.sql.ResultSet res=consulta.executeQuery(cons);
			res.next();
			String calle=res.getString(1);
			String altura=res.getString(2);
			cons="SELECT id_asociado_con,dia,turno FROM asociado_con WHERE legajo="+legajo+" AND calle='"+calle+"' AND altura="+altura+";";
			res=consulta.executeQuery(cons);
			ResultSet fecha=con.createStatement().executeQuery("SELECT dayofweek(curdate());");
			fecha.next();
			
				if (res.next()){
					String dia,id_asociado_con,turno;
					boolean tieneAsignada=false;
					//Recorro todas las asignaciones que tenga el inspector a la ubicacion ingresada, si es que tiene
					do{
						id_asociado_con=res.getString(1);
						dia=res.getString(2);
						turno=res.getString(3);
						if ((dia.equals(letras_dia(Integer.valueOf(fecha.getString(1))))) && es_turno(turno) ){
							tieneAsignada=true;
							break;
						}
					}while (res.next());
					
					if (!tieneAsignada){
						JOptionPane.showMessageDialog(null,"El inspector no tiene asignada esta ubicación en este día y horario","Error de verificación",JOptionPane.ERROR_MESSAGE);
						return null;
					}
					
					accedioParquimetro(idParquimetro);
					String pat=null;
					Iterator<String> i=patentes.iterator();
					//Recorro todas las patentes ingresadas por el inspector
					//Creo una multa para los que no tengan un estacionamiento abierto en la ubicacion elegida
					while(i.hasNext()){
						pat=i.next();
						cons="SELECT patente FROM estacionados WHERE calle='"+calle+"' AND altura="+altura+" AND patente='"+pat+"';";
						res=consulta.executeQuery(cons);
						if (!res.next()){
							cons="INSERT INTO multa(fecha,hora,patente,id_asociado_con) VALUES (curdate(),curtime(),'"+pat+"',"+id_asociado_con+")";
							consulta.executeUpdate(cons);
						}
					}
					//Retorno las multas que genere
					return consulta.executeQuery("SELECT * FROM multa WHERE fecha=curdate()  AND id_asociado_con="+id_asociado_con+" AND hora BETWEEN subtime(curtime(),'00:00:05') AND curtime();");  
				}
				else{
					JOptionPane.showMessageDialog(null,"El inspector no tiene asignada la ubicación "+calle+", "+altura,"Error",JOptionPane.ERROR_MESSAGE);
					return null;
				}
			} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Chequea si corresponde el turno a el día y horario actual
	 * @param turno M por mañana o T por tarde
	 * @return True si corresponde, Falso en caso contrario
	 */
	private boolean es_turno(String turno) {
		try{
			//Obtengo laa diferencia de la hora actual con las 8 , las 14 y las 20, de esta manera puedo saber si al inspector le corresponde trabajar en este turno
			ResultSet hor=con.createStatement().executeQuery("SELECT time_to_sec(timediff(curtime(),'07:59:00')),time_to_sec(timediff(curtime(),'13:59:00')),time_to_sec(timediff(curtime(),'19:59:00'));");
			hor.next();
			int A1,A2,A3;
			A1=hor.getInt(1);
			A2=hor.getInt(2);
			A3=hor.getInt(3);

			System.out.print(A1+A2);
			//Si A1 es mayor a 0 entonces la hota actual es mayor a las 8, si A2 es menor a 0 entonces la hora actual es menor a las 13:59
			if (turno.equals("M") && A1>0 && A2<0){
				return true;
			}
			else{
				if (turno.equals("T") && A2>0 && A3<0)
					return true;
				else{
					return false;
			}}
		}
		catch(SQLException e){
			return false;
		}
	}

	/**
	 * Devuelve las dos primeras letras del dia numero i
	 * @param i numero de día en la semana
	 * @return "Lu" por lunes, "Ma" por martes,...
	 */
	private String letras_dia(int i) {
		//Recibe el numero del dia de la semana que devuelve el servidos y lo transforma en las dos primeras letras del nombre
		switch(i){
			case Calendar.MONDAY:
				return "Lu";
			case Calendar.TUESDAY:
				return "Ma";
			case Calendar.WEDNESDAY:
				return "Mi";
			case Calendar.THURSDAY:
				return "Ju";
			case Calendar.FRIDAY:
				return "Vi";
			case Calendar.SATURDAY:
				return "Sa";
			default:
				return null;
		}
	}

	public java.sql.ResultSet realizarConsulta(String cons){
		try {
			ResultSet res=consulta.executeQuery(cons);
			return res;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}


	/**
	 * Añade una patente a la lista local de patentes
	 * @param p
	 */
	public void addPatente(String pat) throws Exception {
		if (!patentes.contains(pat))
			patentes.add(pat);
		else
			throw new Exception("Patente ya ingresada");
	}

	/**
	 * Borra una patente de la lista local de patentes
	 * @param pat Patente a eliminar
	 */
	public void removePatente(String pat) {
		patentes.remove(pat);		
	}

	/**
	 * La lista esta vacia
	 * @return True si lo esta, False en caso contrario
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return patentes.isEmpty();
	}

	/**
	 * Registra un acceso del inspector al parquimetro
	 * @param id Id del parquimetro al que se conecto
	 * @throws SQLException 
	 */
	public void accedioParquimetro(String id) throws SQLException {
		//Registra un acceso al parquimetro id
		String c="INSERT INTO accede VALUES ("+legajo+","+id+",curdate(),curtime());";		
		con.createStatement().executeUpdate(c);
	}

	
}
