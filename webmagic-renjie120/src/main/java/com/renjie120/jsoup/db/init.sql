drop table table_new_commercial_house;
drop table table_new_house_sort;
drop table table_sec_hand_house_sort;
drop table table_new_house;
drop table table_sec_house;

delete from  table_new_commercial_house;
delete from  table_new_house_sort;
delete from  table_sec_hand_house_sort;
delete from  table_new_house;
delete from  table_sec_house;

create table table_new_commercial_house(
  year int,
  month int,
  city varchar(10),
  tongbi float,
  huanbi float,
  dingji float,id int(10) unsigned NOT NULL AUTO_INCREMENT,PRIMARY KEY    (id)
);

create table table_new_house_sort(
  year int,
  month int,
  city varchar(10),
  tongbi float,
  huanbi float,
  dingji float,
  sorttype smallint,id int(10) unsigned NOT NULL AUTO_INCREMENT,PRIMARY KEY    (id)
);

create table table_sec_hand_house_sort(
  year int,
  month int,
  city varchar(10),
  tongbi float,
  huanbi float,
  dingji float,
  sorttype smallint,id int(10) unsigned NOT NULL AUTO_INCREMENT,PRIMARY KEY    (id)
);


create table table_new_house(
  year int,
  month int,
  city varchar(10),
  tongbi float,
  huanbi float ,
  dingji float,id int(10) unsigned NOT NULL AUTO_INCREMENT,PRIMARY KEY    (id)
);

create table table_sec_house(
  year int,
  month int,
  city varchar(10),
  tongbi float,
  huanbi float,
  dingji float,id int(10) unsigned NOT NULL AUTO_INCREMENT,PRIMARY KEY    (id)
);

