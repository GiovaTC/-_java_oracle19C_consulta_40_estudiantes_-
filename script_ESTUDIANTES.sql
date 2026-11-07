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