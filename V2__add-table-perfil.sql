create table pefiles(
    id int auto_increment primary key ,
    nombre varchar(50)
);

create table usuario_roles(
    id_perfil int,
    id_usuario int,
    constraint fk_usuario foreign key (id_usuario) references usuarios(id),
    constraint fk_roles foreign key (id_perfil) references pefiles(id)
)