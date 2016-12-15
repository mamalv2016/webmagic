 建表语句： 
CREATE TABLE  table_new_commercial_house  (
   year  int(11) DEFAULT NULL,
   month  int(11) DEFAULT NULL,
   city  varchar(10) DEFAULT NULL,
   tongbi  float DEFAULT NULL,
   huanbi  float DEFAULT NULL,
   dingji  float DEFAULT NULL,
   id  int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY ( id )
);
 
CREATE TABLE  table_new_house  (
   year  int(11) DEFAULT NULL,
   month  int(11) DEFAULT NULL,
   city  varchar(10) DEFAULT NULL,
   tongbi  float DEFAULT NULL,
   huanbi  float DEFAULT NULL,
   dingji  float DEFAULT NULL,
   id  int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY ( id )
)   ;
CREATE TABLE  table_new_house_sort  (
   year  int(11) DEFAULT NULL,
   month  int(11) DEFAULT NULL,
   city  varchar(10) DEFAULT NULL,
   tongbi  float DEFAULT NULL,
   huanbi  float DEFAULT NULL,
   dingji  float DEFAULT NULL,
   sorttype  smallint(6) DEFAULT NULL,
   id  int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY ( id )
)  ; 
CREATE TABLE  table_sec_hand_house_sort  (
   year  int(11) DEFAULT NULL,
   month  int(11) DEFAULT NULL,
   city  varchar(10) DEFAULT NULL,
   tongbi  float DEFAULT NULL,
   huanbi  float DEFAULT NULL,
   dingji  float DEFAULT NULL,
   sorttype  smallint(6) DEFAULT NULL,
   id  int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY ( id )
) ;
 
CREATE TABLE  table_sec_house  (
   year  int(11) DEFAULT NULL,
   month  int(11) DEFAULT NULL,
   city  varchar(10) DEFAULT NULL,
   tongbi  float DEFAULT NULL,
   huanbi  float DEFAULT NULL,
   dingji  float DEFAULT NULL,
   id  int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY ( id )
)  ;
CREATE TABLE  table_tongji_page_url  (
   id  int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
   url  varchar(200) DEFAULT NULL,
   deleteflag  varchar(1) DEFAULT NULL,
   status  varchar(1) DEFAULT NULL,
   title  varchar(300) DEFAULT NULL,
  PRIMARY KEY ( id )
)  ;
 
CREATE TABLE  table_tongji_url  (
   url  varchar(200) DEFAULT NULL,
   title  varchar(300) DEFAULT NULL,
   deleteflag  varchar(1) DEFAULT NULL,
   status  varchar(1) DEFAULT NULL,
   id  int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY ( id )
)   ;
--------------------------------------------------------------------------------
delete from table_new_commercial_house;    
delete from table_new_house           ;    
delete from table_new_house_sort      ;    
delete from table_sec_hand_house_sort ;    
delete from table_sec_house           ;    
delete from table_tongji_page_url     ;    
delete from table_tongji_url          ;    
--------------------------------------------------------------------------------
select *  from table_new_commercial_house t;
select *  from table_new_house            t;
select *  from table_new_house_sort       t;
select *  from table_sec_hand_house_sort  t;
select *  from table_sec_house            t;
select *  from table_tongji_page_url      t;
select *  from table_tongji_url           t;
--------------------------------------------------------------------------------
select count(1),'table_new_commercial_house'  from table_new_commercial_house t union 
select count(1),'table_new_house'  from table_new_house t union
select count(1),'table_new_house_sort'  from table_new_house_sort t union
select count(1),'table_sec_hand_house_sort'  from table_sec_hand_house_sort t union
select count(1),'table_sec_house'  from table_sec_house t union
select count(1),'table_tongji_page_url'  from table_tongji_page_url t union
select count(1),'table_tongji_url'  from table_tongji_url t ;