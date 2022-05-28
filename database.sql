CREATE DATABASE testing; /*NOMBRE ASOCIADO AL BACKEND - PROPERTIES*/

DROP TABLE IF EXISTS NIVEL;
CREATE TABLE NIVEL
(
ID_NIVEL INTEGER,
DESCNIVEL VARCHAR(50) NOT NULL,
ESTADO CHAR(1) NOT NULL,
USUARIOREG VARCHAR(10),
FECHAREG TIMESTAMP,
USUARIOUPD VARCHAR(10),
FECHAUPD TIMESTAMP,
CONSTRAINT PK_ID_NIVEL PRIMARY KEY(ID_NIVEL)
);

DROP TABLE IF EXISTS PERFIL;
CREATE TABLE PERFIL
(
ID_PERFIL INTEGER,
ID_NIVEL INTEGER NOT NULL,
DESCPERFIL VARCHAR(50) NOT NULL,
ESTADO CHAR(1) NOT NULL,
USUARIOREG VARCHAR(10),
FECHAREG TIMESTAMP,
USUARIOUPD VARCHAR(10),
FECHAUPD TIMESTAMP,
CONSTRAINT PK_ID_PERFIL PRIMARY KEY(ID_PERFIL),
CONSTRAINT FK_PERFIL_ID_NIVEL FOREIGN KEY(ID_NIVEL) REFERENCES NIVEL(ID_NIVEL)
);

DROP TABLE IF EXISTS EMPLEADO;
CREATE TABLE EMPLEADO 
(
ID_EMPLEADO INTEGER,
ID_PERFIL INTEGER NOT NULL,
ESTADO CHAR(1) NOT NULL,
NOMBRE VARCHAR(50) NOT NULL,
AP_PATERNO VARCHAR(30) NOT NULL,
AP_MATERNO VARCHAR(30) NOT NULL,
CONSTRAINT PK_ID_EMPLEADO PRIMARY KEY(ID_EMPLEADO),
CONSTRAINT FK_EMPLEADO_ID_PERFIL FOREIGN KEY(ID_PERFIL) REFERENCES PERFIL(ID_PERFIL)
);

DROP TABLE IF EXISTS USUARIO;
CREATE TABLE USUARIO
(
ID_USUARIO INTEGER,
ID_EMPLEADO INTEGER,
USUARIO_NICK VARCHAR(10) UNIQUE,
USUARIO_CLAVE VARCHAR(100) NOT NULL,
CLAVE2 VARCHAR(100),
ESTADO CHAR(1) NOT NULL,
USUARIOREG VARCHAR(10),
FECHAREG TIMESTAMP,
USUARIOUPD VARCHAR(10),
FECHAUPD TIMESTAMP,
USUARIO_ACTIVA VARCHAR(10),
FECHA_ACTIVACION TIMESTAMP,
USUARIO_DESACTIVA VARCHAR(10),
FECHA_DESACTIVACION TIMESTAMP,
LASTPASSWORDRESETDATE TIMESTAMP,
CONSTRAINT PK_ID_USUARIO PRIMARY KEY(ID_USUARIO),
CONSTRAINT FK_USUARIO_ID_EMPLEADO FOREIGN KEY(ID_EMPLEADO) REFERENCES EMPLEADO(ID_EMPLEADO)
);

DROP TABLE IF EXISTS OPERACION;
CREATE TABLE OPERACION
(
ID_OPERACION INTEGER,
DESCOPERACION VARCHAR(50) NOT NULL,
ESTADO CHAR(1) NOT NULL,
USUARIOREG VARCHAR(10),
FECHAREG TIMESTAMP,
USUARIOUPD VARCHAR(10),
FECHAUPD TIMESTAMP,
CONSTRAINT PK_ID_OPERACION PRIMARY KEY(ID_OPERACION)
);

/*************************/
/*****  SECUENCIAS **********/
/*************************/
DROP sequence IF EXISTS sec_id_nivel;
create sequence sec_id_nivel
  start with 1
  increment by 1;

DROP sequence IF EXISTS sec_id_perfil;
create sequence sec_id_perfil
  start with 1
  increment by 1;

DROP sequence IF EXISTS sec_id_operacion;
create sequence sec_id_operacion
  start with 1
  increment by 1;

/*************************/
/*****  INSERT **********/
/*************************/

INSERT INTO NIVEL VALUES(nextval('sec_id_nivel'), 'SEGURIDAD', 'A', null, null, null, null);
INSERT INTO PERFIL VALUES(nextval('sec_id_perfil'), 1, 'ADMINISTRADOR', 'A', null, null, null, null);
INSERT INTO EMPLEADO VALUES(1, 1, 'A', 'JONATHAN', 'MAZA', 'VILCHEZ');
INSERT INTO USUARIO VALUES(1, 1, 'SISTEMAS', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 
														null, 't', null, null, null, null, null, null, null, null, null);
INSERT INTO OPERACION VALUES(nextval('sec_id_operacion'), 'SELECCIONAR', 'A', null, null, null, null);
/*************************/
/*****  FUNCIONES **********/
/*************************/

CREATE OR REPLACE FUNCTION ACCESO_LOGIN 
(
	_usu VARCHAR
)
RETURNS refcursor AS $$
DECLARE
	cabecera refcursor;
BEGIN

	open cabecera for 

	SELECT usu.id_usuario, usu.usuario_nick, usu.usuario_clave, usu.estado, 
					emp.id_empleado, emp.nombre, emp.ap_paterno, emp.ap_materno,
					per.id_perfil, per.descperfil, niv.id_nivel, niv.descnivel
		  FROM usuario usu
				INNER JOIN empleado emp ON emp.id_empleado=usu.id_empleado
				INNER JOIN perfil per ON per.id_perfil=emp.id_perfil
				INNER JOIN nivel niv ON niv.id_nivel=per.id_nivel
		  WHERE usu.usuario_nick = _usu;

RETURN cabecera;
END;
$$
LANGUAGE 'plpgsql' VOLATILE;

/**********  NIVEL ***************************************************************************************/

CREATE 
	OR REPLACE FUNCTION CONFIG_SQL_NIVEL ( _idNivel INTEGER, _descripcion VARCHAR, _estado VARCHAR, _start VARCHAR, _length VARCHAR, _orderBy VARCHAR ) RETURNS refcursor AS $$ DECLARE
	qry TEXT;
REF refcursor;
cadenaIdNivel TEXT;
cadenaEstado TEXT;
cadenaDescripcion TEXT;
BEGIN
	IF
		( _idNivel <> 0 ) THEN
			cadenaIdNivel := ' AND ID_NIVEL = ''' || _idNivel || '''  ';
		ELSE cadenaIdNivel := '';
		
	END IF;
	IF
		( _estado IS NOT NULL ) THEN
			cadenaEstado := ' AND ESTADO = ''' || _estado || '''  ';
		ELSE cadenaEstado := '';
		
	END IF;
	IF
		( _descripcion IS NOT NULL ) THEN
			cadenaDescripcion := ' AND UPPER(DESCNIVEL) LIKE ''%' || UPPER ( _descripcion ) || '%'' ';
		ELSE cadenaDescripcion := '';
		
	END IF;
	qry := ' SELECT * FROM (SELECT ID_NIVEL, DESCNIVEL, ESTADO, ROW_NUMBER () OVER (ORDER BY ID_NIVEL) AS rnum 
	FROM NIVEL WHERE 1=1 ' || cadenaIdNivel || cadenaDescripcion || cadenaEstado || ' ) TB ';
	IF
		( _start IS NOT NULL AND _length IS NOT NULL ) THEN
		IF
			( _start = 'all' AND _length = 'all' ) THEN
				qry := qry || ' ORDER BY ' || _orderBy;
			ELSE qry := qry || ' WHERE TB.RNUM >= ' || _start || '+1 AND TB.RNUM <= ' || _start || ' + ' || _length || ' ORDER BY ' || _orderBy;
			
		END IF;
		ELSE qry := ' SELECT COUNT(ID_NIVEL) AS CANTIDAD FROM NIVEL
		WHERE 1=1 ' || cadenaIdNivel || cadenaDescripcion || cadenaEstado;
		
	END IF;
	OPEN REF FOR EXECUTE qry;
	RETURN ( REF );
	
END;
$$ LANGUAGE'plpgsql' VOLATILE;

CREATE OR REPLACE FUNCTION CONFIG_IUD_NIVEL
(
	_idNivel INTEGER,
	_descripcion VARCHAR,
	_estado VARCHAR,
	_usuario VARCHAR,
	_operacion INTEGER
)
  RETURNS INTEGER AS $$
DECLARE
	conteo integer;
BEGIN

	IF(_operacion = 1)THEN	
			SELECT COUNT(*) INTO conteo FROM NIVEL WHERE DESCNIVEL = _descripcion;
			IF(conteo = 0)THEN
					INSERT INTO NIVEL
								(id_nivel,descnivel,estado,usuarioreg,fechareg)
						VALUES 
								(nextval('sec_id_nivel'),_descripcion,_estado,_usuario,CURRENT_DATE);
								IF FOUND THEN
									RETURN 1;
								END IF;
			ELSE
					RETURN 100; --REGISTRO YA EXISTE
			END IF;
	
	ELSIF(_operacion = 2)THEN
			SELECT COUNT(*) INTO conteo FROM NIVEL WHERE DESCNIVEL = _descripcion;
			IF(conteo = 0)THEN
					UPDATE NIVEL
						SET descnivel = _descripcion, usuarioupd = _usuario, fechaupd = CURRENT_DATE
						where id_nivel = _idNivel;
								IF FOUND THEN
									RETURN 2;
								END IF;
			ELSE
					RETURN 100; --REGISTRO YA EXISTE
			END IF;
				
	ELSIF(_operacion = 3)THEN
			UPDATE NIVEL
				SET estado = _estado, usuarioupd = _usuario, fechaupd = CURRENT_DATE
				where id_nivel = _idNivel;
						IF FOUND THEN
							RETURN 3;
					 END IF;

	END IF;
/*
   IF FOUND THEN
      RETURN 1;
   ELSE
      RETURN 0;
   END IF;
*/
	 IF NOT FOUND THEN
      RETURN 0;
   END IF;
END
$$ 
LANGUAGE 'plpgsql';

/**********  PERFIL ***************************************************************************************/

CREATE 
	OR REPLACE FUNCTION CONFIG_SQL_PERFIL ( _idPerfil INTEGER, _idNivel INTEGER, _descripcion VARCHAR, _estado VARCHAR, _start VARCHAR, _length VARCHAR, _orderBy VARCHAR ) RETURNS refcursor AS $$ DECLARE
	qry TEXT;
REF refcursor;
cadenaIdPerfil TEXT;
cadenaIdNivel TEXT;
cadenaEstado TEXT;
cadenaDescripcion TEXT;
BEGIN
	IF
		( _idPerfil <> 0 ) THEN
			cadenaIdPerfil := ' AND PE.ID_PERFIL = ''' || _idPerfil || '''  ';
		ELSE cadenaIdPerfil := '';
		
	END IF;
	IF
		( _idNivel <> 0 ) THEN
			cadenaIdNivel := ' AND PE.ID_NIVEL = ''' || _idNivel || '''  ';
		ELSE cadenaIdNivel := '';
		
	END IF;
	IF
		( _estado IS NOT NULL ) THEN
			cadenaEstado := ' AND PE.ESTADO = ''' || _estado || '''  ';
		ELSE cadenaEstado := '';
		
	END IF;
	IF
		( _descripcion IS NOT NULL ) THEN
			cadenaDescripcion := ' AND UPPER(PE.DESCPERFIL) LIKE ''%' || UPPER ( _descripcion ) || '%'' ';
		ELSE cadenaDescripcion := '';
		
	END IF;
	qry := ' SELECT * FROM (SELECT PE.ID_PERFIL, PE.DESCPERFIL, PE.ESTADO, NI.ID_NIVEL, NI.DESCNIVEL, 
	(SELECT COUNT(*) FROM USUARIO U INNER JOIN EMPLEADO E ON U.ID_EMPLEADO=E.ID_EMPLEADO INNER JOIN PERFIL PER ON PER.ID_PERFIL=E.ID_PERFIL
	WHERE U.ESTADO=''t'' AND PER.ID_PERFIL=PE.ID_PERFIL) AS UDSER_ACTIVOS,
	(SELECT COUNT(*) FROM USUARIO U INNER JOIN EMPLEADO E ON U.ID_EMPLEADO=E.ID_EMPLEADO INNER JOIN PERFIL PER ON PER.ID_PERFIL=E.ID_PERFIL
	WHERE U.ESTADO=''f'' AND PER.ID_PERFIL=PE.ID_PERFIL) AS UDSER_INACTIVOS,
	ROW_NUMBER () OVER (ORDER BY PE.ID_PERFIL) AS rnum 
	FROM PERFIL PE INNER JOIN NIVEL NI ON PE.ID_NIVEL=NI.ID_NIVEL WHERE 1=1 ' || cadenaIdPerfil || cadenaIdNivel || cadenaDescripcion || cadenaEstado || ' ) TB ';
	IF
		( _length IS NOT NULL AND _start IS NOT NULL ) THEN
		IF
			( _start = 'all' AND _length = 'all' ) THEN
				qry := qry || ' ORDER BY ' || _orderBy;
			ELSE qry := qry || ' WHERE TB.RNUM >= ' || _start || '+1 AND TB.RNUM <= ' || _start || ' + ' || _length || ' ORDER BY ' || _orderBy;
			
		END IF;
		ELSE qry := ' SELECT COUNT(PE.ID_PERFIL) AS CANTIDAD FROM PERFIL PE INNER JOIN NIVEL NI ON PE.ID_NIVEL=NI.ID_NIVEL
		WHERE 1=1 ' || cadenaIdPerfil || cadenaIdNivel || cadenaDescripcion || cadenaEstado;
		
	END IF;
	OPEN REF FOR EXECUTE qry;
	RETURN ( REF );
	
END;
$$ LANGUAGE'plpgsql' VOLATILE;

CREATE OR REPLACE FUNCTION CONFIG_IUD_PERFIL
(
	_idPerfil INTEGER,
	_idNivel INTEGER,
	_descripcion VARCHAR,
	_estado VARCHAR,
	_usuario VARCHAR,
	_operacion INTEGER
)
  RETURNS INTEGER AS $$
DECLARE
	conteo integer;
BEGIN

	IF(_operacion = 1)THEN	
			SELECT COUNT(*) INTO conteo FROM PERFIL WHERE DESCPERFIL = _descripcion;
			IF(conteo = 0)THEN
					INSERT INTO PERFIL
								(id_perfil,id_nivel,descperfil,estado,usuarioreg,fechareg)
						VALUES 
								(nextval('sec_id_perfil'),_idNivel,_descripcion,_estado,_usuario,CURRENT_DATE);
								IF FOUND THEN
									RETURN 1;
								END IF;
			ELSE
					RETURN 100; --REGISTRO YA EXISTE
			END IF;
	
	ELSIF(_operacion = 2)THEN
			SELECT COUNT(*) INTO conteo FROM PERFIL WHERE DESCPERFIL = _descripcion AND id_nivel = _idNivel;
			IF(conteo = 0)THEN
					UPDATE PERFIL
						SET descperfil = _descripcion, id_nivel = _idNivel, usuarioupd = _usuario, fechaupd = CURRENT_DATE
						where id_perfil = _idPerfil;
								IF FOUND THEN
									RETURN 2;
								END IF;
			ELSE
					RETURN 100; --REGISTRO YA EXISTE
			END IF;
				
	ELSIF(_operacion = 3)THEN
			UPDATE PERFIL
					SET estado = _estado, usuarioupd = _usuario, fechaupd = CURRENT_DATE
					where id_perfil = _idPerfil;
							IF FOUND THEN
								RETURN 3;
						 END IF;

	END IF;

	 IF NOT FOUND THEN
      RETURN 0;
   END IF;
END
$$ 
LANGUAGE 'plpgsql';

/**********  OPERACION ***************************************************************************************/

CREATE 
	OR REPLACE FUNCTION CONFIG_SQL_OPERACION ( _idOperacion INTEGER, _descripcion VARCHAR, _estado VARCHAR, _start VARCHAR, _length VARCHAR, _orderBy VARCHAR ) RETURNS refcursor AS $$ DECLARE
	qry TEXT;
REF refcursor;
cadenaIdOperacion TEXT;
cadenaEstado TEXT;
cadenaDescripcion TEXT;
BEGIN
	IF
		( _idOperacion <> 0 ) THEN
			cadenaIdOperacion := ' AND ID_OPERACION = ''' || _idOperacion || '''  ';
		ELSE cadenaIdOperacion := '';
		
	END IF;
	IF
		( _estado IS NOT NULL ) THEN
			cadenaEstado := ' AND ESTADO = ''' || _estado || '''  ';
		ELSE cadenaEstado := '';
		
	END IF;
	IF
		( _descripcion IS NOT NULL ) THEN
			cadenaDescripcion := ' AND UPPER(DESCOPERACION) LIKE ''%' || UPPER ( _descripcion ) || '%'' ';
		ELSE cadenaDescripcion := '';
		
	END IF;
	qry := ' SELECT * FROM (SELECT ID_OPERACION, DESCOPERACION, ESTADO, ROW_NUMBER () OVER (ORDER BY ID_OPERACION) AS rnum 
	FROM OPERACION WHERE 1=1 ' || cadenaIdOperacion || cadenaDescripcion || cadenaEstado || ' ) TB ';
	IF
		( _length IS NOT NULL AND _start IS NOT NULL ) THEN
		IF
			( _start = 'all' AND _length = 'all' ) THEN
				qry := qry || ' ORDER BY ' || _orderBy;
			ELSE qry := qry || ' WHERE TB.RNUM >= ' || _start || '+1 AND TB.RNUM <= ' || _start || ' + ' || _length || ' ORDER BY ' || _orderBy;
			
		END IF;
		ELSE qry := ' SELECT COUNT(ID_OPERACION) AS CANTIDAD FROM OPERACION
		WHERE 1=1 ' || cadenaIdOperacion || cadenaDescripcion || cadenaEstado;
		
	END IF;
	OPEN REF FOR EXECUTE qry;
	RETURN ( REF );
	
END;
$$ LANGUAGE'plpgsql' VOLATILE;

CREATE OR REPLACE FUNCTION CONFIG_IUD_OPERACION
(
	_idOperacion INTEGER,
	_descripcion VARCHAR,
	_estado VARCHAR,
	_usuario VARCHAR,
	_operacion INTEGER
)
  RETURNS INTEGER AS $$
DECLARE
	conteo integer;
BEGIN

	IF(_operacion = 1)THEN	
			SELECT COUNT(*) INTO conteo FROM OPERACION WHERE DESCOPERACION = _descripcion;
			IF(conteo = 0)THEN
					INSERT INTO OPERACION
								(id_operacion,descoperacion,estado,usuarioreg,fechareg)
						VALUES 
								(nextval('sec_id_operacion'),_descripcion,_estado,_usuario,CURRENT_DATE);
								IF FOUND THEN
									RETURN 1;
								END IF;
			ELSE
					RETURN 100; --REGISTRO YA EXISTE
			END IF;
	
	ELSIF(_operacion = 2)THEN
			SELECT COUNT(*) INTO conteo FROM OPERACION WHERE DESCOPERACION = _descripcion;
			IF(conteo = 0)THEN
					UPDATE OPERACION
						SET descoperacion = _descripcion, usuarioupd = _usuario, fechaupd = CURRENT_DATE
						where id_operacion = _idOperacion;
								IF FOUND THEN
									RETURN 2;
								END IF;
			ELSE
					RETURN 100; --REGISTRO YA EXISTE
			END IF;
				
	ELSIF(_operacion = 3)THEN
			UPDATE OPERACION
				SET estado = _estado, usuarioupd = _usuario, fechaupd = CURRENT_DATE
				where id_operacion = _idOperacion;
						IF FOUND THEN
							RETURN 3;
					 END IF;

	END IF;
	
	 IF NOT FOUND THEN
      RETURN 0;
   END IF;
END
$$ 
LANGUAGE 'plpgsql';