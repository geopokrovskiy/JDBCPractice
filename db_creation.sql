CREATE TABLE speciality
(
    id                 bigint primary key auto_increment,
    speciality_name    varchar(50) not null,
    active_status_spec bit         not null
);

CREATE TABLE skills
(
    id                  bigint primary key auto_increment,
    skill_name          varchar(50) not null,
    active_status_skill bit         not null
);

CREATE TABLE developers
(
    id                bigint primary key auto_increment,
    id_spec           bigint      not null,
    first_name        varchar(50) not null,
    last_name         varchar(50) not null,
    active_status_dev bit         not null,
    foreign key (id_spec) references speciality (id)
        on delete cascade on update cascade
);

CREATE TABLE devs_skills
(
    id       bigint primary key auto_increment,
    id_dev   bigint not null,
    id_skill bigint not null,
    foreign key (id_dev) references developers (id)
        on delete cascade on update cascade,
    foreign key (id_skill) references skills (id)
        on delete cascade on update cascade
);

ALTER TABLE developers MODIFY id_spec bigint default 0;

INSERT into speciality(id, speciality_name, active_status_spec) values (1, 'default speciality', 1);




