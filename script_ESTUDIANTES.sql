--  5.1 Crear la tabla
CREATE TABLE ESTUDIANTES (
    ID        NUMBER PRIMARY KEY,
    NOMBRE    VARCHAR2(80) NOT NULL,
    APELLIDO  VARCHAR2(80) NOT NULL,
    EDAD      NUMBER(3),
    PROGRAMA  VARCHAR2(100),
    CORREO    VARCHAR2(120)
);

COMMIT;

--5.2 Insertar 40 estudiantes de prueba
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

--5.3 Crear el procedimiento almacenado
--El procedimiento abre un cursor con los primeros 40 estudiantes
--ordenados por ID.
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

COMMIT;

VARIABLE RESULTADO REFCURSOR;

EXEC PR_CONSULTAR_40_ESTUDIANTES(:RESULTADO);

PRINT RESULTADO;

--5.5 Verificar la cantidad de registros.
SELECT COUNT(*) AS TOTAL_ESTUDIANTES FROM ESTUDIANTES;

--Para consultar los datos directamente:
SELECT
    ID,
    NOMBRE,
    APELLIDO,
    EDAD,
    PROGRAMA,
    CORREO
FROM ESTUDIANTES ORDER BY ID;