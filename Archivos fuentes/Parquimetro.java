import java.sql.ResultSet;
import java.sql.SQLException;

public class Parquimetro {

	private java.sql.Connection con;
	private java.sql.Statement consulta;
	private static Parquimetro instancia;
	
	public static Parquimetro getInstancia(){
		if (instancia==null){
			instancia=new Parquimetro();
		}
		return instancia;
	}
	
	private Parquimetro(){
		String servidor = "localhost:3306";
		String baseDatos = "parquimetros";
		String usuario = "parquimetros";
		String clave = "parq";
		String url = "jdbc:mysql://" + servidor + "/" + baseDatos;
		try {
			con=java.sql.DriverManager.getConnection(url,usuario,clave);
			consulta=con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ejecuta el stored procedure conectar almacenado en el servidor
	 * @param id_tarjeta Tarjeta que se quiere conectar
	 * @param id_parquimetro Parquimetro al que se quiere conectar
	 * @return Informacion de cierre o apertura
	 */
	public ResultSet conectar(String id_tarjeta,String id_parquimetro){
		//Ejecutar stored procedure
		try {
			String c="call conectar("+id_tarjeta+","+id_parquimetro+");";
			ResultSet res=consulta.executeQuery(c);
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
