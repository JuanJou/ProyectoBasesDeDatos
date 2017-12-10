import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
	
	private java.sql.Connection con;
	private java.sql.Statement consulta;
	private static Admin instancia;
	
	
	
	public static Admin getInstancia(){
		if (instancia==null){
			instancia=new Admin();
			return instancia;
		}
		else{
			return instancia;
		}
		
	}
	/**
	 * Conecta a java con la base de datos como usuario administrador, obtiene un Statement para realizar consultas al servidor
	 */
	private Admin(){
			String servidor = "localhost:3306";
			String baseDatos = "parquimetros";
			String usuario = "admin";
			String clave = "admin";
			String url = "jdbc:mysql://" + servidor + "/" + baseDatos;
			try {
				con=java.sql.DriverManager.getConnection(url,usuario,clave);
				consulta=con.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Recibe una instruccion y le hace la consulta al servidor
	 * @param cons String que contiene la instruccion
	 * @return ResultSet con los resultados obtenidos
	 */
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
	 * Retorna las ubicaciones cargadas en la base de datos
	 * @return ResultSet con las ubicaciones
	 * @throws SQLException
	 */
	public ResultSet retornarUbicaciones() throws SQLException {
			String cons="SELECT * FROM ubicaciones;";
			return consulta.executeQuery(cons);
	}

	/**
	 * Retorna los parquimetros que se encuentran en la calle y altura pasadas por parametro
	 * @param calle Nombre de la calle
	 * @param altura 
	 * @return ResultSet con los id_parq,numero resultantes 
	 * @throws SQLException
	 */
	public ResultSet obtenerParq(String calle, String altura) throws SQLException {
		String cons="SELECT id_parq,numero FROM parquimetros WHERE altura='"+altura+"' AND calle='"+calle+"';";
		java.sql.ResultSet res=consulta.executeQuery(cons);
		return res;
	}

	/**
	 * Chequea que una patente este registrada en la base de datos
	 * @param pat Patente del automovil
	 * @return True si el auto pertenece al conjunto de autos registrados, falso en caso contrario
	 * @throws SQLException
	 */
	public boolean estaPatente(String pat) throws SQLException {
		//Chequea que la patente pat sea de un auto registrado, ya que si no lo es no se podra labrar una multa.
		String check="SELECT patente FROM automoviles WHERE patente='"+pat+"';";
		ResultSet s=consulta.executeQuery(check);
		return s.next();
	}
	public ResultSet obtenerTarjetas() throws SQLException {
		String cons="SELECT id_tarjeta FROM tarjetas;";
		return con.createStatement().executeQuery(cons);
	}
}
