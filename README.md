# UD2_1-Actividad-JDBC-UD2_1-Actividad-JDBC
Actividad de la UD2 de Acceso a datos con JDBC
# Documentación del Código - Gestión de Estudiantes

## Descripción
Este código Java constituye una aplicación de consola para gestionar información de estudiantes en una base de datos MySQL. Proporciona operaciones como añadir, ver, actualizar y eliminar estudiantes.

## Requisitos
- Java JDK 8 o superior.
- Servidor MySQL en ejecución.
- Conector MySQL JDBC.

## Configuración
1. Asegúrate de tener Java y MySQL instalados.
2. Descarga e importa el proyecto en tu entorno de desarrollo.
3. Ajusta la configuración de la base de datos en `Main.java`.

## Uso
1. Ejecuta la clase `Main.java`.
2. Selecciona opciones del menú para interactuar con la base de datos.
3. Sigue las instrucciones en la consola para realizar operaciones.

## Estructura del Código
- `Main.java`: Contiene la lógica principal del programa, incluyendo el menú y las interacciones con la base de datos.
- `DatabaseConfig.java`: Configuración de conexión a la base de datos.
- Métodos adicionales: `anyadirAlumno`, `mostrarEstudiantes`, `actualizarEstudiante`, `eliminarEstudiante`.

## Base de Datos
- Nombre de la base de datos: `ud2_actividadjdbc`.
- Tabla: `estudiante` con columnas `ID`, `Nombre`, `Apellidos`, `Edad`, `Curso`.

## Dependencias
- MySQL Connector/J: [Enlace](https://dev.mysql.com/downloads/connector/j/)
