package codigo;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static String url = "jdbc:mysql://localhost:3306/ud2_actividadjdbc";
    public static String usuario = "root";
    public static String contrasenya = "";
    private static Connection conexion;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String accion = "";
        do {
            try {
                // Cargar el controlador de la base de datos MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establecer conexión a la base de datos
                conexion = DriverManager.getConnection(url, usuario, contrasenya);

                // Mostrar menú de opciones
                System.out.println("------------------------------------");
                System.out.println("- 1. Añadir un nuevo estudiante     -");
                System.out.println("- 2. Ver el listado de estudiantes  -");
                System.out.println("- 3. Actualizar estudiante          -");
                System.out.println("- 4. Eliminar estudiante            -");
                System.out.println("- 5. Salir                          -");
                System.out.println("------------------------------------");
                accion = sc.next();

                if (accion.equals("1")) {
                    // Añadir un nuevo estudiante
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
                    // Llamar a la función para añadir un alumno
                    int resultado = anyadirAlumno(id, nombre, apellido, edad, curso);

                    if (resultado > 0) {
                        System.out.println("Estudiante añadido correctamente");
                    } else {
                        System.out.println("Error al añadir estudiante");
                    }
                } else if (accion.equals("2")) {
                    // Mostrar el listado de estudiantes
                    mostrarEstudiantes();
                } else if (accion.equals("3")) {
                    // Mostrar el listado de estudiantes antes de actualizar
                    mostrarEstudiantes();
                    System.out.println("Elija el ID del estudiante que quiere actualizar e introduzca los nuevos datos");
                    int id = sc.nextInt();
                    System.out.println("introduzca el nuevo nombre");
                    String nombre = sc.next();
                    System.out.println("introduzca el nuevo apellido");
                    String apellido = sc.next();
                    System.out.println("introduzca la edad");
                    int edad = sc.nextInt();
                    System.out.println("introduzca el curso");
                    String curso = sc.next();
                    // Llamar a la función para actualizar un estudiante
                    actualizarEstudiante(id, nombre, apellido, edad, curso);
                } else if (accion.equals("4")) {
                    // Mostrar el listado de estudiantes antes de eliminar
                    mostrarEstudiantes();
                    System.out.println("Elija el ID del estudiante que quiere eliminar: ");
                    int id = sc.nextInt();
                    // Llamar a la función para eliminar un estudiante
                    eliminarEstudiante(id);
                } else if (accion.equals("5")) {
                    System.out.println("Cerrando base de datos");
                    // Cerrar la conexión a la base de datos
                    conexion.close();
                } else {
                    System.out.println("Por favor introduzca una acción (número) válida");
                    System.out.println("------------------------------------");
                    System.out.println("- 1. Añadir un nuevo estudiante     -");
                    System.out.println("- 2. Ver el listado de estudiantes  -");
                    System.out.println("- 3. Actualizar estudiante          -");
                    System.out.println("- 4. Eliminar estudiante            -");
                    System.out.println("- 5. Salir                          -");
                    System.out.println("------------------------------------");
                }

            } catch (ClassNotFoundException | SQLException e) {
                // Manejar excepciones de clase no encontrada y SQL
                e.printStackTrace();
            }
        } while (!accion.equals("5"));
    }

    public static int anyadirAlumno(int id, String nombre, String apellido, int edad, String curso) {
        int resultado = 0;
        try {
            // Query para insertar un nuevo estudiante
            String query = "INSERT INTO estudiante (ID,Nombre,Apellidos,Edad,Curso) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establecer los parámetros en la consulta
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setInt(4, edad);
            pstmt.setString(5, curso);
            // Ejecutar la consulta
            resultado = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            // Manejar excepciones de SQL (por ejemplo, identificador duplicado)
            System.out.println("Este identificador ya existe");
        }
        return resultado;
    }

    public static void mostrarEstudiantes() {
        try {
            // Query para seleccionar todos los estudiantes
            String query = "SELECT * FROM estudiante";
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Listado de estudiantes:");
            System.out.println("------------------------------------");
            System.out.printf("| %-3s | %-15s | %-15s | %-3s | %-10s |\n", "ID", "Nombre", "Apellidos", "Edad", "Curso");
            System.out.println("------------------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("Nombre");
                String apellido = resultSet.getString("Apellidos");
                int edad = resultSet.getInt("Edad");
                String curso = resultSet.getString("Curso");

                System.out.printf("| %-3d | %-15s | %-15s | %-3d | %-10s |\n", id, nombre, apellido, edad, curso);
            }

            System.out.println("------------------------------------");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            // Manejar excepciones de SQL
            e.printStackTrace();
        }
    }

    public static void actualizarEstudiante(int id, String nuevoNombre, String nuevoApellido, int nuevaEdad,
            String nuevoCurso) {
        try {
            // Query para actualizar un estudiante por ID
            String query = "UPDATE estudiante SET Nombre=?, Apellidos=?, Edad=?, Curso=? WHERE ID=?";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establecer los parámetros en la consulta
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevoApellido);
            pstmt.setInt(3, nuevaEdad);
            pstmt.setString(4, nuevoCurso);
            pstmt.setInt(5, id);

            // Ejecutar la consulta
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
            // Query para eliminar un estudiante por ID
            String query = "DELETE FROM estudiante WHERE ID = ?";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establecer el parámetro en la consulta
            pstmt.setInt(1, id);

            // Ejecutar la consulta
            int resultado = pstmt.executeUpdate();

            if (resultado > 0) {
                // Informar si el estudiante fue eliminado exitosamente
                System.out.println("Estudiante eliminado correctamente");
            } else {
                // Informar si no se encontró ningún estudiante con ese ID
                System.out.println("No se encontró ningún estudiante con ese ID");
            }

            pstmt.close();
        } catch (SQLException e) {
            // Manejar excepciones de SQL
            e.printStackTrace();
        }
    }

}