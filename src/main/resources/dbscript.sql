
create table agent
(
	id int auto_increment
		primary key,
	login varchar(20) not null,
	pass varchar(100) not null,
	message varchar(255) null,
	isFree tinyint(1) default '0' not null,
	port int null,
	ip varchar(20) null
)
;

create table user
(
	id int auto_increment
		primary key,
	login varchar(20) not null,
	pass varchar(100) not null,
	message varchar(255) null,
	port int null,
	ip varchar(20) null
)
;




