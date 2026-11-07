import config.ConexionOracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("       CONSULTA DE 40 ESTUDIANTES");
        System.out.println("       JAVA 21 - ORACLE DATABASE 19C");
        System.out.println("==============================================");

        consultarEstudiantes();
    }

    private static void consultarEstudiantes() {

        String procedimiento =
                "{call PR_CONSULTAR_40_ESTUDIANTES(?)}";

        int contador = 0;

        try (Connection conexion = ConexionOracle.conectar())
        {
            System.out.println();
            System.out.println("CONEXION EXITOSA A ORACLE 19C");

            try (CallableStatement cs =
                         conexion.prepareCall(procedimiento))
            {
                // REGISTRAR el parametro de salida SYS_REFCURSOR.
                cs.registerOutParameter(1, OracleTypes.CURSOR);

                System.out.println(
                        "Ejecutando procedimiento almacenado...");

                //EJECUTAR el procedimiento PL/SQL!
                cs.execute();

                // RECUPERAR el cursor devuelto por Oracle!
                try (ResultSet rs = (ResultSet) cs.getObject(1))
                {
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

                    while (rs.next())
                    {
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