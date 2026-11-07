# -_java_oracle19C_consulta_40_estudiantes_- :.
JavaOracle19C_Consulta40Estudiantes:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/490175e1-9516-4537-ab85-a6ddee7b04e1" />  

<img width="2556" height="1076" alt="image" src="https://github.com/user-attachments/assets/2abbb259-9843-4b86-b566-c6312ce090b1" />  

<img width="2552" height="1078" alt="image" src="https://github.com/user-attachments/assets/0d0b39bb-8f79-47c6-a8b3-6147f050a93d" />  

<img width="2544" height="1076" alt="image" src="https://github.com/user-attachments/assets/30257d4e-b47b-4779-b386-c80ff0aed6e5" />  

```

Aplicación de consola desarrollada en Java 21, Maven e IntelliJ IDEA
para consultar hasta 40 estudiantes almacenados en Oracle Database 19c, mediante
un procedimiento almacenado PL/SQL que devuelve los datos usando SYS_REFCURSOR .

1. Características
- Conexión a Oracle 19c mediante JDBC.
- Ejecución de un procedimiento almacenado con CallableStatement.
- Recuperación de resultados mediante SYS_REFCURSOR.
- Consulta de hasta 40 estudiantes.
- Visualización de los datos en consola.
- Manejo básico de excepciones SQL.
- Datos de prueba generados directamente en Oracle.

2. Tecnologías
  Tecnología        Versión / uso
  Java              21
Oracle Database   19c
Maven             Gestión de dependencias
JDBC              Conexión y ejecución SQL
ojdbc11           23.6.0.24.10
IntelliJ IDEA     IDE recomendado
SQL Developer     Ejecución de scripts

3. Estructura del proyecto
JavaOracle19C_Consulta40Estudiantes/
├── pom.xml
├── sql/
│   └── estudiantes.sql
└── src/
    └── main/
        └── java/
            ├── config/
            │   └── ConexionOracle.java
            └── Main.java

4. Requisitos previos
  1. Tener instalado Java JDK 21.
  2. Tener instalado IntelliJ IDEA.
  3. Tener Oracle Database 19c en ejecución.
  4. Tener acceso a un esquema de Oracle con permisos para crear tablas y
   procedimientos.
  5. Tener SQL Developer u otra herramienta para ejecutar scripts SQL.
  6. Conocer el usuario, contraseña, host, puerto y nombre del servicio
   Oracle.

Importante: los ejemplos usan localhost:1521/orcl. Ajusta la URL
según la configuración real de tu base de datos. Es recomendable crear
un usuario de aplicación en lugar de utilizar SYSTEM.

5. Script completo de Oracle 19c
Crear el archivo sql/estudiantes.sql y ejecutar su contenido conectado
al esquema donde se almacenarán los datos.

5.1 Crear la tabla
CREATE TABLE ESTUDIANTES (
    ID        NUMBER PRIMARY KEY,
    NOMBRE    VARCHAR2(80) NOT NULL,
    APELLIDO  VARCHAR2(80) NOT NULL,
    EDAD      NUMBER(3),
    PROGRAMA  VARCHAR2(100),
    CORREO    VARCHAR2(120)
);

Si la tabla ya existe, no ejecutes nuevamente el CREATE TABLE. Para
reiniciar los datos de prueba puedes ejecutar:
DELETE FROM ESTUDIANTES;
COMMIT;

5.2 Insertar 40 estudiantes de prueba
INSERT INTO ESTUDIANTES (
    ID,
    NOMBRE,
    APELLIDO,
    EDAD,
    PROGRAMA,
    CORREO
)
SELECT
    LEVEL,
    'Estudiante' || LEVEL,
    'Apellido' || LEVEL,
    18 + MOD(LEVEL, 15),
    CASE MOD(LEVEL, 4)
        WHEN 0 THEN 'Ingenieria de Sistemas'
        WHEN 1 THEN 'Administracion'
        WHEN 2 THEN 'Contaduria'
        ELSE 'Ingenieria Industrial'
    END,
    'estudiante' || LEVEL || '@correo.com'
FROM DUAL
CONNECT BY LEVEL <= 40;

COMMIT;
Este bloque inserta 40 registros. Si lo ejecutas más de una vez sin
limpiar la tabla, se producirán errores por claves primarias
duplicadas.

5.3 Crear el procedimiento almacenado
El procedimiento abre un cursor con los primeros 40 estudiantes
ordenados por ID.
CREATE OR REPLACE PROCEDURE PR_CONSULTAR_40_ESTUDIANTES (
    P_RESULTADO OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN P_RESULTADO FOR
        SELECT
            ID,
            NOMBRE,
            APELLIDO,
            EDAD,
            PROGRAMA,
            CORREO
        FROM ESTUDIANTES
        ORDER BY ID
        FETCH FIRST 40 ROWS ONLY;
END PR_CONSULTAR_40_ESTUDIANTES;
/

5.4 Probar el procedimiento desde SQL Developer
Ejecuta estos comandos en una ventana compatible con variables de
SQL*Plus:
VARIABLE RESULTADO REFCURSOR;

EXEC PR_CONSULTAR_40_ESTUDIANTES(:RESULTADO);

PRINT RESULTADO;

5.5 Verificar la cantidad de registros
SELECT COUNT(*) AS TOTAL_ESTUDIANTES
FROM ESTUDIANTES;
Para consultar los datos directamente:
SELECT
    ID,
    NOMBRE,
    APELLIDO,
    EDAD,
    PROGRAMA,
    CORREO
FROM ESTUDIANTES
ORDER BY ID;

6. Configuración Maven
Archivo pom.xml:
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.oracle.estudiantes</groupId>
    <artifactId>JavaOracle19C_Consulta40Estudiantes</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.6.0.24.10</version>
        </dependency>
    </dependencies>

</project>

7. Código Java completo
7.1 Clase de conexión
Ruta: src/main/java/config/ConexionOracle.java
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@//localhost:1521/orcl";

    private static final String USUARIO = "system";

    // Reemplazar por la contraseña del usuario de Oracle.
    private static final String PASSWORD = "TU_PASSWORD";

    private ConexionOracle() {
        // Evita instanciar esta clase de utilidad.
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}

Configuración: reemplaza TU_PASSWORD y, si es necesario, modifica
la URL, el usuario y el puerto. Por ejemplo, para otro servicio Oracle,
utiliza el formato:
jdbc:oracle:thin:@//HOST:PUERTO/NOMBRE_SERVICIO
En un entorno real, no guardes credenciales directamente en el código
fuente. Utiliza variables de entorno o un gestor seguro de secretos.

7.2 Clase principal
Ruta: src/main/java/Main.java
import config.ConexionOracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("==============================================");
        System.out.println("       CONSULTA DE 40 ESTUDIANTES");
        System.out.println("       JAVA 21 - ORACLE DATABASE 19C");
        System.out.println("==============================================");

        consultarEstudiantes();
    }

    public static void consultarEstudiantes() {

        String procedimiento =
                "{call PR_CONSULTAR_40_ESTUDIANTES(?)}";

        int contador = 0;

        try (Connection conexion = ConexionOracle.conectar()) {

            System.out.println();
            System.out.println("CONEXION EXITOSA A ORACLE 19C");

            try (CallableStatement cs =
                         conexion.prepareCall(procedimiento)) {

                // Registrar el parámetro de salida SYS_REFCURSOR.
                cs.registerOutParameter(
                        1,
                        OracleTypes.CURSOR
                );

                System.out.println(
                        "Ejecutando procedimiento almacenado..."
                );

                // Ejecutar el procedimiento PL/SQL.
                cs.execute();

                // Recuperar el cursor devuelto por Oracle.
                try (ResultSet rs =
                             (ResultSet) cs.getObject(1)) {

                    System.out.println();
                    System.out.println(
                            "=============================================================="
                    );
                    System.out.println(
                            "                  LISTADO DE ESTUDIANTES"
                    );
                    System.out.println(
                            "=============================================================="
                    );

                    while (rs.next()) {

                        contador++;

                        int id = rs.getInt("ID");
                        String nombre = rs.getString("NOMBRE");
                        String apellido = rs.getString("APELLIDO");
                        int edad = rs.getInt("EDAD");
                        String programa = rs.getString("PROGRAMA");
                        String correo = rs.getString("CORREO");

                        System.out.println();
                        System.out.println(
                                "ESTUDIANTE No. " + contador
                        );
                        System.out.println(
                                "----------------------------------------------"
                        );
                        System.out.println("ID:        " + id);
                        System.out.println("Nombre:    " + nombre);
                        System.out.println("Apellido:  " + apellido);
                        System.out.println("Edad:      " + edad);
                        System.out.println("Programa:  " + programa);
                        System.out.println("Correo:    " + correo);
                    }
                }
            }

            System.out.println();
            System.out.println(
                    "=============================================================="
            );
            System.out.println(
                    "TOTAL DE ESTUDIANTES CONSULTADOS: " + contador
            );
            System.out.println(
                    "=============================================================="
            );

            if (contador == 40) {
                System.out.println(
                        "CONSULTA EXITOSA: SE RECUPERARON LOS 40 ESTUDIANTES."
                );
            } else {
                System.out.println(
                        "CONSULTA FINALIZADA: SE RECUPERARON "
                                + contador + " REGISTROS."
                );
            }

        } catch (SQLException e) {

            System.err.println();
            System.err.println("ERROR AL CONSULTAR ESTUDIANTES");
            System.err.println("Código Oracle/JDBC: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Descripción: " + e.getMessage());

        }
    }
}

8. Ejecución desde IntelliJ IDEA
  1. Abrir IntelliJ IDEA.
  2. Seleccionar New Project → Maven.
  3. Asignar el nombre JavaOracle19C_Consulta40Estudiantes.
  4. Seleccionar JDK 21.
  5. Crear el paquete config.
  6. Crear la clase ConexionOracle.
  7. Crear la clase Main en el paquete raíz.
  8. Copiar el contenido del archivo pom.xml.
  9. Abrir el panel Maven y seleccionar Reload All Maven Projects.
  10. Abrir Oracle SQL Developer y ejecutar sql/estudiantes.sql.
  11. Ajustar las credenciales de conexión en ConexionOracle.java.
  12. Ejecutar la clase Main.

9. Resultado esperado en consola
Los valores mostrados corresponden a los datos de prueba generados por
el script SQL.
==============================================
       CONSULTA DE 40 ESTUDIANTES
       JAVA 21 - ORACLE DATABASE 19C
==============================================

CONEXION EXITOSA A ORACLE 19C
Ejecutando procedimiento almacenado...

==============================================================
                  LISTADO DE ESTUDIANTES
==============================================================

ESTUDIANTE No. 1
----------------------------------------------
ID:        1
Nombre:    Estudiante1
Apellido:  Apellido1
Edad:      19
Programa:  Administracion
Correo:    estudiante1@correo.com

ESTUDIANTE No. 2
----------------------------------------------
ID:        2
Nombre:    Estudiante2
Apellido:  Apellido2
Edad:      20
Programa:  Contaduria
Correo:    estudiante2@correo.com

...

ESTUDIANTE No. 40
----------------------------------------------
ID:        40
Nombre:    Estudiante40
Apellido:  Apellido40
Edad:      28
Programa:  Ingenieria de Sistemas
Correo:    estudiante40@correo.com

==============================================================
TOTAL DE ESTUDIANTES CONSULTADOS: 40
==============================================================
CONSULTA EXITOSA: SE RECUPERARON LOS 40 ESTUDIANTES.

10. Solución de problemas frecuentes
  Error                                  Posible solución
  ORA-01017                            Verificar usuario y contraseña.

  ORA-12541                            Comprobar que el listener de Oracle
esté activo y que el puerto sea
correcto.

  ORA-12514                            Verificar el nombre del servicio
Oracle en la URL JDBC.

  ORA-00942                            Comprobar que la tabla exista en el
esquema conectado.

  PLS-00201                            Verificar que el procedimiento
exista y que el usuario tenga
permisos de ejecución.

  ORA-06550                            Revisar la sintaxis y compilación
del procedimiento.
package oracle.jdbc does not exist   Actualizar las dependencias Maven y

verificar que ojdbc11 se haya
descargado.

Para verificar la compilación del procedimiento:
SHOW ERRORS PROCEDURE PR_CONSULTAR_40_ESTUDIANTES;

Para verificar que el procedimiento existe:
SELECT OBJECT_NAME, STATUS
FROM USER_OBJECTS
WHERE OBJECT_NAME = 'PR_CONSULTAR_40_ESTUDIANTES';

11. Flujo de funcionamiento
INICIO
   |
   v
Conectar con Oracle 19c
   |
   v
Preparar CallableStatement
   |
   v
Registrar parámetro SYS_REFCURSOR
   |
   v
Ejecutar procedimiento almacenado
   |
   v
Recibir cursor de Oracle
   |
   v
Recorrer los registros con ResultSet
   |
   v
Imprimir información de estudiantes
   |
   v
Mostrar total consultado
   |
   v
Cerrar recursos JDBC
   |
   v
FIN

12. Consideraciones técnicas
- El procedimiento utiliza FETCH FIRST 40 ROWS ONLY, por lo que
  devuelve como máximo 40 estudiantes.
- Si la tabla contiene menos de 40 estudiantes, el programa mostrará
  los registros disponibles.
- El cursor se cierra mediante try-with-resources.
- La conexión y el CallableStatement también se cierran
  automáticamente.
- La consulta es de solo lectura y no modifica los registros de
  estudiantes.
- La tabla y el procedimiento deben estar en el esquema del usuario
  conectado, o deben existir los permisos y referencias
  correspondientes.
- La versión del controlador JDBC indicada es 23.6.0.24.10; si se
  utiliza otra versión aprobada en el entorno, se puede cambiar en el
  pom.xml.

13. Autor y descripción
Proyecto: Consulta de 40 estudiantes mediante procedimiento
almacenado.
Lenguaje: Java 21.
Base de datos: Oracle Database 19c.
Entorno de desarrollo: IntelliJ IDEA.

Objetivo: demostrar la integración entre una aplicación Java de
consola y Oracle Database 19c mediante JDBC y procedimientos almacenados
PL/SQL.
:. . / .
