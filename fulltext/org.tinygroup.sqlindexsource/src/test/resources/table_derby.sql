--prompt PL/SQL Developer import file
--prompt Created on 2016年4月7日 by yancheng
--set feedback off
--set define off
--First　delete　the　tables　if　they　exist.　
--Ignore　the　table　does　not　exist　error　if　present　
drop table ARTICLE;

create table ARTICLE
(
  id     VARCHAR(32) primary key  not null,
  title   VARCHAR(100),
  content   VARCHAR(500),
  author    VARCHAR(32),
  create_date    VARCHAR(8)
);