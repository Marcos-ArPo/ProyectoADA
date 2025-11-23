drop database if exists parking;

create database parking;

use parking;

create table planta (
	id_planta int primary key,
	nombre_plaza varchar(100),
	num_plazas int,
	es_vip bool
);

create table plaza (
	id_plaza int primary key,
	id_planta int,
	numero_plaza int,
	disponible bool,
	es_vip bool,
	foreign key (id_planta) references planta(id_planta)
);

create table cliente (
	id_cliente int primary key,
	nombre varchar(50),
	apellidos varchar(50),
	matricula varchar(9),
	tipo_cli enum ("NORMAL","VIP"),
	cuota_pagada bool
);

create table reserva (
	id_reserva int primary key,
	id_plaza int,
	id_cliente int,
	fecha_res date,
	hora_ini time,
	hora_fin time,
	estado enum ("ACTIVA","FINALIZADA","CANCELADA"),
	foreign key (id_plaza) references plaza(id_plaza),
	foreign key (id_cliente) references cliente(id_cliente)
);

create table serviciovip (
	id_servicio int primary key,
	nombre_serv varchar(20),
	descr varchar(100),
	precio decimal(6,2)
);

create table reserva_servicio (
	id_reserva int,
	id_servicio int,
	primary key (id_reserva, id_servicio),
	foreign key (id_reserva) references reserva(id_reserva),
	foreign key (id_servicio) references serviciovip(id_servicio)
);

-- tabla planta
insert into planta (id_planta, nombre_plaza, num_plazas, es_vip) values
(1, 'planta baja', 50, false),
(2, 'planta -1', 40, false),
(3, 'planta vip', 20, true);

-- tabla plaza
insert into plaza (id_plaza, id_planta, numero_plaza, disponible, es_vip) values
(1, 1, 1, true, false),
(2, 1, 2, true, false),
(3, 1, 3, false, false),
(4, 2, 10, true, false),
(5, 2, 11, false, false),
(6, 3, 1, true, true),
(7, 3, 2, false, true);

-- tabla cliente
insert into cliente (id_cliente, nombre, apellidos, matricula, tipo_cli, cuota_pagada) values
(1, 'carlos', 'martínez lópez', 'FJR2839', 'NORMAL', true),
(2, 'laura', 'gómez ruiz', 'QPL9271', 'NORMAL', false),
(3, 'maría', 'pérez díaz', 'ZBW1043', 'VIP', true),
(4, 'juan', 'santos garcía', 'MHK5510', 'VIP', true);

-- tabla reserva
insert into reserva (id_reserva, id_plaza, id_cliente, fecha_res, hora_ini, hora_fin, estado) values
(1, 3, 1, '2025-01-10', '08:30:00', '12:00:00', 'FINALIZADA'),
(2, 5, 2, '2025-02-02', '09:00:00', '11:30:00', 'CANCELADA'),
(3, 7, 3, '2025-03-15', '15:00:00', '18:00:00', 'ACTIVA'),
(4, 6, 4, '2025-03-20', '10:00:00', '14:00:00', 'ACTIVA');

-- tabla serviciovip
insert into serviciovip (id_servicio, nombre_serv, descr, precio) values
(1, 'Lavado', 'lavado completo exterior e interior', 15.00),
(2, 'Carga', 'carga de vehículo eléctrico', 10.00),
(3, 'Aparcacoches', 'servicio de aparcacoches vip', 20.00);

-- tabla reserva_servicio
insert into reserva_servicio (id_reserva, id_servicio) values
(3, 1),
(3, 2),
(4, 1),
(4, 3);
