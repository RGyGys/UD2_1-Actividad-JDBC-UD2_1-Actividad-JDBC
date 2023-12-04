package codigo;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
	public static String url = "jdbc:mysql://localhost:3306/estudiante";
	public static String usuario = "root";
	public static String contrasenya = "";
	private static Connection conexion;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String accion = "";
		accion = sc.next();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, usuario, contrasenya);
			System.out.println("------------------------------------");
			System.out.println("- 1.Añadir un nuevo estudiante     -");
			System.out.println("- 2.Ver el listado de estudiantes  -");
			System.out.println("- 3.Actualizar estudiante          -");
			System.out.println("- 4.Eliminar estudiante            -");
			System.out.println("- 5.Salir                          -");
			System.out.println("------------------------------------");
			accion = sc.next();

			if (accion.equals("1")) {
				System.out.println("Introduzca los datos del nuevo alumno");
				System.out.print("ID del alumno: ");
				int id = sc.nextInt();
				System.out.println();
				System.out.print("Nombre del alumno: ");
				String nombre = sc.next();
				System.out.println();
				System.out.print("Apellido del alumno: ");
				String apellido = sc.next();
				System.out.println();
				System.out.print("Edad del alumno: ");
				int edad = sc.nextInt();
				System.out.println();
				System.out.print("Curso del alumno: ");
				String curso = sc.next();
				System.out.println();
				int resultado = anyadirAlumno(id, nombre, apellido, edad, curso);

				if (resultado > 0) {
					System.out.println("Estudiante añadido correctamente");
				} else {
					System.out.println("Error al añadir estudiante");
				}
			} else if (accion.equals("2")) {
				mostrarEstudiantes();
			} else if (accion.equals("3")) {
				mostrarEstudiantes();
				System.out.println("Elija el estudiante que quiere actualizar e introduzca los nuevos datos");
				int id = sc.nextInt();
				String nombre = sc.next();
				String apellido = sc.next();
				int edad = sc.nextInt();
				String curso = sc.next();
				actualizarEstudiante(id, nombre, apellido, edad, curso);
			} else if (accion.equals("4")) {
				mostrarEstudiantes();
				System.out.println("Elija el ID del estudiante que quiere eliminar: ");
				int id = sc.nextInt();
				eliminarEstudiante(id);
			} else if (accion.equals("5")) {
				System.out.println("Cerrando base de datos");
				conexion.close();
			} else {
				System.out.println("Por favor introduzca una accion (numero) valida");
				System.out.println("------------------------------------");
				System.out.println("- 1.Añadir un nuevo estudiante     -");
				System.out.println("- 2.Ver el listado de estudiantes  -");
				System.out.println("- 3.Actualizar estudiante          -");
				System.out.println("- 4.Eliminar estudiante            -");
				System.out.println("- 5.Salir                          -");
				System.out.println("------------------------------------");
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static int anyadirAlumno(int id, String nombre, String apellido, int edad, String curso) {
		int resultado = 0;
		try {
			String query = "INSERT INTO estudiante (ID,Nombre,Apellido,Edad,Curso) VALUES (?,?,?,?,?,?)";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setString(2, nombre);
			pstmt.setString(3, apellido);
			pstmt.setInt(4, edad);
			pstmt.setString(5, curso);
			resultado = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// System.err.println(e.getMessage());
			System.out.println("Este identificador ya existe");
		}

		return resultado;

	}

	public static void mostrarEstudiantes() {
		try {
			String query = "SELECT * FROM estudiante";
			Statement statement = conexion.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			System.out.println("Listado de estudiantes:");
			System.out.println("------------------------------------");
			System.out.printf("| %-3s | %-15s | %-15s | %-3s | %-10s |\n", "ID", "Nombre", "Apellido", "Edad", "Curso");
			System.out.println("------------------------------------");

			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String nombre = resultSet.getString("Nombre");
				String apellido = resultSet.getString("Apellido");
				int edad = resultSet.getInt("Edad");
				String curso = resultSet.getString("Curso");

				System.out.printf("| %-3d | %-15s | %-15s | %-3d | %-10s |\n", id, nombre, apellido, edad, curso);
			}

			System.out.println("------------------------------------");

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void actualizarEstudiante(int id, String nuevoNombre, String nuevoApellido, int nuevaEdad,
			String nuevoCurso) {
		try {
			String query = "UPDATE estudiante SET Nombre=?, Apellido=?, Edad=?, Curso=? WHERE ID=?";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, nuevoNombre);
			pstmt.setString(2, nuevoApellido);
			pstmt.setInt(3, nuevaEdad);
			pstmt.setString(4, nuevoCurso);
			pstmt.setInt(5, id);

			int resultado = pstmt.executeUpdate();

			if (resultado > 0) {
				System.out.println("Estudiante actualizado correctamente");
			} else {
				System.out.println("No se encontró ningún estudiante con ese ID");
			}

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void eliminarEstudiante(int id) {
		try {
			String query = "DELETE FROM estudiante WHERE ID = ?";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setInt(1, id);

			int resultado = pstmt.executeUpdate();

			if (resultado > 0) {
				System.out.println("Estudiante eliminado correctamente");
			} else {
				System.out.println("No se encontró ningún estudiante con ese ID");
			}

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}