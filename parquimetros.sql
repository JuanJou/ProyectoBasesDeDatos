CREATE DATABASE parquimetros;

USE parquimetros;

CREATE TABLE conductores(
	dni INT unsigned NOT NULL,
	nombre VARCHAR(20) NOT NULL,
	apellido VARCHAR(20) NOT NULL,
	direccion VARCHAR(30) NOT NULL,
	telefono VARCHAR(10),
	registro INT unsigned NOT NULL,

	CONSTRAINT pk_conductor
	PRIMARY KEY (dni),

	KEY (nombre,apellido)
)ENGINE=InnoDB;

CREATE TABLE inspectores(
	dni INT unsigned NOT NULL,
	nombre VARCHAR(20) NOT NULL,
	apellido VARCHAR(20) NOT NULL,
	password VARCHAR(32) NOT NULL,
	legajo INT unsigned NOT NULL AUTO_INCREMENT,

	CONSTRAINT pk_inspector 
	PRIMARY KEY (legajo),

	KEY (nombre,apellido)


)ENGINE=InnoDB;

CREATE TABLE automoviles(
	patente CHAR(6) NOT NULL,
	marca VARCHAR(20) NOT NULL,
	modelo VARCHAR(15) NOT NULL,
	color VARCHAR(15) NOT NULL,
	dni INT unsigned NOT NULL,

	CONSTRAINT pk_auto
	PRIMARY KEY (patente),
	FOREIGN KEY (dni) REFERENCES conductores(dni),

	KEY (marca,modelo)
)ENGINE=InnoDB;

CREATE TABLE tipos_tarjeta(
	descuento DECIMAL(3,2) unsigned NOT NULL,
	tipo VARCHAR(20) NOT NULL,

	CONSTRAINT pk_tipo
	PRIMARY KEY (tipo)

)ENGINE=InnoDB;

CREATE TABLE tarjetas(
	id_tarjeta INT unsigned NOT NULL AUTO_INCREMENT,
	saldo DECIMAL(5,2) NOT NULL,
	tipo VARCHAR(20) NOT NULL,
	patente CHAR(6) NOT NULL,

	CONSTRAINT pk_tarjeta 
	PRIMARY KEY (id_tarjeta),

	FOREIGN KEY (tipo) REFERENCES tipos_tarjeta(tipo)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	FOREIGN KEY (patente) REFERENCES automoviles(patente)
	ON DELETE RESTRICT ON UPDATE RESTRICT,

	KEY (patente)
)ENGINE=InnoDB;

CREATE TABLE ubicaciones(
	altura INT unsigned NOT NULL,
	calle VARCHAR(20) NOT NULL,
	tarifa DECIMAL(5,2) unsigned NOT NULL,

	CONSTRAINT pk_ubicacion 
	PRIMARY KEY (calle,altura)
)ENGINE=InnoDB;

CREATE TABLE parquimetros(
	id_parq INT unsigned NOT NULL AUTO_INCREMENT,
	numero INT unsigned NOT NULL,
	altura INT unsigned NOT NULL,
	calle VARCHAR(20) NOT NULL,

	CONSTRAINT pk_parq 
	PRIMARY KEY (id_parq),

	FOREIGN KEY (calle,altura) REFERENCES ubicaciones(calle,altura)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	KEY (calle,altura)
)ENGINE=InnoDB;

CREATE TABLE estacionamientos(
	id_tarjeta INT unsigned NOT NULL,
	id_parq INT unsigned NOT NULL,
	fecha_ent date NOT NULL,
	hora_ent time NOT NULL,
	fecha_sal date,
	hora_sal time,

	CONSTRAINT pk_estacionamiento
	PRIMARY KEY (id_parq,fecha_ent,hora_ent),

	FOREIGN KEY (id_tarjeta) REFERENCES tarjetas(id_tarjeta)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	FOREIGN KEY (id_parq) REFERENCES parquimetros(id_parq)
	ON DELETE RESTRICT ON UPDATE CASCADE

	
)ENGINE=InnoDB;

CREATE TABLE accede(
	legajo INT unsigned NOT NULL,
	id_parq INT unsigned NOT NULL,
	fecha date NOT NULL,
	hora time NOT NULL,

	CONSTRAINT pk_accede 
	PRIMARY KEY (id_parq,fecha,hora),

	FOREIGN KEY (legajo) REFERENCES inspectores(legajo)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	FOREIGN KEY (id_parq) REFERENCES parquimetros(id_parq) 
	ON DELETE RESTRICT ON UPDATE CASCADE,

	KEY (legajo)
)ENGINE=InnoDB;

CREATE TABLE asociado_con(
	id_asociado_con INT unsigned NOT NULL AUTO_INCREMENT,
	legajo INT unsigned NOT NULL,
	calle VARCHAR(20) NOT NULL,
	altura INT unsigned NOT NULL,
	dia CHAR(2) NOT NULL,
	turno CHAR(1) NOT NULL,

	CONSTRAINT pk_asociado
	PRIMARY KEY (id_asociado_con),

	FOREIGN KEY (legajo) REFERENCES inspectores(legajo)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	FOREIGN KEY (calle,altura) REFERENCES ubicaciones(calle,altura)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	KEY(legajo)
)ENGINE=InnoDB;

CREATE TABLE multa(
	numero INT unsigned NOT NULL AUTO_INCREMENT,
	fecha date NOT NULL,
	hora time NOT NULL,
	patente CHAR(6) NOT NULL,
	id_asociado_con INT unsigned NOT NULL,

	CONSTRAINT pk_multa
	PRIMARY KEY (numero),

	FOREIGN KEY (patente) REFERENCES automoviles(patente)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	FOREIGN KEY (id_asociado_con) REFERENCES asociado_con(id_asociado_con)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	KEY(id_asociado_con)
)ENGINE=InnoDB;

CREATE TABLE ventas(
	id_tarjeta INT unsigned NOT NULL,
	saldo DECIMAL(5,2) NOT NULL,
	tipo VARCHAR(20) NOT NULL,
	fecha date NOT NULL,
	hora time NOT NULL,

	CONSTRAINT pk_ventas
	PRIMARY KEY(id_tarjeta),

	FOREIGN KEY (id_tarjeta) REFERENCES tarjetas(id_tarjeta)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	KEY(fecha,hora)
)ENGINE=InnoDB;

GRANT ALL PRIVILEGES ON parquimetros.* TO admin@localhost 
    IDENTIFIED BY 'admin' WITH GRANT OPTION;

CREATE USER venta@'%' IDENTIFIED BY 'venta';
GRANT INSERT ON parquimetros.tarjetas TO venta;

CREATE USER inspector@'%' IDENTIFIED BY 'inspector';


CREATE VIEW estacionados AS SELECT p.calle, p.altura, t.patente FROM (parquimetros as p NATURAL JOIN estacionamientos) NATURAL JOIN tarjetas as t WHERE fecha_sal IS NULL; 

GRANT SELECT ON parquimetros.estacionados TO inspector;
GRANT SELECT ON parquimetros.inspectores TO inspector;
GRANT INSERT ON parquimetros.multa TO inspector;
GRANT INSERT ON parquimetros.accede TO inspector;
GRANT SELECT ON parquimetros.parquimetros TO inspector;
GRANT SELECT ON parquimetros.asociado_con TO inspector;
GRANT SELECT ON parquimetros.multa TO inspector;
GRANT SELECT ON parquimetros.automoviles TO venta;
GRANT SELECT ON parquimetros.tipos_tarjeta TO venta;

GRANT CREATE USER ON *.* TO admin@localhost;

FLUSH PRIVILEGES;

DELIMITER !
CREATE PROCEDURE conectar(IN id_card INTEGER,IN id_p INTEGER)
	BEGIN
		DECLARE saldoActual DECIMAL(5,2);
		DECLARE descu DECIMAL(3,2) unsigned;
		DECLARE tarifaAux DECIMAL(5,2) unsigned;
		DECLARE fecha_in date;
		DECLARE hora_in time;
		DECLARE nuevo_saldo DECIMAL(5,2) DEFAULT -999.99;
		DECLARE auxiliar DECIMAL(10,2) DEFAULT 0;
		DECLARE diferencia INTEGER DEFAULT 0;

		#Codigo en caso de error
		DECLARE codigo_SQL  CHAR(5) DEFAULT '00000';	 
	 	DECLARE codigo_MYSQL INT DEFAULT 0;
	 	DECLARE mensaje_error TEXT;
		DECLARE EXIT HANDLER FOR SQLEXCEPTION 	 	 
	 	BEGIN 
				GET DIAGNOSTICS CONDITION 1  codigo_MYSQL= MYSQL_ERRNO,codigo_SQL= RETURNED_SQLSTATE,mensaje_error= MESSAGE_TEXT;
	    SELECT 'SQLEXCEPTION!, transacción abortada' AS resultado, 
		        codigo_MySQL, codigo_SQL,  mensaje_error;		
        ROLLBACK;
	  END;

	  START TRANSACTION;
	  
		  IF EXISTS(SELECT * FROM parquimetros WHERE id_parq=id_p) AND EXISTS(SELECT * FROM tarjetas WHERE id_tarjeta=id_card) THEN

			SELECT tarifa INTO tarifaAux FROM parquimetros NATURAL JOIN ubicaciones WHERE id_parq=id_p LOCK IN SHARE MODE;
			SELECT saldo INTO saldoActual FROM tarjetas WHERE id_tarjeta=id_card FOR UPDATE;
			SELECT descuento INTO descu FROM tipos_tarjeta NATURAL JOIN tarjetas WHERE id_tarjeta=id_card LOCK IN SHARE MODE;

			IF EXISTS(SELECT * FROM estacionamientos WHERE id_tarjeta=id_card AND fecha_sal IS NULL FOR UPDATE) THEN #Quiere decir que hay un estacionamiento abierto y debe ser cerrado
				
				SELECT fecha_ent INTO fecha_in FROM estacionamientos WHERE id_tarjeta=id_card AND fecha_sal IS NULL;
				SELECT hora_ent INTO hora_in FROM estacionamientos WHERE id_tarjeta=id_card AND fecha_sal IS NULL;
				UPDATE estacionamientos SET hora_sal=curtime(),fecha_sal=curdate() WHERE id_tarjeta=id_card AND fecha_sal IS NULL;
				
				SELECT TIMESTAMPDIFF(MINUTE,CONCAT(fecha_in,' ',hora_in),now()) INTO diferencia; #Calculo el tiempo que paso estacionado


				SELECT (saldoActual-CEILING(diferencia)*tarifaAux*(1-descu)) INTO auxiliar;#Calculo el nuevo saldo

				if (auxiliar>-999.99) THEN
					SELECT auxiliar INTO nuevo_saldo;
				END IF;

				IF (nuevo_saldo=-999.99) THEN
					UPDATE tarjetas SET saldo=-999.99 WHERE id_tarjeta=id_card;
					SELECT 'Cierre' AS Operacion,'Error' AS Resultado,-999.99 AS SaldoRestante;
				ELSE
					UPDATE tarjetas SET saldo=nuevo_saldo WHERE id_tarjeta=id_card;
					SELECT 'Cierre' AS Operacion,'Exito' AS Resultado,nuevo_saldo AS SaldoRestante,patente,diferencia AS minutosTranscurridos FROM tarjetas WHERE id_tarjeta=id_card;
				END IF;
			ELSE #No hay estacionamiento por lo tanto es una apertura
				IF (saldoActual<=0) THEN #Saldo negativo no se puede abrir estacionamiento
					SELECT 'Apertura' AS Operacion,'Error' AS Resultado,'Saldo insuficiente' as TiempoDisponible;
				ELSE
					INSERT INTO estacionamientos VALUES (id_card,id_p,curdate(),curtime(),NULL,NULL); 
					SELECT 'Apertura' AS Operacion,'Exito' AS Resultado,ROUND(saldoActual/(tarifaAux*(1-descu))) AS TiempoDisponible,patente FROM tarjetas WHERE id_tarjeta=id_card;
				END IF;
			END IF;
		  ELSE  
		  	SELECT 'Error','No existe tarjeta o parquimetro','';
		  	ROLLBACK;
		  END IF;	

	  COMMIT;
	END;!

DELIMITER ;

CREATE USER parquimetros@'%' IDENTIFIED BY 'parq';
GRANT EXECUTE ON PROCEDURE parquimetros.conectar TO parquimetros;


CREATE TRIGGER venta_tarjetas
AFTER INSERT ON tarjetas
FOR EACH ROW
INSERT INTO ventas(id_tarjeta,tipo,saldo,fecha,hora) VALUES (NEW.id_tarjeta,NEW.tipo,NEW.saldo,curdate(),curtime());