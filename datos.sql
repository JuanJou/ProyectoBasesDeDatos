USE parquimetros;

INSERT INTO tipos_tarjeta VALUES (0.50,'Premium');
INSERT INTO tipos_tarjeta VALUES (0.25,'Frecuente');
INSERT INTO tipos_tarjeta VALUES (0.00,'Comun');

INSERT INTO ubicaciones VALUES (0,'Av. Alem',002.50);
INSERT INTO ubicaciones VALUES (500,'Estomba',003.00);
INSERT INTO ubicaciones VALUES (700,'Vieytes',001.00);
INSERT INTO ubicaciones VALUES (100,'11 de Abril',001.50);
INSERT INTO ubicaciones VALUES (200,'Soler',004.00);
INSERT INTO ubicaciones VALUES (300,'Alsina',002.00);

INSERT INTO parquimetros(numero,altura,calle) VALUES (515,500,'Estomba');
INSERT INTO parquimetros(numero,altura,calle) VALUES (556,500,'Estomba');
INSERT INTO parquimetros(numero,altura,calle) VALUES (594,500,'Estomba');
INSERT INTO parquimetros(numero,altura,calle) VALUES (109,100,'11 de Abril');
INSERT INTO parquimetros(numero,altura,calle) VALUES (172,100,'11 de Abril');
INSERT INTO parquimetros(numero,altura,calle) VALUES (217,200,'Soler');
INSERT INTO parquimetros(numero,altura,calle) VALUES (272,200,'Soler');
INSERT INTO parquimetros(numero,altura,calle) VALUES (12,0,'Av. Alem');
INSERT INTO parquimetros(numero,altura,calle) VALUES (57,0,'Av. Alem');
INSERT INTO parquimetros(numero,altura,calle) VALUES (76,0,'Av. Alem');
INSERT INTO parquimetros(numero,altura,calle) VALUES (707,700,'Vieytes');
INSERT INTO parquimetros(numero,altura,calle) VALUES (728,700,'Vieytes');
INSERT INTO parquimetros(numero,altura,calle) VALUES (777,700,'Vieytes');
INSERT INTO parquimetros(numero,altura,calle) VALUES (329,300,'Alsina');
INSERT INTO parquimetros(numero,altura,calle) VALUES (378,300,'Alsina');


INSERT INTO conductores VALUES (40373955,'Juan','Jouglard','Mitre 250','4544996',177);
INSERT INTO conductores VALUES (40373956,'Martin','Garcia','Cordoba 250','4544996',132);
INSERT INTO conductores VALUES (40373957,'Juliana','Girotti','Parana 250','4544996',145);
INSERT INTO conductores VALUES (40373958,'Mariela','Fernandez','Estomba 250','4544996',198);
INSERT INTO conductores VALUES (40373959,'Adrian','Perez',' 250','4544996',201);
INSERT INTO conductores VALUES (40373950,'Mercedes','Cabral','Mitre 250','4544996',89);
INSERT INTO conductores VALUES (40373963,'Agustin','Gimenez','Mitre 250','4544996',167);
INSERT INTO conductores VALUES (40373980,'Carlos','Pintos','Mitre 250','4544996',134);
INSERT INTO conductores VALUES (40373122,'Sofia','Martinez','Mitre 250','4544996',127);
INSERT INTO conductores VALUES (40374566,'Pedro','Robles','Mitre 250','4544996',105);


INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (22544108,'Luccas','Riggs',MD5('PWK51HWO7PQ'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (20798055,'Marcela','Henderson',MD5('RPZ97FUJ5KO'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (35559433,'Mathias','Bray',MD5('HZG90NDY8UW'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (35465134,'Antwan','Bauer',MD5('CMX53GFZ9KA'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (38637149,'Stefany','Lindsey',MD5('LTW70YTY4PI'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (26834336,'Ornella','Bird',MD5('TQI55ZCY9NL'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (34653598,'Gamaliel','Chaney',MD5('ZGE61FQP5GI'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (14206345,'Daniela','England',MD5('IFB21XRA0CB'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (21365840,'Joan','Mayer',MD5('AMM88MSV2CL'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (14807402,'Kenneth','Mcguire',MD5('BRG65TQX0IG'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (18395517,'Paola','Bean',MD5('ZVP44DIT1GU'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (17847065,'Zahir','Mcguire',MD5('CZM79NHS2JY'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (37808097,'Natasha','Sims',MD5('ORF85SPY1XM'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (39170597,'Fernanda','Thomas',MD5('SWX34KAX6UI'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (19728643,'Angelica','Gilliam',MD5('HJQ23MAX4PQ'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (34694296,'Ashley','Fisher',MD5('RZS42YBE6NU'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (28238030,'Adolfo','Fitzpatrick',MD5('ROI55XPB4JI'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (37853517,'Deivy','Haynes',MD5('WBF91PBN2RQ'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (21492463,'Liam','Benson',MD5('LNT85SFZ2ZK'));
INSERT INTO inspectores (dni,nombre,apellido,password) VALUES (37884331,'Miranda','Reese',MD5('IQC56CZT8XD'));

INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('LAD018','Volkswagen','Gol','Rojo',40373958);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('SJH860','Volkswagen','Amarok','Blanco',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('RKG474','Volkswagen','Tiguan','Gris claro',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('YRC809','Chevrolet','Cruze','Rojo',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('QNO331','Chevrolet','Camaro','Dorado',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('QMX547','Peugeot','308','Blanco',40373958);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('HFW070','Peugeot','3008,','Verde',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('EZW131','Toyota','Camry','Rojo',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('WUB248','Toyota','Corolla','Rojo',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('UIP056','Fiat','Uno','Verde',40373955);
INSERT INTO automoviles (patente,marca,modelo,color,dni) VALUES ('CIO476','Fiat','Palio','Negro',40373955);

INSERT INTO tarjetas(saldo,tipo,patente) VALUES (150.00,'Premium','WUB248');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (000.30,'Comun','CIO476');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (100.00,'Comun','HFW070');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (050.70,'Comun','YRC809');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (010.87,'Frecuente','RKG474');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (122.50,'Frecuente','UIP056');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (047.80,'Frecuente','QNO331');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (150.00,'Comun','QMX547');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (250.60,'Premium','LAD018');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (057.25,'Comun','EZW131');
INSERT INTO tarjetas(saldo,tipo,patente) VALUES (014.20,'Comun','SJH860');


INSERT INTO estacionamientos VALUES (1,1,'2017-08-22','13:23:51',NULL,NULL);
INSERT INTO estacionamientos VALUES (2,10,'2017-08-22','13:35:07',NULL,NULL);
INSERT INTO estacionamientos VALUES (3,12,'2017-08-21','19:02:41','2017-08-21','19:45:37');
INSERT INTO estacionamientos VALUES (4,8,'2017-08-20','07:43:13','2017-08-20','12:06:34');
INSERT INTO estacionamientos VALUES (5,7,'2017-08-22','15:33:32',NULL,NULL);
INSERT INTO estacionamientos VALUES (6,2,'2017-08-22','09:03:52',NULL,NULL);
INSERT INTO estacionamientos VALUES (2,5,'2017-08-19','09:11:24','2017-08-19','12:35:12');
INSERT INTO estacionamientos VALUES (7,11,'2017-08-21','16:03:31','2017-08-21','19:12:24');
INSERT INTO estacionamientos VALUES (2,13,'2017-08-22','11:43:41','2017-08-22','12:15:33');
INSERT INTO estacionamientos VALUES (4,6,'2017-08-22','17:43:11',NULL,NULL);
INSERT INTO estacionamientos VALUES (9,3,'2017-08-19','13:00:21','2017-08-19','17:38:17');
INSERT INTO estacionamientos VALUES (8,4,'2017-08-22','19:32:11',NULL,NULL);


INSERT INTO accede VALUES (1,11,'2017-08-20','11:34:17');
INSERT INTO accede VALUES (2,9,'2017-08-21','18:14:23');
INSERT INTO accede VALUES (3,7,'2017-08-21','14:48:46');
INSERT INTO accede VALUES (4,2,'2017-08-19','09:47:22');
INSERT INTO accede VALUES (5,12,'2017-08-22','19:14:54');
INSERT INTO accede VALUES (6,10,'2017-08-20','13:34:01');
INSERT INTO accede VALUES (7,1,'2017-08-19','14:04:19');
INSERT INTO accede VALUES (8,5,'2017-08-22','16:14:38');
INSERT INTO accede VALUES (9,6,'2017-08-20','17:43:52');

INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (1,'Alsina',300,'Lu','M'); 
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (2,'Alsina',300,'Lu','T');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (3,'Av. Alem',0,'Ma','M');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (4,'Av. Alem',0,'Ma','T');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (5,'Estomba',500,'Mi','M');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (6,'Estomba',500,'Mi','T');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (7,'Vieytes',700,'Ju','M');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (8,'Vieytes',700,'Ju','T');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (9,'11 de Abril',100,'Vi','M');
INSERT INTO asociado_con(legajo,calle,altura,dia,turno) VALUES (10,'11 de Abril',100,'Vi','T');

INSERT INTO multa(fecha,hora,patente,id_asociado_con) VALUES ('2017-08-21','11:38:12','SJH860',1);
INSERT INTO multa(fecha,hora,patente,id_asociado_con) VALUES ('2017-08-20','17:24:45','YRC809',2);
INSERT INTO multa(fecha,hora,patente,id_asociado_con) VALUES ('2017-08-19','10:21:57','QMX547',3);
INSERT INTO multa(fecha,hora,patente,id_asociado_con) VALUES ('2017-08-22','15:47:31','WUB248',4);
INSERT INTO multa(fecha,hora,patente,id_asociado_con) VALUES ('2017-08-20','09:55:42','CIO476',5);