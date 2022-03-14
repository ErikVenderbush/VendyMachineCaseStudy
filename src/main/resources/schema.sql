create or replace table users
(
	uid int auto_increment
		primary key,
	twitch_id bigint not null,
	twitch_username varchar(25) not null,
	twitch_display_name varchar(25) not null,
	twitch_pfp varchar(150) not null,
	password varchar(100) null,
	constraint twitch_id_uindex
		unique (twitch_id)
);

create or replace table commands
(
	cid bigint auto_increment
		primary key,
	command varchar(255) not null,
	response varchar(500) not null,
	enabled bit default b'1' not null,
	permission varchar(25) default 'Everyone' not null,
	cooldown int default 0 not null,
	user int not null,
	constraint commands_uid
		foreign key (user) references users (uid)
);

create or replace table levels
(
	lid bigint auto_increment
		primary key,
	channel int not null,
	viewer int not null,
	level int default 0 not null,
	experience int default 0 not null,
	constraint levels_channel_fk
		foreign key (channel) references users (uid),
	constraint levels_viewer__fk
		foreign key (viewer) references users (uid)
);

create or replace table user_roles
(
	rid bigint auto_increment
		primary key,
	user int not null,
	role varchar(8) null,
	constraint user_roles_users_uid_fk
		foreign key (user) references users (uid)
);