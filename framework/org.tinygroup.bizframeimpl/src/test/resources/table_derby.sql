--prompt PL/SQL Developer import file
--prompt Created on 2013年7月30日 by renhui
--set feedback off
--set define off
--First　delete　the　tables　if　they　exist.　
--Ignore　the　table　does　not　exist　error　if　present　
drop table T_USER;
drop table T_FUNCTION;
drop table T_USER_T_FUNCTION;
drop table incrementer;
--prompt Creating T_USER...
create table T_USER
(
  user_id   VARCHAR(32) primary key not null,
  name   VARCHAR(32),
  title  VARCHAR(32),
  description    VARCHAR(32)
);
--prompt Creating T_FUNCTION...
create table T_FUNCTION
(
  fun_id    VARCHAR(32) primary key not null,
  name   VARCHAR(32),
  title  VARCHAR(32),
  description    VARCHAR(32)
);
--prompt Creating T_USER_T_FUNCTION...
create table T_USER_T_FUNCTION
(
  user_fun_id VARCHAR(32) primary key not null,
  user_id     VARCHAR(32),
  fun_id   VARCHAR(32),
  status   VARCHAR(1)
);
create table incrementer
(
  value int generated always as identity, 
  dummy char(1)
);

--set feedback on
--set define on
--prompt Done.
