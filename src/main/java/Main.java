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
        
    }
}