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

